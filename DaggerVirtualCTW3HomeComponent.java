package com.petkit.android.activities.virtual.ctw3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.virtual.ctw3.VirtualCTW3HomeActivity;
import com.petkit.android.activities.virtual.ctw3.component.VirtualCTW3HomeComponent;
import com.petkit.android.activities.virtual.ctw3.contract.VirtualCTW3HomeContract;
import com.petkit.android.activities.virtual.ctw3.model.VirtualCTW3HomeModel;
import com.petkit.android.activities.virtual.ctw3.model.VirtualCTW3HomeModel_Factory;
import com.petkit.android.activities.virtual.ctw3.presenter.VirtualCTW3HomePresenter;
import com.petkit.android.activities.virtual.ctw3.presenter.VirtualCTW3HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes6.dex */
public final class DaggerVirtualCTW3HomeComponent implements VirtualCTW3HomeComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<VirtualCTW3HomeContract.View> viewProvider;
    public Provider<VirtualCTW3HomeModel> virtualCTW3HomeModelProvider;
    public Provider<VirtualCTW3HomePresenter> virtualCTW3HomePresenterProvider;

    public DaggerVirtualCTW3HomeComponent(AppComponent appComponent, VirtualCTW3HomeContract.View view) {
        initialize(appComponent, view);
    }

    public static VirtualCTW3HomeComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, VirtualCTW3HomeContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.virtualCTW3HomeModelProvider = DoubleCheck.provider(VirtualCTW3HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.virtualCTW3HomePresenterProvider = DoubleCheck.provider(VirtualCTW3HomePresenter_Factory.create(this.virtualCTW3HomeModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.virtual.ctw3.component.VirtualCTW3HomeComponent
    public void inject(VirtualCTW3HomeActivity virtualCTW3HomeActivity) {
        injectVirtualCTW3HomeActivity(virtualCTW3HomeActivity);
    }

    @CanIgnoreReturnValue
    public final VirtualCTW3HomeActivity injectVirtualCTW3HomeActivity(VirtualCTW3HomeActivity virtualCTW3HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(virtualCTW3HomeActivity, this.virtualCTW3HomePresenterProvider.get());
        return virtualCTW3HomeActivity;
    }

    public static final class Builder implements VirtualCTW3HomeComponent.Builder {
        public AppComponent appComponent;
        public VirtualCTW3HomeContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.virtual.ctw3.component.VirtualCTW3HomeComponent.Builder
        public Builder view(VirtualCTW3HomeContract.View view) {
            this.view = (VirtualCTW3HomeContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.virtual.ctw3.component.VirtualCTW3HomeComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.virtual.ctw3.component.VirtualCTW3HomeComponent.Builder
        public VirtualCTW3HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.view, VirtualCTW3HomeContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerVirtualCTW3HomeComponent(this.appComponent, this.view);
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

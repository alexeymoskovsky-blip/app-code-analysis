package com.petkit.android.activities.petkitBleDevice.hg.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.hg.HgHomeActivity;
import com.petkit.android.activities.petkitBleDevice.hg.contract.HgHomeContract;
import com.petkit.android.activities.petkitBleDevice.hg.model.HgHomeModel;
import com.petkit.android.activities.petkitBleDevice.hg.model.HgHomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.hg.module.HgHomeModule;
import com.petkit.android.activities.petkitBleDevice.hg.module.HgHomeModule_ProvideHgHomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.hg.module.HgHomeModule_ProvideHgHomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.hg.presenter.HgHomePresenter;
import com.petkit.android.activities.petkitBleDevice.hg.presenter.HgHomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerHgHomeComponent implements HgHomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<HgHomeModel> hgHomeModelProvider;
    public Provider<HgHomePresenter> hgHomePresenterProvider;
    public Provider<HgHomeContract.Model> provideHgHomeModelProvider;
    public Provider<HgHomeContract.View> provideHgHomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerHgHomeComponent(HgHomeModule hgHomeModule, AppComponent appComponent) {
        initialize(hgHomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(HgHomeModule hgHomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<HgHomeModel> provider = DoubleCheck.provider(HgHomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.hgHomeModelProvider = provider;
        this.provideHgHomeModelProvider = DoubleCheck.provider(HgHomeModule_ProvideHgHomeModelFactory.create(hgHomeModule, provider));
        this.provideHgHomeViewProvider = DoubleCheck.provider(HgHomeModule_ProvideHgHomeViewFactory.create(hgHomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.hgHomePresenterProvider = DoubleCheck.provider(HgHomePresenter_Factory.create(this.provideHgHomeModelProvider, this.provideHgHomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.hg.component.HgHomeComponent
    public void inject(HgHomeActivity hgHomeActivity) {
        injectHgHomeActivity(hgHomeActivity);
    }

    @CanIgnoreReturnValue
    public final HgHomeActivity injectHgHomeActivity(HgHomeActivity hgHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(hgHomeActivity, this.hgHomePresenterProvider.get());
        return hgHomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public HgHomeModule hgHomeModule;

        public Builder() {
        }

        public Builder hgHomeModule(HgHomeModule hgHomeModule) {
            this.hgHomeModule = (HgHomeModule) Preconditions.checkNotNull(hgHomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public HgHomeComponent build() {
            Preconditions.checkBuilderRequirement(this.hgHomeModule, HgHomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerHgHomeComponent(this.hgHomeModule, this.appComponent);
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

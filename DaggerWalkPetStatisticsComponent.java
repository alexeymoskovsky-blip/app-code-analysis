package com.petkit.android.activities.statistics.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.statistics.WalkPetStatisticsActivity;
import com.petkit.android.activities.statistics.contract.WalkPetStatisticsContract;
import com.petkit.android.activities.statistics.model.WalkPetStatisticsModel;
import com.petkit.android.activities.statistics.model.WalkPetStatisticsModel_Factory;
import com.petkit.android.activities.statistics.module.WalkPetStatisticsModule;
import com.petkit.android.activities.statistics.module.WalkPetStatisticsModule_ProvideWalkPetStatisticsModelFactory;
import com.petkit.android.activities.statistics.module.WalkPetStatisticsModule_ProvideWalkPetStatisticsViewFactory;
import com.petkit.android.activities.statistics.presenter.WalkPetStatisticsPresenter;
import com.petkit.android.activities.statistics.presenter.WalkPetStatisticsPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerWalkPetStatisticsComponent implements WalkPetStatisticsComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<WalkPetStatisticsContract.Model> provideWalkPetStatisticsModelProvider;
    public Provider<WalkPetStatisticsContract.View> provideWalkPetStatisticsViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<WalkPetStatisticsModel> walkPetStatisticsModelProvider;
    public Provider<WalkPetStatisticsPresenter> walkPetStatisticsPresenterProvider;

    public DaggerWalkPetStatisticsComponent(WalkPetStatisticsModule walkPetStatisticsModule, AppComponent appComponent) {
        initialize(walkPetStatisticsModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(WalkPetStatisticsModule walkPetStatisticsModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<WalkPetStatisticsModel> provider = DoubleCheck.provider(WalkPetStatisticsModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.walkPetStatisticsModelProvider = provider;
        this.provideWalkPetStatisticsModelProvider = DoubleCheck.provider(WalkPetStatisticsModule_ProvideWalkPetStatisticsModelFactory.create(walkPetStatisticsModule, provider));
        this.provideWalkPetStatisticsViewProvider = DoubleCheck.provider(WalkPetStatisticsModule_ProvideWalkPetStatisticsViewFactory.create(walkPetStatisticsModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.walkPetStatisticsPresenterProvider = DoubleCheck.provider(WalkPetStatisticsPresenter_Factory.create(this.provideWalkPetStatisticsModelProvider, this.provideWalkPetStatisticsViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.statistics.component.WalkPetStatisticsComponent
    public void inject(WalkPetStatisticsActivity walkPetStatisticsActivity) {
        injectWalkPetStatisticsActivity(walkPetStatisticsActivity);
    }

    @CanIgnoreReturnValue
    public final WalkPetStatisticsActivity injectWalkPetStatisticsActivity(WalkPetStatisticsActivity walkPetStatisticsActivity) {
        BaseActivity_MembersInjector.injectMPresenter(walkPetStatisticsActivity, this.walkPetStatisticsPresenterProvider.get());
        return walkPetStatisticsActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public WalkPetStatisticsModule walkPetStatisticsModule;

        public Builder() {
        }

        public Builder walkPetStatisticsModule(WalkPetStatisticsModule walkPetStatisticsModule) {
            this.walkPetStatisticsModule = (WalkPetStatisticsModule) Preconditions.checkNotNull(walkPetStatisticsModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public WalkPetStatisticsComponent build() {
            Preconditions.checkBuilderRequirement(this.walkPetStatisticsModule, WalkPetStatisticsModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerWalkPetStatisticsComponent(this.walkPetStatisticsModule, this.appComponent);
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

package com.petkit.android.activities.statistics.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.statistics.FeedStatisticsActivity;
import com.petkit.android.activities.statistics.contract.FeedStatisticsContract;
import com.petkit.android.activities.statistics.model.FeedStatisticsModel;
import com.petkit.android.activities.statistics.model.FeedStatisticsModel_Factory;
import com.petkit.android.activities.statistics.module.FeedStatisticsModule;
import com.petkit.android.activities.statistics.module.FeedStatisticsModule_ProvideFeedStatisticsModelFactory;
import com.petkit.android.activities.statistics.module.FeedStatisticsModule_ProvideFeedStatisticsViewFactory;
import com.petkit.android.activities.statistics.presenter.FeedStatisticsPresenter;
import com.petkit.android.activities.statistics.presenter.FeedStatisticsPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerFeedStatisticsComponent implements FeedStatisticsComponent {
    public Provider<Application> applicationProvider;
    public Provider<FeedStatisticsModel> feedStatisticsModelProvider;
    public Provider<FeedStatisticsPresenter> feedStatisticsPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<FeedStatisticsContract.Model> provideFeedStatisticsModelProvider;
    public Provider<FeedStatisticsContract.View> provideFeedStatisticsViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerFeedStatisticsComponent(FeedStatisticsModule feedStatisticsModule, AppComponent appComponent) {
        initialize(feedStatisticsModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(FeedStatisticsModule feedStatisticsModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<FeedStatisticsModel> provider = DoubleCheck.provider(FeedStatisticsModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.feedStatisticsModelProvider = provider;
        this.provideFeedStatisticsModelProvider = DoubleCheck.provider(FeedStatisticsModule_ProvideFeedStatisticsModelFactory.create(feedStatisticsModule, provider));
        this.provideFeedStatisticsViewProvider = DoubleCheck.provider(FeedStatisticsModule_ProvideFeedStatisticsViewFactory.create(feedStatisticsModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.feedStatisticsPresenterProvider = DoubleCheck.provider(FeedStatisticsPresenter_Factory.create(this.provideFeedStatisticsModelProvider, this.provideFeedStatisticsViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.statistics.component.FeedStatisticsComponent
    public void inject(FeedStatisticsActivity feedStatisticsActivity) {
        injectFeedStatisticsActivity(feedStatisticsActivity);
    }

    @CanIgnoreReturnValue
    public final FeedStatisticsActivity injectFeedStatisticsActivity(FeedStatisticsActivity feedStatisticsActivity) {
        BaseActivity_MembersInjector.injectMPresenter(feedStatisticsActivity, this.feedStatisticsPresenterProvider.get());
        return feedStatisticsActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public FeedStatisticsModule feedStatisticsModule;

        public Builder() {
        }

        public Builder feedStatisticsModule(FeedStatisticsModule feedStatisticsModule) {
            this.feedStatisticsModule = (FeedStatisticsModule) Preconditions.checkNotNull(feedStatisticsModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public FeedStatisticsComponent build() {
            Preconditions.checkBuilderRequirement(this.feedStatisticsModule, FeedStatisticsModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerFeedStatisticsComponent(this.feedStatisticsModule, this.appComponent);
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

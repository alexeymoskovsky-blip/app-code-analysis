package com.petkit.android.activities.device.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.device.EditDeviceFeedDifferentPlans2Activity;
import com.petkit.android.activities.device.contract.EditDeviceFeedDifferentPlans2Contract;
import com.petkit.android.activities.device.model.EditDeviceFeedDifferentPlans2Model;
import com.petkit.android.activities.device.model.EditDeviceFeedDifferentPlans2Model_Factory;
import com.petkit.android.activities.device.module.EditDeviceFeedDifferentPlans2Module;
import com.petkit.android.activities.device.module.EditDeviceFeedDifferentPlans2Module_ProvideEditDeviceFeedDifferentPlans2ModelFactory;
import com.petkit.android.activities.device.module.EditDeviceFeedDifferentPlans2Module_ProvideEditDeviceFeedDifferentPlans2ViewFactory;
import com.petkit.android.activities.device.presenter.EditDeviceFeedDifferentPlans2Presenter;
import com.petkit.android.activities.device.presenter.EditDeviceFeedDifferentPlans2Presenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes3.dex */
public final class DaggerEditDeviceFeedDifferentPlans2Component implements EditDeviceFeedDifferentPlans2Component {
    public Provider<Application> applicationProvider;
    public Provider<EditDeviceFeedDifferentPlans2Model> editDeviceFeedDifferentPlans2ModelProvider;
    public Provider<EditDeviceFeedDifferentPlans2Presenter> editDeviceFeedDifferentPlans2PresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<EditDeviceFeedDifferentPlans2Contract.Model> provideEditDeviceFeedDifferentPlans2ModelProvider;
    public Provider<EditDeviceFeedDifferentPlans2Contract.View> provideEditDeviceFeedDifferentPlans2ViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerEditDeviceFeedDifferentPlans2Component(EditDeviceFeedDifferentPlans2Module editDeviceFeedDifferentPlans2Module, AppComponent appComponent) {
        initialize(editDeviceFeedDifferentPlans2Module, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(EditDeviceFeedDifferentPlans2Module editDeviceFeedDifferentPlans2Module, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<EditDeviceFeedDifferentPlans2Model> provider = DoubleCheck.provider(EditDeviceFeedDifferentPlans2Model_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.editDeviceFeedDifferentPlans2ModelProvider = provider;
        this.provideEditDeviceFeedDifferentPlans2ModelProvider = DoubleCheck.provider(EditDeviceFeedDifferentPlans2Module_ProvideEditDeviceFeedDifferentPlans2ModelFactory.create(editDeviceFeedDifferentPlans2Module, provider));
        this.provideEditDeviceFeedDifferentPlans2ViewProvider = DoubleCheck.provider(EditDeviceFeedDifferentPlans2Module_ProvideEditDeviceFeedDifferentPlans2ViewFactory.create(editDeviceFeedDifferentPlans2Module));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.editDeviceFeedDifferentPlans2PresenterProvider = DoubleCheck.provider(EditDeviceFeedDifferentPlans2Presenter_Factory.create(this.provideEditDeviceFeedDifferentPlans2ModelProvider, this.provideEditDeviceFeedDifferentPlans2ViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.device.component.EditDeviceFeedDifferentPlans2Component
    public void inject(EditDeviceFeedDifferentPlans2Activity editDeviceFeedDifferentPlans2Activity) {
        injectEditDeviceFeedDifferentPlans2Activity(editDeviceFeedDifferentPlans2Activity);
    }

    @CanIgnoreReturnValue
    public final EditDeviceFeedDifferentPlans2Activity injectEditDeviceFeedDifferentPlans2Activity(EditDeviceFeedDifferentPlans2Activity editDeviceFeedDifferentPlans2Activity) {
        BaseActivity_MembersInjector.injectMPresenter(editDeviceFeedDifferentPlans2Activity, this.editDeviceFeedDifferentPlans2PresenterProvider.get());
        return editDeviceFeedDifferentPlans2Activity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public EditDeviceFeedDifferentPlans2Module editDeviceFeedDifferentPlans2Module;

        public Builder() {
        }

        public Builder editDeviceFeedDifferentPlans2Module(EditDeviceFeedDifferentPlans2Module editDeviceFeedDifferentPlans2Module) {
            this.editDeviceFeedDifferentPlans2Module = (EditDeviceFeedDifferentPlans2Module) Preconditions.checkNotNull(editDeviceFeedDifferentPlans2Module);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public EditDeviceFeedDifferentPlans2Component build() {
            Preconditions.checkBuilderRequirement(this.editDeviceFeedDifferentPlans2Module, EditDeviceFeedDifferentPlans2Module.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerEditDeviceFeedDifferentPlans2Component(this.editDeviceFeedDifferentPlans2Module, this.appComponent);
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

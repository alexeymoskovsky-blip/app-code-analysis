package com.petkit.android.activities.home.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.home.TodoActivity;
import com.petkit.android.activities.home.contract.TodoContract;
import com.petkit.android.activities.home.model.TodoModel;
import com.petkit.android.activities.home.model.TodoModel_Factory;
import com.petkit.android.activities.home.module.TodoModule;
import com.petkit.android.activities.home.module.TodoModule_ProvideTodoModelFactory;
import com.petkit.android.activities.home.module.TodoModule_ProvideTodoViewFactory;
import com.petkit.android.activities.home.presenter.TodoPresenter;
import com.petkit.android.activities.home.presenter.TodoPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerTodoComponent implements TodoComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<TodoContract.Model> provideTodoModelProvider;
    public Provider<TodoContract.View> provideTodoViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<TodoModel> todoModelProvider;
    public Provider<TodoPresenter> todoPresenterProvider;

    public DaggerTodoComponent(TodoModule todoModule, AppComponent appComponent) {
        initialize(todoModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(TodoModule todoModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<TodoModel> provider = DoubleCheck.provider(TodoModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.todoModelProvider = provider;
        this.provideTodoModelProvider = DoubleCheck.provider(TodoModule_ProvideTodoModelFactory.create(todoModule, provider));
        this.provideTodoViewProvider = DoubleCheck.provider(TodoModule_ProvideTodoViewFactory.create(todoModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.todoPresenterProvider = DoubleCheck.provider(TodoPresenter_Factory.create(this.provideTodoModelProvider, this.provideTodoViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.home.component.TodoComponent
    public void inject(TodoActivity todoActivity) {
        injectTodoActivity(todoActivity);
    }

    @CanIgnoreReturnValue
    public final TodoActivity injectTodoActivity(TodoActivity todoActivity) {
        BaseActivity_MembersInjector.injectMPresenter(todoActivity, this.todoPresenterProvider.get());
        return todoActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public TodoModule todoModule;

        public Builder() {
        }

        public Builder todoModule(TodoModule todoModule) {
            this.todoModule = (TodoModule) Preconditions.checkNotNull(todoModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public TodoComponent build() {
            Preconditions.checkBuilderRequirement(this.todoModule, TodoModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerTodoComponent(this.todoModule, this.appComponent);
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

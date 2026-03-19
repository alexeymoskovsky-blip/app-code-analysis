package com.petkit.android.activities.petkitBleDevice.w7h.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity;
import com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract;
import com.petkit.android.activities.petkitBleDevice.w7h.module.W7hHomeModule;
import dagger.BindsInstance;
import dagger.Component;

/* JADX INFO: loaded from: classes5.dex */
@Component(dependencies = {AppComponent.class}, modules = {W7hHomeModule.class})
@ActivityScope
public interface W7hHomeComponent {

    @Component.Builder
    public interface Builder {
        Builder appComponent(AppComponent appComponent);

        W7hHomeComponent build();

        @BindsInstance
        Builder view(W7hHomeContract.View view);
    }

    void inject(W7hHomeActivity w7hHomeActivity);
}

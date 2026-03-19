package com.petkit.android.activities.petkitBleDevice.t6.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract;
import com.petkit.android.activities.petkitBleDevice.t6.module.T5HomeModule;
import dagger.BindsInstance;
import dagger.Component;

/* JADX INFO: loaded from: classes5.dex */
@Component(dependencies = {AppComponent.class}, modules = {T5HomeModule.class})
@ActivityScope
public interface T5HomeComponent {

    @Component.Builder
    public interface Builder {
        Builder appComponent(AppComponent appComponent);

        T5HomeComponent build();

        @BindsInstance
        Builder view(T5HomeContract.View view);
    }

    void inject(T5HomeActivity t5HomeActivity);
}

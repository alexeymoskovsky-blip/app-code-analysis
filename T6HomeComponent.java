package com.petkit.android.activities.petkitBleDevice.t6.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract;
import com.petkit.android.activities.petkitBleDevice.t6.module.T6HomeModule;
import dagger.BindsInstance;
import dagger.Component;

/* JADX INFO: loaded from: classes5.dex */
@Component(dependencies = {AppComponent.class}, modules = {T6HomeModule.class})
@ActivityScope
public interface T6HomeComponent {

    @Component.Builder
    public interface Builder {
        Builder appComponent(AppComponent appComponent);

        T6HomeComponent build();

        @BindsInstance
        Builder view(T6HomeContract.View view);
    }

    void inject(T6HomeActivity t6HomeActivity);
}

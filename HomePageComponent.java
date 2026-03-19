package com.petkit.android.activities.home.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.petkit.android.activities.home.HomePageFragment;
import com.petkit.android.activities.home.module.HomePageModule;
import dagger.Component;

/* JADX INFO: loaded from: classes4.dex */
@Component(dependencies = {AppComponent.class}, modules = {HomePageModule.class})
@ActivityScope
public interface HomePageComponent {
    void inject(HomePageFragment homePageFragment);
}

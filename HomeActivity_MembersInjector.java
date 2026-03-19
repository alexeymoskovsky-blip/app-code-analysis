package com.petkit.android.activities.home;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.petkit.android.activities.home.presenter.HomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes4.dex */
public final class HomeActivity_MembersInjector implements MembersInjector<HomeActivity> {
    public final Provider<HomePresenter> mPresenterProvider;

    public HomeActivity_MembersInjector(Provider<HomePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<HomeActivity> create(Provider<HomePresenter> provider) {
        return new HomeActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(HomeActivity homeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(homeActivity, this.mPresenterProvider.get());
    }
}

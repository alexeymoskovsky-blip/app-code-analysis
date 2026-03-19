package com.petkit.android.activities.home;

import com.jess.arms.base.BaseFragment_MembersInjector;
import com.petkit.android.activities.home.presenter.HomePagePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes4.dex */
public final class HomePageFragment_MembersInjector implements MembersInjector<HomePageFragment> {
    public final Provider<HomePagePresenter> mPresenterProvider;

    public HomePageFragment_MembersInjector(Provider<HomePagePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<HomePageFragment> create(Provider<HomePagePresenter> provider) {
        return new HomePageFragment_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(HomePageFragment homePageFragment) {
        BaseFragment_MembersInjector.injectMPresenter(homePageFragment, this.mPresenterProvider.get());
    }
}

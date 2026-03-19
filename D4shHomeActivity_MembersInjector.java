package com.petkit.android.activities.petkitBleDevice.d4sh;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shHomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes4.dex */
public final class D4shHomeActivity_MembersInjector implements MembersInjector<D4shHomeActivity> {
    public final Provider<D4shHomePresenter> mPresenterProvider;

    public D4shHomeActivity_MembersInjector(Provider<D4shHomePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<D4shHomeActivity> create(Provider<D4shHomePresenter> provider) {
        return new D4shHomeActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(D4shHomeActivity d4shHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4shHomeActivity, this.mPresenterProvider.get());
    }
}

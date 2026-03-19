package com.petkit.android.activities.petkitBleDevice.t4;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.petkit.android.activities.petkitBleDevice.t4.presenter.T4HomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes5.dex */
public final class T4HomeActivity_MembersInjector implements MembersInjector<T4HomeActivity> {
    public final Provider<T4HomePresenter> mPresenterProvider;

    public T4HomeActivity_MembersInjector(Provider<T4HomePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<T4HomeActivity> create(Provider<T4HomePresenter> provider) {
        return new T4HomeActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(T4HomeActivity t4HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t4HomeActivity, this.mPresenterProvider.get());
    }
}

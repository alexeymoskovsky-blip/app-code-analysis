package com.petkit.android.activities.petkitBleDevice.t6;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes5.dex */
public final class T6HomeActivity_MembersInjector implements MembersInjector<T6HomeActivity> {
    public final Provider<T6HomePresenter> mPresenterProvider;

    public T6HomeActivity_MembersInjector(Provider<T6HomePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<T6HomeActivity> create(Provider<T6HomePresenter> provider) {
        return new T6HomeActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(T6HomeActivity t6HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t6HomeActivity, this.mPresenterProvider.get());
    }
}

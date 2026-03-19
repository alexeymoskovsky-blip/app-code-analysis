package com.petkit.android.activities.petkitBleDevice.t7;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.petkit.android.activities.petkitBleDevice.t7.presenter.T7HomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes5.dex */
public final class T7HomeActivity_MembersInjector implements MembersInjector<T7HomeActivity> {
    public final Provider<T7HomePresenter> mPresenterProvider;

    public T7HomeActivity_MembersInjector(Provider<T7HomePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<T7HomeActivity> create(Provider<T7HomePresenter> provider) {
        return new T7HomeActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(T7HomeActivity t7HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t7HomeActivity, this.mPresenterProvider.get());
    }
}

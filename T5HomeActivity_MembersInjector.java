package com.petkit.android.activities.petkitBleDevice.t6;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes5.dex */
public final class T5HomeActivity_MembersInjector implements MembersInjector<T5HomeActivity> {
    public final Provider<T5HomePresenter> mPresenterProvider;

    public T5HomeActivity_MembersInjector(Provider<T5HomePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<T5HomeActivity> create(Provider<T5HomePresenter> provider) {
        return new T5HomeActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(T5HomeActivity t5HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t5HomeActivity, this.mPresenterProvider.get());
    }
}

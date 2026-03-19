package com.petkit.android.activities.petkitBleDevice.w7h;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.petkit.android.activities.petkitBleDevice.w7h.presenter.W7hHomePresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes5.dex */
public final class W7hHomeActivity_MembersInjector implements MembersInjector<W7hHomeActivity> {
    public final Provider<W7hHomePresenter> mPresenterProvider;

    public W7hHomeActivity_MembersInjector(Provider<W7hHomePresenter> provider) {
        this.mPresenterProvider = provider;
    }

    public static MembersInjector<W7hHomeActivity> create(Provider<W7hHomePresenter> provider) {
        return new W7hHomeActivity_MembersInjector(provider);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(W7hHomeActivity w7hHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(w7hHomeActivity, this.mPresenterProvider.get());
    }
}

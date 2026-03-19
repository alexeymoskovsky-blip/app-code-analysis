package com.petkit.android.app.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/* JADX INFO: loaded from: classes6.dex */
public class RxUtils {

    /* JADX INFO: renamed from: com.petkit.android.app.utils.RxUtils$1 */
    public class AnonymousClass1<T> implements ObservableTransformer<T, T> {
        public AnonymousClass1() {
        }

        /* JADX INFO: renamed from: com.petkit.android.app.utils.RxUtils$1$2 */
        public class AnonymousClass2 implements Consumer<Disposable> {
            public AnonymousClass2() {
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(@NonNull Disposable disposable) throws Exception {
                iView.showLoading();
            }
        }

        @Override // io.reactivex.ObservableTransformer
        public Observable<T> apply(Observable<T> observable) {
            return (Observable<T>) observable.subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.petkit.android.app.utils.RxUtils.1.2
                public AnonymousClass2() {
                }

                @Override // io.reactivex.functions.Consumer
                public void accept(@NonNull Disposable disposable) throws Exception {
                    iView.showLoading();
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).doAfterTerminate(new Action() { // from class: com.petkit.android.app.utils.RxUtils.1.1
                public C01111() {
                }

                @Override // io.reactivex.functions.Action
                public void run() {
                    iView.hideLoading();
                }
            }).compose(RxUtils.bindToLifecycle(iView));
        }

        /* JADX INFO: renamed from: com.petkit.android.app.utils.RxUtils$1$1 */
        public class C01111 implements Action {
            public C01111() {
            }

            @Override // io.reactivex.functions.Action
            public void run() {
                iView.hideLoading();
            }
        }
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(IView iView) {
        return new ObservableTransformer<T, T>() { // from class: com.petkit.android.app.utils.RxUtils.1
            public AnonymousClass1() {
            }

            /* JADX INFO: renamed from: com.petkit.android.app.utils.RxUtils$1$2 */
            public class AnonymousClass2 implements Consumer<Disposable> {
                public AnonymousClass2() {
                }

                @Override // io.reactivex.functions.Consumer
                public void accept(@NonNull Disposable disposable) throws Exception {
                    iView.showLoading();
                }
            }

            @Override // io.reactivex.ObservableTransformer
            public Observable<T> apply(Observable<T> observable) {
                return (Observable<T>) observable.subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.petkit.android.app.utils.RxUtils.1.2
                    public AnonymousClass2() {
                    }

                    @Override // io.reactivex.functions.Consumer
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        iView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).doAfterTerminate(new Action() { // from class: com.petkit.android.app.utils.RxUtils.1.1
                    public C01111() {
                    }

                    @Override // io.reactivex.functions.Action
                    public void run() {
                        iView.hideLoading();
                    }
                }).compose(RxUtils.bindToLifecycle(iView));
            }

            /* JADX INFO: renamed from: com.petkit.android.app.utils.RxUtils$1$1 */
            public class C01111 implements Action {
                public C01111() {
                }

                @Override // io.reactivex.functions.Action
                public void run() {
                    iView.hideLoading();
                }
            }
        };
    }

    @Deprecated
    public static <T> LifecycleTransformer<T> bindToLifecycle(IView iView) {
        return RxLifecycleUtils.bindToLifecycle(iView);
    }
}

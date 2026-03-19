package com.jess.arms.integration;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import retrofit2.Retrofit;

/* JADX INFO: loaded from: classes3.dex */
public class RetrofitServiceProxyHandler implements InvocationHandler {
    public Retrofit mRetrofit;
    public Object mRetrofitService;
    public Class<?> mServiceClass;

    public RetrofitServiceProxyHandler(Retrofit retrofit, Class<?> cls) {
        this.mRetrofit = retrofit;
        this.mServiceClass = cls;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, final Method method, @Nullable final Object[] objArr) throws Throwable {
        if (method.getReturnType() == Observable.class) {
            return Observable.defer(new Callable() { // from class: com.jess.arms.integration.RetrofitServiceProxyHandler$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$invoke$0(method, objArr);
                }
            });
        }
        if (method.getReturnType() == Single.class) {
            return Single.defer(new Callable() { // from class: com.jess.arms.integration.RetrofitServiceProxyHandler$$ExternalSyntheticLambda1
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.lambda$invoke$1(method, objArr);
                }
            });
        }
        return method.invoke(getRetrofitService(), objArr);
    }

    public final /* synthetic */ ObservableSource lambda$invoke$0(Method method, Object[] objArr) throws Exception {
        return (Observable) method.invoke(getRetrofitService(), objArr);
    }

    public final /* synthetic */ SingleSource lambda$invoke$1(Method method, Object[] objArr) throws Exception {
        return (Single) method.invoke(getRetrofitService(), objArr);
    }

    public final Object getRetrofitService() {
        if (this.mRetrofitService == null) {
            this.mRetrofitService = this.mRetrofit.create(this.mServiceClass);
        }
        return this.mRetrofitService;
    }
}

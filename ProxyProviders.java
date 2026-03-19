package io.rx_cache2.internal;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.rx_cache2.EncryptKey;
import io.rx_cache2.Migration;
import io.rx_cache2.MigrationCache;
import io.rx_cache2.SchemeMigration;
import io.rx_cache2.internal.RxCache;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes7.dex */
public final class ProxyProviders implements InvocationHandler {
    public final ProcessorProviders processorProviders;
    public final ProxyTranslator proxyTranslator = new ProxyTranslator();

    public ProxyProviders(RxCache.Builder builder, Class<?> cls) {
        this.processorProviders = DaggerRxCacheComponent.builder().rxCacheModule(new RxCacheModule(builder.getCacheDirectory(), Boolean.valueOf(builder.useExpiredDataIfLoaderNotAvailable()), builder.getMaxMBPersistenceCache(), getEncryptKey(cls), getMigrations(cls), builder.getJolyglot())).build().providers();
    }

    public String getEncryptKey(Class<?> cls) {
        EncryptKey encryptKey = (EncryptKey) cls.getAnnotation(EncryptKey.class);
        if (encryptKey == null) {
            return null;
        }
        return encryptKey.value();
    }

    public List<MigrationCache> getMigrations(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Annotation annotation = cls.getAnnotation(SchemeMigration.class);
        if (annotation == null) {
            return arrayList;
        }
        for (Migration migration : ((SchemeMigration) annotation).value()) {
            arrayList.add(new MigrationCache(migration.version(), migration.evictClasses()));
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: io.rx_cache2.internal.ProxyProviders$1 */
    public class AnonymousClass1 implements Callable<ObservableSource<?>> {
        public final /* synthetic */ Object[] val$args;
        public final /* synthetic */ Method val$method;

        public AnonymousClass1(Method method, Object[] objArr) {
            method = method;
            objArr = objArr;
        }

        @Override // java.util.concurrent.Callable
        public ObservableSource<?> call() throws Exception {
            Observable observableProcess = ProxyProviders.this.processorProviders.process(ProxyProviders.this.proxyTranslator.processMethod(method, objArr));
            Class<?> returnType = method.getReturnType();
            if (returnType == Observable.class) {
                return Observable.just(observableProcess);
            }
            if (returnType == Single.class) {
                return Observable.just(Single.fromObservable(observableProcess));
            }
            if (returnType == Maybe.class) {
                return Observable.just(Maybe.fromSingle(Single.fromObservable(observableProcess)));
            }
            if (method.getReturnType() == Flowable.class) {
                return Observable.just(observableProcess.toFlowable(BackpressureStrategy.MISSING));
            }
            throw new RuntimeException(method.getName() + Locale.INVALID_RETURN_TYPE);
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        return Observable.defer(new Callable<ObservableSource<?>>() { // from class: io.rx_cache2.internal.ProxyProviders.1
            public final /* synthetic */ Object[] val$args;
            public final /* synthetic */ Method val$method;

            public AnonymousClass1(Method method2, Object[] objArr2) {
                method = method2;
                objArr = objArr2;
            }

            @Override // java.util.concurrent.Callable
            public ObservableSource<?> call() throws Exception {
                Observable observableProcess = ProxyProviders.this.processorProviders.process(ProxyProviders.this.proxyTranslator.processMethod(method, objArr));
                Class<?> returnType = method.getReturnType();
                if (returnType == Observable.class) {
                    return Observable.just(observableProcess);
                }
                if (returnType == Single.class) {
                    return Observable.just(Single.fromObservable(observableProcess));
                }
                if (returnType == Maybe.class) {
                    return Observable.just(Maybe.fromSingle(Single.fromObservable(observableProcess)));
                }
                if (method.getReturnType() == Flowable.class) {
                    return Observable.just(observableProcess.toFlowable(BackpressureStrategy.MISSING));
                }
                throw new RuntimeException(method.getName() + Locale.INVALID_RETURN_TYPE);
            }
        }).blockingFirst();
    }

    public Observable<Void> evictAll() {
        return this.processorProviders.evictAll();
    }
}

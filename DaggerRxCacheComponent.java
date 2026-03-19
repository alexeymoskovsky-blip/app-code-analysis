package io.rx_cache2.internal;

import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import io.rx_cache2.MigrationCache;
import io.rx_cache2.internal.cache.EvictExpirableRecordsPersistence;
import io.rx_cache2.internal.cache.EvictExpirableRecordsPersistence_Factory;
import io.rx_cache2.internal.cache.EvictExpiredRecordsPersistence;
import io.rx_cache2.internal.cache.EvictExpiredRecordsPersistence_Factory;
import io.rx_cache2.internal.cache.EvictRecord;
import io.rx_cache2.internal.cache.EvictRecord_Factory;
import io.rx_cache2.internal.cache.GetDeepCopy;
import io.rx_cache2.internal.cache.GetDeepCopy_Factory;
import io.rx_cache2.internal.cache.HasRecordExpired_Factory;
import io.rx_cache2.internal.cache.RetrieveRecord;
import io.rx_cache2.internal.cache.RetrieveRecord_Factory;
import io.rx_cache2.internal.cache.SaveRecord;
import io.rx_cache2.internal.cache.SaveRecord_Factory;
import io.rx_cache2.internal.cache.TwoLayersCache;
import io.rx_cache2.internal.cache.TwoLayersCache_Factory;
import io.rx_cache2.internal.encrypt.Encryptor;
import io.rx_cache2.internal.encrypt.FileEncryptor;
import io.rx_cache2.internal.encrypt.FileEncryptor_Factory;
import io.rx_cache2.internal.migration.DoMigrations;
import io.rx_cache2.internal.migration.DoMigrations_Factory;
import io.victoralbertos.jolyglot.JolyglotGenerics;
import java.io.File;
import java.util.List;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes7.dex */
public final class DaggerRxCacheComponent implements RxCacheComponent {
    public static final /* synthetic */ boolean $assertionsDisabled = false;
    public Provider<Disk> diskProvider;
    public Provider<DoMigrations> doMigrationsProvider;
    public Provider<EvictExpirableRecordsPersistence> evictExpirableRecordsPersistenceProvider;
    public Provider<EvictExpiredRecordsPersistence> evictExpiredRecordsPersistenceProvider;
    public Provider<EvictRecord> evictRecordProvider;
    public Provider<FileEncryptor> fileEncryptorProvider;
    public Provider<GetDeepCopy> getDeepCopyProvider;
    public Provider<Integer> maxMbPersistenceCacheProvider;
    public Provider<ProcessorProvidersBehaviour> processorProvidersBehaviourProvider;
    public Provider<File> provideCacheDirectoryProvider;
    public Provider<String> provideEncryptKeyProvider;
    public Provider<Encryptor> provideEncryptorProvider;
    public Provider<JolyglotGenerics> provideJolyglotProvider;
    public Provider<Memory> provideMemoryProvider;
    public Provider<List<MigrationCache>> provideMigrationsProvider;
    public Provider<Persistence> providePersistenceProvider;
    public Provider<ProcessorProviders> provideProcessorProvidersProvider;
    public Provider<RetrieveRecord> retrieveRecordProvider;
    public Provider<SaveRecord> saveRecordProvider;
    public Provider<TwoLayersCache> twoLayersCacheProvider;
    public Provider<Boolean> useExpiredDataIfLoaderNotAvailableProvider;

    public DaggerRxCacheComponent(Builder builder) {
        initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(Builder builder) {
        this.provideMemoryProvider = DoubleCheck.provider(RxCacheModule_ProvideMemoryFactory.create(builder.rxCacheModule));
        this.provideCacheDirectoryProvider = DoubleCheck.provider(RxCacheModule_ProvideCacheDirectoryFactory.create(builder.rxCacheModule));
        Provider<Encryptor> provider = DoubleCheck.provider(RxCacheModule_ProvideEncryptorFactory.create(builder.rxCacheModule));
        this.provideEncryptorProvider = provider;
        this.fileEncryptorProvider = FileEncryptor_Factory.create(provider);
        Provider<JolyglotGenerics> provider2 = DoubleCheck.provider(RxCacheModule_ProvideJolyglotFactory.create(builder.rxCacheModule));
        this.provideJolyglotProvider = provider2;
        this.diskProvider = Disk_Factory.create(this.provideCacheDirectoryProvider, this.fileEncryptorProvider, provider2);
        this.providePersistenceProvider = DoubleCheck.provider(RxCacheModule_ProvidePersistenceFactory.create(builder.rxCacheModule, this.diskProvider));
        this.evictRecordProvider = EvictRecord_Factory.create(MembersInjectors.noOp(), this.provideMemoryProvider, this.providePersistenceProvider);
        this.provideEncryptKeyProvider = DoubleCheck.provider(RxCacheModule_ProvideEncryptKeyFactory.create(builder.rxCacheModule));
        this.retrieveRecordProvider = RetrieveRecord_Factory.create(MembersInjectors.noOp(), this.provideMemoryProvider, this.providePersistenceProvider, this.evictRecordProvider, HasRecordExpired_Factory.create(), this.provideEncryptKeyProvider);
        this.maxMbPersistenceCacheProvider = DoubleCheck.provider(RxCacheModule_MaxMbPersistenceCacheFactory.create(builder.rxCacheModule));
        this.evictExpirableRecordsPersistenceProvider = DoubleCheck.provider(EvictExpirableRecordsPersistence_Factory.create(MembersInjectors.noOp(), this.provideMemoryProvider, this.providePersistenceProvider, this.maxMbPersistenceCacheProvider, this.provideEncryptKeyProvider));
        Factory<SaveRecord> factoryCreate = SaveRecord_Factory.create(MembersInjectors.noOp(), this.provideMemoryProvider, this.providePersistenceProvider, this.maxMbPersistenceCacheProvider, this.evictExpirableRecordsPersistenceProvider, this.provideEncryptKeyProvider);
        this.saveRecordProvider = factoryCreate;
        this.twoLayersCacheProvider = DoubleCheck.provider(TwoLayersCache_Factory.create(this.evictRecordProvider, this.retrieveRecordProvider, factoryCreate));
        this.useExpiredDataIfLoaderNotAvailableProvider = DoubleCheck.provider(RxCacheModule_UseExpiredDataIfLoaderNotAvailableFactory.create(builder.rxCacheModule));
        this.evictExpiredRecordsPersistenceProvider = DoubleCheck.provider(EvictExpiredRecordsPersistence_Factory.create(MembersInjectors.noOp(), this.provideMemoryProvider, this.providePersistenceProvider, HasRecordExpired_Factory.create(), this.provideEncryptKeyProvider));
        this.getDeepCopyProvider = GetDeepCopy_Factory.create(MembersInjectors.noOp(), this.provideMemoryProvider, this.providePersistenceProvider, this.provideJolyglotProvider);
        Provider<List<MigrationCache>> provider3 = DoubleCheck.provider(RxCacheModule_ProvideMigrationsFactory.create(builder.rxCacheModule));
        this.provideMigrationsProvider = provider3;
        Factory<DoMigrations> factoryCreate2 = DoMigrations_Factory.create(this.providePersistenceProvider, provider3, this.provideEncryptKeyProvider);
        this.doMigrationsProvider = factoryCreate2;
        this.processorProvidersBehaviourProvider = ProcessorProvidersBehaviour_Factory.create(this.twoLayersCacheProvider, this.useExpiredDataIfLoaderNotAvailableProvider, this.evictExpiredRecordsPersistenceProvider, this.getDeepCopyProvider, factoryCreate2);
        this.provideProcessorProvidersProvider = RxCacheModule_ProvideProcessorProvidersFactory.create(builder.rxCacheModule, this.processorProvidersBehaviourProvider);
    }

    @Override // io.rx_cache2.internal.RxCacheComponent
    public ProcessorProviders providers() {
        return this.provideProcessorProvidersProvider.get();
    }

    public static final class Builder {
        public RxCacheModule rxCacheModule;

        public Builder() {
        }

        public RxCacheComponent build() {
            if (this.rxCacheModule == null) {
                throw new IllegalStateException(RxCacheModule.class.getCanonicalName() + " must be set");
            }
            return new DaggerRxCacheComponent(this);
        }

        public Builder rxCacheModule(RxCacheModule rxCacheModule) {
            this.rxCacheModule = (RxCacheModule) Preconditions.checkNotNull(rxCacheModule);
            return this;
        }
    }
}

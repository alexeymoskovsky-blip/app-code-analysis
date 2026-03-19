package com.apollographql.apollo.internal;

import com.apollographql.apollo.api.GraphqlFragment;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ScalarTypeAdapters;
import com.apollographql.apollo.api.internal.ApolloLogger;
import com.apollographql.apollo.api.internal.ResponseFieldMapper;
import com.apollographql.apollo.api.internal.Utils;
import com.apollographql.apollo.cache.CacheHeaders;
import com.apollographql.apollo.cache.normalized.ApolloStore;
import com.apollographql.apollo.cache.normalized.ApolloStoreOperation;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCache;
import com.apollographql.apollo.cache.normalized.OptimisticNormalizedCache;
import com.apollographql.apollo.cache.normalized.Record;
import com.apollographql.apollo.cache.normalized.internal.CacheFieldValueResolver;
import com.apollographql.apollo.cache.normalized.internal.CacheKeyBuilder;
import com.apollographql.apollo.cache.normalized.internal.ReadableStore;
import com.apollographql.apollo.cache.normalized.internal.RealCacheKeyBuilder;
import com.apollographql.apollo.cache.normalized.internal.ResponseNormalizer;
import com.apollographql.apollo.cache.normalized.internal.Transaction;
import com.apollographql.apollo.cache.normalized.internal.WriteableStore;
import com.apollographql.apollo.internal.response.RealResponseReader;
import com.apollographql.apollo.internal.response.RealResponseWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public final class RealApolloStore implements ApolloStore, ReadableStore, WriteableStore {
    public final CacheKeyBuilder cacheKeyBuilder;
    public final CacheKeyResolver cacheKeyResolver;
    public final Executor dispatcher;
    public final ReadWriteLock lock;
    public final ApolloLogger logger;
    public final OptimisticNormalizedCache optimisticCache;
    public final ScalarTypeAdapters scalarTypeAdapters;
    public final Set<ApolloStore.RecordChangeSubscriber> subscribers;

    public RealApolloStore(@NotNull NormalizedCache normalizedCache, @NotNull CacheKeyResolver cacheKeyResolver, @NotNull ScalarTypeAdapters scalarTypeAdapters, @NotNull Executor executor, @NotNull ApolloLogger apolloLogger) {
        Utils.checkNotNull(normalizedCache, "cacheStore == null");
        this.optimisticCache = (OptimisticNormalizedCache) new OptimisticNormalizedCache().chain(normalizedCache);
        this.cacheKeyResolver = (CacheKeyResolver) Utils.checkNotNull(cacheKeyResolver, "cacheKeyResolver == null");
        this.scalarTypeAdapters = (ScalarTypeAdapters) Utils.checkNotNull(scalarTypeAdapters, "scalarTypeAdapters == null");
        this.dispatcher = (Executor) Utils.checkNotNull(executor, "dispatcher == null");
        this.logger = (ApolloLogger) Utils.checkNotNull(apolloLogger, "logger == null");
        this.lock = new ReentrantReadWriteLock();
        this.subscribers = Collections.newSetFromMap(new WeakHashMap());
        this.cacheKeyBuilder = new RealCacheKeyBuilder();
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public ResponseNormalizer<Map<String, Object>> networkResponseNormalizer() {
        return new ResponseNormalizer<Map<String, Object>>() { // from class: com.apollographql.apollo.internal.RealApolloStore.1
            @Override // com.apollographql.apollo.cache.normalized.internal.ResponseNormalizer
            @NotNull
            public CacheKey resolveCacheKey(@NotNull ResponseField responseField, @NotNull Map<String, Object> map) {
                return RealApolloStore.this.cacheKeyResolver.fromFieldRecordSet(responseField, map);
            }

            @Override // com.apollographql.apollo.cache.normalized.internal.ResponseNormalizer
            @NotNull
            public CacheKeyBuilder cacheKeyBuilder() {
                return RealApolloStore.this.cacheKeyBuilder;
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public ResponseNormalizer<Record> cacheResponseNormalizer() {
        return new ResponseNormalizer<Record>() { // from class: com.apollographql.apollo.internal.RealApolloStore.2
            @Override // com.apollographql.apollo.cache.normalized.internal.ResponseNormalizer
            @NotNull
            public CacheKey resolveCacheKey(@NotNull ResponseField responseField, @NotNull Record record) {
                return new CacheKey(record.key());
            }

            @Override // com.apollographql.apollo.cache.normalized.internal.ResponseNormalizer
            @NotNull
            public CacheKeyBuilder cacheKeyBuilder() {
                return RealApolloStore.this.cacheKeyBuilder;
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public synchronized void subscribe(ApolloStore.RecordChangeSubscriber recordChangeSubscriber) {
        this.subscribers.add(recordChangeSubscriber);
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public synchronized void unsubscribe(ApolloStore.RecordChangeSubscriber recordChangeSubscriber) {
        this.subscribers.remove(recordChangeSubscriber);
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public void publish(@NotNull Set<String> set) {
        LinkedHashSet linkedHashSet;
        Utils.checkNotNull(set, "changedKeys == null");
        if (set.isEmpty()) {
            return;
        }
        synchronized (this) {
            linkedHashSet = new LinkedHashSet(this.subscribers);
        }
        Iterator it = linkedHashSet.iterator();
        RuntimeException runtimeException = null;
        while (it.hasNext()) {
            try {
                ((ApolloStore.RecordChangeSubscriber) it.next()).onCacheRecordsChanged(set);
            } catch (RuntimeException e) {
                if (runtimeException == null) {
                    runtimeException = e;
                }
            }
        }
        if (runtimeException != null) {
            throw runtimeException;
        }
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Boolean> clearAll() {
        return new ApolloStoreOperation<Boolean>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Boolean perform() {
                return (Boolean) RealApolloStore.this.writeTransaction(new Transaction<WriteableStore, Boolean>() { // from class: com.apollographql.apollo.internal.RealApolloStore.3.1
                    @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
                    public Boolean execute(WriteableStore writeableStore) {
                        RealApolloStore.this.optimisticCache.clearAll();
                        return Boolean.TRUE;
                    }
                });
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Boolean> remove(@NotNull CacheKey cacheKey) {
        return remove(cacheKey, false);
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Boolean> remove(@NotNull final CacheKey cacheKey, final boolean z) {
        Utils.checkNotNull(cacheKey, "cacheKey == null");
        return new ApolloStoreOperation<Boolean>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Boolean perform() {
                return (Boolean) RealApolloStore.this.writeTransaction(new Transaction<WriteableStore, Boolean>() { // from class: com.apollographql.apollo.internal.RealApolloStore.4.1
                    @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
                    public Boolean execute(WriteableStore writeableStore) {
                        AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                        return Boolean.valueOf(RealApolloStore.this.optimisticCache.remove(cacheKey, z));
                    }
                });
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Integer> remove(@NotNull final List<CacheKey> list) {
        Utils.checkNotNull(list, "cacheKey == null");
        return new ApolloStoreOperation<Integer>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Integer perform() {
                return (Integer) RealApolloStore.this.writeTransaction(new Transaction<WriteableStore, Integer>() { // from class: com.apollographql.apollo.internal.RealApolloStore.5.1
                    @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
                    public Integer execute(WriteableStore writeableStore) {
                        Iterator it = list.iterator();
                        int i = 0;
                        while (it.hasNext()) {
                            if (RealApolloStore.this.optimisticCache.remove((CacheKey) it.next())) {
                                i++;
                            }
                        }
                        return Integer.valueOf(i);
                    }
                });
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public <R> R readTransaction(Transaction<ReadableStore, R> transaction) {
        this.lock.readLock().lock();
        try {
            return transaction.execute(this);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public <R> R writeTransaction(Transaction<WriteableStore, R> transaction) {
        this.lock.writeLock().lock();
        try {
            return transaction.execute(this);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public NormalizedCache normalizedCache() {
        return this.optimisticCache;
    }

    @Override // com.apollographql.apollo.cache.normalized.internal.ReadableStore
    @Nullable
    public Record read(@NotNull String str, @NotNull CacheHeaders cacheHeaders) {
        return this.optimisticCache.loadRecord((String) Utils.checkNotNull(str, "key == null"), cacheHeaders);
    }

    @Override // com.apollographql.apollo.cache.normalized.internal.ReadableStore
    @NotNull
    public Collection<Record> read(@NotNull Collection<String> collection, @NotNull CacheHeaders cacheHeaders) {
        return this.optimisticCache.loadRecords((Collection) Utils.checkNotNull(collection, "keys == null"), cacheHeaders);
    }

    @Override // com.apollographql.apollo.cache.normalized.internal.WriteableStore
    @NotNull
    public Set<String> merge(@NotNull Collection<Record> collection, @NotNull CacheHeaders cacheHeaders) {
        return this.optimisticCache.merge((Collection<Record>) Utils.checkNotNull(collection, "recordSet == null"), cacheHeaders);
    }

    @Override // com.apollographql.apollo.cache.normalized.internal.WriteableStore
    public Set<String> merge(@NotNull Record record, @NotNull CacheHeaders cacheHeaders) {
        return this.optimisticCache.merge((Record) Utils.checkNotNull(record, "record == null"), cacheHeaders);
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    public CacheKeyResolver cacheKeyResolver() {
        return this.cacheKeyResolver;
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public <D extends Operation.Data, T, V extends Operation.Variables> ApolloStoreOperation<T> read(@NotNull final Operation<D, T, V> operation) {
        Utils.checkNotNull(operation, "operation == null");
        return new ApolloStoreOperation<T>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.6
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public T perform() {
                return (T) RealApolloStore.this.doRead(operation);
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public <D extends Operation.Data, T, V extends Operation.Variables> ApolloStoreOperation<Response<T>> read(@NotNull final Operation<D, T, V> operation, @NotNull final ResponseFieldMapper<D> responseFieldMapper, @NotNull final ResponseNormalizer<Record> responseNormalizer, @NotNull final CacheHeaders cacheHeaders) {
        Utils.checkNotNull(operation, "operation == null");
        Utils.checkNotNull(responseNormalizer, "responseNormalizer == null");
        return new ApolloStoreOperation<Response<T>>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.7
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Response<T> perform() {
                return RealApolloStore.this.doRead(operation, responseFieldMapper, responseNormalizer, cacheHeaders);
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public <F extends GraphqlFragment> ApolloStoreOperation<F> read(@NotNull final ResponseFieldMapper<F> responseFieldMapper, @NotNull final CacheKey cacheKey, @NotNull final Operation.Variables variables) {
        Utils.checkNotNull(responseFieldMapper, "responseFieldMapper == null");
        Utils.checkNotNull(cacheKey, "cacheKey == null");
        Utils.checkNotNull(variables, "variables == null");
        return (ApolloStoreOperation<F>) new ApolloStoreOperation<F>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.8
            /* JADX WARN: Incorrect return type in method signature: ()TF; */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public GraphqlFragment perform() {
                return RealApolloStore.this.doRead(responseFieldMapper, cacheKey, variables);
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public <D extends Operation.Data, T, V extends Operation.Variables> ApolloStoreOperation<Set<String>> write(@NotNull final Operation<D, T, V> operation, @NotNull final D d) {
        Utils.checkNotNull(operation, "operation == null");
        Utils.checkNotNull(d, "operationData == null");
        return new ApolloStoreOperation<Set<String>>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.9
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Set<String> perform() {
                return RealApolloStore.this.doWrite(operation, d, false, null);
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public <D extends Operation.Data, T, V extends Operation.Variables> ApolloStoreOperation<Boolean> writeAndPublish(@NotNull final Operation<D, T, V> operation, @NotNull final D d) {
        return new ApolloStoreOperation<Boolean>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.10
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Boolean perform() {
                RealApolloStore.this.publish(RealApolloStore.this.doWrite(operation, d, false, null));
                return Boolean.TRUE;
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Set<String>> write(@NotNull final GraphqlFragment graphqlFragment, @NotNull final CacheKey cacheKey, @NotNull final Operation.Variables variables) {
        Utils.checkNotNull(graphqlFragment, "fragment == null");
        Utils.checkNotNull(cacheKey, "cacheKey == null");
        Utils.checkNotNull(variables, "operation == null");
        if (cacheKey.equals(CacheKey.NO_KEY)) {
            throw new IllegalArgumentException("undefined cache key");
        }
        return new ApolloStoreOperation<Set<String>>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.11
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Set<String> perform() {
                return (Set) RealApolloStore.this.writeTransaction(new Transaction<WriteableStore, Set<String>>() { // from class: com.apollographql.apollo.internal.RealApolloStore.11.1
                    @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
                    public Set<String> execute(WriteableStore writeableStore) {
                        AnonymousClass11 anonymousClass11 = AnonymousClass11.this;
                        return RealApolloStore.this.doWrite(graphqlFragment, cacheKey, variables);
                    }
                });
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Boolean> writeAndPublish(@NotNull final GraphqlFragment graphqlFragment, @NotNull final CacheKey cacheKey, @NotNull final Operation.Variables variables) {
        return new ApolloStoreOperation<Boolean>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Boolean perform() {
                RealApolloStore.this.publish(RealApolloStore.this.doWrite(graphqlFragment, cacheKey, variables));
                return Boolean.TRUE;
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public <D extends Operation.Data, T, V extends Operation.Variables> ApolloStoreOperation<Set<String>> writeOptimisticUpdates(@NotNull final Operation<D, T, V> operation, @NotNull final D d, @NotNull final UUID uuid) {
        return new ApolloStoreOperation<Set<String>>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.13
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Set<String> perform() {
                return RealApolloStore.this.doWrite(operation, d, true, uuid);
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public <D extends Operation.Data, T, V extends Operation.Variables> ApolloStoreOperation<Boolean> writeOptimisticUpdatesAndPublish(@NotNull final Operation<D, T, V> operation, @NotNull final D d, @NotNull final UUID uuid) {
        return new ApolloStoreOperation<Boolean>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.14
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Boolean perform() {
                RealApolloStore.this.publish(RealApolloStore.this.doWrite(operation, d, true, uuid));
                return Boolean.TRUE;
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Set<String>> rollbackOptimisticUpdates(@NotNull final UUID uuid) {
        return new ApolloStoreOperation<Set<String>>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.15
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Set<String> perform() {
                return (Set) RealApolloStore.this.writeTransaction(new Transaction<WriteableStore, Set<String>>() { // from class: com.apollographql.apollo.internal.RealApolloStore.15.1
                    @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
                    public Set<String> execute(WriteableStore writeableStore) {
                        AnonymousClass15 anonymousClass15 = AnonymousClass15.this;
                        return RealApolloStore.this.optimisticCache.removeOptimisticUpdates(uuid);
                    }
                });
            }
        };
    }

    @Override // com.apollographql.apollo.cache.normalized.ApolloStore
    @NotNull
    public ApolloStoreOperation<Boolean> rollbackOptimisticUpdatesAndPublish(@NotNull final UUID uuid) {
        return new ApolloStoreOperation<Boolean>(this.dispatcher) { // from class: com.apollographql.apollo.internal.RealApolloStore.16
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.apollographql.apollo.cache.normalized.ApolloStoreOperation
            public Boolean perform() {
                RealApolloStore.this.publish((Set) RealApolloStore.this.writeTransaction(new Transaction<WriteableStore, Set<String>>() { // from class: com.apollographql.apollo.internal.RealApolloStore.16.1
                    @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
                    public Set<String> execute(WriteableStore writeableStore) {
                        AnonymousClass16 anonymousClass16 = AnonymousClass16.this;
                        return RealApolloStore.this.optimisticCache.removeOptimisticUpdates(uuid);
                    }
                }));
                return Boolean.TRUE;
            }
        };
    }

    public <D extends Operation.Data, T, V extends Operation.Variables> T doRead(final Operation<D, T, V> operation) {
        return (T) readTransaction(new Transaction<ReadableStore, T>() { // from class: com.apollographql.apollo.internal.RealApolloStore.17
            @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
            @Nullable
            public T execute(ReadableStore readableStore) {
                String strKey = CacheKeyResolver.rootKeyForOperation(operation).key();
                CacheHeaders cacheHeaders = CacheHeaders.NONE;
                Record record = readableStore.read(strKey, cacheHeaders);
                if (record == null) {
                    return null;
                }
                return (T) operation.wrapData((Operation.Data) operation.responseFieldMapper().map(new RealResponseReader(operation.variables(), record, new CacheFieldValueResolver(readableStore, operation.variables(), RealApolloStore.this.cacheKeyResolver(), cacheHeaders, RealApolloStore.this.cacheKeyBuilder), RealApolloStore.this.scalarTypeAdapters, ResponseNormalizer.NO_OP_NORMALIZER)));
            }
        });
    }

    public <D extends Operation.Data, T, V extends Operation.Variables> Response<T> doRead(final Operation<D, T, V> operation, final ResponseFieldMapper<D> responseFieldMapper, final ResponseNormalizer<Record> responseNormalizer, final CacheHeaders cacheHeaders) {
        return (Response) readTransaction(new Transaction<ReadableStore, Response<T>>() { // from class: com.apollographql.apollo.internal.RealApolloStore.18
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
            @NotNull
            public Response<T> execute(ReadableStore readableStore) {
                Record record = readableStore.read(CacheKeyResolver.rootKeyForOperation(operation).key(), cacheHeaders);
                if (record == null) {
                    return Response.builder(operation).fromCache(true).build();
                }
                RealResponseReader realResponseReader = new RealResponseReader(operation.variables(), record, new CacheFieldValueResolver(readableStore, operation.variables(), RealApolloStore.this.cacheKeyResolver(), cacheHeaders, RealApolloStore.this.cacheKeyBuilder), RealApolloStore.this.scalarTypeAdapters, responseNormalizer);
                try {
                    responseNormalizer.willResolveRootQuery(operation);
                    return Response.builder(operation).data(operation.wrapData((Operation.Data) responseFieldMapper.map(realResponseReader))).fromCache(true).dependentKeys(responseNormalizer.dependentKeys()).build();
                } catch (Exception e) {
                    RealApolloStore.this.logger.e(e, "Failed to read cache response", new Object[0]);
                    return Response.builder(operation).fromCache(true).build();
                }
            }
        });
    }

    public <F extends GraphqlFragment> F doRead(final ResponseFieldMapper<F> responseFieldMapper, final CacheKey cacheKey, final Operation.Variables variables) {
        return (F) readTransaction(new Transaction<ReadableStore, F>() { // from class: com.apollographql.apollo.internal.RealApolloStore.19
            /* JADX WARN: Incorrect return type in method signature: (Lcom/apollographql/apollo/cache/normalized/internal/ReadableStore;)TF; */
            @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
            @Nullable
            public GraphqlFragment execute(ReadableStore readableStore) {
                String strKey = cacheKey.key();
                CacheHeaders cacheHeaders = CacheHeaders.NONE;
                Record record = readableStore.read(strKey, cacheHeaders);
                if (record == null) {
                    return null;
                }
                return (GraphqlFragment) responseFieldMapper.map(new RealResponseReader(variables, record, new CacheFieldValueResolver(readableStore, variables, RealApolloStore.this.cacheKeyResolver(), cacheHeaders, RealApolloStore.this.cacheKeyBuilder), RealApolloStore.this.scalarTypeAdapters, ResponseNormalizer.NO_OP_NORMALIZER));
            }
        });
    }

    public <D extends Operation.Data, T, V extends Operation.Variables> Set<String> doWrite(final Operation<D, T, V> operation, final D d, final boolean z, final UUID uuid) {
        return (Set) writeTransaction(new Transaction<WriteableStore, Set<String>>() { // from class: com.apollographql.apollo.internal.RealApolloStore.20
            @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
            public Set<String> execute(WriteableStore writeableStore) {
                RealResponseWriter realResponseWriter = new RealResponseWriter(operation.variables(), RealApolloStore.this.scalarTypeAdapters);
                d.marshaller().marshal(realResponseWriter);
                ResponseNormalizer<Map<String, Object>> responseNormalizerNetworkResponseNormalizer = RealApolloStore.this.networkResponseNormalizer();
                responseNormalizerNetworkResponseNormalizer.willResolveRootQuery(operation);
                realResponseWriter.resolveFields(responseNormalizerNetworkResponseNormalizer);
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<Record> it = responseNormalizerNetworkResponseNormalizer.records().iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next().toBuilder().mutationId(uuid).build());
                    }
                    return RealApolloStore.this.optimisticCache.mergeOptimisticUpdates(arrayList);
                }
                return RealApolloStore.this.optimisticCache.merge(responseNormalizerNetworkResponseNormalizer.records(), CacheHeaders.NONE);
            }
        });
    }

    public Set<String> doWrite(final GraphqlFragment graphqlFragment, final CacheKey cacheKey, final Operation.Variables variables) {
        return (Set) writeTransaction(new Transaction<WriteableStore, Set<String>>() { // from class: com.apollographql.apollo.internal.RealApolloStore.21
            @Override // com.apollographql.apollo.cache.normalized.internal.Transaction
            public Set<String> execute(WriteableStore writeableStore) {
                RealResponseWriter realResponseWriter = new RealResponseWriter(variables, RealApolloStore.this.scalarTypeAdapters);
                graphqlFragment.marshaller().marshal(realResponseWriter);
                ResponseNormalizer<Map<String, Object>> responseNormalizerNetworkResponseNormalizer = RealApolloStore.this.networkResponseNormalizer();
                responseNormalizerNetworkResponseNormalizer.willResolveRecord(cacheKey);
                realResponseWriter.resolveFields(responseNormalizerNetworkResponseNormalizer);
                return RealApolloStore.this.merge(responseNormalizerNetworkResponseNormalizer.records(), CacheHeaders.NONE);
            }
        });
    }
}

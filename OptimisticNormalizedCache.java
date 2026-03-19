package com.apollographql.apollo.cache.normalized;

import com.apollographql.apollo.cache.CacheHeaders;
import com.apollographql.apollo.cache.normalized.Record;
import com.nytimes.android.external.cache.Cache;
import com.nytimes.android.external.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: OptimisticNormalizedCache.kt */
/* JADX INFO: loaded from: classes.dex */
public final class OptimisticNormalizedCache extends NormalizedCache {

    @NotNull
    public final Cache<String, RecordJournal> lruCache;

    public OptimisticNormalizedCache() {
        Cache cacheBuild = CacheBuilder.newBuilder().build();
        Intrinsics.checkExpressionValueIsNotNull(cacheBuild, "newBuilder().build<String, RecordJournal>()");
        this.lruCache = cacheBuild;
    }

    @Override // com.apollographql.apollo.cache.normalized.NormalizedCache
    @Nullable
    public Record loadRecord(@NotNull String key, @NotNull CacheHeaders cacheHeaders) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(cacheHeaders, "cacheHeaders");
        try {
            NormalizedCache nextCache = getNextCache();
            return mergeJournalRecord(nextCache == null ? null : nextCache.loadRecord(key, cacheHeaders), key);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.apollographql.apollo.cache.normalized.NormalizedCache
    @NotNull
    public Collection<Record> loadRecords(@NotNull Collection<String> keys, @NotNull CacheHeaders cacheHeaders) {
        Collection<Record> collectionLoadRecords;
        Intrinsics.checkParameterIsNotNull(keys, "keys");
        Intrinsics.checkParameterIsNotNull(cacheHeaders, "cacheHeaders");
        NormalizedCache nextCache = getNextCache();
        Map mapEmptyMap = null;
        if (nextCache != null && (collectionLoadRecords = nextCache.loadRecords(keys, cacheHeaders)) != null) {
            Collection<Record> collection = collectionLoadRecords;
            mapEmptyMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(collection, 10)), 16));
            for (Object obj : collection) {
                mapEmptyMap.put(((Record) obj).getKey(), obj);
            }
        }
        if (mapEmptyMap == null) {
            mapEmptyMap = MapsKt__MapsKt.emptyMap();
        }
        ArrayList arrayList = new ArrayList();
        for (String str : keys) {
            Record recordMergeJournalRecord = mergeJournalRecord((Record) mapEmptyMap.get(str), str);
            if (recordMergeJournalRecord != null) {
                arrayList.add(recordMergeJournalRecord);
            }
        }
        return arrayList;
    }

    @Override // com.apollographql.apollo.cache.normalized.NormalizedCache
    public void clearAll() {
        this.lruCache.invalidateAll();
        NormalizedCache nextCache = getNextCache();
        if (nextCache == null) {
            return;
        }
        nextCache.clearAll();
    }

    @Override // com.apollographql.apollo.cache.normalized.NormalizedCache
    public boolean remove(@NotNull CacheKey cacheKey, boolean z) {
        boolean z2;
        Intrinsics.checkParameterIsNotNull(cacheKey, "cacheKey");
        NormalizedCache nextCache = getNextCache();
        boolean zRemove = nextCache == null ? false : nextCache.remove(cacheKey, z);
        RecordJournal ifPresent = this.lruCache.getIfPresent(cacheKey.getKey());
        if (ifPresent == null) {
            return zRemove;
        }
        this.lruCache.invalidate(cacheKey.getKey());
        if (!z) {
            return true;
        }
        Iterator<CacheReference> it = ifPresent.getSnapshot().referencedFields().iterator();
        while (true) {
            while (it.hasNext()) {
                z2 = z2 && remove(new CacheKey(it.next().getKey()), true);
            }
            return z2;
        }
    }

    @NotNull
    public final Set<String> mergeOptimisticUpdates(@NotNull Collection<Record> recordSet) {
        Intrinsics.checkParameterIsNotNull(recordSet, "recordSet");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = recordSet.iterator();
        while (it.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll(arrayList, mergeOptimisticUpdate((Record) it.next()));
        }
        return CollectionsKt___CollectionsKt.toSet(arrayList);
    }

    @NotNull
    public final Set<String> mergeOptimisticUpdate(@NotNull Record record) {
        Intrinsics.checkParameterIsNotNull(record, "record");
        RecordJournal ifPresent = this.lruCache.getIfPresent(record.getKey());
        if (ifPresent == null) {
            this.lruCache.put(record.getKey(), new RecordJournal(record));
            return SetsKt__SetsJVMKt.setOf(record.getKey());
        }
        return ifPresent.commit(record);
    }

    @NotNull
    public final Set<String> removeOptimisticUpdates(@NotNull UUID mutationId) {
        Intrinsics.checkParameterIsNotNull(mutationId, "mutationId");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        ConcurrentMap<String, RecordJournal> concurrentMapAsMap = this.lruCache.asMap();
        Intrinsics.checkExpressionValueIsNotNull(concurrentMapAsMap, "lruCache.asMap()");
        for (Map.Entry<String, RecordJournal> entry : concurrentMapAsMap.entrySet()) {
            String cacheKey = entry.getKey();
            RecordJournal value = entry.getValue();
            linkedHashSet.addAll(value.revert(mutationId));
            if (value.getHistory().isEmpty()) {
                Intrinsics.checkExpressionValueIsNotNull(cacheKey, "cacheKey");
                linkedHashSet2.add(cacheKey);
            }
        }
        this.lruCache.invalidateAll(linkedHashSet2);
        return linkedHashSet;
    }

    @Override // com.apollographql.apollo.cache.normalized.NormalizedCache
    @NotNull
    public Set<String> performMerge(@NotNull Record apolloRecord, @Nullable Record record, @NotNull CacheHeaders cacheHeaders) {
        Intrinsics.checkParameterIsNotNull(apolloRecord, "apolloRecord");
        Intrinsics.checkParameterIsNotNull(cacheHeaders, "cacheHeaders");
        return SetsKt__SetsKt.emptySet();
    }

    @Override // com.apollographql.apollo.cache.normalized.NormalizedCache
    @NotNull
    public Map<KClass<?>, Map<String, Record>> dump() {
        Map mapCreateMapBuilder = MapsKt__MapsJVMKt.createMapBuilder();
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(OptimisticNormalizedCache.class);
        ConcurrentMap<String, RecordJournal> concurrentMapAsMap = this.lruCache.asMap();
        Intrinsics.checkExpressionValueIsNotNull(concurrentMapAsMap, "lruCache.asMap()");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(concurrentMapAsMap.size()));
        Iterator<T> it = concurrentMapAsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), ((RecordJournal) entry.getValue()).getSnapshot());
        }
        mapCreateMapBuilder.put(orCreateKotlinClass, linkedHashMap);
        NormalizedCache nextCache = getNextCache();
        Map<KClass<?>, Map<String, Record>> mapDump = nextCache == null ? null : nextCache.dump();
        if (mapDump == null) {
            mapDump = MapsKt__MapsKt.emptyMap();
        }
        mapCreateMapBuilder.putAll(mapDump);
        return MapsKt__MapsJVMKt.build(mapCreateMapBuilder);
    }

    public final Record mergeJournalRecord(Record record, String str) {
        Record recordBuild;
        RecordJournal ifPresent = this.lruCache.getIfPresent(str);
        if (ifPresent == null) {
            return record;
        }
        Record.Builder builder = record == null ? null : record.toBuilder();
        if (builder == null || (recordBuild = builder.build()) == null) {
            recordBuild = null;
        } else {
            recordBuild.mergeWith(ifPresent.getSnapshot());
        }
        return recordBuild == null ? ifPresent.getSnapshot().toBuilder().build() : recordBuild;
    }

    /* JADX INFO: compiled from: OptimisticNormalizedCache.kt */
    public static final class RecordJournal {

        @NotNull
        public final List<Record> history;

        @NotNull
        public Record snapshot;

        public RecordJournal(@NotNull Record mutationRecord) {
            Intrinsics.checkParameterIsNotNull(mutationRecord, "mutationRecord");
            this.snapshot = mutationRecord.toBuilder().build();
            this.history = CollectionsKt__CollectionsKt.mutableListOf(mutationRecord.toBuilder().build());
        }

        @NotNull
        public final Record getSnapshot() {
            return this.snapshot;
        }

        public final void setSnapshot(@NotNull Record record) {
            Intrinsics.checkParameterIsNotNull(record, "<set-?>");
            this.snapshot = record;
        }

        @NotNull
        public final List<Record> getHistory() {
            return this.history;
        }

        @NotNull
        public final Set<String> commit(@NotNull Record record) {
            Intrinsics.checkParameterIsNotNull(record, "record");
            List<Record> list = this.history;
            list.add(list.size(), record.toBuilder().build());
            return this.snapshot.mergeWith(record);
        }

        @NotNull
        public final Set<String> revert(@NotNull UUID mutationId) {
            Intrinsics.checkParameterIsNotNull(mutationId, "mutationId");
            Iterator<Record> it = this.history.iterator();
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                }
                if (Intrinsics.areEqual(mutationId, it.next().getMutationId())) {
                    break;
                }
                i++;
            }
            if (i == -1) {
                return SetsKt__SetsKt.emptySet();
            }
            Set setCreateSetBuilder = SetsKt__SetsJVMKt.createSetBuilder();
            setCreateSetBuilder.add(getHistory().remove(i).getKey());
            int i2 = i - 1;
            int iMax = Math.max(0, i2);
            int size = getHistory().size();
            if (iMax < size) {
                while (true) {
                    int i3 = iMax + 1;
                    Record record = getHistory().get(iMax);
                    if (iMax == Math.max(0, i2)) {
                        setSnapshot(record.toBuilder().build());
                    } else {
                        setCreateSetBuilder.addAll(getSnapshot().mergeWith(record));
                    }
                    if (i3 >= size) {
                        break;
                    }
                    iMax = i3;
                }
            }
            return SetsKt__SetsJVMKt.build(setCreateSetBuilder);
        }
    }
}

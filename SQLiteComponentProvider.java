package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.ComponentProvider;
import com.google.firebase.firestore.local.IndexBackfiller;
import com.google.firebase.firestore.local.LocalSerializer;
import com.google.firebase.firestore.local.LruGarbageCollector;
import com.google.firebase.firestore.local.Persistence;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.local.Scheduler;
import com.google.firebase.firestore.remote.RemoteSerializer;

/* JADX INFO: loaded from: classes3.dex */
public class SQLiteComponentProvider extends MemoryComponentProvider {
    @Override // com.google.firebase.firestore.core.MemoryComponentProvider, com.google.firebase.firestore.core.ComponentProvider
    public Scheduler createGarbageCollectionScheduler(ComponentProvider.Configuration configuration) {
        return ((SQLitePersistence) getPersistence()).getReferenceDelegate().getGarbageCollector().newScheduler(configuration.getAsyncQueue(), getLocalStore());
    }

    @Override // com.google.firebase.firestore.core.MemoryComponentProvider, com.google.firebase.firestore.core.ComponentProvider
    public IndexBackfiller createIndexBackfiller(ComponentProvider.Configuration configuration) {
        return new IndexBackfiller(getPersistence(), configuration.getAsyncQueue(), getLocalStore());
    }

    @Override // com.google.firebase.firestore.core.MemoryComponentProvider, com.google.firebase.firestore.core.ComponentProvider
    public Persistence createPersistence(ComponentProvider.Configuration configuration) {
        return new SQLitePersistence(configuration.getContext(), configuration.getDatabaseInfo().getPersistenceKey(), configuration.getDatabaseInfo().getDatabaseId(), new LocalSerializer(new RemoteSerializer(configuration.getDatabaseInfo().getDatabaseId())), LruGarbageCollector.Params.WithCacheSizeBytes(configuration.getSettings().getCacheSizeBytes()));
    }
}

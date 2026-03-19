package com.google.firebase.firestore.core;

import androidx.annotation.Nullable;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.ComponentProvider;
import com.google.firebase.firestore.local.IndexBackfiller;
import com.google.firebase.firestore.local.LocalStore;
import com.google.firebase.firestore.local.MemoryPersistence;
import com.google.firebase.firestore.local.Persistence;
import com.google.firebase.firestore.local.QueryEngine;
import com.google.firebase.firestore.local.Scheduler;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.mutation.MutationBatchResult;
import com.google.firebase.firestore.remote.AndroidConnectivityMonitor;
import com.google.firebase.firestore.remote.RemoteEvent;
import com.google.firebase.firestore.remote.RemoteStore;
import io.grpc.Status;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryComponentProvider extends ComponentProvider {
    @Override // com.google.firebase.firestore.core.ComponentProvider
    @Nullable
    public Scheduler createGarbageCollectionScheduler(ComponentProvider.Configuration configuration) {
        return null;
    }

    @Override // com.google.firebase.firestore.core.ComponentProvider
    @Nullable
    public IndexBackfiller createIndexBackfiller(ComponentProvider.Configuration configuration) {
        return null;
    }

    @Override // com.google.firebase.firestore.core.ComponentProvider
    public EventManager createEventManager(ComponentProvider.Configuration configuration) {
        return new EventManager(getSyncEngine());
    }

    @Override // com.google.firebase.firestore.core.ComponentProvider
    public LocalStore createLocalStore(ComponentProvider.Configuration configuration) {
        return new LocalStore(getPersistence(), new QueryEngine(), configuration.getInitialUser());
    }

    @Override // com.google.firebase.firestore.core.ComponentProvider
    public AndroidConnectivityMonitor createConnectivityMonitor(ComponentProvider.Configuration configuration) {
        return new AndroidConnectivityMonitor(configuration.getContext());
    }

    @Override // com.google.firebase.firestore.core.ComponentProvider
    public Persistence createPersistence(ComponentProvider.Configuration configuration) {
        return MemoryPersistence.createEagerGcMemoryPersistence();
    }

    @Override // com.google.firebase.firestore.core.ComponentProvider
    public RemoteStore createRemoteStore(ComponentProvider.Configuration configuration) {
        return new RemoteStore(new RemoteStoreCallback(), getLocalStore(), configuration.getDatastore(), configuration.getAsyncQueue(), getConnectivityMonitor());
    }

    @Override // com.google.firebase.firestore.core.ComponentProvider
    public SyncEngine createSyncEngine(ComponentProvider.Configuration configuration) {
        return new SyncEngine(getLocalStore(), getRemoteStore(), configuration.getInitialUser(), configuration.getMaxConcurrentLimboResolutions());
    }

    public class RemoteStoreCallback implements RemoteStore.RemoteStoreCallback {
        private RemoteStoreCallback() {
        }

        @Override // com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback
        public void handleRemoteEvent(RemoteEvent remoteEvent) {
            MemoryComponentProvider.this.getSyncEngine().handleRemoteEvent(remoteEvent);
        }

        @Override // com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback
        public void handleRejectedListen(int i, Status status) {
            MemoryComponentProvider.this.getSyncEngine().handleRejectedListen(i, status);
        }

        @Override // com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback
        public void handleSuccessfulWrite(MutationBatchResult mutationBatchResult) {
            MemoryComponentProvider.this.getSyncEngine().handleSuccessfulWrite(mutationBatchResult);
        }

        @Override // com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback
        public void handleRejectedWrite(int i, Status status) {
            MemoryComponentProvider.this.getSyncEngine().handleRejectedWrite(i, status);
        }

        @Override // com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback
        public void handleOnlineStateChange(OnlineState onlineState) {
            MemoryComponentProvider.this.getSyncEngine().handleOnlineStateChange(onlineState);
        }

        @Override // com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback
        public ImmutableSortedSet<DocumentKey> getRemoteKeysForTarget(int i) {
            return MemoryComponentProvider.this.getSyncEngine().getRemoteKeysForTarget(i);
        }
    }
}

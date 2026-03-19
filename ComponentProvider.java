package com.google.firebase.firestore.core;

import android.content.Context;
import androidx.annotation.Nullable;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.local.IndexBackfiller;
import com.google.firebase.firestore.local.LocalStore;
import com.google.firebase.firestore.local.Persistence;
import com.google.firebase.firestore.local.Scheduler;
import com.google.firebase.firestore.remote.ConnectivityMonitor;
import com.google.firebase.firestore.remote.Datastore;
import com.google.firebase.firestore.remote.RemoteStore;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ComponentProvider {
    private ConnectivityMonitor connectivityMonitor;
    private EventManager eventManager;

    @Nullable
    private Scheduler garbageCollectionScheduler;

    @Nullable
    private IndexBackfiller indexBackfiller;
    private LocalStore localStore;
    private Persistence persistence;
    private RemoteStore remoteStore;
    private SyncEngine syncEngine;

    public abstract ConnectivityMonitor createConnectivityMonitor(Configuration configuration);

    public abstract EventManager createEventManager(Configuration configuration);

    public abstract Scheduler createGarbageCollectionScheduler(Configuration configuration);

    public abstract IndexBackfiller createIndexBackfiller(Configuration configuration);

    public abstract LocalStore createLocalStore(Configuration configuration);

    public abstract Persistence createPersistence(Configuration configuration);

    public abstract RemoteStore createRemoteStore(Configuration configuration);

    public abstract SyncEngine createSyncEngine(Configuration configuration);

    public static class Configuration {
        private final AsyncQueue asyncQueue;
        private final Context context;
        private final DatabaseInfo databaseInfo;
        private final Datastore datastore;
        private final User initialUser;
        private final int maxConcurrentLimboResolutions;
        private final FirebaseFirestoreSettings settings;

        public Configuration(Context context, AsyncQueue asyncQueue, DatabaseInfo databaseInfo, Datastore datastore, User user, int i, FirebaseFirestoreSettings firebaseFirestoreSettings) {
            this.context = context;
            this.asyncQueue = asyncQueue;
            this.databaseInfo = databaseInfo;
            this.datastore = datastore;
            this.initialUser = user;
            this.maxConcurrentLimboResolutions = i;
            this.settings = firebaseFirestoreSettings;
        }

        public FirebaseFirestoreSettings getSettings() {
            return this.settings;
        }

        public AsyncQueue getAsyncQueue() {
            return this.asyncQueue;
        }

        public DatabaseInfo getDatabaseInfo() {
            return this.databaseInfo;
        }

        public Datastore getDatastore() {
            return this.datastore;
        }

        public User getInitialUser() {
            return this.initialUser;
        }

        public int getMaxConcurrentLimboResolutions() {
            return this.maxConcurrentLimboResolutions;
        }

        public Context getContext() {
            return this.context;
        }
    }

    public Persistence getPersistence() {
        return (Persistence) Assert.hardAssertNonNull(this.persistence, "persistence not initialized yet", new Object[0]);
    }

    @Nullable
    public Scheduler getGarbageCollectionScheduler() {
        return this.garbageCollectionScheduler;
    }

    @Nullable
    public IndexBackfiller getIndexBackfiller() {
        return this.indexBackfiller;
    }

    public LocalStore getLocalStore() {
        return (LocalStore) Assert.hardAssertNonNull(this.localStore, "localStore not initialized yet", new Object[0]);
    }

    public SyncEngine getSyncEngine() {
        return (SyncEngine) Assert.hardAssertNonNull(this.syncEngine, "syncEngine not initialized yet", new Object[0]);
    }

    public RemoteStore getRemoteStore() {
        return (RemoteStore) Assert.hardAssertNonNull(this.remoteStore, "remoteStore not initialized yet", new Object[0]);
    }

    public EventManager getEventManager() {
        return (EventManager) Assert.hardAssertNonNull(this.eventManager, "eventManager not initialized yet", new Object[0]);
    }

    public ConnectivityMonitor getConnectivityMonitor() {
        return (ConnectivityMonitor) Assert.hardAssertNonNull(this.connectivityMonitor, "connectivityMonitor not initialized yet", new Object[0]);
    }

    public void initialize(Configuration configuration) {
        Persistence persistenceCreatePersistence = createPersistence(configuration);
        this.persistence = persistenceCreatePersistence;
        persistenceCreatePersistence.start();
        this.localStore = createLocalStore(configuration);
        this.connectivityMonitor = createConnectivityMonitor(configuration);
        this.remoteStore = createRemoteStore(configuration);
        this.syncEngine = createSyncEngine(configuration);
        this.eventManager = createEventManager(configuration);
        this.localStore.start();
        this.remoteStore.start();
        this.garbageCollectionScheduler = createGarbageCollectionScheduler(configuration);
        this.indexBackfiller = createIndexBackfiller(configuration);
    }
}

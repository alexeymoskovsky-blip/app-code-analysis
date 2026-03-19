package com.google.firebase.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.core.UserData;
import com.google.firebase.firestore.model.mutation.DeleteMutation;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class WriteBatch {
    private final FirebaseFirestore firestore;
    private final ArrayList<Mutation> mutations = new ArrayList<>();
    private boolean committed = false;

    public interface Function {
        void apply(@NonNull WriteBatch writeBatch);
    }

    public WriteBatch(FirebaseFirestore firebaseFirestore) {
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firebaseFirestore);
    }

    @NonNull
    public WriteBatch set(@NonNull DocumentReference documentReference, @NonNull Object obj) {
        return set(documentReference, obj, SetOptions.OVERWRITE);
    }

    @NonNull
    public WriteBatch set(@NonNull DocumentReference documentReference, @NonNull Object obj, @NonNull SetOptions setOptions) {
        UserData.ParsedSetData setData;
        this.firestore.validateReference(documentReference);
        Preconditions.checkNotNull(obj, "Provided data must not be null.");
        Preconditions.checkNotNull(setOptions, "Provided options must not be null.");
        verifyNotCommitted();
        if (setOptions.isMerge()) {
            setData = this.firestore.getUserDataReader().parseMergeData(obj, setOptions.getFieldMask());
        } else {
            setData = this.firestore.getUserDataReader().parseSetData(obj);
        }
        this.mutations.add(setData.toMutation(documentReference.getKey(), Precondition.NONE));
        return this;
    }

    @NonNull
    public WriteBatch update(@NonNull DocumentReference documentReference, @NonNull Map<String, Object> map) {
        return update(documentReference, this.firestore.getUserDataReader().parseUpdateData(map));
    }

    @NonNull
    public WriteBatch update(@NonNull DocumentReference documentReference, @NonNull String str, @Nullable Object obj, Object... objArr) {
        return update(documentReference, this.firestore.getUserDataReader().parseUpdateData(Util.collectUpdateArguments(1, str, obj, objArr)));
    }

    @NonNull
    public WriteBatch update(@NonNull DocumentReference documentReference, @NonNull FieldPath fieldPath, @Nullable Object obj, Object... objArr) {
        return update(documentReference, this.firestore.getUserDataReader().parseUpdateData(Util.collectUpdateArguments(1, fieldPath, obj, objArr)));
    }

    private WriteBatch update(@NonNull DocumentReference documentReference, @NonNull UserData.ParsedUpdateData parsedUpdateData) {
        this.firestore.validateReference(documentReference);
        verifyNotCommitted();
        this.mutations.add(parsedUpdateData.toMutation(documentReference.getKey(), Precondition.exists(true)));
        return this;
    }

    @NonNull
    public WriteBatch delete(@NonNull DocumentReference documentReference) {
        this.firestore.validateReference(documentReference);
        verifyNotCommitted();
        this.mutations.add(new DeleteMutation(documentReference.getKey(), Precondition.NONE));
        return this;
    }

    @NonNull
    public Task<Void> commit() {
        verifyNotCommitted();
        this.committed = true;
        if (this.mutations.size() > 0) {
            return this.firestore.getClient().write(this.mutations);
        }
        return Tasks.forResult(null);
    }

    private void verifyNotCommitted() {
        if (this.committed) {
            throw new IllegalStateException("A write batch can no longer be used after commit() has been called.");
        }
    }
}

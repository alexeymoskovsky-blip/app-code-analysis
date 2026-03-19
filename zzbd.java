package com.google.firebase.auth.internal;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.firebase:firebase-auth@@21.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzbd {
    private static final List zza = new ArrayList(Arrays.asList("firebaseAppName", "firebaseUserUid", "operation", "tenantId", "verifyAssertionRequest", "statusCode", "statusMessage", "timestamp"));
    private static final zzbd zzb = new zzbd();
    private Task zzc;
    private Task zzd;
    private long zze = 0;

    private zzbd() {
    }

    public static zzbd zzc() {
        return zzb;
    }

    private static final void zzf(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        Iterator it = zza.iterator();
        while (it.hasNext()) {
            editorEdit.remove((String) it.next());
        }
        editorEdit.commit();
    }

    @Nullable
    public final Task zza() {
        if (DefaultClock.getInstance().currentTimeMillis() - this.zze < 3600000) {
            return this.zzc;
        }
        return null;
    }

    @Nullable
    public final Task zzb() {
        if (DefaultClock.getInstance().currentTimeMillis() - this.zze < 3600000) {
            return this.zzd;
        }
        return null;
    }

    public final void zzd(Context context) {
        Preconditions.checkNotNull(context);
        zzf(context.getSharedPreferences("com.google.firebase.auth.internal.ProcessDeathHelper", 0));
        this.zzc = null;
        this.zze = 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0095  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zze(com.google.firebase.auth.FirebaseAuth r12) {
        /*
            Method dump skipped, instruction units count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.auth.internal.zzbd.zze(com.google.firebase.auth.FirebaseAuth):void");
    }
}

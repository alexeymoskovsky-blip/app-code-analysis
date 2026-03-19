package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzaa extends zzkh {
    private String zza;
    private Set zzb;
    private Map zzc;
    private Long zzd;
    private Long zze;

    public zzaa(zzkt zzktVar) {
        super(zzktVar);
    }

    private final zzu zzd(Integer num) {
        if (this.zzc.containsKey(num)) {
            return (zzu) this.zzc.get(num);
        }
        zzu zzuVar = new zzu(this, this.zza, null);
        this.zzc.put(num, zzuVar);
        return zzuVar;
    }

    private final boolean zzf(int i, int i2) {
        zzu zzuVar = (zzu) this.zzc.get(Integer.valueOf(i));
        if (zzuVar == null) {
            return false;
        }
        return zzuVar.zze.get(i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:409:0x0a44, code lost:
    
        r0 = r62.zzt.zzay().zzk();
        r6 = com.google.android.gms.measurement.internal.zzeh.zzn(r62.zza);
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0a58, code lost:
    
        if (r8.zzj() == false) goto L412;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0a5a, code lost:
    
        r7 = java.lang.Integer.valueOf(r8.zza());
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x0a63, code lost:
    
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x0a64, code lost:
    
        r0.zzc("Invalid property filter ID. appId, id", r6, java.lang.String.valueOf(r7));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0606  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x077d A[PHI: r0 r5 r22 r26 r27
  0x077d: PHI (r0v99 java.util.Map) = (r0v101 java.util.Map), (r0v107 java.util.Map) binds: [B:305:0x07ab, B:292:0x0779] A[DONT_GENERATE, DONT_INLINE]
  0x077d: PHI (r5v29 android.database.Cursor) = (r5v30 android.database.Cursor), (r5v31 android.database.Cursor) binds: [B:305:0x07ab, B:292:0x0779] A[DONT_GENERATE, DONT_INLINE]
  0x077d: PHI (r22v11 com.google.android.gms.measurement.internal.zzas) = (r22v12 com.google.android.gms.measurement.internal.zzas), (r22v16 com.google.android.gms.measurement.internal.zzas) binds: [B:305:0x07ab, B:292:0x0779] A[DONT_GENERATE, DONT_INLINE]
  0x077d: PHI (r26v7 java.lang.String) = (r26v8 java.lang.String), (r26v11 java.lang.String) binds: [B:305:0x07ab, B:292:0x0779] A[DONT_GENERATE, DONT_INLINE]
  0x077d: PHI (r27v8 java.lang.String) = (r27v9 java.lang.String), (r27v11 java.lang.String) binds: [B:305:0x07ab, B:292:0x0779] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:315:0x07ce  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0864  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0a95  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01ac A[Catch: all -> 0x01ba, SQLiteException -> 0x01bd, TRY_LEAVE, TryCatch #14 {all -> 0x01ba, blocks: (B:60:0x01a6, B:62:0x01ac, B:69:0x01c4, B:70:0x01c9, B:71:0x01d3, B:72:0x01e3, B:79:0x020f, B:74:0x01f2, B:76:0x0202, B:78:0x0208, B:94:0x0235), top: B:454:0x01a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01c4 A[Catch: all -> 0x01ba, SQLiteException -> 0x01bd, TRY_ENTER, TryCatch #14 {all -> 0x01ba, blocks: (B:60:0x01a6, B:62:0x01ac, B:69:0x01c4, B:70:0x01c9, B:71:0x01d3, B:72:0x01e3, B:79:0x020f, B:74:0x01f2, B:76:0x0202, B:78:0x0208, B:94:0x0235), top: B:454:0x01a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x024e  */
    /* JADX WARN: Type inference failed for: r4v25, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v48, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v52, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v53 */
    /* JADX WARN: Type inference failed for: r5v54, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v55 */
    /* JADX WARN: Type inference failed for: r5v56 */
    /* JADX WARN: Type inference failed for: r5v57 */
    /* JADX WARN: Type inference failed for: r5v58 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.database.Cursor] */
    @androidx.annotation.WorkerThread
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List zza(java.lang.String r63, java.util.List r64, java.util.List r65, java.lang.Long r66, java.lang.Long r67) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 2854
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaa.zza(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }

    @Override // com.google.android.gms.measurement.internal.zzkh
    public final boolean zzb() {
        return false;
    }
}

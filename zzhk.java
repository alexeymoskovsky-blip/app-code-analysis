package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzhk implements Runnable {
    final /* synthetic */ com.google.android.gms.internal.measurement.zzcf zza;
    final /* synthetic */ zzhx zzb;

    public zzhk(zzhx zzhxVar, com.google.android.gms.internal.measurement.zzcf zzcfVar) {
        this.zzb = zzhxVar;
        this.zza = zzcfVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x009b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r7 = this;
            com.google.android.gms.measurement.internal.zzhx r0 = r7.zzb
            com.google.android.gms.measurement.internal.zzfr r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzkc r0 = r0.zzu()
            com.google.android.gms.internal.measurement.zzpa.zzc()
            com.google.android.gms.measurement.internal.zzfr r1 = r0.zzt
            com.google.android.gms.measurement.internal.zzag r1 = r1.zzf()
            com.google.android.gms.measurement.internal.zzdt r2 = com.google.android.gms.measurement.internal.zzdu.zzau
            r3 = 0
            boolean r1 = r1.zzs(r3, r2)
            if (r1 == 0) goto L77
            com.google.android.gms.measurement.internal.zzfr r1 = r0.zzt
            com.google.android.gms.measurement.internal.zzew r1 = r1.zzm()
            com.google.android.gms.measurement.internal.zzai r1 = r1.zzc()
            com.google.android.gms.measurement.internal.zzah r2 = com.google.android.gms.measurement.internal.zzah.ANALYTICS_STORAGE
            boolean r1 = r1.zzi(r2)
            if (r1 != 0) goto L3d
            com.google.android.gms.measurement.internal.zzfr r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzeh r0 = r0.zzay()
            com.google.android.gms.measurement.internal.zzef r0 = r0.zzl()
            java.lang.String r1 = "Analytics storage consent denied; will not get session id"
            r0.zza(r1)
        L3b:
            r0 = r3
            goto L87
        L3d:
            com.google.android.gms.measurement.internal.zzfr r1 = r0.zzt
            com.google.android.gms.measurement.internal.zzew r1 = r1.zzm()
            com.google.android.gms.measurement.internal.zzfr r2 = r0.zzt
            com.google.android.gms.common.util.Clock r2 = r2.zzav()
            long r4 = r2.currentTimeMillis()
            boolean r1 = r1.zzk(r4)
            if (r1 != 0) goto L3b
            com.google.android.gms.measurement.internal.zzfr r1 = r0.zzt
            com.google.android.gms.measurement.internal.zzew r1 = r1.zzm()
            com.google.android.gms.measurement.internal.zzes r1 = r1.zzk
            long r1 = r1.zza()
            r4 = 0
            int r6 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r6 != 0) goto L66
            goto L3b
        L66:
            com.google.android.gms.measurement.internal.zzfr r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzew r0 = r0.zzm()
            com.google.android.gms.measurement.internal.zzes r0 = r0.zzk
            long r0 = r0.zza()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            goto L87
        L77:
            com.google.android.gms.measurement.internal.zzfr r0 = r0.zzt
            com.google.android.gms.measurement.internal.zzeh r0 = r0.zzay()
            com.google.android.gms.measurement.internal.zzef r0 = r0.zzl()
            java.lang.String r1 = "getSessionId has been disabled."
            r0.zza(r1)
            goto L3b
        L87:
            if (r0 == 0) goto L9b
            com.google.android.gms.measurement.internal.zzhx r1 = r7.zzb
            com.google.android.gms.measurement.internal.zzfr r1 = r1.zzt
            com.google.android.gms.measurement.internal.zzlb r1 = r1.zzv()
            com.google.android.gms.internal.measurement.zzcf r2 = r7.zza
            long r3 = r0.longValue()
            r1.zzU(r2, r3)
            return
        L9b:
            com.google.android.gms.internal.measurement.zzcf r0 = r7.zza     // Catch: android.os.RemoteException -> La1
            r0.zze(r3)     // Catch: android.os.RemoteException -> La1
            return
        La1:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzhx r1 = r7.zzb
            com.google.android.gms.measurement.internal.zzfr r1 = r1.zzt
            com.google.android.gms.measurement.internal.zzeh r1 = r1.zzay()
            com.google.android.gms.measurement.internal.zzef r1 = r1.zzd()
            java.lang.String r2 = "getSessionId failed with exception"
            r1.zzb(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzhk.run():void");
    }
}

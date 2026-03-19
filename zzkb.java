package com.google.android.gms.measurement.internal;

import android.app.ActivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zznw;
import com.google.android.gms.internal.measurement.zzpd;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzkb {
    final /* synthetic */ zzkc zza;

    public zzkb(zzkc zzkcVar) {
        this.zza = zzkcVar;
    }

    @WorkerThread
    public final void zza() {
        this.zza.zzg();
        if (this.zza.zzt.zzm().zzk(this.zza.zzt.zzav().currentTimeMillis())) {
            this.zza.zzt.zzm().zzg.zza(true);
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (runningAppProcessInfo.importance == 100) {
                this.zza.zzt.zzay().zzj().zza("Detected application was in foreground");
                zzc(this.zza.zzt.zzav().currentTimeMillis(), false);
            }
        }
    }

    @WorkerThread
    public final void zzb(long j, boolean z) {
        this.zza.zzg();
        this.zza.zzm();
        if (this.zza.zzt.zzm().zzk(j)) {
            this.zza.zzt.zzm().zzg.zza(true);
            zzpd.zzc();
            if (this.zza.zzt.zzf().zzs(null, zzdu.zzam)) {
                this.zza.zzt.zzh().zzo();
            }
        }
        this.zza.zzt.zzm().zzj.zzb(j);
        if (this.zza.zzt.zzm().zzg.zzb()) {
            zzc(j, z);
        }
    }

    @VisibleForTesting
    @WorkerThread
    public final void zzc(long j, boolean z) {
        this.zza.zzg();
        if (this.zza.zzt.zzJ()) {
            this.zza.zzt.zzm().zzj.zzb(j);
            this.zza.zzt.zzay().zzj().zzb("Session started, time", Long.valueOf(this.zza.zzt.zzav().elapsedRealtime()));
            long j2 = j / 1000;
            this.zza.zzt.zzq().zzY(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_sid", Long.valueOf(j2), j);
            this.zza.zzt.zzm().zzk.zzb(j2);
            this.zza.zzt.zzm().zzg.zza(false);
            Bundle bundle = new Bundle();
            bundle.putLong("_sid", j2);
            if (this.zza.zzt.zzf().zzs(null, zzdu.zzZ) && z) {
                bundle.putLong("_aib", 1L);
            }
            this.zza.zzt.zzq().zzH(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_s", j, bundle);
            zznw.zzc();
            if (this.zza.zzt.zzf().zzs(null, zzdu.zzac)) {
                String strZza = this.zza.zzt.zzm().zzp.zza();
                if (TextUtils.isEmpty(strZza)) {
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("_ffr", strZza);
                this.zza.zzt.zzq().zzH(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ssr", j, bundle2);
            }
        }
    }
}

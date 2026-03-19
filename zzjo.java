package com.google.android.gms.measurement.internal;

import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzjo extends zzkh {
    public final zzes zza;
    public final zzes zzb;
    public final zzes zzc;
    public final zzes zzd;
    public final zzes zze;
    private final Map zzg;

    public zzjo(zzkt zzktVar) {
        super(zzktVar);
        this.zzg = new HashMap();
        zzew zzewVarZzm = this.zzt.zzm();
        zzewVarZzm.getClass();
        this.zza = new zzes(zzewVarZzm, "last_delete_stale", 0L);
        zzew zzewVarZzm2 = this.zzt.zzm();
        zzewVarZzm2.getClass();
        this.zzb = new zzes(zzewVarZzm2, "backoff", 0L);
        zzew zzewVarZzm3 = this.zzt.zzm();
        zzewVarZzm3.getClass();
        this.zzc = new zzes(zzewVarZzm3, "last_upload", 0L);
        zzew zzewVarZzm4 = this.zzt.zzm();
        zzewVarZzm4.getClass();
        this.zzd = new zzes(zzewVarZzm4, "last_upload_attempt", 0L);
        zzew zzewVarZzm5 = this.zzt.zzm();
        zzewVarZzm5.getClass();
        this.zze = new zzes(zzewVarZzm5, "midnight_offset", 0L);
    }

    @WorkerThread
    @Deprecated
    public final Pair zza(String str) {
        zzjn zzjnVar;
        AdvertisingIdClient.Info advertisingIdInfo;
        zzg();
        long jElapsedRealtime = this.zzt.zzav().elapsedRealtime();
        zzjn zzjnVar2 = (zzjn) this.zzg.get(str);
        if (zzjnVar2 != null && jElapsedRealtime < zzjnVar2.zzc) {
            return new Pair(zzjnVar2.zza, Boolean.valueOf(zzjnVar2.zzb));
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        long jZzi = jElapsedRealtime + this.zzt.zzf().zzi(str, zzdu.zza);
        try {
            advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzau());
        } catch (Exception e) {
            this.zzt.zzay().zzc().zzb("Unable to get advertising id", e);
            zzjnVar = new zzjn("", false, jZzi);
        }
        if (advertisingIdInfo == null) {
            return new Pair("", Boolean.FALSE);
        }
        String id = advertisingIdInfo.getId();
        zzjnVar = id != null ? new zzjn(id, advertisingIdInfo.isLimitAdTrackingEnabled(), jZzi) : new zzjn("", advertisingIdInfo.isLimitAdTrackingEnabled(), jZzi);
        this.zzg.put(str, zzjnVar);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(zzjnVar.zza, Boolean.valueOf(zzjnVar.zzb));
    }

    @Override // com.google.android.gms.measurement.internal.zzkh
    public final boolean zzb() {
        return false;
    }

    @WorkerThread
    public final Pair zzd(String str, zzai zzaiVar) {
        return zzaiVar.zzi(zzah.AD_STORAGE) ? zza(str) : new Pair("", Boolean.FALSE);
    }

    @WorkerThread
    @Deprecated
    public final String zzf(String str, boolean z) {
        zzg();
        String str2 = z ? (String) zza(str).first : "00000000-0000-0000-0000-000000000000";
        MessageDigest messageDigestZzF = zzlb.zzF();
        if (messageDigestZzF == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZzF.digest(str2.getBytes())));
    }
}

package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzpd;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@21.2.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzge implements Callable {
    final /* synthetic */ zzaw zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzgj zzc;

    public zzge(zzgj zzgjVar, zzaw zzawVar, String str) {
        this.zzc = zzgjVar;
        this.zza = zzawVar;
        this.zzb = str;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzky zzkyVar;
        zzh zzhVar;
        com.google.android.gms.internal.measurement.zzga zzgaVar;
        String str;
        Bundle bundle;
        com.google.android.gms.internal.measurement.zzgc zzgcVar;
        String str2;
        zzas zzasVarZzc;
        long j;
        String str3;
        byte[] bArr;
        zzkt zzktVar;
        this.zzc.zza.zzA();
        zzic zzicVarZzr = this.zzc.zza.zzr();
        zzaw zzawVar = this.zza;
        String str4 = this.zzb;
        zzicVarZzr.zzg();
        zzfr.zzO();
        Preconditions.checkNotNull(zzawVar);
        Preconditions.checkNotEmpty(str4);
        if (!zzicVarZzr.zzt.zzf().zzs(str4, zzdu.zzS)) {
            zzicVarZzr.zzt.zzay().zzc().zzb("Generating ScionPayload disabled. packageName", str4);
            return new byte[0];
        }
        if (!"_iap".equals(zzawVar.zza) && !"_iapx".equals(zzawVar.zza)) {
            zzicVarZzr.zzt.zzay().zzc().zzc("Generating a payload for this event is not available. package_name, event_name", str4, zzawVar.zza);
            return null;
        }
        com.google.android.gms.internal.measurement.zzga zzgaVarZza = com.google.android.gms.internal.measurement.zzgb.zza();
        zzicVarZzr.zzf.zzi().zzw();
        try {
            zzh zzhVarZzj = zzicVarZzr.zzf.zzi().zzj(str4);
            if (zzhVarZzj == null) {
                zzicVarZzr.zzt.zzay().zzc().zzb("Log and bundle not available. package_name", str4);
                bArr = new byte[0];
                zzktVar = zzicVarZzr.zzf;
            } else {
                if (zzhVarZzj.zzai()) {
                    com.google.android.gms.internal.measurement.zzgc zzgcVarZzt = com.google.android.gms.internal.measurement.zzgd.zzt();
                    zzgcVarZzt.zzad(1);
                    zzgcVarZzt.zzZ("android");
                    if (!TextUtils.isEmpty(zzhVarZzj.zzt())) {
                        zzgcVarZzt.zzD(zzhVarZzj.zzt());
                    }
                    if (!TextUtils.isEmpty(zzhVarZzj.zzv())) {
                        zzgcVarZzt.zzF((String) Preconditions.checkNotNull(zzhVarZzj.zzv()));
                    }
                    if (!TextUtils.isEmpty(zzhVarZzj.zzw())) {
                        zzgcVarZzt.zzG((String) Preconditions.checkNotNull(zzhVarZzj.zzw()));
                    }
                    if (zzhVarZzj.zzb() != -2147483648L) {
                        zzgcVarZzt.zzH((int) zzhVarZzj.zzb());
                    }
                    zzgcVarZzt.zzV(zzhVarZzj.zzm());
                    zzgcVarZzt.zzP(zzhVarZzj.zzk());
                    String strZzy = zzhVarZzj.zzy();
                    String strZzr = zzhVarZzj.zzr();
                    if (!TextUtils.isEmpty(strZzy)) {
                        zzgcVarZzt.zzU(strZzy);
                    } else if (!TextUtils.isEmpty(strZzr)) {
                        zzgcVarZzt.zzC(strZzr);
                    }
                    zzai zzaiVarZzh = zzicVarZzr.zzf.zzh(str4);
                    zzgcVarZzt.zzM(zzhVarZzj.zzj());
                    if (zzicVarZzr.zzt.zzJ() && zzicVarZzr.zzt.zzf().zzt(zzgcVarZzt.zzap()) && zzaiVarZzh.zzi(zzah.AD_STORAGE) && !TextUtils.isEmpty(null)) {
                        zzgcVarZzt.zzO(null);
                    }
                    zzgcVarZzt.zzL(zzaiVarZzh.zzh());
                    if (zzaiVarZzh.zzi(zzah.AD_STORAGE) && zzhVarZzj.zzah()) {
                        Pair pairZzd = zzicVarZzr.zzf.zzs().zzd(zzhVarZzj.zzt(), zzaiVarZzh);
                        if (zzhVarZzj.zzah() && !TextUtils.isEmpty((CharSequence) pairZzd.first)) {
                            try {
                                zzgcVarZzt.zzae(zzic.zza((String) pairZzd.first, Long.toString(zzawVar.zzd)));
                                Object obj = pairZzd.second;
                                if (obj != null) {
                                    zzgcVarZzt.zzX(((Boolean) obj).booleanValue());
                                }
                            } catch (SecurityException e) {
                                zzicVarZzr.zzt.zzay().zzc().zzb("Resettable device id encryption failed", e.getMessage());
                                bArr = new byte[0];
                                zzktVar = zzicVarZzr.zzf;
                            }
                        }
                    }
                    zzicVarZzr.zzt.zzg().zzu();
                    zzgcVarZzt.zzN(Build.MODEL);
                    zzicVarZzr.zzt.zzg().zzu();
                    zzgcVarZzt.zzY(Build.VERSION.RELEASE);
                    zzgcVarZzt.zzaj((int) zzicVarZzr.zzt.zzg().zzb());
                    zzgcVarZzt.zzan(zzicVarZzr.zzt.zzg().zzc());
                    try {
                        if (zzaiVarZzh.zzi(zzah.ANALYTICS_STORAGE) && zzhVarZzj.zzu() != null) {
                            zzgcVarZzt.zzE(zzic.zza((String) Preconditions.checkNotNull(zzhVarZzj.zzu()), Long.toString(zzawVar.zzd)));
                        }
                        if (!TextUtils.isEmpty(zzhVarZzj.zzx())) {
                            zzgcVarZzt.zzT((String) Preconditions.checkNotNull(zzhVarZzj.zzx()));
                        }
                        String strZzt = zzhVarZzj.zzt();
                        List listZzu = zzicVarZzr.zzf.zzi().zzu(strZzt);
                        Iterator it = listZzu.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                zzkyVar = null;
                                break;
                            }
                            zzkyVar = (zzky) it.next();
                            if ("_lte".equals(zzkyVar.zzc)) {
                                break;
                            }
                        }
                        if (zzkyVar == null || zzkyVar.zze == null) {
                            zzky zzkyVar2 = new zzky(strZzt, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", zzicVarZzr.zzt.zzav().currentTimeMillis(), 0L);
                            listZzu.add(zzkyVar2);
                            zzicVarZzr.zzf.zzi().zzL(zzkyVar2);
                        }
                        zzkv zzkvVarZzu = zzicVarZzr.zzf.zzu();
                        zzkvVarZzu.zzt.zzay().zzj().zza("Checking account type status for ad personalization signals");
                        if (zzkvVarZzu.zzt.zzg().zze()) {
                            String strZzt2 = zzhVarZzj.zzt();
                            Preconditions.checkNotNull(strZzt2);
                            if (zzhVarZzj.zzah() && zzkvVarZzu.zzf.zzo().zzn(strZzt2)) {
                                zzkvVarZzu.zzt.zzay().zzc().zza("Turning off ad personalization due to account type");
                                Iterator it2 = listZzu.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    }
                                    if ("_npa".equals(((zzky) it2.next()).zzc)) {
                                        it2.remove();
                                        break;
                                    }
                                }
                                listZzu.add(new zzky(strZzt2, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_npa", zzkvVarZzu.zzt.zzav().currentTimeMillis(), 1L));
                            }
                        }
                        com.google.android.gms.internal.measurement.zzgm[] zzgmVarArr = new com.google.android.gms.internal.measurement.zzgm[listZzu.size()];
                        for (int i = 0; i < listZzu.size(); i++) {
                            com.google.android.gms.internal.measurement.zzgl zzglVarZzd = com.google.android.gms.internal.measurement.zzgm.zzd();
                            zzglVarZzd.zzf(((zzky) listZzu.get(i)).zzc);
                            zzglVarZzd.zzg(((zzky) listZzu.get(i)).zzd);
                            zzicVarZzr.zzf.zzu().zzu(zzglVarZzd, ((zzky) listZzu.get(i)).zze);
                            zzgmVarArr[i] = (com.google.android.gms.internal.measurement.zzgm) zzglVarZzd.zzaC();
                        }
                        zzgcVarZzt.zzj(Arrays.asList(zzgmVarArr));
                        zzei zzeiVarZzb = zzei.zzb(zzawVar);
                        zzicVarZzr.zzt.zzv().zzL(zzeiVarZzb.zzd, zzicVarZzr.zzf.zzi().zzi(str4));
                        zzicVarZzr.zzt.zzv().zzM(zzeiVarZzb, zzicVarZzr.zzt.zzf().zzd(str4));
                        Bundle bundle2 = zzeiVarZzb.zzd;
                        bundle2.putLong("_c", 1L);
                        zzicVarZzr.zzt.zzay().zzc().zza("Marking in-app purchase as real-time");
                        bundle2.putLong("_r", 1L);
                        bundle2.putString("_o", zzawVar.zzc);
                        if (zzicVarZzr.zzt.zzv().zzae(zzgcVarZzt.zzap())) {
                            zzicVarZzr.zzt.zzv().zzO(bundle2, "_dbg", 1L);
                            zzicVarZzr.zzt.zzv().zzO(bundle2, "_r", 1L);
                        }
                        zzas zzasVarZzn = zzicVarZzr.zzf.zzi().zzn(str4, zzawVar.zza);
                        if (zzasVarZzn == null) {
                            zzgcVar = zzgcVarZzt;
                            zzhVar = zzhVarZzj;
                            zzgaVar = zzgaVarZza;
                            str = str4;
                            bundle = bundle2;
                            str2 = null;
                            zzasVarZzc = new zzas(str4, zzawVar.zza, 0L, 0L, 0L, zzawVar.zzd, 0L, null, null, null, null);
                            j = 0;
                        } else {
                            zzhVar = zzhVarZzj;
                            zzgaVar = zzgaVarZza;
                            str = str4;
                            bundle = bundle2;
                            zzgcVar = zzgcVarZzt;
                            str2 = null;
                            long j2 = zzasVarZzn.zzf;
                            zzasVarZzc = zzasVarZzn.zzc(zzawVar.zzd);
                            j = j2;
                        }
                        zzicVarZzr.zzf.zzi().zzE(zzasVarZzc);
                        zzar zzarVar = new zzar(zzicVarZzr.zzt, zzawVar.zzc, str, zzawVar.zza, zzawVar.zzd, j, bundle);
                        com.google.android.gms.internal.measurement.zzfs zzfsVarZze = com.google.android.gms.internal.measurement.zzft.zze();
                        zzfsVarZze.zzm(zzarVar.zzd);
                        zzfsVarZze.zzi(zzarVar.zzb);
                        zzfsVarZze.zzl(zzarVar.zze);
                        zzat zzatVar = new zzat(zzarVar.zzf);
                        while (zzatVar.hasNext()) {
                            String next = zzatVar.next();
                            com.google.android.gms.internal.measurement.zzfw zzfwVarZze = com.google.android.gms.internal.measurement.zzfx.zze();
                            zzfwVarZze.zzj(next);
                            Object objZzf = zzarVar.zzf.zzf(next);
                            if (objZzf != null) {
                                zzicVarZzr.zzf.zzu().zzt(zzfwVarZze, objZzf);
                                zzfsVarZze.zze(zzfwVarZze);
                            }
                        }
                        com.google.android.gms.internal.measurement.zzgc zzgcVar2 = zzgcVar;
                        zzgcVar2.zzk(zzfsVarZze);
                        com.google.android.gms.internal.measurement.zzge zzgeVarZza = com.google.android.gms.internal.measurement.zzgg.zza();
                        com.google.android.gms.internal.measurement.zzfu zzfuVarZza = com.google.android.gms.internal.measurement.zzfv.zza();
                        zzfuVarZza.zza(zzasVarZzc.zzc);
                        zzfuVarZza.zzb(zzawVar.zza);
                        zzgeVarZza.zza(zzfuVarZza);
                        zzgcVar2.zzaa(zzgeVarZza);
                        zzgcVar2.zzf(zzicVarZzr.zzf.zzf().zza(zzhVar.zzt(), Collections.emptyList(), zzgcVar2.zzat(), Long.valueOf(zzfsVarZze.zzc()), Long.valueOf(zzfsVarZze.zzc())));
                        if (zzfsVarZze.zzq()) {
                            zzgcVar2.zzai(zzfsVarZze.zzc());
                            zzgcVar2.zzQ(zzfsVarZze.zzc());
                        }
                        long jZzn = zzhVar.zzn();
                        if (jZzn != 0) {
                            zzgcVar2.zzab(jZzn);
                        }
                        long jZzp = zzhVar.zzp();
                        if (jZzp != 0) {
                            zzgcVar2.zzac(jZzp);
                        } else if (jZzn != 0) {
                            zzgcVar2.zzac(jZzn);
                        }
                        String strZzB = zzhVar.zzB();
                        zzpd.zzc();
                        if (zzicVarZzr.zzt.zzf().zzs(str2, zzdu.zzal)) {
                            str3 = str;
                            if (zzicVarZzr.zzt.zzf().zzs(str3, zzdu.zzan) && strZzB != null) {
                                zzgcVar2.zzah(strZzB);
                            }
                        } else {
                            str3 = str;
                        }
                        zzhVar.zzE();
                        zzgcVar2.zzI((int) zzhVar.zzo());
                        zzicVarZzr.zzt.zzf().zzh();
                        zzgcVar2.zzal(74029L);
                        zzgcVar2.zzak(zzicVarZzr.zzt.zzav().currentTimeMillis());
                        zzgcVar2.zzag(true);
                        if (zzicVarZzr.zzt.zzf().zzs(str2, zzdu.zzar)) {
                            zzicVarZzr.zzf.zzC(zzgcVar2.zzap(), zzgcVar2);
                        }
                        com.google.android.gms.internal.measurement.zzga zzgaVar2 = zzgaVar;
                        zzgaVar2.zza(zzgcVar2);
                        zzh zzhVar2 = zzhVar;
                        zzhVar2.zzab(zzgcVar2.zzd());
                        zzhVar2.zzZ(zzgcVar2.zzc());
                        zzicVarZzr.zzf.zzi().zzD(zzhVar2);
                        zzicVarZzr.zzf.zzi().zzC();
                        zzicVarZzr.zzf.zzi().zzx();
                        try {
                            return zzicVarZzr.zzf.zzu().zzy(((com.google.android.gms.internal.measurement.zzgb) zzgaVar2.zzaC()).zzbu());
                        } catch (IOException e2) {
                            zzicVarZzr.zzt.zzay().zzd().zzc("Data loss. Failed to bundle and serialize. appId", zzeh.zzn(str3), e2);
                            return str2;
                        }
                    } catch (SecurityException e3) {
                        zzicVarZzr.zzt.zzay().zzc().zzb("app instance id encryption failed", e3.getMessage());
                        byte[] bArr2 = new byte[0];
                        zzicVarZzr.zzf.zzi().zzx();
                        return bArr2;
                    }
                }
                zzicVarZzr.zzt.zzay().zzc().zzb("Log and bundle disabled. package_name", str4);
                bArr = new byte[0];
                zzktVar = zzicVarZzr.zzf;
            }
            zzktVar.zzi().zzx();
            return bArr;
        } catch (Throwable th) {
            zzicVarZzr.zzf.zzi().zzx();
            throw th;
        }
    }
}

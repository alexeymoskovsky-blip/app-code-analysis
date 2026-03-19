package com.qiyukf.nimlib.biz.f;

import android.text.TextUtils;
import com.qiyukf.nimlib.NimNosSceneKeyConstant;
import com.qiyukf.nimlib.biz.d.e.r;
import com.qiyukf.nimlib.m.d;
import com.qiyukf.nimlib.plugin.interact.IChatRoomInteract;
import com.qiyukf.nimlib.sdk.InvocationFuture;
import com.qiyukf.nimlib.sdk.misc.DirCacheFileType;
import com.qiyukf.nimlib.sdk.misc.MiscService;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: MiscServiceRemote.java */
/* JADX INFO: loaded from: classes6.dex */
public class e extends com.qiyukf.nimlib.i.i implements MiscService {
    private long a = 0;

    @Override // com.qiyukf.nimlib.sdk.misc.MiscService
    public InvocationFuture<String> zipLogs() {
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.d.b.a.c().b().post(new Runnable() { // from class: com.qiyukf.nimlib.biz.f.e.1
            @Override // java.lang.Runnable
            public final void run() {
                jVarB.b(com.qiyukf.nimlib.log.a.a(false)).b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.misc.MiscService
    public InvocationFuture<Long> getSizeOfDirCache(final List<DirCacheFileType> list, final long j, final long j2) {
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.d.b.a.c().b().post(new Runnable() { // from class: com.qiyukf.nimlib.biz.f.e.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    jVarB.b(Long.valueOf(com.qiyukf.nimlib.n.g.a(e.a(list), j, j2))).b();
                } catch (Throwable th) {
                    jVarB.a(th).b();
                }
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.misc.MiscService
    public InvocationFuture<Void> clearDirCache(final List<DirCacheFileType> list, final long j, final long j2) {
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.d.b.a.c().b().post(new Runnable() { // from class: com.qiyukf.nimlib.biz.f.e.3
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    com.qiyukf.nimlib.n.g.b(e.a(list), j, j2);
                    jVarB.b((Object) null).b();
                } catch (Throwable th) {
                    jVarB.a(th).b();
                }
            }
        });
        return null;
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.biz.f.e$6, reason: invalid class name */
    /* JADX INFO: compiled from: MiscServiceRemote.java */
    public static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[DirCacheFileType.values().length];
            a = iArr;
            try {
                iArr[DirCacheFileType.IMAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[DirCacheFileType.LOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[DirCacheFileType.VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[DirCacheFileType.AUDIO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[DirCacheFileType.OTHER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[DirCacheFileType.THUMB.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.qiyukf.nimlib.sdk.misc.MiscService
    public InvocationFuture<Long> getServerTime() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        long j = jCurrentTimeMillis - this.a;
        long jC = com.qiyukf.nimlib.biz.k.a().b().c();
        if (jC <= 0 || j > Math.max(com.qiyukf.nimlib.c.h().fetchServerTimeInterval, 1000L)) {
            com.qiyukf.nimlib.biz.k.a().b().a(new d.a() { // from class: com.qiyukf.nimlib.biz.f.e.4
                @Override // com.qiyukf.nimlib.m.d.a
                public final void a(long j2) {
                    jVarB.b(Long.valueOf(j2)).b();
                }

                @Override // com.qiyukf.nimlib.m.d.a
                public final void a(int i, String str) {
                    jVarB.a(i).b();
                }
            }, true);
            return null;
        }
        jVarB.b(Long.valueOf(jC)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.misc.MiscService
    public InvocationFuture<String> getSdkLogUpload(final boolean z, final String str, final String str2) {
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.d.b.a.c().b().post(new Runnable() { // from class: com.qiyukf.nimlib.biz.f.e$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.a(z, jVarB, str, str2);
            }
        });
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z, final com.qiyukf.nimlib.i.j jVar, final String str, final String str2) {
        final String strA = com.qiyukf.nimlib.log.a.a(z);
        if (strA == null) {
            jVar.a(6);
        } else {
            com.qiyukf.nimlib.net.a.b.a.a().a(str, strA, "", (String) null, (Object) strA, NimNosSceneKeyConstant.NIM_SYSTEM_NOS_SCENE, false, new com.qiyukf.nimlib.net.a.b.c() { // from class: com.qiyukf.nimlib.biz.f.e.5
                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj, long j, long j2) {
                }

                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj, String str3) {
                    r rVar = new r(str3, true, str2);
                    rVar.a(jVar);
                    e.a(str, rVar);
                    new File(strA).delete();
                    jVar.b(str3).b();
                }

                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj, int i, String str3) {
                    jVar.a(i).b();
                }

                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj) {
                    jVar.a(417);
                }
            });
        }
    }

    public static /* synthetic */ List a(List list) {
        String strA;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            switch (AnonymousClass6.a[((DirCacheFileType) it.next()).ordinal()]) {
                case 1:
                    strA = com.qiyukf.nimlib.n.b.a.a().a(com.qiyukf.nimlib.n.b.b.TYPE_IMAGE);
                    break;
                case 2:
                    strA = com.qiyukf.nimlib.n.b.a.a().a(com.qiyukf.nimlib.n.b.b.TYPE_LOG);
                    break;
                case 3:
                    strA = com.qiyukf.nimlib.n.b.a.a().a(com.qiyukf.nimlib.n.b.b.TYPE_VIDEO);
                    break;
                case 4:
                    strA = com.qiyukf.nimlib.n.b.a.a().a(com.qiyukf.nimlib.n.b.b.TYPE_AUDIO);
                    break;
                case 5:
                    strA = com.qiyukf.nimlib.n.b.a.a().a(com.qiyukf.nimlib.n.b.b.TYPE_FILE);
                    break;
                case 6:
                    strA = com.qiyukf.nimlib.n.b.a.a().a(com.qiyukf.nimlib.n.b.b.TYPE_THUMB_IMAGE);
                    break;
                default:
                    strA = null;
                    break;
            }
            if (!TextUtils.isEmpty(strA)) {
                arrayList.add(strA);
            }
        }
        return arrayList;
    }

    public static /* synthetic */ void a(String str, r rVar) {
        if (TextUtils.isEmpty(str)) {
            com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(rVar));
        } else {
            com.qiyukf.nimlib.plugin.interact.b.a().a(IChatRoomInteract.class);
        }
    }
}

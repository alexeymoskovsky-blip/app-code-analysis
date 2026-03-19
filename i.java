package com.qiyukf.nimlib.biz.f;

import android.net.Uri;
import android.text.TextUtils;
import android.util.LruCache;
import com.qiyukf.nimlib.NimNosSceneKeyConstant;
import com.qiyukf.nimlib.n.w;
import com.qiyukf.nimlib.net.a.b.a;
import com.qiyukf.nimlib.plugin.interact.IChatRoomInteract;
import com.qiyukf.nimlib.sdk.AbortableFuture;
import com.qiyukf.nimlib.sdk.InvocationFuture;
import com.qiyukf.nimlib.sdk.nos.NosService;
import com.qiyukf.nimlib.sdk.nos.constant.NosTransferStatus;
import com.qiyukf.nimlib.sdk.nos.model.NosThumbParam;
import com.qiyukf.nimlib.sdk.nos.model.NosTransferInfo;
import java.io.File;

/* JADX INFO: compiled from: NosServiceRemote.java */
/* JADX INFO: loaded from: classes6.dex */
public class i extends com.qiyukf.nimlib.i.i implements NosService {
    private LruCache<String, String> a = new LruCache<>(100);

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public AbortableFuture upload(File file, String str) {
        return uploadEnableForce(file, str, NimNosSceneKeyConstant.NIM_DEFAULT_PROFILE, false);
    }

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public AbortableFuture uploadAtScene(File file, String str, String str2) {
        return uploadEnableForce(file, str, str2, false);
    }

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public AbortableFuture<String> uploadEnableForce(File file, String str, String str2, boolean z) {
        final NosTransferInfo nosTransferInfo = new NosTransferInfo();
        nosTransferInfo.setPath(file.getPath());
        nosTransferInfo.setSize(file.length());
        nosTransferInfo.setTransferType(NosTransferInfo.TransferType.UPLOAD);
        if (TextUtils.isEmpty(str)) {
            nosTransferInfo.setExtension(w.b(file.getName()));
        } else {
            nosTransferInfo.setExtension(str);
        }
        if (!w.d(str)) {
            str = "";
        }
        String str3 = str;
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        nosTransferInfo.setStatus(NosTransferStatus.transferring);
        com.qiyukf.nimlib.i.b.a(nosTransferInfo);
        final a.c cVarA = com.qiyukf.nimlib.net.a.b.a.a().a((String) null, nosTransferInfo.getPath(), str3, nosTransferInfo.getMd5(), jVarB, str2, z, new com.qiyukf.nimlib.net.a.b.c<com.qiyukf.nimlib.i.j>() { // from class: com.qiyukf.nimlib.biz.f.i.7
            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* bridge */ /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar, int i, String str4) {
                a(i);
            }

            private void a(int i) {
                nosTransferInfo.setStatus(NosTransferStatus.fail);
                com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                jVarB.a(i).b();
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* bridge */ /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar) {
                a(400);
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar, String str4) {
                nosTransferInfo.setStatus(NosTransferStatus.transferred);
                com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                jVarB.b(str4).b();
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar, long j, long j2) {
                com.qiyukf.nimlib.i.b.b(nosTransferInfo.getPath(), j, j2);
            }
        });
        return new com.qiyukf.nimlib.i.g<Runnable>(cVarA) { // from class: com.qiyukf.nimlib.biz.f.i.1
            @Override // com.qiyukf.nimlib.sdk.AbortableFuture
            public final boolean abort() {
                com.qiyukf.nimlib.net.a.b.a.a().a(cVarA);
                return false;
            }
        };
    }

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public AbortableFuture<Void> download(String str, NosThumbParam nosThumbParam, String str2) {
        com.qiyukf.nimlib.net.a.a.e eVar;
        com.qiyukf.nimlib.net.a.c.e eVar2;
        final NosTransferInfo nosTransferInfo = new NosTransferInfo();
        nosTransferInfo.setUrl(str);
        nosTransferInfo.setPath(str2);
        nosTransferInfo.setTransferType(NosTransferInfo.TransferType.DOWNLOAD);
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        final String url = nosTransferInfo.getUrl();
        String path = nosTransferInfo.getPath();
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            eVar = null;
        } else if (new File(path).exists()) {
            jVarB.b((Object) null).b();
            eVar = null;
        } else {
            com.qiyukf.nimlib.net.a.a.f fVar = new com.qiyukf.nimlib.net.a.a.f() { // from class: com.qiyukf.nimlib.biz.f.i.8
                private long e;

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onStart(com.qiyukf.nimlib.net.a.a.e eVar3) {
                    nosTransferInfo.setStatus(NosTransferStatus.transferring);
                    com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onOK(com.qiyukf.nimlib.net.a.a.e eVar3) {
                    nosTransferInfo.setStatus(NosTransferStatus.transferred);
                    com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                    jVarB.b((Object) null).b();
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onFail(com.qiyukf.nimlib.net.a.a.e eVar3, String str3) {
                    nosTransferInfo.setStatus(NosTransferStatus.fail);
                    com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                    i.b(jVarB, 415);
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onCancel(com.qiyukf.nimlib.net.a.a.e eVar3) {
                    nosTransferInfo.setStatus(NosTransferStatus.fail);
                    com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onProgress(com.qiyukf.nimlib.net.a.a.e eVar3, long j) {
                    com.qiyukf.nimlib.i.b.b(url, j, this.e);
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onGetLength(com.qiyukf.nimlib.net.a.a.e eVar3, long j) {
                    this.e = j;
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onExpire(com.qiyukf.nimlib.net.a.a.e eVar3, String str3) {
                    nosTransferInfo.setStatus(NosTransferStatus.fail);
                    com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                    i.b(jVarB, 4);
                }
            };
            com.qiyukf.nimlib.biz.b.e.f();
            if (!com.qiyukf.nimlib.biz.b.a.a()) {
                if (nosThumbParam != null) {
                    int i = AnonymousClass9.a[nosThumbParam.thumb.ordinal()];
                    if (i == 1) {
                        eVar2 = com.qiyukf.nimlib.net.a.c.e.Internal;
                    } else if (i == 2) {
                        eVar2 = com.qiyukf.nimlib.net.a.c.e.External;
                    } else {
                        eVar2 = com.qiyukf.nimlib.net.a.c.e.Crop;
                    }
                    url = com.qiyukf.nimlib.net.a.c.d.a(url, eVar2, nosThumbParam.width, nosThumbParam.height);
                }
                eVar = new com.qiyukf.nimlib.net.a.a.e(url, path, fVar);
                com.qiyukf.nimlib.net.a.a.g.a().a(eVar);
            } else if (nosThumbParam == null) {
                eVar = com.qiyukf.nimlib.biz.b.e.f().a(url, path, fVar);
            } else {
                eVar = com.qiyukf.nimlib.biz.b.e.f().a(url, path, nosThumbParam.width, nosThumbParam.height, fVar);
            }
        }
        if (eVar == null) {
            return null;
        }
        return new com.qiyukf.nimlib.i.g<com.qiyukf.nimlib.net.a.a.e>(eVar) { // from class: com.qiyukf.nimlib.biz.f.i.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.qiyukf.nimlib.sdk.AbortableFuture
            public final boolean abort() {
                com.qiyukf.nimlib.biz.b.e.f();
                if (com.qiyukf.nimlib.biz.b.a.a()) {
                    com.qiyukf.nimlib.biz.b.e.f().a((com.qiyukf.nimlib.net.a.a.e) this.c);
                    return false;
                }
                com.qiyukf.nimlib.net.a.a.g.a().b((com.qiyukf.nimlib.net.a.a.e) this.c);
                return false;
            }
        };
    }

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public AbortableFuture<Void> downloadFileSecure(final String str, String str2) {
        com.qiyukf.nimlib.net.a.a.e eVarA;
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (TextUtils.isEmpty(str)) {
            b(jVarB, 414);
            return null;
        }
        final NosTransferInfo nosTransferInfo = new NosTransferInfo();
        nosTransferInfo.setUrl(str);
        nosTransferInfo.setPath(str2);
        nosTransferInfo.setTransferType(NosTransferInfo.TransferType.DOWNLOAD);
        com.qiyukf.nimlib.net.a.a.f fVar = new com.qiyukf.nimlib.net.a.a.f() { // from class: com.qiyukf.nimlib.biz.f.i.3
            private long d;

            @Override // com.qiyukf.nimlib.net.a.a.f
            public final void onStart(com.qiyukf.nimlib.net.a.a.e eVar) {
                nosTransferInfo.setStatus(NosTransferStatus.transferring);
                com.qiyukf.nimlib.i.b.a(nosTransferInfo);
            }

            @Override // com.qiyukf.nimlib.net.a.a.f
            public final void onOK(com.qiyukf.nimlib.net.a.a.e eVar) {
                nosTransferInfo.setStatus(NosTransferStatus.transferred);
                com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                i.a(eVar);
            }

            @Override // com.qiyukf.nimlib.net.a.a.f
            public final void onFail(com.qiyukf.nimlib.net.a.a.e eVar, String str3) {
                nosTransferInfo.setStatus(NosTransferStatus.fail);
                com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                i.a(eVar);
            }

            @Override // com.qiyukf.nimlib.net.a.a.f
            public final void onCancel(com.qiyukf.nimlib.net.a.a.e eVar) {
                nosTransferInfo.setStatus(NosTransferStatus.fail);
                com.qiyukf.nimlib.i.b.a(nosTransferInfo);
            }

            @Override // com.qiyukf.nimlib.net.a.a.f
            public final void onProgress(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
                com.qiyukf.nimlib.i.b.b(str, j, this.d);
            }

            @Override // com.qiyukf.nimlib.net.a.a.f
            public final void onGetLength(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
                this.d = j;
            }

            @Override // com.qiyukf.nimlib.net.a.a.f
            public final void onExpire(com.qiyukf.nimlib.net.a.a.e eVar, String str3) {
                nosTransferInfo.setStatus(NosTransferStatus.fail);
                com.qiyukf.nimlib.i.b.a(nosTransferInfo);
                i.a(eVar);
            }
        };
        com.qiyukf.nimlib.biz.b.e.f();
        if (com.qiyukf.nimlib.biz.b.a.a()) {
            eVarA = com.qiyukf.nimlib.biz.b.e.f().a(str, str2, fVar);
        } else {
            final com.qiyukf.nimlib.net.a.a.e eVar = new com.qiyukf.nimlib.net.a.a.e(str, str2, fVar);
            if (com.qiyukf.nimlib.session.k.d(str)) {
                com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(new com.qiyukf.nimlib.biz.d.e.h(str)) { // from class: com.qiyukf.nimlib.biz.f.i.4
                    @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
                    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                        super.a(aVar);
                        if (!aVar.e() || !(aVar instanceof com.qiyukf.nimlib.biz.e.e.f)) {
                            i.b(jVarB, 4);
                            return;
                        }
                        String strJ = ((com.qiyukf.nimlib.biz.e.e.f) aVar).j();
                        String str3 = str;
                        if (!TextUtils.isEmpty(strJ)) {
                            if (str.contains("?")) {
                                str3 = str3 + "&token=" + strJ;
                            } else {
                                str3 = str3 + "?token=" + strJ;
                            }
                        }
                        eVar.a(str3);
                        com.qiyukf.nimlib.net.a.a.g.a().a(eVar);
                    }
                });
            } else {
                com.qiyukf.nimlib.net.a.a.g.a().a(eVar);
            }
            eVarA = eVar;
        }
        return new com.qiyukf.nimlib.i.g<com.qiyukf.nimlib.net.a.a.e>(eVarA) { // from class: com.qiyukf.nimlib.biz.f.i.5
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.qiyukf.nimlib.sdk.AbortableFuture
            public final boolean abort() {
                com.qiyukf.nimlib.biz.b.e.f();
                if (com.qiyukf.nimlib.biz.b.a.a()) {
                    com.qiyukf.nimlib.biz.b.e.f().a((com.qiyukf.nimlib.net.a.a.e) this.c);
                    return false;
                }
                com.qiyukf.nimlib.net.a.a.g.a().b((com.qiyukf.nimlib.net.a.a.e) this.c);
                return false;
            }
        };
    }

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public InvocationFuture<String> getOriginUrlFromShortUrl(String str) {
        getOriginUrlFromShortUrl(null, str);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public InvocationFuture<String> getOriginUrlFromShortUrl(String str, final String str2) {
        IChatRoomInteract iChatRoomInteract;
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (TextUtils.isEmpty(str2)) {
            jVarB.b(str2).b();
            return null;
        }
        String str3 = this.a.get(str2);
        if (!TextUtils.isEmpty(str3)) {
            jVarB.b(str3).b();
            return null;
        }
        if (TextUtils.isEmpty(str2) || !com.qiyukf.nimlib.net.a.a.g.e(str2)) {
            jVarB.b(str2).b();
            this.a.put(str2, str2);
            return null;
        }
        com.qiyukf.nimlib.biz.d.e.j jVar = new com.qiyukf.nimlib.biz.d.e.j(str2);
        jVar.a(jVarB);
        com.qiyukf.nimlib.plugin.interact.b.a().a(IChatRoomInteract.class);
        com.qiyukf.nimlib.biz.g.b bVar = new com.qiyukf.nimlib.biz.g.b(jVar) { // from class: com.qiyukf.nimlib.biz.f.i.6
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                com.qiyukf.nimlib.biz.e.e.h hVar;
                super.a(aVar);
                if (!(aVar instanceof com.qiyukf.nimlib.biz.e.e.h) || (hVar = (com.qiyukf.nimlib.biz.e.e.h) aVar) == null || TextUtils.isEmpty(hVar.j())) {
                    return;
                }
                i.this.a.put(str2, hVar.j());
            }
        };
        boolean zA = false;
        if (!TextUtils.isEmpty(str) && (iChatRoomInteract = (IChatRoomInteract) com.qiyukf.nimlib.plugin.interact.b.a().a(IChatRoomInteract.class)) != null) {
            zA = iChatRoomInteract.a();
        }
        if (!zA) {
            com.qiyukf.nimlib.biz.k.a().a(bVar);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(com.qiyukf.nimlib.i.j jVar, int i) {
        if (jVar != null) {
            jVar.a(i).b();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.biz.f.i$9, reason: invalid class name */
    /* JADX INFO: compiled from: NosServiceRemote.java */
    public static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[NosThumbParam.ThumbType.values().length];
            a = iArr;
            try {
                iArr[NosThumbParam.ThumbType.Internal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[NosThumbParam.ThumbType.External.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[NosThumbParam.ThumbType.Crop.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.qiyukf.nimlib.sdk.nos.NosService
    public String convertDownloadUrlToCDNUrl(String str) {
        return com.qiyukf.nimlib.net.a.c.d.a(str, null);
    }

    public static /* synthetic */ void a(com.qiyukf.nimlib.net.a.a.e eVar) {
        String queryParameter;
        String strF = eVar.f();
        if (TextUtils.isEmpty(strF)) {
            return;
        }
        try {
            queryParameter = Uri.parse(strF).getQueryParameter("token");
        } catch (Throwable th) {
            th.printStackTrace();
            queryParameter = null;
        }
        if (TextUtils.isEmpty(queryParameter)) {
            return;
        }
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.d.e.a(queryParameter));
    }
}

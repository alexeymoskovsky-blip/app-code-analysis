package com.qiyukf.nimlib.push;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.qiyukf.nim.highavailable.NativeHighAvailableGetLbsResponseCallback;
import com.qiyukf.nimlib.biz.e.a;
import com.qiyukf.nimlib.n.o;
import com.qiyukf.nimlib.n.p;
import com.qiyukf.nimlib.push.b.c;
import com.qiyukf.nimlib.push.net.d;
import com.qiyukf.nimlib.push.net.lbs.b;
import com.qiyukf.nimlib.report.extension.i;
import com.qiyukf.nimlib.report.k;
import com.qiyukf.nimlib.report.m;
import com.qiyukf.nimlib.sdk.ResponseCode;
import com.qiyukf.nimlib.sdk.StatusCode;
import com.qiyukf.nimlib.sdk.auth.LoginInfo;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: AuthManager.java */
/* JADX INFO: loaded from: classes6.dex */
public final class a implements c.a {
    private Context b;
    private Handler c;
    private com.qiyukf.nimlib.push.d.c d;
    private com.qiyukf.nimlib.push.net.d e;
    private com.qiyukf.nimlib.network.f f;
    private com.qiyukf.nimlib.push.b.c g;
    private com.qiyukf.nimlib.push.a.b.c i;
    private final AtomicReference<StatusCode> a = new AtomicReference<>(StatusCode.UNLOGIN);
    private boolean h = true;
    private volatile String j = null;
    private b.EnumC0157b k = b.EnumC0157b.TCP;
    private int l = 0;

    @NonNull
    private final AbstractRunnableC0152a m = new AbstractRunnableC0152a() { // from class: com.qiyukf.nimlib.push.a.3
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            com.qiyukf.nimlib.net.trace.a.c().a();
            com.qiyukf.nimlib.push.a.b.c cVar = a.this.i;
            if (cVar != null) {
                if (a.this.a.get() == StatusCode.LOGINING) {
                    com.qiyukf.nimlib.log.c.b.a.I("login request 30s timeout");
                    m.b().a(408, "login request 30s timeout", (i) null);
                    f.o().a(a.C0130a.a(cVar.a(), ResponseCode.RES_ETIMEOUT));
                    if (a.this.e != null) {
                        a.this.e.c();
                    }
                }
                com.qiyukf.nimlib.push.net.lbs.b bVarI = cVar.i();
                if (bVarI != null) {
                    int i = AnonymousClass4.a[bVarI.a().ordinal()];
                    if (i != 1) {
                        if (i == 2) {
                            com.qiyukf.nimlib.push.c.c.c().a(bVarI);
                            return;
                        } else {
                            if (i != 3) {
                                return;
                            }
                            com.qiyukf.nimlib.push.c.b.c().a(bVarI);
                            return;
                        }
                    }
                    if (bVarI.k()) {
                        if (a.this.e != null) {
                            com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("onQuickConnectFinished: %s %s %s", Boolean.valueOf(a.this.d.a(a.this.e.a(), false)), a.this.a, cVar));
                        } else {
                            com.qiyukf.nimlib.log.c.b.a.e("AuthManager", String.format("onQuickConnectFinished: linkClient == null %s %s %s", Boolean.valueOf(a.this.d.a((com.qiyukf.nimlib.push.net.lbs.b) null, false)), a.this.a, cVar));
                        }
                    }
                }
            }
        }
    };

    public final String a() {
        return this.j;
    }

    public final void a(Context context, com.qiyukf.nimlib.push.net.d dVar) {
        this.b = context;
        this.e = dVar;
        this.d = new com.qiyukf.nimlib.push.d.c(dVar);
        this.f = new com.qiyukf.nimlib.network.f();
        this.g = new com.qiyukf.nimlib.push.b.c(this);
        if (j()) {
            a(com.qiyukf.nimlib.c.n(), true, false);
        }
    }

    public final synchronized void b() {
        try {
            com.qiyukf.nimlib.c.a((LoginInfo) null);
            this.b = null;
            this.e = null;
            this.d = null;
            com.qiyukf.nimlib.network.f fVar = this.f;
            if (fVar != null) {
                fVar.c();
                this.f = null;
            }
            com.qiyukf.nimlib.push.b.c cVar = this.g;
            if (cVar != null) {
                cVar.b();
                this.g = null;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void a(LoginInfo loginInfo, boolean z, boolean z2) {
        a(loginInfo, z, z2, false);
    }

    public final void a(LoginInfo loginInfo, boolean z, boolean z2, boolean z3) {
        if (loginInfo == null || !loginInfo.valid()) {
            throw new IllegalArgumentException("LoginInfo is invalid!");
        }
        if (z) {
            com.qiyukf.nimlib.log.c.b.a.I("do SDK auto login, account=" + loginInfo.getAccount() + ", customClientType=" + loginInfo.getCustomClientType());
        } else {
            this.j = null;
            com.qiyukf.nimlib.log.c.b.a.I("do user manual login, account=" + loginInfo.getAccount() + ", customClientType=" + loginInfo.getCustomClientType());
        }
        m.b().a(loginInfo, z, z2);
        if (this.a.get() == StatusCode.LOGINED) {
            com.qiyukf.nimlib.log.c.b.a.I("SDK status is LOGINED, current account=" + com.qiyukf.nimlib.c.q() + ", reset !!!");
            com.qiyukf.nimlib.c.a((LoginInfo) null);
            f.o().g();
        }
        this.a.set(StatusCode.UNLOGIN);
        this.h = z;
        com.qiyukf.nimlib.c.a(loginInfo, z);
        com.qiyukf.nimlib.a.a(this.b, com.qiyukf.nimlib.c.f());
        o.c();
        b(z3);
    }

    public final void c() {
        com.qiyukf.nimlib.push.b.c cVar = this.g;
        if (cVar != null) {
            cVar.c();
        }
    }

    public final void d() {
        this.j = null;
        com.qiyukf.nimlib.c.a((LoginInfo) null);
        try {
            com.qiyukf.nimlib.push.a.b.c cVar = this.i;
            if (cVar != null) {
                com.qiyukf.nimlib.log.c.b.a.I("login is close");
                this.i = null;
                l().removeCallbacks(this.m);
                m mVarB = m.b();
                com.qiyukf.nimlib.report.b.e eVar = com.qiyukf.nimlib.report.b.e.CLOSE;
                mVarB.a(eVar);
                f.o().a(a.C0130a.a(cVar.a(), ResponseCode.RES_ETIMEOUT));
                if (h()) {
                    this.d.b();
                }
                com.qiyukf.nimlib.report.d.a(cVar.a(), 415, com.qiyukf.nimlib.report.b.b.kSendAwaitablePacket, eVar);
            }
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("AuthManager", "logoutCloseLogin error", th);
        }
        com.qiyukf.nimlib.push.net.d dVar = this.e;
        if (dVar != null) {
            dVar.a(true, "normal logout");
        }
        f.o().a(new com.qiyukf.nimlib.push.a.b.d());
        a(StatusCode.UNLOGIN, false);
        com.qiyukf.nimlib.job.a.a().b(com.qiyukf.nimlib.c.d());
    }

    public final void a(int i, int i2, String str, int i3) {
        this.j = null;
        StatusCode statusCode = StatusCode.KICKOUT;
        if (i == 2) {
            statusCode = StatusCode.FORBIDDEN;
        } else if (i == 3) {
            statusCode = StatusCode.KICK_BY_OTHER_CLIENT;
        }
        statusCode.setDesc(str);
        com.qiyukf.nimlib.h.b(i2);
        com.qiyukf.nimlib.h.c(i3);
        a(statusCode, false);
    }

    @Override // com.qiyukf.nimlib.push.b.c.a
    public final boolean e() {
        if ((com.qiyukf.nimlib.abtest.b.E() && this.a.get() == StatusCode.CONNECTING) || this.a.get() == StatusCode.LOGINING || this.a.get() == StatusCode.LOGINED) {
            return false;
        }
        m.b().a(com.qiyukf.nimlib.c.n(), true, true);
        return b(false);
    }

    @Override // com.qiyukf.nimlib.push.b.c.a
    public final void f() {
        com.qiyukf.nimlib.log.c.b.a.I("on network unavailable");
        this.e.c();
        if (!this.h) {
            com.qiyukf.nimlib.log.c.b.a.I("onNetworkUnavailable in manual login, stop auto reconnect");
            com.qiyukf.nimlib.c.a((LoginInfo) null);
        }
        a(StatusCode.NET_BROKEN, false);
    }

    public final void a(int i, Throwable th) {
        i iVarA;
        if (i != 0) {
            if (i != 2) {
                return;
            }
            com.qiyukf.nimlib.log.c.b.a.d("core", "on connection changed to CONNECTED");
            if (j()) {
                com.qiyukf.nimlib.push.net.d dVar = this.e;
                com.qiyukf.nimlib.push.net.lbs.b bVarA = dVar == null ? null : dVar.a();
                if (bVarA != null) {
                    if (AnonymousClass4.a[bVarA.a().ordinal()] == 2) {
                        com.qiyukf.nimlib.push.c.c.c().b(bVarA.b, bVarA.c);
                        a(bVarA);
                        return;
                    } else {
                        this.d.b(bVarA);
                        a(bVarA);
                        return;
                    }
                }
                com.qiyukf.nimlib.log.c.b.a.e("AuthManager", "onConnectionEstablished without currentLinkAddress");
                a((com.qiyukf.nimlib.push.net.lbs.b) null);
                return;
            }
            return;
        }
        com.qiyukf.nimlib.log.c.b.a.d("core", "on connection changed to DISCONNECTED");
        boolean zC = p.c(this.b);
        String strConcat = "on connection broken, network connected=".concat(String.valueOf(zC));
        com.qiyukf.nimlib.log.c.b.a.I(strConcat);
        com.qiyukf.nimlib.push.net.d dVar2 = this.e;
        final com.qiyukf.nimlib.push.net.lbs.b bVarA2 = dVar2 == null ? null : dVar2.a();
        if (zC) {
            com.qiyukf.nimlib.network.f fVar = this.f;
            if (fVar != null && bVarA2 != null) {
                String str = bVarA2.b;
                fVar.a(str, str, bVarA2.c, new com.qiyukf.nimlib.network.d() { // from class: com.qiyukf.nimlib.push.a$$ExternalSyntheticLambda0
                    @Override // com.qiyukf.nimlib.network.d
                    public final void onNetworkCheckResult(boolean z) {
                        a.a(bVarA2, z);
                    }
                });
            }
            com.qiyukf.nimlib.net.trace.a.c().b();
        }
        if (this.i != null) {
            com.qiyukf.nimlib.log.c.b.a.I("login is broken");
            this.i = null;
            l().removeCallbacks(this.m);
            iVarA = null;
        } else {
            com.qiyukf.nimlib.push.net.d dVar3 = this.e;
            com.qiyukf.nimlib.push.net.lbs.b bVarA3 = dVar3 != null ? dVar3.a() : null;
            k kVarA = k.a();
            d.c cVar = d.c.MAIN;
            iVarA = kVarA.a(strConcat, bVarA3);
            m.b().c(iVarA);
        }
        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("onConnectionBroken %s %s %s", Boolean.valueOf(j()), com.qiyukf.nimlib.push.net.lbs.b.b(bVarA2), th));
        if (bVarA2 != null) {
            if (j()) {
                int i2 = AnonymousClass4.a[bVarA2.a().ordinal()];
                if (i2 == 1) {
                    this.d.a(bVarA2, th);
                    if (bVarA2.k() && this.d.a(bVarA2, true)) {
                        return;
                    }
                    if (this.a.get() == StatusCode.CONNECTING && com.qiyukf.nimlib.network.h.a(com.qiyukf.nimlib.c.d())) {
                        boolean zX = com.qiyukf.nimlib.abtest.b.x();
                        Pair<LoginInfo, Integer> pairP = com.qiyukf.nimlib.c.p();
                        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("check status to login again: %s %s %s", this.a, pairP, Boolean.valueOf(zX)));
                        if (zX && pairP != null && pairP.first != null && ((Integer) pairP.second).intValue() == 1 && ((com.qiyukf.nimlib.abtest.b.t() && com.qiyukf.nimlib.push.c.b.c().b() != null) || (com.qiyukf.nimlib.abtest.b.s() && com.qiyukf.nimlib.push.c.c.c().b() != null))) {
                            f.o().a(415);
                            f.o().a((LoginInfo) pairP.first, true);
                            return;
                        }
                    }
                } else if (i2 == 2) {
                    if (com.qiyukf.nimlib.h.e() != StatusCode.LOGINED || com.qiyukf.nimlib.abtest.b.w()) {
                        com.qiyukf.nimlib.push.c.c.c().a(bVarA2);
                    }
                } else if (i2 == 3 && (com.qiyukf.nimlib.h.e() != StatusCode.LOGINED || com.qiyukf.nimlib.abtest.b.w())) {
                    com.qiyukf.nimlib.push.c.b.c().a(bVarA2);
                }
            } else {
                com.qiyukf.nimlib.log.c.b.a.e("AuthManager", String.format("onConnectionBroken isAccountValid false %s", th));
            }
        } else {
            com.qiyukf.nimlib.log.c.b.a.e("AuthManager", String.format("onConnectionBroken without currentLinkAddress %s", th));
        }
        if (this.i != null) {
            m.b().a(com.qiyukf.nimlib.report.b.e.BROKEN);
        } else if (com.qiyukf.nimlib.push.d.c.a(th)) {
            m.b().a(408, strConcat, iVarA);
        } else {
            m.b().a(415, strConcat, iVarA);
        }
        a(zC ? StatusCode.UNLOGIN : StatusCode.NET_BROKEN, false);
        if (this.h) {
            return;
        }
        com.qiyukf.nimlib.push.b.c cVar2 = this.g;
        if (cVar2 != null) {
            cVar2.b();
        }
        com.qiyukf.nimlib.log.c.b.a.I("onConnectionBroken in manual login, stop auto reconnect");
        com.qiyukf.nimlib.c.a((LoginInfo) null);
    }

    public final void a(@NonNull final com.qiyukf.nimlib.push.a.c.b bVar) {
        com.qiyukf.nimlib.push.net.lbs.b bVarI;
        short sH = bVar.h();
        boolean z = sH == 200;
        StatusCode statusCodeStatusOfResCode = StatusCode.statusOfResCode(sH);
        l().removeCallbacks(this.m);
        com.qiyukf.nimlib.push.a.b.c cVar = this.i;
        if (cVar != null && (bVarI = cVar.i()) != null) {
            int i = AnonymousClass4.a[bVarI.a().ordinal()];
            if (i == 1) {
                if (bVarI.k()) {
                    if (z) {
                        this.d.a(bVarI);
                    } else {
                        this.e.c();
                        if (statusCodeStatusOfResCode.wontAutoLogin()) {
                            b(bVar);
                        } else if (sH != 399) {
                            if (this.d.a(bVarI, true)) {
                                return;
                            }
                            b(bVar);
                        }
                    }
                }
                if (z) {
                    com.qiyukf.nimlib.push.net.lbs.c.a().a(bVarI);
                    a(true);
                }
            } else if (i != 2) {
                if (i == 3) {
                    if (z) {
                        com.qiyukf.nimlib.push.c.b.c().a(bVarI.b, bVarI.c);
                    } else if (sH == 408 || sH == 415) {
                        com.qiyukf.nimlib.push.c.b.c().a(bVarI);
                    }
                }
            } else if (z) {
                com.qiyukf.nimlib.push.c.c.c().a(bVarI.b, bVarI.c);
            } else if (sH == 408 || sH == 415) {
                com.qiyukf.nimlib.push.c.c.c().a(bVarI);
            }
        }
        this.i = null;
        if (z) {
            this.j = bVar.m().d();
            com.qiyukf.nimlib.c.o();
        }
        if (bVar.h() == 398) {
            final LoginInfo loginInfoN = com.qiyukf.nimlib.c.n();
            String strF = com.qiyukf.nimlib.c.f();
            boolean z2 = this.h;
            if (!z2 && loginInfoN != null && loginInfoN.valid() && !com.qiyukf.nimlib.c.a(398, strF, loginInfoN.getAccount())) {
                com.qiyukf.nimlib.d.b.a.a(this.b).postDelayed(new Runnable() { // from class: com.qiyukf.nimlib.push.a$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.a(loginInfoN, bVar);
                    }
                }, new Random().nextInt(10000) + 5000);
            } else if (!z2) {
                b(bVar);
            }
            if (loginInfoN == null || !loginInfoN.valid()) {
                com.qiyukf.nimlib.log.c.b.a.I(String.format("cancel recording met %s, login info is not valid", (short) 398));
            } else {
                com.qiyukf.nimlib.c.a(398, strF, loginInfoN);
            }
        }
        if (bVar.h() == 399) {
            com.qiyukf.nimlib.push.net.lbs.c.a().a(new AnonymousClass1(this.h, com.qiyukf.nimlib.c.n(), bVar, com.qiyukf.nimlib.c.f()));
            com.qiyukf.nimlib.c.d(true);
            com.qiyukf.nimlib.push.net.lbs.c.a().g();
        }
        if (!this.h && !z) {
            com.qiyukf.nimlib.push.b.c cVar2 = this.g;
            if (cVar2 != null) {
                cVar2.b();
            }
            com.qiyukf.nimlib.c.a((LoginInfo) null);
        }
        this.h = true;
        if (statusCodeStatusOfResCode.wontAutoLogin()) {
            this.e.c();
            com.qiyukf.nimlib.c.a((LoginInfo) null);
        }
        a(statusCodeStatusOfResCode, false);
        if (statusCodeStatusOfResCode == StatusCode.LOGINED) {
            com.qiyukf.nimlib.job.a.a().a(com.qiyukf.nimlib.c.d());
        }
        if (com.qiyukf.nimlib.c.h().enableLoseConnection && statusCodeStatusOfResCode == StatusCode.FORBIDDEN) {
            a(StatusCode.UNLOGIN, true);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.push.a$4 */
    /* JADX INFO: compiled from: AuthManager.java */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.EnumC0157b.values().length];
            a = iArr;
            try {
                iArr[b.EnumC0157b.TCP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[b.EnumC0157b.WEBSOCKET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[b.EnumC0157b.QUIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public /* synthetic */ void a(LoginInfo loginInfo, com.qiyukf.nimlib.push.a.c.b bVar) {
        try {
            a(loginInfo, false, true);
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.b.b(String.format("retry manual login with %s error", (short) 398), th);
            b(bVar);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.push.a$1 */
    /* JADX INFO: compiled from: AuthManager.java */
    public class AnonymousClass1 implements NativeHighAvailableGetLbsResponseCallback {
        final /* synthetic */ boolean a;
        final /* synthetic */ LoginInfo b;
        final /* synthetic */ com.qiyukf.nimlib.push.a.c.b c;
        final /* synthetic */ String d;

        public AnonymousClass1(boolean z, LoginInfo loginInfo, com.qiyukf.nimlib.push.a.c.b bVar, String str) {
            this.a = z;
            this.b = loginInfo;
            this.c = bVar;
            this.d = str;
        }

        @Override // com.qiyukf.nim.highavailable.NativeHighAvailableGetLbsResponseCallback
        public final void onGetLbsResponse(final int i, String str) {
            LoginInfo loginInfo;
            if (com.qiyukf.nimlib.c.M()) {
                com.qiyukf.nimlib.push.net.lbs.c.a().b(this);
                com.qiyukf.nimlib.c.d(false);
                if (!this.a && (loginInfo = this.b) != null && loginInfo.valid() && !com.qiyukf.nimlib.c.a(399, com.qiyukf.nimlib.c.f(), this.b.getAccount())) {
                    Handler handlerA = com.qiyukf.nimlib.d.b.a.a(a.this.b);
                    final LoginInfo loginInfo2 = this.b;
                    final com.qiyukf.nimlib.push.a.c.b bVar = this.c;
                    handlerA.post(new Runnable() { // from class: com.qiyukf.nimlib.push.a$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.a(loginInfo2, i, bVar);
                        }
                    });
                } else if (!this.a) {
                    a.b(this.c);
                }
                LoginInfo loginInfo3 = this.b;
                if (loginInfo3 == null || !loginInfo3.valid()) {
                    com.qiyukf.nimlib.log.c.b.a.I(String.format("cancel recording met %s, login info is not valid", (short) 398));
                } else {
                    com.qiyukf.nimlib.c.a(399, this.d, this.b);
                }
            }
        }

        public /* synthetic */ void a(LoginInfo loginInfo, int i, com.qiyukf.nimlib.push.a.c.b bVar) {
            try {
                a.this.a(loginInfo, false, true);
            } catch (Throwable th) {
                com.qiyukf.nimlib.log.b.b(String.format("retry manual login with %s error", Integer.valueOf(i)), th);
                a.b(bVar);
            }
        }
    }

    public static void b(@NonNull com.qiyukf.nimlib.push.a.c.b bVar) {
        a.C0130a c0130a = new a.C0130a();
        c0130a.a = bVar.a();
        c0130a.b = bVar.j();
        c0130a.c = bVar.c();
        com.qiyukf.nimlib.ipc.e.a(c0130a);
    }

    public final void a(StatusCode statusCode, boolean z) {
        com.qiyukf.nimlib.push.b.c cVar;
        if (this.a.get() != statusCode) {
            if (z || !this.a.get().wontAutoLogin()) {
                if (statusCode.wontAutoLogin() && (cVar = this.g) != null) {
                    cVar.b();
                }
                this.a.set(statusCode);
                com.qiyukf.nimlib.push.b.c cVar2 = this.g;
                if (cVar2 != null) {
                    cVar2.a(statusCode);
                }
                a(statusCode);
                com.qiyukf.nimlib.log.c.b.a.I("SDK status change to ".concat(String.valueOf(statusCode)));
                if (statusCode == StatusCode.LOGINED || statusCode == StatusCode.NET_BROKEN || statusCode == StatusCode.UNLOGIN) {
                    com.qiyukf.nimlib.log.c.b.a.c();
                }
            }
        }
    }

    private static void a(StatusCode statusCode) {
        com.qiyukf.nimlib.h.a(statusCode);
        com.qiyukf.nimlib.ipc.e.a(statusCode);
    }

    private void a(com.qiyukf.nimlib.push.net.lbs.b bVar) {
        a(StatusCode.LOGINING, false);
        m.b().c();
        this.i = new com.qiyukf.nimlib.push.a.b.c(bVar);
        com.qiyukf.nimlib.push.packet.b.c cVarK = k();
        if (cVarK == null) {
            com.qiyukf.nimlib.push.a.c.b bVar2 = new com.qiyukf.nimlib.push.a.c.b();
            com.qiyukf.nimlib.push.packet.a aVarA = this.i.a();
            aVarA.b((short) 398);
            bVar2.a(aVarA);
            bVar2.a(this.i.f());
            f.o().a(bVar2);
            return;
        }
        this.i.a(cVarK);
        f.o().a(this.i);
        l().removeCallbacks(this.m);
        this.m.a();
        l().postDelayed(this.m, r0.b);
    }

    public final void a(boolean z) {
        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", "resetTcpLinkFailCount:".concat(String.valueOf(z)));
        this.l = 0;
        if (z) {
            this.k = b.EnumC0157b.TCP;
        }
    }

    public final void a(int i) {
        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("incrementTcpLinkFailCount: %s %s", this.k, Integer.valueOf(i)));
        if (this.k == b.EnumC0157b.TCP && com.qiyukf.nimlib.network.h.a(com.qiyukf.nimlib.c.d())) {
            if (i == 408 || i == 415 || i == 1000) {
                int i2 = this.l + 1;
                this.l = i2;
                com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("incrementTcpLinkFailCount: %s", Integer.valueOf(i2)));
                if (this.l >= com.qiyukf.nimlib.abtest.b.u()) {
                    if (com.qiyukf.nimlib.abtest.b.t()) {
                        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("incrementTcpLinkFailCount: switch to QUIC", new Object[0]));
                        this.k = b.EnumC0157b.QUIC;
                    } else if (com.qiyukf.nimlib.abtest.b.s()) {
                        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("incrementTcpLinkFailCount: switch to WEBSOCKET", new Object[0]));
                        this.k = b.EnumC0157b.WEBSOCKET;
                    }
                    a(false);
                }
            }
        }
    }

    private synchronized boolean b(boolean z) {
        if (!j()) {
            com.qiyukf.nimlib.log.c.b.a.I("cancel connect, as auth info is invalid!");
            return false;
        }
        com.qiyukf.nimlib.push.net.d dVar = this.e;
        if (dVar == null) {
            com.qiyukf.nimlib.log.c.b.a.I("auth connect, linkClient===null!!");
            return false;
        }
        dVar.c();
        a(StatusCode.CONNECTING, false);
        com.qiyukf.nimlib.push.b.c cVar = this.g;
        if (cVar != null) {
            cVar.a(this.b);
        }
        int i = AnonymousClass4.a[this.k.ordinal()];
        if (i == 1) {
            i();
        } else if (i != 2) {
            if (i == 3) {
                if (com.qiyukf.nimlib.abtest.b.t()) {
                    com.qiyukf.nimlib.push.net.lbs.b bVarB = com.qiyukf.nimlib.push.c.b.c().b();
                    if (bVarB == null) {
                        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", "QUIC fallback to tcp as no linkAddress");
                        a(true);
                        i();
                    } else {
                        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("connect server quic: %s %s", bVarB, Boolean.valueOf(z)));
                        if (z) {
                            bVarB.a((com.qiyukf.nimlib.abtest.b.v() << 1) / 3);
                        }
                        this.e.a(bVarB);
                    }
                } else {
                    com.qiyukf.nimlib.log.c.b.a.d("AuthManager", "QUIC fallback to tcp as isQuicLinkEnabled false");
                    a(true);
                    i();
                }
            }
        } else if (com.qiyukf.nimlib.abtest.b.s()) {
            com.qiyukf.nimlib.push.net.lbs.b bVarB2 = com.qiyukf.nimlib.push.c.c.c().b();
            if (bVarB2 == null) {
                com.qiyukf.nimlib.log.c.b.a.d("AuthManager", "WEBSOCKET fallback to tcp as no linkAddress");
                a(true);
                i();
            } else {
                com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("connect server websocket: %s %s", bVarB2, Boolean.valueOf(z)));
                if (z) {
                    bVarB2.a((com.qiyukf.nimlib.abtest.b.v() << 1) / 3);
                }
                this.e.a(bVarB2);
            }
        } else {
            com.qiyukf.nimlib.log.c.b.a.d("AuthManager", "WEBSOCKET fallback to tcp as isWebsocketLinkEnabled false");
            a(true);
            i();
        }
        return true;
    }

    private void i() {
        com.qiyukf.nimlib.push.d.c cVar = this.d;
        if (cVar == null || !cVar.a()) {
            com.qiyukf.nimlib.push.net.lbs.c.a().a(new com.qiyukf.nimlib.d.a<com.qiyukf.nimlib.push.net.lbs.b>() { // from class: com.qiyukf.nimlib.push.a.2
                public AnonymousClass2() {
                }

                @Override // com.qiyukf.nimlib.d.a
                public final /* synthetic */ void onCallback(com.qiyukf.nimlib.push.net.lbs.b bVar) {
                    com.qiyukf.nimlib.push.net.lbs.b bVar2 = bVar;
                    com.qiyukf.nimlib.push.net.d dVar = a.this.e;
                    StringBuilder sb = new StringBuilder("connect server ");
                    sb.append(bVar2);
                    sb.append(", rel=");
                    sb.append(!com.qiyukf.nimlib.e.e.a());
                    com.qiyukf.nimlib.log.c.b.a.I(sb.toString());
                    if (dVar == null) {
                        com.qiyukf.nimlib.log.c.b.a.e("AuthManager", "getLinkAddress onCallback linkClient == null");
                    } else {
                        dVar.a(bVar2);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.push.a$2 */
    /* JADX INFO: compiled from: AuthManager.java */
    public class AnonymousClass2 implements com.qiyukf.nimlib.d.a<com.qiyukf.nimlib.push.net.lbs.b> {
        public AnonymousClass2() {
        }

        @Override // com.qiyukf.nimlib.d.a
        public final /* synthetic */ void onCallback(com.qiyukf.nimlib.push.net.lbs.b bVar) {
            com.qiyukf.nimlib.push.net.lbs.b bVar2 = bVar;
            com.qiyukf.nimlib.push.net.d dVar = a.this.e;
            StringBuilder sb = new StringBuilder("connect server ");
            sb.append(bVar2);
            sb.append(", rel=");
            sb.append(!com.qiyukf.nimlib.e.e.a());
            com.qiyukf.nimlib.log.c.b.a.I(sb.toString());
            if (dVar == null) {
                com.qiyukf.nimlib.log.c.b.a.e("AuthManager", "getLinkAddress onCallback linkClient == null");
            } else {
                dVar.a(bVar2);
            }
        }
    }

    public static /* synthetic */ void a(com.qiyukf.nimlib.push.net.lbs.b bVar, boolean z) {
        com.qiyukf.nimlib.log.c.b.a.d("AuthManager", "onConnectionBroken networkChecker linkAddress:" + com.qiyukf.nimlib.push.net.lbs.b.b(bVar) + " isConnected:" + z);
    }

    private static boolean j() {
        return com.qiyukf.nimlib.c.n() != null && com.qiyukf.nimlib.c.n().valid();
    }

    /* JADX WARN: Removed duplicated region for block: B:208:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0307  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.qiyukf.nimlib.push.packet.b.c k() {
        /*
            Method dump skipped, instruction units count: 807
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.push.a.k():com.qiyukf.nimlib.push.packet.b.c");
    }

    @Nullable
    private String a(String str) {
        try {
            if (this.b == null) {
                return null;
            }
            Cursor cursorQuery = this.b.getContentResolver().query(Uri.parse(String.format("content://%s/string/%s/%s", this.b.getPackageName() + ".qiyukf.ipc.provider.preference", "PARAMS", str)), null, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                String string = cursorQuery.getString(0);
                cursorQuery.close();
                com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("get value of %s from cp. get length %s", str, Integer.valueOf(string == null ? 0 : string.length())));
                return string;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

    public final void a(com.qiyukf.nimlib.ipc.a.a aVar) {
        com.qiyukf.nimlib.push.b.c cVar = this.g;
        if (cVar != null) {
            cVar.a(aVar);
        }
        if (this.e == null || !aVar.a()) {
            return;
        }
        this.e.e();
    }

    private Handler l() {
        if (this.c == null) {
            this.c = new Handler(Looper.getMainLooper());
        }
        return this.c;
    }

    public final void g() {
        com.qiyukf.nimlib.push.b.c cVar = this.g;
        if (cVar != null) {
            cVar.a();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.push.a$a */
    /* JADX INFO: compiled from: AuthManager.java */
    public static abstract class AbstractRunnableC0152a implements Runnable {
        private long a;
        final int b;
        final int c;

        public AbstractRunnableC0152a() {
            int iA = com.qiyukf.nimlib.c.i().a();
            this.b = iA;
            this.c = iA / 2;
            this.a = System.currentTimeMillis();
        }

        public final void a() {
            this.a = System.currentTimeMillis();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.push.a$3 */
    /* JADX INFO: compiled from: AuthManager.java */
    public class AnonymousClass3 extends AbstractRunnableC0152a {
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            com.qiyukf.nimlib.net.trace.a.c().a();
            com.qiyukf.nimlib.push.a.b.c cVar = a.this.i;
            if (cVar != null) {
                if (a.this.a.get() == StatusCode.LOGINING) {
                    com.qiyukf.nimlib.log.c.b.a.I("login request 30s timeout");
                    m.b().a(408, "login request 30s timeout", (i) null);
                    f.o().a(a.C0130a.a(cVar.a(), ResponseCode.RES_ETIMEOUT));
                    if (a.this.e != null) {
                        a.this.e.c();
                    }
                }
                com.qiyukf.nimlib.push.net.lbs.b bVarI = cVar.i();
                if (bVarI != null) {
                    int i = AnonymousClass4.a[bVarI.a().ordinal()];
                    if (i != 1) {
                        if (i == 2) {
                            com.qiyukf.nimlib.push.c.c.c().a(bVarI);
                            return;
                        } else {
                            if (i != 3) {
                                return;
                            }
                            com.qiyukf.nimlib.push.c.b.c().a(bVarI);
                            return;
                        }
                    }
                    if (bVarI.k()) {
                        if (a.this.e != null) {
                            com.qiyukf.nimlib.log.c.b.a.d("AuthManager", String.format("onQuickConnectFinished: %s %s %s", Boolean.valueOf(a.this.d.a(a.this.e.a(), false)), a.this.a, cVar));
                        } else {
                            com.qiyukf.nimlib.log.c.b.a.e("AuthManager", String.format("onQuickConnectFinished: linkClient == null %s %s %s", Boolean.valueOf(a.this.d.a((com.qiyukf.nimlib.push.net.lbs.b) null, false)), a.this.a, cVar));
                        }
                    }
                }
            }
        }
    }

    public final boolean h() {
        com.qiyukf.nimlib.push.net.lbs.b bVarI;
        com.qiyukf.nimlib.push.a.b.c cVar = this.i;
        if (cVar == null || (bVarI = cVar.i()) == null) {
            return false;
        }
        return bVarI.k();
    }
}

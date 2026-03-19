package com.qiyukf.nimlib.push.a.a;

import com.qiyukf.nimlib.biz.SyncCrossProcessDBHelper;
import com.qiyukf.nimlib.biz.a.a;
import com.qiyukf.nimlib.biz.d.e.l;
import com.qiyukf.nimlib.biz.d.e.o;
import com.qiyukf.nimlib.biz.k;
import com.qiyukf.nimlib.biz.m;
import com.qiyukf.nimlib.biz.n;
import com.qiyukf.nimlib.h;
import com.qiyukf.nimlib.plugin.interact.IMixPushInteract;
import com.qiyukf.nimlib.push.f;
import com.qiyukf.nimlib.report.q;
import com.qiyukf.nimlib.sdk.ModeCode;
import com.qiyukf.nimlib.sdk.ResponseCode;
import com.qiyukf.nimlib.sdk.StatusCode;
import com.qiyukf.nimlib.sdk.auth.constant.LoginSyncStatus;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: LoginResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class b extends com.qiyukf.nimlib.biz.c.a {
    private final boolean a;

    public b(boolean z) {
        this.a = z;
    }

    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        if (this.a) {
            com.qiyukf.nimlib.push.a.c.b bVar = (com.qiyukf.nimlib.push.a.c.b) aVar;
            if (h.e() == StatusCode.LOGINING) {
                if (bVar.e()) {
                    com.qiyukf.nimlib.log.c.b.a.I("onLoginPush SDK login success, account=" + com.qiyukf.nimlib.c.q());
                    if (!f.o().m()) {
                        com.qiyukf.nimlib.log.c.b.a.I("onLoginPush SDK login success, but link is disconnect, account=" + com.qiyukf.nimlib.c.q());
                        bVar.a().b(ResponseCode.RES_INVALID);
                    }
                } else {
                    com.qiyukf.nimlib.log.c.b.a.I("onLoginPush SDK login failed, code=" + ((int) bVar.h()));
                }
                f.o().a(bVar);
                if (bVar.e()) {
                    com.qiyukf.nimlib.m.e.a(true);
                    com.qiyukf.nimlib.push.b.a();
                    if (!h.b()) {
                        com.qiyukf.nimlib.push.d dVarM = bVar.m();
                        com.qiyukf.nimlib.ipc.a.c cVar = new com.qiyukf.nimlib.ipc.a.c(dVarM.c(), dVarM.a() == 1, dVarM.b());
                        com.qiyukf.nimlib.ipc.e.a(cVar);
                        com.qiyukf.nimlib.log.b.k("sdk sync MixPushState = " + cVar.toString());
                    }
                    com.qiyukf.nimlib.push.packet.b.c cVar2 = new com.qiyukf.nimlib.push.packet.b.c();
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    com.qiyukf.nimlib.log.b.C("syncData before get Data");
                    com.qiyukf.nimlib.ipc.a.h hVarA = com.qiyukf.nimlib.ipc.a.h.a(SyncCrossProcessDBHelper.queryStringValue("k_sync_time_tag", ""));
                    com.qiyukf.nimlib.log.b.C("syncData after get Data,cost time = " + (System.currentTimeMillis() - jCurrentTimeMillis));
                    cVar2.a(a.EnumC0127a.UNREAD_MESSAGE.a(), hVarA.d());
                    cVar2.a(a.EnumC0127a.YSF_UNREAD_MSG.a(), 0);
                    cVar2.a(a.EnumC0127a.TINFO.a(), hVarA.e());
                    cVar2.a(a.EnumC0127a.DND_PUSH.a(), hVarA.f());
                    cVar2.a(a.EnumC0127a.AVCHAT.a(), hVarA.g());
                    cVar2.a(a.EnumC0127a.ROAMING_MSG.a(), hVarA.h());
                    cVar2.a(a.EnumC0127a.BLACK_AND_MUTE.a(), hVarA.i());
                    cVar2.a(a.EnumC0127a.FREIND_LIST.a(), hVarA.j());
                    cVar2.a(a.EnumC0127a.MY_INFO.a(), hVarA.c());
                    cVar2.a(a.EnumC0127a.FRIEND_INFO.a(), hVarA.k());
                    cVar2.a(a.EnumC0127a.MSG_READ.a(), hVarA.l());
                    cVar2.a(a.EnumC0127a.DONNOP_PUSH.a(), hVarA.n());
                    cVar2.a(a.EnumC0127a.MY_TLIST.a(), hVarA.m());
                    cVar2.a(a.EnumC0127a.ROAM_DELETE_MSG.a(), hVarA.o());
                    if (com.qiyukf.nimlib.c.h().sessionReadAck) {
                        long jP = hVarA.p();
                        cVar2.a(a.EnumC0127a.SESSION_ACK_LIST.a(), jP);
                        com.qiyukf.nimlib.log.b.t("sync session ack list, syncTimeTag=".concat(String.valueOf(jP)));
                    }
                    cVar2.a(a.EnumC0127a.ROBOT_LIST.a(), hVarA.q());
                    cVar2.a(a.EnumC0127a.BROADCAST_MSG.a(), hVarA.r());
                    cVar2.a(a.EnumC0127a.SIGNALLING_MSG.a(), hVarA.s());
                    cVar2.a(a.EnumC0127a.SUPER_TINFO.a(), hVarA.t());
                    cVar2.a(a.EnumC0127a.MY_SUPER_TLIST.a(), hVarA.u());
                    if (com.qiyukf.nimlib.c.h().sessionReadAck) {
                        long jX = hVarA.x();
                        com.qiyukf.nimlib.log.b.t("sync super team session ack list, syncTimeTag=".concat(String.valueOf(jX)));
                        cVar2.a(a.EnumC0127a.SUPERTEAM_SESSION_ACK_LIST.a(), jX);
                    }
                    cVar2.a(a.EnumC0127a.MSG_DELETE_SELF.a(), hVarA.y());
                    if (com.qiyukf.nimlib.c.h().notifyStickTopSession) {
                        cVar2.a(a.EnumC0127a.STICK_TOP_SESSION.a(), hVarA.z());
                    }
                    cVar2.a(a.EnumC0127a.SESSION_HISTORY_MSGS_DELETE.a(), hVarA.A());
                    cVar2.a(a.EnumC0127a.SUPER_ROAMING_MSG.a(), hVarA.v());
                    cVar2.a(a.EnumC0127a.ROAM_SUPERTEAM_DELETE_MSG.a(), hVarA.w());
                    cVar2.a(a.EnumC0127a.P2P_TEAM_MODIFY_MMESSAGE.a(), hVarA.B());
                    cVar2.a(a.EnumC0127a.SUPERTEAM_MODIFY_MMESSAGE.a(), hVarA.C());
                    com.qiyukf.nimlib.log.b.C("syncData before send request,cost time = " + (System.currentTimeMillis() - jCurrentTimeMillis));
                    q.a();
                    com.qiyukf.nimlib.push.a.b.f fVar = new com.qiyukf.nimlib.push.a.b.f();
                    fVar.a(cVar2);
                    f.o().a(fVar);
                    com.qiyukf.nimlib.log.c.b.a.I("SDK send login sync data request");
                    com.qiyukf.nimlib.log.c.b.a.I("request sync time tags : ".concat(String.valueOf(hVarA)));
                }
                ArrayList<com.qiyukf.nimlib.biz.f> arrayListL = bVar.l();
                if (arrayListL != null && arrayListL.size() > 0) {
                    String strC = com.qiyukf.nimlib.push.b.c();
                    Iterator<com.qiyukf.nimlib.biz.f> it = arrayListL.iterator();
                    while (it.hasNext()) {
                        if (it.next().a().equals(strC)) {
                            it.remove();
                        }
                    }
                    com.qiyukf.nimlib.push.h.a(arrayListL);
                    return;
                }
                com.qiyukf.nimlib.push.h.a();
                return;
            }
            return;
        }
        com.qiyukf.nimlib.push.a.c.b bVar2 = (com.qiyukf.nimlib.push.a.c.b) aVar;
        if (k.a().m()) {
            com.qiyukf.nimlib.log.c.b.a.d("LoginResponseHandler", "onLoginUIRaw synchronized true");
            synchronized (k.a()) {
                a(bVar2);
            }
        } else {
            com.qiyukf.nimlib.log.c.b.a.d("LoginResponseHandler", "onLoginUIRaw synchronized false");
            a(bVar2);
        }
    }

    private static void a(com.qiyukf.nimlib.push.a.c.b bVar) {
        if (bVar.e()) {
            com.qiyukf.nimlib.log.c.b.a.I("onLoginUI SDK login success, account=" + com.qiyukf.nimlib.c.q());
            k.a().a(bVar.m().d());
            com.qiyukf.nimlib.biz.a.a(bVar.m());
            k.a().b().d();
            h.a(ModeCode.IM);
            b(bVar);
            com.qiyukf.nimlib.c.c(true);
            com.qiyukf.nimlib.log.c.b.a.H("notify LoginSyncDataStatus: BEGIN_SYNC");
            com.qiyukf.nimlib.i.b.a(LoginSyncStatus.BEGIN_SYNC);
            com.qiyukf.nimlib.c.a aVarC = m.c();
            k.a().a(new o(aVarC.a(), aVarC.b(), aVarC.c()));
            k.a().a(new l());
        } else {
            com.qiyukf.nimlib.log.c.b.a.I("onLoginUI SDK login failed, code=" + ((int) bVar.h()));
        }
        k.a().a(bVar.h());
        if (bVar.e()) {
            n.a(bVar.k().c(103));
            n.t(System.currentTimeMillis());
        }
    }

    private static void b(com.qiyukf.nimlib.push.a.c.b bVar) {
        com.qiyukf.nimlib.push.d dVarM = bVar.m();
        int iC = dVarM.c();
        boolean z = dVarM.a() == 1;
        String strB = dVarM.b();
        if (((IMixPushInteract) com.qiyukf.nimlib.plugin.interact.b.a().a(IMixPushInteract.class)) != null) {
            new com.qiyukf.nimlib.ipc.a.c(iC, z, strB);
        }
    }
}

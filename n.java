package com.qiyukf.nimlib.biz.c.j;

import android.os.SystemClock;
import com.qiyukf.nimlib.n.e;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.SessionAckInfo;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.session.x;
import com.qiyukf.nimlib.session.y;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: SessionAckResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class n extends com.qiyukf.nimlib.biz.c.i {
    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        if ((aVar.e() || aVar.h() == 700) && com.qiyukf.nimlib.c.h().sessionReadAck) {
            if (aVar instanceof com.qiyukf.nimlib.biz.e.g.h) {
                com.qiyukf.nimlib.biz.e.g.h hVar = (com.qiyukf.nimlib.biz.e.g.h) aVar;
                long jL = hVar.l();
                com.qiyukf.nimlib.log.b.t("onLoginSyncSession syncTimeTag=".concat(String.valueOf(jL)));
                Map<String, Long> mapJ = hVar.j();
                Map<String, Long> mapK = hVar.k();
                ArrayList arrayList = new ArrayList(mapJ.size() + mapK.size());
                for (Map.Entry<String, Long> entry : mapJ.entrySet()) {
                    arrayList.add(new y(SessionTypeEnum.P2P, entry.getKey(), entry.getValue().longValue()));
                }
                for (Map.Entry<String, Long> entry2 : mapK.entrySet()) {
                    arrayList.add(new y(SessionTypeEnum.Team, entry2.getKey(), entry2.getValue().longValue()));
                }
                a(arrayList);
                com.qiyukf.nimlib.biz.n.d(jL);
                return;
            }
            if (aVar instanceof com.qiyukf.nimlib.biz.e.k.t) {
                com.qiyukf.nimlib.biz.e.k.t tVar = (com.qiyukf.nimlib.biz.e.k.t) aVar;
                SessionTypeEnum sessionTypeEnumJ = tVar.j();
                String strK = tVar.k();
                long jL2 = tVar.l();
                y yVar = new y(sessionTypeEnumJ, strK, jL2);
                ArrayList arrayList2 = new ArrayList(1);
                arrayList2.add(yVar);
                a(arrayList2);
                com.qiyukf.nimlib.log.b.t("onOnlineSyncSessionAckNotify, sessionId=" + strK + ",time=" + jL2);
                return;
            }
            if (aVar instanceof com.qiyukf.nimlib.biz.e.k.c) {
                com.qiyukf.nimlib.biz.e.k.c cVar = (com.qiyukf.nimlib.biz.e.k.c) aVar;
                com.qiyukf.nimlib.biz.d.i.b bVar = (com.qiyukf.nimlib.biz.d.i.b) com.qiyukf.nimlib.biz.k.a().a(cVar);
                if (bVar != null) {
                    x.b(bVar.i(), bVar.j(), bVar.k());
                    com.qiyukf.nimlib.log.b.t("session ack response, sessionId=" + bVar.i() + ", timetag=" + bVar.k());
                }
                com.qiyukf.nimlib.biz.c.i.a(cVar, null);
                return;
            }
            if (aVar instanceof com.qiyukf.nimlib.biz.e.k.b) {
                com.qiyukf.nimlib.biz.e.k.b bVar2 = (com.qiyukf.nimlib.biz.e.k.b) aVar;
                final ArrayList arrayListD = com.qiyukf.nimlib.n.e.d(bVar2.j(), new n$$ExternalSyntheticLambda0());
                com.qiyukf.nimlib.biz.d.i.a aVar2 = (com.qiyukf.nimlib.biz.d.i.a) com.qiyukf.nimlib.biz.k.a().a(bVar2);
                if (aVar2 != null) {
                    com.qiyukf.nimlib.n.e.g(com.qiyukf.nimlib.n.e.e(aVar2.i(), new e.a() { // from class: com.qiyukf.nimlib.biz.c.j.n$$ExternalSyntheticLambda1
                        @Override // com.qiyukf.nimlib.n.e.a
                        public final Object transform(Object obj) {
                            return n.a(arrayListD, (com.qiyukf.nimlib.push.packet.b.c) obj);
                        }
                    }), new e.a() { // from class: com.qiyukf.nimlib.biz.c.j.n$$ExternalSyntheticLambda2
                        @Override // com.qiyukf.nimlib.n.e.a
                        public final Object transform(Object obj) {
                            return n.a((com.qiyukf.nimlib.push.packet.b.c) obj);
                        }
                    });
                    com.qiyukf.nimlib.biz.c.i.a(bVar2, arrayListD, 200);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(ArrayList arrayList, com.qiyukf.nimlib.push.packet.b.c cVar) {
        if (cVar == null) {
            return null;
        }
        y yVarA = y.a(cVar);
        final String sessionId = yVarA.getSessionId();
        final SessionTypeEnum sessionType = yVarA.getSessionType();
        return Boolean.valueOf(!com.qiyukf.nimlib.n.e.c(arrayList, new e.a() { // from class: com.qiyukf.nimlib.biz.c.j.n$$ExternalSyntheticLambda3
            @Override // com.qiyukf.nimlib.n.e.a
            public final Object transform(Object obj) {
                return n.a(sessionId, sessionType, (SessionAckInfo) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(String str, SessionTypeEnum sessionTypeEnum, SessionAckInfo sessionAckInfo) {
        return Boolean.valueOf(sessionAckInfo != null && str.equals(sessionAckInfo.getSessionId()) && sessionTypeEnum == sessionAckInfo.getSessionType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(com.qiyukf.nimlib.push.packet.b.c cVar) {
        if (cVar == null) {
            return Boolean.TRUE;
        }
        y yVarA = y.a(cVar);
        x.b(yVarA.getSessionId(), yVarA.getSessionType(), yVarA.getTime());
        return Boolean.TRUE;
    }

    private static void a(List<SessionAckInfo> list) {
        boolean z;
        for (SessionAckInfo sessionAckInfo : list) {
            com.qiyukf.nimlib.log.b.t("onSessionAck" + sessionAckInfo.toString());
            String sessionId = sessionAckInfo.getSessionId();
            SessionTypeEnum sessionType = sessionAckInfo.getSessionType();
            if (x.a(sessionId, sessionType, x.b(sessionId, sessionType, sessionAckInfo.getTime()))) {
                String sessionId2 = sessionAckInfo.getSessionId();
                SessionTypeEnum sessionType2 = sessionAckInfo.getSessionType();
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                int iB = x.b(sessionId2, sessionType2);
                com.qiyukf.nimlib.session.s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(sessionId2, sessionType2);
                int unreadCount = (sVarQueryRecentContact == null || iB <= sVarQueryRecentContact.getUnreadCount()) ? iB : sVarQueryRecentContact.getUnreadCount();
                if (sVarQueryRecentContact == null || unreadCount == sVarQueryRecentContact.getUnreadCount()) {
                    z = false;
                } else {
                    MsgDBHelper.updateRecentUnreadNum(sessionId2, sessionType2, unreadCount);
                    sVarQueryRecentContact.a(unreadCount);
                    com.qiyukf.nimlib.session.k.a(sVarQueryRecentContact);
                    com.qiyukf.nimlib.i.b.a(sVarQueryRecentContact);
                    z = true;
                }
                long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                StringBuilder sb = new StringBuilder();
                sb.append("recalculate unread count, sessionId=");
                sb.append(sessionId2);
                sb.append(", type=");
                sb.append(sessionType2);
                sb.append(", recalculate unread=");
                sb.append(iB);
                sb.append(", recent unread=");
                sb.append(sVarQueryRecentContact != null ? sVarQueryRecentContact.getUnreadCount() : 0);
                sb.append(", output unread=");
                sb.append(unreadCount);
                sb.append(", updateAndNotify=");
                sb.append(z);
                sb.append(", cost time=");
                sb.append(jElapsedRealtime2);
                sb.append("ms");
                com.qiyukf.nimlib.log.c.b.a.H(sb.toString());
            }
        }
        com.qiyukf.nimlib.l.d.a(list);
    }
}

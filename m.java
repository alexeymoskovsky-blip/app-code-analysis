package com.qiyukf.nimlib.biz.c.j;

import android.text.TextUtils;
import com.qiyukf.nimlib.biz.e.k.aa;
import com.qiyukf.nimlib.biz.e.k.u;
import com.qiyukf.nimlib.biz.e.k.v;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.RevokeType;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.RevokeMsgNotification;
import com.qiyukf.nimlib.session.MsgDBHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: compiled from: RevokeMessageResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class m extends com.qiyukf.nimlib.biz.c.i {
    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        boolean z;
        com.qiyukf.nimlib.session.d dVarA;
        if (!(aVar instanceof v)) {
            int i = 14;
            int i2 = 10;
            if (!(aVar instanceof u)) {
                if (aVar instanceof aa) {
                    aa aaVar = (aa) aVar;
                    com.qiyukf.nimlib.biz.n.f(aaVar.j());
                    List<com.qiyukf.nimlib.push.packet.b.c> listK = aaVar.k();
                    ArrayList<RevokeMsgNotification> arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    HashSet hashSet = new HashSet();
                    byte bL = aaVar.l();
                    for (com.qiyukf.nimlib.push.packet.b.c cVar : listK) {
                        String strC = cVar.c(i2);
                        if (TextUtils.isEmpty(MsgDBHelper.queryRevokeMessage(strC))) {
                            long jE = cVar.e(6);
                            long jE2 = cVar.e(i);
                            SessionTypeEnum sessionTypeEnumA = a(cVar);
                            com.qiyukf.nimlib.session.d dVarA2 = (com.qiyukf.nimlib.session.d) MsgDBHelper.queryMessageByUuid(strC);
                            if (dVarA2 == null) {
                                dVarA2 = a(cVar, jE2, sessionTypeEnumA);
                            } else if (MsgDBHelper.deleteMessage(dVarA2) > 0 && com.qiyukf.nimlib.session.k.d((IMMessage) dVarA2)) {
                                hashSet.add(strC);
                            }
                            MsgDBHelper.saveRevokeMessage(strC);
                            String strC2 = cVar.c(16);
                            if (strC2 == null) {
                                strC2 = cVar.c(3);
                            }
                            String str = strC2;
                            String strC3 = cVar.c(4);
                            com.qiyukf.nimlib.session.d dVar = dVarA2;
                            arrayList.add(new RevokeMsgNotification(dVar, cVar.c(5), str, strC3, bL, RevokeType.typeOfValue(cVar.d(1)), cVar.c(22)));
                            if (jE > 0) {
                                arrayList2.add(Long.valueOf(jE));
                            }
                            i = 14;
                            i2 = 10;
                        }
                    }
                    if (bL == 1) {
                        com.qiyukf.nimlib.biz.d.f.a aVar2 = new com.qiyukf.nimlib.biz.d.f.a();
                        aVar2.a((byte) 7);
                        aVar2.b((byte) 15);
                        aVar2.a((List<Long>) arrayList2);
                        com.qiyukf.nimlib.biz.k.a().a(aVar2, com.qiyukf.nimlib.biz.g.a.d);
                    }
                    for (RevokeMsgNotification revokeMsgNotification : arrayList) {
                        String uuid = revokeMsgNotification.getMessage().getUuid();
                        a(revokeMsgNotification, !TextUtils.isEmpty(uuid) && hashSet.contains(uuid));
                    }
                    return;
                }
                return;
            }
            com.qiyukf.nimlib.push.packet.b.c cVarJ = ((u) aVar).j();
            String strC4 = cVarJ.c(10);
            if (TextUtils.isEmpty(MsgDBHelper.queryRevokeMessage(strC4))) {
                com.qiyukf.nimlib.session.d dVar2 = (com.qiyukf.nimlib.session.d) MsgDBHelper.queryMessageByUuid(strC4);
                long jE3 = cVarJ.e(14);
                SessionTypeEnum sessionTypeEnumA2 = a(cVarJ);
                if (dVar2 == null) {
                    dVarA = a(cVarJ, jE3, sessionTypeEnumA2);
                    z = false;
                } else {
                    z = MsgDBHelper.deleteMessage(dVar2) > 0 && com.qiyukf.nimlib.session.k.d((IMMessage) dVar2);
                    dVarA = dVar2;
                }
                MsgDBHelper.saveRevokeMessage(strC4);
                String strC5 = cVarJ.c(5);
                String strC6 = cVarJ.c(16);
                if (strC6 == null) {
                    strC6 = cVarJ.c(3);
                }
                a(new RevokeMsgNotification(dVarA, strC5, strC6, cVarJ.c(4), 0, RevokeType.typeOfValue(cVarJ.d(1)), cVarJ.c(22)), z);
                return;
            }
            return;
        }
        if (aVar.e()) {
            com.qiyukf.nimlib.biz.d.i.o oVar = (com.qiyukf.nimlib.biz.d.i.o) com.qiyukf.nimlib.biz.k.a().a(aVar);
            com.qiyukf.nimlib.session.d dVarI = oVar != null ? oVar.i() : null;
            if (dVarI != null) {
                if (MsgDBHelper.deleteMessage(dVarI) > 0 && com.qiyukf.nimlib.session.k.d((IMMessage) dVarI)) {
                    com.qiyukf.nimlib.session.k.a((IMMessage) dVarI);
                }
                MsgDBHelper.saveRevokeMessage(dVarI.getUuid());
                com.qiyukf.nimlib.session.k.b((IMMessage) dVarI);
            }
        }
        com.qiyukf.nimlib.biz.c.i.a(aVar, null);
    }

    private static SessionTypeEnum a(com.qiyukf.nimlib.push.packet.b.c cVar) {
        SessionTypeEnum sessionTypeEnum = SessionTypeEnum.None;
        int iD = cVar.d(1);
        if (iD != 7) {
            if (iD != 8) {
                switch (iD) {
                    case 12:
                        return SessionTypeEnum.SUPER_TEAM;
                    case 13:
                        break;
                    case 14:
                        break;
                    default:
                        return sessionTypeEnum;
                }
            }
            return SessionTypeEnum.Team;
        }
        return SessionTypeEnum.P2P;
    }

    private static com.qiyukf.nimlib.session.d a(com.qiyukf.nimlib.push.packet.b.c cVar, long j, SessionTypeEnum sessionTypeEnum) {
        String strC = cVar.c(2);
        String strC2 = cVar.c(3);
        if (!TextUtils.isEmpty(strC) && strC.equals(com.qiyukf.nimlib.c.q())) {
            strC = strC2;
        }
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) MessageBuilder.createEmptyMessage(strC, sessionTypeEnum, j);
        dVar.setFromAccount(strC2);
        return dVar;
    }

    private static void a(RevokeMsgNotification revokeMsgNotification, boolean z) {
        IMMessage message = revokeMsgNotification.getMessage();
        if (z) {
            com.qiyukf.nimlib.session.k.a(message);
        }
        com.qiyukf.nimlib.l.d.a(revokeMsgNotification);
        com.qiyukf.nimlib.session.k.b(message);
        com.qiyukf.nimlib.i.b.a(revokeMsgNotification);
    }
}

package com.qiyukf.nimlib.biz.c.j;

import com.qiyukf.nimlib.n.e;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.MsgDeleteSelfOption;
import com.qiyukf.nimlib.session.MsgDBHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: DeleteMsgSelfResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class d extends com.qiyukf.nimlib.biz.c.i {
    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        ArrayList arrayListD;
        if (!aVar.e()) {
            com.qiyukf.nimlib.biz.c.i.a(aVar, null);
            return;
        }
        if (aVar instanceof com.qiyukf.nimlib.biz.e.k.f) {
            com.qiyukf.nimlib.biz.e.k.f fVar = (com.qiyukf.nimlib.biz.e.k.f) aVar;
            long j = fVar.j();
            com.qiyukf.nimlib.biz.n.s(j);
            com.qiyukf.nimlib.biz.c.i.a(fVar, Long.valueOf(j));
            return;
        }
        if (!(aVar instanceof com.qiyukf.nimlib.biz.e.k.e)) {
            if (aVar instanceof com.qiyukf.nimlib.biz.e.k.h) {
                com.qiyukf.nimlib.biz.e.k.h hVar = (com.qiyukf.nimlib.biz.e.k.h) aVar;
                long j2 = hVar.j();
                com.qiyukf.nimlib.biz.n.s(j2);
                com.qiyukf.nimlib.biz.c.i.a(hVar, Long.valueOf(j2));
                return;
            }
            if (!(aVar instanceof com.qiyukf.nimlib.biz.e.k.g)) {
                if (aVar instanceof com.qiyukf.nimlib.biz.e.g.c) {
                    a((com.qiyukf.nimlib.biz.e.g.c) aVar);
                    return;
                }
                return;
            } else {
                MsgDeleteSelfOption msgDeleteSelfOption = new MsgDeleteSelfOption(((com.qiyukf.nimlib.biz.e.k.g) aVar).j());
                com.qiyukf.nimlib.biz.n.s(msgDeleteSelfOption.getTime());
                a(msgDeleteSelfOption);
                return;
            }
        }
        List<com.qiyukf.nimlib.push.packet.b.c> listJ = ((com.qiyukf.nimlib.biz.e.k.e) aVar).j();
        if (com.qiyukf.nimlib.n.e.b((Collection) listJ)) {
            com.qiyukf.nimlib.i.b.f(new ArrayList(0));
            return;
        }
        ArrayList arrayListD2 = com.qiyukf.nimlib.n.e.d(listJ, new d$$ExternalSyntheticLambda0());
        com.qiyukf.nimlib.biz.n.s(((MsgDeleteSelfOption) arrayListD2.get(0)).getTime());
        if (com.qiyukf.nimlib.n.e.b((Collection) arrayListD2)) {
            arrayListD = new ArrayList(0);
        } else {
            MsgDeleteSelfOption msgDeleteSelfOption2 = (MsgDeleteSelfOption) arrayListD2.get(0);
            if (MsgDBHelper.queryRecentContact(msgDeleteSelfOption2.getSessionId(), msgDeleteSelfOption2.getType()) == null) {
                arrayListD = new ArrayList(0);
            } else {
                arrayListD = com.qiyukf.nimlib.n.e.d(arrayListD2, new e.a() { // from class: com.qiyukf.nimlib.biz.c.j.d$$ExternalSyntheticLambda1
                    @Override // com.qiyukf.nimlib.n.e.a
                    public final Object transform(Object obj) {
                        return this.f$0.b((MsgDeleteSelfOption) obj);
                    }
                });
                com.qiyukf.nimlib.session.k.b(arrayListD, false);
            }
        }
        com.qiyukf.nimlib.i.b.f(arrayListD);
    }

    private void a(com.qiyukf.nimlib.biz.e.g.c cVar) {
        int i;
        MsgDeleteSelfOption msgDeleteSelfOption;
        List<com.qiyukf.nimlib.push.packet.b.c> listJ = cVar.j();
        ArrayList arrayListA = com.qiyukf.nimlib.n.e.a((Collection) listJ, true, (e.a) new d$$ExternalSyntheticLambda0());
        Comparator comparator = new Comparator() { // from class: com.qiyukf.nimlib.biz.c.j.d$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return d.a((MsgDeleteSelfOption) obj, (MsgDeleteSelfOption) obj2);
            }
        };
        if (com.qiyukf.nimlib.n.e.b((Collection) arrayListA)) {
            i = -1;
        } else {
            Iterator it = arrayListA.iterator();
            Object next = it.next();
            i = 0;
            int i2 = 1;
            while (it.hasNext()) {
                Object next2 = it.next();
                if (comparator.compare(next, next2) < 0) {
                    i = i2;
                    next = next2;
                }
                i2++;
            }
        }
        if (i >= 0 && i < arrayListA.size() && (msgDeleteSelfOption = (MsgDeleteSelfOption) arrayListA.get(i)) != null) {
            com.qiyukf.nimlib.biz.n.s(msgDeleteSelfOption.getTime());
        }
        Iterator<com.qiyukf.nimlib.push.packet.b.c> it2 = listJ.iterator();
        while (it2.hasNext()) {
            a(new MsgDeleteSelfOption(it2.next()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(MsgDeleteSelfOption msgDeleteSelfOption, MsgDeleteSelfOption msgDeleteSelfOption2) {
        if (msgDeleteSelfOption == null && msgDeleteSelfOption2 == null) {
            return 0;
        }
        if (msgDeleteSelfOption == null) {
            return -1;
        }
        if (msgDeleteSelfOption2 == null) {
            return 1;
        }
        return Long.compare(msgDeleteSelfOption.getTime(), msgDeleteSelfOption2.getTime());
    }

    private void a(MsgDeleteSelfOption msgDeleteSelfOption) {
        com.qiyukf.nimlib.session.d dVarB = b(msgDeleteSelfOption);
        if (dVarB == null) {
            com.qiyukf.nimlib.log.c.b.a.d("DeleteMsgSelfResponseHandler", "deleteLocalMsgAndNotify with empty message");
            com.qiyukf.nimlib.i.b.b((com.qiyukf.nimlib.session.d) null);
        } else {
            MsgDBHelper.deleteMessage(dVarB);
            com.qiyukf.nimlib.session.k.b((IMMessage) dVarB);
            com.qiyukf.nimlib.i.b.b(dVarB);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.qiyukf.nimlib.session.d b(MsgDeleteSelfOption msgDeleteSelfOption) {
        com.qiyukf.nimlib.session.d dVar = null;
        if (msgDeleteSelfOption == null) {
            return null;
        }
        String sessionId = msgDeleteSelfOption.getSessionId();
        SessionTypeEnum type = msgDeleteSelfOption.getType();
        String deleteMsgClientId = msgDeleteSelfOption.getDeleteMsgClientId();
        if (sessionId != null && type != null && deleteMsgClientId != null) {
            dVar = (com.qiyukf.nimlib.session.d) MessageBuilder.createEmptyMessage(sessionId, type, msgDeleteSelfOption.getDeleteMsgCreateTime());
            dVar.a(msgDeleteSelfOption.getDeleteMsgClientId());
            dVar.setFromAccount(msgDeleteSelfOption.getFrom());
            try {
                dVar.c(Long.parseLong(msgDeleteSelfOption.getDeleteMsgServerId()));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return dVar;
    }
}

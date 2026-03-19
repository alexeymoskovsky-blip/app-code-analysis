package com.qiyukf.nimlib.biz.c.k;

import com.qiyukf.nimlib.biz.c.i;
import com.qiyukf.nimlib.biz.d.j.j;
import com.qiyukf.nimlib.biz.e.l.k;
import com.qiyukf.nimlib.n.e;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.session.ad;
import com.qiyukf.nimlib.session.h;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: ThreadTalkResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class e extends i {
    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        if (!aVar.e()) {
            i.a(aVar, null);
            return;
        }
        if (aVar instanceof k) {
            k kVar = (k) aVar;
            boolean zI = ((j) com.qiyukf.nimlib.biz.k.a().a(kVar)).i();
            com.qiyukf.nimlib.push.packet.b.c cVarJ = kVar.j();
            com.qiyukf.nimlib.session.d dVarA = h.a(cVarJ, false);
            if (dVarA == null) {
                com.qiyukf.nimlib.log.c.b.a.d("ThreadTalkResponseHandler", "failed to convert from Property to , uuid is " + cVarJ.c(11));
            }
            List<com.qiyukf.nimlib.push.packet.b.c> listM = kVar.m();
            ArrayList arrayList = new ArrayList(listM.size());
            for (com.qiyukf.nimlib.push.packet.b.c cVar : listM) {
                com.qiyukf.nimlib.session.d dVarA2 = h.a(cVar, false);
                if (dVarA2 == null) {
                    com.qiyukf.nimlib.log.c.b.a.d("ThreadTalkResponseHandler", "failed to convert from Property to IMMessage, uuid is " + cVar.c(11));
                } else {
                    arrayList.add(dVarA2);
                }
            }
            if (zI) {
                List listE = com.qiyukf.nimlib.n.e.e(arrayList, new e.a() { // from class: com.qiyukf.nimlib.biz.c.k.e$$ExternalSyntheticLambda0
                    @Override // com.qiyukf.nimlib.n.e.a
                    public final Object transform(Object obj) {
                        return e.a((com.qiyukf.nimlib.session.d) obj);
                    }
                });
                if (dVarA != null && h.a(dVarA.getUuid()) && !dVarA.isDeleted()) {
                    listE.add(dVarA);
                }
                MsgDBHelper.saveMessages(listE);
            }
            i.a(kVar, new ad(dVarA, kVar.l(), kVar.k(), arrayList));
            return;
        }
        if (aVar instanceof com.qiyukf.nimlib.biz.e.l.i) {
            a((com.qiyukf.nimlib.biz.e.l.i) aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(com.qiyukf.nimlib.session.d dVar) {
        return Boolean.valueOf((dVar == null || !h.a(dVar.getUuid()) || dVar.isDeleted()) ? false : true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(IMMessage iMMessage) {
        return iMMessage == null ? "" : iMMessage.getUuid();
    }

    private static void a(com.qiyukf.nimlib.biz.e.l.i iVar) {
        boolean zI = ((com.qiyukf.nimlib.biz.d.j.h) com.qiyukf.nimlib.biz.k.a().a(iVar)).i();
        List<com.qiyukf.nimlib.push.packet.b.c> listJ = iVar.j();
        ArrayList arrayList = new ArrayList(listJ.size());
        for (com.qiyukf.nimlib.push.packet.b.c cVar : listJ) {
            if (cVar == null) {
                com.qiyukf.nimlib.log.c.b.a.d("ThreadTalkResponseHandler", "with null in the received property list");
            } else {
                com.qiyukf.nimlib.session.d dVarA = h.a(cVar, false);
                if (dVarA == null) {
                    com.qiyukf.nimlib.log.c.b.a.d("ThreadTalkResponseHandler", "failed to convert from Property to IMMessage, uuid is " + cVar.c(11));
                } else {
                    if (zI && h.a(dVarA.getUuid()) && !dVarA.isDeleted()) {
                        MsgDBHelper.saveMessage(dVarA);
                    }
                    arrayList.add(dVarA);
                }
            }
        }
        com.qiyukf.nimlib.log.c.b.a.d("ThreadTalkResponseHandler", "onQueryHistoryByIdsResponse, " + com.qiyukf.nimlib.n.e.a(arrayList, ", ", new e.a() { // from class: com.qiyukf.nimlib.biz.c.k.e$$ExternalSyntheticLambda1
            @Override // com.qiyukf.nimlib.n.e.a
            public final Object transform(Object obj) {
                return e.a((IMMessage) obj);
            }
        }));
        i.a(iVar, arrayList);
    }
}

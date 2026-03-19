package com.qiyukf.nimlib.biz.c.j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomMessageConfig;
import com.qiyukf.nimlib.sdk.msg.model.GetMessagesDynamicallyParam;
import com.qiyukf.nimlib.sdk.team.model.IMMessageFilter;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.session.x;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: compiled from: CloudMsgHistoryResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class c extends com.qiyukf.nimlib.biz.c.i {

    /* JADX INFO: compiled from: CloudMsgHistoryResponseHandler.java */
    public static abstract class a {
        public abstract void a(int i, @Nullable ArrayList<com.qiyukf.nimlib.session.d> arrayList);

        public void a(@Nullable ArrayList<com.qiyukf.nimlib.session.d> arrayList) {
        }
    }

    public static void a(@NonNull com.qiyukf.nimlib.biz.e.a aVar, @Nullable com.qiyukf.nimlib.biz.d.i.k kVar, @Nullable a aVar2) {
        int iB = 0;
        if (!aVar.e() || kVar == null) {
            aVar2.a(null);
            aVar2.a(aVar.h(), null);
            return;
        }
        List<com.qiyukf.nimlib.push.packet.b.c> listJ = ((com.qiyukf.nimlib.biz.e.k.n) aVar).j();
        ArrayList<com.qiyukf.nimlib.session.d> arrayList = new ArrayList<>(listJ.size());
        ArrayList arrayList2 = new ArrayList();
        HashSet hashSet = new HashSet(listJ.size());
        for (com.qiyukf.nimlib.push.packet.b.c cVar : listJ) {
            if (!hashSet.contains(cVar.c(11))) {
                com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.session.h.a(cVar, false);
                IMMessageFilter iMMessageFilterM = kVar.m();
                if (dVarA != null && (iMMessageFilterM == null || !iMMessageFilterM.shouldIgnore(dVarA))) {
                    arrayList.add(dVarA);
                    hashSet.add(dVarA.getUuid());
                    if (kVar.j() && MsgDBHelper.queryMessageIdByUuid(dVarA.getUuid()) == 0) {
                        arrayList2.add(dVarA);
                    } else if (kVar.j()) {
                        dVarA.setStatus(MsgStatusEnum.statusOfValue(MsgDBHelper.queryStatus(dVarA.getUuid(), false)));
                        if (dVarA.getAttachment() instanceof FileAttachment) {
                            dVarA.setAttachStatus(AttachStatusEnum.statusOfValue(MsgDBHelper.queryStatus(dVarA.getUuid(), true)));
                        }
                    }
                }
            }
        }
        com.qiyukf.nimlib.session.h.a(arrayList);
        if (com.qiyukf.nimlib.n.e.b((Collection) arrayList)) {
            com.qiyukf.nimlib.log.c.b.a.d("CloudMsgHistoryResponseHandler", "empty result");
        } else {
            com.qiyukf.nimlib.session.d dVar = arrayList.get(0);
            String uuid = "";
            String uuid2 = dVar == null ? "" : dVar.getUuid();
            com.qiyukf.nimlib.session.d dVar2 = arrayList.get(arrayList.size() - 1);
            if (dVar2 != null) {
                uuid = dVar2.getUuid();
            }
            com.qiyukf.nimlib.log.c.b.a.d("CloudMsgHistoryResponseHandler", String.format("first msg is %s; last msg is %s", uuid2, uuid));
        }
        aVar2.a(arrayList);
        if (arrayList2.size() > 0) {
            List<com.qiyukf.nimlib.session.d> listA = com.qiyukf.nimlib.session.k.a(arrayList2, kVar.l());
            if (!listA.isEmpty()) {
                com.qiyukf.nimlib.session.d dVar3 = listA.get(0);
                String sessionId = dVar3.getSessionId();
                SessionTypeEnum sessionType = dVar3.getSessionType();
                com.qiyukf.nimlib.session.s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(sessionId, sessionType);
                int unreadCount = sVarQueryRecentContact == null ? 0 : sVarQueryRecentContact.getUnreadCount();
                if (kVar.n()) {
                    iB = x.b(sessionId, sessionType) - unreadCount;
                } else {
                    for (com.qiyukf.nimlib.session.d dVar4 : listA) {
                        CustomMessageConfig config = dVar4.getConfig();
                        if (config == null) {
                            config = new CustomMessageConfig();
                        }
                        config.enableUnreadCount = false;
                        dVar4.setConfig(config);
                    }
                }
                MsgDBHelper.saveMessages(listA);
                com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.session.k.a(dVar3, iB));
            }
        }
        aVar2.a(aVar.h(), arrayList);
    }

    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(final com.qiyukf.nimlib.biz.e.a aVar) {
        final com.qiyukf.nimlib.biz.d.i.k kVar = (com.qiyukf.nimlib.biz.d.i.k) com.qiyukf.nimlib.biz.k.a().a(aVar);
        a(aVar, kVar, new a() { // from class: com.qiyukf.nimlib.biz.c.j.c.1
            @Override // com.qiyukf.nimlib.biz.c.j.c.a
            public final void a(@Nullable ArrayList<com.qiyukf.nimlib.session.d> arrayList) {
                super.a(arrayList);
                com.qiyukf.nimlib.biz.c.i.a(aVar, arrayList);
            }

            @Override // com.qiyukf.nimlib.biz.c.j.c.a
            public final void a(int i, @Nullable ArrayList<com.qiyukf.nimlib.session.d> arrayList) {
                com.qiyukf.nimlib.biz.d.i.k kVar2 = kVar;
                if (kVar2 != null && !kVar2.i() && com.qiyukf.nimlib.n.e.c((Collection) arrayList)) {
                    ArrayList<com.qiyukf.nimlib.session.d> arrayList2 = new ArrayList<>(arrayList);
                    Collections.reverse(arrayList2);
                    arrayList = arrayList2;
                }
                com.qiyukf.nimlib.session.b.c.a().a(kVar, (GetMessagesDynamicallyParam) null, arrayList, (com.qiyukf.nimlib.i.j) null);
            }
        });
    }
}

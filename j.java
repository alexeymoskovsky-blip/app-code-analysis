package com.qiyukf.nimlib.biz.c.j;

import android.util.Pair;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.enums.NIMMessageAIStreamStatus;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.NIMMessageAIConfig;
import com.qiyukf.nimlib.session.MsgDBHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: ModifyMsgRoamNotifyHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class j extends com.qiyukf.nimlib.biz.c.i {
    private Map<Short, com.qiyukf.nimlib.biz.d.a> a = new ConcurrentHashMap();

    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        com.qiyukf.nimlib.session.d dVar;
        com.qiyukf.nimlib.session.s sVarD;
        com.qiyukf.nimlib.session.d dVar2;
        if (!aVar.e()) {
            com.qiyukf.nimlib.biz.c.i.a(aVar, null);
            return;
        }
        com.qiyukf.nimlib.biz.e.g.a aVar2 = (com.qiyukf.nimlib.biz.e.g.a) aVar;
        List<com.qiyukf.nimlib.push.packet.b.c> listJ = aVar2.j();
        long jK = aVar2.k();
        Map<String, IMMessage> mapQueryMsgMapByProperty = MsgDBHelper.queryMsgMapByProperty(listJ);
        ArrayList arrayList = new ArrayList();
        com.qiyukf.nimlib.c.q();
        Iterator<com.qiyukf.nimlib.push.packet.b.c> it = listJ.iterator();
        while (it.hasNext()) {
            com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.session.h.a(it.next(), false);
            if (dVarA != null && (dVar2 = (com.qiyukf.nimlib.session.d) mapQueryMsgMapByProperty.get(dVarA.getUuid())) != null) {
                if (dVar2.getModifyTime() >= dVarA.getModifyTime()) {
                    com.qiyukf.nimlib.log.c.b.a.d("ModifyMsgRoamNotifyHandler", dVar2.getUuid() + " modify msg time:" + dVar2.getModifyTime() + " is newer than server:" + dVarA.getModifyTime() + ", ignore it.");
                } else {
                    dVarA.setStatus(dVar2.getStatus());
                    arrayList.add(dVarA);
                }
            }
        }
        List<com.qiyukf.nimlib.session.d> listA = com.qiyukf.nimlib.session.k.a((List<com.qiyukf.nimlib.session.d>) arrayList, false);
        MsgDBHelper.insertOrUpdateMessages(listA, true);
        ArrayList<com.qiyukf.nimlib.session.d> arrayList2 = new ArrayList();
        arrayList2.addAll(listA);
        HashMap map = new HashMap();
        HashSet<Pair> hashSet = new HashSet();
        for (com.qiyukf.nimlib.session.d dVar3 : arrayList2) {
            map.put(dVar3.getUuid(), dVar3);
            hashSet.add(new Pair(dVar3.getSessionId(), dVar3.getSessionType()));
            NIMMessageAIConfig aIConfig = dVar3.getAIConfig();
            if (aIConfig != null && aIConfig.getAIStreamStatus().getValue() > NIMMessageAIStreamStatus.NIM_MESSAGE_AI_STREAM_STATUS_PLACEHOLDER.getValue()) {
                com.qiyukf.nimlib.session.e.a();
                com.qiyukf.nimlib.session.e.a().q(dVar3.getUuid());
                com.qiyukf.nimlib.session.e.a().o(dVar3.getUuid());
                com.qiyukf.nimlib.session.e.a().i(dVar3.getUuid());
                com.qiyukf.nimlib.session.e.a().l(dVar3.getUuid());
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Pair pair : hashSet) {
            com.qiyukf.nimlib.session.s sVarQueryRecentContact = MsgDBHelper.queryRecentContact((String) pair.first, (SessionTypeEnum) pair.second);
            if (sVarQueryRecentContact != null && (dVar = (com.qiyukf.nimlib.session.d) map.get(sVarQueryRecentContact.getRecentMessageId())) != null && (sVarD = com.qiyukf.nimlib.session.k.d(dVar)) != null) {
                arrayList3.add(sVarD);
            }
        }
        if (!arrayList3.isEmpty()) {
            com.qiyukf.nimlib.i.b.e(arrayList3);
        }
        if (aVar2.a().i() == 28) {
            com.qiyukf.nimlib.biz.n.q(jK);
        } else {
            com.qiyukf.nimlib.biz.n.p(jK);
        }
        com.qiyukf.nimlib.i.b.n(arrayList2);
    }
}

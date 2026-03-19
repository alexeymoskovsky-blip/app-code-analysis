package com.qiyukf.nimlib.biz.c.e;

import android.os.SystemClock;
import com.petkit.android.utils.Constants;
import com.qiyukf.nimlib.biz.c.i;
import com.qiyukf.nimlib.biz.e.g.k;
import com.qiyukf.nimlib.n.e;
import com.qiyukf.nimlib.report.q;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.session.d;
import com.qiyukf.nimlib.session.h;
import com.qiyukf.nimlib.session.s;
import com.qiyukf.nimlib.session.w;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: compiled from: SyncUnreadMsgResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public class c extends i {
    public void a(com.qiyukf.nimlib.push.packet.b.c cVar) {
    }

    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        s sVarA;
        IMMessage iMMessage;
        String str;
        ArrayList arrayList;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        com.qiyukf.nimlib.push.packet.a aVarA = aVar.a();
        com.qiyukf.nimlib.log.c.b.a.d("SyncUnreadMsgResponseHandler", "SyncUnreadMsgResponse processResponse IN,".concat(String.valueOf(aVarA != null ? aVarA.toString() : null)));
        if (aVar.e()) {
            boolean zB = b(aVar);
            List<com.qiyukf.nimlib.push.packet.b.c> listJ = ((k) aVar).j();
            if (listJ == null || listJ.size() == 0) {
                q.a(aVarA, 0);
                return;
            }
            com.qiyukf.nimlib.log.c.b.a.d("SyncUnreadMsgResponseHandler", "current msg size = " + listJ.size());
            ArrayList arrayList2 = new ArrayList();
            HashSet hashSet = new HashSet();
            int size = listJ.size();
            while (true) {
                size--;
                if (size >= 0) {
                    com.qiyukf.nimlib.push.packet.b.c cVar = listJ.get(size);
                    a(cVar);
                    if (!hashSet.contains(cVar.c(11))) {
                        hashSet.add(cVar.c(11));
                        arrayList2.add(cVar);
                    }
                } else {
                    try {
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            com.qiyukf.nimlib.session.b.c.a().a(listJ, zB ? com.qiyukf.nimlib.session.b.a.OFFLINE : com.qiyukf.nimlib.session.b.a.ROAM);
            Set<String> setQueryExistUuidsByUuids = MsgDBHelper.queryExistUuidsByUuids(arrayList2);
            com.qiyukf.nimlib.log.c.b.a.d("SyncUnreadMsgResponseHandler", " msg has exist = ".concat(String.valueOf(setQueryExistUuidsByUuids)));
            HashMap map = new HashMap();
            String strQ = com.qiyukf.nimlib.c.q();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            ArrayList<d> arrayList5 = new ArrayList();
            ArrayList arrayList6 = new ArrayList();
            ArrayList arrayList7 = new ArrayList(e.d(listJ));
            HashMap map2 = new HashMap();
            int size2 = arrayList2.size() - 1;
            while (size2 >= 0) {
                long j = jElapsedRealtime;
                com.qiyukf.nimlib.push.packet.b.c cVar2 = arrayList2.get(size2);
                String strC = cVar2.c(2);
                ArrayList arrayList8 = arrayList2;
                String strC2 = cVar2.c(11);
                boolean zEquals = Objects.equals(strC, strQ);
                d dVarA = h.a(cVar2);
                arrayList7.add(dVarA);
                if (com.qiyukf.nimlib.session.k.c((IMMessage) dVarA)) {
                    com.qiyukf.nimlib.log.c.b.a.d("SyncUnreadMsgResponseHandler", "IMMessageFilter ignore received message, uuid= " + dVarA.getUuid());
                } else {
                    if (!zEquals) {
                        if (!setQueryExistUuidsByUuids.contains(strC2)) {
                            String sessionId = dVarA.getSessionId();
                            ArrayList arrayList9 = (ArrayList) map2.get(sessionId);
                            str = strQ;
                            if (arrayList9 == null) {
                                arrayList = new ArrayList();
                                map2.put(sessionId, arrayList);
                            } else {
                                arrayList = arrayList9;
                            }
                            arrayList.add(dVarA);
                            arrayList3.add(dVarA);
                            arrayList4.add(cVar2);
                            map.put(strC, dVarA.b());
                        }
                    } else {
                        str = strQ;
                        if (setQueryExistUuidsByUuids.contains(strC2)) {
                            arrayList5.add(dVarA);
                            arrayList6.add(cVar2);
                        } else {
                            String sessionId2 = dVarA.getSessionId();
                            ArrayList arrayList10 = (ArrayList) map2.get(sessionId2);
                            if (arrayList10 == null) {
                                arrayList10 = new ArrayList();
                                map2.put(sessionId2, arrayList10);
                            }
                            arrayList10.add(dVarA);
                            arrayList3.add(dVarA);
                            arrayList4.add(cVar2);
                        }
                    }
                    size2--;
                    arrayList2 = arrayList8;
                    jElapsedRealtime = j;
                    strQ = str;
                }
                str = strQ;
                size2--;
                arrayList2 = arrayList8;
                jElapsedRealtime = j;
                strQ = str;
            }
            long j2 = jElapsedRealtime;
            ArrayList arrayList11 = arrayList2;
            if (MsgDBHelper.saveMessages(arrayList3)) {
                com.qiyukf.nimlib.session.b.c.a().b(arrayList7, zB ? com.qiyukf.nimlib.session.b.a.OFFLINE : com.qiyukf.nimlib.session.b.a.ROAM);
            }
            w.a.a.a(arrayList3);
            Map<String, IMMessage> mapQueryMsgMapByProperty = MsgDBHelper.queryMsgMapByProperty(arrayList6);
            MsgDBHelper.updateSyncSelfMessageStatus(arrayList5);
            for (d dVar : arrayList5) {
                if (dVar != null && (iMMessage = mapQueryMsgMapByProperty.get(dVar.getUuid())) != null && (iMMessage.getStatus() != MsgStatusEnum.success || iMMessage.isInBlackList() != dVar.isInBlackList())) {
                    com.qiyukf.nimlib.i.b.a(dVar);
                }
            }
            com.qiyukf.nimlib.session.k.a((List<com.qiyukf.nimlib.push.packet.b.c>) arrayList4);
            com.qiyukf.nimlib.session.k.c(arrayList3);
            ArrayList arrayList12 = new ArrayList();
            for (String str2 : map2.keySet()) {
                ArrayList arrayList13 = (ArrayList) map2.get(str2);
                com.qiyukf.nimlib.log.c.b.a.d("SyncUnreadMsgResponseHandler", "session id = ".concat(String.valueOf(str2)));
                if (!e.b((Collection) arrayList13)) {
                    if (com.qiyukf.nimlib.c.h().sessionReadAck || zB) {
                        sVarA = h.a((ArrayList<d>) arrayList13, (String) map.get(str2), false);
                    } else {
                        h.a((ArrayList<d>) arrayList13);
                        com.qiyukf.nimlib.i.b.b(arrayList13);
                        sVarA = com.qiyukf.nimlib.session.k.a((d) arrayList13.get(arrayList13.size() - 1));
                    }
                    if (sVarA != null) {
                        arrayList12.add(sVarA);
                    }
                }
            }
            if (!arrayList12.isEmpty()) {
                com.qiyukf.nimlib.i.b.e(arrayList12);
            }
            if (zB) {
                a(arrayList11, aVar.c());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("received ");
            sb.append(zB ? Constants.HOME_TODO_CARD_DEVICE_ERR_OFFLINE : "roaming");
            sb.append(" messages, count=" + arrayList3.size());
            com.qiyukf.nimlib.log.c.b.a.H(sb.toString());
            com.qiyukf.nimlib.log.c.b.a.d("SyncUnreadMsgResponseHandler", "SyncUnreadMsgResponse processResponse OUT,cost = " + (SystemClock.elapsedRealtime() - j2) + ChineseToPinyinResource.Field.COMMA + aVarA);
            q.a(aVarA, listJ.size());
        }
    }

    public boolean b(com.qiyukf.nimlib.biz.e.a aVar) {
        return aVar.g() == 4;
    }

    public void a(List<com.qiyukf.nimlib.push.packet.b.c> list, int i) {
        ArrayList arrayList = new ArrayList(list.size());
        HashMap map = new HashMap();
        for (com.qiyukf.nimlib.push.packet.b.c cVar : list) {
            long jE = cVar.e(12);
            int iD = cVar.d(0);
            if (iD == 0) {
                arrayList.add(Long.valueOf(jE));
            } else if (iD == 1) {
                String strC = cVar.c(1);
                Long l = (Long) map.get(strC);
                if (l == null || l.longValue() < jE) {
                    map.put(strC, Long.valueOf(jE));
                }
            }
        }
        if (!arrayList.isEmpty()) {
            com.qiyukf.nimlib.biz.d.f.a aVar = new com.qiyukf.nimlib.biz.d.f.a();
            aVar.a((byte) 7);
            aVar.b((byte) 2);
            aVar.a((List<Long>) arrayList);
            com.qiyukf.nimlib.biz.k.a().a(aVar, com.qiyukf.nimlib.biz.g.a.d);
        }
        if (map.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList(map.values());
        com.qiyukf.nimlib.biz.d.f.a aVar2 = new com.qiyukf.nimlib.biz.d.f.a();
        aVar2.a((byte) 8);
        aVar2.b((byte) 3);
        aVar2.a((List<Long>) arrayList2);
        if (i > 0) {
            aVar2.a(i);
        }
        com.qiyukf.nimlib.biz.k.a().a(aVar2, com.qiyukf.nimlib.biz.g.a.d);
    }
}

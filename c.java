package com.qiyukf.nimlib.biz.c.k;

import android.text.TextUtils;
import com.qiyukf.nimlib.biz.c.i;
import com.qiyukf.nimlib.biz.d.j.m;
import com.qiyukf.nimlib.biz.e.l.f;
import com.qiyukf.nimlib.biz.e.l.j;
import com.qiyukf.nimlib.biz.e.l.o;
import com.qiyukf.nimlib.biz.e.l.p;
import com.qiyukf.nimlib.biz.k;
import com.qiyukf.nimlib.sdk.msg.model.HandleQuickCommentOption;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.MessageKey;
import com.qiyukf.nimlib.sdk.msg.model.QuickCommentOption;
import com.qiyukf.nimlib.sdk.msg.model.QuickCommentOptionWrapper;
import com.qiyukf.nimlib.session.MsgDBHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: QuickCommentResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class c extends i {
    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        List<IMMessage> arrayList;
        if (!aVar.e()) {
            i.a(aVar, null);
            return;
        }
        if (aVar instanceof f) {
            f fVar = (f) aVar;
            com.qiyukf.nimlib.biz.d.j.c cVar = (com.qiyukf.nimlib.biz.d.j.c) k.a().a(fVar);
            if (cVar == null) {
                com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "retrieveRequest failed");
                i.a(fVar, null);
                return;
            }
            long j = fVar.j();
            com.qiyukf.nimlib.session.d dVarA = a(cVar.i());
            if (dVarA == null) {
                com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "add: msg not in db");
                i.a(fVar, null);
                return;
            } else if (!dVarA.o()) {
                com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "add: sync quick comment to enable the table");
                i.a(fVar, null);
                return;
            } else {
                a(dVarA, j);
                a(dVarA.getUuid(), new QuickCommentOption(com.qiyukf.nimlib.c.q(), cVar.j(), j, cVar.k()));
                i.a(fVar, null);
                return;
            }
        }
        if (aVar instanceof p) {
            p pVar = (p) aVar;
            m mVar = (m) k.a().a(pVar);
            if (mVar == null || mVar.i() == null) {
                com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "retrieveRequest failed");
                i.a(pVar, null);
                return;
            }
            long j2 = pVar.j();
            com.qiyukf.nimlib.session.d dVarA2 = a(mVar.i());
            if (dVarA2 == null) {
                com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "remove: msg not in db");
                i.a(pVar, null);
                return;
            } else if (!dVarA2.o()) {
                com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "remove: sync quick comment to enable the table");
                i.a(pVar, null);
                return;
            } else {
                a(dVarA2, j2);
                MsgDBHelper.deleteQuickComment(dVarA2.getUuid(), com.qiyukf.nimlib.c.q(), mVar.j());
                i.a(pVar, null);
                return;
            }
        }
        if (aVar instanceof com.qiyukf.nimlib.biz.e.l.e) {
            com.qiyukf.nimlib.biz.e.l.e eVar = (com.qiyukf.nimlib.biz.e.l.e) aVar;
            HandleQuickCommentOption handleQuickCommentOption = new HandleQuickCommentOption(eVar.j(), eVar.k());
            MessageKey key = handleQuickCommentOption.getKey();
            QuickCommentOption commentOption = handleQuickCommentOption.getCommentOption();
            if (key != null && commentOption != null) {
                String uuid = key.getUuid();
                com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) MsgDBHelper.queryMessageByUuid(uuid);
                if (dVar != null && dVar.o()) {
                    com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "notify add: msg not exist or has not sync yet");
                    a(uuid, commentOption);
                    a(dVar, commentOption.getTime());
                }
            }
            com.qiyukf.nimlib.i.b.a(handleQuickCommentOption);
            return;
        }
        if (aVar instanceof o) {
            o oVar = (o) aVar;
            HandleQuickCommentOption handleQuickCommentOption2 = new HandleQuickCommentOption(oVar.j(), oVar.k());
            MessageKey key2 = handleQuickCommentOption2.getKey();
            QuickCommentOption commentOption2 = handleQuickCommentOption2.getCommentOption();
            if (key2 != null && commentOption2 != null) {
                String uuid2 = key2.getUuid();
                com.qiyukf.nimlib.session.d dVar2 = (com.qiyukf.nimlib.session.d) MsgDBHelper.queryMessageByUuid(uuid2);
                if (dVar2 != null && dVar2.o()) {
                    com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "notify remove: msg not exist or has not sync yet");
                    MsgDBHelper.deleteQuickComment(uuid2, commentOption2.getFromAccount(), commentOption2.getReplyType());
                    a(dVar2, commentOption2.getTime());
                }
            }
            com.qiyukf.nimlib.i.b.b(handleQuickCommentOption2);
            return;
        }
        if (aVar instanceof j) {
            j jVar = (j) aVar;
            com.qiyukf.nimlib.biz.d.j.i iVar = (com.qiyukf.nimlib.biz.d.j.i) k.a().a(jVar);
            if (iVar == null || (arrayList = iVar.i()) == null) {
                arrayList = new ArrayList<>(0);
            }
            int size = arrayList.size();
            HashMap<String, QuickCommentOptionWrapper> mapA = a(jVar);
            ArrayList arrayList2 = new ArrayList(size);
            Iterator<IMMessage> it = arrayList.iterator();
            while (it.hasNext()) {
                com.qiyukf.nimlib.session.d dVarA3 = a(it.next());
                if (dVarA3 != null) {
                    String uuid3 = dVarA3.getUuid();
                    QuickCommentOptionWrapper quickCommentOptionWrapper = mapA.get(uuid3);
                    if (quickCommentOptionWrapper == null || !quickCommentOptionWrapper.isModify()) {
                        quickCommentOptionWrapper = new QuickCommentOptionWrapper(dVarA3.getMessageKey(), MsgDBHelper.queryQuickCommentByUuid(uuid3), false, quickCommentOptionWrapper == null ? dVarA3.getQuickCommentUpdateTime() : quickCommentOptionWrapper.getTime());
                    } else {
                        MsgDBHelper.deleteQuickComment(uuid3);
                        MsgDBHelper.saveQuickComment(uuid3, quickCommentOptionWrapper.getQuickCommentList());
                    }
                    a(dVarA3, quickCommentOptionWrapper.getTime());
                    arrayList2.add(quickCommentOptionWrapper);
                }
            }
            i.a(jVar, arrayList2);
        }
    }

    private static HashMap<String, QuickCommentOptionWrapper> a(j jVar) {
        List<com.qiyukf.nimlib.push.packet.b.c> listJ = jVar.j();
        if (listJ == null) {
            listJ = new ArrayList<>(0);
        }
        HashMap<String, QuickCommentOptionWrapper> map = new HashMap<>(listJ.size() << 1);
        Iterator<com.qiyukf.nimlib.push.packet.b.c> it = listJ.iterator();
        while (it.hasNext()) {
            QuickCommentOptionWrapper quickCommentOptionWrapperFromProperty = QuickCommentOptionWrapper.fromProperty(it.next());
            MessageKey key = quickCommentOptionWrapperFromProperty.getKey();
            if (key != null) {
                String uuid = key.getUuid();
                if (!TextUtils.isEmpty(uuid)) {
                    map.put(uuid, quickCommentOptionWrapperFromProperty);
                }
            }
        }
        return map;
    }

    private static void a(com.qiyukf.nimlib.session.d dVar, long j) {
        com.qiyukf.nimlib.log.c.b.a.d("QuickCommentResponseHandler", "do update time tag, time=".concat(String.valueOf(j)));
        dVar.d(j);
        MsgDBHelper.updateMessage(dVar);
    }

    private static void a(String str, QuickCommentOption quickCommentOption) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(quickCommentOption);
        MsgDBHelper.saveQuickComment(str, arrayList);
    }

    private static com.qiyukf.nimlib.session.d a(IMMessage iMMessage) {
        if (iMMessage != null) {
            String uuid = iMMessage.getUuid();
            if (!TextUtils.isEmpty(uuid)) {
                IMMessage iMMessageQueryMessageByUuid = MsgDBHelper.queryMessageByUuid(uuid);
                if (iMMessageQueryMessageByUuid instanceof com.qiyukf.nimlib.session.d) {
                    return (com.qiyukf.nimlib.session.d) iMMessageQueryMessageByUuid;
                }
            }
        }
        return null;
    }
}

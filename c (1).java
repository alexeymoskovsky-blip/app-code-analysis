package com.qiyukf.nimlib.biz.c.a;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.qiyukf.module.log.entry.LogConstants;
import com.qiyukf.nimlib.biz.k;
import com.qiyukf.nimlib.report.b.h;
import com.qiyukf.nimlib.sdk.ai.model.NIMAIModelStreamCallChunk;
import com.qiyukf.nimlib.sdk.ai.model.NIMAIModelStreamCallContent;
import com.qiyukf.nimlib.sdk.ai.model.NIMAIRAGInfo;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.CopyOnWriteArraySet;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AIAgentStreamCallNotifyHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class c extends com.qiyukf.nimlib.biz.c.a {
    private final Set<String> a = new CopyOnWriteArraySet();

    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        boolean z;
        boolean z2;
        String strOptString;
        int iOptInt;
        int iOptInt2;
        if (aVar.e()) {
            com.qiyukf.nimlib.biz.e.a.c cVar = (com.qiyukf.nimlib.biz.e.a.c) aVar;
            com.qiyukf.nimlib.push.packet.b.c cVarJ = cVar.j();
            if (cVarJ == null) {
                com.qiyukf.nimlib.log.c.b.a.d("AIAgentStreamCallNotifyHandler", "property is null");
                return;
            }
            String strC = cVarJ.c(3);
            if (com.qiyukf.nimlib.a.a.a().g(strC)) {
                com.qiyukf.nimlib.log.c.b.a.d("AIAgentStreamCallNotifyHandler", "requestId:" + strC + "result has return,ignore this stream result");
                return;
            }
            long jC = com.qiyukf.nimlib.a.a.a().c(strC);
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            com.qiyukf.nimlib.a.a.a().a(strC, jElapsedRealtime);
            com.qiyukf.nimlib.log.b.a("AIAgentStreamCall", "receive AIAgentStreamCallNotify, delta = ".concat(String.valueOf(jElapsedRealtime - jC)));
            com.qiyukf.nimlib.push.packet.a aVarA = cVar.a();
            int iD = cVarJ.d(1);
            String strC2 = cVarJ.c(2);
            String strC3 = cVarJ.c(3);
            String strC4 = cVarJ.c(4);
            long jE = cVarJ.e(6);
            NIMAIModelStreamCallChunk nIMAIModelStreamCallChunk = null;
            if (TextUtils.isEmpty(strC4)) {
                z2 = false;
            } else {
                try {
                    JSONObject jSONObject = new JSONObject(strC4);
                    strOptString = jSONObject.optString("msg");
                    iOptInt = jSONObject.optInt("type");
                    iOptInt2 = jSONObject.optInt("index");
                    z = jSONObject.optInt(LogConstants.UPLOAD_FINISH) == 1;
                } catch (JSONException e) {
                    e = e;
                    z = false;
                }
                try {
                    nIMAIModelStreamCallChunk = new NIMAIModelStreamCallChunk(strOptString, jE, iOptInt, iOptInt2);
                } catch (JSONException e2) {
                    e = e2;
                    com.qiyukf.nimlib.log.c.b.a.d("AIAgentStreamCallNotifyHandler", "parse content error", e);
                }
                z2 = z;
            }
            if (nIMAIModelStreamCallChunk == null) {
                com.qiyukf.nimlib.log.c.b.a.d("AIAgentStreamCallNotifyHandler", "streamCallChunk is null");
                return;
            }
            List<NIMAIRAGInfo> listA = com.qiyukf.nimlib.a.b.a(cVarJ.c(5));
            com.qiyukf.nimlib.a.c.b bVar = new com.qiyukf.nimlib.a.c.b();
            bVar.a(strC3);
            com.qiyukf.nimlib.a.a.a aVar2 = com.qiyukf.nimlib.a.a.a.AI_STREAM_CHUNK_TYPE_AGENT;
            bVar.a(aVar2);
            bVar.c(nIMAIModelStreamCallChunk.getContent());
            bVar.a(nIMAIModelStreamCallChunk.getType());
            bVar.b(nIMAIModelStreamCallChunk.getIndex());
            bVar.a(nIMAIModelStreamCallChunk.getChunkTime());
            bVar.a(z2);
            SortedSet<com.qiyukf.nimlib.a.c.b> sortedSetE = com.qiyukf.nimlib.a.a.a().e(strC3);
            if (sortedSetE != null && !sortedSetE.isEmpty()) {
                com.qiyukf.nimlib.a.c.b bVarLast = sortedSetE.last();
                int iF = bVar.f();
                int iF2 = bVarLast.f();
                if (iF2 != iF - 1 && !com.qiyukf.nimlib.a.a.a().k(strC3)) {
                    com.qiyukf.nimlib.a.a.a().j(strC3);
                    StringBuilder sbA = a(sortedSetE, iF, iF2);
                    com.qiyukf.nimlib.log.c.b.a.d("AIAgentStreamCallNotifyHandler", "index disorder:".concat(String.valueOf(sbA)));
                    com.qiyukf.nimlib.report.d.a(aVarA, h.kMissed, com.qiyukf.nimlib.report.b.b.kReceivePacket, strC3, sbA.toString());
                }
            }
            SortedSet<com.qiyukf.nimlib.a.c.b> sortedSetA = com.qiyukf.nimlib.a.a.a().a(strC3, bVar);
            com.qiyukf.nimlib.a.b.b bVarB = com.qiyukf.nimlib.a.a.a().b(strC3);
            if (bVarB == null) {
                final com.qiyukf.nimlib.a.b.b bVar2 = new com.qiyukf.nimlib.a.b.b();
                bVar2.a(iD);
                bVar2.a(strC2);
                bVar2.b(strC3);
                bVar2.a(listA);
                bVar2.a(jE);
                com.qiyukf.nimlib.a.a.a().a(bVar2, jElapsedRealtime);
                if (bVar.f() == 0) {
                    b(bVar2, bVar, sortedSetA);
                    return;
                }
                this.a.add(bVar2.getRequestId());
                com.qiyukf.nimlib.push.packet.b.c cVar2 = new com.qiyukf.nimlib.push.packet.b.c();
                cVar2.a(2, bVar.a());
                cVar2.a(3, aVar2.a());
                cVar2.a(4, com.qiyukf.nimlib.c.q());
                cVar2.a(6, bVar2.getAccountId());
                int iF3 = bVar.f() - 1;
                if (iF3 < 0) {
                    iF3 = 0;
                }
                cVar2.a(7, 0);
                cVar2.a(8, iF3);
                k.a().a(new com.qiyukf.nimlib.biz.g.b(new com.qiyukf.nimlib.biz.d.a.b(cVar2), com.qiyukf.nimlib.biz.g.a.c) { // from class: com.qiyukf.nimlib.biz.c.a.c.1
                    @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
                    public final void a(com.qiyukf.nimlib.biz.e.a aVar3) {
                        SortedSet<com.qiyukf.nimlib.a.c.b> sortedSetE2;
                        super.a(aVar3);
                        if (com.qiyukf.nimlib.a.a.a().g(bVar2.getRequestId())) {
                            com.qiyukf.nimlib.log.c.b.a.d("AIAgentStreamCallNotifyHandler", "requestId:" + bVar2.getRequestId() + "result has return,ignore this stream result");
                            return;
                        }
                        if (aVar3.e()) {
                            sortedSetE2 = com.qiyukf.nimlib.a.a.a().a(bVar2.getRequestId(), ((com.qiyukf.nimlib.biz.e.a.d) aVar3).j());
                        } else {
                            sortedSetE2 = com.qiyukf.nimlib.a.a.a().e(bVar2.getRequestId());
                        }
                        c.b(bVar2, sortedSetE2.last(), sortedSetE2);
                        c.this.a.remove(bVar2.getRequestId());
                    }
                });
                return;
            }
            com.qiyukf.nimlib.a.b.b bVarA = bVarB.a();
            com.qiyukf.nimlib.a.a.a().a(bVarA, jElapsedRealtime);
            if (this.a.contains(strC3)) {
                com.qiyukf.nimlib.log.c.b.a.d("AIAgentStreamCallNotifyHandler", "now is waiting AIStreamChunks response");
            } else {
                b(bVarA, bVar, sortedSetA);
            }
        }
    }

    @NonNull
    private static StringBuilder a(SortedSet<com.qiyukf.nimlib.a.c.b> sortedSet, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (com.qiyukf.nimlib.a.c.b bVar : sortedSet) {
            if (bVar.f() != i2) {
                sb.append(bVar.f());
                sb.append(ChineseToPinyinResource.Field.COMMA);
            } else {
                sb.append(bVar.f());
            }
        }
        sb.append("],");
        sb.append(i);
        return sb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(com.qiyukf.nimlib.a.b.b bVar, com.qiyukf.nimlib.a.c.b bVar2, SortedSet<com.qiyukf.nimlib.a.c.b> sortedSet) {
        NIMAIModelStreamCallContent nIMAIModelStreamCallContentCopy;
        com.qiyukf.nimlib.a.c.b bVarFirst;
        NIMAIModelStreamCallChunk nIMAIModelStreamCallChunk = bVar2 == null ? null : new NIMAIModelStreamCallChunk(bVar2.d(), bVar2.g(), bVar2.e(), bVar2.f());
        NIMAIModelStreamCallContent content = bVar.getContent();
        if (content == null) {
            nIMAIModelStreamCallContentCopy = new NIMAIModelStreamCallContent();
            if (nIMAIModelStreamCallChunk != null) {
                nIMAIModelStreamCallContentCopy.setType(nIMAIModelStreamCallChunk.getType());
            }
        } else {
            nIMAIModelStreamCallContentCopy = content.copy();
        }
        if (sortedSet != null) {
            StringBuilder sb = new StringBuilder();
            for (com.qiyukf.nimlib.a.c.b bVar3 : sortedSet) {
                if (bVar3.d() != null) {
                    sb.append(bVar3.d());
                }
            }
            nIMAIModelStreamCallContentCopy.setMsg(sb.toString());
            if (bVar.getAIRAGs() == null && (bVarFirst = sortedSet.first()) != null && bVarFirst.i() != null) {
                bVar.a(bVarFirst.i());
            }
        }
        nIMAIModelStreamCallContentCopy.setLastChunk(nIMAIModelStreamCallChunk);
        bVar.a(nIMAIModelStreamCallContentCopy);
        if (nIMAIModelStreamCallChunk != null) {
            bVar.a(nIMAIModelStreamCallChunk.getChunkTime());
        }
        com.qiyukf.nimlib.a.c.a(bVar);
    }
}

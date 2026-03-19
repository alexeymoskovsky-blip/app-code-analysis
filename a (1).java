package com.qiyukf.nimlib.biz.c.j;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import com.qiyukf.module.log.entry.LogConstants;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.enums.NIMMessageAIStreamStatus;
import com.qiyukf.nimlib.sdk.msg.model.NIMMessageAIConfig;
import com.qiyukf.nimlib.sdk.msg.model.NIMMessageAIStreamChunk;
import com.qiyukf.nimlib.session.MsgDBHelper;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.CopyOnWriteArraySet;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.json.JSONObject;

/* JADX INFO: compiled from: AIStreamMessageNotifyHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class a extends com.qiyukf.nimlib.biz.c.i {
    private volatile boolean a = false;
    private final Set<String> b = new CopyOnWriteArraySet();

    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        NIMMessageAIConfig aIConfig;
        com.qiyukf.nimlib.biz.e.k.a aVar2 = (com.qiyukf.nimlib.biz.e.k.a) aVar;
        com.qiyukf.nimlib.push.packet.b.c cVarJ = aVar2.j();
        if (cVarJ == null) {
            return;
        }
        String strC = cVarJ.c(11);
        if (com.qiyukf.nimlib.session.e.a().r(strC)) {
            com.qiyukf.nimlib.log.c.b.a.d("AIStreamMessageNotifyHandler", "receive AIStreamMessageNotify , but msg is already finished , clientId = ".concat(String.valueOf(strC)));
            return;
        }
        long jK = com.qiyukf.nimlib.session.e.a().k(strC);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        com.qiyukf.nimlib.session.e.a().a(strC, jElapsedRealtime);
        com.qiyukf.nimlib.log.b.a("AIStreamMessage", "receive AIStreamMessageNotify post, delta = ".concat(String.valueOf(jElapsedRealtime - jK)));
        com.qiyukf.nimlib.push.packet.a aVarA = aVar2.a();
        String strC2 = cVarJ.c(11);
        String strC3 = cVarJ.c(12);
        long jE = cVarJ.e(62);
        String strC4 = cVarJ.c(10);
        final com.qiyukf.nimlib.session.d dVarJ = com.qiyukf.nimlib.session.e.a().j(strC2);
        if (dVarJ == null) {
            com.qiyukf.nimlib.log.b.a("AIStreamMessageNotifyHandler", "receive AIStreamMessageNotify , but aiStreamMsg is null , clientId = ".concat(String.valueOf(strC2)));
            dVarJ = (com.qiyukf.nimlib.session.d) MsgDBHelper.queryMessageByUuid(strC2);
            if (dVarJ == null || (aIConfig = dVarJ.getAIConfig()) == null) {
                return;
            }
            if (aIConfig.getAIStreamStatus().getValue() > NIMMessageAIStreamStatus.NIM_MESSAGE_AI_STREAM_STATUS_PLACEHOLDER.getValue() && jE < dVarJ.getModifyTime()) {
                return;
            }
        }
        NIMMessageAIConfig aIConfig2 = dVarJ.getAIConfig();
        if (aIConfig2 != null) {
            if (aIConfig2.getAIRAGs() == null && cVarJ.f(66)) {
                aIConfig2.setAIRAGs(com.qiyukf.nimlib.a.b.a(cVarJ.c(66)));
            }
            com.qiyukf.nimlib.a.c.b bVarA = a(strC2, strC3, dVarJ.getSessionType(), jE, strC4);
            SortedSet<com.qiyukf.nimlib.a.c.b> sortedSetM = com.qiyukf.nimlib.session.e.a().m(strC2);
            if (sortedSetM != null && !sortedSetM.isEmpty()) {
                com.qiyukf.nimlib.a.c.b bVarLast = sortedSetM.last();
                int iF = bVarA.f();
                int iF2 = bVarLast.f();
                if (iF2 != iF - 1 && !com.qiyukf.nimlib.session.e.a().p(strC2)) {
                    com.qiyukf.nimlib.session.e.a().n(strC2);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    for (com.qiyukf.nimlib.a.c.b bVar : sortedSetM) {
                        if (bVar.f() != iF2) {
                            sb.append(bVar.f());
                            sb.append(ChineseToPinyinResource.Field.COMMA);
                        } else {
                            sb.append(bVar.f());
                        }
                    }
                    sb.append("],");
                    sb.append(iF);
                    com.qiyukf.nimlib.log.c.b.a.d("AIStreamMessageNotifyHandler", "index disorder:".concat(String.valueOf(sb)));
                    com.qiyukf.nimlib.report.d.a(aVarA, com.qiyukf.nimlib.report.b.h.kMissed, com.qiyukf.nimlib.report.b.b.kReceivePacket, strC2, sb.toString());
                }
            }
            SortedSet<com.qiyukf.nimlib.a.c.b> sortedSetA = com.qiyukf.nimlib.session.e.a().a(strC2, bVarA);
            if (sortedSetA == null || sortedSetA.isEmpty()) {
                return;
            }
            com.qiyukf.nimlib.session.e.a().a(dVarJ, jElapsedRealtime);
            if (sortedSetA.size() != 1 || bVarA.f() == 0) {
                if (this.b.contains(strC2)) {
                    com.qiyukf.nimlib.log.c.b.a.d("AIStreamMessageNotifyHandler", "now is waiting AIStreamChunks response");
                    return;
                } else {
                    b(dVarJ, bVarA, sortedSetA);
                    return;
                }
            }
            this.b.add(dVarJ.getUuid());
            com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
            cVar.a(1, bVarA.b());
            cVar.a(2, bVarA.a());
            cVar.a(3, bVarA.c().a());
            cVar.a(4, dVarJ.getFromAccount());
            cVar.a(5, dVarJ.getReceiverId());
            cVar.a(6, dVarJ.getAIConfig().getAccountId());
            int iF3 = bVarA.f() - 1;
            if (iF3 < 0) {
                iF3 = 0;
            }
            cVar.a(7, 0);
            cVar.a(8, iF3);
            com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(new com.qiyukf.nimlib.biz.d.a.b(cVar), com.qiyukf.nimlib.biz.g.a.c) { // from class: com.qiyukf.nimlib.biz.c.j.a.1
                @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
                public final void a(com.qiyukf.nimlib.biz.e.a aVar3) {
                    SortedSet<com.qiyukf.nimlib.a.c.b> sortedSetM2;
                    super.a(aVar3);
                    if (com.qiyukf.nimlib.session.e.a().r(dVarJ.getUuid())) {
                        com.qiyukf.nimlib.log.c.b.a.d("AIStreamMessageNotifyHandler", "getAIStreamChunks receive AIStreamMessageNotify , but msg is already finished , clientId = " + dVarJ.getUuid());
                    } else {
                        if (com.qiyukf.nimlib.session.e.a().j(dVarJ.getUuid()) == null) {
                            com.qiyukf.nimlib.log.c.b.a.d("AIStreamMessageNotifyHandler", "getAIStreamChunks return,but message is removed");
                            return;
                        }
                        if (aVar3.e()) {
                            sortedSetM2 = com.qiyukf.nimlib.session.e.a().a(dVarJ.getUuid(), ((com.qiyukf.nimlib.biz.e.a.d) aVar3).j());
                        } else {
                            sortedSetM2 = com.qiyukf.nimlib.session.e.a().m(dVarJ.getUuid());
                        }
                        a.b(dVarJ, sortedSetM2.last(), sortedSetM2);
                        a.this.b.remove(dVarJ.getUuid());
                    }
                }
            });
        }
    }

    @NonNull
    private static com.qiyukf.nimlib.a.c.b a(String str, String str2, SessionTypeEnum sessionTypeEnum, long j, String str3) {
        com.qiyukf.nimlib.a.c.b bVar = new com.qiyukf.nimlib.a.c.b();
        bVar.a(str);
        bVar.b(str2);
        if (sessionTypeEnum == SessionTypeEnum.P2P) {
            bVar.a(com.qiyukf.nimlib.a.a.a.AI_STREAM_CHUNK_TYPE_P2P);
        } else if (sessionTypeEnum == SessionTypeEnum.Team) {
            bVar.a(com.qiyukf.nimlib.a.a.a.AI_STREAM_CHUNK_TYPE_TEAM);
        } else if (sessionTypeEnum == SessionTypeEnum.SUPER_TEAM) {
            bVar.a(com.qiyukf.nimlib.a.a.a.AI_STREAM_CHUNK_TYPE_SUPER_TEAM);
        } else {
            bVar.a(com.qiyukf.nimlib.a.a.a.AI_STREAM_CHUNK_TYPE_UNKNOWN);
        }
        bVar.a(j);
        try {
            JSONObject jSONObject = new JSONObject(str3);
            bVar.c(jSONObject.optString("msg"));
            bVar.a(jSONObject.optInt("type"));
            bVar.b(jSONObject.optInt("index"));
            boolean z = true;
            if (jSONObject.optInt(LogConstants.UPLOAD_FINISH) != 1) {
                z = false;
            }
            bVar.a(z);
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("AIStreamMessageNotifyHandler", "parse AIStreamChunk error", th);
        }
        return bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(com.qiyukf.nimlib.session.d dVar, com.qiyukf.nimlib.a.c.b bVar, SortedSet<com.qiyukf.nimlib.a.c.b> sortedSet) {
        NIMMessageAIConfig nIMMessageAIConfigCopy;
        com.qiyukf.nimlib.a.c.b bVarFirst;
        com.qiyukf.nimlib.session.d dVarClone = dVar.clone();
        NIMMessageAIConfig aIConfig = dVarClone.getAIConfig();
        if (aIConfig != null) {
            nIMMessageAIConfigCopy = aIConfig.copy();
            dVarClone.setAIConfig(nIMMessageAIConfigCopy);
        } else {
            nIMMessageAIConfigCopy = null;
        }
        if (bVar != null) {
            com.qiyukf.nimlib.log.c.b.a.c("AIStreamMessageNotifyHandler", "notifyReceiveMessageModified,chunk:" + bVar.f() + ",finish:" + bVar.h());
            NIMMessageAIStreamChunk nIMMessageAIStreamChunk = new NIMMessageAIStreamChunk(bVar.d(), dVarClone.getTime(), bVar.g(), bVar.e(), bVar.f());
            if (bVar.g() > dVarClone.getModifyTime()) {
                dVarClone.e(bVar.g());
                dVarClone.n(dVarClone.getFromAccount());
            }
            if (nIMMessageAIConfigCopy != null) {
                nIMMessageAIConfigCopy.setAIStreamStatus(NIMMessageAIStreamStatus.NIM_MESSAGE_AI_STREAM_STATUS_STREAMING);
                nIMMessageAIConfigCopy.setAIStreamLastChunk(nIMMessageAIStreamChunk);
            }
        }
        if (sortedSet != null) {
            StringBuilder sb = new StringBuilder();
            for (com.qiyukf.nimlib.a.c.b bVar2 : sortedSet) {
                if (bVar2.d() != null) {
                    sb.append(bVar2.d());
                }
            }
            dVarClone.setContent(sb.toString());
            if (nIMMessageAIConfigCopy != null && nIMMessageAIConfigCopy.getAIRAGs() == null && (bVarFirst = sortedSet.first()) != null && bVarFirst.i() != null) {
                nIMMessageAIConfigCopy.setAIRAGs(bVarFirst.i());
            }
        }
        com.qiyukf.nimlib.i.b.n(Collections.singletonList(dVarClone));
    }
}

package com.qiyukf.nimlib.biz.f;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.qiyukf.nimlib.NimNosSceneKeyConstant;
import com.qiyukf.nimlib.biz.c.j.n$$ExternalSyntheticLambda0;
import com.qiyukf.nimlib.biz.d.i.q;
import com.qiyukf.nimlib.biz.f.g;
import com.qiyukf.nimlib.n.e;
import com.qiyukf.nimlib.n.w;
import com.qiyukf.nimlib.net.a.b.a;
import com.qiyukf.nimlib.sdk.AbortableFuture;
import com.qiyukf.nimlib.sdk.InvocationFuture;
import com.qiyukf.nimlib.sdk.migration.processor.IMsgExportProcessor;
import com.qiyukf.nimlib.sdk.migration.processor.IMsgImportProcessor;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.attachment.MsgAttachmentParser;
import com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.DeleteTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.enums.NIMMessageAIRegenOpType;
import com.qiyukf.nimlib.sdk.msg.enums.NIMMessageAIStreamStatus;
import com.qiyukf.nimlib.sdk.msg.model.CollectInfo;
import com.qiyukf.nimlib.sdk.msg.model.CollectInfoPage;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.nimlib.sdk.msg.model.GetMessageDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.model.GetMessagesDynamicallyParam;
import com.qiyukf.nimlib.sdk.msg.model.GetMessagesDynamicallyResult;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.LocalAntiSpamResult;
import com.qiyukf.nimlib.sdk.msg.model.MessageKey;
import com.qiyukf.nimlib.sdk.msg.model.MessageReceipt;
import com.qiyukf.nimlib.sdk.msg.model.MsgFullKeywordSearchConfig;
import com.qiyukf.nimlib.sdk.msg.model.MsgPinDbOption;
import com.qiyukf.nimlib.sdk.msg.model.MsgPinSyncResponseOptionWrapper;
import com.qiyukf.nimlib.sdk.msg.model.MsgSearchOption;
import com.qiyukf.nimlib.sdk.msg.model.MsgSendOption;
import com.qiyukf.nimlib.sdk.msg.model.MsgThreadOption;
import com.qiyukf.nimlib.sdk.msg.model.MsgTimingFullKeywordSearchConfig;
import com.qiyukf.nimlib.sdk.msg.model.NIMMessageAIConfig;
import com.qiyukf.nimlib.sdk.msg.model.NIMMessageAIConfigParams;
import com.qiyukf.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.model.QueryMySessionOption;
import com.qiyukf.nimlib.sdk.msg.model.QueryThreadTalkHistoryOption;
import com.qiyukf.nimlib.sdk.msg.model.QuickCommentOptionWrapper;
import com.qiyukf.nimlib.sdk.msg.model.RecentContact;
import com.qiyukf.nimlib.sdk.msg.model.RecentSession;
import com.qiyukf.nimlib.sdk.msg.model.RecentSessionList;
import com.qiyukf.nimlib.sdk.msg.model.RoamMsgHasMoreOption;
import com.qiyukf.nimlib.sdk.msg.model.SearchOrderEnum;
import com.qiyukf.nimlib.sdk.msg.model.SessionAckInfo;
import com.qiyukf.nimlib.sdk.msg.model.ShowNotificationWhenRevokeFilter;
import com.qiyukf.nimlib.sdk.msg.model.StickTopSessionInfo;
import com.qiyukf.nimlib.sdk.msg.model.ThreadTalkHistory;
import com.qiyukf.nimlib.sdk.msg.params.NIMMessageAIRegenParams;
import com.qiyukf.nimlib.sdk.msg.params.NIMMessageAIStreamStopParams;
import com.qiyukf.nimlib.sdk.search.model.MsgIndexRecord;
import com.qiyukf.nimlib.sdk.team.constant.TeamMemberType;
import com.qiyukf.nimlib.sdk.team.model.IMMessageFilter;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.session.aa;
import com.qiyukf.nimlib.session.b.c;
import com.qiyukf.nimlib.session.f;
import com.qiyukf.nimlib.session.s;
import com.qiyukf.nimlib.session.x;
import com.qiyukf.nimlib.session.y;
import com.qiyukf.nimlib.superteam.SuperTeamDBHelper;
import com.qiyukf.nimlib.team.TeamDBHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: MsgServiceRemote.java */
/* JADX INFO: loaded from: classes6.dex */
public class g extends com.qiyukf.nimlib.i.i implements MsgService {
    private static int a(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i > 100) {
            return 100;
        }
        return i;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> sendMessage(IMMessage iMMessage, boolean z) {
        com.qiyukf.nimlib.session.i.a((com.qiyukf.nimlib.session.d) iMMessage, z, com.qiyukf.nimlib.i.i.b(), SystemClock.elapsedRealtime());
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> sendMessage(IMMessage iMMessage, boolean z, MsgSendOption msgSendOption) {
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
        if (msgSendOption != null) {
            NIMMessageAIConfigParams aiConfigParams = msgSendOption.getAiConfigParams();
            if (aiConfigParams != null && !aiConfigParams.isValid()) {
                com.qiyukf.nimlib.i.i.b().a(414).b();
                return null;
            }
            dVar.a(aiConfigParams);
        }
        sendMessage(dVar, z);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> stopAIStreamMessage(IMMessage iMMessage, NIMMessageAIStreamStopParams nIMMessageAIStreamStopParams) {
        int i;
        if (iMMessage == null) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "stopAIStreamMessage msg is null");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        NIMMessageAIConfig aIConfig = iMMessage.getAIConfig();
        if (aIConfig == null) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "stopAIStreamMessage msg is not AI Message");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        if (nIMMessageAIStreamStopParams == null) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "stopAIStreamMessage params is null");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        if (!nIMMessageAIStreamStopParams.isValid()) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "stopAIStreamMessage params is invalid");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        final String uuid = iMMessage.getUuid();
        String strValueOf = String.valueOf(iMMessage.getServerId());
        if (iMMessage.getSessionType() == SessionTypeEnum.P2P) {
            i = 1;
        } else if (iMMessage.getSessionType() == SessionTypeEnum.Team) {
            i = 2;
        } else {
            i = iMMessage.getSessionType() == SessionTypeEnum.SUPER_TEAM ? 3 : 0;
        }
        String fromAccount = iMMessage.getFromAccount();
        String receiverId = iMMessage.getReceiverId();
        String accountId = aIConfig.getAccountId();
        int value = nIMMessageAIStreamStopParams.getOperationType().getValue();
        String updateContent = nIMMessageAIStreamStopParams.getUpdateContent();
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(1, strValueOf);
        cVar.a(2, uuid);
        cVar.a(3, i);
        cVar.a(4, fromAccount);
        cVar.a(5, receiverId);
        cVar.a(6, accountId);
        cVar.a(7, value);
        cVar.a(8, updateContent);
        cVar.a(11, iMMessage.getTime());
        com.qiyukf.nimlib.biz.d.a.e eVar = new com.qiyukf.nimlib.biz.d.a.e(cVar);
        eVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(eVar) { // from class: com.qiyukf.nimlib.biz.f.g.1
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                super.a(aVar);
                if (aVar.e()) {
                    com.qiyukf.nimlib.session.e.a().q(uuid);
                    com.qiyukf.nimlib.session.e.a();
                    com.qiyukf.nimlib.session.e.a().o(uuid);
                    com.qiyukf.nimlib.session.e.a().i(uuid);
                    com.qiyukf.nimlib.session.e.a().l(uuid);
                }
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> regenAIMessage(IMMessage iMMessage, NIMMessageAIRegenParams nIMMessageAIRegenParams) {
        int i;
        if (iMMessage == null) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "regenAIMessage msg is null");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        IMMessage iMMessageQueryMessageByUuid = MsgDBHelper.queryMessageByUuid(iMMessage.getUuid());
        if (iMMessageQueryMessageByUuid == null) {
            com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", "regenAIMessage local msg is not exist");
            iMMessageQueryMessageByUuid = iMMessage;
        }
        NIMMessageAIConfig aIConfig = iMMessageQueryMessageByUuid.getAIConfig();
        if (aIConfig == null) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "regenAIMessage msg is not AI Message");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        NIMMessageAIStreamStatus aIStreamStatus = aIConfig.getAIStreamStatus();
        if (aIStreamStatus == NIMMessageAIStreamStatus.NIM_MESSAGE_AI_STREAM_STATUS_PLACEHOLDER || aIStreamStatus == NIMMessageAIStreamStatus.NIM_MESSAGE_AI_STREAM_STATUS_STREAMING) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "regenAIMessage msg is streaming");
            com.qiyukf.nimlib.i.i.b().a(2).b();
            return null;
        }
        if (nIMMessageAIRegenParams == null) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "regenAIMessage params is null");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        if (!nIMMessageAIRegenParams.isValid()) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "regenAIMessage params is invalid");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        final String uuid = iMMessage.getUuid();
        if (MsgDBHelper.hasDeleteTag(uuid) && nIMMessageAIRegenParams.getOperationType() == NIMMessageAIRegenOpType.NIM_MESSAGE_AI_REGEN_OP_UPDATE) {
            com.qiyukf.nimlib.log.c.b.a.f("MsgServiceRemote", "regenAIMessage message is deleted");
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        String fromAccount = iMMessage.getFromAccount();
        String receiverId = iMMessage.getReceiverId();
        String accountId = aIConfig.getAccountId();
        String strValueOf = String.valueOf(iMMessage.getServerId());
        long time = iMMessage.getTime();
        if (iMMessage.getSessionType() == SessionTypeEnum.P2P) {
            i = 1;
        } else if (iMMessage.getSessionType() == SessionTypeEnum.Team) {
            i = 2;
        } else {
            i = iMMessage.getSessionType() == SessionTypeEnum.SUPER_TEAM ? 3 : 0;
        }
        int value = nIMMessageAIRegenParams.getOperationType().getValue();
        MsgThreadOption threadOption = iMMessage.getThreadOption();
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(1, fromAccount);
        cVar.a(2, receiverId);
        cVar.a(3, accountId);
        cVar.a(4, strValueOf);
        cVar.a(5, uuid);
        cVar.a(6, time);
        cVar.a(7, i);
        cVar.a(8, value);
        if (threadOption != null) {
            cVar.a(9, threadOption.getReplyMsgFromAccount());
            cVar.a(10, threadOption.getReplyMsgToAccount());
            cVar.a(11, threadOption.getReplyMsgTime());
            cVar.a(12, threadOption.getReplyMsgIdServer());
            cVar.a(13, threadOption.getReplyMsgIdClient());
        }
        com.qiyukf.nimlib.biz.d.a.d dVar = new com.qiyukf.nimlib.biz.d.a.d(cVar);
        dVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(dVar) { // from class: com.qiyukf.nimlib.biz.f.g.6
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                super.a(aVar);
                if (aVar.e()) {
                    com.qiyukf.nimlib.session.e.a().s(uuid);
                }
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> replyMessage(IMMessage iMMessage, IMMessage iMMessage2, boolean z) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if ((iMMessage instanceof com.qiyukf.nimlib.session.d) && (iMMessage2 instanceof com.qiyukf.nimlib.session.d)) {
            String sessionId = iMMessage.getSessionId();
            if (!TextUtils.isEmpty(sessionId) && sessionId.equals(iMMessage2.getSessionId())) {
                com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
                dVar.setThreadOption(iMMessage2);
                com.qiyukf.nimlib.session.i.a(dVar, z, com.qiyukf.nimlib.i.i.b(), jElapsedRealtime);
                return null;
            }
        }
        jVarB.a(414).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> replyMessage(IMMessage iMMessage, IMMessage iMMessage2, boolean z, MsgSendOption msgSendOption) {
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
        if (msgSendOption != null) {
            NIMMessageAIConfigParams aiConfigParams = msgSendOption.getAiConfigParams();
            if (aiConfigParams != null && !aiConfigParams.isValid()) {
                com.qiyukf.nimlib.i.i.b().a(414).b();
                return null;
            }
            dVar.a(aiConfigParams);
        }
        replyMessage(dVar, iMMessage2, z);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> saveMessageToLocal(IMMessage iMMessage, boolean z) {
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
        MsgDBHelper.saveMessage(dVar);
        com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.session.k.c(dVar));
        com.qiyukf.nimlib.i.i.b().a(200).b();
        if (!z) {
            return null;
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(dVar);
        com.qiyukf.nimlib.i.b.b(arrayList);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public AbortableFuture<FileAttachment> sendFile(FileAttachment fileAttachment) throws Exception {
        final a.c cVarA = com.qiyukf.nimlib.session.i.a(fileAttachment, com.qiyukf.nimlib.i.i.b());
        return new com.qiyukf.nimlib.i.g<FileAttachment>() { // from class: com.qiyukf.nimlib.biz.f.g.7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(null);
            }

            @Override // com.qiyukf.nimlib.sdk.AbortableFuture
            public final boolean abort() {
                com.qiyukf.nimlib.net.a.b.a.a().a(cVarA);
                return false;
            }
        };
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> insertLocalMessage(IMMessage iMMessage, String str) {
        com.qiyukf.nimlib.session.d dVarL = ((com.qiyukf.nimlib.session.d) iMMessage).l();
        dVarL.setFromAccount(str);
        dVarL.setDirect(MsgDirectionEnum.In);
        dVarL.setStatus(MsgStatusEnum.success);
        return saveMessageToLocal(dVarL, true);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> saveMessageToLocalEx(IMMessage iMMessage, boolean z, long j) {
        if (iMMessage == null) {
            return null;
        }
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
        if (j >= 0) {
            dVar.b(j);
        }
        MsgDBHelper.saveMessage(dVar);
        s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(iMMessage.getSessionId(), iMMessage.getSessionType());
        if (sVarQueryRecentContact == null) {
            com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.session.k.c(dVar));
        } else if (sVarQueryRecentContact.getTime() <= j) {
            s sVarC = com.qiyukf.nimlib.session.k.c(dVar);
            sVarC.a(j);
            com.qiyukf.nimlib.i.b.a(sVarC);
        }
        com.qiyukf.nimlib.i.i.b().a(200).b();
        if (z) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(dVar);
            com.qiyukf.nimlib.i.b.b(arrayList);
        }
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public AbortableFuture downloadAttachment(IMMessage iMMessage, boolean z) {
        com.qiyukf.nimlib.net.a.a.e eVarA = com.qiyukf.nimlib.session.h.a((com.qiyukf.nimlib.session.d) iMMessage, z, com.qiyukf.nimlib.i.i.b());
        if (eVarA == null) {
            return null;
        }
        return new com.qiyukf.nimlib.i.g<com.qiyukf.nimlib.net.a.a.e>(eVarA) { // from class: com.qiyukf.nimlib.biz.f.g.8
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.qiyukf.nimlib.sdk.AbortableFuture
            public final boolean abort() {
                com.qiyukf.nimlib.biz.b.e.f();
                if (com.qiyukf.nimlib.biz.b.a.a()) {
                    com.qiyukf.nimlib.biz.b.e.f().a((com.qiyukf.nimlib.net.a.a.e) this.c);
                    return false;
                }
                com.qiyukf.nimlib.net.a.a.g.a().b((com.qiyukf.nimlib.net.a.a.e) this.c);
                return false;
            }
        };
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<IMMessage> queryMessageListByUuidBlock(List<String> list) {
        List<IMMessage> listQueryMsgListByUuid = MsgDBHelper.queryMsgListByUuid(list);
        b(listQueryMsgListByUuid);
        return listQueryMsgListByUuid;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListByUuid(List<String> list) {
        return a(MsgDBHelper.queryMsgListByUuid(list));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<IMMessage> queryMessageListByServerIdBlock(List<String> list) {
        List<IMMessage> listQueryMsgListByServerId = MsgDBHelper.queryMsgListByServerId(list);
        b(listQueryMsgListByServerId);
        return listQueryMsgListByServerId;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListByType(MsgTypeEnum msgTypeEnum, IMMessage iMMessage, int i) {
        return a(MsgDBHelper.queryMessageListByType(msgTypeEnum, iMMessage, i));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListByType(MsgTypeEnum msgTypeEnum, Long l, int i) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (i <= 0 && l != null) {
            jVarB.a(414).b();
            return null;
        }
        List<IMMessage> listQueryMessageListByType = MsgDBHelper.queryMessageListByType(msgTypeEnum, l, i);
        if (listQueryMessageListByType == null || listQueryMessageListByType.size() == 0) {
            jVarB.b((Object) null).b();
        } else {
            b(listQueryMessageListByType);
            jVarB.b(listQueryMessageListByType).b();
        }
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<IMMessage> queryMessageListBySubtypeBlock(MsgTypeEnum msgTypeEnum, IMMessage iMMessage, int i, int i2) {
        return MsgDBHelper.queryMessageListBySubtype(msgTypeEnum, iMMessage, i, i2);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListBySubtype(MsgTypeEnum msgTypeEnum, IMMessage iMMessage, int i, int i2) {
        a(MsgDBHelper.queryMessageListBySubtype(msgTypeEnum, iMMessage, i, i2));
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public IMMessage queryLastMessage(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelper.queryLatestMessage(str, sessionTypeEnum.getValue());
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageList(String str, SessionTypeEnum sessionTypeEnum, long j, int i) {
        return a(MsgDBHelper.queryMessageList(str, sessionTypeEnum.getValue(), j, i));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListEx(IMMessage iMMessage, QueryDirectionEnum queryDirectionEnum, int i, boolean z) {
        return a(MsgDBHelper.queryMessageListEx((com.qiyukf.nimlib.session.d) iMMessage, queryDirectionEnum, i, z));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<IMMessage> queryMessageListExBlock(IMMessage iMMessage, QueryDirectionEnum queryDirectionEnum, int i, boolean z) {
        return MsgDBHelper.queryMessageListEx((com.qiyukf.nimlib.session.d) iMMessage, queryDirectionEnum, i, z);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListByTypes(List<MsgTypeEnum> list, IMMessage iMMessage, long j, QueryDirectionEnum queryDirectionEnum, int i, boolean z) {
        return a(MsgDBHelper.queryMessageListExWrapper(list, (com.qiyukf.nimlib.session.d) iMMessage, j, queryDirectionEnum, i, z, false));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListByTypesV2(List<MsgTypeEnum> list, IMMessage iMMessage, long j, QueryDirectionEnum queryDirectionEnum, int i, boolean z) {
        return a(MsgDBHelper.queryMessageListExWrapper(list, (com.qiyukf.nimlib.session.d) iMMessage, j, queryDirectionEnum, i, z, true));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> queryRoamMsgHasMoreTime(String str, SessionTypeEnum sessionTypeEnum) {
        com.qiyukf.nimlib.i.i.b().b(Long.valueOf(MsgDBHelper.queryRoamMsgHasMoreTime(str, sessionTypeEnum))).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public long queryRoamMsgHasMoreTimeBlock(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelper.queryRoamMsgHasMoreTime(str, sessionTypeEnum);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> queryRoamMsgHasMoreTagServerId(String str, SessionTypeEnum sessionTypeEnum) {
        com.qiyukf.nimlib.i.i.b().b(Long.valueOf(MsgDBHelper.queryRoamMsgHasMoreServerId(str, sessionTypeEnum))).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public long queryRoamMsgHasMoreTagServerIdBlock(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelper.queryRoamMsgHasMoreServerId(str, sessionTypeEnum);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryMessageListExTime(IMMessage iMMessage, long j, QueryDirectionEnum queryDirectionEnum, int i) {
        return a(MsgDBHelper.queryMessageListEx(null, (com.qiyukf.nimlib.session.d) iMMessage, j, queryDirectionEnum, i, false));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullHistoryById(List<MessageKey> list, boolean z) {
        com.qiyukf.nimlib.biz.d.j.h hVar = new com.qiyukf.nimlib.biz.d.j.h(list, z);
        hVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(hVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistory(IMMessage iMMessage, int i, boolean z) {
        return a(iMMessage, 0L, i, QueryDirectionEnum.QUERY_OLD, z, null, true, null, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistory(IMMessage iMMessage, int i, boolean z, boolean z2) {
        return a(iMMessage, 0L, i, QueryDirectionEnum.QUERY_OLD, z, null, z2, null, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistoryEx(IMMessage iMMessage, long j, int i, QueryDirectionEnum queryDirectionEnum, boolean z) {
        return a(iMMessage, j, i, queryDirectionEnum, z, null, true, null, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistoryExType(IMMessage iMMessage, long j, int i, QueryDirectionEnum queryDirectionEnum, MsgTypeEnum[] msgTypeEnumArr) {
        return a(iMMessage, j, i, queryDirectionEnum, false, msgTypeEnumArr, true, null, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistoryExType(IMMessage iMMessage, long j, int i, QueryDirectionEnum queryDirectionEnum, MsgTypeEnum[] msgTypeEnumArr, boolean z) {
        return a(iMMessage, j, i, queryDirectionEnum, z, msgTypeEnumArr, true, null, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistoryExType(IMMessage iMMessage, long j, int i, QueryDirectionEnum queryDirectionEnum, MsgTypeEnum[] msgTypeEnumArr, boolean z, boolean z2) {
        return a(iMMessage, j, i, queryDirectionEnum, z, msgTypeEnumArr, z2, null, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistoryExType(IMMessage iMMessage, long j, int i, QueryDirectionEnum queryDirectionEnum, MsgTypeEnum[] msgTypeEnumArr, boolean z, boolean z2, IMMessageFilter iMMessageFilter) {
        return a(iMMessage, j, i, queryDirectionEnum, z, msgTypeEnumArr, z2, iMMessageFilter, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistoryExType(IMMessage iMMessage, long j, int i, QueryDirectionEnum queryDirectionEnum, MsgTypeEnum[] msgTypeEnumArr, boolean z, boolean z2, IMMessageFilter iMMessageFilter, boolean z3) {
        return a(iMMessage, j, i, queryDirectionEnum, z, msgTypeEnumArr, z2, iMMessageFilter, z3);
    }

    private static InvocationFuture<List<IMMessage>> a(IMMessage iMMessage, long j, int i, QueryDirectionEnum queryDirectionEnum, boolean z, MsgTypeEnum[] msgTypeEnumArr, boolean z2, IMMessageFilter iMMessageFilter, boolean z3) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", "cancel pull msg history, anchor is ".concat(String.valueOf(iMMessage)));
            return null;
        }
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
        if (dVar.getSessionType() == SessionTypeEnum.Team || dVar.getSessionType() == SessionTypeEnum.SUPER_TEAM) {
            try {
                Long.valueOf(dVar.getSessionId());
            } catch (Exception unused) {
                throw new IllegalArgumentException("sessionID cast to long exception, team sessionID must be Long value String");
            }
        }
        if (msgTypeEnumArr != null) {
            for (MsgTypeEnum msgTypeEnum : msgTypeEnumArr) {
                if (msgTypeEnum == MsgTypeEnum.undef) {
                    throw new IllegalArgumentException("typeEnum params of this method have illegal value");
                }
            }
        }
        boolean z4 = queryDirectionEnum != QueryDirectionEnum.QUERY_OLD;
        com.qiyukf.nimlib.biz.d.i.k kVar = new com.qiyukf.nimlib.biz.d.i.k(dVar.getSessionId(), dVar.getSessionType(), z4 ? iMMessage.getTime() : j, z4 ? j : iMMessage.getTime(), dVar.getServerId(), i, z4, z, msgTypeEnumArr, z2, iMMessageFilter, z3);
        kVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(kVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistory(MsgFullKeywordSearchConfig msgFullKeywordSearchConfig) {
        com.qiyukf.nimlib.biz.d.i.m mVar;
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (msgFullKeywordSearchConfig == null) {
            mVar = null;
        } else {
            mVar = new com.qiyukf.nimlib.biz.d.i.m(w.a(msgFullKeywordSearchConfig.getKeyword()), Math.max(0L, msgFullKeywordSearchConfig.getFromTime()), Math.max(0L, msgFullKeywordSearchConfig.getToTime()), Math.max(0, msgFullKeywordSearchConfig.getSessionLimit()), Math.max(0, msgFullKeywordSearchConfig.getMsgLimit()), msgFullKeywordSearchConfig.isAsc() ? 1 : 2, msgFullKeywordSearchConfig.getP2pList(), msgFullKeywordSearchConfig.getTeamList(), msgFullKeywordSearchConfig.getSenderList(), msgFullKeywordSearchConfig.getMsgTypeList(), msgFullKeywordSearchConfig.getMsgSubtypeList());
        }
        if (mVar == null) {
            com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", "pullMessageHistory failed: ".concat(String.valueOf(msgFullKeywordSearchConfig)));
            jVarB.a(414).b();
            return null;
        }
        mVar.a(jVarB);
        com.qiyukf.nimlib.biz.k.a().a(mVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> pullMessageHistoryOrderByTime(MsgTimingFullKeywordSearchConfig msgTimingFullKeywordSearchConfig) {
        com.qiyukf.nimlib.biz.d.i.n nVar;
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (msgTimingFullKeywordSearchConfig == null) {
            nVar = null;
        } else {
            nVar = new com.qiyukf.nimlib.biz.d.i.n(w.a(msgTimingFullKeywordSearchConfig.getKeyword()), Math.max(0L, msgTimingFullKeywordSearchConfig.getFromTime()), Math.max(0L, msgTimingFullKeywordSearchConfig.getToTime()), Math.max(0, msgTimingFullKeywordSearchConfig.getMsgLimit()), msgTimingFullKeywordSearchConfig.getOrder() == SearchOrderEnum.ASC ? 1 : 2, msgTimingFullKeywordSearchConfig.getP2pList(), msgTimingFullKeywordSearchConfig.getTeamList(), msgTimingFullKeywordSearchConfig.getSenderList(), msgTimingFullKeywordSearchConfig.getMsgTypeList(), msgTimingFullKeywordSearchConfig.getMsgSubtypeList());
        }
        if (nVar == null) {
            com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", "pullMessageHistoryOrderByTime failed: ".concat(String.valueOf(msgTimingFullKeywordSearchConfig)));
            jVarB.a(414).b();
            return null;
        }
        nVar.a(jVarB);
        com.qiyukf.nimlib.biz.k.a().a(nVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<GetMessagesDynamicallyResult> getMessagesDynamically(GetMessagesDynamicallyParam getMessagesDynamicallyParam) {
        com.qiyukf.nimlib.log.c.b.a.c("MsgServiceRemote", String.format("getMessagesDynamically with %s", getMessagesDynamicallyParam));
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (getMessagesDynamicallyParam == null || !getMessagesDynamicallyParam.isValid()) {
            jVarB.a(414).b();
            return null;
        }
        final GetMessagesDynamicallyParam getMessagesDynamicallyParamCreateStandardizedParam = getMessagesDynamicallyParam.createStandardizedParam();
        final String sessionId = getMessagesDynamicallyParamCreateStandardizedParam.getSessionId();
        final SessionTypeEnum sessionType = getMessagesDynamicallyParamCreateStandardizedParam.getSessionType();
        long fromTime = getMessagesDynamicallyParamCreateStandardizedParam.getFromTime();
        long toTime = getMessagesDynamicallyParamCreateStandardizedParam.getToTime();
        final int limit = getMessagesDynamicallyParamCreateStandardizedParam.getLimit();
        GetMessageDirectionEnum direction = getMessagesDynamicallyParamCreateStandardizedParam.getDirection();
        if (direction == null) {
            direction = GetMessageDirectionEnum.FORWARD;
        }
        GetMessageDirectionEnum getMessageDirectionEnum = GetMessageDirectionEnum.BACKWARD;
        if (direction != getMessageDirectionEnum) {
            fromTime = toTime;
        }
        int i = AnonymousClass5.a[aa.a(com.qiyukf.nimlib.push.e.a()).ordinal()];
        if (i != 1) {
            if (i == 2) {
                com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParamCreateStandardizedParam, jVarB);
                return null;
            }
            jVarB.b(new com.qiyukf.nimlib.session.c(false, com.qiyukf.nimlib.session.b.c.a().c(getMessagesDynamicallyParamCreateStandardizedParam))).b();
            return null;
        }
        if (direction == getMessageDirectionEnum && fromTime <= 0) {
            com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParamCreateStandardizedParam, jVarB);
            return null;
        }
        com.qiyukf.nimlib.session.b.d dVarB = com.qiyukf.nimlib.session.b.c.a().b(getMessagesDynamicallyParamCreateStandardizedParam);
        if (dVarB == null) {
            if (com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParamCreateStandardizedParam)) {
                com.qiyukf.nimlib.session.b.c.a().a(sessionId, sessionType, new c.a() { // from class: com.qiyukf.nimlib.biz.f.g$$ExternalSyntheticLambda1
                    @Override // com.qiyukf.nimlib.session.b.c.a
                    public final Object onCallback(Object obj) {
                        return g.a(getMessagesDynamicallyParamCreateStandardizedParam, jVarB, sessionId, sessionType, limit, (IMMessage) obj);
                    }
                });
                return null;
            }
            com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParamCreateStandardizedParam, jVarB);
            return null;
        }
        ArrayList<IMMessage> arrayListA = com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParamCreateStandardizedParam, dVarB);
        com.qiyukf.nimlib.session.b.d dVarA = com.qiyukf.nimlib.session.b.d.a(getMessagesDynamicallyParamCreateStandardizedParam);
        if (com.qiyukf.nimlib.n.e.d(arrayListA) >= limit || dVarB.a(dVarA)) {
            jVarB.b(new com.qiyukf.nimlib.session.c(true, arrayListA)).b();
            return null;
        }
        com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParamCreateStandardizedParam, jVarB);
        return null;
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.biz.f.g$5, reason: invalid class name */
    /* JADX INFO: compiled from: MsgServiceRemote.java */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[aa.values().length];
            a = iArr;
            try {
                iArr[aa.DYNAMIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[aa.REMOTE_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[aa.LOCAL_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Void a(GetMessagesDynamicallyParam getMessagesDynamicallyParam, com.qiyukf.nimlib.i.j jVar, String str, SessionTypeEnum sessionTypeEnum, int i, IMMessage iMMessage) {
        if (iMMessage == null) {
            com.qiyukf.nimlib.log.c.b.a.c("MsgServiceRemote", "on getLastMessage calllback, lastMessage: null");
            com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParam, jVar);
            return null;
        }
        com.qiyukf.nimlib.log.c.b.a.c("MsgServiceRemote", String.format("on getLastMessage calllback, lastMessage: (uuid: %s, serverId: %s, time: %s, content: %s)", iMMessage.getUuid(), Long.valueOf(iMMessage.getServerId()), Long.valueOf(iMMessage.getTime()), iMMessage.getContent()));
        ArrayList<IMMessage> arrayListA = com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParam, com.qiyukf.nimlib.session.b.c.a().d(str, sessionTypeEnum));
        if (com.qiyukf.nimlib.n.e.d(arrayListA) >= i) {
            jVar.b(new com.qiyukf.nimlib.session.c(true, arrayListA)).b();
            return null;
        }
        com.qiyukf.nimlib.session.b.c.a().a(getMessagesDynamicallyParam, jVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> searchMessageHistory(String str, List<String> list, IMMessage iMMessage, int i) {
        return a(MsgDBHelper.searchMessageHistory(str, list, iMMessage, QueryDirectionEnum.QUERY_OLD, i));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> searchMessageHistory(String str, List<String> list, IMMessage iMMessage, QueryDirectionEnum queryDirectionEnum, int i) {
        return a(MsgDBHelper.searchMessageHistory(str, list, iMMessage, queryDirectionEnum, i));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> searchAllMessageHistory(String str, List<String> list, long j, int i) {
        return a(MsgDBHelper.searchAllMessageHistory(str, list, j, i));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> searchMessage(SessionTypeEnum sessionTypeEnum, String str, MsgSearchOption msgSearchOption) {
        if (msgSearchOption == null) {
            msgSearchOption = new MsgSearchOption();
        }
        return a(MsgDBHelper.searchMessage(sessionTypeEnum, str, msgSearchOption));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> searchAllMessage(MsgSearchOption msgSearchOption) {
        if (msgSearchOption == null) {
            msgSearchOption = new MsgSearchOption();
        }
        return a(MsgDBHelper.searchAllMessage(msgSearchOption));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<MsgIndexRecord>> searchAllSession(String str, int i) {
        com.qiyukf.nimlib.i.i.b().b(searchAllSessionBlock(str, i)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<MsgIndexRecord> searchAllSessionBlock(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return com.qiyukf.nimlib.search.a.a.a(com.qiyukf.nimlib.search.a.a(str, i), str);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<MsgIndexRecord>> searchSession(String str, SessionTypeEnum sessionTypeEnum, String str2) {
        com.qiyukf.nimlib.i.i.b().b(searchSessionBlock(str, sessionTypeEnum, str2)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<MsgIndexRecord> searchSessionBlock(String str, SessionTypeEnum sessionTypeEnum, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return com.qiyukf.nimlib.search.a.a.a(com.qiyukf.nimlib.search.a.a(sessionTypeEnum, str2, str), str);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture queryRecentContacts() {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        List<RecentContact> listQueryRecentContacts = MsgDBHelper.queryRecentContacts();
        c(listQueryRecentContacts);
        jVarB.b(listQueryRecentContacts).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<RecentContact> queryRecentContactsBlock() {
        List<RecentContact> listQueryRecentContacts = MsgDBHelper.queryRecentContacts();
        c(listQueryRecentContacts);
        return listQueryRecentContacts;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<RecentContact>> queryRecentContacts(int i) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        List<RecentContact> listQueryRecentContacts = MsgDBHelper.queryRecentContacts(a(i));
        c(listQueryRecentContacts);
        jVarB.b(listQueryRecentContacts).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<RecentContact>> queryRecentContacts(RecentContact recentContact, QueryDirectionEnum queryDirectionEnum, int i) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        List<RecentContact> listQueryRecentContacts = MsgDBHelper.queryRecentContacts(recentContact, queryDirectionEnum, a(i));
        c(listQueryRecentContacts);
        jVarB.b(listQueryRecentContacts).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<RecentContact> queryRecentContactsBlock(int i) {
        List<RecentContact> listQueryRecentContacts = MsgDBHelper.queryRecentContacts(a(i));
        c(listQueryRecentContacts);
        return listQueryRecentContacts;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<RecentContact> queryRecentContactsBlock(RecentContact recentContact, QueryDirectionEnum queryDirectionEnum, int i) {
        List<RecentContact> listQueryRecentContacts = MsgDBHelper.queryRecentContacts(recentContact, queryDirectionEnum, a(i));
        c(listQueryRecentContacts);
        return listQueryRecentContacts;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<RecentContact>> queryRecentContacts(MsgTypeEnum msgTypeEnum) {
        com.qiyukf.nimlib.i.i.b().b(a(com.qiyukf.nimlib.n.e.b(msgTypeEnum))).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<RecentContact> queryRecentContactsBlock(MsgTypeEnum msgTypeEnum) {
        return a(com.qiyukf.nimlib.n.e.b(msgTypeEnum));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<RecentContact>> queryRecentContacts(Set<MsgTypeEnum> set) {
        com.qiyukf.nimlib.i.i.b().b(a(set)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<RecentContact> queryRecentContactsBlock(Set<MsgTypeEnum> set) {
        return a(set);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void updateRecent(RecentContact recentContact) {
        MsgDBHelper.updateRecent(recentContact);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void updateRecentAndNotify(RecentContact recentContact) {
        MsgDBHelper.updateRecent(recentContact);
        com.qiyukf.nimlib.i.b.a((s) recentContact);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void updateRecentByMessage(IMMessage iMMessage, boolean z) {
        s sVarD = com.qiyukf.nimlib.session.k.d((com.qiyukf.nimlib.session.d) iMMessage);
        if (z) {
            com.qiyukf.nimlib.i.b.a(sVarD);
        }
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void updateRoamMsgHasMoreTag(IMMessage iMMessage) {
        if (iMMessage == null) {
            com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", "updateRoamMsgHasMoreTag error, tag is null");
            return;
        }
        RoamMsgHasMoreOption roamMsgHasMoreOption = new RoamMsgHasMoreOption(iMMessage.getSessionId(), iMMessage.getSessionType(), iMMessage.getTime(), iMMessage.getServerId());
        com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", "updateRoamMsgHasMoreTag, option is ".concat(String.valueOf(roamMsgHasMoreOption)));
        MsgDBHelper.updateRoamMsgHasMoreTime(roamMsgHasMoreOption);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void deleteChattingHistory(IMMessage iMMessage) {
        deleteChattingHistory(iMMessage, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void deleteChattingHistory(IMMessage iMMessage, boolean z) {
        if (MsgDBHelper.deleteMessage((com.qiyukf.nimlib.session.d) iMMessage, !z) > 0 && com.qiyukf.nimlib.session.k.d(iMMessage)) {
            s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(iMMessage.getSessionId(), iMMessage.getSessionType());
            if (sVarQueryRecentContact == null) {
                return;
            }
            sVarQueryRecentContact.a(Math.max(0, sVarQueryRecentContact.getUnreadCount() - 1));
            MsgDBHelper.saveRecent(sVarQueryRecentContact);
        }
        com.qiyukf.nimlib.session.k.b(iMMessage);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void deleteChattingHistory(List<IMMessage> list, boolean z) {
        com.qiyukf.nimlib.session.k.b(list, z);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void clearChattingHistory(String str, SessionTypeEnum sessionTypeEnum) {
        clearChattingHistory(str, sessionTypeEnum, true);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void clearChattingHistory(String str, SessionTypeEnum sessionTypeEnum, boolean z) {
        com.qiyukf.nimlib.session.b.c.a().b(str, sessionTypeEnum);
        MsgDBHelper.clearMessage(str, sessionTypeEnum, !z);
        s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(str, sessionTypeEnum);
        if (sVarQueryRecentContact != null) {
            com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.session.k.a(str, sessionTypeEnum, sVarQueryRecentContact));
        }
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> clearMsgDatabase(boolean z) {
        com.qiyukf.nimlib.session.b.c.a().d();
        if (z) {
            List<RecentContact> listQueryRecentContacts = MsgDBHelper.queryRecentContacts();
            if (listQueryRecentContacts.size() != 0) {
                com.qiyukf.nimlib.biz.d.i.h hVar = new com.qiyukf.nimlib.biz.d.i.h();
                for (RecentContact recentContact : listQueryRecentContacts) {
                    if (recentContact.getSessionType() != SessionTypeEnum.SUPER_TEAM && recentContact.getSessionType() != SessionTypeEnum.System) {
                        hVar.a(recentContact.getContactId(), recentContact.getSessionType());
                    }
                }
                if (!hVar.i()) {
                    com.qiyukf.nimlib.biz.k.a().a(hVar, com.qiyukf.nimlib.biz.g.a.b);
                }
            }
        }
        MsgDBHelper.clearAllMessages(z);
        if (z) {
            com.qiyukf.nimlib.i.b.b((s) null);
        }
        com.qiyukf.nimlib.i.i.b().a(200).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> deleteMsgSelf(final IMMessage iMMessage, String str) {
        com.qiyukf.nimlib.biz.d.i.f fVar = new com.qiyukf.nimlib.biz.d.i.f(iMMessage, str);
        fVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(fVar, com.qiyukf.nimlib.biz.g.a.b) { // from class: com.qiyukf.nimlib.biz.f.g.9
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.e()) {
                    g.this.deleteChattingHistory(iMMessage);
                }
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> deleteMsgSelf(final List<IMMessage> list, String str) {
        com.qiyukf.nimlib.biz.d.i.e eVar = new com.qiyukf.nimlib.biz.d.i.e(list, str);
        eVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(eVar, com.qiyukf.nimlib.biz.g.a.b) { // from class: com.qiyukf.nimlib.biz.f.g.10
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.e()) {
                    com.qiyukf.nimlib.session.k.b(list, true);
                }
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public boolean everBeenDeleted(IMMessage iMMessage) {
        long time = iMMessage.getTime();
        if (time > 0 && com.qiyukf.nimlib.biz.n.y() > time) {
            return true;
        }
        SessionTypeEnum sessionType = iMMessage.getSessionType();
        String sessionId = iMMessage.getSessionId();
        if (time > 0 && sessionType != null && !TextUtils.isEmpty(sessionId) && MsgDBHelper.isRemovedWhileClearingSession(sessionId, sessionType, time)) {
            com.qiyukf.nimlib.log.c.b.a.c("MsgServiceRemote", "deleted by session, sessionId=" + sessionId + ", content=" + iMMessage.getContent());
            return true;
        }
        String uuid = iMMessage.getUuid();
        if (TextUtils.isEmpty(uuid) || !MsgDBHelper.hasDeleteTag(iMMessage.getUuid())) {
            return false;
        }
        com.qiyukf.nimlib.log.c.b.a.c("MsgServiceRemote", "deleted by id, uuid=" + uuid + ", content=" + iMMessage.getContent());
        return true;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void deleteRecentContact(RecentContact recentContact) {
        deleteRecentContact(recentContact.getContactId(), recentContact.getSessionType(), DeleteTypeEnum.LOCAL_AND_REMOTE, true);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void deleteRecentContact2(String str, SessionTypeEnum sessionTypeEnum) {
        if (a(str, sessionTypeEnum)) {
            throw new IllegalArgumentException("Invalid param");
        }
        deleteRecentContact(str, sessionTypeEnum, DeleteTypeEnum.LOCAL_AND_REMOTE, true);
        s sVar = new s();
        sVar.a(str);
        sVar.a(sessionTypeEnum);
        com.qiyukf.nimlib.i.b.b(sVar);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> deleteRoamingRecentContact(String str, SessionTypeEnum sessionTypeEnum) {
        if (a(str, sessionTypeEnum)) {
            throw new IllegalArgumentException("Invalid param");
        }
        if (sessionTypeEnum == SessionTypeEnum.SUPER_TEAM) {
            return null;
        }
        com.qiyukf.nimlib.biz.d.i.h hVar = new com.qiyukf.nimlib.biz.d.i.h();
        hVar.a(com.qiyukf.nimlib.i.i.b());
        hVar.a(str, sessionTypeEnum);
        com.qiyukf.nimlib.biz.k.a().a(hVar, com.qiyukf.nimlib.biz.g.a.b);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> deleteRecentContact(String str, SessionTypeEnum sessionTypeEnum, DeleteTypeEnum deleteTypeEnum, boolean z) {
        SessionTypeEnum sessionTypeEnum2;
        boolean zMatches = true;
        if (z) {
            a(str, sessionTypeEnum, false, true, (com.qiyukf.nimlib.i.j) null);
            com.qiyukf.nimlib.l.a.a(com.qiyukf.nimlib.l.h.MESSAGE);
        }
        if (DeleteTypeEnum.deleteLocal(deleteTypeEnum)) {
            MsgDBHelper.deleteRecentContact(str, sessionTypeEnum);
        }
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (!DeleteTypeEnum.deleteRemote(deleteTypeEnum)) {
            jVarB.b((Object) null).b();
            return null;
        }
        if (TextUtils.isEmpty(str) || !(sessionTypeEnum == (sessionTypeEnum2 = SessionTypeEnum.P2P) || sessionTypeEnum == SessionTypeEnum.Team)) {
            zMatches = false;
        } else if (sessionTypeEnum != sessionTypeEnum2) {
            zMatches = str.matches("[0-9]+");
        }
        if (!zMatches) {
            jVarB.a(414).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.i.h hVar = new com.qiyukf.nimlib.biz.d.i.h();
        hVar.a(jVarB);
        hVar.a(str, sessionTypeEnum);
        com.qiyukf.nimlib.biz.k.a().a(hVar, com.qiyukf.nimlib.biz.g.a.b);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void deleteRoamMsgHasMoreTag(String str, SessionTypeEnum sessionTypeEnum) {
        com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", String.format("deleteRoamMsgHasMoreTag, sessionId=%s, sessionType=%s", str, sessionTypeEnum));
        MsgDBHelper.deleteRoamMsgHasMoreTime(str, sessionTypeEnum);
        com.qiyukf.nimlib.i.i.b().b();
    }

    private static boolean a(String str, SessionTypeEnum sessionTypeEnum) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return (sessionTypeEnum == SessionTypeEnum.Team || sessionTypeEnum == SessionTypeEnum.SUPER_TEAM) && !str.matches("[0-9]+");
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public AbortableFuture<String> transVoiceToText(String str, String str2, long j) {
        return transVoiceToTextEnableForce(str, str2, j, NimNosSceneKeyConstant.NIM_DEFAULT_IM, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public AbortableFuture<String> transVoiceToTextAtScene(String str, String str2, long j, String str3) {
        return transVoiceToTextEnableForce(str, str2, j, str3, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public AbortableFuture<String> transVoiceToTextEnableForce(String str, String str2, final long j, String str3, boolean z) {
        if (str2 == null || !new File(str2).exists()) {
            throw new IllegalArgumentException("Invalid audio path.");
        }
        final int iB = com.qiyukf.share.media.b.a(str2) ? com.qiyukf.share.media.b.b(str2) : 16000;
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (TextUtils.isEmpty(str)) {
            com.qiyukf.nimlib.net.a.b.a.a().a(str2, com.qiyukf.nimlib.n.m.b(str2), jVarB, str3, z, new com.qiyukf.nimlib.net.a.b.c() { // from class: com.qiyukf.nimlib.biz.f.g.11
                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj, long j2, long j3) {
                }

                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj, String str4) {
                    g.b(str4, j, iB, jVarB);
                }

                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj, int i, String str4) {
                    jVarB.a(i).b();
                }

                @Override // com.qiyukf.nimlib.net.a.b.c
                public final void a(Object obj) {
                    a(obj, 400, (String) null);
                }
            });
            return null;
        }
        b(str, j, iB, jVarB);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, long j, int i, com.qiyukf.nimlib.i.j jVar) {
        com.qiyukf.nimlib.biz.d.e.p pVar = new com.qiyukf.nimlib.biz.d.e.p();
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(0, "AAC");
        cVar.a(1, String.valueOf(i));
        cVar.a(2, str);
        cVar.a(3, j);
        pVar.a(cVar);
        pVar.a(jVar);
        com.qiyukf.nimlib.biz.k.a().a(pVar);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void setChattingAccount(String str, SessionTypeEnum sessionTypeEnum) {
        if (MsgService.MSG_CHATTING_ACCOUNT_ALL.equals(str)) {
            com.qiyukf.nimlib.h.a(str);
            com.qiyukf.nimlib.l.a.a(com.qiyukf.nimlib.l.h.c);
            return;
        }
        if (str == null) {
            str = "";
        }
        com.qiyukf.nimlib.h.a(com.qiyukf.nimlib.session.k.a(str, sessionTypeEnum.getValue()));
        if (w.b((CharSequence) str)) {
            clearUnreadCount(str, sessionTypeEnum);
        }
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public int getTotalUnreadCount() {
        return MsgDBHelper.queryUnreadMsgCount();
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public int getTotalUnreadCount(boolean z) {
        boolean zA;
        int unreadCount = 0;
        for (RecentContact recentContact : queryRecentContactsBlock()) {
            if (recentContact.getSessionType() == SessionTypeEnum.P2P) {
                zA = com.qiyukf.nimlib.user.c.a(recentContact.getContactId());
            } else {
                zA = recentContact.getSessionType() != SessionTypeEnum.Team ? !(recentContact.getSessionType() == SessionTypeEnum.SUPER_TEAM && com.qiyukf.nimlib.team.b.a(SuperTeamDBHelper.getMemberBits(recentContact.getContactId()))) : !com.qiyukf.nimlib.team.b.a(TeamDBHelper.getMemberBits(recentContact.getContactId()));
            }
            if (z == zA) {
                unreadCount += recentContact.getUnreadCount();
            }
        }
        return unreadCount;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public int getUnreadCountBySessionType(SessionTypeEnum sessionTypeEnum) {
        int unreadCount = 0;
        if (sessionTypeEnum == null) {
            com.qiyukf.nimlib.log.c.b.a.e("MsgServiceRemote", "get unread count by session type with null");
            return 0;
        }
        List<RecentContact> listQueryUnreadRecentContactBySessionType = MsgDBHelper.queryUnreadRecentContactBySessionType(sessionTypeEnum);
        if (com.qiyukf.nimlib.n.e.b((Collection) listQueryUnreadRecentContactBySessionType)) {
            return 0;
        }
        Iterator<RecentContact> it = listQueryUnreadRecentContactBySessionType.iterator();
        while (it.hasNext()) {
            unreadCount += it.next().getUnreadCount();
        }
        return unreadCount;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryUnreadMessageList(String str, SessionTypeEnum sessionTypeEnum) {
        return a(queryUnreadMessageListBlock(str, sessionTypeEnum));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<IMMessage> queryUnreadMessageListBlock(String str, SessionTypeEnum sessionTypeEnum) {
        if (MsgDBHelper.queryRecentContact(str, sessionTypeEnum) == null) {
            return new ArrayList();
        }
        long jQuerySessionReadTimeTag = MsgDBHelper.querySessionReadTimeTag(str, sessionTypeEnum);
        ArrayList<IMMessage> arrayListQueryUnreadMessages = MsgDBHelper.queryUnreadMessages(str, sessionTypeEnum, jQuerySessionReadTimeTag);
        if (arrayListQueryUnreadMessages.isEmpty()) {
            return arrayListQueryUnreadMessages;
        }
        ArrayList arrayList = new ArrayList();
        for (IMMessage iMMessage : arrayListQueryUnreadMessages) {
            if (com.qiyukf.nimlib.session.k.a(iMMessage, false, jQuerySessionReadTimeTag)) {
                arrayList.add(iMMessage);
            }
        }
        return arrayList;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> clearUnreadCount(String str, SessionTypeEnum sessionTypeEnum) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (TextUtils.isEmpty(str)) {
            jVarB.a(414).b();
            return null;
        }
        a(str, sessionTypeEnum, true, true, jVarB);
        com.qiyukf.nimlib.l.a.a(com.qiyukf.nimlib.l.h.MESSAGE);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<SessionAckInfo>> clearUnreadCount(List<Pair<String, SessionTypeEnum>> list) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            jVarB.b(new ArrayList(0)).b();
            return null;
        }
        a(list, jVarB);
        com.qiyukf.nimlib.l.a.a(com.qiyukf.nimlib.l.h.MESSAGE);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<SessionAckInfo>> clearUnreadCount(SessionTypeEnum sessionTypeEnum) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (sessionTypeEnum == null) {
            jVarB.a(414).b();
            return null;
        }
        ArrayList arrayListA = com.qiyukf.nimlib.n.e.a((Collection) MsgDBHelper.queryUnreadRecentContactBySessionType(sessionTypeEnum), true, new e.a() { // from class: com.qiyukf.nimlib.biz.f.g$$ExternalSyntheticLambda0
            @Override // com.qiyukf.nimlib.n.e.a
            public final Object transform(Object obj) {
                return g.a((RecentContact) obj);
            }
        });
        if (com.qiyukf.nimlib.n.e.b((Collection) arrayListA)) {
            jVarB.b(new ArrayList(0)).b();
            return null;
        }
        a(arrayListA, jVarB);
        com.qiyukf.nimlib.l.a.a(com.qiyukf.nimlib.l.h.MESSAGE);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Pair a(RecentContact recentContact) {
        if (recentContact == null) {
            return null;
        }
        return new Pair(recentContact.getContactId(), recentContact.getSessionType());
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> sendMessageReceipt(String str, IMMessage iMMessage) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (iMMessage == null || iMMessage.getSessionType() != SessionTypeEnum.P2P || iMMessage.getDirect() != MsgDirectionEnum.In) {
            jVarB.a((Throwable) new IllegalArgumentException("input message is illegal")).b();
            return null;
        }
        long time = iMMessage.getTime();
        String uuid = iMMessage.getUuid();
        if (time > 0 && f.a.a.a(new MessageReceipt(str, time))) {
            q qVar = new q(str, uuid, time);
            qVar.a(jVarB);
            com.qiyukf.nimlib.biz.k.a().a(qVar, com.qiyukf.nimlib.biz.g.a.b);
        } else {
            jVarB.b((Object) null).b();
        }
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void updateIMMessageStatus(IMMessage iMMessage) {
        MsgDBHelper.updateMessageStatus((com.qiyukf.nimlib.session.d) iMMessage);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void updateIMMessage(IMMessage iMMessage) {
        MsgDBHelper.updateMessageLocalExt((com.qiyukf.nimlib.session.d) iMMessage);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> sendCustomNotification(CustomNotification customNotification) {
        int i;
        if (TextUtils.isEmpty(customNotification.getSessionId()) || customNotification.getSessionType() == null) {
            throw new IllegalArgumentException("illegal receiver");
        }
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(2, customNotification.getSessionId());
        if (customNotification.getSessionType() == SessionTypeEnum.P2P) {
            i = 100;
        } else if (customNotification.getSessionType() == SessionTypeEnum.Team) {
            i = 101;
        } else if (customNotification.getSessionType() == SessionTypeEnum.SUPER_TEAM) {
            i = 103;
        } else {
            i = customNotification.getSessionType() == SessionTypeEnum.Ysf ? 102 : 0;
        }
        if (i != 0) {
            cVar.a(1, i);
        }
        cVar.a(5, customNotification.getContent());
        if (!customNotification.isSendToOnlineUserOnly()) {
            cVar.a(7, 1);
        }
        if (!TextUtils.isEmpty(customNotification.getApnsText())) {
            cVar.a(8, customNotification.getApnsText());
        }
        String strA = com.qiyukf.nimlib.session.k.a(customNotification.getPushPayload());
        if (!TextUtils.isEmpty(strA)) {
            cVar.a(9, strA);
        }
        if (customNotification.getConfig() != null) {
            if (!customNotification.getConfig().enablePush) {
                cVar.a(107, 0);
            }
            if (customNotification.getConfig().enablePushNick) {
                cVar.a(110, 1);
            }
            if (!customNotification.getConfig().enableUnreadCount) {
                cVar.a(109, 0);
            }
        }
        if (customNotification.getNIMAntiSpamOption() != null) {
            cVar.a(12, customNotification.getNIMAntiSpamOption().enable ? 1 : 0);
            cVar.a(13, customNotification.getNIMAntiSpamOption().content);
        }
        cVar.a(21, customNotification.getEnv());
        com.qiyukf.nimlib.biz.d.i.d dVar = new com.qiyukf.nimlib.biz.d.i.d(i);
        dVar.a(cVar);
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(dVar, com.qiyukf.nimlib.biz.g.a.a) { // from class: com.qiyukf.nimlib.biz.f.g.12
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                jVarB.a(aVar.h()).b();
            }
        });
        return null;
    }

    private void a(@NonNull List<Pair<String, SessionTypeEnum>> list, com.qiyukf.nimlib.i.j jVar) {
        SessionTypeEnum sessionTypeEnum;
        ArrayList arrayList = new ArrayList(list.size());
        ArrayList arrayList2 = new ArrayList(list.size());
        for (Pair<String, SessionTypeEnum> pair : list) {
            if (pair != null) {
                String str = (String) pair.first;
                if (!TextUtils.isEmpty(str) && (sessionTypeEnum = (SessionTypeEnum) pair.second) != null) {
                    s sVarA = a(str, sessionTypeEnum, false, false, (com.qiyukf.nimlib.i.j) null);
                    if (sVarA != null) {
                        arrayList.add(sVarA);
                    }
                    if (com.qiyukf.nimlib.c.h().sessionReadAck) {
                        long jA = x.a(str, sessionTypeEnum);
                        if (jA > 0 && x.c(str, sessionTypeEnum, jA)) {
                            arrayList2.add(pair);
                        }
                    }
                }
            }
        }
        a(arrayList2, jVar, 0, new ArrayList<>());
        if (com.qiyukf.nimlib.n.e.c((Collection) arrayList)) {
            com.qiyukf.nimlib.i.b.e(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List<Pair<String, SessionTypeEnum>> list, com.qiyukf.nimlib.i.j jVar, int i, @NonNull ArrayList<SessionAckInfo> arrayList) {
        int iMin = Math.min(i + 50, list.size());
        if (iMin <= i) {
            jVar.b(arrayList).b();
        } else {
            com.qiyukf.nimlib.biz.d.i.a aVar = new com.qiyukf.nimlib.biz.d.i.a(list.subList(i, iMin));
            com.qiyukf.nimlib.biz.k.a().a(new AnonymousClass13(aVar, new com.qiyukf.nimlib.biz.g.a(1, 10), aVar, arrayList, list, jVar, iMin));
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.biz.f.g$13, reason: invalid class name */
    /* JADX INFO: compiled from: MsgServiceRemote.java */
    public class AnonymousClass13 extends com.qiyukf.nimlib.biz.g.b {
        final /* synthetic */ com.qiyukf.nimlib.biz.d.i.a a;
        final /* synthetic */ ArrayList b;
        final /* synthetic */ List c;
        final /* synthetic */ com.qiyukf.nimlib.i.j d;
        final /* synthetic */ int e;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass13(com.qiyukf.nimlib.biz.d.a aVar, com.qiyukf.nimlib.biz.g.a aVar2, com.qiyukf.nimlib.biz.d.i.a aVar3, ArrayList arrayList, List list, com.qiyukf.nimlib.i.j jVar, int i) {
            super(aVar, aVar2);
            this.a = aVar3;
            this.b = arrayList;
            this.c = list;
            this.d = jVar;
            this.e = i;
        }

        @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
        public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
            if (aVar instanceof com.qiyukf.nimlib.biz.e.k.b) {
                List<com.qiyukf.nimlib.push.packet.b.c> listI = this.a.i();
                com.qiyukf.nimlib.biz.e.k.b bVar = (com.qiyukf.nimlib.biz.e.k.b) aVar;
                short sH = aVar.h();
                final ArrayList arrayListA = com.qiyukf.nimlib.n.e.a((Collection) ((sH == 200 || sH == 700) ? bVar.j() : listI), false, (e.a) new n$$ExternalSyntheticLambda0());
                this.b.addAll(arrayListA);
                com.qiyukf.nimlib.n.e.g(com.qiyukf.nimlib.n.e.e(listI, new e.a() { // from class: com.qiyukf.nimlib.biz.f.g$13$$ExternalSyntheticLambda1
                    @Override // com.qiyukf.nimlib.n.e.a
                    public final Object transform(Object obj) {
                        return g.AnonymousClass13.a(arrayListA, (com.qiyukf.nimlib.push.packet.b.c) obj);
                    }
                }), new e.a() { // from class: com.qiyukf.nimlib.biz.f.g$13$$ExternalSyntheticLambda2
                    @Override // com.qiyukf.nimlib.n.e.a
                    public final Object transform(Object obj) {
                        return g.AnonymousClass13.a((com.qiyukf.nimlib.push.packet.b.c) obj);
                    }
                });
            }
            g.this.a((List<Pair<String, SessionTypeEnum>>) this.c, this.d, this.e, (ArrayList<SessionAckInfo>) this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Boolean a(ArrayList arrayList, com.qiyukf.nimlib.push.packet.b.c cVar) {
            if (cVar == null) {
                return null;
            }
            y yVarA = y.a(cVar);
            final String sessionId = yVarA.getSessionId();
            final SessionTypeEnum sessionType = yVarA.getSessionType();
            return Boolean.valueOf(!com.qiyukf.nimlib.n.e.c(arrayList, new e.a() { // from class: com.qiyukf.nimlib.biz.f.g$13$$ExternalSyntheticLambda0
                @Override // com.qiyukf.nimlib.n.e.a
                public final Object transform(Object obj) {
                    return g.AnonymousClass13.a(sessionId, sessionType, (SessionAckInfo) obj);
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
    }

    @Nullable
    private static s a(@Nullable String str, @Nullable SessionTypeEnum sessionTypeEnum, boolean z, boolean z2, @Nullable com.qiyukf.nimlib.i.j jVar) {
        s sVar = null;
        if (!w.a((CharSequence) str) && sessionTypeEnum != null) {
            long jA = com.qiyukf.nimlib.c.h().sessionReadAck ? x.a(str, sessionTypeEnum) : -1L;
            s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(str, sessionTypeEnum);
            if (sVarQueryRecentContact != null && sVarQueryRecentContact.getUnreadCount() > 0) {
                MsgDBHelper.setRecentRead(str, sessionTypeEnum.getValue());
                sVarQueryRecentContact.a(0);
                com.qiyukf.nimlib.session.k.a(sVarQueryRecentContact);
                if (z) {
                    com.qiyukf.nimlib.i.b.a(sVarQueryRecentContact);
                }
                sVar = sVarQueryRecentContact;
            }
            if (z2) {
                x.a(str, sessionTypeEnum, jA, jVar);
            }
        }
        return sVar;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void clearAllUnreadCount() {
        List<RecentContact> listQueryAllUnreadRecentContact = MsgDBHelper.queryAllUnreadRecentContact();
        if (listQueryAllUnreadRecentContact == null || listQueryAllUnreadRecentContact.isEmpty()) {
            return;
        }
        ArrayList<s> arrayList = new ArrayList();
        Iterator<RecentContact> it = listQueryAllUnreadRecentContact.iterator();
        while (it.hasNext()) {
            arrayList.add((s) it.next());
        }
        for (s sVar : arrayList) {
            String contactId = sVar.getContactId();
            SessionTypeEnum sessionType = sVar.getSessionType();
            long jA = com.qiyukf.nimlib.c.h().sessionReadAck ? x.a(contactId, sessionType) : -1L;
            MsgDBHelper.setRecentRead(contactId, sessionType.getValue());
            sVar.a(0);
            com.qiyukf.nimlib.session.k.a(sVar);
            x.a(contactId, sessionType, jA, null);
        }
        com.qiyukf.nimlib.i.b.e(arrayList);
        com.qiyukf.nimlib.l.a.a(com.qiyukf.nimlib.l.h.c);
    }

    private static InvocationFuture<List<IMMessage>> a(List<IMMessage> list) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        b(list);
        jVarB.b(list).b();
        return null;
    }

    private static void b(List<IMMessage> list) {
        Iterator<IMMessage> it = list.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
    }

    private static void a(IMMessage iMMessage) {
        String uuid = iMMessage.getUuid();
        if (iMMessage.getStatus() == MsgStatusEnum.fail) {
            if (com.qiyukf.nimlib.session.e.a().c(uuid)) {
                iMMessage.setStatus(MsgStatusEnum.sending);
            }
            if (com.qiyukf.nimlib.session.e.a().h(uuid)) {
                iMMessage.setAttachStatus(AttachStatusEnum.transferring);
                return;
            }
            return;
        }
        if (com.qiyukf.nimlib.session.e.a().f(uuid)) {
            iMMessage.setAttachStatus(AttachStatusEnum.transferring);
        }
    }

    private static void c(List<RecentContact> list) {
        Iterator<RecentContact> it = list.iterator();
        while (it.hasNext()) {
            com.qiyukf.nimlib.session.k.a((s) it.next());
        }
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<ArrayList<IMMessage>> searchRoamingMsg(String str, long j, long j2, String str2, int i, boolean z) {
        com.qiyukf.nimlib.biz.d.i.p pVar = new com.qiyukf.nimlib.biz.d.i.p(str, j, j2, str2, i, z);
        pVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(pVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void registerIMMessageFilter(IMMessageFilter iMMessageFilter) {
        com.qiyukf.nimlib.session.k.a(iMMessageFilter);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void registerShouldShowNotificationWhenRevokeFilter(ShowNotificationWhenRevokeFilter showNotificationWhenRevokeFilter) {
        com.qiyukf.nimlib.session.k.a(showNotificationWhenRevokeFilter);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> revokeMessage(IMMessage iMMessage) {
        a(iMMessage, (String) null, (Map<String, Object>) null, true, (String) null, (String) null);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> revokeMessageEx(IMMessage iMMessage, String str, Map<String, Object> map) {
        a(iMMessage, str, map, true, (String) null, (String) null);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> revokeMessage(IMMessage iMMessage, String str, Map<String, Object> map, boolean z) {
        a(iMMessage, str, map, z, (String) null, (String) null);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> revokeMessage(IMMessage iMMessage, String str, Map<String, Object> map, boolean z, String str2) {
        a(iMMessage, str, map, z, str2, (String) null);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> revokeMessage(IMMessage iMMessage, String str, Map<String, Object> map, boolean z, String str2, String str3) {
        a(iMMessage, str, map, z, str2, str3);
        return null;
    }

    private static void a(IMMessage iMMessage, String str, Map<String, Object> map, boolean z, String str2, String str3) {
        String str4;
        com.qiyukf.nimlib.biz.d.i.o oVar;
        String strQ = com.qiyukf.nimlib.c.q();
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
        if (dVar == null || dVar.getServerId() == 0) {
            if (dVar == null) {
                str4 = " msg == null";
            } else {
                str4 = "serverId = " + dVar.getServerId() + " , sessionId = " + iMMessage.getSessionId() + " , self account = " + strQ;
            }
            com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", str4);
            jVarB.a(414).b();
            return;
        }
        String strA = com.qiyukf.nimlib.session.k.a(map);
        if (!TextUtils.equals(iMMessage.getFromAccount(), strQ)) {
            if (b(iMMessage)) {
                oVar = new com.qiyukf.nimlib.biz.d.i.o((com.qiyukf.nimlib.session.d) iMMessage, strQ, str, strA, z, str2, str3);
            } else {
                com.qiyukf.nimlib.log.c.b.a.d("MsgServiceRemote", "from account = " + dVar.getFromAccount() + " , self account = " + strQ + ", session type = " + iMMessage.getSessionType());
                jVarB.a(414).b();
                return;
            }
        } else {
            oVar = new com.qiyukf.nimlib.biz.d.i.o((com.qiyukf.nimlib.session.d) iMMessage, null, str, strA, z, str2, str3);
        }
        oVar.a(jVarB);
        com.qiyukf.nimlib.biz.k.a().a(oVar);
    }

    private static boolean b(IMMessage iMMessage) {
        if (iMMessage.getSessionType() != SessionTypeEnum.Team) {
            return false;
        }
        TeamMemberType memberType = TeamDBHelper.getMemberType(iMMessage.getSessionId(), com.qiyukf.nimlib.c.q());
        return memberType == TeamMemberType.Manager || memberType == TeamMemberType.Owner;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> importRecentSessions(List<Pair<String, SessionTypeEnum>> list) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (list != null && !list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (Pair<String, SessionTypeEnum> pair : list) {
                if (pair != null && !TextUtils.isEmpty((CharSequence) pair.first)) {
                    s sVar = new s();
                    sVar.a((String) pair.first);
                    sVar.a((SessionTypeEnum) pair.second);
                    arrayList.add(sVar);
                }
            }
            MsgDBHelper.importRecentContactByUnionKey(arrayList);
        }
        jVarB.b((Object) null).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public LocalAntiSpamResult checkLocalAntiSpam(String str, String str2) {
        return com.qiyukf.nimlib.c.c.a(str, str2);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public RecentContact createEmptyRecentContact(String str, SessionTypeEnum sessionTypeEnum, long j, long j2, boolean z) {
        return a(str, sessionTypeEnum, j, j2, z, false);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public RecentContact createEmptyRecentContact(String str, SessionTypeEnum sessionTypeEnum, long j, long j2, boolean z, boolean z2) {
        return a(str, sessionTypeEnum, j, j2, z, z2);
    }

    private static RecentContact a(String str, SessionTypeEnum sessionTypeEnum, long j, long j2, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str) || sessionTypeEnum == null || j2 <= 0) {
            return null;
        }
        s sVar = new s();
        sVar.a(str);
        sVar.a(sessionTypeEnum);
        sVar.setTag(j);
        sVar.a(j2);
        sVar.setMsgStatus(MsgStatusEnum.success);
        if (z2) {
            sVar.setLastMsg(MsgDBHelper.queryLatestMessage(str, sessionTypeEnum.getValue()));
        }
        if (z && MsgDBHelper.queryRecentContact(str, sessionTypeEnum) == null) {
            MsgDBHelper.saveRecent(sVar);
            com.qiyukf.nimlib.i.b.a(sVar);
        }
        return sVar;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public RecentContact queryRecentContact(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelper.queryRecentContact(str, sessionTypeEnum);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> cancelUploadAttachment(IMMessage iMMessage) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        a.c cVarG = com.qiyukf.nimlib.session.e.a().g(iMMessage.getUuid());
        if (cVarG == null) {
            jVarB.a(-1).b();
            return null;
        }
        com.qiyukf.nimlib.net.a.b.a.a().a(cVarG);
        jVarB.a(200).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public AbortableFuture<Void> exportAllMessage(IMsgExportProcessor iMsgExportProcessor, boolean z) {
        if (iMsgExportProcessor == null) {
            throw new IllegalArgumentException("exportProcessor must not null");
        }
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.j.b.a().a(iMsgExportProcessor, jVarB, z);
        return new com.qiyukf.nimlib.i.g<com.qiyukf.nimlib.i.j>(jVarB) { // from class: com.qiyukf.nimlib.biz.f.g.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.qiyukf.nimlib.sdk.AbortableFuture
            public final boolean abort() {
                com.qiyukf.nimlib.j.b.a().a((com.qiyukf.nimlib.i.j) this.c);
                return false;
            }
        };
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public AbortableFuture<Void> importAllMessage(IMsgImportProcessor iMsgImportProcessor, boolean z) {
        if (iMsgImportProcessor == null) {
            throw new IllegalArgumentException("importProcessor must not null");
        }
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.j.b.a().a(jVarB, iMsgImportProcessor, z);
        return new com.qiyukf.nimlib.i.g<com.qiyukf.nimlib.i.j>(jVarB) { // from class: com.qiyukf.nimlib.biz.f.g.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.qiyukf.nimlib.sdk.AbortableFuture
            public final boolean abort() {
                com.qiyukf.nimlib.j.b.a().a((com.qiyukf.nimlib.i.j) this.c);
                return false;
            }
        };
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void clearServerHistory(String str, boolean z) {
        a(str, SessionTypeEnum.P2P, z, false, (String) null);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void clearServerHistory(String str, SessionTypeEnum sessionTypeEnum) {
        clearServerHistory(str, sessionTypeEnum, false, null);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void clearServerHistory(String str, SessionTypeEnum sessionTypeEnum, boolean z) {
        a(str, sessionTypeEnum, z, false, (String) null);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void clearServerHistory(String str, SessionTypeEnum sessionTypeEnum, boolean z, String str2) {
        a(str, sessionTypeEnum, true, z, str2);
    }

    private static void a(String str, SessionTypeEnum sessionTypeEnum, boolean z, boolean z2, String str2) {
        com.qiyukf.nimlib.session.b.c.a().b(str, sessionTypeEnum);
        MsgDBHelper.clearMessage(str, sessionTypeEnum, false);
        s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(str, sessionTypeEnum);
        if (sVarQueryRecentContact != null) {
            com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.session.k.a(str, sessionTypeEnum, sVarQueryRecentContact));
        }
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.d.i.c(str, sessionTypeEnum, z, z2, str2));
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void deleteRangeHistory(String str, SessionTypeEnum sessionTypeEnum, long j, long j2) {
        long j3;
        long j4;
        if (j == j2) {
            throw new IllegalArgumentException("time set error: startTime equals endTime");
        }
        if (j > j2) {
            j4 = j;
            j3 = j2;
        } else {
            j3 = j;
            j4 = j2;
        }
        MsgDBHelper.deleteRangeHistory(str, sessionTypeEnum, j3, j4);
        s sVarQueryRecentContact = MsgDBHelper.queryRecentContact(str, sessionTypeEnum);
        if (sVarQueryRecentContact != null) {
            com.qiyukf.nimlib.session.d dVarQueryLatestMessage = MsgDBHelper.queryLatestMessage(str, sessionTypeEnum.getValue());
            if (dVarQueryLatestMessage == null) {
                com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.session.k.a(str, sessionTypeEnum, sVarQueryRecentContact));
                return;
            }
            if (TextUtils.equals(sVarQueryRecentContact.getRecentMessageId(), dVarQueryLatestMessage.getUuid())) {
                return;
            }
            s sVarE = com.qiyukf.nimlib.session.k.e(dVarQueryLatestMessage);
            sVarE.a(sVarQueryRecentContact.getUnreadCount());
            sVarE.setTag(sVarQueryRecentContact.getTag());
            sVarE.f(sVarQueryRecentContact.c());
            MsgDBHelper.saveRecent(sVarE);
            com.qiyukf.nimlib.i.b.a(sVarE);
        }
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<RecentSessionList> queryMySessionList(long j, Long l, Integer num, Integer num2, Integer num3) {
        com.qiyukf.nimlib.biz.d.i.i iVar = new com.qiyukf.nimlib.biz.d.i.i(j, l, num, num2);
        iVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(iVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<RecentSessionList> queryMySessionList(QueryMySessionOption queryMySessionOption) {
        if (queryMySessionOption == null) {
            queryMySessionOption = new QueryMySessionOption();
        }
        com.qiyukf.nimlib.biz.d.i.i iVar = new com.qiyukf.nimlib.biz.d.i.i(queryMySessionOption.getMinTimestamp(), Long.valueOf(queryMySessionOption.getMaxTimestamp()), Integer.valueOf(queryMySessionOption.isNeedLastMsg() ? 1 : 0), Integer.valueOf(queryMySessionOption.getLimit()));
        iVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(iVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<RecentSession> queryMySession(@NonNull String str) {
        com.qiyukf.nimlib.biz.d.i.j jVar = new com.qiyukf.nimlib.biz.d.i.j(str);
        jVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(jVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> updateMySession(String str, String str2) {
        com.qiyukf.nimlib.biz.d.b bVar = new com.qiyukf.nimlib.biz.d.b(str, str2);
        bVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(bVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> deleteMySession(String[] strArr) {
        com.qiyukf.nimlib.biz.d.i.g gVar = new com.qiyukf.nimlib.biz.d.i.g(strArr);
        gVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(gVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void migrateMessages(String str, String str2, boolean z) {
        if (TextUtils.equals(str, str2)) {
            return;
        }
        MsgDBHelper.migrateMessages(com.qiyukf.nimlib.c.d(), str, str2, z);
    }

    private List<RecentContact> a(Set<MsgTypeEnum> set) {
        List<RecentContact> listQueryRecentContactsBlock = queryRecentContactsBlock();
        if (com.qiyukf.nimlib.n.e.b((Collection) listQueryRecentContactsBlock) || com.qiyukf.nimlib.n.e.b((Collection) set)) {
            return listQueryRecentContactsBlock;
        }
        ArrayList arrayList = new ArrayList(listQueryRecentContactsBlock.size());
        for (RecentContact recentContact : listQueryRecentContactsBlock) {
            if (recentContact instanceof s) {
                s sVarA = a((s) recentContact, set);
                if (sVarA != null) {
                    recentContact = sVarA;
                }
                arrayList.add(recentContact);
            }
        }
        return arrayList;
    }

    private static s a(s sVar, Set<MsgTypeEnum> set) {
        if (sVar == null || com.qiyukf.nimlib.n.e.b((Collection) set)) {
            return sVar;
        }
        String contactId = sVar.getContactId();
        SessionTypeEnum sessionType = sVar.getSessionType();
        MsgTypeEnum msgType = sVar.getMsgType();
        if (TextUtils.isEmpty(contactId) || sessionType == null) {
            return null;
        }
        if (msgType == null || !set.contains(msgType)) {
            return sVar;
        }
        IMMessage iMMessageQueryLatestMessageFilterMsgType = MsgDBHelper.queryLatestMessageFilterMsgType(contactId, sessionType.getValue(), com.qiyukf.nimlib.n.e.a((Collection) set, false, new e.a() { // from class: com.qiyukf.nimlib.biz.f.g$$ExternalSyntheticLambda2
            @Override // com.qiyukf.nimlib.n.e.a
            public final Object transform(Object obj) {
                return Integer.valueOf(((MsgTypeEnum) obj).getValue());
            }
        }));
        if (iMMessageQueryLatestMessageFilterMsgType == null) {
            sVar.b("");
            sVar.c("");
            sVar.b(MsgTypeEnum.text.getValue());
            sVar.setMsgStatus(MsgStatusEnum.success);
            sVar.d("");
            return sVar;
        }
        s sVarE = com.qiyukf.nimlib.session.k.e((com.qiyukf.nimlib.session.d) iMMessageQueryLatestMessageFilterMsgType);
        sVarE.a(sVar.getUnreadCount());
        sVarE.setTag(sVar.getTag());
        sVarE.f(sVar.c());
        return sVarE;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<ThreadTalkHistory> queryThreadTalkHistory(IMMessage iMMessage, long j, long j2, int i, QueryDirectionEnum queryDirectionEnum, boolean z) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(1000).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.j.j jVar = new com.qiyukf.nimlib.biz.d.j.j((com.qiyukf.nimlib.session.d) iMMessage, j, j2, i, queryDirectionEnum, z);
        jVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(jVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<ThreadTalkHistory> queryThreadTalkHistory(MessageKey messageKey, QueryThreadTalkHistoryOption queryThreadTalkHistoryOption) {
        if (messageKey == null || queryThreadTalkHistoryOption == null || !messageKey.isValid() || !queryThreadTalkHistoryOption.isValid()) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.j.j jVar = new com.qiyukf.nimlib.biz.d.j.j(messageKey, queryThreadTalkHistoryOption);
        jVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(jVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public int queryReplyCountInThreadTalkBlock(IMMessage iMMessage) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(1000).b();
            return 0;
        }
        return MsgDBHelper.queryReplyCount(iMMessage.isThread() ? iMMessage.getUuid() : iMMessage.getThreadOption().getThreadMsgIdClient(), iMMessage.getSessionId(), iMMessage.getSessionType());
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<IMMessage>> queryLocalThreadTalkHistory(IMMessage iMMessage) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        com.qiyukf.nimlib.i.i.b().b(MsgDBHelper.queryReplyMsgList(iMMessage.getUuid(), iMMessage.getSessionId(), iMMessage.getSessionType())).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> addQuickComment(IMMessage iMMessage, long j, String str) {
        a(iMMessage, j, str, false, false, "", "", null);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> addQuickComment(IMMessage iMMessage, long j, String str, boolean z, boolean z2, String str2, String str3, Map<String, Object> map) {
        a(iMMessage, j, str, z, z2, str2, str3, map);
        return null;
    }

    private static void a(IMMessage iMMessage, long j, String str, boolean z, boolean z2, String str2, String str3, Map<String, Object> map) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return;
        }
        com.qiyukf.nimlib.biz.d.j.c cVar = new com.qiyukf.nimlib.biz.d.j.c((com.qiyukf.nimlib.session.d) iMMessage, j, str, z, z2, str2, str3, map);
        cVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(cVar);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> removeQuickComment(IMMessage iMMessage, long j, String str) {
        b(iMMessage, j, str, false, false, "", "", null);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> removeQuickComment(IMMessage iMMessage, long j, String str, boolean z, boolean z2, String str2, String str3, Map<String, Object> map) {
        b(iMMessage, j, str, z, z2, str2, str3, map);
        return null;
    }

    private static void b(IMMessage iMMessage, long j, String str, boolean z, boolean z2, String str2, String str3, Map<String, Object> map) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return;
        }
        com.qiyukf.nimlib.biz.d.j.m mVar = new com.qiyukf.nimlib.biz.d.j.m((com.qiyukf.nimlib.session.d) iMMessage, j, str, z, z2, str2, str3, map);
        mVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(mVar);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<List<QuickCommentOptionWrapper>> queryQuickComment(List<IMMessage> list) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (list == null || list.isEmpty()) {
            jVarB.b(new ArrayList()).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.j.i iVar = new com.qiyukf.nimlib.biz.d.j.i(list);
        iVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(iVar) { // from class: com.qiyukf.nimlib.biz.f.g.4
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                super.a(aVar);
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<CollectInfo> addCollect(int i, String str, String str2, String str3) {
        com.qiyukf.nimlib.biz.d.j.a aVar = new com.qiyukf.nimlib.biz.d.j.a(i, str, str2, str3);
        aVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(aVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Integer> removeCollect(List<Pair<Long, Long>> list) {
        com.qiyukf.nimlib.biz.d.j.k kVar = new com.qiyukf.nimlib.biz.d.j.k(list);
        kVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(kVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<CollectInfo> updateCollect(CollectInfo collectInfo, String str) {
        if (!(collectInfo instanceof com.qiyukf.nimlib.session.a)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
        }
        return updateCollect(collectInfo.getId(), collectInfo.getCreateTime(), str);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<CollectInfo> updateCollect(long j, long j2, String str) {
        com.qiyukf.nimlib.biz.d.j.o oVar = new com.qiyukf.nimlib.biz.d.j.o(j, j2, str);
        oVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(oVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<CollectInfoPage> queryCollect(int i) {
        a((CollectInfo) null, 0L, i, QueryDirectionEnum.QUERY_OLD, (Integer) null, true);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<CollectInfoPage> queryCollect(CollectInfo collectInfo, long j, int i, QueryDirectionEnum queryDirectionEnum) {
        a(collectInfo, j, i, queryDirectionEnum, (Integer) null, true);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<CollectInfoPage> queryCollect(CollectInfo collectInfo, long j, int i, QueryDirectionEnum queryDirectionEnum, int i2, boolean z) {
        a(collectInfo, j, i, queryDirectionEnum, Integer.valueOf(i2), z);
        return null;
    }

    private static void a(CollectInfo collectInfo, long j, int i, QueryDirectionEnum queryDirectionEnum, Integer num, boolean z) {
        com.qiyukf.nimlib.biz.d.j.g gVar;
        boolean z2 = queryDirectionEnum != QueryDirectionEnum.QUERY_OLD;
        if (collectInfo == null) {
            gVar = new com.qiyukf.nimlib.biz.d.j.g(null, null, null, i, z2, num, z);
        } else {
            Long lValueOf = Long.valueOf(z2 ? collectInfo.getCreateTime() : j);
            if (!z2) {
                j = collectInfo.getCreateTime();
            }
            gVar = new com.qiyukf.nimlib.biz.d.j.g(lValueOf, Long.valueOf(j), Long.valueOf(collectInfo.getId()), i, z2, num, z);
        }
        gVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(gVar);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> addMsgPin(IMMessage iMMessage, String str) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        if (str == null) {
            str = "";
        }
        com.qiyukf.nimlib.biz.d.j.b bVar = new com.qiyukf.nimlib.biz.d.j.b(iMMessage.getSessionType(), iMMessage.getFromAccount(), com.qiyukf.nimlib.session.h.a((com.qiyukf.nimlib.session.d) iMMessage), iMMessage.getTime(), iMMessage.getServerId(), iMMessage.getUuid(), str);
        bVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(bVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> updateMsgPin(IMMessage iMMessage, String str) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        if (str == null) {
            str = "";
        }
        com.qiyukf.nimlib.biz.d.j.p pVar = new com.qiyukf.nimlib.biz.d.j.p(iMMessage.getSessionType(), iMMessage.getFromAccount(), com.qiyukf.nimlib.session.h.a((com.qiyukf.nimlib.session.d) iMMessage), iMMessage.getTime(), iMMessage.getServerId(), iMMessage.getUuid(), str);
        pVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(pVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Long> removeMsgPin(IMMessage iMMessage, String str) {
        if (!(iMMessage instanceof com.qiyukf.nimlib.session.d)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        if (str == null) {
            str = "";
        }
        com.qiyukf.nimlib.biz.d.j.l lVar = new com.qiyukf.nimlib.biz.d.j.l(iMMessage.getSessionType(), iMMessage.getFromAccount(), com.qiyukf.nimlib.session.h.a((com.qiyukf.nimlib.session.d) iMMessage), iMMessage.getTime(), iMMessage.getServerId(), iMMessage.getUuid(), str);
        lVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(lVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<MsgPinSyncResponseOptionWrapper> syncMsgPin(SessionTypeEnum sessionTypeEnum, String str, long j) {
        if (sessionTypeEnum == null || sessionTypeEnum == SessionTypeEnum.None || TextUtils.isEmpty(str)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.j.f fVar = new com.qiyukf.nimlib.biz.d.j.f(str, sessionTypeEnum, j);
        fVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(fVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<MsgPinDbOption> queryMsgPinBlock(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelper.queryMsgPin(str);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<StickTopSessionInfo> addStickTopSession(String str, SessionTypeEnum sessionTypeEnum, String str2) {
        if (sessionTypeEnum == null || sessionTypeEnum == SessionTypeEnum.None || TextUtils.isEmpty(str)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.j.d dVar = new com.qiyukf.nimlib.biz.d.j.d(str, sessionTypeEnum, str2);
        dVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(dVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<Void> removeStickTopSession(String str, SessionTypeEnum sessionTypeEnum, String str2) {
        if (sessionTypeEnum == null || sessionTypeEnum == SessionTypeEnum.None || TextUtils.isEmpty(str)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.j.n nVar = new com.qiyukf.nimlib.biz.d.j.n(str, sessionTypeEnum, str2);
        nVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(nVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public InvocationFuture<StickTopSessionInfo> updateStickTopSession(String str, SessionTypeEnum sessionTypeEnum, String str2) {
        if (sessionTypeEnum == null || sessionTypeEnum == SessionTypeEnum.None || TextUtils.isEmpty(str)) {
            com.qiyukf.nimlib.i.i.b().a(414).b();
            return null;
        }
        com.qiyukf.nimlib.biz.d.j.q qVar = new com.qiyukf.nimlib.biz.d.j.q(str, sessionTypeEnum, str2);
        qVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(qVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public List<StickTopSessionInfo> queryStickTopSessionBlock() {
        return new ArrayList(MsgDBHelper.queryStickTopSession());
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public boolean isStickTopSession(String str, SessionTypeEnum sessionTypeEnum) {
        if (TextUtils.isEmpty(str) || sessionTypeEnum == null) {
            return false;
        }
        return MsgDBHelper.isStickTopSession(str, sessionTypeEnum);
    }

    @Override // com.qiyukf.nimlib.sdk.msg.MsgService
    public void registerCustomAttachmentParser(MsgAttachmentParser msgAttachmentParser) {
        com.qiyukf.nimlib.session.e.a().c().a(MsgTypeEnum.custom.getValue(), msgAttachmentParser);
    }
}

package com.qiyukf.nimlib.session;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatDelegate;
import com.qiyukf.nimlib.net.a.b.a;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.StatusCode;
import com.qiyukf.nimlib.sdk.ai.params.NIMAIModelCallContent;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.attachment.AudioAttachment;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.attachment.ImageAttachment;
import com.qiyukf.nimlib.sdk.msg.attachment.MsgAttachment;
import com.qiyukf.nimlib.sdk.msg.attachment.VideoAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.MessageRobotInfo;
import com.qiyukf.nimlib.sdk.msg.model.MsgThreadOption;
import com.qiyukf.nimlib.sdk.msg.model.NIMAntiSpamOption;
import com.qiyukf.nimlib.sdk.msg.model.NIMMessageAIConfigParams;
import com.qiyukf.nimlib.sdk.util.UriUtils;
import com.qiyukf.nimlib.session.v;
import java.io.File;
import java.util.List;

/* JADX INFO: compiled from: MessageSender.java */
/* JADX INFO: loaded from: classes6.dex */
public final class i {
    public static void a(d dVar, boolean z, com.qiyukf.nimlib.i.j jVar, com.qiyukf.nimlib.biz.d.i.s sVar) {
        a(dVar, z, jVar, sVar, SystemClock.elapsedRealtime());
    }

    public static void a(d dVar, boolean z, com.qiyukf.nimlib.i.j jVar, long j) {
        a(dVar, z, jVar, (com.qiyukf.nimlib.biz.d.i.s) null, j);
    }

    private static void a(d dVar, boolean z, com.qiyukf.nimlib.i.j jVar, com.qiyukf.nimlib.biz.d.i.s sVar, long j) {
        boolean z2;
        if (TextUtils.isEmpty(dVar.getSessionId())) {
            com.qiyukf.nimlib.log.c.b.a.f("core", "no message receiver");
            throw new IllegalArgumentException("Receiver cannot be null");
        }
        com.qiyukf.nimlib.report.g.a().a(dVar, j);
        if (z) {
            long jQueryMessageIdByUuid = MsgDBHelper.queryMessageIdByUuid(dVar.getUuid());
            dVar.a(jQueryMessageIdByUuid);
            z2 = jQueryMessageIdByUuid > 0;
        } else {
            z2 = z;
        }
        dVar.b(System.currentTimeMillis());
        long jB = com.qiyukf.nimlib.c.A() ? v.a.a.b() : -1L;
        if (z2) {
            MsgDBHelper.updateMessage(dVar, MsgStatusEnum.fail);
        } else {
            MsgDBHelper.saveMessage(dVar, MsgStatusEnum.fail);
        }
        com.qiyukf.nimlib.log.c.b.a.d("MessageSender", "before send msg, uuid=" + dVar.getUuid());
        s sVarB = k.b(dVar);
        if (com.qiyukf.nimlib.h.e() != StatusCode.LOGINED && !com.qiyukf.nimlib.n.p.b(com.qiyukf.nimlib.c.d())) {
            MsgStatusEnum msgStatusEnum = MsgStatusEnum.fail;
            sVarB.setMsgStatus(msgStatusEnum);
            com.qiyukf.nimlib.i.b.a(sVarB);
            dVar.setStatus(msgStatusEnum);
            dVar.setAttachStatus(AttachStatusEnum.fail);
            com.qiyukf.nimlib.i.b.a(dVar);
            com.qiyukf.nimlib.report.g.a().a((IMMessage) dVar, 1);
            jVar.a(1).b();
            return;
        }
        e.a().a(dVar.getUuid());
        sVarB.setMsgStatus(MsgStatusEnum.sending);
        com.qiyukf.nimlib.i.b.a(sVarB);
        if (a(dVar, z, jB, jVar, sVar)) {
            return;
        }
        b(dVar, jB, z, jVar, sVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(d dVar, long j, boolean z, com.qiyukf.nimlib.i.j jVar, com.qiyukf.nimlib.biz.d.i.s sVar) {
        com.qiyukf.nimlib.push.packet.b.c cVarA = a(dVar, j, z);
        if (sVar == null) {
            if (dVar.getSessionType() == SessionTypeEnum.P2P) {
                sVar = new com.qiyukf.nimlib.biz.d.i.s();
            } else if (dVar.getSessionType() == SessionTypeEnum.Team) {
                sVar = new com.qiyukf.nimlib.biz.d.i.t();
            }
        }
        if (sVar != null) {
            sVar.a(jVar);
            sVar.a(cVarA);
            a(sVar);
        }
    }

    private static com.qiyukf.nimlib.push.packet.b.c a(d dVar, long j, boolean z) {
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(0, dVar.getSessionType().getValue());
        cVar.a(1, dVar.getSessionId());
        cVar.a(9, dVar.getContent());
        cVar.a(8, dVar.getMsgType().getValue());
        cVar.a(11, dVar.getUuid());
        if (dVar.needMsgAck()) {
            cVar.a(26, 1);
        }
        String strI = dVar.i();
        if (!TextUtils.isEmpty(strI)) {
            cVar.a(15, strI);
        }
        if (!TextUtils.isEmpty(dVar.getPushContent())) {
            cVar.a(17, dVar.getPushContent());
        }
        if (!TextUtils.isEmpty(dVar.k())) {
            cVar.a(16, dVar.k());
        }
        if (j >= 0) {
            cVar.a(14, j);
        }
        String strA = dVar.a(true);
        if (!TextUtils.isEmpty(strA)) {
            cVar.a(10, strA);
        }
        if (z) {
            cVar.a(13, 1);
        }
        if (dVar.getSessionId().equals(com.qiyukf.nimlib.c.q())) {
            cVar.a(5, com.qiyukf.nimlib.push.b.c());
        }
        if (dVar.getMemberPushOption() != null) {
            cVar.a(20, dVar.getMemberPushOption().isForcePush() ? 1 : 0);
            cVar.a(19, dVar.getMemberPushOption().getForcePushContent());
            List<String> forcePushList = dVar.getMemberPushOption().getForcePushList();
            cVar.a(18, forcePushList == null ? "#%@all@%#" : k.d(forcePushList));
        }
        cVar.a(28, dVar.isSessionUpdate() ? 1 : 0);
        if (dVar.getConfig() != null) {
            if (!dVar.getConfig().enableHistory) {
                cVar.a(100, 0);
            }
            if (!dVar.getConfig().enableRoaming) {
                cVar.a(101, 0);
            }
            if (!dVar.getConfig().enableSelfSync) {
                cVar.a(102, 0);
            }
            if (!dVar.getConfig().enablePush) {
                cVar.a(107, 0);
            }
            if (!dVar.getConfig().enablePersist) {
                cVar.a(AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR, 0);
            }
            if (!dVar.getConfig().enableUnreadCount) {
                cVar.a(109, 0);
            }
            if (!dVar.getConfig().enablePushNick) {
                cVar.a(110, 0);
            }
            if (!dVar.getConfig().enableRoute) {
                cVar.a(105, 0);
            }
        }
        if (dVar.getNIMAntiSpamOption() != null) {
            NIMAntiSpamOption nIMAntiSpamOption = dVar.getNIMAntiSpamOption();
            if (!nIMAntiSpamOption.enable) {
                cVar.a(25, 0);
            }
            if (!TextUtils.isEmpty(nIMAntiSpamOption.content)) {
                cVar.a(21, 1);
                cVar.a(22, nIMAntiSpamOption.content);
            }
            if (!TextUtils.isEmpty(nIMAntiSpamOption.antiSpamConfigId)) {
                cVar.a(23, nIMAntiSpamOption.antiSpamConfigId);
            }
        }
        if (dVar.n()) {
            cVar.a(24, 1);
        }
        MsgThreadOption threadOption = dVar.getThreadOption();
        if (!dVar.isThread()) {
            cVar.a(29, threadOption.getReplyMsgFromAccount());
            cVar.a(30, threadOption.getReplyMsgToAccount());
            cVar.a(31, threadOption.getReplyMsgTime());
            cVar.a(32, threadOption.getReplyMsgIdServer());
            cVar.a(33, threadOption.getReplyMsgIdClient());
            cVar.a(34, threadOption.getThreadMsgFromAccount());
            cVar.a(35, threadOption.getThreadMsgToAccount());
            cVar.a(36, threadOption.getThreadMsgTime());
            cVar.a(37, threadOption.getThreadMsgIdServer());
            cVar.a(38, threadOption.getThreadMsgIdClient());
        }
        cVar.a(39, dVar.isDeleted() ? 1 : 0);
        cVar.a(40, dVar.getCallbackExtension());
        int subtype = dVar.getSubtype();
        if (subtype > 0) {
            cVar.a(41, subtype);
        }
        cVar.a(42, dVar.getYidunAntiCheating());
        cVar.a(43, dVar.getEnv());
        cVar.a(44, dVar.getYidunAntiSpamExt());
        ae aeVarP = dVar.p();
        if (aeVarP != null && aeVarP.a() > 0) {
            cVar.a(46, aeVarP.d().toString());
        }
        MessageRobotInfo robotInfo = dVar.getRobotInfo();
        if (robotInfo != null) {
            cVar.a(47, robotInfo.getFunction());
            cVar.a(48, robotInfo.getTopic());
            cVar.a(49, robotInfo.getCustomContent());
            cVar.a(50, robotInfo.getAccount());
        }
        NIMMessageAIConfigParams nIMMessageAIConfigParamsR = dVar.r();
        if (nIMMessageAIConfigParamsR != null) {
            NIMAIModelCallContent content = nIMMessageAIConfigParamsR.getContent();
            if (content == null && dVar.getMsgType() == MsgTypeEnum.text) {
                content = new NIMAIModelCallContent(dVar.getContent(), 0);
            }
            cVar.a(56, nIMMessageAIConfigParamsR.getAccountId());
            cVar.a(57, com.qiyukf.nimlib.a.b.a(content));
            cVar.a(58, com.qiyukf.nimlib.a.b.a(nIMMessageAIConfigParamsR.getMessages()));
            cVar.a(59, nIMMessageAIConfigParamsR.getPromptVariables());
            cVar.a(60, com.qiyukf.nimlib.a.b.a(nIMMessageAIConfigParamsR.getModelConfigParams()));
            cVar.a(65, nIMMessageAIConfigParamsR.isAIStream() ? 1 : 0);
        }
        return cVar;
    }

    public static a.c a(final FileAttachment fileAttachment, final com.qiyukf.nimlib.i.j jVar) throws Exception {
        a(fileAttachment, 1);
        return com.qiyukf.nimlib.net.a.b.a.a().a(fileAttachment, jVar, new com.qiyukf.nimlib.net.a.b.c<com.qiyukf.nimlib.i.j>() { // from class: com.qiyukf.nimlib.session.i.1
            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* bridge */ /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2, int i, String str) {
                a(i);
            }

            private void a(int i) {
                jVar.a(i).b();
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* bridge */ /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2) {
                a(400);
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2, String str) {
                fileAttachment.setUrl(str);
                jVar.b(fileAttachment).b();
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2, long j, long j2) {
                com.qiyukf.nimlib.i.b.a(fileAttachment.getPath(), j, j2);
            }
        });
    }

    private static void a(FileAttachment fileAttachment, int i) throws Exception {
        if (fileAttachment == null) {
            com.qiyukf.nimlib.log.c.b.a.d("MessageSender", "calculateMd5 FileAttachment == null");
            return;
        }
        boolean z = fileAttachment instanceof VideoAttachment;
        String strB = null;
        String thumbPath = z ? fileAttachment.getThumbPath() : null;
        String extension = fileAttachment.getExtension();
        if (fileAttachment.getUri() != null) {
            strB = com.qiyukf.nimlib.n.m.a(com.qiyukf.nimlib.c.d(), fileAttachment.getUri());
            if (TextUtils.isEmpty(extension)) {
                extension = UriUtils.getFileExtensionFromUri(com.qiyukf.nimlib.c.d(), fileAttachment.getUri());
            }
        } else if (!TextUtils.isEmpty(fileAttachment.getPath())) {
            strB = com.qiyukf.nimlib.n.m.b(fileAttachment.getPath());
            if (TextUtils.isEmpty(extension)) {
                extension = com.qiyukf.nimlib.n.w.b(fileAttachment.getPath());
            }
        }
        fileAttachment.setMd5(strB);
        if (i == 0) {
            return;
        }
        com.qiyukf.nimlib.n.b.b bVar = com.qiyukf.nimlib.n.b.b.TYPE_FILE;
        if (fileAttachment instanceof AudioAttachment) {
            bVar = com.qiyukf.nimlib.n.b.b.TYPE_AUDIO;
        } else if (fileAttachment instanceof ImageAttachment) {
            bVar = com.qiyukf.nimlib.n.b.b.TYPE_IMAGE;
        } else if (z) {
            bVar = com.qiyukf.nimlib.n.b.b.TYPE_VIDEO;
        }
        String str = com.qiyukf.nimlib.n.b.c.a(strB, bVar) + "." + extension;
        if (!com.qiyukf.nimlib.net.a.c.a.d(str)) {
            if (fileAttachment.getUri() != null) {
                com.qiyukf.nimlib.net.a.c.a.a(com.qiyukf.nimlib.c.d(), fileAttachment.getUri(), str);
                fileAttachment.setPath(str);
                fileAttachment.setSize(new File(str).length());
            } else if (!TextUtils.isEmpty(fileAttachment.getPath()) && i == 2) {
                com.qiyukf.nimlib.net.a.c.a.a(fileAttachment.getPath(), str);
                fileAttachment.setPath(str);
                fileAttachment.setSize(new File(str).length());
            }
        } else {
            fileAttachment.setPath(str);
            fileAttachment.setSize(new File(str).length());
        }
        if (z) {
            String thumbPathForSave = ((VideoAttachment) fileAttachment).getThumbPathForSave();
            if (TextUtils.isEmpty(thumbPath) || com.qiyukf.nimlib.net.a.c.a.d(thumbPathForSave)) {
                return;
            }
            com.qiyukf.nimlib.log.c.b.a.d("MessageSender", "move thumb " + thumbPath + " to " + thumbPathForSave + " result = " + com.qiyukf.nimlib.net.a.c.a.b(thumbPath, thumbPathForSave));
        }
    }

    private static boolean a(d dVar, boolean z, long j, com.qiyukf.nimlib.i.j jVar, com.qiyukf.nimlib.biz.d.i.s sVar) throws Exception {
        MsgAttachment attachment = dVar.getAttachment();
        if (attachment == null || !(attachment instanceof FileAttachment)) {
            return false;
        }
        FileAttachment fileAttachment = (FileAttachment) attachment;
        if (!TextUtils.isEmpty(fileAttachment.getUrl())) {
            if (fileAttachment instanceof AudioAttachment) {
                AudioAttachment audioAttachment = (AudioAttachment) fileAttachment;
                if (audioAttachment.getAutoTransform()) {
                    dVar.setAttachStatus(AttachStatusEnum.transferring);
                    if (TextUtils.isEmpty(fileAttachment.getMd5())) {
                        a(fileAttachment, 1);
                    }
                    b(audioAttachment, dVar, z, jVar, sVar, j);
                    return true;
                }
            }
            return false;
        }
        MsgTypeEnum msgType = dVar.getMsgType();
        if ((msgType == MsgTypeEnum.audio || msgType == MsgTypeEnum.image || msgType == MsgTypeEnum.video) && fileAttachment.getSize() == 0) {
            com.qiyukf.nimlib.log.c.b.a.f("core", "the size of file attachment is 0");
            throw new IllegalArgumentException("the size of file attachment is 0");
        }
        dVar.setAttachStatus(AttachStatusEnum.transferring);
        if (TextUtils.isEmpty(fileAttachment.getExtension())) {
            fileAttachment.setExtension(a(fileAttachment));
        }
        if (!z || TextUtils.isEmpty(fileAttachment.getMd5())) {
            a(dVar);
        }
        b(dVar, z, j, jVar, sVar);
        return true;
    }

    private static void b(final d dVar, final boolean z, final long j, final com.qiyukf.nimlib.i.j jVar, final com.qiyukf.nimlib.biz.d.i.s sVar) {
        final FileAttachment fileAttachment = (FileAttachment) dVar.getAttachment();
        final e eVarA = e.a();
        final String uuid = dVar.getUuid();
        com.qiyukf.nimlib.report.g.a().a(dVar);
        eVarA.a(dVar.getUuid(), com.qiyukf.nimlib.net.a.b.a.a().a(fileAttachment, jVar, new com.qiyukf.nimlib.net.a.b.c<com.qiyukf.nimlib.i.j>() { // from class: com.qiyukf.nimlib.session.i.2
            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2) {
                eVarA.g(uuid);
                com.qiyukf.nimlib.report.g.a().b(dVar);
                eVarA.b(uuid);
                d dVar2 = dVar;
                MsgStatusEnum msgStatusEnum = MsgStatusEnum.fail;
                dVar2.setStatus(msgStatusEnum);
                dVar.setAttachStatus(AttachStatusEnum.cancel);
                try {
                    MsgDBHelper.updateMessage(dVar, msgStatusEnum);
                    MsgDBHelper.setMessageStatusCode(dVar.getUuid(), 400);
                    dVar.e(400);
                    com.qiyukf.nimlib.i.b.a(dVar);
                    com.qiyukf.nimlib.i.j jVar3 = jVar;
                    if (jVar3 != null) {
                        jVar3.a(400).b();
                    }
                } catch (Exception unused) {
                    com.qiyukf.nimlib.log.c.b.a.f("ui", "db already close");
                }
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2, int i, String str) {
                eVarA.g(uuid);
                com.qiyukf.nimlib.report.g.a().b(dVar);
                eVarA.b(uuid);
                d dVar2 = dVar;
                MsgStatusEnum msgStatusEnum = MsgStatusEnum.fail;
                dVar2.setStatus(msgStatusEnum);
                dVar.setAttachStatus(AttachStatusEnum.fail);
                try {
                    MsgDBHelper.updateMessage(dVar, msgStatusEnum);
                    MsgDBHelper.setMessageStatusCode(dVar.getUuid(), i);
                    dVar.e(i);
                    com.qiyukf.nimlib.i.b.a(dVar);
                    com.qiyukf.nimlib.i.j jVar3 = jVar;
                    if (jVar3 != null) {
                        jVar3.a(i).b();
                    }
                } catch (Exception unused) {
                    com.qiyukf.nimlib.log.c.b.a.f("ui", "db already close");
                }
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2, String str) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(str.contains("?") ? "&" : "?");
                sb.append("createTime=");
                sb.append(System.currentTimeMillis());
                String string = sb.toString();
                eVarA.g(uuid);
                com.qiyukf.nimlib.report.g.a().b(dVar);
                fileAttachment.setUrl(string);
                dVar.setAttachment(fileAttachment);
                dVar.setAttachStatus(AttachStatusEnum.transferred);
                try {
                    MsgDBHelper.updateMessage(dVar, MsgStatusEnum.fail);
                    FileAttachment fileAttachment2 = fileAttachment;
                    if (!(fileAttachment2 instanceof AudioAttachment) || !((AudioAttachment) fileAttachment2).getAutoTransform()) {
                        i.b(dVar, j, z, jVar, sVar);
                    } else {
                        i.b((AudioAttachment) fileAttachment, dVar, z, jVar, sVar, j);
                    }
                } catch (Exception unused) {
                    com.qiyukf.nimlib.log.c.b.a.f("ui", "db already close");
                }
            }

            @Override // com.qiyukf.nimlib.net.a.b.c
            public final /* bridge */ /* synthetic */ void a(com.qiyukf.nimlib.i.j jVar2, long j2, long j3) {
                com.qiyukf.nimlib.i.b.a(uuid, j2, j3);
            }
        }));
    }

    private static void a(d dVar) throws Exception {
        FileAttachment fileAttachment = (FileAttachment) dVar.getAttachment();
        if (fileAttachment == null) {
            com.qiyukf.nimlib.log.c.b.a.d("MessageSender", "calculateMd5 FileAttachment == null,uuid = " + dVar.getUuid());
            return;
        }
        a(fileAttachment, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final AudioAttachment audioAttachment, final d dVar, final boolean z, final com.qiyukf.nimlib.i.j jVar, final com.qiyukf.nimlib.biz.d.i.s sVar, final long j) {
        ((MsgService) NIMClient.getService(MsgService.class)).transVoiceToText(audioAttachment.getUrl(), audioAttachment.getPath(), audioAttachment.getDuration()).setCallback(new RequestCallback() { // from class: com.qiyukf.nimlib.session.i.3
            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onSuccess(Object obj) {
                audioAttachment.setText(obj.toString());
                dVar.setAttachment(audioAttachment);
                ((MsgService) NIMClient.getService(MsgService.class)).updateIMMessage(dVar);
                d dVar2 = new d();
                dVar2.a(dVar.getUuid());
                dVar2.b(dVar.getSessionId());
                dVar2.setFromAccount(com.qiyukf.nimlib.c.q());
                dVar2.setDirect(MsgDirectionEnum.Out);
                dVar2.setStatus(MsgStatusEnum.sending);
                dVar2.a(dVar.getSessionType());
                dVar2.b(dVar.getTime());
                dVar2.a(dVar.a());
                dVar2.a(MsgTypeEnum.text.getValue());
                dVar2.setContent(obj.toString());
                i.b(dVar2, j, z, jVar, sVar);
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i) {
                dVar.setStatus(MsgStatusEnum.fail);
                dVar.setAttachStatus(AttachStatusEnum.fail);
                com.qiyukf.nimlib.i.b.a(dVar);
                e.a().b(dVar.getUuid());
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onException(Throwable th) {
                dVar.setStatus(MsgStatusEnum.fail);
                dVar.setAttachStatus(AttachStatusEnum.fail);
                com.qiyukf.nimlib.i.b.a(dVar);
                e.a().b(dVar.getUuid());
            }
        });
    }

    private static String a(FileAttachment fileAttachment) {
        if (fileAttachment instanceof ImageAttachment) {
            return "jpg";
        }
        if (fileAttachment instanceof VideoAttachment) {
            return "mp4";
        }
        return "";
    }

    private static void a(com.qiyukf.nimlib.biz.d.i.s sVar) {
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.d.i.r(sVar, com.qiyukf.nimlib.biz.g.a.b));
    }
}

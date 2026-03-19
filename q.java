package com.qiyukf.nimlib.biz.c.j;

import android.text.TextUtils;
import com.qiyukf.nimlib.biz.e.k.ac;
import com.qiyukf.nimlib.sdk.friend.model.AddFriendNotify;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SystemMessageStatus;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotificationConfig;
import com.qiyukf.nimlib.sdk.msg.model.NIMAntiSpamOption;
import com.qiyukf.nimlib.sdk.msg.model.SystemMessage;
import com.qiyukf.nimlib.session.MsgDBHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: compiled from: SystemMsgNotifyHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public class q extends com.qiyukf.nimlib.biz.c.i {
    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        int iD;
        if (aVar.e()) {
            if (aVar instanceof ac) {
                a(((ac) aVar).j(), false);
            } else if (aVar instanceof com.qiyukf.nimlib.biz.e.g.j) {
                List<com.qiyukf.nimlib.push.packet.b.c> listJ = ((com.qiyukf.nimlib.biz.e.g.j) aVar).j();
                Collections.sort(listJ, new Comparator<com.qiyukf.nimlib.push.packet.b.c>() { // from class: com.qiyukf.nimlib.biz.c.j.q.1
                    @Override // java.util.Comparator
                    public final /* synthetic */ int compare(com.qiyukf.nimlib.push.packet.b.c cVar, com.qiyukf.nimlib.push.packet.b.c cVar2) {
                        return Long.compare(cVar.e(0), cVar2.e(0));
                    }
                });
                ArrayList arrayList = new ArrayList();
                ArrayList<Long> arrayListG = com.qiyukf.nimlib.biz.n.G();
                if (com.qiyukf.nimlib.n.e.b((Collection) arrayListG)) {
                    arrayListG = new ArrayList<>();
                }
                for (com.qiyukf.nimlib.push.packet.b.c cVar : listJ) {
                    long jE = cVar.e(6);
                    if (!arrayListG.contains(Long.valueOf(jE))) {
                        a(cVar, true);
                        if (jE > 0 && ((iD = cVar.d(1)) == 100 || iD == 101 || iD == 103 || iD == 102)) {
                            arrayListG.add(Long.valueOf(jE));
                        }
                    }
                    if (jE > 0) {
                        arrayList.add(Long.valueOf(jE));
                    }
                }
                a(arrayList);
                arrayListG.retainAll(arrayList);
                com.qiyukf.nimlib.biz.n.a(arrayListG);
            }
            com.qiyukf.nimlib.i.b.a(MsgDBHelper.querySystemMessageUnreadNum());
        }
    }

    private static void a(com.qiyukf.nimlib.push.packet.b.c cVar, boolean z) {
        int iD = cVar.d(1);
        if (iD == 100 || iD == 101 || iD == 103 || iD == 102) {
            a(cVar);
        } else {
            a(cVar, iD != 6, z);
        }
    }

    private static void a(com.qiyukf.nimlib.push.packet.b.c cVar) {
        long jE = cVar.e(6);
        if (jE > 0 && !com.qiyukf.nimlib.session.ac.a().a(Long.valueOf(jE))) {
            com.qiyukf.nimlib.log.c.b.a.H("receive repeated custom notification，msgId = ".concat(String.valueOf(jE)));
            return;
        }
        CustomNotification customNotification = new CustomNotification();
        customNotification.setTime(cVar.e(0));
        customNotification.setContent(cVar.c(5));
        customNotification.setFromAccount(cVar.c(3));
        com.qiyukf.nimlib.log.c.b.a.J("receive custom notification: sessionId: " + cVar.c(3) + ", content: " + cVar.c(5));
        customNotification.setApnsText(cVar.c(8));
        String strC = cVar.c(9);
        if (!TextUtils.isEmpty(strC)) {
            customNotification.setPushPayload(com.qiyukf.nimlib.session.k.b(strC));
        }
        if (jE > 0) {
            customNotification.setSendToOnlineUserOnly(false);
        }
        int iD = cVar.d(1);
        if (iD == 100) {
            customNotification.setSessionType(SessionTypeEnum.P2P);
            customNotification.setSessionId(cVar.c(3));
        } else if (iD == 101) {
            customNotification.setSessionType(SessionTypeEnum.Team);
            customNotification.setSessionId(cVar.c(2));
        } else if (iD == 103) {
            customNotification.setSessionType(SessionTypeEnum.SUPER_TEAM);
            customNotification.setSessionId(cVar.c(2));
        } else if (iD == 102) {
            customNotification.setSessionType(SessionTypeEnum.Ysf);
            customNotification.setSessionId(cVar.c(3));
        }
        CustomNotificationConfig customNotificationConfig = new CustomNotificationConfig();
        customNotificationConfig.enablePush = cVar.d(107) == 1;
        customNotificationConfig.enablePushNick = cVar.d(110) == 1;
        customNotificationConfig.enableUnreadCount = cVar.d(109) == 1;
        customNotification.setConfig(customNotificationConfig);
        NIMAntiSpamOption nIMAntiSpamOption = new NIMAntiSpamOption();
        if (cVar.f(12)) {
            nIMAntiSpamOption.enable = cVar.d(12) == 1;
            customNotification.setNIMAntiSpamOption(nIMAntiSpamOption);
        }
        if (cVar.f(13)) {
            nIMAntiSpamOption.content = cVar.c(13);
            customNotification.setNIMAntiSpamOption(nIMAntiSpamOption);
        }
        com.qiyukf.nimlib.i.b.a(customNotification);
    }

    private static void a(com.qiyukf.nimlib.push.packet.b.c cVar, boolean z, boolean z2) {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setFromAccount(cVar.c(3));
        systemMessage.setTargetId(cVar.c(2));
        systemMessage.setTime(cVar.e(0));
        systemMessage.setContent(cVar.c(4));
        systemMessage.setAttach(cVar.c(5));
        systemMessage.setStatus(SystemMessageStatus.init);
        systemMessage.setUnread(z);
        int iD = cVar.d(1);
        systemMessage.setType(iD);
        a(systemMessage);
        a(systemMessage, iD, z2);
        if (iD == 2) {
            com.qiyukf.nimlib.session.k.b(systemMessage);
        }
        if (iD != 6) {
            MsgDBHelper.saveSystemMessage(systemMessage, iD);
            com.qiyukf.nimlib.i.b.a(systemMessage);
        }
    }

    private static void a(SystemMessage systemMessage) {
        if (TextUtils.isEmpty(systemMessage.getAttach())) {
            return;
        }
        systemMessage.setAttachObject(com.qiyukf.nimlib.team.c.a(systemMessage.getAttach()));
    }

    private static void a(SystemMessage systemMessage, int i, boolean z) {
        if (i != 5) {
            if (z || i != 6) {
                return;
            }
            com.qiyukf.nimlib.friend.a.a(systemMessage.getFromAccount(), false);
            return;
        }
        com.qiyukf.nimlib.session.k.a(systemMessage);
        if (z || systemMessage.getAttachObject() == null) {
            return;
        }
        AddFriendNotify addFriendNotify = (AddFriendNotify) systemMessage.getAttachObject();
        if (addFriendNotify.getEvent() == AddFriendNotify.Event.RECV_ADD_FRIEND_DIRECT || addFriendNotify.getEvent() == AddFriendNotify.Event.RECV_AGREE_ADD_FRIEND) {
            com.qiyukf.nimlib.friend.a.a(systemMessage.getFromAccount(), addFriendNotify.getServerExt());
        }
    }

    public void a(List<Long> list) {
        com.qiyukf.nimlib.biz.d.f.a aVar = new com.qiyukf.nimlib.biz.d.f.a();
        aVar.a((byte) 7);
        aVar.b((byte) 3);
        aVar.a(list);
        com.qiyukf.nimlib.biz.k.a().a(aVar, com.qiyukf.nimlib.biz.g.a.d);
    }
}

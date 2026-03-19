package com.qiyukf.uikit.session.module.input;

import android.os.Handler;
import android.os.Looper;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.session.module.a.a;
import com.qiyukf.unicorn.api.msg.MessageService;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.z;
import com.qiyukf.unicorn.h.a.d.ah;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes6.dex */
public class RobotStreamHelper {
    private static final String TAG = "RobotStreamHelper";
    private static final RobotStreamHelper instance = new RobotStreamHelper();
    private final Map<Long, List<AnswerFragment>> robotStreamAnswerMap = new HashMap();
    private final Map<Long, ah> robotStreamAnswerAttachmentMap = new HashMap();
    private final Map<String, Handler> messageUpdateHandlers = new HashMap();

    private RobotStreamHelper() {
    }

    public static RobotStreamHelper getInstance() {
        return instance;
    }

    private void outputOneByOne(a aVar, String str, IMMessage iMMessage, ah ahVar) {
        ah ahVar2 = (ah) iMMessage.getAttachment();
        String str2 = ahVar2.d().c().get(0).c;
        if (str.equals(str2) || str.isEmpty()) {
            return;
        }
        if (str2 == null) {
            str2 = "";
        }
        int iCalculateLeadingTagsLength = calculateLeadingTagsLength(str) + str2.length();
        if (iCalculateLeadingTagsLength >= str.length()) {
            ahVar2.d().c().get(0).c = str;
            if (ahVar != null) {
                iMMessage.setAttachment(ahVar);
            }
            scrollToBottom(aVar);
            return;
        }
        String uuid = iMMessage.getUuid();
        removeExistingHandler(uuid);
        Handler handler = new Handler(Looper.getMainLooper());
        this.messageUpdateHandlers.put(uuid, handler);
        handler.postDelayed(new Runnable(iCalculateLeadingTagsLength, str, ahVar, iMMessage, aVar, uuid, ahVar2, handler) { // from class: com.qiyukf.uikit.session.module.input.RobotStreamHelper.1
            private int currentPosition;
            final /* synthetic */ int val$adjustedCurrentLength;
            final /* synthetic */ ah val$attachment;
            final /* synthetic */ Handler val$handler;
            final /* synthetic */ IMMessage val$imMessage;
            final /* synthetic */ ah val$imMessageAttachment;
            final /* synthetic */ a val$messageListPanel;
            final /* synthetic */ String val$targetAnswer;
            final /* synthetic */ String val$uuid;

            {
                this.val$adjustedCurrentLength = iCalculateLeadingTagsLength;
                this.val$targetAnswer = str;
                this.val$attachment = ahVar;
                this.val$imMessage = iMMessage;
                this.val$messageListPanel = aVar;
                this.val$uuid = uuid;
                this.val$imMessageAttachment = ahVar2;
                this.val$handler = handler;
                this.currentPosition = iCalculateLeadingTagsLength;
            }

            @Override // java.lang.Runnable
            public void run() {
                int iIndexOf;
                if (this.currentPosition >= this.val$targetAnswer.length()) {
                    ah ahVar3 = this.val$attachment;
                    if (ahVar3 != null) {
                        this.val$imMessage.setAttachment(ahVar3);
                        RobotStreamHelper.this.scrollToBottom(this.val$messageListPanel);
                    }
                    RobotStreamHelper.this.removeExistingHandler(this.val$uuid);
                    return;
                }
                int i = this.currentPosition;
                int i2 = (this.val$targetAnswer.charAt(i) != '<' || (iIndexOf = this.val$targetAnswer.indexOf(62, i)) == -1) ? i + 1 : iIndexOf + 1;
                this.currentPosition = i2;
                this.val$imMessageAttachment.d().c().get(0).c = this.val$targetAnswer.substring(0, i2);
                RobotStreamHelper.this.scrollToBottom(this.val$messageListPanel);
                Handler handler2 = (Handler) RobotStreamHelper.this.messageUpdateHandlers.get(this.val$uuid);
                if (handler2 == null || handler2 != this.val$handler) {
                    return;
                }
                handler2.postDelayed(this, 100L);
            }
        }, 100L);
    }

    private int calculateLeadingTagsLength(String str) {
        int iIndexOf;
        int i = 0;
        int i2 = 0;
        while (i < str.length() && str.charAt(i) == '<' && (iIndexOf = str.indexOf(62, i)) != -1) {
            i2 += (iIndexOf - i) + 1;
            i = iIndexOf + 1;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToBottom(a aVar) {
        aVar.j().notifyDataSetChanged();
        if (aVar.g()) {
            aVar.i();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeExistingHandler(String str) {
        Handler handlerRemove = this.messageUpdateHandlers.remove(str);
        if (handlerRemove != null) {
            handlerRemove.removeCallbacksAndMessages(null);
        }
    }

    public void removeByQuestionMsgId(long j) {
        removeExistingHandler(String.valueOf(j));
    }

    private String getSendAnswer(long j, String str, int i) {
        List<AnswerFragment> arrayList = this.robotStreamAnswerMap.get(Long.valueOf(j));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(new AnswerFragment(i, str));
        Collections.sort(arrayList, new Comparator<AnswerFragment>() { // from class: com.qiyukf.uikit.session.module.input.RobotStreamHelper.2
            @Override // java.util.Comparator
            public int compare(AnswerFragment answerFragment, AnswerFragment answerFragment2) {
                return Integer.compare(answerFragment.sequence, answerFragment2.sequence);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            AnswerFragment answerFragment = arrayList.get(i2);
            if (!(i2 == 0 && answerFragment.sequence == 1) && (i2 <= 0 || answerFragment.sequence - arrayList.get(i2 - 1).sequence != 1)) {
                break;
            }
            sb.append(answerFragment.answer);
        }
        this.robotStreamAnswerMap.put(Long.valueOf(j), arrayList);
        return sb.toString();
    }

    public static class AnswerFragment {
        public String answer;
        public int sequence;

        public AnswerFragment(int i, String str) {
            this.sequence = i;
            this.answer = str;
        }
    }

    public void onRobotStreamAnswer(a aVar, String str, ah ahVar, long j) {
        if (ahVar == null) {
            return;
        }
        z zVar = c.h().f().get(str);
        String str2 = zVar == null ? str : zVar.d;
        long jB = ahVar.b();
        int iG = ahVar.g();
        String str3 = ahVar.d().c().get(0).c;
        if (ahVar.c() == 0) {
            if (this.robotStreamAnswerMap.containsKey(Long.valueOf(jB))) {
                return;
            }
            this.robotStreamAnswerMap.put(Long.valueOf(jB), new ArrayList());
            MessageService.saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str2, str, SessionTypeEnum.Ysf, String.valueOf(ahVar.b()), ahVar, j), true, false);
            return;
        }
        if (ahVar.c() == 1) {
            if (this.robotStreamAnswerMap.containsKey(Long.valueOf(jB))) {
                String sendAnswer = getSendAnswer(jB, str3, iG);
                IMMessage iMMessageA = aVar.a(String.valueOf(ahVar.b()));
                if (iMMessageA != null && (iMMessageA.getAttachment() instanceof ah)) {
                    outputOneByOne(aVar, sendAnswer, iMMessageA, null);
                    return;
                } else {
                    MessageService.saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str2, str, SessionTypeEnum.Ysf, String.valueOf(ahVar.b()), ahVar, j), true, false);
                    return;
                }
            }
            return;
        }
        if (ahVar.c() == 2) {
            this.robotStreamAnswerMap.remove(Long.valueOf(jB));
            IMMessage iMMessageA2 = aVar.a(String.valueOf(ahVar.b()));
            if (iMMessageA2 == null || !(iMMessageA2.getAttachment() instanceof ah)) {
                return;
            }
            outputOneByOne(aVar, str3, iMMessageA2, ahVar);
            return;
        }
        if (ahVar.c() == 3) {
            AbsUnicornLog.i(TAG, "onRobotStreamAnswer: fail");
            removeByQuestionMsgId(ahVar.b());
            IMMessage iMMessageA3 = aVar.a(String.valueOf(ahVar.b()));
            if (iMMessageA3 != null) {
                aVar.b(iMMessageA3);
            }
        }
    }
}

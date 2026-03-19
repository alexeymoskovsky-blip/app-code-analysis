package com.qiyukf.unicorn.k;

import android.content.Context;
import android.text.TextUtils;
import androidx.collection.LongSparseArray;
import androidx.fragment.app.Fragment;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.database.f;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.evaluation.EvaluationApi;
import com.qiyukf.unicorn.api.evaluation.entry.EvaluationOpenEntry;
import com.qiyukf.unicorn.f.h;
import com.qiyukf.unicorn.f.p;
import com.qiyukf.unicorn.h.a.d.g;
import com.qiyukf.unicorn.h.a.d.i;
import com.qiyukf.unicorn.h.a.f.x;
import com.qiyukf.unicorn.k.d;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.ui.evaluate.c;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: EvaluationManager.java */
/* JADX INFO: loaded from: classes6.dex */
public final class a {
    private Fragment a;
    private String b;
    private Map<String, IMMessage> c = new HashMap();
    private Map<String, Long> d = new HashMap();
    private Map<String, Boolean> e = new HashMap();
    private LongSparseArray<RequestCallbackWrapper<String>> f = new LongSparseArray<>();
    private LongSparseArray<RequestCallbackWrapper<String>> g = new LongSparseArray<>();
    private com.qiyukf.unicorn.ui.evaluate.c h;
    private boolean i;

    public static void a(IMMessage iMMessage) {
        String sessionId = iMMessage.getSessionId();
        int iL = com.qiyukf.unicorn.d.c.l(sessionId);
        if (iL == -1 || iL == 4) {
            return;
        }
        MsgDirectionEnum direct = iMMessage.getDirect();
        if ((direct == MsgDirectionEnum.Out && iL % 2 == 0) || (direct == MsgDirectionEnum.In && iL % 2 == 1)) {
            com.qiyukf.unicorn.d.c.a(sessionId, iL + 1);
        }
    }

    public final void a(String str, String str2, i iVar) {
        if (a(str2) == null) {
            return;
        }
        if (com.qiyukf.unicorn.d.c.m(str2) == 1 || a(str2).g()) {
            a(str, System.currentTimeMillis(), str2, iVar.b(), iVar.c(), iVar.a(), iVar.d(), iVar.e());
        }
    }

    public final void a(Fragment fragment, String str) {
        this.a = fragment;
        this.b = str;
        if (this.c.containsKey(str)) {
            IMMessage iMMessageRemove = this.c.remove(str);
            if (this.h == null && iMMessageRemove != null && (iMMessageRemove.getAttachment() instanceof com.qiyukf.unicorn.h.a.f.c)) {
                a(fragment.getContext(), iMMessageRemove);
            }
        }
    }

    public final void a() {
        this.a = null;
        this.b = null;
    }

    public final void a(com.qiyukf.unicorn.ui.evaluate.c cVar) {
        this.h = cVar;
    }

    public final void b() {
        com.qiyukf.unicorn.ui.evaluate.c cVar = this.h;
        if (cVar == null || !cVar.isShowing() || this.a == null) {
            return;
        }
        this.h.cancel();
    }

    public final void a(String str, long j, String str2, long j2, boolean z, int i, String str3, long j3) {
        if (f.a().b()) {
            com.qiyukf.unicorn.h.a.c.c cVarA = a(str2);
            if (cVarA.m() == 1) {
                g gVar = new g();
                gVar.a(-1);
                gVar.a(a(str2));
                if (gVar.e().b() != null) {
                    j.a(gVar.e().b(), "richTextInvite", str3);
                }
                gVar.e().b(str3);
                gVar.a("android");
                gVar.a(j2);
                gVar.b(i);
                gVar.b(j3);
                com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(str, str2, SessionTypeEnum.Ysf, (String) null, gVar, j);
                dVarA.setStatus(MsgStatusEnum.success);
                com.qiyukf.nimlib.ysf.b.a(dVarA);
                return;
            }
            com.qiyukf.unicorn.h.a.f.c cVar = new com.qiyukf.unicorn.h.a.f.c();
            cVar.b(-1);
            cVar.a(a(str2));
            if (cVar.i().b() != null) {
                j.a(cVar.i().b(), "richTextInvite", str3);
            }
            cVar.i().b(str3);
            cVar.a("android");
            cVar.a(j2);
            cVar.c(i);
            if (j3 != 0) {
                cVar.a(Long.valueOf(j3));
            }
            com.qiyukf.nimlib.session.d dVarA2 = com.qiyukf.nimlib.ysf.a.a(str, str2, SessionTypeEnum.Ysf, (String) null, cVar, j);
            com.qiyukf.nimlib.ysf.b.a(dVarA2);
            if (z) {
                if (cVarA.m() == 2) {
                    if (EvaluationApi.getInstance().getOnEvaluationEventListener() == null) {
                        return;
                    }
                    EvaluationApi.getInstance().getOnEvaluationEventListener().onEvaluationMessageClick(a(cVar, dVarA2.getSessionId()), this.a.getContext());
                } else if (cVarA.m() == 0) {
                    b(dVarA2);
                }
            }
        }
    }

    private static EvaluationOpenEntry a(com.qiyukf.unicorn.h.a.f.c cVar, String str) {
        EvaluationOpenEntry evaluationOpenEntry = new EvaluationOpenEntry();
        evaluationOpenEntry.setEvaluationEntryList(cVar.i().e());
        evaluationOpenEntry.setExchange(str);
        evaluationOpenEntry.setLastRemark(cVar.g());
        evaluationOpenEntry.setLastSource(cVar.d());
        evaluationOpenEntry.setSessionId(cVar.f());
        evaluationOpenEntry.setTitle(cVar.i().c());
        evaluationOpenEntry.setType(cVar.i().d());
        evaluationOpenEntry.setResolvedEnabled(cVar.i().k());
        evaluationOpenEntry.setResolvedRequired(cVar.i().l());
        return evaluationOpenEntry;
    }

    private void b(IMMessage iMMessage) {
        if (iMMessage.getSessionId().equals(this.b)) {
            if (this.h == null) {
                a(this.a.getContext(), iMMessage);
            }
        } else {
            if (this.c.containsKey(iMMessage.getSessionId())) {
                return;
            }
            this.c.put(iMMessage.getSessionId(), iMMessage);
        }
    }

    public final Map<String, IMMessage> c() {
        return this.c;
    }

    public final Map<String, Long> d() {
        return this.d;
    }

    public final Map<String, Boolean> e() {
        return this.e;
    }

    public final boolean c(String str) {
        return a(str, true);
    }

    public final boolean a(String str, boolean z) {
        boolean z2 = true;
        if (this.e.get(str) != null && ((this.e.get(str) == null || !this.e.get(str).booleanValue()) && com.qiyukf.unicorn.c.h().g(str) != 1)) {
            z2 = false;
        }
        if (!z2) {
            AbsUnicornLog.i("EvaluationManager", "evaluation is not enable");
            if (z) {
                t.a(R.string.ysf_evaluation_limit);
            }
        }
        return z2;
    }

    public final void a(Context context, IMMessage iMMessage) {
        com.qiyukf.unicorn.ui.evaluate.c cVar = new com.qiyukf.unicorn.ui.evaluate.c(context, (com.qiyukf.unicorn.h.a.f.c) iMMessage.getAttachment());
        cVar.setCanceledOnTouchOutside(false);
        cVar.a(new c.b() { // from class: com.qiyukf.unicorn.k.a.1
            final /* synthetic */ com.qiyukf.unicorn.ui.evaluate.c a;
            final /* synthetic */ IMMessage b;
            final /* synthetic */ Context c;

            public AnonymousClass1(com.qiyukf.unicorn.ui.evaluate.c cVar2, IMMessage iMMessage2, Context context2) {
                cVar = cVar2;
                iMMessage = iMMessage2;
                context = context2;
            }

            @Override // com.qiyukf.unicorn.ui.evaluate.c.b
            public final void onSubmit(int i, List<String> list, String str, String str2, int i2, long j) {
                cVar.a(false);
                cVar.b(true);
                a.this.a(iMMessage, i, str, list, str2, i2, new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.1.1
                    public C01821() {
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                        if (i3 != 200 && i3 != 415) {
                            if (cVar.isShowing()) {
                                cVar.a(true);
                                cVar.b(false);
                                t.a(context.getString(R.string.ysf_network_error));
                                return;
                            }
                            return;
                        }
                        if (i3 == 200) {
                            cVar.cancel();
                        } else if (cVar.isShowing()) {
                            cVar.a(true);
                            cVar.b(false);
                        }
                    }
                });
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$1$1 */
            /* JADX INFO: compiled from: EvaluationManager.java */
            public class C01821 extends RequestCallbackWrapper<String> {
                public C01821() {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                    if (i3 != 200 && i3 != 415) {
                        if (cVar.isShowing()) {
                            cVar.a(true);
                            cVar.b(false);
                            t.a(context.getString(R.string.ysf_network_error));
                            return;
                        }
                        return;
                    }
                    if (i3 == 200) {
                        cVar.cancel();
                    } else if (cVar.isShowing()) {
                        cVar.a(true);
                        cVar.b(false);
                    }
                }
            }
        });
        cVar2.show();
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$1 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass1 implements c.b {
        final /* synthetic */ com.qiyukf.unicorn.ui.evaluate.c a;
        final /* synthetic */ IMMessage b;
        final /* synthetic */ Context c;

        public AnonymousClass1(com.qiyukf.unicorn.ui.evaluate.c cVar2, IMMessage iMMessage2, Context context2) {
            cVar = cVar2;
            iMMessage = iMMessage2;
            context = context2;
        }

        @Override // com.qiyukf.unicorn.ui.evaluate.c.b
        public final void onSubmit(int i, List<String> list, String str, String str2, int i2, long j) {
            cVar.a(false);
            cVar.b(true);
            a.this.a(iMMessage, i, str, list, str2, i2, new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.1.1
                public C01821() {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                    if (i3 != 200 && i3 != 415) {
                        if (cVar.isShowing()) {
                            cVar.a(true);
                            cVar.b(false);
                            t.a(context.getString(R.string.ysf_network_error));
                            return;
                        }
                        return;
                    }
                    if (i3 == 200) {
                        cVar.cancel();
                    } else if (cVar.isShowing()) {
                        cVar.a(true);
                        cVar.b(false);
                    }
                }
            });
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$1$1 */
        /* JADX INFO: compiled from: EvaluationManager.java */
        public class C01821 extends RequestCallbackWrapper<String> {
            public C01821() {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                if (i3 != 200 && i3 != 415) {
                    if (cVar.isShowing()) {
                        cVar.a(true);
                        cVar.b(false);
                        t.a(context.getString(R.string.ysf_network_error));
                        return;
                    }
                    return;
                }
                if (i3 == 200) {
                    cVar.cancel();
                } else if (cVar.isShowing()) {
                    cVar.a(true);
                    cVar.b(false);
                }
            }
        }
    }

    public final void b(Context context, IMMessage iMMessage) {
        a(context, iMMessage, (RequestCallbackWrapper) null);
    }

    public final void a(Context context, IMMessage iMMessage, RequestCallbackWrapper requestCallbackWrapper) {
        com.qiyukf.unicorn.ui.evaluate.c cVar = new com.qiyukf.unicorn.ui.evaluate.c(context, (x) iMMessage.getAttachment());
        cVar.setCanceledOnTouchOutside(false);
        cVar.a(new c.a() { // from class: com.qiyukf.unicorn.k.a.4
            final /* synthetic */ RequestCallbackWrapper a;

            public AnonymousClass4(RequestCallbackWrapper requestCallbackWrapper2) {
                requestCallbackWrapper = requestCallbackWrapper2;
            }

            @Override // com.qiyukf.unicorn.ui.evaluate.c.a
            public final void a() {
                RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                if (requestCallbackWrapper2 != null) {
                    requestCallbackWrapper2.onResult(400, "", null);
                }
            }
        });
        cVar.a(new c.b() { // from class: com.qiyukf.unicorn.k.a.5
            final /* synthetic */ com.qiyukf.unicorn.ui.evaluate.c a;
            final /* synthetic */ IMMessage b;
            final /* synthetic */ Context c;
            final /* synthetic */ RequestCallbackWrapper d;

            public AnonymousClass5(com.qiyukf.unicorn.ui.evaluate.c cVar2, IMMessage iMMessage2, Context context2, RequestCallbackWrapper requestCallbackWrapper2) {
                cVar = cVar2;
                iMMessage = iMMessage2;
                context = context2;
                requestCallbackWrapper = requestCallbackWrapper2;
            }

            @Override // com.qiyukf.unicorn.ui.evaluate.c.b
            public final void onSubmit(int i, List<String> list, String str, String str2, int i2, long j) {
                cVar.a(false);
                cVar.b(true);
                a.this.b(iMMessage, i, str, list, str2, i2, new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.5.1
                    public AnonymousClass1() {
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                        String str4 = str3;
                        if (i3 == 200 || i3 == 415) {
                            cVar.cancel();
                        } else if (cVar.isShowing()) {
                            cVar.a(true);
                            cVar.b(false);
                            t.a(context.getString(R.string.ysf_network_error));
                        }
                        RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                        if (requestCallbackWrapper2 != null) {
                            requestCallbackWrapper2.onResult(i3, str4, th);
                        }
                    }
                });
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$5$1 */
            /* JADX INFO: compiled from: EvaluationManager.java */
            public class AnonymousClass1 extends RequestCallbackWrapper<String> {
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                    String str4 = str3;
                    if (i3 == 200 || i3 == 415) {
                        cVar.cancel();
                    } else if (cVar.isShowing()) {
                        cVar.a(true);
                        cVar.b(false);
                        t.a(context.getString(R.string.ysf_network_error));
                    }
                    RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                    if (requestCallbackWrapper2 != null) {
                        requestCallbackWrapper2.onResult(i3, str4, th);
                    }
                }
            }
        });
        cVar2.show();
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$4 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass4 implements c.a {
        final /* synthetic */ RequestCallbackWrapper a;

        public AnonymousClass4(RequestCallbackWrapper requestCallbackWrapper2) {
            requestCallbackWrapper = requestCallbackWrapper2;
        }

        @Override // com.qiyukf.unicorn.ui.evaluate.c.a
        public final void a() {
            RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
            if (requestCallbackWrapper2 != null) {
                requestCallbackWrapper2.onResult(400, "", null);
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$5 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass5 implements c.b {
        final /* synthetic */ com.qiyukf.unicorn.ui.evaluate.c a;
        final /* synthetic */ IMMessage b;
        final /* synthetic */ Context c;
        final /* synthetic */ RequestCallbackWrapper d;

        public AnonymousClass5(com.qiyukf.unicorn.ui.evaluate.c cVar2, IMMessage iMMessage2, Context context2, RequestCallbackWrapper requestCallbackWrapper2) {
            cVar = cVar2;
            iMMessage = iMMessage2;
            context = context2;
            requestCallbackWrapper = requestCallbackWrapper2;
        }

        @Override // com.qiyukf.unicorn.ui.evaluate.c.b
        public final void onSubmit(int i, List<String> list, String str, String str2, int i2, long j) {
            cVar.a(false);
            cVar.b(true);
            a.this.b(iMMessage, i, str, list, str2, i2, new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.5.1
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                    String str4 = str3;
                    if (i3 == 200 || i3 == 415) {
                        cVar.cancel();
                    } else if (cVar.isShowing()) {
                        cVar.a(true);
                        cVar.b(false);
                        t.a(context.getString(R.string.ysf_network_error));
                    }
                    RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                    if (requestCallbackWrapper2 != null) {
                        requestCallbackWrapper2.onResult(i3, str4, th);
                    }
                }
            });
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$5$1 */
        /* JADX INFO: compiled from: EvaluationManager.java */
        public class AnonymousClass1 extends RequestCallbackWrapper<String> {
            public AnonymousClass1() {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                String str4 = str3;
                if (i3 == 200 || i3 == 415) {
                    cVar.cancel();
                } else if (cVar.isShowing()) {
                    cVar.a(true);
                    cVar.b(false);
                    t.a(context.getString(R.string.ysf_network_error));
                }
                RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                if (requestCallbackWrapper2 != null) {
                    requestCallbackWrapper2.onResult(i3, str4, th);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$6 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass6 extends RequestCallbackWrapper<String> {
        final /* synthetic */ com.qiyukf.unicorn.h.a.f.c a;
        final /* synthetic */ RequestCallbackWrapper b;

        public AnonymousClass6(com.qiyukf.unicorn.h.a.f.c cVar, RequestCallbackWrapper requestCallbackWrapper) {
            cVar = cVar;
            requestCallbackWrapper = requestCallbackWrapper;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i, String str, Throwable th) {
            String str2 = str;
            if (i == 200 || i == 201) {
                if (cVar.i().b() != null) {
                    j.a(cVar.i().b(), "richTextThanks", str2);
                }
                cVar.i().a(str2);
                if (i == 201) {
                    cVar.a(0);
                } else {
                    cVar.a(1);
                }
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(a.this.b, SessionTypeEnum.Ysf, cVar), true);
                if (cVar.f() == com.qiyukf.unicorn.d.c.j(a.this.b)) {
                    com.qiyukf.unicorn.d.c.b(a.this.b, 2);
                    com.qiyukf.unicorn.d.c.a(a.this.b, -1);
                }
                i = 200;
            }
            d.a aVarK = com.qiyukf.unicorn.c.h().k();
            if (aVarK != null) {
                aVarK.onEvaluationEvent(a.this.b);
            }
            RequestCallbackWrapper requestCallbackWrapper = requestCallbackWrapper;
            if (requestCallbackWrapper != null) {
                requestCallbackWrapper.onResult(i, str2, th);
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
            super.onFailed(i);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$7 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass7 implements RequestCallback<Void> {
        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
        }

        public AnonymousClass7() {
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$8 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass8 extends RequestCallbackWrapper<String> {
        final /* synthetic */ IMMessage a;
        final /* synthetic */ String b;
        final /* synthetic */ com.qiyukf.unicorn.h.a.f.c c;
        final /* synthetic */ com.qiyukf.unicorn.h.a.f.c d;
        final /* synthetic */ String e;
        final /* synthetic */ IMMessage f;
        final /* synthetic */ RequestCallbackWrapper g;

        public AnonymousClass8(IMMessage iMMessage, String str, com.qiyukf.unicorn.h.a.f.c cVar, com.qiyukf.unicorn.h.a.f.c cVar2, String str2, IMMessage iMMessage2, RequestCallbackWrapper requestCallbackWrapper) {
            iMMessage = iMMessage;
            str = str;
            cVar = cVar;
            cVar = cVar2;
            str = str2;
            iMMessage = iMMessage2;
            requestCallbackWrapper = requestCallbackWrapper;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i, String str, Throwable th) {
            d.a aVarK;
            String str2 = str;
            if (i == 200 || i == 201) {
                String sessionId = iMMessage.getSessionId();
                SessionTypeEnum sessionTypeEnum = SessionTypeEnum.Ysf;
                IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(sessionId, sessionTypeEnum, str);
                iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageCreateTextMessage, true);
                cVar.l();
                ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
                if (cVar.i().b() != null) {
                    j.a(cVar.i().b(), "richTextThanks", str2);
                }
                cVar.i().a(str2);
                if (i == 201) {
                    cVar.a(0);
                } else {
                    cVar.a(1);
                }
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str, sessionTypeEnum, cVar), true);
                if (cVar.f() == com.qiyukf.unicorn.d.c.j(str)) {
                    com.qiyukf.unicorn.d.c.b(str, 2);
                    com.qiyukf.unicorn.d.c.a(str, -1);
                }
                i = 200;
            }
            if ((com.qiyukf.unicorn.d.c.j(str) == ((com.qiyukf.unicorn.h.a.f.c) iMMessage.getAttachment()).f() || iMMessage.isTheSame(iMMessage)) && (aVarK = com.qiyukf.unicorn.c.h().k()) != null) {
                aVarK.onEvaluationEvent(str);
            }
            RequestCallbackWrapper requestCallbackWrapper = requestCallbackWrapper;
            if (requestCallbackWrapper != null) {
                requestCallbackWrapper.onResult(i, str2, th);
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
            super.onFailed(i);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$9 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass9 implements RequestCallback<Void> {
        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
        }

        public AnonymousClass9() {
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$10 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass10 extends RequestCallbackWrapper<String> {
        final /* synthetic */ g a;
        final /* synthetic */ IMMessage b;
        final /* synthetic */ com.qiyukf.unicorn.h.a.f.c c;
        final /* synthetic */ String d;
        final /* synthetic */ IMMessage e;
        final /* synthetic */ RequestCallbackWrapper f;

        public AnonymousClass10(g gVar, IMMessage iMMessage, com.qiyukf.unicorn.h.a.f.c cVar, String str, IMMessage iMMessage2, RequestCallbackWrapper requestCallbackWrapper) {
            gVar = gVar;
            iMMessage = iMMessage;
            cVar = cVar;
            str = str;
            iMMessage = iMMessage2;
            requestCallbackWrapper = requestCallbackWrapper;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i, String str, Throwable th) {
            d.a aVarK;
            String str2 = str;
            if (i == 200 || i == 201) {
                gVar.g();
                ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
                if (cVar.i().b() != null) {
                    j.a(cVar.i().b(), "richTextThanks", str2);
                }
                cVar.i().a(str2);
                if (i == 201) {
                    cVar.a(0);
                } else {
                    cVar.a(1);
                }
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, cVar), true);
                if (cVar.f() == com.qiyukf.unicorn.d.c.j(str)) {
                    com.qiyukf.unicorn.d.c.b(str, 2);
                    com.qiyukf.unicorn.d.c.a(str, -1);
                }
                i = 200;
            }
            if ((com.qiyukf.unicorn.d.c.j(str) == ((g) iMMessage.getAttachment()).b() || iMMessage.isTheSame(iMMessage)) && (aVarK = com.qiyukf.unicorn.c.h().k()) != null) {
                aVarK.onEvaluationEvent(str);
            }
            RequestCallbackWrapper requestCallbackWrapper = requestCallbackWrapper;
            if (requestCallbackWrapper != null) {
                requestCallbackWrapper.onResult(i, str2, th);
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
            super.onFailed(i);
            requestCallbackWrapper.onFailed(i);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$11 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass11 extends RequestCallbackWrapper<String> {
        final /* synthetic */ x a;
        final /* synthetic */ String b;
        final /* synthetic */ RequestCallbackWrapper c;

        public AnonymousClass11(x xVar, String str, RequestCallbackWrapper requestCallbackWrapper) {
            xVar = xVar;
            str = str;
            requestCallbackWrapper = requestCallbackWrapper;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i, String str, Throwable th) {
            String str2 = str;
            if (i == 200) {
                if (xVar.k().b() != null) {
                    j.a(xVar.k().b(), "messageThanks", str2);
                }
                xVar.k().a(str2);
                com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, xVar);
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA, true);
                com.qiyukf.unicorn.d.c.c(str, dVarA.getUuid());
                if (xVar.d() == com.qiyukf.unicorn.d.c.j(str)) {
                    com.qiyukf.unicorn.d.c.c(str, 2);
                }
            }
            d.a aVarK = com.qiyukf.unicorn.c.h().k();
            if (aVarK != null) {
                aVarK.onRobotEvaluationEvent(str);
            }
            RequestCallbackWrapper requestCallbackWrapper = requestCallbackWrapper;
            if (requestCallbackWrapper != null) {
                requestCallbackWrapper.onResult(i, str2, th);
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
            super.onFailed(i);
        }
    }

    public final void b(IMMessage iMMessage, int i, String str, List<String> list, String str2, int i2, RequestCallbackWrapper<String> requestCallbackWrapper) {
        x xVar = (x) iMMessage.getAttachment();
        x xVar2 = new x();
        xVar2.a(i);
        xVar2.a(xVar.k());
        xVar2.b(str);
        xVar2.a("android");
        p pVarC = com.qiyukf.unicorn.c.h().c(this.b);
        if (pVarC != null && pVarC.f) {
            xVar2.a(pVarC.g);
        } else {
            xVar2.a(xVar.d());
        }
        xVar2.a(list);
        xVar2.b(i2);
        String sessionId = iMMessage.getSessionId();
        IMMessage iMMessageQueryLastMessage = ((MsgService) NIMClient.getService(MsgService.class)).queryLastMessage(sessionId, SessionTypeEnum.Ysf);
        c.a(xVar2, sessionId).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.k.a.2
            final /* synthetic */ IMMessage a;
            final /* synthetic */ String b;

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onException(Throwable th) {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i3) {
            }

            public AnonymousClass2(IMMessage iMMessage2, String str22) {
                iMMessage = iMMessage2;
                str = str22;
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final /* synthetic */ void onSuccess(Void r3) {
                IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(iMMessage.getSessionId(), SessionTypeEnum.Ysf, str);
                iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageCreateTextMessage, true);
            }
        });
        b(xVar2.d(), new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.3
            final /* synthetic */ x a;
            final /* synthetic */ x b;
            final /* synthetic */ IMMessage c;
            final /* synthetic */ String d;
            final /* synthetic */ IMMessage e;
            final /* synthetic */ RequestCallbackWrapper f;

            public AnonymousClass3(x xVar3, x xVar22, IMMessage iMMessage2, String sessionId2, IMMessage iMMessageQueryLastMessage2, RequestCallbackWrapper requestCallbackWrapper2) {
                xVar = xVar3;
                xVar = xVar22;
                iMMessage = iMMessage2;
                str = sessionId2;
                iMMessage = iMMessageQueryLastMessage2;
                requestCallbackWrapper = requestCallbackWrapper2;
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                d.a aVarK;
                String str4 = str3;
                if (i3 == 200) {
                    if (xVar.k().b() != null) {
                        j.a(xVar.k().b(), "messageThanks", str4);
                    }
                    xVar.i();
                    xVar.k().a(str4);
                    xVar.k().a(str4);
                    ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
                    com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, xVar);
                    ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA, true);
                    com.qiyukf.unicorn.d.c.c(str, dVarA.getUuid());
                    if (xVar.d() == com.qiyukf.unicorn.d.c.j(str)) {
                        com.qiyukf.unicorn.d.c.c(str, 2);
                    }
                }
                if ((com.qiyukf.unicorn.d.c.k(str) == ((x) iMMessage.getAttachment()).d() || iMMessage.isTheSame(iMMessage)) && (aVarK = com.qiyukf.unicorn.c.h().k()) != null) {
                    aVarK.onRobotEvaluationEvent(str);
                }
                RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                if (requestCallbackWrapper2 != null) {
                    requestCallbackWrapper2.onResult(i3, str4, th);
                }
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i3) {
                super.onFailed(i3);
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$2 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass2 implements RequestCallback<Void> {
        final /* synthetic */ IMMessage a;
        final /* synthetic */ String b;

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i3) {
        }

        public AnonymousClass2(IMMessage iMMessage2, String str22) {
            iMMessage = iMMessage2;
            str = str22;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* synthetic */ void onSuccess(Void r3) {
            IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(iMMessage.getSessionId(), SessionTypeEnum.Ysf, str);
            iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
            ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageCreateTextMessage, true);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.a$3 */
    /* JADX INFO: compiled from: EvaluationManager.java */
    public class AnonymousClass3 extends RequestCallbackWrapper<String> {
        final /* synthetic */ x a;
        final /* synthetic */ x b;
        final /* synthetic */ IMMessage c;
        final /* synthetic */ String d;
        final /* synthetic */ IMMessage e;
        final /* synthetic */ RequestCallbackWrapper f;

        public AnonymousClass3(x xVar3, x xVar22, IMMessage iMMessage2, String sessionId2, IMMessage iMMessageQueryLastMessage2, RequestCallbackWrapper requestCallbackWrapper2) {
            xVar = xVar3;
            xVar = xVar22;
            iMMessage = iMMessage2;
            str = sessionId2;
            iMMessage = iMMessageQueryLastMessage2;
            requestCallbackWrapper = requestCallbackWrapper2;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
            d.a aVarK;
            String str4 = str3;
            if (i3 == 200) {
                if (xVar.k().b() != null) {
                    j.a(xVar.k().b(), "messageThanks", str4);
                }
                xVar.i();
                xVar.k().a(str4);
                xVar.k().a(str4);
                ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
                com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, xVar);
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA, true);
                com.qiyukf.unicorn.d.c.c(str, dVarA.getUuid());
                if (xVar.d() == com.qiyukf.unicorn.d.c.j(str)) {
                    com.qiyukf.unicorn.d.c.c(str, 2);
                }
            }
            if ((com.qiyukf.unicorn.d.c.k(str) == ((x) iMMessage.getAttachment()).d() || iMMessage.isTheSame(iMMessage)) && (aVarK = com.qiyukf.unicorn.c.h().k()) != null) {
                aVarK.onRobotEvaluationEvent(str);
            }
            RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
            if (requestCallbackWrapper2 != null) {
                requestCallbackWrapper2.onResult(i3, str4, th);
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i3) {
            super.onFailed(i3);
        }
    }

    private void a(long j, RequestCallbackWrapper<String> requestCallbackWrapper) {
        this.f.put(j, requestCallbackWrapper);
    }

    public final RequestCallbackWrapper<String> a(long j) {
        RequestCallbackWrapper<String> requestCallbackWrapper = this.f.get(j);
        this.f.remove(j);
        return requestCallbackWrapper;
    }

    private void b(long j, RequestCallbackWrapper<String> requestCallbackWrapper) {
        this.g.put(j, requestCallbackWrapper);
    }

    public final RequestCallbackWrapper<String> b(long j) {
        RequestCallbackWrapper<String> requestCallbackWrapper = this.g.get(j);
        this.g.remove(j);
        return requestCallbackWrapper;
    }

    public final boolean f() {
        return this.i;
    }

    public final void a(boolean z) {
        this.i = z;
    }

    public static com.qiyukf.unicorn.h.a.c.c a(String str) {
        com.qiyukf.unicorn.h.a.c.c cVarQ = com.qiyukf.unicorn.d.c.q(str);
        return cVarQ == null ? com.qiyukf.unicorn.h.a.c.c.a() : cVarQ;
    }

    public static com.qiyukf.unicorn.h.a.c.e b(String str) {
        com.qiyukf.unicorn.h.a.c.e eVarR = com.qiyukf.unicorn.d.c.r(str);
        return eVarR == null ? com.qiyukf.unicorn.h.a.c.e.a() : eVarR;
    }

    public final void a(h hVar, RequestCallbackWrapper<String> requestCallbackWrapper) {
        if (com.qiyukf.unicorn.c.h().d().a(this.b, true)) {
            if (TextUtils.isEmpty(this.b)) {
                this.b = hVar.a;
            }
            com.qiyukf.unicorn.h.a.f.c cVar = new com.qiyukf.unicorn.h.a.f.c();
            cVar.b(hVar.b);
            cVar.a(a(this.b));
            cVar.b(hVar.c);
            cVar.a("android");
            if (hVar.g == 0) {
                hVar.g = com.qiyukf.unicorn.d.c.j(hVar.a);
            }
            cVar.a(hVar.g);
            cVar.a(hVar.d);
            cVar.d(hVar.f);
            cVar.e(hVar.h);
            c.a(cVar, this.b);
            a(cVar.f(), new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.6
                final /* synthetic */ com.qiyukf.unicorn.h.a.f.c a;
                final /* synthetic */ RequestCallbackWrapper b;

                public AnonymousClass6(com.qiyukf.unicorn.h.a.f.c cVar2, RequestCallbackWrapper requestCallbackWrapper2) {
                    cVar = cVar2;
                    requestCallbackWrapper = requestCallbackWrapper2;
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i, String str, Throwable th) {
                    String str2 = str;
                    if (i == 200 || i == 201) {
                        if (cVar.i().b() != null) {
                            j.a(cVar.i().b(), "richTextThanks", str2);
                        }
                        cVar.i().a(str2);
                        if (i == 201) {
                            cVar.a(0);
                        } else {
                            cVar.a(1);
                        }
                        ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(a.this.b, SessionTypeEnum.Ysf, cVar), true);
                        if (cVar.f() == com.qiyukf.unicorn.d.c.j(a.this.b)) {
                            com.qiyukf.unicorn.d.c.b(a.this.b, 2);
                            com.qiyukf.unicorn.d.c.a(a.this.b, -1);
                        }
                        i = 200;
                    }
                    d.a aVarK = com.qiyukf.unicorn.c.h().k();
                    if (aVarK != null) {
                        aVarK.onEvaluationEvent(a.this.b);
                    }
                    RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                    if (requestCallbackWrapper2 != null) {
                        requestCallbackWrapper2.onResult(i, str2, th);
                    }
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
                public final void onFailed(int i) {
                    super.onFailed(i);
                }
            });
        }
    }

    public final void a(IMMessage iMMessage, int i, String str, List<String> list, String str2, int i2, RequestCallbackWrapper<String> requestCallbackWrapper) {
        if (com.qiyukf.unicorn.c.h().d().a(iMMessage.getSessionId(), true)) {
            if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.f.c) {
                com.qiyukf.unicorn.h.a.f.c cVar = (com.qiyukf.unicorn.h.a.f.c) iMMessage.getAttachment();
                com.qiyukf.unicorn.h.a.f.c cVar2 = new com.qiyukf.unicorn.h.a.f.c();
                cVar2.b(i);
                cVar2.a(cVar.i());
                cVar2.b(str);
                cVar2.a("android");
                cVar2.a(cVar.f());
                cVar2.a(list);
                cVar2.d(i2);
                if (cVar.n() != null) {
                    cVar2.a(cVar.n());
                }
                String sessionId = iMMessage.getSessionId();
                IMMessage iMMessageQueryLastMessage = ((MsgService) NIMClient.getService(MsgService.class)).queryLastMessage(sessionId, SessionTypeEnum.Ysf);
                c.a(cVar2, sessionId).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.k.a.7
                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onException(Throwable th) {
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onFailed(int i3) {
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
                    }

                    public AnonymousClass7() {
                    }
                });
                a(cVar2.f(), new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.8
                    final /* synthetic */ IMMessage a;
                    final /* synthetic */ String b;
                    final /* synthetic */ com.qiyukf.unicorn.h.a.f.c c;
                    final /* synthetic */ com.qiyukf.unicorn.h.a.f.c d;
                    final /* synthetic */ String e;
                    final /* synthetic */ IMMessage f;
                    final /* synthetic */ RequestCallbackWrapper g;

                    public AnonymousClass8(IMMessage iMMessage2, String str22, com.qiyukf.unicorn.h.a.f.c cVar3, com.qiyukf.unicorn.h.a.f.c cVar22, String sessionId2, IMMessage iMMessageQueryLastMessage2, RequestCallbackWrapper requestCallbackWrapper2) {
                        iMMessage = iMMessage2;
                        str = str22;
                        cVar = cVar3;
                        cVar = cVar22;
                        str = sessionId2;
                        iMMessage = iMMessageQueryLastMessage2;
                        requestCallbackWrapper = requestCallbackWrapper2;
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                        d.a aVarK;
                        String str22 = str3;
                        if (i3 == 200 || i3 == 201) {
                            String sessionId2 = iMMessage.getSessionId();
                            SessionTypeEnum sessionTypeEnum = SessionTypeEnum.Ysf;
                            IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(sessionId2, sessionTypeEnum, str);
                            iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
                            ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageCreateTextMessage, true);
                            cVar.l();
                            ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
                            if (cVar.i().b() != null) {
                                j.a(cVar.i().b(), "richTextThanks", str22);
                            }
                            cVar.i().a(str22);
                            if (i3 == 201) {
                                cVar.a(0);
                            } else {
                                cVar.a(1);
                            }
                            ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str, sessionTypeEnum, cVar), true);
                            if (cVar.f() == com.qiyukf.unicorn.d.c.j(str)) {
                                com.qiyukf.unicorn.d.c.b(str, 2);
                                com.qiyukf.unicorn.d.c.a(str, -1);
                            }
                            i3 = 200;
                        }
                        if ((com.qiyukf.unicorn.d.c.j(str) == ((com.qiyukf.unicorn.h.a.f.c) iMMessage.getAttachment()).f() || iMMessage.isTheSame(iMMessage)) && (aVarK = com.qiyukf.unicorn.c.h().k()) != null) {
                            aVarK.onEvaluationEvent(str);
                        }
                        RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                        if (requestCallbackWrapper2 != null) {
                            requestCallbackWrapper2.onResult(i3, str22, th);
                        }
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onFailed(int i3) {
                        super.onFailed(i3);
                    }
                });
                return;
            }
            if (iMMessage2.getAttachment() instanceof g) {
                g gVar = (g) iMMessage2.getAttachment();
                com.qiyukf.unicorn.h.a.f.c cVar3 = new com.qiyukf.unicorn.h.a.f.c();
                cVar3.b(i);
                cVar3.a(gVar.e());
                cVar3.b(str);
                cVar3.a("android");
                cVar3.a(gVar.b());
                cVar3.a(list);
                cVar3.d(i2);
                if (gVar.l() != 0) {
                    cVar3.a(Long.valueOf(gVar.l()));
                }
                String sessionId2 = iMMessage2.getSessionId();
                IMMessage iMMessageQueryLastMessage2 = ((MsgService) NIMClient.getService(MsgService.class)).queryLastMessage(sessionId2, SessionTypeEnum.Ysf);
                c.a(cVar3, sessionId2).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.k.a.9
                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onException(Throwable th) {
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onFailed(int i3) {
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallback
                    public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
                    }

                    public AnonymousClass9() {
                    }
                });
                a(cVar3.f(), new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.10
                    final /* synthetic */ g a;
                    final /* synthetic */ IMMessage b;
                    final /* synthetic */ com.qiyukf.unicorn.h.a.f.c c;
                    final /* synthetic */ String d;
                    final /* synthetic */ IMMessage e;
                    final /* synthetic */ RequestCallbackWrapper f;

                    public AnonymousClass10(g gVar2, IMMessage iMMessage2, com.qiyukf.unicorn.h.a.f.c cVar32, String sessionId22, IMMessage iMMessageQueryLastMessage22, RequestCallbackWrapper requestCallbackWrapper2) {
                        gVar = gVar2;
                        iMMessage = iMMessage2;
                        cVar = cVar32;
                        str = sessionId22;
                        iMMessage = iMMessageQueryLastMessage22;
                        requestCallbackWrapper = requestCallbackWrapper2;
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                        d.a aVarK;
                        String str22 = str3;
                        if (i3 == 200 || i3 == 201) {
                            gVar.g();
                            ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
                            if (cVar.i().b() != null) {
                                j.a(cVar.i().b(), "richTextThanks", str22);
                            }
                            cVar.i().a(str22);
                            if (i3 == 201) {
                                cVar.a(0);
                            } else {
                                cVar.a(1);
                            }
                            ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, cVar), true);
                            if (cVar.f() == com.qiyukf.unicorn.d.c.j(str)) {
                                com.qiyukf.unicorn.d.c.b(str, 2);
                                com.qiyukf.unicorn.d.c.a(str, -1);
                            }
                            i3 = 200;
                        }
                        if ((com.qiyukf.unicorn.d.c.j(str) == ((g) iMMessage.getAttachment()).b() || iMMessage.isTheSame(iMMessage)) && (aVarK = com.qiyukf.unicorn.c.h().k()) != null) {
                            aVarK.onEvaluationEvent(str);
                        }
                        RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                        if (requestCallbackWrapper2 != null) {
                            requestCallbackWrapper2.onResult(i3, str22, th);
                        }
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
                    public final void onFailed(int i3) {
                        super.onFailed(i3);
                        requestCallbackWrapper.onFailed(i3);
                    }
                });
            }
        }
    }

    public final void a(String str, int i, String str2, List<String> list, int i2, RequestCallbackWrapper<String> requestCallbackWrapper) {
        long jK = com.qiyukf.unicorn.d.c.k(str);
        x xVar = new x();
        xVar.a(i);
        xVar.a(b(str));
        xVar.b(str2);
        xVar.a("android");
        p pVarC = com.qiyukf.unicorn.c.h().c(str);
        if (pVarC != null && pVarC.f) {
            xVar.a(pVarC.g);
        } else {
            xVar.a(jK);
        }
        xVar.a(list);
        xVar.b(i2);
        c.a(xVar, str);
        b(xVar.d(), new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.k.a.11
            final /* synthetic */ x a;
            final /* synthetic */ String b;
            final /* synthetic */ RequestCallbackWrapper c;

            public AnonymousClass11(x xVar2, String str3, RequestCallbackWrapper requestCallbackWrapper2) {
                xVar = xVar2;
                str = str3;
                requestCallbackWrapper = requestCallbackWrapper2;
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                String str22 = str3;
                if (i3 == 200) {
                    if (xVar.k().b() != null) {
                        j.a(xVar.k().b(), "messageThanks", str22);
                    }
                    xVar.k().a(str22);
                    com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, xVar);
                    ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA, true);
                    com.qiyukf.unicorn.d.c.c(str, dVarA.getUuid());
                    if (xVar.d() == com.qiyukf.unicorn.d.c.j(str)) {
                        com.qiyukf.unicorn.d.c.c(str, 2);
                    }
                }
                d.a aVarK = com.qiyukf.unicorn.c.h().k();
                if (aVarK != null) {
                    aVarK.onRobotEvaluationEvent(str);
                }
                RequestCallbackWrapper requestCallbackWrapper2 = requestCallbackWrapper;
                if (requestCallbackWrapper2 != null) {
                    requestCallbackWrapper2.onResult(i3, str22, th);
                }
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper, com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i3) {
                super.onFailed(i3);
            }
        });
    }
}

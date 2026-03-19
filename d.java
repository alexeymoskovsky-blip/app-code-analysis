package com.qiyukf.unicorn.k;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import androidx.collection.LongSparseArray;
import com.facebook.AuthenticationTokenClaims;
import com.qiyukf.basemodule.ServiceHelper;
import com.qiyukf.basemodule.interfaces.VideoService;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.database.f;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.StatusCode;
import com.qiyukf.nimlib.sdk.auth.AuthServiceObserver;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.attachment.MsgAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.uikit.session.helper.CustomURLSpan;
import com.qiyukf.uikit.session.module.input.VisitorForbiddenHelper;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderFactory;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderText;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderTips;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.event.EventCallback;
import com.qiyukf.unicorn.api.event.EventService;
import com.qiyukf.unicorn.api.event.RequestStaffCallback;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.event.entry.RequestStaffEntry;
import com.qiyukf.unicorn.api.event.entry.TransferCloseResultEntry;
import com.qiyukf.unicorn.api.event.entry.TransferRequestEntry;
import com.qiyukf.unicorn.api.event.eventcallback.TransferCloseResultCallback;
import com.qiyukf.unicorn.api.event.eventcallback.TransferRequestCallback;
import com.qiyukf.unicorn.api.msg.OnPushMessageListener;
import com.qiyukf.unicorn.api.msg.SessionStatusEnum;
import com.qiyukf.unicorn.api.msg.attachment.ProductAttachment;
import com.qiyukf.unicorn.api.pop.POPManager;
import com.qiyukf.unicorn.f.g;
import com.qiyukf.unicorn.f.p;
import com.qiyukf.unicorn.f.q;
import com.qiyukf.unicorn.f.t;
import com.qiyukf.unicorn.f.u;
import com.qiyukf.unicorn.f.w;
import com.qiyukf.unicorn.f.z;
import com.qiyukf.unicorn.h.a.a.a.o;
import com.qiyukf.unicorn.h.a.a.a.s;
import com.qiyukf.unicorn.h.a.a.a.x;
import com.qiyukf.unicorn.h.a.d.ab;
import com.qiyukf.unicorn.h.a.d.ac;
import com.qiyukf.unicorn.h.a.d.ag;
import com.qiyukf.unicorn.h.a.d.ah;
import com.qiyukf.unicorn.h.a.d.ai;
import com.qiyukf.unicorn.h.a.d.am;
import com.qiyukf.unicorn.h.a.d.ao;
import com.qiyukf.unicorn.h.a.d.ar;
import com.qiyukf.unicorn.h.a.d.az;
import com.qiyukf.unicorn.h.a.d.c;
import com.qiyukf.unicorn.h.a.f.j;
import com.qiyukf.unicorn.h.a.f.y;
import com.qiyukf.unicorn.n.v;
import com.qiyukf.unicorn.ui.viewholder.MsgViewHolderCardFloatMessage;
import com.qiyukf.unicorn.ui.viewholder.MsgViewHolderCardFloatSendMessage;
import com.qiyukf.unicorn.ui.viewholder.a.aa;
import com.qiyukf.unicorn.ui.viewholder.a.ad;
import com.qiyukf.unicorn.ui.viewholder.h;
import com.qiyukf.unicorn.ui.viewholder.i;
import com.qiyukf.unicorn.ui.viewholder.k;
import com.qiyukf.unicorn.ui.viewholder.l;
import com.qiyukf.unicorn.ui.viewholder.m;
import com.qiyukf.unicorn.ui.viewholder.n;
import com.qiyukf.unicorn.ui.viewholder.o;
import com.qiyukf.unicorn.ui.viewholder.r;
import com.qiyukf.unicorn.video.VideoSignProtocolDialog;
import com.qiyukf.unicorn.widget.dialog.UnicornDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: compiled from: SessionManager.java */
/* JADX INFO: loaded from: classes6.dex */
public final class d {
    private Context E;
    private Handler G;
    private boolean H;
    private u I;
    private j K;
    private RequestStaffCallback L;
    private Set<Integer> M;
    private boolean N;
    private ConsultSource e;
    private a m;
    private Map<String, Runnable> c = new HashMap();
    private final Map<Long, Long> d = new HashMap();
    private boolean j = false;
    private TransferRequestCallback k = null;
    private Map<String, p> l = new HashMap();
    private Map<String, ProductDetail> n = new HashMap();
    private Map<String, List<com.qiyukf.unicorn.h.a.e.a>> o = new HashMap();
    private Map<Long, List<g>> p = new HashMap();
    private Map<String, t> q = new HashMap();
    private LongSparseArray<t> r = new LongSparseArray<>();
    private Map<String, Long> s = new HashMap();
    private Map<String, com.qiyukf.unicorn.f.b> t = new HashMap();
    private Map<String, Boolean> u = new HashMap();
    private Map<String, Boolean> v = new HashMap();
    private Map<String, Boolean> w = new HashMap();
    private boolean x = false;
    private Map<String, Boolean> y = new HashMap();
    private Map<String, com.qiyukf.unicorn.f.a> z = new HashMap();
    private Map<String, Long> A = new HashMap();
    private LongSparseArray<x> B = new LongSparseArray<>();
    private Map<String, Boolean> C = new HashMap();
    private Map<String, ai> D = new HashMap();
    private Map<String, q> F = new HashMap();
    private boolean J = false;
    private boolean O = false;
    private Observer<CustomNotification> P = new Observer<CustomNotification>() { // from class: com.qiyukf.unicorn.k.d.12
        public AnonymousClass12() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(CustomNotification customNotification) {
            CustomNotification customNotification2 = customNotification;
            if (customNotification2.getSessionType() == SessionTypeEnum.Ysf) {
                try {
                    d.a(d.this, customNotification2.getTime(), customNotification2.getSessionId(), customNotification2.getContent());
                } catch (Exception e) {
                    AbsUnicornLog.e("SessionManager", "customNotificationObserver error", e);
                }
            }
        }
    };
    private Observer<IMMessage> Q = new Observer<IMMessage>() { // from class: com.qiyukf.unicorn.k.d.13
        public AnonymousClass13() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(IMMessage iMMessage) {
            IMMessage iMMessage2 = iMMessage;
            if (iMMessage2.getSessionType() == SessionTypeEnum.Ysf) {
                if (d.this.b(iMMessage2) && f.a().c()) {
                    iMMessage2.setStatus(MsgStatusEnum.unread);
                    MsgDBHelper.updateMessageStatus((com.qiyukf.nimlib.session.d) iMMessage2);
                }
                com.qiyukf.unicorn.k.a.a(iMMessage2);
            }
        }
    };
    private Observer<List<IMMessage>> R = new Observer<List<IMMessage>>() { // from class: com.qiyukf.unicorn.k.d.2
        public AnonymousClass2() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(List<IMMessage> list) {
            String strQ;
            List<IMMessage> list2 = list;
            if (list2.get(0).getSessionType() == SessionTypeEnum.Ysf) {
                for (IMMessage iMMessage : list2) {
                    if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.a) {
                        com.qiyukf.unicorn.h.a.d.a aVar = (com.qiyukf.unicorn.h.a.d.a) iMMessage.getAttachment();
                        d.this.f.a(aVar.c(), aVar.d(), aVar.k());
                        com.qiyukf.unicorn.d.c.t(aVar.c());
                        if (com.qiyukf.unicorn.c.h().i() != null && !TextUtils.isEmpty(com.qiyukf.unicorn.c.h().i().shopId)) {
                            com.qiyukf.unicorn.d.c.d(com.qiyukf.unicorn.c.h().i().shopId, aVar.c());
                        }
                        d.this.g.a(aVar.n());
                        d.a(d.this, aVar);
                        if (!d.this.v(iMMessage.getSessionId()).booleanValue()) {
                            d.this.a(iMMessage);
                        }
                    }
                    d.this.i.a(iMMessage);
                    int iA = c.a(iMMessage);
                    if (iMMessage.getDirect() == MsgDirectionEnum.In && !(iMMessage.getAttachment() instanceof ar)) {
                        z zVar = (z) d.this.b.get(iMMessage.getSessionId());
                        if (iA == 2) {
                            return;
                        }
                        if (d.this.l.get(iMMessage.getFromAccount()) != null && !((p) d.this.l.get(iMMessage.getFromAccount())).f) {
                            strQ = "CORP_AVATER_TAG";
                        } else {
                            if (com.qiyukf.unicorn.c.h().i() != null && !TextUtils.isEmpty(com.qiyukf.unicorn.c.h().i().shopId)) {
                                String strU = zVar == null ? com.qiyukf.unicorn.d.c.u(com.qiyukf.unicorn.c.h().i().shopId) : zVar.d;
                                if (TextUtils.isEmpty(strU)) {
                                    strU = iMMessage.getFromAccount();
                                }
                                iMMessage.setFromAccount(strU);
                            } else {
                                strQ = zVar == null ? com.qiyukf.unicorn.d.c.q() : zVar.d;
                                if (!com.qiyukf.unicorn.f.x.c(iMMessage.getFromAccount())) {
                                    if (TextUtils.isEmpty(strQ) && d.this.g(iMMessage.getSessionId()) == 0 && (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.z)) {
                                        iMMessage.setFromAccount(iMMessage.getSessionId());
                                        return;
                                    } else if (TextUtils.isEmpty(strQ)) {
                                        strQ = com.qiyukf.unicorn.f.x.a(iMMessage.getSessionId());
                                    }
                                }
                            }
                            ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, false);
                        }
                        iMMessage.setFromAccount(strQ);
                        ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, false);
                    }
                    if (iMMessage.getAttachment() instanceof ac) {
                        long jD = d.this.d(iMMessage.getSessionId());
                        if (jD <= 0) {
                            jD = d.this.h(iMMessage.getSessionId());
                        }
                        if (jD > 0) {
                            ((ac) iMMessage.getAttachment()).a(jD);
                            ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage, false);
                        }
                    }
                    if (iMMessage.getAttachment() instanceof s) {
                        com.qiyukf.unicorn.d.c.a(d.this.d(iMMessage.getSessionId()), iMMessage.getUuid());
                    }
                    if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.c) {
                        d.a(iMMessage.getSessionId(), (com.qiyukf.unicorn.h.a.d.c) iMMessage.getAttachment(), iMMessage.getTime(), iMMessage.getDirect());
                    }
                }
                com.qiyukf.unicorn.k.a.a(list2.get(0));
            }
        }
    };
    private Observer<StatusCode> S = new Observer<StatusCode>() { // from class: com.qiyukf.unicorn.k.d.3
        public AnonymousClass3() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(StatusCode statusCode) {
            if (statusCode == StatusCode.LOGINED && com.qiyukf.unicorn.c.f().isMixSDK && com.qiyukf.unicorn.c.f().isPullMessageFromServer && System.currentTimeMillis() - com.qiyukf.unicorn.d.c.x() > 86400000) {
                com.qiyukf.unicorn.d.c.d(System.currentTimeMillis());
                v.a();
            }
        }
    };
    private com.qiyukf.unicorn.h.a.c a = com.qiyukf.unicorn.h.a.c.a();
    private Map<String, z> b = new HashMap();
    private com.qiyukf.unicorn.f.x f = new com.qiyukf.unicorn.f.x();
    private w g = new w();
    private com.qiyukf.unicorn.k.a h = new com.qiyukf.unicorn.k.a();
    private com.qiyukf.unicorn.k.b i = new com.qiyukf.unicorn.k.b(this.g, this.f);

    /* JADX INFO: compiled from: SessionManager.java */
    public interface a {
        void initState();

        boolean isInMessageListPanel(String str);

        void onEvaluationEvent(String str);

        void onRequestStaffStart(String str, com.qiyukf.unicorn.f.d dVar);

        void onRevertStatus(String str);

        void onRobotEvaluationEvent(String str);

        void onRobotEvaluatorOpen(String str);

        void onSaveMsgToPage(String str, List<IMMessage> list);

        void onUpdateEvaluationShow(String str, boolean z);

        void onVideoError(String str, String str2);

        void openInquiryForm(long j);
    }

    public static /* synthetic */ RequestStaffCallback d(d dVar) {
        dVar.L = null;
        return null;
    }

    public final void a(Set<Integer> set) {
        this.M = set;
    }

    public final void a() {
        a aVar = this.m;
        if (aVar != null) {
            aVar.initState();
        }
    }

    public d(Context context) {
        this.G = new Handler(context.getMainLooper());
        com.qiyukf.nimlib.session.e.a().c().a(MsgTypeEnum.qiyuCustom.getValue(), this.a);
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.P, true);
        if (com.qiyukf.unicorn.c.f().sdkEvents != null && com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory != null) {
            com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory.eventOf(2);
        }
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeMsgStatus(this.Q, true);
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeReceiveMessage(this.R, true);
        ((AuthServiceObserver) NIMClient.getService(AuthServiceObserver.class)).observeOnlineStatus(this.S, true);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.a.class, (Class<? extends MsgViewHolderBase>) MsgViewHolderTips.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.f.b.class, (Class<? extends MsgViewHolderBase>) r.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.f.c.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.f.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.g.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.d.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) ac.class, (Class<? extends MsgViewHolderBase>) o.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.f.x.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.p.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) ar.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.s.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) y.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.q.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) ag.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.c.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) ah.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.e.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) ab.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.d.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) ProductAttachment.class, (Class<? extends MsgViewHolderBase>) m.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.z.class, (Class<? extends MsgViewHolderBase>) n.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) ao.class, (Class<? extends MsgViewHolderBase>) MsgViewHolderTips.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) am.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.u.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.t.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.t.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.m.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.g.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.m.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) az.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.v.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) j.class, (Class<? extends MsgViewHolderBase>) h.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.q.class, (Class<? extends MsgViewHolderBase>) l.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.f.m.class, (Class<? extends MsgViewHolderBase>) k.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.f.e.class, (Class<? extends MsgViewHolderBase>) i.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.c.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.b.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.d.class, (Class<? extends MsgViewHolderBase>) MsgViewHolderCardFloatMessage.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.e.class, (Class<? extends MsgViewHolderBase>) MsgViewHolderCardFloatSendMessage.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.a.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.f.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.b.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.g.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.n.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.t.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.p.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.w.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.q.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.x.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.t.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.ab.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.r.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.y.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.v.class, (Class<? extends MsgViewHolderBase>) ad.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.j.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.o.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) x.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.h.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.f.ai.class, (Class<? extends MsgViewHolderBase>) MsgViewHolderText.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.o.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.u.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.u.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.ac.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) s.class, (Class<? extends MsgViewHolderBase>) aa.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.h.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.l.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.f.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.m.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.b.a.c.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.v.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.i.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.n.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.c.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.j.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.d.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.k.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.k.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.r.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.a.l.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.s.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.b.b.b.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.q.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.b.b.c.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.z.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.b.c.class, (Class<? extends MsgViewHolderBase>) ad.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.b.a.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.a.p.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.a.b.b.class, (Class<? extends MsgViewHolderBase>) ad.class);
        MsgViewHolderFactory.register((Class<? extends MsgAttachment>) com.qiyukf.unicorn.h.a.d.o.class, (Class<? extends MsgViewHolderBase>) com.qiyukf.unicorn.ui.viewholder.j.class);
    }

    public final com.qiyukf.unicorn.f.x b() {
        return this.f;
    }

    public final w c() {
        return this.g;
    }

    public final com.qiyukf.unicorn.k.a d() {
        return this.h;
    }

    public final void a(RequestStaffCallback requestStaffCallback) {
        this.L = requestStaffCallback;
    }

    public final boolean a(u uVar) {
        ConsultSource consultSource;
        int iF = uVar.f();
        boolean zG = uVar.g();
        String strB = uVar.b();
        if (iF != 3 && iF != 5 && iF != 8) {
            uVar.c(0);
        }
        AbsUnicornLog.i("SessionManager", "requestStaff isTransfer= ".concat(String.valueOf(zG)));
        AbsUnicornLog.i("SessionManager", "requestStaff requestStaffScenes= ".concat(String.valueOf(iF)));
        this.j = zG;
        if (!zG) {
            this.k = null;
        }
        if (this.K != null) {
            AbsUnicornLog.i("SessionManager", "requestStaff mPlatformToCorpCmdAttachment ");
            uVar.a(true);
            uVar.a((com.qiyukf.unicorn.f.d) null);
            uVar.a(0);
            uVar.a(this.K.d());
            uVar.a(Long.valueOf(this.K.c()));
            this.K = null;
        }
        uVar.b(com.qiyukf.unicorn.n.aa.c(this.E));
        String strB2 = uVar.b();
        boolean zC = uVar.c();
        com.qiyukf.unicorn.f.d dVarE = uVar.e();
        if (TextUtils.isEmpty(strB2) || (consultSource = this.e) == null) {
            AbsUnicornLog.i("SessionManager", "needRequest is false exchange= ".concat(String.valueOf(strB2)));
        } else {
            uVar.b(consultSource.isEnableVideo || uVar.o());
            q qVar = new q();
            qVar.a(this.e.staffId);
            qVar.b(this.e.groupId);
            qVar.c(this.e.robotId);
            qVar.b(this.e.robotFirst);
            qVar.a(zC);
            qVar.d(this.e.faqGroupId);
            qVar.e(this.e.groupTmpId);
            qVar.a(this.e.uri);
            if (dVarE != null) {
                qVar.a(dVarE.b());
                qVar.b(dVarE.a());
            }
            if (!qVar.equals(this.F.get(strB2))) {
                this.F.put(strB2, qVar);
            } else {
                z zVar = this.b.get(strB2);
                AbsUnicornLog.i("SessionManager", "needRequest session= ".concat(String.valueOf(zVar)));
                AbsUnicornLog.i("SessionManager", "needRequest Stream sessionRequestStaffStream=".concat(String.valueOf(uVar)));
                if ((zVar == null || zVar.g != 1 || (!zC && (dVarE == null || dVarE.b <= 0))) && ((zVar == null || zVar.g != 1 || uVar.m() == 0 || uVar.m() == (-zVar.b)) && ((zVar != null || i(strB2) || b(strB2) != 0) && uVar.j() != 1 && uVar.d() == null && !uVar.o()))) {
                    AbsUnicornLog.i("SessionManager", "do not needRequest");
                }
            }
            if (com.qiyukf.unicorn.c.f().sdkEvents != null && com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory != null) {
                AbsUnicornLog.i("SessionManager", "sdkEvent requestStaffIntercept");
                RequestStaffEntry requestStaffEntry = new RequestStaffEntry();
                com.qiyukf.unicorn.f.d dVarE2 = uVar.e();
                String strB3 = uVar.b();
                int iF2 = uVar.f();
                boolean zC2 = uVar.c();
                boolean zG2 = uVar.g();
                if (this.e != null) {
                    AbsUnicornLog.i("SessionManager", "requestStaffIntercept consultSource");
                    requestStaffEntry.setUri(this.e.uri);
                    requestStaffEntry.setTitle(this.e.title);
                    requestStaffEntry.setCustom(this.e.custom);
                    requestStaffEntry.setGroupId(this.e.groupId);
                    requestStaffEntry.setStaffId(this.e.staffId);
                    requestStaffEntry.setFaqGroupId(this.e.faqGroupId);
                    requestStaffEntry.setRobotFirst(this.e.robotFirst);
                    requestStaffEntry.setVipLevel(this.e.vipLevel);
                    requestStaffEntry.setRobotId(this.e.robotId);
                    requestStaffEntry.setProductDetail(this.e.productDetail);
                    requestStaffEntry.setCardDetail(this.e.cardDetail);
                }
                if (dVarE2 != null) {
                    AbsUnicornLog.i("SessionManager", "requestStaffIntercept category");
                    if (dVarE2.a() == 0) {
                        requestStaffEntry.setGroupId(dVarE2.c());
                    } else {
                        requestStaffEntry.setGroupId(dVarE2.a());
                    }
                    requestStaffEntry.setStaffId(dVarE2.b());
                    requestStaffEntry.setLabel(dVarE2.c);
                    requestStaffEntry.setEntryId(dVarE2.d);
                }
                requestStaffEntry.setShopId(strB3 == null ? "-1" : strB3);
                requestStaffEntry.setScenes(iF2);
                requestStaffEntry.setHumanOnly(zC2);
                requestStaffEntry.setTransfar(zG2);
                if (g(strB3) == 0 || g(strB3) != 1) {
                    requestStaffEntry.setRobot(false);
                } else {
                    requestStaffEntry.setRobot(true);
                }
                uVar.a(dVarE2);
                UnicornEventBase unicornEventBaseEventOf = com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory.eventOf(0);
                if (unicornEventBaseEventOf != null) {
                    unicornEventBaseEventOf.onEvent(requestStaffEntry, this.E, new EventCallback<RequestStaffEntry>() { // from class: com.qiyukf.unicorn.k.d.11
                        final /* synthetic */ u a;
                        final /* synthetic */ boolean b;
                        final /* synthetic */ String c;

                        public AnonymousClass11(u uVar2, boolean zC22, String strB32) {
                            uVar = uVar2;
                            z = zC22;
                            str = strB32;
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public final /* synthetic */ void onProcessEventSuccess(RequestStaffEntry requestStaffEntry2) {
                            RequestStaffEntry requestStaffEntry3 = requestStaffEntry2;
                            uVar.a(requestStaffEntry3.isHumanOnly());
                            uVar.a(requestStaffEntry3);
                            d.this.b(uVar);
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public final void onPorcessEventError() {
                            uVar.a((RequestStaffEntry) null);
                            uVar.a(z);
                            d.this.b(uVar);
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public final void onNotPorcessEvent() {
                            uVar.a((RequestStaffEntry) null);
                            uVar.a(z);
                            d.this.b(uVar);
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public final void onInterceptEvent() {
                            if (d.this.g(str) == 0 || d.this.g(str) != 1) {
                                d.this.m.onRevertStatus(str);
                            }
                        }
                    });
                } else {
                    uVar2.a((RequestStaffEntry) null);
                    uVar2.a(zC22);
                    b(uVar2);
                }
                RequestStaffCallback requestStaffCallback = this.L;
                if (requestStaffCallback != null) {
                    requestStaffCallback.onSuccess();
                    this.L = null;
                }
                return true;
            }
            AbsUnicornLog.i("SessionManager", "sdkEvent realRequestStaff");
            b(uVar2);
            return i(strB);
        }
        if (this.L != null) {
            if (i(strB)) {
                this.L.onSuccess();
            } else {
                this.L.onFailed(202);
            }
            this.L = null;
        }
        return i(strB);
    }

    public void b(u uVar) {
        C(uVar.b());
        if (!com.qiyukf.unicorn.c.h().h.c().containsKey(uVar.b()) && this.x) {
            AbsUnicornLog.i("SessionManager", "realRequestStaff evaluatorAgainOpen");
            this.x = false;
            new com.qiyukf.unicorn.n.a<Void, Void>(com.qiyukf.unicorn.n.a.HTTP_TAG) { // from class: com.qiyukf.unicorn.k.d.6
                final /* synthetic */ RequestCallback a;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(String str, RequestCallback requestCallback) {
                    super(str);
                    requestCallback = requestCallback;
                }

                @Override // com.qiyukf.unicorn.n.a
                public final /* synthetic */ Void doInBackground(Void[] voidArr) {
                    com.qiyukf.unicorn.i.a.b(com.qiyukf.unicorn.d.c.d(), com.qiyukf.unicorn.d.c.g(), (RequestCallback<com.qiyukf.unicorn.f.o>) requestCallback);
                    return null;
                }
            }.execute(new Void[0]);
        } else {
            AbsUnicornLog.i("SessionManager", "realRequestStaff requestVideoProtocol");
            c(uVar);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$1 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass1 implements RequestCallback<com.qiyukf.unicorn.f.o> {
        final /* synthetic */ u a;

        public AnonymousClass1(u uVar) {
            this.a = uVar;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* synthetic */ void onSuccess(com.qiyukf.unicorn.f.o oVar) {
            final com.qiyukf.unicorn.f.o oVar2 = oVar;
            Handler handlerB = com.qiyukf.unicorn.n.e.b();
            final u uVar = this.a;
            handlerB.post(new Runnable() { // from class: com.qiyukf.unicorn.k.d$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(oVar2, uVar);
                }
            });
        }

        public /* synthetic */ void a(com.qiyukf.unicorn.f.o oVar, u uVar) {
            if (!oVar.e() || oVar.d() == null || oVar.d().a() == null || !(d.this.E instanceof Activity)) {
                d.this.c(uVar);
            } else {
                d.this.a(uVar.b());
                new com.qiyukf.unicorn.ui.evaluate.a(d.this.E, uVar.b(), oVar, new RequestCallbackWrapper() { // from class: com.qiyukf.unicorn.k.d.1.1
                    final /* synthetic */ u a;

                    public C01831(u uVar2) {
                        uVar = uVar2;
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public final void onResult(int i, Object obj, Throwable th) {
                        if (i == 400) {
                            EventService.cancelEvaluation(uVar.b());
                        }
                        d.this.C(uVar.b());
                        d.this.c(uVar);
                    }
                }).show();
            }
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$1$1 */
        /* JADX INFO: compiled from: SessionManager.java */
        public class C01831 extends RequestCallbackWrapper {
            final /* synthetic */ u a;

            public C01831(u uVar2) {
                uVar = uVar2;
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final void onResult(int i, Object obj, Throwable th) {
                if (i == 400) {
                    EventService.cancelEvaluation(uVar.b());
                }
                d.this.C(uVar.b());
                d.this.c(uVar);
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
            Handler handlerB = com.qiyukf.unicorn.n.e.b();
            final u uVar = this.a;
            handlerB.post(new Runnable() { // from class: com.qiyukf.unicorn.k.d$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(uVar);
                }
            });
        }

        public /* synthetic */ void b(u uVar) {
            d.this.c(uVar);
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
            Handler handlerB = com.qiyukf.unicorn.n.e.b();
            final u uVar = this.a;
            handlerB.post(new Runnable() { // from class: com.qiyukf.unicorn.k.d$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(uVar);
                }
            });
        }

        public /* synthetic */ void a(u uVar) {
            d.this.c(uVar);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$6 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass6 extends com.qiyukf.unicorn.n.a<Void, Void> {
        final /* synthetic */ RequestCallback a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(String str, RequestCallback requestCallback) {
            super(str);
            requestCallback = requestCallback;
        }

        @Override // com.qiyukf.unicorn.n.a
        public final /* synthetic */ Void doInBackground(Void[] voidArr) {
            com.qiyukf.unicorn.i.a.b(com.qiyukf.unicorn.d.c.d(), com.qiyukf.unicorn.d.c.g(), (RequestCallback<com.qiyukf.unicorn.f.o>) requestCallback);
            return null;
        }
    }

    public void c(u uVar) {
        if (uVar.o() && this.E != null && this.N) {
            AbsUnicornLog.i("SessionManager", "requestVideoProtocol isRequestVideoProtocol");
            if (this.m != null && !p()) {
                a(uVar.b());
                this.m.onVideoError(uVar.b(), "请配置视频客服SDK");
                return;
            } else {
                new com.qiyukf.unicorn.n.a<Void, Void>(com.qiyukf.unicorn.n.a.HTTP_TAG) { // from class: com.qiyukf.unicorn.k.d.8
                    final /* synthetic */ RequestCallback a;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass8(String str, RequestCallback requestCallback) {
                        super(str);
                        requestCallback = requestCallback;
                    }

                    @Override // com.qiyukf.unicorn.n.a
                    public final /* synthetic */ Void doInBackground(Void[] voidArr) {
                        com.qiyukf.unicorn.i.a.a(com.qiyukf.unicorn.c.e(), com.qiyukf.unicorn.d.c.d(), com.qiyukf.unicorn.d.c.g(), (RequestCallback<String>) requestCallback);
                        return null;
                    }
                }.execute(new Void[0]);
                this.N = false;
                return;
            }
        }
        AbsUnicornLog.i("SessionManager", "requestVideoProtocol requestStaffAndVideo");
        d(uVar);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$7 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass7 implements RequestCallback<String> {
        final /* synthetic */ u a;

        public AnonymousClass7(u uVar) {
            this.a = uVar;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* synthetic */ void onSuccess(String str) {
            final String str2 = str;
            Handler handlerB = com.qiyukf.unicorn.n.e.b();
            final u uVar = this.a;
            handlerB.post(new Runnable() { // from class: com.qiyukf.unicorn.k.d$7$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(str2, uVar);
                }
            });
        }

        public /* synthetic */ void a(String str, final u uVar) {
            if (TextUtils.isEmpty(str)) {
                d.this.d(uVar);
                return;
            }
            d.this.a(uVar.b());
            final VideoSignProtocolDialog videoSignProtocolDialog = new VideoSignProtocolDialog(d.this.E, str);
            videoSignProtocolDialog.setCancelable(false);
            videoSignProtocolDialog.setOnClickListener(new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.k.d$7$$ExternalSyntheticLambda1
                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public final void onClick(int i) {
                    this.f$0.a(uVar, videoSignProtocolDialog, i);
                }
            });
            videoSignProtocolDialog.show();
        }

        public /* synthetic */ void a(u uVar, VideoSignProtocolDialog videoSignProtocolDialog, int i) {
            if (i == 0) {
                if (d.this.m != null) {
                    d.this.m.onVideoError(uVar.b(), "未同意隐私权限,发起视频客服失败");
                }
            } else {
                d.this.d(uVar);
            }
            videoSignProtocolDialog.dismiss();
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
            d.this.a(this.a.b());
            Handler handlerB = com.qiyukf.unicorn.n.e.b();
            final u uVar = this.a;
            handlerB.post(new Runnable() { // from class: com.qiyukf.unicorn.k.d$7$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(uVar);
                }
            });
        }

        public /* synthetic */ void b(u uVar) {
            if (d.this.m != null) {
                d.this.m.onVideoError(uVar.b(), "请求视频客服失败,请稍候重试");
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
            d.this.a(this.a.b());
            Handler handlerB = com.qiyukf.unicorn.n.e.b();
            final u uVar = this.a;
            handlerB.post(new Runnable() { // from class: com.qiyukf.unicorn.k.d$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(uVar);
                }
            });
        }

        public /* synthetic */ void a(u uVar) {
            if (d.this.m != null) {
                d.this.m.onVideoError(uVar.b(), "请求视频客服错误,请稍候重试");
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$8 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass8 extends com.qiyukf.unicorn.n.a<Void, Void> {
        final /* synthetic */ RequestCallback a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(String str, RequestCallback requestCallback) {
            super(str);
            requestCallback = requestCallback;
        }

        @Override // com.qiyukf.unicorn.n.a
        public final /* synthetic */ Void doInBackground(Void[] voidArr) {
            com.qiyukf.unicorn.i.a.a(com.qiyukf.unicorn.c.e(), com.qiyukf.unicorn.d.c.d(), com.qiyukf.unicorn.d.c.g(), (RequestCallback<String>) requestCallback);
            return null;
        }
    }

    public void d(u uVar) {
        a(uVar.b());
        this.I = uVar;
        String strB = uVar.b();
        com.qiyukf.unicorn.f.d dVarE = uVar.e();
        RequestStaffEntry requestStaffEntryI = uVar.i();
        C(strB);
        b(strB, false);
        com.qiyukf.unicorn.h.a.f.s sVar = new com.qiyukf.unicorn.h.a.f.s();
        sVar.g(com.qiyukf.unicorn.c.d().getPackageName());
        sVar.a(uVar.c() ? 1 : 0);
        sVar.f(uVar.o() ? 1 : 0);
        sVar.a(uVar.p());
        if (this.e != null) {
            AbsUnicornLog.i("SessionManager", "requestStaffAndVideo consultSource");
            sVar.g(this.e.groupTmpId);
            sVar.h(this.e.shortcutTemplateId);
            sVar.a(this.e.uri);
            sVar.b(this.e.title);
            sVar.c(this.e.custom);
            sVar.b(this.e.groupId);
            sVar.a(this.e.staffId);
            sVar.d(this.e.faqGroupId);
            sVar.b(this.e.robotFirst ? 1 : 0);
            sVar.c(this.e.vipLevel);
            sVar.e(this.e.robotId);
            sVar.h(this.e.robotWelcomeMsgId);
            if (requestStaffEntryI != null && requestStaffEntryI.getProductDetail() != null) {
                this.e.productDetail = requestStaffEntryI.getProductDetail();
            }
            if (requestStaffEntryI != null && requestStaffEntryI.getCardDetail() != null) {
                this.e.cardDetail = requestStaffEntryI.getCardDetail();
            }
        }
        if (uVar.m() != 0) {
            sVar.e(uVar.m());
        }
        sVar.d("Android");
        sVar.e(Build.BRAND + "$$" + Build.VERSION.RELEASE);
        ConsultSource consultSource = this.e;
        if (consultSource != null && !TextUtils.isEmpty(consultSource.clientIpAddress)) {
            sVar.f(this.e.clientIpAddress);
        } else {
            sVar.f(com.qiyukf.nimlib.c.s());
        }
        sVar.a();
        sVar.g(o());
        sVar.k(com.qiyukf.nimlib.o.a.a() + " " + com.qiyukf.nimlib.o.a.b());
        if (dVarE != null) {
            AbsUnicornLog.i("SessionManager", "requestStaffAndVideo category");
            sVar.a(dVarE.b());
            sVar.b(dVarE.a());
            sVar.c(dVarE.d);
        }
        if (requestStaffEntryI != null) {
            AbsUnicornLog.i("SessionManager", "requestStaffAndVideo entry");
            sVar.a(requestStaffEntryI.getUri());
            sVar.b(requestStaffEntryI.getTitle());
            sVar.c(requestStaffEntryI.getCustom());
            sVar.d(requestStaffEntryI.getFaqGroupId());
            sVar.e(requestStaffEntryI.getRobotId());
            sVar.c(requestStaffEntryI.getEntryId());
            sVar.b(requestStaffEntryI.isRobotFirst() ? 1 : 0);
            sVar.c(requestStaffEntryI.getVipLevel());
            sVar.e(requestStaffEntryI.getRobotId());
            sVar.b(requestStaffEntryI.getGroupId());
            sVar.a(requestStaffEntryI.getStaffId());
        }
        sVar.e(uVar.j());
        sVar.d(uVar.k());
        sVar.f(uVar.l());
        if (!TextUtils.isEmpty(com.qiyukf.unicorn.d.c.i())) {
            sVar.i(com.qiyukf.unicorn.d.c.i());
        }
        if (uVar.d() != null) {
            sVar.a(uVar.d());
            strB = uVar.a();
        }
        if (!TextUtils.isEmpty(uVar.n())) {
            sVar.j(uVar.n());
        }
        AbsUnicornLog.i("SessionManager", "sendCustomNotification RequestStaffAttachment");
        c.a(sVar, strB).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.k.d.9
            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
            }

            public AnonymousClass9() {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i) {
                if (d.this.L != null) {
                    d.this.L.onFailed(i);
                    d.d(d.this);
                }
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onException(Throwable th) {
                if (d.this.L != null) {
                    d.this.L.onFailed(202);
                    d.d(d.this);
                }
            }
        });
        D(strB);
        com.qiyukf.unicorn.b bVarI = com.qiyukf.unicorn.c.i();
        SessionStatusEnum sessionStatusEnum = SessionStatusEnum.NONE;
        bVarI.b(strB);
        a aVar = this.m;
        if (aVar != null) {
            aVar.onRequestStaffStart(strB, dVarE);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$9 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass9 implements RequestCallback<Void> {
        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
        }

        public AnonymousClass9() {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i) {
            if (d.this.L != null) {
                d.this.L.onFailed(i);
                d.d(d.this);
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
            if (d.this.L != null) {
                d.this.L.onFailed(202);
                d.d(d.this);
            }
        }
    }

    public void C(String str) {
        b bVar = new b(str, (byte) 0);
        this.c.put(str, bVar);
        this.G.postDelayed(bVar, q());
    }

    public final void a(String str) {
        Runnable runnableRemove = this.c.remove(str);
        if (runnableRemove != null) {
            this.G.removeCallbacks(runnableRemove);
        }
    }

    private void D(String str) {
        this.b.remove(str);
        this.v.remove(str);
        this.w.remove(str);
    }

    private static int o() {
        VideoService videoService = (VideoService) ServiceHelper.getService(VideoService.class);
        if (videoService != null) {
            return videoService.getVideoVersion();
        }
        return 0;
    }

    public final void a(String str, long j, long j2, String str2, boolean z, TransferCloseResultCallback transferCloseResultCallback, TransferRequestCallback transferRequestCallback) {
        this.k = transferRequestCallback;
        com.qiyukf.unicorn.h.a.f.b bVar = new com.qiyukf.unicorn.h.a.f.b();
        bVar.a(d(str));
        bVar.a(str2);
        c.a(bVar, str).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.k.d.10
            final /* synthetic */ TransferCloseResultCallback a;
            final /* synthetic */ String b;
            final /* synthetic */ com.qiyukf.unicorn.h.a.f.b c;
            final /* synthetic */ long d;
            final /* synthetic */ long e;
            final /* synthetic */ boolean f;

            public AnonymousClass10(TransferCloseResultCallback transferCloseResultCallback2, String str3, com.qiyukf.unicorn.h.a.f.b bVar2, long j3, long j22, boolean z2) {
                transferCloseResultCallback = transferCloseResultCallback2;
                str = str3;
                bVar = bVar2;
                j = j3;
                j = j22;
                z = z2;
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i, Void r6, Throwable th) {
                com.qiyukf.unicorn.f.d dVar;
                if (transferCloseResultCallback != null) {
                    TransferCloseResultEntry transferCloseResultEntry = new TransferCloseResultEntry();
                    transferCloseResultEntry.setCode(i);
                    transferCloseResultCallback.handlerTransferCloseCallback(transferCloseResultEntry);
                }
                if (i == 200) {
                    ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, bVar), true);
                    if (j == 0 && j == 0) {
                        dVar = null;
                    } else {
                        dVar = new com.qiyukf.unicorn.f.d();
                        long j3 = j;
                        dVar.a = j3 != 0 ? 2 : 1;
                        dVar.b = j3 != 0 ? j3 : j;
                        dVar.b(j3);
                        dVar.a(j);
                    }
                    u uVar = new u(str);
                    uVar.a(z);
                    uVar.a(dVar);
                    uVar.a(z ? 5 : 0);
                    uVar.h();
                    d.this.a(uVar);
                    return;
                }
                com.qiyukf.unicorn.n.t.b(R.string.ysf_transfer_staff_error);
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$10 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass10 extends RequestCallbackWrapper<Void> {
        final /* synthetic */ TransferCloseResultCallback a;
        final /* synthetic */ String b;
        final /* synthetic */ com.qiyukf.unicorn.h.a.f.b c;
        final /* synthetic */ long d;
        final /* synthetic */ long e;
        final /* synthetic */ boolean f;

        public AnonymousClass10(TransferCloseResultCallback transferCloseResultCallback2, String str3, com.qiyukf.unicorn.h.a.f.b bVar2, long j3, long j22, boolean z2) {
            transferCloseResultCallback = transferCloseResultCallback2;
            str = str3;
            bVar = bVar2;
            j = j3;
            j = j22;
            z = z2;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i, Void r6, Throwable th) {
            com.qiyukf.unicorn.f.d dVar;
            if (transferCloseResultCallback != null) {
                TransferCloseResultEntry transferCloseResultEntry = new TransferCloseResultEntry();
                transferCloseResultEntry.setCode(i);
                transferCloseResultCallback.handlerTransferCloseCallback(transferCloseResultEntry);
            }
            if (i == 200) {
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, bVar), true);
                if (j == 0 && j == 0) {
                    dVar = null;
                } else {
                    dVar = new com.qiyukf.unicorn.f.d();
                    long j3 = j;
                    dVar.a = j3 != 0 ? 2 : 1;
                    dVar.b = j3 != 0 ? j3 : j;
                    dVar.b(j3);
                    dVar.a(j);
                }
                u uVar = new u(str);
                uVar.a(z);
                uVar.a(dVar);
                uVar.a(z ? 5 : 0);
                uVar.h();
                d.this.a(uVar);
                return;
            }
            com.qiyukf.unicorn.n.t.b(R.string.ysf_transfer_staff_error);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$11 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass11 implements EventCallback<RequestStaffEntry> {
        final /* synthetic */ u a;
        final /* synthetic */ boolean b;
        final /* synthetic */ String c;

        public AnonymousClass11(u uVar2, boolean zC22, String strB32) {
            uVar = uVar2;
            z = zC22;
            str = strB32;
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public final /* synthetic */ void onProcessEventSuccess(RequestStaffEntry requestStaffEntry2) {
            RequestStaffEntry requestStaffEntry3 = requestStaffEntry2;
            uVar.a(requestStaffEntry3.isHumanOnly());
            uVar.a(requestStaffEntry3);
            d.this.b(uVar);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public final void onPorcessEventError() {
            uVar.a((RequestStaffEntry) null);
            uVar.a(z);
            d.this.b(uVar);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public final void onNotPorcessEvent() {
            uVar.a((RequestStaffEntry) null);
            uVar.a(z);
            d.this.b(uVar);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public final void onInterceptEvent() {
            if (d.this.g(str) == 0 || d.this.g(str) != 1) {
                d.this.m.onRevertStatus(str);
            }
        }
    }

    public final int b(String str) {
        p pVar = this.l.get(str);
        if (pVar == null) {
            return 0;
        }
        return pVar.b;
    }

    public final p c(String str) {
        return this.l.get(str);
    }

    public final long d(String str) {
        z zVar = this.b.get(str);
        if (zVar == null) {
            return 0L;
        }
        return zVar.a;
    }

    public final z e(String str) {
        return this.b.get(str);
    }

    public final long f(String str) {
        try {
            if (this.s.get(str) == null) {
                return -100L;
            }
            return this.s.get(str).longValue();
        } catch (NullPointerException e) {
            AbsUnicornLog.e("SessionManager", "获取 getFaqSessionId 失败", e);
            return 0L;
        }
    }

    public final int g(String str) {
        z zVar = this.b.get(str);
        if (zVar == null) {
            return 0;
        }
        return zVar.g;
    }

    public final long h(String str) {
        p pVar = this.l.get(str);
        if (pVar == null) {
            return 0L;
        }
        return pVar.g;
    }

    public final boolean e() {
        return (this.b.isEmpty() && this.l.isEmpty()) ? false : true;
    }

    public final Map<String, z> f() {
        return this.b;
    }

    public final void a(RequestCallback requestCallback) {
        for (String str : this.b.keySet()) {
            com.qiyukf.unicorn.h.a.f.b bVar = new com.qiyukf.unicorn.h.a.f.b();
            bVar.a(this.b.get(str).a);
            c.a(bVar, c.a());
        }
        com.qiyukf.unicorn.h.a.f.g gVar = new com.qiyukf.unicorn.h.a.f.g();
        gVar.a(com.qiyukf.unicorn.d.c.d());
        c.a(gVar, c.a()).setCallback(requestCallback);
        g();
    }

    public final void g() {
        h();
        this.q.clear();
        this.r.clear();
        this.s.clear();
        this.t.clear();
        this.A.clear();
        this.o.clear();
        this.n.clear();
        this.F.clear();
        this.c.clear();
    }

    public final void h() {
        Map<String, z> map = this.b;
        if (map != null) {
            map.clear();
        }
        Runnable runnableRemove = this.c.remove(com.qiyukf.unicorn.d.c.c());
        Handler handler = this.G;
        if (handler != null) {
            if (runnableRemove != null) {
                handler.removeCallbacks(runnableRemove);
            }
            this.G.removeCallbacks(null);
        }
        this.l.clear();
    }

    public final void a(String str, boolean z) {
        Runnable runnable;
        this.H = z;
        r();
        if (this.l.containsKey(str)) {
            if (z) {
                b(str, 0L);
                return;
            }
            p pVar = this.l.get(str);
            if (pVar == null || (runnable = pVar.e) == null) {
                return;
            }
            this.G.removeCallbacks(runnable);
        }
    }

    public final boolean i(String str) {
        return this.c.containsKey(str);
    }

    public final SessionStatusEnum j(String str) {
        if (this.b.containsKey(str)) {
            return SessionStatusEnum.IN_SESSION;
        }
        if (this.l.containsKey(str)) {
            return SessionStatusEnum.IN_QUEUE;
        }
        return SessionStatusEnum.NONE;
    }

    public static IMMessage k(String str) {
        IMMessage iMMessageF = F(str);
        if (iMMessageF == null || !((ar) iMMessageF.getAttachment()).d()) {
            return null;
        }
        return iMMessageF;
    }

    public static boolean l(String str) {
        IMMessage iMMessageF = F(str);
        return iMMessageF == null || !((ar) iMMessageF.getAttachment()).d() || System.currentTimeMillis() - iMMessageF.getTime() > AuthenticationTokenClaims.MAX_TIME_SINCE_TOKEN_ISSUED;
    }

    public final void a(OnPushMessageListener onPushMessageListener) {
        this.i.a(onPushMessageListener);
    }

    public final void b(OnPushMessageListener onPushMessageListener) {
        this.i.b(onPushMessageListener);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$12 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass12 implements Observer<CustomNotification> {
        public AnonymousClass12() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(CustomNotification customNotification) {
            CustomNotification customNotification2 = customNotification;
            if (customNotification2.getSessionType() == SessionTypeEnum.Ysf) {
                try {
                    d.a(d.this, customNotification2.getTime(), customNotification2.getSessionId(), customNotification2.getContent());
                } catch (Exception e) {
                    AbsUnicornLog.e("SessionManager", "customNotificationObserver error", e);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$13 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass13 implements Observer<IMMessage> {
        public AnonymousClass13() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(IMMessage iMMessage) {
            IMMessage iMMessage2 = iMMessage;
            if (iMMessage2.getSessionType() == SessionTypeEnum.Ysf) {
                if (d.this.b(iMMessage2) && f.a().c()) {
                    iMMessage2.setStatus(MsgStatusEnum.unread);
                    MsgDBHelper.updateMessageStatus((com.qiyukf.nimlib.session.d) iMMessage2);
                }
                com.qiyukf.unicorn.k.a.a(iMMessage2);
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$2 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass2 implements Observer<List<IMMessage>> {
        public AnonymousClass2() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(List<IMMessage> list) {
            String strQ;
            List<IMMessage> list2 = list;
            if (list2.get(0).getSessionType() == SessionTypeEnum.Ysf) {
                for (IMMessage iMMessage : list2) {
                    if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.a) {
                        com.qiyukf.unicorn.h.a.d.a aVar = (com.qiyukf.unicorn.h.a.d.a) iMMessage.getAttachment();
                        d.this.f.a(aVar.c(), aVar.d(), aVar.k());
                        com.qiyukf.unicorn.d.c.t(aVar.c());
                        if (com.qiyukf.unicorn.c.h().i() != null && !TextUtils.isEmpty(com.qiyukf.unicorn.c.h().i().shopId)) {
                            com.qiyukf.unicorn.d.c.d(com.qiyukf.unicorn.c.h().i().shopId, aVar.c());
                        }
                        d.this.g.a(aVar.n());
                        d.a(d.this, aVar);
                        if (!d.this.v(iMMessage.getSessionId()).booleanValue()) {
                            d.this.a(iMMessage);
                        }
                    }
                    d.this.i.a(iMMessage);
                    int iA = c.a(iMMessage);
                    if (iMMessage.getDirect() == MsgDirectionEnum.In && !(iMMessage.getAttachment() instanceof ar)) {
                        z zVar = (z) d.this.b.get(iMMessage.getSessionId());
                        if (iA == 2) {
                            return;
                        }
                        if (d.this.l.get(iMMessage.getFromAccount()) != null && !((p) d.this.l.get(iMMessage.getFromAccount())).f) {
                            strQ = "CORP_AVATER_TAG";
                        } else {
                            if (com.qiyukf.unicorn.c.h().i() != null && !TextUtils.isEmpty(com.qiyukf.unicorn.c.h().i().shopId)) {
                                String strU = zVar == null ? com.qiyukf.unicorn.d.c.u(com.qiyukf.unicorn.c.h().i().shopId) : zVar.d;
                                if (TextUtils.isEmpty(strU)) {
                                    strU = iMMessage.getFromAccount();
                                }
                                iMMessage.setFromAccount(strU);
                            } else {
                                strQ = zVar == null ? com.qiyukf.unicorn.d.c.q() : zVar.d;
                                if (!com.qiyukf.unicorn.f.x.c(iMMessage.getFromAccount())) {
                                    if (TextUtils.isEmpty(strQ) && d.this.g(iMMessage.getSessionId()) == 0 && (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.z)) {
                                        iMMessage.setFromAccount(iMMessage.getSessionId());
                                        return;
                                    } else if (TextUtils.isEmpty(strQ)) {
                                        strQ = com.qiyukf.unicorn.f.x.a(iMMessage.getSessionId());
                                    }
                                }
                            }
                            ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, false);
                        }
                        iMMessage.setFromAccount(strQ);
                        ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, false);
                    }
                    if (iMMessage.getAttachment() instanceof ac) {
                        long jD = d.this.d(iMMessage.getSessionId());
                        if (jD <= 0) {
                            jD = d.this.h(iMMessage.getSessionId());
                        }
                        if (jD > 0) {
                            ((ac) iMMessage.getAttachment()).a(jD);
                            ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage, false);
                        }
                    }
                    if (iMMessage.getAttachment() instanceof s) {
                        com.qiyukf.unicorn.d.c.a(d.this.d(iMMessage.getSessionId()), iMMessage.getUuid());
                    }
                    if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.c) {
                        d.a(iMMessage.getSessionId(), (com.qiyukf.unicorn.h.a.d.c) iMMessage.getAttachment(), iMMessage.getTime(), iMMessage.getDirect());
                    }
                }
                com.qiyukf.unicorn.k.a.a(list2.get(0));
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$3 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass3 implements Observer<StatusCode> {
        public AnonymousClass3() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(StatusCode statusCode) {
            if (statusCode == StatusCode.LOGINED && com.qiyukf.unicorn.c.f().isMixSDK && com.qiyukf.unicorn.c.f().isPullMessageFromServer && System.currentTimeMillis() - com.qiyukf.unicorn.d.c.x() > 86400000) {
                com.qiyukf.unicorn.d.c.d(System.currentTimeMillis());
                v.a();
            }
        }
    }

    private static boolean p() {
        VideoService videoService = (VideoService) ServiceHelper.getService(VideoService.class);
        if (videoService != null) {
            return videoService.isSupportVideo(com.qiyukf.unicorn.c.d(), com.qiyukf.unicorn.c.e(), com.qiyukf.unicorn.d.c.g(), 0, com.qiyukf.unicorn.d.c.d());
        }
        return false;
    }

    public final void a(String str, com.qiyukf.unicorn.h.a.d.a aVar) {
        Context context;
        if (this.j) {
            TransferRequestEntry transferRequestEntry = new TransferRequestEntry();
            transferRequestEntry.setCode(aVar.b());
            this.k.handlerTransferRequestCallback(transferRequestEntry);
        }
        if (aVar.b() == 200 || aVar.b() == 203) {
            b(str, aVar);
            if (aVar.F()) {
                VideoService videoService = (VideoService) ServiceHelper.getService(VideoService.class);
                JSONObject jSONObject = new JSONObject();
                if (aVar.G()) {
                    com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_IS_CALL", aVar.G());
                    com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_STAFF_AVATAR", aVar.k());
                    com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_STAFF_NAME", aVar.d());
                    com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_USER_NAME", aVar.H());
                }
                com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_ROOM_ID", aVar.z());
                com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_SESSION_ID", aVar.f());
                if (aVar.x() != null) {
                    com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_CAMERA_SWITCH", aVar.x().a());
                    com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_MICROPHONE_SWITCH", aVar.x().b());
                    com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_RESOLUTION", aVar.x().c());
                }
                com.qiyukf.nimlib.n.j.a(jSONObject, "VIDEO_CALL_NUMBER", aVar.B());
                if (videoService != null && (context = this.E) != null) {
                    videoService.openAnswerAndDialing(context, jSONObject);
                }
            }
            VisitorForbiddenHelper.getInstance().setVisitorForbiddenConfig(aVar.y());
            return;
        }
        this.G.postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.k.d.4
            final /* synthetic */ String a;
            final /* synthetic */ com.qiyukf.unicorn.h.a.d.a b;

            public AnonymousClass4(String str2, com.qiyukf.unicorn.h.a.d.a aVar2) {
                str = str2;
                aVar = aVar2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                d.this.b(str, aVar);
            }
        }, 1000L);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$4 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass4 implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ com.qiyukf.unicorn.h.a.d.a b;

        public AnonymousClass4(String str2, com.qiyukf.unicorn.h.a.d.a aVar2) {
            str = str2;
            aVar = aVar2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            d.this.b(str, aVar);
        }
    }

    public void b(String str, com.qiyukf.unicorn.h.a.d.a aVar) {
        a aVar2;
        d(str, aVar);
        Runnable runnableRemove = this.c.remove(str);
        if (runnableRemove != null) {
            this.G.removeCallbacks(runnableRemove);
        }
        this.b.remove(str);
        int iB = aVar.b();
        if (iB == 201) {
            this.g.a(aVar.n());
            com.qiyukf.unicorn.d.c.t(aVar.c());
            this.f.a(aVar.c(), aVar.d(), aVar.k());
            if (com.qiyukf.unicorn.c.h().e != null && !TextUtils.isEmpty(com.qiyukf.unicorn.c.h().e.shopId)) {
                com.qiyukf.unicorn.d.c.d(com.qiyukf.unicorn.c.h().e.shopId, com.qiyukf.unicorn.c.h().e.shopId);
            }
        }
        this.O = aVar.A();
        boolean z = true;
        if (com.qiyukf.unicorn.m.a.a().c() && (aVar.i() != 1 || this.O)) {
            com.qiyukf.unicorn.h.a.f.z zVar = new com.qiyukf.unicorn.h.a.f.z();
            zVar.a(Long.valueOf(aVar.f() == 0 ? -1L : aVar.f()));
            zVar.a("Android");
            zVar.a();
            zVar.a(com.qiyukf.unicorn.c.f().templateId);
            zVar.b(com.qiyukf.unicorn.c.d().getPackageName());
            if ((iB == 203 && !aVar.q()) || iB == 201 || iB == 200) {
                c.a(zVar, str);
            }
        }
        if (iB == 200) {
            this.f.a(aVar.c(), aVar.d(), aVar.k());
            z zVar2 = new z(aVar.f());
            zVar2.d = aVar.c();
            zVar2.e = aVar.d();
            zVar2.f = aVar.e();
            zVar2.g = aVar.i();
            zVar2.h = aVar.j();
            zVar2.i = aVar.I();
            zVar2.b = aVar.l();
            zVar2.c = aVar.m();
            zVar2.j = aVar.k();
            this.b.put(str, zVar2);
            E(str);
            if (com.qiyukf.unicorn.c.i() != null) {
                com.qiyukf.unicorn.b bVarI = com.qiyukf.unicorn.c.i();
                SessionStatusEnum sessionStatusEnum = SessionStatusEnum.IN_SESSION;
                bVarI.b(str);
            }
            if (zVar2.g == 0) {
                com.qiyukf.unicorn.l.b.a().b();
            }
            this.t.put(str, aVar.o());
            if (aVar.D() && !aVar.F() && (aVar2 = this.m) != null) {
                aVar2.openInquiryForm(aVar.E());
            }
            RequestStaffCallback requestStaffCallback = this.L;
            if (requestStaffCallback != null) {
                requestStaffCallback.onSuccess();
            }
        } else if (iB == 203) {
            p pVar = new p(aVar.f(), aVar.g(), aVar.h(), aVar.p(), aVar.q(), aVar.r(), aVar.k());
            if (aVar.v() == 0) {
                this.l.put(str, pVar);
                a(str, pVar);
            }
            b(str, 10000L);
            if (!pVar.f) {
                this.f.a("CORP_AVATER_TAG", aVar.d(), aVar.k());
            }
            E(str);
            com.qiyukf.unicorn.b bVarI2 = com.qiyukf.unicorn.c.i();
            SessionStatusEnum sessionStatusEnum2 = SessionStatusEnum.IN_QUEUE;
            bVarI2.b(str);
            RequestStaffCallback requestStaffCallback2 = this.L;
            if (requestStaffCallback2 != null) {
                requestStaffCallback2.onFailed(203);
            }
        } else {
            com.qiyukf.unicorn.b bVarI3 = com.qiyukf.unicorn.c.i();
            SessionStatusEnum sessionStatusEnum3 = SessionStatusEnum.NONE;
            bVarI3.b(str);
            RequestStaffCallback requestStaffCallback3 = this.L;
            if (requestStaffCallback3 != null) {
                requestStaffCallback3.onFailed(aVar.b());
            }
        }
        this.L = null;
        c(str, aVar);
        if (iB == 200 || iB == 201 || iB == 205) {
            if (iB != 201 && iB != 205) {
                z = false;
            }
            b(str, z);
        }
    }

    private static void c(String str, com.qiyukf.unicorn.h.a.d.a aVar) {
        int iB = aVar.b();
        if (iB == 200 || iB == 203) {
            com.qiyukf.unicorn.h.a.f.o oVar = new com.qiyukf.unicorn.h.a.f.o();
            oVar.a(Long.valueOf(aVar.f()));
            c.a(oVar, str);
        }
    }

    public final void a(IMMessage iMMessage) {
        com.qiyukf.unicorn.f.y yVar;
        if (this.I == null || iMMessage == null || iMMessage.getAttachment() == null) {
            return;
        }
        String sessionId = iMMessage.getSessionId();
        if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.a) {
            com.qiyukf.unicorn.h.a.d.a aVar = (com.qiyukf.unicorn.h.a.d.a) iMMessage.getAttachment();
            if (this.I.f() == 8 && aVar.b() == 200 && this.I.m() != 0) {
                ArrayList<IMMessage> arrayListQueryMessageListEx = MsgDBHelper.queryMessageListEx((com.qiyukf.nimlib.session.d) MessageBuilder.createEmptyMessage(sessionId, SessionTypeEnum.Ysf, iMMessage.getTime()), QueryDirectionEnum.QUERY_OLD, 5, true);
                if (arrayListQueryMessageListEx.size() != 0) {
                    yVar = new com.qiyukf.unicorn.f.y();
                    int size = arrayListQueryMessageListEx.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        IMMessage iMMessage2 = arrayListQueryMessageListEx.get(size);
                        if ((iMMessage2.getAttachment() instanceof ac) || (iMMessage2.getAttachment() instanceof com.qiyukf.unicorn.h.a.a.a.o)) {
                            if (yVar.b() == null || yVar.b().size() == 0) {
                                ArrayList arrayList = new ArrayList();
                                if (iMMessage2.getAttachment() instanceof ac) {
                                    ac acVar = (ac) iMMessage2.getAttachment();
                                    List<com.qiyukf.unicorn.f.r> listI = acVar.i();
                                    String strH = acVar.h();
                                    if (listI != null) {
                                        for (com.qiyukf.unicorn.f.r rVar : listI) {
                                            if (!TextUtils.isEmpty(rVar.c) && rVar.c.contains("qiyu://action.qiyukf.com") && rVar.c.contains(CustomURLSpan.TRANSFER_ROBOT)) {
                                                arrayList.addAll(com.qiyukf.unicorn.n.a.a.a(rVar.c));
                                            }
                                        }
                                    }
                                    if (!TextUtils.isEmpty(strH) && strH.contains("qiyu://action.qiyukf.com") && strH.contains(CustomURLSpan.TRANSFER_ROBOT)) {
                                        arrayList.addAll(com.qiyukf.unicorn.n.a.a.a(strH));
                                    }
                                } else if (iMMessage2.getAttachment() instanceof com.qiyukf.unicorn.h.a.a.a.o) {
                                    for (o.a aVar2 : ((com.qiyukf.unicorn.h.a.a.a.o) iMMessage2.getAttachment()).c()) {
                                        if (aVar2.e() && !TextUtils.isEmpty(aVar2.f()) && aVar2.f().contains("qiyu://action.qiyukf.com") && aVar2.f().contains(CustomURLSpan.TRANSFER_ROBOT)) {
                                            arrayList.addAll(com.qiyukf.unicorn.n.a.a.a(aVar2.f()));
                                        }
                                    }
                                }
                                yVar.a(arrayList);
                            }
                        } else {
                            MsgDirectionEnum direct = iMMessage2.getDirect();
                            MsgDirectionEnum msgDirectionEnum = MsgDirectionEnum.Out;
                            if (direct == msgDirectionEnum && iMMessage2.getMsgType() == MsgTypeEnum.text) {
                                yVar.a(iMMessage2.getContent());
                                break;
                            } else if (iMMessage2.getDirect() == msgDirectionEnum && (iMMessage2.getAttachment() instanceof y)) {
                                yVar.a(((y) iMMessage2.getAttachment()).a());
                                break;
                            }
                        }
                        size--;
                    }
                } else {
                    yVar = null;
                }
                if (yVar != null && yVar.b() != null && !TextUtils.isEmpty(yVar.a()) && yVar.b().contains(String.valueOf(this.I.m()))) {
                    com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) MessageBuilder.createTextMessage(sessionId, SessionTypeEnum.Ysf, yVar.a());
                    dVar.setStatus(MsgStatusEnum.success);
                    dVar.b(iMMessage.getTime() + 1);
                    if (this.m != null) {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(dVar);
                        this.m.onSaveMsgToPage(sessionId, arrayList2);
                    }
                    ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVar, false);
                    com.qiyukf.unicorn.d.c.C("LAST_SESSION_ROBOT_QUESTION".concat(String.valueOf(sessionId)));
                }
            }
        }
        this.I = null;
    }

    private void d(String str, com.qiyukf.unicorn.h.a.d.a aVar) {
        com.qiyukf.unicorn.f.a aVar2 = this.z.get(str);
        if (aVar2 == null) {
            aVar2 = new com.qiyukf.unicorn.f.a();
        }
        aVar2.a(aVar.w() == 1);
        this.z.put(str, aVar2);
    }

    private static void E(String str) {
        if (com.qiyukf.unicorn.c.i().c(str) || !f.a().c()) {
            return;
        }
        com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) POPManager.queryLastMessage(str);
        if (dVar == null) {
            dVar = (com.qiyukf.nimlib.session.d) MessageBuilder.createEmptyMessage(str, SessionTypeEnum.Ysf, System.currentTimeMillis());
            dVar.setStatus(MsgStatusEnum.success);
            dVar.a(MsgTypeEnum.tip.getValue());
            dVar.setContent("");
        }
        com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.session.k.a(dVar));
    }

    private void a(String str, p pVar) {
        com.qiyukf.unicorn.h.a.d.m mVar = new com.qiyukf.unicorn.h.a.d.m();
        mVar.a(a(pVar));
        com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(str, SessionTypeEnum.Ysf, mVar);
        dVarA.setStatus(MsgStatusEnum.success);
        pVar.i = dVarA;
        ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA, true);
    }

    private void f(String str, boolean z) {
        p pVar = this.l.get(str);
        if (pVar == null) {
            return;
        }
        IMMessage iMMessage = pVar.i;
        if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.m) {
            com.qiyukf.unicorn.h.a.d.m mVar = (com.qiyukf.unicorn.h.a.d.m) iMMessage.getAttachment();
            mVar.a(a(pVar));
            mVar.a(z);
        }
        ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
    }

    private String a(p pVar) {
        if (pVar == null) {
            return "";
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (pVar.c || !TextUtils.isEmpty(pVar.d)) {
            spannableStringBuilder.append((CharSequence) pVar.d);
        } else {
            Context context = this.E;
            if (context != null) {
                spannableStringBuilder.append((CharSequence) context.getString(R.string.ysf_service_in_queue_hide_length));
            } else {
                spannableStringBuilder.append((CharSequence) "There are a lot of people in the queue, please be patient...");
            }
        }
        return spannableStringBuilder.toString();
    }

    private static IMMessage F(String str) {
        com.qiyukf.nimlib.session.d dVarQueryLatestMessage;
        if (!f.a().c()) {
            return null;
        }
        try {
            dVarQueryLatestMessage = MsgDBHelper.queryLatestMessage(str, SessionTypeEnum.Ysf.getValue());
        } catch (Exception unused) {
            dVarQueryLatestMessage = null;
        }
        if (dVarQueryLatestMessage == null || !(dVarQueryLatestMessage.getAttachment() instanceof ar)) {
            return null;
        }
        return dVarQueryLatestMessage;
    }

    public final t m(String str) {
        t tVar = this.q.get(str);
        return tVar == null ? t.a : tVar;
    }

    public final t n(String str) {
        Long l = this.s.get(str);
        if (l == null) {
            return null;
        }
        return this.r.get(l.longValue());
    }

    public final ai o(String str) {
        return this.D.get(str);
    }

    public final com.qiyukf.unicorn.f.b p(String str) {
        if (d(str) == 0) {
            return null;
        }
        return this.t.get(str);
    }

    private static long q() {
        return com.qiyukf.nimlib.n.p.b(com.qiyukf.unicorn.c.d()) ? 15000L : 3000L;
    }

    private void b(String str, long j) {
        p pVar = this.l.get(str);
        if (pVar == null) {
            return;
        }
        if (pVar.e == null) {
            pVar.e = new Runnable() { // from class: com.qiyukf.unicorn.k.d.5
                final /* synthetic */ String a;

                public AnonymousClass5(String str2) {
                    str = str2;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    com.qiyukf.unicorn.h.a.f.l lVar = new com.qiyukf.unicorn.h.a.f.l();
                    lVar.a(com.qiyukf.unicorn.d.c.d());
                    c.a(lVar, str);
                }
            };
        }
        this.G.removeCallbacks(pVar.e);
        this.G.postDelayed(pVar.e, j);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.k.d$5 */
    /* JADX INFO: compiled from: SessionManager.java */
    public class AnonymousClass5 implements Runnable {
        final /* synthetic */ String a;

        public AnonymousClass5(String str2) {
            str = str2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            com.qiyukf.unicorn.h.a.f.l lVar = new com.qiyukf.unicorn.h.a.f.l();
            lVar.a(com.qiyukf.unicorn.d.c.d());
            c.a(lVar, str);
        }
    }

    public final void b(String str, boolean z) {
        Runnable runnable;
        VideoService videoService = (VideoService) ServiceHelper.getService(VideoService.class);
        if (videoService != null) {
            videoService.receiveCmd(15, z ? "-1" : "1");
        }
        p pVarRemove = this.l.remove(str);
        if (pVarRemove == null || (runnable = pVarRemove.e) == null) {
            return;
        }
        this.G.removeCallbacks(runnable);
    }

    private static IMMessage a(long j, String str) {
        return MessageBuilder.createEmptyMessage(str, SessionTypeEnum.Ysf, j);
    }

    private void r() {
        if (this.H || this.b.size() != 0) {
            return;
        }
        this.l.size();
    }

    public final void a(ConsultSource consultSource) {
        this.e = consultSource;
    }

    public final ConsultSource i() {
        return this.e;
    }

    public final ProductDetail q(String str) {
        return this.n.get(str);
    }

    public final void a(String str, ProductDetail productDetail) {
        this.n.put(str, productDetail);
    }

    public final List<com.qiyukf.unicorn.h.a.e.a> r(String str) {
        return this.o.get(str);
    }

    public final void a(String str, List<com.qiyukf.unicorn.h.a.e.a> list) {
        this.o.put(str, list);
    }

    public final void s(String str) {
        if (this.o.get(str) == null) {
            this.o.put(str, new ArrayList());
        }
        if (this.o.get(str).size() <= 0 || this.o.get(str).get(0).b() != -1) {
            com.qiyukf.unicorn.h.a.e.a aVar = new com.qiyukf.unicorn.h.a.e.a();
            aVar.c();
            Context context = this.E;
            if (context != null) {
                aVar.a(context.getResources().getString(R.string.ysf_entry_request_staff));
            } else {
                aVar.a("Live Agent");
            }
            this.o.get(str).add(0, aVar);
        }
    }

    public final void a(String str, long j) {
        List<com.qiyukf.unicorn.h.a.e.a> list = this.o.get(str);
        if (list == null || list.size() == 0) {
            return;
        }
        for (com.qiyukf.unicorn.h.a.e.a aVar : list) {
            if (aVar.a() == j) {
                aVar.f();
            }
        }
    }

    public final boolean t(String str) {
        z zVar = this.b.get(str);
        return zVar != null && zVar.g == 1 && zVar.h == 1;
    }

    public final void a(String str, int i) {
        z zVar = this.b.get(str);
        if (zVar != null) {
            zVar.h = i;
        }
    }

    public final void c(String str, boolean z) {
        z zVar = this.b.get(str);
        if (zVar != null) {
            zVar.i = z;
        }
    }

    public final boolean u(String str) {
        z zVar = this.b.get(str);
        if (zVar != null) {
            return zVar.i;
        }
        return false;
    }

    public final void a(a aVar) {
        this.m = aVar;
    }

    public final void a(Context context) {
        this.E = context;
        this.N = context != null;
    }

    public final Context j() {
        return this.E;
    }

    public final a k() {
        return this.m;
    }

    public final x a(long j) {
        return this.B.get(j);
    }

    public final void a(long j, x xVar) {
        this.B.put(j, xVar);
    }

    /* JADX INFO: compiled from: SessionManager.java */
    public static class b implements Runnable {
        private String a;

        public /* synthetic */ b(String str, byte b) {
            this(str);
        }

        private b(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (TextUtils.isEmpty(com.qiyukf.unicorn.d.c.c()) || this.a.equals(com.qiyukf.unicorn.d.c.c())) {
                com.qiyukf.unicorn.h.a.d.a aVar = new com.qiyukf.unicorn.h.a.d.a();
                aVar.a(408);
                aVar.a(this.a);
                com.qiyukf.nimlib.i.b.a(com.qiyukf.nimlib.ysf.a.a(aVar, this.a));
            }
        }
    }

    public final void d(String str, boolean z) {
        this.C.put(str, Boolean.valueOf(z));
    }

    public final Boolean v(String str) {
        if (this.C.get(str) == null) {
            return Boolean.FALSE;
        }
        return this.C.get(str);
    }

    public final Boolean w(String str) {
        return Boolean.valueOf(this.u.get(str) == null ? false : this.u.get(str).booleanValue());
    }

    public final boolean x(String str) {
        if (this.v.get(str) == null) {
            return false;
        }
        return this.v.get(str).booleanValue();
    }

    public final boolean y(String str) {
        if (this.w.get(str) == null) {
            return false;
        }
        return this.w.get(str).booleanValue();
    }

    public final void z(String str) {
        this.w.put(str, Boolean.FALSE);
    }

    public final void a(boolean z) {
        this.x = z;
    }

    public final void a(Long l, List<g> list) {
        this.p.put(l, list);
    }

    public final List<g> a(Long l) {
        return this.p.get(l) == null ? new ArrayList() : this.p.get(l);
    }

    public final com.qiyukf.unicorn.f.a A(String str) {
        return this.z.get(str) == null ? new com.qiyukf.unicorn.f.a() : this.z.get(str);
    }

    public final boolean l() {
        return this.J;
    }

    public final void b(boolean z) {
        this.J = z;
    }

    public final void a(j jVar) {
        this.K = jVar;
    }

    public final j m() {
        return this.K;
    }

    public final void e(String str, boolean z) {
        this.y.put(str, Boolean.valueOf(z));
    }

    public final boolean B(String str) {
        return this.y.get(str) != null && this.y.get(str).booleanValue();
    }

    public final boolean n() {
        return this.O;
    }

    private void c(String str, long j) {
        long jLongValue = this.A.get(str) == null ? 0L : this.A.get(str).longValue();
        ArrayList<IMMessage> arrayList = new ArrayList();
        if (jLongValue == 0) {
            arrayList.addAll(MsgDBHelper.queryMessageListEx((com.qiyukf.nimlib.session.d) a(j, str), QueryDirectionEnum.QUERY_OLD, 20, true));
        } else if (jLongValue > j) {
            return;
        } else {
            arrayList.addAll(MsgDBHelper.queryMessageListEx((com.qiyukf.nimlib.session.d) a(jLongValue, str), jLongValue, j, true));
        }
        for (IMMessage iMMessage : arrayList) {
            if (iMMessage.getStatus() == MsgStatusEnum.unread && iMMessage.getTime() < j) {
                iMMessage.setStatus(MsgStatusEnum.read);
                ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage, true);
            }
        }
        this.A.put(str, Long.valueOf(j));
    }

    public final boolean b(IMMessage iMMessage) {
        if (com.qiyukf.unicorn.c.h().p(iMMessage.getSessionId()) == null || !"1".equals(com.qiyukf.unicorn.c.h().p(iMMessage.getSessionId()).a()) || iMMessage.getSessionId() == null || com.qiyukf.unicorn.c.h().g(iMMessage.getSessionId()) != 0) {
            return false;
        }
        return (iMMessage.getMsgType() == MsgTypeEnum.text || iMMessage.getMsgType() == MsgTypeEnum.image || iMMessage.getMsgType() == MsgTypeEnum.file || iMMessage.getMsgType() == MsgTypeEnum.video || iMMessage.getMsgType() == MsgTypeEnum.custom || iMMessage.getMsgType() == MsgTypeEnum.audio) && TextUtils.isEmpty(com.qiyukf.unicorn.n.u.a(this.E, iMMessage)) && iMMessage.getStatus() == MsgStatusEnum.success && iMMessage.getDirect() == MsgDirectionEnum.Out;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:320:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void a(com.qiyukf.unicorn.k.d r20, long r21, java.lang.String r23, java.lang.String r24) {
        /*
            Method dump skipped, instruction units count: 2396
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.k.d.a(com.qiyukf.unicorn.k.d, long, java.lang.String, java.lang.String):void");
    }

    public static /* synthetic */ void a(d dVar, com.qiyukf.unicorn.h.a.d.a aVar) {
        ConsultSource consultSource;
        String strSubstring;
        if (aVar.i() != 0 || (consultSource = dVar.e) == null || TextUtils.isEmpty(consultSource.vipStaffid) || TextUtils.isEmpty(dVar.e.VIPStaffAvatarUrl)) {
            return;
        }
        com.qiyukf.unicorn.f.x xVar = dVar.f;
        ConsultSource consultSource2 = dVar.e;
        String str = consultSource2.vipStaffid;
        if (TextUtils.isEmpty(consultSource2.vipStaffName)) {
            strSubstring = aVar.d();
        } else {
            strSubstring = dVar.e.vipStaffName.length() > 40 ? dVar.e.vipStaffName.substring(0, 40) : dVar.e.vipStaffName;
        }
        xVar.a(str, strSubstring, dVar.e.VIPStaffAvatarUrl);
    }

    public static /* synthetic */ void a(String str, com.qiyukf.unicorn.h.a.d.c cVar, long j, MsgDirectionEnum msgDirectionEnum) {
        int i = 0;
        int i2 = 0;
        while (i2 < cVar.j().size()) {
            c.e eVar = cVar.j().get(i2);
            if ("button".equals(eVar.a())) {
                int i3 = 0;
                while (i3 < eVar.c()) {
                    int i4 = i + 1;
                    com.qiyukf.unicorn.h.a.d.d dVar = new com.qiyukf.unicorn.h.a.d.d();
                    dVar.a(cVar.a());
                    dVar.b(cVar.b());
                    dVar.a(cVar.c());
                    dVar.b(eVar.c());
                    dVar.a(eVar.a());
                    dVar.a(eVar.b());
                    dVar.c(cVar.a(i2, eVar.c(), i3));
                    z zVar = com.qiyukf.unicorn.c.h().b.get(str);
                    com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(zVar == null ? str : zVar.d, str, SessionTypeEnum.Ysf, (String) null, dVar, j + ((long) i4));
                    dVarA.setDirect(msgDirectionEnum);
                    ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA, true);
                    i3++;
                    i2 = i2;
                    i = i4;
                }
            } else {
                int i5 = i + 1;
                com.qiyukf.unicorn.h.a.d.d dVar2 = new com.qiyukf.unicorn.h.a.d.d();
                dVar2.a(cVar.a());
                dVar2.b(cVar.b());
                dVar2.a(cVar.c());
                dVar2.b(eVar.c());
                dVar2.a(eVar.a());
                dVar2.a(eVar.b());
                dVar2.c(cVar.b(i2));
                z zVar2 = com.qiyukf.unicorn.c.h().b.get(str);
                com.qiyukf.nimlib.session.d dVarA2 = com.qiyukf.nimlib.ysf.a.a(zVar2 == null ? str : zVar2.d, str, SessionTypeEnum.Ysf, (String) null, dVar2, j + ((long) i5));
                dVarA2.setDirect(msgDirectionEnum);
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA2, true);
                i = i5;
            }
            i2++;
        }
        if (cVar.k() == 1) {
            int i6 = i + 1;
            com.qiyukf.unicorn.h.a.d.e eVar2 = new com.qiyukf.unicorn.h.a.d.e();
            eVar2.a(cVar.k());
            eVar2.a(cVar.l());
            eVar2.b(cVar.m());
            eVar2.b(cVar.n());
            eVar2.c(cVar.o());
            eVar2.d(cVar.d());
            eVar2.e(cVar.e());
            z zVar3 = com.qiyukf.unicorn.c.h().b.get(str);
            com.qiyukf.nimlib.session.d dVarA3 = com.qiyukf.nimlib.ysf.a.a(zVar3 == null ? str : zVar3.d, str, SessionTypeEnum.Ysf, (String) null, eVar2, j + ((long) i6));
            dVarA3.setDirect(msgDirectionEnum);
            ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(dVarA3, true);
        }
    }
}

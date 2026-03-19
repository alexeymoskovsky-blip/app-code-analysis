package com.qiyukf.nimlib.ipc;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.qiyukf.nimlib.apm.model.BaseEventExtension;
import com.qiyukf.nimlib.apm.model.BaseEventModel;
import com.qiyukf.nimlib.biz.k;
import com.qiyukf.nimlib.c;
import com.qiyukf.nimlib.h;
import com.qiyukf.nimlib.ipc.a.g;
import com.qiyukf.nimlib.plugin.interact.IMixPushInteract;
import com.qiyukf.nimlib.report.extension.i;
import com.qiyukf.nimlib.report.model.DualStackEventModel;
import com.qiyukf.nimlib.report.r;
import com.qiyukf.nimlib.report.s;
import com.qiyukf.nimlib.report.t;
import com.qiyukf.nimlib.report.u;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.service.NimService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: compiled from: LocalAgent.java */
/* JADX INFO: loaded from: classes6.dex */
public final class b {
    private Messenger a;
    private Messenger b;
    private a c;
    private IBinder d;
    private d e;
    private d f;
    private List<Message> g;

    public b(Context context) {
        if (!h.h()) {
            if (com.qiyukf.nimlib.log.b.a.a()) {
                Log.i(NIMClient.TAG, "LocalAgent only lives in main process");
            }
            com.qiyukf.nimlib.log.b.d("LocalAgent only lives in main process");
        } else {
            this.g = new ArrayList();
            HandlerThread handlerThread = new HandlerThread("LocalAgent");
            handlerThread.start();
            this.c = new a(handlerThread.getLooper());
            this.b = new Messenger(this.c);
        }
    }

    public final void a() {
        if (!h.h()) {
            com.qiyukf.nimlib.log.b.d("startCoreProcess can only in main process");
        } else if (!com.qiyukf.nimlib.c.v()) {
            e();
        } else {
            com.qiyukf.nimlib.log.c.b.a.H("reduced IM, delay start push process!");
        }
    }

    private void e() {
        if ((this.e == null || this.f == null) && NimService.a(com.qiyukf.nimlib.c.d(), 1)) {
            a(com.qiyukf.nimlib.c.d());
        }
    }

    public final void b() {
        if (this.a == null) {
            return;
        }
        a(11, (Parcelable) null);
    }

    public final void a(com.qiyukf.nimlib.ipc.a.a aVar) {
        boolean zA = a(2, aVar);
        com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", String.format("sendAppStatus isAppOnForeground:%s sent:%s", Boolean.valueOf(aVar.a()), Boolean.valueOf(zA)));
        if (aVar.a() && zA) {
            Message messageObtain = Message.obtain();
            messageObtain.what = 4;
            this.c.sendMessageDelayed(messageObtain, 5000L);
        }
    }

    public final void a(Boolean bool) {
        if (bool == null) {
            return;
        }
        com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", String.format("sendNetworkConnectStatus isConnected:%s sent:%s", bool, Boolean.valueOf(a(com.qiyukf.nimlib.ipc.a.a(28, bool)))));
    }

    public final void c() {
        if (k.a().c().c() <= 4) {
            a(18, (Parcelable) null);
        }
    }

    public final boolean a(int i, Parcelable parcelable) {
        return a(com.qiyukf.nimlib.ipc.a.a(i, parcelable));
    }

    private boolean a(Message message) {
        e();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= 3) {
                break;
            }
            try {
                com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", "sender = " + this.a);
                Messenger messenger = this.a;
                if (messenger == null) {
                    break;
                }
                messenger.send(message);
                z = true;
                break;
            } catch (DeadObjectException e) {
                e.printStackTrace();
                com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", "DeadObjectException when send", e);
                f();
            } catch (Exception e2) {
                com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", "Exception when send", e2);
                if (!f.a(e2)) {
                    a(false);
                    break;
                }
                i++;
            }
        }
        if (!z) {
            g();
            synchronized (this.g) {
                this.g.add(message);
            }
            d dVar = this.e;
            if (dVar != null) {
                dVar.b();
            }
        }
        return z;
    }

    private void a(Context context) {
        d dVar;
        if (com.qiyukf.nimlib.log.b.a.a()) {
            Log.i(NIMClient.TAG, "bindService context = ".concat(String.valueOf(context)));
        }
        if (context == null) {
            return;
        }
        this.e = new d(context, NimService.a(context), "main_conn") { // from class: com.qiyukf.nimlib.ipc.b.1
            @Override // com.qiyukf.nimlib.ipc.d
            public final void a(IBinder iBinder) {
                super.a(iBinder);
                b.a(b.this, iBinder);
                if (com.qiyukf.nimlib.c.K() || com.qiyukf.nimlib.c.v() || com.qiyukf.nimlib.c.J() || b.this.e == null) {
                    return;
                }
                b.this.e.c();
            }

            @Override // com.qiyukf.nimlib.ipc.d
            public final void a() {
                super.a();
                if ((com.qiyukf.nimlib.c.K() || com.qiyukf.nimlib.c.v() || com.qiyukf.nimlib.c.J()) && b.this.e != null) {
                    b.this.e.b();
                }
            }
        };
        this.f = new d(context, NimService.b(context), "aux_conn") { // from class: com.qiyukf.nimlib.ipc.b.2
            @Override // com.qiyukf.nimlib.ipc.d
            public final void a() {
                super.a();
                if (com.qiyukf.nimlib.c.K() || com.qiyukf.nimlib.c.v() || com.qiyukf.nimlib.c.J() || b.this.f == null) {
                    return;
                }
                b.this.f.b();
            }

            @Override // com.qiyukf.nimlib.ipc.d
            public final void a(IBinder iBinder) {
                super.a(iBinder);
                if (com.qiyukf.nimlib.c.K() || com.qiyukf.nimlib.c.v() || com.qiyukf.nimlib.c.J()) {
                    return;
                }
                if (b.this.d == null || b.this.a == null) {
                    com.qiyukf.nimlib.log.b.d("AuxService onConnected, reconnect NimService...");
                    if (b.this.e != null) {
                        b.this.e.b();
                    }
                }
            }
        };
        d dVar2 = this.e;
        if (dVar2 != null) {
            dVar2.b();
        }
        if (com.qiyukf.nimlib.c.K() || com.qiyukf.nimlib.c.v() || com.qiyukf.nimlib.c.J() || (dVar = this.f) == null) {
            return;
        }
        dVar.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void j() {
        com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", "binderDied");
        f();
        if (com.qiyukf.nimlib.c.K() || com.qiyukf.nimlib.c.v() || com.qiyukf.nimlib.c.J()) {
            com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", "binder won't connect, " + com.qiyukf.nimlib.c.K() + ", " + com.qiyukf.nimlib.c.v() + ", " + com.qiyukf.nimlib.c.J());
            return;
        }
        d dVar = this.e;
        if (dVar != null) {
            dVar.b();
        }
    }

    private void a(boolean z) {
        if (z && this.d != null) {
            this.a = new Messenger(this.d);
            i();
            h();
            return;
        }
        this.a = null;
    }

    private void f() {
        com.qiyukf.nimlib.log.b.d("!!! Push binder dead !!!");
        this.d = null;
        com.qiyukf.nimlib.ipc.b.a.a(com.qiyukf.nimlib.ipc.a.e.PUSH_BINDER_DEAD).a();
        a(false);
        com.qiyukf.nimlib.log.c.b.a.c();
    }

    private void g() {
        if (this.g == null) {
            this.g = new ArrayList();
        }
    }

    private void h() {
        ArrayList arrayList;
        g();
        synchronized (this.g) {
            try {
                if (this.g.size() > 0) {
                    arrayList = new ArrayList(this.g);
                    this.g.clear();
                } else {
                    arrayList = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                a((Message) it.next());
            }
        }
    }

    private void i() {
        Message messageObtain = Message.obtain((Handler) null, 1);
        messageObtain.replyTo = this.b;
        try {
            this.a.send(messageObtain);
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.b.d("ipc register exception : ".concat(String.valueOf(th)));
            a(false);
        }
    }

    /* JADX INFO: compiled from: LocalAgent.java */
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            try {
                i = message.what;
            } catch (Throwable th) {
                com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", "handle push message error.", th);
                return;
            }
            if (i == 2) {
                com.qiyukf.nimlib.c.c(((com.qiyukf.nimlib.ipc.a.a) com.qiyukf.nimlib.ipc.a.a(message)).b());
                return;
            }
            if (i == 3) {
                removeMessages(4);
                return;
            }
            if (i == 4) {
                com.qiyukf.nimlib.log.c.b.a.e("LocalAgent", String.format("LocalHandler handleMessage MSG_APP_STATUS_ACK_TIMEOUT main:%s sender:%s binder:%s", b.this.e, b.this.a, b.this.d));
                if (b.this.e != null) {
                    b.this.e.a(0);
                    return;
                } else {
                    b.this.d();
                    return;
                }
            }
            if (i != 19) {
                switch (i) {
                    case 14:
                        com.qiyukf.nimlib.ipc.a.f fVar = (com.qiyukf.nimlib.ipc.a.f) com.qiyukf.nimlib.ipc.a.a(message);
                        if (fVar != null) {
                            k.a().a(fVar);
                            return;
                        }
                        return;
                    case 15:
                        final g gVar = (g) com.qiyukf.nimlib.ipc.a.b(message);
                        if (com.qiyukf.nimlib.c.b()) {
                            k.a().a(gVar);
                            return;
                        } else {
                            com.qiyukf.nimlib.c.a(new c.a() { // from class: com.qiyukf.nimlib.ipc.b.a.1
                                @Override // com.qiyukf.nimlib.c.a
                                public final void a(boolean z) {
                                    if (z) {
                                        a.this.post(new Runnable() { // from class: com.qiyukf.nimlib.ipc.b.a.1.1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                k.a().a(gVar);
                                            }
                                        });
                                        com.qiyukf.nimlib.c.b(this);
                                    }
                                }
                            });
                            return;
                        }
                    case 16:
                        ArrayList arrayList = (ArrayList) com.qiyukf.nimlib.ipc.a.b(message);
                        k.a();
                        k.a((ArrayList<com.qiyukf.nimlib.biz.f>) arrayList);
                        return;
                    case 17:
                        com.qiyukf.nimlib.ipc.a.a(message);
                        com.qiyukf.nimlib.plugin.interact.b.a().a(IMixPushInteract.class);
                        return;
                    default:
                        switch (i) {
                            case 22:
                                BaseEventModel baseEventModel = (BaseEventModel) com.qiyukf.nimlib.ipc.a.a(message);
                                String eventKey = baseEventModel.getEventKey();
                                if (FirebaseAnalytics.Event.LOGIN.equals(eventKey)) {
                                    t.a().a((com.qiyukf.nimlib.report.model.f) baseEventModel);
                                    return;
                                }
                                if ("exceptions".equals(eventKey)) {
                                    com.qiyukf.nimlib.report.model.d dVar = (com.qiyukf.nimlib.report.model.d) baseEventModel;
                                    s.a();
                                    try {
                                        com.qiyukf.nimlib.apm.c.a(dVar.getEventKey(), (BaseEventModel<? extends BaseEventExtension>) dVar);
                                        return;
                                    } catch (Throwable th2) {
                                        com.qiyukf.nimlib.log.c.b.a.d("UIExceptionEventManager", "receivePushEvent Exception", th2);
                                        return;
                                    }
                                }
                                if ("nim_sdk_dual_stack_racing".equals(eventKey)) {
                                    DualStackEventModel dualStackEventModel = (DualStackEventModel) baseEventModel;
                                    com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", String.format("receiveEventString dualStackEventModel: %s", dualStackEventModel));
                                    com.qiyukf.nimlib.apm.c.a(eventKey, (BaseEventModel<? extends BaseEventExtension>) dualStackEventModel);
                                    return;
                                }
                                return;
                            case 23:
                                BaseEventExtension baseEventExtension = (BaseEventExtension) com.qiyukf.nimlib.ipc.a.a(message);
                                r.a();
                                if (baseEventExtension instanceof i) {
                                    t.a().a((i) baseEventExtension);
                                    return;
                                }
                                return;
                            case 24:
                                JSONObject jSONObject = new JSONObject((String) com.qiyukf.nimlib.ipc.a.b(message));
                                r.a();
                                String strOptString = jSONObject.optString("eventKey");
                                if ("exceptions".equals(strOptString)) {
                                    s.a();
                                    s.a(jSONObject.optJSONObject(NotificationCompat.CATEGORY_EVENT));
                                    return;
                                } else if ("nim_sdk_sync".equals(strOptString)) {
                                    u.a().a(jSONObject.optJSONObject(NotificationCompat.CATEGORY_EVENT));
                                    return;
                                } else {
                                    if ("nim_sdk_lbs_records".equals(strOptString)) {
                                        r.a(jSONObject.optJSONObject(NotificationCompat.CATEGORY_EVENT));
                                        return;
                                    }
                                    return;
                                }
                            case 25:
                                String str = (String) com.qiyukf.nimlib.ipc.a.b(message);
                                com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", String.format("handleNtpIpcEvent %s %s", Boolean.valueOf(com.qiyukf.nimlib.m.e.a(k.a().b(), str)), str));
                                return;
                            case 26:
                                JSONObject jSONObject2 = new JSONObject((String) com.qiyukf.nimlib.ipc.a.b(message));
                                if ("msgSend".equals(jSONObject2.optString("eventKey"))) {
                                    com.qiyukf.nimlib.report.g.a().a(jSONObject2);
                                    return;
                                }
                                return;
                            default:
                                super.handleMessage(message);
                                return;
                        }
                }
                com.qiyukf.nimlib.log.c.b.a.d("LocalAgent", "handle push message error.", th);
                return;
            }
            com.qiyukf.nimlib.e.d.e().a((String) com.qiyukf.nimlib.ipc.a.b(message));
        }
    }

    public final void d() {
        if (this.a == null || this.d == null) {
            d dVar = this.e;
            if (dVar == null || !dVar.d()) {
                com.qiyukf.nimlib.log.b.d("IPC has not established while awaking UI, start rebinding...");
                if (NimService.a(com.qiyukf.nimlib.c.d(), 1)) {
                    a(com.qiyukf.nimlib.c.d());
                }
            }
        }
    }

    public static /* synthetic */ void a(final b bVar, IBinder iBinder) {
        bVar.d = iBinder;
        try {
            iBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.qiyukf.nimlib.ipc.b$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    this.f$0.j();
                }
            }, 0);
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.f("LocalAgent", "binder linkToDeath exception ".concat(String.valueOf(th)));
        }
        bVar.a(true);
    }
}

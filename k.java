package com.qiyukf.nimlib.i;

import android.os.Handler;
import android.util.SparseArray;
import com.qiyukf.nimlib.biz.f.m;
import com.qiyukf.nimlib.biz.f.n;
import com.qiyukf.nimlib.biz.f.o;
import com.qiyukf.nimlib.biz.f.p;
import com.qiyukf.nimlib.n.z;
import com.qiyukf.nimlib.sdk.ai.NIMAIService;
import com.qiyukf.nimlib.sdk.auth.AuthService;
import com.qiyukf.nimlib.sdk.event.EventSubscribeService;
import com.qiyukf.nimlib.sdk.friend.FriendService;
import com.qiyukf.nimlib.sdk.generic.CustomizedAPIService;
import com.qiyukf.nimlib.sdk.misc.MiscService;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.SystemMessageService;
import com.qiyukf.nimlib.sdk.nos.NosService;
import com.qiyukf.nimlib.sdk.passthrough.PassthroughService;
import com.qiyukf.nimlib.sdk.redpacket.RedPacketService;
import com.qiyukf.nimlib.sdk.robot.RobotService;
import com.qiyukf.nimlib.sdk.settings.SettingsService;
import com.qiyukf.nimlib.sdk.team.TeamService;
import com.qiyukf.nimlib.sdk.test.MockTestService;
import com.qiyukf.nimlib.sdk.uinfo.UserService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: TransactionExecutor.java */
/* JADX INFO: loaded from: classes6.dex */
final class k {
    private final Map<String, a> a = new HashMap();
    private final SparseArray<g> b = new SparseArray<>();
    private final Handler c = com.qiyukf.nimlib.d.b.a.c().a("bk_executor");
    private final Handler d = com.qiyukf.nimlib.d.b.a.c().a("bk_executor_high");

    /* JADX INFO: compiled from: TransactionExecutor.java */
    public static class a {
        private final Map<String, Method> a = new HashMap();
        private i b;

        public a(Class<?> cls, Class<? extends i> cls2) {
            for (Method method : cls.getDeclaredMethods()) {
                this.a.put(a(method), method);
            }
            try {
                this.b = cls2.newInstance();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        private static String a(Method method) {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName());
            for (Class<?> cls : method.getParameterTypes()) {
                sb.append("_");
                sb.append(cls.getSimpleName());
            }
            return sb.toString();
        }

        public final Object a(j jVar) throws Throwable {
            return this.a.get(a(jVar.c())).invoke(this.b, jVar.g());
        }
    }

    public k() {
        a(AuthService.class, com.qiyukf.nimlib.biz.f.a.class);
        a(MsgService.class, com.qiyukf.nimlib.biz.f.g.class);
        a(TeamService.class, o.class);
        a(SystemMessageService.class, n.class);
        a(UserService.class, p.class);
        a(FriendService.class, com.qiyukf.nimlib.biz.f.d.class);
        a(NosService.class, com.qiyukf.nimlib.biz.f.i.class);
        a(SettingsService.class, m.class);
        a(EventSubscribeService.class, com.qiyukf.nimlib.biz.f.c.class);
        a(RobotService.class, com.qiyukf.nimlib.biz.f.l.class);
        a(RedPacketService.class, com.qiyukf.nimlib.biz.f.k.class);
        a(MockTestService.class, com.qiyukf.nimlib.biz.f.f.class);
        a(MiscService.class, com.qiyukf.nimlib.biz.f.e.class);
        a(PassthroughService.class, com.qiyukf.nimlib.biz.f.j.class);
        a(CustomizedAPIService.class, com.qiyukf.nimlib.biz.f.b.class);
        a(NIMAIService.class, com.qiyukf.nimlib.biz.f.h.class);
        for (Map.Entry<Class<?>, Class<? extends i>> entry : com.qiyukf.nimlib.plugin.b.a().d().entrySet()) {
            a(entry.getKey(), entry.getValue());
        }
        com.qiyukf.nimlib.log.c.b.a.d("TransExec", "register service completed, total size=" + this.a.size());
    }

    public final Object a(j jVar) {
        a aVar = this.a.get(jVar.e());
        if (aVar == null) {
            return null;
        }
        i.a(jVar);
        try {
            com.qiyukf.nimlib.log.c.b.a.d("TransExec", "execute ".concat(String.valueOf(jVar)));
            long jNanoTime = System.nanoTime();
            Object objA = aVar.a(jVar);
            z.a(jNanoTime, 2147483647L, new z.a() { // from class: com.qiyukf.nimlib.i.k.1
                final /* synthetic */ j a;

                public AnonymousClass1(j jVar2) {
                    jVar = jVar2;
                }

                @Override // com.qiyukf.nimlib.n.z.a
                public final void a(long j) {
                    com.qiyukf.nimlib.log.c.b.a.d("TransExec", "execute(cost=" + j + ") " + jVar);
                }
            });
            i.a();
            return objA;
        } catch (Throwable th) {
            th = th;
            try {
                if ((th instanceof InvocationTargetException) && th.getCause() != null) {
                    th = th.getCause();
                }
                com.qiyukf.nimlib.log.c.b.a.b("TransExec", "execute " + jVar2 + " exception", th);
                com.qiyukf.nimlib.i.a.c(jVar2.a(th));
                i.a();
                return null;
            } catch (Throwable th2) {
                i.a();
                throw th2;
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.i.k$1 */
    /* JADX INFO: compiled from: TransactionExecutor.java */
    public class AnonymousClass1 implements z.a {
        final /* synthetic */ j a;

        public AnonymousClass1(j jVar2) {
            jVar = jVar2;
        }

        @Override // com.qiyukf.nimlib.n.z.a
        public final void a(long j) {
            com.qiyukf.nimlib.log.c.b.a.d("TransExec", "execute(cost=" + j + ") " + jVar);
        }
    }

    public final Handler b(j jVar) {
        return jVar.m() > 0 ? this.d : this.c;
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.i.k$2 */
    /* JADX INFO: compiled from: TransactionExecutor.java */
    public class AnonymousClass2 implements Runnable {
        final /* synthetic */ j a;

        public AnonymousClass2(j jVar) {
            jVar = jVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Object objA = k.this.a(jVar);
            if (jVar.l()) {
                com.qiyukf.nimlib.report.a.a().c(jVar);
            }
            if (objA instanceof g) {
                synchronized (k.this.b) {
                    k.this.b.put(jVar.h(), (g) objA);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.i.k$3 */
    /* JADX INFO: compiled from: TransactionExecutor.java */
    public class AnonymousClass3 implements z.a {
        final /* synthetic */ j a;

        public AnonymousClass3(j jVar) {
            jVar = jVar;
        }

        @Override // com.qiyukf.nimlib.n.z.a
        public final void a(long j) {
            com.qiyukf.nimlib.log.c.b.a.d("TransExec", "execute(elapse=" + j + ") " + jVar);
        }
    }

    public final void c(j jVar) {
        g gVar;
        com.qiyukf.nimlib.log.c.b.a.d("TransExec", "abort ".concat(String.valueOf(jVar)));
        synchronized (this.b) {
            gVar = this.b.get(jVar.h());
            this.b.remove(jVar.h());
        }
        if (gVar != null) {
            gVar.abort();
        }
        com.qiyukf.nimlib.report.a.a().b(jVar);
    }

    public final void d(j jVar) {
        synchronized (this.b) {
            this.b.remove(jVar.h());
        }
    }

    private void a(Class<?> cls, Class<? extends i> cls2) {
        this.a.put(cls.getSimpleName(), new a(cls, cls2));
    }
}

package com.qiyukf.nimlib.l;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import com.qiyukf.nimlib.sdk.NotificationFoldStyle;
import com.qiyukf.nimlib.sdk.StatusBarNotificationConfig;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.SessionAckInfo;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: NotificationShower.java */
/* JADX INFO: loaded from: classes6.dex */
final class f {
    private g a;
    private NotificationFoldStyle b;
    private NotificationManager d;
    private Bitmap f;
    private long e = 0;
    private Context c = com.qiyukf.nimlib.c.d();

    public f() {
        StatusBarNotificationConfig statusBarNotificationConfig = com.qiyukf.nimlib.c.h().statusBarNotificationConfig;
        if (statusBarNotificationConfig != null) {
            if (statusBarNotificationConfig.notificationFoldStyle == null) {
                statusBarNotificationConfig.notificationFoldStyle = NotificationFoldStyle.ALL;
            }
            this.b = statusBarNotificationConfig.notificationFoldStyle;
            b();
        }
        this.d = (NotificationManager) this.c.getSystemService("notification");
        e.e(this.c);
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.l.f$1 */
    /* JADX INFO: compiled from: NotificationShower.java */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[NotificationFoldStyle.values().length];
            a = iArr;
            try {
                iArr[NotificationFoldStyle.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[NotificationFoldStyle.EXPAND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[NotificationFoldStyle.CONTACT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void b() {
        int i = AnonymousClass1.a[this.b.ordinal()];
        if (i == 1) {
            this.a = new c(this.c);
        } else if (i == 2) {
            this.a = new j(this.c);
        } else {
            if (i != 3) {
                return;
            }
            this.a = new b(this.c);
        }
    }

    public final void a(NotificationFoldStyle notificationFoldStyle) {
        NotificationFoldStyle notificationFoldStyle2 = this.b;
        if (notificationFoldStyle2 == null && notificationFoldStyle == null) {
            notificationFoldStyle = NotificationFoldStyle.ALL;
        } else {
            if (notificationFoldStyle == notificationFoldStyle2) {
                return;
            }
            if (notificationFoldStyle == null) {
                notificationFoldStyle = NotificationFoldStyle.ALL;
            }
        }
        com.qiyukf.nimlib.c.h().statusBarNotificationConfig.notificationFoldStyle = notificationFoldStyle;
        this.b = notificationFoldStyle;
        a();
        b();
    }

    /* JADX WARN: Removed duplicated region for block: B:232:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0202  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(@androidx.annotation.NonNull com.qiyukf.nimlib.sdk.msg.model.IMMessage r21, java.util.Map<java.lang.String, com.qiyukf.nimlib.sdk.msg.model.IMMessage> r22, java.lang.String r23, int r24, boolean r25) {
        /*
            Method dump skipped, instruction units count: 920
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.l.f.a(com.qiyukf.nimlib.sdk.msg.model.IMMessage, java.util.Map, java.lang.String, int, boolean):void");
    }

    public final void a() {
        g gVar = this.a;
        if (gVar != null) {
            gVar.a(this.d, new i(0));
        }
    }

    public final void a(List<SessionAckInfo> list) {
        i iVar = new i(2);
        iVar.a(list);
        g gVar = this.a;
        if (gVar != null) {
            gVar.a(this.d, iVar);
        }
    }

    private PendingIntent a(Map<String, IMMessage> map) {
        try {
            return this.a.a(map);
        } catch (Throwable unused) {
            return null;
        }
    }
}

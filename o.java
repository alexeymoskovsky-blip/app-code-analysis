package com.qiyukf.nimlib.biz.c.j;

import android.text.TextUtils;
import android.util.Pair;
import com.qiyukf.nimlib.biz.e.k.af;
import com.qiyukf.nimlib.biz.e.k.ag;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.RevokeType;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.RevokeMsgNotification;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.session.u;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: SessionServiceResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class o extends com.qiyukf.nimlib.biz.c.i {
    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        RevokeMsgNotification revokeMsgNotificationA;
        String str;
        RevokeMsgNotification revokeMsgNotificationA2;
        String str2;
        if (!aVar.e()) {
            com.qiyukf.nimlib.biz.c.i.a(aVar, null);
            return;
        }
        String strC = "";
        if (aVar instanceof com.qiyukf.nimlib.biz.e.k.l) {
            com.qiyukf.nimlib.biz.e.k.l lVar = (com.qiyukf.nimlib.biz.e.k.l) aVar;
            boolean zEquals = "1".equals(lVar.j().c(5));
            ArrayList<com.qiyukf.nimlib.push.packet.b.c> arrayListK = lVar.k();
            ArrayList arrayList = new ArrayList(arrayListK.size());
            for (com.qiyukf.nimlib.push.packet.b.c cVar : arrayListK) {
                String strC2 = cVar.c(1);
                long j = Long.parseLong(cVar.c(2));
                String strC3 = cVar.c(3);
                int iD = cVar.d(5);
                if (iD == 0) {
                    strC = cVar.c(4);
                } else if (iD == 1) {
                    revokeMsgNotificationA = a(a(strC2), cVar.c(4));
                }
                arrayList.add(new com.qiyukf.nimlib.session.t(strC2, j, strC3, strC, iD, revokeMsgNotificationA));
            }
            com.qiyukf.nimlib.biz.c.i.a(lVar, new u(zEquals, arrayList));
            return;
        }
        if (aVar instanceof com.qiyukf.nimlib.biz.e.k.m) {
            com.qiyukf.nimlib.biz.e.k.m mVar = (com.qiyukf.nimlib.biz.e.k.m) aVar;
            String strJ = mVar.j();
            long jK = mVar.k();
            String strL = mVar.l();
            String strM = mVar.m();
            int iN = mVar.n();
            if (iN == 0) {
                str2 = strM;
                revokeMsgNotificationA2 = null;
            } else {
                revokeMsgNotificationA2 = iN == 1 ? a(a(strJ), strM) : null;
                str2 = "";
            }
            com.qiyukf.nimlib.biz.c.i.a(mVar, new com.qiyukf.nimlib.session.t(strJ, jK, strL, str2, iN, revokeMsgNotificationA2));
            return;
        }
        if (aVar instanceof ag) {
            ag agVar = (ag) aVar;
            ((com.qiyukf.nimlib.i.j) com.qiyukf.nimlib.biz.k.a().a(agVar).e()).a(agVar.h()).b();
            return;
        }
        if (aVar instanceof com.qiyukf.nimlib.biz.e.k.i) {
            com.qiyukf.nimlib.biz.e.k.i iVar = (com.qiyukf.nimlib.biz.e.k.i) aVar;
            ((com.qiyukf.nimlib.i.j) com.qiyukf.nimlib.biz.k.a().a(iVar).e()).a(iVar.h()).b();
            return;
        }
        if (aVar instanceof af) {
            af afVar = (af) aVar;
            String strJ2 = afVar.j();
            long jK2 = afVar.k();
            String strL2 = afVar.l();
            String strM2 = afVar.m();
            int iN2 = afVar.n();
            if (iN2 == 0) {
                str = strM2;
                revokeMsgNotificationA = null;
            } else {
                revokeMsgNotificationA = iN2 == 1 ? a(a(strJ2), strM2) : null;
                str = "";
            }
            com.qiyukf.nimlib.i.b.a(new com.qiyukf.nimlib.session.t(strJ2, jK2, strL2, str, iN2, revokeMsgNotificationA));
        }
    }

    private static RevokeMsgNotification a(Pair<SessionTypeEnum, String> pair, String str) {
        com.qiyukf.nimlib.session.d dVar;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String strOptString = jSONObject.optString(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
            if (TextUtils.isEmpty(strOptString) || (dVar = (com.qiyukf.nimlib.session.d) MsgDBHelper.queryMessageByUuid(strOptString)) == null) {
                dVar = (com.qiyukf.nimlib.session.d) MessageBuilder.createEmptyMessage((String) pair.second, (SessionTypeEnum) pair.first, jSONObject.optLong(Constants.VIA_REPORT_TYPE_MAKE_FRIEND));
                dVar.setFromAccount(jSONObject.optString("3"));
                dVar.setContent(jSONObject.optString(Constants.VIA_TO_TYPE_QZONE));
                dVar.k(jSONObject.optString(Constants.VIA_SHARE_TYPE_MINI_PROGRAM));
                dVar.a(strOptString);
                dVar.c(jSONObject.optLong(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE));
            }
            return new RevokeMsgNotification(dVar, "", jSONObject.optString(Constants.VIA_REPORT_TYPE_START_WAP), jSONObject.optString(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO), 0, RevokeType.typeOfValue(jSONObject.optInt("1")), "");
        } catch (JSONException unused) {
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.util.Pair<com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum, java.lang.String> a(java.lang.String r7) {
        /*
            r0 = 1
            r1 = 0
            r2 = 2
            boolean r3 = android.text.TextUtils.isEmpty(r7)
            java.lang.String r4 = ""
            r5 = 0
            if (r3 == 0) goto L12
            android.util.Pair r7 = new android.util.Pair
            r7.<init>(r5, r4)
            return r7
        L12:
            java.lang.String r3 = "\\|"
            java.lang.String[] r7 = r7.split(r3)
            int r3 = r7.length
            if (r3 >= r2) goto L21
            android.util.Pair r7 = new android.util.Pair
            r7.<init>(r5, r4)
            return r7
        L21:
            r3 = r7[r1]
            r3.hashCode()
            r4 = -1
            int r6 = r3.hashCode()
            switch(r6) {
                case -1718157151: goto L48;
                case 109294: goto L3c;
                case 3555933: goto L30;
                default: goto L2e;
            }
        L2e:
            r1 = -1
            goto L52
        L30:
            java.lang.String r1 = "team"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L3a
            goto L2e
        L3a:
            r1 = 2
            goto L52
        L3c:
            java.lang.String r1 = "p2p"
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L46
            goto L2e
        L46:
            r1 = 1
            goto L52
        L48:
            java.lang.String r2 = "super_team"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L52
            goto L2e
        L52:
            switch(r1) {
                case 0: goto L5c;
                case 1: goto L59;
                case 2: goto L56;
                default: goto L55;
            }
        L55:
            goto L5e
        L56:
            com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum r5 = com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum.Team
            goto L5e
        L59:
            com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum r5 = com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum.P2P
            goto L5e
        L5c:
            com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum r5 = com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum.SUPER_TEAM
        L5e:
            android.util.Pair r1 = new android.util.Pair
            r7 = r7[r0]
            r1.<init>(r5, r7)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.biz.c.j.o.a(java.lang.String):android.util.Pair");
    }
}

package com.qiyukf.nimlib.biz.c.l;

import android.text.TextUtils;
import com.qiyukf.nimlib.sdk.msg.constant.NotificationType;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamMemberType;
import com.qiyukf.nimlib.sdk.team.model.MemberChangeAttachment;
import com.qiyukf.nimlib.team.TeamDBHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: compiled from: TeamTalkNotifyHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class l extends com.qiyukf.nimlib.biz.c.i {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0204  */
    @Override // com.qiyukf.nimlib.biz.c.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(com.qiyukf.nimlib.biz.e.a r18) {
        /*
            Method dump skipped, instruction units count: 886
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.biz.c.l.l.a(com.qiyukf.nimlib.biz.e.a):void");
    }

    private static String a(List<com.qiyukf.nimlib.push.packet.b.c> list) {
        Iterator<com.qiyukf.nimlib.push.packet.b.c> it = list.iterator();
        String strC = null;
        while (it.hasNext()) {
            strC = it.next().c(6);
            if (!TextUtils.isEmpty(strC)) {
                break;
            }
        }
        return strC;
    }

    /* JADX INFO: renamed from: com.qiyukf.nimlib.biz.c.l.l$1, reason: invalid class name */
    /* JADX INFO: compiled from: TeamTalkNotifyHandler.java */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[NotificationType.values().length];
            a = iArr;
            try {
                iArr[NotificationType.InviteMember.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[NotificationType.PassTeamApply.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[NotificationType.KickMember.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[NotificationType.DismissTeam.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[NotificationType.UpdateTeam.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[NotificationType.LeaveTeam.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[NotificationType.TransferOwner.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[NotificationType.AcceptInvite.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[NotificationType.AddTeamManager.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[NotificationType.RemoveTeamManager.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    private static void a(com.qiyukf.nimlib.session.d dVar) {
        try {
            com.qiyukf.nimlib.team.d dVarA = com.qiyukf.nimlib.team.d.a(com.qiyukf.nimlib.team.c.b(new JSONObject(dVar.a(false)).getJSONObject("data").getJSONObject("tinfo")));
            dVarA.f(1);
            dVarA.b(dVar.getTime());
            com.qiyukf.nimlib.team.c.a(dVarA);
        } catch (Exception e) {
            com.qiyukf.nimlib.log.c.b.a.d("team", "save team info by notify error: " + e.getMessage());
        }
    }

    private static boolean b(com.qiyukf.nimlib.session.d dVar) {
        String sessionId = dVar.getSessionId();
        MemberChangeAttachment memberChangeAttachment = (MemberChangeAttachment) dVar.getAttachment();
        Iterator<String> it = memberChangeAttachment.getTargets().iterator();
        while (it.hasNext()) {
            if (it.next().equals(com.qiyukf.nimlib.c.q())) {
                com.qiyukf.nimlib.team.c.a(sessionId, false, false);
                com.qiyukf.nimlib.session.b.c.a().c(sessionId, SessionTypeEnum.Team);
                return true;
            }
        }
        com.qiyukf.nimlib.team.d dVarQueryTeam = TeamDBHelper.queryTeam(sessionId);
        if (dVarQueryTeam != null) {
            dVarQueryTeam.d(dVarQueryTeam.getMemberCount() - memberChangeAttachment.getTargets().size());
            dVarQueryTeam.b(dVar.getTime());
            com.qiyukf.nimlib.team.c.a(dVarQueryTeam);
        }
        return false;
    }

    private static void c(com.qiyukf.nimlib.session.d dVar) {
        try {
            com.qiyukf.nimlib.team.c.a(dVar.getSessionId(), com.qiyukf.nimlib.team.c.b(new JSONObject(dVar.a(false)).getJSONObject("data").getJSONObject("tinfo")));
        } catch (Exception e) {
            com.qiyukf.nimlib.log.c.b.a.d("team", "update team info by notify error: " + e.getMessage());
        }
    }

    private static void a(String str, long j, MemberChangeAttachment memberChangeAttachment, String str2) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = memberChangeAttachment.getTargets().iterator();
        while (it.hasNext()) {
            arrayList.add(a(str, j, it.next(), str2));
        }
        com.qiyukf.nimlib.team.c.a((ArrayList<com.qiyukf.nimlib.team.g>) arrayList);
    }

    private static com.qiyukf.nimlib.team.g a(String str, long j, String str2, String str3) {
        com.qiyukf.nimlib.team.g gVar = new com.qiyukf.nimlib.team.g();
        gVar.a(str);
        gVar.b(str2);
        gVar.a(TeamMemberType.Normal);
        gVar.b(1);
        gVar.b(j);
        gVar.c(j);
        gVar.d(str3);
        if (str2.equals(com.qiyukf.nimlib.c.q())) {
            com.qiyukf.nimlib.biz.d.k.h hVar = new com.qiyukf.nimlib.biz.d.k.h();
            hVar.a(str);
            hVar.a(com.qiyukf.nimlib.biz.n.c(str));
            com.qiyukf.nimlib.biz.k.a().a(hVar);
        }
        return gVar;
    }

    private static void a(String str, long j, String str2, TeamMemberType teamMemberType) {
        com.qiyukf.nimlib.team.g gVarQueryTeamMember = TeamDBHelper.queryTeamMember(str, str2);
        if (gVarQueryTeamMember != null) {
            gVarQueryTeamMember.a(teamMemberType);
            gVarQueryTeamMember.c(j);
            com.qiyukf.nimlib.team.c.a(gVarQueryTeamMember);
        }
    }

    private static void a(String str, long j, List<String> list, TeamMemberType teamMemberType) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            com.qiyukf.nimlib.team.g gVarQueryTeamMember = TeamDBHelper.queryTeamMember(str, it.next());
            if (gVarQueryTeamMember != null) {
                gVarQueryTeamMember.a(teamMemberType);
                gVarQueryTeamMember.c(j);
                arrayList.add(gVarQueryTeamMember);
            }
        }
        com.qiyukf.nimlib.team.c.a((ArrayList<com.qiyukf.nimlib.team.g>) arrayList);
    }
}

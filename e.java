package com.qiyukf.nimlib.push.a.a;

import android.text.TextUtils;
import android.util.Pair;
import com.qiyukf.nimlib.biz.k;
import com.qiyukf.nimlib.h;
import com.qiyukf.nimlib.plugin.interact.IMixPushInteract;
import com.qiyukf.nimlib.push.a.c.g;
import com.qiyukf.nimlib.push.f;
import com.qiyukf.nimlib.report.n;
import com.qiyukf.nimlib.report.u;
import com.qiyukf.nimlib.sdk.auth.constant.LoginSyncStatus;
import com.qiyukf.nimlib.sdk.superteam.SuperTeam;
import com.qiyukf.nimlib.sdk.team.model.Team;
import com.qiyukf.nimlib.superteam.SuperTeamDBHelper;
import com.qiyukf.nimlib.team.TeamDBHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: compiled from: SyncResponseHandler.java */
/* JADX INFO: loaded from: classes6.dex */
public final class e extends com.qiyukf.nimlib.biz.c.a {
    private final boolean a;

    public e(boolean z) {
        this.a = z;
    }

    @Override // com.qiyukf.nimlib.biz.c.a
    public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
        boolean zE = aVar.e();
        short sH = aVar.h();
        try {
            if (h.h()) {
                u.a().a(zE, sH);
            } else {
                n.a().a(zE, sH);
            }
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("SyncEventManager", "stopTrackEvent51 exception, isSuccess = " + zE + ", code = " + ((int) sH), th);
        }
        if (this.a) {
            f.o();
            f.l();
            if (!aVar.e()) {
                com.qiyukf.nimlib.log.c.b.a.I("SDK login sync data failed, disconnect link! code=" + ((int) aVar.h()));
                f.o().k();
                return;
            }
            com.qiyukf.nimlib.log.c.b.a.I("SDK login sync data succeed");
            return;
        }
        com.qiyukf.nimlib.plugin.interact.b.a().a(IMixPushInteract.class);
        long j = ((g) aVar).j();
        if (j == 0) {
            com.qiyukf.nimlib.log.c.b.a.H("this is fake sync response in ui process");
            return;
        }
        com.qiyukf.nimlib.c.c(false);
        if (aVar.e()) {
            com.qiyukf.nimlib.biz.n.k(j);
            com.qiyukf.nimlib.session.b.c.a().a(j);
        }
        com.qiyukf.nimlib.log.c.b.a.H("SDK login sync data completed");
        com.qiyukf.nimlib.c.B();
        com.qiyukf.nimlib.i.b.a(LoginSyncStatus.SYNC_COMPLETED);
        b();
        a();
    }

    private void a() {
        if (!com.qiyukf.nimlib.c.P()) {
            com.qiyukf.nimlib.log.c.b.a.H("sync superTeam member disable");
            return;
        }
        ArrayList<SuperTeam> arrayListQueryAllSuperTeams = SuperTeamDBHelper.queryAllSuperTeams();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (TextUtils.isEmpty(com.qiyukf.nimlib.c.q())) {
            return;
        }
        for (SuperTeam superTeam : arrayListQueryAllSuperTeams) {
            long jE = com.qiyukf.nimlib.biz.n.e(superTeam.getId());
            if (jE == 0) {
                arrayList3.add(superTeam.getId());
            }
            if (jE == 0 || ((com.qiyukf.nimlib.superteam.b) superTeam).d() > jE) {
                if (superTeam.getMemberLimit() > 2000) {
                    arrayList.add(new Pair(superTeam.getId(), Long.valueOf(jE)));
                } else {
                    arrayList2.add(new Pair(superTeam.getId(), Long.valueOf(jE)));
                }
            }
        }
        if (arrayList3.size() > 0 && SuperTeamDBHelper.clearTeamMembers(false, arrayList3) < 0) {
            com.qiyukf.nimlib.log.c.b.a.H("clear super team member dirty data failed");
            com.qiyukf.nimlib.i.b.b(false);
            return;
        }
        com.qiyukf.nimlib.log.c.b.a.H("clear super team member dirty data, size =" + arrayList3.size() + " , data = " + arrayList3.toString());
        List<Pair<String, Long>> listA = a(arrayList, arrayList2);
        if (!listA.isEmpty()) {
            a(listA, 0, 500);
            com.qiyukf.nimlib.log.c.b.a.H("sync super team member info , request amount = " + listA.size() + " , data = " + listA.toString());
            return;
        }
        com.qiyukf.nimlib.i.b.b(true);
        com.qiyukf.nimlib.log.c.b.a.H("no need to sync super team member info ");
    }

    private static List<Pair<String, Long>> a(ArrayList<Pair<String, Long>> arrayList, ArrayList<Pair<String, Long>> arrayList2) {
        int size = arrayList.size();
        int size2 = arrayList2.size();
        ArrayList arrayList3 = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= size && i2 >= size2) {
                return arrayList3;
            }
            int iMin = Math.min(size - i, 10);
            if (i < size) {
                int i3 = i + iMin;
                arrayList3.addAll(arrayList.subList(i, i3));
                i = i3;
            }
            int iMin2 = Math.min(size2 - i2, 500 - iMin);
            if (i2 < size2) {
                int i4 = i2 + iMin2;
                arrayList3.addAll(arrayList2.subList(i2, i4));
                i2 = i4;
            }
            int i5 = (500 - iMin2) - iMin;
            for (int i6 = 0; i6 < i5; i6++) {
                arrayList3.add(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final List<Pair<String, Long>> list, final int i, final int i2) {
        int size;
        if (list == null || i >= (size = list.size()) || i < 0 || i2 <= 0) {
            return;
        }
        final int iMin = Math.min(size, i + i2);
        List<Pair<String, Long>> listSubList = list.subList(i, iMin);
        int iIndexOf = listSubList.indexOf(null);
        if (iIndexOf != -1) {
            listSubList = listSubList.subList(0, iIndexOf);
        }
        k.a().a(new com.qiyukf.nimlib.biz.g.b(new com.qiyukf.nimlib.biz.d.h.b(listSubList)) { // from class: com.qiyukf.nimlib.push.a.a.e.1
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                com.qiyukf.nimlib.log.c.b.a.H("sync super team, startIndex=" + i + ", stopIndex=" + iMin + ", code=" + ((int) aVar.h()));
                e.this.a(list, iMin, i2);
            }
        });
    }

    private static void b() {
        if (!com.qiyukf.nimlib.c.O()) {
            com.qiyukf.nimlib.log.c.b.a.H("sync team member disable");
            return;
        }
        ArrayList<Team> arrayListQueryAllTeams = TeamDBHelper.queryAllTeams();
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(com.qiyukf.nimlib.c.q())) {
            return;
        }
        for (Team team : arrayListQueryAllTeams) {
            long jC = com.qiyukf.nimlib.biz.n.c(team.getId());
            if (jC == 0) {
                arrayList.add(team.getId());
            }
            if (jC == 0 || ((com.qiyukf.nimlib.team.d) team).d() > jC) {
                map.put(team.getId(), Long.valueOf(jC));
            }
        }
        if (arrayList.size() > 0 && TeamDBHelper.clearTeamMembers(false, arrayList) < 0) {
            com.qiyukf.nimlib.log.c.b.a.H("clear team member dirty data failed");
            com.qiyukf.nimlib.i.b.a(false);
            return;
        }
        com.qiyukf.nimlib.log.c.b.a.H("clear team member dirty data, size =" + arrayList.size() + " , data = " + arrayList.toString());
        if (map.size() > 0) {
            k.a().a(new com.qiyukf.nimlib.biz.d.h.c(map), com.qiyukf.nimlib.biz.g.a.d);
            com.qiyukf.nimlib.log.c.b.a.H("sync team member info , size = " + map.size() + " , data = " + map.toString());
            return;
        }
        com.qiyukf.nimlib.i.b.a(true);
        com.qiyukf.nimlib.log.c.b.a.H("no need to sync team member info ");
    }
}

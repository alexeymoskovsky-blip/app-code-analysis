package com.qiyukf.nimlib.biz.f;

import android.text.TextUtils;
import android.util.Pair;
import com.qiyukf.nimlib.biz.d.k.q;
import com.qiyukf.nimlib.biz.d.k.r;
import com.qiyukf.nimlib.biz.d.k.s;
import com.qiyukf.nimlib.biz.d.k.t;
import com.qiyukf.nimlib.biz.d.k.u;
import com.qiyukf.nimlib.biz.d.k.v;
import com.qiyukf.nimlib.biz.d.k.w;
import com.qiyukf.nimlib.sdk.InvocationFuture;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.ResponseCode;
import com.qiyukf.nimlib.sdk.antispam.model.AntiSpamConfig;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.SearchOrderEnum;
import com.qiyukf.nimlib.sdk.msg.model.TeamMessageReceipt;
import com.qiyukf.nimlib.sdk.msg.model.TeamMsgAckInfo;
import com.qiyukf.nimlib.sdk.team.TeamService;
import com.qiyukf.nimlib.sdk.team.constant.TeamAllMuteModeEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamBeInviteModeEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamExtensionUpdateModeEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamFieldEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamInviteModeEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamMemberType;
import com.qiyukf.nimlib.sdk.team.constant.TeamMessageNotifyTypeEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamTypeEnum;
import com.qiyukf.nimlib.sdk.team.constant.TeamUpdateModeEnum;
import com.qiyukf.nimlib.sdk.team.constant.VerifyTypeEnum;
import com.qiyukf.nimlib.sdk.team.model.CreateTeamResult;
import com.qiyukf.nimlib.sdk.team.model.NIMTeamMemberRoleTypeSearchOption;
import com.qiyukf.nimlib.sdk.team.model.NIMTeamMemberSearchResult;
import com.qiyukf.nimlib.sdk.team.model.Team;
import com.qiyukf.nimlib.sdk.team.model.TeamInfoResult;
import com.qiyukf.nimlib.sdk.team.model.TeamMember;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.team.TeamDBHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;

/* JADX INFO: compiled from: TeamServiceRemote.java */
/* JADX INFO: loaded from: classes6.dex */
public class o extends com.qiyukf.nimlib.i.i implements TeamService {
    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<CreateTeamResult> createTeam(Map<TeamFieldEnum, Serializable> map, TeamTypeEnum teamTypeEnum, String str, List<String> list) {
        return createTeam(map, teamTypeEnum, str, list, null);
    }

    private static com.qiyukf.nimlib.push.packet.b.c a(Map<TeamFieldEnum, Serializable> map) {
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        if (map != null && map.size() > 0) {
            for (Map.Entry<TeamFieldEnum, Serializable> entry : map.entrySet()) {
                if (entry.getKey().getFieldType() == String.class) {
                    cVar.a(entry.getKey().getValue(), (String) entry.getValue());
                } else if (entry.getKey().getFieldType() == VerifyTypeEnum.class) {
                    cVar.a(entry.getKey().getValue(), ((VerifyTypeEnum) entry.getValue()).getValue());
                } else if (entry.getKey().getFieldType() == TeamInviteModeEnum.class) {
                    cVar.a(entry.getKey().getValue(), ((TeamInviteModeEnum) entry.getValue()).getValue());
                } else if (entry.getKey().getFieldType() == TeamBeInviteModeEnum.class) {
                    cVar.a(entry.getKey().getValue(), ((TeamBeInviteModeEnum) entry.getValue()).getValue());
                } else if (entry.getKey().getFieldType() == TeamUpdateModeEnum.class) {
                    cVar.a(entry.getKey().getValue(), ((TeamUpdateModeEnum) entry.getValue()).getValue());
                } else if (entry.getKey().getFieldType() == TeamExtensionUpdateModeEnum.class) {
                    cVar.a(entry.getKey().getValue(), ((TeamExtensionUpdateModeEnum) entry.getValue()).getValue());
                } else if (entry.getKey().getFieldType() == Integer.class) {
                    cVar.a(entry.getKey().getValue(), ((Integer) entry.getValue()).intValue());
                }
            }
        }
        return cVar;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<String>> addMembersEx(String str, List<String> list, String str2, String str3) {
        a(str, list, str2, str3);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<String>> addMembers(String str, List<String> list) {
        a(str, list, (String) null, (String) null);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> removeMember(String str, String str2) {
        com.qiyukf.nimlib.biz.d.k.m mVar = new com.qiyukf.nimlib.biz.d.k.m();
        mVar.a(str);
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        mVar.a((List<String>) arrayList);
        mVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(mVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> removeMembers(String str, List<String> list) {
        com.qiyukf.nimlib.biz.d.k.m mVar = new com.qiyukf.nimlib.biz.d.k.m();
        mVar.a(str);
        mVar.a(list);
        mVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(mVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> updateName(String str, String str2) {
        return updateTeam(str, TeamFieldEnum.Name, str2);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> updateTeam(String str, TeamFieldEnum teamFieldEnum, Serializable serializable) {
        HashMap map = new HashMap(1);
        map.put(teamFieldEnum, serializable);
        return updateTeamFields(str, map);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> updateTeamFields(String str, Map<TeamFieldEnum, Serializable> map) {
        return updateTeamFields(str, map, null);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> updateTeamFields(String str, Map<TeamFieldEnum, Serializable> map, AntiSpamConfig antiSpamConfig) {
        if (map.containsKey(TeamFieldEnum.AllMute)) {
            throw new IllegalArgumentException("unsupported team field：AllMute");
        }
        if (map.containsKey(TeamFieldEnum.Ext_Server_Only)) {
            throw new IllegalArgumentException("unsupported team field：ext server only");
        }
        b(map);
        com.qiyukf.nimlib.push.packet.b.c cVarA = a(map);
        cVarA.a(1, str);
        w wVar = new w();
        wVar.a(cVarA);
        wVar.a(com.qiyukf.nimlib.i.i.b());
        if (antiSpamConfig != null) {
            com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
            String antiSpamBusinessId = antiSpamConfig.getAntiSpamBusinessId();
            if (!TextUtils.isEmpty(antiSpamBusinessId)) {
                cVar.a(1, antiSpamBusinessId);
            }
            wVar.b(cVar);
        }
        com.qiyukf.nimlib.biz.k.a().a(wVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> dismissTeam(final String str) {
        com.qiyukf.nimlib.biz.d.k.d dVar = new com.qiyukf.nimlib.biz.d.k.d();
        dVar.a(str);
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(dVar) { // from class: com.qiyukf.nimlib.biz.f.o.1
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.h() == 803) {
                    aVar.a().b(ResponseCode.RES_SUCCESS);
                }
                if (aVar.e()) {
                    com.qiyukf.nimlib.team.c.a(str, true, true);
                }
                jVarB.a(aVar.h()).b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> quitTeam(final String str) {
        com.qiyukf.nimlib.biz.d.k.n nVar = new com.qiyukf.nimlib.biz.d.k.n();
        nVar.a(str);
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(nVar) { // from class: com.qiyukf.nimlib.biz.f.o.8
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.h() == 804) {
                    aVar.a().b(ResponseCode.RES_SUCCESS);
                }
                if (aVar.e()) {
                    com.qiyukf.nimlib.team.c.a(str, false, true);
                }
                jVarB.a(aVar.h()).b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Team> queryTeam(String str) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.team.d dVarQueryTeam = TeamDBHelper.queryTeam(str);
        if (dVarQueryTeam == null) {
            searchTeam(str);
            return null;
        }
        jVarB.a(200).a(dVarQueryTeam).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public Team queryTeamBlock(String str) {
        return TeamDBHelper.queryTeam(str);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Team> searchTeam(String str) {
        com.qiyukf.nimlib.biz.d.k.j jVar = new com.qiyukf.nimlib.biz.d.k.j();
        jVar.a(str);
        jVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(jVar, com.qiyukf.nimlib.biz.g.a.c));
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<TeamInfoResult> searchTeam(List<Long> list) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            jVarB.b((Object) null).b();
            return null;
        }
        if (list.size() > 10) {
            list = list.subList(0, 10);
        }
        com.qiyukf.nimlib.biz.d.k.i iVar = new com.qiyukf.nimlib.biz.d.k.i(list);
        iVar.a(jVarB);
        com.qiyukf.nimlib.biz.k.a().a(iVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Team> applyJoinTeam(String str, String str2) {
        com.qiyukf.nimlib.biz.d.k.l lVar = new com.qiyukf.nimlib.biz.d.k.l(str, str2);
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(lVar) { // from class: com.qiyukf.nimlib.biz.f.o.9
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.e() || aVar.h() == 808) {
                    com.qiyukf.nimlib.team.d dVarA = com.qiyukf.nimlib.team.d.a(((com.qiyukf.nimlib.biz.e.m.j) aVar).j());
                    dVarA.f(aVar.e() ? 1 : 0);
                    com.qiyukf.nimlib.team.c.a(dVarA);
                    jVarB.b(dVarA);
                }
                jVarB.a(aVar.h()).b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> passApply(String str, String str2) {
        return a(new q(str, str2, null, true));
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> rejectApply(String str, String str2, String str3) {
        return a(new q(str, str2, str3, false));
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<TeamMember>> addManagers(final String str, final List<String> list) {
        com.qiyukf.nimlib.biz.d.k.b bVar = new com.qiyukf.nimlib.biz.d.k.b(str, list, true);
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(bVar) { // from class: com.qiyukf.nimlib.biz.f.o.10
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                jVarB.a(aVar.h());
                if (aVar.e()) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        com.qiyukf.nimlib.team.g gVarQueryTeamMember = TeamDBHelper.queryTeamMember(str, (String) it.next());
                        if (gVarQueryTeamMember != null) {
                            gVarQueryTeamMember.a(TeamMemberType.Manager);
                            arrayList.add(gVarQueryTeamMember);
                        }
                    }
                    TeamDBHelper.saveTeamMembers(arrayList);
                    jVarB.a(arrayList);
                }
                jVarB.b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<TeamMember>> removeManagers(final String str, final List<String> list) {
        com.qiyukf.nimlib.biz.d.k.b bVar = new com.qiyukf.nimlib.biz.d.k.b(str, list, false);
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(bVar) { // from class: com.qiyukf.nimlib.biz.f.o.11
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                jVarB.a(aVar.h());
                if (aVar.e()) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        com.qiyukf.nimlib.team.g gVarQueryTeamMember = TeamDBHelper.queryTeamMember(str, (String) it.next());
                        if (gVarQueryTeamMember != null) {
                            gVarQueryTeamMember.a(TeamMemberType.Normal);
                            arrayList.add(gVarQueryTeamMember);
                        }
                    }
                    TeamDBHelper.saveTeamMembers(arrayList);
                    jVarB.a(arrayList);
                }
                jVarB.b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<TeamMember>> transferTeam(final String str, final String str2, final boolean z) {
        t tVar = new t(str, str2, z);
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(tVar) { // from class: com.qiyukf.nimlib.biz.f.o.12
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                jVarB.a(aVar.h());
                if (aVar.e()) {
                    if (!z) {
                        ArrayList arrayList = new ArrayList();
                        com.qiyukf.nimlib.team.g gVarQueryTeamMember = TeamDBHelper.queryTeamMember(str, str2);
                        if (gVarQueryTeamMember != null) {
                            gVarQueryTeamMember.a(TeamMemberType.Owner);
                            arrayList.add(gVarQueryTeamMember);
                        }
                        com.qiyukf.nimlib.team.g gVarQueryTeamMember2 = TeamDBHelper.queryTeamMember(str, com.qiyukf.nimlib.c.q());
                        if (gVarQueryTeamMember2 != null) {
                            gVarQueryTeamMember2.a(TeamMemberType.Normal);
                            arrayList.add(gVarQueryTeamMember2);
                        }
                        TeamDBHelper.saveTeamMembers(arrayList);
                        jVarB.a(arrayList);
                        com.qiyukf.nimlib.team.d dVarQueryTeam = TeamDBHelper.queryTeam(str);
                        if (dVarQueryTeam != null) {
                            dVarQueryTeam.c(str2);
                            com.qiyukf.nimlib.team.c.a(dVarQueryTeam);
                        }
                    } else {
                        com.qiyukf.nimlib.biz.n.a(str, 0L);
                        TeamDBHelper.quitTeam(str);
                        com.qiyukf.nimlib.i.b.b(TeamDBHelper.queryTeam(str));
                    }
                }
                jVarB.b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> acceptInvite(String str, String str2) {
        return a(new r(str, str2, null, true));
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> declineInvite(String str, String str2, String str3) {
        return a(new r(str, str2, str3, false));
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public int queryTeamCountBlock() {
        return TeamDBHelper.queryTeamCount();
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public int queryTeamCountByTypeBlock(TeamTypeEnum teamTypeEnum) {
        return TeamDBHelper.queryTeamCountByType(teamTypeEnum);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<Team>> queryTeamList() {
        com.qiyukf.nimlib.i.i.b().b(TeamDBHelper.queryAllTeams()).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public List<Team> queryTeamListBlock() {
        return TeamDBHelper.queryAllTeams();
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<Team>> queryTeamListById(List<String> list) {
        com.qiyukf.nimlib.i.i.b().b(TeamDBHelper.queryTeamListById(list)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public List<Team> queryTeamListByIdBlock(List<String> list) {
        return TeamDBHelper.queryTeamListById(list);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<Team>> queryTeamListByType(TeamTypeEnum teamTypeEnum) {
        com.qiyukf.nimlib.i.i.b().b(TeamDBHelper.queryTeamListByType(teamTypeEnum)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public List<Team> queryTeamListByTypeBlock(TeamTypeEnum teamTypeEnum) {
        return TeamDBHelper.queryTeamListByType(teamTypeEnum);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<TeamMember>> queryMemberList(final String str) {
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        boolean z = false;
        if (!a(str)) {
            com.qiyukf.nimlib.team.d dVarQueryTeam = TeamDBHelper.queryTeam(str);
            if (dVarQueryTeam != null && TeamDBHelper.queryMemberCount(str) != dVarQueryTeam.getMemberCount()) {
                z = true;
            }
            if (!z) {
                b(str, jVarB);
                return null;
            }
        }
        com.qiyukf.nimlib.biz.d.k.h hVar = new com.qiyukf.nimlib.biz.d.k.h();
        hVar.a(str);
        hVar.a(z ? 0L : com.qiyukf.nimlib.biz.n.c(str));
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(hVar) { // from class: com.qiyukf.nimlib.biz.f.o.13
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                try {
                    o.this.b(str, jVarB);
                } catch (Throwable th) {
                    jVarB.a(th).b();
                }
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<TeamMember> queryTeamMember(final String str, final String str2) {
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (a(str)) {
            com.qiyukf.nimlib.biz.d.k.h hVar = new com.qiyukf.nimlib.biz.d.k.h();
            hVar.a(str);
            hVar.a(com.qiyukf.nimlib.biz.n.c(str));
            com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(hVar) { // from class: com.qiyukf.nimlib.biz.f.o.14
                @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
                public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                    try {
                        o.b(str, str2, jVarB);
                    } catch (Throwable th) {
                        jVarB.a(th).b();
                    }
                }
            });
            return null;
        }
        b(str, str2, jVarB);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public TeamMember queryTeamMemberBlock(String str, String str2) {
        return TeamDBHelper.queryTeamMember(str, str2);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public List<TeamMember> queryMutedTeamMembers(String str) {
        return TeamDBHelper.queryMutedMemberList(str);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> updateMyTeamNick(String str, String str2) {
        return updateMemberNick(str, com.qiyukf.nimlib.c.q(), str2);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> updateMemberNick(String str, String str2, String str3) {
        boolean zEquals = str2.equals(com.qiyukf.nimlib.c.q());
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(1, str);
        cVar.a(5, str3);
        if (!zEquals) {
            cVar.a(3, str2);
        }
        return a(cVar, zEquals);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> updateMyMemberExtension(String str, Map<String, Object> map) {
        String strQ = com.qiyukf.nimlib.c.q();
        boolean zEquals = strQ.equals(com.qiyukf.nimlib.c.q());
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(1, str);
        cVar.a(12, com.qiyukf.nimlib.session.k.a(map));
        if (!zEquals) {
            cVar.a(3, strQ);
        }
        return a(cVar, zEquals);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> muteTeam(String str, TeamMessageNotifyTypeEnum teamMessageNotifyTypeEnum) {
        long memberBits = TeamDBHelper.getMemberBits(str);
        com.qiyukf.nimlib.log.c.b.a.d("TeamService", String.format("muteTeam, teamId=%s, notifyType=%s, bits=%s", str, teamMessageNotifyTypeEnum, Long.valueOf(memberBits)));
        if (teamMessageNotifyTypeEnum == TeamMessageNotifyTypeEnum.All) {
            memberBits = com.qiyukf.nimlib.team.b.b(com.qiyukf.nimlib.team.b.a(memberBits, false), false);
        } else if (teamMessageNotifyTypeEnum == TeamMessageNotifyTypeEnum.Manager) {
            memberBits = com.qiyukf.nimlib.team.b.b(com.qiyukf.nimlib.team.b.a(memberBits, false), true);
        } else if (teamMessageNotifyTypeEnum == TeamMessageNotifyTypeEnum.Mute) {
            memberBits = com.qiyukf.nimlib.team.b.b(com.qiyukf.nimlib.team.b.a(memberBits, true), false);
        }
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(1, str);
        cVar.a(7, memberBits);
        return a(cVar, true);
    }

    private static void b(Map<TeamFieldEnum, Serializable> map) {
        for (Map.Entry<TeamFieldEnum, Serializable> entry : map.entrySet()) {
            if (!entry.getKey().getFieldType().isInstance(entry.getValue())) {
                throw new IllegalArgumentException("type of TeamFieldEnum." + entry.getKey().name() + " wrong, should be " + entry.getKey().getFieldType().getName());
            }
            if (entry.getKey() == TeamFieldEnum.undefined) {
                throw new IllegalArgumentException("undefined team field");
            }
        }
    }

    private InvocationFuture<Void> a(final com.qiyukf.nimlib.push.packet.b.c cVar, final boolean z) {
        com.qiyukf.nimlib.biz.d.a uVar = z ? new u(cVar, null) : new v(cVar);
        uVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(uVar) { // from class: com.qiyukf.nimlib.biz.f.o.15
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.e()) {
                    if (z) {
                        cVar.a(3, com.qiyukf.nimlib.c.q());
                    }
                    com.qiyukf.nimlib.team.c.a(cVar);
                }
                ((com.qiyukf.nimlib.i.j) this.h.e()).a(aVar.h()).b();
            }
        });
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final String str, final com.qiyukf.nimlib.i.j jVar) {
        Set<String> setQueryMemberAccountList = TeamDBHelper.queryMemberAccountList(str);
        ArrayList arrayList = new ArrayList();
        for (String str2 : setQueryMemberAccountList) {
            if (com.qiyukf.nimlib.user.c.b(str2)) {
                arrayList.add(str2);
            }
        }
        if (!arrayList.isEmpty()) {
            com.qiyukf.nimlib.user.c.a(arrayList, new RequestCallbackWrapper<ArrayList<com.qiyukf.nimlib.user.b>>() { // from class: com.qiyukf.nimlib.biz.f.o.2
                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i, ArrayList<com.qiyukf.nimlib.user.b> arrayList2, Throwable th) {
                    o.c(str, jVar);
                }
            });
        } else {
            c(str, jVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(String str, com.qiyukf.nimlib.i.j jVar) {
        jVar.b(TeamDBHelper.queryMemberList(str)).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, com.qiyukf.nimlib.i.j jVar) {
        com.qiyukf.nimlib.team.g gVarQueryTeamMember = TeamDBHelper.queryTeamMember(str, str2);
        if (gVarQueryTeamMember != null) {
            jVar.b(gVarQueryTeamMember);
        } else {
            jVar.a(404);
        }
        jVar.b();
    }

    private static boolean a(String str) {
        long jQueryMemberTimetag = TeamDBHelper.queryMemberTimetag(str);
        long jC = com.qiyukf.nimlib.biz.n.c(str);
        return jC == 0 || jC < jQueryMemberTimetag;
    }

    private InvocationFuture<Void> a(com.qiyukf.nimlib.biz.d.a aVar) {
        aVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(aVar) { // from class: com.qiyukf.nimlib.biz.f.o.3
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar2) {
                ((com.qiyukf.nimlib.i.j) this.h.e()).a(aVar2.h()).b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> muteTeamMember(String str, String str2, boolean z) {
        com.qiyukf.nimlib.biz.d.k.o oVar = new com.qiyukf.nimlib.biz.d.k.o(str, str2, z);
        oVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(oVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> muteAllTeamMember(String str, boolean z) {
        com.qiyukf.nimlib.biz.d.k.p pVar = new com.qiyukf.nimlib.biz.d.k.p(str, (z ? TeamAllMuteModeEnum.MuteNormal : TeamAllMuteModeEnum.Cancel).getValue());
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(pVar) { // from class: com.qiyukf.nimlib.biz.f.o.4
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                jVarB.a(aVar.h());
                if (aVar.e()) {
                    jVarB.a((Object) null);
                }
                jVarB.b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> sendTeamMessageReceipt(IMMessage iMMessage) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (!com.qiyukf.nimlib.c.h().enableTeamMsgAck) {
            com.qiyukf.nimlib.log.c.b.a.H("team msg ack is disabled");
            jVarB.a(1000).b();
            return null;
        }
        if (!iMMessage.needMsgAck()) {
            com.qiyukf.nimlib.log.c.b.a.H("msg not need ack");
            jVarB.a(200).b();
            return null;
        }
        if (iMMessage.hasSendAck()) {
            com.qiyukf.nimlib.log.c.b.a.H("msg has send ack");
            jVarB.a(200).b();
            return null;
        }
        com.qiyukf.nimlib.team.h.c().a(iMMessage.getUuid(), jVarB);
        com.qiyukf.nimlib.team.k.b().a(iMMessage);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public void refreshTeamMessageReceipt(List<IMMessage> list) {
        ArrayList arrayList;
        if (!com.qiyukf.nimlib.c.h().enableTeamMsgAck) {
            com.qiyukf.nimlib.log.c.b.a.H("team msg ack is disabled");
            return;
        }
        final List<IMMessage> listC = com.qiyukf.nimlib.team.h.c().c(list);
        if (listC != null) {
            arrayList = new ArrayList(listC.size());
            for (IMMessage iMMessage : listC) {
                arrayList.add(new Pair(iMMessage.getSessionId(), Long.valueOf(((com.qiyukf.nimlib.session.d) iMMessage).getServerId())));
            }
        } else {
            arrayList = null;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        com.qiyukf.nimlib.log.c.b.a.H("refresh team message receipts, size=" + arrayList.size());
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(new com.qiyukf.nimlib.biz.d.k.e(arrayList)) { // from class: com.qiyukf.nimlib.biz.f.o.5
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.e()) {
                    com.qiyukf.nimlib.biz.c.j.t.a(((com.qiyukf.nimlib.biz.e.m.q) aVar).j());
                    return;
                }
                com.qiyukf.nimlib.team.h.c().d(listC);
                com.qiyukf.nimlib.log.c.b.a.H("refresh team mag ack info failed, code=" + ((int) aVar.h()));
            }
        });
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<TeamMsgAckInfo> fetchTeamMessageReceiptDetail(IMMessage iMMessage) {
        return a(iMMessage, true, (Set<String>) null);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<TeamMsgAckInfo> fetchTeamMessageReceiptDetail(IMMessage iMMessage, Set<String> set) {
        return a(iMMessage, false, set);
    }

    private InvocationFuture<TeamMsgAckInfo> a(IMMessage iMMessage, final boolean z, final Set<String> set) {
        final com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (!com.qiyukf.nimlib.c.h().enableTeamMsgAck) {
            com.qiyukf.nimlib.log.c.b.a.H("team msg ack is disabled");
            jVarB.a(1000).b();
            return null;
        }
        if (iMMessage == null || !iMMessage.needMsgAck() || iMMessage.getSessionType() != SessionTypeEnum.Team) {
            com.qiyukf.nimlib.log.c.b.a.H("msg is null or ack not needed or session type is not team");
            jVarB.a(414).b();
            return null;
        }
        if (!z && com.qiyukf.nimlib.n.e.b((Collection) set)) {
            com.qiyukf.nimlib.log.c.b.a.H("fetch receipts from part of members with empty account set");
            jVarB.b(new TeamMsgAckInfo(iMMessage.getSessionId(), iMMessage.getUuid(), new ArrayList(0), new ArrayList(0))).b();
            return null;
        }
        TeamMsgAckInfo teamMsgAckInfoQueryTeamMsgAckDetail = MsgDBHelper.queryTeamMsgAckDetail(iMMessage.getUuid());
        Pair<Integer, Integer> pairA = com.qiyukf.nimlib.team.h.c().a(iMMessage.getUuid());
        if (teamMsgAckInfoQueryTeamMsgAckDetail != null && pairA != null && teamMsgAckInfoQueryTeamMsgAckDetail.getAckCount() == ((Integer) pairA.first).intValue() && teamMsgAckInfoQueryTeamMsgAckDetail.getUnAckCount() == ((Integer) pairA.second).intValue()) {
            com.qiyukf.nimlib.log.c.b.a.H("no need to fetch team message ack detail, as current is the newest data, reply directly");
            if (!z) {
                teamMsgAckInfoQueryTeamMsgAckDetail = teamMsgAckInfoQueryTeamMsgAckDetail.newInstanceFromPartOfAccount(set);
            }
            jVarB.b(teamMsgAckInfoQueryTeamMsgAckDetail).b();
            return null;
        }
        final boolean z2 = teamMsgAckInfoQueryTeamMsgAckDetail == null || ((teamMsgAckInfoQueryTeamMsgAckDetail.getAckAccountList() == null || teamMsgAckInfoQueryTeamMsgAckDetail.getAckAccountList().isEmpty()) && (teamMsgAckInfoQueryTeamMsgAckDetail.getUnAckAccountList() == null || teamMsgAckInfoQueryTeamMsgAckDetail.getUnAckAccountList().isEmpty()));
        com.qiyukf.nimlib.log.c.b.a.H("fetch team message receipt detail, msgId=" + iMMessage.getUuid() + ", snapshot=" + z2);
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(new com.qiyukf.nimlib.biz.d.k.f(iMMessage.getSessionId(), Long.valueOf(((com.qiyukf.nimlib.session.d) iMMessage).getServerId()), z2)) { // from class: com.qiyukf.nimlib.biz.f.o.6
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.e()) {
                    com.qiyukf.nimlib.biz.e.m.r rVar = (com.qiyukf.nimlib.biz.e.m.r) aVar;
                    TeamMsgAckInfo teamMsgAckInfoJ = rVar.j();
                    if (!z2) {
                        MsgDBHelper.updateTeamMsgAckDetail(teamMsgAckInfoJ.getMsgId(), rVar.k());
                        teamMsgAckInfoJ = MsgDBHelper.queryTeamMsgAckDetail(teamMsgAckInfoJ.getMsgId());
                    } else {
                        MsgDBHelper.saveTeamMsgAckDetail(teamMsgAckInfoJ, rVar.k());
                    }
                    if (teamMsgAckInfoJ != null) {
                        com.qiyukf.nimlib.team.h.c().e(new ArrayList(Arrays.asList(new TeamMessageReceipt(teamMsgAckInfoJ))));
                    }
                    com.qiyukf.nimlib.i.j jVar = jVarB;
                    if (!z && teamMsgAckInfoJ != null) {
                        teamMsgAckInfoJ = teamMsgAckInfoJ.newInstanceFromPartOfAccount(set);
                    }
                    jVar.b(teamMsgAckInfoJ).b();
                    return;
                }
                jVarB.a(aVar.h()).b();
            }
        });
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public TeamMsgAckInfo queryTeamMessageReceiptDetailBlock(IMMessage iMMessage) {
        return b(iMMessage, true, (Set<String>) null);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public TeamMsgAckInfo queryTeamMessageReceiptDetailBlock(IMMessage iMMessage, Set<String> set) {
        return b(iMMessage, false, set);
    }

    private static TeamMsgAckInfo b(IMMessage iMMessage, boolean z, Set<String> set) {
        if (!com.qiyukf.nimlib.c.h().enableTeamMsgAck) {
            com.qiyukf.nimlib.log.c.b.a.H("team msg ack is disabled");
            return null;
        }
        if (iMMessage == null || !iMMessage.needMsgAck() || iMMessage.getSessionType() != SessionTypeEnum.Team) {
            com.qiyukf.nimlib.log.c.b.a.H("msg is null or ack not needed or session type is not team");
            return null;
        }
        if (!z && com.qiyukf.nimlib.n.e.b((Collection) set)) {
            com.qiyukf.nimlib.log.c.b.a.H("query receipts from part of members with empty account set");
            return new TeamMsgAckInfo(iMMessage.getSessionId(), iMMessage.getUuid(), new ArrayList(0), new ArrayList(0));
        }
        TeamMsgAckInfo teamMsgAckInfoQueryTeamMsgAckDetail = MsgDBHelper.queryTeamMsgAckDetail(iMMessage.getUuid());
        return (set == null || teamMsgAckInfoQueryTeamMsgAckDetail == null) ? teamMsgAckInfoQueryTeamMsgAckDetail : teamMsgAckInfoQueryTeamMsgAckDetail.newInstanceFromPartOfAccount(set);
    }

    private static void a(String str, List<String> list, String str2, String str3) {
        if (str2 == null) {
            str2 = "";
        }
        if (str3 == null) {
            str3 = "";
        }
        com.qiyukf.nimlib.biz.d.k.k kVar = new com.qiyukf.nimlib.biz.d.k.k();
        kVar.a(str);
        kVar.a(list);
        kVar.b(str2);
        kVar.c(str3);
        kVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(kVar);
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Map<String, String>> getMemberInvitor(String str, List<String> list) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (TextUtils.isEmpty(str) || list == null || list.isEmpty() || list.size() > 200) {
            jVarB.a(414).b();
            return null;
        }
        ArrayList<com.qiyukf.nimlib.team.g> arrayListQueryMemberListByAccids = TeamDBHelper.queryMemberListByAccids(str, list);
        boolean z = arrayListQueryMemberListByAccids == null || arrayListQueryMemberListByAccids.size() == 0 || arrayListQueryMemberListByAccids.size() != list.size();
        HashMap map = new HashMap(list.size());
        if (!z) {
            for (String str2 : list) {
                Iterator<com.qiyukf.nimlib.team.g> it = arrayListQueryMemberListByAccids.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    com.qiyukf.nimlib.team.g next = it.next();
                    if (TextUtils.equals(str2, next.getAccount())) {
                        if (next.getInvitorAccid() == null) {
                            z = true;
                            break;
                        }
                        map.put(str2, next.getInvitorAccid());
                    }
                }
                if (z) {
                    break;
                }
            }
        }
        if (!z) {
            z = map.size() != list.size();
        }
        if (!z) {
            jVarB.b(map).b();
        } else {
            com.qiyukf.nimlib.biz.d.k.g gVar = new com.qiyukf.nimlib.biz.d.k.g(str, list);
            gVar.a(jVarB);
            com.qiyukf.nimlib.biz.k.a().a(gVar);
        }
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<Team>> searchTeamsByKeyword(String str) {
        com.qiyukf.nimlib.i.i.b().b(TeamDBHelper.searchTeamsByKeyword(str)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<IMMessage>> searchTeamMsgByKeyword(long j, long j2, long j3, String str, int i, boolean z) {
        s sVar = new s(j, j2, j3, str, i, z);
        sVar.a(com.qiyukf.nimlib.i.i.b());
        com.qiyukf.nimlib.biz.k.a().a(sVar);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<List<String>> searchTeamIdByName(String str) {
        com.qiyukf.nimlib.i.i.b().b(TeamDBHelper.queryTeamIdByName(str)).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<NIMTeamMemberSearchResult> getTeamMemberList(String str, NIMTeamMemberRoleTypeSearchOption nIMTeamMemberRoleTypeSearchOption) {
        com.qiyukf.nimlib.i.j jVarB = com.qiyukf.nimlib.i.i.b();
        if (TextUtils.isEmpty(str)) {
            jVarB.a(414).b();
            return null;
        }
        if (nIMTeamMemberRoleTypeSearchOption == null || !nIMTeamMemberRoleTypeSearchOption.isValid()) {
            jVarB.a(414).b();
            return null;
        }
        com.qiyukf.nimlib.team.a aVar = new com.qiyukf.nimlib.team.a();
        if (!TeamDBHelper.isMyTeam(str)) {
            aVar.a(new ArrayList());
            aVar.a(true);
            aVar.a(0L);
            jVarB.b(aVar).b();
            return null;
        }
        HashSet hashSet = new HashSet();
        if (nIMTeamMemberRoleTypeSearchOption.getRoleTypes() != null) {
            hashSet.addAll(nIMTeamMemberRoleTypeSearchOption.getRoleTypes());
        }
        List<TeamMember> listQueryMemberListByTypes = TeamDBHelper.queryMemberListByTypes(str, hashSet, nIMTeamMemberRoleTypeSearchOption.getOffset().intValue(), nIMTeamMemberRoleTypeSearchOption.getLimit().intValue() + 1, SearchOrderEnum.DESC.equals(nIMTeamMemberRoleTypeSearchOption.getOrder()));
        boolean z = listQueryMemberListByTypes.size() <= nIMTeamMemberRoleTypeSearchOption.getLimit().intValue();
        if (!z) {
            listQueryMemberListByTypes.remove(listQueryMemberListByTypes.size() - 1);
        }
        aVar.a(listQueryMemberListByTypes);
        aVar.a(z);
        aVar.a(z ? 0L : nIMTeamMemberRoleTypeSearchOption.getOffset().intValue() + listQueryMemberListByTypes.size());
        jVarB.b(aVar).b();
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> addTeamMembersFollow(String str, List<String> list) {
        a(com.qiyukf.nimlib.i.i.b(), str, list, true);
        return null;
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<Void> removeTeamMembersFollow(String str, List<String> list) {
        a(com.qiyukf.nimlib.i.i.b(), str, list, false);
        return null;
    }

    private void a(final com.qiyukf.nimlib.i.j jVar, String str, List<String> list, boolean z) {
        if (TextUtils.isEmpty(str) || com.qiyukf.nimlib.n.e.b((Collection) list)) {
            jVar.a(414).b();
            return;
        }
        com.qiyukf.nimlib.push.packet.b.c cVar = new com.qiyukf.nimlib.push.packet.b.c();
        cVar.a(1, str);
        com.qiyukf.nimlib.push.packet.b.c cVar2 = new com.qiyukf.nimlib.push.packet.b.c();
        cVar2.a(1, new JSONArray((Collection) list).toString());
        cVar2.a(2, z ? 1 : 0);
        u uVar = new u(cVar, cVar2);
        uVar.a(jVar);
        com.qiyukf.nimlib.biz.k.a().a(new com.qiyukf.nimlib.biz.g.b(uVar) { // from class: com.qiyukf.nimlib.biz.f.o.7
            @Override // com.qiyukf.nimlib.biz.g.b, com.qiyukf.nimlib.biz.g.c
            public final void a(com.qiyukf.nimlib.biz.e.a aVar) {
                if (aVar.e()) {
                    com.qiyukf.nimlib.team.c.a(((com.qiyukf.nimlib.biz.e.m.w) aVar).j());
                }
                jVar.a(aVar.h()).b();
            }
        });
    }

    @Override // com.qiyukf.nimlib.sdk.team.TeamService
    public InvocationFuture<CreateTeamResult> createTeam(Map<TeamFieldEnum, Serializable> map, TeamTypeEnum teamTypeEnum, String str, List<String> list, AntiSpamConfig antiSpamConfig) {
        b(map);
        com.qiyukf.nimlib.biz.d.k.c cVar = new com.qiyukf.nimlib.biz.d.k.c();
        if (list == null) {
            list = new ArrayList<>();
        }
        cVar.a(list);
        cVar.a(str);
        com.qiyukf.nimlib.push.packet.b.c cVarA = a(map);
        cVarA.a(4, teamTypeEnum.getValue());
        cVar.a(cVarA);
        cVar.a(com.qiyukf.nimlib.i.i.b());
        if (antiSpamConfig != null) {
            com.qiyukf.nimlib.push.packet.b.c cVar2 = new com.qiyukf.nimlib.push.packet.b.c();
            String antiSpamBusinessId = antiSpamConfig.getAntiSpamBusinessId();
            if (!TextUtils.isEmpty(antiSpamBusinessId)) {
                cVar2.a(1, antiSpamBusinessId);
            }
            cVar.b(cVar2);
        }
        com.qiyukf.nimlib.biz.k.a().a(cVar);
        return null;
    }
}

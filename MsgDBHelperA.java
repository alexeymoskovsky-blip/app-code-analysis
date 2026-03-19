package com.qiyukf.nimlib.session;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import com.qiyukf.nimlib.n.e;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SystemMessageStatus;
import com.qiyukf.nimlib.sdk.msg.constant.SystemMessageType;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.MessageKey;
import com.qiyukf.nimlib.sdk.msg.model.MessageReceipt;
import com.qiyukf.nimlib.sdk.msg.model.MsgPinDbOption;
import com.qiyukf.nimlib.sdk.msg.model.MsgSearchOption;
import com.qiyukf.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.model.QuickCommentOption;
import com.qiyukf.nimlib.sdk.msg.model.RecentContact;
import com.qiyukf.nimlib.sdk.msg.model.RoamMsgHasMoreOption;
import com.qiyukf.nimlib.sdk.msg.model.SearchOrderEnum;
import com.qiyukf.nimlib.sdk.msg.model.StickTopSessionInfo;
import com.qiyukf.nimlib.sdk.msg.model.SystemMessage;
import com.qiyukf.nimlib.sdk.msg.model.TeamMsgAckInfo;
import com.qiyukf.nimlib.search.b;
import com.qiyukf.nimlib.session.MsgDBHelperUtils;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes6.dex */
public class MsgDBHelperA {
    public static void saveRecent(s sVar) {
        MsgDBHelperUtils.database().a("insert or replace into lstmsg(uid,fromuid,messageId,msgstatus,unreadnum,content,time,sessiontype,tag,msgtype,attach,extension) values ('" + com.qiyukf.nimlib.database.a.d.a(sVar.getContactId()) + "','" + com.qiyukf.nimlib.database.a.d.a(sVar.getFromAccount()) + "','" + sVar.getRecentMessageId() + "','" + sVar.getMsgStatus().getValue() + "','" + sVar.getUnreadCount() + "','" + com.qiyukf.nimlib.database.a.d.a(sVar.getContent()) + "','" + sVar.getTime() + "','" + sVar.getSessionType().getValue() + "','" + sVar.getTag() + "','" + sVar.b() + "','" + com.qiyukf.nimlib.database.a.d.a(sVar.a()) + "','" + com.qiyukf.nimlib.database.a.d.a(sVar.c()) + "')");
    }

    public static void importRecentContactByUnionKey(List<s> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (s sVar : list) {
                StringBuilder sb = new StringBuilder();
                sb.append("('");
                sb.append(com.qiyukf.nimlib.database.a.d.a(sVar.getContactId()));
                sb.append("','");
                sb.append(sVar.getSessionType().getValue());
                sb.append("')");
                MsgDBHelperUtils.database().a("insert or ignore into lstmsg (uid,sessiontype) values" + ((Object) sb));
            }
            MsgDBHelperUtils.database().h();
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    public static void saveRoamMsgHasMore(List<RoamMsgHasMoreOption> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (RoamMsgHasMoreOption roamMsgHasMoreOption : list) {
                com.qiyukf.nimlib.database.d dVarDatabase = MsgDBHelperUtils.database();
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT OR REPLACE INTO  roam_msg_has_more (serverid, session_id, session_type, time) VALUES ");
                sb.append("('" + roamMsgHasMoreOption.getServerId() + "','" + com.qiyukf.nimlib.database.a.d.a(roamMsgHasMoreOption.getSessionId()) + "','" + roamMsgHasMoreOption.getSessionType().getValue() + "','" + roamMsgHasMoreOption.getTime() + "')");
                dVarDatabase.a(sb.toString());
            }
            MsgDBHelperUtils.database().h();
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    public static void saveQuickComment(String str, List<QuickCommentOption> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (QuickCommentOption quickCommentOption : list) {
                com.qiyukf.nimlib.database.d dVarDatabase = MsgDBHelperUtils.database();
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT OR REPLACE INTO quick_comment (uuid, operator, type, time, ext) VALUES ");
                sb.append("('" + str + "','" + com.qiyukf.nimlib.database.a.d.a(quickCommentOption.getFromAccount()) + "','" + quickCommentOption.getReplyType() + "','" + quickCommentOption.getTime() + "','" + com.qiyukf.nimlib.database.a.d.a(quickCommentOption.getExt()) + "')");
                dVarDatabase.a(sb.toString());
            }
            MsgDBHelperUtils.database().h();
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    public static void saveCollectInfo(List<a> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (a aVar : list) {
                com.qiyukf.nimlib.database.d dVarDatabase = MsgDBHelperUtils.database();
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT OR REPLACE INTO collect_info (id, type, data, ext, uniqueId, createTime, updateTime) VALUES ");
                sb.append("('" + aVar.getId() + "','" + aVar.getType() + "','" + com.qiyukf.nimlib.database.a.d.a(aVar.getData()) + "','" + com.qiyukf.nimlib.database.a.d.a(aVar.getExt()) + "','" + com.qiyukf.nimlib.database.a.d.a(aVar.getUniqueId()) + "','" + aVar.getCreateTime() + "','" + aVar.getUpdateTime() + "')");
                dVarDatabase.a(sb.toString());
            }
            MsgDBHelperUtils.database().h();
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    public static void saveMsgPin(List<p> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (p pVar : list) {
                if (pVar != null) {
                    MessageKey key = pVar.getKey();
                    o oVarA = pVar.a();
                    if (key != null && oVarA != null) {
                        com.qiyukf.nimlib.database.d dVarDatabase = MsgDBHelperUtils.database();
                        StringBuilder sb = new StringBuilder();
                        sb.append("INSERT OR REPLACE INTO msg_pin (uuid, session_id, operator, ext, create_time, update_time) VALUES ");
                        sb.append("('" + key.getUuid() + "','" + h.a(key) + "','" + com.qiyukf.nimlib.database.a.d.a(oVarA.getAccount()) + "','" + com.qiyukf.nimlib.database.a.d.a(oVarA.getExt()) + "','" + oVarA.getCreateTime() + "','" + oVarA.getUpdateTime() + "')");
                        dVarDatabase.a(sb.toString());
                    }
                }
            }
            MsgDBHelperUtils.database().h();
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    public static void saveStickTopSession(List<StickTopSessionInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (StickTopSessionInfo stickTopSessionInfo : list) {
                if (stickTopSessionInfo != null) {
                    com.qiyukf.nimlib.database.d dVarDatabase = MsgDBHelperUtils.database();
                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT OR REPLACE INTO session_stick_top (session_id, session_type, ext, create_time, update_time) VALUES ");
                    sb.append("('" + com.qiyukf.nimlib.database.a.d.a(stickTopSessionInfo.getSessionId()) + "','" + stickTopSessionInfo.getSessionType().getValue() + "','" + com.qiyukf.nimlib.database.a.d.a(stickTopSessionInfo.getExt()) + "','" + stickTopSessionInfo.getCreateTime() + "','" + stickTopSessionInfo.getUpdateTime() + "')");
                    dVarDatabase.a(sb.toString());
                }
            }
            MsgDBHelperUtils.database().h();
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    public static List<d> queryMessageListInSeqIdRange(long j, long j2, int[] iArr, int[] iArr2) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where messageid > ");
        sb.append(j);
        sb.append(" and messageid <= ");
        sb.append(j2);
        sb.append(" and");
        sb.append(" sessiontype in (");
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append(ChineseToPinyinResource.Field.COMMA);
            }
            sb.append(iArr[i]);
        }
        sb.append(") and msgtype in (");
        for (int i2 = 0; i2 < iArr2.length; i2++) {
            if (i2 > 0) {
                sb.append(ChineseToPinyinResource.Field.COMMA);
            }
            sb.append(iArr2[i2]);
        }
        sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        sb.append(" order by messageid asc");
        ArrayList arrayList = new ArrayList();
        Iterator<IMMessage> it = MsgDBHelperInternal.queryMsgHistories(sb.toString()).iterator();
        while (it.hasNext()) {
            arrayList.add((d) it.next());
        }
        return arrayList;
    }

    public static long getMessageTimeByUuid(String str) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery(("SELECT time FROM msghistory where uuid='" + com.qiyukf.nimlib.database.a.d.a(str) + "'").toString());
        long j = (cursorRawQuery == null || !cursorRawQuery.moveToNext()) ? 0L : cursorRawQuery.getLong(0);
        if (cursorRawQuery != null && !cursorRawQuery.isClosed()) {
            cursorRawQuery.close();
        }
        return j;
    }

    public static ArrayList<IMMessage> queryMessageList(String str, int i, long j, int i2) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageList(%s, %s, %s, %s)", str, Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(i2)));
        return MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where id='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + i + "' ORDER BY time desc limit " + i2 + " offset " + j);
    }

    public static ArrayList<IMMessage> queryMessageListEx(d dVar, long j, long j2, boolean z) {
        String sessionId = dVar.getSessionId();
        int value = dVar.getSessionType().getValue();
        boolean z2 = dVar.a() > 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id='");
        sb.append(com.qiyukf.nimlib.database.a.d.a(sessionId));
        sb.append("' and sessiontype='");
        sb.append(value);
        sb.append("'");
        sb.append(" and time>=");
        sb.append(j);
        sb.append(" and time<=");
        sb.append(j2);
        sb.append(" ORDER BY time ASC");
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString());
        if (!z2) {
            return arrayListQueryMsgHistories;
        }
        Iterator<IMMessage> it = arrayListQueryMsgHistories.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            if (((d) it.next()).a() == dVar.a()) {
                break;
            }
        }
        for (int i2 = 0; i2 <= i - 1; i2++) {
            arrayListQueryMsgHistories.remove(i2);
        }
        if (i <= 1) {
            return arrayListQueryMsgHistories;
        }
        sb.delete(sb.lastIndexOf(" "), sb.length());
        sb.append(" offset ");
        sb.append(i);
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static ArrayList<IMMessage> queryMessageListEx(List<MsgTypeEnum> list, d dVar, long j, QueryDirectionEnum queryDirectionEnum, int i, boolean z) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListEx(%s, %s, %s, %s, %s), types size is %s", list, d.a(dVar), Long.valueOf(j), queryDirectionEnum, Integer.valueOf(i), Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        String sessionId = dVar.getSessionId();
        int value = dVar.getSessionType().getValue();
        boolean z2 = dVar.a() > 0;
        boolean z3 = com.qiyukf.nimlib.n.e.b((Collection) list) || list.contains(dVar.getMsgType());
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id='");
        sb.append(com.qiyukf.nimlib.database.a.d.a(sessionId));
        sb.append("' and sessiontype='");
        sb.append(value);
        sb.append("'");
        boolean z4 = queryDirectionEnum == QueryDirectionEnum.QUERY_NEW;
        if (z4) {
            sb.append(" and time>=");
            sb.append(dVar.getTime());
        } else if (dVar.getTime() > 0) {
            sb.append(" and time<=");
            sb.append(dVar.getTime());
        }
        if (j > 0) {
            if (z4) {
                sb.append(" and time<=");
                sb.append(j);
            } else {
                sb.append(" and time>=");
                sb.append(j);
            }
        }
        if (list != null && !list.isEmpty()) {
            Iterator<MsgTypeEnum> it = list.iterator();
            String str = " and msgtype in(";
            while (it.hasNext()) {
                str = (str + it.next().getValue()) + ChineseToPinyinResource.Field.COMMA;
            }
            sb.append(str.substring(0, str.length() - 1) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        int i2 = (!z ? z2 : z3 && z2) ? i : i + 1;
        sb.append(" ORDER BY time ");
        sb.append(z4 ? "ASC" : "DESC");
        sb.append(" limit ");
        sb.append(i2);
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString());
        if ((z && !z3) || !z2) {
            return arrayListQueryMsgHistories;
        }
        Iterator<IMMessage> it2 = arrayListQueryMsgHistories.iterator();
        int i3 = 0;
        while (it2.hasNext()) {
            i3++;
            if (((d) it2.next()).a() == dVar.a()) {
                break;
            }
        }
        for (int i4 = 0; i4 <= i3 - 1; i4++) {
            arrayListQueryMsgHistories.remove(0);
        }
        if (i3 <= 1) {
            return arrayListQueryMsgHistories;
        }
        sb.delete(sb.lastIndexOf(" ") + 1, sb.length());
        sb.append(i);
        sb.append(" offset ");
        sb.append(i3);
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static List<IMMessage> queryMessageListByType(MsgTypeEnum msgTypeEnum, IMMessage iMMessage, int i) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListByType(%s, %s, %s)", msgTypeEnum, d.a(iMMessage), Integer.valueOf(i)));
        String sessionId = iMMessage.getSessionId();
        int value = iMMessage.getSessionType().getValue();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id='");
        sb.append(com.qiyukf.nimlib.database.a.d.a(sessionId));
        sb.append("' and sessiontype='");
        sb.append(value);
        sb.append("'");
        if (iMMessage.getTime() > 0) {
            sb.append(" and time<'");
            sb.append(iMMessage.getTime());
            sb.append("'");
        }
        sb.append(" and msgtype='");
        sb.append(msgTypeEnum.getValue());
        sb.append("' ORDER BY time desc limit ");
        sb.append(i);
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static List<IMMessage> queryMessageListByType(MsgTypeEnum msgTypeEnum, Long l, int i) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListByType(%s, %s, %s)", msgTypeEnum, l, Integer.valueOf(i)));
        StringBuilder sb = new StringBuilder();
        if (l == null) {
            sb.append("SELECT ");
            sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
            sb.append(" FROM msghistory where msgtype='");
            sb.append(msgTypeEnum.getValue());
            sb.append("' ORDER BY time desc");
        } else {
            sb.append("SELECT ");
            sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
            sb.append(" FROM msghistory where time<");
            sb.append(l);
            sb.append(" and msgtype='");
            sb.append(msgTypeEnum.getValue());
            sb.append("' ORDER BY time desc limit ");
            sb.append(i);
        }
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static ArrayList<IMMessage> queryMessageListBySubtype(MsgTypeEnum msgTypeEnum, IMMessage iMMessage, int i, int i2) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListBySubtype(%s, %s, %s, %s)", msgTypeEnum, d.a(iMMessage), Integer.valueOf(i), Integer.valueOf(i2)));
        String sessionId = iMMessage.getSessionId();
        int value = iMMessage.getSessionType().getValue();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id='");
        sb.append(com.qiyukf.nimlib.database.a.d.a(sessionId));
        sb.append("' and sessiontype='");
        sb.append(value);
        sb.append("' and subtype=");
        sb.append(i2);
        if (iMMessage.getTime() > 0) {
            sb.append(" and time<'");
            sb.append(iMMessage.getTime());
            sb.append("'");
        }
        sb.append(" and msgtype='");
        sb.append(msgTypeEnum.getValue());
        sb.append("' ORDER BY time desc limit ");
        sb.append(i);
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static IMMessage queryMessageByUuid(String str) {
        if (com.qiyukf.nimlib.n.w.a((CharSequence) str)) {
            return null;
        }
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where uuid='" + str + "'");
        if (arrayListQueryMsgHistories == null || arrayListQueryMsgHistories.size() != 1) {
            return null;
        }
        return arrayListQueryMsgHistories.get(0);
    }

    public static IMMessage queryMessageBySeqId(long j) {
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where messageid='" + j + "'");
        if (arrayListQueryMsgHistories == null || arrayListQueryMsgHistories.size() != 1) {
            return null;
        }
        return arrayListQueryMsgHistories.get(0);
    }

    public static ArrayList<IMMessage> queryMessageByPage(int i, int i2, boolean z) {
        StringBuilder sb = new StringBuilder("select ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" from msghistory order by messageid  ");
        sb.append(z ? "asc" : SocialConstants.PARAM_APP_DESC);
        sb.append(" limit ");
        sb.append(i);
        sb.append(" offset ");
        sb.append(i2);
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static long queryMessageIdByUuid(String str) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT messageid FROM msghistory where uuid='" + str + "'");
        if (cursorRawQuery != null) {
            j = cursorRawQuery.moveToNext() ? cursorRawQuery.getLong(0) : 0L;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return j;
    }

    public static int queryStatus(String str, boolean z) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT " + (z ? "status2" : "status") + " FROM msghistory where uuid='" + str + "'");
        if (cursorRawQuery != null) {
            i = cursorRawQuery.moveToNext() ? cursorRawQuery.getInt(0) : 0;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return i;
    }

    public static IMMessage queryLatestMessageFilterMsgType(String str, int i, List<Integer> list) {
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where id='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + i + "' and msgtype not in (" + com.qiyukf.nimlib.n.e.e(list) + ChineseToPinyinResource.Field.RIGHT_BRACKET + " ORDER BY time desc limit 1 offset 0");
        if (arrayListQueryMsgHistories.size() == 1) {
            return arrayListQueryMsgHistories.get(0);
        }
        return null;
    }

    public static List<IMMessage> searchMessageHistory(String str, List<String> list, IMMessage iMMessage, QueryDirectionEnum queryDirectionEnum, int i) {
        String sessionId = iMMessage.getSessionId();
        int value = iMMessage.getSessionType().getValue();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id='");
        sb.append(com.qiyukf.nimlib.database.a.d.a(sessionId));
        sb.append("' and sessiontype='");
        sb.append(value);
        sb.append("'");
        boolean z = queryDirectionEnum == QueryDirectionEnum.QUERY_NEW;
        if (iMMessage.getTime() > 0) {
            if (z) {
                sb.append(" and time>'");
                sb.append(iMMessage.getTime());
                sb.append("'");
            } else {
                sb.append(" and time<'");
                sb.append(iMMessage.getTime());
                sb.append("'");
            }
        }
        sb.append(" and (");
        if (list != null && list.size() > 0) {
            sb.append("fromid in (");
            for (String str2 : list) {
                sb.append("'");
                sb.append(com.qiyukf.nimlib.database.a.d.a(str2));
                sb.append("',");
            }
            sb.replace(sb.length() - 1, sb.length(), ") or");
        }
        sb.append(" content like ");
        sb.append(com.qiyukf.nimlib.database.a.d.b(str));
        sb.append(") ORDER BY time ");
        sb.append(z ? "ASC" : "DESC");
        sb.append(" limit ");
        sb.append(i);
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static List<IMMessage> searchAllMessageHistory(String str, List<String> list, long j, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where 1=1");
        if (j > 0) {
            sb.append(" and time<'");
            sb.append(j);
            sb.append("'");
        }
        sb.append(" and (");
        if (list != null && list.size() > 0) {
            sb.append("fromid in (");
            for (String str2 : list) {
                sb.append("'");
                sb.append(com.qiyukf.nimlib.database.a.d.a(str2));
                sb.append("',");
            }
            sb.replace(sb.length() - 1, sb.length(), ") or");
        }
        sb.append(" content like ");
        sb.append(com.qiyukf.nimlib.database.a.d.b(str));
        sb.append(") ORDER BY time desc limit ");
        sb.append(i);
        return MsgDBHelperInternal.queryMsgHistories(sb.toString());
    }

    public static List<IMMessage> searchMessage(SessionTypeEnum sessionTypeEnum, String str, MsgSearchOption msgSearchOption) {
        com.qiyukf.nimlib.log.b.q(String.format("searchMessage sessionType = %s,sessionId = %s MsgSearchOption = %s", sessionTypeEnum, str, msgSearchOption));
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id='");
        sb.append(com.qiyukf.nimlib.database.a.d.a(str));
        sb.append("' and sessiontype='");
        sb.append(sessionTypeEnum.getValue());
        sb.append("'");
        long startTime = msgSearchOption.getStartTime();
        long endTime = msgSearchOption.getEndTime() == 0 ? Long.MAX_VALUE : msgSearchOption.getEndTime();
        sb.append(" and time>");
        sb.append(startTime);
        sb.append(" and time<");
        sb.append(endTime);
        boolean zIsAllMessageTypes = msgSearchOption.isAllMessageTypes();
        List<MsgTypeEnum> messageTypes = msgSearchOption.getMessageTypes();
        if (!zIsAllMessageTypes) {
            if (com.qiyukf.nimlib.n.e.b((Collection) messageTypes)) {
                messageTypes = new ArrayList<>();
                messageTypes.add(MsgTypeEnum.text);
            }
            sb.append(" and msgtype in (");
            sb.append(messageTypes.get(0).getValue());
            if (messageTypes.size() > 1) {
                for (int i = 1; i < messageTypes.size(); i++) {
                    sb.append(ChineseToPinyinResource.Field.COMMA);
                    sb.append(messageTypes.get(i).getValue());
                }
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        List<Integer> messageSubTypes = msgSearchOption.getMessageSubTypes();
        if (!com.qiyukf.nimlib.n.e.b((Collection) messageSubTypes)) {
            sb.append(" and subtype in (");
            sb.append(messageSubTypes.get(0));
            if (messageSubTypes.size() > 1) {
                for (int i2 = 1; i2 < messageSubTypes.size(); i2++) {
                    sb.append(ChineseToPinyinResource.Field.COMMA);
                    sb.append(messageSubTypes.get(i2));
                }
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        String strD = msgSearchOption.isEnableContentTransfer() ? com.qiyukf.nimlib.database.a.d.d(msgSearchOption.getSearchContent()) : msgSearchOption.getSearchContent();
        List<String> fromIds = msgSearchOption.getFromIds();
        if (!TextUtils.isEmpty(strD) || !com.qiyukf.nimlib.n.e.b((Collection) fromIds)) {
            sb.append(" and (");
            boolean zIsEmpty = TextUtils.isEmpty(strD);
            if (!zIsEmpty) {
                sb.append("content like ");
                sb.append(com.qiyukf.nimlib.database.a.d.b(strD));
            }
            if (fromIds != null && fromIds.size() > 0) {
                if (!zIsEmpty) {
                    sb.append(" or ");
                }
                sb.append("fromid in ('");
                sb.append(com.qiyukf.nimlib.database.a.d.a(fromIds.get(0)));
                sb.append("'");
                if (fromIds.size() > 1) {
                    for (int i3 = 1; i3 < fromIds.size(); i3++) {
                        sb.append(",'");
                        sb.append(com.qiyukf.nimlib.database.a.d.a(fromIds.get(i3)));
                        sb.append("'");
                    }
                }
                sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        boolean z = msgSearchOption.getOrder() == SearchOrderEnum.ASC;
        sb.append(" ORDER BY time ");
        sb.append(z ? "ASC" : "DESC");
        int limit = msgSearchOption.getLimit();
        if (limit > 0) {
            sb.append(" limit ");
            sb.append(limit);
        }
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString());
        if (!z && !arrayListQueryMsgHistories.isEmpty()) {
            Collections.reverse(arrayListQueryMsgHistories);
        }
        return arrayListQueryMsgHistories;
    }

    public static List<IMMessage> searchAllMessage(MsgSearchOption msgSearchOption) {
        com.qiyukf.nimlib.log.b.q(String.format("searchAllMessage MsgSearchOption = %s", msgSearchOption));
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where 1=1");
        long startTime = msgSearchOption.getStartTime();
        long endTime = msgSearchOption.getEndTime() == 0 ? Long.MAX_VALUE : msgSearchOption.getEndTime();
        sb.append(" and time>");
        sb.append(startTime);
        sb.append(" and time<");
        sb.append(endTime);
        boolean zIsAllMessageTypes = msgSearchOption.isAllMessageTypes();
        List<MsgTypeEnum> messageTypes = msgSearchOption.getMessageTypes();
        if (!zIsAllMessageTypes) {
            if (com.qiyukf.nimlib.n.e.b((Collection) messageTypes)) {
                messageTypes = new ArrayList<>();
                messageTypes.add(MsgTypeEnum.text);
            }
            sb.append(" and msgtype in (");
            sb.append(messageTypes.get(0).getValue());
            if (messageTypes.size() > 1) {
                for (int i = 1; i < messageTypes.size(); i++) {
                    sb.append(ChineseToPinyinResource.Field.COMMA);
                    sb.append(messageTypes.get(i).getValue());
                }
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        List<Integer> messageSubTypes = msgSearchOption.getMessageSubTypes();
        if (!com.qiyukf.nimlib.n.e.b((Collection) messageSubTypes)) {
            sb.append(" and subtype in (");
            sb.append(messageSubTypes.get(0));
            if (messageSubTypes.size() > 1) {
                for (int i2 = 1; i2 < messageSubTypes.size(); i2++) {
                    sb.append(ChineseToPinyinResource.Field.COMMA);
                    sb.append(messageSubTypes.get(i2));
                }
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        String strD = msgSearchOption.isEnableContentTransfer() ? com.qiyukf.nimlib.database.a.d.d(msgSearchOption.getSearchContent()) : msgSearchOption.getSearchContent();
        List<String> fromIds = msgSearchOption.getFromIds();
        if (!TextUtils.isEmpty(strD) || !com.qiyukf.nimlib.n.e.b((Collection) fromIds)) {
            sb.append(" and (");
            boolean zIsEmpty = TextUtils.isEmpty(strD);
            if (!zIsEmpty) {
                sb.append("content like ");
                sb.append(com.qiyukf.nimlib.database.a.d.b(strD));
            }
            if (fromIds != null && fromIds.size() > 0) {
                if (!zIsEmpty) {
                    sb.append(" or ");
                }
                sb.append("fromid in ('");
                sb.append(com.qiyukf.nimlib.database.a.d.a(fromIds.get(0)));
                sb.append("'");
                if (fromIds.size() > 1) {
                    for (int i3 = 1; i3 < fromIds.size(); i3++) {
                        sb.append(",'");
                        sb.append(com.qiyukf.nimlib.database.a.d.a(fromIds.get(i3)));
                        sb.append("'");
                    }
                }
                sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        boolean z = msgSearchOption.getOrder() == SearchOrderEnum.ASC;
        sb.append(" ORDER BY time ");
        sb.append(z ? "ASC" : "DESC");
        int limit = msgSearchOption.getLimit();
        if (limit > 0) {
            sb.append(" limit ");
            sb.append(limit);
        }
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString());
        if (!z && !arrayListQueryMsgHistories.isEmpty()) {
            Collections.reverse(arrayListQueryMsgHistories);
        }
        return arrayListQueryMsgHistories;
    }

    public static List<RecentContact> queryRecentContacts(int i) {
        return MsgDBHelperInternal.queryRecentContacts("select uid,fromuid,messageId,msgstatus,unreadnum,content,time,sessiontype,tag,msgtype,attach,extension from lstmsg order by time desc limit ".concat(String.valueOf(i)));
    }

    public static s queryRecentContact(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperInternal.queryRecentContact("select uid,fromuid,messageId,msgstatus,unreadnum,content,time,sessiontype,tag,msgtype,attach,extension from lstmsg where uid='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + sessionTypeEnum.getValue() + "'");
    }

    public static long queryRoamMsgHasMoreTime(String str, SessionTypeEnum sessionTypeEnum) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery(String.format("SELECT time FROM roam_msg_has_more WHERE session_id='%s' AND session_type='%s'", com.qiyukf.nimlib.database.a.d.a(str), Integer.valueOf(sessionTypeEnum.getValue())));
        if (cursorRawQuery != null) {
            j = cursorRawQuery.moveToNext() ? cursorRawQuery.getLong(0) : 0L;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        com.qiyukf.nimlib.log.b.q(String.format("queryRoamMsgHasMoreTime(%s, %s): %s", str, sessionTypeEnum, Long.valueOf(j)));
        return j;
    }

    public static long queryRoamMsgHasMoreServerId(String str, SessionTypeEnum sessionTypeEnum) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery(String.format("SELECT serverid FROM roam_msg_has_more WHERE session_id='%s' AND session_type='%s'", com.qiyukf.nimlib.database.a.d.a(str), Integer.valueOf(sessionTypeEnum.getValue())));
        if (cursorRawQuery != null) {
            j = cursorRawQuery.moveToNext() ? cursorRawQuery.getLong(0) : 0L;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        com.qiyukf.nimlib.log.b.q(String.format("queryRoamMsgHasMoreTime(%s, %s): %s", str, sessionTypeEnum, Long.valueOf(j)));
        return j;
    }

    public static int queryReplyCount(String str, String str2, SessionTypeEnum sessionTypeEnum) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT COUNT(1) FROM msghistory WHERE threadmsgidclient='" + com.qiyukf.nimlib.database.a.d.a(str) + "' AND id='" + com.qiyukf.nimlib.database.a.d.a(str2) + "' AND sessiontype=" + sessionTypeEnum.getValue());
        int i = 0;
        if (cursorRawQuery != null) {
            try {
                if (cursorRawQuery.moveToNext()) {
                    i = cursorRawQuery.getInt(0);
                }
            } catch (Throwable th) {
                try {
                    cursorRawQuery.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (cursorRawQuery != null) {
            cursorRawQuery.close();
        }
        return i;
    }

    public static List<IMMessage> queryReplyMsgList(String str, String str2, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory WHERE threadmsgidclient='" + com.qiyukf.nimlib.database.a.d.a(str) + "' AND id='" + com.qiyukf.nimlib.database.a.d.a(str2) + "' AND sessiontype=" + sessionTypeEnum.getValue() + " ORDER BY time ASC");
    }

    public static ArrayList<QuickCommentOption> queryQuickCommentByUuid(String str) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery(String.format("SELECT operator, type, time, ext FROM quick_comment WHERE uuid='%s'", com.qiyukf.nimlib.database.a.d.a(str)));
        ArrayList<QuickCommentOption> arrayList = new ArrayList<>();
        if (cursorRawQuery == null) {
            return arrayList;
        }
        while (cursorRawQuery.moveToNext()) {
            arrayList.add(new QuickCommentOption(cursorRawQuery.getString(0), cursorRawQuery.getLong(1), cursorRawQuery.getLong(2), cursorRawQuery.getString(3)));
        }
        if (!cursorRawQuery.isClosed()) {
            cursorRawQuery.close();
        }
        return arrayList;
    }

    public static List<MsgPinDbOption> queryMsgPin(String str) {
        if (TextUtils.isEmpty(str)) {
            return new ArrayList(0);
        }
        return MsgDBHelperInternal.readMsgPinDbOptionList(String.format("SELECT uuid, session_id, operator, ext, create_time, update_time FROM msg_pin WHERE session_id='%s'", com.qiyukf.nimlib.database.a.d.a(str)), -1);
    }

    public static boolean isStickTopSession(String str, SessionTypeEnum sessionTypeEnum) {
        boolean z = false;
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery(String.format("SELECT COUNT(1) FROM session_stick_top WHERE session_id='%s' AND session_type=%s", com.qiyukf.nimlib.database.a.d.a(str), Integer.valueOf(sessionTypeEnum.getValue())));
        if (cursorRawQuery == null) {
            return false;
        }
        if (cursorRawQuery.moveToNext() && cursorRawQuery.getInt(0) > 0) {
            z = true;
        }
        if (!cursorRawQuery.isClosed()) {
            cursorRawQuery.close();
        }
        return z;
    }

    public static void updateSyncSelfMessageStatus(List<d> list) {
        if (list == null || list.isEmpty()) {
            com.qiyukf.nimlib.log.b.F("updateSyncSelfMessageStatus msgList is empty");
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (d dVar : list) {
                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE msghistory set");
                if (dVar.getStatus() != null) {
                    sb.append(" status='");
                    sb.append(dVar.getStatus().getValue());
                    sb.append("',");
                }
                if (dVar.getTime() > 0) {
                    sb.append(" time='");
                    sb.append(dVar.getTime());
                    sb.append("',");
                }
                if (dVar.getServerId() > 0) {
                    sb.append(" serverid='");
                    sb.append(dVar.getServerId());
                    sb.append("',");
                }
                sb.append(" isblacked='");
                sb.append(dVar.q());
                sb.append("',");
                sb.deleteCharAt(sb.length() - 1);
                sb.append(" where uuid='");
                sb.append(dVar.getUuid());
                sb.append("'");
                MsgDBHelperUtils.database().a(sb.toString());
                com.qiyukf.nimlib.log.b.F("updateSyncSelfMessageStatus update uuid = " + dVar.getUuid());
            }
            MsgDBHelperUtils.database().h();
            com.qiyukf.nimlib.log.b.F("updateSyncSelfMessageStatus update success");
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void updateMessageStatus(com.qiyukf.nimlib.session.d r7) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "UPDATE msghistory set"
            r0.append(r1)
            com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum r1 = r7.getStatus()
            java.lang.String r2 = "',"
            r3 = 1
            r4 = 0
            if (r1 == 0) goto L29
            java.lang.String r1 = " status='"
            r0.append(r1)
            com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum r1 = r7.getStatus()
            int r1 = r1.getValue()
            r0.append(r1)
            r0.append(r2)
            r1 = 1
            goto L2a
        L29:
            r1 = 0
        L2a:
            com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum r5 = r7.getAttachStatus()
            if (r5 == 0) goto L44
            java.lang.String r1 = " status2='"
            r0.append(r1)
            com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum r1 = r7.getAttachStatus()
            int r1 = r1.getValue()
            r0.append(r1)
            r0.append(r2)
            r1 = 1
        L44:
            com.qiyukf.nimlib.sdk.msg.attachment.MsgAttachment r5 = r7.getAttachment()
            java.lang.String r6 = r7.d()
            if (r5 == 0) goto L6c
            java.lang.String r1 = r5.toJson(r4)
            boolean r4 = java.util.Objects.equals(r6, r1)
            if (r4 != 0) goto L5b
            r7.e(r1)
        L5b:
            java.lang.String r4 = " attach='"
            r0.append(r4)
            java.lang.String r1 = com.qiyukf.nimlib.database.a.d.a(r1)
            r0.append(r1)
            r0.append(r2)
        L6a:
            r1 = 1
            goto L78
        L6c:
            boolean r2 = android.text.TextUtils.isEmpty(r6)
            if (r2 != 0) goto L78
            java.lang.String r1 = " attach=null,"
            r0.append(r1)
            goto L6a
        L78:
            if (r1 == 0) goto L9e
            int r1 = r0.length()
            int r1 = r1 - r3
            r0.deleteCharAt(r1)
            java.lang.String r1 = " where uuid='"
            r0.append(r1)
            java.lang.String r7 = r7.getUuid()
            r0.append(r7)
            java.lang.String r7 = "'"
            r0.append(r7)
            com.qiyukf.nimlib.database.d r7 = com.qiyukf.nimlib.session.MsgDBHelperUtils.database()
            java.lang.String r0 = r0.toString()
            r7.a(r0)
        L9e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.session.MsgDBHelperA.updateMessageStatus(com.qiyukf.nimlib.session.d):void");
    }

    public static void updateMessageLocalExt(d dVar) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set localext='" + dVar.j() + "' where messageid='" + dVar.a() + "'");
    }

    public static void updateMessageCallbackExt(long j, String str) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set callbackext='" + str + "' where messageid='" + j + "'");
    }

    public static void updateMessageStatus(long j, int i) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set status='" + i + "' where messageid='" + j + "'");
    }

    public static void updateAttachStatus(String str, int i) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set status2='" + i + "' where uuid='" + com.qiyukf.nimlib.database.a.d.a(str) + "'");
    }

    public static void setRecentStatus(String str, int i, long j) {
        MsgDBHelperUtils.database().a(j <= 0 ? String.format("UPDATE lstmsg set msgstatus='%s' where messageId='%s'", Integer.valueOf(i), str) : String.format("UPDATE lstmsg set msgstatus='%s',time='%s' where messageId='%s'", Integer.valueOf(i), Long.valueOf(j), str));
    }

    public static void setRecentRead(String str, int i) {
        MsgDBHelperUtils.database().a("update lstmsg set unreadnum = 0 where uid='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + i + "'");
    }

    public static void updateRecentUnreadNum(String str, SessionTypeEnum sessionTypeEnum, int i) {
        MsgDBHelperUtils.database().a("update lstmsg set unreadnum=" + i + " where uid='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + sessionTypeEnum.getValue() + "'");
    }

    public static void updateRecent(RecentContact recentContact) {
        MsgDBHelperUtils.database().a("UPDATE lstmsg set tag='" + recentContact.getTag() + "',extension='" + com.qiyukf.nimlib.database.a.d.a(k.a(recentContact.getExtension())) + "' where uid='" + com.qiyukf.nimlib.database.a.d.a(recentContact.getContactId()) + "' and sessiontype='" + recentContact.getSessionType().getValue() + "'");
    }

    public static void updateRoamMsgHasMoreTime(RoamMsgHasMoreOption roamMsgHasMoreOption) {
        MsgDBHelperUtils.database().a("UPDATE roam_msg_has_more SET time='" + roamMsgHasMoreOption.getTime() + "', serverid='" + roamMsgHasMoreOption.getServerId() + "' WHERE session_id='" + roamMsgHasMoreOption.getSessionId() + "' AND session_type='" + roamMsgHasMoreOption.getSessionType().getValue() + "'");
        com.qiyukf.nimlib.log.b.q(String.format("updateRoamMsgHasMoreTime(%s)", roamMsgHasMoreOption));
    }

    public static void updateCollectInfo(a aVar) {
        if (aVar == null) {
            return;
        }
        MsgDBHelperUtils.database().a("UPDATE collect_info SET type='" + aVar.getType() + "', data='" + com.qiyukf.nimlib.database.a.d.a(aVar.getData()) + "', ext='" + com.qiyukf.nimlib.database.a.d.a(aVar.getExt()) + "', uniqueId='" + com.qiyukf.nimlib.database.a.d.a(aVar.getUniqueId()) + "', createTime='" + aVar.getCreateTime() + "', updateTime='" + aVar.getUpdateTime() + "' WHERE id='" + aVar.getId() + "'");
    }

    public static void updateMsgPin(String str, String str2, String str3, long j) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        MsgDBHelperUtils.database().a("UPDATE msg_pin SET ext='" + com.qiyukf.nimlib.database.a.d.a(str3) + "', update_time='" + j + "' WHERE uuid='" + com.qiyukf.nimlib.database.a.d.a(str) + "' AND session_id='" + com.qiyukf.nimlib.database.a.d.a(str2) + "'");
    }

    public static void updateStickTopSession(String str, SessionTypeEnum sessionTypeEnum, String str2, long j) {
        if (sessionTypeEnum == null || TextUtils.isEmpty(str)) {
            return;
        }
        MsgDBHelperUtils.database().a("UPDATE session_stick_top SET ext='" + com.qiyukf.nimlib.database.a.d.a(str2) + "', update_time='" + j + "' WHERE session_id='" + com.qiyukf.nimlib.database.a.d.a(str) + "' AND session_type='" + sessionTypeEnum.getValue() + "'");
    }

    public static int deleteMessage(d dVar, boolean z) {
        if (dVar == null) {
            return 0;
        }
        int iA = MsgDBHelperUtils.database().a("msghistory", String.format("uuid='%s'", dVar.getUuid()));
        if (z) {
            MsgDBHelperInternal.recordDelete(dVar.getUuid(), dVar.getSessionId(), dVar.getSessionType());
        }
        com.qiyukf.nimlib.search.b bVar = b.a.a;
        if (iA != 0 && iA != 1) {
            com.qiyukf.nimlib.log.c.b.a.H("warn: delete one msg but result is ".concat(String.valueOf(iA)));
        }
        return iA;
    }

    public static int deleteMessage(List<? extends IMMessage> list, boolean z) {
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            return 0;
        }
        int iA = MsgDBHelperUtils.database().a("msghistory", String.format("uuid IN (%s)", MsgDBHelperUtils.toString(list, new MsgDBHelperUtils.IGetString() { // from class: com.qiyukf.nimlib.session.MsgDBHelperA$$ExternalSyntheticLambda1
            @Override // com.qiyukf.nimlib.session.MsgDBHelperUtils.IGetString
            public final String getString(Object obj) {
                return MsgDBHelperA.lambda$deleteMessage$0((IMMessage) obj);
            }
        })));
        for (IMMessage iMMessage : list) {
            if (iMMessage instanceof d) {
                if (z) {
                    MsgDBHelperInternal.recordDelete(iMMessage.getUuid(), iMMessage.getSessionId(), iMMessage.getSessionType());
                }
                com.qiyukf.nimlib.search.b bVar = b.a.a;
            }
        }
        return iA;
    }

    public static /* synthetic */ String lambda$deleteMessage$0(IMMessage iMMessage) {
        return String.format("'%s'", iMMessage.getUuid());
    }

    public static void clearMessage(String str, SessionTypeEnum sessionTypeEnum, boolean z) {
        MsgDBHelperUtils.database().a("DELETE FROM msghistory where (id='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + sessionTypeEnum.getValue() + "')");
        if (z) {
            MsgDBHelperInternal.recordClearContact(str, sessionTypeEnum, System.currentTimeMillis());
        }
        com.qiyukf.nimlib.search.b bVar = b.a.a;
    }

    public static void deleteRecentContact(String str, SessionTypeEnum sessionTypeEnum) {
        MsgDBHelperUtils.database().a("DELETE FROM lstmsg where uid = '" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + sessionTypeEnum.getValue() + "'");
    }

    public static void deleteRoamMsgHasMoreTime(String str, SessionTypeEnum sessionTypeEnum) {
        MsgDBHelperUtils.database().a("DELETE FROM roam_msg_has_more where session_id='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and session_type='" + sessionTypeEnum.getValue() + "'");
        com.qiyukf.nimlib.log.b.q(String.format("deleteRoamMsgHasMoreTime(%s, %s)", str, sessionTypeEnum));
    }

    public static void deleteQuickComment(String str, String str2, long j) {
        MsgDBHelperUtils.database().a("DELETE FROM quick_comment where uuid='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and operator='" + com.qiyukf.nimlib.database.a.d.a(str2) + "' and type=" + j);
    }

    public static void deleteQuickComment(String str) {
        MsgDBHelperUtils.database().a("DELETE FROM quick_comment where uuid='" + com.qiyukf.nimlib.database.a.d.a(str) + "'");
    }

    public static void deleteCollectInfo(List<Long> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Long l : list) {
            if (l != null) {
                sb.append(", ");
                sb.append("'");
                sb.append(l);
                sb.append("'");
            }
        }
        MsgDBHelperUtils.database().a(String.format("DELETE FROM collect_info where id IN (%s)", sb.substring(2)));
    }

    public static void deleteMsgPin(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        MsgDBHelperUtils.database().a(String.format("DELETE FROM msg_pin WHERE uuid='%s' AND session_id='%s'", str, str2));
    }

    public static void deleteMsgPin(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        MsgDBHelperUtils.database().a(String.format("DELETE FROM msg_pin WHERE session_id='%s'", str));
    }

    public static void deleteStickTopSession(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        MsgDBHelperUtils.database().a(String.format("DELETE FROM session_stick_top WHERE session_id='%s'", str));
    }

    public static boolean hasDeleteTag(String str) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT COUNT(1) FROM delete_message_record WHERE uuid='" + com.qiyukf.nimlib.database.a.d.a(str) + "'");
        return cursorRawQuery != null && cursorRawQuery.moveToNext() && cursorRawQuery.getLong(0) > 0;
    }

    public static Set<String> hasDeleteTag(Collection<d> collection) {
        HashSet hashSet = new HashSet();
        if (collection != null && !collection.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (d dVar : collection) {
                if (dVar != null) {
                    String uuid = dVar.getUuid();
                    if (!TextUtils.isEmpty(uuid)) {
                        sb.append(", ");
                        sb.append("'");
                        sb.append(uuid);
                        sb.append("'");
                    }
                }
            }
            Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT uuid FROM delete_message_record WHERE uuid IN (" + sb.substring(2) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
            if (cursorRawQuery != null) {
                while (cursorRawQuery.moveToNext()) {
                    hashSet.add(cursorRawQuery.getString(0));
                }
                if (!cursorRawQuery.isClosed()) {
                    cursorRawQuery.close();
                }
            }
        }
        return hashSet;
    }

    public static long getClearSessionTime(String str, SessionTypeEnum sessionTypeEnum) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT time FROM clear_message_record WHERE session_id='" + str + "' AND session_type=" + sessionTypeEnum.getValue());
        if (cursorRawQuery == null || !cursorRawQuery.moveToNext()) {
            return 0L;
        }
        return cursorRawQuery.getLong(0);
    }

    public static ArrayList<SystemMessage> querySystemMessages(int i, int i2) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT messageid, id, fromid, type, time, status, content, attach, unread FROM system_msg where type!=6 ORDER BY time desc LIMIT " + i2 + " OFFSET " + i);
        ArrayList<SystemMessage> arrayList = new ArrayList<>();
        if (cursorRawQuery != null) {
            while (cursorRawQuery.moveToNext()) {
                arrayList.add(MsgDBHelperCursorTransfer.systemMsgFromCursor(cursorRawQuery));
            }
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return arrayList;
    }

    public static ArrayList<SystemMessage> querySystemMessage(List<SystemMessageType> list, int i, int i2) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT messageid, id, fromid, type, time, status, content, attach, unread FROM system_msg where type in(" + MsgDBHelperUtils.buildTypeSql(list) + ") ORDER BY time desc LIMIT " + i2 + " OFFSET " + i);
        ArrayList<SystemMessage> arrayList = new ArrayList<>();
        if (cursorRawQuery != null) {
            while (cursorRawQuery.moveToNext()) {
                arrayList.add(MsgDBHelperCursorTransfer.systemMsgFromCursor(cursorRawQuery));
            }
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return arrayList;
    }

    public static void setSystemMessageStatus(long j, SystemMessageStatus systemMessageStatus) {
        MsgDBHelperUtils.database().a("UPDATE system_msg SET status='" + systemMessageStatus.getValue() + "' where messageid='" + j + "'");
    }

    public static void setAllSystemMessageRead(List<SystemMessageType> list) {
        MsgDBHelperUtils.database().a("UPDATE system_msg SET unread='0' where type in(" + MsgDBHelperUtils.buildTypeSql(list) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
    }

    public static void setSystemMessageRead(long j) {
        MsgDBHelperUtils.database().a("UPDATE system_msg SET unread='0' where messageid='" + j + "'");
    }

    public static int querySystemMessageUnreadNum(List<SystemMessageType> list) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT count(*) FROM system_msg where unread=='1' and type in(" + MsgDBHelperUtils.buildTypeSql(list) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
        if (cursorRawQuery != null) {
            i = cursorRawQuery.moveToNext() ? cursorRawQuery.getInt(0) : 0;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return i;
    }

    public static void deleteSystemMessage(long j) {
        MsgDBHelperUtils.database().a("DELETE FROM system_msg where messageid='" + j + "'");
    }

    public static void clearSystemMessages(List<SystemMessageType> list) {
        MsgDBHelperUtils.database().a("DELETE FROM system_msg where type in(" + MsgDBHelperUtils.buildTypeSql(list) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
    }

    public static void saveMessageReceipt(List<g> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            g gVar = list.get(i);
            if (sb.length() == 0) {
                sb.append(" select '");
            } else {
                sb.append(" union select '");
            }
            sb.append(com.qiyukf.nimlib.database.a.d.a(gVar.a));
            sb.append("','");
            sb.append(gVar.b);
            sb.append("','");
            sb.append(gVar.c);
            sb.append("'");
            if (sb.length() > 10000) {
                MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO message_receipt (session_id,time,max_time)" + ((Object) sb));
                sb = new StringBuilder();
            }
        }
        if (sb.length() > 0) {
            MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO message_receipt (session_id,time,max_time)" + ((Object) sb));
        }
    }

    public static Map<String, g> queryMessageReceipt(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT session_id,time,max_time FROM message_receipt where session_id in(" + MsgDBHelperUtils.buildSessionIdsSql(list) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
        HashMap map = new HashMap(list.size());
        if (cursorRawQuery != null) {
            while (cursorRawQuery.moveToNext()) {
                g receiptFromCursor = MsgDBHelperCursorTransfer.readReceiptFromCursor(cursorRawQuery);
                map.put(receiptFromCursor.a, receiptFromCursor);
            }
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return map;
    }

    public static void saveSendReceiptRecord(MessageReceipt messageReceipt) {
        MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO send_receipt_record (session_id,time) values ('" + com.qiyukf.nimlib.database.a.d.a(messageReceipt.getSessionId()) + "','" + messageReceipt.getTime() + "')");
    }

    public static long getSessionLastReceivedMsgTimeTag(String str, SessionTypeEnum sessionTypeEnum) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT max(time) FROM msghistory where id='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + sessionTypeEnum.getValue() + "' and direct=1");
        if (cursorRawQuery != null) {
            j = cursorRawQuery.moveToNext() ? cursorRawQuery.getLong(0) : 0L;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return j;
    }

    public static void saveSessionReadRecord(String str, SessionTypeEnum sessionTypeEnum, long j) {
        com.qiyukf.nimlib.log.b.t("save session record: sessionId=" + str + ", timetag=" + j);
        MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO session_read_record (session_id,session_type,time) values ('" + com.qiyukf.nimlib.database.a.d.a(str) + "','" + sessionTypeEnum.getValue() + "','" + j + "')");
    }

    public static long querySessionReadTimeTag(String str, SessionTypeEnum sessionTypeEnum) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery(String.format("SELECT time FROM session_read_record where session_id='%s' and session_type='%s'", com.qiyukf.nimlib.database.a.d.a(str), Integer.valueOf(sessionTypeEnum.getValue())));
        if (cursorRawQuery != null) {
            j = cursorRawQuery.moveToNext() ? cursorRawQuery.getLong(0) : 0L;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return j;
    }

    public static ArrayList<IMMessage> queryUnreadMessages(String str, SessionTypeEnum sessionTypeEnum, long j) {
        return MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where id='" + com.qiyukf.nimlib.database.a.d.a(str) + "' and sessiontype='" + sessionTypeEnum.getValue() + "' and direct='1' and time > " + j);
    }

    public static void saveSenderNick(String str, String str2) {
        MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO sender_nick (account,nick) values ('" + com.qiyukf.nimlib.database.a.d.a(str) + "','" + com.qiyukf.nimlib.database.a.d.a(str2) + "')");
    }

    public static void saveSenderNickMap(Map<String, String> map) {
        StringBuilder sb = new StringBuilder("saveSenderNickMap count = ");
        sb.append(map == null ? 0 : map.size());
        com.qiyukf.nimlib.log.b.F(sb.toString());
        if (map == null || map.isEmpty()) {
            return;
        }
        MsgDBHelperUtils.database().f();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO sender_nick (account,nick) values ('" + com.qiyukf.nimlib.database.a.d.a(entry.getKey()) + "','" + com.qiyukf.nimlib.database.a.d.a(entry.getValue()) + "')");
            }
            MsgDBHelperUtils.database().h();
            com.qiyukf.nimlib.log.b.F("saveSenderNickMap success");
            MsgDBHelperUtils.database().g();
        } catch (Throwable th) {
            MsgDBHelperUtils.database().g();
            throw th;
        }
    }

    public static Map<String, String> queryAllSenderNick() {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT account,nick FROM sender_nick");
        HashMap map = new HashMap();
        if (cursorRawQuery != null) {
            while (cursorRawQuery.moveToNext()) {
                map.put(cursorRawQuery.getString(0), cursorRawQuery.getString(1));
            }
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return map;
    }

    public static void saveRevokeMessage(String str) {
        MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO revoke_message (uuid) values ('" + com.qiyukf.nimlib.database.a.d.a(str) + "')");
    }

    public static String queryRevokeMessage(String str) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT uuid FROM revoke_message where uuid='" + str + "'");
        if (cursorRawQuery != null) {
            string = cursorRawQuery.moveToNext() ? cursorRawQuery.getString(0) : null;
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        }
        return string;
    }

    public static void markHasSendTeamMsgAck(List<String> list) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set acksend='1' where uuid in(" + MsgDBHelperUtils.buildSessionIdsSql(list) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
    }

    public static void updateTeamMsgAckCount(String str, int i, int i2) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set ackcount='" + i + "', unackcount='" + i2 + "' where uuid='" + str + "' and (ackcount<'" + i + "' or ackcount='0')");
    }

    public static void saveTeamMsgAckDetail(TeamMsgAckInfo teamMsgAckInfo, String str) {
        ArrayList arrayList = new ArrayList();
        if (teamMsgAckInfo.getAckAccountList() != null) {
            Iterator<String> it = teamMsgAckInfo.getAckAccountList().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toLowerCase());
            }
        }
        if (teamMsgAckInfo.getUnAckAccountList() != null) {
            Iterator<String> it2 = teamMsgAckInfo.getUnAckAccountList().iterator();
            while (it2.hasNext()) {
                arrayList.add(it2.next().toLowerCase());
            }
        }
        Collections.sort(arrayList);
        JSONArray jSONArray = new JSONArray();
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            jSONArray.put((String) it3.next());
        }
        ContentValues contentValues = new ContentValues(4);
        contentValues.put("msgid", teamMsgAckInfo.getMsgId());
        contentValues.put("tid", teamMsgAckInfo.getTeamId());
        contentValues.put("snapshot", jSONArray.toString());
        contentValues.put("bitmap", str);
        MsgDBHelperUtils.database().b("team_msg_ack", null, contentValues);
    }

    public static void updateTeamMsgAckDetail(String str, String str2) {
        MsgDBHelperUtils.database().a("UPDATE team_msg_ack set bitmap='" + str2 + "' where msgid='" + str + "'");
    }

    public static TeamMsgAckInfo queryTeamMsgAckDetail(String str) {
        String string;
        String string2;
        String string3;
        boolean z;
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT msgid,tid,snapshot,bitmap FROM team_msg_ack where msgid='" + str + "'");
        if (cursorRawQuery != null) {
            if (cursorRawQuery.moveToNext()) {
                z = true;
                string = cursorRawQuery.getString(1);
                string2 = cursorRawQuery.getString(2);
                string3 = cursorRawQuery.getString(3);
            } else {
                string = null;
                string2 = null;
                string3 = null;
                z = false;
            }
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
        } else {
            string = null;
            string2 = null;
            string3 = null;
            z = false;
        }
        if (z && string2 != null && string3 != null) {
            try {
                JSONArray jSONArray = new JSONArray(string2);
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                Pair<List<String>, List<String>> bitmap = MsgDBHelperUtils.parseBitmap(com.qiyukf.nimlib.n.i.a(string3), arrayList);
                return bitmap == null ? new TeamMsgAckInfo(string, str, (List<String>) null, (List<String>) null) : new TeamMsgAckInfo(string, str, (List<String>) bitmap.first, (List<String>) bitmap.second);
            } catch (JSONException e) {
                e.printStackTrace();
                com.qiyukf.nimlib.log.c.b.a.H("queryTeamMsgAckDetail parse error, e=" + e.getMessage());
            }
        }
        return null;
    }

    public static void deleteRangeHistory(String str, SessionTypeEnum sessionTypeEnum, long j, long j2) {
        MsgDBHelperUtils.database().a(String.format("DELETE FROM msghistory where(id='%s' and sessiontype='%s' and time> %s and time<%s)", com.qiyukf.nimlib.database.a.d.a(str), Integer.valueOf(sessionTypeEnum.getValue()), Long.valueOf(j), Long.valueOf(j2)));
        com.qiyukf.nimlib.search.b bVar = b.a.a;
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x0129 A[Catch: Exception -> 0x00e5, TRY_ENTER, TRY_LEAVE, TryCatch #7 {Exception -> 0x00e5, blocks: (B:101:0x00e1, B:121:0x0129), top: B:151:0x000d }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:104:0x00e6 -> B:147:0x012c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void migrateMessages(android.content.Context r11, java.lang.String r12, java.lang.String r13, boolean r14) {
        /*
            Method dump skipped, instruction units count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.session.MsgDBHelperA.migrateMessages(android.content.Context, java.lang.String, java.lang.String, boolean):void");
    }

    public static int removeSessionReliableInfo(@NonNull List<Long> list) {
        String str = String.format("id IN (%s)", com.qiyukf.nimlib.n.e.a(list, ChineseToPinyinResource.Field.COMMA, new e.a() { // from class: com.qiyukf.nimlib.session.MsgDBHelperA$$ExternalSyntheticLambda0
            @Override // com.qiyukf.nimlib.n.e.a
            public final Object transform(Object obj) {
                return String.valueOf((Long) obj);
            }
        }));
        com.qiyukf.nimlib.log.b.q(String.format("to remove session reliable info with whereClause %s", str));
        return MsgDBHelperUtils.database().a("session_reliable_table", str);
    }

    public static int removeSessionReliableInfo(@NonNull String str, @NonNull SessionTypeEnum sessionTypeEnum) {
        String str2 = String.format("session_id='%s' AND session_type=%s", com.qiyukf.nimlib.database.a.d.a(str), Integer.valueOf(sessionTypeEnum.getValue()));
        com.qiyukf.nimlib.log.b.q(String.format("to remove session reliable info with whereClause %s", str2));
        return MsgDBHelperUtils.database().a("session_reliable_table", str2);
    }

    public static com.qiyukf.nimlib.session.b.d queryLastSessionReliableInfo(@NonNull String str, @NonNull SessionTypeEnum sessionTypeEnum) {
        return (com.qiyukf.nimlib.session.b.d) com.qiyukf.nimlib.n.e.a((Collection) MsgDBHelperInternal.querySessionReliableInfos(String.format("SELECT id,%s FROM session_reliable_table WHERE session_id='%s' AND session_type=%s ORDER BY stop_time DESC LIMIT 1", "session_id, session_type, start_time, start_server_id, start_client_id, stop_time, stop_server_id, stop_client_id", com.qiyukf.nimlib.database.a.d.a(str), Integer.valueOf(sessionTypeEnum.getValue()))));
    }

    @NonNull
    public static List<com.qiyukf.nimlib.session.b.d> queryMayOverLappedInfos(@NonNull com.qiyukf.nimlib.session.b.d dVar) {
        ArrayList arrayList = new ArrayList();
        String strD = dVar.d();
        SessionTypeEnum sessionTypeEnumE = dVar.e();
        if (com.qiyukf.nimlib.n.w.a((CharSequence) strD) || sessionTypeEnumE == null) {
            return arrayList;
        }
        return MsgDBHelperInternal.querySessionReliableInfos(String.format("SELECT id,%s FROM session_reliable_table WHERE session_id='%s' AND session_type=%s AND start_time<=%s AND stop_time>=%s", "session_id, session_type, start_time, start_server_id, start_client_id, stop_time, stop_server_id, stop_client_id", com.qiyukf.nimlib.database.a.d.a(strD), Integer.valueOf(sessionTypeEnumE.getValue()), Long.valueOf(dVar.k()), Long.valueOf(dVar.h())));
    }

    @NonNull
    public static List<com.qiyukf.nimlib.session.b.d> queryParentInfos(@NonNull com.qiyukf.nimlib.session.b.d dVar) {
        ArrayList arrayList = new ArrayList();
        String strD = dVar.d();
        SessionTypeEnum sessionTypeEnumE = dVar.e();
        if (!com.qiyukf.nimlib.n.w.a((CharSequence) strD) && sessionTypeEnumE != null) {
            Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery(String.format("SELECT id,%s FROM session_reliable_table WHERE session_id='%s' AND session_type=%s AND ((start_time<%s OR (start_time=%s AND start_client_id='%s')) AND (stop_time>%s OR (stop_time=%s AND stop_client_id='%s')))", "session_id, session_type, start_time, start_server_id, start_client_id, stop_time, stop_server_id, stop_client_id", com.qiyukf.nimlib.database.a.d.a(strD), Integer.valueOf(sessionTypeEnumE.getValue()), Long.valueOf(dVar.h()), Long.valueOf(dVar.h()), dVar.g(), Long.valueOf(dVar.k()), Long.valueOf(dVar.k()), dVar.j()));
            if (cursorRawQuery != null) {
                while (cursorRawQuery.moveToNext()) {
                    arrayList.add(MsgDBHelperCursorTransfer.sessionReliableInfoFromCursor(cursorRawQuery));
                }
                if (!cursorRawQuery.isClosed()) {
                    cursorRawQuery.close();
                }
            }
            com.qiyukf.nimlib.log.b.q(String.format("query parent infos with %s. result is %s", dVar, com.qiyukf.nimlib.n.e.e(arrayList)));
        }
        return arrayList;
    }

    public static List<IMMessage> queryMsgListByUuid(List<String> list) {
        boolean z = true;
        com.qiyukf.nimlib.log.b.q(String.format("queryMsgListByUuid, uuid size is %s", Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            return new ArrayList(0);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("in ('");
        for (String str : list) {
            if (z) {
                sb.append(str);
                sb.append("'");
                z = false;
            } else {
                sb.append(", '");
                sb.append(str);
                sb.append("'");
            }
        }
        sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        return MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where uuid " + sb.toString());
    }

    public static Map<String, IMMessage> queryMsgMapByProperty(List<com.qiyukf.nimlib.push.packet.b.c> list) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMsgMapByProperty, msgProperty size is %s", Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        HashMap map = new HashMap();
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            com.qiyukf.nimlib.log.b.F("queryMsgMapByProperty msgProperty list is empty ");
            return map;
        }
        int size = list.size();
        com.qiyukf.nimlib.log.b.F("queryMsgMapByProperty msgProperty size = ".concat(String.valueOf(size)));
        if (size <= 200) {
            return MsgDBHelperInternal.queryMsgHistoriesMapFromProperty(list, map);
        }
        int i = size / 200;
        int i2 = size % 200;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i3 * 200;
            int i5 = i4 + 200;
            List<com.qiyukf.nimlib.push.packet.b.c> listSubList = list.subList(i4, i5);
            com.qiyukf.nimlib.log.b.a("queryMsgMapByProperty for i = %d,fromIndex = %d,toIndex = %d", Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
            MsgDBHelperInternal.queryMsgHistoriesMapFromProperty(listSubList, map);
        }
        if (i2 > 0) {
            int i6 = i * 200;
            int i7 = i2 + i6;
            List<com.qiyukf.nimlib.push.packet.b.c> listSubList2 = list.subList(i6, i7);
            com.qiyukf.nimlib.log.b.a("queryMsgMapByProperty lastFromIndex = %d,lastToIndex = %d", Integer.valueOf(i6), Integer.valueOf(i7));
            MsgDBHelperInternal.queryMsgHistoriesMapFromProperty(listSubList2, map);
        }
        return map;
    }

    public static List<IMMessage> queryMsgListByServerId(List<String> list) {
        boolean z = true;
        com.qiyukf.nimlib.log.b.q(String.format("queryMsgListByUuid, serverId size is %s", Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        StringBuilder sb = new StringBuilder();
        sb.append("in ('");
        for (String str : list) {
            if (z) {
                sb.append(str);
                sb.append("'");
                z = false;
            } else {
                sb.append(", '");
                sb.append(str);
                sb.append("'");
            }
        }
        sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        return MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where serverid " + sb.toString());
    }
}

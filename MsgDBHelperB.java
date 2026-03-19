package com.qiyukf.nimlib.session;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SystemMessageStatus;
import com.qiyukf.nimlib.sdk.msg.constant.SystemMessageType;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
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
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes6.dex */
public class MsgDBHelperB {
    public static String repeatPlaceholders(int i) {
        if (i <= 0) {
            return "";
        }
        return TextUtils.join(ChineseToPinyinResource.Field.COMMA, Collections.nCopies(i, "?"));
    }

    public static ArrayList<IMMessage> queryMessageList(String str, int i, long j, int i2) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageList(%s, %s, %s, %s)", str, Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(i2)));
        return MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where id=? and sessiontype=? ORDER BY time desc limit ? offset ?", new String[]{str, String.valueOf(i), String.valueOf(i2), String.valueOf(j)});
    }

    public static ArrayList<IMMessage> queryMessageListEx(List<MsgTypeEnum> list, d dVar, long j, QueryDirectionEnum queryDirectionEnum, int i, boolean z) {
        int i2;
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListEx(%s, %s, %s, %s, %s), types size is %s", list, d.a(dVar), Long.valueOf(j), queryDirectionEnum, Integer.valueOf(i), Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        String sessionId = dVar.getSessionId();
        int value = dVar.getSessionType().getValue();
        boolean z2 = dVar.a() > 0;
        boolean z3 = com.qiyukf.nimlib.n.e.b((Collection) list) || list.contains(dVar.getMsgType());
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory WHERE id=? AND sessiontype=?");
        arrayList.add(sessionId);
        arrayList.add(String.valueOf(value));
        boolean z4 = queryDirectionEnum == QueryDirectionEnum.QUERY_NEW;
        if (z4) {
            sb.append(" AND time>=?");
            arrayList.add(String.valueOf(dVar.getTime()));
        } else if (dVar.getTime() > 0) {
            sb.append(" AND time<=?");
            arrayList.add(String.valueOf(dVar.getTime()));
        }
        if (j > 0) {
            if (z4) {
                sb.append(" AND time<=?");
            } else {
                sb.append(" AND time>=?");
            }
            arrayList.add(String.valueOf(j));
        }
        if (list != null && !list.isEmpty()) {
            sb.append(" AND msgtype in(");
            sb.append(repeatPlaceholders(list.size()));
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            Iterator<MsgTypeEnum> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(String.valueOf(it.next().getValue()));
            }
        }
        int i3 = (!z ? z2 : z3 && z2) ? i : i + 1;
        sb.append(" ORDER BY time ");
        sb.append(z4 ? "ASC" : "DESC");
        sb.append(" LIMIT ?");
        arrayList.add(String.valueOf(i3));
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
        if ((z && !z3) || !z2) {
            return arrayListQueryMsgHistories;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= arrayListQueryMsgHistories.size()) {
                i2 = 0;
                break;
            }
            if (((d) arrayListQueryMsgHistories.get(i4)).a() == dVar.a()) {
                i2 = i4 + 1;
                break;
            }
            i4++;
        }
        if (i2 == 0) {
            return arrayListQueryMsgHistories;
        }
        for (int i5 = 0; i5 <= i2 - 1; i5++) {
            arrayListQueryMsgHistories.remove(0);
        }
        if (i2 <= 1) {
            return arrayListQueryMsgHistories;
        }
        sb.delete(sb.lastIndexOf(" ") + 1, sb.length());
        sb.append("?");
        arrayList.set(arrayList.size() - 1, String.valueOf(i));
        sb.append(" OFFSET ?");
        arrayList.add(String.valueOf(i2));
        return MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
    }

    public static List<IMMessage> queryMessageListByType(MsgTypeEnum msgTypeEnum, IMMessage iMMessage, int i) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListByType(%s, %s, %s)", msgTypeEnum, d.a(iMMessage), Integer.valueOf(i)));
        String sessionId = iMMessage.getSessionId();
        int value = iMMessage.getSessionType().getValue();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id=?");
        arrayList.add(sessionId);
        sb.append(" and sessiontype=?");
        arrayList.add(String.valueOf(value));
        if (iMMessage.getTime() > 0) {
            sb.append(" and time<?");
            arrayList.add(String.valueOf(iMMessage.getTime()));
        }
        sb.append(" and msgtype=?");
        arrayList.add(String.valueOf(msgTypeEnum.getValue()));
        sb.append(" ORDER BY time desc");
        sb.append(" limit ?");
        arrayList.add(String.valueOf(i));
        return MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
    }

    public static List<IMMessage> queryMessageListByType(MsgTypeEnum msgTypeEnum, Long l, int i) {
        String str;
        String[] strArr;
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListByType(%s, %s, %s)", msgTypeEnum, l, Integer.valueOf(i)));
        if (l == null) {
            str = "SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where msgtype=? ORDER BY time desc";
            strArr = new String[]{String.valueOf(msgTypeEnum.getValue())};
        } else {
            String str2 = "SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where time<? and msgtype=? ORDER BY time desc limit ?";
            String[] strArr2 = {String.valueOf(l), String.valueOf(msgTypeEnum.getValue()), String.valueOf(i)};
            str = str2;
            strArr = strArr2;
        }
        return MsgDBHelperInternal.queryMsgHistories(str, strArr);
    }

    public static ArrayList<IMMessage> queryMessageListBySubtype(MsgTypeEnum msgTypeEnum, IMMessage iMMessage, int i, int i2) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMessageListBySubtype(%s, %s, %s, %s)", msgTypeEnum, d.a(iMMessage), Integer.valueOf(i), Integer.valueOf(i2)));
        String sessionId = iMMessage.getSessionId();
        int value = iMMessage.getSessionType().getValue();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id=?");
        arrayList.add(sessionId);
        sb.append(" and sessiontype=?");
        arrayList.add(String.valueOf(value));
        sb.append(" and subtype=?");
        arrayList.add(String.valueOf(i2));
        if (iMMessage.getTime() > 0) {
            sb.append(" and time<?");
            arrayList.add(String.valueOf(iMMessage.getTime()));
        }
        sb.append(" and msgtype=?");
        arrayList.add(String.valueOf(msgTypeEnum.getValue()));
        sb.append(" ORDER BY time desc");
        sb.append(" limit ?");
        arrayList.add(String.valueOf(i));
        return MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
    }

    public static ArrayList<IMMessage> queryMessageByPage(int i, int i2, boolean z) {
        StringBuilder sb = new StringBuilder("select ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" from msghistory order by messageid ");
        sb.append(z ? "asc" : SocialConstants.PARAM_APP_DESC);
        sb.append(" limit ? offset ?");
        return MsgDBHelperInternal.queryMsgHistories(sb.toString(), new String[]{String.valueOf(i), String.valueOf(i2)});
    }

    public static IMMessage queryLatestMessageFilterMsgType(String str, int i, List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id=? and sessiontype=?");
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        arrayList.add(String.valueOf(i));
        if (list != null && !list.isEmpty()) {
            sb.append(" and msgtype not in (");
            sb.append(repeatPlaceholders(list.size()));
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(String.valueOf(it.next()));
            }
        }
        sb.append(" ORDER BY time desc limit 1 offset 0");
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
        if (arrayListQueryMsgHistories.size() <= 0) {
            return null;
        }
        return arrayListQueryMsgHistories.get(0);
    }

    public static List<IMMessage> searchMessageHistory(String str, List<String> list, IMMessage iMMessage, final QueryDirectionEnum queryDirectionEnum, int i) {
        List listA = com.qiyukf.nimlib.n.e.a(list, MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME);
        if (com.qiyukf.nimlib.n.e.b((Collection) listA)) {
            return searchMessageHistoryPiece(str, list, iMMessage, queryDirectionEnum, i);
        }
        List<IMMessage> arrayList = new ArrayList<>(i);
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList = com.qiyukf.nimlib.n.e.a(arrayList, searchMessageHistoryPiece(str, (List) it.next(), iMMessage, queryDirectionEnum, i), i, new Comparator() { // from class: com.qiyukf.nimlib.session.MsgDBHelperB$$ExternalSyntheticLambda3
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return MsgDBHelperB.lambda$searchMessageHistory$0(queryDirectionEnum, (IMMessage) obj, (IMMessage) obj2);
                }
            });
        }
        return arrayList;
    }

    public static /* synthetic */ int lambda$searchMessageHistory$0(QueryDirectionEnum queryDirectionEnum, IMMessage iMMessage, IMMessage iMMessage2) {
        int iCompare = Long.compare(iMMessage.getTime(), iMMessage2.getTime());
        return queryDirectionEnum == QueryDirectionEnum.QUERY_NEW ? iCompare : -iCompare;
    }

    private static List<IMMessage> searchMessageHistoryPiece(String str, List<String> list, IMMessage iMMessage, QueryDirectionEnum queryDirectionEnum, int i) {
        String sessionId = iMMessage.getSessionId();
        int value = iMMessage.getSessionType().getValue();
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id=? and sessiontype=?");
        arrayList.add(sessionId);
        arrayList.add(String.valueOf(value));
        boolean z = queryDirectionEnum == QueryDirectionEnum.QUERY_NEW;
        if (iMMessage.getTime() > 0) {
            if (z) {
                sb.append(" and time>?");
            } else {
                sb.append(" and time<?");
            }
            arrayList.add(String.valueOf(iMMessage.getTime()));
        }
        sb.append(" and (");
        if (list != null && !list.isEmpty()) {
            sb.append("fromid in (");
            sb.append(repeatPlaceholders(list.size()));
            sb.append(") or");
            arrayList.addAll(list);
        }
        sb.append(" content like ? ESCAPE '\\') ORDER BY time ");
        sb.append(z ? "ASC" : "DESC");
        sb.append(" limit ?");
        arrayList.add(com.qiyukf.nimlib.database.a.d.c(str));
        arrayList.add(String.valueOf(i));
        return MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
    }

    public static List<IMMessage> searchAllMessageHistory(String str, List<String> list, long j, int i) {
        List listA = com.qiyukf.nimlib.n.e.a(list, MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME);
        if (com.qiyukf.nimlib.n.e.b((Collection) listA)) {
            return searchAllMessageHistoryPiece(str, list, j, i);
        }
        List<IMMessage> arrayList = new ArrayList<>(i);
        Iterator it = listA.iterator();
        while (it.hasNext()) {
            arrayList = com.qiyukf.nimlib.n.e.a(arrayList, searchAllMessageHistoryPiece(str, (List) it.next(), j, i), i, new Comparator() { // from class: com.qiyukf.nimlib.session.MsgDBHelperB$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return MsgDBHelperB.lambda$searchAllMessageHistory$1((IMMessage) obj, (IMMessage) obj2);
                }
            });
        }
        return arrayList;
    }

    public static /* synthetic */ int lambda$searchAllMessageHistory$1(IMMessage iMMessage, IMMessage iMMessage2) {
        return -Long.compare(iMMessage.getTime(), iMMessage2.getTime());
    }

    private static List<IMMessage> searchAllMessageHistoryPiece(String str, List<String> list, long j, int i) {
        String str2 = "SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory WHERE 1=1";
        ArrayList arrayList = new ArrayList();
        if (j > 0) {
            str2 = str2 + " and time<?";
            arrayList.add(String.valueOf(j));
        }
        String str3 = str2 + " and (";
        if (list != null && list.size() > 0) {
            str3 = ((str3 + "fromid in (") + repeatPlaceholders(list.size())) + ") or";
            arrayList.addAll(list);
        }
        arrayList.add(com.qiyukf.nimlib.database.a.d.c(str));
        String str4 = (str3 + " content like ? ESCAPE '\\')") + " ORDER BY time desc limit ?";
        arrayList.add(String.valueOf(i));
        return MsgDBHelperInternal.queryMsgHistories(str4, (String[]) arrayList.toArray(new String[0]));
    }

    public static List<IMMessage> searchMessage(SessionTypeEnum sessionTypeEnum, String str, final MsgSearchOption msgSearchOption) {
        if (msgSearchOption == null) {
            return new ArrayList(0);
        }
        List<List<String>> listA = com.qiyukf.nimlib.n.e.a(msgSearchOption.getFromIds(), MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME);
        if (com.qiyukf.nimlib.n.e.b((Collection) listA)) {
            return searchMessagePiece(sessionTypeEnum, str, msgSearchOption);
        }
        ArrayList arrayList = new ArrayList();
        for (List<String> list : listA) {
            MsgSearchOption msgSearchOption2 = new MsgSearchOption(msgSearchOption);
            msgSearchOption2.setFromIds(list);
            arrayList.add(msgSearchOption2);
        }
        int iMax = Math.max(0, msgSearchOption.getLimit());
        List<IMMessage> arrayList2 = new ArrayList<>(msgSearchOption.getLimit());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2 = com.qiyukf.nimlib.n.e.a(arrayList2, searchMessagePiece(sessionTypeEnum, str, (MsgSearchOption) it.next()), iMax, new Comparator() { // from class: com.qiyukf.nimlib.session.MsgDBHelperB$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return MsgDBHelperB.lambda$searchMessage$2(msgSearchOption, (IMMessage) obj, (IMMessage) obj2);
                }
            });
        }
        return arrayList2;
    }

    public static /* synthetic */ int lambda$searchMessage$2(MsgSearchOption msgSearchOption, IMMessage iMMessage, IMMessage iMMessage2) {
        int iCompare = Long.compare(iMMessage.getTime(), iMMessage2.getTime());
        return msgSearchOption.getOrder() == SearchOrderEnum.ASC ? iCompare : -iCompare;
    }

    private static List<IMMessage> searchMessagePiece(SessionTypeEnum sessionTypeEnum, String str, MsgSearchOption msgSearchOption) {
        com.qiyukf.nimlib.log.b.q(String.format("searchMessage sessionType = %s, sessionId = %s, MsgSearchOption = %s", sessionTypeEnum, str, msgSearchOption));
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id=? and sessiontype=?");
        arrayList.add(str);
        arrayList.add(String.valueOf(sessionTypeEnum.getValue()));
        long startTime = msgSearchOption.getStartTime();
        long endTime = msgSearchOption.getEndTime() == 0 ? Long.MAX_VALUE : msgSearchOption.getEndTime();
        sb.append(" and time>?");
        sb.append(" and time<?");
        arrayList.add(String.valueOf(startTime));
        arrayList.add(String.valueOf(endTime));
        boolean zIsAllMessageTypes = msgSearchOption.isAllMessageTypes();
        List<MsgTypeEnum> messageTypes = msgSearchOption.getMessageTypes();
        if (!zIsAllMessageTypes) {
            if (com.qiyukf.nimlib.n.e.b((Collection) messageTypes)) {
                messageTypes = new ArrayList<>();
                messageTypes.add(MsgTypeEnum.text);
            }
            sb.append(" and msgtype in (");
            sb.append(repeatPlaceholders(messageTypes.size()));
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            Iterator<MsgTypeEnum> it = messageTypes.iterator();
            while (it.hasNext()) {
                arrayList.add(String.valueOf(it.next().getValue()));
            }
        }
        List<Integer> messageSubTypes = msgSearchOption.getMessageSubTypes();
        if (!com.qiyukf.nimlib.n.e.b((Collection) messageSubTypes)) {
            sb.append(" and subtype in (");
            sb.append(repeatPlaceholders(messageSubTypes.size()));
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            Iterator<Integer> it2 = messageSubTypes.iterator();
            while (it2.hasNext()) {
                arrayList.add(String.valueOf(it2.next()));
            }
        }
        String searchContent = msgSearchOption.getSearchContent();
        List<String> fromIds = msgSearchOption.getFromIds();
        if (!TextUtils.isEmpty(searchContent) || !com.qiyukf.nimlib.n.e.b((Collection) fromIds)) {
            sb.append(" and (");
            boolean zIsEmpty = TextUtils.isEmpty(searchContent);
            if (!zIsEmpty) {
                sb.append("content like ? ESCAPE '\\'");
                arrayList.add(com.qiyukf.nimlib.database.a.d.c(searchContent));
            }
            if (fromIds != null && fromIds.size() > 0) {
                if (!zIsEmpty) {
                    sb.append(" or ");
                }
                sb.append("fromid in (");
                sb.append(repeatPlaceholders(fromIds.size()));
                sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
                arrayList.addAll(fromIds);
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        boolean z = msgSearchOption.getOrder() == SearchOrderEnum.ASC;
        sb.append(" ORDER BY time ");
        sb.append(z ? "ASC" : "DESC");
        int limit = msgSearchOption.getLimit();
        if (limit > 0) {
            sb.append(" limit ?");
            arrayList.add(String.valueOf(limit));
        }
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
        if (!z && !arrayListQueryMsgHistories.isEmpty()) {
            Collections.reverse(arrayListQueryMsgHistories);
        }
        return arrayListQueryMsgHistories;
    }

    public static List<IMMessage> searchAllMessage(final MsgSearchOption msgSearchOption) {
        if (msgSearchOption == null) {
            return new ArrayList(0);
        }
        List<List<String>> listA = com.qiyukf.nimlib.n.e.a(msgSearchOption.getFromIds(), MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME);
        if (com.qiyukf.nimlib.n.e.b((Collection) listA)) {
            return searchAllMessagePiece(msgSearchOption);
        }
        ArrayList arrayList = new ArrayList();
        for (List<String> list : listA) {
            MsgSearchOption msgSearchOption2 = new MsgSearchOption(msgSearchOption);
            msgSearchOption2.setFromIds(list);
            arrayList.add(msgSearchOption2);
        }
        int iMax = Math.max(0, msgSearchOption.getLimit());
        List<IMMessage> arrayList2 = new ArrayList<>(msgSearchOption.getLimit());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2 = com.qiyukf.nimlib.n.e.a(arrayList2, searchAllMessagePiece((MsgSearchOption) it.next()), iMax, new Comparator() { // from class: com.qiyukf.nimlib.session.MsgDBHelperB$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return MsgDBHelperB.lambda$searchAllMessage$3(msgSearchOption, (IMMessage) obj, (IMMessage) obj2);
                }
            });
        }
        return arrayList2;
    }

    public static /* synthetic */ int lambda$searchAllMessage$3(MsgSearchOption msgSearchOption, IMMessage iMMessage, IMMessage iMMessage2) {
        int iCompare = Long.compare(iMMessage.getTime(), iMMessage2.getTime());
        return msgSearchOption.getOrder() == SearchOrderEnum.ASC ? iCompare : -iCompare;
    }

    private static List<IMMessage> searchAllMessagePiece(MsgSearchOption msgSearchOption) {
        com.qiyukf.nimlib.log.b.q(String.format("searchAllMessage MsgSearchOption = %s", msgSearchOption));
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where 1=1");
        ArrayList arrayList = new ArrayList();
        long startTime = msgSearchOption.getStartTime();
        long endTime = msgSearchOption.getEndTime() == 0 ? Long.MAX_VALUE : msgSearchOption.getEndTime();
        sb.append(" and time>? and time<?");
        arrayList.add(String.valueOf(startTime));
        arrayList.add(String.valueOf(endTime));
        boolean zIsAllMessageTypes = msgSearchOption.isAllMessageTypes();
        List<MsgTypeEnum> messageTypes = msgSearchOption.getMessageTypes();
        if (!zIsAllMessageTypes) {
            if (com.qiyukf.nimlib.n.e.b((Collection) messageTypes)) {
                messageTypes = new ArrayList<>();
                messageTypes.add(MsgTypeEnum.text);
            }
            sb.append(" and msgtype in (");
            sb.append(repeatPlaceholders(messageTypes.size()));
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            Iterator<MsgTypeEnum> it = messageTypes.iterator();
            while (it.hasNext()) {
                arrayList.add(String.valueOf(it.next().getValue()));
            }
        }
        List<Integer> messageSubTypes = msgSearchOption.getMessageSubTypes();
        if (!com.qiyukf.nimlib.n.e.b((Collection) messageSubTypes)) {
            sb.append(" and subtype in (");
            sb.append(repeatPlaceholders(messageSubTypes.size()));
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
            Iterator<Integer> it2 = messageSubTypes.iterator();
            while (it2.hasNext()) {
                arrayList.add(String.valueOf(it2.next()));
            }
        }
        String searchContent = msgSearchOption.getSearchContent();
        List<String> fromIds = msgSearchOption.getFromIds();
        if (!TextUtils.isEmpty(searchContent) || !com.qiyukf.nimlib.n.e.b((Collection) fromIds)) {
            sb.append(" and (");
            boolean zIsEmpty = TextUtils.isEmpty(searchContent);
            if (!zIsEmpty) {
                sb.append("content like ? ESCAPE '\\'");
                arrayList.add(com.qiyukf.nimlib.database.a.d.c(searchContent));
            }
            if (fromIds != null && fromIds.size() > 0) {
                if (!zIsEmpty) {
                    sb.append(" or ");
                }
                sb.append("fromid in (");
                sb.append(repeatPlaceholders(fromIds.size()));
                sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
                arrayList.addAll(fromIds);
            }
            sb.append(ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        boolean z = msgSearchOption.getOrder() == SearchOrderEnum.ASC;
        sb.append(" ORDER BY time ");
        sb.append(z ? "ASC" : "DESC");
        int limit = msgSearchOption.getLimit();
        if (limit > 0) {
            sb.append(" limit ?");
            arrayList.add(String.valueOf(limit));
        }
        ArrayList<IMMessage> arrayListQueryMsgHistories = MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
        if (!z && !arrayListQueryMsgHistories.isEmpty()) {
            Collections.reverse(arrayListQueryMsgHistories);
        }
        return arrayListQueryMsgHistories;
    }

    public static int queryReplyCount(String str, String str2, SessionTypeEnum sessionTypeEnum) {
        Cursor cursorRawQuery = MsgDBHelperUtils.rawQuery("SELECT COUNT(1) FROM msghistory WHERE threadmsgidclient=? AND id=? AND sessiontype=?", new String[]{str, str2, String.valueOf(sessionTypeEnum.getValue())});
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
        return MsgDBHelperA.queryReplyMsgList(str, str2, sessionTypeEnum);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void updateMessageStatus(com.qiyukf.nimlib.session.d r7) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.lang.String r2 = "UPDATE msghistory SET"
            r0.append(r2)
            com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum r2 = r7.getStatus()
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L2d
            java.lang.String r2 = " status=?,"
            r0.append(r2)
            com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum r2 = r7.getStatus()
            int r2 = r2.getValue()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1.add(r2)
            r2 = 1
            goto L2e
        L2d:
            r2 = 0
        L2e:
            com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum r5 = r7.getAttachStatus()
            if (r5 == 0) goto L49
            java.lang.String r2 = " status2=?,"
            r0.append(r2)
            com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum r2 = r7.getAttachStatus()
            int r2 = r2.getValue()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1.add(r2)
            r2 = 1
        L49:
            com.qiyukf.nimlib.sdk.msg.attachment.MsgAttachment r5 = r7.getAttachment()
            java.lang.String r6 = r7.d()
            if (r5 == 0) goto L6a
            java.lang.String r2 = r5.toJson(r4)
            boolean r5 = java.util.Objects.equals(r6, r2)
            if (r5 != 0) goto L60
            r7.e(r2)
        L60:
            java.lang.String r5 = " attach=?,"
            r0.append(r5)
            r1.add(r2)
        L68:
            r2 = 1
            goto L76
        L6a:
            boolean r5 = android.text.TextUtils.isEmpty(r6)
            if (r5 != 0) goto L76
            java.lang.String r2 = " attach=null,"
            r0.append(r2)
            goto L68
        L76:
            if (r2 == 0) goto L9f
            int r2 = r0.length()
            int r2 = r2 - r3
            r0.deleteCharAt(r2)
            java.lang.String r2 = " WHERE uuid=?"
            r0.append(r2)
            java.lang.String r7 = r7.getUuid()
            r1.add(r7)
            java.lang.String[] r7 = new java.lang.String[r4]
            java.lang.Object[] r7 = r1.toArray(r7)
            java.lang.String[] r7 = (java.lang.String[]) r7
            com.qiyukf.nimlib.database.d r1 = com.qiyukf.nimlib.session.MsgDBHelperUtils.database()
            java.lang.String r0 = r0.toString()
            r1.a(r0, r7)
        L9f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.session.MsgDBHelperB.updateMessageStatus(com.qiyukf.nimlib.session.d):void");
    }

    public static void updateMessageLocalExt(d dVar) {
        MsgDBHelperUtils.database().a("UPDATE msghistory SET localext=? WHERE messageid=?", (Object[]) new String[]{dVar.j(), String.valueOf(dVar.a())});
    }

    public static void updateMessageStatus(long j, int i) {
        MsgDBHelperUtils.database().a("UPDATE msghistory SET status=?WHERE messageid=?", (Object[]) new String[]{String.valueOf(i), String.valueOf(j)});
    }

    public static int deleteMessage(d dVar, boolean z) {
        if (dVar == null) {
            return 0;
        }
        int iA = MsgDBHelperUtils.database().a("msghistory", "uuid=?", new String[]{dVar.getUuid()});
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
        ArrayList arrayList = new ArrayList();
        Iterator<? extends IMMessage> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getUuid());
        }
        int iA = 0;
        for (List list2 : com.qiyukf.nimlib.n.e.a(arrayList, MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME)) {
            iA += MsgDBHelperUtils.database().a("msghistory", String.format("uuid IN (%s)", repeatPlaceholders(list2.size())), (String[]) list2.toArray(new String[0]));
        }
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

    public static void clearMessage(String str, SessionTypeEnum sessionTypeEnum, boolean z) {
        MsgDBHelperUtils.database().a("DELETE FROM msghistory WHERE id=? AND sessiontype=?", (Object[]) new String[]{str, String.valueOf(sessionTypeEnum.getValue())});
        if (z) {
            MsgDBHelperInternal.recordClearContact(str, sessionTypeEnum, System.currentTimeMillis());
        }
        com.qiyukf.nimlib.search.b bVar = b.a.a;
    }

    public static ArrayList<IMMessage> queryUnreadMessages(String str, SessionTypeEnum sessionTypeEnum, long j) {
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        sb.append("SELECT ");
        sb.append(MsgDBHelperConstants.msgHistoryColumnStr());
        sb.append(" FROM msghistory where id=? and sessiontype=? and direct='1' and time > ?");
        arrayList.add(str);
        arrayList.add(String.valueOf(sessionTypeEnum.getValue()));
        arrayList.add(String.valueOf(j));
        return MsgDBHelperInternal.queryMsgHistories(sb.toString(), (String[]) arrayList.toArray(new String[0]));
    }

    public static void deleteRangeHistory(String str, SessionTypeEnum sessionTypeEnum, long j, long j2) {
        MsgDBHelperUtils.database().a("DELETE FROM msghistory WHERE id=? AND sessiontype=? AND time>? AND time<?", (Object[]) new String[]{str, String.valueOf(sessionTypeEnum.getValue()), String.valueOf(j), String.valueOf(j2)});
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
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.nimlib.session.MsgDBHelperB.migrateMessages(android.content.Context, java.lang.String, java.lang.String, boolean):void");
    }

    public static List<d> queryMessageListInSeqIdRange(long j, long j2, int[] iArr, int[] iArr2) {
        return MsgDBHelperA.queryMessageListInSeqIdRange(j, j2, iArr, iArr2);
    }

    public static ArrayList<IMMessage> queryMessageListEx(d dVar, long j, long j2, boolean z) {
        return MsgDBHelperA.queryMessageListEx(dVar, j, j2, z);
    }

    public static long getMessageTimeByUuid(String str) {
        return MsgDBHelperA.getMessageTimeByUuid(str);
    }

    public static Map<String, IMMessage> queryMsgMapByProperty(List<com.qiyukf.nimlib.push.packet.b.c> list) {
        return MsgDBHelperA.queryMsgMapByProperty(list);
    }

    public static IMMessage queryMessageByUuid(String str) {
        return MsgDBHelperA.queryMessageByUuid(str);
    }

    public static IMMessage queryMessageBySeqId(long j) {
        return MsgDBHelperA.queryMessageBySeqId(j);
    }

    public static long queryMessageIdByUuid(String str) {
        return MsgDBHelperA.queryMessageIdByUuid(str);
    }

    public static int queryStatus(String str, boolean z) {
        return MsgDBHelperA.queryStatus(str, z);
    }

    public static void updateSyncSelfMessageStatus(List<d> list) {
        MsgDBHelperA.updateSyncSelfMessageStatus(list);
    }

    public static void updateMessageCallbackExt(long j, String str) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set callbackext=? where messageid=?", new Object[]{str, Long.valueOf(j)});
    }

    public static void updateAttachStatus(String str, int i) {
        MsgDBHelperA.updateAttachStatus(str, i);
    }

    public static void setMessageStatusCode(String str, int i) {
        MsgDBHelperUtils.database().a("UPDATE msghistory set isblacked=? where uuid=?", new Object[]{Integer.valueOf(i), str});
    }

    public static long getSessionLastReceivedMsgTimeTag(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.getSessionLastReceivedMsgTimeTag(str, sessionTypeEnum);
    }

    public static void markHasSendTeamMsgAck(List<String> list) {
        MsgDBHelperA.markHasSendTeamMsgAck(list);
    }

    public static void updateTeamMsgAckCount(String str, int i, int i2) {
        MsgDBHelperA.updateTeamMsgAckCount(str, i, i2);
    }

    public static void saveRecent(s sVar) {
        MsgDBHelperUtils.database().a("INSERT OR REPLACE INTO lstmsg(uid,fromuid,messageId,msgstatus,unreadnum,content,time,sessiontype,tag,msgtype,attach,extension) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{sVar.getContactId(), sVar.getFromAccount(), sVar.getRecentMessageId(), Integer.valueOf(sVar.getMsgStatus().getValue()), Integer.valueOf(sVar.getUnreadCount()), sVar.getContent(), Long.valueOf(sVar.getTime()), Integer.valueOf(sVar.getSessionType().getValue()), Long.valueOf(sVar.getTag()), Integer.valueOf(sVar.b()), sVar.a(), sVar.c()});
    }

    public static void importRecentContactByUnionKey(List<s> list) {
        MsgDBHelperA.importRecentContactByUnionKey(list);
    }

    public static void saveRoamMsgHasMore(List<RoamMsgHasMoreOption> list) {
        MsgDBHelperA.saveRoamMsgHasMore(list);
    }

    public static void saveQuickComment(String str, List<QuickCommentOption> list) {
        MsgDBHelperA.saveQuickComment(str, list);
    }

    public static void saveCollectInfo(List<a> list) {
        MsgDBHelperA.saveCollectInfo(list);
    }

    public static void saveMsgPin(List<p> list) {
        MsgDBHelperA.saveMsgPin(list);
    }

    public static void saveStickTopSession(List<StickTopSessionInfo> list) {
        MsgDBHelperA.saveStickTopSession(list);
    }

    public static List<RecentContact> queryRecentContacts(int i) {
        return MsgDBHelperA.queryRecentContacts(i);
    }

    public static s queryRecentContact(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.queryRecentContact(str, sessionTypeEnum);
    }

    public static long queryRoamMsgHasMoreTime(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.queryRoamMsgHasMoreTime(str, sessionTypeEnum);
    }

    public static long queryRoamMsgHasMoreServerId(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.queryRoamMsgHasMoreServerId(str, sessionTypeEnum);
    }

    public static ArrayList<QuickCommentOption> queryQuickCommentByUuid(String str) {
        return MsgDBHelperA.queryQuickCommentByUuid(str);
    }

    public static List<MsgPinDbOption> queryMsgPin(String str) {
        return MsgDBHelperA.queryMsgPin(str);
    }

    public static boolean isStickTopSession(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.isStickTopSession(str, sessionTypeEnum);
    }

    public static void setRecentStatus(String str, int i, long j) {
        MsgDBHelperA.setRecentStatus(str, i, j);
    }

    public static void setRecentRead(String str, int i) {
        MsgDBHelperA.setRecentRead(str, i);
    }

    public static void updateRecentUnreadNum(String str, SessionTypeEnum sessionTypeEnum, int i) {
        MsgDBHelperA.updateRecentUnreadNum(str, sessionTypeEnum, i);
    }

    public static void updateRecent(RecentContact recentContact) {
        MsgDBHelperA.updateRecent(recentContact);
    }

    public static void updateRoamMsgHasMoreTime(RoamMsgHasMoreOption roamMsgHasMoreOption) {
        MsgDBHelperA.updateRoamMsgHasMoreTime(roamMsgHasMoreOption);
    }

    public static void updateCollectInfo(a aVar) {
        MsgDBHelperA.updateCollectInfo(aVar);
    }

    public static void updateMsgPin(String str, String str2, String str3, long j) {
        MsgDBHelperA.updateMsgPin(str, str2, str3, j);
    }

    public static void updateStickTopSession(String str, SessionTypeEnum sessionTypeEnum, String str2, long j) {
        MsgDBHelperA.updateStickTopSession(str, sessionTypeEnum, str2, j);
    }

    public static void deleteRecentContact(String str, SessionTypeEnum sessionTypeEnum) {
        MsgDBHelperA.deleteRecentContact(str, sessionTypeEnum);
    }

    public static void deleteRoamMsgHasMoreTime(String str, SessionTypeEnum sessionTypeEnum) {
        MsgDBHelperA.deleteRoamMsgHasMoreTime(str, sessionTypeEnum);
    }

    public static void deleteQuickComment(String str, String str2, long j) {
        MsgDBHelperA.deleteQuickComment(str, str2, j);
    }

    public static void deleteQuickComment(String str) {
        MsgDBHelperA.deleteQuickComment(str);
    }

    public static void deleteCollectInfo(List<Long> list) {
        MsgDBHelperA.deleteCollectInfo(list);
    }

    public static void deleteMsgPin(String str, String str2) {
        MsgDBHelperA.deleteMsgPin(str, str2);
    }

    public static void deleteMsgPin(String str) {
        MsgDBHelperA.deleteMsgPin(str);
    }

    public static void deleteStickTopSession(String str) {
        MsgDBHelperA.deleteStickTopSession(str);
    }

    public static boolean hasDeleteTag(String str) {
        return MsgDBHelperA.hasDeleteTag(str);
    }

    public static Set<String> hasDeleteTag(Collection<d> collection) {
        return MsgDBHelperA.hasDeleteTag(collection);
    }

    public static long getClearSessionTime(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.getClearSessionTime(str, sessionTypeEnum);
    }

    public static ArrayList<SystemMessage> querySystemMessages(int i, int i2) {
        return MsgDBHelperA.querySystemMessages(i, i2);
    }

    public static ArrayList<SystemMessage> querySystemMessage(List<SystemMessageType> list, int i, int i2) {
        return MsgDBHelperA.querySystemMessage(list, i, i2);
    }

    public static void setSystemMessageStatus(long j, SystemMessageStatus systemMessageStatus) {
        MsgDBHelperA.setSystemMessageStatus(j, systemMessageStatus);
    }

    public static void setAllSystemMessageRead(List<SystemMessageType> list) {
        MsgDBHelperA.setAllSystemMessageRead(list);
    }

    public static void setSystemMessageRead(long j) {
        MsgDBHelperA.setSystemMessageRead(j);
    }

    public static int querySystemMessageUnreadNum(List<SystemMessageType> list) {
        return MsgDBHelperA.querySystemMessageUnreadNum(list);
    }

    public static void deleteSystemMessage(long j) {
        MsgDBHelperA.deleteSystemMessage(j);
    }

    public static void clearSystemMessages(List<SystemMessageType> list) {
        MsgDBHelperA.clearSystemMessages(list);
    }

    public static void saveMessageReceipt(List<g> list) {
        MsgDBHelperA.saveMessageReceipt(list);
    }

    public static Map<String, g> queryMessageReceipt(List<String> list) {
        return MsgDBHelperA.queryMessageReceipt(list);
    }

    public static void saveSendReceiptRecord(MessageReceipt messageReceipt) {
        MsgDBHelperA.saveSendReceiptRecord(messageReceipt);
    }

    public static void saveSessionReadRecord(String str, SessionTypeEnum sessionTypeEnum, long j) {
        MsgDBHelperA.saveSessionReadRecord(str, sessionTypeEnum, j);
    }

    public static long querySessionReadTimeTag(String str, SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.querySessionReadTimeTag(str, sessionTypeEnum);
    }

    public static void saveSenderNick(String str, String str2) {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("account", str);
        contentValues.put("nick", str2);
        MsgDBHelperUtils.database().c("sender_nick", null, contentValues);
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
                ContentValues contentValues = new ContentValues(2);
                contentValues.put("account", entry.getKey());
                contentValues.put("nick", entry.getValue());
                MsgDBHelperUtils.database().c("sender_nick", null, contentValues);
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
        return MsgDBHelperA.queryAllSenderNick();
    }

    public static void saveRevokeMessage(String str) {
        MsgDBHelperA.saveRevokeMessage(str);
    }

    public static String queryRevokeMessage(String str) {
        return MsgDBHelperA.queryRevokeMessage(str);
    }

    public static void saveTeamMsgAckDetail(TeamMsgAckInfo teamMsgAckInfo, String str) {
        MsgDBHelperA.saveTeamMsgAckDetail(teamMsgAckInfo, str);
    }

    public static void updateTeamMsgAckDetail(String str, String str2) {
        MsgDBHelperA.updateTeamMsgAckDetail(str, str2);
    }

    public static TeamMsgAckInfo queryTeamMsgAckDetail(String str) {
        return MsgDBHelperA.queryTeamMsgAckDetail(str);
    }

    public static int removeSessionReliableInfo(@NonNull List<Long> list) {
        return MsgDBHelperA.removeSessionReliableInfo(list);
    }

    public static int removeSessionReliableInfo(@NonNull String str, @NonNull SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.removeSessionReliableInfo(str, sessionTypeEnum);
    }

    public static com.qiyukf.nimlib.session.b.d queryLastSessionReliableInfo(@NonNull String str, @NonNull SessionTypeEnum sessionTypeEnum) {
        return MsgDBHelperA.queryLastSessionReliableInfo(str, sessionTypeEnum);
    }

    @NonNull
    public static List<com.qiyukf.nimlib.session.b.d> queryMayOverLappedInfos(@NonNull com.qiyukf.nimlib.session.b.d dVar) {
        return MsgDBHelperA.queryMayOverLappedInfos(dVar);
    }

    @NonNull
    public static List<com.qiyukf.nimlib.session.b.d> queryParentInfos(@NonNull com.qiyukf.nimlib.session.b.d dVar) {
        return MsgDBHelperA.queryParentInfos(dVar);
    }

    public static List<IMMessage> queryMsgListByUuid(List<String> list) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMsgListByUuid, uuid size is %s", Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            return new ArrayList(0);
        }
        ArrayList arrayList = new ArrayList(com.qiyukf.nimlib.n.e.d(list));
        for (List list2 : com.qiyukf.nimlib.n.e.a(list, MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME)) {
            arrayList.addAll(MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where uuid in (" + repeatPlaceholders(list2.size()) + ChineseToPinyinResource.Field.RIGHT_BRACKET, (String[]) list2.toArray(new String[0])));
        }
        return arrayList;
    }

    public static List<IMMessage> queryMsgListBySeqId(List<Long> list) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMsgListBySeqId, seqIds size is %s", Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            return new ArrayList(0);
        }
        int i = MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < list.size()) {
            int i3 = i2 + i;
            List<Long> listSubList = list.subList(i2, Math.min(i3, list.size()));
            String str = "SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where messageid in (" + repeatPlaceholders(listSubList.size()) + ") ORDER BY time DESC";
            String[] strArr = new String[listSubList.size()];
            for (int i4 = 0; i4 < listSubList.size(); i4++) {
                strArr[i4] = String.valueOf(listSubList.get(i4));
            }
            arrayList.addAll(MsgDBHelperInternal.queryMsgHistories(str, strArr));
            i2 = i3;
        }
        return arrayList;
    }

    public static List<IMMessage> queryMsgListByServerId(List<String> list) {
        com.qiyukf.nimlib.log.b.q(String.format("queryMsgListByServerId, serverId size is %s", Integer.valueOf(com.qiyukf.nimlib.n.e.d(list))));
        if (com.qiyukf.nimlib.n.e.b((Collection) list)) {
            return new ArrayList(0);
        }
        ArrayList arrayList = new ArrayList(com.qiyukf.nimlib.n.e.d(list));
        for (List list2 : com.qiyukf.nimlib.n.e.a(list, MsgDBHelper.MAX_NUMBERS_OF_IN_FRAME)) {
            arrayList.addAll(MsgDBHelperInternal.queryMsgHistories("SELECT " + MsgDBHelperConstants.msgHistoryColumnStr() + " FROM msghistory where serverid in (" + repeatPlaceholders(list2.size()) + ChineseToPinyinResource.Field.RIGHT_BRACKET, (String[]) list2.toArray(new String[0])));
        }
        return arrayList;
    }
}

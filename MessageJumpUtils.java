package com.petkit.android.activities.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.card.MyCardHomeActivity;
import com.petkit.android.activities.chat.mode.Payload;
import com.petkit.android.activities.cloudservice.CloudServiceWebViewActivity;
import com.petkit.android.activities.cloudservice.ServiceDetailActivity;
import com.petkit.android.activities.cloudservice.ServiceManagementActivity;
import com.petkit.android.activities.community.PostDetailActivity;
import com.petkit.android.activities.home.HistoryMsgActivity;
import com.petkit.android.activities.home.MessageActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.model.PayloadContent;
import com.petkit.android.model.PostItem;
import com.petkit.android.model.User;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class MessageJumpUtils {
    public MessageJumpUtils() {
    }

    public static class MessageJumpUtilsInstance {
        public static final MessageJumpUtils instance = new MessageJumpUtils();
    }

    public static MessageJumpUtils getInstance() {
        return MessageJumpUtilsInstance.instance;
    }

    public void jumpDeviceNotification(Activity activity, Payload payload, String str, boolean z) {
        if (jumpNotification(activity, payload, str, z) || jumpLog(activity, payload, str) || jumpCircleMessage(activity, payload, str) || !z) {
            return;
        }
        if (payload.getMsgType() == 1 || payload.getMsgType() == 2 || payload.getMsgType() == 3 || payload.getMsgType() == 5) {
            activity.startActivity(MessageActivity.newIntent(activity, false));
        } else if (payload.getMsgType() == 4 || payload.getMsgType() == 6) {
            activity.startActivity(HistoryMsgActivity.newIntent(activity));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:765:0x0ffc A[PHI: r26
  0x0ffc: PHI (r26v173 java.lang.Class) = 
  (r26v1 java.lang.Class)
  (r26v2 java.lang.Class)
  (r26v3 java.lang.Class)
  (r26v4 java.lang.Class)
  (r26v5 java.lang.Class)
  (r26v6 java.lang.Class)
  (r26v7 java.lang.Class)
  (r26v8 java.lang.Class)
  (r26v9 java.lang.Class)
  (r26v10 java.lang.Class)
  (r26v11 java.lang.Class)
  (r26v12 java.lang.Class)
  (r26v13 java.lang.Class)
  (r26v14 java.lang.Class)
  (r26v15 java.lang.Class)
  (r26v16 java.lang.Class)
  (r26v17 java.lang.Class)
  (r26v18 java.lang.Class)
  (r26v19 java.lang.Class)
  (r26v20 java.lang.Class)
  (r26v21 java.lang.Class)
  (r26v22 java.lang.Class)
  (r26v23 java.lang.Class)
  (r26v24 java.lang.Class)
  (r26v25 java.lang.Class)
  (r26v26 java.lang.Class)
  (r26v27 java.lang.Class)
  (r26v28 java.lang.Class)
  (r26v29 java.lang.Class)
  (r26v30 java.lang.Class)
  (r26v31 java.lang.Class)
  (r26v32 java.lang.Class)
  (r26v33 java.lang.Class)
  (r26v34 java.lang.Class)
  (r26v35 java.lang.Class)
  (r26v36 java.lang.Class)
  (r26v37 java.lang.Class)
  (r26v38 java.lang.Class)
  (r26v39 java.lang.Class)
  (r26v40 java.lang.Class)
  (r26v41 java.lang.Class)
  (r26v42 java.lang.Class)
  (r26v43 java.lang.Class)
  (r26v44 java.lang.Class)
  (r26v45 java.lang.Class)
  (r26v46 java.lang.Class)
  (r26v47 java.lang.Class)
  (r26v48 java.lang.Class)
  (r26v49 java.lang.Class)
  (r26v50 java.lang.Class)
  (r26v51 java.lang.Class)
  (r26v52 java.lang.Class)
  (r26v53 java.lang.Class)
  (r26v54 java.lang.Class)
  (r26v55 java.lang.Class)
  (r26v56 java.lang.Class)
  (r26v57 java.lang.Class)
  (r26v58 java.lang.Class)
  (r26v59 java.lang.Class)
  (r26v60 java.lang.Class)
  (r26v61 java.lang.Class)
  (r26v62 java.lang.Class)
  (r26v63 java.lang.Class)
  (r26v64 java.lang.Class)
  (r26v65 java.lang.Class)
  (r26v66 java.lang.Class)
  (r26v67 java.lang.Class)
  (r26v68 java.lang.Class)
  (r26v69 java.lang.Class)
  (r26v70 java.lang.Class)
  (r26v71 java.lang.Class)
  (r26v72 java.lang.Class)
  (r26v73 java.lang.Class)
  (r26v74 java.lang.Class)
  (r26v75 java.lang.Class)
  (r26v76 java.lang.Class)
  (r26v77 java.lang.Class)
  (r26v78 java.lang.Class)
  (r26v79 java.lang.Class)
  (r26v80 java.lang.Class)
  (r26v81 java.lang.Class)
  (r26v82 java.lang.Class)
  (r26v83 java.lang.Class)
  (r26v84 java.lang.Class)
  (r26v85 java.lang.Class)
  (r26v86 java.lang.Class)
  (r26v87 java.lang.Class)
  (r26v88 java.lang.Class)
  (r26v89 java.lang.Class)
  (r26v90 java.lang.Class)
  (r26v91 java.lang.Class)
  (r26v92 java.lang.Class)
  (r26v93 java.lang.Class)
  (r26v94 java.lang.Class)
  (r26v95 java.lang.Class)
  (r26v96 java.lang.Class)
  (r26v97 java.lang.Class)
  (r26v98 java.lang.Class)
  (r26v99 java.lang.Class)
  (r26v100 java.lang.Class)
  (r26v101 java.lang.Class)
  (r26v102 java.lang.Class)
  (r26v103 java.lang.Class)
  (r26v104 java.lang.Class)
  (r26v105 java.lang.Class)
  (r26v106 java.lang.Class)
  (r26v107 java.lang.Class)
  (r26v108 java.lang.Class)
  (r26v109 java.lang.Class)
  (r26v110 java.lang.Class)
  (r26v111 java.lang.Class)
  (r26v112 java.lang.Class)
  (r26v113 java.lang.Class)
  (r26v114 java.lang.Class)
  (r26v115 java.lang.Class)
  (r26v116 java.lang.Class)
  (r26v117 java.lang.Class)
  (r26v118 java.lang.Class)
  (r26v119 java.lang.Class)
  (r26v120 java.lang.Class)
  (r26v121 java.lang.Class)
  (r26v122 java.lang.Class)
  (r26v123 java.lang.Class)
  (r26v124 java.lang.Class)
  (r26v125 java.lang.Class)
  (r26v126 java.lang.Class)
  (r26v127 java.lang.Class)
  (r26v128 java.lang.Class)
  (r26v129 java.lang.Class)
  (r26v130 java.lang.Class)
  (r26v131 java.lang.Class)
  (r26v132 java.lang.Class)
  (r26v133 java.lang.Class)
  (r26v134 java.lang.Class)
  (r26v135 java.lang.Class)
  (r26v136 java.lang.Class)
  (r26v137 java.lang.Class)
  (r26v138 java.lang.Class)
  (r26v139 java.lang.Class)
  (r26v140 java.lang.Class)
  (r26v141 java.lang.Class)
  (r26v142 java.lang.Class)
  (r26v143 java.lang.Class)
  (r26v144 java.lang.Class)
  (r26v145 java.lang.Class)
  (r26v146 java.lang.Class)
  (r26v147 java.lang.Class)
  (r26v148 java.lang.Class)
  (r26v149 java.lang.Class)
  (r26v150 java.lang.Class)
  (r26v151 java.lang.Class)
  (r26v152 java.lang.Class)
  (r26v153 java.lang.Class)
  (r26v154 java.lang.Class)
  (r26v155 java.lang.Class)
  (r26v156 java.lang.Class)
  (r26v157 java.lang.Class)
  (r26v158 java.lang.Class)
  (r26v159 java.lang.Class)
  (r26v160 java.lang.Class)
  (r26v161 java.lang.Class)
  (r26v162 java.lang.Class)
  (r26v163 java.lang.Class)
  (r26v164 java.lang.Class)
  (r26v165 java.lang.Class)
  (r26v166 java.lang.Class)
  (r26v167 java.lang.Class)
  (r26v168 java.lang.Class)
  (r26v169 java.lang.Class)
  (r26v170 java.lang.Class)
  (r26v171 java.lang.Class)
  (r26v174 java.lang.Class)
 binds: [B:1447:0x1a94, B:1443:0x1a87, B:1439:0x1a7a, B:1435:0x1a6d, B:1431:0x1a60, B:1427:0x1a53, B:1423:0x1a46, B:1419:0x1a37, B:1415:0x1a27, B:1411:0x1a17, B:1407:0x1a07, B:1403:0x19f7, B:1399:0x19e7, B:1395:0x19d7, B:1391:0x19c7, B:1387:0x19b7, B:1383:0x19a7, B:1379:0x1997, B:1375:0x1987, B:1371:0x1977, B:1367:0x1967, B:1363:0x1957, B:1359:0x1947, B:1355:0x1937, B:1351:0x1927, B:1347:0x1917, B:1343:0x1907, B:1339:0x18f7, B:1335:0x18e7, B:1331:0x18d7, B:1327:0x18c7, B:1323:0x18b7, B:1319:0x18a7, B:1315:0x1897, B:1311:0x1887, B:1307:0x1877, B:1303:0x1867, B:1299:0x1857, B:1295:0x1847, B:1291:0x1837, B:1287:0x1827, B:1283:0x1817, B:1279:0x1807, B:1275:0x17f7, B:1271:0x17e7, B:1267:0x17d7, B:1263:0x17c7, B:1259:0x17b7, B:1255:0x17a7, B:1251:0x1797, B:1247:0x1787, B:1243:0x1777, B:1239:0x1767, B:1235:0x1757, B:1231:0x1747, B:1227:0x1737, B:1223:0x1727, B:1219:0x1717, B:1215:0x1707, B:1211:0x16f7, B:1207:0x16e7, B:1203:0x16d7, B:1199:0x16c7, B:1195:0x16b7, B:1191:0x16a7, B:1187:0x1697, B:1183:0x1687, B:1179:0x1677, B:1175:0x1667, B:1171:0x1657, B:1167:0x1647, B:1163:0x1637, B:1159:0x1627, B:1155:0x1617, B:1151:0x1607, B:1147:0x15f7, B:1143:0x15e7, B:1139:0x15d7, B:1135:0x15c7, B:1131:0x15b7, B:1127:0x15a7, B:1123:0x1597, B:1119:0x1587, B:1115:0x1577, B:1111:0x1567, B:1107:0x1557, B:1103:0x1547, B:1099:0x1537, B:1095:0x1527, B:1091:0x1517, B:1087:0x1507, B:1083:0x14f7, B:1079:0x14e7, B:1075:0x14d7, B:1071:0x14c7, B:1067:0x14b7, B:1063:0x14a7, B:1059:0x1497, B:1055:0x1487, B:1051:0x1477, B:1047:0x1467, B:1043:0x1457, B:1039:0x1447, B:1035:0x1437, B:1031:0x1427, B:1027:0x1417, B:1023:0x1407, B:1019:0x13f7, B:1015:0x13e7, B:1011:0x13d7, B:1007:0x13c7, B:1003:0x13b7, B:999:0x13a7, B:995:0x1397, B:991:0x1387, B:987:0x1377, B:983:0x1367, B:979:0x1357, B:975:0x1347, B:971:0x1337, B:967:0x1327, B:963:0x1317, B:959:0x1307, B:955:0x12f7, B:951:0x12e7, B:947:0x12d7, B:943:0x12c7, B:939:0x12b7, B:935:0x12a7, B:931:0x1297, B:927:0x1287, B:923:0x1277, B:919:0x1267, B:915:0x1257, B:911:0x1247, B:907:0x1237, B:903:0x1227, B:899:0x1217, B:895:0x1207, B:891:0x11f7, B:887:0x11e7, B:883:0x11d7, B:879:0x11c7, B:875:0x11b7, B:871:0x11a7, B:867:0x1197, B:863:0x1187, B:859:0x1177, B:855:0x1167, B:851:0x1157, B:847:0x1147, B:843:0x1137, B:839:0x1127, B:835:0x1117, B:831:0x1107, B:827:0x10f7, B:823:0x10e7, B:819:0x10d7, B:815:0x10c7, B:811:0x10b7, B:807:0x10a7, B:803:0x1097, B:799:0x1087, B:795:0x1077, B:791:0x1067, B:787:0x1057, B:783:0x1047, B:779:0x1037, B:775:0x1027, B:771:0x1017, B:767:0x1007, B:764:0x0ffa] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean jumpNotification(final android.app.Activity r28, final com.petkit.android.activities.chat.mode.Payload r29, java.lang.String r30, boolean r31) {
        /*
            Method dump skipped, instruction units count: 10000
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.chat.MessageJumpUtils.jumpNotification(android.app.Activity, com.petkit.android.activities.chat.mode.Payload, java.lang.String, boolean):boolean");
    }

    public final boolean goDeviceHomeActivity(Activity activity, String str, int i, boolean z) {
        if (i == 21) {
            if (T6Utils.getT6RecordByDeviceId(CommonUtil.getLong(str), 1) == null) {
                PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                return true;
            }
        } else {
            switch (i) {
                case 25:
                    if (D4shUtils.getD4shRecordByDeviceId(CommonUtil.getLong(str), 0) == null) {
                        PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                        return true;
                    }
                    break;
                case 26:
                    if (D4shUtils.getD4shRecordByDeviceId(CommonUtil.getLong(str), 1) == null) {
                        PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                        return true;
                    }
                    break;
                case 27:
                    if (T6Utils.getT6RecordByDeviceId(CommonUtil.getLong(str), 0) == null) {
                        PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                        return true;
                    }
                    break;
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_DEVICE_ID, CommonUtil.getLong(str));
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_DEVICE_AI, true);
        intent.putExtra(Constants.EXTRA_DEVICE_AI_AWARD_FAIL, z);
        if (i == 21) {
            intent.setClass(activity, T5HomeActivity.class);
        } else {
            switch (i) {
                case 25:
                    intent.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
                    intent.setClass(activity, D4shHomeActivity.class);
                    break;
                case 26:
                    intent.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
                    intent.setClass(activity, D4shHomeActivity.class);
                    break;
                case 27:
                    intent.setClass(activity, T6HomeActivity.class);
                    break;
            }
        }
        activity.startActivity(intent);
        return true;
    }

    public final boolean goOrderListPage(Activity activity, String str, int i) {
        if (CommonUtils.isEmpty(str)) {
            return false;
        }
        String str2 = CommonUtils.getH5Server(activity) + "?token=" + CommonUtils.getSysMap(activity, Consts.SHARED_SESSION_ID) + "&pageType=order-list&language=" + DataHelper.getStringSF(activity, Consts.SHARED_SETTING_LANGUAGE);
        Intent intent = new Intent(activity, (Class<?>) CloudServiceWebViewActivity.class);
        intent.putExtra(Constants.EXTRA_LOAD_PATH, str2);
        activity.startActivity(intent);
        return true;
    }

    @SuppressLint({"DefaultLocale"})
    public final boolean goWebH5Page(Activity activity, String str, int i) {
        User user;
        if (CommonUtils.isEmpty(str)) {
            return false;
        }
        String sysMap = CommonUtils.getSysMap(activity, Consts.SHARED_SESSION_ID);
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult == null || (user = currentLoginResult.getUser()) == null) {
            return false;
        }
        String str2 = String.format("%s?token=%s&deviceId=%d&deviceType=%s&userId=%s", CommonUtils.getH5Server(activity), sysMap, Long.valueOf(Long.parseLong(str)), CommonUtils.getDeviceTypeStringByInt(i), user.getId());
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_LOAD_PATH, str2);
        Intent intent = new Intent(activity, (Class<?>) CloudServiceWebViewActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        return true;
    }

    public final boolean goCloudListPage(Activity activity, int... iArr) {
        activity.startActivity(new Intent(activity, (Class<?>) ServiceManagementActivity.class));
        return true;
    }

    public final boolean goServiceDetailPage(Activity activity, String str, int i, int i2) {
        if (CommonUtils.isEmpty(str)) {
            return false;
        }
        Intent intent = new Intent(activity, (Class<?>) ServiceDetailActivity.class);
        intent.putExtra(Constants.EXTRA_CLOUD_SERVICE_FROM, true);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, Long.parseLong(str));
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_SERVICE_ID, i2);
        activity.startActivity(intent);
        return true;
    }

    public final boolean goAiServiceDetailPage(Activity activity, String str, int i, boolean z) {
        if (CommonUtil.getInt(str) < 0) {
            return false;
        }
        if (i == 21) {
            if (T6Utils.getT6RecordByDeviceId(CommonUtil.getLong(str), 1) == null) {
                if (z) {
                    return false;
                }
                PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                return true;
            }
        } else {
            switch (i) {
                case 25:
                    if (D4shUtils.getD4shRecordByDeviceId(CommonUtil.getLong(str), 0) == null) {
                        if (z) {
                            return false;
                        }
                        PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                        return true;
                    }
                    break;
                case 26:
                    if (D4shUtils.getD4shRecordByDeviceId(CommonUtil.getLong(str), 1) == null) {
                        if (z) {
                            return false;
                        }
                        PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                        return true;
                    }
                    break;
                case 27:
                    if (T6Utils.getT6RecordByDeviceId(CommonUtil.getLong(str), 0) == null) {
                        if (z) {
                            return false;
                        }
                        PetkitToast.showShortToast(activity, R.string.No_device_is_bound);
                        return true;
                    }
                    break;
            }
        }
        Intent intent = new Intent(activity, (Class<?>) ServiceDetailActivity.class);
        intent.putExtra(Constants.EXTRA_CLOUD_SERVICE_FROM, true);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, Long.parseLong(str));
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        activity.startActivity(intent);
        return true;
    }

    public final boolean goServiceDetailPage(Activity activity, String str, int i) {
        if (CommonUtils.isEmpty(str)) {
            return false;
        }
        if (TextUtils.isEmpty(str) || Long.parseLong(str) <= 0) {
            goCloudListPage(activity, i);
            return true;
        }
        Intent intent = new Intent(activity, (Class<?>) ServiceDetailActivity.class);
        intent.putExtra(Constants.EXTRA_CLOUD_SERVICE_FROM, true);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, Long.parseLong(str));
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        activity.startActivity(intent);
        return true;
    }

    public final boolean goCardListPage(Activity activity) {
        activity.startActivity(new Intent(activity, (Class<?>) MyCardHomeActivity.class));
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean jumpLog(android.app.Activity r10, com.petkit.android.activities.chat.mode.Payload r11, java.lang.String r12) {
        /*
            Method dump skipped, instruction units count: 1314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.chat.MessageJumpUtils.jumpLog(android.app.Activity, com.petkit.android.activities.chat.mode.Payload, java.lang.String):boolean");
    }

    public boolean jumpCircleMessage(Activity activity, Payload payload, String str) {
        if (payload == null || payload.getType() == null) {
            return false;
        }
        if ((!Constants.IM_PAYLOAD_TYPE_POST_COMMENT.equals(payload.getType()) && !Constants.IM_PAYLOAD_TYPE_POST_COMMENTREPLY.equals(payload.getType()) && !Constants.IM_PAYLOAD_TYPE_AT_COMMENT.equals(payload.getType()) && !Constants.IM_PAYLOAD_TYPE_AT_POST.equals(payload.getType()) && !Constants.IM_PAYLOAD_TYPE_POST_FAVOR.equals(payload.getType())) || str == null) {
            return false;
        }
        PayloadContent payloadContent = (PayloadContent) new Gson().fromJson(str, PayloadContent.class);
        if (payloadContent.getPost() == null) {
            return false;
        }
        PostItem post = payloadContent.getPost();
        if (post.getAuthor().getId().equals(CommonUtils.getCurrentUserId())) {
            post.setAuthor(UserInforUtils.createAuthorObjectForuserself());
        }
        Intent intent = new Intent(activity, (Class<?>) PostDetailActivity.class);
        intent.putExtra(Constants.EXTRA_POST_DATA, post);
        intent.putExtra(Constants.EXTRA_CLICK_FROM, "message");
        if (payloadContent.getComment() != null) {
            intent.putExtra("commentId", payloadContent.getComment());
            intent.putExtra("replyTo", payloadContent.getComment().getCommentor().getId());
            intent.putExtra("replyNick", payloadContent.getComment().getCommentor().getNick());
        }
        activity.startActivity(intent);
        return true;
    }
}

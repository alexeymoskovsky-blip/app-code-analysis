package com.petkit.android.activities.petkitBleDevice.utils;

import android.content.Context;
import com.petkit.android.activities.cozy.mode.CozyRecord;
import com.petkit.android.activities.cozy.utils.CozyUtils;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.family.mode.FamilyDeviceInfor;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.petkitBleDevice.aqh1.mode.Aqh1Record;
import com.petkit.android.activities.petkitBleDevice.aqh1.utils.Aqh1Utils;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrRecord;
import com.petkit.android.activities.petkitBleDevice.aqr.utils.AqrUtils;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Record;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3Utils;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgRecord;
import com.petkit.android.activities.petkitBleDevice.hg.utils.HgUtils;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Record;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.mode.K2Record;
import com.petkit.android.activities.petkitBleDevice.mode.T3Record;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Record;
import com.petkit.android.activities.petkitBleDevice.r2.utils.R2Utils;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hDataUtils;
import com.petkit.android.utils.ThreadPoolManager;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class RecordHandleUtils {
    public RecordHandleUtils() {
    }

    public static class IconUtilsInstance {
        public static final RecordHandleUtils instance = new RecordHandleUtils();
    }

    public static RecordHandleUtils getInstance() {
        return IconUtilsInstance.instance;
    }

    public void deleteDeviceRecord(long j, int i) {
        W7hRecord w7hRecordByDeviceId;
        T7Record t7RecordById;
        T6Record t6RecordByDeviceId;
        T6Record t6RecordByDeviceId2;
        CTW3Record cTW3RecordByDeviceId;
        D4shRecord d4shRecordByDeviceId;
        D4shRecord d4shRecordByDeviceId2;
        HgRecord hgRecordByDeviceId;
        Aqh1Record aqh1RecordByDeviceId;
        R2Record r2RecordByDeviceId;
        AqrRecord aqrRecordByDeviceId;
        K3Record k3RecordByDeviceId;
        T4Record t4RecordByDeviceId;
        W5Record w5RecordByDeviceId;
        P3Record p3RecordByDeviceId;
        D4sRecord d4sRecordByDeviceId;
        D4Record d4RecordByDeviceId;
        AqRecord aqRecordByDeviceId;
        D3Record d3RecordByDeviceId;
        K2Record k2RecordByDeviceId;
        T3Record t3RecordByDeviceId;
        D2Record d2RecordByDeviceId;
        CozyRecord cozyRecordByDeviceId;
        FeederRecord feederRecordByDeviceId;
        if (i == 4 && (feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(j)) != null) {
            feederRecordByDeviceId.delete();
        }
        if (i == 5 && (cozyRecordByDeviceId = CozyUtils.getCozyRecordByDeviceId(j)) != null) {
            cozyRecordByDeviceId.delete();
        }
        if (i == 6 && (d2RecordByDeviceId = D2Utils.getD2RecordByDeviceId(j)) != null) {
            d2RecordByDeviceId.delete();
        }
        if (i == 7 && (t3RecordByDeviceId = BleDeviceUtils.getT3RecordByDeviceId(j)) != null) {
            t3RecordByDeviceId.delete();
        }
        if (i == 8 && (k2RecordByDeviceId = BleDeviceUtils.getK2RecordByDeviceId(j)) != null) {
            k2RecordByDeviceId.delete();
        }
        if (i == 9 && (d3RecordByDeviceId = D3Utils.getD3RecordByDeviceId(j)) != null) {
            d3RecordByDeviceId.delete();
        }
        if (i == 10 && (aqRecordByDeviceId = AqUtils.getAqRecordByDeviceId(j)) != null) {
            aqRecordByDeviceId.delete();
        }
        if (i == 11 && (d4RecordByDeviceId = D4Utils.getD4RecordByDeviceId(j)) != null) {
            d4RecordByDeviceId.delete();
        }
        if (i == 20 && (d4sRecordByDeviceId = D4sUtils.getD4sRecordByDeviceId(j)) != null) {
            d4sRecordByDeviceId.delete();
        }
        if (i == 12 && (p3RecordByDeviceId = P3Utils.getP3RecordByDeviceId(j)) != null) {
            p3RecordByDeviceId.delete();
        }
        if (i == 14 && (w5RecordByDeviceId = W5Utils.getW5RecordByDeviceId(j)) != null) {
            w5RecordByDeviceId.delete();
        }
        if (i == 15 && (t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(j)) != null) {
            t4RecordByDeviceId.delete();
        }
        if (i == 16 && (k3RecordByDeviceId = K3Utils.getK3RecordByDeviceId(j)) != null) {
            k3RecordByDeviceId.delete();
        }
        if (i == 17 && (aqrRecordByDeviceId = AqrUtils.getAqrRecordByDeviceId(j)) != null) {
            aqrRecordByDeviceId.delete();
        }
        if (i == 18 && (r2RecordByDeviceId = R2Utils.getR2RecordByDeviceId(j)) != null) {
            r2RecordByDeviceId.delete();
        }
        if (i == 19 && (aqh1RecordByDeviceId = Aqh1Utils.getAqh1RecordByDeviceId(j)) != null) {
            aqh1RecordByDeviceId.delete();
        }
        if (i == 22 && (hgRecordByDeviceId = HgUtils.getHgRecordByDeviceId(j)) != null) {
            hgRecordByDeviceId.delete();
        }
        if (i == 25 && (d4shRecordByDeviceId2 = D4shUtils.getD4shRecordByDeviceId(j, 0)) != null) {
            d4shRecordByDeviceId2.delete();
        }
        if (i == 26 && (d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(j, 1)) != null) {
            d4shRecordByDeviceId.delete();
        }
        if (i == 24 && (cTW3RecordByDeviceId = CTW3Utils.getCTW3RecordByDeviceId(j)) != null) {
            cTW3RecordByDeviceId.delete();
        }
        if (i == 27 && (t6RecordByDeviceId2 = T6Utils.getT6RecordByDeviceId(j, 0)) != null) {
            t6RecordByDeviceId2.delete();
        }
        if (i == 21 && (t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(j, 1)) != null) {
            t6RecordByDeviceId.delete();
        }
        if (i == 28 && (t7RecordById = T7DataUtils.getInstance().getT7RecordById(j)) != null) {
            t7RecordById.delete();
        }
        if (i != 29 || (w7hRecordByDeviceId = W7hDataUtils.getInstance().getW7hRecordByDeviceId(j)) == null) {
            return;
        }
        w7hRecordByDeviceId.delete();
    }

    public void checkAndDeleteRecordThroughFamilyList(Context context, final List<FamilyInfor> list, final List<FamilyInfor> list2) {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.RecordHandleUtils.1
            @Override // java.lang.Runnable
            public void run() {
                List list3;
                List list4 = list2;
                if (list4 == null || list4.size() == 0 || (list3 = list) == null || list3.size() == 0) {
                    return;
                }
                for (FamilyInfor familyInfor : list2) {
                    if (list.contains(familyInfor)) {
                        for (FamilyInfor familyInfor2 : list) {
                            if (familyInfor2.getGroupId() == familyInfor.getGroupId() && familyInfor.getDeviceList() != null && familyInfor.getDeviceList().size() > 0) {
                                for (FamilyDeviceInfor familyDeviceInfor : familyInfor.getDeviceList()) {
                                    if (!familyInfor2.getDeviceList().contains(familyDeviceInfor)) {
                                        RecordHandleUtils.this.deleteDeviceRecord(familyDeviceInfor.getDeviceId(), familyDeviceInfor.getType());
                                    }
                                }
                            }
                        }
                    } else if (familyInfor.getDeviceList() != null && familyInfor.getDeviceList().size() > 0) {
                        for (FamilyDeviceInfor familyDeviceInfor2 : familyInfor.getDeviceList()) {
                            RecordHandleUtils.this.deleteDeviceRecord(familyDeviceInfor2.getDeviceId(), familyDeviceInfor2.getType());
                        }
                    }
                }
            }
        });
    }
}

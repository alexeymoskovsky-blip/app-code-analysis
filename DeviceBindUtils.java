package com.petkit.android.activities.device.utils;

import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqUpdateParam;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrRecord;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrUpdateParam;
import com.petkit.android.activities.petkitBleDevice.aqr.utils.AqrUtils;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Record;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3UpdateParam;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3Utils;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgRecord;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgUpdateParam;
import com.petkit.android.activities.petkitBleDevice.hg.utils.HgUtils;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Record;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3UpdateParam;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3UpdateParam;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Record;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2UpdateParam;
import com.petkit.android.activities.petkitBleDevice.r2.utils.R2Utils;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5UpdateParam;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceBindUtils {

    public interface DeviceBindResultListener {
        void bindFail(ErrorInfor errorInfor);

        void bindSuccess(DeviceInfos deviceInfos);

        void signupFail(ErrorInfor errorInfor);

        void signupSuccess(DeviceInfos deviceInfos);

        void uploadServerFinish();
    }

    public static DeviceInfos getDeviceInfo(int i, long j) {
        if (i != 10) {
            if (i == 12) {
                P3Record p3RecordByDeviceId = P3Utils.getP3RecordByDeviceId(j);
                return p3RecordByDeviceId == null ? new P3Record() : p3RecordByDeviceId;
            }
            if (i == 14) {
                W5Record orCreateW5RecordByDeviceId = W5Utils.getOrCreateW5RecordByDeviceId(j);
                return orCreateW5RecordByDeviceId == null ? new W5Record() : orCreateW5RecordByDeviceId;
            }
            if (i == 22) {
                HgRecord hgRecordByDeviceId = HgUtils.getHgRecordByDeviceId(j);
                return hgRecordByDeviceId == null ? new HgRecord() : hgRecordByDeviceId;
            }
            if (i == 24) {
                CTW3Record cTW3RecordByDeviceId = CTW3Utils.getCTW3RecordByDeviceId(j);
                return cTW3RecordByDeviceId == null ? new CTW3Record() : cTW3RecordByDeviceId;
            }
            if (i != 101 && i != 102) {
                switch (i) {
                    case 16:
                        K3Record orCreateK3RecordByDeviceId = K3Utils.getOrCreateK3RecordByDeviceId(j);
                        return orCreateK3RecordByDeviceId == null ? new K3Record() : orCreateK3RecordByDeviceId;
                    case 17:
                        AqrRecord orCreateAqrRecordByDeviceId = AqrUtils.getOrCreateAqrRecordByDeviceId(j);
                        return orCreateAqrRecordByDeviceId == null ? new AqrRecord() : orCreateAqrRecordByDeviceId;
                    case 18:
                        R2Record r2RecordByDeviceId = R2Utils.getR2RecordByDeviceId(j);
                        return r2RecordByDeviceId == null ? new R2Record() : r2RecordByDeviceId;
                    default:
                        return null;
                }
            }
        }
        AqRecord aqRecordByDeviceId = AqUtils.getAqRecordByDeviceId(j);
        return aqRecordByDeviceId == null ? new AqRecord() : aqRecordByDeviceId;
    }

    public static void update(BaseActivity baseActivity, int i, long j, DeviceInfos deviceInfos, DeviceBindResultListener deviceBindResultListener) {
        if (i == 10) {
            AqRecord aqRecord = (AqRecord) deviceInfos;
            HashMap map = new HashMap();
            map.put("id", String.valueOf(aqRecord.getDeviceId()));
            map.put("kv", new Gson().toJson(new AqUpdateParam(aqRecord)));
            WebModelRepository.getInstance().aqUpdate(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.2
                public AnonymousClass2() {
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    PetkitLog.d("aq1s update success");
                    LogcatStorageHelper.addLog("aq1s update success");
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("aq1s update fail " + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("aq1s update fail " + errorInfor.getMsg());
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }
            });
            return;
        }
        if (i == 12) {
            P3Record p3Record = (P3Record) deviceInfos;
            HashMap map2 = new HashMap();
            map2.put("deviceId", String.valueOf(j));
            map2.put("kv", new Gson().toJson(new P3UpdateParam(Integer.valueOf(p3Record.getVoltage()), Integer.valueOf(p3Record.getBattery()), Integer.valueOf(p3Record.getFirmware()), Integer.valueOf(p3Record.getHardware()), Integer.valueOf(p3Record.getSoundId()))));
            WebModelRepository.getInstance().p3Update(baseActivity, map2, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.1
                public AnonymousClass1() {
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    PetkitLog.d("p3 update success");
                    LogcatStorageHelper.addLog("p3 update success");
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("p3 update fail " + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("p3 update fail " + errorInfor.getMsg());
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }
            });
            return;
        }
        if (i == 14) {
            W5Record w5Record = (W5Record) deviceInfos;
            HashMap map3 = new HashMap();
            map3.put("id", String.valueOf(j));
            map3.put("kv", new Gson().toJson(new W5UpdateParam(w5Record)));
            if (w5Record.getHardware() == 0) {
                return;
            }
            WebModelRepository.getInstance().w5Update(baseActivity, map3, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.3
                public AnonymousClass3() {
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    PetkitLog.d("w5 update success");
                    LogcatStorageHelper.addLog("w5 update success");
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("w5 update fail " + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("w5 update fail " + errorInfor.getMsg());
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }
            });
            return;
        }
        if (i == 22) {
            HgRecord hgRecord = (HgRecord) deviceInfos;
            HashMap map4 = new HashMap();
            map4.put("id", String.valueOf(hgRecord.getDeviceId()));
            map4.put("kv", new Gson().toJson(new HgUpdateParam(hgRecord)));
            WebModelRepository.getInstance();
            WebModelRepository.hgUpdate(baseActivity, map4, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.7
                public AnonymousClass7() {
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    PetkitLog.d("hg update success");
                    LogcatStorageHelper.addLog("hg update success");
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("hg update fail " + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("hg update fail " + errorInfor.getMsg());
                    DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                    if (deviceBindResultListener2 != null) {
                        deviceBindResultListener2.uploadServerFinish();
                    }
                }
            });
            return;
        }
        if (i != 24) {
            switch (i) {
                case 16:
                    HashMap map5 = new HashMap();
                    map5.put("id", String.valueOf(j));
                    map5.put("kv", new Gson().toJson(new K3UpdateParam((K3Record) deviceInfos)));
                    WebModelRepository.getInstance().k3Update(baseActivity, map5, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.4
                        public AnonymousClass4() {
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(String str) {
                            PetkitLog.d("k3 update success");
                            LogcatStorageHelper.addLog("k3 update success");
                            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                            if (deviceBindResultListener2 != null) {
                                deviceBindResultListener2.uploadServerFinish();
                            }
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitLog.d("k3 update fail " + errorInfor.getMsg());
                            LogcatStorageHelper.addLog("k3 update fail " + errorInfor.getMsg());
                            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                            if (deviceBindResultListener2 != null) {
                                deviceBindResultListener2.uploadServerFinish();
                            }
                        }
                    });
                    break;
                case 17:
                    AqrRecord aqrRecord = (AqrRecord) deviceInfos;
                    HashMap map6 = new HashMap();
                    map6.put("id", String.valueOf(aqrRecord.getDeviceId()));
                    map6.put("kv", new Gson().toJson(new AqrUpdateParam(aqrRecord)));
                    WebModelRepository.getInstance().aqrUpdate(baseActivity, map6, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.5
                        public AnonymousClass5() {
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(String str) {
                            PetkitLog.d("aqr update success");
                            LogcatStorageHelper.addLog("aqr update success");
                            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                            if (deviceBindResultListener2 != null) {
                                deviceBindResultListener2.uploadServerFinish();
                            }
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitLog.d("aqr update fail " + errorInfor.getMsg());
                            LogcatStorageHelper.addLog("aqr update fail " + errorInfor.getMsg());
                            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                            if (deviceBindResultListener2 != null) {
                                deviceBindResultListener2.uploadServerFinish();
                            }
                        }
                    });
                    break;
                case 18:
                    R2Record r2Record = (R2Record) deviceInfos;
                    HashMap map7 = new HashMap();
                    map7.put("id", String.valueOf(r2Record.getDeviceId()));
                    map7.put("kv", new Gson().toJson(new R2UpdateParam(r2Record)));
                    WebModelRepository.getInstance();
                    WebModelRepository.r2Update(baseActivity, map7, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.6
                        public AnonymousClass6() {
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(String str) {
                            PetkitLog.d("r2 update success");
                            LogcatStorageHelper.addLog("r2 update success");
                            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                            if (deviceBindResultListener2 != null) {
                                deviceBindResultListener2.uploadServerFinish();
                            }
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitLog.d("r2 update fail " + errorInfor.getMsg());
                            LogcatStorageHelper.addLog("r2 update fail " + errorInfor.getMsg());
                            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                            if (deviceBindResultListener2 != null) {
                                deviceBindResultListener2.uploadServerFinish();
                            }
                        }
                    });
                    break;
            }
            return;
        }
        CTW3Record cTW3Record = (CTW3Record) deviceInfos;
        cTW3Record.setFirmware(CTW3Utils.getCTW3RecordByDeviceId(cTW3Record.getDeviceId()).getFirmware());
        HashMap map8 = new HashMap();
        map8.put("id", String.valueOf(cTW3Record.getDeviceId()));
        map8.put("kv", new Gson().toJson(new CTW3UpdateParam(cTW3Record)));
        if (cTW3Record.getHardware() == 0) {
            return;
        }
        WebModelRepository.getInstance();
        WebModelRepository.ctw3Update(baseActivity, map8, new PetkitCallback<String>() { // from class: com.petkit.android.activities.device.utils.DeviceBindUtils.8
            public AnonymousClass8() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("ctw3 update success");
                LogcatStorageHelper.addLog("ctw3 update success");
                DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                if (deviceBindResultListener2 != null) {
                    deviceBindResultListener2.uploadServerFinish();
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("ctw3 update fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("ctw3 update fail " + errorInfor.getMsg());
                DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
                if (deviceBindResultListener2 != null) {
                    deviceBindResultListener2.uploadServerFinish();
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$1 */
    public class AnonymousClass1 implements PetkitCallback<String> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("p3 update success");
            LogcatStorageHelper.addLog("p3 update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("p3 update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("p3 update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$2 */
    public class AnonymousClass2 implements PetkitCallback<String> {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("aq1s update success");
            LogcatStorageHelper.addLog("aq1s update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq1s update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq1s update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$3 */
    public class AnonymousClass3 implements PetkitCallback<String> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("w5 update success");
            LogcatStorageHelper.addLog("w5 update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("w5 update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("w5 update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$4 */
    public class AnonymousClass4 implements PetkitCallback<String> {
        public AnonymousClass4() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("k3 update success");
            LogcatStorageHelper.addLog("k3 update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("k3 update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("k3 update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$5 */
    public class AnonymousClass5 implements PetkitCallback<String> {
        public AnonymousClass5() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("aqr update success");
            LogcatStorageHelper.addLog("aqr update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aqr update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqr update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$6 */
    public class AnonymousClass6 implements PetkitCallback<String> {
        public AnonymousClass6() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("r2 update success");
            LogcatStorageHelper.addLog("r2 update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("r2 update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("r2 update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$7 */
    public class AnonymousClass7 implements PetkitCallback<String> {
        public AnonymousClass7() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("hg update success");
            LogcatStorageHelper.addLog("hg update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("hg update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("hg update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceBindUtils$8 */
    public class AnonymousClass8 implements PetkitCallback<String> {
        public AnonymousClass8() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("ctw3 update success");
            LogcatStorageHelper.addLog("ctw3 update success");
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("ctw3 update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("ctw3 update fail " + errorInfor.getMsg());
            DeviceBindResultListener deviceBindResultListener2 = deviceBindResultListener;
            if (deviceBindResultListener2 != null) {
                deviceBindResultListener2.uploadServerFinish();
            }
        }
    }
}

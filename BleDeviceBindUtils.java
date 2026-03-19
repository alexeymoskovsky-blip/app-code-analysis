package com.petkit.android.activities.petkitBleDevice.utils;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.mall.mode.PurchaseEntranceData;
import com.petkit.android.activities.petkitBleDevice.aqh1.mode.Aqh1Record;
import com.petkit.android.activities.petkitBleDevice.aqh1.utils.Aqh1Utils;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.mode.DeviceServerConfigs;
import com.petkit.android.activities.petkitBleDevice.mode.K2Record;
import com.petkit.android.activities.petkitBleDevice.mode.T3Record;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hDataUtils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.DeviceScanResult;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class BleDeviceBindUtils {

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$1 */
    public class AnonymousClass1 extends TypeToken<List<DeviceServerConfigs>> {
    }

    public interface BleDeviceBindResultListener {
        void bindFail(ErrorInfor errorInfor);

        void bindSuccess(DeviceInfos deviceInfos);

        void getDeviceStateFail();

        void getDeviceStateSuccess(DeviceInfos deviceInfos);

        void signupStatusFail(ErrorInfor errorInfor);

        void signupStatusGoToBind(DeviceInfos deviceInfos);

        void signupStatusGoToFailedSpecificActivity(String str);

        void signupStatusResultEmpty();

        void signupStatusnullFail();

        void verifyLegalityFail(String str);

        void verifyLegalitySuccess();
    }

    public static void bindDevice(BaseActivity baseActivity, int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
    }

    public static void getDeviceState(BaseActivity baseActivity, boolean z, int i, DeviceInfos deviceInfos, String str, String str2, BleDeviceBindResultListener bleDeviceBindResultListener) {
    }

    public static boolean isHasInstruction(int i) {
        if (i == 7 || i == 9 || i == 11 || i == 15) {
            return true;
        }
        switch (i) {
            case 20:
            case 21:
            case 22:
                return true;
            default:
                switch (i) {
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static boolean isNeedInitLanguage(int i) {
        return i == 27 || i == 28 || i == 21 || i == 15 || i == 7 || i == 9;
    }

    public static boolean isNeedVerifyLegality(int i) {
        return false;
    }

    public static boolean isOldDevice(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSupport5G(int i) {
        return i == 21 || i == 27 || i == 28;
    }

    public static void signupStatus(BaseActivity baseActivity, boolean z, boolean z2, int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
    }

    public static boolean isSupport5G(int i, DeviceScanResult deviceScanResult) {
        if (i != 21) {
            switch (i) {
                case 25:
                case 26:
                    return DeviceUtils.isD4shDeviceSupport5g(deviceScanResult);
                case 27:
                case 28:
                case 29:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public static boolean checkDeviceNetInfo(DeviceInfos deviceInfos) {
        return (deviceInfos == null || TextUtils.isEmpty(deviceInfos.getIp()) || TextUtils.isEmpty(deviceInfos.getBssid()) || TextUtils.isEmpty(deviceInfos.getSsid()) || TextUtils.isEmpty(String.valueOf(deviceInfos.getRsq()))) ? false : true;
    }

    public static DeviceInfos getDeviceInfo(int i, long j) {
        if (i == 7) {
            T3Record t3RecordByDeviceId = BleDeviceUtils.getT3RecordByDeviceId(j);
            return t3RecordByDeviceId == null ? new T3Record() : t3RecordByDeviceId;
        }
        if (i != 8) {
            if (i == 9) {
                D3Record d3RecordByDeviceId = D3Utils.getD3RecordByDeviceId(j);
                return d3RecordByDeviceId == null ? new D3Record() : d3RecordByDeviceId;
            }
            if (i == 11) {
                D4Record d4RecordByDeviceId = D4Utils.getD4RecordByDeviceId(j);
                return d4RecordByDeviceId == null ? new D4Record() : d4RecordByDeviceId;
            }
            if (i != 15) {
                switch (i) {
                    case 19:
                        Aqh1Record aqh1RecordByDeviceId = Aqh1Utils.getAqh1RecordByDeviceId(j);
                        return aqh1RecordByDeviceId == null ? new Aqh1Record() : aqh1RecordByDeviceId;
                    case 20:
                        D4sRecord d4sRecordByDeviceId = D4sUtils.getD4sRecordByDeviceId(j);
                        return d4sRecordByDeviceId == null ? new D4sRecord() : d4sRecordByDeviceId;
                    case 21:
                        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(j, 1);
                        if (t6RecordByDeviceId != null) {
                            return t6RecordByDeviceId;
                        }
                        T6Record t6Record = new T6Record();
                        t6Record.setTypeCode(1);
                        return t6Record;
                    default:
                        switch (i) {
                            case 25:
                                D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(j, 0);
                                return d4shRecordByDeviceId == null ? new D4shRecord() : d4shRecordByDeviceId;
                            case 26:
                                D4shRecord d4shRecordByDeviceId2 = D4shUtils.getD4shRecordByDeviceId(j, 1);
                                return d4shRecordByDeviceId2 == null ? new D4shRecord(1) : d4shRecordByDeviceId2;
                            case 27:
                                T6Record t6RecordByDeviceId2 = T6Utils.getT6RecordByDeviceId(j, 0);
                                if (t6RecordByDeviceId2 != null) {
                                    return t6RecordByDeviceId2;
                                }
                                T6Record t6Record2 = new T6Record();
                                t6Record2.setTypeCode(0);
                                return t6Record2;
                            case 28:
                                T7Record t7RecordById = T7DataUtils.getInstance().getT7RecordById(j);
                                return t7RecordById == null ? new T7Record() : t7RecordById;
                            case 29:
                                break;
                            default:
                                return null;
                        }
                        break;
                }
            } else {
                T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(j);
                return t4RecordByDeviceId == null ? new T4Record() : t4RecordByDeviceId;
            }
        } else if (BleDeviceUtils.getK2RecordByDeviceId(j) == null) {
            new K2Record();
        }
        W7hRecord w7hRecordByDeviceId = W7hDataUtils.getInstance().getW7hRecordByDeviceId(j);
        return w7hRecordByDeviceId == null ? new W7hRecord() : w7hRecordByDeviceId;
    }

    public static void reportBindSuccess(Context context, int i) {
        if (i == 7) {
            HashMap map = new HashMap();
            map.put("type", "t3");
            map.put("result", "success");
            return;
        }
        if (i == 8) {
            HashMap map2 = new HashMap();
            map2.put("type", "k2");
            map2.put("result", "success");
            return;
        }
        if (i == 9) {
            HashMap map3 = new HashMap();
            map3.put("type", "d3");
            map3.put("result", "success");
            return;
        }
        if (i == 11) {
            HashMap map4 = new HashMap();
            map4.put("type", "d4");
            map4.put("result", "success");
            return;
        }
        if (i == 15) {
            HashMap map5 = new HashMap();
            map5.put("type", "t4");
            map5.put("result", "success");
            return;
        }
        if (i == 19) {
            HashMap map6 = new HashMap();
            map6.put("type", "aqh1");
            map6.put("result", "success");
            return;
        }
        if (i == 20) {
            HashMap map7 = new HashMap();
            map7.put("type", "d4s");
            map7.put("result", "success");
        } else if (i == 25) {
            HashMap map8 = new HashMap();
            map8.put("type", "d4sh");
            map8.put("result", "success");
        } else {
            if (i != 26) {
                return;
            }
            HashMap map9 = new HashMap();
            map9.put("type", "d4h");
            map9.put("result", "success");
        }
    }

    public static void reportMsg(Context context, int i, int i2, String str) {
        if (i == 7) {
            HashMap map = new HashMap();
            map.put("type", "t3");
            map.put("result", "fail");
            if (i2 != 0) {
                map.put("status", String.valueOf(i2));
            }
            map.put("msg", str);
            return;
        }
        if (i == 8) {
            HashMap map2 = new HashMap();
            map2.put("type", "k2");
            map2.put("result", "fail");
            if (i2 != 0) {
                map2.put("status", String.valueOf(i2));
            }
            map2.put("msg", str);
            return;
        }
        if (i == 9) {
            HashMap map3 = new HashMap();
            map3.put("type", "d3");
            map3.put("result", "fail");
            if (i2 != 0) {
                map3.put("status", String.valueOf(i2));
            }
            map3.put("msg", str);
            return;
        }
        if (i == 11) {
            HashMap map4 = new HashMap();
            map4.put("type", "d4");
            map4.put("result", "fail");
            if (i2 != 0) {
                map4.put("status", String.valueOf(i2));
            }
            map4.put("msg", str);
            return;
        }
        if (i == 15) {
            HashMap map5 = new HashMap();
            map5.put("type", "t4");
            map5.put("result", "fail");
            if (i2 != 0) {
                map5.put("status", String.valueOf(i2));
            }
            map5.put("msg", str);
            return;
        }
        if (i == 19) {
            HashMap map6 = new HashMap();
            map6.put("type", "aqh1");
            map6.put("result", "fail");
            if (i2 != 0) {
                map6.put("status", String.valueOf(i2));
            }
            map6.put("msg", str);
            return;
        }
        if (i == 20) {
            HashMap map7 = new HashMap();
            map7.put("type", "d4s");
            map7.put("result", "fail");
            if (i2 != 0) {
                map7.put("status", String.valueOf(i2));
            }
            map7.put("msg", str);
            return;
        }
        if (i == 25) {
            HashMap map8 = new HashMap();
            map8.put("type", "d4sh");
            map8.put("result", "fail");
            if (i2 != 0) {
                map8.put("status", String.valueOf(i2));
            }
            map8.put("msg", str);
            return;
        }
        if (i != 26) {
            return;
        }
        HashMap map9 = new HashMap();
        map9.put("type", "d4h");
        map9.put("result", "fail");
        if (i2 != 0) {
            map9.put("status", String.valueOf(i2));
        }
        map9.put("msg", str);
    }

    public static String getPurchaseEntrance(int i, int i2) {
        PurchaseEntranceData purchaseEntranceData = (PurchaseEntranceData) new Gson().fromJson(CommonUtils.getSysMap(Consts.PURCHASE_ENTRANCE_DATA), PurchaseEntranceData.class);
        if (purchaseEntranceData != null) {
            if (i != 7) {
                if (i == 8) {
                    if (purchaseEntranceData.getK2() == null) {
                        return "";
                    }
                    return purchaseEntranceData.getK2().getShareUrl();
                }
                if (i == 9) {
                    if (purchaseEntranceData.getD3() == null) {
                        return "";
                    }
                    return purchaseEntranceData.getD3().getShareUrl();
                }
                if (i == 11) {
                    if (purchaseEntranceData.getD4() == null) {
                        return "";
                    }
                    return purchaseEntranceData.getD4().getShareUrl();
                }
                if (i == 15) {
                    if (i2 == 2) {
                        if (purchaseEntranceData.getT4_2() == null) {
                            return "";
                        }
                        return purchaseEntranceData.getT4_2().getShareUrl();
                    }
                    if (purchaseEntranceData.getT4() == null) {
                        return "";
                    }
                    return purchaseEntranceData.getT4().getShareUrl();
                }
                if (i == 16) {
                    if (purchaseEntranceData.getK3() == null) {
                        return "";
                    }
                    return purchaseEntranceData.getK3().getShareUrl();
                }
                if (i == 20) {
                    if (purchaseEntranceData.getD4S() == null) {
                        return "";
                    }
                    return purchaseEntranceData.getD4S().getShareUrl();
                }
                if (i == 21) {
                    if (purchaseEntranceData.getT5() == null) {
                        return "";
                    }
                    return purchaseEntranceData.getT5().getShareUrl();
                }
                switch (i) {
                    case 25:
                        if (purchaseEntranceData.getD4SH() == null) {
                            return "";
                        }
                        return purchaseEntranceData.getD4SH().getShareUrl();
                    case 26:
                        if (purchaseEntranceData.getD4H() == null) {
                            return "";
                        }
                        return purchaseEntranceData.getD4H().getShareUrl();
                    case 27:
                        if (purchaseEntranceData.getT6() == null) {
                            return "";
                        }
                        return purchaseEntranceData.getT6().getShareUrl();
                    case 28:
                        if (purchaseEntranceData.getT7() == null) {
                            return "";
                        }
                        return purchaseEntranceData.getT7().getShareUrl();
                    default:
                        return "";
                }
            }
            if (purchaseEntranceData.getT3() != null) {
                return purchaseEntranceData.getT3().getShareUrl();
            }
        }
        return "";
    }

    public static String getInstallVideoKey(int i) {
        if (i == 27) {
            return "t6_camera_video";
        }
        if (i == 28) {
            return "t7_camera_video";
        }
        return "";
    }

    public static String getBindVideoKey(int i) {
        switch (i) {
            case 7:
                return "t3_bind_video";
            case 8:
                return "k2_bind_video";
            case 9:
                return "d3_bind_video";
            case 10:
            case 12:
            case 14:
            case 17:
            case 18:
            case 22:
            case 23:
            case 24:
            default:
                return "";
            case 11:
                return "d4_bind_video";
            case 13:
                return "h2_bind_video";
            case 15:
                return "t4_bind_video";
            case 16:
                return "k3_bind_video";
            case 19:
                return "aqh1_bind_video";
            case 20:
                return "d4s_bind_video";
            case 21:
                return "t5_bind_video";
            case 25:
                return "d4sh_bind_video";
            case 26:
                return "d4h_bind_video";
            case 27:
                return "t6_bind_video";
            case 28:
                return "t7_bind_video";
        }
    }

    public static DeviceServerConfigs getDeviceServerConfigsByType(int i) {
        List<DeviceServerConfigs> list = (List) new Gson().fromJson(CommonUtils.getSysMap(Consts.SP_DEVICE_SERVER_CONFIGS), new TypeToken<List<DeviceServerConfigs>>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.1
        }.getType());
        if (list == null) {
            return null;
        }
        for (DeviceServerConfigs deviceServerConfigs : list) {
            if (deviceServerConfigs.getDeviceTypeId() == i) {
                return deviceServerConfigs;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$2 */
    public class AnonymousClass2 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass2(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$3 */
    public class AnonymousClass3 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass3(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$4 */
    public class AnonymousClass4 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass4(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$5 */
    public class AnonymousClass5 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass5(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$6 */
    public class AnonymousClass6 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass6(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$7 */
    public class AnonymousClass7 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass7(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$8 */
    public class AnonymousClass8 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass8(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$9 */
    public class AnonymousClass9 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass9(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils$10 */
    public class AnonymousClass10 implements PetkitCallback<String> {
        public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
        public final /* synthetic */ DeviceInfos val$deviceInfos;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass10(int i, DeviceInfos deviceInfos, BleDeviceBindResultListener bleDeviceBindResultListener) {
            i = i;
            deviceInfos = deviceInfos;
            bleDeviceBindResultListener = bleDeviceBindResultListener;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            if ("success".equals(str)) {
                PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
                if (bleDeviceBindResultListener != null) {
                    bleDeviceBindResultListener.verifyLegalitySuccess();
                    return;
                }
                return;
            }
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
            BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener2 != null) {
                bleDeviceBindResultListener2.verifyLegalityFail(str);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
            BleDeviceBindResultListener bleDeviceBindResultListener = bleDeviceBindResultListener;
            if (bleDeviceBindResultListener != null) {
                bleDeviceBindResultListener.verifyLegalityFail(errorInfor.getMsg());
            }
        }
    }

    public static void verifyLegality(BaseActivity baseActivity, int i, DeviceInfos deviceInfos, HashMap<String, String> map, BleDeviceBindResultListener bleDeviceBindResultListener) {
        if (i == 6) {
            WebModelRepository.getInstance().verifyLegalityD2(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.2
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass2(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
            return;
        }
        if (i2 == 7) {
            WebModelRepository.getInstance().verifyLegalityT3(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.6
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass6(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
            return;
        }
        if (i2 == 9) {
            WebModelRepository.getInstance().verifyLegalityD3(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.3
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass3(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
            return;
        }
        if (i2 == 11) {
            WebModelRepository.getInstance().verifyLegalityD4(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.4
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass4(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
            return;
        }
        if (i2 == 15) {
            WebModelRepository.getInstance().verifyLegalityT4(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.7
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass7(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
            return;
        }
        if (i2 == 19) {
            WebModelRepository.getInstance().verifyLegalityAQH1(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.8
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass8(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
            return;
        }
        if (i2 == 20) {
            WebModelRepository.getInstance().verifyLegalityD4S(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.5
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass5(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
        } else if (i2 == 25) {
            WebModelRepository.getInstance().verifyLegalityD4SH(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.9
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass9(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
        } else {
            if (i2 != 26) {
                return;
            }
            WebModelRepository.getInstance().verifyLegalityD4H(baseActivity, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils.10
                public final /* synthetic */ BleDeviceBindResultListener val$bleDeviceBindResultListener;
                public final /* synthetic */ DeviceInfos val$deviceInfos;
                public final /* synthetic */ int val$deviceType;

                public AnonymousClass10(int i2, DeviceInfos deviceInfos2, BleDeviceBindResultListener bleDeviceBindResultListener2) {
                    i = i2;
                    deviceInfos = deviceInfos2;
                    bleDeviceBindResultListener = bleDeviceBindResultListener2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(String str) {
                    if ("success".equals(str)) {
                        PetkitLog.d("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        LogcatStorageHelper.addLog("verifyLegality success:" + i + " " + deviceInfos.getDeviceId());
                        BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                        if (bleDeviceBindResultListener2 != null) {
                            bleDeviceBindResultListener2.verifyLegalitySuccess();
                            return;
                        }
                        return;
                    }
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + str);
                    BleDeviceBindResultListener bleDeviceBindResultListener22 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener22 != null) {
                        bleDeviceBindResultListener22.verifyLegalityFail(str);
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    PetkitLog.d("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    LogcatStorageHelper.addLog("verifyLegality error:" + i + " " + deviceInfos.getDeviceId() + " " + errorInfor.getCode() + errorInfor.getMsg());
                    BleDeviceBindResultListener bleDeviceBindResultListener2 = bleDeviceBindResultListener;
                    if (bleDeviceBindResultListener2 != null) {
                        bleDeviceBindResultListener2.verifyLegalityFail(errorInfor.getMsg());
                    }
                }
            });
        }
    }
}

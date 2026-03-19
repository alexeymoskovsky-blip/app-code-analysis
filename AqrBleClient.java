package com.petkit.android.activities.petkitBleDevice.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.petkit.android.activities.device.utils.DeviceBindUtils;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrConfig;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrDevice;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrRecord;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrUpdateParam;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.RefreshAqrMsg;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.UpdateAqrMsg;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.AqrDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.k3.mode.SnResult;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.DeviceScanResult;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.util.HashMap;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class AqrBleClient extends PetkitBleClient {
    public static final String TAG = "AQR_OTA";
    public boolean isCheckConfig;
    public DeviceBindUtils.DeviceBindResultListener listener;
    public DeviceScanResult result;

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void getConfig() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public boolean isAqDevice() {
        return true;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processStreamData(Map<Integer, PetkitBleMsg> map) {
    }

    @Override // com.petkit.android.activities.device.utils.DeviceBindUtils.DeviceBindResultListener
    public void uploadServerFinish() {
    }

    public AqrBleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public AqrBleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d("AQR_OTA", "findSn");
        LogcatStorageHelper.addLog("findSn start");
        HashMap map = new HashMap();
        map.put("mac", this.deviceInfos.getMac());
        WebModelRepository.getInstance().aqrFindSn((BaseActivity) this.mContext, map, new PetkitCallback<SnResult>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(SnResult snResult) {
                if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                    AqrBleClient.this.signupFail(new ErrorInfor(33, "get aqrSn from server null"));
                    LogcatStorageHelper.addLog("get aqrSn from server null " + snResult.toString());
                    PetkitLog.d("get aqrSn from server null");
                    return;
                }
                PetkitLog.d("aqSn success " + snResult.toString());
                LogcatStorageHelper.addLog("aqrSn success " + snResult.toString());
                AqrBleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq aqrFindSn fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aqrFindSn fail " + errorInfor.getMsg());
                AqrBleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<SnResult> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(SnResult snResult) {
            if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                AqrBleClient.this.signupFail(new ErrorInfor(33, "get aqrSn from server null"));
                LogcatStorageHelper.addLog("get aqrSn from server null " + snResult.toString());
                PetkitLog.d("get aqrSn from server null");
                return;
            }
            PetkitLog.d("aqSn success " + snResult.toString());
            LogcatStorageHelper.addLog("aqrSn success " + snResult.toString());
            AqrBleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq aqrFindSn fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqrFindSn fail " + errorInfor.getMsg());
            AqrBleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onReceiveCustomData(PetkitBleMsg petkitBleMsg) {
        super.onReceiveCustomData(petkitBleMsg);
        PetkitLog.d("AQR_OTA", "onReceiveCustomData");
        if (petkitBleMsg != null && this.deviceInfos != null) {
            if (2 == petkitBleMsg.getType()) {
                this.deviceInfos = AqrDataConvertor.parseBleMsgData(petkitBleMsg, (AqrRecord) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd = petkitBleMsg.getCmd();
                if (cmd == 240) {
                    postCustomData(nextBleMsg());
                    return;
                }
                switch (cmd) {
                    case 210:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            postCustomData(AqrDataConvertor.getTimingSetting());
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 211:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            postCustomData(AqrDataConvertor.getOtherSetting());
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 212:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            this.deviceInfos.save();
                            changeLinkStatus(2);
                            onConnectSuccess(this.deviceInfos);
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    default:
                        switch (cmd) {
                            case PetkitBLEConsts.CMD_PETKIT_BLE_220 /* 220 */:
                            case PetkitBLEConsts.CMD_PETKIT_BLE_221 /* 221 */:
                                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                                break;
                            case PetkitBLEConsts.CMD_PETKIT_BLE_222 /* 222 */:
                                if (this.isCheckConfig) {
                                    this.isCheckConfig = false;
                                    ((AqrRecord) this.deviceInfos).save();
                                    updateDeviceInfo();
                                }
                                if (this.deviceInfos.getDeviceId() != -1) {
                                    onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                                } else {
                                    onError(this, petkitBleMsg.getCmd());
                                }
                                break;
                        }
                        break;
                }
            }
            if (1 == petkitBleMsg.getType()) {
                this.deviceInfos = AqrDataConvertor.parseBleMsgData(petkitBleMsg, (AqrRecord) this.deviceInfos, (BaseActivity) this.mContext);
                if (petkitBleMsg.getCmd() != 230) {
                    return;
                }
                if (this.deviceInfos.getDeviceId() != -1) {
                    this.deviceInfos.save();
                    EventBus.getDefault().post(new RefreshAqrMsg((AqrRecord) this.deviceInfos, "success", true));
                    postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_230, ByteUtil.toByte(1), 2, petkitBleMsg.getSequence()));
                    HashMap map = new HashMap();
                    map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
                    map.put("kv", new Gson().toJson(new AqrUpdateParam((AqrRecord) this.deviceInfos)));
                    EventBus.getDefault().post(new UpdateAqrMsg(map));
                    return;
                }
                onError(this, petkitBleMsg.getCmd());
                return;
            }
            return;
        }
        PetkitLog.d("data bytes is null");
        LogcatStorageHelper.addLog("data bytes is null");
        PetkitLog.d("AQR_OTA", "onReceiveCustomData msg:" + petkitBleMsg + " deviceInfos:" + this.deviceInfos);
        LogcatStorageHelper.addLog("onReceiveCustomData msg:" + petkitBleMsg + " deviceInfos:" + this.deviceInfos);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void updateDeviceLog(PetkitBleMsg petkitBleMsg) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceInfos.getDeviceId()));
        byte[] bArr = new byte[2];
        System.arraycopy(petkitBleMsg.getData(), 0, bArr, 0, 2);
        map.put("restartTimes", String.valueOf((int) ByteUtil.bytes2Short(bArr)));
        byte[] bArr2 = new byte[4];
        System.arraycopy(petkitBleMsg.getData(), 2, bArr2, 0, 4);
        map.put("runTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        System.arraycopy(petkitBleMsg.getData(), 6, bArr2, 0, 4);
        map.put("whiteLightTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        System.arraycopy(petkitBleMsg.getData(), 10, bArr2, 0, 4);
        map.put("colorLightTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        System.arraycopy(petkitBleMsg.getData(), 14, bArr2, 0, 4);
        map.put("pumpTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        map.put("testResult", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[18])));
        map.put("testResultCode", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[19])));
        WebModelRepository.getInstance().aqrSaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient.2
            public AnonymousClass2() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save Log:" + str);
                LogcatStorageHelper.addLog("aqr save Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient$2 */
    public class AnonymousClass2 implements PetkitCallback<String> {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save Log:" + str);
            LogcatStorageHelper.addLog("aqr save Log:" + str);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("save Log error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("save Log error:" + errorInfor.getMsg());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onError(int i) {
        onError(this, i);
    }

    public void getAqrConfig() {
        this.isCheckConfig = true;
        HashMap map = new HashMap();
        map.put("id", String.valueOf(((AqrRecord) this.deviceInfos).getDeviceId()));
        WebModelRepository.getInstance().getAqrConfig((BaseActivity) this.mContext, map, new PetkitCallback<AqrConfig>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqrConfig aqrConfig) {
                if (aqrConfig.equals(((AqrRecord) AqrBleClient.this.deviceInfos).getConfig())) {
                    AqrBleClient.this.isCheckConfig = false;
                    AqrBleClient.this.updateDeviceInfo();
                } else {
                    ((AqrRecord) AqrBleClient.this.deviceInfos).setConfig(aqrConfig);
                    AqrBleClient aqrBleClient = AqrBleClient.this;
                    aqrBleClient.postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_222, AqrDataConvertor.splicingData(PetkitBLEConsts.CMD_PETKIT_BLE_222, (AqrRecord) aqrBleClient.deviceInfos)));
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aqr getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aqr getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
                EventBus.getDefault().post(new RefreshAqrMsg((AqrRecord) AqrBleClient.this.deviceInfos, errorInfor.getMsg()));
                AqrBleClient.this.isCheckConfig = false;
                AqrBleClient.this.updateDeviceInfo();
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<AqrConfig> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqrConfig aqrConfig) {
            if (aqrConfig.equals(((AqrRecord) AqrBleClient.this.deviceInfos).getConfig())) {
                AqrBleClient.this.isCheckConfig = false;
                AqrBleClient.this.updateDeviceInfo();
            } else {
                ((AqrRecord) AqrBleClient.this.deviceInfos).setConfig(aqrConfig);
                AqrBleClient aqrBleClient = AqrBleClient.this;
                aqrBleClient.postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_222, AqrDataConvertor.splicingData(PetkitBLEConsts.CMD_PETKIT_BLE_222, (AqrRecord) aqrBleClient.deviceInfos)));
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aqr getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqr getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
            EventBus.getDefault().post(new RefreshAqrMsg((AqrRecord) AqrBleClient.this.deviceInfos, errorInfor.getMsg()));
            AqrBleClient.this.isCheckConfig = false;
            AqrBleClient.this.updateDeviceInfo();
        }
    }

    public void updateDeviceInfo() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(((AqrRecord) this.deviceInfos).getDeviceId()));
        map.put("kv", new Gson().toJson(new AqrUpdateParam((AqrRecord) this.deviceInfos)));
        WebModelRepository.getInstance().aqrUpdate((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient.4
            public AnonymousClass4() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("aqrUpdate：" + str);
                LogcatStorageHelper.addLog("aqrUpdate：" + str);
                if ("success".equals(str)) {
                    ((AqrRecord) AqrBleClient.this.deviceInfos).save();
                    EventBus.getDefault().post(new RefreshAqrMsg((AqrRecord) AqrBleClient.this.deviceInfos, str));
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
                EventBus.getDefault().post(new RefreshAqrMsg((AqrRecord) AqrBleClient.this.deviceInfos, errorInfor.getMsg()));
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient$4 */
    public class AnonymousClass4 implements PetkitCallback<String> {
        public AnonymousClass4() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("aqrUpdate：" + str);
            LogcatStorageHelper.addLog("aqrUpdate：" + str);
            if ("success".equals(str)) {
                ((AqrRecord) AqrBleClient.this.deviceInfos).save();
                EventBus.getDefault().post(new RefreshAqrMsg((AqrRecord) AqrBleClient.this.deviceInfos, str));
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
            EventBus.getDefault().post(new RefreshAqrMsg((AqrRecord) AqrBleClient.this.deviceInfos, errorInfor.getMsg()));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void signup() {
        PetkitLog.d("AQR_OTA", "signup");
        HashMap map = new HashMap();
        if (this.deviceInfos.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
        }
        map.put("mac", this.deviceInfos.getMac());
        map.put("sn", this.deviceInfos.getSn());
        WebModelRepository.getInstance().aqrSignup((BaseActivity) this.mContext, map, new PetkitCallback<AqrDevice>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient.5
            public AnonymousClass5() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqrDevice aqrDevice) {
                PetkitLog.d("aqrSignup success");
                LogcatStorageHelper.addLog("aqrSignup success");
                if (aqrDevice == null) {
                    AqrBleClient.this.signupFail(new ErrorInfor(33, "aqrDevice is null"));
                    return;
                }
                AqrRecord aqrRecord = new AqrRecord(aqrDevice);
                LogcatStorageHelper.addLog("signup deviceId:" + AqrBleClient.this.deviceInfos.getDeviceId());
                AqrBleClient.this.signupSuccess(aqrRecord);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aqr signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aqr signup fail " + errorInfor.getMsg());
                AqrBleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqrBleClient$5 */
    public class AnonymousClass5 implements PetkitCallback<AqrDevice> {
        public AnonymousClass5() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqrDevice aqrDevice) {
            PetkitLog.d("aqrSignup success");
            LogcatStorageHelper.addLog("aqrSignup success");
            if (aqrDevice == null) {
                AqrBleClient.this.signupFail(new ErrorInfor(33, "aqrDevice is null"));
                return;
            }
            AqrRecord aqrRecord = new AqrRecord(aqrDevice);
            LogcatStorageHelper.addLog("signup deviceId:" + AqrBleClient.this.deviceInfos.getDeviceId());
            AqrBleClient.this.signupSuccess(aqrRecord);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aqr signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqr signup fail " + errorInfor.getMsg());
            AqrBleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d("AQR_OTA", "securityCheckFinish");
        postCustomData(AqrDataConvertor.getAqConfig());
    }
}

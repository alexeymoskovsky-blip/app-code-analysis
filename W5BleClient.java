package com.petkit.android.activities.petkitBleDevice.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.jess.arms.base.BaseActivity;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.home.mode.DeviceStateUpdateEvent;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.ble.mode.W5DataConvertor;
import com.petkit.android.activities.petkitBleDevice.k3.mode.SnResult;
import com.petkit.android.activities.petkitBleDevice.w5.mode.RefreshW5DataEvent;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Device;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class W5BleClient extends PetkitBleClient implements PetkitBleListener {
    public static final String TAG = "W5_OTA";

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void getConfig() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public boolean isAqDevice() {
        return false;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processStreamData(Map<Integer, PetkitBleMsg> map) {
    }

    @Override // com.petkit.android.activities.device.utils.DeviceBindUtils.DeviceBindResultListener
    public void uploadServerFinish() {
    }

    public W5BleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public W5BleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d(TAG, "findSn");
        LogcatStorageHelper.addLog("findSn start");
        HashMap map = new HashMap();
        map.put("mac", this.deviceInfos.getMac());
        WebModelRepository.getInstance().w5FindSn((BaseActivity) this.mContext, map, new PetkitCallback<SnResult>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.W5BleClient.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(SnResult snResult) {
                if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                    W5BleClient.this.signupFail(new ErrorInfor(33, "get w5Sn from server null"));
                    LogcatStorageHelper.addLog("get w5Sn from server null " + snResult.toString());
                    PetkitLog.d("get w5Sn from server null");
                    return;
                }
                PetkitLog.d("aqSn success " + snResult.toString());
                LogcatStorageHelper.addLog("w5Sn success " + snResult.toString());
                W5BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d(" w5FindSn fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("w5FindSn fail " + errorInfor.getMsg());
                W5BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.W5BleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<SnResult> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(SnResult snResult) {
            if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                W5BleClient.this.signupFail(new ErrorInfor(33, "get w5Sn from server null"));
                LogcatStorageHelper.addLog("get w5Sn from server null " + snResult.toString());
                PetkitLog.d("get w5Sn from server null");
                return;
            }
            PetkitLog.d("aqSn success " + snResult.toString());
            LogcatStorageHelper.addLog("w5Sn success " + snResult.toString());
            W5BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d(" w5FindSn fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("w5FindSn fail " + errorInfor.getMsg());
            W5BleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void onOtaStateChanged(int i, int i2) {
        super.onOtaStateChanged(i, i2);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onReceiveCustomData(PetkitBleMsg petkitBleMsg) {
        super.onReceiveCustomData(petkitBleMsg);
        if (petkitBleMsg != null && this.deviceInfos != null) {
            if (2 == petkitBleMsg.getType()) {
                this.deviceInfos = W5DataConvertor.parseBleMsgData(petkitBleMsg, (W5Record) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd = petkitBleMsg.getCmd();
                if (cmd == 66) {
                    if (this.deviceInfos.getDeviceId() != -1) {
                        postCustomData(W5DataConvertor.getW5Setting());
                        return;
                    } else {
                        onError(this, petkitBleMsg.getCmd());
                        return;
                    }
                }
                if (cmd == 240) {
                    postCustomData(nextBleMsg());
                    return;
                }
                if (cmd == 210) {
                    if (this.deviceInfos.getDeviceId() != -1) {
                        if (this.petkitBleData.getBleMsgList().size() > 0) {
                            postCustomData(nextBleMsg());
                            return;
                        } else {
                            this.deviceInfos.save();
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                            return;
                        }
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd == 211) {
                    if (this.deviceInfos.getDeviceId() != -1) {
                        this.deviceInfos.save();
                        if (BLEConsts.W4X_NAME.equals(this.device.getName()) || BLEConsts.W4X_UVC_NAME.equals(this.device.getName()) || BLEConsts.W5N_NAME.equals(this.device.getName()) || BLEConsts.W5C_NAME.equals(this.device.getName()) || BLEConsts.CTW2_NAME.equals(this.device.getName())) {
                            if (BLEConsts.W5C_NAME.equals(this.device.getName())) {
                                W5Record w5Record = (W5Record) this.deviceInfos;
                                if (new BigDecimal(w5Record.getHardware() + ((w5Record.getFirmware() * 1.0f) / 100.0f)).setScale(2, 4).doubleValue() >= 1.26d) {
                                    postCustomData(W5DataConvertor.getW5NewSetting());
                                } else {
                                    onConnectSuccess(this.deviceInfos);
                                }
                            } else {
                                W5Record w5Record2 = (W5Record) this.deviceInfos;
                                if (new BigDecimal(w5Record2.getHardware() + ((w5Record2.getFirmware() * 1.0f) / 100.0f)).setScale(2, 4).doubleValue() >= 2.43d) {
                                    postCustomData(W5DataConvertor.getW5NewSetting());
                                } else {
                                    onConnectSuccess(this.deviceInfos);
                                }
                            }
                        } else {
                            onConnectSuccess(this.deviceInfos);
                        }
                        onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        return;
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd == 215) {
                    PetkitLog.d("W5 ble receive CMD_PETKIT_BLE_215");
                    if (this.deviceInfos.getDeviceId() != -1) {
                        if (this.petkitBleData.getBleMsgList().size() > 0) {
                            postCustomData(nextBleMsg());
                            return;
                        } else {
                            this.deviceInfos.save();
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                            return;
                        }
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd == 216) {
                    PetkitLog.d("W5 ble receive CMD_PETKIT_BLE_216");
                    if (this.deviceInfos.getDeviceId() != -1) {
                        if (this.petkitBleData.getBleMsgList().size() > 0) {
                            postCustomData(nextBleMsg());
                            return;
                        }
                        this.deviceInfos.save();
                        onConnectSuccess(this.deviceInfos);
                        onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        return;
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd != 225 && cmd != 226) {
                    switch (cmd) {
                        case PetkitBLEConsts.CMD_PETKIT_BLE_222 /* 222 */:
                            postCustomData(W5DataConvertor.getW5RunningInfo());
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                            break;
                    }
                }
                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                return;
            }
            if (1 == petkitBleMsg.getType()) {
                if (W5Utils.getW5RecordByDeviceId(this.deviceInfos.getDeviceId()) != null) {
                    this.deviceInfos = W5Utils.getW5RecordByDeviceId(this.deviceInfos.getDeviceId());
                }
                this.deviceInfos = W5DataConvertor.parseBleMsgData(petkitBleMsg, (W5Record) this.deviceInfos, (BaseActivity) this.mContext);
                if (petkitBleMsg.getCmd() != 230) {
                    return;
                }
                if (this.deviceInfos.getDeviceId() != -1) {
                    this.deviceInfos.save();
                    postCustomData(W5DataConvertor.getDeviceUpdateResponse());
                    EventBus.getDefault().post(new RefreshW5DataEvent(this.deviceInfos.getDeviceId()));
                    EventBus.getDefault().post(new DeviceStateUpdateEvent(14, this.deviceInfos.getDeviceId()));
                    return;
                }
                onError(this, petkitBleMsg.getCmd());
                return;
            }
            return;
        }
        PetkitLog.d("data bytes is null");
        LogcatStorageHelper.addLog("data bytes is null");
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void updateDeviceLog(PetkitBleMsg petkitBleMsg) {
        PetkitLog.d("updateDeviceLog", "Log:" + ByteUtil.toHex(petkitBleMsg.getData()));
        HashMap map = new HashMap();
        byte[] bArr = new byte[4];
        System.arraycopy(petkitBleMsg.getData(), 0, bArr, 0, 4);
        byte[] bArr2 = new byte[8];
        System.arraycopy(petkitBleMsg.getData(), 4, bArr2, 0, 8);
        byte[] bArr3 = new byte[4];
        System.arraycopy(petkitBleMsg.getData(), 12, bArr3, 0, 4);
        map.put("deviceId", String.valueOf(this.deviceInfos.getDeviceId()));
        map.put("restartTimes", String.valueOf(ByteUtil.bytes2Int(bArr)));
        map.put("runTime", String.valueOf(ByteUtil.bytes2Long(bArr2)));
        map.put("pumpTimes", String.valueOf(ByteUtil.bytes2Int(bArr3)));
        map.put("testResult", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[22])));
        map.put("testResultCode", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[23])));
        WebModelRepository.getInstance().w5SaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.W5BleClient.2
            public AnonymousClass2() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save w5 Log:" + str);
                LogcatStorageHelper.addLog("save w5 Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save w5 Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save w5 Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.W5BleClient$2 */
    public class AnonymousClass2 implements PetkitCallback<String> {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save w5 Log:" + str);
            LogcatStorageHelper.addLog("save w5 Log:" + str);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("save w5 Log error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("save w5 Log error:" + errorInfor.getMsg());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onError(int i) {
        onError(this, i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void signup() {
        PetkitLog.d(TAG, "signup");
        HashMap map = new HashMap();
        if (this.deviceInfos.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
        }
        map.put("mac", this.deviceInfos.getMac());
        map.put("sn", this.deviceInfos.getSn());
        WebModelRepository.getInstance().w5Signup((BaseActivity) this.mContext, map, new PetkitCallback<W5Device>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.W5BleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(W5Device w5Device) {
                if (w5Device == null) {
                    W5BleClient.this.signupFail(new ErrorInfor(33, "w5Device is null"));
                    return;
                }
                W5Record w5Record = new W5Record(w5Device);
                PetkitLog.d("w5Signup success");
                LogcatStorageHelper.addLog("w5Signup success");
                LogcatStorageHelper.addLog("w5 signup deviceId:" + W5BleClient.this.deviceInfos.getDeviceId());
                W5BleClient.this.signupSuccess(w5Record);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("w5 signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("w5 signup fail " + errorInfor.getMsg());
                W5BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.W5BleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<W5Device> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(W5Device w5Device) {
            if (w5Device == null) {
                W5BleClient.this.signupFail(new ErrorInfor(33, "w5Device is null"));
                return;
            }
            W5Record w5Record = new W5Record(w5Device);
            PetkitLog.d("w5Signup success");
            LogcatStorageHelper.addLog("w5Signup success");
            LogcatStorageHelper.addLog("w5 signup deviceId:" + W5BleClient.this.deviceInfos.getDeviceId());
            W5BleClient.this.signupSuccess(w5Record);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("w5 signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("w5 signup fail " + errorInfor.getMsg());
            W5BleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d(TAG, "W5 securityCheckFinish");
        postCustomData(W5DataConvertor.getSyncBattery());
    }
}

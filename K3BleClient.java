package com.petkit.android.activities.petkitBleDevice.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.home.mode.DeviceStateUpdateEvent;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.K3DataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.ble.mode.W5DataConvertor;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3DataEvent;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Device;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Record;
import com.petkit.android.activities.petkitBleDevice.k3.mode.RefreshK3DataEvent;
import com.petkit.android.activities.petkitBleDevice.k3.mode.SnResult;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class K3BleClient extends PetkitBleClient implements PetkitBleListener {
    public static final String TAG = "K3_OTA";

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void getConfig() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public boolean isAqDevice() {
        return false;
    }

    @Override // com.petkit.android.activities.device.utils.DeviceBindUtils.DeviceBindResultListener
    public void uploadServerFinish() {
    }

    public K3BleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public K3BleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
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
                this.deviceInfos = K3DataConvertor.parseBleMsgData(petkitBleMsg, (K3Record) this.deviceInfos, (BaseActivity) this.mContext);
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
                if (cmd == 80) {
                    if (1 == ByteUtil.toInt(petkitBleMsg.getData()[0])) {
                        return;
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd != 240) {
                    switch (cmd) {
                        case 210:
                        case 211:
                            if (this.deviceInfos.getDeviceId() != -1) {
                                if (this.petkitBleData.getBleMsgList().size() > 0) {
                                    postCustomData(nextBleMsg());
                                } else {
                                    this.deviceInfos.save();
                                    changeLinkStatus(2);
                                    onConnectSuccess(this.deviceInfos);
                                    onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                                }
                            } else {
                                onError(this, petkitBleMsg.getCmd());
                            }
                            break;
                        case 212:
                            if (this.deviceInfos.getDeviceId() != -1) {
                                byte[] bArr = new byte[4];
                                System.arraycopy(petkitBleMsg.getData(), 1, bArr, 0, 4);
                                this.maxDataLength = ByteUtil.bytes2Int(bArr);
                                postCustomData(BaseDataConvertor.setStreamSetting(32, this.maxPackageLength));
                                setMaxPackageCount(32);
                                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                            } else {
                                onError(this, petkitBleMsg.getCmd());
                            }
                            break;
                        default:
                            switch (cmd) {
                                case PetkitBLEConsts.CMD_PETKIT_BLE_220 /* 220 */:
                                case PetkitBLEConsts.CMD_PETKIT_BLE_221 /* 221 */:
                                case PetkitBLEConsts.CMD_PETKIT_BLE_222 /* 222 */:
                                    onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                                    break;
                            }
                            break;
                    }
                }
                postCustomData(nextBleMsg());
                return;
            }
            if (1 == petkitBleMsg.getType()) {
                if (K3Utils.getK3RecordByDeviceId(this.deviceInfos.getDeviceId()) != null) {
                    this.deviceInfos = K3Utils.getK3RecordByDeviceId(this.deviceInfos.getDeviceId());
                }
                int cmd2 = petkitBleMsg.getCmd();
                if (cmd2 != 69) {
                    if (cmd2 == 230 && ((K3Record) this.deviceInfos).getSettings() != null) {
                        K3Record bleMsgData = K3DataConvertor.parseBleMsgData(petkitBleMsg, (K3Record) this.deviceInfos, (BaseActivity) this.mContext);
                        this.deviceInfos = bleMsgData;
                        if (bleMsgData.getDeviceId() != -1) {
                            this.deviceInfos.save();
                            postCustomData(K3DataConvertor.getDeviceUpdateResponse());
                            EventBus.getDefault().post(new RefreshK3DataEvent(this.deviceInfos.getDeviceId()));
                            EventBus.getDefault().post(new DeviceStateUpdateEvent(16, this.deviceInfos.getDeviceId()));
                            return;
                        }
                        onError(this, petkitBleMsg.getCmd());
                        return;
                    }
                    return;
                }
                PetkitLog.d("workData", "k3 work sync data end");
                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                EventBus.getDefault().post(new K3DataEvent(this.deviceInfos.getDeviceId(), FileUtils.getAppCacheActivityDataDirPath() + "K3-" + this.deviceInfos.getDeviceId() + "-" + Consts.TEMP_ACTIVITY_DATA_FILE_NAME));
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
        byte[] bArr4 = new byte[4];
        System.arraycopy(petkitBleMsg.getData(), 16, bArr4, 0, 4);
        map.put("deviceId", String.valueOf(this.deviceInfos.getDeviceId()));
        map.put("restartTimes", String.valueOf(ByteUtil.bytes2Int(bArr)));
        map.put("runTime", String.valueOf(ByteUtil.bytes2Long(bArr2)));
        map.put("lightRunTime", String.valueOf(ByteUtil.bytes2Int(bArr3)));
        map.put("refreshTimes", String.valueOf(ByteUtil.bytes2Int(bArr4)));
        map.put("testResult", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[20])));
        map.put("testResultCode", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[21])));
        WebModelRepository.getInstance().k3SaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.K3BleClient.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save k3 Log:" + str);
                LogcatStorageHelper.addLog("save k3 Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save k3 Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save k3 Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.K3BleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<String> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save k3 Log:" + str);
            LogcatStorageHelper.addLog("save k3 Log:" + str);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("save k3 Log error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("save k3 Log error:" + errorInfor.getMsg());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processStreamData(Map<Integer, PetkitBleMsg> map) {
        K3DataConvertor.processStreamData(map, this.deviceInfos.getDeviceId());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onError(int i) {
        onError(this, i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d(TAG, "findSn");
        LogcatStorageHelper.addLog("findSn start");
        HashMap map = new HashMap();
        map.put("mac", this.deviceInfos.getMac());
        WebModelRepository.getInstance().k3FindSn((BaseActivity) this.mContext, map, new PetkitCallback<SnResult>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.K3BleClient.2
            public AnonymousClass2() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(SnResult snResult) {
                if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                    K3BleClient.this.signupFail(new ErrorInfor(33, "get k3Sn from server null"));
                    LogcatStorageHelper.addLog("get k3Sn from server null ");
                    PetkitLog.d("get k3Sn from server null");
                    return;
                }
                PetkitLog.d("k3Sn success " + snResult.toString());
                LogcatStorageHelper.addLog("k3Sn success " + snResult.toString());
                K3BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("k3 k3FindSn fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("k3 k3FindSn fail " + errorInfor.getMsg());
                K3BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.K3BleClient$2 */
    public class AnonymousClass2 implements PetkitCallback<SnResult> {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(SnResult snResult) {
            if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                K3BleClient.this.signupFail(new ErrorInfor(33, "get k3Sn from server null"));
                LogcatStorageHelper.addLog("get k3Sn from server null ");
                PetkitLog.d("get k3Sn from server null");
                return;
            }
            PetkitLog.d("k3Sn success " + snResult.toString());
            LogcatStorageHelper.addLog("k3Sn success " + snResult.toString());
            K3BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("k3 k3FindSn fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("k3 k3FindSn fail " + errorInfor.getMsg());
            K3BleClient.this.signupFail(errorInfor);
        }
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
        map.put("timezone", "" + (TimeZone.getDefault().getRawOffset() / 3600000.0f));
        WebModelRepository.getInstance().k3Signup((BaseActivity) this.mContext, map, new PetkitCallback<K3Device>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.K3BleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(K3Device k3Device) {
                if (k3Device == null) {
                    K3BleClient.this.signupFail(new ErrorInfor(33, "k3Device is null"));
                    return;
                }
                K3Record k3Record = new K3Record(k3Device);
                PetkitLog.d("k3Signup success");
                LogcatStorageHelper.addLog("k3Signup success");
                LogcatStorageHelper.addLog("k3 signup deviceId:" + K3BleClient.this.deviceInfos.getDeviceId());
                K3BleClient.this.signupSuccess(k3Record);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("k3 signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("k3 signup fail " + errorInfor.getMsg());
                K3BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.K3BleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<K3Device> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(K3Device k3Device) {
            if (k3Device == null) {
                K3BleClient.this.signupFail(new ErrorInfor(33, "k3Device is null"));
                return;
            }
            K3Record k3Record = new K3Record(k3Device);
            PetkitLog.d("k3Signup success");
            LogcatStorageHelper.addLog("k3Signup success");
            LogcatStorageHelper.addLog("k3 signup deviceId:" + K3BleClient.this.deviceInfos.getDeviceId());
            K3BleClient.this.signupSuccess(k3Record);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("k3 signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("k3 signup fail " + errorInfor.getMsg());
            K3BleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d(TAG, "K3 securityCheckFinish");
        postCustomData(K3DataConvertor.getK3RunningSettingInfo());
    }
}

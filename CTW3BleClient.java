package com.petkit.android.activities.petkitBleDevice.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.CTW3DataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3DataEvent;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Device;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Record;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3Utils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.util.HashMap;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class CTW3BleClient extends PetkitBleClient implements PetkitBleListener {
    public static final String TAG = "CTW3_OTA";

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
    }

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

    public CTW3BleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public CTW3BleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
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
                this.deviceInfos = CTW3DataConvertor.parseBleMsgData(petkitBleMsg, (CTW3Record) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd = petkitBleMsg.getCmd();
                if (cmd == 66) {
                    if (this.deviceInfos.getDeviceId() != -1) {
                        postCustomData(CTW3DataConvertor.getCTW3Setting());
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
                if (cmd == 215) {
                    PetkitLog.d("ctw3 ble receive CMD_PETKIT_BLE_215");
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
                    PetkitLog.d("ctw3 ble receive CMD_PETKIT_BLE_216");
                    if (this.deviceInfos.getDeviceId() != -1) {
                        if (this.petkitBleData.getBleMsgList().size() > 0) {
                            postCustomData(nextBleMsg());
                            return;
                        }
                        this.deviceInfos.save();
                        onConnectSuccess(this.deviceInfos);
                        postCustomData(CTW3DataConvertor.startSyncHistoryRecord());
                        onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        return;
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd != 225 && cmd != 226) {
                    switch (cmd) {
                        case 210:
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
                        case 211:
                            if (this.deviceInfos.getDeviceId() != -1) {
                                this.deviceInfos.save();
                                postCustomData(CTW3DataConvertor.getCTW3NewSetting());
                                return;
                            } else {
                                onError(this, petkitBleMsg.getCmd());
                                return;
                            }
                        case 212:
                            if (this.deviceInfos.getDeviceId() != -1) {
                                byte[] bArr = new byte[4];
                                System.arraycopy(petkitBleMsg.getData(), 1, bArr, 0, 4);
                                this.maxDataLength = ByteUtil.bytes2Int(bArr);
                                postCustomData(BaseDataConvertor.setStreamSetting(32, this.maxPackageLength));
                                setMaxPackageCount(32);
                                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                                return;
                            }
                            onError(this, petkitBleMsg.getCmd());
                            return;
                        default:
                            switch (cmd) {
                                case PetkitBLEConsts.CMD_PETKIT_BLE_222 /* 222 */:
                                    postCustomData(CTW3DataConvertor.getCTW3RunningInfo());
                                    onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                                    break;
                            }
                    }
                }
                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                return;
            }
            if (1 == petkitBleMsg.getType()) {
                if (CTW3Utils.getCTW3RecordByDeviceId(this.deviceInfos.getDeviceId()) != null) {
                    this.deviceInfos = CTW3Utils.getCTW3RecordByDeviceId(this.deviceInfos.getDeviceId());
                }
                this.deviceInfos = CTW3DataConvertor.parseBleMsgData(petkitBleMsg, (CTW3Record) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd2 = petkitBleMsg.getCmd();
                if (cmd2 != 69) {
                    if (cmd2 != 230) {
                        return;
                    }
                    if (this.deviceInfos.getDeviceId() != -1) {
                        this.deviceInfos.save();
                        postCustomData(CTW3DataConvertor.getDeviceUpdateResponse());
                        onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        return;
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                PetkitLog.d("workData", "ctw3 work sync data end");
                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                EventBus.getDefault().post(new CTW3DataEvent(this.deviceInfos.getDeviceId(), FileUtils.getAppCacheActivityDataDirPath() + "CTW3-" + this.deviceInfos.getDeviceId() + "-" + Consts.TEMP_CTW3_RECORD_DATA_FILE_NAME));
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
        WebModelRepository.getInstance().ctw3SaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.CTW3BleClient.1
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save ctw3 Log:" + str);
                LogcatStorageHelper.addLog("save ctw3 Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save ctw3 Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save ctw3 Log error:" + errorInfor.getMsg());
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processStreamData(Map<Integer, PetkitBleMsg> map) {
        CTW3DataConvertor.processStreamData(map, this.deviceInfos.getDeviceId());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onError(int i) {
        onError(this, i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void signup() {
        HashMap map = new HashMap();
        if (this.deviceInfos.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
        }
        map.put("mac", this.deviceInfos.getMac());
        map.put("sn", this.deviceInfos.getSn());
        WebModelRepository.getInstance().ctw3Signup((BaseActivity) this.mContext, map, new PetkitCallback<CTW3Device>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.CTW3BleClient.2
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(CTW3Device cTW3Device) {
                if (cTW3Device == null) {
                    CTW3BleClient.this.signupFail(new ErrorInfor(33, "ctw3Device is null"));
                    return;
                }
                CTW3Record cTW3Record = new CTW3Record(cTW3Device);
                PetkitLog.d("ctw3Signup success");
                LogcatStorageHelper.addLog("ctw3Signup success");
                LogcatStorageHelper.addLog("ctw3 signup deviceId:" + CTW3BleClient.this.deviceInfos.getDeviceId());
                CTW3BleClient.this.signupSuccess(cTW3Record);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("ctw3 signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("ctw3 signup fail " + errorInfor.getMsg());
                CTW3BleClient.this.signupFail(errorInfor);
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d(TAG, "ctw3 securityCheckFinish");
        postCustomData(CTW3DataConvertor.getSyncBattery());
    }
}

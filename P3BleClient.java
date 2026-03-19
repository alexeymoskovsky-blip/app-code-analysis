package com.petkit.android.activities.petkitBleDevice.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.P3DataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.k3.mode.SnResult;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3DataEvent;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Device;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3FindPetRingMsg;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3SyncDataFlag;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.util.HashMap;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class P3BleClient extends PetkitBleClient implements PetkitBleListener {
    public static final String TAG = "P3_OTA";

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

    public P3BleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public P3BleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d(TAG, "findSn");
        LogcatStorageHelper.addLog("findSn start");
        HashMap map = new HashMap();
        map.put("mac", this.deviceInfos.getMac());
        WebModelRepository.getInstance().p3FindSn((BaseActivity) this.mContext, map, new PetkitCallback<SnResult>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.P3BleClient.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(SnResult snResult) {
                if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                    P3BleClient.this.signupFail(new ErrorInfor(33, "get p3Sn from server null"));
                    LogcatStorageHelper.addLog("get p3Sn from server null " + snResult.toString());
                    PetkitLog.d("get p3Sn from server null");
                    return;
                }
                PetkitLog.d("aqSn success " + snResult.toString());
                LogcatStorageHelper.addLog("p3Sn success " + snResult.toString());
                P3BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d(" p3FindSn fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("p3FindSn fail " + errorInfor.getMsg());
                P3BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.P3BleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<SnResult> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(SnResult snResult) {
            if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                P3BleClient.this.signupFail(new ErrorInfor(33, "get p3Sn from server null"));
                LogcatStorageHelper.addLog("get p3Sn from server null " + snResult.toString());
                PetkitLog.d("get p3Sn from server null");
                return;
            }
            PetkitLog.d("aqSn success " + snResult.toString());
            LogcatStorageHelper.addLog("p3Sn success " + snResult.toString());
            P3BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d(" p3FindSn fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("p3FindSn fail " + errorInfor.getMsg());
            P3BleClient.this.signupFail(errorInfor);
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
                this.deviceInfos = P3DataConvertor.parseBleMsgData(petkitBleMsg, (P3Record) this.deviceInfos, (BaseActivity) null);
                int cmd = petkitBleMsg.getCmd();
                if (cmd == 66) {
                    if (this.deviceInfos.getDeviceId() != -1) {
                        postCustomData(P3DataConvertor.getP3Setting());
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
                    ((BaseApplication) CommonUtils.getAppContext()).getP3SyncDevice().remove(new P3SyncDataFlag(this.deviceInfos.getDeviceId()));
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd == 240) {
                    postCustomData(nextBleMsg());
                    return;
                }
                if (cmd != 210) {
                    if (cmd == 211) {
                        if (this.deviceInfos.getDeviceId() != -1) {
                            byte[] bArr = new byte[4];
                            System.arraycopy(petkitBleMsg.getData(), 1, bArr, 0, 4);
                            this.maxDataLength = ByteUtil.bytes2Int(bArr);
                            postCustomData(BaseDataConvertor.setStreamSetting(32, this.maxPackageLength));
                            setMaxPackageCount(32);
                            return;
                        }
                        ((BaseApplication) CommonUtils.getAppContext()).getP3SyncDevice().remove(new P3SyncDataFlag(this.deviceInfos.getDeviceId()));
                        onError(this, petkitBleMsg.getCmd());
                        return;
                    }
                    if (cmd == 220) {
                        EventBus.getDefault().post(new P3FindPetRingMsg(this.device.getAddress()));
                        onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        return;
                    } else if (cmd != 221) {
                        return;
                    }
                }
                if (this.deviceInfos.getDeviceId() != -1) {
                    changeLinkStatus(2);
                    onConnectSuccess(this.deviceInfos);
                    onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                    return;
                }
                onError(this, petkitBleMsg.getCmd());
                return;
            }
            if (1 == petkitBleMsg.getType() && petkitBleMsg.getCmd() == 69) {
                PetkitLog.d("syncData", "sync data end");
                P3Record p3RecordByDeviceId = P3Utils.getP3RecordByDeviceId(this.deviceInfos.getDeviceId());
                this.deviceInfos = p3RecordByDeviceId;
                onReceiveBleResult(this, petkitBleMsg, p3RecordByDeviceId);
                EventBus.getDefault().post(new P3DataEvent(this.deviceInfos.getDeviceId(), FileUtils.getAppCacheActivityDataDirPath() + "p3-" + this.deviceInfos.getDeviceId() + "-" + ((P3Record) this.deviceInfos).getPetId() + "-" + Consts.TEMP_ACTIVITY_DATA_FILE_NAME));
                return;
            }
            return;
        }
        PetkitLog.d("data bytes is null");
        LogcatStorageHelper.addLog("data bytes is null");
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void updateDeviceLog(PetkitBleMsg petkitBleMsg) {
        if (this.mContext instanceof HomeActivity) {
            return;
        }
        PetkitLog.d("updateDeviceLog", "Log:" + ByteUtil.toHex(petkitBleMsg.getData()));
        HashMap map = new HashMap();
        byte[] bArr = new byte[4];
        System.arraycopy(petkitBleMsg.getData(), 0, bArr, 0, 4);
        byte[] bArr2 = new byte[8];
        System.arraycopy(petkitBleMsg.getData(), 4, bArr2, 0, 8);
        byte[] bArr3 = new byte[4];
        System.arraycopy(petkitBleMsg.getData(), 4, bArr3, 0, 4);
        map.put("deviceId", String.valueOf(this.deviceInfos.getDeviceId()));
        map.put("restartTimes", String.valueOf(ByteUtil.bytes2Int(bArr)));
        map.put("runTime", String.valueOf(ByteUtil.bytes2Long(bArr2)));
        map.put("ringTimes", String.valueOf(ByteUtil.bytes2Int(bArr3)));
        map.put("testResult", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[22])));
        map.put("testResultCode", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[23])));
        WebModelRepository.getInstance().p3SaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.P3BleClient.2
            public AnonymousClass2() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save p3 Log:" + str);
                LogcatStorageHelper.addLog("save p3 Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save p3 Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save p3 Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.P3BleClient$2 */
    public class AnonymousClass2 implements PetkitCallback<String> {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save p3 Log:" + str);
            LogcatStorageHelper.addLog("save p3 Log:" + str);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("save p3 Log error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("save p3 Log error:" + errorInfor.getMsg());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processStreamData(Map<Integer, PetkitBleMsg> map) {
        P3DataConvertor.processStreamData(map, this.deviceInfos.getDeviceId());
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
        WebModelRepository.getInstance().p3Signup((BaseActivity) this.mContext, map, new PetkitCallback<P3Device>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.P3BleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(P3Device p3Device) {
                if (p3Device == null) {
                    P3BleClient.this.signupFail(new ErrorInfor(33, "p3Device is null"));
                    return;
                }
                P3Record p3Record = new P3Record(p3Device);
                PetkitLog.d("p3Signup success");
                LogcatStorageHelper.addLog("p3Signup success");
                LogcatStorageHelper.addLog("p3 signup deviceId:" + P3BleClient.this.deviceInfos.getDeviceId());
                P3BleClient.this.signupSuccess(p3Record);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("p3 signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("p3 signup fail " + errorInfor.getMsg());
                P3BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.P3BleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<P3Device> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(P3Device p3Device) {
            if (p3Device == null) {
                P3BleClient.this.signupFail(new ErrorInfor(33, "p3Device is null"));
                return;
            }
            P3Record p3Record = new P3Record(p3Device);
            PetkitLog.d("p3Signup success");
            LogcatStorageHelper.addLog("p3Signup success");
            LogcatStorageHelper.addLog("p3 signup deviceId:" + P3BleClient.this.deviceInfos.getDeviceId());
            P3BleClient.this.signupSuccess(p3Record);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("p3 signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("p3 signup fail " + errorInfor.getMsg());
            P3BleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d(TAG, "P3 securityCheckFinish");
        postCustomData(P3DataConvertor.getSyncBattery());
    }
}

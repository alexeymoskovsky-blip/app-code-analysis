package com.petkit.android.activities.petkitBleDevice.ble;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.home.mode.DeviceStateUpdateEvent;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.ble.mode.R2DataConvertor;
import com.petkit.android.activities.petkitBleDevice.k3.mode.SnResult;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Data;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2DataEvent;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Device;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Record;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2UpdateParam;
import com.petkit.android.activities.petkitBleDevice.r2.utils.R2Utils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class R2BleClient extends PetkitBleClient implements PetkitBleListener {
    public static final String TAG = "R2_Client";

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

    public R2BleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public R2BleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void onOtaStateChanged(int i, int i2) {
        super.onOtaStateChanged(i, i2);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d(TAG, "findSn");
        LogcatStorageHelper.addLog("findSn start");
        HashMap map = new HashMap();
        map.put("mac", this.deviceInfos.getMac());
        WebModelRepository.getInstance().r2FindSn((BaseActivity) this.mContext, map, new PetkitCallback<SnResult>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(SnResult snResult) {
                if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                    R2BleClient.this.signupFail(new ErrorInfor(33, "get r2Sn from server null"));
                    LogcatStorageHelper.addLog("get r2Sn from server null " + snResult.toString());
                    PetkitLog.d("get r2Sn from server null");
                    return;
                }
                PetkitLog.d("aqSn success " + snResult.toString());
                LogcatStorageHelper.addLog("r2Sn success " + snResult.toString());
                R2BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d(" r2FindSn fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("r2FindSn fail " + errorInfor.getMsg());
                R2BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<SnResult> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(SnResult snResult) {
            if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                R2BleClient.this.signupFail(new ErrorInfor(33, "get r2Sn from server null"));
                LogcatStorageHelper.addLog("get r2Sn from server null " + snResult.toString());
                PetkitLog.d("get r2Sn from server null");
                return;
            }
            PetkitLog.d("aqSn success " + snResult.toString());
            LogcatStorageHelper.addLog("r2Sn success " + snResult.toString());
            R2BleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d(" r2FindSn fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("r2FindSn fail " + errorInfor.getMsg());
            R2BleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onReceiveCustomData(PetkitBleMsg petkitBleMsg) {
        super.onReceiveCustomData(petkitBleMsg);
        if (petkitBleMsg != null && this.deviceInfos != null) {
            if (2 == petkitBleMsg.getType()) {
                if (R2Utils.getR2RecordByDeviceId(this.deviceInfos.getDeviceId()) != null) {
                    this.deviceInfos = R2Utils.getR2RecordByDeviceId(this.deviceInfos.getDeviceId());
                }
                this.deviceInfos = R2DataConvertor.parseBleMsgData(petkitBleMsg, (R2Record) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd = petkitBleMsg.getCmd();
                if (cmd == 80) {
                    if (1 == ByteUtil.toInt(petkitBleMsg.getData()[0])) {
                        return;
                    }
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (cmd == 240) {
                    postCustomData(nextBleMsg());
                    return;
                }
                switch (cmd) {
                    case 210:
                        PetkitLog.d(TAG, "receive CMD_PETKIT_BLE_210");
                        if (this.deviceInfos.getDeviceId() != -1) {
                            if (this.petkitBleData.getBleMsgList().size() > 0) {
                                this.deviceInfos.save();
                                postCustomData(nextBleMsg());
                            } else {
                                this.deviceInfos.save();
                                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                            }
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 211:
                        PetkitLog.d(TAG, "receive CMD_PETKIT_BLE_211");
                        if (this.deviceInfos.getDeviceId() != -1) {
                            this.deviceInfos.save();
                            postCustomData(R2DataConvertor.getStatisticInfo());
                            onConnectSuccess(this.deviceInfos);
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
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
                    case 213:
                        DeviceInfos deviceInfos = this.deviceInfos;
                        if (deviceInfos == null || deviceInfos.getDeviceId() != -1) {
                            PetkitLog.d(TAG, "receive CMD_PETKIT_BLE_213");
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
            if (1 == petkitBleMsg.getType()) {
                if (R2Utils.getR2RecordByDeviceId(this.deviceInfos.getDeviceId()) != null) {
                    this.deviceInfos = R2Utils.getR2RecordByDeviceId(this.deviceInfos.getDeviceId());
                }
                int cmd2 = petkitBleMsg.getCmd();
                if (cmd2 == 69) {
                    PetkitLog.d("workData", "r2 work sync data end");
                    onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                    EventBus.getDefault().post(new R2DataEvent(this.deviceInfos.getDeviceId(), FileUtils.getAppCacheActivityDataDirPath() + "R2-" + this.deviceInfos.getDeviceId() + "-" + Consts.TEMP_ACTIVITY_DATA_FILE_NAME));
                    return;
                }
                if (cmd2 != 230) {
                    return;
                }
                R2Record bleMsgData = R2DataConvertor.parseBleMsgData(petkitBleMsg, (R2Record) this.deviceInfos, (BaseActivity) this.mContext);
                this.deviceInfos = bleMsgData;
                if (bleMsgData.getDeviceId() != -1) {
                    this.deviceInfos.save();
                    HashMap map = new HashMap();
                    map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
                    map.put("kv", new Gson().toJson(new R2UpdateParam((R2Record) this.deviceInfos)));
                    AsyncHttpUtil.post(ApiTools.SAMPLE_API_R2_UPDATE, map, new AsyncHttpRespHandler((Activity) this.mContext, false) { // from class: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient.2
                        public AnonymousClass2(Activity activity, boolean z) {
                            super(activity, z);
                        }

                        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                        public void onSuccess(int i, Header[] headerArr, byte[] bArr2) {
                            super.onSuccess(i, headerArr, bArr2);
                            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                            if (baseRsp.getError() != null) {
                                PetkitLog.d("r2 update fail " + baseRsp.getError().getMsg());
                                return;
                            }
                            PetkitLog.d("r2 update success");
                            LogcatStorageHelper.addLog("r2 update success");
                            EventBus.getDefault().post(new DeviceStateUpdateEvent(18, R2BleClient.this.deviceInfos.getDeviceId()));
                        }
                    });
                    postCustomData(R2DataConvertor.getDeviceUpdateResponse());
                    EventBus.getDefault().post(new R2Data((R2Record) this.deviceInfos));
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient$2 */
    public class AnonymousClass2 extends AsyncHttpRespHandler {
        public AnonymousClass2(Activity activity, boolean z) {
            super(activity, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr2) {
            super.onSuccess(i, headerArr, bArr2);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PetkitLog.d("r2 update fail " + baseRsp.getError().getMsg());
                return;
            }
            PetkitLog.d("r2 update success");
            LogcatStorageHelper.addLog("r2 update success");
            EventBus.getDefault().post(new DeviceStateUpdateEvent(18, R2BleClient.this.deviceInfos.getDeviceId()));
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
        WebModelRepository.getInstance().r2Signup((BaseActivity) this.mContext, map, new PetkitCallback<R2Device>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(R2Device r2Device) {
                if (r2Device == null) {
                    R2BleClient.this.signupFail(new ErrorInfor(33, "r2Device is null"));
                    return;
                }
                R2Record r2Record = new R2Record(r2Device);
                PetkitLog.d("r2Signup success");
                LogcatStorageHelper.addLog("r2Signup success");
                LogcatStorageHelper.addLog("r2 signup deviceId:" + R2BleClient.this.deviceInfos.getDeviceId());
                R2BleClient.this.signupSuccess(r2Record);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("r2 signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("r2 signup fail " + errorInfor.getMsg());
                R2BleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<R2Device> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(R2Device r2Device) {
            if (r2Device == null) {
                R2BleClient.this.signupFail(new ErrorInfor(33, "r2Device is null"));
                return;
            }
            R2Record r2Record = new R2Record(r2Device);
            PetkitLog.d("r2Signup success");
            LogcatStorageHelper.addLog("r2Signup success");
            LogcatStorageHelper.addLog("r2 signup deviceId:" + R2BleClient.this.deviceInfos.getDeviceId());
            R2BleClient.this.signupSuccess(r2Record);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("r2 signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("r2 signup fail " + errorInfor.getMsg());
            R2BleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d(TAG, "K3 securityCheckFinish");
        postCustomData(R2DataConvertor.getR2RunningSettingInfo());
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
        map.put("heatRunTime", String.valueOf(ByteUtil.bytes2Int(bArr3)));
        map.put("testResult", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[16])));
        map.put("testResultCode", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[17])));
        WebModelRepository.getInstance().r2SaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient.4
            public AnonymousClass4() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save r2 Log:" + str);
                LogcatStorageHelper.addLog("save r2 Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save r2 Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save r2 Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.R2BleClient$4 */
    public class AnonymousClass4 implements PetkitCallback<String> {
        public AnonymousClass4() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save r2 Log:" + str);
            LogcatStorageHelper.addLog("save r2 Log:" + str);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("save r2 Log error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("save r2 Log error:" + errorInfor.getMsg());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processStreamData(Map<Integer, PetkitBleMsg> map) {
        R2DataConvertor.processStreamData(map, this.deviceInfos.getDeviceId());
    }
}

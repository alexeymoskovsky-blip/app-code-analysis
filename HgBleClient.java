package com.petkit.android.activities.petkitBleDevice.ble;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.home.mode.DeviceStateUpdateEvent;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.HgDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgData;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgDevice;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgRecord;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgUpdateParam;
import com.petkit.android.activities.petkitBleDevice.hg.utils.HgUtils;
import com.petkit.android.activities.petkitBleDevice.k3.mode.SnResult;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class HgBleClient extends PetkitBleClient implements PetkitBleListener {
    public static final String TAG = "Hg_Client";

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

    public HgBleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public HgBleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d(TAG, "findSn");
        LogcatStorageHelper.addLog("findSn start");
        HashMap map = new HashMap();
        map.put("mac", this.deviceInfos.getMac());
        WebModelRepository.getInstance().hgFindSn((BaseActivity) this.mContext, map, new PetkitCallback<SnResult>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(SnResult snResult) {
                if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                    HgBleClient.this.signupFail(new ErrorInfor(33, "get hgSn from server null"));
                    LogcatStorageHelper.addLog("get hgSn from server null " + snResult.toString());
                    PetkitLog.d("get hgSn from server null");
                    return;
                }
                PetkitLog.d("hgSn success " + snResult.toString());
                LogcatStorageHelper.addLog("hgSn success " + snResult.toString());
                HgBleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d(" hgFindSn fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("hgFindSn fail " + errorInfor.getMsg());
                HgBleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<SnResult> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(SnResult snResult) {
            if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                HgBleClient.this.signupFail(new ErrorInfor(33, "get hgSn from server null"));
                LogcatStorageHelper.addLog("get hgSn from server null " + snResult.toString());
                PetkitLog.d("get hgSn from server null");
                return;
            }
            PetkitLog.d("hgSn success " + snResult.toString());
            LogcatStorageHelper.addLog("hgSn success " + snResult.toString());
            HgBleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d(" hgFindSn fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("hgFindSn fail " + errorInfor.getMsg());
            HgBleClient.this.signupFail(errorInfor);
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
                if (HgUtils.getHgRecordByDeviceId(this.deviceInfos.getDeviceId()) != null) {
                    this.deviceInfos = HgUtils.getHgRecordByDeviceId(this.deviceInfos.getDeviceId());
                }
                this.deviceInfos = HgDataConvertor.parseBleMsgData(petkitBleMsg, (HgRecord) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd = petkitBleMsg.getCmd();
                if (cmd != 65) {
                    if (cmd == 80) {
                        if (1 == ByteUtil.toInt(petkitBleMsg.getData()[0])) {
                            return;
                        }
                        onError(this, petkitBleMsg.getCmd());
                        return;
                    }
                    if (cmd == 210) {
                        PetkitLog.d(TAG, "receive CMD_PETKIT_BLE_210");
                        if (this.deviceInfos.getDeviceId() != -1) {
                            if (this.petkitBleData.getBleMsgList().size() > 0) {
                                this.deviceInfos.save();
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
                        PetkitLog.d(TAG, "receive CMD_PETKIT_BLE_211");
                        if (this.petkitBleData.getBleMsgList().size() > 0) {
                            this.deviceInfos.save();
                            postCustomData(nextBleMsg());
                            return;
                        } else if (this.deviceInfos.getDeviceId() != -1) {
                            this.deviceInfos.save();
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                            return;
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                            return;
                        }
                    }
                    if (cmd == 213) {
                        DeviceInfos deviceInfos = this.deviceInfos;
                        if (deviceInfos == null || deviceInfos.getDeviceId() != -1) {
                            PetkitLog.d(TAG, "receive CMD_PETKIT_BLE_213");
                            return;
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                            return;
                        }
                    }
                    if (cmd != 214) {
                        switch (cmd) {
                        }
                    } else {
                        if (this.deviceInfos.getDeviceId() != -1) {
                            this.deviceInfos.save();
                            onConnectSuccess(this.deviceInfos);
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                            return;
                        }
                        onError(this, petkitBleMsg.getCmd());
                        return;
                    }
                }
                onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                return;
            }
            if (1 == petkitBleMsg.getType()) {
                if (HgUtils.getHgRecordByDeviceId(this.deviceInfos.getDeviceId()) != null) {
                    this.deviceInfos = HgUtils.getHgRecordByDeviceId(this.deviceInfos.getDeviceId());
                }
                if (petkitBleMsg.getCmd() != 230) {
                    return;
                }
                HgRecord bleMsgData = HgDataConvertor.parseBleMsgData(petkitBleMsg, (HgRecord) this.deviceInfos, (BaseActivity) this.mContext);
                this.deviceInfos = bleMsgData;
                if (bleMsgData.getDeviceId() != -1) {
                    this.deviceInfos.save();
                    HashMap map = new HashMap();
                    map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
                    map.put("kv", new Gson().toJson(new HgUpdateParam((HgRecord) this.deviceInfos)));
                    AsyncHttpUtil.post(ApiTools.SAMPLE_API_HG_UPDATE, map, new AsyncHttpRespHandler((Activity) this.mContext, false) { // from class: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient.2
                        public AnonymousClass2(Activity activity, boolean z) {
                            super(activity, z);
                        }

                        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                            super.onSuccess(i, headerArr, bArr);
                            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                            if (baseRsp.getError() != null) {
                                PetkitLog.d("Hg update fail " + baseRsp.getError().getMsg());
                                return;
                            }
                            PetkitLog.d("Hg update success");
                            LogcatStorageHelper.addLog("Hg update success");
                            EventBus.getDefault().post(new DeviceStateUpdateEvent(22, HgBleClient.this.deviceInfos.getDeviceId()));
                        }
                    });
                    postCustomData(HgDataConvertor.getDeviceUpdateResponse());
                    EventBus.getDefault().post(new HgData((HgRecord) this.deviceInfos));
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient$2 */
    public class AnonymousClass2 extends AsyncHttpRespHandler {
        public AnonymousClass2(Activity activity, boolean z) {
            super(activity, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PetkitLog.d("Hg update fail " + baseRsp.getError().getMsg());
                return;
            }
            PetkitLog.d("Hg update success");
            LogcatStorageHelper.addLog("Hg update success");
            EventBus.getDefault().post(new DeviceStateUpdateEvent(22, HgBleClient.this.deviceInfos.getDeviceId()));
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
        WebModelRepository.getInstance().hgSignup((BaseActivity) this.mContext, map, new PetkitCallback<HgDevice>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(HgDevice hgDevice) {
                if (hgDevice == null) {
                    HgBleClient.this.signupFail(new ErrorInfor(33, "hgDevice is null"));
                    return;
                }
                HgRecord hgRecord = new HgRecord(hgDevice);
                hgRecord.save();
                PetkitLog.d("hgSignup success");
                LogcatStorageHelper.addLog("hgSignup success");
                LogcatStorageHelper.addLog("hg signup deviceId:" + HgBleClient.this.deviceInfos.getDeviceId());
                HgBleClient.this.signupSuccess(hgRecord);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("hg signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("hg signup fail " + errorInfor.getMsg());
                if (errorInfor.getCode() == 711 && PetkitBleDeviceManager.getInstance().checkDeviceState(HgBleClient.this.deviceInfos.getMac())) {
                    PetkitBleDeviceManager.getInstance().postCustomData(HgBleClient.this.deviceInfos.getMac(), 22, HgDataConvertor.cancelActivateDevice());
                }
                HgBleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<HgDevice> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(HgDevice hgDevice) {
            if (hgDevice == null) {
                HgBleClient.this.signupFail(new ErrorInfor(33, "hgDevice is null"));
                return;
            }
            HgRecord hgRecord = new HgRecord(hgDevice);
            hgRecord.save();
            PetkitLog.d("hgSignup success");
            LogcatStorageHelper.addLog("hgSignup success");
            LogcatStorageHelper.addLog("hg signup deviceId:" + HgBleClient.this.deviceInfos.getDeviceId());
            HgBleClient.this.signupSuccess(hgRecord);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("hg signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("hg signup fail " + errorInfor.getMsg());
            if (errorInfor.getCode() == 711 && PetkitBleDeviceManager.getInstance().checkDeviceState(HgBleClient.this.deviceInfos.getMac())) {
                PetkitBleDeviceManager.getInstance().postCustomData(HgBleClient.this.deviceInfos.getMac(), 22, HgDataConvertor.cancelActivateDevice());
            }
            HgBleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d(TAG, "HG securityCheckFinish");
        postCustomData(HgDataConvertor.getHgRunningSettingInfo());
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
        WebModelRepository.getInstance().hgSaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient.4
            public AnonymousClass4() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save hg Log:" + str);
                LogcatStorageHelper.addLog("save hg Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save hg Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save hg Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.HgBleClient$4 */
    public class AnonymousClass4 implements PetkitCallback<String> {
        public AnonymousClass4() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save hg Log:" + str);
            LogcatStorageHelper.addLog("save hg Log:" + str);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("save hg Log error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("save hg Log error:" + errorInfor.getMsg());
        }
    }
}

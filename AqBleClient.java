package com.petkit.android.activities.petkitBleDevice.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqColorfulMode;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqConfig;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqDevice;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqStreamerMode;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqUpdateParam;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqWhiteMode;
import com.petkit.android.activities.petkitBleDevice.aq.mode.RefreshAqMsg;
import com.petkit.android.activities.petkitBleDevice.aq.mode.UpdateAqMsg;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqBleDataUtils;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient;
import com.petkit.android.activities.petkitBleDevice.ble.mode.AqDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.BaseDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class AqBleClient extends PetkitBleClient {
    public static final String TAG = "AQ_OTA";
    public boolean isCheckConfig;

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

    public AqBleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public AqBleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
    }

    public void initDevice(AqRecord aqRecord) {
        this.deviceInfos = aqRecord;
        this.isBindDevice = true;
        postCustomData(new PetkitBleMsg(73, ByteUtil.mergeBytes(ByteUtil.makeUpBtyesForward(ByteUtil.long2Bytes(aqRecord.getDeviceId()), 8), ByteUtil.makeUpBtyesForward(ByteUtil.hexStrToByteArray(aqRecord.getSecret()), 8))));
    }

    public void checkConfig(AqRecord aqRecord) {
        this.isCheckConfig = true;
        this.deviceInfos = aqRecord;
        HashMap map = new HashMap();
        map.put("id", String.valueOf(aqRecord.getDeviceId()));
        WebModelRepository.getInstance().aqGetRiseTime((BaseActivity) this.mContext, map, new PetkitCallback<List<List<Integer>>>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient.1
            public final /* synthetic */ AqRecord val$aqRecord;

            public AnonymousClass1(AqRecord aqRecord2) {
                aqRecord = aqRecord2;
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(List<List<Integer>> list) {
                AqBleClient.this.getConfig(list);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aqGetRiseTime error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aqGetRiseTime error:" + errorInfor.getMsg());
                AqBleClient.this.getConfig(aqRecord.getRiseTime());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<List<List<Integer>>> {
        public final /* synthetic */ AqRecord val$aqRecord;

        public AnonymousClass1(AqRecord aqRecord2) {
            aqRecord = aqRecord2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(List<List<Integer>> list) {
            AqBleClient.this.getConfig(list);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aqGetRiseTime error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqGetRiseTime error:" + errorInfor.getMsg());
            AqBleClient.this.getConfig(aqRecord.getRiseTime());
        }
    }

    public void startSecurityCheck(AqRecord aqRecord) {
        this.deviceInfos = aqRecord;
        this.isBindDevice = false;
        this.petkitBleData = BaseDataConvertor.securityCheckAndSyncData(aqRecord.getSecret(), true);
        postCustomData(nextBleMsg());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onReceiveCustomData(PetkitBleMsg petkitBleMsg) {
        super.onReceiveCustomData(petkitBleMsg);
        PetkitLog.d(TAG, "onReceiveCustomData");
        if (petkitBleMsg != null) {
            if (2 == petkitBleMsg.getType()) {
                this.deviceInfos = AqDataConvertor.parseBleMsgData(petkitBleMsg, (AqRecord) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd = petkitBleMsg.getCmd();
                if (cmd == 222) {
                    if (this.isCheckConfig) {
                        this.isCheckConfig = false;
                        ((AqRecord) this.deviceInfos).save();
                        updateDeviceInfo();
                    }
                    onReceiveBleResult(this, petkitBleMsg, (AqRecord) this.deviceInfos);
                    return;
                }
                if (cmd == 240) {
                    postCustomData(nextBleMsg());
                    return;
                }
                switch (cmd) {
                    case 210:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            postCustomData(AqDataConvertor.getTimingSetting());
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 211:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            postCustomData(AqDataConvertor.getOtherSetting());
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 212:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            onConnectSuccess(this.deviceInfos);
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 213:
                        break;
                    default:
                        onReceiveBleResult(this, petkitBleMsg, (AqRecord) this.deviceInfos);
                        break;
                }
            }
            if (1 == petkitBleMsg.getType() && petkitBleMsg.getCmd() == 230) {
                if (petkitBleMsg.getData().length < 32 || ((AqRecord) this.deviceInfos) == null) {
                    LogcatStorageHelper.addLog("receive CMD_PETKIT_BLE_230  error");
                    postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_230, AqBleDataUtils.splicingData(PetkitBLEConsts.CMD_PETKIT_BLE_230, (AqRecord) this.deviceInfos, 0), 2, petkitBleMsg.getSequence()));
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                int i = ByteUtil.toInt(petkitBleMsg.getData()[0]);
                int i2 = ByteUtil.toInt(petkitBleMsg.getData()[1]);
                int i3 = ByteUtil.toInt(petkitBleMsg.getData()[2]);
                int i4 = ByteUtil.toInt(petkitBleMsg.getData()[3]);
                ByteUtil.toInt(petkitBleMsg.getData()[4]);
                ByteUtil.toInt(petkitBleMsg.getData()[5]);
                int i5 = ByteUtil.toInt(petkitBleMsg.getData()[6]);
                int i6 = ByteUtil.toInt(petkitBleMsg.getData()[7]);
                int i7 = ByteUtil.toInt(petkitBleMsg.getData()[8]);
                int i8 = ByteUtil.toInt(petkitBleMsg.getData()[9]);
                ByteUtil.toInt(petkitBleMsg.getData()[10]);
                ByteUtil.toInt(petkitBleMsg.getData()[11]);
                int i9 = ByteUtil.toInt(petkitBleMsg.getData()[12]);
                int i10 = ByteUtil.toInt(petkitBleMsg.getData()[13]);
                int i11 = ByteUtil.toInt(petkitBleMsg.getData()[14]);
                int i12 = ByteUtil.toInt(petkitBleMsg.getData()[15]);
                ByteUtil.toInt(petkitBleMsg.getData()[16]);
                ByteUtil.toInt(petkitBleMsg.getData()[17]);
                int i13 = ByteUtil.toInt(petkitBleMsg.getData()[18]);
                int i14 = ByteUtil.toInt(petkitBleMsg.getData()[19]);
                int i15 = ByteUtil.toInt(petkitBleMsg.getData()[20]);
                int i16 = ByteUtil.toInt(petkitBleMsg.getData()[21]);
                int i17 = ByteUtil.toInt(petkitBleMsg.getData()[22]);
                ArrayList arrayList = new ArrayList();
                arrayList.add(Integer.valueOf(i15));
                arrayList.add(Integer.valueOf(i16));
                arrayList.add(Integer.valueOf(i17));
                int i18 = ByteUtil.toInt(petkitBleMsg.getData()[23]);
                int i19 = ByteUtil.toInt(petkitBleMsg.getData()[24]);
                int i20 = ByteUtil.toInt(petkitBleMsg.getData()[25]);
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(Integer.valueOf(i18));
                arrayList2.add(Integer.valueOf(i19));
                arrayList2.add(Integer.valueOf(i20));
                int i21 = ByteUtil.toInt(petkitBleMsg.getData()[26]);
                int i22 = ByteUtil.toInt(petkitBleMsg.getData()[27]);
                int i23 = ByteUtil.toInt(petkitBleMsg.getData()[28]);
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(Integer.valueOf(i21));
                arrayList3.add(Integer.valueOf(i22));
                arrayList3.add(Integer.valueOf(i23));
                int i24 = ByteUtil.toInt(petkitBleMsg.getData()[29]);
                int i25 = ByteUtil.toInt(petkitBleMsg.getData()[30]);
                int i26 = ByteUtil.toInt(petkitBleMsg.getData()[31]);
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(Integer.valueOf(i24));
                arrayList4.add(Integer.valueOf(i25));
                arrayList4.add(Integer.valueOf(i26));
                ArrayList arrayList5 = new ArrayList();
                arrayList5.add(arrayList);
                arrayList5.add(arrayList2);
                arrayList5.add(arrayList3);
                arrayList5.add(arrayList4);
                DeviceInfos deviceInfos = this.deviceInfos;
                if (deviceInfos == null) {
                    return;
                }
                AqRecord aqRecordByDeviceId = AqUtils.getAqRecordByDeviceId(deviceInfos.getDeviceId());
                this.deviceInfos = aqRecordByDeviceId;
                if (aqRecordByDeviceId == null) {
                    return;
                }
                AqConfig config = aqRecordByDeviceId.getConfig();
                if (config == null) {
                    config = new AqConfig();
                    config.setConfig(new AqConfig.ConfigBean());
                }
                config.getConfig().setStreamerBaseColors(arrayList5);
                ((AqRecord) this.deviceInfos).setConfig(config);
                ((AqRecord) this.deviceInfos).setPower(i);
                ((AqRecord) this.deviceInfos).setMode(i4);
                ((AqRecord) this.deviceInfos).setErrState(i2);
                ((AqRecord) this.deviceInfos).setPumpState(i3);
                AqWhiteMode whiteMode = ((AqRecord) this.deviceInfos).getSettings().getWhiteMode();
                whiteMode.setColorTemp(AqUtils.aqDutyCycle2ColorTemperatureAndLight(i5, i6, ((AqRecord) this.deviceInfos).getConfig().getConfig().getMaxColorTemp(), ((AqRecord) this.deviceInfos).getConfig().getConfig().getMinColorTemp()));
                whiteMode.setLight(i8);
                whiteMode.setChasingLight(i7);
                AqColorfulMode colorfulMode = ((AqRecord) this.deviceInfos).getSettings().getColorfulMode();
                colorfulMode.setLight(i9);
                ArrayList arrayList6 = new ArrayList();
                arrayList6.add(Integer.valueOf(i10));
                arrayList6.add(Integer.valueOf(i11));
                arrayList6.add(Integer.valueOf(i12));
                Log.e("RgbColor", "230 RgbColor:" + arrayList6.toString());
                LogcatStorageHelper.addLog("230 RgbColor:" + arrayList6.toString());
                colorfulMode.setRgbColor(arrayList6);
                AqStreamerMode streamerMode = ((AqRecord) this.deviceInfos).getSettings().getStreamerMode();
                streamerMode.setLight(i13);
                streamerMode.setSpeed(i14);
                this.deviceInfos.save();
                EventBus.getDefault().post(new RefreshAqMsg((AqRecord) this.deviceInfos, "success", true));
                postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_230, AqBleDataUtils.splicingData(PetkitBLEConsts.CMD_PETKIT_BLE_230, (AqRecord) this.deviceInfos, 1), 2, petkitBleMsg.getSequence()));
                HashMap map = new HashMap();
                map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
                map.put("kv", new Gson().toJson(new AqUpdateParam((AqRecord) this.deviceInfos)));
                EventBus.getDefault().post(new UpdateAqMsg(map));
                return;
            }
            return;
        }
        PetkitLog.d("data bytes is null");
        LogcatStorageHelper.addLog("data bytes is null");
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
        map.put("coldWhiteLightTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        System.arraycopy(petkitBleMsg.getData(), 10, bArr2, 0, 4);
        map.put("warmWhiteLightTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        System.arraycopy(petkitBleMsg.getData(), 14, bArr2, 0, 4);
        map.put("colorLightTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        System.arraycopy(petkitBleMsg.getData(), 18, bArr2, 0, 4);
        map.put("pumpTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        map.put("testResult", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[22])));
        map.put("testResultCode", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[23])));
        WebModelRepository.getInstance().aqSaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient.2
            public AnonymousClass2() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save Log:" + str);
                LogcatStorageHelper.addLog("aq save Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient$2 */
    public class AnonymousClass2 implements PetkitCallback<String> {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save Log:" + str);
            LogcatStorageHelper.addLog("aq save Log:" + str);
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

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void getConfig() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(((AqRecord) this.deviceInfos).getDeviceId()));
        WebModelRepository.getInstance().getConfig((BaseActivity) this.mContext, map, new PetkitCallback<AqConfig>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqConfig aqConfig) {
                ((AqRecord) AqBleClient.this.deviceInfos).setConfig(aqConfig);
                if (AqBleClient.this.isBindDevice) {
                    PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                    AqBleClient aqBleClient = AqBleClient.this;
                    petkitBleDeviceManager.startBind(aqBleClient.device, (AqRecord) aqBleClient.deviceInfos);
                } else {
                    PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                    AqBleClient aqBleClient2 = AqBleClient.this;
                    petkitBleDeviceManager2.startSync(aqBleClient2.device, (AqRecord) aqBleClient2.deviceInfos);
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq get Config fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq get Config fail  " + errorInfor.getMsg());
                if (AqBleClient.this.isBindDevice) {
                    PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                    AqBleClient aqBleClient = AqBleClient.this;
                    petkitBleDeviceManager.startBind(aqBleClient.device, (AqRecord) aqBleClient.deviceInfos);
                } else {
                    PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                    AqBleClient aqBleClient2 = AqBleClient.this;
                    petkitBleDeviceManager2.startSync(aqBleClient2.device, (AqRecord) aqBleClient2.deviceInfos);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<AqConfig> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqConfig aqConfig) {
            ((AqRecord) AqBleClient.this.deviceInfos).setConfig(aqConfig);
            if (AqBleClient.this.isBindDevice) {
                PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                AqBleClient aqBleClient = AqBleClient.this;
                petkitBleDeviceManager.startBind(aqBleClient.device, (AqRecord) aqBleClient.deviceInfos);
            } else {
                PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                AqBleClient aqBleClient2 = AqBleClient.this;
                petkitBleDeviceManager2.startSync(aqBleClient2.device, (AqRecord) aqBleClient2.deviceInfos);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq get Config fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq get Config fail  " + errorInfor.getMsg());
            if (AqBleClient.this.isBindDevice) {
                PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                AqBleClient aqBleClient = AqBleClient.this;
                petkitBleDeviceManager.startBind(aqBleClient.device, (AqRecord) aqBleClient.deviceInfos);
            } else {
                PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                AqBleClient aqBleClient2 = AqBleClient.this;
                petkitBleDeviceManager2.startSync(aqBleClient2.device, (AqRecord) aqBleClient2.deviceInfos);
            }
        }
    }

    public void getConfig(List<List<Integer>> list) {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(((AqRecord) this.deviceInfos).getDeviceId()));
        WebModelRepository.getInstance().getConfig((BaseActivity) this.mContext, map, new PetkitCallback<AqConfig>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient.4
            public final /* synthetic */ List val$riseTime;

            public AnonymousClass4(List list2) {
                list = list2;
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqConfig aqConfig) {
                if (aqConfig.equals(((AqRecord) AqBleClient.this.deviceInfos).getConfig()) && ((AqRecord) AqBleClient.this.deviceInfos).isTheSameRiseTime(list)) {
                    AqBleClient.this.isCheckConfig = false;
                    AqBleClient.this.updateDeviceInfo();
                } else {
                    ((AqRecord) AqBleClient.this.deviceInfos).setConfig(aqConfig);
                    ((AqRecord) AqBleClient.this.deviceInfos).setRiseTime(list);
                    AqBleClient aqBleClient = AqBleClient.this;
                    aqBleClient.postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_222, AqBleDataUtils.splicingData(PetkitBLEConsts.CMD_PETKIT_BLE_222, (AqRecord) aqBleClient.deviceInfos)));
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
                EventBus.getDefault().post(new RefreshAqMsg((AqRecord) AqBleClient.this.deviceInfos, errorInfor.getMsg()));
                AqBleClient.this.isCheckConfig = false;
                AqBleClient.this.updateDeviceInfo();
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient$4 */
    public class AnonymousClass4 implements PetkitCallback<AqConfig> {
        public final /* synthetic */ List val$riseTime;

        public AnonymousClass4(List list2) {
            list = list2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqConfig aqConfig) {
            if (aqConfig.equals(((AqRecord) AqBleClient.this.deviceInfos).getConfig()) && ((AqRecord) AqBleClient.this.deviceInfos).isTheSameRiseTime(list)) {
                AqBleClient.this.isCheckConfig = false;
                AqBleClient.this.updateDeviceInfo();
            } else {
                ((AqRecord) AqBleClient.this.deviceInfos).setConfig(aqConfig);
                ((AqRecord) AqBleClient.this.deviceInfos).setRiseTime(list);
                AqBleClient aqBleClient = AqBleClient.this;
                aqBleClient.postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_222, AqBleDataUtils.splicingData(PetkitBLEConsts.CMD_PETKIT_BLE_222, (AqRecord) aqBleClient.deviceInfos)));
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
            EventBus.getDefault().post(new RefreshAqMsg((AqRecord) AqBleClient.this.deviceInfos, errorInfor.getMsg()));
            AqBleClient.this.isCheckConfig = false;
            AqBleClient.this.updateDeviceInfo();
        }
    }

    public void updateDeviceInfo() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(((AqRecord) this.deviceInfos).getDeviceId()));
        map.put("kv", new Gson().toJson(new AqUpdateParam((AqRecord) this.deviceInfos)));
        WebModelRepository.getInstance().aqUpdate((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient.5
            public AnonymousClass5() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("aqUpdate：" + str);
                LogcatStorageHelper.addLog("aqUpdate：" + str);
                if ("success".equals(str)) {
                    ((AqRecord) AqBleClient.this.deviceInfos).save();
                    EventBus.getDefault().post(new RefreshAqMsg((AqRecord) AqBleClient.this.deviceInfos, str));
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
                EventBus.getDefault().post(new RefreshAqMsg((AqRecord) AqBleClient.this.deviceInfos, errorInfor.getMsg()));
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient$5 */
    public class AnonymousClass5 implements PetkitCallback<String> {
        public AnonymousClass5() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("aqUpdate：" + str);
            LogcatStorageHelper.addLog("aqUpdate：" + str);
            if ("success".equals(str)) {
                ((AqRecord) AqBleClient.this.deviceInfos).save();
                EventBus.getDefault().post(new RefreshAqMsg((AqRecord) AqBleClient.this.deviceInfos, str));
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqUpdate error:" + errorInfor.getCode() + errorInfor.getMsg());
            EventBus.getDefault().post(new RefreshAqMsg((AqRecord) AqBleClient.this.deviceInfos, errorInfor.getMsg()));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void signup() {
        PetkitLog.d(TAG, "signup");
        PetkitLog.d(TAG, "sn:" + this.deviceInfos.getSn());
        HashMap map = new HashMap();
        if (this.deviceInfos.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.deviceInfos.getDeviceId()));
        }
        map.put("mac", this.deviceInfos.getMac());
        if (this.deviceInfos.getSn() != null) {
            map.put("sn", this.deviceInfos.getSn());
        }
        WebModelRepository.getInstance().aqSignup((BaseActivity) this.mContext, map, new PetkitCallback<AqDevice>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient.6
            public AnonymousClass6() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqDevice aqDevice) {
                PetkitLog.d("aqSignup success");
                LogcatStorageHelper.addLog("aqSignup success");
                if (aqDevice == null) {
                    AqBleClient.this.signupFail(new ErrorInfor(33, "aqDevice is null"));
                    return;
                }
                AqRecord aqRecord = new AqRecord(aqDevice);
                LogcatStorageHelper.addLog("signup deviceId:" + AqBleClient.this.deviceInfos.getDeviceId());
                AqBleClient.this.signupSuccess(aqRecord);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq signup fail " + errorInfor.getMsg());
                AqBleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.AqBleClient$6 */
    public class AnonymousClass6 implements PetkitCallback<AqDevice> {
        public AnonymousClass6() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqDevice aqDevice) {
            PetkitLog.d("aqSignup success");
            LogcatStorageHelper.addLog("aqSignup success");
            if (aqDevice == null) {
                AqBleClient.this.signupFail(new ErrorInfor(33, "aqDevice is null"));
                return;
            }
            AqRecord aqRecord = new AqRecord(aqDevice);
            LogcatStorageHelper.addLog("signup deviceId:" + AqBleClient.this.deviceInfos.getDeviceId());
            AqBleClient.this.signupSuccess(aqRecord);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq signup fail " + errorInfor.getMsg());
            AqBleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d(TAG, "aq findSn");
        LogcatStorageHelper.addLog("aq findSn start");
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d(TAG, "securityCheckFinish");
        postCustomData(AqDataConvertor.getAqConfig());
    }
}

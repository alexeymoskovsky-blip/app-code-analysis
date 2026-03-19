package com.petkit.android.activities.petkitBleDevice.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceBindUtils;
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
import com.petkit.android.activities.petkitBleDevice.ble.mode.Aq1sDataConvertor;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class Aq1sBleClient extends PetkitBleClient {
    public static final String TAG = "AQR_OTA";
    public boolean isCheckConfig;
    public DeviceBindUtils.DeviceBindResultListener listener;
    public DeviceScanResult result;

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public boolean isAqDevice() {
        return true;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processStreamData(Map<Integer, PetkitBleMsg> map) {
    }

    public Aq1sBleClient(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    public Aq1sBleClient(Context context, BluetoothDevice bluetoothDevice, PetkitBleClient.BleClientListener bleClientListener) {
        super(context, bluetoothDevice, bleClientListener);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void findSn() {
        PetkitLog.d("AQR_OTA", "findSn");
        LogcatStorageHelper.addLog("findSn start");
        HashMap map = new HashMap();
        map.put("mac", this.deviceInfos.getMac());
        WebModelRepository.getInstance().aqFindSn((BaseActivity) this.mContext, map, new PetkitCallback<SnResult>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(SnResult snResult) {
                if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                    Aq1sBleClient.this.signupFail(new ErrorInfor(33, "get aqSn from server null"));
                    LogcatStorageHelper.addLog("get aqSn from server null " + snResult.toString());
                    PetkitLog.d("get aqSn from server null");
                    return;
                }
                PetkitLog.d("aqSn success " + snResult.toString());
                LogcatStorageHelper.addLog("aqSn success " + snResult.toString());
                Aq1sBleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq aqFindSn fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq k3FindSn fail " + errorInfor.getMsg());
                Aq1sBleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient$1 */
    public class AnonymousClass1 implements PetkitCallback<SnResult> {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(SnResult snResult) {
            if (snResult == null || TextUtils.isEmpty(snResult.getSn())) {
                Aq1sBleClient.this.signupFail(new ErrorInfor(33, "get aqSn from server null"));
                LogcatStorageHelper.addLog("get aqSn from server null " + snResult.toString());
                PetkitLog.d("get aqSn from server null");
                return;
            }
            PetkitLog.d("aqSn success " + snResult.toString());
            LogcatStorageHelper.addLog("aqSn success " + snResult.toString());
            Aq1sBleClient.this.postCustomData(BaseDataConvertor.writeSn(snResult.getSn()));
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq aqFindSn fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq k3FindSn fail " + errorInfor.getMsg());
            Aq1sBleClient.this.signupFail(errorInfor);
        }
    }

    public void checkConfig(AqRecord aqRecord) {
        this.isCheckConfig = true;
        this.deviceInfos = aqRecord;
        HashMap map = new HashMap();
        map.put("id", String.valueOf(aqRecord.getDeviceId()));
        WebModelRepository.getInstance().aqGetRiseTime((BaseActivity) this.mContext, map, new PetkitCallback<List<List<Integer>>>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient.2
            public final /* synthetic */ AqRecord val$aqRecord;

            public AnonymousClass2(AqRecord aqRecord2) {
                aqRecord = aqRecord2;
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(List<List<Integer>> list) {
                PetkitLog.d("aq1s:Client", "aqGetRiseTime成功");
                Aq1sBleClient.this.getConfig(list);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aqrGetRiseTime error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aqrGetRiseTime error:" + errorInfor.getMsg());
                Aq1sBleClient.this.getConfig(aqRecord.getRiseTime());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient$2 */
    public class AnonymousClass2 implements PetkitCallback<List<List<Integer>>> {
        public final /* synthetic */ AqRecord val$aqRecord;

        public AnonymousClass2(AqRecord aqRecord2) {
            aqRecord = aqRecord2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(List<List<Integer>> list) {
            PetkitLog.d("aq1s:Client", "aqGetRiseTime成功");
            Aq1sBleClient.this.getConfig(list);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aqrGetRiseTime error:" + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aqrGetRiseTime error:" + errorInfor.getMsg());
            Aq1sBleClient.this.getConfig(aqRecord.getRiseTime());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient, com.petkit.android.activities.petkitBleDevice.ble.PetkitBleListener
    public void onReceiveCustomData(PetkitBleMsg petkitBleMsg) {
        super.onReceiveCustomData(petkitBleMsg);
        PetkitLog.d("AQR_OTA", "onReceiveCustomData");
        if (petkitBleMsg != null) {
            if (2 == petkitBleMsg.getType()) {
                this.deviceInfos = Aq1sDataConvertor.parseBleMsgData(petkitBleMsg, (AqRecord) this.deviceInfos, (BaseActivity) this.mContext);
                int cmd = petkitBleMsg.getCmd();
                if (cmd == 240) {
                    postCustomData(nextBleMsg());
                    return;
                }
                switch (cmd) {
                    case 210:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            PetkitLog.d("aq1s:Client", "接受指令210");
                            postCustomData(Aq1sDataConvertor.getTimingSetting());
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 211:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            PetkitLog.d("aq1s:Client", "接受指令211");
                            postCustomData(Aq1sDataConvertor.getOtherSetting());
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 212:
                        if (this.deviceInfos.getDeviceId() != -1) {
                            PetkitLog.d("aq1s:Client", "接受指令212");
                            changeLinkStatus(2);
                            onConnectSuccess(this.deviceInfos);
                            onReceiveBleResult(this, petkitBleMsg, this.deviceInfos);
                        } else {
                            onError(this, petkitBleMsg.getCmd());
                        }
                        break;
                    case 213:
                        break;
                    default:
                        switch (cmd) {
                            case PetkitBLEConsts.CMD_PETKIT_BLE_220 /* 220 */:
                                PetkitLog.d("aq1s:Client", "接受指令220");
                                onReceiveBleResult(this, petkitBleMsg, (AqRecord) this.deviceInfos);
                                break;
                            case PetkitBLEConsts.CMD_PETKIT_BLE_221 /* 221 */:
                                PetkitLog.d("aq1s:Client", "接受指令221");
                                onReceiveBleResult(this, petkitBleMsg, (AqRecord) this.deviceInfos);
                                break;
                            case PetkitBLEConsts.CMD_PETKIT_BLE_222 /* 222 */:
                                PetkitLog.d("aq1s:Client", "接受指令222");
                                if (this.isCheckConfig) {
                                    this.isCheckConfig = false;
                                    ((AqRecord) this.deviceInfos).save();
                                    updateDeviceInfo();
                                    PetkitLog.d("aq1s:222");
                                }
                                onReceiveBleResult(this, petkitBleMsg, (AqRecord) this.deviceInfos);
                                break;
                            default:
                                onReceiveBleResult(this, petkitBleMsg, (AqRecord) this.deviceInfos);
                                break;
                        }
                        break;
                }
            }
            if (1 == petkitBleMsg.getType() && petkitBleMsg.getCmd() == 230) {
                PetkitLog.d("aq1s:Client", "接受指令230");
                if (petkitBleMsg.getData().length < 32 || ((AqRecord) this.deviceInfos) == null) {
                    LogcatStorageHelper.addLog("receive CMD_PETKIT_BLE_230  error");
                    postCustomData(new PetkitBleMsg(PetkitBLEConsts.CMD_PETKIT_BLE_230, AqBleDataUtils.splicingData(PetkitBLEConsts.CMD_PETKIT_BLE_230, (AqRecord) this.deviceInfos, 0), 2, petkitBleMsg.getSequence()));
                    onError(this, petkitBleMsg.getCmd());
                    return;
                }
                if (petkitBleMsg.getData().length == 32) {
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
                    colorfulMode.setRgbColor(arrayList6);
                    AqStreamerMode streamerMode = ((AqRecord) this.deviceInfos).getSettings().getStreamerMode();
                    streamerMode.setLight(i13);
                    streamerMode.setSpeed(i14);
                    this.deviceInfos.save();
                } else {
                    int i27 = ByteUtil.toInt(petkitBleMsg.getData()[0]);
                    int i28 = ByteUtil.toInt(petkitBleMsg.getData()[1]);
                    int i29 = ByteUtil.toInt(petkitBleMsg.getData()[2]);
                    int i30 = ByteUtil.toInt(petkitBleMsg.getData()[3]);
                    int i31 = ByteUtil.toInt(petkitBleMsg.getData()[5]);
                    byte[] bArr = new byte[4];
                    System.arraycopy(petkitBleMsg.getData(), 6, bArr, 0, 4);
                    ByteUtil.toInt(petkitBleMsg.getData()[10]);
                    ByteUtil.toInt(petkitBleMsg.getData()[11]);
                    int i32 = ByteUtil.toInt(petkitBleMsg.getData()[12]);
                    int i33 = ByteUtil.toInt(petkitBleMsg.getData()[13]);
                    int i34 = ByteUtil.toInt(petkitBleMsg.getData()[14]);
                    int i35 = ByteUtil.toInt(petkitBleMsg.getData()[15]);
                    ByteUtil.toInt(petkitBleMsg.getData()[16]);
                    ByteUtil.toInt(petkitBleMsg.getData()[17]);
                    int i36 = ByteUtil.toInt(petkitBleMsg.getData()[18]);
                    int i37 = ByteUtil.toInt(petkitBleMsg.getData()[19]);
                    int i38 = ByteUtil.toInt(petkitBleMsg.getData()[20]);
                    int i39 = ByteUtil.toInt(petkitBleMsg.getData()[21]);
                    ByteUtil.toInt(petkitBleMsg.getData()[22]);
                    ByteUtil.toInt(petkitBleMsg.getData()[23]);
                    int i40 = ByteUtil.toInt(petkitBleMsg.getData()[24]);
                    int i41 = ByteUtil.toInt(petkitBleMsg.getData()[25]);
                    int i42 = ByteUtil.toInt(petkitBleMsg.getData()[26]);
                    int i43 = ByteUtil.toInt(petkitBleMsg.getData()[27]);
                    int i44 = ByteUtil.toInt(petkitBleMsg.getData()[28]);
                    ArrayList arrayList7 = new ArrayList();
                    arrayList7.add(Integer.valueOf(i42));
                    arrayList7.add(Integer.valueOf(i43));
                    arrayList7.add(Integer.valueOf(i44));
                    int i45 = ByteUtil.toInt(petkitBleMsg.getData()[29]);
                    int i46 = ByteUtil.toInt(petkitBleMsg.getData()[30]);
                    int i47 = ByteUtil.toInt(petkitBleMsg.getData()[31]);
                    ArrayList arrayList8 = new ArrayList();
                    arrayList8.add(Integer.valueOf(i45));
                    arrayList8.add(Integer.valueOf(i46));
                    arrayList8.add(Integer.valueOf(i47));
                    int i48 = ByteUtil.toInt(petkitBleMsg.getData()[32]);
                    int i49 = ByteUtil.toInt(petkitBleMsg.getData()[33]);
                    int i50 = ByteUtil.toInt(petkitBleMsg.getData()[34]);
                    ArrayList arrayList9 = new ArrayList();
                    arrayList9.add(Integer.valueOf(i48));
                    arrayList9.add(Integer.valueOf(i49));
                    arrayList9.add(Integer.valueOf(i50));
                    int i51 = ByteUtil.toInt(petkitBleMsg.getData()[35]);
                    int i52 = ByteUtil.toInt(petkitBleMsg.getData()[36]);
                    int i53 = ByteUtil.toInt(petkitBleMsg.getData()[37]);
                    ArrayList arrayList10 = new ArrayList();
                    arrayList10.add(Integer.valueOf(i51));
                    arrayList10.add(Integer.valueOf(i52));
                    arrayList10.add(Integer.valueOf(i53));
                    arrayList10.add(Integer.valueOf(i53));
                    ArrayList arrayList11 = new ArrayList();
                    arrayList11.add(arrayList7);
                    arrayList11.add(arrayList8);
                    arrayList11.add(arrayList9);
                    arrayList11.add(arrayList10);
                    DeviceInfos deviceInfos2 = this.deviceInfos;
                    if (deviceInfos2 == null) {
                        return;
                    }
                    AqRecord aqRecordByDeviceId2 = AqUtils.getAqRecordByDeviceId(deviceInfos2.getDeviceId());
                    this.deviceInfos = aqRecordByDeviceId2;
                    if (aqRecordByDeviceId2 == null) {
                        return;
                    }
                    AqConfig config2 = aqRecordByDeviceId2.getConfig();
                    if (config2 == null) {
                        config2 = new AqConfig();
                        config2.setConfig(new AqConfig.ConfigBean());
                    }
                    config2.getConfig().setStreamerBaseColors(arrayList11);
                    ((AqRecord) this.deviceInfos).setConfig(config2);
                    ((AqRecord) this.deviceInfos).setPower(i27);
                    ((AqRecord) this.deviceInfos).setMode(i30);
                    ((AqRecord) this.deviceInfos).setErrState(i28);
                    ((AqRecord) this.deviceInfos).setPumpState(i29);
                    ((AqRecord) this.deviceInfos).setIsSterilizing(i31);
                    ((AqRecord) this.deviceInfos).setSterilizationTime(ByteUtil.bytes2Int(bArr));
                    AqWhiteMode whiteMode2 = ((AqRecord) this.deviceInfos).getSettings().getWhiteMode();
                    whiteMode2.setColorTemp(AqUtils.aqDutyCycle2ColorTemperatureAndLight(i32, i33, ((AqRecord) this.deviceInfos).getConfig().getConfig().getMaxColorTemp(), ((AqRecord) this.deviceInfos).getConfig().getConfig().getMinColorTemp()));
                    whiteMode2.setLight(i35);
                    whiteMode2.setChasingLight(i34);
                    AqColorfulMode colorfulMode2 = ((AqRecord) this.deviceInfos).getSettings().getColorfulMode();
                    colorfulMode2.setLight(i36);
                    ArrayList arrayList12 = new ArrayList();
                    arrayList12.add(Integer.valueOf(i37));
                    arrayList12.add(Integer.valueOf(i38));
                    arrayList12.add(Integer.valueOf(i39));
                    colorfulMode2.setRgbColor(arrayList12);
                    AqStreamerMode streamerMode2 = ((AqRecord) this.deviceInfos).getSettings().getStreamerMode();
                    streamerMode2.setLight(i40);
                    streamerMode2.setSpeed(i41);
                    this.deviceInfos.save();
                }
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
        map.put("colorLightTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        System.arraycopy(petkitBleMsg.getData(), 14, bArr2, 0, 4);
        map.put("pumpTime", String.valueOf(ByteUtil.bytes2Int(bArr2)));
        map.put("testResult", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[18])));
        map.put("testResultCode", String.valueOf(ByteUtil.toInt(petkitBleMsg.getData()[19])));
        WebModelRepository.getInstance().aqSaveLog((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("save Log:" + str);
                LogcatStorageHelper.addLog("aq1s save Log:" + str);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("save Log error:" + errorInfor.getMsg());
                LogcatStorageHelper.addLog("save Log error:" + errorInfor.getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient$3 */
    public class AnonymousClass3 implements PetkitCallback<String> {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("save Log:" + str);
            LogcatStorageHelper.addLog("aq1s save Log:" + str);
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
        WebModelRepository.getInstance().getConfig((BaseActivity) this.mContext, map, new PetkitCallback<AqConfig>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient.4
            public AnonymousClass4() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqConfig aqConfig) {
                ((AqRecord) Aq1sBleClient.this.deviceInfos).setConfig(aqConfig);
                if (Aq1sBleClient.this.isBindDevice) {
                    PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                    Aq1sBleClient aq1sBleClient = Aq1sBleClient.this;
                    petkitBleDeviceManager.startBind(aq1sBleClient.device, (AqRecord) aq1sBleClient.deviceInfos);
                } else {
                    PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                    Aq1sBleClient aq1sBleClient2 = Aq1sBleClient.this;
                    petkitBleDeviceManager2.startSync(aq1sBleClient2.device, (AqRecord) aq1sBleClient2.deviceInfos);
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq get Config fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq get Config fail  " + errorInfor.getMsg());
                if (Aq1sBleClient.this.isBindDevice) {
                    PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                    Aq1sBleClient aq1sBleClient = Aq1sBleClient.this;
                    petkitBleDeviceManager.startBind(aq1sBleClient.device, (AqRecord) aq1sBleClient.deviceInfos);
                } else {
                    PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                    Aq1sBleClient aq1sBleClient2 = Aq1sBleClient.this;
                    petkitBleDeviceManager2.startSync(aq1sBleClient2.device, (AqRecord) aq1sBleClient2.deviceInfos);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient$4 */
    public class AnonymousClass4 implements PetkitCallback<AqConfig> {
        public AnonymousClass4() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqConfig aqConfig) {
            ((AqRecord) Aq1sBleClient.this.deviceInfos).setConfig(aqConfig);
            if (Aq1sBleClient.this.isBindDevice) {
                PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                Aq1sBleClient aq1sBleClient = Aq1sBleClient.this;
                petkitBleDeviceManager.startBind(aq1sBleClient.device, (AqRecord) aq1sBleClient.deviceInfos);
            } else {
                PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                Aq1sBleClient aq1sBleClient2 = Aq1sBleClient.this;
                petkitBleDeviceManager2.startSync(aq1sBleClient2.device, (AqRecord) aq1sBleClient2.deviceInfos);
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq get Config fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq get Config fail  " + errorInfor.getMsg());
            if (Aq1sBleClient.this.isBindDevice) {
                PetkitBleDeviceManager petkitBleDeviceManager = PetkitBleDeviceManager.getInstance();
                Aq1sBleClient aq1sBleClient = Aq1sBleClient.this;
                petkitBleDeviceManager.startBind(aq1sBleClient.device, (AqRecord) aq1sBleClient.deviceInfos);
            } else {
                PetkitBleDeviceManager petkitBleDeviceManager2 = PetkitBleDeviceManager.getInstance();
                Aq1sBleClient aq1sBleClient2 = Aq1sBleClient.this;
                petkitBleDeviceManager2.startSync(aq1sBleClient2.device, (AqRecord) aq1sBleClient2.deviceInfos);
            }
        }
    }

    public final void getConfig(List<List<Integer>> list) {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(((AqRecord) this.deviceInfos).getDeviceId()));
        WebModelRepository.getInstance().getConfig((BaseActivity) this.mContext, map, new PetkitCallback<AqConfig>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient.5
            public final /* synthetic */ List val$riseTime;

            public AnonymousClass5(List list2) {
                list = list2;
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqConfig aqConfig) {
                PetkitLog.d("aq1s:Client", "getConfig成功");
                if (aqConfig.equals(((AqRecord) Aq1sBleClient.this.deviceInfos).getConfig()) && ((AqRecord) Aq1sBleClient.this.deviceInfos).isTheSameRiseTime(list)) {
                    Aq1sBleClient.this.isCheckConfig = false;
                    Aq1sBleClient.this.updateDeviceInfo();
                } else {
                    ((AqRecord) Aq1sBleClient.this.deviceInfos).setConfig(aqConfig);
                    ((AqRecord) Aq1sBleClient.this.deviceInfos).setRiseTime(list);
                    Aq1sBleClient aq1sBleClient = Aq1sBleClient.this;
                    aq1sBleClient.postCustomData(Aq1sDataConvertor.modifyConfig((AqRecord) aq1sBleClient.deviceInfos));
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
                EventBus.getDefault().post(new RefreshAqMsg((AqRecord) Aq1sBleClient.this.deviceInfos, errorInfor.getMsg()));
                Aq1sBleClient.this.isCheckConfig = false;
                Aq1sBleClient.this.updateDeviceInfo();
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient$5 */
    public class AnonymousClass5 implements PetkitCallback<AqConfig> {
        public final /* synthetic */ List val$riseTime;

        public AnonymousClass5(List list2) {
            list = list2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqConfig aqConfig) {
            PetkitLog.d("aq1s:Client", "getConfig成功");
            if (aqConfig.equals(((AqRecord) Aq1sBleClient.this.deviceInfos).getConfig()) && ((AqRecord) Aq1sBleClient.this.deviceInfos).isTheSameRiseTime(list)) {
                Aq1sBleClient.this.isCheckConfig = false;
                Aq1sBleClient.this.updateDeviceInfo();
            } else {
                ((AqRecord) Aq1sBleClient.this.deviceInfos).setConfig(aqConfig);
                ((AqRecord) Aq1sBleClient.this.deviceInfos).setRiseTime(list);
                Aq1sBleClient aq1sBleClient = Aq1sBleClient.this;
                aq1sBleClient.postCustomData(Aq1sDataConvertor.modifyConfig((AqRecord) aq1sBleClient.deviceInfos));
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq getConfig error:" + errorInfor.getCode() + errorInfor.getMsg());
            EventBus.getDefault().post(new RefreshAqMsg((AqRecord) Aq1sBleClient.this.deviceInfos, errorInfor.getMsg()));
            Aq1sBleClient.this.isCheckConfig = false;
            Aq1sBleClient.this.updateDeviceInfo();
        }
    }

    public final void updateDeviceInfo() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(((AqRecord) this.deviceInfos).getDeviceId()));
        map.put("kv", new Gson().toJson(new AqUpdateParam((AqRecord) this.deviceInfos)));
        WebModelRepository.getInstance().aqUpdate((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient.6
            public AnonymousClass6() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PetkitLog.d("aq1s update success");
                LogcatStorageHelper.addLog("aq1s update success");
                if ("success".equals(str)) {
                    ((AqRecord) Aq1sBleClient.this.deviceInfos).save();
                    EventBus.getDefault().post(new RefreshAqMsg((AqRecord) Aq1sBleClient.this.deviceInfos, str));
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq1s update fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq1s update fail " + errorInfor.getMsg());
                EventBus.getDefault().post(new RefreshAqMsg((AqRecord) Aq1sBleClient.this.deviceInfos, errorInfor.getMsg()));
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient$6 */
    public class AnonymousClass6 implements PetkitCallback<String> {
        public AnonymousClass6() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(String str) {
            PetkitLog.d("aq1s update success");
            LogcatStorageHelper.addLog("aq1s update success");
            if ("success".equals(str)) {
                ((AqRecord) Aq1sBleClient.this.deviceInfos).save();
                EventBus.getDefault().post(new RefreshAqMsg((AqRecord) Aq1sBleClient.this.deviceInfos, str));
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq1s update fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq1s update fail " + errorInfor.getMsg());
            EventBus.getDefault().post(new RefreshAqMsg((AqRecord) Aq1sBleClient.this.deviceInfos, errorInfor.getMsg()));
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
        if (this.deviceInfos.getSn() != null) {
            map.put("sn", this.deviceInfos.getSn());
        }
        WebModelRepository.getInstance().aqSignup((BaseActivity) this.mContext, map, new PetkitCallback<AqDevice>() { // from class: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient.7
            public AnonymousClass7() {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(AqDevice aqDevice) {
                PetkitLog.d("aqSignup success");
                LogcatStorageHelper.addLog("aqSignup success");
                if (aqDevice == null) {
                    Aq1sBleClient.this.signupFail(new ErrorInfor(33, "aqDevice is null"));
                    return;
                }
                AqRecord aqRecord = new AqRecord(aqDevice);
                LogcatStorageHelper.addLog("signup deviceId:" + Aq1sBleClient.this.deviceInfos.getDeviceId());
                Aq1sBleClient.this.signupSuccess(aqRecord);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("aq signup fail " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("aq signup fail " + errorInfor.getMsg());
                Aq1sBleClient.this.signupFail(errorInfor);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ble.Aq1sBleClient$7 */
    public class AnonymousClass7 implements PetkitCallback<AqDevice> {
        public AnonymousClass7() {
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqDevice aqDevice) {
            PetkitLog.d("aqSignup success");
            LogcatStorageHelper.addLog("aqSignup success");
            if (aqDevice == null) {
                Aq1sBleClient.this.signupFail(new ErrorInfor(33, "aqDevice is null"));
                return;
            }
            AqRecord aqRecord = new AqRecord(aqDevice);
            LogcatStorageHelper.addLog("signup deviceId:" + Aq1sBleClient.this.deviceInfos.getDeviceId());
            Aq1sBleClient.this.signupSuccess(aqRecord);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitLog.d("aq signup fail " + errorInfor.getMsg());
            LogcatStorageHelper.addLog("aq signup fail " + errorInfor.getMsg());
            Aq1sBleClient.this.signupFail(errorInfor);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void securityCheckFinish() {
        PetkitLog.d("AQR_OTA", "securityCheckFinish");
        postCustomData(AqrDataConvertor.getAqConfig());
    }

    @Override // com.petkit.android.activities.device.utils.DeviceBindUtils.DeviceBindResultListener
    public void uploadServerFinish() {
        DeviceBindUtils.DeviceBindResultListener deviceBindResultListener = this.listener;
        if (deviceBindResultListener != null) {
            deviceBindResultListener.uploadServerFinish();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ble.PetkitBleClient
    public void processServerConfigAndUpdate(BaseActivity baseActivity, long j, String str, BluetoothDevice bluetoothDevice, int i, DeviceBindUtils.DeviceBindResultListener deviceBindResultListener) {
        this.listener = deviceBindResultListener;
        checkConfig((AqRecord) this.deviceInfos);
    }
}

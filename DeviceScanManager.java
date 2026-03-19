package com.petkit.android.activities.device.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.util.Log;
import com.jess.arms.utils.Consts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.ble.DeviceScanResult;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.ble.PetkitBLEManager;
import com.petkit.android.ble.StopDeviceScanMsg;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceScanManager implements PetkitBLEManager.onPetkitBleListener {
    public final int TIMER_INTERVAL;
    public final String Tag;
    public PetkitBLEManager bleManager;
    public CountDownTimer countDownTimer;
    public List<Integer> deviceTypeList;
    public Disposable disposable;
    public boolean isRegister;
    public BroadcastReceiver receiver;
    public List<DeviceScanResult> resultList;
    public DeviceType[] scanDeviceType;
    public List<ScanResult> scanResults;
    public int scanTime;
    public Map<Integer, List<Integer>> typeCodeMap;
    public List<String> wifiDeviceType;
    public WifiManager wifiManager;

    public enum DeviceType {
        FIT,
        MATE,
        GO,
        T3,
        K2,
        D1,
        D2,
        COZY,
        AQ,
        D3,
        D4,
        P3,
        W5,
        AQR,
        T4_2,
        T4,
        K3,
        R2,
        W5C,
        W5N,
        W4X,
        AQH1,
        CTW2,
        D4_1,
        D4_2,
        T6,
        D4S,
        HG,
        CTW3,
        CTW3_2,
        CTW3_UV,
        CTW3_100,
        CTW3_UV_100,
        W4XUVC,
        D4SH,
        D4H,
        D4SH_2,
        D4H_2,
        D4SH_3,
        D4H_3,
        T5,
        T5_2,
        T7,
        W7H,
        ALL
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onReceiveCustomData(int i, String str) {
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onStateChanged(PetkitBLEConsts.ConnectState connectState) {
    }

    public /* synthetic */ DeviceScanManager(AnonymousClass1 anonymousClass1) {
        this();
    }

    public List<DeviceScanResult> getResultList() {
        return this.resultList;
    }

    public void clearResultList() {
        this.resultList.clear();
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onLeScan(BluetoothDevice bluetoothDevice, DeviceInfo deviceInfo) {
        Log.d("DeviceScanManager", deviceInfo.toString());
        if (this.deviceTypeList.contains(Integer.valueOf(deviceInfo.getType()))) {
            Log.d("test123", this.deviceTypeList.toString());
            Log.d("test123", deviceInfo.toString());
            if (this.deviceTypeList.size() == 1) {
                if (deviceInfo.getDeviceType() == 14 || deviceInfo.getDeviceType() == 11 || deviceInfo.getDeviceType() == 25 || deviceInfo.getDeviceType() == 26) {
                    if (this.typeCodeMap.containsKey(Integer.valueOf(deviceInfo.getDeviceType())) && this.typeCodeMap.get(Integer.valueOf(deviceInfo.getDeviceType())) != null && this.typeCodeMap.get(Integer.valueOf(deviceInfo.getDeviceType())).contains(Integer.valueOf(deviceInfo.getTypeCode()))) {
                        DeviceScanResult deviceScanResult = new DeviceScanResult(bluetoothDevice, deviceInfo, DeviceScanResult.ScanResultType.DEVICE_DATA);
                        EventBus.getDefault().post(deviceScanResult);
                        if (this.resultList.contains(deviceScanResult)) {
                            return;
                        }
                        LogcatStorageHelper.addLog("[DeviceScanManager] scan ble device:" + deviceScanResult.toString());
                        this.resultList.add(0, deviceScanResult);
                        return;
                    }
                    return;
                }
                DeviceScanResult deviceScanResult2 = new DeviceScanResult(bluetoothDevice, deviceInfo, DeviceScanResult.ScanResultType.DEVICE_DATA);
                EventBus.getDefault().post(deviceScanResult2);
                if (this.resultList.contains(deviceScanResult2)) {
                    return;
                }
                if ((deviceInfo.getType() == 25 && !"Petkit_A_D4SH".equals(deviceInfo.getName())) || (deviceInfo.getType() == 26 && !"Petkit_A_D4H".equals(deviceInfo.getName()))) {
                    LogcatStorageHelper.addLog("[DeviceScanManager] scan ble device:" + deviceScanResult2.toString());
                    return;
                }
                LogcatStorageHelper.addLog("[DeviceScanManager] scan ble device:" + deviceScanResult2.toString());
                Log.d("DeviceScanManager", "[DeviceScanManager] scan ble device:" + deviceScanResult2.toString());
                this.resultList.add(0, deviceScanResult2);
                return;
            }
            if (deviceInfo.getDeviceType() == 14 || deviceInfo.getDeviceType() == 11 || deviceInfo.getDeviceType() == 21) {
                if (this.typeCodeMap.containsKey(Integer.valueOf(deviceInfo.getDeviceType())) && this.typeCodeMap.get(Integer.valueOf(deviceInfo.getDeviceType())) != null && this.typeCodeMap.get(Integer.valueOf(deviceInfo.getDeviceType())).contains(Integer.valueOf(deviceInfo.getTypeCode()))) {
                    DeviceScanResult deviceScanResult3 = new DeviceScanResult(bluetoothDevice, deviceInfo, DeviceScanResult.ScanResultType.DEVICE_DATA);
                    EventBus.getDefault().post(deviceScanResult3);
                    if (this.resultList.contains(deviceScanResult3)) {
                        return;
                    }
                    LogcatStorageHelper.addLog("[DeviceScanManager] scan ble device:" + deviceScanResult3.toString());
                    this.resultList.add(0, deviceScanResult3);
                    return;
                }
                return;
            }
            DeviceScanResult deviceScanResult4 = new DeviceScanResult(bluetoothDevice, deviceInfo, DeviceScanResult.ScanResultType.DEVICE_DATA);
            EventBus.getDefault().post(deviceScanResult4);
            if (this.resultList.contains(deviceScanResult4)) {
                return;
            }
            if ((deviceInfo.getType() == 25 && !"Petkit_A_D4SH".equals(deviceInfo.getName())) || (deviceInfo.getType() == 26 && !"Petkit_A_D4H".equals(deviceInfo.getName()))) {
                LogcatStorageHelper.addLog("[DeviceScanManager] scan ble device:" + deviceScanResult4.toString());
                return;
            }
            LogcatStorageHelper.addLog("[DeviceScanManager] scan ble device:" + deviceScanResult4.toString());
            this.resultList.add(0, deviceScanResult4);
        }
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onError(int i) {
        Log.e("DeviceScanManager", "ble onError ：" + i);
        LogcatStorageHelper.addLog("DeviceScanManager ble onError ：" + i);
    }

    public static class SingletonHolder {
        public static final DeviceScanManager INSTANCE = new DeviceScanManager();
    }

    public DeviceScanManager() {
        this.Tag = "DeviceScanManager";
        this.TIMER_INTERVAL = 1000;
        this.scanTime = 0;
        this.deviceTypeList = new ArrayList();
        this.wifiDeviceType = new ArrayList();
        this.resultList = new ArrayList();
        this.isRegister = false;
        this.typeCodeMap = new HashMap();
        this.receiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.device.utils.DeviceScanManager.3
            public AnonymousClass3() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                    DeviceScanManager.this.getWifiInfo();
                }
            }
        };
    }

    public static DeviceScanManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void init() {
        EventBus.getDefault().register(this);
        PetkitLog.d("DeviceScanManager", "init：" + toString());
        this.wifiManager = (WifiManager) CommonUtils.getAppContext().getApplicationContext().getSystemService("wifi");
        PetkitBLEManager petkitBLEManager = PetkitBLEManager.getInstance();
        this.bleManager = petkitBLEManager;
        petkitBLEManager.setBleListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        CommonUtils.getAppContext().registerReceiver(this.receiver, intentFilter);
        this.isRegister = true;
    }

    public void startScan(int i, DeviceType... deviceTypeArr) {
        PetkitBLEManager petkitBLEManager;
        if (this.bleManager != null) {
            PetkitLog.d("DeviceScanManager", "startScan：return");
            return;
        }
        PetkitLog.d("DeviceScanManager", "startScan：scanTime:" + i);
        StringBuilder sb = new StringBuilder();
        for (DeviceType deviceType : deviceTypeArr) {
            sb.append(deviceType);
            sb.append("/");
        }
        LogcatStorageHelper.addLog("[DeviceScanManager] start scan device:" + sb.toString());
        PetkitLog.d("[DeviceScanManager] start scan device:" + sb.toString());
        this.scanTime = i;
        this.resultList.clear();
        init();
        this.scanDeviceType = deviceTypeArr;
        initScanType();
        if ((this.deviceTypeList.contains(1) || this.deviceTypeList.contains(2) || this.deviceTypeList.contains(3) || this.deviceTypeList.contains(4) || this.deviceTypeList.contains(8) || this.deviceTypeList.contains(7) || this.deviceTypeList.contains(10) || this.deviceTypeList.contains(9) || this.deviceTypeList.contains(11) || this.deviceTypeList.contains(12) || this.deviceTypeList.contains(14) || this.deviceTypeList.contains(15) || this.deviceTypeList.contains(16) || this.deviceTypeList.contains(17) || this.deviceTypeList.contains(18) || this.deviceTypeList.contains(19) || this.deviceTypeList.contains(20) || this.deviceTypeList.contains(22) || this.deviceTypeList.contains(24) || this.deviceTypeList.contains(25) || this.deviceTypeList.contains(26) || this.deviceTypeList.contains(27) || this.deviceTypeList.contains(21) || this.deviceTypeList.contains(28) || this.deviceTypeList.contains(29)) && (petkitBLEManager = this.bleManager) != null) {
            petkitBLEManager.setBleScanTime(i);
            this.bleManager.startScan(new ScanFilter[0]);
        }
        if (this.wifiDeviceType.contains(Consts.WIFI_SSID_D1_HEADER) || this.wifiDeviceType.contains(Consts.WIFI_SSID_D2_HEADER) || this.wifiDeviceType.contains(Consts.WIFI_SSID_COZY_HEADER)) {
            pollScanWifi();
        }
        if (i >= 0) {
            AnonymousClass1 anonymousClass1 = new CountDownTimer(i, 1000L) { // from class: com.petkit.android.activities.device.utils.DeviceScanManager.1
                public final /* synthetic */ int val$scanTime;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(int i2, long j, int i22) {
                    super(i22, j);
                    i = i22;
                }

                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    EventBus.getDefault().post(new DeviceScanResult(DeviceScanResult.ScanResultType.SCAN_TIME, (int) (j / 1000)));
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    if (DeviceScanManager.this.bleManager != null) {
                        DeviceScanManager.this.bleManager.stopScan();
                    }
                    EventBus.getDefault().post(new DeviceScanResult((ScanResult) null, DeviceScanResult.ScanResultType.SCAN_FINISH));
                    LogcatStorageHelper.addLog("[DeviceScanManager] scan device finish,scanTime:" + i);
                    DeviceScanManager.this.onDestroy();
                }
            };
            this.countDownTimer = anonymousClass1;
            anonymousClass1.start();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceScanManager$1 */
    public class AnonymousClass1 extends CountDownTimer {
        public final /* synthetic */ int val$scanTime;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i22, long j, int i222) {
            super(i222, j);
            i = i222;
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            EventBus.getDefault().post(new DeviceScanResult(DeviceScanResult.ScanResultType.SCAN_TIME, (int) (j / 1000)));
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (DeviceScanManager.this.bleManager != null) {
                DeviceScanManager.this.bleManager.stopScan();
            }
            EventBus.getDefault().post(new DeviceScanResult((ScanResult) null, DeviceScanResult.ScanResultType.SCAN_FINISH));
            LogcatStorageHelper.addLog("[DeviceScanManager] scan device finish,scanTime:" + i);
            DeviceScanManager.this.onDestroy();
        }
    }

    public final void initScanType() {
        this.wifiDeviceType.clear();
        this.deviceTypeList.clear();
        this.typeCodeMap.clear();
        for (DeviceType deviceType : this.scanDeviceType) {
            switch (AnonymousClass4.$SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[deviceType.ordinal()]) {
                case 1:
                    this.deviceTypeList.add(29);
                    continue;
                    break;
                case 2:
                    addTypeCode(21, 1);
                    continue;
                    break;
                case 3:
                    this.deviceTypeList.add(27);
                    continue;
                    break;
                case 4:
                    this.deviceTypeList.add(28);
                    continue;
                    break;
                case 5:
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D1_HEADER);
                    continue;
                    break;
                case 6:
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D2_HEADER);
                    continue;
                    break;
                case 7:
                    this.wifiDeviceType.add(Consts.WIFI_SSID_COZY_HEADER);
                    continue;
                    break;
                case 8:
                    addTypeCode(25, 0);
                    continue;
                    break;
                case 9:
                    addTypeCode(26, 0);
                    continue;
                    break;
                case 10:
                    addTypeCode(25, 1);
                    break;
                case 11:
                    addTypeCode(26, 1);
                    break;
                case 12:
                    addTypeCode(25, 2);
                    break;
                case 13:
                    addTypeCode(26, 2);
                    break;
                case 14:
                    this.deviceTypeList.add(20);
                    break;
                case 15:
                    this.deviceTypeList.add(10);
                    break;
                case 16:
                    this.deviceTypeList.add(8);
                    break;
                case 17:
                    this.deviceTypeList.add(7);
                    break;
                case 18:
                    this.deviceTypeList.add(9);
                    break;
                case 19:
                    addTypeCode(11, 1);
                    break;
                case 20:
                    addTypeCode(11, 2);
                    break;
                case 21:
                    addTypeCode(11, 3);
                    break;
                case 22:
                    this.deviceTypeList.add(12);
                    break;
                case 23:
                    addTypeCode(14, 1);
                    break;
                case 24:
                    addTypeCode(14, 2);
                    break;
                case 25:
                    addTypeCode(14, 3);
                    break;
                case 26:
                    addTypeCode(14, 4);
                    break;
                case 27:
                    addTypeCode(14, 5);
                    break;
                case 28:
                    addTypeCode(14, 6);
                    break;
                case 29:
                    this.deviceTypeList.add(17);
                    break;
                case 30:
                    addTypeCode(15, 1);
                    break;
                case 31:
                    addTypeCode(15, 2);
                    break;
                case 32:
                    addTypeCode(21, 2);
                    break;
                case 33:
                    this.deviceTypeList.add(16);
                    break;
                case 34:
                    this.deviceTypeList.add(18);
                    break;
                case 35:
                    this.deviceTypeList.add(22);
                    break;
                case 36:
                    addTypeCode(24, 3);
                    break;
                case 37:
                    addTypeCode(24, 2);
                    break;
                case 38:
                    addTypeCode(24, 3);
                    break;
                case 39:
                    addTypeCode(24, 1);
                    break;
                case 40:
                    addTypeCode(24, 4);
                    break;
                case 41:
                    this.deviceTypeList.add(19);
                    break;
                case 42:
                    this.deviceTypeList.add(1);
                    this.deviceTypeList.add(2);
                    break;
                case 43:
                    this.deviceTypeList.add(4);
                    break;
                case 44:
                    this.deviceTypeList.add(3);
                    break;
                default:
                    this.deviceTypeList.add(7);
                    this.deviceTypeList.add(10);
                    this.deviceTypeList.add(8);
                    this.deviceTypeList.add(9);
                    this.deviceTypeList.add(11);
                    this.deviceTypeList.add(12);
                    this.deviceTypeList.add(14);
                    this.deviceTypeList.add(15);
                    this.deviceTypeList.add(16);
                    this.deviceTypeList.add(17);
                    this.deviceTypeList.add(18);
                    this.deviceTypeList.add(19);
                    this.deviceTypeList.add(20);
                    this.deviceTypeList.add(22);
                    this.deviceTypeList.add(26);
                    this.deviceTypeList.add(25);
                    this.deviceTypeList.add(28);
                    this.deviceTypeList.add(29);
                    this.deviceTypeList.add(27);
                    this.deviceTypeList.add(21);
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D1_HEADER);
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D2_HEADER);
                    this.wifiDeviceType.add(Consts.WIFI_SSID_COZY_HEADER);
                    addTypeCode(24, 1);
                    addTypeCode(24, 2);
                    addTypeCode(24, 3);
                    addTypeCode(24, 4);
                    addTypeCode(14, 1);
                    addTypeCode(14, 2);
                    addTypeCode(14, 3);
                    addTypeCode(14, 4);
                    addTypeCode(14, 5);
                    addTypeCode(14, 6);
                    addTypeCode(21, 1);
                    addTypeCode(21, 2);
                    addTypeCode(11, 1);
                    addTypeCode(11, 2);
                    addTypeCode(11, 3);
                    addTypeCode(15, 1);
                    addTypeCode(15, 2);
                    addTypeCode(25, 0);
                    addTypeCode(25, 1);
                    addTypeCode(25, 2);
                    addTypeCode(26, 0);
                    addTypeCode(26, 1);
                    addTypeCode(26, 2);
                    break;
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceScanManager$4 */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType;

        static {
            int[] iArr = new int[DeviceType.values().length];
            $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType = iArr;
            try {
                iArr[DeviceType.W7H.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.T5.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.T6.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.T7.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D1.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D2.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.COZY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4SH.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4H.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4SH_2.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4H_2.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4SH_3.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4H_3.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4S.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.AQ.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.K2.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.T3.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D3.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4_1.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.D4_2.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.P3.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.W5.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.W5C.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.W5N.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.W4X.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.CTW2.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.W4XUVC.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.AQR.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.T4.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.T4_2.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.T5_2.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.K3.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.R2.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.HG.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.CTW3.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.CTW3_100.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.CTW3_UV.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.CTW3_2.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.CTW3_UV_100.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.AQH1.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.FIT.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.GO.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.MATE.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$com$petkit$android$activities$device$utils$DeviceScanManager$DeviceType[DeviceType.ALL.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
        }
    }

    public final void addTypeCode(int i, int i2) {
        if (!this.typeCodeMap.containsKey(Integer.valueOf(i))) {
            this.typeCodeMap.put(Integer.valueOf(i), new ArrayList());
        }
        if (this.typeCodeMap.get(Integer.valueOf(i)) != null) {
            this.typeCodeMap.get(Integer.valueOf(i)).add(Integer.valueOf(i2));
        }
        if (this.deviceTypeList.contains(Integer.valueOf(i))) {
            return;
        }
        this.deviceTypeList.add(Integer.valueOf(i));
    }

    public final void pollScanWifi() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        Observable.interval(0L, 30000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.device.utils.DeviceScanManager.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            public AnonymousClass2() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable2) {
                DeviceScanManager.this.disposable = disposable2;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                DeviceScanManager.this.wifiManager.startScan();
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceScanManager$2 */
    public class AnonymousClass2 implements Observer<Long> {
        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public AnonymousClass2() {
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable2) {
            DeviceScanManager.this.disposable = disposable2;
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            DeviceScanManager.this.wifiManager.startScan();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.device.utils.DeviceScanManager$3 */
    public class AnonymousClass3 extends BroadcastReceiver {
        public AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                DeviceScanManager.this.getWifiInfo();
            }
        }
    }

    public final boolean checkWifiDevice(ScanResult scanResult) {
        Iterator<String> it = this.wifiDeviceType.iterator();
        while (it.hasNext()) {
            if (scanResult.SSID.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public final void getWifiInfo() {
        List<ScanResult> scanResults = this.wifiManager.getScanResults();
        this.scanResults = scanResults;
        Iterator<ScanResult> it = scanResults.iterator();
        String str = "";
        while (it.hasNext()) {
            str = str + it.next().SSID + "/";
        }
        PetkitLog.d("getWifiInfo", str);
        List<ScanResult> list = this.scanResults;
        if (list != null) {
            for (ScanResult scanResult : list) {
                if (checkWifiDevice(scanResult)) {
                    DeviceScanResult deviceScanResult = new DeviceScanResult(scanResult, DeviceScanResult.ScanResultType.DEVICE_DATA);
                    LogcatStorageHelper.addLog("[DeviceScanManager] scan wifi device:" + deviceScanResult.toString());
                    EventBus.getDefault().post(deviceScanResult);
                    if (!this.resultList.contains(deviceScanResult)) {
                        this.resultList.add(0, deviceScanResult);
                    }
                }
            }
        }
    }

    @Subscriber
    public void stopSelf(StopDeviceScanMsg stopDeviceScanMsg) {
        onDestroy();
    }

    public void onDestroy() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        PetkitBLEManager petkitBLEManager = this.bleManager;
        if (petkitBLEManager != null) {
            petkitBLEManager.stopScan();
            this.bleManager = null;
        }
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.countDownTimer = null;
        }
        EventBus.getDefault().unregister(this);
        if (this.isRegister) {
            CommonUtils.getAppContext().unregisterReceiver(this.receiver);
            this.isRegister = false;
        }
        PetkitLog.d("DeviceScanManager", "onDestroy");
    }
}

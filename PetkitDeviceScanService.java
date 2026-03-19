package com.petkit.android.ble.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.jess.arms.utils.Consts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.ble.DeviceScanResult;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.ble.PetkitBLEManager;
import com.petkit.android.ble.StopDeviceScanMsg;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LogcatStorageHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes6.dex */
public class PetkitDeviceScanService extends Service implements PetkitBLEManager.onPetkitBleListener {
    public PetkitBLEManager bleManager;
    public CountDownTimer countDownTimer;
    public List<Integer> deviceTypeList;
    public Disposable disposable;
    public DeviceType[] scanDeviceType;
    public List<ScanResult> scanResults;
    public List<String> wifiDeviceType;
    public WifiManager wifiManager;
    public final String Tag = "PetkitDeviceScanService";
    public final int TIMER_INTERVAL = 1000;
    public int scanTime = 0;
    public BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.petkit.android.ble.service.PetkitDeviceScanService.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                PetkitDeviceScanService.this.getWifiInfo();
            }
        }
    };

    public enum DeviceType {
        FIT,
        MATE,
        GO,
        T3,
        K2,
        D1,
        D2,
        COZY,
        ALL
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onReceiveCustomData(int i, String str) {
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onStateChanged(PetkitBLEConsts.ConnectState connectState) {
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        Log.d("PetkitDeviceScanService", "onCreate");
        this.deviceTypeList = new ArrayList();
        this.wifiDeviceType = new ArrayList();
        this.wifiManager = (WifiManager) getApplication().getApplicationContext().getSystemService("wifi");
        PetkitBLEManager petkitBLEManager = PetkitBLEManager.getInstance();
        this.bleManager = petkitBLEManager;
        petkitBLEManager.setBleListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        if (Build.VERSION.SDK_INT >= 34) {
            registerReceiver(this.receiver, intentFilter, 4);
        } else {
            registerReceiver(this.receiver, intentFilter);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        PetkitBLEManager petkitBLEManager;
        Log.d("PetkitDeviceScanService", "onStartCommand");
        this.scanTime = intent.getIntExtra(Constants.EXTRA_BLE_SCAN_TIME, 0);
        this.scanDeviceType = (DeviceType[]) intent.getSerializableExtra(Constants.EXTRA_BLE_SCAN_DEVICE_TYPE);
        initScanType();
        if ((this.deviceTypeList.contains(1) || this.deviceTypeList.contains(2) || this.deviceTypeList.contains(3) || this.deviceTypeList.contains(4) || this.deviceTypeList.contains(8) || this.deviceTypeList.contains(7)) && (petkitBLEManager = this.bleManager) != null) {
            petkitBLEManager.setBleScanTime(this.scanTime);
            this.bleManager.startScan(new ScanFilter[0]);
        }
        if (this.wifiDeviceType.contains(Consts.WIFI_SSID_D1_HEADER) || this.wifiDeviceType.contains(Consts.WIFI_SSID_D2_HEADER) || this.wifiDeviceType.contains(Consts.WIFI_SSID_COZY_HEADER)) {
            pollScanWifi();
        }
        if (this.scanTime >= 0) {
            CountDownTimer countDownTimer = new CountDownTimer(this.scanTime, 1000L) { // from class: com.petkit.android.ble.service.PetkitDeviceScanService.1
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    EventBus.getDefault().post(new DeviceScanResult(DeviceScanResult.ScanResultType.SCAN_TIME, (int) (j / 1000)));
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    if (PetkitDeviceScanService.this.bleManager != null) {
                        PetkitDeviceScanService.this.bleManager.stopScan();
                    }
                    EventBus.getDefault().post(new DeviceScanResult((ScanResult) null, DeviceScanResult.ScanResultType.SCAN_FINISH));
                    PetkitDeviceScanService.this.stopSelf();
                }
            };
            this.countDownTimer = countDownTimer;
            countDownTimer.start();
        }
        return super.onStartCommand(intent, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getWifiInfo() {
        List<ScanResult> scanResults = this.wifiManager.getScanResults();
        this.scanResults = scanResults;
        if (scanResults != null) {
            for (ScanResult scanResult : scanResults) {
                if (checkWifiDevice(scanResult)) {
                    EventBus.getDefault().post(new DeviceScanResult(scanResult, DeviceScanResult.ScanResultType.DEVICE_DATA));
                }
            }
        }
    }

    private boolean checkWifiDevice(ScanResult scanResult) {
        Iterator<String> it = this.wifiDeviceType.iterator();
        while (it.hasNext()) {
            if (scanResult.SSID.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    private void initScanType() {
        this.wifiDeviceType.clear();
        this.deviceTypeList.clear();
        for (DeviceType deviceType : this.scanDeviceType) {
            switch (AnonymousClass4.$SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[deviceType.ordinal()]) {
                case 1:
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D1_HEADER);
                    break;
                case 2:
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D2_HEADER);
                    break;
                case 3:
                    this.wifiDeviceType.add(Consts.WIFI_SSID_COZY_HEADER);
                    break;
                case 4:
                    this.deviceTypeList.add(8);
                    break;
                case 5:
                    this.deviceTypeList.add(7);
                    break;
                case 6:
                    this.deviceTypeList.add(1);
                    this.deviceTypeList.add(2);
                    break;
                case 7:
                    this.deviceTypeList.add(4);
                    break;
                case 8:
                    this.deviceTypeList.add(3);
                    break;
                default:
                    this.deviceTypeList.add(1);
                    this.deviceTypeList.add(2);
                    this.deviceTypeList.add(3);
                    this.deviceTypeList.add(4);
                    this.deviceTypeList.add(7);
                    this.deviceTypeList.add(7);
                    this.deviceTypeList.add(8);
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D1_HEADER);
                    this.wifiDeviceType.add(Consts.WIFI_SSID_D2_HEADER);
                    this.wifiDeviceType.add(Consts.WIFI_SSID_COZY_HEADER);
                    break;
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.ble.service.PetkitDeviceScanService$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType;

        static {
            int[] iArr = new int[DeviceType.values().length];
            $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType = iArr;
            try {
                iArr[DeviceType.D1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.D2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.COZY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.K2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.T3.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.FIT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.GO.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.MATE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$petkit$android$ble$service$PetkitDeviceScanService$DeviceType[DeviceType.ALL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private void pollScanWifi() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        Observable.interval(0L, 30000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.ble.service.PetkitDeviceScanService.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable2) {
                PetkitDeviceScanService.this.disposable = disposable2;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                PetkitDeviceScanService.this.wifiManager.startScan();
            }
        });
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onLeScan(BluetoothDevice bluetoothDevice, DeviceInfo deviceInfo) {
        Log.d("BluetoothDevice", deviceInfo.toString());
        if (this.deviceTypeList.contains(Integer.valueOf(deviceInfo.getType()))) {
            EventBus.getDefault().post(new DeviceScanResult(bluetoothDevice, deviceInfo, DeviceScanResult.ScanResultType.DEVICE_DATA));
        }
    }

    @Override // com.petkit.android.ble.PetkitBLEManager.onPetkitBleListener
    public void onError(int i) {
        Log.e("PetkitDeviceScanService", "ble onError ：" + i);
        LogcatStorageHelper.addLog("PetkitDeviceScanService ble onError ：" + i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Intent newIntent(Context context, int i, DeviceType... deviceTypeArr) {
        Intent intent = new Intent(context, (Class<?>) PetkitDeviceScanService.class);
        intent.putExtra(Constants.EXTRA_BLE_SCAN_TIME, i);
        intent.putExtra(Constants.EXTRA_BLE_SCAN_DEVICE_TYPE, (Serializable) deviceTypeArr);
        return intent;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
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
        unregisterReceiver(this.receiver);
        Log.d("PetkitDeviceScanService", "onDestroy");
    }

    @Subscriber
    public void stopSelf(StopDeviceScanMsg stopDeviceScanMsg) {
        stopSelf();
    }
}

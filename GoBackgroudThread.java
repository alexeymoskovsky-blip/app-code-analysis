package com.petkit.android.activities.go.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.github.sunnysuperman.commons.utils.FileUtil;
import com.orm.query.Condition;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.go.model.GoDayData;
import com.petkit.android.activities.go.model.GoMarker;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.go.utils.GoDateUtils;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import cz.msebera.android.httpclient.util.ByteArrayBuffer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.threeten.bp.Ser;

/* JADX INFO: loaded from: classes3.dex */
@TargetApi(18)
public class GoBackgroudThread extends Thread {
    public static final String[] DeviceFilter = {BLEConsts.GO_DISPLAY_NAME};
    public static final int GO_BACKGROUND_GAP_TIMER_SCAN = 120000;
    public BluetoothAdapter mBluetoothAdapter;
    public int mConnectionState;
    public Context mContext;
    public int mError;
    public HashMap<BluetoothGatt, GoAliveThread> mGoAliveThreadHashMap;
    public boolean mNotificationsEnabled;
    public long mOtaDeviceId;
    public boolean mPaused;
    public boolean mServiceChangedIndicationsEnabled;
    public final Object mLock = new Object();
    public boolean mAborted = false;

    @SuppressLint({"NewApi"})
    public final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() { // from class: com.petkit.android.activities.go.service.GoBackgroudThread.2
        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            if (i != 0) {
                GoBackgroudThread.this.addLog("Connection state change error: " + i + " newState: " + i2);
                GoBackgroudThread.this.mPaused = false;
                GoBackgroudThread.this.mConnectionState = 0;
                if (GoBackgroudThread.this.mGoAliveThreadHashMap.get(bluetoothGatt) != null) {
                    GoAliveThread goAliveThread = (GoAliveThread) GoBackgroudThread.this.mGoAliveThreadHashMap.get(bluetoothGatt);
                    goAliveThread.setAbort(true);
                    GoBackgroudThread.this.updateGoConnectState(goAliveThread.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 0);
                }
            } else if (i2 == 2) {
                GoBackgroudThread.this.addLog("Connected to GATT server");
                GoBackgroudThread.this.mConnectionState = -2;
                if (bluetoothGatt.getDevice().getBondState() == 12) {
                    try {
                        synchronized (this) {
                            wait(1600L);
                        }
                    } catch (InterruptedException unused) {
                    }
                }
                boolean zDiscoverServices = bluetoothGatt.discoverServices();
                GoBackgroudThread goBackgroudThread = GoBackgroudThread.this;
                StringBuilder sb = new StringBuilder();
                sb.append("Attempting to start service discovery... ");
                sb.append(zDiscoverServices ? "succeed" : "failed");
                goBackgroudThread.addLog(sb.toString());
                if (zDiscoverServices) {
                    return;
                } else {
                    GoBackgroudThread.this.mError = BLEConsts.ERROR_SERVICE_DISCOVERY_NOT_STARTED;
                }
            } else if (i2 == 0) {
                GoBackgroudThread.this.addLog("Disconnected from GATT server");
                GoBackgroudThread.this.mPaused = false;
                GoBackgroudThread.this.mConnectionState = 0;
                if (GoBackgroudThread.this.mGoAliveThreadHashMap.get(bluetoothGatt) != null) {
                    GoAliveThread goAliveThread2 = (GoAliveThread) GoBackgroudThread.this.mGoAliveThreadHashMap.get(bluetoothGatt);
                    goAliveThread2.setAbort(true);
                    GoBackgroudThread.this.updateGoConnectState(goAliveThread2.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 0);
                }
            }
            synchronized (GoBackgroudThread.this.mLock) {
                GoBackgroudThread.this.mLock.notifyAll();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            if (i == 0) {
                GoBackgroudThread.this.addLog("Services discovered");
                GoBackgroudThread.this.mConnectionState = -3;
            } else {
                GoBackgroudThread.this.addLog("Service discovery error: " + i);
                GoBackgroudThread.this.mError = i | 16384;
            }
            synchronized (GoBackgroudThread.this.mLock) {
                GoBackgroudThread.this.mLock.notifyAll();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            GoBackgroudThread.this.addLog("onDescriptorWrite ...");
            if (i == 0) {
                GoBackgroudThread.this.addLog("onDescriptorWrite ..." + i);
                if (BLEConsts.CLIENT_CHARACTERISTIC_CONFIG.equals(bluetoothGattDescriptor.getUuid())) {
                    if (BLEConsts.SERVICE_CHANGED_UUID.equals(bluetoothGattDescriptor.getCharacteristic().getUuid())) {
                        GoBackgroudThread.this.mServiceChangedIndicationsEnabled = bluetoothGattDescriptor.getValue()[0] == 2;
                        GoBackgroudThread.this.addLog("onDescriptorWrite mServiceChangedIndicationsEnabled." + GoBackgroudThread.this.mServiceChangedIndicationsEnabled);
                    } else {
                        GoBackgroudThread.this.mNotificationsEnabled = bluetoothGattDescriptor.getValue()[0] == 1;
                        GoBackgroudThread.this.addLog("onDescriptorWrite mNotificationsEnabled." + GoBackgroudThread.this.mNotificationsEnabled);
                    }
                }
            } else {
                GoBackgroudThread.this.addLog("Descriptor write error: " + i);
                GoBackgroudThread.this.mError = i | 16384;
            }
            synchronized (GoBackgroudThread.this.mLock) {
                GoBackgroudThread.this.mLock.notifyAll();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i != 0) {
                GoBackgroudThread.this.addLog("Characteristic write error: " + i);
                GoBackgroudThread.this.mError = i | 16384;
            }
            synchronized (GoBackgroudThread.this.mLock) {
                GoBackgroudThread.this.mLock.notifyAll();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 0) {
                GoBackgroudThread.this.addLog("Read Response received from " + bluetoothGattCharacteristic.getUuid() + ", value (0x): " + GoBackgroudThread.this.parse(bluetoothGattCharacteristic.getValue()));
            } else {
                GoBackgroudThread.this.addLog("Characteristic read error: " + i);
                GoBackgroudThread.this.mError = i | 16384;
            }
            synchronized (GoBackgroudThread.this.mLock) {
                GoBackgroudThread.this.mLock.notifyAll();
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (value != null && value[0] != -48) {
                GoBackgroudThread.this.addLog("onCharacteristicChanged: " + GoBackgroudThread.this.parse(value));
            }
            GoAliveThread goAliveThread = (GoAliveThread) GoBackgroudThread.this.mGoAliveThreadHashMap.get(bluetoothGatt);
            if (goAliveThread != null) {
                goAliveThread.onDataReceived(value);
            }
        }
    };
    public List<DeviceInfo> mDeviceInfoList = new ArrayList();
    public Timer mTimer = null;
    public boolean timeOut = false;

    public GoBackgroudThread(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:(2:96|9)(2:10|(9:12|(4:15|(3:104|17|108)(1:107)|106|13)|105|18|(9:20|(1:42)(4:24|87|25|(1:37)(7:30|44|(1:46)(1:47)|48|(1:50)(1:51)|52|(4:91|54|101|100)(1:99)))|43|44|(0)(0)|48|(0)(0)|52|(0)(0))(1:98)|55|(4:58|(3:110|60|113)(1:112)|111|56)|109|61)(2:97|62))|63|85|64|1c0|76|102|100) */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01da, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01db, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01e0, code lost:
    
        removeAllAliveGo();
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(r18.mContext).sendBroadcast(new android.content.Intent(com.petkit.android.activities.go.utils.GoDataUtils.BROADCAST_GO_BACKGROUND_THREAD_CANCELED));
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01f3, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x017f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0188 A[SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.go.service.GoBackgroudThread.run():void");
    }

    public void setScanAborted(boolean z) {
        this.mAborted = z;
        addLog("set scan abort, stop");
        synchronized (this.mLock) {
            this.mLock.notifyAll();
        }
    }

    public void setOTAState(long j, boolean z) {
        if (z) {
            this.mOtaDeviceId = j;
            removeGo(j);
        } else {
            this.mOtaDeviceId = 0L;
            enableScan();
        }
    }

    public final boolean checkIfNeedToScan() {
        List<GoRecord> goRecordList = GoDataUtils.getGoRecordList();
        if (goRecordList == null || goRecordList.size() <= 0) {
            return false;
        }
        boolean z = false;
        for (GoRecord goRecord : goRecordList) {
            Iterator<Map.Entry<BluetoothGatt, GoAliveThread>> it = this.mGoAliveThreadHashMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    if (goRecord.getConstate() == 2) {
                        updateGoConnectState(goRecord.getDeviceId(), 0);
                    }
                    z = true;
                } else if (it.next().getValue().mGoAliveDeviceInfo.deviceInfo.getDeviceId() == goRecord.getDeviceId()) {
                    if (goRecord.getConstate() != 2) {
                        updateGoConnectState(goRecord.getDeviceId(), 2);
                    }
                }
            }
        }
        return z;
    }

    public void enableScan() {
        int i = this.mConnectionState;
        if (i == -5 || i == 0) {
            this.timeOut = true;
            synchronized (this.mLock) {
                this.mLock.notifyAll();
            }
        }
    }

    public void writeData(long j, byte[] bArr) {
        HashMap<BluetoothGatt, GoAliveThread> map = this.mGoAliveThreadHashMap;
        if (map == null || map.size() <= 0) {
            return;
        }
        Iterator<Map.Entry<BluetoothGatt, GoAliveThread>> it = this.mGoAliveThreadHashMap.entrySet().iterator();
        while (it.hasNext()) {
            GoAliveThread value = it.next().getValue();
            if (value.mGoAliveDeviceInfo.deviceInfo.getDeviceId() == j) {
                value.writeData(bArr);
                return;
            } else if (j == 0) {
                value.writeData(bArr);
            }
        }
    }

    public void removeGo(long j) {
        HashMap<BluetoothGatt, GoAliveThread> map = this.mGoAliveThreadHashMap;
        if (map == null || map.size() <= 0) {
            return;
        }
        Iterator<Map.Entry<BluetoothGatt, GoAliveThread>> it = this.mGoAliveThreadHashMap.entrySet().iterator();
        while (it.hasNext()) {
            GoAliveThread value = it.next().getValue();
            if (value.mGoAliveDeviceInfo.deviceInfo.getDeviceId() == j) {
                value.setAbort(true);
                return;
            }
        }
    }

    public void removeAllAliveGo() {
        addLog("remove all alive go");
        HashMap<BluetoothGatt, GoAliveThread> map = this.mGoAliveThreadHashMap;
        if (map == null || map.size() <= 0) {
            return;
        }
        Iterator<Map.Entry<BluetoothGatt, GoAliveThread>> it = this.mGoAliveThreadHashMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().setAbort(true);
        }
    }

    public void enableCall(boolean z) {
        addLog("enableCall " + z);
        HashMap<BluetoothGatt, GoAliveThread> map = this.mGoAliveThreadHashMap;
        if (map == null || map.size() <= 0) {
            return;
        }
        byte[] bArrBuildOpCodeBuffer = GoDataUtils.buildOpCodeBuffer(-50, z ? 1 : 0);
        Iterator<Map.Entry<BluetoothGatt, GoAliveThread>> it = this.mGoAliveThreadHashMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().writeData(bArrBuildOpCodeBuffer);
        }
    }

    public final synchronized void writeSyncCode(GoAliveDeviceInfo goAliveDeviceInfo, byte[] bArr) {
        goAliveDeviceInfo.controlCharacteristic.setValue(bArr);
        goAliveDeviceInfo.gatt.writeCharacteristic(goAliveDeviceInfo.controlCharacteristic);
    }

    public final synchronized void cancelAliveThread(GoAliveDeviceInfo goAliveDeviceInfo) {
        if (this.mGoAliveThreadHashMap.get(goAliveDeviceInfo.gatt) != null) {
            this.mGoAliveThreadHashMap.remove(goAliveDeviceInfo.gatt);
            updateGoConnectState(goAliveDeviceInfo.deviceInfo.getDeviceId(), 0);
            disconnect(goAliveDeviceInfo);
        }
    }

    public final void startScan() {
        this.mDeviceInfoList.clear();
        BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() { // from class: com.petkit.android.activities.go.service.GoBackgroudThread.1
            @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
            public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                GoBackgroudThread.this.processScanResult(bluetoothDevice, i, bArr);
            }
        };
        this.mBluetoothAdapter.startLeScan(leScanCallback);
        this.mConnectionState = -8;
        this.mError = 0;
        try {
            sendStepStateBroadcast(-1, 0L, "");
            waitUntilTimeOut(GoDataUtils.SCAN_DURATION);
            synchronized (this.mLock) {
                while (true) {
                    try {
                        if (this.mConnectionState != -8 || this.timeOut || this.mError != 0 || this.mAborted) {
                            if (!this.mPaused) {
                                break;
                            }
                        }
                        this.mLock.wait();
                    } finally {
                    }
                }
            }
        } catch (InterruptedException unused) {
            addLog("Sleeping interrupted");
        }
        cancelTimer();
        addLog("GoBackgroudThread stop scan");
        try {
            this.mBluetoothAdapter.stopLeScan(leScanCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void processScanResult(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        if (bluetoothDevice == null || deviceInfoExists(bluetoothDevice.getAddress())) {
            return;
        }
        DeviceInfo deviceInfoCreateDeviceInfo = createDeviceInfo(bluetoothDevice, i, bArr);
        if (checkDeviceFilter(deviceInfoCreateDeviceInfo.getName())) {
            addLog("find device: " + bluetoothDevice.toString());
            if (!GoDataUtils.checkGoOwner(deviceInfoCreateDeviceInfo.getDeviceId())) {
                addLog("this go is not mine, ignore it");
                return;
            }
            if (this.mOtaDeviceId != deviceInfoCreateDeviceInfo.getDeviceId()) {
                addScanedDevice(deviceInfoCreateDeviceInfo);
                this.mConnectionState = -9;
                synchronized (this.mLock) {
                    this.mLock.notifyAll();
                }
                return;
            }
            addLog("this go is doing ota, ignore it");
        }
    }

    public final BluetoothGatt connect(String str) {
        this.mConnectionState = -1;
        addLog("Connecting to the device...");
        BluetoothGatt bluetoothGattConnectGatt = this.mBluetoothAdapter.getRemoteDevice(str).connectGatt(this.mContext, false, this.mGattCallback);
        try {
            synchronized (this.mLock) {
                while (true) {
                    try {
                        int i = this.mConnectionState;
                        if ((i != -1 && i != -2) || this.mError != 0 || this.mAborted) {
                            break;
                        }
                        this.mLock.wait();
                    } finally {
                    }
                }
            }
        } catch (InterruptedException e) {
            addLog("Sleeping interrupted " + e.toString());
        }
        return bluetoothGattConnectGatt;
    }

    public final void refreshDeviceCache(BluetoothGatt bluetoothGatt, boolean z) {
        if (z || !(bluetoothGatt == null || bluetoothGatt.getDevice() == null || bluetoothGatt.getDevice().getBondState() != 10)) {
            try {
                Method method = bluetoothGatt.getClass().getMethod("refresh", null);
                if (method != null) {
                    method.invoke(bluetoothGatt, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void disconnect(GoAliveDeviceInfo goAliveDeviceInfo) {
        addLog("Disconnecting from the device..." + goAliveDeviceInfo.deviceInfo.getDeviceId());
        goAliveDeviceInfo.gatt.setCharacteristicNotification(goAliveDeviceInfo.dataCharacteristic, false);
        goAliveDeviceInfo.gatt.disconnect();
        refreshDeviceCache(goAliveDeviceInfo.gatt, false);
        goAliveDeviceInfo.gatt.close();
    }

    public final int initialize() {
        if (CommonUtils.getAndroidSDKVersion() < 18) {
            return -1;
        }
        BluetoothManager bluetoothManager = (BluetoothManager) this.mContext.getSystemService("bluetooth");
        if (bluetoothManager == null) {
            addLog("Unable to initialize BluetoothManager.");
            return 1;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        this.mBluetoothAdapter = adapter;
        if (adapter == null) {
            addLog("Unable to obtain a BluetoothAdapter.");
            return 1;
        }
        if (!adapter.isEnabled()) {
            if (!BaseApplication.isAutoEnableBLE()) {
                return 1;
            }
            BaseApplication.setAutoEnableBLE(false);
            this.mBluetoothAdapter.enable();
            waitUntilTimeOut(3000L);
            synchronized (this.mLock) {
                while (!this.timeOut) {
                    try {
                        this.mLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0096, code lost:
    
        if (r5.mServiceChangedIndicationsEnabled == false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ae A[EDGE_INSN: B:61:0x00ae->B:38:0x00ae BREAK  A[LOOP:0: B:19:0x0088->B:37:0x00a8], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00a8 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void enableCCCD(android.bluetooth.BluetoothGatt r6, android.bluetooth.BluetoothGattCharacteristic r7, int r8) throws com.petkit.android.ble.exception.DeviceDisconnectedException, com.petkit.android.ble.exception.BLEAbortedException, com.petkit.android.ble.exception.BLEErrorException {
        /*
            Method dump skipped, instruction units count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.go.service.GoBackgroudThread.enableCCCD(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int):void");
    }

    public final boolean checkDeviceFilter(String str) {
        int length = DeviceFilter.length;
        if (str == null) {
            return false;
        }
        if (length <= 0) {
            return true;
        }
        boolean zEqualsIgnoreCase = false;
        for (int i = 0; i < length && !zEqualsIgnoreCase; i++) {
            zEqualsIgnoreCase = str.equalsIgnoreCase(DeviceFilter[i]);
        }
        return zEqualsIgnoreCase;
    }

    public final boolean deviceInfoExists(String str) {
        for (int i = 0; i < this.mDeviceInfoList.size(); i++) {
            if (this.mDeviceInfoList.get(i).getAddress().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final void addScanedDevice(DeviceInfo deviceInfo) {
        this.mDeviceInfoList.add(deviceInfo);
    }

    public final DeviceInfo createDeviceInfo(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        return new DeviceInfo(bluetoothDevice, i, bArr);
    }

    public final String getDeviceInfoIds() {
        List<DeviceInfo> list = this.mDeviceInfoList;
        if (list != null && list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.mDeviceInfoList.size(); i++) {
                sb.append(this.mDeviceInfoList.get(i).getDeviceId());
                sb.append(ChineseToPinyinResource.Field.COMMA);
            }
            return sb.toString();
        }
        return "";
    }

    public final void waitUntilTimeOut(long j) {
        if (this.mTimer == null) {
            this.mTimer = new Timer();
        }
        this.mTimer.schedule(new TimerTask() { // from class: com.petkit.android.activities.go.service.GoBackgroudThread.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                GoBackgroudThread.this.timeOut = true;
                synchronized (GoBackgroudThread.this.mLock) {
                    GoBackgroudThread.this.mLock.notifyAll();
                }
            }
        }, j);
        this.timeOut = false;
    }

    public final void cancelTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer = null;
        }
    }

    public void addLog(String str) {
        PetkitLog.d(str);
        LogcatStorageHelper.addLog(str);
    }

    public String parse(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) == 0) {
            return "";
        }
        char[] cArr = new char[length * 2];
        for (int i = 0; i < length; i++) {
            byte b = bArr[i];
            int i2 = i * 2;
            char[] cArr2 = BLEConsts.HEX_ARRAY;
            cArr[i2] = cArr2[(b & 255) >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public final void resetAllGoConnectState() {
        addLog("resetAllGoConnectState");
        List<GoRecord> goRecordList = GoDataUtils.getGoRecordList();
        if (goRecordList == null || goRecordList.size() <= 0) {
            return;
        }
        for (GoRecord goRecord : goRecordList) {
            goRecord.setConstate(0);
            goRecord.save();
        }
        sendConnectStateBroadcast();
    }

    public final void updateGoConnectState(long j, int i) {
        GoRecord goRecordById = GoDataUtils.getGoRecordById(j);
        if (goRecordById != null) {
            goRecordById.setConstate(i);
            goRecordById.save();
            sendConnectStateBroadcast();
        }
    }

    public final void sendConnectStateBroadcast() {
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(new Intent(GoDataUtils.BROADCAST_GO_STATE_UPDATE));
    }

    public final void sendStepStateBroadcast(int i, long j, String str) {
        Intent intent = new Intent(GoDataUtils.BROADCAST_GO_STEP_STATE);
        intent.putExtra(GoDataUtils.EXTRA_GO_ACTION, i);
        intent.putExtra(GoDataUtils.EXTRA_GO_ID, j);
        intent.putExtra(GoDataUtils.EXTRA_GO_DATA, str);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public final void sendWalkingStateChangedBroadcast(long j, int i) {
        Intent intent = new Intent(GoDataUtils.BROADCAST_GO_WALKING_STATE_CHANGED);
        intent.putExtra(GoDataUtils.EXTRA_GO_ID, j);
        intent.putExtra(GoDataUtils.EXTRA_GO_DATA, i);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public final void sendWalkDataDisconveryBroadcast(long j, int i) {
        Intent intent = new Intent(GoDataUtils.BROADCAST_GO_WALK_DATA_DISCONVERY);
        intent.putExtra(GoDataUtils.EXTRA_GO_ID, j);
        intent.putExtra(GoDataUtils.EXTRA_GO_DATA, i);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    public class GoAliveThread extends Thread {
        public static final int GO_BACKGROUND_GAP_TIMER_ALIVE = 5000;
        public static final int GO_CMD_TIMEOUT = 120000;
        public int DataConfirmFlag;
        public int curSectionBlockSize;
        public boolean isSyncData;
        public int lastHeartbeatDuration;
        public long lastHeartbeatTimeMillis;
        public boolean mAbort;
        public ByteArrayBuffer mByteArrayBuffer;
        public Timer mCheckTimer;
        public boolean mDataMissMode;
        public int mError;
        public GoAliveDeviceInfo mGoAliveDeviceInfo;
        public byte[] mReceivedData;
        public boolean mRequestCompleted;
        public final Object mSyncLock;
        public SparseArray<byte[]> mTempDataBuffers;
        public Timer mTimer;
        public TimerTask mTimerTask;
        public ArrayList<WriteDataStruct> mWriteDatas;
        public boolean needToWriteGpsCmd;
        public int receiveDataLength;
        public boolean timeOut;
        public int totalDataLength;

        public final boolean checkSectionDataConfirmFlag(int i, int i2) {
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                i3 |= 1 << i4;
            }
            return i == i3;
        }

        public GoAliveThread(GoAliveDeviceInfo goAliveDeviceInfo) {
            this.mReceivedData = null;
            this.mAbort = false;
            this.isSyncData = false;
            this.needToWriteGpsCmd = true;
            this.mSyncLock = new Object();
            this.mTimer = null;
            this.timeOut = false;
            this.mTempDataBuffers = new SparseArray<>();
            this.DataConfirmFlag = 0;
            this.mDataMissMode = false;
            this.totalDataLength = 0;
            this.receiveDataLength = 0;
            this.curSectionBlockSize = 0;
            this.mTimerTask = null;
            this.mGoAliveDeviceInfo = goAliveDeviceInfo;
            this.mWriteDatas = new ArrayList<>();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Timer timer;
            super.run();
            GoBackgroudThread.this.addLog("GoAliveThread start, device id: " + this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
            this.needToWriteGpsCmd = true;
            startTimerToCheckInNormal(120000);
            GoRecord goRecordById = GoDataUtils.getGoRecordById(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
            if (goRecordById == null) {
                GoBackgroudThread.this.cancelAliveThread(this.mGoAliveDeviceInfo);
                GoBackgroudThread.this.addLog("this go have been removed");
                return;
            }
            writeOpCode(GoDataUtils.buildOpCodeBuffer(-56, new int[0]));
            writeOpCode(GoDataUtils.buildOpCodeBuffer(86, goRecordById.getSecret()));
            writeOpCode(GoDataUtils.buildOpCodeBuffer(84, new int[0]));
            writeOpCode(GoDataUtils.buildOpCodeBuffer(-33, new int[0]));
            writeOpCode(GoDataUtils.buildOpCodeBuffer(-51, new int[0]));
            while (!this.mAbort) {
                WriteDataStruct writeDataStruct = getWriteDataStruct();
                writeOpCode(writeDataStruct.data);
                if (this.timeOut || this.mError != 0 || !this.mRequestCompleted) {
                    break;
                }
                this.mWriteDatas.remove(writeDataStruct);
                int i = writeDataStruct.gapTime;
                if (i > 0) {
                    try {
                        waitUntilTimeOut(i);
                        synchronized (this.mSyncLock) {
                            while (!this.timeOut && !this.mAbort && this.mError == 0 && (this.mWriteDatas.size() == 0 || writeDataStruct.data[0] != -48)) {
                                try {
                                    this.mSyncLock.wait();
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                        if (!this.timeOut && (timer = this.mTimer) != null) {
                            timer.cancel();
                            this.mTimer = null;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            stopCheckInNormalTimer();
            this.needToWriteGpsCmd = false;
            GoBackgroudThread.this.addLog("GoAliveThread stop timeOut : " + this.timeOut + ", mRequestCompleted: " + this.mRequestCompleted + ", mAbort : " + this.mAbort + " , mError: " + this.mError);
            GoBackgroudThread.this.cancelAliveThread(this.mGoAliveDeviceInfo);
        }

        public final WriteDataStruct getWriteDataStruct() {
            if (this.mWriteDatas.size() > 0) {
                return this.mWriteDatas.get(0);
            }
            return new WriteDataStruct(GoDataUtils.buildOpCodeBuffer(-48, new int[0]), 5000);
        }

        public final void writeData(byte[] bArr) {
            writeData(bArr, 0);
        }

        public final void writeData(byte[] bArr, int i) {
            this.mWriteDatas.add(new WriteDataStruct(bArr, i));
            synchronized (this.mSyncLock) {
                this.mSyncLock.notifyAll();
            }
        }

        public final void writeOpCode(byte[] bArr) {
            this.mRequestCompleted = false;
            this.timeOut = false;
            GoBackgroudThread.this.writeSyncCode(this.mGoAliveDeviceInfo, bArr);
            try {
                synchronized (this.mSyncLock) {
                    while (!this.mAbort && !this.mRequestCompleted && this.mError == 0) {
                        try {
                            this.mSyncLock.wait();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public final void onDataReceived(byte[] bArr) {
            refreshAliveTime();
            this.mReceivedData = bArr;
            if (parseData()) {
                return;
            }
            this.mRequestCompleted = true;
            synchronized (this.mSyncLock) {
                this.mSyncLock.notifyAll();
            }
        }

        public final boolean parseData() {
            byte[] bArr = this.mReceivedData;
            if (bArr == null) {
                return false;
            }
            byte b = bArr[0];
            if (b != -48) {
                if (b != -42 && b != -39) {
                    if (b == -33) {
                        int i = bArr[1] & 255;
                        long j = parserLongFromByteArray(bArr, 2, 4);
                        long j2 = parserLongFromByteArray(this.mReceivedData, 6, 4);
                        GoBackgroudThread.this.addLog("restartCount: " + i + ", startDuration: " + j + ", time: " + j2);
                        return false;
                    }
                    if (b != 86) {
                        if (b == 68) {
                            return parserSyncData(bArr);
                        }
                        if (b != 69) {
                            switch (b) {
                                case -51:
                                    updateGoRecordConfig(bArr);
                                    break;
                            }
                            return false;
                        }
                        writeData(GoDataUtils.buildOpCodeBuffer(69, 1), 5000);
                        saveConfimedSectionData();
                        this.isSyncData = false;
                        return false;
                    }
                    if (bArr[1] != 1) {
                        this.mError = 1;
                        return false;
                    }
                    GoRecord goRecordById = GoDataUtils.getGoRecordById(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
                    GoBackgroudThread.this.addLog("verify successful, firmware: " + ((int) this.mReceivedData[2]));
                    if (goRecordById == null || this.mReceivedData[2] == goRecordById.getFirmware()) {
                        return false;
                    }
                    goRecordById.setFirmware(this.mReceivedData[2]);
                    goRecordById.save();
                    Intent intent = new Intent(GoDataUtils.BROADCAST_GO_UPLOAD_CONFIG);
                    intent.putExtra(GoDataUtils.EXTRA_GO_ID, goRecordById.getDeviceId());
                    LocalBroadcastManager.getInstance(GoBackgroudThread.this.mContext).sendBroadcast(intent);
                    return false;
                }
                if ((bArr[1] & 255) == 1) {
                    writeData(GoDataUtils.buildOpCodeBuffer(-51, new int[0]));
                    return false;
                }
                GoBackgroudThread.this.addLog("write data failed, write data: " + GoBackgroudThread.this.parse(this.mReceivedData));
                return false;
            }
            parseHeartbeatData(bArr);
            return false;
        }

        public final void setAbort(boolean z) {
            GoBackgroudThread.this.addLog("GoAliveThread setAbort " + z + ", device id: " + this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
            this.mAbort = z;
            synchronized (this.mSyncLock) {
                this.mSyncLock.notifyAll();
            }
        }

        public final void waitUntilTimeOut(long j) {
            if (this.mTimer == null) {
                this.mTimer = new Timer();
            }
            this.mTimer.schedule(new TimerTask() { // from class: com.petkit.android.activities.go.service.GoBackgroudThread.GoAliveThread.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    GoAliveThread.this.timeOut = true;
                    synchronized (GoAliveThread.this.mSyncLock) {
                        GoAliveThread.this.mSyncLock.notifyAll();
                    }
                }
            }, j);
            this.timeOut = false;
        }

        public final void updateGoRecordConfig(byte[] bArr) {
            boolean z;
            GoRecord goRecordById = GoDataUtils.getGoRecordById(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
            boolean z2 = true;
            int i = bArr[1] & 255;
            if (goRecordById.getLightMode() != i) {
                goRecordById.setLightMode(i);
                z = true;
            } else {
                z = false;
            }
            int i2 = bArr[2] & 255;
            if (goRecordById.getLightAtNight() != i2) {
                goRecordById.setLightAtNight(i2);
                z = true;
            }
            int i3 = bArr[4] & 255;
            if (goRecordById.getCallAlert() != i3) {
                goRecordById.setCallAlert(i3);
                z = true;
            }
            int i4 = bArr[5] & 255;
            if (goRecordById.getCallAlertTimes() != i4) {
                goRecordById.setCallAlertTimes(i4);
                z = true;
            }
            int i5 = bArr[6] & 255;
            if (goRecordById.getCallAlertMode() != i5) {
                goRecordById.setCallAlertMode(i5);
                z = true;
            }
            int i6 = ((bArr[8] & 255) << 8) + (bArr[9] & 255);
            int i7 = ((bArr[10] & 255) << 8) + (bArr[11] & 255);
            if (goRecordById.getLightTimeStart() != i6 || goRecordById.getLightTimeEnd() != i7) {
                goRecordById.setLightTimeStart(i6);
                goRecordById.setLightTimeEnd(i7);
                z = true;
            }
            int i8 = bArr[13] & 255;
            if (goRecordById.getLightOnCharge() != i8) {
                goRecordById.setLightOnCharge(i8);
            } else {
                z2 = z;
            }
            if (z2) {
                goRecordById.save();
                GoBackgroudThread.this.addLog("GoRecord update: " + goRecordById.toString());
                Intent intent = new Intent(GoDataUtils.BROADCAST_GO_UPDATE);
                intent.putExtra(GoDataUtils.EXTRA_GO_ID, goRecordById.getDeviceId());
                LocalBroadcastManager.getInstance(GoBackgroudThread.this.mContext).sendBroadcast(intent);
                Intent intent2 = new Intent(GoDataUtils.BROADCAST_GO_UPLOAD_CONFIG);
                intent2.putExtra(GoDataUtils.EXTRA_GO_ID, goRecordById.getDeviceId());
                LocalBroadcastManager.getInstance(GoBackgroudThread.this.mContext).sendBroadcast(intent2);
            }
        }

        public final void parseHeartbeatData(byte[] bArr) {
            long j;
            GoWalkData goWalkDataByConditions;
            int i;
            boolean z;
            GoWalkData goWalkDataByConditions2;
            int i2 = bArr[1] & 255;
            float f = ((bArr[2] * 256) + (bArr[3] & 255)) / 1000.0f;
            int i3 = bArr[4] & 255;
            long j2 = parserLongFromByteArray(bArr, 5, 4);
            int i4 = bArr[9] & 255;
            byte b = bArr[10];
            int i5 = bArr[11] & 255;
            int i6 = bArr[12] & 255;
            GoRecord goRecordById = GoDataUtils.getGoRecordById(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
            if (i2 > 0) {
                j = j2;
                if (i2 + 5 <= goRecordById.getBattery() || i2 - 5 > goRecordById.getBattery() || (i2 < 10 && i2 + 2 <= goRecordById.getBattery())) {
                    goRecordById.setBattery(i2);
                    goRecordById.setVoltage(f);
                    goRecordById.save();
                    Intent intent = new Intent(GoDataUtils.BROADCAST_GO_UPDATE);
                    intent.putExtra(GoDataUtils.EXTRA_GO_ID, goRecordById.getDeviceId());
                    LocalBroadcastManager.getInstance(GoBackgroudThread.this.mContext).sendBroadcast(intent);
                    Intent intent2 = new Intent(GoDataUtils.BROADCAST_GO_UPLOAD_CONFIG);
                    intent2.putExtra(GoDataUtils.EXTRA_GO_ID, goRecordById.getDeviceId());
                    LocalBroadcastManager.getInstance(GoBackgroudThread.this.mContext).sendBroadcast(intent2);
                }
                if (i2 < 15 && (b & 1) == 1 && (goWalkDataByConditions2 = GoDataUtils.getGoWalkDataByConditions(Condition.prop("state").eq(1), Condition.prop("deviceId").eq(Long.valueOf(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId())))) != null && goWalkDataByConditions2.getLowPowerStateWhileWalking() == 0) {
                    goWalkDataByConditions2.setLowPowerStateWhileWalking(1);
                    goWalkDataByConditions2.save();
                    GoBackgroudThread.this.sendWalkingStateChangedBroadcast(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 6);
                }
            } else {
                j = j2;
            }
            if (i4 > 0) {
                if (this.isSyncData) {
                    return;
                }
                this.isSyncData = true;
                writeData(GoDataUtils.buildOpCodeBuffer(68, new int[0]));
                return;
            }
            if (i5 == 1) {
                goRecordById.setBattery(0);
                goRecordById.save();
                writeData(GoDataUtils.buildOpCodeBuffer(-41, 1));
                Intent intent3 = new Intent(GoDataUtils.BROADCAST_GO_UPDATE);
                intent3.putExtra(GoDataUtils.EXTRA_GO_ID, goRecordById.getDeviceId());
                LocalBroadcastManager.getInstance(GoBackgroudThread.this.mContext).sendBroadcast(intent3);
                Intent intent4 = new Intent(GoDataUtils.BROADCAST_GO_UPLOAD_CONFIG);
                intent4.putExtra(GoDataUtils.EXTRA_GO_ID, goRecordById.getDeviceId());
                LocalBroadcastManager.getInstance(GoBackgroudThread.this.mContext).sendBroadcast(intent4);
                return;
            }
            if (i3 != 1 || j <= 0) {
                if (i3 != 0 || (goWalkDataByConditions = GoDataUtils.getGoWalkDataByConditions(Condition.prop("state").eq(1), Condition.prop("deviceId").eq(Long.valueOf(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId())))) == null) {
                    return;
                }
                GoBackgroudThread.this.addLog("find a invalid walk record, remove it. " + goWalkDataByConditions.toString());
                HashMap map = new HashMap();
                map.put("goId", String.valueOf(goWalkDataByConditions.getDeviceId()));
                map.put("time", DateUtil.formatISO8601DateWithMills(new Date()));
                goWalkDataByConditions.delete();
                return;
            }
            long j3 = j * 1000;
            GoWalkData goWalkDataByConditions3 = GoDataUtils.getGoWalkDataByConditions(Condition.prop("timeindex").eq(Long.valueOf(GoDateUtils.getDeviceBaseTime() + j3)), Condition.prop("deviceId").eq(Long.valueOf(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId())));
            if (goWalkDataByConditions3 != null) {
                if (goWalkDataByConditions3.getState() != 1) {
                    GoBackgroudThread.this.addLog("this walk record has been canceled by app");
                    writeData(GoDataUtils.buildOpCodeBuffer(-49, 0));
                    GoBackgroudThread.this.sendWalkingStateChangedBroadcast(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 4);
                } else if (this.needToWriteGpsCmd && CommonUtils.checkGPSIsOpened(GoBackgroudThread.this.mContext)) {
                    this.needToWriteGpsCmd = false;
                    writeData(GoDataUtils.buildOpCodeBuffer(-40, 1));
                }
            } else {
                GoWalkData goWalkDataByConditions4 = GoDataUtils.getGoWalkDataByConditions(Condition.prop("state").eq(1), Condition.prop("deviceId").eq(Long.valueOf(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId())));
                if (goWalkDataByConditions4 != null) {
                    GoBackgroudThread.this.addLog("error occurred, app last walking record is still used, remove it. " + goWalkDataByConditions4.toString());
                    goWalkDataByConditions4.delete();
                }
                GoWalkData goWalkData = new GoWalkData();
                goWalkData.setOwnerId(Long.valueOf(CommonUtils.getCurrentUserId()).longValue());
                goWalkData.setDeviceId(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
                goWalkData.setTimeindex(GoDateUtils.getDeviceBaseTime() + j3);
                goWalkData.setT1(GoDateUtils.getDeviceTimeStamp(j3));
                goWalkData.setState(1);
                if (i2 < 15 && (b & 1) == 1) {
                    goWalkData.setLowPowerStateWhileWalking(1);
                }
                goWalkData.save();
                GoBackgroudThread.this.addLog("find a walking started, " + goWalkData.toString());
                GoBackgroudThread.this.sendWalkingStateChangedBroadcast(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 2);
                if (CommonUtils.checkGPSIsOpened(GoBackgroudThread.this.mContext)) {
                    i = 0;
                    this.needToWriteGpsCmd = false;
                    z = true;
                    writeData(GoDataUtils.buildOpCodeBuffer(-40, 1));
                }
                if (i6 > 0 || this.isSyncData) {
                }
                this.isSyncData = z;
                writeData(GoDataUtils.buildOpCodeBuffer(68, new int[i]));
                return;
            }
            i = 0;
            z = true;
            if (i6 > 0) {
            }
        }

        public final boolean parserSyncData(byte[] bArr) {
            int i = bArr[1] & 255;
            this.curSectionBlockSize = bArr[2] & 255;
            StringBuilder sb = new StringBuilder(bArr.length);
            for (int i2 = 2; i2 < bArr.length; i2++) {
                sb.append(String.format("%02X ", Byte.valueOf(bArr[i2])));
            }
            GoBackgroudThread.this.addLog("[R-D] receive index:" + String.valueOf(i) + FileUtil.SLASH_CHAR + String.valueOf(this.curSectionBlockSize) + " data: " + sb.toString());
            if (dataConfirm(i)) {
                parseActivityData(bArr);
                if (checkSectionDataComplete(bArr)) {
                    writeData(buildOpCodeBuffer(67));
                    GoBackgroudThread.this.sendStepStateBroadcast(-5, this.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), String.valueOf((this.receiveDataLength * 100) / this.totalDataLength));
                    return false;
                }
            }
            return true;
        }

        public final boolean dataConfirm(int i) {
            int i2 = this.DataConfirmFlag;
            int i3 = 1 << i;
            boolean z = (i2 & i3) == 0;
            if (z) {
                this.DataConfirmFlag = i3 | i2;
            }
            return z;
        }

        public final void parseActivityData(byte[] bArr) {
            byte[] bArr2;
            if (bArr[3] == 0) {
                this.totalDataLength = (int) parserLongFromByteArray(bArr, 4, 4);
                GoBackgroudThread.this.addLog("total length: " + this.totalDataLength);
                bArr2 = new byte[bArr.length - 8];
                System.arraycopy(bArr, 8, bArr2, 0, bArr.length - 8);
            } else {
                bArr2 = new byte[bArr.length - 3];
                System.arraycopy(bArr, 3, bArr2, 0, bArr.length - 3);
            }
            GoBackgroudThread.this.addLog("mTempDataBuffers put index: " + (bArr[1] & 255));
            this.mTempDataBuffers.put(bArr[1] & 255, bArr2);
            this.receiveDataLength = this.receiveDataLength + (bArr.length - 3);
        }

        public final boolean checkSectionDataComplete(byte[] bArr) {
            int i = bArr[1] & 255;
            int i2 = bArr[2] & 255;
            if (i + 1 != i2 && (!this.mDataMissMode || !checkSectionDataConfirmFlag(this.DataConfirmFlag, i2))) {
                return false;
            }
            GoBackgroudThread.this.addLog("[R-D] send confirmbit to get next packages data. mDataMissMode: " + this.mDataMissMode);
            if (this.mByteArrayBuffer == null) {
                this.mByteArrayBuffer = new ByteArrayBuffer(100);
            }
            for (int i3 = 0; i3 < this.mTempDataBuffers.size(); i3++) {
                this.mByteArrayBuffer.append(this.mTempDataBuffers.get(i3), 0, this.mTempDataBuffers.get(i3).length);
            }
            this.mTempDataBuffers.clear();
            return true;
        }

        public final byte[] buildOpCodeBuffer(int i) {
            byte[] bArr = new byte[13];
            if (i == 67) {
                int i2 = this.DataConfirmFlag;
                int i3 = this.curSectionBlockSize;
                int i4 = BLEConsts.MAX_BLOCK_SIZE;
                if (i3 < i4) {
                    for (int i5 = i4 - 1; i5 >= this.curSectionBlockSize; i5--) {
                        i2 |= 1 << i5;
                    }
                }
                if (i2 == -1) {
                    this.DataConfirmFlag = 0;
                    this.mDataMissMode = false;
                } else {
                    this.mDataMissMode = true;
                    LogcatStorageHelper.addLog("start reget mode, some data dismiss");
                }
                String hexString = Integer.toHexString(i2);
                bArr[0] = Ser.YEAR_TYPE;
                bArr[1] = Integer.valueOf(Integer.parseInt(hexString.substring(0, 2), 16)).byteValue();
                bArr[2] = Integer.valueOf(Integer.parseInt(hexString.substring(2, 4), 16)).byteValue();
                bArr[3] = Integer.valueOf(Integer.parseInt(hexString.substring(4, 6), 16)).byteValue();
                bArr[4] = Integer.valueOf(Integer.parseInt(hexString.substring(6, 8), 16)).byteValue();
            }
            return bArr;
        }

        public final void saveConfimedSectionData() {
            int i;
            byte[] bArr;
            ByteArrayBuffer byteArrayBuffer = this.mByteArrayBuffer;
            if (byteArrayBuffer == null || byteArrayBuffer.length() <= 0) {
                return;
            }
            byte[] byteArray = this.mByteArrayBuffer.toByteArray();
            GoBackgroudThread.this.addLog("saveConfimedSectionData : " + GoBackgroudThread.this.parse(byteArray));
            long j = 0;
            int i2 = 0;
            int i3 = 0;
            boolean z = false;
            boolean z2 = false;
            while (i2 < byteArray.length && (i = byteArray[i2] & 15) != 0) {
                long j2 = parserLongFromByteArray(byteArray, i2 + 1, i);
                int i4 = byteArray[i2] >> 4;
                if (i4 == 1) {
                    bArr = byteArray;
                    long j3 = 1000 * j2;
                    if (GoDataUtils.getGoWalkDataByConditions(Condition.prop("timeindex").eq(Long.valueOf(j3 + GoDateUtils.getDeviceBaseTime())), Condition.prop("deviceId").eq(Long.valueOf(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId()))) == null) {
                        GoWalkData goWalkData = new GoWalkData();
                        goWalkData.setT1(GoDateUtils.getDeviceTimeStamp(j3));
                        goWalkData.setTimeindex(j3 + GoDateUtils.getDeviceBaseTime());
                        goWalkData.setDeviceId(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
                        goWalkData.setSource(1);
                        goWalkData.setOwnerId(Long.valueOf(CommonUtils.getCurrentUserId()).longValue());
                        Pet defaultPetForGoWalkData = GoDataUtils.getDefaultPetForGoWalkData(goWalkData);
                        if (defaultPetForGoWalkData != null) {
                            goWalkData.setPetId(defaultPetForGoWalkData.getId());
                        }
                        goWalkData.setLowPowerStateWhileWalking(0);
                        goWalkData.setDay(Integer.valueOf(DateUtil.getFormatDate7FromString(goWalkData.getT1())).intValue());
                        goWalkData.save();
                        i3++;
                        GoBackgroudThread.this.addLog("get new walk data from Go: " + goWalkData.toString());
                    }
                    j = j2;
                } else if (i4 == 2) {
                    bArr = byteArray;
                    GoBackgroudThread.this.addLog(String.format("parser data T1: %s, T2: %s", Long.valueOf(j), Long.valueOf(j2)));
                    GoWalkData goWalkDataByConditions = GoDataUtils.getGoWalkDataByConditions(Condition.prop("timeindex").eq(Long.valueOf((j * 1000) + GoDateUtils.getDeviceBaseTime())), Condition.prop("deviceId").eq(Long.valueOf(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId())));
                    if (CommonUtils.isEmpty(goWalkDataByConditions.getT2())) {
                        z = goWalkDataByConditions.getState() == 1;
                        int iIntValue = Integer.valueOf(DateUtil.getFormatDate7FromString(goWalkDataByConditions.getT1())).intValue();
                        goWalkDataByConditions.setDay(iIntValue);
                        goWalkDataByConditions.setT2(GoDateUtils.getDeviceTimeStamp(j2 * 1000));
                        goWalkDataByConditions.setSource(1);
                        goWalkDataByConditions.setLowPowerStateWhileWalking(0);
                        goWalkDataByConditions.setState(GoDataUtils.checkWalkDurationIsValid(goWalkDataByConditions) ? 2 : 5);
                        Pet defaultPetForGoWalkData2 = GoDataUtils.getDefaultPetForGoWalkData(goWalkDataByConditions);
                        if (defaultPetForGoWalkData2 != null) {
                            goWalkDataByConditions.setPetId(defaultPetForGoWalkData2.getId());
                        }
                        goWalkDataByConditions.save();
                        if (goWalkDataByConditions.getState() == 2) {
                            GoRecord goRecordById = GoDataUtils.getGoRecordById(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId());
                            goRecordById.increaseNumberUnread(GoBackgroudThread.this.mContext);
                            GoBackgroudThread.this.sendWalkDataDisconveryBroadcast(goRecordById.getDeviceId(), iIntValue);
                            GoBackgroudThread.this.addLog("walking data is complete from go: " + goWalkDataByConditions.toString());
                            GoDayData orCreateGoDayDataForDay = GoDataUtils.getOrCreateGoDayDataForDay(goWalkDataByConditions.getDeviceId(), iIntValue);
                            orCreateGoDayDataForDay.setDistance(goWalkDataByConditions.getDistance());
                            orCreateGoDayDataForDay.setDuration(orCreateGoDayDataForDay.getDuration() + goWalkDataByConditions.getDuration());
                            orCreateGoDayDataForDay.setTimes(orCreateGoDayDataForDay.getTimes() + 1);
                            orCreateGoDayDataForDay.save();
                        }
                        HashMap map = new HashMap();
                        map.put("fromWhere", DeviceRequestsHelper.DEVICE_INFO_DEVICE);
                        map.put("type", GoDataUtils.getGoWalkingFinishTypeForStatistics(goWalkDataByConditions));
                    } else if (goWalkDataByConditions.getState() == 2) {
                        long j4 = j2 * 1000;
                        if (GoDateUtils.getDeviceBaseTime() + j4 < DateUtil.getMillisecondByDateString(goWalkDataByConditions.getT2())) {
                            goWalkDataByConditions.setT2(GoDateUtils.getDeviceTimeStamp(j4));
                            goWalkDataByConditions.setLowPowerStateWhileWalking(0);
                            goWalkDataByConditions.setSource(1);
                            Pet defaultPetForGoWalkData3 = GoDataUtils.getDefaultPetForGoWalkData(goWalkDataByConditions);
                            if (defaultPetForGoWalkData3 != null) {
                                goWalkDataByConditions.setPetId(defaultPetForGoWalkData3.getId());
                            }
                            goWalkDataByConditions.save();
                        }
                    }
                    z2 = false;
                } else if (i4 != 4) {
                    bArr = byteArray;
                } else {
                    GoWalkData goWalkDataByConditions2 = GoDataUtils.getGoWalkDataByConditions(Condition.prop("timeindex").eq(Long.valueOf((j * 1000) + GoDateUtils.getDeviceBaseTime())), Condition.prop("deviceId").eq(Long.valueOf(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId())));
                    GoMarker goMarker = new GoMarker();
                    long j5 = j2 * 1000;
                    goMarker.setCreatedAt(GoDateUtils.getDeviceTimeStamp(j5));
                    goMarker.setTimeindex(j5 + GoDateUtils.getDeviceBaseTime());
                    goWalkDataByConditions2.addMarker(goMarker);
                    GoBackgroudThread.this.addLog("find marker : " + goMarker.toString());
                    bArr = byteArray;
                    z2 = true;
                }
                i2 += i + 1;
                byteArray = bArr;
            }
            if (z) {
                GoBackgroudThread.this.sendWalkingStateChangedBroadcast(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 1);
            } else if (i3 > 0) {
                GoBackgroudThread.this.sendWalkingStateChangedBroadcast(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 3);
            }
            if (z2) {
                GoBackgroudThread.this.sendWalkingStateChangedBroadcast(this.mGoAliveDeviceInfo.deviceInfo.getDeviceId(), 5);
            }
            this.mByteArrayBuffer.clear();
        }

        public final long parserLongFromByteArray(byte[] bArr, int i, int i2) {
            try {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i, bArr2, 0, i2);
                return Long.parseLong(GoBackgroudThread.this.parse(bArr2), 16);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0L;
            }
        }

        public class WriteDataStruct {
            public byte[] data;
            public int gapTime;

            public WriteDataStruct(byte[] bArr, int i) {
                this.data = bArr;
                this.gapTime = i;
            }
        }

        public class HeartbeatTimerTask extends TimerTask {
            public HeartbeatTimerTask() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (GoBackgroudThread.this.mPaused || System.currentTimeMillis() - GoAliveThread.this.lastHeartbeatTimeMillis <= GoAliveThread.this.lastHeartbeatDuration) {
                    return;
                }
                GoAliveThread.this.mError = BLEConsts.ERROR_SYNC_TIMEOUT;
                GoAliveThread.this.stopCheckInNormalTimer();
                synchronized (GoAliveThread.this.mSyncLock) {
                    GoAliveThread.this.mSyncLock.notifyAll();
                }
            }
        }

        public final void refreshAliveTime() {
            this.lastHeartbeatTimeMillis = System.currentTimeMillis();
        }

        public final void stopCheckInNormalTimer() {
            Timer timer = this.mCheckTimer;
            if (timer != null) {
                timer.cancel();
                this.mCheckTimer = null;
                TimerTask timerTask = this.mTimerTask;
                if (timerTask != null) {
                    timerTask.cancel();
                    this.mTimerTask = null;
                }
            }
        }

        public final void startTimerToCheckInNormal(int i) {
            if (this.lastHeartbeatDuration == i) {
                return;
            }
            stopCheckInNormalTimer();
            this.lastHeartbeatDuration = i;
            this.lastHeartbeatTimeMillis = System.currentTimeMillis();
            this.mCheckTimer = new Timer();
            HeartbeatTimerTask heartbeatTimerTask = new HeartbeatTimerTask();
            this.mTimerTask = heartbeatTimerTask;
            Timer timer = this.mCheckTimer;
            int i2 = this.lastHeartbeatDuration;
            timer.schedule(heartbeatTimerTask, i2, i2);
        }
    }

    public class GoAliveDeviceInfo {
        public BluetoothGattCharacteristic controlCharacteristic;
        public BluetoothGattCharacteristic dataCharacteristic;
        public DeviceInfo deviceInfo;
        public BluetoothGatt gatt;

        public GoAliveDeviceInfo(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, BluetoothGattCharacteristic bluetoothGattCharacteristic2, DeviceInfo deviceInfo) {
            this.gatt = bluetoothGatt;
            this.controlCharacteristic = bluetoothGattCharacteristic;
            this.dataCharacteristic = bluetoothGattCharacteristic2;
            this.deviceInfo = deviceInfo;
        }
    }
}

package com.petkit.android.activities.petkitBleDevice.ctw3.utils;

import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleData;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BleViewListener;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3ControlBleListener;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Record;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleStateUtils;
import com.petkit.android.api.http.apiResponse.HomeDeviceListRsp;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class CTW3BleUtils2 {
    public static final long pollTime = 5000;
    public boolean autoBleFlag;
    public CTW3BleApiUtils ctw3BleApiUtils;
    public Map<Long, CTW3BleStateUtils> ctw3BleStateUtilsMap;
    public Disposable pollDisposable;

    public interface BleDeviceListener {
        void onResult(boolean z);
    }

    public /* synthetic */ CTW3BleUtils2(AnonymousClass1 anonymousClass1) {
        this();
    }

    public CTW3BleUtils2() {
        this.ctw3BleApiUtils = CTW3BleApiUtils.getInstance();
        this.ctw3BleStateUtilsMap = new HashMap();
        this.autoBleFlag = true;
    }

    public static class CTW3BleUtilsHolder {
        public static CTW3BleUtils2 instance = new CTW3BleUtils2();
    }

    public static CTW3BleUtils2 getInstance() {
        return CTW3BleUtilsHolder.instance;
    }

    public void refreshCTW3StateUtilsMap(HomeDeviceListRsp homeDeviceListRsp) {
        if (homeDeviceListRsp.getResult().getDevices() == null || homeDeviceListRsp.getResult().getDevices().size() == 0) {
            return;
        }
        for (HomeDeviceData homeDeviceData : homeDeviceListRsp.getResult().getDevices()) {
            if ("CTW3".equalsIgnoreCase(homeDeviceData.getType())) {
                CTW3Record orCreateCTW3RecordByDeviceId = CTW3Utils.getOrCreateCTW3RecordByDeviceId(homeDeviceData.getId());
                if (!this.ctw3BleStateUtilsMap.containsKey(Long.valueOf(orCreateCTW3RecordByDeviceId.getDeviceId()))) {
                    this.ctw3BleStateUtilsMap.put(Long.valueOf(orCreateCTW3RecordByDeviceId.getDeviceId()), new CTW3BleStateUtils());
                }
            }
        }
        Iterator<Map.Entry<Long, CTW3BleStateUtils>> it = this.ctw3BleStateUtilsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, CTW3BleStateUtils> next = it.next();
            Iterator<HomeDeviceData> it2 = homeDeviceListRsp.getResult().getDevices().iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().getId() == next.getKey().longValue()) {
                        break;
                    }
                } else {
                    it.remove();
                    break;
                }
            }
        }
    }

    public boolean isAutoBleFlag() {
        return this.autoBleFlag;
    }

    public void setAutoBleFlag(boolean z) {
        this.autoBleFlag = z;
    }

    public void bleConnect(CTW3Record cTW3Record, CTW3BleViewListener cTW3BleViewListener) {
        bleConnect(cTW3Record.getDeviceId(), cTW3Record.getMac(), cTW3BleViewListener);
    }

    public void bleConnect(long j, String str, CTW3BleViewListener cTW3BleViewListener) {
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(j));
        if (cTW3BleStateUtils == null) {
            return;
        }
        bleConnect(j, str, cTW3BleStateUtils, cTW3BleViewListener);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$1 */
    public class AnonymousClass1 implements CTW3BleApiUtils.OnConnectListener {
        public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ String val$mac;
        public final /* synthetic */ CTW3BleViewListener val$viewListener;

        public AnonymousClass1(CTW3BleViewListener cTW3BleViewListener, CTW3BleStateUtils cTW3BleStateUtils, long j, String str) {
            cTW3BleViewListener = cTW3BleViewListener;
            cTW3BleStateUtils = cTW3BleStateUtils;
            j = j;
            str = str;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnConnectListener
        public void onResult(CTW3BleApiUtils.BleConnectStartRsp bleConnectStartRsp) {
            if (bleConnectStartRsp.getError() != null) {
                cTW3BleViewListener.onApiFailed(bleConnectStartRsp.getError().getMsg());
                CTW3BleUtils2.this.setBleStatus(cTW3BleStateUtils, j, CTW3BleStateUtils.RemoteBleStatus.connect_timeout);
                LogcatStorageHelper.addLog("bleConnect error:" + bleConnectStartRsp.getError().getMsg());
                return;
            }
            String string = bleConnectStartRsp.getResult().getString("state");
            if (string.equals("1") || string.equals("1.0")) {
                CTW3BleUtils2 cTW3BleUtils2 = CTW3BleUtils2.this;
                CTW3BleStateUtils cTW3BleStateUtils = cTW3BleStateUtils;
                long j = j;
                CTW3BleStateUtils.RemoteBleStatus remoteBleStatus = CTW3BleStateUtils.RemoteBleStatus.connecting;
                cTW3BleUtils2.setBleStatus(cTW3BleStateUtils, j, remoteBleStatus);
                cTW3BleViewListener.onBleStatus(remoteBleStatus);
                CTW3BleUtils2.this.pollBleStatus(j, str, cTW3BleViewListener);
                return;
            }
            if (string.equals("-2") || string.equals("-2.0")) {
                CTW3BleUtils2 cTW3BleUtils22 = CTW3BleUtils2.this;
                CTW3BleStateUtils cTW3BleStateUtils2 = cTW3BleStateUtils;
                long j2 = j;
                CTW3BleStateUtils.RemoteBleStatus remoteBleStatus2 = CTW3BleStateUtils.RemoteBleStatus.connect_no_device;
                cTW3BleUtils22.setBleStatus(cTW3BleStateUtils2, j2, remoteBleStatus2);
                cTW3BleViewListener.onBleStatus(remoteBleStatus2);
                return;
            }
            if (string.equals("-1") || string.equals("-1.0")) {
                CTW3BleUtils2 cTW3BleUtils23 = CTW3BleUtils2.this;
                CTW3BleStateUtils cTW3BleStateUtils3 = cTW3BleStateUtils;
                long j3 = j;
                CTW3BleStateUtils.RemoteBleStatus remoteBleStatus3 = CTW3BleStateUtils.RemoteBleStatus.connect_offline;
                cTW3BleUtils23.setBleStatus(cTW3BleStateUtils3, j3, remoteBleStatus3);
                cTW3BleViewListener.onBleStatus(remoteBleStatus3);
                return;
            }
            if (string.equals("2") || string.equals("2.0")) {
                return;
            }
            CTW3BleUtils2 cTW3BleUtils24 = CTW3BleUtils2.this;
            CTW3BleStateUtils cTW3BleStateUtils4 = cTW3BleStateUtils;
            long j4 = j;
            CTW3BleStateUtils.RemoteBleStatus remoteBleStatus4 = CTW3BleStateUtils.RemoteBleStatus.connect_timeout;
            cTW3BleUtils24.setBleStatus(cTW3BleStateUtils4, j4, remoteBleStatus4);
            cTW3BleViewListener.onBleStatus(remoteBleStatus4);
        }
    }

    public final void bleConnect(long j, String str, CTW3BleStateUtils cTW3BleStateUtils, CTW3BleViewListener cTW3BleViewListener) {
        disposable();
        this.ctw3BleApiUtils.bleConnect(j, str, new CTW3BleApiUtils.OnConnectListener() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.1
            public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
            public final /* synthetic */ long val$deviceId;
            public final /* synthetic */ String val$mac;
            public final /* synthetic */ CTW3BleViewListener val$viewListener;

            public AnonymousClass1(CTW3BleViewListener cTW3BleViewListener2, CTW3BleStateUtils cTW3BleStateUtils2, long j2, String str2) {
                cTW3BleViewListener = cTW3BleViewListener2;
                cTW3BleStateUtils = cTW3BleStateUtils2;
                j = j2;
                str = str2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnConnectListener
            public void onResult(CTW3BleApiUtils.BleConnectStartRsp bleConnectStartRsp) {
                if (bleConnectStartRsp.getError() != null) {
                    cTW3BleViewListener.onApiFailed(bleConnectStartRsp.getError().getMsg());
                    CTW3BleUtils2.this.setBleStatus(cTW3BleStateUtils, j, CTW3BleStateUtils.RemoteBleStatus.connect_timeout);
                    LogcatStorageHelper.addLog("bleConnect error:" + bleConnectStartRsp.getError().getMsg());
                    return;
                }
                String string = bleConnectStartRsp.getResult().getString("state");
                if (string.equals("1") || string.equals("1.0")) {
                    CTW3BleUtils2 cTW3BleUtils2 = CTW3BleUtils2.this;
                    CTW3BleStateUtils cTW3BleStateUtils2 = cTW3BleStateUtils;
                    long j2 = j;
                    CTW3BleStateUtils.RemoteBleStatus remoteBleStatus = CTW3BleStateUtils.RemoteBleStatus.connecting;
                    cTW3BleUtils2.setBleStatus(cTW3BleStateUtils2, j2, remoteBleStatus);
                    cTW3BleViewListener.onBleStatus(remoteBleStatus);
                    CTW3BleUtils2.this.pollBleStatus(j, str, cTW3BleViewListener);
                    return;
                }
                if (string.equals("-2") || string.equals("-2.0")) {
                    CTW3BleUtils2 cTW3BleUtils22 = CTW3BleUtils2.this;
                    CTW3BleStateUtils cTW3BleStateUtils22 = cTW3BleStateUtils;
                    long j22 = j;
                    CTW3BleStateUtils.RemoteBleStatus remoteBleStatus2 = CTW3BleStateUtils.RemoteBleStatus.connect_no_device;
                    cTW3BleUtils22.setBleStatus(cTW3BleStateUtils22, j22, remoteBleStatus2);
                    cTW3BleViewListener.onBleStatus(remoteBleStatus2);
                    return;
                }
                if (string.equals("-1") || string.equals("-1.0")) {
                    CTW3BleUtils2 cTW3BleUtils23 = CTW3BleUtils2.this;
                    CTW3BleStateUtils cTW3BleStateUtils3 = cTW3BleStateUtils;
                    long j3 = j;
                    CTW3BleStateUtils.RemoteBleStatus remoteBleStatus3 = CTW3BleStateUtils.RemoteBleStatus.connect_offline;
                    cTW3BleUtils23.setBleStatus(cTW3BleStateUtils3, j3, remoteBleStatus3);
                    cTW3BleViewListener.onBleStatus(remoteBleStatus3);
                    return;
                }
                if (string.equals("2") || string.equals("2.0")) {
                    return;
                }
                CTW3BleUtils2 cTW3BleUtils24 = CTW3BleUtils2.this;
                CTW3BleStateUtils cTW3BleStateUtils4 = cTW3BleStateUtils;
                long j4 = j;
                CTW3BleStateUtils.RemoteBleStatus remoteBleStatus4 = CTW3BleStateUtils.RemoteBleStatus.connect_timeout;
                cTW3BleUtils24.setBleStatus(cTW3BleStateUtils4, j4, remoteBleStatus4);
                cTW3BleViewListener.onBleStatus(remoteBleStatus4);
            }
        });
    }

    public final void pollBleStatus(long j, String str, CTW3BleViewListener cTW3BleViewListener) {
        Disposable disposable = this.pollDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.pollDisposable.dispose();
            this.pollDisposable = null;
        }
        Observable.interval(0L, 5000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.2
            public final /* synthetic */ long val$deviceId;
            public final /* synthetic */ String val$mac;
            public final /* synthetic */ CTW3BleViewListener val$viewListener;

            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            public AnonymousClass2(long j2, String str2, CTW3BleViewListener cTW3BleViewListener2) {
                j = j2;
                str = str2;
                cTW3BleViewListener = cTW3BleViewListener2;
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable2) {
                CTW3BleUtils2.this.pollDisposable = disposable2;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                CTW3BleUtils2.this.bleConnectStatus(j, str, cTW3BleViewListener);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$2 */
    public class AnonymousClass2 implements Observer<Long> {
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ String val$mac;
        public final /* synthetic */ CTW3BleViewListener val$viewListener;

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public AnonymousClass2(long j2, String str2, CTW3BleViewListener cTW3BleViewListener2) {
            j = j2;
            str = str2;
            cTW3BleViewListener = cTW3BleViewListener2;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable2) {
            CTW3BleUtils2.this.pollDisposable = disposable2;
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            CTW3BleUtils2.this.bleConnectStatus(j, str, cTW3BleViewListener);
        }
    }

    public void bleConnectStatus(long j, String str, CTW3BleViewListener cTW3BleViewListener) {
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(j));
        if (cTW3BleStateUtils == null) {
            return;
        }
        this.ctw3BleApiUtils.bleConnectStatus(j, str, new CTW3BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.3
            public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
            public final /* synthetic */ long val$deviceId;
            public final /* synthetic */ CTW3BleViewListener val$viewListener;

            public AnonymousClass3(CTW3BleViewListener cTW3BleViewListener2, CTW3BleStateUtils cTW3BleStateUtils2, long j2) {
                cTW3BleViewListener = cTW3BleViewListener2;
                cTW3BleStateUtils = cTW3BleStateUtils2;
                j = j2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
            public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() != null) {
                    cTW3BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                    LogcatStorageHelper.addLog("bleConnectStatus error:" + bleRsp.getError().getMsg());
                    return;
                }
                String result = bleRsp.getResult();
                if (result.equals("1")) {
                    CTW3BleUtils2.this.disposable();
                    CTW3BleUtils2 cTW3BleUtils2 = CTW3BleUtils2.this;
                    CTW3BleStateUtils cTW3BleStateUtils2 = cTW3BleStateUtils;
                    long j2 = j;
                    CTW3BleStateUtils.RemoteBleStatus remoteBleStatus = CTW3BleStateUtils.RemoteBleStatus.connect_success;
                    cTW3BleUtils2.setBleStatus(cTW3BleStateUtils2, j2, remoteBleStatus);
                    cTW3BleViewListener.onBleStatus(remoteBleStatus);
                    return;
                }
                if (result.equals("-1")) {
                    CTW3BleUtils2.this.disposable();
                    CTW3BleUtils2 cTW3BleUtils22 = CTW3BleUtils2.this;
                    CTW3BleStateUtils cTW3BleStateUtils3 = cTW3BleStateUtils;
                    long j3 = j;
                    CTW3BleStateUtils.RemoteBleStatus remoteBleStatus2 = CTW3BleStateUtils.RemoteBleStatus.connect_timeout;
                    cTW3BleUtils22.setBleStatus(cTW3BleStateUtils3, j3, remoteBleStatus2);
                    cTW3BleViewListener.onBleStatus(remoteBleStatus2);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$3 */
    public class AnonymousClass3 implements CTW3BleApiUtils.OnBleListener {
        public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ CTW3BleViewListener val$viewListener;

        public AnonymousClass3(CTW3BleViewListener cTW3BleViewListener2, CTW3BleStateUtils cTW3BleStateUtils2, long j2) {
            cTW3BleViewListener = cTW3BleViewListener2;
            cTW3BleStateUtils = cTW3BleStateUtils2;
            j = j2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
        public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() != null) {
                cTW3BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("bleConnectStatus error:" + bleRsp.getError().getMsg());
                return;
            }
            String result = bleRsp.getResult();
            if (result.equals("1")) {
                CTW3BleUtils2.this.disposable();
                CTW3BleUtils2 cTW3BleUtils2 = CTW3BleUtils2.this;
                CTW3BleStateUtils cTW3BleStateUtils2 = cTW3BleStateUtils;
                long j2 = j;
                CTW3BleStateUtils.RemoteBleStatus remoteBleStatus = CTW3BleStateUtils.RemoteBleStatus.connect_success;
                cTW3BleUtils2.setBleStatus(cTW3BleStateUtils2, j2, remoteBleStatus);
                cTW3BleViewListener.onBleStatus(remoteBleStatus);
                return;
            }
            if (result.equals("-1")) {
                CTW3BleUtils2.this.disposable();
                CTW3BleUtils2 cTW3BleUtils22 = CTW3BleUtils2.this;
                CTW3BleStateUtils cTW3BleStateUtils3 = cTW3BleStateUtils;
                long j3 = j;
                CTW3BleStateUtils.RemoteBleStatus remoteBleStatus2 = CTW3BleStateUtils.RemoteBleStatus.connect_timeout;
                cTW3BleUtils22.setBleStatus(cTW3BleStateUtils3, j3, remoteBleStatus2);
                cTW3BleViewListener.onBleStatus(remoteBleStatus2);
            }
        }
    }

    public final void disposable() {
        Disposable disposable = this.pollDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.pollDisposable.dispose();
        this.pollDisposable = null;
    }

    public void checkBleStatus(CTW3Record cTW3Record, CTW3BleViewListener cTW3BleViewListener) {
        if (cTW3Record == null) {
            return;
        }
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(cTW3Record.getDeviceId()));
        if (cTW3BleStateUtils == null) {
            cTW3BleStateUtils = new CTW3BleStateUtils();
        }
        cTW3BleViewListener.onBleStatus(cTW3BleStateUtils.getRemoteBleStatus());
    }

    public void cancelBle(long j, CTW3BleViewListener cTW3BleViewListener) {
        CTW3BleStateUtils cTW3BleStateUtils;
        CTW3Record cTW3RecordByDeviceId = CTW3Utils.getCTW3RecordByDeviceId(j);
        if (cTW3RecordByDeviceId == null || (cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(cTW3RecordByDeviceId.getDeviceId()))) == null) {
            return;
        }
        disposable();
        this.ctw3BleApiUtils.cancelBleConnect(cTW3RecordByDeviceId, new CTW3BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.4
            public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
            public final /* synthetic */ CTW3Record val$ctw3Record;
            public final /* synthetic */ CTW3BleViewListener val$viewListener;

            public AnonymousClass4(CTW3BleViewListener cTW3BleViewListener2, CTW3BleStateUtils cTW3BleStateUtils2, CTW3Record cTW3RecordByDeviceId2) {
                cTW3BleViewListener = cTW3BleViewListener2;
                cTW3BleStateUtils = cTW3BleStateUtils2;
                cTW3Record = cTW3RecordByDeviceId2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
            public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() == null) {
                    CTW3BleUtils2.this.setBleStatus(cTW3BleStateUtils, cTW3Record.getDeviceId(), CTW3BleStateUtils.RemoteBleStatus.prepare);
                    cTW3BleViewListener.onBleStatus(cTW3BleStateUtils.getRemoteBleStatus());
                    return;
                }
                cTW3BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$4 */
    public class AnonymousClass4 implements CTW3BleApiUtils.OnBleListener {
        public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
        public final /* synthetic */ CTW3Record val$ctw3Record;
        public final /* synthetic */ CTW3BleViewListener val$viewListener;

        public AnonymousClass4(CTW3BleViewListener cTW3BleViewListener2, CTW3BleStateUtils cTW3BleStateUtils2, CTW3Record cTW3RecordByDeviceId2) {
            cTW3BleViewListener = cTW3BleViewListener2;
            cTW3BleStateUtils = cTW3BleStateUtils2;
            cTW3Record = cTW3RecordByDeviceId2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
        public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() == null) {
                CTW3BleUtils2.this.setBleStatus(cTW3BleStateUtils, cTW3Record.getDeviceId(), CTW3BleStateUtils.RemoteBleStatus.prepare);
                cTW3BleViewListener.onBleStatus(cTW3BleStateUtils.getRemoteBleStatus());
                return;
            }
            cTW3BleViewListener.onApiFailed(bleRsp.getError().getMsg());
            LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
        }
    }

    public void cancelBle(CTW3Record cTW3Record, CTW3BleViewListener cTW3BleViewListener) {
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(cTW3Record.getDeviceId()));
        if (cTW3BleStateUtils == null) {
            return;
        }
        disposable();
        this.ctw3BleApiUtils.cancelBleConnect(cTW3Record, new CTW3BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.5
            public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
            public final /* synthetic */ CTW3Record val$ctw3Record;
            public final /* synthetic */ CTW3BleViewListener val$viewListener;

            public AnonymousClass5(CTW3BleViewListener cTW3BleViewListener2, CTW3BleStateUtils cTW3BleStateUtils2, CTW3Record cTW3Record2) {
                cTW3BleViewListener = cTW3BleViewListener2;
                cTW3BleStateUtils = cTW3BleStateUtils2;
                cTW3Record = cTW3Record2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
            public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() == null) {
                    CTW3BleUtils2.this.setBleStatus(cTW3BleStateUtils, cTW3Record.getDeviceId(), CTW3BleStateUtils.RemoteBleStatus.prepare);
                    cTW3BleViewListener.onBleStatus(cTW3BleStateUtils.getRemoteBleStatus());
                    return;
                }
                cTW3BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$5 */
    public class AnonymousClass5 implements CTW3BleApiUtils.OnBleListener {
        public final /* synthetic */ CTW3BleStateUtils val$ctw3BleStateUtils;
        public final /* synthetic */ CTW3Record val$ctw3Record;
        public final /* synthetic */ CTW3BleViewListener val$viewListener;

        public AnonymousClass5(CTW3BleViewListener cTW3BleViewListener2, CTW3BleStateUtils cTW3BleStateUtils2, CTW3Record cTW3Record2) {
            cTW3BleViewListener = cTW3BleViewListener2;
            cTW3BleStateUtils = cTW3BleStateUtils2;
            cTW3Record = cTW3Record2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
        public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() == null) {
                CTW3BleUtils2.this.setBleStatus(cTW3BleStateUtils, cTW3Record.getDeviceId(), CTW3BleStateUtils.RemoteBleStatus.prepare);
                cTW3BleViewListener.onBleStatus(cTW3BleStateUtils.getRemoteBleStatus());
                return;
            }
            cTW3BleViewListener.onApiFailed(bleRsp.getError().getMsg());
            LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
        }
    }

    public boolean isConnected(CTW3Record cTW3Record) {
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(cTW3Record.getDeviceId()));
        if (cTW3BleStateUtils == null) {
            return false;
        }
        return cTW3BleStateUtils.isRemoteBleConnected();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$6 */
    public class AnonymousClass6 implements CTW3BleApiUtils.OnBleListener {
        public final /* synthetic */ CTW3ControlBleListener val$controlBleListener;

        public AnonymousClass6(CTW3ControlBleListener cTW3ControlBleListener) {
            cTW3ControlBleListener = cTW3ControlBleListener;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
        public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() != null) {
                cTW3ControlBleListener.onControlFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("controlDevice error:" + bleRsp.getError().getMsg());
                return;
            }
            if (bleRsp.getResult().equals("1")) {
                cTW3ControlBleListener.closeLoading();
                cTW3ControlBleListener.onControlSend();
            } else {
                cTW3ControlBleListener.onControlFailed(bleRsp.getResult());
            }
        }
    }

    public void controlDevice(CTW3Record cTW3Record, PetkitBleData petkitBleData, CTW3ControlBleListener cTW3ControlBleListener) {
        this.ctw3BleApiUtils.bleControlDevice(cTW3Record.getDeviceId(), cTW3Record.getMac(), petkitBleData, new CTW3BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.6
            public final /* synthetic */ CTW3ControlBleListener val$controlBleListener;

            public AnonymousClass6(CTW3ControlBleListener cTW3ControlBleListener2) {
                cTW3ControlBleListener = cTW3ControlBleListener2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OnBleListener
            public void onResult(CTW3BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() != null) {
                    cTW3ControlBleListener.onControlFailed(bleRsp.getError().getMsg());
                    LogcatStorageHelper.addLog("controlDevice error:" + bleRsp.getError().getMsg());
                    return;
                }
                if (bleRsp.getResult().equals("1")) {
                    cTW3ControlBleListener.closeLoading();
                    cTW3ControlBleListener.onControlSend();
                } else {
                    cTW3ControlBleListener.onControlFailed(bleRsp.getResult());
                }
            }
        });
    }

    public void bleDisconnected(long j) {
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(j));
        if (cTW3BleStateUtils == null) {
            return;
        }
        CTW3BleStateUtils.RemoteBleStatus remoteBleStatus = cTW3BleStateUtils.getRemoteBleStatus();
        if (remoteBleStatus == CTW3BleStateUtils.RemoteBleStatus.connect_success || remoteBleStatus == CTW3BleStateUtils.RemoteBleStatus.connecting) {
            setBleStatus(cTW3BleStateUtils, j, CTW3BleStateUtils.RemoteBleStatus.prepare);
        }
    }

    public void bleDisconnected(long j, CTW3BleViewListener cTW3BleViewListener) {
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(j));
        if (cTW3BleStateUtils == null) {
            return;
        }
        CTW3BleStateUtils.RemoteBleStatus remoteBleStatus = cTW3BleStateUtils.getRemoteBleStatus();
        if (remoteBleStatus == CTW3BleStateUtils.RemoteBleStatus.connect_success || remoteBleStatus == CTW3BleStateUtils.RemoteBleStatus.connecting || remoteBleStatus == CTW3BleStateUtils.RemoteBleStatus.prepare) {
            CTW3BleStateUtils.RemoteBleStatus remoteBleStatus2 = CTW3BleStateUtils.RemoteBleStatus.prepare;
            setBleStatus(cTW3BleStateUtils, j, remoteBleStatus2);
            cTW3BleViewListener.onBleStatus(remoteBleStatus2);
        }
    }

    public void closeBle() {
        for (Map.Entry<Long, CTW3BleStateUtils> entry : this.ctw3BleStateUtilsMap.entrySet()) {
            if (entry.getValue().getRemoteBleStatus() == CTW3BleStateUtils.RemoteBleStatus.connect_success) {
                cancelBle(entry.getKey().longValue(), new CTW3BleViewListener() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.7
                    @Override // com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BleViewListener
                    public void onApiFailed(String str) {
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BleViewListener
                    public void onBleStatus(CTW3BleStateUtils.RemoteBleStatus remoteBleStatus) {
                    }

                    public AnonymousClass7() {
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$7 */
    public class AnonymousClass7 implements CTW3BleViewListener {
        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BleViewListener
        public void onApiFailed(String str) {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BleViewListener
        public void onBleStatus(CTW3BleStateUtils.RemoteBleStatus remoteBleStatus) {
        }

        public AnonymousClass7() {
        }
    }

    public final void setBleStatus(CTW3BleStateUtils cTW3BleStateUtils, long j, CTW3BleStateUtils.RemoteBleStatus remoteBleStatus) {
        cTW3BleStateUtils.setRemoteBleStatus(remoteBleStatus);
        PetkitLog.e("setBleStatus", "setBleStatus:deviceId=" + j + ",status:" + remoteBleStatus.name());
        LogcatStorageHelper.addLog("setBleStatus:deviceId=" + j + ",status:" + remoteBleStatus.name());
    }

    public void bleUpdate(CTW3Record cTW3Record) {
        this.ctw3BleApiUtils.bleUpdate(cTW3Record);
    }

    public void resetStatus(CTW3Record cTW3Record) {
        if (cTW3Record == null) {
            return;
        }
        CTW3BleStateUtils cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(cTW3Record.getDeviceId()));
        if (cTW3BleStateUtils == null) {
            cTW3BleStateUtils = new CTW3BleStateUtils();
        }
        if (cTW3BleStateUtils.getRemoteBleStatus() == CTW3BleStateUtils.RemoteBleStatus.connect_no_device || cTW3BleStateUtils.getRemoteBleStatus() == CTW3BleStateUtils.RemoteBleStatus.connect_timeout || cTW3BleStateUtils.getRemoteBleStatus() == CTW3BleStateUtils.RemoteBleStatus.connect_offline) {
            cTW3BleStateUtils.setRemoteBleStatus(CTW3BleStateUtils.RemoteBleStatus.prepare);
        }
    }

    public void destroy(CTW3Record cTW3Record) {
        CTW3BleStateUtils cTW3BleStateUtils;
        if (cTW3Record == null || (cTW3BleStateUtils = this.ctw3BleStateUtilsMap.get(Long.valueOf(cTW3Record.getDeviceId()))) == null) {
            return;
        }
        if (cTW3BleStateUtils.getRemoteBleStatus() == CTW3BleStateUtils.RemoteBleStatus.connecting) {
            disposable();
        }
        if (cTW3BleStateUtils.getRemoteBleStatus() != CTW3BleStateUtils.RemoteBleStatus.connect_success) {
            cTW3BleStateUtils.setRemoteBleStatus(CTW3BleStateUtils.RemoteBleStatus.prepare);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2$8 */
    public class AnonymousClass8 implements CTW3BleApiUtils.OwnSupportDeviceListener {
        public final /* synthetic */ BleDeviceListener val$listener;

        public AnonymousClass8(BleDeviceListener bleDeviceListener) {
            bleDeviceListener = bleDeviceListener;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OwnSupportDeviceListener
        public void onResult(CTW3BleApiUtils.OwnSupportDeviceRsp ownSupportDeviceRsp) {
            if (ownSupportDeviceRsp.getError() == null) {
                bleDeviceListener.onResult(ownSupportDeviceRsp.getResult() != null && ownSupportDeviceRsp.getResult().size() > 0);
            }
        }
    }

    public void ownBleDevice(BleDeviceListener bleDeviceListener) {
        this.ctw3BleApiUtils.ownSupportBleDevice(new CTW3BleApiUtils.OwnSupportDeviceListener() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2.8
            public final /* synthetic */ BleDeviceListener val$listener;

            public AnonymousClass8(BleDeviceListener bleDeviceListener2) {
                bleDeviceListener = bleDeviceListener2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.OwnSupportDeviceListener
            public void onResult(CTW3BleApiUtils.OwnSupportDeviceRsp ownSupportDeviceRsp) {
                if (ownSupportDeviceRsp.getError() == null) {
                    bleDeviceListener.onResult(ownSupportDeviceRsp.getResult() != null && ownSupportDeviceRsp.getResult().size() > 0);
                }
            }
        });
    }
}

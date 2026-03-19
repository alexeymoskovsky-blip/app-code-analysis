package com.petkit.android.activities.petkitBleDevice.w5.utils;

import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleData;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5BleViewListener;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5ControlBleListener;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleStateUtils;
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

/* JADX INFO: loaded from: classes5.dex */
public class W5BleUtils2 {
    public static final long pollTime = 5000;
    public boolean autoBleFlag;
    public Disposable pollDisposable;
    public W5BleApiUtils w5BleApiUtils;
    public Map<Long, W5BleStateUtils> w5BleStateUtilsMap;

    public interface BleDeviceListener {
        void onResult(boolean z);
    }

    public /* synthetic */ W5BleUtils2(AnonymousClass1 anonymousClass1) {
        this();
    }

    public W5BleUtils2() {
        this.w5BleApiUtils = W5BleApiUtils.getInstance();
        this.w5BleStateUtilsMap = new HashMap();
        this.autoBleFlag = true;
    }

    public static class W5BleUtilsHolder {
        public static W5BleUtils2 instance = new W5BleUtils2();
    }

    public static W5BleUtils2 getInstance() {
        return W5BleUtilsHolder.instance;
    }

    public void refreshW5StateUtilsMap(HomeDeviceListRsp homeDeviceListRsp) {
        if (homeDeviceListRsp.getResult().getDevices() == null || homeDeviceListRsp.getResult().getDevices().size() == 0) {
            return;
        }
        for (HomeDeviceData homeDeviceData : homeDeviceListRsp.getResult().getDevices()) {
            if ("W5".equalsIgnoreCase(homeDeviceData.getType())) {
                W5Record orCreateW5RecordByDeviceId = W5Utils.getOrCreateW5RecordByDeviceId(homeDeviceData.getId());
                if (!this.w5BleStateUtilsMap.containsKey(Long.valueOf(orCreateW5RecordByDeviceId.getDeviceId()))) {
                    this.w5BleStateUtilsMap.put(Long.valueOf(orCreateW5RecordByDeviceId.getDeviceId()), new W5BleStateUtils());
                }
            }
        }
        Iterator<Map.Entry<Long, W5BleStateUtils>> it = this.w5BleStateUtilsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, W5BleStateUtils> next = it.next();
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

    public void bleConnect(W5Record w5Record, W5BleViewListener w5BleViewListener) {
        bleConnect(w5Record.getDeviceId(), w5Record.getMac(), w5BleViewListener);
    }

    public void bleConnect(long j, String str, W5BleViewListener w5BleViewListener) {
        W5BleStateUtils w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(j));
        if (w5BleStateUtils == null) {
            return;
        }
        bleConnect(j, str, w5BleStateUtils, w5BleViewListener);
    }

    public final void bleConnect(long j, String str, W5BleStateUtils w5BleStateUtils, W5BleViewListener w5BleViewListener) {
        disposable();
        W5BleStateUtils.RemoteBleStatus remoteBleStatus = W5BleStateUtils.RemoteBleStatus.connecting;
        setBleStatus(w5BleStateUtils, j, remoteBleStatus);
        w5BleViewListener.onBleStatus(remoteBleStatus);
        this.w5BleApiUtils.bleConnect(j, str, new W5BleApiUtils.OnConnectListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.1
            public final /* synthetic */ long val$deviceId;
            public final /* synthetic */ String val$mac;
            public final /* synthetic */ W5BleViewListener val$viewListener;
            public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;

            public AnonymousClass1(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, long j2, String str2) {
                w5BleViewListener = w5BleViewListener2;
                w5BleStateUtils = w5BleStateUtils2;
                j = j2;
                str = str2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnConnectListener
            public void onResult(W5BleApiUtils.BleConnectStartRsp bleConnectStartRsp) {
                if (bleConnectStartRsp.getError() != null) {
                    w5BleViewListener.onApiFailed(bleConnectStartRsp.getError().getMsg());
                    W5BleUtils2.this.setBleStatus(w5BleStateUtils, j, W5BleStateUtils.RemoteBleStatus.connect_timeout);
                    LogcatStorageHelper.addLog("bleConnect error:" + bleConnectStartRsp.getError().getMsg());
                    return;
                }
                String string = bleConnectStartRsp.getResult().getString("state");
                if (string.equals("1") || string.equals("1.0")) {
                    W5BleUtils2 w5BleUtils2 = W5BleUtils2.this;
                    W5BleStateUtils w5BleStateUtils2 = w5BleStateUtils;
                    long j2 = j;
                    W5BleStateUtils.RemoteBleStatus remoteBleStatus2 = W5BleStateUtils.RemoteBleStatus.connecting;
                    w5BleUtils2.setBleStatus(w5BleStateUtils2, j2, remoteBleStatus2);
                    w5BleViewListener.onBleStatus(remoteBleStatus2);
                    W5BleUtils2.this.pollBleStatus(j, str, w5BleViewListener);
                    return;
                }
                if (string.equals("-2") || string.equals("-2.0")) {
                    W5BleUtils2 w5BleUtils22 = W5BleUtils2.this;
                    W5BleStateUtils w5BleStateUtils3 = w5BleStateUtils;
                    long j3 = j;
                    W5BleStateUtils.RemoteBleStatus remoteBleStatus3 = W5BleStateUtils.RemoteBleStatus.connect_no_device;
                    w5BleUtils22.setBleStatus(w5BleStateUtils3, j3, remoteBleStatus3);
                    w5BleViewListener.onBleStatus(remoteBleStatus3);
                    return;
                }
                if (string.equals("-1") || string.equals("-1.0")) {
                    W5BleUtils2 w5BleUtils23 = W5BleUtils2.this;
                    W5BleStateUtils w5BleStateUtils4 = w5BleStateUtils;
                    long j4 = j;
                    W5BleStateUtils.RemoteBleStatus remoteBleStatus4 = W5BleStateUtils.RemoteBleStatus.connect_offline;
                    w5BleUtils23.setBleStatus(w5BleStateUtils4, j4, remoteBleStatus4);
                    w5BleViewListener.onBleStatus(remoteBleStatus4);
                    return;
                }
                if (string.equals("2") || string.equals("2.0")) {
                    return;
                }
                W5BleUtils2 w5BleUtils24 = W5BleUtils2.this;
                W5BleStateUtils w5BleStateUtils5 = w5BleStateUtils;
                long j5 = j;
                W5BleStateUtils.RemoteBleStatus remoteBleStatus5 = W5BleStateUtils.RemoteBleStatus.connect_timeout;
                w5BleUtils24.setBleStatus(w5BleStateUtils5, j5, remoteBleStatus5);
                w5BleViewListener.onBleStatus(remoteBleStatus5);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$1 */
    public class AnonymousClass1 implements W5BleApiUtils.OnConnectListener {
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ String val$mac;
        public final /* synthetic */ W5BleViewListener val$viewListener;
        public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;

        public AnonymousClass1(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, long j2, String str2) {
            w5BleViewListener = w5BleViewListener2;
            w5BleStateUtils = w5BleStateUtils2;
            j = j2;
            str = str2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnConnectListener
        public void onResult(W5BleApiUtils.BleConnectStartRsp bleConnectStartRsp) {
            if (bleConnectStartRsp.getError() != null) {
                w5BleViewListener.onApiFailed(bleConnectStartRsp.getError().getMsg());
                W5BleUtils2.this.setBleStatus(w5BleStateUtils, j, W5BleStateUtils.RemoteBleStatus.connect_timeout);
                LogcatStorageHelper.addLog("bleConnect error:" + bleConnectStartRsp.getError().getMsg());
                return;
            }
            String string = bleConnectStartRsp.getResult().getString("state");
            if (string.equals("1") || string.equals("1.0")) {
                W5BleUtils2 w5BleUtils2 = W5BleUtils2.this;
                W5BleStateUtils w5BleStateUtils2 = w5BleStateUtils;
                long j2 = j;
                W5BleStateUtils.RemoteBleStatus remoteBleStatus2 = W5BleStateUtils.RemoteBleStatus.connecting;
                w5BleUtils2.setBleStatus(w5BleStateUtils2, j2, remoteBleStatus2);
                w5BleViewListener.onBleStatus(remoteBleStatus2);
                W5BleUtils2.this.pollBleStatus(j, str, w5BleViewListener);
                return;
            }
            if (string.equals("-2") || string.equals("-2.0")) {
                W5BleUtils2 w5BleUtils22 = W5BleUtils2.this;
                W5BleStateUtils w5BleStateUtils3 = w5BleStateUtils;
                long j3 = j;
                W5BleStateUtils.RemoteBleStatus remoteBleStatus3 = W5BleStateUtils.RemoteBleStatus.connect_no_device;
                w5BleUtils22.setBleStatus(w5BleStateUtils3, j3, remoteBleStatus3);
                w5BleViewListener.onBleStatus(remoteBleStatus3);
                return;
            }
            if (string.equals("-1") || string.equals("-1.0")) {
                W5BleUtils2 w5BleUtils23 = W5BleUtils2.this;
                W5BleStateUtils w5BleStateUtils4 = w5BleStateUtils;
                long j4 = j;
                W5BleStateUtils.RemoteBleStatus remoteBleStatus4 = W5BleStateUtils.RemoteBleStatus.connect_offline;
                w5BleUtils23.setBleStatus(w5BleStateUtils4, j4, remoteBleStatus4);
                w5BleViewListener.onBleStatus(remoteBleStatus4);
                return;
            }
            if (string.equals("2") || string.equals("2.0")) {
                return;
            }
            W5BleUtils2 w5BleUtils24 = W5BleUtils2.this;
            W5BleStateUtils w5BleStateUtils5 = w5BleStateUtils;
            long j5 = j;
            W5BleStateUtils.RemoteBleStatus remoteBleStatus5 = W5BleStateUtils.RemoteBleStatus.connect_timeout;
            w5BleUtils24.setBleStatus(w5BleStateUtils5, j5, remoteBleStatus5);
            w5BleViewListener.onBleStatus(remoteBleStatus5);
        }
    }

    public final void pollBleStatus(long j, String str, W5BleViewListener w5BleViewListener) {
        Disposable disposable = this.pollDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.pollDisposable.dispose();
            this.pollDisposable = null;
        }
        Observable.interval(0L, 5000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.2
            public final /* synthetic */ long val$deviceId;
            public final /* synthetic */ String val$mac;
            public final /* synthetic */ W5BleViewListener val$viewListener;

            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            public AnonymousClass2(long j2, String str2, W5BleViewListener w5BleViewListener2) {
                j = j2;
                str = str2;
                w5BleViewListener = w5BleViewListener2;
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable2) {
                W5BleUtils2.this.pollDisposable = disposable2;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                W5BleUtils2.this.bleConnectStatus(j, str, w5BleViewListener);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$2 */
    public class AnonymousClass2 implements Observer<Long> {
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ String val$mac;
        public final /* synthetic */ W5BleViewListener val$viewListener;

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public AnonymousClass2(long j2, String str2, W5BleViewListener w5BleViewListener2) {
            j = j2;
            str = str2;
            w5BleViewListener = w5BleViewListener2;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable2) {
            W5BleUtils2.this.pollDisposable = disposable2;
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            W5BleUtils2.this.bleConnectStatus(j, str, w5BleViewListener);
        }
    }

    public void bleConnectStatus(long j, String str, W5BleViewListener w5BleViewListener) {
        W5BleStateUtils w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(j));
        if (w5BleStateUtils == null) {
            return;
        }
        this.w5BleApiUtils.bleConnectStatus(j, str, new W5BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.3
            public final /* synthetic */ long val$deviceId;
            public final /* synthetic */ W5BleViewListener val$viewListener;
            public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;

            public AnonymousClass3(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, long j2) {
                w5BleViewListener = w5BleViewListener2;
                w5BleStateUtils = w5BleStateUtils2;
                j = j2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
            public void onResult(W5BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() != null) {
                    w5BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                    LogcatStorageHelper.addLog("bleConnectStatus error:" + bleRsp.getError().getMsg());
                    return;
                }
                String result = bleRsp.getResult();
                if (result.equals("1")) {
                    W5BleUtils2.this.disposable();
                    W5BleUtils2 w5BleUtils2 = W5BleUtils2.this;
                    W5BleStateUtils w5BleStateUtils2 = w5BleStateUtils;
                    long j2 = j;
                    W5BleStateUtils.RemoteBleStatus remoteBleStatus = W5BleStateUtils.RemoteBleStatus.connect_success;
                    w5BleUtils2.setBleStatus(w5BleStateUtils2, j2, remoteBleStatus);
                    w5BleViewListener.onBleStatus(remoteBleStatus);
                    return;
                }
                if (result.equals("-1")) {
                    W5BleUtils2.this.disposable();
                    W5BleUtils2 w5BleUtils22 = W5BleUtils2.this;
                    W5BleStateUtils w5BleStateUtils3 = w5BleStateUtils;
                    long j3 = j;
                    W5BleStateUtils.RemoteBleStatus remoteBleStatus2 = W5BleStateUtils.RemoteBleStatus.connect_timeout;
                    w5BleUtils22.setBleStatus(w5BleStateUtils3, j3, remoteBleStatus2);
                    w5BleViewListener.onBleStatus(remoteBleStatus2);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$3 */
    public class AnonymousClass3 implements W5BleApiUtils.OnBleListener {
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ W5BleViewListener val$viewListener;
        public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;

        public AnonymousClass3(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, long j2) {
            w5BleViewListener = w5BleViewListener2;
            w5BleStateUtils = w5BleStateUtils2;
            j = j2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
        public void onResult(W5BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() != null) {
                w5BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("bleConnectStatus error:" + bleRsp.getError().getMsg());
                return;
            }
            String result = bleRsp.getResult();
            if (result.equals("1")) {
                W5BleUtils2.this.disposable();
                W5BleUtils2 w5BleUtils2 = W5BleUtils2.this;
                W5BleStateUtils w5BleStateUtils2 = w5BleStateUtils;
                long j2 = j;
                W5BleStateUtils.RemoteBleStatus remoteBleStatus = W5BleStateUtils.RemoteBleStatus.connect_success;
                w5BleUtils2.setBleStatus(w5BleStateUtils2, j2, remoteBleStatus);
                w5BleViewListener.onBleStatus(remoteBleStatus);
                return;
            }
            if (result.equals("-1")) {
                W5BleUtils2.this.disposable();
                W5BleUtils2 w5BleUtils22 = W5BleUtils2.this;
                W5BleStateUtils w5BleStateUtils3 = w5BleStateUtils;
                long j3 = j;
                W5BleStateUtils.RemoteBleStatus remoteBleStatus2 = W5BleStateUtils.RemoteBleStatus.connect_timeout;
                w5BleUtils22.setBleStatus(w5BleStateUtils3, j3, remoteBleStatus2);
                w5BleViewListener.onBleStatus(remoteBleStatus2);
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

    public void checkBleStatus(W5Record w5Record, W5BleViewListener w5BleViewListener) {
        W5BleStateUtils w5BleStateUtils;
        if (w5Record == null || (w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(w5Record.getDeviceId()))) == null) {
            return;
        }
        w5BleViewListener.onBleStatus(w5BleStateUtils.getRemoteBleStatus());
    }

    public void cancelBle(long j, W5BleViewListener w5BleViewListener) {
        W5BleStateUtils w5BleStateUtils;
        W5Record w5RecordByDeviceId = W5Utils.getW5RecordByDeviceId(j);
        if (w5RecordByDeviceId == null || (w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(w5RecordByDeviceId.getDeviceId()))) == null) {
            return;
        }
        disposable();
        this.w5BleApiUtils.cancelBleConnect(w5RecordByDeviceId, new W5BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.4
            public final /* synthetic */ W5BleViewListener val$viewListener;
            public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;
            public final /* synthetic */ W5Record val$w5Record;

            public AnonymousClass4(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, W5Record w5RecordByDeviceId2) {
                w5BleViewListener = w5BleViewListener2;
                w5BleStateUtils = w5BleStateUtils2;
                w5Record = w5RecordByDeviceId2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
            public void onResult(W5BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() == null) {
                    W5BleUtils2.this.setBleStatus(w5BleStateUtils, w5Record.getDeviceId(), W5BleStateUtils.RemoteBleStatus.prepare);
                    w5BleViewListener.onBleStatus(w5BleStateUtils.getRemoteBleStatus());
                    return;
                }
                w5BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$4 */
    public class AnonymousClass4 implements W5BleApiUtils.OnBleListener {
        public final /* synthetic */ W5BleViewListener val$viewListener;
        public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;
        public final /* synthetic */ W5Record val$w5Record;

        public AnonymousClass4(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, W5Record w5RecordByDeviceId2) {
            w5BleViewListener = w5BleViewListener2;
            w5BleStateUtils = w5BleStateUtils2;
            w5Record = w5RecordByDeviceId2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
        public void onResult(W5BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() == null) {
                W5BleUtils2.this.setBleStatus(w5BleStateUtils, w5Record.getDeviceId(), W5BleStateUtils.RemoteBleStatus.prepare);
                w5BleViewListener.onBleStatus(w5BleStateUtils.getRemoteBleStatus());
                return;
            }
            w5BleViewListener.onApiFailed(bleRsp.getError().getMsg());
            LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
        }
    }

    public void cancelBle(W5Record w5Record, W5BleViewListener w5BleViewListener) {
        W5BleStateUtils w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(w5Record.getDeviceId()));
        if (w5BleStateUtils == null) {
            return;
        }
        disposable();
        this.w5BleApiUtils.cancelBleConnect(w5Record, new W5BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.5
            public final /* synthetic */ W5BleViewListener val$viewListener;
            public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;
            public final /* synthetic */ W5Record val$w5Record;

            public AnonymousClass5(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, W5Record w5Record2) {
                w5BleViewListener = w5BleViewListener2;
                w5BleStateUtils = w5BleStateUtils2;
                w5Record = w5Record2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
            public void onResult(W5BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() == null) {
                    W5BleUtils2.this.setBleStatus(w5BleStateUtils, w5Record.getDeviceId(), W5BleStateUtils.RemoteBleStatus.prepare);
                    w5BleViewListener.onBleStatus(w5BleStateUtils.getRemoteBleStatus());
                    return;
                }
                w5BleViewListener.onApiFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$5 */
    public class AnonymousClass5 implements W5BleApiUtils.OnBleListener {
        public final /* synthetic */ W5BleViewListener val$viewListener;
        public final /* synthetic */ W5BleStateUtils val$w5BleStateUtils;
        public final /* synthetic */ W5Record val$w5Record;

        public AnonymousClass5(W5BleViewListener w5BleViewListener2, W5BleStateUtils w5BleStateUtils2, W5Record w5Record2) {
            w5BleViewListener = w5BleViewListener2;
            w5BleStateUtils = w5BleStateUtils2;
            w5Record = w5Record2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
        public void onResult(W5BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() == null) {
                W5BleUtils2.this.setBleStatus(w5BleStateUtils, w5Record.getDeviceId(), W5BleStateUtils.RemoteBleStatus.prepare);
                w5BleViewListener.onBleStatus(w5BleStateUtils.getRemoteBleStatus());
                return;
            }
            w5BleViewListener.onApiFailed(bleRsp.getError().getMsg());
            LogcatStorageHelper.addLog("cancelBleConnect error:" + bleRsp.getError().getMsg());
        }
    }

    public boolean isConnected(W5Record w5Record) {
        W5BleStateUtils w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(w5Record.getDeviceId()));
        if (w5BleStateUtils == null) {
            return false;
        }
        return w5BleStateUtils.isRemoteBleConnected();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$6 */
    public class AnonymousClass6 implements W5BleApiUtils.OnBleListener {
        public final /* synthetic */ W5ControlBleListener val$controlBleListener;

        public AnonymousClass6(W5ControlBleListener w5ControlBleListener) {
            w5ControlBleListener = w5ControlBleListener;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
        public void onResult(W5BleApiUtils.BleRsp bleRsp) {
            if (bleRsp.getError() != null) {
                w5ControlBleListener.onControlFailed(bleRsp.getError().getMsg());
                LogcatStorageHelper.addLog("controlDevice error:" + bleRsp.getError().getMsg());
                return;
            }
            if (bleRsp.getResult().equals("1")) {
                w5ControlBleListener.closeLoading();
                w5ControlBleListener.onControlSend();
            } else {
                w5ControlBleListener.onControlFailed(bleRsp.getResult());
            }
        }
    }

    public void controlDevice(W5Record w5Record, PetkitBleData petkitBleData, W5ControlBleListener w5ControlBleListener) {
        this.w5BleApiUtils.bleControlDevice(w5Record.getDeviceId(), w5Record.getMac(), petkitBleData, new W5BleApiUtils.OnBleListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.6
            public final /* synthetic */ W5ControlBleListener val$controlBleListener;

            public AnonymousClass6(W5ControlBleListener w5ControlBleListener2) {
                w5ControlBleListener = w5ControlBleListener2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OnBleListener
            public void onResult(W5BleApiUtils.BleRsp bleRsp) {
                if (bleRsp.getError() != null) {
                    w5ControlBleListener.onControlFailed(bleRsp.getError().getMsg());
                    LogcatStorageHelper.addLog("controlDevice error:" + bleRsp.getError().getMsg());
                    return;
                }
                if (bleRsp.getResult().equals("1")) {
                    w5ControlBleListener.closeLoading();
                    w5ControlBleListener.onControlSend();
                } else {
                    w5ControlBleListener.onControlFailed(bleRsp.getResult());
                }
            }
        });
    }

    public void bleDisconnected(long j) {
        W5BleStateUtils w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(j));
        if (w5BleStateUtils == null) {
            return;
        }
        W5BleStateUtils.RemoteBleStatus remoteBleStatus = w5BleStateUtils.getRemoteBleStatus();
        if (remoteBleStatus == W5BleStateUtils.RemoteBleStatus.connect_success || remoteBleStatus == W5BleStateUtils.RemoteBleStatus.connecting) {
            setBleStatus(w5BleStateUtils, j, W5BleStateUtils.RemoteBleStatus.prepare);
        }
    }

    public void bleDisconnected(long j, W5BleViewListener w5BleViewListener) {
        W5BleStateUtils w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(j));
        if (w5BleStateUtils == null) {
            return;
        }
        W5BleStateUtils.RemoteBleStatus remoteBleStatus = w5BleStateUtils.getRemoteBleStatus();
        if (remoteBleStatus == W5BleStateUtils.RemoteBleStatus.connect_success || remoteBleStatus == W5BleStateUtils.RemoteBleStatus.connecting || remoteBleStatus == W5BleStateUtils.RemoteBleStatus.prepare) {
            W5BleStateUtils.RemoteBleStatus remoteBleStatus2 = W5BleStateUtils.RemoteBleStatus.prepare;
            setBleStatus(w5BleStateUtils, j, remoteBleStatus2);
            w5BleViewListener.onBleStatus(remoteBleStatus2);
        }
    }

    public void closeBle() {
        for (Map.Entry<Long, W5BleStateUtils> entry : this.w5BleStateUtilsMap.entrySet()) {
            if (entry.getValue().getRemoteBleStatus() == W5BleStateUtils.RemoteBleStatus.connect_success) {
                cancelBle(entry.getKey().longValue(), new W5BleViewListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.7
                    @Override // com.petkit.android.activities.petkitBleDevice.w5.mode.W5BleViewListener, com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BleViewListener
                    public void onApiFailed(String str) {
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.w5.mode.W5BleViewListener
                    public void onBleStatus(W5BleStateUtils.RemoteBleStatus remoteBleStatus) {
                    }

                    public AnonymousClass7() {
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$7 */
    public class AnonymousClass7 implements W5BleViewListener {
        @Override // com.petkit.android.activities.petkitBleDevice.w5.mode.W5BleViewListener, com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BleViewListener
        public void onApiFailed(String str) {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.mode.W5BleViewListener
        public void onBleStatus(W5BleStateUtils.RemoteBleStatus remoteBleStatus) {
        }

        public AnonymousClass7() {
        }
    }

    public final void setBleStatus(W5BleStateUtils w5BleStateUtils, long j, W5BleStateUtils.RemoteBleStatus remoteBleStatus) {
        w5BleStateUtils.setRemoteBleStatus(remoteBleStatus);
        PetkitLog.e("setBleStatus", "setBleStatus:deviceId=" + j + ",status:" + remoteBleStatus.name());
        LogcatStorageHelper.addLog("setBleStatus:deviceId=" + j + ",status:" + remoteBleStatus.name());
    }

    public void bleUpdate(W5Record w5Record) {
        this.w5BleApiUtils.bleUpdate(w5Record);
    }

    public void resetStatus(W5Record w5Record) {
        W5BleStateUtils w5BleStateUtils;
        if (w5Record == null || (w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(w5Record.getDeviceId()))) == null) {
            return;
        }
        if (w5BleStateUtils.getRemoteBleStatus() == W5BleStateUtils.RemoteBleStatus.connect_no_device || w5BleStateUtils.getRemoteBleStatus() == W5BleStateUtils.RemoteBleStatus.connect_timeout || w5BleStateUtils.getRemoteBleStatus() == W5BleStateUtils.RemoteBleStatus.connect_offline) {
            w5BleStateUtils.setRemoteBleStatus(W5BleStateUtils.RemoteBleStatus.prepare);
        }
    }

    public void destroy(W5Record w5Record) {
        W5BleStateUtils w5BleStateUtils;
        if (w5Record == null || (w5BleStateUtils = this.w5BleStateUtilsMap.get(Long.valueOf(w5Record.getDeviceId()))) == null) {
            return;
        }
        if (w5BleStateUtils.getRemoteBleStatus() == W5BleStateUtils.RemoteBleStatus.connecting) {
            disposable();
        }
        if (w5BleStateUtils.getRemoteBleStatus() != W5BleStateUtils.RemoteBleStatus.connect_success) {
            w5BleStateUtils.setRemoteBleStatus(W5BleStateUtils.RemoteBleStatus.prepare);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2$8 */
    public class AnonymousClass8 implements W5BleApiUtils.OwnSupportDeviceListener {
        public final /* synthetic */ BleDeviceListener val$listener;

        public AnonymousClass8(BleDeviceListener bleDeviceListener) {
            bleDeviceListener = bleDeviceListener;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OwnSupportDeviceListener
        public void onResult(W5BleApiUtils.OwnSupportDeviceRsp ownSupportDeviceRsp) {
            if (ownSupportDeviceRsp.getError() == null) {
                bleDeviceListener.onResult(ownSupportDeviceRsp.getResult() != null && ownSupportDeviceRsp.getResult().size() > 0);
            }
        }
    }

    public void ownBleDevice(BleDeviceListener bleDeviceListener) {
        this.w5BleApiUtils.ownSupportBleDevice(new W5BleApiUtils.OwnSupportDeviceListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2.8
            public final /* synthetic */ BleDeviceListener val$listener;

            public AnonymousClass8(BleDeviceListener bleDeviceListener2) {
                bleDeviceListener = bleDeviceListener2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleApiUtils.OwnSupportDeviceListener
            public void onResult(W5BleApiUtils.OwnSupportDeviceRsp ownSupportDeviceRsp) {
                if (ownSupportDeviceRsp.getError() == null) {
                    bleDeviceListener.onResult(ownSupportDeviceRsp.getResult() != null && ownSupportDeviceRsp.getResult().size() > 0);
                }
            }
        });
    }
}

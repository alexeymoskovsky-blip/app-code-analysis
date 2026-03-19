package com.petkit.android.activities.base.im.iot;

import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.PetkitLog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public class IotRefreshManager {
    public static volatile IotRefreshManager INSTANCE = null;
    public static final String SP_NEW_IOT_CONNECT = "SP_NEW_IOT_CONNECT";
    public Disposable disposable;
    public boolean mqttConnect;

    public interface PollingCallBack {
        void callBack();
    }

    public static IotRefreshManager getInstance() {
        if (INSTANCE == null) {
            synchronized (IotRefreshManager.class) {
                try {
                    if (INSTANCE == null) {
                        INSTANCE = new IotRefreshManager();
                    }
                } finally {
                }
            }
        }
        return INSTANCE;
    }

    public void polling(final PollingCallBack pollingCallBack, final String str) {
        stopDisposable();
        boolean zIsIotConnect = isIotConnect();
        this.mqttConnect = zIsIotConnect;
        int i = zIsIotConnect ? 30000 : 5000;
        PetkitLog.d("IotRefreshManager", "当前轮询的时间是：" + str + "，轮询的时长是：" + i);
        long j = (long) i;
        Observable.interval(j, j, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.base.im.iot.IotRefreshManager.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                IotRefreshManager.this.disposable = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                PollingCallBack pollingCallBack2 = pollingCallBack;
                if (pollingCallBack2 != null) {
                    pollingCallBack2.callBack();
                }
                boolean zIsIotConnect2 = IotRefreshManager.this.isIotConnect();
                if (IotRefreshManager.this.mqttConnect != zIsIotConnect2) {
                    IotRefreshManager.this.updateMqttPolling(zIsIotConnect2, pollingCallBack, str);
                }
            }
        });
    }

    public void stopDisposable() {
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }

    public void updateMqttPolling(boolean z, PollingCallBack pollingCallBack, String str) {
        if (z == this.mqttConnect) {
            return;
        }
        stopDisposable();
        polling(pollingCallBack, str);
    }

    public void setRefreshTimeInDisconnect(boolean z) {
        PetkitLog.d("IotRefreshManager", "Iot连接状态：" + z);
        CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), SP_NEW_IOT_CONNECT, z);
    }

    public boolean isIotConnect() {
        boolean sysBoolMap = CommonUtils.getSysBoolMap(CommonUtils.getAppContext(), SP_NEW_IOT_CONNECT, false);
        PetkitLog.d("IotRefreshManager", "获取Iot连接状态：" + sysBoolMap);
        return sysBoolMap;
    }
}

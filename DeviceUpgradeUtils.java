package com.petkit.android.activities.petkitBleDevice.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import androidx.appcompat.widget.ActivityChooserModel;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.cozy.mode.CozyDevice;
import com.petkit.android.activities.cozy.utils.CozyUtils;
import com.petkit.android.activities.d2.mode.D2Device;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.go.ApiResponse.GoListRsp;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.go.service.GoMainService;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqConfig;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqDevice;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.petkitBleDevice.aqh1.mode.Aqh1Device;
import com.petkit.android.activities.petkitBleDevice.aqh1.utils.Aqh1Utils;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrDevice;
import com.petkit.android.activities.petkitBleDevice.aqr.utils.AqrUtils;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Device;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3Utils;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Device;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Device;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sDevice;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDevice;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgDevice;
import com.petkit.android.activities.petkitBleDevice.hg.utils.HgUtils;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Device;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.mode.K2Device;
import com.petkit.android.activities.petkitBleDevice.mode.T3Device;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Device;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Device;
import com.petkit.android.activities.petkitBleDevice.r2.utils.R2Utils;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Device;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Device;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Device;
import com.petkit.android.activities.petkitBleDevice.t7.net.T7Service;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Device;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hDevice;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hDataUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.PetkitErrorHandleSubscriber;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.HsDeviceRsp;
import com.petkit.android.api.service.Aqh1Service;
import com.petkit.android.api.service.AqrService;
import com.petkit.android.api.service.BleDeviceService;
import com.petkit.android.api.service.CTW3Service;
import com.petkit.android.api.service.CozyService;
import com.petkit.android.api.service.D2Service;
import com.petkit.android.api.service.D4Service;
import com.petkit.android.api.service.D4hService;
import com.petkit.android.api.service.D4sService;
import com.petkit.android.api.service.D4shService;
import com.petkit.android.api.service.HgService;
import com.petkit.android.api.service.K3Service;
import com.petkit.android.api.service.P3Service;
import com.petkit.android.api.service.R2Service;
import com.petkit.android.api.service.T4Service;
import com.petkit.android.api.service.T5Service;
import com.petkit.android.api.service.T6Service;
import com.petkit.android.api.service.W5Service;
import com.petkit.android.api.service.W7hService;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.JSONUtils;
import com.petkit.android.utils.ThreadPoolManager;
import cz.msebera.android.httpclient.Header;
import io.agora.rtc2.internal.AudioDeviceInventoryLowerThanM;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes5.dex */
public class DeviceUpgradeUtils {
    public static void updateDeviceInfo(Context context, ArrayList<Integer> arrayList) {
        if (arrayList == null || TextUtils.isEmpty(CommonUtils.getCurrentUserId())) {
            return;
        }
        getMyHsDevices();
        getGoList(context);
        if (arrayList.contains(4)) {
            getFeederList();
        }
        if (arrayList.contains(5)) {
            getCozyList();
        }
        if (arrayList.contains(6)) {
            getD2List();
        }
        if (arrayList.contains(7)) {
            getT3List();
        }
        if (arrayList.contains(8)) {
            getK2List();
        }
        if (arrayList.contains(9)) {
            getD3List();
        }
        if (arrayList.contains(10)) {
            getAqList();
        }
        if (arrayList.contains(11)) {
            getD4List();
        }
        if (arrayList.contains(20)) {
            getD4sList();
        }
        if (arrayList.contains(12)) {
            getP3List();
        }
        if (arrayList.contains(14)) {
            getW5List();
        }
        if (arrayList.contains(15)) {
            getT4List();
        }
        if (arrayList.contains(16)) {
            getK3List();
        }
        if (arrayList.contains(17)) {
            getAqrList();
        }
        if (arrayList.contains(18)) {
            getR2List();
        }
        if (arrayList.contains(19)) {
            getAqh1List();
        }
        if (arrayList.contains(22)) {
            getHgList();
        }
        if (arrayList.contains(25)) {
            getD4shList();
        }
        if (arrayList.contains(26)) {
            getD4hList();
        }
        if (arrayList.contains(24)) {
            getCTW3List();
        }
        if (arrayList.contains(27)) {
            getT6List();
        }
        if (arrayList.contains(21)) {
            getT5List();
        }
        if (arrayList.contains(28)) {
            getT7List();
        }
        if (arrayList.contains(29)) {
            getW7hList();
        }
    }

    public static void getT5List() {
        ((T5Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(T5Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<T6Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.1
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<T6Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T6Utils.removeAllT5Record();
                        DeviceCenterUtils.setT6RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            T6Utils.storeT5RecordFromT5BindDevice((T6Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getT6List() {
        ((T6Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(T6Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<T6Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.2
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<T6Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T6Utils.removeAllT6Record();
                        DeviceCenterUtils.setT6RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            T6Utils.storeT6RecordFromT6BindDevice((T6Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getT7List() {
        ((T7Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(T7Service.class)).ownDevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<T7Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.3
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<T7Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T7DataUtils.getInstance().removeAllRecord();
                        DeviceCenterUtils.setT7RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            T7DataUtils.getInstance().storeT7RecordFromBindDevice((T7Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getW7hList() {
        ((W7hService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(W7hService.class)).ownDevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<W7hDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.4
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<W7hDevice> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        W7hDataUtils.getInstance().removeAllW7hRecord();
                        DeviceCenterUtils.setW7hRecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            W7hDataUtils.getInstance().storeW7hRecordFromW7hBindDevice((W7hDevice) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getCTW3List() {
        ((CTW3Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(CTW3Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<CTW3Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.5
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<CTW3Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CTW3Utils.removeAllCTW3Record();
                        DeviceCenterUtils.setCTW3RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            CTW3Utils.storeCTW3RecordFromCTW3BindDevice((CTW3Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getD4hList() {
        ((D4hService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4hService.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<D4shDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.6
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<D4shDevice> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        D4shUtils.removeAllD4hRecord();
                        DeviceCenterUtils.setD4shRecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        for (D4shDevice d4shDevice : list) {
                            d4shDevice.setTypeCode(1);
                            D4shUtils.storeD4shRecord(d4shDevice);
                        }
                    }
                });
            }
        });
    }

    public static void getHgList() {
        ((HgService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(HgService.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<HgDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.7
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<HgDevice> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        HgUtils.removeAllHgRecord();
                        DeviceCenterUtils.setHgRecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            HgUtils.storeHgRecordFromHgBindDevice((HgDevice) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getAqrList() {
        ((AqrService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(AqrService.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<AqrDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.8
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<AqrDevice> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AqrUtils.removeAllAqrRecord();
                        DeviceCenterUtils.setAqrRecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            AqrUtils.storeAqrRecordFromAqBindDevice((AqrDevice) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getK3List() {
        ((K3Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(K3Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<K3Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.9
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<K3Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        K3Utils.removeAllK3Record();
                        DeviceCenterUtils.setK3RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            K3Utils.storeK3RecordFromK3BindDevice((K3Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getT4List() {
        ((T4Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(T4Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<T4Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.10
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<T4Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T4Utils.removeAllT4Record();
                        DeviceCenterUtils.setT4RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            T4Utils.storeT4RecordFromT4BindDevice((T4Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getAqh1List() {
        ((Aqh1Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(Aqh1Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<Aqh1Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.11
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<Aqh1Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Aqh1Utils.removeAllAqh1Record();
                        DeviceCenterUtils.setT4RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            Aqh1Utils.storeAqh1RecordFromAqh1BindDevice((Aqh1Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getW5List() {
        ((W5Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(W5Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<W5Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.12
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<W5Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.12.1
                    @Override // java.lang.Runnable
                    public void run() {
                        W5Utils.removeAllW5Record();
                        DeviceCenterUtils.setW5RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            W5Utils.storeW5RecordFromW5BindDevice((W5Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getP3List() {
        ((P3Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(P3Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<P3Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.13
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<P3Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.13.1
                    @Override // java.lang.Runnable
                    public void run() {
                        P3Utils.removeAllP3Record();
                        DeviceCenterUtils.setP3RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            P3Utils.storeP3RecordFromP3BindDevice((P3Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getR2List() {
        ((R2Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(R2Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<R2Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.14
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<R2Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.14.1
                    @Override // java.lang.Runnable
                    public void run() {
                        R2Utils.removeAllR2Record();
                        DeviceCenterUtils.setR2RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            R2Utils.storeR2RecordFromP3BindDevice((R2Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getD4List() {
        ((D4Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4Service.class)).d4OwnDevice().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<D4Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.15
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<D4Device> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.15.1
                    @Override // java.lang.Runnable
                    public void run() {
                        D4Utils.removeAllD4Record();
                        DeviceCenterUtils.setD4RecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            D4Utils.storeD4RecordFromD4Device((D4Device) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getD4sList() {
        ((D4sService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4sService.class)).d4sOwnDevice().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<D4sDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.16
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<D4sDevice> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.16.1
                    @Override // java.lang.Runnable
                    public void run() {
                        D4sUtils.removeAllD4sRecord();
                        DeviceCenterUtils.setD4sRecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            D4sUtils.storeD4sRecord((D4sDevice) it.next());
                        }
                    }
                });
            }
        });
    }

    public static void getD4shList() {
        ((D4shService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4shService.class)).d4shOwnDevice().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<D4shDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.17
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<D4shDevice> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.17.1
                    @Override // java.lang.Runnable
                    public void run() {
                        D4shUtils.removeAllD4shRecord();
                        DeviceCenterUtils.setD4shRecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        for (D4shDevice d4shDevice : list) {
                            d4shDevice.setTypeCode(0);
                            D4shUtils.storeD4shRecord(d4shDevice);
                        }
                    }
                });
            }
        });
    }

    public static void getAqList() {
        ((BleDeviceService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(BleDeviceService.class)).aqOwnDevice().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<AqDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.18
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(final List<AqDevice> list) {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.18.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AqUtils.removeAllAqRecord();
                        DeviceCenterUtils.setAqRecordUpdate(true);
                        List list2 = list;
                        if (list2 == null || list2.size() <= 0) {
                            return;
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            AqUtils.storeAqRecordFromAqBindDevice((AqDevice) it.next());
                            DeviceUpgradeUtils.getAqConfig(r1.getId());
                        }
                    }
                });
            }
        });
    }

    public static void getAqConfig(final long j) {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(j));
        ((BleDeviceService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(BleDeviceService.class)).aqGetConfig(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<AqConfig>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.19
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(AqConfig aqConfig) {
                AqRecord orCreateAqRecordByDeviceId = AqUtils.getOrCreateAqRecordByDeviceId(j);
                orCreateAqRecordByDeviceId.setConfig(aqConfig);
                orCreateAqRecordByDeviceId.save();
            }
        });
    }

    public static void getMyHsDevices() {
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_HS_MYDEVICES2, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.20
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                HsDeviceRsp hsDeviceRsp = (HsDeviceRsp) this.gson.fromJson(this.responseResult, HsDeviceRsp.class);
                if (hsDeviceRsp.getResult() != null) {
                    HsConsts.addDeviceList(CommonUtils.getAppContext(), hsDeviceRsp.getResult());
                    DeviceCenterUtils.setMateRecordUpdated();
                }
            }
        }, false);
    }

    public static boolean isBackground(Context context) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(ActivityChooserModel.ATTRIBUTE_ACTIVITY)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(context.getPackageName())) {
                return runningAppProcessInfo.importance != 100;
            }
        }
        return false;
    }

    public static void getGoList(final Context context) {
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_GO_OWN_DEVICES, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.21
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                GoListRsp goListRsp = (GoListRsp) this.gson.fromJson(this.responseResult, GoListRsp.class);
                GoDataUtils.removeAllGoRecord();
                if (goListRsp.getResult() == null || goListRsp.getResult().size() <= 0) {
                    return;
                }
                GoDataUtils.storeGoListInfo(context, goListRsp.getResult());
                DeviceCenterUtils.setGoRecordUpdate(true);
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 31) {
                    if (CommonUtils.checkPermission((Activity) context, "android.permission.ACCESS_FINE_LOCATION") && CommonUtils.checkPermission((Activity) context, "android.permission.BLUETOOTH_SCAN") && CommonUtils.checkPermission((Activity) context, AudioDeviceInventoryLowerThanM.PERMISSION_BLUETOOTH_CONNECT) && CommonUtils.checkPermission((Activity) context, "android.permission.BLUETOOTH_ADVERTISE")) {
                        GoWalkData goWalkDataByState = GoDataUtils.getGoWalkDataByState(1);
                        if (CommonUtils.isRunningForeground()) {
                            if (i2 >= 26 && goWalkDataByState != null) {
                                if (i2 < 31 || !DeviceUpgradeUtils.isBackground(context)) {
                                    context.startForegroundService(new Intent(context, (Class<?>) GoMainService.class));
                                }
                            } else {
                                context.startService(new Intent(context, (Class<?>) GoMainService.class));
                            }
                        }
                    }
                } else {
                    GoWalkData goWalkDataByState2 = GoDataUtils.getGoWalkDataByState(1);
                    if (i2 >= 26 && goWalkDataByState2 != null) {
                        if (i2 < 31 || !DeviceUpgradeUtils.isBackground(context)) {
                            context.startForegroundService(new Intent(context, (Class<?>) GoMainService.class));
                        }
                    } else {
                        context.startService(new Intent(context, (Class<?>) GoMainService.class));
                    }
                }
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(GoDataUtils.BROADCAST_GO_WALKING_STATE_CHANGED));
            }
        }, false);
    }

    public static void getFeederList() {
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_OWN_DEVICES, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.22
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (((BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class)).getError() == null) {
                    try {
                        FeederUtils.removeAllFeederRecord();
                        JSONArray jSONArray = JSONUtils.getJSONObject(this.responseResult).getJSONArray("result");
                        if (jSONArray.length() > 0) {
                            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                FeederUtils.storeFeederRecordFromJson(jSONArray.getJSONObject(i2));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DeviceCenterUtils.setFeederRecordUpdated(true);
                    LocalBroadcastManager.getInstance(CommonUtils.getAppContext()).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
                }
            }
        }, false);
    }

    public static void getD2List() {
        ((D2Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D2Service.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<D2Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.23
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(List<D2Device> list) {
                D2Utils.removeAllD2Device();
                DeviceCenterUtils.setD2RecordUpdated(true);
                if (list == null || list.size() <= 0) {
                    return;
                }
                Iterator<D2Device> it = list.iterator();
                while (it.hasNext()) {
                    D2Utils.storeD2RecordFromD2Device(it.next());
                }
            }
        });
    }

    public static void getCozyList() {
        ((CozyService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(CozyService.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<CozyDevice>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.24
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(List<CozyDevice> list) {
                CozyUtils.removeAllCozyRecord();
                DeviceCenterUtils.setCozyRecordUpdated(true);
                if (list == null || list.size() <= 0) {
                    return;
                }
                Iterator<CozyDevice> it = list.iterator();
                while (it.hasNext()) {
                    CozyUtils.storeCozyRecordFromCozyDevice(it.next());
                }
            }
        });
    }

    public static void getT3List() {
        ((BleDeviceService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(BleDeviceService.class)).owndevices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<T3Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.25
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(List<T3Device> list) {
                BleDeviceUtils.removeAllT3Device();
                DeviceCenterUtils.setT3RecordUpdate(true);
                if (list == null || list.size() <= 0) {
                    return;
                }
                Iterator<T3Device> it = list.iterator();
                while (it.hasNext()) {
                    BleDeviceUtils.storeT3RecordFromT3Device(it.next());
                }
            }
        });
    }

    public static void getK2List() {
        ((BleDeviceService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(BleDeviceService.class)).ownK2devices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<K2Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.26
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(List<K2Device> list) {
                BleDeviceUtils.removeAllK2Device();
                DeviceCenterUtils.setK2RecordUpdate(true);
                if (list == null || list.size() <= 0) {
                    return;
                }
                Iterator<K2Device> it = list.iterator();
                while (it.hasNext()) {
                    BleDeviceUtils.storeK2RecordFromK2Device(it.next());
                }
            }
        });
    }

    public static void getD3List() {
        ((BleDeviceService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(BleDeviceService.class)).ownD3devices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<List<D3Device>>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils.27
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(List<D3Device> list) {
                D3Utils.removeAllD3Device();
                DeviceCenterUtils.setD3RecordUpdate(true);
                if (list == null || list.size() <= 0) {
                    return;
                }
                Iterator<D3Device> it = list.iterator();
                while (it.hasNext()) {
                    D3Utils.storeD3RecordFromD3Device(it.next());
                }
            }
        });
    }
}

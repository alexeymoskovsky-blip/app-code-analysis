package com.petkit.android.activities.petkitBleDevice.ctw3.utils;

import com.alibaba.fastjson.JSONObject;
import com.jess.arms.utils.Consts;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleData;
import com.petkit.android.activities.petkitBleDevice.ble.mode.PetkitBleMsg;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Record;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.w5.mode.BleDevice;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.PetkitErrorHandleSubscriber;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.IRepository;
import com.petkit.android.api.service.BleDeviceService;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.PetkitLog;
import com.tencent.connect.common.Constants;
import cz.msebera.android.httpclient.Header;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
public class CTW3BleApiUtils implements IRepository {
    public AtomicInteger mSendSequence;

    public interface OnBleListener {
        void onResult(BleRsp bleRsp);
    }

    public interface OnConnectListener {
        void onResult(BleConnectStartRsp bleConnectStartRsp);
    }

    public interface OwnSupportDeviceListener {
        void onResult(OwnSupportDeviceRsp ownSupportDeviceRsp);
    }

    public CTW3BleApiUtils() {
        this.mSendSequence = new AtomicInteger(0);
    }

    public static class CTW3bleApiUtilsHolder {
        public static CTW3BleApiUtils instance = new CTW3BleApiUtils();
    }

    public static CTW3BleApiUtils getInstance() {
        return CTW3bleApiUtilsHolder.instance;
    }

    public void bleConnect(long j, String str, final OnConnectListener onConnectListener) {
        if (CommonUtils.getSysMap(IRepository.context, Consts.SHARED_SESSION_ID, "").isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("bleId", "" + j);
        map.put("type", Constants.VIA_REPORT_TYPE_CHAT_AIO);
        map.put("mac", str);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_BLE_CONNECT_START, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                onConnectListener.onResult((BleConnectStartRsp) this.gson.fromJson(this.responseResult, BleConnectStartRsp.class));
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }
        }, false);
    }

    public void bleConnectStatus(long j, String str, final OnBleListener onBleListener) {
        HashMap map = new HashMap();
        map.put("bleId", "" + j);
        map.put("type", Constants.VIA_REPORT_TYPE_CHAT_AIO);
        map.put("mac", str);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_BLE_CONNECT_POLL, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                onBleListener.onResult((BleRsp) this.gson.fromJson(this.responseResult, BleRsp.class));
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }
        }, false);
    }

    public void cancelBleConnect(CTW3Record cTW3Record, final OnBleListener onBleListener) {
        HashMap map = new HashMap();
        map.put("bleId", "" + cTW3Record.getDeviceId());
        map.put("type", Constants.VIA_REPORT_TYPE_CHAT_AIO);
        map.put("mac", cTW3Record.getMac());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_BLE_CONNECT_CANCEL, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                onBleListener.onResult((BleRsp) this.gson.fromJson(this.responseResult, BleRsp.class));
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }
        }, false);
    }

    public void bleUpdate(CTW3Record cTW3Record) {
        HashMap map = new HashMap();
        map.put("bleId", "" + cTW3Record.getDeviceId());
        map.put("type", Constants.VIA_REPORT_TYPE_CHAT_AIO);
        map.put("mac", cTW3Record.getMac());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_BLE_UPDATE, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }
        }, false);
    }

    public void bleControlDevice(long j, String str, PetkitBleData petkitBleData, final OnBleListener onBleListener) {
        HashMap map = new HashMap();
        map.put("bleId", "" + j);
        map.put("type", Constants.VIA_REPORT_TYPE_CHAT_AIO);
        map.put("mac", str);
        PetkitBleMsg petkitBleMsg = petkitBleData.getBleMsgList().get(0);
        if (petkitBleMsg != null) {
            map.put("cmd", "" + petkitBleMsg.getCmd());
            if ((1 == petkitBleMsg.getType() || petkitBleMsg.getType() == 0) && petkitBleMsg.getCmd() > 0) {
                petkitBleMsg.setSequence(generateSendSequence());
            }
            try {
                PetkitLog.d("bleControlDevice", "bleMsg cmd:" + petkitBleMsg.getCmd() + "data:" + ByteUtil.toHex(petkitBleMsg.getData()));
                map.put("data", CommonUtils.bytesToString(petkitBleMsg.toRawDataBytes()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        ((BleDeviceService) IRepository.repositoryManager.obtainRetrofitService(BleDeviceService.class)).bleControlDevice(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<String>(IRepository.mErrorHandler) { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.5
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(String str2) {
                BleRsp bleRsp = CTW3BleApiUtils.this.new BleRsp();
                bleRsp.setResult(str2);
                onBleListener.onResult(bleRsp);
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
                BleRsp bleRsp = CTW3BleApiUtils.this.new BleRsp();
                bleRsp.setError(errorInfor);
                onBleListener.onResult(bleRsp);
            }
        });
    }

    public void ownSupportBleDevice(final OwnSupportDeviceListener ownSupportDeviceListener) {
        HashMap map = new HashMap();
        map.put(Consts.PET_GROUP_ID, String.valueOf(FamilyUtils.getInstance().getCurrentFamilyId(IRepository.context)));
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_BLE_QUERY_SUPPORT_DEVICE, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleApiUtils.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                OwnSupportDeviceRsp ownSupportDeviceRsp = (OwnSupportDeviceRsp) this.gson.fromJson(this.responseResult, OwnSupportDeviceRsp.class);
                OwnSupportDeviceListener ownSupportDeviceListener2 = ownSupportDeviceListener;
                if (ownSupportDeviceListener2 != null) {
                    ownSupportDeviceListener2.onResult(ownSupportDeviceRsp);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }
        }, false);
    }

    public final int generateSendSequence() {
        return this.mSendSequence.getAndIncrement() % 256;
    }

    public class BleConnectStartRsp extends BaseRsp {
        public JSONObject result;

        public BleConnectStartRsp() {
        }

        public JSONObject getResult() {
            return this.result;
        }

        public void setResult(JSONObject jSONObject) {
            this.result = jSONObject;
        }
    }

    public class BleRsp extends BaseRsp {
        public String result;

        public BleRsp() {
        }

        public String getResult() {
            return this.result;
        }

        public void setResult(String str) {
            this.result = str;
        }
    }

    public class OwnSupportDeviceRsp extends BaseRsp {
        public List<BleDevice> result;

        public OwnSupportDeviceRsp() {
        }

        public List<BleDevice> getResult() {
            return this.result;
        }

        public void setResult(List<BleDevice> list) {
            this.result = list;
        }
    }
}

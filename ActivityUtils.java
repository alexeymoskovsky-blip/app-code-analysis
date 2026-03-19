package com.petkit.android.activities.petkitBleDevice.utils;

import android.app.Activity;
import android.content.Intent;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.cozy.CozyHomeActivity;
import com.petkit.android.activities.cozy.mode.CozyRecord;
import com.petkit.android.activities.cozy.utils.CozyUtils;
import com.petkit.android.activities.d2.D2HomeActivity;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.feeder.FeederHomeActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.home.ShareDeviceActivity;
import com.petkit.android.activities.petkitBleDevice.K2HomeActivity;
import com.petkit.android.activities.petkitBleDevice.T3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.aq.AqHomeActivity;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.petkitBleDevice.aqh1.Aqh1HomeActivity;
import com.petkit.android.activities.petkitBleDevice.aqh1.mode.Aqh1Record;
import com.petkit.android.activities.petkitBleDevice.aqh1.utils.Aqh1Utils;
import com.petkit.android.activities.petkitBleDevice.d3.D3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d4.D4HomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.D4sHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.hg.HgHomeActivity;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgRecord;
import com.petkit.android.activities.petkitBleDevice.hg.utils.HgUtils;
import com.petkit.android.activities.petkitBleDevice.k3.K3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Record;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.mode.K2Record;
import com.petkit.android.activities.petkitBleDevice.mode.T3Record;
import com.petkit.android.activities.petkitBleDevice.p3.P3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.r2.R2HomeActivity;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Record;
import com.petkit.android.activities.petkitBleDevice.r2.utils.R2Utils;
import com.petkit.android.activities.petkitBleDevice.t4.T4HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.w5.W5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.JSONUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes5.dex */
public class ActivityUtils {
    public WeakReference<Activity> sCurrentActivityWeakRef;

    public interface GetDetailResultListener {
        void getDetailFail();

        void getDetailSuccess();
    }

    public /* synthetic */ ActivityUtils(AnonymousClass1 anonymousClass1) {
        this();
    }

    public ActivityUtils() {
    }

    public static class IconUtilsInstance {
        public static final ActivityUtils instance = new ActivityUtils();
    }

    public static ActivityUtils getInstance() {
        return IconUtilsInstance.instance;
    }

    public Activity getCurrentActivity() {
        WeakReference<Activity> weakReference = this.sCurrentActivityWeakRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public void setCurrentActivity(Activity activity) {
        this.sCurrentActivityWeakRef = new WeakReference<>(activity);
    }

    public void getDeviceDetailAndEnterHomeView(Activity activity, long j, int i) {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(j));
        switch (i) {
            case 4:
                if (FeederUtils.getFeederRecordByDeviceId(j) != null) {
                    Intent intent = new Intent(activity, (Class<?>) FeederHomeActivity.class);
                    intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
                    activity.startActivity(intent);
                } else {
                    HashMap map2 = new HashMap();
                    map2.put("id", String.valueOf(j));
                    AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_DETAIL, map2, new AsyncHttpRespHandler(activity) { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.15
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass15(Activity activity2, Activity activity22, long j2) {
                            super(activity22);
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                        public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                            super.onSuccess(i2, headerArr, bArr);
                            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                            if (baseRsp.getError() != null) {
                                EventBus.getDefault().post("", "feederShareError");
                                PetkitToast.showShortToast(activity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                                return;
                            }
                            try {
                                JSONObject jSONObject = JSONUtils.getJSONObject(this.responseResult).getJSONObject("result");
                                if (jSONObject != null) {
                                    FeederUtils.storeFeederRecordFromJson(jSONObject);
                                    DeviceCenterUtils.setFeederRecordUpdated(true);
                                    Intent intent2 = new Intent(activity, (Class<?>) FeederHomeActivity.class);
                                    intent2.putExtra(Constants.EXTRA_DEVICE_ID, j);
                                    activity.startActivity(intent2);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case 5:
                if (CozyUtils.getCozyRecordByDeviceId(j2) != null) {
                    Intent intent2 = new Intent(activity22, (Class<?>) CozyHomeActivity.class);
                    intent2.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().cozyDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<CozyRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.16
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass16(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(CozyRecord cozyRecord) {
                            cozyRecord.save();
                            Intent intent3 = new Intent(activity, (Class<?>) CozyHomeActivity.class);
                            intent3.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent3);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "CozyShareError");
                        }
                    });
                }
                break;
            case 6:
                if (D2Utils.getD2RecordByDeviceId(j2) != null) {
                    Intent intent3 = new Intent(activity22, (Class<?>) D2HomeActivity.class);
                    intent3.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent3);
                } else {
                    WebModelRepository.getInstance().d2DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D2Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.14
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass14(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D2Record d2Record) {
                            d2Record.save();
                            Intent intent4 = new Intent(activity, (Class<?>) D2HomeActivity.class);
                            intent4.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent4);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D2ShareError");
                        }
                    });
                }
                break;
            case 7:
                if (BleDeviceUtils.getT3RecordByDeviceId(j2) != null) {
                    Intent intent4 = new Intent(activity22, (Class<?>) T3HomeActivity.class);
                    intent4.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent4);
                } else {
                    WebModelRepository.getInstance().t3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.11
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass11(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T3Record t3Record) {
                            t3Record.save();
                            Intent intent5 = new Intent(activity, (Class<?>) T3HomeActivity.class);
                            intent5.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent5);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T3ShareError");
                        }
                    });
                }
                break;
            case 8:
                if (BleDeviceUtils.getK2RecordByDeviceId(j2) != null) {
                    Intent intent5 = new Intent(activity22, (Class<?>) K2HomeActivity.class);
                    intent5.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent5);
                } else {
                    WebModelRepository.getInstance().k2DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<K2Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.10
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass10(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(K2Record k2Record) {
                            k2Record.save();
                            Intent intent6 = new Intent(activity, (Class<?>) K2HomeActivity.class);
                            intent6.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent6);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "K2ShareError");
                        }
                    });
                }
                break;
            case 9:
                if (D3Utils.getD3RecordByDeviceId(j2) != null) {
                    Intent intent6 = new Intent(activity22, (Class<?>) D3HomeActivity.class);
                    intent6.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent6);
                } else {
                    WebModelRepository.getInstance().d3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.8
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass8(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D3Record d3Record) {
                            d3Record.save();
                            Intent intent7 = new Intent(activity, (Class<?>) D3HomeActivity.class);
                            intent7.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent7);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D3ShareError");
                        }
                    });
                }
                break;
            case 10:
                if (AqUtils.getAqRecordByDeviceId(j2) != null) {
                    Intent intent7 = new Intent(activity22, (Class<?>) AqHomeActivity.class);
                    intent7.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent7);
                } else {
                    WebModelRepository.getInstance().aqDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<AqRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.9
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass9(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(AqRecord aqRecord) {
                            aqRecord.save();
                            Intent intent8 = new Intent(activity, (Class<?>) AqHomeActivity.class);
                            intent8.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent8);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "AqShareError");
                        }
                    });
                }
                break;
            case 11:
                if (D4Utils.getD4RecordByDeviceId(j2) != null) {
                    Intent intent8 = new Intent(activity22, (Class<?>) D4HomeActivity.class);
                    intent8.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent8);
                } else {
                    WebModelRepository.getInstance().d4DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.7
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass7(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4Record d4Record) {
                            d4Record.save();
                            Intent intent9 = new Intent(activity, (Class<?>) D4HomeActivity.class);
                            intent9.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent9);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4ShareError");
                        }
                    });
                }
                break;
            case 12:
                if (P3Utils.getP3RecordByDeviceId(j2) != null) {
                    Intent intent9 = new Intent(activity22, (Class<?>) P3HomeActivity.class);
                    intent9.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent9);
                } else {
                    WebModelRepository.getInstance().p3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<P3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.6
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass6(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(P3Record p3Record) {
                            p3Record.save();
                            Intent intent10 = new Intent(activity, (Class<?>) P3HomeActivity.class);
                            intent10.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent10);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "P3ShareError");
                        }
                    });
                }
                break;
            case 14:
                if (W5Utils.getW5RecordByDeviceId(j2) != null) {
                    Intent intent10 = new Intent(activity22, (Class<?>) W5HomeActivity.class);
                    intent10.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent10);
                } else {
                    WebModelRepository.getInstance().w5DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<W5Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.5
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass5(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(W5Record w5Record) {
                            w5Record.save();
                            Intent intent11 = new Intent(activity, (Class<?>) W5HomeActivity.class);
                            intent11.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent11);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "W5ShareError");
                        }
                    });
                }
                break;
            case 15:
                if (T4Utils.getT4RecordByDeviceId(j2) != null) {
                    Intent intent11 = new Intent(activity22, (Class<?>) T4HomeActivity.class);
                    intent11.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent11);
                } else {
                    WebModelRepository.getInstance().t4DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T4Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.3
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass3(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T4Record t4Record) {
                            t4Record.save();
                            Intent intent12 = new Intent(activity, (Class<?>) T4HomeActivity.class);
                            intent12.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent12);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T4ShareError");
                        }
                    });
                }
                break;
            case 16:
                if (K3Utils.getK3RecordByDeviceId(j2) != null) {
                    Intent intent12 = new Intent(activity22, (Class<?>) K3HomeActivity.class);
                    intent12.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent12);
                } else {
                    WebModelRepository.getInstance().k3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<K3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.4
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass4(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(K3Record k3Record) {
                            k3Record.save();
                            Intent intent13 = new Intent(activity, (Class<?>) K3HomeActivity.class);
                            intent13.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent13);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "K3ShareError");
                        }
                    });
                }
                break;
            case 18:
                if (R2Utils.getR2RecordByDeviceId(j2) != null) {
                    Intent intent13 = new Intent(activity22, (Class<?>) R2HomeActivity.class);
                    intent13.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent13);
                } else {
                    WebModelRepository.getInstance().r2DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<R2Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.1
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass1(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(R2Record r2Record) {
                            r2Record.save();
                            Intent intent14 = new Intent(activity, (Class<?>) R2HomeActivity.class);
                            intent14.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent14);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "R2ShareError");
                        }
                    });
                }
                break;
            case 19:
                if (Aqh1Utils.getAqh1RecordByDeviceId(j2) != null) {
                    Intent intent14 = new Intent(activity22, (Class<?>) Aqh1HomeActivity.class);
                    intent14.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent14);
                } else {
                    WebModelRepository.getInstance().aqh1DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<Aqh1Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.12
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass12(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(Aqh1Record aqh1Record) {
                            aqh1Record.save();
                            Intent intent15 = new Intent(activity, (Class<?>) Aqh1HomeActivity.class);
                            intent15.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent15);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "Aqh1ShareError");
                        }
                    });
                }
                break;
            case 20:
                if (D4sUtils.getD4sRecordByDeviceId(j2) != null) {
                    Intent intent15 = new Intent(activity22, (Class<?>) D4sHomeActivity.class);
                    intent15.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent15);
                } else {
                    WebModelRepository.getInstance().d4sDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4sRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.13
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass13(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4sRecord d4sRecord) {
                            d4sRecord.save();
                            Intent intent16 = new Intent(activity, (Class<?>) D4sHomeActivity.class);
                            intent16.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent16);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4sShareError");
                        }
                    });
                }
                break;
            case 21:
                if (T6Utils.getT6RecordByDeviceId(j2, 1) != null) {
                    Intent intent16 = new Intent(activity22, (Class<?>) T5HomeActivity.class);
                    intent16.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    intent16.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
                    activity22.startActivity(intent16);
                } else {
                    WebModelRepository.getInstance().t5DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T6Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.20
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass20(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T6Record t6Record) {
                            t6Record.saveData(1);
                            Intent intent17 = new Intent(activity, (Class<?>) T5HomeActivity.class);
                            intent17.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            intent17.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
                            activity.startActivity(intent17);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T5ShareError");
                        }
                    });
                }
                break;
            case 22:
                if (HgUtils.getHgRecordByDeviceId(j2) != null) {
                    Intent intent17 = new Intent(activity22, (Class<?>) HgHomeActivity.class);
                    intent17.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    activity22.startActivity(intent17);
                } else {
                    WebModelRepository.getInstance().hgDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<HgRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.2
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass2(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(HgRecord hgRecord) {
                            hgRecord.save();
                            Intent intent18 = new Intent(activity, (Class<?>) HgHomeActivity.class);
                            intent18.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            activity.startActivity(intent18);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "HgShareError");
                        }
                    });
                }
                break;
            case 25:
                if (D4shUtils.getD4shRecordByDeviceId(j2, 0) != null) {
                    Intent intent18 = new Intent(activity22, (Class<?>) D4shHomeActivity.class);
                    intent18.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    intent18.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
                    activity22.startActivity(intent18);
                } else {
                    WebModelRepository.getInstance().d4shDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4shRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.18
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass18(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4shRecord d4shRecord) {
                            d4shRecord.setTypeCode(0);
                            d4shRecord.save();
                            Intent intent19 = new Intent(activity, (Class<?>) D4shHomeActivity.class);
                            intent19.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            intent19.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
                            activity.startActivity(intent19);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4shShareError");
                        }
                    });
                }
                break;
            case 26:
                if (D4shUtils.getD4shRecordByDeviceId(j2, 1) != null) {
                    Intent intent19 = new Intent(activity22, (Class<?>) D4shHomeActivity.class);
                    intent19.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    intent19.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
                    activity22.startActivity(intent19);
                } else {
                    WebModelRepository.getInstance().d4hDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4shRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.17
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass17(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4shRecord d4shRecord) {
                            d4shRecord.setTypeCode(1);
                            d4shRecord.save();
                            Intent intent20 = new Intent(activity, (Class<?>) D4shHomeActivity.class);
                            intent20.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            intent20.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
                            activity.startActivity(intent20);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4shShareError");
                        }
                    });
                }
                break;
            case 27:
                if (T6Utils.getT6RecordByDeviceId(j2, 0) != null) {
                    Intent intent20 = new Intent(activity22, (Class<?>) T6HomeActivity.class);
                    intent20.putExtra(Constants.EXTRA_DEVICE_ID, j2);
                    intent20.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
                    activity22.startActivity(intent20);
                } else {
                    WebModelRepository.getInstance().t6DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T6Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.19
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ long val$deviceId;

                        public AnonymousClass19(Activity activity22, long j2) {
                            activity = activity22;
                            j = j2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T6Record t6Record) {
                            t6Record.saveData(0);
                            Intent intent21 = new Intent(activity, (Class<?>) T6HomeActivity.class);
                            intent21.putExtra(Constants.EXTRA_DEVICE_ID, j);
                            intent21.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
                            activity.startActivity(intent21);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T6ShareError");
                        }
                    });
                }
                break;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$1 */
    public class AnonymousClass1 implements PetkitCallback<R2Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass1(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(R2Record r2Record) {
            r2Record.save();
            Intent intent14 = new Intent(activity, (Class<?>) R2HomeActivity.class);
            intent14.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent14);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "R2ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$2 */
    public class AnonymousClass2 implements PetkitCallback<HgRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass2(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(HgRecord hgRecord) {
            hgRecord.save();
            Intent intent18 = new Intent(activity, (Class<?>) HgHomeActivity.class);
            intent18.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent18);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "HgShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$3 */
    public class AnonymousClass3 implements PetkitCallback<T4Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass3(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T4Record t4Record) {
            t4Record.save();
            Intent intent12 = new Intent(activity, (Class<?>) T4HomeActivity.class);
            intent12.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent12);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T4ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$4 */
    public class AnonymousClass4 implements PetkitCallback<K3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass4(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(K3Record k3Record) {
            k3Record.save();
            Intent intent13 = new Intent(activity, (Class<?>) K3HomeActivity.class);
            intent13.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent13);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "K3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$5 */
    public class AnonymousClass5 implements PetkitCallback<W5Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass5(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(W5Record w5Record) {
            w5Record.save();
            Intent intent11 = new Intent(activity, (Class<?>) W5HomeActivity.class);
            intent11.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent11);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "W5ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$6 */
    public class AnonymousClass6 implements PetkitCallback<P3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass6(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(P3Record p3Record) {
            p3Record.save();
            Intent intent10 = new Intent(activity, (Class<?>) P3HomeActivity.class);
            intent10.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent10);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "P3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$7 */
    public class AnonymousClass7 implements PetkitCallback<D4Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass7(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4Record d4Record) {
            d4Record.save();
            Intent intent9 = new Intent(activity, (Class<?>) D4HomeActivity.class);
            intent9.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent9);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$8 */
    public class AnonymousClass8 implements PetkitCallback<D3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass8(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D3Record d3Record) {
            d3Record.save();
            Intent intent7 = new Intent(activity, (Class<?>) D3HomeActivity.class);
            intent7.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent7);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$9 */
    public class AnonymousClass9 implements PetkitCallback<AqRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass9(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqRecord aqRecord) {
            aqRecord.save();
            Intent intent8 = new Intent(activity, (Class<?>) AqHomeActivity.class);
            intent8.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent8);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "AqShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$10 */
    public class AnonymousClass10 implements PetkitCallback<K2Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass10(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(K2Record k2Record) {
            k2Record.save();
            Intent intent6 = new Intent(activity, (Class<?>) K2HomeActivity.class);
            intent6.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent6);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "K2ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$11 */
    public class AnonymousClass11 implements PetkitCallback<T3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass11(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T3Record t3Record) {
            t3Record.save();
            Intent intent5 = new Intent(activity, (Class<?>) T3HomeActivity.class);
            intent5.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent5);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$12 */
    public class AnonymousClass12 implements PetkitCallback<Aqh1Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass12(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(Aqh1Record aqh1Record) {
            aqh1Record.save();
            Intent intent15 = new Intent(activity, (Class<?>) Aqh1HomeActivity.class);
            intent15.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent15);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "Aqh1ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$13 */
    public class AnonymousClass13 implements PetkitCallback<D4sRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass13(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4sRecord d4sRecord) {
            d4sRecord.save();
            Intent intent16 = new Intent(activity, (Class<?>) D4sHomeActivity.class);
            intent16.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent16);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4sShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$14 */
    public class AnonymousClass14 implements PetkitCallback<D2Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass14(Activity activity22, long j2) {
            activity = activity22;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D2Record d2Record) {
            d2Record.save();
            Intent intent4 = new Intent(activity, (Class<?>) D2HomeActivity.class);
            intent4.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent4);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D2ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$15 */
    public class AnonymousClass15 extends AsyncHttpRespHandler {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass15(Activity activity22, Activity activity222, long j2) {
            super(activity222);
            activity = activity222;
            j = j2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i2, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                EventBus.getDefault().post("", "feederShareError");
                PetkitToast.showShortToast(activity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            try {
                JSONObject jSONObject = JSONUtils.getJSONObject(this.responseResult).getJSONObject("result");
                if (jSONObject != null) {
                    FeederUtils.storeFeederRecordFromJson(jSONObject);
                    DeviceCenterUtils.setFeederRecordUpdated(true);
                    Intent intent2 = new Intent(activity, (Class<?>) FeederHomeActivity.class);
                    intent2.putExtra(Constants.EXTRA_DEVICE_ID, j);
                    activity.startActivity(intent2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$16 */
    public class AnonymousClass16 implements PetkitCallback<CozyRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass16(Activity activity222, long j2) {
            activity = activity222;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(CozyRecord cozyRecord) {
            cozyRecord.save();
            Intent intent3 = new Intent(activity, (Class<?>) CozyHomeActivity.class);
            intent3.putExtra(Constants.EXTRA_DEVICE_ID, j);
            activity.startActivity(intent3);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "CozyShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$17 */
    public class AnonymousClass17 implements PetkitCallback<D4shRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass17(Activity activity222, long j2) {
            activity = activity222;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4shRecord d4shRecord) {
            d4shRecord.setTypeCode(1);
            d4shRecord.save();
            Intent intent20 = new Intent(activity, (Class<?>) D4shHomeActivity.class);
            intent20.putExtra(Constants.EXTRA_DEVICE_ID, j);
            intent20.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
            activity.startActivity(intent20);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4shShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$18 */
    public class AnonymousClass18 implements PetkitCallback<D4shRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass18(Activity activity222, long j2) {
            activity = activity222;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4shRecord d4shRecord) {
            d4shRecord.setTypeCode(0);
            d4shRecord.save();
            Intent intent19 = new Intent(activity, (Class<?>) D4shHomeActivity.class);
            intent19.putExtra(Constants.EXTRA_DEVICE_ID, j);
            intent19.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
            activity.startActivity(intent19);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4shShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$19 */
    public class AnonymousClass19 implements PetkitCallback<T6Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass19(Activity activity222, long j2) {
            activity = activity222;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T6Record t6Record) {
            t6Record.saveData(0);
            Intent intent21 = new Intent(activity, (Class<?>) T6HomeActivity.class);
            intent21.putExtra(Constants.EXTRA_DEVICE_ID, j);
            intent21.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
            activity.startActivity(intent21);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T6ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$20 */
    public class AnonymousClass20 implements PetkitCallback<T6Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass20(Activity activity222, long j2) {
            activity = activity222;
            j = j2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T6Record t6Record) {
            t6Record.saveData(1);
            Intent intent17 = new Intent(activity, (Class<?>) T5HomeActivity.class);
            intent17.putExtra(Constants.EXTRA_DEVICE_ID, j);
            intent17.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
            activity.startActivity(intent17);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T5ShareError");
        }
    }

    public void getDeviceDetailAndEnterAppointView(Activity activity, long j, int i, Intent intent) {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(j));
        switch (i) {
            case 4:
                if (FeederUtils.getFeederRecordByDeviceId(j) != null) {
                    activity.startActivity(intent);
                } else {
                    HashMap map2 = new HashMap();
                    map2.put("id", String.valueOf(j));
                    AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_DETAIL, map2, new AsyncHttpRespHandler(activity) { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.39
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass39(Activity activity2, Activity activity22, Intent intent2) {
                            super(activity22);
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                        public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                            super.onSuccess(i2, headerArr, bArr);
                            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                            if (baseRsp.getError() != null) {
                                EventBus.getDefault().post("", "feederShareError");
                                PetkitToast.showShortToast(activity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                                return;
                            }
                            try {
                                JSONObject jSONObject = JSONUtils.getJSONObject(this.responseResult).getJSONObject("result");
                                if (jSONObject != null) {
                                    FeederUtils.storeFeederRecordFromJson(jSONObject);
                                    DeviceCenterUtils.setFeederRecordUpdated(true);
                                    activity.startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case 5:
                if (CozyUtils.getCozyRecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().cozyDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<CozyRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.40
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass40(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(CozyRecord cozyRecord) {
                            cozyRecord.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "CozyShareError");
                        }
                    });
                }
                break;
            case 6:
                if (D2Utils.getD2RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().d2DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D2Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.38
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass38(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D2Record d2Record) {
                            d2Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D2ShareError");
                        }
                    });
                }
                break;
            case 7:
                if (BleDeviceUtils.getT3RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().t3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.33
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass33(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T3Record t3Record) {
                            t3Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T3ShareError");
                        }
                    });
                }
                break;
            case 8:
                if (BleDeviceUtils.getK2RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().k2DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<K2Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.32
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass32(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(K2Record k2Record) {
                            k2Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "K2ShareError");
                        }
                    });
                }
                break;
            case 9:
                if (D3Utils.getD3RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().d3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.30
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass30(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D3Record d3Record) {
                            d3Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D3ShareError");
                        }
                    });
                }
                break;
            case 10:
                if (AqUtils.getAqRecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().aqDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<AqRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.31
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass31(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(AqRecord aqRecord) {
                            aqRecord.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "AqShareError");
                        }
                    });
                }
                break;
            case 11:
                if (D4Utils.getD4RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().d4DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.29
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass29(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4Record d4Record) {
                            d4Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4ShareError");
                        }
                    });
                }
                break;
            case 12:
                if (P3Utils.getP3RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().p3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<P3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.28
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass28(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(P3Record p3Record) {
                            p3Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "P3ShareError");
                        }
                    });
                }
                break;
            case 14:
                if (W5Utils.getW5RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().w5DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<W5Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.27
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass27(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(W5Record w5Record) {
                            w5Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "W5ShareError");
                        }
                    });
                }
                break;
            case 15:
                if (T4Utils.getT4RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().t4DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T4Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.25
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass25(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T4Record t4Record) {
                            t4Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T4ShareError");
                        }
                    });
                }
                break;
            case 16:
                if (K3Utils.getK3RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().k3DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<K3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.26
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass26(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(K3Record k3Record) {
                            k3Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "K3ShareError");
                        }
                    });
                }
                break;
            case 18:
                if (R2Utils.getR2RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().r2DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<R2Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.23
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass23(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(R2Record r2Record) {
                            r2Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "R2ShareError");
                        }
                    });
                }
                break;
            case 19:
                if (Aqh1Utils.getAqh1RecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().aqh1DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<Aqh1Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.34
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass34(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(Aqh1Record aqh1Record) {
                            aqh1Record.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "Aqh1ShareError");
                        }
                    });
                }
                break;
            case 20:
                if (D4sUtils.getD4sRecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().d4sDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4sRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.35
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass35(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4sRecord d4sRecord) {
                            d4sRecord.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4sShareError");
                        }
                    });
                }
                break;
            case 21:
                if (T6Utils.getT6RecordByDeviceId(j, 1) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().t6DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T6Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.21
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass21(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T6Record t6Record) {
                            t6Record.saveData(1);
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T6ShareError");
                        }
                    });
                }
                break;
            case 22:
                if (HgUtils.getHgRecordByDeviceId(j) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().hgDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<HgRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.24
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass24(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(HgRecord hgRecord) {
                            hgRecord.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "HgShareError");
                        }
                    });
                }
                break;
            case 25:
                if (D4shUtils.getD4shRecordByDeviceId(j, 0) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().d4shDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4shRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.36
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass36(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4shRecord d4shRecord) {
                            d4shRecord.setTypeCode(0);
                            d4shRecord.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4shShareError");
                        }
                    });
                }
                break;
            case 26:
                if (D4shUtils.getD4shRecordByDeviceId(j, 1) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().d4hDeviceDetail((BaseActivity) activity22, map, new PetkitCallback<D4shRecord>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.37
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass37(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(D4shRecord d4shRecord) {
                            d4shRecord.setTypeCode(1);
                            d4shRecord.save();
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "D4hShareError");
                        }
                    });
                }
                break;
            case 27:
                if (T6Utils.getT6RecordByDeviceId(j, 0) != null) {
                    activity22.startActivity(intent2);
                } else {
                    WebModelRepository.getInstance().t6DeviceDetail((BaseActivity) activity22, map, new PetkitCallback<T6Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.22
                        public final /* synthetic */ Activity val$activity;
                        public final /* synthetic */ Intent val$intent;

                        public AnonymousClass22(Activity activity22, Intent intent2) {
                            activity = activity22;
                            intent = intent2;
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onSuccess(T6Record t6Record) {
                            t6Record.saveData(0);
                            activity.startActivity(intent);
                        }

                        @Override // com.petkit.android.api.PetkitCallback
                        public void onFailure(ErrorInfor errorInfor) {
                            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
                            EventBus.getDefault().post("", "T6ShareError");
                        }
                    });
                }
                break;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$21 */
    public class AnonymousClass21 implements PetkitCallback<T6Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass21(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T6Record t6Record) {
            t6Record.saveData(1);
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T6ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$22 */
    public class AnonymousClass22 implements PetkitCallback<T6Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass22(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T6Record t6Record) {
            t6Record.saveData(0);
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T6ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$23 */
    public class AnonymousClass23 implements PetkitCallback<R2Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass23(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(R2Record r2Record) {
            r2Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "R2ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$24 */
    public class AnonymousClass24 implements PetkitCallback<HgRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass24(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(HgRecord hgRecord) {
            hgRecord.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "HgShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$25 */
    public class AnonymousClass25 implements PetkitCallback<T4Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass25(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T4Record t4Record) {
            t4Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T4ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$26 */
    public class AnonymousClass26 implements PetkitCallback<K3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass26(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(K3Record k3Record) {
            k3Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "K3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$27 */
    public class AnonymousClass27 implements PetkitCallback<W5Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass27(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(W5Record w5Record) {
            w5Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "W5ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$28 */
    public class AnonymousClass28 implements PetkitCallback<P3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass28(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(P3Record p3Record) {
            p3Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "P3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$29 */
    public class AnonymousClass29 implements PetkitCallback<D4Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass29(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4Record d4Record) {
            d4Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$30 */
    public class AnonymousClass30 implements PetkitCallback<D3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass30(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D3Record d3Record) {
            d3Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$31 */
    public class AnonymousClass31 implements PetkitCallback<AqRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass31(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(AqRecord aqRecord) {
            aqRecord.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "AqShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$32 */
    public class AnonymousClass32 implements PetkitCallback<K2Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass32(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(K2Record k2Record) {
            k2Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "K2ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$33 */
    public class AnonymousClass33 implements PetkitCallback<T3Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass33(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T3Record t3Record) {
            t3Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T3ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$34 */
    public class AnonymousClass34 implements PetkitCallback<Aqh1Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass34(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(Aqh1Record aqh1Record) {
            aqh1Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "Aqh1ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$35 */
    public class AnonymousClass35 implements PetkitCallback<D4sRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass35(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4sRecord d4sRecord) {
            d4sRecord.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4sShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$36 */
    public class AnonymousClass36 implements PetkitCallback<D4shRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass36(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4shRecord d4shRecord) {
            d4shRecord.setTypeCode(0);
            d4shRecord.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4shShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$37 */
    public class AnonymousClass37 implements PetkitCallback<D4shRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass37(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D4shRecord d4shRecord) {
            d4shRecord.setTypeCode(1);
            d4shRecord.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D4hShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$38 */
    public class AnonymousClass38 implements PetkitCallback<D2Record> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass38(Activity activity22, Intent intent2) {
            activity = activity22;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(D2Record d2Record) {
            d2Record.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "D2ShareError");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$39 */
    public class AnonymousClass39 extends AsyncHttpRespHandler {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass39(Activity activity22, Activity activity222, Intent intent2) {
            super(activity222);
            activity = activity222;
            intent = intent2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i2, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                EventBus.getDefault().post("", "feederShareError");
                PetkitToast.showShortToast(activity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            try {
                JSONObject jSONObject = JSONUtils.getJSONObject(this.responseResult).getJSONObject("result");
                if (jSONObject != null) {
                    FeederUtils.storeFeederRecordFromJson(jSONObject);
                    DeviceCenterUtils.setFeederRecordUpdated(true);
                    activity.startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$40 */
    public class AnonymousClass40 implements PetkitCallback<CozyRecord> {
        public final /* synthetic */ Activity val$activity;
        public final /* synthetic */ Intent val$intent;

        public AnonymousClass40(Activity activity222, Intent intent2) {
            activity = activity222;
            intent = intent2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(CozyRecord cozyRecord) {
            cozyRecord.save();
            activity.startActivity(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PetkitToast.showShortToast(activity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "CozyShareError");
        }
    }

    public void getDeviceDetail(ShareDeviceActivity shareDeviceActivity, long j, int i, Intent intent, GetDetailResultListener getDetailResultListener) {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(j));
        if (i != 7) {
            return;
        }
        if (BleDeviceUtils.getT3RecordByDeviceId(j) == null) {
            WebModelRepository.getInstance().t3DeviceDetail(shareDeviceActivity, map, new PetkitCallback<T3Record>() { // from class: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils.41
                public final /* synthetic */ GetDetailResultListener val$listener;
                public final /* synthetic */ ShareDeviceActivity val$shareDeviceActivity;

                public AnonymousClass41(GetDetailResultListener getDetailResultListener2, ShareDeviceActivity shareDeviceActivity2) {
                    getDetailResultListener = getDetailResultListener2;
                    shareDeviceActivity = shareDeviceActivity2;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(T3Record t3Record) {
                    t3Record.save();
                    GetDetailResultListener getDetailResultListener2 = getDetailResultListener;
                    if (getDetailResultListener2 != null) {
                        getDetailResultListener2.getDetailSuccess();
                    }
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                    GetDetailResultListener getDetailResultListener2 = getDetailResultListener;
                    if (getDetailResultListener2 != null) {
                        getDetailResultListener2.getDetailFail();
                    }
                    PetkitToast.showShortToast(shareDeviceActivity, errorInfor.getMsg(), R.drawable.toast_failed);
                    EventBus.getDefault().post("", "T3ShareError");
                }
            });
        } else if (getDetailResultListener2 != null) {
            getDetailResultListener2.getDetailSuccess();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils$41 */
    public class AnonymousClass41 implements PetkitCallback<T3Record> {
        public final /* synthetic */ GetDetailResultListener val$listener;
        public final /* synthetic */ ShareDeviceActivity val$shareDeviceActivity;

        public AnonymousClass41(GetDetailResultListener getDetailResultListener2, ShareDeviceActivity shareDeviceActivity2) {
            getDetailResultListener = getDetailResultListener2;
            shareDeviceActivity = shareDeviceActivity2;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(T3Record t3Record) {
            t3Record.save();
            GetDetailResultListener getDetailResultListener2 = getDetailResultListener;
            if (getDetailResultListener2 != null) {
                getDetailResultListener2.getDetailSuccess();
            }
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            GetDetailResultListener getDetailResultListener2 = getDetailResultListener;
            if (getDetailResultListener2 != null) {
                getDetailResultListener2.getDetailFail();
            }
            PetkitToast.showShortToast(shareDeviceActivity, errorInfor.getMsg(), R.drawable.toast_failed);
            EventBus.getDefault().post("", "T3ShareError");
        }
    }
}

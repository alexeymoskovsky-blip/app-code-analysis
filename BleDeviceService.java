package com.petkit.android.api.service;

import com.alibaba.fastjson.JSONObject;
import com.petkit.android.activities.feeder.model.DifferentFeedPlan;
import com.petkit.android.activities.feeder.model.FeederPlan;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqConfig;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqDevice;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aqh1.mode.Aqh1Record;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3DailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Device;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3DeviceStateRsp;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3SignupStatusResult;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Sound;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Statistic;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3SurplusControlTipsResult;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Tip;
import com.petkit.android.activities.petkitBleDevice.k3.mode.SnResult;
import com.petkit.android.activities.petkitBleDevice.mode.DeviceServerConfigs;
import com.petkit.android.activities.petkitBleDevice.mode.DeviceStatus;
import com.petkit.android.activities.petkitBleDevice.mode.K2BindDevice;
import com.petkit.android.activities.petkitBleDevice.mode.K2Device;
import com.petkit.android.activities.petkitBleDevice.mode.K2DeviceStateRsp;
import com.petkit.android.activities.petkitBleDevice.mode.K2Record;
import com.petkit.android.activities.petkitBleDevice.mode.K2SignupStatusResult;
import com.petkit.android.activities.petkitBleDevice.mode.K2TimingResult;
import com.petkit.android.activities.petkitBleDevice.mode.NewShareUserInfor;
import com.petkit.android.activities.petkitBleDevice.mode.T3BindDevice;
import com.petkit.android.activities.petkitBleDevice.mode.T3Device;
import com.petkit.android.activities.petkitBleDevice.mode.T3DeviceRecord;
import com.petkit.android.activities.petkitBleDevice.mode.T3DeviceStateRsp;
import com.petkit.android.activities.petkitBleDevice.mode.T3Record;
import com.petkit.android.activities.petkitBleDevice.mode.T3SignupStatusResult;
import com.petkit.android.activities.petkitBleDevice.mode.T3StatisticResult;
import com.petkit.android.activities.petkitBleDevice.mode.T3TimingResult;
import com.petkit.android.activities.petkitBleDevice.mode.ToiletCompareResult;
import com.petkit.android.activities.petkitBleDevice.mode.TradeInInfo;
import com.petkit.android.activities.petkitBleDevice.t4.mode.CleanRecordResult;
import com.petkit.android.activities.petkitBleDevice.w5.mode.AddWaterRecord;
import com.petkit.android.activities.petkitBleDevice.w5.mode.BleDevice;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.HttpResult;
import com.petkit.android.api.http.apiResponse.FirmwareCheckUpdateRsp;
import com.petkit.android.api.http.apiResponse.OtaCheckResult;
import com.petkit.android.api.http.apiResponse.OtaStatusResult;
import com.petkit.android.model.Pet;
import com.petkit.android.model.User;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/* JADX INFO: loaded from: classes6.dex */
public interface BleDeviceService {
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_ADD_SOUND)
    Observable<HttpResult<List<D3Sound>>> addSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_ADD_CLEAN_RECORD)
    Observable<HttpResult<String>> addT3CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T5_ADD_CLEAN_RECORD)
    Observable<HttpResult<String>> addT5CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T6_ADD_CLEAN_RECORD)
    Observable<HttpResult<String>> addT6CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_DEVICE_DATA)
    Observable<HttpResult<AqRecord>> aqDeviceData(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_GET_CONFIG)
    Observable<HttpResult<AqConfig>> aqGetConfig(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_GET_RISE_TIME)
    Observable<HttpResult<List<List<Integer>>>> aqGetRiseTime(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_LINK)
    Observable<HttpResult<AqDevice>> aqLink(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_OWN_DEVICE)
    Observable<HttpResult<List<AqDevice>>> aqOwnDevice();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_SAVE_LOG)
    Observable<HttpResult<String>> aqSaveLog(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_SIGNUP)
    Observable<HttpResult<AqDevice>> aqSignup(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_DELETE)
    Observable<HttpResult<String>> aqUnLink(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_UPDATE)
    Observable<HttpResult<String>> aqUpdate(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_UPDATE_LOCATION)
    Observable<HttpResult<String>> aqUpdateLocation(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQH1_DEVICE_DETAIL)
    Observable<HttpResult<Aqh1Record>> aqh1DeviceDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_BLE_CONNECT_CANCEL)
    Observable<HttpResult<String>> bleConnectCancel(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_BLE_CONNECT_POLL)
    Observable<HttpResult<String>> bleConnectPoll(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_BLE_CONNECT_START)
    Observable<HttpResult<JSONObject>> bleConnectStart(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_BLE_CONTROL_DEVICE)
    Observable<HttpResult<String>> bleControlDevice(@FieldMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_BLE_PRODUCTS)
    Observable<HttpResult<Map<String, Object>>> bleProduct();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_BLE_QUERY_SUPPORT_DEVICE)
    Observable<HttpResult<List<BleDevice>>> bleQuerySupportDevice(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_CALL_PET)
    Observable<HttpResult<String>> callPet(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_REMOVE_FROM_SHARE)
    Observable<HttpResult<String>> cancelD3Share(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_REMOVE_FROM_SHARE)
    Observable<HttpResult<String>> cancelK2Share(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_CANCEL_REALTIME_FEED)
    Observable<HttpResult<String>> cancelRealTimeFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_REMOVE_FROM_SHARE)
    Observable<HttpResult<String>> cancelT3Share(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_CONTROL_DEVICE)
    Observable<HttpResult<String>> controlDevice(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_CONTROL_DEVICE)
    Observable<HttpResult<String>> controlK2Device(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_ADD_INVENT)
    Observable<HttpResult<String>> d3AddInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_CANCEL_INVENT)
    Observable<HttpResult<String>> d3CancleInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_DEVICE_DETAIL)
    Observable<HttpResult<D3Record>> d3DeviceDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_LINK)
    Observable<HttpResult<D3Device>> d3Link(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_OTA_CHECK)
    Observable<HttpResult<OtaCheckResult>> d3OtaCheck(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_OTA_RESET)
    Observable<HttpResult<String>> d3OtaReset(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_OTA_START)
    Observable<HttpResult<String>> d3OtaStart(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_OTA_STATUS)
    Observable<HttpResult<OtaStatusResult>> d3OtaStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_SHARE_OPEN)
    Observable<HttpResult<String>> d3ShareOpen(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_GET_SHARE_REMOVE)
    Observable<HttpResult<String>> d3ShareRemove(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_UNLINK)
    Observable<HttpResult<String>> d3Unlink(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_UPDATE_SETTINGS)
    Observable<HttpResult<String>> d3UpdateSettings(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_DAILY_FEED_AND_EAT)
    Observable<HttpResult<D3DailyFeeds>> dailyFeedAndEat(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_PET_DATA_UPDATE_RECORD)
    Observable<HttpResult<String>> dataUpdateRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_DELETE_FIX_TIME_SETTING)
    Observable<HttpResult<String>> deleteFixTimeSetting(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_DELETE_FIX_TIME_SETTING)
    Observable<HttpResult<String>> deleteT3FixTimeSetting(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_FEED_AND_EAT_STATISTIC)
    Observable<HttpResult<Map<String, D3Statistic>>> feedAndEatStatistic(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_FIND_SN)
    Observable<HttpResult<SnResult>> findSn(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_W5_ADD_WATER_RECORD)
    Observable<HttpResult<AddWaterRecord>> getAddWaterRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_USER_GET_CAT_TIPS)
    Observable<HttpResult<String>> getCatTips(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_GET_CORRECT_RESULT)
    Observable<HttpResult<Integer>> getCorrectResult(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_DEVICE_STATE)
    Observable<HttpResult<D3DeviceStateRsp>> getD3DeviceState(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_FEED)
    Observable<HttpResult<DifferentFeedPlan>> getD3DifferentPlan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_FEED)
    Observable<HttpResult<FeederPlan>> getD3Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_SHARE_INVENT_USERS)
    Observable<HttpResult<ArrayList<NewShareUserInfor>>> getD3ShareInventUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_GET_SHARE_USERS)
    Observable<HttpResult<ArrayList<User>>> getD3ShareUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GET_DEVICE_SERVERS)
    Observable<HttpResult<List<DeviceServerConfigs>>> getDeviceServers();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_DEVICE_STATE)
    Observable<HttpResult<T3DeviceStateRsp>> getDeviceState(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_GET_FIX_TIME_SETTING)
    Observable<HttpResult<List<K2TimingResult>>> getFixTimeSetting(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_GET_GENERAL_CONFIG)
    Observable<HttpResult<TradeInInfo>> getGeneralConfig();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_DEVICE_STATE)
    Observable<HttpResult<K2DeviceStateRsp>> getK2DeviceState(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_SHARE_INVENT_USERS)
    Observable<HttpResult<ArrayList<NewShareUserInfor>>> getK2ShareInventUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_GET_SHARE_USERS)
    Observable<HttpResult<ArrayList<User>>> getK2ShareUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_SOUND_LIST)
    Observable<HttpResult<List<D3Sound>>> getSoundList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_GET_SURPLUS_CONTROL_TIPS)
    Observable<HttpResult<D3SurplusControlTipsResult>> getSurplusControlTips(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_GET_CLEAN_RECORD)
    Observable<HttpResult<CleanRecordResult>> getT3CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_GET_DEVICE_RECORD)
    Observable<HttpResult<List<T3DeviceRecord>>> getT3DeviceRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_GET_FIX_TIME_SETTING)
    Observable<HttpResult<List<T3TimingResult>>> getT3FixTimeSetting(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_SHARE_INVENT_USERS)
    Observable<HttpResult<ArrayList<NewShareUserInfor>>> getT3ShareInventUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_GET_SHARE_USERS)
    Observable<HttpResult<ArrayList<User>>> getT3ShareUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_STATISTIC)
    Observable<HttpResult<T3StatisticResult>> getT3Statistic(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T5_GET_CLEAN_RECORD)
    Observable<HttpResult<CleanRecordResult>> getT5CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T6_GET_CLEAN_RECORD)
    Observable<HttpResult<CleanRecordResult>> getT6CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_GET_TIPS)
    Observable<HttpResult<List<D3Tip>>> getTips(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_TOILET_COMPARE)
    Observable<HttpResult<ToiletCompareResult>> getToiletCompare(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_ADD_INVENT)
    Observable<HttpResult<String>> k2AddInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_CANCEL_INVENT)
    Observable<HttpResult<String>> k2CancleInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_DEVICE_DETAIL)
    Observable<HttpResult<K2Record>> k2DeviceDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_LINK)
    Observable<HttpResult<K2BindDevice>> k2Link(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_OTA_CHECK)
    Observable<HttpResult<OtaCheckResult>> k2OtaCheck(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_OTA_RESET)
    Observable<HttpResult<String>> k2OtaReset(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_OTA_START)
    Observable<HttpResult<String>> k2OtaStart(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_OTA_STATUS)
    Observable<HttpResult<OtaStatusResult>> k2OtaStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_SHARE_OPEN)
    Observable<HttpResult<String>> k2ShareOpen(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_GET_SHARE_REMOVE)
    Observable<HttpResult<String>> k2ShareRemove(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_UNLINK)
    Observable<HttpResult<String>> k2Unlink(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_LINK)
    Observable<HttpResult<T3BindDevice>> link(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_LINK_STATUS)
    Observable<HttpResult<List<DeviceStatus>>> linkStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_HS_SHAREADD)
    Observable<HttpResult<String>> mateAddInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_OTA_CHECK)
    Observable<HttpResult<OtaCheckResult>> otaCheck(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_OTA_RESET)
    Observable<HttpResult<String>> otaReset(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_OTA_START)
    Observable<HttpResult<String>> otaStart(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_OTA_STATUS)
    Observable<HttpResult<OtaStatusResult>> otaStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_OWN_DEVICE)
    Observable<HttpResult<List<D3Device>>> ownD3devices();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_OWN_DEVICE)
    Observable<HttpResult<List<K2Device>>> ownK2devices();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_OWN_DEVICE)
    Observable<HttpResult<List<T3Device>>> owndevices();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_PET_UPDATE_RECORD)
    Observable<HttpResult<String>> petUpdateRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_PLAY_SOUND)
    Observable<HttpResult<String>> playSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_RECORD_USER_ACTION)
    Observable<HttpResult<String>> recordUserAction(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_W5_REMOVE_ADD_WATER_RECORD)
    Observable<HttpResult<String>> removeAddWaterRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_REMOVE_DAILY_FEED)
    Observable<HttpResult<String>> removeDailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_REMOVE_FEED_RECORD)
    Observable<HttpResult<String>> removeFeedRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_REMOVE_RECORD)
    Observable<HttpResult<String>> removeRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_REMOVE_SOUND)
    Observable<HttpResult<String>> removeSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_REMOVE_CLEAN_RECORD)
    Observable<HttpResult<String>> removeT3CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_REMOVE_RECORD)
    Observable<HttpResult<String>> removeT3Record(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T5_REMOVE_CLEAN_RECORD)
    Observable<HttpResult<String>> removeT5CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T6_REMOVE_CLEAN_RECORD)
    Observable<HttpResult<String>> removeT6CleanRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_DESICCANT_RESET)
    Observable<HttpResult<Integer>> resetDesiccant(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_RESET_FIX_TIME_SETTING)
    Observable<HttpResult<String>> resetFixTimeSetting(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_RESTORE_FEED)
    Observable<HttpResult<String>> restoreD3Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_RESTORE_DAILY_FEED)
    Observable<HttpResult<String>> restoreDailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_W5_SAVE_ADD_WATER_RECORD)
    Observable<HttpResult<Integer>> saveAddWaterRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_APP_SAVE_CARD_MODE_SETTING)
    Observable<HttpResult<String>> saveCardModeSetting(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_SAVE_FEED)
    Observable<HttpResult<String>> saveD3Feed(@FieldMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_SAVE_REPEATS)
    Observable<HttpResult<String>> saveD3Repeats(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_SAVE_FIX_TIME_SETTING)
    Observable<HttpResult<String>> saveFixTimeSetting(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GO_SAVE_DATA)
    Observable<HttpResult<String[]>> saveGoData(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_SAVE_FIX_TIME_SETTING)
    Observable<HttpResult<String>> saveT3FixTimeSetting(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_SET_AUTO_CLEAN_TIME)
    Observable<HttpResult<String>> setAutoCleanTime(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_SIGNUP_STATUS)
    Observable<HttpResult<D3SignupStatusResult>> signupD3Status(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_SIGNUP_STATUS)
    Observable<HttpResult<K2SignupStatusResult>> signupK2Status(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_SIGNUP_STATUS)
    Observable<HttpResult<T3SignupStatusResult>> signupStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_START_CORRECT)
    Observable<HttpResult<String>> startCorrect(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_SUSPEND_FEED)
    Observable<HttpResult<String>> suspendD3Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_ADD_INVENT)
    Observable<HttpResult<String>> t3AddInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_CANCEL_INVENT)
    Observable<HttpResult<String>> t3CancleInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_DEVICE_DETAIL)
    Observable<HttpResult<T3Record>> t3DeviceDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_SHARE_OPEN)
    Observable<HttpResult<String>> t3ShareOpen(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_GET_SHARE_REMOVE)
    Observable<HttpResult<String>> t3ShareRemove(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_UNLINK)
    Observable<HttpResult<String>> unlink(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_UPDATE_SETTINGS)
    Observable<HttpResult<String>> updateDevice(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_HS_UPDATEAVATAR)
    Observable<HttpResult<String>> updateMateCover(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_PET_UPDATE_PROP)
    Observable<HttpResult<Pet>> updatePetProp(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_UPDATE_SETTINGS)
    Observable<HttpResult<String>> updateSettings(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_APP_SAVEUNIT)
    Observable<HttpResult<String>> updateWeightUnit(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_UPGRADE_CHECK)
    Observable<HttpResult<FirmwareCheckUpdateRsp.FirmwareCheckUpdateResult>> upgradeCheck(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_UPGRADE_REPORT)
    Observable<HttpResult<String>> upgradeReport(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_WALK_PET_SAVE_DATA)
    Observable<HttpResult<String>> uploadGoWalk(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_LOCK)
    Observable<HttpResult<String>> verifyLegalityD3(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_LOCK)
    Observable<HttpResult<String>> verifyLegalityT3(@QueryMap Map<String, String> map);
}

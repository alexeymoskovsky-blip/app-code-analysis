package com.petkit.android.api.service;

import com.petkit.android.activities.cloudservice.mode.CloudService;
import com.petkit.android.activities.feeder.model.DifferentFeedPlan;
import com.petkit.android.activities.feeder.model.FeederPlan;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Sound;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.AttireListResult;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDevice;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDeviceStateRsp;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shEatStatisticRsp;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shEventMediaInfo;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.mode.HighLightRecordRsp;
import com.petkit.android.activities.petkitBleDevice.mode.NewShareUserInfor;
import com.petkit.android.activities.petkitBleDevice.mode.OtaResult;
import com.petkit.android.activities.petkitBleDevice.mode.PetkitVideoSegment;
import com.petkit.android.activities.petkitBleDevice.mode.SignupStatusResult;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.OssStsInfoV2;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.VlogM3U8Mode;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.CountBean;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.HttpResult;
import com.petkit.android.api.http.apiResponse.OtaStatusResult;
import com.petkit.android.model.User;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/* JADX INFO: loaded from: classes6.dex */
public interface D4hService {
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_ADD_SOUND)
    Observable<HttpResult<List<D3Sound>>> addSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_ADDED_FOOD)
    Observable<HttpResult<String>> addedFood(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_BIND_STATUS)
    Observable<HttpResult<String>> bindStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REMOVE_FROM_SHARE)
    Observable<HttpResult<String>> cancelD4shShare(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_CANCEL_REALTIME_FEED)
    Observable<HttpResult<String>> cancelRealTimeFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_ADD_INVENT)
    Observable<HttpResult<String>> d4hAddInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_ATTIRE_LIST)
    Observable<HttpResult<List<AttireListResult>>> d4hAttireList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_CLOUD_BIND_DEVICE)
    Observable<HttpResult<String>> d4hCloudBindDevice(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_DEVICE_DETAIL)
    Observable<HttpResult<D4shRecord>> d4hDeviceDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SHARE_OPEN)
    Observable<HttpResult<String>> d4hShareOpen(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_UNLINK)
    Observable<HttpResult<String>> d4hUnlink(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_UPDATE_SETTINGS)
    Observable<HttpResult<String>> d4hUpdateSettings(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_CANCEL_INVENT)
    Observable<HttpResult<String>> d4shCancleInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OWN_DEVICE)
    Observable<HttpResult<List<D4shDevice>>> d4shOwnDevice();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_SHARE_REMOVE)
    Observable<HttpResult<String>> d4shShareRemove(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST("d4s/dailyFeeds")
    Observable<HttpResult<D4shDailyFeeds>> dailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @GET(ApiTools.SAMPLE_API_D4H_DATE_PICKER)
    Observable<HttpResult<List<CountBean>>> datePicker(@Path("startDay") String str, @Path("endDay") String str2, @QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST("d4s/eatStatistic")
    Observable<HttpResult<Map<String, D4shEatStatisticRsp>>> eatStatistic(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_FOOD_NO_REMIND)
    Observable<HttpResult<String>> foodNoRemind(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_FEED)
    Observable<HttpResult<FeederPlan>> getD4Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_DEVICE_STATE)
    Observable<HttpResult<D4shDeviceStateRsp>> getD4hDeviceState(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_FEED)
    Observable<HttpResult<DifferentFeedPlan>> getD4hDifferentPlan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SHARE_INVENT_USERS)
    Observable<HttpResult<ArrayList<NewShareUserInfor>>> getD4hShareInventUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_SHARE_USERS)
    Observable<HttpResult<ArrayList<User>>> getD4shShareUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_DEVICE_RECORD)
    Observable<HttpResult<D4shDailyFeeds>> getDeviceRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_DEVICE_INFO)
    Observable<HttpResult<CloudService>> getDeviceServiceInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_EVENT_MEDIA_INFO)
    Observable<HttpResult<D4shEventMediaInfo>> getEventMediaInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_HIGH_LIGHT)
    Observable<HttpResult<HighLightRecordRsp>> getHighLight(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_HIGH_LIGHT_M3U8)
    Observable<HttpResult<ArrayList<VlogM3U8Mode>>> getHighlightM3u8(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OSS_STS_INFO)
    Observable<HttpResult<OssStsInfoV2>> getOssInfo(@QueryMap Map<String, Object> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_SERVICE_BY_DEVICE_ID)
    Observable<HttpResult<CloudService>> getServiceByDeviceId(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SOUND_LIST)
    Observable<HttpResult<List<D3Sound>>> getSoundList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_CLOUD_VIDEO)
    Observable<HttpResult<List<PetkitVideoSegment>>> getVideoSegmentList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_LINK)
    Observable<HttpResult<D4shDevice>> link(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OTA_CHECK)
    Observable<HttpResult<OtaResult>> otaCheck(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OTA_COMPLETE)
    Observable<HttpResult<String>> otaComplete(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OTA_RESET)
    Observable<HttpResult<String>> otaReset(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OTA_START)
    Observable<HttpResult<String>> otaStart(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OTA_STATUS)
    Observable<HttpResult<OtaStatusResult>> otaStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_OWN_DEVICE)
    Observable<HttpResult<List<D4shDevice>>> owndevices();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_PLAY_SOUND)
    Observable<HttpResult<String>> playSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REMOVE_DAILY_FEED)
    Observable<HttpResult<String>> removeDailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REMOVE_FEED_RECORD)
    Observable<HttpResult<String>> removeFeedRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REMOVE_HIGH_LIGHT)
    Observable<HttpResult<String>> removeHighlight(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REMOVE_RECORD)
    Observable<HttpResult<String>> removeRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REMOVE_SOUND)
    Observable<HttpResult<String>> removeSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REMOVE_VIDEO_EVENT)
    Observable<HttpResult<String>> removeVideoEvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_DESICCANT_RESET)
    Observable<HttpResult<Integer>> resetD4hDesiccant(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_RESTORE_FEED)
    Observable<HttpResult<String>> restoreD4Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_RESTORE_DAILY_FEED)
    Observable<HttpResult<String>> restoreDailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SAVE_FEED)
    Observable<HttpResult<String>> saveD4DifferentFeed(@QueryMap Map<String, String> map, @Body RequestBody requestBody);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SAVE_REPEATS)
    Observable<HttpResult<String>> saveD4Repeats(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SAVE_FEED)
    Observable<HttpResult<String>> saveD4hFeed(@FieldMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SIGNUP_STATUS)
    Observable<HttpResult<SignupStatusResult>> signupD4hStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_SUSPEND_FEED)
    Observable<HttpResult<String>> suspendD4Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_TEMPORARY_OPEN_CAMERA)
    Observable<HttpResult<String>> temporaryOpenCamera(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_UPDATE_SOUND)
    Observable<HttpResult<List<D3Sound>>> updateSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_UPLOAD_HIGH_LIGHT)
    Observable<HttpResult<String>> uploadHighlight(@QueryMap Map<String, Object> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_UPLOAD_UNRELIABLE_VIDEO)
    Observable<HttpResult<String>> uploadUnreliableVideo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_LOCK)
    Observable<HttpResult<String>> verifyLegalityD4H(@QueryMap Map<String, String> map);
}

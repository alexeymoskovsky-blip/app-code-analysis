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
public interface D4shService {
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_ADD_SOUND)
    Observable<HttpResult<List<D3Sound>>> addSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_ADDED_FOOD)
    Observable<HttpResult<String>> addedFood(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_BIND_STATUS)
    Observable<HttpResult<String>> bindStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REMOVE_FROM_SHARE)
    Observable<HttpResult<String>> cancelD4shShare(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_CANCEL_REALTIME_FEED)
    Observable<HttpResult<String>> cancelRealTimeFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_ADD_INVENT)
    Observable<HttpResult<String>> d4shAddInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_ATTIRE_LIST)
    Observable<HttpResult<List<AttireListResult>>> d4shAttireList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_CANCEL_INVENT)
    Observable<HttpResult<String>> d4shCancleInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_CLOUD_BIND_DEVICE)
    Observable<HttpResult<String>> d4shCloudBindDevice(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_DEVICE_DETAIL)
    Observable<HttpResult<D4shRecord>> d4shDeviceDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OWN_DEVICE)
    Observable<HttpResult<List<D4shDevice>>> d4shOwnDevice();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SHARE_OPEN)
    Observable<HttpResult<String>> d4shShareOpen(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_SHARE_REMOVE)
    Observable<HttpResult<String>> d4shShareRemove(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_UNLINK)
    Observable<HttpResult<String>> d4shUnlink(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_UPDATE_SETTINGS)
    Observable<HttpResult<String>> d4shUpdateSettings(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST("d4s/dailyFeeds")
    Observable<HttpResult<D4shDailyFeeds>> dailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @GET(ApiTools.SAMPLE_API_D4SH_DATE_PICKER)
    Observable<HttpResult<List<CountBean>>> datePicker(@Path("startDay") String str, @Path("endDay") String str2, @QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST("d4s/eatStatistic")
    Observable<HttpResult<Map<String, D4shEatStatisticRsp>>> eatStatistic(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_FOOD_NO_REMIND)
    Observable<HttpResult<String>> foodNoRemind(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_FEED)
    Observable<HttpResult<FeederPlan>> getD4Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_GET_OVER_GRAPH)
    Observable<HttpResult<D4shDailyFeeds.D4shDailyEat>> getD4hEatOverGraph(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_MATCH_PET)
    Observable<HttpResult<String>> getD4hMatchPet(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_DEVICE_STATE)
    Observable<HttpResult<D4shDeviceStateRsp>> getD4shDeviceState(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_FEED)
    Observable<HttpResult<DifferentFeedPlan>> getD4shDifferentPlan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_OVER_GRAPH)
    Observable<HttpResult<D4shDailyFeeds.D4shDailyEat>> getD4shEatOverGraph(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_MATCH_PET)
    Observable<HttpResult<String>> getD4shMatchPet(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SHARE_INVENT_USERS)
    Observable<HttpResult<ArrayList<NewShareUserInfor>>> getD4shShareInventUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_SHARE_USERS)
    Observable<HttpResult<ArrayList<User>>> getD4shShareUsers(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_DEVICE_RECORD)
    Observable<HttpResult<D4shDailyFeeds>> getDeviceRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_DEVICE_INFO)
    Observable<HttpResult<CloudService>> getDeviceServiceInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_EVENT_MEDIA_INFO)
    Observable<HttpResult<D4shEventMediaInfo>> getEventMediaInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_HIGH_LIGHT)
    Observable<HttpResult<HighLightRecordRsp>> getHighLight(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_HIGH_LIGHT_M3U8)
    Observable<HttpResult<ArrayList<VlogM3U8Mode>>> getHighlightM3u8(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OSS_STS_INFO)
    Observable<HttpResult<OssStsInfoV2>> getOssInfo(@QueryMap Map<String, Object> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_GET_SERVICE_BY_DEVICE_ID)
    Observable<HttpResult<CloudService>> getServiceByDeviceId(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SOUND_LIST)
    Observable<HttpResult<List<D3Sound>>> getSoundList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_CLOUD_VIDEO)
    Observable<HttpResult<List<PetkitVideoSegment>>> getVideoSegmentList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_LINK)
    Observable<HttpResult<D4shDevice>> link(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OTA_CHECK)
    Observable<HttpResult<OtaResult>> otaCheck(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OTA_COMPLETE)
    Observable<HttpResult<String>> otaComplete(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OTA_RESET)
    Observable<HttpResult<String>> otaReset(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OTA_START)
    Observable<HttpResult<String>> otaStart(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OTA_STATUS)
    Observable<HttpResult<OtaStatusResult>> otaStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_OWN_DEVICE)
    Observable<HttpResult<List<D4shDevice>>> owndevices();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_PLAY_SOUND)
    Observable<HttpResult<String>> playSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REMOVE_DAILY_FEED)
    Observable<HttpResult<String>> removeDailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REMOVE_FEED_RECORD)
    Observable<HttpResult<String>> removeFeedRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REMOVE_HIGH_LIGHT)
    Observable<HttpResult<String>> removeHighlight(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REMOVE_RECORD)
    Observable<HttpResult<String>> removeRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REMOVE_SOUND)
    Observable<HttpResult<String>> removeSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REMOVE_VIDEO_EVENT)
    Observable<HttpResult<String>> removeVideoEvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_DESICCANT_RESET)
    Observable<HttpResult<Integer>> resetD4shDesiccant(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_RESTORE_FEED)
    Observable<HttpResult<String>> restoreD4Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_RESTORE_DAILY_FEED)
    Observable<HttpResult<String>> restoreDailyFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SAVE_FEED)
    Observable<HttpResult<String>> saveD4DifferentFeed(@QueryMap Map<String, String> map, @Body RequestBody requestBody);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SAVE_REPEATS)
    Observable<HttpResult<String>> saveD4Repeats(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SAVE_FEED)
    Observable<HttpResult<String>> saveD4shFeed(@FieldMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SIGNUP_STATUS)
    Observable<HttpResult<SignupStatusResult>> signupD4shStatus(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_SUSPEND_FEED)
    Observable<HttpResult<String>> suspendD4Plan(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_TEMPORARY_OPEN_CAMERA)
    Observable<HttpResult<String>> temporaryOpenCamera(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_UPDATE_SOUND)
    Observable<HttpResult<List<D3Sound>>> updateSound(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_UPLOAD_HIGH_LIGHT)
    Observable<HttpResult<String>> uploadHighlight(@QueryMap Map<String, Object> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_UPLOAD_UNRELIABLE_VIDEO)
    Observable<HttpResult<String>> uploadUnreliableVideo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_LOCK)
    Observable<HttpResult<String>> verifyLegalityD4SH(@QueryMap Map<String, String> map);
}

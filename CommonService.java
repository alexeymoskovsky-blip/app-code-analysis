package com.petkit.android.api.service;

import com.petkit.android.activities.card.mode.CardInfo;
import com.petkit.android.activities.card.mode.PurchaseEligibilityInfo;
import com.petkit.android.activities.cloudservice.mode.CloudService;
import com.petkit.android.activities.cloudservice.mode.CloudServiceFreeTrialInfo;
import com.petkit.android.activities.cloudservice.mode.DeviceServiceInfo;
import com.petkit.android.activities.cloudservice.mode.MineSupportCsDevice;
import com.petkit.android.activities.cloudservice.mode.RenewalRecord;
import com.petkit.android.activities.cloudservice.mode.SupportCsDevice;
import com.petkit.android.activities.cloudservice.mode.TransferDeviceInfo;
import com.petkit.android.activities.device.mode.AiInfo;
import com.petkit.android.activities.device.mode.AliOssInfo;
import com.petkit.android.activities.device.mode.CheckDetailInfo;
import com.petkit.android.activities.device.mode.MaterialDetailInfo;
import com.petkit.android.activities.device.mode.MaterialSubRes;
import com.petkit.android.activities.device.mode.MaterialUploadInfo;
import com.petkit.android.activities.device.mode.MaterialUploadRes;
import com.petkit.android.activities.doctor.mode.MedicalConversionCreateMode;
import com.petkit.android.activities.doctor.mode.MedicalConversionList;
import com.petkit.android.activities.doctor.mode.MedicalPrivacyInfo;
import com.petkit.android.activities.doctor.mode.PetError;
import com.petkit.android.activities.feeder.model.FeederPlanItem;
import com.petkit.android.activities.home.mode.CardRankResult;
import com.petkit.android.activities.home.mode.CloudServiceDelayTimeResult;
import com.petkit.android.activities.home.mode.DeviceInviteData;
import com.petkit.android.activities.home.mode.HomeCardData;
import com.petkit.android.activities.home.mode.OutSideInviteData;
import com.petkit.android.activities.home.mode.PetkitDeviceRecord;
import com.petkit.android.activities.home.mode.RefreshHomeData;
import com.petkit.android.activities.home.mode.SaleHotLineData;
import com.petkit.android.activities.home.mode.SearchResultInfo;
import com.petkit.android.activities.home.mode.SoInfor;
import com.petkit.android.activities.petkitBleDevice.mode.CurrentCloudData;
import com.petkit.android.activities.petkitBleDevice.mode.DeviceConsumable;
import com.petkit.android.activities.petkitBleDevice.mode.GifVideoData;
import com.petkit.android.activities.petkitBleDevice.mode.ReplaceRecord;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.HttpResult;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.media.video.rtmUtil.RtmListInfo;
import com.petkit.android.model.RemindType;
import com.petkit.android.model.User;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/* JADX INFO: loaded from: classes6.dex */
public interface CommonService {
    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GROUP_INVENT_ACCEPT)
    Observable<HttpResult<String>> acceptDeviceInvent(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GROUP_DEVICE_ACCEPT)
    Observable<HttpResult<String>> acceptDeviceShare(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_CARD_COUPON_CODE)
    Observable<HttpResult<Object>> addCard(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_CREATION_AWARD_RECEIVE)
    Observable<HttpResult<String>> aiAwardReceive(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_CREATION_CHECK_CANCEL)
    Observable<HttpResult<Object>> aiCheckCancel(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_CREATION_CHECK_DETAIL)
    Observable<HttpResult<CheckDetailInfo>> aiCheckDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_CREATION_DEVICE_INFO)
    Observable<HttpResult<AiInfo>> aiCreation(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_CREATION_DEVICE_UPLOAD)
    Observable<HttpResult<Object>> aiCreationUpload(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_MATERIAL_DETAIL)
    Observable<HttpResult<MaterialUploadInfo>> aiMaterialDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_MATERIAL_EXIST)
    Observable<HttpResult<MaterialUploadRes>> aiMaterialExist(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_MATERIAL_INFO)
    Observable<HttpResult<MaterialDetailInfo>> aiMaterialInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_MATERIAL_UPLOAD)
    Observable<HttpResult<MaterialSubRes>> aiMaterialUpload(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_ALI_OSS_INFO)
    Observable<HttpResult<AliOssInfo>> aiOssInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_ALL_CLOUD_DEVICES)
    Observable<HttpResult<List<MineSupportCsDevice>>> allCloudDevices(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_HS_SHAREREMOVEFROM)
    Observable<HttpResult<String>> cancelMateShare(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_CARD_CANCEL_EXPIRE_TIPS)
    Observable<HttpResult<Object>> cancelRedPoint();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_CLOUD_DEVICES_URL)
    Observable<HttpResult<List<SupportCsDevice>>> cloudDevicesUrl();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_CLOUD_SERVICE_TRANSFER)
    Observable<HttpResult<String>> cloudServiceTransfer(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_DELETE_SERVICE)
    Observable<HttpResult<String>> deleteService(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_DISABLE_JUDGE)
    Observable<HttpResult<String>> disableJudge(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_EDIT_FEED)
    Observable<HttpResult<List<FeederPlanItem>>> editDeviceFeed(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_EDIT_RELATIONS)
    Observable<HttpResult<String>> editDeviceRelations(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_CARD_COUPON_EXCHANGE)
    Observable<HttpResult<Object>> exchangeCard(@Body RequestBody requestBody);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_CARD_EXPIRE_TIPS)
    Observable<HttpResult<Object>> expireTips();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQ_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getAqRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQH1_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getAqh1RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AQR_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getAqrRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_APP_GET_CARD_RANK)
    Observable<HttpResult<CardRankResult>> getCardRank(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_APP_GET_CLOUD_SERVICE_DELAY_TIME)
    Observable<HttpResult<CloudServiceDelayTimeResult>> getCloudServiceDelayTime();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_USER_SENDCODEFORREGISTER)
    Observable<HttpResult<Object>> getCode(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GET_CONSUMABLES_RECORD)
    Observable<HttpResult<ReplaceRecord>> getConsumablesRecord(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_COPY_WRITING_GIF_VIDEO)
    Observable<HttpResult<GifVideoData>> getCopywritingGifVideo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @GET(ApiTools.SAMPLE_API_CARD_COUPON_LIST)
    Observable<HttpResult<List<CardInfo>>> getCouponList();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_COZY_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getCozyRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_CTW3_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getCtw3RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D2_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getD2RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D3_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getD3RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getD4RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4H_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getD4hRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4S_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getD4sRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_D4SH_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getD4shRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_CONSUMABLES)
    Observable<HttpResult<Map<String, List<DeviceConsumable>>>> getDeviceConsumables();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_CURRENT_CLOUD_DEVICES)
    Observable<HttpResult<List<CurrentCloudData>>> getDeviceCurrentCloudDevice(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GROUP_DEVICE_SHARE_INFOR)
    Observable<HttpResult<DeviceInviteData>> getDeviceShareInfor(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_FEEDER_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getFeederRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_FIT_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getFitRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_PACKAGE_FREE)
    Observable<HttpResult<Object>> getFreePackage(@Body RequestBody requestBody);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_PACKAGE_FREE_INFO)
    Observable<HttpResult<CloudServiceFreeTrialInfo>> getFreePackageInfo(@Body RequestBody requestBody);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GO_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getGoRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_H2_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getH2RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_HG_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getHgRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_HOME_CARD)
    Observable<HttpResult<HomeCardData>> getHomeCard(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GROUP_INVENT_DETAIL)
    Observable<HttpResult<OutSideInviteData>> getInventDetail(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K2_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getK2RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_K3_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getK3RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_MATE_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getMateRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_MEDICAL_CONVERSION_GRAY)
    Observable<HttpResult<Boolean>> getMedicalConversationGray(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_MEDICAL_PRIVACY_INFO)
    Observable<HttpResult<MedicalPrivacyInfo>> getMedicalPrivacyInfo();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_P3_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getP3RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_PET_ERROR_INFO)
    Observable<HttpResult<PetError>> getPetErrorInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GET_PETKIT_DEVICES)
    Observable<HttpResult<List<PetkitDeviceRecord>>> getPetkitDevices(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_R2_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getR2RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getRefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_RTM_START_RTM)
    Observable<HttpResult<RtmListInfo>> getRtmList();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_USER_SALE_HOTLINE)
    Observable<HttpResult<SaleHotLineData>> getSaleHotLine();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_APP_GET_SO_URL)
    Observable<HttpResult<SoInfor>> getSoUrl(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T3_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getT3RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T4_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getT4RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T5_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getT5RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_T6_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getT6RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_APP_GET_THIRD_PARTY_APP_SCHEMA)
    Observable<HttpResult<List<String>>> getThirdPartyAppSchema();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_TODO_CARD)
    Observable<HttpResult<HomeCardData>> getTodoCard();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GET_CS_URL)
    Observable<HttpResult<String>> getUdeskUrl();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GET_USER_INFOR)
    Observable<HttpResult<User>> getUserInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_W5_REFRESH_HOME)
    Observable<HttpResult<RefreshHomeData>> getW5RefreshHome(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_PET_ERROR_IGNORE)
    Observable<HttpResult<String>> ignorePetError(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_TODO_IGNORE)
    Observable<HttpResult<String>> ignoreRemind(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_MEDICAL_CONVERSION_CREATE)
    Observable<HttpResult<MedicalConversionCreateMode>> medicalConversionCreate(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_MEDICAL_CONVERSION_LIST)
    Observable<HttpResult<MedicalConversionList>> medicalConversionList(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_MEDICAL_PRIVACY_ACCEPT)
    Observable<HttpResult<Object>> medicalPrivacyAccept(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_DATA_UPDATE_CLEAN_RECORD)
    Observable<HttpResult<String>> petUpdateData(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_CREATION_PRIVACY_ACCEPT)
    Observable<HttpResult<Object>> privacyAccept();

    @Headers({"Domain-Name: petkit"})
    @GET(ApiTools.SAMPLE_API_PRODUCT_SKU_PURCHASE_ELIGIBILITY)
    Observable<HttpResult<PurchaseEligibilityInfo>> purchaseEligibility();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_PRODUCT_SKU_PURCHASE_ELIGIBILITY_NOREMIND)
    Observable<HttpResult<String>> purchaseEligibilityNoRemind(@Body RequestBody requestBody);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GROUP_DEVICE_REFUSE)
    Observable<HttpResult<String>> refuseDeviceShare(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_AI_CREATION_PRIVACY_REFUSED)
    Observable<HttpResult<Object>> refusedAccept();

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_USER_VERIFYCODE)
    Observable<HttpResult<Object>> register(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_RENEWAL_MANAGE_FOR_BUNDLE)
    Observable<HttpResult<DeviceServiceInfo>> renewalManageForBundle(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_APP_SAVE_CARD_RANK)
    Observable<HttpResult<String>> saveCardRank(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_SCHEDULE_SAVE)
    Observable<HttpResult<BaseRsp>> scheduleSave(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_SCHEDULE_TYPES_APPOINT)
    Observable<HttpResult<RemindType>> scheduleTypesAppoint(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_SEARCH_UER)
    Observable<HttpResult<SearchResultInfo>> searchUser(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_SERVICE_CHARGE_HISTORY)
    Observable<HttpResult<List<RenewalRecord>>> serviceChargeHistory(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_SERVICE_MANAGE)
    Observable<HttpResult<List<CloudService>>> serviceManage(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_USER_REGISTER_ACCOUNT)
    Observable<HttpResult<Object>> submitCode(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_TRANSFER_DEVICE_INFO)
    Observable<HttpResult<TransferDeviceInfo>> transferDeviceInfo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_UN_SIGN)
    Observable<HttpResult<Object>> unSign(@Body RequestBody requestBody);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLET_API_FEEDER_UNLINK)
    Observable<HttpResult<String>> unlinkFeeder(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_PET_UNBINDDEVICE)
    Observable<HttpResult<String>> unlinkFit(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_GO_UNLINK)
    Observable<HttpResult<String>> unlinkGo(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_HS_UNBINDDEVICE)
    Observable<HttpResult<String>> unlinkMate(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_PET_UPDATE_BODY_PIC)
    Observable<HttpResult<Object>> updateBodyPic(@QueryMap Map<String, Object> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_API_DEVICE_EDIT_NAME)
    Observable<HttpResult<String>> updateDeviceName(@QueryMap Map<String, String> map);

    @Headers({"Domain-Name: petkit"})
    @POST(ApiTools.SAMPLE_USER_MEDICAL_READ)
    Observable<HttpResult<String>> userMedicalRead();
}

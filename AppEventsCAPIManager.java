package com.facebook.appevents.cloudbridge;

import android.content.SharedPreferences;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.instrument.crashshield.CrashShieldHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AppEventsCAPIManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AppEventsCAPIManager {

    @NotNull
    private static final String SETTINGS_PATH = "/cloudbridge_settings";
    private static boolean isEnabled;

    @NotNull
    public static final AppEventsCAPIManager INSTANCE = new AppEventsCAPIManager();
    private static final String TAG = AppEventsCAPIManager.class.getCanonicalName();

    private AppEventsCAPIManager() {
    }

    public final boolean isEnabled$facebook_core_release() {
        return isEnabled;
    }

    public final void setEnabled$facebook_core_release(boolean z) {
        isEnabled = z;
    }

    @JvmStatic
    @Nullable
    public static final Map<String, Object> getSavedCloudBridgeCredentials$facebook_core_release() {
        if (CrashShieldHandler.isObjectCrashing(AppEventsCAPIManager.class)) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(FacebookSdk.CLOUDBRIDGE_SAVED_CREDENTIALS, 0);
            if (sharedPreferences == null) {
                return null;
            }
            SettingsAPIFields settingsAPIFields = SettingsAPIFields.DATASETID;
            String string = sharedPreferences.getString(settingsAPIFields.getRawValue(), null);
            SettingsAPIFields settingsAPIFields2 = SettingsAPIFields.URL;
            String string2 = sharedPreferences.getString(settingsAPIFields2.getRawValue(), null);
            SettingsAPIFields settingsAPIFields3 = SettingsAPIFields.ACCESSKEY;
            String string3 = sharedPreferences.getString(settingsAPIFields3.getRawValue(), null);
            if (string != null && !StringsKt__StringsJVMKt.isBlank(string) && string2 != null && !StringsKt__StringsJVMKt.isBlank(string2) && string3 != null && !StringsKt__StringsJVMKt.isBlank(string3)) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                linkedHashMap.put(settingsAPIFields2.getRawValue(), string2);
                linkedHashMap.put(settingsAPIFields.getRawValue(), string);
                linkedHashMap.put(settingsAPIFields3.getRawValue(), string3);
                Logger.Companion.log(LoggingBehavior.APP_EVENTS, TAG.toString(), " \n\nLoading Cloudbridge settings from saved Prefs: \n================\n DATASETID: %s\n URL: %s \n ACCESSKEY: %s \n\n ", string, string2, string3);
                return linkedHashMap;
            }
            return null;
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, AppEventsCAPIManager.class);
            return null;
        }
    }

    public final void setSavedCloudBridgeCredentials$facebook_core_release(@Nullable Map<String, ? extends Object> map) {
        SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(FacebookSdk.CLOUDBRIDGE_SAVED_CREDENTIALS, 0);
        if (sharedPreferences == null) {
            return;
        }
        if (map == null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.clear();
            editorEdit.apply();
            return;
        }
        SettingsAPIFields settingsAPIFields = SettingsAPIFields.DATASETID;
        Object obj = map.get(settingsAPIFields.getRawValue());
        SettingsAPIFields settingsAPIFields2 = SettingsAPIFields.URL;
        Object obj2 = map.get(settingsAPIFields2.getRawValue());
        SettingsAPIFields settingsAPIFields3 = SettingsAPIFields.ACCESSKEY;
        Object obj3 = map.get(settingsAPIFields3.getRawValue());
        if (obj == null || obj2 == null || obj3 == null) {
            return;
        }
        SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
        editorEdit2.putString(settingsAPIFields.getRawValue(), obj.toString());
        editorEdit2.putString(settingsAPIFields2.getRawValue(), obj2.toString());
        editorEdit2.putString(settingsAPIFields3.getRawValue(), obj3.toString());
        editorEdit2.apply();
        Logger.Companion.log(LoggingBehavior.APP_EVENTS, TAG.toString(), " \n\nSaving Cloudbridge settings from saved Prefs: \n================\n DATASETID: %s\n URL: %s \n ACCESSKEY: %s \n\n ", obj, obj2, obj3);
    }

    @JvmStatic
    public static final void enable() {
        try {
            GraphRequest graphRequest = new GraphRequest(null, FacebookSdk.getApplicationId() + SETTINGS_PATH, null, HttpMethod.GET, new GraphRequest.Callback() { // from class: com.facebook.appevents.cloudbridge.AppEventsCAPIManager$$ExternalSyntheticLambda0
                @Override // com.facebook.GraphRequest.Callback
                public final void onCompleted(GraphResponse graphResponse) {
                    AppEventsCAPIManager.enable$lambda$0(graphResponse);
                }
            }, null, 32, null);
            Logger.Companion companion = Logger.Companion;
            LoggingBehavior loggingBehavior = LoggingBehavior.APP_EVENTS;
            String str = TAG;
            Intrinsics.checkNotNull(str, "null cannot be cast to non-null type kotlin.String");
            companion.log(loggingBehavior, str, " \n\nCreating Graph Request: \n=============\n%s\n\n ", graphRequest);
            graphRequest.executeAsync();
        } catch (JSONException e) {
            Logger.Companion companion2 = Logger.Companion;
            LoggingBehavior loggingBehavior2 = LoggingBehavior.APP_EVENTS;
            String str2 = TAG;
            Intrinsics.checkNotNull(str2, "null cannot be cast to non-null type kotlin.String");
            companion2.log(loggingBehavior2, str2, " \n\nGraph Request Exception: \n=============\n%s\n\n ", ExceptionsKt__ExceptionsKt.stackTraceToString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void enable$lambda$0(GraphResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        INSTANCE.getCAPIGSettingsFromGraphResponse$facebook_core_release(response);
    }

    public final void getCAPIGSettingsFromGraphResponse$facebook_core_release(@NotNull GraphResponse response) {
        Object obj;
        boolean zBooleanValue = false;
        Intrinsics.checkNotNullParameter(response, "response");
        if (response.getError() != null) {
            Logger.Companion companion = Logger.Companion;
            LoggingBehavior loggingBehavior = LoggingBehavior.APP_EVENTS;
            String str = TAG;
            Intrinsics.checkNotNull(str, "null cannot be cast to non-null type kotlin.String");
            companion.log(loggingBehavior, str, " \n\nGraph Response Error: \n================\nResponse Error: %s\nResponse Error Exception: %s\n\n ", response.getError().toString(), String.valueOf(response.getError().getException()));
            Map<String, Object> savedCloudBridgeCredentials$facebook_core_release = getSavedCloudBridgeCredentials$facebook_core_release();
            if (savedCloudBridgeCredentials$facebook_core_release != null) {
                URL url = new URL(String.valueOf(savedCloudBridgeCredentials$facebook_core_release.get(SettingsAPIFields.URL.getRawValue())));
                AppEventsConversionsAPITransformerWebRequests.configure(String.valueOf(savedCloudBridgeCredentials$facebook_core_release.get(SettingsAPIFields.DATASETID.getRawValue())), url.getProtocol() + "://" + url.getHost(), String.valueOf(savedCloudBridgeCredentials$facebook_core_release.get(SettingsAPIFields.ACCESSKEY.getRawValue())));
                isEnabled = true;
                return;
            }
            return;
        }
        Logger.Companion companion2 = Logger.Companion;
        LoggingBehavior loggingBehavior2 = LoggingBehavior.APP_EVENTS;
        String TAG2 = TAG;
        Intrinsics.checkNotNull(TAG2, "null cannot be cast to non-null type kotlin.String");
        companion2.log(loggingBehavior2, TAG2, " \n\nGraph Response Received: \n================\n%s\n\n ", response);
        JSONObject jSONObject = response.getJSONObject();
        if (jSONObject != null) {
            try {
                obj = jSONObject.get("data");
            } catch (NullPointerException e) {
                Logger.Companion companion3 = Logger.Companion;
                LoggingBehavior loggingBehavior3 = LoggingBehavior.APP_EVENTS;
                String TAG3 = TAG;
                Intrinsics.checkNotNullExpressionValue(TAG3, "TAG");
                companion3.log(loggingBehavior3, TAG3, "CloudBridge Settings API response is not a valid json: \n%s ", ExceptionsKt__ExceptionsKt.stackTraceToString(e));
                return;
            } catch (JSONException e2) {
                Logger.Companion companion4 = Logger.Companion;
                LoggingBehavior loggingBehavior4 = LoggingBehavior.APP_EVENTS;
                String TAG4 = TAG;
                Intrinsics.checkNotNullExpressionValue(TAG4, "TAG");
                companion4.log(loggingBehavior4, TAG4, "CloudBridge Settings API response is not a valid json: \n%s ", ExceptionsKt__ExceptionsKt.stackTraceToString(e2));
                return;
            }
        } else {
            obj = null;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.json.JSONArray");
        Map<String, ? extends Object> mapConvertJSONObjectToHashMap = Utility.convertJSONObjectToHashMap(new JSONObject((String) CollectionsKt___CollectionsKt.firstOrNull((List) Utility.convertJSONArrayToList((JSONArray) obj))));
        String str2 = (String) mapConvertJSONObjectToHashMap.get(SettingsAPIFields.URL.getRawValue());
        String str3 = (String) mapConvertJSONObjectToHashMap.get(SettingsAPIFields.DATASETID.getRawValue());
        String str4 = (String) mapConvertJSONObjectToHashMap.get(SettingsAPIFields.ACCESSKEY.getRawValue());
        if (str2 == null || str3 == null || str4 == null) {
            Intrinsics.checkNotNullExpressionValue(TAG2, "TAG");
            companion2.log(loggingBehavior2, TAG2, "CloudBridge Settings API response doesn't have valid data");
            return;
        }
        try {
            AppEventsConversionsAPITransformerWebRequests.configure(str3, str2, str4);
            setSavedCloudBridgeCredentials$facebook_core_release(mapConvertJSONObjectToHashMap);
            SettingsAPIFields settingsAPIFields = SettingsAPIFields.ENABLED;
            if (mapConvertJSONObjectToHashMap.get(settingsAPIFields.getRawValue()) != null) {
                Object obj2 = mapConvertJSONObjectToHashMap.get(settingsAPIFields.getRawValue());
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
                zBooleanValue = ((Boolean) obj2).booleanValue();
            }
            isEnabled = zBooleanValue;
        } catch (MalformedURLException e3) {
            Logger.Companion companion5 = Logger.Companion;
            LoggingBehavior loggingBehavior5 = LoggingBehavior.APP_EVENTS;
            String TAG5 = TAG;
            Intrinsics.checkNotNullExpressionValue(TAG5, "TAG");
            companion5.log(loggingBehavior5, TAG5, "CloudBridge Settings API response doesn't have valid url\n %s ", ExceptionsKt__ExceptionsKt.stackTraceToString(e3));
        }
    }
}

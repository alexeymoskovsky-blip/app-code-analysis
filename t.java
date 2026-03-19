package com.qiyukf.nimlib.report;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.qiyukf.nimlib.apm.model.BaseEventExtension;
import com.qiyukf.nimlib.apm.model.BaseEventModel;
import com.qiyukf.nimlib.n.w;
import com.qiyukf.nimlib.push.net.lbs.b;
import com.qiyukf.nimlib.sdk.SDKOptions;
import com.qiyukf.nimlib.sdk.SecondTimeoutConfig;
import com.qiyukf.nimlib.sdk.ServerAddresses;
import com.qiyukf.nimlib.sdk.mixpush.MixPushConfig;
import com.qiyukf.nimlib.sdk.msg.model.CaptureDeviceInfoConfig;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: compiled from: UILoginEventManager.java */
/* JADX INFO: loaded from: classes6.dex */
public final class t {
    private final Handler a = com.qiyukf.nimlib.d.b.a.c().a("event_thread");
    private com.qiyukf.nimlib.report.model.f b;

    /* JADX INFO: compiled from: UILoginEventManager.java */
    public static class a {
        private static final t a = new t();
    }

    public static t a() {
        return a.a;
    }

    public final void a(final String str) {
        this.a.post(new Runnable() { // from class: com.qiyukf.nimlib.report.t$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str) {
        try {
            com.qiyukf.nimlib.report.model.f fVar = new com.qiyukf.nimlib.report.model.f();
            fVar.setNtpTime(com.qiyukf.nimlib.report.d.a.a());
            fVar.setUserId(str);
            fVar.setAction("manual_login");
            fVar.a(w.a());
            fVar.setStartTime(com.qiyukf.nimlib.report.d.a.a(fVar.isNtpTime()));
            this.b = fVar;
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("UILoginEventManager", "startTrackLoginEvent Exception", th);
        }
    }

    public final void a(final String str, final b.EnumC0157b enumC0157b) {
        this.a.post(new Runnable() { // from class: com.qiyukf.nimlib.report.t$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b(str, enumC0157b);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str, b.EnumC0157b enumC0157b) {
        com.qiyukf.nimlib.report.model.f fVar = this.b;
        if (fVar != null) {
            fVar.b(str);
            this.b.a(enumC0157b);
        }
    }

    public final void a(final String str, final String str2) {
        this.a.post(new Runnable() { // from class: com.qiyukf.nimlib.report.t$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b(str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str, String str2) {
        try {
            if (Objects.equals(str, str2)) {
                return;
            }
            com.qiyukf.nimlib.apm.c.a(this.b, com.qiyukf.nimlib.report.extension.i.a(0, "switch_key", str, "currentAppKey = " + str2 + ",oldAppKey = " + str, true));
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("UILoginEventManager", "updateSwitchKey Exception", th);
        }
    }

    public final void a(final com.qiyukf.nimlib.report.extension.i iVar) {
        if (iVar == null) {
            return;
        }
        this.a.post(new Runnable() { // from class: com.qiyukf.nimlib.report.t$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b(iVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(com.qiyukf.nimlib.report.extension.i iVar) {
        try {
            com.qiyukf.nimlib.apm.c.a(this.b, iVar);
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("UILoginEventManager", "receivePushEventExtension Exception", th);
        }
    }

    public final void a(final com.qiyukf.nimlib.report.model.f fVar) {
        if (fVar == null) {
            return;
        }
        this.a.post(new Runnable() { // from class: com.qiyukf.nimlib.report.t$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.c(fVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(com.qiyukf.nimlib.report.model.f fVar) {
        try {
            if (fVar.c()) {
                b(fVar);
                com.qiyukf.nimlib.apm.c.a(FirebaseAnalytics.Event.LOGIN, (BaseEventModel<? extends BaseEventExtension>) fVar);
            } else {
                com.qiyukf.nimlib.apm.c.a(this.b, fVar);
            }
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("UILoginEventManager", "receivePushLoginEvent Exception", th);
        }
    }

    public final void a(final int i) {
        this.a.post(new Runnable() { // from class: com.qiyukf.nimlib.report.t$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.b(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(int i) {
        com.qiyukf.nimlib.report.model.f fVar = this.b;
        this.b = null;
        boolean z = i == 200;
        if (fVar == null) {
            return;
        }
        try {
            fVar.setSucceed(z);
            fVar.setStopTime(com.qiyukf.nimlib.report.d.a.a(fVar.isNtpTime()));
            try {
                com.qiyukf.nimlib.report.extension.i iVarD = com.qiyukf.nimlib.report.extension.i.d();
                iVarD.a(i == 200);
                iVarD.a(i);
                if (fVar.b() == b.EnumC0157b.TCP) {
                    iVarD.d(com.qiyukf.nimlib.biz.k.a().g());
                }
                iVarD.b(fVar.a());
                if (i != 200) {
                    Context contextD = com.qiyukf.nimlib.c.d();
                    if (contextD != null) {
                        iVarD.c(String.format("UI Process login response error, isNetAvailable: %s isNetworkConnected: %s", Boolean.valueOf(com.qiyukf.nimlib.n.p.b(contextD)), Boolean.valueOf(com.qiyukf.nimlib.network.h.a(contextD))));
                    } else {
                        iVarD.c("UI Process login response error, context is null");
                    }
                }
                iVarD.a("protocol");
                iVarD.a(fVar.getStartTime());
                iVarD.b(com.qiyukf.nimlib.report.d.a.a(fVar.isNtpTime()));
                List<com.qiyukf.nimlib.report.extension.i> extension = fVar.getExtension();
                if (com.qiyukf.nimlib.n.e.c((Collection) extension)) {
                    for (com.qiyukf.nimlib.report.extension.i iVar : extension) {
                        if (TextUtils.equals(iVar.b(), "protocol")) {
                            com.qiyukf.nimlib.log.c.b.a.e("UILoginEventManager", String.format("loginResponse skip as exist old %s new %s", iVar, iVarD));
                            break;
                        }
                    }
                    com.qiyukf.nimlib.apm.c.a(fVar, iVarD);
                } else {
                    com.qiyukf.nimlib.apm.c.a(fVar, iVarD);
                }
            } catch (Throwable th) {
                com.qiyukf.nimlib.log.c.b.a.d("UILoginEventManager", "loginResponseFailed Exception", th);
            }
            b(fVar);
            com.qiyukf.nimlib.apm.c.a(FirebaseAnalytics.Event.LOGIN, (BaseEventModel<? extends BaseEventExtension>) fVar);
        } catch (Throwable th2) {
            com.qiyukf.nimlib.log.c.b.a.d("UILoginEventManager", "stopTrackLoginEvent Exception", th2);
        }
    }

    private static void b(com.qiyukf.nimlib.report.model.f fVar) {
        if (fVar == null) {
            return;
        }
        if (com.qiyukf.nimlib.c.K()) {
            com.qiyukf.nimlib.apm.c.a(fVar, com.qiyukf.nimlib.report.extension.i.a(0, "lazy_init", null, "lazy init", true));
        }
        com.qiyukf.nimlib.apm.c.a(fVar, com.qiyukf.nimlib.report.extension.i.a(0, "conf_init", null, b(), true));
    }

    private static String b() {
        SDKOptions sDKOptionsH = com.qiyukf.nimlib.c.h();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("app_key", sDKOptionsH.appKey);
            jSONObject.putOpt("use_asset_server_address_config", Boolean.valueOf(sDKOptionsH.useAssetServerAddressConfig));
            jSONObject.putOpt("sdk_storage_root_path", sDKOptionsH.sdkStorageRootPath);
            jSONObject.putOpt("database_encrypt_key", com.qiyukf.nimlib.log.b.a.a(sDKOptionsH.databaseEncryptKey));
            jSONObject.putOpt("preload_attach", Boolean.valueOf(sDKOptionsH.preloadAttach));
            jSONObject.putOpt("thumbnail_size", Integer.valueOf(sDKOptionsH.thumbnailSize));
            jSONObject.putOpt("session_read_ack", Boolean.valueOf(sDKOptionsH.sessionReadAck));
            jSONObject.putOpt("improve_sdk_process_priority", Boolean.valueOf(sDKOptionsH.improveSDKProcessPriority));
            ServerAddresses serverAddresses = sDKOptionsH.serverConfig;
            JSONObject json = null;
            jSONObject.putOpt("server_config", serverAddresses == null ? null : serverAddresses.toJson());
            jSONObject.putOpt("pre_load_servers", Boolean.valueOf(sDKOptionsH.preLoadServers));
            jSONObject.putOpt("team_notification_message_mark_unread", Boolean.valueOf(sDKOptionsH.teamNotificationMessageMarkUnread));
            jSONObject.putOpt("use_x_log", Boolean.valueOf(sDKOptionsH.useXLog));
            jSONObject.putOpt("animated_image_thumbnail_enabled", Boolean.valueOf(sDKOptionsH.animatedImageThumbnailEnabled));
            jSONObject.putOpt("async_init_sdk", Boolean.valueOf(sDKOptionsH.asyncInitSDK));
            jSONObject.putOpt("reduced_im", Boolean.valueOf(sDKOptionsH.reducedIM));
            jSONObject.putOpt("check_manifest_config", Boolean.valueOf(sDKOptionsH.checkManifestConfig));
            MixPushConfig mixPushConfig = sDKOptionsH.mixPushConfig;
            jSONObject.putOpt("mix_push_config", mixPushConfig == null ? null : mixPushConfig.toJson());
            jSONObject.putOpt("enable_back_off_reconnect_strategy", Boolean.valueOf(sDKOptionsH.enableBackOffReconnectStrategy));
            jSONObject.putOpt("enable_background_keep_alive", Boolean.valueOf(sDKOptionsH.enableBackgroundKeepAlive));
            jSONObject.putOpt("enable_lbs_optimize", Boolean.valueOf(sDKOptionsH.enableLBSOptimize));
            jSONObject.putOpt("enable_team_msg_ack", Boolean.valueOf(sDKOptionsH.enableTeamMsgAck));
            jSONObject.putOpt("should_consider_revoked_message_unread_count", Boolean.valueOf(sDKOptionsH.shouldConsiderRevokedMessageUnreadCount));
            jSONObject.putOpt("use_nt_server", Boolean.valueOf(sDKOptionsH.useNtServer));
            jSONObject.putOpt("login_custom_tag", sDKOptionsH.loginCustomTag);
            jSONObject.putOpt("disable_awake", Boolean.valueOf(sDKOptionsH.disableAwake));
            jSONObject.putOpt("fetch_server_time_interval", Long.valueOf(sDKOptionsH.fetchServerTimeInterval));
            jSONObject.putOpt("report_im_log", Boolean.valueOf(sDKOptionsH.reportImLog));
            jSONObject.putOpt("custom_push_content_type", sDKOptionsH.customPushContentType);
            jSONObject.putOpt("notify_stick_top_session", Boolean.valueOf(sDKOptionsH.notifyStickTopSession));
            jSONObject.putOpt("enable_foreground_service", Boolean.valueOf(sDKOptionsH.enableForegroundService));
            jSONObject.putOpt("cdn_request_data_interval", Integer.valueOf(sDKOptionsH.cdnRequestDataInterval));
            jSONObject.putOpt("rollback_sql_cipher", Boolean.valueOf(sDKOptionsH.rollbackSQLCipher));
            jSONObject.putOpt("core_process_start_timeout", Integer.valueOf(sDKOptionsH.coreProcessStartTimeout));
            jSONObject.putOpt("clear_time_tag_at_beginning", Boolean.valueOf(sDKOptionsH.clearTimeTagAtBeginning));
            jSONObject.putOpt("enable_database_backup", Boolean.valueOf(sDKOptionsH.enableDatabaseBackup));
            CaptureDeviceInfoConfig captureDeviceInfoConfig = sDKOptionsH.captureDeviceInfoConfig;
            jSONObject.putOpt("capture_device_info_config", captureDeviceInfoConfig == null ? null : captureDeviceInfoConfig.toJson());
            SecondTimeoutConfig secondTimeoutConfig = sDKOptionsH.secondTimeoutForSendMessage;
            if (secondTimeoutConfig != null) {
                json = secondTimeoutConfig.toJson();
            }
            jSONObject.putOpt("second_timeout_for_send_message", json);
            jSONObject.putOpt("flutter_sdk_version", sDKOptionsH.flutterSdkVersion);
            jSONObject.putOpt("enable_lose_connection", Boolean.valueOf(sDKOptionsH.enableLoseConnection));
            jSONObject.putOpt("enable_mix_store", Boolean.valueOf(com.qiyukf.nimlib.biz.n.a()));
            jSONObject.putOpt("enable_fcs", Boolean.valueOf(com.qiyukf.nimlib.c.G()));
            jSONObject.putOpt("enable_fcs_next", Boolean.valueOf(com.qiyukf.nimlib.abtest.b.k()));
            jSONObject.putOpt("has_fcs_next", Boolean.valueOf(com.qiyukf.nimlib.plugin.b.a().b() != null));
            jSONObject.putOpt("enable_quic_link", Boolean.valueOf(com.qiyukf.nimlib.abtest.b.t()));
            jSONObject.putOpt("enable_websocket_link", Boolean.valueOf(com.qiyukf.nimlib.abtest.b.s()));
        } catch (Throwable th) {
            com.qiyukf.nimlib.log.c.b.a.d("UILoginEventManager", "getSDKOptionsJson Exception", th);
        }
        return jSONObject.toString();
    }
}

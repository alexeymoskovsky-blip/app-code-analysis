package com.facebook.appevents;

import androidx.annotation.RestrictTo;
import com.facebook.appevents.AppEventsManager;
import com.facebook.appevents.aam.MetadataIndexer;
import com.facebook.appevents.cloudbridge.AppEventsCAPIManager;
import com.facebook.appevents.eventdeactivation.EventDeactivationManager;
import com.facebook.appevents.gps.ara.GpsAraTriggersManager;
import com.facebook.appevents.gps.pa.PACustomAudienceClient;
import com.facebook.appevents.gps.topics.GpsTopicsManager;
import com.facebook.appevents.iap.InAppPurchaseManager;
import com.facebook.appevents.integrity.BannedParamManager;
import com.facebook.appevents.integrity.BlocklistEventsManager;
import com.facebook.appevents.integrity.MACARuleMatchingManager;
import com.facebook.appevents.integrity.ProtectedModeManager;
import com.facebook.appevents.integrity.RedactedEventsManager;
import com.facebook.appevents.integrity.SensitiveParamsManager;
import com.facebook.appevents.integrity.StdParamsEnforcementManager;
import com.facebook.appevents.ml.ModelManager;
import com.facebook.appevents.restrictivedatafilter.RestrictiveDataManager;
import com.facebook.internal.FeatureManager;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.instrument.crashshield.CrashShieldHandler;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AppEventsManager.kt */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class AppEventsManager {

    @NotNull
    public static final AppEventsManager INSTANCE = new AppEventsManager();

    private AppEventsManager() {
    }

    @JvmStatic
    public static final void start() {
        if (CrashShieldHandler.isObjectCrashing(AppEventsManager.class)) {
            return;
        }
        try {
            FetchedAppSettingsManager.getAppSettingsAsync(new AnonymousClass1());
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, AppEventsManager.class);
        }
    }

    /* JADX INFO: renamed from: com.facebook.appevents.AppEventsManager$start$1 */
    /* JADX INFO: compiled from: AppEventsManager.kt */
    public static final class AnonymousClass1 implements FetchedAppSettingsManager.FetchedAppSettingsCallback {
        @Override // com.facebook.internal.FetchedAppSettingsManager.FetchedAppSettingsCallback
        public void onError() {
        }

        @Override // com.facebook.internal.FetchedAppSettingsManager.FetchedAppSettingsCallback
        public void onSuccess(@Nullable FetchedAppSettings fetchedAppSettings) {
            FeatureManager.checkFeature(FeatureManager.Feature.AAM, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda0
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$0(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.RestrictiveDataFiltering, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda7
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$1(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.PrivacyProtection, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda8
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$2(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.EventDeactivation, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda9
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$3(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.BannedParamFiltering, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda10
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$4(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.IapLogging, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda11
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$5(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.StdParamEnforcement, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda12
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$6(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.ProtectedMode, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda13
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$7(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.MACARuleMatching, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda14
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$8(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.BlocklistEvents, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda15
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$9(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.FilterRedactedEvents, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda1
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$10(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.FilterSensitiveParams, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda2
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$11(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.CloudBridge, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda3
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$12(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.GPSARATriggers, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda4
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$13(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.GPSPACAProcessing, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda5
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$14(z);
                }
            });
            FeatureManager.checkFeature(FeatureManager.Feature.GPSTopicsObservation, new FeatureManager.Callback() { // from class: com.facebook.appevents.AppEventsManager$start$1$$ExternalSyntheticLambda6
                @Override // com.facebook.internal.FeatureManager.Callback
                public final void onCompleted(boolean z) {
                    AppEventsManager.AnonymousClass1.onSuccess$lambda$15(z);
                }
            });
        }

        public static final void onSuccess$lambda$0(boolean z) {
            if (z) {
                MetadataIndexer.enable();
            }
        }

        public static final void onSuccess$lambda$1(boolean z) {
            if (z) {
                RestrictiveDataManager.enable();
            }
        }

        public static final void onSuccess$lambda$2(boolean z) {
            if (z) {
                ModelManager.enable();
            }
        }

        public static final void onSuccess$lambda$3(boolean z) {
            if (z) {
                EventDeactivationManager.enable();
            }
        }

        public static final void onSuccess$lambda$4(boolean z) {
            if (z) {
                BannedParamManager.enable();
            }
        }

        public static final void onSuccess$lambda$5(boolean z) {
            if (z) {
                InAppPurchaseManager.enableAutoLogging();
            }
        }

        public static final void onSuccess$lambda$6(boolean z) {
            if (z) {
                StdParamsEnforcementManager.enable();
            }
        }

        public static final void onSuccess$lambda$7(boolean z) {
            if (z) {
                ProtectedModeManager.enable();
            }
        }

        public static final void onSuccess$lambda$8(boolean z) {
            if (z) {
                MACARuleMatchingManager.enable();
            }
        }

        public static final void onSuccess$lambda$9(boolean z) {
            if (z) {
                BlocklistEventsManager.enable();
            }
        }

        public static final void onSuccess$lambda$10(boolean z) {
            if (z) {
                RedactedEventsManager.enable();
            }
        }

        public static final void onSuccess$lambda$11(boolean z) {
            if (z) {
                SensitiveParamsManager.enable();
            }
        }

        public static final void onSuccess$lambda$12(boolean z) {
            if (z) {
                AppEventsCAPIManager.enable();
            }
        }

        public static final void onSuccess$lambda$13(boolean z) {
            if (z) {
                GpsAraTriggersManager.enable();
            }
        }

        public static final void onSuccess$lambda$14(boolean z) {
            if (z) {
                PACustomAudienceClient.enable();
            }
        }

        public static final void onSuccess$lambda$15(boolean z) {
            if (z) {
                GpsTopicsManager.enableTopicsObservation();
            }
        }
    }
}

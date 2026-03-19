package com.petkit.android.activities.feeder.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.api.OtaCheckRsp;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.OtaCheckResult;
import com.petkit.android.api.http.apiResponse.OtaStatusRsp;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class FeederOtaActivity extends BaseActivity {
    private static final int OTA_NEED_UPGRADE = 0;
    private static final int OTA_NO_UPGRADE = 6;
    private static final int OTA_READY = 1;
    private static final int OTA_UPGRADE_FAILED = 4;
    private static final int OTA_UPGRADE_SUCCEED = 3;
    private static final int OTA_UPGRADE_WAITING = 5;
    private static final int OTA_UPGRADING = 2;
    private String deviceId;
    private OtaCheckResult mOtaCheckResult = new OtaCheckResult();
    private int mCurMode = 0;
    private boolean chatNotification = false;
    private boolean inProgress = false;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() { // from class: com.petkit.android.activities.feeder.setting.FeederOtaActivity.1
        @Override // java.lang.Runnable
        public void run() {
            FeederOtaActivity.this.otaStatus();
        }
    };
    private Runnable mCheckRunnable = new Runnable() { // from class: com.petkit.android.activities.feeder.setting.FeederOtaActivity.2
        @Override // java.lang.Runnable
        public void run() {
            FeederOtaActivity.this.otaCheck();
        }
    };
    private boolean isAnimationSet = false;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.deviceId = bundle.getString(FeederUtils.EXTRA_FEEDER_ID);
            this.chatNotification = bundle.getBoolean("chatNotification");
        } else {
            this.deviceId = getIntent().getStringExtra(FeederUtils.EXTRA_FEEDER_ID);
            this.chatNotification = getIntent().getBooleanExtra("chatNotification", false);
        }
        if (isEmpty(this.deviceId)) {
            finish();
        } else {
            setContentView(R.layout.activity_feeder_ota);
            initView();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(FeederUtils.EXTRA_FEEDER_ID, this.deviceId);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.OTA);
        setViewState(0);
        otaCheck();
    }

    private void initView() {
        findViewById(R.id.ota_btn).setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress(int i, int i2, int i3) {
        updateView(i, i2);
        ((TextView) findViewById(R.id.ota_upgrade_left_time)).setText(getString(R.string.Mate_ota_left_time_mintus_format, "" + i3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateView(int i, int i2) {
        updateView(i, i2, "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateView(int i, int i2, String str) {
        setViewState(1);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ota_upgrade);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.ota_no_upgrade);
        Button button = (Button) findViewById(R.id.ota_btn);
        TextView textView = (TextView) findViewById(R.id.ota_upgrade_state);
        TextView textView2 = (TextView) findViewById(R.id.ota_version);
        TextView textView3 = (TextView) findViewById(R.id.ota_release_time);
        TextView textView4 = (TextView) findViewById(R.id.ota_upgrade_note);
        TextView textView5 = (TextView) findViewById(R.id.ota_upgrade_tips_text);
        TextView textView6 = (TextView) findViewById(R.id.ota_upgrade_left_time);
        this.mCurMode = i;
        if (i == 0) {
            linearLayout.setVisibility(0);
            relativeLayout.setVisibility(8);
            textView2.setText(getString(R.string.Mate_ota_version_format, " v" + this.mOtaCheckResult.getVersion()));
            textView3.setText(getString(R.string.Mate_ota_publish_format, DateUtil.getFormatDateFromString(this.mOtaCheckResult.getCreatedAt())));
            textView4.setText(this.mOtaCheckResult.getReleaseNotes());
            button.setText(getString(R.string.Mate_ota_start));
            button.setVisibility(0);
            textView6.setText("");
            findViewById(R.id.ota_upgrade_view).setVisibility(8);
            return;
        }
        if (i == 1) {
            if (!this.isAnimationSet) {
                startProgressAnimation();
            }
            textView2.setText(getString(R.string.Mate_ota_version_format, " v" + this.mOtaCheckResult.getVersion()));
            textView3.setText(getString(R.string.Mate_ota_publish_format, DateUtil.getFormatDateFromString(this.mOtaCheckResult.getCreatedAt())));
            linearLayout.setVisibility(0);
            relativeLayout.setVisibility(8);
            findViewById(R.id.ota_upgrade_view).setVisibility(0);
            textView4.setText(this.mOtaCheckResult.getReleaseNotes());
            textView.setText(getString(R.string.Mate_ota_updating));
            return;
        }
        if (i == 2) {
            if (!this.isAnimationSet) {
                startProgressAnimation();
            }
            textView2.setText(getString(R.string.Mate_ota_version_format, " v" + this.mOtaCheckResult.getVersion()));
            textView3.setText(getString(R.string.Mate_ota_publish_format, DateUtil.getFormatDateFromString(this.mOtaCheckResult.getCreatedAt())));
            linearLayout.setVisibility(0);
            relativeLayout.setVisibility(8);
            findViewById(R.id.ota_upgrade_view).setVisibility(0);
            button.setVisibility(8);
            textView4.setText(this.mOtaCheckResult.getReleaseNotes());
            textView.setText(getString(R.string.Mate_otaing_format, i2 + "%"));
            return;
        }
        if (i == 4) {
            linearLayout.setVisibility(0);
            relativeLayout.setVisibility(8);
            String string = str;
            if (isEmpty(string)) {
                string = getString(R.string.Mate_ota_fail);
            }
            textView6.setText(string);
            textView2.setText(getString(R.string.Mate_ota_version_format, " v" + this.mOtaCheckResult.getVersion()));
            textView3.setText(getString(R.string.Mate_ota_publish_format, DateUtil.getFormatDateFromString(this.mOtaCheckResult.getCreatedAt())));
            textView4.setText(this.mOtaCheckResult.getReleaseNotes());
            findViewById(R.id.ota_upgrade_view).setVisibility(8);
            button.setText(getString(R.string.Retry));
            button.setVisibility(0);
            button.setOnClickListener(this);
            button.setBackgroundResource(R.drawable.selector_solid_feeder_main_color_with_radius);
            clearProgressAnimation();
            return;
        }
        if (i != 5) {
            if (i != 6) {
                return;
            }
            linearLayout.setVisibility(8);
            relativeLayout.setVisibility(0);
            textView5.setText(R.string.Mate_ota_no_upgrade);
            textView6.setText("");
            clearProgressAnimation();
            showAnimation();
            DeviceCenterUtils.deleteFeederOtaDevicesFlag(Long.valueOf(this.deviceId).longValue());
            return;
        }
        linearLayout.setVisibility(0);
        relativeLayout.setVisibility(8);
        textView2.setText(getString(R.string.Mate_ota_version_format, " v" + this.mOtaCheckResult.getVersion()));
        textView3.setText(getString(R.string.Mate_ota_publish_format, DateUtil.getFormatDateFromString(this.mOtaCheckResult.getCreatedAt())));
        textView4.setText(this.mOtaCheckResult.getReleaseNotes());
        button.setText(getString(R.string.Mate_ota_start));
        button.setVisibility(0);
        button.setBackgroundResource(R.drawable.solid_gray_with_radius);
        button.setOnClickListener(null);
        textView6.setText(R.string.Feeder_ota_start_offline_prompt);
        findViewById(R.id.ota_upgrade_view).setVisibility(8);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.ota_btn) {
            int i = this.mCurMode;
            if (i == 0 || i == 4) {
                otaUpgrade();
            } else {
                view.setOnClickListener(null);
            }
        }
    }

    private void removeRunnable() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mRunnable);
            this.mHandler.removeCallbacks(this.mCheckRunnable);
            this.mHandler = null;
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        removeRunnable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void otaCheck() {
        if (isFinishing()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", this.deviceId);
        post(ApiTools.SAMPLET_API_FEEDER_OTA_CHECK, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feeder.setting.FeederOtaActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                OtaCheckRsp otaCheckRsp = (OtaCheckRsp) this.gson.fromJson(this.responseResult, OtaCheckRsp.class);
                if (otaCheckRsp != null) {
                    if (otaCheckRsp.getError() == null) {
                        if (otaCheckRsp.getResult() != null) {
                            FeederOtaActivity.this.mOtaCheckResult = otaCheckRsp.getResult();
                        }
                        if (FeederOtaActivity.this.mHandler != null) {
                            FeederOtaActivity.this.mHandler.removeCallbacks(FeederOtaActivity.this.mCheckRunnable);
                        }
                        if (FeederOtaActivity.this.mOtaCheckResult != null) {
                            FeederOtaActivity feederOtaActivity = FeederOtaActivity.this;
                            if (!feederOtaActivity.isEmpty(feederOtaActivity.mOtaCheckResult.getVersion())) {
                                FeederOtaActivity.this.otaStatus();
                                return;
                            }
                        }
                        FeederOtaActivity feederOtaActivity2 = FeederOtaActivity.this;
                        feederOtaActivity2.updateOtaDevice(feederOtaActivity2.deviceId);
                        FeederOtaActivity.this.updateView(6, 0);
                        FeederOtaActivity.this.inProgress = false;
                        FeederOtaActivity.this.sendOtaResultBroadcast(2);
                        FeederOtaActivity.this.changeFeederRecordState(1);
                        return;
                    }
                    FeederOtaActivity.this.showLongToast(otaCheckRsp.getError().getMsg());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (FeederOtaActivity.this.mHandler != null) {
                    FeederOtaActivity.this.mHandler.postDelayed(FeederOtaActivity.this.mCheckRunnable, 3000L);
                }
            }
        }, false);
    }

    private void otaUpgrade() {
        HashMap map = new HashMap();
        map.put("deviceId", this.deviceId);
        map.put("firmwareId", this.mOtaCheckResult.getFirmwareId());
        post(ApiTools.SAMPLET_API_FEEDER_OTA_START, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feeder.setting.FeederOtaActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp != null) {
                    if (baseRsp.getError() == null) {
                        FeederOtaActivity.this.changeFeederRecordState(4);
                        FeederOtaActivity.this.otaStatus();
                        FeederOtaActivity.this.startProgressAnimation();
                        return;
                    }
                    FeederOtaActivity.this.showLongToast(baseRsp.getError().getMsg());
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void otaStatus() {
        if (isFinishing()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", this.deviceId);
        post(ApiTools.SAMPLET_API_FEEDER_OTA_STATUS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feeder.setting.FeederOtaActivity.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                OtaStatusRsp otaStatusRsp = (OtaStatusRsp) this.gson.fromJson(this.responseResult, OtaStatusRsp.class);
                if (FeederOtaActivity.this.isFinishing() || otaStatusRsp == null) {
                    return;
                }
                if (otaStatusRsp.getError() == null && otaStatusRsp.getResult() != null) {
                    int status = otaStatusRsp.getResult().getStatus();
                    int progress = otaStatusRsp.getResult().getProgress();
                    int leftTime = otaStatusRsp.getResult().getLeftTime();
                    if (status == 0) {
                        FeederOtaActivity.this.updateView(0, 0);
                        return;
                    }
                    if (status == 1 || status == 2) {
                        FeederOtaActivity.this.inProgress = true;
                        FeederOtaActivity.this.sendOtaResultBroadcast(1);
                        FeederOtaActivity.this.updateProgress(2, progress, leftTime);
                        FeederOtaActivity.this.mHandler.postDelayed(FeederOtaActivity.this.mRunnable, status == 2 ? 5000L : 3000L);
                        return;
                    }
                    if (status != 3) {
                        if (status == 4) {
                            FeederOtaActivity.this.updateView(4, 0, otaStatusRsp.getResult().getReason());
                            FeederOtaActivity.this.sendOtaResultBroadcast(3);
                            return;
                        } else {
                            if (status != 5) {
                                return;
                            }
                            FeederOtaActivity.this.updateView(5, 0);
                            FeederOtaActivity.this.mHandler.postDelayed(FeederOtaActivity.this.mRunnable, status == 2 ? 5000L : 3000L);
                            return;
                        }
                    }
                    if (FeederOtaActivity.this.inProgress) {
                        FeederOtaActivity.this.updateView(6, 0);
                        FeederOtaActivity.this.sendOtaResultBroadcast(2);
                        return;
                    }
                    if (FeederOtaActivity.this.mOtaCheckResult != null) {
                        FeederOtaActivity feederOtaActivity = FeederOtaActivity.this;
                        if (!feederOtaActivity.isEmpty(feederOtaActivity.mOtaCheckResult.getVersion())) {
                            FeederOtaActivity.this.updateView(0, 0);
                            return;
                        }
                    }
                    FeederOtaActivity.this.updateView(6, 0);
                    return;
                }
                if (otaStatusRsp.getError() != null) {
                    FeederOtaActivity.this.showLongToast(otaStatusRsp.getError().getMsg());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (FeederOtaActivity.this.mHandler != null) {
                    FeederOtaActivity.this.mHandler.postDelayed(FeederOtaActivity.this.mRunnable, 3000L);
                }
            }
        }, false);
    }

    private void resetOtaStatus() {
        HashMap map = new HashMap();
        map.put("deviceId", this.deviceId);
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_OTA_RESET, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feeder.setting.FeederOtaActivity.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp == null || baseRsp.getError() == null) {
                    return;
                }
                FeederOtaActivity.this.showLongToast(baseRsp.getError().getMsg());
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOtaDevice(String str) {
        ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
        String updatematedevices = chatCenter.getUpdatematedevices();
        if (isEmpty(updatematedevices) || !updatematedevices.contains(str)) {
            return;
        }
        chatCenter.setUpdatematedevices(updatematedevices.replace("&" + str, ""));
        SugarRecord.save(chatCenter);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_UPDATE_DEVICE_UPDATE_NOTIFICATION));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOtaResultBroadcast(int i) {
        Intent intent = new Intent(FeederUtils.BROADCAST_FEEDER_OTA);
        intent.putExtra(Constants.EXTRA_TYPE, i);
        intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, this.deviceId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void showAnimation() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(0.6f, 1.0f, 0.6f, 1.0f, 1, 0.5f, 1, 0.5f));
        animationSet.addAnimation(new AlphaAnimation(0.1f, 1.0f));
        animationSet.setDuration(1000L);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);
        imageView.startAnimation(animationSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startProgressAnimation() {
        ImageView imageView = (ImageView) findViewById(R.id.ota_animation_left);
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(this, R.anim.translation_in);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        animationLoadAnimation.setInterpolator(linearInterpolator);
        imageView.startAnimation(animationLoadAnimation);
        ImageView imageView2 = (ImageView) findViewById(R.id.ota_animation_right);
        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(this, R.anim.translation_out);
        animationLoadAnimation2.setInterpolator(linearInterpolator);
        imageView2.startAnimation(animationLoadAnimation2);
        this.isAnimationSet = true;
    }

    private void clearProgressAnimation() {
        ((ImageView) findViewById(R.id.ota_animation_left)).clearAnimation();
        ((ImageView) findViewById(R.id.ota_animation_right)).clearAnimation();
        this.isAnimationSet = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeFeederRecordState(int i) {
        FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(Long.valueOf(this.deviceId).longValue());
        feederRecordByDeviceId.setState(i);
        feederRecordByDeviceId.save();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_UPDATE));
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_STATE_CHANGED));
    }
}

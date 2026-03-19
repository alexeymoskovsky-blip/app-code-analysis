package com.petkit.android.activities.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.LoadDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.im.iot.NewIotManager;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.cs.CsUtils;
import com.petkit.android.activities.doctor.utils.DoctorUtils;
import com.petkit.android.activities.feed.WeightUnitSettingActivity;
import com.petkit.android.activities.mall.utils.ShopifyUtils;
import com.petkit.android.activities.petkitBleDevice.PrivacyActivity;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.registe.NormalLoginActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.PushTokenRsp;
import com.petkit.android.utils.AppUpgradeController;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.Timer;
import java.util.TimerTask;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes5.dex */
public class SettingActivity extends BaseActivity {
    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_setting);
        EventBus.getDefault().register(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Settings);
        setTitleLineVisibility(8);
        initView();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void refreshUpdateState(CheckedUpdateEvent checkedUpdateEvent) {
        findViewById(R.id.tv_new).setVisibility(8);
    }

    private void initView() {
        findViewById(R.id.setting_account_manage).setOnClickListener(this);
        findViewById(R.id.setting_modify_pw).setOnClickListener(this);
        findViewById(R.id.medical_privacy).setOnClickListener(this);
        findViewById(R.id.setting_notify).setOnClickListener(this);
        findViewById(R.id.setting_about).setOnClickListener(this);
        findViewById(R.id.setting_logout).setOnClickListener(this);
        findViewById(R.id.setting_clear_cache).setOnClickListener(this);
        findViewById(R.id.setting_weight_unit).setOnClickListener(this);
        findViewById(R.id.setting_privacy).setOnClickListener(this);
        findViewById(R.id.setting_region).setOnClickListener(this);
        findViewById(R.id.setting_language).setOnClickListener(this);
        if (DataHelper.getBooleanSF(this, "needUpdate")) {
            findViewById(R.id.tv_new).setVisibility(0);
        } else {
            findViewById(R.id.tv_new).setVisibility(8);
        }
        if (UserInforUtils.isPetPenSwitchOpen()) {
            ((TextView) findViewById(R.id.setting_privacy)).setText(R.string.User_setting_privacy);
        } else {
            ((TextView) findViewById(R.id.setting_privacy)).setText(R.string.Personalized_recommendations);
        }
        if (DoctorUtils.getInstance().isTestUser(this)) {
            findViewById(R.id.ll_medical_privacy).setVisibility(0);
        } else {
            findViewById(R.id.ll_medical_privacy).setVisibility(8);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.setting_modify_pw || view.getId() == R.id.setting_account_manage) {
            startActivity(new Intent(this, (Class<?>) AccountsManageActivity.class));
            return;
        }
        if (view.getId() == R.id.setting_about) {
            startActivity(AboutActivity.class, false);
            return;
        }
        if (view.getId() == R.id.medical_privacy) {
            startActivity(PrivacyActivity.newIntent(this, 1));
            return;
        }
        if (view.getId() == R.id.setting_logout) {
            new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Confirm_logout).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.setting.SettingActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$onClick$0(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) null).show();
            return;
        }
        if (view.getId() == R.id.setting_clear_cache) {
            LoadDialog.show(this, getString(R.string.Clear_cache));
            AppUpgradeController.startDeleteCacheFiles();
            new Timer().schedule(new AnonymousClass1(), 3000L);
            return;
        }
        if (view.getId() == R.id.setting_weight_unit) {
            startActivity(WeightUnitSettingActivity.class, false);
            return;
        }
        if (view.getId() == R.id.setting_privacy) {
            if (UserInforUtils.isPetPenSwitchOpen()) {
                startActivity(PrivacySettingActivity.class, false);
                return;
            } else {
                startActivity(new Intent(this, (Class<?>) PersonalizedRecommendationsActivity.class));
                return;
            }
        }
        if (view.getId() == R.id.setting_notify) {
            startActivity(NotifySettingActivity.class, false);
        } else if (view.getId() == R.id.setting_language) {
            startActivity(LanguageSettingActivity.class, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(DialogInterface dialogInterface, int i) {
        unregisterPushToken();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.setting.SettingActivity$1, reason: invalid class name */
    public class AnonymousClass1 extends TimerTask {
        public AnonymousClass1() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            SettingActivity.this.runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.setting.SettingActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$run$0();
                }
            });
        }

        public final /* synthetic */ void lambda$run$0() {
            SettingActivity.this.showShortToast(R.string.Success, R.drawable.toast_succeed);
            LoadDialog.dismissDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doLogout() {
        CsUtils.getInstance().logout(this);
        CommonUtils.addSysMap(this, Consts.SHARED_SESSION_ID, "");
        AsyncHttpUtil.addHttpHeader(Consts.HTTP_HEADER_SESSION, "");
        DeviceCenterUtils.resetInfoWhileLogout();
        ShopifyUtils.clearAll();
        CommonUtils.logoutClearSp(this);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_LOGOUT));
        startActivity(NormalLoginActivity.class, true);
        overridePendingTransition(0, 0);
        NewIotManager.getInstance().destroy();
        DataHelper.setStringSF(this, DeviceCenterUtils.SP_DEVICE_LIST + FamilyUtils.getInstance().getCurrentFamilyId(CommonUtils.getAppContext()) + CommonUtils.getCurrentUserId(), "");
        ColorUtils.clearAllPetColor();
    }

    private void unregisterPushToken() {
        LoadDialog.show(this);
        post(ApiTools.SAMPLE_API_USER_UNREGISTER_PUSH_TOKEN, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.setting.SettingActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                LoadDialog.dismissDialog();
                SettingActivity.this.doLogout();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                PushTokenRsp pushTokenRsp = (PushTokenRsp) this.gson.fromJson(this.responseResult, PushTokenRsp.class);
                if (pushTokenRsp.getError() == null && "success".equals(pushTokenRsp.getResult())) {
                    PetkitLog.d("unregisterPushToken success");
                }
            }
        }, false);
    }
}

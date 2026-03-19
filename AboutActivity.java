package com.petkit.android.activities.setting;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.AppUpdateRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.LinkedHashMap;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes5.dex */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_about);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Title_about_us);
        setTitleLineVisibility(8);
        findViewById(R.id.about_protocol).setOnClickListener(this);
        findViewById(R.id.about_privacy).setOnClickListener(this);
        findViewById(R.id.about_update).setOnClickListener(this);
        findViewById(R.id.about_official_web).setOnClickListener(this);
        if (DataHelper.getBooleanSF(this, "needUpdate")) {
            findViewById(R.id.tv_find_update).setVisibility(0);
        } else {
            findViewById(R.id.tv_find_update).setVisibility(8);
        }
        ((TextView) findViewById(R.id.about_version)).setText("V " + CommonUtils.getAppVersionName(this));
        DeviceUtils.getLanguageLocale(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.about_protocol) {
            startWebviewActivity(ApiTools.getWebUrlByKey("user_agreement"), getString(R.string.Settings_user_aggreement));
            return;
        }
        if (id == R.id.about_privacy) {
            startWebviewActivity(ApiTools.getWebUrlByKey("policy"), getString(R.string.Privacy_policy));
        } else if (id == R.id.about_update) {
            checkAppUpdate();
        } else if (id == R.id.about_official_web) {
            startWebviewActivity(ApiTools.getWebUrlByKey("official_site"), getString(R.string.Website));
        }
    }

    private void startWebviewActivity(String str, String str2) {
        startActivity(WebviewActivity.newIntent(this, str2, str));
    }

    private void checkAppUpdate() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("version", CommonUtils.getAppVersionName(this));
        linkedHashMap.put("appId", Constants.PETKIT_APP_ID);
        post(ApiTools.getServiceUpdateUri() + ApiTools.SAMPLE_API_APP_UPDATE, linkedHashMap, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.AboutActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                AppUpdateRsp appUpdateRsp = (AppUpdateRsp) this.gson.fromJson(this.responseResult, AppUpdateRsp.class);
                if (appUpdateRsp.getError() != null) {
                    AboutActivity.this.showLongToast(appUpdateRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (appUpdateRsp.getResult() != null && appUpdateRsp.getResult().getFile() != null && appUpdateRsp.getResult().getFile().getUrl() != null) {
                    DataHelper.setBooleanSF(BaseApplication.context, "update" + appUpdateRsp.getResult().getVersion(), Boolean.TRUE);
                    DataHelper.setBooleanSF(BaseApplication.context, "needUpdate", Boolean.FALSE);
                    AboutActivity.this.findViewById(R.id.tv_find_update).setVisibility(8);
                    EventBus.getDefault().post(new CheckedUpdateEvent());
                    AboutActivity.startDownload(AboutActivity.this, appUpdateRsp.getResult().getFile().getUrl());
                    return;
                }
                DataHelper.setBooleanSF(BaseApplication.context, "needUpdate", Boolean.FALSE);
                EventBus.getDefault().post(new CheckedUpdateEvent());
                AboutActivity.this.findViewById(R.id.tv_find_update).setVisibility(8);
                AboutActivity.this.showLongToast(R.string.Hint_no_update);
            }
        }, false);
    }

    public static void startDownload(Context context, String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
            intent.setPackage("com.android.vending");
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }
    }
}

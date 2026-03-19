package com.petkit.android.activities.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.petkit.android.activities.appwidget.provider.FeederAppWidgetProvider;
import com.petkit.android.activities.appwidget.provider.ToiletAppWidgetProvider;
import com.petkit.android.activities.appwidget.utils.AppWidgetUtils;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.im.iot.NewIotManager;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3BleUtils2;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5BleUtils2;
import com.petkit.android.activities.registe.AccountSelectRegionActivity;
import com.petkit.android.activities.registe.NormalLoginActivity;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class ReLoginDialogActivity extends Activity {
    String errorMessage;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.errorMessage = getIntent().getStringExtra("Error_message");
        setContentView(R.layout.layout_dialog);
        setFinishOnTouchOutside(false);
        initView();
        W5BleUtils2.getInstance().closeBle();
        CTW3BleUtils2.getInstance().closeBle();
        NewIotManager.getInstance().stop();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) || super.dispatchKeyEvent(keyEvent);
    }

    private void initView() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = BaseApplication.displayMetrics;
        attributes.width = (int) (((double) displayMetrics.widthPixels) * 0.8d);
        attributes.height = (int) (((double) displayMetrics.heightPixels) * 0.3d);
        window.setAttributes(attributes);
        ((TextView) findViewById(R.id.error_message)).setText(this.errorMessage);
        findViewById(R.id.dialog_sure).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.common.ReLoginDialogActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CommonUtils.addSysMap(Consts.SHARED_SESSION_ID, "");
                AsyncHttpUtil.addHttpHeader(Consts.HTTP_HEADER_SESSION, "");
                DeviceCenterUtils.resetInfoWhileLogout();
                NewIotManager.getInstance().destroy();
                LocalBroadcastManager.getInstance(ReLoginDialogActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_LOGOUT));
                String stringSF = DataHelper.getStringSF(ReLoginDialogActivity.this, Consts.ACCOUNT_REGION_STRING);
                if (stringSF == null || stringSF.length() == 0) {
                    ReLoginDialogActivity.this.startActivity(new Intent(ReLoginDialogActivity.this, (Class<?>) AccountSelectRegionActivity.class));
                } else {
                    Intent intent = new Intent();
                    intent.setClass(ReLoginDialogActivity.this, NormalLoginActivity.class);
                    intent.putExtra(Constants.EXTRA_CAN_GO_BACK, false);
                    ReLoginDialogActivity.this.startActivity(intent);
                }
                ReLoginDialogActivity.this.finish();
            }
        });
    }

    public void refreshAppWidget() {
        Intent intent = new Intent(this, (Class<?>) ToiletAppWidgetProvider.class);
        intent.setAction(AppWidgetUtils.APPWIDGET_CHECK_DEVICE_IS_AVAILABLE);
        sendBroadcast(intent);
        Intent intent2 = new Intent(this, (Class<?>) FeederAppWidgetProvider.class);
        intent2.setAction(AppWidgetUtils.APPWIDGET_CHECK_DEVICE_IS_AVAILABLE);
        sendBroadcast(intent2);
    }

    public static boolean isDialogNeedShowing() {
        return CommonUtils.isTopActivity("WelcomeActivity");
    }
}

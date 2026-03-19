package com.petkit.android.activities.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.im.iot.NewIotManager;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.registe.NormalLoginActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LoginUtils;
import com.petkit.android.utils.Md5;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.PhoneCodeView;
import com.petkit.oversea.R;
import com.qiyukf.nimlib.report.extension.DualStackEventExtension;
import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes5.dex */
public class CheckCodeActivity extends BaseActivity {
    Disposable disposable;
    private String email = "";
    private boolean isLogoff = false;
    private int mLeftSec;
    PhoneCodeView phoneCodeView;
    TextView tvLogin;
    TextView tvNumber;
    TextView tvResend;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_reset_password_code);
        this.email = UserInforUtils.getAccount().getEmail();
        if (bundle == null) {
            this.isLogoff = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
        } else {
            this.isLogoff = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
        }
        this.tvLogin = (TextView) findViewById(R.id.tv_login);
        TextView textView = (TextView) findViewById(R.id.tv_number);
        this.tvNumber = textView;
        textView.setText("+" + this.email);
        TextView textView2 = (TextView) findViewById(R.id.tv_resend);
        this.tvResend = textView2;
        textView2.setOnClickListener(this);
        this.tvLogin.setOnClickListener(this);
        PhoneCodeView phoneCodeView = (PhoneCodeView) findViewById(R.id.phone_code);
        this.phoneCodeView = phoneCodeView;
        phoneCodeView.setOnInputListener(new PhoneCodeView.OnInputListener() { // from class: com.petkit.android.activities.setting.CheckCodeActivity.1
            @Override // com.petkit.android.widget.PhoneCodeView.OnInputListener
            public void onSuccess(String str) {
                CheckCodeActivity.this.tvLogin.setBackgroundResource(R.drawable.selector_solid_blue_with_radius);
                CheckCodeActivity.this.tvLogin.setClickable(true);
            }

            @Override // com.petkit.android.widget.PhoneCodeView.OnInputListener
            public void onInput() {
                CheckCodeActivity.this.tvLogin.setBackgroundResource(R.drawable.selector_solid_gray_with_radius);
                CheckCodeActivity.this.tvLogin.setClickable(false);
            }
        });
        startTimerToResetVcode();
        if (this.isLogoff) {
            this.tvLogin.setText(getString(R.string.Logoff));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Hint_input_verify_code);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.tv_login) {
            if (this.isLogoff) {
                logoff();
                return;
            } else {
                checkCode();
                return;
            }
        }
        if (id == R.id.tv_resend) {
            if (this.isLogoff) {
                sendLogoffCode();
            } else {
                forgetPassword(this.email);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimerToResetVcode() {
        this.mLeftSec = 60;
        this.disposable = Observable.interval(0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.setting.CheckCodeActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$startTimerToResetVcode$0((Long) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startTimerToResetVcode$0(Long l) throws Exception {
        int i = this.mLeftSec;
        this.mLeftSec = i - 1;
        setVcodeTimer(i);
    }

    public void setVcodeTimer(int i) {
        if (i > 0) {
            this.tvResend.setText(getString(R.string.Resend) + ChineseToPinyinResource.Field.LEFT_BRACKET + i + getString(R.string.Unit_second_short) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
            this.tvResend.setClickable(false);
            this.tvResend.setTextColor(CommonUtils.getColorById(R.color.gray));
            return;
        }
        this.tvResend.setText(R.string.Resend);
        this.tvResend.setClickable(true);
        this.tvResend.setTextColor(CommonUtils.getColorById(R.color.blue));
    }

    private void checkCode() {
        HashMap map = new HashMap();
        map.put("username", this.email);
        map.put("password", Md5.encode(this.phoneCodeView.getPhoneCode()));
        map.put("client", CommonUtils.getClientInfor(Constants.PETKIT_APP_ID));
        map.put("encrypt", "1");
        map.put("oldVersion", CommonUtils.getSysMap(this, Consts.SHARED_OLD_VERSION));
        LoadDialog.show(this);
        new LoginUtils(this, new LoginUtils.LoginResultListener() { // from class: com.petkit.android.activities.setting.CheckCodeActivity.2
            @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
            public void onFailed(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
            public void onSuccess() {
                LoadDialog.dismissDialog();
                CheckCodeActivity.this.entryModifyPassword();
            }

            @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
            public void onFailed(String str) {
                LoadDialog.dismissDialog();
                CheckCodeActivity.this.showShortToast(str, R.drawable.toast_failed);
            }
        }, map).start();
    }

    private void sendLogoffCode() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("ts", "" + jCurrentTimeMillis);
        map.put("codeKey", Md5.encode("0" + (jCurrentTimeMillis - 1)).toLowerCase(Locale.ENGLISH));
        post(ApiTools.SAMPLE_API_USER_SEND_DELETE_ACCOUNT_CODE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.CheckCodeActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    CheckCodeActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (this.responseResult.contains("success")) {
                    CheckCodeActivity.this.showLongToast(R.string.Sent);
                    Disposable disposable = CheckCodeActivity.this.disposable;
                    if (disposable != null) {
                        disposable.dispose();
                        CheckCodeActivity.this.disposable = null;
                    }
                    CheckCodeActivity.this.startTimerToResetVcode();
                }
            }
        }, false);
    }

    private void forgetPassword(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("ts", "" + jCurrentTimeMillis);
        map.put("codeKey", Md5.encode(str + "0" + (jCurrentTimeMillis - 1)).toLowerCase(Locale.getDefault()));
        post(ApiTools.PASSPORT_RESETPASSD, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.CheckCodeActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    CheckCodeActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (this.responseResult.contains("success")) {
                    CheckCodeActivity.this.showLongToast(R.string.Sent);
                    Disposable disposable = CheckCodeActivity.this.disposable;
                    if (disposable != null) {
                        disposable.dispose();
                        CheckCodeActivity.this.disposable = null;
                    }
                    CheckCodeActivity.this.startTimerToResetVcode();
                }
            }
        }, false);
    }

    private void logoff() {
        HashMap map = new HashMap();
        map.put(DualStackEventExtension.KEY_CODE, this.phoneCodeView.getPhoneCode());
        post(ApiTools.SAMPLE_API_USER_LOGOFF, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.CheckCodeActivity.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null && resultStringRsp.getResult() != null && resultStringRsp.getResult().equalsIgnoreCase("success")) {
                    CommonUtils.addSysMap(CheckCodeActivity.this, Consts.SHARED_SESSION_ID, "");
                    AsyncHttpUtil.addHttpHeader(Consts.HTTP_HEADER_SESSION, "");
                    DeviceCenterUtils.resetInfoWhileLogout();
                    LocalBroadcastManager.getInstance(CheckCodeActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_LOGOUT));
                    NewIotManager.getInstance().destroy();
                    CheckCodeActivity.this.startActivity(NormalLoginActivity.class, true);
                    CheckCodeActivity.this.overridePendingTransition(0, 0);
                } else {
                    CheckCodeActivity.this.showShortToast(resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
                }
                LoadDialog.dismissDialog();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                LoadDialog.dismissDialog();
            }
        }, false);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.disposable;
        if (disposable != null) {
            disposable.dispose();
            this.disposable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void entryModifyPassword() {
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_MSG_CHANGE_USER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        AsyncHttpRespHandler.dismissLogoutDialog();
        Intent intent2 = new Intent(this, (Class<?>) ModifyPasswordActivity.class);
        intent2.putExtra(Constants.EXTRA_TYPE, ModifyPasswordActivity.MODIFYPASSWORD_FORGOT_TYPE);
        intent2.putExtra(Constants.EXTRA_DYNAMIC_PASSWORD, this.phoneCodeView.getPhoneCode());
        startActivity(intent2);
        overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
        sendCloseActivityBroadcast();
    }
}

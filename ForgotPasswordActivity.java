package com.petkit.android.activities.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.model.Region;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LoginUtils;
import com.petkit.android.utils.Md5;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes5.dex */
public class ForgotPasswordActivity extends BaseActivity {
    private Disposable disposable;
    private TextView getVcode;
    private boolean hasSentPassword = false;
    private String lastMobile = "";
    private Region mRegion;
    private EditText userNameEditText;
    private EditText vcodeEditText;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_forgot_password);
        this.mRegion = (Region) getIntent().getSerializableExtra(Constants.EXTRA_REGION);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
        this.userNameEditText = (EditText) findViewById(R.id.input_mobile);
        String stringExtra = getIntent().getStringExtra("username");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.userNameEditText.setText(stringExtra);
        }
        this.vcodeEditText = (EditText) findViewById(R.id.input_vcode);
        if (!TextUtils.isEmpty(stringExtra)) {
            this.vcodeEditText.requestFocus();
        }
        TextView textView = (TextView) findViewById(R.id.get_vcode);
        this.getVcode = textView;
        textView.setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.get_vcode) {
            String string = this.userNameEditText.getEditableText().toString();
            if (isEmpty(string)) {
                PetkitToast.showTopToast(this, getResources().getString(R.string.Hint_input_account), R.drawable.top_toast_warn_icon, 1);
                return;
            } else {
                forgetPassword(string);
                return;
            }
        }
        if (id == R.id.iv_back) {
            finish();
            return;
        }
        if (id == R.id.login) {
            String string2 = this.userNameEditText.getEditableText().toString();
            String string3 = this.vcodeEditText.getEditableText().toString();
            if (isEmpty(string2)) {
                PetkitToast.showTopToast(this, getResources().getString(R.string.Hint_input_account), R.drawable.top_toast_warn_icon, 1);
                return;
            }
            if (isEmpty(string3)) {
                PetkitToast.showTopToast(this, getResources().getString(R.string.Hint_input_verify_code), R.drawable.top_toast_warn_icon, 1);
                return;
            }
            HashMap map = new HashMap();
            map.put("username", string2);
            map.put("password", Md5.encode(string3));
            map.put("client", CommonUtils.getClientInfor(Constants.PETKIT_APP_ID));
            map.put("encrypt", "1");
            map.put("oldVersion", CommonUtils.getSysMap(this, Consts.SHARED_OLD_VERSION));
            LoadDialog.show(this);
            new LoginUtils(this, new LoginUtils.LoginResultListener() { // from class: com.petkit.android.activities.setting.ForgotPasswordActivity.1
                @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                public void onFailed(ErrorInfor errorInfor) {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                public void onSuccess() {
                    LoadDialog.dismissDialog();
                    ForgotPasswordActivity.this.entryMainHome();
                }

                @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                public void onFailed(String str) {
                    LoadDialog.dismissDialog();
                    PetkitToast.showTopToast(ForgotPasswordActivity.this, str, R.drawable.top_toast_error_icon, 1);
                }
            }, map, this.mRegion).start();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.setting.ForgotPasswordActivity$1 */
    public class AnonymousClass1 implements LoginUtils.LoginResultListener {
        @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
        public void onFailed(ErrorInfor errorInfor) {
        }

        public AnonymousClass1() {
        }

        @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
        public void onSuccess() {
            LoadDialog.dismissDialog();
            ForgotPasswordActivity.this.entryMainHome();
        }

        @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
        public void onFailed(String str) {
            LoadDialog.dismissDialog();
            PetkitToast.showTopToast(ForgotPasswordActivity.this, str, R.drawable.top_toast_error_icon, 1);
        }
    }

    private void forgetPassword(String str) {
        if (this.hasSentPassword) {
            return;
        }
        this.lastMobile = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("ts", "" + jCurrentTimeMillis);
        map.put("codeKey", Md5.encode(str + "0" + (jCurrentTimeMillis - 1)).toLowerCase(Locale.ENGLISH));
        AsyncHttpUtil.post(ApiTools.PASSPORT_RESETPASSD, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.ForgotPasswordActivity.2
            public AnonymousClass2(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showTopToast(ForgotPasswordActivity.this, baseRsp.getError().getMsg(), R.drawable.top_toast_error_icon, 1);
                } else if (this.responseResult.contains("success")) {
                    ForgotPasswordActivity.this.hasSentPassword = true;
                    ForgotPasswordActivity.this.pollingCountDown();
                    ForgotPasswordActivity forgotPasswordActivity = ForgotPasswordActivity.this;
                    PetkitToast.showTopToast(forgotPasswordActivity, forgotPasswordActivity.getResources().getString(R.string.Sent), R.drawable.top_toast_success_icon, 1);
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.setting.ForgotPasswordActivity$2 */
    public class AnonymousClass2 extends AsyncHttpRespHandler {
        public AnonymousClass2(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PetkitToast.showTopToast(ForgotPasswordActivity.this, baseRsp.getError().getMsg(), R.drawable.top_toast_error_icon, 1);
            } else if (this.responseResult.contains("success")) {
                ForgotPasswordActivity.this.hasSentPassword = true;
                ForgotPasswordActivity.this.pollingCountDown();
                ForgotPasswordActivity forgotPasswordActivity = ForgotPasswordActivity.this;
                PetkitToast.showTopToast(forgotPasswordActivity, forgotPasswordActivity.getResources().getString(R.string.Sent), R.drawable.top_toast_success_icon, 1);
            }
        }
    }

    public void entryMainHome() {
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_MSG_CHANGE_USER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        AsyncHttpRespHandler.dismissLogoutDialog();
        Intent intent2 = new Intent(this, (Class<?>) ModifyPasswordNewActivity.class);
        intent2.putExtra(Constants.EXTRA_TYPE, ModifyPasswordActivity.MODIFYPASSWORD_FORGOT_TYPE);
        intent2.putExtra(Constants.EXTRA_DYNAMIC_PASSWORD, this.vcodeEditText.getEditableText().toString());
        startActivity(intent2);
        overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
        sendCloseActivityBroadcast();
    }

    public void pollingCountDown() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        Observable.interval(0L, 1L, TimeUnit.SECONDS).take(61).map(new Function<Long, Long>() { // from class: com.petkit.android.activities.setting.ForgotPasswordActivity.4
            public final /* synthetic */ int val$count_time;

            public AnonymousClass4(int i) {
                i = i;
            }

            @Override // io.reactivex.functions.Function
            public Long apply(@NotNull Long l) throws Exception {
                return Long.valueOf(((long) i) - l.longValue());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.setting.ForgotPasswordActivity.3
            public final /* synthetic */ int val$count_time;

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            public AnonymousClass3(int i) {
                i = i;
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable2) {
                ForgotPasswordActivity.this.disposable = disposable2;
                ForgotPasswordActivity.this.getVcode.setBackground(ForgotPasswordActivity.this.getResources().getDrawable(R.drawable.get_vcode_gray_bg));
                ForgotPasswordActivity.this.getVcode.setText("" + i + ForgotPasswordActivity.this.getResources().getString(R.string.Unit_second_short));
                ForgotPasswordActivity.this.getVcode.setTextColor(ForgotPasswordActivity.this.getResources().getColor(R.color.home_p3_line_gray));
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                ForgotPasswordActivity.this.getVcode.setText("" + l + ForgotPasswordActivity.this.getResources().getString(R.string.Unit_second_short));
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ForgotPasswordActivity.this.hasSentPassword = false;
                ForgotPasswordActivity.this.getVcode.setBackground(ForgotPasswordActivity.this.getResources().getDrawable(R.drawable.get_vcode_bg));
                ForgotPasswordActivity.this.getVcode.setText(ForgotPasswordActivity.this.getResources().getString(R.string.Fetch_dynamic_password));
                ForgotPasswordActivity.this.getVcode.setTextColor(ForgotPasswordActivity.this.getResources().getColor(R.color.login_new_blue));
                if (ForgotPasswordActivity.this.disposable != null) {
                    ForgotPasswordActivity.this.disposable.dispose();
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.setting.ForgotPasswordActivity$4 */
    public class AnonymousClass4 implements Function<Long, Long> {
        public final /* synthetic */ int val$count_time;

        public AnonymousClass4(int i) {
            i = i;
        }

        @Override // io.reactivex.functions.Function
        public Long apply(@NotNull Long l) throws Exception {
            return Long.valueOf(((long) i) - l.longValue());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.setting.ForgotPasswordActivity$3 */
    public class AnonymousClass3 implements Observer<Long> {
        public final /* synthetic */ int val$count_time;

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public AnonymousClass3(int i) {
            i = i;
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable2) {
            ForgotPasswordActivity.this.disposable = disposable2;
            ForgotPasswordActivity.this.getVcode.setBackground(ForgotPasswordActivity.this.getResources().getDrawable(R.drawable.get_vcode_gray_bg));
            ForgotPasswordActivity.this.getVcode.setText("" + i + ForgotPasswordActivity.this.getResources().getString(R.string.Unit_second_short));
            ForgotPasswordActivity.this.getVcode.setTextColor(ForgotPasswordActivity.this.getResources().getColor(R.color.home_p3_line_gray));
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            ForgotPasswordActivity.this.getVcode.setText("" + l + ForgotPasswordActivity.this.getResources().getString(R.string.Unit_second_short));
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            ForgotPasswordActivity.this.hasSentPassword = false;
            ForgotPasswordActivity.this.getVcode.setBackground(ForgotPasswordActivity.this.getResources().getDrawable(R.drawable.get_vcode_bg));
            ForgotPasswordActivity.this.getVcode.setText(ForgotPasswordActivity.this.getResources().getString(R.string.Fetch_dynamic_password));
            ForgotPasswordActivity.this.getVcode.setTextColor(ForgotPasswordActivity.this.getResources().getColor(R.color.login_new_blue));
            if (ForgotPasswordActivity.this.disposable != null) {
                ForgotPasswordActivity.this.disposable.dispose();
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }
}

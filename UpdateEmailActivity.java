package com.petkit.android.activities.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.mall.utils.ShopifyUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.model.Account;
import com.petkit.android.utils.Md5;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import com.qiyukf.nimlib.report.extension.DualStackEventExtension;
import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes5.dex */
public class UpdateEmailActivity extends BaseActivity implements View.OnClickListener {
    private Button btnConfirm;
    private EditText etNewCode;
    private EditText etNewEmail;
    private boolean isSend = false;
    private String tempEmail = "";
    private TextView tvEmail;
    private TextView tvGetCode;

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_update_email);
        this.tvEmail = (TextView) findViewById(R.id.tv_email);
        this.etNewEmail = (EditText) findViewById(R.id.et_new_email);
        this.etNewCode = (EditText) findViewById(R.id.et_new_code);
        this.tvGetCode = (TextView) findViewById(R.id.tv_get_code);
        this.btnConfirm = (Button) findViewById(R.id.btn_confirm);
        setTitle(getString(R.string.Change_email));
        this.tvEmail.setText(getString(R.string.Current_email) + ":" + UserInforUtils.getAccount().getEmail());
        this.tvGetCode.setOnClickListener(this);
        this.btnConfirm.setOnClickListener(this);
        this.tvGetCode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.setting.UpdateEmailActivity.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int height = UpdateEmailActivity.this.tvGetCode.getHeight();
                ViewGroup.LayoutParams layoutParams = UpdateEmailActivity.this.etNewCode.getLayoutParams();
                layoutParams.height = height;
                UpdateEmailActivity.this.etNewCode.setLayoutParams(layoutParams);
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_get_code) {
            if (this.etNewEmail.getText().toString().equals(this.tempEmail)) {
                this.isSend = true;
            } else {
                this.isSend = false;
                this.tempEmail = this.etNewEmail.getText().toString();
            }
            if (!TextUtils.isEmpty(this.etNewEmail.getText().toString())) {
                if (this.isSend) {
                    PetkitToast.showToast(getString(R.string.Error_dynamic_password_has_already_been_sent));
                    return;
                } else {
                    sendForChangeCode();
                    return;
                }
            }
            PetkitToast.showToast(getString(R.string.Hint_input_email));
            return;
        }
        if (view.getId() == R.id.btn_confirm) {
            if (TextUtils.isEmpty(this.etNewEmail.getText().toString())) {
                PetkitToast.showToast(getString(R.string.Hint_input_email));
            } else if (TextUtils.isEmpty(this.etNewCode.getText().toString())) {
                PetkitToast.showToast(getString(R.string.Hint_input_verify_code));
            } else {
                updateEmail();
            }
        }
    }

    private void startCountDown() {
        Observable.timer(60L, TimeUnit.SECONDS).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.setting.UpdateEmailActivity.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                UpdateEmailActivity.this.isSend = false;
            }
        });
    }

    private void sendForChangeCode() {
        startCountDown();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("username", this.etNewEmail.getText().toString());
        map.put("ts", "" + jCurrentTimeMillis);
        map.put("codeKey", Md5.encode(this.etNewEmail.getText().toString() + "0" + (jCurrentTimeMillis - 1)).toLowerCase(Locale.ENGLISH));
        showLoadDialog();
        post(ApiTools.SAMPLE_API_USER_SENDCODEFORRESETUSERNAME, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.UpdateEmailActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                UpdateEmailActivity.this.dismissLoadDialog();
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    UpdateEmailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    UpdateEmailActivity.this.isSend = false;
                    UpdateEmailActivity.this.tempEmail = "";
                } else if (this.responseResult.contains("success")) {
                    PetkitToast.showToast(UpdateEmailActivity.this.getString(R.string.Sent));
                    UpdateEmailActivity.this.isSend = true;
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                UpdateEmailActivity.this.dismissLoadDialog();
            }
        }, false);
    }

    private void updateEmail() {
        HashMap map = new HashMap();
        map.put("username", this.etNewEmail.getText().toString());
        map.put(DualStackEventExtension.KEY_CODE, this.etNewCode.getText().toString());
        showLoadDialog();
        post(ApiTools.SAMPLE_API_USER_UPDATEUSERNAME, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.UpdateEmailActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                UpdateEmailActivity.this.dismissLoadDialog();
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    UpdateEmailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    UpdateEmailActivity.this.isSend = false;
                } else if (this.responseResult.contains("success")) {
                    Account account = UserInforUtils.getAccount();
                    account.setEmail(UpdateEmailActivity.this.etNewEmail.getText().toString());
                    UserInforUtils.storeAccount(UpdateEmailActivity.this, account);
                    ShopifyUtils.clearAll();
                    UpdateEmailActivity.this.setResult(-1);
                    UpdateEmailActivity.this.finish();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                UpdateEmailActivity.this.dismissLoadDialog();
            }
        }, false);
    }
}

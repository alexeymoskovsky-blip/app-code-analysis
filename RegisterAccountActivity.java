package com.petkit.android.activities.registe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.pet.PetRegisterFirstPartActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.SignupRsp;
import com.petkit.android.model.Region;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.Md5;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes5.dex */
public class RegisterAccountActivity extends BaseActivity {
    private static final int REQUEST_REGISTER_FIRST_PET = 4661;
    private static final int REQUEST_REGISTER_USER_INFO = 4660;
    private Region mRegion;
    private EditText mobileEditText;
    private EditText pwEditText;
    private TextView registerTypePrompt;
    private EditText vcodeEditText;
    private int registerType = 0;
    private boolean hasSentPassword = false;
    private String lastMobile = "";

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.mRegion = (Region) getIntent().getSerializableExtra(Constants.EXTRA_REGION);
        } else {
            this.mRegion = (Region) bundle.getSerializable(Constants.EXTRA_REGION);
        }
        setContentView(R.layout.activity_register_step_1);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_REGION, this.mRegion);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Title_required_infomation);
        setTitleRightButton(R.string.Register, this);
        this.mobileEditText = (EditText) findViewById(R.id.input_mobile);
        this.pwEditText = (EditText) findViewById(R.id.input_password);
        this.vcodeEditText = (EditText) findViewById(R.id.input_vcode);
        findViewById(R.id.get_vcode).setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.register_type_prompt);
        this.registerTypePrompt = textView;
        textView.setOnClickListener(this);
        this.registerTypePrompt.setVisibility(8);
        if (Constants.REGISTER_TYPE_MOBILE.equals(this.mRegion.getAccountType())) {
            this.registerType = 0;
        } else if ("email".equals(this.mRegion.getAccountType())) {
            this.registerType = 1;
        } else {
            this.registerTypePrompt.setVisibility(0);
        }
        setRegisterPrompt();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i != 4660) {
                if (i != 4661) {
                    return;
                }
                entryMainHome();
            } else {
                Intent intent2 = new Intent(this, (Class<?>) PetRegisterFirstPartActivity.class);
                intent2.putExtra(Constants.VIEW_FROM, 1);
                startActivityForResult(intent2, 4661);
                int i3 = R.anim.slide_none;
                overridePendingTransition(i3, i3);
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.title_right_btn) {
            String string = this.mobileEditText.getEditableText().toString();
            String string2 = this.pwEditText.getEditableText().toString();
            String string3 = this.vcodeEditText.getEditableText().toString();
            if (this.registerType == 0) {
                if (CommonUtils.isEmpty(string) || !CommonUtils.checkPhoneNumber(string)) {
                    showShortToast(R.string.Error_wrong_email, R.drawable.toast_failed);
                    return;
                }
            } else if (CommonUtils.isEmpty(string) || !CommonUtils.checkEmail(string)) {
                return;
            }
            if (isEmpty(string2) || string2.length() < 6) {
                showShortToast(R.string.Error_wrong_password, R.drawable.toast_failed);
                return;
            } else if (this.registerType == 0 && isEmpty(string3)) {
                showShortToast(R.string.Hint_input_verify_code, R.drawable.toast_failed);
                return;
            } else {
                doSignup(string, string2, string3);
                return;
            }
        }
        if (view.getId() == R.id.register_type_prompt) {
            this.registerType = this.registerType == 0 ? 1 : 0;
            setRegisterPrompt();
            return;
        }
        if (view.getId() == R.id.get_vcode && this.registerType == 0) {
            String string4 = this.mobileEditText.getEditableText().toString();
            if (CommonUtils.isEmpty(string4) || !CommonUtils.checkPhoneNumber(string4)) {
                showShortToast(R.string.Error_wrong_phonenumber, R.drawable.toast_failed);
                return;
            }
            if (!this.lastMobile.equals(string4)) {
                this.hasSentPassword = false;
            }
            sendVcode(string4);
        }
    }

    private void setRegisterPrompt() {
        String string;
        this.mobileEditText.setText("");
        this.pwEditText.setText("");
        if (this.registerType == 0) {
            string = getString(R.string.Register_email);
            this.mobileEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mobile, 0, 0, 0);
            this.mobileEditText.setInputType(3);
            findViewById(R.id.input_vcode_parent).setVisibility(0);
        } else {
            string = getString(R.string.Register_phone);
            this.mobileEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_email, 0, 0, 0);
            this.mobileEditText.setInputType(32);
            this.mobileEditText.setHint(R.string.Hint_input_email);
            findViewById(R.id.input_vcode_parent).setVisibility(8);
        }
        SpannableString spannableString = new SpannableString(getString(R.string.I_wish_to_use_format, string));
        spannableString.setSpan(new ForegroundColorSpan(CommonUtils.getColorById(R.color.yellow)), spannableString.length() - string.length(), spannableString.length(), 33);
        this.registerTypePrompt.setText(spannableString);
    }

    private void sendVcode(String str) {
        if (this.hasSentPassword) {
            showLongToast(R.string.Error_dynamic_password_has_already_been_sent);
            return;
        }
        this.lastMobile = str;
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put(Constants.REGISTER_TYPE_MOBILE, str);
        map.put("ts", "" + jCurrentTimeMillis);
        map.put("codeKey", Md5.encode(str + "0" + (jCurrentTimeMillis - 1)).toLowerCase(Locale.getDefault()));
        post(ApiTools.PASSPORT_SENDCODEFORSIGNUP, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.registe.RegisterAccountActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    RegisterAccountActivity.this.hasSentPassword = true;
                    RegisterAccountActivity.this.showLongToast(R.string.Sent);
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.registe.RegisterAccountActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RegisterAccountActivity.this.hasSentPassword = false;
                        }
                    }, 60000L);
                    return;
                }
                RegisterAccountActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    private void doSignup(final String str, final String str2, final String str3) {
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("password", str2);
        if (!isEmpty(str3)) {
            map.put("smscode", str3);
        }
        map.put("client", CommonUtils.getClientInfor(Constants.PETKIT_APP_ID));
        map.put(TtmlNode.TAG_REGION, this.mRegion.getId());
        post(ApiTools.PASSPORT_SIGNUP, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.registe.RegisterAccountActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                SignupRsp signupRsp = (SignupRsp) this.gson.fromJson(this.responseResult, SignupRsp.class);
                if (signupRsp.getError() == null) {
                    RegisterAccountActivity.this.doRegister(str, str3, str2);
                } else {
                    RegisterAccountActivity.this.showLongToast(signupRsp.getError().getMsg(), R.drawable.toast_failed);
                }
            }
        }, false);
    }

    private void entryMainHome() {
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_MSG_CHANGE_USER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        startActivity(HomeActivity.class, false);
        overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
        sendCloseActivityBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doRegister(final String str, String str2, String str3) {
        String gateway = this.mRegion.getGateway();
        CommonUtils.addSysMap(Constants.SHARED_BASE_GATEWAY, gateway);
        ApiTools.setApiBaseUrl(gateway);
        boolean z = true;
        if (!"CN".equals(this.mRegion.getId())) {
            CommonUtils.addSysIntMap(this, Constants.SHARED_GUIDE_COMPLETE, 1);
        }
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("password", Md5.encode(str3));
        map.put("encrypt", "1");
        map.put("client", CommonUtils.getClientInfor(Constants.PETKIT_APP_ID));
        map.put("oldVersion", CommonUtils.getSysMap(this, Consts.SHARED_OLD_VERSION));
        post(ApiTools.SAMPLE_API_USER_LOGIN, map, new AsyncHttpRespHandler(this, z) { // from class: com.petkit.android.activities.registe.RegisterAccountActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                final LoginRsp loginRsp = (LoginRsp) this.gson.fromJson(this.responseResult, LoginRsp.class);
                if (loginRsp.getResult() != null) {
                    AsyncHttpUtil.addHttpHeader(Consts.HTTP_HEADER_SESSION, loginRsp.getResult().getSession().getId());
                    CommonUtils.addSysMap(RegisterAccountActivity.this, Consts.SHARED_SESSION_ID, loginRsp.getResult().getSession().getId());
                    UserInforUtils.updateLoginResult(loginRsp.getResult());
                    CommonUtils.addSysMap(RegisterAccountActivity.this, Consts.SHARED_USER_ACCOUNT_INFOR, this.gson.toJson(loginRsp.getResult().getUser().getAccount()));
                    CommonUtils.addSysMap(RegisterAccountActivity.this, Consts.SHARED_USER_NAME, str);
                    RegisterAccountActivity registerAccountActivity = RegisterAccountActivity.this;
                    CommonUtils.addSysMap(registerAccountActivity, Consts.SHARED_OLD_VERSION, CommonUtils.getAppVersionName(registerAccountActivity));
                    CommonUtils.addSysMap(RegisterAccountActivity.this, Consts.SHARED_USER_ID, loginRsp.getResult().getUser().getId());
                    CommonUtils.addSysMap(Constants.SHARED_IM_SERVER_URL, this.gson.toJson(loginRsp.getResult().getImserver()));
                    CommonUtils.addSysIntMap(RegisterAccountActivity.this, Constants.RECOMMEND_FOLLOW_FLAG, 0);
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.registe.RegisterAccountActivity.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Intent intent = new Intent(RegisterAccountActivity.this, (Class<?>) RegisterUserInforActivity.class);
                            intent.putExtra(Constants.EXTRA_USER_DETAIL, loginRsp.getResult().getUser());
                            RegisterAccountActivity.this.startActivityForResult(intent, 4660);
                        }
                    }, 100L);
                    return;
                }
                RegisterAccountActivity.this.showShortToast(loginRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }
}

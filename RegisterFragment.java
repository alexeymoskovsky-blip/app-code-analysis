package com.petkit.android.activities.registe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.model.Region;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.Md5;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes5.dex */
public class RegisterFragment extends BaseFragment {
    public static final int REQUEST_REGION = 4662;
    public static final int REQUEST_REGISTER_FIRST_PET = 4661;
    public static final int REQUEST_REGISTER_USER_INFO = 4660;
    private Disposable disposable;
    private EditText emailEditText;
    private int leftTime;
    private Region mRegion;
    private EditText pwEditText;
    private EditText regionEditText;
    private String session_id;
    private TextView tvGetCode;
    private ImageButton userPasswordVisible;
    private boolean isShow = false;
    private boolean hasSentPassword = false;
    private boolean isSend = false;

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_registe);
        setNoTitle();
        this.emailEditText = (EditText) this.contentView.findViewById(R.id.input_email);
        EditText editText = (EditText) this.contentView.findViewById(R.id.input_password);
        this.pwEditText = editText;
        editText.setHint(R.string.Hint_input_verify_code);
        this.tvGetCode = (TextView) this.contentView.findViewById(R.id.tv_get_code);
        ImageButton imageButton = (ImageButton) this.contentView.findViewById(R.id.ib_login_password_visible);
        this.userPasswordVisible = imageButton;
        imageButton.setOnClickListener(this);
        this.tvGetCode.setOnClickListener(this);
        this.contentView.findViewById(R.id.registe).setOnClickListener(this);
        TextView textView = (TextView) this.contentView.findViewById(R.id.user_agreement);
        textView.setText(new SpannableString(getString(R.string.Settings_user_aggreement)));
        textView.setTextColor(getResources().getColor(R.color.d4sh_main_blue));
        textView.setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.registe) {
            String string = this.emailEditText.getEditableText().toString();
            String string2 = this.pwEditText.getEditableText().toString();
            if (this.mRegion == null) {
                PetkitToast.showShortToast(getActivity(), R.string.Error_select_region, R.drawable.toast_failed);
                return;
            }
            if (CommonUtils.isEmpty(string) || !CommonUtils.checkEmail(string)) {
                return;
            }
            if (isEmpty(string2) || string2.length() < 6) {
                PetkitToast.showShortToast(getActivity(), R.string.Error_wrong_password, R.drawable.toast_failed);
                return;
            } else {
                doSignup(string, string2, null);
                return;
            }
        }
        if (id == R.id.tv_get_code) {
            if (!TextUtils.isEmpty(this.emailEditText.getText().toString())) {
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
        if (id == R.id.user_agreement) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EXTRA_LOAD_PATH, ApiTools.getWebUrlByKey("user_agreement"));
            bundle.putString(Constants.EXTRA_LOAD_TITLE, getString(R.string.Settings_user_aggreement));
            startActivityWithData(WebviewActivity.class, bundle, false);
            return;
        }
        if (id == R.id.ib_login_password_visible) {
            if (!this.isShow) {
                this.isShow = true;
                this.userPasswordVisible.setImageResource(R.drawable.passward_visible);
                this.pwEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                EditText editText = this.pwEditText;
                editText.setSelection(editText.getText().length());
                return;
            }
            this.isShow = false;
            this.userPasswordVisible.setImageResource(R.drawable.passward_invisible);
            this.pwEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            EditText editText2 = this.pwEditText;
            editText2.setSelection(editText2.getText().length());
        }
    }

    private void sendForChangeCode() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("username", this.emailEditText.getText().toString());
        map.put("ts", "" + jCurrentTimeMillis);
        post(ApiTools.PASSPORT_SENDCODEFORSIGNUP, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.registe.fragment.RegisterFragment.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showToast(baseRsp.getError().getMsg());
                    RegisterFragment.this.isSend = false;
                } else if (this.responseResult.contains("success")) {
                    PetkitToast.showToast(RegisterFragment.this.getString(R.string.Sent));
                    RegisterFragment.this.openRemainTimer(60);
                    RegisterFragment.this.startCountDown();
                    RegisterFragment.this.isSend = true;
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }
        }, false);
    }

    public void openRemainTimer(int i) {
        if (i < 0) {
            i = 0;
        }
        this.leftTime = i;
        if (this.disposable == null) {
            this.disposable = Observable.interval(1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.registe.fragment.RegisterFragment$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$openRemainTimer$0((Long) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openRemainTimer$0(Long l) throws Exception {
        int i = this.leftTime - 1;
        this.leftTime = i;
        if (i < 0) {
            closeRemainTimer();
            this.leftTime = 0;
            return;
        }
        TextView textView = (TextView) this.contentView.findViewById(R.id.tv_get_code);
        this.tvGetCode = textView;
        textView.setText(this.leftTime + "s");
    }

    public void closeRemainTimer() {
        this.tvGetCode.setText(R.string.Fetch_verify_code);
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCountDown() {
        Observable.timer(60L, TimeUnit.SECONDS).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.registe.fragment.RegisterFragment.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                RegisterFragment.this.isSend = false;
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 4662) {
            this.emailEditText.requestFocus();
        }
    }

    public void setRegion(Region region) {
        this.mRegion = region;
    }

    private void doSignup(final String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("validCode", str2);
        if (!isEmpty(str3)) {
            map.put("smscode", str3);
        }
        map.put("client", CommonUtils.getClientInfor(Constants.PETKIT_APP_ID));
        map.put(TtmlNode.TAG_REGION, this.mRegion.getId());
        ((BaseActivity) getActivity()).showLoadDialog();
        AsyncHttpUtil.post(ApiTools.PASSPORT_SIGNUP, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.registe.fragment.RegisterFragment.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                super.onSuccess(i, headerArr, bArr);
                ((BaseActivity) RegisterFragment.this.getActivity()).dismissLoadDialog();
                LoginRsp loginRsp = (LoginRsp) this.gson.fromJson(this.responseResult, LoginRsp.class);
                if (loginRsp.getResult() != null) {
                    Gson gson = new Gson();
                    DataHelper.setStringSF(RegisterFragment.this.getContext(), Consts.ACCOUNT_REGION_STRING, gson.toJson(RegisterFragment.this.mRegion));
                    AsyncHttpUtil.addHttpHeader(Consts.HTTP_HEADER_SESSION, loginRsp.getResult().getSession().getId());
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_SESSION_ID, loginRsp.getResult().getSession().getId());
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_LOGIN_RESULT, gson.toJson(loginRsp.getResult()));
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_USER_ACCOUNT_INFOR, gson.toJson(loginRsp.getResult().getUser().getAccount()));
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_USER_NAME, str);
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_OLD_VERSION, CommonUtils.getAppVersionName(RegisterFragment.this.getContext()));
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_USER_ID, loginRsp.getResult().getUser().getId());
                    CommonUtils.addSysMap(Constants.SHARED_IM_SERVER_URL, gson.toJson(loginRsp.getResult().getImserver()));
                    UserInforUtils.updateLoginResult(loginRsp.getResult());
                    CommonUtils.addSysIntMap(RegisterFragment.this.getContext(), Constants.RECOMMEND_FOLLOW_FLAG, 0);
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.registe.fragment.RegisterFragment.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RegisterFragment.this.startActivity(HomeActivity.class, false);
                        }
                    }, 100L);
                    return;
                }
                ((BaseActivity) RegisterFragment.this.getActivity()).dismissLoadDialog();
                PetkitToast.showShortToast(RegisterFragment.this.getActivity(), loginRsp.getError().getMsg(), R.drawable.toast_failed);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                ((BaseActivity) RegisterFragment.this.getActivity()).dismissLoadDialog();
            }
        }, false);
    }

    private void doRegister(final String str, String str2, String str3) {
        String gateway = this.mRegion.getGateway();
        if (!gateway.contains("https")) {
            gateway = gateway.replace("http", "https");
        }
        CommonUtils.addSysMap(Constants.SHARED_BASE_GATEWAY, gateway);
        ApiTools.setApiBaseUrl(gateway);
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("password", Md5.encode(str3));
        map.put("encrypt", "1");
        map.put("client", CommonUtils.getClientInfor(Constants.PETKIT_APP_ID));
        map.put("oldVersion", CommonUtils.getSysMap(getContext(), Consts.SHARED_OLD_VERSION));
        map.put(TtmlNode.TAG_REGION, this.mRegion.getId());
        post(ApiTools.SAMPLE_API_USER_LOGIN, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.registe.fragment.RegisterFragment.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ((BaseActivity) RegisterFragment.this.getActivity()).dismissLoadDialog();
                LoginRsp loginRsp = (LoginRsp) this.gson.fromJson(this.responseResult, LoginRsp.class);
                if (loginRsp.getResult() != null) {
                    Gson gson = new Gson();
                    DataHelper.setStringSF(RegisterFragment.this.getContext(), Consts.ACCOUNT_REGION_STRING, gson.toJson(RegisterFragment.this.mRegion));
                    AsyncHttpUtil.addHttpHeader(Consts.HTTP_HEADER_SESSION, loginRsp.getResult().getSession().getId());
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_SESSION_ID, loginRsp.getResult().getSession().getId());
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_LOGIN_RESULT, gson.toJson(loginRsp.getResult()));
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_USER_ACCOUNT_INFOR, gson.toJson(loginRsp.getResult().getUser().getAccount()));
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_USER_NAME, str);
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_OLD_VERSION, CommonUtils.getAppVersionName(RegisterFragment.this.getContext()));
                    CommonUtils.addSysMap(RegisterFragment.this.getContext(), Consts.SHARED_USER_ID, loginRsp.getResult().getUser().getId());
                    CommonUtils.addSysMap(Constants.SHARED_IM_SERVER_URL, gson.toJson(loginRsp.getResult().getImserver()));
                    UserInforUtils.updateLoginResult(loginRsp.getResult());
                    CommonUtils.addSysIntMap(RegisterFragment.this.getContext(), Constants.RECOMMEND_FOLLOW_FLAG, 0);
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.registe.fragment.RegisterFragment.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RegisterFragment.this.startActivity(HomeActivity.class, false);
                        }
                    }, 100L);
                    return;
                }
                ((BaseActivity) RegisterFragment.this.getActivity()).dismissLoadDialog();
                PetkitToast.showShortToast(RegisterFragment.this.getActivity(), loginRsp.getError().getMsg(), R.drawable.toast_failed);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                ((BaseActivity) RegisterFragment.this.getActivity()).dismissLoadDialog();
            }
        }, false);
    }

    public void refreshFocus() {
        EditText editText = this.emailEditText;
        if (editText == null || this.pwEditText == null) {
            return;
        }
        if (editText.getText().toString().length() > 0) {
            this.pwEditText.requestFocus();
        } else {
            this.emailEditText.requestFocus();
        }
    }
}

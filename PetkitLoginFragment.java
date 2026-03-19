package com.petkit.android.activities.registe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.gamingservices.cloudgaming.internal.SDKConstants;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.registe.RegionMainActivity;
import com.petkit.android.activities.registe.mode.FaceBookResult;
import com.petkit.android.activities.registe.mode.TwitterResult;
import com.petkit.android.activities.setting.ForgotPasswordActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.model.Region;
import com.petkit.android.model.SerMap;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.JSONUtils;
import com.petkit.android.utils.LoginUtils;
import com.petkit.android.utils.Md5;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public class PetkitLoginFragment extends BaseFragment implements View.OnFocusChangeListener {
    public static final String BROADCAST_MSG_LOGIN = "com.petkit.android.login";
    public static final int REQUEST_REGION = 4662;
    private CallbackManager callbackManager;
    private Disposable disposable;
    private int leftTime;
    private Region mRegion;
    private EditText passwordEditText;
    private TextView tvGetCode;
    private TextView tvLoginWay;
    TwitterAuthClient twitterAuthClient;
    private ImageButton userNameClear;
    private EditText userNameEditText;
    private ImageButton userPasswordClear;
    private ImageButton userPasswordVisible;
    private boolean isShow = false;
    private boolean canGoBack = true;
    private boolean isSend = false;
    private int loginWay = 0;
    private TextWatcher textWatcher = new TextWatcher() { // from class: com.petkit.android.activities.registe.fragment.PetkitLoginFragment.1
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            PetkitLoginFragment petkitLoginFragment = PetkitLoginFragment.this;
            if (!petkitLoginFragment.isEmpty(petkitLoginFragment.userNameEditText.getText().toString())) {
                if (PetkitLoginFragment.this.userNameEditText.hasFocus()) {
                    PetkitLoginFragment.this.userNameClear.setVisibility(0);
                }
            } else {
                PetkitLoginFragment.this.userNameClear.setVisibility(4);
            }
            PetkitLoginFragment petkitLoginFragment2 = PetkitLoginFragment.this;
            if (!petkitLoginFragment2.isEmpty(petkitLoginFragment2.passwordEditText.getText().toString())) {
                if (PetkitLoginFragment.this.passwordEditText.hasFocus()) {
                    PetkitLoginFragment.this.userPasswordClear.setVisibility(0);
                    return;
                }
                return;
            }
            PetkitLoginFragment.this.userPasswordClear.setVisibility(4);
        }
    };

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
        initFaceBook();
    }

    public void setRegion(Region region) {
        this.mRegion = region;
    }

    private void initFaceBook() {
        this.callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() { // from class: com.petkit.android.activities.registe.fragment.PetkitLoginFragment.2
            @Override // com.facebook.FacebookCallback
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                HashMap map = new HashMap();
                HashMap<String, String> map2 = new HashMap<>();
                map.put(SDKConstants.PARAM_ACCESS_TOKEN, accessToken.getToken());
                map2.put("tpType", Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                map2.put("username", accessToken.getUserId());
                map2.put("tpInfo", JSONUtils.mapToJsonStr(map));
                map2.put("client", CommonUtils.getClientInfor(com.petkit.android.utils.Constants.PETKIT_APP_ID));
                map2.put("encrypt", "1");
                map2.put("oldVersion", CommonUtils.getSysMap(PetkitLoginFragment.this.getActivity(), Consts.SHARED_OLD_VERSION));
                PetkitLoginFragment.this.startLogin(map2);
            }

            @Override // com.facebook.FacebookCallback
            public void onCancel() {
                LoadDialog.dismissDialog();
            }

            @Override // com.facebook.FacebookCallback
            public void onError(FacebookException facebookException) {
                LoadDialog.dismissDialog();
                PetkitToast.showTopToast(PetkitLoginFragment.this.getActivity(), PetkitLoginFragment.this.getActivity().getResources().getString(R.string.Authorization_failed), R.drawable.top_toast_error_icon, 1);
                if (!(facebookException instanceof FacebookAuthorizationException) || AccessToken.getCurrentAccessToken() == null) {
                    return;
                }
                LoginManager.getInstance().logOut();
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_login);
        setNoTitle();
        this.contentView.findViewById(R.id.login).setOnClickListener(this);
        this.contentView.findViewById(R.id.forgot_password).setOnClickListener(this);
        this.contentView.findViewById(R.id.login_facebook).setOnClickListener(this);
        this.contentView.findViewById(R.id.login_twitter).setOnClickListener(this);
        ImageButton imageButton = (ImageButton) this.contentView.findViewById(R.id.ib_login_name);
        this.userNameClear = imageButton;
        imageButton.setOnClickListener(this);
        ImageButton imageButton2 = (ImageButton) this.contentView.findViewById(R.id.ib_login_password);
        this.userPasswordClear = imageButton2;
        imageButton2.setOnClickListener(this);
        this.userNameEditText = (EditText) this.contentView.findViewById(R.id.input_login_name);
        this.tvGetCode = (TextView) this.contentView.findViewById(R.id.tv_get_code);
        this.tvLoginWay = (TextView) this.contentView.findViewById(R.id.tv_login_way);
        this.passwordEditText = (EditText) this.contentView.findViewById(R.id.input_login_password);
        ImageButton imageButton3 = (ImageButton) this.contentView.findViewById(R.id.ib_login_password_visible);
        this.userPasswordVisible = imageButton3;
        imageButton3.setOnClickListener(this);
        this.tvGetCode.setOnClickListener(this);
        this.tvLoginWay.setOnClickListener(this);
        this.userNameEditText.addTextChangedListener(this.textWatcher);
        this.passwordEditText.addTextChangedListener(this.textWatcher);
        this.userNameEditText.setOnFocusChangeListener(this);
        this.passwordEditText.setOnFocusChangeListener(this);
        String sysMap = CommonUtils.getSysMap(getContext(), Consts.SHARED_USER_NAME);
        if (!isEmpty(sysMap)) {
            this.userNameEditText.setText(sysMap);
            this.userNameClear.setVisibility(0);
            this.passwordEditText.requestFocus();
        }
        switchLoginWay(this.loginWay);
    }

    public void switchLoginWay(int i) {
        if (i == 0) {
            this.userPasswordVisible.setVisibility(8);
            this.passwordEditText.setHint(R.string.Hint_input_verify_code);
            this.tvGetCode.setVisibility(0);
            this.tvLoginWay.setText(getString(R.string.Use_password_to_login) + " >");
            this.userPasswordVisible.setVisibility(8);
            return;
        }
        if (i == 1) {
            this.userPasswordVisible.setVisibility(8);
            this.passwordEditText.setHint(R.string.Hint_input_password);
            this.tvGetCode.setVisibility(8);
            this.tvLoginWay.setText(getString(R.string.Use_code_to_login) + " >");
            this.userPasswordVisible.setVisibility(0);
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_login_way) {
            if (this.loginWay == 0) {
                this.loginWay = 1;
            } else {
                this.loginWay = 0;
            }
            switchLoginWay(this.loginWay);
            return;
        }
        if (id == R.id.login) {
            String string = this.userNameEditText.getEditableText().toString();
            String string2 = this.passwordEditText.getEditableText().toString();
            if (this.mRegion == null) {
                PetkitToast.showShortToast(getActivity(), R.string.Error_select_region, R.drawable.toast_failed);
                return;
            }
            if (isEmpty(string)) {
                PetkitToast.showTopToast(getActivity(), getActivity().getResources().getString(R.string.Hint_input_account), R.drawable.top_toast_error_icon, 1);
                return;
            }
            if (this.loginWay != 0 && isEmpty(string2)) {
                PetkitToast.showTopToast(getActivity(), getActivity().getResources().getString(R.string.Hint_input_password), R.drawable.top_toast_error_icon, 1);
                return;
            }
            HashMap<String, String> map = new HashMap<>();
            if (this.loginWay == 1) {
                map.put("username", string);
                map.put("password", Md5.encode(string2));
                map.put("client", CommonUtils.getClientInfor(com.petkit.android.utils.Constants.PETKIT_APP_ID));
                map.put("encrypt", "1");
                map.put("oldVersion", CommonUtils.getSysMap(getContext(), Consts.SHARED_OLD_VERSION));
            } else {
                map.put("username", string);
                map.put("validCode", string2);
                map.put("client", CommonUtils.getClientInfor(com.petkit.android.utils.Constants.PETKIT_APP_ID));
                map.put("encrypt", "1");
                map.put("oldVersion", CommonUtils.getSysMap(getContext(), Consts.SHARED_OLD_VERSION));
            }
            LoadDialog.show(getActivity());
            startLogin(map);
            return;
        }
        if (id == R.id.forgot_password) {
            Intent intent = new Intent(getContext(), (Class<?>) ForgotPasswordActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(com.petkit.android.utils.Constants.EXTRA_REGION, this.mRegion);
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }
        if (id == R.id.tv_get_code) {
            if (!TextUtils.isEmpty(this.userNameEditText.getText().toString())) {
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
        if (id == R.id.login_twitter) {
            loginPlatform("TWITTER");
            return;
        }
        if (id == R.id.login_facebook) {
            loginPlatform("FACEBOOK");
            return;
        }
        if (id == R.id.ib_login_name) {
            this.userNameEditText.setText("");
            this.userNameClear.setVisibility(4);
            return;
        }
        if (id == R.id.ib_login_password) {
            this.passwordEditText.setText("");
            this.userPasswordClear.setVisibility(4);
            return;
        }
        if (id == R.id.ib_login_password_visible) {
            if (!this.isShow) {
                this.isShow = true;
                this.userPasswordVisible.setImageResource(R.drawable.passward_visible);
                this.passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                EditText editText = this.passwordEditText;
                editText.setSelection(editText.getText().length());
                return;
            }
            this.isShow = false;
            this.userPasswordVisible.setImageResource(R.drawable.passward_invisible);
            this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            EditText editText2 = this.passwordEditText;
            editText2.setSelection(editText2.getText().length());
        }
    }

    public void openRemainTimer(int i) {
        if (i < 0) {
            i = 0;
        }
        this.leftTime = i;
        if (this.disposable == null) {
            this.disposable = Observable.interval(1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.registe.fragment.PetkitLoginFragment$$ExternalSyntheticLambda0
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
        this.tvGetCode.setText(this.leftTime + "s");
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
        Observable.timer(60L, TimeUnit.SECONDS).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.registe.fragment.PetkitLoginFragment.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                PetkitLoginFragment.this.isSend = false;
            }
        });
    }

    private void sendForChangeCode() {
        HashMap map = new HashMap();
        map.put("username", this.userNameEditText.getText().toString());
        post(ApiTools.SAMPLE_API_USER_SENDCODEFORQUICKLOGIN, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.registe.fragment.PetkitLoginFragment.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showToast(baseRsp.getError().getMsg());
                    PetkitLoginFragment.this.isSend = false;
                } else if (this.responseResult.contains("success")) {
                    PetkitToast.showToast(PetkitLoginFragment.this.getString(R.string.Sent));
                    PetkitLoginFragment.this.openRemainTimer(60);
                    PetkitLoginFragment.this.startCountDown();
                    PetkitLoginFragment.this.isSend = true;
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }
        }, false);
    }

    public void startLogin(final HashMap<String, String> map) {
        new LoginUtils(getActivity(), new LoginUtils.LoginResultListener() { // from class: com.petkit.android.activities.registe.fragment.PetkitLoginFragment.5
            @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
            public void onFailed(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
            public void onSuccess() {
                PetkitLoginFragment petkitLoginFragment = PetkitLoginFragment.this;
                if (petkitLoginFragment.isEmpty(CommonUtils.getSysMap(petkitLoginFragment.getActivity(), Consts.SHARED_SESSION_ID))) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(com.petkit.android.utils.Constants.EXTRA_BOOLEAN, true);
                    bundle.putBoolean(com.petkit.android.utils.Constants.EXTRA_INFO_BOOLEAN, true);
                    SerMap serMap = new SerMap();
                    serMap.setMap(map);
                    bundle.putSerializable("params", serMap);
                    PetkitLoginFragment.this.startActivityWithData(RegionMainActivity.class, bundle, false);
                } else {
                    try {
                        LoadDialog.dismissDialog();
                    } catch (IllegalArgumentException unused) {
                    }
                    CommonUtils.addSysMap(PetkitLoginFragment.this.getActivity(), Consts.SHARED_USER_NAME, PetkitLoginFragment.this.userNameEditText.getEditableText().toString());
                    PetkitLoginFragment.this.entryMainHome();
                }
                Intent intent = new Intent();
                intent.setAction("com.petkit.android.login");
                intent.putExtra(Consts.SHARED_SESSION_ID, CommonUtils.getSysMap(Consts.SHARED_SESSION_ID));
                intent.putExtra(com.petkit.android.utils.Constants.MATE_BASE_GATEWAY, CommonUtils.getSysMap(com.petkit.android.utils.Constants.SHARED_BASE_GATEWAY));
                intent.putExtra(Consts.SHARED_USER_ID, CommonUtils.getCurrentUserId());
                PetkitLoginFragment.this.getActivity().sendBroadcast(intent);
            }

            @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
            public void onFailed(String str) {
                LoadDialog.dismissDialog();
                PetkitToast.showTopToast(PetkitLoginFragment.this.getActivity(), str, R.drawable.top_toast_error_icon, 1);
            }
        }, map, this.mRegion).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void entryMainHome() {
        Intent intent = new Intent();
        intent.setAction(com.petkit.android.utils.Constants.BROADCAST_MSG_CHANGE_USER);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        startActivity(HomeActivity.class, false);
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
        ((BaseActivity) getActivity()).sendCloseActivityBroadcast();
    }

    @Subscriber
    public void handleTwitterResult(TwitterResult twitterResult) {
        TwitterAuthClient twitterAuthClient = this.twitterAuthClient;
        if (twitterAuthClient != null) {
            twitterAuthClient.onActivityResult(twitterResult.getRequestCode(), twitterResult.getResultCode(), twitterResult.getData());
        }
    }

    @Subscriber
    public void handleFacebookResult(FaceBookResult faceBookResult) {
        CallbackManager callbackManager = this.callbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(faceBookResult.getRequestCode(), faceBookResult.getResultCode(), faceBookResult.getData());
        }
    }

    public void loginPlatform(String str) {
        LoadDialog.show(getActivity());
        if ("TWITTER".equals(str)) {
            TwitterAuthClient twitterAuthClient = this.twitterAuthClient;
            if (twitterAuthClient == null) {
                this.twitterAuthClient = new TwitterAuthClient();
            } else {
                twitterAuthClient.cancelAuthorize();
                this.twitterAuthClient = new TwitterAuthClient();
            }
            this.twitterAuthClient.authorize(getActivity(), new Callback<TwitterSession>() { // from class: com.petkit.android.activities.registe.fragment.PetkitLoginFragment.6
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<TwitterSession> result) {
                    if (result == null) {
                        return;
                    }
                    HashMap<String, String> map = new HashMap<>();
                    HashMap map2 = new HashMap();
                    map2.put("appid", Consts.APP_ID_TWITTER);
                    map2.put("oauthToken", result.data.getAuthToken().token);
                    map2.put("oauthTokenSecret", result.data.getAuthToken().secret);
                    map.put("tpType", "12");
                    map.put("username", result.data.getUserId() + "");
                    map.put("tpInfo", JSONUtils.mapToJsonStr(map2));
                    map.put("client", CommonUtils.getClientInfor(com.petkit.android.utils.Constants.PETKIT_APP_ID));
                    map.put("encrypt", "1");
                    map.put("oldVersion", CommonUtils.getSysMap(PetkitLoginFragment.this.getActivity(), Consts.SHARED_OLD_VERSION));
                    PetkitLoginFragment.this.startLogin(map);
                }

                @Override // com.twitter.sdk.android.core.Callback
                public void failure(TwitterException twitterException) {
                    PetkitLoginFragment.this.twitterAuthClient.cancelAuthorize();
                    LoadDialog.dismissDialog();
                    PetkitToast.showTopToast(PetkitLoginFragment.this.getActivity(), PetkitLoginFragment.this.getActivity().getResources().getString(R.string.Authorization_failed), R.drawable.top_toast_error_icon, 1);
                }
            });
            return;
        }
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        if (view.getId() == R.id.input_login_name) {
            if (z && !isEmpty(this.userNameEditText.getText().toString())) {
                this.userNameClear.setVisibility(0);
            }
            this.userPasswordClear.setVisibility(4);
            return;
        }
        if (view.getId() == R.id.input_login_password) {
            if (z && !isEmpty(this.passwordEditText.getText().toString())) {
                this.userPasswordClear.setVisibility(0);
            }
            this.userNameClear.setVisibility(4);
        }
    }

    public void refreshFocus() {
        EditText editText = this.userNameEditText;
        if (editText == null || this.passwordEditText == null) {
            return;
        }
        if (editText.getText().toString().length() > 0) {
            this.passwordEditText.requestFocus();
        } else {
            this.userNameEditText.requestFocus();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        CallbackManager callbackManager = this.callbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        }
        super.onActivityResult(i, i2, intent);
    }
}

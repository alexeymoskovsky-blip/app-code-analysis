package com.petkit.android.activities.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.im.event.LogoutEvent;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.registe.NormalLoginActivity;
import com.petkit.android.activities.registe.RegionMainActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.ThirdpartieRsp;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.Account;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.Md5;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes5.dex */
public class AccountsManageActivity extends BaseActivity {
    private static final int ACCOUNT_TYPE_EMAIL = 0;
    private static final int ACCOUNT_TYPE_FACEBOOK = 1;
    private static final int ACCOUNT_TYPE_TWITTER = 2;
    private CallbackManager callbackManager;
    private String email;
    private boolean isbindFacebook;
    private boolean isbindTwitter;
    private Account mAccount;
    private Activity mActivity;
    private AlertDialog mDialog;
    TextView tvPasswordSetting;
    private TwitterAuthClient twitterAuthClient;

    /* JADX INFO: Access modifiers changed from: private */
    public void unBindthirdparty(int i) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = this;
        setContentView(R.layout.activity_accounts_manage);
        initFaceBook();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.account_region) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.EXTRA_BOOLEAN, true);
            startActivityWithData(RegionMainActivity.class, bundle, false);
            return;
        }
        if (id == R.id.setting_logoff) {
            showLogoffDialog();
            return;
        }
        if (id == R.id.rl_root_panel) {
            int iIntValue = ((Integer) view.getTag()).intValue();
            if (iIntValue == 0) {
                if (TextUtils.isEmpty(UserInforUtils.getAccount().getEmail())) {
                    startActivityForResult(BindEmailActivity.class, 17);
                    return;
                } else {
                    startActivityForResult(UpdateEmailActivity.class, 17);
                    return;
                }
            }
            if (1 == iIntValue) {
                if (this.isbindFacebook) {
                    return;
                }
                loginPlatform("FACEBOOK");
                return;
            } else {
                if (this.isbindTwitter) {
                    return;
                }
                loginPlatform("TWITTER");
                return;
            }
        }
        if (id == R.id.account_name) {
            startActivityForResult(AccountNameSettingActivity.class, 19);
            return;
        }
        if (id == R.id.account_pw) {
            if (TextUtils.isEmpty(UserInforUtils.getAccount().getEmail())) {
                startActivityForResult(BindEmailActivity.class, 17);
                return;
            }
            Intent intent = new Intent(this, (Class<?>) ModifyPasswordActivity.class);
            intent.putExtra(Constants.EXTRA_TYPE, ModifyPasswordActivity.MODIFYPASSWORD_SETTING_TYPE);
            startActivityForResult(intent, 18);
        }
    }

    private void showLogoffDialog() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_confirm_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setOutsideTouchable(false);
        ((TextView) viewInflate.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_confirm)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showLogoffDialog$1(easyPopupWindow, view);
            }
        });
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_content);
        textView.setText(getString(R.string.Logoff_prompt));
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(-1);
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLogoffDialog$1(EasyPopupWindow easyPopupWindow, View view) {
        easyPopupWindow.dismiss();
        checkDevice();
    }

    private void checkDevice() {
        int familyListAllDevicesCount = FamilyUtils.getInstance().getFamilyListAllDevicesCount(FamilyUtils.getInstance().getAllOwnFamilyList(this));
        String email = UserInforUtils.getAccount().getEmail();
        this.email = email;
        if (familyListAllDevicesCount > 0) {
            PetkitToast.showToast(getString(R.string.Logoff_fail_prompt));
            return;
        }
        if (!TextUtils.isEmpty(email)) {
            showLogoffDialogAgain();
        } else if (!TextUtils.isEmpty(this.email)) {
            showLogoffDialogAgain();
        } else {
            showLogoffDialogAgainWithoutEmail();
        }
    }

    private void showLogoffDialogAgainWithoutEmail() {
        UserInforUtils.getAccount().getMobile();
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_confirm_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setOutsideTouchable(false);
        ((TextView) viewInflate.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView.setText(getString(R.string.Stick_logoff));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showLogoffDialogAgainWithoutEmail$3(easyPopupWindow, view);
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_content)).setText(getString(R.string.Logoff_account_prompt));
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(-1);
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLogoffDialogAgainWithoutEmail$3(EasyPopupWindow easyPopupWindow, View view) {
        logoff();
        easyPopupWindow.dismiss();
    }

    private void showLogoffDialogAgain() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_confirm_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setOutsideTouchable(false);
        ((TextView) viewInflate.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView.setText(getString(R.string.Stick_logoff));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showLogoffDialogAgain$5(easyPopupWindow, view);
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_content)).setText(getString(R.string.Logoff_content_prompt) + "：\n" + this.email);
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(-1);
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLogoffDialogAgain$5(EasyPopupWindow easyPopupWindow, View view) {
        sendLogoffCode();
        easyPopupWindow.dismiss();
    }

    private void logoff() {
        post(ApiTools.SAMPLE_API_USER_LOGOFF, new HashMap(), new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.AccountsManageActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null && resultStringRsp.getResult() != null && resultStringRsp.getResult().equalsIgnoreCase("success")) {
                    CommonUtils.addSysMap(AccountsManageActivity.this, Consts.SHARED_SESSION_ID, "");
                    AsyncHttpUtil.addHttpHeader(Consts.HTTP_HEADER_SESSION, "");
                    DeviceCenterUtils.resetInfoWhileLogout();
                    LocalBroadcastManager.getInstance(AccountsManageActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_LOGOUT));
                    EventBus.getDefault().post(new LogoutEvent());
                    AccountsManageActivity.this.startActivity(NormalLoginActivity.class, true);
                    AccountsManageActivity.this.overridePendingTransition(0, 0);
                } else {
                    AccountsManageActivity.this.showShortToast(resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
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

    private void showUnBindDialog(final int i) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this.mActivity).setCancelable(false).setTitle(this.mActivity.getString(R.string.Prompt)).setMessage(this.mActivity.getString(R.string.OK) + this.mActivity.getString(R.string.Unbind) + "?").setPositiveButton(this.mActivity.getString(R.string.OK), new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                AccountsManageActivity.this.mDialog.dismiss();
                AccountsManageActivity.this.unBindthirdparty(i);
            }
        }).setNegativeButton(this.mActivity.getString(R.string.Cancel), new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.setting.AccountsManageActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                AccountsManageActivity.this.mDialog.dismiss();
            }
        }).create();
        this.mDialog = alertDialogCreate;
        alertDialogCreate.show();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        CallbackManager callbackManager = this.callbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        }
        if (i != 140) {
            if (i2 == -1) {
                initAccountInfo();
            }
        } else {
            TwitterAuthClient twitterAuthClient = this.twitterAuthClient;
            if (twitterAuthClient != null) {
                twitterAuthClient.onActivityResult(i, i2, intent);
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Account_management);
        setTitleLineVisibility(8);
        initAccountInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAccountInfo() {
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        TextView textView = (TextView) findViewById(R.id.tv_password_setting);
        this.tvPasswordSetting = textView;
        textView.setText(this.mActivity.getResources().getString(R.string.Change_password));
        ((TextView) findViewById(R.id.account_region)).setOnClickListener(this);
        ((TextView) findViewById(R.id.setting_logoff)).setOnClickListener(this);
        TextView textView2 = (TextView) findViewById(R.id.account_name);
        textView2.setText(currentLoginResult.getUser().getNick());
        textView2.setOnClickListener(this);
        ((TextView) findViewById(R.id.account_pw)).setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.account_list);
        linearLayout.removeAllViews();
        Account account = UserInforUtils.getAccount();
        this.mAccount = account;
        if (isEmpty(account.getEmail())) {
            linearLayout.addView(getAccountCellView(0, R.drawable.account_email, R.string.Email, "", R.string.Bind, R.drawable.selector_solid_account_blue_with_radius, true));
        } else {
            linearLayout.addView(getAccountCellView(0, R.drawable.account_email, R.string.Email, this.mAccount.getEmail(), R.string.Change_email, R.drawable.selector_solid_account_gray_with_radius, true));
        }
        List<Account.Thirdpartie> thirdparties = this.mAccount.getThirdparties();
        if (thirdparties != null && thirdparties.size() == 2) {
            for (int i = 0; i < thirdparties.size(); i++) {
                if (thirdparties.get(i).getType() == 11) {
                    this.isbindFacebook = true;
                    linearLayout.addView(getAccountCellView(1, R.drawable.login_facebook, R.string.Share_facebook, thirdparties.get(i).getInfo().getNick(), R.string.Already_bind, R.drawable.selector_solid_account_gray_with_radius, true));
                } else {
                    this.isbindTwitter = true;
                }
            }
            return;
        }
        if (thirdparties != null && thirdparties.size() == 1) {
            if (thirdparties.get(0).getType() == 11) {
                this.isbindTwitter = false;
                this.isbindFacebook = true;
                linearLayout.addView(getAccountCellView(1, R.drawable.login_facebook, R.string.Share_facebook, thirdparties.get(0).getInfo().getNick(), R.string.Already_bind, R.drawable.selector_solid_account_gray_with_radius, true));
                return;
            } else {
                this.isbindTwitter = true;
                this.isbindFacebook = false;
                linearLayout.addView(getAccountCellView(1, R.drawable.login_facebook, R.string.Share_facebook, "", R.string.Bind, R.drawable.selector_solid_account_blue_with_radius, true));
                return;
            }
        }
        this.isbindTwitter = false;
        this.isbindFacebook = false;
        linearLayout.addView(getAccountCellView(1, R.drawable.login_facebook, R.string.Share_facebook, "", R.string.Bind, R.drawable.selector_solid_account_blue_with_radius, true));
    }

    private View getAccountCellView(int i, int i2, int i3, String str, int i4, int i5, boolean z) {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_account_cell, (ViewGroup) null);
        RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.rl_root_panel);
        relativeLayout.setTag(Integer.valueOf(i));
        relativeLayout.setOnClickListener(this);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.account_icon);
        imageView.setImageResource(i2);
        imageView.setVisibility(8);
        ((TextView) viewInflate.findViewById(R.id.account_type_name)).setText(i3);
        TextView textView = (TextView) viewInflate.findViewById(R.id.account_name);
        textView.setText(R.string.Unbind_account);
        if (!TextUtils.isEmpty(str)) {
            textView.setText(str);
        }
        Button button = (Button) viewInflate.findViewById(R.id.account_action);
        button.setText(i4);
        button.setBackgroundResource(i5);
        button.setTag(Integer.valueOf(i));
        button.setOnClickListener(this);
        viewInflate.findViewById(R.id.view_line).setVisibility(z ? 0 : 8);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindthirdparty(HashMap<String, String> map) {
        post(ApiTools.SAMPLE_API_USER_BINDTHIRDPARTY, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.AccountsManageActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ThirdpartieRsp thirdpartieRsp = (ThirdpartieRsp) this.gson.fromJson(this.responseResult, ThirdpartieRsp.class);
                if (thirdpartieRsp.getError() != null) {
                    AccountsManageActivity.this.showShortToast(thirdpartieRsp.getError().getMsg(), R.drawable.toast_failed);
                } else if (thirdpartieRsp.getResult() != null) {
                    List<Account.Thirdpartie> thirdparties = AccountsManageActivity.this.mAccount.getThirdparties();
                    if (thirdparties == null) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(thirdpartieRsp.getResult());
                        AccountsManageActivity.this.mAccount.setThirdparties(arrayList);
                    } else {
                        thirdparties.add(thirdpartieRsp.getResult());
                    }
                    AccountsManageActivity accountsManageActivity = AccountsManageActivity.this;
                    UserInforUtils.storeAccount(accountsManageActivity, accountsManageActivity.mAccount);
                    AccountsManageActivity.this.initAccountInfo();
                    AccountsManageActivity.this.showShortToast(R.string.Success);
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

    private void sendLogoffCode() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("ts", "" + jCurrentTimeMillis);
        map.put("codeKey", Md5.encode("0" + (jCurrentTimeMillis - 1)).toLowerCase(Locale.ENGLISH));
        showLoadDialog();
        post(ApiTools.SAMPLE_API_USER_SEND_DELETE_ACCOUNT_CODE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.AccountsManageActivity.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                AccountsManageActivity.this.dismissLoadDialog();
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AccountsManageActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else if (this.responseResult.contains("success")) {
                    Intent intent = new Intent(AccountsManageActivity.this, (Class<?>) CheckCodeActivity.class);
                    intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                    AccountsManageActivity.this.startActivity(intent);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                AccountsManageActivity.this.dismissLoadDialog();
            }
        }, false);
    }

    public void loginPlatform(String str) {
        LoadDialog.show(this);
        if ("TWITTER".equals(str)) {
            if (this.twitterAuthClient == null) {
                this.twitterAuthClient = new TwitterAuthClient();
            }
            this.twitterAuthClient.authorize(this, new Callback<TwitterSession>() { // from class: com.petkit.android.activities.setting.AccountsManageActivity.6
                @Override // com.twitter.sdk.android.core.Callback
                public void success(Result<TwitterSession> result) {
                    if (result == null) {
                        return;
                    }
                    HashMap map = new HashMap();
                    map.put("tpType", "12");
                    map.put("tpInfo", "{\"appid\":\"dWRwdHhoemRCaC1HT1QxSWxVYm86MTpjaQ\",\"oauthToken\":\"" + result.data.getAuthToken().token + "\",\"oauthTokenSecret\":\"" + result.data.getAuthToken().secret + "\"}");
                    AccountsManageActivity.this.bindthirdparty(map);
                }

                @Override // com.twitter.sdk.android.core.Callback
                public void failure(TwitterException twitterException) {
                    AccountsManageActivity.this.twitterAuthClient.cancelAuthorize();
                    LoadDialog.dismissDialog();
                    AccountsManageActivity accountsManageActivity = AccountsManageActivity.this;
                    accountsManageActivity.showShortToast(accountsManageActivity.getString(R.string.Authorization_failed));
                }
            });
            return;
        }
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    private void initFaceBook() {
        this.callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() { // from class: com.petkit.android.activities.setting.AccountsManageActivity.7
            @Override // com.facebook.FacebookCallback
            public void onSuccess(LoginResult loginResult) {
                String token = loginResult.getAccessToken().getToken();
                HashMap map = new HashMap();
                map.put("tpType", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
                map.put("tpInfo", "{\"accessToken\":\"" + token + "\"}");
                AccountsManageActivity.this.bindthirdparty(map);
            }

            @Override // com.facebook.FacebookCallback
            public void onCancel() {
                LoadDialog.dismissDialog();
            }

            @Override // com.facebook.FacebookCallback
            public void onError(FacebookException facebookException) {
                LoadDialog.dismissDialog();
                AccountsManageActivity accountsManageActivity = AccountsManageActivity.this;
                accountsManageActivity.showShortToast(accountsManageActivity.getString(R.string.Authorization_failed));
            }
        });
    }
}

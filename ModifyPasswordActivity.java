package com.petkit.android.activities.setting;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.Md5;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes5.dex */
public class ModifyPasswordActivity extends BaseActivity {
    public static int MODIFYPASSWORD_FORGOT_TYPE = 1;
    public static int MODIFYPASSWORD_SETTING_TYPE = 2;
    String email;
    private EditText newPwEditText;
    private String oldPw;
    private EditText oldPwEditText;
    private EditText repEditText;
    TextView tvForget;
    private int type;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.type = bundle.getInt(Constants.EXTRA_TYPE);
            this.oldPw = bundle.getString(Constants.EXTRA_DYNAMIC_PASSWORD);
        } else {
            this.type = getIntent().getIntExtra(Constants.EXTRA_TYPE, 0);
            this.oldPw = getIntent().getStringExtra(Constants.EXTRA_DYNAMIC_PASSWORD);
        }
        setContentView(R.layout.activity_modify_password);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_TYPE, this.type);
        bundle.putString(Constants.EXTRA_DYNAMIC_PASSWORD, this.oldPw);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Change_password);
        setTitleLineVisibility(8);
        this.oldPwEditText = (EditText) findViewById(R.id.setting_oldpw);
        this.newPwEditText = (EditText) findViewById(R.id.setting_newpw);
        this.repEditText = (EditText) findViewById(R.id.setting_repeatpw);
        this.tvForget = (TextView) findViewById(R.id.tv_forgetpw);
        findViewById(R.id.tv_save).setOnClickListener(this);
        this.tvForget.setOnClickListener(this);
        int i = this.type;
        if (i == MODIFYPASSWORD_FORGOT_TYPE) {
            setTitleLeftTextButton(R.string.Cancel, this);
            findViewById(R.id.oldpw_layout).setVisibility(8);
            findViewById(R.id.hint_oldpw).setVisibility(8);
            this.tvForget.setVisibility(8);
            return;
        }
        if (i == MODIFYPASSWORD_SETTING_TYPE) {
            findViewById(R.id.oldpw_layout).setVisibility(0);
            findViewById(R.id.hint_oldpw).setVisibility(0);
            this.tvForget.setVisibility(0);
        }
    }

    private void doModify() {
        if (this.type == MODIFYPASSWORD_SETTING_TYPE) {
            this.oldPw = this.oldPwEditText.getEditableText().toString();
        }
        String string = this.newPwEditText.getEditableText().toString();
        String string2 = this.repEditText.getEditableText().toString();
        if (isEmpty(string) || string.length() < 6 || isEmpty(string2)) {
            showLongToast(R.string.Error_wrong_password);
            return;
        }
        boolean zIsContainChinese = CommonUtils.isContainChinese(string);
        boolean zIsContainChinese2 = CommonUtils.isContainChinese(string2);
        if (zIsContainChinese || zIsContainChinese2) {
            showLongToast(R.string.Password_illegal);
            return;
        }
        if (string.equals(this.oldPw)) {
            showLongToast(R.string.Error_new_password_same_with_old);
            return;
        }
        if (!string.equals(string2)) {
            showLongToast(R.string.Error_repeat_new_password);
            return;
        }
        HashMap map = new HashMap();
        if (this.type == MODIFYPASSWORD_FORGOT_TYPE) {
            map.put("username ", CommonUtils.getSysMap(Consts.SHARED_USER_NAME));
        }
        map.put("password", string);
        map.put("oldPassword", this.oldPw);
        post(ApiTools.SAMPLE_API_USER_PASSD, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.ModifyPasswordActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (this.responseResult.contains("success")) {
                    ModifyPasswordActivity.this.showLongToast(R.string.Succeed, R.drawable.toast_succeed);
                    if (ModifyPasswordActivity.this.type != ModifyPasswordActivity.MODIFYPASSWORD_FORGOT_TYPE) {
                        if (ModifyPasswordActivity.this.type == ModifyPasswordActivity.MODIFYPASSWORD_SETTING_TYPE) {
                            ModifyPasswordActivity.this.finish();
                            return;
                        }
                        return;
                    } else {
                        ModifyPasswordActivity.this.startActivity(HomeActivity.class, false);
                        ModifyPasswordActivity.this.finish();
                        ModifyPasswordActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
                        return;
                    }
                }
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp != null && baseRsp.getError() != null) {
                    ModifyPasswordActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    ModifyPasswordActivity.this.showLongToast(R.string.Failure, R.drawable.toast_failed);
                }
            }
        }, false);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_left_text) {
            startActivity(HomeActivity.class, false);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
            return;
        }
        if (id == R.id.tv_save) {
            doModify();
            return;
        }
        if (id == R.id.tv_forgetpw) {
            this.email = UserInforUtils.getAccount().getEmail();
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_confirm_layout, (ViewGroup) null);
            final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
            easyPopupWindow.setOutsideTouchable(false);
            ((TextView) viewInflate.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.ModifyPasswordActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    easyPopupWindow.dismiss();
                }
            });
            ((TextView) viewInflate.findViewById(R.id.tv_confirm)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.ModifyPasswordActivity$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$onClick$1(easyPopupWindow, view2);
                }
            });
            ((TextView) viewInflate.findViewById(R.id.tv_content)).setText(getString(R.string.Hint_reset_password) + "：\n+" + this.email);
            easyPopupWindow.setContentView(viewInflate);
            easyPopupWindow.setHeight(-1);
            easyPopupWindow.setWidth(-1);
            easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(EasyPopupWindow easyPopupWindow, View view) {
        forgetPassword(this.email);
        easyPopupWindow.dismiss();
    }

    private void forgetPassword(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("ts", "" + jCurrentTimeMillis);
        map.put("codeKey", Md5.encode(str + "0" + (jCurrentTimeMillis - 1)).toLowerCase(Locale.ENGLISH));
        showLoadDialog();
        post(ApiTools.PASSPORT_RESETPASSD, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.ModifyPasswordActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ModifyPasswordActivity.this.dismissLoadDialog();
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    ModifyPasswordActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else if (this.responseResult.contains("success")) {
                    ModifyPasswordActivity.this.startActivity(CheckCodeActivity.class);
                }
            }
        }, false);
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) {
            int i = this.type;
            if (i == MODIFYPASSWORD_FORGOT_TYPE) {
                startActivity(HomeActivity.class, false);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
                return true;
            }
            if (i != MODIFYPASSWORD_SETTING_TYPE) {
                return true;
            }
            finish();
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}

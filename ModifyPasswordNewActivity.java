package com.petkit.android.activities.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.PetkitToast;
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
public class ModifyPasswordNewActivity extends BaseActivity {
    public static int MODIFYPASSWORD_FORGOT_TYPE = 1;
    public static int MODIFYPASSWORD_SETTING_TYPE = 2;
    String email;
    private ImageView ivBack;
    private ImageView ivNewPassword;
    private ImageView ivNewPasswordRepeat;
    private EditText newPwEditText;
    private String oldPw;
    private EditText oldPwEditText;
    private EditText repEditText;
    private TextView tvDetermine;
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
        setContentView(R.layout.activity_modify_password_new);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_TYPE, this.type);
        bundle.putString(Constants.EXTRA_DYNAMIC_PASSWORD, this.oldPw);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
        setTitleRightButton(R.string.Next, this);
        this.oldPwEditText = (EditText) findViewById(R.id.setting_oldpw);
        this.newPwEditText = (EditText) findViewById(R.id.setting_newpw);
        this.repEditText = (EditText) findViewById(R.id.setting_repeatpw);
        this.ivNewPassword = (ImageView) findViewById(R.id.iv_new_password);
        this.ivNewPasswordRepeat = (ImageView) findViewById(R.id.iv_password_repeat);
        this.tvDetermine = (TextView) findViewById(R.id.tv_determine);
        this.ivBack = (ImageView) findViewById(R.id.iv_back);
        this.ivNewPassword.setOnClickListener(this);
        this.ivNewPasswordRepeat.setOnClickListener(this);
        this.tvDetermine.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        checkTvState();
        TextView textView = (TextView) findViewById(R.id.tv_forgetpw);
        this.tvForget = textView;
        textView.setOnClickListener(this);
        int i = this.type;
        if (i == MODIFYPASSWORD_FORGOT_TYPE) {
            setTitleLeftTextButton(R.string.Cancel, this);
            findViewById(R.id.oldpw_layout).setVisibility(8);
            findViewById(R.id.hint_oldpw).setVisibility(8);
            this.tvForget.setVisibility(8);
        } else if (i == MODIFYPASSWORD_SETTING_TYPE) {
            findViewById(R.id.oldpw_layout).setVisibility(0);
            findViewById(R.id.hint_oldpw).setVisibility(0);
            this.tvForget.setVisibility(0);
        }
        this.newPwEditText.addTextChangedListener(new TextWatcher() { // from class: com.petkit.android.activities.setting.ModifyPasswordNewActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                ModifyPasswordNewActivity.this.ivNewPassword.setVisibility(TextUtils.isEmpty(charSequence) ? 4 : 0);
                ModifyPasswordNewActivity.this.checkTvState();
            }
        });
        this.repEditText.addTextChangedListener(new TextWatcher() { // from class: com.petkit.android.activities.setting.ModifyPasswordNewActivity.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                ModifyPasswordNewActivity.this.ivNewPasswordRepeat.setVisibility(TextUtils.isEmpty(charSequence) ? 4 : 0);
                ModifyPasswordNewActivity.this.checkTvState();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkTvState() {
        if (!TextUtils.isEmpty(this.newPwEditText.getText().toString()) && !TextUtils.isEmpty(this.repEditText.getText().toString())) {
            this.tvDetermine.setBackgroundResource(R.drawable.selector_solid_new_blue);
            this.tvDetermine.setClickable(true);
            this.tvDetermine.setTextColor(-1);
        } else {
            this.tvDetermine.setBackgroundResource(R.drawable.solid_login_light_blue_bg);
            this.tvDetermine.setClickable(false);
            this.tvDetermine.setTextColor(getResources().getColor(R.color.slide_bar_hint_color));
        }
    }

    private void doModify() {
        if (this.type == MODIFYPASSWORD_SETTING_TYPE) {
            this.oldPw = this.oldPwEditText.getEditableText().toString();
        }
        String string = this.newPwEditText.getEditableText().toString();
        String string2 = this.repEditText.getEditableText().toString();
        boolean z = true;
        if (isEmpty(string) || string.length() < 6 || isEmpty(string2)) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Error_wrong_password), R.drawable.top_toast_warn_icon, 1);
            return;
        }
        boolean zIsContainChinese = CommonUtils.isContainChinese(string);
        boolean zIsContainChinese2 = CommonUtils.isContainChinese(string2);
        if (zIsContainChinese || zIsContainChinese2) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Password_illegal), R.drawable.top_toast_warn_icon, 1);
            return;
        }
        if (string.equals(this.oldPw)) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Error_new_password_same_with_old), R.drawable.top_toast_warn_icon, 1);
            return;
        }
        if (!string.equals(string2)) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Error_repeat_new_password), R.drawable.top_toast_warn_icon, 1);
            return;
        }
        HashMap map = new HashMap();
        if (this.type == MODIFYPASSWORD_FORGOT_TYPE) {
            map.put("username ", CommonUtils.getSysMap(Consts.SHARED_USER_NAME));
        }
        map.put("password", string);
        map.put("oldPassword", this.oldPw);
        post(ApiTools.SAMPLE_API_USER_PASSD, map, new AsyncHttpRespHandler(this, z) { // from class: com.petkit.android.activities.setting.ModifyPasswordNewActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (this.responseResult.contains("success")) {
                    ModifyPasswordNewActivity modifyPasswordNewActivity = ModifyPasswordNewActivity.this;
                    PetkitToast.showTopToast(modifyPasswordNewActivity, modifyPasswordNewActivity.getResources().getString(R.string.Succeed), R.drawable.top_toast_success_icon, 1);
                    if (ModifyPasswordNewActivity.this.type != ModifyPasswordNewActivity.MODIFYPASSWORD_FORGOT_TYPE) {
                        if (ModifyPasswordNewActivity.this.type == ModifyPasswordNewActivity.MODIFYPASSWORD_SETTING_TYPE) {
                            ModifyPasswordNewActivity.this.finish();
                            return;
                        }
                        return;
                    } else {
                        ModifyPasswordNewActivity.this.startActivity(HomeActivity.class, false);
                        ModifyPasswordNewActivity.this.finish();
                        ModifyPasswordNewActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
                        return;
                    }
                }
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp != null && baseRsp.getError() != null) {
                    PetkitToast.showTopToast(ModifyPasswordNewActivity.this, baseRsp.getError().getMsg(), R.drawable.top_toast_error_icon, 1);
                } else {
                    ModifyPasswordNewActivity modifyPasswordNewActivity2 = ModifyPasswordNewActivity.this;
                    PetkitToast.showTopToast(modifyPasswordNewActivity2, modifyPasswordNewActivity2.getResources().getString(R.string.Failure), R.drawable.top_toast_error_icon, 1);
                }
            }
        }, false);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
            return;
        }
        if (view.getId() == R.id.title_left_text) {
            startActivity(HomeActivity.class, false);
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
            return;
        }
        if (view.getId() == R.id.title_right_btn) {
            doModify();
            return;
        }
        if (view.getId() == R.id.tv_forgetpw) {
            this.email = UserInforUtils.getAccount().getEmail();
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_confirm_layout, (ViewGroup) null);
            final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
            easyPopupWindow.setOutsideTouchable(false);
            ((TextView) viewInflate.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.ModifyPasswordNewActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    easyPopupWindow.dismiss();
                }
            });
            ((TextView) viewInflate.findViewById(R.id.tv_confirm)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.ModifyPasswordNewActivity$$ExternalSyntheticLambda1
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
            return;
        }
        if (view.getId() == R.id.iv_new_password) {
            this.newPwEditText.setText("");
        } else if (view.getId() == R.id.iv_password_repeat) {
            this.repEditText.setText("");
        } else if (view.getId() == R.id.tv_determine) {
            doModify();
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
        post(ApiTools.PASSPORT_RESETPASSD, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.setting.ModifyPasswordNewActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ModifyPasswordNewActivity.this.dismissLoadDialog();
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showTopToast(ModifyPasswordNewActivity.this, baseRsp.getError().getMsg(), R.drawable.top_toast_error_icon, 1);
                } else if (this.responseResult.contains("success")) {
                    ModifyPasswordNewActivity.this.startActivity(CheckCodeActivity.class);
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

package com.petkit.android.activities.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes5.dex */
public class PrivacySettingActivity extends BaseActivity {
    CheckBox cbCheckLocation;
    CheckBox cbPersonalizedRecommendations;
    CheckBox cbReceiveEmailSubscribe;
    CheckBox cbReceiveFollowMessage;
    private NormalCenterTipWindow personalizedRecommendationsWindow;
    LoginRsp.LoginResult result;
    TextView tvReceiveMessage;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_privacy_setting);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.User_setting_privacy);
        setTitleLineVisibility(8);
        findViewById(R.id.not_let_ta_look_my_pet_posts).setOnClickListener(this);
        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_check_location);
        this.cbCheckLocation = checkBox;
        checkBox.setOnClickListener(this);
        findViewById(R.id.not_look_ta_pet_posts).setOnClickListener(this);
        this.tvReceiveMessage = (TextView) findViewById(R.id.tv_receive_message);
        findViewById(R.id.ll_receive_message).setOnClickListener(this);
        findViewById(R.id.ll_personalized_recommendations).setOnClickListener(this);
        findViewById(R.id.ll_receive_message).setVisibility(8);
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        this.result = currentLoginResult;
        if (currentLoginResult.getSettings().getChatPermission() == 1) {
            this.tvReceiveMessage.setText(R.string.All_people);
        } else {
            this.tvReceiveMessage.setText(R.string.Followed_people);
        }
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.cb_follow);
        this.cbReceiveFollowMessage = checkBox2;
        checkBox2.setChecked(this.result.getSettings().getReceiveFollowMessage() == 1);
        this.cbReceiveFollowMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.setting.PrivacySettingActivity$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$setupViews$0(compoundButton, z);
            }
        });
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.cb_subscribe);
        this.cbReceiveEmailSubscribe = checkBox3;
        checkBox3.setChecked(this.result.getSettings().getSubscribe() == 1);
        this.cbReceiveEmailSubscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.setting.PrivacySettingActivity$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$setupViews$1(compoundButton, z);
            }
        });
        findViewById(R.id.black_list).setOnClickListener(this);
        findViewById(R.id.not_look_ta_pet_posts).setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupViews$0(CompoundButton compoundButton, boolean z) {
        if ((this.result.getSettings().getReceiveFollowMessage() == 1) != z) {
            this.result.getSettings().setReceiveFollowMessage(z ? 1 : 0);
            setReceiveFollowMessage(z ? 1 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupViews$1(CompoundButton compoundButton, boolean z) {
        if ((this.result.getSettings().getSubscribe() == 1) != z) {
            this.result.getSettings().setSubscribe(z ? 1 : 0);
            setReceiveSubscribe(z ? 1 : 0);
        }
    }

    private void openPersonalizedRecommendationsWindow() {
        if (this.personalizedRecommendationsWindow == null) {
            this.personalizedRecommendationsWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.setting.PrivacySettingActivity.1
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    PrivacySettingActivity.this.personalizedRecommendationsWindow.dismiss();
                    PrivacySettingActivity.this.setPersonalizedRecommendations(0);
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    PrivacySettingActivity.this.cbPersonalizedRecommendations.setChecked(true);
                }
            }, (String) null, getString(R.string.Close_recommendation_tip));
        }
        NormalCenterTipWindow normalCenterTipWindow = this.personalizedRecommendationsWindow;
        if (normalCenterTipWindow == null || normalCenterTipWindow.isShowing()) {
            return;
        }
        this.personalizedRecommendationsWindow.show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        this.result = currentLoginResult;
        if (currentLoginResult.getSettings().getChatPermission() == 0) {
            this.tvReceiveMessage.setText(R.string.All_people);
        } else {
            this.tvReceiveMessage.setText(R.string.Followed_people);
        }
        this.cbCheckLocation.setChecked(this.result.getSettings().getLocationPrivacy() == 1);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cb_check_location) {
            updateLocationPrivacy(this.cbCheckLocation.isChecked());
            return;
        }
        if (id == R.id.not_let_ta_look_my_pet_posts) {
            Intent intent = new Intent(this, (Class<?>) ShieldPersonsListActivity.class);
            intent.putExtra(Constants.EXTRA_SHIELD_TYPE, 1);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_none);
            return;
        }
        if (id == R.id.not_look_ta_pet_posts) {
            Intent intent2 = new Intent(this, (Class<?>) ShieldPersonsListActivity.class);
            intent2.putExtra(Constants.EXTRA_SHIELD_TYPE, 2);
            startActivity(intent2);
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_none);
            return;
        }
        if (id == R.id.black_list) {
            Intent intent3 = new Intent(this, (Class<?>) ShieldPersonsListActivity.class);
            intent3.putExtra(Constants.EXTRA_SHIELD_TYPE, 3);
            startActivity(intent3);
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_none);
            return;
        }
        if (id == R.id.ll_receive_message) {
            startActivity(new Intent(this, (Class<?>) MessageSettingActivity.class));
        } else if (id == R.id.ll_personalized_recommendations) {
            startActivity(new Intent(this, (Class<?>) PersonalizedRecommendationsActivity.class));
        }
    }

    private void updateLocationPrivacy(final boolean z) {
        HashMap<String, String> map = new HashMap<>();
        map.put("locationPrivacy", z ? "1" : "0");
        LoadDialog.show(this);
        WebModelRepository.getInstance().updateLocationPrivacy(this, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.setting.PrivacySettingActivity.2
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                PrivacySettingActivity.this.result.getSettings().setLocationPrivacy(z ? 1 : 0);
                UserInforUtils.updateLoginResult(PrivacySettingActivity.this.result);
                LoadDialog.dismissDialog();
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                LoadDialog.dismissDialog();
                PrivacySettingActivity.this.result = UserInforUtils.getCurrentLoginResult();
                PrivacySettingActivity privacySettingActivity = PrivacySettingActivity.this;
                privacySettingActivity.cbReceiveFollowMessage.setChecked(privacySettingActivity.result.getSettings().getReceiveFollowMessage() == 1);
                PrivacySettingActivity privacySettingActivity2 = PrivacySettingActivity.this;
                privacySettingActivity2.cbCheckLocation.setChecked(privacySettingActivity2.result.getSettings().getLocationPrivacy() == 1);
            }
        });
    }

    public void setReceiveFollowMessage(int i) {
        HashMap map = new HashMap(1);
        map.put("receiveFollowMessage", String.valueOf(i));
        LoadDialog.show(this);
        WebModelRepository.getInstance().saveReceiveFollowMessage(this, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.setting.PrivacySettingActivity.3
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                UserInforUtils.updateLoginResult(PrivacySettingActivity.this.result);
                LoadDialog.dismissDialog();
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                LoadDialog.dismissDialog();
                PrivacySettingActivity.this.result = UserInforUtils.getCurrentLoginResult();
                PrivacySettingActivity privacySettingActivity = PrivacySettingActivity.this;
                privacySettingActivity.cbReceiveFollowMessage.setChecked(privacySettingActivity.result.getSettings().getReceiveFollowMessage() == 1);
            }
        });
    }

    public void setReceiveSubscribe(int i) {
        HashMap map = new HashMap(1);
        map.put("agree", String.valueOf(i));
        LoadDialog.show(this);
        WebModelRepository.saveReceiveEmailSubscribe(this, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.setting.PrivacySettingActivity.4
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                UserInforUtils.updateLoginResult(PrivacySettingActivity.this.result);
                LoadDialog.dismissDialog();
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                LoadDialog.dismissDialog();
                PrivacySettingActivity.this.showShortToast(errorInfor.getMsg());
                PrivacySettingActivity.this.result = UserInforUtils.getCurrentLoginResult();
                PrivacySettingActivity privacySettingActivity = PrivacySettingActivity.this;
                privacySettingActivity.cbReceiveEmailSubscribe.setChecked(privacySettingActivity.result.getSettings().getSubscribe() == 1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPersonalizedRecommendations(final int i) {
        HashMap map = new HashMap();
        map.put("recommendPermission", String.valueOf(i));
        post(ApiTools.SAMPLE_API_PERSONAL_RECOMMEND, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.setting.PrivacySettingActivity.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                PrivacySettingActivity.this.dismissLoadDialog();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PrivacySettingActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    PrivacySettingActivity.this.result.getSettings().setRecommendPermission(i);
                    UserInforUtils.updateLoginResult(PrivacySettingActivity.this.result);
                }
            }
        });
    }
}

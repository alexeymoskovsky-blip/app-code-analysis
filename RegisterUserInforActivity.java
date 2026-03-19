package com.petkit.android.activities.registe;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.pim.IMChatController;
import com.petkit.android.activities.personal.ChooseUserGenderActivity;
import com.petkit.android.activities.personal.mode.DefaultPicResult;
import com.petkit.android.activities.personal.mode.UpdateGender;
import com.petkit.android.activities.personal.widget.HeadImageWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.UpdateUserAvatarRsp;
import com.petkit.android.api.http.apiResponse.UserRsp;
import com.petkit.android.model.User;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UploadImagesUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public class RegisterUserInforActivity extends BaseActivity {
    private String birth;
    private BasePetkitWindow birthDayPopMenu;
    private TextView birthDayTextView;
    private DefaultPicResult defaultPicResult;
    private boolean isNeedGuide;
    private EditText nickEditText;
    private String oldNick;
    private TextView tvSex;
    private User user;
    private ImageView userAvatar;
    private int genderSelect = 2;
    private int mYear = -1;
    private int mMonth = -1;
    private int mDay = -1;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        EventBus.getDefault().register(this);
        this.isNeedPhotoController = true;
        super.onCreate(bundle);
        if (bundle == null) {
            this.user = (User) getIntent().getSerializableExtra(Constants.EXTRA_USER_DETAIL);
        } else {
            this.user = (User) bundle.getSerializable(Constants.EXTRA_USER_DETAIL);
        }
        initViews();
        User user = this.user;
        if (user != null && !isEmpty(user.getNick()) && this.user.getBirth() == 0) {
            this.oldNick = this.user.getNick();
            this.isNeedGuide = true;
        } else {
            this.isNeedGuide = false;
        }
        setContentView(R.layout.activity_register_step_2);
        findViewById(R.id.ll_sex).setOnClickListener(this);
        getDefaultPic();
    }

    private void getDefaultPic() {
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_GET_USER_DEFAULT_PIC, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.registe.RegisterUserInforActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RegisterUserInforActivity.this.defaultPicResult = (DefaultPicResult) this.gson.fromJson(this.responseResult, DefaultPicResult.class);
                if (RegisterUserInforActivity.this.defaultPicResult == null || RegisterUserInforActivity.this.defaultPicResult.getResult() == null || RegisterUserInforActivity.this.defaultPicResult.getResult().size() <= 0) {
                    return;
                }
                String url = RegisterUserInforActivity.this.defaultPicResult.getResult().get(0).getUrl();
                ((BaseActivity) RegisterUserInforActivity.this).imageFilePath = url;
                ((BaseApplication) RegisterUserInforActivity.this.getApplication()).getAppComponent().imageLoader().loadImage(RegisterUserInforActivity.this, GlideImageConfig.builder().url(url).imageView((ImageView) RegisterUserInforActivity.this.findViewById(R.id.user_avatar)).transformation(new GlideCircleTransform(RegisterUserInforActivity.this)).build());
                RegisterUserInforActivity.this.updateAvatar2(url);
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_USER_DETAIL, this.user);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        if (this.isNeedGuide) {
            setTitle(R.string.Final_step);
            setTitleRightButton(R.string.Done, this);
            setTitleLeftTextButton(R.string.Logout, this);
            EditText editText = (EditText) findViewById(R.id.input_nick);
            this.nickEditText = editText;
            editText.setText(this.user.getNick());
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(this.user.getAvatar()).imageView((ImageView) findViewById(R.id.user_avatar)).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
            if (this.user.getGender() == 1) {
                this.genderSelect = 1;
            } else {
                this.genderSelect = 2;
            }
            setGenderViewState();
        } else {
            setTitle(R.string.Final_step);
            setTitleRightButton(R.string.Done, this);
            setTitleLeftTextButton(R.string.Logout, this);
            setNoTitleLeftButton();
            this.nickEditText = (EditText) findViewById(R.id.input_nick);
            ((TextView) findViewById(R.id.user_avatar_prompt)).setText(getString(R.string.Avatar) + ChineseToPinyinResource.Field.LEFT_BRACKET + getString(R.string.Optional) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
            setGenderViewState();
        }
        ((TextView) findViewById(R.id.birth_selectable)).setText(getString(R.string.Birthday) + ChineseToPinyinResource.Field.LEFT_BRACKET + getString(R.string.Optional) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
        findViewById(R.id.user_gender_man).setOnClickListener(this);
        findViewById(R.id.user_gender_woman).setOnClickListener(this);
        findViewById(R.id.user_avatar).setOnClickListener(this);
        findViewById(R.id.user_birth_view).setOnClickListener(this);
        CommonUtils.addSysIntMap(this, Constants.PETKIT_BIRTHDAY_FLAG + UserInforUtils.getUser().getId(), 1);
    }

    private void initViews() {
        this.nickEditText = (EditText) findViewById(R.id.input_nick);
        this.userAvatar = (ImageView) findViewById(R.id.user_avatar);
        this.birthDayTextView = (TextView) findViewById(R.id.user_birthday);
        this.tvSex = (TextView) findViewById(R.id.tv_sex);
        findViewById(R.id.user_gender_man).setOnClickListener(this);
        findViewById(R.id.user_gender_woman).setOnClickListener(this);
        findViewById(R.id.user_avatar).setOnClickListener(this);
        findViewById(R.id.user_birth_view).setOnClickListener(this);
        findViewById(R.id.ll_sex).setOnClickListener(this);
        findViewById(R.id.title_right_btn).setOnClickListener(this);
        findViewById(R.id.menu_1).setOnClickListener(this);
        findViewById(R.id.menu_2).setOnClickListener(this);
        findViewById(R.id.menu_cancel).setOnClickListener(this);
        findViewById(R.id.title_left_text).setOnClickListener(this);
        findViewById(R.id.user_birthday_ok).setOnClickListener(this);
        findViewById(R.id.user_birthday_cancel).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.ll_sex) {
            startActivity(ChooseUserGenderActivity.newIntent(this, ""));
            return;
        }
        if (view.getId() == R.id.title_right_btn) {
            String string = this.nickEditText.getEditableText().toString();
            if (isEmpty(string)) {
                showLongToast(R.string.Hint_input_nick);
                return;
            } else {
                updateUserProps(string);
                return;
            }
        }
        if (view.getId() == R.id.user_gender_man) {
            this.genderSelect = 1;
            setGenderViewState();
            return;
        }
        if (view.getId() == R.id.user_gender_woman) {
            this.genderSelect = 2;
            setGenderViewState();
            return;
        }
        if (view.getId() == R.id.user_avatar) {
            setCrop(true);
            new HeadImageWindow(this, this.defaultPicResult, new HeadImageWindow.HeadClickListener() { // from class: com.petkit.android.activities.registe.RegisterUserInforActivity.2
                @Override // com.petkit.android.activities.personal.widget.HeadImageWindow.HeadClickListener
                public void onCancelClick() {
                }

                @Override // com.petkit.android.activities.personal.widget.HeadImageWindow.HeadClickListener
                public void onClickItem(String str) {
                    if (!TextUtils.isEmpty(str)) {
                        ((BaseActivity) RegisterUserInforActivity.this).imageFilePath = str;
                        ((BaseApplication) RegisterUserInforActivity.this.getApplication()).getAppComponent().imageLoader().loadImage(RegisterUserInforActivity.this, GlideImageConfig.builder().imageView((ImageView) RegisterUserInforActivity.this.findViewById(R.id.user_avatar)).url(TextUtils.isEmpty(str) ? RegisterUserInforActivity.this.user.getAvatar() : str).transformation(new GlideCircleTransform(RegisterUserInforActivity.this)).build());
                        RegisterUserInforActivity.this.updateAvatar2(str);
                        return;
                    }
                    RegisterUserInforActivity.this.showCameraAndAblumMenu();
                }
            }).show(getWindow().getDecorView());
            return;
        }
        if (view.getId() == R.id.user_birth_view) {
            hideSoftInput();
            showBirthDayPopMenu();
            return;
        }
        if (view.getId() == R.id.menu_1) {
            this.mPopupWindow.dismiss();
            getPhotoFromCamera();
            return;
        }
        if (view.getId() == R.id.menu_2) {
            this.mPopupWindow.dismiss();
            getPhotoFromAlbum();
            return;
        }
        if (view.getId() == R.id.menu_cancel) {
            this.mPopupWindow.dismiss();
            return;
        }
        if (view.getId() == R.id.title_left_text) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_CLOSE_MAINACTIVITY));
            CommonUtils.addSysMap(this, Consts.SHARED_SESSION_ID, "");
            IMChatController.disconnect();
            startActivity(new Intent(this, (Class<?>) WelcomeActivity.class));
            finish();
            return;
        }
        if (view.getId() == R.id.user_birthday_ok) {
            setBirth();
            this.birthDayPopMenu.dismiss();
        } else if (view.getId() == R.id.user_birthday_cancel) {
            this.birthDayPopMenu.dismiss();
        }
    }

    private void setBirth() {
        this.birth = String.format("%04d-%02d-%02d", Integer.valueOf(this.mYear), Integer.valueOf(this.mMonth + 1), Integer.valueOf(this.mDay));
        ((TextView) findViewById(R.id.user_birthday)).setText(this.birth);
        this.birth = this.birth.replaceAll("-", "");
    }

    private void showBirthDayPopMenu() {
        View viewInflate = getLayoutInflater().inflate(R.layout.user_birthday_popupwindow, (ViewGroup) null);
        viewInflate.findViewById(R.id.user_birthday_cancel).setOnClickListener(this);
        viewInflate.findViewById(R.id.user_birthday_ok).setOnClickListener(this);
        DatePicker datePicker = (DatePicker) viewInflate.findViewById(R.id.user_birth_date);
        Calendar calendar = Calendar.getInstance();
        if (this.mYear < 1) {
            this.mYear = calendar.get(1);
            this.mMonth = calendar.get(2);
            this.mDay = calendar.get(5);
        }
        datePicker.setMaxDate(System.currentTimeMillis());
        datePicker.init(this.mYear, this.mMonth, this.mDay, new DatePicker.OnDateChangedListener() { // from class: com.petkit.android.activities.registe.RegisterUserInforActivity.3
            @Override // android.widget.DatePicker.OnDateChangedListener
            public void onDateChanged(DatePicker datePicker2, int i, int i2, int i3) {
                RegisterUserInforActivity.this.mYear = i;
                RegisterUserInforActivity.this.mMonth = i2;
                RegisterUserInforActivity.this.mDay = i3;
            }
        });
        BasePetkitWindow basePetkitWindow = new BasePetkitWindow((Context) this, viewInflate, false);
        this.birthDayPopMenu = basePetkitWindow;
        basePetkitWindow.setBackgroundDrawable(new BitmapDrawable());
        this.birthDayPopMenu.setOutsideTouchable(true);
        this.birthDayPopMenu.setFocusable(true);
        this.birthDayPopMenu.showAtLocation(this.contentView, 80, 0, 0);
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void uploadHeadPic(String str) {
        this.imageFilePath = str;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(this.imageFilePath, "");
        new UploadImagesUtils(UploadImagesUtils.NS_UAVATAR, linkedHashMap, new UploadImagesUtils.IUploadImagesListener() { // from class: com.petkit.android.activities.registe.RegisterUserInforActivity.4
            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageSuccess(LinkedHashMap<String, String> linkedHashMap2) {
                String str2 = linkedHashMap2.get(((BaseActivity) RegisterUserInforActivity.this).imageFilePath);
                PetkitLog.d("url = " + str2);
                RegisterUserInforActivity.this.updateAvatar2(str2);
            }

            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageFailed() {
                LoadDialog.dismissDialog();
                RegisterUserInforActivity.this.showShortToast(R.string.Publish_post_failed);
            }
        }, 2).start();
        showLoadDialog();
    }

    private void setGenderViewState() {
        TextView textView = (TextView) findViewById(R.id.user_gender_man);
        TextView textView2 = (TextView) findViewById(R.id.user_gender_woman);
        if (this.genderSelect == 1) {
            textView.setBackgroundResource(R.drawable.select_sex_blue_bg);
            textView.setTextColor(CommonUtils.getColorById(R.color.login_new_blue));
            textView2.setBackgroundResource(R.drawable.select_sex_gray_bg);
            textView2.setTextColor(CommonUtils.getColorById(R.color.home_p3_line_gray));
            return;
        }
        textView.setBackgroundResource(R.drawable.select_sex_gray_bg);
        textView.setTextColor(CommonUtils.getColorById(R.color.home_p3_line_gray));
        textView2.setBackgroundResource(R.drawable.select_sex_blue_bg);
        textView2.setTextColor(CommonUtils.getColorById(R.color.login_new_blue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageView() {
        ImageView imageView = (ImageView) findViewById(R.id.user_avatar);
        User user = this.user;
        if (user == null || user.getAvatar() == null || this.imageFilePath != null) {
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(this.imageFilePath).imageView(imageView).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
        }
    }

    private void updateUserProps(final String str) {
        String str2;
        boolean z = true;
        String str3 = this.oldNick;
        if (str3 != null && str.equals(str3)) {
            str2 = this.birth == null ? String.format("{\"gender\":%d}", Integer.valueOf(this.genderSelect)) : String.format("{\"gender\":%d,\"birth\":\"%s\"}", Integer.valueOf(this.genderSelect), this.birth);
        } else if (this.birth == null) {
            str2 = String.format("{\"nick\":\"%s\",\"gender\":%d}", str, Integer.valueOf(this.genderSelect));
        } else {
            str2 = String.format("{\"nick\":\"%s\",\"gender\":%d,\"birth\":\"%s\"}", str, Integer.valueOf(this.genderSelect), this.birth);
        }
        HashMap map = new HashMap();
        map.put("kv", str2);
        post(ApiTools.SAMPLE_API_USER_UPDATEPROPS, map, new AsyncHttpRespHandler(this, z) { // from class: com.petkit.android.activities.registe.RegisterUserInforActivity.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                UserRsp userRsp = (UserRsp) this.gson.fromJson(this.responseResult, UserRsp.class);
                if (userRsp.getError() == null) {
                    LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                    currentLoginResult.getUser().setNick(str);
                    currentLoginResult.getUser().setGender(RegisterUserInforActivity.this.genderSelect);
                    currentLoginResult.getUser().setBirthLabel(userRsp.getResult().getBirthLabel());
                    UserInforUtils.updateLoginResult(currentLoginResult);
                    Intent intent = new Intent(Constants.BROADCAST_MSG_UPDATE_USER);
                    intent.putExtra(Constants.JID_TYPE_USER, currentLoginResult.getUser());
                    LocalBroadcastManager.getInstance(RegisterUserInforActivity.this).sendBroadcast(intent);
                    if (RegisterUserInforActivity.this.isNeedGuide) {
                        RegisterUserInforActivity.this.finish();
                        RegisterUserInforActivity.this.overridePendingTransition(R.anim.slide_none, R.anim.slide_out_to_top);
                        return;
                    }
                    Intent intent2 = new Intent();
                    intent2.putExtra(Constants.EXTRA_TYPE, false);
                    RegisterUserInforActivity.this.setResult(-1, intent2);
                    RegisterUserInforActivity.this.finish();
                    RegisterUserInforActivity registerUserInforActivity = RegisterUserInforActivity.this;
                    int i2 = R.anim.slide_none;
                    registerUserInforActivity.overridePendingTransition(i2, i2);
                    return;
                }
                RegisterUserInforActivity.this.showLongToast(userRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAvatar2(final String str) {
        HashMap map = new HashMap();
        map.put(Consts.PET_AVATAR, str);
        post(ApiTools.SAMPLE_API_USER_UPDATEAVATAR2, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.registe.RegisterUserInforActivity.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                RegisterUserInforActivity.this.dismissLoadDialog();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                UpdateUserAvatarRsp updateUserAvatarRsp = (UpdateUserAvatarRsp) this.gson.fromJson(this.responseResult, UpdateUserAvatarRsp.class);
                if (updateUserAvatarRsp.getError() == null) {
                    RegisterUserInforActivity.this.setImageView();
                    LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                    currentLoginResult.getUser().setAvatar(str);
                    UserInforUtils.updateLoginResult(currentLoginResult);
                    Intent intent = new Intent();
                    intent.setAction(Constants.BROADCAST_MSG_UPDATE_USER_AVATAR);
                    intent.putExtra("infor", str);
                    LocalBroadcastManager.getInstance(RegisterUserInforActivity.this).sendBroadcast(intent);
                    return;
                }
                RegisterUserInforActivity.this.showLongToast(updateUserAvatarRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        });
    }

    @Subscriber
    public void updateGender(UpdateGender updateGender) {
        TextView textView = (TextView) findViewById(R.id.tv_sex);
        User user = UserInforUtils.getUser();
        if (user.getGender() == 1) {
            textView.setText(R.string.Male);
            return;
        }
        if (user.getGender() == 2) {
            textView.setText(R.string.Female);
            return;
        }
        if (user.getGender() == 3) {
            String stringSF = DataHelper.getStringSF(this, Constants.USER_SEX_CUSTOM + UserInforUtils.getUser().getId());
            if (TextUtils.isEmpty(stringSF)) {
                return;
            }
            textView.setText(stringSF);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

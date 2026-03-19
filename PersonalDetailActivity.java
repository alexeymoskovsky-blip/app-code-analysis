package com.petkit.android.activities.personal;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.common.InforModifyActivity;
import com.petkit.android.activities.permission.mode.PermissionBean;
import com.petkit.android.activities.personal.mode.DefaultPicResult;
import com.petkit.android.activities.personal.mode.StringRsp;
import com.petkit.android.activities.personal.mode.UpdateGender;
import com.petkit.android.activities.personal.widget.HeadImageWindow;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.UserRsp;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.User;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UploadImagesUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes4.dex */
public class PersonalDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_FOR_ADDRESS = 275;
    private static final int REQUEST_CODE_FOR_USERBIRTH = 276;
    private String custom = "";
    private DefaultPicResult defaultPicResult;
    private boolean isClickCamera;
    private boolean isClickGender;
    private BroadcastReceiver mBroadcastReceiver;
    private AlertDialog mDialog;
    private User user;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.isNeedPhotoController = true;
        super.onCreate(bundle);
        if (bundle == null) {
            this.user = (User) getIntent().getSerializableExtra(Constants.EXTRA_USER_DETAIL);
        } else {
            this.user = (User) bundle.getSerializable(Constants.EXTRA_USER_DETAIL);
        }
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_personal_detail);
        registerBroadcastReceiver();
        if (this.user.getGender() == 3) {
            String stringSF = DataHelper.getStringSF(this, Constants.USER_SEX_CUSTOM + UserInforUtils.getUser().getId());
            if (!TextUtils.isEmpty(stringSF)) {
                ((TextView) findViewById(R.id.personal_gender)).setText(stringSF);
            }
        }
        getCustomGender();
        getDefaultPic();
    }

    private void getCustomGender() {
        post(ApiTools.SAMPLE_API_GET_CUSTOM_GENDER, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                StringRsp stringRsp = (StringRsp) this.gson.fromJson(this.responseResult, StringRsp.class);
                PersonalDetailActivity.this.custom = stringRsp.getResult();
                PetkitLog.d("custom name:" + PersonalDetailActivity.this.custom);
                DataHelper.setStringSF(PersonalDetailActivity.this, Constants.USER_SEX_CUSTOM + UserInforUtils.getUser().getId(), PersonalDetailActivity.this.custom);
                if (PersonalDetailActivity.this.user.getGender() == 3) {
                    ((TextView) PersonalDetailActivity.this.findViewById(R.id.personal_gender)).setText(stringRsp.getResult());
                }
            }
        });
    }

    private void getDefaultPic() {
        post(ApiTools.SAMPLE_API_GET_USER_DEFAULT_PIC, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                PersonalDetailActivity.this.defaultPicResult = (DefaultPicResult) this.gson.fromJson(this.responseResult, DefaultPicResult.class);
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_USER_DETAIL, this.user);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Title_user_profile);
        setTitleLineVisibility(8);
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.user.getAvatar()).imageView((ImageView) findViewById(R.id.personal_avatar)).errorPic(this.user.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideCircleTransform(this)).build());
        findViewById(R.id.personal_avatar_view).setOnClickListener(this);
        ((TextView) findViewById(R.id.personal_nick)).setText(this.user.getNick());
        findViewById(R.id.personal_nick_view).setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.personal_gender);
        if (this.user.getGender() == 1) {
            textView.setText(R.string.Male);
        } else if (this.user.getGender() == 2) {
            textView.setText(R.string.Female);
        } else {
            textView.setText("");
        }
        findViewById(R.id.personal_gender_view).setOnClickListener(this);
        ((TextView) findViewById(R.id.personal_birth)).setText(DateUtil.getDateFormatShortString(String.valueOf(this.user.getBirth())));
        findViewById(R.id.personal_birth_view).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.personal_avatar_view) {
            setCrop(true);
            this.isClickCamera = true;
            this.isClickGender = false;
            new HeadImageWindow(this, this.defaultPicResult, new HeadImageWindow.HeadClickListener() { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.3
                @Override // com.petkit.android.activities.personal.widget.HeadImageWindow.HeadClickListener
                public void onCancelClick() {
                }

                @Override // com.petkit.android.activities.personal.widget.HeadImageWindow.HeadClickListener
                public void onClickItem(String str) {
                    if (!TextUtils.isEmpty(str)) {
                        ((BaseActivity) PersonalDetailActivity.this).imageFilePath = str;
                        PersonalDetailActivity.this.updateAvatar2(str);
                    } else {
                        PersonalDetailActivity.this.showCameraAndAblumMenu();
                    }
                }
            }).show(getWindow().getDecorView());
            return;
        }
        if (id == R.id.personal_nick_view) {
            Bundle bundle = new Bundle();
            bundle.putInt(InforModifyActivity.MODIFY_TYPE, Constants.INFO_UPDATE_TYPE_USER_NAME);
            bundle.putString(InforModifyActivity.BASE_DATA, this.user.getNick());
            startActivityWithData(InforModifyActivity.class, bundle, false);
            return;
        }
        if (id == R.id.personal_gender_view) {
            this.isClickCamera = false;
            this.isClickGender = true;
            startActivity(ChooseUserGenderActivity.newIntent(this, this.custom));
            return;
        }
        if (id == R.id.personal_birth_view) {
            Intent intent = new Intent(this, (Class<?>) RegisterBirthdayActivity.class);
            intent.putExtra(Constants.EXTRA_USER_DETAIL, this.user);
            intent.putExtra(Constants.EXTRA_BIRTHDAY, this.user.getBirth());
            intent.putExtra(Constants.EXTRA_INFO_BOOLEAN, true);
            startActivityForResult(intent, REQUEST_CODE_FOR_USERBIRTH);
            return;
        }
        if (id == R.id.menu_1) {
            this.mPopupWindow.dismiss();
            if (this.isClickCamera) {
                getPhotoFromCamera();
                return;
            } else {
                if (!this.isClickGender || 1 == this.user.getGender()) {
                    return;
                }
                updateUserGender(1);
                return;
            }
        }
        if (id == R.id.menu_2) {
            this.mPopupWindow.dismiss();
            if (this.isClickCamera) {
                getPhotoFromAlbum();
                return;
            } else {
                if (!this.isClickGender || 2 == this.user.getGender()) {
                    return;
                }
                updateUserGender(2);
                return;
            }
        }
        if (id == R.id.menu_cancel) {
            this.mPopupWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == REQUEST_CODE_FOR_USERBIRTH) {
            updateUserBirthDay(intent.getStringExtra(Constants.EXTRA_BIRTHDAY));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void uploadHeadPic(String str) {
        this.imageFilePath = str;
        setImageView();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(this.imageFilePath, "");
        new UploadImagesUtils(UploadImagesUtils.NS_UAVATAR, linkedHashMap, new UploadImagesUtils.IUploadImagesListener() { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.4
            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageSuccess(LinkedHashMap<String, String> linkedHashMap2) {
                String str2 = linkedHashMap2.get(((BaseActivity) PersonalDetailActivity.this).imageFilePath);
                PetkitLog.d("url = " + str2);
                PersonalDetailActivity.this.updateAvatar2(str2);
            }

            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageFailed() {
                LoadDialog.dismissDialog();
                PersonalDetailActivity.this.showShortToast(R.string.Publish_post_failed);
            }
        }, 2).start();
        showLoadDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAvatar2(final String str) {
        HashMap map = new HashMap();
        map.put(Consts.PET_AVATAR, str);
        PetkitLog.d("url = " + str);
        WebModelRepository.getInstance().updateAvatar(this, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.5
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str2) {
                PersonalDetailActivity.this.dismissLoadDialog();
                PersonalDetailActivity.this.setImageView();
                LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                currentLoginResult.getUser().setAvatar(str);
                UserInforUtils.updateLoginResult(currentLoginResult);
                Intent intent = new Intent();
                intent.setAction(Constants.BROADCAST_MSG_UPDATE_USER_AVATAR);
                intent.putExtra("infor", str);
                LocalBroadcastManager.getInstance(PersonalDetailActivity.this).sendBroadcast(intent);
                PersonalDetailActivity.this.setImageView();
                PersonalDetailActivity personalDetailActivity = PersonalDetailActivity.this;
                PetkitToast.showTopToast(personalDetailActivity, personalDetailActivity.getResources().getString(R.string.Save_success_prompt), R.drawable.top_toast_success_icon, 0);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PersonalDetailActivity.this.dismissLoadDialog();
                PersonalDetailActivity.this.showLongToast(errorInfor.getMsg(), R.drawable.toast_failed);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageView() {
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.imageFilePath).imageView((ImageView) findViewById(R.id.personal_avatar)).errorPic(this.user.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideCircleTransform(this)).build());
    }

    private void updateUserGender(final int i) {
        HashMap map = new HashMap();
        map.put("kv", String.format("{\"gender\":%d}", Integer.valueOf(i)));
        post(ApiTools.SAMPLE_API_USER_UPDATEPROPS, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                UserRsp userRsp = (UserRsp) this.gson.fromJson(this.responseResult, UserRsp.class);
                if (userRsp.getError() == null) {
                    PersonalDetailActivity.this.user.setGender(i);
                    PersonalDetailActivity.this.user.setBirthLabel(userRsp.getResult().getBirthLabel());
                    ((TextView) PersonalDetailActivity.this.findViewById(R.id.personal_gender)).setText(PersonalDetailActivity.this.user.getGender() == 1 ? R.string.Male : R.string.Female);
                    LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                    currentLoginResult.setUser(PersonalDetailActivity.this.user);
                    UserInforUtils.updateLoginResult(currentLoginResult);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.JID_TYPE_USER, PersonalDetailActivity.this.user);
                    intent.setAction(Constants.BROADCAST_MSG_UPDATE_USER);
                    LocalBroadcastManager.getInstance(PersonalDetailActivity.this).sendBroadcast(intent);
                    return;
                }
                PersonalDetailActivity.this.showLongToast(userRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        });
    }

    public void showChooseGender() {
        showPopMenu(getString(R.string.Male), getString(R.string.Female));
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void showPopMenu(String str, String... strArr) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
            }
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_pop_menu, (ViewGroup) null);
            this.mPopupWindow = new BasePetkitWindow((Context) this, viewInflate, false);
            if (!isEmpty(str)) {
                TextView textView = (TextView) viewInflate.findViewById(R.id.menu_desc);
                textView.setVisibility(0);
                textView.setText(str);
                viewInflate.findViewById(R.id.menu_desc_gap).setVisibility(0);
            }
            int i = 0;
            while (i < strArr.length) {
                Resources resources = getResources();
                StringBuilder sb = new StringBuilder();
                sb.append("menu_");
                int i2 = i + 1;
                sb.append(i2);
                Button button = (Button) viewInflate.findViewById(resources.getIdentifier(sb.toString(), "id", getPackageName()));
                button.setVisibility(0);
                button.setText(strArr[i]);
                button.setTextColor(getResources().getColor(R.color.bottom_menu_blue));
                if (i == 0) {
                    button.setBackground(getResources().getDrawable(R.drawable.solid_white_window_top_radius));
                } else if (i == strArr.length - 1) {
                    button.setBackground(getResources().getDrawable(R.drawable.solid_white_window_bottom_radius));
                }
                button.setOnClickListener(this);
                i = i2;
            }
            if (strArr.length < 3) {
                View viewFindViewById = viewInflate.findViewById(getResources().getIdentifier("menu_gap_" + strArr.length, "id", getPackageName()));
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(8);
                }
            }
            Button button2 = (Button) viewInflate.findViewById(R.id.menu_cancel);
            button2.setTextColor(getResources().getColor(R.color.bottom_menu_blue));
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((BaseActivity) PersonalDetailActivity.this).mPopupWindow.dismiss();
                }
            });
            this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            this.mPopupWindow.setOutsideTouchable(true);
            this.mPopupWindow.setFocusable(true);
            this.mPopupWindow.showAtLocation(this.contentView, 80, 0, 0);
        }
    }

    private void updateUserBirthDay(final String str) {
        HashMap map = new HashMap();
        final String strReplaceAll = str.replaceAll("-", "");
        map.put("kv", String.format("{\"birth\":%s}", strReplaceAll));
        post(ApiTools.SAMPLE_API_USER_UPDATEPROPS, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.8
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                UserRsp userRsp = (UserRsp) this.gson.fromJson(this.responseResult, UserRsp.class);
                if (userRsp.getError() == null) {
                    PersonalDetailActivity.this.user.setBirth(Integer.valueOf(strReplaceAll).intValue());
                    PersonalDetailActivity.this.user.setBirthLabel(userRsp.getResult().getBirthLabel());
                    ((TextView) PersonalDetailActivity.this.findViewById(R.id.personal_birth)).setText(DateUtil.getDateFormatShortString(str));
                    Intent intent = new Intent();
                    intent.putExtra(Constants.JID_TYPE_USER, PersonalDetailActivity.this.user);
                    intent.setAction(Constants.BROADCAST_MSG_UPDATE_USER);
                    LocalBroadcastManager.getInstance(PersonalDetailActivity.this).sendBroadcast(intent);
                    return;
                }
                PersonalDetailActivity.this.showLongToast(userRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        });
    }

    private void registerBroadcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.personal.PersonalDetailActivity.9
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_USER)) {
                    PersonalDetailActivity.this.user = (User) intent.getSerializableExtra(Constants.JID_TYPE_USER);
                    ((TextView) PersonalDetailActivity.this.findViewById(R.id.personal_nick)).setText(PersonalDetailActivity.this.user.getNick());
                    return;
                }
                if (intent.getAction().equals("com.petkit.android.exit")) {
                    PersonalDetailActivity.this.finish();
                    return;
                }
                if (Constants.BROADCAST_PERMISSION_FINISHED.equals(intent.getAction())) {
                    boolean booleanExtra = intent.getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
                    String stringExtra = intent.getStringExtra(Constants.EXTRA_PERMISSION_CLASSNAME);
                    ArrayList arrayList = (ArrayList) intent.getSerializableExtra(Constants.EXTRA_PERMISSION_CONTENT);
                    if (booleanExtra && stringExtra.contains(getClass().getSimpleName())) {
                        if (arrayList.contains(new PermissionBean("android.permission.CAMERA")) || arrayList.contains(new PermissionBean("android.permission.WRITE_EXTERNAL_STORAGE"))) {
                            PersonalDetailActivity.this.showCameraAndAblumMenu();
                        }
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.petkit.android.exit");
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_USER);
        intentFilter.addAction(Constants.BROADCAST_PERMISSION_FINISHED);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    @Subscriber
    public void updateGender(UpdateGender updateGender) {
        User user = UserInforUtils.getUser();
        if (user.getGender() == 1) {
            ((TextView) findViewById(R.id.personal_gender)).setText(R.string.Male);
            return;
        }
        if (user.getGender() == 2) {
            ((TextView) findViewById(R.id.personal_gender)).setText(R.string.Female);
            return;
        }
        if (user.getGender() == 3) {
            String stringSF = DataHelper.getStringSF(this, Constants.USER_SEX_CUSTOM + UserInforUtils.getUser().getId());
            if (TextUtils.isEmpty(stringSF)) {
                return;
            }
            ((TextView) findViewById(R.id.personal_gender)).setText(stringSF);
        }
    }
}

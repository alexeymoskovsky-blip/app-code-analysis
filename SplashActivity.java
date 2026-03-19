package com.petkit.android.activities.registe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.im.iot.IotRefreshManager;
import com.petkit.android.activities.chat.emotion.EmojiconHandler;
import com.petkit.android.activities.chat.emotion.EmotionGroup;
import com.petkit.android.activities.common.AppUpdateCheckService;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.registe.mode.InItUMEvent;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.EmotionGroupListRsp;
import com.petkit.android.api.http.apiResponse.ImgUrlRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.SettingRsp;
import com.petkit.android.model.Account;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.ChatItem;
import com.petkit.android.model.ChatMsg;
import com.petkit.android.model.Pet;
import com.petkit.android.model.User;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PathUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes5.dex */
public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private static final int FILECHOOSER_RESULTCODE = 256;
    private static final int WAIT_SECONDS = 2000;
    private static final boolean enablePermissionCheck = false;
    private String finalUrl;
    String firmBasePayloadString;
    String groupId;
    private ImageView imageView;
    String inviteId;
    private boolean isMateInto;
    private BroadcastReceiver mBroadcastReceiver;
    private ImgUrlRsp.ImgUrl mImgUrl;
    private String mSystemLanguage;
    private Timer mTimer;
    private ValueCallback<Uri> mUploadMessage;
    String pageType;
    private String session_id;
    private String title;
    private TextView upgradePromptTextView;
    private String imagePath = FileUtils.getAppCacheImageDirPath() + "petkit_advertise.png";
    private int lastCountValue = -1;
    private String description = "";
    boolean isShowingPolicyPopView = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadEmotionsZip(String str, String str2) {
    }

    public static /* synthetic */ int access$906(SplashActivity splashActivity) {
        int i = splashActivity.lastCountValue - 1;
        splashActivity.lastCountValue = i;
        return i;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        getWindow().setFlags(1024, 1024);
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        handleFcmMessage();
        this.isMateInto = getIntent().getBooleanExtra(Constants.IS_MATE_INTO, false);
        addPetkitCardSort();
        registerBoradcastReceiver();
        IotRefreshManager.getInstance().setRefreshTimeInDisconnect(false);
        CommonUtils.removeSysMap(CommonUtils.getAppContext(), Consts.SP_NEW_IOT_INFO);
        Intent intent = getIntent();
        String scheme = intent.getScheme();
        Uri data = intent.getData();
        System.out.println("scheme:" + scheme);
        if (data != null) {
            this.pageType = data.getQueryParameter("pageType");
            this.inviteId = data.getQueryParameter("invitedId");
            this.groupId = data.getQueryParameter(Consts.PET_GROUP_ID);
        }
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.upgradePromptTextView = (TextView) findViewById(R.id.upgrade_prompt);
        this.imageView.setOnClickListener(this);
        int sysIntMap = CommonUtils.getSysIntMap(this, Consts.SHARED_CURRENT_APP_VERSION);
        if (sysIntMap < 4911) {
            clearOldNewMsgCount();
        }
        if (sysIntMap < 5048) {
            CommonUtils.addSysMap(DeviceCenterUtils.SP_PET_DATA_LIST, "");
        }
        if (sysIntMap < CommonUtils.getAppVersionCode(this)) {
            this.imageView.setVisibility(8);
            this.upgradePromptTextView.setVisibility(0);
            SpannableString spannableString = new SpannableString(getString(R.string.Hint_wait_for_upgrade) + "\n\n0%");
            spannableString.setSpan(new RelativeSizeSpan(1.6f), 0, getString(R.string.Hint_wait_for_upgrade).length(), 33);
            this.upgradePromptTextView.setText(spannableString);
            new UpgradeVersionTask().execute(new Void[0]);
            return;
        }
        this.upgradePromptTextView.setVisibility(8);
        User userCheckUserBirthday = UserInforUtils.checkUserBirthday(this);
        Pet petCheckPetBirthday = UserInforUtils.checkPetBirthday(this);
        if (userCheckUserBirthday != null || petCheckPetBirthday != null) {
            this.imageView.setVisibility(8);
            ((ViewStub) findViewById(R.id.birthday_stub)).inflate();
            View viewFindViewById = findViewById(R.id.birthday_view);
            View viewFindViewById2 = findViewById(R.id.birth_avatar_view);
            ImageView imageView = (ImageView) findViewById(R.id.birth_avatar_user);
            ImageView imageView2 = (ImageView) findViewById(R.id.birth_avatar_pet);
            TextView textView = (TextView) findViewById(R.id.birth_prompt);
            try {
                if (userCheckUserBirthday != null) {
                    viewFindViewById.setBackgroundResource(R.drawable.birth_user_bg);
                    viewFindViewById2.setBackgroundResource(R.drawable.birthday_avatar_user_bg);
                    ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(userCheckUserBirthday.getAvatar()).imageView(imageView).errorPic(userCheckUserBirthday.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideRoundTransform(this, (int) DeviceUtils.dpToPixel(this, 5.0f))).build());
                    imageView2.setVisibility(8);
                    textView.setText(getString(R.string.Birth_user_prompt_format, userCheckUserBirthday.getNick()));
                    textView.setTextColor(CommonUtils.getColorById(R.color.birth_text_color_user));
                } else if (petCheckPetBirthday.getBlocke() == 0) {
                    User user = UserInforUtils.getUser();
                    viewFindViewById.setBackgroundResource(R.drawable.birth_pet_bg);
                    viewFindViewById2.setBackgroundResource(R.drawable.birthday_avatar_pet_bg);
                    ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(petCheckPetBirthday.getAvatar()).imageView(imageView2).errorPic(petCheckPetBirthday.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
                    imageView.setVisibility(8);
                    textView.setText(getString(R.string.Birth_pet_prompt_format, getString(user.getGender() == 2 ? R.string.Pet_mother : R.string.Pet_farther)));
                    textView.setTextColor(CommonUtils.getColorById(R.color.birth_text_color_pet));
                }
            } catch (Exception e) {
                e.printStackTrace();
                PetkitLog.d(e.getMessage());
            }
            startNextStep(true, true, true);
            return;
        }
        if (!DataHelper.getBooleanSF(this, ShareConstants.WEB_DIALOG_PARAM_PRIVACY)) {
            startNextStep(true, true, true);
        } else {
            getImageUrl();
        }
    }

    private void handleFcmMessage() {
        if (getIntent().getExtras() != null) {
            for (String str : getIntent().getExtras().keySet()) {
                Object obj = getIntent().getExtras().get(str);
                if ("extra".equals(str)) {
                    this.firmBasePayloadString = (String) obj;
                }
                Log.d("SplashActivity", "FireBaseKey: " + str + " FireBaseValue: " + obj.toString());
            }
        }
    }

    private void checkEmotionGroups() {
        ArrayList arrayList;
        this.mSystemLanguage = CommonUtils.getSystemLanguage();
        String sysMap = CommonUtils.getSysMap(this, Consts.SHARED_EMOTION_GROUP_RESULT + this.mSystemLanguage);
        String sysMap2 = CommonUtils.getSysMap(this, Constants.PETKIT_DOWNLOAD_EMOTION_ZIP);
        if (!TextUtils.isEmpty(sysMap) && TextUtils.isEmpty(sysMap2) && (arrayList = (ArrayList) new Gson().fromJson(sysMap, new TypeToken<List<EmotionGroup>>() { // from class: com.petkit.android.activities.registe.SplashActivity.1
        }.getType())) != null && arrayList.size() > 0) {
            downloadEmotionsZip(((EmotionGroup) arrayList.get(0)).getId(), ((EmotionGroup) arrayList.get(0)).getZipUrl());
        }
    }

    private void addPetkitCardSort() {
        if (TextUtils.isEmpty(CommonUtils.getSysMap(this, Constants.PETKIT_CARD_SORT))) {
            CommonUtils.addSysMap(this, Constants.PETKIT_CARD_SORT, "pet,fit,feeding,mate");
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        this.lastCountValue = -1;
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer = null;
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.imageView) {
            if (this.mImgUrl != null) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_LOAD_PATH, this.mImgUrl.getLink());
                bundle.putString(Constants.EXTRA_LOAD_TITLE, "");
                bundle.putBoolean(Constants.EXTRA_BACK_TO_MAIN, true);
                startActivityWithData(WebviewActivity.class, bundle, true);
                return;
            }
            return;
        }
        if (view.getId() == R.id.count_down) {
            startNextStep(false, false, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNextStep(boolean z, boolean z2, boolean z3) {
        checkSession();
        if (z) {
            Timer timer = new Timer();
            this.mTimer = timer;
            timer.schedule(new TimerTask() { // from class: com.petkit.android.activities.registe.SplashActivity.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    SplashActivity.this.runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.registe.SplashActivity.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!DataHelper.getBooleanSF(SplashActivity.this, ShareConstants.WEB_DIALOG_PARAM_PRIVACY)) {
                                SplashActivity.this.showPolicyPopView();
                                return;
                            }
                            SplashActivity splashActivity = SplashActivity.this;
                            if (!splashActivity.isEmpty(splashActivity.session_id)) {
                                SplashActivity.this.entryMain();
                            } else {
                                SplashActivity.this.entryLogin();
                            }
                        }
                    });
                }
            }, 2000L);
            if (z3) {
                try {
                    startService(new Intent(this, (Class<?>) AppUpdateCheckService.class));
                    return;
                } catch (Exception e) {
                    PetkitLog.d(e.getMessage());
                    LogcatStorageHelper.addLog("splashActivity error:" + e.getMessage());
                    return;
                }
            }
            return;
        }
        if (!DataHelper.getBooleanSF(this, ShareConstants.WEB_DIALOG_PARAM_PRIVACY)) {
            showPolicyPopView();
        } else if (!isEmpty(this.session_id)) {
            entryMain();
        } else {
            entryLogin();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void entryMain() {
        Intent intent = new Intent(this, (Class<?>) HomeActivity.class);
        intent.putExtra(Constants.EXTRA_REFRESH_SESSION, true);
        String str = this.pageType;
        if (str != null) {
            intent.putExtra(Constants.EXTRA_INVITE_DATA_PAGETYPE, Integer.valueOf(str));
        }
        String str2 = this.inviteId;
        if (str2 != null) {
            intent.putExtra(Constants.EXTRA_INVITE_DATA_INVITEID, str2);
        }
        String str3 = this.groupId;
        if (str3 != null) {
            intent.putExtra(Constants.EXTRA_INVITE_DATA_GROUPID, Integer.valueOf(str3));
        }
        intent.putExtra(Constants.IS_MATE_INTO, this.isMateInto);
        String str4 = this.firmBasePayloadString;
        if (str4 != null) {
            intent.putExtra("UMENG_EXTRA", str4);
            Log.d("SplashActivity", "firmBasePayload: " + this.firmBasePayloadString);
        }
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void entryLogin() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        }
        String stringSF = DataHelper.getStringSF(this, Consts.ACCOUNT_REGION_STRING);
        if (stringSF == null || stringSF.length() == 0) {
            startActivity(new Intent(this, (Class<?>) AccountSelectRegionActivity.class));
        } else {
            startActivity(new Intent(this, (Class<?>) NormalLoginActivity.class));
        }
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getImageUrl() {
        String region;
        Account account;
        checkSession();
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.session_id) && (account = UserInforUtils.getAccount()) != null) {
            region = account.getRegion();
        } else {
            region = "";
        }
        map.put(TtmlNode.TAG_REGION, region);
        PetkitLog.d("getImageUrl,region:" + region);
        LogcatStorageHelper.addLog("getImageUrl,region:" + region);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_GETURL, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.registe.SplashActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (SplashActivity.this.isFinishing()) {
                    return;
                }
                ImgUrlRsp imgUrlRsp = (ImgUrlRsp) this.gson.fromJson(this.responseResult, ImgUrlRsp.class);
                if (imgUrlRsp.getResult() == null) {
                    SplashActivity.this.startNextStep(false, true, true);
                    return;
                }
                if (SplashActivity.this.isEmpty(imgUrlRsp.getResult().getUrl())) {
                    SplashActivity.this.startNextStep(false, true, true);
                    SplashActivity.this.imageView.setVisibility(4);
                    SplashActivity.this.upgradePromptTextView.setVisibility(8);
                    return;
                }
                SplashActivity.this.mImgUrl = imgUrlRsp.getResult();
                ((BaseApplication) SplashActivity.this.getApplication()).getAppComponent().imageLoader().loadImage(SplashActivity.this, GlideImageConfig.builder().url(SplashActivity.this.mImgUrl.getUrl()).imageView(SplashActivity.this.imageView).build());
                SplashActivity splashActivity = SplashActivity.this;
                if (!splashActivity.isEmpty(splashActivity.mImgUrl.getLink())) {
                    SplashActivity.this.imageView.setOnClickListener(SplashActivity.this);
                }
                SplashActivity splashActivity2 = SplashActivity.this;
                splashActivity2.showCountdownInfo(splashActivity2.mImgUrl.getCountdown());
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                SplashActivity.this.startNextStep(false, true, true);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCountdownInfo(int i) {
        if (i > 0) {
            TextView textView = (TextView) findViewById(R.id.count_down);
            textView.setVisibility(0);
            textView.setOnClickListener(this);
            textView.setText(i + " " + getString(R.string.Skip));
            this.lastCountValue = i;
            startCountDown();
            return;
        }
        startNextStep(true, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCountDown() {
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.registe.SplashActivity.4
            @Override // java.lang.Runnable
            public void run() {
                if (SplashActivity.this.lastCountValue == -1) {
                    return;
                }
                if (SplashActivity.access$906(SplashActivity.this) <= 0) {
                    SplashActivity.this.startNextStep(false, false, false);
                    return;
                }
                ((TextView) SplashActivity.this.findViewById(R.id.count_down)).setText(SplashActivity.this.lastCountValue + " " + SplashActivity.this.getString(R.string.Skip));
                SplashActivity.this.startCountDown();
            }
        }, 1000L);
    }

    private void getEmotionGroups() {
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_IM_EMOTION_GROUPS, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.registe.SplashActivity.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                EmotionGroupListRsp emotionGroupListRsp = (EmotionGroupListRsp) this.gson.fromJson(this.responseResult, EmotionGroupListRsp.class);
                if (emotionGroupListRsp.getResult() == null || emotionGroupListRsp.getResult().size() <= 0) {
                    return;
                }
                CommonUtils.addSysMap(SplashActivity.this, Consts.SHARED_EMOTION_GROUP_RESULT + SplashActivity.this.mSystemLanguage, this.gson.toJson(emotionGroupListRsp.getResult()));
                ArrayList<EmotionGroup> result = emotionGroupListRsp.getResult();
                String id = result.get(0).getId();
                String zipUrl = result.get(0).getZipUrl();
                if (TextUtils.isEmpty(CommonUtils.getSysMap(SplashActivity.this, Constants.PETKIT_DOWNLOAD_EMOTION_ZIP))) {
                    SplashActivity.this.downloadEmotionsZip(id, zipUrl);
                } else {
                    EmojiconHandler.bigEmotionData = EmojiconHandler.initBigEmotionData();
                }
            }
        }, false);
    }

    private void startGetAppSetting() {
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_APP_SETTING, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.registe.SplashActivity.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                SettingRsp settingRsp = (SettingRsp) this.gson.fromJson(this.responseResult, SettingRsp.class);
                if (settingRsp.getResult() != null) {
                    CommonUtils.addSysMap(Consts.TIME_FORMAT, String.valueOf(settingRsp.getResult().getTimeFormat()));
                    DataHelper.setIntergerSF(SplashActivity.this, Consts.SHARE_SWITCH, settingRsp.getResult().getShare_switch());
                    if (TimeUtils.getInstance().is24HourSystem()) {
                        if (settingRsp.getResult().getTimeFormat() != 24) {
                            SplashActivity.this.saveTimeFormatSetting(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO);
                        }
                    } else if (settingRsp.getResult().getTimeFormat() != 12) {
                        SplashActivity.this.saveTimeFormatSetting("12");
                    }
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveTimeFormatSetting(final String str) {
        HashMap map = new HashMap();
        map.put("timeFormat", str);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_SAVE_TIMEFORMAT_SETTING, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.registe.SplashActivity.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                CommonUtils.addSysMap(Consts.TIME_FORMAT, str);
            }
        }, false);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.registe.SplashActivity.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_MSG_APP_NEW_VERSION)) {
                    if (SplashActivity.this.mTimer != null) {
                        SplashActivity.this.mTimer.cancel();
                        SplashActivity.this.mTimer = null;
                    }
                    SplashActivity.this.lastCountValue = -1;
                    return;
                }
                if (Constants.BROADCAST_MSG_UPGRADE_DIALOG_FINISH.equals(intent.getAction())) {
                    SplashActivity.this.startNextStep(false, false, false);
                } else if (Constants.BROADCAST_PERMISSION_FINISHED.equals(intent.getAction())) {
                    if (intent.getBooleanExtra(Constants.EXTRA_BOOLEAN, false)) {
                        SplashActivity.this.startNextStep(true, false, true);
                    } else {
                        SplashActivity.this.finish();
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_APP_NEW_VERSION);
        intentFilter.addAction(Constants.BROADCAST_MSG_UPGRADE_DIALOG_FINISH);
        intentFilter.addAction(Constants.BROADCAST_PERMISSION_FINISHED);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    public class getImageRunnable implements Runnable {
        public String imageUrl;

        public getImageRunnable(String str) {
            this.imageUrl = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            SplashActivity.this.getImage(this.imageUrl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getImage(String str) {
        try {
            HttpResponse httpResponseExecute = new DefaultHttpClient().execute((HttpUriRequest) new HttpGet(str));
            if (httpResponseExecute.getStatusLine().getStatusCode() == 200) {
                InputStream content = httpResponseExecute.getEntity().getContent();
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(content);
                content.close();
                PathUtil.saveMyBitmap(PathUtil.createnNewFile(this.imagePath), bitmapDecodeStream);
                CommonUtils.addSysMap(Constants.PETKIT_ADVERTISE_IMAGE_URL, str);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public class UpgradeVersionTask extends AsyncTask<Void, Long, Boolean> {
        public long totalCount;

        public UpgradeVersionTask() {
            this.totalCount = 0L;
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voidArr) {
            int sysIntMap = CommonUtils.getSysIntMap(SplashActivity.this, Consts.SHARED_CURRENT_APP_VERSION);
            if (sysIntMap < CommonUtils.getAppVersionCode(SplashActivity.this)) {
                SplashActivity splashActivity = SplashActivity.this;
                CommonUtils.addSysIntMap(splashActivity, Consts.SHARED_CURRENT_APP_VERSION, CommonUtils.getAppVersionCode(splashActivity));
                if (sysIntMap < 800) {
                    CommonUtils.addSysIntMap(SplashActivity.this, Constants.SHARED_GUIDE_COMPLETE, 0);
                    CommonUtils.addSysMap(SplashActivity.this, Consts.SHARED_SESSION_ID, "");
                }
                if (sysIntMap < 4500 && !UserInforUtils.checkHasLogout()) {
                    rebuildChatDatabaseForLog();
                }
            }
            return Boolean.TRUE;
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Long... lArr) {
            super.onProgressUpdate((Object[]) lArr);
            StringBuilder sb = new StringBuilder();
            sb.append(SplashActivity.this.getString(R.string.Hint_wait_for_upgrade));
            sb.append("\n\n");
            sb.append(lArr[0].longValue() <= this.totalCount ? (lArr[0].longValue() * 100) / this.totalCount : 100L);
            sb.append("%");
            SpannableString spannableString = new SpannableString(sb.toString());
            spannableString.setSpan(new RelativeSizeSpan(1.6f), 0, SplashActivity.this.getString(R.string.Hint_wait_for_upgrade).length(), 33);
            SplashActivity.this.upgradePromptTextView.setText(spannableString);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            SplashActivity.this.imageView.setVisibility(0);
            SplashActivity.this.upgradePromptTextView.setVisibility(8);
            if (!DataHelper.getBooleanSF(SplashActivity.this, ShareConstants.WEB_DIALOG_PARAM_PRIVACY)) {
                SplashActivity.this.startNextStep(true, true, true);
            } else {
                SplashActivity.this.getImageUrl();
                SplashActivity.this.startNextStep(true, true, true);
            }
        }

        public final void rebuildChatDatabaseForLog() {
            if (TextUtils.isEmpty(CommonUtils.getCurrentUserId())) {
                return;
            }
            List list = Select.from(ChatMsg.class).where(Condition.prop("idindex").eq(Long.valueOf(ChatUtils.convertDBQueryIndexByIds(CommonUtils.getCurrentUserId(), Constants.JID_SYSTEM_API_NOTIFY)))).orderBy("timeindex DESC").list();
            ChatItem chatItem = ChatUtils.getChatItem(CommonUtils.getCurrentUserId(), Constants.JID_SYSTEM_API_NOTIFY);
            ChatItem chatItemOrCreate = ChatUtils.getChatItemOrCreate(CommonUtils.getCurrentUserId(), Constants.JID_TYPE_SYSTEM_LOG);
            for (int i = 0; i < list.size(); i++) {
                ChatMsg chatMsg = (ChatMsg) list.get(i);
                if (ChatUtils.isSystemLogByChatMsg(chatMsg)) {
                    chatMsg.setIdindex(ChatUtils.convertDBQueryIndexByIds(CommonUtils.getCurrentUserId(), Constants.JID_TYPE_SYSTEM_LOG));
                    chatMsg.setChatTo(Constants.JID_TYPE_SYSTEM_LOG);
                    chatMsg.save();
                    if (i < chatItem.getNewMsgCount()) {
                        chatItemOrCreate.setNewMsgCount(chatItemOrCreate.getNewMsgCount() + 1);
                    }
                }
            }
            if (chatItemOrCreate.getNewMsgCount() > 0) {
                chatItem.setNewMsgCount(chatItem.getNewMsgCount() - chatItemOrCreate.getNewMsgCount());
                chatItem.save();
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setNewMsgCount(chatCenter.getNewMsgCount() - chatItemOrCreate.getNewMsgCount());
                chatCenter.save();
            }
            chatItemOrCreate.save();
        }
    }

    private void clearOldNewMsgCount() {
        ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
        List<ChatItem> allChatItems = ChatUtils.getAllChatItems();
        for (int i = 0; i < allChatItems.size(); i++) {
            ChatItem chatItem = allChatItems.get(i);
            if (chatItem.getNewMsgCount() > 0) {
                chatItem.setNewMsgCount(0);
                chatItem.save();
            }
        }
        chatCenter.setNewMsgCount(0);
        SugarRecord.save(chatCenter);
    }

    private void initWebView(WebView webView, String str) {
        webView.setVerticalScrollBarEnabled(false);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(-1);
        settings.setDomStorageEnabled(true);
        settings.setUserAgentString(settings.getUserAgentString() + Consts.USER_AGENT);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(getApplicationContext().getDir("database", 0).getPath());
        webView.setWebViewClient(new WebViewClient() { // from class: com.petkit.android.activities.registe.SplashActivity.9
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str2) {
                PetkitLog.d("shouldOverrideUrlLoading url: " + str2);
                if (str2 != null && str2.startsWith("bilibili://")) {
                    return true;
                }
                if (str2 != null && (str2.startsWith("tmall://") || str2.startsWith("taobao://"))) {
                    return false;
                }
                if (str2 != null && str2.startsWith("des://")) {
                    SplashActivity.this.description = URLDecoder.decode(str2.replaceAll("%(?![0-9a-fA-F]{2})", "%25").replace("des://", ""));
                    return true;
                }
                webView2.loadUrl(str2);
                return true;
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str2, Bitmap bitmap) {
                super.onPageStarted(webView2, str2, bitmap);
                PetkitLog.d("onPageStarted url: " + str2);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str2) {
                super.onPageFinished(webView2, str2);
                webView2.loadUrl("javascript:window.location.assign('des://'+ document.getElementsByName('description')[0].content)");
                SplashActivity.this.title = webView2.getTitle();
                SplashActivity.this.finalUrl = str2;
                PetkitLog.d("onPageFinished url: " + str2);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView2, int i, String str2, String str3) {
                webView2.stopLoading();
                webView2.clearView();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() { // from class: com.petkit.android.activities.registe.SplashActivity.10
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                SplashActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                SplashActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            public void openFileChooser(ValueCallback valueCallback, String str2) {
                SplashActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("*/*");
                SplashActivity.this.startActivityForResult(Intent.createChooser(intent, "File Browser"), 256);
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String str2, String str3) {
                SplashActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                SplashActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            @Override // android.webkit.WebChromeClient
            public void onExceededDatabaseQuota(String str2, String str3, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                super.onExceededDatabaseQuota(str2, str3, j, j2, j3, quotaUpdater);
                quotaUpdater.updateQuota(j2 * 2);
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView2, String str2) {
                super.onReceivedTitle(webView2, str2);
                if (SplashActivity.this.isEmpty(str2)) {
                    return;
                }
                SplashActivity.this.setTitle(str2);
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView2, int i) {
                super.onProgressChanged(webView2, i);
            }
        });
        String sysMap = CommonUtils.getSysMap(this, Consts.SHARED_SESSION_ID);
        if (str.contains("?")) {
            webView.loadUrl(str + "&userId=" + CommonUtils.getCurrentUserId() + "&X-Session=" + sysMap);
            return;
        }
        webView.loadUrl(str + "?userId=" + CommonUtils.getCurrentUserId() + "&X-Session=" + sysMap);
    }

    public void showPolicyPopView() {
        if (isFinishing() || this.isShowingPolicyPopView || isDestroyed()) {
            return;
        }
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_policy_content, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_check);
        TextView textView4 = (TextView) viewInflate.findViewById(R.id.tv_content);
        TextView textView5 = (TextView) viewInflate.findViewById(R.id.tv_user_service);
        textView4.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView3.getPaint().setFlags(8);
        textView5.getPaint().setFlags(8);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setFocusable(false);
        easyPopupWindow.setmShowAlpha(0.5f);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.registe.SplashActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPolicyPopView$0(view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.registe.SplashActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPolicyPopView$1(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.registe.SplashActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPolicyPopView$2(easyPopupWindow, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.registe.SplashActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPolicyPopView$3(easyPopupWindow, view);
            }
        });
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(-1);
        if (LayoutInflater.from(this).inflate(R.layout.activity_splash, (ViewGroup) null) == null || getWindow() == null || getWindow().getDecorView() == null) {
            return;
        }
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        this.isShowingPolicyPopView = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPolicyPopView$0(View view) {
        startActivity(WebviewActivity.newIntent(this, getString(R.string.Privacy_policy), ApiTools.getWebUrlByKey("policy")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPolicyPopView$1(View view) {
        startActivity(WebviewActivity.newIntent(this, getString(R.string.Settings_user_aggreement), ApiTools.getWebUrlByKey("user_agreement")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPolicyPopView$2(EasyPopupWindow easyPopupWindow, View view) {
        easyPopupWindow.dismiss();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPolicyPopView$3(EasyPopupWindow easyPopupWindow, View view) {
        easyPopupWindow.dismiss();
        EventBus.getDefault().post(new InItUMEvent());
        DataHelper.setBooleanSF(this, ShareConstants.WEB_DIALOG_PARAM_PRIVACY, Boolean.TRUE);
        if (!isEmpty(this.session_id)) {
            entryMain();
        } else {
            entryLogin();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void checkSession() {
        String sysMap = CommonUtils.getSysMap(this, Consts.SHARED_SESSION_ID);
        this.session_id = sysMap;
        if (!isEmpty(sysMap)) {
            LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
            if (currentLoginResult.getImservers() == null || currentLoginResult.getImservers().size() == 0) {
                this.session_id = null;
            }
        }
        if (isEmpty(this.session_id)) {
            return;
        }
        startGetAppSetting();
    }
}

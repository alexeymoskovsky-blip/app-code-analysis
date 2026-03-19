package com.petkit.android.activities.petkitBleDevice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.facebook.CallbackManager;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.community.widget.ShareWebPopMenu;
import com.petkit.android.activities.device.utils.ServiceUtils;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class AdWebViewActivity extends BaseActivity {
    public static final String ADD_WATER_TYPE = "ADD_WATER_TYPE";
    private static final int FILECHOOSER_RESULTCODE = 256;
    private CallbackManager callbackManager;
    private ImageButton ibBack;
    private ImageButton ibClose;
    private ImageButton ibForward;
    private ImageButton ibShare;
    private String loadPath;
    private ProgressBar loadingProgressBar;
    private ValueCallback<Uri> mUploadMessage;
    private String title;
    private int titleColor;
    private WebView webView;
    private String description = "";
    private String finalUrl = "";
    private boolean isBacktoMain = false;
    private long initTime = 0;
    private boolean isFinishLoad = false;

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.jess.arms.widget.InputMethodRelativeLayout.OnSizeChangedListener
    public void onSizeChange(boolean z, int i, int i2) {
    }

    public static Intent newIntent(Context context, String str, String str2) {
        Intent intent = new Intent(context, (Class<?>) AdWebViewActivity.class);
        intent.putExtra(Constants.EXTRA_LOAD_PATH, str2);
        intent.putExtra(Constants.EXTRA_LOAD_TITLE, str);
        return intent;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.loadPath = bundle.getString(Constants.EXTRA_LOAD_PATH);
            this.title = bundle.getString(Constants.EXTRA_LOAD_TITLE);
            this.titleColor = bundle.getInt(Constants.EXTRA_TITLE_COLOR);
            this.isBacktoMain = bundle.getBoolean(Constants.EXTRA_BACK_TO_MAIN, false);
        } else {
            this.loadPath = getIntent().getStringExtra(Constants.EXTRA_LOAD_PATH);
            this.title = getIntent().getStringExtra(Constants.EXTRA_LOAD_TITLE);
            this.titleColor = getIntent().getIntExtra(Constants.EXTRA_TITLE_COLOR, 0);
            this.isBacktoMain = getIntent().getBooleanExtra(Constants.EXTRA_BACK_TO_MAIN, false);
        }
        this.finalUrl = this.loadPath;
        this.callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_ad_webview);
        this.initTime = System.currentTimeMillis();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_TITLE_COLOR, this.titleColor);
        bundle.putString(Constants.EXTRA_LOAD_PATH, this.loadPath);
        bundle.putString(Constants.EXTRA_LOAD_TITLE, this.title);
        bundle.putBoolean(Constants.EXTRA_BACK_TO_MAIN, this.isBacktoMain);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        if (this.titleColor != 0) {
            setTitle(this.title, R.color.white);
            setTitleBackgroundColor(this.titleColor);
            setTitleLeftButton(R.drawable.btn_back_white);
        }
        if (this.loadPath == null) {
            finish();
        }
        this.loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        initWebView();
        String sysMap = CommonUtils.getSysMap(this, Consts.SHARED_SESSION_ID);
        if (!this.loadPath.contains("https") || !this.loadPath.startsWith("https")) {
            String strReplace = this.loadPath.replace("http", "https");
            this.loadPath = strReplace;
            this.loadPath = strReplace.replace("httpss", "https");
        }
        if (this.loadPath.contains("?")) {
            this.webView.loadUrl(this.loadPath + "&userId=" + CommonUtils.getCurrentUserId() + "&X-Session=" + sysMap);
        } else {
            this.webView.loadUrl(this.loadPath + "?userId=" + CommonUtils.getCurrentUserId() + "&X-Session=" + sysMap);
        }
        setTitleRightImageViewVisiblity(0);
        setTitleRightImageView(R.drawable.email_icon, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity.1
            public AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ServiceUtils.startToEmail2(AdWebViewActivity.this);
            }
        });
        this.webView.setDownloadListener(new MyWebViewDownLoadListener());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity$1 */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ServiceUtils.startToEmail2(AdWebViewActivity.this);
        }
    }

    private void initWebView() {
        setTitle(this.title);
        this.webView = (WebView) findViewById(R.id.webview);
        this.ibBack = (ImageButton) findViewById(R.id.ib_back);
        this.ibClose = (ImageButton) findViewById(R.id.ib_close);
        this.ibForward = (ImageButton) findViewById(R.id.ib_forward);
        this.ibShare = (ImageButton) findViewById(R.id.ib_share);
        this.webView.setVerticalScrollBarEnabled(false);
        this.ibBack.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity.2
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AdWebViewActivity.this.webView.canGoBack()) {
                    AdWebViewActivity.this.webView.goBack();
                }
            }
        });
        this.ibForward.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity.3
            public AnonymousClass3() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AdWebViewActivity.this.webView.canGoForward()) {
                    AdWebViewActivity.this.webView.goForward();
                }
            }
        });
        this.ibClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity.4
            public AnonymousClass4() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AdWebViewActivity.this.isFinishLoad) {
                    AdWebViewActivity.this.webView.loadUrl(AdWebViewActivity.this.finalUrl);
                } else {
                    AdWebViewActivity.this.webView.goBack();
                }
            }
        });
        this.ibShare.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity.5
            public AnonymousClass5() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CallbackManager callbackManager = AdWebViewActivity.this.callbackManager;
                AdWebViewActivity adWebViewActivity = AdWebViewActivity.this;
                new ShareWebPopMenu(callbackManager, adWebViewActivity, adWebViewActivity.title, AdWebViewActivity.this.finalUrl, null, AdWebViewActivity.this.description).show();
            }
        });
        WebSettings settings = this.webView.getSettings();
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
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity.6
            public AnonymousClass6() {
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                AdWebViewActivity.this.finalUrl = str;
            }
        });
        this.webView.setWebChromeClient(new WebChromeClient() { // from class: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity.7
            public AnonymousClass7() {
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                AdWebViewActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                AdWebViewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            public void openFileChooser(ValueCallback valueCallback, String str) {
                AdWebViewActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("*/*");
                AdWebViewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Browser"), 256);
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
                AdWebViewActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                AdWebViewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            @Override // android.webkit.WebChromeClient
            public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                super.onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
                quotaUpdater.updateQuota(j2 * 2);
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
                if (AdWebViewActivity.this.isEmpty(str)) {
                    return;
                }
                AdWebViewActivity.this.setTitle(str);
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    AdWebViewActivity.this.ibClose.setImageResource(R.drawable.blue_refresh_icon);
                    AdWebViewActivity.this.isFinishLoad = true;
                    AdWebViewActivity.this.loadingProgressBar.setVisibility(8);
                    if (AdWebViewActivity.this.webView.canGoForward()) {
                        AdWebViewActivity.this.ibForward.setImageResource(R.drawable.forward_clickable_icon);
                    } else {
                        AdWebViewActivity.this.ibForward.setImageResource(R.drawable.forward_unclickable_icon);
                    }
                    if (AdWebViewActivity.this.webView.canGoBack()) {
                        AdWebViewActivity.this.ibBack.setImageResource(R.drawable.back_clickable_icon);
                    } else {
                        AdWebViewActivity.this.ibBack.setImageResource(R.drawable.back_unclickable_icon);
                    }
                } else {
                    AdWebViewActivity.this.isFinishLoad = false;
                    AdWebViewActivity.this.ibClose.setImageResource(R.drawable.blue_close_icon);
                    if (AdWebViewActivity.this.loadingProgressBar.getVisibility() == 8) {
                        AdWebViewActivity.this.loadingProgressBar.setVisibility(0);
                    }
                    AdWebViewActivity.this.loadingProgressBar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        public AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (AdWebViewActivity.this.webView.canGoBack()) {
                AdWebViewActivity.this.webView.goBack();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity$3 */
    public class AnonymousClass3 implements View.OnClickListener {
        public AnonymousClass3() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (AdWebViewActivity.this.webView.canGoForward()) {
                AdWebViewActivity.this.webView.goForward();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity$4 */
    public class AnonymousClass4 implements View.OnClickListener {
        public AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (AdWebViewActivity.this.isFinishLoad) {
                AdWebViewActivity.this.webView.loadUrl(AdWebViewActivity.this.finalUrl);
            } else {
                AdWebViewActivity.this.webView.goBack();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity$5 */
    public class AnonymousClass5 implements View.OnClickListener {
        public AnonymousClass5() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CallbackManager callbackManager = AdWebViewActivity.this.callbackManager;
            AdWebViewActivity adWebViewActivity = AdWebViewActivity.this;
            new ShareWebPopMenu(callbackManager, adWebViewActivity, adWebViewActivity.title, AdWebViewActivity.this.finalUrl, null, AdWebViewActivity.this.description).show();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity$6 */
    public class AnonymousClass6 extends WebViewClient {
        public AnonymousClass6() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            AdWebViewActivity.this.finalUrl = str;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.AdWebViewActivity$7 */
    public class AnonymousClass7 extends WebChromeClient {
        public AnonymousClass7() {
        }

        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            AdWebViewActivity.this.mUploadMessage = valueCallback;
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("image/*");
            AdWebViewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
        }

        public void openFileChooser(ValueCallback valueCallback, String str) {
            AdWebViewActivity.this.mUploadMessage = valueCallback;
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("*/*");
            AdWebViewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Browser"), 256);
        }

        public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
            AdWebViewActivity.this.mUploadMessage = valueCallback;
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("image/*");
            AdWebViewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
        }

        @Override // android.webkit.WebChromeClient
        public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
            super.onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
            quotaUpdater.updateQuota(j2 * 2);
        }

        @Override // android.webkit.WebChromeClient
        public void onReceivedTitle(WebView webView, String str) {
            super.onReceivedTitle(webView, str);
            if (AdWebViewActivity.this.isEmpty(str)) {
                return;
            }
            AdWebViewActivity.this.setTitle(str);
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            if (i == 100) {
                AdWebViewActivity.this.ibClose.setImageResource(R.drawable.blue_refresh_icon);
                AdWebViewActivity.this.isFinishLoad = true;
                AdWebViewActivity.this.loadingProgressBar.setVisibility(8);
                if (AdWebViewActivity.this.webView.canGoForward()) {
                    AdWebViewActivity.this.ibForward.setImageResource(R.drawable.forward_clickable_icon);
                } else {
                    AdWebViewActivity.this.ibForward.setImageResource(R.drawable.forward_unclickable_icon);
                }
                if (AdWebViewActivity.this.webView.canGoBack()) {
                    AdWebViewActivity.this.ibBack.setImageResource(R.drawable.back_clickable_icon);
                } else {
                    AdWebViewActivity.this.ibBack.setImageResource(R.drawable.back_unclickable_icon);
                }
            } else {
                AdWebViewActivity.this.isFinishLoad = false;
                AdWebViewActivity.this.ibClose.setImageResource(R.drawable.blue_close_icon);
                if (AdWebViewActivity.this.loadingProgressBar.getVisibility() == 8) {
                    AdWebViewActivity.this.loadingProgressBar.setVisibility(0);
                }
                AdWebViewActivity.this.loadingProgressBar.setProgress(i);
            }
            super.onProgressChanged(webView, i);
        }
    }

    private void setLoadingIndictorState(boolean z) {
        this.loadingProgressBar.setVisibility(z ? 0 : 8);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            finish();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        new HashMap().put("durations", calcTime((int) ((System.currentTimeMillis() - this.initTime) / 1000)));
        ((LinearLayout) findViewById(R.id.view)).removeView(this.webView);
        this.webView.removeAllViews();
        this.webView.destroy();
    }

    public String calcTime(int i) {
        String str;
        int i2 = i / 60;
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        int i5 = i % 60;
        if (i3 > 0) {
            return i3 + "h";
        }
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        sb.append("");
        if (i4 > 0) {
            str = i4 + "min";
        } else {
            str = "";
        }
        sb.append(str);
        if (i5 > 0) {
            str2 = i5 + "s";
        }
        sb.append(str2);
        return sb.toString();
    }

    public class MyWebViewDownLoadListener implements DownloadListener {
        public MyWebViewDownLoadListener() {
        }

        public /* synthetic */ MyWebViewDownLoadListener(AdWebViewActivity adWebViewActivity, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            AdWebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        CallbackManager callbackManager = this.callbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        }
        if (i != 256 || this.mUploadMessage == null) {
            return;
        }
        this.mUploadMessage.onReceiveValue((intent == null || i2 != -1) ? null : intent.getData());
        this.mUploadMessage = null;
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void cancel(View view) {
        if (this.isBacktoMain) {
            startActivity(HomeActivity.class, true);
        } else {
            super.cancel(view);
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0 && this.isBacktoMain) {
            startActivity(HomeActivity.class, true);
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}

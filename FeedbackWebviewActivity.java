package com.petkit.android.activities.petkitBleDevice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.RequiresApi;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class FeedbackWebviewActivity extends BaseActivity {
    private static final int FILECHOOSER_RESULTCODE = 256;
    private String description = "";
    private String finalUrl = "";
    private boolean isBacktoMain = false;
    private String loadPath;
    private ProgressBar loadingProgressBar;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMsgs;
    private String postData;
    private String title;
    private int titleColor;
    private WebView webView;

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.jess.arms.widget.InputMethodRelativeLayout.OnSizeChangedListener
    public void onSizeChange(boolean z, int i, int i2) {
    }

    public static Intent newIntent(Context context, String str, String str2) {
        Intent intent = new Intent(context, (Class<?>) FeedbackWebviewActivity.class);
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
        setContentView(R.layout.activity_webview);
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
        this.loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        initWebView();
        CommonUtils.getSysMap(this, Consts.SHARED_SESSION_ID);
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        this.postData = "nickname=" + currentLoginResult.getUser().getNick() + "&avatar=" + currentLoginResult.getUser().getAvatar() + "&openid=" + currentLoginResult.getUser().getId();
        if (this.loadPath.contains("?")) {
            this.webView.postUrl(this.loadPath, this.postData.getBytes());
        } else {
            this.webView.postUrl(this.loadPath, this.postData.getBytes());
        }
        setTitleRightImageViewVisiblity(8);
        this.webView.setDownloadListener(new MyWebViewDownLoadListener());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            finish();
        }
    }

    private void initWebView() {
        setTitle(this.title);
        WebView webView = (WebView) findViewById(R.id.webview);
        this.webView = webView;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(2);
        settings.setDomStorageEnabled(true);
        settings.setUserAgentString(settings.getUserAgentString() + Consts.USER_AGENT);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(getApplicationContext().getDir("database", 0).getPath());
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.petkit.android.activities.petkitBleDevice.FeedbackWebviewActivity.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                super.shouldOverrideUrlLoading(webView2, str);
                PetkitLog.d("shouldOverrideUrlLoading:" + str);
                webView2.postUrl(str, FeedbackWebviewActivity.this.postData.getBytes());
                return true;
            }
        });
        this.webView.setWebChromeClient(new WebChromeClient() { // from class: com.petkit.android.activities.petkitBleDevice.FeedbackWebviewActivity.2
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                FeedbackWebviewActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                FeedbackWebviewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            public void openFileChooser(ValueCallback valueCallback, String str) {
                FeedbackWebviewActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("*/*");
                FeedbackWebviewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Browser"), 256);
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
                FeedbackWebviewActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                FeedbackWebviewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView2, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                FeedbackWebviewActivity.this.mUploadMsgs = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                FeedbackWebviewActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                super.onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
                quotaUpdater.updateQuota(j2 * 2);
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView2, String str) {
                super.onReceivedTitle(webView2, str);
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView2, int i) {
                if (i == 100) {
                    FeedbackWebviewActivity.this.loadingProgressBar.setVisibility(8);
                } else {
                    if (FeedbackWebviewActivity.this.loadingProgressBar.getVisibility() == 8) {
                        FeedbackWebviewActivity.this.loadingProgressBar.setVisibility(0);
                    }
                    FeedbackWebviewActivity.this.loadingProgressBar.setProgress(i);
                }
                super.onProgressChanged(webView2, i);
            }
        });
    }

    private void setLoadingIndictorState(boolean z) {
        this.loadingProgressBar.setVisibility(z ? 0 : 8);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ((LinearLayout) findViewById(R.id.view)).removeView(this.webView);
        this.webView.removeAllViews();
        this.webView.destroy();
    }

    public class MyWebViewDownLoadListener implements DownloadListener {
        public MyWebViewDownLoadListener() {
        }

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            FeedbackWebviewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    @RequiresApi(api = 21)
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 256) {
            if (this.mUploadMessage == null && this.mUploadMsgs == null) {
                return;
            }
            Uri data = (intent == null || i2 != -1) ? null : intent.getData();
            ValueCallback<Uri> valueCallback = this.mUploadMessage;
            if (valueCallback != null) {
                valueCallback.onReceiveValue(data);
                this.mUploadMessage = null;
            }
            ValueCallback<Uri[]> valueCallback2 = this.mUploadMsgs;
            if (valueCallback2 != null) {
                valueCallback2.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(i2, intent));
                this.mUploadMsgs = null;
            }
        }
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
    @SuppressLint({"RestrictedApi"})
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4) {
            return false;
        }
        if (this.webView.canGoBack()) {
            this.webView.goBack();
            return false;
        }
        finish();
        return false;
    }
}

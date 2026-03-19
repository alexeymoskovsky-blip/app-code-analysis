package com.petkit.android.activities.petkitBleDevice.w7h.widget;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hDataUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hUtils;
import com.petkit.android.activities.petkitBleDevice.widget.LoadingWindow;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import java.util.HashMap;

/* JADX INFO: loaded from: classes5.dex */
public class WebViewControllerActivity extends BaseActivity {
    private static final int FILE_CHOOSER_RESULT_CODE = 256;
    private Button btView;
    private long deviceId;
    private String loadPath;
    private ProgressBar loadingProgressBar;
    private LoadingWindow loadingWindow;
    private BroadcastReceiver mBroadcastReceiver;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageAboveL;
    private String title;
    private String viewType = "";
    private WebView webView;

    public interface WebViewType {
        public static final String ADD_WATER_TYPE = "ADD_WATER_TYPE";
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_CLICK_PATH, this.viewType);
        bundle.putString(Constants.EXTRA_LOAD_PATH, this.loadPath);
        bundle.putString(Constants.EXTRA_LOAD_TITLE, this.title);
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.deviceId);
    }

    public static Intent newIntent(Context context, String str, String str2, String str3, long j) {
        Intent intent = new Intent(context, (Class<?>) WebViewControllerActivity.class);
        intent.putExtra(Constants.EXTRA_LOAD_PATH, str2);
        intent.putExtra(Constants.EXTRA_CLICK_PATH, str3);
        intent.putExtra(Constants.EXTRA_LOAD_TITLE, str);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        return intent;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.viewType = bundle.getString(Constants.EXTRA_CLICK_PATH);
            this.loadPath = bundle.getString(Constants.EXTRA_LOAD_PATH);
            this.title = bundle.getString(Constants.EXTRA_LOAD_TITLE);
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID, 0L);
        } else {
            this.viewType = getIntent().getStringExtra(Constants.EXTRA_CLICK_PATH);
            this.loadPath = getIntent().getStringExtra(Constants.EXTRA_LOAD_PATH);
            this.title = getIntent().getStringExtra(Constants.EXTRA_LOAD_TITLE);
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
        }
        setContentView(R.layout.activity_control_webview);
        registerBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.btn_click && !TextUtils.isEmpty(this.viewType) && this.viewType.equals("ADD_WATER_TYPE")) {
            addWaterRequest();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        this.btView = (Button) findViewById(R.id.btn_click);
        this.loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.webView = (WebView) findViewById(R.id.web_view);
        this.btView.setOnClickListener(this);
        initWebView();
        initView();
    }

    private void initView() {
        setTitle(this.title);
        if (this.viewType.equals("ADD_WATER_TYPE")) {
            this.btView.setBackgroundResource(R.drawable.selector_bg_solid_blue_common);
            this.btView.setText(getString(R.string.Re_add_water));
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initWebView() {
        String sysMap = CommonUtils.getSysMap(this, Consts.SHARED_SESSION_ID);
        if (this.loadPath.contains("?")) {
            this.webView.loadUrl(this.loadPath + "&userId=" + CommonUtils.getCurrentUserId() + "&X-Session=" + sysMap);
        } else {
            this.webView.loadUrl(this.loadPath + "?userId=" + CommonUtils.getCurrentUserId() + "&X-Session=" + sysMap);
        }
        this.webView.setDownloadListener(new MyWebViewDownLoadListener());
        this.webView.setVerticalScrollBarEnabled(false);
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
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.WebViewControllerActivity.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                PetkitLog.d("shouldOverrideUrlLoading url: " + str);
                if (str != null && (str.startsWith("tbopen://") || str.startsWith("tmall://") || str.startsWith("taobao://"))) {
                    if (CommonUtils.checkPackage("com.taobao.taobao")) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse(str));
                        WebViewControllerActivity.this.startActivity(intent);
                        WebViewControllerActivity.this.finish();
                    } else {
                        PetkitToast.showToast(WebViewControllerActivity.this.getResources().getString(R.string.Not_installed_taobao));
                    }
                    return false;
                }
                if (str != null && str.startsWith("alipays://")) {
                    return false;
                }
                if (str != null && str.startsWith("bilibili://")) {
                    return true;
                }
                if (str != null && (str.startsWith("tmall://") || str.startsWith("taobao://"))) {
                    return false;
                }
                if (str != null && str.startsWith("des://")) {
                    return true;
                }
                webView.loadUrl(str);
                return true;
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                PetkitLog.d("onPageStarted url: " + str);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (!str.contains("alipay")) {
                    webView.loadUrl("javascript:window.location.assign('des://'+ document.getElementsByName('description')[0].content)");
                }
                WebViewControllerActivity.this.title = webView.getTitle();
                PetkitLog.d("onPageFinished url: " + str);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                WebViewControllerActivity.this.setLoadingIndictorState(false);
                webView.stopLoading();
                webView.clearView();
            }
        });
        this.webView.setWebChromeClient(new WebChromeClient() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.WebViewControllerActivity.2
            @Override // android.webkit.WebChromeClient
            public void onHideCustomView() {
            }

            @Override // android.webkit.WebChromeClient
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                WebViewControllerActivity.this.mUploadMessageAboveL = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                WebViewControllerActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                WebViewControllerActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                WebViewControllerActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            public void openFileChooser(ValueCallback valueCallback, String str) {
                WebViewControllerActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("*/*");
                WebViewControllerActivity.this.startActivityForResult(Intent.createChooser(intent, "File Browser"), 256);
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
                WebViewControllerActivity.this.mUploadMessage = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                WebViewControllerActivity.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 256);
            }

            @Override // android.webkit.WebChromeClient
            public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
                super.onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
                quotaUpdater.updateQuota(j2 * 2);
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    WebViewControllerActivity.this.loadingProgressBar.setVisibility(8);
                } else {
                    if (WebViewControllerActivity.this.loadingProgressBar.getVisibility() == 8) {
                        WebViewControllerActivity.this.loadingProgressBar.setVisibility(0);
                    }
                    WebViewControllerActivity.this.loadingProgressBar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
                return super.onJsAlert(webView, str, str2, jsResult);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
                return super.onJsConfirm(webView, str, str2, jsResult);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
                return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLoadingIndictorState(boolean z) {
        this.loadingProgressBar.setVisibility(z ? 0 : 8);
    }

    private void registerBroadcastReceiver() {
        if (this.mBroadcastReceiver != null) {
            return;
        }
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.WebViewControllerActivity.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == null) {
                    return;
                }
                if (intent.getAction().equals(W7hUtils.BROADCAST_W7H_EVENT_ADD_WATER)) {
                    long longExtra = intent.getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
                    if (longExtra == WebViewControllerActivity.this.deviceId) {
                        WebViewControllerActivity.this.hideLoading();
                        W7hRecord w7hRecordByDeviceId = W7hDataUtils.getInstance().getW7hRecordByDeviceId(longExtra);
                        w7hRecordByDeviceId.getState().setAddWaterState(0);
                        W7hDataUtils.getInstance().updateRecord(longExtra, w7hRecordByDeviceId);
                        WebViewControllerActivity.this.finish();
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals(W7hUtils.BROADCAST_W7H_EVENT_ADD_WATER_END) && intent.getLongExtra(Constants.EXTRA_DEVICE_ID, 0L) == WebViewControllerActivity.this.deviceId) {
                    WebViewControllerActivity.this.hideLoading();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(W7hUtils.BROADCAST_W7H_EVENT_ADD_WATER);
        intentFilter.addAction(W7hUtils.BROADCAST_W7H_EVENT_ADD_WATER_END);
        LocalBroadcastManager.getInstance(CommonUtils.getApplication()).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(CommonUtils.getApplication()).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void showLoading() {
        if (this.loadingWindow == null) {
            this.loadingWindow = new LoadingWindow(this);
        }
        this.loadingWindow.showLoading(getWindow().getDecorView());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLoading() {
        LoadingWindow loadingWindow = this.loadingWindow;
        if (loadingWindow != null) {
            loadingWindow.hideLoading();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LoadingWindow loadingWindow = this.loadingWindow;
        if (loadingWindow != null && loadingWindow.isShowing()) {
            this.loadingWindow.hideLoading();
        }
        unregisterBroadcastReceiver();
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

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            WebViewControllerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 256) {
            Uri data = (intent == null || i2 != -1) ? null : intent.getData();
            ValueCallback<Uri> valueCallback = this.mUploadMessage;
            if (valueCallback != null) {
                valueCallback.onReceiveValue(data);
                this.mUploadMessage = null;
                return;
            }
            ValueCallback<Uri[]> valueCallback2 = this.mUploadMessageAboveL;
            if (valueCallback2 != null) {
                valueCallback2.onReceiveValue(new Uri[]{data});
                this.mUploadMessageAboveL = null;
            }
        }
    }

    private void addWaterRequest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(this.deviceId));
        map.put("type", "start");
        map.put("kv", "{\"start_action\":2}");
        showLoading();
        WebModelRepository.getInstance().controlW7hDevice(this, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.WebViewControllerActivity.4
            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
            }
        });
    }
}

package com.petkit.android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.util.Log;
import androidx.multidex.MultiDexExtractor;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.MyActivityManger;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.AliOsstokenRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.QiniuOsstokenRsp;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.UpdateLogParam;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiyukf.unicorn.mediaselect.internal.loader.AlbumLoader;
import cz.msebera.android.httpclient.Header;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes6.dex */
@SuppressLint({"SimpleDateFormat"})
public class TXP2PLogcatStorageHelper {
    public static TXP2PLogcatStorageHelper INSTANCE = null;
    public static final int MAX_RETRY_TIMES = 1;
    public static String PATH_LOGCAT = null;
    public static final String tag = "LogcatStorageHelper";
    public AliOsstokenRsp.AliOsstokenResult.AliOssTokenData aliOssTokenData;
    public Context context;
    public OSSCredentialProvider credentialProvider;
    public int fileCount;
    public boolean httpSucced;
    public String logPostUrl;
    public int mPId;
    public Collection<File> mUploadingFiles;
    public OSSClient oss;
    public String qiNiuKey;
    public String qiNiuResult;
    public QiNiuResultRecord qiNiuResultRecord = null;
    public String qiNiuToken;
    public String qiNiuUrl;
    public int resultCount;
    public int retryTimes;
    public String zipfileString;

    public static /* synthetic */ int access$108(TXP2PLogcatStorageHelper tXP2PLogcatStorageHelper) {
        int i = tXP2PLogcatStorageHelper.retryTimes;
        tXP2PLogcatStorageHelper.retryTimes = i + 1;
        return i;
    }

    public void init(Context context, String str, String str2) {
        this.logPostUrl = str;
        this.qiNiuUrl = str2;
        this.context = context;
        PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + "p2p_logs/";
        File file = new File(PATH_LOGCAT);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public static TXP2PLogcatStorageHelper getInstance(Context context, String str, String str2) {
        if (INSTANCE == null) {
            synchronized (TXP2PLogcatStorageHelper.class) {
                try {
                    if (INSTANCE == null) {
                        INSTANCE = new TXP2PLogcatStorageHelper(context, str, str2);
                    }
                } finally {
                }
            }
        }
        return INSTANCE;
    }

    public TXP2PLogcatStorageHelper(Context context, String str, String str2) {
        init(context, str, str2);
        this.mPId = Process.myPid();
    }

    public void start() throws Throwable {
        checkUploadDebugFile();
    }

    public final void checkUploadDebugFile() throws Throwable {
        String[] list = new File(PATH_LOGCAT).list();
        if (list == null || list.length <= 0) {
            return;
        }
        uploadDebugFile(list);
    }

    public void initALiOssClient(AliOsstokenRsp.AliOsstokenResult.AliOssTokenData aliOssTokenData) {
        if (aliOssTokenData != null) {
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setConnectionTimeout(15000);
            clientConfiguration.setSocketTimeout(15000);
            clientConfiguration.setMaxConcurrentRequest(5);
            clientConfiguration.setMaxErrorRetry(2);
            OSSLog.enableLog();
            this.credentialProvider = new OSSStsTokenCredentialProvider(aliOssTokenData.getKeyId(), aliOssTokenData.getSecret(), aliOssTokenData.getToken());
            this.oss = new OSSClient(MyActivityManger.getInstance().getCurrentActivity(), aliOssTokenData.getEndPoint(), this.credentialProvider, clientConfiguration);
        }
    }

    public void getOsstoken() {
        if (this.retryTimes > 1) {
            processFailed();
            return;
        }
        HashMap map = new HashMap();
        map.put("type", "other");
        map.put("namespace", "applog");
        map.put(AlbumLoader.COLUMN_COUNT, this.fileCount + "");
        this.httpSucced = false;
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_APP_UPLOADOSSTOKEN, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.utils.TXP2PLogcatStorageHelper.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                if (TXP2PLogcatStorageHelper.this.httpSucced) {
                    if (TXP2PLogcatStorageHelper.this.aliOssTokenData != null) {
                        TXP2PLogcatStorageHelper.this.retryTimes = 0;
                        TXP2PLogcatStorageHelper.this.uploadFileToAli();
                        return;
                    } else {
                        TXP2PLogcatStorageHelper.this.retryTimes = 0;
                        TXP2PLogcatStorageHelper.this.uploadLogZipToQiNiu();
                        return;
                    }
                }
                TXP2PLogcatStorageHelper.access$108(TXP2PLogcatStorageHelper.this);
                TXP2PLogcatStorageHelper.this.getOsstoken();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                try {
                    String string = new JSONObject(this.responseResult).getJSONObject("result").getString("type");
                    if (string.equals("ali")) {
                        AliOsstokenRsp aliOsstokenRsp = (AliOsstokenRsp) this.gson.fromJson(this.responseResult, AliOsstokenRsp.class);
                        TXP2PLogcatStorageHelper.this.httpSucced = true;
                        TXP2PLogcatStorageHelper.this.aliOssTokenData = aliOsstokenRsp.getResult().getData();
                        TXP2PLogcatStorageHelper tXP2PLogcatStorageHelper = TXP2PLogcatStorageHelper.this;
                        tXP2PLogcatStorageHelper.initALiOssClient(tXP2PLogcatStorageHelper.aliOssTokenData);
                    } else if (string.equals("qiniu")) {
                        try {
                            QiniuOsstokenRsp qiniuOsstokenRsp = (QiniuOsstokenRsp) this.gson.fromJson(new String(bArr), QiniuOsstokenRsp.class);
                            TXP2PLogcatStorageHelper.this.qiNiuKey = qiniuOsstokenRsp.getResult().getData().get(0).getKey();
                            TXP2PLogcatStorageHelper.this.qiNiuToken = qiniuOsstokenRsp.getResult().getData().get(0).getToken();
                            TXP2PLogcatStorageHelper.this.httpSucced = true;
                        } catch (Exception unused) {
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, false);
    }

    public final void uploadDebugFile(String... strArr) throws Throwable {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str : strArr) {
            arrayList2.add(new File(PATH_LOGCAT + str));
        }
        this.zipfileString = PATH_LOGCAT + System.currentTimeMillis() + MultiDexExtractor.EXTRACTED_SUFFIX;
        try {
            ZipUtil.zipFiles(arrayList2, new File(this.zipfileString));
            arrayList.add(this.zipfileString);
            this.mUploadingFiles = arrayList2;
            this.fileCount = arrayList.size();
            this.retryTimes = 0;
            getOsstoken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadFileToAli() {
        final String str = this.aliOssTokenData.getPathPrefix() + ((System.currentTimeMillis() / 1000) + CommonUtils.getCurrentUserId());
        PutObjectRequest putObjectRequest = new PutObjectRequest(this.aliOssTokenData.getBucketName(), str, this.zipfileString);
        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() { // from class: com.petkit.android.utils.TXP2PLogcatStorageHelper.2
            @Override // com.alibaba.sdk.android.oss.callback.OSSProgressCallback
            public void onProgress(PutObjectRequest putObjectRequest2, long j, long j2) {
            }
        });
        this.oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() { // from class: com.petkit.android.utils.TXP2PLogcatStorageHelper.3
            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onSuccess(PutObjectRequest putObjectRequest2, PutObjectResult putObjectResult) {
                String str2 = String.format("https://%s", TXP2PLogcatStorageHelper.this.aliOssTokenData.getBucketName() + "." + TXP2PLogcatStorageHelper.this.aliOssTokenData.getEndPoint() + "/" + str);
                StringBuilder sb = new StringBuilder();
                sb.append("Ali Oss Update Success----阿里OSS上传成功---Url：");
                sb.append(str2);
                LogcatStorageHelper.addLog(sb.toString());
                File file = new File(TXP2PLogcatStorageHelper.this.zipfileString);
                long length = file.length();
                file.delete();
                TXP2PLogcatStorageHelper.this.retryTimes = 0;
                TXP2PLogcatStorageHelper.this.aliHttpReuqest(new Gson().toJson(new UpdateLogParam(str2, length)));
            }

            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onFailure(PutObjectRequest putObjectRequest2, ClientException clientException, ServiceException serviceException) {
                TXP2PLogcatStorageHelper.access$108(TXP2PLogcatStorageHelper.this);
                TXP2PLogcatStorageHelper.this.uploadFileToAli();
                if (clientException != null) {
                    clientException.printStackTrace();
                    LogcatStorageHelper.addLog("Ali Oss Update Fail----客户端异常 Error:" + clientException.getMessage());
                }
                if (serviceException != null) {
                    LogcatStorageHelper.addLog("Ali Oss Update Fail----服务端异常 Error:" + serviceException.getMessage());
                }
            }
        });
    }

    public final void uploadLogZipToQiNiu() {
        if (this.zipfileString == null || this.retryTimes > 1) {
            processFailed();
        } else {
            new UploadManager(new Configuration.Builder().useHttps(true).build()).put(this.zipfileString, this.qiNiuKey, this.qiNiuToken, new UpCompletionHandler() { // from class: com.petkit.android.utils.TXP2PLogcatStorageHelper.4
                @Override // com.qiniu.android.storage.UpCompletionHandler
                public void complete(String str, ResponseInfo responseInfo, JSONObject jSONObject) {
                    if (responseInfo.isOK()) {
                        TXP2PLogcatStorageHelper.this.qiNiuResult = jSONObject.toString().replaceAll("\\\\", "");
                        new File(TXP2PLogcatStorageHelper.this.zipfileString).delete();
                        TXP2PLogcatStorageHelper tXP2PLogcatStorageHelper = TXP2PLogcatStorageHelper.this;
                        tXP2PLogcatStorageHelper.qiNiuResultRecord = QiNiuResultRecord.getInstance(tXP2PLogcatStorageHelper.context);
                        TXP2PLogcatStorageHelper.this.qiNiuResultRecord.writeQiNiuResult(TXP2PLogcatStorageHelper.this.qiNiuResult);
                        TXP2PLogcatStorageHelper.this.retryTimes = 0;
                        TXP2PLogcatStorageHelper.this.uploadQiNiuResultToApi();
                        return;
                    }
                    TXP2PLogcatStorageHelper.access$108(TXP2PLogcatStorageHelper.this);
                    TXP2PLogcatStorageHelper.this.uploadLogZipToQiNiu();
                }
            }, (UploadOptions) null);
        }
    }

    public final void aliHttpReuqest(final String str) {
        if (this.retryTimes > 1) {
            processFailed();
            return;
        }
        Log.d("AliOss", "start uploadLog");
        Log.d("AliOss", "log:" + str);
        HashMap map = new HashMap();
        map.put("log", str);
        WebModelRepository.getInstance().uploadLog((BaseActivity) this.context, map, new PetkitCallback<String>() { // from class: com.petkit.android.utils.TXP2PLogcatStorageHelper.5
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str2) {
                Log.d("AliOss", "uploadLog success");
                if (TXP2PLogcatStorageHelper.this.mUploadingFiles != null) {
                    Iterator it = TXP2PLogcatStorageHelper.this.mUploadingFiles.iterator();
                    while (it.hasNext()) {
                        ((File) it.next()).delete();
                    }
                    TXP2PLogcatStorageHelper.this.mUploadingFiles = null;
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                TXP2PLogcatStorageHelper.access$108(TXP2PLogcatStorageHelper.this);
                Log.d("AliOss", "uploadLog fail");
                TXP2PLogcatStorageHelper.this.aliHttpReuqest(str);
            }
        });
    }

    public final void uploadQiNiuResultToApi() {
        Map<String, String> qiNiuResults = this.qiNiuResultRecord.readQiNiuResults();
        int i = 0;
        while (true) {
            this.resultCount = i;
            if (this.resultCount >= 3) {
                return;
            }
            String str = qiNiuResults.get("record" + this.resultCount);
            this.qiNiuResult = str;
            if (str != null && str.length() > 0) {
                httpReuqest(this.qiNiuResult, this.resultCount);
            }
            i = this.resultCount + 1;
        }
    }

    public final void httpReuqest(final String str, final int i) {
        if (this.retryTimes > 1) {
            processFailed();
            return;
        }
        HashMap map = new HashMap();
        map.put("log", str);
        AsyncHttpUtil.post(this.logPostUrl, (Map<String, String>) map, new AsyncHttpResponseHandler() { // from class: com.petkit.android.utils.TXP2PLogcatStorageHelper.6
            @Override // com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                try {
                    if (new JSONObject(new String(bArr)).optString("result", " ").equals("success")) {
                        QiNiuResultRecord qiNiuResultRecord = TXP2PLogcatStorageHelper.this.qiNiuResultRecord;
                        StringBuilder sb = new StringBuilder();
                        QiNiuResultRecord unused = TXP2PLogcatStorageHelper.this.qiNiuResultRecord;
                        sb.append("record");
                        sb.append(i);
                        qiNiuResultRecord.deleteQiNiuResult(sb.toString());
                        if (TXP2PLogcatStorageHelper.this.mUploadingFiles != null) {
                            Iterator it = TXP2PLogcatStorageHelper.this.mUploadingFiles.iterator();
                            while (it.hasNext()) {
                                ((File) it.next()).delete();
                            }
                            TXP2PLogcatStorageHelper.this.mUploadingFiles = null;
                            return;
                        }
                        return;
                    }
                    TXP2PLogcatStorageHelper.access$108(TXP2PLogcatStorageHelper.this);
                    TXP2PLogcatStorageHelper.this.httpReuqest(str, i);
                } catch (JSONException unused2) {
                    TXP2PLogcatStorageHelper.access$108(TXP2PLogcatStorageHelper.this);
                    TXP2PLogcatStorageHelper.this.httpReuqest(str, i);
                }
            }

            @Override // com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i2, Header[] headerArr, byte[] bArr, Throwable th) {
                TXP2PLogcatStorageHelper.access$108(TXP2PLogcatStorageHelper.this);
                TXP2PLogcatStorageHelper.this.httpReuqest(str, i);
            }
        }, false);
    }

    public final void processFailed() {
        if (this.zipfileString != null) {
            new File(this.zipfileString).delete();
            this.zipfileString = null;
        }
        if (this.mUploadingFiles != null) {
            this.mUploadingFiles = null;
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String getFileName() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
    }

    public static String getDateEN() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public static class QiNiuResultRecord {
        public static final int MAX_COUNT = 3;
        public static final String RECORD = "record";
        public static QiNiuResultRecord qiNiuResultRecord;
        public SharedPreferences.Editor editor;
        public SharedPreferences sharedPreferences;

        public QiNiuResultRecord(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("qiNiuResultRecord", 32768);
            this.sharedPreferences = sharedPreferences;
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            this.editor = editorEdit;
            editorEdit.putInt(AlbumLoader.COLUMN_COUNT, 0);
        }

        public static QiNiuResultRecord getInstance(Context context) {
            if (qiNiuResultRecord == null) {
                qiNiuResultRecord = new QiNiuResultRecord(context);
            }
            return qiNiuResultRecord;
        }

        public void writeQiNiuResult(String str) {
            int i = this.sharedPreferences.getInt(AlbumLoader.COLUMN_COUNT, 0);
            this.editor.putString("record" + i, str);
            this.editor.putInt(AlbumLoader.COLUMN_COUNT, (i + 1) % 3);
            this.editor.commit();
        }

        public Map<String, String> readQiNiuResults() {
            HashMap map = new HashMap();
            for (int i = 0; i < 3; i++) {
                map.put("record" + i, this.sharedPreferences.getString("record" + i, ""));
            }
            return map;
        }

        public void deleteQiNiuResult(String str) {
            if (this.sharedPreferences.contains(str)) {
                this.editor.remove(str);
                this.editor.commit();
            }
        }
    }
}

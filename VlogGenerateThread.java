package com.petkit.android.activities.petkitBleDevice.vlog;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jess.arms.utils.ImageUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.petkitBleDevice.download.mode.CloudVideo;
import com.petkit.android.activities.petkitBleDevice.download.mode.MediaMsg;
import com.petkit.android.activities.petkitBleDevice.download.mode.VideoDownloadRecord;
import com.petkit.android.activities.petkitBleDevice.download.utils.VideoDownloadManager;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.OssStsInfo;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.OssStsInfoV2;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.VlogM3U8Mode;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.HttpResult;
import com.petkit.android.api.http.PetkitErrorHandleSubscriber;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.service.D4hService;
import com.petkit.android.api.service.D4shService;
import com.petkit.android.api.service.T6Service;
import com.petkit.android.api.service.W7hService;
import com.petkit.android.ble.PetkitBLEConsts;
import com.petkit.android.media.video.player.ijkplayer.VideoUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.wode369.videocroplibrary.features.trim.VideoTrimmerUtil;
import com.wode369.videocroplibrary.interfaces.VideoTrimListener;
import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public class VlogGenerateThread implements Runnable {
    public static final int TIMEOUT_SECOND_DOWNLOAD = 100;
    public static final int TIMEOUT_SECOND_GENERATE_VIDEO = 60;
    public static final int TIMEOUT_SECOND_UPLOAD = 120;
    public String aesKey;
    public int cycle;
    public int deviceType;
    public int mCurSec;
    public long mDeviceId;
    public String mId;
    public String mIdDir;
    public VlogGenerateListener mListener;
    public vlogMakeThread mMakeThread;
    public String mPreviewFilePath;
    public float mProgress;
    public int mTimeout;
    public Timer mTimeoutTimer;
    public int mVdieoHeight;
    public String mVideoFilePath;
    public int mVideoWidth;
    public int typeCode;
    public int mStep = 0;
    public int mGenerateVideoRealTime = 0;
    public long mGenerateStartTime = 0;
    public Context mContext = CommonUtils.getAppContext();
    public LinkedHashMap<VlogM3U8Mode, VideoDownloadRecord> mDownloadMap = new LinkedHashMap<>();
    public String mVlogDir = this.mContext.getExternalCacheDir().getAbsolutePath() + "/vlog/";

    public interface VlogGenerateListener {
        void onComplete(String str, long j, String str2);

        void onFailed(String str, long j, int i, String str2);

        void onProgress(String str, int i, int i2);

        void onStart(String str);
    }

    public static /* synthetic */ int access$2508(VlogGenerateThread vlogGenerateThread) {
        int i = vlogGenerateThread.mCurSec;
        vlogGenerateThread.mCurSec = i + 1;
        return i;
    }

    public VlogGenerateThread(long j, int i, int i2, String str, int i3, ArrayList<VlogM3U8Mode> arrayList, VlogGenerateListener vlogGenerateListener) {
        this.mDeviceId = j;
        this.typeCode = i2;
        this.cycle = i3;
        this.deviceType = i;
        this.mId = str;
        this.mListener = vlogGenerateListener;
        this.mIdDir = this.mVlogDir + this.mId + "/";
        File file = new File(this.mIdDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        ArrayList arrayList2 = new ArrayList();
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        for (VlogM3U8Mode vlogM3U8Mode : arrayList) {
            if (!arrayList2.contains(vlogM3U8Mode.getMediaApi())) {
                arrayList2.add(vlogM3U8Mode.getMediaApi());
                VideoDownloadRecord videoDownloadRecordByUrlAndType = VideoDownloadManager.getVideoDownloadRecordByUrlAndType(VideoUtils.getVideoUrl(vlogM3U8Mode.getMediaApi()), 3);
                if (videoDownloadRecordByUrlAndType != null && !TextUtils.isEmpty(videoDownloadRecordByUrlAndType.getTranscodePath()) && videoDownloadRecordByUrlAndType.getState() == 12 && new File(videoDownloadRecordByUrlAndType.getTranscodePath()).exists()) {
                    this.mDownloadMap.put(vlogM3U8Mode, videoDownloadRecordByUrlAndType);
                } else {
                    this.mDownloadMap.put(vlogM3U8Mode, null);
                }
            }
        }
    }

    public final String getVlogTail(int i, int i2) {
        return i != 27 ? i2 == 1 ? "vlog-tail-hd.mp4" : "vlog-tail-sd.mp4" : i2 == 1 ? "vlog-tail-t6-hd.mp4" : "vlog-tail-t6-sd.mp4";
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.mListener.onStart(this.mId);
            this.mProgress = 0.0f;
            startTimer(100);
            startDownload();
            if (isDownloadComplete()) {
                checkFirstVideoDownloaded();
            }
        } catch (Error e) {
            PetkitLog.e("VlogGenerateThread", "fatal error" + e.getMessage());
            LogcatStorageHelper.addLog("VlogGenerateThread error: " + e.getMessage());
        } catch (Exception e2) {
            PetkitLog.e("VlogGenerateThread", "run failed" + e2.getMessage());
            LogcatStorageHelper.addLog("VlogGenerateThread error: " + e2.getMessage());
        }
    }

    public class vlogMakeThread extends Thread {
        public int mCurIndex = -1;
        public boolean isAbort = false;

        public vlogMakeThread() {
        }

        /* JADX WARN: Can't wrap try/catch for region: R(12:52|(11:55|(1:57)(1:58)|59|(1:61)(1:62)|63|(1:65)(1:66)|67|(1:69)(1:70)|71|(1:129)(2:75|(6:130|77|(1:79)|(1:81)|82|(4:89|(1:91)|92|(3:125|94|95)(7:96|(3:98|(1:100)|101)(1:88)|104|121|105|127|109))(8:86|87|88|104|121|105|127|109))(1:102))|53)|131|103|87|88|104|121|105|127|109|48) */
        /* JADX WARN: Code restructure failed: missing block: B:107:0x076d, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:108:0x076e, code lost:
        
            r0.printStackTrace();
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instruction units count: 2056
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.vlogMakeThread.run():void");
        }

        public void setAbort(boolean z) {
            this.isAbort = z;
        }
    }

    public final void startDownload() {
        for (Map.Entry<VlogM3U8Mode, VideoDownloadRecord> entry : this.mDownloadMap.entrySet()) {
            if (entry.getValue() == null) {
                PetkitLog.d(this.mId + "开始下载，" + entry.getKey() + entry.getKey().getRa());
                StringBuilder sb = new StringBuilder();
                sb.append(this.mId);
                sb.append("开始下载，");
                sb.append(entry.getKey().getRa());
                LogcatStorageHelper.addLog(sb.toString());
                VideoDownloadManager.startDownloadTask((Context) CommonUtils.getApplication(), new CloudVideo(entry.getKey().getRa(), "", VideoUtils.getVideoUrl(entry.getKey().getMediaApi()), entry.getKey().getStorageSpace(), 1000 * entry.getKey().getStartTime(), 0, 3, 0, 1, this.deviceType), false);
                return;
            }
        }
    }

    public final boolean isDownloadComplete() {
        for (Map.Entry<VlogM3U8Mode, VideoDownloadRecord> entry : this.mDownloadMap.entrySet()) {
            if (entry.getValue() == null || entry.getValue().getState() != 12) {
                return false;
            }
        }
        return true;
    }

    public final void checkFirstVideoDownloaded() {
        Map.Entry<VlogM3U8Mode, VideoDownloadRecord> next = this.mDownloadMap.entrySet().iterator().next();
        VideoDownloadRecord value = next.getValue();
        if (value != null && value.getState() == 12 && this.mVideoWidth == 0) {
            File file = new File(value.getTranscodePath());
            if (file.isDirectory() || file.length() == 0) {
                file.delete();
                value.delete();
                VideoDownloadManager.startDownloadTask((Context) CommonUtils.getApplication(), new CloudVideo(next.getKey().getRa(), "", VideoUtils.getVideoUrl(next.getKey().getMediaApi()), next.getKey().getStorageSpace(), next.getKey().getStartTime() * 1000, 0, 3, 0, 1, this.deviceType), false);
            } else if (this.mPreviewFilePath == null) {
                getPreviewFromVideo(value.getTranscodePath());
            }
        }
    }

    public final void getPreviewFromVideo(String str) {
        PetkitLog.d(this.mId + " getPreviewFromVideo start: " + str);
        String str2 = this.mIdDir + this.mId + "-" + System.currentTimeMillis() + ".jpg";
        this.mPreviewFilePath = str2;
        VideoTrimmerUtil.getVideoPreview(str, str2, new VideoTrimListener() { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.1
            @Override // com.wode369.videocroplibrary.interfaces.VideoTrimListener
            public void onCancel() {
            }

            @Override // com.wode369.videocroplibrary.interfaces.VideoTrimListener
            public void onVideoPrepared(int i) {
            }

            @Override // com.wode369.videocroplibrary.interfaces.VideoTrimListener
            public void onStartTrim() {
                PetkitLog.d(VlogGenerateThread.this.mId + "getPreviewFromVideo start");
                LogcatStorageHelper.addLog(VlogGenerateThread.this.mId + "getPreviewFromVideo start");
            }

            @Override // com.wode369.videocroplibrary.interfaces.VideoTrimListener
            public void onFinishTrim(String str3) {
                PetkitLog.d("getPreviewFromVideo onFinishTrim：" + str3);
                LogcatStorageHelper.addLog("getPreviewFromVideo onFinishTrim：" + str3);
                if (VlogGenerateThread.this.mMakeThread == null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(str3, options);
                    if (VlogGenerateThread.this.deviceType == 27) {
                        VlogGenerateThread.this.mVideoWidth = 1080;
                        VlogGenerateThread.this.mVdieoHeight = 1080;
                    } else if (VlogGenerateThread.this.deviceType == 29) {
                        VlogGenerateThread.this.mVideoWidth = 1728;
                        VlogGenerateThread.this.mVdieoHeight = 972;
                    } else {
                        VlogGenerateThread.this.mVideoWidth = options.outWidth;
                        VlogGenerateThread.this.mVdieoHeight = options.outHeight;
                    }
                    VlogGenerateThread.this.mMakeThread = VlogGenerateThread.this.new vlogMakeThread();
                    VlogGenerateThread.this.mMakeThread.start();
                    if (VlogGenerateThread.this.isDownloadComplete()) {
                        VlogGenerateThread.this.startTimer(60);
                    }
                }
            }

            @Override // com.wode369.videocroplibrary.interfaces.VideoTrimListener
            public void onError(String str3) {
                PetkitLog.d("getPreviewFromVideo error：" + str3);
                LogcatStorageHelper.addLog("getPreviewFromVideo error：" + str3);
                VlogGenerateThread.this.gotoFailed(str3);
            }
        });
    }

    public final void uploadVlog() {
        HashMap map = new HashMap();
        map.put("deviceId", Long.valueOf(this.mDeviceId));
        map.put("cycle", Integer.valueOf(this.cycle));
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread$2, reason: invalid class name */
    public class AnonymousClass2 extends PetkitErrorHandleSubscriber<OssStsInfo> {
        @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
        public void _onError(ErrorInfor errorInfor) {
        }

        public AnonymousClass2(RxErrorHandler rxErrorHandler) {
            super(rxErrorHandler);
        }

        @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
        public void onSuccess(OssStsInfo ossStsInfo) {
            if (TextUtils.isEmpty(ossStsInfo.getAesKeyStr())) {
                VlogGenerateThread.this.startUploadFileWithAli(ossStsInfo);
                return;
            }
            VlogGenerateThread.this.aesKey = ossStsInfo.getAesKeyStr();
            File fileEncryptImageFile = ImageUtils.encryptImageFile(new File(VlogGenerateThread.this.mPreviewFilePath), ossStsInfo.getAesKeyStr());
            if (fileEncryptImageFile != null) {
                VlogGenerateThread.this.mPreviewFilePath = fileEncryptImageFile.getAbsolutePath();
            }
            VlogGenerateThread.this.startUploadFileWithAli(ossStsInfo);
        }
    }

    public final void complete(String str) {
        for (Map.Entry<VlogM3U8Mode, VideoDownloadRecord> entry : this.mDownloadMap.entrySet()) {
            if (entry.getValue() != null) {
                VideoDownloadManager.removeTask(CommonUtils.getAppContext(), entry.getValue().getMediaApi(), 3);
            }
        }
        FileUtils.deleteDir(new File(this.mIdDir));
        cancelTimer();
        this.mListener.onComplete(this.mId, this.mDeviceId, str);
    }

    public final void startUploadFileWithAli(final OssStsInfo ossStsInfo) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeout(15000);
        clientConfiguration.setSocketTimeout(15000);
        clientConfiguration.setMaxConcurrentRequest(5);
        clientConfiguration.setMaxErrorRetry(2);
        OSSLog.enableLog();
        final long length = new File(this.mVideoFilePath).length();
        final OSSClient oSSClient = new OSSClient(CommonUtils.getApplication(), ossStsInfo.getEndpoint(), new OSSStsTokenCredentialProvider(ossStsInfo.getAccessKeyId(), ossStsInfo.getAccessKeySecret(), ossStsInfo.getSecurityToken()), clientConfiguration);
        final String str = ossStsInfo.getPathPrefix() + "/" + this.mId + "-" + System.currentTimeMillis() + ".mp4";
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossStsInfo.getBucketName(), str, this.mVideoFilePath);
        final String str2 = ossStsInfo.getPathPrefix() + "/" + this.mId + "-" + System.currentTimeMillis() + ".jpg";
        final PutObjectRequest putObjectRequest2 = new PutObjectRequest(ossStsInfo.getBucketName(), str2, this.mPreviewFilePath);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setHeader("x-oss-object-acl", "public-read");
        putObjectRequest2.setMetadata(objectMetadata);
        oSSClient.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.3
            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onSuccess(PutObjectRequest putObjectRequest3, PutObjectResult putObjectResult) {
                PetkitLog.d("PutObject", "UploadSuccess");
                LogcatStorageHelper.addLog("UploadSuccess");
                oSSClient.asyncPutObject(putObjectRequest2, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.3.1
                    @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
                    public void onSuccess(PutObjectRequest putObjectRequest4, PutObjectResult putObjectResult2) {
                        VlogGenerateThread.this.saveVlogUrlToServer(String.format("https://%s/", ossStsInfo.getServerDomain()) + str, length, String.format("https://%s/", ossStsInfo.getServerDomain()) + str2);
                    }

                    @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
                    public void onFailure(PutObjectRequest putObjectRequest4, ClientException clientException, ServiceException serviceException) {
                        VlogGenerateThread.this.mListener.onFailed(VlogGenerateThread.this.mId, VlogGenerateThread.this.mDeviceId, VlogGenerateThread.this.mStep, "startUploadFileWithAli image failed");
                    }
                });
            }

            @Override // com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
            public void onFailure(PutObjectRequest putObjectRequest3, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    PetkitLog.e("ErrorCode", serviceException.getErrorCode());
                    PetkitLog.e("RequestId", serviceException.getRequestId());
                    PetkitLog.e("HostId", serviceException.getHostId());
                    PetkitLog.e("RawMessage", serviceException.getRawMessage());
                }
                VlogGenerateThread.this.mListener.onFailed(VlogGenerateThread.this.mId, VlogGenerateThread.this.mDeviceId, VlogGenerateThread.this.mStep, "startUploadFileWithAli video failed");
            }
        });
    }

    public final void saveVlogUrlToServer(final String str, long j, String str2) {
        Observable<HttpResult<String>> observableUploadHighlight;
        HashMap map = new HashMap();
        map.put("id", this.mId);
        map.put("preview", str2);
        map.put("videoUrl", str);
        map.put("storageSpace", Long.valueOf(j));
        map.put("duration", Integer.valueOf(this.mDownloadMap.size() * 3));
        if (!TextUtils.isEmpty(this.aesKey)) {
            map.put("aesKey", this.aesKey);
        }
        switch (this.deviceType) {
            case 25:
                observableUploadHighlight = ((D4shService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4shService.class)).uploadHighlight(map);
                break;
            case 26:
                observableUploadHighlight = ((D4hService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4hService.class)).uploadHighlight(map);
                break;
            case 27:
                observableUploadHighlight = ((T6Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(T6Service.class)).uploadHighlight(map);
                break;
            case 28:
            default:
                observableUploadHighlight = null;
                break;
            case 29:
                observableUploadHighlight = ((W7hService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(W7hService.class)).uploadHighlight(map);
                break;
        }
        if (observableUploadHighlight != null) {
            observableUploadHighlight.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<String>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.4
                @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
                public void onSuccess(String str3) {
                    VlogGenerateThread.this.complete(str);
                }

                @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
                public void _onError(ErrorInfor errorInfor) {
                    VlogGenerateThread.this.mListener.onFailed(VlogGenerateThread.this.mId, VlogGenerateThread.this.mDeviceId, VlogGenerateThread.this.mStep, "saveVlogUrlToServer failed");
                }
            });
        }
    }

    public final void gotoFailed(String str) {
        PetkitLog.e("gotoFailed", str);
        LogcatStorageHelper.addLog("gotoFailed: " + str);
        vlogMakeThread vlogmakethread = this.mMakeThread;
        if (vlogmakethread != null) {
            vlogmakethread.setAbort(true);
        }
        cancelTimer();
        this.mListener.onFailed(this.mId, this.mDeviceId, this.mStep, str);
    }

    public final void startTimer(int i) {
        cancelTimer();
        this.mCurSec = 0;
        this.mTimeout = i;
        Timer timer = new Timer();
        this.mTimeoutTimer = timer;
        timer.schedule(new TimerTask() { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.5
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                VlogGenerateThread.access$2508(VlogGenerateThread.this);
                if (VlogGenerateThread.this.mGenerateVideoRealTime == 0) {
                    VlogGenerateThread.this.mProgress += 0.35714287f;
                } else {
                    VlogGenerateThread.this.mProgress += 100.0f / (r0.mGenerateVideoRealTime + PetkitBLEConsts.CMD_PETKIT_BLE_220);
                }
                if (VlogGenerateThread.this.mCurSec >= VlogGenerateThread.this.mTimeout) {
                    PetkitLog.d("onFailed id" + VlogGenerateThread.this.mCurSec + ":" + VlogGenerateThread.this.mTimeout);
                    LogcatStorageHelper.addLog("onFailed id" + VlogGenerateThread.this.mCurSec + ":" + VlogGenerateThread.this.mTimeout);
                    VlogGenerateThread.this.gotoFailed("timeout");
                    return;
                }
                VlogGenerateThread vlogGenerateThread = VlogGenerateThread.this;
                if (vlogGenerateThread.mProgress > 100.0f) {
                    vlogGenerateThread.mProgress = 100.0f;
                }
                PetkitLog.d("Vlog onProgress： " + Math.round(VlogGenerateThread.this.mProgress));
                LogcatStorageHelper.addLog("Vlog onProgress： " + Math.round(VlogGenerateThread.this.mProgress));
                VlogGenerateThread.this.mListener.onProgress(VlogGenerateThread.this.mId, VlogGenerateThread.this.mStep, Math.round(VlogGenerateThread.this.mProgress));
            }
        }, 0L, 1000L);
    }

    public final void cancelTimer() {
        Timer timer = this.mTimeoutTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimeoutTimer = null;
        }
        this.mTimeout = 0;
        this.mCurSec = 0;
    }

    public void downloadState(MediaMsg mediaMsg) {
        PetkitLog.d(this.mId + " downloadState，" + mediaMsg.toString());
        LogcatStorageHelper.addLog(this.mId + " downloadState，" + mediaMsg.toString());
        VlogM3U8Mode keyFromDownloadMap = getKeyFromDownloadMap(mediaMsg);
        if (keyFromDownloadMap != null) {
            if (mediaMsg.getState() == 12) {
                PetkitLog.d(this.mId + " 下载完成，" + mediaMsg.getUrl());
                LogcatStorageHelper.addLog(this.mId + " 下载完成，" + mediaMsg.getUrl());
                this.mDownloadMap.put(keyFromDownloadMap, VideoDownloadManager.getVideoDownloadRecordByUrlAndType(mediaMsg.getUrl(), 3));
                if (isDownloadComplete()) {
                    checkFirstVideoDownloaded();
                    startTimer(60);
                    return;
                } else {
                    startDownload();
                    return;
                }
            }
            PetkitLog.d(this.mId + " 下载状态更新，state：" + mediaMsg.getState() + ", url: " + mediaMsg.getUrl());
            LogcatStorageHelper.addLog(this.mId + " 下载状态更新，state：" + mediaMsg.getState() + ", url: " + mediaMsg.getUrl());
            this.mDownloadMap.put(keyFromDownloadMap, VideoDownloadManager.getVideoDownloadRecordByUrlAndType(mediaMsg.getUrl(), 3));
        }
    }

    public final VlogM3U8Mode getKeyFromDownloadMap(MediaMsg mediaMsg) {
        for (Map.Entry<VlogM3U8Mode, VideoDownloadRecord> entry : this.mDownloadMap.entrySet()) {
            if (mediaMsg.getUrl().equalsIgnoreCase(VideoUtils.getVideoUrl(entry.getKey().getMediaApi()))) {
                return entry.getKey();
            }
        }
        return null;
    }

    public final void uploadVlogV2() {
        Observable<HttpResult<OssStsInfoV2>> ossInfo;
        HashMap map = new HashMap();
        map.put("deviceId", Long.valueOf(this.mDeviceId));
        map.put("cycle", Integer.valueOf(this.cycle));
        switch (this.deviceType) {
            case 25:
                ossInfo = ((D4shService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4shService.class)).getOssInfo(map);
                break;
            case 26:
                ossInfo = ((D4hService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(D4hService.class)).getOssInfo(map);
                break;
            case 27:
                ossInfo = ((T6Service) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(T6Service.class)).getOssInfo(map);
                break;
            case 28:
            default:
                ossInfo = null;
                break;
            case 29:
                ossInfo = ((W7hService) ((BaseApplication) CommonUtils.getApplication()).getAppComponent().repositoryManager().obtainRetrofitService(W7hService.class)).getOssInfo(map);
                break;
        }
        if (ossInfo == null) {
            PetkitLog.d("PutObject", "start uploadVlogV2: ossInfo is null");
            LogcatStorageHelper.addLog("PutObjectstart uploadVlogV2: ossInfo is null");
            return;
        }
        PetkitLog.d("PutObject", "start uploadVlogV2: " + this.mVideoFilePath);
        LogcatStorageHelper.addLog("PutObjectstart uploadVlogV2: " + this.mPreviewFilePath);
        ossInfo.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new PetkitErrorHandleSubscriber<OssStsInfoV2>(((BaseApplication) CommonUtils.getApplication()).getAppComponent().rxErrorHandler()) { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.6
            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void onSuccess(OssStsInfoV2 ossStsInfoV2) {
                if (!TextUtils.isEmpty(ossStsInfoV2.getStandbyAesKeyStr())) {
                    VlogGenerateThread.this.aesKey = ossStsInfoV2.getStandbyAesKeyStr();
                    File fileEncryptImageFile = ImageUtils.encryptImageFile(new File(VlogGenerateThread.this.mPreviewFilePath), ossStsInfoV2.getStandbyAesKeyStr());
                    if (fileEncryptImageFile != null) {
                        VlogGenerateThread.this.mPreviewFilePath = fileEncryptImageFile.getAbsolutePath();
                    }
                }
                if ("oci".equalsIgnoreCase(ossStsInfoV2.getType())) {
                    VlogGenerateThread.this.startUploadFileWithOci(ossStsInfoV2, 0);
                } else {
                    VlogGenerateThread.this.startUploadFileWithAli(ossStsInfoV2.toOssStsInfo());
                }
            }

            @Override // com.petkit.android.api.http.PetkitErrorHandleSubscriber
            public void _onError(ErrorInfor errorInfor) {
                PetkitLog.d("PutObject", "start uploadVlogV2 _onError: " + errorInfor.getMsg());
                LogcatStorageHelper.addLog("start uploadVlogV2 _onError: " + errorInfor.getMsg());
            }
        });
        startTimer(120);
    }

    public final void startUploadFileWithOci(final OssStsInfoV2 ossStsInfoV2, final int i) {
        String str;
        PetkitLog.d("PutObject", "startUploadFileWithOci: " + this.mVideoFilePath);
        LogcatStorageHelper.addLog("PutObjectstartUploadFileWithOci: " + this.mVideoFilePath);
        File file = new File(this.mVideoFilePath);
        final long length = new File(this.mVideoFilePath).length();
        final String str2 = this.mId + "-" + System.currentTimeMillis() + ".mp4";
        if (i == 0) {
            str = ossStsInfoV2.getPrimaryParUrl() + "/" + ossStsInfoV2.getPathPrefix() + "/" + str2;
        } else if (i == 1) {
            str = ossStsInfoV2.getStandbyParUrl() + "/" + ossStsInfoV2.getPathPrefix() + "/" + str2;
        } else {
            gotoFailed("OCI File upload failed!");
            return;
        }
        try {
            AsyncHttpUtil.put(this.mContext, str, file, "video/mp4", new TextHttpResponseHandler() { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.7
                @Override // com.loopj.android.http.TextHttpResponseHandler
                public void onSuccess(int i2, Header[] headerArr, String str3) {
                    String str4;
                    PetkitLog.d("PutObject", "startUploadFileWithOci onSuccess1");
                    LogcatStorageHelper.addLog("startUploadFileWithOci onSuccess1 ");
                    File file2 = new File(VlogGenerateThread.this.mPreviewFilePath);
                    final String str5 = VlogGenerateThread.this.mId + "-" + System.currentTimeMillis() + ".jpg";
                    int i3 = i;
                    if (i3 == 0) {
                        str4 = ossStsInfoV2.getPrimaryParUrl() + "/" + ossStsInfoV2.getPathPrefix() + "/" + str5;
                    } else {
                        if (i3 != 1) {
                            VlogGenerateThread.this.gotoFailed("OCI File upload failed!");
                            return;
                        }
                        str4 = ossStsInfoV2.getStandbyParUrl() + "/" + ossStsInfoV2.getPathPrefix() + "/" + str5;
                    }
                    try {
                        AsyncHttpUtil.put(VlogGenerateThread.this.mContext, str4, file2, TweetComposer.MIME_TYPE_JPEG, new TextHttpResponseHandler() { // from class: com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.7.1
                            @Override // com.loopj.android.http.TextHttpResponseHandler
                            public void onSuccess(int i4, Header[] headerArr2, String str6) {
                                String standbyDomain;
                                PetkitLog.d("PutObject", "startUploadFileWithOci onSuccess2");
                                LogcatStorageHelper.addLog("PutObject startUploadFileWithOci onSuccess2 ");
                                AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                                if (i == 0) {
                                    standbyDomain = ossStsInfoV2.getPrimaryDomain();
                                } else {
                                    standbyDomain = ossStsInfoV2.getStandbyDomain();
                                }
                                VlogGenerateThread.this.saveVlogUrlToServer(standbyDomain + ossStsInfoV2.getPathPrefix() + "/" + str2, length, standbyDomain + ossStsInfoV2.getPathPrefix() + "/" + str5);
                            }

                            @Override // com.loopj.android.http.TextHttpResponseHandler
                            public void onFailure(int i4, Header[] headerArr2, String str6, Throwable th) {
                                if (th != null) {
                                    PetkitLog.d("PutObject", "startUploadFileWithOci onFailure2 " + th.getMessage());
                                    LogcatStorageHelper.addLog("startUploadFileWithOci onFailure2 " + th.getMessage());
                                }
                                VlogGenerateThread.this.saveVlogUrlToServer(String.format("https://%s", ossStsInfoV2.getServerDomain()) + ossStsInfoV2.getPathPrefix() + str2, length, "");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        VlogGenerateThread.this.gotoFailed("File not found!");
                    }
                }

                @Override // com.loopj.android.http.TextHttpResponseHandler
                public void onFailure(int i2, Header[] headerArr, String str3, Throwable th) {
                    if (th != null) {
                        PetkitLog.d("PutObject", "startUploadFileWithOci onFailure1 " + th.getMessage());
                        LogcatStorageHelper.addLog("startUploadFileWithOci onFailure1 " + th.getMessage());
                    }
                    VlogGenerateThread.this.startUploadFileWithOci(ossStsInfoV2, i + 1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            gotoFailed("File not found!");
        }
    }
}

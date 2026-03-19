package com.petkit.android.media.video.rtmUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.media.video.rtmUtil.RtmCommandInfo;
import com.petkit.android.media.video.rtmUtil.RtmManager;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import cz.msebera.android.httpclient.Header;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.SendMessageOptions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes6.dex */
public class RtmManager {
    public final String TAG;
    public Disposable disposable60;
    public boolean isLoginRtm;
    public RtmClient mRtmClient;
    public RtmMsgReceiveCallBack receiveCallBack;
    public int retryTime;
    public List<RtmInfo> rtmInfoList;
    public String rtmToken;
    public String uid;

    public static /* synthetic */ void lambda$initRtm$0() {
    }

    public /* synthetic */ RtmManager(AnonymousClass1 anonymousClass1) {
        this();
    }

    public static /* synthetic */ int access$608(RtmManager rtmManager) {
        int i = rtmManager.retryTime;
        rtmManager.retryTime = i + 1;
        return i;
    }

    public static class SingletonHolder {
        public static final RtmManager INSTANCE = new RtmManager();
    }

    public RtmManager() {
        this.TAG = "RTM";
        this.retryTime = 0;
    }

    public static RtmManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setRtmListInfo(RtmListInfo rtmListInfo) {
        this.rtmToken = rtmListInfo.getRtmToken();
        this.uid = rtmListInfo.getAppRtmUserId();
        List<RtmInfo> deviceRtmInfo = rtmListInfo.getDeviceRtmInfo();
        this.rtmInfoList = deviceRtmInfo;
        if (deviceRtmInfo == null || deviceRtmInfo.size() < 1) {
            return;
        }
        PetkitLog.d("RTM", "收到的可观测id为:" + getRtmIds());
        initRtm();
    }

    public final String getRtmIds() {
        StringBuilder sb = new StringBuilder();
        Iterator<RtmInfo> it = this.rtmInfoList.iterator();
        while (it.hasNext()) {
            sb.append(it.next().getDeviceId());
            sb.append("、");
        }
        return sb.toString();
    }

    public final void initRtm() {
        playerLog("开始初始化RTM");
        try {
            if (this.mRtmClient == null) {
                this.mRtmClient = RtmClient.createInstance(CommonUtils.getAppContext(), "244c49951296440cbc1e3b937bf5e410", new RtmClientListener() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager.1
                    @Override // io.agora.rtm.RtmClientListener
                    public void onPeersOnlineStatusChanged(Map<String, Integer> map) {
                    }

                    @Override // io.agora.rtm.RtmClientListener
                    public void onTokenPrivilegeWillExpire() {
                    }

                    public AnonymousClass1() {
                    }

                    @Override // io.agora.rtm.RtmClientListener
                    public void onConnectionStateChanged(int i, int i2) {
                        PetkitLog.d("RTM", "Connection state changed to " + i + "Reason: " + i2 + "\n");
                        if (RtmManager.this.receiveCallBack != null) {
                            RtmManager.this.receiveCallBack.receiveState(i);
                        }
                    }

                    @Override // io.agora.rtm.RtmClientListener
                    public void onTokenExpired() {
                        RtmManager.this.playerLog("--onTokenExpired");
                        if (RtmManager.this.isLoginRtm) {
                            RtmManager.this.isLoginRtm = false;
                            RtmManager.this.getRtmListInfo();
                        }
                    }

                    @Override // io.agora.rtm.RtmClientListener
                    public void onMessageReceived(RtmMessage rtmMessage, String str) {
                        RtmManager.this.playerLog("Message received from " + str + " Message: " + new String(rtmMessage.getRawMessage()) + "\n");
                        if (RtmManager.this.receiveCallBack != null) {
                            RtmManager.this.receiveCallBack.receiveMsg(new String(rtmMessage.getRawMessage()));
                        }
                    }
                });
                rtmLogin(this.rtmToken, this.uid, new RtmLoginListener() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager$$ExternalSyntheticLambda0
                    @Override // com.petkit.android.media.video.rtmUtil.RtmLoginListener
                    public final void loginSuccess() {
                        RtmManager.lambda$initRtm$0();
                    }
                });
            }
        } catch (Exception e) {
            playerLog("RTM initialization failed! " + e.getMessage());
            throw new RuntimeException("RTM initialization failed!");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$1 */
    public class AnonymousClass1 implements RtmClientListener {
        @Override // io.agora.rtm.RtmClientListener
        public void onPeersOnlineStatusChanged(Map<String, Integer> map) {
        }

        @Override // io.agora.rtm.RtmClientListener
        public void onTokenPrivilegeWillExpire() {
        }

        public AnonymousClass1() {
        }

        @Override // io.agora.rtm.RtmClientListener
        public void onConnectionStateChanged(int i, int i2) {
            PetkitLog.d("RTM", "Connection state changed to " + i + "Reason: " + i2 + "\n");
            if (RtmManager.this.receiveCallBack != null) {
                RtmManager.this.receiveCallBack.receiveState(i);
            }
        }

        @Override // io.agora.rtm.RtmClientListener
        public void onTokenExpired() {
            RtmManager.this.playerLog("--onTokenExpired");
            if (RtmManager.this.isLoginRtm) {
                RtmManager.this.isLoginRtm = false;
                RtmManager.this.getRtmListInfo();
            }
        }

        @Override // io.agora.rtm.RtmClientListener
        public void onMessageReceived(RtmMessage rtmMessage, String str) {
            RtmManager.this.playerLog("Message received from " + str + " Message: " + new String(rtmMessage.getRawMessage()) + "\n");
            if (RtmManager.this.receiveCallBack != null) {
                RtmManager.this.receiveCallBack.receiveMsg(new String(rtmMessage.getRawMessage()));
            }
        }
    }

    public void rtmLogin(String str, String str2, RtmLoginListener rtmLoginListener) {
        if (this.isLoginRtm) {
            return;
        }
        playerLog("开始登录RTM--token: " + str + ";uid: " + str2);
        this.mRtmClient.login(str, str2, new ResultCallback<Void>() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager.2
            public final /* synthetic */ RtmLoginListener val$listener;
            public final /* synthetic */ String val$rtm_token;
            public final /* synthetic */ String val$uid;

            public AnonymousClass2(RtmLoginListener rtmLoginListener2, String str3, String str22) {
                rtmLoginListener = rtmLoginListener2;
                str = str3;
                str = str22;
            }

            @Override // io.agora.rtm.ResultCallback
            public void onSuccess(Void r2) {
                RtmManager.this.isLoginRtm = true;
                RtmManager.this.playerLog("RTM login success openMute");
                RtmManager.this.startDisposable60();
                RtmManager.this.retryTime = 0;
                rtmLoginListener.loginSuccess();
            }

            @Override // io.agora.rtm.ResultCallback
            public void onFailure(ErrorInfo errorInfo) {
                RtmManager.this.stopHeart60();
                RtmManager.this.isLoginRtm = false;
                RtmManager.this.playerLog("RTM login fail :" + errorInfo.getErrorDescription());
                if (RtmManager.this.retryTime < 3) {
                    RtmManager.access$608(RtmManager.this);
                    RtmManager.this.rtmLogin(str, str, rtmLoginListener);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$2 */
    public class AnonymousClass2 implements ResultCallback<Void> {
        public final /* synthetic */ RtmLoginListener val$listener;
        public final /* synthetic */ String val$rtm_token;
        public final /* synthetic */ String val$uid;

        public AnonymousClass2(RtmLoginListener rtmLoginListener2, String str3, String str22) {
            rtmLoginListener = rtmLoginListener2;
            str = str3;
            str = str22;
        }

        @Override // io.agora.rtm.ResultCallback
        public void onSuccess(Void r2) {
            RtmManager.this.isLoginRtm = true;
            RtmManager.this.playerLog("RTM login success openMute");
            RtmManager.this.startDisposable60();
            RtmManager.this.retryTime = 0;
            rtmLoginListener.loginSuccess();
        }

        @Override // io.agora.rtm.ResultCallback
        public void onFailure(ErrorInfo errorInfo) {
            RtmManager.this.stopHeart60();
            RtmManager.this.isLoginRtm = false;
            RtmManager.this.playerLog("RTM login fail :" + errorInfo.getErrorDescription());
            if (RtmManager.this.retryTime < 3) {
                RtmManager.access$608(RtmManager.this);
                RtmManager.this.rtmLogin(str, str, rtmLoginListener);
            }
        }
    }

    public void rtmLogout() {
        if (this.isLoginRtm) {
            playerLog("开始退出RTM");
            RtmClient rtmClient = this.mRtmClient;
            if (rtmClient != null) {
                rtmClient.logout(new ResultCallback<Void>() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager.3
                    public AnonymousClass3() {
                    }

                    @Override // io.agora.rtm.ResultCallback
                    public void onSuccess(Void r2) {
                        RtmManager.this.playerLog("RTM退出成功");
                        RtmManager.this.stopHeart60();
                        RtmManager.this.isLoginRtm = false;
                    }

                    @Override // io.agora.rtm.ResultCallback
                    public void onFailure(ErrorInfo errorInfo) {
                        RtmManager.this.playerLog("RTM退出错误，" + errorInfo.getErrorDescription());
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$3 */
    public class AnonymousClass3 implements ResultCallback<Void> {
        public AnonymousClass3() {
        }

        @Override // io.agora.rtm.ResultCallback
        public void onSuccess(Void r2) {
            RtmManager.this.playerLog("RTM退出成功");
            RtmManager.this.stopHeart60();
            RtmManager.this.isLoginRtm = false;
        }

        @Override // io.agora.rtm.ResultCallback
        public void onFailure(ErrorInfo errorInfo) {
            RtmManager.this.playerLog("RTM退出错误，" + errorInfo.getErrorDescription());
        }
    }

    public void rtmReLogin() {
        playerLog("RTM开始重新登录...");
        RtmClient rtmClient = this.mRtmClient;
        if (rtmClient != null) {
            rtmClient.logout(new AnonymousClass4());
        } else {
            initRtm();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$4 */
    public class AnonymousClass4 implements ResultCallback<Void> {
        public static /* synthetic */ void lambda$onFailure$1() {
        }

        public static /* synthetic */ void lambda$onSuccess$0() {
        }

        public AnonymousClass4() {
        }

        @Override // io.agora.rtm.ResultCallback
        public void onSuccess(Void r4) {
            RtmManager.this.playerLog("RTM退出成功");
            RtmManager.this.stopHeart60();
            RtmManager.this.isLoginRtm = false;
            RtmManager rtmManager = RtmManager.this;
            rtmManager.rtmLogin(rtmManager.rtmToken, RtmManager.this.uid, new RtmLoginListener() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager$4$$ExternalSyntheticLambda0
                @Override // com.petkit.android.media.video.rtmUtil.RtmLoginListener
                public final void loginSuccess() {
                    RtmManager.AnonymousClass4.lambda$onSuccess$0();
                }
            });
        }

        @Override // io.agora.rtm.ResultCallback
        public void onFailure(ErrorInfo errorInfo) {
            RtmManager.this.playerLog("RTM退出错误，" + errorInfo.getErrorDescription());
            RtmManager rtmManager = RtmManager.this;
            rtmManager.rtmLogin(rtmManager.rtmToken, RtmManager.this.uid, new RtmLoginListener() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager$4$$ExternalSyntheticLambda1
                @Override // com.petkit.android.media.video.rtmUtil.RtmLoginListener
                public final void loginSuccess() {
                    RtmManager.AnonymousClass4.lambda$onFailure$1();
                }
            });
        }
    }

    public void startLive(String str, int i, RtmMsgSendCallBack rtmMsgSendCallBack) {
        if (this.isLoginRtm) {
            RtmCommandInfo rtmCommandInfo = new RtmCommandInfo();
            rtmCommandInfo.setCmd("start_live");
            RtmCommandInfo.Param param = new RtmCommandInfo.Param();
            param.setIsSD(Integer.valueOf(i != 1 ? 0 : 1));
            rtmCommandInfo.setPayload(param);
            sendPeerMsg(str, new Gson().toJson(rtmCommandInfo), rtmMsgSendCallBack);
        }
    }

    public void stopLive(String str, RtmMsgSendCallBack rtmMsgSendCallBack) {
        if (this.isLoginRtm) {
            RtmCommandInfo rtmCommandInfo = new RtmCommandInfo();
            rtmCommandInfo.setCmd("stop_live");
            sendPeerMsg(str, new Gson().toJson(rtmCommandInfo), rtmMsgSendCallBack);
        }
    }

    public void reversalCamera(String str, int i, String str2, RtmMsgSendCallBack rtmMsgSendCallBack) {
        if (this.isLoginRtm) {
            RtmCommandInfo rtmCommandInfo = new RtmCommandInfo();
            rtmCommandInfo.setCmd("ptz_ctrl");
            RtmCommandInfo.Param param = new RtmCommandInfo.Param();
            param.setType(Integer.valueOf(i));
            if (str2.equals("left")) {
                param.setPtz_dir(-1);
            } else if (str2.equals(TtmlNode.RIGHT)) {
                param.setPtz_dir(1);
            } else {
                param.setPtz_dir(0);
                if (i == 0) {
                    return;
                }
            }
            rtmCommandInfo.setPayload(param);
            sendPeerMsg(str, new Gson().toJson(rtmCommandInfo), rtmMsgSendCallBack);
        }
    }

    public void startTalk(int i, String str, RtmMsgSendCallBack rtmMsgSendCallBack) {
        if (this.isLoginRtm) {
            RtmCommandInfo rtmCommandInfo = new RtmCommandInfo();
            rtmCommandInfo.setCmd("mic_ctrl");
            RtmCommandInfo.Param param = new RtmCommandInfo.Param();
            param.setStatus(Integer.valueOf(i));
            rtmCommandInfo.setPayload(param);
            sendPeerMsg(str, new Gson().toJson(rtmCommandInfo), rtmMsgSendCallBack);
        }
    }

    public void rtm60Heart() {
        List<RtmInfo> list;
        if (!this.isLoginRtm || (list = this.rtmInfoList) == null || list.size() < 1) {
            return;
        }
        RtmCommandInfo rtmCommandInfo = new RtmCommandInfo();
        rtmCommandInfo.setCmd("rtm_heartbeat");
        String json = new Gson().toJson(rtmCommandInfo);
        Iterator<RtmInfo> it = this.rtmInfoList.iterator();
        while (it.hasNext()) {
            sendPeerMsg(it.next().getDevRtmUserId(), json, new RtmMsgSendCallBack() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager.5
                @Override // com.petkit.android.media.video.rtmUtil.RtmMsgSendCallBack
                public void call(RtmMessage rtmMessage) {
                }

                @Override // com.petkit.android.media.video.rtmUtil.RtmMsgSendCallBack
                public void callError(int i) {
                }

                public AnonymousClass5() {
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$5 */
    public class AnonymousClass5 implements RtmMsgSendCallBack {
        @Override // com.petkit.android.media.video.rtmUtil.RtmMsgSendCallBack
        public void call(RtmMessage rtmMessage) {
        }

        @Override // com.petkit.android.media.video.rtmUtil.RtmMsgSendCallBack
        public void callError(int i) {
        }

        public AnonymousClass5() {
        }
    }

    public void rtm500Heart(String str, int i, RtmMsgSendCallBack rtmMsgSendCallBack) {
        if (this.isLoginRtm) {
            RtmCommandInfo rtmCommandInfo = new RtmCommandInfo();
            rtmCommandInfo.setCmd("live_heartbeat");
            RtmCommandInfo.Param param = new RtmCommandInfo.Param();
            param.setIsSD(Integer.valueOf(i != 1 ? 0 : 1));
            rtmCommandInfo.setPayload(param);
            sendPeerMsg(str, new Gson().toJson(rtmCommandInfo), rtmMsgSendCallBack);
        }
    }

    public void switchLiveQuality(int i, String str, RtmMsgSendCallBack rtmMsgSendCallBack) {
        if (this.isLoginRtm) {
            RtmCommandInfo rtmCommandInfo = new RtmCommandInfo();
            rtmCommandInfo.setCmd("quality");
            RtmCommandInfo.Param param = new RtmCommandInfo.Param();
            param.setIsSD(Integer.valueOf(i != 1 ? 0 : 1));
            rtmCommandInfo.setPayload(param);
            sendPeerMsg(str, new Gson().toJson(rtmCommandInfo), rtmMsgSendCallBack);
        }
    }

    public void switchD4HLiveQuality(int i, String str, RtmMsgSendCallBack rtmMsgSendCallBack) {
        String str2;
        if (this.isLoginRtm) {
            if (i == 1) {
                str2 = "video_SD";
            } else {
                str2 = "video_HD";
            }
            sendPeerMsg(str, str2, rtmMsgSendCallBack);
        }
    }

    public void sendPeerMsg(String str, String str2, RtmMsgSendCallBack rtmMsgSendCallBack) {
        if (this.mRtmClient == null) {
            return;
        }
        playerLog("发的消息是：" + str2);
        RtmClient rtmClient = this.mRtmClient;
        if (rtmClient != null) {
            RtmMessage rtmMessageCreateMessage = rtmClient.createMessage();
            rtmMessageCreateMessage.setText(str2);
            SendMessageOptions sendMessageOptions = new SendMessageOptions();
            RtmClient rtmClient2 = this.mRtmClient;
            if (rtmClient2 != null) {
                rtmClient2.sendMessageToPeer(str, rtmMessageCreateMessage, sendMessageOptions, new ResultCallback<Void>() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager.6
                    public final /* synthetic */ RtmMsgSendCallBack val$callBack;
                    public final /* synthetic */ RtmMessage val$message;
                    public final /* synthetic */ String val$peer_id;

                    public AnonymousClass6(String str3, RtmMessage rtmMessageCreateMessage2, RtmMsgSendCallBack rtmMsgSendCallBack2) {
                        str = str3;
                        rtmMessage = rtmMessageCreateMessage2;
                        rtmMsgSendCallBack = rtmMsgSendCallBack2;
                    }

                    @Override // io.agora.rtm.ResultCallback
                    public void onSuccess(Void r2) {
                        PetkitLog.d("RTM", "Message sent from " + RtmManager.this.uid + " To " + str + " ： " + rtmMessage.getText() + "\n");
                        RtmMsgSendCallBack rtmMsgSendCallBack2 = rtmMsgSendCallBack;
                        if (rtmMsgSendCallBack2 != null) {
                            rtmMsgSendCallBack2.call(rtmMessage);
                        }
                    }

                    @Override // io.agora.rtm.ResultCallback
                    public void onFailure(ErrorInfo errorInfo) {
                        PetkitLog.d("RTM", "Message fails to send from " + RtmManager.this.uid + " To " + str + " Error ： " + errorInfo + "\n");
                        RtmMsgSendCallBack rtmMsgSendCallBack2 = rtmMsgSendCallBack;
                        if (rtmMsgSendCallBack2 != null) {
                            rtmMsgSendCallBack2.callError(errorInfo.getErrorCode());
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$6 */
    public class AnonymousClass6 implements ResultCallback<Void> {
        public final /* synthetic */ RtmMsgSendCallBack val$callBack;
        public final /* synthetic */ RtmMessage val$message;
        public final /* synthetic */ String val$peer_id;

        public AnonymousClass6(String str3, RtmMessage rtmMessageCreateMessage2, RtmMsgSendCallBack rtmMsgSendCallBack2) {
            str = str3;
            rtmMessage = rtmMessageCreateMessage2;
            rtmMsgSendCallBack = rtmMsgSendCallBack2;
        }

        @Override // io.agora.rtm.ResultCallback
        public void onSuccess(Void r2) {
            PetkitLog.d("RTM", "Message sent from " + RtmManager.this.uid + " To " + str + " ： " + rtmMessage.getText() + "\n");
            RtmMsgSendCallBack rtmMsgSendCallBack2 = rtmMsgSendCallBack;
            if (rtmMsgSendCallBack2 != null) {
                rtmMsgSendCallBack2.call(rtmMessage);
            }
        }

        @Override // io.agora.rtm.ResultCallback
        public void onFailure(ErrorInfo errorInfo) {
            PetkitLog.d("RTM", "Message fails to send from " + RtmManager.this.uid + " To " + str + " Error ： " + errorInfo + "\n");
            RtmMsgSendCallBack rtmMsgSendCallBack2 = rtmMsgSendCallBack;
            if (rtmMsgSendCallBack2 != null) {
                rtmMsgSendCallBack2.callError(errorInfo.getErrorCode());
            }
        }
    }

    public RtmInfo getInfoByDeviceId(String str, int i) {
        if (this.rtmInfoList != null && str != null && !str.equals("")) {
            for (RtmInfo rtmInfo : this.rtmInfoList) {
                if (rtmInfo.getDeviceId().equals(str) && rtmInfo.getType() == i) {
                    return rtmInfo;
                }
            }
        }
        return null;
    }

    public String getUid() {
        String str = this.uid;
        return str == null ? "" : str;
    }

    public final void playerLog(String str) {
        PetkitLog.d("RTM", str);
        LogcatStorageHelper.addLog("RTM" + str);
    }

    public boolean isLoginRtm() {
        return this.isLoginRtm;
    }

    public void setReceiveCallBack(RtmMsgReceiveCallBack rtmMsgReceiveCallBack) {
        this.receiveCallBack = rtmMsgReceiveCallBack;
    }

    public final void startDisposable60() {
        stopHeart60();
        Observable.interval(0L, 60L, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager.7
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            public AnonymousClass7() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                RtmManager.this.disposable60 = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                RtmManager.this.rtm60Heart();
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$7 */
    public class AnonymousClass7 implements Observer<Long> {
        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public AnonymousClass7() {
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            RtmManager.this.disposable60 = disposable;
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            RtmManager.this.rtm60Heart();
        }
    }

    public final void stopHeart60() {
        Disposable disposable = this.disposable60;
        if (disposable != null) {
            disposable.dispose();
            this.disposable60 = null;
        }
    }

    public void getRtmListInfo() {
        playerLog("开始获取RTM登录信息");
        this.mRtmClient = null;
        this.isLoginRtm = false;
        AsyncHttpUtil.get(ApiTools.SAMPLE_API_RTM_START_RTM, new AsyncHttpRespHandler() { // from class: com.petkit.android.media.video.rtmUtil.RtmManager.8
            public AnonymousClass8() {
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                JSONObject object = JSON.parseObject(this.responseResult);
                RtmManager.this.playerLog("获取RTM登录信息为：" + this.responseResult);
                RtmListInfo rtmListInfo = (RtmListInfo) JSON.parseObject(object.getString("result"), RtmListInfo.class);
                if (rtmListInfo != null) {
                    RtmManager.this.setRtmListInfo(rtmListInfo);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                RtmManager.this.playerLog("获取RTM登录信息失败：" + th.getMessage());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.media.video.rtmUtil.RtmManager$8 */
    public class AnonymousClass8 extends AsyncHttpRespHandler {
        public AnonymousClass8() {
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            JSONObject object = JSON.parseObject(this.responseResult);
            RtmManager.this.playerLog("获取RTM登录信息为：" + this.responseResult);
            RtmListInfo rtmListInfo = (RtmListInfo) JSON.parseObject(object.getString("result"), RtmListInfo.class);
            if (rtmListInfo != null) {
                RtmManager.this.setRtmListInfo(rtmListInfo);
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            super.onFailure(i, headerArr, bArr, th);
            RtmManager.this.playerLog("获取RTM登录信息失败：" + th.getMessage());
        }
    }
}

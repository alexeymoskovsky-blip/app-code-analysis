package com.petkit.android.activities.base.im.iot;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.facebook.login.widget.ToolTipPopup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.im.model.IoTMessage;
import com.petkit.android.activities.home.mode.NewIotInfo;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/* JADX INFO: loaded from: classes3.dex */
public class NewIotManager {
    public static final int INITIAL_DELAY_MS = 300;
    public static volatile NewIotManager INSTANCE = null;
    public static final int MAX_RETRY_COUNT = 3;
    public static final String TAG = "NewIotManager";
    public Runnable iotRetryRunnable;
    public boolean isConnecting;
    public NewIotInfo.IotInfo mDeviceInfo;
    public IotConnectState mState;
    public MqttAsyncClient mqttClient;
    public int retryCount;
    public Runnable retryRunnable;
    public final Handler iotHandler = new Handler(Looper.getMainLooper());
    public final ExecutorService iotService = Executors.newSingleThreadExecutor();
    public final Set<ConnectionStateListener> stateListeners = new HashSet();
    public final Set<MessageListener> messageListeners = new HashSet();
    public int currentRetryCount = 0;
    public final Handler retryHandler = new Handler(Looper.getMainLooper());

    public interface ConnectionStateListener {
        void onStateChanged(IotConnectState iotConnectState, String str);
    }

    public enum IotConnectState {
        Connected,
        Connecting,
        ConnectFail,
        Disconnected,
        Error
    }

    public interface MessageListener {
        void onMessageReceived(String str, IoTMessage ioTMessage);
    }

    public final long calculateNextDelay(int i) {
        return (1 << (i - 1)) * 300;
    }

    public static /* synthetic */ int access$808(NewIotManager newIotManager) {
        int i = newIotManager.currentRetryCount;
        newIotManager.currentRetryCount = i + 1;
        return i;
    }

    public static NewIotManager getInstance() {
        if (INSTANCE == null) {
            synchronized (NewIotManager.class) {
                try {
                    if (INSTANCE == null) {
                        INSTANCE = new NewIotManager();
                    }
                } finally {
                }
            }
        }
        return INSTANCE;
    }

    public void start(String str) {
        log("自建IoT 开始建立连接：" + str, new int[0]);
        String sysMap = CommonUtils.getSysMap(CommonUtils.getAppContext(), Consts.SP_NEW_IOT_INFO, null);
        if (!TextUtils.isEmpty(sysMap)) {
            init((NewIotInfo.IotInfo) new Gson().fromJson(sysMap, NewIotInfo.IotInfo.class));
        } else {
            getIotInfo();
        }
    }

    public final void retry() {
        log("自建IoT 开始尝试重新连接", new int[0]);
        IotConnectState iotConnectState = this.mState;
        if (iotConnectState == IotConnectState.Connected || iotConnectState == IotConnectState.Connecting) {
            log("MQTT连接已经启动或正在连接中: " + this.mState, new int[0]);
            return;
        }
        int i = this.retryCount + 1;
        this.retryCount = i;
        if (i > 6) {
            IotRefreshManager.getInstance().setRefreshTimeInDisconnect(false);
            log("自建IoT尝试重新连接6次后仍然无法连接成功", new int[0]);
            return;
        }
        Runnable runnable = this.iotRetryRunnable;
        if (runnable != null) {
            this.iotHandler.removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$retry$0();
            }
        };
        this.iotRetryRunnable = runnable2;
        this.iotHandler.postDelayed(runnable2, 10000L);
    }

    public final /* synthetic */ void lambda$retry$0() {
        IotConnectState iotConnectState = this.mState;
        if (iotConnectState != IotConnectState.Connected && iotConnectState != IotConnectState.Connecting) {
            log("自建IoT执行重新连接", new int[0]);
            start("retry_重连");
        }
        this.iotRetryRunnable = null;
    }

    public final void init(final NewIotInfo.IotInfo iotInfo) {
        this.mDeviceInfo = iotInfo;
        IotConnectState iotConnectState = this.mState;
        if (iotConnectState == IotConnectState.Connected || iotConnectState == IotConnectState.Connecting) {
            log("MQTT连接已经启动或正在连接中: " + this.mState, new int[0]);
            return;
        }
        log("MQTT正在初始化：", new int[0]);
        this.iotService.execute(new Runnable() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$init$1(iotInfo);
            }
        });
    }

    public final /* synthetic */ void lambda$init$1(NewIotInfo.IotInfo iotInfo) {
        try {
            connectToServer(iotInfo);
        } catch (Exception e) {
            updateState(IotConnectState.Error, "连接失败: " + e.getMessage());
            this.isConnecting = false;
        }
    }

    public final void connectToServer(NewIotInfo.IotInfo iotInfo) throws MqttException {
        synchronized (this) {
            try {
                if (this.isConnecting) {
                    log("自建Iot已处于链接中，无需重连", new int[0]);
                    return;
                }
                this.isConnecting = true;
                this.mqttClient = new MqttAsyncClient("tcp://" + iotInfo.getMqttHost(), iotInfo.getDeviceName(), new MemoryPersistence());
                MqttConnectOptions mqttConnectOptions = getMqttConnectOptions(iotInfo);
                String strBuildWillTopic = buildWillTopic(iotInfo);
                if (strBuildWillTopic != null) {
                    mqttConnectOptions.setWill(strBuildWillTopic, "{\"status\":\"offline\"}".getBytes(), 0, false);
                }
                setupClientCallback();
                this.mqttClient.connect(mqttConnectOptions, null, new IMqttActionListener() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager.1
                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onSuccess(IMqttToken iMqttToken) {
                    }

                    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                    public void onFailure(IMqttToken iMqttToken, Throwable th) {
                        NewIotManager.this.isConnecting = false;
                        NewIotManager.this.updateState(IotConnectState.Disconnected, "连接失败: TCP握手失败");
                        NewIotManager.this.cancelRetry();
                        if (IotRefreshManager.getInstance().isIotConnect()) {
                            IotRefreshManager.getInstance().setRefreshTimeInDisconnect(false);
                        }
                    }
                });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @NonNull
    public final MqttConnectOptions getMqttConnectOptions(NewIotInfo.IotInfo iotInfo) {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setConnectionTimeout(30);
        mqttConnectOptions.setKeepAliveInterval(60);
        mqttConnectOptions.setMaxInflight(20);
        mqttConnectOptions.setMqttVersion(4);
        mqttConnectOptions.setMaxReconnectDelay(30000);
        if (iotInfo.getDeviceName() != null && iotInfo.getDeviceSecret() != null) {
            mqttConnectOptions.setUserName(iotInfo.getDeviceName());
            mqttConnectOptions.setPassword(iotInfo.getDeviceSecret().toCharArray());
        }
        return mqttConnectOptions;
    }

    public void reConnectIot() {
        start("从没网到网络恢复，开始重连IoT");
    }

    public final String buildWillTopic(NewIotInfo.IotInfo iotInfo) {
        if (iotInfo.getDeviceName() == null) {
            return null;
        }
        return String.format("/%s/%s/user/update", iotInfo.getProductKey(), iotInfo.getDeviceName());
    }

    public final void setupClientCallback() {
        MqttAsyncClient mqttAsyncClient = this.mqttClient;
        if (mqttAsyncClient == null) {
            return;
        }
        mqttAsyncClient.setCallback(new AnonymousClass2());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.base.im.iot.NewIotManager$2, reason: invalid class name */
    public class AnonymousClass2 implements MqttCallbackExtended {
        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        }

        public AnonymousClass2() {
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
        public void connectComplete(boolean z, String str) {
            NewIotManager.this.isConnecting = false;
            NewIotManager.this.updateState(IotConnectState.Connected, z ? "重新连接成功" : "连接成功");
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$connectComplete$0();
                }
            }, 1000L);
        }

        public final /* synthetic */ void lambda$connectComplete$0() {
            if (NewIotManager.this.canSubscribeImmediately()) {
                NewIotManager.this.log("连接成功，立即订阅", new int[0]);
                IotRefreshManager.getInstance().setRefreshTimeInDisconnect(true);
                NewIotManager.this.subscribeToTopics();
            } else {
                NewIotManager.this.log("连接成功，但订阅条件未就绪，启动重试机制", new int[0]);
                NewIotManager.this.startRetrySubscription();
            }
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void connectionLost(Throwable th) {
            NewIotManager.this.isConnecting = false;
            String message = th != null ? th.getMessage() : "未知原因";
            NewIotManager.this.updateState(IotConnectState.Disconnected, "连接断开: " + message);
            NewIotManager.this.cancelRetry();
            if (IotRefreshManager.getInstance().isIotConnect()) {
                IotRefreshManager.getInstance().setRefreshTimeInDisconnect(false);
            }
        }

        @Override // org.eclipse.paho.client.mqttv3.MqttCallback
        public void messageArrived(String str, MqttMessage mqttMessage) {
            try {
                String str2 = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
                NewIotManager.this.log("自建IoT_获取的数据：" + str2, 1);
                if (BaseApplication.isForeground()) {
                    NewIotManager.this.log("自建IoT_已经切换到前台，不再处理接收到的消息", 1);
                    return;
                }
                if (TextUtils.isEmpty(str2)) {
                    NewIotManager.this.log("自建IoT_获取消息为空", new int[0]);
                    return;
                }
                try {
                    NewIotManager.this.notifyMessageReceived(str, (IoTMessage) new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z").create().fromJson(str2, IoTMessage.class));
                } catch (Exception e) {
                    NewIotManager.this.log("自建IoT_解析透传消息失败：" + e.getMessage(), new int[0]);
                }
            } catch (Exception e2) {
                NewIotManager.this.log(SystemClock.elapsedRealtime() + "_处理消息失败: " + e2.getMessage(), new int[0]);
            }
        }
    }

    public final void subscribeToTopics() {
        MqttAsyncClient mqttAsyncClient = this.mqttClient;
        if (mqttAsyncClient == null) {
            log("无法订阅主题: 客户端为空", new int[0]);
            return;
        }
        if (!mqttAsyncClient.isConnected()) {
            log("无法订阅主题: 客户端未连接", new int[0]);
        } else if (this.mDeviceInfo == null) {
            log("无法订阅主题: 设备信息配置为空", new int[0]);
        } else {
            this.iotService.execute(new Runnable() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$subscribeToTopics$2();
                }
            });
        }
    }

    public final /* synthetic */ void lambda$subscribeToTopics$2() {
        try {
            if (this.mDeviceInfo.getDeviceName() == null) {
                log("设备deviceName为null, 无法订阅", new int[0]);
            } else {
                this.mqttClient.subscribe(String.format("/%s/%s/user/get", this.mDeviceInfo.getProductKey(), this.mDeviceInfo.getDeviceName()), 0);
            }
        } catch (MqttException e) {
            PetkitLog.d(TAG, "订阅主题失败: " + e.getMessage());
        }
    }

    public void stop() {
        MqttAsyncClient mqttAsyncClient = this.mqttClient;
        if (mqttAsyncClient == null) {
            updateState(IotConnectState.Disconnected, "客户端未初始化");
            return;
        }
        try {
            if (mqttAsyncClient.isConnected()) {
                this.mqttClient.disconnect();
                log("MQTT已断开连接", new int[0]);
            }
            this.mqttClient.close();
            this.mqttClient = null;
            updateState(IotConnectState.Disconnected, "连接已停止");
        } catch (MqttException e) {
            updateState(IotConnectState.Error, "停止MQTT连接失败: " + e.getMessage());
        }
        CommonUtils.removeSysMap(CommonUtils.getAppContext(), Consts.SP_NEW_IOT_INFO);
    }

    public final boolean canSubscribeImmediately() {
        MqttAsyncClient mqttAsyncClient = this.mqttClient;
        return (mqttAsyncClient == null || !mqttAsyncClient.isConnected() || this.mDeviceInfo == null) ? false : true;
    }

    public final void startRetrySubscription() {
        this.currentRetryCount = 0;
        cancelRetry();
        Runnable runnable = new Runnable() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager.3
            @Override // java.lang.Runnable
            public void run() {
                if (NewIotManager.this.currentRetryCount >= 3) {
                    NewIotManager.this.log("达到最大重试次数(3)，停止重试", new int[0]);
                    return;
                }
                NewIotManager.access$808(NewIotManager.this);
                NewIotManager.this.log("第" + NewIotManager.this.currentRetryCount + "次尝试订阅", new int[0]);
                if (NewIotManager.this.canSubscribeImmediately()) {
                    NewIotManager.this.log("条件满足，开始订阅", new int[0]);
                    NewIotManager.this.subscribeToTopics();
                    NewIotManager.this.cancelRetry();
                    return;
                }
                NewIotManager.this.log("条件不满足，准备下次重试", new int[0]);
                NewIotManager newIotManager = NewIotManager.this;
                long jCalculateNextDelay = newIotManager.calculateNextDelay(newIotManager.currentRetryCount);
                if (NewIotManager.this.currentRetryCount < 3) {
                    NewIotManager.this.retryHandler.postDelayed(this, jCalculateNextDelay);
                    return;
                }
                NewIotManager newIotManager2 = NewIotManager.this;
                StringBuilder sb = new StringBuilder();
                sb.append("无法订阅：客户端=");
                sb.append(NewIotManager.this.mqttClient != null);
                sb.append(", 连接=");
                sb.append(NewIotManager.this.mqttClient != null && NewIotManager.this.mqttClient.isConnected());
                sb.append(", 设备信息=");
                sb.append(NewIotManager.this.mDeviceInfo != null);
                newIotManager2.log(sb.toString(), new int[0]);
                NewIotManager.this.resetMqtt();
            }
        };
        this.retryRunnable = runnable;
        this.retryHandler.postDelayed(runnable, 300L);
    }

    public final void cancelRetry() {
        Runnable runnable = this.retryRunnable;
        if (runnable != null) {
            this.retryHandler.removeCallbacks(runnable);
            this.retryRunnable = null;
        }
    }

    public final void resetMqtt() {
        cancelRetry();
        this.retryCount = 0;
        synchronized (this) {
            stop();
            log("多次订阅失败，6秒后，重新建立", new int[0]);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$resetMqtt$3();
                }
            }, ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
        }
    }

    public final /* synthetic */ void lambda$resetMqtt$3() {
        start("多次订阅失败，重新就建立mqtt");
    }

    public final void updateState(IotConnectState iotConnectState, String str) {
        this.mState = iotConnectState;
        log(str, new int[0]);
        Iterator<ConnectionStateListener> it = this.stateListeners.iterator();
        while (it.hasNext()) {
            it.next().onStateChanged(iotConnectState, str);
        }
    }

    public final void notifyMessageReceived(String str, IoTMessage ioTMessage) {
        Iterator<MessageListener> it = this.messageListeners.iterator();
        while (it.hasNext()) {
            it.next().onMessageReceived(str, ioTMessage);
        }
    }

    public void addConnectionStateListener(ConnectionStateListener connectionStateListener) {
        if (connectionStateListener != null) {
            this.stateListeners.add(connectionStateListener);
        }
    }

    public void removeConnectionStateListener(ConnectionStateListener connectionStateListener) {
        this.stateListeners.remove(connectionStateListener);
    }

    public void addMessageListener(MessageListener messageListener) {
        if (messageListener != null) {
            this.messageListeners.add(messageListener);
        }
    }

    public void removeMessageListener(MessageListener messageListener) {
        this.messageListeners.remove(messageListener);
    }

    public void clearAllListeners() {
        this.stateListeners.clear();
        this.messageListeners.clear();
    }

    public void destroy() {
        this.isConnecting = false;
        clearAllListeners();
        stop();
        this.iotService.shutdown();
        INSTANCE = null;
    }

    public final void getIotInfo() {
        WebModelRepository.getInstance().getNewIotInfo(new PetkitCallback<NewIotInfo>() { // from class: com.petkit.android.activities.base.im.iot.NewIotManager.4
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(NewIotInfo newIotInfo) {
                if (newIotInfo == null || newIotInfo.getPetkit() == null) {
                    NewIotManager.this.log("获取IoT信息失败: info为null", new int[0]);
                    NewIotManager.this.retry();
                    return;
                }
                String json = new Gson().toJson(newIotInfo.getPetkit());
                CommonUtils.addSysMap(Consts.SP_NEW_IOT_INFO, json);
                NewIotManager.this.log("获取IoT信息成功：" + json, new int[0]);
                NewIotManager.this.init(newIotInfo.getPetkit());
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                NewIotManager.this.log("获取IoT信息失败", new int[0]);
                NewIotManager.this.retry();
            }
        });
    }

    public final void log(String str, int... iArr) {
        PetkitLog.d(TAG, str);
        if (iArr.length > 0) {
            LogcatStorageHelper.addLog(str);
        }
    }
}

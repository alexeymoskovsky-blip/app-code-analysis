package com.qiyukf.uikit.session.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.database.f;
import com.qiyukf.nimlib.h;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.nimlib.session.k;
import com.qiyukf.uikit.common.fragment.TFragment;
import com.qiyukf.uikit.common.media.a.a;
import com.qiyukf.uikit.session.helper.InquiryFormHelper;
import com.qiyukf.uikit.session.module.a;
import com.qiyukf.uikit.session.module.b;
import com.qiyukf.uikit.session.module.input.InputPanel;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.h.a.a.a.s;
import com.qiyukf.unicorn.h.a.f.ac;
import com.qiyukf.unicorn.n.e;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes6.dex */
public class MessageFragment extends TFragment implements b {
    public static final int LEAVE_MSG_ACTIVITY_REQUEST_CODE = 16;
    protected static final String TAG = "MessageFragment";
    protected a container;
    private com.qiyukf.uikit.session.a customization;
    protected String exchange;
    protected FrameLayout flAnnouncementParent;
    protected InputPanel inputPanel;
    protected InquiryFormHelper inquiryFormHelper;
    protected LinearLayout llMessageFragmentAd;
    protected com.qiyukf.uikit.session.module.a.a messageListPanel;
    private Sensor myProximitySensor;
    private SensorManager mySensorManager;
    protected SensorEventListener proximitySensorEventListener;
    protected View rootView;
    private View screenLockLayout;
    protected SessionTypeEnum sessionType;
    protected TextView tipsMessageLabel;
    protected String title;
    protected TextView tvAnnouncementText;
    protected ImageView ysfIvCloseAnnouncement;
    protected boolean isLeaveMsgBack = false;
    private c.a onInitListener = new c.a() { // from class: com.qiyukf.uikit.session.fragment.MessageFragment.1
        @Override // com.qiyukf.unicorn.c.a
        public void onInit() {
            MessageFragment.this.registerObservers(true);
            MessageFragment.this.initModules();
            if (MessageFragment.this.isResumed()) {
                MessageFragment messageFragment = MessageFragment.this;
                messageFragment.setChattingAccount(messageFragment.exchange, messageFragment.sessionType);
            }
        }
    };
    private final a.InterfaceC0166a playAudioListener = new a.InterfaceC0166a() { // from class: com.qiyukf.uikit.session.fragment.MessageFragment.3
        @Override // com.qiyukf.uikit.common.media.a.a.InterfaceC0166a
        public void updatePlayingProgress(com.qiyukf.uikit.common.media.a.b bVar, long j) {
        }

        @Override // com.qiyukf.uikit.common.media.a.a.InterfaceC0166a
        public void onAudioControllerReady(com.qiyukf.uikit.common.media.a.b bVar) {
            MessageFragment.this.initSensors();
            MessageFragment.this.getActivity().getWindow().setFlags(128, 128);
            MessageFragment.this.getActivity();
            if (com.qiyukf.uikit.session.b.b.h().b() == 0) {
                MessageFragment.this.messageListPanel.b(R.string.ysf_audio_is_playing_by_earphone);
            }
        }

        @Override // com.qiyukf.uikit.common.media.a.a.InterfaceC0166a
        public void onEndPlay(com.qiyukf.uikit.common.media.a.b bVar) {
            e.b().post(new Runnable() { // from class: com.qiyukf.uikit.session.fragment.MessageFragment.3.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MessageFragment.this.getActivity() != null) {
                        MessageFragment.this.getActivity().getWindow().setFlags(0, 128);
                    }
                    MessageFragment.this.exitFromFullscreen();
                }
            });
        }
    };
    private Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() { // from class: com.qiyukf.uikit.session.fragment.MessageFragment.4
        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(List<IMMessage> list) {
            if (list == null || list.isEmpty()) {
                AbsUnicornLog.i(MessageFragment.TAG, "onEvent: messages is empty");
            } else {
                MessageFragment.this.messageListPanel.a(list);
                MessageFragment.this.onReceiveMessage(list);
            }
        }
    };

    @Override // com.qiyukf.uikit.session.module.b
    public boolean isAllowSendMessage(boolean z) {
        return true;
    }

    public void onReceiveMessage(List<IMMessage> list) {
    }

    @Override // com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.ysf_message_fragment, viewGroup, false);
        this.rootView = viewInflate;
        return viewInflate;
    }

    @Override // com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (!Unicorn.isInit()) {
            Unicorn.initSdk();
            AbsUnicornLog.i(TAG, "onCreate, !SDKCache.hasServiceInited()");
        }
        findViews();
        parseIntent();
        if (c.b()) {
            this.onInitListener.onInit();
        } else {
            c.a(this.onInitListener);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.isLeaveMsgBack) {
            return;
        }
        com.qiyukf.uikit.session.module.a.a aVar = this.messageListPanel;
        if (aVar != null) {
            aVar.d();
        }
        InputPanel inputPanel = this.inputPanel;
        if (inputPanel != null) {
            inputPanel.onResume();
        }
        com.qiyukf.uikit.session.module.a.a aVar2 = this.messageListPanel;
        if (aVar2 != null) {
            aVar2.f();
        }
        getActivity();
        com.qiyukf.uikit.session.b.b.h().a(this.playAudioListener);
        if (c.b()) {
            setChattingAccount(this.exchange, this.sessionType);
            if (c.h().p(this.container.c) != null && c.h().g(this.exchange) == 0) {
                ac acVar = new ac();
                acVar.a(String.valueOf(c.h().d(this.exchange)));
                com.qiyukf.unicorn.k.c.a(acVar, this.exchange);
            }
            uploadHistorySessionUnread();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        InputPanel inputPanel = this.inputPanel;
        if (inputPanel != null) {
            inputPanel.onPause();
        }
        com.qiyukf.uikit.session.module.a.a aVar = this.messageListPanel;
        if (aVar != null) {
            aVar.e();
        }
        getActivity();
        com.qiyukf.uikit.session.b.b.h().e();
        getActivity();
        com.qiyukf.uikit.session.b.b.h().b(this.playAudioListener);
        setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
    }

    @Override // com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        com.qiyukf.uikit.session.module.a.a aVar = this.messageListPanel;
        if (aVar != null) {
            aVar.c();
        }
        unregisterProximitySensorListener();
        if (c.b()) {
            registerObservers(false);
        }
        c.b(this.onInitListener);
        super.onDestroy();
    }

    @Override // com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        InputPanel inputPanel = this.inputPanel;
        if (inputPanel != null) {
            inputPanel.onConfigurationChanged(configuration);
        }
        com.qiyukf.uikit.session.module.a.a aVar = this.messageListPanel;
        if (aVar != null) {
            aVar.a(configuration);
        }
    }

    public void setExchange(String str) {
        this.exchange = str;
        this.container.c = str;
        setChattingAccount(str, this.sessionType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setChattingAccount(String str, SessionTypeEnum sessionTypeEnum) {
        if (f.a().c()) {
            ((MsgService) NIMClient.getService(MsgService.class)).setChattingAccount(str, sessionTypeEnum);
        } else {
            h.a(k.a(str, sessionTypeEnum.getValue()));
        }
    }

    public void reloadMessage() {
        this.messageListPanel.a(this.container);
    }

    private void findViews() {
        TextView textView = (TextView) this.rootView.findViewById(R.id.message_tips_label);
        this.tipsMessageLabel = textView;
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        this.llMessageFragmentAd = (LinearLayout) this.rootView.findViewById(R.id.ll_message_fragment_ad);
        this.flAnnouncementParent = (FrameLayout) this.rootView.findViewById(R.id.ysf_fl_announcement_parent);
        this.ysfIvCloseAnnouncement = (ImageView) this.rootView.findViewById(R.id.ysf_iv_close_announcement);
        this.tvAnnouncementText = (TextView) this.rootView.findViewById(R.id.ysf_tv_announcement_text);
    }

    private void parseIntent() {
        String string = getArguments().getString("account");
        this.exchange = string;
        if (TextUtils.isEmpty(string)) {
            this.exchange = com.qiyukf.unicorn.d.c.c();
        }
        if (TextUtils.isEmpty(this.exchange) && com.qiyukf.nimlib.c.n() != null) {
            this.exchange = com.qiyukf.unicorn.k.c.a();
        }
        this.sessionType = (SessionTypeEnum) getArguments().getSerializable("type");
        this.customization = (com.qiyukf.uikit.session.a) getArguments().getSerializable("customization");
        this.container = new com.qiyukf.uikit.session.module.a(this, this.exchange, this.sessionType, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initModules() {
        com.qiyukf.uikit.session.module.a.a aVar = this.messageListPanel;
        if (aVar == null) {
            this.messageListPanel = new com.qiyukf.uikit.session.module.a.a(this.container, this.rootView);
        } else {
            aVar.a(this.container);
        }
        InputPanel inputPanel = this.inputPanel;
        if (inputPanel == null) {
            InputPanel inputPanel2 = new InputPanel(this.container, this.rootView, this.customization);
            this.inputPanel = inputPanel2;
            inputPanel2.setTitle(this.title);
        } else {
            inputPanel.reload(this.container);
            this.inputPanel.setTitle(this.title);
        }
        com.qiyukf.uikit.session.a aVar2 = this.customization;
        if (aVar2 != null) {
            int i = aVar2.c;
            if (i != 0) {
                this.inputPanel.setMoreBgColor(i);
            } else if (com.qiyukf.unicorn.m.a.a().c()) {
                this.inputPanel.setMoreBgColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            } else {
                this.inputPanel.setMoreBgColor(0);
            }
            if (com.qiyukf.uikit.a.c(this.customization.a) || this.customization.b != 0) {
                com.qiyukf.uikit.session.module.a.a aVar3 = this.messageListPanel;
                com.qiyukf.uikit.session.a aVar4 = this.customization;
                aVar3.a(aVar4.a, aVar4.b);
                return;
            } else {
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    setChattingBackground();
                    return;
                }
                return;
            }
        }
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.inputPanel.setMoreBgColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            setChattingBackground();
        } else {
            this.inputPanel.setMoreBgColor(0);
            this.messageListPanel.a((String) null, 0);
        }
    }

    private void setChattingBackground() {
        if (com.qiyukf.unicorn.m.a.a().b().q() == 1) {
            this.messageListPanel.a((String) null, Color.parseColor(com.qiyukf.unicorn.m.a.a().b().r()));
        } else if (com.qiyukf.unicorn.m.a.a().b().q() == 2 && com.qiyukf.unicorn.m.a.a().b().j() != null && !TextUtils.isEmpty(com.qiyukf.unicorn.m.a.a().b().j().a())) {
            this.messageListPanel.a(com.qiyukf.unicorn.m.a.a().b().j().a(), 0);
        } else {
            this.messageListPanel.a((String) null, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSensors() {
        initProximitySensor();
        if (com.qiyukf.unicorn.d.c.l()) {
            getActivity().setVolumeControlStream(0);
        } else {
            getActivity().setVolumeControlStream(3);
        }
    }

    private void initProximitySensor() {
        if (this.mySensorManager != null) {
            return;
        }
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService("sensor");
        this.mySensorManager = sensorManager;
        this.myProximitySensor = sensorManager.getDefaultSensor(8);
        this.proximitySensorEventListener = new SensorEventListener() { // from class: com.qiyukf.uikit.session.fragment.MessageFragment.2
            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == 8) {
                    float f = sensorEvent.values[0];
                    if (f >= 5.0f || f >= sensorEvent.sensor.getMaximumRange()) {
                        MessageFragment.this.exitFromFullscreen();
                        return;
                    }
                    MessageFragment.this.getActivity();
                    if (com.qiyukf.uikit.session.b.b.h().d()) {
                        MessageFragment.this.gotoFullscreen();
                    }
                }
            }
        };
        registerProximitySensorListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gotoFullscreen() {
        if (getActivity() == null) {
            return;
        }
        getActivity().getWindow().setFlags(32768, 32768);
        getActivity().getWindow().setFlags(1024, 1024);
        if (this.screenLockLayout == null) {
            View.inflate(getActivity(), R.layout.ysf_screen_lock_layout, (ViewGroup) getActivity().getWindow().getDecorView());
            this.screenLockLayout = getActivity().findViewById(R.id.screen_lock_layout);
        }
        this.screenLockLayout.setVisibility(0);
        if (com.qiyukf.unicorn.d.c.l()) {
            return;
        }
        getActivity();
        if (com.qiyukf.uikit.session.b.b.h().f()) {
            getActivity().setVolumeControlStream(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitFromFullscreen() {
        View view;
        if (getActivity() == null || (view = this.screenLockLayout) == null || view.getVisibility() == 8) {
            return;
        }
        boolean zG = this.messageListPanel.g();
        getActivity().getWindow().setFlags(0, 32768);
        getActivity().getWindow().clearFlags(1024);
        setNavigationVisibility(true);
        View view2 = this.screenLockLayout;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        if (zG) {
            this.messageListPanel.h();
        }
        if (com.qiyukf.unicorn.d.c.l()) {
            return;
        }
        getActivity();
        if (com.qiyukf.uikit.session.b.b.h().g()) {
            getActivity().setVolumeControlStream(3);
            this.messageListPanel.b(R.string.ysf_audio_switch_to_speaker);
        }
    }

    @TargetApi(14)
    private void setNavigationVisibility(boolean z) {
        View decorView = getActivity().getWindow().getDecorView();
        if (z) {
            decorView.setSystemUiVisibility(0);
        } else {
            decorView.setSystemUiVisibility(2);
        }
    }

    private void registerProximitySensorListener() {
        SensorManager sensorManager;
        SensorEventListener sensorEventListener;
        Sensor sensor = this.myProximitySensor;
        if (sensor == null || (sensorManager = this.mySensorManager) == null || (sensorEventListener = this.proximitySensorEventListener) == null) {
            return;
        }
        sensorManager.registerListener(sensorEventListener, sensor, 3);
    }

    private void unregisterProximitySensorListener() {
        SensorManager sensorManager;
        SensorEventListener sensorEventListener;
        if (this.myProximitySensor == null || (sensorManager = this.mySensorManager) == null || (sensorEventListener = this.proximitySensorEventListener) == null) {
            return;
        }
        sensorManager.unregisterListener(sensorEventListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerObservers(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeReceiveMessage(this.incomingMessageObserver, z);
        com.qiyukf.unicorn.k.c.a(z ? this : null);
    }

    @Override // com.qiyukf.uikit.session.module.b
    public boolean sendMessage(final IMMessage iMMessage, boolean z) {
        IMMessage iMMessageQueryMessageByUuid;
        if (!isAllowSendMessage(true)) {
            if (!z) {
                return false;
            }
            updateMessageFail(iMMessage);
            return false;
        }
        if (!TextUtils.isEmpty(com.qiyukf.unicorn.d.c.e(c.h().d(this.exchange))) && (iMMessageQueryMessageByUuid = MsgDBHelper.queryMessageByUuid(com.qiyukf.unicorn.d.c.e(c.h().d(this.exchange)))) != null && iMMessageQueryMessageByUuid.getAttachment() != null && (iMMessageQueryMessageByUuid.getAttachment() instanceof s)) {
            s sVar = (s) iMMessageQueryMessageByUuid.getAttachment();
            sVar.h();
            sVar.m();
            com.qiyukf.unicorn.b.a aVar = (com.qiyukf.unicorn.b.a) sVar.a();
            Boolean bool = Boolean.TRUE;
            aVar.a("isAlreadyShowQuickEntry", bool);
            aVar.a("CHECK_BOX_IS_DONE", bool);
            ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessageQueryMessageByUuid, true);
            com.qiyukf.unicorn.d.c.a(c.h().d(this.exchange), "");
        }
        setLocalMessageSessionId(iMMessage);
        ((YsfService) NIMClient.getService(YsfService.class)).sendMessage(iMMessage, z).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.uikit.session.fragment.MessageFragment.5
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public void onResult(int i, Void r2, Throwable th) {
                if (i == 1) {
                    MessageFragment.updateMessageFail(iMMessage);
                }
            }
        });
        if (!z) {
            this.messageListPanel.a(iMMessage);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateMessageFail(IMMessage iMMessage) {
        iMMessage.setStatus(MsgStatusEnum.fail);
        ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage, true);
    }

    private void setLocalMessageSessionId(IMMessage iMMessage) {
        Map<String, Object> localExtension = iMMessage.getLocalExtension();
        if (localExtension == null) {
            localExtension = new HashMap<>();
        }
        localExtension.put("sessionid", Long.valueOf(c.h().d(this.exchange)));
        iMMessage.setLocalExtension(localExtension);
    }

    @Override // com.qiyukf.uikit.session.module.b
    public void sendMessageToInputPanel(IMMessage iMMessage) {
        if (iMMessage == null) {
            return;
        }
        this.inputPanel.sendMessageToInputPanel(iMMessage.getContent());
    }

    @Override // com.qiyukf.uikit.session.module.b
    public void shouldCollapseInputPanel() {
        this.inputPanel.collapse();
    }

    @Override // com.qiyukf.uikit.session.module.b
    public boolean isLongClickEnabled() {
        return !this.inputPanel.isRecording();
    }

    @Override // com.qiyukf.uikit.session.module.b
    public void saveMessageToLocal(IMMessage iMMessage, boolean z, boolean z2) {
        if (isAllowSendMessage(true)) {
            if (z2) {
                ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessage, z);
            } else {
                this.messageListPanel.a(iMMessage);
            }
        }
    }

    @Override // com.qiyukf.uikit.session.module.b
    public void onMessageQuote(IMMessage iMMessage) {
        if (isAllowSendMessage(true)) {
            this.inputPanel.onMessageQuote(iMMessage);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 16) {
            if (i2 == 20 && getActivity() != null) {
                getActivity().finish();
                return;
            } else {
                this.isLeaveMsgBack = true;
                return;
            }
        }
        InputPanel inputPanel = this.inputPanel;
        if (inputPanel != null) {
            inputPanel.onActivityResult(i, i2, intent);
        }
        com.qiyukf.uikit.session.module.a.a aVar = this.messageListPanel;
        if (aVar != null) {
            aVar.a(i, i2, intent);
        }
    }

    private void uploadHistorySessionUnread() {
        List<String> listB = com.qiyukf.unicorn.d.c.B("UNREAD_SESSIONID_KEY");
        if (listB.size() == 0) {
            return;
        }
        for (String str : listB) {
            ac acVar = new ac();
            acVar.a(str);
            com.qiyukf.unicorn.k.c.a(acVar, this.exchange);
        }
        com.qiyukf.unicorn.d.c.A("UNREAD_SESSIONID_KEY");
    }
}

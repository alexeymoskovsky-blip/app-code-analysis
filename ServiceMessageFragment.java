package com.qiyukf.unicorn.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import com.qiyukf.basemodule.ServiceHelper;
import com.qiyukf.basemodule.interfaces.VideoService;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.h;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.n.p;
import com.qiyukf.nimlib.n.y;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.StatusCode;
import com.qiyukf.nimlib.sdk.auth.AuthService;
import com.qiyukf.nimlib.sdk.auth.AuthServiceObserver;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.uikit.session.fragment.MessageFragment;
import com.qiyukf.uikit.session.helper.InquiryFormHelper;
import com.qiyukf.uikit.session.module.a.b;
import com.qiyukf.uikit.session.module.input.InputPanel;
import com.qiyukf.uikit.session.module.input.RobotStreamHelper;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.CardDetail;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.api.OnMixSdkReconnectClickListener;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.api.customization.input.ActionPanelOptions;
import com.qiyukf.unicorn.api.customization.input.InputPanelOptions;
import com.qiyukf.unicorn.api.customization.page_ad.IMPageViewConfig;
import com.qiyukf.unicorn.api.event.EventService;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.event.entry.ConnectionStaffResultEntry;
import com.qiyukf.unicorn.api.lifecycle.SessionLifeCycleListener;
import com.qiyukf.unicorn.api.lifecycle.SessionLifeCycleOptions;
import com.qiyukf.unicorn.api.msg.MessageService;
import com.qiyukf.unicorn.api.msg.attachment.ProductAttachment;
import com.qiyukf.unicorn.api.pop.OnShopEventListener;
import com.qiyukf.unicorn.api.pop.SessionListEntrance;
import com.qiyukf.unicorn.api.pop.ShopEntrance;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.d;
import com.qiyukf.unicorn.f.u;
import com.qiyukf.unicorn.h.a.d.aa;
import com.qiyukf.unicorn.h.a.d.ab;
import com.qiyukf.unicorn.h.a.d.ac;
import com.qiyukf.unicorn.h.a.d.ah;
import com.qiyukf.unicorn.h.a.d.ai;
import com.qiyukf.unicorn.h.a.d.al;
import com.qiyukf.unicorn.h.a.d.am;
import com.qiyukf.unicorn.h.a.d.f;
import com.qiyukf.unicorn.h.a.d.l;
import com.qiyukf.unicorn.h.a.d.n;
import com.qiyukf.unicorn.h.a.d.t;
import com.qiyukf.unicorn.h.a.d.x;
import com.qiyukf.unicorn.k.d;
import com.qiyukf.unicorn.n.g;
import com.qiyukf.unicorn.n.z;
import com.qiyukf.unicorn.ui.activity.ServiceMessageActivity;
import com.qiyukf.unicorn.ui.c.a;
import com.qiyukf.unicorn.ui.c.b;
import com.qiyukf.unicorn.ui.evaluate.e;
import com.qiyukf.unicorn.widget.dialog.ProgressDialog;
import com.qiyukf.unicorn.widget.dialog.UnicornDialog;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes6.dex */
public class ServiceMessageFragment extends MessageFragment {
    private static final int STATE_ERROR = -1;
    private static final int STATE_HUMAN = 1;
    private static final int STATE_INIT = 0;
    private static final int STATE_IN_QUEUE = 4;
    private static final int STATE_KICK_OUT = -3;
    private static final int STATE_NO_LOGIN = -2;
    private static final int STATE_NO_STAFF = 3;
    private static final int STATE_NO_STAFF_SILENT = 9;
    private static final int STATE_PRODUCT_INVALID = 5;
    private static final int STATE_REQUESTING = 2;
    private static final int STATE_ROBOT = 6;
    private static final int STATE_ROBOT_IN_QUEUE = 10;
    private static final int STATE_SESSION_CLOSE = 8;
    private static final int STATE_STAFF_GROUP = 7;
    public static final int STATE_VIDEO_ERROR = -4;
    protected static final String TAG = "ServiceMessageFragment";
    private static long lastSessionId;
    private ViewGroup actionMenuContainer;
    private a actionMenuPanel;
    private com.qiyukf.unicorn.ui.d.a actionScrollPanel;
    private e evaluator;
    private d lastCategory;
    private SessionLifeCycleOptions lifeCycleOptions;
    private com.qiyukf.unicorn.ui.evaluate.a.a robotEvaluator;
    private SessionLifeCycleListener sessionLifeCycleListener;
    private ConsultSource source;
    private int state = 0;
    private boolean hasSentProductMsg = false;
    private boolean isLogging = false;
    private boolean isOpenEvaluator = false;
    private boolean isOpenRobotEvaluator = false;
    private boolean statusChange = false;
    private c.a onInitListener = new c.a() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.1
        @Override // com.qiyukf.unicorn.c.a
        public void onInit() {
            ServiceMessageFragment.this.isOpenEvaluator = c.h().w(((MessageFragment) ServiceMessageFragment.this).exchange).booleanValue();
            if (c.h().e(((MessageFragment) ServiceMessageFragment.this).exchange) != null && c.h().e(((MessageFragment) ServiceMessageFragment.this).exchange).g == 1) {
                ServiceMessageFragment.this.isOpenRobotEvaluator = c.h().x(((MessageFragment) ServiceMessageFragment.this).exchange);
            }
            c.h().a(ServiceMessageFragment.this.source);
            ServiceMessageFragment.this.registerObservers(true);
            ServiceMessageFragment.this.initState();
            ServiceMessageFragment.this.registerViewHolderEvent();
            ServiceMessageFragment.this.checkAndRequest();
        }
    };
    private Observer<CustomNotification> commandObserver = new Observer<CustomNotification>() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.14
        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(CustomNotification customNotification) {
            if (TextUtils.equals(((MessageFragment) ServiceMessageFragment.this).exchange, customNotification.getSessionId()) && customNotification.getSessionType() == SessionTypeEnum.Ysf) {
                ServiceMessageFragment.this.showCommandMessage(customNotification);
            }
        }
    };
    private Observer<StatusCode> statusObserver = new Observer<StatusCode>() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.15
        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(StatusCode statusCode) {
            if (ServiceMessageFragment.this.state == 7) {
                c.h();
                if (!com.qiyukf.unicorn.k.d.l(((MessageFragment) ServiceMessageFragment.this).exchange)) {
                    return;
                }
            }
            AbsUnicornLog.i(ServiceMessageFragment.TAG, "sdk status change status= " + statusCode + " + isLogging: " + ServiceMessageFragment.this.isLogging + " + state: " + ServiceMessageFragment.this.state);
            if (statusCode == StatusCode.LOGINED) {
                if (ServiceMessageFragment.this.isLogging) {
                    ServiceMessageFragment.this.onFirstLogin();
                    return;
                }
                if (!ServiceMessageFragment.this.isResumed() || ServiceMessageFragment.this.state != -1) {
                    if (ServiceMessageFragment.this.state == -1) {
                        ServiceMessageFragment.this.statusChange = true;
                        return;
                    }
                    return;
                } else {
                    AbsUnicornLog.i(ServiceMessageFragment.TAG, "ServiceMessageFragment requestStaff 4");
                    ServiceMessageFragment.this.requestStaff(0, false);
                    return;
                }
            }
            if (statusCode.wontAutoLoginForever()) {
                ServiceMessageFragment.this.state = -3;
            } else {
                if (!statusCode.shouldReLogin() && !statusCode.wontAutoLogin()) {
                    return;
                }
                if (ServiceMessageFragment.this.isLogging) {
                    ServiceMessageFragment.this.state = -2;
                } else if (p.b(ServiceMessageFragment.this.getContext()) && ServiceMessageFragment.this.isCanSendMessage()) {
                    return;
                } else {
                    ServiceMessageFragment.this.state = -1;
                }
            }
            ServiceMessageFragment.this.onStatusChange();
        }
    };

    public void setArguments(String str, ConsultSource consultSource, ViewGroup viewGroup) {
        ActionPanelOptions actionPanelOptions;
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
            setArguments(arguments);
        }
        this.title = str;
        this.actionMenuContainer = viewGroup;
        arguments.putString("title", str);
        if (consultSource != null) {
            arguments.putSerializable("source", consultSource);
            if (!TextUtils.isEmpty(consultSource.shopId)) {
                arguments.putString("account", consultSource.shopId.toLowerCase());
            }
            SessionLifeCycleOptions sessionLifeCycleOptions = consultSource.sessionLifeCycleOptions;
            if (sessionLifeCycleOptions != null) {
                this.sessionLifeCycleListener = sessionLifeCycleOptions.getSessionLifeCycleListener();
            }
        }
        arguments.putSerializable("type", SessionTypeEnum.Ysf);
        UICustomization uICustomization = getUICustomization();
        InputPanelOptions inputPanelOptions = getInputPanelOptions();
        com.qiyukf.uikit.session.a aVar = new com.qiyukf.uikit.session.a();
        if (uICustomization != null) {
            aVar.a = uICustomization.msgBackgroundUri;
            aVar.b = uICustomization.msgBackgroundColor;
        }
        if (inputPanelOptions != null) {
            int i = inputPanelOptions.emojiIconResId;
            if (i != 0) {
                aVar.e = i;
            }
            int i2 = inputPanelOptions.photoIconResId;
            if (i2 != 0) {
                aVar.f = i2;
            }
            int i3 = inputPanelOptions.voiceIconResId;
            if (i3 != 0) {
                aVar.d = i3;
            }
            int i4 = inputPanelOptions.moreIconResId;
            if (i4 != 0) {
                aVar.g = i4;
            }
            boolean z = inputPanelOptions.showActionPanel;
            aVar.h = z;
            if (z && (actionPanelOptions = inputPanelOptions.actionPanelOptions) != null) {
                aVar.c = actionPanelOptions.backgroundColor;
            }
        }
        if (uICustomization == null && inputPanelOptions == null) {
            return;
        }
        arguments.putSerializable("customization", aVar);
    }

    @Override // com.qiyukf.uikit.session.fragment.MessageFragment, com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ConsultSource consultSource = (ConsultSource) getArguments().getSerializable("source");
        this.source = consultSource;
        if (consultSource != null && consultSource.groupId != 0) {
            AbsUnicornLog.i(TAG, "ServiceMessageFragment ConsultSource source.groupId: " + this.source.groupId);
        }
        checkSource();
        this.actionMenuPanel = new a(this.actionMenuContainer);
        this.evaluator = new e(this, this.exchange);
        com.qiyukf.unicorn.ui.evaluate.a.a aVar = new com.qiyukf.unicorn.ui.evaluate.a.a(this, this.exchange);
        this.robotEvaluator = aVar;
        this.actionScrollPanel = new com.qiyukf.unicorn.ui.d.a(this.rootView, aVar);
        initActionMenuPanel();
        addSessionListEntrance();
        customizeUI();
        setAdInfoFromSorce();
        c.h().a(true);
        if (c.b()) {
            this.onInitListener.onInit();
        } else {
            this.state = 2;
            getActivity().setTitle(R.string.ysf_requesting_staff);
            c.a(this.onInitListener);
        }
        try {
            showWaterMask();
        } catch (Exception e) {
            AbsUnicornLog.e(TAG, "ServiceMessageFragment showWaterMask error", e);
        }
        setAnnouncementUI(com.qiyukf.unicorn.m.a.a().b().g(), com.qiyukf.unicorn.m.a.a().b().f(), com.qiyukf.unicorn.m.a.a().b().e());
    }

    private void showWaterMask() {
        JSONArray jSONArrayB;
        if (getActivity() != null && com.qiyukf.unicorn.m.a.a().c() && com.qiyukf.unicorn.m.a.a().b().C() == 1) {
            StringBuilder sb = new StringBuilder();
            if (com.qiyukf.unicorn.m.a.a().b().D() == 2) {
                String strH = com.qiyukf.unicorn.d.c.h();
                if (!TextUtils.isEmpty(strH) && (jSONArrayB = j.b(strH)) != null && jSONArrayB.length() > 0) {
                    int i = 0;
                    while (true) {
                        if (i < jSONArrayB.length()) {
                            JSONObject jSONObjectD = j.d(jSONArrayB, i);
                            if (jSONObjectD != null && jSONObjectD.has("key") && TextUtils.equals(j.e(jSONObjectD, "key"), "real_name")) {
                                sb.append(j.e(jSONObjectD, "value"));
                                sb.append("_");
                                break;
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                }
            }
            String strK = c.a().k();
            if (TextUtils.isEmpty(strK)) {
                sb.append("Guest_");
                sb.append(y.a(System.currentTimeMillis()));
            } else {
                sb.append(strK);
                sb.append("_");
                sb.append(y.a(System.currentTimeMillis()));
            }
            z.a().a(getActivity(), sb.toString());
        }
    }

    @Override // com.qiyukf.uikit.session.fragment.MessageFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (!this.isLeaveMsgBack) {
            try {
                c.h().d().a(this, this.exchange);
                c.h().d(this.exchange, true);
            } catch (NullPointerException e) {
                AbsUnicornLog.e(TAG, "邀请评价发生异常", e);
            }
            if (this.statusChange) {
                this.statusChange = false;
                AbsUnicornLog.i(TAG, "ServiceMessageFragment requestStaff 1");
                requestStaff(100, false);
            }
        }
        videoHungUp(11075, "exit video");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0057  */
    @Override // com.qiyukf.uikit.session.fragment.MessageFragment, com.qiyukf.uikit.session.module.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isAllowSendMessage(boolean r5) {
        /*
            r4 = this;
            boolean r0 = com.qiyukf.unicorn.c.b()
            java.lang.String r1 = "ServiceMessageFragment"
            r2 = 0
            if (r0 != 0) goto L14
            java.lang.String r5 = "ServiceMessageFragment disallow SendMessage 1"
            com.qiyukf.module.log.base.AbsUnicornLog.i(r1, r5)
            int r5 = com.qiyukf.unicorn.R.string.ysf_send_message_disallow_as_requesting
            com.qiyukf.unicorn.n.t.a(r5)
            return r2
        L14:
            boolean r0 = r4.isCanSendMessage()
            if (r0 == 0) goto L1c
            r5 = 1
            return r5
        L1c:
            if (r5 != 0) goto L1f
            return r2
        L1f:
            com.qiyukf.unicorn.k.d r0 = com.qiyukf.unicorn.c.h()
            java.lang.String r3 = r4.exchange
            boolean r0 = r0.i(r3)
            if (r0 == 0) goto L36
            java.lang.String r5 = "ServiceMessageFragment disallow SendMessage 2"
            com.qiyukf.module.log.base.AbsUnicornLog.i(r1, r5)
            int r5 = com.qiyukf.unicorn.R.string.ysf_send_message_disallow_as_requesting
            com.qiyukf.unicorn.n.t.a(r5)
            return r2
        L36:
            java.lang.String r0 = "ServiceMessageFragment requestStaff 2"
            com.qiyukf.module.log.base.AbsUnicornLog.i(r1, r0)
            r0 = 7
            boolean r3 = r4.requestStaff(r0, r2)
            if (r3 == 0) goto L62
            int r5 = r4.state
            if (r5 != r0) goto L57
            com.qiyukf.unicorn.c.h()
            java.lang.String r5 = r4.exchange
            boolean r5 = com.qiyukf.unicorn.k.d.l(r5)
            if (r5 != 0) goto L57
            int r5 = com.qiyukf.unicorn.R.string.ysf_group_status_toast
            com.qiyukf.unicorn.n.t.a(r5)
            goto L61
        L57:
            java.lang.String r5 = "ServiceMessageFragment disallow SendMessage 3"
            com.qiyukf.module.log.base.AbsUnicornLog.i(r1, r5)
            int r5 = com.qiyukf.unicorn.R.string.ysf_send_message_disallow_as_requesting
            com.qiyukf.unicorn.n.t.a(r5)
        L61:
            return r2
        L62:
            boolean r5 = super.isAllowSendMessage(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.isAllowSendMessage(boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerViewHolderEvent() {
        this.messageListPanel.j().a(new b.a() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.2
            @Override // com.qiyukf.uikit.session.module.a.b.a
            public void quitQueueEvent() {
                ServiceMessageFragment.this.quitQueue(false);
            }

            @Override // com.qiyukf.uikit.session.module.a.b.a
            public void reRequestEvent() {
                ServiceMessageFragment.this.requestStaff(6, false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAndRequest() {
        ConsultSource consultSource;
        if (!p.b(getContext())) {
            this.state = -1;
            onStatusChange();
            return;
        }
        if (!c.a().a(false) && !c.f().isMixSDK && com.qiyukf.unicorn.h.a.b() != null) {
            this.state = 2;
            onStatusChange();
            setManualLogin();
            return;
        }
        boolean zRequestStaff = true;
        if (!c.a().a(true)) {
            this.isLogging = true;
            this.state = 2;
            onStatusChange();
            return;
        }
        markPushMessage();
        if (c.i().a(this.exchange) > 0 && (consultSource = this.source) != null && consultSource.faqGroupId == 0 && c.h().m() == null) {
            zRequestStaff = false;
        }
        com.qiyukf.unicorn.f.z zVarE = c.h().e(this.exchange);
        if (zVarE != null) {
            AbsUnicornLog.i(TAG, "notifyAppConnectResult session is not");
            com.qiyukf.unicorn.h.a.d.a aVar = new com.qiyukf.unicorn.h.a.d.a();
            aVar.a(200);
            aVar.b(zVarE.g);
            aVar.c(zVarE.d);
            aVar.e(zVarE.j);
            aVar.d(zVarE.e);
            aVar.a(zVarE.c);
            notifyAppConnectResult(aVar);
        }
        if (zRequestStaff) {
            AbsUnicornLog.i(TAG, "ServiceMessageFragment requestStaff 6");
            zRequestStaff = requestStaff(0, false);
        }
        if (getUICustomization() == null || !getUICustomization().disableKeyboardOnEnterConsult || zRequestStaff) {
            return;
        }
        this.inputPanel.setNoStaffSilent(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean requestStaff(int r12, boolean r13) {
        /*
            r11 = this;
            int r0 = r11.state
            com.qiyukf.unicorn.f.u r1 = new com.qiyukf.unicorn.f.u
            java.lang.String r2 = r11.exchange
            r1.<init>(r2)
            r2 = 0
            r1.a(r2)
            r1.a(r12)
            r1.b(r13)
            com.qiyukf.unicorn.f.u r3 = new com.qiyukf.unicorn.f.u
            java.lang.String r4 = r11.exchange
            r3.<init>(r4)
            r3.a(r2)
            com.qiyukf.unicorn.f.d r4 = r11.lastCategory
            r3.a(r4)
            com.qiyukf.unicorn.f.d r4 = r11.lastCategory
            if (r4 != 0) goto L27
            goto L29
        L27:
            int r12 = r4.a
        L29:
            r3.a(r12)
            r3.b(r13)
            int r12 = r11.state
            java.lang.String r13 = "ServiceMessageFragment"
            r4 = 1
            r5 = 7
            r6 = 2
            if (r12 != r5) goto L85
            com.qiyukf.unicorn.c.h()
            java.lang.String r12 = r11.exchange
            boolean r12 = com.qiyukf.unicorn.k.d.l(r12)
            if (r12 != 0) goto L85
            com.qiyukf.unicorn.api.ConsultSource r12 = r11.source
            long r7 = r12.staffId
            r9 = 0
            int r3 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r3 > 0) goto L53
            long r7 = r12.groupId
            int r12 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r12 <= 0) goto La1
        L53:
            com.qiyukf.unicorn.c.h()
            java.lang.String r12 = r11.exchange
            com.qiyukf.nimlib.sdk.msg.model.IMMessage r12 = com.qiyukf.unicorn.k.d.k(r12)
            if (r12 == 0) goto L72
            com.qiyukf.nimlib.sdk.msg.attachment.MsgAttachment r3 = r12.getAttachment()
            com.qiyukf.unicorn.h.a.d.ar r3 = (com.qiyukf.unicorn.h.a.d.ar) r3
            r3.e()
            java.lang.Class<com.qiyukf.nimlib.sdk.ysf.YsfService> r3 = com.qiyukf.nimlib.sdk.ysf.YsfService.class
            java.lang.Object r3 = com.qiyukf.nimlib.sdk.NIMClient.getService(r3)
            com.qiyukf.nimlib.sdk.ysf.YsfService r3 = (com.qiyukf.nimlib.sdk.ysf.YsfService) r3
            r3.updateIMMessageStatus(r12, r4)
        L72:
            java.lang.String r12 = "requestStaff sessionRequestGroup"
            com.qiyukf.module.log.base.AbsUnicornLog.i(r13, r12)
            com.qiyukf.unicorn.k.d r12 = com.qiyukf.unicorn.c.h()
            boolean r12 = r12.a(r1)
            if (r12 == 0) goto La1
            r11.state = r6
            goto La1
        L85:
            com.qiyukf.unicorn.k.d r12 = com.qiyukf.unicorn.c.h()
            boolean r12 = r12.a(r3)
            if (r12 == 0) goto La1
            int r12 = r11.state
            if (r12 == r6) goto La1
            r1 = 3
            if (r12 == r1) goto La1
            r1 = -4
            if (r12 == r1) goto La1
            java.lang.String r12 = "requestStaff sessionRequestNormal"
            com.qiyukf.module.log.base.AbsUnicornLog.i(r13, r12)
            r11.state = r6
        La1:
            int r12 = r11.state
            if (r12 == r0) goto La8
            r11.onStatusChange()
        La8:
            com.qiyukf.unicorn.k.d r12 = com.qiyukf.unicorn.c.h()
            r12.a(r2)
            int r12 = r11.state
            if (r12 == r6) goto Lb7
            if (r12 != r5) goto Lb6
            goto Lb7
        Lb6:
            return r2
        Lb7:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.requestStaff(int, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStatusChange() {
        onStatusChange(null, 1, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01c3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onStatusChange(java.lang.String r19, int r20, long r21) {
        /*
            Method dump skipped, instruction units count: 710
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.onStatusChange(java.lang.String, int, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStatusChange$0() {
        ProductDetail productDetailM1551clone;
        if (isAssignStaffCanSendProduct() && (productDetailM1551clone = this.source.productDetail.m1551clone()) != null) {
            sendProductMessage(productDetailM1551clone);
        }
        if (isAssignStaffCanSendCard()) {
            MessageService.sendCardMessage(this.source.cardDetail.getShopId(), this.source.cardDetail.getCardJson(), this.source.cardDetail.getSendByUser(), this.source.cardDetail.getSendProToRobot(), this.source.cardDetail.getActionText(), this.source.cardDetail.getActionTextColor(), this.source.cardDetail.getIntent(), this.source.cardDetail.getParams());
            lastSessionId = c.h().d(this.exchange);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStatusChange$1() {
        if (isStateChangeCanSendProduct()) {
            if (this.state == 6 && this.source.productDetail.isSendByUser()) {
                ProductDetail productDetailM1551clone = this.source.productDetail.m1551clone();
                if (productDetailM1551clone != null) {
                    sendProductMessage(productDetailM1551clone);
                    return;
                }
                return;
            }
            sendProductMessage(this.source.productDetail);
        }
    }

    private void videoHungUp(int i, String str) {
        VideoService videoService = (VideoService) ServiceHelper.getService(VideoService.class);
        if (videoService != null) {
            videoService.receiveCmd(i, str);
        }
    }

    public boolean isShowBarNewTitle() {
        return com.qiyukf.unicorn.m.a.a().c() && com.qiyukf.unicorn.m.a.a().b().b() == 1;
    }

    public void showCommandMessage(CustomNotification customNotification) {
        com.qiyukf.unicorn.h.a.b attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(customNotification.getContent());
        if (attachStr != null) {
            try {
                onNotification(attachStr, customNotification.getTime());
            } catch (Exception e) {
                AbsUnicornLog.e(TAG, "showCommandMessage error", e);
            }
        }
    }

    private void onNotification(com.qiyukf.unicorn.h.a.b bVar, long j) {
        int cmdId = bVar.getCmdId();
        if (cmdId == 2) {
            onAssignStaff((com.qiyukf.unicorn.h.a.d.a) bVar);
            return;
        }
        if (cmdId == 6) {
            this.state = 8;
            if (((am) bVar).b() != 1) {
                this.evaluator.c();
            }
            InquiryFormHelper inquiryFormHelper = this.inquiryFormHelper;
            if (inquiryFormHelper != null) {
                inquiryFormHelper.closeInquiryFormDialog();
                this.inquiryFormHelper = null;
            }
            c.h().e(this.exchange, false);
            onStatusChange();
            return;
        }
        if (cmdId == 15) {
            onQueueStatus((x) bVar);
            return;
        }
        if (cmdId == 25) {
            this.inputPanel.onReceiveFaqList((l) bVar);
            return;
        }
        if (cmdId == 28) {
            onProcessMsgWithDrawal((t) bVar);
            return;
        }
        if (cmdId == 34) {
            onProcessRunUIResponse((ai) bVar);
            return;
        }
        if (cmdId == 59) {
            onInputingEventProcess((n) bVar);
            return;
        }
        if (cmdId == 90) {
            this.state = 7;
            onStatusChange();
            return;
        }
        if (cmdId == 211) {
            onProcessServiceProphetBotList((al) bVar);
            return;
        }
        if (cmdId == 11111) {
            onEntryPosition((f) bVar);
            return;
        }
        switch (cmdId) {
            case Constants.REQUEST_QZONE_SHARE /* 10104 */:
                onRobotAgentAnswerStart(this.exchange, (ab) bVar);
                break;
            case Constants.REQUEST_QQ_FAVORITES /* 10105 */:
                onRobotAgentAnswer((aa) bVar);
                break;
            case Constants.REQUEST_SEND_TO_MY_COMPUTER /* 10106 */:
                RobotStreamHelper.getInstance().onRobotStreamAnswer(this.messageListPanel, this.exchange, (ah) bVar, j);
                break;
        }
    }

    private void onRobotAgentAnswer(aa aaVar) {
        com.qiyukf.unicorn.h.a.b attachStr;
        if (aaVar == null) {
            return;
        }
        try {
            IMMessage iMMessageA = this.messageListPanel.a(String.valueOf(aaVar.b()));
            if (iMMessageA == null || (attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(aaVar.a())) == null || !(attachStr instanceof ac)) {
                return;
            }
            iMMessageA.setAttachment(attachStr);
            this.messageListPanel.j().notifyDataSetChanged();
            if (this.messageListPanel.g()) {
                this.messageListPanel.h();
            }
        } catch (Exception e) {
            AbsUnicornLog.e(TAG, "onRobotAgentAnswer error", e);
        }
    }

    private void onAssignStaff(final com.qiyukf.unicorn.h.a.d.a aVar) {
        String string;
        if (aVar.v() == 1) {
            if (aVar.b() == 200) {
                string = getString(R.string.ysf_session_ing_and_end_again_request);
            } else {
                string = getString(R.string.ysf_queue_ing_and_end_again_request);
            }
            UnicornDialog.showDoubleBtnDialog(getContext(), null, string, true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.4
                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public void onClick(int i) {
                    if (i != 0) {
                        ServiceMessageFragment.this.lastCategory = null;
                        c.h().a(((MessageFragment) ServiceMessageFragment.this).exchange, aVar);
                        ServiceMessageFragment.this.doAssignStaff(aVar);
                    } else {
                        AbsUnicornLog.i(ServiceMessageFragment.TAG, "ServiceMessageFragment requestStaff 3");
                        ServiceMessageFragment.this.requestStaff(0, true);
                    }
                }
            });
            return;
        }
        this.lastCategory = null;
        if (aVar.b() == 200 || aVar.b() == 201) {
            doAssignStaff(aVar);
        } else {
            postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.5
                @Override // java.lang.Runnable
                public void run() {
                    ServiceMessageFragment.this.doAssignStaff(aVar);
                }
            }, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAssignStaff(com.qiyukf.unicorn.h.a.d.a aVar) {
        int iB = aVar.b();
        if (iB == 200) {
            this.state = c.h().g(this.exchange) == 1 ? 6 : 1;
            postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$doAssignStaff$2();
                }
            }, 250L);
            ConsultSource consultSource = this.source;
            if (consultSource != null && !TextUtils.isEmpty(consultSource.vipStaffid) && !TextUtils.isEmpty(this.source.vipStaffWelcomeMsg) && this.state == 1 && aVar.v() == 0) {
                sendMessage(MessageBuilder.createTextMessage(this.exchange, SessionTypeEnum.Ysf, this.source.vipStaffWelcomeMsg), false);
            }
            c.h().e(this.exchange, aVar.J());
        } else if (iB == 201) {
            this.state = 3;
        } else if (iB == 203) {
            this.state = aVar.q() ? 10 : 4;
        } else if (iB == 204) {
            this.state = 5;
        } else if (iB == 205) {
            this.state = 9;
        } else if (iB == 207) {
            this.state = -4;
            aVar.f(aVar.C());
        } else {
            this.state = -1;
        }
        notifyAppConnectResult(aVar);
        onStatusChange(aVar.u(), aVar.t(), aVar.m());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doAssignStaff$2() {
        if (isAssignStaffCanSendProduct()) {
            if (this.state == 6 && this.source.productDetail.isSendByUser()) {
                ProductDetail productDetailM1551clone = this.source.productDetail.m1551clone();
                if (productDetailM1551clone != null) {
                    sendProductMessage(productDetailM1551clone);
                }
            } else {
                sendProductMessage(this.source.productDetail);
            }
            lastSessionId = c.h().d(this.exchange);
        }
        if (isAssignStaffCanSendCard()) {
            MessageService.sendCardMessage(this.source.cardDetail.getShopId(), this.source.cardDetail.getCardJson(), this.source.cardDetail.getSendByUser(), this.source.cardDetail.getSendProToRobot(), this.source.cardDetail.getActionText(), this.source.cardDetail.getActionTextColor(), this.source.cardDetail.getIntent(), this.source.cardDetail.getParams());
            lastSessionId = c.h().d(this.exchange);
        }
    }

    private void notifyAppConnectResult(final com.qiyukf.unicorn.h.a.d.a aVar) {
        UnicornEventBase unicornEventBaseEventOf;
        if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null && (unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(1)) != null) {
            ConnectionStaffResultEntry connectionStaffResultEntry = new ConnectionStaffResultEntry();
            if (aVar.b() == 200) {
                connectionStaffResultEntry.setStaffType(aVar.i() == 1 ? 0 : 1);
                connectionStaffResultEntry.setConnectResult(0);
                if (this.state == 1) {
                    ConsultSource consultSource = this.source;
                    if (consultSource == null) {
                        return;
                    }
                    if (!TextUtils.isEmpty(consultSource.vipStaffid)) {
                        connectionStaffResultEntry.setVipStaffid(this.source.vipStaffid);
                    }
                    if (!TextUtils.isEmpty(this.source.VIPStaffAvatarUrl)) {
                        connectionStaffResultEntry.setVIPStaffAvatarUrl(this.source.VIPStaffAvatarUrl);
                    }
                    if (!TextUtils.isEmpty(this.source.vipStaffName)) {
                        connectionStaffResultEntry.setVipStaffName(this.source.vipStaffName.length() > 40 ? this.source.vipStaffName.substring(0, 40) : this.source.vipStaffName);
                    }
                }
                connectionStaffResultEntry.setStaffId(aVar.c());
                connectionStaffResultEntry.setStaffRealId(aVar.l());
                connectionStaffResultEntry.setStaffIconUrl(aVar.k());
                connectionStaffResultEntry.setStaffName(aVar.d());
                connectionStaffResultEntry.setGroupId(aVar.m());
                connectionStaffResultEntry.setSessionId(aVar.f());
                connectionStaffResultEntry.setCode(aVar.b());
            } else {
                connectionStaffResultEntry.setCode(aVar.b());
                connectionStaffResultEntry.setConnectResult(1);
                if (p.b(getContext())) {
                    connectionStaffResultEntry.setErrorType(1);
                } else {
                    connectionStaffResultEntry.setErrorType(0);
                }
            }
            unicornEventBaseEventOf.onEvent(connectionStaffResultEntry, getContext(), null);
        }
        if (!(getActivity() instanceof ServiceMessageActivity) || getActivity() == null) {
            return;
        }
        if (isShowBarNewTitle()) {
            final String strA = (com.qiyukf.unicorn.m.a.a().b().c() != 1 || TextUtils.isEmpty(com.qiyukf.unicorn.m.a.a().b().a())) ? "" : com.qiyukf.unicorn.m.a.a().b().a();
            if (com.qiyukf.unicorn.m.a.a().b().d() == 1 && com.qiyukf.unicorn.m.a.a().b().o() != null && !TextUtils.isEmpty(com.qiyukf.unicorn.m.a.a().b().o().a())) {
                com.qiyukf.uikit.a.a(com.qiyukf.unicorn.m.a.a().b().o().a(), new ImageLoaderListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.6
                    @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                    public void onLoadFailed(Throwable th) {
                    }

                    @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                    public void onLoadComplete(@NonNull Bitmap bitmap) {
                        if (ServiceMessageFragment.this.getActivity() == null || bitmap == null) {
                            return;
                        }
                        ((ServiceMessageActivity) ServiceMessageFragment.this.getActivity()).setAvatar(bitmap, strA, ServiceMessageFragment.this.state == 1);
                    }
                });
                return;
            } else if (TextUtils.isEmpty(aVar.k())) {
                ((ServiceMessageActivity) getActivity()).setAvatar(BitmapFactory.decodeResource(getResources(), R.drawable.ysf_def_avatar_staff), strA, this.state == 1);
                return;
            } else {
                com.qiyukf.uikit.a.a(aVar.k(), new ImageLoaderListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.7
                    @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                    public void onLoadFailed(Throwable th) {
                    }

                    @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                    public void onLoadComplete(@NonNull Bitmap bitmap) {
                        if (ServiceMessageFragment.this.getActivity() == null || bitmap == null) {
                            return;
                        }
                        ((ServiceMessageActivity) ServiceMessageFragment.this.getActivity()).setAvatar(bitmap, strA, ServiceMessageFragment.this.state == 1);
                    }
                });
                return;
            }
        }
        if (TextUtils.isEmpty(aVar.k())) {
            ((ServiceMessageActivity) getActivity()).setAvatar(BitmapFactory.decodeResource(getResources(), R.drawable.ysf_def_avatar_staff), aVar.d(), this.state == 1);
        } else {
            com.qiyukf.uikit.a.a(aVar.k(), new ImageLoaderListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.8
                @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                public void onLoadFailed(Throwable th) {
                }

                @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                public void onLoadComplete(@NonNull Bitmap bitmap) {
                    if (ServiceMessageFragment.this.getActivity() == null || bitmap == null) {
                        return;
                    }
                    ((ServiceMessageActivity) ServiceMessageFragment.this.getActivity()).setAvatar(bitmap, aVar.d(), ServiceMessageFragment.this.state == 1);
                }
            });
        }
    }

    public void onInputingEventProcess(n nVar) {
        InputPanel inputPanel;
        if (nVar.a() != c.h().d(this.exchange) || (inputPanel = this.inputPanel) == null) {
            return;
        }
        inputPanel.onReceiveInputingEvent();
    }

    public void onProcessRunUIResponse(ai aiVar) {
        if (aiVar == null) {
            return;
        }
        InputPanel inputPanel = this.inputPanel;
        if (inputPanel != null) {
            inputPanel.setRunUIConfig(aiVar);
        }
        updateCustomUIOption(aiVar);
        setAnnouncementUI(aiVar.e(), aiVar.f(), aiVar.g());
    }

    private void setAnnouncementUI(String str, final String str2, int i) {
        if (com.qiyukf.unicorn.m.a.a().c() && !TextUtils.isEmpty(str) && com.qiyukf.unicorn.m.a.a().b().E()) {
            this.tvAnnouncementText.setText(str);
            if (i == 1 && !TextUtils.isEmpty(str2)) {
                this.tvAnnouncementText.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.9
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (c.f().onMessageItemClickListener != null) {
                            c.f().onMessageItemClickListener.onURLClicked(ServiceMessageFragment.this.getContext(), str2);
                        }
                    }
                });
            }
            this.flAnnouncementParent.setVisibility(0);
            this.ysfIvCloseAnnouncement.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.10
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ((MessageFragment) ServiceMessageFragment.this).flAnnouncementParent.setVisibility(8);
                    com.qiyukf.unicorn.m.a.a().b().F();
                }
            });
            return;
        }
        this.flAnnouncementParent.setVisibility(8);
    }

    private void onQueueStatus(x xVar) {
        int iB;
        if (this.state == 1 || (iB = xVar.b()) == 200) {
            return;
        }
        if (iB == 301) {
            this.state = 1;
            onStatusChange();
        } else if (iB == 302) {
            this.state = 3;
            onStatusChange(xVar.f(), 0, 0L);
        } else if (iB == 303) {
            this.state = 9;
            onStatusChange(xVar.f(), 1, 0L);
        } else {
            this.state = -1;
            onStatusChange();
        }
    }

    private void onProcessMsgWithDrawal(t tVar) {
        IMMessage iMMessageQueryMessageByUuid = MsgDBHelper.queryMessageByUuid(tVar.b());
        if (iMMessageQueryMessageByUuid != null) {
            ((MsgService) NIMClient.getService(MsgService.class)).deleteChattingHistory(iMMessageQueryMessageByUuid);
            this.messageListPanel.b(iMMessageQueryMessageByUuid);
            tVar.a(getString(R.string.ysf_staff_withdrawal_str, com.qiyukf.nimlib.c.I().getUserInfo(iMMessageQueryMessageByUuid.getFromAccount()).getName()));
            ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(MessageBuilder.createCustomMessage(this.exchange, SessionTypeEnum.Ysf, tVar), true);
            return;
        }
        IMMessage iMMessageQueryMessageByUuid2 = MsgDBHelper.queryMessageByUuid(tVar.b().substring(tVar.b().indexOf(MqttTopic.MULTI_LEVEL_WILDCARD) + 1));
        if (iMMessageQueryMessageByUuid2 != null) {
            iMMessageQueryMessageByUuid2.setStatus(MsgStatusEnum.recall);
            ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessageQueryMessageByUuid2, true);
        }
    }

    private CharSequence onKickOut() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getString(R.string.ysf_some_error_kickout));
        SpannableString spannableString = new SpannableString(getString(R.string.ysf_retry_connect));
        spannableString.setSpan(new ClickableSpan() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.11
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                ServiceMessageFragment.this.isLogging = true;
                if (c.f().isMixSDK) {
                    OnMixSdkReconnectClickListener onMixSdkReconnectClickListener = c.f().onMixSdkReconnectClickListener;
                    if (onMixSdkReconnectClickListener != null) {
                        onMixSdkReconnectClickListener.onMixSdkReconnectClicked(ServiceMessageFragment.this.getContext());
                        return;
                    }
                    return;
                }
                if (com.qiyukf.unicorn.h.a.b() != null) {
                    ServiceMessageFragment.this.setManualLogin();
                } else {
                    c.a().a(true);
                }
            }
        }, 0, spannableString.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString);
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setManualLogin() {
        this.isLogging = true;
        ((AuthService) NIMClient.getService(AuthService.class)).login(com.qiyukf.unicorn.h.a.b());
    }

    private CharSequence retryText() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getString(R.string.ysf_some_error_happened));
        SpannableString spannableString = new SpannableString(getString(R.string.ysf_retry_connect));
        spannableString.setSpan(new ClickableSpan() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.12
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                if (!h.e().shouldReLogin()) {
                    ServiceMessageFragment.this.requestStaff(4, false);
                } else if (com.qiyukf.unicorn.h.a.b() != null) {
                    ServiceMessageFragment.this.setManualLogin();
                } else {
                    c.a().a(true);
                }
            }
        }, 0, spannableString.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString);
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCanSendMessage() {
        int i = this.state;
        return i == 1 || i == 6 || i == 3 || isInQueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFirstLogin() {
        this.isLogging = false;
        c.a();
        c.i().a();
        if (TextUtils.isEmpty(this.exchange)) {
            setExchange(com.qiyukf.unicorn.d.c.c());
            this.evaluator.a(this.exchange);
            this.robotEvaluator.a(this.exchange);
            c.h().d().a(this, this.exchange);
            Bundle arguments = getArguments();
            if (arguments != null) {
                arguments.putString("account", this.exchange);
            }
        }
        reloadMessage();
        u uVar = new u(this.exchange);
        uVar.a(false);
        uVar.a(this.lastCategory);
        d dVar = this.lastCategory;
        uVar.a(dVar != null ? dVar.a : 0);
        AbsUnicornLog.i(TAG, "ServiceMessageFragment requestStaff 5");
        c.h().a(uVar);
    }

    @Override // com.qiyukf.uikit.session.fragment.MessageFragment
    public void onReceiveMessage(List<IMMessage> list) {
        if (isMyMessage(list.get(0))) {
            ConsultSource consultSource = this.source;
            if (consultSource != null && !TextUtils.isEmpty(consultSource.prompt) && !TextUtils.isEmpty(this.source.vipStaffid) && !TextUtils.isEmpty(this.source.VIPStaffAvatarUrl) && this.state == 1) {
                for (IMMessage iMMessage : list) {
                    if (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.a) {
                        com.qiyukf.unicorn.h.a.d.a aVar = (com.qiyukf.unicorn.h.a.d.a) iMMessage.getAttachment();
                        aVar.s();
                        aVar.b(this.source.prompt.length() > 100 ? this.source.prompt.substring(0, 100) : this.source.prompt);
                    }
                    iMMessage.setFromAccount(this.source.vipStaffid);
                    ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(iMMessage, true);
                }
            }
            for (IMMessage iMMessage2 : list) {
                if (iMMessage2.getAttachment() instanceof com.qiyukf.unicorn.h.a.d.a) {
                    c.h().a(iMMessage2);
                }
            }
            markPushMessage();
            if (com.qiyukf.unicorn.d.c.l(this.exchange) != -1) {
                this.evaluator.b();
            }
        }
    }

    private boolean isMyMessage(IMMessage iMMessage) {
        return iMMessage.getSessionType() == SessionTypeEnum.Ysf && TextUtils.equals(this.exchange, iMMessage.getSessionId());
    }

    private void initActionMenuPanel() {
        if (this.actionMenuContainer == null) {
            return;
        }
        this.actionMenuPanel.a(getUICustomization());
        this.actionMenuPanel.a(this.evaluator);
        this.actionMenuPanel.a(new a.InterfaceC0198a() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.16
            @Override // com.qiyukf.unicorn.ui.c.a.InterfaceC0198a
            public void onMenuItemClick(com.qiyukf.unicorn.ui.c.b bVar) {
                OnMessageItemClickListener onMessageItemClickListener = c.f().onMessageItemClickListener;
                switch (AnonymousClass25.$SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId[bVar.b() - 1]) {
                    case 1:
                        OnShopEventListener onShopEventListener = c.f().onShopEventListener;
                        if (onShopEventListener != null) {
                            onShopEventListener.onShopEntranceClick(ServiceMessageFragment.this.getContext(), ((MessageFragment) ServiceMessageFragment.this).exchange);
                        }
                        break;
                    case 2:
                        u uVar = new u(((MessageFragment) ServiceMessageFragment.this).exchange);
                        uVar.a(true);
                        uVar.a((d) null);
                        uVar.a(5);
                        uVar.c(30);
                        c.h().a(uVar);
                        break;
                    case 3:
                        ServiceMessageFragment.this.evaluator.a();
                        break;
                    case 4:
                        ServiceMessageFragment.this.onCloseSession();
                        break;
                    case 5:
                        g.a(ServiceMessageFragment.this.getActivity());
                        break;
                    case 6:
                    case 7:
                        if (onMessageItemClickListener != null && !TextUtils.isEmpty(bVar.g())) {
                            onMessageItemClickListener.onURLClicked(((MessageFragment) ServiceMessageFragment.this).container.a, bVar.g());
                            break;
                        }
                        break;
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment$25, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass25 {
        static final /* synthetic */ int[] $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId;

        static {
            int[] iArr = new int[b.a.a().length];
            $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId = iArr;
            try {
                iArr[b.a.b - 1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId[b.a.c - 1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId[b.a.d - 1] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId[b.a.e - 1] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId[b.a.a - 1] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId[b.a.f - 1] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$qiyukf$unicorn$ui$menu$MenuItem$MenuId[b.a.g - 1] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActionMenuPanel() {
        int i;
        if (this.actionMenuPanel == null) {
            return;
        }
        if (!com.qiyukf.unicorn.m.a.a().c() || (i = this.state) == 6 || i == 2 || i == 0) {
            ArrayList arrayList = new ArrayList();
            ShopEntrance shopEntrance = this.source.shopEntrance;
            if (shopEntrance != null) {
                arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.b, shopEntrance.getLogo(), true, this.source.shopEntrance.getName()));
            }
            int i2 = this.state;
            if ((i2 == 1 || i2 == 0 || i2 == 8) && com.qiyukf.unicorn.a.a().b() == null && showEvaluator()) {
                arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.d));
            }
            if (this.state == 6 && c.h().t(this.exchange)) {
                if (c.h().u(this.exchange)) {
                    c.h().s(this.exchange);
                    this.inputPanel.setQuickEntryList(c.h().r(this.exchange), false);
                } else {
                    arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.c));
                }
            } else {
                List<com.qiyukf.unicorn.h.a.e.a> listR = c.h().r(this.exchange);
                if (listR != null && listR.size() > 0 && listR.get(0).b() == -1) {
                    listR.remove(0);
                }
            }
            if (this.state == 1 && this.lifeCycleOptions.canCloseSession()) {
                arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.e, true));
            } else if (isInQueue() && this.lifeCycleOptions.canQuitQueue()) {
                arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.e, true));
            } else if (this.state == 8 && this.lifeCycleOptions.canCloseSession()) {
                arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.e, false));
            }
            this.actionMenuPanel.a(arrayList);
            return;
        }
        updateCustomUIOption(c.h().o(this.exchange));
    }

    private List<com.qiyukf.unicorn.ui.c.b> getCustomUIMenuList(List<ai.c.a> list) {
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        for (ai.c.a aVar : list) {
            String strC = aVar.c();
            strC.hashCode();
            switch (strC) {
                case "custom":
                    arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.f, aVar.a(), true, aVar.b(), aVar.d()));
                    break;
                case "evaluate":
                    int i = this.state;
                    if (i != 1 && i != 0 && i != 8) {
                        break;
                    } else {
                        if (com.qiyukf.unicorn.a.a().b() == null && showEvaluator()) {
                            com.qiyukf.unicorn.ui.c.b bVar = new com.qiyukf.unicorn.ui.c.b(b.a.d, aVar.a(), true, aVar.b(), aVar.d());
                            bVar.a(aVar.e() == null ? "" : aVar.e().a());
                            arrayList.add(bVar);
                        }
                        break;
                    }
                    break;
                case "close_session":
                    if (this.state == 1) {
                        arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.e, aVar.a(), true, aVar.b(), aVar.d()));
                        break;
                    } else {
                        if (isInQueue()) {
                            arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.e, aVar.a(), true, aVar.b(), aVar.d()));
                        } else if (this.state == 8) {
                            arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.e, aVar.a(), false, aVar.b(), aVar.d()));
                        }
                        break;
                    }
                    break;
                case "open_link":
                    arrayList.add(new com.qiyukf.unicorn.ui.c.b(b.a.g, aVar.a(), true, aVar.b(), aVar.d()));
                    break;
            }
        }
        return arrayList;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.qiyukf.unicorn.ui.c.b getLeftCustomMenu(java.util.List<com.qiyukf.unicorn.h.a.d.ai.c.a> r12) {
        /*
            Method dump skipped, instruction units count: 314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.getLeftCustomMenu(java.util.List):com.qiyukf.unicorn.ui.c.b");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showEvaluator() {
        return this.isOpenEvaluator && c.h().d().a(this.exchange, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCloseSession() {
        if (this.state != 1 && !isInQueue()) {
            com.qiyukf.unicorn.n.t.a(R.string.ysf_session_already_quit);
        } else {
            final boolean zIsInQueue = isInQueue();
            UnicornDialog.showDoubleBtnDialog(getContext(), null, getString(zIsInQueue ? R.string.ysf_dialog_quit_queue : R.string.ysf_dialog_close_session), true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.17
                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public void onClick(int i) {
                    if (i == 0) {
                        if (!zIsInQueue && ServiceMessageFragment.this.state == 1) {
                            ServiceMessageFragment.this.closeSession(c.h().d(((MessageFragment) ServiceMessageFragment.this).exchange));
                        } else if (zIsInQueue && ServiceMessageFragment.this.isInQueue()) {
                            ServiceMessageFragment.this.quitQueue(true);
                        }
                    }
                }
            });
        }
    }

    private void addSessionListEntrance() {
        SessionListEntrance sessionListEntrance = this.source.sessionListEntrance;
        if (sessionListEntrance == null) {
            return;
        }
        ImageView imageView = (ImageView) this.rootView.findViewById(R.id.ysf_session_list_entrance);
        imageView.setVisibility(0);
        SessionListEntrance.Position position = sessionListEntrance.getPosition();
        int imageResId = sessionListEntrance.getImageResId();
        if (position == null) {
            position = SessionListEntrance.Position.TOP_RIGHT;
        }
        if (imageResId <= 0) {
            imageResId = position == SessionListEntrance.Position.TOP_RIGHT ? R.drawable.ysf_session_list_entrance_right : R.drawable.ysf_session_list_entrance_left;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.gravity = position == SessionListEntrance.Position.TOP_RIGHT ? 8388613 : GravityCompat.START;
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imageResId);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OnShopEventListener onShopEventListener = c.f().onShopEventListener;
                if (onShopEventListener != null) {
                    onShopEventListener.onSessionListEntranceClick(ServiceMessageFragment.this.getContext());
                }
            }
        });
    }

    private void customizeUI() {
        UICustomization uICustomization = getUICustomization();
        if (uICustomization == null) {
            return;
        }
        int i = uICustomization.topTipBarBackgroundColor;
        if (i != 0) {
            this.tipsMessageLabel.setBackgroundColor(i);
        }
        int i2 = uICustomization.topTipBarTextColor;
        if (i2 != 0) {
            this.tipsMessageLabel.setTextColor(i2);
        }
        float f = uICustomization.topTipBarTextSize;
        if (f > 0.0f) {
            this.tipsMessageLabel.setTextSize(f);
        }
    }

    private UICustomization getUICustomization() {
        return c.f().uiCustomization;
    }

    private InputPanelOptions getInputPanelOptions() {
        return c.f().inputPanelOptions;
    }

    private void setAdInfoFromSorce() {
        IMPageViewConfig iMPageViewConfig;
        YSFOptions ySFOptionsF = c.f();
        if (ySFOptionsF == null || (iMPageViewConfig = ySFOptionsF.imPageViewConfig) == null || iMPageViewConfig.adViewProvider == null) {
            return;
        }
        this.llMessageFragmentAd.removeAllViews();
        this.llMessageFragmentAd.addView(ySFOptionsF.imPageViewConfig.adViewProvider.getAdview(getContext()));
    }

    private void sendProductMessage(ProductDetail productDetail) {
        if (productDetail == null) {
            return;
        }
        ProductAttachment productAttachment = new ProductAttachment();
        productAttachment.fromProductDetail(productDetail, true);
        if (productAttachment.getShow() == 1 || productAttachment.getSendByUser() == 1) {
            IMMessage iMMessageCreateCustomMessage = MessageBuilder.createCustomMessage(this.exchange, SessionTypeEnum.Ysf, productAttachment);
            iMMessageCreateCustomMessage.setStatus(MsgStatusEnum.success);
            if (1 == productAttachment.getSendByUser()) {
                this.messageListPanel.a(iMMessageCreateCustomMessage);
                this.hasSentProductMsg = true;
                c.h().a(this.exchange, productDetail.m1551clone());
                return;
            } else {
                if (sendMessage(iMMessageCreateCustomMessage, false)) {
                    this.hasSentProductMsg = true;
                    c.h().a(this.exchange, productDetail.m1551clone());
                    return;
                }
                return;
            }
        }
        com.qiyukf.unicorn.k.c.a(productAttachment, this.exchange);
        this.hasSentProductMsg = true;
        c.h().a(this.exchange, productDetail.m1551clone());
    }

    public boolean onBackPressed() {
        com.qiyukf.unicorn.ui.evaluate.a.a aVar;
        if (isInQueue() && this.lifeCycleOptions.canQuitQueue()) {
            String quitQueuePrompt = this.lifeCycleOptions.getQuitQueuePrompt();
            if (TextUtils.isEmpty(quitQueuePrompt)) {
                quitQueuePrompt = getString(R.string.ysf_dialog_message_queue);
            }
            UnicornDialog.showItemsDialog(getContext(), null, quitQueuePrompt, new String[]{getString(R.string.ysf_line_up_for_me), getString(R.string.ysf_next_consultation), getString(R.string.ysf_cancel)}, true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.19
                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public void onClick(int i) {
                    if (i != 0) {
                        if (i == 1 && ServiceMessageFragment.this.isInQueue()) {
                            ServiceMessageFragment.this.quitQueue(true);
                            return;
                        }
                        return;
                    }
                    if (ServiceMessageFragment.this.sessionLifeCycleListener != null) {
                        ServiceMessageFragment.this.sessionLifeCycleListener.onLeaveSession();
                    }
                }
            });
            return true;
        }
        if (this.state == 1 && this.lifeCycleOptions.canBackPrompt() && c.h().B(this.exchange)) {
            UnicornDialog.showDoubleBtnDialog(getContext(), null, getString(R.string.ysf_dialog_close_session), getString(R.string.ysf_back_close_session), getString(R.string.ysf_back_leave), false, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.20
                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public void onClick(int i) {
                    if (i == 0) {
                        if (ServiceMessageFragment.this.lifeCycleOptions.canShowEvaluation()) {
                            c.h();
                            if (com.qiyukf.unicorn.d.c.p(((MessageFragment) ServiceMessageFragment.this).exchange) && ServiceMessageFragment.this.canEvaluation()) {
                                c.h().d().a(true);
                                EventService.openEvaluation(ServiceMessageFragment.this.getActivity(), ((MessageFragment) ServiceMessageFragment.this).exchange, new RequestCallbackWrapper() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.20.1
                                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                                    public void onResult(int i2, Object obj, Throwable th) {
                                        if (ServiceMessageFragment.this.getContext() == null) {
                                            return;
                                        }
                                        EventService.closeSession(((MessageFragment) ServiceMessageFragment.this).exchange, ServiceMessageFragment.this.getContext().getString(R.string.ysf_already_quit_session));
                                        if (ServiceMessageFragment.this.sessionLifeCycleListener != null) {
                                            ServiceMessageFragment.this.sessionLifeCycleListener.onLeaveSession();
                                        }
                                    }
                                });
                                return;
                            }
                        }
                        EventService.closeSession(((MessageFragment) ServiceMessageFragment.this).exchange, ServiceMessageFragment.this.getContext().getString(R.string.ysf_already_quit_session));
                        if (ServiceMessageFragment.this.sessionLifeCycleListener != null) {
                            ServiceMessageFragment.this.sessionLifeCycleListener.onLeaveSession();
                            return;
                        }
                        return;
                    }
                    if (ServiceMessageFragment.this.sessionLifeCycleListener != null) {
                        ServiceMessageFragment.this.sessionLifeCycleListener.onLeaveSession();
                    }
                }
            });
            return true;
        }
        if (this.state != 6 || !c.h().y(this.exchange) || (aVar = this.robotEvaluator) == null || !aVar.a()) {
            return false;
        }
        this.robotEvaluator.a(new RequestCallbackWrapper() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.21
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public void onResult(int i, Object obj, Throwable th) {
                c.h().z(((MessageFragment) ServiceMessageFragment.this).exchange);
                if (ServiceMessageFragment.this.sessionLifeCycleListener != null) {
                    ServiceMessageFragment.this.sessionLifeCycleListener.onLeaveSession();
                }
            }
        });
        return true;
    }

    @Override // com.qiyukf.uikit.session.fragment.MessageFragment, androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeSession(long j) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        final com.qiyukf.unicorn.h.a.f.b bVar = new com.qiyukf.unicorn.h.a.f.b();
        bVar.a(j);
        com.qiyukf.unicorn.k.c.a(bVar, this.exchange).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.22
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public void onResult(int i, Void r2, Throwable th) {
                if (ServiceMessageFragment.this.isAdded()) {
                    progressDialog.cancel();
                    if (i == 200) {
                        ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(((MessageFragment) ServiceMessageFragment.this).exchange, SessionTypeEnum.Ysf, bVar), true);
                    } else {
                        progressDialog.showProgress(false);
                        progressDialog.setMessage(c.d().getString(R.string.ysf_msg_quit_session_failed));
                        progressDialog.show(1000L);
                    }
                }
            }
        });
    }

    private void markPushMessage() {
        String strR = com.qiyukf.unicorn.d.c.r();
        if (TextUtils.isEmpty(strR)) {
            return;
        }
        com.qiyukf.unicorn.d.c.v(null);
        for (final String str : TextUtils.split(strR, ChineseToPinyinResource.Field.COMMA)) {
            com.qiyukf.unicorn.k.c.a(new com.qiyukf.unicorn.h.a.f.h(str, 2), this.exchange).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.24
                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public void onResult(int i, Void r2, Throwable th) {
                    if (i != 200) {
                        com.qiyukf.unicorn.d.c.v(str);
                    }
                }
            });
        }
    }

    private void checkSource() {
        if (this.source == null) {
            this.source = new ConsultSource(null, null, null);
        }
        SessionLifeCycleOptions sessionLifeCycleOptions = this.source.sessionLifeCycleOptions;
        if (sessionLifeCycleOptions == null) {
            this.lifeCycleOptions = new SessionLifeCycleOptions();
        } else {
            this.lifeCycleOptions = sessionLifeCycleOptions;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInQueue() {
        int i = this.state;
        return i == 4 || i == 10;
    }

    private boolean isStateChangeCanSendProduct() {
        ProductDetail productDetail;
        if (this.hasSentProductMsg || (productDetail = this.source.productDetail) == null || !productDetail.valid() || !this.source.productDetail.isAlwaysSend()) {
            return false;
        }
        if (this.source.productDetail.equals(c.h().q(this.exchange)) && !this.source.productDetail.isSendByUser()) {
            return false;
        }
        int i = this.state;
        return i == 1 || (i == 6 && this.source.isSendProductonRobot);
    }

    private boolean isAssignStaffCanSendProduct() {
        ProductDetail productDetail = this.source.productDetail;
        if (productDetail == null || !productDetail.valid()) {
            return false;
        }
        if (c.h().d(this.exchange) == lastSessionId && this.state != 3) {
            return false;
        }
        int i = this.state;
        return i == 1 || (i == 6 && this.source.isSendProductonRobot) || (i == 3 && this.source.isSendProductionLeave);
    }

    private boolean isAssignStaffCanSendCard() {
        CardDetail cardDetail = this.source.cardDetail;
        if (cardDetail == null || !cardDetail.valid()) {
            return false;
        }
        if (c.h().d(this.exchange) == lastSessionId && this.state != 3) {
            return false;
        }
        int i = this.state;
        return i == 1 || (i == 6 && this.source.cardDetail.getSendProToRobot() == 1) || (this.state == 3 && this.source.isSendProductionLeave);
    }

    @Override // com.qiyukf.uikit.session.fragment.MessageFragment, com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        try {
            c.h().d().a();
        } catch (NullPointerException e) {
            AbsUnicornLog.e(TAG, "邀请评价发生异常", e);
        }
        com.qiyukf.uikit.a.a();
        this.source = null;
        if (c.b()) {
            if (c.h() != null && c.h().d() != null) {
                c.h().d().b();
            }
            c.h().d(this.exchange, false);
            registerObservers(false);
            if (!c.h().l()) {
                c.h().a((ConsultSource) null);
            }
            c.h().b(false);
        }
        c.b(this.onInitListener);
        videoHungUp(-11075, "exit video");
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initState() {
        com.qiyukf.unicorn.k.d dVarH = c.h();
        if (com.qiyukf.unicorn.k.d.k(this.exchange) != null && !com.qiyukf.unicorn.k.d.l(this.exchange)) {
            this.state = 7;
        } else if (dVarH.b(this.exchange) > 0) {
            this.state = dVarH.c(this.exchange).f ? 10 : 4;
        } else if (dVarH.i(this.exchange)) {
            this.state = 2;
        } else if (dVarH.g(this.exchange) == 1) {
            this.state = 6;
        } else if (dVarH.d(this.exchange) > 0) {
            this.state = 1;
        }
        onStatusChange();
    }

    private void onRobotAgentAnswerStart(String str, ab abVar) {
        if (abVar == null) {
            return;
        }
        com.qiyukf.unicorn.f.z zVar = c.h().f().get(str);
        final com.qiyukf.nimlib.session.d dVarA = com.qiyukf.nimlib.ysf.a.a(zVar == null ? str : zVar.d, str, SessionTypeEnum.Ysf, String.valueOf(abVar.b()), abVar, System.currentTimeMillis());
        com.qiyukf.unicorn.k.c.a(dVarA, true, false);
        postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (dVarA.getAttachment() instanceof ac) {
                        return;
                    }
                    ((MessageFragment) ServiceMessageFragment.this).messageListPanel.b(dVarA);
                } catch (Exception e) {
                    AbsUnicornLog.e(ServiceMessageFragment.TAG, "onRobotAgentAnswerStart error", e);
                }
            }
        }, abVar.a());
    }

    private void onEntryPosition(f fVar) {
        if (fVar == null) {
            return;
        }
        c.h().a(this.exchange, fVar.a());
        c.h().c(this.exchange, fVar.b());
        if (fVar.b()) {
            c.h().s(this.exchange);
            this.inputPanel.setQuickEntryList(c.h().r(this.exchange), false);
        } else {
            updateActionMenuPanel();
        }
    }

    private void onProcessServiceProphetBotList(al alVar) {
        if (c.h().r(this.exchange) != null && c.h().r(this.exchange).size() > 0 && c.h().r(this.exchange).get(0).b() == -1) {
            if (alVar.a() == null) {
                alVar.a(new ArrayList());
            }
            alVar.a().add(0, c.h().r(this.exchange).get(0));
        }
        c.h().a(this.exchange, alVar.a());
        this.inputPanel.setQuickEntryList(alVar.a(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerObservers(boolean z) {
        if (z) {
            c.h().a(new d.a() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.13
                @Override // com.qiyukf.unicorn.k.d.a
                public void onRequestStaffStart(String str, com.qiyukf.unicorn.f.d dVar) {
                    if (TextUtils.equals(((MessageFragment) ServiceMessageFragment.this).exchange, str)) {
                        ServiceMessageFragment.this.state = 2;
                        ServiceMessageFragment.this.lastCategory = dVar;
                        ServiceMessageFragment.this.onStatusChange();
                    }
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void onEvaluationEvent(String str) {
                    if (TextUtils.equals(((MessageFragment) ServiceMessageFragment.this).exchange, str)) {
                        ServiceMessageFragment.this.evaluator.b();
                    }
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void onRobotEvaluationEvent(String str) {
                    if (TextUtils.equals(((MessageFragment) ServiceMessageFragment.this).exchange, str)) {
                        ServiceMessageFragment.this.robotEvaluator.c();
                    }
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void onRevertStatus(String str) {
                    ServiceMessageFragment.this.state = -1;
                    ServiceMessageFragment.this.onStatusChange();
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void onUpdateEvaluationShow(String str, boolean z2) {
                    if (TextUtils.equals(((MessageFragment) ServiceMessageFragment.this).exchange, str)) {
                        if (ServiceMessageFragment.this.state == 1) {
                            ServiceMessageFragment.this.isOpenEvaluator = z2;
                            ((MessageFragment) ServiceMessageFragment.this).inputPanel.notifyActionListModify(ServiceMessageFragment.this.showEvaluator());
                            ServiceMessageFragment.this.evaluator.b();
                        }
                        ServiceMessageFragment.this.updateActionMenuPanel();
                    }
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void onRobotEvaluatorOpen(String str) {
                    if (TextUtils.equals(((MessageFragment) ServiceMessageFragment.this).exchange, str)) {
                        if (ServiceMessageFragment.this.state == 6 || ServiceMessageFragment.this.state == 10) {
                            ServiceMessageFragment.this.actionScrollPanel.a(0);
                            ServiceMessageFragment.this.isOpenRobotEvaluator = true;
                        }
                        ServiceMessageFragment.this.robotEvaluator.c();
                    }
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void onSaveMsgToPage(String str, List<IMMessage> list) {
                    if (TextUtils.equals(((MessageFragment) ServiceMessageFragment.this).exchange, str)) {
                        ((MessageFragment) ServiceMessageFragment.this).messageListPanel.a(list);
                    }
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void onVideoError(String str, String str2) {
                    ServiceMessageFragment.this.state = -4;
                    ServiceMessageFragment.this.onStatusChange(str2, 0, 0L);
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void openInquiryForm(long j) {
                    ServiceMessageFragment serviceMessageFragment = ServiceMessageFragment.this;
                    ((MessageFragment) serviceMessageFragment).inquiryFormHelper = new InquiryFormHelper(serviceMessageFragment.getActivity());
                    ((MessageFragment) ServiceMessageFragment.this).inquiryFormHelper.openInquiryFormDialog(j, ((MessageFragment) ServiceMessageFragment.this).exchange);
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public void initState() {
                    ServiceMessageFragment.this.state = 0;
                    ServiceMessageFragment.this.onStatusChange();
                }

                @Override // com.qiyukf.unicorn.k.d.a
                public boolean isInMessageListPanel(String str) {
                    return ((MessageFragment) ServiceMessageFragment.this).messageListPanel.a(str) != null;
                }
            });
            c.h().a(getActivity());
        } else {
            c.h().a((d.a) null);
            c.h().a((Context) null);
        }
        c.h().a(this.exchange, z);
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.commandObserver, z);
        ((AuthServiceObserver) NIMClient.getService(AuthServiceObserver.class)).observeOnlineStatus(this.statusObserver, z);
    }

    private void updateCustomUIOption(ai aiVar) {
        if (c.h().n()) {
            return;
        }
        if (aiVar == null || aiVar.d() == null) {
            this.actionMenuPanel.a((com.qiyukf.unicorn.ui.c.b) null, new ArrayList());
            return;
        }
        if (this.state == 10) {
            this.actionMenuPanel.a((com.qiyukf.unicorn.ui.c.b) null, new ArrayList());
            return;
        }
        ai.c cVarD = aiVar.d();
        this.actionMenuPanel.a(getLeftCustomMenu(cVarD.a()), getCustomUIMenuList(cVarD.b()));
        e eVar = this.evaluator;
        int i = this.state;
        boolean z = true;
        if (i != 1 && i != 0 && i != 8) {
            z = false;
        }
        eVar.a(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canEvaluation() {
        c.h();
        com.qiyukf.unicorn.h.a.c.c cVarA = com.qiyukf.unicorn.k.a.a(this.exchange);
        long jS = com.qiyukf.unicorn.d.c.s(String.valueOf(com.qiyukf.unicorn.d.c.j(this.exchange)));
        return ((cVarA != null && System.currentTimeMillis() <= (cVarA.f().longValue() * 60000) + jS) || jS == 0) && 2 != com.qiyukf.unicorn.d.c.m(this.exchange);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void quitQueue(final boolean z) {
        SessionLifeCycleListener sessionLifeCycleListener;
        com.qiyukf.unicorn.f.p pVarC = c.h().c(this.exchange);
        if (pVarC == null) {
            return;
        }
        long j = pVarC.a;
        final com.qiyukf.unicorn.h.a.f.b bVar = new com.qiyukf.unicorn.h.a.f.b();
        bVar.a(j);
        com.qiyukf.unicorn.k.c.a(bVar, this.exchange).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment.23
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public void onResult(int i, Void r3, Throwable th) {
                if (i == 200) {
                    c.h().b(((MessageFragment) ServiceMessageFragment.this).exchange, true);
                    ServiceMessageFragment.this.state = 0;
                    ServiceMessageFragment.this.onStatusChange();
                    if (z) {
                        return;
                    }
                    ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(com.qiyukf.nimlib.ysf.a.a(((MessageFragment) ServiceMessageFragment.this).exchange, SessionTypeEnum.Ysf, bVar), true);
                    return;
                }
                com.qiyukf.unicorn.n.t.a(R.string.ysf_msg_quit_queue_failed);
            }
        });
        if (!z || (sessionLifeCycleListener = this.sessionLifeCycleListener) == null) {
            return;
        }
        sessionLifeCycleListener.onLeaveSession();
    }
}

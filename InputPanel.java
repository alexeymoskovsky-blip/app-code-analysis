package com.qiyukf.uikit.session.module.input;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.n.w;
import com.qiyukf.nimlib.sdk.media.record.AudioRecorder;
import com.qiyukf.nimlib.sdk.media.record.IAudioRecordCallback;
import com.qiyukf.nimlib.sdk.media.record.RecordType;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.attachment.AudioAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.b;
import com.qiyukf.uikit.session.emoji.EmoticonPickerView;
import com.qiyukf.uikit.session.emoji.EmoticonView;
import com.qiyukf.uikit.session.emoji.IEmoticonSelectedListener;
import com.qiyukf.uikit.session.emoji.MoonUtil;
import com.qiyukf.uikit.session.helper.QuoteMsgHelper;
import com.qiyukf.uikit.session.module.a;
import com.qiyukf.uikit.session.module.input.BottomLayoutHelper;
import com.qiyukf.uikit.session.module.input.faq.FaqAssociatedList;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.customization.action.AlbumAction;
import com.qiyukf.unicorn.api.customization.action.BaseAction;
import com.qiyukf.unicorn.api.customization.action.CameraAction;
import com.qiyukf.unicorn.api.customization.action.CloseSessionAction;
import com.qiyukf.unicorn.api.customization.action.EvaluationAction;
import com.qiyukf.unicorn.api.customization.action.ImageAction;
import com.qiyukf.unicorn.api.customization.action.InquireWorkSheetAction;
import com.qiyukf.unicorn.api.customization.action.LinkClickAction;
import com.qiyukf.unicorn.api.customization.action.PickFileAction;
import com.qiyukf.unicorn.api.customization.action.QueryProductAction;
import com.qiyukf.unicorn.api.customization.action.TakeVideoAction;
import com.qiyukf.unicorn.api.customization.action.VideoAlbumAction;
import com.qiyukf.unicorn.api.customization.action.WorkSheetAction;
import com.qiyukf.unicorn.api.customization.input.ActionListProvider;
import com.qiyukf.unicorn.api.customization.input.ActionPanelOptions;
import com.qiyukf.unicorn.api.customization.input.InputPanelOptions;
import com.qiyukf.unicorn.api.event.EventCallback;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.event.entry.RequestPermissionEventEntry;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.i;
import com.qiyukf.unicorn.f.t;
import com.qiyukf.unicorn.h.a.d.ai;
import com.qiyukf.unicorn.h.a.d.l;
import com.qiyukf.unicorn.h.a.d.z;
import com.qiyukf.unicorn.h.a.e.d;
import com.qiyukf.unicorn.h.a.e.e;
import com.qiyukf.unicorn.h.a.e.f;
import com.qiyukf.unicorn.h.a.e.g;
import com.qiyukf.unicorn.h.a.e.h;
import com.qiyukf.unicorn.h.a.f.ae;
import com.qiyukf.unicorn.h.a.f.m;
import com.qiyukf.unicorn.n.j;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.widget.StylableTextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONArray;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* JADX INFO: loaded from: classes6.dex */
public class InputPanel implements IAudioRecordCallback, IEmoticonSelectedListener {
    private static final String TAG = "InputPanel";
    private static final int maxDuration = 60;
    private ImageView actionListButton;
    private LevelListDrawable amplitudeDrawable;
    private ViewGroup amplitudeView;
    private ai attachment;
    private TextView audioRecordBtn;
    private AudioRecorder audioRecorder;
    private View audioSwitchButton;
    private BottomLayoutHelper bottomLayoutHelper;
    private ImageView cancelAmplitudeIndicator;
    private View cancelImageView;
    private TextView cancelTipLabel;
    private a container;
    private View emojiButton;
    private EmoticonPickerView emoticonPickerView;
    private FaqAssociatedList faqAssociatedList;
    private LinearLayout messageActivityBottomLayout;
    private EditText messageEditText;
    private ImageView messageQuoteClose;
    private ImageView messageQuoteIv;
    private LinearLayout messageQuoteLayout;
    private View messageQuoteLine;
    private TextView messageQuoteTv;
    private int moreBgColor;
    private QuickEntryHelper quickEntryHelper;
    private View recordingAnimationView;
    private TextView recordingCountDownLabel;
    private View recordingEndTip;
    private View recordingView;
    private ImageView recordingViewMic;
    private View rootView;
    private StylableTextView sendMessageButton;
    private com.qiyukf.uikit.session.a sessionCustomization;
    private long startRecordTime;
    private ImageView textSwitchButton;
    private String title;
    private Runnable retryTitleRunnable = new Runnable() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.1
        @Override // java.lang.Runnable
        public void run() {
            if (InputPanel.this.container.b.getActivity() != null) {
                InputPanel.this.container.b.getActivity().setTitle(TextUtils.isEmpty(InputPanel.this.title) ? o.b(InputPanel.this.container.a) : InputPanel.this.title);
            }
        }
    };
    private Handler mEventHandler = new Handler(Looper.getMainLooper());
    private boolean started = false;
    private boolean cancelled = false;
    private boolean touched = false;
    private boolean isRobot = false;
    private String typingContent = "";
    private IMMessage quoteMsg = null;
    private Runnable sendTypingRunnable = new Runnable() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.4
        @Override // java.lang.Runnable
        public void run() {
            t tVarM = c.h().m(InputPanel.this.container.c);
            long jD = c.h().d(InputPanel.this.container.c);
            long jG = c.h().g(InputPanel.this.container.c);
            String string = InputPanel.this.messageEditText.getText().toString();
            if (tVarM.a() && jD > 0 && jG == 0 && !InputPanel.this.container.c.equals(b.b()) && !TextUtils.equals(string, InputPanel.this.typingContent)) {
                InputPanel.this.typingContent = string;
                ae aeVar = new ae();
                aeVar.a(jD);
                aeVar.a(InputPanel.this.typingContent);
                aeVar.b(System.currentTimeMillis());
                aeVar.a(tVarM.b());
                com.qiyukf.unicorn.k.c.a(aeVar, InputPanel.this.container.c);
            }
            InputPanel.this.uiHandler.postDelayed(this, (long) (tVarM.b() * 1000.0f));
        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.5
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view == InputPanel.this.textSwitchButton) {
                InputPanel.this.bottomLayoutHelper.showEditor(true);
                InputPanel.this.checkSendButtonEnable();
                return;
            }
            if (view == InputPanel.this.audioSwitchButton) {
                InputPanel.this.bottomLayoutHelper.showAudioRecordView();
                InputPanel.this.checkSendButtonEnable();
                return;
            }
            if (view == InputPanel.this.sendMessageButton) {
                InputPanel.this.onTextMessageSendButtonPressed();
                return;
            }
            if (view != InputPanel.this.actionListButton) {
                if (view == InputPanel.this.emojiButton) {
                    InputPanel.this.bottomLayoutHelper.toggleEmojiLayout();
                    return;
                }
                if (view == InputPanel.this.messageEditText) {
                    InputPanel.this.bottomLayoutHelper.showEditor(true);
                    return;
                } else {
                    if (view == InputPanel.this.messageQuoteClose) {
                        InputPanel.this.messageQuoteLayout.setVisibility(8);
                        InputPanel.this.quoteMsg = null;
                        InputPanel.this.audioSwitchButton.setEnabled(true);
                        return;
                    }
                    return;
                }
            }
            if (com.qiyukf.unicorn.k.c.a(true)) {
                UICustomization uICustomization = c.f().uiCustomization;
                boolean z = uICustomization != null && uICustomization.isRobotSendImage && InputPanel.this.isRobot;
                if (!com.qiyukf.unicorn.m.a.a().c() || z) {
                    int i = uICustomization != null ? uICustomization.showRobotImageEntry : 0;
                    if (InputPanel.this.sessionCustomization == null || !InputPanel.this.sessionCustomization.h || z) {
                        if (i == 0) {
                            if (InputPanel.this.actions != null && InputPanel.this.actions.size() != 0) {
                                ((BaseAction) InputPanel.this.actions.get(0)).onClick();
                                return;
                            }
                            AbsUnicornLog.i(InputPanel.TAG, "actions is actions= " + InputPanel.this.actions);
                            return;
                        }
                        if (!InputPanel.this.isRobot) {
                            InputPanel.this.bottomLayoutHelper.notifyActionListModify(new ArrayList(InputPanel.this.actions));
                        }
                        InputPanel.this.bottomLayoutHelper.toggleActionPanelLayout();
                        return;
                    }
                }
                InputPanel.this.bottomLayoutHelper.toggleActionPanelLayout();
            }
        }
    };
    private Runnable recordingUpdateUI = new Runnable() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.9
        @Override // java.lang.Runnable
        public void run() {
            if (InputPanel.this.audioRecorder != null) {
                InputPanel inputPanel = InputPanel.this;
                inputPanel.onUpdateAmplitude(inputPanel.audioRecorder.getCurrentRecordMaxAmplitude());
                InputPanel.this.mEventHandler.postDelayed(this, 100L);
            }
        }
    };
    private FaqAssociatedList.OnFaqItemClickListener onFaqItemClickListener = new FaqAssociatedList.OnFaqItemClickListener() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.11
        @Override // com.qiyukf.uikit.session.module.input.faq.FaqAssociatedList.OnFaqItemClickListener
        public void onClick(l.a aVar) {
            InputPanel.this.messageEditText.setText(aVar.a());
            InputPanel.this.onTextMessageSendButtonPressed();
        }
    };
    private List<BaseAction> actions = getActionList();
    private Handler uiHandler = new Handler();

    @Override // com.qiyukf.nimlib.sdk.media.record.IAudioRecordCallback
    public void onRecordReady() {
    }

    public InputPanel(a aVar, View view, com.qiyukf.uikit.session.a aVar2) {
        int i;
        this.moreBgColor = 0;
        this.container = aVar;
        this.rootView = view;
        this.sessionCustomization = aVar2;
        if (aVar2 != null && (i = aVar2.c) != 0) {
            this.moreBgColor = i;
        } else if (com.qiyukf.unicorn.m.a.a().c()) {
            this.moreBgColor = Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().e());
        }
        init();
    }

    public void setMoreBgColor(int i) {
        this.moreBgColor = i;
    }

    public void onResume() {
        this.uiHandler.post(this.sendTypingRunnable);
    }

    public void onPause() {
        if (this.audioRecorder != null) {
            onEndAudioRecord(true);
        }
        com.qiyukf.unicorn.d.c.b(this.container.c, this.messageEditText.getText().toString());
        this.uiHandler.removeCallbacks(this.sendTypingRunnable);
    }

    public boolean collapse() {
        View actionPanelLayout = this.bottomLayoutHelper.getActionPanelLayout();
        EmoticonPickerView emoticonPickerView = this.emoticonPickerView;
        boolean z = (emoticonPickerView != null && emoticonPickerView.getVisibility() == 0) || (actionPanelLayout != null && actionPanelLayout.getVisibility() == 0);
        this.bottomLayoutHelper.hideAll();
        return z;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void reload(a aVar) {
        this.container = aVar;
    }

    private void init() {
        initViews();
        initPanel();
        initInputBarListener();
        initTextEdit();
        initAudioRecordButton();
        checkSendButtonEnable();
        setBackKeyListener();
        for (int i = 0; i < this.actions.size(); i++) {
            this.actions.get(i).setIndex(i);
            this.actions.get(i).setContainer(this.container);
        }
    }

    private void initViews() {
        this.messageActivityBottomLayout = (LinearLayout) this.rootView.findViewById(R.id.messageActivityBottomLayout);
        this.textSwitchButton = (ImageView) this.rootView.findViewById(R.id.buttonTextMessage);
        this.audioSwitchButton = this.rootView.findViewById(R.id.buttonAudioMessage);
        this.actionListButton = (ImageView) this.rootView.findViewById(R.id.action_list_trigger_button);
        this.emojiButton = this.rootView.findViewById(R.id.emoji_button);
        this.sendMessageButton = (StylableTextView) this.rootView.findViewById(R.id.send_message_button);
        this.messageEditText = (EditText) this.rootView.findViewById(R.id.editTextMessage);
        this.audioRecordBtn = (TextView) this.rootView.findViewById(R.id.audioRecord);
        this.recordingView = this.rootView.findViewById(R.id.ysf_audio_recording_panel);
        this.cancelTipLabel = (TextView) this.rootView.findViewById(R.id.ysf_cancel_recording_text_view);
        this.amplitudeView = (ViewGroup) this.rootView.findViewById(R.id.ysf_audio_amplitude_panel);
        this.cancelAmplitudeIndicator = (ImageView) this.rootView.findViewById(R.id.ysf_amplitude_indicator);
        this.recordingViewMic = (ImageView) this.rootView.findViewById(R.id.ysf_recording_view_mic);
        this.amplitudeDrawable = (LevelListDrawable) ((ImageView) this.rootView.findViewById(R.id.ysf_amplitude_indicator)).getDrawable();
        this.cancelImageView = this.rootView.findViewById(R.id.ysf_recording_cancel_indicator);
        this.recordingCountDownLabel = (TextView) this.rootView.findViewById(R.id.ysf_recording_count_down_label);
        this.recordingAnimationView = this.rootView.findViewById(R.id.ysf_audio_recording_animation_view);
        this.recordingEndTip = this.rootView.findViewById(R.id.ysf_audio_record_end_tip);
        this.messageQuoteLayout = (LinearLayout) this.rootView.findViewById(R.id.ysf_message_quote_layout);
        this.messageQuoteClose = (ImageView) this.rootView.findViewById(R.id.ysf_message_quote_close);
        this.messageQuoteLine = this.rootView.findViewById(R.id.ysf_message_quote_line);
        this.messageQuoteTv = (TextView) this.rootView.findViewById(R.id.ysf_message_quote_text);
        this.messageQuoteIv = (ImageView) this.rootView.findViewById(R.id.ysf_message_quote_iv);
        this.emoticonPickerView = (EmoticonPickerView) this.rootView.findViewById(R.id.emoticon_picker_view);
        BottomLayoutHelper bottomLayoutHelper = new BottomLayoutHelper(this.container.b, this.messageActivityBottomLayout, this, this.actions, this.moreBgColor);
        this.bottomLayoutHelper = bottomLayoutHelper;
        bottomLayoutHelper.setHideActionListener(new BottomLayoutHelper.CallbackToHideAction() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.2
            @Override // com.qiyukf.uikit.session.module.input.BottomLayoutHelper.CallbackToHideAction
            public void callback(boolean z) {
                InputPanel.this.rotateAnimation(!z);
            }
        });
        this.quickEntryHelper = new QuickEntryHelper(this.container, this.messageActivityBottomLayout);
        this.textSwitchButton.setVisibility(8);
        if (com.qiyukf.unicorn.m.a.a().c() && com.qiyukf.unicorn.m.a.a().b().l() == 0) {
            this.audioSwitchButton.setVisibility(8);
        } else {
            this.audioSwitchButton.setVisibility(0);
        }
        FaqAssociatedList faqAssociatedList = new FaqAssociatedList();
        this.faqAssociatedList = faqAssociatedList;
        faqAssociatedList.init(this.container.b.getContext(), this.rootView, this.container.c, this.onFaqItemClickListener);
        if (p.b(this.container.a)) {
            this.messageActivityBottomLayout.setPadding(p.d(), 0, p.d(), 0);
        }
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.messageActivityBottomLayout.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().e()));
            this.emoticonPickerView.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().e()));
            Drawable background = this.audioSwitchButton.getBackground();
            int color = Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f());
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            background.setColorFilter(color, mode);
            this.emojiButton.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()), mode);
            this.textSwitchButton.getDrawable().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()), mode);
            this.audioRecordBtn.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.audioRecordBtn.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 100));
            this.sendMessageButton.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()));
            this.messageQuoteLayout.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().e()));
            this.messageQuoteClose.getDrawable().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()), mode);
            this.messageQuoteTv.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            this.messageQuoteLine.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
        }
    }

    private void initInputPanelImg() {
        if (com.qiyukf.unicorn.m.a.a().c() && com.qiyukf.unicorn.m.a.a().b() != null) {
            String strK = com.qiyukf.unicorn.m.a.a().b().k();
            this.actionListButton.setImageResource(R.drawable.ysf_ic_tigger_btn_transparent);
            this.actionListButton.setBackground(com.qiyukf.unicorn.m.b.d(strK));
            this.actionListButton.getDrawable().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()), PorterDuff.Mode.SRC_IN);
            return;
        }
        this.actionListButton.setImageResource(R.color.ysf_transparent_color);
        this.actionListButton.setBackgroundResource(R.drawable.ysf_ic_input_bottom_img_and_video);
        com.qiyukf.uikit.session.a aVar = this.sessionCustomization;
        if (aVar == null) {
            return;
        }
        View view = this.audioSwitchButton;
        int i = aVar.d;
        if (i == 0) {
            i = R.drawable.ysf_ic_input_voice_back;
        }
        view.setBackgroundResource(i);
        View view2 = this.emojiButton;
        int i2 = this.sessionCustomization.e;
        if (i2 == 0) {
            i2 = R.drawable.ysf_ic_input_emoji_back;
        }
        view2.setBackgroundResource(i2);
        com.qiyukf.uikit.session.a aVar2 = this.sessionCustomization;
        if (aVar2.h) {
            ImageView imageView = this.actionListButton;
            int i3 = aVar2.g;
            if (i3 == 0) {
                i3 = R.drawable.ysf_ic_input_bottom_add;
            }
            imageView.setBackgroundResource(i3);
            return;
        }
        ImageView imageView2 = this.actionListButton;
        int i4 = aVar2.f;
        if (i4 == 0) {
            i4 = R.drawable.ysf_ic_input_bottom_img_and_video;
        }
        imageView2.setBackgroundResource(i4);
    }

    private void initInputBarListener() {
        this.textSwitchButton.setOnClickListener(this.clickListener);
        this.audioSwitchButton.setOnClickListener(this.clickListener);
        this.emojiButton.setOnClickListener(this.clickListener);
        this.sendMessageButton.setOnClickListener(this.clickListener);
        this.actionListButton.setOnClickListener(this.clickListener);
        this.messageEditText.setOnClickListener(this.clickListener);
        this.messageQuoteClose.setOnClickListener(this.clickListener);
    }

    private void initTextEdit() {
        UICustomization uICustomization = c.f().uiCustomization;
        if (uICustomization != null) {
            float f = uICustomization.inputTextSize;
            if (f > 0.0f) {
                this.messageEditText.setTextSize(f);
            }
            int i = uICustomization.inputTextColor;
            if (i != 0) {
                this.messageEditText.setTextColor(i);
            } else if (com.qiyukf.unicorn.m.a.a().c()) {
                setEditTextColor();
            }
        } else if (com.qiyukf.unicorn.m.a.a().c()) {
            setEditTextColor();
        }
        this.messageEditText.setInputType(IjkMediaPlayer.OnNativeInvokeListener.CTRL_WILL_TCP_OPEN);
        this.messageEditText.addTextChangedListener(new TextWatcher() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.3
            private int count;
            private int start;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                this.start = i2;
                this.count = i4;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                InputPanel.this.checkSendButtonEnable();
                MoonUtil.replaceEmoticons(InputPanel.this.container.a, editable, this.start, this.count);
                InputPanel.this.faqAssociatedList.onEditTextChanged(editable.toString());
            }
        });
        if (uICustomization == null || !uICustomization.disableKeyboardOnEnterConsult) {
            setDraft();
        }
        if (uICustomization != null && uICustomization.hideKeyboardOnEnterConsult) {
            this.bottomLayoutHelper.showEditor(false);
            this.messageEditText.setFocusable(true);
            this.messageEditText.setFocusableInTouchMode(true);
            this.messageEditText.requestFocus();
        } else {
            this.bottomLayoutHelper.showEditor(true);
        }
        if (uICustomization == null || !uICustomization.disableKeyboardOnEnterConsult) {
            return;
        }
        setNoStaffSilent(true, true);
    }

    private void setEditTextColor() {
        this.messageEditText.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
        this.messageEditText.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
    }

    private void setDraft() {
        String strE = com.qiyukf.unicorn.d.c.e(this.container.c);
        if (TextUtils.isEmpty(strE)) {
            return;
        }
        if (strE.length() > 4000) {
            strE = strE.substring(0, 4000);
        }
        sendMessageToInputPanel(strE);
        com.qiyukf.unicorn.d.c.b(this.container.c, (String) null);
    }

    private void initPanel() {
        View view;
        int i;
        UICustomization uICustomization = c.f().uiCustomization;
        this.rootView.findViewById(R.id.switchLayout).setVisibility((!com.qiyukf.unicorn.m.a.a().c() ? !(uICustomization == null || !uICustomization.hideAudio) : com.qiyukf.unicorn.m.a.a().b().l() == 0) ? 0 : 8);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            if (com.qiyukf.unicorn.m.a.a().b().m() == 0) {
                view = this.emojiButton;
                i = 8;
            } else {
                view = this.emojiButton;
                i = 0;
            }
        } else {
            View view2 = this.emojiButton;
            if (uICustomization == null || !uICustomization.hideEmoji) {
                view = view2;
                i = 0;
            } else {
                view = view2;
                i = 8;
            }
        }
        view.setVisibility(i);
        boolean zA = com.qiyukf.unicorn.n.f.b.a(this.messageEditText.getText());
        boolean z = !zA;
        this.actionListButton.setVisibility(!zA ? 8 : 0);
        this.sendMessageButton.setVisibility(zA ? 8 : 0);
        this.sendMessageButton.setEnabled(z);
        initInputPanelImg();
    }

    public void setIsRobot(boolean z, boolean z2) {
        this.isRobot = z;
        cancelAudioRecord(true);
        closeAudioRecordView();
        this.bottomLayoutHelper.showEditor(false);
        if (z) {
            UICustomization uICustomization = c.f().uiCustomization;
            boolean z3 = uICustomization != null && uICustomization.isRobotSendImage;
            int i = uICustomization != null ? uICustomization.showRobotImageEntry : 0;
            if (z3) {
                if (i == 0) {
                    this.actionListButton.setImageResource(R.color.ysf_transparent_color);
                    this.actionListButton.setBackgroundResource(R.drawable.ysf_ic_input_bottom_img_and_video);
                    this.actions = getActionList();
                    for (int i2 = 0; i2 < this.actions.size(); i2++) {
                        this.actions.get(i2).setIndex(i2);
                        this.actions.get(i2).setContainer(this.container);
                    }
                } else {
                    String strK = com.qiyukf.unicorn.m.a.a().b().k();
                    this.actionListButton.setImageResource(R.drawable.ysf_ic_tigger_btn_transparent);
                    this.actionListButton.setBackground(com.qiyukf.unicorn.m.b.d(strK));
                    this.actionListButton.getDrawable().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()), PorterDuff.Mode.SRC_IN);
                    this.actions.clear();
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new AlbumAction(R.drawable.ysf_ic_action_album, R.string.ysf_picker_image_folder));
                    arrayList.add(new CameraAction(R.drawable.ysf_ic_action_camera, R.string.ysf_input_panel_take));
                    arrayList.add(new TakeVideoAction(R.drawable.ysf_ic_action_take_video, R.string.ysf_input_panel_take_video));
                    arrayList.add(new VideoAlbumAction(R.drawable.ysf_ic_action_select_video, R.string.ysf_input_panel_select_video));
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        ((BaseAction) arrayList.get(i3)).setIndex(i3);
                        ((BaseAction) arrayList.get(i3)).setContainer(this.container);
                    }
                    this.actions.addAll(arrayList);
                    this.bottomLayoutHelper.notifyActionListModify(arrayList);
                }
            }
            this.rootView.findViewById(R.id.switchLayout).setVisibility((uICustomization == null || !uICustomization.hideAudioWithRobot) ? 0 : 8);
            this.emojiButton.setVisibility(8);
            this.actionListButton.setVisibility(((uICustomization == null || !uICustomization.isRobotSendImage) && !z2) ? 8 : 0);
            this.sendMessageButton.setVisibility(((uICustomization != null && uICustomization.isRobotSendImage) || z2) ? 8 : 0);
            return;
        }
        initPanel();
    }

    public void setRunUIConfig(ai aiVar) {
        this.attachment = aiVar;
        if (!com.qiyukf.unicorn.m.a.a().c() || this.attachment == null) {
            return;
        }
        boolean z = false;
        if (c.h().w(this.container.c).booleanValue() && c.h().d().a(this.container.c, false)) {
            z = true;
        }
        if (this.attachment.c() != null && this.attachment.c().size() > 0) {
            List<BaseAction> list = this.actions;
            if (list != null) {
                list.clear();
                this.actions.addAll(transferAction(this.attachment.c(), z, this.attachment.a()));
            }
            checkSendButtonEnable();
            this.bottomLayoutHelper.notifyActionListModify(transferAction(this.attachment.c(), z, this.attachment.a()));
        } else {
            this.actionListButton.setVisibility(8);
        }
        if (this.attachment.h()) {
            return;
        }
        this.quickEntryHelper.setQuickEntryList(transferQuick(this.attachment.b(), z));
    }

    public void notifyActionListModify(boolean z) {
        ai aiVar = this.attachment;
        if (aiVar != null && z) {
            this.bottomLayoutHelper.notifyActionListModify(transferAction(aiVar.c(), z, this.attachment.a()));
            if (this.attachment.h()) {
                return;
            }
            this.quickEntryHelper.setQuickEntryList(transferQuick(this.attachment.b(), z));
        }
    }

    private List<i> transferQuick(List<ai.b> list, boolean z) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (ai.b bVar : list) {
            String strA = bVar.a();
            strA.hashCode();
            switch (strA) {
                case "custom":
                    arrayList.add(new com.qiyukf.unicorn.h.a.e.c(bVar.b(), bVar.c()));
                    break;
                case "query_worksheet":
                    try {
                        arrayList.add(new e(bVar.b(), getListWorkSheetTmpId(bVar.d()), getIsOpenUrge(bVar.d())));
                        break;
                    } catch (NumberFormatException e) {
                        AbsUnicornLog.e(TAG, "transfer inquire worksheet id is error", e);
                        break;
                    }
                    break;
                case "create_worksheet":
                    try {
                        arrayList.add(new h(bVar.b(), Long.parseLong(bVar.d())));
                        break;
                    } catch (NumberFormatException e2) {
                        AbsUnicornLog.e(TAG, "transfer worksheet id is error", e2);
                        break;
                    }
                    break;
                case "query_product":
                    try {
                        arrayList.add(new g(bVar.b(), bVar.d()));
                        break;
                    } catch (Exception e3) {
                        AbsUnicornLog.e(TAG, "transfer query product id is error", e3);
                        break;
                    }
                    break;
                case "evaluate":
                    if (z) {
                        arrayList.add(new d(bVar.b()));
                        break;
                    } else {
                        break;
                    }
                    break;
                case "close_session":
                    if (c.h().e(this.container.c) != null) {
                        arrayList.add(new com.qiyukf.unicorn.h.a.e.b(bVar.b()));
                        break;
                    } else {
                        break;
                    }
                    break;
                case "open_link":
                    arrayList.add(new f(bVar.c(), bVar.b()));
                    break;
            }
        }
        return arrayList;
    }

    private List<BaseAction> transferAction(List<ai.a> list, boolean z, long j) {
        AlbumAction albumAction;
        VideoAlbumAction videoAlbumAction;
        CameraAction cameraAction;
        TakeVideoAction takeVideoAction;
        QueryProductAction queryProductAction;
        EvaluationAction evaluationAction;
        PickFileAction pickFileAction;
        CloseSessionAction closeSessionAction;
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        for (ai.a aVar : list) {
            String strA = aVar.a();
            strA.hashCode();
            switch (strA) {
                case "select_photo":
                    if (TextUtils.isEmpty(aVar.b())) {
                        albumAction = new AlbumAction(R.drawable.ysf_ic_action_album, aVar.c());
                    } else {
                        albumAction = new AlbumAction(aVar.b(), aVar.c());
                    }
                    albumAction.setActionFontColor(Color.parseColor("#666666"));
                    arrayList.add(albumAction);
                    break;
                case "select_video":
                    if (TextUtils.isEmpty(aVar.b())) {
                        videoAlbumAction = new VideoAlbumAction(R.drawable.ysf_ic_action_select_video, aVar.c());
                    } else {
                        videoAlbumAction = new VideoAlbumAction(aVar.b(), aVar.c());
                    }
                    videoAlbumAction.setActionFontColor(Color.parseColor("#666666"));
                    arrayList.add(videoAlbumAction);
                    break;
                case "custom":
                case "open_link":
                    LinkClickAction linkClickAction = new LinkClickAction(aVar.b(), aVar.c());
                    linkClickAction.setUrl(aVar.d());
                    linkClickAction.setActionFontColor(Color.parseColor("#666666"));
                    arrayList.add(linkClickAction);
                    break;
                case "query_worksheet":
                    InquireWorkSheetAction inquireWorkSheetAction = new InquireWorkSheetAction(aVar.b(), aVar.c());
                    try {
                        inquireWorkSheetAction.setTemplateIds(getListWorkSheetTmpId(aVar.d()));
                        inquireWorkSheetAction.setOpenUrge(getIsOpenUrge(aVar.d()));
                        arrayList.add(inquireWorkSheetAction);
                        break;
                    } catch (NumberFormatException e) {
                        AbsUnicornLog.e(TAG, "transfer inquire worksheet id is error", e);
                        break;
                    }
                    break;
                case "take_photo":
                    if (TextUtils.isEmpty(aVar.b())) {
                        cameraAction = new CameraAction(R.drawable.ysf_ic_action_camera, aVar.c());
                    } else {
                        cameraAction = new CameraAction(aVar.b(), aVar.c());
                    }
                    cameraAction.setActionFontColor(Color.parseColor("#666666"));
                    arrayList.add(cameraAction);
                    break;
                case "take_video":
                    if (TextUtils.isEmpty(aVar.b())) {
                        takeVideoAction = new TakeVideoAction(R.drawable.ysf_ic_action_take_video, aVar.c());
                    } else {
                        takeVideoAction = new TakeVideoAction(aVar.b(), aVar.c());
                    }
                    takeVideoAction.setActionFontColor(Color.parseColor("#666666"));
                    arrayList.add(takeVideoAction);
                    break;
                case "create_worksheet":
                    WorkSheetAction workSheetAction = new WorkSheetAction(aVar.b(), aVar.c());
                    workSheetAction.setActionFontColor(Color.parseColor("#666666"));
                    try {
                        workSheetAction.setTemplateId(Long.parseLong(aVar.d()));
                        arrayList.add(workSheetAction);
                        break;
                    } catch (NumberFormatException e2) {
                        AbsUnicornLog.e(TAG, "transfer worksheet id is error", e2);
                        break;
                    }
                    break;
                case "query_product":
                    long jD = c.h().d(this.container.c);
                    long j2 = jD == 0 ? j : jD;
                    if (TextUtils.isEmpty(aVar.b())) {
                        queryProductAction = new QueryProductAction(R.drawable.ysf_ic_action_query_product, aVar.c(), aVar.d(), j2);
                    } else {
                        queryProductAction = new QueryProductAction(aVar.b(), aVar.c(), aVar.d(), j2);
                    }
                    arrayList.add(queryProductAction);
                    break;
                case "evaluate":
                    if (z) {
                        if (TextUtils.isEmpty(aVar.b())) {
                            evaluationAction = new EvaluationAction(R.drawable.ysf_ic_action_evaluation, aVar.c());
                        } else {
                            evaluationAction = new EvaluationAction(aVar.b(), aVar.c());
                        }
                        evaluationAction.setActionFontColor(Color.parseColor("#666666"));
                        arrayList.add(evaluationAction);
                        break;
                    } else {
                        break;
                    }
                    break;
                case "select_file":
                    if (TextUtils.isEmpty(aVar.b())) {
                        pickFileAction = new PickFileAction(R.drawable.ysf_ic_action_pick_file, aVar.c());
                    } else {
                        pickFileAction = new PickFileAction(aVar.b(), aVar.c());
                    }
                    pickFileAction.setActionFontColor(Color.parseColor("#666666"));
                    arrayList.add(pickFileAction);
                    break;
                case "close_session":
                    if (c.h().e(this.container.c) == null) {
                        break;
                    } else {
                        if (TextUtils.isEmpty(aVar.b())) {
                            closeSessionAction = new CloseSessionAction(R.drawable.ysf_ic_action_quit, aVar.c());
                        } else {
                            closeSessionAction = new CloseSessionAction(aVar.b(), aVar.c());
                        }
                        closeSessionAction.setActionFontColor(Color.parseColor("#666666"));
                        arrayList.add(closeSessionAction);
                        break;
                    }
                    break;
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ((BaseAction) arrayList.get(i)).setIndex(i);
            ((BaseAction) arrayList.get(i)).setContainer(this.container);
        }
        return arrayList;
    }

    private List<Long> getListWorkSheetTmpId(String str) {
        JSONArray jSONArrayG;
        ArrayList arrayList = new ArrayList();
        if (str == null || j.a(str) == null || (jSONArrayG = j.g(j.a(str), "ids")) == null) {
            return arrayList;
        }
        for (int i = 0; i < jSONArrayG.length(); i++) {
            try {
                arrayList.add(Long.valueOf(j.c(jSONArrayG, i)));
            } catch (Exception e) {
                AbsUnicornLog.e(TAG, "getListWorkSheetTmpId is error", e);
            }
        }
        return arrayList;
    }

    private boolean getIsOpenUrge(String str) {
        if (str != null && j.a(str) != null) {
            try {
                return 1 == j.a(j.a(str), NotificationCompat.CATEGORY_REMINDER);
            } catch (Exception e) {
                AbsUnicornLog.e(TAG, "getIsOpenUrge is error", e);
            }
        }
        return false;
    }

    public void setNoStaffSilent(boolean z) {
        setNoStaffSilent(z, false);
    }

    public void setNoStaffSilent(boolean z, boolean z2) {
        String string;
        EditText editText;
        if (z) {
            cancelAudioRecord(true);
            closeAudioRecordView();
            this.bottomLayoutHelper.showEditor(false);
            this.messageEditText.setText((CharSequence) null);
            this.bottomLayoutHelper.hideAll();
        }
        Activity activity = this.container.a;
        if (activity == null) {
            string = "input question";
        } else {
            string = activity.getString(R.string.ysf_input_question_label);
        }
        if (com.qiyukf.unicorn.m.a.a().c()) {
            string = com.qiyukf.unicorn.m.a.a().b().n();
        } else if (c.f().uiCustomization != null && !TextUtils.isEmpty(c.f().uiCustomization.editTextHint)) {
            string = c.f().uiCustomization.editTextHint;
        }
        if (z2) {
            editText = this.messageEditText;
            string = editText.getContext().getString(R.string.ysf_connect_unable_message);
        } else {
            editText = this.messageEditText;
            if (z) {
                string = editText.getContext().getString(R.string.ysf_no_staff_disabled);
            }
        }
        editText.setHint(string);
        if (!z && c.f().uiCustomization != null && c.f().uiCustomization.disableKeyboardOnEnterConsult) {
            setDraft();
        }
        this.messageEditText.setEnabled(!z);
        this.audioSwitchButton.setEnabled(!z);
        this.emojiButton.setEnabled(!z);
        this.actionListButton.setEnabled(!z);
    }

    public void onReceiveInputingEvent() {
        this.uiHandler.removeCallbacks(this.retryTitleRunnable);
        if (this.container.b.getActivity() != null) {
            this.container.b.getActivity().setTitle(R.string.ysf_inputing_title);
        }
        this.uiHandler.postDelayed(this.retryTitleRunnable, 2000L);
    }

    public void setQuickEntryList(List<? extends i> list, boolean z) {
        if (z && com.qiyukf.unicorn.m.a.a().c()) {
            return;
        }
        this.quickEntryHelper.setQuickEntryList(list);
    }

    public void onReceiveFaqList(l lVar) {
        if (lVar.a() == c.h().f(this.container.c)) {
            this.faqAssociatedList.onFaqListUpdate(lVar.b());
        }
    }

    @TargetApi(11)
    public void rotateAnimation(boolean z) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.actionListButton, "rotation", 0.0f, 45.0f);
        objectAnimatorOfFloat.setDuration(300L);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.actionListButton, "rotation", 45.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(300L);
        com.qiyukf.uikit.session.a aVar = this.sessionCustomization;
        if (aVar == null || !aVar.h) {
            return;
        }
        if (z) {
            objectAnimatorOfFloat2.start();
        } else {
            objectAnimatorOfFloat.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTextMessageSendButtonPressed() {
        IMMessage iMMessageCreateTextMessage;
        long jLongValue;
        String strTrim = this.messageEditText.getText().toString().trim();
        if (VisitorForbiddenHelper.getInstance().isEnable() && VisitorForbiddenHelper.getInstance().hasForbidden(strTrim)) {
            return;
        }
        List<String> listB = com.qiyukf.unicorn.n.f.a.b(strTrim);
        if (this.quoteMsg != null) {
            m mVar = new m();
            try {
                if (this.quoteMsg.getUuid().contains(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                    jLongValue = Long.parseLong(this.quoteMsg.getUuid().substring(0, this.quoteMsg.getUuid().indexOf(35)));
                } else {
                    jLongValue = ((Long) this.quoteMsg.getLocalExtension().get("sessionid")).longValue();
                }
            } catch (Exception unused) {
                jLongValue = 0;
            }
            if (this.quoteMsg.getUuid().contains(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                mVar.a(this.quoteMsg.getUuid());
            } else {
                mVar.a(jLongValue + MqttTopic.MULTI_LEVEL_WILDCARD + this.quoteMsg.getUuid());
            }
            mVar.a(jLongValue);
            mVar.b();
            mVar.b(strTrim);
            mVar.b(c.h().d(this.container.c));
            mVar.d(QuoteMsgHelper.getQuoteMessageType(this.quoteMsg));
            mVar.c(QuoteMsgHelper.getQuoteMessageContent(this.quoteMsg));
            a aVar = this.container;
            iMMessageCreateTextMessage = MessageBuilder.createCustomMessage(aVar.c, aVar.d, strTrim, mVar);
        } else {
            a aVar2 = this.container;
            iMMessageCreateTextMessage = MessageBuilder.createTextMessage(aVar2.c, aVar2.d, strTrim);
        }
        if (c.h().A(this.container.c).b() && listB.size() > 0 && !TextUtils.isEmpty(strTrim)) {
            if (!isContainBlackUrl(listB, c.h().A(this.container.c).a())) {
                sendTextMessageAndClearEditText(iMMessageCreateTextMessage);
                return;
            }
            c.h();
            if (com.qiyukf.unicorn.k.d.k(this.container.c) != null) {
                c.h();
                if (!com.qiyukf.unicorn.k.d.l(this.container.c)) {
                    com.qiyukf.unicorn.n.t.a(R.string.ysf_group_status_toast);
                    return;
                }
            }
            sendLocalFailMessage(strTrim);
            return;
        }
        sendTextMessageAndClearEditText(iMMessageCreateTextMessage);
    }

    private void sendTextMessageAndClearEditText(IMMessage iMMessage) {
        if (this.container.e.sendMessage(iMMessage, false)) {
            this.messageEditText.setText("");
            if (this.quoteMsg != null) {
                this.messageQuoteLayout.setVisibility(8);
                this.quoteMsg = null;
            }
        }
    }

    private void sendLocalFailMessage(String str) {
        a aVar = this.container;
        IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(aVar.c, aVar.d, str);
        iMMessageCreateTextMessage.setStatus(MsgStatusEnum.fail);
        Map<String, Object> localExtension = iMMessageCreateTextMessage.getLocalExtension();
        if (localExtension == null) {
            localExtension = new HashMap<>();
        }
        localExtension.put("text_msg_touch_is_ban_tag", Boolean.TRUE);
        iMMessageCreateTextMessage.setLocalExtension(localExtension);
        this.container.e.saveMessageToLocal(iMMessageCreateTextMessage, true, true);
        this.messageEditText.setText("");
    }

    private boolean isContainBlackUrl(List<String> list, List<String> list2) {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String host = Uri.parse(it.next()).getHost();
            if (TextUtils.isEmpty(host)) {
                return false;
            }
            for (String str : list2) {
                if (TextUtils.isEmpty(str) || !host.contains(str)) {
                }
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkSendButtonEnable() {
        ImageView imageView;
        ai aiVar;
        UICustomization uICustomization = c.f().uiCustomization;
        boolean z = true;
        boolean z2 = (uICustomization != null && uICustomization.isRobotSendImage) || (c.h().n() && (aiVar = this.attachment) != null && aiVar.c() != null && this.attachment.c().size() > 0);
        boolean z3 = this.audioRecordBtn.getVisibility() == 0;
        boolean zA = com.qiyukf.unicorn.n.f.b.a(this.messageEditText.getText());
        boolean z4 = !zA;
        boolean z5 = !z3 && (!zA || (this.isRobot && !z2));
        if ((this.isRobot && !z2) || (!z3 && !zA)) {
            z = false;
        }
        this.sendMessageButton.setEnabled(z4);
        this.sendMessageButton.setVisibility(z5 ? 0 : 8);
        if (this.isRobot) {
            this.actionListButton.setVisibility(z ? 0 : 8);
            return;
        }
        if (com.qiyukf.unicorn.m.a.a().c()) {
            ai aiVar2 = this.attachment;
            if (aiVar2 == null || (aiVar2.c() != null && this.attachment.c().size() > 0)) {
                this.actionListButton.setVisibility(z ? 0 : 8);
                return;
            }
            imageView = this.actionListButton;
        } else {
            imageView = this.actionListButton;
            if (!z) {
            }
            imageView.setVisibility(i);
        }
        i = 8;
        imageView.setVisibility(i);
    }

    @Override // com.qiyukf.uikit.session.emoji.IEmoticonSelectedListener
    public void onEmojiSelected(String str) {
        int iLastIndexOf;
        try {
            Editable text = this.messageEditText.getText();
            int i = 0;
            if (str.equals(EmoticonView.DELETE_EMOJI)) {
                this.messageEditText.dispatchKeyEvent(new KeyEvent(0, 67));
                return;
            }
            if (str.equals(EmoticonView.DELETE_CUSTOM_EMOJI)) {
                String string = text.toString();
                int selectionStart = this.messageEditText.getSelectionStart();
                if (selectionStart != -1 && (iLastIndexOf = string.substring(0, selectionStart).lastIndexOf("[:")) < selectionStart && iLastIndexOf != -1 && MoonUtil.isCustomEmojiMessage(string.substring(iLastIndexOf, selectionStart)) && ']' == string.charAt(selectionStart - 1)) {
                    this.messageEditText.getText().delete(iLastIndexOf, selectionStart);
                    return;
                }
                return;
            }
            int selectionStart2 = this.messageEditText.getSelectionStart();
            int selectionEnd = this.messageEditText.getSelectionEnd();
            if (selectionStart2 < 0) {
                selectionStart2 = 0;
            }
            if (selectionEnd >= 0) {
                i = selectionEnd;
            }
            if (selectionStart2 <= i) {
                text.replace(selectionStart2, i, str);
            } else {
                text.replace(i, selectionStart2, str);
            }
            this.messageEditText.setSelection(Math.min(Math.min(selectionStart2, i) + str.length(), this.messageEditText.getText().toString().length()));
        } catch (Exception e) {
            AbsUnicornLog.e(TAG, "onEmojiSelected is error".concat(String.valueOf(e)));
        }
    }

    @Override // com.qiyukf.uikit.session.emoji.IEmoticonSelectedListener
    public void onStickerSelected(String str, String str2) {
        z zVarA = z.a(str2, str);
        IMMessage iMMessageCreateCustomMessage = MessageBuilder.createCustomMessage(this.container.c, SessionTypeEnum.Ysf, zVarA);
        iMMessageCreateCustomMessage.setContent(zVarA.getContent());
        this.container.e.sendMessage(iMMessageCreateCustomMessage, false);
    }

    private void initAudioRecordButton() {
        this.audioRecordBtn.setOnTouchListener(new View.OnTouchListener() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.6
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final UnicornEventBase unicornEventBaseEventOf;
                if (motionEvent.getAction() == 0) {
                    if (com.qiyukf.unicorn.n.j.a(InputPanel.this.container.b.getContext(), com.qiyukf.unicorn.f.l.h)) {
                        InputPanel.this.touched = true;
                        InputPanel.this.initAudioRecord();
                        InputPanel.this.onStartAudioRecord();
                    } else if (c.f().sdkEvents == null || c.f().sdkEvents.eventProcessFactory == null || com.qiyukf.unicorn.n.j.a(InputPanel.this.container.b.getContext(), com.qiyukf.unicorn.f.l.h) || (unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5)) == null) {
                        InputPanel.this.checkPermissionAndRecord(null, null);
                    } else {
                        List<String> listAsList = Arrays.asList(com.qiyukf.unicorn.f.l.h);
                        final RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                        requestPermissionEventEntry.setScenesType(8);
                        requestPermissionEventEntry.setPermissionList(listAsList);
                        unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, InputPanel.this.container.b.getContext(), new EventCallback() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.6.1
                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onProcessEventSuccess(Object obj) {
                                InputPanel.this.checkPermissionAndRecord(unicornEventBaseEventOf, requestPermissionEventEntry);
                            }

                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onPorcessEventError() {
                                InputPanel.this.checkPermissionAndRecord(unicornEventBaseEventOf, requestPermissionEventEntry);
                            }

                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onNotPorcessEvent() {
                                InputPanel.this.checkPermissionAndRecord(unicornEventBaseEventOf, requestPermissionEventEntry);
                            }

                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onInterceptEvent() {
                                com.qiyukf.unicorn.n.t.a(R.string.ysf_no_permission_send_audio);
                            }
                        });
                    }
                } else if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                    if (InputPanel.this.audioRecorder != null && InputPanel.this.touched) {
                        InputPanel.this.touched = false;
                        InputPanel.this.onEndAudioRecord(InputPanel.isCancelled(view, motionEvent));
                    }
                } else if (motionEvent.getAction() == 2 && InputPanel.this.audioRecorder != null) {
                    InputPanel.this.cancelAudioRecord(InputPanel.isCancelled(view, motionEvent));
                }
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPermissionAndRecord(final UnicornEventBase unicornEventBase, final RequestPermissionEventEntry requestPermissionEventEntry) {
        com.qiyukf.unicorn.n.j.a(this.container.b).a(com.qiyukf.unicorn.f.l.h).a(new j.a() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.7
            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(InputPanel.this.container.b.getContext(), requestPermissionEventEntry);
                }
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(InputPanel.this.container.b.getContext(), requestPermissionEventEntry)) {
                    com.qiyukf.unicorn.n.t.a(R.string.ysf_no_permission_send_audio);
                }
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCancelled(View view, MotionEvent motionEvent) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return motionEvent.getRawX() < ((float) iArr[0]) || motionEvent.getRawX() > ((float) (iArr[0] + view.getWidth())) || motionEvent.getRawY() < ((float) (iArr[1] + (-40)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAudioRecord() {
        if (this.audioRecorder == null) {
            this.audioRecorder = new AudioRecorder(this.container.a, RecordType.AAC, 60, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStartAudioRecord() {
        this.container.a.getWindow().setFlags(128, 128);
        openAudioRecordView();
        this.audioRecorder.startRecord();
        this.cancelled = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEndAudioRecord(boolean z) {
        this.container.a.getWindow().setFlags(0, 128);
        this.audioRecorder.completeRecord(z);
        this.audioRecordBtn.setText(R.string.ysf_audio_record_touch_to_record);
        setAudioRecordBackground(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAudioRecord(boolean z) {
        if (this.started && this.cancelled != z) {
            this.cancelled = z;
            updateTimerTip(z);
        }
    }

    private void updateTimerTip(boolean z) {
        Log.i("InputPanelTest", "cancel:".concat(String.valueOf(z)));
        if (z) {
            this.audioRecordBtn.setText(R.string.ysf_audio_record_touch_to_record);
            this.recordingAnimationView.setVisibility(4);
            this.cancelImageView.setVisibility(0);
            setAudioRecordBackground(true);
        } else {
            this.audioRecordBtn.setText(R.string.ysf_audio_record_up_to_complete);
            this.cancelImageView.setVisibility(4);
            this.recordingAnimationView.setVisibility(0);
            if (System.currentTimeMillis() - this.startRecordTime > 50000) {
                this.amplitudeView.setVisibility(4);
                this.recordingCountDownLabel.setVisibility(0);
            } else {
                this.amplitudeView.setVisibility(0);
                this.recordingCountDownLabel.setVisibility(4);
            }
            setAudioRecordBackground(false);
        }
        updateTipLabel(z, System.currentTimeMillis() - this.startRecordTime >= 59000);
    }

    private void openAudioRecordView() {
        this.recordingView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeAudioRecordView() {
        this.recordingView.setVisibility(8);
    }

    private void setAudioRecordBackground(boolean z) {
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.audioRecordBtn.setBackground(z ? com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 100) : com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), com.qiyukf.unicorn.m.a.a().b().s().a(), 100));
        }
    }

    private void updateTipLabel(boolean z, boolean z2) {
        if (z) {
            this.cancelTipLabel.setText(R.string.ysf_audio_record_cancel_tip);
        } else if (z2) {
            this.cancelTipLabel.setText(this.container.a.getString(R.string.ysf_audio_record_time_is_up_tips, 60));
        } else {
            this.cancelTipLabel.setText(R.string.ysf_audio_record_move_up_to_cancel);
        }
    }

    @Override // com.qiyukf.nimlib.sdk.media.record.IAudioRecordCallback
    public void onRecordStart(File file, RecordType recordType) {
        this.mEventHandler.post(this.recordingUpdateUI);
        this.started = true;
        if (this.touched) {
            this.startRecordTime = System.currentTimeMillis();
            this.audioRecordBtn.setText(R.string.ysf_audio_record_up_to_complete);
            setAudioRecordBackground(false);
            updateTimerTip(false);
            openAudioRecordView();
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.qiyukf.nimlib.sdk.media.record.IAudioRecordCallback
    public void onRecordSuccess(File file, long j, RecordType recordType) {
        this.mEventHandler.removeCallbacks(this.recordingUpdateUI);
        closeAudioRecordView();
        a aVar = this.container;
        String str = aVar.c;
        SessionTypeEnum sessionTypeEnum = aVar.d;
        boolean z = this.isRobot;
        com.qiyukf.nimlib.session.d dVar = new com.qiyukf.nimlib.session.d();
        dVar.a(w.a());
        dVar.b(str);
        dVar.setFromAccount(com.qiyukf.nimlib.c.q());
        dVar.setDirect(MsgDirectionEnum.Out);
        dVar.setStatus(MsgStatusEnum.sending);
        dVar.a(sessionTypeEnum);
        dVar.b(System.currentTimeMillis());
        dVar.a(MsgTypeEnum.audio.getValue());
        AudioAttachment audioAttachment = new AudioAttachment();
        audioAttachment.setPath(file.getPath());
        audioAttachment.setSize(file.length());
        if (j > 0 && j < 1000) {
            j = 1000;
        }
        audioAttachment.setDuration(j);
        audioAttachment.setAutoTransform(z);
        audioAttachment.setExtension(w.b(file.getName()));
        dVar.setAttachment(audioAttachment);
        this.container.e.sendMessage(dVar, false);
    }

    @Override // com.qiyukf.nimlib.sdk.media.record.IAudioRecordCallback
    public void onRecordFail() {
        this.mEventHandler.removeCallbacks(this.recordingUpdateUI);
        this.cancelAmplitudeIndicator.setVisibility(8);
        this.recordingViewMic.setImageResource(R.drawable.ysf_recording_alert);
        this.cancelTipLabel.setText(R.string.ysf_audio_record_alert);
        this.cancelTipLabel.setPadding(p.a(25.0f), p.a(5.0f), p.a(25.0f), p.a(5.0f));
        this.uiHandler.postDelayed(new Runnable() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.8
            @Override // java.lang.Runnable
            public void run() {
                InputPanel.this.cancelAmplitudeIndicator.setVisibility(0);
                InputPanel.this.recordingViewMic.setImageResource(R.drawable.ysf_recording_mic);
                InputPanel.this.cancelTipLabel.setText(R.string.ysf_audio_record_cancel_tip);
                InputPanel.this.cancelTipLabel.setPadding(p.a(5.0f), p.a(5.0f), p.a(5.0f), p.a(5.0f));
                InputPanel.this.closeAudioRecordView();
            }
        }, 1000L);
    }

    @Override // com.qiyukf.nimlib.sdk.media.record.IAudioRecordCallback
    public void onRecordCancel() {
        closeAudioRecordView();
        this.mEventHandler.removeCallbacks(this.recordingUpdateUI);
    }

    @Override // com.qiyukf.nimlib.sdk.media.record.IAudioRecordCallback
    public void onRecordReachedMaxTime(int i) {
        closeAudioRecordView();
        this.audioRecorder.handleEndRecord(true, i);
        this.mEventHandler.removeCallbacks(this.recordingUpdateUI);
    }

    public void onUpdateAmplitude(int i) {
        this.amplitudeDrawable.setLevel(Math.max(0, Math.min(6, (int) (((int) (Math.log10(i / 100) * 20.0d)) / 6.32f))));
    }

    public boolean isRecording() {
        AudioRecorder audioRecorder = this.audioRecorder;
        return audioRecorder != null && audioRecorder.isRecording();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 != -1) {
            return;
        }
        QuickEntryHelper quickEntryHelper = this.quickEntryHelper;
        if (quickEntryHelper != null) {
            quickEntryHelper.onActivityResult(i, intent);
        }
        int i3 = (i << 16) >> 24;
        if (i3 != 0) {
            int i4 = i3 - 1;
            if (i4 < 0 || i4 >= this.actions.size()) {
                AbsUnicornLog.i(TAG, "request code out of actions' range");
                return;
            }
            BaseAction baseAction = this.actions.get(i4);
            if (baseAction != null) {
                baseAction.onActivityResult(i & 255, i2, intent);
            }
        }
    }

    private void setBackKeyListener() {
        this.messageEditText.setOnKeyListener(new View.OnKeyListener() { // from class: com.qiyukf.uikit.session.module.input.InputPanel.10
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 1 && i == 4) {
                    return InputPanel.this.collapse();
                }
                return false;
            }
        });
    }

    public void onConfigurationChanged(Configuration configuration) {
        BottomLayoutHelper bottomLayoutHelper = this.bottomLayoutHelper;
        if (bottomLayoutHelper != null) {
            bottomLayoutHelper.onConfigurationChanged(configuration);
        }
        if (p.f()) {
            if (configuration.orientation == 2) {
                this.messageActivityBottomLayout.setPadding(p.d(), 0, p.d(), 0);
            } else {
                this.messageActivityBottomLayout.setPadding(0, 0, 0, 0);
            }
        }
    }

    public void sendMessageToInputPanel(String str) {
        this.messageEditText.setText(str);
        this.messageEditText.setSelection(str.length());
    }

    public List<BaseAction> getActionList() {
        ActionListProvider actionListProvider;
        ArrayList arrayList = new ArrayList();
        InputPanelOptions inputPanelOptions = c.f().inputPanelOptions;
        AbsUnicornLog.i(TAG, "getActionList() inputPanelOption= ".concat(String.valueOf(inputPanelOptions)));
        if (inputPanelOptions != null && inputPanelOptions.showActionPanel) {
            ActionPanelOptions actionPanelOptions = inputPanelOptions.actionPanelOptions;
            if (actionPanelOptions == null || (actionListProvider = actionPanelOptions.actionListProvider) == null || actionListProvider.getActionList().size() == 0) {
                arrayList.add(new AlbumAction(R.drawable.ysf_ic_action_album, R.string.ysf_picker_image_folder));
                arrayList.add(new CameraAction(R.drawable.ysf_ic_action_camera, R.string.ysf_input_panel_take));
            } else {
                AbsUnicornLog.i(TAG, "actionListProvider size= " + inputPanelOptions.actionPanelOptions.actionListProvider.getActionList().size());
                arrayList.addAll(inputPanelOptions.actionPanelOptions.actionListProvider.getActionList());
            }
        } else {
            arrayList.add(new ImageAction());
            AbsUnicornLog.i(TAG, "addImageAction actions=".concat(String.valueOf(arrayList)));
        }
        return arrayList;
    }

    public void onMessageQuote(IMMessage iMMessage) {
        this.messageQuoteLayout.setVisibility(0);
        this.quoteMsg = iMMessage;
        QuoteMsgHelper.handleQuoteMessageShow(this.container.a, QuoteMsgHelper.getQuoteMessageType(iMMessage), QuoteMsgHelper.getQuoteMessageContent(iMMessage), this.messageQuoteTv, this.messageQuoteIv);
        this.bottomLayoutHelper.showEditor(true);
        checkSendButtonEnable();
    }

    @Override // com.qiyukf.uikit.session.emoji.IEmoticonSelectedListener
    public void onEmojiLoadSuccess(List<com.qiyukf.unicorn.f.g> list) {
        c.h().a(Long.valueOf(c.h().d(this.container.c)), list);
    }

    @Override // com.qiyukf.uikit.session.emoji.IEmoticonSelectedListener
    public boolean isEmojiLoad() {
        List<com.qiyukf.unicorn.f.g> listA = c.h().a(Long.valueOf(c.h().d(this.container.c)));
        return (listA == null || listA.size() == 0) ? false : true;
    }
}

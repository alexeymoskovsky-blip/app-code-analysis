package com.qiyukf.uikit.session.viewholder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.qiyukf.nimlib.n.y;
import com.qiyukf.nimlib.sdk.msg.attachment.AudioAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.common.media.a.a;
import com.qiyukf.uikit.session.b.b;
import com.qiyukf.uikit.session.emoji.MoonUtil;
import com.qiyukf.uikit.session.helper.ClickMovementMethod;
import com.qiyukf.uikit.session.helper.SpanUtil;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.d.c;
import com.qiyukf.unicorn.n.e;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.t;

/* JADX INFO: loaded from: classes6.dex */
public class MsgViewHolderAudio extends MsgViewHolderBase {
    private static final int CLICK_TO_PLAY_AUDIO_DELAY = 500;
    private static final int MAX_AUDIO_TIME_SECOND = 120;
    private ImageView animationView;
    private b audioControl;
    private TextView bodyTextView;
    private View containerView;
    private TextView durationLabel;
    private boolean isNeedShowToast;
    private a.InterfaceC0166a onPlayListener = new a.InterfaceC0166a() { // from class: com.qiyukf.uikit.session.viewholder.MsgViewHolderAudio.2
        @Override // com.qiyukf.uikit.common.media.a.a.InterfaceC0166a
        public void updatePlayingProgress(com.qiyukf.uikit.common.media.a.b bVar, long j) {
        }

        @Override // com.qiyukf.uikit.common.media.a.a.InterfaceC0166a
        public void onAudioControllerReady(com.qiyukf.uikit.common.media.a.b bVar) {
            if (MsgViewHolderAudio.this.isCurrent(bVar)) {
                MsgViewHolderAudio.this.play();
            }
        }

        @Override // com.qiyukf.uikit.common.media.a.a.InterfaceC0166a
        public void onEndPlay(com.qiyukf.uikit.common.media.a.b bVar) {
            if (MsgViewHolderAudio.this.isCurrent(bVar)) {
                MsgViewHolderAudio.this.updateTime(bVar.a());
                MsgViewHolderAudio.this.stop();
            }
        }
    };
    private View unreadIndicator;

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int leftBackground() {
        return 0;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int rightBackground() {
        return 0;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_message_item_audio;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.durationLabel = (TextView) findViewById(R.id.message_item_audio_duration);
        this.containerView = findViewById(R.id.message_item_audio_container);
        this.unreadIndicator = findViewById(R.id.message_item_audio_unread_indicator);
        this.animationView = (ImageView) findViewById(R.id.message_item_audio_playing_animation);
        this.bodyTextView = (TextView) findViewById(R.id.nim_message_item_text_body);
        this.audioControl = b.h();
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        AudioAttachment audioAttachment = (AudioAttachment) this.message.getAttachment();
        if (audioAttachment.getAutoTransform() && audioAttachment.getText() != null) {
            setShowText(true);
            layoutText();
            this.bodyTextView.setText(getDisplayText());
            TextView textView = this.bodyTextView;
            textView.setLinkTextColor(linkColor(textView));
            this.bodyTextView.setOnTouchListener(ClickMovementMethod.newInstance());
            return;
        }
        setShowText(false);
        layoutAudio();
        refreshStatus();
        controlPlaying();
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void onItemClick() {
        if (this.containerView.getVisibility() != 0 || this.audioControl == null) {
            return;
        }
        if (isReceivedMessage() && this.message.getAttachStatus() != AttachStatusEnum.transferred) {
            t.a(R.string.ysf_no_permission_audio_error);
            return;
        }
        if (this.message.getStatus() != MsgStatusEnum.read) {
            this.unreadIndicator.setVisibility(8);
        }
        this.audioControl.a(this.message, c.l() ? 0 : 3);
        this.audioControl.a(true, this.adapter, this.message);
    }

    private void setShowText(boolean z) {
        this.bodyTextView.setVisibility(z ? 0 : 8);
        this.containerView.setVisibility(z ? 8 : 0);
        this.unreadIndicator.setVisibility(z ? 8 : 0);
    }

    private void layoutText() {
        if (isReceivedMessage()) {
            this.bodyTextView.setBackgroundResource(leftBgResId());
            com.qiyukf.unicorn.m.a.a().b(this.bodyTextView);
            this.bodyTextView.setTextColor(leftTextColor());
        } else {
            this.bodyTextView.setBackgroundResource(rightBgResId());
            com.qiyukf.unicorn.m.a.a().a(this.bodyTextView);
            this.bodyTextView.setTextColor(rightTextColor());
        }
        setTextMsgSize();
    }

    private void layoutAudio() {
        if (isReceivedMessage()) {
            setGravity(this.animationView, 19);
            setGravity(this.durationLabel, 21);
            this.containerView.setBackgroundResource(leftBgResId());
            com.qiyukf.unicorn.m.a.a().b(this.animationView);
            this.animationView.setBackgroundResource(leftAudioAnimationResId());
            this.animationView.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()), PorterDuff.Mode.SRC_IN);
            this.durationLabel.setTextColor(leftTextColor());
            return;
        }
        setGravity(this.animationView, 21);
        setGravity(this.durationLabel, 19);
        this.unreadIndicator.setVisibility(8);
        this.containerView.setBackgroundResource(rightBgResId());
        com.qiyukf.unicorn.m.a.a().a(this.containerView);
        this.animationView.setBackgroundResource(rightAudioAnimationResId());
        this.animationView.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()), PorterDuff.Mode.SRC_IN);
        this.durationLabel.setTextColor(rightTextColor());
    }

    private void refreshStatus() {
        AudioAttachment audioAttachment = (AudioAttachment) this.message.getAttachment();
        MsgStatusEnum status = this.message.getStatus();
        AttachStatusEnum attachStatus = this.message.getAttachStatus();
        if (TextUtils.isEmpty(audioAttachment.getPath())) {
            if (attachStatus == AttachStatusEnum.fail || status == MsgStatusEnum.fail) {
                this.alertButton.setVisibility(0);
            } else {
                this.alertButton.setVisibility(8);
            }
        }
        if (status == MsgStatusEnum.sending || attachStatus == AttachStatusEnum.transferring) {
            this.progressBar.setVisibility(0);
        } else {
            this.progressBar.setVisibility(8);
        }
        if (isReceivedMessage() && attachStatus == AttachStatusEnum.transferred && status != MsgStatusEnum.read) {
            this.unreadIndicator.setVisibility(0);
        } else {
            this.unreadIndicator.setVisibility(8);
        }
    }

    private void controlPlaying() {
        long duration = ((AudioAttachment) this.message.getAttachment()).getDuration();
        setAudioBubbleWidth(duration);
        if (!isMessagePlaying(this.audioControl, this.message)) {
            updateTime(duration);
            stop();
        } else {
            play();
        }
        this.audioControl.a(this.onPlayListener);
    }

    private static int getAudioMaxEdge() {
        return (int) (((double) p.c()) * 0.6d);
    }

    private static int getAudioMinEdge() {
        return (int) (((double) p.c()) * 0.1875d);
    }

    private void setAudioBubbleWidth(long j) {
        int iCalculateBubbleWidth = calculateBubbleWidth(y.b(j), 120);
        ViewGroup.LayoutParams layoutParams = this.containerView.getLayoutParams();
        layoutParams.width = iCalculateBubbleWidth;
        this.containerView.setLayoutParams(layoutParams);
    }

    private int calculateBubbleWidth(long j, int i) {
        int iAtan;
        int audioMaxEdge = getAudioMaxEdge();
        int audioMinEdge = getAudioMinEdge();
        if (j <= 0) {
            iAtan = audioMinEdge;
        } else {
            iAtan = (j <= 0 || j > ((long) i)) ? audioMaxEdge : (int) ((((double) (audioMaxEdge - audioMinEdge)) * 0.6366197723675814d * Math.atan(j / 10.0d)) + ((double) audioMinEdge));
        }
        return iAtan < audioMinEdge ? audioMinEdge : iAtan > audioMaxEdge ? audioMaxEdge : iAtan;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTime(final long j) {
        e.b().post(new Runnable() { // from class: com.qiyukf.uikit.session.viewholder.MsgViewHolderAudio.1
            @Override // java.lang.Runnable
            public void run() {
                long jB = y.b(j);
                if (jB >= 0) {
                    MsgViewHolderAudio.this.durationLabel.setText(jB + "\"");
                    return;
                }
                MsgViewHolderAudio.this.durationLabel.setText("");
            }
        });
    }

    private boolean isMessagePlaying(b bVar, IMMessage iMMessage) {
        return bVar.i() != null && bVar.i().isTheSame(iMMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrent(com.qiyukf.uikit.common.media.a.b bVar) {
        return (bVar instanceof com.qiyukf.uikit.session.b.a) && ((com.qiyukf.uikit.session.b.a) bVar).c() == this.message;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void play() {
        if (this.animationView.getBackground() instanceof AnimationDrawable) {
            ((AnimationDrawable) this.animationView.getBackground()).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stop() {
        if (this.animationView.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) this.animationView.getBackground();
            animationDrawable.stop();
            animationDrawable.selectDrawable(animationDrawable.getNumberOfFrames() - 1);
        }
    }

    @Override // com.qiyukf.uikit.common.a.f
    public void reclaim() {
        this.audioControl.b(this.onPlayListener);
        stop();
        super.reclaim();
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void onDetached() {
        this.audioControl.b(this.onPlayListener);
        stop();
        super.onDetached();
    }

    private int leftBgResId() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        return (uICustomization == null || (i = uICustomization.msgItemBackgroundLeft) <= 0) ? R.drawable.ysf_msg_back_left_selector : i;
    }

    private int rightBgResId() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        return (uICustomization == null || (i = uICustomization.msgItemBackgroundRight) <= 0) ? R.drawable.ysf_msg_blue_back_rigth_selector : i;
    }

    private int leftTextColor() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        if (uICustomization != null && (i = uICustomization.textMsgColorLeft) != 0) {
            return i;
        }
        if (com.qiyukf.unicorn.m.a.a().c()) {
            return Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f());
        }
        return -16777216;
    }

    private int rightTextColor() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        if (uICustomization != null && (i = uICustomization.textMsgColorRight) != 0) {
            return i;
        }
        if (com.qiyukf.unicorn.m.a.a().c()) {
            return Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d());
        }
        return -1;
    }

    private int leftAudioAnimationResId() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        return (uICustomization == null || (i = uICustomization.audioMsgAnimationLeft) <= 0) ? R.drawable.ysf_audio_animation_list_left : i;
    }

    private int rightAudioAnimationResId() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        return (uICustomization == null || (i = uICustomization.audioMsgAnimationRight) <= 0) ? R.drawable.ysf_audio_animation_list_right : i;
    }

    private CharSequence getDisplayText() {
        return SpanUtil.replaceWebLinks(this.context, MoonUtil.replaceEmoticons(this.context, ((AudioAttachment) this.message.getAttachment()).getText()));
    }

    private void setTextMsgSize() {
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        if (uICustomization != null) {
            float f = uICustomization.textMsgSize;
            if (f > 0.0f) {
                this.bodyTextView.setTextSize(f);
            }
        }
    }

    private int linkColor(TextView textView) {
        int i;
        int i2;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        if (uICustomization != null) {
            if (isReceivedMessage() && (i2 = uICustomization.hyperLinkColorLeft) != 0) {
                return i2;
            }
            if (!isReceivedMessage() && (i = uICustomization.hyperLinkColorRight) != 0) {
                return i;
            }
        }
        int currentTextColor = textView.getCurrentTextColor();
        return (16777215 & currentTextColor) != 0 ? currentTextColor : textView.getContext().getResources().getColor(R.color.ysf_text_link_color_blue);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.viewholder.MsgViewHolderAudio$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$qiyukf$nimlib$sdk$msg$constant$MsgStatusEnum;

        static {
            int[] iArr = new int[MsgStatusEnum.values().length];
            $SwitchMap$com$qiyukf$nimlib$sdk$msg$constant$MsgStatusEnum = iArr;
            try {
                iArr[MsgStatusEnum.fail.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$qiyukf$nimlib$sdk$msg$constant$MsgStatusEnum[MsgStatusEnum.sending.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$qiyukf$nimlib$sdk$msg$constant$MsgStatusEnum[MsgStatusEnum.unread.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$qiyukf$nimlib$sdk$msg$constant$MsgStatusEnum[MsgStatusEnum.read.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void setStatus() {
        int i = AnonymousClass3.$SwitchMap$com$qiyukf$nimlib$sdk$msg$constant$MsgStatusEnum[this.message.getStatus().ordinal()];
        if (i == 1) {
            this.progressBar.setVisibility(8);
            this.alertButton.setVisibility(0);
            if (((AudioAttachment) this.message.getAttachment()).getAutoTransform() && this.isNeedShowToast) {
                t.a(R.string.ysf_audio_translate_to_text_failed);
                this.isNeedShowToast = false;
                return;
            }
            return;
        }
        if (i == 2) {
            this.progressBar.setVisibility(0);
            this.alertButton.setVisibility(8);
            this.isNeedShowToast = true;
            return;
        }
        if (i == 3) {
            if (com.qiyukf.unicorn.c.h().p(this.message.getSessionId()) != null && "1".equals(com.qiyukf.unicorn.c.h().p(this.message.getSessionId()).a())) {
                this.progressBar.setVisibility(8);
                this.alertButton.setVisibility(8);
                this.tvMessageItemReadStatus.setVisibility(0);
                this.tvMessageItemReadStatus.setText(R.string.ysf_message_unread);
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    this.tvMessageItemReadStatus.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().k()));
                    return;
                } else {
                    this.tvMessageItemReadStatus.setTextColor(Color.rgb(51, 136, 255));
                    return;
                }
            }
            this.progressBar.setVisibility(8);
            this.alertButton.setVisibility(8);
            this.tvMessageItemReadStatus.setVisibility(8);
            return;
        }
        if (i == 4) {
            if (com.qiyukf.unicorn.c.h().p(this.message.getSessionId()) == null || !"1".equals(com.qiyukf.unicorn.c.h().p(this.message.getSessionId()).a())) {
                return;
            }
            this.progressBar.setVisibility(8);
            this.alertButton.setVisibility(8);
            this.tvMessageItemReadStatus.setVisibility(0);
            this.tvMessageItemReadStatus.setText(R.string.ysf_message_read);
            if (com.qiyukf.unicorn.m.a.a().c()) {
                this.tvMessageItemReadStatus.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
                return;
            } else {
                this.tvMessageItemReadStatus.setTextColor(Color.rgb(177, 177, 177));
                return;
            }
        }
        this.progressBar.setVisibility(8);
        this.alertButton.setVisibility(8);
    }
}

package com.qiyukf.unicorn.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.sdk.AbortableFuture;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.attachment.AudioAttachment;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.common.fragment.TFragment;
import com.qiyukf.uikit.session.helper.QuoteMsgHelper;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.h.a.f.m;
import com.qiyukf.unicorn.m.a;
import com.qiyukf.unicorn.n.f;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.w;

/* JADX INFO: loaded from: classes6.dex */
public class TranslateFragment extends TFragment {
    private static final String AUDIO_MESSAGE = "msg";
    private static final String CONTENT = "content";
    private static final String TYPE = "type";
    private AudioAttachment audioAttachment;
    private RequestCallbackWrapper<String> callback = new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.ui.fragment.TranslateFragment.3
        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public void onResult(int i, String str, Throwable th) {
            TranslateFragment.this.onTranslateCompleted();
            if (i == 200) {
                TranslateFragment.this.translateText.setTextSize(22.0f);
                TranslateFragment.this.translateText.setText(str);
            } else {
                TranslateFragment.this.onTranslateFailed();
            }
        }
    };
    private Button cancelButton;
    private AbortableFuture<String> future;
    private IMMessage message;
    private ProgressBar progressBar;
    private m quoteMsgAttachment;
    private TextView translateText;

    public static TranslateFragment newInstance(IMMessage iMMessage) {
        TranslateFragment translateFragment = new TranslateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("msg", iMMessage);
        translateFragment.setArguments(bundle);
        return translateFragment;
    }

    public static TranslateFragment newInstance(IMMessage iMMessage, String str, String str2) {
        TranslateFragment translateFragment = new TranslateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("msg", iMMessage);
        bundle.putSerializable("type", str);
        bundle.putSerializable("content", str2);
        translateFragment.setArguments(bundle);
        return translateFragment;
    }

    @Override // com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        IMMessage iMMessage;
        super.onCreate(bundle);
        if (getArguments() == null || (iMMessage = (IMMessage) getArguments().getSerializable("msg")) == null) {
            return;
        }
        this.message = iMMessage;
        if (iMMessage.getAttachment() != null) {
            if (iMMessage.getAttachment() instanceof AudioAttachment) {
                this.audioAttachment = (AudioAttachment) iMMessage.getAttachment();
            } else if (iMMessage.getAttachment() instanceof m) {
                this.quoteMsgAttachment = (m) iMMessage.getAttachment();
            }
        }
    }

    @Override // com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.ysf_fragment_translate, viewGroup, false);
        this.cancelButton = (Button) viewInflate.findViewById(R.id.ysf_translate_cancel_button);
        this.translateText = (TextView) viewInflate.findViewById(R.id.ysf_translated_text);
        this.progressBar = (ProgressBar) viewInflate.findViewById(R.id.ysf_message_item_progress);
        if (a.a().c()) {
            this.translateText.setTextColor(Color.parseColor(a.a().b().s().f()));
            if (w.a()) {
                viewInflate.setBackgroundColor(getResources().getColor(R.color.ysf_dark_module));
            }
        }
        return viewInflate;
    }

    @Override // com.qiyukf.uikit.common.fragment.TFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        initViews();
        AudioAttachment audioAttachment = this.audioAttachment;
        if (audioAttachment != null) {
            getTranslateFromServer(audioAttachment);
            return;
        }
        if (this.quoteMsgAttachment != null) {
            int dimension = (int) getActivity().getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width);
            String strD = this.quoteMsgAttachment.d();
            String strC = this.quoteMsgAttachment.c();
            if (TextUtils.equals(QuoteMsgHelper.QUOTE_MSG_TYPE_RICH_TEXT, strD)) {
                strC = j.e(j.a(strC), "content");
            }
            f.a(this.translateText, strC, dimension, this.message.getSessionId());
            onTranslateCompleted();
            return;
        }
        String str = (String) getArguments().getSerializable("type");
        String strE = (String) getArguments().getSerializable("content");
        int dimension2 = (int) getActivity().getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width);
        if (TextUtils.equals(QuoteMsgHelper.QUOTE_MSG_TYPE_RICH_TEXT, str)) {
            strE = j.e(j.a(strE), "content");
        }
        f.a(this.translateText, strE, dimension2, this.message.getSessionId());
        onTranslateCompleted();
    }

    private void initViews() {
        if (getActivity() == null) {
            return;
        }
        this.cancelButton.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.TranslateFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TranslateFragment.this.getActivity().onBackPressed();
            }
        });
        if (getView() != null) {
            getView().setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.fragment.TranslateFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TranslateFragment.this.cancelButton.getVisibility() == 8) {
                        TranslateFragment.this.getActivity().onBackPressed();
                    }
                }
            });
        }
    }

    private void getTranslateFromServer(AudioAttachment audioAttachment) {
        AbortableFuture<String> abortableFutureTransVoiceToText = ((MsgService) NIMClient.getService(MsgService.class)).transVoiceToText(audioAttachment.getUrl(), audioAttachment.getPath(), audioAttachment.getDuration());
        this.future = abortableFutureTransVoiceToText;
        abortableFutureTransVoiceToText.setCallback(this.callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTranslateCompleted() {
        this.progressBar.setVisibility(8);
        this.cancelButton.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTranslateFailed() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ysf_ic_failed);
        int iA = p.a(24.0f);
        drawable.setBounds(0, 0, iA, iA);
        this.translateText.setCompoundDrawables(drawable, null, null, null);
        this.translateText.setCompoundDrawablePadding(p.a(6.0f));
        this.translateText.setText(getString(R.string.ysf_audio_translate_failed));
        this.translateText.setTextSize(15.0f);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        AbortableFuture<String> abortableFuture;
        if (this.cancelButton.getVisibility() == 0 && (abortableFuture = this.future) != null) {
            abortableFuture.abort();
        }
        super.onDetach();
    }
}

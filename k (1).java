package com.qiyukf.unicorn.ui.viewholder;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.uikit.session.activity.WatchVideoActivity;
import com.qiyukf.uikit.session.helper.QuoteMsgHelper;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderText;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.ui.activity.FileDownloadActivity;
import com.qiyukf.unicorn.ui.activity.UrlImagePreviewActivity;
import com.qiyukf.unicorn.ui.fragment.TranslateFragment;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: compiled from: MsgViewHolderMessageQuote.java */
/* JADX INFO: loaded from: classes6.dex */
public class k extends MsgViewHolderText {
    private com.qiyukf.unicorn.h.a.f.m a;
    private TextView b;
    private ImageView c;
    private View d;
    private TextView e;
    private String f;
    private String g;

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderText, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        super.inflateContentView();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_ysf_message_item_text_reference);
        this.b = (TextView) findViewById(R.id.tv_ysf_message_item_text_reference);
        this.c = (ImageView) findViewById(R.id.iv_ysf_message_item_text_reference);
        this.d = findViewById(R.id.view_ysf_message_item_text_reference);
        this.e = (TextView) findViewById(R.id.reference_ysf_message_item_text_reference);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.d.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            this.b.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            this.e.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
        }
        linearLayout.setVisibility(0);
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderText, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        this.b.setVisibility(8);
        this.c.setVisibility(8);
        if (QuoteMsgHelper.isQuoteMessage(this.message)) {
            JSONObject jSONObjectA = com.qiyukf.nimlib.n.j.a(((com.qiyukf.nimlib.session.d) this.message).j());
            if (jSONObjectA != null) {
                String strE = com.qiyukf.nimlib.n.j.e(jSONObjectA, "quoteMessage");
                if (!TextUtils.isEmpty(strE)) {
                    JSONObject jSONObjectA2 = com.qiyukf.nimlib.n.j.a(strE);
                    this.f = com.qiyukf.nimlib.n.j.e(jSONObjectA2, "type");
                    this.g = com.qiyukf.nimlib.n.j.e(jSONObjectA2, "content");
                }
            }
        } else {
            com.qiyukf.unicorn.h.a.f.m mVar = (com.qiyukf.unicorn.h.a.f.m) this.message.getAttachment();
            this.a = mVar;
            this.f = mVar.d();
            this.g = this.a.c();
        }
        QuoteMsgHelper.handleQuoteMessageShow(this.context, this.f, this.g, this.b, this.c);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.k.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String quoteMessageContent = QuoteMsgHelper.getQuoteMessageContent(k.this.f, k.this.g);
                if (TextUtils.equals("image", k.this.f)) {
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(quoteMessageContent);
                    UrlImagePreviewActivity.start(((com.qiyukf.uikit.common.a.f) k.this).context, arrayList, 0);
                }
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.k.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (TextUtils.equals("video", k.this.f)) {
                    String strE2 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(k.this.g), "url");
                    if (TextUtils.isEmpty(strE2)) {
                        return;
                    }
                    WatchVideoActivity.start(((com.qiyukf.uikit.common.a.f) k.this).context, strE2);
                    return;
                }
                if (TextUtils.equals("text", k.this.f) || TextUtils.equals(QuoteMsgHelper.QUOTE_MSG_TYPE_RICH_TEXT, k.this.f)) {
                    if (((com.qiyukf.uikit.common.a.f) k.this).context instanceof Activity) {
                        if (k.this.a != null) {
                            ((FragmentActivity) ((com.qiyukf.uikit.common.a.f) k.this).context).getSupportFragmentManager().beginTransaction().add(android.R.id.content, TranslateFragment.newInstance(((MsgViewHolderBase) k.this).message)).addToBackStack(null).commitAllowingStateLoss();
                            return;
                        } else {
                            ((FragmentActivity) ((com.qiyukf.uikit.common.a.f) k.this).context).getSupportFragmentManager().beginTransaction().add(android.R.id.content, TranslateFragment.newInstance(((MsgViewHolderBase) k.this).message, k.this.f, k.this.g)).addToBackStack(null).commitAllowingStateLoss();
                            return;
                        }
                    }
                    return;
                }
                if (TextUtils.equals("file", k.this.f)) {
                    if (k.this.a != null) {
                        String strA = k.this.a.a();
                        IMMessage iMMessageQueryMessageByUuid = MsgDBHelper.queryMessageByUuid(strA);
                        if (iMMessageQueryMessageByUuid == null) {
                            iMMessageQueryMessageByUuid = MsgDBHelper.queryMessageByUuid(strA.substring(strA.indexOf(35) + 1));
                        }
                        if (iMMessageQueryMessageByUuid == null || !(iMMessageQueryMessageByUuid.getAttachment() instanceof FileAttachment)) {
                            return;
                        }
                        FileDownloadActivity.start(((com.qiyukf.uikit.common.a.f) k.this).context, iMMessageQueryMessageByUuid);
                        return;
                    }
                    try {
                        String string = com.qiyukf.nimlib.n.j.a(k.this.g).getString("url");
                        String string2 = com.qiyukf.nimlib.n.j.a(k.this.g).getString("name");
                        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                            return;
                        }
                        String strA2 = k.a(string);
                        File file = new File(strA2);
                        if (file.exists()) {
                            k.a(k.this, string2, file);
                        } else {
                            k.a(k.this, string, strA2, string2);
                        }
                    } catch (Exception e) {
                        AbsUnicornLog.e("MsgViewHolderMessageQuo", "bindContentView: " + e.getMessage());
                    }
                }
            }
        });
    }

    public static /* synthetic */ String a(String str) {
        return com.qiyukf.unicorn.n.e.d.a(com.qiyukf.nimlib.n.m.a(str), com.qiyukf.unicorn.n.e.c.TYPE_FILE);
    }

    public static /* synthetic */ void a(k kVar, String str, File file) {
        FileDownloadActivity.start(kVar.context, MessageBuilder.createFileMessage(kVar.message.getSessionId(), kVar.message.getSessionType(), file, str));
    }

    public static /* synthetic */ void a(k kVar, String str, final String str2, final String str3) {
        if (TextUtils.isEmpty(str) || str2 == null) {
            com.qiyukf.unicorn.n.t.a(R.string.ysf_download_video_fail);
        } else {
            com.qiyukf.nimlib.net.a.a.g.a().a(new com.qiyukf.nimlib.net.a.a.e(str, str2, new com.qiyukf.nimlib.net.a.a.f() { // from class: com.qiyukf.unicorn.ui.viewholder.k.3
                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onCancel(com.qiyukf.nimlib.net.a.a.e eVar) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onExpire(com.qiyukf.nimlib.net.a.a.e eVar, String str4) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onFail(com.qiyukf.nimlib.net.a.a.e eVar, String str4) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onGetLength(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onProgress(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onStart(com.qiyukf.nimlib.net.a.a.e eVar) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public final void onOK(com.qiyukf.nimlib.net.a.a.e eVar) {
                    k.a(k.this, str3, new File(str2));
                }
            }));
        }
    }
}

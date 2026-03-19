package com.qiyukf.unicorn.ui.viewholder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.danikula.videocache.sourcestorage.DatabaseSourceInfoStorage;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.uikit.session.activity.WatchVideoActivity;
import com.qiyukf.uikit.session.emoji.MoonUtil;
import com.qiyukf.uikit.session.helper.QuoteMsgHelper;
import com.qiyukf.uikit.session.helper.SpanUtil;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderText;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.ui.activity.FileDownloadActivity;
import com.qiyukf.unicorn.ui.activity.UrlImagePreviewActivity;
import com.qiyukf.unicorn.ui.fragment.TranslateFragment;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.XMLReader;

/* JADX INFO: compiled from: MsgViewHolderMessageReference.java */
/* JADX INFO: loaded from: classes6.dex */
public class l extends MsgViewHolderText {
    private com.qiyukf.unicorn.h.a.d.q a;
    private TextView b;
    private ImageView c;
    private View d;
    private TextView e;

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
        String string;
        this.a = (com.qiyukf.unicorn.h.a.d.q) this.message.getAttachment();
        super.bindContentView();
        this.b.setVisibility(8);
        this.c.setVisibility(8);
        String strB = this.a.c().b();
        strB.hashCode();
        switch (strB) {
            case "richtext":
                this.b.setVisibility(0);
                String strE = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), "content");
                if (com.qiyukf.unicorn.n.f.b(strE)) {
                    Html.fromHtml(strE, null, new a());
                } else {
                    this.b.setText(com.qiyukf.unicorn.n.f.a(com.qiyukf.unicorn.n.f.a(strE)).replace("\n", " "));
                }
                this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.l.5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ((FragmentActivity) ((com.qiyukf.uikit.common.a.f) l.this).context).getSupportFragmentManager().beginTransaction().add(android.R.id.content, TranslateFragment.newInstance(((MsgViewHolderBase) l.this).message, QuoteMsgHelper.QUOTE_MSG_TYPE_RICH_TEXT, l.this.a.c().a())).addToBackStack(null).commitAllowingStateLoss();
                    }
                });
                break;
            case "custom":
                this.b.setVisibility(0);
                String strE2 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), "title");
                String strE3 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), SocialConstants.PARAM_APP_DESC);
                if (this.a.c().a().contains("orderId")) {
                    if (strE2 != null) {
                        string = this.context.getString(R.string.ysf_msg_notify_order) + strE2;
                    } else if (strE3 != null) {
                        string = this.context.getString(R.string.ysf_msg_notify_order) + strE3;
                    } else {
                        string = this.context.getString(R.string.ysf_msg_notify_order);
                    }
                } else if (strE2 != null) {
                    string = this.context.getString(R.string.ysf_msg_notify_card) + strE2;
                } else if (strE3 != null) {
                    string = this.context.getString(R.string.ysf_msg_notify_card) + strE3;
                } else {
                    string = this.context.getString(R.string.ysf_msg_notify_card);
                }
                this.b.setText(string);
                break;
            case "file":
                this.b.setVisibility(0);
                final String strE4 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), "name");
                final String strE5 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), "url");
                this.b.setText(this.context.getString(R.string.ysf_msg_notify_file) + strE4);
                this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.l.4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        try {
                            if (TextUtils.isEmpty(strE5) || TextUtils.isEmpty(strE4)) {
                                return;
                            }
                            String strA = l.a(strE5);
                            File file = new File(strA);
                            if (file.exists()) {
                                l.a(l.this, strE4, file);
                            } else {
                                l.a(l.this, strE5, strA, strE4);
                            }
                        } catch (Exception e) {
                            AbsUnicornLog.e("MsgViewHolderMessageRef", "bindContentView: " + e.getMessage());
                        }
                    }
                });
                break;
            case "text":
                this.b.setVisibility(0);
                this.b.setText(this.a.c().a());
                this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.l.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ((FragmentActivity) ((com.qiyukf.uikit.common.a.f) l.this).context).getSupportFragmentManager().beginTransaction().add(android.R.id.content, TranslateFragment.newInstance(((MsgViewHolderBase) l.this).message, "text", l.this.a.c().a())).addToBackStack(null).commitAllowingStateLoss();
                    }
                });
                break;
            case "audio":
                this.b.setVisibility(0);
                this.b.setText(this.context.getString(R.string.ysf_msg_notify_audio));
                break;
            case "image":
                this.c.setVisibility(0);
                final String strE6 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), "url");
                com.qiyukf.uikit.a.a(strE6, this.c);
                this.c.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.l.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ArrayList arrayList = new ArrayList(1);
                        arrayList.add(strE6);
                        UrlImagePreviewActivity.start(((com.qiyukf.uikit.common.a.f) l.this).context, arrayList, 0);
                    }
                });
                break;
            case "video":
                this.b.setVisibility(0);
                String strE7 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), "name");
                final String strE8 = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.c().a()), "url");
                this.b.setText(this.context.getString(R.string.ysf_msg_notify_video) + strE7);
                this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.l.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if (TextUtils.isEmpty(strE8)) {
                            return;
                        }
                        WatchVideoActivity.start(((com.qiyukf.uikit.common.a.f) l.this).context, strE8);
                    }
                });
                break;
            case "cardMessage":
                this.b.setVisibility(0);
                this.b.setText(this.context.getString(R.string.ysf_msg_notify_card));
                break;
        }
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderText
    public void setText(TextView textView) {
        if (this.a.b() == 1) {
            textView.setText(SpanUtil.replaceWebLinks(this.context, this.a.a()));
            return;
        }
        String strE = com.qiyukf.nimlib.n.j.e(com.qiyukf.nimlib.n.j.a(this.a.a()), "content");
        if (MoonUtil.isCustomEmojiMessage(strE)) {
            textView.setText(MoonUtil.replaceEmojiEmoticons(this.context, strE, textView));
        } else {
            com.qiyukf.unicorn.n.f.a(textView, strE, (int) textView.getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width), this.message.getSessionId());
        }
    }

    /* JADX INFO: compiled from: MsgViewHolderMessageReference.java */
    public class a implements Html.TagHandler {
        public a() {
        }

        @Override // android.text.Html.TagHandler
        @SuppressLint({"SetTextI18n"})
        public final void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader) {
            if ("html".equals(str)) {
                HashMap mapB = l.b(xMLReader);
                if (mapB.get("src") == null || mapB.get("title") == null) {
                    return;
                }
                String str2 = (String) mapB.get("title");
                l.this.b.setText("[" + str2.substring(0, str2.indexOf(".")) + "]");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HashMap<String, String> b(XMLReader xMLReader) {
        HashMap<String, String> map = new HashMap<>();
        try {
            Field declaredField = xMLReader.getClass().getDeclaredField("theNewElement");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(xMLReader);
            Field declaredField2 = obj.getClass().getDeclaredField("theAtts");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            Field declaredField3 = obj2.getClass().getDeclaredField("data");
            declaredField3.setAccessible(true);
            String[] strArr = (String[]) declaredField3.get(obj2);
            Field declaredField4 = obj2.getClass().getDeclaredField(DatabaseSourceInfoStorage.COLUMN_LENGTH);
            declaredField4.setAccessible(true);
            int iIntValue = ((Integer) declaredField4.get(obj2)).intValue();
            for (int i = 0; i < iIntValue; i++) {
                int i2 = i * 5;
                map.put(strArr[i2 + 1], strArr[i2 + 4]);
            }
        } catch (Exception unused) {
        }
        return map;
    }

    public static /* synthetic */ String a(String str) {
        return com.qiyukf.unicorn.n.e.d.a(com.qiyukf.nimlib.n.m.a(str), com.qiyukf.unicorn.n.e.c.TYPE_FILE);
    }

    public static /* synthetic */ void a(l lVar, String str, File file) {
        FileDownloadActivity.start(lVar.context, MessageBuilder.createFileMessage(lVar.message.getSessionId(), lVar.message.getSessionType(), file, str));
    }

    public static /* synthetic */ void a(l lVar, String str, final String str2, final String str3) {
        if (TextUtils.isEmpty(str) || str2 == null) {
            com.qiyukf.unicorn.n.t.a(R.string.ysf_download_video_fail);
        } else {
            com.qiyukf.nimlib.net.a.a.g.a().a(new com.qiyukf.nimlib.net.a.a.e(str, str2, new com.qiyukf.nimlib.net.a.a.f() { // from class: com.qiyukf.unicorn.ui.viewholder.l.6
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
                    l.a(l.this, str3, new File(str2));
                }
            }));
        }
    }
}

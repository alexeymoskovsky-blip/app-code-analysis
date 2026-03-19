package com.qiyukf.unicorn.ui.viewholder.a;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.helper.SendImageHelper;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.h.a.a.a.j;
import com.qiyukf.unicorn.ui.activity.UrlImagePreviewActivity;
import com.qiyukf.unicorn.widget.FileNameTextView;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: compiled from: TemplateHolderFormNotify.java */
/* JADX INFO: loaded from: classes6.dex */
public class o extends i implements DialogInterface.OnDismissListener, View.OnClickListener {
    private static String a;
    private TextView b;
    private View c;
    private Dialog d;
    private View e;
    private View f;
    private Button g;
    private com.qiyukf.unicorn.h.a.a.a.j h;
    private List<j.a> i;
    private final List<c> j = new ArrayList();

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final boolean e() {
        return false;
    }

    public static void b(String str) {
        if (a == null) {
            a = str;
        }
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_message_item_form_notify;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.b = (TextView) findViewById(R.id.ysf_message_form_title);
        this.c = findViewById(R.id.ysf_message_form_expand);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        com.qiyukf.unicorn.h.a.a.a.j jVar = (com.qiyukf.unicorn.h.a.a.a.j) this.message.getAttachment();
        this.h = jVar;
        this.i = jVar.e();
        this.b.setText(this.h.c());
        this.c.setVisibility(this.h.f() ? 8 : 0);
        this.c.setOnClickListener(this);
        if (this.h.f()) {
            return;
        }
        b();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.c) {
            long msgSessionId = getMsgSessionId();
            if (msgSessionId <= 0 || msgSessionId != com.qiyukf.unicorn.c.h().d(this.message.getSessionId())) {
                com.qiyukf.unicorn.n.t.a(R.string.ysf_bot_form_disabled);
                return;
            } else {
                if (a == null) {
                    a = this.message.getUuid();
                    b();
                    return;
                }
                return;
            }
        }
        if (view == this.e || view == this.f) {
            h();
            return;
        }
        if (view == this.g) {
            long msgSessionId2 = getMsgSessionId();
            if (msgSessionId2 <= 0 || msgSessionId2 != com.qiyukf.unicorn.c.h().d(this.message.getSessionId())) {
                com.qiyukf.unicorn.n.t.a(R.string.ysf_bot_form_disabled);
                h();
                return;
            }
            Iterator<c> it = this.j.iterator();
            boolean z = true;
            while (it.hasNext()) {
                if (!c.a(it.next())) {
                    z = false;
                }
            }
            if (z) {
                h();
                String str = (this.h.d() == null ? "" : this.h.d()) + "&msgIdClient=" + this.message.getUuid();
                for (j.a aVar : this.i) {
                    str = str + "&" + aVar.a() + URLEncodedUtils.NAME_VALUE_SEPARATOR + (aVar.f() == null ? "" : aVar.f().toString());
                }
                com.qiyukf.unicorn.h.a.a.b.a aVar2 = new com.qiyukf.unicorn.h.a.a.b.a();
                aVar2.a(this.i);
                com.qiyukf.unicorn.b.b bVar = new com.qiyukf.unicorn.b.b();
                bVar.b(str);
                bVar.a(aVar2.c());
                aVar2.a(bVar);
                getAdapter().b().b(MessageBuilder.createCustomMessage(this.message.getSessionId(), SessionTypeEnum.Ysf, aVar2));
                this.h.g();
                ((com.qiyukf.unicorn.b.a) this.h.a()).a("hasCommit", Boolean.TRUE);
                ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(this.message, true);
            }
        }
    }

    private void b() {
        if (TextUtils.equals(a, this.message.getUuid()) && this.d == null) {
            getAdapter().b().b();
            Dialog dialog = new Dialog(this.context, R.style.ysf_form_dialog_style);
            this.d = dialog;
            dialog.setContentView(R.layout.ysf_popup_window_form);
            this.d.setOnDismissListener(this);
            WindowManager.LayoutParams attributes = this.d.getWindow().getAttributes();
            attributes.width = -1;
            attributes.height = -1;
            a(this.d);
            this.d.show();
        }
    }

    private void a(Dialog dialog) {
        TextView textView = (TextView) dialog.findViewById(R.id.ysf_message_form_window_title);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.ysf_message_form_window_item_container);
        this.e = dialog.findViewById(R.id.ysf_message_form_window_placeholder);
        this.f = dialog.findViewById(R.id.ysf_message_form_window_close);
        this.g = (Button) dialog.findViewById(R.id.ysf_message_form_window_submit);
        textView.setText(this.h.c());
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.j.clear();
        for (j.a aVar : this.i) {
            c aVar2 = aVar.e() ? new a(linearLayout) : new b(linearLayout);
            aVar2.a(aVar);
            linearLayout.addView(aVar2.b());
            this.j.add(aVar2);
        }
    }

    private void h() {
        Dialog dialog = this.d;
        if (dialog != null) {
            com.qiyukf.unicorn.n.g.a(dialog.getWindow().getDecorView());
            this.d.dismiss();
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        a = null;
        this.d = null;
    }

    /* JADX INFO: compiled from: TemplateHolderFormNotify.java */
    public abstract class c {
        private TextView a;
        protected View b;
        protected j.a c;
        private View e;
        private View f;

        public abstract int a();

        public c(ViewGroup viewGroup) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(a(), viewGroup, false);
            this.b = viewInflate;
            this.a = (TextView) viewInflate.findViewById(R.id.ysf_message_form_item_label);
            this.e = this.b.findViewById(R.id.ysf_message_form_item_required);
            this.f = this.b.findViewById(R.id.ysf_message_form_item_error);
        }

        public final View b() {
            return this.b;
        }

        public void a(j.a aVar) {
            this.c = aVar;
            this.a.setText(aVar.c());
            this.e.setVisibility(aVar.d() ? 0 : 8);
        }

        public static /* synthetic */ boolean a(c cVar) {
            if (!cVar.c.d()) {
                return true;
            }
            boolean zI = cVar.c.i();
            cVar.f.setVisibility(zI ? 8 : 0);
            return zI;
        }
    }

    /* JADX INFO: compiled from: TemplateHolderFormNotify.java */
    public class b extends c implements TextWatcher {
        private EditText e;

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public b(ViewGroup viewGroup) {
            super(viewGroup);
            this.e = (EditText) this.b.findViewById(R.id.ysf_message_form_item_input_edit);
        }

        @Override // com.qiyukf.unicorn.ui.viewholder.a.o.c
        public final int a() {
            return R.layout.ysf_message_item_form_notify_item_input;
        }

        @Override // com.qiyukf.unicorn.ui.viewholder.a.o.c
        public final void a(j.a aVar) {
            super.a(aVar);
            this.e.addTextChangedListener(this);
            if (TextUtils.isEmpty(aVar.g())) {
                return;
            }
            this.e.setText(aVar.g());
            EditText editText = this.e;
            editText.setSelection(editText.length());
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            String string = editable.toString();
            String strTrim = string.replace("&", "").trim();
            if (!TextUtils.equals(string, strTrim)) {
                this.e.setText(strTrim);
                this.e.setSelection(strTrim.length());
            }
            this.c.a(strTrim);
        }
    }

    /* JADX INFO: compiled from: TemplateHolderFormNotify.java */
    public class a extends c implements View.OnClickListener {
        private Button e;
        private View f;
        private FileNameTextView g;
        private TextView h;
        private View i;
        private String j;
        private boolean k;
        private SendImageHelper.Callback l;

        public a(ViewGroup viewGroup) {
            super(viewGroup);
            this.l = new SendImageHelper.Callback() { // from class: com.qiyukf.unicorn.ui.viewholder.a.o.a.1
                @Override // com.qiyukf.uikit.session.helper.SendImageHelper.Callback
                public final void sendImage(File file, String str, boolean z) {
                    a.this.j = "file://" + file.getPath();
                    a.this.k = true;
                    a.a(a.this);
                    FileAttachment fileAttachment = new FileAttachment();
                    fileAttachment.setPath(file.getPath());
                    fileAttachment.setSize(file.length());
                    fileAttachment.setDisplayName(str);
                    ((MsgService) NIMClient.getService(MsgService.class)).sendFile(fileAttachment).setCallback(new RequestCallbackWrapper<FileAttachment>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.o.a.1.1
                        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                        public final /* synthetic */ void onResult(int i, FileAttachment fileAttachment2, Throwable th) {
                            FileAttachment fileAttachment3 = fileAttachment2;
                            if (a.this.b.getTag() == o.this.d) {
                                a.this.k = false;
                                a.a(a.this);
                                if (i == 200) {
                                    JSONObject jSONObject = new JSONObject();
                                    com.qiyukf.nimlib.n.j.a(jSONObject, "name", fileAttachment3.getDisplayName());
                                    com.qiyukf.nimlib.n.j.a(jSONObject, "size", fileAttachment3.getSize());
                                    com.qiyukf.nimlib.n.j.a(jSONObject, "url", fileAttachment3.getUrl());
                                    a.this.c.a(jSONObject);
                                    a.this.c();
                                    return;
                                }
                                com.qiyukf.unicorn.n.t.a(R.string.ysf_bot_form_upload_image_failed);
                            }
                        }
                    });
                }
            };
            this.e = (Button) this.b.findViewById(R.id.ysf_message_form_item_image_select);
            this.f = this.b.findViewById(R.id.ysf_message_form_item_image_layout);
            this.g = (FileNameTextView) this.b.findViewById(R.id.ysf_message_form_item_image_name);
            this.h = (TextView) this.b.findViewById(R.id.ysf_message_form_item_image_size);
            this.i = this.b.findViewById(R.id.ysf_message_form_item_image_delete);
        }

        @Override // com.qiyukf.unicorn.ui.viewholder.a.o.c
        public final int a() {
            return R.layout.ysf_message_item_form_notify_item_image;
        }

        @Override // com.qiyukf.unicorn.ui.viewholder.a.o.c
        public final void a(j.a aVar) {
            super.a(aVar);
            this.e.setOnClickListener(this);
            this.f.setOnClickListener(this);
            this.i.setOnClickListener(this);
            this.b.setTag(o.this.d);
            c();
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (view == this.e) {
                o.this.getAdapter().b().a(this.l);
                return;
            }
            if (view == this.f) {
                if (TextUtils.isEmpty(this.j)) {
                    return;
                }
                ArrayList arrayList = new ArrayList(1);
                arrayList.add(this.j);
                UrlImagePreviewActivity.start(((com.qiyukf.uikit.common.a.f) o.this).context, arrayList, 0);
                return;
            }
            if (view == this.i) {
                this.j = null;
                this.c.a(null);
                c();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c() {
            JSONObject jSONObjectH = this.c.h();
            if (jSONObjectH == null) {
                this.e.setVisibility(0);
                this.f.setVisibility(8);
                return;
            }
            this.e.setVisibility(8);
            this.f.setVisibility(0);
            String strE = com.qiyukf.nimlib.n.j.e(jSONObjectH, "name");
            String strA = com.qiyukf.unicorn.n.b.e.a(com.qiyukf.nimlib.n.j.b(jSONObjectH, "size"));
            this.g.setText(strE);
            this.h.setText(strA);
        }

        public static /* synthetic */ void a(a aVar) {
            aVar.e.setText(aVar.k ? R.string.ysf_bot_form_uploading_image : R.string.ysf_bot_form_upload_image);
            aVar.e.setEnabled(!aVar.k);
        }
    }
}

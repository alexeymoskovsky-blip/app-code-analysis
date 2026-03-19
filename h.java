package com.qiyukf.unicorn.ui.viewholder.a;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.internal.ServerProtocol;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.helper.ClickMovementMethod;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.UICustomization;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: TemplateHolderAutoWorkSheet.java */
/* JADX INFO: loaded from: classes6.dex */
public class h extends i {
    protected TextView a;
    protected LinearLayout b;
    protected Button c;
    protected LinearLayout d;
    private com.qiyukf.unicorn.h.a.a.a.x e;
    private com.qiyukf.unicorn.f.p f;

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_msg_holder_event_base;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.a = (TextView) findViewById(R.id.ysf_tv_msg_event_base_title);
        this.c = (Button) findViewById(R.id.ysf_btn_msg_event_base);
        this.b = (LinearLayout) findViewById(R.id.ysf_ll_msg_event_base_btn_parent);
        this.d = (LinearLayout) findViewById(R.id.ysf_divider_evaluation_event_line);
        this.a.setOnTouchListener(ClickMovementMethod.newInstance());
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        com.qiyukf.unicorn.f.p pVar;
        com.qiyukf.unicorn.f.p pVar2;
        this.e = (com.qiyukf.unicorn.h.a.a.a.x) this.message.getAttachment();
        this.f = com.qiyukf.unicorn.c.h().c(this.message.getSessionId());
        this.a.setText(TextUtils.isEmpty(this.e.e()) ? this.context.getString(R.string.ysf_please_clink_btn_input_info) : this.e.e());
        Map<String, Object> localExtension = this.message.getLocalExtension();
        if (localExtension == null || localExtension.get("AUTO_WORK_SHEET_IS_POPUP") == null || ((pVar2 = this.f) != null && pVar2.f)) {
            Map<String, Object> localExtension2 = this.message.getLocalExtension();
            if (localExtension2 == null) {
                localExtension2 = new HashMap<>();
            }
            localExtension2.put("AUTO_WORK_SHEET_IS_POPUP", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            this.message.setLocalExtension(localExtension2);
            ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(this.message, true);
            b();
        }
        this.c.setTextColor(this.context.getResources().getColor(R.color.ysf_white));
        this.c.setText(R.string.ysf_work_sheet_auth);
        if (this.e.d().equals(String.valueOf(com.qiyukf.unicorn.c.h().d(this.message.getSessionId()))) || ((pVar = this.f) != null && pVar.f && String.valueOf(pVar.g).equals(this.e.d()))) {
            if (com.qiyukf.unicorn.m.a.a().c() && this.c.getBackground() != null) {
                this.c.setBackgroundDrawable(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k()));
            } else {
                this.c.setBackgroundResource(R.drawable.ysf_evaluator_btn_first_bg);
            }
        } else {
            this.c.setBackgroundResource(R.drawable.ysf_btn_unenable_back);
        }
        if (this.e.l()) {
            this.c.setEnabled(false);
        } else {
            this.c.setEnabled(true);
        }
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.h.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                com.qiyukf.unicorn.f.p pVarC = com.qiyukf.unicorn.c.h().c(((MsgViewHolderBase) h.this).message.getSessionId());
                if (h.this.e.d().equals(String.valueOf(com.qiyukf.unicorn.c.h().d(((MsgViewHolderBase) h.this).message.getSessionId())))) {
                    h.this.b();
                    return;
                }
                if (pVarC == null || !pVarC.f || !String.valueOf(pVarC.g).equals(h.this.e.d())) {
                    if (!h.this.e.d().equals(String.valueOf(com.qiyukf.unicorn.c.h().d(((MsgViewHolderBase) h.this).message.getSessionId())))) {
                        com.qiyukf.unicorn.n.t.a(R.string.ysf_form_is_expired);
                        return;
                    } else {
                        com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_cannot_submit_form);
                        return;
                    }
                }
                h.this.b();
            }
        });
    }

    public final void b() {
        getAdapter().b().b();
        getAdapter().b().a(this.e, this.message.getSessionId(), new RequestCallback<String>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.h.2
            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onException(Throwable th) {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i) {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final /* synthetic */ void onSuccess(String str) {
                if (h.this.e == null || ((MsgViewHolderBase) h.this).message == null) {
                    return;
                }
                h.this.e.m();
                ((com.qiyukf.unicorn.b.a) h.this.e.a()).a("hasCommit", Boolean.TRUE);
                ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(((MsgViewHolderBase) h.this).message, true);
            }
        });
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final int c() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        return (uICustomization == null || (i = uICustomization.msgItemBackgroundLeft) <= 0) ? R.drawable.ysf_msg_back_left_selector : i;
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final int d() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        return (uICustomization == null || (i = uICustomization.msgItemBackgroundRight) <= 0) ? R.drawable.ysf_msg_blue_back_rigth_selector : i;
    }
}

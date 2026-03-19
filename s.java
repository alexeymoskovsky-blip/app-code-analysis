package com.qiyukf.unicorn.ui.viewholder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.AuthenticationTokenClaims;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.YSFOptions;
import com.qiyukf.unicorn.h.a.d.ar;
import com.qiyukf.unicorn.widget.dialog.CategoryDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: compiled from: MsgViewHolderStaffGroup.java */
/* JADX INFO: loaded from: classes6.dex */
public class s extends c<com.qiyukf.unicorn.f.d> {
    private HashMap<com.qiyukf.unicorn.f.d, View> b = new HashMap<>();

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final String e() {
        return null;
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final /* synthetic */ void a(View view, com.qiyukf.unicorn.f.d dVar) {
        final com.qiyukf.unicorn.f.d dVar2 = dVar;
        super.a(view, dVar2);
        if (c()) {
            ar arVar = (ar) this.message.getAttachment();
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ysf_clickable_item_staff_children);
            for (final com.qiyukf.unicorn.f.e eVar : dVar2.d()) {
                View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.ysf_message_item_clickable_item, (ViewGroup) null);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_clickable_item_text);
                textView.setText(eVar.c());
                textView.setTextColor(Color.parseColor(c.a()));
                textView.getCompoundDrawables()[0].setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().g()), PorterDuff.Mode.SRC_IN);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.s.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        s sVar = s.this;
                        s.a(sVar, ((MsgViewHolderBase) sVar).message, dVar2, eVar);
                    }
                });
                linearLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -2));
                this.b.put(dVar2, view);
            }
            if (arVar.j() == dVar2.d) {
                linearLayout.setVisibility(0);
            } else {
                linearLayout.setVisibility(8);
            }
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final /* synthetic */ void a(TextView textView, com.qiyukf.unicorn.f.d dVar) {
        textView.setText(dVar.c);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final /* bridge */ /* synthetic */ boolean a(com.qiyukf.unicorn.f.d dVar) {
        return a2(dVar);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final /* synthetic */ void b(TextView textView, com.qiyukf.unicorn.f.d dVar) {
        LinearLayout linearLayout;
        Drawable drawable;
        com.qiyukf.unicorn.f.d dVar2 = dVar;
        if (!a2(dVar2)) {
            a(this.message, dVar2);
            return;
        }
        ar arVar = (ar) this.message.getAttachment();
        boolean z = arVar.j() == dVar2.d;
        for (View view : this.b.values()) {
            if (!z) {
                TextView textView2 = (TextView) view.findViewById(R.id.ysf_clickable_item_text);
                LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.ysf_clickable_item_staff_children);
                Drawable drawable2 = this.context.getResources().getDrawable(R.drawable.ysf_message_item_clickable_item_right_arrow);
                drawable2.setBounds(0, 0, com.qiyukf.unicorn.n.p.a(2.0f), com.qiyukf.unicorn.n.p.a(16.0f));
                textView2.setCompoundDrawables(drawable2, null, null, null);
                textView2.getCompoundDrawables()[0].setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().g()), PorterDuff.Mode.SRC_IN);
                linearLayout2.setVisibility(8);
            }
        }
        View view2 = this.b.get(dVar2);
        if (view2 == null || (linearLayout = (LinearLayout) view2.findViewById(R.id.ysf_clickable_item_staff_children)) == null) {
            return;
        }
        if (z) {
            drawable = this.context.getResources().getDrawable(R.drawable.ysf_message_item_clickable_item_right_arrow);
            drawable.setBounds(0, 0, com.qiyukf.unicorn.n.p.a(2.0f), com.qiyukf.unicorn.n.p.a(16.0f));
            arVar.a(0L);
        } else {
            Drawable drawable3 = this.context.getResources().getDrawable(R.drawable.ysf_message_item_clickable_item_bottom_arrow);
            drawable3.setBounds(0, 0, com.qiyukf.unicorn.n.p.a(16.0f), com.qiyukf.unicorn.n.p.a(2.0f));
            arVar.a(dVar2.d);
            drawable = drawable3;
        }
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.getCompoundDrawables()[0].setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().g()), PorterDuff.Mode.SRC_IN);
        linearLayout.setVisibility(0);
        ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(this.message, true);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        final IMMessage iMMessage = this.message;
        YSFOptions ySFOptionsF = com.qiyukf.unicorn.c.f();
        ar arVar = (ar) iMMessage.getAttachment();
        if ((ySFOptionsF.categoryDialogStyle > 0 || arVar.i()) && arVar.d() && !arVar.g()) {
            List<com.qiyukf.unicorn.f.d> listC = arVar.c();
            CategoryDialog categoryDialog = new CategoryDialog(this.context, ySFOptionsF.categoryDialogStyle == 1 ? 17 : 80, arVar.i());
            categoryDialog.setTitle(arVar.a());
            categoryDialog.setEntryList(iMMessage, listC);
            categoryDialog.setOnClickListener(new CategoryDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.s.1
                @Override // com.qiyukf.unicorn.widget.dialog.CategoryDialog.OnClickListener
                public final void onClick(com.qiyukf.unicorn.f.d dVar, com.qiyukf.unicorn.f.e eVar) {
                    if (eVar == null) {
                        s.this.a(iMMessage, dVar);
                    } else {
                        s.a(s.this, iMMessage, dVar, eVar);
                    }
                }
            });
            categoryDialog.show();
            arVar.h();
            ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage, true);
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final String d() {
        return ((ar) this.message.getAttachment()).a();
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final List<com.qiyukf.unicorn.f.d> f() {
        ar arVar = (ar) this.message.getAttachment();
        if (!arVar.d()) {
            return new ArrayList();
        }
        return arVar.c();
    }

    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
    private static boolean a2(com.qiyukf.unicorn.f.d dVar) {
        return (dVar.d() == null || dVar.d().isEmpty()) ? false : true;
    }

    private ar a(IMMessage iMMessage) {
        ar arVar = (ar) iMMessage.getAttachment();
        long jCurrentTimeMillis = System.currentTimeMillis() - iMMessage.getTime();
        com.qiyukf.unicorn.c.h();
        if (jCurrentTimeMillis > AuthenticationTokenClaims.MAX_TIME_SINCE_TOKEN_ISSUED) {
            com.qiyukf.unicorn.n.t.b(this.context.getString(R.string.ysf_message_expired_and_input_message_use_service));
            ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage, true);
            return null;
        }
        if (!arVar.d()) {
            return null;
        }
        arVar.e();
        ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage, true);
        return arVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(IMMessage iMMessage, com.qiyukf.unicorn.f.d dVar) {
        ar arVarA = a(iMMessage);
        if (arVarA == null) {
            return;
        }
        a(dVar.c);
        com.qiyukf.unicorn.f.u uVar = new com.qiyukf.unicorn.f.u(iMMessage.getSessionId());
        uVar.a(true);
        uVar.a(dVar);
        AbsUnicornLog.i("MsgViewHolderStaffGroup", "ServiceMessageFragment requestStaff 7");
        uVar.a(dVar.a);
        uVar.b(arVarA.i());
        com.qiyukf.unicorn.c.h().a(uVar);
    }

    private void a(String str) {
        IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(this.message.getSessionId(), SessionTypeEnum.Ysf, str);
        iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
        ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageCreateTextMessage, true);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final boolean c() {
        ar arVar = (ar) this.message.getAttachment();
        long jCurrentTimeMillis = System.currentTimeMillis() - this.message.getTime();
        com.qiyukf.unicorn.c.h();
        if (jCurrentTimeMillis > AuthenticationTokenClaims.MAX_TIME_SINCE_TOKEN_ISSUED) {
            return false;
        }
        return arVar.d();
    }

    public static /* synthetic */ void a(s sVar, IMMessage iMMessage, com.qiyukf.unicorn.f.d dVar, com.qiyukf.unicorn.f.e eVar) {
        ar arVarA = sVar.a(iMMessage);
        if (arVarA != null) {
            sVar.a(dVar.c + "-" + eVar.c());
            dVar.b = eVar.a();
            dVar.a = eVar.b();
            dVar.c = eVar.c();
            dVar.d = eVar.d();
            com.qiyukf.unicorn.f.u uVar = new com.qiyukf.unicorn.f.u(iMMessage.getSessionId());
            uVar.a(true);
            uVar.a(dVar);
            AbsUnicornLog.i("MsgViewHolderStaffGroup", "ServiceMessageFragment requestStaff 7");
            uVar.a(dVar.a);
            uVar.b(arVarA.i());
            uVar.a((Integer) 2);
            com.qiyukf.unicorn.c.h().a(uVar);
        }
    }
}

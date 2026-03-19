package com.qiyukf.unicorn.ui.viewholder.a;

import android.os.Handler;
import android.widget.TextView;
import com.facebook.internal.ServerProtocol;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.msg.MessageService;
import com.qiyukf.unicorn.ui.botproductlist.BotProductDetailDoneDialog;
import com.qiyukf.unicorn.ui.botproductlist.ProductAndOrderListDialog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: compiled from: TemplateHolderDrawerList.java */
/* JADX INFO: loaded from: classes6.dex */
public class n extends i {
    private com.qiyukf.unicorn.h.a.a.a.i a;
    private TextView b;

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_message_action_custom_layout;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.b = (TextView) findViewById(R.id.ysf_tv_holder_drawer_list);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        com.qiyukf.unicorn.h.a.a.a.i iVar = (com.qiyukf.unicorn.h.a.a.a.i) this.message.getAttachment();
        this.a = iVar;
        this.b.setText(iVar.d());
        Map<String, Object> localExtension = this.message.getLocalExtension();
        if (localExtension == null || localExtension.get("DRAWER_DIALOG_IS_OPEN_TAG") == null) {
            Map<String, Object> localExtension2 = this.message.getLocalExtension();
            if (localExtension2 == null) {
                localExtension2 = new HashMap<>();
            }
            localExtension2.put("DRAWER_DIALOG_IS_OPEN_TAG", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            this.message.setLocalExtension(localExtension2);
            ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(this.message, true);
            Iterator<com.qiyukf.unicorn.h.a.c.f> it = this.a.f().iterator();
            while (it.hasNext()) {
                it.next().b("drawer_list");
            }
            final ProductAndOrderListDialog productAndOrderListDialog = new ProductAndOrderListDialog(this.context, this.a.f(), this.a.c(), this.a.e());
            productAndOrderListDialog.setClickCallback(new BotProductDetailDoneDialog.ClickCallback() { // from class: com.qiyukf.unicorn.ui.viewholder.a.n.1
                @Override // com.qiyukf.unicorn.ui.botproductlist.BotProductDetailDoneDialog.ClickCallback
                public final void onDoneClick(com.qiyukf.unicorn.h.a.c.b bVar) {
                    if ("url".equals(bVar.i())) {
                        com.qiyukf.unicorn.c.f().onMessageItemClickListener.onURLClicked(((com.qiyukf.uikit.common.a.f) n.this).context, bVar.j());
                        return;
                    }
                    if ("block".equals(bVar.i())) {
                        com.qiyukf.unicorn.b.b.c cVar = new com.qiyukf.unicorn.b.b.c();
                        cVar.fromJson(bVar.a());
                        cVar.a(true);
                        cVar.b(n.this.b().toString());
                        com.qiyukf.unicorn.b.b bVar2 = new com.qiyukf.unicorn.b.b();
                        bVar2.a(cVar.j());
                        bVar2.b(cVar.k());
                        bVar2.a(cVar.c());
                        cVar.a(bVar2);
                        MessageService.sendMessage(MessageBuilder.createCustomMessage(((MsgViewHolderBase) n.this).message.getSessionId(), SessionTypeEnum.Ysf, cVar));
                        productAndOrderListDialog.cancel();
                    }
                }
            });
            new Handler().postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.viewholder.a.n.2
                @Override // java.lang.Runnable
                public final void run() {
                    if (((com.qiyukf.uikit.common.a.f) n.this).context != null) {
                        try {
                            productAndOrderListDialog.show();
                        } catch (Exception unused) {
                        }
                    }
                }
            }, 500L);
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final int c() {
        int i;
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        return (uICustomization == null || (i = uICustomization.msgRobotItemBackgroundLeft) <= 0) ? R.drawable.ysf_msg_back_left_selector : i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        com.qiyukf.nimlib.n.j.a(jSONObject, "MSG_CLIENT_ID_TAG", this.message.getUuid());
        return jSONObject;
    }
}

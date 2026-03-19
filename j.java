package com.qiyukf.unicorn.ui.viewholder.a;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.msg.MessageService;
import com.qiyukf.unicorn.ui.botproductlist.BotProductDetailDoneDialog;
import com.qiyukf.unicorn.ui.botproductlist.ProductAndOrderListDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: compiled from: TemplateHolderBubbleList.java */
/* JADX INFO: loaded from: classes6.dex */
public class j extends i {
    private com.qiyukf.unicorn.h.a.a.a.c a;
    private TextView b;
    private LinearLayout c;
    private TextView d;
    private TextView e;
    private View f;

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        com.qiyukf.unicorn.h.a.a.a.c cVar = (com.qiyukf.unicorn.h.a.a.a.c) this.message.getAttachment();
        this.a = cVar;
        this.b.setText(cVar.c());
        if (this.message.getLocalExtension() != null && this.message.getLocalExtension().get("IS_SEND_PRODUCT_TAG") != null && ((Boolean) this.message.getLocalExtension().get("IS_SEND_PRODUCT_TAG")).booleanValue()) {
            this.d.setVisibility(8);
            this.f.setVisibility(8);
            this.c.setVisibility(8);
            return;
        }
        this.c.setVisibility(0);
        this.d.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.j.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (!j.this.g()) {
                    com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_msg_invalid);
                    return;
                }
                ArrayList arrayList = new ArrayList();
                com.qiyukf.unicorn.h.a.c.f fVar = new com.qiyukf.unicorn.h.a.c.f();
                fVar.a(j.this.a.f());
                fVar.a("bubble_list");
                fVar.a(j.this.a.e());
                arrayList.add(fVar);
                final ProductAndOrderListDialog productAndOrderListDialog = new ProductAndOrderListDialog(((com.qiyukf.uikit.common.a.f) j.this).context, arrayList, j.this.a.g(), j.this.a.d());
                productAndOrderListDialog.setClickCallback(new BotProductDetailDoneDialog.ClickCallback() { // from class: com.qiyukf.unicorn.ui.viewholder.a.j.1.1
                    @Override // com.qiyukf.unicorn.ui.botproductlist.BotProductDetailDoneDialog.ClickCallback
                    public final void onDoneClick(com.qiyukf.unicorn.h.a.c.b bVar) {
                        if ("url".equals(bVar.i())) {
                            com.qiyukf.unicorn.c.f().onMessageItemClickListener.onURLClicked(((com.qiyukf.uikit.common.a.f) j.this).context, bVar.j());
                            return;
                        }
                        if ("block".equals(bVar.i())) {
                            if (!j.this.g()) {
                                com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_msg_invalid);
                            } else {
                                j.a(j.this, bVar);
                                productAndOrderListDialog.cancel();
                            }
                        }
                    }
                });
                productAndOrderListDialog.show();
            }
        });
        if (this.a.f().size() <= 4) {
            this.d.setVisibility(8);
            this.f.setVisibility(8);
            a(this.a.f(), this.a.f().size());
        } else {
            this.f.setVisibility(0);
            this.d.setVisibility(0);
            a(this.a.f(), 4);
        }
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_holder_bubble_list;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.b = (TextView) findViewById(R.id.ysf_tv_holder_bubble_list_title);
        this.c = (LinearLayout) findViewById(R.id.ysf_ll_holder_bubble_list_parent);
        this.d = (TextView) findViewById(R.id.ysf_tv_holder_bubble_list_more);
        this.e = (TextView) findViewById(R.id.ysf_tv_holder_bubble_list_empty);
        this.f = findViewById(R.id.ysf_holder_bubble_list_line);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.d.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().k()));
        } else {
            this.d.setTextColor(Color.parseColor("#337EFF"));
        }
    }

    private void a(List<com.qiyukf.unicorn.h.a.c.b> list, int i) {
        if (list.size() == 0) {
            this.e.setVisibility(0);
            this.e.setText(this.a.d());
            this.c.setVisibility(8);
            return;
        }
        this.e.setVisibility(8);
        this.c.setVisibility(0);
        this.c.removeAllViews();
        int iMin = Math.min(list.size(), Math.max(0, i));
        for (int i2 = 0; i2 < iMin; i2++) {
            final com.qiyukf.unicorn.h.a.c.b bVar = list.get(i2);
            View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.ysf_item_bot_product_list, (ViewGroup) this.c, false);
            a aVar = new a(viewInflate);
            if ("1".equals(bVar.b())) {
                aVar.k.setVisibility(0);
                aVar.j.setVisibility(8);
                aVar.c.setText(bVar.d());
                if (!TextUtils.isEmpty(bVar.c())) {
                    com.qiyukf.uikit.a.a(bVar.c(), aVar.b, aVar.b.getWidth(), aVar.b.getHeight());
                    aVar.b.setVisibility(0);
                } else {
                    aVar.b.setVisibility(8);
                }
                if (!TextUtils.isEmpty(bVar.e())) {
                    aVar.l.setText(bVar.e());
                    aVar.l.setVisibility(0);
                } else {
                    aVar.l.setVisibility(8);
                }
            } else {
                aVar.k.setVisibility(8);
                aVar.j.setVisibility(0);
                if (!TextUtils.isEmpty(bVar.c())) {
                    com.qiyukf.uikit.a.a(bVar.c(), aVar.d, aVar.d.getWidth(), aVar.d.getHeight());
                    aVar.d.setVisibility(0);
                } else {
                    aVar.d.setVisibility(8);
                }
                aVar.e.setText(bVar.d());
                aVar.f.setText(bVar.f());
                aVar.g.setText(bVar.g());
                aVar.i.setText(bVar.h());
                aVar.h.setText(bVar.e());
                aVar.j.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.j.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if ("url".equals(bVar.i())) {
                            com.qiyukf.unicorn.c.f().onMessageItemClickListener.onURLClicked(((com.qiyukf.uikit.common.a.f) j.this).context, bVar.j());
                            return;
                        }
                        if ("block".equals(bVar.i())) {
                            if (!j.this.g()) {
                                com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_msg_invalid);
                                return;
                            }
                            final BotProductDetailDoneDialog botProductDetailDoneDialog = new BotProductDetailDoneDialog(((com.qiyukf.uikit.common.a.f) j.this).context);
                            botProductDetailDoneDialog.setBotProductListBean(bVar);
                            botProductDetailDoneDialog.setCallback(new BotProductDetailDoneDialog.ClickCallback() { // from class: com.qiyukf.unicorn.ui.viewholder.a.j.2.1
                                @Override // com.qiyukf.unicorn.ui.botproductlist.BotProductDetailDoneDialog.ClickCallback
                                public final void onDoneClick(com.qiyukf.unicorn.h.a.c.b bVar2) {
                                    j.a(j.this, bVar2);
                                    botProductDetailDoneDialog.dismiss();
                                }
                            });
                            botProductDetailDoneDialog.show();
                        }
                    }
                });
            }
            this.c.addView(viewInflate);
            if (i2 != iMin - 1) {
                this.c.addView(LayoutInflater.from(this.context).inflate(R.layout.ysf_include_divider, (ViewGroup) this.c, false));
            }
        }
    }

    private JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        com.qiyukf.nimlib.n.j.a(jSONObject, "MSG_CLIENT_ID_TAG", this.message.getUuid());
        return jSONObject;
    }

    /* JADX INFO: compiled from: TemplateHolderBubbleList.java */
    public class a {
        private ImageView b;
        private TextView c;
        private ImageView d;
        private TextView e;
        private TextView f;
        private TextView g;
        private TextView h;
        private TextView i;
        private RelativeLayout j;
        private LinearLayout k;
        private TextView l;

        public a(View view) {
            this.b = (ImageView) view.findViewById(R.id.ysf_iv_item_bot_product_shop_img);
            this.c = (TextView) view.findViewById(R.id.ysf_tv_item_bot_product_shop_name);
            this.l = (TextView) view.findViewById(R.id.ysf_tv_item_bot_product_sub_title);
            this.d = (ImageView) view.findViewById(R.id.ysf_iv_bot_product_detail_img);
            this.e = (TextView) view.findViewById(R.id.ysf_tv_bot_product_detail_title);
            this.f = (TextView) view.findViewById(R.id.ysf_tv_bot_product_detail_money);
            this.g = (TextView) view.findViewById(R.id.ysf_tv_bot_product_detail_sku);
            this.h = (TextView) view.findViewById(R.id.ysf_tv_bot_product_detail_info);
            this.i = (TextView) view.findViewById(R.id.ysf_tv_bot_product_detail_status);
            this.j = (RelativeLayout) view.findViewById(R.id.ysf_item_bot_product_info_parent);
            this.k = (LinearLayout) view.findViewById(R.id.ysf_ll_bot_product_shop_parent);
        }
    }

    public static /* synthetic */ void a(j jVar, com.qiyukf.unicorn.h.a.c.b bVar) {
        com.qiyukf.unicorn.b.b.c cVar = new com.qiyukf.unicorn.b.b.c();
        cVar.fromJson(bVar.a());
        cVar.a(true);
        cVar.b(jVar.b().toString());
        com.qiyukf.unicorn.b.b bVar2 = new com.qiyukf.unicorn.b.b();
        bVar2.a(cVar.j());
        bVar2.b(cVar.k());
        bVar2.a(cVar.c());
        cVar.a(bVar2);
        Map<String, Object> localExtension = jVar.message.getLocalExtension();
        if (localExtension == null) {
            localExtension = new HashMap<>();
        }
        localExtension.put("IS_SEND_PRODUCT_TAG", Boolean.TRUE);
        jVar.message.setLocalExtension(localExtension);
        ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(jVar.message, true);
        MessageService.sendMessage(MessageBuilder.createCustomMessage(jVar.message.getSessionId(), SessionTypeEnum.Ysf, cVar));
    }
}

package com.qiyukf.unicorn.ui.viewholder;

import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.report.extension.DualStackEventExtension;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.pop.POPManager;
import com.qiyukf.unicorn.api.pop.SessionListEntrance;
import com.qiyukf.unicorn.api.pop.ShopEntrance;
import com.qiyukf.unicorn.api.pop.ShopInfo;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: compiled from: MsgViewHolderEventPlatformToCorp.java */
/* JADX INFO: loaded from: classes6.dex */
public class h extends e {
    private com.qiyukf.unicorn.h.a.f.j f;

    @Override // com.qiyukf.unicorn.ui.viewholder.e, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        this.f = (com.qiyukf.unicorn.h.a.f.j) this.message.getAttachment();
        com.qiyukf.unicorn.n.f.a(this.a, String.format(this.context.getResources().getString(R.string.ysf_platform_to_corp), this.f.e()), (int) this.context.getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width), this.message.getSessionId());
        this.c.setVisibility(0);
        this.c.setText(R.string.ysf_contact_merchant_service);
        com.qiyukf.unicorn.h.a.f.j jVar = this.f;
        if (jVar.c() != 0) {
            String sessionId = this.message.getSessionId();
            if (jVar.c() == com.qiyukf.unicorn.c.h().d(sessionId) || jVar.c() == com.qiyukf.unicorn.c.h().h(sessionId)) {
                this.c.setEnabled(true);
                b();
                return;
            }
        }
        this.c.setEnabled(false);
        d();
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.e
    public final void a() {
        new com.qiyukf.unicorn.n.a<Void, Void>(com.qiyukf.unicorn.n.a.HTTP_TAG) { // from class: com.qiyukf.unicorn.ui.viewholder.h.1
            @Override // com.qiyukf.unicorn.n.a
            public final /* synthetic */ Void doInBackground(Void[] voidArr) {
                return a();
            }

            private Void a() {
                HashMap map = new HashMap();
                map.put("corpId", String.valueOf(h.this.f.b()));
                map.put("parentCorpId", String.valueOf(h.this.f.a()));
                map.put("appKey", com.qiyukf.unicorn.c.e());
                try {
                    String strA = com.qiyukf.unicorn.i.a.c.a("/webapi/user/company/check/status.action", map);
                    AbsUnicornLog.i("MsgViewHolderEventPlatf", "/webapi/user/company/check/status.action" + strA);
                    JSONObject jSONObjectA = com.qiyukf.nimlib.n.j.a(strA);
                    if (jSONObjectA == null) {
                        com.qiyukf.unicorn.n.t.a(R.string.ysf_platform_to_corp_expired);
                    }
                    boolean zC = com.qiyukf.nimlib.n.j.c(jSONObjectA, "result");
                    int iA = com.qiyukf.nimlib.n.j.a(jSONObjectA, DualStackEventExtension.KEY_CODE);
                    if (zC && iA == 200) {
                        com.qiyukf.unicorn.c.h().a(h.this.f);
                        com.qiyukf.unicorn.c.h().b(true);
                        ConsultSource consultSource = new ConsultSource(null, ((com.qiyukf.uikit.common.a.f) h.this).context.getString(R.string.ysf_from_to_platform), null);
                        consultSource.shopId = h.this.f.d();
                        consultSource.sessionListEntrance = new SessionListEntrance.Builder().build();
                        String strD = h.this.f.d();
                        ShopInfo shopInfo = POPManager.getShopInfo(h.this.f.d());
                        if (shopInfo != null) {
                            strD = shopInfo.getName();
                            consultSource.shopEntrance = new ShopEntrance.Builder().setName(shopInfo.getName()).setLogo(shopInfo.getAvatar()).build();
                        }
                        Unicorn.openServiceActivity(((com.qiyukf.uikit.common.a.f) h.this).context, strD, consultSource);
                    } else {
                        com.qiyukf.unicorn.n.t.a(R.string.ysf_platform_to_corp_expired);
                    }
                } catch (com.qiyukf.unicorn.i.a.d e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(new Void[0]);
    }
}

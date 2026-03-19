package com.qiyukf.unicorn.ui.viewholder;

import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.h.a.a.a.x;

/* JADX INFO: compiled from: MsgInviteInputWorkSheet.java */
/* JADX INFO: loaded from: classes6.dex */
public class a extends e {
    private com.qiyukf.unicorn.h.a.a.a.m f;
    private x g;

    @Override // com.qiyukf.unicorn.ui.viewholder.e, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        com.qiyukf.unicorn.h.a.a.a.m mVar = (com.qiyukf.unicorn.h.a.a.a.m) this.message.getAttachment();
        this.f = mVar;
        x xVarA = mVar.a();
        this.g = xVarA;
        this.a.setText(xVarA.f());
        if (this.f.b()) {
            this.c.setEnabled(false);
            this.c.setText(R.string.ysf_already_input_info);
            d();
        } else {
            if (f()) {
                this.c.setEnabled(true);
                b();
            } else {
                this.c.setEnabled(false);
                d();
            }
            this.c.setText(R.string.ysf_input_info_str);
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.e
    public final void a() {
        if (f()) {
            getAdapter().b().b();
            this.g.k();
            getAdapter().b().a(this.g, this.message.getSessionId(), new RequestCallback<String>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.1
                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onException(Throwable th) {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onFailed(int i) {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final /* synthetic */ void onSuccess(String str) {
                    if (a.this.g == null || ((MsgViewHolderBase) a.this).message == null) {
                        return;
                    }
                    a.this.f.c();
                    ((YsfService) NIMClient.getService(YsfService.class)).updateMessage(((MsgViewHolderBase) a.this).message, true);
                }
            });
        }
    }

    private boolean f() {
        return com.qiyukf.unicorn.c.h().e(this.message.getSessionId()) != null && String.valueOf(com.qiyukf.unicorn.c.h().d(this.message.getSessionId())).equals(this.f.a().d());
    }
}

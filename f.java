package com.qiyukf.unicorn.ui.viewholder;

import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.evaluation.EvaluationApi;
import com.qiyukf.unicorn.api.evaluation.entry.EvaluationOpenEntry;

/* JADX INFO: compiled from: MsgViewHolderEventEvaluator.java */
/* JADX INFO: loaded from: classes6.dex */
public class f extends e {
    private com.qiyukf.unicorn.h.a.f.c f;
    private com.qiyukf.unicorn.h.a.c.c g;

    @Override // com.qiyukf.unicorn.ui.viewholder.e, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        com.qiyukf.unicorn.h.a.f.c cVar = (com.qiyukf.unicorn.h.a.f.c) this.message.getAttachment();
        this.f = cVar;
        this.g = cVar.i();
        com.qiyukf.unicorn.n.f.a(this.a, this.f.a().toString(), (int) this.context.getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width), this.message.getSessionId());
        if (this.f.e() == 0) {
            this.c.setVisibility(8);
            this.d.setVisibility(8);
        } else {
            this.c.setVisibility(0);
            this.d.setVisibility(0);
        }
        if (this.f.c()) {
            if (!this.f.b()) {
                this.c.setVisibility(8);
                this.d.setVisibility(8);
            }
            this.c.setText(this.context.getString(R.string.ysf_evaluation_modify));
            c();
        } else if (this.f.j() > 0) {
            this.c.setText(this.context.getString(R.string.ysf_again_evaluation));
            c();
        } else {
            b();
            this.c.setText(R.string.ysf_immediately_evaluation);
        }
        if (this.f.k()) {
            this.c.setEnabled(false);
            d();
            this.c.setText(R.string.ysf_already_evaluation_str);
            return;
        }
        this.c.setEnabled(true);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.e
    public final void a() {
        if (com.qiyukf.unicorn.c.h().d().c(this.message.getSessionId())) {
            long jS = com.qiyukf.unicorn.d.c.s(String.valueOf(this.f.f()));
            if (jS == 0 || System.currentTimeMillis() < jS + (this.g.f().longValue() * 60000)) {
                if (this.g.m() == 2) {
                    if (com.qiyukf.unicorn.a.a().b() != null) {
                        com.qiyukf.unicorn.a.a();
                        return;
                    }
                    if (EvaluationApi.getInstance().getOnEvaluationEventListener() != null) {
                        EvaluationApi.OnEvaluationEventListener onEvaluationEventListener = EvaluationApi.getInstance().getOnEvaluationEventListener();
                        EvaluationOpenEntry evaluationOpenEntry = new EvaluationOpenEntry();
                        evaluationOpenEntry.setEvaluationEntryList(this.f.i().e());
                        evaluationOpenEntry.setExchange(this.message.getSessionId());
                        evaluationOpenEntry.setLastRemark(this.f.g());
                        evaluationOpenEntry.setLastSource(this.f.d());
                        evaluationOpenEntry.setSessionId(this.f.f());
                        evaluationOpenEntry.setTitle(this.f.i().c());
                        evaluationOpenEntry.setType(this.f.i().d());
                        evaluationOpenEntry.setResolvedEnabled(this.f.i().k());
                        evaluationOpenEntry.setResolvedRequired(this.f.i().l());
                        onEvaluationEventListener.onEvaluationMessageClick(evaluationOpenEntry, this.context);
                        return;
                    }
                    com.qiyukf.unicorn.n.t.b(R.string.ysf_custom_evaluation_page);
                    return;
                }
                com.qiyukf.unicorn.c.h().d().a(this.context, this.message);
                return;
            }
            com.qiyukf.unicorn.n.t.a(R.string.ysf_evaluation_time_out);
        }
    }
}

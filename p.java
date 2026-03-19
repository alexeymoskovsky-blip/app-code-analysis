package com.qiyukf.unicorn.ui.viewholder;

import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.evaluation.EvaluationApi;
import com.qiyukf.unicorn.api.evaluation.entry.EvaluationOpenEntry;
import com.qiyukf.unicorn.h.a.f.x;

/* JADX INFO: compiled from: MsgViewHolderRobotEvaluatorSuccess.java */
/* JADX INFO: loaded from: classes6.dex */
public class p extends e {
    private x f;
    private com.qiyukf.unicorn.h.a.c.e g;
    private long h = 0;

    @Override // com.qiyukf.unicorn.ui.viewholder.e, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        x xVar = (x) this.message.getAttachment();
        this.f = xVar;
        this.g = xVar.k();
        com.qiyukf.unicorn.n.f.a(this.a, this.g.h(), (int) this.context.getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width), this.message.getSessionId());
        this.c.setVisibility(0);
        this.d.setVisibility(0);
        if (this.f.b()) {
            if (!this.f.a()) {
                this.c.setVisibility(8);
                this.d.setVisibility(8);
            }
            this.c.setText(R.string.ysf_evaluation_modify);
            c();
        } else if (this.f.g() > 0) {
            this.c.setText(R.string.ysf_again_evaluation);
            c();
        } else {
            b();
            this.c.setText(R.string.ysf_immediately_evaluation);
        }
        if (this.f.h()) {
            this.c.setEnabled(false);
            d();
            this.c.setText(R.string.ysf_already_evaluation_str);
        } else if (!a(String.valueOf(this.f.d())) && !e()) {
            this.c.setEnabled(false);
        } else {
            this.c.setEnabled(true);
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.e
    public final void a() {
        if (System.currentTimeMillis() - this.h < 1000) {
            return;
        }
        if (!a(String.valueOf(this.f.d())) && !e()) {
            com.qiyukf.unicorn.n.t.b(R.string.ysf_session_close_cannot_evaluation);
            return;
        }
        if (this.g.c() == 2) {
            if (com.qiyukf.unicorn.a.a().b() != null) {
                com.qiyukf.unicorn.a.a();
            } else if (EvaluationApi.getInstance().getOnEvaluationEventListener() != null) {
                EvaluationApi.OnEvaluationEventListener onEvaluationEventListener = EvaluationApi.getInstance().getOnEvaluationEventListener();
                EvaluationOpenEntry evaluationOpenEntry = new EvaluationOpenEntry();
                evaluationOpenEntry.setExchange(this.message.getSessionId());
                evaluationOpenEntry.setLastRemark(this.f.e());
                evaluationOpenEntry.setLastSource(this.f.c());
                evaluationOpenEntry.setSessionId(this.f.d());
                evaluationOpenEntry.setEvaluationEntryList(this.g.k());
                evaluationOpenEntry.setTitle(this.g.e());
                evaluationOpenEntry.setType(this.g.d());
                evaluationOpenEntry.setResolvedEnabled(this.g.f());
                evaluationOpenEntry.setResolvedRequired(this.g.g());
                onEvaluationEventListener.onEvaluationMessageClick(evaluationOpenEntry, this.context);
            } else {
                com.qiyukf.unicorn.n.t.b(R.string.ysf_custom_evaluation_page);
            }
        } else {
            com.qiyukf.unicorn.c.h().d().b(this.context, this.message);
        }
        this.h = System.currentTimeMillis();
    }
}

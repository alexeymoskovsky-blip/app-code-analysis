package com.qiyukf.unicorn.ui.evaluate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.evaluation.EvaluationApi;
import com.qiyukf.unicorn.api.evaluation.entry.EvaluationOpenEntry;
import com.qiyukf.unicorn.f.h;
import com.qiyukf.unicorn.f.o;
import com.qiyukf.unicorn.n.g;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.ui.evaluate.c;
import java.util.List;

/* JADX INFO: compiled from: EvaluationAgainDialog.java */
/* JADX INFO: loaded from: classes6.dex */
public final class a extends Dialog {
    private String a;
    private o b;
    private RequestCallbackWrapper c;
    private TextView d;
    private TextView e;
    private TextView f;
    private Context g;

    public a(@NonNull Context context, String str, o oVar, RequestCallbackWrapper requestCallbackWrapper) {
        super(context, R.style.ysf_popup_dialog_style);
        this.g = context;
        this.a = str;
        this.b = oVar;
        this.c = requestCallbackWrapper;
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.ysf_dialog_evaluation_again, (ViewGroup) null);
        setContentView(viewInflate);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.d = (TextView) viewInflate.findViewById(R.id.ysf_evaluation_again_dialog_text);
        this.e = (TextView) viewInflate.findViewById(R.id.ysf_evaluation_again_dialog_ok);
        this.f = (TextView) viewInflate.findViewById(R.id.ysf_evaluation_again_dialog_cancel);
        if (!TextUtils.isEmpty(this.b.c())) {
            this.d.setText(this.b.c());
        } else {
            this.d.setText(R.string.ysf_evaluation);
        }
        com.qiyukf.unicorn.c.h().d().d().put(this.a, Long.valueOf(this.b.a()));
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.d.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.e.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()));
            this.e.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().e(), com.qiyukf.unicorn.m.a.a().e(), 5));
            this.f.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.f.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 5));
            if (w.a()) {
                viewInflate.getBackground().setColorFilter(this.g.getResources().getColor(R.color.ysf_dark_module), PorterDuff.Mode.SRC_IN);
            }
        }
        this.e.setOnClickListener(new AnonymousClass1());
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.evaluate.a.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                a.this.c.onResult(400, "", null);
                a.this.dismiss();
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.a$1, reason: invalid class name */
    /* JADX INFO: compiled from: EvaluationAgainDialog.java */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            com.qiyukf.unicorn.d.c.a(a.this.a, a.this.b.d().a());
            if (System.currentTimeMillis() <= a.this.b.b() + (a.this.b.d().a().f().longValue() * 60000)) {
                com.qiyukf.unicorn.c.h().d().e().put(a.this.a, Boolean.TRUE);
                if (EvaluationApi.getInstance().getOnEvaluationEventListener() == null) {
                    g.a((Activity) a.this.g);
                    final c cVar = new c(a.this.g, a.this.a);
                    cVar.setCanceledOnTouchOutside(false);
                    cVar.a(new c.a() { // from class: com.qiyukf.unicorn.ui.evaluate.a.1.1
                        @Override // com.qiyukf.unicorn.ui.evaluate.c.a
                        public final void a() {
                            a.this.c.onResult(400, "", null);
                        }
                    });
                    cVar.a(new c.b() { // from class: com.qiyukf.unicorn.ui.evaluate.a.1.2
                        @Override // com.qiyukf.unicorn.ui.evaluate.c.b
                        public final void onSubmit(int i, List<String> list, String str, String str2, int i2, long j) {
                            cVar.a(false);
                            cVar.b(true);
                            h hVar = new h();
                            hVar.a = a.this.a;
                            hVar.b = i;
                            hVar.c = str;
                            hVar.d = list;
                            hVar.e = str2;
                            hVar.f = i2;
                            hVar.h = 0;
                            hVar.g = a.this.b.a();
                            com.qiyukf.unicorn.c.h().d().a(hVar, new RequestCallbackWrapper<String>() { // from class: com.qiyukf.unicorn.ui.evaluate.a.1.2.1
                                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                                public final /* synthetic */ void onResult(int i3, String str3, Throwable th) {
                                    c cVar2;
                                    c cVar3;
                                    String str4 = str3;
                                    if ((i3 == 200 || i3 == 415) && (cVar2 = cVar) != null) {
                                        cVar2.cancel();
                                    } else if (i3 != 200 && (cVar3 = cVar) != null && cVar3.isShowing()) {
                                        cVar.a(true);
                                        cVar.b(false);
                                        t.a(a.this.g.getString(R.string.ysf_network_error));
                                    }
                                    a.this.c.onResult(i3, str4, th);
                                }
                            });
                        }
                    });
                    cVar.show();
                } else {
                    EvaluationOpenEntry evaluationOpenEntry = new EvaluationOpenEntry();
                    com.qiyukf.unicorn.c.h();
                    com.qiyukf.unicorn.h.a.c.c cVarA = com.qiyukf.unicorn.k.a.a(a.this.a);
                    evaluationOpenEntry.setEvaluationEntryList(cVarA.e());
                    evaluationOpenEntry.setType(cVarA.d());
                    evaluationOpenEntry.setTitle(cVarA.c());
                    evaluationOpenEntry.setExchange(a.this.a);
                    evaluationOpenEntry.setSessionId(a.this.b.a());
                    evaluationOpenEntry.setResolvedEnabled(cVarA.k());
                    evaluationOpenEntry.setResolvedRequired(cVarA.l());
                    EvaluationApi.getInstance().getOnEvaluationEventListener().onEvaluationMessageClick(evaluationOpenEntry, (Activity) a.this.g);
                }
                a.this.dismiss();
                return;
            }
            t.a(R.string.ysf_evaluation_time_out);
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        attributes.gravity = 80;
        getWindow().setAttributes(attributes);
    }
}

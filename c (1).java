package com.qiyukf.unicorn.ui.evaluate;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.evaluation.entry.EvaluationOptionEntry;
import com.qiyukf.unicorn.h.a.f.x;
import com.qiyukf.unicorn.n.g;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.widget.dialog.UnicornDialog;
import com.qiyukf.unicorn.widget.flowlayout.FlowLayout;
import com.qiyukf.unicorn.widget.flowlayout.TagAdapter;
import com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: EvaluationDialog.java */
/* JADX INFO: loaded from: classes6.dex */
public final class c extends Dialog implements DialogInterface.OnCancelListener, DialogInterface.OnShowListener, View.OnClickListener {
    private String A;
    private int B;
    private Set<Integer>[] C;
    private List<String> D;
    private int E;
    private int F;
    private int G;
    private boolean H;
    private long I;
    private View.OnTouchListener J;
    private TextWatcher K;
    private TagAdapter<String> L;
    private TagFlowLayout.OnTagClickListener M;
    private View a;
    private TextView b;
    private TextView c;
    private ImageView d;
    private View e;
    private ScrollView f;
    private ScrollView g;
    private LinearLayout h;
    private EditText i;
    private Button j;
    private TextView k;
    private LinearLayout l;
    private TextView m;
    private TextView n;
    private TextView o;
    private LinearLayout p;
    private TagFlowLayout q;
    private View r;
    private b s;
    private a t;
    private Context u;
    private int v;
    private com.qiyukf.unicorn.h.a.f.c w;
    private x x;
    private com.qiyukf.unicorn.h.a.c.c y;
    private com.qiyukf.unicorn.h.a.c.e z;

    /* JADX INFO: compiled from: EvaluationDialog.java */
    public interface a {
        void a();
    }

    /* JADX INFO: compiled from: EvaluationDialog.java */
    public interface b {
        void onSubmit(int i, List<String> list, String str, String str2, int i2, long j);
    }

    public c(Context context, String str) {
        this(context, str, 0);
    }

    public c(Context context, String str, int i) {
        super(context, R.style.ysf_popup_dialog_style);
        this.B = -1;
        this.D = new ArrayList();
        this.H = false;
        this.J = new View.OnTouchListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.3
            public AnonymousClass3() {
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$3$1 */
            /* JADX INFO: compiled from: EvaluationDialog.java */
            public class AnonymousClass1 implements Runnable {
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    c.this.g.fullScroll(130);
                }
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                c.this.i.postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.evaluate.c.3.1
                    public AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        c.this.g.fullScroll(130);
                    }
                }, 200L);
                return false;
            }
        };
        this.K = new TextWatcher() { // from class: com.qiyukf.unicorn.ui.evaluate.c.4
            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            public AnonymousClass4() {
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                c.this.o.setText(editable.length() + "/200");
                if (c.this.B == -1 || !c.this.H) {
                    return;
                }
                c.this.a(true);
            }
        };
        this.L = new TagAdapter<String>(this.D) { // from class: com.qiyukf.unicorn.ui.evaluate.c.5
            public AnonymousClass5(List list) {
                super(list);
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final /* synthetic */ View getView(FlowLayout flowLayout, int i2, String str2) {
                View viewInflate = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.ysf_evaluation_tag_item, (ViewGroup) flowLayout, false);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tag_text);
                textView.setText(str2);
                textView.setSelected(c.this.C[c.this.B].contains(Integer.valueOf(i2)));
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    textView.setTextColor(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), c.this.u.getResources().getColor(R.color.ysf_grey_999999), false));
                    textView.setBackgroundDrawable(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), 2));
                }
                return viewInflate;
            }
        };
        this.M = new TagFlowLayout.OnTagClickListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.6
            public AnonymousClass6() {
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i2, FlowLayout flowLayout) {
                TextView textView = (TextView) view.findViewById(R.id.ysf_tag_text);
                if (!textView.isSelected()) {
                    textView.setSelected(true);
                    c.this.C[c.this.B].add(Integer.valueOf(i2));
                } else {
                    textView.setSelected(false);
                    c.this.C[c.this.B].remove(Integer.valueOf(i2));
                }
                c.this.a(true);
                return true;
            }
        };
        this.u = context;
        this.A = str;
        this.v = i;
        a();
    }

    public c(Context context, com.qiyukf.unicorn.h.a.f.c cVar) {
        super(context, R.style.ysf_popup_dialog_style);
        this.B = -1;
        this.D = new ArrayList();
        this.H = false;
        this.J = new View.OnTouchListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.3
            public AnonymousClass3() {
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$3$1 */
            /* JADX INFO: compiled from: EvaluationDialog.java */
            public class AnonymousClass1 implements Runnable {
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    c.this.g.fullScroll(130);
                }
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                c.this.i.postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.evaluate.c.3.1
                    public AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        c.this.g.fullScroll(130);
                    }
                }, 200L);
                return false;
            }
        };
        this.K = new TextWatcher() { // from class: com.qiyukf.unicorn.ui.evaluate.c.4
            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            public AnonymousClass4() {
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                c.this.o.setText(editable.length() + "/200");
                if (c.this.B == -1 || !c.this.H) {
                    return;
                }
                c.this.a(true);
            }
        };
        this.L = new TagAdapter<String>(this.D) { // from class: com.qiyukf.unicorn.ui.evaluate.c.5
            public AnonymousClass5(List list) {
                super(list);
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final /* synthetic */ View getView(FlowLayout flowLayout, int i2, String str2) {
                View viewInflate = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.ysf_evaluation_tag_item, (ViewGroup) flowLayout, false);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tag_text);
                textView.setText(str2);
                textView.setSelected(c.this.C[c.this.B].contains(Integer.valueOf(i2)));
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    textView.setTextColor(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), c.this.u.getResources().getColor(R.color.ysf_grey_999999), false));
                    textView.setBackgroundDrawable(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), 2));
                }
                return viewInflate;
            }
        };
        this.M = new TagFlowLayout.OnTagClickListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.6
            public AnonymousClass6() {
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i2, FlowLayout flowLayout) {
                TextView textView = (TextView) view.findViewById(R.id.ysf_tag_text);
                if (!textView.isSelected()) {
                    textView.setSelected(true);
                    c.this.C[c.this.B].add(Integer.valueOf(i2));
                } else {
                    textView.setSelected(false);
                    c.this.C[c.this.B].remove(Integer.valueOf(i2));
                }
                c.this.a(true);
                return true;
            }
        };
        this.u = context;
        this.w = cVar;
        this.v = 0;
        a();
    }

    public c(Context context, x xVar) {
        super(context, R.style.ysf_popup_dialog_style);
        this.B = -1;
        this.D = new ArrayList();
        this.H = false;
        this.J = new View.OnTouchListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.3
            public AnonymousClass3() {
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$3$1 */
            /* JADX INFO: compiled from: EvaluationDialog.java */
            public class AnonymousClass1 implements Runnable {
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    c.this.g.fullScroll(130);
                }
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                c.this.i.postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.evaluate.c.3.1
                    public AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        c.this.g.fullScroll(130);
                    }
                }, 200L);
                return false;
            }
        };
        this.K = new TextWatcher() { // from class: com.qiyukf.unicorn.ui.evaluate.c.4
            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            public AnonymousClass4() {
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                c.this.o.setText(editable.length() + "/200");
                if (c.this.B == -1 || !c.this.H) {
                    return;
                }
                c.this.a(true);
            }
        };
        this.L = new TagAdapter<String>(this.D) { // from class: com.qiyukf.unicorn.ui.evaluate.c.5
            public AnonymousClass5(List list) {
                super(list);
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final /* synthetic */ View getView(FlowLayout flowLayout, int i2, String str2) {
                View viewInflate = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.ysf_evaluation_tag_item, (ViewGroup) flowLayout, false);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tag_text);
                textView.setText(str2);
                textView.setSelected(c.this.C[c.this.B].contains(Integer.valueOf(i2)));
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    textView.setTextColor(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), c.this.u.getResources().getColor(R.color.ysf_grey_999999), false));
                    textView.setBackgroundDrawable(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), 2));
                }
                return viewInflate;
            }
        };
        this.M = new TagFlowLayout.OnTagClickListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.6
            public AnonymousClass6() {
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i2, FlowLayout flowLayout) {
                TextView textView = (TextView) view.findViewById(R.id.ysf_tag_text);
                if (!textView.isSelected()) {
                    textView.setSelected(true);
                    c.this.C[c.this.B].add(Integer.valueOf(i2));
                } else {
                    textView.setSelected(false);
                    c.this.C[c.this.B].remove(Integer.valueOf(i2));
                }
                c.this.a(true);
                return true;
            }
        };
        this.u = context;
        this.v = 1;
        this.x = xVar;
        a();
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        attributes.gravity = 80;
        getWindow().setAttributes(attributes);
    }

    private void b() {
        String strE;
        int iC;
        List<String> listJ;
        com.qiyukf.unicorn.h.a.c.c cVar;
        List<EvaluationOptionEntry> listE = this.v == 0 ? this.y.e() : this.z.k();
        ArrayList<d> arrayList = new ArrayList();
        int i = this.E;
        int i2 = 4;
        if (i == 2) {
            arrayList.add(new d(listE.get(0).getName(), R.drawable.ysf_back_evaluator_sorce_up_hand));
            arrayList.add(new d(listE.get(1).getName(), R.drawable.ysf_back_evaluator_score_down_hand));
        } else if (i == 3) {
            arrayList.add(new d(listE.get(0).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(1).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(2).getName(), R.drawable.ysf_back_evaluator_star));
        } else if (i == 4) {
            arrayList.add(new d(listE.get(0).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(1).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(2).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(3).getName(), R.drawable.ysf_back_evaluator_star));
        } else {
            arrayList.add(new d(listE.get(0).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(1).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(2).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(3).getName(), R.drawable.ysf_back_evaluator_star));
            arrayList.add(new d(listE.get(4).getName(), R.drawable.ysf_back_evaluator_star));
        }
        int iA = -1;
        for (d dVar : arrayList) {
            ImageView imageView = new ImageView(this.u);
            imageView.setImageResource(dVar.a());
            int iIndexOf = arrayList.indexOf(dVar);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.1
                final /* synthetic */ int a;

                public AnonymousClass1(int iIndexOf2) {
                    i = iIndexOf2;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    c.this.a(i, true);
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(p.a(40.0f), p.a(40.0f));
            layoutParams.setMargins(p.a(9.0f), 0, p.a(9.0f), 0);
            this.h.addView(imageView, layoutParams);
            if (this.v == 0) {
                com.qiyukf.unicorn.h.a.f.c cVar2 = this.w;
                if (cVar2 != null && cVar2.d() != 0 && this.w.d() == listE.get(iIndexOf2).getValue()) {
                    iA = a(iIndexOf2);
                }
            } else {
                x xVar = this.x;
                if (xVar != null && xVar.c() != 0 && this.x.c() == listE.get(iIndexOf2).getValue()) {
                    iA = a(iIndexOf2);
                }
            }
        }
        if (this.v == 0 && (cVar = this.y) != null) {
            EditText editText = this.i;
            getContext();
            editText.setHint(cVar.o());
        }
        if (iA != -1) {
            a(iA, false);
        } else if ((this.v == 1 ? this.z.l() : this.y.n()) == 1) {
            int i3 = this.E;
            if (i3 == 2) {
                i2 = 0;
            } else if (i3 == 3) {
                i2 = 2;
            } else if (i3 == 4) {
                i2 = 3;
            }
            a(i2, true);
        }
        if (this.F == 1) {
            this.l.setVisibility(0);
        } else {
            this.l.setVisibility(8);
        }
        if (this.v == 0) {
            com.qiyukf.unicorn.h.a.f.c cVar3 = this.w;
            if (cVar3 != null && cVar3.m() == 1) {
                this.m.setSelected(true);
                this.n.setSelected(false);
            } else {
                com.qiyukf.unicorn.h.a.f.c cVar4 = this.w;
                if (cVar4 != null && cVar4.m() == 2) {
                    this.n.setSelected(true);
                    this.m.setSelected(false);
                } else {
                    this.m.setSelected(false);
                    this.n.setSelected(false);
                }
            }
        } else {
            x xVar2 = this.x;
            if (xVar2 != null && xVar2.f() == 1) {
                this.m.setSelected(true);
                this.n.setSelected(false);
            } else {
                x xVar3 = this.x;
                if (xVar3 != null && xVar3.f() == 2) {
                    this.n.setSelected(true);
                    this.m.setSelected(false);
                } else {
                    this.m.setSelected(false);
                    this.n.setSelected(false);
                }
            }
        }
        if (this.v == 0) {
            com.qiyukf.unicorn.h.a.f.c cVar5 = this.w;
            if (cVar5 == null || TextUtils.isEmpty(cVar5.g())) {
                this.o.setText("0/200");
            } else {
                this.o.setText(this.w.g().length() + "/200");
            }
        } else {
            x xVar4 = this.x;
            if (xVar4 == null || TextUtils.isEmpty(xVar4.e())) {
                this.o.setText("0/200");
            } else {
                this.o.setText(this.x.e().length() + "/200");
            }
        }
        int i4 = this.v;
        if (i4 == 0) {
            if (this.w == null) {
                this.H = true;
                return;
            }
        } else if (this.x == null) {
            this.H = true;
            return;
        }
        if (i4 == 0) {
            strE = this.w.g();
            iC = this.w.d();
            listJ = this.w.h();
        } else {
            strE = this.x.e();
            iC = this.x.c();
            listJ = this.x.j();
        }
        if ((!TextUtils.isEmpty(strE) || iC != 0) && !TextUtils.isEmpty(strE)) {
            this.i.setText(strE);
        }
        if (iA != -1) {
            EvaluationOptionEntry evaluationOptionEntry = this.v == 0 ? this.y.e().get(a(iA)) : this.z.k().get(a(iA));
            if (listJ == null) {
                this.H = true;
                return;
            }
            for (String str : listJ) {
                if (evaluationOptionEntry.getTagList().contains(str)) {
                    this.C[a(iA)].add(Integer.valueOf(evaluationOptionEntry.getTagList().indexOf(str)));
                }
            }
            this.L.notifyDataChanged();
        }
        this.H = true;
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$1 */
    /* JADX INFO: compiled from: EvaluationDialog.java */
    public class AnonymousClass1 implements View.OnClickListener {
        final /* synthetic */ int a;

        public AnonymousClass1(int iIndexOf2) {
            i = iIndexOf2;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            c.this.a(i, true);
        }
    }

    public final void a(b bVar) {
        this.s = bVar;
    }

    public final void a(a aVar) {
        this.t = aVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.qiyukf.unicorn.ui.evaluate.c$b] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [int] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        boolean z;
        String strE;
        x xVar;
        String string;
        com.qiyukf.unicorn.h.a.f.c cVar;
        g.a(getWindow().getDecorView());
        if (view == this.d) {
            int i = this.v;
            if (i == 0 && (cVar = this.w) != null) {
                strE = cVar.g();
            } else if (i == 1 && (xVar = this.x) != null) {
                strE = xVar.e();
            } else {
                strE = "";
            }
            if (this.i.length() == 0 || TextUtils.isEmpty(strE) || strE.equals(this.i.getText().toString())) {
                a aVar = this.t;
                if (aVar != null) {
                    aVar.a();
                }
                cancel();
                return;
            }
            this.a.setVisibility(8);
            if (this.v == 0 ? this.y.g() : this.z.i()) {
                string = this.u.getString(R.string.ysf_evaluation_dialog_message_multi);
            } else {
                string = this.u.getString(R.string.ysf_evaluation_dialog_message);
            }
            String str = string;
            Context context = this.u;
            UnicornDialog.showDoubleBtnDialog(context, null, str, context.getString(R.string.ysf_yes), this.u.getString(R.string.ysf_no), false, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.evaluate.c.2
                public AnonymousClass2() {
                }

                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public final void onClick(int i2) {
                    if (i2 == 0) {
                        if (c.this.t != null) {
                            c.this.t.a();
                        }
                        c.this.cancel();
                        return;
                    }
                    c.this.a.setVisibility(0);
                }
            });
            return;
        }
        if (view.getId() == R.id.ysf_btn_submit && this.s != null && this.B != -1) {
            if (this.F == 0 || this.G == 0 || this.m.isSelected() || this.n.isSelected()) {
                z = true;
            } else {
                t.a(R.string.ysf_select_question_is_resolve);
                z = false;
            }
            if (z) {
                EvaluationOptionEntry evaluationOptionEntry = this.v == 0 ? this.y.e().get(this.B) : this.z.k().get(this.B);
                int value = evaluationOptionEntry.getValue();
                String name = evaluationOptionEntry.getName();
                Set<Integer> set = this.C[this.B];
                ArrayList arrayList = new ArrayList(set.size());
                Iterator<Integer> it = set.iterator();
                while (it.hasNext()) {
                    arrayList.add(evaluationOptionEntry.getTagList().get(it.next().intValue()));
                }
                ?? IsSelected = this.n.isSelected() ? 2 : this.m.isSelected();
                String strTrim = this.i.getText().toString().trim();
                if (evaluationOptionEntry.getTagRequired() == 1 && arrayList.size() == 0) {
                    t.b(R.string.ysf_evaluation_empty_label);
                    return;
                } else if (evaluationOptionEntry.getCommentRequired() == 1 && TextUtils.isEmpty(strTrim)) {
                    t.b(R.string.ysf_evaluation_empty_remark);
                    return;
                } else {
                    this.s.onSubmit(value, arrayList, strTrim, name, IsSelected, this.I);
                    return;
                }
            }
        }
        if (view.getId() == R.id.ysf_tv_evaluator_unsolve) {
            if (this.n.isSelected()) {
                this.n.setSelected(false);
            } else {
                this.n.setSelected(true);
            }
            this.m.setSelected(false);
            a(true);
            return;
        }
        if (view.getId() == R.id.ysf_tv_evaluator_solve) {
            if (this.m.isSelected()) {
                this.m.setSelected(false);
            } else {
                this.m.setSelected(true);
            }
            this.n.setSelected(false);
            a(true);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$2 */
    /* JADX INFO: compiled from: EvaluationDialog.java */
    public class AnonymousClass2 implements UnicornDialog.OnClickListener {
        public AnonymousClass2() {
        }

        @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
        public final void onClick(int i2) {
            if (i2 == 0) {
                if (c.this.t != null) {
                    c.this.t.a();
                }
                c.this.cancel();
                return;
            }
            c.this.a.setVisibility(0);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$3 */
    /* JADX INFO: compiled from: EvaluationDialog.java */
    public class AnonymousClass3 implements View.OnTouchListener {
        public AnonymousClass3() {
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$3$1 */
        /* JADX INFO: compiled from: EvaluationDialog.java */
        public class AnonymousClass1 implements Runnable {
            public AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                c.this.g.fullScroll(130);
            }
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            c.this.i.postDelayed(new Runnable() { // from class: com.qiyukf.unicorn.ui.evaluate.c.3.1
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public final void run() {
                    c.this.g.fullScroll(130);
                }
            }, 200L);
            return false;
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$4 */
    /* JADX INFO: compiled from: EvaluationDialog.java */
    public class AnonymousClass4 implements TextWatcher {
        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        public AnonymousClass4() {
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            c.this.o.setText(editable.length() + "/200");
            if (c.this.B == -1 || !c.this.H) {
                return;
            }
            c.this.a(true);
        }
    }

    public final void a(boolean z) {
        Button button = this.j;
        if (button != null) {
            button.setEnabled(z);
        }
    }

    public final void b(boolean z) {
        Button button = this.j;
        if (button != null) {
            button.setText(z ? R.string.ysf_evaluation_btn_submitting : R.string.ysf_evaluation_btn_submit);
        }
    }

    public void a(int i, boolean z) {
        this.B = a(i);
        if (z) {
            a(true);
        }
        int i2 = 0;
        while (i2 < this.h.getChildCount()) {
            if (this.E == 2) {
                this.h.getChildAt(i2).setSelected(i2 == i);
            } else {
                this.h.getChildAt(i2).setSelected(i2 <= i);
            }
            i2++;
        }
        List<String> tagList = this.v == 0 ? this.y.e().get(this.B).getTagList() : this.z.k().get(this.B).getTagList();
        this.D.clear();
        this.r.setVisibility(8);
        if (tagList.size() > 8) {
            this.r.setVisibility(0);
            this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, p.a(155.0f)));
        } else if (tagList.size() > 6) {
            this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, p.a(155.0f)));
        } else if (tagList.size() > 4) {
            this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, p.a(117.0f)));
        } else if (tagList.size() > 2) {
            this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, p.a(79.0f)));
        } else if (tagList.size() > 0) {
            this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, p.a(41.0f)));
        } else {
            this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, 0));
        }
        this.D.addAll(tagList);
        this.L.notifyDataChanged();
        this.k.setText(this.v == 0 ? this.y.e().get(this.B).getName() : this.z.k().get(this.B).getName());
        this.p.setVisibility(0);
        if (this.F == 1) {
            this.l.setVisibility(0);
        }
    }

    private int a(int i) {
        int i2 = this.E;
        return i2 != 2 ? i2 != 3 ? i2 != 4 ? 4 - i : 3 - i : 2 - i : i;
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$5 */
    /* JADX INFO: compiled from: EvaluationDialog.java */
    public class AnonymousClass5 extends TagAdapter<String> {
        public AnonymousClass5(List list) {
            super(list);
        }

        @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
        public final /* synthetic */ View getView(FlowLayout flowLayout, int i2, String str2) {
            View viewInflate = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.ysf_evaluation_tag_item, (ViewGroup) flowLayout, false);
            TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tag_text);
            textView.setText(str2);
            textView.setSelected(c.this.C[c.this.B].contains(Integer.valueOf(i2)));
            if (com.qiyukf.unicorn.m.a.a().c()) {
                textView.setTextColor(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), c.this.u.getResources().getColor(R.color.ysf_grey_999999), false));
                textView.setBackgroundDrawable(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), 2));
            }
            return viewInflate;
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.evaluate.c$6 */
    /* JADX INFO: compiled from: EvaluationDialog.java */
    public class AnonymousClass6 implements TagFlowLayout.OnTagClickListener {
        public AnonymousClass6() {
        }

        @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
        public final boolean onTagClick(View view, int i2, FlowLayout flowLayout) {
            TextView textView = (TextView) view.findViewById(R.id.ysf_tag_text);
            if (!textView.isSelected()) {
                textView.setSelected(true);
                c.this.C[c.this.B].add(Integer.valueOf(i2));
            } else {
                textView.setSelected(false);
                c.this.C[c.this.B].remove(Integer.valueOf(i2));
            }
            c.this.a(true);
            return true;
        }
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        com.qiyukf.unicorn.c.h().d().a(this);
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        com.qiyukf.unicorn.c.h().d().a((c) null);
    }

    private void a() {
        this.I = com.qiyukf.unicorn.c.h().d(this.A);
        if (this.v == 1) {
            x xVar = this.x;
            if (xVar != null) {
                this.z = xVar.k();
            }
            com.qiyukf.unicorn.h.a.c.e eVar = this.z;
            if (eVar == null || eVar.k() == null) {
                com.qiyukf.unicorn.c.h();
                this.z = com.qiyukf.unicorn.k.a.b(this.A);
            }
            this.C = new Set[this.z.k().size()];
            int i = 0;
            while (true) {
                Set<Integer>[] setArr = this.C;
                if (i >= setArr.length) {
                    break;
                }
                setArr[i] = new HashSet();
                i++;
            }
        } else {
            com.qiyukf.unicorn.h.a.f.c cVar = this.w;
            if (cVar != null) {
                this.y = cVar.i();
            }
            com.qiyukf.unicorn.h.a.c.c cVar2 = this.y;
            if (cVar2 == null || cVar2.e() == null) {
                com.qiyukf.unicorn.c.h();
                this.y = com.qiyukf.unicorn.k.a.a(this.A);
            }
            this.C = new Set[this.y.e().size()];
            int i2 = 0;
            while (true) {
                Set<Integer>[] setArr2 = this.C;
                if (i2 >= setArr2.length) {
                    break;
                }
                setArr2[i2] = new HashSet();
                i2++;
            }
        }
        this.E = this.v == 0 ? this.y.d() : this.z.d();
        this.F = this.v == 0 ? this.y.k() : this.z.f();
        this.G = this.v == 0 ? this.y.l() : this.z.g();
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.ysf_dialog_evaluation, (ViewGroup) null);
        this.a = viewInflate;
        setContentView(viewInflate);
        setCancelable(false);
        setOnShowListener(this);
        setOnCancelListener(this);
        this.b = (TextView) this.a.findViewById(R.id.ysf_evaluation_title);
        this.c = (TextView) this.a.findViewById(R.id.ysf_tv_evaluator_problem);
        this.d = (ImageView) this.a.findViewById(R.id.ysf_evaluation_dialog_close);
        this.e = this.a.findViewById(R.id.ysf_message_include_divider);
        this.f = (ScrollView) this.a.findViewById(R.id.scroll_view);
        this.i = (EditText) this.a.findViewById(R.id.ysf_evaluation_dialog_et_remark);
        this.j = (Button) this.a.findViewById(R.id.ysf_btn_submit);
        this.g = (ScrollView) this.a.findViewById(R.id.ysf_sl_evaluator_dialog_parent);
        this.k = (TextView) this.a.findViewById(R.id.ysf_tv_evaluator_select_score);
        this.h = (LinearLayout) this.a.findViewById(R.id.ysf_evaluation_dialog_radio_group);
        this.m = (TextView) this.a.findViewById(R.id.ysf_tv_evaluator_solve);
        this.n = (TextView) this.a.findViewById(R.id.ysf_tv_evaluator_unsolve);
        this.o = (TextView) this.a.findViewById(R.id.ysf_tv_evaluator_remark_word_count);
        this.p = (LinearLayout) this.a.findViewById(R.id.ysf_ll_evaluation_dialog_remark_parent);
        this.l = (LinearLayout) this.a.findViewById(R.id.ysf_ll_evaluator_dialog_solve_parent);
        this.r = this.a.findViewById(R.id.ysf_view_evaluator_shadow);
        this.q = (TagFlowLayout) this.a.findViewById(R.id.ysf_evaluation_tag_layout);
        this.d.setOnClickListener(this);
        this.i.setOnTouchListener(this.J);
        this.j.setOnClickListener(this);
        this.q.setAdapter(this.L);
        this.q.setOnTagClickListener(this.M);
        this.i.addTextChangedListener(this.K);
        this.m.setOnClickListener(this);
        this.n.setOnClickListener(this);
        com.qiyukf.unicorn.m.a.a().a(this.j);
        if (com.qiyukf.unicorn.c.h().d().f()) {
            com.qiyukf.unicorn.c.h().d().a(false);
            this.j.setText(R.string.ysf_back_evaluation_and_close);
        }
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.b.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.c.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            Drawable drawable = this.d.getDrawable();
            int color = Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b());
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            drawable.setColorFilter(color, mode);
            this.e.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()), mode);
            this.j.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k()));
            this.m.setTextColor(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), this.u.getResources().getColor(R.color.ysf_grey_999999), true));
            this.n.setTextColor(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), this.u.getResources().getColor(R.color.ysf_grey_999999), true));
            this.m.setBackground(com.qiyukf.unicorn.m.b.c(com.qiyukf.unicorn.m.a.a().b().k()));
            this.n.setBackground(com.qiyukf.unicorn.m.b.c(com.qiyukf.unicorn.m.a.a().b().k()));
            this.k.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.j.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()));
            this.i.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
            this.i.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.o.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            this.p.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 2));
            if (w.a()) {
                this.a.getBackground().setColorFilter(this.u.getResources().getColor(R.color.ysf_dark_module), mode);
            }
        } else {
            this.j.setBackgroundResource(R.drawable.ysf_evaluation_dialog_btn_submit_bg_selector);
        }
        b();
    }
}

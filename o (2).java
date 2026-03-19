package com.qiyukf.unicorn.ui.viewholder;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.h.a.d.ac;
import com.qiyukf.unicorn.h.a.f.w;
import com.qiyukf.unicorn.n.y;
import com.qiyukf.unicorn.widget.flowlayout.FlowLayout;
import com.qiyukf.unicorn.widget.flowlayout.TagAdapter;
import com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: compiled from: MsgViewHolderRobotAnswer.java */
/* JADX INFO: loaded from: classes6.dex */
public class o extends c<com.qiyukf.unicorn.f.r> {
    private View b;
    private View c;
    private View d;
    private EditText e;
    private TextView f;
    private TextView g;
    private TagFlowLayout h;
    private LinearLayout i;
    private TextView j;
    private View k;
    private View l;
    private View m;
    private final List<String> n;
    private final TagAdapter<String> o;
    private final TagFlowLayout.OnTagClickListener p;

    public o() {
        ArrayList arrayList = new ArrayList();
        this.n = arrayList;
        this.o = new TagAdapter<String>(arrayList) { // from class: com.qiyukf.unicorn.ui.viewholder.o.6
            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final /* synthetic */ View getView(FlowLayout flowLayout, int i, String str) {
                String str2 = str;
                View viewInflate = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.ysf_robot_evaluation_tag_item, (ViewGroup) flowLayout, false);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_robot_tag_text);
                textView.setText(str2);
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    textView.setTextColor(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().b().k(), Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f())));
                    textView.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k(), 13));
                }
                if (Math.max((int) y.a(str2, com.qiyukf.unicorn.n.p.a(12.0f)), (int) y.a(flowLayout.getContext(), str2, 12.0f)) > (flowLayout.getMeasuredWidth() - com.qiyukf.unicorn.n.p.a(24.0f)) / 2) {
                    ViewGroup.LayoutParams layoutParams = viewInflate.getLayoutParams();
                    layoutParams.width = flowLayout.getMeasuredWidth();
                    viewInflate.setLayoutParams(layoutParams);
                } else {
                    ViewGroup.LayoutParams layoutParams2 = viewInflate.getLayoutParams();
                    layoutParams2.width = flowLayout.getMeasuredWidth() / 2;
                    viewInflate.setLayoutParams(layoutParams2);
                }
                ViewGroup.LayoutParams layoutParams3 = textView.getLayoutParams();
                layoutParams3.width = -1;
                textView.setLayoutParams(layoutParams3);
                return viewInflate;
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final boolean unSelected(int i, int i2, View view) {
                if (!(view instanceof ViewGroup)) {
                    return true;
                }
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    viewGroup.getChildAt(i3).setSelected(false);
                }
                return true;
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final void onSelected(int i, View view) {
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int childCount = viewGroup.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        viewGroup.getChildAt(i2).setSelected(true);
                    }
                }
            }

            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final /* synthetic */ boolean setSelected(int i, String str) {
                return i == ((ac) ((MsgViewHolderBase) o.this).message.getAttachment()).c();
            }
        };
        this.p = new TagFlowLayout.OnTagClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.o.7
            @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i, FlowLayout flowLayout) {
                TextView textView = (TextView) view.findViewById(R.id.ysf_robot_tag_text);
                ac acVar = (ac) ((MsgViewHolderBase) o.this).message.getAttachment();
                if (!textView.isSelected()) {
                    i = -1;
                }
                acVar.c(i);
                o.this.a(textView.isSelected() || !o.this.e.getText().toString().isEmpty());
                return true;
            }
        };
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final /* synthetic */ void a(TextView textView, com.qiyukf.unicorn.f.r rVar) {
        com.qiyukf.unicorn.f.r rVar2 = rVar;
        textView.setText(TextUtils.isEmpty(rVar2.b) ? this.context.getString(R.string.ysf_guess_want_ask) : rVar2.b);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final /* synthetic */ void b(TextView textView, com.qiyukf.unicorn.f.r rVar) {
        com.qiyukf.unicorn.f.r rVar2 = rVar;
        if (!b()) {
            com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_msg_invalid);
            return;
        }
        if (com.qiyukf.unicorn.c.h().g(this.message.getSessionId()) == 1) {
            com.qiyukf.unicorn.h.a.f.y yVar = new com.qiyukf.unicorn.h.a.f.y();
            yVar.a(rVar2.a);
            yVar.b(rVar2.b);
            yVar.a(this.message.getUuid());
            getAdapter().b().b(MessageBuilder.createCustomMessage(this.message.getSessionId(), this.message.getSessionType(), yVar));
        } else {
            IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(this.message.getSessionId(), this.message.getSessionType(), rVar2.b);
            iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
            getAdapter().b().b(iMMessageCreateTextMessage);
        }
        String sessionId = this.message.getSessionId();
        this.message.getSessionId();
        com.qiyukf.unicorn.c.a.a(sessionId, 0, rVar2.e, rVar2.a, rVar2.b);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        super.inflateContentView();
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.ysf_message_item_robot_evaluation, (ViewGroup) this.a, false);
        this.b = viewInflate;
        this.c = viewInflate.findViewById(R.id.ysf_robot_evaluate_useful);
        this.f = (TextView) this.b.findViewById(R.id.ysf_robot_evaluate_useful_text);
        this.g = (TextView) this.b.findViewById(R.id.ysf_robot_evaluate_useless_text);
        this.d = this.b.findViewById(R.id.ysf_robot_evaluate_useless);
        this.e = (EditText) this.b.findViewById(R.id.ysf_robot_evaluation_content);
        this.h = (TagFlowLayout) this.b.findViewById(R.id.ysf_robot_evaluation_tag_layout);
        this.i = (LinearLayout) this.b.findViewById(R.id.ysf_robot_evaluation_tag_ll);
        this.j = (TextView) this.b.findViewById(R.id.ysf_robot_evaluation_submit);
        this.k = this.b.findViewById(R.id.ysf_message_include_divider);
        this.l = this.b.findViewById(R.id.ysf_robot_useful_divider);
        this.m = this.b.findViewById(R.id.ysf_robot_evaluate_vertical_divider);
        this.a.addView(this.b);
        this.h.setOnTagClickListener(this.p);
        this.h.setMaxSelectCount(1);
        this.h.setAdapter(this.o);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.k.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            this.l.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            this.m.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            this.e.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 3));
            this.e.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
            this.e.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        final ac acVar = (ac) this.message.getAttachment();
        if (acVar.k() > 0 && a(acVar)) {
            this.b.setVisibility(0);
            this.c.setSelected(acVar.k() == 2);
            this.d.setSelected(acVar.k() == 3);
            if (com.qiyukf.unicorn.m.a.a().c()) {
                this.f.setTextColor(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().b().k(), Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f())));
                this.f.setCompoundDrawables(com.qiyukf.unicorn.m.b.a(this.context, com.qiyukf.unicorn.m.a.a().b().k(), true), null, null, null);
                this.g.setTextColor(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().b().k(), Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f())));
                this.g.setCompoundDrawables(com.qiyukf.unicorn.m.b.a(this.context, com.qiyukf.unicorn.m.a.a().b().k(), false), null, null, null);
                a(!this.e.getText().toString().isEmpty() || this.h.getSelectedList().size() > 0);
            }
            a(acVar.k());
            this.c.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.o.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (o.this.c.isSelected()) {
                        return;
                    }
                    if (o.this.a(acVar)) {
                        o.a(o.this, 2);
                    } else {
                        com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_evaluate_disable);
                    }
                }
            });
            this.d.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.o.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (o.this.d.isSelected()) {
                        return;
                    }
                    if (o.this.a(acVar)) {
                        o.a(o.this, 3);
                    } else {
                        com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_evaluate_disable);
                    }
                }
            });
            this.j.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.o.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (o.this.a(acVar)) {
                        w wVar = new w();
                        wVar.b(((MsgViewHolderBase) o.this).message.getUuid());
                        wVar.c(o.this.e.getText().toString());
                        if (o.this.h.getSelectedList().iterator().hasNext()) {
                            wVar.a((String) o.this.n.get(o.this.h.getSelectedList().iterator().next().intValue()));
                        }
                        com.qiyukf.unicorn.k.c.a(wVar, ((MsgViewHolderBase) o.this).message.getSessionId()).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.viewholder.o.3.1
                            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                            public final /* synthetic */ void onResult(int i, Void r2, Throwable th) {
                                String string;
                                if (i == 200) {
                                    if (((com.qiyukf.uikit.common.a.f) o.this).context != null) {
                                        string = ((com.qiyukf.uikit.common.a.f) o.this).context.getString(R.string.ysf_thanks_feedback);
                                    } else {
                                        string = "thanks";
                                    }
                                    com.qiyukf.unicorn.n.t.a(string);
                                    acVar.d(0);
                                    AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                                    acVar.a(o.this.e.getText().toString());
                                    ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(((MsgViewHolderBase) o.this).message, true);
                                    return;
                                }
                                com.qiyukf.unicorn.n.t.a(R.string.ysf_network_error);
                            }
                        });
                        return;
                    }
                    com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_evaluate_disable);
                }
            });
            this.e.addTextChangedListener(new TextWatcher() { // from class: com.qiyukf.unicorn.ui.viewholder.o.4
                @Override // android.text.TextWatcher
                public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public final void afterTextChanged(Editable editable) {
                    String string = editable.toString();
                    acVar.a(string);
                    o.this.a(!string.isEmpty() || o.this.h.getSelectedList().size() > 0);
                }
            });
            return;
        }
        this.b.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        this.j.setEnabled(z);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.j.setTextColor(Color.parseColor(z ? com.qiyukf.unicorn.m.a.a().b().k() : com.qiyukf.unicorn.m.a.a().b().s().c()));
        } else {
            this.j.setTextColor(z ? Color.parseColor(com.qiyukf.unicorn.m.a.a().b().k()) : this.a.getResources().getColor(R.color.ysf_grey_999999));
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final String d() {
        ac acVar = (ac) this.message.getAttachment();
        List<com.qiyukf.unicorn.f.r> listI = acVar.i();
        if (l()) {
            return listI.get(0).c;
        }
        if (TextUtils.isEmpty(acVar.f())) {
            return null;
        }
        return acVar.f();
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final boolean l() {
        ac acVar = (ac) this.message.getAttachment();
        List<com.qiyukf.unicorn.f.r> listI = acVar.i();
        return TextUtils.isEmpty(acVar.f()) && listI != null && listI.size() == 1 && !TextUtils.isEmpty(listI.get(0).c);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final String e() {
        return ((ac) this.message.getAttachment()).h();
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final List<com.qiyukf.unicorn.f.r> f() {
        ac acVar = (ac) this.message.getAttachment();
        List<com.qiyukf.unicorn.f.r> listI = acVar.i();
        if (!TextUtils.isEmpty(acVar.f()) || listI == null || listI.size() != 1 || TextUtils.isEmpty(listI.get(0).c)) {
            return listI;
        }
        return null;
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final int j() {
        ac acVar = (ac) this.message.getAttachment();
        if (f() != null && ((acVar.b() + 1) * 5) - f().size() >= 5) {
            acVar.b(0);
            return 0;
        }
        return acVar.b() * 5;
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final void k() {
        ac acVar = (ac) this.message.getAttachment();
        acVar.b(acVar.b() + 1);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final boolean g() {
        this.message.getAttachment();
        return true;
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final boolean h() {
        return ((ac) this.message.getAttachment()).g();
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.c
    public final boolean i() {
        return ((ac) this.message.getAttachment()).k() == 0 && f() != null && f().size() > 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        ac acVar = (ac) this.message.getAttachment();
        if (i == 3 && acVar.n()) {
            this.n.clear();
            if (acVar.o() != null) {
                this.n.addAll(Arrays.asList(acVar.o()));
            }
            this.o.notifyDataChanged();
            this.i.setVisibility(0);
            this.e.setText(acVar.l());
            EditText editText = this.e;
            editText.setSelection(editText.getText().toString().length());
            this.e.setHint(acVar.b(this.context));
            return;
        }
        this.i.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(ac acVar) {
        if (acVar.m() == 0) {
            return false;
        }
        String sessionId = this.message.getSessionId();
        return acVar.m() == com.qiyukf.unicorn.c.h().d(sessionId) || acVar.m() == com.qiyukf.unicorn.c.h().h(sessionId);
    }

    public static /* synthetic */ void a(o oVar, final int i) {
        oVar.c.setSelected(i == 2);
        oVar.d.setSelected(i == 3);
        final ac acVar = (ac) oVar.message.getAttachment();
        com.qiyukf.unicorn.h.a.f.v vVar = new com.qiyukf.unicorn.h.a.f.v();
        vVar.a(oVar.message.getUuid());
        vVar.a(i);
        com.qiyukf.unicorn.k.c.a(vVar, oVar.message.getSessionId()).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.viewholder.o.5
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i2, Void r2, Throwable th) {
                if (i2 == 200) {
                    acVar.d(i);
                    ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(((MsgViewHolderBase) o.this).message, false);
                    if (i == 2 || !o.this.a(acVar)) {
                        return;
                    }
                    if (acVar.n()) {
                        o.this.a(i);
                    } else {
                        o.l(o.this);
                    }
                    o.this.getAdapter().b().c();
                    return;
                }
                com.qiyukf.unicorn.n.t.a(R.string.ysf_network_error);
            }
        });
        if (i == 3 && acVar.n()) {
            oVar.i.setVisibility(0);
        } else {
            oVar.i.setVisibility(8);
        }
    }

    public static /* synthetic */ void l(o oVar) {
        w wVar = new w();
        wVar.b(oVar.message.getUuid());
        wVar.c("");
        com.qiyukf.unicorn.k.c.a(wVar, oVar.message.getSessionId());
    }
}

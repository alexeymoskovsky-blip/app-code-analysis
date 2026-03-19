package com.qiyukf.unicorn.ui.viewholder.a;

import android.content.Context;
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
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.helper.ClickMovementMethod;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.h.a.d.ah;
import com.qiyukf.unicorn.widget.DotProgressBar;
import com.qiyukf.unicorn.widget.flowlayout.FlowLayout;
import com.qiyukf.unicorn.widget.flowlayout.TagAdapter;
import com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: compiled from: MsgViewHolderRobotStreamAnswer.java */
/* JADX INFO: loaded from: classes6.dex */
public class e extends MsgViewHolderBase {
    private View a;
    private View b;
    private View c;
    private EditText d;
    private TextView e;
    private TextView f;
    private TagFlowLayout g;
    private LinearLayout h;
    private TextView i;
    private TextView j;
    private View k;
    private View l;
    private View m;
    private DotProgressBar n;
    private LinearLayout o;
    private TextView p;
    private final List<String> q;
    private final TagFlowLayout.OnTagClickListener r;
    private final TagAdapter<String> s;

    public e() {
        ArrayList arrayList = new ArrayList();
        this.q = arrayList;
        this.r = new TagFlowLayout.OnTagClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.6
            @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i, FlowLayout flowLayout) {
                TextView textView = (TextView) view.findViewById(R.id.ysf_robot_tag_text);
                ah.a aVarD = ((ah) ((MsgViewHolderBase) e.this).message.getAttachment()).d();
                if (!textView.isSelected()) {
                    i = -1;
                }
                aVarD.a(i);
                e.this.a(textView.isSelected() || !e.this.d.getText().toString().isEmpty());
                return true;
            }
        };
        this.s = new TagAdapter<String>(arrayList) { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.7
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
                if (Math.max((int) com.qiyukf.unicorn.n.y.a(str2, com.qiyukf.unicorn.n.p.a(12.0f)), (int) com.qiyukf.unicorn.n.y.a(flowLayout.getContext(), str2, 12.0f)) > (flowLayout.getMeasuredWidth() - com.qiyukf.unicorn.n.p.a(24.0f)) / 2) {
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
                return i == ((ah) ((MsgViewHolderBase) e.this).message.getAttachment()).d().a();
            }
        };
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_message_item_robot_stream_answer;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        int color;
        this.j = (TextView) findViewById(R.id.ysf_robot_stream_answer_text);
        this.a = findViewById(R.id.ysf_robot_stream_layout);
        this.b = findViewById(R.id.ysf_robot_stream_useful);
        this.e = (TextView) findViewById(R.id.ysf_robot_stream_useful_text);
        this.f = (TextView) findViewById(R.id.ysf_robot_stream_useless_text);
        this.c = findViewById(R.id.ysf_robot_stream_useless);
        this.d = (EditText) findViewById(R.id.ysf_robot_stream_content);
        this.g = (TagFlowLayout) findViewById(R.id.ysf_robot_stream_tag_layout);
        this.h = (LinearLayout) findViewById(R.id.ysf_robot_stream_tag_ll);
        this.i = (TextView) findViewById(R.id.ysf_robot_stream_submit);
        this.k = findViewById(R.id.ysf_message_include_divider);
        this.l = findViewById(R.id.ysf_robot_useful_divider);
        this.m = findViewById(R.id.ysf_robot_stream_vertical_divider);
        this.n = (DotProgressBar) findViewById(R.id.ysf_robot_stream_progress);
        this.o = (LinearLayout) findViewById(R.id.ysf_robot_stream_progress_layout);
        this.p = (TextView) findViewById(R.id.ysf_robot_stream_progress_loading_text);
        this.g.setOnTagClickListener(this.r);
        this.g.setMaxSelectCount(1);
        this.g.setAdapter(this.s);
        TextView textView = this.j;
        Context context = textView.getContext();
        UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
        if (uICustomization == null || (color = uICustomization.hyperLinkColorLeft) == 0) {
            color = context.getResources().getColor(R.color.ysf_text_link_color_blue);
        }
        textView.setLinkTextColor(color);
        this.j.setOnTouchListener(ClickMovementMethod.newInstance());
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.k.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            this.l.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            this.m.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            this.d.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 3));
            this.d.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
            this.d.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.j.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.p.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
        }
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        final ah ahVar = (ah) this.message.getAttachment();
        String str = ahVar.d().c().get(0).c;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim())) {
            this.o.setVisibility(0);
            this.n.setVisibility(0);
            if (TextUtils.isEmpty(ahVar.f())) {
                this.p.setVisibility(8);
            } else {
                this.p.setVisibility(0);
                this.p.setText(ahVar.f());
            }
            this.j.setVisibility(8);
        } else {
            this.n.setVisibility(8);
            this.o.setVisibility(8);
            this.j.setVisibility(0);
            TextView textView = this.j;
            UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
            if (uICustomization != null) {
                float f = uICustomization.textMsgSize;
                if (f > 0.0f) {
                    textView.setTextSize(f);
                }
            }
            com.qiyukf.unicorn.n.f.a(textView, str, (int) textView.getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width), this.message.getSessionId());
            if (ahVar.d().d() > 0 && a(ahVar)) {
                this.a.setVisibility(0);
                this.b.setSelected(ahVar.d().d() == 2);
                this.c.setSelected(ahVar.d().d() == 3);
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    this.e.setTextColor(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().b().k(), Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f())));
                    this.e.setCompoundDrawables(com.qiyukf.unicorn.m.b.a(this.context, com.qiyukf.unicorn.m.a.a().b().k(), true), null, null, null);
                    this.f.setTextColor(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().b().k(), Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f())));
                    this.f.setCompoundDrawables(com.qiyukf.unicorn.m.b.a(this.context, com.qiyukf.unicorn.m.a.a().b().k(), false), null, null, null);
                    a(!this.d.getText().toString().isEmpty() || this.g.getSelectedList().size() > 0);
                }
                a(ahVar.d().d());
                this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if (e.this.b.isSelected()) {
                            return;
                        }
                        if (e.this.a(ahVar)) {
                            e.a(e.this, 2);
                        } else {
                            com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_evaluate_disable);
                        }
                    }
                });
                this.c.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if (e.this.c.isSelected()) {
                            return;
                        }
                        if (e.this.a(ahVar)) {
                            e.a(e.this, 3);
                        } else {
                            com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_evaluate_disable);
                        }
                    }
                });
                this.i.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        if (e.this.a(ahVar)) {
                            com.qiyukf.unicorn.h.a.f.w wVar = new com.qiyukf.unicorn.h.a.f.w();
                            wVar.b(ahVar.e());
                            wVar.c(e.this.d.getText().toString());
                            if (e.this.g.getSelectedList().iterator().hasNext()) {
                                wVar.a((String) e.this.q.get(e.this.g.getSelectedList().iterator().next().intValue()));
                            }
                            com.qiyukf.unicorn.k.c.a(wVar, ((MsgViewHolderBase) e.this).message.getSessionId()).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.3.1
                                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                                public final /* synthetic */ void onResult(int i, Void r2, Throwable th) {
                                    String string;
                                    if (i == 200) {
                                        if (((com.qiyukf.uikit.common.a.f) e.this).context != null) {
                                            string = ((com.qiyukf.uikit.common.a.f) e.this).context.getString(R.string.ysf_thanks_feedback);
                                        } else {
                                            string = "thanks";
                                        }
                                        com.qiyukf.unicorn.n.t.a(string);
                                        ahVar.d().b(0);
                                        ahVar.d().a(e.this.d.getText().toString());
                                        ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(((MsgViewHolderBase) e.this).message, true);
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
                this.d.addTextChangedListener(new TextWatcher() { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.4
                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        String string = editable.toString();
                        ahVar.d().a(string);
                        e.this.a(!string.isEmpty() || e.this.g.getSelectedList().size() > 0);
                    }
                });
                return;
            }
        }
        this.a.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        ah ahVar = (ah) this.message.getAttachment();
        if (i == 3 && ahVar.d().f()) {
            this.q.clear();
            if (ahVar.d().g() != null) {
                this.q.addAll(Arrays.asList(ahVar.d().g()));
            }
            this.s.notifyDataChanged();
            this.h.setVisibility(0);
            this.d.setText(ahVar.d().e());
            EditText editText = this.d;
            editText.setSelection(editText.getText().toString().length());
            this.d.setHint(ahVar.d().a(this.context));
            return;
        }
        this.h.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(ah ahVar) {
        if (ahVar.a() == 0) {
            return false;
        }
        String sessionId = this.message.getSessionId();
        return ahVar.a() == com.qiyukf.unicorn.c.h().d(sessionId) || ahVar.a() == com.qiyukf.unicorn.c.h().h(sessionId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        this.i.setEnabled(z);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.i.setTextColor(Color.parseColor(z ? com.qiyukf.unicorn.m.a.a().b().k() : com.qiyukf.unicorn.m.a.a().b().s().c()));
        } else {
            this.i.setTextColor(z ? Color.parseColor(com.qiyukf.unicorn.m.a.a().b().k()) : this.context.getResources().getColor(R.color.ysf_grey_999999));
        }
    }

    public static /* synthetic */ void a(e eVar, final int i) {
        eVar.b.setSelected(i == 2);
        eVar.c.setSelected(i == 3);
        final ah ahVar = (ah) eVar.message.getAttachment();
        com.qiyukf.unicorn.h.a.f.v vVar = new com.qiyukf.unicorn.h.a.f.v();
        vVar.a(ahVar.e());
        vVar.a(i);
        com.qiyukf.unicorn.k.c.a(vVar, eVar.message.getSessionId()).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.e.5
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i2, Void r2, Throwable th) {
                if (i2 == 200) {
                    ahVar.d().b(i);
                    ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(((MsgViewHolderBase) e.this).message, false);
                    if (i == 2 || !e.this.a(ahVar)) {
                        return;
                    }
                    if (ahVar.d().f()) {
                        e.this.a(i);
                    } else {
                        e.k(e.this);
                    }
                    e.this.getAdapter().b().c();
                    return;
                }
                com.qiyukf.unicorn.n.t.a(R.string.ysf_network_error);
            }
        });
        if (i == 3 && ahVar.d().f()) {
            eVar.h.setVisibility(0);
        } else {
            eVar.h.setVisibility(8);
        }
    }

    public static /* synthetic */ void k(e eVar) {
        ah ahVar = (ah) eVar.message.getAttachment();
        com.qiyukf.unicorn.h.a.f.w wVar = new com.qiyukf.unicorn.h.a.f.w();
        wVar.b(ahVar.e());
        wVar.c("");
        com.qiyukf.unicorn.k.c.a(wVar, eVar.message.getSessionId());
    }
}

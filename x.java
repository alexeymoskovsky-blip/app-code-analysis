package com.qiyukf.unicorn.ui.viewholder.a;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.h.a.a.a.q;
import com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout;
import com.qiyukf.unicorn.widget.pulltorefresh.PullableListView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: TemplateHolderOrderList.java */
/* JADX INFO: loaded from: classes6.dex */
public class x extends i implements View.OnClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener, PullToRefreshLayout.OnRefreshListener {
    private TextView a;
    private ListView b;
    private View c;
    private TextView d;
    private a e;
    private PopupWindow f;
    private View g;
    private View h;
    private PullToRefreshLayout i;
    private PullableListView j;
    private a k;
    private String l;
    private String m;
    private boolean n;
    private Observer<CustomNotification> o = new Observer<CustomNotification>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.x.2
        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(CustomNotification customNotification) {
            com.qiyukf.unicorn.h.a.b attachStr;
            CustomNotification customNotification2 = customNotification;
            if (x.this.n && customNotification2.getSessionType() == SessionTypeEnum.Ysf && (attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(customNotification2.getContent())) != null && (attachStr instanceof com.qiyukf.unicorn.h.a.a.b)) {
                com.qiyukf.unicorn.h.a.a.b bVar = (com.qiyukf.unicorn.h.a.a.b) attachStr;
                if (bVar.a() instanceof com.qiyukf.unicorn.h.a.a.a.q) {
                    x.this.n = false;
                    com.qiyukf.unicorn.h.a.a.a.q qVar = (com.qiyukf.unicorn.h.a.a.a.q) bVar.a();
                    if (qVar.e() == null || qVar.d().isEmpty()) {
                        x.this.j.setEnable(false, false);
                        x.this.i.loadMoreFinish(2);
                        return;
                    }
                    x.this.l = qVar.e().b();
                    x.this.m = qVar.e().c();
                    x.this.k.b(qVar.d());
                    x.this.k.notifyDataSetChanged();
                    x.this.i.loadMoreFinish(0);
                }
            }
        }
    };

    @Override // com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout.OnRefreshListener
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_message_item_bot_list;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.a = (TextView) findViewById(R.id.ysf_tv_bot_list_title);
        this.b = (ListView) findViewById(R.id.ysf_lv_bot_list);
        this.c = findViewById(R.id.ysf_bot_footer_layout);
        this.d = (TextView) findViewById(R.id.ysf_bot_footer_text);
        this.e = new a((byte) 0);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        com.qiyukf.unicorn.h.a.a.a.q qVar = (com.qiyukf.unicorn.h.a.a.a.q) this.message.getAttachment();
        this.a.setText(qVar.c());
        this.e.a(qVar.d());
        this.b.setAdapter((ListAdapter) this.e);
        this.b.setOnItemClickListener(this);
        if (qVar.e() != null && qVar.d().size() >= 3) {
            this.c.setVisibility(0);
            this.d.setText(qVar.e().a());
            this.d.setOnClickListener(this);
        } else {
            new q.a();
            this.c.setVisibility(8);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.d) {
            if (!g()) {
                com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_msg_invalid);
                return;
            }
            if (getAdapter().b().a()) {
                PopupWindow popupWindow = new PopupWindow(this.context);
                this.f = popupWindow;
                popupWindow.setWidth(-1);
                this.f.setHeight((int) (((double) com.qiyukf.unicorn.n.p.b()) * 0.8d));
                this.f.setContentView(LayoutInflater.from(this.context).inflate(R.layout.ysf_popup_window_bot_list, (ViewGroup) null));
                byte b = 0;
                this.f.setBackgroundDrawable(new ColorDrawable(0));
                this.f.setOutsideTouchable(false);
                this.f.setFocusable(true);
                this.f.setOnDismissListener(this);
                this.f.setAnimationStyle(R.style.ysf_dialog_window_animation_style);
                this.f.showAtLocation(((Activity) this.context).getWindow().getDecorView().findViewById(android.R.id.content), 81, 0, 0);
                com.qiyukf.unicorn.n.y.a(((Activity) this.context).getWindow(), 0.3f);
                this.g = this.f.getContentView().findViewById(R.id.ysf_bot_list_placeholder);
                TextView textView = (TextView) this.f.getContentView().findViewById(R.id.ysf_bot_list_title);
                this.h = this.f.getContentView().findViewById(R.id.ysf_bot_list_close);
                this.i = (PullToRefreshLayout) this.f.getContentView().findViewById(R.id.ysf_ptr_layout_bot_list);
                this.j = (PullableListView) this.f.getContentView().findViewById(R.id.ysf_lv_bot_list);
                textView.setText(R.string.ysf_bot_order_list_title);
                this.g.setOnClickListener(this);
                this.h.setOnClickListener(this);
                this.j.setOnItemClickListener(this);
                com.qiyukf.unicorn.h.a.a.a.q qVar = (com.qiyukf.unicorn.h.a.a.a.q) this.message.getAttachment();
                this.l = qVar.e().b();
                this.m = qVar.e().c();
                if (this.k == null) {
                    this.k = new a(b);
                }
                a aVar = this.k;
                this.k = aVar;
                aVar.a(qVar.d());
                this.j.setAdapter((ListAdapter) this.k);
                this.j.setEnable(false, true);
                this.i.setOnRefreshListener(this);
                a(true);
                getAdapter().b().b();
                return;
            }
            return;
        }
        if (view == this.h || view == this.g) {
            this.f.dismiss();
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        q.b.a aVar = (q.b.a) adapterView.getAdapter().getItem(i);
        if (aVar.j() != null) {
            OnMessageItemClickListener onMessageItemClickListener = com.qiyukf.unicorn.c.f().onMessageItemClickListener;
            if (onMessageItemClickListener != null) {
                onMessageItemClickListener.onURLClicked(this.context, aVar.j());
            }
        } else {
            if (!g()) {
                com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_msg_invalid);
                return;
            }
            com.qiyukf.unicorn.b.b.b bVar = new com.qiyukf.unicorn.b.b.b();
            bVar.fromJson(aVar.a());
            com.qiyukf.unicorn.b.b bVar2 = new com.qiyukf.unicorn.b.b();
            bVar2.a(aVar.b());
            bVar2.b(aVar.c());
            bVar2.a(bVar.c());
            bVar.a(bVar2);
            getAdapter().b().b(MessageBuilder.createCustomMessage(this.message.getSessionId(), SessionTypeEnum.Ysf, bVar));
        }
        if (adapterView == this.j) {
            this.f.dismiss();
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        com.qiyukf.unicorn.n.y.a(((Activity) this.context).getWindow(), 1.0f);
        a(false);
        this.n = false;
    }

    private void a(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.o, z);
    }

    /* JADX INFO: compiled from: TemplateHolderOrderList.java */
    public static class a extends BaseAdapter {
        private List<Object> a;

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getViewTypeCount() {
            return 2;
        }

        private a() {
            this.a = new ArrayList();
        }

        public /* synthetic */ a(byte b2) {
            this();
        }

        public final void a(List<q.b> list) {
            this.a.clear();
            b(list);
        }

        public final void b(List<q.b> list) {
            for (int i = 0; i < list.size(); i++) {
                q.b bVar = list.get(i);
                this.a.add(bVar);
                this.a.addAll(bVar.c());
            }
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.a.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return this.a.get(i);
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getItemViewType(int i) {
            return this.a.get(i) instanceof q.b ? 0 : 1;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return this.a.get(i) instanceof q.b.a;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            b bVar;
            C0205a c0205a;
            int itemViewType = getItemViewType(i);
            if (itemViewType == 0) {
                if (view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ysf_view_holder_order_list_order_header, (ViewGroup) null);
                    bVar = new b(view);
                    view.setTag(bVar);
                } else {
                    bVar = (b) view.getTag();
                }
                bVar.a((q.b) this.a.get(i), i == 0);
            } else if (itemViewType == 1) {
                if (view == null) {
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ysf_view_holder_order_list_goods, (ViewGroup) null);
                    c0205a = new C0205a(view);
                    view.setTag(c0205a);
                } else {
                    c0205a = (C0205a) view.getTag();
                }
                c0205a.a((q.b.a) this.a.get(i), i == this.a.size() - 1, i < this.a.size() - 1 && (this.a.get(i + 1) instanceof q.b));
            }
            return view;
        }

        /* JADX INFO: compiled from: TemplateHolderOrderList.java */
        public static class b {
            private View a;
            private View b;
            private TextView c;
            private TextView d;

            public b(View view) {
                this.a = view.findViewById(R.id.ysf_order_list_order_header_content);
                this.b = view.findViewById(R.id.ysf_order_list_header_divider);
                this.c = (TextView) view.findViewById(R.id.ysf_tv_order_shop_name);
                this.d = (TextView) view.findViewById(R.id.ysf_tv_order_state);
            }

            public final void a(q.b bVar, boolean z) {
                this.c.setText(bVar.a());
                this.d.setText(bVar.b());
                this.a.setPadding(0, z ? 0 : com.qiyukf.unicorn.n.p.a(10.0f), 0, 0);
                this.b.setVisibility(z ? 8 : 0);
            }
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.viewholder.a.x$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: TemplateHolderOrderList.java */
        public static class C0205a {
            private ImageView a;
            private TextView b;
            private TextView c;
            private TextView d;
            private TextView e;
            private TextView f;
            private View g;

            public C0205a(View view) {
                this.a = (ImageView) view.findViewById(R.id.ysf_iv_goods_img);
                this.b = (TextView) view.findViewById(R.id.ysf_tv_goods_name);
                this.c = (TextView) view.findViewById(R.id.ysf_tv_goods_sku);
                this.d = (TextView) view.findViewById(R.id.ysf_tv_goods_price);
                this.e = (TextView) view.findViewById(R.id.ysf_tv_goods_count);
                this.f = (TextView) view.findViewById(R.id.ysf_tv_goods_state);
                this.g = view.findViewById(R.id.ysf_v_order_list_goods_divider);
            }

            public final void a(q.b.a aVar, boolean z, boolean z2) {
                int iA = com.qiyukf.unicorn.n.p.a(60.0f);
                com.qiyukf.uikit.a.a(aVar.e(), this.a, iA, iA);
                this.b.setText(aVar.f());
                this.c.setText(aVar.i());
                this.d.setText(aVar.g());
                this.e.setText(aVar.h());
                this.f.setText(aVar.d());
                this.g.setVisibility(z ? 8 : 0);
                int iA2 = z2 ? 0 : com.qiyukf.unicorn.n.p.a(10.0f);
                this.g.setPadding(iA2, 0, iA2, 0);
            }
        }
    }

    @Override // com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout.OnRefreshListener
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (com.qiyukf.unicorn.c.h().d(this.message.getSessionId()) == 0) {
            this.i.loadMoreFinish(1);
            com.qiyukf.unicorn.n.t.a(R.string.ysf_bot_load_more_disabled);
            return;
        }
        com.qiyukf.unicorn.h.a.a.c cVar = new com.qiyukf.unicorn.h.a.a.c();
        cVar.b(this.l);
        cVar.c(this.m);
        cVar.a("order_list");
        com.qiyukf.unicorn.k.c.a(cVar, this.message.getSessionId()).setCallback(new RequestCallbackWrapper<Void>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.x.1
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i, Void r2, Throwable th) {
                if (i == 200) {
                    x.this.n = true;
                } else {
                    x.this.n = false;
                    x.this.i.loadMoreFinish(1);
                }
            }
        });
    }
}

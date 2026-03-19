package com.qiyukf.unicorn.ui.worksheet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.n.m;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.f.k;
import com.qiyukf.unicorn.h.a.a.a.x;
import com.qiyukf.unicorn.h.a.d.az;
import com.qiyukf.unicorn.h.a.f.ai;
import com.qiyukf.unicorn.h.a.f.aj;
import com.qiyukf.unicorn.h.a.f.ak;
import com.qiyukf.unicorn.mediaselect.internal.entity.Item;
import com.qiyukf.unicorn.mediaselect.internal.model.SelectedItemCollection;
import com.qiyukf.unicorn.mediaselect.internal.ui.activity.BasePreviewActivity;
import com.qiyukf.unicorn.mediaselect.internal.utils.PathUtils;
import com.qiyukf.unicorn.n.b.e;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.ui.worksheet.c;
import com.qiyukf.unicorn.widget.MultipleStatusLayout;
import com.qiyukf.unicorn.widget.ScrollGridView;
import com.qiyukf.unicorn.widget.dialog.ProgressDialog;
import com.qiyukf.unicorn.widget.timepicker.TimeSelector;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: WorkSheetDialog.java */
/* JADX INFO: loaded from: classes6.dex */
public final class d extends Dialog implements c.a {
    private long A;
    private long B;
    private com.qiyukf.unicorn.ui.a.b C;
    private Observer<CustomNotification> D;
    private final Item a;
    private a b;
    private a c;
    private x d;
    private String e;
    private ArrayList<Item> f;
    private Map<ScrollGridView, ArrayList<Item>> g;
    private Map<ScrollGridView, Boolean> h;
    private boolean i;
    private Context j;
    private Fragment k;
    private Button l;
    private com.qiyukf.unicorn.ui.a.b m;
    private ScrollGridView n;
    private LinearLayout o;
    private ProgressDialog p;
    private LinearLayout q;
    private ScrollView r;
    private TextView s;
    private TextView t;
    private TextView u;
    private FrameLayout v;
    private ImageView w;
    private View x;
    private MultipleStatusLayout y;
    private List<x.a> z;

    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public interface a {
        void jumpSelectAnnexActivity(int i);

        void jumpWatchImgActivity(ArrayList<Item> arrayList, int i);

        void onSubmitDone(String str);
    }

    public d(@NonNull Fragment fragment, x xVar, String str, a aVar, a aVar2) {
        super(fragment.getActivity(), R.style.ysf_popup_dialog_style);
        this.a = Item.createEmpteItem();
        this.f = new ArrayList<>();
        this.g = new LinkedHashMap();
        this.h = new LinkedHashMap();
        this.i = false;
        this.z = new ArrayList();
        this.B = 0L;
        this.D = new Observer<CustomNotification>() { // from class: com.qiyukf.unicorn.ui.worksheet.d.1
            public AnonymousClass1() {
            }

            @Override // com.qiyukf.nimlib.sdk.Observer
            public final /* synthetic */ void onEvent(CustomNotification customNotification) {
                CustomNotification customNotification2 = customNotification;
                if (TextUtils.equals(d.this.e, customNotification2.getSessionId()) && customNotification2.getSessionType() == SessionTypeEnum.Ysf) {
                    d.a(d.this, customNotification2);
                }
            }
        };
        this.j = fragment.getActivity();
        this.k = fragment;
        this.d = xVar;
        this.e = str;
        this.b = aVar;
        this.c = aVar2;
        if (xVar != null) {
            this.z.addAll(xVar.h());
        }
        a();
    }

    public d(@NonNull Fragment fragment, long j, String str, a aVar, a aVar2) {
        super(fragment.getActivity(), R.style.ysf_popup_dialog_style);
        this.a = Item.createEmpteItem();
        this.f = new ArrayList<>();
        this.g = new LinkedHashMap();
        this.h = new LinkedHashMap();
        this.i = false;
        this.z = new ArrayList();
        this.B = 0L;
        this.D = new Observer<CustomNotification>() { // from class: com.qiyukf.unicorn.ui.worksheet.d.1
            public AnonymousClass1() {
            }

            @Override // com.qiyukf.nimlib.sdk.Observer
            public final /* synthetic */ void onEvent(CustomNotification customNotification) {
                CustomNotification customNotification2 = customNotification;
                if (TextUtils.equals(d.this.e, customNotification2.getSessionId()) && customNotification2.getSessionType() == SessionTypeEnum.Ysf) {
                    d.a(d.this, customNotification2);
                }
            }
        };
        this.k = fragment;
        this.j = fragment.getActivity();
        this.A = j;
        this.e = str;
        this.b = aVar;
        this.c = aVar2;
        a();
    }

    public final void a(Intent intent, int i) {
        com.qiyukf.unicorn.ui.a.b bVar;
        if (com.qiyukf.unicorn.c.f().isUseSAF) {
            Uri data = intent.getData();
            String strA = w.a(this.j, data);
            a(new Item(intent.hashCode(), e.c(strA) ? "image/*" : e.d(strA) ? "video/*" : "*/*", data, 0L, 0L), i);
        } else {
            Bundle bundleExtra = intent.getBundleExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE);
            if (bundleExtra == null) {
                return;
            }
            ArrayList parcelableArrayList = bundleExtra.getParcelableArrayList(SelectedItemCollection.STATE_SELECTION);
            if (parcelableArrayList != null) {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    a((Item) it.next(), i);
                }
            }
        }
        if (i == 1) {
            com.qiyukf.unicorn.ui.a.b bVar2 = this.m;
            if (bVar2 != null) {
                bVar2.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i != 2 || (bVar = this.C) == null) {
            return;
        }
        bVar.notifyDataSetChanged();
    }

    public final void b(Intent intent, int i) {
        ArrayList parcelableArrayList;
        Bundle bundleExtra = intent.getBundleExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE);
        if (bundleExtra == null || (parcelableArrayList = bundleExtra.getParcelableArrayList(SelectedItemCollection.STATE_SELECTION)) == null) {
            return;
        }
        if (i == 1) {
            this.f.clear();
            this.f.add(this.a);
            Iterator it = parcelableArrayList.iterator();
            while (it.hasNext()) {
                a((Item) it.next(), i);
            }
            com.qiyukf.unicorn.ui.a.b bVar = this.m;
            if (bVar != null) {
                bVar.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i == 2) {
            for (ScrollGridView scrollGridView : this.g.keySet()) {
                if (scrollGridView.getAdapter() == this.C && this.g.get(scrollGridView) != null) {
                    this.g.get(scrollGridView).clear();
                    this.g.get(scrollGridView).add(this.a);
                }
            }
            Iterator it2 = parcelableArrayList.iterator();
            while (it2.hasNext()) {
                a((Item) it2.next(), i);
            }
            com.qiyukf.unicorn.ui.a.b bVar2 = this.C;
            if (bVar2 != null) {
                bVar2.notifyDataSetChanged();
            }
        }
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        attributes.gravity = 80;
        getWindow().setAttributes(attributes);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void cancel() {
        try {
            a(false);
            super.cancel();
        } catch (Exception e) {
            AbsUnicornLog.e("WorkSheetDialog", "WorkSheetDialog cancel is error", e);
        }
    }

    private void a() {
        setContentView(LayoutInflater.from(getContext()).inflate(R.layout.ysf_dialog_work_sheet, (ViewGroup) null));
        if (this.j instanceof Activity) {
            a(true);
            b();
            c();
            g();
            return;
        }
        cancel();
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$1 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass1 implements Observer<CustomNotification> {
        public AnonymousClass1() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public final /* synthetic */ void onEvent(CustomNotification customNotification) {
            CustomNotification customNotification2 = customNotification;
            if (TextUtils.equals(d.this.e, customNotification2.getSessionId()) && customNotification2.getSessionType() == SessionTypeEnum.Ysf) {
                d.a(d.this, customNotification2);
            }
        }
    }

    private void a(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.D, z);
    }

    private void b() {
        this.n = (ScrollGridView) findViewById(R.id.ysf_gv_work_sheet_annex_list);
        this.y = (MultipleStatusLayout) findViewById(R.id.ysf_msl_work_sheet_parent);
        this.t = (TextView) findViewById(R.id.ysf_tv_work_sheet_tip);
        this.l = (Button) findViewById(R.id.ysf_work_sheet_done);
        this.s = (TextView) findViewById(R.id.ysf_tv_work_sheet_annex_label);
        this.o = (LinearLayout) findViewById(R.id.ysf_work_sheet_customize_field_layout);
        this.q = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_item_parent);
        this.r = (ScrollView) findViewById(R.id.ysf_sl_work_sheet_list_parent);
        this.u = (TextView) findViewById(R.id.ysf_work_sheet_info);
        this.v = (FrameLayout) findViewById(R.id.ysf_work_sheet_info_fl);
        this.w = (ImageView) findViewById(R.id.ysf_work_sheet_close);
        this.x = findViewById(R.id.ysf_message_include_divider);
        this.w.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.d.5
            public AnonymousClass5() {
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                d.this.cancel();
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$5 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass5 implements View.OnClickListener {
        public AnonymousClass5() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            d.this.cancel();
        }
    }

    private void c() {
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.u.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.s.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.t.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            this.l.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()));
            this.l.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().e()));
            this.x.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            Drawable drawable = this.w.getDrawable();
            int color = Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b());
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            drawable.setColorFilter(color, mode);
            if (w.a()) {
                this.v.getBackground().setColorFilter(this.j.getResources().getColor(R.color.ysf_dark_module), mode);
                this.r.getBackground().setColorFilter(this.j.getResources().getColor(R.color.ysf_dark_module), mode);
            }
        } else {
            this.l.setBackgroundResource(R.drawable.ysf_evaluation_dialog_btn_submit_bg_selector);
        }
        com.qiyukf.unicorn.m.a.a().a(this.l);
        d();
        this.l.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.d.6
            public AnonymousClass6() {
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                d.b(d.this);
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$6 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass6 implements View.OnClickListener {
        public AnonymousClass6() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            d.b(d.this);
        }
    }

    private void d() {
        ArrayList<Item> arrayList = this.f;
        if (arrayList != null && arrayList.size() == 0) {
            this.f.add(this.a);
        }
        com.qiyukf.unicorn.ui.a.b bVar = new com.qiyukf.unicorn.ui.a.b(this.k, this.f, new k() { // from class: com.qiyukf.unicorn.ui.worksheet.d.7
            @Override // com.qiyukf.unicorn.f.k
            public /* synthetic */ void a(com.qiyukf.unicorn.ui.a.b bVar2) {
                k.CC.$default$a(this, bVar2);
            }

            public AnonymousClass7() {
            }

            @Override // com.qiyukf.unicorn.f.k
            public final void removePhoto(int i) {
                d.this.f.remove(i);
                if (!Item.EMPTY_TYPE_TAG.equals(((Item) d.this.f.get(d.this.f.size() - 1)).mimeType)) {
                    d.this.f.add(d.this.a);
                }
                d.this.m.notifyDataSetChanged();
            }
        }, this.b);
        this.m = bVar;
        this.n.setAdapter((ListAdapter) bVar);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$7 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass7 implements k {
        @Override // com.qiyukf.unicorn.f.k
        public /* synthetic */ void a(com.qiyukf.unicorn.ui.a.b bVar2) {
            k.CC.$default$a(this, bVar2);
        }

        public AnonymousClass7() {
        }

        @Override // com.qiyukf.unicorn.f.k
        public final void removePhoto(int i) {
            d.this.f.remove(i);
            if (!Item.EMPTY_TYPE_TAG.equals(((Item) d.this.f.get(d.this.f.size() - 1)).mimeType)) {
                d.this.f.add(d.this.a);
            }
            d.this.m.notifyDataSetChanged();
        }
    }

    private void a(ScrollGridView scrollGridView) {
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(this.a);
        scrollGridView.setAdapter((ListAdapter) new com.qiyukf.unicorn.ui.a.b(this.k, arrayList, new k() { // from class: com.qiyukf.unicorn.ui.worksheet.d.8
            final /* synthetic */ ArrayList a;
            final /* synthetic */ ScrollGridView b;

            public AnonymousClass8(ArrayList arrayList2, ScrollGridView scrollGridView2) {
                arrayList = arrayList2;
                scrollGridView = scrollGridView2;
            }

            @Override // com.qiyukf.unicorn.f.k
            public final void a(com.qiyukf.unicorn.ui.a.b bVar) {
                d.this.C = bVar;
            }

            @Override // com.qiyukf.unicorn.f.k
            public final void removePhoto(int i) {
                arrayList.remove(i);
                if (!Item.EMPTY_TYPE_TAG.equals(((Item) arrayList.get(r2.size() - 1)).mimeType)) {
                    arrayList.add(d.this.a);
                }
                ((com.qiyukf.unicorn.ui.a.b) scrollGridView.getAdapter()).notifyDataSetChanged();
            }
        }, this.c));
        this.g.put(scrollGridView2, arrayList2);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$8 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass8 implements k {
        final /* synthetic */ ArrayList a;
        final /* synthetic */ ScrollGridView b;

        public AnonymousClass8(ArrayList arrayList2, ScrollGridView scrollGridView2) {
            arrayList = arrayList2;
            scrollGridView = scrollGridView2;
        }

        @Override // com.qiyukf.unicorn.f.k
        public final void a(com.qiyukf.unicorn.ui.a.b bVar) {
            d.this.C = bVar;
        }

        @Override // com.qiyukf.unicorn.f.k
        public final void removePhoto(int i) {
            arrayList.remove(i);
            if (!Item.EMPTY_TYPE_TAG.equals(((Item) arrayList.get(r2.size() - 1)).mimeType)) {
                arrayList.add(d.this.a);
            }
            ((com.qiyukf.unicorn.ui.a.b) scrollGridView.getAdapter()).notifyDataSetChanged();
        }
    }

    private boolean e() {
        return (this.A == 0 || this.d == null) ? false : true;
    }

    private boolean f() {
        x xVar = this.d;
        return xVar != null && xVar.j();
    }

    private void g() {
        if (this.d == null) {
            x xVarA = com.qiyukf.unicorn.c.h().a(this.A);
            this.d = xVarA;
            if (xVarA != null) {
                this.z.addAll(xVarA.h());
                Iterator<x.a> it = this.z.iterator();
                while (it.hasNext()) {
                    it.next().a("");
                }
            }
        }
        x xVar = this.d;
        if (xVar == null) {
            if (this.A == 0) {
                t.a("templateId is error");
                cancel();
                return;
            } else {
                this.y.showLoadingView();
                aj ajVar = new aj();
                ajVar.b(this.A);
                com.qiyukf.unicorn.k.c.a(ajVar, this.e);
                return;
            }
        }
        if (TextUtils.isEmpty(xVar.e())) {
            this.t.setVisibility(8);
        } else {
            this.t.setText(this.d.e());
            this.t.setVisibility(0);
        }
        this.y.showContent();
        for (x.a aVar : this.z) {
            if (aVar.f().equals("uploadFile")) {
                String string = TextUtils.isEmpty(aVar.a()) ? this.j.getString(R.string.ysf_annex_str) : aVar.a();
                StringBuilder sb = new StringBuilder();
                sb.append(string);
                sb.append(aVar.b() == 1 ? this.j.getString(R.string.ysf_require_field) : "");
                String string2 = sb.toString();
                this.i = aVar.b() == 1;
                this.n.setVisibility(0);
                this.s.setVisibility(0);
                this.s.setText(string2);
            } else if (aVar.c() == 7) {
                View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.ysf_work_sheet_customize_field, (ViewGroup) null);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tv_work_sheet_annex_label_other);
                ScrollGridView scrollGridView = (ScrollGridView) viewInflate.findViewById(R.id.ysf_gv_work_sheet_annex_list_other);
                String string3 = TextUtils.isEmpty(aVar.a()) ? this.j.getString(R.string.ysf_annex_str) : aVar.a();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(string3);
                sb2.append(aVar.b() == 1 ? this.j.getString(R.string.ysf_require_field) : "");
                String string4 = sb2.toString();
                this.h.put(scrollGridView, Boolean.valueOf(aVar.b() == 1));
                scrollGridView.setVisibility(0);
                scrollGridView.setTag(aVar);
                textView.setVisibility(0);
                textView.setText(string4);
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    textView.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
                }
                a(scrollGridView);
                this.o.addView(viewInflate);
            } else {
                a(aVar);
            }
        }
    }

    private void h() {
        if (!o.a(this.j)) {
            t.a(R.string.ysf_network_unable);
            return;
        }
        for (int i = 0; i < this.q.getChildCount(); i++) {
            ViewGroup viewGroup = (ViewGroup) this.q.getChildAt(i);
            if (viewGroup.getTag() != null) {
                x.a aVar = (x.a) viewGroup.getTag();
                if (aVar.c() == 0) {
                    Editable text = "2".equals(aVar.g()) ? ((EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_multiline)).getText() : ((EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content)).getText();
                    if (aVar.b() == 1 && TextUtils.isEmpty(text)) {
                        t.b(R.string.ysf_leave_msg_menu_required_tips);
                        i();
                        return;
                    }
                } else {
                    EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content);
                    if (aVar.b() == 1 && TextUtils.isEmpty(editText.getText().toString())) {
                        t.b(R.string.ysf_leave_msg_menu_required_tips);
                        i();
                        return;
                    }
                }
            }
        }
        if (this.i && this.f.size() == 1 && Item.EMPTY_TYPE_TAG.equals(this.f.get(0).mimeType)) {
            t.a(R.string.ysf_leave_msg_annex_toast);
            return;
        }
        for (Map.Entry<ScrollGridView, Boolean> entry : this.h.entrySet()) {
            ScrollGridView key = entry.getKey();
            if (entry.getValue().booleanValue() && this.g.get(key).size() == 1 && Item.EMPTY_TYPE_TAG.equals(this.g.get(key).get(0).mimeType)) {
                t.a(R.string.ysf_leave_msg_annex_toast);
                return;
            }
        }
        String string = this.j.getString(R.string.ysf_submit_ing_str);
        if (this.p == null) {
            ProgressDialog progressDialog = new ProgressDialog(this.j);
            this.p = progressDialog;
            progressDialog.setCancelable(false);
            this.p.setMessage(string);
        }
        this.p.show();
        if (this.f.size() != 1 || this.g.size() > 0) {
            ArrayList arrayList = new ArrayList(6);
            ArrayList arrayList2 = new ArrayList(6);
            for (Item item : this.f) {
                if (!Item.EMPTY_TYPE_TAG.equals(item.mimeType)) {
                    if (com.qiyukf.unicorn.c.f().isUseSAF) {
                        arrayList.add(item.getContentUri().toString());
                    } else {
                        arrayList.add(PathUtils.getPath(this.j, item.getContentUri()));
                    }
                    arrayList2.add(item.getContentUri());
                }
            }
            Iterator<ArrayList<Item>> it = this.g.values().iterator();
            while (it.hasNext()) {
                for (Item item2 : it.next()) {
                    if (!Item.EMPTY_TYPE_TAG.equals(item2.mimeType)) {
                        if (com.qiyukf.unicorn.c.f().isUseSAF) {
                            arrayList.add(item2.getContentUri().toString());
                        } else {
                            arrayList.add(PathUtils.getPath(this.j, item2.getContentUri()));
                        }
                        arrayList2.add(item2.getContentUri());
                    }
                }
            }
            if (arrayList.size() == 0 || TextUtils.isEmpty((CharSequence) arrayList.get(0))) {
                a((JSONArray) null);
                return;
            }
            JSONArray jSONArray = new JSONArray();
            if (o.a() || com.qiyukf.unicorn.c.f().isUseSAF) {
                a(arrayList, arrayList2, 0, jSONArray);
                return;
            } else {
                a(arrayList, 0, jSONArray);
                return;
            }
        }
        a((JSONArray) null);
    }

    @Override // com.qiyukf.unicorn.ui.worksheet.c.a
    public final void a(String str, String str2) {
        b(str, str2);
    }

    public void b(String str, String str2) {
        for (int i = 0; i < this.q.getChildCount(); i++) {
            ViewGroup viewGroup = (ViewGroup) this.q.getChildAt(i);
            if (viewGroup.getTag() != null) {
                x.a aVar = (x.a) viewGroup.getTag();
                if (aVar.f().equals(str) && aVar.c() != 0) {
                    EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content);
                    if (this.j.getString(R.string.ysf_unselect_str).equals(str2) || TextUtils.isEmpty(str2)) {
                        editText.setHint(R.string.ysf_please_choose_str);
                        str2 = null;
                    }
                    aVar.a(str2);
                    editText.setText(str2);
                    return;
                }
            }
        }
    }

    private void a(Item item, int i) {
        if (i == 1) {
            if (this.f.size() <= 4) {
                ArrayList<Item> arrayList = this.f;
                arrayList.add(arrayList.size() - 1, item);
                return;
            } else {
                if (this.f.size() == 5) {
                    ArrayList<Item> arrayList2 = this.f;
                    arrayList2.remove(arrayList2.size() - 1);
                    this.f.add(item);
                    return;
                }
                return;
            }
        }
        if (i == 2) {
            for (ScrollGridView scrollGridView : this.g.keySet()) {
                if (scrollGridView.getAdapter() == this.C && this.g.get(scrollGridView) != null) {
                    if (this.g.get(scrollGridView).size() <= 4) {
                        this.g.get(scrollGridView).add(this.g.get(scrollGridView).size() - 1, item);
                    } else if (this.g.get(scrollGridView).size() == 5) {
                        this.g.get(scrollGridView).remove(this.g.get(scrollGridView).size() - 1);
                        this.g.get(scrollGridView).add(item);
                    }
                }
            }
        }
    }

    private void a(x.a aVar) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String strA = aVar.a();
        if (!TextUtils.isEmpty(strA)) {
            spannableStringBuilder.append((CharSequence) strA);
        } else {
            spannableStringBuilder.append((CharSequence) this.j.getString(R.string.ysf_please_input_str));
        }
        if (aVar.b() == 1) {
            spannableStringBuilder.append((CharSequence) this.j.getString(R.string.ysf_require_field));
        }
        a(spannableStringBuilder, aVar, b(aVar));
    }

    private View.OnClickListener b(x.a aVar) {
        int iC = aVar.c();
        if (iC == 1 || iC == 2) {
            return new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.d.9
                final /* synthetic */ x.a a;

                public AnonymousClass9(x.a aVar2) {
                    aVar = aVar2;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    c cVar = new c(d.this.j, aVar);
                    cVar.a(d.this);
                    cVar.show();
                }
            };
        }
        if (iC != 3) {
            return null;
        }
        return new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.d.10
            final /* synthetic */ x.a a;

            public AnonymousClass10(x.a aVar2) {
                aVar = aVar2;
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$10$1 */
            /* JADX INFO: compiled from: WorkSheetDialog.java */
            public class AnonymousClass1 implements TimeSelector.ResultHandler {
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                public final void handle(String str, Date date) {
                    d.this.B = date.getTime();
                    AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                    d.this.b(String.valueOf(aVar.f()), str);
                }
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TimeSelector timeSelector = new TimeSelector(d.this.j, new TimeSelector.ResultHandler() { // from class: com.qiyukf.unicorn.ui.worksheet.d.10.1
                    public AnonymousClass1() {
                    }

                    @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                    public final void handle(String str, Date date) {
                        d.this.B = date.getTime();
                        AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                        d.this.b(String.valueOf(aVar.f()), str);
                    }
                }, TimeSelector.START_TIME, TimeSelector.END_TIME, true, true, true, true, true, "yyyy-MM-dd HH:mm", aVar.a());
                timeSelector.setCurrentData(new Date());
                timeSelector.show();
            }
        };
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$9 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass9 implements View.OnClickListener {
        final /* synthetic */ x.a a;

        public AnonymousClass9(x.a aVar2) {
            aVar = aVar2;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            c cVar = new c(d.this.j, aVar);
            cVar.a(d.this);
            cVar.show();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$10 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass10 implements View.OnClickListener {
        final /* synthetic */ x.a a;

        public AnonymousClass10(x.a aVar2) {
            aVar = aVar2;
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$10$1 */
        /* JADX INFO: compiled from: WorkSheetDialog.java */
        public class AnonymousClass1 implements TimeSelector.ResultHandler {
            public AnonymousClass1() {
            }

            @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
            public final void handle(String str, Date date) {
                d.this.B = date.getTime();
                AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                d.this.b(String.valueOf(aVar.f()), str);
            }
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            TimeSelector timeSelector = new TimeSelector(d.this.j, new TimeSelector.ResultHandler() { // from class: com.qiyukf.unicorn.ui.worksheet.d.10.1
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                public final void handle(String str, Date date) {
                    d.this.B = date.getTime();
                    AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                    d.this.b(String.valueOf(aVar.f()), str);
                }
            }, TimeSelector.START_TIME, TimeSelector.END_TIME, true, true, true, true, true, "yyyy-MM-dd HH:mm", aVar.a());
            timeSelector.setCurrentData(new Date());
            timeSelector.show();
        }
    }

    private void a(CharSequence charSequence, x.a aVar, View.OnClickListener onClickListener) {
        ViewGroup viewGroup = (ViewGroup) View.inflate(this.j, R.layout.ysf_item_work_sheet_dialog, null);
        viewGroup.setTag(aVar);
        TextView textView = (TextView) viewGroup.findViewById(R.id.ysf_tv_item_work_sheet_label);
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.ysf_iv_work_sheet_info_arrow);
        RelativeLayout relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.ysf_rl_item_work_sheet_input);
        EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content);
        LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.ysf_ll_work_sheet_work_item_multiline_parent);
        EditText editText2 = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_multiline);
        TextView textView2 = (TextView) viewGroup.findViewById(R.id.ysf_tv_work_sheet_item_multiline_count);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            relativeLayout.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 5));
            linearLayout.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 5));
            textView.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            editText.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
            editText.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            imageView.getDrawable().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()), PorterDuff.Mode.SRC_IN);
            editText2.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
            editText2.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            textView2.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
        }
        textView.setText(charSequence);
        if (!TextUtils.isEmpty(aVar.d())) {
            aVar.a(aVar.d());
        }
        if (onClickListener != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000000000)});
            imageView.setVisibility(0);
            relativeLayout.setOnClickListener(onClickListener);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            if (!TextUtils.isEmpty(aVar.d())) {
                editText.setText(aVar.d());
            } else {
                editText.setHint(R.string.ysf_please_choose_str);
            }
        } else if ("2".equals(aVar.g())) {
            linearLayout.setVisibility(0);
            relativeLayout.setVisibility(8);
            if (!TextUtils.isEmpty(aVar.e())) {
                editText2.setHint(aVar.e());
            } else {
                editText2.setHint(R.string.ysf_please_input_str);
            }
            if (!TextUtils.isEmpty(aVar.d())) {
                editText2.setText(aVar.d());
                textView2.setText(aVar.d().length() + "/500");
                editText2.addTextChangedListener(a(textView2));
            } else {
                textView2.setText("0/500");
                editText2.addTextChangedListener(a(textView2));
            }
        } else {
            imageView.setVisibility(8);
            if (!TextUtils.isEmpty(aVar.e())) {
                editText.setHint(aVar.e());
            } else {
                editText.setHint(R.string.ysf_please_input_str);
            }
            if (!TextUtils.isEmpty(aVar.d())) {
                editText.setText(aVar.d());
            }
            if ("1".equals(aVar.g())) {
                editText.setInputType(2);
            }
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.bottomMargin = p.a(10.0f);
        this.q.addView(viewGroup, layoutParams);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$11 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass11 implements TextWatcher {
        final /* synthetic */ TextView a;

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public AnonymousClass11(TextView textView) {
            textView = textView;
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            textView.setText(editable.length() + "/500");
        }
    }

    private TextWatcher a(TextView textView) {
        return new TextWatcher() { // from class: com.qiyukf.unicorn.ui.worksheet.d.11
            final /* synthetic */ TextView a;

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public AnonymousClass11(TextView textView2) {
                textView = textView2;
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                textView.setText(editable.length() + "/500");
            }
        };
    }

    public void i() {
        ProgressDialog progressDialog = this.p;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void a(List<String> list, List<Uri> list2, int i, JSONArray jSONArray) {
        String str;
        if (list2.size() == i) {
            a(jSONArray);
            return;
        }
        if (list2.size() == 0 || list2.get(i) == null) {
            return;
        }
        String strA = m.a(com.qiyukf.nimlib.c.d(), list2.get(i));
        if (com.qiyukf.unicorn.c.f().isUseSAF) {
            String strA2 = w.a(this.j, Uri.parse(list.get(i)));
            if (TextUtils.isEmpty(strA2)) {
                strA2 = "png";
            }
            str = strA + "." + strA2;
        } else {
            str = strA + "." + e.a(list.get(i));
        }
        String strA3 = com.qiyukf.unicorn.n.e.d.a(str, com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (com.qiyukf.nimlib.net.a.c.a.a(com.qiyukf.nimlib.c.d(), list2.get(i), strA3)) {
            File file = new File(strA3);
            if (!com.qiyukf.unicorn.c.f().isUseSAF || file.length() <= 52428800) {
                FileAttachment fileAttachment = new FileAttachment();
                fileAttachment.setPath(file.getPath());
                fileAttachment.setSize(file.length());
                fileAttachment.setDisplayName(file.getName());
                ((MsgService) NIMClient.getService(MsgService.class)).sendFile(fileAttachment).setCallback(new RequestCallbackWrapper<FileAttachment>() { // from class: com.qiyukf.unicorn.ui.worksheet.d.12
                    final /* synthetic */ JSONArray a;
                    final /* synthetic */ List b;
                    final /* synthetic */ List c;
                    final /* synthetic */ int d;

                    public AnonymousClass12(JSONArray jSONArray2, List list3, List list22, int i2) {
                        jSONArray = jSONArray2;
                        list = list3;
                        list = list22;
                        i = i2;
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public final /* synthetic */ void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
                        FileAttachment fileAttachment3 = fileAttachment2;
                        if (i2 == 200) {
                            JSONObject jSONObject = new JSONObject();
                            j.a(jSONObject, "name", fileAttachment3.getDisplayName());
                            j.a(jSONObject, "size", fileAttachment3.getSize());
                            j.a(jSONObject, "url", fileAttachment3.getUrl());
                            j.a(jSONArray, jSONObject);
                            d.this.a((List<String>) list, (List<Uri>) list, i + 1, jSONArray);
                            return;
                        }
                        t.a(R.string.ysf_bot_form_upload_image_failed);
                    }
                });
                return;
            }
            return;
        }
        t.a(R.string.ysf_video_exception);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$12 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass12 extends RequestCallbackWrapper<FileAttachment> {
        final /* synthetic */ JSONArray a;
        final /* synthetic */ List b;
        final /* synthetic */ List c;
        final /* synthetic */ int d;

        public AnonymousClass12(JSONArray jSONArray2, List list3, List list22, int i2) {
            jSONArray = jSONArray2;
            list = list3;
            list = list22;
            i = i2;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
            FileAttachment fileAttachment3 = fileAttachment2;
            if (i2 == 200) {
                JSONObject jSONObject = new JSONObject();
                j.a(jSONObject, "name", fileAttachment3.getDisplayName());
                j.a(jSONObject, "size", fileAttachment3.getSize());
                j.a(jSONObject, "url", fileAttachment3.getUrl());
                j.a(jSONArray, jSONObject);
                d.this.a((List<String>) list, (List<Uri>) list, i + 1, jSONArray);
                return;
            }
            t.a(R.string.ysf_bot_form_upload_image_failed);
        }
    }

    public void a(List<String> list, int i, JSONArray jSONArray) {
        if (list.size() == i) {
            a(jSONArray);
            return;
        }
        String strA = com.qiyukf.unicorn.n.e.d.a(m.b(list.get(i)) + "." + e.a(list.get(i)), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (com.qiyukf.nimlib.net.a.c.a.a(list.get(i), strA) != -1) {
            File file = new File(strA);
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setPath(file.getPath());
            fileAttachment.setSize(file.length());
            fileAttachment.setDisplayName(file.getName());
            ((MsgService) NIMClient.getService(MsgService.class)).sendFile(fileAttachment).setCallback(new RequestCallbackWrapper<FileAttachment>() { // from class: com.qiyukf.unicorn.ui.worksheet.d.2
                final /* synthetic */ JSONArray a;
                final /* synthetic */ List b;
                final /* synthetic */ int c;

                public AnonymousClass2(JSONArray jSONArray2, List list2, int i2) {
                    jSONArray = jSONArray2;
                    list = list2;
                    i = i2;
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
                    FileAttachment fileAttachment3 = fileAttachment2;
                    if (i2 == 200) {
                        JSONObject jSONObject = new JSONObject();
                        j.a(jSONObject, "name", fileAttachment3.getDisplayName());
                        j.a(jSONObject, "size", fileAttachment3.getSize());
                        j.a(jSONObject, "url", fileAttachment3.getUrl());
                        j.a(jSONArray, jSONObject);
                        d.this.a((List<String>) list, i + 1, jSONArray);
                        return;
                    }
                    t.a(R.string.ysf_bot_form_upload_image_failed);
                }
            });
            return;
        }
        t.a(R.string.ysf_media_exception);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$2 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass2 extends RequestCallbackWrapper<FileAttachment> {
        final /* synthetic */ JSONArray a;
        final /* synthetic */ List b;
        final /* synthetic */ int c;

        public AnonymousClass2(JSONArray jSONArray2, List list2, int i2) {
            jSONArray = jSONArray2;
            list = list2;
            i = i2;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public final /* synthetic */ void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
            FileAttachment fileAttachment3 = fileAttachment2;
            if (i2 == 200) {
                JSONObject jSONObject = new JSONObject();
                j.a(jSONObject, "name", fileAttachment3.getDisplayName());
                j.a(jSONObject, "size", fileAttachment3.getSize());
                j.a(jSONObject, "url", fileAttachment3.getUrl());
                j.a(jSONArray, jSONObject);
                d.this.a((List<String>) list, i + 1, jSONArray);
                return;
            }
            t.a(R.string.ysf_bot_form_upload_image_failed);
        }
    }

    private void a(JSONArray jSONArray) {
        int i;
        com.qiyukf.unicorn.b.b bVar = new com.qiyukf.unicorn.b.b();
        ak akVar = new ak();
        ai aiVar = new ai();
        List<ai.a> arrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        x xVar = this.d;
        if (xVar != null && xVar.g() != null) {
            sb.append(this.d.g());
        }
        for (int i2 = 0; i2 < this.q.getChildCount(); i2++) {
            ViewGroup viewGroup = (ViewGroup) this.q.getChildAt(i2);
            if (viewGroup.getTag() != null) {
                ai.a aVar = new ai.a();
                x.a aVar2 = (x.a) viewGroup.getTag();
                if (aVar2.c() == 0) {
                    Editable text = "2".equals(aVar2.g()) ? ((EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_multiline)).getText() : ((EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content)).getText();
                    aVar.a(aVar2.f());
                    aVar.b(aVar2.a());
                    aVar.a(text.toString().trim());
                    String strTrim = text.toString().trim();
                    sb.append("&");
                    sb.append(aVar2.f());
                    sb.append(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                    sb.append(strTrim);
                } else if (aVar2.c() == 3) {
                    aVar.a(aVar2.f());
                    aVar.b(aVar2.a());
                    long j = this.B;
                    if (j == 0) {
                        aVar.a("");
                        sb.append("&");
                        sb.append(aVar2.f());
                        sb.append(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                    } else {
                        aVar.a(Long.valueOf(j));
                        sb.append("&");
                        sb.append(aVar2.f());
                        sb.append(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                        sb.append(this.B);
                    }
                    aVar.c("3");
                } else {
                    EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content);
                    aVar.a(aVar2.f());
                    aVar.b(aVar2.a());
                    aVar.a(editText.getText().toString().trim());
                    String strTrim2 = editText.getText().toString().trim();
                    sb.append("&");
                    sb.append(aVar2.f());
                    sb.append(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                    sb.append(strTrim2);
                }
                aVar.a(aVar2.c());
                aVar.a(aVar2.b() == 1);
                arrayList.add(aVar);
            }
        }
        if (jSONArray != null) {
            if (this.f.size() > 1) {
                JSONArray jSONArray2 = new JSONArray();
                int size = this.f.contains(this.a) ? this.f.size() - 1 : this.f.size();
                i = 0;
                while (i < size) {
                    jSONArray2.put(jSONArray.optJSONObject(i));
                    i++;
                }
                ai.a aVar3 = new ai.a();
                aVar3.a("uploadFile");
                aVar3.a(jSONArray2);
                arrayList.add(aVar3);
            } else {
                i = 0;
            }
            for (ScrollGridView scrollGridView : this.g.keySet()) {
                x.a aVar4 = (x.a) scrollGridView.getTag();
                if (this.g.get(scrollGridView) != null) {
                    JSONArray jSONArray3 = new JSONArray();
                    int size2 = this.g.get(scrollGridView).contains(this.a) ? this.g.get(scrollGridView).size() - 1 : this.g.get(scrollGridView).size();
                    int i3 = 0;
                    while (i3 < size2) {
                        jSONArray3.put(jSONArray.optJSONObject(i));
                        i3++;
                        i++;
                    }
                    ai.a aVar5 = new ai.a();
                    aVar5.a(aVar4.f());
                    aVar5.b(aVar4.a());
                    aVar5.a(jSONArray3);
                    aVar5.a(aVar4.c());
                    aVar5.a(aVar4.b() == 1);
                    arrayList.add(aVar5);
                }
            }
        }
        aiVar.a(arrayList);
        if (e()) {
            x xVar2 = this.d;
            if (xVar2 != null) {
                aiVar.a(xVar2.i());
            }
            akVar.a(com.qiyukf.unicorn.c.h().d(this.e));
            akVar.a(aiVar);
            akVar.a(sb.toString());
            akVar.a();
            akVar.b("android");
            aiVar.a(akVar);
            com.qiyukf.unicorn.k.c.a(akVar, this.e).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.ui.worksheet.d.3
                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
                }

                public AnonymousClass3() {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onFailed(int i4) {
                    d.this.i();
                    t.a(R.string.ysf_request_fail_str);
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onException(Throwable th) {
                    d.this.i();
                    t.a(R.string.ysf_request_fail_str);
                }
            });
            return;
        }
        if (f()) {
            x xVar3 = this.d;
            if (xVar3 != null) {
                aiVar.a(xVar3.i());
            }
            akVar.a(com.qiyukf.unicorn.c.h().d(this.e));
            akVar.a(aiVar);
            akVar.a(sb.toString());
            akVar.a();
            akVar.b("android");
            aiVar.a(akVar);
            com.qiyukf.unicorn.k.c.a(akVar, this.e).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.ui.worksheet.d.4
                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
                }

                public AnonymousClass4() {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onFailed(int i4) {
                    d.this.i();
                    t.a(R.string.ysf_request_fail_str);
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onException(Throwable th) {
                    d.this.i();
                    t.a(R.string.ysf_request_fail_str);
                }
            });
            return;
        }
        aiVar.a(arrayList);
        bVar.a(aiVar.c());
        bVar.b(sb.toString());
        aiVar.a(bVar);
        IMMessage iMMessageCreateCustomMessage = MessageBuilder.createCustomMessage(this.e, SessionTypeEnum.Ysf, aiVar);
        iMMessageCreateCustomMessage.setContent(this.j.getString(R.string.ysf_info_already_submit));
        com.qiyukf.unicorn.k.c.c(iMMessageCreateCustomMessage);
        a aVar6 = this.b;
        if (aVar6 != null) {
            aVar6.onSubmitDone(this.j.getString(R.string.ysf_info_already_submit));
        } else {
            a aVar7 = this.c;
            if (aVar7 != null) {
                aVar7.onSubmitDone(this.j.getString(R.string.ysf_info_already_submit));
            }
        }
        i();
        cancel();
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$3 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass3 implements RequestCallback<Void> {
        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
        }

        public AnonymousClass3() {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i4) {
            d.this.i();
            t.a(R.string.ysf_request_fail_str);
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
            d.this.i();
            t.a(R.string.ysf_request_fail_str);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.worksheet.d$4 */
    /* JADX INFO: compiled from: WorkSheetDialog.java */
    public class AnonymousClass4 implements RequestCallback<Void> {
        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final /* bridge */ /* synthetic */ void onSuccess(Void r1) {
        }

        public AnonymousClass4() {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onFailed(int i4) {
            d.this.i();
            t.a(R.string.ysf_request_fail_str);
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public final void onException(Throwable th) {
            d.this.i();
            t.a(R.string.ysf_request_fail_str);
        }
    }

    public static /* synthetic */ void a(d dVar, CustomNotification customNotification) {
        com.qiyukf.unicorn.h.a.b attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(customNotification.getContent());
        if (attachStr != null) {
            int cmdId = attachStr.getCmdId();
            if (cmdId == 11036) {
                x xVarA = ((com.qiyukf.unicorn.h.a.a.a.w) attachStr).a();
                dVar.d = xVarA;
                if (xVarA == null) {
                    t.a(R.string.ysf_get_work_sheet_fail_data);
                    dVar.cancel();
                    return;
                } else {
                    dVar.z.addAll(xVarA.h());
                    com.qiyukf.unicorn.c.h().a(dVar.A, dVar.d);
                    dVar.g();
                    return;
                }
            }
            if (cmdId != 11045) {
                return;
            }
            az azVar = (az) attachStr;
            if ((!TextUtils.isEmpty(attachStr.getContent()) && attachStr.getContent().contains("失败")) || attachStr.getContent().contains("error")) {
                dVar.i();
                t.b(attachStr.getContent());
                return;
            }
            IMMessage iMMessageCreateCustomMessage = MessageBuilder.createCustomMessage(dVar.e, SessionTypeEnum.Ysf, attachStr);
            iMMessageCreateCustomMessage.setStatus(MsgStatusEnum.success);
            iMMessageCreateCustomMessage.setDirect(MsgDirectionEnum.Out);
            ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageCreateCustomMessage, true);
            a aVar = dVar.b;
            if (aVar != null) {
                aVar.onSubmitDone(TextUtils.isEmpty(azVar.getContent()) ? dVar.j.getString(R.string.ysf_info_already_submit) : azVar.getContent());
            } else {
                a aVar2 = dVar.c;
                if (aVar2 != null) {
                    aVar2.onSubmitDone(TextUtils.isEmpty(azVar.getContent()) ? dVar.j.getString(R.string.ysf_info_already_submit) : azVar.getContent());
                }
            }
            dVar.i();
            dVar.cancel();
        }
    }

    public static /* synthetic */ void b(d dVar) {
        if (dVar.e()) {
            dVar.h();
            return;
        }
        if (dVar.f()) {
            if (com.qiyukf.unicorn.c.h().e(dVar.e) != null) {
                dVar.h();
                return;
            }
        } else {
            x xVar = dVar.d;
            if (xVar != null && !xVar.j()) {
                AbsUnicornLog.i("WorkSheetDialog", "isHumanWorkSheet= " + dVar.d.j());
                com.qiyukf.unicorn.f.p pVarC = com.qiyukf.unicorn.c.h().c(dVar.e);
                if (dVar.d.d().equals(String.valueOf(com.qiyukf.unicorn.c.h().d(dVar.e))) || (pVarC != null && pVarC.f && String.valueOf(pVarC.g).equals(dVar.d.d()))) {
                    dVar.h();
                    return;
                }
            }
        }
        Context context = dVar.j;
        if (context != null) {
            t.a(context.getString(R.string.ysf_work_sheet_session_change));
        }
    }
}

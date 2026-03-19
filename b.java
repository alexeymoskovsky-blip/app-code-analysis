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
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.n.m;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.f.k;
import com.qiyukf.unicorn.h.a.d.aj;
import com.qiyukf.unicorn.h.a.f.ah;
import com.qiyukf.unicorn.mediaselect.internal.entity.Item;
import com.qiyukf.unicorn.mediaselect.internal.model.SelectedItemCollection;
import com.qiyukf.unicorn.mediaselect.internal.ui.activity.BasePreviewActivity;
import com.qiyukf.unicorn.mediaselect.internal.utils.PathUtils;
import com.qiyukf.unicorn.n.b.e;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.ui.worksheet.a;
import com.qiyukf.unicorn.ui.worksheet.d;
import com.qiyukf.unicorn.widget.MultipleStatusLayout;
import com.qiyukf.unicorn.widget.ScrollGridView;
import com.qiyukf.unicorn.widget.dialog.ProgressDialog;
import com.qiyukf.unicorn.widget.timepicker.TimeSelector;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: WorkSheetAppendFileDialog.java */
/* JADX INFO: loaded from: classes6.dex */
public final class b extends Dialog implements a.InterfaceC0207a {
    private FrameLayout A;
    private ScrollView B;
    private Context a;
    private Button b;
    private com.qiyukf.unicorn.ui.a.b c;
    private ScrollGridView d;
    private LinearLayout e;
    private ProgressDialog f;
    private LinearLayout g;
    private TextView h;
    private TextView i;
    private TextView j;
    private TextView k;
    private View l;
    private MultipleStatusLayout m;
    private final long n;
    private final long o;
    private final String p;
    private long q;
    private Map<ScrollGridView, ArrayList<Item>> r;
    private ArrayList<Item> s;
    private final Item t;
    private d.a u;
    private d.a v;
    private List<ah.a> w;
    private List<aj.d> x;
    private com.qiyukf.unicorn.ui.a.b y;
    private ImageView z;

    public static b a(Context context, List<aj.d> list, long j, long j2, String str, d.a aVar, d.a aVar2) {
        b bVar = new b(context, list, j, j2, str, aVar, aVar2);
        bVar.show();
        return bVar;
    }

    private b(@NonNull Context context, List<aj.d> list, long j, long j2, String str, d.a aVar, d.a aVar2) {
        super(context, R.style.ysf_popup_dialog_style);
        this.q = 0L;
        this.r = new LinkedHashMap();
        this.s = new ArrayList<>();
        this.t = Item.createEmpteItem();
        this.w = new ArrayList();
        this.a = context;
        this.u = aVar;
        this.v = aVar2;
        this.x = list;
        this.n = j;
        this.o = j2;
        this.p = str;
        setContentView(LayoutInflater.from(getContext()).inflate(R.layout.ysf_dialog_work_sheet, (ViewGroup) null));
        this.k = (TextView) findViewById(R.id.ysf_work_sheet_info);
        this.d = (ScrollGridView) findViewById(R.id.ysf_gv_work_sheet_annex_list);
        this.e = (LinearLayout) findViewById(R.id.ysf_work_sheet_customize_field_layout);
        this.m = (MultipleStatusLayout) findViewById(R.id.ysf_msl_work_sheet_parent);
        this.j = (TextView) findViewById(R.id.ysf_tv_work_sheet_tip);
        this.i = (TextView) findViewById(R.id.ysf_work_sheet_info);
        this.b = (Button) findViewById(R.id.ysf_work_sheet_done);
        this.h = (TextView) findViewById(R.id.ysf_tv_work_sheet_annex_label);
        this.A = (FrameLayout) findViewById(R.id.ysf_work_sheet_info_fl);
        this.g = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_item_parent);
        this.i.setSingleLine(false);
        this.B = (ScrollView) findViewById(R.id.ysf_sl_work_sheet_list_parent);
        this.i.setText(this.a.getString(R.string.ysf_follow_append_file_info));
        this.j.setVisibility(8);
        this.l = findViewById(R.id.ysf_message_include_divider);
        this.z = (ImageView) findViewById(R.id.ysf_work_sheet_close);
        findViewById(R.id.ysf_work_sheet_close).setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.b.7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                b.this.cancel();
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.b.8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (!o.a(b.this.a)) {
                    t.a(R.string.ysf_network_unable);
                    return;
                }
                b bVar = b.this;
                b.a(bVar, bVar.a.getString(R.string.ysf_submit_ing_str));
                if (b.this.s.size() == 1 && b.this.r.size() <= 0) {
                    b.this.a((JSONArray) null);
                    return;
                }
                ArrayList arrayList = new ArrayList(6);
                ArrayList arrayList2 = new ArrayList(6);
                for (Item item : b.this.s) {
                    if (!Item.EMPTY_TYPE_TAG.equals(item.mimeType)) {
                        if (!com.qiyukf.unicorn.c.f().isUseSAF) {
                            arrayList.add(PathUtils.getPath(b.this.a, item.getContentUri()));
                        } else {
                            arrayList.add(item.getContentUri().toString());
                        }
                        arrayList2.add(item.getContentUri());
                    }
                }
                Iterator it = b.this.r.values().iterator();
                while (it.hasNext()) {
                    for (Item item2 : (ArrayList) it.next()) {
                        if (!Item.EMPTY_TYPE_TAG.equals(item2.mimeType)) {
                            arrayList.add(PathUtils.getPath(b.this.a, item2.getContentUri()));
                            arrayList2.add(item2.getContentUri());
                        }
                    }
                }
                if (arrayList.size() == 0 || TextUtils.isEmpty((CharSequence) arrayList.get(0))) {
                    b.this.a((JSONArray) null);
                    return;
                }
                JSONArray jSONArray = new JSONArray();
                if (o.a() || com.qiyukf.unicorn.c.f().isUseSAF) {
                    b.a(b.this, arrayList, arrayList2, 0, jSONArray);
                } else {
                    b.a(b.this, arrayList, 0, jSONArray);
                }
            }
        });
        a();
    }

    private void a() {
        View.OnClickListener onClickListener;
        int i = 0;
        int i2 = 1;
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.k.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.h.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.j.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            this.b.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()));
            this.b.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().e()));
            this.l.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            Drawable drawable = this.z.getDrawable();
            int color = Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b());
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            drawable.setColorFilter(color, mode);
            if (w.a()) {
                this.A.getBackground().setColorFilter(this.a.getResources().getColor(R.color.ysf_dark_module), mode);
                this.B.getBackground().setColorFilter(this.a.getResources().getColor(R.color.ysf_dark_module), mode);
            }
            this.b.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().e()));
        } else {
            this.b.setBackgroundResource(R.drawable.ysf_evaluation_dialog_btn_submit_bg_selector);
        }
        com.qiyukf.unicorn.m.a.a().a(this.b);
        String string = this.a.getString(R.string.ysf_message_reference);
        this.b.setText(string.trim().substring(0, string.length() - 1).trim());
        ArrayList<Item> arrayList = this.s;
        if (arrayList != null && arrayList.size() == 0) {
            this.s.add(this.t);
        }
        com.qiyukf.unicorn.ui.a.b bVar = new com.qiyukf.unicorn.ui.a.b((Activity) this.a, this.s, new k() { // from class: com.qiyukf.unicorn.ui.worksheet.b.1
            @Override // com.qiyukf.unicorn.f.k
            public /* synthetic */ void a(com.qiyukf.unicorn.ui.a.b bVar2) {
                k.CC.$default$a(this, bVar2);
            }

            @Override // com.qiyukf.unicorn.f.k
            public final void removePhoto(int i3) {
                b.this.s.remove(i3);
                if (!Item.EMPTY_TYPE_TAG.equals(((Item) b.this.s.get(b.this.s.size() - 1)).mimeType)) {
                    b.this.s.add(b.this.t);
                }
                b.this.c.notifyDataSetChanged();
            }
        }, this.u);
        this.c = bVar;
        this.d.setAdapter((ListAdapter) bVar);
        for (final aj.d dVar : this.x) {
            if (dVar.b().equals("uploadFile")) {
                String string2 = TextUtils.isEmpty(dVar.c()) ? this.a.getString(R.string.ysf_annex_str) : dVar.c();
                this.d.setVisibility(i);
                this.h.setVisibility(i);
                this.h.setText(string2);
            } else if (dVar.e() == 7) {
                View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.ysf_work_sheet_customize_field, (ViewGroup) null);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tv_work_sheet_annex_label_other);
                final ScrollGridView scrollGridView = (ScrollGridView) viewInflate.findViewById(R.id.ysf_gv_work_sheet_annex_list_other);
                String string3 = TextUtils.isEmpty(dVar.c()) ? this.a.getString(R.string.ysf_annex_str) : dVar.c();
                scrollGridView.setVisibility(i);
                scrollGridView.setTag(dVar);
                textView.setVisibility(i);
                textView.setText(string3);
                scrollGridView.setTag(dVar);
                final ArrayList<Item> arrayList2 = new ArrayList<>();
                arrayList2.add(this.t);
                scrollGridView.setAdapter((ListAdapter) new com.qiyukf.unicorn.ui.a.b((Activity) this.a, arrayList2, new k() { // from class: com.qiyukf.unicorn.ui.worksheet.b.3
                    @Override // com.qiyukf.unicorn.f.k
                    public final void a(com.qiyukf.unicorn.ui.a.b bVar2) {
                        b.this.y = bVar2;
                    }

                    @Override // com.qiyukf.unicorn.f.k
                    public final void removePhoto(int i3) {
                        arrayList2.remove(i3);
                        if (!Item.EMPTY_TYPE_TAG.equals(((Item) arrayList2.get(r2.size() - 1)).mimeType)) {
                            arrayList2.add(b.this.t);
                        }
                        ((com.qiyukf.unicorn.ui.a.b) scrollGridView.getAdapter()).notifyDataSetChanged();
                    }
                }, this.v));
                this.r.put(scrollGridView, arrayList2);
                this.e.addView(viewInflate);
            } else {
                String strC = dVar.c();
                int iE = dVar.e();
                if (iE == i2 || iE == 2) {
                    onClickListener = new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.b.5
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            a aVar = new a(b.this.a, dVar);
                            aVar.a(b.this);
                            aVar.show();
                        }
                    };
                } else {
                    onClickListener = iE != 3 ? null : new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.worksheet.b.6
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            TimeSelector timeSelector = new TimeSelector(b.this.a, new TimeSelector.ResultHandler() { // from class: com.qiyukf.unicorn.ui.worksheet.b.6.1
                                @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                                public final void handle(String str, Date date) {
                                    b.this.q = date.getTime();
                                    AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                    b.this.b(String.valueOf(dVar.d()), str);
                                }
                            }, TimeSelector.START_TIME, TimeSelector.END_TIME, true, true, true, true, true, "yyyy-MM-dd HH:mm", dVar.c());
                            timeSelector.setCurrentData(new Date());
                            timeSelector.show();
                        }
                    };
                }
                ViewGroup viewGroup = (ViewGroup) View.inflate(this.a, R.layout.ysf_item_work_sheet_dialog, null);
                viewGroup.setTag(dVar);
                TextView textView2 = (TextView) viewGroup.findViewById(R.id.ysf_tv_item_work_sheet_label);
                ImageView imageView = (ImageView) viewGroup.findViewById(R.id.ysf_iv_work_sheet_info_arrow);
                RelativeLayout relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.ysf_rl_item_work_sheet_input);
                EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content);
                LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.ysf_ll_work_sheet_work_item_multiline_parent);
                EditText editText2 = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_multiline);
                final TextView textView3 = (TextView) viewGroup.findViewById(R.id.ysf_tv_work_sheet_item_multiline_count);
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    relativeLayout.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 5));
                    linearLayout.setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().s().a(), "#00000000", 5));
                    textView2.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
                    editText.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
                    editText.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
                    imageView.getDrawable().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()), PorterDuff.Mode.SRC_IN);
                    editText2.setHintTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
                    editText2.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
                    textView3.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()));
                }
                textView2.setText(strC);
                if (onClickListener != null) {
                    i2 = 1;
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000000000)});
                    imageView.setVisibility(0);
                    relativeLayout.setOnClickListener(onClickListener);
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(false);
                    editText.setHint(R.string.ysf_please_choose_str);
                } else {
                    i2 = 1;
                    if ("2".equals(dVar.f())) {
                        linearLayout.setVisibility(0);
                        relativeLayout.setVisibility(8);
                        editText2.setHint(R.string.ysf_please_input_str);
                        textView3.setText("0/500");
                        editText2.addTextChangedListener(new TextWatcher() { // from class: com.qiyukf.unicorn.ui.worksheet.b.4
                            @Override // android.text.TextWatcher
                            public final void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                            }

                            @Override // android.text.TextWatcher
                            public final void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                            }

                            @Override // android.text.TextWatcher
                            public final void afterTextChanged(Editable editable) {
                                textView3.setText(editable.length() + "/500");
                            }
                        });
                    } else {
                        imageView.setVisibility(8);
                        editText.setHint(R.string.ysf_please_input_str);
                        if ("1".equals(dVar.f())) {
                            editText.setInputType(2);
                        }
                    }
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                layoutParams.bottomMargin = p.a(10.0f);
                this.g.addView(viewGroup, layoutParams);
                i = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(JSONArray jSONArray) {
        int i;
        ah ahVar = new ah();
        ahVar.a(this.n);
        ahVar.b(this.o);
        for (int i2 = 0; i2 < this.g.getChildCount(); i2++) {
            ViewGroup viewGroup = (ViewGroup) this.g.getChildAt(i2);
            if (viewGroup.getTag() != null) {
                ah.a aVar = new ah.a();
                aj.d dVar = (aj.d) viewGroup.getTag();
                if (dVar.e() == 0) {
                    Editable text = "2".equals(dVar.f()) ? ((EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_multiline)).getText() : ((EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content)).getText();
                    aVar.a(dVar.d());
                    aVar.b(text.toString().trim());
                } else if (dVar.e() == 3) {
                    aVar.a(dVar.d());
                    long j = this.q;
                    if (j == 0) {
                        aVar.b("");
                    } else {
                        aVar.b(String.valueOf(j));
                    }
                } else {
                    EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content);
                    aVar.a(dVar.d());
                    aVar.b(editText.getText().toString().trim());
                }
                this.w.add(aVar);
            }
        }
        if (jSONArray != null) {
            if (this.s.size() > 1) {
                JSONArray jSONArray2 = new JSONArray();
                int size = this.s.contains(this.t) ? this.s.size() - 1 : this.s.size();
                i = 0;
                while (i < size) {
                    jSONArray2.put(jSONArray.optJSONObject(i));
                    i++;
                }
                ah.a aVar2 = new ah.a();
                aVar2.a("uploadFile");
                aVar2.b(jSONArray2.toString());
                this.w.add(aVar2);
            } else {
                i = 0;
            }
            for (ScrollGridView scrollGridView : this.r.keySet()) {
                aj.d dVar2 = (aj.d) scrollGridView.getTag();
                if (this.r.get(scrollGridView) != null) {
                    JSONArray jSONArray3 = new JSONArray();
                    int size2 = this.r.get(scrollGridView).contains(this.t) ? this.r.get(scrollGridView).size() - 1 : this.r.get(scrollGridView).size();
                    int i3 = 0;
                    while (i3 < size2) {
                        jSONArray3.put(jSONArray.optJSONObject(i));
                        i3++;
                        i++;
                    }
                    ah.a aVar3 = new ah.a();
                    aVar3.a(dVar2.d());
                    aVar3.b(jSONArray3.toString());
                    this.w.add(aVar3);
                }
            }
        }
        ahVar.a(this.w);
        com.qiyukf.unicorn.k.c.a(ahVar, this.p).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.ui.worksheet.b.2
            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i4) {
                b.this.b();
                t.a(R.string.ysf_request_fail_str);
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onException(Throwable th) {
                b.this.b();
                t.a(R.string.ysf_request_fail_str);
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final /* synthetic */ void onSuccess(Void r1) {
                b.this.b();
                b.this.cancel();
            }
        });
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

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        ProgressDialog progressDialog = this.f;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.f.dismiss();
    }

    @Override // com.qiyukf.unicorn.ui.worksheet.a.InterfaceC0207a
    public final void a(String str, String str2) {
        b(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, String str2) {
        for (int i = 0; i < this.g.getChildCount(); i++) {
            ViewGroup viewGroup = (ViewGroup) this.g.getChildAt(i);
            if (viewGroup.getTag() != null) {
                aj.d dVar = (aj.d) viewGroup.getTag();
                if (String.valueOf(dVar.d()).equals(str) && dVar.e() != 0) {
                    EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_work_sheet_item_content);
                    if (this.a.getString(R.string.ysf_unselect_str).equals(str2) || TextUtils.isEmpty(str2)) {
                        editText.setHint(R.string.ysf_please_choose_str);
                        str2 = null;
                    }
                    dVar.a(str2);
                    editText.setText(str2);
                    return;
                }
            }
        }
    }

    public final void a(Intent intent, int i) {
        ArrayList parcelableArrayList;
        Bundle bundleExtra = intent.getBundleExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE);
        if (bundleExtra == null || (parcelableArrayList = bundleExtra.getParcelableArrayList(SelectedItemCollection.STATE_SELECTION)) == null) {
            return;
        }
        if (i == 1) {
            this.s.clear();
            this.s.add(this.t);
            Iterator it = parcelableArrayList.iterator();
            while (it.hasNext()) {
                a((Item) it.next(), i);
            }
            com.qiyukf.unicorn.ui.a.b bVar = this.c;
            if (bVar != null) {
                bVar.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i == 2) {
            for (ScrollGridView scrollGridView : this.r.keySet()) {
                if (scrollGridView.getAdapter() == this.y && this.r.get(scrollGridView) != null) {
                    this.r.get(scrollGridView).clear();
                    this.r.get(scrollGridView).add(this.t);
                }
            }
            Iterator it2 = parcelableArrayList.iterator();
            while (it2.hasNext()) {
                a((Item) it2.next(), i);
            }
            com.qiyukf.unicorn.ui.a.b bVar2 = this.y;
            if (bVar2 != null) {
                bVar2.notifyDataSetChanged();
            }
        }
    }

    public final void b(Intent intent, int i) {
        com.qiyukf.unicorn.ui.a.b bVar;
        if (com.qiyukf.unicorn.c.f().isUseSAF) {
            Uri data = intent.getData();
            String strA = w.a(this.a, data);
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
            com.qiyukf.unicorn.ui.a.b bVar2 = this.c;
            if (bVar2 != null) {
                bVar2.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i != 2 || (bVar = this.y) == null) {
            return;
        }
        bVar.notifyDataSetChanged();
    }

    private void a(Item item, int i) {
        if (i == 1) {
            if (this.s.size() <= 4) {
                ArrayList<Item> arrayList = this.s;
                arrayList.add(arrayList.size() - 1, item);
                return;
            } else {
                if (this.s.size() == 5) {
                    ArrayList<Item> arrayList2 = this.s;
                    arrayList2.remove(arrayList2.size() - 1);
                    this.s.add(item);
                    return;
                }
                return;
            }
        }
        if (i == 2) {
            for (ScrollGridView scrollGridView : this.r.keySet()) {
                if (scrollGridView.getAdapter() == this.y && this.r.get(scrollGridView) != null) {
                    if (this.r.get(scrollGridView).size() <= 4) {
                        this.r.get(scrollGridView).add(this.r.get(scrollGridView).size() - 1, item);
                    } else if (this.r.get(scrollGridView).size() == 5) {
                        this.r.get(scrollGridView).remove(this.r.get(scrollGridView).size() - 1);
                        this.r.get(scrollGridView).add(item);
                    }
                }
            }
        }
    }

    public static /* synthetic */ void a(b bVar, String str) {
        if (bVar.f == null) {
            ProgressDialog progressDialog = new ProgressDialog(bVar.a);
            bVar.f = progressDialog;
            progressDialog.setCancelable(false);
            bVar.f.setMessage(str);
        }
        bVar.f.show();
    }

    public static /* synthetic */ void a(b bVar, final List list, final List list2, final int i, final JSONArray jSONArray) {
        String str;
        if (list2.size() == i) {
            bVar.a(jSONArray);
            return;
        }
        if (list2.size() == 0 || list2.get(i) == null) {
            return;
        }
        String strA = m.a(com.qiyukf.nimlib.c.d(), (Uri) list2.get(i));
        if (com.qiyukf.unicorn.c.f().isUseSAF) {
            String strA2 = w.a(bVar.a, Uri.parse((String) list.get(i)));
            if (TextUtils.isEmpty(strA2)) {
                strA2 = "png";
            }
            str = strA + "." + strA2;
        } else {
            str = strA + "." + e.a((String) list.get(i));
        }
        String strA3 = com.qiyukf.unicorn.n.e.d.a(str, com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (com.qiyukf.nimlib.net.a.c.a.a(com.qiyukf.nimlib.c.d(), (Uri) list2.get(i), strA3)) {
            File file = new File(strA3);
            if (!com.qiyukf.unicorn.c.f().isUseSAF || file.length() <= 52428800) {
                FileAttachment fileAttachment = new FileAttachment();
                fileAttachment.setPath(file.getPath());
                fileAttachment.setSize(file.length());
                fileAttachment.setDisplayName(file.getName());
                ((MsgService) NIMClient.getService(MsgService.class)).sendFile(fileAttachment).setCallback(new RequestCallbackWrapper<FileAttachment>() { // from class: com.qiyukf.unicorn.ui.worksheet.b.9
                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public final /* synthetic */ void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
                        FileAttachment fileAttachment3 = fileAttachment2;
                        if (i2 == 200) {
                            JSONObject jSONObject = new JSONObject();
                            j.a(jSONObject, "name", fileAttachment3.getDisplayName());
                            j.a(jSONObject, "size", fileAttachment3.getSize());
                            j.a(jSONObject, "url", fileAttachment3.getUrl());
                            j.a(jSONArray, jSONObject);
                            b.a(b.this, list, list2, i + 1, jSONArray);
                            return;
                        }
                        t.a(R.string.ysf_bot_form_upload_image_failed);
                        b.this.b();
                    }
                });
                return;
            }
            return;
        }
        t.a(R.string.ysf_video_exception);
        bVar.b();
    }

    public static /* synthetic */ void a(b bVar, final List list, final int i, final JSONArray jSONArray) {
        if (list.size() == i) {
            bVar.a(jSONArray);
            return;
        }
        String strA = com.qiyukf.unicorn.n.e.d.a(m.b((String) list.get(i)) + "." + e.a((String) list.get(i)), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (com.qiyukf.nimlib.net.a.c.a.a((String) list.get(i), strA) != -1) {
            File file = new File(strA);
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setPath(file.getPath());
            fileAttachment.setSize(file.length());
            fileAttachment.setDisplayName(file.getName());
            ((MsgService) NIMClient.getService(MsgService.class)).sendFile(fileAttachment).setCallback(new RequestCallbackWrapper<FileAttachment>() { // from class: com.qiyukf.unicorn.ui.worksheet.b.10
                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public final /* synthetic */ void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
                    FileAttachment fileAttachment3 = fileAttachment2;
                    if (i2 == 200) {
                        JSONObject jSONObject = new JSONObject();
                        j.a(jSONObject, "name", fileAttachment3.getDisplayName());
                        j.a(jSONObject, "size", fileAttachment3.getSize());
                        j.a(jSONObject, "url", fileAttachment3.getUrl());
                        j.a(jSONArray, jSONObject);
                        b.a(b.this, list, i + 1, jSONArray);
                        return;
                    }
                    t.a(R.string.ysf_bot_form_upload_image_failed);
                    b.this.b();
                }
            });
            return;
        }
        t.a(R.string.ysf_media_exception);
        bVar.b();
    }
}

package com.qiyukf.unicorn.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.n.m;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.uikit.common.activity.BaseFragmentActivity;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.h.a.d.aj;
import com.qiyukf.unicorn.h.a.d.ay;
import com.qiyukf.unicorn.h.a.d.ba;
import com.qiyukf.unicorn.h.a.f.ab;
import com.qiyukf.unicorn.h.a.f.al;
import com.qiyukf.unicorn.k.c;
import com.qiyukf.unicorn.m.a;
import com.qiyukf.unicorn.mediaselect.Matisse;
import com.qiyukf.unicorn.mediaselect.MimeType;
import com.qiyukf.unicorn.mediaselect.internal.entity.Item;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.ui.worksheet.b;
import com.qiyukf.unicorn.ui.worksheet.d;
import com.qiyukf.unicorn.widget.dialog.ProgressDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes6.dex */
public class WorkSheetDetailActivity extends BaseFragmentActivity {
    private List<aj.d> appendFieldList;
    private b appendFileDialog;
    private aj attachment;
    private String exchange;
    private ProgressDialog progressDialog;
    private long workSheetId;
    private Button ysfBtnWorkSheetAppendFields;
    private Button ysfBtnWorkSheetUrge;
    private LinearLayout ysfLlWorkSheetAnnexItemParent;
    private LinearLayout ysfLlWorkSheetAnnexParent;
    private LinearLayout ysfLlWorkSheetCustomFieldParent;
    private LinearLayout ysfLlWorkSheetDetailParent;
    private LinearLayout ysfLlWorkSheetRecordParent;
    private TextView ysfRecordTitleInfo;
    private ScrollView ysfScrollView;
    private TextView ysfTitleInfo;
    private TextView ysfTvWorkSheetAnnexEmpty;
    private TextView ysfTvWorkSheetAnnexTitle;
    private TextView ysfTvWorkSheetDetailId;
    private View ysfTvWorkSheetDetailIdDivide;
    private TextView ysfTvWorkSheetDetailTag;
    private TextView ysfTvWorkSheetDetailType;
    private TextView ysfTvWorkSheetDetailTypeTag;
    private View ysfTvWorkSheetDetailTypeTagDivide;
    private boolean isOpenUrge = false;
    private long logId = -1;
    private int photoListNum = 0;
    private Observer<CustomNotification> customNotificationObserver = new Observer<CustomNotification>() { // from class: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.3
        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(CustomNotification customNotification) {
            com.qiyukf.unicorn.h.a.b attachStr;
            if (customNotification.getSessionType() == SessionTypeEnum.Ysf && (attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(customNotification.getContent())) != null) {
                if (attachStr instanceof aj) {
                    WorkSheetDetailActivity.this.setUI((aj) attachStr);
                } else if (attachStr instanceof ba) {
                    WorkSheetDetailActivity.this.onUrgeNotify((ba) attachStr);
                } else if (attachStr instanceof ay) {
                    WorkSheetDetailActivity.this.onAppendFileResult((ay) attachStr);
                }
            }
        }
    };

    public static void start(Context context, long j, boolean z, String str) {
        Intent intent = new Intent(context, (Class<?>) WorkSheetDetailActivity.class);
        intent.putExtra("WORK_SHEET_ID", j);
        intent.putExtra("IS_OPEN_URGE_TAG", z);
        intent.putExtra("BID_TAG", str);
        context.startActivity(intent);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ysf_activity_work_sheet_detail);
        parseIntent();
        initView();
        registerObserver(true);
        getWorkSheetData();
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        registerObserver(false);
    }

    private void parseIntent() {
        this.workSheetId = getIntent().getLongExtra("WORK_SHEET_ID", 0L);
        this.isOpenUrge = getIntent().getBooleanExtra("IS_OPEN_URGE_TAG", false);
        this.exchange = getIntent().getStringExtra("BID_TAG");
    }

    private void initView() {
        this.ysfTitleInfo = (TextView) findViewById(R.id.ysf_tv_work_sheet_detail_info_title);
        this.ysfRecordTitleInfo = (TextView) findViewById(R.id.ysf_tv_work_sheet_detail_record_title);
        this.ysfTvWorkSheetDetailId = (TextView) findViewById(R.id.ysf_tv_work_sheet_detail_id);
        this.ysfTvWorkSheetDetailType = (TextView) findViewById(R.id.ysf_tv_work_sheet_detail_type);
        this.ysfTvWorkSheetDetailTypeTag = (TextView) findViewById(R.id.ysf_tv_work_sheet_detail_type_tag);
        this.ysfTvWorkSheetDetailIdDivide = findViewById(R.id.ysf_view_work_sheet_detail_id_divide);
        this.ysfLlWorkSheetCustomFieldParent = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_custom_field_parent);
        this.ysfTvWorkSheetAnnexTitle = (TextView) findViewById(R.id.ysf_ll_work_sheet_annex_item_title);
        this.ysfLlWorkSheetAnnexItemParent = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_annex_item_parent);
        this.ysfLlWorkSheetRecordParent = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_record_parent);
        this.ysfTvWorkSheetAnnexEmpty = (TextView) findViewById(R.id.ysf_tv_work_sheet_annex_empty);
        this.ysfBtnWorkSheetUrge = (Button) findViewById(R.id.ysf_btn_work_sheet_urge);
        this.ysfBtnWorkSheetAppendFields = (Button) findViewById(R.id.ysf_btn_work_sheet_append_fields);
        this.ysfLlWorkSheetAnnexParent = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_annex_parent);
        this.ysfTvWorkSheetDetailTag = (TextView) findViewById(R.id.ysf_tv_work_sheet_detail_id_tag);
        this.ysfTvWorkSheetDetailTypeTagDivide = findViewById(R.id.ysf_tv_work_sheet_detail_type_divide);
        this.ysfScrollView = (ScrollView) findViewById(R.id.ysf_sv_work_sheet_detail_parent);
        this.ysfLlWorkSheetDetailParent = (LinearLayout) findViewById(R.id.ysf_sv_work_sheet_detail_parent_ll);
        if (a.a().c()) {
            this.ysfTitleInfo.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.ysfTvWorkSheetDetailTag.setTextColor(Color.parseColor(a.a().b().s().f()));
            this.ysfTvWorkSheetAnnexTitle.setTextColor(Color.parseColor(a.a().b().s().f()));
            this.ysfTvWorkSheetDetailTypeTag.setTextColor(Color.parseColor(a.a().b().s().f()));
            this.ysfTvWorkSheetDetailId.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.ysfTvWorkSheetDetailType.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.ysfRecordTitleInfo.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.ysfTvWorkSheetDetailIdDivide.setBackgroundColor(Color.parseColor(a.a().b().s().a()));
            this.ysfTvWorkSheetDetailTypeTagDivide.setBackgroundColor(Color.parseColor(a.a().b().s().a()));
            this.ysfBtnWorkSheetUrge.setTextColor(Color.parseColor(a.a().b().s().d()));
            this.ysfBtnWorkSheetAppendFields.setTextColor(Color.parseColor(a.a().b().s().d()));
            Drawable background = this.ysfBtnWorkSheetUrge.getBackground();
            int color = Color.parseColor(a.a().e());
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            background.setColorFilter(color, mode);
            this.ysfBtnWorkSheetAppendFields.getBackground().setColorFilter(Color.parseColor(a.a().e()), mode);
            if (w.a()) {
                this.ysfScrollView.getBackground().setColorFilter(getResources().getColor(R.color.ysf_dark_module), mode);
                this.ysfLlWorkSheetDetailParent.getBackground().setColorFilter(getResources().getColor(R.color.ysf_dark_module), mode);
                this.ysfTitleInfo.setBackgroundColor(getResources().getColor(R.color.ysf_dark_module_line));
                this.ysfRecordTitleInfo.setBackgroundColor(getResources().getColor(R.color.ysf_dark_module_line));
            }
        }
        if (this.isOpenUrge) {
            this.ysfBtnWorkSheetUrge.setVisibility(0);
        } else {
            this.ysfBtnWorkSheetUrge.setVisibility(8);
        }
        this.ysfBtnWorkSheetUrge.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (WorkSheetDetailActivity.this.attachment == null) {
                    return;
                }
                WorkSheetDetailActivity workSheetDetailActivity = WorkSheetDetailActivity.this;
                workSheetDetailActivity.showProgressDialog(workSheetDetailActivity.getString(R.string.ysf_reminders_ing_str));
                al alVar = new al();
                alVar.a(WorkSheetDetailActivity.this.workSheetId);
                alVar.a("Android");
                c.a(alVar, WorkSheetDetailActivity.this.exchange);
            }
        });
        this.ysfBtnWorkSheetAppendFields.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (WorkSheetDetailActivity.this.appendFieldList == null || WorkSheetDetailActivity.this.appendFieldList.size() == 0 || WorkSheetDetailActivity.this.logId == -1) {
                    return;
                }
                WorkSheetDetailActivity workSheetDetailActivity = WorkSheetDetailActivity.this;
                workSheetDetailActivity.appendFileDialog = b.a(workSheetDetailActivity, workSheetDetailActivity.appendFieldList, WorkSheetDetailActivity.this.attachment.a().a(), WorkSheetDetailActivity.this.logId, WorkSheetDetailActivity.this.exchange, new d.a() { // from class: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.2.1
                    @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                    public void onSubmitDone(String str) {
                    }

                    @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                    public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i) {
                        WorkSheetDetailActivity.this.photoListNum = 1;
                        WatchPictureActivity.start(WorkSheetDetailActivity.this, arrayList, i, 20);
                    }

                    @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                    public void jumpSelectAnnexActivity(int i) {
                        WorkSheetDetailActivity.this.photoListNum = 1;
                        Matisse.startSelectMediaFile(WorkSheetDetailActivity.this, MimeType.ofAll(), i, 19);
                    }
                }, new d.a() { // from class: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.2.2
                    @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                    public void onSubmitDone(String str) {
                    }

                    @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                    public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i) {
                        WorkSheetDetailActivity.this.photoListNum = 2;
                        WatchPictureActivity.start(WorkSheetDetailActivity.this, arrayList, i, 20);
                    }

                    @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                    public void jumpSelectAnnexActivity(int i) {
                        WorkSheetDetailActivity.this.photoListNum = 2;
                        Matisse.startSelectMediaFile(WorkSheetDetailActivity.this, MimeType.ofAll(), i, 19);
                    }
                });
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        b bVar;
        super.onActivityResult(i, i2, intent);
        if (intent == null || i == 0 || (bVar = this.appendFileDialog) == null) {
            return;
        }
        if (i == 20) {
            bVar.a(intent, this.photoListNum);
        } else if (i == 19 || i == 17) {
            bVar.b(intent, this.photoListNum);
        }
        this.photoListNum = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppendFileResult(ay ayVar) {
        if (ayVar == null) {
            return;
        }
        if (ayVar.a() == 200) {
            getWorkSheetData();
            t.a(R.string.ysf_my_worksheet_submit_success);
        } else {
            t.a(ayVar.b());
        }
    }

    private void registerObserver(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.customNotificationObserver, z);
    }

    private void getWorkSheetData() {
        showProgressDialog(getString(R.string.ysf_loading_str));
        ab abVar = new ab();
        abVar.a(this.workSheetId);
        abVar.a("Android");
        abVar.b("2500");
        c.a(abVar, this.exchange);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUI(aj ajVar) {
        this.attachment = ajVar;
        if (ajVar == null || ajVar.a() == null) {
            dismissProgressDialog();
            t.b(R.string.ysf_get_worksheet_info_fail);
            return;
        }
        aj.e eVarA = ajVar.a();
        this.ysfTvWorkSheetDetailId.setText(String.valueOf(eVarA.a()));
        this.ysfTvWorkSheetDetailType.setText(eVarA.b());
        processWorkSheetCustomField(eVarA.e());
        processWorkSheetAnnex(eVarA.f());
        processWorkSheetRecord(eVarA.g());
        processUrgeBtnUI(eVarA.c(), eVarA.d());
        dismissProgressDialog();
    }

    private void processWorkSheetCustomField(List<aj.e.a> list) {
        this.ysfLlWorkSheetCustomFieldParent.removeAllViews();
        if (list == null) {
            return;
        }
        for (aj.e.a aVar : list) {
            if (aVar.c() == 7) {
                JSONArray jSONArrayB = j.b(aVar.b());
                ArrayList<aj.b> arrayList = new ArrayList();
                if (jSONArrayB != null) {
                    for (int i = 0; i < jSONArrayB.length(); i++) {
                        JSONObject jSONObjectD = j.d(jSONArrayB, i);
                        aj.b bVar = new aj.b();
                        bVar.a(j.e(jSONObjectD, "name"));
                        bVar.a(j.a(jSONObjectD, "size"));
                        bVar.b(j.e(jSONObjectD, "url"));
                        arrayList.add(bVar);
                    }
                }
                View viewInflate = LayoutInflater.from(this).inflate(R.layout.ysf_item_work_sheet_custom_field, (ViewGroup) null);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tv_work_sheet_detail_custom_field_tag);
                TextView textView2 = (TextView) viewInflate.findViewById(R.id.ysf_tv_work_sheet_detail_custom_field);
                View viewFindViewById = viewInflate.findViewById(R.id.ysf_work_sheet_detail_custom_divider);
                if (a.a().c()) {
                    textView.setTextColor(Color.parseColor(a.a().b().s().f()));
                    textView2.setTextColor(Color.parseColor(a.a().b().s().b()));
                    viewFindViewById.setBackgroundColor(Color.parseColor(a.a().b().s().a()));
                    if (w.a()) {
                        viewInflate.getBackground().setColorFilter(getResources().getColor(R.color.ysf_dark_module), PorterDuff.Mode.SRC_IN);
                    }
                }
                LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ysf_tv_work_sheet_detail_custom_annex_field);
                if (TextUtils.isEmpty(aVar.a())) {
                    textView.setText(R.string.ysf_please_input_str);
                } else {
                    textView.setText(aVar.a());
                }
                if (arrayList.size() > 0) {
                    textView2.setVisibility(8);
                    linearLayout.setVisibility(0);
                    for (final aj.b bVar2 : arrayList) {
                        View viewInflate2 = LayoutInflater.from(this).inflate(R.layout.ysf_item_work_sheet_annex, (ViewGroup) null);
                        TextView textView3 = (TextView) viewInflate2.findViewById(R.id.ysf_tv_work_sheet_annex);
                        textView3.setText(bVar2.a());
                        setTextColorAndDrawablesColor(textView3);
                        viewInflate2.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.4
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                if (TextUtils.isEmpty(bVar2.c())) {
                                    return;
                                }
                                String strA = com.qiyukf.nimlib.n.b.c.a(m.a(bVar2.c()), com.qiyukf.nimlib.n.b.b.TYPE_FILE);
                                FileDownloadActivity.start(WorkSheetDetailActivity.this, com.qiyukf.nimlib.ysf.a.a(WorkSheetDetailActivity.this.exchange, bVar2.a(), m.a(strA), bVar2.c(), strA, bVar2.b()));
                            }
                        });
                        linearLayout.addView(viewInflate2);
                    }
                } else {
                    textView2.setText("");
                }
                this.ysfLlWorkSheetCustomFieldParent.addView(viewInflate);
            } else {
                View viewInflate3 = LayoutInflater.from(this).inflate(R.layout.ysf_item_work_sheet_custom_field, (ViewGroup) null);
                TextView textView4 = (TextView) viewInflate3.findViewById(R.id.ysf_tv_work_sheet_detail_custom_field_tag);
                TextView textView5 = (TextView) viewInflate3.findViewById(R.id.ysf_tv_work_sheet_detail_custom_field);
                View viewFindViewById2 = viewInflate3.findViewById(R.id.ysf_work_sheet_detail_custom_divider);
                if (a.a().c()) {
                    textView4.setTextColor(Color.parseColor(a.a().b().s().f()));
                    textView5.setTextColor(Color.parseColor(a.a().b().s().b()));
                    viewFindViewById2.setBackgroundColor(Color.parseColor(a.a().b().s().a()));
                    if (w.a()) {
                        viewInflate3.getBackground().setColorFilter(getResources().getColor(R.color.ysf_dark_module), PorterDuff.Mode.SRC_IN);
                    }
                }
                if (TextUtils.isEmpty(aVar.a())) {
                    textView4.setText(R.string.ysf_please_input_str);
                } else {
                    textView4.setText(aVar.a());
                }
                if (aVar.c() == 3) {
                    try {
                        textView5.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(Long.parseLong(aVar.b()))));
                    } catch (Exception unused) {
                        textView5.setText(aVar.b());
                    }
                    this.ysfLlWorkSheetCustomFieldParent.addView(viewInflate3);
                } else {
                    textView5.setText(aVar.b());
                    this.ysfLlWorkSheetCustomFieldParent.addView(viewInflate3);
                }
            }
        }
    }

    private static void setTextColorAndDrawablesColor(TextView textView) {
        if (a.a().c()) {
            textView.setTextColor(Color.parseColor(a.a().b().s().g()));
            textView.getCompoundDrawables()[0].setColorFilter(Color.parseColor(a.a().b().s().g()), PorterDuff.Mode.SRC_IN);
        }
    }

    private void processWorkSheetAnnex(List<aj.b> list) {
        this.ysfLlWorkSheetAnnexItemParent.removeAllViews();
        if (list == null) {
            this.ysfLlWorkSheetAnnexParent.setVisibility(8);
            this.ysfLlWorkSheetAnnexItemParent.setVisibility(8);
            return;
        }
        if (list.size() == 0) {
            this.ysfLlWorkSheetAnnexParent.setVisibility(8);
            this.ysfLlWorkSheetAnnexItemParent.setVisibility(8);
            this.ysfTvWorkSheetAnnexEmpty.setVisibility(0);
        }
        for (final aj.b bVar : list) {
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.ysf_item_work_sheet_annex, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tv_work_sheet_annex);
            textView.setText(bVar.a());
            setTextColorAndDrawablesColor(textView);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.isEmpty(bVar.c())) {
                        return;
                    }
                    String strA = com.qiyukf.nimlib.n.b.c.a(m.a(bVar.c()), com.qiyukf.nimlib.n.b.b.TYPE_FILE);
                    FileDownloadActivity.start(WorkSheetDetailActivity.this, com.qiyukf.nimlib.ysf.a.a(WorkSheetDetailActivity.this.exchange, bVar.a(), m.a(strA), bVar.c(), strA, bVar.b()));
                }
            });
            this.ysfLlWorkSheetAnnexItemParent.addView(viewInflate);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02b4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void processWorkSheetRecord(java.util.List<com.qiyukf.unicorn.h.a.d.aj.c> r20) {
        /*
            Method dump skipped, instruction units count: 711
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.ui.activity.WorkSheetDetailActivity.processWorkSheetRecord(java.util.List):void");
    }

    private void setItemRecordTextColor(TextView textView, int i) {
        if (i == 105 || i == 15) {
            textView.setTextColor(getResources().getColor(R.color.ysf_blue_337EFF));
            return;
        }
        if (i == 25) {
            textView.setTextColor(getResources().getColor(R.color.ysf_green_61e19b));
            return;
        }
        if (i == 40 || i == 45) {
            textView.setTextColor(getResources().getColor(R.color.ysf_blue_337EFF));
            return;
        }
        if (i == 50) {
            textView.setTextColor(getResources().getColor(R.color.ysf_rec_f24957));
            return;
        }
        if (i == 110) {
            textView.setTextColor(getResources().getColor(R.color.ysf_yellow_ff9900));
        } else if (a.a().c()) {
            textView.setTextColor(Color.parseColor(a.a().b().s().f()));
        } else {
            textView.setTextColor(getResources().getColor(R.color.ysf_grey_999999));
        }
    }

    private void processUrgeBtnUI(int i, int i2) {
        if (i == 20) {
            this.ysfBtnWorkSheetUrge.setVisibility(8);
        }
        if (i2 == 1) {
            this.ysfBtnWorkSheetUrge.setText(R.string.ysf_already_reminders);
            this.ysfBtnWorkSheetUrge.setEnabled(false);
            this.ysfBtnWorkSheetUrge.getBackground().setColorFilter(com.qiyukf.unicorn.m.b.a(0.6f, Color.parseColor(a.a().e())), PorterDuff.Mode.SRC_IN);
        } else {
            this.ysfBtnWorkSheetUrge.setText(R.string.ysf_i_want_to_remind);
            this.ysfBtnWorkSheetUrge.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUrgeNotify(ba baVar) {
        if (baVar != null && baVar.a() == 1) {
            t.b(R.string.ysf_remind_success);
            getWorkSheetData();
        } else {
            t.b(R.string.ysf_remind_fail);
        }
        dismissProgressDialog();
    }

    public void showProgressDialog(String str) {
        if (this.progressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            this.progressDialog = progressDialog;
            progressDialog.setCancelable(false);
            this.progressDialog.setMessage(str);
        }
        this.progressDialog.show();
    }

    public void dismissProgressDialog() {
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}

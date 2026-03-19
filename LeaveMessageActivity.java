package com.qiyukf.unicorn.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.n.m;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.common.activity.BaseFragmentActivity;
import com.qiyukf.uikit.session.helper.ClickMovementMethod;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.msg.UnicornMessageBuilder;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.k;
import com.qiyukf.unicorn.h.a.d.p;
import com.qiyukf.unicorn.m.a;
import com.qiyukf.unicorn.mediaselect.internal.entity.Item;
import com.qiyukf.unicorn.mediaselect.internal.utils.PathUtils;
import com.qiyukf.unicorn.n.aa;
import com.qiyukf.unicorn.n.b.e;
import com.qiyukf.unicorn.n.f;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.ui.a.b;
import com.qiyukf.unicorn.ui.worksheet.d;
import com.qiyukf.unicorn.widget.MultipleStatusLayout;
import com.qiyukf.unicorn.widget.ScrollGridView;
import com.qiyukf.unicorn.widget.dialog.ProgressDialog;
import com.qiyukf.unicorn.widget.timepicker.TimeSelector;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes6.dex */
public class LeaveMessageActivity extends BaseFragmentActivity {
    public static final int ANNEX_FIELD_ID = -4;
    public static final int EMAIL_FIELD_ID = -3;
    public static final String FIELD_ID_TAG = "fieldId";
    public static final String FIELD_NAME_TAG = "fieldName";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_VALUE_TAG = "fieldValue";
    public static final String LEAVE_MSG_EXCHANGE_TAG = "LEAVE_MSG_EXCHANGE_TAG";
    public static final String LEAVE_MSG_LABEL_TAG = "LEAVE_MSG_LABEL_TAG";
    public static final String LEAVE_MSG_TEMPLATE_ID_TAG = "LEAVE_MSG_TEMPLATE_ID_TAG";
    public static final int MESSAGE_FIELD_ID = -1;
    public static final int NO_SUBMIT_LEAVE_MSG_CODE = 20;
    public static final int PHONE_FIELD_ID = -2;
    public static final int REQUEST_CODE_CUSTOM_FIELD = 19;
    private static final String TAG = "LeaveMessageActivity";
    private String exchange;
    private long leaveMsgGroupId;
    private String leaveMsgLabel;
    private ProgressDialog progressDialog;
    private b selectAnnexAdapter;
    private Handler uiHandler;
    private View ysfDivide;
    private EditText ysfEtLeaveMsgMessage;
    private ScrollGridView ysfGvAnnexList;
    private ImageView ysfLeaveMessageClose;
    private Button ysfLeaveMessageDone;
    private LinearLayout ysfLeaveParent;
    private TextView ysfLeaveTitle;
    private LinearLayout ysfLlLeaveMsgItemParent;
    private MultipleStatusLayout ysfMslLeaveMsgParent;
    private TextView ysfTvLeaveMsgHint;
    public final Item EMPTY_ITEM = Item.createEmpteItem();
    private long selectTime = 0;
    private Observer<CustomNotification> commandObserver = new Observer<CustomNotification>() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.1
        public AnonymousClass1() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(CustomNotification customNotification) {
            com.qiyukf.unicorn.h.a.b attachStr;
            if (TextUtils.equals(LeaveMessageActivity.this.exchange, customNotification.getSessionId()) && customNotification.getSessionType() == SessionTypeEnum.Ysf && (attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(customNotification.getContent())) != null && attachStr.getCmdId() == 87) {
                if (((p) attachStr).a() == 0) {
                    LeaveMessageActivity.this.isSubmitLeaveMsg = true;
                    LeaveMessageActivity.this.dismissProgressDialog();
                    LeaveMessageActivity leaveMessageActivity = LeaveMessageActivity.this;
                    leaveMessageActivity.leaveMsgSuccessSendMsg(leaveMessageActivity.fieldMainJsonArray, LeaveMessageActivity.this.photoList);
                    LeaveMessageActivity.this.showSuccessLayout();
                    return;
                }
                t.b(R.string.ysf_send_fail_str);
            }
        }
    };
    private ArrayList<Item> photoList = new ArrayList<>();
    private JSONArray fieldMainJsonArray = new JSONArray();
    private boolean isSubmitLeaveMsg = false;
    private boolean annexIsMust = false;

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity
    public boolean hasTitleBar() {
        return false;
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$1 */
    public class AnonymousClass1 implements Observer<CustomNotification> {
        public AnonymousClass1() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(CustomNotification customNotification) {
            com.qiyukf.unicorn.h.a.b attachStr;
            if (TextUtils.equals(LeaveMessageActivity.this.exchange, customNotification.getSessionId()) && customNotification.getSessionType() == SessionTypeEnum.Ysf && (attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(customNotification.getContent())) != null && attachStr.getCmdId() == 87) {
                if (((p) attachStr).a() == 0) {
                    LeaveMessageActivity.this.isSubmitLeaveMsg = true;
                    LeaveMessageActivity.this.dismissProgressDialog();
                    LeaveMessageActivity leaveMessageActivity = LeaveMessageActivity.this;
                    leaveMessageActivity.leaveMsgSuccessSendMsg(leaveMessageActivity.fieldMainJsonArray, LeaveMessageActivity.this.photoList);
                    LeaveMessageActivity.this.showSuccessLayout();
                    return;
                }
                t.b(R.string.ysf_send_fail_str);
            }
        }
    }

    public static void start(Fragment fragment, String str, String str2, long j, int i) {
        Intent intent = new Intent();
        if (fragment == null) {
            return;
        }
        intent.setClass(fragment.getActivity(), LeaveMessageActivity.class);
        intent.putExtra(LEAVE_MSG_LABEL_TAG, str2);
        intent.putExtra(LEAVE_MSG_EXCHANGE_TAG, str);
        intent.putExtra(LEAVE_MSG_TEMPLATE_ID_TAG, j);
        fragment.startActivityForResult(intent, i);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ysf_activity_leave_message);
        initParams();
        findView();
        parseIntent();
        initView();
        registerService();
        requestLeaveMessageInfo();
    }

    private void initParams() {
        this.uiHandler = new Handler(getMainLooper());
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.commandObserver, false);
    }

    private void findView() {
        this.ysfLeaveParent = (LinearLayout) findViewById(R.id.ysf_leave_message_parent);
        this.ysfGvAnnexList = (ScrollGridView) findViewById(R.id.ysf_gv_annex_list);
        this.ysfLeaveMessageDone = (Button) findViewById(R.id.ysf_leave_message_done);
        this.ysfMslLeaveMsgParent = (MultipleStatusLayout) findViewById(R.id.ysf_msl_leave_msg_parent);
        this.ysfLlLeaveMsgItemParent = (LinearLayout) findViewById(R.id.ysf_ll_leave_msg_item_parent);
        this.ysfTvLeaveMsgHint = (TextView) findViewById(R.id.ysf_tv_leave_msg_hint);
        this.ysfEtLeaveMsgMessage = (EditText) findViewById(R.id.ysf_et_leave_msg_message);
        this.ysfLeaveMessageClose = (ImageView) findViewById(R.id.ysf_leave_message_close);
        this.ysfLeaveTitle = (TextView) findViewById(R.id.ysf_leave_message_title);
        this.ysfDivide = findViewById(R.id.ysf_message_include_divider);
        this.ysfLeaveMessageClose.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.2
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LeaveMessageActivity.this.finish();
            }
        });
        this.ysfTvLeaveMsgHint.setOnTouchListener(ClickMovementMethod.newInstance());
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        public AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LeaveMessageActivity.this.finish();
        }
    }

    public void parseIntent() {
        this.exchange = getIntent().getStringExtra(LEAVE_MSG_EXCHANGE_TAG);
        this.leaveMsgLabel = getIntent().getStringExtra(LEAVE_MSG_LABEL_TAG);
        this.leaveMsgGroupId = getIntent().getLongExtra(LEAVE_MSG_TEMPLATE_ID_TAG, 0L);
    }

    private void initView() {
        ForegroundColorSpan foregroundColorSpan;
        if (a.a().c()) {
            this.ysfLeaveTitle.setTextColor(Color.parseColor(a.a().b().s().f()));
            Drawable drawable = this.ysfLeaveMessageClose.getDrawable();
            int color = Color.parseColor(a.a().b().s().b());
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            drawable.setColorFilter(color, mode);
            this.ysfDivide.setBackgroundColor(Color.parseColor(a.a().b().s().a()));
            this.ysfTvLeaveMsgHint.setTextColor(Color.parseColor(a.a().b().s().b()));
            foregroundColorSpan = new ForegroundColorSpan(Color.parseColor(a.a().b().k()));
            this.ysfLeaveMessageDone.setBackground(com.qiyukf.unicorn.m.b.a(a.a().e()));
            this.ysfEtLeaveMsgMessage.setHintTextColor(Color.parseColor(a.a().b().s().c()));
            this.ysfEtLeaveMsgMessage.setTextColor(Color.parseColor(a.a().b().s().f()));
            this.ysfLeaveMessageDone.setTextColor(Color.parseColor(a.a().b().s().d()));
            this.ysfEtLeaveMsgMessage.setBackground(com.qiyukf.unicorn.m.b.a(a.a().b().s().a(), "#00000000", 5));
            if (w.a()) {
                this.ysfLeaveParent.getBackground().setColorFilter(getResources().getColor(R.color.ysf_dark_module), mode);
            }
        } else {
            foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.ysf_blue_5092e1));
            this.ysfLeaveMessageDone.setBackgroundResource(R.drawable.ysf_evaluation_dialog_btn_submit_bg_selector);
        }
        ArrayList<Item> arrayList = this.photoList;
        if (arrayList != null && arrayList.size() == 0) {
            this.photoList.add(this.EMPTY_ITEM);
        }
        b bVar = new b(this, this.photoList, new k() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.3
            @Override // com.qiyukf.unicorn.f.k
            public /* synthetic */ void a(b bVar2) {
                k.CC.$default$a(this, bVar2);
            }

            public void addPhoto(Bitmap bitmap) {
            }

            public AnonymousClass3() {
            }

            @Override // com.qiyukf.unicorn.f.k
            public void removePhoto(int i) {
                LeaveMessageActivity.this.photoList.remove(i);
                if (!Item.EMPTY_TYPE_TAG.equals(((Item) LeaveMessageActivity.this.photoList.get(LeaveMessageActivity.this.photoList.size() - 1)).mimeType)) {
                    LeaveMessageActivity.this.photoList.add(LeaveMessageActivity.this.EMPTY_ITEM);
                }
                LeaveMessageActivity.this.selectAnnexAdapter.notifyDataSetChanged();
            }
        }, (d.a) null);
        this.selectAnnexAdapter = bVar;
        this.ysfGvAnnexList.setAdapter((ListAdapter) bVar);
        f.a(this.ysfTvLeaveMsgHint, this.leaveMsgLabel, com.qiyukf.unicorn.n.p.a() - com.qiyukf.unicorn.n.p.a(32.0f), "-1");
        a.a().a(this.ysfLeaveMessageDone);
        this.ysfLeaveMessageDone.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.4
            public AnonymousClass4() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LeaveMessageActivity.this.isAcceptableAndUpload();
            }
        });
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getString(R.string.ysf_leave_message_require_label));
        spannableStringBuilder.setSpan(foregroundColorSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
        this.ysfEtLeaveMsgMessage.setHint(spannableStringBuilder.toString());
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$3 */
    public class AnonymousClass3 implements k {
        @Override // com.qiyukf.unicorn.f.k
        public /* synthetic */ void a(b bVar2) {
            k.CC.$default$a(this, bVar2);
        }

        public void addPhoto(Bitmap bitmap) {
        }

        public AnonymousClass3() {
        }

        @Override // com.qiyukf.unicorn.f.k
        public void removePhoto(int i) {
            LeaveMessageActivity.this.photoList.remove(i);
            if (!Item.EMPTY_TYPE_TAG.equals(((Item) LeaveMessageActivity.this.photoList.get(LeaveMessageActivity.this.photoList.size() - 1)).mimeType)) {
                LeaveMessageActivity.this.photoList.add(LeaveMessageActivity.this.EMPTY_ITEM);
            }
            LeaveMessageActivity.this.selectAnnexAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$4 */
    public class AnonymousClass4 implements View.OnClickListener {
        public AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LeaveMessageActivity.this.isAcceptableAndUpload();
        }
    }

    public void isAcceptableAndUpload() {
        if (!o.a(this)) {
            t.a(R.string.ysf_network_unable);
            return;
        }
        if (this.annexIsMust && this.photoList.size() == 1 && Item.EMPTY_TYPE_TAG.equals(this.photoList.get(0).mimeType)) {
            t.a(R.string.ysf_leave_msg_annex_toast);
            return;
        }
        if (TextUtils.isEmpty(this.ysfEtLeaveMsgMessage.getText())) {
            t.a(R.string.ysf_leave_msg_null_tip);
            return;
        }
        showProgressDialog(getString(R.string.ysf_submit_ing_str));
        if (this.photoList.size() != 1) {
            JSONObject jSONObject = new JSONObject();
            j.a(jSONObject, FIELD_ID_TAG, -4);
            j.a(jSONObject, FIELD_NAME_TAG, getString(R.string.ysf_annex_str));
            ArrayList arrayList = new ArrayList(6);
            ArrayList arrayList2 = new ArrayList(6);
            for (Item item : this.photoList) {
                if (!Item.EMPTY_TYPE_TAG.equals(item.mimeType)) {
                    if (c.f().isUseSAF) {
                        arrayList.add(item.getContentUri().toString());
                    } else {
                        arrayList.add(PathUtils.getPath(this, item.getContentUri()));
                    }
                    arrayList2.add(item.getContentUri());
                }
            }
            if (arrayList.size() == 0 || TextUtils.isEmpty((CharSequence) arrayList.get(0))) {
                return;
            }
            JSONArray jSONArray = new JSONArray();
            if (o.a() || c.f().isUseSAF) {
                uploadMediaFileQ(arrayList, arrayList2, 0, jSONArray, jSONObject);
                return;
            } else {
                uploadMediaFile(arrayList, 0, jSONArray, jSONObject);
                return;
            }
        }
        submitClick(null);
    }

    private void submitClick(JSONObject jSONObject) {
        com.qiyukf.unicorn.h.a.f.f fVar = new com.qiyukf.unicorn.h.a.f.f();
        fVar.c(this.ysfEtLeaveMsgMessage.getText().toString().trim());
        fVar.b(this.exchange);
        fVar.a(com.qiyukf.unicorn.d.c.d());
        try {
            this.fieldMainJsonArray = new JSONArray("[]");
            for (int i = 0; i < this.ysfLlLeaveMsgItemParent.getChildCount(); i++) {
                ViewGroup viewGroup = (ViewGroup) this.ysfLlLeaveMsgItemParent.getChildAt(i);
                if (viewGroup.getTag() != null) {
                    com.qiyukf.unicorn.f.j jVar = (com.qiyukf.unicorn.f.j) viewGroup.getTag();
                    JSONObject jSONObject2 = new JSONObject();
                    j.a(jSONObject2, FIELD_NAME_TAG, jVar.b());
                    j.a(jSONObject2, FIELD_ID_TAG, jVar.a());
                    if (jVar.c() == 0) {
                        EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_leave_msg_item_content);
                        if (jVar.d() == 1 && TextUtils.isEmpty(editText.getText())) {
                            t.b(R.string.ysf_leave_msg_menu_required_tips);
                            dismissProgressDialog();
                            return;
                        }
                        if (jVar.e() == -2) {
                            String string = editText.getText().toString();
                            if (string.length() < 5) {
                                t.a(getString(R.string.ysf_phone_number_less, 5));
                                dismissProgressDialog();
                                return;
                            }
                            fVar.d(string);
                        } else if (jVar.e() == -3) {
                            fVar.e(editText.getText().toString());
                        }
                        j.a(jSONObject2, FIELD_VALUE_TAG, editText.getText().toString().trim());
                    } else if (jVar.c() == 6) {
                        j.a(jSONObject2, FIELD_VALUE_TAG, "");
                        j.a(jSONObject2, "type", jVar.c());
                        long j = this.selectTime;
                        if (j == 0) {
                            j.a(jSONObject2, FIELD_VALUE_TAG, "");
                        } else {
                            j.a(jSONObject2, FIELD_VALUE_TAG, String.valueOf(j));
                        }
                    } else {
                        TextView textView = (TextView) viewGroup.findViewById(R.id.ysf_tv_leave_msg_info_value);
                        if (jVar.d() == 1 && getString(R.string.ysf_please_choose_str).equals(textView.getText().toString())) {
                            t.b(R.string.ysf_leave_msg_menu_required_tips);
                            dismissProgressDialog();
                            return;
                        }
                        j.a(jSONObject2, FIELD_VALUE_TAG, textView.getText().toString().trim());
                    }
                    j.a(this.fieldMainJsonArray, jSONObject2);
                }
            }
            if (TextUtils.isEmpty(this.ysfEtLeaveMsgMessage.getText().toString().trim())) {
                t.a(R.string.ysf_leave_msg_empty);
                dismissProgressDialog();
                return;
            }
            JSONObject jSONObject3 = new JSONObject();
            j.a(jSONObject3, FIELD_NAME_TAG, getString(R.string.ysf_leave_message));
            j.a(jSONObject3, FIELD_ID_TAG, -1);
            j.a(jSONObject3, FIELD_VALUE_TAG, this.ysfEtLeaveMsgMessage.getText().toString().trim());
            j.a(this.fieldMainJsonArray, jSONObject3);
            if (jSONObject != null) {
                j.a(this.fieldMainJsonArray, jSONObject);
            }
            fVar.a(this.fieldMainJsonArray);
            com.qiyukf.unicorn.k.c.a(fVar, this.exchange).setCallback(new RequestCallback<Void>() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.5
                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public void onSuccess(Void r1) {
                }

                public AnonymousClass5() {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public void onFailed(int i2) {
                    t.a(LeaveMessageActivity.this.getString(R.string.ysf_request_fail_str));
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public void onException(Throwable th) {
                    t.a(LeaveMessageActivity.this.getString(R.string.ysf_request_fail_str));
                }
            });
        } catch (JSONException e) {
            AbsUnicornLog.e(TAG, "创建 jsonArray 失败", e);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$5 */
    public class AnonymousClass5 implements RequestCallback<Void> {
        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public void onSuccess(Void r1) {
        }

        public AnonymousClass5() {
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public void onFailed(int i2) {
            t.a(LeaveMessageActivity.this.getString(R.string.ysf_request_fail_str));
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public void onException(Throwable th) {
            t.a(LeaveMessageActivity.this.getString(R.string.ysf_request_fail_str));
        }
    }

    public void registerService() {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.commandObserver, true);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$6 */
    public class AnonymousClass6 implements RequestCallback<List<com.qiyukf.unicorn.f.j>> {
        public AnonymousClass6() {
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$6$1 */
        public class AnonymousClass1 implements Runnable {
            final /* synthetic */ List val$response;

            public AnonymousClass1(List list) {
                list = list;
            }

            @Override // java.lang.Runnable
            public void run() {
                List<com.qiyukf.unicorn.f.j> list = list;
                if (list == null) {
                    LeaveMessageActivity.this.ysfMslLeaveMsgParent.showLoadErrorView();
                    return;
                }
                for (com.qiyukf.unicorn.f.j jVar : list) {
                    if (jVar.a() != -4) {
                        LeaveMessageActivity.this.addCustomFieldItem(jVar);
                    } else {
                        LeaveMessageActivity.this.annexIsMust = jVar.d() == 1;
                        LeaveMessageActivity.this.ysfGvAnnexList.setVisibility(0);
                    }
                }
                LeaveMessageActivity.this.ysfMslLeaveMsgParent.showContent();
            }
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public void onSuccess(List<com.qiyukf.unicorn.f.j> list) {
            LeaveMessageActivity.this.uiHandler.post(new Runnable() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.6.1
                final /* synthetic */ List val$response;

                public AnonymousClass1(List list2) {
                    list = list2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    List<com.qiyukf.unicorn.f.j> list2 = list;
                    if (list2 == null) {
                        LeaveMessageActivity.this.ysfMslLeaveMsgParent.showLoadErrorView();
                        return;
                    }
                    for (com.qiyukf.unicorn.f.j jVar : list2) {
                        if (jVar.a() != -4) {
                            LeaveMessageActivity.this.addCustomFieldItem(jVar);
                        } else {
                            LeaveMessageActivity.this.annexIsMust = jVar.d() == 1;
                            LeaveMessageActivity.this.ysfGvAnnexList.setVisibility(0);
                        }
                    }
                    LeaveMessageActivity.this.ysfMslLeaveMsgParent.showContent();
                }
            });
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public void onFailed(int i) {
            AbsUnicornLog.i(LeaveMessageActivity.TAG, "requestLeaveMessageInfo is error code= ".concat(String.valueOf(i)));
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallback
        public void onException(Throwable th) {
            AbsUnicornLog.e(LeaveMessageActivity.TAG, "requestLeaveMessageInfo is exception", th);
        }
    }

    private void requestLeaveMessageInfo() {
        this.ysfMslLeaveMsgParent.showLoadingView();
        new com.qiyukf.unicorn.n.a<Void, Void>(com.qiyukf.unicorn.n.a.HTTP_TAG) { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.7
            final /* synthetic */ RequestCallback val$callback;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(String str, RequestCallback requestCallback) {
                super(str);
                requestCallback = requestCallback;
            }

            @Override // com.qiyukf.unicorn.n.a
            public Void doInBackground(Void... voidArr) {
                com.qiyukf.unicorn.i.a.a(LeaveMessageActivity.this.exchange, LeaveMessageActivity.this.leaveMsgGroupId, (RequestCallback<List<com.qiyukf.unicorn.f.j>>) requestCallback);
                return null;
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$7 */
    public class AnonymousClass7 extends com.qiyukf.unicorn.n.a<Void, Void> {
        final /* synthetic */ RequestCallback val$callback;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(String str, RequestCallback requestCallback) {
            super(str);
            requestCallback = requestCallback;
        }

        @Override // com.qiyukf.unicorn.n.a
        public Void doInBackground(Void... voidArr) {
            com.qiyukf.unicorn.i.a.a(LeaveMessageActivity.this.exchange, LeaveMessageActivity.this.leaveMsgGroupId, (RequestCallback<List<com.qiyukf.unicorn.f.j>>) requestCallback);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x00b4  */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onActivityResult(int r12, int r13, android.content.Intent r14) {
        /*
            Method dump skipped, instruction units count: 217
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        backProcess();
        super.onBackPressed();
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity
    public void onTitleBarBackPressed() {
        backProcess();
    }

    private void addPhoto(Item item) {
        if (this.photoList.size() <= 4) {
            this.photoList.add(r0.size() - 1, item);
        } else if (this.photoList.size() == 5) {
            this.photoList.remove(r0.size() - 1);
            this.photoList.add(item);
        }
        this.selectAnnexAdapter.notifyDataSetChanged();
    }

    public static void setListViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter adapter = gridView.getAdapter();
        if (adapter == null) {
            return;
        }
        int measuredHeight = 0;
        for (int i = 0; i < adapter.getCount(); i += 3) {
            View view = adapter.getView(i, null, gridView);
            view.measure(0, 0);
            measuredHeight += view.getMeasuredHeight() + com.qiyukf.unicorn.n.p.a(20.0f);
        }
        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        layoutParams.height = measuredHeight;
        ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(com.qiyukf.unicorn.n.p.a(16.0f), com.qiyukf.unicorn.n.p.a(10.0f), com.qiyukf.unicorn.n.p.a(16.0f), com.qiyukf.unicorn.n.p.a(10.0f));
        gridView.setLayoutParams(layoutParams);
    }

    public void addCustomFieldItem(com.qiyukf.unicorn.f.j jVar) {
        if (jVar.c() == -4) {
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String strB = jVar.b();
        if ("en".equals(aa.b(this).getLanguage())) {
            if (strB.length() > 17) {
                spannableStringBuilder.append((CharSequence) strB.substring(0, 16)).append((CharSequence) "...");
            } else {
                spannableStringBuilder.append((CharSequence) strB);
            }
        } else if (strB.length() > 10) {
            spannableStringBuilder.append((CharSequence) strB.substring(0, 10)).append((CharSequence) "...");
        } else {
            spannableStringBuilder.append((CharSequence) strB);
        }
        if (jVar.d() == 1) {
            spannableStringBuilder.append((CharSequence) getString(R.string.ysf_require_field));
        }
        addInfoItem(spannableStringBuilder, jVar, customFileClickListener(jVar));
    }

    public void refreshData(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (int i2 = 0; i2 < this.ysfLlLeaveMsgItemParent.getChildCount(); i2++) {
            ViewGroup viewGroup = (ViewGroup) this.ysfLlLeaveMsgItemParent.getChildAt(i2);
            if (viewGroup.getTag() != null) {
                com.qiyukf.unicorn.f.j jVar = (com.qiyukf.unicorn.f.j) viewGroup.getTag();
                if (jVar.a() == i && jVar.c() != 0) {
                    TextView textView = (TextView) viewGroup.findViewById(R.id.ysf_tv_leave_msg_info_value);
                    textView.setText(str);
                    if (a.a().c()) {
                        textView.setTextColor(Color.parseColor(a.a().b().s().f()));
                        return;
                    }
                    return;
                }
            }
        }
    }

    private View.OnClickListener customFileClickListener(com.qiyukf.unicorn.f.j jVar) {
        int iC = jVar.c();
        if (iC == 1 || iC == 2) {
            return new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.8
                final /* synthetic */ com.qiyukf.unicorn.f.j val$field;

                public AnonymousClass8(com.qiyukf.unicorn.f.j jVar2) {
                    jVar = jVar2;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    LeaveMsgCustomFieldMenuActivity.startForResult(LeaveMessageActivity.this, 19, jVar);
                }
            };
        }
        if (iC != 6) {
            return null;
        }
        return new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.9
            final /* synthetic */ com.qiyukf.unicorn.f.j val$field;

            public AnonymousClass9(com.qiyukf.unicorn.f.j jVar2) {
                jVar = jVar2;
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$9$1 */
            public class AnonymousClass1 implements TimeSelector.ResultHandler {
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                public void handle(String str, Date date) {
                    LeaveMessageActivity.this.selectTime = date.getTime();
                    AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                    LeaveMessageActivity.this.refreshData(jVar.a(), str);
                }
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TimeSelector timeSelector = new TimeSelector(LeaveMessageActivity.this, new TimeSelector.ResultHandler() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.9.1
                    public AnonymousClass1() {
                    }

                    @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                    public void handle(String str, Date date) {
                        LeaveMessageActivity.this.selectTime = date.getTime();
                        AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                        LeaveMessageActivity.this.refreshData(jVar.a(), str);
                    }
                }, TimeSelector.START_TIME, TimeSelector.END_TIME, true, true, true, true, true, "yyyy-MM-dd HH:mm", jVar.b());
                timeSelector.setCurrentData(new Date());
                timeSelector.show();
            }
        };
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$8 */
    public class AnonymousClass8 implements View.OnClickListener {
        final /* synthetic */ com.qiyukf.unicorn.f.j val$field;

        public AnonymousClass8(com.qiyukf.unicorn.f.j jVar2) {
            jVar = jVar2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LeaveMsgCustomFieldMenuActivity.startForResult(LeaveMessageActivity.this, 19, jVar);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$9 */
    public class AnonymousClass9 implements View.OnClickListener {
        final /* synthetic */ com.qiyukf.unicorn.f.j val$field;

        public AnonymousClass9(com.qiyukf.unicorn.f.j jVar2) {
            jVar = jVar2;
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$9$1 */
        public class AnonymousClass1 implements TimeSelector.ResultHandler {
            public AnonymousClass1() {
            }

            @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
            public void handle(String str, Date date) {
                LeaveMessageActivity.this.selectTime = date.getTime();
                AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                LeaveMessageActivity.this.refreshData(jVar.a(), str);
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TimeSelector timeSelector = new TimeSelector(LeaveMessageActivity.this, new TimeSelector.ResultHandler() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.9.1
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                public void handle(String str, Date date) {
                    LeaveMessageActivity.this.selectTime = date.getTime();
                    AnonymousClass9 anonymousClass9 = AnonymousClass9.this;
                    LeaveMessageActivity.this.refreshData(jVar.a(), str);
                }
            }, TimeSelector.START_TIME, TimeSelector.END_TIME, true, true, true, true, true, "yyyy-MM-dd HH:mm", jVar.b());
            timeSelector.setCurrentData(new Date());
            timeSelector.show();
        }
    }

    private void addInfoItem(CharSequence charSequence, com.qiyukf.unicorn.f.j jVar, View.OnClickListener onClickListener) {
        ViewGroup viewGroup = (ViewGroup) View.inflate(this, R.layout.ysf_view_holder_leave_msg_info, null);
        viewGroup.setTag(jVar);
        TextView textView = (TextView) viewGroup.findViewById(R.id.ysf_tv_leave_msg_info_label);
        TextView textView2 = (TextView) viewGroup.findViewById(R.id.ysf_tv_leave_msg_info_value);
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.ysf_iv_leave_msg_info_arrow);
        EditText editText = (EditText) viewGroup.findViewById(R.id.ysf_et_leave_msg_item_content);
        if (a.a().c()) {
            viewGroup.setBackground(com.qiyukf.unicorn.m.b.a(a.a().b().s().a(), "#00000000", 5));
            textView.setTextColor(Color.parseColor(a.a().b().s().c()));
            textView2.setTextColor(Color.parseColor(a.a().b().s().f()));
            imageView.getDrawable().setColorFilter(Color.parseColor(a.a().b().s().b()), PorterDuff.Mode.SRC_IN);
            editText.setHintTextColor(Color.parseColor(a.a().b().s().c()));
            editText.setTextColor(Color.parseColor(a.a().b().s().f()));
        }
        if (jVar.a() == -2) {
            editText.setInputType(3);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        }
        textView.setText(charSequence);
        if (onClickListener != null) {
            imageView.setVisibility(0);
            textView2.setVisibility(0);
            textView.setVisibility(0);
            editText.setVisibility(8);
            viewGroup.setOnClickListener(onClickListener);
            if (TextUtils.isEmpty(jVar.g())) {
                textView2.setText(getString(R.string.ysf_please_choose_str));
            } else {
                textView2.setText(jVar.g());
            }
            if (a.a().c()) {
                textView.setTextColor(Color.parseColor(a.a().b().s().f()));
                textView2.setTextColor(Color.parseColor(a.a().b().s().c()));
            }
        } else {
            textView2.setVisibility(8);
            editText.setVisibility(0);
            textView.setVisibility(8);
            imageView.setVisibility(8);
            if ("1".equals(jVar.f())) {
                editText.setInputType(2);
            }
            editText.setHint(charSequence);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, com.qiyukf.unicorn.n.p.a(58.0f));
        layoutParams.bottomMargin = com.qiyukf.unicorn.n.p.a(10.0f);
        this.ysfLlLeaveMsgItemParent.addView(viewGroup, layoutParams);
    }

    public void backProcess() {
        if (!this.isSubmitLeaveMsg) {
            setResult(20);
        }
        finish();
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

    public void showSuccessLayout() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(0, com.qiyukf.unicorn.n.p.a(120.0f), 0, 0);
        try {
            this.ysfMslLeaveMsgParent.showCustom(layoutParams);
            this.ysfLlLeaveMsgItemParent.setVisibility(8);
            this.ysfEtLeaveMsgMessage.setVisibility(8);
            this.ysfGvAnnexList.setVisibility(8);
            this.ysfLeaveMessageDone.setVisibility(8);
            this.ysfMslLeaveMsgParent.getView(5).findViewById(R.id.ysf_leave_message_success_close).setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.10
                public AnonymousClass10() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    LeaveMessageActivity.this.backProcess();
                }
            });
            if (a.a().c()) {
                TextView textView = (TextView) this.ysfMslLeaveMsgParent.getView(5).findViewById(R.id.ysf_leave_message_success_title);
                TextView textView2 = (TextView) this.ysfMslLeaveMsgParent.getView(5).findViewById(R.id.ysf_leave_message_success_content);
                textView.setTextColor(Color.parseColor(a.a().b().s().f()));
                textView2.setTextColor(Color.parseColor(a.a().b().s().f()));
                if (w.a()) {
                    this.ysfMslLeaveMsgParent.getView(5).getBackground().setColorFilter(getResources().getColor(R.color.ysf_dark_module), PorterDuff.Mode.SRC_IN);
                }
            }
        } catch (NullPointerException e) {
            AbsUnicornLog.e(TAG, "showSuccessLayout is error", e);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$10 */
    public class AnonymousClass10 implements View.OnClickListener {
        public AnonymousClass10() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LeaveMessageActivity.this.backProcess();
        }
    }

    public void leaveMsgSuccessSendMsg(JSONArray jSONArray, List<Item> list) {
        com.qiyukf.unicorn.h.a.d.o oVar = new com.qiyukf.unicorn.h.a.d.o();
        oVar.a(list.size());
        oVar.a(jSONArray.toString());
        IMMessage iMMessageBuildCustomMessage = UnicornMessageBuilder.buildCustomMessage(this.exchange, oVar);
        iMMessageBuildCustomMessage.setDirect(MsgDirectionEnum.Out);
        MsgStatusEnum msgStatusEnum = MsgStatusEnum.success;
        iMMessageBuildCustomMessage.setStatus(msgStatusEnum);
        ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageBuildCustomMessage, true);
        IMMessage iMMessageBuildTextMessage = UnicornMessageBuilder.buildTextMessage(this.exchange, getString(R.string.ysf_leave_msg_process_hint));
        iMMessageBuildTextMessage.setDirect(MsgDirectionEnum.In);
        iMMessageBuildTextMessage.setStatus(msgStatusEnum);
        ((YsfService) NIMClient.getService(YsfService.class)).saveMessageToLocal(iMMessageBuildTextMessage, true);
    }

    public void uploadMediaFileQ(List<String> list, List<Uri> list2, int i, JSONArray jSONArray, JSONObject jSONObject) {
        String str;
        if (list2.size() == i) {
            j.a(jSONObject, FIELD_VALUE_TAG, jSONArray);
            submitClick(jSONObject);
            return;
        }
        if (list2.size() == 0 || list2.get(i) == null) {
            return;
        }
        String strA = m.a(this, list2.get(i));
        if (c.f().isUseSAF) {
            String strA2 = w.a(this, Uri.parse(list.get(i)));
            if (TextUtils.isEmpty(strA2)) {
                strA2 = "png";
            }
            str = strA + "." + strA2;
        } else {
            str = strA + "." + e.a(list.get(i));
        }
        String strA3 = com.qiyukf.unicorn.n.e.d.a(str, com.qiyukf.unicorn.n.e.c.TYPE_IMAGE);
        if (com.qiyukf.nimlib.net.a.c.a.a(this, list2.get(i), strA3)) {
            File file = new File(strA3);
            if (!c.f().isUseSAF || file.length() <= 52428800) {
                FileAttachment fileAttachment = new FileAttachment();
                fileAttachment.setPath(file.getPath());
                fileAttachment.setSize(file.length());
                fileAttachment.setDisplayName(file.getName());
                ((MsgService) NIMClient.getService(MsgService.class)).sendFile(fileAttachment).setCallback(new RequestCallbackWrapper<FileAttachment>() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.11
                    final /* synthetic */ JSONObject val$annexJsonObject;
                    final /* synthetic */ List val$filePaths;
                    final /* synthetic */ List val$fileUris;
                    final /* synthetic */ JSONArray val$jsonArray;
                    final /* synthetic */ int val$position;

                    public AnonymousClass11(JSONArray jSONArray2, List list3, List list22, int i2, JSONObject jSONObject2) {
                        jSONArray = jSONArray2;
                        list = list3;
                        list = list22;
                        i = i2;
                        jSONObject = jSONObject2;
                    }

                    @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                    public void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
                        if (i2 == 200) {
                            JSONObject jSONObject2 = new JSONObject();
                            j.a(jSONObject2, "name", fileAttachment2.getDisplayName());
                            j.a(jSONObject2, "size", fileAttachment2.getSize());
                            j.a(jSONObject2, "url", fileAttachment2.getUrl());
                            j.a(jSONArray, jSONObject2);
                            LeaveMessageActivity.this.uploadMediaFileQ(list, list, i + 1, jSONArray, jSONObject);
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

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$11 */
    public class AnonymousClass11 extends RequestCallbackWrapper<FileAttachment> {
        final /* synthetic */ JSONObject val$annexJsonObject;
        final /* synthetic */ List val$filePaths;
        final /* synthetic */ List val$fileUris;
        final /* synthetic */ JSONArray val$jsonArray;
        final /* synthetic */ int val$position;

        public AnonymousClass11(JSONArray jSONArray2, List list3, List list22, int i2, JSONObject jSONObject2) {
            jSONArray = jSONArray2;
            list = list3;
            list = list22;
            i = i2;
            jSONObject = jSONObject2;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
            if (i2 == 200) {
                JSONObject jSONObject2 = new JSONObject();
                j.a(jSONObject2, "name", fileAttachment2.getDisplayName());
                j.a(jSONObject2, "size", fileAttachment2.getSize());
                j.a(jSONObject2, "url", fileAttachment2.getUrl());
                j.a(jSONArray, jSONObject2);
                LeaveMessageActivity.this.uploadMediaFileQ(list, list, i + 1, jSONArray, jSONObject);
                return;
            }
            t.a(R.string.ysf_bot_form_upload_image_failed);
        }
    }

    public void uploadMediaFile(List<String> list, int i, JSONArray jSONArray, JSONObject jSONObject) {
        if (list.size() == i) {
            j.a(jSONObject, FIELD_VALUE_TAG, jSONArray);
            submitClick(jSONObject);
            return;
        }
        String strA = com.qiyukf.unicorn.n.e.d.a(m.b(list.get(i)) + "." + e.a(list.get(i)), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (com.qiyukf.nimlib.net.a.c.a.a(list.get(i), strA) != -1) {
            File file = new File(strA);
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setPath(file.getPath());
            fileAttachment.setSize(file.length());
            fileAttachment.setDisplayName(file.getName());
            ((MsgService) NIMClient.getService(MsgService.class)).sendFile(fileAttachment).setCallback(new RequestCallbackWrapper<FileAttachment>() { // from class: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity.12
                final /* synthetic */ JSONObject val$annexJsonObject;
                final /* synthetic */ List val$filePaths;
                final /* synthetic */ JSONArray val$jsonArray;
                final /* synthetic */ int val$position;

                public AnonymousClass12(JSONArray jSONArray2, List list2, int i2, JSONObject jSONObject2) {
                    jSONArray = jSONArray2;
                    list = list2;
                    i = i2;
                    jSONObject = jSONObject2;
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                public void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
                    if (i2 == 200) {
                        JSONObject jSONObject2 = new JSONObject();
                        j.a(jSONObject2, "name", fileAttachment2.getDisplayName());
                        j.a(jSONObject2, "size", fileAttachment2.getSize());
                        j.a(jSONObject2, "url", fileAttachment2.getUrl());
                        j.a(jSONArray, jSONObject2);
                        LeaveMessageActivity.this.uploadMediaFile(list, i + 1, jSONArray, jSONObject);
                        return;
                    }
                    t.a(R.string.ysf_bot_form_upload_image_failed);
                }
            });
            return;
        }
        t.a(R.string.ysf_media_exception);
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.LeaveMessageActivity$12 */
    public class AnonymousClass12 extends RequestCallbackWrapper<FileAttachment> {
        final /* synthetic */ JSONObject val$annexJsonObject;
        final /* synthetic */ List val$filePaths;
        final /* synthetic */ JSONArray val$jsonArray;
        final /* synthetic */ int val$position;

        public AnonymousClass12(JSONArray jSONArray2, List list2, int i2, JSONObject jSONObject2) {
            jSONArray = jSONArray2;
            list = list2;
            i = i2;
            jSONObject = jSONObject2;
        }

        @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
        public void onResult(int i2, FileAttachment fileAttachment2, Throwable th) {
            if (i2 == 200) {
                JSONObject jSONObject2 = new JSONObject();
                j.a(jSONObject2, "name", fileAttachment2.getDisplayName());
                j.a(jSONObject2, "size", fileAttachment2.getSize());
                j.a(jSONObject2, "url", fileAttachment2.getUrl());
                j.a(jSONArray, jSONObject2);
                LeaveMessageActivity.this.uploadMediaFile(list, i + 1, jSONArray, jSONObject);
                return;
            }
            t.a(R.string.ysf_bot_form_upload_image_failed);
        }
    }
}

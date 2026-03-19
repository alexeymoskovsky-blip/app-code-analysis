package com.qiyukf.unicorn.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.facebook.appevents.UserDataStore;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.uikit.common.activity.BaseFragmentActivity;
import com.qiyukf.uikit.session.helper.WorkSheetHelper;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.h.a.b;
import com.qiyukf.unicorn.h.a.d.ak;
import com.qiyukf.unicorn.h.a.f.aa;
import com.qiyukf.unicorn.m.a;
import com.qiyukf.unicorn.n.g;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.ui.a.c;
import com.qiyukf.unicorn.widget.FullPopupMenu;
import com.qiyukf.unicorn.widget.dialog.ProgressDialog;
import com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout;
import com.qiyukf.unicorn.widget.pulltorefresh.PullableListView;
import com.tencent.connect.common.Constants;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes6.dex */
public class UserWorkSheetListActivity extends BaseFragmentActivity {
    private EditText etSearch;
    private String exchange;
    private OrderItem groupSort;
    private ImageView ivArrow1;
    private ImageView ivArrow2;
    private ImageView ivDelete;
    private ImageView ivSearch;
    private LinearLayout llParent;
    private LinearLayout llSort;
    private LinearLayout llStatus;
    private TextView mCancel;
    private FullPopupMenu popupMenuSort;
    private FullPopupMenu popupMenuStatus;
    private ProgressDialog progressDialog;
    private ArrayList<Long> templateIds;
    private TextView tvSortHeader;
    private TextView tvStatusHeader;
    private c workSheetListAdapter;
    private ImageView ysfIvWorkSheetEmpty;
    private PullableListView ysfPlWorkSheetList;
    private PullToRefreshLayout ysfPtlWorkSheetListParent;
    private TextView ysfTvWorkSheetEmpty;
    private LinearLayout ysfTvWorkSheetListEmpty;
    private TextView ysf_tv_work_sheet_list_total;
    private List<ak.a> workSheetList = new ArrayList();
    private boolean isOpenUrge = false;
    private final List<OrderItem> groupSortList = new ArrayList();
    private final List<OrderItem> groupStatusList = new ArrayList();
    private List<OrderItem> groupStatus = new ArrayList();
    private Observer<CustomNotification> customNotificationObserver = new UserWorkSheetListActivity$$ExternalSyntheticLambda0(this);

    public static void start(Context context, List<Long> list, boolean z, String str) {
        Intent intent = new Intent(context, (Class<?>) UserWorkSheetListActivity.class);
        if (list != null) {
            intent.putExtra("TEMPLATE_ID_TAG", new ArrayList(list));
        }
        intent.putExtra("IS_OPEN_URGE_TAG", z);
        intent.putExtra("BID_TAG", str);
        context.startActivity(intent);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ysf_activity_worksheet_list);
        parseIntent();
        registerObserver(true);
        initView();
        initSortPopupMenu();
        initStatusPopupMenu();
        getWorkSheetList();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    private void parseIntent() {
        this.templateIds = (ArrayList) getIntent().getSerializableExtra("TEMPLATE_ID_TAG");
        this.isOpenUrge = getIntent().getBooleanExtra("IS_OPEN_URGE_TAG", false);
        this.exchange = getIntent().getStringExtra("BID_TAG");
    }

    private void initView() {
        this.llParent = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_list_parent);
        this.ysfPlWorkSheetList = (PullableListView) findViewById(R.id.ysf_pl_work_sheet_list);
        this.ysfPtlWorkSheetListParent = (PullToRefreshLayout) findViewById(R.id.ysf_ptl_work_sheet_list_parent);
        this.ysf_tv_work_sheet_list_total = (TextView) findViewById(R.id.ysf_tv_work_sheet_list_total);
        this.ysfPlWorkSheetList.setEnable(true, false);
        this.ysfTvWorkSheetListEmpty = (LinearLayout) findViewById(R.id.ysf_tv_work_sheet_list_empty);
        this.ysfTvWorkSheetEmpty = (TextView) findViewById(R.id.ysf_tv_work_sheet_empty);
        this.ysfIvWorkSheetEmpty = (ImageView) findViewById(R.id.ysf_iv_work_sheet_empty);
        this.etSearch = (EditText) findViewById(R.id.ysf_et_work_sheet_search);
        this.ivDelete = (ImageView) findViewById(R.id.ysf_iv_work_sheet_search_delete);
        this.ivSearch = (ImageView) findViewById(R.id.ysf_iv_work_sheet_search_icon);
        this.ivArrow1 = (ImageView) findViewById(R.id.ysf_iv_work_sheet_status1);
        this.ivArrow2 = (ImageView) findViewById(R.id.ysf_iv_work_sheet_status2);
        this.mCancel = (TextView) findViewById(R.id.ysf_iv_work_sheet_search_cancel);
        this.tvSortHeader = (TextView) findViewById(R.id.ysf_tv_work_sheet_sort);
        this.llSort = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_sort);
        this.tvStatusHeader = (TextView) findViewById(R.id.ysf_tv_work_sheet_status);
        this.llStatus = (LinearLayout) findViewById(R.id.ysf_ll_work_sheet_status);
        c cVar = new c(this, this.workSheetList);
        this.workSheetListAdapter = cVar;
        this.ysfPlWorkSheetList.setAdapter((ListAdapter) cVar);
        if (a.a().c()) {
            Drawable background = this.ivSearch.getBackground();
            int color = Color.parseColor(a.a().b().s().b());
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            background.setColorFilter(color, mode);
            this.etSearch.setHintTextColor(Color.parseColor(a.a().b().s().c()));
            this.etSearch.setTextColor(Color.parseColor(a.a().b().s().f()));
            this.mCancel.setTextColor(Color.parseColor(a.a().e()));
            this.ivDelete.getDrawable().setColorFilter(Color.parseColor(a.a().b().s().b()), mode);
            this.ysf_tv_work_sheet_list_total.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.tvStatusHeader.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.tvSortHeader.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.ivArrow1.getBackground().setColorFilter(Color.parseColor(a.a().b().s().b()), mode);
            this.ivArrow2.getBackground().setColorFilter(Color.parseColor(a.a().b().s().b()), mode);
            this.ysfTvWorkSheetEmpty.setTextColor(Color.parseColor(a.a().b().s().b()));
            this.ysfIvWorkSheetEmpty.getBackground().setColorFilter(Color.parseColor(a.a().b().s().c()), mode);
            if (w.a()) {
                this.llParent.setBackgroundColor(getResources().getColor(R.color.ysf_dark_module));
                this.ysfTvWorkSheetEmpty.setTextColor(Color.parseColor(a.a().b().s().b()));
            }
        }
        this.ysfPlWorkSheetList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.1
            public AnonymousClass1() {
            }

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (UserWorkSheetListActivity.this.workSheetList.size() <= i) {
                    return;
                }
                UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
                WorkSheetDetailActivity.start(userWorkSheetListActivity, ((ak.a) userWorkSheetListActivity.workSheetList.get(i)).a(), UserWorkSheetListActivity.this.isOpenUrge, UserWorkSheetListActivity.this.exchange);
            }
        });
        this.ysfPtlWorkSheetListParent.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.2
            @Override // com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout.OnRefreshListener
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            }

            public AnonymousClass2() {
            }

            @Override // com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout.OnRefreshListener
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                UserWorkSheetListActivity.this.getWorkSheetList();
            }
        });
        this.etSearch.addTextChangedListener(new TextWatcher() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public AnonymousClass3() {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                UserWorkSheetListActivity.this.ivDelete.setVisibility(TextUtils.isEmpty(editable.toString()) ? 8 : 0);
            }
        });
        this.etSearch.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.4
            public AnonymousClass4() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserWorkSheetListActivity.this.ivDelete.setVisibility(TextUtils.isEmpty(UserWorkSheetListActivity.this.etSearch.getText().toString()) ? 8 : 0);
                UserWorkSheetListActivity.this.setCancelVisible();
            }
        });
        this.etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.5
            public AnonymousClass5() {
            }

            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                UserWorkSheetListActivity.this.setCancelVisible();
            }
        });
        this.ivDelete.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.6
            public AnonymousClass6() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserWorkSheetListActivity.this.etSearch.setText("");
            }
        });
        this.mCancel.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.7
            public AnonymousClass7() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserWorkSheetListActivity.this.etSearch.setText("");
                UserWorkSheetListActivity.this.cancelKeyBoard();
                UserWorkSheetListActivity.this.getWorkSheetList();
            }
        });
        this.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.8
            public AnonymousClass8() {
            }

            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 3) {
                    return true;
                }
                UserWorkSheetListActivity.this.cancelKeyBoard();
                UserWorkSheetListActivity.this.getWorkSheetList();
                return true;
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$1 */
    public class AnonymousClass1 implements AdapterView.OnItemClickListener {
        public AnonymousClass1() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (UserWorkSheetListActivity.this.workSheetList.size() <= i) {
                return;
            }
            UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
            WorkSheetDetailActivity.start(userWorkSheetListActivity, ((ak.a) userWorkSheetListActivity.workSheetList.get(i)).a(), UserWorkSheetListActivity.this.isOpenUrge, UserWorkSheetListActivity.this.exchange);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$2 */
    public class AnonymousClass2 implements PullToRefreshLayout.OnRefreshListener {
        @Override // com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout.OnRefreshListener
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        }

        public AnonymousClass2() {
        }

        @Override // com.qiyukf.unicorn.widget.pulltorefresh.PullToRefreshLayout.OnRefreshListener
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            UserWorkSheetListActivity.this.getWorkSheetList();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$3 */
    public class AnonymousClass3 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public AnonymousClass3() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            UserWorkSheetListActivity.this.ivDelete.setVisibility(TextUtils.isEmpty(editable.toString()) ? 8 : 0);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$4 */
    public class AnonymousClass4 implements View.OnClickListener {
        public AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            UserWorkSheetListActivity.this.ivDelete.setVisibility(TextUtils.isEmpty(UserWorkSheetListActivity.this.etSearch.getText().toString()) ? 8 : 0);
            UserWorkSheetListActivity.this.setCancelVisible();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$5 */
    public class AnonymousClass5 implements View.OnFocusChangeListener {
        public AnonymousClass5() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            UserWorkSheetListActivity.this.setCancelVisible();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$6 */
    public class AnonymousClass6 implements View.OnClickListener {
        public AnonymousClass6() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            UserWorkSheetListActivity.this.etSearch.setText("");
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$7 */
    public class AnonymousClass7 implements View.OnClickListener {
        public AnonymousClass7() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            UserWorkSheetListActivity.this.etSearch.setText("");
            UserWorkSheetListActivity.this.cancelKeyBoard();
            UserWorkSheetListActivity.this.getWorkSheetList();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$8 */
    public class AnonymousClass8 implements TextView.OnEditorActionListener {
        public AnonymousClass8() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 3) {
                return true;
            }
            UserWorkSheetListActivity.this.cancelKeyBoard();
            UserWorkSheetListActivity.this.getWorkSheetList();
            return true;
        }
    }

    private void initSortPopupMenu() {
        this.groupSortList.add(new OrderItem(UserDataStore.CITY, getString(R.string.ysf_creation_time)));
        this.groupSortList.add(new OrderItem("ut", getString(R.string.ysf_update_time)));
        this.groupSortList.add(new OrderItem("appendField", getString(R.string.ysf_append_file)));
        OrderItem orderItem = this.groupSortList.get(0);
        this.groupSort = orderItem;
        this.tvSortHeader.setText(getGroupName(orderItem));
        this.llSort.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.9
            public AnonymousClass9() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int size = UserWorkSheetListActivity.this.groupSortList.size();
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = ((OrderItem) UserWorkSheetListActivity.this.groupSortList.get(i)).getName();
                }
                UserWorkSheetListActivity.this.popupMenuSort.setItems(strArr);
                FullPopupMenu fullPopupMenu = UserWorkSheetListActivity.this.popupMenuSort;
                UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
                fullPopupMenu.setSelectPosition(userWorkSheetListActivity.getGroupSortPosition((List<OrderItem>) userWorkSheetListActivity.groupSortList, UserWorkSheetListActivity.this.groupSort));
                UserWorkSheetListActivity.this.popupMenuSort.showAsDropDown(UserWorkSheetListActivity.this.llSort);
            }
        });
        FullPopupMenu fullPopupMenu = new FullPopupMenu(this);
        this.popupMenuSort = fullPopupMenu;
        fullPopupMenu.setOnClickListener(new FullPopupMenu.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.10
            public AnonymousClass10() {
            }

            @Override // com.qiyukf.unicorn.widget.FullPopupMenu.OnClickListener
            public void onClick(int i) {
                OrderItem orderItem2 = (OrderItem) UserWorkSheetListActivity.this.groupSortList.get(i);
                if (UserWorkSheetListActivity.this.groupSort.getSortBy().equals(orderItem2.getSortBy())) {
                    return;
                }
                UserWorkSheetListActivity.this.groupSort = orderItem2;
                TextView textView = UserWorkSheetListActivity.this.tvSortHeader;
                UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
                textView.setText(userWorkSheetListActivity.getGroupName(userWorkSheetListActivity.groupSort));
                UserWorkSheetListActivity.this.getWorkSheetList();
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$9 */
    public class AnonymousClass9 implements View.OnClickListener {
        public AnonymousClass9() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int size = UserWorkSheetListActivity.this.groupSortList.size();
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = ((OrderItem) UserWorkSheetListActivity.this.groupSortList.get(i)).getName();
            }
            UserWorkSheetListActivity.this.popupMenuSort.setItems(strArr);
            FullPopupMenu fullPopupMenu = UserWorkSheetListActivity.this.popupMenuSort;
            UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
            fullPopupMenu.setSelectPosition(userWorkSheetListActivity.getGroupSortPosition((List<OrderItem>) userWorkSheetListActivity.groupSortList, UserWorkSheetListActivity.this.groupSort));
            UserWorkSheetListActivity.this.popupMenuSort.showAsDropDown(UserWorkSheetListActivity.this.llSort);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$10 */
    public class AnonymousClass10 implements FullPopupMenu.OnClickListener {
        public AnonymousClass10() {
        }

        @Override // com.qiyukf.unicorn.widget.FullPopupMenu.OnClickListener
        public void onClick(int i) {
            OrderItem orderItem2 = (OrderItem) UserWorkSheetListActivity.this.groupSortList.get(i);
            if (UserWorkSheetListActivity.this.groupSort.getSortBy().equals(orderItem2.getSortBy())) {
                return;
            }
            UserWorkSheetListActivity.this.groupSort = orderItem2;
            TextView textView = UserWorkSheetListActivity.this.tvSortHeader;
            UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
            textView.setText(userWorkSheetListActivity.getGroupName(userWorkSheetListActivity.groupSort));
            UserWorkSheetListActivity.this.getWorkSheetList();
        }
    }

    private void initStatusPopupMenu() {
        this.groupStatusList.add(new OrderItem("5", getString(R.string.ysf_work_sheet_status_un_process)));
        this.groupStatusList.add(new OrderItem(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, getString(R.string.ysf_work_sheet_status_ing)));
        this.groupStatusList.add(new OrderItem("20", getString(R.string.ysf_work_sheet_status_done)));
        this.groupStatusList.add(new OrderItem("25", getString(R.string.ysf_work_sheet_status_turn_down)));
        this.groupStatus.addAll(this.groupStatusList);
        this.tvStatusHeader.setText(getString(R.string.ysf_work_sheet_status));
        this.llStatus.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.11
            public AnonymousClass11() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int size = UserWorkSheetListActivity.this.groupStatusList.size();
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = ((OrderItem) UserWorkSheetListActivity.this.groupStatusList.get(i)).getName();
                }
                UserWorkSheetListActivity.this.popupMenuStatus.setItems(strArr);
                FullPopupMenu fullPopupMenu = UserWorkSheetListActivity.this.popupMenuStatus;
                UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
                fullPopupMenu.setSelectPosition(userWorkSheetListActivity.getGroupSortPosition((List<OrderItem>) userWorkSheetListActivity.groupStatusList, (List<OrderItem>) UserWorkSheetListActivity.this.groupStatus));
                UserWorkSheetListActivity.this.popupMenuStatus.showAsDropDown(UserWorkSheetListActivity.this.llStatus);
            }
        });
        FullPopupMenu fullPopupMenu = new FullPopupMenu(this);
        this.popupMenuStatus = fullPopupMenu;
        fullPopupMenu.setSelectPosition(getGroupSortPosition(this.groupStatusList, this.groupStatus));
        this.popupMenuStatus.setOnClickListener(new FullPopupMenu.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity.12
            public AnonymousClass12() {
            }

            @Override // com.qiyukf.unicorn.widget.FullPopupMenu.OnClickListener
            public void onClick(int i) {
                OrderItem orderItem = (OrderItem) UserWorkSheetListActivity.this.groupStatusList.get(i);
                if (UserWorkSheetListActivity.this.groupStatus.size() == 1 && ((OrderItem) UserWorkSheetListActivity.this.groupStatus.get(0)).getName().equals(orderItem.getName())) {
                    return;
                }
                if (UserWorkSheetListActivity.this.groupStatus.contains(orderItem)) {
                    UserWorkSheetListActivity.this.groupStatus.remove(orderItem);
                } else {
                    UserWorkSheetListActivity.this.groupStatus.add(orderItem);
                }
                TextView textView = UserWorkSheetListActivity.this.tvStatusHeader;
                UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
                textView.setText(userWorkSheetListActivity.getGroupListName(userWorkSheetListActivity.groupStatus));
                UserWorkSheetListActivity.this.getWorkSheetList();
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$11 */
    public class AnonymousClass11 implements View.OnClickListener {
        public AnonymousClass11() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int size = UserWorkSheetListActivity.this.groupStatusList.size();
            String[] strArr = new String[size];
            for (int i = 0; i < size; i++) {
                strArr[i] = ((OrderItem) UserWorkSheetListActivity.this.groupStatusList.get(i)).getName();
            }
            UserWorkSheetListActivity.this.popupMenuStatus.setItems(strArr);
            FullPopupMenu fullPopupMenu = UserWorkSheetListActivity.this.popupMenuStatus;
            UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
            fullPopupMenu.setSelectPosition(userWorkSheetListActivity.getGroupSortPosition((List<OrderItem>) userWorkSheetListActivity.groupStatusList, (List<OrderItem>) UserWorkSheetListActivity.this.groupStatus));
            UserWorkSheetListActivity.this.popupMenuStatus.showAsDropDown(UserWorkSheetListActivity.this.llStatus);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.UserWorkSheetListActivity$12 */
    public class AnonymousClass12 implements FullPopupMenu.OnClickListener {
        public AnonymousClass12() {
        }

        @Override // com.qiyukf.unicorn.widget.FullPopupMenu.OnClickListener
        public void onClick(int i) {
            OrderItem orderItem = (OrderItem) UserWorkSheetListActivity.this.groupStatusList.get(i);
            if (UserWorkSheetListActivity.this.groupStatus.size() == 1 && ((OrderItem) UserWorkSheetListActivity.this.groupStatus.get(0)).getName().equals(orderItem.getName())) {
                return;
            }
            if (UserWorkSheetListActivity.this.groupStatus.contains(orderItem)) {
                UserWorkSheetListActivity.this.groupStatus.remove(orderItem);
            } else {
                UserWorkSheetListActivity.this.groupStatus.add(orderItem);
            }
            TextView textView = UserWorkSheetListActivity.this.tvStatusHeader;
            UserWorkSheetListActivity userWorkSheetListActivity = UserWorkSheetListActivity.this;
            textView.setText(userWorkSheetListActivity.getGroupListName(userWorkSheetListActivity.groupStatus));
            UserWorkSheetListActivity.this.getWorkSheetList();
        }
    }

    public int getGroupSortPosition(List<OrderItem> list, OrderItem orderItem) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSortBy().equals(orderItem.getSortBy())) {
                return i;
            }
        }
        return 0;
    }

    public List<Integer> getGroupSortPosition(List<OrderItem> list, List<OrderItem> list2) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            for (int i2 = 0; i2 < list2.size(); i2++) {
                if (list.get(i).getSortBy().equals(list2.get(i2).getSortBy())) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
        }
        return arrayList;
    }

    public String getGroupListName(List<OrderItem> list) {
        if (list.size() == this.groupStatusList.size()) {
            return getString(R.string.ysf_work_sheet_status);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getName());
            if (i != list.size() - 1) {
                sb.append(ChineseToPinyinResource.Field.COMMA);
            }
        }
        return sb.toString();
    }

    private String getGroupListSortBy(List<OrderItem> list) {
        if (list.size() == this.groupStatusList.size()) {
            return "5,10,20,25";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getSortBy());
            if (i != list.size() - 1) {
                sb.append(ChineseToPinyinResource.Field.COMMA);
            }
        }
        return sb.toString();
    }

    public String getGroupName(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return orderItem.getName();
    }

    public void setCancelVisible() {
        this.etSearch.setCursorVisible(true);
        this.mCancel.setVisibility(0);
    }

    public void cancelKeyBoard() {
        this.ivDelete.setVisibility(8);
        this.mCancel.setVisibility(8);
        this.etSearch.setCursorVisible(false);
        g.a(this);
    }

    public void getWorkSheetList() {
        if (!o.a(this)) {
            t.b(R.string.ysf_network_unable);
            return;
        }
        if (TextUtils.isEmpty(com.qiyukf.unicorn.d.c.a())) {
            t.b(R.string.ysf_current_state_cannot_get_worksheet_list);
            return;
        }
        showProgressDialog(getString(R.string.ysf_loading_str));
        aa aaVar = new aa();
        aaVar.a(com.qiyukf.unicorn.d.c.a());
        ArrayList<Long> arrayList = this.templateIds;
        if (arrayList != null && arrayList.size() > 0) {
            aaVar.a(this.templateIds);
        }
        aaVar.a(com.qiyukf.unicorn.c.h().d(this.exchange));
        aaVar.b("Android");
        aaVar.g("2500");
        aaVar.c(this.groupSort.getSortBy());
        aaVar.e(getGroupListSortBy(this.groupStatus));
        aaVar.f(this.etSearch.getText().toString().trim());
        aaVar.d(SocialConstants.PARAM_APP_DESC);
        com.qiyukf.unicorn.k.c.a(aaVar, this.exchange);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        registerObserver(false);
    }

    public /* synthetic */ void lambda$new$9fdcadc3$1(CustomNotification customNotification) {
        b attachStr;
        if (customNotification.getSessionType() == SessionTypeEnum.Ysf && (attachStr = b.parseAttachStr(customNotification.getContent())) != null && (attachStr instanceof ak)) {
            ak akVar = (ak) attachStr;
            String strB = akVar.b();
            if (akVar.a() && !TextUtils.isEmpty(strB)) {
                WorkSheetHelper.goToThirdSystemWorkSheetUrl(this, strB);
                dismissProgressDialog();
                finish();
                return;
            }
            setUI(akVar);
        }
    }

    private void setUI(ak akVar) {
        dismissProgressDialog();
        if (akVar == null) {
            t.b(R.string.ysf_get_worksheet_list_fail);
            this.ysfPtlWorkSheetListParent.refreshFinish(0);
            return;
        }
        if (akVar.d() != 200) {
            this.ysfPtlWorkSheetListParent.setVisibility(8);
            this.ysfTvWorkSheetListEmpty.setVisibility(0);
            this.ysf_tv_work_sheet_list_total.setText(getString(R.string.ysf_work_sheet_list_count, 0));
            return;
        }
        this.ysfPtlWorkSheetListParent.refreshFinish(0);
        this.workSheetList.clear();
        if (akVar.c() != null && akVar.c().size() > 0) {
            this.ysfPtlWorkSheetListParent.setVisibility(0);
            this.ysfTvWorkSheetListEmpty.setVisibility(8);
            this.workSheetList.addAll(akVar.c());
            this.workSheetListAdapter.notifyDataSetChanged();
            this.ysf_tv_work_sheet_list_total.setText(getString(R.string.ysf_work_sheet_list_count, Integer.valueOf(akVar.c().size())));
            return;
        }
        this.ysfPtlWorkSheetListParent.setVisibility(8);
        this.ysfTvWorkSheetListEmpty.setVisibility(0);
        this.ysf_tv_work_sheet_list_total.setText(getString(R.string.ysf_work_sheet_list_count, 0));
    }

    private void registerObserver(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.customNotificationObserver, z);
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
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.progressDialog.dismiss();
    }

    public static class OrderItem {
        String name;
        String sortBy;

        public String getSortBy() {
            return this.sortBy;
        }

        public void setSortBy(String str) {
            this.sortBy = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public OrderItem(String str, String str2) {
            this.sortBy = str;
            this.name = str2;
        }
    }
}

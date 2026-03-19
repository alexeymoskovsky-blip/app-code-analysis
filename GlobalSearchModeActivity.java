package com.petkit.android.activities.community;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.fragment.app.FragmentTransaction;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.community.adapter.GlobalSearchAdapter;
import com.petkit.android.activities.community.adapter.render.GlobalSearchBaseRender;
import com.petkit.android.activities.community.fragment.GlobalSearchItemFragment;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.widget.ClearEditText;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GlobalSearchModeActivity extends BaseActivity implements GlobalSearchItemFragment.OnSearchItemClickListener, TextWatcher, GlobalSearchBaseRender.OnSearchMoreListener {
    private static final String SEARCH_TYPE_PET = "pet";
    private static final String SEARCH_TYPE_POST = "post";
    private static final String SEARCH_TYPE_TOPIC = "topic";
    private static final String SEARCH_TYPE_USER = "user";
    private LinearLayout contaner;
    private GlobalSearchItemFragment fragment;
    protected String lastTime;
    Button mCancelSearchMode;
    ClearEditText mClearEditText;
    List<Object> mList;
    GlobalSearchAdapter searchAdapter;
    protected String searchKey;
    protected View searchModeView;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_global_search_content);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        this.contaner = (LinearLayout) findViewById(R.id.global_search_content);
        initView();
    }

    private void initView() {
        setNoTitle();
        setTopView(R.layout.layout_search_header);
        View view = this.mTopView;
        this.searchModeView = view;
        ClearEditText clearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);
        this.mClearEditText = clearEditText;
        clearEditText.setGravityLeft(true);
        this.mClearEditText.requestFocus();
        this.mClearEditText.addTextChangedListener(this);
        Button button = (Button) this.searchModeView.findViewById(R.id.cancel_search);
        this.mCancelSearchMode = button;
        button.setOnClickListener(this);
        this.mCancelSearchMode.setVisibility(0);
        setViewState(1);
        showSoftInput(this.mClearEditText);
        setContentFragment();
        this.mList = new ArrayList();
        this.searchAdapter = new GlobalSearchAdapter(this);
    }

    private void setContentFragment() {
        if (this.fragment == null) {
            this.fragment = new GlobalSearchItemFragment();
        }
        addContentFragment();
    }

    private void addContentFragment() {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.add(R.id.re_basecontent, this.fragment);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeContentFragment() {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        GlobalSearchItemFragment globalSearchItemFragment = this.fragment;
        if (globalSearchItemFragment != null && globalSearchItemFragment.isVisible()) {
            fragmentTransactionBeginTransaction.remove(this.fragment);
        }
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    private void doSearch(String str) {
        searchGlobal(str);
    }

    private void searchGlobal(String str) {
        HashMap map = new HashMap();
        map.put("query", str);
        map.put("lastKey", this.lastTime);
        cancenRequest(true);
        post(ApiTools.SAMPLE_API_SEARCH_ALL, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.community.GlobalSearchModeActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:41:0x00a8  */
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onSuccess(int r9, cz.msebera.android.httpclient.Header[] r10, byte[] r11) {
                /*
                    Method dump skipped, instruction units count: 428
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.community.GlobalSearchModeActivity.AnonymousClass1.onSuccess(int, cz.msebera.android.httpclient.Header[], byte[]):void");
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                GlobalSearchModeActivity.this.setViewState(2);
            }
        }, false);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.cancel_search) {
            finish();
            overridePendingTransition(0, android.R.anim.fade_out);
        }
    }

    @Override // com.petkit.android.activities.community.fragment.GlobalSearchItemFragment.OnSearchItemClickListener
    public void searchPetOwner() {
        startActivity(new Intent(this, (Class<?>) UserSearchModeActivity.class));
        overridePendingTransition(R.anim.search_fade_in, 0);
        new HashMap().put("user", "1");
    }

    @Override // com.petkit.android.activities.community.fragment.GlobalSearchItemFragment.OnSearchItemClickListener
    public void searchPet() {
        startActivity(new Intent(this, (Class<?>) PetSearchModeActivity.class));
        overridePendingTransition(R.anim.search_fade_in, 0);
        new HashMap().put(SEARCH_TYPE_PET, "1");
    }

    @Override // com.petkit.android.activities.community.fragment.GlobalSearchItemFragment.OnSearchItemClickListener
    public void searchPetPost() {
        startActivity(new Intent(this, (Class<?>) PostSearchModeActivity.class));
        overridePendingTransition(R.anim.search_fade_in, 0);
        new HashMap().put(SEARCH_TYPE_POST, "1");
    }

    @Override // com.petkit.android.activities.community.fragment.GlobalSearchItemFragment.OnSearchItemClickListener
    public void searchPetTopic() {
        startActivity(new Intent(this, (Class<?>) TopicSearchModeActivity.class));
        overridePendingTransition(R.anim.search_fade_in, 0);
        new HashMap().put("topic", "1");
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String string = charSequence.toString();
        this.searchKey = string;
        this.lastTime = null;
        if (!isEmpty(string)) {
            setViewState(0);
            removeContentFragment();
            doSearch(this.searchKey);
        } else {
            this.contaner.removeAllViews();
            setViewState(1);
            addContentFragment();
        }
    }

    @Override // com.petkit.android.activities.community.adapter.render.GlobalSearchBaseRender.OnSearchMoreListener
    public String getSearchKey() {
        return this.searchKey;
    }
}

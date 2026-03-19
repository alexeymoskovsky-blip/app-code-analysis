package com.petkit.android.activities.remind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.remind.HealthRemindEmptyFragment;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.RemindDetailsListRsp;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes5.dex */
public class HealthRemindActivity extends BaseListActivity implements HealthRemindEmptyFragment.IHealthRemindEmptyListener {
    public static final int REMIND_TYPE_HISTORY = 1;
    public static final int REMIND_TYPE_NORMAL = 0;
    private String lastKey = null;
    private HealthRemindEmptyFragment mHealthRemindEmptyFragment;
    private int mRemindListType;
    private String petId;
    private TextView tvHistory;

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.petId = bundle.getString(Constants.EXTRA_PET_ID);
            this.mRemindListType = bundle.getInt(Constants.EXTRA_TYPE);
        } else {
            this.petId = getIntent().getStringExtra(Constants.EXTRA_PET_ID);
            this.mRemindListType = getIntent().getIntExtra(Constants.EXTRA_TYPE, 0);
        }
        EventBus.getDefault().register(this);
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_PET_ID, this.petId);
        bundle.putInt(Constants.EXTRA_TYPE, this.mRemindListType);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onRemindUpdated(RemindUpdatedEvent remindUpdatedEvent) {
        this.lastKey = null;
        this.mListAdapter.setList(new ArrayList());
        setViewState(0);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
        getRemindDetailList();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitleLineVisibility(8);
        setTitle(this.mRemindListType == 0 ? R.string.Smart_reminder : R.string.History_reminder);
        if (this.mRemindListType == 0) {
            setTitleRightButton(getString(R.string.History_reminder), getResources().getColor(R.color.login_new_blue), this);
            setTitle(this.mRemindListType == 0 ? R.string.Smart_reminder : R.string.History_reminder);
            setBottomView(R.layout.layout_list_footerview_prompt);
            TextView textView = (TextView) findViewById(R.id.footer_prompt);
            this.tvHistory = textView;
            textView.setText(R.string.Add_reminder);
            this.tvHistory.setOnClickListener(this);
        }
        getRemindDetailList();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        RemindDetailListAdapter remindDetailListAdapter = new RemindDetailListAdapter(this, null, this.mRemindListType);
        this.mListAdapter = remindDetailListAdapter;
        this.mListView.setAdapter((ListAdapter) remindDetailListAdapter);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getRemindDetailList();
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        this.lastKey = null;
        getRemindDetailList();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intentNewIntent;
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount < 0 || headerViewsCount >= this.mListAdapter.getCount()) {
            return;
        }
        if (this.mRemindListType == 1) {
            intentNewIntent = AddRemindActivity.newIntent(this, (RemindDetail) this.mListAdapter.getItem(headerViewsCount), 3);
        } else {
            intentNewIntent = AddRemindActivity.newIntent(this, (RemindDetail) this.mListAdapter.getItem(headerViewsCount), 2);
        }
        startActivityForResult(intentNewIntent, 1);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_right_btn) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.EXTRA_TYPE, 1);
            startActivityWithData(HealthRemindActivity.class, bundle, false);
        } else if (id == R.id.footer_prompt || id == R.id.remind_add) {
            Intent intent = new Intent(this, (Class<?>) AddRemindTypeActivity.class);
            intent.putExtra(Constants.EXTRA_PET_ID, this.petId);
            startActivityForResult(intent, 1);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getRemindDetailList();
    }

    private void getRemindDetailList() {
        HashMap map = new HashMap();
        map.put("lastKey", this.lastKey);
        map.put("limit", String.valueOf(20));
        post(this.mRemindListType == 0 ? ApiTools.SAMPLE_API_SCHEDULE_SCHEDULES : ApiTools.SAMPLE_API_SCHEDULE_HISTORY, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.remind.HealthRemindActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                HealthRemindActivity.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, R.string.Hint_error_text_network_lost, 0);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                HealthRemindActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RemindDetailsListRsp remindDetailsListRsp = (RemindDetailsListRsp) this.gson.fromJson(this.responseResult, RemindDetailsListRsp.class);
                if (remindDetailsListRsp.getError() != null) {
                    if (HealthRemindActivity.this.lastKey == null) {
                        HealthRemindActivity.this.setViewState(2);
                    }
                    HealthRemindActivity.this.showLongToast(remindDetailsListRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (remindDetailsListRsp.getResult() != null) {
                    HealthRemindActivity.this.setViewState(1);
                    if (HealthRemindActivity.this.mRemindListType != 1 || (remindDetailsListRsp.getResult().getList() != null && remindDetailsListRsp.getResult().getList().size() != 0)) {
                        if (HealthRemindActivity.this.lastKey != null) {
                            ((BaseListActivity) HealthRemindActivity.this).mListAdapter.addList(remindDetailsListRsp.getResult().getList());
                        } else if (remindDetailsListRsp.getResult().getList() == null || remindDetailsListRsp.getResult().getList().size() == 0) {
                            HealthRemindActivity.this.setViewState(3);
                            if (HealthRemindActivity.this.mHealthRemindEmptyFragment == null) {
                                HealthRemindActivity.this.mHealthRemindEmptyFragment = new HealthRemindEmptyFragment();
                            }
                            HealthRemindActivity healthRemindActivity = HealthRemindActivity.this;
                            healthRemindActivity.setFragment(healthRemindActivity.mHealthRemindEmptyFragment);
                            if (HealthRemindActivity.this.tvHistory != null) {
                                HealthRemindActivity.this.tvHistory.setVisibility(8);
                            }
                        } else {
                            if (HealthRemindActivity.this.mHealthRemindEmptyFragment != null) {
                                HealthRemindActivity healthRemindActivity2 = HealthRemindActivity.this;
                                healthRemindActivity2.removeFragment(healthRemindActivity2.mHealthRemindEmptyFragment);
                            }
                            ((BaseListActivity) HealthRemindActivity.this).mListAdapter.setList(remindDetailsListRsp.getResult().getList());
                            if (HealthRemindActivity.this.tvHistory != null) {
                                HealthRemindActivity.this.tvHistory.setVisibility(0);
                            }
                        }
                        HealthRemindActivity.this.lastKey = remindDetailsListRsp.getResult().getLastKey();
                        return;
                    }
                    if (HealthRemindActivity.this.lastKey == null) {
                        HealthRemindActivity.this.setStateFailOrEmpty(R.drawable.icon_remind_empty, R.string.Hint_empty_text_history_reminder, 0);
                    }
                }
            }
        }, false);
    }

    @Override // com.petkit.android.activities.remind.HealthRemindEmptyFragment.IHealthRemindEmptyListener
    public void onAddHealthRemind() {
        Intent intent = new Intent(this, (Class<?>) AddRemindTypeActivity.class);
        intent.putExtra(Constants.EXTRA_PET_ID, this.petId);
        startActivityForResult(intent, 1);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

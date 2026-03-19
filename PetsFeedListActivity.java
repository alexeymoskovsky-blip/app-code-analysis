package com.petkit.android.activities.feed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.feed.adapter.PetsFeedListAdapter;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.MyOwnPetsRsp;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class PetsFeedListActivity extends BaseListActivity {
    private BroadcastReceiver mBroadcastReceiver;

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Title_pets_list);
        setTitleLineVisibility(8);
        setBottomView(R.layout.layout_add_pet);
        findViewById(R.id.bottom_view_id).setOnClickListener(this);
        this.mBottomView.setVisibility(8);
        getPetsList();
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        PetsFeedListAdapter petsFeedListAdapter = new PetsFeedListAdapter(this, null);
        this.mListAdapter = petsFeedListAdapter;
        this.mListView.setAdapter((ListAdapter) petsFeedListAdapter);
        this.mListView.setDividerHeight(0);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.bottom_view_id) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.EXTRA_BOOLEAN, true);
            bundle.putBoolean(Constants.EXTRA_FROM_DEVICE, false);
            startActivityWithData(PetCreateActivity.class, bundle, false);
        }
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getPetsList();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        startActivity(HealthyFeedActivity.newIntent(this, ((MyOwnPetsRsp.MyOwnPetsResult) this.mListAdapter.getItem(i)).getPet().getId(), false));
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        getPetsList();
    }

    private void getPetsList() {
        HashMap map = new HashMap();
        map.put(Consts.PET_GROUP_ID, String.valueOf(FamilyUtils.getInstance().getCurrentFamilyId(this)));
        post(ApiTools.SAMPLE_API_PET_HEALTHFEEDINGS, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.feed.PetsFeedListActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                PetsFeedListActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                MyOwnPetsRsp myOwnPetsRsp = (MyOwnPetsRsp) this.gson.fromJson(this.responseResult, MyOwnPetsRsp.class);
                if (myOwnPetsRsp.getError() != null) {
                    PetsFeedListActivity.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, myOwnPetsRsp.getError().getMsg(), 0);
                    return;
                }
                if (myOwnPetsRsp.getResult().size() == 0) {
                    ((BaseListActivity) PetsFeedListActivity.this).mListAdapter.clearList();
                    PetsFeedListActivity.this.setStateFailOrEmpty(R.drawable.pet_list_empty, R.string.Confirm_add_new_dog, 0);
                    ((BaseActivity) PetsFeedListActivity.this).mBottomView.setVisibility(0);
                } else {
                    ((BaseActivity) PetsFeedListActivity.this).mBottomView.setVisibility(8);
                    PetsFeedListActivity.this.setViewState(1);
                    ((BaseListActivity) PetsFeedListActivity.this).mListAdapter.setList(myOwnPetsRsp.getResult());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetsFeedListActivity.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, R.string.Hint_network_failed, 0);
            }
        });
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.feed.PetsFeedListActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_DOG)) {
                    PetsFeedListActivity.this.startRefresh();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

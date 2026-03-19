package com.petkit.android.activities.personal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.fragment.BaseTopicsListFragment;
import com.petkit.android.activities.community.PersonalListActivity;
import com.petkit.android.activities.community.PersonalRecommendListActivity;
import com.petkit.android.activities.community.adapter.FollowersUsersListAdapter;
import com.petkit.android.activities.community.adapter.UsersListAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.UserDetailListRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class PersonalFollowsListActivity extends PersonalDetailListActivity {
    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
    }

    @Override // com.petkit.android.activities.community.PersonalListActivity, in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        super.onRefreshBegin(ptrFrameLayout);
        getFollowerList();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getFollowerList();
    }

    @Override // com.petkit.android.activities.community.PersonalListActivity, com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitleLineVisibility(8);
    }

    @Override // com.petkit.android.activities.community.PersonalListActivity, com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        if (z) {
            startActivityForResult(PersonalRecommendListActivity.class, 17);
        } else {
            super.onRefresh(false);
        }
    }

    @Override // com.petkit.android.activities.community.PersonalListActivity
    public void startGet() {
        getFollowerList();
    }

    @Override // com.petkit.android.activities.personal.PersonalDetailListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && ((i == 2097 || 2067 == i) && this.parameters.equals(CommonUtils.getCurrentUserId()))) {
            this.mPtrFrame.autoRefresh(true);
            this.mListView.setSelection(0);
        }
        if (i == 17) {
            super.onRefresh(false);
        }
    }

    @Override // com.petkit.android.activities.community.PersonalListActivity
    public int getTitleResourceId() {
        return this.parameters.equals(CommonUtils.getCurrentUserId()) ? R.string.My_follows : R.string.His_follows;
    }

    private void getFollowerList() {
        HashMap map = new HashMap();
        map.put(BaseTopicsListFragment.USERID, this.parameters);
        map.put("lastKey", this.lastTime);
        map.put("limit", String.valueOf(20));
        post(ApiTools.SAMPLE_API_FOLLOW_FOLLOWERS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.personal.PersonalFollowsListActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                ((BaseListActivity) PersonalFollowsListActivity.this).mPtrFrame.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                UserDetailListRsp userDetailListRsp = (UserDetailListRsp) this.gson.fromJson(this.responseResult, UserDetailListRsp.class);
                if (userDetailListRsp.getError() != null) {
                    if (((PersonalListActivity) PersonalFollowsListActivity.this).lastTime == null) {
                        PersonalFollowsListActivity.this.setViewState(2);
                        return;
                    } else {
                        PersonalFollowsListActivity.this.showShortToast(userDetailListRsp.getError().getMsg(), R.drawable.toast_failed);
                        return;
                    }
                }
                PersonalFollowsListActivity.this.setViewState(1);
                if (((BaseListActivity) PersonalFollowsListActivity.this).mListAdapter == null) {
                    ((BaseListActivity) PersonalFollowsListActivity.this).mListAdapter = new FollowersUsersListAdapter(PersonalFollowsListActivity.this, userDetailListRsp.getResult().getList());
                    if (((PersonalListActivity) PersonalFollowsListActivity.this).parameters.equals(CommonUtils.getCurrentUserId()) && (((BaseListActivity) PersonalFollowsListActivity.this).mListAdapter instanceof UsersListAdapter)) {
                        ((UsersListAdapter) ((BaseListActivity) PersonalFollowsListActivity.this).mListAdapter).setMyFollowers(true);
                    }
                    ((BaseListActivity) PersonalFollowsListActivity.this).mListView.setAdapter((ListAdapter) ((BaseListActivity) PersonalFollowsListActivity.this).mListAdapter);
                }
                PersonalFollowsListActivity personalFollowsListActivity = PersonalFollowsListActivity.this;
                if (!personalFollowsListActivity.isEmpty(((PersonalListActivity) personalFollowsListActivity).lastTime)) {
                    ((BaseListActivity) PersonalFollowsListActivity.this).mListAdapter.addList(userDetailListRsp.getResult().getList());
                } else if (userDetailListRsp.getResult().getList() == null || userDetailListRsp.getResult().getList().size() == 0) {
                    PersonalFollowsListActivity.this.setViewState(3);
                    if (((PersonalListActivity) PersonalFollowsListActivity.this).parameters.equals(UserInforUtils.getCurrentUserId(PersonalFollowsListActivity.this))) {
                        PersonalFollowsListActivity.this.setStateFailOrEmpty(R.drawable.default_follow_list_empty, R.string.Hint_empty_text_post_focus, R.string.Hint_empty_text_friends);
                    } else {
                        PersonalFollowsListActivity.this.setStateFailOrEmpty(R.drawable.default_follow_list_empty, R.string.Hint_empty_text_Ta_follow, 0);
                    }
                } else {
                    ((BaseListActivity) PersonalFollowsListActivity.this).mListAdapter.setList(userDetailListRsp.getResult().getList());
                }
                ((PersonalListActivity) PersonalFollowsListActivity.this).lastTime = userDetailListRsp.getResult().getLastKey();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (((PersonalListActivity) PersonalFollowsListActivity.this).lastTime == null) {
                    PersonalFollowsListActivity.this.setViewState(2);
                }
            }
        }, false);
    }
}

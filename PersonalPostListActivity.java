package com.petkit.android.activities.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.activities.base.fragment.BaseTopicsListFragment;
import com.petkit.android.activities.community.PostDetailActivity;
import com.petkit.android.activities.community.adapter.CommunityListAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.NormalListRsp;
import com.petkit.android.model.PostItem;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.ShareHelper;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class PersonalPostListActivity extends BaseListActivity {
    public static final int TYPE_COLLECT = 0;
    public static final int TYPE_POST = 1;
    private String apiUrl;
    private boolean isUserSelf = false;
    private String lastTime = null;
    private int type;
    private String userId;

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.userId = bundle.getString(Constants.EXTRA_USER_ID);
            this.type = bundle.getInt(Constants.EXTRA_TYPE, 1);
            this.isUserSelf = bundle.getBoolean(Constants.EXTRA_IS_USERSELF, false);
        } else {
            this.userId = getIntent().getStringExtra(Constants.EXTRA_USER_ID);
            this.type = getIntent().getIntExtra(Constants.EXTRA_TYPE, 1);
            this.isUserSelf = getIntent().getBooleanExtra(Constants.EXTRA_IS_USERSELF, false);
        }
        if (this.type == 1) {
            this.apiUrl = ApiTools.SAMPLE_API_POST_USER_POSTS;
        } else {
            this.apiUrl = ApiTools.SAMPLE_API_POST_USER_COLLECTIONS;
        }
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        ((CommunityListAdapter) this.mListAdapter).unRegisterBroadcastReceiver();
        this.mListAdapter = null;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_TYPE, this.type);
        bundle.putBoolean(Constants.EXTRA_IS_USERSELF, this.isUserSelf);
        bundle.putString(Constants.EXTRA_USER_ID, this.userId);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Ta_pet_posts);
        getList();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        CommunityListAdapter communityListAdapter = new CommunityListAdapter(this, new ArrayList(), 2);
        this.mListAdapter = communityListAdapter;
        this.mListView.setAdapter((ListAdapter) communityListAdapter);
        this.mListView.scrollBy(0, -1);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getList();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_NOTIFYDATASETCHANGED));
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onListViewScroll(AbsListView absListView, int i, int i2, int i3) {
        super.onListViewScroll(absListView, i, i2, i3);
        NormalBaseAdapter normalBaseAdapter = this.mListAdapter;
        if (normalBaseAdapter != null) {
            int playingPosition = ((CommunityListAdapter) normalBaseAdapter).getPlayingPosition();
            int headerViewsCount = i - this.mListView.getHeaderViewsCount();
            int lastVisiblePosition = absListView.getLastVisiblePosition() - this.mListView.getHeaderViewsCount();
            if (playingPosition != -1) {
                if (playingPosition < headerViewsCount || playingPosition > lastVisiblePosition) {
                    ((CommunityListAdapter) this.mListAdapter).setPlayingPosition(-1);
                }
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount >= this.mListAdapter.getCount() || !(this.mListAdapter.getItem(headerViewsCount) instanceof PostItem)) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) PostDetailActivity.class);
        intent.putExtra(Constants.EXTRA_POST_DATA, (PostItem) this.mListAdapter.getItem(headerViewsCount));
        startActivityForResult(intent, 255);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ShareHelper.dealWithOnActivityResult(this, i, i2, intent);
        if (i2 == -1 && i == 255) {
            refreshComplete();
            this.mListView.setSelection(0);
        }
    }

    private void getList() {
        HashMap map = new HashMap();
        map.put(BaseTopicsListFragment.USERID, this.userId);
        map.put("limit", String.valueOf(20));
        if (!isEmpty(this.lastTime)) {
            map.put("lastKey", this.lastTime);
        }
        post(this.apiUrl, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.personal.PersonalPostListActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                PersonalPostListActivity.this.refreshComplete();
                ((BaseListActivity) PersonalPostListActivity.this).mListView.scrollBy(0, -1);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                String string;
                super.onSuccess(i, headerArr, bArr);
                NormalListRsp normalListRsp = (NormalListRsp) this.gson.fromJson(this.responseResult, NormalListRsp.class);
                if (PersonalPostListActivity.this.isFinishing()) {
                    return;
                }
                if (normalListRsp.getError() != null) {
                    PersonalPostListActivity personalPostListActivity = PersonalPostListActivity.this;
                    if (personalPostListActivity.isEmpty(personalPostListActivity.lastTime)) {
                        PersonalPostListActivity.this.setStateFailOrEmpty();
                    }
                    PersonalPostListActivity.this.showLongToast(normalListRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (normalListRsp.getResult() != null) {
                    PersonalPostListActivity personalPostListActivity2 = PersonalPostListActivity.this;
                    int i2 = 0;
                    if (!personalPostListActivity2.isEmpty(personalPostListActivity2.lastTime) || (normalListRsp.getResult().getList() != null && normalListRsp.getResult().getList().size() != 0)) {
                        PersonalPostListActivity.this.setStateNormal();
                        PersonalPostListActivity personalPostListActivity3 = PersonalPostListActivity.this;
                        if (personalPostListActivity3.isEmpty(personalPostListActivity3.lastTime)) {
                            ArrayList arrayList = new ArrayList();
                            while (i2 < normalListRsp.getResult().getList().size()) {
                                arrayList.add(normalListRsp.getResult().getList().get(i2));
                                i2++;
                            }
                            ((BaseListActivity) PersonalPostListActivity.this).mListAdapter.setList(arrayList);
                        } else {
                            ArrayList arrayList2 = new ArrayList();
                            while (i2 < normalListRsp.getResult().getList().size()) {
                                arrayList2.add(normalListRsp.getResult().getList().get(i2));
                                i2++;
                            }
                            ((BaseListActivity) PersonalPostListActivity.this).mListAdapter.addList(arrayList2);
                        }
                    } else if (PersonalPostListActivity.this.type == 1) {
                        if (PersonalPostListActivity.this.isUserSelf) {
                            string = PersonalPostListActivity.this.getString(R.string.Hint_empty_text_my_posted_post) + "\n" + PersonalPostListActivity.this.getString(R.string.Hint_empty_action_posted_post);
                        } else {
                            string = PersonalPostListActivity.this.getString(R.string.Hint_empty_text_user_posted_post);
                        }
                        PersonalPostListActivity.this.setStateFailOrEmpty(R.drawable.icon_my_post_none, string, 0);
                    } else {
                        PersonalPostListActivity personalPostListActivity4 = PersonalPostListActivity.this;
                        personalPostListActivity4.setStateFailOrEmpty(R.drawable.icon_my_collect_none, personalPostListActivity4.isUserSelf ? R.string.Hint_empty_text_my_collected_post : R.string.Hint_empty_text_user_collected_post, 0);
                    }
                    PersonalPostListActivity.this.lastTime = normalListRsp.getResult().getLastKey();
                }
            }
        }, false);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setStateLoad();
        this.lastTime = null;
        getList();
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        this.lastTime = null;
        getList();
    }
}

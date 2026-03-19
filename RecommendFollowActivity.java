package com.petkit.android.activities.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.community.adapter.RecommendFollowUsersAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.api.http.apiResponse.UserRecommendListRsp;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.UserDetail;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes4.dex */
public class RecommendFollowActivity extends BaseListActivity {
    private List<UserDetail> mTempUserDetails = new ArrayList();
    private List<UserDetail> mUserDetails;

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Recommend_follow);
        setTitleRightButton(R.string.Skip, this);
        setNoTitleLeftButton();
        setBottomView(R.layout.layout_follow);
        findViewById(R.id.follow).setOnClickListener(this);
        getFollowRecommend();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        RecommendFollowUsersAdapter recommendFollowUsersAdapter = new RecommendFollowUsersAdapter(this, null);
        this.mListAdapter = recommendFollowUsersAdapter;
        this.mListView.setAdapter((ListAdapter) recommendFollowUsersAdapter);
        this.mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_right_btn) {
            setResult(-1);
            finishWithAnim();
            return;
        }
        if (id == R.id.follow) {
            follows();
            return;
        }
        if (id == R.id.follow_selector) {
            int iIntValue = ((Integer) view.getTag()).intValue();
            if (view.isSelected()) {
                view.setSelected(false);
                this.mTempUserDetails.remove(this.mUserDetails.get(iIntValue));
            } else {
                view.setSelected(true);
                this.mTempUserDetails.add(this.mUserDetails.get(iIntValue));
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    private void getFollowRecommend() {
        HashMap map = new HashMap();
        map.put("limit", Constants.VIA_REPORT_TYPE_WPA_STATE);
        post(ApiTools.SAMPLE_API_FOLLOW_RECOMMEND, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.home.RecommendFollowActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                if (((BaseListActivity) RecommendFollowActivity.this).mListAdapter == null || ((BaseListActivity) RecommendFollowActivity.this).mListAdapter.getCount() == 0) {
                    RecommendFollowActivity.this.setViewState(2);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                UserRecommendListRsp userRecommendListRsp = (UserRecommendListRsp) this.gson.fromJson(this.responseResult, UserRecommendListRsp.class);
                if (userRecommendListRsp.getError() != null || userRecommendListRsp.getResult() == null) {
                    return;
                }
                if (userRecommendListRsp.getError() != null) {
                    RecommendFollowActivity.this.showLongToast(userRecommendListRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (userRecommendListRsp.getResult() != null) {
                    RecommendFollowActivity.this.setViewState(1);
                    RecommendFollowActivity.this.mUserDetails = userRecommendListRsp.getResult();
                    ((BaseListActivity) RecommendFollowActivity.this).mListAdapter.setList(RecommendFollowActivity.this.mUserDetails);
                    RecommendFollowActivity.this.mTempUserDetails.addAll(RecommendFollowActivity.this.mUserDetails);
                    ((RecommendFollowUsersAdapter) ((BaseListActivity) RecommendFollowActivity.this).mListAdapter).setSelectedList(RecommendFollowActivity.this.mTempUserDetails);
                }
            }
        }, false);
    }

    private void follows() {
        HashMap map = new HashMap();
        StringBuilder sb = new StringBuilder();
        if (this.mTempUserDetails.size() > 0) {
            for (int i = 0; i < this.mTempUserDetails.size(); i++) {
                sb.append(this.mTempUserDetails.get(i).getUser().getId());
                sb.append(ChineseToPinyinResource.Field.COMMA);
            }
            map.put("followeeUserIds", sb.toString());
            post(ApiTools.SAMPLE_API_FOLLOW_FOLLOW2, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.home.RecommendFollowActivity.2
                @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                    super.onSuccess(i2, headerArr, bArr);
                    ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                    if (resultStringRsp.getError() != null) {
                        RecommendFollowActivity.this.showShortToast(resultStringRsp.getError().getMsg());
                        return;
                    }
                    if (resultStringRsp.getResult() == null || !resultStringRsp.getResult().equalsIgnoreCase("success")) {
                        return;
                    }
                    ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                    chatCenter.setFollowerscount(chatCenter.getFollowerscount() + RecommendFollowActivity.this.mTempUserDetails.size());
                    SugarRecord.save(chatCenter);
                    LocalBroadcastManager.getInstance(RecommendFollowActivity.this).sendBroadcast(new Intent(com.petkit.android.utils.Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
                    RecommendFollowActivity.this.finishWithAnim();
                }
            }, false);
            return;
        }
        showShortToast(R.string.Follow_no_choose_anyone);
    }

    public void finishWithAnim() {
        finish();
        overridePendingTransition(R.anim.slide_none, R.anim.slide_out_to_top);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finishWithAnim();
    }
}

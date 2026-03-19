package com.petkit.android.activities.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.share.internal.ShareConstants;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseListFragment;
import com.petkit.android.activities.base.BaseTabActivity;
import com.petkit.android.activities.base.fragment.BaseTopicsListFragment;
import com.petkit.android.activities.community.PostCommentInputActivity;
import com.petkit.android.activities.community.fragment.PostClassifyCollectListFragment;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.PostItem;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import com.viewpagerindicator.TabPageIndicator;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class MyCollectsActivity extends BaseTabActivity {
    @Override // com.petkit.android.activities.base.BaseTabActivity
    public void initAdapter() {
        this.mViewPager.setOffscreenPageLimit(2);
        this.pagerAdapter = new CollectsPagerAdapter(getSupportFragmentManager());
        setTitle(R.string.Collect);
        this.indicator.setOnTabReselectedListener(new TabPageIndicator.OnTabReselectedListener() { // from class: com.petkit.android.activities.personal.MyCollectsActivity.1
            @Override // com.viewpagerindicator.TabPageIndicator.OnTabReselectedListener
            public void onTabReselected(int i) {
                ((CollectsPagerAdapter) ((BaseTabActivity) MyCollectsActivity.this).pagerAdapter).moveToTop(i);
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.content_comments) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_POST_DATA, (PostItem) view.getTag());
            startActivityWithData(PostCommentInputActivity.class, bundle, false);
            overridePendingTransition(R.anim.zoom_in, 0);
        }
    }

    public void sendRemoveCollect(final PostItem postItem) {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, postItem.getId());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_REMOVE_COLLECT, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.personal.MyCollectsActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showShortToast(MyCollectsActivity.this, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PetkitToast.showShortToast(MyCollectsActivity.this, R.string.Uncollected);
                postItem.setMyCollect(0);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setCollectsCount(chatCenter.getCollectsCount() - 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(MyCollectsActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
                Intent intent = new Intent(Constants.BROADCAST_MSG_REMOVE_COLLECT);
                intent.putExtra(Constants.EXTRA_POST_ID, postItem.getId());
                LocalBroadcastManager.getInstance(MyCollectsActivity.this).sendBroadcast(intent);
            }
        }, false);
    }

    public class CollectsPagerAdapter extends FragmentPagerAdapter {
        public int[] allTitles;
        public List<BaseListFragment> fragments;

        public CollectsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.fragments = new ArrayList();
            this.allTitles = new int[]{R.string.Pet_post, R.string.Topic};
            init();
        }

        public void moveToTop(int i) {
            this.fragments.get(i).moveToTop();
        }

        public void refreshFragment(int i, boolean z) {
            this.fragments.get(i).onRefresh(z);
        }

        private void init() {
            PostClassifyCollectListFragment postClassifyCollectListFragment = new PostClassifyCollectListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(BaseTopicsListFragment.USERID, UserInforUtils.getCurrentUserId(MyCollectsActivity.this));
            postClassifyCollectListFragment.setArguments(bundle);
            this.fragments.add(postClassifyCollectListFragment);
            if (UserInforUtils.isPetPenSwitchOpen()) {
                BaseTopicsListFragment baseTopicsListFragment = new BaseTopicsListFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt(BaseTopicsListFragment.LISTTYPE, 1);
                bundle2.putInt(BaseTopicsListFragment.SUBTYPE, 0);
                baseTopicsListFragment.setArguments(bundle2);
                this.fragments.add(baseTopicsListFragment);
                MyCollectsActivity.this.setTabVisibility(0);
                return;
            }
            MyCollectsActivity.this.setTabVisibility(8);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            return MyCollectsActivity.this.getString(this.allTitles[i]);
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.fragments.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.fragments.size();
        }
    }
}

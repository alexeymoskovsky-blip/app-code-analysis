package com.petkit.android.activities.community;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.BaseSearchModeListActivity;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.activities.base.widget.PinnedHeaderListView;
import com.petkit.android.activities.base.widget.impl.IPinnedHeader;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.TopicSearchListRsp;
import com.petkit.android.model.Topic;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TopicSearchActivity extends BaseSearchModeListActivity {
    private String lastKey;
    private TopicSearchListAdapter mSearchListAdapter;
    private String searchKey = null;
    private TopicSearchListRsp topicSearchListRsp;

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
    }

    @Override // com.petkit.android.activities.base.BaseSearchModeListActivity, com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Participant_topic);
        findTopics();
        setSearchModeView();
        setEditTextHint(R.string.Create_search_topic);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        findTopics();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Topic topic;
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        TopicSearchListAdapter topicSearchListAdapter = this.mSearchListAdapter;
        if (topicSearchListAdapter != null) {
            topic = (Topic) topicSearchListAdapter.getItem(headerViewsCount);
        } else {
            NormalBaseAdapter normalBaseAdapter = this.mListAdapter;
            if (normalBaseAdapter != null) {
                topic = (Topic) normalBaseAdapter.getItem(headerViewsCount);
                ((TopicSearchListAdapter) this.mListAdapter).checkItemIsFirstGroup(headerViewsCount);
            } else {
                topic = null;
            }
        }
        if (topic != null) {
            setResultAndFinish(topic);
        }
    }

    @Override // com.petkit.android.activities.base.BaseSearchModeListActivity
    public void setSearchModeStatus(boolean z) {
        TopicSearchListRsp topicSearchListRsp;
        super.setSearchModeStatus(z);
        if (z || (topicSearchListRsp = this.topicSearchListRsp) == null) {
            return;
        }
        this.mSearchListAdapter = null;
        this.searchKey = null;
        this.lastKey = topicSearchListRsp.getResult().getTopics().getLastKey();
        setViewState(1);
        int size = this.topicSearchListRsp.getResult().getUsed() != null ? this.topicSearchListRsp.getResult().getUsed().size() : 0;
        this.topicSearchListRsp.getResult().getTopics().getList().addAll(0, this.topicSearchListRsp.getResult().getUsed());
        TopicSearchListAdapter topicSearchListAdapter = new TopicSearchListAdapter(this.topicSearchListRsp.getResult().getTopics().getList(), size);
        this.mListAdapter = topicSearchListAdapter;
        this.mListView.setAdapter((ListAdapter) topicSearchListAdapter);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.title_right_btn) {
            finish();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        this.lastKey = null;
        setViewState(0);
        findTopics();
    }

    @Override // com.petkit.android.activities.base.BaseSearchModeListActivity
    public void doSearch(String str) {
        this.searchKey = str;
        this.lastKey = null;
        if (isEmpty(str) && this.mListAdapter != null) {
            setViewState(1);
            this.mListView.setAdapter((ListAdapter) this.mListAdapter);
            this.mListAdapter.notifyDataSetChanged();
        } else {
            setViewState(0);
            findTopics();
        }
    }

    private void setResultAndFinish(Topic topic) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_TOPIC, topic);
        setResult(-1, intent);
        finish();
    }

    private void findTopics() {
        HashMap map = new HashMap();
        map.put("limit", "20");
        if (!isEmpty(this.searchKey)) {
            map.put("topicName", this.searchKey);
        }
        if (!isEmpty(this.lastKey)) {
            map.put("lastKey", this.lastKey);
        }
        cancenRequest(true);
        post(ApiTools.SAMPLE_API_POST_FIND_TOPICS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.community.TopicSearchActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                TopicSearchActivity topicSearchActivity = TopicSearchActivity.this;
                if (topicSearchActivity.isEmpty(topicSearchActivity.searchKey)) {
                    if (((BaseListActivity) TopicSearchActivity.this).mListAdapter == null || ((BaseListActivity) TopicSearchActivity.this).mListAdapter.getCount() == 0) {
                        TopicSearchActivity.this.setViewState(2);
                    }
                } else if (TopicSearchActivity.this.mSearchListAdapter == null || TopicSearchActivity.this.mSearchListAdapter.getCount() == 0) {
                    TopicSearchActivity.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, R.string.No_content_tap_to_reload, 0);
                }
                TopicSearchActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                TopicSearchActivity.this.showShortToast(R.string.Hint_network_failed);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                int size;
                super.onSuccess(i, headerArr, bArr);
                TopicSearchListRsp topicSearchListRsp = (TopicSearchListRsp) this.gson.fromJson(this.responseResult, TopicSearchListRsp.class);
                if (topicSearchListRsp.getError() != null || topicSearchListRsp.getResult() == null) {
                    return;
                }
                if (topicSearchListRsp.getError() != null) {
                    TopicSearchActivity.this.showLongToast(topicSearchListRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (topicSearchListRsp.getResult() != null) {
                    TopicSearchActivity.this.setViewState(1);
                    TopicSearchActivity topicSearchActivity = TopicSearchActivity.this;
                    if (topicSearchActivity.isEmpty(topicSearchActivity.searchKey)) {
                        if (TopicSearchActivity.this.topicSearchListRsp == null) {
                            TopicSearchActivity.this.topicSearchListRsp = topicSearchListRsp;
                            size = TopicSearchActivity.this.topicSearchListRsp.getResult().getUsed() != null ? TopicSearchActivity.this.topicSearchListRsp.getResult().getUsed().size() : 0;
                            TopicSearchActivity.this.topicSearchListRsp.getResult().getTopics().getList().addAll(0, TopicSearchActivity.this.topicSearchListRsp.getResult().getUsed());
                        } else {
                            size = -1;
                        }
                        if (((BaseListActivity) TopicSearchActivity.this).mListAdapter == null) {
                            ((BaseListActivity) TopicSearchActivity.this).mListAdapter = TopicSearchActivity.this.new TopicSearchListAdapter(topicSearchListRsp.getResult().getTopics().getList(), size);
                            ((BaseListActivity) TopicSearchActivity.this).mListView.setAdapter((ListAdapter) ((BaseListActivity) TopicSearchActivity.this).mListAdapter);
                        } else {
                            TopicSearchActivity topicSearchActivity2 = TopicSearchActivity.this;
                            if (!topicSearchActivity2.isEmpty(topicSearchActivity2.lastKey)) {
                                ((BaseListActivity) TopicSearchActivity.this).mListAdapter.addList(topicSearchListRsp.getResult().getTopics().getList());
                            } else {
                                ((BaseListActivity) TopicSearchActivity.this).mListAdapter.setList(topicSearchListRsp.getResult().getTopics().getList());
                            }
                        }
                    } else if (TopicSearchActivity.this.mSearchListAdapter == null) {
                        TopicSearchActivity.this.mSearchListAdapter = TopicSearchActivity.this.new TopicSearchListAdapter(topicSearchListRsp.getResult().getTopics().getList(), 0);
                        ((BaseListActivity) TopicSearchActivity.this).mListView.setAdapter((ListAdapter) TopicSearchActivity.this.mSearchListAdapter);
                    } else {
                        TopicSearchActivity topicSearchActivity3 = TopicSearchActivity.this;
                        if (!topicSearchActivity3.isEmpty(topicSearchActivity3.lastKey)) {
                            TopicSearchActivity.this.mSearchListAdapter.addList(topicSearchListRsp.getResult().getTopics().getList());
                        } else {
                            TopicSearchActivity.this.mSearchListAdapter.setTopics(topicSearchListRsp.getResult().getTopics().getList(), 0);
                        }
                    }
                    TopicSearchActivity.this.lastKey = topicSearchListRsp.getResult().getTopics().getLastKey();
                }
            }
        }, false);
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        this.lastKey = null;
        findTopics();
    }

    public class TopicSearchListAdapter extends LoadMoreBaseAdapter implements AbsListView.OnScrollListener, IPinnedHeader {
        private int usedTopicCount;

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
        }

        public TopicSearchListAdapter(List<Topic> list, int i) {
            super(TopicSearchActivity.this, null);
            this.usedTopicCount = i;
            setListPageSize(20);
            setList(list);
        }

        public void setTopics(List<Topic> list, int i) {
            this.usedTopicCount = i;
            setList(list);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
        public View getContentView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                view = LayoutInflater.from(TopicSearchActivity.this).inflate(R.layout.adapter_topic_search_list, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.topicIcon = (ImageView) view.findViewById(R.id.topic_image);
                viewHolder.topicName = (TextView) view.findViewById(R.id.topic_title);
                viewHolder.topicContent = (TextView) view.findViewById(R.id.topic_content);
                viewHolder.topicHeader = (TextView) view.findViewById(R.id.topic_search_header);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (i == 0 || isPinnedHeaderPushedUp(i - 1, i)) {
                viewHolder.topicHeader.setVisibility(0);
                if (!checkItemIsFirstGroup(i)) {
                    viewHolder.topicHeader.setText(R.string.Topic_recent_join);
                } else {
                    viewHolder.topicHeader.setText(R.string.Topic_all);
                }
            } else {
                viewHolder.topicHeader.setVisibility(8);
            }
            Topic topic = (Topic) getItem(i);
            if (i == 0 && !TextUtils.isEmpty(TopicSearchActivity.this.searchKey) && TextUtils.isEmpty(topic.getTopicId())) {
                viewHolder.topicContent.setText(R.string.Topic_add);
            } else {
                viewHolder.topicContent.setText(topic.getTopicname());
            }
            ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(topic.getImgs()).imageView(viewHolder.topicIcon).errorPic(R.drawable.default_topic_image_middle).build());
            viewHolder.topicName.setText(topic.getTopicname());
            return view;
        }

        public class ViewHolder {
            public TextView topicContent;
            public TextView topicHeader;
            public ImageView topicIcon;
            public TextView topicName;

            public ViewHolder() {
            }
        }

        @Override // com.petkit.android.activities.base.widget.impl.IPinnedHeader
        public int getPinnedHeaderState(int i) {
            if (getCount() == 0 || i < 0) {
                return 0;
            }
            return isPinnedHeaderPushedUp(i, i + 1) ? 2 : 1;
        }

        private boolean isPinnedHeaderPushedUp(int i, int i2) {
            return i2 < getCount() && i == this.usedTopicCount - 1;
        }

        @Override // com.petkit.android.activities.base.widget.impl.IPinnedHeader
        public void configurePinnedHeader(View view, int i) {
            TextView textView = (TextView) view.findViewById(R.id.pinned_header);
            textView.setBackgroundColor(Color.parseColor("#ffeaeaea"));
            textView.setTextColor(CommonUtils.getColorById(R.color.gray));
            if (this.usedTopicCount > i) {
                textView.setText(R.string.Topic_recent_join);
            } else {
                textView.setText(R.string.Topic_all);
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            if (absListView instanceof PinnedHeaderListView) {
                ((PinnedHeaderListView) absListView).configureHeaderView(i);
            }
        }

        public boolean checkItemIsFirstGroup(int i) {
            return i >= this.usedTopicCount;
        }
    }
}

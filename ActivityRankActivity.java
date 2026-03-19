package com.petkit.android.activities.fit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.facebook.CallbackManager;
import com.google.gson.Gson;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.fit.adapter.PetRankListAdapter;
import com.petkit.android.activities.personal.PersonalActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.DogRankRsp;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.DailyDetailItem;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DailyDetailUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.ShareHelper;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.widget.SharePopMenu;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.HashMap;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* JADX INFO: loaded from: classes3.dex */
@SuppressLint({"InflateParams"})
public class ActivityRankActivity extends BaseListActivity {
    public static int ACTIVITY_RANK = 1;
    public static int LAZY_RANK = 2;
    private CallbackManager callbackManager;
    private Pet curPet;
    private String day;
    private DailyDetailItem mDailyDetailItem;
    private View mHeaderView;
    private boolean isInitHeaderView = false;
    private int type = 0;
    private int rank = 0;
    private int score = 0;

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.day = bundle.getString(Constants.EXTRA_Characteristic_DAY);
            this.curPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
            this.type = bundle.getInt(Constants.EXTRA_TYPE);
            this.rank = bundle.getInt(Constants.EXTRA_RANK);
            this.score = bundle.getInt(Constants.EXTRA_SCORE);
        } else {
            this.day = getIntent().getStringExtra(Constants.EXTRA_Characteristic_DAY);
            this.curPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
            this.type = getIntent().getIntExtra(Constants.EXTRA_TYPE, 0);
            this.rank = getIntent().getIntExtra(Constants.EXTRA_RANK, 0);
            this.score = getIntent().getIntExtra(Constants.EXTRA_SCORE, 0);
        }
        this.mDailyDetailItem = DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), String.valueOf(this.day));
        this.callbackManager = CallbackManager.Factory.create();
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_Characteristic_DAY, this.day);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
        bundle.putInt(Constants.EXTRA_TYPE, this.type);
        bundle.putInt(Constants.EXTRA_RANK, this.rank);
        bundle.putInt(Constants.EXTRA_SCORE, this.score);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        CallbackManager callbackManager = this.callbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        }
        ShareHelper.dealWithOnActivityResult(this, i, i2, intent);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        int i;
        super.setupViews();
        setTitleLeftButton(R.drawable.btn_back_white);
        setDividerLineVisibility(8);
        setViewState(0);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setDividerHeight(0);
        this.mListView.setBackgroundResource(R.color.white);
        setTitleRightImageView(R.drawable.posts_share, this);
        if (this.type == ACTIVITY_RANK) {
            setTitle(R.string.Activity_rank, R.color.white);
            i = R.color.calorie_detail_bg;
            getActivityRank();
        } else {
            setTitle(R.string.Lazy_cat_rank, R.color.white);
            i = R.color.rest_detail_bg;
            getLazyRank();
        }
        setTitleBackgroundColor(CommonUtils.getColorById(i));
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        View viewInitHeaderView = initHeaderView();
        if (viewInitHeaderView != null) {
            this.mListView.addHeaderView(viewInitHeaderView);
        }
        PetRankListAdapter petRankListAdapter = new PetRankListAdapter(this, null, this.curPet.getId());
        this.mListAdapter = petRankListAdapter;
        this.mListView.setAdapter((ListAdapter) petRankListAdapter);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount >= 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_AUTHOR, ((DogRankRsp.DogRankResult) this.mListAdapter.getItem(headerViewsCount)).getDog().getOwner());
            startActivityWithData(PersonalActivity.class, bundle, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View initHeaderView() {
        SpannableStringUtils.SpanText spanText;
        if (this.isInitHeaderView) {
            return null;
        }
        this.isInitHeaderView = true;
        this.mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_rank_header, (ViewGroup) null);
        CommonUtils.getColorById(R.color.gray);
        TextView textView = (TextView) this.mHeaderView.findViewById(R.id.activity_rank_num);
        int i = this.rank;
        String strValueOf = i <= 0 ? "-" : String.valueOf(i);
        int i2 = this.rank;
        if (i2 == 1) {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_first), 2.2f);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.first_icon, 0, 0);
        } else if (i2 == 2) {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_second), 2.2f);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.second_icon, 0, 0);
        } else if (i2 == 3) {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_third), 2.2f);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.third_icon, 0, 0);
        } else {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.gray), 2.2f);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        textView.setText(SpannableStringUtils.makeSpannableString(spanText));
        ((TextView) this.mHeaderView.findViewById(R.id.pet_name)).setText(this.curPet.getName());
        ((TextView) this.mHeaderView.findViewById(R.id.activity_rank_des)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText("  " + getString(R.string.Comprehensive_score), CommonUtils.getColorById(R.color.gray), 0.8f), new SpannableStringUtils.SpanText("" + this.score, CommonUtils.getColorById(R.color.calorie_detail_bg), 0.9f)));
        ((TextView) this.mHeaderView.findViewById(R.id.activity_rank_tip)).setVisibility(8);
        this.mHeaderView.findViewById(R.id.activity_rank_gap_line).setVisibility(8);
        getWarmTips();
        return this.mHeaderView;
    }

    private void getWarmTips() {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        post(ApiTools.SAMPLE_API_ACTIVITY_WARMTIPS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.ActivityRankActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getResult() != null) {
                    TextView textView = (TextView) ActivityRankActivity.this.mHeaderView.findViewById(R.id.activity_rank_tip);
                    textView.setVisibility(0);
                    textView.setText(resultStringRsp.getResult());
                    ActivityRankActivity.this.mHeaderView.findViewById(R.id.activity_rank_gap_line).setVisibility(0);
                }
            }
        }, false);
    }

    private void getActivityRank() {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put("day", String.valueOf(this.day));
        map.put(IjkMediaPlayer.OnNativeInvokeListener.ARG_OFFSET, String.valueOf(0));
        map.put("limit", String.valueOf(100));
        post(ApiTools.SAMPLE_API_ACTIVITY_RANK, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.ActivityRankActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                ActivityRankActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                DogRankRsp dogRankRsp = (DogRankRsp) this.gson.fromJson(this.responseResult, DogRankRsp.class);
                if (dogRankRsp.getError() != null) {
                    ActivityRankActivity.this.showShortToast(dogRankRsp.getError().getMsg(), R.drawable.toast_failed);
                    if (((BaseListActivity) ActivityRankActivity.this).mListAdapter == null || ((BaseListActivity) ActivityRankActivity.this).mListAdapter.getCount() == 0) {
                        ActivityRankActivity.this.setViewState(2);
                        return;
                    }
                    return;
                }
                if (dogRankRsp.getResult() != null) {
                    ActivityRankActivity.this.setViewState(1);
                    ((BaseListActivity) ActivityRankActivity.this).mListAdapter.setList(dogRankRsp.getResult());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (((BaseListActivity) ActivityRankActivity.this).mListAdapter == null || ((BaseListActivity) ActivityRankActivity.this).mListAdapter.getCount() == 0) {
                    ActivityRankActivity.this.setViewState(2);
                }
            }
        }, false);
    }

    private void getLazyRank() {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put("day", String.valueOf(this.day));
        map.put(IjkMediaPlayer.OnNativeInvokeListener.ARG_OFFSET, String.valueOf(0));
        map.put("limit", String.valueOf(100));
        post(ApiTools.SAMPLE_API_ACTIVITY_CATRANK, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.ActivityRankActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                ActivityRankActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                DogRankRsp dogRankRsp = (DogRankRsp) this.gson.fromJson(this.responseResult, DogRankRsp.class);
                if (dogRankRsp.getError() != null) {
                    ActivityRankActivity.this.showShortToast(dogRankRsp.getError().getMsg(), R.drawable.toast_failed);
                    if (((BaseListActivity) ActivityRankActivity.this).mListAdapter == null || ((BaseListActivity) ActivityRankActivity.this).mListAdapter.getCount() == 0) {
                        ActivityRankActivity.this.setViewState(2);
                        return;
                    }
                    return;
                }
                if (dogRankRsp.getResult() != null) {
                    View viewInitHeaderView = ActivityRankActivity.this.initHeaderView();
                    if (viewInitHeaderView != null) {
                        ((BaseListActivity) ActivityRankActivity.this).mListView.addHeaderView(viewInitHeaderView);
                    }
                    ActivityRankActivity.this.setViewState(1);
                    ((BaseListActivity) ActivityRankActivity.this).mListAdapter.setList(dogRankRsp.getResult());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (((BaseListActivity) ActivityRankActivity.this).mListAdapter == null || ((BaseListActivity) ActivityRankActivity.this).mListAdapter.getCount() == 0) {
                    ActivityRankActivity.this.setViewState(2);
                }
            }
        }, false);
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        if (this.type == ACTIVITY_RANK) {
            getActivityRank();
        } else {
            getLazyRank();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.title_right_image) {
            int rank = this.type == ACTIVITY_RANK ? this.mDailyDetailItem.getRank() : this.mDailyDetailItem.getLazyrank();
            if (rank == 0) {
                LogcatStorageHelper.addLog("[WARNING] share rank info, rank is 0!!!   mDailyDetailItem: " + new Gson().toJson(this.mDailyDetailItem));
            }
            SharePopMenu sharePopMenu = new SharePopMenu(this, this.callbackManager);
            sharePopMenu.setData(3, getString(R.string.Activity_calorie_share_text, this.curPet.getName(), Integer.valueOf(rank)), this.curPet, this.day);
            sharePopMenu.show();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getActivityRank();
    }
}

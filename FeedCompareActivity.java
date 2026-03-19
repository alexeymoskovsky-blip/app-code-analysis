package com.petkit.android.activities.feed;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.feed.adapter.FeedCompareListAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.FeedPetRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class FeedCompareActivity extends BaseListActivity {
    private Pet curPet;
    private String lastKey = null;
    private boolean pound = false;

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.curPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
        } else {
            this.curPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
        }
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult.getSettings() != null) {
            this.pound = currentLoginResult.getSettings().getUnit() == 1;
        }
        super.onCreate(bundle);
        if (this.curPet == null) {
            finish();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(this.curPet.getCategory().getName());
        setTopView(R.layout.adapter_feed_compare);
        initListTopView();
        setViewState(0);
        getCompareList(this.lastKey);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        FeedCompareListAdapter feedCompareListAdapter = new FeedCompareListAdapter(this, null, this.pound);
        this.mListAdapter = feedCompareListAdapter;
        this.mListView.setAdapter((ListAdapter) feedCompareListAdapter);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getCompareList(this.lastKey);
    }

    private void initListTopView() {
        StringBuilder sb;
        double dDoubleValue;
        this.mTopView.setBackgroundColor(Color.parseColor("#ff1461a1"));
        ImageView imageView = (ImageView) findViewById(R.id.avatar);
        TextView textView = (TextView) findViewById(R.id.name);
        TextView textView2 = (TextView) findViewById(R.id.age);
        TextView textView3 = (TextView) findViewById(R.id.food);
        TextView textView4 = (TextView) findViewById(R.id.weight);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView2.setTextColor(getResources().getColor(R.color.white));
        textView3.setTextColor(Color.parseColor("#8ab7d0"));
        if (this.pound) {
            sb = new StringBuilder();
            sb.append("");
            dDoubleValue = CommonUtil.KgToLb(Double.valueOf(this.curPet.getWeight()).doubleValue());
        } else {
            sb = new StringBuilder();
            sb.append("");
            dDoubleValue = Double.valueOf(this.curPet.getWeight()).doubleValue();
        }
        sb.append(CommonUtil.doubleToDouble(dDoubleValue));
        textView4.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(sb.toString(), CommonUtils.getColorById(R.color.white), 2.0f), new SpannableStringUtils.SpanText(getString(this.pound ? R.string.Unit_lb : R.string.Unit_kilogram_short), CommonUtils.getColorById(R.color.white), 0.8f)));
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.curPet.getAvatar()).imageView(imageView).errorPic(this.curPet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        textView.setText(this.curPet.getName());
        textView2.setCompoundDrawablesWithIntrinsicBounds(this.curPet.getGender() == 1 ? R.drawable.gender_man : R.drawable.gender_women, 0, 0, 0);
        textView2.setText(CommonUtils.getSimplifyAgeByBirthday(this, this.curPet.getBirth()));
        textView3.setText(PetUtils.getPetFoodInfo(this, this.curPet));
    }

    private void getCompareList(String str) {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put("limit", "20");
        if (str != null) {
            map.put("lastKey", str);
        }
        post(ApiTools.SAMPLE_API_FEED_SIMILAR_WEIGHT, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feed.FeedCompareActivity.1
            public final /* synthetic */ String val$thisLastKey;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Activity this, String str2) {
                super(this);
                str = str2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                FeedCompareActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                FeedPetRsp feedPetRsp = (FeedPetRsp) this.gson.fromJson(this.responseResult, FeedPetRsp.class);
                if (feedPetRsp.getError() != null) {
                    if (str == null) {
                        FeedCompareActivity.this.setViewState(2);
                        return;
                    } else {
                        FeedCompareActivity.this.showShortToast(feedPetRsp.getError().getMsg());
                        return;
                    }
                }
                if (feedPetRsp.getResult() != null) {
                    FeedCompareActivity.this.setViewState(1);
                    if (str == null) {
                        ((BaseListActivity) FeedCompareActivity.this).mListAdapter.setList(feedPetRsp.getResult().getList());
                    } else {
                        ((BaseListActivity) FeedCompareActivity.this).mListAdapter.addList(feedPetRsp.getResult().getList());
                    }
                    FeedCompareActivity.this.lastKey = feedPetRsp.getResult().getLastKey();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                FeedCompareActivity.this.setViewState(2);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.feed.FeedCompareActivity$1 */
    public class AnonymousClass1 extends AsyncHttpRespHandler {
        public final /* synthetic */ String val$thisLastKey;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Activity this, String str2) {
            super(this);
            str = str2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFinish() {
            super.onFinish();
            FeedCompareActivity.this.refreshComplete();
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            FeedPetRsp feedPetRsp = (FeedPetRsp) this.gson.fromJson(this.responseResult, FeedPetRsp.class);
            if (feedPetRsp.getError() != null) {
                if (str == null) {
                    FeedCompareActivity.this.setViewState(2);
                    return;
                } else {
                    FeedCompareActivity.this.showShortToast(feedPetRsp.getError().getMsg());
                    return;
                }
            }
            if (feedPetRsp.getResult() != null) {
                FeedCompareActivity.this.setViewState(1);
                if (str == null) {
                    ((BaseListActivity) FeedCompareActivity.this).mListAdapter.setList(feedPetRsp.getResult().getList());
                } else {
                    ((BaseListActivity) FeedCompareActivity.this).mListAdapter.addList(feedPetRsp.getResult().getList());
                }
                FeedCompareActivity.this.lastKey = feedPetRsp.getResult().getLastKey();
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            super.onFailure(i, headerArr, bArr, th);
            FeedCompareActivity.this.setViewState(2);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        getCompareList(this.lastKey);
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        this.lastKey = null;
        getCompareList(null);
    }
}

package com.petkit.android.activities.fit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jess.arms.widget.InterceptScrollView;
import com.jjoe64.graphview.IInterceptListener;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.CharacteristicDetailRsp;
import com.petkit.android.model.DailyDetailItem;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DailyDetailUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.widget.CharacteristicDetailGraphView;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class CharacteristicConsumptionFragment extends BaseFragment implements IInterceptListener {
    public static final String CURPET = "curPet";
    public static final String DAILYDETAILITEM = "dailyDetailItem";
    public static final String DAY = "day";
    public static final String POUND = "pound";
    private Pet curPet;
    private String day;
    private CharacteristicDetailRsp detailResults;
    private DailyDetailItem mDailyDetailItem;
    private InterceptScrollView mScrollView;
    private boolean pound;

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        getArgs();
        initView();
        init();
        setNoTitle();
    }

    private void getArgs() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.day = arguments.getString("day");
            this.curPet = (Pet) arguments.getSerializable("curPet");
            this.mDailyDetailItem = (DailyDetailItem) arguments.getSerializable("dailyDetailItem");
            this.pound = arguments.getBoolean(POUND);
        }
    }

    private void initView() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.activity_consumption_detail, (ViewGroup) null);
        this.mScrollView = (InterceptScrollView) viewInflate.findViewById(R.id.scrollview);
        setContentView(viewInflate);
    }

    private void init() {
        this.mDailyDetailItem = DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), String.valueOf(this.day));
        setViewState(0);
        getDailyDetail();
    }

    public void refreshFood(String str) {
        this.mDailyDetailItem.setFood(str);
        refreshConsumptionDetailView();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.consumption_more_rl || this.detailResults == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) WebviewActivity.class);
        intent.putExtra(Constants.EXTRA_LOAD_PATH, ApiTools.getWebUrlByKey("activity_consume"));
        intent.putExtra(Constants.EXTRA_TITLE_COLOR, CommonUtils.getColorById(R.color.consume_detail_bg));
        getActivity().startActivity(intent);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getDailyDetail();
    }

    @Override // com.jjoe64.graphview.IInterceptListener
    public void changeInterceptState(boolean z) {
        this.mScrollView.setScrollingEnabled(!z);
        if (getActivity() instanceof IInterceptListener) {
            ((IInterceptListener) getActivity()).changeInterceptState(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initConsumptionDetailView() {
        int colorById = CommonUtils.getColorById(R.color.yellow);
        int colorById2 = CommonUtils.getColorById(R.color.gray);
        int colorById3 = CommonUtils.getColorById(R.color.black);
        ((TextView) getActivity().findViewById(R.id.consumption_calorie)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.valueOf(this.mDailyDetailItem.getCalorie()), colorById, 2.5f), new SpannableStringUtils.SpanText(getString(R.string.Unit_kcal), colorById, 0.8f)));
        ((TextView) getActivity().findViewById(R.id.basic_consumption)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Basic_Consumption) + "\n", colorById3, 1.0f), new SpannableStringUtils.SpanText(String.valueOf(this.mDailyDetailItem.getBasiccalorie()), colorById3, 2.0f), new SpannableStringUtils.SpanText(getString(R.string.Unit_kcal), colorById2, 0.8f)));
        ((TextView) getActivity().findViewById(R.id.add_tv)).setText("+");
        ((TextView) getActivity().findViewById(R.id.activity_consumption)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Activity_Consumption) + "\n", colorById3, 1.0f), new SpannableStringUtils.SpanText(String.valueOf(this.mDailyDetailItem.getActivitycalorie()), colorById3, 2.0f), new SpannableStringUtils.SpanText(getString(R.string.Unit_kcal), colorById2, 0.8f)));
        ((CharacteristicDetailGraphView) getActivity().findViewById(R.id.Charact_history_consumption_detail)).init(this.detailResults.getResult(), DateUtil.str2Date(this.day, DateUtil.DATE_FORMAT_7), 2, this);
        getActivity().findViewById(R.id.consumption_more_rl).setOnClickListener(this);
    }

    private void refreshConsumptionDetailView() {
        int colorById = CommonUtils.getColorById(R.color.yellow);
        int colorById2 = CommonUtils.getColorById(R.color.gray);
        int colorById3 = CommonUtils.getColorById(R.color.black);
        ((TextView) getActivity().findViewById(R.id.consumption_calorie)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.valueOf(this.mDailyDetailItem.getCalorie()), colorById, 2.5f), new SpannableStringUtils.SpanText(getString(R.string.Unit_kcal), colorById, 0.8f)));
        ((TextView) getActivity().findViewById(R.id.basic_consumption)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Basic_Consumption) + "\n", colorById3, 1.0f), new SpannableStringUtils.SpanText(String.valueOf(this.mDailyDetailItem.getBasiccalorie()), colorById3, 2.0f), new SpannableStringUtils.SpanText(getString(R.string.Unit_kcal), colorById2, 0.8f)));
        ((TextView) getActivity().findViewById(R.id.add_tv)).setText("+");
        ((TextView) getActivity().findViewById(R.id.activity_consumption)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Activity_Consumption) + "\n", colorById3, 1.0f), new SpannableStringUtils.SpanText(String.valueOf(this.mDailyDetailItem.getActivitycalorie()), colorById3, 2.0f), new SpannableStringUtils.SpanText(getString(R.string.Unit_kcal), colorById2, 0.8f)));
    }

    private void getDailyDetail() {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put("day", String.valueOf(this.day));
        post(ApiTools.SAMPLE_API_ACTIVITY_COLORIE_REPORT, map, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.fit.fragment.CharacteristicConsumptionFragment.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                CharacteristicDetailRsp characteristicDetailRsp = (CharacteristicDetailRsp) this.gson.fromJson(this.responseResult, CharacteristicDetailRsp.class);
                if (CharacteristicConsumptionFragment.this.getActivity().isFinishing()) {
                    return;
                }
                if (characteristicDetailRsp.getError() != null) {
                    CharacteristicConsumptionFragment.this.setViewState(2);
                    CharacteristicConsumptionFragment.this.setViewEmpty(R.drawable.default_list_empty_icon, R.string.Hint_network_failed, R.string.Retry);
                } else if (characteristicDetailRsp.getResult() != null) {
                    CharacteristicConsumptionFragment.this.setViewState(1);
                    CharacteristicConsumptionFragment.this.detailResults = characteristicDetailRsp;
                    CharacteristicConsumptionFragment.this.initConsumptionDetailView();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                CharacteristicConsumptionFragment.this.setViewState(2);
                CharacteristicConsumptionFragment.this.setViewEmpty(R.drawable.default_list_empty_icon, R.string.Hint_network_failed, R.string.Retry);
            }
        }, false);
    }
}

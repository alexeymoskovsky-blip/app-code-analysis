package com.petkit.android.activities.fit.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.material.timepicker.TimeModel;
import com.jess.arms.widget.InterceptScrollView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.IInterceptListener;
import com.jjoe64.graphview.RectGraphView;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.fit.ActivityRankActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.CharacteristicDetailRsp;
import com.petkit.android.model.DailyDetailItem;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DailyDetailUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.widget.CharacteristicDetailGraphView;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class CharacteristicRestFragment extends BaseFragment implements IInterceptListener {
    public static final String CURPET = "curPet";
    public static final String DAILYDETAILITEM = "dailyDetailItem";
    public static final String DAY = "day";
    private Pet curPet;
    private String day;
    private CharacteristicDetailRsp detailResults;
    private DailyDetailItem mDailyDetailItem;
    int mRectGraphType = 0;
    private InterceptScrollView mScrollView;

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        getArgs();
        initView();
        init();
        setNoTitle();
    }

    private void initView() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.activity_rest_detail, (ViewGroup) null);
        this.mScrollView = (InterceptScrollView) viewInflate.findViewById(R.id.scrollview);
        viewInflate.findViewById(R.id.data_rest_all).setOnClickListener(this);
        viewInflate.findViewById(R.id.data_rest_light).setOnClickListener(this);
        viewInflate.findViewById(R.id.data_rest_deep).setOnClickListener(this);
        setContentView(viewInflate);
    }

    private void init() {
        this.mDailyDetailItem = DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), String.valueOf(this.day));
        this.mRectGraphType = 0;
        setViewState(0);
        getDailyDetail();
    }

    private void getArgs() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.day = arguments.getString("day");
            this.curPet = (Pet) arguments.getSerializable("curPet");
            this.mDailyDetailItem = (DailyDetailItem) arguments.getSerializable("dailyDetailItem");
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getDailyDetail();
    }

    private void getDailyDetail() {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put("day", String.valueOf(this.day));
        post(ApiTools.SAMPLE_API_ACTIVITY_SLEEP_REPORT, map, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.fit.fragment.CharacteristicRestFragment.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                CharacteristicDetailRsp characteristicDetailRsp = (CharacteristicDetailRsp) this.gson.fromJson(this.responseResult, CharacteristicDetailRsp.class);
                if (CharacteristicRestFragment.this.getActivity().isFinishing()) {
                    return;
                }
                if (characteristicDetailRsp.getError() != null) {
                    CharacteristicRestFragment.this.setViewState(2);
                    CharacteristicRestFragment.this.setViewEmpty(R.drawable.default_list_empty_icon, R.string.Hint_network_failed, R.string.Retry);
                } else if (characteristicDetailRsp.getResult() != null) {
                    CharacteristicRestFragment.this.detailResults = characteristicDetailRsp;
                    if (!CharacteristicRestFragment.this.checkCurrentPetIsDog()) {
                        CharacteristicRestFragment.this.mDailyDetailItem.setLazyrank(CharacteristicRestFragment.this.detailResults.getResult().getLazyRank());
                        CharacteristicRestFragment.this.mDailyDetailItem.setLazyscore(CharacteristicRestFragment.this.detailResults.getResult().getLazyScore());
                        SugarRecord.save(CharacteristicRestFragment.this.mDailyDetailItem);
                    }
                    CharacteristicRestFragment.this.setViewState(1);
                    CharacteristicRestFragment.this.refreshRestDetailView();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                CharacteristicRestFragment.this.setViewState(2);
                CharacteristicRestFragment.this.setViewEmpty(R.drawable.default_list_empty_icon, R.string.Hint_network_failed, R.string.Retry);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshRestDetailView() {
        SpannableStringUtils.SpanText spanText;
        int colorById = CommonUtils.getColorById(R.color.gray);
        int colorById2 = CommonUtils.getColorById(R.color.black);
        TextView textView = (TextView) getActivity().findViewById(R.id.data_rest_time);
        int iConvertHourFromSeconds = CommonUtils.convertHourFromSeconds(this.mDailyDetailItem.getRest());
        int iConvertMinFromSeconds = CommonUtils.convertMinFromSeconds(this.mDailyDetailItem.getRest());
        textView.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertHourFromSeconds)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertHourFromSeconds > 1 ? R.string.Unit_hours : R.string.Unit_hour), colorById, 0.8f), new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertMinFromSeconds)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertMinFromSeconds > 1 ? R.string.Unit_minutes : R.string.Unit_minute), colorById, 0.8f)));
        initRestGrapView(this.mDailyDetailItem, this.mRectGraphType);
        ((CharacteristicDetailGraphView) getActivity().findViewById(R.id.Charact_history_rest_detail)).init(this.detailResults.getResult(), DateUtil.str2Date(this.day, DateUtil.DATE_FORMAT_7), 1, this);
        if (checkCurrentPetIsDog()) {
            getActivity().findViewById(R.id.data_rest_ll).setVisibility(0);
            getActivity().findViewById(R.id.rest_more_rl).setVisibility(0);
            getActivity().findViewById(R.id.rest_more_rl).setOnClickListener(this);
            getActivity().findViewById(R.id.lazy_rank).setVisibility(8);
            return;
        }
        getActivity().findViewById(R.id.data_rest_ll).setVisibility(8);
        getActivity().findViewById(R.id.rest_more_rl).setVisibility(8);
        getActivity().findViewById(R.id.lazy_rank).setVisibility(0);
        getActivity().findViewById(R.id.lazy_rank).setOnClickListener(this);
        TextView textView2 = (TextView) getActivity().findViewById(R.id.lazy_rank_num);
        String strValueOf = this.mDailyDetailItem.getLazyrank() <= 0 ? "-" : String.valueOf(this.mDailyDetailItem.getLazyrank());
        if (this.mDailyDetailItem.getLazyrank() == 1) {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_first), 2.0f);
        } else if (this.mDailyDetailItem.getLazyrank() == 2) {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_second), 2.0f);
        } else if (this.mDailyDetailItem.getLazyrank() == 3) {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_third), 2.0f);
        } else {
            spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.gray), 2.0f);
        }
        textView2.setText(SpannableStringUtils.makeSpannableString(spanText, new SpannableStringUtils.SpanText(" ", colorById, 1.0f)));
        ((TextView) getActivity().findViewById(R.id.lazy_rank_pet_name)).setText(this.curPet.getName());
        ((TextView) getActivity().findViewById(R.id.lazy_rank_des)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText("  " + getString(R.string.Comprehensive_score), CommonUtils.getColorById(R.color.gray), 0.8f), new SpannableStringUtils.SpanText("" + this.mDailyDetailItem.getLazyscore(), CommonUtils.getColorById(R.color.calorie_detail_bg), 0.9f)));
    }

    private void initRestGrapView(DailyDetailItem dailyDetailItem, int i) {
        RectGraphView rectGraphView = new RectGraphView(getActivity(), "", this, null);
        rectGraphView.setHorizontalLabels(new String[]{"0", Constants.VIA_SHARE_TYPE_INFO, "12", "18", Constants.VIA_REPORT_TYPE_CHAT_AIO});
        rectGraphView.getGraphViewStyle().setHorizontalLabelsColor(CommonUtils.getColorById(R.color.gray));
        if (dailyDetailItem != null && dailyDetailItem.getDeepsleeps() != null && dailyDetailItem.getDeepsleeps().size() > 0) {
            GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle = new GraphViewSeries.GraphViewSeriesStyle(CommonUtils.getColorById(checkCurrentPetIsDog() ? R.color.sleep_deep_color : R.color.sleep_light_color), 0);
            checkCurrentPetIsDog();
            graphViewSeriesStyle.setWeight(0.9f);
            if (checkCurrentPetIsDog() && i == 1) {
                graphViewSeriesStyle.color = Color.rgb(235, 235, 235);
            }
            GraphViewSeries graphViewSeries = new GraphViewSeries("", graphViewSeriesStyle, new GraphView.GraphViewData[0]);
            for (int i2 = 0; i2 < dailyDetailItem.getDeepsleeps().size(); i2++) {
                graphViewSeries.appendData(new GraphView.GraphViewData(dailyDetailItem.getDeepsleeps().get(i2).get(0).intValue(), dailyDetailItem.getDeepsleeps().get(i2).get(1).intValue()), false);
            }
            rectGraphView.addSeries(graphViewSeries);
        }
        if (dailyDetailItem != null && dailyDetailItem.getLightsleeps() != null && dailyDetailItem.getLightsleeps().size() > 0) {
            GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle2 = new GraphViewSeries.GraphViewSeriesStyle(CommonUtils.getColorById(R.color.sleep_light_color), 0);
            checkCurrentPetIsDog();
            graphViewSeriesStyle2.setWeight(0.9f);
            if (checkCurrentPetIsDog() && i == 2) {
                graphViewSeriesStyle2.color = Color.rgb(235, 235, 235);
            }
            GraphViewSeries graphViewSeries2 = new GraphViewSeries("", graphViewSeriesStyle2, new GraphView.GraphViewData[0]);
            for (int i3 = 0; i3 < dailyDetailItem.getLightsleeps().size(); i3++) {
                graphViewSeries2.appendData(new GraphView.GraphViewData(dailyDetailItem.getLightsleeps().get(i3).get(0).intValue(), dailyDetailItem.getLightsleeps().get(i3).get(1).intValue()), false);
            }
            rectGraphView.addSeries(graphViewSeries2);
        }
        this.mRectGraphType = i;
        getActivity().findViewById(R.id.data_rest_all).setBackgroundResource(this.mRectGraphType == 0 ? R.drawable.solid_white_radius_bottom_bg : R.color.transparent);
        getActivity().findViewById(R.id.data_rest_light).setBackgroundResource(this.mRectGraphType == 1 ? R.drawable.solid_white_radius_bottom_bg : R.color.transparent);
        getActivity().findViewById(R.id.data_rest_deep).setBackgroundResource(this.mRectGraphType == 2 ? R.drawable.solid_white_radius_bottom_bg : R.color.transparent);
        int colorById = CommonUtils.getColorById(R.color.gray);
        int colorById2 = CommonUtils.getColorById(R.color.black);
        int i4 = this.mRectGraphType;
        int rest = i4 == 0 ? this.mDailyDetailItem.getRest() : i4 == 1 ? this.mDailyDetailItem.getLightsleep() : this.mDailyDetailItem.getDeepsleep();
        TextView textView = (TextView) getActivity().findViewById(R.id.data_rest_time);
        int iConvertHourFromSeconds = CommonUtils.convertHourFromSeconds(rest);
        int iConvertMinFromSeconds = CommonUtils.convertMinFromSeconds(rest);
        textView.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertHourFromSeconds)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertHourFromSeconds > 1 ? R.string.Unit_hours : R.string.Unit_hour), colorById, 0.8f), new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertMinFromSeconds)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertMinFromSeconds > 1 ? R.string.Unit_minutes : R.string.Unit_minute), colorById, 0.8f)));
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.data_rest_detail);
        frameLayout.removeAllViews();
        frameLayout.addView(rectGraphView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCurrentPetIsDog() {
        return this.curPet.getType().getId() == 1;
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rest_more_rl) {
            if (this.detailResults != null) {
                Intent intent = new Intent(getActivity(), (Class<?>) WebviewActivity.class);
                intent.putExtra(com.petkit.android.utils.Constants.EXTRA_LOAD_PATH, ApiTools.getWebUrlByKey("activity_sleep"));
                intent.putExtra(com.petkit.android.utils.Constants.EXTRA_TITLE_COLOR, CommonUtils.getColorById(R.color.rest_detail_bg));
                getActivity().startActivity(intent);
                return;
            }
            return;
        }
        if (id == R.id.data_rest_all) {
            if (this.mRectGraphType != 0) {
                initRestGrapView(this.mDailyDetailItem, 0);
                return;
            }
            return;
        }
        if (id == R.id.data_rest_light) {
            if (this.mRectGraphType != 1) {
                initRestGrapView(this.mDailyDetailItem, 1);
            }
        } else if (id == R.id.data_rest_deep) {
            if (this.mRectGraphType != 2) {
                initRestGrapView(this.mDailyDetailItem, 2);
            }
        } else if (id == R.id.lazy_rank) {
            Intent intent2 = new Intent(getActivity(), (Class<?>) ActivityRankActivity.class);
            intent2.putExtra(com.petkit.android.utils.Constants.EXTRA_Characteristic_DAY, this.day);
            intent2.putExtra(com.petkit.android.utils.Constants.EXTRA_DOG, this.curPet);
            intent2.putExtra(com.petkit.android.utils.Constants.EXTRA_RANK, this.mDailyDetailItem.getLazyrank());
            intent2.putExtra(com.petkit.android.utils.Constants.EXTRA_SCORE, this.mDailyDetailItem.getLazyscore());
            intent2.putExtra(com.petkit.android.utils.Constants.EXTRA_TYPE, ActivityRankActivity.LAZY_RANK);
            getActivity().startActivity(intent2);
        }
    }

    @Override // com.jjoe64.graphview.IInterceptListener
    public void changeInterceptState(boolean z) {
        this.mScrollView.setScrollingEnabled(!z);
        if (getActivity() instanceof IInterceptListener) {
            ((IInterceptListener) getActivity()).changeInterceptState(z);
        }
    }
}

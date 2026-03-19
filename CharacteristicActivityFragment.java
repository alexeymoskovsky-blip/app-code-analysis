package com.petkit.android.activities.fit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.material.timepicker.TimeModel;
import com.jess.arms.widget.InterceptScrollView;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.IInterceptListener;
import com.jjoe64.graphview.LineShaderGraphView;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.fit.ActivityRankActivity;
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
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CharacteristicActivityFragment extends BaseFragment implements IInterceptListener {
    public static final String CURPET = "curPet";
    public static final String DAILYDETAILITEM = "dailyDetailItem";
    public static final String DAY = "day";
    private Pet curPet;
    private String day;
    private CharacteristicDetailRsp detailResults;
    private DailyDetailItem mDailyDetailItem;
    private InterceptScrollView mScrollView;
    private boolean needLoadYesterday;

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        getArgs();
        initView();
        init();
        setNoTitle();
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
    }

    private void getArgs() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.day = arguments.getString("day");
            this.curPet = (Pet) arguments.getSerializable("curPet");
            this.mDailyDetailItem = (DailyDetailItem) arguments.getSerializable("dailyDetailItem");
        }
    }

    private void initView() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.activity_activity_detail, (ViewGroup) null);
        this.mScrollView = (InterceptScrollView) viewInflate.findViewById(R.id.scrollview);
        setContentView(viewInflate);
    }

    private void init() {
        setViewState(0);
        getDailyDetail(!DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), String.valueOf(CommonUtils.getDayAfterOffset(Integer.valueOf(this.day).intValue(), -1))).isInit());
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.activity_rank) {
            Intent intent = new Intent(getActivity(), (Class<?>) ActivityRankActivity.class);
            intent.putExtra(Constants.EXTRA_Characteristic_DAY, this.day);
            intent.putExtra(Constants.EXTRA_DOG, this.curPet);
            intent.putExtra(Constants.EXTRA_RANK, this.mDailyDetailItem.getRank());
            intent.putExtra(Constants.EXTRA_SCORE, this.mDailyDetailItem.getScore());
            intent.putExtra(Constants.EXTRA_TYPE, ActivityRankActivity.ACTIVITY_RANK);
            getActivity().startActivity(intent);
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getDailyDetail(this.needLoadYesterday);
    }

    @Override // com.jjoe64.graphview.IInterceptListener
    public void changeInterceptState(boolean z) {
        this.mScrollView.setScrollingEnabled(!z);
        if (getActivity() instanceof IInterceptListener) {
            ((IInterceptListener) getActivity()).changeInterceptState(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDetailView() {
        SpannableStringUtils.SpanText spanText;
        int colorById = CommonUtils.getColorById(R.color.gray);
        int colorById2 = CommonUtils.getColorById(R.color.black);
        int play = this.mDailyDetailItem.getPlay() + this.mDailyDetailItem.getWalk();
        int iConvertHourFromSeconds = CommonUtils.convertHourFromSeconds(play);
        int iConvertMinFromSeconds = CommonUtils.convertMinFromSeconds(play);
        int iConvertHourFromSeconds2 = CommonUtils.convertHourFromSeconds(this.mDailyDetailItem.getPlay());
        int iConvertMinFromSeconds2 = CommonUtils.convertMinFromSeconds(this.mDailyDetailItem.getPlay());
        int i = iConvertHourFromSeconds - iConvertHourFromSeconds2;
        int i2 = iConvertMinFromSeconds - iConvertMinFromSeconds2;
        if (i2 < 0) {
            i2 += 60;
            i--;
        }
        TextView textView = (TextView) getActivity().findViewById(R.id.data_activity_time);
        textView.setVisibility(0);
        textView.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertHourFromSeconds)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertHourFromSeconds > 1 ? R.string.Unit_hours : R.string.Unit_hour), colorById, 1.0f), new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertMinFromSeconds)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertMinFromSeconds > 1 ? R.string.Unit_minutes : R.string.Unit_minute), colorById, 1.0f)));
        if (checkCurrentPetIsDog()) {
            getActivity().findViewById(R.id.data_activity_ll).setVisibility(0);
        } else {
            getActivity().findViewById(R.id.data_activity_ll).setVisibility(8);
        }
        TextView textView2 = (TextView) getActivity().findViewById(R.id.data_activity_paly);
        if (checkCurrentPetIsDog()) {
            textView2.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.State_Run) + "\n", colorById2, 1.0f), new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertHourFromSeconds2)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertHourFromSeconds2 > 1 ? R.string.Unit_hours : R.string.Unit_hour), colorById2, 1.0f), new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(iConvertMinFromSeconds2)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(iConvertMinFromSeconds2 > 1 ? R.string.Unit_minutes : R.string.Unit_minute), colorById2, 1.0f)));
        }
        TextView textView3 = (TextView) getActivity().findViewById(R.id.data_activity_walk);
        if (checkCurrentPetIsDog()) {
            textView3.setVisibility(0);
            textView3.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.State_Walk) + "\n", colorById2, 1.0f), new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(i > 1 ? R.string.Unit_hours : R.string.Unit_hour), colorById2, 1.0f), new SpannableStringUtils.SpanText(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i2)), colorById2, 2.0f), new SpannableStringUtils.SpanText(getString(i2 > 1 ? R.string.Unit_minutes : R.string.Unit_minute), colorById2, 1.0f)));
        }
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.data_activity_detail);
        frameLayout.removeAllViews();
        frameLayout.addView(initCaloriesGraphView(this.mDailyDetailItem.getData()));
        ((TextView) getActivity().findViewById(R.id.charact_compare_title_desc)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Circle), CommonUtils.getColorById(R.color.calorie_detail_compare_color_today), 1.0f), new SpannableStringUtils.SpanText(getString(R.string.Today) + " ", CommonUtils.getColorById(R.color.gray), 1.0f), new SpannableStringUtils.SpanText(getString(R.string.Circle), CommonUtils.getColorById(R.color.calorie_detail_compare_color_yesterday), 1.0f), new SpannableStringUtils.SpanText(getString(R.string.Yesterday), CommonUtils.getColorById(R.color.gray), 1.0f)));
        if (checkCurrentPetIsDog()) {
            getActivity().findViewById(R.id.activity_rank).setVisibility(0);
            TextView textView4 = (TextView) getActivity().findViewById(R.id.activity_rank_num);
            String strValueOf = this.mDailyDetailItem.getRank() <= 0 ? "-" : String.valueOf(this.mDailyDetailItem.getRank());
            if (this.mDailyDetailItem.getRank() == 1) {
                spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_first), 2.0f);
            } else if (this.mDailyDetailItem.getRank() == 2) {
                spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_second), 2.0f);
            } else if (this.mDailyDetailItem.getRank() == 3) {
                spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.activity_rank_third), 2.0f);
            } else {
                spanText = new SpannableStringUtils.SpanText(strValueOf, CommonUtils.getColorById(R.color.gray), 2.0f);
            }
            textView4.setText(SpannableStringUtils.makeSpannableString(spanText, new SpannableStringUtils.SpanText(" ", colorById, 1.0f)));
            ((TextView) getActivity().findViewById(R.id.activity_rank_pet_name)).setText(this.curPet.getName());
            ((TextView) getActivity().findViewById(R.id.activity_rank_des)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText("  " + getString(R.string.Comprehensive_score), CommonUtils.getColorById(R.color.gray), 0.8f), new SpannableStringUtils.SpanText("" + this.mDailyDetailItem.getScore(), CommonUtils.getColorById(R.color.calorie_detail_bg), 0.9f)));
            getActivity().findViewById(R.id.activity_rank).setOnClickListener(this);
            return;
        }
        getActivity().findViewById(R.id.activity_rank).setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCharacteristicDetailView() {
        ((CharacteristicDetailGraphView) getActivity().findViewById(R.id.Charact_history_activity_detail)).init(this.detailResults.getResult(), DateUtil.str2Date(this.day, DateUtil.DATE_FORMAT_7), 3, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCompareGraphView(List<Integer> list, List<Integer> list2) {
        LineShaderGraphView lineShaderGraphView = new LineShaderGraphView(getActivity(), "", this, null);
        lineShaderGraphView.setManualYAxisBounds(150000.0d, 0.0d);
        lineShaderGraphView.setHorizontalLabels(new String[]{"0", com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO, "12", "18", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO});
        GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle = new GraphViewSeries.GraphViewSeriesStyle(CommonUtils.getColorById(R.color.graph_view_gray_pool), 0);
        graphViewSeriesStyle.setQuad(true);
        graphViewSeriesStyle.setGradientColors(new int[]{CommonUtils.getColorById(R.color.calorie_detail_compare_color_today), CommonUtils.getColorById(R.color.calorie_detail_compare_color_today)});
        GraphViewSeries graphViewSeries = new GraphViewSeries("", graphViewSeriesStyle, new GraphView.GraphViewData[0]);
        if (list == null) {
            lineShaderGraphView.addSeries(graphViewSeries);
        } else {
            for (int i = 0; i < list.size(); i++) {
                graphViewSeries.appendData(new GraphView.GraphViewData(i, list.get(i).intValue()), false);
            }
            lineShaderGraphView.addSeries(graphViewSeries);
        }
        GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle2 = new GraphViewSeries.GraphViewSeriesStyle(0, 0);
        graphViewSeriesStyle2.setQuad(true);
        graphViewSeriesStyle2.setGradientColors(new int[]{CommonUtils.getColorById(R.color.calorie_detail_compare_color_yesterday), CommonUtils.getColorById(R.color.calorie_detail_compare_color_yesterday)});
        GraphViewSeries graphViewSeries2 = new GraphViewSeries("", graphViewSeriesStyle2, new GraphView.GraphViewData[0]);
        if (list2 == null) {
            lineShaderGraphView.addSeries(graphViewSeries2);
        } else {
            for (int i2 = 0; i2 < list2.size(); i2++) {
                graphViewSeries2.appendData(new GraphView.GraphViewData(i2, list2.get(i2).intValue()), false);
            }
            lineShaderGraphView.addSeries(graphViewSeries2);
        }
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.charact_compare_detail);
        frameLayout.removeAllViews();
        frameLayout.addView(lineShaderGraphView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCurrentPetIsDog() {
        return this.curPet.getType().getId() == 1;
    }

    private View initCaloriesGraphView(List<Integer> list) {
        BarGraphView barGraphView = new BarGraphView(getActivity(), "", this, null);
        barGraphView.setManualYAxisBounds(225000.0d, 0.0d);
        barGraphView.setHorizontalLabels(new String[]{"0", com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO, "12", "18", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO});
        GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle = new GraphViewSeries.GraphViewSeriesStyle(CommonUtils.getColorById(R.color.graph_view_gray_pool), 0);
        graphViewSeriesStyle.setQuad(true);
        graphViewSeriesStyle.setGradientColors(new int[]{CommonUtils.getColorById(R.color.petkit_detail_gradient_start), CommonUtils.getColorById(R.color.petkit_detail_gradient_end)});
        GraphViewSeries graphViewSeries = new GraphViewSeries("", graphViewSeriesStyle, new GraphView.GraphViewData[0]);
        if (list == null) {
            barGraphView.addSeries(graphViewSeries);
        } else {
            for (int i = 0; i < list.size(); i++) {
                graphViewSeries.appendData(new GraphView.GraphViewData(i, list.get(i).intValue()), false);
            }
            barGraphView.addSeries(graphViewSeries);
        }
        return barGraphView;
    }

    private void getDailyDetail(boolean z) {
        this.needLoadYesterday = z;
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put("day", String.valueOf(this.day));
        if (z) {
            map.put("yesterdayDataFrequency", "900");
        }
        post(ApiTools.SAMPLE_API_ACTIVITY_PLAY_REPORT, map, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.fit.fragment.CharacteristicActivityFragment.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                CharacteristicDetailRsp characteristicDetailRsp = (CharacteristicDetailRsp) this.gson.fromJson(this.responseResult, CharacteristicDetailRsp.class);
                if (CharacteristicActivityFragment.this.getActivity() == null) {
                    return;
                }
                if (characteristicDetailRsp.getError() != null) {
                    CharacteristicActivityFragment.this.setViewState(2);
                    CharacteristicActivityFragment.this.setViewEmpty(R.drawable.default_list_empty_icon, R.string.Hint_network_failed, R.string.Retry);
                    return;
                }
                if (characteristicDetailRsp.getResult() != null) {
                    CharacteristicActivityFragment.this.setViewState(1);
                    CharacteristicActivityFragment.this.detailResults = characteristicDetailRsp;
                    if (CharacteristicActivityFragment.this.checkCurrentPetIsDog()) {
                        CharacteristicActivityFragment.this.mDailyDetailItem.setRank(CharacteristicActivityFragment.this.detailResults.getResult().getRank());
                        CharacteristicActivityFragment.this.mDailyDetailItem.setScore(CharacteristicActivityFragment.this.detailResults.getResult().getScore());
                        SugarRecord.save(CharacteristicActivityFragment.this.mDailyDetailItem);
                    }
                    CharacteristicActivityFragment.this.refreshDetailView();
                    if (characteristicDetailRsp.getResult().getYesterdayData() == null || characteristicDetailRsp.getResult().getYesterdayData().size() <= 0) {
                        DailyDetailItem dailyDetailItem = DailyDetailUtils.getDailyDetailItem(CharacteristicActivityFragment.this.curPet.getId(), String.valueOf(CommonUtils.getDayAfterOffset(Integer.valueOf(CharacteristicActivityFragment.this.day).intValue(), -1)));
                        CharacteristicActivityFragment characteristicActivityFragment = CharacteristicActivityFragment.this;
                        characteristicActivityFragment.setCompareGraphView(characteristicActivityFragment.mDailyDetailItem.getData(), dailyDetailItem.getData());
                    } else {
                        CharacteristicActivityFragment characteristicActivityFragment2 = CharacteristicActivityFragment.this;
                        characteristicActivityFragment2.setCompareGraphView(characteristicActivityFragment2.mDailyDetailItem.getData(), characteristicDetailRsp.getResult().getYesterdayData());
                    }
                    CharacteristicActivityFragment.this.refreshCharacteristicDetailView();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                CharacteristicActivityFragment.this.setViewState(2);
                CharacteristicActivityFragment.this.setViewEmpty(R.drawable.default_list_empty_icon, R.string.Hint_network_failed, R.string.Retry);
            }
        }, false);
    }
}

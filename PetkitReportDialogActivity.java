package com.petkit.android.activities.fit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.DetailReportRsp;
import com.petkit.android.model.DetailReport;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.widget.CharacteristicReportDetailView;
import com.petkit.android.widget.ScoreArcView;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@SuppressLint({"InflateParams"})
public class PetkitReportDialogActivity extends Activity implements View.OnClickListener {
    private int curDay;
    private Pet curPet;
    private String day;
    private TextView dayTextView;
    private View mLoaddingView;
    private TextView noDataTv;
    private LinearLayout petkitReportView;
    private ScrollView scrollview;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.day = bundle.getString(Constants.EXTRA_Characteristic_DAY);
            this.curPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
        } else {
            this.day = getIntent().getStringExtra(Constants.EXTRA_Characteristic_DAY);
            this.curPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
        }
        setContentView(R.layout.activity_petkit_report_dialog);
        setupViews();
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_Characteristic_DAY, this.day);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
    }

    private void setupViews() {
        this.curDay = Integer.parseInt(this.day);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        DisplayMetrics displayMetrics = BaseApplication.displayMetrics;
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        attributes.width = (int) (((double) i) * 0.9d);
        attributes.height = (int) (((double) i2) * 0.9d);
        window.setAttributes(attributes);
        initView();
        this.mLoaddingView.setVisibility(0);
        this.noDataTv.setVisibility(8);
        getDetailReport(this.curPet.getId(), this.day);
        refreshDay(this.curDay);
    }

    private void initView() {
        this.scrollview = (ScrollView) findViewById(R.id.scrollview);
        this.petkitReportView = (LinearLayout) findViewById(R.id.petkit_report_view);
        this.mLoaddingView = findViewById(R.id.fullscreen_loading_indicator);
        this.noDataTv = (TextView) findViewById(R.id.petkit_report_no_data);
        findViewById(R.id.cancel_tv).setOnClickListener(this);
        this.dayTextView = (TextView) findViewById(R.id.day);
        findViewById(R.id.left).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
        this.mLoaddingView.setBackgroundColor(CommonUtils.getColorById(R.color.white));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancel_tv) {
            finish();
            return;
        }
        if (id == R.id.right) {
            if (DateUtil.date2Str(new Date(), DateUtil.DATE_FORMAT_7).equals(this.curDay + "")) {
                return;
            }
            this.petkitReportView.setVisibility(8);
            this.mLoaddingView.setVisibility(0);
            this.noDataTv.setVisibility(8);
            this.curDay = CommonUtils.getDayAfterOffset(this.curDay, 1);
            getDetailReport(this.curPet.getId(), this.curDay + "");
            refreshDay(this.curDay);
            return;
        }
        if (id == R.id.left) {
            this.petkitReportView.setVisibility(8);
            this.noDataTv.setVisibility(8);
            this.mLoaddingView.setVisibility(0);
            this.curDay = CommonUtils.getDayAfterOffset(this.curDay, -1);
            getDetailReport(this.curPet.getId(), this.curDay + "");
            refreshDay(this.curDay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPetkitReportView(DetailReport detailReport) {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_petkit_report, (ViewGroup) null);
        if (checkCurrentPetIsDog()) {
            viewInflate.findViewById(R.id.date_arc_ll).setVisibility(0);
            viewInflate.findViewById(R.id.report_tips_ll).setVisibility(0);
            ScoreArcView scoreArcView = (ScoreArcView) viewInflate.findViewById(R.id.mood_score_view);
            ScoreArcView scoreArcView2 = (ScoreArcView) viewInflate.findViewById(R.id.health_score_view);
            if (detailReport.getMoodScore() > 0) {
                scoreArcView.setScore(detailReport.getMoodScore(), SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Mood) + "\n", CommonUtils.getColorById(R.color.black), 1.0f), new SpannableStringUtils.SpanText(detailReport.getMoodScore() + "", CommonUtils.getColorById(R.color.black), 2.5f)), CommonUtils.getColorById(R.color.score_mood_bg));
            } else {
                scoreArcView.setScore(0, SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Mood) + "\n", CommonUtils.getColorById(R.color.gray), 1.0f), new SpannableStringUtils.SpanText("-", CommonUtils.getColorById(R.color.gray), 2.5f)), CommonUtils.getColorById(R.color.score_mood_bg));
            }
            if (detailReport.getHealthScore() > 0) {
                scoreArcView2.setScore(detailReport.getHealthScore(), SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Health) + "\n", CommonUtils.getColorById(R.color.black), 1.0f), new SpannableStringUtils.SpanText(detailReport.getHealthScore() + "", CommonUtils.getColorById(R.color.black), 2.5f)), CommonUtils.getColorById(R.color.score_health_bg));
            } else {
                scoreArcView2.setScore(0, SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Health) + "\n", CommonUtils.getColorById(R.color.gray), 1.0f), new SpannableStringUtils.SpanText("-", CommonUtils.getColorById(R.color.gray), 2.5f)), CommonUtils.getColorById(R.color.score_health_bg));
            }
            TextView textView = (TextView) viewInflate.findViewById(R.id.mood_summary_tv);
            if (!TextUtils.isEmpty(detailReport.getMoodSummary())) {
                textView.setText(detailReport.getMoodSummary());
            }
            TextView textView2 = (TextView) viewInflate.findViewById(R.id.health_summary_tv);
            if (!TextUtils.isEmpty(detailReport.getHealthSummary())) {
                textView2.setText(detailReport.getHealthSummary());
            }
        } else {
            viewInflate.findViewById(R.id.date_arc_ll).setVisibility(8);
            viewInflate.findViewById(R.id.report_tips_ll).setVisibility(8);
        }
        ((CharacteristicReportDetailView) viewInflate.findViewById(R.id.charact_report_play)).init(detailReport, 1, this.curPet);
        ((CharacteristicReportDetailView) viewInflate.findViewById(R.id.charact_report_rest)).init(detailReport, 2, this.curPet);
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tips_tv);
        if (!TextUtils.isEmpty(detailReport.getTips())) {
            textView3.setVisibility(0);
            textView3.setText(detailReport.getTips());
            viewInflate.findViewById(R.id.divider_line).setVisibility(0);
        } else {
            viewInflate.findViewById(R.id.divider_line).setVisibility(8);
            textView3.setVisibility(8);
        }
        this.petkitReportView.removeAllViews();
        this.petkitReportView.addView(viewInflate);
        refreshDay(this.curDay);
        scrollTo(this.scrollview, 33);
    }

    private void refreshDay(int i) {
        String string;
        String str = i + "";
        String strLong2str = DateUtil.long2str(CommonUtils.getDogLastSyncTime(this.curPet.getId()) + DateUtil.getMillisecondByDateTime("2000-01-01T00:00:00.000+0000", "yyyy-MM-dd'T'HH:mm:ss.SSSZ"), "yyyy-MM-dd HH:mm");
        if (DateUtil.getConvertDateString(strLong2str, "yyyy-MM-dd HH:mm", DateUtil.DATE_FORMAT_7).equals(str)) {
            string = getString(R.string.Up_to) + strLong2str.substring(10);
        } else {
            string = getString(R.string.Whole_day);
        }
        this.dayTextView.setText(DateUtil.getConvertDateString(str, DateUtil.DATE_FORMAT_7, DateUtil.DATE_FORMAT_8) + "\n" + string);
    }

    private void getDetailReport(String str, String str2) {
        HashMap map = new HashMap();
        map.put("petId", str);
        map.put("day", str2);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_ACTIVITY_DETAILREPORT, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.PetkitReportDialogActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                PetkitReportDialogActivity.this.mLoaddingView.setVisibility(8);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                DetailReportRsp detailReportRsp = (DetailReportRsp) this.gson.fromJson(this.responseResult, DetailReportRsp.class);
                if (detailReportRsp.getError() != null) {
                    PetkitReportDialogActivity.this.noDataTv.setVisibility(0);
                    PetkitReportDialogActivity.this.petkitReportView.setVisibility(8);
                } else if (detailReportRsp.getResult() != null) {
                    if (detailReportRsp.getResult().getDay() == 0) {
                        PetkitReportDialogActivity.this.noDataTv.setVisibility(0);
                        PetkitReportDialogActivity.this.petkitReportView.setVisibility(8);
                        PetkitReportDialogActivity.this.noDataTv.setText(R.string.Today_none_data);
                    } else {
                        PetkitReportDialogActivity.this.petkitReportView.setVisibility(0);
                        PetkitReportDialogActivity.this.initPetkitReportView(detailReportRsp.getResult());
                        PetkitReportDialogActivity.this.noDataTv.setVisibility(8);
                    }
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetkitReportDialogActivity.this.noDataTv.setVisibility(0);
                PetkitReportDialogActivity.this.noDataTv.setText(R.string.Hint_network_failed);
            }
        }, false);
    }

    private boolean checkCurrentPetIsDog() {
        return this.curPet.getType().getId() == 1;
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_to_bottom);
    }

    private void scrollTo(final ScrollView scrollView, final int i) {
        new Handler().post(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitReportDialogActivity.2
            @Override // java.lang.Runnable
            public void run() {
                scrollView.fullScroll(i);
            }
        });
    }
}

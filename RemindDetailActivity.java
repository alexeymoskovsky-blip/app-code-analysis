package com.petkit.android.activities.remind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.RemindDetailRsp;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.ConvertTimeTypeUtil;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.Date;
import java.util.HashMap;

/* JADX INFO: loaded from: classes5.dex */
public class RemindDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final int REMIND_TYPE_HISTORY = 1;
    public static final int REMIND_TYPE_MESSAGE = 2;
    public static final int REMIND_TYPE_NORMAL = 0;
    private String id;
    private boolean isHistory = false;
    private Context mContext;
    private RemindDetail mRemindDetail;
    private int mRemindtype;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        if (bundle != null) {
            this.id = bundle.getString(Constants.EXTRA_STRING_ID);
            this.mRemindtype = bundle.getInt(Constants.EXTRA_TYPE, 0);
            this.mRemindDetail = (RemindDetail) bundle.getSerializable(Constants.EXTRA_REMINDDETAIL);
        } else {
            this.id = getIntent().getStringExtra(Constants.EXTRA_STRING_ID);
            this.mRemindtype = getIntent().getIntExtra(Constants.EXTRA_TYPE, 0);
            this.mRemindDetail = (RemindDetail) getIntent().getSerializableExtra(Constants.EXTRA_REMINDDETAIL);
        }
        setContentView(R.layout.activity_remind_detail);
        ConvertTimeTypeUtil.initDateType(this.mContext);
        if (this.mRemindDetail == null) {
            getRemindDetail(this.id);
        } else {
            refresh();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_STRING_ID, this.id);
        bundle.putInt(Constants.EXTRA_TYPE, this.mRemindtype);
        bundle.putSerializable(Constants.EXTRA_REMINDDETAIL, this.mRemindDetail);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Reminder_detail);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        setTitleRightButton(R.string.Edit, this);
        findViewById(R.id.scrollview).setVisibility(0);
        findViewById(R.id.list_empty).setVisibility(8);
        if (this.mRemindDetail.getStatus() == 1) {
            this.isHistory = true;
        }
        if (this.mRemindtype == 2 && this.mRemindDetail.getStatus() == 1) {
            showLongToast(this.mContext.getResources().getString(R.string.Reminder_completed));
        }
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.mRemindDetail.getType().getImg()).imageView((ImageView) findViewById(R.id.type_image)).errorPic(R.drawable.default_image_middle).transformation(new GlideRoundTransform(this, (int) DeviceUtils.dpToPixel(this, 5.0f))).build());
        ((TextView) findViewById(R.id.type_name_tv)).setText(this.mRemindDetail.getType().getName());
        ((TextView) findViewById(R.id.plan_time_tv)).setText(DateUtil.getFormatDateFromString(this.mRemindDetail.getTime()));
        TextView textView = (TextView) findViewById(R.id.advance_date_tv);
        textView.setText(this.mRemindDetail.getAlarmBefore() + "");
        textView.setText(ConvertTimeTypeUtil.convertAdvanceTime(this.mRemindDetail.getAlarmBefore()));
        TextView textView2 = (TextView) findViewById(R.id.repeat_remind_tv);
        textView2.setText(this.mRemindDetail.getRepeat() + "");
        if (TextUtils.isEmpty(this.mRemindDetail.getRepeat())) {
            textView2.setText(ConvertTimeTypeUtil.repeatList.get(0));
        } else {
            textView2.setText(ConvertTimeTypeUtil.convertrepeatTime(this.mRemindDetail.getRepeat()));
        }
        TextView textView3 = (TextView) findViewById(R.id.relevance_pet_tv);
        if (this.mRemindDetail.getPet() != null && !TextUtils.isEmpty(this.mRemindDetail.getPet().getName())) {
            textView3.setText(this.mRemindDetail.getPet().getName());
        } else {
            textView3.setText(getString(R.string.Not_assigned_pets));
        }
        ((TextView) findViewById(R.id.remind_remark_tv)).setText(TextUtils.isEmpty(this.mRemindDetail.getNotes()) ? "" : this.mRemindDetail.getNotes());
        ((TextView) findViewById(R.id.remind_remark)).setVisibility(TextUtils.isEmpty(this.mRemindDetail.getNotes()) ? 8 : 0);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.remind_finish_rl);
        relativeLayout.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.remind_finish_ll);
        TextView textView4 = (TextView) findViewById(R.id.delete_remind_tv);
        if (this.isHistory) {
            relativeLayout.setVisibility(8);
            setTitleRightButton(R.string.Delete, this);
            linearLayout.setVisibility(8);
            textView4.setVisibility(0);
            relativeLayout.setPadding(0, 0, 0, (int) DeviceUtils.dpToPixel(this.mContext, 15.0f));
            return;
        }
        relativeLayout.setVisibility(0);
        linearLayout.setVisibility(0);
        textView4.setVisibility(8);
        relativeLayout.setPadding(0, 0, 0, (int) DeviceUtils.dpToPixel(this.mContext, 10.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshEmptyLayout(String str) {
        setTitleRightButton("", (View.OnClickListener) null);
        findViewById(R.id.list_empty).setVisibility(0);
        findViewById(R.id.scrollview).setVisibility(8);
        ((TextView) findViewById(R.id.list_empty_text)).setText(str);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_right_btn) {
            if (this.isHistory) {
                deleteRemind();
                return;
            } else {
                startActivityForResult(AddRemindActivity.newIntent(this, this.mRemindDetail, 1), 1);
                return;
            }
        }
        if (id == R.id.remind_finish_rl) {
            completeRemind();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1) {
            setResult(-1);
            finish();
        }
    }

    private void getRemindDetail(String str) {
        HashMap map = new HashMap();
        map.put("id", str);
        post(ApiTools.SAMPLE_API_SCHEDULE_GET, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.RemindDetailActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                RemindDetailActivity remindDetailActivity = RemindDetailActivity.this;
                remindDetailActivity.refreshEmptyLayout(remindDetailActivity.getString(R.string.Hint_error_text_network_lost));
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RemindDetailRsp remindDetailRsp = (RemindDetailRsp) this.gson.fromJson(this.responseResult, RemindDetailRsp.class);
                if (remindDetailRsp.getError() != null) {
                    if (remindDetailRsp.getError().getCode() == 1101) {
                        RemindDetailActivity.this.refreshEmptyLayout(remindDetailRsp.getError().getMsg());
                        return;
                    } else {
                        RemindDetailActivity.this.showLongToast(remindDetailRsp.getError().getMsg(), R.drawable.toast_failed);
                        return;
                    }
                }
                if (remindDetailRsp.getResult() != null) {
                    RemindDetailActivity.this.mRemindDetail = remindDetailRsp.getResult();
                    RemindDetailActivity.this.refresh();
                }
            }
        }, false);
    }

    private void completeRemind() {
        HashMap map = new HashMap();
        map.put("id", this.mRemindDetail.getId());
        map.put("time", DateUtil.formatISO8601DateWithMills(new Date()));
        post(ApiTools.SAMPLE_API_SCHEDULE_COMPLETE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.RemindDetailActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    RemindDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    RemindDetailActivity.this.setResult(-1);
                    RemindDetailActivity.this.finish();
                }
            }
        }, false);
    }

    private void deleteRemind() {
        HashMap map = new HashMap();
        map.put("id", this.mRemindDetail.getId());
        post(ApiTools.SAMPLE_API_SCHEDULE_REMOVE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.RemindDetailActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    RemindDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    RemindDetailActivity.this.setResult(-1);
                    RemindDetailActivity.this.finish();
                }
            }
        }, false);
    }
}

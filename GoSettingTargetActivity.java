package com.petkit.android.activities.go.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.cozy.event.BindResultEvent;
import com.petkit.android.activities.go.GoHomeActivity;
import com.petkit.android.activities.go.model.GoDayData;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.go.widget.GoBindCompleteWindow;
import com.petkit.android.activities.go.widget.HorizontalScaleScrollView;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes3.dex */
public class GoSettingTargetActivity extends BaseActivity {
    private static final int DEFAULT_TARGET = 120;
    private GoBindCompleteWindow goBindCompleteWindow;
    private BroadcastReceiver mBroadcastReceiver;
    private int mComeFrom = 0;
    private GoRecord mGoRecord;
    private int mTarget;
    private TextView mTargetTextView;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        long longExtra;
        super.onCreate(bundle);
        if (bundle != null) {
            longExtra = bundle.getLong(GoDataUtils.EXTRA_GO_ID);
            this.mComeFrom = bundle.getInt(Constants.EXTRA_TYPE);
        } else {
            longExtra = getIntent().getLongExtra(GoDataUtils.EXTRA_GO_ID, 0L);
            this.mComeFrom = getIntent().getIntExtra(Constants.EXTRA_TYPE, 0);
        }
        GoRecord goRecordById = GoDataUtils.getGoRecordById(longExtra);
        this.mGoRecord = goRecordById;
        if (goRecordById == null) {
            finish();
        } else {
            setContentView(R.layout.activity_walk_setting_target);
            registerBoradcastReceiver();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        GoBindCompleteWindow goBindCompleteWindow = this.goBindCompleteWindow;
        if (goBindCompleteWindow != null) {
            goBindCompleteWindow.dismiss();
            this.goBindCompleteWindow = null;
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
        bundle.putInt(Constants.EXTRA_TYPE, this.mComeFrom);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        if (this.mComeFrom == 1) {
            setTitle(R.string.Go_binding, R.color.black);
            setTitleStatus("(4/4)", 0, CommonUtils.getColorById(R.color.black), 0);
        } else {
            setTitle(R.string.Go_time_set_title, R.color.black);
        }
        setTitleLeftButton(R.drawable.icon_back_black_normal);
        setDividerLineVisibility(8);
        this.mTargetTextView = (TextView) findViewById(R.id.go_target_time);
        setTargetTextView(this.mGoRecord.getGoal());
        HorizontalScaleScrollView horizontalScaleScrollView = (HorizontalScaleScrollView) findViewById(R.id.horizontalScale);
        horizontalScaleScrollView.setScaleColor(getResources().getColor(R.color.gray));
        horizontalScaleScrollView.setPointerColor(getResources().getColor(R.color.walk_target_point));
        horizontalScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.go.setting.GoSettingTargetActivity$$ExternalSyntheticLambda0
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public final void onScaleScroll(int i) {
                this.f$0.setTargetTextView(i);
            }
        });
        horizontalScaleScrollView.setDefault(this.mGoRecord.getGoal() == -1 ? 120 : this.mGoRecord.getGoal(), BaseApplication.displayMetrics.widthPixels);
        findViewById(R.id.go_target_complete).setOnClickListener(this);
        EventBus.getDefault().post(new BindResultEvent(1, PetUtils.ALL_DEVICE));
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.go_target_complete) {
            updateTarget();
            return;
        }
        if (id == R.id.done) {
            GoBindCompleteWindow goBindCompleteWindow = this.goBindCompleteWindow;
            if (goBindCompleteWindow != null) {
                goBindCompleteWindow.dismiss();
                this.goBindCompleteWindow = null;
            }
            Bundle bundle = new Bundle();
            bundle.putLong(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
            startActivityWithData(HomeActivity.class, bundle, false);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(GoDataUtils.BROADCAST_GO_BIND_COMPLETE));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTargetTextView(int i) {
        this.mTarget = i;
        this.mTargetTextView.setText(CommonUtil.setSpannableStringIntegerSize(GoDataUtils.formatGoTime(this, i * 60, ""), 1.5f));
    }

    private void updateTarget() {
        boolean z = true;
        if (this.mTarget == this.mGoRecord.getGoal() && this.mComeFrom != 1) {
            finish();
            return;
        }
        HashMap map = new HashMap();
        map.put("id", "" + this.mGoRecord.getDeviceId());
        map.put("kv", String.format("{\"goal\":\"%s\"}", Integer.valueOf(this.mTarget)));
        post(ApiTools.SAMPLE_API_GO_UPDATE, map, new AsyncHttpRespHandler(this, z) { // from class: com.petkit.android.activities.go.setting.GoSettingTargetActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    GoSettingTargetActivity.this.mGoRecord.setGoal(GoSettingTargetActivity.this.mTarget);
                    GoSettingTargetActivity.this.mGoRecord.save();
                    GoDayData goDayDataForDay = GoDataUtils.getGoDayDataForDay(GoSettingTargetActivity.this.mGoRecord.getDeviceId(), Integer.valueOf(CommonUtils.getDateStringByOffset(0)).intValue());
                    if (goDayDataForDay != null) {
                        goDayDataForDay.setGoal(GoSettingTargetActivity.this.mTarget);
                        goDayDataForDay.save();
                    }
                    Intent intent = new Intent();
                    intent.setAction(GoDataUtils.BROADCAST_GO_UPDATE);
                    LocalBroadcastManager.getInstance(GoSettingTargetActivity.this).sendBroadcast(intent);
                    if (GoSettingTargetActivity.this.mComeFrom == 1) {
                        GoSettingTargetActivity.this.showCompleteWindow();
                        return;
                    }
                    if (GoSettingTargetActivity.this.mComeFrom == 2) {
                        Bundle bundle = new Bundle();
                        bundle.putLong(GoDataUtils.EXTRA_GO_ID, GoSettingTargetActivity.this.mGoRecord.getDeviceId());
                        GoSettingTargetActivity.this.startActivityWithData(GoHomeActivity.class, bundle, true);
                        return;
                    } else {
                        GoSettingTargetActivity.this.setResult(-1);
                        GoSettingTargetActivity.this.finish();
                        return;
                    }
                }
                GoSettingTargetActivity.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (this.mComeFrom == 1) {
            Bundle bundle = new Bundle();
            bundle.putLong(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
            startActivityWithData(HomeActivity.class, bundle, false);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(GoDataUtils.BROADCAST_GO_BIND_COMPLETE));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void cancel(View view) {
        super.cancel(view);
        if (this.mComeFrom == 1) {
            Bundle bundle = new Bundle();
            bundle.putLong(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
            startActivityWithData(HomeActivity.class, bundle, false);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(GoDataUtils.BROADCAST_GO_BIND_COMPLETE));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCompleteWindow() {
        View viewInflate = getLayoutInflater().inflate(R.layout.layout_go_bind_success, (ViewGroup) null);
        viewInflate.findViewById(R.id.done).setOnClickListener(this);
        GoBindCompleteWindow goBindCompleteWindow = new GoBindCompleteWindow(this, viewInflate, true);
        this.goBindCompleteWindow = goBindCompleteWindow;
        goBindCompleteWindow.setBackgroundDrawable(new BitmapDrawable());
        this.goBindCompleteWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.go.setting.GoSettingTargetActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(GoDataUtils.BROADCAST_GO_BIND_COMPLETE)) {
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.go.setting.GoSettingTargetActivity.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            GoSettingTargetActivity.this.finish();
                        }
                    }, 400L);
                } else if (action.equals(GoDataUtils.BROADCAST_GO_WALKING_MAP_START)) {
                    GoSettingTargetActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_BIND_COMPLETE);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_WALKING_MAP_START);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

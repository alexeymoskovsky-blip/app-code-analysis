package com.petkit.android.activities.walkdog.setting;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.go.widget.HorizontalScaleScrollView;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.statistics.mode.RefreshWalkData;
import com.petkit.android.activities.walkdog.WalkHomeActivity;
import com.petkit.android.activities.walkdog.WalkIntroActivity;
import com.petkit.android.activities.walkdog.model.WalkDayData;
import com.petkit.android.activities.walkdog.model.WalkRecord;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.auth.NTLMEngineImpl;
import java.util.HashMap;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes6.dex */
public class WalkSettingTargetActivity extends BaseActivity {
    private static final int DEFAULT_TARGET = 60;
    private boolean isStatistics;
    private BroadcastReceiver mBroadcastReceiver;
    private int mTarget;
    private TextView mTargetTextView;
    private WalkRecord mWalkRecord;
    String petId;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mWalkRecord = WalkDataUtils.getWalkRecord();
        this.petId = getIntent().getStringExtra(WalkDataUtils.EXTRA_WALK_PET_ID);
        this.isStatistics = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
        setContentView(R.layout.activity_walk_setting_target);
        if (this.mWalkRecord.getGoal() <= 0 && WalkIntroActivity.checkWalkIntroGuideShow(this)) {
            startActivity(WalkIntroActivity.class);
        }
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitleLineVisibility(8);
        setTitle(R.string.Go_time_set_title, R.color.black);
        setTitleLeftButton(R.drawable.icon_back_black_normal);
        this.mTargetTextView = (TextView) findViewById(R.id.go_target_time);
        setTargetTextView(this.mWalkRecord.getGoal());
        HorizontalScaleScrollView horizontalScaleScrollView = (HorizontalScaleScrollView) findViewById(R.id.horizontalScale);
        horizontalScaleScrollView.setScaleColor(getResources().getColor(R.color.gray));
        horizontalScaleScrollView.setPointerColor(getResources().getColor(R.color.walk_target_point));
        horizontalScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i) {
                WalkSettingTargetActivity.this.setTargetTextView(i);
            }
        });
        horizontalScaleScrollView.setDefault(this.mWalkRecord.getGoal() == -1 ? 60 : this.mWalkRecord.getGoal(), BaseApplication.displayMetrics.widthPixels);
        findViewById(R.id.go_target_complete).setOnClickListener(this);
        setTitleLeftButton(R.drawable.btn_back_gray, new View.OnClickListener() { // from class: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity.2
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WalkSettingTargetActivity.this.onBackPressed();
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity$1 */
    public class AnonymousClass1 implements BaseScaleView.OnScrollListener {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
        public void onScaleScroll(int i) {
            WalkSettingTargetActivity.this.setTargetTextView(i);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        public AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            WalkSettingTargetActivity.this.onBackPressed();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.go_target_complete) {
            updateTarget();
        }
    }

    public void setTargetTextView(int i) {
        this.mTarget = i;
        this.mTargetTextView.setText(CommonUtil.setSpannableStringIntegerSize(WalkDataUtils.formatWalkTime(this, i * 60, ""), 1.5f));
    }

    private void updateTarget() {
        HashMap map = new HashMap();
        map.put("setting", String.format("{\"goal\":%s}", Integer.valueOf(this.mTarget)));
        post(ApiTools.SAMPLE_API_WALK_PET_UPDATE_SETTING, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity.3
            public AnonymousClass3(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    WalkSettingTargetActivity.this.mWalkRecord.setGoal(WalkSettingTargetActivity.this.mTarget);
                    WalkSettingTargetActivity.this.mWalkRecord.save();
                    WalkDayData walkDayDataForDay = WalkDataUtils.getWalkDayDataForDay(Integer.valueOf(CommonUtils.getDateStringByOffset(0)).intValue());
                    if (walkDayDataForDay != null) {
                        walkDayDataForDay.setGoal(WalkSettingTargetActivity.this.mTarget);
                        walkDayDataForDay.save();
                    }
                    Intent intent = new Intent();
                    intent.setAction(WalkDataUtils.BROADCAST_WALK_UPDATE);
                    LocalBroadcastManager.getInstance(WalkSettingTargetActivity.this).sendBroadcast(intent);
                    EventBus.getDefault().post(new RefreshWalkData());
                    WalkSettingTargetActivity.this.finish();
                    if (WalkSettingTargetActivity.this.isStatistics) {
                        return;
                    }
                    Intent intent2 = new Intent(WalkSettingTargetActivity.this, (Class<?>) WalkHomeActivity.class);
                    intent2.addFlags(NTLMEngineImpl.FLAG_REQUEST_128BIT_KEY_EXCH);
                    intent2.putExtra(WalkDataUtils.EXTRA_WALK_PET_ID, WalkSettingTargetActivity.this.petId);
                    WalkSettingTargetActivity.this.startActivity(intent2);
                    return;
                }
                WalkSettingTargetActivity.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity$3 */
    public class AnonymousClass3 extends AsyncHttpRespHandler {
        public AnonymousClass3(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() == null) {
                WalkSettingTargetActivity.this.mWalkRecord.setGoal(WalkSettingTargetActivity.this.mTarget);
                WalkSettingTargetActivity.this.mWalkRecord.save();
                WalkDayData walkDayDataForDay = WalkDataUtils.getWalkDayDataForDay(Integer.valueOf(CommonUtils.getDateStringByOffset(0)).intValue());
                if (walkDayDataForDay != null) {
                    walkDayDataForDay.setGoal(WalkSettingTargetActivity.this.mTarget);
                    walkDayDataForDay.save();
                }
                Intent intent = new Intent();
                intent.setAction(WalkDataUtils.BROADCAST_WALK_UPDATE);
                LocalBroadcastManager.getInstance(WalkSettingTargetActivity.this).sendBroadcast(intent);
                EventBus.getDefault().post(new RefreshWalkData());
                WalkSettingTargetActivity.this.finish();
                if (WalkSettingTargetActivity.this.isStatistics) {
                    return;
                }
                Intent intent2 = new Intent(WalkSettingTargetActivity.this, (Class<?>) WalkHomeActivity.class);
                intent2.addFlags(NTLMEngineImpl.FLAG_REQUEST_128BIT_KEY_EXCH);
                intent2.putExtra(WalkDataUtils.EXTRA_WALK_PET_ID, WalkSettingTargetActivity.this.petId);
                WalkSettingTargetActivity.this.startActivity(intent2);
                return;
            }
            WalkSettingTargetActivity.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void cancel(View view) {
        super.cancel(view);
        startActivityWithData(HomeActivity.class, new Bundle(), false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity$4 */
    public class AnonymousClass4 extends BroadcastReceiver {
        public AnonymousClass4() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            if (action.equals(WalkDataUtils.BROADCAST_WALKING_MAP_START)) {
                WalkSettingTargetActivity.this.finish();
            }
        }
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity.4
            public AnonymousClass4() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(WalkDataUtils.BROADCAST_WALKING_MAP_START)) {
                    WalkSettingTargetActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WalkDataUtils.BROADCAST_WALKING_MAP_START);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

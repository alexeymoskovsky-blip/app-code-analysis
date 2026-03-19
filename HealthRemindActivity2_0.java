package com.petkit.android.activities.remind;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.remind.HealthRemindEmptyFragment2_0;
import com.petkit.android.activities.remind.adapter.RemindDetailListAdapter2_0;
import com.petkit.android.activities.remind.utils.ReminderUtils;
import com.petkit.android.activities.remind.widget.RemindRepeatWindow;
import com.petkit.android.activities.universalWindow.BaseBottomWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.RemindDetailsListRsp;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes5.dex */
public class HealthRemindActivity2_0 extends BaseListActivity implements HealthRemindEmptyFragment2_0.IHealthRemindEmptyListener, RemindDetailListAdapter2_0.OnClickListener, BaseBottomWindow.OnClickListener, RemindRepeatWindow.RemindRepeatListener {
    public static final int REMIND_TYPE_HISTORY = 1;
    public static final int REMIND_TYPE_NORMAL = 0;
    private LinearLayout animationView;
    private ImageView ivTopRightImageView;
    private HealthRemindEmptyFragment2_0 mHealthRemindEmptyFragment;
    private int mRemindListType;
    private String petId;
    private RemindRepeatWindow remindRepeatWindow;
    private TextView tvCreateRemind;
    private String lastKey = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'");

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTitleBtn() {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTopLeftBtn() {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTopRightBtn() {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.petId = bundle.getString(Constants.EXTRA_PET_ID);
            this.mRemindListType = bundle.getInt(Constants.EXTRA_TYPE);
        } else {
            this.petId = getIntent().getStringExtra(Constants.EXTRA_PET_ID);
            this.mRemindListType = getIntent().getIntExtra(Constants.EXTRA_TYPE, 0);
        }
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        EventBus.getDefault().register(this);
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_PET_ID, this.petId);
        bundle.putInt(Constants.EXTRA_TYPE, this.mRemindListType);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onRemindUpdated(RemindUpdatedEvent remindUpdatedEvent) {
        this.lastKey = null;
        this.mListAdapter.setList(new ArrayList());
        setViewState(0);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
        getRemindDetailList();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitleLineVisibility(8);
        setTitle(this.mRemindListType == 0 ? R.string.Smart_reminder : R.string.History_reminder);
        setTitleRightImageView(R.drawable.history_remind_icon, this);
        this.mListView.setDividerHeight(0);
        this.mListView.setSelector(R.color.transparent);
        this.contentView.getChildAt(2).setVisibility(8);
        if (this.mRemindListType == 0) {
            setBottomView(R.layout.layout_remind_list_footerview_prompt);
            TextView textView = (TextView) findViewById(R.id.tv_create_remind);
            this.tvCreateRemind = textView;
            textView.setOnClickListener(this);
        }
        getRemindDetailList();
        initAnimationView();
    }

    private void initAnimationView() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.main_view);
        frameLayout.setBackground(getResources().getDrawable(R.color.home_bg_gray));
        LayoutInflater.from(this).inflate(R.layout.layout_remind_animator_targer, (ViewGroup) frameLayout, true);
        LinearLayout linearLayout = (LinearLayout) frameLayout.getChildAt(1);
        this.animationView = linearLayout;
        linearLayout.setVisibility(8);
        this.ivTopRightImageView = (ImageView) frameLayout.findViewById(R.id.title_right_image);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        RemindDetailListAdapter2_0 remindDetailListAdapter2_0 = new RemindDetailListAdapter2_0(this, (List<RemindDetail>) null, this);
        this.mListAdapter = remindDetailListAdapter2_0;
        this.mListView.setAdapter((ListAdapter) remindDetailListAdapter2_0);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getRemindDetailList();
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        this.lastKey = null;
        getRemindDetailList();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Intent intentNewIntent;
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount < 0 || headerViewsCount >= this.mListAdapter.getCount()) {
            return;
        }
        if (this.mRemindListType == 1) {
            intentNewIntent = AddRemindActivity2_0.newIntent(this, (RemindDetail) this.mListAdapter.getItem(headerViewsCount), 3);
        } else {
            intentNewIntent = AddRemindActivity2_0.newIntent(this, (RemindDetail) this.mListAdapter.getItem(headerViewsCount), 2);
        }
        startActivityForResult(intentNewIntent, 1);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_right_image) {
            startActivity(new Intent(this, (Class<?>) HistoryRemindActivity.class));
            return;
        }
        if (id == R.id.title_right_btn) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.EXTRA_TYPE, 1);
            startActivityWithData(HealthRemindActivity2_0.class, bundle, false);
        } else if (id == R.id.tv_create_remind || id == R.id.footer_prompt || id == R.id.remind_add) {
            Intent intent = new Intent(this, (Class<?>) RemindTypeActivity.class);
            intent.putExtra(Constants.EXTRA_PET_ID, this.petId);
            startActivityForResult(intent, 1);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getRemindDetailList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRemindDetailList() {
        HashMap map = new HashMap();
        map.put("lastKey", this.lastKey);
        map.put("limit", String.valueOf(20));
        post(this.mRemindListType == 0 ? ApiTools.SAMPLE_API_SCHEDULE_SCHEDULES : ApiTools.SAMPLE_API_SCHEDULE_HISTORY, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.remind.HealthRemindActivity2_0.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                HealthRemindActivity2_0.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, R.string.Hint_error_text_network_lost, 0);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                HealthRemindActivity2_0.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RemindDetailsListRsp remindDetailsListRsp = (RemindDetailsListRsp) this.gson.fromJson(this.responseResult, RemindDetailsListRsp.class);
                if (remindDetailsListRsp.getError() != null) {
                    if (HealthRemindActivity2_0.this.lastKey == null) {
                        HealthRemindActivity2_0.this.setViewState(2);
                    }
                    HealthRemindActivity2_0.this.showLongToast(remindDetailsListRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (remindDetailsListRsp.getResult() != null) {
                    HealthRemindActivity2_0.this.setViewState(1);
                    if (HealthRemindActivity2_0.this.lastKey != null) {
                        ((BaseListActivity) HealthRemindActivity2_0.this).mListAdapter.addList(remindDetailsListRsp.getResult().getList());
                        HealthRemindActivity2_0.this.checkListViewHeight();
                    } else if (remindDetailsListRsp.getResult().getList() == null || remindDetailsListRsp.getResult().getList().size() == 0) {
                        if (HealthRemindActivity2_0.this.mHealthRemindEmptyFragment == null) {
                            HealthRemindActivity2_0.this.mHealthRemindEmptyFragment = new HealthRemindEmptyFragment2_0();
                        }
                        HealthRemindActivity2_0 healthRemindActivity2_0 = HealthRemindActivity2_0.this;
                        healthRemindActivity2_0.setFragment(healthRemindActivity2_0.mHealthRemindEmptyFragment);
                        if (((BaseActivity) HealthRemindActivity2_0.this).mBottomView != null) {
                            ((BaseActivity) HealthRemindActivity2_0.this).mBottomView.setVisibility(8);
                        }
                    } else {
                        if (HealthRemindActivity2_0.this.mHealthRemindEmptyFragment != null) {
                            HealthRemindActivity2_0 healthRemindActivity2_02 = HealthRemindActivity2_0.this;
                            healthRemindActivity2_02.removeFragment(healthRemindActivity2_02.mHealthRemindEmptyFragment);
                        }
                        ((BaseListActivity) HealthRemindActivity2_0.this).mListAdapter.setList(remindDetailsListRsp.getResult().getList());
                        if (((BaseActivity) HealthRemindActivity2_0.this).mBottomView != null) {
                            ((BaseActivity) HealthRemindActivity2_0.this).mBottomView.setVisibility(0);
                            HealthRemindActivity2_0.this.checkListViewHeight();
                        }
                    }
                    HealthRemindActivity2_0.this.lastKey = remindDetailsListRsp.getResult().getLastKey();
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkListViewHeight() {
        this.mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.remind.HealthRemindActivity2_0.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ((BaseListActivity) HealthRemindActivity2_0.this).mListView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = HealthRemindActivity2_0.this.contentView.getHeight();
                ((BaseListActivity) HealthRemindActivity2_0.this).mListView.measure(View.MeasureSpec.makeMeasureSpec(BaseApplication.displayMetrics.widthPixels, 1073741824), View.MeasureSpec.makeMeasureSpec(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, Integer.MIN_VALUE));
                int measuredHeight = ((BaseListActivity) HealthRemindActivity2_0.this).mListView.getMeasuredHeight();
                PetkitLog.d("HealthRemindActivity2_0", "height1:" + height + ",height2:" + measuredHeight);
                if (height > measuredHeight) {
                    ((BaseActivity) HealthRemindActivity2_0.this).mBottomView.setBackground(null);
                } else {
                    ((BaseActivity) HealthRemindActivity2_0.this).mBottomView.setBackground(HealthRemindActivity2_0.this.getResources().getDrawable(R.drawable.corner_top_16_solid_white));
                }
            }
        });
    }

    @Override // com.petkit.android.activities.remind.HealthRemindEmptyFragment2_0.IHealthRemindEmptyListener
    public void onAddHealthRemind() {
        Intent intent = new Intent(this, (Class<?>) RemindTypeActivity.class);
        intent.putExtra(Constants.EXTRA_PET_ID, this.petId);
        startActivityForResult(intent, 1);
    }

    @Override // com.petkit.android.activities.remind.adapter.RemindDetailListAdapter2_0.OnClickListener
    public void onComplete(int i) {
        RemindDetail remindDetail = (RemindDetail) this.mListAdapter.getItem(i);
        if (TextUtils.isEmpty(remindDetail.getRepeat())) {
            completeRemind(remindDetail, i);
            return;
        }
        String time = remindDetail.getTime();
        try {
            Date iSO8601Date = DateUtil.parseISO8601Date(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(iSO8601Date);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(11, 0);
            calendar2.set(12, 0);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            if (calendar.compareTo(calendar2) == 0) {
                completeRemind(remindDetail, i);
            } else {
                showRemindWindow(calendar.compareTo(calendar2), remindDetail, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogcatStorageHelper.addLog(" completeRemind error:" + e.getMessage() + " time:" + time);
            PetkitLog.d(" completeRemind error:" + e.getMessage() + " time:" + time);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:52|3|(1:30)(2:8|(1:29)(2:13|(1:28)(3:18|(9:23|31|50|32|36|37|(1:39)(1:(4:41|(2:44|42)|54|45))|46|55)|26)))|27|31|50|32|36|37|(0)(0)|46|55) */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x010a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x010b, code lost:
    
        r0.printStackTrace();
        r0 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:39:0x011e A[Catch: Exception -> 0x0097, TRY_ENTER, TryCatch #1 {Exception -> 0x0097, blocks: (B:3:0x002f, B:5:0x003c, B:8:0x004a, B:10:0x0056, B:13:0x0063, B:15:0x006f, B:18:0x007c, B:20:0x0088, B:31:0x00fd, B:36:0x010f, B:39:0x011e, B:46:0x0225, B:41:0x017e, B:42:0x01ae, B:44:0x01b4, B:45:0x01f4, B:35:0x010b, B:26:0x009a, B:28:0x00b4, B:29:0x00cd, B:30:0x00e6, B:32:0x0101), top: B:52:0x002f, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x017c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showRemindWindow(int r16, com.petkit.android.model.RemindDetail r17, int r18) {
        /*
            Method dump skipped, instruction units count: 690
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.remind.HealthRemindActivity2_0.showRemindWindow(int, com.petkit.android.model.RemindDetail, int):void");
    }

    private void completeRemind(final RemindDetail remindDetail, final int i, Date date) {
        HashMap map = new HashMap();
        map.put("id", remindDetail.getId());
        map.put("time", DateUtil.formatISO8601DateWithMills(new Date()));
        map.put("newTime", DateUtil.formatISO8601DateWithMills(date));
        post(ApiTools.SAMPLE_API_SCHEDULE_COMPLETE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.HealthRemindActivity2_0.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    HealthRemindActivity2_0.this.checkSchedule(remindDetail);
                    HealthRemindActivity2_0.this.startItemDismissAnimation(remindDetail, i);
                } else {
                    HealthRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                }
            }
        }, false);
    }

    private void completeRemind(final RemindDetail remindDetail, final int i) {
        HashMap map = new HashMap();
        map.put("id", remindDetail.getId());
        map.put("time", DateUtil.formatISO8601DateWithMills(new Date()));
        post(ApiTools.SAMPLE_API_SCHEDULE_COMPLETE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.HealthRemindActivity2_0.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    HealthRemindActivity2_0.this.checkSchedule(remindDetail);
                    HealthRemindActivity2_0.this.startItemDismissAnimation(remindDetail, i);
                } else {
                    HealthRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void checkSchedule(com.petkit.android.model.RemindDetail r8) {
        /*
            r7 = this;
            java.lang.String r0 = "system.api.schedule"
            java.lang.String r1 = r8.getTime()
            int r1 = com.petkit.android.utils.DateUtil.getOffsetDaysToTodayFromString(r1)
            r2 = 1
            if (r1 != 0) goto L2a
            java.lang.String r8 = r8.getTime()     // Catch: java.lang.Exception -> L28
            java.util.Date r8 = com.petkit.android.utils.DateUtil.parseISO8601Date(r8)     // Catch: java.lang.Exception -> L28
            long r3 = r8.getTime()     // Catch: java.lang.Exception -> L28
            java.util.Date r8 = new java.util.Date     // Catch: java.lang.Exception -> L28
            r8.<init>()     // Catch: java.lang.Exception -> L28
            long r5 = r8.getTime()     // Catch: java.lang.Exception -> L28
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 >= 0) goto L2a
            r8 = 1
            goto L2b
        L28:
            r8 = move-exception
            goto L5c
        L2a:
            r8 = 0
        L2b:
            if (r1 < 0) goto L2f
            if (r8 == 0) goto L5f
        L2f:
            int r8 = com.petkit.android.utils.CommonUtils.getUnReadMsgCount(r0)     // Catch: java.lang.Exception -> L28
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L28
            r1.<init>()     // Catch: java.lang.Exception -> L28
            java.lang.String r3 = "HealthRemindActivity2_0,schedule count："
            r1.append(r3)     // Catch: java.lang.Exception -> L28
            r1.append(r8)     // Catch: java.lang.Exception -> L28
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> L28
            com.petkit.android.utils.LogcatStorageHelper.addLog(r1)     // Catch: java.lang.Exception -> L28
            if (r8 <= 0) goto L5f
            int r8 = r8 - r2
            com.petkit.android.utils.CommonUtils.saveUnReadMsgCount(r0, r8)     // Catch: java.lang.Exception -> L28
            android.content.Intent r8 = new android.content.Intent     // Catch: java.lang.Exception -> L28
            java.lang.String r0 = "com.petkit.android.updateMsg"
            r8.<init>(r0)     // Catch: java.lang.Exception -> L28
            androidx.localbroadcastmanager.content.LocalBroadcastManager r0 = androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(r7)     // Catch: java.lang.Exception -> L28
            r0.sendBroadcast(r8)     // Catch: java.lang.Exception -> L28
            goto L5f
        L5c:
            r8.printStackTrace()
        L5f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.remind.HealthRemindActivity2_0.checkSchedule(com.petkit.android.model.RemindDetail):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startItemDismissAnimation(final RemindDetail remindDetail, int i) {
        DeviceInfos deviceInfosFindDeviceInfo;
        TextView textView = (TextView) this.animationView.findViewById(R.id.remind_type_tv);
        TextView textView2 = (TextView) this.animationView.findViewById(R.id.remind_time_tv);
        TextView textView3 = (TextView) this.animationView.findViewById(R.id.tv_day);
        TextView textView4 = (TextView) this.animationView.findViewById(R.id.tv_repeat);
        TextView textView5 = (TextView) this.animationView.findViewById(R.id.tv_remark);
        TextView textView6 = (TextView) this.animationView.findViewById(R.id.pet_name_tv);
        TextView textView7 = (TextView) this.animationView.findViewById(R.id.device_name_tv);
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(remindDetail.getType().getImg()).imageView((ImageView) this.animationView.findViewById(R.id.remind_type_image)).errorPic(R.drawable.default_image_middle).build());
        if (remindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM)) {
            textView.setText(remindDetail.getName());
        } else {
            textView.setText(remindDetail.getType().getName());
        }
        textView2.setText(DateUtil.getFormatDateFromString(remindDetail.getTime()));
        try {
            if (DateUtil.parseISO8601Date(remindDetail.getTime()).getTime() < new Date().getTime()) {
                textView2.setText(DateUtil.getFormatDateFromString(remindDetail.getTime()));
                textView2.setTextColor(getResources().getColor(R.color.remind_warn_red));
            } else {
                textView2.setText(DateUtil.getFormatDateFromString(remindDetail.getTime()));
                textView2.setTextColor(getResources().getColor(R.color.light_black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int offsetDaysToTodayFromString = DateUtil.getOffsetDaysToTodayFromString(remindDetail.getTime());
        if (offsetDaysToTodayFromString == 0) {
            try {
                if (DateUtil.parseISO8601Date(remindDetail.getTime()).getTime() < new Date().getTime()) {
                    textView3.setText(R.string.Remind_expired);
                } else {
                    textView3.setText(R.string.Today);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (offsetDaysToTodayFromString < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(-offsetDaysToTodayFromString);
            sb.append(getString(offsetDaysToTodayFromString < -1 ? R.string.Unit_days : R.string.Unit_day));
            textView3.setText(getResources().getString(R.string.Remind_overdue_format, sb.toString()));
        } else {
            textView3.setText(getResources().getString(R.string.Plan_to_remind_left_date_format, offsetDaysToTodayFromString + getString(R.string.Unit_days)));
        }
        String repeat = remindDetail.getRepeat();
        if (TextUtils.isEmpty(repeat)) {
            textView4.setText(getString(R.string.None));
        } else if (repeat.length() > 1) {
            String strSubstring = repeat.substring(0, repeat.length() - 1);
            textView4.setText(strSubstring + ReminderUtils.getDateUnit(this, repeat.substring(repeat.length() - 1), Integer.parseInt(strSubstring) > 1));
        }
        if (TextUtils.isEmpty(remindDetail.getNotes())) {
            textView5.setText(R.string.Pet_state_null);
        } else {
            textView5.setText(remindDetail.getNotes());
        }
        if (remindDetail.getPet() != null && !TextUtils.isEmpty(remindDetail.getPet().getName())) {
            textView6.setText(remindDetail.getPet().getName());
            textView6.setVisibility(0);
        } else {
            textView6.setVisibility(8);
        }
        if (!"-1".equals(remindDetail.getType().getWithDeviceType()) && !TextUtils.isEmpty(remindDetail.getType().getWithDeviceType()) && (deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(remindDetail.getDeviceId(), remindDetail.getDeviceType())) != null) {
            textView7.setVisibility(0);
            textView7.setText(deviceInfosFindDeviceInfo.getName());
        } else {
            textView7.setVisibility(8);
        }
        LinearLayout linearLayout = (LinearLayout) this.mListView.getChildAt(i - this.mListView.getFirstVisiblePosition());
        final View childAt = linearLayout.getChildAt(1);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.animationView.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, ArmsUtils.dip2px(this, 50.0f) + linearLayout.getTop() + childAt.getTop(), layoutParams.rightMargin, layoutParams.bottomMargin);
        this.animationView.setLayoutParams(layoutParams);
        int top = this.ivTopRightImageView.getTop();
        this.animationView.setVisibility(0);
        childAt.setVisibility(4);
        LinearLayout linearLayout2 = this.animationView;
        linearLayout2.setPivotX(linearLayout2.getWidth() == 0 ? BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(this, 25.0f) : this.animationView.getWidth());
        this.animationView.setPivotY(0.0f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.animationView, Key.TRANSLATION_X, 0.0f, -ArmsUtils.dip2px(this, 11.0f));
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        long j = 700;
        objectAnimatorOfFloat.setDuration(j);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.animationView, Key.TRANSLATION_Y, 0.0f, -(((ArmsUtils.dip2px(this, 50.0f) + linearLayout.getTop()) + childAt.getTop()) - top));
        objectAnimatorOfFloat2.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimatorOfFloat2.setDuration(j);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.animationView, Key.SCALE_X, 1.0f, 0.0f);
        objectAnimatorOfFloat3.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimatorOfFloat3.setDuration(j);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.animationView, Key.SCALE_Y, 1.0f, 0.0f);
        objectAnimatorOfFloat4.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimatorOfFloat4.setDuration(j);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3, objectAnimatorOfFloat4);
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.remind.HealthRemindActivity2_0.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                HealthRemindActivity2_0.this.animationView.setVisibility(8);
                List list = ((BaseListActivity) HealthRemindActivity2_0.this).mListAdapter.getList();
                list.remove(remindDetail);
                childAt.setVisibility(0);
                ((BaseListActivity) HealthRemindActivity2_0.this).mListAdapter.setList(list);
                HealthRemindActivity2_0.this.checkListViewHeight();
                HealthRemindActivity2_0.this.lastKey = null;
                HealthRemindActivity2_0.this.getRemindDetailList();
            }
        });
        animatorSet.start();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnLeft() {
        RemindRepeatWindow remindRepeatWindow = this.remindRepeatWindow;
        if (remindRepeatWindow != null) {
            remindRepeatWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnRight() {
        RemindRepeatWindow remindRepeatWindow = this.remindRepeatWindow;
        if (remindRepeatWindow != null) {
            remindRepeatWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.remind.widget.RemindRepeatWindow.RemindRepeatListener
    public void onOriginalClick(Date date, RemindDetail remindDetail, int i) {
        completeRemind(remindDetail, i, date);
    }

    @Override // com.petkit.android.activities.remind.widget.RemindRepeatWindow.RemindRepeatListener
    public void onTodayClick(Date date, RemindDetail remindDetail, int i) {
        completeRemind(remindDetail, i, date);
    }
}

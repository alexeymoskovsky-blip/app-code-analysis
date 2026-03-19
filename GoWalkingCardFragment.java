package com.petkit.android.activities.go.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.go.service.GoMainService;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class GoWalkingCardFragment extends BaseFragment implements Runnable {
    private BroadcastReceiver mBroadcastReceiver;
    private TextView mDistanceTextView;
    private int mDuration;
    private TextView mDurationTextView;
    private GoWalkData mGoWalkData;

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        GoRecord goRecordById;
        setContentView(layoutInflater, R.layout.fragment_go_walking_card);
        setNoTitle();
        GoWalkData goWalkDataById = GoDataUtils.getGoWalkDataById(getArguments().getLong(Constants.EXTRA_TAG_ID));
        this.mGoWalkData = goWalkDataById;
        if (goWalkDataById == null || (goRecordById = GoDataUtils.getGoRecordById(goWalkDataById.getDeviceId())) == null) {
            return;
        }
        ((TextView) this.contentView.findViewById(R.id.go_nick)).setText(goRecordById.getName() + ":");
        ((TextView) this.contentView.findViewById(R.id.go_connect_state)).setText(GoDataUtils.getGoConnectStateString(getContext(), goRecordById));
        this.contentView.findViewById(R.id.go_walk_cancel).setOnClickListener(this);
        this.contentView.findViewById(R.id.go_connect_retry).setOnClickListener(this);
        this.contentView.findViewById(R.id.tv_add_marker).setOnClickListener(this);
        this.contentView.findViewById(R.id.img_publish).setOnClickListener(this);
        refreshWalkingConnectState();
        this.mDistanceTextView = (TextView) this.contentView.findViewById(R.id.go_walk_distance);
        this.mDurationTextView = (TextView) this.contentView.findViewById(R.id.go_walk_time);
        this.mDuration = (int) ((System.currentTimeMillis() - DateUtil.getMillisecondByDateString(this.mGoWalkData.getT1())) / 1000);
        refreshWalkingView();
        new Handler().postDelayed(this, 1000L);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.go_walk_cancel || id == R.id.tv_add_marker || id == R.id.img_publish) {
            if (getActivity() instanceof View.OnClickListener) {
                ((View.OnClickListener) getActivity()).onClick(view);
            }
        } else if (id == R.id.go_connect_retry) {
            BaseApplication.setAutoEnableBLE(true);
            int i = Build.VERSION.SDK_INT;
            if (i >= 31) {
                if (CommonUtils.isRunningForeground()) {
                    if (i >= 26) {
                        getActivity().startForegroundService(new Intent(getContext(), (Class<?>) GoMainService.class));
                    } else {
                        getActivity().startService(new Intent(getContext(), (Class<?>) GoMainService.class));
                    }
                }
            } else if (i >= 26) {
                getActivity().startForegroundService(new Intent(getContext(), (Class<?>) GoMainService.class));
            } else {
                getActivity().startService(new Intent(getContext(), (Class<?>) GoMainService.class));
            }
            view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scan_rotate));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshWalkingConnectState() {
        if (getContext() == null) {
            return;
        }
        GoRecord goRecordById = GoDataUtils.getGoRecordById(this.mGoWalkData.getDeviceId());
        TextView textView = (TextView) this.contentView.findViewById(R.id.go_nick);
        TextView textView2 = (TextView) this.contentView.findViewById(R.id.go_connect_state);
        ImageView imageView = (ImageView) this.contentView.findViewById(R.id.go_connect_retry);
        textView2.setText(GoDataUtils.getGoConnectStateString(getContext(), goRecordById));
        if (goRecordById.getConstate() == 2) {
            textView.setTextColor(CommonUtils.getColorById(R.color.walk_score_process));
            textView2.setTextColor(CommonUtils.getColorById(R.color.walk_score_process));
            imageView.clearAnimation();
            imageView.setVisibility(8);
            return;
        }
        textView.setTextColor(CommonUtils.getColorById(R.color.gray));
        textView2.setTextColor(CommonUtils.getColorById(R.color.gray));
        if (goRecordById.getConstate() == 0) {
            imageView.clearAnimation();
            imageView.setVisibility(0);
        }
    }

    public void refreshWalkingView() {
        GoWalkData goWalkData = this.mGoWalkData;
        if (goWalkData == null || this.mDistanceTextView == null || this.mDurationTextView == null) {
            return;
        }
        GoWalkData goWalkDataById = GoDataUtils.getGoWalkDataById(goWalkData.getId().longValue());
        this.mGoWalkData = goWalkDataById;
        if (goWalkDataById == null) {
            return;
        }
        this.mDistanceTextView.setText(CommonUtil.setSpannableStringIntegerSize(GoDataUtils.formatDistance(getContext(), this.mGoWalkData.getDistance()), 2.0f));
        String runningTime = formatRunningTime();
        SpannableString spannableString = new SpannableString(runningTime);
        spannableString.setSpan(new RelativeSizeSpan(2.0f), 0, runningTime.length(), 33);
        this.mDurationTextView.setText(spannableString);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (getContext() != null) {
            this.mDuration++;
            refreshWalkingView();
            new Handler().postDelayed(this, 1000L);
        }
    }

    private String formatRunningTime() {
        int i = this.mDuration;
        return String.format("%02d:%02d:%02d", Integer.valueOf(i / 3600), Integer.valueOf((i % 3600) / 60), Integer.valueOf(i % 60));
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.go.fragment.GoWalkingCardFragment.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (GoWalkingCardFragment.this.mGoWalkData == null || GoDataUtils.getGoWalkDataById(GoWalkingCardFragment.this.mGoWalkData.getId().longValue()) == null) {
                    return;
                }
                GoWalkingCardFragment goWalkingCardFragment = GoWalkingCardFragment.this;
                goWalkingCardFragment.mGoWalkData = GoDataUtils.getGoWalkDataById(goWalkingCardFragment.mGoWalkData.getId().longValue());
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(GoDataUtils.BROADCAST_GO_STATE_UPDATE)) {
                    GoWalkingCardFragment.this.refreshWalkingConnectState();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_STATE_UPDATE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.mBroadcastReceiver);
    }
}

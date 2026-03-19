package com.petkit.android.activities.d2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.Key;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.device.D2PlanItemEditActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.api.FeederPlanRsp;
import com.petkit.android.activities.feeder.model.FeederPlan;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class D2PlanPromptFragment extends BaseFragment {
    private static final int TIME_FOOD_HIDE_DURATION = 400;
    private static final int TIME_FOOD_SHOW_DURATION = 800;
    private D2Record mD2Record;

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        long j;
        super.onCreate(bundle);
        if (bundle == null) {
            j = getArguments().getLong(Constants.EXTRA_DEVICE_ID, 0L);
        } else {
            j = bundle.getLong(Constants.EXTRA_DEVICE_ID);
        }
        this.mD2Record = D2Utils.getD2RecordByDeviceId(j);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.mD2Record.getDeviceId());
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.layout_d2_plan_prompt);
        setNoTitle();
        this.contentView.findViewById(R.id.feeder_plan_generate).setOnClickListener(this);
        this.contentView.findViewById(R.id.feeder_plan_manual).setOnClickListener(this);
        startClockAnimation();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.feeder_plan_generate) {
            generatePlan();
            return;
        }
        if (id == R.id.feeder_plan_manual) {
            FeederPlan feederPlan = new FeederPlan();
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.EXTRA_DEVICE_ID, this.mD2Record.getDeviceId());
            bundle.putBoolean(Constants.EXTRA_BOOLEAN, true);
            bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN, feederPlan);
            startActivityWithData(D2PlanItemEditActivity.class, bundle, false);
        }
    }

    private void startClockAnimation() {
        if (getActivity() == null) {
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.d2.D2PlanPromptFragment.1
            @Override // java.lang.Runnable
            public void run() {
                ImageView imageView = (ImageView) ((BaseFragment) D2PlanPromptFragment.this).contentView.findViewById(R.id.feeder_plan_clock);
                if (imageView.getDrawable() instanceof AnimationDrawable) {
                    ((AnimationDrawable) imageView.getDrawable()).start();
                }
            }
        }, 1000L);
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.d2.D2PlanPromptFragment.2
            @Override // java.lang.Runnable
            public void run() {
                D2PlanPromptFragment.this.startClockHideAnimation();
                D2PlanPromptFragment.this.openDoorAndFoodAnimation();
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startClockHideAnimation() {
        if (getActivity() == null) {
            return;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(1200L);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.d2.D2PlanPromptFragment.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                D2PlanPromptFragment.this.startEditViewDisplayAnimation();
            }
        });
        this.contentView.findViewById(R.id.feeder_plan_clock).startAnimation(alphaAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startEditViewDisplayAnimation() {
        if (getActivity() == null) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.contentView.findViewById(R.id.feeder_plan_edit_view), Key.ALPHA, 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1200L);
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openDoorAndFoodAnimation() {
        if (getActivity() == null) {
            return;
        }
        ((ImageView) this.contentView.findViewById(R.id.d2_icon)).setImageResource(R.drawable.d2_01);
        ImageView imageView = (ImageView) this.contentView.findViewById(R.id.d2_door);
        imageView.setImageResource(R.drawable.d2_door_open_anim);
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).start();
        }
    }

    private void changeViewAlphaAnimation(View view, boolean z, int i) {
        if (getActivity() == null) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, Key.ALPHA, view.getAlpha(), z ? 1.0f : 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(i);
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.start();
    }

    private void generatePlan() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mD2Record.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDERMINI_MAKE_FEED, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.d2.D2PlanPromptFragment.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                FeederPlanRsp feederPlanRsp = (FeederPlanRsp) this.gson.fromJson(this.responseResult, FeederPlanRsp.class);
                if (feederPlanRsp.getError() != null) {
                    D2PlanPromptFragment.this.showShortToast(feederPlanRsp.getError().getMsg());
                    return;
                }
                if (feederPlanRsp.getResult() != null && feederPlanRsp.getResult().getItems() != null) {
                    int amount = 0;
                    for (int i2 = 0; i2 < feederPlanRsp.getResult().getItems().size(); i2++) {
                        amount += feederPlanRsp.getResult().getItems().get(i2).getAmount();
                    }
                    feederPlanRsp.getResult().setCount(feederPlanRsp.getResult().getItems().size());
                    feederPlanRsp.getResult().setTotalAmount(amount);
                }
                LoadDialog.dismissDialog();
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EXTRA_DEVICE_ID, D2PlanPromptFragment.this.mD2Record.getDeviceId());
                bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN, feederPlanRsp.getResult());
                bundle.putBoolean(Constants.EXTRA_BOOLEAN, true);
                D2PlanPromptFragment.this.startActivityWithData(D2PlanEditActivity.class, bundle, false);
            }
        }, false);
    }
}

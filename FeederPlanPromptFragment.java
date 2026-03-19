package com.petkit.android.activities.feeder;

import android.animation.Animator;
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
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.api.FeederPlanRsp;
import com.petkit.android.activities.feeder.model.FeederPlan;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.feeder.setting.FeederPlanEditActivity;
import com.petkit.android.activities.feeder.setting.FeederPlanItemEditActivity;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class FeederPlanPromptFragment extends BaseFragment {
    private static final int TIME_FOOD_HIDE_DURATION = 400;
    private static final int TIME_FOOD_SHOW_DURATION = 800;
    private FeederRecord mFeederRecord;

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        long j;
        super.onCreate(bundle);
        if (bundle == null) {
            j = getArguments().getLong(FeederUtils.EXTRA_FEEDER_ID, 0L);
        } else {
            j = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
        }
        this.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(j);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.layout_feeder_plan_prompt);
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
            bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            bundle.putBoolean(Constants.EXTRA_BOOLEAN, true);
            bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN, feederPlan);
            startActivityWithData(FeederPlanItemEditActivity.class, bundle, false);
        }
    }

    private void startClockAnimation() {
        if (getActivity() == null) {
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.1
            @Override // java.lang.Runnable
            public void run() {
                ImageView imageView = (ImageView) ((BaseFragment) FeederPlanPromptFragment.this).contentView.findViewById(R.id.feeder_plan_clock);
                if (imageView.getDrawable() instanceof AnimationDrawable) {
                    ((AnimationDrawable) imageView.getDrawable()).start();
                }
            }
        }, 1000L);
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.2
            @Override // java.lang.Runnable
            public void run() {
                FeederPlanPromptFragment.this.startClockHideAnimation();
                FeederPlanPromptFragment.this.openDoorAndFoodAnimation();
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
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                FeederPlanPromptFragment.this.startEditViewDisplayAnimation();
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
        View viewFindViewById = this.contentView.findViewById(R.id.feeder_door);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewFindViewById, "y", viewFindViewById.getY(), viewFindViewById.getY() - DeviceUtils.dpToPixel(getContext(), 35.5f));
        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.4
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
                FeederPlanPromptFragment feederPlanPromptFragment = FeederPlanPromptFragment.this;
                feederPlanPromptFragment.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment).contentView.findViewById(R.id.feeder_food_1), true, 800);
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FeederPlanPromptFragment feederPlanPromptFragment2 = FeederPlanPromptFragment.this;
                        feederPlanPromptFragment2.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment2).contentView.findViewById(R.id.feeder_food_2), true, 800);
                    }
                }, 800L);
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.4.2
                    @Override // java.lang.Runnable
                    public void run() {
                        FeederPlanPromptFragment feederPlanPromptFragment2 = FeederPlanPromptFragment.this;
                        feederPlanPromptFragment2.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment2).contentView.findViewById(R.id.feeder_food_3), true, 800);
                    }
                }, 1600L);
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.4.3
                    @Override // java.lang.Runnable
                    public void run() {
                        FeederPlanPromptFragment feederPlanPromptFragment2 = FeederPlanPromptFragment.this;
                        feederPlanPromptFragment2.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment2).contentView.findViewById(R.id.feeder_food_4), true, 800);
                    }
                }, 2400L);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000L);
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.start();
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.5
            @Override // java.lang.Runnable
            public void run() {
                FeederPlanPromptFragment.this.closeDoorAndFoodAnimation();
            }
        }, GoDataUtils.SCAN_DURATION);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeDoorAndFoodAnimation() {
        if (getActivity() == null) {
            return;
        }
        View viewFindViewById = this.contentView.findViewById(R.id.feeder_door);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(viewFindViewById, "y", viewFindViewById.getY(), viewFindViewById.getY() + DeviceUtils.dpToPixel(getContext(), 35.5f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000L);
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.start();
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.6
            @Override // java.lang.Runnable
            public void run() {
                FeederPlanPromptFragment feederPlanPromptFragment = FeederPlanPromptFragment.this;
                feederPlanPromptFragment.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment).contentView.findViewById(R.id.feeder_food_4), false, 400);
                FeederPlanPromptFragment feederPlanPromptFragment2 = FeederPlanPromptFragment.this;
                feederPlanPromptFragment2.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment2).contentView.findViewById(R.id.feeder_food_3), false, 400);
                FeederPlanPromptFragment feederPlanPromptFragment3 = FeederPlanPromptFragment.this;
                feederPlanPromptFragment3.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment3).contentView.findViewById(R.id.feeder_food_2), false, 400);
                FeederPlanPromptFragment feederPlanPromptFragment4 = FeederPlanPromptFragment.this;
                feederPlanPromptFragment4.changeViewAlphaAnimation(((BaseFragment) feederPlanPromptFragment4).contentView.findViewById(R.id.feeder_food_1), false, 400);
            }
        }, 400L);
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.7
            @Override // java.lang.Runnable
            public void run() {
                FeederPlanPromptFragment.this.openDoorAndFoodAnimation();
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeViewAlphaAnimation(View view, boolean z, int i) {
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
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_MAKE_FEED, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.feeder.FeederPlanPromptFragment.8
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                FeederPlanRsp feederPlanRsp = (FeederPlanRsp) this.gson.fromJson(this.responseResult, FeederPlanRsp.class);
                if (feederPlanRsp.getError() != null) {
                    FeederPlanPromptFragment.this.showShortToast(feederPlanRsp.getError().getMsg());
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
                bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, FeederPlanPromptFragment.this.mFeederRecord.getDeviceId());
                bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN, feederPlanRsp.getResult());
                bundle.putBoolean(Constants.EXTRA_BOOLEAN, true);
                FeederPlanPromptFragment.this.startActivityWithData(FeederPlanEditActivity.class, bundle, false);
            }
        }, false);
    }
}

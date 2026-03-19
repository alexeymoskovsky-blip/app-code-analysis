package com.petkit.android.activities.petkitBleDevice;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.petkitBleDevice.mode.DumpSandInfor;
import com.petkit.android.activities.petkitBleDevice.mode.GuideInfor;
import com.petkit.android.activities.petkitBleDevice.mode.OnConfirmCallback;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.registe.utils.AnimationUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class T4EmptyCatLitter01Fragment extends Fragment implements View.OnClickListener {
    private Animation animation;
    private CheckBox cbCheckbox;
    private OnConfirmCallback confirmCallback;
    private Context context;
    private long deviceId;
    private int deviceType;
    private RoundImageview imageView;
    private RelatedProductsInfor infor;
    private ImageView ivLoading;
    private OnDumpSandClickListener listener;
    private LinearLayout llConfirm;
    private TextView tvBuy;
    private TextView tvDesc;
    private TextView tvNotice;
    private TextView tvSetNext;
    private TextView tvTitle;

    public interface OnDumpSandClickListener {
        void onClick();
    }

    public T4EmptyCatLitter01Fragment(OnConfirmCallback onConfirmCallback, RelatedProductsInfor relatedProductsInfor, OnDumpSandClickListener onDumpSandClickListener, long j, int i) {
        this.confirmCallback = onConfirmCallback;
        this.listener = onDumpSandClickListener;
        this.infor = relatedProductsInfor;
        this.deviceId = j;
        this.deviceType = i;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_t4_empty_cat_litter01, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        DumpSandInfor dumpSand;
        super.onViewCreated(view, bundle);
        initViews(view);
        this.cbCheckbox.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.T4EmptyCatLitter01Fragment.1
            public AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                T4EmptyCatLitter01Fragment.this.cbCheckbox.setChecked(false);
            }
        });
        startLoading();
        RelatedProductsInfor relatedProductsInfor = this.infor;
        if (relatedProductsInfor == null || relatedProductsInfor.getStandard() == null) {
            return;
        }
        if (this.deviceType == 21) {
            if (this.infor.getStandard().getT5() == null || (dumpSand = this.infor.getStandard().getT5().getDumpSand()) == null || dumpSand.getGuides() == null || dumpSand.getGuides().size() <= 0) {
                return;
            }
            GuideInfor guideInfor = dumpSand.getGuides().get(0);
            Glide.with(this.context).load(guideInfor.getGuideUrl()).asGif().into(this.imageView);
            this.tvTitle.setText(guideInfor.getGuideTitle());
            this.tvDesc.setText(guideInfor.getGuideDesc());
            this.tvNotice.setText(guideInfor.getGuideNotice());
            this.tvBuy.setVisibility(TextUtils.isEmpty(this.infor.getStandard().getT5().getDumpSand().getShareUrl()) ? 8 : 0);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.tvSetNext.getLayoutParams();
            layoutParams.bottomMargin = TextUtils.isEmpty(this.infor.getStandard().getT5().getDumpSand().getShareUrl()) ? ArmsUtils.dip2px(this.context, 20.0f) : ArmsUtils.dip2px(this.context, 12.0f);
            this.tvSetNext.setLayoutParams(layoutParams);
            return;
        }
        DumpSandInfor dumpSand2 = this.infor.getStandard().getT4().getDumpSand();
        if (dumpSand2 == null || dumpSand2.getGuides() == null || dumpSand2.getGuides().size() <= 0) {
            return;
        }
        GuideInfor guideInfor2 = dumpSand2.getGuides().get(0);
        if (T4Utils.getT4RecordByDeviceId(this.deviceId).getTypeCode() == 2) {
            Glide.with(this.context).load(guideInfor2.getGuideUrl()).asGif().into(this.imageView);
        } else {
            Glide.with(this.context).load(guideInfor2.getGuideUrl()).into(this.imageView);
        }
        this.tvTitle.setText(guideInfor2.getGuideTitle());
        this.tvDesc.setText(guideInfor2.getGuideDesc());
        this.tvNotice.setText(guideInfor2.getGuideNotice());
        this.tvBuy.setVisibility(TextUtils.isEmpty(this.infor.getStandard().getT4().getDumpSand().getShareUrl()) ? 8 : 0);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.tvSetNext.getLayoutParams();
        layoutParams2.bottomMargin = TextUtils.isEmpty(this.infor.getStandard().getT4().getDumpSand().getShareUrl()) ? ArmsUtils.dip2px(this.context, 20.0f) : ArmsUtils.dip2px(this.context, 12.0f);
        this.tvSetNext.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.T4EmptyCatLitter01Fragment$1 */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T4EmptyCatLitter01Fragment.this.cbCheckbox.setChecked(false);
        }
    }

    private void initViews(View view) {
        this.cbCheckbox = (CheckBox) view.findViewById(R.id.cb_checkbox);
        this.tvSetNext = (TextView) view.findViewById(R.id.tv_set_next);
        this.llConfirm = (LinearLayout) view.findViewById(R.id.ll_confirm);
        this.tvBuy = (TextView) view.findViewById(R.id.tv_buy);
        this.imageView = (RoundImageview) view.findViewById(R.id.imageview);
        this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
        this.tvDesc = (TextView) view.findViewById(R.id.tv_desc);
        this.tvNotice = (TextView) view.findViewById(R.id.tv_notice);
        this.ivLoading = (ImageView) view.findViewById(R.id.iv_loading);
        view.findViewById(R.id.cb_checkbox).setOnClickListener(this);
        view.findViewById(R.id.ll_confirm).setOnClickListener(this);
        view.findViewById(R.id.tv_set_next).setOnClickListener(this);
        view.findViewById(R.id.tv_buy).setOnClickListener(this);
    }

    private void startLoading() {
        this.ivLoading.setVisibility(0);
        if (this.animation == null) {
            this.animation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        }
        this.animation.setFillAfter(true);
        this.animation.setDuration(2000L);
        this.animation.setRepeatCount(-1);
        this.animation.setInterpolator(new AccelerateDecelerateInterpolator());
        this.ivLoading.startAnimation(this.animation);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_confirm) {
            this.cbCheckbox.setChecked(true);
            return;
        }
        if (id == R.id.tv_set_next) {
            if (this.cbCheckbox.isChecked()) {
                this.confirmCallback.confirm();
                return;
            } else {
                AnimationUtils.horizontalJitterAnimation(this.llConfirm).start();
                return;
            }
        }
        if (id == R.id.tv_buy) {
            this.listener.onClick();
        }
    }
}

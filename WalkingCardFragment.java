package com.petkit.android.activities.walkdog.fragment;

import android.os.Handler;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.view.KeyEventDispatcher;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.walkdog.adapter.PetsNameListAdapter;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public class WalkingCardFragment extends BaseFragment implements Runnable {
    private TextView mDistanceTextView;
    private int mDuration;
    private TextView mDurationTextView;
    private WalkData mWalkData;
    private TextView tvAddMarker;

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_walking_card);
        setNoTitle();
        WalkData walkDataById = WalkDataUtils.getWalkDataById(getArguments().getLong(Constants.EXTRA_TAG_ID));
        this.mWalkData = walkDataById;
        if (walkDataById == null) {
            return;
        }
        this.tvAddMarker = (TextView) this.contentView.findViewById(R.id.tv_add_marker);
        this.contentView.findViewById(R.id.go_walk_cancel).setOnClickListener(this);
        this.contentView.findViewById(R.id.tv_add_marker).setOnClickListener(this);
        this.contentView.findViewById(R.id.img_publish).setOnClickListener(this);
        this.mDistanceTextView = (TextView) this.contentView.findViewById(R.id.go_walk_distance);
        this.mDurationTextView = (TextView) this.contentView.findViewById(R.id.go_walk_time);
        this.mDuration = (int) ((System.currentTimeMillis() - DateUtil.getMillisecondByDateString(this.mWalkData.getT1())) / 1000);
        refreshWalkingView();
        initPetAvatarView();
        new Handler().postDelayed(this, 1000L);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(final View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.tv_add_marker) {
            if (this.mWalkData.getPetIds().size() == 1) {
                KeyEventDispatcher.Component activity = getActivity();
                Objects.requireNonNull(activity);
                ((View.OnClickListener) activity).onClick(view);
                return;
            }
            View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.pets_popup_window, (ViewGroup) null);
            final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(getContext());
            easyPopupWindow.setParams(new LinearLayout.LayoutParams(this.tvAddMarker.getWidth(), (int) dip2px(100.0f)));
            easyPopupWindow.setBackgroundDrawable(null);
            easyPopupWindow.setContentView(viewInflate);
            ListView listView = (ListView) viewInflate.findViewById(R.id.list_view);
            List<Pet> list = this.mWalkData.getmPets();
            ArrayList arrayList = new ArrayList();
            Iterator<Pet> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
            listView.setAdapter((ListAdapter) new PetsNameListAdapter(getActivity(), arrayList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.petkit.android.activities.walkdog.fragment.WalkingCardFragment$$ExternalSyntheticLambda0
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view2, int i, long j) {
                    this.f$0.lambda$onClick$0(view, easyPopupWindow, adapterView, view2, i, j);
                }
            });
            int[] iArr = new int[2];
            this.tvAddMarker.getLocationOnScreen(iArr);
            easyPopupWindow.showAtLocation(this.tvAddMarker, 0, iArr[0], iArr[1] - easyPopupWindow.getHeight());
            return;
        }
        if ((id == R.id.go_walk_cancel || id == R.id.img_publish) && (getActivity() instanceof View.OnClickListener)) {
            ((View.OnClickListener) getActivity()).onClick(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(View view, EasyPopupWindow easyPopupWindow, AdapterView adapterView, View view2, int i, long j) {
        view.setTag(Integer.valueOf(i));
        ((View.OnClickListener) getActivity()).onClick(view);
        easyPopupWindow.dismiss();
    }

    public void refreshWalkingView() {
        WalkData walkData = this.mWalkData;
        if (walkData == null || this.mDistanceTextView == null || this.mDurationTextView == null) {
            return;
        }
        WalkData walkDataById = WalkDataUtils.getWalkDataById(walkData.getId().longValue());
        this.mWalkData = walkDataById;
        if (walkDataById == null) {
            return;
        }
        this.mDistanceTextView.setText(CommonUtil.setSpannableStringIntegerSize(WalkDataUtils.formatDistance(getContext(), this.mWalkData.getDistance()), 2.0f));
        String runningTime = formatRunningTime();
        SpannableString spannableString = new SpannableString(runningTime);
        spannableString.setSpan(new RelativeSizeSpan(2.0f), 0, runningTime.length(), 33);
        this.mDurationTextView.setText(spannableString);
    }

    private void initPetAvatarView() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.findViewById(R.id.ll_avatar_container);
        linearLayout.removeAllViews();
        for (Pet pet : this.mWalkData.getmPets()) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackground(getResources().getDrawable(R.drawable.circle_white_bg));
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int) dip2px(40.0f), (int) dip2px(40.0f)));
            imageView.setPadding((int) dip2px(2.0f), 2, (int) dip2px(2.0f), 2);
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(pet.getAvatar()).cacheStrategy(3).imageView(imageView).placeholder(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
            linearLayout.addView(imageView);
        }
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
}

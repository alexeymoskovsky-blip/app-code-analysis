package com.petkit.android.activities.feed.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.BaseRecyclerAdapter;
import com.petkit.android.activities.base.adapter.RecyclerViewHolderBase;
import com.petkit.android.activities.feed.mode.CardDataBase;
import com.petkit.android.activities.feed.mode.FeedData;
import com.petkit.android.activities.feed.mode.PetData;
import com.petkit.android.activities.feed.mode.WeightControl;
import com.petkit.android.activities.pet.PetDetailModifyActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.PetRsp;
import com.petkit.android.model.HealthFeed;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.ExpandableLayout;
import com.petkit.android.widget.FeedPlanView;
import com.petkit.android.widget.InterceptRecyclerView;
import com.petkit.android.widget.PetStateSelectView;
import com.petkit.android.widget.RiseNumberTextView.RiseNumberTextView;
import com.petkit.android.widget.windows.PetWeightPickerWindow;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes3.dex */
public class FeedRecyclerCardListAdapter extends BaseRecyclerAdapter<CardDataBase> {
    public static final int ANIMOTION_DOWN = 1;
    public static final int ANIMOTION_UP = 0;
    public static final int VIEW_TYPE_FEED = 1;
    public static final int VIEW_TYPE_HEADER = 0;
    public Context context;
    public Pet curPet;
    public RadioButton currentRadio;
    public int disableControl;
    public Handler handler;
    public ExpandableLayout mExpandView;
    public InterceptRecyclerView mRecyclerView;
    public String mWeight;
    public int weightControl;

    public FeedRecyclerCardListAdapter(Activity activity, InterceptRecyclerView interceptRecyclerView, Pet pet) {
        super(activity);
        this.handler = new Handler();
        this.mRecyclerView = interceptRecyclerView;
        this.curPet = pet;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return ((CardDataBase) this.mItemDataList.get(i)).getItemViewType() == 0 ? 0 : 1;
    }

    public void setExpand() {
        ExpandableLayout expandableLayout = this.mExpandView;
        if (expandableLayout != null) {
            startAnimotion(expandableLayout.getHeaderLayout().findViewById(R.id.expand_img), 1);
            this.mRecyclerView.setScrollingEnabled(true);
            this.mExpandView.getContentLayout().setVisibility(8);
            this.mExpandView.setIsOpened(Boolean.FALSE);
        }
    }

    public Boolean isExpandViewOpened() {
        ExpandableLayout expandableLayout = this.mExpandView;
        if (expandableLayout != null) {
            return expandableLayout.isOpened();
        }
        return Boolean.FALSE;
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public void showData(RecyclerView.ViewHolder viewHolder, int i, List<CardDataBase> list) {
        if (viewHolder instanceof RecyclerHeaderViewHolder) {
            ((RecyclerHeaderViewHolder) viewHolder).setData(list.get(i));
        } else if (viewHolder instanceof FeedRecyclerViewHolder) {
            ((FeedRecyclerViewHolder) viewHolder).setData(list.get(i));
        }
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public View createView(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.context = context;
        if (i == 0) {
            return LayoutInflater.from(context).inflate(R.layout.manage_header_item_layout, viewGroup, false);
        }
        if (i == 1) {
            return LayoutInflater.from(context).inflate(R.layout.layout_feed_card, viewGroup, false);
        }
        return null;
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public RecyclerViewHolderBase createViewHolder(View view, int i) {
        if (i == 0) {
            return new RecyclerHeaderViewHolder(view);
        }
        if (i == 1) {
            return new FeedRecyclerViewHolder(view);
        }
        return null;
    }

    public class RecyclerHeaderViewHolder extends RecyclerViewHolderBase {
        public FrameLayout contentLayout;
        public View control_divider_line;
        public LinearLayout control_layout;
        public ImageView expandImg;
        public ExpandableLayout expandView;
        public FrameLayout headerLayout;
        public TextView hintTv;
        public TextView lable_tv;
        public FrameLayout loading;
        public ImageView petAvatar;
        public LinearLayout petInfoHint;
        public TextView petName;
        public PetStateSelectView petStateSelectView;
        public TextView pet_lable_tv;
        public LinearLayout physiologicalStatusLayout;
        public RadioGroup radioGroup;
        public RadioButton radio_gain_weiht;
        public RadioButton radio_keep_weight;
        public RadioButton radio_lose_weight;
        public ImageView retry_btn;
        public RelativeLayout retry_layout;
        public TextView retry_tv;
        public LinearLayout state_layout;
        public TextView state_sex_tv;
        public TextView state_sick_tv;
        public TextView weight;
        public TextView weight_tv;

        public RecyclerHeaderViewHolder(View view) {
            super(view);
            this.expandView = (ExpandableLayout) view.findViewById(R.id.expendlayout);
            this.petInfoHint = (LinearLayout) view.findViewById(R.id.pet_info_hint);
            this.hintTv = (TextView) view.findViewById(R.id.hint_tv);
            this.headerLayout = this.expandView.getHeaderLayout();
            this.contentLayout = this.expandView.getContentLayout();
            this.petAvatar = (ImageView) this.headerLayout.findViewById(R.id.pet_avatar);
            this.expandImg = (ImageView) this.headerLayout.findViewById(R.id.expand_img);
            this.petName = (TextView) this.headerLayout.findViewById(R.id.pet_name);
            this.retry_layout = (RelativeLayout) this.headerLayout.findViewById(R.id.retry_layout);
            this.retry_btn = (ImageView) this.headerLayout.findViewById(R.id.retry_btn);
            this.loading = (FrameLayout) this.headerLayout.findViewById(R.id.loading);
            this.retry_tv = (TextView) this.headerLayout.findViewById(R.id.retry_tv);
            this.state_layout = (LinearLayout) this.headerLayout.findViewById(R.id.state_layout);
            this.state_sick_tv = (TextView) this.headerLayout.findViewById(R.id.state_sick_tv);
            this.state_sex_tv = (TextView) this.headerLayout.findViewById(R.id.state_sex_tv);
            this.weight = (TextView) this.headerLayout.findViewById(R.id.weight_tv);
            this.pet_lable_tv = (TextView) this.headerLayout.findViewById(R.id.pet_lable_tv);
            this.physiologicalStatusLayout = (LinearLayout) this.contentLayout.findViewById(R.id.physiological_status_layout);
            this.petStateSelectView = (PetStateSelectView) this.contentLayout.findViewById(R.id.petStateSelectView);
            this.weight_tv = (TextView) this.contentLayout.findViewById(R.id.weight_tv);
            this.lable_tv = (TextView) this.contentLayout.findViewById(R.id.lable_tv);
            this.radioGroup = (RadioGroup) this.contentLayout.findViewById(R.id.radioGroup);
            this.radio_gain_weiht = (RadioButton) this.contentLayout.findViewById(R.id.radio_gain_weiht);
            this.radio_lose_weight = (RadioButton) this.contentLayout.findViewById(R.id.radio_lose_weight);
            this.radio_keep_weight = (RadioButton) this.contentLayout.findViewById(R.id.radio_keep_weight);
            this.control_layout = (LinearLayout) this.contentLayout.findViewById(R.id.control_layout);
            this.control_divider_line = this.contentLayout.findViewById(R.id.control_divider_line);
            this.petInfoHint.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.1
                final /* synthetic */ FeedRecyclerCardListAdapter val$this$0;

                public AnonymousClass1(FeedRecyclerCardListAdapter feedRecyclerCardListAdapter) {
                    feedRecyclerCardListAdapter = feedRecyclerCardListAdapter;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (FeedRecyclerCardListAdapter.this.isExpandViewOpened().booleanValue()) {
                        return;
                    }
                    Intent intent = new Intent(FeedRecyclerCardListAdapter.this.context, (Class<?>) PetDetailModifyActivity.class);
                    intent.putExtra(Constants.EXTRA_DOG, FeedRecyclerCardListAdapter.this.curPet);
                    FeedRecyclerCardListAdapter.this.context.startActivity(intent);
                }
            });
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$1 */
        public class AnonymousClass1 implements View.OnClickListener {
            final /* synthetic */ FeedRecyclerCardListAdapter val$this$0;

            public AnonymousClass1(FeedRecyclerCardListAdapter feedRecyclerCardListAdapter) {
                feedRecyclerCardListAdapter = feedRecyclerCardListAdapter;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (FeedRecyclerCardListAdapter.this.isExpandViewOpened().booleanValue()) {
                    return;
                }
                Intent intent = new Intent(FeedRecyclerCardListAdapter.this.context, (Class<?>) PetDetailModifyActivity.class);
                intent.putExtra(Constants.EXTRA_DOG, FeedRecyclerCardListAdapter.this.curPet);
                FeedRecyclerCardListAdapter.this.context.startActivity(intent);
            }
        }

        public void setData(CardDataBase cardDataBase) {
            FeedRecyclerCardListAdapter.this.mExpandView = this.expandView;
            this.petInfoHint.setVisibility(8);
            if (cardDataBase instanceof PetData) {
                PetData petData = (PetData) cardDataBase;
                FeedRecyclerCardListAdapter.this.curPet = petData.getPet();
                if (PetUtils.getPetLackInfo(FeedRecyclerCardListAdapter.this.curPet) != PetUtils.PET_INFO_LACK.COMPLETE) {
                    this.petInfoHint.setVisibility(0);
                    this.hintTv.setText(FeedRecyclerCardListAdapter.this.context.getString(R.string.Pet_perfect_info_tip_format, FeedRecyclerCardListAdapter.this.curPet.getName()));
                }
                ((BaseApplication) FeedRecyclerCardListAdapter.this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(FeedRecyclerCardListAdapter.this.mActivity, GlideImageConfig.builder().url(FeedRecyclerCardListAdapter.this.curPet.getAvatar()).imageView(this.petAvatar).errorPic(FeedRecyclerCardListAdapter.this.curPet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(FeedRecyclerCardListAdapter.this.mActivity)).build());
                this.petName.setText(FeedRecyclerCardListAdapter.this.curPet.getName());
                this.weight.setVisibility(0);
                this.expandImg.setVisibility(0);
                this.retry_layout.setVisibility(8);
                if (FeedRecyclerCardListAdapter.this.curPet.getStates() != null && FeedRecyclerCardListAdapter.this.curPet.getStates().size() > 0) {
                    this.state_layout.setVisibility(0);
                    this.state_sick_tv.setVisibility(8);
                    this.state_sex_tv.setVisibility(8);
                    Iterator<Integer> it = FeedRecyclerCardListAdapter.this.curPet.getStates().iterator();
                    while (it.hasNext()) {
                        int iIntValue = it.next().intValue();
                        if (iIntValue == 1) {
                            this.state_sick_tv.setVisibility(0);
                            this.state_sick_tv.setText(FeedRecyclerCardListAdapter.this.context.getString(R.string.Pet_state_sick));
                        } else if (iIntValue == 2) {
                            this.state_sex_tv.setVisibility(0);
                            this.state_sex_tv.setText(FeedRecyclerCardListAdapter.this.context.getString(R.string.Pet_state_oestrus));
                        } else if (iIntValue == 3) {
                            this.state_sex_tv.setVisibility(0);
                            this.state_sex_tv.setText(FeedRecyclerCardListAdapter.this.context.getString(R.string.Pet_state_pregnancy));
                        } else if (iIntValue == 4) {
                            this.state_sex_tv.setVisibility(0);
                            this.state_sex_tv.setText(FeedRecyclerCardListAdapter.this.context.getString(R.string.Pet_state_lactation));
                        }
                    }
                } else {
                    this.state_layout.setVisibility(8);
                }
                if (!TextUtils.isEmpty(FeedRecyclerCardListAdapter.this.curPet.getWeight())) {
                    if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                        double dKgToLb = CommonUtil.KgToLb(Double.valueOf(FeedRecyclerCardListAdapter.this.curPet.getWeight()).doubleValue());
                        this.weight.setText(CommonUtil.doubleToDouble(dKgToLb) + FeedRecyclerCardListAdapter.this.context.getString(R.string.Unit_lb));
                    } else {
                        this.weight.setText(CommonUtil.doubleToDouble(Double.valueOf(FeedRecyclerCardListAdapter.this.curPet.getWeight()).doubleValue()) + FeedRecyclerCardListAdapter.this.context.getString(R.string.Unit_kg));
                    }
                }
                if (FeedRecyclerCardListAdapter.this.curPet.getWeightLabel() == null) {
                    this.pet_lable_tv.setVisibility(8);
                } else {
                    this.pet_lable_tv.setVisibility(0);
                    this.pet_lable_tv.setText(FeedRecyclerCardListAdapter.this.curPet.getWeightLabel());
                }
                refreshContentView(petData);
                this.weight_tv.setOnClickListener(new AnonymousClass2());
                this.expandImg.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.3
                    final /* synthetic */ PetData val$petData;

                    public AnonymousClass3(PetData petData2) {
                        petData = petData2;
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        int i = 0;
                        if (RecyclerHeaderViewHolder.this.contentLayout.getVisibility() == 8) {
                            RecyclerHeaderViewHolder.this.refreshContentView(petData);
                            RecyclerHeaderViewHolder recyclerHeaderViewHolder = RecyclerHeaderViewHolder.this;
                            FeedRecyclerCardListAdapter.this.startAnimotion(recyclerHeaderViewHolder.expandImg, 0);
                            FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                            FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(false);
                            return;
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        if (RecyclerHeaderViewHolder.this.petStateSelectView.state_sick == 2) {
                            stringBuffer.append("1,");
                        }
                        if (RecyclerHeaderViewHolder.this.petStateSelectView.state_oestrus == 2) {
                            stringBuffer.append("2,");
                        }
                        if (RecyclerHeaderViewHolder.this.petStateSelectView.state_pregnancy == 2) {
                            stringBuffer.append("3,");
                            i = 3;
                        }
                        if (RecyclerHeaderViewHolder.this.petStateSelectView.state_lactation == 2) {
                            stringBuffer.append("4,");
                            i = 4;
                        }
                        if (FeedRecyclerCardListAdapter.this.weightControl == FeedRecyclerCardListAdapter.this.curPet.getWeightControl() && FeedRecyclerCardListAdapter.this.mWeight.equals(FeedRecyclerCardListAdapter.this.curPet.getWeight()) && !RecyclerHeaderViewHolder.this.petStateSelectView.isChange()) {
                            RecyclerHeaderViewHolder recyclerHeaderViewHolder2 = RecyclerHeaderViewHolder.this;
                            FeedRecyclerCardListAdapter.this.startAnimotion(recyclerHeaderViewHolder2.expandImg, 1);
                            FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                            FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                            return;
                        }
                        HashMap map = new HashMap();
                        map.put("states", stringBuffer.toString());
                        map.put("weightControl", "" + FeedRecyclerCardListAdapter.this.weightControl);
                        map.put("weight", FeedRecyclerCardListAdapter.this.mWeight);
                        map.put("pregnantWeek", "" + RecyclerHeaderViewHolder.this.petStateSelectView.getPregnancyValue());
                        map.put("lactationWeek", "" + RecyclerHeaderViewHolder.this.petStateSelectView.getLacationValue());
                        map.put("petId", FeedRecyclerCardListAdapter.this.curPet.getId());
                        RecyclerHeaderViewHolder.this.updatePetInfo(map);
                        HashMap map2 = new HashMap();
                        if (i != 0) {
                            map2.put("status", i + "");
                        }
                        if (FeedRecyclerCardListAdapter.this.weightControl != FeedRecyclerCardListAdapter.this.curPet.getWeightControl()) {
                            map2.put("control", FeedRecyclerCardListAdapter.this.weightControl + "");
                        }
                    }
                });
                return;
            }
            this.weight.setVisibility(8);
            this.expandImg.setVisibility(8);
            this.retry_layout.setVisibility(0);
            this.loading.setVisibility(8);
            this.retry_btn.setVisibility(0);
            this.pet_lable_tv.setVisibility(8);
            this.state_layout.setVisibility(8);
            if (FeedRecyclerCardListAdapter.this.curPet != null) {
                ((BaseApplication) FeedRecyclerCardListAdapter.this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(FeedRecyclerCardListAdapter.this.mActivity, GlideImageConfig.builder().url(FeedRecyclerCardListAdapter.this.curPet.getAvatar()).imageView(this.petAvatar).errorPic(FeedRecyclerCardListAdapter.this.curPet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(FeedRecyclerCardListAdapter.this.mActivity)).build());
                this.petName.setText(FeedRecyclerCardListAdapter.this.curPet.getName());
            }
            this.retry_btn.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.4
                public AnonymousClass4() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    RecyclerHeaderViewHolder.this.loading.setVisibility(0);
                    RecyclerHeaderViewHolder.this.retry_btn.setVisibility(8);
                    LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA));
                }
            });
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$2 */
        public class AnonymousClass2 implements View.OnClickListener {
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new PetWeightPickerWindow(FeedRecyclerCardListAdapter.this.context, FeedRecyclerCardListAdapter.this.curPet, new PetWeightPickerWindow.OnWeightChangeListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$2$$ExternalSyntheticLambda0
                    @Override // com.petkit.android.widget.windows.PetWeightPickerWindow.OnWeightChangeListener
                    public final void onWeightChange(double d) {
                        this.f$0.lambda$onClick$0(d);
                    }
                }).show(FeedRecyclerCardListAdapter.this.mActivity.getWindow().getDecorView());
            }

            public /* synthetic */ void lambda$onClick$0(double d) {
                RecyclerHeaderViewHolder.this.weight_tv.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.valueOf(CommonUtil.doubleToDouble(d)), CommonUtils.getColorById(R.color.white), 2.0f), new SpannableStringUtils.SpanText(FeedRecyclerCardListAdapter.this.context.getString(UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? R.string.Unit_pound : R.string.Unit_kilogram), CommonUtils.getColorById(R.color.white), 0.8f)));
                FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                if (1 == UserInforUtils.getCurrentLoginResult().getSettings().getUnit()) {
                    d = CommonUtil.LbToKg(d);
                }
                feedRecyclerCardListAdapter.mWeight = String.valueOf(d);
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$3 */
        public class AnonymousClass3 implements View.OnClickListener {
            final /* synthetic */ PetData val$petData;

            public AnonymousClass3(PetData petData2) {
                petData = petData2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i = 0;
                if (RecyclerHeaderViewHolder.this.contentLayout.getVisibility() == 8) {
                    RecyclerHeaderViewHolder.this.refreshContentView(petData);
                    RecyclerHeaderViewHolder recyclerHeaderViewHolder = RecyclerHeaderViewHolder.this;
                    FeedRecyclerCardListAdapter.this.startAnimotion(recyclerHeaderViewHolder.expandImg, 0);
                    FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                    FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(false);
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                if (RecyclerHeaderViewHolder.this.petStateSelectView.state_sick == 2) {
                    stringBuffer.append("1,");
                }
                if (RecyclerHeaderViewHolder.this.petStateSelectView.state_oestrus == 2) {
                    stringBuffer.append("2,");
                }
                if (RecyclerHeaderViewHolder.this.petStateSelectView.state_pregnancy == 2) {
                    stringBuffer.append("3,");
                    i = 3;
                }
                if (RecyclerHeaderViewHolder.this.petStateSelectView.state_lactation == 2) {
                    stringBuffer.append("4,");
                    i = 4;
                }
                if (FeedRecyclerCardListAdapter.this.weightControl == FeedRecyclerCardListAdapter.this.curPet.getWeightControl() && FeedRecyclerCardListAdapter.this.mWeight.equals(FeedRecyclerCardListAdapter.this.curPet.getWeight()) && !RecyclerHeaderViewHolder.this.petStateSelectView.isChange()) {
                    RecyclerHeaderViewHolder recyclerHeaderViewHolder2 = RecyclerHeaderViewHolder.this;
                    FeedRecyclerCardListAdapter.this.startAnimotion(recyclerHeaderViewHolder2.expandImg, 1);
                    FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                    FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                    return;
                }
                HashMap map = new HashMap();
                map.put("states", stringBuffer.toString());
                map.put("weightControl", "" + FeedRecyclerCardListAdapter.this.weightControl);
                map.put("weight", FeedRecyclerCardListAdapter.this.mWeight);
                map.put("pregnantWeek", "" + RecyclerHeaderViewHolder.this.petStateSelectView.getPregnancyValue());
                map.put("lactationWeek", "" + RecyclerHeaderViewHolder.this.petStateSelectView.getLacationValue());
                map.put("petId", FeedRecyclerCardListAdapter.this.curPet.getId());
                RecyclerHeaderViewHolder.this.updatePetInfo(map);
                HashMap map2 = new HashMap();
                if (i != 0) {
                    map2.put("status", i + "");
                }
                if (FeedRecyclerCardListAdapter.this.weightControl != FeedRecyclerCardListAdapter.this.curPet.getWeightControl()) {
                    map2.put("control", FeedRecyclerCardListAdapter.this.weightControl + "");
                }
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$4 */
        public class AnonymousClass4 implements View.OnClickListener {
            public AnonymousClass4() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RecyclerHeaderViewHolder.this.loading.setVisibility(0);
                RecyclerHeaderViewHolder.this.retry_btn.setVisibility(8);
                LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA));
            }
        }

        public final void refreshContentView(PetData petData) {
            double dDoubleValue;
            Pet pet = petData.getPet();
            if (pet.getMaleState().contains("2") || pet.getFemaleState().contains("2")) {
                this.petStateSelectView.setPetType(2);
                this.physiologicalStatusLayout.setVisibility(8);
            } else if (pet.getGender() == 1) {
                this.petStateSelectView.setPetType(0);
                this.physiologicalStatusLayout.setVisibility(8);
            } else if (pet.getGender() == 2) {
                this.petStateSelectView.setPetType(1);
                this.physiologicalStatusLayout.setVisibility(0);
            } else {
                this.petStateSelectView.setPetType(3);
                this.physiologicalStatusLayout.setVisibility(8);
            }
            if (pet.getStates() != null) {
                Iterator<Integer> it = pet.getStates().iterator();
                while (it.hasNext()) {
                    int iIntValue = it.next().intValue();
                    if (iIntValue == 3) {
                        PetStateSelectView petStateSelectView = this.petStateSelectView;
                        HealthFeed healthFeed = petData.mHealthFeed;
                        petStateSelectView.setPregnancyState(healthFeed == null ? 0 : healthFeed.getPregnantWeeks());
                    } else if (iIntValue == 4) {
                        PetStateSelectView petStateSelectView2 = this.petStateSelectView;
                        HealthFeed healthFeed2 = petData.mHealthFeed;
                        petStateSelectView2.setLactationState(healthFeed2 == null ? 0 : healthFeed2.getLactationWeeks());
                    }
                }
            }
            if (!TextUtils.isEmpty(pet.getWeight())) {
                FeedRecyclerCardListAdapter.this.mWeight = pet.getWeight();
                if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                    dDoubleValue = CommonUtil.KgToLb(Double.valueOf(pet.getWeight()).doubleValue());
                    this.weight_tv.setText(CommonUtil.doubleToDouble(dDoubleValue) + FeedRecyclerCardListAdapter.this.context.getString(R.string.Unit_pound));
                } else {
                    dDoubleValue = Double.valueOf(pet.getWeight()).doubleValue();
                }
                this.weight_tv.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.valueOf(CommonUtil.doubleToDouble(dDoubleValue)), CommonUtils.getColorById(R.color.white), 2.0f), new SpannableStringUtils.SpanText(FeedRecyclerCardListAdapter.this.context.getString(UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? R.string.Unit_pound : R.string.Unit_kilogram), CommonUtils.getColorById(R.color.white), 0.8f)));
            }
            if (pet.getWeightLabel() == null) {
                this.lable_tv.setVisibility(8);
            } else {
                this.lable_tv.setVisibility(0);
                this.lable_tv.setText(pet.getWeightLabel());
            }
            HealthFeed healthFeed3 = petData.mHealthFeed;
            WeightControl weightControl = healthFeed3 == null ? null : healthFeed3.getWeightControl();
            if (weightControl != null && weightControl.getDisable() != null && weightControl.getDisable().size() > 1) {
                this.control_layout.setVisibility(8);
                this.control_divider_line.setVisibility(8);
            } else {
                this.control_layout.setVisibility(0);
                this.control_divider_line.setVisibility(0);
            }
            if (weightControl == null || weightControl.getDisable() == null || weightControl.getDisable().size() <= 0) {
                FeedRecyclerCardListAdapter.this.disableControl = 0;
            } else {
                FeedRecyclerCardListAdapter.this.disableControl = weightControl.getDisable().get(0).intValue();
            }
            FeedRecyclerCardListAdapter.this.weightControl = pet.getWeightControl();
            if (pet.getWeightControl() == 2) {
                this.radio_lose_weight.setChecked(true);
                FeedRecyclerCardListAdapter.this.currentRadio = this.radio_lose_weight;
            } else if (pet.getWeightControl() == 3) {
                this.radio_keep_weight.setChecked(true);
                FeedRecyclerCardListAdapter.this.currentRadio = this.radio_keep_weight;
            } else {
                this.radio_gain_weiht.setChecked(true);
                FeedRecyclerCardListAdapter.this.currentRadio = this.radio_gain_weiht;
            }
            this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.5
                final /* synthetic */ WeightControl val$weightControl;

                public AnonymousClass5(WeightControl weightControl2) {
                    weightControl = weightControl2;
                }

                @Override // android.widget.RadioGroup.OnCheckedChangeListener
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.radio_gain_weiht) {
                        if (FeedRecyclerCardListAdapter.this.disableControl == 1) {
                            FeedRecyclerCardListAdapter.this.currentRadio.setChecked(true);
                            if (FeedRecyclerCardListAdapter.this.isExpandViewOpened().booleanValue()) {
                                PetkitToast.showShortToast((Activity) FeedRecyclerCardListAdapter.this.context, weightControl.getTip());
                                return;
                            }
                            return;
                        }
                        FeedRecyclerCardListAdapter.this.weightControl = 1;
                        RecyclerHeaderViewHolder recyclerHeaderViewHolder = RecyclerHeaderViewHolder.this;
                        FeedRecyclerCardListAdapter.this.currentRadio = recyclerHeaderViewHolder.radio_gain_weiht;
                        return;
                    }
                    if (i == R.id.radio_lose_weight) {
                        if (FeedRecyclerCardListAdapter.this.disableControl == 2) {
                            FeedRecyclerCardListAdapter.this.currentRadio.setChecked(true);
                            if (FeedRecyclerCardListAdapter.this.isExpandViewOpened().booleanValue()) {
                                PetkitToast.showShortToast((Activity) FeedRecyclerCardListAdapter.this.context, weightControl.getTip());
                                return;
                            }
                            return;
                        }
                        FeedRecyclerCardListAdapter.this.weightControl = 2;
                        RecyclerHeaderViewHolder recyclerHeaderViewHolder2 = RecyclerHeaderViewHolder.this;
                        FeedRecyclerCardListAdapter.this.currentRadio = recyclerHeaderViewHolder2.radio_lose_weight;
                        return;
                    }
                    if (i == R.id.radio_keep_weight) {
                        FeedRecyclerCardListAdapter.this.weightControl = 3;
                        RecyclerHeaderViewHolder recyclerHeaderViewHolder3 = RecyclerHeaderViewHolder.this;
                        FeedRecyclerCardListAdapter.this.currentRadio = recyclerHeaderViewHolder3.radio_keep_weight;
                    }
                }
            });
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$5 */
        public class AnonymousClass5 implements RadioGroup.OnCheckedChangeListener {
            final /* synthetic */ WeightControl val$weightControl;

            public AnonymousClass5(WeightControl weightControl2) {
                weightControl = weightControl2;
            }

            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radio_gain_weiht) {
                    if (FeedRecyclerCardListAdapter.this.disableControl == 1) {
                        FeedRecyclerCardListAdapter.this.currentRadio.setChecked(true);
                        if (FeedRecyclerCardListAdapter.this.isExpandViewOpened().booleanValue()) {
                            PetkitToast.showShortToast((Activity) FeedRecyclerCardListAdapter.this.context, weightControl.getTip());
                            return;
                        }
                        return;
                    }
                    FeedRecyclerCardListAdapter.this.weightControl = 1;
                    RecyclerHeaderViewHolder recyclerHeaderViewHolder = RecyclerHeaderViewHolder.this;
                    FeedRecyclerCardListAdapter.this.currentRadio = recyclerHeaderViewHolder.radio_gain_weiht;
                    return;
                }
                if (i == R.id.radio_lose_weight) {
                    if (FeedRecyclerCardListAdapter.this.disableControl == 2) {
                        FeedRecyclerCardListAdapter.this.currentRadio.setChecked(true);
                        if (FeedRecyclerCardListAdapter.this.isExpandViewOpened().booleanValue()) {
                            PetkitToast.showShortToast((Activity) FeedRecyclerCardListAdapter.this.context, weightControl.getTip());
                            return;
                        }
                        return;
                    }
                    FeedRecyclerCardListAdapter.this.weightControl = 2;
                    RecyclerHeaderViewHolder recyclerHeaderViewHolder2 = RecyclerHeaderViewHolder.this;
                    FeedRecyclerCardListAdapter.this.currentRadio = recyclerHeaderViewHolder2.radio_lose_weight;
                    return;
                }
                if (i == R.id.radio_keep_weight) {
                    FeedRecyclerCardListAdapter.this.weightControl = 3;
                    RecyclerHeaderViewHolder recyclerHeaderViewHolder3 = RecyclerHeaderViewHolder.this;
                    FeedRecyclerCardListAdapter.this.currentRadio = recyclerHeaderViewHolder3.radio_keep_weight;
                }
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$6 */
        public class AnonymousClass6 extends AsyncHttpRespHandler {
            public final /* synthetic */ Map val$params;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(Activity activity, boolean z, Map map) {
                super(activity, z);
                map = map;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                PetRsp petRsp = (PetRsp) this.gson.fromJson(this.responseResult, PetRsp.class);
                if (petRsp.getError() != null) {
                    new AlertDialog.Builder(FeedRecyclerCardListAdapter.this.context).setCancelable(false).setTitle(R.string.Prompt).setMessage(petRsp.getError().getMsg()).setPositiveButton(R.string.Retry, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.6.2
                        public AnonymousClass2() {
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                            RecyclerHeaderViewHolder.this.updatePetInfo(map);
                        }
                    }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.6.1
                        public AnonymousClass1() {
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                            FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                            feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                            FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                        }
                    }).show();
                    return;
                }
                FeedRecyclerCardListAdapter.this.curPet = petRsp.getResult();
                FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                RecyclerHeaderViewHolder.this.petStateSelectView.resetChange();
                FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                FeedRecyclerCardListAdapter.this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.6.3
                    public AnonymousClass3() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                        intent.putExtra(Constants.EXTRA_BOOLEAN, false);
                        LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                        UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
                        FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                    }
                }, 300L);
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$6$2 */
            public class AnonymousClass2 implements DialogInterface.OnClickListener {
                public AnonymousClass2() {
                }

                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                    RecyclerHeaderViewHolder.this.updatePetInfo(map);
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$6$1 */
            public class AnonymousClass1 implements DialogInterface.OnClickListener {
                public AnonymousClass1() {
                }

                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                    FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                    feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                    FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$6$3 */
            public class AnonymousClass3 implements Runnable {
                public AnonymousClass3() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                    intent.putExtra(Constants.EXTRA_BOOLEAN, false);
                    LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                    UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
                    FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                }
            }
        }

        public final void updatePetInfo(Map<String, String> map) {
            AsyncHttpUtil.post(ApiTools.SAMPLE_API_PET_UPDATEINFO, map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler((Activity) FeedRecyclerCardListAdapter.this.context, true) { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.6
                public final /* synthetic */ Map val$params;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(Activity activity, boolean z, Map map2) {
                    super(activity, z);
                    map = map2;
                }

                @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                public void onFinish() {
                    super.onFinish();
                }

                @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                    super.onSuccess(i, headerArr, bArr);
                    PetRsp petRsp = (PetRsp) this.gson.fromJson(this.responseResult, PetRsp.class);
                    if (petRsp.getError() != null) {
                        new AlertDialog.Builder(FeedRecyclerCardListAdapter.this.context).setCancelable(false).setTitle(R.string.Prompt).setMessage(petRsp.getError().getMsg()).setPositiveButton(R.string.Retry, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.6.2
                            public AnonymousClass2() {
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i2) {
                                AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                RecyclerHeaderViewHolder.this.updatePetInfo(map);
                            }
                        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.6.1
                            public AnonymousClass1() {
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i2) {
                                FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                                FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                                feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                                FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                            }
                        }).show();
                        return;
                    }
                    FeedRecyclerCardListAdapter.this.curPet = petRsp.getResult();
                    FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                    RecyclerHeaderViewHolder.this.petStateSelectView.resetChange();
                    FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                    feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                    FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                    FeedRecyclerCardListAdapter.this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.RecyclerHeaderViewHolder.6.3
                        public AnonymousClass3() {
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                            intent.putExtra(Constants.EXTRA_BOOLEAN, false);
                            LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                            UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
                            FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                        }
                    }, 300L);
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$6$2 */
                public class AnonymousClass2 implements DialogInterface.OnClickListener {
                    public AnonymousClass2() {
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                        RecyclerHeaderViewHolder.this.updatePetInfo(map);
                    }
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$6$1 */
                public class AnonymousClass1 implements DialogInterface.OnClickListener {
                    public AnonymousClass1() {
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                        FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                        feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                        FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                    }
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$RecyclerHeaderViewHolder$6$3 */
                public class AnonymousClass3 implements Runnable {
                    public AnonymousClass3() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                        intent.putExtra(Constants.EXTRA_BOOLEAN, false);
                        LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                        UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
                        FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                    }
                }
            }, false);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$1 */
    public class AnonymousClass1 extends AsyncHttpRespHandler {
        public final /* synthetic */ Map val$params;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Activity activity, boolean z, Map map) {
            super(activity, z);
            map = map;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFinish() {
            super.onFinish();
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            PetRsp petRsp = (PetRsp) this.gson.fromJson(this.responseResult, PetRsp.class);
            if (petRsp.getError() != null) {
                new AlertDialog.Builder(FeedRecyclerCardListAdapter.this.context).setCancelable(false).setTitle(R.string.Prompt).setMessage(petRsp.getError().getMsg()).setPositiveButton(R.string.Retry, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.1.2
                    public AnonymousClass2() {
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        FeedRecyclerCardListAdapter.this.updatePetWeight(map);
                    }
                }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.1.1
                    public DialogInterfaceOnClickListenerC00501() {
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                        FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                        feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                        FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                    }
                }).show();
                return;
            }
            FeedRecyclerCardListAdapter.this.curPet = petRsp.getResult();
            FeedRecyclerCardListAdapter.this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.1.3
                public AnonymousClass3() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                    intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                    LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                    FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                    UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
                }
            }, 300L);
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$1$2 */
        public class AnonymousClass2 implements DialogInterface.OnClickListener {
            public AnonymousClass2() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                FeedRecyclerCardListAdapter.this.updatePetWeight(map);
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$1$1 */
        public class DialogInterfaceOnClickListenerC00501 implements DialogInterface.OnClickListener {
            public DialogInterfaceOnClickListenerC00501() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$1$3 */
        public class AnonymousClass3 implements Runnable {
            public AnonymousClass3() {
            }

            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
            }
        }
    }

    public final void updatePetWeight(Map<String, String> map) {
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_PET_UPDATE_PROP, map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler((Activity) this.context, true) { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.1
            public final /* synthetic */ Map val$params;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Activity activity, boolean z, Map map2) {
                super(activity, z);
                map = map2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                PetRsp petRsp = (PetRsp) this.gson.fromJson(this.responseResult, PetRsp.class);
                if (petRsp.getError() != null) {
                    new AlertDialog.Builder(FeedRecyclerCardListAdapter.this.context).setCancelable(false).setTitle(R.string.Prompt).setMessage(petRsp.getError().getMsg()).setPositiveButton(R.string.Retry, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.1.2
                        public AnonymousClass2() {
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            FeedRecyclerCardListAdapter.this.updatePetWeight(map);
                        }
                    }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.1.1
                        public DialogInterfaceOnClickListenerC00501() {
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                            FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                            feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                            FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                        }
                    }).show();
                    return;
                }
                FeedRecyclerCardListAdapter.this.curPet = petRsp.getResult();
                FeedRecyclerCardListAdapter.this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.1.3
                    public AnonymousClass3() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                        intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                        LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                        FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                        UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
                    }
                }, 300L);
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$1$2 */
            public class AnonymousClass2 implements DialogInterface.OnClickListener {
                public AnonymousClass2() {
                }

                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    FeedRecyclerCardListAdapter.this.updatePetWeight(map);
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$1$1 */
            public class DialogInterfaceOnClickListenerC00501 implements DialogInterface.OnClickListener {
                public DialogInterfaceOnClickListenerC00501() {
                }

                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    FeedRecyclerCardListAdapter.this.mExpandView.setExpand();
                    FeedRecyclerCardListAdapter feedRecyclerCardListAdapter = FeedRecyclerCardListAdapter.this;
                    feedRecyclerCardListAdapter.startAnimotion(feedRecyclerCardListAdapter.mExpandView.getHeaderLayout().findViewById(R.id.expand_img), 1);
                    FeedRecyclerCardListAdapter.this.mRecyclerView.setScrollingEnabled(true);
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$1$3 */
            public class AnonymousClass3 implements Runnable {
                public AnonymousClass3() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    Intent intent = new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA);
                    intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                    LocalBroadcastManager.getInstance(FeedRecyclerCardListAdapter.this.context).sendBroadcast(intent);
                    FeedRecyclerCardListAdapter.this.sendUpdateDogBroadcast();
                    UserInforUtils.updateDogInformation(FeedRecyclerCardListAdapter.this.curPet, 3);
                }
            }
        }, false);
    }

    public final void startAnimotion(View view, int i) {
        ObjectAnimator duration = i == 0 ? ObjectAnimator.ofFloat(view, "rotation", 0.0f, 180.0f).setDuration(200L) : i == 1 ? ObjectAnimator.ofFloat(view, "rotation", 180.0f, 0.0f).setDuration(200L) : null;
        duration.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(duration);
        animatorSet.start();
    }

    public final void sendUpdateDogBroadcast() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_DOG, this.curPet);
        intent.putExtra(Constants.EXTRA_BOOLEAN, true);
        intent.setAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

    public class FeedRecyclerViewHolder extends RecyclerViewHolderBase {
        public FeedPlanView feedPlanView;
        public RiseNumberTextView feed_ad;
        public TextView feed_food;
        public TextView special_tip;

        public FeedRecyclerViewHolder(View view) {
            super(view);
            this.feed_ad = (RiseNumberTextView) view.findViewById(R.id.feed_advice_value);
            this.feed_food = (TextView) view.findViewById(R.id.feed_food);
            this.feedPlanView = (FeedPlanView) view.findViewById(R.id.my_plan_content);
            this.special_tip = (TextView) view.findViewById(R.id.special_tip);
            if (FeedRecyclerCardListAdapter.this.context instanceof View.OnClickListener) {
                view.findViewById(R.id.my_feed_plan_change).setOnClickListener((View.OnClickListener) FeedRecyclerCardListAdapter.this.context);
                view.findViewById(R.id.feed_know).setOnClickListener((View.OnClickListener) FeedRecyclerCardListAdapter.this.context);
                view.findViewById(R.id.feed_compare).setOnClickListener((View.OnClickListener) FeedRecyclerCardListAdapter.this.context);
                this.feed_food.setOnClickListener((View.OnClickListener) FeedRecyclerCardListAdapter.this.context);
            }
        }

        public void setData(CardDataBase cardDataBase) {
            float feedAmount;
            HealthFeed healthFeed;
            this.special_tip.setVisibility(4);
            if (!(cardDataBase instanceof FeedData) || (healthFeed = ((FeedData) cardDataBase).mHealthFeed) == null) {
                feedAmount = 0.0f;
            } else {
                feedAmount = healthFeed.getFeedAmount();
                this.feedPlanView.setPlanData((int) feedAmount, FeedRecyclerCardListAdapter.this.getFeedRatio(healthFeed.getFeedRatio()));
                if (healthFeed.getPregnantWeeks() > 0) {
                    this.special_tip.setVisibility(0);
                    this.special_tip.setText(FeedRecyclerCardListAdapter.this.context.getString(R.string.Pet_current_pregnant_weeks_format, FeedRecyclerCardListAdapter.this.curPet.getName(), String.valueOf(healthFeed.getPregnantWeeks()), FeedRecyclerCardListAdapter.this.context.getString(healthFeed.getPregnantWeeks() > 1 ? R.string.Unit_weeks : R.string.Unit_week)));
                } else if (healthFeed.getLactationWeeks() > 0) {
                    this.special_tip.setVisibility(0);
                    this.special_tip.setText(FeedRecyclerCardListAdapter.this.context.getString(R.string.Pet_current_lactation_weeks_format, FeedRecyclerCardListAdapter.this.curPet.getName(), String.valueOf(healthFeed.getPregnantWeeks()), FeedRecyclerCardListAdapter.this.context.getString(healthFeed.getLactationWeeks() > 1 ? R.string.Unit_weeks : R.string.Unit_week)));
                }
            }
            this.feed_ad.setText("0");
            if (feedAmount > 0.0f) {
                if (String.valueOf(feedAmount).endsWith(".0")) {
                    this.feed_ad.withNumber((int) feedAmount);
                } else {
                    this.feed_ad.withNumber(feedAmount);
                }
                this.feed_ad.setDuration(CommonUtil.getDurationByNumberSize(this.feed_ad.sizeOfFloat(feedAmount)));
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter.FeedRecyclerViewHolder.1
                    public AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        FeedRecyclerViewHolder.this.feed_ad.start();
                    }
                }, 300);
            }
            initView();
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.feed.adapter.FeedRecyclerCardListAdapter$FeedRecyclerViewHolder$1 */
        public class AnonymousClass1 implements Runnable {
            public AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                FeedRecyclerViewHolder.this.feed_ad.start();
            }
        }

        private void initView() {
            if (FeedRecyclerCardListAdapter.this.curPet != null) {
                this.feed_food.setText(PetUtils.getPetFoodInfo(FeedRecyclerCardListAdapter.this.mActivity, FeedRecyclerCardListAdapter.this.curPet));
                this.feed_ad.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                this.feed_ad.setTextSize(64.0f);
            }
        }
    }

    public final float[] getFeedRatio(String str) {
        if (TextUtils.isEmpty(str)) {
            return new float[0];
        }
        String[] strArrSplit = str.split(ChineseToPinyinResource.Field.COMMA);
        int length = strArrSplit.length;
        float[] fArr = new float[length];
        float f = 0.0f;
        for (int i = 0; i < strArrSplit.length; i++) {
            float fFloatValue = Float.valueOf(strArrSplit[i]).floatValue();
            fArr[i] = fFloatValue;
            f += fFloatValue;
        }
        float[] fArr2 = new float[length];
        if (f != 0.0f && length != 0) {
            for (int i2 = 0; i2 < length; i2++) {
                fArr2[i2] = fArr[i2] / f;
            }
        }
        return fArr2;
    }
}

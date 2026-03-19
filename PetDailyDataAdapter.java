package com.petkit.android.activities.home.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.PetDataCard;
import com.petkit.android.activities.pet.adapter.PetsManageAdapter;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.statistics.utils.StatisticsUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class PetDailyDataAdapter extends RecyclerView.Adapter<PetDailyDataViewHolder> implements ItemTouchHelperAdapter {
    public List<PetDataCard> dataList;
    public OnClickListener listener;
    public Context mContext;
    public PetsManageAdapter.OnItemDragListener onItemDragListener;
    public long time = 0;
    public float lastX = 0.0f;
    public float lastY = 0.0f;
    public float dLastX = 0.0f;
    public float dLastY = 0.0f;
    public Disposable disposable = null;

    public interface OnClickListener {
        void onClick(PetDataCard petDataCard);
    }

    @Override // com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter
    public void onItemDismiss(int i) {
    }

    public PetDailyDataAdapter(Context context, OnClickListener onClickListener, List<PetDataCard> list) {
        this.listener = onClickListener;
        this.mContext = context;
        this.dataList = list;
    }

    public List<PetDataCard> getDataList() {
        return this.dataList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PetDailyDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PetDailyDataViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_pet_daily_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final PetDailyDataViewHolder petDailyDataViewHolder, final int i) {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        Resources resources3;
        int i4;
        SpannableString spannableString;
        Resources resources4;
        int i5;
        Resources resources5;
        int i6;
        boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
        float f = zEquals ? 1.8571428f : 1.0f;
        petDailyDataViewHolder.rlParentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.PetDailyDataAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$0(i, view);
            }
        });
        petDailyDataViewHolder.rlParentPanel.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.home.adapter.PetDailyDataAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$onBindViewHolder$1(petDailyDataViewHolder, view, motionEvent);
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.add(petDailyDataViewHolder.ivPetAvatarOne);
        arrayList.add(petDailyDataViewHolder.ivPetAvatarTwo);
        arrayList.add(petDailyDataViewHolder.ivPetAvatarThree);
        if (PetDataCard.CardType.FEED.equals(this.dataList.get(i).getKey())) {
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.feed_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Out_of_food));
            int feederChart = UserInforUtils.getCurrentLoginResult().getSettings().getFeederChart();
            if (feederChart == 1 || feederChart == 3) {
                petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getAmountWithUnitSpannableString(this.mContext.getResources().getString(R.string.About), this.dataList.get(i).getFeed().getGrams(), f));
                if (this.dataList.get(i).getFeed().getTime() > 0) {
                    petDailyDataViewHolder.tvTypeContentTwo.setText(this.mContext.getResources().getString(R.string.Recent_feed) + " " + TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, this.dataList.get(i).getFeed().getTime()));
                } else {
                    petDailyDataViewHolder.tvTypeContentTwo.setText("");
                }
            } else if (feederChart == 2 || feederChart == 4) {
                if (this.dataList.get(i).getFeed().getParts() > 1) {
                    resources5 = this.mContext.getResources();
                    i6 = R.string.D4s_feeders_unit;
                } else {
                    resources5 = this.mContext.getResources();
                    i6 = R.string.D4s_feeder_unit;
                }
                String string = resources5.getString(i6);
                SpannableString spannableString2 = new SpannableString(String.valueOf(this.dataList.get(i).getFeed().getParts()) + string);
                if (!TextUtils.isEmpty(spannableString2.toString())) {
                    spannableString2.setSpan(new RelativeSizeSpan(f), spannableString2.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getFeed().getParts())), spannableString2.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getFeed().getParts())) + String.valueOf(this.dataList.get(i).getFeed().getParts()).length() + string.length(), 33);
                    spannableString2.setSpan(new StyleSpan(1), spannableString2.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getFeed().getParts())), spannableString2.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getFeed().getParts())) + String.valueOf(this.dataList.get(i).getFeed().getParts()).length() + string.length(), 33);
                    petDailyDataViewHolder.tvTypeContentOne.setText(spannableString2);
                }
                if (this.dataList.get(i).getFeed().getPartsTime() > 0) {
                    petDailyDataViewHolder.tvTypeContentTwo.setText(this.mContext.getResources().getString(R.string.Recent_feed) + " " + TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, this.dataList.get(i).getFeed().getPartsTime()));
                } else {
                    petDailyDataViewHolder.tvTypeContentTwo.setText("");
                }
            }
            petDailyDataViewHolder.llPetAvatar.setVisibility(4);
            return;
        }
        if (PetDataCard.CardType.EAT.equals(this.dataList.get(i).getKey())) {
            ArrayList arrayList2 = new ArrayList();
            List<D3Record> allD3Device = D3Utils.getAllD3Device();
            List<D4sRecord> allD4sDevice = D4sUtils.getAllD4sDevice();
            if (allD3Device != null) {
                for (int size = allD3Device.size() - 1; size >= 0; size--) {
                    if (allD3Device.get(size).getDeviceShared() != null) {
                        allD3Device.remove(size);
                    }
                }
                arrayList2.addAll(allD3Device);
            }
            if (allD4sDevice != null) {
                for (int size2 = allD4sDevice.size() - 1; size2 >= 0; size2--) {
                    if (allD4sDevice.get(size2).getDeviceShared() != null) {
                        allD4sDevice.remove(size2);
                    }
                }
                arrayList2.addAll(allD4sDevice);
            }
            if (allD4sDevice.size() != 0 && ((allD4sDevice.size() == 0 || allD3Device.size() != 0) && allD4sDevice.size() != 0)) {
                allD3Device.size();
            }
            int feederChart2 = UserInforUtils.getCurrentLoginResult().getSettings().getFeederChart();
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.eat_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Eating));
            if (feederChart2 == 3 || feederChart2 == 4) {
                if (this.dataList.get(i).getEat().getEatCount() > 1) {
                    resources4 = this.mContext.getResources();
                    i5 = R.string.Unit_times;
                } else {
                    resources4 = this.mContext.getResources();
                    i5 = R.string.Unit_time;
                }
                String string2 = resources4.getString(i5);
                SpannableString spannableString3 = new SpannableString(String.valueOf(this.dataList.get(i).getEat().getEatCount()) + string2);
                if (!TextUtils.isEmpty(spannableString3.toString())) {
                    spannableString3.setSpan(new RelativeSizeSpan(f), spannableString3.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getEat().getEatCount())), spannableString3.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getEat().getEatCount())) + String.valueOf(this.dataList.get(i).getEat().getEatCount()).length() + string2.length(), 33);
                    spannableString3.setSpan(new StyleSpan(1), spannableString3.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getEat().getEatCount())), spannableString3.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getEat().getEatCount())) + String.valueOf(this.dataList.get(i).getEat().getEatCount()).length() + string2.length(), 33);
                    petDailyDataViewHolder.tvTypeContentOne.setText(spannableString3);
                }
                if (this.dataList.get(i).getEat().getCountsTime() > 0) {
                    petDailyDataViewHolder.tvTypeContentTwo.setText(this.mContext.getResources().getString(R.string.Recent_eat) + " " + TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, this.dataList.get(i).getEat().getCountsTime()));
                } else {
                    petDailyDataViewHolder.tvTypeContentTwo.setText("");
                }
            } else if (feederChart2 == 1 || feederChart2 == 2) {
                petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getAmountWithUnitSpannableString(this.mContext.getResources().getString(R.string.About), this.dataList.get(i).getEat().getGrams(), f));
                if (this.dataList.get(i).getEat().getTime() > 0) {
                    petDailyDataViewHolder.tvTypeContentTwo.setText(this.mContext.getResources().getString(R.string.Recent_eat) + " " + TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, this.dataList.get(i).getEat().getTime()));
                } else {
                    petDailyDataViewHolder.tvTypeContentTwo.setText("");
                }
            }
            petDailyDataViewHolder.llPetAvatar.setVisibility(4);
            return;
        }
        if (PetDataCard.CardType.TOILET.equals(this.dataList.get(i).getKey())) {
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.toilet_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Go_to_the_toilet));
            if (this.dataList.get(i).getToilet().getCount() > 1) {
                resources3 = this.mContext.getResources();
                i4 = R.string.Unit_times;
            } else {
                resources3 = this.mContext.getResources();
                i4 = R.string.Unit_time;
            }
            String string3 = resources3.getString(i4);
            if (this.dataList.get(i).getToilet().getPetIds() != null) {
                if (this.dataList.get(i).getToilet().getPetIds().size() > 1) {
                    spannableString = new SpannableString(this.mContext.getResources().getString(R.string.Total_food_amount, String.valueOf(this.dataList.get(i).getToilet().getCount())) + string3);
                } else {
                    spannableString = new SpannableString(String.valueOf(this.dataList.get(i).getToilet().getCount()) + string3);
                }
            } else {
                spannableString = new SpannableString(0 + string3);
            }
            if (!TextUtils.isEmpty(spannableString.toString())) {
                spannableString.setSpan(new RelativeSizeSpan(f), spannableString.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getToilet().getCount())), spannableString.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getToilet().getCount())) + String.valueOf(this.dataList.get(i).getToilet().getCount()).length() + string3.length(), 33);
                spannableString.setSpan(new StyleSpan(1), spannableString.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getToilet().getCount())), spannableString.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getToilet().getCount())) + String.valueOf(this.dataList.get(i).getToilet().getCount()).length() + string3.length(), 33);
                petDailyDataViewHolder.tvTypeContentOne.setText(spannableString);
            }
            int time = this.dataList.get(i).getToilet().getTime();
            if (time >= 0) {
                String strSecondsToTimeStringWithUnit = TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, time);
                petDailyDataViewHolder.tvTypeContentTwo.setText(this.mContext.getResources().getString(R.string.Recent_time) + " " + strSecondsToTimeStringWithUnit);
                petDailyDataViewHolder.tvTypeContentTwo.setVisibility(0);
            } else {
                petDailyDataViewHolder.tvTypeContentTwo.setVisibility(4);
            }
            List<PetDataCard> list = this.dataList;
            if (list != null && list.get(i).getToilet().getPetIds() != null && this.dataList.get(i).getToilet().getPetIds().size() > 1) {
                int size3 = this.dataList.get(i).getToilet().getPetIds().size() > 3 ? 3 : this.dataList.get(i).getToilet().getPetIds().size();
                for (int i7 = 0; i7 < size3; i7++) {
                    Pet petById = UserInforUtils.getPetById(this.dataList.get(i).getToilet().getPetIds().get(i7));
                    if (petById != null) {
                        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(petById.getAvatar()).imageView((ImageView) arrayList.get(i7)).errorPic(petById.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
                    }
                }
                petDailyDataViewHolder.llPetAvatar.setVisibility(0);
                return;
            }
            petDailyDataViewHolder.llPetAvatar.setVisibility(4);
            return;
        }
        if (PetDataCard.CardType.ACTIVITY.equals(this.dataList.get(i).getKey())) {
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.activity_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Activity));
            List<PetDataCard> list2 = this.dataList;
            if (list2 == null || list2.get(i).getActivity().getPetIds() == null) {
                petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getTimeWithUnitSpannableString(this.mContext, "", 0, f, true));
            } else {
                petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getTimeWithUnitSpannableString(this.mContext, this.dataList.get(i).getActivity().getPetIds().size() > 1 ? this.mContext.getResources().getString(R.string.D3_record_amount_prompt, "") : "", this.dataList.get(i).getActivity().getTime(), f, true));
            }
            petDailyDataViewHolder.tvTypeContentTwo.setText("");
            List<PetDataCard> list3 = this.dataList;
            if (list3 != null && list3.get(i).getActivity().getPetIds() != null && this.dataList.get(i).getActivity().getPetIds().size() > 1) {
                int size4 = this.dataList.get(i).getActivity().getPetIds().size() > 3 ? 3 : this.dataList.get(i).getActivity().getPetIds().size();
                while (i < size4) {
                    Pet petById2 = UserInforUtils.getPetById(this.dataList.get(i).getActivity().getPetIds().get(i));
                    if (petById2 != null) {
                        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(petById2.getAvatar()).imageView((ImageView) arrayList.get(i)).errorPic(petById2.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
                    }
                    i++;
                }
                petDailyDataViewHolder.llPetAvatar.setVisibility(4);
                return;
            }
            petDailyDataViewHolder.llPetAvatar.setVisibility(4);
            return;
        }
        if (PetDataCard.CardType.SLEEP.equals(this.dataList.get(i).getKey())) {
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.sleep_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Sleep));
            List<PetDataCard> list4 = this.dataList;
            if (list4 == null || list4.get(i).getSleep().getPetIds() == null) {
                petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getTimeWithUnitSpannableString(this.mContext, "", 0, f, true));
            } else {
                petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getTimeWithUnitSpannableString(this.mContext, this.dataList.get(i).getSleep().getPetIds().size() > 1 ? this.mContext.getResources().getString(R.string.D3_record_amount_prompt, "") : "", this.dataList.get(i).getSleep().getTime(), f, true));
            }
            petDailyDataViewHolder.tvTypeContentTwo.setText("");
            List<PetDataCard> list5 = this.dataList;
            if (list5 != null && list5.get(i).getSleep().getPetIds() != null && this.dataList.get(i).getSleep().getPetIds().size() > 1) {
                int size5 = this.dataList.get(i).getSleep().getPetIds().size() > 3 ? 3 : this.dataList.get(i).getSleep().getPetIds().size();
                for (int i8 = 0; i8 < size5; i8++) {
                    Pet petById3 = UserInforUtils.getPetById(this.dataList.get(i).getSleep().getPetIds().get(i8));
                    if (petById3 != null) {
                        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(petById3.getAvatar()).imageView((ImageView) arrayList.get(i8)).errorPic(petById3.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
                    }
                }
                petDailyDataViewHolder.llPetAvatar.setVisibility(0);
                return;
            }
            petDailyDataViewHolder.llPetAvatar.setVisibility(4);
            return;
        }
        if (PetDataCard.CardType.WEIGHT.equals(this.dataList.get(i).getKey())) {
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.weight_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Weight));
            petDailyDataViewHolder.tvTypeContentTwo.setText("");
            if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                resources2 = this.mContext.getResources();
                i3 = R.string.Unit_lb;
            } else {
                resources2 = this.mContext.getResources();
                i3 = R.string.Unit_kg;
            }
            String string4 = resources2.getString(i3);
            petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getSizeAndBoldSpannableString(0 + string4, 0 + string4, f));
            if (this.dataList.get(i).getPetWeight() != null && this.dataList.get(i).getPetWeight().size() > 0) {
                String petId = this.dataList.get(i).getPetWeight().get(0).getPetId();
                double weight = this.dataList.get(i).getPetWeight().get(0).getWeight();
                Pet petById4 = UserInforUtils.getPetById(petId);
                if (petById4 != null) {
                    ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(petById4.getAvatar()).imageView((ImageView) arrayList.get(0)).errorPic(petById4.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
                    double dDoubleToDouble = UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? CommonUtil.doubleToDouble(CommonUtil.KgToLb(weight)) : CommonUtil.doubleToDouble(weight);
                    petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getSizeAndBoldSpannableString(dDoubleToDouble + string4, dDoubleToDouble + string4, f));
                }
                double dDoubleToDouble2 = UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? CommonUtil.doubleToDouble(CommonUtil.KgToLb(weight)) : CommonUtil.doubleToDouble(weight);
                petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getSizeAndBoldSpannableString(dDoubleToDouble2 + string4, dDoubleToDouble2 + string4, f));
                petDailyDataViewHolder.llPetAvatar.setVisibility(0);
                return;
            }
            petDailyDataViewHolder.tvTypeContentOne.setText(StatisticsUtils.getSizeAndBoldSpannableString(0 + string4, 0 + string4, f));
            petDailyDataViewHolder.llPetAvatar.setVisibility(4);
            return;
        }
        if (PetDataCard.CardType.WALK_PET.equals(this.dataList.get(i).getKey())) {
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.walk_pet_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Walkdog));
            TextView textView = petDailyDataViewHolder.tvTypeContentOne;
            Context context = this.mContext;
            int time2 = this.dataList.get(i).getWalkpet().getTime();
            if (zEquals) {
                f -= 0.4f;
            }
            textView.setText(StatisticsUtils.getTimeWithSecWithUnitSpannableString(context, "", time2, f, true));
            petDailyDataViewHolder.tvTypeContentTwo.setText(CommonUtils.getAppContext().getResources().getString(R.string.Today_walking_pet_duration));
            petDailyDataViewHolder.llPetAvatar.setVisibility(4);
            return;
        }
        if (PetDataCard.CardType.DRINK.equals(this.dataList.get(i).getKey())) {
            petDailyDataViewHolder.ivDataType.setImageResource(R.drawable.drink_data_icon);
            petDailyDataViewHolder.tvTypeName.setText(this.mContext.getString(R.string.Drink_water));
            if (this.dataList.get(i).getDrink().getCount() > 1) {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_times;
            } else {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_time;
            }
            String string5 = resources.getString(i2);
            SpannableString spannableString4 = new SpannableString(this.dataList.get(i).getDrink().getCount() + string5);
            if (!TextUtils.isEmpty(spannableString4.toString())) {
                spannableString4.setSpan(new RelativeSizeSpan(f), spannableString4.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getDrink().getCount())), spannableString4.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getDrink().getCount())) + String.valueOf(this.dataList.get(i).getDrink().getCount()).length() + string5.length(), 33);
                spannableString4.setSpan(new StyleSpan(1), spannableString4.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getDrink().getCount())), spannableString4.toString().lastIndexOf(String.valueOf(this.dataList.get(i).getDrink().getCount())) + String.valueOf(this.dataList.get(i).getDrink().getCount()).length() + string5.length(), 33);
                petDailyDataViewHolder.tvTypeContentOne.setText(spannableString4);
            }
            if (TextUtils.isEmpty(this.dataList.get(i).getDrink().getTime())) {
                petDailyDataViewHolder.tvTypeContentTwo.setText("");
                return;
            }
            if (!TextUtils.isEmpty(this.dataList.get(i).getDrink().getTime()) && this.dataList.get(i).getDrink().getTime().contains(":")) {
                petDailyDataViewHolder.tvTypeContentTwo.setText(this.mContext.getResources().getString(R.string.Recently_drunk_water) + " " + this.dataList.get(i).getDrink().getTime());
                return;
            }
            TextView textView2 = petDailyDataViewHolder.tvTypeContentTwo;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getResources().getString(R.string.Recently_drunk_water));
            sb.append(" ");
            sb.append(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, TextUtils.isEmpty(this.dataList.get(i).getDrink().getTime()) ? 0 : Integer.parseInt(this.dataList.get(i).getDrink().getTime())));
            textView2.setText(sb.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClick(this.dataList.get(i));
        }
    }

    public final /* synthetic */ boolean lambda$onBindViewHolder$1(final PetDailyDataViewHolder petDailyDataViewHolder, View view, MotionEvent motionEvent) {
        Disposable disposable;
        if (motionEvent.getActionMasked() == 0) {
            this.time = System.currentTimeMillis();
            this.lastX = motionEvent.getRawX();
            this.lastY = motionEvent.getRawY();
            this.dLastX = motionEvent.getRawX();
            this.dLastY = motionEvent.getRawY();
            this.disposable = Observable.timer(800L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.home.adapter.PetDailyDataAdapter.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    PetDailyDataAdapter petDailyDataAdapter = PetDailyDataAdapter.this;
                    float fAbs = Math.abs(petDailyDataAdapter.dLastX - petDailyDataAdapter.lastX);
                    PetDailyDataAdapter petDailyDataAdapter2 = PetDailyDataAdapter.this;
                    float fAbs2 = Math.abs(petDailyDataAdapter2.dLastY - petDailyDataAdapter2.lastY);
                    if (fAbs >= 50.0f || fAbs2 >= 50.0f || PetDailyDataAdapter.this.onItemDragListener == null) {
                        return;
                    }
                    ((Vibrator) CommonUtils.getAppContext().getSystemService("vibrator")).vibrate(100L);
                    PetDailyDataAdapter.this.onItemDragListener.onDrag(petDailyDataViewHolder);
                    Disposable disposable2 = PetDailyDataAdapter.this.disposable;
                    if (disposable2 == null || disposable2.isDisposed()) {
                        return;
                    }
                    PetDailyDataAdapter.this.disposable.dispose();
                    PetDailyDataAdapter.this.disposable = null;
                }
            });
            return false;
        }
        if (motionEvent.getActionMasked() == 2) {
            this.lastX = motionEvent.getRawX();
            this.lastY = motionEvent.getRawY();
            return false;
        }
        if ((motionEvent.getActionMasked() != 1 && motionEvent.getActionMasked() != 3) || (disposable = this.disposable) == null || disposable.isDisposed()) {
            return false;
        }
        this.disposable.dispose();
        this.disposable = null;
        return false;
    }

    public PetsManageAdapter.OnItemDragListener getOnItemDragListener() {
        return this.onItemDragListener;
    }

    public void setOnItemDragListener(PetsManageAdapter.OnItemDragListener onItemDragListener) {
        this.onItemDragListener = onItemDragListener;
    }

    @Override // com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter
    public boolean onItemMove(int i, int i2) {
        Collections.swap(this.dataList, i, i2);
        notifyItemMoved(i, i2);
        notifyItemRangeChanged(Math.min(i, i2), Math.abs(i - i2) + 1);
        DeviceCenterUtils.changeDataListSort(i, i2);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataList.size();
    }

    public static class PetDailyDataViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivDataType;
        public ImageView ivPetAvatarOne;
        public ImageView ivPetAvatarThree;
        public ImageView ivPetAvatarTwo;
        public RelativeLayout llPetAvatar;
        public RelativeLayout rlParentPanel;
        public TextView tvTypeContentOne;
        public TextView tvTypeContentTwo;
        public TextView tvTypeName;

        public PetDailyDataViewHolder(@NonNull View view) {
            super(view);
            this.ivDataType = (ImageView) view.findViewById(R.id.iv_data_type);
            this.tvTypeName = (TextView) view.findViewById(R.id.tv_type_name);
            this.tvTypeContentOne = (TextView) view.findViewById(R.id.tv_type_content_one);
            this.tvTypeContentTwo = (TextView) view.findViewById(R.id.tv_type_content_two);
            this.ivPetAvatarOne = (ImageView) view.findViewById(R.id.iv_pet_avatar_one);
            this.ivPetAvatarTwo = (ImageView) view.findViewById(R.id.iv_pet_avatar_two);
            this.ivPetAvatarThree = (ImageView) view.findViewById(R.id.iv_pet_avatar_three);
            this.llPetAvatar = (RelativeLayout) view.findViewById(R.id.ll_pet_avatar);
            this.rlParentPanel = (RelativeLayout) view.findViewById(R.id.rl_parent_panel);
        }
    }
}

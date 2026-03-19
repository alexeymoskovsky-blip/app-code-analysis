package com.petkit.android.activities.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jess.arms.utils.DataHelper;
import com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.home.NewCardOnClickListener;
import com.petkit.android.activities.home.adapter.holder.HomeVirtualAreaViewHolder;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.adapter.model.NewHomeCardData;
import com.petkit.android.activities.home.adapter.model.ToDoBean;
import com.petkit.android.activities.home.adapter.newholder.AqHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.Aqh1HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.AqrHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.CTW3HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.CozyHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.D1HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.D2HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.D3HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.D4HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.D4hHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.D4sHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.FitHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.GoHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.HgHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.K2HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.K3HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.MateHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.P3HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.R2HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.T3HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.T4HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.T5HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.W5HomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.newholder.W7hHomeLargeDeviceViewHolder;
import com.petkit.android.activities.home.mode.CardRankResult;
import com.petkit.android.activities.home.utils.HomeCardDiffCallback;
import com.petkit.android.activities.pet.adapter.PetsManageAdapter;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.t7.adp.T7HomeLargeDeviceViewHolder;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.widget.MarqueeTextView;
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
public class HomePageLargeDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    public int currentTheme;
    public List<NewHomeCardData> deviceDataList;
    public final NewCardOnClickListener listener;
    public final Context mContext;
    public List<NewHomeCardData> oldDeviceDataList;
    public PetsManageAdapter.OnItemDragListener onItemDragListener;
    public long time = 0;
    public float lastX = 0.0f;
    public float lastY = 0.0f;
    public float dLastX = 0.0f;
    public float dLastY = 0.0f;
    public Disposable disposable = null;

    public void setOnItemDragListener(PetsManageAdapter.OnItemDragListener onItemDragListener) {
        this.onItemDragListener = onItemDragListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        T7HomeLargeDeviceViewHolder t7HomeLargeDeviceViewHolder;
        ImageView imageView;
        Bitmap bitmap;
        Bitmap bitmap2;
        Bitmap bitmap3;
        super.onViewRecycled(viewHolder);
        if (viewHolder instanceof W7hHomeLargeDeviceViewHolder) {
            RoundImageview roundImageview = ((W7hHomeLargeDeviceViewHolder) viewHolder).ivVideo;
            if (roundImageview != null) {
                Glide.clear(roundImageview);
                return;
            }
            return;
        }
        if (viewHolder instanceof T6HomeLargeDeviceViewHolder) {
            T6HomeLargeDeviceViewHolder t6HomeLargeDeviceViewHolder = (T6HomeLargeDeviceViewHolder) viewHolder;
            ImageView imageView2 = t6HomeLargeDeviceViewHolder.ivVideo;
            if (imageView2 != null) {
                Drawable drawable = imageView2.getDrawable();
                if ((drawable instanceof BitmapDrawable) && (bitmap3 = ((BitmapDrawable) drawable).getBitmap()) != null && !bitmap3.isRecycled()) {
                    bitmap3.recycle();
                }
                t6HomeLargeDeviceViewHolder.ivVideo.setImageDrawable(null);
                return;
            }
            return;
        }
        if (viewHolder instanceof T5HomeLargeDeviceViewHolder) {
            T5HomeLargeDeviceViewHolder t5HomeLargeDeviceViewHolder = (T5HomeLargeDeviceViewHolder) viewHolder;
            ImageView imageView3 = t5HomeLargeDeviceViewHolder.ivVideo;
            if (imageView3 != null) {
                Drawable drawable2 = imageView3.getDrawable();
                if ((drawable2 instanceof BitmapDrawable) && (bitmap2 = ((BitmapDrawable) drawable2).getBitmap()) != null && !bitmap2.isRecycled()) {
                    bitmap2.recycle();
                }
                t5HomeLargeDeviceViewHolder.ivVideo.setImageDrawable(null);
                return;
            }
            return;
        }
        if (viewHolder instanceof D4shHomeLargeDeviceViewHolder) {
            RoundImageview roundImageview2 = ((D4shHomeLargeDeviceViewHolder) viewHolder).ivVideo;
            if (roundImageview2 != null) {
                Glide.clear(roundImageview2);
                return;
            }
            return;
        }
        if (viewHolder instanceof D4hHomeLargeDeviceViewHolder) {
            RoundImageview roundImageview3 = ((D4hHomeLargeDeviceViewHolder) viewHolder).ivVideo;
            if (roundImageview3 != null) {
                Glide.clear(roundImageview3);
                return;
            }
            return;
        }
        if (!(viewHolder instanceof T7HomeLargeDeviceViewHolder) || (imageView = (t7HomeLargeDeviceViewHolder = (T7HomeLargeDeviceViewHolder) viewHolder).ivVideo) == null) {
            return;
        }
        Drawable drawable3 = imageView.getDrawable();
        if ((drawable3 instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) drawable3).getBitmap()) != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        t7HomeLargeDeviceViewHolder.ivVideo.setImageDrawable(null);
    }

    public HomePageLargeDeviceAdapter(Context context, NewCardOnClickListener newCardOnClickListener, List<NewHomeCardData> list) {
        this.listener = newCardOnClickListener;
        this.mContext = context;
        this.deviceDataList = list;
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        List<NewHomeCardData> list = this.deviceDataList;
        if (list == null || i < 0 || i >= list.size()) {
            return -1L;
        }
        NewHomeCardData newHomeCardData = this.deviceDataList.get(i);
        if (newHomeCardData.getType().equals(Constants.HOME_CARD_MAIN_DEVICE) && newHomeCardData.getHomeDeviceData() != null) {
            return newHomeCardData.getHomeDeviceData().getId() + ((long) newHomeCardData.getHomeDeviceData().getType().hashCode());
        }
        if (newHomeCardData.getType().equals(Constants.HOME_CARD_VIRTUAL_AREA)) {
            return 1679946081;
        }
        return -1L;
    }

    public void updateAdapter(List<NewHomeCardData> list) {
        DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(new HomeCardDiffCallback(new ArrayList(this.deviceDataList), list));
        this.deviceDataList.clear();
        this.deviceDataList.addAll(list);
        diffResultCalculateDiff.dispatchUpdatesTo(this);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PetkitLog.d("large----------->", String.valueOf(i));
        if (i == 14) {
            return new W5HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_w5_item, viewGroup, false));
        }
        if (i != 100001) {
            switch (i) {
                case 1:
                    return new FitHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_p3_item, viewGroup, false));
                case 2:
                    return new MateHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_r2_item, viewGroup, false));
                case 3:
                    return new GoHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_r2_item, viewGroup, false));
                case 4:
                    return new D1HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_d4s_item, viewGroup, false));
                case 5:
                    return new CozyHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_r2_item, viewGroup, false));
                case 6:
                    return new D2HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_d4s_item, viewGroup, false));
                case 7:
                    return new T3HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_t3_item, viewGroup, false));
                case 8:
                    return new K2HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_k2_item, viewGroup, false));
                case 9:
                    return new D3HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_d4s_item, viewGroup, false));
                case 10:
                    return new AqHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_aqr_item, viewGroup, false));
                case 11:
                    return new D4HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_d4s_item, viewGroup, false));
                case 12:
                    return new P3HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_p3_item, viewGroup, false));
                default:
                    switch (i) {
                        case 16:
                            return new K3HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_k3_item, viewGroup, false));
                        case 17:
                            return new AqrHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_aqr_item, viewGroup, false));
                        case 18:
                            return new R2HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_r2_item, viewGroup, false));
                        case 19:
                            return new Aqh1HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_aqh1_item, viewGroup, false));
                        case 20:
                            return new D4sHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_d4s_item, viewGroup, false));
                        case 21:
                            return new T5HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_t6_item, viewGroup, false));
                        case 22:
                            return new HgHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_hg_item, viewGroup, false));
                        default:
                            switch (i) {
                                case 24:
                                    return new CTW3HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_ctw3_item, viewGroup, false));
                                case 25:
                                    return new D4shHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_d4sh_item, viewGroup, false));
                                case 26:
                                    return new D4hHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_d4sh_item, viewGroup, false));
                                case 27:
                                    return new T6HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_t6_item, viewGroup, false));
                                case 28:
                                    return new T7HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_t7_item, viewGroup, false));
                                case 29:
                                    return new W7hHomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_w7h_item, viewGroup, false));
                                default:
                                    return new T4HomeLargeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_large_home_page_t4_item, viewGroup, false));
                            }
                    }
            }
        }
        return new HomeVirtualAreaViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_virtual_area_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility", "MissingPermission"})
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        NewHomeCardData newHomeCardData = this.deviceDataList.get(i);
        if (newHomeCardData.getType() == Constants.HOME_CARD_MAIN_DEVICE) {
            final HomeDeviceData homeDeviceData = newHomeCardData.getHomeDeviceData();
            PetkitLog.d("HomePageFragment", this.deviceDataList.get(i).getHomeDeviceData().getType());
            int i2 = 1;
            if ((homeDeviceData != null && homeDeviceData.getData() != null) || homeDeviceData.getId() == -1) {
                BaseHomeLargeDeviceViewHolder baseHomeLargeDeviceViewHolder = (BaseHomeLargeDeviceViewHolder) viewHolder;
                baseHomeLargeDeviceViewHolder.updateData(homeDeviceData, i, this.listener);
                baseHomeLargeDeviceViewHolder.setIsEmptyView(false);
            } else {
                ((BaseHomeLargeDeviceViewHolder) viewHolder).setIsEmptyView(true);
            }
            BaseHomeLargeDeviceViewHolder baseHomeLargeDeviceViewHolder2 = (BaseHomeLargeDeviceViewHolder) viewHolder;
            baseHomeLargeDeviceViewHolder2.parentPanel.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$onBindViewHolder$1(viewHolder, view, motionEvent);
                }
            });
            if (homeDeviceData.getData() != null) {
                RelativeLayout relativeLayout = baseHomeLargeDeviceViewHolder2.rlConsumableTip;
                if (relativeLayout != null) {
                    relativeLayout.setVisibility(!homeDeviceData.getData().getToDoList().isEmpty() ? 0 : 8);
                }
                LinearLayout linearLayout = baseHomeLargeDeviceViewHolder2.llConsumableTip;
                if (linearLayout != null) {
                    linearLayout.removeAllViews();
                    int i3 = 0;
                    while (i3 < homeDeviceData.getData().getToDoList().size()) {
                        ToDoBean toDoBean = homeDeviceData.getData().getToDoList().get(i3);
                        baseHomeLargeDeviceViewHolder2.llConsumableTip.addView(getItem(viewHolder.itemView.getContext(), homeDeviceData, homeDeviceData.getId(), getItemViewType(i), toDoBean, i3 == homeDeviceData.getData().getToDoList().size() - i2, this.listener));
                        i3++;
                        i2 = 1;
                    }
                }
            }
            if (viewHolder instanceof T4HomeLargeDeviceViewHolder) {
                ((T4HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda13
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$2(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda22
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$3(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof T3HomeLargeDeviceViewHolder) {
                ((T3HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda23
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$4(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda24
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$5(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof W5HomeLargeDeviceViewHolder) {
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda25
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$6(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D1HomeLargeDeviceViewHolder) {
                ((D1HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda26
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$7(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda27
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$8(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D2HomeLargeDeviceViewHolder) {
                ((D2HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda28
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$9(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda29
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$10(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D3HomeLargeDeviceViewHolder) {
                ((D3HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$11(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$12(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D4HomeLargeDeviceViewHolder) {
                ((D4HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$13(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$14(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D4sHomeLargeDeviceViewHolder) {
                ((D4sHomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$15(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$16(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D4shHomeLargeDeviceViewHolder) {
                ((D4shHomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda9
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$17(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda10
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$18(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D4hHomeLargeDeviceViewHolder) {
                ((D4hHomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda11
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$19(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda12
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$20(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof T5HomeLargeDeviceViewHolder) {
                ((T5HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda14
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$21(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda15
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$22(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof T6HomeLargeDeviceViewHolder) {
                ((T6HomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda16
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$23(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda17
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$24(homeDeviceData, view);
                    }
                });
                return;
            } else if (viewHolder instanceof W7hHomeLargeDeviceViewHolder) {
                ((W7hHomeLargeDeviceViewHolder) viewHolder).rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda18
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$25(homeDeviceData, view);
                    }
                });
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda19
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$26(homeDeviceData, view);
                    }
                });
                return;
            } else if (viewHolder instanceof HgHomeLargeDeviceViewHolder) {
                ((BaseHomeLargeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda20
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$27(homeDeviceData, view);
                    }
                });
                return;
            } else {
                baseHomeLargeDeviceViewHolder2.parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda21
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$28(homeDeviceData, view);
                    }
                });
                return;
            }
        }
        ((HomeVirtualAreaViewHolder) viewHolder).updateData(this.deviceDataList.get(i).getVirtualList(), this.listener);
    }

    public final /* synthetic */ boolean lambda$onBindViewHolder$1(final RecyclerView.ViewHolder viewHolder, View view, MotionEvent motionEvent) {
        Disposable disposable;
        if (motionEvent.getActionMasked() == 0) {
            this.time = System.currentTimeMillis();
            this.lastX = motionEvent.getRawX();
            this.lastY = motionEvent.getRawY();
            this.dLastX = motionEvent.getRawX();
            this.dLastY = motionEvent.getRawY();
            this.disposable = Observable.timer(800L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda30
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$onBindViewHolder$0(viewHolder, (Long) obj);
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

    public final /* synthetic */ void lambda$onBindViewHolder$0(RecyclerView.ViewHolder viewHolder, Long l) throws Exception {
        float fAbs = Math.abs(this.dLastX - this.lastX);
        float fAbs2 = Math.abs(this.dLastY - this.lastY);
        if (fAbs >= 50.0f || fAbs2 >= 50.0f || this.onItemDragListener == null) {
            return;
        }
        ((Vibrator) CommonUtils.getAppContext().getSystemService("vibrator")).vibrate(100L);
        this.onItemDragListener.onDrag(viewHolder);
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$2(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$4(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$5(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$6(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$7(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$8(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$9(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$10(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$11(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$12(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$13(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$14(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$15(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$16(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$17(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$18(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$19(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$20(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$21(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$22(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$23(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$24(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$25(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$26(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$27(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$28(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.deviceDataList.size();
    }

    @Override // com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter
    public boolean onItemMove(int i, int i2) {
        if (this.deviceDataList.get(i).getType() == Constants.HOME_CARD_VIRTUAL_AREA || this.deviceDataList.get(i2).getType() == Constants.HOME_CARD_VIRTUAL_AREA) {
            return false;
        }
        PetkitLog.d("onItemMove", "fromPosition:" + i + " fromType:" + getItemViewType(i) + " toPosition:" + i2 + " toType:" + getItemViewType(i2));
        if (getIndexByDeviceTypeAndId(-1, 0L) == -1) {
            DeviceCenterUtils.changeDeviceListSort(i, i2);
        } else {
            DeviceCenterUtils.changeDeviceListSort(i > 2 ? i - 1 : i, i2 > 2 ? i2 - 1 : i2);
        }
        Collections.swap(this.deviceDataList, i, i2);
        notifyItemMoved(i, i2);
        CardRankResult cardRankResult = (CardRankResult) new Gson().fromJson(DataHelper.getStringSF(this.mContext, Constants.DEVICE_CARD_RANK + CommonUtils.getCurrentUserId() + FamilyUtils.getInstance().getCurrentFamilyId(this.mContext)), CardRankResult.class);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < cardRankResult.getData().size(); i5++) {
            if ((cardRankResult.getData().get(i5).getVirtual() == 1 && cardRankResult.getData().get(i5).getTypeName().equalsIgnoreCase(this.deviceDataList.get(i).getHomeDeviceData().getType()) && cardRankResult.getData().get(i5).getDeviceId() == this.deviceDataList.get(i).getHomeDeviceData().getId()) || (cardRankResult.getData().get(i5).getVirtual() == 0 && cardRankResult.getData().get(i5).getDeviceType().intValue() == CommonUtils.getDeviceTypeByString(this.deviceDataList.get(i).getHomeDeviceData().getType()) && cardRankResult.getData().get(i5).getDeviceId() == this.deviceDataList.get(i).getHomeDeviceData().getId())) {
                i3 = i5;
            } else if ((cardRankResult.getData().get(i5).getVirtual() == 1 && cardRankResult.getData().get(i5).getTypeName().equalsIgnoreCase(this.deviceDataList.get(i2).getHomeDeviceData().getType()) && cardRankResult.getData().get(i5).getDeviceId() == this.deviceDataList.get(i2).getHomeDeviceData().getId()) || (cardRankResult.getData().get(i5).getVirtual() == 0 && cardRankResult.getData().get(i5).getDeviceType().intValue() == CommonUtils.getDeviceTypeByString(this.deviceDataList.get(i2).getHomeDeviceData().getType()) && cardRankResult.getData().get(i5).getDeviceId() == this.deviceDataList.get(i2).getHomeDeviceData().getId())) {
                i4 = i5;
            }
        }
        Collections.swap(cardRankResult.getData(), i3, i4);
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onItemMove(cardRankResult, 2);
        }
        return true;
    }

    @Override // com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter
    public void onItemDismiss(int i) {
        this.deviceDataList.remove(i);
        notifyItemRemoved(i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0056  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getItemViewType(int r25) {
        /*
            Method dump skipped, instruction units count: 884
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter.getItemViewType(int):int");
    }

    public int getIndexByDeviceTypeAndId(int i, long j) {
        PetkitLog.d(getClass().getSimpleName(), "getIndexByDeviceTypeAndId:" + i + " deviceID:" + j);
        if (this.deviceDataList == null) {
            return -1;
        }
        int i2 = 0;
        if (i == -1) {
            while (i2 < this.deviceDataList.size()) {
                if (i == getItemViewType(i2)) {
                    return i2;
                }
                i2++;
            }
        } else {
            while (i2 < this.deviceDataList.size()) {
                if (i == getItemViewType(i2) && this.deviceDataList.get(i2).getHomeDeviceData().getId() == j) {
                    return i2;
                }
                i2++;
            }
        }
        return -1;
    }

    public List<NewHomeCardData> getDeviceDataList() {
        return this.deviceDataList;
    }

    public View getItem(Context context, HomeDeviceData homeDeviceData, final long j, final int i, final ToDoBean toDoBean, boolean z, final NewCardOnClickListener newCardOnClickListener) {
        final View viewInflate = LayoutInflater.from(context).inflate(R.layout.view_consumable_tip, (ViewGroup) null);
        viewInflate.findViewById(R.id.v_start).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageLargeDeviceAdapter.lambda$getItem$29(newCardOnClickListener, j, i, viewInflate, toDoBean, view);
            }
        });
        MarqueeTextView marqueeTextView = (MarqueeTextView) viewInflate.findViewById(R.id.tv_title);
        marqueeTextView.setText(toDoBean.getDesc());
        marqueeTextView.setSelected(true);
        marqueeTextView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageLargeDeviceAdapter.lambda$getItem$30(j, i, newCardOnClickListener, viewInflate, toDoBean, view);
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_time)).setText(DateUtil.getTodoRemindDateTime(this.mContext, toDoBean.getTime()));
        viewInflate.findViewById(R.id.v_line).setVisibility(z ? 8 : 0);
        return viewInflate;
    }

    public static /* synthetic */ void lambda$getItem$29(NewCardOnClickListener newCardOnClickListener, long j, int i, View view, ToDoBean toDoBean, View view2) {
        String str;
        if (newCardOnClickListener != null) {
            DeviceInfos deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(j, i);
            if (deviceInfosFindDeviceInfo != null && deviceInfosFindDeviceInfo.getRelation() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds().size() > 0) {
                str = deviceInfosFindDeviceInfo.getRelation().getPetIds().get(0);
            } else {
                str = "";
            }
            newCardOnClickListener.onConsumableClick(view, j, i, toDoBean, str);
        }
    }

    public static /* synthetic */ void lambda$getItem$30(long j, int i, NewCardOnClickListener newCardOnClickListener, View view, ToDoBean toDoBean, View view2) {
        String str;
        DeviceInfos deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(j, i);
        if (deviceInfosFindDeviceInfo != null && deviceInfosFindDeviceInfo.getRelation() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds().size() > 0) {
            str = deviceInfosFindDeviceInfo.getRelation().getPetIds().get(0);
        } else {
            str = "";
        }
        String str2 = str;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onConsumableTitleClick(view, j, i, toDoBean, str2);
        }
    }

    public final boolean HDevice(HomeDeviceData homeDeviceData) {
        if (homeDeviceData == null || homeDeviceData.getData() == null || TextUtils.isEmpty(homeDeviceData.getType())) {
            return false;
        }
        String type = homeDeviceData.getType();
        type.hashCode();
        switch (type) {
        }
        return false;
    }
}

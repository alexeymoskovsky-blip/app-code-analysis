package com.petkit.android.activities.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jess.arms.utils.DataHelper;
import com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.home.NewCardOnClickListener;
import com.petkit.android.activities.home.adapter.holder.AqHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.Aqh1HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.AqrHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.CTW3HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.CozyHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.D1HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.D2HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.D3HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.D4HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.D4hHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.D4sHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.D4shHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.FitHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.GoHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.HgHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.HomeVirtualAreaViewHolder;
import com.petkit.android.activities.home.adapter.holder.K2HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.K3HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.MateHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.P3HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.R2HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.T3HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.T4HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.T5HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.T6HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.W5HomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.holder.W7hHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.adapter.model.NewHomeCardData;
import com.petkit.android.activities.home.adapter.model.ToDoBean;
import com.petkit.android.activities.home.mode.CardRankResult;
import com.petkit.android.activities.pet.adapter.PetsManageAdapter;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.t7.adp.T7HomeDeviceViewHolder;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class HomePageDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    public int currentTheme;
    public List<NewHomeCardData> deviceDataList;
    public final NewCardOnClickListener listener;
    public final Context mContext;
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
        T7HomeDeviceViewHolder t7HomeDeviceViewHolder;
        ImageView imageView;
        Bitmap bitmap;
        Bitmap bitmap2;
        Bitmap bitmap3;
        super.onViewRecycled(viewHolder);
        if (viewHolder instanceof W7hHomeDeviceViewHolder) {
            RoundImageview roundImageview = ((W7hHomeDeviceViewHolder) viewHolder).ivVideo;
            if (roundImageview != null) {
                Glide.clear(roundImageview);
                return;
            }
            return;
        }
        if (viewHolder instanceof T6HomeDeviceViewHolder) {
            T6HomeDeviceViewHolder t6HomeDeviceViewHolder = (T6HomeDeviceViewHolder) viewHolder;
            RoundImageview roundImageview2 = t6HomeDeviceViewHolder.ivVideo;
            if (roundImageview2 != null) {
                Drawable drawable = roundImageview2.getDrawable();
                if ((drawable instanceof BitmapDrawable) && (bitmap3 = ((BitmapDrawable) drawable).getBitmap()) != null && !bitmap3.isRecycled()) {
                    bitmap3.recycle();
                }
                t6HomeDeviceViewHolder.ivVideo.setImageDrawable(null);
                return;
            }
            return;
        }
        if (viewHolder instanceof T5HomeDeviceViewHolder) {
            T5HomeDeviceViewHolder t5HomeDeviceViewHolder = (T5HomeDeviceViewHolder) viewHolder;
            RoundImageview roundImageview3 = t5HomeDeviceViewHolder.ivVideo;
            if (roundImageview3 != null) {
                Drawable drawable2 = roundImageview3.getDrawable();
                if ((drawable2 instanceof BitmapDrawable) && (bitmap2 = ((BitmapDrawable) drawable2).getBitmap()) != null && !bitmap2.isRecycled()) {
                    bitmap2.recycle();
                }
                t5HomeDeviceViewHolder.ivVideo.setImageDrawable(null);
                return;
            }
            return;
        }
        if (viewHolder instanceof D4shHomeDeviceViewHolder) {
            RoundImageview roundImageview4 = ((D4shHomeDeviceViewHolder) viewHolder).ivVideo;
            if (roundImageview4 != null) {
                Glide.clear(roundImageview4);
                return;
            }
            return;
        }
        if (viewHolder instanceof D4hHomeDeviceViewHolder) {
            RoundImageview roundImageview5 = ((D4hHomeDeviceViewHolder) viewHolder).ivVideo;
            if (roundImageview5 != null) {
                Glide.clear(roundImageview5);
                return;
            }
            return;
        }
        if (!(viewHolder instanceof T7HomeDeviceViewHolder) || (imageView = (t7HomeDeviceViewHolder = (T7HomeDeviceViewHolder) viewHolder).ivVideo) == null) {
            return;
        }
        Drawable drawable3 = imageView.getDrawable();
        if ((drawable3 instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) drawable3).getBitmap()) != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        t7HomeDeviceViewHolder.ivVideo.setImageDrawable(null);
    }

    public HomePageDeviceAdapter(Context context, NewCardOnClickListener newCardOnClickListener, List<NewHomeCardData> list) {
        this.listener = newCardOnClickListener;
        this.mContext = context;
        this.deviceDataList = list;
    }

    public void updateAdapter(List<NewHomeCardData> list) {
        this.deviceDataList = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PetkitLog.d("small----------->", String.valueOf(i));
        if (i != 100001) {
            switch (i) {
                case 1:
                    return new FitHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_fit_item, viewGroup, false));
                case 2:
                    return new MateHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_mate_item, viewGroup, false));
                case 3:
                    return new GoHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_go_item, viewGroup, false));
                case 4:
                    return new D1HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_d1_item, viewGroup, false));
                case 5:
                    return new CozyHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_cozy_item, viewGroup, false));
                case 6:
                    return new D2HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_d2_item, viewGroup, false));
                case 7:
                    return new T3HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_t3_item, viewGroup, false));
                case 8:
                    return new K2HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_k2_item, viewGroup, false));
                case 9:
                    return new D3HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_d3_item, viewGroup, false));
                case 10:
                    return new AqHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_aq_item, viewGroup, false));
                case 11:
                    return new D4HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_d4_item, viewGroup, false));
                case 12:
                    return new P3HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_p3_item, viewGroup, false));
                default:
                    switch (i) {
                        case 14:
                            return new W5HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_w5_item, viewGroup, false));
                        case 15:
                            return new T4HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_t4_item, viewGroup, false));
                        case 16:
                            return new K3HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_k3_item, viewGroup, false));
                        case 17:
                            return new AqrHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_aqr_item, viewGroup, false));
                        case 18:
                            return new R2HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_r2_item, viewGroup, false));
                        case 19:
                            return new Aqh1HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_r2_item, viewGroup, false));
                        case 20:
                            return new D4sHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_d4s_item, viewGroup, false));
                        case 21:
                            return new T5HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_t6_item, viewGroup, false));
                        case 22:
                            return new HgHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_hg_item, viewGroup, false));
                        default:
                            switch (i) {
                                case 24:
                                    return new CTW3HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_ctw3_item, viewGroup, false));
                                case 25:
                                    return new D4shHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_d4sh_item, viewGroup, false));
                                case 26:
                                    return new D4hHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_d4h_item, viewGroup, false));
                                case 27:
                                    return new T6HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_t6_item, viewGroup, false));
                                case 28:
                                    return new T7HomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_t7_item, viewGroup, false));
                                case 29:
                                    return new W7hHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_w7h_item, viewGroup, false));
                                default:
                                    return new FitHomeDeviceViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_page_fit_item, viewGroup, false));
                            }
                    }
            }
        }
        return new HomeVirtualAreaViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_home_virtual_area_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        LinearLayout linearLayout;
        NewHomeCardData newHomeCardData = this.deviceDataList.get(i);
        if (newHomeCardData.getType().equals(Constants.HOME_CARD_MAIN_DEVICE)) {
            final HomeDeviceData homeDeviceData = newHomeCardData.getHomeDeviceData();
            if ((homeDeviceData != null && homeDeviceData.getData() != null) || (homeDeviceData != null && homeDeviceData.getId() == -1)) {
                BaseHomeDeviceViewHolder baseHomeDeviceViewHolder = (BaseHomeDeviceViewHolder) viewHolder;
                baseHomeDeviceViewHolder.updateData(homeDeviceData);
                baseHomeDeviceViewHolder.setIsEmptyView(false);
            } else {
                ((BaseHomeDeviceViewHolder) viewHolder).setIsEmptyView(true);
            }
            BaseHomeDeviceViewHolder baseHomeDeviceViewHolder2 = (BaseHomeDeviceViewHolder) viewHolder;
            baseHomeDeviceViewHolder2.parentPanel.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$onBindViewHolder$0(viewHolder, view, motionEvent);
                }
            });
            if (homeDeviceData.getData() != null && (linearLayout = baseHomeDeviceViewHolder2.llConsumableTip) != null) {
                linearLayout.removeAllViews();
                int i2 = 0;
                while (i2 < homeDeviceData.getData().getToDoList().size()) {
                    baseHomeDeviceViewHolder2.llConsumableTip.addView(getItem(viewHolder.itemView.getContext(), homeDeviceData, homeDeviceData.getId(), getItemViewType(i), homeDeviceData.getData().getToDoList().get(i2), i2 == homeDeviceData.getData().getToDoList().size() - 1, this.listener));
                    i2++;
                }
            }
            if (viewHolder instanceof T4HomeDeviceViewHolder) {
                ((BaseHomeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$1(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof W5HomeDeviceViewHolder) {
                ((BaseHomeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$2(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D3HomeDeviceViewHolder) {
                ((BaseHomeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$3(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof HgHomeDeviceViewHolder) {
                ((BaseHomeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$4(homeDeviceData, view);
                    }
                });
                return;
            }
            if (viewHolder instanceof D4hHomeDeviceViewHolder) {
                ((BaseHomeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$5(homeDeviceData, view);
                    }
                });
                return;
            } else if (viewHolder instanceof D4shHomeDeviceViewHolder) {
                ((BaseHomeDeviceViewHolder) viewHolder).parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$6(homeDeviceData, view);
                    }
                });
                return;
            } else {
                baseHomeDeviceViewHolder2.parentPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda9
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$7(homeDeviceData, view);
                    }
                });
                return;
            }
        }
        ((HomeVirtualAreaViewHolder) viewHolder).updateData(this.deviceDataList.get(i).getVirtualList(), this.listener);
    }

    public final /* synthetic */ boolean lambda$onBindViewHolder$0(RecyclerView.ViewHolder viewHolder, View view, MotionEvent motionEvent) {
        Disposable disposable;
        if (motionEvent.getActionMasked() == 0) {
            this.time = System.currentTimeMillis();
            this.lastX = motionEvent.getRawX();
            this.lastY = motionEvent.getRawY();
            this.dLastX = motionEvent.getRawX();
            this.dLastY = motionEvent.getRawY();
            this.disposable = Observable.timer(800L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter.1
                public final /* synthetic */ RecyclerView.ViewHolder val$holder;

                public AnonymousClass1(RecyclerView.ViewHolder viewHolder2) {
                    viewHolder = viewHolder2;
                }

                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    HomePageDeviceAdapter homePageDeviceAdapter = HomePageDeviceAdapter.this;
                    float fAbs = Math.abs(homePageDeviceAdapter.dLastX - homePageDeviceAdapter.lastX);
                    HomePageDeviceAdapter homePageDeviceAdapter2 = HomePageDeviceAdapter.this;
                    float fAbs2 = Math.abs(homePageDeviceAdapter2.dLastY - homePageDeviceAdapter2.lastY);
                    if (fAbs >= 50.0f || fAbs2 >= 50.0f || HomePageDeviceAdapter.this.onItemDragListener == null) {
                        return;
                    }
                    ((Vibrator) CommonUtils.getAppContext().getSystemService("vibrator")).vibrate(100L);
                    HomePageDeviceAdapter.this.onItemDragListener.onDrag(viewHolder);
                    Disposable disposable2 = HomePageDeviceAdapter.this.disposable;
                    if (disposable2 == null || disposable2.isDisposed()) {
                        return;
                    }
                    HomePageDeviceAdapter.this.disposable.dispose();
                    HomePageDeviceAdapter.this.disposable = null;
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

    /* JADX INFO: renamed from: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$1 */
    public class AnonymousClass1 implements Consumer<Long> {
        public final /* synthetic */ RecyclerView.ViewHolder val$holder;

        public AnonymousClass1(RecyclerView.ViewHolder viewHolder2) {
            viewHolder = viewHolder2;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Long l) throws Exception {
            HomePageDeviceAdapter homePageDeviceAdapter = HomePageDeviceAdapter.this;
            float fAbs = Math.abs(homePageDeviceAdapter.dLastX - homePageDeviceAdapter.lastX);
            HomePageDeviceAdapter homePageDeviceAdapter2 = HomePageDeviceAdapter.this;
            float fAbs2 = Math.abs(homePageDeviceAdapter2.dLastY - homePageDeviceAdapter2.lastY);
            if (fAbs >= 50.0f || fAbs2 >= 50.0f || HomePageDeviceAdapter.this.onItemDragListener == null) {
                return;
            }
            ((Vibrator) CommonUtils.getAppContext().getSystemService("vibrator")).vibrate(100L);
            HomePageDeviceAdapter.this.onItemDragListener.onDrag(viewHolder);
            Disposable disposable2 = HomePageDeviceAdapter.this.disposable;
            if (disposable2 == null || disposable2.isDisposed()) {
                return;
            }
            HomePageDeviceAdapter.this.disposable.dispose();
            HomePageDeviceAdapter.this.disposable = null;
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$1(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$2(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$3(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$4(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$5(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$6(HomeDeviceData homeDeviceData, View view) {
        NewCardOnClickListener newCardOnClickListener = this.listener;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onClick(homeDeviceData, new String[0]);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$7(HomeDeviceData homeDeviceData, View view) {
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
        this.deviceDataList.get(i).getHomeDeviceData();
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
            newCardOnClickListener.onItemMove(cardRankResult, 1);
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
    /* JADX WARN: Removed duplicated region for block: B:209:0x0055  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getItemViewType(int r25) {
        /*
            Method dump skipped, instruction units count: 908
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter.getItemViewType(int):int");
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
        final View viewInflate = LayoutInflater.from(context).inflate(R.layout.view_consumable_tip_small, (ViewGroup) null);
        viewInflate.findViewById(R.id.v_start).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageDeviceAdapter.lambda$getItem$8(newCardOnClickListener, j, i, viewInflate, toDoBean, view);
            }
        });
        MarqueeTextView marqueeTextView = (MarqueeTextView) viewInflate.findViewById(R.id.tv_title);
        marqueeTextView.setText(toDoBean.getDesc() + DateUtil.getTodoRemindDateTime(this.mContext, toDoBean.getTime()));
        marqueeTextView.setSelected(true);
        marqueeTextView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.HomePageDeviceAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageDeviceAdapter.lambda$getItem$9(j, i, newCardOnClickListener, viewInflate, toDoBean, view);
            }
        });
        viewInflate.findViewById(R.id.v_line).setVisibility(z ? 8 : 0);
        return viewInflate;
    }

    public static /* synthetic */ void lambda$getItem$8(NewCardOnClickListener newCardOnClickListener, long j, int i, View view, ToDoBean toDoBean, View view2) {
        String str;
        if (newCardOnClickListener != null) {
            DeviceInfos deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(j, i);
            if (deviceInfosFindDeviceInfo != null && deviceInfosFindDeviceInfo.getRelation() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds().size() > 0) {
                str = deviceInfosFindDeviceInfo.getRelation().getPetIds().get(0);
            } else {
                str = "";
            }
            newCardOnClickListener.onSmallConsumableClick(view, j, i, toDoBean, str);
        }
    }

    public static /* synthetic */ void lambda$getItem$9(long j, int i, NewCardOnClickListener newCardOnClickListener, View view, ToDoBean toDoBean, View view2) {
        String str;
        DeviceInfos deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(j, i);
        if (deviceInfosFindDeviceInfo != null && deviceInfosFindDeviceInfo.getRelation() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds() != null && deviceInfosFindDeviceInfo.getRelation().getPetIds().size() > 0) {
            str = deviceInfosFindDeviceInfo.getRelation().getPetIds().get(0);
        } else {
            str = "";
        }
        String str2 = str;
        if (newCardOnClickListener != null) {
            newCardOnClickListener.onSmallConsumableTitleClick(view, j, i, toDoBean, str2);
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setCurrentTheme(int i) {
        if (this.currentTheme == i) {
            return;
        }
        this.currentTheme = i;
        notifyDataSetChanged();
    }
}

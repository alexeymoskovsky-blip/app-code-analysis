package com.petkit.android.activities.petkitBleDevice.hg.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.text.SpannableString;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter;
import com.petkit.android.activities.pet.adapter.PetsManageAdapter;
import com.petkit.android.activities.petkitBleDevice.hg.mode.ModeData;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class HgModesManagementAdapter extends RecyclerView.Adapter<DeviceMenuViewHolder> implements ItemTouchHelperAdapter {
    public boolean isCentigrade;
    public OnClickListener listener;
    public Context mContext;
    public List<ModeData> menus;
    public PetsManageAdapter.OnItemDragListener onItemDragListener;
    public OnMoveListener onMoveListener;
    public String unitString;
    public Boolean isAllowMove = Boolean.FALSE;
    public long time = 0;
    public float lastX = 0.0f;
    public float lastY = 0.0f;
    public float dLastX = 0.0f;
    public float dLastY = 0.0f;
    public Disposable disposable = null;

    public interface OnClickListener {
        void onClickDelete(ModeData modeData);

        void onViewClick(ModeData modeData, int i);
    }

    public interface OnMoveListener {
        void onMoveItem(int i, int i2);
    }

    public void setOnMoveListener(OnMoveListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }

    public void setOnItemDragListener(PetsManageAdapter.OnItemDragListener onItemDragListener) {
        this.onItemDragListener = onItemDragListener;
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setIsAllowMove(Boolean bool) {
        this.isAllowMove = bool;
        notifyDataSetChanged();
    }

    public void setIsCentigrade(boolean z) {
        this.isCentigrade = z;
        if (!z) {
            this.unitString = this.mContext.getResources().getString(R.string.fahrenheit_temperature);
        } else {
            this.unitString = this.mContext.getResources().getString(R.string.symbol_temperature);
        }
        notifyDataSetChanged();
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public void setData(List<ModeData> list) {
        this.menus.clear();
        this.menus.addAll(list);
        notifyDataSetChanged();
    }

    public List<ModeData> getData() {
        return this.menus;
    }

    public HgModesManagementAdapter(Context context, OnClickListener onClickListener, List<ModeData> list) {
        this.listener = onClickListener;
        this.mContext = context;
        this.menus = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public DeviceMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DeviceMenuViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.hg_modes_management_item_layout, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final DeviceMenuViewHolder deviceMenuViewHolder, final int i) {
        Resources resources;
        int i2;
        final ModeData modeData = this.menus.get(i);
        if (modeData.getModeType() == 1) {
            deviceMenuViewHolder.ivMode.setImageResource(R.drawable.modes_list_comfort_icon);
            deviceMenuViewHolder.tvModeName.setText(this.mContext.getResources().getString(R.string.Mode_of, this.mContext.getResources().getString(R.string.Comfort)));
            deviceMenuViewHolder.ivEdit.setVisibility(8);
            deviceMenuViewHolder.swipeMenuLayout.setSwipeEnable(false);
            if (this.isAllowMove.booleanValue()) {
                deviceMenuViewHolder.ivMove.setVisibility(0);
            } else {
                deviceMenuViewHolder.ivMove.setVisibility(8);
            }
        } else if (modeData.getModeType() == 2) {
            deviceMenuViewHolder.ivMode.setImageResource(R.drawable.modes_list_normal_icon);
            deviceMenuViewHolder.tvModeName.setText(this.mContext.getResources().getString(R.string.Mode_of, this.mContext.getResources().getString(R.string.Standard)));
            deviceMenuViewHolder.ivEdit.setVisibility(8);
            deviceMenuViewHolder.swipeMenuLayout.setSwipeEnable(false);
            deviceMenuViewHolder.swipeMenuLayout.setOnDragListener(new View.OnDragListener() { // from class: com.petkit.android.activities.petkitBleDevice.hg.adapter.HgModesManagementAdapter.1
                @Override // android.view.View.OnDragListener
                public boolean onDrag(View view, DragEvent dragEvent) {
                    return false;
                }

                public AnonymousClass1() {
                }
            });
            if (this.isAllowMove.booleanValue()) {
                deviceMenuViewHolder.ivMove.setVisibility(0);
            } else {
                deviceMenuViewHolder.ivMove.setVisibility(8);
            }
        } else if (modeData.getModeType() == 3) {
            deviceMenuViewHolder.ivMode.setImageResource(R.drawable.modes_list_fast_icon);
            deviceMenuViewHolder.tvModeName.setText(this.mContext.getResources().getString(R.string.Mode_of, this.mContext.getResources().getString(R.string.Fast)));
            deviceMenuViewHolder.ivEdit.setVisibility(8);
            deviceMenuViewHolder.swipeMenuLayout.setSwipeEnable(false);
            if (this.isAllowMove.booleanValue()) {
                deviceMenuViewHolder.ivMove.setVisibility(0);
            } else {
                deviceMenuViewHolder.ivMove.setVisibility(8);
            }
        } else if (modeData.getModeType() == 0) {
            deviceMenuViewHolder.ivMode.setImageResource(R.drawable.modes_list_other_icon);
            deviceMenuViewHolder.tvModeName.setText(this.mContext.getResources().getString(R.string.Mode_of, modeData.getModeName()));
            if (this.isAllowMove.booleanValue()) {
                deviceMenuViewHolder.ivEdit.setVisibility(8);
                deviceMenuViewHolder.ivMove.setVisibility(0);
                deviceMenuViewHolder.swipeMenuLayout.setSwipeEnable(false);
            } else {
                deviceMenuViewHolder.ivEdit.setVisibility(0);
                deviceMenuViewHolder.ivMove.setVisibility(8);
                deviceMenuViewHolder.swipeMenuLayout.setSwipeEnable(true);
            }
        }
        deviceMenuViewHolder.ivMove.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.hg.adapter.HgModesManagementAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$onBindViewHolder$0(deviceMenuViewHolder, view, motionEvent);
            }
        });
        String str = CentigradeToFahrenheit(this.menus.get(i).getTemp() / 100.0f) + " " + this.unitString;
        deviceMenuViewHolder.tvTemp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, String.valueOf(CentigradeToFahrenheit(this.menus.get(i).getTemp() / 100.0f)), this.mContext.getResources().getColor(R.color.light_black), 22, true));
        String str2 = this.menus.get(i).getRev() + " " + this.mContext.getResources().getString(R.string.Unit_gear);
        deviceMenuViewHolder.tvRev.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, String.valueOf(this.menus.get(i).getRev()), this.mContext.getResources().getColor(R.color.light_black), 22, true));
        if (this.menus.get(i).getDryTime() / 60 > 1) {
            resources = this.mContext.getResources();
            i2 = R.string.Unit_minutes_short;
        } else {
            resources = this.mContext.getResources();
            i2 = R.string.Unit_minute_short;
        }
        String str3 = (this.menus.get(i).getDryTime() / 60) + " " + resources.getString(i2);
        deviceMenuViewHolder.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, String.valueOf(this.menus.get(i).getDryTime() / 60), this.mContext.getResources().getColor(R.color.light_black), 22, true));
        deviceMenuViewHolder.rlMode.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.hg.adapter.HgModesManagementAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$1(modeData, i, view);
            }
        });
        deviceMenuViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.hg.adapter.HgModesManagementAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$2(modeData, view);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.hg.adapter.HgModesManagementAdapter$1 */
    public class AnonymousClass1 implements View.OnDragListener {
        @Override // android.view.View.OnDragListener
        public boolean onDrag(View view, DragEvent dragEvent) {
            return false;
        }

        public AnonymousClass1() {
        }
    }

    public final /* synthetic */ boolean lambda$onBindViewHolder$0(DeviceMenuViewHolder deviceMenuViewHolder, View view, MotionEvent motionEvent) {
        Disposable disposable;
        if (motionEvent.getActionMasked() == 0) {
            this.time = System.currentTimeMillis();
            this.lastX = motionEvent.getRawX();
            this.lastY = motionEvent.getRawY();
            this.dLastX = motionEvent.getRawX();
            this.dLastY = motionEvent.getRawY();
            this.disposable = Observable.timer(800L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.hg.adapter.HgModesManagementAdapter.2
                public final /* synthetic */ DeviceMenuViewHolder val$holder;

                public AnonymousClass2(DeviceMenuViewHolder deviceMenuViewHolder2) {
                    deviceMenuViewHolder = deviceMenuViewHolder2;
                }

                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    HgModesManagementAdapter hgModesManagementAdapter = HgModesManagementAdapter.this;
                    float fAbs = Math.abs(hgModesManagementAdapter.dLastX - hgModesManagementAdapter.lastX);
                    HgModesManagementAdapter hgModesManagementAdapter2 = HgModesManagementAdapter.this;
                    float fAbs2 = Math.abs(hgModesManagementAdapter2.dLastY - hgModesManagementAdapter2.lastY);
                    if (fAbs >= 50.0f || fAbs2 >= 50.0f || HgModesManagementAdapter.this.onItemDragListener == null) {
                        return;
                    }
                    ((Vibrator) CommonUtils.getAppContext().getSystemService("vibrator")).vibrate(100L);
                    HgModesManagementAdapter.this.onItemDragListener.onDrag(deviceMenuViewHolder);
                    Disposable disposable2 = HgModesManagementAdapter.this.disposable;
                    if (disposable2 == null || disposable2.isDisposed()) {
                        return;
                    }
                    HgModesManagementAdapter.this.disposable.dispose();
                    HgModesManagementAdapter.this.disposable = null;
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.hg.adapter.HgModesManagementAdapter$2 */
    public class AnonymousClass2 implements Consumer<Long> {
        public final /* synthetic */ DeviceMenuViewHolder val$holder;

        public AnonymousClass2(DeviceMenuViewHolder deviceMenuViewHolder2) {
            deviceMenuViewHolder = deviceMenuViewHolder2;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Long l) throws Exception {
            HgModesManagementAdapter hgModesManagementAdapter = HgModesManagementAdapter.this;
            float fAbs = Math.abs(hgModesManagementAdapter.dLastX - hgModesManagementAdapter.lastX);
            HgModesManagementAdapter hgModesManagementAdapter2 = HgModesManagementAdapter.this;
            float fAbs2 = Math.abs(hgModesManagementAdapter2.dLastY - hgModesManagementAdapter2.lastY);
            if (fAbs >= 50.0f || fAbs2 >= 50.0f || HgModesManagementAdapter.this.onItemDragListener == null) {
                return;
            }
            ((Vibrator) CommonUtils.getAppContext().getSystemService("vibrator")).vibrate(100L);
            HgModesManagementAdapter.this.onItemDragListener.onDrag(deviceMenuViewHolder);
            Disposable disposable2 = HgModesManagementAdapter.this.disposable;
            if (disposable2 == null || disposable2.isDisposed()) {
                return;
            }
            HgModesManagementAdapter.this.disposable.dispose();
            HgModesManagementAdapter.this.disposable = null;
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$1(ModeData modeData, int i, View view) {
        if (this.listener == null || this.isAllowMove.booleanValue()) {
            return;
        }
        this.listener.onViewClick(modeData, i);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$2(ModeData modeData, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClickDelete(modeData);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.menus.size();
    }

    public static class DeviceMenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivEdit;
        public ImageView ivMode;
        public ImageView ivMove;
        public RelativeLayout rlMode;
        public RelativeLayout rlModeName;
        public SwipeMenuLayout swipeMenuLayout;
        public TextView tvDelete;
        public TextView tvModeName;
        public TextView tvRev;
        public TextView tvTemp;
        public TextView tvTime;

        public DeviceMenuViewHolder(@NonNull View view) {
            super(view);
            this.ivMode = (ImageView) view.findViewById(R.id.iv_mode);
            this.ivEdit = (ImageView) view.findViewById(R.id.iv_edit);
            this.ivMove = (ImageView) view.findViewById(R.id.iv_move);
            this.tvModeName = (TextView) view.findViewById(R.id.tv_mode_name);
            this.tvTemp = (TextView) view.findViewById(R.id.tv_temp);
            this.tvRev = (TextView) view.findViewById(R.id.tv_rev);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.rlModeName = (RelativeLayout) view.findViewById(R.id.rl_mode_name);
            this.tvDelete = (TextView) view.findViewById(R.id.tv_del);
            this.swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipe);
            this.rlMode = (RelativeLayout) view.findViewById(R.id.rl_menu_panel);
        }
    }

    private float CentigradeToFahrenheit(float f) {
        if (!this.isCentigrade) {
            return new BigDecimal((f * 1.8f) + 32.0f).setScale(1, 4).floatValue();
        }
        return new BigDecimal(f).setScale(1, 4).floatValue();
    }

    @Override // com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter
    public boolean onItemMove(int i, int i2) {
        if (this.menus.get(i) == null || this.menus.get(i2) == null) {
            return false;
        }
        OnMoveListener onMoveListener = this.onMoveListener;
        if (onMoveListener != null) {
            onMoveListener.onMoveItem(i, i2);
        }
        if (i < i2) {
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                Collections.swap(this.menus, i3, i4);
                i3 = i4;
            }
        } else {
            for (int i5 = i; i5 > i2; i5--) {
                Collections.swap(this.menus, i5, i5 - 1);
            }
        }
        notifyItemMoved(i, i2);
        return true;
    }

    @Override // com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter
    public void onItemDismiss(int i) {
        if (i < this.menus.size()) {
            this.menus.remove(i);
            notifyItemRemoved(i);
        }
    }
}

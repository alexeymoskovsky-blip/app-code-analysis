package com.petkit.android.activities.petkitBleDevice.d4s.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.feed.mode.RefreshFeedDataEvent;
import com.petkit.android.activities.petkitBleDevice.d4.mode.FeedStatistic;
import com.petkit.android.activities.petkitBleDevice.d4s.D4sHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sDateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sDayItem;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.widget.frame.DividerView;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D4sHomeRecordAdapter extends RecyclerView.Adapter<BaseRecordViewHolder> {
    public D4sDateFeedData d4DateFeedData;
    public D4sRecord d4Record;
    public ArrayList<D4sDayItem> dateDataList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
    public long deviceId;
    public boolean isMonth;
    public OnClickListener listener;
    public Context mContext;
    public View view;
    public List<FeedStatistic.ListBean> weekOrMonthDataList;

    public interface OnClickListener {
        void onDelete(int i, int i2, int i3);

        void onDeleteFeedRecord(int i, int i2);

        void onViewClick(ItemsBean itemsBean, int i);
    }

    public ArrayList<D4sDayItem> getDateDataList() {
        return this.dateDataList;
    }

    public void setDateDataList(ArrayList<D4sDayItem> arrayList) {
        this.dateDataList = arrayList;
    }

    public D4sDateFeedData getD4DateFeedData() {
        return this.d4DateFeedData;
    }

    public void setD4DateFeedData(D4sDateFeedData d4sDateFeedData) {
        this.d4DateFeedData = d4sDateFeedData;
        this.weekOrMonthDataList = null;
        this.dateDataList = new ArrayList<>();
        for (D4sDailyFeeds.D4sDailyFeed.ItemsBean itemsBean : d4sDateFeedData.getD4DailyFeeds()) {
            this.dateDataList.add(new D4sDayItem(1, itemsBean.getTime(), itemsBean, true));
        }
        for (D4sDailyFeeds.D4sDailyEat.ItemsBean itemsBean2 : d4sDateFeedData.getD4sDailyEats()) {
            this.dateDataList.add(new D4sDayItem(2, itemsBean2.getEndTime(), itemsBean2, false));
        }
        Collections.sort(this.dateDataList);
    }

    public List<FeedStatistic.ListBean> getWeekOrMonthDataList() {
        return this.weekOrMonthDataList;
    }

    public void setWeekOrMonthDataList(List<FeedStatistic.ListBean> list, boolean z) {
        this.weekOrMonthDataList = list;
        this.isMonth = z;
        this.d4DateFeedData = null;
        this.dateDataList = null;
        notifyDataSetChanged();
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public D4sHomeRecordAdapter(Context context, long j) {
        this.mContext = context;
        this.deviceId = j;
    }

    public D4sHomeRecordAdapter(Context context, long j, OnClickListener onClickListener) {
        this.listener = onClickListener;
        this.mContext = context;
        this.deviceId = j;
        this.d4Record = D4sUtils.getD4sRecordByDeviceId(j);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new DateRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4s_date_record_item, viewGroup, false));
        }
        if (i == 2) {
            return new WeekOrMonthRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4_month_or_week_record_item, viewGroup, false));
        }
        if (i == 3) {
            return new DateRecordWithDeleteViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4s_month_or_week_record_with_delete_item, viewGroup, false));
        }
        return new HistoryRecordEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t3_history_record_empty, viewGroup, false));
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x0845  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.BaseRecordViewHolder r30, @android.annotation.SuppressLint({"RecyclerView"}) final int r31) {
        /*
            Method dump skipped, instruction units count: 7888
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter$BaseRecordViewHolder, int):void");
    }

    public final /* synthetic */ void lambda$onBindViewHolder$0(final BaseRecordViewHolder baseRecordViewHolder, D4sDailyFeeds.D4sDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordViewHolder) baseRecordViewHolder).dateRecordCheckbox.isChecked()) {
            if (this.d4Record.getState().getPim() == 2) {
                if (this.d4Record.getState().getConservationStatus() == 1) {
                    Context context = this.mContext;
                    NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                    newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.2
                        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                        public void onClickIKnow() {
                            ((DateRecordViewHolder) baseRecordViewHolder).dateRecordCheckbox.setChecked(false);
                        }
                    });
                    if (newIKnowWindow.isShowing()) {
                        return;
                    }
                    newIKnowWindow.show(((D4sHomeActivity) this.mContext).getWindow().getDecorView());
                    return;
                }
                restoreDailyFeed(itemsBean);
                return;
            }
            restoreDailyFeed(itemsBean);
            return;
        }
        if (this.d4Record.getState().getPim() == 2) {
            if (this.d4Record.getState().getConservationStatus() == 1) {
                Context context2 = this.mContext;
                NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(context2, (String) null, context2.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.3
                    @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                    public void onClickIKnow() {
                        ((DateRecordViewHolder) baseRecordViewHolder).dateRecordCheckbox.setChecked(true);
                    }
                });
                if (newIKnowWindow2.isShowing()) {
                    return;
                }
                newIKnowWindow2.show(((D4sHomeActivity) this.mContext).getWindow().getDecorView());
                return;
            }
            removeDailyFeed(itemsBean);
            return;
        }
        removeDailyFeed(itemsBean);
    }

    public final void setDividerViewHeight(final BaseRecordViewHolder baseRecordViewHolder, boolean z) {
        if (z) {
            DateRecordViewHolder dateRecordViewHolder = (DateRecordViewHolder) baseRecordViewHolder;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) dateRecordViewHolder.dividerView.getLayoutParams();
            layoutParams.height = ArmsUtils.dip2px(this.mContext, 32.0f);
            dateRecordViewHolder.dividerView.setLayoutParams(layoutParams);
        }
        baseRecordViewHolder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.9
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                baseRecordViewHolder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measuredHeight = baseRecordViewHolder.itemView.getMeasuredHeight();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) ((DateRecordViewHolder) baseRecordViewHolder).dividerView.getLayoutParams();
                layoutParams2.height = Math.max(ArmsUtils.dip2px(D4sHomeRecordAdapter.this.mContext, 32.0f), measuredHeight - ArmsUtils.dip2px(D4sHomeRecordAdapter.this.mContext, 39.0f));
                ((DateRecordViewHolder) baseRecordViewHolder).dividerView.setLayoutParams(layoutParams2);
            }
        });
    }

    public void removeItem(int i) {
        if (this.dateDataList.size() > i) {
            this.dateDataList.remove(i);
            notifyDataSetChanged();
        }
    }

    public final void restoreDailyFeed(final D4sDailyFeeds.D4sDailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d4DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().restoreD4sDailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.10
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(0);
                D4sHomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D4sHomeRecordAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public final void removeDailyFeed(final D4sDailyFeeds.D4sDailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d4DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().removeD4sDailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.11
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(1);
                D4sHomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D4sHomeRecordAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public String getWeekStatisticTime(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(str.substring(4, 6).startsWith("0") ? 5 : 4, 6));
        sb.append(".");
        sb.append(str.substring(str.substring(6, 8).startsWith("0") ? 7 : 6, 8));
        return sb.toString();
    }

    public String getMonthStatisticTime(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(4, 6).startsWith("0") ? str.substring(5, 6) : str.substring(4, 6));
        sb.append(".");
        sb.append(str.substring(6, 8).startsWith("0") ? str.substring(7, 8) : str.substring(6, 8));
        sb.append("~");
        sb.append(str2.substring(4, 6).startsWith("0") ? str2.substring(5, 6) : str2.substring(4, 6));
        sb.append(".");
        sb.append(str2.substring(6, 8).startsWith("0") ? str2.substring(7, 8) : str2.substring(6, 8));
        return sb.toString();
    }

    public final String getd4ItemDataNameWithState(D4sDailyFeeds.D4sDailyFeed.ItemsBean itemsBean) {
        int src = itemsBean.getSrc();
        if (src == 2 || src == 3) {
            return this.mContext.getString(R.string.Feeder_add_manual_online);
        }
        if (src == 4) {
            return this.mContext.getString(R.string.Feeder_add_manual_offline);
        }
        return itemsBean.getName();
    }

    public final int getD4ItemDataIconByState(D4sDailyFeeds.D4sDailyFeed.ItemsBean itemsBean) {
        if (D4sUtils.checkD4ItemIsTimeout(this.d4Record.getSettings().getFactor1(), this.d4Record.getSettings().getFactor2(), this.d4DateFeedData.getDay(), itemsBean, this.d4Record.getActualTimeZone())) {
            if (itemsBean.getState() == null) {
                return R.drawable.d4s_state_failed;
            }
            if (itemsBean.getStatus() == 1 || itemsBean.getStatus() == 2) {
                return R.drawable.d4s_state_failed;
            }
            if (itemsBean.getState().getResult() == 0) {
                if (itemsBean.getState().getErrCode() == 8) {
                    return R.drawable.d4s_state_failed;
                }
                return R.drawable.d4s_state_complete;
            }
            return R.drawable.d4s_state_failed;
        }
        if (itemsBean.getStatus() == 3) {
            return R.drawable.d4s_state_eating;
        }
        if (itemsBean.getStatus() == 0) {
            return R.drawable.d4_state_wait;
        }
        return R.drawable.d4s_state_canceled;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ArrayList<D4sDayItem> arrayList = this.dateDataList;
        if (arrayList != null && arrayList.size() > 0) {
            if (this.dateDataList.get(i).getType() != 1 || this.d4Record.getDeviceShared() != null) {
                return (this.dateDataList.get(i).getType() == 2 && this.d4Record.getDeviceShared() == null) ? 3 : 1;
            }
            D4sDailyFeeds.D4sDailyFeed.ItemsBean itemsBean = (D4sDailyFeeds.D4sDailyFeed.ItemsBean) this.dateDataList.get(i).getBean();
            return (itemsBean.getStatus() == 0 && itemsBean.getState() != null && itemsBean.getState().getResult() == 0 && itemsBean.getState().getRealAmount1() + itemsBean.getState().getRealAmount2() > 0 && itemsBean.getState().getErrCode() == 0) ? 3 : 1;
        }
        List<FeedStatistic.ListBean> list = this.weekOrMonthDataList;
        return (list == null || list.size() <= 0) ? -1 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<D4sDayItem> arrayList = this.dateDataList;
        if (arrayList != null && arrayList.size() > 0) {
            return this.dateDataList.size();
        }
        List<FeedStatistic.ListBean> list = this.weekOrMonthDataList;
        if (list == null || list.size() <= 0) {
            return 1;
        }
        return this.weekOrMonthDataList.size();
    }

    public static class WeekOrMonthRecordViewHolder extends BaseRecordViewHolder {
        public ImageView ivWeekOrMonthRecordState;
        public LinearLayout llWeekOrMonthRecordPoint;
        public LinearLayout llWeekOrMonthRecordPointDefault;
        public TextView tvWeekOrMonthRecordName;
        public TextView tvWeekOrMonthRecordTime;

        public WeekOrMonthRecordViewHolder(View view) {
            super(view);
            this.tvWeekOrMonthRecordTime = (TextView) view.findViewById(R.id.tv_week_or_month_record_time);
            this.ivWeekOrMonthRecordState = (ImageView) view.findViewById(R.id.iv_week_or_month_record_state);
            this.llWeekOrMonthRecordPointDefault = (LinearLayout) view.findViewById(R.id.ll_week_or_month_record_point_default);
            this.llWeekOrMonthRecordPoint = (LinearLayout) view.findViewById(R.id.ll_week_or_month_record_point);
            this.tvWeekOrMonthRecordName = (TextView) view.findViewById(R.id.tv_week_or_month_record_name);
        }
    }

    public static class DateRecordWithDeleteViewHolder extends BaseRecordViewHolder {
        public CheckBox dateRecordCheckbox;
        public DividerView dividerView;
        public ImageView ivDateRecordArrow;
        public ImageView ivRecordState;
        public LinearLayout llDateRecordPoint;
        public LinearLayout llDateRecordPointAdditional;
        public LinearLayout llDateRecordPointDefault;
        public LinearLayout llDateRecordPointMost;
        public RelativeLayout rlDateRecordPanel;
        public RelativeLayout rlRootPanel;
        public TextView tvDateRecordAmount;
        public TextView tvDateRecordPrompt;
        public TextView tvDateRecordSave;
        public TextView tvDateRecordState;
        public TextView tvDateRecordTime;
        public TextView tvDel;

        public DateRecordWithDeleteViewHolder(View view) {
            super(view);
            this.rlRootPanel = (RelativeLayout) view.findViewById(R.id.rl_root_record_panel);
            this.tvDateRecordTime = (TextView) view.findViewById(R.id.tv_date_record_time);
            this.ivRecordState = (ImageView) view.findViewById(R.id.iv_record_state);
            this.llDateRecordPointMost = (LinearLayout) view.findViewById(R.id.ll_date_record_point_most);
            this.llDateRecordPointAdditional = (LinearLayout) view.findViewById(R.id.ll_date_record_point_additional);
            this.llDateRecordPointDefault = (LinearLayout) view.findViewById(R.id.ll_date_record_point_default);
            this.llDateRecordPoint = (LinearLayout) view.findViewById(R.id.ll_date_record_point);
            this.tvDateRecordAmount = (TextView) view.findViewById(R.id.tv_date_record_amount);
            this.tvDateRecordPrompt = (TextView) view.findViewById(R.id.tv_date_record_prompt);
            this.ivDateRecordArrow = (ImageView) view.findViewById(R.id.iv_date_record_arrow);
            this.tvDateRecordState = (TextView) view.findViewById(R.id.tv_date_record_state);
            this.dateRecordCheckbox = (CheckBox) view.findViewById(R.id.date_record_checkbox);
            this.rlDateRecordPanel = (RelativeLayout) view.findViewById(R.id.rl_date_record_panel);
            this.tvDateRecordSave = (TextView) view.findViewById(R.id.tv_date_record_save);
            this.dividerView = (DividerView) view.findViewById(R.id.divider_view);
            this.tvDel = (TextView) view.findViewById(R.id.tv_del);
        }
    }

    public static class DateRecordViewHolder extends BaseRecordViewHolder {
        public CheckBox dateRecordCheckbox;
        public DividerView dividerView;
        public ImageView ivDateRecordArrow;
        public ImageView ivRecordState;
        public LinearLayout llDateRecordPoint;
        public LinearLayout llDateRecordPointAdditional;
        public LinearLayout llDateRecordPointDefault;
        public LinearLayout llDateRecordPointMost;
        public RelativeLayout rlDateRecordPanel;
        public RelativeLayout rlRootPanel;
        public TextView tvDateRecordAmount;
        public TextView tvDateRecordPrompt;
        public TextView tvDateRecordSave;
        public TextView tvDateRecordState;
        public TextView tvDateRecordTime;

        public DateRecordViewHolder(View view) {
            super(view);
            this.rlRootPanel = (RelativeLayout) view.findViewById(R.id.rl_root_record_panel);
            this.tvDateRecordTime = (TextView) view.findViewById(R.id.tv_date_record_time);
            this.ivRecordState = (ImageView) view.findViewById(R.id.iv_record_state);
            this.llDateRecordPointMost = (LinearLayout) view.findViewById(R.id.ll_date_record_point_most);
            this.llDateRecordPointAdditional = (LinearLayout) view.findViewById(R.id.ll_date_record_point_additional);
            this.llDateRecordPointDefault = (LinearLayout) view.findViewById(R.id.ll_date_record_point_default);
            this.llDateRecordPoint = (LinearLayout) view.findViewById(R.id.ll_date_record_point);
            this.tvDateRecordAmount = (TextView) view.findViewById(R.id.tv_date_record_amount);
            this.tvDateRecordPrompt = (TextView) view.findViewById(R.id.tv_date_record_prompt);
            this.ivDateRecordArrow = (ImageView) view.findViewById(R.id.iv_date_record_arrow);
            this.tvDateRecordState = (TextView) view.findViewById(R.id.tv_date_record_state);
            this.dateRecordCheckbox = (CheckBox) view.findViewById(R.id.date_record_checkbox);
            this.rlDateRecordPanel = (RelativeLayout) view.findViewById(R.id.rl_date_record_panel);
            this.tvDateRecordSave = (TextView) view.findViewById(R.id.tv_date_record_save);
            this.dividerView = (DividerView) view.findViewById(R.id.divider_view);
        }
    }

    public static class HistoryRecordEmptyViewHolder extends BaseRecordViewHolder {
        public HistoryRecordEmptyViewHolder(View view) {
            super(view);
        }
    }

    public static class BaseRecordViewHolder extends RecyclerView.ViewHolder {
        public BaseRecordViewHolder(View view) {
            super(view);
        }
    }
}

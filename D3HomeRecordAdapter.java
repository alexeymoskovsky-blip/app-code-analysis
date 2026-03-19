package com.petkit.android.activities.petkitBleDevice.d3.adapter;

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
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.petkit.android.activities.feed.mode.RefreshFeedDataEvent;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3DailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3DateFeedData;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3DayItem;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.mode.FeedAndEatStatistic;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.widget.frame.DividerView;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D3HomeRecordAdapter extends RecyclerView.Adapter<BaseRecordViewHolder> {
    public D3DateFeedData d3DateFeedData;
    public D3Record d3Record;
    public ArrayList<D3DayItem> dateDataList;
    public SimpleDateFormat dateFormat;
    public DeleteRecordViewClick deleteRecordViewClick;
    public long deviceId;
    public Integer guidePageIndex;
    public GuidePageListener guidePageListener;
    public boolean isMonth;
    public Map<Integer, Integer> itemHeightMap;
    public OnClickListener listener;
    public Context mContext;
    public BaseRecordViewHolder needSwipeLayoutHolder;
    public boolean shared;
    public List<FeedAndEatStatistic.ListBean> weekOrMonthDataList;

    public interface DeleteRecordViewClick {
        void onDeleteFeedRecordClick(int i, int i2);

        void onViewClick(int i, int i2, int i3, int i4);
    }

    public interface GuidePageListener {
        void onMeasureComplete(Map<Integer, Integer> map, Integer num);
    }

    public interface OnClickListener {
        void onViewClick(ItemsBean itemsBean, int i);
    }

    public ArrayList<D3DayItem> getDateDataList() {
        return this.dateDataList;
    }

    public void setDateDataList(ArrayList<D3DayItem> arrayList) {
        this.dateDataList = arrayList;
    }

    public D3DateFeedData getD3DateFeedData() {
        return this.d3DateFeedData;
    }

    public void setD3DateFeedData(D3DateFeedData d3DateFeedData) {
        this.d3DateFeedData = d3DateFeedData;
        this.weekOrMonthDataList = null;
        this.dateDataList = new ArrayList<>();
        for (D3DailyFeeds.D3DailyFeed.ItemsBean itemsBean : d3DateFeedData.getD3DailyFeeds()) {
            this.dateDataList.add(new D3DayItem(1, itemsBean.getTime(), itemsBean, true));
        }
        for (D3DailyFeeds.D3DailyEat.ItemsBean itemsBean2 : d3DateFeedData.getD3DailyEats()) {
            this.dateDataList.add(new D3DayItem(2, itemsBean2.getEndTime(), itemsBean2, false));
        }
        Collections.sort(this.dateDataList);
        this.guidePageIndex = null;
        this.itemHeightMap.clear();
    }

    public List<FeedAndEatStatistic.ListBean> getWeekOrMonthDataList() {
        return this.weekOrMonthDataList;
    }

    public void setWeekOrMonthDataList(List<FeedAndEatStatistic.ListBean> list, boolean z) {
        this.weekOrMonthDataList = list;
        this.isMonth = z;
        this.d3DateFeedData = null;
        this.dateDataList = null;
        notifyDataSetChanged();
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public D3HomeRecordAdapter(Context context, long j) {
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
        this.itemHeightMap = new HashMap();
        this.shared = false;
        this.needSwipeLayoutHolder = null;
        this.mContext = context;
        this.deviceId = j;
    }

    public D3HomeRecordAdapter(Context context, long j, OnClickListener onClickListener, DeleteRecordViewClick deleteRecordViewClick, GuidePageListener guidePageListener, boolean z) {
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
        this.itemHeightMap = new HashMap();
        this.shared = false;
        this.needSwipeLayoutHolder = null;
        this.listener = onClickListener;
        this.deleteRecordViewClick = deleteRecordViewClick;
        this.guidePageListener = guidePageListener;
        this.mContext = context;
        this.deviceId = j;
        this.d3Record = D3Utils.getD3RecordByDeviceId(j);
        this.shared = z;
    }

    public void setSwipeLayoutExpand(int i) {
        BaseRecordViewHolder baseRecordViewHolder = this.needSwipeLayoutHolder;
        if (baseRecordViewHolder != null) {
            ((DateRecordViewHolder) baseRecordViewHolder).swipeMenuLayout.smoothExpand();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new DateRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d3_date_record_item, viewGroup, false));
        }
        if (i == 2) {
            return new WeekOrMonthRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d3_month_or_week_record_item, viewGroup, false));
        }
        return new HistoryRecordEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t3_history_record_empty, viewGroup, false));
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02e8  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.BaseRecordViewHolder r18, final int r19) {
        /*
            Method dump skipped, instruction units count: 2492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter$BaseRecordViewHolder, int):void");
    }

    public final /* synthetic */ void lambda$onBindViewHolder$0(BaseRecordViewHolder baseRecordViewHolder, D3DailyFeeds.D3DailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordViewHolder) baseRecordViewHolder).dateRecordCheckbox.isChecked()) {
            restoreDailyFeed(itemsBean);
        } else {
            removeDailyFeed(itemsBean);
        }
    }

    public final void setDividerViewHeight(final BaseRecordViewHolder baseRecordViewHolder, final int i, boolean z) {
        if (!this.itemHeightMap.containsKey(Integer.valueOf(i)) || z) {
            if (z) {
                DateRecordViewHolder dateRecordViewHolder = (DateRecordViewHolder) baseRecordViewHolder;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) dateRecordViewHolder.dividerView.getLayoutParams();
                layoutParams.height = ArmsUtils.dip2px(this.mContext, 32.0f);
                dateRecordViewHolder.dividerView.setLayoutParams(layoutParams);
            }
            baseRecordViewHolder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.4
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    baseRecordViewHolder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int measuredHeight = baseRecordViewHolder.itemView.getMeasuredHeight();
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) ((DateRecordViewHolder) baseRecordViewHolder).dividerView.getLayoutParams();
                    layoutParams2.height = Math.max(ArmsUtils.dip2px(D3HomeRecordAdapter.this.mContext, 32.0f), measuredHeight - ArmsUtils.dip2px(D3HomeRecordAdapter.this.mContext, 39.0f));
                    ((DateRecordViewHolder) baseRecordViewHolder).dividerView.setLayoutParams(layoutParams2);
                    baseRecordViewHolder.itemView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            D3HomeRecordAdapter.this.itemHeightMap.put(Integer.valueOf(i), Integer.valueOf(((DateRecordViewHolder) baseRecordViewHolder).itemView.getMeasuredHeight()));
                            if (D3HomeRecordAdapter.this.itemHeightMap.size() != D3HomeRecordAdapter.this.dateDataList.size() || D3HomeRecordAdapter.this.guidePageListener == null) {
                                return;
                            }
                            D3HomeRecordAdapter.this.guidePageListener.onMeasureComplete(D3HomeRecordAdapter.this.itemHeightMap, D3HomeRecordAdapter.this.guidePageIndex);
                        }
                    });
                }
            });
        }
    }

    public final void restoreDailyFeed(final D3DailyFeeds.D3DailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d3DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().restoreDailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.5
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(0);
                D3HomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D3HomeRecordAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public final void removeDailyFeed(final D3DailyFeeds.D3DailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d3DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().removeDailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.6
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(1);
                D3HomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D3HomeRecordAdapter.this.notifyDataSetChanged();
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

    public final String getD3ItemDataNameWithState(D3DailyFeeds.D3DailyFeed.ItemsBean itemsBean) {
        int src = itemsBean.getSrc();
        if (src == 2 || src == 3) {
            return this.mContext.getString(R.string.Feeder_add_manual_online);
        }
        if (src == 4) {
            return this.mContext.getString(R.string.Feeder_add_manual_offline);
        }
        return itemsBean.getName();
    }

    public final int getD3ItemDataIconByState(D3DailyFeeds.D3DailyFeed.ItemsBean itemsBean) {
        if (D3Utils.checkD3ItemIsTimeout(this.d3DateFeedData.getDay(), itemsBean, this.d3Record.getActualTimeZone())) {
            if (itemsBean.getStatus() == 1 || itemsBean.getStatus() == 2) {
                return R.drawable.d3_state_failed;
            }
            if (itemsBean.getStatus() == 0 && itemsBean.getState() != null && itemsBean.getState().getResult() == 6) {
                return R.drawable.d3_state_complete;
            }
            if (itemsBean.getSrc() == 4) {
                if (itemsBean.getState().getErrCode() == 0) {
                    return R.drawable.d3_state_complete;
                }
                if (checkErrCode(itemsBean)) {
                    return R.drawable.d3_state_failed;
                }
                return R.drawable.d3_state_complete;
            }
            if (itemsBean.getState() != null && itemsBean.getState().getErrCode() == 0 && !CommonUtils.isEmpty(itemsBean.getState().getCompletedAt())) {
                return R.drawable.d3_state_complete;
            }
            if (itemsBean.getState() != null && checkErrCode(itemsBean)) {
                return R.drawable.d3_state_failed;
            }
            return R.drawable.d3_state_failed;
        }
        if (itemsBean.getStatus() == 3) {
            return R.drawable.d3_state_eating;
        }
        if (itemsBean.getStatus() == 0) {
            return R.drawable.d3_state_wait;
        }
        return R.drawable.d3_state_canceled;
    }

    public final boolean checkErrCode(D3DailyFeeds.D3DailyFeed.ItemsBean itemsBean) {
        if (itemsBean.getState() == null) {
            return false;
        }
        if (itemsBean.getState().getErrCode() == 2 || itemsBean.getState().getErrCode() == 3 || itemsBean.getState().getErrCode() == 4 || itemsBean.getState().getErrCode() == 5 || itemsBean.getState().getErrCode() == 10 || itemsBean.getState().getErrCode() == 11 || itemsBean.getState().getErrCode() == 14 || itemsBean.getState().getErrCode() == 15 || itemsBean.getState().getErrCode() == 16 || itemsBean.getState().getErrCode() == 17 || itemsBean.getState().getErrCode() == 19 || itemsBean.getState().getErrCode() == 20) {
            return true;
        }
        return itemsBean.getState().getErrCode() == 18 && itemsBean.getState().getResult() == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ArrayList<D3DayItem> arrayList = this.dateDataList;
        if (arrayList != null && arrayList.size() > 0) {
            return 1;
        }
        List<FeedAndEatStatistic.ListBean> list = this.weekOrMonthDataList;
        return (list == null || list.size() <= 0) ? -1 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<D3DayItem> arrayList = this.dateDataList;
        if (arrayList != null && arrayList.size() > 0) {
            return this.dateDataList.size();
        }
        List<FeedAndEatStatistic.ListBean> list = this.weekOrMonthDataList;
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
        public SwipeMenuLayout swipeMenuLayout;
        public TextView tvDateRecordAmount;
        public TextView tvDateRecordSave;
        public TextView tvDateRecordState;
        public TextView tvDateRecordTime;
        public TextView tvDel;

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
            this.ivDateRecordArrow = (ImageView) view.findViewById(R.id.iv_date_record_arrow);
            this.tvDateRecordState = (TextView) view.findViewById(R.id.tv_date_record_state);
            this.dateRecordCheckbox = (CheckBox) view.findViewById(R.id.date_record_checkbox);
            this.rlDateRecordPanel = (RelativeLayout) view.findViewById(R.id.rl_date_record_panel);
            this.tvDateRecordSave = (TextView) view.findViewById(R.id.tv_date_record_save);
            this.swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipe_menu_layout);
            this.tvDel = (TextView) view.findViewById(R.id.tv_del);
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

    public void setDeleteRecordViewClick(DeleteRecordViewClick deleteRecordViewClick) {
        this.deleteRecordViewClick = deleteRecordViewClick;
    }
}

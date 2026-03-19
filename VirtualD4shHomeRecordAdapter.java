package com.petkit.android.activities.virtual.d4sh.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.petkit.android.activities.petkitBleDevice.d4.mode.FeedStatistic;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4shDateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDayItem;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVideoRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shDeviceRecordView;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes6.dex */
public class VirtualD4shHomeRecordAdapter extends D4shAbstractHomeRecordAdapter {
    public int bindDataTimes;
    public D4shDateFeedData d4DateFeedData;
    public D4shRecord d4shRecord;
    public ArrayList<D4shDayItem> dateDataList;
    public long deviceId;
    public int filterRecordType;
    public boolean isMonth;
    public RecordOnClickListener listener;
    public Context mContext;
    public boolean serviceInvalid;
    public long startBindDataTime;
    public View view;
    public List<FeedStatistic.ListBean> weekOrMonthDataList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
    public int d4shDeviceRecordTopVideoInScreenIndex = -1;

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public List<D4shVideoRecord> getRecordList() {
        return null;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public boolean isShowingCatPop() {
        return false;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void setHideCatPop(boolean z) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void updateOnePet(String str, String str2, String str3, int i) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public ArrayList<D4shDayItem> getDateDataList() {
        return this.dateDataList;
    }

    public void setDateDataList(ArrayList<D4shDayItem> arrayList) {
        this.dateDataList = arrayList;
    }

    public D4shDateFeedData getD4DateFeedData() {
        return this.d4DateFeedData;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void setD4DateFeedData(D4shDateFeedData d4shDateFeedData) {
        int i;
        int i2;
        int i3;
        int i4;
        this.d4DateFeedData = d4shDateFeedData;
        this.weekOrMonthDataList = null;
        this.dateDataList = new ArrayList<>();
        if (d4shDateFeedData != null && ((i4 = this.filterRecordType) == 0 || i4 == 4)) {
            for (D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean : d4shDateFeedData.getD4sDailyFeeds()) {
                this.dateDataList.add(new D4shDayItem(1, itemsBean.getTime(), itemsBean, true, this.d4shRecord.getActualTimeZone()));
            }
        }
        if (d4shDateFeedData != null && ((i3 = this.filterRecordType) == 0 || i3 == 2)) {
            for (D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean2 : d4shDateFeedData.getD4sDailyEats()) {
                this.dateDataList.add(new D4shDayItem(2, itemsBean2.getStartTime(), itemsBean2, false, this.d4shRecord.getActualTimeZone()));
            }
        }
        if (d4shDateFeedData != null && ((i2 = this.filterRecordType) == 0 || i2 == 1)) {
            for (D4shDailyFeeds.PetBean.ItemsBean itemsBean3 : d4shDateFeedData.getD4shDeviceRecordPetTypeDataList()) {
                this.dateDataList.add(new D4shDayItem(3, (int) itemsBean3.getTimestamp(), itemsBean3, false, this.d4shRecord.getActualTimeZone()));
            }
        }
        if (d4shDateFeedData != null && ((i = this.filterRecordType) == 0 || i == 3)) {
            for (D4shDailyFeeds.MoveBean.ItemsBeanX itemsBeanX : d4shDateFeedData.getD4shDeviceRecordMoveTypeDataList()) {
                this.dateDataList.add(new D4shDayItem(4, (int) itemsBeanX.getTimestamp(), itemsBeanX, false, this.d4shRecord.getActualTimeZone()));
            }
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

    public RecordOnClickListener getListener() {
        return this.listener;
    }

    public void setListener(RecordOnClickListener recordOnClickListener) {
        this.listener = recordOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public int getFilterRecordType() {
        return this.filterRecordType;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void setFilterRecordType(int i) {
        this.filterRecordType = i;
        setD4DateFeedData(this.d4DateFeedData);
        notifyDataSetChanged();
    }

    public VirtualD4shHomeRecordAdapter(Context context, long j) {
        this.mContext = context;
        this.deviceId = j;
    }

    public VirtualD4shHomeRecordAdapter(Context context, long j, int i, int i2, RecordOnClickListener recordOnClickListener) {
        EventBus.getDefault().register(this);
        this.listener = recordOnClickListener;
        this.filterRecordType = i2;
        this.mContext = context;
        this.deviceId = j;
        D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(j, i);
        this.d4shRecord = d4shRecordByDeviceId;
        if (d4shRecordByDeviceId != null) {
            this.serviceInvalid = d4shRecordByDeviceId.getCloudProduct() == null;
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new DateRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4sh_date_record_item, viewGroup, false));
        }
        if (i == 2) {
            return new WeekOrMonthRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4_month_or_week_record_item, viewGroup, false));
        }
        if (i == 3) {
            return new DateRecordWithDeleteViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4sh_month_or_week_record_with_delete_item, viewGroup, false));
        }
        if (i == 5) {
            return new RecordEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.d4sh_device_record_adapter_pet_empty_view, viewGroup, false));
        }
        if (i == 6) {
            return new EatCompareRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4sh_eat_compare_record_item, viewGroup, false));
        }
        return new HistoryRecordEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.d4sh_device_record_adapter_empty_view, viewGroup, false));
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0aa0  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x1225  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x1363  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x1369  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x1376  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x1412  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x1a73  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x1bb1  */
    /* JADX WARN: Removed duplicated region for block: B:438:0x1bb7  */
    /* JADX WARN: Removed duplicated region for block: B:439:0x1bc4  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x1c60  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x1ce5  */
    /* JADX WARN: Removed duplicated region for block: B:567:0x2653  */
    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder r67, @android.annotation.SuppressLint({"RecyclerView"}) final int r68) {
        /*
            Method dump skipped, instruction units count: 13236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4shHomeRecordAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(final BaseRecordViewHolder baseRecordViewHolder, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordViewHolder) baseRecordViewHolder).d4shDeviceRecordView.isDeviceRecordPlanCheckBoxCheck()) {
            if (this.d4shRecord.getState().getPim() == 2) {
                Context context = this.mContext;
                NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Battery_mode_is_being_used), (String) null);
                newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4shHomeRecordAdapter.4
                    @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                    public void onClickIKnow() {
                        ((DateRecordViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.FALSE);
                    }
                });
                if (newIKnowWindow.isShowing()) {
                    return;
                }
                newIKnowWindow.show(((Activity) this.mContext).getWindow().getDecorView());
                return;
            }
            restoreDailyFeed(itemsBean);
            return;
        }
        if (this.d4shRecord.getState().getPim() == 2) {
            Context context2 = this.mContext;
            NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(context2, (String) null, context2.getResources().getString(R.string.Battery_mode_is_being_used), (String) null);
            newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4shHomeRecordAdapter.5
                @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                public void onClickIKnow() {
                    ((DateRecordViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.TRUE);
                }
            });
            if (newIKnowWindow2.isShowing()) {
                return;
            }
            newIKnowWindow2.show(((Activity) this.mContext).getWindow().getDecorView());
            return;
        }
        removeDailyFeed(itemsBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(D4shVideoRecord d4shVideoRecord, View view) {
        RecordOnClickListener recordOnClickListener = this.listener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onItemClick(d4shVideoRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$2(final BaseRecordViewHolder baseRecordViewHolder, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.isDeviceRecordPlanCheckBoxCheck()) {
            if (this.d4shRecord.getState().getPim() == 2) {
                if (this.d4shRecord.getState().getConservationStatus() == 1) {
                    Context context = this.mContext;
                    NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                    newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4shHomeRecordAdapter.10
                        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                        public void onClickIKnow() {
                            ((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.FALSE);
                        }
                    });
                    if (newIKnowWindow.isShowing()) {
                        return;
                    }
                    newIKnowWindow.show(((Activity) this.mContext).getWindow().getDecorView());
                    return;
                }
                restoreDailyFeed(itemsBean);
                return;
            }
            restoreDailyFeed(itemsBean);
            return;
        }
        if (this.d4shRecord.getState().getPim() == 2) {
            if (this.d4shRecord.getState().getConservationStatus() == 1) {
                Context context2 = this.mContext;
                NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(context2, (String) null, context2.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4shHomeRecordAdapter.11
                    @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                    public void onClickIKnow() {
                        ((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.TRUE);
                    }
                });
                if (newIKnowWindow2.isShowing()) {
                    return;
                }
                newIKnowWindow2.show(((Activity) this.mContext).getWindow().getDecorView());
                return;
            }
            removeDailyFeed(itemsBean);
            return;
        }
        removeDailyFeed(itemsBean);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$3(D4shVideoRecord d4shVideoRecord, View view) {
        RecordOnClickListener recordOnClickListener = this.listener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onItemClick(d4shVideoRecord);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void removeItem(int i) {
        if (this.dateDataList.size() > i) {
            this.dateDataList.remove(i);
            notifyDataSetChanged();
        }
    }

    private void restoreDailyFeed(D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean) {
        itemsBean.setStatus(0);
        notifyDataSetChanged();
    }

    private void removeDailyFeed(D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean) {
        itemsBean.setStatus(1);
        notifyDataSetChanged();
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

    private String getd4ItemDataNameWithState(D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean) {
        int src = itemsBean.getSrc();
        if (src == 2 || src == 3) {
            return this.mContext.getString(R.string.Feeder_add_manual_online);
        }
        if (src == 4) {
            return this.mContext.getString(R.string.Feeder_add_manual_offline);
        }
        return itemsBean.getName();
    }

    private int getD4ItemDataIconByState(D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean) {
        if (D4shUtils.checkD4ItemIsTimeout(this.d4DateFeedData.getDay(), itemsBean, this.d4shRecord.getActualTimeZone())) {
            if (itemsBean.getState() == null) {
                return R.drawable.d4sh_state_failed;
            }
            if (itemsBean.getStatus() == 1 || itemsBean.getStatus() == 2) {
                return R.drawable.d4sh_state_failed;
            }
            if (itemsBean.getState().getResult() == 0) {
                if (itemsBean.getState().getErrCode() == 8) {
                    return R.drawable.d4sh_state_failed;
                }
                if (itemsBean.getState().getRealAmount1() + itemsBean.getState().getRealAmount2() > 0) {
                    return R.drawable.d4sh_state_complete;
                }
                return R.drawable.d4sh_state_failed;
            }
            return R.drawable.d4sh_state_failed;
        }
        if (itemsBean.getStatus() == 3) {
            return R.drawable.d4sh_state_eating;
        }
        return R.drawable.d4sh_state_wait;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ArrayList<D4shDayItem> arrayList = this.dateDataList;
        if (arrayList != null && arrayList.size() > 0) {
            if (this.dateDataList.get(i).getType() == 1 && this.d4shRecord.getDeviceShared() == null) {
                D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyFeed.ItemsBean) this.dateDataList.get(i).getBean();
                return ((itemsBean.getStatus() == 0 && itemsBean.getState() == null && !D4shUtils.checkD4ItemIsTimeout(this.d4DateFeedData.getDay(), itemsBean, this.d4shRecord.getActualTimeZone())) || itemsBean.getStatus() == 1) ? 1 : 3;
            }
            if (this.dateDataList.get(i).getType() == 2 && ((D4shDailyFeeds.D4shDailyEat.ItemsBean) this.dateDataList.get(i).getBean()).getEndTime() >= 0 && this.d4shRecord.getDeviceShared() == null) {
                return 3;
            }
            if (this.dateDataList.get(i).getType() == 3 && this.d4shRecord.getDeviceShared() == null) {
                return 3;
            }
            if (this.dateDataList.get(i).getType() == 4 && this.d4shRecord.getDeviceShared() == null) {
                return 3;
            }
            return this.dateDataList.get(i).getType() == 5 ? 6 : 1;
        }
        ArrayList<D4shDayItem> arrayList2 = this.dateDataList;
        if (arrayList2 != null && arrayList2.size() == 0 && ((this.filterRecordType == 2 && this.d4shRecord.getSettings().getEatDetection() == 0) || (this.filterRecordType == 1 && this.d4shRecord.getSettings().getPetDetection() == 0))) {
            return 5;
        }
        List<FeedStatistic.ListBean> list = this.weekOrMonthDataList;
        return (list == null || list.size() <= 0) ? -1 : 2;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<D4shDayItem> arrayList = this.dateDataList;
        if (arrayList != null && arrayList.size() > 0) {
            return this.dateDataList.size();
        }
        List<FeedStatistic.ListBean> list = this.weekOrMonthDataList;
        if (list == null || list.size() <= 0) {
            return 1;
        }
        return this.weekOrMonthDataList.size();
    }

    private SpannableString getDeviceRecordDescSpannableString(int i, int i2, int i3, int i4) {
        String allAmountFormat = D4sUtils.getAllAmountFormat(i, i3, i2, i4);
        String string = this.mContext.getResources().getString(R.string.Defalut_bucket1_name);
        String string2 = this.mContext.getResources().getString(R.string.Defalut_bucket2_name);
        String str = String.format("%s [%s x%d, %s x%d]", this.mContext.getResources().getString(R.string.D3_record_amount_prompt, allAmountFormat + D4shUtils.getD4shAmountUnit(this.mContext, Integer.parseInt(allAmountFormat))), string, Integer.valueOf(i), string2, Integer.valueOf(i2));
        if (i == 0) {
            str = String.format("%s [%s x%d]", this.mContext.getResources().getString(R.string.D3_record_amount_prompt, allAmountFormat + D4shUtils.getD4shAmountUnit(this.mContext, Integer.parseInt(allAmountFormat))), string2, Integer.valueOf(i2));
        } else if (i2 == 0) {
            str = String.format("%s [%s x%d]", this.mContext.getResources().getString(R.string.D3_record_amount_prompt, allAmountFormat + D4shUtils.getD4shAmountUnit(this.mContext, Integer.parseInt(allAmountFormat))), string, Integer.valueOf(i));
        }
        SpannableString spannableString = new SpannableString(str);
        int iIndexOf = str.indexOf(string);
        if (iIndexOf > 0) {
            spannableString.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.d4sh_bucket_1_name_green)), iIndexOf, string.length() + iIndexOf, 33);
            spannableString.setSpan(new StyleSpan(1), iIndexOf, string.length() + iIndexOf, 33);
        }
        int iIndexOf2 = str.indexOf(string2);
        if (iIndexOf2 > 0) {
            spannableString.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.d4sh_text_main_orange)), iIndexOf2, string2.length() + iIndexOf2, 33);
            spannableString.setSpan(new StyleSpan(1), iIndexOf2, string2.length() + iIndexOf2, 33);
        }
        return spannableString;
    }

    public void setMediaInfo(int i, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, D4shDeviceRecordView d4shDeviceRecordView) {
        PetkitLog.d("setMediaInfo:" + itemsBean.getTime());
        String mediaApi = itemsBean.getMediaApi();
        if (this.d4shRecord.getServiceStatus() == 1) {
            if (TextUtils.isEmpty(mediaApi)) {
                if (itemsBean.getState() == null || itemsBean.getState().getMedia() == null) {
                    if (itemsBean.getEatVideo() == 0) {
                        d4shDeviceRecordView.showEatVideoOffTv(Boolean.TRUE);
                        d4shDeviceRecordView.hidePreview();
                    } else if (TextUtils.isEmpty(itemsBean.getPreview())) {
                        d4shDeviceRecordView.hidePreview();
                        if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                            d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                            d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
                        }
                    } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                        d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                        d4shDeviceRecordView.hidePreview();
                        d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
                    } else {
                        d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
                    }
                } else if (itemsBean.getState() != null && itemsBean.getState().getMedia() != null && itemsBean.getState().getMedia().intValue() == 0) {
                    d4shDeviceRecordView.showCameraOffTv(Boolean.TRUE);
                    d4shDeviceRecordView.hidePreview();
                } else if (itemsBean.getEatVideo() == 0) {
                    d4shDeviceRecordView.showEatVideoOffTv(Boolean.TRUE);
                    d4shDeviceRecordView.hidePreview();
                } else if (TextUtils.isEmpty(itemsBean.getPreview())) {
                    d4shDeviceRecordView.hidePreview();
                    if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                        d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                        d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
                    }
                } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                    d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                    d4shDeviceRecordView.hidePreview();
                    d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
                } else {
                    d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
                }
            } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                d4shDeviceRecordView.hidePreview();
                d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
            } else if (itemsBean.getState() == null || itemsBean.getState().getMedia() == null) {
                if (!TextUtils.isEmpty(itemsBean.getPreview())) {
                    d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey(), Integer.valueOf(R.drawable.petkit_feeder_device_record_item_video_picture_view_play_icon), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 26.0f)), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 26.0f)));
                } else {
                    d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(Integer.valueOf(R.drawable.solid_d4sh_device_record_picture_preview_gray_corners_8), Integer.valueOf(R.drawable.petkit_feeder_device_record_video_picture_view_logo_icon), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 44.0f)), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 44.0f)));
                }
            } else if (itemsBean.getState() != null && itemsBean.getState().getMedia() != null && itemsBean.getState().getMedia().intValue() == 0) {
                d4shDeviceRecordView.showCameraOffTv(Boolean.TRUE);
                d4shDeviceRecordView.hidePreview();
            } else if (!TextUtils.isEmpty(itemsBean.getPreview())) {
                d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey(), Integer.valueOf(R.drawable.petkit_feeder_device_record_item_video_picture_view_play_icon), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 26.0f)), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 26.0f)));
            } else {
                d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(Integer.valueOf(R.drawable.solid_d4sh_device_record_picture_preview_gray_corners_8), Integer.valueOf(R.drawable.petkit_feeder_device_record_video_picture_view_logo_icon), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 44.0f)), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 44.0f)));
            }
        } else if (itemsBean.getState() == null || itemsBean.getState().getMedia() == null) {
            if (itemsBean.getEatVideo() == 0) {
                d4shDeviceRecordView.showEatPicOffTv(Boolean.TRUE);
                d4shDeviceRecordView.hidePreview();
            } else if (TextUtils.isEmpty(itemsBean.getPreview())) {
                d4shDeviceRecordView.hidePreview();
                if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                    d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                    d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
                }
            } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                d4shDeviceRecordView.hidePreview();
                d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
            } else {
                d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
            }
        } else if (itemsBean.getState() != null && itemsBean.getState().getMedia() != null && itemsBean.getState().getMedia().intValue() == 0) {
            d4shDeviceRecordView.showCameraPicOffTv(Boolean.TRUE);
            d4shDeviceRecordView.hidePreview();
        } else if (itemsBean.getEatVideo() == 0) {
            d4shDeviceRecordView.showEatPicOffTv(Boolean.TRUE);
            d4shDeviceRecordView.hidePreview();
        } else if (TextUtils.isEmpty(itemsBean.getPreview())) {
            d4shDeviceRecordView.hidePreview();
            if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
                d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
            }
        } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire() && itemsBean.getExpire() != 0) {
            d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
            d4shDeviceRecordView.hidePreview();
            d4shDeviceRecordView.setDeviceRecordDescSpannableString(null);
        } else {
            d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
        }
        if (this.d4shRecord.getCloudProduct() != null && itemsBean.getStartTime() <= Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate()) && !TextUtils.isEmpty(mediaApi) && this.d4shDeviceRecordTopVideoInScreenIndex == i) {
            d4shDeviceRecordView.setFeederDeviceRecordItemViewPictureViewVideoUrl(itemsBean.getMediaApi());
        } else {
            d4shDeviceRecordView.pausePreviewVideo();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void setD4shDeviceRecordTopVideoInScreenIndex(int i) {
        ArrayList<D4shDayItem> arrayList = this.dateDataList;
        if (arrayList == null || arrayList.size() <= i || !D4shUtils.previewVideoAvailable(this.dateDataList.get(i)) || this.d4shDeviceRecordTopVideoInScreenIndex == i) {
            return;
        }
        this.d4shDeviceRecordTopVideoInScreenIndex = i;
        notifyDataSetChanged();
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

    public static class EatCompareRecordViewHolder extends BaseRecordViewHolder {
        public ImageView ivEatCompareOne;
        public ImageView ivEatCompareTwo;
        public LinearLayout llEatCompare;
        public RelativeLayout rlEatCompareOne;
        public RelativeLayout rlEatCompareTwo;
        public SwipeMenuLayout swipeMenuLayout;
        public TextView tvDateAmPm;
        public TextView tvDateRecordAmount;
        public TextView tvDateRecordPrompt;
        public TextView tvDateRecordTime;
        public TextView tvDel;

        public EatCompareRecordViewHolder(View view) {
            super(view);
            this.tvDateRecordTime = (TextView) view.findViewById(R.id.tv_date_record_time);
            this.tvDateAmPm = (TextView) view.findViewById(R.id.tv_date_am_pm);
            this.tvDateRecordAmount = (TextView) view.findViewById(R.id.tv_date_record_amount);
            this.tvDateRecordPrompt = (TextView) view.findViewById(R.id.tv_date_record_prompt);
            this.llEatCompare = (LinearLayout) view.findViewById(R.id.ll_eat_compare);
            this.ivEatCompareOne = (ImageView) view.findViewById(R.id.iv_eat_compare_one);
            this.ivEatCompareTwo = (ImageView) view.findViewById(R.id.iv_eat_compare_two);
            this.rlEatCompareOne = (RelativeLayout) view.findViewById(R.id.rl_eat_compare_one);
            this.rlEatCompareTwo = (RelativeLayout) view.findViewById(R.id.rl_eat_compare_two);
            this.tvDel = (TextView) view.findViewById(R.id.tv_del);
            this.swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipe);
        }
    }

    public static class DateRecordWithDeleteViewHolder extends BaseRecordViewHolder {
        public D4shDeviceRecordView d4shDeviceRecordView;
        public TextView tvDel;

        public DateRecordWithDeleteViewHolder(View view) {
            super(view);
            this.d4shDeviceRecordView = (D4shDeviceRecordView) view.findViewById(R.id.d4sh_device_record_view);
            this.tvDel = (TextView) view.findViewById(R.id.tv_del);
        }
    }

    public static class DateRecordViewHolder extends BaseRecordViewHolder {
        public D4shDeviceRecordView d4shDeviceRecordView;

        public DateRecordViewHolder(View view) {
            super(view);
            this.d4shDeviceRecordView = (D4shDeviceRecordView) view;
        }
    }

    public static class HistoryRecordEmptyViewHolder extends BaseRecordViewHolder {
        public HistoryRecordEmptyViewHolder(View view) {
            super(view);
        }
    }

    public static class RecordEmptyViewHolder extends BaseRecordViewHolder {
        public TextView tvEmpty;

        public RecordEmptyViewHolder(View view) {
            super(view);
            this.tvEmpty = (TextView) view.findViewById(R.id.tv_empty_context);
        }
    }
}

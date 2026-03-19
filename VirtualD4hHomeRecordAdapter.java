package com.petkit.android.activities.virtual.d4sh.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.petkitBleDevice.d4.mode.FeedStatistic;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4shDateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDayItem;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVideoRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVideoRecordFeedbackMsg;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shDeviceRecordView;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes6.dex */
public class VirtualD4hHomeRecordAdapter extends D4shAbstractHomeRecordAdapter {
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

    public VirtualD4hHomeRecordAdapter(Context context, long j) {
        this.mContext = context;
        this.deviceId = j;
    }

    public VirtualD4hHomeRecordAdapter(Context context, long j, int i, int i2, RecordOnClickListener recordOnClickListener) {
        EventBus.getDefault().register(this);
        this.listener = recordOnClickListener;
        this.filterRecordType = i2;
        this.mContext = context;
        this.deviceId = j;
        this.d4shRecord = D4shUtils.getD4shRecordByDeviceId(j, i);
        this.serviceInvalid = true;
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
        return new HistoryRecordEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.d4sh_device_record_adapter_empty_view, viewGroup, false));
    }

    /* JADX WARN: Removed duplicated region for block: B:338:0x1257  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0317  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder r65, @android.annotation.SuppressLint({"RecyclerView"}) final int r66) {
        /*
            Method dump skipped, instruction units count: 6921
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4hHomeRecordAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(final BaseRecordViewHolder baseRecordViewHolder, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordViewHolder) baseRecordViewHolder).d4shDeviceRecordView.isDeviceRecordPlanCheckBoxCheck()) {
            if (this.d4shRecord.getState().getPim() == 2) {
                if (this.d4shRecord.getState().getConservationStatus() == 1) {
                    Context context = this.mContext;
                    NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                    newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4hHomeRecordAdapter.2
                        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                        public void onClickIKnow() {
                            ((DateRecordViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.FALSE);
                        }
                    });
                    if (newIKnowWindow.isShowing()) {
                        return;
                    }
                    newIKnowWindow.show(((D4shHomeActivity) this.mContext).getWindow().getDecorView());
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
                newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4hHomeRecordAdapter.3
                    @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                    public void onClickIKnow() {
                        ((DateRecordViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.TRUE);
                    }
                });
                if (newIKnowWindow2.isShowing()) {
                    return;
                }
                newIKnowWindow2.show(((D4shHomeActivity) this.mContext).getWindow().getDecorView());
                return;
            }
            removeDailyFeed(itemsBean);
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

    public final /* synthetic */ void lambda$onBindViewHolder$2(D4shVideoRecord d4shVideoRecord, View view) {
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
                return R.drawable.d4sh_state_complete;
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
            if (this.dateDataList.get(i).getType() == 2 && this.d4shRecord.getDeviceShared() == null) {
                return 3;
            }
            if (this.dateDataList.get(i).getType() == 3 && this.d4shRecord.getDeviceShared() == null) {
                return 3;
            }
            return (this.dateDataList.get(i).getType() == 4 && this.d4shRecord.getDeviceShared() == null) ? 3 : 1;
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

    private SpannableString getDeviceRecordDescSpannableString(int i, int i2) {
        String amountFormat;
        String str;
        if (this.d4shRecord.getTypeCode() == 0) {
            amountFormat = D4shUtils.getAmountFormat(i, i2);
        } else {
            amountFormat = D4Utils.getAmountFormat(i, i2);
        }
        Resources resources = this.mContext.getResources();
        int i3 = R.string.D3_record_amount_prompt;
        if (this.d4shRecord.getTypeCode() == 0) {
            str = amountFormat + D4shUtils.getD4shAmountUnit(this.mContext, Integer.parseInt(amountFormat));
        } else {
            str = amountFormat + D4Utils.getD4AmountUnit(this.mContext, i, i2);
        }
        return new SpannableString(String.format("%s", resources.getString(i3, str)));
    }

    public void setMediaInfo(int i, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, D4shDeviceRecordView d4shDeviceRecordView) {
        String mediaApi = itemsBean.getMediaApi();
        if (this.d4shRecord.getServiceStatus() != 0) {
            if (TextUtils.isEmpty(mediaApi)) {
                if (itemsBean.getState() == null || itemsBean.getState().getMedia() == null) {
                    if (TextUtils.isEmpty(itemsBean.getPreview())) {
                        d4shDeviceRecordView.hidePreview();
                    } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire()) {
                        d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                        d4shDeviceRecordView.hidePreview();
                    } else {
                        d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
                    }
                } else if (itemsBean.getState() != null && itemsBean.getState().getMedia() != null && itemsBean.getState().getMedia().intValue() == 0) {
                    d4shDeviceRecordView.showCameraOffTv(Boolean.TRUE);
                    d4shDeviceRecordView.hidePreview();
                } else if (TextUtils.isEmpty(itemsBean.getPreview())) {
                    d4shDeviceRecordView.hidePreview();
                } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire()) {
                    d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                    d4shDeviceRecordView.hidePreview();
                } else {
                    d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
                }
            } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire()) {
                d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                d4shDeviceRecordView.hidePreview();
            } else if (!TextUtils.isEmpty(itemsBean.getPreview())) {
                d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey(), Integer.valueOf(R.drawable.petkit_feeder_device_record_item_video_picture_view_play_icon), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 26.0f)), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 26.0f)));
            } else {
                d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(Integer.valueOf(R.drawable.solid_d4sh_device_record_picture_preview_gray_corners_8), Integer.valueOf(R.drawable.petkit_feeder_device_record_video_picture_view_logo_icon), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 44.0f)), Integer.valueOf(ArmsUtils.dip2px(this.mContext, 44.0f)));
            }
        } else if (itemsBean.getState() == null || itemsBean.getState().getMedia() == null) {
            if (TextUtils.isEmpty(itemsBean.getPreview())) {
                d4shDeviceRecordView.hidePreview();
            } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire()) {
                d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
                d4shDeviceRecordView.hidePreview();
            } else {
                d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
            }
        } else if (itemsBean.getState() != null && itemsBean.getState().getMedia() != null && itemsBean.getState().getMedia().intValue() == 0) {
            d4shDeviceRecordView.showCameraPicOffTv(Boolean.TRUE);
            d4shDeviceRecordView.hidePreview();
        } else if (TextUtils.isEmpty(itemsBean.getPreview())) {
            d4shDeviceRecordView.hidePreview();
        } else if (System.currentTimeMillis() / 1000 > itemsBean.getExpire()) {
            d4shDeviceRecordView.showVideoExpiredTv(Boolean.TRUE);
            d4shDeviceRecordView.hidePreview();
        } else {
            d4shDeviceRecordView.showDeviceRecordVideoPictureViewPicturePreview(itemsBean.getPreview(), itemsBean.getAesKey());
        }
        if (this.d4shRecord.getServiceStatus() != 0 && !TextUtils.isEmpty(mediaApi) && this.d4shDeviceRecordTopVideoInScreenIndex == i) {
            d4shDeviceRecordView.setFeederDeviceRecordItemViewPictureViewVideoUrl(mediaApi);
        } else {
            d4shDeviceRecordView.pausePreviewVideo();
        }
    }

    @Subscriber
    public void updateD4shVideoRecordFeedback(D4shVideoRecordFeedbackMsg d4shVideoRecordFeedbackMsg) {
        if (this.dateDataList != null) {
            String eventId = d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getEventId();
            int recordType = d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getRecordType();
            for (int i = 0; i < this.dateDataList.size(); i++) {
                D4shDayItem d4shDayItem = this.dateDataList.get(i);
                int type = d4shDayItem.getType();
                if (type == 1) {
                    D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyFeed.ItemsBean) d4shDayItem.getBean();
                    if (recordType == d4shDayItem.getType() && itemsBean.getEventId().equals(eventId)) {
                        itemsBean.setIsNeedUploadVideo(0);
                        notifyDataSetChanged();
                        return;
                    }
                } else if (type == 2) {
                    D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean2 = (D4shDailyFeeds.D4shDailyEat.ItemsBean) d4shDayItem.getBean();
                    if (recordType == d4shDayItem.getType() && itemsBean2.getEventId().equals(eventId)) {
                        itemsBean2.setIsNeedUploadVideo(0);
                        notifyDataSetChanged();
                        return;
                    }
                } else if (type == 3) {
                    D4shDailyFeeds.PetBean.ItemsBean itemsBean3 = (D4shDailyFeeds.PetBean.ItemsBean) d4shDayItem.getBean();
                    if (recordType == d4shDayItem.getType() && itemsBean3.getEventId().equals(eventId)) {
                        itemsBean3.setIsNeedUploadVideo(0);
                        notifyDataSetChanged();
                        return;
                    }
                } else if (type != 4) {
                    continue;
                } else {
                    D4shDailyFeeds.MoveBean.ItemsBeanX itemsBeanX = (D4shDailyFeeds.MoveBean.ItemsBeanX) d4shDayItem.getBean();
                    if (recordType == d4shDayItem.getType() && itemsBeanX.getEventId().equals(eventId)) {
                        itemsBeanX.setIsNeedUploadVideo(0);
                        notifyDataSetChanged();
                        return;
                    }
                }
            }
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
}

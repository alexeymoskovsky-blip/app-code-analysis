package com.petkit.android.activities.petkitBleDevice.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.utils.ArmsUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.petkit.android.activities.base.adapter.BaseCard;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.petkitBleDevice.mode.T3DeviceRecord;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.widget.frame.DividerView;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes4.dex */
public class T3HomeHistoryRecordAdapter extends RecyclerView.Adapter<BaseRecordViewHolder> {
    public SimpleDateFormat dateFormat;
    public long deviceId;
    public FamilyInfor familyInfor;
    public int firstPetInIndex;
    public boolean isShared;
    public ArrayList<T3DeviceRecord> items;
    public OnClickListener listener;
    public Activity mContext;
    public String recordDate;
    public TimeZone timezone;
    public boolean underweightFlag;
    public String weight;
    public int weightUnit;

    public interface OnClickListener {
        void onDelClick(T3DeviceRecord t3DeviceRecord, int i, String str);

        void onUnderweightClick();

        void onUnknownPetClick(String str, String str2, String str3, double d);

        void onViewClick(T3DeviceRecord t3DeviceRecord, int i, String str);
    }

    public String getRecordDate() {
        return this.recordDate;
    }

    public void setRecordDate(String str) {
        this.recordDate = str;
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public int getFirstPetInIndex() {
        return this.firstPetInIndex;
    }

    public void setFirstPetInIndex(int i) {
        this.firstPetInIndex = i;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public void setData(List<T3DeviceRecord> list, TimeZone timeZone, String str) {
        this.timezone = timeZone;
        this.recordDate = str;
        this.items.clear();
        this.items.addAll(list);
        notifyDataSetChanged();
        this.firstPetInIndex = -1;
        if (this.isShared) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (this.items.get(i).getEventType() == 10) {
                this.firstPetInIndex = i;
                return;
            }
        }
    }

    public ArrayList<T3DeviceRecord> getData() {
        return this.items;
    }

    public T3HomeHistoryRecordAdapter(Activity activity, boolean z, long j, FamilyInfor familyInfor) {
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
        this.firstPetInIndex = -1;
        this.weight = "";
        this.underweightFlag = false;
        this.mContext = activity;
        this.familyInfor = familyInfor;
        this.items = new ArrayList<>();
        this.isShared = z;
        this.deviceId = j;
        this.underweightFlag = BleDeviceUtils.getT3RecordByDeviceId(j).getSettings().getUnderweight() == 1;
    }

    public T3HomeHistoryRecordAdapter(Activity activity, OnClickListener onClickListener) {
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
        this.firstPetInIndex = -1;
        this.weight = "";
        this.underweightFlag = false;
        this.listener = onClickListener;
        this.mContext = activity;
        this.items = new ArrayList<>();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == -1) {
            return new HistoryRecordEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t3_history_record_empty, viewGroup, false));
        }
        if (i == 262) {
            return new HistoryRecordGroupViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t3_history_record_group, viewGroup, false));
        }
        return new HistoryRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t3_history_record_item, viewGroup, false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0625  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0634  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0372  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0511  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0526  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0540  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.BaseRecordViewHolder r20, @android.annotation.SuppressLint({"RecyclerView"}) final int r21) {
        /*
            Method dump skipped, instruction units count: 1857
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter$BaseRecordViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onDelClick(this.items.get(i), i, this.recordDate);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$1(double d, int i, View view) {
        Resources resources;
        int i2;
        if (this.listener != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.weightUnit == 0 ? String.format("%.1f", Double.valueOf(d)) : String.format("%.1f", Double.valueOf(CommonUtil.KgToLb(d))));
            if (this.weightUnit == 0) {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_kg;
            } else {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_lb;
            }
            sb.append(resources.getString(i2));
            String string = sb.toString();
            this.listener.onUnknownPetClick(string, "" + this.items.get(i).getTimestamp(), this.items.get(i).getPetId(), d);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter$1, reason: invalid class name */
    public class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
        final /* synthetic */ BaseRecordViewHolder val$holder;
        final /* synthetic */ boolean val$index;
        final /* synthetic */ int val$position;
        final /* synthetic */ String val$tempWeight;
        final /* synthetic */ String val$text;

        public AnonymousClass1(BaseRecordViewHolder baseRecordViewHolder, String str, String str2, int i, boolean z) {
            this.val$holder = baseRecordViewHolder;
            this.val$text = str;
            this.val$tempWeight = str2;
            this.val$position = i;
            this.val$index = z;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            ((HistoryRecordGroupViewHolder) this.val$holder).rlHistoryRecordContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            final int width = ((HistoryRecordGroupViewHolder) this.val$holder).rlHistoryRecordContent.getWidth() - ArmsUtils.dip2px(T3HomeHistoryRecordAdapter.this.mContext, 8.0f);
            StaticLayout staticLayout = new StaticLayout(this.val$text, ((HistoryRecordGroupViewHolder) this.val$holder).tvHistoryRecordContent.getPaint(), width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            float lineRight = staticLayout.getLineRight(staticLayout.getLineCount() - 1);
            float lineBottom = staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
            final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((HistoryRecordGroupViewHolder) this.val$holder).ivUnderweight.getLayoutParams();
            if (ArmsUtils.dip2px(T3HomeHistoryRecordAdapter.this.mContext, 21.0f) + lineRight > width) {
                String string = T3HomeHistoryRecordAdapter.this.mContext.getResources().getString(R.string.T3_history_record_pet_into_with_weight_prompt, "\n" + this.val$tempWeight);
                StringBuilder sb = new StringBuilder();
                Resources resources = T3HomeHistoryRecordAdapter.this.mContext.getResources();
                int i = R.string.T3_history_record_pet_into_prompt;
                T3HomeHistoryRecordAdapter t3HomeHistoryRecordAdapter = T3HomeHistoryRecordAdapter.this;
                sb.append(resources.getString(i, t3HomeHistoryRecordAdapter.calDurationTime(((T3DeviceRecord) t3HomeHistoryRecordAdapter.items.get(this.val$position)).getContent().getTimeOut(), ((T3DeviceRecord) T3HomeHistoryRecordAdapter.this.items.get(this.val$position)).getContent().getTimeIn())));
                sb.append(string);
                String string2 = sb.toString();
                SpannableString spannableString = new SpannableString(string2);
                spannableString.setSpan(new ForegroundColorSpan(T3HomeHistoryRecordAdapter.this.mContext.getResources().getColor(R.color.light_black)), string2.indexOf(string) + (this.val$index ? 1 : 0), string2.indexOf(string) + string.length(), 33);
                ((HistoryRecordGroupViewHolder) this.val$holder).tvHistoryRecordContent.setText(spannableString);
                final String string3 = spannableString.toString();
                final BaseRecordViewHolder baseRecordViewHolder = this.val$holder;
                ((HistoryRecordGroupViewHolder) baseRecordViewHolder).rlHistoryRecordContent.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onGlobalLayout$0(string3, baseRecordViewHolder, width, layoutParams);
                    }
                });
                return;
            }
            layoutParams.leftMargin = ((int) lineRight) + ArmsUtils.dip2px(T3HomeHistoryRecordAdapter.this.mContext, 5.0f);
            layoutParams.topMargin = ((int) lineBottom) - ArmsUtils.dip2px(T3HomeHistoryRecordAdapter.this.mContext, 11.0f);
            PetkitLog.d("leftMargin:" + layoutParams.leftMargin + ",topMargin:" + layoutParams.topMargin);
            ((HistoryRecordGroupViewHolder) this.val$holder).ivUnderweight.setLayoutParams(layoutParams);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGlobalLayout$0(String str, BaseRecordViewHolder baseRecordViewHolder, int i, RelativeLayout.LayoutParams layoutParams) {
            HistoryRecordGroupViewHolder historyRecordGroupViewHolder = (HistoryRecordGroupViewHolder) baseRecordViewHolder;
            StaticLayout staticLayout = new StaticLayout(str, historyRecordGroupViewHolder.tvHistoryRecordContent.getPaint(), i, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            float lineRight = staticLayout.getLineRight(staticLayout.getLineCount() - 1);
            float lineBottom = staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
            layoutParams.leftMargin = ((int) lineRight) + ArmsUtils.dip2px(T3HomeHistoryRecordAdapter.this.mContext, 5.0f);
            layoutParams.topMargin = ((int) lineBottom) - ArmsUtils.dip2px(T3HomeHistoryRecordAdapter.this.mContext, 11.0f);
            PetkitLog.d("leftMargin:" + layoutParams.leftMargin + ",topMargin:" + layoutParams.topMargin);
            historyRecordGroupViewHolder.ivUnderweight.setLayoutParams(layoutParams);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$2(View view) {
        this.listener.onUnderweightClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onDelClick(this.items.get(i), i, this.recordDate);
        }
    }

    public final int getImageResByType(T3DeviceRecord t3DeviceRecord) {
        int eventType = t3DeviceRecord.getEventType();
        if (eventType == 5 || eventType == 6 || eventType == 7) {
            if (t3DeviceRecord.getContent().getResult() == 0) {
                return R.drawable.history_finish_icon;
            }
            return R.drawable.history_error_icon;
        }
        if (eventType != 8) {
            if (eventType == 10) {
                return R.drawable.history_left_icon;
            }
            return R.drawable.history_finish_icon;
        }
        if (t3DeviceRecord.getContent().getResult() == 0) {
            return R.drawable.history_deo_icon;
        }
        return R.drawable.history_error_icon;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getDesContent(com.petkit.android.activities.petkitBleDevice.mode.T3DeviceRecord r12) {
        /*
            Method dump skipped, instruction units count: 842
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.getDesContent(com.petkit.android.activities.petkitBleDevice.mode.T3DeviceRecord):java.lang.String");
    }

    public final String getDeodorantContentByStartReason(int i, int i2) {
        if (i == 0) {
            if (i2 == 0) {
                return this.mContext.getResources().getString(R.string.K3_deodorant_record_atuo_deodorant_complete_prompt);
            }
            if (i2 == 1) {
                return this.mContext.getResources().getString(R.string.T3_history_record_timing_deodorant_complete_prompt);
            }
            if (i2 == 2 || i2 == 3) {
                return this.mContext.getResources().getString(R.string.T3_history_record_manual_deodorant_complete_prompt);
            }
            return "";
        }
        if (i == 2) {
            if (i2 == 0) {
                return this.mContext.getResources().getString(R.string.T3_history_record_auto_deodorant_error_prompt);
            }
            if (i2 == 1) {
                return this.mContext.getResources().getString(R.string.T3_history_record_timing_deodorant_error_prompt);
            }
            if (i2 == 2 || i2 == 3) {
                return this.mContext.getResources().getString(R.string.T3_history_record_manual_deodorant_error_prompt);
            }
            return "";
        }
        if (i != 3) {
            return "";
        }
        if (i2 == 0) {
            return this.mContext.getResources().getString(R.string.K3_deodorant_record_atuo_deodorant_complete_prompt) + this.mContext.getResources().getString(R.string.T3_history_record_deodorant_complete_liquid_prompt);
        }
        if (i2 == 1) {
            return this.mContext.getResources().getString(R.string.T3_history_record_timing_deodorant_complete_prompt) + this.mContext.getResources().getString(R.string.T3_history_record_deodorant_complete_liquid_prompt);
        }
        if (i2 == 2 || i2 == 3) {
            return this.mContext.getResources().getString(R.string.T3_history_record_manual_deodorant_complete_prompt) + this.mContext.getResources().getString(R.string.T3_history_record_deodorant_complete_liquid_prompt);
        }
        return "";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x015d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getCleanContentByStartReason(int r9, int r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instruction units count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.getCleanContentByStartReason(int, int, java.lang.String, java.lang.String):java.lang.String");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (this.items.size() == 0) {
            return -1;
        }
        if (this.items.get(i).getEventType() == 10) {
            return BaseCard.VIEW_TYPE_T3_GROUP;
        }
        return 7;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.items.size() == 0) {
            return 1;
        }
        return this.items.size();
    }

    public String calDurationTime(long j, long j2) {
        return TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, j - j2);
    }

    public static class HistoryRecordGroupViewHolder extends BaseRecordViewHolder {
        public DividerView dividerView;
        public DividerView dividerView1;
        public ImageView ivRecordState;
        public ImageView ivUnderweight;
        public TextView lightWeightPrompt;
        public LinearLayout llClearRecord;
        public LinearLayout llDeodorantRecord;
        public RelativeLayout rlHistoryRecordContent;
        public RelativeLayout rlToiletContent;
        public SwipeMenuLayout swipeMenuLayout;
        public TextView tvClearContent;
        public TextView tvDel;
        public TextView tvDeodorantContent;
        public TextView tvHistoryRecordContent;
        public TextView tvHistoryRecordPrompt;
        public TextView tvHistoryRecordTime;

        public HistoryRecordGroupViewHolder(View view) {
            super(view);
            this.tvDel = (TextView) view.findViewById(R.id.tv_del);
            this.swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.sml_layout);
            this.tvHistoryRecordTime = (TextView) view.findViewById(R.id.tv_history_record_time);
            this.tvHistoryRecordContent = (TextView) view.findViewById(R.id.tv_history_record_content);
            this.tvHistoryRecordPrompt = (TextView) view.findViewById(R.id.tv_history_record_prompt);
            this.lightWeightPrompt = (TextView) view.findViewById(R.id.light_weight_prompt);
            this.llClearRecord = (LinearLayout) view.findViewById(R.id.ll_clear_record);
            this.llDeodorantRecord = (LinearLayout) view.findViewById(R.id.ll_deodorant_record);
            this.ivRecordState = (ImageView) view.findViewById(R.id.iv_record_state);
            this.rlToiletContent = (RelativeLayout) view.findViewById(R.id.rl_toilet_content);
            this.tvClearContent = (TextView) view.findViewById(R.id.tv_clear_content);
            this.tvDeodorantContent = (TextView) view.findViewById(R.id.tv_deodorant_content);
            this.dividerView = (DividerView) view.findViewById(R.id.divider_view);
            this.dividerView1 = (DividerView) view.findViewById(R.id.divider_view1);
            this.ivUnderweight = (ImageView) view.findViewById(R.id.iv_underweight);
            this.rlHistoryRecordContent = (RelativeLayout) view.findViewById(R.id.rl_history_record_content);
        }
    }

    public static class HistoryRecordViewHolder extends BaseRecordViewHolder {
        public DividerView dividerView;
        public ImageView ivRecordState;
        public SwipeMenuLayout swipeMenuLayout;
        public TextView tvDel;
        public TextView tvHistoryRecordContent;
        public TextView tvHistoryRecordTime;

        public HistoryRecordViewHolder(View view) {
            super(view);
            this.tvDel = (TextView) view.findViewById(R.id.tv_del);
            this.swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.sml_layout);
            this.tvHistoryRecordTime = (TextView) view.findViewById(R.id.tv_history_record_time);
            this.tvHistoryRecordContent = (TextView) view.findViewById(R.id.tv_history_record_content);
            this.ivRecordState = (ImageView) view.findViewById(R.id.iv_record_state);
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

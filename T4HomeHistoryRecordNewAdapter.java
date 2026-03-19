package com.petkit.android.activities.petkitBleDevice.t4.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextUtils;
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
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4DeviceRecord;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.widget.frame.DividerView;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes5.dex */
public class T4HomeHistoryRecordNewAdapter extends RecyclerView.Adapter<BaseRecordViewHolder> {
    public SimpleDateFormat dateFormat;
    public long deviceId;
    public FamilyInfor familyInfor;
    public int firstPetInIndex;
    public boolean isShared;
    public ArrayList<T4DeviceRecord> items;
    public OnClickListener listener;
    public Activity mContext;
    public String recordDate;
    public TimeZone timezone;
    public boolean underweightFlag;
    public String weight;
    public int weightUnit;

    public interface OnClickListener {
        void onDelClick(T4DeviceRecord t4DeviceRecord, int i, String str);

        void onUnderweightClick();

        void onUnknownPetClick(String str, String str2, String str3, double d, int i);

        void onViewClick(T4DeviceRecord t4DeviceRecord, int i, String str);
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

    public List<T4DeviceRecord> getDataList() {
        return this.items;
    }

    public void setData(List<T4DeviceRecord> list, TimeZone timeZone, String str) {
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

    public ArrayList<T4DeviceRecord> getData() {
        return this.items;
    }

    public T4HomeHistoryRecordNewAdapter(Activity activity, boolean z, long j, FamilyInfor familyInfor) {
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
        this.firstPetInIndex = -1;
        this.weight = "";
        this.underweightFlag = false;
        this.mContext = activity;
        this.familyInfor = familyInfor;
        this.deviceId = j;
        this.items = new ArrayList<>();
        this.isShared = z;
        this.underweightFlag = T4Utils.getT4RecordByDeviceId(j).getSettings().getUnderweight() == 1;
    }

    public T4HomeHistoryRecordNewAdapter(Activity activity, OnClickListener onClickListener) {
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
            return new HistoryRecordGroupViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t4_history_record_group, viewGroup, false));
        }
        return new HistoryRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t3_history_record_item, viewGroup, false));
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0623  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0632  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x050b  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0520  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x053a  */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v14 */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.BaseRecordViewHolder r20, @android.annotation.SuppressLint({"RecyclerView"}) final int r21) {
        /*
            Method dump skipped, instruction units count: 1943
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter$BaseRecordViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onDelClick(this.items.get(i), i, this.recordDate);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(double d, int i, View view) {
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
            this.listener.onUnknownPetClick(string, "" + this.items.get(i).getTimestamp(), this.items.get(i).getPetId(), d, i);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter$1, reason: invalid class name */
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
            final int width = ((HistoryRecordGroupViewHolder) this.val$holder).rlHistoryRecordContent.getWidth() - ArmsUtils.dip2px(T4HomeHistoryRecordNewAdapter.this.mContext, 8.0f);
            StaticLayout staticLayout = new StaticLayout(this.val$text, ((HistoryRecordGroupViewHolder) this.val$holder).tvHistoryRecordContent.getPaint(), width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            float lineRight = staticLayout.getLineRight(staticLayout.getLineCount() - 1);
            float lineBottom = staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
            final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((HistoryRecordGroupViewHolder) this.val$holder).ivUnderweight.getLayoutParams();
            if (ArmsUtils.dip2px(T4HomeHistoryRecordNewAdapter.this.mContext, 21.0f) + lineRight > width) {
                String string = T4HomeHistoryRecordNewAdapter.this.mContext.getResources().getString(R.string.T3_history_record_pet_into_with_weight_prompt, "\n" + this.val$tempWeight);
                StringBuilder sb = new StringBuilder();
                Resources resources = T4HomeHistoryRecordNewAdapter.this.mContext.getResources();
                int i = R.string.T3_history_record_pet_into_prompt;
                T4HomeHistoryRecordNewAdapter t4HomeHistoryRecordNewAdapter = T4HomeHistoryRecordNewAdapter.this;
                sb.append(resources.getString(i, t4HomeHistoryRecordNewAdapter.calDurationTime(((T4DeviceRecord) t4HomeHistoryRecordNewAdapter.items.get(this.val$position)).getContent().getTimeOut(), ((T4DeviceRecord) T4HomeHistoryRecordNewAdapter.this.items.get(this.val$position)).getContent().getTimeIn())));
                sb.append(string);
                String string2 = sb.toString();
                SpannableString spannableString = new SpannableString(string2);
                spannableString.setSpan(new ForegroundColorSpan(T4HomeHistoryRecordNewAdapter.this.mContext.getResources().getColor(R.color.light_black)), string2.indexOf(string) + (this.val$index ? 1 : 0), string2.indexOf(string) + string.length(), 33);
                ((HistoryRecordGroupViewHolder) this.val$holder).tvHistoryRecordContent.setText(spannableString);
                final String string3 = spannableString.toString();
                final BaseRecordViewHolder baseRecordViewHolder = this.val$holder;
                ((HistoryRecordGroupViewHolder) baseRecordViewHolder).rlHistoryRecordContent.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onGlobalLayout$0(string3, baseRecordViewHolder, width, layoutParams);
                    }
                });
                return;
            }
            layoutParams.leftMargin = ((int) lineRight) + ArmsUtils.dip2px(T4HomeHistoryRecordNewAdapter.this.mContext, 5.0f);
            layoutParams.topMargin = ((int) lineBottom) - ArmsUtils.dip2px(T4HomeHistoryRecordNewAdapter.this.mContext, 11.0f);
            PetkitLog.d("leftMargin:" + layoutParams.leftMargin + ",topMargin:" + layoutParams.topMargin);
            ((HistoryRecordGroupViewHolder) this.val$holder).ivUnderweight.setLayoutParams(layoutParams);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGlobalLayout$0(String str, BaseRecordViewHolder baseRecordViewHolder, int i, RelativeLayout.LayoutParams layoutParams) {
            HistoryRecordGroupViewHolder historyRecordGroupViewHolder = (HistoryRecordGroupViewHolder) baseRecordViewHolder;
            StaticLayout staticLayout = new StaticLayout(str, historyRecordGroupViewHolder.tvHistoryRecordContent.getPaint(), i, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            float lineRight = staticLayout.getLineRight(staticLayout.getLineCount() - 1);
            float lineBottom = staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
            layoutParams.leftMargin = ((int) lineRight) + ArmsUtils.dip2px(T4HomeHistoryRecordNewAdapter.this.mContext, 10.0f);
            layoutParams.topMargin = ((int) lineBottom) - ArmsUtils.dip2px(T4HomeHistoryRecordNewAdapter.this.mContext, 11.0f);
            PetkitLog.d("leftMargin:" + layoutParams.leftMargin + ",topMargin:" + layoutParams.topMargin);
            historyRecordGroupViewHolder.ivUnderweight.setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$2(View view) {
        this.listener.onUnderweightClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onDelClick(this.items.get(i), i, this.recordDate);
        }
    }

    private int getImageResByType(T4DeviceRecord t4DeviceRecord) {
        int eventType = t4DeviceRecord.getEventType();
        if (eventType != 5 && eventType != 6) {
            if (eventType != 7) {
                if (eventType == 8) {
                    if (t4DeviceRecord.getContent().getResult() == 0 || t4DeviceRecord.getContent().getResult() == 3 || t4DeviceRecord.getContent().getResult() == 8) {
                        return R.drawable.history_deo_icon;
                    }
                    if (t4DeviceRecord.getContent().getResult() == 9) {
                        return R.drawable.history_deo_cancel_icon;
                    }
                    return R.drawable.history_error_icon;
                }
                if (eventType == 10) {
                    return R.drawable.history_left_icon;
                }
                if (eventType == 17) {
                    if (t4DeviceRecord.getContent() == null || t4DeviceRecord.getContent().getResult() == 0 || t4DeviceRecord.getContent().getResult() == 8) {
                        return R.drawable.history_light_icon;
                    }
                    return R.drawable.history_error_icon;
                }
                return R.drawable.history_finish_icon;
            }
            if (t4DeviceRecord.getContent().getResult() == 5) {
                if (!TextUtils.isEmpty(t4DeviceRecord.getContent().getErr())) {
                    return R.drawable.history_error_icon;
                }
                return R.drawable.history_maintenance_icon;
            }
        }
        if (t4DeviceRecord.getContent().getResult() == 0) {
            return R.drawable.history_finish_icon;
        }
        return R.drawable.history_error_icon;
    }

    private String getDesContent(T4DeviceRecord t4DeviceRecord) {
        Resources resources;
        int i;
        if (t4DeviceRecord.getEventType() == 10) {
            if (t4DeviceRecord.getContent().getPetWeight() != null) {
                LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                if (currentLoginResult != null) {
                    this.weightUnit = currentLoginResult.getSettings().getUnit();
                }
                double dIntValue = (t4DeviceRecord.getContent().getPetWeight().intValue() * 1.0f) / 1000.0f;
                StringBuilder sb = new StringBuilder();
                sb.append(this.weightUnit == 0 ? String.format("%.1f", Double.valueOf(dIntValue)) : String.format("%.1f", Double.valueOf(CommonUtil.KgToLb(dIntValue))));
                if (this.weightUnit == 0) {
                    resources = this.mContext.getResources();
                    i = R.string.Unit_kg;
                } else {
                    resources = this.mContext.getResources();
                    i = R.string.Unit_lb;
                }
                sb.append(resources.getString(i));
                String string = sb.toString();
                if (t4DeviceRecord.getContent().getAutoClear() == 2) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_pet_into_prompt_two, calDurationTime(t4DeviceRecord.getContent().getTimeOut(), t4DeviceRecord.getContent().getTimeIn())) + this.mContext.getResources().getString(R.string.T3_history_record_pet_into_with_weight_prompt, string) + this.mContext.getResources().getString(R.string.T3_history_record_pet_into_interval_prompt);
                }
                if (t4DeviceRecord.getContent().getAutoClear() == 5) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_pet_into_prompt_two, calDurationTime(t4DeviceRecord.getContent().getTimeOut(), t4DeviceRecord.getContent().getTimeIn())) + this.mContext.getResources().getString(R.string.T3_history_record_pet_into_with_weight_prompt, string) + this.mContext.getResources().getString(R.string.T4_history_record_pet_into_disturb_prompt);
                }
                return this.mContext.getResources().getString(R.string.T4_history_record_pet_into_prompt_two, calDurationTime(t4DeviceRecord.getContent().getTimeOut(), t4DeviceRecord.getContent().getTimeIn())) + this.mContext.getResources().getString(R.string.T3_history_record_pet_into_with_weight_prompt, string);
            }
            if (t4DeviceRecord.getContent().getAutoClear() == 2) {
                return this.mContext.getResources().getString(R.string.T3_history_record_pet_into_prompt, calDurationTime(t4DeviceRecord.getContent().getTimeOut(), t4DeviceRecord.getContent().getTimeIn())) + this.mContext.getResources().getString(R.string.T3_history_record_pet_into_interval_prompt);
            }
            if (t4DeviceRecord.getContent().getAutoClear() != 5) {
                return this.mContext.getResources().getString(R.string.T3_history_record_pet_into_prompt, calDurationTime(t4DeviceRecord.getContent().getTimeOut(), t4DeviceRecord.getContent().getTimeIn()));
            }
            return this.mContext.getResources().getString(R.string.T3_history_record_pet_into_prompt, calDurationTime(t4DeviceRecord.getContent().getTimeOut(), t4DeviceRecord.getContent().getTimeIn())) + this.mContext.getResources().getString(R.string.T4_history_record_pet_into_disturb_prompt);
        }
        if (t4DeviceRecord.getEventType() == 17) {
            if (t4DeviceRecord.getContent() == null) {
                return this.mContext.getResources().getString(R.string.K3_light_record_on_prompt);
            }
            return getLightContentByStartReason(t4DeviceRecord.getContent().getResult());
        }
        return getDesContent2(t4DeviceRecord);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0175  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getDesContent2(com.petkit.android.activities.petkitBleDevice.t4.mode.T4DeviceRecord r13) {
        /*
            Method dump skipped, instruction units count: 910
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.getDesContent2(com.petkit.android.activities.petkitBleDevice.t4.mode.T4DeviceRecord):java.lang.String");
    }

    public String getLightContentByStartReason(int i) {
        if (i != 0) {
            if (i == 1) {
                return this.mContext.getResources().getString(R.string.T4_control_light_result1);
            }
            if (i == 2) {
                return this.mContext.getResources().getString(R.string.T4_control_light_result2);
            }
            if (i == 5) {
                return this.mContext.getResources().getString(R.string.T4_control_light_result3);
            }
            if (i == 6) {
                return this.mContext.getResources().getString(R.string.T4_control_light_result4);
            }
            if (i == 7) {
                return this.mContext.getResources().getString(R.string.T4_control_light_result5);
            }
            if (i != 8) {
                return "";
            }
        }
        return this.mContext.getResources().getString(R.string.K3_light_record_on_prompt);
    }

    private String getDeodorantContentByStartReason(int i, int i2) {
        switch (i) {
            case 0:
            case 3:
            case 8:
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
            case 1:
                if (i2 == 0) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_auto_deodorant_terminate_prompt);
                }
                if (i2 == 1) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_timing_deodorant_terminate_prompt);
                }
                if (i2 == 2 || i2 == 3) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_manual_deodorant_terminate_prompt);
                }
                break;
            case 2:
                break;
            case 4:
                if (i2 == 0) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_auto_deodorant_cancel_prompt);
                }
                if (i2 == 1) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_timing_deodorant_cancel_prompt);
                }
                if (i2 == 2 || i2 == 3) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_manual_deodorant_cancel_prompt);
                }
                return "";
            case 5:
                if (i2 == 0) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_auto_deodorant_without_k3_error_prompt);
                }
                if (i2 == 1) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_timing_deodorant_without_k3_error_prompt);
                }
                if (i2 == 2 || i2 == 3) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_manual_deodorant_without_k3_error_prompt);
                }
                return "";
            case 6:
                if (i2 == 0) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_auto_deodorant_k3_offline_error_prompt);
                }
                if (i2 == 1) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_timing_deodorant_k3_offline_error_prompt);
                }
                if (i2 == 2 || i2 == 3) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_manual_deodorant_k3_offline_error_prompt);
                }
                return "";
            case 7:
                if (i2 == 0) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_auto_deodorant_low_power_error_prompt);
                }
                if (i2 == 1) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_timing_deodorant_low_power_error_prompt);
                }
                if (i2 == 2 || i2 == 3) {
                    return this.mContext.getResources().getString(R.string.T4_history_record_manual_deodorant_low_power_error_prompt);
                }
                return "";
            case 9:
                return this.mContext.getResources().getString(R.string.T4_record_deodorant_task_cancel);
            default:
                return "";
        }
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getCleanContentByStartReason(int r9, int r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instruction units count: 724
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.getCleanContentByStartReason(int, int, java.lang.String, java.lang.String):java.lang.String");
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

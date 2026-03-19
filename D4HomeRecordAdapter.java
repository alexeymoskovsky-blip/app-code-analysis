package com.petkit.android.activities.petkitBleDevice.d4.adapter;

import android.annotation.SuppressLint;
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
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4DailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4DateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4DayItem;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.mode.FeedStatistic;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.widget.frame.DividerView;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D4HomeRecordAdapter extends RecyclerView.Adapter<BaseRecordViewHolder> {
    public D4DateFeedData d4DateFeedData;
    public D4Record d4Record;
    public ArrayList<D4DayItem> dateDataList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
    public long deviceId;
    public boolean isMonth;
    public OnClickListener listener;
    public Context mContext;
    public List<FeedStatistic.ListBean> weekOrMonthDataList;

    public interface OnClickListener {
        void onDeleteFeedRecordClick(int i, int i2);

        void onViewClick(ItemsBean itemsBean, int i);
    }

    public ArrayList<D4DayItem> getDateDataList() {
        return this.dateDataList;
    }

    public void setDateDataList(ArrayList<D4DayItem> arrayList) {
        this.dateDataList = arrayList;
    }

    public D4DateFeedData getD4DateFeedData() {
        return this.d4DateFeedData;
    }

    public void setD4DateFeedData(D4DateFeedData d4DateFeedData) {
        this.d4DateFeedData = d4DateFeedData;
        this.weekOrMonthDataList = null;
        this.dateDataList = new ArrayList<>();
        for (D4DailyFeeds.D4DailyFeed.ItemsBean itemsBean : d4DateFeedData.getD4DailyFeeds()) {
            this.dateDataList.add(new D4DayItem(itemsBean.getTime(), itemsBean, true));
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

    public D4HomeRecordAdapter(Context context, long j) {
        this.mContext = context;
        this.deviceId = j;
    }

    public D4HomeRecordAdapter(Context context, long j, OnClickListener onClickListener) {
        this.listener = onClickListener;
        this.mContext = context;
        this.deviceId = j;
        this.d4Record = D4Utils.getD4RecordByDeviceId(j);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new DateRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4_date_record_item, viewGroup, false));
        }
        if (i == 2) {
            return new WeekOrMonthRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_d4_month_or_week_record_item, viewGroup, false));
        }
        return new HistoryRecordEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_t3_history_record_empty, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final BaseRecordViewHolder baseRecordViewHolder, @SuppressLint({"RecyclerView"}) final int i) {
        String str = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "，" : ".";
        if (baseRecordViewHolder instanceof DateRecordViewHolder) {
            DateRecordViewHolder dateRecordViewHolder = (DateRecordViewHolder) baseRecordViewHolder;
            dateRecordViewHolder.tvDateRecordTime.setText(D4Utils.calcTime(this.mContext, this.dateDataList.get(i).getTime()));
            final D4DailyFeeds.D4DailyFeed.ItemsBean bean = this.dateDataList.get(i).getBean();
            View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    int i2 = ((D4DayItem) D4HomeRecordAdapter.this.dateDataList.get(i)).isState() ? 8 : 0;
                    ((DateRecordViewHolder) baseRecordViewHolder).rlDateRecordPanel.setVisibility(i2);
                    ((DateRecordViewHolder) baseRecordViewHolder).llDateRecordPointAdditional.setVisibility(i2);
                    if (bean.getIsExecuted() == 0) {
                        ((DateRecordViewHolder) baseRecordViewHolder).llDateRecordPointMost.setVisibility(i2);
                    } else {
                        ((DateRecordViewHolder) baseRecordViewHolder).llDateRecordPointMost.setVisibility(8);
                    }
                    ((DateRecordViewHolder) baseRecordViewHolder).ivDateRecordArrow.setImageResource(i2 == 8 ? R.drawable.d3_record_up : R.drawable.d3_record_down);
                    ((D4DayItem) D4HomeRecordAdapter.this.dateDataList.get(i)).setState(!((D4DayItem) D4HomeRecordAdapter.this.dateDataList.get(i)).isState());
                    D4HomeRecordAdapter.this.setDividerViewHeight(baseRecordViewHolder, true);
                }
            };
            dateRecordViewHolder.ivDateRecordArrow.setOnClickListener(onClickListener);
            dateRecordViewHolder.ivRecordState.setImageResource(getD4ItemDataIconByState(bean));
            if (bean.getStatus() == 0) {
                if (bean.getState() != null) {
                    if (bean.getState().getResult() == 1 || bean.getState().getResult() == 2) {
                        dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mContext.getResources().getString(R.string.D3_record_feed_prompt, getd4ItemDataNameWithState(bean)));
                        sb.append(str);
                        sb.append(this.mContext.getResources().getString(R.string.D3_record_amount_prompt, D4Utils.getAmountFormat(bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor())));
                        sb.append(str);
                        sb.append(this.mContext.getResources().getString(R.string.D3_record_manual_terminal_prompt));
                        dateRecordViewHolder.tvDateRecordAmount.setText(sb.toString());
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                        dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                        dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                    } else if (bean.getState().getResult() == 6) {
                        dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                        dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                        dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                        if (bean.getState().getRealAmount() == 0) {
                            dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D3_record_food_lack_fail_prompt, getd4ItemDataNameWithState(bean)));
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(this.mContext.getResources().getString(R.string.D3_record_feed_prompt, getd4ItemDataNameWithState(bean)));
                            sb2.append(str);
                            sb2.append(this.mContext.getResources().getString(R.string.D3_record_amount_prompt, D4Utils.getAmountFormat(bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor())));
                            sb2.append(str);
                            sb2.append(this.mContext.getResources().getString(R.string.D3_record_food_lack_prompt));
                            dateRecordViewHolder.tvDateRecordAmount.setText(sb2.toString());
                        }
                    } else if (bean.getState().getResult() == 4) {
                        dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                        dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                        dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                        if (bean.getState().getRealAmount() == 0) {
                            dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D4_record_no_food_fail_prompt, getd4ItemDataNameWithState(bean)));
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(this.mContext.getResources().getString(R.string.D3_record_feed_prompt, getd4ItemDataNameWithState(bean)));
                            sb3.append(str);
                            sb3.append(this.mContext.getResources().getString(R.string.D3_record_amount_prompt, D4Utils.getAmountFormat(bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor())));
                            sb3.append(str);
                            sb3.append(this.mContext.getResources().getString(R.string.No_food_prompt));
                            dateRecordViewHolder.tvDateRecordAmount.setText(sb3.toString());
                        }
                    } else if (bean.getState().getResult() == 5) {
                        dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                        dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                        dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                        int errCode = bean.getState().getErrCode();
                        if (errCode == 6) {
                            dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D3_record_ota_cancel_prompt, getd4ItemDataNameWithState(bean)));
                        } else if (errCode == 7) {
                            dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D3_record_feeding_cancel_prompt, getd4ItemDataNameWithState(bean)));
                        }
                    } else if (bean.getState().getResult() == 3) {
                        dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                        dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                        dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                        int errCode2 = bean.getState().getErrCode();
                        if (errCode2 == 1) {
                            dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D3_record_dc_error_prompt, getd4ItemDataNameWithState(bean)));
                        } else if (errCode2 == 2 || errCode2 == 3 || errCode2 == 4) {
                            dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D3_record_system_error_prompt, getd4ItemDataNameWithState(bean)));
                        } else if (errCode2 == 5) {
                            dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D4_record_low_voltage_error_prompt, getd4ItemDataNameWithState(bean)));
                        }
                    } else {
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                        dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                        dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                        if (bean.getState().getErrCode() == 8) {
                            dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                            TextView textView = dateRecordViewHolder.tvDateRecordAmount;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(this.mContext.getResources().getString(R.string.D3_record_feed_prompt, getd4ItemDataNameWithState(bean)));
                            sb4.append(str);
                            sb4.append(this.mContext.getResources().getString(R.string.D3_record_month_prompt, D4Utils.getAmountFormat(bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor()) + str + this.mContext.getResources().getString(R.string.D4_record_door_error_prompt)));
                            textView.setText(sb4.toString());
                        } else {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(this.mContext.getResources().getString(R.string.D3_record_feed_prompt, getd4ItemDataNameWithState(bean)));
                            sb5.append(str);
                            sb5.append(this.mContext.getResources().getString(R.string.D3_record_amount_prompt, D4Utils.getAmountFormat(bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getState().getRealAmount(), this.d4Record.getSettings().getFactor())));
                            dateRecordViewHolder.tvDateRecordAmount.setText(sb5.toString());
                            if (this.d4Record.getDeviceShared() == null) {
                                dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(true);
                                dateRecordViewHolder.tvDel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.2
                                    @Override // android.view.View.OnClickListener
                                    public void onClick(View view) {
                                        if (D4HomeRecordAdapter.this.listener != null) {
                                            D4HomeRecordAdapter.this.listener.onDeleteFeedRecordClick(D4HomeRecordAdapter.this.d4DateFeedData.getDay(), ((D4DayItem) D4HomeRecordAdapter.this.dateDataList.get(i)).getTime());
                                        }
                                    }
                                });
                            } else {
                                dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                            }
                        }
                    }
                } else if (D4Utils.checkD4ItemIsTimeout(this.d4Record.getSettings().getFactor(), this.d4DateFeedData.getDay(), bean, this.d4Record.getActualTimeZone())) {
                    dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                    dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                    dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                    dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D3_record_unknown_prompt, getd4ItemDataNameWithState(bean)));
                    dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                    dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                    dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                    dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(this.mContext.getResources().getString(R.string.D3_record_plan_prompt, getd4ItemDataNameWithState(bean)));
                    sb6.append(str);
                    sb6.append(this.mContext.getResources().getString(R.string.D3_record_amount_prompt, D4Utils.getAmountFormat(bean.getAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getAmount(), this.d4Record.getSettings().getFactor())));
                    dateRecordViewHolder.tvDateRecordAmount.setText(sb6.toString());
                    if (this.dateDataList.get(i).isState()) {
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(0);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(0);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        if (this.dateDataList.get(i).getBean().getSrc() == 3) {
                            dateRecordViewHolder.dateRecordCheckbox.setVisibility(8);
                        } else {
                            dateRecordViewHolder.dateRecordCheckbox.setVisibility(0);
                        }
                    } else {
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                    }
                    dateRecordViewHolder.tvDateRecordState.setText(this.mContext.getResources().getString(R.string.Feeder_state_wait));
                    dateRecordViewHolder.dateRecordCheckbox.setChecked(true);
                    dateRecordViewHolder.ivDateRecordArrow.setVisibility(0);
                    dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(onClickListener);
                    dateRecordViewHolder.rlRootPanel.setOnClickListener(onClickListener);
                    dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                    dateRecordViewHolder.ivDateRecordArrow.setImageResource(!this.dateDataList.get(i).isState() ? R.drawable.d3_record_up : R.drawable.d3_record_down);
                    if (this.dateDataList.get(i).isState()) {
                        if (bean.getIsExecuted() == 1) {
                            dateRecordViewHolder.tvDateRecordSave.setVisibility(8);
                            dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(0);
                            dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        } else {
                            dateRecordViewHolder.tvDateRecordSave.setVisibility(0);
                            dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(0);
                            dateRecordViewHolder.llDateRecordPointMost.setVisibility(0);
                        }
                    } else if (bean.getIsExecuted() == 1) {
                        dateRecordViewHolder.tvDateRecordSave.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                    } else {
                        dateRecordViewHolder.tvDateRecordSave.setVisibility(0);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                    }
                }
            } else if (bean.getStatus() == 1) {
                dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                if (D4Utils.checkD4ItemIsTimeout(this.d4Record.getSettings().getFactor(), this.d4DateFeedData.getDay(), bean, this.d4Record.getActualTimeZone())) {
                    dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                    dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                    dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                    dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.D3_record_cancel_passed_prompt, getd4ItemDataNameWithState(bean)));
                    dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                    dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                    dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
                } else {
                    if (this.dateDataList.get(i).isState()) {
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(0);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(0);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        if (this.dateDataList.get(i).getBean().getSrc() == 3) {
                            dateRecordViewHolder.dateRecordCheckbox.setVisibility(8);
                        } else {
                            dateRecordViewHolder.dateRecordCheckbox.setVisibility(0);
                        }
                    } else {
                        dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                        dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                    }
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(this.mContext.getResources().getString(R.string.D3_record_plan_prompt, getd4ItemDataNameWithState(bean)));
                    sb7.append(str);
                    sb7.append(this.mContext.getResources().getString(R.string.D3_record_amount_prompt, D4Utils.getAmountFormat(bean.getAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getAmount(), this.d4Record.getSettings().getFactor())));
                    sb7.append(str);
                    sb7.append(this.mContext.getResources().getString(R.string.Canceled));
                    dateRecordViewHolder.tvDateRecordAmount.setText(sb7.toString());
                    dateRecordViewHolder.tvDateRecordState.setText(this.mContext.getResources().getString(R.string.Canceled));
                    dateRecordViewHolder.dateRecordCheckbox.setChecked(false);
                    dateRecordViewHolder.ivDateRecordArrow.setVisibility(0);
                    dateRecordViewHolder.ivDateRecordArrow.setImageResource(!this.dateDataList.get(i).isState() ? R.drawable.d3_record_up : R.drawable.d3_record_down);
                    dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(onClickListener);
                    dateRecordViewHolder.rlRootPanel.setOnClickListener(onClickListener);
                    if (this.dateDataList.get(i).isState()) {
                        if (bean.getIsExecuted() == 1) {
                            dateRecordViewHolder.tvDateRecordSave.setVisibility(8);
                            dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(0);
                            dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                        } else {
                            dateRecordViewHolder.tvDateRecordSave.setVisibility(0);
                            dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(0);
                            dateRecordViewHolder.llDateRecordPointMost.setVisibility(0);
                        }
                    }
                }
            } else if (bean.getStatus() == 2) {
                dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                dateRecordViewHolder.tvDateRecordAmount.setText(this.mContext.getResources().getString(R.string.Feeder_item_not_start_prompt));
                dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
            } else if (bean.getStatus() == 3) {
                dateRecordViewHolder.swipeMenuLayout.setSwipeEnable(false);
                dateRecordViewHolder.rlDateRecordPanel.setVisibility(8);
                dateRecordViewHolder.llDateRecordPointAdditional.setVisibility(8);
                dateRecordViewHolder.llDateRecordPointMost.setVisibility(8);
                StringBuilder sb8 = new StringBuilder();
                sb8.append(this.mContext.getResources().getString(R.string.D3_record_plan_prompt, getd4ItemDataNameWithState(bean)));
                sb8.append(str);
                sb8.append(this.mContext.getResources().getString(R.string.D3_record_amount_prompt, D4Utils.getAmountFormat(bean.getAmount(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, bean.getAmount(), this.d4Record.getSettings().getFactor())));
                sb8.append(str);
                sb8.append(this.mContext.getResources().getString(R.string.D3_record_feeding_prompt));
                dateRecordViewHolder.tvDateRecordAmount.setText(sb8.toString());
                dateRecordViewHolder.ivDateRecordArrow.setVisibility(8);
                dateRecordViewHolder.tvDateRecordAmount.setOnClickListener(null);
                dateRecordViewHolder.rlRootPanel.setOnClickListener(null);
            }
            dateRecordViewHolder.dateRecordCheckbox.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$0(baseRecordViewHolder, bean, view);
                }
            });
            if (i == this.dateDataList.size() - 1) {
                dateRecordViewHolder.llDateRecordPointDefault.setVisibility(8);
                dateRecordViewHolder.dividerView.setVisibility(8);
            } else {
                dateRecordViewHolder.llDateRecordPointDefault.setVisibility(8);
                dateRecordViewHolder.dividerView.setVisibility(0);
            }
            baseRecordViewHolder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.3
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    baseRecordViewHolder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int measuredHeight = baseRecordViewHolder.itemView.getMeasuredHeight();
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((DateRecordViewHolder) baseRecordViewHolder).dividerView.getLayoutParams();
                    layoutParams.height = Math.max(ArmsUtils.dip2px(D4HomeRecordAdapter.this.mContext, 32.0f), measuredHeight - ArmsUtils.dip2px(D4HomeRecordAdapter.this.mContext, 39.0f));
                    ((DateRecordViewHolder) baseRecordViewHolder).dividerView.setLayoutParams(layoutParams);
                }
            });
            return;
        }
        if (baseRecordViewHolder instanceof WeekOrMonthRecordViewHolder) {
            WeekOrMonthRecordViewHolder weekOrMonthRecordViewHolder = (WeekOrMonthRecordViewHolder) baseRecordViewHolder;
            weekOrMonthRecordViewHolder.ivWeekOrMonthRecordState.setImageResource(R.drawable.d4_state_complete);
            ViewGroup.LayoutParams layoutParams = weekOrMonthRecordViewHolder.tvWeekOrMonthRecordTime.getLayoutParams();
            if (this.isMonth) {
                weekOrMonthRecordViewHolder.tvWeekOrMonthRecordTime.setText(getMonthStatisticTime(String.valueOf(this.weekOrMonthDataList.get(i).getStart()), String.valueOf(this.weekOrMonthDataList.get(i).getEnd())));
                String d4AmountTimesUnit = D4Utils.getD4AmountTimesUnit(this.mContext, this.weekOrMonthDataList.get(i).getTimes());
                TextView textView2 = weekOrMonthRecordViewHolder.tvWeekOrMonthRecordName;
                StringBuilder sb9 = new StringBuilder();
                sb9.append(this.mContext.getResources().getString(R.string.D3_record_month_prompt, this.weekOrMonthDataList.get(i).getTimes() + d4AmountTimesUnit));
                sb9.append(str);
                sb9.append(this.mContext.getResources().getString(R.string.D3_record_month_prompt, D4Utils.getAmountFormat(this.weekOrMonthDataList.get(i).getFeed(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, this.weekOrMonthDataList.get(i).getFeed(), this.d4Record.getSettings().getFactor())));
                textView2.setText(sb9.toString());
                layoutParams.width = ArmsUtils.dip2px(this.mContext, 100.0f);
            } else {
                weekOrMonthRecordViewHolder.tvWeekOrMonthRecordTime.setText(getWeekStatisticTime(String.valueOf(this.weekOrMonthDataList.get(i).getStart())));
                String d4AmountTimesUnit2 = D4Utils.getD4AmountTimesUnit(this.mContext, this.weekOrMonthDataList.get(i).getTimes());
                TextView textView3 = weekOrMonthRecordViewHolder.tvWeekOrMonthRecordName;
                StringBuilder sb10 = new StringBuilder();
                sb10.append(this.mContext.getResources().getString(R.string.D3_record_month_prompt, this.weekOrMonthDataList.get(i).getTimes() + d4AmountTimesUnit2));
                sb10.append(str);
                sb10.append(this.mContext.getResources().getString(R.string.D3_record_month_prompt, D4Utils.getAmountFormat(this.weekOrMonthDataList.get(i).getFeed(), this.d4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(this.mContext, this.weekOrMonthDataList.get(i).getFeed(), this.d4Record.getSettings().getFactor())));
                textView3.setText(sb10.toString());
                layoutParams.width = ArmsUtils.dip2px(this.mContext, 50.0f);
            }
            weekOrMonthRecordViewHolder.tvWeekOrMonthRecordTime.setTextColor(this.mContext.getResources().getColor(R.color.d3_record_time_black));
            weekOrMonthRecordViewHolder.tvWeekOrMonthRecordTime.setLayoutParams(layoutParams);
            if (i == this.weekOrMonthDataList.size() - 1) {
                weekOrMonthRecordViewHolder.llWeekOrMonthRecordPointDefault.setVisibility(8);
            } else {
                weekOrMonthRecordViewHolder.llWeekOrMonthRecordPointDefault.setVisibility(0);
            }
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$0(BaseRecordViewHolder baseRecordViewHolder, D4DailyFeeds.D4DailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordViewHolder) baseRecordViewHolder).dateRecordCheckbox.isChecked()) {
            restoreDailyFeed(itemsBean);
        } else {
            removeDailyFeed(itemsBean);
        }
    }

    public final void setDividerViewHeight(final BaseRecordViewHolder baseRecordViewHolder, boolean z) {
        if (z) {
            DateRecordViewHolder dateRecordViewHolder = (DateRecordViewHolder) baseRecordViewHolder;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) dateRecordViewHolder.dividerView.getLayoutParams();
            layoutParams.height = ArmsUtils.dip2px(this.mContext, 32.0f);
            dateRecordViewHolder.dividerView.setLayoutParams(layoutParams);
        }
        baseRecordViewHolder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                baseRecordViewHolder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int measuredHeight = baseRecordViewHolder.itemView.getMeasuredHeight();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) ((DateRecordViewHolder) baseRecordViewHolder).dividerView.getLayoutParams();
                layoutParams2.height = Math.max(ArmsUtils.dip2px(D4HomeRecordAdapter.this.mContext, 32.0f), measuredHeight - ArmsUtils.dip2px(D4HomeRecordAdapter.this.mContext, 39.0f));
                ((DateRecordViewHolder) baseRecordViewHolder).dividerView.setLayoutParams(layoutParams2);
            }
        });
    }

    public final void restoreDailyFeed(final D4DailyFeeds.D4DailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d4DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().restoreD4DailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.5
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(0);
                D4HomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D4HomeRecordAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public final void removeDailyFeed(final D4DailyFeeds.D4DailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d4DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().removeD4DailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.6
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(1);
                D4HomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D4HomeRecordAdapter.this.notifyDataSetChanged();
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

    public final String getd4ItemDataNameWithState(D4DailyFeeds.D4DailyFeed.ItemsBean itemsBean) {
        int src = itemsBean.getSrc();
        if (src == 2 || src == 3) {
            return this.mContext.getString(R.string.Feeder_add_manual_online);
        }
        if (src == 4) {
            return this.mContext.getString(R.string.Feeder_add_manual_offline);
        }
        return itemsBean.getName();
    }

    public final int getD4ItemDataIconByState(D4DailyFeeds.D4DailyFeed.ItemsBean itemsBean) {
        if (D4Utils.checkD4ItemIsTimeout(this.d4Record.getSettings().getFactor(), this.d4DateFeedData.getDay(), itemsBean, this.d4Record.getActualTimeZone())) {
            if (itemsBean.getState() == null) {
                return R.drawable.d4_state_failed;
            }
            if (itemsBean.getStatus() == 1 || itemsBean.getStatus() == 2) {
                return R.drawable.d4_state_failed;
            }
            if (itemsBean.getState().getResult() == 0) {
                if (itemsBean.getState().getErrCode() == 8) {
                    return R.drawable.d4_state_failed;
                }
                return R.drawable.d4_state_complete;
            }
            return R.drawable.d4_state_failed;
        }
        if (itemsBean.getStatus() == 3) {
            return R.drawable.d4_state_eating;
        }
        if (itemsBean.getStatus() == 0) {
            return R.drawable.d4_state_wait;
        }
        return R.drawable.d4_state_canceled;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ArrayList<D4DayItem> arrayList = this.dateDataList;
        if (arrayList != null && arrayList.size() > 0) {
            return 1;
        }
        List<FeedStatistic.ListBean> list = this.weekOrMonthDataList;
        return (list == null || list.size() <= 0) ? -1 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<D4DayItem> arrayList = this.dateDataList;
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
            this.dividerView = (DividerView) view.findViewById(R.id.divider_view);
            this.swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipe_menu_layout);
            this.tvDel = (TextView) view.findViewById(R.id.tv_del);
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

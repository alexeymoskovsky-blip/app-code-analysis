package com.petkit.android.activities.petkitBleDevice.d4sh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.PetkitToast;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.petkit.android.activities.feed.mode.RefreshFeedDataEvent;
import com.petkit.android.activities.petkitBleDevice.PetWeightActivity;
import com.petkit.android.activities.petkitBleDevice.d4.mode.FeedStatistic;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4shDateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDayItem;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVideoRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shDeviceRecordView;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D4shHomeRecordAdapter extends D4shAbstractHomeRecordAdapter {
    public int bindDataTimes;
    public D4shDateFeedData d4DateFeedData;
    public D4shRecord d4shRecord;
    public ArrayList<D4shDayItem> dateDataList;
    public long deviceId;
    public int filterRecordType;
    public boolean hideCatPop;
    public boolean isMonth;
    public boolean isShowCatPop;
    public RecordOnClickListener listener;
    public Context mContext;
    public boolean serviceInvalid;
    public long startBindDataTime;
    public View view;
    public List<FeedStatistic.ListBean> weekOrMonthDataList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_6);
    public ArrayList<D4shVideoRecord> recordList = new ArrayList<>();
    public int d4shDeviceRecordTopVideoInScreenIndex = -1;

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
        this.d4DateFeedData = d4shDateFeedData;
        this.weekOrMonthDataList = null;
        this.dateDataList = new ArrayList<>();
        int i = this.filterRecordType;
        if ((i == 0 || i == 4) && d4shDateFeedData != null) {
            for (D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean : d4shDateFeedData.getD4sDailyFeeds()) {
                this.dateDataList.add(new D4shDayItem(1, itemsBean.getCompletedAt() == null ? itemsBean.getTime() : TimeUtils.getInstance().timeStampToSeconds(itemsBean.getCompletedAt().longValue(), this.d4shRecord.getActualTimeZone()), itemsBean, true, this.d4shRecord.getActualTimeZone()));
            }
        }
        int i2 = this.filterRecordType;
        if ((i2 == 0 || i2 == 2) && d4shDateFeedData != null) {
            for (D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean2 : d4shDateFeedData.getD4sDailyEats()) {
                this.dateDataList.add(new D4shDayItem(2, itemsBean2.getStartTime(), itemsBean2, false, this.d4shRecord.getActualTimeZone()));
            }
        }
        int i3 = this.filterRecordType;
        if ((i3 == 0 || i3 == 1) && d4shDateFeedData != null) {
            for (D4shDailyFeeds.PetBean.ItemsBean itemsBean3 : d4shDateFeedData.getD4shDeviceRecordPetTypeDataList()) {
                this.dateDataList.add(new D4shDayItem(3, (int) itemsBean3.getTimestamp(), itemsBean3, false, this.d4shRecord.getActualTimeZone()));
            }
        }
        int i4 = this.filterRecordType;
        if ((i4 == 0 || i4 == 3) && d4shDateFeedData != null) {
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

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void setHideCatPop(boolean z) {
        this.hideCatPop = z;
        this.isShowCatPop = false;
        notifyDataSetChanged();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public boolean isShowingCatPop() {
        return this.isShowCatPop;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public List<D4shVideoRecord> getRecordList() {
        SpannableString petTitleInfo;
        String str;
        String string;
        int i = 1;
        this.recordList = new ArrayList<>();
        char c = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.dateDataList.size()) {
            if (this.dateDataList.get(i2).getType() == i) {
                D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyFeed.ItemsBean) this.dateDataList.get(i2).getBean();
                int time = itemsBean.getCompletedAt() == null ? itemsBean.getTime() : TimeUtils.getInstance().timeStampToSeconds(itemsBean.getCompletedAt().longValue(), this.d4shRecord.getActualTimeZone());
                if (itemsBean.getStatus() == 0) {
                    if (itemsBean.getState() != null) {
                        if (itemsBean.getState().getResult() == i || itemsBean.getState().getResult() == 2) {
                            Resources resources = this.mContext.getResources();
                            int i4 = R.string.D3_record_feed_prompt;
                            Object[] objArr = new Object[i];
                            objArr[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources.getString(i4, objArr);
                        } else if (itemsBean.getState().getResult() == 4) {
                            Resources resources2 = this.mContext.getResources();
                            int i5 = R.string.D3_record_feed_prompt;
                            Object[] objArr2 = new Object[i];
                            objArr2[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources2.getString(i5, objArr2);
                        } else if (itemsBean.getState().getResult() == 5) {
                            Resources resources3 = this.mContext.getResources();
                            int i6 = R.string.D3_record_feed_prompt;
                            Object[] objArr3 = new Object[i];
                            objArr3[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources3.getString(i6, objArr3);
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() <= 0) {
                                Resources resources4 = this.mContext.getResources();
                                int i7 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr4 = new Object[i];
                                objArr4[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources4.getString(i7, objArr4);
                            }
                        } else if (itemsBean.getState().getResult() == 6) {
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() > 0) {
                                Resources resources5 = this.mContext.getResources();
                                int i8 = R.string.D3_record_feed_prompt;
                                Object[] objArr5 = new Object[i];
                                objArr5[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources5.getString(i8, objArr5);
                            } else {
                                Resources resources6 = this.mContext.getResources();
                                int i9 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr6 = new Object[i];
                                objArr6[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources6.getString(i9, objArr6);
                            }
                        } else if (itemsBean.getState().getResult() == 11) {
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() > 0) {
                                Resources resources7 = this.mContext.getResources();
                                int i10 = R.string.D3_record_feed_prompt;
                                Object[] objArr7 = new Object[i];
                                objArr7[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources7.getString(i10, objArr7);
                            } else {
                                Resources resources8 = this.mContext.getResources();
                                int i11 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr8 = new Object[i];
                                objArr8[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources8.getString(i11, objArr8);
                            }
                        } else if (itemsBean.getState().getResult() == 9) {
                            Resources resources9 = this.mContext.getResources();
                            int i12 = R.string.D3_record_feed_prompt;
                            Object[] objArr9 = new Object[i];
                            objArr9[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources9.getString(i12, objArr9);
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() <= 0) {
                                Resources resources10 = this.mContext.getResources();
                                int i13 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr10 = new Object[i];
                                objArr10[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources10.getString(i13, objArr10);
                            }
                        } else if (itemsBean.getState().getResult() == 10) {
                            Resources resources11 = this.mContext.getResources();
                            int i14 = R.string.D3_record_feed_prompt;
                            Object[] objArr11 = new Object[i];
                            objArr11[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources11.getString(i14, objArr11);
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() <= 0) {
                                Resources resources12 = this.mContext.getResources();
                                int i15 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr12 = new Object[i];
                                objArr12[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources12.getString(i15, objArr12);
                            }
                        } else if (itemsBean.getState().getResult() == 12) {
                            Resources resources13 = this.mContext.getResources();
                            int i16 = R.string.D3_record_feed_prompt;
                            Object[] objArr13 = new Object[i];
                            objArr13[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources13.getString(i16, objArr13);
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() <= 0) {
                                Resources resources14 = this.mContext.getResources();
                                int i17 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr14 = new Object[i];
                                objArr14[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources14.getString(i17, objArr14);
                            }
                        } else if (itemsBean.getState().getResult() == 13) {
                            Resources resources15 = this.mContext.getResources();
                            int i18 = R.string.D3_record_feed_prompt;
                            Object[] objArr15 = new Object[i];
                            objArr15[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources15.getString(i18, objArr15);
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() <= 0) {
                                Resources resources16 = this.mContext.getResources();
                                int i19 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr16 = new Object[i];
                                objArr16[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources16.getString(i19, objArr16);
                            }
                        } else if (itemsBean.getState().getResult() == 7) {
                            Resources resources17 = this.mContext.getResources();
                            int i20 = R.string.D4sh_device_record_feed_cancel;
                            Object[] objArr17 = new Object[i];
                            objArr17[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources17.getString(i20, objArr17);
                        } else if (itemsBean.getState().getResult() == 3) {
                            Resources resources18 = this.mContext.getResources();
                            int i21 = R.string.D4sh_device_record_feed_failure;
                            Object[] objArr18 = new Object[i];
                            objArr18[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources18.getString(i21, objArr18);
                        } else if (itemsBean.getState().getResult() == 8) {
                            Resources resources19 = this.mContext.getResources();
                            int i22 = R.string.D4sh_Stop_Out_Food;
                            Object[] objArr19 = new Object[i];
                            objArr19[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources19.getString(i22, objArr19);
                        } else if (itemsBean.getState().getErrCode() == 8) {
                            if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() > 0) {
                                Resources resources20 = this.mContext.getResources();
                                int i23 = R.string.D3_record_feed_prompt;
                                Object[] objArr20 = new Object[i];
                                objArr20[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources20.getString(i23, objArr20);
                            } else {
                                Resources resources21 = this.mContext.getResources();
                                int i24 = R.string.D4sh_device_record_feed_failure;
                                Object[] objArr21 = new Object[i];
                                objArr21[c] = getd4ItemDataNameWithState(itemsBean);
                                string = resources21.getString(i24, objArr21);
                            }
                        } else if (Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount1(), this.d4shRecord.getSettings().getFactor1())).intValue() + Integer.valueOf(D4shUtils.getAmountFormat(itemsBean.getState().getRealAmount2(), this.d4shRecord.getSettings().getFactor2())).intValue() > 0) {
                            Resources resources22 = this.mContext.getResources();
                            int i25 = R.string.D3_record_feed_prompt;
                            Object[] objArr22 = new Object[i];
                            objArr22[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources22.getString(i25, objArr22);
                        } else {
                            Resources resources23 = this.mContext.getResources();
                            int i26 = R.string.D4sh_device_record_feed_failure;
                            Object[] objArr23 = new Object[i];
                            objArr23[c] = getd4ItemDataNameWithState(itemsBean);
                            string = resources23.getString(i26, objArr23);
                        }
                    } else {
                        Resources resources24 = this.mContext.getResources();
                        int i27 = R.string.D3_record_plan_prompt;
                        Object[] objArr24 = new Object[i];
                        objArr24[c] = getd4ItemDataNameWithState(itemsBean);
                        string = resources24.getString(i27, objArr24);
                    }
                    str = string;
                } else {
                    if (itemsBean.getStatus() != i && itemsBean.getStatus() != 2) {
                        itemsBean.getStatus();
                    }
                    str = "";
                }
                this.recordList.add(D4shVideoRecord.createByEatRecord(itemsBean.getIsNeedUploadVideo(), this.deviceId, itemsBean.getPreview(), itemsBean.getAesKey(), itemsBean.getMediaApi(), Integer.valueOf(itemsBean.getTime()), (Integer) (-1), Long.valueOf(itemsBean.getStartTime()), itemsBean.getEventId(), i3, str, TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, time), 2, itemsBean.getExpire(), itemsBean.getDuration(), itemsBean.getStorageSpace(), -1L, itemsBean.getMark()));
            } else {
                if (this.dateDataList.get(i2).getType() == 2) {
                    new SpannableString("");
                    D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean2 = (D4shDailyFeeds.D4shDailyEat.ItemsBean) this.dateDataList.get(i2).getBean();
                    int iTimeStampToSeconds = TimeUtils.getInstance().timeStampToSeconds(itemsBean2.getEatStartTime(), this.d4shRecord.getActualTimeZone());
                    i3 = R.drawable.d4sh_state_eating;
                    int iTimeStampToSeconds2 = TimeUtils.getInstance().timeStampToSeconds(itemsBean2.getEatStartTime(), this.d4shRecord.getActualTimeZone());
                    int iTimeStampToSeconds3 = TimeUtils.getInstance().timeStampToSeconds(itemsBean2.getEatEndTime(), this.d4shRecord.getActualTimeZone());
                    if (itemsBean2.getEmpty() == 1) {
                        petTitleInfo = getPetTitleInfo(this.dateDataList.get(i2));
                        if (itemsBean2.getDesc() != null) {
                            new SpannableString(itemsBean2.getDesc());
                        }
                    } else {
                        SpannableString petTitleInfo2 = getPetTitleInfo(this.dateDataList.get(i2));
                        if (itemsBean2.getEatEndTime() > 0) {
                            new SpannableString(this.mContext.getResources().getString(R.string.D3_record_time_prompt, D4sUtils.calDurationTime(this.mContext, iTimeStampToSeconds3, iTimeStampToSeconds2)));
                        }
                        petTitleInfo = petTitleInfo2;
                    }
                    this.recordList.add(D4shVideoRecord.createByEatRecord(itemsBean2.getPetId(), itemsBean2.getIsNeedUploadVideo(), this.deviceId, itemsBean2.getPreview(), itemsBean2.getAesKey(), itemsBean2.getMediaApi(), Integer.valueOf(itemsBean2.getStartTime()), Integer.valueOf(itemsBean2.getEndTime()), Long.valueOf(itemsBean2.getEatStartTime()), itemsBean2.getEventId(), i3, petTitleInfo.toString(), TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, iTimeStampToSeconds), 1, itemsBean2.getExpire(), itemsBean2.getDuration(), itemsBean2.getStorageSpace(), itemsBean2.getEatEndTime(), itemsBean2.getMark()));
                    if (itemsBean2.getPreview1() != null && itemsBean2.getPreview2() != null && itemsBean2.getEmpty() != 1) {
                        D4shDailyFeeds.CompareBean.ItemsBeanX itemsBeanX = new D4shDailyFeeds.CompareBean.ItemsBeanX();
                        itemsBeanX.setEventId(itemsBean2.getEventId());
                        itemsBeanX.setAesKey1(itemsBean2.getAesKey1());
                        itemsBeanX.setAesKey2(itemsBean2.getAesKey2());
                        itemsBeanX.setPreview1(itemsBean2.getPreview1());
                        itemsBeanX.setPreview2(itemsBean2.getPreview2());
                        itemsBeanX.setExpire1(itemsBean2.getExpire1());
                        itemsBeanX.setExpire2(itemsBean2.getExpire2());
                        itemsBeanX.setEndTime(itemsBean2.getEndTime());
                        itemsBeanX.setStartTime(itemsBean2.getStartTime());
                        itemsBeanX.setEatStartTime(itemsBean2.getEatStartTime());
                        itemsBeanX.setEatEndTime(itemsBean2.getEatEndTime());
                        this.recordList.add(D4shVideoRecord.createByEatCompareRecord(this.deviceId, itemsBeanX, 0, 5));
                    }
                } else if (this.dateDataList.get(i2).getType() == 3) {
                    new SpannableString("");
                    D4shDailyFeeds.PetBean.ItemsBean itemsBean3 = (D4shDailyFeeds.PetBean.ItemsBean) this.dateDataList.get(i2).getBean();
                    int iTimeStampToSeconds4 = TimeUtils.getInstance().timeStampToSeconds(itemsBean3.getTimestamp(), this.d4shRecord.getActualTimeZone());
                    i3 = R.drawable.d4sh_state_pet;
                    SpannableString petTitleInfo3 = getPetTitleInfo(this.dateDataList.get(i2));
                    if (itemsBean3.getDesc() != null) {
                        new SpannableString(itemsBean3.getDesc());
                    }
                    this.recordList.add(D4shVideoRecord.createByEatRecord(itemsBean3.getPetId(), itemsBean3.getIsNeedUploadVideo(), this.deviceId, itemsBean3.getPreview(), itemsBean3.getAesKey(), itemsBean3.getMediaApi(), Integer.valueOf((int) itemsBean3.getTimestamp()), (Integer) (-1), Long.valueOf(itemsBean3.getTimestamp()), itemsBean3.getEventId(), i3, petTitleInfo3.toString(), TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, iTimeStampToSeconds4), 3, itemsBean3.getExpire(), itemsBean3.getDuration(), itemsBean3.getStorageSpace(), -1L, itemsBean3.getMark()));
                } else if (this.dateDataList.get(i2).getType() == 4) {
                    new SpannableString("");
                    D4shDailyFeeds.MoveBean.ItemsBeanX itemsBeanX2 = (D4shDailyFeeds.MoveBean.ItemsBeanX) this.dateDataList.get(i2).getBean();
                    int iTimeStampToSeconds5 = TimeUtils.getInstance().timeStampToSeconds(itemsBeanX2.getTimestamp(), this.d4shRecord.getActualTimeZone());
                    i3 = R.drawable.d4sh_state_activity;
                    this.recordList.add(D4shVideoRecord.createByEatRecord(itemsBeanX2.getIsNeedUploadVideo(), this.deviceId, itemsBeanX2.getPreview(), itemsBeanX2.getAesKey(), itemsBeanX2.getMediaApi(), Integer.valueOf((int) itemsBeanX2.getTimestamp()), (Integer) (-1), Long.valueOf(itemsBeanX2.getTimestamp()), itemsBeanX2.getEventId(), i3, getPetTitleInfo(this.dateDataList.get(i2)).toString(), TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, iTimeStampToSeconds5), 3, itemsBeanX2.getExpire(), itemsBeanX2.getDuration(), itemsBeanX2.getStorageSpace(), -1L, itemsBeanX2.getMark()));
                }
                i = 1;
                i2++;
                c = 0;
            }
            i = 1;
            i2++;
            c = 0;
        }
        return this.recordList;
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

    public D4shHomeRecordAdapter(Context context, long j) {
        this.mContext = context;
        this.deviceId = j;
    }

    public D4shHomeRecordAdapter(Context context, long j, int i, int i2, RecordOnClickListener recordOnClickListener) {
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

    /* JADX WARN: Removed duplicated region for block: B:118:0x0b07  */
    /* JADX WARN: Removed duplicated region for block: B:594:0x2764  */
    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder r78, @android.annotation.SuppressLint({"RecyclerView"}) final int r79) {
        /*
            Method dump skipped, instruction units count: 13505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.d4sh.adapter.BaseRecordViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(final BaseRecordViewHolder baseRecordViewHolder, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordViewHolder) baseRecordViewHolder).d4shDeviceRecordView.isDeviceRecordPlanCheckBoxCheck()) {
            if (this.d4shRecord.getState().getPim() == 2) {
                Context context = this.mContext;
                NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Battery_mode_is_being_used), (String) null);
                newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.4
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
        if (this.d4shRecord.getState().getPim() == 2) {
            Context context2 = this.mContext;
            NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(context2, (String) null, context2.getResources().getString(R.string.Battery_mode_is_being_used), (String) null);
            newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.5
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(D4shVideoRecord d4shVideoRecord, View view) {
        RecordOnClickListener recordOnClickListener = this.listener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onItemClick(d4shVideoRecord);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$2(final BaseRecordViewHolder baseRecordViewHolder, D4shRecord d4shRecord, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.isDeviceRecordPlanCheckBoxCheck()) {
            if (d4shRecord.getState().getPim() == 2) {
                if (d4shRecord.getState().getConservationStatus() == 1) {
                    Context context = this.mContext;
                    NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                    newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.12
                        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                        public void onClickIKnow() {
                            ((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.FALSE);
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
        if (d4shRecord.getState().getPim() == 2) {
            if (d4shRecord.getState().getConservationStatus() == 1) {
                Context context2 = this.mContext;
                NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(context2, (String) null, context2.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.13
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

    public final /* synthetic */ void lambda$onBindViewHolder$3(final BaseRecordViewHolder baseRecordViewHolder, D4shRecord d4shRecord, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.isDeviceRecordPlanCheckBoxCheck()) {
            if (d4shRecord.getState().getPim() == 2) {
                if (d4shRecord.getState().getConservationStatus() == 1) {
                    Context context = this.mContext;
                    NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                    newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.14
                        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                        public void onClickIKnow() {
                            ((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.FALSE);
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
        if (d4shRecord.getState().getPim() == 2) {
            if (d4shRecord.getState().getConservationStatus() == 1) {
                Context context2 = this.mContext;
                NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(context2, (String) null, context2.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.15
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

    public final /* synthetic */ void lambda$onBindViewHolder$4(final BaseRecordViewHolder baseRecordViewHolder, D4shRecord d4shRecord, D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean, View view) {
        if (((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.isDeviceRecordPlanCheckBoxCheck()) {
            if (d4shRecord.getState().getPim() == 2) {
                if (d4shRecord.getState().getConservationStatus() == 1) {
                    Context context = this.mContext;
                    NewIKnowWindow newIKnowWindow = new NewIKnowWindow(context, (String) null, context.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                    newIKnowWindow.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.16
                        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                        public void onClickIKnow() {
                            ((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.FALSE);
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
        if (d4shRecord.getState().getPim() == 2) {
            if (d4shRecord.getState().getConservationStatus() == 1) {
                Context context2 = this.mContext;
                NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(context2, (String) null, context2.getResources().getString(R.string.Deep_energy_saving_is_running_tip), (String) null);
                newIKnowWindow2.setOnClickIKnowListener(new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.17
                    @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                    public void onClickIKnow() {
                        ((DateRecordWithDeleteViewHolder) baseRecordViewHolder).d4shDeviceRecordView.setDeviceRecordPlanCheckBoxCheck(Boolean.TRUE);
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
    public /* synthetic */ void lambda$onBindViewHolder$5(D4shVideoRecord d4shVideoRecord, View view) {
        RecordOnClickListener recordOnClickListener = this.listener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onItemClick(d4shVideoRecord);
        }
    }

    private boolean hasPet(D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean) {
        String name;
        Pet petById = UserInforUtils.getPetById(itemsBean.getPetId());
        if (petById == null) {
            name = itemsBean.getPetName();
        } else {
            name = petById.getName();
        }
        return !TextUtils.isEmpty(name);
    }

    private SpannableString getPetTitleInfo(D4shDayItem d4shDayItem) {
        String petName;
        String string;
        Pet petById;
        String name;
        if (d4shDayItem.getType() == 2) {
            D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyEat.ItemsBean) d4shDayItem.getBean();
            Pet petById2 = UserInforUtils.getPetById(itemsBean.getPetId());
            if (petById2 == null) {
                name = itemsBean.getPetName();
            } else {
                name = petById2.getName();
            }
            if (!TextUtils.isEmpty(name)) {
                String string2 = this.mContext.getResources().getString(R.string.D4sh3_device_record_pet_eat, name);
                int color = Color.parseColor(ColorUtils.getPetColorById(itemsBean.getPetId(), name));
                SpannableString spannableString = new SpannableString(string2);
                spannableString.setSpan(new ForegroundColorSpan(color), string2.indexOf(name), string2.indexOf(name) + name.length(), 33);
                return spannableString;
            }
            return new SpannableString(this.mContext.getResources().getString(R.string.D4SH_Bow_Empty));
        }
        if (d4shDayItem.getType() == 3) {
            D4shDailyFeeds.PetBean.ItemsBean itemsBean2 = (D4shDailyFeeds.PetBean.ItemsBean) d4shDayItem.getBean();
            if (itemsBean2.getContent() == null || (petById = UserInforUtils.getPetById(itemsBean2.getContent().getPetId())) == null) {
                petName = itemsBean2.getPetName();
            } else {
                petName = petById.getName();
            }
            int count = itemsBean2.getContent() != null ? itemsBean2.getContent().getCount() : 0;
            if (TextUtils.isEmpty(petName) || itemsBean2.getContent() == null) {
                if (count > 1) {
                    return new SpannableString(this.mContext.getResources().getString(R.string.Cat_appeared_multi));
                }
                return new SpannableString(this.mContext.getResources().getString(R.string.Cat_appeared));
            }
            if (count > 1) {
                string = this.mContext.getResources().getString(R.string.Appeared_multi, petName);
            } else {
                string = this.mContext.getResources().getString(R.string.Appeared, petName);
            }
            int color2 = Color.parseColor(ColorUtils.getPetColorById(itemsBean2.getContent().getPetId(), petName));
            SpannableString spannableString2 = new SpannableString(string);
            spannableString2.setSpan(new ForegroundColorSpan(color2), string.indexOf(petName), string.indexOf(petName) + petName.length(), 33);
            return spannableString2;
        }
        if (d4shDayItem.getType() == 4) {
            return new SpannableString(this.mContext.getResources().getString(R.string.Movement_detected));
        }
        return new SpannableString("");
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter
    public void removeItem(int i) {
        if (this.dateDataList.size() > i) {
            this.dateDataList.remove(i);
            notifyDataSetChanged();
        }
    }

    private void restoreDailyFeed(final D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d4DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().restoreD4shDailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.27
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(0);
                D4shHomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D4shHomeRecordAdapter.this.notifyDataSetChanged();
            }
        });
    }

    private void removeDailyFeed(final D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put("day", String.valueOf(this.d4DateFeedData.getDay()));
        map.put("id", String.valueOf(itemsBean.getId()));
        WebModelRepository.getInstance().removeD4shDailyFeed((BaseActivity) this.mContext, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.28
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                itemsBean.setStatus(1);
                D4shHomeRecordAdapter.this.notifyDataSetChanged();
                if (TextUtils.isEmpty(itemsBean.getName())) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PetkitLog.d("restoreDailyFeed error:" + errorInfor.getMsg());
                PetkitToast.showToast(errorInfor.getMsg());
                D4shHomeRecordAdapter.this.notifyDataSetChanged();
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
                if (itemsBean.getStatus() == 0 && itemsBean.getState() == null && !D4shUtils.checkD4ItemIsTimeout(this.d4DateFeedData.getDay(), itemsBean, this.d4shRecord.getActualTimeZone())) {
                    return 3;
                }
                itemsBean.getStatus();
                return 3;
            }
            if (this.dateDataList.get(i).getType() == 2 && ((D4shDailyFeeds.D4shDailyEat.ItemsBean) this.dateDataList.get(i).getBean()).getEndTime() >= 0 && this.d4shRecord.getDeviceShared() == null) {
                return 3;
            }
            if (this.dateDataList.get(i).getType() == 3 && this.d4shRecord.getDeviceShared() == null) {
                return 3;
            }
            return (!(this.dateDataList.get(i).getType() == 4 && this.d4shRecord.getDeviceShared() == null) && this.dateDataList.get(i).getType() == 5) ? 6 : 3;
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

    public final SpannableString getDeviceRecordDescSpannableString(int i, int i2, int i3, int i4) {
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
                        } else {
                            d4shDeviceRecordView.showVideoTv(Boolean.TRUE);
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
                    } else {
                        d4shDeviceRecordView.showVideoTv(Boolean.TRUE);
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
                } else {
                    d4shDeviceRecordView.showVideoTv(Boolean.TRUE);
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
            } else {
                d4shDeviceRecordView.showVideoTv(Boolean.TRUE);
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
    @SuppressLint({"NotifyDataSetChanged"})
    public void updateOnePet(String str, String str2, String str3, int i) {
        Iterator<D4shDayItem> it = this.dateDataList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            D4shDayItem next = it.next();
            if (next.getType() == i && i == 2) {
                D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyEat.ItemsBean) next.getBean();
                if (itemsBean.getEventId().equals(str3)) {
                    if (str.equals(itemsBean.getPetId())) {
                        itemsBean.setPetId(str2);
                    } else {
                        itemsBean.setPetId(str2);
                    }
                }
            } else if (next.getType() == i && i == 3) {
                D4shDailyFeeds.PetBean.ItemsBean itemsBean2 = (D4shDailyFeeds.PetBean.ItemsBean) next.getBean();
                if (itemsBean2.getContent() != null && itemsBean2.getEventId().equals(str3)) {
                    itemsBean2.getContent().setPetId(str2);
                    break;
                } else if (itemsBean2.getContent() == null && itemsBean2.getEventId().equals(str3)) {
                    itemsBean2.setContent(new D4shDailyFeeds.PetBean.ItemsBean.Content(str2, "", 1));
                }
            }
        }
        notifyDataSetChanged();
    }

    public void showPetIdentifyPop(D4shRecord d4shRecord, final D4shDeviceRecordView d4shDeviceRecordView, D4shDayItem d4shDayItem) {
        if (d4shRecord == null || d4shRecord.getDeviceShared() == null) {
            if (d4shRecord == null || d4shRecord.getModelCode() == 2) {
                if (d4shDayItem.getType() == 2) {
                    if (UserInforUtils.getPetById(((D4shDailyFeeds.D4shDailyEat.ItemsBean) d4shDayItem.getBean()).getPetId()) != null) {
                        return;
                    }
                } else if (d4shDayItem.getType() != 3 || UserInforUtils.getPetById(((D4shDailyFeeds.PetBean.ItemsBean) d4shDayItem.getBean()).getContent().getPetId()) != null) {
                    return;
                }
                if (FamilyUtils.getInstance().getCurrentFamilyIsUploadCatAndDogPhoto(this.mContext)) {
                    if (DataHelper.getBooleanSF(this.mContext, "CAT_FACE_ALREADY_UPLOAD_POP" + FamilyUtils.getInstance().getCurrentFamilyInfo(this.mContext).getGroupId())) {
                        return;
                    }
                    d4shDeviceRecordView.llCatFace.setVisibility(0);
                    this.isShowCatPop = true;
                    d4shDeviceRecordView.tvCatFace.setText(this.mContext.getString(R.string.Guide_tips_not_matched));
                    DataHelper.setBooleanSF(this.mContext, "CAT_FACE_ALREADY_UPLOAD_POP" + FamilyUtils.getInstance().getCurrentFamilyInfo(this.mContext).getGroupId(), Boolean.TRUE);
                    return;
                }
                boolean booleanSF = DataHelper.getBooleanSF(this.mContext, Constants.D4SH_CAT_FACE_NOT_UPLOAD_POP + FamilyUtils.getInstance().getCurrentFamilyInfo(this.mContext).getGroupId());
                long sysLongMap = CommonUtils.getSysLongMap(this.mContext, "D4SH_CAT_FACE_NOT_UPLOAD_POP_TIME_" + FamilyUtils.getInstance().getCurrentFamilyInfo(this.mContext).getGroupId());
                boolean z = System.currentTimeMillis() - sysLongMap > 86400000;
                if (booleanSF || sysLongMap <= 0 || !z) {
                    return;
                }
                d4shDeviceRecordView.llCatFace.setVisibility(0);
                this.isShowCatPop = true;
                String string = this.mContext.getString(R.string.Upload_face_photo);
                String str = this.mContext.getString(R.string.Guide_tips_not_upload_face_photo) + " ";
                SpannableString spannableString = new SpannableString(str);
                int length = str.length();
                spannableString.setSpan(new ImageSpan(this.mContext, R.drawable.icon_arrow_right_blue, 0), length - 1, length, 18);
                int iIndexOf = str.indexOf(string);
                if (iIndexOf != -1) {
                    spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter.29
                        @Override // android.text.style.ClickableSpan
                        public void onClick(@NonNull View view) {
                            d4shDeviceRecordView.llCatFace.setVisibility(8);
                            D4shHomeRecordAdapter.this.hideCatPop = true;
                            D4shHomeRecordAdapter.this.isShowCatPop = false;
                            D4shHomeRecordAdapter.this.mContext.startActivity(PetWeightActivity.newIntent(D4shHomeRecordAdapter.this.mContext, D4shHomeRecordAdapter.this.deviceId, 26));
                        }

                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public void updateDrawState(@NonNull TextPaint textPaint) {
                            super.updateDrawState(textPaint);
                            textPaint.setUnderlineText(false);
                            textPaint.setColor(D4shHomeRecordAdapter.this.mContext.getResources().getColor(R.color.new_bind_blue));
                        }
                    }, iIndexOf, string.length() + iIndexOf, 17);
                }
                d4shDeviceRecordView.tvCatFace.setText(spannableString);
                d4shDeviceRecordView.tvCatFace.setMovementMethod(LinkMovementMethod.getInstance());
                d4shDeviceRecordView.tvCatFace.setHighlightColor(this.mContext.getResources().getColor(R.color.transparent));
                DataHelper.setBooleanSF(this.mContext, Constants.D4SH_CAT_FACE_NOT_UPLOAD_POP + FamilyUtils.getInstance().getCurrentFamilyInfo(this.mContext).getGroupId(), Boolean.TRUE);
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
        public SwipeMenuLayout swipeMenuLayout;
        public TextView tvDel;

        public DateRecordWithDeleteViewHolder(View view) {
            super(view);
            this.d4shDeviceRecordView = (D4shDeviceRecordView) view.findViewById(R.id.d4sh_device_record_view);
            this.swipeMenuLayout = (SwipeMenuLayout) view.findViewById(R.id.swipe);
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

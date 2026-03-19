package com.petkit.android.activities.petkitBleDevice.t7.adp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.petkitBleDevice.hg.widget.NewRoundImageview;
import com.petkit.android.activities.petkitBleDevice.t6.mode.ShitInfo;
import com.petkit.android.activities.petkitBleDevice.t6.widget.CircleRelativeLayout;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MySwipeMenuLayout;
import com.petkit.android.activities.petkitBleDevice.t7.T7HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t7.T7LightAssistActivity;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7ContentInfo;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7EventClearInfo;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7EventInfo;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7AnimUtil;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7ConstUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes5.dex */
public class T7EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public boolean addFootView;
    public final T7HomeActivity context;
    public EventListListener eventListListener;
    public List<T7EventInfo> list;
    public boolean loadMore;
    public long selectDayTime;
    public final T7AnimUtil t7AnimUtil;
    public final T7Record t7Record;
    public final int tabType;
    public TimeZone timezone;
    public final int FootType = 1;
    public int lastPlayPosition = -1;

    public interface EventListListener {
        void deleteEvent(T7EventInfo t7EventInfo, int i);

        void lookShit(T7EventInfo t7EventInfo, int i, int i2);

        void startPlayVideo(T7EventInfo t7EventInfo, int i);
    }

    public void setEventListListener(EventListListener eventListListener) {
        this.eventListListener = eventListListener;
    }

    public void setLoadMore(boolean z) {
        this.loadMore = z;
    }

    public void addFootView(boolean z) {
        this.addFootView = z;
    }

    public T7EventAdapter(T7HomeActivity t7HomeActivity, List<T7EventInfo> list, T7Record t7Record, int i) {
        this.context = t7HomeActivity;
        this.t7Record = t7Record;
        this.list = list;
        this.tabType = i;
        if (t7Record != null) {
            this.timezone = t7Record.getActualTimeZone();
        }
        this.t7AnimUtil = new T7AnimUtil();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (this.addFootView && i == getItemCount() - 1) ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.addFootView ? this.list.size() + 1 : this.list.size();
    }

    public void setSelectDayTime(long j) {
        this.selectDayTime = j;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new FootHolder(LayoutInflater.from(this.context).inflate(R.layout.t6_item_foot_view, viewGroup, false));
        }
        return new T7Holder(LayoutInflater.from(this.context).inflate(R.layout.t7_item_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (this.addFootView && i == getItemCount() - 1) {
            initFootHolder((FootHolder) viewHolder);
        } else {
            initT7Holder((T7Holder) viewHolder, i);
        }
    }

    public void setLastPlayPosition(int i) {
        this.lastPlayPosition = i;
    }

    public int getLastPlayPosition() {
        return this.lastPlayPosition;
    }

    public final void initFootHolder(FootHolder footHolder) {
        if (this.loadMore) {
            footHolder.tvNoDatDa.setVisibility(8);
            footHolder.ivLoadMore.setVisibility(0);
            this.t7AnimUtil.startRotateAnim(footHolder.ivLoadMore);
        } else {
            footHolder.ivLoadMore.setVisibility(8);
            footHolder.tvNoDatDa.setVisibility(0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0239  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initT7Holder(com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter.T7Holder r20, int r21) {
        /*
            Method dump skipped, instruction units count: 1210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter.initT7Holder(com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$T7Holder, int):void");
    }

    public final void showTimelineTime(T7EventInfo t7EventInfo, T7Holder t7Holder) {
        String strTimeStampToTimeStringWithUnit;
        long startTime = 0;
        if (t7EventInfo.getEventType() == 10) {
            if (t7EventInfo.getContent() != null) {
                startTime = t7EventInfo.getContent().getTimeIn();
            }
        } else if (t7EventInfo.getContent() != null) {
            if (t7EventInfo.getEventType() == 7 && t7EventInfo.getContent().getResult() != null && t7EventInfo.getContent().getResult().intValue() == 5) {
                startTime = t7EventInfo.getTimestamp();
            } else {
                startTime = t7EventInfo.getContent().getStartTime();
            }
        }
        if (TimeUtils.getInstance().is24HourSystem()) {
            t7Holder.tvEventTimeAm.setVisibility(8);
            strTimeStampToTimeStringWithUnit = TimeUtils.getInstance().timeStampToTimeStringWithUnit(this.context, startTime, this.timezone);
        } else {
            t7Holder.tvEventTimeAm.setVisibility(0);
            String[] t6EventTime = TimeUtils.getInstance().getT6EventTime(this.context, startTime, this.timezone);
            String str = t6EventTime[0];
            t7Holder.tvEventTimeAm.setText(t6EventTime[1]);
            strTimeStampToTimeStringWithUnit = str;
        }
        t7Holder.tvEventTime.setText(strTimeStampToTimeStringWithUnit);
    }

    public final void specialClear(T7Holder t7Holder, T7EventInfo t7EventInfo) {
        if (t7EventInfo.getEventType() == 10 && t7EventInfo.getContent() != null) {
            t7Holder.tvCancelClearTime.setText(TimeUtils.getInstance().timeStampToTimeStringWithUnit(this.context, t7EventInfo.getContent().getTimeIn(), this.timezone));
            if (t7EventInfo.getContent().getAutoClear() == 2) {
                t7Holder.llCancelClear.setVisibility(0);
                t7Holder.tvCancelClear.setText(this.context.getResources().getString(R.string.T3_history_record_pet_into_interval_prompt_text));
                return;
            }
            if (t7EventInfo.getContent().getAutoClear() == 5) {
                t7Holder.llCancelClear.setVisibility(0);
                t7Holder.tvCancelClear.setText(this.context.getResources().getString(R.string.T4_history_record_pet_into_disturb_prompt_text));
                return;
            } else if (t7EventInfo.getContent().getAutoClear() == 7) {
                t7Holder.llCancelClear.setVisibility(0);
                t7Holder.tvCancelClear.setText(String.format("%s %s", "", this.context.getResources().getString(R.string.Toilet_record_underweight_not_clean_up)));
                return;
            } else if (t7EventInfo.getContent().getAutoClear() == 8) {
                t7Holder.llCancelClear.setVisibility(0);
                t7Holder.tvCancelClear.setText(String.format("%s %s", "", this.context.getResources().getString(R.string.T3_record_auto_clean_cancel_kitten_protection)));
                return;
            } else {
                t7Holder.llCancelClear.setVisibility(8);
                return;
            }
        }
        t7Holder.llCancelClear.setVisibility(8);
    }

    public final void relativeClear(final T7Holder t7Holder, T7EventInfo t7EventInfo, final T7EventClearInfo t7EventClearInfo, int i) {
        if (t7EventInfo.getEventType() == 10 && !t7EventInfo.getSubContent().isEmpty() && t7EventClearInfo != null) {
            t7Holder.llRelativeClear.setVisibility(0);
            t7Holder.tvRelativeClearTime.setText(TimeUtils.getInstance().getToiletTime(this.context, this.selectDayTime, t7EventClearInfo.getContent() == null ? 0L : t7EventClearInfo.getContent().getStartTime(), this.timezone));
            t7Holder.tvRelativeClear.setText(T7Utils.getClearContent(this.context, t7EventClearInfo.getContent()));
            t7Holder.tvLookShit.setVisibility((!t7EventClearInfo.getShitPictures().isEmpty() || showToiletShitPic(t7EventInfo)) ? 0 : 8);
            if (t7EventClearInfo.getContent() != null && t7EventClearInfo.getContent().getResult() != null && t7EventClearInfo.getContent().getResult().intValue() != 0) {
                t7Holder.rlClearPic.setVisibility(8);
                t7Holder.ivClearPlay.setVisibility(8);
                t7Holder.tvClearNoData.setVisibility(8);
                return;
            }
            if (t7EventClearInfo.getMedia() == 0 && TextUtils.isEmpty(t7EventClearInfo.getPreview()) && TextUtils.isEmpty(t7EventClearInfo.getMediaApi())) {
                t7Holder.rlClearPic.setVisibility(8);
                t7Holder.ivClearPlay.setVisibility(8);
                t7Holder.tvClearNoData.setVisibility(0);
                t7Holder.tvClearNoData.setText(this.context.getResources().getString(R.string.Camera_off_no_feedsvideo));
                return;
            }
            if (t7EventClearInfo.getUpload() == 0 && TextUtils.isEmpty(t7EventClearInfo.getPreview()) && TextUtils.isEmpty(t7EventClearInfo.getMediaApi())) {
                t7Holder.rlClearPic.setVisibility(8);
                t7Holder.ivClearPlay.setVisibility(8);
                t7Holder.tvClearNoData.setVisibility(0);
                t7Holder.tvClearNoData.setText(this.context.getResources().getString(R.string.T6_event_video_close_title));
                return;
            }
            if (t7EventClearInfo.getContent() != null && t7EventClearInfo.getContent().getVideo() == 1) {
                t7Holder.rlClearPic.setVisibility(8);
                t7Holder.ivClearPlay.setVisibility(8);
                t7Holder.tvClearNoData.setVisibility(0);
                if (t7EventClearInfo.getContent().getLightAssist() == 0) {
                    t7Holder.tvClearNoData.setText(this.context.getResources().getString(R.string.T7_event_no_video_lighting_assist_close_title));
                    return;
                } else if (t7EventClearInfo.getContent().getLightAssist() == 1 && t7EventClearInfo.getContent().getLightAssistInRange() == 0) {
                    t7Holder.tvClearNoData.setText(this.context.getResources().getString(R.string.T7_event_no_video_lighting_assist_close_title));
                    return;
                } else {
                    t7Holder.tvClearNoData.setText(this.context.getResources().getString(R.string.No_video));
                    return;
                }
            }
            if (TextUtils.isEmpty(t7EventClearInfo.getPreview()) && TextUtils.isEmpty(t7EventClearInfo.getMediaApi())) {
                t7Holder.rlClearPic.setVisibility(8);
                t7Holder.ivClearPlay.setVisibility(8);
                t7Holder.tvClearNoData.setVisibility(0);
                t7Holder.tvClearNoData.setText(this.context.getResources().getString(R.string.No_video));
                return;
            }
            if (System.currentTimeMillis() / 1000 > t7EventClearInfo.getExpire()) {
                t7Holder.rlClearPic.setVisibility(8);
                t7Holder.ivClearPlay.setVisibility(8);
                t7Holder.tvClearNoData.setVisibility(0);
                t7Holder.tvClearNoData.setText(this.context.getResources().getString(R.string.Image_expired));
                return;
            }
            if (isExpireService(i)) {
                t7Holder.ivClearPlay.setVisibility(0);
            } else {
                t7Holder.ivClearPlay.setVisibility(8);
            }
            t7Holder.rlClearPic.setVisibility(0);
            t7Holder.tvClearNoData.setVisibility(8);
            new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(t7EventClearInfo.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda12
                @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                public final void onCachePath(String str) {
                    T7EventAdapter.lambda$relativeClear$0(t7EventClearInfo, t7Holder, str);
                }
            });
            return;
        }
        t7Holder.llRelativeClear.setVisibility(8);
        t7Holder.rlClearPic.setVisibility(8);
        t7Holder.ivClearPlay.setVisibility(8);
        t7Holder.tvClearNoData.setVisibility(8);
        t7Holder.tvLookShit.setVisibility(8);
    }

    public static /* synthetic */ void lambda$relativeClear$0(T7EventClearInfo t7EventClearInfo, T7Holder t7Holder, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), t7EventClearInfo.getAesKey());
                if (fileDecryptImageFile != null) {
                    t7Holder.ivClearPic.setImageBitmap(CommonUtil.bimapSquareRound(100, T7Utils.getRotateBitmap(fileDecryptImageFile.getAbsolutePath(), t7EventClearInfo.getPreview())));
                } else {
                    t7Holder.ivClearPic.setImageResource(R.drawable.petkit_default_image);
                }
                return;
            } catch (Exception unused) {
                t7Holder.ivClearPic.setImageResource(R.drawable.petkit_default_image);
                return;
            }
        }
        t7Holder.ivClearPic.setImageResource(R.drawable.petkit_default_image);
    }

    public final void relativeOdor(T7Holder t7Holder, T7EventInfo t7EventInfo, T7EventClearInfo t7EventClearInfo) {
        T7EventClearInfo infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 8);
        if (t7EventInfo.getEventType() != 10 || t7EventInfo.getSubContent().isEmpty() || infoByEventType == null) {
            t7Holder.llOdorBottom.setVisibility(8);
            t7Holder.llOdorTop.setVisibility(8);
            return;
        }
        if (t7EventClearInfo != null && t7EventClearInfo.getContent().getStartTime() <= infoByEventType.getContent().getStartTime()) {
            t7Holder.llOdorBottom.setVisibility(0);
            t7Holder.llOdorTop.setVisibility(8);
            t7Holder.tvBottomOdorTime.setText(TimeUtils.getInstance().getToiletTime(this.context, this.selectDayTime, infoByEventType.getContent() != null ? infoByEventType.getContent().getStartTime() : 0L, this.timezone));
            t7Holder.tvBottomOdor.setText(T7Utils.getSprayContent(this.context, infoByEventType.getContent().getResult(), infoByEventType.getContent().getStartReason()));
            return;
        }
        t7Holder.llOdorBottom.setVisibility(8);
        t7Holder.llOdorTop.setVisibility(0);
        t7Holder.tvTopOdorTime.setText(TimeUtils.getInstance().getToiletTime(this.context, this.selectDayTime, infoByEventType.getContent() != null ? infoByEventType.getContent().getStartTime() : 0L, this.timezone));
        t7Holder.tvTopOdor.setText(T7Utils.getSprayContent(this.context, infoByEventType.getContent().getResult(), infoByEventType.getContent().getStartReason()));
    }

    public final void showCommonUi(final T7Holder t7Holder, final T7EventInfo t7EventInfo, int i) {
        if (t7EventInfo.getEventType() == 5 && t7EventInfo.getContent() != null && t7EventInfo.getContent().getStartReason() != 0) {
            t7Holder.rlPlay.setVisibility(8);
            t7Holder.tvNoVideo.setVisibility(8);
        } else if (t7EventInfo.getMedia() == 0 && TextUtils.isEmpty(t7EventInfo.getPreview()) && TextUtils.isEmpty(t7EventInfo.getMediaApi())) {
            t7Holder.rlPlay.setVisibility(8);
            t7Holder.tvNoVideo.setVisibility(0);
            t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.Camera_off_no_feedsvideo));
        } else if (t7EventInfo.getToiletDetection() == 0 && t7EventInfo.getEventType() == 10 && TextUtils.isEmpty(t7EventInfo.getPreview()) && TextUtils.isEmpty(t7EventInfo.getMediaApi())) {
            t7Holder.rlPlay.setVisibility(8);
            t7Holder.tvNoVideo.setVisibility(0);
            t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.T6_toiletDetection_close));
        } else if (t7EventInfo.getUpload() == 0 && TextUtils.isEmpty(t7EventInfo.getPreview()) && TextUtils.isEmpty(t7EventInfo.getMediaApi())) {
            t7Holder.rlPlay.setVisibility(8);
            t7Holder.tvNoVideo.setVisibility(0);
            t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.T6_event_video_close_title));
        } else if (t7EventInfo.getContent() != null && t7EventInfo.getContent().getVideo() == 1) {
            t7Holder.rlPlay.setVisibility(8);
            t7Holder.tvNoVideo.setVisibility(0);
            if (t7EventInfo.getEventType() == 10 && t7EventInfo.getContent().getToiletLightAssist() == 0) {
                t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.T7_event_no_video_lighting_assist_close_title));
            } else if (t7EventInfo.getEventType() == 10 && t7EventInfo.getContent().getToiletLightAssist() == 1 && t7EventInfo.getContent().getToiletLightAssistInRange() == 0) {
                t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.T7_event_no_video_lighting_assist_close_title));
            } else if (t7EventInfo.getEventType() == 5 && t7EventInfo.getContent().getLightAssist() == 0) {
                t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.T7_event_no_video_lighting_assist_close_title));
            } else if (t7EventInfo.getEventType() == 5 && t7EventInfo.getContent().getLightAssist() == 1 && t7EventInfo.getContent().getLightAssistInRange() == 0) {
                t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.T7_event_no_video_lighting_assist_close_title));
            } else {
                t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.No_video));
            }
        } else if (TextUtils.isEmpty(t7EventInfo.getPreview()) && TextUtils.isEmpty(t7EventInfo.getMediaApi())) {
            t7Holder.rlPlay.setVisibility(8);
            t7Holder.tvNoVideo.setVisibility(0);
            t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.No_video));
        } else if (System.currentTimeMillis() / 1000 > t7EventInfo.getExpire()) {
            t7Holder.rlPlay.setVisibility(8);
            t7Holder.tvNoVideo.setVisibility(0);
            t7Holder.tvNoVideo.setText(this.context.getResources().getString(R.string.Image_expired));
        } else {
            t7Holder.tvNoVideo.setVisibility(8);
            t7Holder.rlPlay.setVisibility(0);
            new MyImageTask(this.context) { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter.1
                @Override // com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter.MyImageTask
                public void getFilePath(String str) {
                    if (str != null) {
                        File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), t7EventInfo.getAesKey());
                        if (fileDecryptImageFile != null) {
                            t7Holder.ivEventPic.setImageBitmap(T7Utils.getRotateBitmap(fileDecryptImageFile.getAbsolutePath(), t7EventInfo.getPreview()));
                            return;
                        } else {
                            t7Holder.ivEventPic.setImageResource(R.drawable.t7_default_image);
                            return;
                        }
                    }
                    t7Holder.ivEventPic.setImageResource(R.drawable.t7_default_image);
                }
            }.execute(CommonUtil.httpToHttps(t7EventInfo.getPreview()));
        }
        if (!isExpireService(i) || TextUtils.isEmpty(t7EventInfo.getMediaApi())) {
            t7Holder.ivTopPlay.setVisibility(8);
        } else {
            t7Holder.ivTopPlay.setVisibility(0);
        }
        if (TextUtils.isEmpty(t7EventInfo.getMediaApi())) {
            t7Holder.flParent.setRotation(0.0f);
            return;
        }
        if (t7EventInfo.getMediaApi().contains("RA090")) {
            t7Holder.flParent.setRotation(90.0f);
            return;
        }
        if (t7EventInfo.getMediaApi().contains("RA180")) {
            t7Holder.flParent.setRotation(180.0f);
        } else if (t7EventInfo.getMediaApi().contains("RA270")) {
            t7Holder.flParent.setRotation(-90.0f);
        } else {
            t7Holder.flParent.setRotation(0.0f);
        }
    }

    public final void initListener(final T7Holder t7Holder, final T7EventInfo t7EventInfo, final int i) {
        t7Holder.flParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$1(t7EventInfo, i, view);
            }
        });
        t7Holder.rlClearPic.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$2(t7EventInfo, i, view);
            }
        });
        t7Holder.tvDelete.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$3(t7Holder, t7EventInfo, i, view);
            }
        });
        t7Holder.tvTopLookShit.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$4(t7EventInfo, i, view);
            }
        });
        t7Holder.tvLookShit.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$5(t7EventInfo, i, view);
            }
        });
        t7Holder.llPh.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$6(t7EventInfo, i, view);
            }
        });
        t7Holder.llBlood.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$7(t7EventInfo, i, view);
            }
        });
        t7Holder.llShitError.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$8(t7EventInfo, i, view);
            }
        });
        t7Holder.llClearPh.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$9(t7EventInfo, i, view);
            }
        });
        t7Holder.llClearBlood.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$10(t7EventInfo, i, view);
            }
        });
        t7Holder.llShitClearError.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$11(t7EventInfo, i, view);
            }
        });
        t7Holder.llCatVoice.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$12(t7EventInfo, i, view);
            }
        });
    }

    public final /* synthetic */ void lambda$initListener$1(T7EventInfo t7EventInfo, int i, View view) {
        EventListListener eventListListener = this.eventListListener;
        if (eventListListener != null) {
            eventListListener.startPlayVideo(t7EventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initListener$2(T7EventInfo t7EventInfo, int i, View view) {
        T7EventClearInfo infoByEventType;
        if (t7EventInfo.getSubContent() == null || t7EventInfo.getSubContent().isEmpty() || this.eventListListener == null || (infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5)) == null) {
            return;
        }
        this.eventListListener.startPlayVideo(getInfoFromClearInfo(infoByEventType, t7EventInfo.getPetId(), null, t7EventInfo.getEventId()), i);
    }

    public final /* synthetic */ void lambda$initListener$3(T7Holder t7Holder, T7EventInfo t7EventInfo, int i, View view) {
        if (this.eventListListener != null) {
            t7Holder.swipeMenuLayout.smoothClose();
            this.eventListListener.deleteEvent(t7EventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initListener$4(T7EventInfo t7EventInfo, int i, View view) {
        if (this.eventListListener != null) {
            for (ShitInfo shitInfo : t7EventInfo.getShitPictures()) {
                shitInfo.setEventId(t7EventInfo.getEventId());
                shitInfo.setEventType(t7EventInfo.getEventType());
            }
            this.eventListListener.lookShit(t7EventInfo, i, t7EventInfo.getEventType() == 10 ? 1 : 0);
        }
    }

    public final /* synthetic */ void lambda$initListener$5(T7EventInfo t7EventInfo, int i, View view) {
        T7EventClearInfo infoByEventType;
        if (this.eventListListener == null || (infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5)) == null) {
            return;
        }
        if (!t7EventInfo.getShitPictures().isEmpty()) {
            infoByEventType.setExpire(t7EventInfo.getExpire());
        }
        if (infoByEventType.getContent() != null && t7EventInfo.getContent() != null && infoByEventType.getContent().getSoftErr() == 0) {
            infoByEventType.getContent().setSoftErr(t7EventInfo.getContent().getSoftErr());
        }
        infoByEventType.getContent().setSoftClear(t7EventInfo.getContent().getSoftClear());
        this.eventListListener.lookShit(getInfoFromClearInfo(infoByEventType, t7EventInfo.getPetId(), showToiletShitPic(t7EventInfo) ? t7EventInfo.getShitPictures() : null, t7EventInfo.getEventId()), i, 0);
    }

    public final /* synthetic */ void lambda$initListener$6(T7EventInfo t7EventInfo, int i, View view) {
        EventListListener eventListListener;
        if (t7EventInfo.getShitPictures().isEmpty() || System.currentTimeMillis() / 1000 > t7EventInfo.getExpire() || (eventListListener = this.eventListListener) == null) {
            return;
        }
        eventListListener.lookShit(t7EventInfo, i, 1);
    }

    public final /* synthetic */ void lambda$initListener$7(T7EventInfo t7EventInfo, int i, View view) {
        EventListListener eventListListener;
        if (t7EventInfo.getShitPictures().isEmpty() || System.currentTimeMillis() / 1000 > t7EventInfo.getExpire() || (eventListListener = this.eventListListener) == null) {
            return;
        }
        eventListListener.lookShit(t7EventInfo, i, 1);
    }

    public final /* synthetic */ void lambda$initListener$8(T7EventInfo t7EventInfo, int i, View view) {
        EventListListener eventListListener;
        T7EventClearInfo infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5);
        if (infoByEventType == null) {
            if (t7EventInfo.getShitPictures().isEmpty() || System.currentTimeMillis() / 1000 > t7EventInfo.getExpire() || (eventListListener = this.eventListListener) == null) {
                return;
            }
            eventListListener.lookShit(t7EventInfo, i, 1);
            return;
        }
        if (!(infoByEventType.getShitPictures().isEmpty() && t7EventInfo.getShitPictures().isEmpty()) && System.currentTimeMillis() / 1000 <= t7EventInfo.getExpire()) {
            if (infoByEventType.getContent() != null && t7EventInfo.getContent() != null && infoByEventType.getContent().getSoftErr() == 0) {
                infoByEventType.getContent().setSoftErr(t7EventInfo.getContent().getSoftErr());
            }
            this.eventListListener.lookShit(getInfoFromClearInfo(infoByEventType, t7EventInfo.getPetId(), showToiletShitPic(t7EventInfo) ? t7EventInfo.getShitPictures() : null, t7EventInfo.getEventId()), i, 0);
        }
    }

    public final /* synthetic */ void lambda$initListener$9(T7EventInfo t7EventInfo, int i, View view) {
        T7EventClearInfo infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5);
        if (infoByEventType == null || infoByEventType.getShitPictures().isEmpty() || System.currentTimeMillis() / 1000 > t7EventInfo.getExpire()) {
            return;
        }
        this.eventListListener.lookShit(getInfoFromClearInfo(infoByEventType, t7EventInfo.getPetId(), showToiletShitPic(t7EventInfo) ? t7EventInfo.getShitPictures() : null, t7EventInfo.getEventId()), i, 0);
    }

    public final /* synthetic */ void lambda$initListener$10(T7EventInfo t7EventInfo, int i, View view) {
        T7EventClearInfo infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5);
        if (infoByEventType == null || infoByEventType.getShitPictures().isEmpty() || System.currentTimeMillis() / 1000 > t7EventInfo.getExpire()) {
            return;
        }
        this.eventListListener.lookShit(getInfoFromClearInfo(infoByEventType, t7EventInfo.getPetId(), showToiletShitPic(t7EventInfo) ? t7EventInfo.getShitPictures() : null, t7EventInfo.getEventId()), i, 0);
    }

    public final /* synthetic */ void lambda$initListener$11(T7EventInfo t7EventInfo, int i, View view) {
        T7EventClearInfo infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5);
        if (infoByEventType == null) {
            return;
        }
        if (!(infoByEventType.getShitPictures().isEmpty() && t7EventInfo.getShitPictures().isEmpty()) && System.currentTimeMillis() / 1000 <= t7EventInfo.getExpire()) {
            this.eventListListener.lookShit(getInfoFromClearInfo(infoByEventType, t7EventInfo.getPetId(), showToiletShitPic(t7EventInfo) ? t7EventInfo.getShitPictures() : null, t7EventInfo.getEventId()), i, 0);
        }
    }

    public final /* synthetic */ void lambda$initListener$12(T7EventInfo t7EventInfo, int i, View view) {
        if (this.eventListListener != null) {
            if (!(TextUtils.isEmpty(t7EventInfo.getPreview()) && TextUtils.isEmpty(t7EventInfo.getMediaApi())) && System.currentTimeMillis() / 1000 <= t7EventInfo.getExpire()) {
                DataHelper.setBooleanSF(this.context, T7ConstUtils.T7_VIDEO_FULL, Boolean.TRUE);
                this.eventListListener.startPlayVideo(t7EventInfo, i);
            }
        }
    }

    public final void initSpecialView(T7Holder t7Holder) {
        t7Holder.llLightHelp.setVisibility(8);
        t7Holder.llRelateLightHelp.setVisibility(8);
        t7Holder.llCatVoice.setVisibility(8);
        t7Holder.llPh.setVisibility(8);
        t7Holder.llClearPh.setVisibility(8);
        t7Holder.llBlood.setVisibility(8);
        t7Holder.llClearBlood.setVisibility(8);
        t7Holder.llShitError.setVisibility(8);
        t7Holder.llShitClearError.setVisibility(8);
        t7Holder.tvShitLeftTop.setVisibility(8);
        t7Holder.tvShitLeftBottom.setVisibility(8);
        t7Holder.tvTopLookShit.setVisibility(8);
        t7Holder.tvLookShit.setVisibility(8);
    }

    public final boolean showToiletLightTips(T7EventInfo t7EventInfo) {
        if (t7EventInfo.getContent() == null || t7EventInfo.getMedia() == 0 || t7EventInfo.getToiletDetection() == 0 || t7EventInfo.getUpload() == 0 || t7EventInfo.getContent().getVideo() == 0) {
            return false;
        }
        return (t7EventInfo.getContent().getToiletLightAssist() == 1 && t7EventInfo.getContent().getToiletLightAssistInRange() == 0) || t7EventInfo.getContent().getToiletLightAssist() == 0;
    }

    public final boolean showCleanLightTips(T7EventInfo t7EventInfo) {
        if (t7EventInfo.getContent() == null || t7EventInfo.getMedia() == 0 || t7EventInfo.getUpload() == 0 || t7EventInfo.getContent().getResult() == null || t7EventInfo.getContent().getResult().intValue() != 0 || t7EventInfo.getContent().getVideo() == 0) {
            return false;
        }
        return (t7EventInfo.getContent().getLightAssist() == 1 && t7EventInfo.getContent().getLightAssistInRange() == 0) || t7EventInfo.getContent().getLightAssist() == 0;
    }

    public final boolean showRelativeCleanLightTips(T7EventClearInfo t7EventClearInfo) {
        if (t7EventClearInfo.getContent() == null || t7EventClearInfo.getMedia() == 0 || t7EventClearInfo.getUpload() == 0 || t7EventClearInfo.getContent().getResult() == null || t7EventClearInfo.getContent().getResult().intValue() != 0 || t7EventClearInfo.getContent().getVideo() == 0) {
            return false;
        }
        return (t7EventClearInfo.getContent().getLightAssist() == 1 && t7EventClearInfo.getContent().getLightAssistInRange() == 0) || t7EventClearInfo.getContent().getLightAssist() == 0;
    }

    public final void setHelpText(TextView textView) {
        String str = this.context.getString(R.string.Light_assist_settings) + ">";
        String string = this.context.getString(R.string.T7_open_light_assist_tips, str);
        int iIndexOf = string.indexOf(str);
        int length = str.length() + iIndexOf;
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7EventAdapter.2
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(T7EventAdapter.this.context.getResources().getColor(R.color.new_bind_blue));
                textPaint.setUnderlineText(false);
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(@NonNull View view) {
                T7EventAdapter.this.context.startActivity(T7LightAssistActivity.newIntent(T7EventAdapter.this.context, T7EventAdapter.this.t7Record.getDeviceId()));
            }
        }, iIndexOf, length, 17);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
    }

    public final void initType5(T7Holder t7Holder, T7EventInfo t7EventInfo) {
        boolean z;
        t7Holder.ivEventLogo.setVisibility(8);
        t7Holder.ivEventIcon.setVisibility(0);
        t7Holder.tvCatName.setText(T7Utils.getClearContent(this.context, t7EventInfo.getContent()));
        if (t7EventInfo.getContent().getResult() != null && t7EventInfo.getContent().getResult().intValue() == 0) {
            t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
            z = !t7EventInfo.getShitPictures().isEmpty();
        } else {
            t7Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
            z = false;
        }
        t7Holder.tvTopLookShit.setVisibility(z ? 0 : 8);
    }

    public final void initType6(T7Holder t7Holder, T7EventInfo t7EventInfo) {
        t7Holder.ivEventLogo.setVisibility(8);
        t7Holder.ivEventIcon.setVisibility(0);
        T7ContentInfo content = t7EventInfo.getContent();
        if (content == null) {
            return;
        }
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else {
            t7Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        }
        t7Holder.tvCatName.setText(T7Utils.getCatLitterContent(this.context, content.getResult(), content.getErr(), content.getStartReason()));
        t7Holder.rlPlay.setVisibility(8);
        t7Holder.tvNoVideo.setVisibility(8);
    }

    public final void initType7(T7Holder t7Holder, T7EventInfo t7EventInfo) {
        t7Holder.ivEventLogo.setVisibility(8);
        t7Holder.ivEventIcon.setVisibility(0);
        T7ContentInfo content = t7EventInfo.getContent();
        if (content == null) {
            return;
        }
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else if (content.getResult().intValue() != 5 || !TextUtils.isEmpty(content.getErr())) {
            t7Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        } else {
            t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        }
        t7Holder.tvCatName.setText(T7Utils.getResetContent(this.context, content.getResult(), content.getErr(), content.getModeTime()));
        t7Holder.rlPlay.setVisibility(8);
        t7Holder.tvNoVideo.setVisibility(8);
    }

    public final void initType12(T7Holder t7Holder, T7EventInfo t7EventInfo) {
        t7Holder.ivEventLogo.setVisibility(8);
        t7Holder.ivEventIcon.setVisibility(0);
        T7ContentInfo content = t7EventInfo.getContent();
        if (content == null) {
            return;
        }
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else if (content.getResult().intValue() != 5 || !TextUtils.isEmpty(content.getErr())) {
            t7Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        } else {
            t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        }
        t7Holder.tvCatName.setText(T7Utils.getSmoothContent(this.context, content.getResult(), content.getErr()));
        t7Holder.rlPlay.setVisibility(8);
        t7Holder.tvNoVideo.setVisibility(8);
    }

    public final void initType8(T7Holder t7Holder, T7EventInfo t7EventInfo) {
        t7Holder.ivEventLogo.setVisibility(8);
        t7Holder.ivEventIcon.setVisibility(0);
        T7ContentInfo content = t7EventInfo.getContent();
        if (content == null) {
            return;
        }
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else {
            t7Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        }
        t7Holder.tvCatName.setText(T7Utils.getSprayContent(this.context, content.getResult(), content.getStartReason()));
        t7Holder.rlPlay.setVisibility(8);
        t7Holder.tvNoVideo.setVisibility(8);
    }

    public final void initType10(T7Holder t7Holder, T7EventInfo t7EventInfo, T7EventClearInfo t7EventClearInfo) {
        t7Holder.ivEventLogo.setVisibility(0);
        t7Holder.ivEventLogo.setImageResource(R.drawable.icon_t7_pet_out);
        t7Holder.ivEventIcon.setVisibility(8);
        t7Holder.tvCatName.setText(T7Utils.getPetWeight(this.context, t7EventInfo));
        boolean zShowToiletShitPic = showToiletShitPic(t7EventInfo);
        if (!t7EventInfo.getSubContent().isEmpty() && t7EventClearInfo != null) {
            t7Holder.tvTopLookShit.setVisibility(8);
        } else {
            t7Holder.tvTopLookShit.setVisibility(zShowToiletShitPic ? 0 : 8);
        }
    }

    public final void initType20(T7Holder t7Holder, T7EventInfo t7EventInfo) {
        t7Holder.ivEventLogo.setVisibility(0);
        t7Holder.ivEventLogo.setImageResource(R.drawable.icon_t6_pet_in);
        t7Holder.ivEventIcon.setVisibility(8);
        t7Holder.tvCatName.setText(this.context.getResources().getString(R.string.Cat_appeared));
    }

    public final boolean isExpireService(long j) {
        if (this.t7Record == null) {
            return false;
        }
        return T7DataUtils.getInstance().isExpireService(this.t7Record.getDeviceId(), j);
    }

    private boolean showToiletShitPic(T7EventInfo t7EventInfo) {
        if (t7EventInfo.getContent() == null) {
            return false;
        }
        if (getInfoByEventType(t7EventInfo.getSubContent(), 5) != null) {
            if (t7EventInfo.getShitPictures() == null || t7EventInfo.getShitPictures().isEmpty()) {
                return false;
            }
        } else if ((t7EventInfo.getContent().getPetPh() <= 0 && t7EventInfo.getContent().getSoftClear() != 1) || t7EventInfo.getShitPictures() == null || t7EventInfo.getShitPictures().isEmpty()) {
            return false;
        }
        return true;
    }

    public final boolean showClearShitPic(T7EventClearInfo t7EventClearInfo) {
        return !t7EventClearInfo.getShitPictures().isEmpty();
    }

    public final T7EventClearInfo getInfoByEventType(List<T7EventClearInfo> list, int i) {
        for (T7EventClearInfo t7EventClearInfo : list) {
            if (t7EventClearInfo.getEventType() == i) {
                return t7EventClearInfo;
            }
        }
        return null;
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setList(List<T7EventInfo> list, int i, int i2) {
        if (i < 1 || this.tabType != i2) {
            return;
        }
        if (i == 1) {
            this.list.clear();
            this.list.addAll(list);
        } else if (!hasEvent(list)) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void clearList() {
        this.list = new ArrayList();
        notifyDataSetChanged();
    }

    private boolean hasEvent(List<T7EventInfo> list) {
        if (list.isEmpty()) {
            return false;
        }
        T7EventInfo t7EventInfo = list.get(0);
        Iterator<T7EventInfo> it = this.list.iterator();
        while (it.hasNext()) {
            if (t7EventInfo.getEventId().equals(it.next().getEventId())) {
                return true;
            }
        }
        return false;
    }

    public void updateUploadVideo(String str) {
        if (str == null) {
            return;
        }
        for (T7EventInfo t7EventInfo : this.list) {
            if (str.equals(t7EventInfo.getEventId())) {
                t7EventInfo.setIsNeedUploadVideo(0);
                return;
            }
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void deleteShit(String str, int i, int i2, String str2) {
        List<T7EventInfo> list = this.list;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T7EventInfo t7EventInfo : this.list) {
            if (i2 == 5) {
                if (t7EventInfo.getEventType() == 10) {
                    T7EventClearInfo infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5);
                    if (!t7EventInfo.getSubContent().isEmpty() && infoByEventType != null && infoByEventType.getEventId().equals(str)) {
                        if (i == 1) {
                            infoByEventType.setShitPictures(deleteShitPic(infoByEventType.getShitPictures(), str2));
                        } else {
                            infoByEventType.setPreview("");
                        }
                        notifyDataSetChanged();
                        return;
                    }
                } else if (t7EventInfo.getEventType() == 5) {
                    if (t7EventInfo.getEventId().equals(str)) {
                        if (i == 1) {
                            t7EventInfo.setShitPictures(deleteShitPic(t7EventInfo.getShitPictures(), str2));
                        } else {
                            t7EventInfo.setPreview("");
                        }
                        notifyDataSetChanged();
                        return;
                    }
                } else if (t7EventInfo.getEventId().equals(str)) {
                    t7EventInfo.setPreview("");
                    notifyDataSetChanged();
                    return;
                }
            } else if (t7EventInfo.getEventType() == 10) {
                if (t7EventInfo.getEventId().equals(str)) {
                    if (i == 1) {
                        t7EventInfo.setShitPictures(deleteShitPic(t7EventInfo.getShitPictures(), str2));
                    } else {
                        t7EventInfo.setPreview("");
                    }
                    notifyDataSetChanged();
                    return;
                }
            } else if (t7EventInfo.getEventId().equals(str)) {
                t7EventInfo.setPreview("");
                notifyDataSetChanged();
                return;
            }
        }
    }

    private List<ShitInfo> deleteShitPic(List<ShitInfo> list, String str) {
        Iterator<ShitInfo> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ShitInfo next = it.next();
            if (next.getPicId().equals(str)) {
                list.remove(next);
                break;
            }
        }
        return list;
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public int deleteEvent(String str, int i) {
        List<T7EventInfo> list = this.list;
        if (list == null || list.isEmpty()) {
            return 0;
        }
        for (T7EventInfo t7EventInfo : this.list) {
            if (i == 5) {
                if (t7EventInfo.getEventType() == 10) {
                    T7EventClearInfo infoByEventType = getInfoByEventType(t7EventInfo.getSubContent(), 5);
                    if (!t7EventInfo.getSubContent().isEmpty() && infoByEventType != null && infoByEventType.getEventId().equals(str)) {
                        t7EventInfo.setSubContent(new ArrayList());
                        notifyDataSetChanged();
                        return this.list.size();
                    }
                } else if (t7EventInfo.getEventId().equals(str)) {
                    this.list.remove(t7EventInfo);
                    notifyDataSetChanged();
                    return this.list.size();
                }
            } else if (t7EventInfo.getEventId().equals(str)) {
                this.list.remove(t7EventInfo);
                notifyDataSetChanged();
                return this.list.size();
            }
        }
        return this.list.size();
    }

    public T7EventInfo getInfo(int i) {
        if (i < 0) {
            return null;
        }
        return this.list.get(i);
    }

    public List<T7EventInfo> getInfoList() {
        return this.list;
    }

    public void updateFeedback(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (T7EventInfo t7EventInfo : this.list) {
            if (t7EventInfo.getEventId().equals(str)) {
                t7EventInfo.setIsNeedUploadVideo(0);
            }
        }
    }

    public static class T7Holder extends RecyclerView.ViewHolder {
        public FrameLayout flParent;
        public NewRoundImageview ivClearPic;
        public ImageView ivClearPlay;
        public ImageView ivEventIcon;
        public ImageView ivEventLogo;
        public ImageView ivEventPic;
        public ImageView ivTopPlay;
        public LinearLayout llBlood;
        public LinearLayout llCancelClear;
        public LinearLayout llCatVoice;
        public LinearLayout llClearBlood;
        public LinearLayout llClearPh;
        public LinearLayout llLightHelp;
        public LinearLayout llOdorBottom;
        public LinearLayout llOdorTop;
        public LinearLayout llPh;
        public LinearLayout llRelateLightHelp;
        public LinearLayout llRelativeClear;
        public LinearLayout llShitClearError;
        public LinearLayout llShitError;
        public int mPosition;
        public RelativeLayout rlClearPic;
        public CircleRelativeLayout rlPlay;
        public MySwipeMenuLayout swipeMenuLayout;
        public TextView tvBlood;
        public TextView tvBottomOdor;
        public TextView tvBottomOdorTime;
        public TextView tvCancelClear;
        public TextView tvCancelClearTime;
        public TextView tvCatName;
        public TextView tvCatVoice;
        public TextView tvClearBlood;
        public TextView tvClearNoData;
        public TextView tvClearPh;
        public TextView tvDelete;
        public TextView tvEventTime;
        public TextView tvEventTimeAm;
        public TextView tvLightHelp;
        public TextView tvLookShit;
        public TextView tvNoVideo;
        public TextView tvPh;
        public TextView tvRelateLightHelp;
        public TextView tvRelativeClear;
        public TextView tvRelativeClearTime;
        public TextView tvShitClearError;
        public TextView tvShitError;
        public TextView tvShitLeftBottom;
        public TextView tvShitLeftTop;
        public TextView tvTopLookShit;
        public TextView tvTopOdor;
        public TextView tvTopOdorTime;
        public View vLine;

        @SuppressLint({"ClickableViewAccessibility"})
        public T7Holder(@NonNull View view) {
            super(view);
            this.swipeMenuLayout = (MySwipeMenuLayout) view.findViewById(R.id.t7_swipe);
            this.tvEventTime = (TextView) view.findViewById(R.id.tv_event_time);
            this.tvEventTimeAm = (TextView) view.findViewById(R.id.tv_event_time_am);
            this.ivEventLogo = (ImageView) view.findViewById(R.id.iv_event_logo);
            this.ivEventIcon = (ImageView) view.findViewById(R.id.iv_event_icon);
            this.tvCatName = (TextView) view.findViewById(R.id.tv_cat_name);
            this.tvShitLeftTop = (TextView) view.findViewById(R.id.tv_shit_left_top);
            this.llLightHelp = (LinearLayout) view.findViewById(R.id.ll_light_help);
            this.tvLightHelp = (TextView) view.findViewById(R.id.tv_light_help);
            this.llRelateLightHelp = (LinearLayout) view.findViewById(R.id.ll_relate_light_help);
            this.tvRelateLightHelp = (TextView) view.findViewById(R.id.tv_relate_light_help);
            this.llCatVoice = (LinearLayout) view.findViewById(R.id.ll_cat_voice);
            this.tvCatVoice = (TextView) view.findViewById(R.id.tv_cat_voice);
            this.llPh = (LinearLayout) view.findViewById(R.id.ll_ph);
            this.tvPh = (TextView) view.findViewById(R.id.tv_ph);
            this.llBlood = (LinearLayout) view.findViewById(R.id.ll_blood);
            this.tvBlood = (TextView) view.findViewById(R.id.tv_blood);
            this.llClearPh = (LinearLayout) view.findViewById(R.id.ll_clear_ph);
            this.tvClearPh = (TextView) view.findViewById(R.id.tv_clear_ph);
            this.llClearBlood = (LinearLayout) view.findViewById(R.id.ll_clear_blood);
            this.tvClearBlood = (TextView) view.findViewById(R.id.tv_clear_blood);
            this.llShitError = (LinearLayout) view.findViewById(R.id.ll_shit_error);
            this.tvShitError = (TextView) view.findViewById(R.id.tv_shit_error);
            this.llShitClearError = (LinearLayout) view.findViewById(R.id.ll_shit_clear_error);
            this.tvShitClearError = (TextView) view.findViewById(R.id.tv_shit_clear_error);
            this.rlPlay = (CircleRelativeLayout) view.findViewById(R.id.rl_play);
            this.ivEventPic = (ImageView) view.findViewById(R.id.iv_event_pic);
            this.ivTopPlay = (ImageView) view.findViewById(R.id.iv_top_play);
            this.flParent = (FrameLayout) view.findViewById(R.id.fl_parent);
            this.tvNoVideo = (TextView) view.findViewById(R.id.tv_no_video);
            this.tvTopLookShit = (TextView) view.findViewById(R.id.tv_top_look_shit);
            this.tvShitLeftBottom = (TextView) view.findViewById(R.id.tv_shit_left_bottom);
            this.llCancelClear = (LinearLayout) view.findViewById(R.id.ll_cancel_clear);
            this.tvCancelClearTime = (TextView) view.findViewById(R.id.tv_cancel_clear_time);
            this.tvCancelClear = (TextView) view.findViewById(R.id.tv_cancel_clear);
            this.llOdorTop = (LinearLayout) view.findViewById(R.id.ll_odor_top);
            this.tvTopOdorTime = (TextView) view.findViewById(R.id.tv_top_odor_time);
            this.tvTopOdor = (TextView) view.findViewById(R.id.tv_top_odor);
            this.llRelativeClear = (LinearLayout) view.findViewById(R.id.ll_relative_clear);
            this.tvRelativeClearTime = (TextView) view.findViewById(R.id.tv_relative_clear_time);
            this.tvRelativeClear = (TextView) view.findViewById(R.id.tv_relative_clear);
            this.rlClearPic = (RelativeLayout) view.findViewById(R.id.rl_clear_pic);
            this.ivClearPic = (NewRoundImageview) view.findViewById(R.id.iv_clear_pic);
            this.ivClearPlay = (ImageView) view.findViewById(R.id.iv_clear_play);
            this.tvClearNoData = (TextView) view.findViewById(R.id.tv_clear_no_data);
            this.llOdorBottom = (LinearLayout) view.findViewById(R.id.ll_odor_bottom);
            this.tvBottomOdorTime = (TextView) view.findViewById(R.id.tv_bottom_odor_time);
            this.tvBottomOdor = (TextView) view.findViewById(R.id.tv_bottom_odor);
            this.tvLookShit = (TextView) view.findViewById(R.id.tv_look_shit);
            this.vLine = view.findViewById(R.id.v_line);
            this.tvDelete = (TextView) view.findViewById(R.id.tv_del);
            view.setTag(this);
        }
    }

    public static class FootHolder extends RecyclerView.ViewHolder {
        public ImageView ivLoadMore;
        public LinearLayout llBottom;
        public TextView tvNoDatDa;

        public FootHolder(@NonNull View view) {
            super(view);
            this.llBottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
            this.tvNoDatDa = (TextView) view.findViewById(R.id.tv_no_data);
            this.ivLoadMore = (ImageView) view.findViewById(R.id.iv_load_more);
        }
    }

    private boolean delete(int i) {
        T7Record t7Record;
        return (i == 5 || i == 10 || i == 20) && (t7Record = this.t7Record) != null && t7Record.getDeviceShared() == null;
    }

    public static abstract class MyImageTask extends AsyncTask<String, Void, String> {
        public final WeakReference<T7HomeActivity> mContext;

        public abstract void getFilePath(String str);

        public MyImageTask(T7HomeActivity t7HomeActivity) {
            this.mContext = new WeakReference<>(t7HomeActivity);
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) {
            if (this.mContext.get().isFinishing()) {
                return null;
            }
            try {
                return Glide.with(CommonUtils.getAppContext()).load(strArr[0]).downloadOnly(Integer.MIN_VALUE, Integer.MIN_VALUE).get().getPath();
            } catch (Exception unused) {
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            if (this.mContext.get().isFinishing()) {
                return;
            }
            getFilePath(str);
        }
    }

    public final T7EventInfo getInfoFromClearInfo(T7EventClearInfo t7EventClearInfo, String str, List<ShitInfo> list, String str2) {
        T7EventInfo t7EventInfo = new T7EventInfo();
        t7EventInfo.setDeviceId(t7EventClearInfo.getDeviceId());
        t7EventInfo.setEventId(t7EventClearInfo.getEventId());
        t7EventInfo.setIsNeedUploadVideo(t7EventClearInfo.getIsNeedUploadVideo());
        t7EventInfo.setContent(t7EventClearInfo.getContent());
        t7EventInfo.setEventType(t7EventClearInfo.getEventType());
        t7EventInfo.setPetId(str);
        t7EventInfo.setUpload(t7EventClearInfo.getUpload());
        t7EventInfo.setPreview(t7EventClearInfo.getPreview());
        t7EventInfo.setAesKey(t7EventClearInfo.getAesKey());
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (ShitInfo shitInfo : list) {
                shitInfo.setEventType(10);
                shitInfo.setEventId(str2);
            }
            arrayList.addAll(list);
        }
        if (showClearShitPic(t7EventClearInfo)) {
            for (ShitInfo shitInfo2 : t7EventClearInfo.getShitPictures()) {
                shitInfo2.setEventType(5);
                shitInfo2.setEventId(t7EventClearInfo.getEventId());
            }
            arrayList.addAll(t7EventClearInfo.getShitPictures());
        }
        t7EventInfo.setShitPictures(arrayList);
        t7EventInfo.setStorageSpace(t7EventClearInfo.getStorageSpace());
        t7EventInfo.setDuration(t7EventClearInfo.getDuration());
        t7EventInfo.setExpire(t7EventClearInfo.getExpire());
        t7EventInfo.setMark(t7EventClearInfo.getMark());
        t7EventInfo.setMedia(t7EventClearInfo.getMedia());
        t7EventInfo.setMediaApi(t7EventClearInfo.getMediaApi());
        t7EventInfo.setTimestamp(t7EventClearInfo.getTimestamp());
        return t7EventInfo;
    }
}

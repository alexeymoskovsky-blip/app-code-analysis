package com.petkit.android.activities.petkitBleDevice.t6.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
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
import com.github.sunnysuperman.commons.utils.CollectionUtil;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.ImageUtils;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.petkitBleDevice.hg.widget.NewRoundImageview;
import com.petkit.android.activities.petkitBleDevice.t6.mode.ShitInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6ContentInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventClearInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetCleanEvent;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6DataUtils;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MySwipeMenuLayout;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes5.dex */
public class T6EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public boolean addFootView;
    public final Activity context;
    public EventListListener eventListListener;
    public final FamilyInfor familyInfor;
    public boolean hideCatPop;
    public boolean isShare;
    public List<T6EventInfo> list;
    public boolean loadMore;
    public long selectDayTime;
    public final T6Record t6Record;
    public TimeZone timezone;
    public boolean underweightFlag;
    public final int FootType = 1;
    public int lastPlayPosition = -1;
    public final T6AnimUtil t6AnimUtil = new T6AnimUtil();

    public interface EventListListener {
        void clickVoiceError(T6EventInfo t6EventInfo, int i);

        void deleteEvent(T6EventInfo t6EventInfo, int i);

        void jumpToClearSetting();

        void littleWight();

        void lookShit(T6EventInfo t6EventInfo, int i);

        void selectPet(T6EventInfo t6EventInfo, int i);

        void selectPet2(T6EventInfo t6EventInfo, int i);

        void startPlayVideo(T6EventInfo t6EventInfo, int i);
    }

    public final boolean delete(int i) {
        return i == 5 || i == 10 || i == 19 || i == 20;
    }

    public void setEventListListener(EventListListener eventListListener) {
        this.eventListListener = eventListListener;
    }

    public void setLoadMore(boolean z) {
        this.loadMore = z;
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setHideCatPop(boolean z) {
        this.hideCatPop = z;
        notifyDataSetChanged();
    }

    public void addFootView(boolean z) {
        this.addFootView = z;
    }

    public T6EventAdapter(Activity activity, List<T6EventInfo> list, T6Record t6Record, FamilyInfor familyInfor) {
        this.underweightFlag = false;
        this.context = activity;
        this.list = list;
        this.t6Record = t6Record;
        this.familyInfor = familyInfor;
        if (t6Record != null) {
            this.timezone = t6Record.getActualTimeZone();
            if (t6Record.getSettings() != null) {
                this.underweightFlag = t6Record.getSettings().getUnderweight() == 1;
            }
            this.isShare = t6Record.getDeviceShared() != null;
        }
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
        return new T6Holder(LayoutInflater.from(this.context).inflate(R.layout.t6_item_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (this.addFootView && i == getItemCount() - 1) {
            initFootHolder((FootHolder) viewHolder);
        } else {
            initT6Holder((T6Holder) viewHolder, i);
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
            T6AnimUtil t6AnimUtil = this.t6AnimUtil;
            if (t6AnimUtil != null) {
                t6AnimUtil.startRotateAnim(footHolder.ivLoadMore);
                return;
            }
            return;
        }
        footHolder.ivLoadMore.setVisibility(8);
        footHolder.tvNoDatDa.setVisibility(0);
    }

    /* JADX WARN: Removed duplicated region for block: B:165:0x060b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initT6Holder(final com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.T6Holder r25, final int r26) {
        /*
            Method dump skipped, instruction units count: 2234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.initT6Holder(com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter$T6Holder, int):void");
    }

    public static /* synthetic */ void lambda$initT6Holder$0(T6EventClearInfo t6EventClearInfo, T6Holder t6Holder, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), t6EventClearInfo.getAesKey());
                if (fileDecryptImageFile != null) {
                    t6Holder.ivClearPic.setImageBitmap(CommonUtil.bimapSquareRound(100, BitmapFactory.decodeFile(fileDecryptImageFile.getAbsolutePath())));
                    T6DataUtils.getInstance().rotateVideo(t6EventClearInfo.getPreview(), t6Holder.ivClearPic);
                } else {
                    t6Holder.ivClearPic.setImageResource(R.drawable.default_image);
                }
                return;
            } catch (Exception unused) {
                t6Holder.ivClearPic.setImageResource(R.drawable.default_image);
                return;
            }
        }
        t6Holder.ivClearPic.setImageResource(R.drawable.default_image);
    }

    public final /* synthetic */ void lambda$initT6Holder$1(T6EventInfo t6EventInfo, int i, View view) {
        EventListListener eventListListener = this.eventListListener;
        if (eventListListener != null) {
            eventListListener.startPlayVideo(t6EventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initT6Holder$2(T6EventInfo t6EventInfo, int i, View view) {
        T6EventClearInfo infoByEventType;
        if (t6EventInfo.getSubContent() == null || t6EventInfo.getSubContent().size() < 1 || this.eventListListener == null || (infoByEventType = getInfoByEventType(t6EventInfo.getSubContent(), 5)) == null) {
            return;
        }
        this.eventListListener.startPlayVideo(getInfoFromClearInfo(infoByEventType, t6EventInfo.getPetId()), i);
    }

    public final /* synthetic */ void lambda$initT6Holder$3(T6Holder t6Holder, T6EventInfo t6EventInfo, int i, View view) {
        if (this.eventListListener != null) {
            t6Holder.swipeMenuLayout.smoothClose();
            this.eventListListener.deleteEvent(t6EventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initT6Holder$4(T6EventInfo t6EventInfo, int i, View view) {
        EventListListener eventListListener;
        if (this.isShare || (eventListListener = this.eventListListener) == null) {
            return;
        }
        eventListListener.selectPet(t6EventInfo, i);
    }

    public final /* synthetic */ void lambda$initT6Holder$5(T6EventInfo t6EventInfo, T6Holder t6Holder, int i, View view) {
        EventListListener eventListListener;
        EventListListener eventListListener2;
        if (this.isShare) {
            return;
        }
        if (t6EventInfo.getEventType() == 10) {
            if (t6Holder.tvLittle.getVisibility() == 0 || (eventListListener2 = this.eventListListener) == null) {
                return;
            }
            eventListListener2.selectPet(t6EventInfo, i);
            return;
        }
        if ((t6EventInfo.getEventType() == 19 || t6EventInfo.getEventType() == 20) && (eventListListener = this.eventListListener) != null) {
            eventListListener.selectPet2(t6EventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initT6Holder$6(T6EventInfo t6EventInfo, int i, View view) {
        EventListListener eventListListener;
        if (this.isShare) {
            return;
        }
        if (t6EventInfo.getEventType() == 10) {
            EventListListener eventListListener2 = this.eventListListener;
            if (eventListListener2 != null) {
                eventListListener2.selectPet(t6EventInfo, i);
                return;
            }
            return;
        }
        if ((t6EventInfo.getEventType() == 19 || t6EventInfo.getEventType() == 20) && (eventListListener = this.eventListListener) != null) {
            eventListListener.selectPet2(t6EventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initT6Holder$7(T6EventInfo t6EventInfo, int i, View view) {
        EventListListener eventListListener;
        if (t6EventInfo.getMedia() == 0 || t6EventInfo.getUpload() == 0 || (eventListListener = this.eventListListener) == null) {
            return;
        }
        eventListListener.lookShit(t6EventInfo, i);
    }

    public final /* synthetic */ void lambda$initT6Holder$8(T6EventInfo t6EventInfo, int i, View view) {
        if (this.eventListListener != null) {
            if (!(TextUtils.isEmpty(t6EventInfo.getPreview()) && TextUtils.isEmpty(t6EventInfo.getMediaApi())) && System.currentTimeMillis() / 1000 <= t6EventInfo.getExpire()) {
                DataHelper.setBooleanSF(this.context, Constants.T6_VIDEO_FULL, Boolean.TRUE);
                this.eventListListener.clickVoiceError(t6EventInfo, i);
            }
        }
    }

    public final /* synthetic */ void lambda$initT6Holder$9(T6EventInfo t6EventInfo, int i, View view) {
        if (this.eventListListener == null || t6EventInfo.getMedia() == 0 || t6EventInfo.getUpload() == 0) {
            return;
        }
        this.eventListListener.lookShit(t6EventInfo, i);
    }

    public final /* synthetic */ void lambda$initT6Holder$10(T6EventInfo t6EventInfo, int i, View view) {
        T6EventClearInfo infoByEventType;
        if (this.eventListListener == null || (infoByEventType = getInfoByEventType(t6EventInfo.getSubContent(), 5)) == null) {
            return;
        }
        this.eventListListener.lookShit(getInfoFromClearInfo(infoByEventType, t6EventInfo.getPetId()), i);
    }

    public final /* synthetic */ void lambda$initT6Holder$11(T6EventInfo t6EventInfo, int i, View view) {
        T6EventClearInfo infoByEventType;
        if (this.eventListListener == null || (infoByEventType = getInfoByEventType(t6EventInfo.getSubContent(), 5)) == null || infoByEventType.getMedia() == 0 || infoByEventType.getUpload() == 0) {
            return;
        }
        this.eventListListener.lookShit(getInfoFromClearInfo(infoByEventType, t6EventInfo.getPetId()), i);
    }

    public final /* synthetic */ void lambda$initT6Holder$12(T6EventInfo t6EventInfo, int i, View view) {
        EventListListener eventListListener = this.eventListListener;
        if (eventListListener != null) {
            eventListListener.selectPet2(t6EventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initT6Holder$13(T6EventInfo t6EventInfo, View view) {
        EventListListener eventListListener;
        if (!this.isShare && t6EventInfo.getEventType() == 10 && t6EventInfo.getContent() != null && t6EventInfo.getContent().getAutoClear() == 7 && t6EventInfo.getContent().getPetOutTips() == 1 && (eventListListener = this.eventListListener) != null) {
            eventListListener.jumpToClearSetting();
        }
    }

    public final T6EventClearInfo getInfoByEventType(List<T6EventClearInfo> list, int i) {
        for (T6EventClearInfo t6EventClearInfo : list) {
            if (t6EventClearInfo.getEventType() == i) {
                return t6EventClearInfo;
            }
        }
        return null;
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setList(List<T6EventInfo> list, int i) {
        if (i < 1) {
            return;
        }
        if (i == 1) {
            this.list = list;
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

    public final boolean hasEvent(List<T6EventInfo> list) {
        if (list.size() < 1) {
            return false;
        }
        T6EventInfo t6EventInfo = list.get(0);
        Iterator<T6EventInfo> it = this.list.iterator();
        while (it.hasNext()) {
            if (t6EventInfo.getEventId().equals(it.next().getEventId())) {
                return true;
            }
        }
        return false;
    }

    public void updateUploadVideo(String str) {
        if (str == null) {
            return;
        }
        for (T6EventInfo t6EventInfo : this.list) {
            if (str.equals(t6EventInfo.getEventId())) {
                t6EventInfo.setIsNeedUploadVideo(0);
                return;
            }
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void deleteShit(String str, int i, int i2, String str2) {
        List<T6EventInfo> list = this.list;
        if (list == null || list.size() < 1) {
            return;
        }
        for (T6EventInfo t6EventInfo : this.list) {
            if (i2 == 5) {
                if (t6EventInfo.getEventType() == 10) {
                    T6EventClearInfo infoByEventType = getInfoByEventType(t6EventInfo.getSubContent(), 5);
                    if (t6EventInfo.getSubContent().size() > 0 && infoByEventType != null && infoByEventType.getEventId().equals(str)) {
                        if (i == 1) {
                            infoByEventType.setShitPictures(deleteShitPic(infoByEventType.getShitPictures(), str2));
                        } else {
                            infoByEventType.setPreview("");
                        }
                        notifyDataSetChanged();
                        return;
                    }
                } else if (t6EventInfo.getEventType() == 5) {
                    if (t6EventInfo.getEventId().equals(str)) {
                        if (i == 1) {
                            t6EventInfo.setShitPictures(deleteShitPic(t6EventInfo.getShitPictures(), str2));
                        } else {
                            t6EventInfo.setPreview("");
                        }
                        notifyDataSetChanged();
                        return;
                    }
                } else if (t6EventInfo.getEventId().equals(str)) {
                    t6EventInfo.setPreview("");
                    notifyDataSetChanged();
                    return;
                }
            } else if (t6EventInfo.getEventId().equals(str)) {
                t6EventInfo.setPreview("");
                notifyDataSetChanged();
                return;
            }
        }
    }

    public final List<ShitInfo> deleteShitPic(List<ShitInfo> list, String str) {
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
        List<T6EventInfo> list = this.list;
        if (list == null || list.size() < 1) {
            return 0;
        }
        for (T6EventInfo t6EventInfo : this.list) {
            if (i == 5) {
                if (t6EventInfo.getEventType() == 10) {
                    T6EventClearInfo infoByEventType = getInfoByEventType(t6EventInfo.getSubContent(), 5);
                    if (t6EventInfo.getSubContent().size() > 0 && infoByEventType != null && infoByEventType.getEventId().equals(str)) {
                        t6EventInfo.setSubContent(new ArrayList());
                        notifyDataSetChanged();
                        return this.list.size();
                    }
                } else if (t6EventInfo.getEventId().equals(str)) {
                    this.list.remove(t6EventInfo);
                    notifyDataSetChanged();
                    return this.list.size();
                }
            } else if (t6EventInfo.getEventId().equals(str)) {
                this.list.remove(t6EventInfo);
                notifyDataSetChanged();
                return this.list.size();
            }
        }
        return this.list.size();
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void updateOnePet(String str, String str2, String str3) {
        for (T6EventInfo t6EventInfo : this.list) {
            if (t6EventInfo.getPetId().equals(str) && t6EventInfo.getEventId().equals(str3)) {
                t6EventInfo.setPetId(str2);
                notifyDataSetChanged();
            }
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void updateOnePet(UpdatePetCleanEvent updatePetCleanEvent) {
        for (T6EventInfo t6EventInfo : this.list) {
            if (t6EventInfo.getEventType() == 5 && t6EventInfo.getPetId().equals(updatePetCleanEvent.getOldPetId()) && t6EventInfo.getEventId().equals(updatePetCleanEvent.getEventId())) {
                t6EventInfo.setPetId(updatePetCleanEvent.getPetId());
                t6EventInfo.setPetName(updatePetCleanEvent.getPetName());
                t6EventInfo.getContent().setManualMatch(updatePetCleanEvent.isPetChanged() ? 1 : t6EventInfo.getContent().getManualMatch());
            }
            if (!CollectionUtil.isEmpty(t6EventInfo.getSubContent())) {
                for (T6EventClearInfo t6EventClearInfo : t6EventInfo.getSubContent()) {
                    if (t6EventClearInfo.getEventType() == 5) {
                        t6EventClearInfo.setPetId(updatePetCleanEvent.getPetId());
                        t6EventClearInfo.setPetName(updatePetCleanEvent.getPetName());
                        t6EventClearInfo.getContent().setManualMatch(updatePetCleanEvent.isPetChanged() ? 1 : t6EventClearInfo.getContent().getManualMatch());
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public boolean isExpireService(int i) {
        T6Record t6Record;
        boolean z = false;
        if (i < 0 || (t6Record = this.t6Record) == null) {
            return false;
        }
        boolean z2 = t6Record.getCloudProduct() == null || (this.t6Record.getCloudProduct().getWorkIndate() != null && this.list.get(i).getTimestamp() > Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()));
        if (this.t6Record.getServiceStatus() == 0 || (this.t6Record.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }

    public final void initType19(T6Holder t6Holder, T6EventInfo t6EventInfo) {
        t6Holder.ivEventLogo.setVisibility(0);
        t6Holder.ivEventIcon.setVisibility(8);
        t6Holder.tvTopLookShit.setVisibility(8);
        t6Holder.llTopPh.setVisibility(8);
        if (!TextUtils.isEmpty(t6EventInfo.getDesc())) {
            t6Holder.tvEventStyle1.setText(addArrow(t6EventInfo.getDesc()));
            t6Holder.tvEventStyle1.setVisibility(0);
        }
        String petInfo = getPetInfo(t6Holder, t6EventInfo.getPetId(), t6EventInfo.getEventType(), 0, 0, t6EventInfo.getPetName(), t6EventInfo.getContent() == null ? 0 : t6EventInfo.getContent().getPetOutTips(), t6EventInfo.getContent() == null ? 0 : t6EventInfo.getContent().getClearOverTips());
        if (!TextUtils.isEmpty(petInfo)) {
            String string = this.context.getResources().getString(R.string.Cat_near, petInfo);
            int color = Color.parseColor(ColorUtils.getPetColorById(t6EventInfo.getPetId(), petInfo));
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(color), string.indexOf(petInfo), string.indexOf(petInfo) + petInfo.length(), 33);
            t6Holder.tvCatName.setText(spannableString);
            t6Holder.tvCatName.setMovementMethod(LinkMovementMethod.getInstance());
            t6Holder.tvCatName.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
        }
        t6Holder.llEventStyle2.setVisibility(8);
        t6Holder.tvLittle.setVisibility(8);
        if (this.hideCatPop) {
            t6Holder.llCatFace.setVisibility(8);
        }
    }

    public final void initType20(T6Holder t6Holder, T6EventInfo t6EventInfo) {
        String string;
        t6Holder.ivEventLogo.setVisibility(0);
        t6Holder.ivEventIcon.setVisibility(8);
        t6Holder.tvTopLookShit.setVisibility(8);
        t6Holder.llTopPh.setVisibility(8);
        if (!TextUtils.isEmpty(t6EventInfo.getDesc())) {
            t6Holder.tvEventStyle1.setText(addArrow(t6EventInfo.getDesc()));
            t6Holder.tvEventStyle1.setVisibility(0);
        }
        int count = t6EventInfo.getContent() != null ? t6EventInfo.getContent().getCount() : 0;
        String petInfo = getPetInfo(t6Holder, t6EventInfo.getPetId(), t6EventInfo.getEventType(), 0, count, t6EventInfo.getPetName(), t6EventInfo.getContent() == null ? 0 : t6EventInfo.getContent().getPetOutTips(), t6EventInfo.getContent() == null ? 0 : t6EventInfo.getContent().getClearOverTips());
        if (!TextUtils.isEmpty(petInfo)) {
            if (count > 1) {
                string = this.context.getResources().getString(R.string.Appeared_multi, petInfo);
            } else {
                string = this.context.getResources().getString(R.string.Appeared, petInfo);
            }
            int color = Color.parseColor(ColorUtils.getPetColorById(t6EventInfo.getPetId(), petInfo));
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(color), string.indexOf(petInfo), string.indexOf(petInfo) + petInfo.length(), 33);
            t6Holder.tvCatName.setText(spannableString);
            t6Holder.tvCatName.setMovementMethod(LinkMovementMethod.getInstance());
            t6Holder.tvCatName.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
        }
        t6Holder.llEventStyle2.setVisibility(8);
        t6Holder.tvLittle.setVisibility(8);
        if (this.hideCatPop) {
            t6Holder.llCatFace.setVisibility(8);
        }
    }

    @SuppressLint({"DefaultLocale"})
    public final void initType10(final T6Holder t6Holder, final T6EventInfo t6EventInfo, final int i) {
        t6Holder.ivEventLogo.setVisibility(0);
        t6Holder.ivEventIcon.setVisibility(8);
        t6Holder.tvTopLookShit.setVisibility(8);
        t6Holder.llTopPh.setVisibility(8);
        t6Holder.llCatFace.setVisibility(8);
        int petOutTips = t6EventInfo.getContent() == null ? 0 : t6EventInfo.getContent().getPetOutTips();
        int autoClear = t6EventInfo.getContent() == null ? 0 : t6EventInfo.getContent().getAutoClear();
        int clearOverTips = t6EventInfo.getContent() == null ? 0 : t6EventInfo.getContent().getClearOverTips();
        String petInfo = getPetInfo(t6Holder, t6EventInfo.getPetId(), t6EventInfo.getEventType(), autoClear, 0, t6EventInfo.getPetName(), petOutTips, clearOverTips);
        if (petInfo == null || t6EventInfo.getContent() == null) {
            return;
        }
        String str = T6Utils.getPetWeight(this.context, t6EventInfo, petInfo) + "  ";
        int color = Color.parseColor(ColorUtils.getPetColorById(t6EventInfo.getPetId(), petInfo));
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(color), str.indexOf(petInfo), str.indexOf(petInfo) + petInfo.length(), 33);
        if (CommonUtil.getInt(t6EventInfo.getPetId()) == -1 && !this.underweightFlag && !this.isShare && petOutTips == 0 && clearOverTips == 0) {
            int length = str.length();
            spannableString.setSpan(new ImageSpan(this.context, R.drawable.icon_t6_gray_flag, 0), length - 1, length, 18);
            spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.2
                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(@NonNull TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setUnderlineText(false);
                }

                @Override // android.text.style.ClickableSpan
                public void onClick(@NonNull View view) {
                    if (T6EventAdapter.this.eventListListener != null) {
                        T6EventAdapter.this.eventListListener.littleWight();
                    }
                }
            }, length - 2, length, 17);
            spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.3
                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(@NonNull TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setUnderlineText(false);
                    textPaint.setColor(T6EventAdapter.this.context.getResources().getColor(R.color.common_text));
                }

                @Override // android.text.style.ClickableSpan
                public void onClick(@NonNull View view) {
                    if (t6EventInfo.getEventType() != 10 || t6Holder.tvLittle.getVisibility() == 0 || T6EventAdapter.this.isShare || T6EventAdapter.this.eventListListener == null) {
                        return;
                    }
                    T6EventAdapter.this.eventListListener.selectPet(t6EventInfo, i);
                }
            }, 0, length - 3, 33);
        }
        t6Holder.tvCatName.setText(spannableString);
        t6Holder.tvCatName.setMovementMethod(LinkMovementMethod.getInstance());
        t6Holder.tvCatName.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initType5(com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.T6Holder r8, com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventInfo r9) {
        /*
            Method dump skipped, instruction units count: 383
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.initType5(com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter$T6Holder, com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventInfo):void");
    }

    public final void initType6(T6Holder t6Holder, T6EventInfo t6EventInfo) {
        t6Holder.ivEventLogo.setVisibility(8);
        t6Holder.llEventStyle2.setVisibility(8);
        t6Holder.ivEventIcon.setVisibility(0);
        t6Holder.tvTopLookShit.setVisibility(8);
        t6Holder.llTopPh.setVisibility(8);
        t6Holder.tvLittle.setVisibility(8);
        t6Holder.llCatFace.setVisibility(8);
        T6ContentInfo content = t6EventInfo.getContent();
        if (content == null) {
            return;
        }
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t6Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else {
            t6Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        }
        t6Holder.tvCatName.setText(getCatLitterContent(content.getResult(), content.getErr(), this.t6Record.getTypeCode(), content.getStartReason()));
    }

    public final void initType7(T6Holder t6Holder, T6EventInfo t6EventInfo) {
        t6Holder.ivEventLogo.setVisibility(8);
        t6Holder.llEventStyle2.setVisibility(8);
        t6Holder.ivEventIcon.setVisibility(0);
        t6Holder.tvTopLookShit.setVisibility(8);
        t6Holder.llTopPh.setVisibility(8);
        t6Holder.tvLittle.setVisibility(8);
        t6Holder.llCatFace.setVisibility(8);
        T6ContentInfo content = t6EventInfo.getContent();
        if (content == null) {
            return;
        }
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t6Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else if (content.getResult().intValue() != 5 || !TextUtils.isEmpty(content.getErr())) {
            t6Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        } else {
            t6Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        }
        t6Holder.tvCatName.setText(getResetContent(content.getResult(), content.getErr(), content.getModeTime(), this.t6Record.getTypeCode()));
    }

    public final void initType8(T6Holder t6Holder, T6EventInfo t6EventInfo) {
        t6Holder.ivEventLogo.setVisibility(8);
        t6Holder.llEventStyle2.setVisibility(8);
        t6Holder.ivEventIcon.setVisibility(0);
        t6Holder.tvTopLookShit.setVisibility(8);
        t6Holder.llTopPh.setVisibility(8);
        t6Holder.tvLittle.setVisibility(8);
        t6Holder.llEventStyle3.setVisibility(8);
        t6Holder.llCatFace.setVisibility(8);
        T6ContentInfo content = t6EventInfo.getContent();
        if (content == null) {
            return;
        }
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t6Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else {
            t6Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        }
        t6Holder.tvCatName.setText(getSprayContent(content.getResult(), content.getStartReason(), content.getModeTime(), content.getErr()));
    }

    public final void initType21(T6Holder t6Holder, T6EventInfo t6EventInfo) {
        t6Holder.ivEventLogo.setVisibility(8);
        t6Holder.llEventStyle2.setVisibility(8);
        t6Holder.ivEventIcon.setVisibility(0);
        t6Holder.tvTopLookShit.setVisibility(8);
        t6Holder.llTopPh.setVisibility(8);
        t6Holder.tvLittle.setVisibility(8);
        t6Holder.llCatFace.setVisibility(8);
        T6ContentInfo content = t6EventInfo.getContent();
        if (content.getResult() != null && content.getResult().intValue() == 0) {
            t6Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
        } else {
            t6Holder.ivEventIcon.setImageResource(R.drawable.icon_t6_event_error);
        }
        t6Holder.tvCatName.setText(getPackageContent(content.getResult(), content.getErr()));
    }

    public final String getPetInfo(T6Holder t6Holder, String str, int i, int i2, int i3, String str2, int i4, int i5) {
        Pet petById = UserInforUtils.getPetById(str);
        if (petById != null) {
            str2 = petById.getName();
        }
        if (TextUtils.isEmpty(str2)) {
            if (i == 10) {
                t6Holder.ivEventLogo.setImageResource(R.drawable.icon_t6_pet_out_gray);
            } else if (i == 20) {
                if (i3 > 1) {
                    t6Holder.tvCatName.setText(this.context.getResources().getString(R.string.Cat_appeared_multi));
                } else {
                    t6Holder.tvCatName.setText(this.context.getResources().getString(R.string.Cat_appeared));
                }
                t6Holder.ivEventLogo.setImageResource(R.drawable.icon_t6_pet_in_gray);
            } else {
                t6Holder.tvCatName.setText(this.context.getResources().getString(R.string.Cat_approaching_litter_box));
                t6Holder.ivEventLogo.setImageResource(R.drawable.icon_t6_pet_in_gray);
            }
            if (CommonUtil.getInt(str) == -1) {
                t6Holder.llEventStyle2.setVisibility(8);
                if (this.isShare || i != 10) {
                    t6Holder.tvLittle.setVisibility(8);
                } else if (i2 != 7) {
                    t6Holder.tvLittle.setVisibility(0);
                    t6Holder.tvLittle.setText(this.context.getResources().getString(R.string.Ignore_record_light_weight));
                } else if (i4 == 1) {
                    t6Holder.tvLittle.setVisibility(0);
                    t6Holder.tvLittle.setText(this.context.getResources().getString(R.string.Toilet_open_protection_tips));
                } else if (i4 == 2) {
                    t6Holder.tvLittle.setVisibility(0);
                    t6Holder.tvLittle.setText(this.context.getResources().getString(R.string.Ignore_record_light_weight));
                } else if (i5 > 0) {
                    t6Holder.tvLittle.setVisibility(8);
                } else {
                    t6Holder.tvLittle.setText(this.context.getResources().getString(R.string.Ignore_record_light_weight));
                    t6Holder.tvLittle.setVisibility(0);
                }
            } else if (!this.isShare && i == 10 && i2 == 7 && i4 == 1) {
                t6Holder.llEventStyle2.setVisibility(8);
                t6Holder.tvLittle.setVisibility(0);
                t6Holder.tvLittle.setText(this.context.getResources().getString(R.string.Toilet_open_protection_tips));
            } else {
                t6Holder.llEventStyle2.setVisibility(0);
                t6Holder.tvLittle.setVisibility(8);
            }
            if (this.isShare) {
                t6Holder.llEventStyle2.setVisibility(8);
            }
        } else {
            if (i == 10) {
                t6Holder.ivEventLogo.setImageResource(R.drawable.icon_t6_pet_out);
            } else if (i == 20) {
                t6Holder.ivEventLogo.setImageResource(R.drawable.icon_t6_pet_in);
            } else {
                t6Holder.ivEventLogo.setImageResource(R.drawable.icon_t6_pet_in);
            }
            t6Holder.llEventStyle2.setVisibility(8);
            t6Holder.tvLittle.setVisibility(8);
        }
        return str2;
    }

    public T6EventInfo getInfo(int i) {
        if (i < 0) {
            return null;
        }
        return this.list.get(i);
    }

    public List<T6EventInfo> getInforList() {
        return this.list;
    }

    public final SpannableString addArrow(String str) {
        SpannableString spannableString = new SpannableString(str + "  ");
        int length = str.length();
        spannableString.setSpan(new ImageSpan(this.context, R.drawable.icon_arrow_right_center, 0), length + (-1), length, 18);
        return spannableString;
    }

    public static class T6Holder extends RecyclerView.ViewHolder {
        public FrameLayout flParent;
        public NewRoundImageview ivClearPic;
        public ImageView ivClearPlay;
        public ImageView ivEventIcon;
        public ImageView ivEventLogo;
        public NewRoundImageview ivEventPic;
        public ImageView ivTopPlay;
        public LinearLayout llCatFace;
        public LinearLayout llCatVoice;
        public LinearLayout llClear;
        public LinearLayout llClear2;
        public LinearLayout llDeodorant;
        public LinearLayout llDeodorant2;
        public LinearLayout llEventStyle2;
        public LinearLayout llEventStyle3;
        public LinearLayout llPh;
        public LinearLayout llTopPh;
        public int mPosition;
        public RelativeLayout rlClearPic;
        public RelativeLayout rlPlay;
        public MySwipeMenuLayout swipeMenuLayout;
        public TextView tvCatFace;
        public TextView tvCatName;
        public TextView tvCatVoice;
        public TextView tvClearEvent;
        public TextView tvClearEvent2;
        public TextView tvClearTime;
        public TextView tvClearTime2;
        public TextView tvDelete;
        public TextView tvDeodorantEvent;
        public TextView tvDeodorantEvent2;
        public TextView tvDeodorantTime;
        public TextView tvDeodorantTime2;
        public TextView tvEventClearStyle;
        public TextView tvEventStyle1;
        public TextView tvEventStyle3;
        public TextView tvEventTime;
        public TextView tvEventTimeAm;
        public TextView tvLittle;
        public TextView tvLookShit;
        public TextView tvPh;
        public TextView tvTopLookShit;
        public TextView tvTopPh;
        public View vLine;

        @SuppressLint({"ClickableViewAccessibility"})
        public T6Holder(@NonNull View view) {
            super(view);
            this.swipeMenuLayout = (MySwipeMenuLayout) view.findViewById(R.id.t6_swipe);
            this.rlPlay = (RelativeLayout) view.findViewById(R.id.rl_play);
            this.flParent = (FrameLayout) view.findViewById(R.id.fl_parent);
            this.tvEventTime = (TextView) view.findViewById(R.id.tv_event_time);
            this.tvEventTimeAm = (TextView) view.findViewById(R.id.tv_event_time_am);
            this.tvCatName = (TextView) view.findViewById(R.id.tv_cat_name);
            this.llCatVoice = (LinearLayout) view.findViewById(R.id.ll_cat_voice);
            this.tvCatVoice = (TextView) view.findViewById(R.id.tv_cat_voice);
            this.llPh = (LinearLayout) view.findViewById(R.id.ll_ph);
            this.tvPh = (TextView) view.findViewById(R.id.tv_ph);
            this.llTopPh = (LinearLayout) view.findViewById(R.id.ll_top_ph);
            this.tvTopPh = (TextView) view.findViewById(R.id.tv_top_ph);
            this.tvEventStyle1 = (TextView) view.findViewById(R.id.tv_event_style1);
            this.llEventStyle2 = (LinearLayout) view.findViewById(R.id.ll_event_style2);
            this.llEventStyle3 = (LinearLayout) view.findViewById(R.id.ll_event_style3);
            this.tvEventStyle3 = (TextView) view.findViewById(R.id.tv_event_style3);
            this.ivEventPic = (NewRoundImageview) view.findViewById(R.id.iv_event_pic);
            this.ivEventLogo = (ImageView) view.findViewById(R.id.iv_event_logo);
            this.ivEventIcon = (ImageView) view.findViewById(R.id.iv_event_icon);
            this.tvClearTime = (TextView) view.findViewById(R.id.tv_clear_time);
            this.tvClearEvent = (TextView) view.findViewById(R.id.tv_clear_event);
            this.rlClearPic = (RelativeLayout) view.findViewById(R.id.rl_clear_pic);
            this.ivClearPic = (NewRoundImageview) view.findViewById(R.id.iv_clear_pic);
            this.ivTopPlay = (ImageView) view.findViewById(R.id.iv_top_play);
            this.ivClearPlay = (ImageView) view.findViewById(R.id.iv_clear_play);
            this.llClear = (LinearLayout) view.findViewById(R.id.ll_clear);
            this.llClear2 = (LinearLayout) view.findViewById(R.id.ll_clear2);
            this.tvClearTime2 = (TextView) view.findViewById(R.id.tv_clear_time2);
            this.tvClearEvent2 = (TextView) view.findViewById(R.id.tv_clear_event2);
            this.tvEventClearStyle = (TextView) view.findViewById(R.id.tv_event_clear_style);
            this.tvLookShit = (TextView) view.findViewById(R.id.tv_look_shit);
            this.tvTopLookShit = (TextView) view.findViewById(R.id.tv_top_look_shit);
            this.llDeodorant = (LinearLayout) view.findViewById(R.id.ll_deodorant);
            this.tvDeodorantTime = (TextView) view.findViewById(R.id.tv_deodorant_time);
            this.tvDeodorantEvent = (TextView) view.findViewById(R.id.tv_deodorant_event);
            this.llDeodorant2 = (LinearLayout) view.findViewById(R.id.ll_deodorant2);
            this.tvDeodorantTime2 = (TextView) view.findViewById(R.id.tv_deodorant_time2);
            this.tvDeodorantEvent2 = (TextView) view.findViewById(R.id.tv_deodorant_event2);
            this.vLine = view.findViewById(R.id.v_line);
            this.llCatFace = (LinearLayout) view.findViewById(R.id.ll_cat_face);
            this.tvCatFace = (TextView) view.findViewById(R.id.tv_cat_face);
            this.tvLittle = (TextView) view.findViewById(R.id.tv_little);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getCatLitterContent(java.lang.Integer r4, java.lang.String r5, int r6, int r7) {
        /*
            Method dump skipped, instruction units count: 698
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.getCatLitterContent(java.lang.Integer, java.lang.String, int, int):java.lang.String");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getResetContent(java.lang.Integer r6, java.lang.String r7, int r8, int r9) {
        /*
            Method dump skipped, instruction units count: 702
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.getResetContent(java.lang.Integer, java.lang.String, int, int):java.lang.String");
    }

    public final String getSprayContent(Integer num, int i, int i2, String str) {
        if (num == null) {
            return "";
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            if (i == 0) {
                return this.context.getResources().getString(R.string.T5_deodorant_record_atuo_deodorant_complete_prompt);
            }
            if (i == 1) {
                return this.context.getResources().getString(R.string.T5_deodorant_record_timing_deodorant_complete_prompt);
            }
            return this.context.getResources().getString(R.string.T5_deodorant_record_manual_deodorant_complete_prompt);
        }
        if (iIntValue != 1) {
            if (iIntValue != 3) {
                if (iIntValue != 4) {
                    if (iIntValue == 5) {
                        if (i == 0) {
                            return this.context.getResources().getString(R.string.T5_deodorant_record_atuo_deodorant_k4_not_install_error_prompt);
                        }
                        if (i == 2 || i == 3) {
                            return this.context.getResources().getString(R.string.T5_deodorant_record_manual_deodorant_k4_not_install_error_prompt);
                        }
                        return this.context.getResources().getString(R.string.T5_deodorant_record_timing_deodorant_k4_not_install_error_prompt);
                    }
                    if (iIntValue != 6) {
                        if (iIntValue == 7) {
                            return this.context.getResources().getString(R.string.T5_deodorizing_cancel);
                        }
                        if (i == 0) {
                            return this.context.getResources().getString(R.string.T5_deodorant_record_atuo_deodorant_error_prompt);
                        }
                        if (i == 1) {
                            return this.context.getResources().getString(R.string.T5_deodorant_record_timing_deodorant_error_prompt);
                        }
                        return this.context.getResources().getString(R.string.T5_deodorant_record_manual_deodorant_error_prompt);
                    }
                }
                if (i == 0) {
                    return this.context.getResources().getString(R.string.T5_history_record_auto_deodorant_cancel_prompt);
                }
                if (i == 1) {
                    return this.context.getResources().getString(R.string.T5_history_record_timing_deodorant_cancel_prompt);
                }
                return this.context.getResources().getString(R.string.T5_history_record_manual_deodorant_cancel_prompt);
            }
        } else {
            if (i == 0) {
                return this.context.getResources().getString(R.string.T5_history_record_auto_deodorant_terminate_prompt);
            }
            if (i == 1) {
                return this.context.getResources().getString(R.string.T5_history_record_timing_deodorant_terminate_prompt);
            }
            if (i == 2 || i == 3) {
                return this.context.getResources().getString(R.string.T5_history_record_manual_deodorant_terminate_prompt);
            }
        }
        if (i == 0) {
            return this.context.getResources().getString(R.string.T5_deodorant_record_atuo_deodorant_complete_prompt) + this.context.getResources().getString(R.string.T3_history_record_deodorant_complete_liquid_prompt);
        }
        if (i == 1) {
            return this.context.getResources().getString(R.string.T5_deodorant_record_timing_deodorant_complete_prompt) + this.context.getResources().getString(R.string.T3_history_record_deodorant_complete_liquid_prompt);
        }
        return this.context.getResources().getString(R.string.T5_deodorant_record_manual_deodorant_complete_prompt) + this.context.getResources().getString(R.string.T3_history_record_deodorant_complete_liquid_prompt);
    }

    public final String getPackageContent(Integer num, String str) {
        if (num == null) {
            return "";
        }
        if (num.intValue() == 0) {
            return this.context.getResources().getString(R.string.T6_pack_success);
        }
        str.hashCode();
        switch (str) {
            case "trunk_F":
                return this.context.getResources().getString(R.string.T6_history_record_pack_trunk_F_error_prompt);
            case "packERR":
                return this.context.getResources().getString(R.string.T6_history_record_pack_packerr_error_prompt);
            case "roller_E":
                return this.context.getResources().getString(R.string.T6_history_record_pack_roller_e_error_prompt);
            case "hallP":
                return this.context.getResources().getString(R.string.T6_history_record_pack_hallp_error_prompt);
            case "hallT":
                return this.context.getResources().getString(R.string.T6_history_record_pack_hallt_error_prompt);
            case "packModule_C":
                return this.context.getResources().getString(R.string.T6_history_record_pack_packModule_c_error_prompt);
            case "packModule_O":
                return this.context.getResources().getString(R.string.T6_history_record_pack_packModule_o_error_prompt);
            case "package_T":
                return this.context.getResources().getString(R.string.T6_history_record_pack_package_t_error_prompt);
            case "package_U":
                return this.context.getResources().getString(R.string.T6_history_record_pack_package_u_error_prompt);
            case "packbox_E":
                return this.context.getResources().getString(R.string.T6_history_record_pack_packbox_e_error_prompt);
            case "packbox_I":
                return this.context.getResources().getString(R.string.T6_history_record_pack_packbox_i_error_prompt);
            case "packbox_U":
                return this.context.getResources().getString(R.string.T6_history_record_pack_packbox_u_error_prompt);
            default:
                return this.context.getResources().getString(R.string.T6_pack_failed);
        }
    }

    public static abstract class MyImageTask extends AsyncTask<String, Void, String> {
        public abstract void getFilePath(String str);

        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) {
            try {
                return Glide.with(CommonUtils.getAppContext()).load(strArr[0]).downloadOnly(Integer.MIN_VALUE, Integer.MIN_VALUE).get().getPath();
            } catch (Exception unused) {
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            getFilePath(str);
        }
    }

    public final T6EventInfo getInfoFromClearInfo(T6EventClearInfo t6EventClearInfo, String str) {
        T6EventInfo t6EventInfo = new T6EventInfo();
        t6EventInfo.setDeviceId(t6EventClearInfo.getDeviceId());
        t6EventInfo.setEventId(t6EventClearInfo.getEventId());
        t6EventInfo.setIsNeedUploadVideo(t6EventClearInfo.getIsNeedUploadVideo());
        t6EventInfo.setContent(t6EventClearInfo.getContent());
        t6EventInfo.setEventType(t6EventClearInfo.getEventType());
        t6EventInfo.setPetId(str);
        t6EventInfo.setUpload(t6EventClearInfo.getUpload());
        t6EventInfo.setPreview(t6EventClearInfo.getPreview());
        t6EventInfo.setAesKey(t6EventClearInfo.getAesKey());
        t6EventInfo.setShitPictures(t6EventClearInfo.getShitPictures());
        t6EventInfo.setStorageSpace(t6EventClearInfo.getStorageSpace());
        t6EventInfo.setDuration(t6EventClearInfo.getDuration());
        t6EventInfo.setExpire(t6EventClearInfo.getExpire());
        t6EventInfo.setMark(t6EventClearInfo.getMark());
        t6EventInfo.setMedia(t6EventClearInfo.getMedia());
        t6EventInfo.setMediaApi(t6EventClearInfo.getMediaApi());
        t6EventInfo.setTimestamp(t6EventClearInfo.getTimestamp());
        return t6EventInfo;
    }
}

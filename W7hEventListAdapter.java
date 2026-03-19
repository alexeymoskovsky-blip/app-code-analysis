package com.petkit.android.activities.petkitBleDevice.w7h.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.ImageUtils;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6DataUtils;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hContentInfo;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hEventInfo;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import com.petkit.oversea.databinding.ItemFooterViewBinding;
import com.petkit.oversea.databinding.W7hItemViewBinding;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes5.dex */
@SuppressLint({"NotifyDataSetChanged"})
public class W7hEventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final Activity context;
    public EventListListener eventListListener;
    public boolean hideCatPop;
    public boolean isShare;
    public List<W7hEventInfo> list;
    public boolean loadMore;
    public long selectDayTime;
    public TimeZone timezone;
    public W7hRecord w7hRecord;
    public boolean addFootView = true;
    public int lastPlayPosition = -1;
    public final int NormalType = 0;
    public final int FooterType = 1;
    public T6AnimUtil animUtil = new T6AnimUtil();

    public interface EventListListener {
        void deleteEvent(W7hEventInfo w7hEventInfo, int i);

        void selectPet(W7hEventInfo w7hEventInfo, int i);

        void startPlayVideo(W7hEventInfo w7hEventInfo, int i);
    }

    private boolean delete(int i) {
        return true;
    }

    public W7hEventListAdapter(Activity activity, List<W7hEventInfo> list, W7hRecord w7hRecord) {
        this.context = activity;
        this.list = list;
        this.w7hRecord = w7hRecord;
        if (w7hRecord != null) {
            this.timezone = w7hRecord.getActualTimeZone();
            this.isShare = w7hRecord.getDeviceShared() != null;
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

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new FooterHolder((ItemFooterViewBinding) DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.item_footer_view, viewGroup, false));
        }
        return new EventItemHolder((W7hItemViewBinding) DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.w7h_item_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint({"RecyclerView"}) int i) {
        if (this.addFootView && i == getItemCount() - 1) {
            initFooterHolder(((FooterHolder) viewHolder).binding);
            return;
        }
        EventItemHolder eventItemHolder = (EventItemHolder) viewHolder;
        initW7hItemHolder(eventItemHolder.binding, i);
        eventItemHolder.mPosition = i;
    }

    public final void initW7hItemHolder(final W7hItemViewBinding w7hItemViewBinding, final int i) {
        String strTimeStampToTimeStringWithUnit;
        final W7hEventInfo w7hEventInfo = this.list.get(i);
        W7hContentInfo content = w7hEventInfo.getContent();
        List<W7hEventInfo> subContent = w7hEventInfo.getSubContent();
        W7hEventInfo w7hEventInfo2 = !subContent.isEmpty() ? subContent.get(0) : null;
        if (this.isShare) {
            w7hItemViewBinding.swipeMenuLayout.setSwipeEnable(false);
        } else {
            w7hItemViewBinding.swipeMenuLayout.setSwipeEnable(delete(w7hEventInfo.getEventType()));
        }
        boolean z = true;
        if (TimeUtils.getInstance().is24HourSystem()) {
            w7hItemViewBinding.tvEventTimeAm.setVisibility(8);
            strTimeStampToTimeStringWithUnit = TimeUtils.getInstance().timeStampToTimeStringWithUnit(this.context, content.getStartTime(), this.timezone);
        } else {
            w7hItemViewBinding.tvEventTimeAm.setVisibility(0);
            String[] t6EventTime = TimeUtils.getInstance().getT6EventTime(this.context, content.getStartTime(), this.timezone);
            String str = t6EventTime[0];
            w7hItemViewBinding.tvEventTimeAm.setText(t6EventTime[1]);
            strTimeStampToTimeStringWithUnit = str;
        }
        w7hItemViewBinding.tvEventTime.setText(strTimeStampToTimeStringWithUnit);
        int eventType = w7hEventInfo.getEventType();
        if (eventType == 5 || eventType == 6) {
            initDrankType(w7hItemViewBinding, w7hEventInfo);
        } else {
            if (eventType == 12) {
                initDisinfectType(w7hItemViewBinding, w7hEventInfo);
            } else if (eventType != 20) {
                switch (eventType) {
                    case 8:
                        initWashingType(w7hItemViewBinding, w7hEventInfo);
                        break;
                    case 9:
                        initAddWaterType(w7hItemViewBinding, w7hEventInfo);
                        break;
                    case 10:
                        initDrainType(w7hItemViewBinding, w7hEventInfo);
                        break;
                }
            } else {
                initDetectionType(w7hItemViewBinding, w7hEventInfo);
            }
            z = false;
        }
        if (this.hideCatPop) {
            w7hItemViewBinding.llCatFace.setVisibility(8);
        }
        w7hItemViewBinding.tvCatName.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hEventListAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initW7hItemHolder$0(w7hEventInfo, i, view);
            }
        });
        w7hItemViewBinding.llEventDesc.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hEventListAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initW7hItemHolder$1(w7hEventInfo, i, view);
            }
        });
        w7hItemViewBinding.tvDel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hEventListAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initW7hItemHolder$2(w7hItemViewBinding, w7hEventInfo, i, view);
            }
        });
        w7hItemViewBinding.llEventDesc.setVisibility(8);
        if (!TextUtils.isEmpty(w7hEventInfo.getDesc())) {
            w7hItemViewBinding.tvEventDesc.setText(w7hEventInfo.getDesc());
            w7hItemViewBinding.llEventDesc.setVisibility(0);
        }
        if (w7hEventInfo2 != null) {
            w7hItemViewBinding.llSubEventTitle.setVisibility(0);
            subEventAddWater(w7hItemViewBinding, w7hEventInfo2);
        } else {
            w7hItemViewBinding.llSubEventTitle.setVisibility(8);
            w7hItemViewBinding.layAddWater.setVisibility(8);
            w7hItemViewBinding.tvSubEventNotResource.setVisibility(8);
            w7hItemViewBinding.layAddWater.setVisibility(8);
            w7hItemViewBinding.ivAddWaterPlay.setVisibility(8);
            w7hItemViewBinding.layAddWater.setOnClickListener(null);
        }
        if (!z) {
            w7hItemViewBinding.llRightContent.setVisibility(8);
            w7hItemViewBinding.tvSubEventNotResource.setVisibility(8);
            w7hItemViewBinding.layAddWater.setVisibility(8);
            w7hItemViewBinding.ivAddWaterPlay.setVisibility(8);
            return;
        }
        w7hItemViewBinding.llRightContent.setVisibility(0);
        if (!isExpireService(i) || TextUtils.isEmpty(w7hEventInfo.getMediaApi())) {
            w7hItemViewBinding.ivTopPlay.setVisibility(8);
        } else {
            w7hItemViewBinding.ivTopPlay.setVisibility(0);
        }
        if (w7hEventInfo.getMedia() == 0) {
            w7hItemViewBinding.layVideo.setVisibility(8);
            w7hItemViewBinding.tvEventNotResource.setVisibility(0);
            w7hItemViewBinding.tvEventNotResource.setText(this.context.getResources().getString(R.string.Camera_off_no_feedsvideo));
        } else if (w7hEventInfo.getUpload() == 0) {
            w7hItemViewBinding.layVideo.setVisibility(8);
            w7hItemViewBinding.tvEventNotResource.setVisibility(0);
            w7hItemViewBinding.tvEventNotResource.setText(this.context.getResources().getString(R.string.T6_event_video_close_title));
        } else if (TextUtils.isEmpty(w7hEventInfo.getPreview()) && TextUtils.isEmpty(w7hEventInfo.getMediaApi())) {
            w7hItemViewBinding.tvEventNotResource.setVisibility(8);
            w7hItemViewBinding.layVideo.setVisibility(8);
            w7hItemViewBinding.tvEventNotResource.setVisibility(0);
            w7hItemViewBinding.tvEventNotResource.setText(this.context.getResources().getString(R.string.No_video));
        } else if (System.currentTimeMillis() / 1000 > w7hEventInfo.getExpire()) {
            w7hItemViewBinding.layVideo.setVisibility(8);
            w7hItemViewBinding.tvEventNotResource.setVisibility(0);
            w7hItemViewBinding.tvEventNotResource.setText(this.context.getResources().getString(R.string.Image_expired));
        } else {
            w7hItemViewBinding.tvEventNotResource.setVisibility(8);
            w7hItemViewBinding.layVideo.setVisibility(0);
            new T6EventAdapter.MyImageTask() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hEventListAdapter.1
                @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6EventAdapter.MyImageTask
                public void getFilePath(String str2) {
                    if (str2 != null) {
                        File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str2), w7hEventInfo.getAesKey());
                        if (fileDecryptImageFile != null && fileDecryptImageFile.exists()) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(fileDecryptImageFile.getAbsolutePath(), options);
                            if (bitmapDecodeFile != null) {
                                w7hItemViewBinding.ivEventPic.setImageBitmap(CommonUtil.bimapSquareRound(170, bitmapDecodeFile));
                                T6DataUtils.getInstance().rotateVideo(w7hEventInfo.getPreview(), w7hItemViewBinding.ivEventPic);
                                return;
                            }
                            w7hItemViewBinding.ivEventPic.setImageResource(R.drawable.petkit_default_image);
                            return;
                        }
                        w7hItemViewBinding.ivEventPic.setImageResource(R.drawable.petkit_default_image);
                        return;
                    }
                    w7hItemViewBinding.ivEventPic.setImageResource(R.drawable.petkit_default_image);
                }
            }.execute(CommonUtil.httpToHttps(w7hEventInfo.getPreview()));
        }
        w7hItemViewBinding.flParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hEventListAdapter$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initW7hItemHolder$3(w7hEventInfo, i, view);
            }
        });
    }

    public final /* synthetic */ void lambda$initW7hItemHolder$0(W7hEventInfo w7hEventInfo, int i, View view) {
        EventListListener eventListListener;
        if (this.isShare) {
            return;
        }
        if ((w7hEventInfo.getEventType() == 20 || w7hEventInfo.getEventType() == 5 || w7hEventInfo.getEventType() == 6) && (eventListListener = this.eventListListener) != null) {
            eventListListener.selectPet(w7hEventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initW7hItemHolder$1(W7hEventInfo w7hEventInfo, int i, View view) {
        EventListListener eventListListener;
        if (this.isShare) {
            return;
        }
        if ((w7hEventInfo.getEventType() == 20 || w7hEventInfo.getEventType() == 5 || w7hEventInfo.getEventType() == 6) && (eventListListener = this.eventListListener) != null) {
            eventListListener.selectPet(w7hEventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initW7hItemHolder$2(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo, int i, View view) {
        if (this.eventListListener != null) {
            w7hItemViewBinding.swipeMenuLayout.smoothClose();
            this.eventListListener.deleteEvent(w7hEventInfo, i);
        }
    }

    public final /* synthetic */ void lambda$initW7hItemHolder$3(W7hEventInfo w7hEventInfo, int i, View view) {
        EventListListener eventListListener = this.eventListListener;
        if (eventListListener != null) {
            eventListListener.startPlayVideo(w7hEventInfo, i);
        }
    }

    public List<W7hEventInfo> getInforList() {
        List<W7hEventInfo> list = this.list;
        if (list != null && !list.isEmpty()) {
            return this.list;
        }
        return new ArrayList();
    }

    public final void initDrankType(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        String petInfo = getPetInfo(w7hItemViewBinding, w7hEventInfo);
        if (TextUtils.isEmpty(petInfo)) {
            return;
        }
        String string = this.context.getResources().getString(R.string.Pet_drink_use_time, petInfo, TimeUtils.getInstance().secondsToMinuteAndSecondsText(this.context, w7hEventInfo.getContent().getDrinkTime()));
        int color = Color.parseColor(ColorUtils.getPetColorById(w7hEventInfo.getPetId(), petInfo));
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(color), string.indexOf(petInfo), string.indexOf(petInfo) + petInfo.length(), 33);
        w7hItemViewBinding.tvCatName.setText(spannableString);
        w7hItemViewBinding.tvCatName.setMovementMethod(LinkMovementMethod.getInstance());
        w7hItemViewBinding.tvCatName.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
    }

    public final void initWashingType(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        w7hItemViewBinding.tvCatName.setText(W7hUtils.getInstance().getEventFlushTitle(this.context, w7hEventInfo));
    }

    public final void initDisinfectType(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        w7hItemViewBinding.tvCatName.setText(W7hUtils.getInstance().getEventDisinfect(this.context, w7hEventInfo));
    }

    public final void initDrainType(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        w7hItemViewBinding.tvCatName.setText(W7hUtils.getInstance().getEventDrainTitle(this.context, w7hEventInfo));
    }

    public final void initAddWaterType(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        w7hItemViewBinding.tvCatName.setText(W7hUtils.getInstance().getEventRefillTitle(this.context, w7hEventInfo));
    }

    public final void subEventAddWater(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        String strTimeStampToTimeStringWithUnit;
        if (TimeUtils.getInstance().is24HourSystem()) {
            w7hItemViewBinding.tvEventTimeAm.setVisibility(8);
            strTimeStampToTimeStringWithUnit = TimeUtils.getInstance().timeStampToTimeStringWithUnit(this.context, w7hEventInfo.getContent().getStartTime(), this.timezone);
        } else {
            w7hItemViewBinding.tvEventTimeAm.setVisibility(0);
            String[] t6EventTime = TimeUtils.getInstance().getT6EventTime(this.context, w7hEventInfo.getContent().getStartTime(), this.timezone);
            String str = t6EventTime[0];
            w7hItemViewBinding.tvEventTimeAm.setText(t6EventTime[1]);
            strTimeStampToTimeStringWithUnit = str;
        }
        w7hItemViewBinding.tvSubEventTime.setText(strTimeStampToTimeStringWithUnit);
        w7hItemViewBinding.tvSubEventName.setText(W7hUtils.getInstance().getEventRefillTitle(this.context, w7hEventInfo));
    }

    public final void initDetectionType(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        String string;
        int count = w7hEventInfo.getContent() != null ? w7hEventInfo.getContent().getCount() : 0;
        String petInfo = getPetInfo(w7hItemViewBinding, w7hEventInfo);
        if (TextUtils.isEmpty(petInfo)) {
            return;
        }
        if (count > 1) {
            string = this.context.getResources().getString(R.string.Appeared_multi, petInfo);
        } else {
            string = this.context.getResources().getString(R.string.Appeared, petInfo);
        }
        int color = Color.parseColor(ColorUtils.getPetColorById(w7hEventInfo.getPetId(), petInfo));
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(color), string.indexOf(petInfo), string.indexOf(petInfo) + petInfo.length(), 33);
        w7hItemViewBinding.tvCatName.setText(spannableString);
        w7hItemViewBinding.tvCatName.setMovementMethod(LinkMovementMethod.getInstance());
        w7hItemViewBinding.tvCatName.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
    }

    public final String getPetInfo(W7hItemViewBinding w7hItemViewBinding, W7hEventInfo w7hEventInfo) {
        int eventType = w7hEventInfo.getEventType();
        String petId = w7hEventInfo.getPetId();
        String petName = w7hEventInfo.getPetName();
        int count = w7hEventInfo.getContent() != null ? w7hEventInfo.getContent().getCount() : 0;
        Pet petById = UserInforUtils.getPetById(petId);
        if (petById != null) {
            petName = petById.getName();
        }
        if (TextUtils.isEmpty(petName) && w7hEventInfo.getContent() != null) {
            if (eventType == 5 || eventType == 6) {
                w7hItemViewBinding.tvCatName.setText(this.context.getResources().getString(R.string.Pet_drink_use_time, this.context.getResources().getString(R.string.Pet), TimeUtils.getInstance().secondsToMinuteAndSecondsText(this.context, w7hEventInfo.getContent().getDrinkTime())));
            } else if (eventType == 20) {
                if (count > 1) {
                    w7hItemViewBinding.tvCatName.setText(this.context.getResources().getString(R.string.Cat_appeared_multi));
                } else {
                    w7hItemViewBinding.tvCatName.setText(this.context.getResources().getString(R.string.D4sh_device_record_pet_walk_around));
                }
            }
        }
        return petName;
    }

    public final void initFooterHolder(ItemFooterViewBinding itemFooterViewBinding) {
        if (this.loadMore) {
            itemFooterViewBinding.tvNoData.setVisibility(8);
            itemFooterViewBinding.ivLoadMore.setVisibility(0);
            T6AnimUtil t6AnimUtil = this.animUtil;
            if (t6AnimUtil != null) {
                t6AnimUtil.startRotateAnim(itemFooterViewBinding.ivLoadMore);
                return;
            }
            return;
        }
        itemFooterViewBinding.ivLoadMore.setVisibility(8);
        itemFooterViewBinding.tvNoData.setVisibility(0);
    }

    public void setEventListListener(EventListListener eventListListener) {
        this.eventListListener = eventListListener;
    }

    public void setLoadMore(boolean z) {
        this.loadMore = z;
    }

    public void setAddFootView(boolean z) {
        this.addFootView = z;
    }

    public void clearList() {
        this.list = new ArrayList();
        notifyDataSetChanged();
    }

    public void setList(List<W7hEventInfo> list, int i) {
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

    private boolean hasEvent(List<W7hEventInfo> list) {
        if (list.size() < 1) {
            return false;
        }
        W7hEventInfo w7hEventInfo = list.get(0);
        Iterator<W7hEventInfo> it = this.list.iterator();
        while (it.hasNext()) {
            if (w7hEventInfo.getEventId().equals(it.next().getEventId())) {
                return true;
            }
        }
        return false;
    }

    public void setSelectDayTime(long j) {
        this.selectDayTime = j;
    }

    public void setLastPlayPosition(int i) {
        this.lastPlayPosition = i;
    }

    public int getLastPlayPosition() {
        return this.lastPlayPosition;
    }

    public int deleteEvent(String str, int i) {
        List<W7hEventInfo> list = this.list;
        if (list == null || list.isEmpty()) {
            return 0;
        }
        for (W7hEventInfo w7hEventInfo : this.list) {
            if (w7hEventInfo.getEventId().equals(str)) {
                this.list.remove(w7hEventInfo);
                notifyDataSetChanged();
                return this.list.size();
            }
        }
        return this.list.size();
    }

    public W7hEventInfo getInfo(int i) {
        if (i < 0) {
            return null;
        }
        return this.list.get(i);
    }

    public void setHideCatPop(boolean z) {
        this.hideCatPop = z;
        notifyDataSetChanged();
    }

    public void updateUploadVideo(String str) {
        if (str == null) {
            return;
        }
        for (W7hEventInfo w7hEventInfo : this.list) {
            if (str.equals(w7hEventInfo.getEventId())) {
                w7hEventInfo.setIsNeedUploadVideo(0);
                return;
            }
        }
    }

    public void updateOnePet(String str, String str2, String str3) {
        for (W7hEventInfo w7hEventInfo : this.list) {
            if (w7hEventInfo.getPetId().equals(str) && w7hEventInfo.getEventId().equals(str3)) {
                w7hEventInfo.setPetId(str2);
                notifyDataSetChanged();
            }
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {
        public final ItemFooterViewBinding binding;

        public FooterHolder(ItemFooterViewBinding itemFooterViewBinding) {
            super(itemFooterViewBinding.getRoot());
            this.binding = itemFooterViewBinding;
        }
    }

    public static class EventItemHolder extends RecyclerView.ViewHolder {
        public final W7hItemViewBinding binding;
        public int mPosition;

        public EventItemHolder(W7hItemViewBinding w7hItemViewBinding) {
            super(w7hItemViewBinding.getRoot());
            this.binding = w7hItemViewBinding;
            this.itemView.setTag(this);
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

    public boolean isExpireService(int i) {
        W7hRecord w7hRecord;
        boolean z = false;
        if (i < 0 || (w7hRecord = this.w7hRecord) == null) {
            return false;
        }
        boolean z2 = w7hRecord.getCloudProduct() == null || (this.w7hRecord.getCloudProduct().getWorkIndate() != null && this.list.get(i).getTimestamp() > Long.parseLong(this.w7hRecord.getCloudProduct().getWorkIndate()));
        if (this.w7hRecord.getServiceStatus() == 0 || (this.w7hRecord.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }

    public boolean isSubExpireService(long j) {
        W7hRecord w7hRecord = this.w7hRecord;
        boolean z = false;
        if (w7hRecord == null) {
            return false;
        }
        boolean z2 = w7hRecord.getCloudProduct() == null || (this.w7hRecord.getCloudProduct().getWorkIndate() != null && j > Long.parseLong(this.w7hRecord.getCloudProduct().getWorkIndate()));
        if (this.w7hRecord.getServiceStatus() == 0 || (this.w7hRecord.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }
}

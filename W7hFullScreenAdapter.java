package com.petkit.android.activities.petkitBleDevice.w7h.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetEvent;
import com.petkit.android.activities.petkitBleDevice.t6.widget.CircleLayout;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7Utils;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hEventInfo;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hDataUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes5.dex */
public class W7hFullScreenAdapter extends RecyclerView.Adapter<W7hHolder> {
    public final FragmentActivity context;
    public int currentPosition = -1;
    public List<W7hEventInfo> list;
    public ItemClickListener listener;
    public TimeZone timezone;
    public final W7hRecord w7hRecord;

    public interface ItemClickListener {
        void startPlayVideo(W7hEventInfo w7hEventInfo, int i);
    }

    public void setListener(ItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    public W7hFullScreenAdapter(FragmentActivity fragmentActivity, List<W7hEventInfo> list, long j) {
        this.context = fragmentActivity;
        this.list = list;
        W7hRecord w7hRecordByDeviceId = W7hDataUtils.getInstance().getW7hRecordByDeviceId(j);
        this.w7hRecord = w7hRecordByDeviceId;
        if (w7hRecordByDeviceId != null) {
            this.timezone = w7hRecordByDeviceId.getActualTimeZone();
        }
    }

    public void setCurrentPosition(int i) {
        this.currentPosition = i;
        notifyDataSetChanged();
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setList(List<W7hEventInfo> list, int i) {
        if (i < 1) {
            return;
        }
        if (i == 1) {
            this.list = list;
        } else {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public int deleteEvent(String str, int i) {
        List<W7hEventInfo> list = this.list;
        if (list == null || list.size() < 1) {
            return 0;
        }
        for (W7hEventInfo w7hEventInfo : this.list) {
            if (i == 5) {
                if (w7hEventInfo.getEventType() == 10) {
                    if (w7hEventInfo.getSubContent().size() > 0 && w7hEventInfo.getSubContent().get(0).getEventId().equals(str)) {
                        w7hEventInfo.setSubContent(new ArrayList());
                        notifyDataSetChanged();
                        return this.list.size();
                    }
                } else if (w7hEventInfo.getEventId().equals(str)) {
                    this.list.remove(w7hEventInfo);
                    notifyDataSetChanged();
                    return this.list.size();
                }
            } else if (w7hEventInfo.getEventId().equals(str)) {
                this.list.remove(w7hEventInfo);
                notifyDataSetChanged();
                return this.list.size();
            }
        }
        return this.list.size();
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void clearList() {
        this.list = new ArrayList();
        notifyDataSetChanged();
    }

    public void clearPlayerState() {
        if (this.currentPosition != -1) {
            this.currentPosition = -1;
            notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public W7hHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new W7hHolder(LayoutInflater.from(this.context).inflate(R.layout.w7h_full_screen_item_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final W7hHolder w7hHolder, @SuppressLint({"RecyclerView"}) final int i) {
        final W7hEventInfo w7hEventInfo = this.list.get(i);
        w7hHolder.ltPlayer.setAnimation(R.raw.cat_play);
        if (this.currentPosition == i) {
            w7hHolder.ltPlayer.setVisibility(0);
            w7hHolder.ltPlayer.playAnimation();
        } else {
            w7hHolder.ltPlayer.setVisibility(8);
            w7hHolder.ltPlayer.pauseAnimation();
        }
        w7hHolder.tvEvent.setTextColor(ContextCompat.getColor(this.context, R.color.white));
        w7hHolder.tvEventTime.setTextColor(ContextCompat.getColor(this.context, R.color.alpha_40_white));
        w7hHolder.tvEventTime.setText(TimeUtils.getInstance().timeStampToTimeStringWithUnit(this.context, w7hEventInfo.getContent() == null ? 0L : w7hEventInfo.getContent().getStartTime(), this.timezone));
        int eventType = w7hEventInfo.getEventType();
        if (eventType == 5 || eventType == 6) {
            initDrankType(w7hHolder, w7hEventInfo);
        } else if (eventType == 8) {
            initWashingType(w7hHolder, w7hEventInfo);
        } else if (eventType == 9) {
            initAddWaterType(w7hHolder, w7hEventInfo);
        } else if (eventType == 20) {
            initDetectionType(w7hHolder, w7hEventInfo);
        }
        w7hHolder.cl.setVisibility(0);
        w7hHolder.ivEventIcon.setVisibility(8);
        final int eventStatus = getEventStatus(w7hEventInfo);
        w7hHolder.tvStatus.setVisibility(eventStatus <= 0 ? 8 : 0);
        if (eventStatus == 1) {
            w7hHolder.tvStatus.setText(this.context.getResources().getString(R.string.Image_expired));
        } else if (eventStatus == 2) {
            w7hHolder.tvStatus.setText(this.context.getResources().getString(R.string.No_video));
        } else if (!this.list.get(i).getPreview().isEmpty()) {
            new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(w7hEventInfo.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hFullScreenAdapter$$ExternalSyntheticLambda0
                @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                public final void onCachePath(String str) {
                    W7hFullScreenAdapter.lambda$onBindViewHolder$0(w7hEventInfo, w7hHolder, str);
                }
            });
        } else {
            w7hHolder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
        }
        w7hHolder.clParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hFullScreenAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$1(i, eventStatus, view);
            }
        });
    }

    public static /* synthetic */ void lambda$onBindViewHolder$0(W7hEventInfo w7hEventInfo, W7hHolder w7hHolder, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), w7hEventInfo.getAesKey());
                if (fileDecryptImageFile != null) {
                    w7hHolder.ivEventPic.setImageBitmap(T7Utils.getRotateBitmap(fileDecryptImageFile.getAbsolutePath(), w7hEventInfo.getPreview()));
                } else {
                    w7hHolder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
                }
                return;
            } catch (Exception unused) {
                w7hHolder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
                return;
            }
        }
        w7hHolder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, int i2, View view) {
        if (this.listener != null) {
            if ((this.list.get(i).getEventType() != 5 || this.list.get(i).getContent() == null || this.list.get(i).getContent().getStartReason() == 0) && i2 == 0) {
                this.listener.startPlayVideo(this.list.get(i), i);
            }
        }
    }

    public final String getPetInfo(W7hEventInfo w7hEventInfo) {
        String petId = w7hEventInfo.getPetId();
        String petName = w7hEventInfo.getPetName();
        Pet petById = UserInforUtils.getPetById(petId);
        return petById == null ? petName : petById.getName();
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

    public int getEventStatus(W7hEventInfo w7hEventInfo) {
        if ((w7hEventInfo.getMediaApi().isEmpty() && w7hEventInfo.getPreview().isEmpty()) || w7hEventInfo.getUpload() == 0) {
            return 2;
        }
        return System.currentTimeMillis() / 1000 > w7hEventInfo.getExpire() ? 1 : 0;
    }

    public final void initDrankType(W7hHolder w7hHolder, W7hEventInfo w7hEventInfo) {
        String petInfo = getPetInfo(w7hEventInfo);
        if (!TextUtils.isEmpty(petInfo)) {
            String string = this.context.getResources().getString(R.string.Pet_drink_use_time, petInfo, TimeUtils.getInstance().secondsToMinuteAndSecondsText(this.context, w7hEventInfo.getContent().getDrinkTime()));
            int color = Color.parseColor(ColorUtils.getPetColorById(w7hEventInfo.getPetId(), petInfo));
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(color), string.indexOf(petInfo), string.indexOf(petInfo) + petInfo.length(), 33);
            w7hHolder.tvEvent.setText(spannableString);
            w7hHolder.tvEvent.setMovementMethod(LinkMovementMethod.getInstance());
            w7hHolder.tvEvent.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
            return;
        }
        w7hHolder.tvEvent.setText(this.context.getResources().getString(R.string.Pet_drink_use_time, this.context.getResources().getString(R.string.Pet), TimeUtils.getInstance().secondsToMinuteAndSecondsText(this.context, w7hEventInfo.getContent().getDrinkTime())));
    }

    public final void initDetectionType(W7hHolder w7hHolder, W7hEventInfo w7hEventInfo) {
        String string;
        int count = w7hEventInfo.getContent() != null ? w7hEventInfo.getContent().getCount() : 0;
        String petInfo = getPetInfo(w7hEventInfo);
        if (!TextUtils.isEmpty(petInfo)) {
            if (count > 1) {
                string = this.context.getResources().getString(R.string.Appeared_multi, petInfo);
            } else {
                string = this.context.getResources().getString(R.string.Appeared, petInfo);
            }
            int color = Color.parseColor(ColorUtils.getPetColorById(w7hEventInfo.getPetId(), petInfo));
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(color), string.indexOf(petInfo), string.indexOf(petInfo) + petInfo.length(), 33);
            w7hHolder.tvEvent.setText(spannableString);
            w7hHolder.tvEvent.setMovementMethod(LinkMovementMethod.getInstance());
            w7hHolder.tvEvent.setHighlightColor(this.context.getResources().getColor(R.color.transparent));
            return;
        }
        w7hHolder.tvEvent.setText(this.context.getResources().getString(R.string.D4sh_device_record_pet_walk_around));
    }

    public final void initAddWaterType(W7hHolder w7hHolder, W7hEventInfo w7hEventInfo) {
        w7hHolder.tvEvent.setText(W7hUtils.getInstance().getEventRefillTitle(this.context, w7hEventInfo));
    }

    public final void initWashingType(W7hHolder w7hHolder, W7hEventInfo w7hEventInfo) {
        w7hHolder.tvEvent.setText(W7hUtils.getInstance().getEventFlushTitle(this.context, w7hEventInfo));
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

    @SuppressLint({"NotifyDataSetChanged"})
    public void updatePetEvent(UpdatePetEvent updatePetEvent) {
        if (updatePetEvent == null) {
            return;
        }
        for (W7hEventInfo w7hEventInfo : this.list) {
            if (updatePetEvent.getEventId().equals(w7hEventInfo.getEventId())) {
                w7hEventInfo.setPetId(updatePetEvent.getPetId());
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public W7hEventInfo getInfo(int i) {
        if (i < 0) {
            return null;
        }
        return this.list.get(i);
    }

    public List<W7hEventInfo> getList() {
        return this.list;
    }

    public void updateOnePet(String str, String str2, String str3) {
        for (W7hEventInfo w7hEventInfo : this.list) {
            if (w7hEventInfo.getPetId().equals(str) && w7hEventInfo.getEventId().equals(str3)) {
                w7hEventInfo.setPetId(str2);
                notifyDataSetChanged();
            }
        }
    }

    public static class W7hHolder extends RecyclerView.ViewHolder {
        public CircleLayout cl;
        public LinearLayout clParent;
        public ImageView ivEventIcon;
        public ImageView ivEventPic;
        public LottieAnimationView ltPlayer;
        public TextView tvEvent;
        public TextView tvEventTime;
        public TextView tvLook;
        public TextView tvStatus;

        public W7hHolder(@NonNull View view) {
            super(view);
            this.clParent = (LinearLayout) view.findViewById(R.id.cl_parent);
            this.cl = (CircleLayout) view.findViewById(R.id.cl);
            this.ltPlayer = (LottieAnimationView) view.findViewById(R.id.lt_player);
            this.ivEventIcon = (ImageView) view.findViewById(R.id.iv_event_icon);
            this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
            this.tvEventTime = (TextView) view.findViewById(R.id.tv_event_time);
            this.tvStatus = (TextView) view.findViewById(R.id.tv_status);
            this.ivEventPic = (ImageView) view.findViewById(R.id.iv_event_pic);
            this.tvLook = (TextView) view.findViewById(R.id.tv_look);
            view.setTag(this);
        }
    }
}

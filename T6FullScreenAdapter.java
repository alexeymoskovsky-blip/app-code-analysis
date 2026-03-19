package com.petkit.android.activities.petkitBleDevice.t6.adapter;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.util.Log;
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
import com.petkit.android.activities.petkitBleDevice.t6.T6LiveFullscreenActivity;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetEvent;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6DataUtils;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.CircleLayout;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes5.dex */
public class T6FullScreenAdapter extends RecyclerView.Adapter<T6Holder> {
    public final FragmentActivity context;
    public List<T6EventInfo> list;
    public ItemClickListener listener;
    public final T6Record t6Record;
    public TimeZone timezone;

    public interface ItemClickListener {
        void lookShit(T6EventInfo t6EventInfo, int i);

        void startPlayVideo(T6EventInfo t6EventInfo, int i);
    }

    public void setListener(ItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    public T6FullScreenAdapter(FragmentActivity fragmentActivity, List<T6EventInfo> list, long j, int i) {
        this.context = fragmentActivity;
        this.list = list;
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(j, i == 27 ? 0 : 1);
        this.t6Record = t6RecordByDeviceId;
        if (t6RecordByDeviceId != null) {
            this.timezone = t6RecordByDeviceId.getActualTimeZone();
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setList(List<T6EventInfo> list, int i) {
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
        List<T6EventInfo> list = this.list;
        if (list == null || list.size() < 1) {
            return 0;
        }
        for (T6EventInfo t6EventInfo : this.list) {
            if (i == 5) {
                if (t6EventInfo.getEventType() == 10) {
                    if (t6EventInfo.getSubContent().size() > 0 && t6EventInfo.getSubContent().get(0).getEventId().equals(str)) {
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
    public void clearList() {
        this.list = new ArrayList();
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public T6Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new T6Holder(LayoutInflater.from(this.context).inflate(R.layout.t6_full_screen_item_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final T6Holder t6Holder, @SuppressLint({"RecyclerView"}) final int i) {
        boolean z;
        int iIntValue;
        String str;
        final T6EventInfo t6EventInfo = this.list.get(i);
        String str2 = T6LiveFullscreenActivity.eventId;
        if (str2 != null && str2.equals(t6EventInfo.getEventId())) {
            t6Holder.tvEvent.setTextColor(ContextCompat.getColor(this.context, R.color.color_659BFF));
            t6Holder.tvEventTime.setTextColor(ContextCompat.getColor(this.context, R.color.color_659BFF));
        } else {
            t6Holder.tvEvent.setTextColor(ContextCompat.getColor(this.context, R.color.white));
            t6Holder.tvEventTime.setTextColor(ContextCompat.getColor(this.context, R.color.alpha_40_white));
        }
        long startTime = 0;
        if (t6EventInfo.getEventType() == 10) {
            if (t6EventInfo.getContent() != null) {
                startTime = t6EventInfo.getContent().getTimeIn();
            }
        } else if (t6EventInfo.getContent() != null) {
            startTime = t6EventInfo.getContent().getStartTime();
        }
        t6Holder.tvEventTime.setText(TimeUtils.getInstance().timeStampToTimeStringWithUnit(this.context, startTime, this.timezone));
        if (t6EventInfo.getEventType() == 5) {
            if (t6EventInfo.getContent() == null) {
                str = "";
                z = false;
            } else {
                boolean z2 = (t6EventInfo.getContent().getStartReason() == 0 && t6EventInfo.getContent().getResult().intValue() == 0) ? false : true;
                String clearContent = T6Utils.getClearContent(this.context, t6EventInfo.getContent(), this.t6Record.getTypeCode());
                if (t6EventInfo.getContent().getResult().intValue() == 0) {
                    t6Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
                } else {
                    t6Holder.ivEventIcon.setImageResource(R.drawable.d4s_state_failed);
                }
                z = z2;
                str = clearContent;
            }
            t6Holder.tvEvent.setText(str);
        } else {
            setPetInfo(t6Holder, t6EventInfo.getPetId(), t6EventInfo.getEventType(), t6EventInfo.getContent() != null ? t6EventInfo.getContent().getCount() : 0);
            z = false;
        }
        t6Holder.cl.setVisibility(z ? 8 : 0);
        t6Holder.ivEventIcon.setVisibility(z ? 0 : 8);
        final int eventStatus = getEventStatus(t6EventInfo);
        t6Holder.tvStatus.setVisibility(eventStatus > 0 ? 0 : 8);
        if (eventStatus == 1) {
            t6Holder.tvStatus.setText(this.context.getResources().getString(R.string.Image_expired));
        } else if (eventStatus == 2) {
            t6Holder.tvStatus.setText(this.context.getResources().getString(R.string.No_video));
        } else if (eventStatus == 3) {
            t6Holder.tvStatus.setText(this.context.getResources().getString(R.string.T6_toiletDetection_close));
        } else if (eventStatus == 4) {
            t6Holder.tvStatus.setText(this.context.getResources().getString(R.string.Camera_off_no_feedsvideo));
        } else if (!this.list.get(i).getPreview().isEmpty()) {
            new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(t6EventInfo.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6FullScreenAdapter$$ExternalSyntheticLambda0
                @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                public final void onCachePath(String str3) {
                    T6FullScreenAdapter.lambda$onBindViewHolder$0(t6EventInfo, t6Holder, str3);
                }
            });
        } else {
            t6Holder.ivEventPic.setImageResource(R.drawable.default_image);
        }
        try {
            Field declaredField = LottieAnimationView.class.getDeclaredField("animationResId");
            declaredField.setAccessible(true);
            iIntValue = ((Integer) declaredField.get(t6Holder.ltPlayer)).intValue();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            iIntValue = 0;
        }
        if (iIntValue == 0) {
            t6Holder.ltPlayer.setAnimation(R.raw.t6play);
        }
        boolean zIsExpireService = isExpireService(i);
        if (T6LiveFullscreenActivity.eventId != null && t6EventInfo.getEventId().equals(T6LiveFullscreenActivity.eventId) && eventStatus == 0 && zIsExpireService) {
            t6Holder.ltPlayer.setVisibility(0);
            t6Holder.ltPlayer.playAnimation();
        } else {
            t6Holder.ltPlayer.setVisibility(8);
            t6Holder.ltPlayer.pauseAnimation();
        }
        t6Holder.clParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6FullScreenAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$1(t6EventInfo, i, eventStatus, view);
            }
        });
        if (t6EventInfo.getShitPictures() != null && t6EventInfo.getShitPictures().size() > 0 && t6EventInfo.getContent().getResult().intValue() == 0 && t6EventInfo.getMedia() != 0 && t6EventInfo.getUpload() != 0) {
            t6Holder.tvLook.setVisibility(0);
        } else {
            t6Holder.tvLook.setVisibility(8);
        }
        t6Holder.tvLook.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6FullScreenAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$2(i, view);
            }
        });
    }

    public static /* synthetic */ void lambda$onBindViewHolder$0(T6EventInfo t6EventInfo, T6Holder t6Holder, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), t6EventInfo.getAesKey());
                if (fileDecryptImageFile != null) {
                    t6Holder.ivEventPic.setImageBitmap(CommonUtil.bimapSquareRound(80, BitmapFactory.decodeFile(fileDecryptImageFile.getAbsolutePath())));
                    T6DataUtils.getInstance().rotateVideo(t6EventInfo.getPreview(), t6Holder.ivEventPic);
                } else {
                    t6Holder.ivEventPic.setImageResource(R.drawable.default_image);
                }
                return;
            } catch (Exception unused) {
                t6Holder.ivEventPic.setImageResource(R.drawable.default_image);
                return;
            }
        }
        t6Holder.ivEventPic.setImageResource(R.drawable.default_image);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$1(T6EventInfo t6EventInfo, int i, int i2, View view) {
        Log.d("T6fullscreenAdapter", "startPlayVideo: ");
        String str = T6LiveFullscreenActivity.eventId;
        if (str == null || str.isEmpty() || !T6LiveFullscreenActivity.eventId.equals(t6EventInfo.getEventId())) {
            T6LiveFullscreenActivity.eventId = t6EventInfo.getEventId();
            if (this.listener != null) {
                if ((this.list.get(i).getEventType() != 5 || this.list.get(i).getContent() == null || this.list.get(i).getContent().getStartReason() == 0) && i2 == 0) {
                    this.listener.startPlayVideo(this.list.get(i), i);
                }
            }
        }
    }

    public /* synthetic */ void lambda$onBindViewHolder$2(int i, View view) {
        Log.d("T6fullscreenAdapter", "lookShit: ");
        ItemClickListener itemClickListener = this.listener;
        if (itemClickListener != null) {
            itemClickListener.lookShit(this.list.get(i), i);
        }
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

    public final void setPetInfo(T6Holder t6Holder, String str, int i, int i2) {
        Pet petById = UserInforUtils.getPetById(str);
        if (petById == null) {
            if (i == 10) {
                t6Holder.tvEvent.setText(this.context.getResources().getString(R.string.T4_history_record_pet_into_prompt_one));
                return;
            }
            if (i != 20) {
                t6Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_approaching_litter_box));
                return;
            } else if (i2 > 1) {
                t6Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_appeared_multi));
                return;
            } else {
                t6Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_appeared));
                return;
            }
        }
        String name = petById.getName();
        if (i == 10) {
            t6Holder.tvEvent.setText(name + this.context.getResources().getString(R.string.T6_toileted));
            return;
        }
        if (i != 20) {
            t6Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_near, name));
        } else if (i2 > 1) {
            t6Holder.tvEvent.setText(this.context.getResources().getString(R.string.Appeared_multi, name));
        } else {
            t6Holder.tvEvent.setText(this.context.getResources().getString(R.string.Appeared, name));
        }
    }

    public int getEventStatus(T6EventInfo t6EventInfo) {
        if (t6EventInfo.getMedia() == 0) {
            return 4;
        }
        if (t6EventInfo.getToiletDetection() == 0 && t6EventInfo.getEventType() == 10) {
            return 3;
        }
        if ((t6EventInfo.getMediaApi().isEmpty() && t6EventInfo.getPreview().isEmpty()) || t6EventInfo.getUpload() == 0) {
            return 2;
        }
        return System.currentTimeMillis() / 1000 > t6EventInfo.getExpire() ? 1 : 0;
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

    public void updatePetEvent(UpdatePetEvent updatePetEvent) {
        if (updatePetEvent == null) {
            return;
        }
        for (T6EventInfo t6EventInfo : this.list) {
            if (updatePetEvent.getEventId().equals(t6EventInfo.getEventId())) {
                t6EventInfo.setPetId(updatePetEvent.getPetId());
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public T6EventInfo getInfo(int i) {
        if (i < 0) {
            return null;
        }
        return this.list.get(i);
    }

    public List<T6EventInfo> getList() {
        return this.list;
    }

    public static class T6Holder extends RecyclerView.ViewHolder {
        public CircleLayout cl;
        public LinearLayout clParent;
        public ImageView ivEventIcon;
        public ImageView ivEventPic;
        public LottieAnimationView ltPlayer;
        public TextView tvEvent;
        public TextView tvEventTime;
        public TextView tvLook;
        public TextView tvStatus;

        public T6Holder(@NonNull View view) {
            super(view);
            this.clParent = (LinearLayout) view.findViewById(R.id.cl_parent);
            this.cl = (CircleLayout) view.findViewById(R.id.cl);
            this.ivEventIcon = (ImageView) view.findViewById(R.id.iv_event_icon);
            this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
            this.tvLook = (TextView) view.findViewById(R.id.tv_look);
            this.tvEventTime = (TextView) view.findViewById(R.id.tv_event_time);
            this.tvStatus = (TextView) view.findViewById(R.id.tv_status);
            this.ivEventPic = (ImageView) view.findViewById(R.id.iv_event_pic);
            this.ltPlayer = (LottieAnimationView) view.findViewById(R.id.lt_player);
            view.setTag(this);
        }
    }
}

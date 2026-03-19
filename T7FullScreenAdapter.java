package com.petkit.android.activities.petkitBleDevice.t7.adp;

import android.annotation.SuppressLint;
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
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.petkitBleDevice.t6.mode.ShitInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetEvent;
import com.petkit.android.activities.petkitBleDevice.t6.widget.CircleLayout;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7EventInfo;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes5.dex */
public class T7FullScreenAdapter extends RecyclerView.Adapter<T7Holder> {
    public final FragmentActivity context;
    public List<T7EventInfo> list;
    public ItemClickListener listener;
    public final T7Record t7Record;
    public TimeZone timezone;

    public interface ItemClickListener {
        void lookShit(T7EventInfo t7EventInfo, int i);

        void startPlayVideo(T7EventInfo t7EventInfo, int i);
    }

    public void setListener(ItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    public T7FullScreenAdapter(FragmentActivity fragmentActivity, List<T7EventInfo> list, long j) {
        this.context = fragmentActivity;
        this.list = list;
        T7Record t7RecordById = T7DataUtils.getInstance().getT7RecordById(j);
        this.t7Record = t7RecordById;
        if (t7RecordById != null) {
            this.timezone = t7RecordById.getActualTimeZone();
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setList(List<T7EventInfo> list, int i) {
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
        List<T7EventInfo> list = this.list;
        if (list == null || list.size() < 1) {
            return 0;
        }
        for (T7EventInfo t7EventInfo : this.list) {
            if (i == 5) {
                if (t7EventInfo.getEventType() == 10) {
                    if (t7EventInfo.getSubContent().size() > 0 && t7EventInfo.getSubContent().get(0).getEventId().equals(str)) {
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

    @SuppressLint({"NotifyDataSetChanged"})
    public void deleteShit(String str, int i, int i2, String str2) {
        List<T7EventInfo> list = this.list;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T7EventInfo t7EventInfo : this.list) {
            if (i2 == 5) {
                if (t7EventInfo.getEventType() == 5) {
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
    public void clearList() {
        this.list = new ArrayList();
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public T7Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new T7Holder(LayoutInflater.from(this.context).inflate(R.layout.t6_full_screen_item_view, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final T7Holder t7Holder, @SuppressLint({"RecyclerView"}) final int i) {
        boolean z;
        String str;
        final T7EventInfo t7EventInfo = this.list.get(i);
        t7Holder.tvEvent.setTextColor(ContextCompat.getColor(this.context, R.color.white));
        t7Holder.tvEventTime.setTextColor(ContextCompat.getColor(this.context, R.color.alpha_40_white));
        long startTime = 0;
        if (t7EventInfo.getEventType() == 10) {
            if (t7EventInfo.getContent() != null) {
                startTime = t7EventInfo.getContent().getTimeIn();
            }
        } else if (t7EventInfo.getContent() != null) {
            startTime = t7EventInfo.getContent().getStartTime();
        }
        t7Holder.tvEventTime.setText(TimeUtils.getInstance().timeStampToTimeStringWithUnit(this.context, startTime, this.timezone));
        if (t7EventInfo.getEventType() == 5) {
            if (t7EventInfo.getContent() == null) {
                str = "";
                z = false;
            } else {
                boolean z2 = (t7EventInfo.getContent().getStartReason() == 0 && (t7EventInfo.getContent().getResult() == null || t7EventInfo.getContent().getResult().intValue() == 0)) ? false : true;
                String clearContent = T7Utils.getClearContent(this.context, t7EventInfo.getContent());
                if (t7EventInfo.getContent().getResult() != null && t7EventInfo.getContent().getResult().intValue() == 0) {
                    t7Holder.ivEventIcon.setImageResource(R.drawable.check_box_selected);
                } else {
                    t7Holder.ivEventIcon.setImageResource(R.drawable.d4s_state_failed);
                }
                z = z2;
                str = clearContent;
            }
            t7Holder.tvEvent.setText(str);
        } else {
            setPetInfo(t7Holder, t7EventInfo.getPetId(), t7EventInfo.getEventType(), t7EventInfo.getContent() != null ? t7EventInfo.getContent().getCount() : 0);
            z = false;
        }
        t7Holder.cl.setVisibility(z ? 8 : 0);
        t7Holder.ivEventIcon.setVisibility(z ? 0 : 8);
        final int eventStatus = getEventStatus(t7EventInfo);
        t7Holder.tvStatus.setVisibility(eventStatus > 0 ? 0 : 8);
        if (eventStatus == 1) {
            t7Holder.tvStatus.setText(this.context.getResources().getString(R.string.Image_expired));
        } else if (eventStatus == 2) {
            t7Holder.tvStatus.setText(this.context.getResources().getString(R.string.No_video));
        } else if (eventStatus == 3) {
            t7Holder.tvStatus.setText(this.context.getResources().getString(R.string.T6_toiletDetection_close));
        } else if (!this.list.get(i).getPreview().isEmpty()) {
            new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(t7EventInfo.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7FullScreenAdapter$$ExternalSyntheticLambda0
                @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                public final void onCachePath(String str2) {
                    T7FullScreenAdapter.lambda$onBindViewHolder$0(t7EventInfo, t7Holder, str2);
                }
            });
        } else {
            t7Holder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
        }
        t7Holder.clParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7FullScreenAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$1(i, eventStatus, view);
            }
        });
        if (t7EventInfo.getShitPictures() != null && t7EventInfo.getShitPictures().size() > 0) {
            if (t7EventInfo.getEventType() != 10 || t7EventInfo.getContent().getPetPh() > 0 || t7EventInfo.getContent().getSoftClear() == 1) {
                t7Holder.tvLook.setVisibility(0);
            } else {
                t7Holder.tvLook.setVisibility(8);
            }
        } else {
            t7Holder.tvLook.setVisibility(8);
        }
        t7Holder.tvLook.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7FullScreenAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$2(t7EventInfo, i, view);
            }
        });
    }

    public static /* synthetic */ void lambda$onBindViewHolder$0(T7EventInfo t7EventInfo, T7Holder t7Holder, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), t7EventInfo.getAesKey());
                if (fileDecryptImageFile != null) {
                    t7Holder.ivEventPic.setImageBitmap(T7Utils.getRotateBitmap(fileDecryptImageFile.getAbsolutePath(), t7EventInfo.getPreview()));
                } else {
                    t7Holder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
                }
                return;
            } catch (Exception unused) {
                t7Holder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
                return;
            }
        }
        t7Holder.ivEventPic.setImageResource(R.drawable.petkit_default_image);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$1(int i, int i2, View view) {
        if (this.listener != null) {
            if ((this.list.get(i).getEventType() != 5 || this.list.get(i).getContent() == null || this.list.get(i).getContent().getStartReason() == 0) && i2 == 0) {
                this.listener.startPlayVideo(this.list.get(i), i);
            }
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$2(T7EventInfo t7EventInfo, int i, View view) {
        Log.d("T7fullscreenAdapter", "lookShit: ");
        if (this.listener != null) {
            for (int i2 = 0; i2 < t7EventInfo.getShitPictures().size(); i2++) {
                t7EventInfo.getShitPictures().get(i2).setEventType(t7EventInfo.getEventType());
                t7EventInfo.getShitPictures().get(i2).setEventId(t7EventInfo.getEventId());
            }
            this.listener.lookShit(t7EventInfo, i);
        }
    }

    public boolean isExpireService(int i) {
        T7Record t7Record;
        boolean z = false;
        if (i < 0 || (t7Record = this.t7Record) == null) {
            return false;
        }
        boolean z2 = t7Record.getCloudProduct() == null || (this.t7Record.getCloudProduct().getWorkIndate() != null && this.list.get(i).getTimestamp() > Long.parseLong(this.t7Record.getCloudProduct().getWorkIndate()));
        if (this.t7Record.getServiceStatus() == 0 || (this.t7Record.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }

    public final void setPetInfo(T7Holder t7Holder, String str, int i, int i2) {
        Pet petById = UserInforUtils.getPetById(str);
        if (petById == null) {
            if (i == 10) {
                t7Holder.tvEvent.setText(this.context.getResources().getString(R.string.T4_history_record_pet_into_prompt_one));
                return;
            }
            if (i != 20) {
                t7Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_approaching_litter_box));
                return;
            } else if (i2 > 1) {
                t7Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_appeared_multi));
                return;
            } else {
                t7Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_appeared));
                return;
            }
        }
        String name = petById.getName();
        if (i == 10) {
            t7Holder.tvEvent.setText(name + this.context.getResources().getString(R.string.T6_toileted));
            return;
        }
        if (i != 20) {
            t7Holder.tvEvent.setText(this.context.getResources().getString(R.string.Cat_near, name));
        } else if (i2 > 1) {
            t7Holder.tvEvent.setText(this.context.getResources().getString(R.string.Appeared_multi, name));
        } else {
            t7Holder.tvEvent.setText(this.context.getResources().getString(R.string.Appeared, name));
        }
    }

    public int getEventStatus(T7EventInfo t7EventInfo) {
        if (t7EventInfo.getMediaApi().isEmpty() && t7EventInfo.getPreview().isEmpty()) {
            return 2;
        }
        if (t7EventInfo.getToiletDetection() == 0 && t7EventInfo.getEventType() == 10) {
            return 3;
        }
        if (t7EventInfo.getUpload() == 0) {
            return 2;
        }
        return System.currentTimeMillis() / 1000 > t7EventInfo.getExpire() ? 1 : 0;
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
    public void updatePetEvent(UpdatePetEvent updatePetEvent) {
        if (updatePetEvent == null) {
            return;
        }
        for (T7EventInfo t7EventInfo : this.list) {
            if (updatePetEvent.getEventId().equals(t7EventInfo.getEventId())) {
                t7EventInfo.setPetId(updatePetEvent.getPetId());
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public T7EventInfo getInfo(int i) {
        if (i < 0) {
            return null;
        }
        return this.list.get(i);
    }

    public List<T7EventInfo> getList() {
        return this.list;
    }

    public static class T7Holder extends RecyclerView.ViewHolder {
        public CircleLayout cl;
        public LinearLayout clParent;
        public ImageView ivEventIcon;
        public ImageView ivEventPic;
        public TextView tvEvent;
        public TextView tvEventTime;
        public TextView tvLook;
        public TextView tvStatus;

        public T7Holder(@NonNull View view) {
            super(view);
            this.clParent = (LinearLayout) view.findViewById(R.id.cl_parent);
            this.cl = (CircleLayout) view.findViewById(R.id.cl);
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

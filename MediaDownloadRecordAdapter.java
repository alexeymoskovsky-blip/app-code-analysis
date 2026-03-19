package com.petkit.android.activities.petkitBleDevice.download.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.petkitBleDevice.download.mode.MediaMsg;
import com.petkit.android.activities.petkitBleDevice.download.mode.VideoDownloadRecord;
import com.petkit.android.activities.petkitBleDevice.download.utils.VideoDownloadManager;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes4.dex */
public class MediaDownloadRecordAdapter extends RecyclerView.Adapter<MediaDownloadRecordViewHolder> {
    public List<VideoDownloadRecord> dataList;
    public boolean isEdit;
    public OnClickListener listener;
    public Context mContext;
    public List<Boolean> selectState = new ArrayList();

    public interface OnClickListener {
        void onSelectChange(int i);

        void onStartNewTask();

        void onViewClick(VideoDownloadRecord videoDownloadRecord, int i);
    }

    public List<Boolean> getSelectState() {
        return this.selectState;
    }

    public List<VideoDownloadRecord> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<VideoDownloadRecord> list) {
        this.dataList = list;
        this.selectState.clear();
        for (int i = 0; i < list.size(); i++) {
            this.selectState.add(Boolean.FALSE);
        }
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public void changeSelectState() {
        int i = 0;
        if (isSelectAll()) {
            while (i < this.selectState.size()) {
                this.selectState.set(i, Boolean.FALSE);
                notifyDataSetChanged();
                i++;
            }
            return;
        }
        while (i < this.selectState.size()) {
            this.selectState.set(i, Boolean.TRUE);
            notifyDataSetChanged();
            i++;
        }
    }

    public MediaDownloadRecordAdapter(Context context, boolean z, OnClickListener onClickListener) {
        EventBus.getDefault().register(this);
        this.listener = onClickListener;
        this.mContext = context;
        this.isEdit = z;
    }

    public void setEdit(boolean z) {
        this.isEdit = z;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public MediaDownloadRecordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MediaDownloadRecordViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_download_record_item, viewGroup, false));
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x0227  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull com.petkit.android.activities.petkitBleDevice.download.adapter.MediaDownloadRecordAdapter.MediaDownloadRecordViewHolder r9, @android.annotation.SuppressLint({"RecyclerView"}) int r10) {
        /*
            Method dump skipped, instruction units count: 644
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.download.adapter.MediaDownloadRecordAdapter.onBindViewHolder(com.petkit.android.activities.petkitBleDevice.download.adapter.MediaDownloadRecordAdapter$MediaDownloadRecordViewHolder, int):void");
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.MediaDownloadRecordAdapter$1 */
    public class AnonymousClass1 implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ int val$position;

        public AnonymousClass1(int i) {
            i = i;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            MediaDownloadRecordAdapter.this.selectState.set(i, Boolean.valueOf(z));
            if (MediaDownloadRecordAdapter.this.listener != null) {
                MediaDownloadRecordAdapter.this.listener.onSelectChange(i);
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.MediaDownloadRecordAdapter$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        final /* synthetic */ int val$position;

        public AnonymousClass2(int i) {
            i = i;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (MediaDownloadRecordAdapter.this.listener == null || i >= MediaDownloadRecordAdapter.this.dataList.size()) {
                return;
            }
            MediaDownloadRecordAdapter.this.listener.onViewClick((VideoDownloadRecord) MediaDownloadRecordAdapter.this.dataList.get(i), i);
        }
    }

    public boolean isSelectAll() {
        for (int i = 0; i < this.selectState.size(); i++) {
            if (!this.selectState.get(i).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasSelected() {
        for (int i = 0; i < this.selectState.size(); i++) {
            if (this.selectState.get(i).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataList.size();
    }

    public int getSelectedCount() {
        int i = 0;
        for (int i2 = 0; i2 < this.selectState.size(); i2++) {
            if (this.selectState.get(i2).booleanValue()) {
                i++;
            }
        }
        return i;
    }

    @Subscriber
    public void downloadState(MediaMsg mediaMsg) {
        PetkitLog.d("downloadState", "adapter downloadState:" + mediaMsg.getUrl());
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= this.dataList.size()) {
                break;
            }
            if (mediaMsg.getUrl().equals(this.dataList.get(i).getMediaApi())) {
                PetkitLog.d("onTask", "onTaskComplete:" + i);
                this.dataList.get(i).setState(mediaMsg.getState());
                notifyItemChanged(i);
                z = true;
                break;
            }
            i++;
        }
        if (mediaMsg.getState() == 3) {
            this.selectState.add(Boolean.FALSE);
        }
        if (z) {
            return;
        }
        this.dataList = VideoDownloadManager.getAllVideoNotCompleteDownloadRecord();
        notifyDataSetChanged();
    }

    public static class MediaDownloadRecordViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cbEdit;
        public ImageView ivDownloadImg;
        public ProgressBar pbDownload;
        public RelativeLayout rlRootRecordPanel;
        public TextView tvName;
        public TextView tvProgress;

        public MediaDownloadRecordViewHolder(View view) {
            super(view);
            this.rlRootRecordPanel = (RelativeLayout) view.findViewById(R.id.rl_root_record_panel);
            this.ivDownloadImg = (ImageView) view.findViewById(R.id.iv_download_img);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.pbDownload = (ProgressBar) view.findViewById(R.id.pb_download);
            this.tvProgress = (TextView) view.findViewById(R.id.tv_progress);
            this.cbEdit = (CheckBox) view.findViewById(R.id.cb_edit);
        }
    }
}

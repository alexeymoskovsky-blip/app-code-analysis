package com.petkit.android.activities.device.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.device.AiActivity;
import com.petkit.android.activities.device.mode.AiTaskInfo;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class AiTaskAdapter extends RecyclerView.Adapter<TaskHolder> {
    public int actStatus;
    public final List<AiTaskInfo> items;
    public String language;
    public int lastPlayPosition = -1;
    public OnTaskClickListener listener;
    public final AiActivity mContext;

    public interface OnTaskClickListener {
        void onClick(AiTaskInfo aiTaskInfo, int i);

        void play(AiTaskInfo aiTaskInfo, int i);
    }

    public void setListener(OnTaskClickListener onTaskClickListener) {
        this.listener = onTaskClickListener;
    }

    public AiTaskAdapter(AiActivity aiActivity, List<AiTaskInfo> list, int i) {
        this.mContext = aiActivity;
        this.items = list;
        this.actStatus = i;
        String stringSF = DataHelper.getStringSF(aiActivity, Consts.SHARED_SETTING_LANGUAGE);
        this.language = stringSF;
        if (TextUtils.isEmpty(stringSF)) {
            this.language = "zh_CN";
        }
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setItems(List<AiTaskInfo> list) {
        this.items.clear();
        this.items.addAll(list);
        notifyDataSetChanged();
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setActStatus(int i) {
        this.actStatus = i;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TaskHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_sub_task_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"NotifyDataSetChanged"})
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, @SuppressLint({"RecyclerView"}) final int i) {
        final AiTaskInfo aiTaskInfo = this.items.get(i);
        taskHolder.tvTitle.setText(aiTaskInfo.getTitle());
        taskHolder.tvContent.setText(aiTaskInfo.getDesc());
        int i2 = this.actStatus;
        if (i2 == 1) {
            taskHolder.tvSub.setVisibility(8);
            taskHolder.ivSub.setVisibility(8);
            if (this.language.equals("zh_CN")) {
                taskHolder.ivMission.setImageResource(R.drawable.img_ai_end);
            } else {
                taskHolder.ivMission.setImageResource(R.drawable.img_ai_end_en);
            }
        } else if (i2 == 2) {
            taskHolder.tvSub.setVisibility(0);
            taskHolder.tvSub.setText("        ");
            taskHolder.ivSub.setVisibility(0);
            if (this.language.equals("zh_CN")) {
                taskHolder.ivMission.setImageResource(R.drawable.img_ai_complete);
            } else {
                taskHolder.ivMission.setImageResource(R.drawable.img_ai_complete_en);
            }
        } else if (i2 == 3) {
            taskHolder.tvSub.setVisibility(0);
            taskHolder.tvSub.setText("        ");
            taskHolder.ivSub.setVisibility(0);
            if (this.language.equals("zh_CN")) {
                taskHolder.ivMission.setImageResource(R.drawable.img_ai_complete);
            } else {
                taskHolder.ivMission.setImageResource(R.drawable.img_ai_complete_en);
            }
        } else {
            taskHolder.tvSub.setVisibility(0);
            taskHolder.ivSub.setVisibility(0);
            if (aiTaskInfo.getStatus() != null) {
                int iIntValue = aiTaskInfo.getStatus().intValue();
                if (iIntValue == 0) {
                    taskHolder.tvSub.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_bind_blue));
                    taskHolder.tvSub.setText(this.mContext.getResources().getString(R.string.Under_review));
                    if (this.language.equals("zh_CN")) {
                        taskHolder.ivMission.setImageResource(R.drawable.img_ai_not_complete);
                    } else {
                        taskHolder.ivMission.setImageResource(R.drawable.img_ai_not_complete_en);
                    }
                } else if (iIntValue == 1) {
                    taskHolder.tvSub.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_bind_blue));
                    taskHolder.tvSub.setText("        ");
                    if (this.language.equals("zh_CN")) {
                        taskHolder.ivMission.setImageResource(R.drawable.img_ai_complete);
                    } else {
                        taskHolder.ivMission.setImageResource(R.drawable.img_ai_complete_en);
                    }
                } else if (iIntValue == 2) {
                    if (this.language.equals("zh_CN")) {
                        taskHolder.ivMission.setImageResource(R.drawable.img_ai_not_complete);
                    } else {
                        taskHolder.ivMission.setImageResource(R.drawable.img_ai_not_complete_en);
                    }
                    taskHolder.tvSub.setText(this.mContext.getResources().getString(R.string.Review_failed));
                    taskHolder.tvSub.setTextColor(ContextCompat.getColor(this.mContext, R.color.orange_high_light));
                }
            } else {
                taskHolder.tvSub.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_bind_blue));
                taskHolder.tvSub.setText(this.mContext.getResources().getString(R.string.Ai_upload));
                if (this.language.equals("zh_CN")) {
                    taskHolder.ivMission.setImageResource(R.drawable.img_ai_not_complete);
                } else {
                    taskHolder.ivMission.setImageResource(R.drawable.img_ai_not_complete_en);
                }
            }
        }
        if (aiTaskInfo.getExampleType() == 1) {
            new PetKitTask(this.mContext, taskHolder.ivAi).execute(aiTaskInfo.getExample());
            taskHolder.ivAiPlay.setVisibility(0);
        } else {
            ((BaseApplication) this.mContext.getApplication()).getAppComponent().imageLoader().loadImage(this.mContext, GlideImageConfig.builder().url(aiTaskInfo.getExample()).imageView(taskHolder.ivAi).errorPic(R.drawable.default_image).build());
            taskHolder.ivAiPlay.setVisibility(8);
        }
        taskHolder.tvSub.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.device.adapter.AiTaskAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$0(aiTaskInfo, i, view);
            }
        });
        taskHolder.ivSub.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.device.adapter.AiTaskAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$1(aiTaskInfo, i, view);
            }
        });
        taskHolder.ivAiPlay.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.device.adapter.AiTaskAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$2(aiTaskInfo, i, view);
            }
        });
    }

    public final /* synthetic */ void lambda$onBindViewHolder$0(AiTaskInfo aiTaskInfo, int i, View view) {
        OnTaskClickListener onTaskClickListener;
        if (this.actStatus == 2 || (onTaskClickListener = this.listener) == null) {
            return;
        }
        onTaskClickListener.onClick(aiTaskInfo, i);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$1(AiTaskInfo aiTaskInfo, int i, View view) {
        OnTaskClickListener onTaskClickListener;
        if (this.actStatus == 2 || (onTaskClickListener = this.listener) == null) {
            return;
        }
        onTaskClickListener.onClick(aiTaskInfo, i);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$2(AiTaskInfo aiTaskInfo, int i, View view) {
        OnTaskClickListener onTaskClickListener = this.listener;
        if (onTaskClickListener != null) {
            onTaskClickListener.play(aiTaskInfo, i);
        }
    }

    public void setLastPlayPosition(int i) {
        this.lastPlayPosition = i;
    }

    public int getLastPlayPosition() {
        return this.lastPlayPosition;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    public static class TaskHolder extends RecyclerView.ViewHolder {
        public ImageView ivAi;
        public ImageView ivAiPlay;
        public ImageView ivMission;
        public ImageView ivSub;
        public RelativeLayout rlVideo;
        public TextView tvContent;
        public TextView tvExample;
        public TextView tvSub;
        public TextView tvTitle;

        public TaskHolder(View view) {
            super(view);
            this.tvSub = (TextView) view.findViewById(R.id.tv_sub);
            this.ivSub = (ImageView) view.findViewById(R.id.iv_voice_arrow);
            this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.tvContent = (TextView) view.findViewById(R.id.tv_content);
            this.ivAi = (ImageView) view.findViewById(R.id.iv_ai);
            this.rlVideo = (RelativeLayout) view.findViewById(R.id.rl_video);
            this.ivAiPlay = (ImageView) view.findViewById(R.id.iv_ai_play);
            this.ivMission = (ImageView) view.findViewById(R.id.iv_mission);
            this.tvExample = (TextView) view.findViewById(R.id.tv_example);
            view.setTag(this);
        }
    }

    public final Bitmap getFirstBitmap(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                mediaMetadataRetriever.setDataSource(str);
                Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L);
                try {
                    mediaMetadataRetriever.release();
                    return frameAtTime;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Throwable th) {
                try {
                    mediaMetadataRetriever.release();
                    throw th;
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            try {
                mediaMetadataRetriever.release();
                return null;
            } catch (IOException e4) {
                throw new RuntimeException(e4);
            }
        }
    }

    public class PetKitTask extends AsyncTask<String, Integer, Bitmap> {
        public final ImageView imageView;
        public final WeakReference<AiActivity> taskActivity;

        @Override // android.os.AsyncTask
        public void onPreExecute() {
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... numArr) {
        }

        public PetKitTask(AiActivity aiActivity, ImageView imageView) {
            this.taskActivity = new WeakReference<>(aiActivity);
            this.imageView = imageView;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            AiActivity aiActivity = this.taskActivity.get();
            if (aiActivity == null || aiActivity.isFinishing() || bitmap == null) {
                return;
            }
            this.imageView.setImageBitmap(bitmap);
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(String... strArr) {
            return AiTaskAdapter.this.getFirstBitmap(CommonUtils.getHttpsString(strArr[0]));
        }
    }
}

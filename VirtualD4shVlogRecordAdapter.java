package com.petkit.android.activities.petkitBleDevice.d4sh.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.BaseAnimaRecyclerAdapter;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shVlogActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.VlogUtils;
import com.petkit.android.activities.petkitBleDevice.mode.HighlightRecord;
import com.petkit.android.activities.petkitBleDevice.vlog.VlogMarkRecord;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class VirtualD4shVlogRecordAdapter extends BaseAnimaRecyclerAdapter<HighlightRecord> {
    public static final int VIEW_TYPE_EMPTY = 1;
    public static final int VIEW_TYPE_NO_EMPTY = 2;
    public D4shRecord d4shRecord;
    public BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener onDailyHighlightItemClickListener;
    public boolean refreshLoadingStatus;
    public int typeCode;

    public interface OnDailyHighlightItemClickListener {
        void onMarkVlogClick(HighlightRecord highlightRecord);

        void onPlayBtnClick(HighlightRecord highlightRecord);
    }

    public VirtualD4shVlogRecordAdapter(Activity activity, D4shRecord d4shRecord) {
        super(activity);
        this.refreshLoadingStatus = true;
        this.d4shRecord = d4shRecord;
    }

    public void setTypeCode(int i) {
        this.typeCode = i;
    }

    public void setOnDailyHighlightItemClickListener(BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener onDailyHighlightItemClickListener) {
        this.onDailyHighlightItemClickListener = onDailyHighlightItemClickListener;
    }

    public void setRefreshLoadingStatus(boolean z) {
        this.refreshLoadingStatus = z;
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public void showData(RecyclerView.ViewHolder viewHolder, int i, List list) {
        if (viewHolder instanceof HighlightRecordViewHolder) {
            HighlightRecordViewHolder highlightRecordViewHolder = (HighlightRecordViewHolder) viewHolder;
            final HighlightRecord highlightRecord = (HighlightRecord) list.get(i);
            boolean z = System.currentTimeMillis() > ((long) highlightRecord.getExpired()) * 1000 && highlightRecord.getExpired() != 0;
            if (highlightRecord.getCreatedAt() < 0) {
                highlightRecordViewHolder.tvDate.setVisibility(8);
            } else {
                highlightRecordViewHolder.tvDate.setText(DateUtil.getShortDateStrByTimestamp(this.mActivity, highlightRecord.getCreatedAt() * 1000));
                highlightRecordViewHolder.tvDate.setVisibility(0);
            }
            highlightRecordViewHolder.vlogPromptView.setVisibility(8);
            highlightRecordViewHolder.vlogPromptBtn.setVisibility(8);
            highlightRecordViewHolder.maskView.setVisibility(8);
            if (highlightRecord.getDisabled() == 1) {
                highlightRecordViewHolder.ivPreview.setVisibility(0);
                highlightRecordViewHolder.ivPreview.setImageResource(R.drawable.solid_dark_black_8);
                highlightRecordViewHolder.highLightPlayImageView.setVisibility(8);
                highlightRecordViewHolder.maskView.setVisibility(0);
                highlightRecordViewHolder.maskView.setBackground(this.mActivity.getDrawable(R.drawable.d4sh_mask_style));
                highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                highlightRecordViewHolder.vlogPromptBtn.setVisibility(8);
                highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.Video_part_deleted));
                return;
            }
            if (highlightRecord.getDisabled() == 2) {
                highlightRecordViewHolder.ivPreview.setVisibility(0);
                highlightRecordViewHolder.ivPreview.setImageResource(R.drawable.solid_dark_black_8);
                highlightRecordViewHolder.highLightPlayImageView.setVisibility(8);
                highlightRecordViewHolder.maskView.setVisibility(0);
                highlightRecordViewHolder.maskView.setBackground(this.mActivity.getDrawable(R.drawable.d4sh_mask_style));
                highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                highlightRecordViewHolder.vlogPromptBtn.setVisibility(8);
                highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.Vlog_off_prompt));
                return;
            }
            if (!TextUtils.isEmpty(highlightRecord.getVideoUrl())) {
                highlightRecordViewHolder.maskView.setVisibility(0);
                highlightRecordViewHolder.maskView.setBackground(this.mActivity.getDrawable(R.drawable.d4sh_mask_gradient_style));
                if (!TextUtils.isEmpty(highlightRecord.getPreview())) {
                    ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(highlightRecord.getPreview()).secretKey(highlightRecord.getAesKey()).errorPic(R.drawable.solid_dark_black_8).imageView(highlightRecordViewHolder.ivPreview).transformation(new GlideRoundTransform(CommonUtils.getAppContext(), (int) DeviceUtils.dpToPixel(CommonUtils.getAppContext(), 8.0f))).build());
                }
                highlightRecordViewHolder.ivPreview.setVisibility(0);
                highlightRecordViewHolder.highLightPlayImageView.setVisibility(8);
                DateUtil.getShortDateStrByTimestamp(this.mActivity, (highlightRecord.getCreatedAt() * 1000) + 604800000);
                highlightRecordViewHolder.ivPreview.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.VirtualD4shVlogRecordAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showData$0(highlightRecord, view);
                    }
                });
                return;
            }
            highlightRecordViewHolder.ivPreview.setVisibility(0);
            highlightRecordViewHolder.ivPreview.setImageResource(R.drawable.solid_dark_black_8);
            highlightRecordViewHolder.highLightPlayImageView.setVisibility(8);
            highlightRecordViewHolder.maskView.setVisibility(0);
            highlightRecordViewHolder.maskView.setBackground(this.mActivity.getDrawable(R.drawable.d4sh_mask_style));
            VlogMarkRecord vlogMarkRecord = VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord.getId());
            if (vlogMarkRecord != null) {
                if (vlogMarkRecord.getStatus() == 1) {
                    highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                    highlightRecordViewHolder.vlogPromptTextView.setVisibility(0);
                    highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.In_the_making, vlogMarkRecord.getProgress() + "%"));
                    return;
                }
                if (vlogMarkRecord.getStatus() == 2) {
                    highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                    highlightRecordViewHolder.vlogPromptBtn.setVisibility(0);
                    highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.Production_failure));
                    highlightRecordViewHolder.vlogPromptBtn.setText(this.mActivity.getString(R.string.Retry));
                    highlightRecordViewHolder.vlogPromptBtn.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.VirtualD4shVlogRecordAdapter$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$showData$1(highlightRecord, view);
                        }
                    });
                    return;
                }
                if (vlogMarkRecord.getStatus() == 4) {
                    highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                    highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.Vlog_make_waiting));
                    return;
                }
                return;
            }
            if (!TextUtils.isEmpty(highlightRecord.getPreview())) {
                ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(highlightRecord.getPreview()).secretKey(highlightRecord.getAesKey()).imageView(highlightRecordViewHolder.ivPreview).errorPic(R.drawable.solid_dark_black_8).transformation(new GlideRoundTransform(CommonUtils.getAppContext(), (int) DeviceUtils.dpToPixel(CommonUtils.getAppContext(), 8.0f))).build());
            }
            if (z) {
                highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                highlightRecordViewHolder.vlogPromptBtn.setVisibility(8);
                highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.Footage_has_expired));
                return;
            } else {
                if (highlightRecord.getId() != 0) {
                    highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                    highlightRecordViewHolder.vlogPromptBtn.setVisibility(0);
                    highlightRecordViewHolder.vlogPromptBtn.setText(this.mActivity.getString(R.string.Start_production));
                    highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.Vlog_fragment_is_ready));
                    highlightRecordViewHolder.vlogPromptBtn.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.VirtualD4shVlogRecordAdapter$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$showData$2(highlightRecord, view);
                        }
                    });
                    return;
                }
                highlightRecordViewHolder.vlogPromptView.setVisibility(0);
                highlightRecordViewHolder.vlogPromptBtn.setVisibility(8);
                highlightRecordViewHolder.vlogPromptTextView.setText(this.mActivity.getString(R.string.Video_part_too_little));
                return;
            }
        }
        if (viewHolder instanceof HighlightEmptyViewHolder) {
            String str = this.mActivity.getString(R.string.Highlight_empty_prompt) + " ";
            SpannableString spannableString = new SpannableString(str);
            int length = str.length();
            Drawable drawable = ContextCompat.getDrawable(this.mActivity, R.drawable.gray_flag);
            drawable.setBounds(0, 6, drawable.getIntrinsicWidth(), drawable.getIntrinsicWidth());
            spannableString.setSpan(new ImageSpan(this.mActivity, R.drawable.gray_flag), length - 1, length, 33);
            HighlightEmptyViewHolder highlightEmptyViewHolder = (HighlightEmptyViewHolder) viewHolder;
            highlightEmptyViewHolder.tvEmptyContext.setText(spannableString);
            highlightEmptyViewHolder.tvEmptyContext.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.adapter.VirtualD4shVlogRecordAdapter$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showData$3(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showData$0(HighlightRecord highlightRecord, View view) {
        BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener onDailyHighlightItemClickListener = this.onDailyHighlightItemClickListener;
        if (onDailyHighlightItemClickListener != null) {
            onDailyHighlightItemClickListener.onPlayBtnClick(highlightRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showData$1(HighlightRecord highlightRecord, View view) {
        startVlogGenerate(highlightRecord);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showData$2(HighlightRecord highlightRecord, View view) {
        startVlogGenerate(highlightRecord);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showData$3(View view) {
        Activity activity = this.mActivity;
        activity.startActivity(D4shVlogActivity.newIntent(activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), -1));
    }

    private void startVlogGenerate(HighlightRecord highlightRecord) {
        if (CommonUtils.checkPermission(this.mActivity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            VlogMarkRecord markingRecord = VlogUtils.getMarkingRecord(CommonUtils.getCurrentUserId());
            if (markingRecord != null && markingRecord.getVlogId() != highlightRecord.getId()) {
                VlogUtils.saveVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord.getId(), 4, 0);
            } else {
                VlogUtils.saveVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord.getId(), 1, 0);
            }
            BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener onDailyHighlightItemClickListener = this.onDailyHighlightItemClickListener;
            if (onDailyHighlightItemClickListener != null) {
                onDailyHighlightItemClickListener.onMarkVlogClick(highlightRecord);
            }
            notifyDataSetChanged();
            return;
        }
        Activity activity = this.mActivity;
        activity.startActivity(PermissionDialogActivity.newIntent(activity, activity.getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
    }

    private void removeUploadFailedVisible(ViewGroup viewGroup) {
        if (viewGroup.getChildCount() == 4) {
            viewGroup.removeViewAt(3);
        }
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<BaseCard> arrayList = this.mItemDataList;
        if (arrayList == 0 || arrayList.size() == 0) {
            return 1;
        }
        return super.getItemCount();
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public View createView(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_daily_highlight_empty, viewGroup, false);
        }
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_daily_highlight_virtual, viewGroup, false);
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public RecyclerView.ViewHolder createViewHolder(View view, int i) {
        if (i == 1) {
            return new HighlightEmptyViewHolder(view);
        }
        return new HighlightRecordViewHolder(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        ArrayList<BaseCard> arrayList = this.mItemDataList;
        return (arrayList == 0 || arrayList.size() == 0) ? 1 : 2;
    }

    public static class HighlightRecordViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout contentView;
        public ImageView highLightPlayImageView;
        public ImageView ivPreview;
        public View maskView;
        public TextView tvDate;
        public TextView vlogPromptBtn;
        public TextView vlogPromptTextView;
        public LinearLayout vlogPromptView;

        public HighlightRecordViewHolder(@NonNull View view) {
            super(view);
            this.tvDate = (TextView) view.findViewById(R.id.tv_date);
            this.ivPreview = (ImageView) view.findViewById(R.id.iv_preview);
            this.highLightPlayImageView = (ImageView) view.findViewById(R.id.high_light_play_image_view);
            this.contentView = (RelativeLayout) view.findViewById(R.id.content_view);
            this.vlogPromptView = (LinearLayout) view.findViewById(R.id.vlog_prompt_view);
            this.vlogPromptTextView = (TextView) view.findViewById(R.id.vlog_prompt_text_view);
            this.vlogPromptBtn = (TextView) view.findViewById(R.id.vlog_prompt_btn);
            this.maskView = view.findViewById(R.id.mask_view);
        }
    }

    public static class HighlightEmptyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmptyContext;

        public HighlightEmptyViewHolder(@NonNull View view) {
            super(view);
            this.tvEmptyContext = (TextView) view.findViewById(R.id.tv_empty_context);
        }
    }
}

package com.petkit.android.activities.petkitBleDevice.t6.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
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
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.base.adapter.BaseAnimaRecyclerAdapter;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shVlogActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.VlogUtils;
import com.petkit.android.activities.petkitBleDevice.hg.widget.NewRoundImageview;
import com.petkit.android.activities.petkitBleDevice.mode.HighlightRecord;
import com.petkit.android.activities.petkitBleDevice.t6.T6VlogActivity;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.vlog.VlogMarkRecord;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class T6VlogRecordAdapter extends BaseAnimaRecyclerAdapter<HighlightRecord> {
    public static final int VIEW_TYPE_EMPTY = 1;
    public static final int VIEW_TYPE_NO_EMPTY = 2;
    public int deviceType;
    public BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener onDailyHighlightItemClickListener;
    public boolean refreshLoadingStatus;
    public T6Record t6Record;

    public interface OnDailyHighlightItemClickListener {
        void onMarkVlogClick(HighlightRecord highlightRecord);

        void onPlayBtnClick(HighlightRecord highlightRecord);
    }

    public T6VlogRecordAdapter(Activity activity, T6Record t6Record, int i) {
        super(activity);
        this.refreshLoadingStatus = true;
        this.t6Record = t6Record;
        this.deviceType = i;
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
            final HighlightRecordViewHolder highlightRecordViewHolder = (HighlightRecordViewHolder) viewHolder;
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
                    new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(highlightRecord.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter$$ExternalSyntheticLambda0
                        @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                        public final void onCachePath(String str) {
                            T6VlogRecordAdapter.lambda$showData$0(highlightRecord, highlightRecordViewHolder, str);
                        }
                    });
                }
                highlightRecordViewHolder.ivPreview.setVisibility(0);
                highlightRecordViewHolder.highLightPlayImageView.setVisibility(8);
                DateUtil.getShortDateStrByTimestamp(this.mActivity, (highlightRecord.getCreatedAt() * 1000) + 604800000);
                highlightRecordViewHolder.ivPreview.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showData$1(highlightRecord, view);
                    }
                });
                return;
            }
            highlightRecordViewHolder.ivPreview.setVisibility(0);
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
                    highlightRecordViewHolder.vlogPromptBtn.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$showData$2(highlightRecord, view);
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
                new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(highlightRecord.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter$$ExternalSyntheticLambda3
                    @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                    public final void onCachePath(String str) {
                        T6VlogRecordAdapter.lambda$showData$3(highlightRecord, highlightRecordViewHolder, str);
                    }
                });
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
                    highlightRecordViewHolder.vlogPromptBtn.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$showData$4(highlightRecord, view);
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
            String str = this.mActivity.getString(R.string.Highlight_empty_prompt) + "  ";
            SpannableString spannableString = new SpannableString(str);
            int length = str.length();
            spannableString.setSpan(new ImageSpan(this.mActivity, R.drawable.gray_flag, 0), length - 1, length, 18);
            HighlightEmptyViewHolder highlightEmptyViewHolder = (HighlightEmptyViewHolder) viewHolder;
            highlightEmptyViewHolder.tvEmptyContext.setText(spannableString);
            highlightEmptyViewHolder.tvEmptyContext.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showData$5(view);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$showData$0(HighlightRecord highlightRecord, HighlightRecordViewHolder highlightRecordViewHolder, String str) {
        if (str != null) {
            highlightRecordViewHolder.ivPreview.setImageBitmap(CommonUtil.bimapSquareRound(170, T6Utils.getRotateBitmap(ImageUtils.decryptImageFile(new File(str), highlightRecord.getAesKey()).getAbsolutePath(), highlightRecord.getPreview())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showData$1(HighlightRecord highlightRecord, View view) {
        BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener onDailyHighlightItemClickListener = this.onDailyHighlightItemClickListener;
        if (onDailyHighlightItemClickListener != null) {
            onDailyHighlightItemClickListener.onPlayBtnClick(highlightRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showData$2(HighlightRecord highlightRecord, View view) {
        startVlogGenerate(highlightRecord);
    }

    public static /* synthetic */ void lambda$showData$3(HighlightRecord highlightRecord, HighlightRecordViewHolder highlightRecordViewHolder, String str) {
        if (str != null) {
            highlightRecordViewHolder.ivPreview.setImageBitmap(CommonUtil.bimapSquareRound(170, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), highlightRecord.getAesKey()).getAbsolutePath())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showData$4(HighlightRecord highlightRecord, View view) {
        startVlogGenerate(highlightRecord);
    }

    public final /* synthetic */ void lambda$showData$5(View view) {
        switch (this.deviceType) {
            case 25:
            case 26:
                Activity activity = this.mActivity;
                activity.startActivity(D4shVlogActivity.newIntent(activity, this.t6Record.getDeviceId(), this.deviceType, -1));
                break;
            case 27:
                Activity activity2 = this.mActivity;
                activity2.startActivity(T6VlogActivity.newIntent(activity2, this.t6Record.getDeviceId(), this.deviceType, -1));
                break;
        }
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
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_t6_daily_highlight, viewGroup, false);
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
        public NewRoundImageview ivPreview;
        public View maskView;
        public TextView tvDate;
        public TextView vlogPromptBtn;
        public TextView vlogPromptTextView;
        public LinearLayout vlogPromptView;

        public HighlightRecordViewHolder(@NonNull View view) {
            super(view);
            this.tvDate = (TextView) view.findViewById(R.id.tv_date);
            this.ivPreview = (NewRoundImageview) view.findViewById(R.id.iv_preview);
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

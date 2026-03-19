package com.petkit.android.activities.petkitBleDevice.download.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.arialyy.aria.core.Aria;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.petkitBleDevice.download.mode.AlbumFileMode;
import com.petkit.android.activities.petkitBleDevice.download.mode.AlbumMode;
import com.petkit.android.activities.petkitBleDevice.download.mode.AlbumTimeMode;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class LocalAlbumPhotoAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public List<AlbumMode> dataList;
    public SimpleDateFormat dateFormat;
    public boolean isEdit;
    public OnClickListener listener;
    public Context mContext;
    public List<Boolean> selectState = new ArrayList();

    public interface OnClickListener {
        void onSelectChange(int i);

        void onViewClick(AlbumFileMode albumFileMode, int i);
    }

    public List<Boolean> getSelectState() {
        return this.selectState;
    }

    public List<AlbumMode> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<AlbumMode> list) {
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

    public LocalAlbumPhotoAdapter(Context context, boolean z, OnClickListener onClickListener) {
        String str;
        Aria.download(this).register();
        this.listener = onClickListener;
        this.mContext = context;
        this.isEdit = z;
        Locale languageLocale = DeviceUtils.getLanguageLocale(BaseApplication.context);
        if (languageLocale != null) {
            if (languageLocale.getLanguage().equals("zh")) {
                str = "MM" + context.getResources().getString(R.string.Month_short) + "dd" + context.getResources().getString(R.string.Date_short);
            } else {
                str = "MM/dd";
            }
            try {
                this.dateFormat = new SimpleDateFormat(str, languageLocale);
                return;
            } catch (Exception unused) {
                this.dateFormat = new SimpleDateFormat("MM" + context.getResources().getString(R.string.Month_short) + "dd" + context.getResources().getString(R.string.Date_short));
                return;
            }
        }
        this.dateFormat = new SimpleDateFormat("MM" + context.getResources().getString(R.string.Month_short) + "dd" + context.getResources().getString(R.string.Date_short));
    }

    public LocalAlbumPhotoAdapter(Context context, OnClickListener onClickListener) {
        String str;
        Aria.download(this).register();
        this.listener = onClickListener;
        this.mContext = context;
        Locale languageLocale = DeviceUtils.getLanguageLocale(BaseApplication.context);
        if (languageLocale != null) {
            if (languageLocale.getLanguage().equals("zh")) {
                str = "MM" + context.getResources().getString(R.string.Month_short) + "dd" + context.getResources().getString(R.string.Date_short);
            } else {
                str = "MM/dd";
            }
            this.dateFormat = new SimpleDateFormat(str, languageLocale);
            return;
        }
        this.dateFormat = new SimpleDateFormat("MM" + context.getResources().getString(R.string.Month_short) + "dd" + context.getResources().getString(R.string.Date_short));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new AlbumTimeViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_local_album_item, viewGroup, false));
        }
        return new AlbumContentViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_local_album_content_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, @SuppressLint({"RecyclerView"}) int i) {
        if (getItemViewType(i) == 1) {
            ((AlbumTimeViewHolder) baseViewHolder).tvTitle.setText(this.dateFormat.format(new Date(((AlbumTimeMode) this.dataList.get(i)).getTime())));
            return;
        }
        AlbumFileMode albumFileMode = (AlbumFileMode) this.dataList.get(i);
        if (albumFileMode.getFile().getAbsolutePath().contains("T_27") || albumFileMode.getFile().getAbsolutePath().contains("T_21")) {
            ((AlbumContentViewHolder) baseViewHolder).ivContent.setImageBitmap(CommonUtil.bimapSquareRound(170, BitmapFactory.decodeFile(albumFileMode.getFile().getAbsolutePath())));
        } else {
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(albumFileMode.getFile().getAbsolutePath()).scaleType(ImageView.ScaleType.CENTER_CROP).imageView(((AlbumContentViewHolder) baseViewHolder).ivContent).build());
        }
        AlbumContentViewHolder albumContentViewHolder = (AlbumContentViewHolder) baseViewHolder;
        ViewGroup.LayoutParams layoutParams = albumContentViewHolder.rlRoot.getLayoutParams();
        DisplayMetrics displayMetrics = BaseApplication.displayMetrics;
        layoutParams.height = (int) (Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) / 3.0f);
        albumContentViewHolder.rlRoot.setLayoutParams(layoutParams);
        albumContentViewHolder.cbSelect.setVisibility(this.isEdit ? 0 : 8);
        albumContentViewHolder.cbSelect.setChecked(this.selectState.get(i).booleanValue());
        albumContentViewHolder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumPhotoAdapter.1
            final /* synthetic */ int val$position;

            public AnonymousClass1(int i2) {
                i = i2;
            }

            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (LocalAlbumPhotoAdapter.this.listener != null) {
                    LocalAlbumPhotoAdapter.this.selectState.set(i, Boolean.valueOf(z));
                    LocalAlbumPhotoAdapter.this.listener.onSelectChange(i);
                }
            }
        });
        albumContentViewHolder.rlRoot.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumPhotoAdapter.2
            final /* synthetic */ BaseViewHolder val$holder;
            final /* synthetic */ int val$position;

            public AnonymousClass2(BaseViewHolder baseViewHolder2, int i2) {
                baseViewHolder = baseViewHolder2;
                i = i2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!LocalAlbumPhotoAdapter.this.isEdit) {
                    if (LocalAlbumPhotoAdapter.this.listener != null) {
                        LocalAlbumPhotoAdapter.this.listener.onViewClick((AlbumFileMode) LocalAlbumPhotoAdapter.this.dataList.get(i), i);
                    }
                } else {
                    ((AlbumContentViewHolder) baseViewHolder).cbSelect.setChecked(!((AlbumContentViewHolder) r3).cbSelect.isChecked());
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumPhotoAdapter$1 */
    public class AnonymousClass1 implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ int val$position;

        public AnonymousClass1(int i2) {
            i = i2;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (LocalAlbumPhotoAdapter.this.listener != null) {
                LocalAlbumPhotoAdapter.this.selectState.set(i, Boolean.valueOf(z));
                LocalAlbumPhotoAdapter.this.listener.onSelectChange(i);
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumPhotoAdapter$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        final /* synthetic */ BaseViewHolder val$holder;
        final /* synthetic */ int val$position;

        public AnonymousClass2(BaseViewHolder baseViewHolder2, int i2) {
            baseViewHolder = baseViewHolder2;
            i = i2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (!LocalAlbumPhotoAdapter.this.isEdit) {
                if (LocalAlbumPhotoAdapter.this.listener != null) {
                    LocalAlbumPhotoAdapter.this.listener.onViewClick((AlbumFileMode) LocalAlbumPhotoAdapter.this.dataList.get(i), i);
                }
            } else {
                ((AlbumContentViewHolder) baseViewHolder).cbSelect.setChecked(!((AlbumContentViewHolder) r3).cbSelect.isChecked());
            }
        }
    }

    public int getSelectedCount() {
        int i = 0;
        for (int i2 = 0; i2 < this.selectState.size(); i2++) {
            if ((this.dataList.get(i2) instanceof AlbumFileMode) && this.selectState.get(i2).booleanValue()) {
                i++;
            }
        }
        return i;
    }

    public boolean isSelectAll() {
        for (int i = 0; i < this.selectState.size(); i++) {
            if ((this.dataList.get(i) instanceof AlbumFileMode) && !this.selectState.get(i).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasSelected() {
        for (int i = 0; i < this.selectState.size(); i++) {
            if ((this.dataList.get(i) instanceof AlbumFileMode) && this.selectState.get(i).booleanValue()) {
                return true;
            }
        }
        return false;
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

    public void setEdit(boolean z) {
        this.isEdit = z;
        if (!z) {
            for (int i = 0; i < this.dataList.size(); i++) {
                this.selectState.set(i, Boolean.FALSE);
            }
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataList.size();
    }

    public static class AlbumContentViewHolder extends BaseViewHolder {
        public CheckBox cbSelect;
        public ImageView ivContent;
        public RelativeLayout rlRoot;
        public TextView tvTime;

        public AlbumContentViewHolder(View view) {
            super(view);
            this.cbSelect = (CheckBox) view.findViewById(R.id.cb_select);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.ivContent = (ImageView) view.findViewById(R.id.iv_content);
            this.rlRoot = (RelativeLayout) view.findViewById(R.id.rl_album_root);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.dataList.get(i) instanceof AlbumTimeMode ? 1 : 2;
    }

    public static class AlbumTimeViewHolder extends BaseViewHolder {
        public TextView tvTitle;

        public AlbumTimeViewHolder(View view) {
            super(view);
            this.tvTitle = (TextView) view.findViewById(R.id.tv_album_title);
        }
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View view) {
            super(view);
        }
    }
}

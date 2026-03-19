package com.petkit.android.activities.petkitBleDevice.download.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaMetadataRetriever;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.sunnysuperman.commons.utils.EncryptUtil;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.petkitBleDevice.download.mode.AlbumFileMode;
import com.petkit.android.activities.petkitBleDevice.download.mode.AlbumMode;
import com.petkit.android.activities.petkitBleDevice.download.mode.AlbumTimeMode;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import com.wode369.videocroplibrary.features.trim.VideoTrimmerUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class LocalAlbumAdapter extends RecyclerView.Adapter<BaseViewHolder> {
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

    public void setEdit(boolean z) {
        this.isEdit = z;
        if (!z) {
            for (int i = 0; i < this.dataList.size(); i++) {
                this.selectState.set(i, Boolean.FALSE);
            }
        }
        notifyDataSetChanged();
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public LocalAlbumAdapter(Context context, boolean z, OnClickListener onClickListener) {
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
            this.dateFormat = new SimpleDateFormat(str, languageLocale);
            return;
        }
        this.dateFormat = new SimpleDateFormat("MM" + context.getResources().getString(R.string.Month_short) + "dd" + context.getResources().getString(R.string.Date_short));
    }

    public LocalAlbumAdapter(Context context, OnClickListener onClickListener) {
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
        String strValueOf;
        String strValueOf2;
        String strValueOf3;
        String strValueOf4;
        if (getItemViewType(i) == 1) {
            ((AlbumTimeViewHolder) baseViewHolder).tvTitle.setText(this.dateFormat.format(new Date(((AlbumTimeMode) this.dataList.get(i)).getTime())));
            return;
        }
        AlbumFileMode albumFileMode = (AlbumFileMode) this.dataList.get(i);
        if (albumFileMode.getFile().getAbsolutePath().contains("T_27") || albumFileMode.getFile().getAbsolutePath().contains("T_21") || albumFileMode.getFile().getAbsolutePath().contains("T_28")) {
            AlbumContentViewHolder albumContentViewHolder = (AlbumContentViewHolder) baseViewHolder;
            albumContentViewHolder.ivContent.setVisibility(4);
            Glide.with(CommonUtils.getAppContext()).load(albumFileMode.getFile().getAbsolutePath()).listener((RequestListener<? super String, GlideDrawable>) new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter.1
                public final /* synthetic */ BaseViewHolder val$holder;

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onException(Exception exc, String str, Target<GlideDrawable> target, boolean z) {
                    return false;
                }

                public AnonymousClass1(BaseViewHolder baseViewHolder2) {
                    baseViewHolder = baseViewHolder2;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                    Observable.timer(200L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter.1.1
                        public C00731() {
                        }

                        @Override // io.reactivex.functions.Consumer
                        public void accept(Long l) throws Exception {
                            int i2 = BaseApplication.displayMetrics.widthPixels;
                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (i2 / 3.0f), (int) (i2 / 3.0f), Bitmap.Config.ARGB_8888);
                            ((AlbumContentViewHolder) baseViewHolder).ivContent.draw(new Canvas(bitmapCreateBitmap));
                            ((AlbumContentViewHolder) baseViewHolder).ivContent.setImageBitmap(CommonUtil.bimapSquareRound(170, bitmapCreateBitmap));
                            ((AlbumContentViewHolder) baseViewHolder).ivContent.setVisibility(0);
                        }
                    });
                    return false;
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter$1$1 */
                public class C00731 implements Consumer<Long> {
                    public C00731() {
                    }

                    @Override // io.reactivex.functions.Consumer
                    public void accept(Long l) throws Exception {
                        int i2 = BaseApplication.displayMetrics.widthPixels;
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (i2 / 3.0f), (int) (i2 / 3.0f), Bitmap.Config.ARGB_8888);
                        ((AlbumContentViewHolder) baseViewHolder).ivContent.draw(new Canvas(bitmapCreateBitmap));
                        ((AlbumContentViewHolder) baseViewHolder).ivContent.setImageBitmap(CommonUtil.bimapSquareRound(170, bitmapCreateBitmap));
                        ((AlbumContentViewHolder) baseViewHolder).ivContent.setVisibility(0);
                    }
                }
            }).into(albumContentViewHolder.ivContent);
        } else {
            AlbumContentViewHolder albumContentViewHolder2 = (AlbumContentViewHolder) baseViewHolder2;
            albumContentViewHolder2.ivContent.setVisibility(0);
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(albumFileMode.getFile().getAbsolutePath()).scaleType(ImageView.ScaleType.CENTER_CROP).imageView(albumContentViewHolder2.ivContent).build());
        }
        AlbumContentViewHolder albumContentViewHolder3 = (AlbumContentViewHolder) baseViewHolder2;
        ViewGroup.LayoutParams layoutParams = albumContentViewHolder3.rlRoot.getLayoutParams();
        layoutParams.height = (int) (BaseApplication.displayMetrics.widthPixels / 3.0f);
        albumContentViewHolder3.rlRoot.setLayoutParams(layoutParams);
        String videoLength = VideoTrimmerUtil.getVideoLength(albumFileMode.getFile().getAbsolutePath());
        if (videoLength != null) {
            BigDecimal bigDecimal = new BigDecimal(videoLength);
            int iDoubleValue = (int) (bigDecimal.doubleValue() % 60.0d);
            int iDoubleValue2 = (int) (bigDecimal.doubleValue() / 60.0d);
            TextView textView = albumContentViewHolder3.tvTime;
            StringBuilder sb = new StringBuilder();
            if (iDoubleValue2 > 9) {
                strValueOf3 = String.valueOf(iDoubleValue2);
            } else {
                strValueOf3 = "0" + iDoubleValue2;
            }
            sb.append(strValueOf3);
            sb.append(":");
            if (iDoubleValue > 9) {
                strValueOf4 = String.valueOf(iDoubleValue);
            } else {
                strValueOf4 = "0" + iDoubleValue;
            }
            sb.append(strValueOf4);
            textView.setText(sb.toString());
        } else {
            Integer videoDuration = getVideoDuration(albumFileMode.getFile().getAbsolutePath());
            if (videoDuration != null) {
                BigDecimal bigDecimal2 = new BigDecimal(videoDuration.intValue());
                int iDoubleValue3 = (int) (bigDecimal2.doubleValue() % 60.0d);
                int iDoubleValue4 = (int) (bigDecimal2.doubleValue() / 60.0d);
                TextView textView2 = albumContentViewHolder3.tvTime;
                StringBuilder sb2 = new StringBuilder();
                if (iDoubleValue4 > 9) {
                    strValueOf = String.valueOf(iDoubleValue4);
                } else {
                    strValueOf = "0" + iDoubleValue4;
                }
                sb2.append(strValueOf);
                sb2.append(":");
                if (iDoubleValue3 > 9) {
                    strValueOf2 = String.valueOf(iDoubleValue3);
                } else {
                    strValueOf2 = "0" + iDoubleValue3;
                }
                sb2.append(strValueOf2);
                textView2.setText(sb2.toString());
            }
        }
        albumContentViewHolder3.cbSelect.setVisibility(this.isEdit ? 0 : 8);
        albumContentViewHolder3.cbSelect.setChecked(this.selectState.get(i).booleanValue());
        albumContentViewHolder3.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter.2
            final /* synthetic */ int val$position;

            public AnonymousClass2(int i2) {
                i = i2;
            }

            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (LocalAlbumAdapter.this.listener != null) {
                    if (LocalAlbumAdapter.this.dataList.get(i) instanceof AlbumFileMode) {
                        LocalAlbumAdapter.this.selectState.set(i, Boolean.valueOf(z));
                    }
                    LocalAlbumAdapter.this.listener.onSelectChange(i);
                }
            }
        });
        albumContentViewHolder3.rlRoot.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter.3
            final /* synthetic */ BaseViewHolder val$holder;
            final /* synthetic */ int val$position;

            public AnonymousClass3(BaseViewHolder baseViewHolder2, int i2) {
                baseViewHolder = baseViewHolder2;
                i = i2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!LocalAlbumAdapter.this.isEdit) {
                    if (LocalAlbumAdapter.this.listener != null) {
                        LocalAlbumAdapter.this.listener.onViewClick((AlbumFileMode) LocalAlbumAdapter.this.dataList.get(i), i);
                    }
                } else {
                    ((AlbumContentViewHolder) baseViewHolder).cbSelect.setChecked(!((AlbumContentViewHolder) r3).cbSelect.isChecked());
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter$1 */
    public class AnonymousClass1 implements RequestListener<String, GlideDrawable> {
        public final /* synthetic */ BaseViewHolder val$holder;

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onException(Exception exc, String str, Target<GlideDrawable> target, boolean z) {
            return false;
        }

        public AnonymousClass1(BaseViewHolder baseViewHolder2) {
            baseViewHolder = baseViewHolder2;
        }

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
            Observable.timer(200L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter.1.1
                public C00731() {
                }

                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    int i2 = BaseApplication.displayMetrics.widthPixels;
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (i2 / 3.0f), (int) (i2 / 3.0f), Bitmap.Config.ARGB_8888);
                    ((AlbumContentViewHolder) baseViewHolder).ivContent.draw(new Canvas(bitmapCreateBitmap));
                    ((AlbumContentViewHolder) baseViewHolder).ivContent.setImageBitmap(CommonUtil.bimapSquareRound(170, bitmapCreateBitmap));
                    ((AlbumContentViewHolder) baseViewHolder).ivContent.setVisibility(0);
                }
            });
            return false;
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter$1$1 */
        public class C00731 implements Consumer<Long> {
            public C00731() {
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                int i2 = BaseApplication.displayMetrics.widthPixels;
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (i2 / 3.0f), (int) (i2 / 3.0f), Bitmap.Config.ARGB_8888);
                ((AlbumContentViewHolder) baseViewHolder).ivContent.draw(new Canvas(bitmapCreateBitmap));
                ((AlbumContentViewHolder) baseViewHolder).ivContent.setImageBitmap(CommonUtil.bimapSquareRound(170, bitmapCreateBitmap));
                ((AlbumContentViewHolder) baseViewHolder).ivContent.setVisibility(0);
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter$2 */
    public class AnonymousClass2 implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ int val$position;

        public AnonymousClass2(int i2) {
            i = i2;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (LocalAlbumAdapter.this.listener != null) {
                if (LocalAlbumAdapter.this.dataList.get(i) instanceof AlbumFileMode) {
                    LocalAlbumAdapter.this.selectState.set(i, Boolean.valueOf(z));
                }
                LocalAlbumAdapter.this.listener.onSelectChange(i);
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.download.adapter.LocalAlbumAdapter$3 */
    public class AnonymousClass3 implements View.OnClickListener {
        final /* synthetic */ BaseViewHolder val$holder;
        final /* synthetic */ int val$position;

        public AnonymousClass3(BaseViewHolder baseViewHolder2, int i2) {
            baseViewHolder = baseViewHolder2;
            i = i2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (!LocalAlbumAdapter.this.isEdit) {
                if (LocalAlbumAdapter.this.listener != null) {
                    LocalAlbumAdapter.this.listener.onViewClick((AlbumFileMode) LocalAlbumAdapter.this.dataList.get(i), i);
                }
            } else {
                ((AlbumContentViewHolder) baseViewHolder).cbSelect.setChecked(!((AlbumContentViewHolder) r3).cbSelect.isChecked());
            }
        }
    }

    public Integer getVideoDuration(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        if (isFileIntegrityValid(str, "存储的校验和")) {
            mediaMetadataRetriever.setDataSource(str);
            String strExtractMetadata = mediaMetadataRetriever.extractMetadata(9);
            PetkitLog.d(getClass().getSimpleName(), "video duration:" + strExtractMetadata);
            if (strExtractMetadata != null) {
                return Integer.valueOf(Integer.parseInt(strExtractMetadata) / 1000);
            }
        }
        return null;
    }

    public boolean isFileIntegrityValid(String str, String str2) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                return false;
            }
            MessageDigest messageDigest = MessageDigest.getInstance(EncryptUtil.MD5);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[8192];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                messageDigest.update(bArr, 0, i);
            }
            fileInputStream.close();
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDigest) {
                sb.append(String.format("%02x", Byte.valueOf(b)));
            }
            return sb.toString().equals(str2);
        } catch (IOException e) {
            e = e;
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
            e.printStackTrace();
            return false;
        }
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

    public int getSelectedCount() {
        int i = 0;
        for (int i2 = 0; i2 < this.selectState.size(); i2++) {
            if ((this.dataList.get(i2) instanceof AlbumFileMode) && this.selectState.get(i2).booleanValue()) {
                i++;
            }
        }
        return i;
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
            if (this.dataList.get(i) instanceof AlbumFileMode) {
                this.selectState.set(i, Boolean.TRUE);
            }
            notifyDataSetChanged();
            i++;
        }
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

    public final int getRotate(String str) {
        PetkitLog.d("LocalAlbumAdapter", "当前相册视频图片路径：" + str);
        if (str.contains("RA090")) {
            return 270;
        }
        if (str.contains("RA180")) {
            return 180;
        }
        return str.contains("RA270") ? 90 : 0;
    }
}

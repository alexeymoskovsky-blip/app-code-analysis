package com.jess.arms.widget.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.GifTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.widget.imageloader.BaseImageLoaderStrategy;
import com.jess.arms.widget.imageloader.StreamBitmapDecoderWithDecryption;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/* JADX INFO: loaded from: classes3.dex */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageConfig> {
    @Override // com.jess.arms.widget.imageloader.BaseImageLoaderStrategy
    public void downloadOnly(Context context, GlideImageConfig glideImageConfig) {
    }

    @Override // com.jess.arms.widget.imageloader.BaseImageLoaderStrategy
    public void loadImage(Context context, GlideImageConfig glideImageConfig) {
        if (context == null) {
            throw new IllegalStateException("Context is required");
        }
        if (glideImageConfig == null) {
            throw new IllegalStateException("GlideImageConfig is required");
        }
        if (glideImageConfig.getImageView() == null) {
            throw new IllegalStateException("Imageview is required");
        }
        RequestManager requestManagerWith = Glide.with(context);
        if (glideImageConfig.isGif()) {
            GifTypeRequest<String> gifTypeRequestAsGif = requestManagerWith.load(glideImageConfig.getUrl()).asGif();
            gifTypeRequestAsGif.diskCacheStrategy(DiskCacheStrategy.RESULT);
            gifTypeRequestAsGif.into(glideImageConfig.getImageView());
            return;
        }
        BitmapTypeRequest<String> bitmapTypeRequestAsBitmap = requestManagerWith.load(glideImageConfig.getUrl()).asBitmap();
        if (!TextUtils.isEmpty(glideImageConfig.getSecretKey())) {
            bitmapTypeRequestAsBitmap.decoder((ResourceDecoder<ImageVideoWrapper, Bitmap>) new StreamBitmapDecoderWithDecryption(context, glideImageConfig.getSecretKey()));
        }
        if (glideImageConfig.getDecodeFormat() != null) {
            bitmapTypeRequestAsBitmap.format(glideImageConfig.getDecodeFormat());
        }
        if (ImageView.ScaleType.FIT_CENTER == glideImageConfig.getScaleType()) {
            bitmapTypeRequestAsBitmap.fitCenter();
        } else {
            bitmapTypeRequestAsBitmap.centerCrop();
        }
        int cacheStrategy = glideImageConfig.getCacheStrategy();
        if (cacheStrategy == 0) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.ALL);
        } else if (cacheStrategy == 1) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (cacheStrategy == 2) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        } else if (cacheStrategy == 3) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.RESULT);
        }
        if (glideImageConfig.getTransformation() != null) {
            bitmapTypeRequestAsBitmap.transform(glideImageConfig.getTransformation());
        }
        if (glideImageConfig.getErrorPic() != 0) {
            bitmapTypeRequestAsBitmap.error(glideImageConfig.getErrorPic());
        }
        if (glideImageConfig.getPlaceholder() != 0) {
            bitmapTypeRequestAsBitmap.placeholder(glideImageConfig.getPlaceholder());
        } else if (glideImageConfig.getErrorPic() != 0) {
            bitmapTypeRequestAsBitmap.placeholder(glideImageConfig.getErrorPic());
        }
        if (glideImageConfig.getListener() != null) {
            bitmapTypeRequestAsBitmap.listener((RequestListener<? super String, TranscodeType>) glideImageConfig.getListener());
        }
        bitmapTypeRequestAsBitmap.into(glideImageConfig.getImageView());
    }

    @Override // com.jess.arms.widget.imageloader.BaseImageLoaderStrategy
    public void autoLoadImage(Context context, GlideImageConfig glideImageConfig) {
        if (context == null) {
            throw new IllegalStateException("Context is required");
        }
        if (glideImageConfig == null) {
            throw new IllegalStateException("GlideImageConfig is required");
        }
        if (glideImageConfig.getImageView() == null) {
            throw new IllegalStateException("Imageview is required");
        }
        RequestManager requestManagerWith = Glide.with(context);
        if (true == glideImageConfig.isGif()) {
            GifTypeRequest<String> gifTypeRequestAsGif = requestManagerWith.load(glideImageConfig.getUrl()).asGif();
            gifTypeRequestAsGif.diskCacheStrategy(DiskCacheStrategy.RESULT);
            gifTypeRequestAsGif.into(glideImageConfig.getImageView());
            return;
        }
        BitmapTypeRequest<String> bitmapTypeRequestAsBitmap = requestManagerWith.load(glideImageConfig.getUrl()).asBitmap();
        if (!TextUtils.isEmpty(glideImageConfig.getSecretKey())) {
            bitmapTypeRequestAsBitmap.decoder((ResourceDecoder<ImageVideoWrapper, Bitmap>) new StreamBitmapDecoderWithDecryption(context, glideImageConfig.getSecretKey()));
        }
        if (ImageView.ScaleType.FIT_CENTER == glideImageConfig.getScaleType()) {
            bitmapTypeRequestAsBitmap.fitCenter();
        } else {
            bitmapTypeRequestAsBitmap.centerCrop();
        }
        int cacheStrategy = glideImageConfig.getCacheStrategy();
        if (cacheStrategy == 0) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.ALL);
        } else if (cacheStrategy == 1) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (cacheStrategy == 2) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        } else if (cacheStrategy == 3) {
            bitmapTypeRequestAsBitmap.diskCacheStrategy(DiskCacheStrategy.RESULT);
        }
        if (glideImageConfig.getTransformation() != null) {
            bitmapTypeRequestAsBitmap.transform(glideImageConfig.getTransformation());
        }
        if (glideImageConfig.getErrorPic() != 0) {
            bitmapTypeRequestAsBitmap.error(glideImageConfig.getErrorPic());
        }
        if (glideImageConfig.getPlaceholder() != 0) {
            bitmapTypeRequestAsBitmap.placeholder(glideImageConfig.getPlaceholder());
        } else if (glideImageConfig.getErrorPic() != 0) {
            bitmapTypeRequestAsBitmap.placeholder(glideImageConfig.getErrorPic());
        }
        if (glideImageConfig.getListener() != null) {
            bitmapTypeRequestAsBitmap.listener((RequestListener<? super String, TranscodeType>) glideImageConfig.getListener());
        }
        bitmapTypeRequestAsBitmap.into(glideImageConfig.getImageView());
    }

    @Override // com.jess.arms.widget.imageloader.BaseImageLoaderStrategy
    public void clear(Context context, GlideImageConfig glideImageConfig) {
        if (context == null) {
            throw new IllegalStateException("Context is required");
        }
        if (glideImageConfig == null) {
            throw new IllegalStateException("GlideImageConfig is required");
        }
        if (glideImageConfig.getImageViews() != null && glideImageConfig.getImageViews().length > 0) {
            for (ImageView imageView : glideImageConfig.getImageViews()) {
                Glide.clear(imageView);
            }
        }
        if (glideImageConfig.getTargets() != null && glideImageConfig.getTargets().length > 0) {
            for (Target target : glideImageConfig.getTargets()) {
                Glide.clear((Target<?>) target);
            }
        }
        if (glideImageConfig.isClearDiskCache()) {
            Observable.just(0).observeOn(Schedulers.io()).subscribe(new Consumer<Integer>() { // from class: com.jess.arms.widget.imageloader.glide.GlideImageLoaderStrategy.1
                final /* synthetic */ Context val$ctx;

                public AnonymousClass1(Context context2) {
                    context = context2;
                }

                @Override // io.reactivex.functions.Consumer
                public void accept(@NonNull Integer num) throws Exception {
                    Glide.get(context).clearDiskCache();
                }
            });
        }
        if (glideImageConfig.isClearMemory()) {
            Glide.get(context2).clearMemory();
        }
    }

    /* JADX INFO: renamed from: com.jess.arms.widget.imageloader.glide.GlideImageLoaderStrategy$1 */
    public class AnonymousClass1 implements Consumer<Integer> {
        final /* synthetic */ Context val$ctx;

        public AnonymousClass1(Context context2) {
            context = context2;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(@NonNull Integer num) throws Exception {
            Glide.get(context).clearDiskCache();
        }
    }
}

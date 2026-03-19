package com.petkit.android.activities.community;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.model.ImageDetail;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.FileUtils;
import com.petkit.oversea.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/* JADX INFO: loaded from: classes3.dex */
public class ImageDetailActivity extends BaseActivity {
    public static final String IMAGE_LIST_DATA = "IMAGE_LIST_DATA";
    public static final String IMAGE_LIST_POSITION = "IMAGE_LIST_POSITION";
    private ArrayList<Object> imagesList;
    private int position;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        if (bundle != null) {
            this.imagesList = (ArrayList) bundle.getSerializable(IMAGE_LIST_DATA);
            this.position = bundle.getInt(IMAGE_LIST_POSITION, 0);
        } else {
            this.imagesList = (ArrayList) getIntent().getSerializableExtra(IMAGE_LIST_DATA);
            this.position = getIntent().getIntExtra(IMAGE_LIST_POSITION, 0);
        }
        for (int i = 0; i < this.imagesList.size(); i++) {
            Object obj = this.imagesList.get(i);
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.contains("http://sandbox.img5.petkit.cn")) {
                    this.imagesList.set(i, str.replace("http://sandbox.img5.petkit.cn", "https://sandbox-img5.petkit.cn"));
                } else if (!str.contains("https")) {
                    this.imagesList.set(i, str.replace("http", "https"));
                }
            } else if (obj instanceof ImageDetail) {
                ImageDetail imageDetail = (ImageDetail) obj;
                String url = imageDetail.getUrl();
                if (url.contains("http://sandbox.img5.petkit.cn")) {
                    imageDetail.setUrl(url.replace("http://sandbox.img5.petkit.cn", "https://sandbox-img5.petkit.cn"));
                } else if (!url.contains("https")) {
                    imageDetail.setUrl(url.replace("http", "https"));
                }
            }
        }
        setContentView(R.layout.activity_image_detail);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(IMAGE_LIST_POSITION, this.position);
        bundle.putSerializable(IMAGE_LIST_DATA, this.imagesList);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_none, R.anim.img_scale_out);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new ImagePagerAdapter(this.imagesList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.community.ImageDetailActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            public AnonymousClass1() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                ImageDetailActivity.this.position = i;
            }
        });
        viewPager.setCurrentItem(this.position);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.ImageDetailActivity$1 */
    public class AnonymousClass1 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass1() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            ImageDetailActivity.this.position = i;
        }
    }

    public class ImagePagerAdapter extends PagerAdapter {
        public List<Object> mList;

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public ImagePagerAdapter(List<Object> list) {
            this.mList = list;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.mList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public View instantiateItem(ViewGroup viewGroup, int i) {
            String url;
            View viewInflate = LayoutInflater.from(ImageDetailActivity.this).inflate(R.layout.adapter_scale_view, (ViewGroup) null);
            PhotoView photoView = (PhotoView) viewInflate.findViewById(R.id.scale_view);
            Object obj = this.mList.get(i);
            if (obj instanceof String) {
                url = (String) obj;
            } else if (!(obj instanceof ImageDetail)) {
                url = "";
            } else {
                url = ((ImageDetail) obj).getUrl();
            }
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() { // from class: com.petkit.android.activities.community.ImageDetailActivity$ImagePagerAdapter$$ExternalSyntheticLambda0
                @Override // uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener
                public final void onPhotoTap(View view, float f, float f2) {
                    this.f$0.lambda$instantiateItem$0(view, f, f2);
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.petkit.android.activities.community.ImageDetailActivity$ImagePagerAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return this.f$0.lambda$instantiateItem$1(view);
                }
            });
            ProgressBar progressBar = (ProgressBar) viewInflate.findViewById(R.id.scale_loading);
            progressBar.setVisibility(0);
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with((FragmentActivity) ImageDetailActivity.this).load(CommonUtil.httpToHttps(url)).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.transparent).error(getCount() > 2 ? R.drawable.default_image : R.drawable.default_image_middle).listener((RequestListener<? super String, GlideDrawable>) new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.community.ImageDetailActivity.ImagePagerAdapter.1
                public final /* synthetic */ ProgressBar val$spinner;

                public AnonymousClass1(ProgressBar progressBar2) {
                    progressBar = progressBar2;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onException(Exception exc, String str, Target<GlideDrawable> target, boolean z) {
                    progressBar.setVisibility(8);
                    ImageDetailActivity.this.showShortToast(R.string.Hint_download_origin_image_failed);
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                    progressBar.setVisibility(8);
                    return false;
                }
            }).into(photoView);
            viewGroup.addView(viewInflate, 0);
            return viewInflate;
        }

        public final /* synthetic */ void lambda$instantiateItem$0(View view, float f, float f2) {
            ImageDetailActivity.this.finish();
            ImageDetailActivity.this.overridePendingTransition(R.anim.slide_none, R.anim.img_scale_out);
        }

        public final /* synthetic */ boolean lambda$instantiateItem$1(View view) {
            ImageDetailActivity.this.showPopMenu(ImageDetailActivity.this.getString(R.string.Save));
            return true;
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.community.ImageDetailActivity$ImagePagerAdapter$1 */
        public class AnonymousClass1 implements RequestListener<String, GlideDrawable> {
            public final /* synthetic */ ProgressBar val$spinner;

            public AnonymousClass1(ProgressBar progressBar2) {
                progressBar = progressBar2;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onException(Exception exc, String str, Target<GlideDrawable> target, boolean z) {
                progressBar.setVisibility(8);
                ImageDetailActivity.this.showShortToast(R.string.Hint_download_origin_image_failed);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                progressBar.setVisibility(8);
                return false;
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        String url;
        int id = view.getId();
        if (id == R.id.menu_1) {
            if (!CommonUtils.checkPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                startActivity(PermissionDialogActivity.newIntent(this, getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
                return;
            }
            this.mPopupWindow.dismiss();
            Object obj = this.imagesList.get(this.position);
            if (obj instanceof String) {
                url = (String) obj;
            } else if (!(obj instanceof ImageDetail)) {
                return;
            } else {
                url = ((ImageDetail) obj).getUrl();
            }
            saveFile(CommonUtil.httpToHttps(url));
            return;
        }
        if (id == R.id.menu_cancel) {
            this.mPopupWindow.dismiss();
        }
    }

    private void saveFile(String str) {
        new ImageCacheSimple(this).getImageCachePath(str, new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.community.ImageDetailActivity$$ExternalSyntheticLambda2
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str2) {
                this.f$0.lambda$saveFile$3(str2);
            }
        });
        LoadDialog.show(this);
    }

    public /* synthetic */ void lambda$saveFile$3(final String str) {
        if (str == null) {
            showShortToast(R.string.Failure);
        } else {
            new Runnable() { // from class: com.petkit.android.activities.community.ImageDetailActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$saveFile$2(str);
                }
            }.run();
        }
    }

    public /* synthetic */ void lambda$saveFile$2(String str) {
        String str2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        String str3 = options.outMimeType;
        if (TextUtils.isEmpty(str3)) {
            str2 = ".jpg";
        } else {
            str2 = "." + str3.substring(6);
        }
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                final String strSaveBitmapByMediaStore = FileUtils.saveBitmapByMediaStore(BitmapFactory.decodeFile(str), System.currentTimeMillis() + str2);
                runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.community.ImageDetailActivity$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$saveFile$0(strSaveBitmapByMediaStore);
                    }
                });
            } else {
                final String strSaveFileToPublicDirectory = FileUtils.saveFileToPublicDirectory(str, System.currentTimeMillis() + str2, Environment.DIRECTORY_DCIM);
                runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.community.ImageDetailActivity$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$saveFile$1(strSaveFileToPublicDirectory);
                    }
                });
            }
        } catch (Exception e) {
            showShortToast(R.string.Failure);
            e.printStackTrace();
        }
        LoadDialog.dismissDialog();
    }

    public /* synthetic */ void lambda$saveFile$0(String str) {
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
        showShortToast(getString(R.string.Save) + " " + str);
    }

    public /* synthetic */ void lambda$saveFile$1(String str) {
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
        showShortToast(getString(R.string.Save) + " " + str);
    }

    public static void copyFile(String str, String str2) throws Exception {
        if (!new File(str).exists()) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(str);
        FileOutputStream fileOutputStream = new FileOutputStream(str2);
        byte[] bArr = new byte[1024];
        while (true) {
            int i = fileInputStream.read(bArr);
            if (i != -1) {
                fileOutputStream.write(bArr, 0, i);
            } else {
                fileInputStream.close();
                return;
            }
        }
    }
}

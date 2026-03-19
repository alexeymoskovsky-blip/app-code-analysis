package com.qiyukf.unicorn.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.d.b.a;
import com.qiyukf.uikit.common.activity.BaseFragmentActivity;
import com.qiyukf.uikit.common.ui.imageview.BaseZoomableImageView;
import com.qiyukf.uikit.common.ui.imageview.MultiTouchZoomableImageView;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.mediaselect.internal.entity.Item;
import com.qiyukf.unicorn.mediaselect.internal.model.SelectedItemCollection;
import com.qiyukf.unicorn.mediaselect.internal.ui.activity.BasePreviewActivity;
import com.qiyukf.unicorn.mediaselect.internal.utils.PhotoMetadataUtils;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.widget.dialog.UnicornDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes6.dex */
public class WatchPictureActivity extends BaseFragmentActivity {
    private static final String TAG = "WatchPictureActivity";
    public static final String WATCH_PICTURE_INDEX_LABEL = "WATCH_PICTURE_INDEX_LABEL";
    public static final String WATCH_PICTURE_LIST_LABEL = "WATCH_PICTURE_List_LABEL";
    private ImagePagerAdapter imagePagerAdapter;
    private ViewPager imageViewPager;
    public ArrayList<Item> photoList = new ArrayList<>();
    private int currentPosition = 0;
    private boolean mIsToolbarHide = false;

    public static void start(Activity activity, ArrayList<Item> arrayList, int i, int i2) {
        Intent intent = new Intent();
        intent.putExtra(WATCH_PICTURE_LIST_LABEL, arrayList);
        intent.putExtra(WATCH_PICTURE_INDEX_LABEL, i);
        intent.setClass(activity, WatchPictureActivity.class);
        activity.startActivityForResult(intent, i2);
    }

    public static void start(Fragment fragment, ArrayList<Item> arrayList, int i, int i2) {
        Intent intent = new Intent();
        intent.putExtra(WATCH_PICTURE_LIST_LABEL, arrayList);
        intent.putExtra(WATCH_PICTURE_INDEX_LABEL, i);
        intent.setClass(fragment.getActivity(), WatchPictureActivity.class);
        fragment.startActivityForResult(intent, i2);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        int size;
        super.onCreate(bundle);
        setContentView(R.layout.ysf_activity_watch_picture);
        onParseIntent();
        ArrayList<Item> arrayList = this.photoList;
        if (arrayList != null) {
            size = arrayList.size();
        } else {
            this.photoList = new ArrayList<>();
            size = 0;
        }
        setTitle(ChineseToPinyinResource.Field.LEFT_BRACKET + (this.currentPosition + 1) + "/" + size + ChineseToPinyinResource.Field.RIGHT_BRACKET);
        findViews();
        addDeleteMenu();
    }

    private void onParseIntent() {
        this.photoList = getIntent().getParcelableArrayListExtra(WATCH_PICTURE_LIST_LABEL);
        this.currentPosition = getIntent().getIntExtra(WATCH_PICTURE_INDEX_LABEL, 0);
    }

    private void findViews() {
        this.imageViewPager = (ViewPager) findViewById(R.id.ysf_vp_watch_img);
        this.imagePagerAdapter = new ImagePagerAdapter(this.currentPosition, this.photoList);
        this.imageViewPager.setOffscreenPageLimit(2);
        this.imageViewPager.setAdapter(this.imagePagerAdapter);
        this.imageViewPager.setCurrentItem(this.currentPosition);
        this.imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.1
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
                WatchPictureActivity.this.currentPosition = i;
                WatchPictureActivity.this.setTitle("（" + (WatchPictureActivity.this.currentPosition + 1) + "/" + WatchPictureActivity.this.photoList.size() + "）");
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$1 */
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
            WatchPictureActivity.this.currentPosition = i;
            WatchPictureActivity.this.setTitle("（" + (WatchPictureActivity.this.currentPosition + 1) + "/" + WatchPictureActivity.this.photoList.size() + "）");
        }
    }

    private void addDeleteMenu() {
        ImageView imageView = new ImageView(this);
        imageView.setMaxWidth(p.a(30.0f));
        imageView.setPadding(0, p.a(10.0f), 0, p.a(10.0f));
        imageView.setImageResource(R.drawable.ysf_ic_delete_right_icon);
        addViewMenu(imageView);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.2
            public AnonymousClass2() {
            }

            /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$2$1 */
            public class AnonymousClass1 implements UnicornDialog.OnClickListener {
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public void onClick(int i) {
                    WatchPictureActivity watchPictureActivity = WatchPictureActivity.this;
                    watchPictureActivity.photoList.remove(watchPictureActivity.currentPosition);
                    if (WatchPictureActivity.this.photoList.size() == 0) {
                        WatchPictureActivity.this.finishIntent();
                        return;
                    }
                    WatchPictureActivity.this.setTitle(ChineseToPinyinResource.Field.LEFT_BRACKET + (WatchPictureActivity.this.currentPosition + 1) + "/" + WatchPictureActivity.this.photoList.size() + "）");
                    WatchPictureActivity.this.imagePagerAdapter.notifyDataSetChanged();
                }
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                WatchPictureActivity watchPictureActivity = WatchPictureActivity.this;
                UnicornDialog.showDoubleBtnDialog(watchPictureActivity, "", watchPictureActivity.getString(R.string.ysf_leave_msg_delete_prompt), WatchPictureActivity.this.getString(R.string.ysf_cancel), WatchPictureActivity.this.getString(R.string.ysf_leave_msg_sure), true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.2.1
                    public AnonymousClass1() {
                    }

                    @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                    public void onClick(int i) {
                        WatchPictureActivity watchPictureActivity2 = WatchPictureActivity.this;
                        watchPictureActivity2.photoList.remove(watchPictureActivity2.currentPosition);
                        if (WatchPictureActivity.this.photoList.size() == 0) {
                            WatchPictureActivity.this.finishIntent();
                            return;
                        }
                        WatchPictureActivity.this.setTitle(ChineseToPinyinResource.Field.LEFT_BRACKET + (WatchPictureActivity.this.currentPosition + 1) + "/" + WatchPictureActivity.this.photoList.size() + "）");
                        WatchPictureActivity.this.imagePagerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$2$1 */
        public class AnonymousClass1 implements UnicornDialog.OnClickListener {
            public AnonymousClass1() {
            }

            @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
            public void onClick(int i) {
                WatchPictureActivity watchPictureActivity2 = WatchPictureActivity.this;
                watchPictureActivity2.photoList.remove(watchPictureActivity2.currentPosition);
                if (WatchPictureActivity.this.photoList.size() == 0) {
                    WatchPictureActivity.this.finishIntent();
                    return;
                }
                WatchPictureActivity.this.setTitle(ChineseToPinyinResource.Field.LEFT_BRACKET + (WatchPictureActivity.this.currentPosition + 1) + "/" + WatchPictureActivity.this.photoList.size() + "）");
                WatchPictureActivity.this.imagePagerAdapter.notifyDataSetChanged();
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            WatchPictureActivity watchPictureActivity = WatchPictureActivity.this;
            UnicornDialog.showDoubleBtnDialog(watchPictureActivity, "", watchPictureActivity.getString(R.string.ysf_leave_msg_delete_prompt), WatchPictureActivity.this.getString(R.string.ysf_cancel), WatchPictureActivity.this.getString(R.string.ysf_leave_msg_sure), true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.2.1
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public void onClick(int i) {
                    WatchPictureActivity watchPictureActivity2 = WatchPictureActivity.this;
                    watchPictureActivity2.photoList.remove(watchPictureActivity2.currentPosition);
                    if (WatchPictureActivity.this.photoList.size() == 0) {
                        WatchPictureActivity.this.finishIntent();
                        return;
                    }
                    WatchPictureActivity.this.setTitle(ChineseToPinyinResource.Field.LEFT_BRACKET + (WatchPictureActivity.this.currentPosition + 1) + "/" + WatchPictureActivity.this.photoList.size() + "）");
                    WatchPictureActivity.this.imagePagerAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity
    public void onTitleBarBackPressed() {
        finishIntent();
    }

    private BaseZoomableImageView imageViewOfItem(Item item) {
        Iterator<Item> it = this.photoList.iterator();
        int i = 0;
        while (it.hasNext() && !it.next().uri.equals(item.uri)) {
            i++;
        }
        return imageViewOf(i);
    }

    private BaseZoomableImageView imageViewOf(int i) {
        try {
            return (BaseZoomableImageView) this.imageViewPager.findViewWithTag(Integer.valueOf(i)).findViewById(R.id.ysf_watch_pic_and_video_imageView);
        } catch (Exception e) {
            AbsUnicornLog.e(TAG, "imageViewOf is error postion= ".concat(String.valueOf(i)), e);
            return null;
        }
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ViewGroup.LayoutParams layoutParams = this.imageViewPager.getLayoutParams();
        layoutParams.height = p.b();
        layoutParams.width = p.a();
        this.imageViewPager.setLayoutParams(layoutParams);
        this.imagePagerAdapter.notifyDataSetChanged();
    }

    public class ImagePagerAdapter extends PagerAdapter {
        private Handler decodeHandler = a.c().a();
        private int mFirstDisplayImageIndex;
        private List<Item> messageList;

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public ImagePagerAdapter(int i, List<Item> list) {
            this.messageList = list;
            this.mFirstDisplayImageIndex = i;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            View view = (View) obj;
            ((MultiTouchZoomableImageView) view.findViewById(R.id.ysf_watch_pic_and_video_imageView)).clear();
            viewGroup.removeView(view);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            List<Item> list = this.messageList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) throws Throwable {
            View viewInflate = LayoutInflater.from(WatchPictureActivity.this).inflate(R.layout.ysf_watch_pic_and_video_item, (ViewGroup) null);
            MultiTouchZoomableImageView multiTouchZoomableImageView = (MultiTouchZoomableImageView) viewInflate.findViewById(R.id.ysf_watch_pic_and_video_imageView);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.ysf_iv_watch_pic_and_video_start);
            viewGroup.addView(viewInflate);
            viewInflate.setTag(Integer.valueOf(i));
            if (WatchPictureActivity.this.photoList.get(i).isVideo()) {
                imageView.setVisibility(0);
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.ImagePagerAdapter.1
                    final /* synthetic */ int val$position;

                    public AnonymousClass1(int i2) {
                        i = i2;
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (WatchPictureActivity.this.photoList.get(i).isVideo()) {
                            Intent intent = new Intent("android.intent.action.VIEW");
                            WatchPictureActivity watchPictureActivity = WatchPictureActivity.this;
                            intent.setDataAndType(watchPictureActivity.photoList.get(watchPictureActivity.currentPosition).uri, "video/*");
                            intent.addFlags(1);
                            try {
                                WatchPictureActivity.this.startActivity(intent);
                            } catch (ActivityNotFoundException unused) {
                                t.a(R.string.ysf_error_no_video_activity);
                            }
                        }
                    }
                });
            } else {
                imageView.setVisibility(8);
            }
            if (i2 == this.mFirstDisplayImageIndex) {
                WatchPictureActivity watchPictureActivity = WatchPictureActivity.this;
                watchPictureActivity.updateImage(watchPictureActivity.photoList.get(i2), true);
            } else {
                this.decodeHandler.post(new Runnable() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.ImagePagerAdapter.2
                    final /* synthetic */ int val$position;

                    public AnonymousClass2(int i2) {
                        i = i2;
                    }

                    @Override // java.lang.Runnable
                    public void run() throws Throwable {
                        WatchPictureActivity watchPictureActivity2 = WatchPictureActivity.this;
                        watchPictureActivity2.updateImage(watchPictureActivity2.photoList.get(i), false);
                    }
                });
            }
            multiTouchZoomableImageView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.ImagePagerAdapter.3
                public AnonymousClass3() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (WatchPictureActivity.this.mIsToolbarHide) {
                        ((BaseFragmentActivity) WatchPictureActivity.this).titleBar.animate().setInterpolator(new FastOutSlowInInterpolator()).translationYBy(((BaseFragmentActivity) WatchPictureActivity.this).titleBar.getMeasuredHeight()).start();
                    } else {
                        ((BaseFragmentActivity) WatchPictureActivity.this).titleBar.animate().setInterpolator(new FastOutSlowInInterpolator()).translationYBy(((BaseFragmentActivity) WatchPictureActivity.this).titleBar.getMeasuredHeight()).start();
                    }
                    WatchPictureActivity.this.mIsToolbarHide = !r2.mIsToolbarHide;
                }
            });
            return viewInflate;
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$ImagePagerAdapter$1 */
        public class AnonymousClass1 implements View.OnClickListener {
            final /* synthetic */ int val$position;

            public AnonymousClass1(int i2) {
                i = i2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (WatchPictureActivity.this.photoList.get(i).isVideo()) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    WatchPictureActivity watchPictureActivity = WatchPictureActivity.this;
                    intent.setDataAndType(watchPictureActivity.photoList.get(watchPictureActivity.currentPosition).uri, "video/*");
                    intent.addFlags(1);
                    try {
                        WatchPictureActivity.this.startActivity(intent);
                    } catch (ActivityNotFoundException unused) {
                        t.a(R.string.ysf_error_no_video_activity);
                    }
                }
            }
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$ImagePagerAdapter$2 */
        public class AnonymousClass2 implements Runnable {
            final /* synthetic */ int val$position;

            public AnonymousClass2(int i2) {
                i = i2;
            }

            @Override // java.lang.Runnable
            public void run() throws Throwable {
                WatchPictureActivity watchPictureActivity2 = WatchPictureActivity.this;
                watchPictureActivity2.updateImage(watchPictureActivity2.photoList.get(i), false);
            }
        }

        /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$ImagePagerAdapter$3 */
        public class AnonymousClass3 implements View.OnClickListener {
            public AnonymousClass3() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (WatchPictureActivity.this.mIsToolbarHide) {
                    ((BaseFragmentActivity) WatchPictureActivity.this).titleBar.animate().setInterpolator(new FastOutSlowInInterpolator()).translationYBy(((BaseFragmentActivity) WatchPictureActivity.this).titleBar.getMeasuredHeight()).start();
                } else {
                    ((BaseFragmentActivity) WatchPictureActivity.this).titleBar.animate().setInterpolator(new FastOutSlowInInterpolator()).translationYBy(((BaseFragmentActivity) WatchPictureActivity.this).titleBar.getMeasuredHeight()).start();
                }
                WatchPictureActivity.this.mIsToolbarHide = !r2.mIsToolbarHide;
            }
        }
    }

    public void updateImage(Item item, boolean z) throws Throwable {
        BaseZoomableImageView baseZoomableImageViewImageViewOfItem = imageViewOfItem(item);
        if (baseZoomableImageViewImageViewOfItem == null) {
            return;
        }
        if (c.f().isUseSAF && !item.isVideo() && !item.isImage()) {
            baseZoomableImageViewImageViewOfItem.setImageBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.ysf_message_file_icon_unknown_preview)).getBitmap());
        } else {
            if (TextUtils.isEmpty(item.uri.toString())) {
                return;
            }
            Point bitmapSize = PhotoMetadataUtils.getBitmapSize(item.getContentUri(), this);
            com.qiyukf.uikit.a.a(item.uri.toString(), bitmapSize.x, bitmapSize.y, new ImageLoaderListener() { // from class: com.qiyukf.unicorn.ui.activity.WatchPictureActivity.3
                final /* synthetic */ BaseZoomableImageView val$imageView;

                public AnonymousClass3(BaseZoomableImageView baseZoomableImageViewImageViewOfItem2) {
                    baseZoomableImageView = baseZoomableImageViewImageViewOfItem2;
                }

                @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                public void onLoadComplete(@NonNull Bitmap bitmap) {
                    baseZoomableImageView.setImageBitmap(bitmap);
                }

                @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                public void onLoadFailed(Throwable th) {
                    if (th != null) {
                        AbsUnicornLog.e(WatchPictureActivity.TAG, "ImageEngineImpl loadImage is error", th);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.unicorn.ui.activity.WatchPictureActivity$3 */
    public class AnonymousClass3 implements ImageLoaderListener {
        final /* synthetic */ BaseZoomableImageView val$imageView;

        public AnonymousClass3(BaseZoomableImageView baseZoomableImageViewImageViewOfItem2) {
            baseZoomableImageView = baseZoomableImageViewImageViewOfItem2;
        }

        @Override // com.qiyukf.unicorn.api.ImageLoaderListener
        public void onLoadComplete(@NonNull Bitmap bitmap) {
            baseZoomableImageView.setImageBitmap(bitmap);
        }

        @Override // com.qiyukf.unicorn.api.ImageLoaderListener
        public void onLoadFailed(Throwable th) {
            if (th != null) {
                AbsUnicornLog.e(WatchPictureActivity.TAG, "ImageEngineImpl loadImage is error", th);
            }
        }
    }

    public void finishIntent() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SelectedItemCollection.STATE_SELECTION, this.photoList);
        intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE, bundle);
        setResult(-1, intent);
        finish();
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        finishIntent();
        super.onBackPressed();
    }

    private MediaPlayer getVideoMediaPlayer(File file) {
        try {
            return MediaPlayer.create(this, Uri.fromFile(file));
        } catch (Exception e) {
            AbsUnicornLog.e(TAG, "getVideoMediaPlayer is error", e);
            return null;
        }
    }
}

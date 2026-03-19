package com.petkit.android.activities.community;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.browser.browseractions.BrowserServiceFileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.chat.downloadManager.downloader.DLCons;
import com.petkit.android.activities.community.fragment.PhotoFolderFragment;
import com.petkit.android.activities.community.fragment.PhotoPreviewFragment;
import com.petkit.android.activities.community.localphoto.PhotoInfo;
import com.petkit.android.activities.community.localphoto.PhotoSerializable;
import com.petkit.android.activities.feeder.setting.esptouch.util.ByteUtil;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.model.Topic;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.FileUtils;
import com.petkit.oversea.R;
import com.qiyukf.unicorn.mediaselect.internal.loader.AlbumLoader;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* JADX INFO: loaded from: classes3.dex */
public class SelectPhotoActivity extends FragmentActivity implements PhotoFolderFragment.OnPageLodingClickListener, PhotoFolderFragment.OnPhotoSelectClickListener, View.OnClickListener, PhotoPreviewFragment.OnPageSelectedistener {
    private static final int VIEW_STATE_HIGHLIGHT = 0;
    private static final int VIEW_STATE_NORMAL = 1;
    private ImageButton btnback;
    private ArrayList<PhotoInfo> filterList;
    private ArrayList<PhotoInfo> hasList;
    private String localTempImageFileName;
    private BroadcastReceiver mBroadcastReceiver;
    private PhotoInfo mCurPhoto;
    private FragmentManager manager;
    private PhotoFolderFragment photoFolderFragment;
    private ImageView rightImage;
    private TextView selectedCount;
    private RelativeLayout selectedLayout;
    private TextView title;
    private View titleView;
    private String topic;
    private ArrayList<Topic> topics;
    private int backInt = 0;
    private boolean publishFromPost = false;
    private boolean isFromPopMenu = false;
    private int mMaxPhotoCount = 0;
    Uri uri = null;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_normal_fragment);
        if (bundle != null) {
            this.hasList = (ArrayList) bundle.getSerializable("photo_list");
            this.filterList = (ArrayList) bundle.getSerializable("photo_filterlist");
            this.localTempImageFileName = bundle.getString(Constants.EXTRA_STRING_ID);
            this.publishFromPost = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.isFromPopMenu = bundle.getBoolean("from_popMenu");
            this.topics = (ArrayList) bundle.getSerializable(Constants.EXTRA_TOPIC);
            this.mMaxPhotoCount = bundle.getInt("MaxPhotoCount");
        } else {
            this.hasList = (ArrayList) getIntent().getSerializableExtra("photo_list");
            this.filterList = (ArrayList) getIntent().getSerializableExtra("photo_filterlist");
            this.localTempImageFileName = getIntent().getStringExtra(Constants.EXTRA_STRING_ID);
            this.publishFromPost = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
            this.isFromPopMenu = getIntent().getBooleanExtra("from_popMenu", false);
            this.topics = (ArrayList) getIntent().getSerializableExtra(Constants.EXTRA_TOPIC);
            this.mMaxPhotoCount = getIntent().getIntExtra("MaxPhotoCount", 9);
            this.topic = getIntent().getStringExtra(Constants.EXTRA_PUBLISH_TOPIC);
        }
        this.manager = getSupportFragmentManager();
        if (this.hasList == null) {
            this.hasList = new ArrayList<>();
        }
        findViewById(R.id.selected_already).setOnClickListener(this);
        this.selectedCount = (TextView) findViewById(R.id.selected_count);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.selected_layout);
        this.selectedLayout = relativeLayout;
        relativeLayout.setVisibility(0);
        this.titleView = findViewById(R.id.layout_title_view);
        ImageView imageView = (ImageView) findViewById(R.id.title_right_image);
        this.rightImage = imageView;
        imageView.setOnClickListener(this);
        this.btnback = (ImageButton) findViewById(R.id.title_left_btn);
        TextView textView = (TextView) findViewById(R.id.title_name);
        this.title = textView;
        textView.setOnClickListener(this);
        this.btnback.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.SelectPhotoActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(view);
            }
        });
        this.title.setText(getString(R.string.Select_photo));
        this.title.setCompoundDrawablePadding((int) DeviceUtils.dpToPixel(this, 8.0f));
        this.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_arrow_bottom_small, 0);
        this.selectedCount.setText(this.hasList.size() + "");
        this.photoFolderFragment = new PhotoFolderFragment();
        Bundle bundle2 = new Bundle();
        PhotoSerializable photoSerializable = new PhotoSerializable();
        photoSerializable.setList(this.hasList);
        bundle2.putSerializable("list", photoSerializable);
        bundle2.putInt("MaxPhotoCount", this.mMaxPhotoCount);
        this.photoFolderFragment.setArguments(bundle2);
        FragmentTransaction fragmentTransactionBeginTransaction = this.manager.beginTransaction();
        fragmentTransactionBeginTransaction.add(R.id.body, this.photoFolderFragment);
        fragmentTransactionBeginTransaction.addToBackStack(null);
        fragmentTransactionBeginTransaction.commit();
        registerBoradcastReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        int i = this.backInt;
        if (i == 0) {
            finish();
            if (this.isFromPopMenu) {
                overridePendingTransition(R.anim.slide_none, R.anim.slide_out_to_top);
                return;
            }
            return;
        }
        if (i == 1) {
            this.backInt = i - 1;
            setViewState(1);
            this.manager.beginTransaction().show(this.photoFolderFragment).commit();
            this.manager.popBackStack(0, 0);
            this.photoFolderFragment.notifyDataSetChanged();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    public void setViewState(int i) {
        if (i == 0) {
            this.selectedLayout.setBackgroundColor(CommonUtils.getColorById(R.color.select_img_bg));
            this.titleView.setBackgroundColor(CommonUtils.getColorById(R.color.select_img_bg));
            this.rightImage.setImageResource(R.drawable.selector_gou_photo);
            this.btnback.setImageResource(R.drawable.btn_back_white);
            this.rightImage.setVisibility(0);
            this.title.setVisibility(8);
            findViewById(R.id.title_divider_line).setVisibility(8);
            findViewById(R.id.selected_line).setVisibility(8);
            return;
        }
        if (i != 1) {
            return;
        }
        this.selectedLayout.setBackgroundColor(CommonUtils.getColorById(R.color.white));
        this.titleView.setBackgroundColor(CommonUtils.getColorById(R.color.white));
        this.btnback.setImageResource(R.drawable.btn_back_gray);
        this.title.setVisibility(0);
        this.rightImage.setVisibility(8);
        findViewById(R.id.title_divider_line).setVisibility(0);
        findViewById(R.id.selected_line).setVisibility(0);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.publishFromPost);
        bundle.putSerializable("photo_list", this.hasList);
        bundle.putSerializable(Constants.EXTRA_TOPIC, this.topics);
        bundle.putSerializable("photo_filterlist", this.filterList);
        bundle.putString(Constants.EXTRA_STRING_ID, this.localTempImageFileName);
        bundle.putBoolean("from_popMenu", this.isFromPopMenu);
        bundle.putInt("MaxPhotoCount", this.mMaxPhotoCount);
    }

    @Override // com.petkit.android.activities.community.fragment.PhotoFolderFragment.OnPageLodingClickListener
    public void onPageLodingClickListener(int i, List<PhotoInfo> list) {
        if (i < 0) {
            getPhotoFromCamera();
            return;
        }
        setViewState(0);
        this.mCurPhoto = list.get(i);
        int i2 = 0;
        while (true) {
            if (i2 >= this.hasList.size()) {
                break;
            }
            if (this.mCurPhoto.getImage_id() == this.hasList.get(i2).getImage_id()) {
                this.rightImage.setSelected(true);
                break;
            } else {
                this.rightImage.setSelected(false);
                i2++;
            }
        }
        FragmentTransaction fragmentTransactionBeginTransaction = this.manager.beginTransaction();
        Fragment photoPreviewFragment = new PhotoPreviewFragment();
        Bundle bundle = new Bundle();
        PhotoSerializable photoSerializable = new PhotoSerializable();
        photoSerializable.setList(list);
        bundle.putInt(AlbumLoader.COLUMN_COUNT, this.hasList.size());
        bundle.putInt("currentId", i);
        bundle.putSerializable("list", photoSerializable);
        photoPreviewFragment.setArguments(bundle);
        fragmentTransactionBeginTransaction.hide(this.photoFolderFragment).commit();
        FragmentTransaction fragmentTransactionBeginTransaction2 = this.manager.beginTransaction();
        fragmentTransactionBeginTransaction2.add(R.id.body, photoPreviewFragment);
        fragmentTransactionBeginTransaction2.setTransition(4097);
        fragmentTransactionBeginTransaction2.addToBackStack(null);
        fragmentTransactionBeginTransaction2.commit();
        this.backInt++;
    }

    @Override // com.petkit.android.activities.community.fragment.PhotoFolderFragment.OnPhotoSelectClickListener
    public void onPhotoSelectClickListener(PhotoInfo photoInfo) {
        if (photoInfo.getPath_file() != null) {
            if (photoInfo.isChoose()) {
                this.hasList.add(photoInfo);
            } else {
                Iterator<PhotoInfo> it = this.hasList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    PhotoInfo next = it.next();
                    if (next.getImage_id() == photoInfo.getImage_id()) {
                        this.hasList.remove(next);
                        break;
                    }
                }
            }
            this.selectedCount.setText(this.hasList.size() + "");
            return;
        }
        getPhotoFromCamera();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int i2;
        if (i == 4 && this.backInt == 0) {
            finish();
            if (this.isFromPopMenu) {
                overridePendingTransition(R.anim.slide_none, R.anim.slide_out_to_top);
            }
        } else if (i == 4 && (i2 = this.backInt) == 1) {
            this.backInt = i2 - 1;
            setViewState(1);
            this.manager.beginTransaction().show(this.photoFolderFragment).commit();
            this.manager.popBackStack(0, 0);
            this.photoFolderFragment.notifyDataSetChanged();
        }
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i != 1) {
                if (i == 2) {
                    this.filterList.addAll((ArrayList) intent.getSerializableExtra("photo_list"));
                    Intent intent2 = new Intent();
                    intent2.putExtra(Constants.EXTRA_PUBLISH_TOPIC, MqttTopic.MULTI_LEVEL_WILDCARD + getString(R.string.Walking_dog) + MqttTopic.MULTI_LEVEL_WILDCARD);
                    intent2.putExtra("photo_list", this.filterList);
                    intent2.putExtra(Constants.EXTRA_BOOLEAN, true);
                    setResult(-1, intent2);
                    finish();
                    overridePendingTransition(0, 0);
                    return;
                }
                return;
            }
            if (CommonUtils.isEmpty(this.localTempImageFileName)) {
                return;
            }
            this.localTempImageFileName = FileUtils.getAppCacheImageDirPath() + this.localTempImageFileName;
            if (Build.VERSION.SDK_INT >= 29 && this.uri != null) {
                try {
                    FileUtils.writeStringToFile(this.localTempImageFileName, ByteUtil.inputStreamToBytes(getContentResolver().openInputStream(this.uri)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPath_file("file://" + this.localTempImageFileName);
            photoInfo.setPath_absolute(this.localTempImageFileName);
            this.hasList.add(photoInfo);
            Intent intent3 = new Intent();
            if (getResources().getBoolean(R.bool.config_photo_fiter) && this.publishFromPost) {
                intent3.putExtra("from_camera", true);
                intent3.putExtra("photo_list", this.hasList);
                if (this.isFromPopMenu) {
                    intent3.putExtra("flag_forResult", false);
                    intent3.putExtra(Constants.EXTRA_TOPIC, this.topics);
                    intent3.putExtra(Constants.EXTRA_PUBLISH_TOPIC, this.topic);
                    intent3.setClass(this, PublishActivity.class);
                    startActivity(intent3);
                    finish();
                    return;
                }
                setResult(-1, intent3);
                finish();
                overridePendingTransition(0, 0);
                return;
            }
            intent3.putExtra("photo_list", this.hasList);
            setResult(-1, intent3);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
    }

    public void getPhotoFromCamera() {
        if (!CommonUtils.checkPermission(this, "android.permission.CAMERA")) {
            startActivity(PermissionDialogActivity.newIntent(this, getClass().getName(), "android.permission.CAMERA"));
            return;
        }
        this.localTempImageFileName = String.valueOf(new Date().getTime()) + BrowserServiceFileProvider.FILE_EXTENSION;
        File file = new File(FileUtils.getAppCacheImageDirPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file2 = new File(file, this.localTempImageFileName);
        intent.putExtra("orientation", 0);
        if (CommonUtils.getAppVersionCode(this) < 24) {
            intent.putExtra("output", Uri.fromFile(file2));
        } else if (Build.VERSION.SDK_INT < 29) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("_data", file2.getAbsolutePath());
            Uri uriInsert = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            this.uri = uriInsert;
            intent.putExtra("output", uriInsert);
        } else {
            ContentValues contentValues2 = new ContentValues(1);
            contentValues2.put("relative_path", "DCIM/");
            contentValues2.put("_display_name", new Date().getTime() + "camera.dest.jpg");
            contentValues2.put(DLCons.DBCons.TB_TASK_MIME_TYPE, TweetComposer.MIME_TYPE_JPEG);
            Uri uriInsert2 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues2);
            this.uri = uriInsert2;
            intent.putExtra("output", uriInsert2);
        }
        startActivityForResult(intent, 1);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_name) {
            this.photoFolderFragment.setAlbumListView(0);
            this.selectedLayout.setVisibility(8);
            return;
        }
        if (id == R.id.title_right_image) {
            if (view.isSelected()) {
                view.setSelected(false);
                this.mCurPhoto.setChoose(false);
                Iterator<PhotoInfo> it = this.hasList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    PhotoInfo next = it.next();
                    if (next.getImage_id() == this.mCurPhoto.getImage_id()) {
                        this.hasList.remove(next);
                        break;
                    }
                }
            } else if (this.hasList.size() < this.mMaxPhotoCount) {
                view.setSelected(true);
                this.mCurPhoto.setChoose(true);
                this.hasList.add(this.mCurPhoto);
            } else {
                PetkitToast.showShortToast(this, getString(R.string.ImagePicker_add_limited_format, "" + this.mMaxPhotoCount), R.drawable.toast_failed);
            }
            this.selectedCount.setText(this.hasList.size() + "");
            this.photoFolderFragment.setHasSelect(this.hasList.size());
            return;
        }
        if (id == R.id.selected_already) {
            if (this.hasList.size() > 0) {
                Intent intent = new Intent();
                if (getResources().getBoolean(R.bool.config_photo_fiter) && this.publishFromPost) {
                    if (this.isFromPopMenu) {
                        intent.putExtra("flag_forResult", false);
                        intent.putExtra("photo_list", this.hasList);
                        intent.putExtra(Constants.EXTRA_TOPIC, this.topics);
                        intent.putExtra(Constants.EXTRA_PUBLISH_TOPIC, MqttTopic.MULTI_LEVEL_WILDCARD + getString(R.string.Walking_dog) + MqttTopic.MULTI_LEVEL_WILDCARD);
                        intent.setClass(this, PublishActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    intent.putExtra("photo_list", this.hasList);
                    setResult(-1, intent);
                    finish();
                    return;
                }
                intent.putExtra("photo_list", this.hasList);
                setResult(-1, intent);
                finish();
                return;
            }
            PetkitToast.showShortToast(this, R.string.Error_image_picker_null);
        }
    }

    @Override // com.petkit.android.activities.community.fragment.PhotoPreviewFragment.OnPageSelectedistener
    public void onPageSelectedListener(PhotoInfo photoInfo) {
        this.mCurPhoto = photoInfo;
        for (int i = 0; i < this.hasList.size(); i++) {
            if (photoInfo.getImage_id() == this.hasList.get(i).getImage_id()) {
                this.rightImage.setSelected(true);
                return;
            }
            this.rightImage.setSelected(false);
        }
    }

    @Override // com.petkit.android.activities.community.fragment.PhotoFolderFragment.OnPageLodingClickListener
    public void onAlbumListClickListener(List<PhotoInfo> list) {
        this.selectedLayout.setVisibility(0);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.community.SelectPhotoActivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_MSG_FINISH_SELECTPHOTO)) {
                    SelectPhotoActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_FINISH_SELECTPHOTO);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

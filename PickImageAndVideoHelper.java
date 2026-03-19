package com.qiyukf.uikit.session.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.Fragment;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.m;
import com.qiyukf.nimlib.net.a.c.a;
import com.qiyukf.uikit.common.fragment.TFragment;
import com.qiyukf.uikit.session.activity.CaptureVideoActivity;
import com.qiyukf.uikit.session.activity.PickImageActivity;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.event.EventCallback;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.event.entry.RequestPermissionEventEntry;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.l;
import com.qiyukf.unicorn.mediaselect.Matisse;
import com.qiyukf.unicorn.mediaselect.MimeType;
import com.qiyukf.unicorn.n.b.e;
import com.qiyukf.unicorn.n.e.d;
import com.qiyukf.unicorn.n.g;
import com.qiyukf.unicorn.n.j;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import com.qiyukf.unicorn.widget.dialog.UnicornDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes6.dex */
public class PickImageAndVideoHelper {
    public static final int CAPTURE_VIDEO = 3;
    private static final int PICK_IMAGE_COUNT = 9;
    public static final int REQUEST_CODE_CHOOSE = 35;
    private static File videoFile;
    private static String videoFilePath;
    private VideoMessageHelperListener listener;

    public interface VideoMessageHelperListener {
        void onVideoPicked(File file, String str);
    }

    public PickImageAndVideoHelper(VideoMessageHelperListener videoMessageHelperListener) {
        this.listener = videoMessageHelperListener;
    }

    public static void showSelector(TFragment tFragment, int i, boolean z, String str, boolean z2) {
        CharSequence[] charSequenceArr;
        if (tFragment.isAdded()) {
            g.a(tFragment);
            if (z2) {
                charSequenceArr = new CharSequence[]{tFragment.getString(R.string.ysf_input_panel_take), tFragment.getString(R.string.ysf_pick_video_record), tFragment.getString(R.string.ysf_picker_image_choose_from_photo_album), tFragment.getString(R.string.ysf_picker_video_from_photo_album)};
            } else {
                charSequenceArr = new CharSequence[]{tFragment.getString(R.string.ysf_input_panel_take), tFragment.getString(R.string.ysf_picker_image_choose_from_photo_album)};
            }
            UnicornDialog.showItemsDialog(tFragment.getContext(), null, null, charSequenceArr, true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.1
                final /* synthetic */ boolean val$isNeedVideo;
                final /* synthetic */ boolean val$multiSelect;
                final /* synthetic */ String val$outPath;
                final /* synthetic */ int val$requestCode;

                public AnonymousClass1(int i2, String str2, boolean z3, boolean z22) {
                    i = i2;
                    str = str2;
                    z = z3;
                    z = z22;
                }

                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public void onClick(int i2) {
                    if (i2 == 0) {
                        PickImageAndVideoHelper.goCameraActivity(tFragment, i, str, z);
                        return;
                    }
                    if (i2 == 1) {
                        if (z) {
                            PickImageAndVideoHelper.goTakeVideoActivity(tFragment, i, str, z);
                            return;
                        } else {
                            PickImageAndVideoHelper.goAlbumActivity(tFragment, i, str, z);
                            return;
                        }
                    }
                    if (i2 == 2) {
                        PickImageAndVideoHelper.goAlbumActivity(tFragment, i, str, z);
                    } else if (i2 == 3) {
                        PickImageAndVideoHelper.goSelectVideoActivity(tFragment);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$1 */
    public class AnonymousClass1 implements UnicornDialog.OnClickListener {
        final /* synthetic */ boolean val$isNeedVideo;
        final /* synthetic */ boolean val$multiSelect;
        final /* synthetic */ String val$outPath;
        final /* synthetic */ int val$requestCode;

        public AnonymousClass1(int i2, String str2, boolean z3, boolean z22) {
            i = i2;
            str = str2;
            z = z3;
            z = z22;
        }

        @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
        public void onClick(int i2) {
            if (i2 == 0) {
                PickImageAndVideoHelper.goCameraActivity(tFragment, i, str, z);
                return;
            }
            if (i2 == 1) {
                if (z) {
                    PickImageAndVideoHelper.goTakeVideoActivity(tFragment, i, str, z);
                    return;
                } else {
                    PickImageAndVideoHelper.goAlbumActivity(tFragment, i, str, z);
                    return;
                }
            }
            if (i2 == 2) {
                PickImageAndVideoHelper.goAlbumActivity(tFragment, i, str, z);
            } else if (i2 == 3) {
                PickImageAndVideoHelper.goSelectVideoActivity(tFragment);
            }
        }
    }

    public static void goCameraActivity(Fragment fragment, int i, String str, boolean z) {
        if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null && !j.a(fragment.getContext(), l.d)) {
            UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5);
            if (unicornEventBaseEventOf != null) {
                List<String> listAsList = Arrays.asList(l.d);
                RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                requestPermissionEventEntry.setScenesType(7);
                requestPermissionEventEntry.setPermissionList(listAsList);
                unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, fragment.getContext(), new EventCallback() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.2
                    final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
                    final /* synthetic */ boolean val$multiSelect;
                    final /* synthetic */ String val$outPath;
                    final /* synthetic */ int val$requestCode;
                    final /* synthetic */ UnicornEventBase val$unicornEventBase;

                    public AnonymousClass2(int i2, String str2, boolean z2, UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
                        i = i2;
                        str = str2;
                        z = z2;
                        unicornEventBase = unicornEventBaseEventOf2;
                        requestPermissionEventEntry = requestPermissionEventEntry2;
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onProcessEventSuccess(Object obj) {
                        PickImageAndVideoHelper.checkPermissionAndPickImg(fragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onPorcessEventError() {
                        PickImageAndVideoHelper.checkPermissionAndPickImg(fragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onNotPorcessEvent() {
                        PickImageAndVideoHelper.checkPermissionAndPickImg(fragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onInterceptEvent() {
                        t.a(R.string.ysf_no_permission_camera);
                    }
                });
                return;
            }
            checkPermissionAndPickImg(fragment, i2, str2, z2, null, null);
            return;
        }
        checkPermissionAndPickImg(fragment, i2, str2, z2, null, null);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$2 */
    public class AnonymousClass2 implements EventCallback {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ boolean val$multiSelect;
        final /* synthetic */ String val$outPath;
        final /* synthetic */ int val$requestCode;
        final /* synthetic */ UnicornEventBase val$unicornEventBase;

        public AnonymousClass2(int i2, String str2, boolean z2, UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
            i = i2;
            str = str2;
            z = z2;
            unicornEventBase = unicornEventBaseEventOf2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onProcessEventSuccess(Object obj) {
            PickImageAndVideoHelper.checkPermissionAndPickImg(fragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onPorcessEventError() {
            PickImageAndVideoHelper.checkPermissionAndPickImg(fragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onNotPorcessEvent() {
            PickImageAndVideoHelper.checkPermissionAndPickImg(fragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onInterceptEvent() {
            t.a(R.string.ysf_no_permission_camera);
        }
    }

    public static void goTakeVideoActivity(TFragment tFragment, int i, String str, boolean z) {
        if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null && !j.a(tFragment.getContext(), l.d)) {
            UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5);
            if (unicornEventBaseEventOf != null) {
                List<String> listAsList = Arrays.asList(l.d);
                RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                requestPermissionEventEntry.setScenesType(1);
                requestPermissionEventEntry.setPermissionList(listAsList);
                unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, tFragment.getContext(), new EventCallback() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.3
                    final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
                    final /* synthetic */ UnicornEventBase val$unicornEventBase;

                    public AnonymousClass3(UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
                        unicornEventBase = unicornEventBaseEventOf2;
                        requestPermissionEventEntry = requestPermissionEventEntry2;
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onProcessEventSuccess(Object obj) {
                        PickImageAndVideoHelper.checkPermissionAndGoTakeVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onPorcessEventError() {
                        PickImageAndVideoHelper.checkPermissionAndGoTakeVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onNotPorcessEvent() {
                        PickImageAndVideoHelper.checkPermissionAndGoTakeVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onInterceptEvent() {
                        t.a(R.string.ysf_no_permission_video);
                    }
                });
                return;
            }
            checkPermissionAndGoTakeVideo(tFragment, null, null);
            return;
        }
        checkPermissionAndGoTakeVideo(tFragment, null, null);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$3 */
    public class AnonymousClass3 implements EventCallback {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ UnicornEventBase val$unicornEventBase;

        public AnonymousClass3(UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
            unicornEventBase = unicornEventBaseEventOf2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onProcessEventSuccess(Object obj) {
            PickImageAndVideoHelper.checkPermissionAndGoTakeVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onPorcessEventError() {
            PickImageAndVideoHelper.checkPermissionAndGoTakeVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onNotPorcessEvent() {
            PickImageAndVideoHelper.checkPermissionAndGoTakeVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onInterceptEvent() {
            t.a(R.string.ysf_no_permission_video);
        }
    }

    public static void goAlbumActivity(TFragment tFragment, int i, String str, boolean z) {
        if (c.f().isUseSAF) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("image/*");
            Intent intent2 = new Intent("android.intent.action.CHOOSER");
            intent2.putExtra("android.intent.extra.INTENT", intent);
            tFragment.startActivityForResult(intent2, i);
            return;
        }
        if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null && !j.a(tFragment.getContext(), l.e)) {
            UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5);
            if (unicornEventBaseEventOf != null) {
                List<String> listAsList = Arrays.asList(l.e);
                RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                requestPermissionEventEntry.setScenesType(6);
                requestPermissionEventEntry.setPermissionList(listAsList);
                unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, tFragment.getContext(), new EventCallback() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.4
                    final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
                    final /* synthetic */ boolean val$multiSelect;
                    final /* synthetic */ String val$outPath;
                    final /* synthetic */ int val$requestCode;
                    final /* synthetic */ UnicornEventBase val$unicornEventBase;

                    public AnonymousClass4(int i2, String str2, boolean z2, UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
                        i = i2;
                        str = str2;
                        z = z2;
                        unicornEventBase = unicornEventBaseEventOf2;
                        requestPermissionEventEntry = requestPermissionEventEntry2;
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onProcessEventSuccess(Object obj) {
                        PickImageAndVideoHelper.checkPermissionAndGoAlbum(tFragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onPorcessEventError() {
                        PickImageAndVideoHelper.checkPermissionAndGoAlbum(tFragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onNotPorcessEvent() {
                        PickImageAndVideoHelper.checkPermissionAndGoAlbum(tFragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onInterceptEvent() {
                        t.a(R.string.ysf_no_permission_photo);
                    }
                });
                return;
            }
            checkPermissionAndGoAlbum(tFragment, i2, str2, z2, null, null);
            return;
        }
        checkPermissionAndGoAlbum(tFragment, i2, str2, z2, null, null);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$4 */
    public class AnonymousClass4 implements EventCallback {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ boolean val$multiSelect;
        final /* synthetic */ String val$outPath;
        final /* synthetic */ int val$requestCode;
        final /* synthetic */ UnicornEventBase val$unicornEventBase;

        public AnonymousClass4(int i2, String str2, boolean z2, UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
            i = i2;
            str = str2;
            z = z2;
            unicornEventBase = unicornEventBaseEventOf2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onProcessEventSuccess(Object obj) {
            PickImageAndVideoHelper.checkPermissionAndGoAlbum(tFragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onPorcessEventError() {
            PickImageAndVideoHelper.checkPermissionAndGoAlbum(tFragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onNotPorcessEvent() {
            PickImageAndVideoHelper.checkPermissionAndGoAlbum(tFragment, i, str, z, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onInterceptEvent() {
            t.a(R.string.ysf_no_permission_photo);
        }
    }

    public static void goSelectVideoActivity(TFragment tFragment) {
        if (c.f().isUseSAF) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("video/*");
            Intent intent2 = new Intent("android.intent.action.CHOOSER");
            intent2.putExtra("android.intent.extra.INTENT", intent);
            tFragment.startActivityForResult(intent2, 2);
            return;
        }
        if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null && !j.a(tFragment.getContext(), l.f)) {
            UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5);
            if (unicornEventBaseEventOf != null) {
                List<String> listAsList = Arrays.asList(l.f);
                RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                requestPermissionEventEntry.setScenesType(4);
                requestPermissionEventEntry.setPermissionList(listAsList);
                unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, tFragment.getContext(), new EventCallback() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.5
                    final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
                    final /* synthetic */ UnicornEventBase val$unicornEventBase;

                    public AnonymousClass5(UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
                        unicornEventBase = unicornEventBaseEventOf2;
                        requestPermissionEventEntry = requestPermissionEventEntry2;
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onProcessEventSuccess(Object obj) {
                        PickImageAndVideoHelper.checkPermissionAndGoSelectVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onPorcessEventError() {
                        PickImageAndVideoHelper.checkPermissionAndGoSelectVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onNotPorcessEvent() {
                        PickImageAndVideoHelper.checkPermissionAndGoSelectVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onInterceptEvent() {
                        t.a(R.string.ysf_no_permission_photo);
                    }
                });
                return;
            }
            checkPermissionAndGoSelectVideo(tFragment, null, null);
            return;
        }
        checkPermissionAndGoSelectVideo(tFragment, null, null);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$5 */
    public class AnonymousClass5 implements EventCallback {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ UnicornEventBase val$unicornEventBase;

        public AnonymousClass5(UnicornEventBase unicornEventBaseEventOf2, RequestPermissionEventEntry requestPermissionEventEntry2) {
            unicornEventBase = unicornEventBaseEventOf2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onProcessEventSuccess(Object obj) {
            PickImageAndVideoHelper.checkPermissionAndGoSelectVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onPorcessEventError() {
            PickImageAndVideoHelper.checkPermissionAndGoSelectVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onNotPorcessEvent() {
            PickImageAndVideoHelper.checkPermissionAndGoSelectVideo(tFragment, unicornEventBase, requestPermissionEventEntry);
        }

        @Override // com.qiyukf.unicorn.api.event.EventCallback
        public void onInterceptEvent() {
            t.a(R.string.ysf_no_permission_photo);
        }
    }

    public static void chooseVideoFromCamera(TFragment tFragment, int i) {
        if (d.a(com.qiyukf.unicorn.n.e.c.TYPE_VIDEO)) {
            String strA = d.a(UUID.randomUUID().toString() + ".mp4", com.qiyukf.unicorn.n.e.c.TYPE_TEMP);
            videoFilePath = strA;
            if (strA == null) {
                Log.e("TAG", "videoFilePath = " + videoFilePath + "this is ");
                return;
            }
            videoFile = new File(videoFilePath);
            Log.e("TAG", "videoFile = " + videoFile + "this is ");
            CaptureVideoActivity.start(tFragment, videoFilePath, i);
        }
    }

    public static void onCaptureVideoResult(Intent intent, VideoMessageHelperListener videoMessageHelperListener) {
        if (intent.getIntExtra(CaptureVideoActivity.SELECT_VIDEO_TYPE_TAG, 0) == 0) {
            File file = videoFile;
            if (file == null || !file.exists()) {
                String stringExtra = intent.getStringExtra(CaptureVideoActivity.EXTRA_DATA_FILE_NAME);
                if (!TextUtils.isEmpty(stringExtra)) {
                    videoFile = new File(stringExtra);
                }
            }
            File file2 = videoFile;
            if (file2 == null || !file2.exists()) {
                return;
            }
            if (videoFile.length() <= 0) {
                videoFile.delete();
                return;
            }
            String path = videoFile.getPath();
            String strB = m.b(path);
            String strA = d.a(strB + ".mp4", com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
            if (!a.b(path, strA) || videoMessageHelperListener == null) {
                return;
            }
            videoMessageHelperListener.onVideoPicked(new File(strA), strB);
            return;
        }
        onCapturePageSelectLocalVideoResult(intent, videoMessageHelperListener);
    }

    private static void onCapturePageSelectLocalVideoResult(Intent intent, VideoMessageHelperListener videoMessageHelperListener) {
        if (intent == null) {
            return;
        }
        if (o.a()) {
            ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(CaptureVideoActivity.EXTRA_DATA_VIDEO_URL_LIST);
            if (parcelableArrayListExtra == null || parcelableArrayListExtra.size() == 0 || parcelableArrayListExtra.get(0) == null) {
                return;
            }
            String strA = m.a(com.qiyukf.nimlib.c.d(), (Uri) parcelableArrayListExtra.get(0));
            String strA2 = d.a(strA + "." + w.a(com.qiyukf.nimlib.c.d(), (Uri) parcelableArrayListExtra.get(0)), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
            if (!a.a(com.qiyukf.nimlib.c.d(), (Uri) parcelableArrayListExtra.get(0), strA2)) {
                t.a(R.string.ysf_video_exception);
                return;
            } else {
                if (videoMessageHelperListener != null) {
                    videoMessageHelperListener.onVideoPicked(new File(strA2), strA);
                    return;
                }
                return;
            }
        }
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(CaptureVideoActivity.EXTRA_DATA_VIDEO_PATH);
        if (stringArrayListExtra == null || stringArrayListExtra.size() == 0 || TextUtils.isEmpty(stringArrayListExtra.get(0))) {
            return;
        }
        String strB = m.b(stringArrayListExtra.get(0));
        String strA3 = d.a(strB + "." + e.a(stringArrayListExtra.get(0)), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (a.a(stringArrayListExtra.get(0), strA3) == -1) {
            t.a(R.string.ysf_video_exception);
        } else if (videoMessageHelperListener != null) {
            videoMessageHelperListener.onVideoPicked(new File(strA3), strB);
        }
    }

    public static void onSelectLocalVideoResult(Intent intent, VideoMessageHelperListener videoMessageHelperListener) {
        if (intent == null) {
            return;
        }
        if (o.a()) {
            List<Uri> listObtainResult = Matisse.obtainResult(intent);
            if (listObtainResult == null || listObtainResult.size() == 0 || listObtainResult.get(0) == null) {
                return;
            }
            String strA = m.a(com.qiyukf.nimlib.c.d(), listObtainResult.get(0));
            String strA2 = d.a(strA + "." + w.a(com.qiyukf.nimlib.c.d(), listObtainResult.get(0)), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
            if (!a.a(com.qiyukf.nimlib.c.d(), listObtainResult.get(0), strA2)) {
                t.a(R.string.ysf_video_exception);
                return;
            } else {
                if (videoMessageHelperListener != null) {
                    videoMessageHelperListener.onVideoPicked(new File(strA2), strA);
                    return;
                }
                return;
            }
        }
        List<String> listObtainPathResult = Matisse.obtainPathResult(intent);
        if (listObtainPathResult == null || listObtainPathResult.size() == 0 || TextUtils.isEmpty(listObtainPathResult.get(0))) {
            return;
        }
        String strB = m.b(listObtainPathResult.get(0));
        String strA3 = d.a(strB + "." + e.a(listObtainPathResult.get(0)), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (a.a(listObtainPathResult.get(0), strA3) == -1) {
            t.a(R.string.ysf_video_exception);
        } else if (videoMessageHelperListener != null) {
            videoMessageHelperListener.onVideoPicked(new File(strA3), strB);
        }
    }

    public static void checkPermissionAndPickImg(Fragment fragment, int i, String str, boolean z, UnicornEventBase unicornEventBase, RequestPermissionEventEntry requestPermissionEventEntry) {
        j.a(fragment).a(l.d).a(new j.a() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.6
            final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
            final /* synthetic */ Fragment val$fragment;
            final /* synthetic */ boolean val$multiSelect;
            final /* synthetic */ String val$outPath;
            final /* synthetic */ int val$requestCode;

            public AnonymousClass6(Fragment fragment2, RequestPermissionEventEntry requestPermissionEventEntry2, int i2, String str2, boolean z2) {
                fragment = fragment2;
                requestPermissionEventEntry = requestPermissionEventEntry2;
                i = i2;
                str = str2;
                z = z2;
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(fragment.getContext(), requestPermissionEventEntry);
                }
                PickImageActivity.start(fragment, i, 2, str, z, 1, false, false, 0, 0);
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(fragment.getContext(), requestPermissionEventEntry)) {
                    t.a(R.string.ysf_no_permission_camera);
                }
            }
        }).a();
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$6 */
    public class AnonymousClass6 implements j.a {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ Fragment val$fragment;
        final /* synthetic */ boolean val$multiSelect;
        final /* synthetic */ String val$outPath;
        final /* synthetic */ int val$requestCode;

        public AnonymousClass6(Fragment fragment2, RequestPermissionEventEntry requestPermissionEventEntry2, int i2, String str2, boolean z2) {
            fragment = fragment2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
            i = i2;
            str = str2;
            z = z2;
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onGranted() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 != null) {
                unicornEventBase2.onGrantEvent(fragment.getContext(), requestPermissionEventEntry);
            }
            PickImageActivity.start(fragment, i, 2, str, z, 1, false, false, 0, 0);
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onDenied() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(fragment.getContext(), requestPermissionEventEntry)) {
                t.a(R.string.ysf_no_permission_camera);
            }
        }
    }

    public static void checkPermissionAndGoTakeVideo(TFragment tFragment, UnicornEventBase unicornEventBase, RequestPermissionEventEntry requestPermissionEventEntry) {
        j.a(tFragment).a(l.d).a(new j.a() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.7
            final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
            final /* synthetic */ TFragment val$fragment;

            public AnonymousClass7(TFragment tFragment2, RequestPermissionEventEntry requestPermissionEventEntry2) {
                tFragment = tFragment2;
                requestPermissionEventEntry = requestPermissionEventEntry2;
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(tFragment.getContext(), requestPermissionEventEntry);
                }
                PickImageAndVideoHelper.chooseVideoFromCamera(tFragment, 1);
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(tFragment.getContext(), requestPermissionEventEntry)) {
                    t.a(R.string.ysf_no_permission_video);
                }
            }
        }).a();
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$7 */
    public class AnonymousClass7 implements j.a {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ TFragment val$fragment;

        public AnonymousClass7(TFragment tFragment2, RequestPermissionEventEntry requestPermissionEventEntry2) {
            tFragment = tFragment2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onGranted() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 != null) {
                unicornEventBase2.onGrantEvent(tFragment.getContext(), requestPermissionEventEntry);
            }
            PickImageAndVideoHelper.chooseVideoFromCamera(tFragment, 1);
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onDenied() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(tFragment.getContext(), requestPermissionEventEntry)) {
                t.a(R.string.ysf_no_permission_video);
            }
        }
    }

    public static void checkPermissionAndGoAlbum(TFragment tFragment, int i, String str, boolean z, UnicornEventBase unicornEventBase, RequestPermissionEventEntry requestPermissionEventEntry) {
        j.a(tFragment).a(l.e).a(new j.a() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.8
            final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
            final /* synthetic */ TFragment val$fragment;
            final /* synthetic */ boolean val$multiSelect;
            final /* synthetic */ String val$outPath;
            final /* synthetic */ int val$requestCode;

            public AnonymousClass8(TFragment tFragment2, RequestPermissionEventEntry requestPermissionEventEntry2, int i2, String str2, boolean z2) {
                tFragment = tFragment2;
                requestPermissionEventEntry = requestPermissionEventEntry2;
                i = i2;
                str = str2;
                z = z2;
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(tFragment.getContext(), requestPermissionEventEntry);
                }
                PickImageActivity.start((Fragment) tFragment, i, 1, str, z, 9, false, false, 0, 0);
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(tFragment.getContext(), requestPermissionEventEntry)) {
                    t.a(R.string.ysf_no_permission_photo);
                }
            }
        }).a();
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$8 */
    public class AnonymousClass8 implements j.a {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ TFragment val$fragment;
        final /* synthetic */ boolean val$multiSelect;
        final /* synthetic */ String val$outPath;
        final /* synthetic */ int val$requestCode;

        public AnonymousClass8(TFragment tFragment2, RequestPermissionEventEntry requestPermissionEventEntry2, int i2, String str2, boolean z2) {
            tFragment = tFragment2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
            i = i2;
            str = str2;
            z = z2;
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onGranted() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 != null) {
                unicornEventBase2.onGrantEvent(tFragment.getContext(), requestPermissionEventEntry);
            }
            PickImageActivity.start((Fragment) tFragment, i, 1, str, z, 9, false, false, 0, 0);
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onDenied() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(tFragment.getContext(), requestPermissionEventEntry)) {
                t.a(R.string.ysf_no_permission_photo);
            }
        }
    }

    public static void checkPermissionAndGoSelectVideo(TFragment tFragment, UnicornEventBase unicornEventBase, RequestPermissionEventEntry requestPermissionEventEntry) {
        j.a(tFragment).a(l.f).a(new j.a() { // from class: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.9
            final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
            final /* synthetic */ TFragment val$fragment;

            public AnonymousClass9(TFragment tFragment2, RequestPermissionEventEntry requestPermissionEventEntry2) {
                tFragment = tFragment2;
                requestPermissionEventEntry = requestPermissionEventEntry2;
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(tFragment.getContext(), requestPermissionEventEntry);
                }
                Matisse.startSelectMediaFile(tFragment, MimeType.ofVideo(), 9, 2);
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(tFragment.getContext(), requestPermissionEventEntry)) {
                    t.a(R.string.ysf_no_permission_photo);
                }
            }
        }).a();
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.PickImageAndVideoHelper$9 */
    public class AnonymousClass9 implements j.a {
        final /* synthetic */ RequestPermissionEventEntry val$eventEntry;
        final /* synthetic */ TFragment val$fragment;

        public AnonymousClass9(TFragment tFragment2, RequestPermissionEventEntry requestPermissionEventEntry2) {
            tFragment = tFragment2;
            requestPermissionEventEntry = requestPermissionEventEntry2;
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onGranted() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 != null) {
                unicornEventBase2.onGrantEvent(tFragment.getContext(), requestPermissionEventEntry);
            }
            Matisse.startSelectMediaFile(tFragment, MimeType.ofVideo(), 9, 2);
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onDenied() {
            UnicornEventBase unicornEventBase2 = unicornEventBase;
            if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(tFragment.getContext(), requestPermissionEventEntry)) {
                t.a(R.string.ysf_no_permission_photo);
            }
        }
    }

    public static void onPickVideoResult(Context context, Intent intent, VideoMessageHelperListener videoMessageHelperListener) {
        Uri data = intent.getData();
        if (data == null || context == null) {
            return;
        }
        String strA = m.a(context, data);
        String strA2 = d.a(strA + "." + e.a(data.toString()), com.qiyukf.unicorn.n.e.c.TYPE_IMAGE);
        AbsUnicornLog.d("onPickVideoResult", "md5Path = ".concat(String.valueOf(strA2)));
        if (!a.a(context, data, strA2)) {
            t.a(R.string.ysf_media_exception);
            return;
        }
        if (strA2 == null || videoMessageHelperListener == null) {
            return;
        }
        File file = new File(strA2);
        if (file.length() > 52428800) {
            return;
        }
        videoMessageHelperListener.onVideoPicked(file, strA);
    }
}

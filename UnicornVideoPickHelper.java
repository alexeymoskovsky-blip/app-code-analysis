package com.qiyukf.unicorn.api.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import com.qiyukf.nimlib.n.m;
import com.qiyukf.nimlib.net.a.c.a;
import com.qiyukf.uikit.session.activity.CaptureVideoActivity;
import com.qiyukf.uikit.session.helper.VideoMsgHelper;
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
import com.qiyukf.unicorn.n.j;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.n.w;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class UnicornVideoPickHelper {
    private UincornVideoSelectListener uincornVideoSelectListener;
    private VideoMsgHelper videoMsgHelper;

    public interface UincornVideoSelectListener {
        void onVideoPicked(File file, String str);
    }

    public UnicornVideoPickHelper(Activity activity, final UincornVideoSelectListener uincornVideoSelectListener) {
        this.uincornVideoSelectListener = uincornVideoSelectListener;
        this.videoMsgHelper = new VideoMsgHelper(activity, new VideoMsgHelper.VideoMessageHelperListener() { // from class: com.qiyukf.unicorn.api.helper.UnicornVideoPickHelper.1
            @Override // com.qiyukf.uikit.session.helper.VideoMsgHelper.VideoMessageHelperListener
            public void onVideoPicked(File file, String str) {
                if (UnicornVideoPickHelper.this.uincornVideoSelectListener != null) {
                    uincornVideoSelectListener.onVideoPicked(file, str);
                }
            }
        });
    }

    public void goCaptureVideo(int i) {
        VideoMsgHelper videoMsgHelper = this.videoMsgHelper;
        if (videoMsgHelper != null) {
            videoMsgHelper.goCaptureVideo(i);
        }
    }

    public void goVideoAlbum(int i) {
        VideoMsgHelper videoMsgHelper = this.videoMsgHelper;
        if (videoMsgHelper != null) {
            videoMsgHelper.goVideoAlbum(i);
        }
    }

    public void onCaptureVideoResult(Intent intent) {
        VideoMsgHelper videoMsgHelper;
        if (intent == null || (videoMsgHelper = this.videoMsgHelper) == null) {
            return;
        }
        videoMsgHelper.onCaptureVideoResult(intent);
    }

    public void onSelectLocalVideoResult(Intent intent) {
        VideoMsgHelper videoMsgHelper;
        if (intent == null || (videoMsgHelper = this.videoMsgHelper) == null) {
            return;
        }
        videoMsgHelper.onSelectLocalVideoResult(intent);
    }

    public static void goVideoAlbumActivity(final Fragment fragment, final int i) {
        if (c.f().isUseSAF) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("video/*");
            Intent intent2 = new Intent("android.intent.action.CHOOSER");
            intent2.putExtra("android.intent.extra.INTENT", intent);
            fragment.startActivityForResult(intent2, i);
            return;
        }
        if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null && !j.a(fragment.getContext(), l.f)) {
            final UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5);
            if (unicornEventBaseEventOf != null) {
                List<String> listAsList = Arrays.asList(l.f);
                final RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                requestPermissionEventEntry.setScenesType(4);
                requestPermissionEventEntry.setPermissionList(listAsList);
                unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, fragment.getContext(), new EventCallback() { // from class: com.qiyukf.unicorn.api.helper.UnicornVideoPickHelper.2
                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onProcessEventSuccess(Object obj) {
                        UnicornVideoPickHelper.checkPermissionAndGoSelectVideo(fragment, i, unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onPorcessEventError() {
                        UnicornVideoPickHelper.checkPermissionAndGoSelectVideo(fragment, i, unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onNotPorcessEvent() {
                        UnicornVideoPickHelper.checkPermissionAndGoSelectVideo(fragment, i, unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onInterceptEvent() {
                        t.a(R.string.ysf_no_permission_photo);
                    }
                });
                return;
            }
            checkPermissionAndGoSelectVideo(fragment, i, null, null);
            return;
        }
        checkPermissionAndGoSelectVideo(fragment, i, null, null);
    }

    public static void goVideoActivity(Fragment fragment, String str, int i) {
        CaptureVideoActivity.start(fragment, str, i);
    }

    public static void onCaptureVideoResult(String str, UincornVideoSelectListener uincornVideoSelectListener) {
        String strB = m.b(str);
        String strA = d.a(strB + ".mp4", com.qiyukf.unicorn.n.e.c.TYPE_AUDIO);
        if (!a.b(str, strA)) {
            t.b(R.string.ysf_video_exception);
        } else if (uincornVideoSelectListener != null) {
            uincornVideoSelectListener.onVideoPicked(new File(strA), strB);
        }
    }

    public static void onSelectLocalVideoResult(String str, UincornVideoSelectListener uincornVideoSelectListener) {
        String strB = m.b(str);
        String strA = d.a(strB + "." + e.a(str), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (a.a(str, strA) == -1) {
            t.a(R.string.ysf_video_exception);
        } else if (uincornVideoSelectListener != null) {
            uincornVideoSelectListener.onVideoPicked(new File(strA), strB);
        }
    }

    public static void onSelectLocalVideoResult(Uri uri, UincornVideoSelectListener uincornVideoSelectListener) {
        if (uri == null) {
            return;
        }
        String strA = m.a(com.qiyukf.nimlib.c.d(), uri);
        String strA2 = d.a(strA + "." + w.a(com.qiyukf.nimlib.c.d(), uri), com.qiyukf.unicorn.n.e.c.TYPE_VIDEO);
        if (!a.a(com.qiyukf.nimlib.c.d(), uri, strA2)) {
            t.a(R.string.ysf_video_exception);
        } else if (uincornVideoSelectListener != null) {
            uincornVideoSelectListener.onVideoPicked(new File(strA2), strA);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkPermissionAndGoSelectVideo(final Fragment fragment, final int i, final UnicornEventBase unicornEventBase, final RequestPermissionEventEntry requestPermissionEventEntry) {
        j.a(fragment).a(l.f).a(new j.a() { // from class: com.qiyukf.unicorn.api.helper.UnicornVideoPickHelper.3
            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(fragment.getContext(), requestPermissionEventEntry);
                }
                Matisse.startSelectMediaFile(fragment, MimeType.ofVideo(), 9, i);
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(fragment.getContext(), requestPermissionEventEntry)) {
                    t.a(R.string.ysf_no_permission_photo);
                }
            }
        }).a();
    }
}

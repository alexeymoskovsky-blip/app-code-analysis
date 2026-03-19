package com.qiyukf.unicorn.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.auto.service.AutoService;
import com.qiyukf.basemodule.interfaces.UnicornService;
import com.qiyukf.basemodule.interfaces.VideoImageLoaderListener;
import com.qiyukf.basemodule.interfaces.VideoRequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.uikit.a;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.api.OnVideoFloatBackIntent;
import com.qiyukf.unicorn.api.event.EventCallback;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.event.entry.RequestPermissionEventEntry;
import com.qiyukf.unicorn.h.a.f.ag;
import com.qiyukf.unicorn.i.a.c;
import com.qiyukf.unicorn.n.j;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes6.dex */
@AutoService({UnicornService.class})
public class UnicornServiceImpl implements UnicornService {
    private Context mContext;

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void loadImageHead(String str, int i, int i2, final VideoImageLoaderListener videoImageLoaderListener) {
        ImageLoaderListener imageLoaderListener = new ImageLoaderListener() { // from class: com.qiyukf.unicorn.video.UnicornServiceImpl.1
            @Override // com.qiyukf.unicorn.api.ImageLoaderListener
            public void onLoadComplete(@NonNull Bitmap bitmap) {
                videoImageLoaderListener.onLoadComplete(bitmap);
            }

            @Override // com.qiyukf.unicorn.api.ImageLoaderListener
            public void onLoadFailed(Throwable th) {
                videoImageLoaderListener.onLoadFailed(th);
            }
        };
        Bitmap bitmapB = a.b(str);
        if (bitmapB != null) {
            imageLoaderListener.onLoadComplete(bitmapB);
        } else {
            a.a(str, i, i2, imageLoaderListener);
        }
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void urlPost(final String str, final Map<String, String> map, final VideoRequestCallback<String> videoRequestCallback) {
        final RequestCallback<String> requestCallback = new RequestCallback<String>() { // from class: com.qiyukf.unicorn.video.UnicornServiceImpl.2
            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public void onSuccess(String str2) {
                videoRequestCallback.onSuccess(str2);
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public void onFailed(int i) {
                videoRequestCallback.onFailed(i);
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public void onException(Throwable th) {
                videoRequestCallback.onException(th);
            }
        };
        new com.qiyukf.unicorn.n.a<Void, Void>(com.qiyukf.unicorn.n.a.HTTP_TAG) { // from class: com.qiyukf.unicorn.video.UnicornServiceImpl.3
            @Override // com.qiyukf.unicorn.n.a
            public Void doInBackground(Void[] voidArr) {
                c.b(str, (Map<String, String>) map, (RequestCallback<String>) requestCallback);
                return null;
            }
        }.execute(new Void[0]);
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void urlGet(final String str, final Map<String, String> map, final VideoRequestCallback<String> videoRequestCallback) {
        final RequestCallback<String> requestCallback = new RequestCallback<String>() { // from class: com.qiyukf.unicorn.video.UnicornServiceImpl.4
            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public void onSuccess(String str2) {
                videoRequestCallback.onSuccess(str2);
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public void onFailed(int i) {
                videoRequestCallback.onFailed(i);
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public void onException(Throwable th) {
                videoRequestCallback.onException(th);
            }
        };
        new com.qiyukf.unicorn.n.a<Void, Void>(com.qiyukf.unicorn.n.a.HTTP_TAG) { // from class: com.qiyukf.unicorn.video.UnicornServiceImpl.5
            @Override // com.qiyukf.unicorn.n.a
            public Void doInBackground(Void[] voidArr) {
                c.a(str, (Map<String, String>) map, (RequestCallback<String>) requestCallback);
                return null;
            }
        }.execute(new Void[0]);
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void sendCmd(int i, String str) {
        if (i != 11095) {
            return;
        }
        ag agVar = new ag();
        agVar.a(str);
        com.qiyukf.unicorn.k.c.a(agVar, com.qiyukf.unicorn.k.c.a());
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void requestPermissions(final Context context, List<String> list, int i, final VideoRequestCallback<String> videoRequestCallback) {
        if ((list == null || list.size() == 0) && videoRequestCallback != null) {
            videoRequestCallback.onSuccess("");
        }
        this.mContext = context;
        final String[] strArr = (String[]) list.toArray(new String[list.size()]);
        if (com.qiyukf.unicorn.c.f().sdkEvents != null && com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory != null && !j.a(context, strArr)) {
            final UnicornEventBase unicornEventBaseEventOf = com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory.eventOf(5);
            if (unicornEventBaseEventOf != null) {
                final RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                requestPermissionEventEntry.setScenesType(i);
                requestPermissionEventEntry.setPermissionList(list);
                unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, context, new EventCallback() { // from class: com.qiyukf.unicorn.video.UnicornServiceImpl.6
                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onProcessEventSuccess(Object obj) {
                        UnicornServiceImpl.this.checkPermissionAndSave(context, strArr, videoRequestCallback, unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onPorcessEventError() {
                        UnicornServiceImpl.this.checkPermissionAndSave(context, strArr, videoRequestCallback, unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onNotPorcessEvent() {
                        UnicornServiceImpl.this.checkPermissionAndSave(context, strArr, videoRequestCallback, unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onInterceptEvent() {
                        videoRequestCallback.onFailed(-1);
                    }
                });
                return;
            }
            checkPermissionAndSave(context, strArr, videoRequestCallback, null, null);
            return;
        }
        checkPermissionAndSave(context, strArr, videoRequestCallback, null, null);
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void requestPermissionsResult(int i, String[] strArr, int[] iArr) {
        j.a(this.mContext, i, strArr, iArr);
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void onUrlClick(Context context, String str) {
        OnMessageItemClickListener onMessageItemClickListener = com.qiyukf.unicorn.c.f().onMessageItemClickListener;
        if (onMessageItemClickListener != null) {
            onMessageItemClickListener.onURLClicked(context, str);
        } else {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public boolean openActivity(Context context) {
        OnVideoFloatBackIntent onVideoFloatBackIntent = com.qiyukf.unicorn.c.f().onVideoFloatBackIntent;
        if (onVideoFloatBackIntent == null) {
            return false;
        }
        onVideoFloatBackIntent.onVideoFloatBackIntent(context);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPermissionAndSave(final Context context, String[] strArr, final VideoRequestCallback<String> videoRequestCallback, final UnicornEventBase unicornEventBase, final RequestPermissionEventEntry requestPermissionEventEntry) {
        j.a((Activity) context).a(strArr).a(new j.a() { // from class: com.qiyukf.unicorn.video.UnicornServiceImpl.7
            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(context, requestPermissionEventEntry);
                }
                VideoRequestCallback videoRequestCallback2 = videoRequestCallback;
                if (videoRequestCallback2 != null) {
                    videoRequestCallback2.onSuccess("");
                }
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                VideoRequestCallback videoRequestCallback2;
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if ((unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(context, requestPermissionEventEntry)) && (videoRequestCallback2 = videoRequestCallback) != null) {
                    videoRequestCallback2.onFailed(-1);
                }
            }
        }).a();
    }

    @Override // com.qiyukf.basemodule.interfaces.UnicornService
    public void receiveRegisterCmds(Set<Integer> set) {
        com.qiyukf.unicorn.c.h().a(set);
    }
}

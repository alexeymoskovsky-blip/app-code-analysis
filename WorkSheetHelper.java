package com.qiyukf.uikit.session.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.h.a.a.a.w;
import com.qiyukf.unicorn.h.a.a.a.x;
import com.qiyukf.unicorn.h.a.b;
import com.qiyukf.unicorn.h.a.f.aj;
import com.qiyukf.unicorn.mediaselect.Matisse;
import com.qiyukf.unicorn.mediaselect.MimeType;
import com.qiyukf.unicorn.mediaselect.internal.entity.Item;
import com.qiyukf.unicorn.ui.activity.WatchPictureActivity;
import com.qiyukf.unicorn.ui.worksheet.d;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes6.dex */
public class WorkSheetHelper {
    private CmdRequstCallback cmdRequstCallback;
    private Observer<CustomNotification> customNotificationObserver;
    private d dialog;
    private Fragment fragment;
    private int photoListNum = 0;
    private int selectAnnexRequestCode;
    private int watchAnnexRequestCode;

    public interface CmdRequstCallback {
        void onResponse(boolean z, String str);
    }

    public WorkSheetHelper(Fragment fragment) {
        this.fragment = fragment;
        initObserver();
    }

    private void registerObserver(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.customNotificationObserver, z);
    }

    private void initObserver() {
        this.customNotificationObserver = new WorkSheetHelper$$ExternalSyntheticLambda0(this);
    }

    public /* synthetic */ void lambda$initObserver$3047f1c8$1(CustomNotification customNotification) {
        b attachStr;
        CmdRequstCallback cmdRequstCallback;
        if (customNotification.getSessionType() == SessionTypeEnum.Ysf && (attachStr = b.parseAttachStr(customNotification.getContent())) != null) {
            if (attachStr.getCmdId() == 11036) {
                w wVar = (w) attachStr;
                if (wVar.a() != null && wVar.a().c() && (cmdRequstCallback = this.cmdRequstCallback) != null) {
                    cmdRequstCallback.onResponse(true, wVar.a().g());
                    return;
                }
            }
            CmdRequstCallback cmdRequstCallback2 = this.cmdRequstCallback;
            if (cmdRequstCallback2 != null) {
                cmdRequstCallback2.onResponse(false, "");
            }
        }
    }

    public void openWorkSheetDialog(final long j, final String str, final int i, final int i2, final RequestCallback<String> requestCallback) {
        this.watchAnnexRequestCode = i;
        this.selectAnnexRequestCode = i2;
        Fragment fragment = this.fragment;
        if (fragment == null || fragment.isDetached() || this.fragment.getActivity() == null || this.fragment.getActivity().isFinishing()) {
            return;
        }
        if (this.customNotificationObserver == null) {
            initObserver();
        }
        if (this.cmdRequstCallback == null) {
            this.cmdRequstCallback = new CmdRequstCallback() { // from class: com.qiyukf.uikit.session.helper.WorkSheetHelper$$ExternalSyntheticLambda1
                @Override // com.qiyukf.uikit.session.helper.WorkSheetHelper.CmdRequstCallback
                public final void onResponse(boolean z, String str2) {
                    this.f$0.lambda$openWorkSheetDialog$0(j, str, i, i2, requestCallback, z, str2);
                }
            };
        }
        registerObserver(true);
        aj ajVar = new aj();
        ajVar.b(j);
        ajVar.a(c.h().d(str));
        com.qiyukf.unicorn.k.c.a(ajVar, str);
    }

    public /* synthetic */ void lambda$openWorkSheetDialog$0(long j, String str, int i, int i2, RequestCallback requestCallback, boolean z, String str2) {
        if (z) {
            goToThirdSystemWorkSheetUrl(this.fragment.getContext(), str2);
        } else {
            showWorkSheetDialog(j, str, i, i2, requestCallback);
        }
        registerObserver(false);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.WorkSheetHelper$1 */
    public class AnonymousClass1 implements d.a {
        final /* synthetic */ RequestCallback val$requestCallback;
        final /* synthetic */ int val$selectAnnexRequestCode;
        final /* synthetic */ int val$watchAnnexRequestCode;

        public AnonymousClass1(RequestCallback requestCallback, int i, int i2) {
            requestCallback = requestCallback;
            i = i;
            i = i2;
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void onSubmitDone(String str) {
            RequestCallback requestCallback = requestCallback;
            if (requestCallback != null) {
                requestCallback.onSuccess(str);
            }
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i) {
            WorkSheetHelper.this.photoListNum = 1;
            WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i, i);
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpSelectAnnexActivity(int i) {
            WorkSheetHelper.this.photoListNum = 1;
            Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i, i);
        }
    }

    private void showWorkSheetDialog(long j, String str, int i, int i2, RequestCallback<String> requestCallback) {
        d dVar = new d(this.fragment, j, str, new d.a() { // from class: com.qiyukf.uikit.session.helper.WorkSheetHelper.1
            final /* synthetic */ RequestCallback val$requestCallback;
            final /* synthetic */ int val$selectAnnexRequestCode;
            final /* synthetic */ int val$watchAnnexRequestCode;

            public AnonymousClass1(RequestCallback requestCallback2, int i3, int i22) {
                requestCallback = requestCallback2;
                i = i3;
                i = i22;
            }

            @Override // com.qiyukf.unicorn.ui.worksheet.d.a
            public void onSubmitDone(String str2) {
                RequestCallback requestCallback2 = requestCallback;
                if (requestCallback2 != null) {
                    requestCallback2.onSuccess(str2);
                }
            }

            @Override // com.qiyukf.unicorn.ui.worksheet.d.a
            public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i3) {
                WorkSheetHelper.this.photoListNum = 1;
                WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i3, i);
            }

            @Override // com.qiyukf.unicorn.ui.worksheet.d.a
            public void jumpSelectAnnexActivity(int i3) {
                WorkSheetHelper.this.photoListNum = 1;
                Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i3, i);
            }
        }, new d.a() { // from class: com.qiyukf.uikit.session.helper.WorkSheetHelper.2
            final /* synthetic */ RequestCallback val$requestCallback;
            final /* synthetic */ int val$selectAnnexRequestCode;
            final /* synthetic */ int val$watchAnnexRequestCode;

            public AnonymousClass2(RequestCallback requestCallback2, int i3, int i22) {
                requestCallback = requestCallback2;
                i = i3;
                i = i22;
            }

            @Override // com.qiyukf.unicorn.ui.worksheet.d.a
            public void onSubmitDone(String str2) {
                RequestCallback requestCallback2 = requestCallback;
                if (requestCallback2 != null) {
                    requestCallback2.onSuccess(str2);
                }
            }

            @Override // com.qiyukf.unicorn.ui.worksheet.d.a
            public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i3) {
                WorkSheetHelper.this.photoListNum = 2;
                WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i3, i);
            }

            @Override // com.qiyukf.unicorn.ui.worksheet.d.a
            public void jumpSelectAnnexActivity(int i3) {
                WorkSheetHelper.this.photoListNum = 2;
                Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i3, i);
            }
        });
        this.dialog = dVar;
        dVar.show();
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.WorkSheetHelper$2 */
    public class AnonymousClass2 implements d.a {
        final /* synthetic */ RequestCallback val$requestCallback;
        final /* synthetic */ int val$selectAnnexRequestCode;
        final /* synthetic */ int val$watchAnnexRequestCode;

        public AnonymousClass2(RequestCallback requestCallback2, int i3, int i22) {
            requestCallback = requestCallback2;
            i = i3;
            i = i22;
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void onSubmitDone(String str2) {
            RequestCallback requestCallback2 = requestCallback;
            if (requestCallback2 != null) {
                requestCallback2.onSuccess(str2);
            }
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i3) {
            WorkSheetHelper.this.photoListNum = 2;
            WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i3, i);
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpSelectAnnexActivity(int i3) {
            WorkSheetHelper.this.photoListNum = 2;
            Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i3, i);
        }
    }

    public void openWorkSheetDialog(x xVar, String str, int i, int i2, RequestCallback<String> requestCallback) {
        this.watchAnnexRequestCode = i;
        this.selectAnnexRequestCode = i2;
        if (this.fragment == null) {
            return;
        }
        if (xVar.c()) {
            if (TextUtils.isEmpty(xVar.g())) {
                return;
            }
            goToThirdSystemWorkSheetUrl(this.fragment.getContext(), xVar.g());
        } else {
            d dVar = new d(this.fragment, xVar, str, new d.a() { // from class: com.qiyukf.uikit.session.helper.WorkSheetHelper.3
                final /* synthetic */ RequestCallback val$requestCallback;
                final /* synthetic */ int val$selectAnnexRequestCode;
                final /* synthetic */ int val$watchAnnexRequestCode;

                public AnonymousClass3(RequestCallback requestCallback2, int i3, int i22) {
                    requestCallback = requestCallback2;
                    i = i3;
                    i = i22;
                }

                @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                public void onSubmitDone(String str2) {
                    RequestCallback requestCallback2 = requestCallback;
                    if (requestCallback2 != null) {
                        requestCallback2.onSuccess(str2);
                    }
                }

                @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i3) {
                    WorkSheetHelper.this.photoListNum = 1;
                    WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i3, i);
                }

                @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                public void jumpSelectAnnexActivity(int i3) {
                    WorkSheetHelper.this.photoListNum = 1;
                    Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i3, i);
                }
            }, new d.a() { // from class: com.qiyukf.uikit.session.helper.WorkSheetHelper.4
                final /* synthetic */ RequestCallback val$requestCallback;
                final /* synthetic */ int val$selectAnnexRequestCode;
                final /* synthetic */ int val$watchAnnexRequestCode;

                public AnonymousClass4(RequestCallback requestCallback2, int i3, int i22) {
                    requestCallback = requestCallback2;
                    i = i3;
                    i = i22;
                }

                @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                public void onSubmitDone(String str2) {
                    RequestCallback requestCallback2 = requestCallback;
                    if (requestCallback2 != null) {
                        requestCallback2.onSuccess(str2);
                    }
                }

                @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i3) {
                    WorkSheetHelper.this.photoListNum = 2;
                    WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i3, i);
                }

                @Override // com.qiyukf.unicorn.ui.worksheet.d.a
                public void jumpSelectAnnexActivity(int i3) {
                    WorkSheetHelper.this.photoListNum = 2;
                    Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i3, i);
                }
            });
            this.dialog = dVar;
            dVar.show();
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.WorkSheetHelper$3 */
    public class AnonymousClass3 implements d.a {
        final /* synthetic */ RequestCallback val$requestCallback;
        final /* synthetic */ int val$selectAnnexRequestCode;
        final /* synthetic */ int val$watchAnnexRequestCode;

        public AnonymousClass3(RequestCallback requestCallback2, int i3, int i22) {
            requestCallback = requestCallback2;
            i = i3;
            i = i22;
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void onSubmitDone(String str2) {
            RequestCallback requestCallback2 = requestCallback;
            if (requestCallback2 != null) {
                requestCallback2.onSuccess(str2);
            }
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i3) {
            WorkSheetHelper.this.photoListNum = 1;
            WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i3, i);
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpSelectAnnexActivity(int i3) {
            WorkSheetHelper.this.photoListNum = 1;
            Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i3, i);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.helper.WorkSheetHelper$4 */
    public class AnonymousClass4 implements d.a {
        final /* synthetic */ RequestCallback val$requestCallback;
        final /* synthetic */ int val$selectAnnexRequestCode;
        final /* synthetic */ int val$watchAnnexRequestCode;

        public AnonymousClass4(RequestCallback requestCallback2, int i3, int i22) {
            requestCallback = requestCallback2;
            i = i3;
            i = i22;
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void onSubmitDone(String str2) {
            RequestCallback requestCallback2 = requestCallback;
            if (requestCallback2 != null) {
                requestCallback2.onSuccess(str2);
            }
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpWatchImgActivity(ArrayList<Item> arrayList, int i3) {
            WorkSheetHelper.this.photoListNum = 2;
            WatchPictureActivity.start(WorkSheetHelper.this.fragment, arrayList, i3, i);
        }

        @Override // com.qiyukf.unicorn.ui.worksheet.d.a
        public void jumpSelectAnnexActivity(int i3) {
            WorkSheetHelper.this.photoListNum = 2;
            Matisse.startSelectMediaFile(WorkSheetHelper.this.fragment, MimeType.ofAll(), i3, i);
        }
    }

    public void onResultWorkSheet(int i, Intent intent) {
        d dVar;
        if (intent == null || i == 0 || (dVar = this.dialog) == null) {
            return;
        }
        if (i == this.watchAnnexRequestCode) {
            dVar.b(intent, this.photoListNum);
        } else if (i == this.selectAnnexRequestCode || i == 17) {
            dVar.a(intent, this.photoListNum);
        }
        this.photoListNum = 0;
    }

    public static void goToThirdSystemWorkSheetUrl(Context context, String str) {
        OnMessageItemClickListener onMessageItemClickListener = c.f().onMessageItemClickListener;
        if (onMessageItemClickListener != null) {
            onMessageItemClickListener.onURLClicked(context, str);
        } else {
            try {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            } catch (Exception unused) {
            }
        }
    }
}

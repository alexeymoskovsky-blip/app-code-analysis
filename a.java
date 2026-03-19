package com.qiyukf.uikit.session.module.a;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.w;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.attachment.AudioAttachment;
import com.qiyukf.nimlib.sdk.msg.attachment.FileAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.AttachmentProgress;
import com.qiyukf.nimlib.sdk.msg.model.CustomNotification;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.nimlib.session.MsgDBHelper;
import com.qiyukf.uikit.b.b;
import com.qiyukf.uikit.common.a.e;
import com.qiyukf.uikit.common.a.f;
import com.qiyukf.uikit.common.fragment.TFragment;
import com.qiyukf.uikit.common.ui.listview.AutoRefreshListView;
import com.qiyukf.uikit.common.ui.listview.ListViewUtil;
import com.qiyukf.uikit.common.ui.listview.MessageListView;
import com.qiyukf.uikit.session.helper.PickImageAndVideoHelper;
import com.qiyukf.uikit.session.helper.QuoteMsgHelper;
import com.qiyukf.uikit.session.helper.SendImageHelper;
import com.qiyukf.uikit.session.helper.WorkSheetHelper;
import com.qiyukf.uikit.session.module.a.b;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderFactory;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.h.a.a.a.j;
import com.qiyukf.unicorn.h.a.a.a.x;
import com.qiyukf.unicorn.h.a.d.ag;
import com.qiyukf.unicorn.h.a.d.az;
import com.qiyukf.unicorn.h.a.f.ac;
import com.qiyukf.unicorn.h.a.f.q;
import com.qiyukf.unicorn.n.aa;
import com.qiyukf.unicorn.n.e.d;
import com.qiyukf.unicorn.n.g;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.ui.fragment.TranslateFragment;
import com.qiyukf.unicorn.ui.viewholder.a.o;
import com.qiyukf.unicorn.widget.ItemBlackPopupWindow;
import com.qiyukf.unicorn.widget.dialog.UnicornDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: compiled from: MessageListPanel.java */
/* JADX INFO: loaded from: classes6.dex */
public final class a implements e {
    public volatile boolean a;
    private boolean b;
    private com.qiyukf.uikit.session.module.a c;
    private View d;
    private MessageListView e;
    private List<IMMessage> f;
    private com.qiyukf.uikit.session.module.a.b g;
    private ImageView h;
    private Handler i;
    private View j;
    private TextView k;
    private ImageView l;
    private ImageView m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private int r;
    private SendImageHelper.Callback s;
    private WorkSheetHelper t;
    private Observer<CustomNotification> u;
    private PickImageAndVideoHelper.VideoMessageHelperListener v;
    private Observer<IMMessage> w;
    private Observer<AttachmentProgress> x;
    private b.a y;
    private Runnable z;

    @Override // com.qiyukf.uikit.common.a.e
    public final boolean b() {
        return false;
    }

    public a(com.qiyukf.uikit.session.module.a aVar, View view) {
        this(aVar, view, (byte) 0);
    }

    private a(com.qiyukf.uikit.session.module.a aVar, View view, byte b2) {
        int i;
        this.b = true;
        this.a = false;
        this.p = false;
        this.q = false;
        this.r = 0;
        this.u = new Observer<CustomNotification>() { // from class: com.qiyukf.uikit.session.module.a.a.8
            @Override // com.qiyukf.nimlib.sdk.Observer
            public final /* synthetic */ void onEvent(CustomNotification customNotification) {
                CustomNotification customNotification2 = customNotification;
                if (TextUtils.equals(a.this.c.c, customNotification2.getSessionId()) && customNotification2.getSessionType() == SessionTypeEnum.Ysf) {
                    a.this.a(customNotification2);
                }
            }
        };
        this.v = new PickImageAndVideoHelper.VideoMessageHelperListener() { // from class: com.qiyukf.uikit.session.module.a.a.9
            @Override // com.qiyukf.uikit.session.helper.PickImageAndVideoHelper.VideoMessageHelperListener
            public final void onVideoPicked(File file, String str) {
                MediaPlayer mediaPlayerA = a.this.a(file);
                a.this.c.e.sendMessage(MessageBuilder.createVideoMessage(a.this.c.c, SessionTypeEnum.Ysf, file, mediaPlayerA == null ? 0L : mediaPlayerA.getDuration(), mediaPlayerA == null ? 0 : mediaPlayerA.getVideoWidth(), mediaPlayerA == null ? 0 : mediaPlayerA.getVideoHeight(), file.getName()), false);
            }
        };
        this.w = new Observer<IMMessage>() { // from class: com.qiyukf.uikit.session.module.a.a.10
            @Override // com.qiyukf.nimlib.sdk.Observer
            public final /* synthetic */ void onEvent(IMMessage iMMessage) {
                IMMessage iMMessage2 = iMMessage;
                if (a.this.c(iMMessage2)) {
                    a.b(a.this, iMMessage2);
                }
            }
        };
        this.x = new Observer<AttachmentProgress>() { // from class: com.qiyukf.uikit.session.module.a.a.11
            @Override // com.qiyukf.nimlib.sdk.Observer
            public final /* synthetic */ void onEvent(AttachmentProgress attachmentProgress) {
                a.a(a.this, attachmentProgress);
            }
        };
        this.z = new Runnable() { // from class: com.qiyukf.uikit.session.module.a.a.4
            @Override // java.lang.Runnable
            public final void run() {
                a.this.j.setVisibility(8);
            }
        };
        this.c = aVar;
        this.d = view;
        this.n = false;
        this.o = false;
        this.f = new ArrayList();
        com.qiyukf.uikit.session.module.a.b bVar = new com.qiyukf.uikit.session.module.a.b(this.c.a, this.f, this);
        this.g = bVar;
        bVar.a(new b(this, (byte) 0));
        this.h = (ImageView) this.d.findViewById(R.id.message_activity_background);
        MessageListView messageListView = (MessageListView) this.d.findViewById(R.id.messageListView);
        this.e = messageListView;
        messageListView.requestDisallowInterceptTouchEvent(true);
        if (this.n && !this.o) {
            this.e.setMode(AutoRefreshListView.Mode.BOTH);
        } else {
            this.e.setMode(AutoRefreshListView.Mode.START);
        }
        this.e.setOverScrollMode(2);
        this.e.setAdapter((BaseAdapter) this.g);
        this.e.setListViewEventListener(new MessageListView.OnListViewEventListener() { // from class: com.qiyukf.uikit.session.module.a.a.5
            @Override // com.qiyukf.uikit.common.ui.listview.MessageListView.OnListViewEventListener
            public final void onListViewStartScroll() {
                a.this.c.e.shouldCollapseInputPanel();
            }

            @Override // com.qiyukf.uikit.common.ui.listview.MessageListView.OnListViewEventListener
            public final void onSizeChanged(int i2, int i3, int i4, int i5) {
                if (i5 - i3 > p.d() + p.e() || a.this.g()) {
                    ListViewUtil.scrollToBottom(a.this.e);
                }
            }

            @Override // com.qiyukf.uikit.common.ui.listview.MessageListView.OnListViewEventListener
            public final void onListViewTouched() {
                g.a(a.this.c.b);
            }
        });
        this.e.setOnRefreshListener(new C0168a(this.o));
        this.i = new Handler();
        b(true);
        this.j = this.d.findViewById(R.id.play_audio_mode_tips_bar);
        this.k = (TextView) this.d.findViewById(R.id.play_audio_mode_tips_label);
        this.l = (ImageView) this.d.findViewById(R.id.ysf_play_audio_mode_tip_close);
        this.m = (ImageView) this.d.findViewById(R.id.play_audio_mode_tips_indicator);
        this.l.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.uikit.session.module.a.a.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                if (a.this.j != null) {
                    a.this.j.setVisibility(8);
                }
            }
        });
        UICustomization uICustomization = c.f().uiCustomization;
        if (uICustomization != null && (i = uICustomization.msgListViewDividerHeight) > 0) {
            this.e.setDividerHeight(i);
        }
        if (p.b(this.c.a)) {
            this.e.setPadding(p.d(), 0, p.d(), 0);
        }
    }

    public final void c() {
        this.i.removeCallbacks(null);
        b(false);
    }

    public final void a(com.qiyukf.uikit.session.module.a aVar) {
        this.c = aVar;
        this.f.clear();
        this.e.setOnRefreshListener(new C0168a(this.o));
    }

    public final void a(Configuration configuration) {
        if (p.f()) {
            if (configuration.orientation == 2) {
                this.e.setPadding(p.d(), 0, p.d(), 0);
            } else {
                this.e.setPadding(0, 0, 0, 0);
            }
        }
    }

    public final void d() {
        this.c.a.runOnUiThread(new Runnable() { // from class: com.qiyukf.uikit.session.module.a.a.6
            @Override // java.lang.Runnable
            public final void run() {
                a.this.g.notifyDataSetChanged();
            }
        });
    }

    public final void e() {
        this.q = true;
    }

    public final void f() {
        this.q = false;
    }

    public final boolean g() {
        return ListViewUtil.isAtBottom(this.e);
    }

    public final void h() {
        a(false, false);
    }

    public final void i() {
        ListViewUtil.smoothScrollToPosition(this.e, r0.getAdapter().getCount() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final boolean z, final boolean z2) {
        this.i.postDelayed(new Runnable() { // from class: com.qiyukf.uikit.session.module.a.a.7
            @Override // java.lang.Runnable
            public final void run() {
                if (z) {
                    ListViewUtil.smoothScrollToPositionFromTop(a.this.e, a.this.g.getCount(), 0, z2 ? 500 : 100);
                } else {
                    ListViewUtil.scrollToBottom(a.this.e);
                }
            }
        }, 10L);
    }

    public final void a(List<IMMessage> list) {
        ArrayList arrayList = new ArrayList(list.size());
        boolean z = false;
        int i = 0;
        for (IMMessage iMMessage : list) {
            if (c(iMMessage)) {
                this.f.add(iMMessage);
                arrayList.add(iMMessage);
                i++;
                com.qiyukf.nimlib.log.c.b.a.d("MessageListPanel", "onIncomingMessage: add message " + iMMessage.getUuid());
                z = true;
            }
            if (iMMessage.getAttachment() instanceof j) {
                o.b(iMMessage.getUuid());
            }
        }
        k();
        if (z) {
            this.g.notifyDataSetChanged();
        }
        this.g.a(arrayList, false, true);
        if (i > 0) {
            a(true, false);
        }
        if (c.h().p(this.c.c) == null || c.h().g(this.c.c) != 0 || this.q) {
            return;
        }
        ac acVar = new ac();
        acVar.a(String.valueOf(c.h().d(this.c.c)));
        com.qiyukf.unicorn.k.c.a(acVar, list.get(0) != null ? list.get(0).getSessionId() : this.c.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        int i = 0;
        for (int size = this.g.getItems().size() - 1; size >= 0; size--) {
            if ((this.g.getItems().get(size).getAttachment() instanceof ag) && (i = i + 1) >= 2) {
                this.g.getItems().remove(size);
            }
        }
    }

    public final void a(CustomNotification customNotification) {
        List<IMMessage> list;
        com.qiyukf.unicorn.h.a.b attachStr = com.qiyukf.unicorn.h.a.b.parseAttachStr(customNotification.getContent());
        if (attachStr == null || attachStr.getCmdId() != 2) {
            return;
        }
        com.qiyukf.unicorn.h.a.d.a aVar = (com.qiyukf.unicorn.h.a.d.a) attachStr;
        if (aVar.b() == 200 && (this.a || com.qiyukf.unicorn.d.c.w(this.c.c) != aVar.f())) {
            com.qiyukf.unicorn.d.c.d(this.c.c, aVar.f());
            return;
        }
        if (this.a || (list = this.f) == null || list.size() != 0) {
            return;
        }
        AutoRefreshListView.OnRefreshListener refreshListener = this.e.getRefreshListener();
        this.a = true;
        refreshListener.onRefreshFromStart(0);
    }

    public final void a(int i, int i2, Intent intent) {
        WorkSheetHelper workSheetHelper;
        if (i2 != -1) {
            return;
        }
        if (i == 1) {
            PickImageAndVideoHelper.onCaptureVideoResult(intent, this.v);
            return;
        }
        if (i == 2) {
            if (c.f().isUseSAF) {
                PickImageAndVideoHelper.onPickVideoResult(this.c.a, intent, this.v);
                return;
            } else {
                PickImageAndVideoHelper.onSelectLocalVideoResult(intent, this.v);
                return;
            }
        }
        if (i == 8) {
            SendImageHelper.onPickImageActivityResult(this.c.b, intent, 9, this.s);
            return;
        }
        if (i == 9) {
            SendImageHelper.onPreviewImageActivityResult(this.c.b, intent, i, 8, this.s);
            return;
        }
        if (i != 17) {
            if (i == 18 && (workSheetHelper = this.t) != null) {
                workSheetHelper.onResultWorkSheet(18, intent);
                return;
            }
            return;
        }
        WorkSheetHelper workSheetHelper2 = this.t;
        if (workSheetHelper2 != null) {
            workSheetHelper2.onResultWorkSheet(17, intent);
        }
    }

    public final void a(IMMessage iMMessage) {
        this.f.add(iMMessage);
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(iMMessage);
        this.g.a(arrayList, false, true);
        this.g.notifyDataSetChanged();
        if (this.q) {
            this.r++;
        }
        ListViewUtil.scrollToBottom(this.e);
    }

    @Override // com.qiyukf.uikit.common.a.e
    public final int a() {
        return MsgViewHolderFactory.getViewTypeCount();
    }

    @Override // com.qiyukf.uikit.common.a.e
    public final Class<? extends f> a(int i) {
        return MsgViewHolderFactory.getViewHolderByType(this.f.get(i));
    }

    private void b(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeCustomNotification(this.u, z);
        MsgServiceObserve msgServiceObserve = (MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class);
        msgServiceObserve.observeMsgStatus(this.w, z);
        msgServiceObserve.observeAttachmentProgress(this.x, z);
        if (z) {
            l();
        } else {
            m();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(IMMessage iMMessage) {
        return iMMessage.getSessionType() == this.c.d && iMMessage.getSessionId() != null && iMMessage.getSessionId().equals(this.c.c);
    }

    private void c(final int i) {
        this.c.a.runOnUiThread(new Runnable() { // from class: com.qiyukf.uikit.session.module.a.a.12
            @Override // java.lang.Runnable
            public final void run() {
                if (i < 0) {
                    return;
                }
                Object viewHolderByIndex = ListViewUtil.getViewHolderByIndex(a.this.e, i);
                if (viewHolderByIndex instanceof MsgViewHolderBase) {
                    ((MsgViewHolderBase) viewHolderByIndex).refreshCurrentItem();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b(String str) {
        for (int i = 0; i < this.f.size(); i++) {
            if (TextUtils.equals(this.f.get(i).getUuid(), str)) {
                return i;
            }
        }
        return -1;
    }

    public final void a(String str, int i) {
        if (com.qiyukf.uikit.a.c(str)) {
            com.qiyukf.uikit.a.a(str, p.a(), p.b(), new ImageLoaderListener() { // from class: com.qiyukf.uikit.session.module.a.a.2
                @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                public final void onLoadFailed(Throwable th) {
                }

                @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                public final void onLoadComplete(@NonNull Bitmap bitmap) {
                    a.this.h.setImageBitmap(bitmap);
                }
            });
        } else if (i != 0) {
            this.h.setBackgroundColor(i);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.module.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: MessageListPanel.java */
    public class C0168a implements AutoRefreshListView.OnRefreshListener {
        private boolean d;
        private QueryDirectionEnum b = null;
        private boolean e = true;
        private RequestCallback<List<IMMessage>> f = new RequestCallbackWrapper<List<IMMessage>>() { // from class: com.qiyukf.uikit.session.module.a.a.a.1
            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
            public final /* synthetic */ void onResult(int i, List<IMMessage> list, Throwable th) {
                List<IMMessage> list2 = list;
                if (list2 != null) {
                    if (C0168a.a(C0168a.this)) {
                        if (!a.this.a && !a.this.b && !c.f().isDefaultLoadMsg && list2.size() != 0) {
                            com.qiyukf.unicorn.h.a.f.b bVar = new com.qiyukf.unicorn.h.a.f.b();
                            bVar.a(a.this.c.a.getString(R.string.ysf_last_message_history));
                            IMMessage iMMessageCreateCustomMessage = MessageBuilder.createCustomMessage(com.qiyukf.nimlib.c.q(), SessionTypeEnum.Ysf, bVar);
                            iMMessageCreateCustomMessage.setStatus(MsgStatusEnum.success);
                            list2.add(iMMessageCreateCustomMessage);
                        }
                        a.this.a = true;
                        C0168a.a(C0168a.this, list2);
                        return;
                    }
                    C0168a.a(C0168a.this, new ArrayList());
                }
            }
        };
        private IMMessage c = null;

        public C0168a(boolean z) {
            this.d = z;
            if (z) {
                a();
            } else {
                a(QueryDirectionEnum.QUERY_OLD, 0);
            }
        }

        private void a(QueryDirectionEnum queryDirectionEnum, int i) {
            a.this.b = i == 0;
            this.b = queryDirectionEnum;
            a.this.e.onRefreshStart(queryDirectionEnum == QueryDirectionEnum.QUERY_NEW ? AutoRefreshListView.Mode.END : AutoRefreshListView.Mode.START);
            if (com.qiyukf.nimlib.database.f.a().c()) {
                ((MsgService) NIMClient.getService(MsgService.class)).queryMessageListEx(b(), queryDirectionEnum, 20, true).setCallback(this.f);
            }
        }

        private void a() {
            this.b = QueryDirectionEnum.QUERY_OLD;
            ((MsgService) NIMClient.getService(MsgService.class)).pullMessageHistory(b(), 20, true).setCallback(this.f);
        }

        private IMMessage b() {
            if (a.this.f.size() != 0) {
                return (IMMessage) a.this.f.get(this.b == QueryDirectionEnum.QUERY_NEW ? a.this.f.size() - 1 : 0);
            }
            IMMessage iMMessage = this.c;
            return iMMessage == null ? MessageBuilder.createEmptyMessage(a.this.c.c, a.this.c.d, 0L) : iMMessage;
        }

        @Override // com.qiyukf.uikit.common.ui.listview.AutoRefreshListView.OnRefreshListener
        public final void onRefreshFromStart(int i) {
            if (this.d) {
                a();
            } else {
                a(QueryDirectionEnum.QUERY_OLD, i);
            }
        }

        @Override // com.qiyukf.uikit.common.ui.listview.AutoRefreshListView.OnRefreshListener
        public final void onRefreshFromEnd() {
            if (this.d) {
                return;
            }
            a(QueryDirectionEnum.QUERY_NEW, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static /* synthetic */ boolean a(com.qiyukf.uikit.session.module.a.a.C0168a r7) {
            /*
                com.qiyukf.unicorn.b r0 = com.qiyukf.unicorn.c.i()
                r1 = 0
                if (r0 != 0) goto L9
                r0 = 0
                goto L15
            L9:
                com.qiyukf.uikit.session.module.a.a r2 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r2 = com.qiyukf.uikit.session.module.a.a.b(r2)
                java.lang.String r2 = r2.c
                int r0 = r0.a(r2)
            L15:
                com.qiyukf.unicorn.k.d r2 = com.qiyukf.unicorn.c.h()
                com.qiyukf.uikit.session.module.a.a r3 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r3 = com.qiyukf.uikit.session.module.a.a.b(r3)
                java.lang.String r3 = r3.c
                com.qiyukf.unicorn.f.p r2 = r2.c(r3)
                com.qiyukf.unicorn.c.h()
                com.qiyukf.uikit.session.module.a.a r3 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r3 = com.qiyukf.uikit.session.module.a.a.b(r3)
                java.lang.String r3 = r3.c
                com.qiyukf.nimlib.sdk.msg.model.IMMessage r3 = com.qiyukf.unicorn.k.d.k(r3)
                if (r3 == 0) goto L47
                com.qiyukf.unicorn.c.h()
                com.qiyukf.uikit.session.module.a.a r3 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r3 = com.qiyukf.uikit.session.module.a.a.b(r3)
                java.lang.String r3 = r3.c
                boolean r3 = com.qiyukf.unicorn.k.d.l(r3)
                if (r3 == 0) goto L9d
            L47:
                com.qiyukf.unicorn.api.YSFOptions r3 = com.qiyukf.unicorn.c.f()
                boolean r3 = r3.isDefaultLoadMsg
                if (r3 != 0) goto L9d
                if (r0 > 0) goto L9d
                com.qiyukf.uikit.session.module.a.a r3 = com.qiyukf.uikit.session.module.a.a.this
                boolean r3 = r3.a
                if (r3 != 0) goto L9d
                if (r2 == 0) goto L5d
                boolean r2 = r2.c
                if (r2 != 0) goto L9d
            L5d:
                com.qiyukf.unicorn.k.d r2 = com.qiyukf.unicorn.c.h()
                com.qiyukf.uikit.session.module.a.a r3 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r3 = com.qiyukf.uikit.session.module.a.a.b(r3)
                java.lang.String r3 = r3.c
                long r2 = r2.d(r3)
                r4 = 0
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 == 0) goto L93
                com.qiyukf.uikit.session.module.a.a r2 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r2 = com.qiyukf.uikit.session.module.a.a.b(r2)
                java.lang.String r2 = r2.c
                long r2 = com.qiyukf.unicorn.d.c.w(r2)
                com.qiyukf.unicorn.k.d r4 = com.qiyukf.unicorn.c.h()
                com.qiyukf.uikit.session.module.a.a r5 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r5 = com.qiyukf.uikit.session.module.a.a.b(r5)
                java.lang.String r5 = r5.c
                long r4 = r4.d(r5)
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 == 0) goto L9d
            L93:
                com.qiyukf.uikit.session.module.a.a r2 = com.qiyukf.uikit.session.module.a.a.this
                boolean r2 = com.qiyukf.uikit.session.module.a.a.f(r2)
                if (r2 != 0) goto L9c
                goto L9d
            L9c:
                return r1
            L9d:
                if (r0 <= 0) goto Lba
                com.qiyukf.uikit.session.module.a.a r0 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r0 = com.qiyukf.uikit.session.module.a.a.b(r0)
                java.lang.String r0 = r0.c
                com.qiyukf.unicorn.k.d r1 = com.qiyukf.unicorn.c.h()
                com.qiyukf.uikit.session.module.a.a r7 = com.qiyukf.uikit.session.module.a.a.this
                com.qiyukf.uikit.session.module.a r7 = com.qiyukf.uikit.session.module.a.a.b(r7)
                java.lang.String r7 = r7.c
                long r1 = r1.d(r7)
                com.qiyukf.unicorn.d.c.d(r0, r1)
            Lba:
                r7 = 1
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.qiyukf.uikit.session.module.a.a.C0168a.a(com.qiyukf.uikit.session.module.a.a$a):boolean");
        }

        public static /* synthetic */ void a(C0168a c0168a, List list) {
            int size = list.size();
            if (c0168a.d) {
                Collections.reverse(list);
            }
            if (c0168a.e && a.this.f.size() > 0) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    IMMessage iMMessage = (IMMessage) it.next();
                    Iterator it2 = a.this.f.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            IMMessage iMMessage2 = (IMMessage) it2.next();
                            if (iMMessage2.isTheSame(iMMessage)) {
                                a.this.f.remove(iMMessage2);
                                break;
                            }
                        }
                    }
                }
            }
            if (c0168a.e && c0168a.c != null) {
                a.this.f.add(c0168a.c);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(list);
            if (c0168a.b == QueryDirectionEnum.QUERY_NEW) {
                a.this.f.addAll(arrayList);
            } else {
                a.this.f.addAll(0, arrayList);
            }
            if (c0168a.e) {
                a.this.a(Unicorn.getUnreadCount() > 0, true);
            }
            a.this.g.a(a.this.f, true, c0168a.e);
            a.this.k();
            a.this.d();
            a.this.e.onRefreshComplete(size, 20, true);
            c0168a.e = false;
        }
    }

    /* JADX INFO: compiled from: MessageListPanel.java */
    public class b implements b.InterfaceC0169b {
        private b() {
        }

        public /* synthetic */ b(a aVar, byte b) {
            this();
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void a(IMMessage iMMessage) {
            if (iMMessage.getDirect() == MsgDirectionEnum.Out) {
                if (iMMessage.getStatus() == MsgStatusEnum.fail) {
                    e(iMMessage);
                    return;
                }
                if (iMMessage.getAttachment() instanceof FileAttachment) {
                    FileAttachment fileAttachment = (FileAttachment) iMMessage.getAttachment();
                    if (TextUtils.isEmpty(fileAttachment.getPath()) && TextUtils.isEmpty(fileAttachment.getThumbPath())) {
                        d(iMMessage);
                        return;
                    }
                    return;
                }
                e(iMMessage);
                return;
            }
            d(iMMessage);
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final boolean a(View view, final IMMessage iMMessage) {
            if (!a.this.c.e.isLongClickEnabled() || a.this.c.a == null || a.this.c.a.isFinishing() || a.this.c.a.isDestroyed()) {
                return true;
            }
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            final ArrayList arrayList = new ArrayList();
            final String string = a.this.c.a.getString(R.string.ysf_re_send_has_blank);
            MsgStatusEnum status = iMMessage.getStatus();
            MsgStatusEnum msgStatusEnum = MsgStatusEnum.fail;
            if (status == msgStatusEnum) {
                Map<String, Object> localExtension = iMMessage.getLocalExtension();
                if (iMMessage.getMsgType() != MsgTypeEnum.text || localExtension == null || localExtension.get("text_msg_touch_is_ban_tag") == null || !(localExtension.get("text_msg_touch_is_ban_tag") instanceof Boolean) || !((Boolean) localExtension.get("text_msg_touch_is_ban_tag")).booleanValue()) {
                    arrayList.add(string);
                }
            }
            final String string2 = a.this.c.a.getString(R.string.ysf_message_recall);
            MsgStatusEnum status2 = iMMessage.getStatus();
            MsgStatusEnum msgStatusEnum2 = MsgStatusEnum.recall;
            if (status2 != msgStatusEnum2 && iMMessage.getDirect() == MsgDirectionEnum.Out && c.h().g(iMMessage.getSessionId()) == 0 && iMMessage.getStatus() != msgStatusEnum && System.currentTimeMillis() - iMMessage.getTime() <= 120000 && !(iMMessage.getAttachment() instanceof az)) {
                arrayList.add(string2);
            }
            final String string3 = a.this.c.a.getString(R.string.ysf_copy_has_blank);
            MsgTypeEnum msgType = iMMessage.getMsgType();
            MsgTypeEnum msgTypeEnum = MsgTypeEnum.text;
            if (msgType == msgTypeEnum || (iMMessage.getAttachment() instanceof com.qiyukf.unicorn.h.a.a)) {
                arrayList.add(string3);
            }
            final String string4 = a.this.c.a.getString(R.string.ysf_copy_select_text);
            if (iMMessage.getMsgType() == msgTypeEnum) {
                arrayList.add(string4);
            }
            final String string5 = a.this.c.a.getString(R.string.ysf_quote_reply);
            if (iMMessage.getStatus() != msgStatusEnum2 && QuoteMsgHelper.canQuoteMessage(a.this.c.a, a.this.c.c, iMMessage, a.this.n)) {
                arrayList.add(string5);
            }
            final String string6 = a.this.c.a.getString(com.qiyukf.unicorn.d.c.l() ? R.string.ysf_audio_play_by_speaker : R.string.ysf_audio_play_by_earphone);
            final String string7 = a.this.c.a.getString(R.string.ysf_audio_translate);
            if (iMMessage.getMsgType() == MsgTypeEnum.audio) {
                arrayList.add(string6);
                Locale localeB = aa.b(a.this.c.a);
                if (Locale.CHINESE.getLanguage().equals(localeB.getLanguage()) && !Locale.TRADITIONAL_CHINESE.getCountry().equals(localeB.getCountry())) {
                    arrayList.add(string7);
                }
            }
            final String string8 = a.this.c.a.getString(R.string.ysf_delete_has_blank);
            if (arrayList.isEmpty()) {
                return true;
            }
            final ItemBlackPopupWindow itemBlackPopupWindow = new ItemBlackPopupWindow(a.this.c.a, arrayList, iArr[1], iMMessage.getDirect() == MsgDirectionEnum.In);
            itemBlackPopupWindow.setOnItemClickListener(new ItemBlackPopupWindow.OnItemClickListener() { // from class: com.qiyukf.uikit.session.module.a.a.b.4
                @Override // com.qiyukf.unicorn.widget.ItemBlackPopupWindow.OnItemClickListener
                public final void onClick(int i) {
                    if (TextUtils.equals((CharSequence) arrayList.get(i), string)) {
                        final b bVar = b.this;
                        final IMMessage iMMessage2 = iMMessage;
                        if (a.this.b(iMMessage2.getUuid()) >= 0) {
                            UnicornDialog.showDoubleBtnDialog(a.this.c.a, null, a.this.c.a.getString(R.string.ysf_re_send_message), true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.uikit.session.module.a.a.b.5
                                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                                public final void onClick(int i2) {
                                    if (i2 == 0) {
                                        b.this.e(iMMessage2);
                                    }
                                }
                            });
                        }
                    } else if (TextUtils.equals((CharSequence) arrayList.get(i), string3)) {
                        b bVar2 = b.this;
                        IMMessage iMMessage3 = iMMessage;
                        if (iMMessage3.getMsgType() == MsgTypeEnum.text) {
                            com.qiyukf.unicorn.n.g.a.a(a.this.c.a, iMMessage3.getContent());
                        } else if (iMMessage3.getAttachment() instanceof com.qiyukf.unicorn.h.a.a) {
                            com.qiyukf.unicorn.n.g.a.a(a.this.c.a, ((com.qiyukf.unicorn.h.a.a) iMMessage3.getAttachment()).a(a.this.c.a));
                        }
                    } else if (TextUtils.equals((CharSequence) arrayList.get(i), string6)) {
                        com.qiyukf.unicorn.d.c.a(!com.qiyukf.unicorn.d.c.l());
                        a.this.b(com.qiyukf.unicorn.d.c.l() ? R.string.ysf_audio_current_mode_is_earphone : R.string.ysf_audio_current_mode_is_speaker);
                    } else if (TextUtils.equals((CharSequence) arrayList.get(i), string7)) {
                        b bVar3 = b.this;
                        IMMessage iMMessage4 = iMMessage;
                        MsgDirectionEnum direct = iMMessage4.getDirect();
                        MsgDirectionEnum msgDirectionEnum = MsgDirectionEnum.In;
                        if (direct == msgDirectionEnum && iMMessage4.getAttachStatus() != AttachStatusEnum.transferred) {
                            t.a(R.string.ysf_no_permission_audio_error);
                        } else {
                            MsgStatusEnum status3 = iMMessage4.getStatus();
                            MsgStatusEnum msgStatusEnum3 = MsgStatusEnum.read;
                            if (status3 != msgStatusEnum3 && iMMessage4.getDirect() == msgDirectionEnum) {
                                iMMessage4.setStatus(msgStatusEnum3);
                                ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(iMMessage4, true);
                            }
                            g.a(a.this.c.a);
                            if (a.this.c.b.getActivity() != null) {
                                a.this.c.b.getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, TranslateFragment.newInstance(iMMessage4)).addToBackStack(null).commitAllowingStateLoss();
                            }
                        }
                    } else if (TextUtils.equals((CharSequence) arrayList.get(i), string8)) {
                        b bVar4 = b.this;
                        IMMessage iMMessage5 = iMMessage;
                        ((MsgService) NIMClient.getService(MsgService.class)).deleteChattingHistory(iMMessage5);
                        a.this.g.a(iMMessage5);
                    } else if (TextUtils.equals((CharSequence) arrayList.get(i), string2)) {
                        a.c(a.this, iMMessage);
                    } else if (TextUtils.equals((CharSequence) arrayList.get(i), string5)) {
                        a.this.c.e.onMessageQuote(iMMessage);
                    } else if (TextUtils.equals((CharSequence) arrayList.get(i), string4) && a.this.c.b.getActivity() != null) {
                        FragmentTransaction fragmentTransactionBeginTransaction = a.this.c.b.getActivity().getSupportFragmentManager().beginTransaction();
                        IMMessage iMMessage6 = iMMessage;
                        fragmentTransactionBeginTransaction.add(android.R.id.content, TranslateFragment.newInstance(iMMessage6, "text", iMMessage6.getContent())).addToBackStack(null).commitAllowingStateLoss();
                    }
                    itemBlackPopupWindow.dismiss();
                }
            });
            itemBlackPopupWindow.showAt(view);
            return true;
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final boolean a() {
            return a.this.c.e.isAllowSendMessage(true);
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void b(IMMessage iMMessage) {
            a.this.c.e.sendMessage(iMMessage, false);
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void c(IMMessage iMMessage) {
            a.this.c.e.sendMessageToInputPanel(iMMessage);
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void b() {
            a.this.c.e.shouldCollapseInputPanel();
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void a(SendImageHelper.Callback callback) {
            a.this.s = callback;
            PickImageAndVideoHelper.showSelector((TFragment) a.this.c.b, 8, false, d.a(w.a() + ".jpg", com.qiyukf.unicorn.n.e.c.TYPE_TEMP), false);
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void c() {
            a.this.h();
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void a(x xVar, String str, final RequestCallback<String> requestCallback) {
            a aVar = a.this;
            aVar.t = new WorkSheetHelper(aVar.c.b);
            a.this.t.openWorkSheetDialog(xVar, str, 18, 17, new RequestCallback<String>() { // from class: com.qiyukf.uikit.session.module.a.a.b.1
                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onException(Throwable th) {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onFailed(int i) {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final /* synthetic */ void onSuccess(String str2) {
                    requestCallback.onSuccess(str2);
                    a.this.t = null;
                }
            });
        }

        @Override // com.qiyukf.uikit.session.module.a.b.InterfaceC0169b
        public final void a(long j, final RequestCallback<String> requestCallback) {
            a aVar = a.this;
            aVar.t = new WorkSheetHelper(aVar.c.b);
            a.this.t.openWorkSheetDialog(j, a.this.c.c, 18, 17, new RequestCallback<String>() { // from class: com.qiyukf.uikit.session.module.a.a.b.2
                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onException(Throwable th) {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final void onFailed(int i) {
                }

                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                public final /* synthetic */ void onSuccess(String str) {
                    requestCallback.onSuccess(str);
                    a.this.t = null;
                }
            });
        }

        private void d(final IMMessage iMMessage) {
            UnicornDialog.showDoubleBtnDialog(a.this.c.a, null, a.this.c.a.getString(R.string.ysf_re_download_message), true, new UnicornDialog.OnClickListener() { // from class: com.qiyukf.uikit.session.module.a.a.b.3
                @Override // com.qiyukf.unicorn.widget.dialog.UnicornDialog.OnClickListener
                public final void onClick(int i) {
                    if (i == 0 && iMMessage.getAttachment() != null && (iMMessage.getAttachment() instanceof FileAttachment)) {
                        ((MsgService) NIMClient.getService(MsgService.class)).downloadAttachment(iMMessage, true);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        public void e(IMMessage iMMessage) {
            com.qiyukf.nimlib.session.d dVar = (com.qiyukf.nimlib.session.d) iMMessage;
            dVar.b(System.currentTimeMillis());
            dVar.b(a.this.c.c);
            dVar.setStatus(MsgStatusEnum.sending);
            dVar.a(a.this.c.d);
            if (dVar.getMsgType() == MsgTypeEnum.audio) {
                AudioAttachment audioAttachment = (AudioAttachment) dVar.getAttachment();
                audioAttachment.setAutoTransform(a.this.p);
                dVar.setAttachment(audioAttachment);
            }
            a.this.g.a(iMMessage);
            a.this.c.e.sendMessage(dVar, true);
            a.this.a(iMMessage);
        }
    }

    public final void b(IMMessage iMMessage) {
        this.g.a(iMMessage);
    }

    private void l() {
        if (this.y == null) {
            this.y = new b.a() { // from class: com.qiyukf.uikit.session.module.a.a.3
            };
        }
        com.qiyukf.uikit.b.a.a(this.y);
    }

    private void m() {
        b.a aVar = this.y;
        if (aVar != null) {
            com.qiyukf.uikit.b.a.b(aVar);
        }
    }

    public final void b(int i) {
        int i2 = com.qiyukf.unicorn.d.c.l() ? R.drawable.ysf_play_audio_mode_earphone1 : R.drawable.ysf_play_audio_mode_speaker1;
        this.k.setText(i);
        this.m.setBackgroundResource(i2);
        this.j.setVisibility(0);
        this.i.removeCallbacks(this.z);
        this.i.postDelayed(this.z, 3000L);
    }

    public final void a(boolean z) {
        this.p = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MediaPlayer a(File file) {
        try {
            return MediaPlayer.create(this.c.a, Uri.fromFile(file));
        } catch (Exception e) {
            AbsUnicornLog.e("MessageListPanel", "getVideoMediaPlayer is error file", e);
            return null;
        }
    }

    public final com.qiyukf.uikit.session.module.a.b j() {
        return this.g;
    }

    public final IMMessage a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        for (IMMessage iMMessage : this.g.getItems()) {
            if (iMMessage.getUuid().equals(str)) {
                return iMMessage;
            }
        }
        return null;
    }

    public static /* synthetic */ void b(a aVar, IMMessage iMMessage) {
        if (c.h().b(iMMessage)) {
            iMMessage.setStatus(MsgStatusEnum.unread);
            MsgDBHelper.updateMessageStatus((com.qiyukf.nimlib.session.d) iMMessage);
        }
        int iB = aVar.b(iMMessage.getUuid());
        if (iB < 0 || iB >= aVar.f.size()) {
            return;
        }
        IMMessage iMMessage2 = aVar.f.get(iB);
        iMMessage2.setStatus(iMMessage.getStatus());
        iMMessage2.setAttachStatus(iMMessage.getAttachStatus());
        iMMessage2.setAttachment(iMMessage.getAttachment());
        iMMessage2.setLocalExtension(iMMessage.getLocalExtension());
        iMMessage2.setRemoteExtension(iMMessage.getRemoteExtension());
        iMMessage2.setContent(iMMessage.getContent());
        aVar.c(iB);
        aVar.g.notifyDataSetChanged();
        if (ListViewUtil.isAtBottom(aVar.e) || aVar.r != 0 || iMMessage.getDirect() == MsgDirectionEnum.Out) {
            aVar.r = 0;
            aVar.a(false, false);
        }
    }

    public static /* synthetic */ void a(a aVar, AttachmentProgress attachmentProgress) {
        int iB = aVar.b(attachmentProgress.getUuid());
        if (iB < 0 || iB >= aVar.f.size()) {
            return;
        }
        aVar.g.a(aVar.f.get(iB), attachmentProgress.getTransferred() / attachmentProgress.getTotal());
        aVar.c(iB);
    }

    public static /* synthetic */ void c(a aVar, IMMessage iMMessage) {
        if (aVar.c.e.isAllowSendMessage(true)) {
            q qVar = new q();
            qVar.a(String.valueOf(c.h().d(iMMessage.getSessionId())));
            qVar.b(iMMessage.getUuid());
            com.qiyukf.unicorn.k.c.a(qVar, iMMessage.getSessionId());
            AbsUnicornLog.i("MessageListPanel", "withdrawMessage sessionId=" + qVar.a() + ", msgId=" + qVar.b());
        }
    }
}

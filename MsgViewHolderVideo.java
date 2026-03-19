package com.qiyukf.uikit.session.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.y;
import com.qiyukf.nimlib.sdk.msg.attachment.VideoAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.uikit.common.a.f;
import com.qiyukf.uikit.session.activity.WatchVideoActivity;
import com.qiyukf.uikit.session.helper.QuoteMsgHelper;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.event.EventCallback;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.event.entry.RequestPermissionEventEntry;
import com.qiyukf.unicorn.f.l;
import com.qiyukf.unicorn.h.a.f.q;
import com.qiyukf.unicorn.n.b.e;
import com.qiyukf.unicorn.n.d.a;
import com.qiyukf.unicorn.n.d.c;
import com.qiyukf.unicorn.n.e.b;
import com.qiyukf.unicorn.n.e.d;
import com.qiyukf.unicorn.n.j;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.widget.ItemBlackPopupWindow;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class MsgViewHolderVideo extends MsgViewHolderThumbBase {
    private static final String TAG = "MsgViewHolderVideo";
    a.C0189a thumbSize;
    private TextView tvYsfItemMessageDuration;
    private TextView tvYsfItemMessageSize;
    VideoAttachment videoAttachment;
    private TextView ysfTvHolderVideoShadow;

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_message_item_video;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void onItemClick() {
        WatchVideoActivity.start(this.context, this.message, false);
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderThumbBase, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        super.inflateContentView();
        this.tvYsfItemMessageSize = (TextView) findViewById(R.id.tv_ysf_item_message_size);
        this.tvYsfItemMessageDuration = (TextView) findViewById(R.id.tv_ysf_item_message_duration);
        this.ysfTvHolderVideoShadow = (TextView) findViewById(R.id.ysf_tv_holder_video_shadow);
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderThumbBase, com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        super.bindContentView();
        VideoAttachment videoAttachment = (VideoAttachment) this.message.getAttachment();
        this.videoAttachment = videoAttachment;
        long jB = y.b(videoAttachment.getDuration());
        if (jB == 0) {
            jB = 1;
        }
        this.tvYsfItemMessageDuration.setText((jB < 10 ? "00:0" : "00:").concat(String.valueOf(jB)));
        this.tvYsfItemMessageSize.setText(e.a(this.videoAttachment.getSize()));
        this.ysfTvHolderVideoShadow.setWidth(getThumbSize().a);
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderThumbBase
    public String thumbFromSourceFile(String str) {
        VideoAttachment videoAttachment = (VideoAttachment) this.message.getAttachment();
        String thumbPathForSave = videoAttachment.getThumbPathForSave();
        if (c.a(str, thumbPathForSave, videoAttachment.getWidth(), videoAttachment.getHeight())) {
            return thumbPathForSave;
        }
        return null;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderThumbBase
    public int[] getBounds() {
        VideoAttachment videoAttachment = (VideoAttachment) this.message.getAttachment();
        return new int[]{videoAttachment.getWidth(), videoAttachment.getHeight()};
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderThumbBase
    public void setLayoutParamer(a.C0189a c0189a) {
        this.thumbSize = c0189a;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        this.thumbnail.setLayoutParams(layoutParams);
        setLayoutParams(c0189a.a, c0189a.b, this.cover);
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public boolean onItemLongClick() {
        showPopupWindow(this.thumbnail);
        return true;
    }

    private void showPopupWindow(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        final ArrayList arrayList = new ArrayList();
        if (this.message.getStatus() != MsgStatusEnum.recall) {
            if (this.message.getDirect() == MsgDirectionEnum.Out && com.qiyukf.unicorn.c.h().g(this.message.getSessionId()) == 0 && this.message.getStatus() != MsgStatusEnum.fail && System.currentTimeMillis() - this.message.getTime() <= 120000) {
                arrayList.add(this.context.getString(R.string.ysf_message_recall));
            }
            if (QuoteMsgHelper.canQuoteMessage(this.context, this.message.getSessionId(), this.message, false)) {
                arrayList.add(this.context.getString(R.string.ysf_quote_reply));
            }
        }
        arrayList.add(this.context.getString(R.string.ysf_mute_label));
        arrayList.add(this.context.getString(R.string.ysf_save_str));
        final ItemBlackPopupWindow itemBlackPopupWindow = new ItemBlackPopupWindow(this.context, arrayList, iArr[1], this.message.getDirect() == MsgDirectionEnum.In);
        itemBlackPopupWindow.setOnItemClickListener(new ItemBlackPopupWindow.OnItemClickListener() { // from class: com.qiyukf.uikit.session.viewholder.MsgViewHolderVideo.1
            @Override // com.qiyukf.unicorn.widget.ItemBlackPopupWindow.OnItemClickListener
            public void onClick(int i) {
                if (TextUtils.equals(((f) MsgViewHolderVideo.this).context.getString(R.string.ysf_message_recall), (CharSequence) arrayList.get(i))) {
                    MsgViewHolderVideo.this.recallMessage();
                } else if (TextUtils.equals(((f) MsgViewHolderVideo.this).context.getString(R.string.ysf_mute_label), (CharSequence) arrayList.get(i))) {
                    WatchVideoActivity.start(((f) MsgViewHolderVideo.this).context, MsgViewHolderVideo.this.message, true);
                } else if (TextUtils.equals(((f) MsgViewHolderVideo.this).context.getString(R.string.ysf_save_str), (CharSequence) arrayList.get(i))) {
                    MsgViewHolderVideo.this.saveVideo();
                } else if (TextUtils.equals(((f) MsgViewHolderVideo.this).context.getString(R.string.ysf_quote_reply), (CharSequence) arrayList.get(i))) {
                    com.qiyukf.unicorn.k.c.d(MsgViewHolderVideo.this.message);
                }
                itemBlackPopupWindow.dismiss();
            }
        });
        itemBlackPopupWindow.showAt(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recallMessage() {
        if (getAdapter().b().a()) {
            q qVar = new q();
            qVar.a(String.valueOf(com.qiyukf.unicorn.c.h().d(this.message.getSessionId())));
            qVar.b(this.message.getUuid());
            com.qiyukf.unicorn.k.c.a(qVar, this.message.getSessionId());
            AbsUnicornLog.i(TAG, "withdrawMessage sessionId=" + qVar.a() + ", msgId=" + qVar.b());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveVideo() {
        if (com.qiyukf.unicorn.c.f().sdkEvents != null && com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory != null && !j.a(this.context, l.b)) {
            final UnicornEventBase unicornEventBaseEventOf = com.qiyukf.unicorn.c.f().sdkEvents.eventProcessFactory.eventOf(5);
            if (unicornEventBaseEventOf != null) {
                List<String> listAsList = Arrays.asList(l.b);
                final RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                requestPermissionEventEntry.setScenesType(3);
                requestPermissionEventEntry.setPermissionList(listAsList);
                unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, this.context, new EventCallback() { // from class: com.qiyukf.uikit.session.viewholder.MsgViewHolderVideo.2
                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onProcessEventSuccess(Object obj) {
                        MsgViewHolderVideo.this.saveVideo(unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onPorcessEventError() {
                        MsgViewHolderVideo.this.saveVideo(unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onNotPorcessEvent() {
                        MsgViewHolderVideo.this.saveVideo(unicornEventBaseEventOf, requestPermissionEventEntry);
                    }

                    @Override // com.qiyukf.unicorn.api.event.EventCallback
                    public void onInterceptEvent() {
                        t.a(R.string.ysf_no_permission_save_video);
                    }
                });
                return;
            }
            saveVideo(null, null);
            return;
        }
        saveVideo(null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveVideo(final UnicornEventBase unicornEventBase, final RequestPermissionEventEntry requestPermissionEventEntry) {
        j.a((Activity) this.context).a(l.b).a(new j.a() { // from class: com.qiyukf.uikit.session.viewholder.MsgViewHolderVideo.3
            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 != null) {
                    unicornEventBase2.onGrantEvent(((f) MsgViewHolderVideo.this).context, requestPermissionEventEntry);
                }
                if (!TextUtils.isEmpty(MsgViewHolderVideo.this.videoAttachment.getPath())) {
                    String strB = d.b(((f) MsgViewHolderVideo.this).context);
                    if (TextUtils.isEmpty(MsgViewHolderVideo.this.videoAttachment.getExtension())) {
                        t.a(R.string.ysf_video_save_fail);
                        return;
                    }
                    if (o.a()) {
                        if (b.b(((f) MsgViewHolderVideo.this).context, new File(MsgViewHolderVideo.this.videoAttachment.getPath()))) {
                            t.b(((f) MsgViewHolderVideo.this).context.getString(R.string.ysf_video_save_success));
                            return;
                        } else {
                            t.a(R.string.ysf_video_save_fail);
                            return;
                        }
                    }
                    String str = strB + ("video_" + System.currentTimeMillis() + ".mp4");
                    if (com.qiyukf.nimlib.net.a.c.a.a(MsgViewHolderVideo.this.videoAttachment.getPath(), str) != -1) {
                        try {
                            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                            intent.setData(Uri.fromFile(new File(str)));
                            ((f) MsgViewHolderVideo.this).context.sendBroadcast(intent);
                            t.b(((f) MsgViewHolderVideo.this).context.getString(R.string.ysf_video_save_to, strB));
                            return;
                        } catch (Exception unused) {
                            t.b(R.string.ysf_picture_save_fail);
                            return;
                        }
                    }
                    t.a(R.string.ysf_video_save_fail);
                    return;
                }
                t.a(R.string.ysf_first_download_video);
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                UnicornEventBase unicornEventBase2 = unicornEventBase;
                if (unicornEventBase2 == null || !unicornEventBase2.onDenyEvent(((f) MsgViewHolderVideo.this).context, requestPermissionEventEntry)) {
                    t.a(R.string.ysf_no_permission_save_video);
                }
            }
        }).a();
    }
}

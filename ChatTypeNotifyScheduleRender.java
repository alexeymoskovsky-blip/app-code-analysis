package com.petkit.android.activities.chat.adapter.render;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.adapter.ChatAdapter;
import com.petkit.android.model.ChatMsg;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class ChatTypeNotifyScheduleRender extends ChatTypeNotifyWebBaseRender {
    public TextView scheduleContent;
    public ImageView scheduleIcon;
    public TextView scheduleTime;
    public TextView scheduleTitle;

    public ChatTypeNotifyScheduleRender(Activity activity, ChatAdapter chatAdapter) {
        super(activity, chatAdapter);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyWebBaseRender
    public void initExternalView() {
        super.initExternalView();
        this.notifyImg.setImageResource(R.drawable.icon_push_remind);
        this.layoutContainer.setLayoutResource(R.layout.layout_notify_schedule);
        this.layoutContainer.inflate();
        this.scheduleTitle = (TextView) this.contentView.findViewById(R.id.schedule_title);
        this.scheduleIcon = (ImageView) this.contentView.findViewById(R.id.schedule_icon);
        this.scheduleContent = (TextView) this.contentView.findViewById(R.id.schedule_content);
        this.scheduleTime = (TextView) this.contentView.findViewById(R.id.schedule_time);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyWebBaseRender, com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitDatas(int i) {
        super.fitDatas(i);
        ChatMsg item = this.chatAdapter.getItem(i);
        String payloadContent = item.getPayloadContent();
        try {
            this.time.setText(DateUtil.getTodoRemindDateTime(this.activity, item.getTimestamp().trim()));
        } catch (Exception e) {
            PetkitLog.d("fitDatas:" + e.getMessage());
            this.time.setText("");
        }
        RemindDetail remindDetail = (RemindDetail) new Gson().fromJson(payloadContent, RemindDetail.class);
        if (remindDetail.getPet() != null && !TextUtils.isEmpty(remindDetail.getPet().getName())) {
            this.scheduleTitle.setText(this.activity.getString(R.string.Reminder_title_format, remindDetail.getPet().getName(), remindDetail.getType().getName()));
        } else {
            this.scheduleTitle.setText(remindDetail.getType().getName());
        }
        this.scheduleTime.setText(DateUtil.getFormatDateFromString(remindDetail.getTime()));
        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(remindDetail.getType().getImg()).imageView(this.scheduleIcon).errorPic(R.drawable.default_image).build());
        this.scheduleContent.setText(item.getMsg());
        this.notifyTitle.setText(this.activity.getString(R.string.Reminder_have_new));
    }
}

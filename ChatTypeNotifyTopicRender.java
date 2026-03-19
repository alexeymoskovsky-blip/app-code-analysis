package com.petkit.android.activities.chat.adapter.render;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.adapter.ChatAdapter;
import com.petkit.android.model.Topic;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.JSONUtils;
import com.petkit.oversea.R;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class ChatTypeNotifyTopicRender extends ChatTypeNotifyBaseRender {
    public TextView topic_content;
    public ImageView topic_image;
    public TextView topic_title;

    public ChatTypeNotifyTopicRender(Activity activity, ChatAdapter chatAdapter) {
        super(activity, chatAdapter);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyBaseRender
    public void initExternalView() {
        super.initExternalView();
        this.notifyImg.setImageResource(R.drawable.icon_push_topic);
        this.layoutContainer.setLayoutResource(R.layout.layout_notify_topic);
        this.layoutContainer.inflate();
        View viewFindViewById = this.contentView.findViewById(R.id.layout_container_id);
        viewFindViewById.setBackgroundColor(CommonUtils.getColorById(R.color.notify_gray_bg));
        this.topic_image = (ImageView) viewFindViewById.findViewById(R.id.topic_image);
        this.topic_title = (TextView) viewFindViewById.findViewById(R.id.topic_title);
        this.topic_content = (TextView) viewFindViewById.findViewById(R.id.topic_content);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyBaseRender, com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitDatas(int i) {
        super.fitDatas(i);
        try {
            Topic topic = (Topic) new Gson().fromJson(JSONUtils.getValue(JSONUtils.getJSONObject(this.chatAdapter.getItem(i).getPayloadContent()), Constants.OVERSEA_BUSINESS_CONTROL_TOPIC), Topic.class);
            ((BaseApplication) this.activity.getApplication()).getAppComponent().imageLoader().loadImage(this.activity, GlideImageConfig.builder().url(topic.getImgs()).imageView(this.topic_image).errorPic(R.drawable.default_topic_image_middle).build());
            this.topic_title.setText(topic.getTopicname());
            this.topic_content.setText(topic.getDescribe());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

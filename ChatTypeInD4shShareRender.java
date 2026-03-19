package com.petkit.android.activities.chat.adapter.render;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.petkit.android.activities.chat.adapter.ChatAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.model.ChatMsg;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.JSONUtils;
import com.petkit.oversea.R;
import java.util.HashMap;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class ChatTypeInD4shShareRender extends ChatTypeInBaseRender {
    public TextView chatFeederName;
    public TextView chatFeederShareMsg;
    public View feederShareView;

    public ChatTypeInD4shShareRender(Activity activity, ChatAdapter chatAdapter) {
        super(activity, chatAdapter);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeInBaseRender
    public void initContent() {
        this.chatContent.setLayoutResource(R.layout.adapter_chat_in_d4_share);
        this.chatContent.inflate();
        this.feederShareView = this.contentView.findViewById(R.id.chat_content_id);
        this.chatFeederShareMsg = (TextView) this.contentView.findViewById(R.id.share_msg);
        this.chatFeederName = (TextView) this.contentView.findViewById(R.id.feeder_name);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeInBaseRender, com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitEvents() {
        super.fitEvents();
        this.feederShareView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.chat.adapter.render.ChatTypeInD4shShareRender.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    String value = JSONUtils.getValue(JSONUtils.getJSONObject(ChatTypeInD4shShareRender.this.chatAdapter.getItem(((Integer) view.getTag()).intValue()).getPayloadContent()), "deviceId");
                    if (value == null || value.isEmpty()) {
                        return;
                    }
                    D4shUtils.startActivityForD4shSharedDeviceId(((ChatTypeInBaseRender) ChatTypeInD4shShareRender.this).activity, D4shHomeActivity.class, Long.valueOf(value).longValue(), 0);
                    new HashMap().put("type", Constants.IM_PAYLOAD_TYPE_API_D4SH_SHARE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeInBaseRender, com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitDatas(int i) {
        super.fitDatas(i);
        ChatMsg item = this.chatAdapter.getItem(i);
        this.chatFeederShareMsg.setText(item.getMsg());
        try {
            JSONUtils.getValue(JSONUtils.getJSONObject(item.getPayloadContent()), "ownerNick");
            this.chatFeederName.setText(((ChatTypeInBaseRender) this).activity.getString(R.string.D4SH_name_default));
            this.feederShareView.setTag(Integer.valueOf(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

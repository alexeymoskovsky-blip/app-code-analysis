package com.petkit.android.activities.chat.adapter.render;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.chat.MessageJumpUtils;
import com.petkit.android.activities.chat.adapter.ChatAdapter;
import com.petkit.android.activities.chat.mode.MessageContent;
import com.petkit.android.activities.chat.mode.Payload;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7ConstUtils;
import com.petkit.android.activities.remind.AddRemindActivity2_0;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.RemindDetailRsp;
import com.petkit.android.model.ChatMsg;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class ChatTypeNotifyDeviceBaseRender extends ChatTypeBaseRender {
    public LinearLayout contentView;
    public View divider;
    public ViewStub layoutContainer;
    public ImageView notifyImg;
    public ImageView notifyImgRedPoint;
    public RelativeLayout notifyLayout;
    public TextView notifyTitle;
    public TextView notifyTitleBold;
    public int position;
    public TextView time;

    public void initExternalView() {
    }

    public ChatTypeNotifyDeviceBaseRender(Activity activity, ChatAdapter chatAdapter) {
        super(activity, chatAdapter);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.layout_notify_device_render, (ViewGroup) null);
        this.contentView = linearLayout;
        this.notifyTitleBold = (TextView) linearLayout.findViewById(R.id.notify_title_bold);
        this.notifyLayout = (RelativeLayout) this.contentView.findViewById(R.id.notify_layout);
        this.notifyTitle = (TextView) this.contentView.findViewById(R.id.notify_title);
        this.notifyImg = (ImageView) this.contentView.findViewById(R.id.notify_img);
        this.layoutContainer = (ViewStub) this.contentView.findViewById(R.id.layout_container);
        this.time = (TextView) this.contentView.findViewById(R.id.msg_time);
        this.divider = this.contentView.findViewById(R.id.divider);
        this.notifyImgRedPoint = (ImageView) this.contentView.findViewById(R.id.notify_img_red_point);
        initExternalView();
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public View getConvertView() {
        return this.contentView;
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitEvents() {
        this.notifyLayout.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyDeviceBaseRender.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ChatTypeNotifyDeviceBaseRender chatTypeNotifyDeviceBaseRender = ChatTypeNotifyDeviceBaseRender.this;
                ChatMsg item = chatTypeNotifyDeviceBaseRender.chatAdapter.getItem(chatTypeNotifyDeviceBaseRender.position);
                if (item != null) {
                    String payloadContent = item.getPayloadContent();
                    if (MessageJumpUtils.getInstance().jumpNotification(ChatTypeNotifyDeviceBaseRender.this.activity, new Payload((MessageContent) new Gson().fromJson(payloadContent, MessageContent.class), item.getPayloadType()), payloadContent, false)) {
                        return;
                    }
                    Activity activity = ChatTypeNotifyDeviceBaseRender.this.activity;
                    PetkitToast.showTopToast(activity, activity.getString(R.string.Hint_unknown_message), 0, 0);
                }
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:112:0x030a  */
    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fitDatas(int r10) {
        /*
            Method dump skipped, instruction units count: 2942
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyDeviceBaseRender.fitDatas(int):void");
    }

    public final boolean cloudService(String str) {
        return str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4H_CLOUD_PAYMENT_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4SH_CLOUD_PAYMENT_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4SH_CLOUD_SIGNUP_FAILED) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4H_CLOUD_SIGNUP_FAILED) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4SH_CLOUD_SIGNUP_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4H_CLOUD_SIGNUP_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4H_CLOUD_PAYMENT_FAILED) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4SH_CLOUD_PAYMENT_FAILED) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4H_CLOUD_RENEWAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4SH_CLOUD_RENEWAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4H_CLOUD_RENEWAL_FINAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4SH_CLOUD_RENEWAL_FINAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4H_CLOUD_RENEWAL_CANCEL) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_D4SH_CLOUD_RENEWAL_CANCEL) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_CLOUD_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_CLOUD_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SUBSCRIPTION_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SUBSCRIPTION_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SUBSCRIPTION_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SUBSCRIPTION_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_UPGRADE_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_UPGRADE_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SUBSCRIPTION_UPGRADE_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SUBSCRIPTION_UPGRADE_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SERVICE_EXPIRATION) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SERVICE_EXPIRATION) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SERVICE_EXPIRATION_3) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SERVICE_EXPIRATION_3) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SERVICE_UNLINK) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SERVICE_UNLINK) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SERVICE_DATE_CHANGE) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SKU_DISCOUNT_REMIND) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SERVICE_DATE_CHANGE) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SKU_DISCOUNT_REMIND) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SERVICE_TRANSFER) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SERVICE_TRANSFER) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4H_SERVICE_TRANSFER_RECIPIENT) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_D4SH_SERVICE_TRANSFER_RECIPIENT) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_BS_COUPON_3) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_BS_COUPON_TODAY) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T5_CLOUD_PAYMENT_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T5_CLOUD_PAYMENT_FAILED) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T5_CLOUD_RENEWAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T5_CLOUD_RENEWAL_FINAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T5_CLOUD_RENEWAL_CANCEL) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_CLOUD_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SUBSCRIPTION_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SUBSCRIPTION_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_UPGRADE_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SUBSCRIPTION_UPGRADE_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SERVICE_EXPIRATION) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SERVICE_EXPIRATION_3) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SERVICE_UNLINK) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SERVICE_DATE_CHANGE) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SERVICE_TRANSFER) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SKU_DISCOUNT_REMIND) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T5_SERVICE_TRANSFER_RECIPIENT) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T5_CLOUD_SIGNUP_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T6_CLOUD_PAYMENT_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T6_CLOUD_PAYMENT_FAILED) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T6_CLOUD_RENEWAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T6_CLOUD_RENEWAL_FINAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T6_CLOUD_RENEWAL_CANCEL) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T6_CLOUD_RENEWAL_CANCEL) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_CLOUD_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SUBSCRIPTION_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SUBSCRIPTION_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_UPGRADE_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SUBSCRIPTION_UPGRADE_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SERVICE_EXPIRATION) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SKU_DISCOUNT_REMIND) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SERVICE_EXPIRATION_3) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SERVICE_UNLINK) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SERVICE_DATE_CHANGE) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SERVICE_TRANSFER) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_T6_CLOUD_SIGNUP_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T6_SERVICE_TRANSFER_RECIPIENT) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_W7H_CLOUD_PAYMENT_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_W7H_CLOUD_PAYMENT_FAILED) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_W7H_CLOUD_RENEWAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_W7H_CLOUD_RENEWAL_FINAL_PENDING) || str.startsWith(Constants.IM_PAYLOAD_TYPE_BS_W7H_CLOUD_RENEWAL_CANCEL) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_CLOUD_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SUBSCRIPTION_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SUBSCRIPTION_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_UPGRADE_PAYMENT_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SUBSCRIPTION_UPGRADE_RENEWAL_PAID) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SERVICE_EXPIRATION) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SERVICE_EXPIRATION_3) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SERVICE_UNLINK) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SERVICE_DATE_CHANGE) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SERVICE_TRANSFER) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_W7H_SERVICE_TRANSFER_RECIPIENT) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_BS_T7_CLOUD_PAYMENT_PENDING) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_BS_T7_CLOUD_PAYMENT_FAILED) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_BS_T7_CLOUD_RENEWAL_PENDING) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_BS_T7_CLOUD_RENEWAL_FINAL_PENDING) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_BS_T7_CLOUD_RENEWAL_CANCEL) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_CLOUD_PAYMENT_PAID) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SUBSCRIPTION_PAYMENT_PAID) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SUBSCRIPTION_RENEWAL_PAID) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_UPGRADE_PAYMENT_PAID) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SUBSCRIPTION_UPGRADE_RENEWAL_PAID) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SERVICE_EXPIRATION) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SERVICE_EXPIRATION_3) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SERVICE_UNLINK) || str.startsWith(Constants.IM_PAYLOAD_TYPE_API_T7_SKU_DISCOUNT_REMIND) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SERVICE_DATE_CHANGE) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SERVICE_TRANSFER) || str.startsWith(T7ConstUtils.IM_PAYLOAD_TYPE_API_T7_SERVICE_TRANSFER_RECIPIENT);
    }

    public final boolean Ai(String str) {
        return str.startsWith(Constants.API_AI_CREATION_AWARD_SUCCESS_NOTIFICATION) || str.startsWith(Constants.API_AI_CREATION_AWARD_FAIL_NOTIFICATION) || str.startsWith(Constants.API_AI_T6_REWARD_PLAN_ACTIVE) || str.startsWith(Constants.API_AI_T5_REWARD_PLAN_ACTIVE) || str.startsWith(Constants.API_AI_D4SH_REWARD_PLAN_ACTIVE) || str.startsWith(Constants.API_AI_D4H_REWARD_PLAN_ACTIVE) || str.startsWith(Constants.API_AI_CREATION_CHECK_NOTIFICATION);
    }

    private void getRemindDetail(String str, final RemindDetail remindDetail) {
        HashMap map = new HashMap();
        map.put("id", str);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_SCHEDULE_GET, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.activity, true) { // from class: com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyDeviceBaseRender.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                ChatTypeNotifyDeviceBaseRender.this.activity.startActivity(AddRemindActivity2_0.newIntent(ChatTypeNotifyDeviceBaseRender.this.activity, remindDetail, 2));
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RemindDetailRsp remindDetailRsp = (RemindDetailRsp) this.gson.fromJson(this.responseResult, RemindDetailRsp.class);
                if (remindDetailRsp.getError() != null) {
                    PetkitToast.showToast(remindDetailRsp.getError().getMsg());
                } else if (remindDetailRsp.getResult() != null) {
                    ChatTypeNotifyDeviceBaseRender.this.activity.startActivity(AddRemindActivity2_0.newIntent(ChatTypeNotifyDeviceBaseRender.this.activity, remindDetailRsp.getResult(), 2));
                }
            }
        }, false);
    }
}

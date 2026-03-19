package com.qiyukf.uikit.session.helper;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.session.d;
import com.qiyukf.uikit.a;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.msg.attachment.ProductAttachment;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.h.a.d.q;
import com.qiyukf.unicorn.h.a.d.z;
import com.qiyukf.unicorn.h.a.f.m;
import com.qiyukf.unicorn.n.f;
import com.qiyukf.unicorn.n.u;
import com.tencent.open.SocialConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes6.dex */
public class QuoteMsgHelper {
    public static final String QUOTE_MSG_TYPE_AUDIO = "audio";
    public static final String QUOTE_MSG_TYPE_CUSTOM = "custom";
    public static final String QUOTE_MSG_TYPE_FILE = "file";
    public static final String QUOTE_MSG_TYPE_IMAGE = "image";
    public static final String QUOTE_MSG_TYPE_RICH_TEXT = "richtext";
    public static final String QUOTE_MSG_TYPE_TEXT = "text";
    public static final String QUOTE_MSG_TYPE_VIDEO = "video";

    public static String getQuoteMessageContent(IMMessage iMMessage) {
        if (MsgTypeEnum.image == iMMessage.getMsgType() || MsgTypeEnum.file == iMMessage.getMsgType() || MsgTypeEnum.video == iMMessage.getMsgType() || MsgTypeEnum.audio == iMMessage.getMsgType() || (iMMessage.getAttachment() instanceof ProductAttachment) || (iMMessage.getAttachment() instanceof z) || (iMMessage.getAttachment() instanceof q)) {
            if (iMMessage.getAttachment() instanceof q) {
                return ((q) iMMessage.getAttachment()).a();
            }
            return iMMessage.getAttachment().toJson(false);
        }
        return iMMessage.getContent();
    }

    public static String getQuoteMessageType(IMMessage iMMessage) {
        JSONObject jSONObjectA;
        if (MsgTypeEnum.image == iMMessage.getMsgType()) {
            return "image";
        }
        if (MsgTypeEnum.file == iMMessage.getMsgType()) {
            return "file";
        }
        if (MsgTypeEnum.video == iMMessage.getMsgType()) {
            return "video";
        }
        if (MsgTypeEnum.audio == iMMessage.getMsgType()) {
            return "audio";
        }
        if (iMMessage.getAttachment() instanceof ProductAttachment) {
            return "custom";
        }
        return iMMessage.getAttachment() instanceof z ? QUOTE_MSG_TYPE_RICH_TEXT : ((iMMessage.getAttachment() instanceof q) && (jSONObjectA = j.a(((q) iMMessage.getAttachment()).a())) != null && jSONObjectA.has("cmd") && j.a(jSONObjectA, "cmd") == 65) ? QUOTE_MSG_TYPE_RICH_TEXT : "text";
    }

    public static void handleQuoteMessageShow(Context context, String str, String str2, TextView textView, ImageView imageView) {
        String quoteMessageContent = getQuoteMessageContent(str, str2);
        if (TextUtils.equals("image", str)) {
            imageView.setVisibility(0);
            textView.setVisibility(8);
            a.a(quoteMessageContent, imageView);
            return;
        }
        imageView.setVisibility(8);
        textView.setVisibility(0);
        if (TextUtils.equals("file", str)) {
            quoteMessageContent = context.getString(R.string.ysf_msg_notify_file) + quoteMessageContent;
        } else if (TextUtils.equals("video", str)) {
            quoteMessageContent = context.getString(R.string.ysf_msg_notify_video) + quoteMessageContent;
        } else if (TextUtils.equals("audio", str)) {
            quoteMessageContent = context.getString(R.string.ysf_msg_notify_audio) + quoteMessageContent;
        } else if (TextUtils.equals("custom", str)) {
            if (quoteMessageContent.contains("orderId")) {
                quoteMessageContent = context.getString(R.string.ysf_msg_notify_order) + quoteMessageContent;
            } else {
                quoteMessageContent = context.getString(R.string.ysf_msg_notify_card) + quoteMessageContent;
            }
        }
        textView.setText(quoteMessageContent);
    }

    public static String getQuoteMessageContent(String str, String str2) {
        JSONObject jSONObjectA = j.a(str2);
        if (TextUtils.equals("image", str)) {
            return j.e(jSONObjectA, "url");
        }
        if (TextUtils.equals("file", str)) {
            return j.e(jSONObjectA, "name");
        }
        if (TextUtils.equals("video", str)) {
            return j.e(jSONObjectA, "name");
        }
        if (TextUtils.equals(QUOTE_MSG_TYPE_RICH_TEXT, str)) {
            return f.a(j.e(jSONObjectA, "content")).replace("\n", " ");
        }
        if (TextUtils.equals("audio", str)) {
            return "";
        }
        if (!TextUtils.equals("custom", str)) {
            return str2;
        }
        if (jSONObjectA.has("title")) {
            return j.e(jSONObjectA, "title");
        }
        if (jSONObjectA.has(SocialConstants.PARAM_APP_DESC)) {
            return j.e(jSONObjectA, SocialConstants.PARAM_APP_DESC);
        }
        return j.e(jSONObjectA, "orderId");
    }

    public static boolean isQuoteMessage(IMMessage iMMessage) {
        if (!c.f().isPullMessageFromServer || !(iMMessage instanceof d)) {
            return false;
        }
        d dVar = (d) iMMessage;
        if (TextUtils.isEmpty(dVar.j())) {
            return false;
        }
        return dVar.j().contains("quoteMessage");
    }

    public static boolean canQuoteMessage(Context context, String str, IMMessage iMMessage, boolean z) {
        if (c.h().e(str) == null) {
            return false;
        }
        return (z || u.b(context, iMMessage) || !(iMMessage.getMsgType() == MsgTypeEnum.text || iMMessage.getMsgType() == MsgTypeEnum.image || iMMessage.getMsgType() == MsgTypeEnum.file || iMMessage.getMsgType() == MsgTypeEnum.video || iMMessage.getMsgType() == MsgTypeEnum.audio || (iMMessage.getAttachment() instanceof ProductAttachment) || (iMMessage.getAttachment() instanceof z) || (iMMessage.getAttachment() instanceof m) || (iMMessage.getAttachment() instanceof q) || isQuoteMessage(iMMessage))) ? false : true;
    }
}

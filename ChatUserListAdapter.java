package com.petkit.android.activities.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.model.ChatItem;
import com.petkit.android.model.ChatUser;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import cz.msebera.android.httpclient.Header;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes4.dex */
public class ChatUserListAdapter extends LoadMoreBaseAdapter {
    private final String[] FILTER_CHATITEM_IDS;
    private final String[] STATIC_CHATITEM_IDS;
    private SimpleDateFormat dateFormat;
    private boolean isFromMyselfFragment;
    private final Object mLock;

    @Override // com.petkit.android.activities.base.adapter.NormalBaseAdapter, android.widget.Adapter
    public long getItemId(int i) {
        return 0L;
    }

    public ChatUserListAdapter(Activity activity, List<ChatItem> list, boolean z) {
        super(activity, null);
        this.STATIC_CHATITEM_IDS = new String[]{Constants.JID_SYSTEM_API_COMMENT, Constants.JID_SYSTEM_API_FAVOR, Constants.JID_SYSTEM_API_AT, Constants.JID_SYSTEM_API_PUBLISH};
        this.FILTER_CHATITEM_IDS = new String[]{Constants.JID_TYPE_DOCTOR, "cs", Constants.JID_SYSTEM_API_PUBLISH};
        this.mLock = new Object();
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_12);
        this.isFromMyselfFragment = z;
        setListPageSize(100);
        setList(list);
    }

    public long getNewestTimeindex() {
        long timeindex = 0;
        for (int i = 0; i < this.STATIC_CHATITEM_IDS.length; i++) {
            if (getItem(i) != null && timeindex < getItem(i).getTimeindex()) {
                timeindex = getItem(i).getTimeindex();
            }
        }
        int count = getCount();
        String[] strArr = this.STATIC_CHATITEM_IDS;
        return (count <= strArr.length || getItem(strArr.length) == null || timeindex >= getItem(this.STATIC_CHATITEM_IDS.length).getTimeindex()) ? timeindex : getItem(this.STATIC_CHATITEM_IDS.length).getTimeindex();
    }

    public long getLastTimeindex() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return (getCount() <= this.STATIC_CHATITEM_IDS.length || jCurrentTimeMillis <= getItem(getLastIndex()).getTimeindex()) ? jCurrentTimeMillis : getItem(getLastIndex()).getTimeindex();
    }

    private List<ChatItem> removeStaticChatItems(List<ChatItem> list) {
        if (list == null) {
            return null;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            ChatItem chatItem = list.get(size);
            int i = 0;
            int i2 = 0;
            while (true) {
                String[] strArr = this.STATIC_CHATITEM_IDS;
                if (i2 >= strArr.length) {
                    break;
                }
                if (strArr[i2].equals(chatItem.getUserId())) {
                    list.remove(chatItem);
                    break;
                }
                i2++;
            }
            while (true) {
                String[] strArr2 = this.FILTER_CHATITEM_IDS;
                if (i >= strArr2.length) {
                    break;
                }
                if (strArr2[i].equals(chatItem.getUserId())) {
                    list.remove(chatItem);
                    break;
                }
                i++;
            }
        }
        return list;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter, com.petkit.android.activities.base.adapter.NormalBaseAdapter, com.petkit.android.activities.base.adapter.BaseAdapterHelper
    public void setList(List list) {
        List<T> list2 = this.mList;
        if (list2 == 0) {
            this.mList = new ArrayList();
        } else {
            list2.clear();
        }
        for (int i = 0; i < this.STATIC_CHATITEM_IDS.length; i++) {
            this.mList.add((T) ChatUtils.getChatItemOrCreate(CommonUtils.getCurrentUserId(), this.STATIC_CHATITEM_IDS[i]));
        }
        if (list != null) {
            this.mList.addAll(removeStaticChatItems(list));
        }
        List<T> list3 = this.mList;
        enableLoadMore(list3 == 0 || list3.size() >= this.mListPageSize);
        super.setList(this.mList);
        super.setList(this.mList);
        checkUpdateUserInfor();
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter, com.petkit.android.activities.base.adapter.NormalBaseAdapter, com.petkit.android.activities.base.adapter.BaseAdapterHelper
    public void addList(List list) {
        enableLoadMore(list != null && this.mList.size() >= this.mListPageSize);
        super.addList(list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void addNewestChatItems(List<ChatItem> list) {
        if (list != null) {
            synchronized (this.mLock) {
                for (int i = 0; i < list.size(); i++) {
                    try {
                        ChatItem chatItem = list.get(i);
                        int i2 = 0;
                        while (true) {
                            if (i2 < this.mList.size()) {
                                ChatItem item = getItem(i2);
                                if (chatItem == null || item == null || chatItem.getIdsindex() != item.getIdsindex()) {
                                    i2++;
                                } else {
                                    this.mList.remove(i2);
                                    if (i2 < this.STATIC_CHATITEM_IDS.length) {
                                        this.mList.add(i2, (T) chatItem);
                                    }
                                }
                            }
                        }
                    } finally {
                    }
                }
                this.mList.addAll(this.STATIC_CHATITEM_IDS.length, removeStaticChatItems(list));
                notifyDataSetChanged();
                checkUpdateUserInfor();
            }
        }
    }

    public void addChatItemsForNextPage(List<ChatItem> list) {
        if (list != null) {
            addList(removeStaticChatItems(list));
            notifyDataSetChanged();
            checkUpdateUserInfor();
        }
    }

    public void deleteItemByPosition(int i) {
        List<T> list = this.mList;
        if (list == 0 || list.size() <= i) {
            return;
        }
        this.mList.remove(i);
        notifyDataSetChanged();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void updateChatItems(String str) {
        if (CommonUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mLock) {
            try {
                String strReplaceAll = str.replaceAll(ChineseToPinyinResource.Field.COMMA, "");
                boolean zContains = strReplaceAll.contains(MsgService.MSG_CHATTING_ACCOUNT_ALL);
                for (int count = getCount() - 1; count >= 0; count--) {
                    ChatItem item = getItem(count);
                    if (item != null && (zContains || strReplaceAll.contains(String.valueOf(item.getTimeindex())))) {
                        Object chatItemByTimeindex = ChatUtils.getChatItemByTimeindex(item.getTimeindex());
                        if (chatItemByTimeindex != null) {
                            this.mList.remove(count);
                            this.mList.add(count, (T) chatItemByTimeindex);
                        }
                        strReplaceAll = strReplaceAll.replaceAll(String.valueOf(item.getTimeindex()), "");
                        PetkitLog.info(strReplaceAll);
                        if (CommonUtils.isEmpty(strReplaceAll)) {
                            break;
                        }
                    }
                }
                notifyDataSetChanged();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void updateChatItem(String str) {
        for (int i = 0; i < getCount(); i++) {
            ChatItem item = getItem(i);
            if (item != null && item.getUserId().equals(ChatUtils.convertJIDtoDatabaseId(str))) {
                item.setNewMsgCount(0);
                notifyDataSetChanged();
                return;
            }
        }
    }

    public class ViewHolder {
        public ImageView arrowRight;
        public ImageView chatAvatar;
        public TextView chatContent;
        public TextView chatName;
        public View chatPoint;
        public TextView chatTime;
        public TextView newChatCount;
        public RelativeLayout rlChat;

        public ViewHolder() {
        }
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    public View getContentView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View view2;
        int i2;
        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            View viewInflate = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_chat_list, viewGroup, false);
            ViewHolder viewHolder2 = new ViewHolder();
            viewHolder2.chatAvatar = (ImageView) viewInflate.findViewById(R.id.chat_avatar);
            viewHolder2.arrowRight = (ImageView) viewInflate.findViewById(R.id.arrow_right);
            viewHolder2.chatName = (TextView) viewInflate.findViewById(R.id.chat_name);
            viewHolder2.chatContent = (TextView) viewInflate.findViewById(R.id.chat_content);
            viewHolder2.chatTime = (TextView) viewInflate.findViewById(R.id.chat_time);
            viewHolder2.newChatCount = (TextView) viewInflate.findViewById(R.id.chat_count);
            viewHolder2.chatPoint = viewInflate.findViewById(R.id.chat_point);
            viewHolder2.rlChat = (RelativeLayout) viewInflate.findViewById(R.id.rl_chat);
            viewInflate.setTag(viewHolder2);
            viewHolder = viewHolder2;
            view2 = viewInflate;
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        ChatItem item = getItem(i);
        ChatUser chatUserOrCreate = ChatUtils.getChatUserOrCreate(item.getUserId());
        String avatar = "";
        int i3 = 8;
        if (Constants.JID_SYSTEM_API_COMMENT.equals(chatUserOrCreate.getUserId())) {
            i2 = R.drawable.icon_msg_comment;
            if (UserInforUtils.isPetPenSwitchOpen() && !this.isFromMyselfFragment) {
                viewHolder.chatContent.setVisibility(8);
                viewHolder.chatTime.setVisibility(8);
                chatUserOrCreate.setNick(this.mActivity.getString(R.string.Comment));
            } else {
                viewHolder.rlChat.setVisibility(8);
            }
        } else if (chatUserOrCreate.getUserId().equals(Constants.JID_SYSTEM_API_FAVOR)) {
            i2 = R.drawable.icon_msg_favor;
            if (UserInforUtils.isPetPenSwitchOpen() && !this.isFromMyselfFragment) {
                viewHolder.chatContent.setVisibility(8);
                viewHolder.chatTime.setVisibility(8);
                chatUserOrCreate.setNick(this.mActivity.getString(R.string.Favor));
            } else {
                viewHolder.rlChat.setVisibility(8);
            }
        } else if (chatUserOrCreate.getUserId().equals(Constants.JID_SYSTEM_API_AT)) {
            i2 = R.drawable.icon_msg_at;
            if (UserInforUtils.isPetPenSwitchOpen() && !this.isFromMyselfFragment) {
                viewHolder.chatContent.setVisibility(8);
                viewHolder.chatTime.setVisibility(8);
                chatUserOrCreate.setNick(this.mActivity.getString(R.string.At_me));
            } else {
                viewHolder.rlChat.setVisibility(8);
            }
        } else if (chatUserOrCreate.getUserId().equals(Constants.JID_SYSTEM_API_PUBLISH)) {
            i2 = R.drawable.icon_msg_notify;
            if (UserInforUtils.isPetPenSwitchOpen() && !this.isFromMyselfFragment) {
                viewHolder.chatContent.setVisibility(8);
                viewHolder.chatTime.setVisibility(8);
                chatUserOrCreate.setNick(this.mActivity.getString(R.string.System_message));
            } else {
                viewHolder.rlChat.setVisibility(8);
            }
        } else {
            if (chatUserOrCreate.getUserId().startsWith("yz.new")) {
                viewHolder.rlChat.setVisibility(8);
            }
            if (!CommonUtils.isEmpty(item.getHintmsg())) {
                viewHolder.chatContent.setText(item.getHintmsg());
                try {
                    viewHolder.chatTime.setText(CommonUtils.getChatTimeFromString(Long.valueOf(Long.parseLong(item.getUpdateTime()))));
                } catch (Exception unused) {
                    viewHolder.chatTime.setText(CommonUtils.getChatTimeFromString(this.mActivity, item.getUpdateTime()));
                }
            } else {
                viewHolder.chatTime.setText("");
                viewHolder.chatContent.setText(R.string.Empty);
            }
            viewHolder.chatContent.setVisibility(0);
            viewHolder.chatTime.setVisibility(0);
            i2 = chatUserOrCreate.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f;
            if (chatUserOrCreate.getUserId().equals(Constants.JID_TYPE_DOCTOR)) {
                chatUserOrCreate.setNick(this.mActivity.getString(R.string.Pet_doctor));
                avatar = chatUserOrCreate.getAvatar();
            } else if (chatUserOrCreate.getUserId().equals("cs")) {
                chatUserOrCreate.setNick(this.mActivity.getString(R.string.Official_cs));
                int i4 = R.drawable.icon_msg_cs;
                try {
                    viewHolder.chatTime.setText(this.dateFormat.format(Long.valueOf(Long.parseLong(item.getUpdateTime()) * 1000)));
                } catch (Exception e) {
                    PetkitLog.d("JID_TYPE_CS error:" + e.toString() + " " + e.getMessage() + item.getUpdateTime());
                    viewHolder.chatTime.setText("");
                }
                i2 = i4;
            } else {
                avatar = chatUserOrCreate.getAvatar();
            }
        }
        if (Constants.JID_SYSTEM_API_COMMENT.equals(chatUserOrCreate.getUserId())) {
            viewHolder.arrowRight.setVisibility(0);
            if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_COMMENT) == 0) {
                viewHolder.newChatCount.setVisibility(4);
                viewHolder.chatPoint.setVisibility(4);
            } else {
                viewHolder.newChatCount.setVisibility(0);
                viewHolder.chatPoint.setVisibility(8);
                if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_COMMENT) > 999) {
                    viewHolder.newChatCount.setText("...");
                } else {
                    viewHolder.newChatCount.setText(String.valueOf(CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_COMMENT)));
                }
            }
        } else if (chatUserOrCreate.getUserId().equals(Constants.JID_SYSTEM_API_FAVOR)) {
            viewHolder.arrowRight.setVisibility(0);
            if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_FAVOR) == 0) {
                viewHolder.newChatCount.setVisibility(4);
                viewHolder.chatPoint.setVisibility(4);
            } else {
                viewHolder.newChatCount.setVisibility(0);
                viewHolder.chatPoint.setVisibility(8);
                if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_FAVOR) > 999) {
                    viewHolder.newChatCount.setText("...");
                } else {
                    viewHolder.newChatCount.setText(String.valueOf(CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_FAVOR)));
                }
            }
        } else if (chatUserOrCreate.getUserId().equals(Constants.JID_SYSTEM_API_AT)) {
            viewHolder.arrowRight.setVisibility(0);
            if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_AT) == 0) {
                viewHolder.newChatCount.setVisibility(4);
                viewHolder.chatPoint.setVisibility(4);
            } else {
                viewHolder.newChatCount.setVisibility(0);
                viewHolder.chatPoint.setVisibility(8);
                if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_AT) > 999) {
                    viewHolder.newChatCount.setText("...");
                } else {
                    viewHolder.newChatCount.setText(String.valueOf(CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_AT)));
                }
            }
        } else if (chatUserOrCreate.getUserId().equals(Constants.JID_SYSTEM_API_PUBLISH)) {
            viewHolder.arrowRight.setVisibility(0);
            if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_PUBLISH) == 0) {
                viewHolder.newChatCount.setVisibility(4);
                viewHolder.chatPoint.setVisibility(4);
            } else {
                viewHolder.newChatCount.setVisibility(0);
                viewHolder.chatPoint.setVisibility(8);
                if (CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_PUBLISH) > 999) {
                    viewHolder.newChatCount.setText("...");
                } else {
                    viewHolder.newChatCount.setText(String.valueOf(CommonUtils.getUnReadMsgCount(Constants.JID_SYSTEM_API_PUBLISH)));
                }
            }
        } else {
            ImageView imageView = viewHolder.arrowRight;
            if (item.getNewMsgCount() == 0 && i < 4) {
                i3 = 0;
            }
            imageView.setVisibility(i3);
            if (item.getNewMsgCount() == 0) {
                viewHolder.newChatCount.setVisibility(4);
                viewHolder.chatPoint.setVisibility(4);
            } else {
                viewHolder.newChatCount.setVisibility(0);
                viewHolder.chatPoint.setVisibility(4);
                if (item.getNewMsgCount() > 999) {
                    viewHolder.newChatCount.setText("...");
                } else {
                    viewHolder.newChatCount.setText(String.valueOf(item.getNewMsgCount()));
                }
            }
        }
        viewHolder.chatName.setText(chatUserOrCreate.getNick());
        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(avatar).imageView(viewHolder.chatAvatar).errorPic(i2).transformation(new GlideRoundTransform(CommonUtils.getAppContext(), (int) DeviceUtils.dpToPixel(CommonUtils.getAppContext(), 12.0f))).build());
        return view2;
    }

    private void checkUpdateUserInfor() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getCount(); i++) {
            ChatItem item = getItem(i);
            if (item != null) {
                ChatUser chatUserOrCreate = ChatUtils.getChatUserOrCreate(item.getUserId());
                if (!item.getUserId().equals(Constants.JID_SYSTEM_API_COMMENT) && !item.getUserId().equals(Constants.JID_SYSTEM_API_FAVOR) && !item.getUserId().equals(Constants.JID_SYSTEM_API_AT) && !item.getUserId().equals(Constants.JID_SYSTEM_API_PUBLISH) && (CommonUtils.isEmpty(chatUserOrCreate.getNick()) || (item.getUserId().startsWith(Constants.JID_TYPE_DOCTOR) && CommonUtils.isEmpty(chatUserOrCreate.getAvatar())))) {
                    if (item.getUserId().startsWith("cs") || item.getUserId().startsWith(Constants.JID_TYPE_DOCTOR)) {
                        sb.append(String.format("\"%s\",", item.getUserId()));
                    } else {
                        sb.append(String.format("\"%s\",", "user." + item.getUserId()));
                    }
                }
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            getChatUserInfor(sb.toString());
        }
    }

    private void getChatUserInfor(String str) {
        HashMap map = new HashMap();
        map.put("jids", String.format("[%s]", str));
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_IM_FIND_USER, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.home.adapter.ChatUserListAdapter.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                int asInt;
                super.onSuccess(i, headerArr, bArr);
                if (((NormalBaseAdapter) ChatUserListAdapter.this).mActivity != null && ((BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class)).getError() == null) {
                    for (JsonElement jsonElement : new JsonParser().parse(this.responseResult).getAsJsonObject().getAsJsonArray("result")) {
                        String asString = jsonElement.getAsJsonObject().get("jid").getAsString();
                        if (asString != null) {
                            ChatUser chatUserOrCreate = ChatUtils.getChatUserOrCreate(asString);
                            if (chatUserOrCreate == null) {
                                chatUserOrCreate = new ChatUser();
                            }
                            if (jsonElement.getAsJsonObject().has("nick")) {
                                String asString2 = jsonElement.getAsJsonObject().get("nick").getAsString();
                                if (!CommonUtils.isEmpty(asString2) && !asString2.equals(chatUserOrCreate.getNick())) {
                                    chatUserOrCreate.setNick(asString2);
                                }
                            }
                            if (jsonElement.getAsJsonObject().has(Consts.PET_AVATAR)) {
                                String asString3 = jsonElement.getAsJsonObject().get(Consts.PET_AVATAR).getAsString();
                                if (!CommonUtils.isEmpty(asString3) && !asString3.equals(chatUserOrCreate.getAvatar())) {
                                    chatUserOrCreate.setAvatar(asString3);
                                }
                            }
                            if (jsonElement.getAsJsonObject().has("readOnly") && (asInt = jsonElement.getAsJsonObject().get("readOnly").getAsInt()) != chatUserOrCreate.getReadOnly()) {
                                chatUserOrCreate.setReadOnly(asInt);
                            }
                            SugarRecord.save(chatUserOrCreate);
                            ChatUserListAdapter.this.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override // com.petkit.android.activities.base.adapter.NormalBaseAdapter, android.widget.Adapter
    public ChatItem getItem(int i) {
        return (ChatItem) super.getItem(i);
    }
}

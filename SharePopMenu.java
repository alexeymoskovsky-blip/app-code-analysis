package com.petkit.android.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.BuildConfig;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orm.SugarRecord;
import com.petkit.android.activities.community.PostDetailActivity;
import com.petkit.android.activities.community.fragment.TopicDetailListFragment;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PostItem;
import com.petkit.android.model.Topic;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.ShareHelper;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes6.dex */
public class SharePopMenu {
    public static final int SHARE_TO_FACEBOOK = 0;
    public static final int SHARE_TO_TWITTER = 1;
    public static final int SHARE_TO_VK = 2;
    public static final int SHARE_TYPE_GO_WALK = 4;
    public static final int SHARE_TYPE_PETKIT = 2;
    public static final int SHARE_TYPE_PETKIT_RANK = 3;
    public static final int SHARE_TYPE_POST = 0;
    public static final int SHARE_TYPE_TOPIC = 1;
    public static final int SHARE_TYPE_WALK = 5;
    public static CallbackManager callbackManager;
    private String action;
    private boolean canPresentShareDialog;
    private View contentView;
    private String day;
    private Activity mActivity;
    private GoWalkData mGoWalkData;
    private BasePetkitWindow mShareMenu;
    private int mShareType;
    private int mType;
    private WalkData mWalkData;
    private Pet pet;
    private PostItem postItem;
    private String shareContentString;
    private ShareDialog shareDialog;
    private Topic topic;
    private String uploadFileString;
    private View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.petkit.android.widget.SharePopMenu.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int i;
            SharePopMenu.this.dismiss();
            int id = view.getId();
            if (id == R.id.share_facebook) {
                SharePopMenu.this.action = BuildConfig.LIBRARY_PACKAGE_NAME;
                i = 0;
            } else if (id == R.id.share_twitter) {
                SharePopMenu.this.action = "com.twitter";
                i = 1;
            } else if (id == R.id.share_vk) {
                SharePopMenu.this.action = "com.vk";
                i = 2;
            } else if (id == R.id.menu_cancel) {
                return;
            } else {
                i = -1;
            }
            if (i >= 0) {
                SharePopMenu sharePopMenu = SharePopMenu.this;
                if (!sharePopMenu.isInstalledApp(sharePopMenu.action)) {
                    Toast.makeText(SharePopMenu.this.mActivity, SharePopMenu.this.mActivity.getString(R.string.Hint_not_installed_app), 0).show();
                    return;
                }
                SharePopMenu.this.mType = i;
                if (SharePopMenu.this.mShareType != 2) {
                    if (SharePopMenu.this.mShareType != 3) {
                        if (SharePopMenu.this.mShareType == 0) {
                            SharePopMenu.this.sharepost(i);
                        } else if (SharePopMenu.this.mShareType == 4) {
                            SharePopMenu.this.shareGoWalk(i);
                        } else if (SharePopMenu.this.mShareType == 5) {
                            SharePopMenu.this.shareWalk(i);
                        } else {
                            SharePopMenu.this.shareTopic(i);
                        }
                    } else {
                        SharePopMenu sharePopMenu2 = SharePopMenu.this;
                        sharePopMenu2.getRankShareUrl(sharePopMenu2.checkCurrentPetIsDog() ? "1" : "2", SharePopMenu.this.pet.getId(), SharePopMenu.this.day);
                    }
                } else {
                    SharePopMenu sharePopMenu3 = SharePopMenu.this;
                    sharePopMenu3.getActivityShareUrl(sharePopMenu3.pet.getId(), SharePopMenu.this.day);
                }
            }
            if (id == R.id.menu_1) {
                if (SharePopMenu.this.mShareType != 0) {
                    if (SharePopMenu.this.mShareType == 1) {
                        SharePopMenu.this.dismiss();
                        if (SharePopMenu.this.topic == null) {
                            return;
                        }
                        if (SharePopMenu.this.topic.getCollect() == 0) {
                            SharePopMenu sharePopMenu4 = SharePopMenu.this;
                            sharePopMenu4.collectTopic(sharePopMenu4.topic.getTopicId());
                            return;
                        } else {
                            SharePopMenu sharePopMenu5 = SharePopMenu.this;
                            sharePopMenu5.removeCollectTopic(sharePopMenu5.topic.getTopicId());
                            return;
                        }
                    }
                    return;
                }
                SharePopMenu.this.dismiss();
                if (SharePopMenu.this.postItem == null) {
                    return;
                }
                if (SharePopMenu.this.postItem.getMyCollect() == 1) {
                    SharePopMenu.this.sendRemoveCollect();
                    return;
                } else {
                    SharePopMenu.this.sendCollect();
                    return;
                }
            }
            if (id == R.id.menu_2) {
                if (SharePopMenu.this.mShareType != 0) {
                    if (SharePopMenu.this.mShareType == 1) {
                        SharePopMenu.this.dismiss();
                        if (SharePopMenu.this.topic == null) {
                            return;
                        }
                        SharePopMenu.this.showReportDialog();
                        return;
                    }
                    return;
                }
                SharePopMenu.this.dismiss();
                if (!SharePopMenu.this.postItem.getAuthor().getId().equals(UserInforUtils.getCurrentUserId(SharePopMenu.this.mActivity))) {
                    SharePopMenu.this.showReportDialog();
                } else {
                    SharePopMenu.this.showDeleteDialog();
                }
            }
        }
    };
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() { // from class: com.petkit.android.widget.SharePopMenu.17
        @Override // com.facebook.FacebookCallback
        public void onCancel() {
            Log.d("petkit", "levin Canceled");
            Toast.makeText(SharePopMenu.this.mActivity, R.string.Cancel, 0).show();
        }

        @Override // com.facebook.FacebookCallback
        public void onError(FacebookException facebookException) {
            Log.d("petkit", "levin" + String.format("Error: %s", facebookException.toString()));
            Toast.makeText(SharePopMenu.this.mActivity, R.string.Share_failed, 0).show();
        }

        @Override // com.facebook.FacebookCallback
        public void onSuccess(Sharer.Result result) {
            Log.d("petkit", "levin Success!");
            result.getPostId();
        }

        public final void showResult(String str, String str2) {
            new AlertDialog.Builder(SharePopMenu.this.mActivity).setTitle(str).setMessage(str2).setPositiveButton(R.string.OK, (DialogInterface.OnClickListener) null).show();
        }
    };

    public SharePopMenu(Activity activity, CallbackManager callbackManager2) {
        this.contentView = LayoutInflater.from(activity).inflate(R.layout.layout_pop_share, (ViewGroup) null);
        this.mShareMenu = new BasePetkitWindow((Context) activity, this.contentView, false);
        this.mActivity = activity;
        callbackManager = callbackManager2;
        init(this.contentView);
    }

    public void setData(PostItem postItem) {
        this.mShareType = 0;
        this.postItem = postItem;
    }

    public void setData(Topic topic) {
        this.mShareType = 1;
        this.topic = topic;
    }

    public void setData(int i, String str, Pet pet, String str2) {
        this.mShareType = i;
        this.shareContentString = str;
        this.pet = pet;
        this.day = str2;
    }

    public void setData(GoWalkData goWalkData) {
        this.mShareType = 4;
        this.mGoWalkData = goWalkData;
    }

    public void setData(WalkData walkData) {
        this.mShareType = 5;
        this.mWalkData = walkData;
    }

    public void setData(int i, String... strArr) {
        this.mShareType = i;
        if (i == 0) {
            this.shareContentString = strArr[1];
            return;
        }
        if (i == 1) {
            this.shareContentString = strArr[1];
        } else if (i == 2) {
            this.shareContentString = this.mActivity.getString(R.string.Activity_share_text_format, strArr[1]);
        } else {
            if (i != 3) {
                return;
            }
            this.shareContentString = this.mActivity.getString(R.string.Activity_calorie_share_text, strArr[0], strArr[1]);
        }
    }

    private void init(View view) {
        view.findViewById(R.id.share_facebook).setOnClickListener(this.onClickListener);
        view.findViewById(R.id.share_twitter).setOnClickListener(this.onClickListener);
        view.findViewById(R.id.share_vk).setOnClickListener(this.onClickListener);
        view.findViewById(R.id.menu_1).setOnClickListener(this.onClickListener);
        view.findViewById(R.id.menu_2).setOnClickListener(this.onClickListener);
        view.findViewById(R.id.menu_3).setOnClickListener(this.onClickListener);
        view.findViewById(R.id.menu_4).setOnClickListener(this.onClickListener);
        view.findViewById(R.id.menu_cancel).setOnClickListener(this.onClickListener);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.widget.SharePopMenu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                SharePopMenu.this.dismiss();
            }
        });
        initFacebookSdk();
    }

    public void show() {
        int i = this.mShareType;
        if (i == 0) {
            if (this.postItem.getMyCollect() == 0) {
                setMenuText(R.id.menu_1, R.string.Collect, R.drawable.menu_collect);
            } else {
                setMenuText(R.id.menu_1, R.string.Uncollect, R.drawable.menu_collected);
            }
            if (this.postItem.getAuthor().getId().equals(CommonUtils.getCurrentUserId())) {
                setMenuText(R.id.menu_2, R.string.Delete, R.drawable.menu_delete);
            } else {
                setMenuText(R.id.menu_2, R.string.Report, R.drawable.menu_report);
            }
            setMenuText(R.id.menu_3, 0, 0);
            setMenuText(R.id.menu_4, 0, 0);
        } else if (i == 1) {
            Topic topic = this.topic;
            if (topic != null && topic.getCollect() == 0) {
                setMenuText(R.id.menu_1, R.string.Collect, R.drawable.menu_collect);
            } else {
                setMenuText(R.id.menu_1, R.string.Uncollect, R.drawable.menu_collected);
            }
            setMenuText(R.id.menu_2, R.string.Report, R.drawable.menu_report);
            setMenuText(R.id.menu_3, 0, 0);
            setMenuText(R.id.menu_4, 0, 0);
        } else if (i == 2 || i == 3 || i == 4 || i == 5) {
            this.contentView.findViewById(R.id.menu_second).setVisibility(8);
        }
        this.mShareMenu.setBackgroundDrawable(new BitmapDrawable());
        this.mShareMenu.setOutsideTouchable(true);
        this.mShareMenu.setFocusable(true);
        this.mShareMenu.showAtLocation(this.mActivity.getWindow().getDecorView(), 80, 0, 0);
    }

    private void setMenuText(int i, int i2, int i3) {
        TextView textView = (TextView) this.contentView.findViewById(i);
        if (i2 > 0) {
            textView.setText(i2);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, i3, 0, 0);
        } else {
            textView.setText("");
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInstalledApp(String str) {
        Iterator<PackageInfo> it = this.mActivity.getPackageManager().getInstalledPackages(8192).iterator();
        while (it.hasNext()) {
            if (it.next().packageName.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public void dismiss() {
        BasePetkitWindow basePetkitWindow = this.mShareMenu;
        if (basePetkitWindow != null) {
            basePetkitWindow.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getActivityShareUrl(String str, String str2) {
        HashMap map = new HashMap();
        map.put("petId", str);
        map.put("day", str2);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_SHAREPETACTIVITYDATA, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                String str3;
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    String result = resultStringRsp.getResult();
                    String[] strArrSplit = result.split(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                    if (strArrSplit == null) {
                        str3 = "";
                    } else {
                        str3 = strArrSplit[strArrSplit.length - 1];
                    }
                    SharePopMenu sharePopMenu = SharePopMenu.this;
                    sharePopMenu.shareCallback(sharePopMenu.mActivity, str3);
                    ShareHelper.startShare(SharePopMenu.callbackManager, SharePopMenu.this.mActivity, SharePopMenu.this.mType, SharePopMenu.this.shareContentString, result, null);
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sharepost(int i) {
        HashMap map = new HashMap();
        map.put("id", this.postItem.getId());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_SHARETPOST, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity) { // from class: com.petkit.android.widget.SharePopMenu.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                String str;
                super.onSuccess(i2, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, resultStringRsp.getError().getMsg());
                    return;
                }
                String[] strArrSplit = resultStringRsp.getResult().split(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                String string = "";
                if (strArrSplit == null) {
                    str = "";
                } else {
                    str = strArrSplit[strArrSplit.length - 1];
                }
                SharePopMenu sharePopMenu = SharePopMenu.this;
                sharePopMenu.shareCallback(sharePopMenu.mActivity, str);
                CallbackManager callbackManager2 = SharePopMenu.callbackManager;
                Activity activity = SharePopMenu.this.mActivity;
                int i3 = SharePopMenu.this.mType;
                if (!TextUtils.isEmpty(SharePopMenu.this.postItem.getDetail())) {
                    string = Html.fromHtml(SharePopMenu.this.postItem.getDetail()).toString();
                }
                ShareHelper.startShare(callbackManager2, activity, i3, string, resultStringRsp.getResult(), null);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareTopic(int i) {
        HashMap map = new HashMap();
        map.put("id", this.topic.getTopicId());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_SHARETOPIC, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                String str;
                super.onSuccess(i2, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, resultStringRsp.getError().getMsg());
                    return;
                }
                String[] strArrSplit = resultStringRsp.getResult().split(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                if (strArrSplit == null) {
                    str = "";
                } else {
                    str = strArrSplit[strArrSplit.length - 1];
                }
                SharePopMenu sharePopMenu = SharePopMenu.this;
                sharePopMenu.shareCallback(sharePopMenu.mActivity, str);
                ShareHelper.startShare(SharePopMenu.callbackManager, SharePopMenu.this.mActivity, SharePopMenu.this.mType, SharePopMenu.this.topic.getTopicname(), resultStringRsp.getResult(), null);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCollect() {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, this.postItem.getId());
        new HashMap().put("fromWhere", "post");
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_COLLECT, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PetkitToast.showShortToast(SharePopMenu.this.mActivity, R.string.Collected, R.drawable.icon_collect_true);
                SharePopMenu.this.postItem.setMyCollect(1);
                SharePopMenu.this.mActivity.setResult(-1);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setCollectsCount(chatCenter.getCollectsCount() + 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(SharePopMenu.this.mActivity).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRemoveCollect() {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, this.postItem.getId());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_REMOVE_COLLECT, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PetkitToast.showShortToast(SharePopMenu.this.mActivity, R.string.Uncollected);
                SharePopMenu.this.postItem.setMyCollect(0);
                SharePopMenu.this.mActivity.setResult(-1);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setCollectsCount(chatCenter.getCollectsCount() - 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(SharePopMenu.this.mActivity).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
                Intent intent = new Intent(Constants.BROADCAST_MSG_REMOVE_COLLECT);
                intent.putExtra(Constants.EXTRA_POST_ID, SharePopMenu.this.postItem.getId());
                LocalBroadcastManager.getInstance(SharePopMenu.this.mActivity).sendBroadcast(intent);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showReportDialog() {
        new AlertDialog.Builder(this.mActivity).setCancelable(true).setTitle(R.string.Warning).setMessage(R.string.Confirm_report).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.widget.SharePopMenu.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                String str;
                SharePopMenu sharePopMenu = SharePopMenu.this;
                if (sharePopMenu.mShareType == 0) {
                    str = "POST[" + SharePopMenu.this.postItem.getId() + "] has been reported.";
                } else {
                    str = "TOPIC[" + SharePopMenu.this.topic.getTopicId() + "] has been reported.";
                }
                sharePopMenu.sendUserSuggest(str);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.widget.SharePopMenu.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteDialog() {
        new AlertDialog.Builder(this.mActivity).setCancelable(true).setTitle(R.string.Warning).setMessage(R.string.Confirm_delete_post).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.widget.SharePopMenu.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                SharePopMenu.this.sendRemovePost();
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.widget.SharePopMenu.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUserSuggest(String str) {
        HashMap map = new HashMap();
        map.put("content", str);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_USER_SUGGEST, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.12
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, R.string.Succeed, R.drawable.toast_succeed);
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRemovePost() {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, this.postItem.getId());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_REMOVE, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.13
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PetkitToast.showShortToast(SharePopMenu.this.mActivity, R.string.Succeed);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setPostsCount(chatCenter.getPostsCount() - 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(SharePopMenu.this.mActivity).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
                Intent intent = new Intent(Constants.BROADCAST_MSG_REMOVE_POST);
                intent.putExtra(Constants.EXTRA_POST_ID, SharePopMenu.this.postItem.getId());
                LocalBroadcastManager.getInstance(SharePopMenu.this.mActivity).sendBroadcast(intent);
                SharePopMenu.this.mActivity.setResult(-1);
                if (SharePopMenu.this.mActivity instanceof PostDetailActivity) {
                    SharePopMenu.this.mActivity.finish();
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectTopic(String str) {
        HashMap map = new HashMap();
        map.put(TopicDetailListFragment.TOPICID, str);
        new HashMap().put("fromWhere", Constants.OVERSEA_BUSINESS_CONTROL_TOPIC);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_TOPIC_COLLECT, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.14
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, R.string.Collected, R.drawable.icon_collect_true);
                    SharePopMenu.this.topic.setCollect(1);
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCollectTopic(String str) {
        HashMap map = new HashMap();
        map.put(TopicDetailListFragment.TOPICID, str);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_TOPIC_COLLECT_REMOVE, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.15
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PetkitToast.showShortToast(SharePopMenu.this.mActivity, R.string.Uncollected, R.drawable.icon_collect_false);
                SharePopMenu.this.mActivity.setResult(-1);
                SharePopMenu.this.topic.setCollect(0);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRankShareUrl(String str, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("petType", str);
        map.put("petId", str2);
        map.put("day", str3);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_SHAREACTIVITY2, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.widget.SharePopMenu.16
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                String str4;
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    String result = resultStringRsp.getResult();
                    String[] strArrSplit = result.split(URLEncodedUtils.NAME_VALUE_SEPARATOR);
                    if (strArrSplit == null) {
                        str4 = "";
                    } else {
                        str4 = strArrSplit[strArrSplit.length - 1];
                    }
                    SharePopMenu sharePopMenu = SharePopMenu.this;
                    sharePopMenu.shareCallback(sharePopMenu.mActivity, str4);
                    ShareHelper.startShare(SharePopMenu.callbackManager, SharePopMenu.this.mActivity, SharePopMenu.this.mType, SharePopMenu.this.shareContentString, result, null);
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCurrentPetIsDog() {
        return this.pet.getType().getId() == 1;
    }

    public void initShareIntent(int i, String str, String str2, Bitmap bitmap, String str3) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(TweetComposer.MIME_TYPE_JPEG);
        List<ResolveInfo> listQueryIntentActivities = this.mActivity.getPackageManager().queryIntentActivities(intent, 0);
        if (listQueryIntentActivities.isEmpty()) {
            return;
        }
        for (ResolveInfo resolveInfo : listQueryIntentActivities) {
            if (resolveInfo.activityInfo.packageName.toLowerCase().contains("com.vk") || resolveInfo.activityInfo.name.toLowerCase().contains("com.vk")) {
                intent.putExtra("android.intent.extra.SUBJECT", str);
                intent.putExtra("android.intent.extra.TEXT", str + str2);
                intent.setPackage(resolveInfo.activityInfo.packageName);
                this.mActivity.startActivity(Intent.createChooser(intent, "Select"));
                return;
            }
        }
    }

    private void initFacebookSdk() {
        ShareDialog shareDialog = new ShareDialog(this.mActivity);
        this.shareDialog = shareDialog;
        shareDialog.registerCallback(callbackManager, this.shareCallback);
        this.canPresentShareDialog = ShareDialog.canShow((Class<? extends ShareContent<?, ?>>) ShareLinkContent.class);
    }

    public void postStatusUpdate(String str, String str2) {
        ShareLinkContent shareLinkContentBuild = new ShareLinkContent.Builder().setContentUrl(Uri.parse(str2)).build();
        if (this.canPresentShareDialog) {
            this.shareDialog.show(shareLinkContentBuild);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareCallback(Activity activity, String str) {
        HashMap map = new HashMap();
        map.put("shareId", str);
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_POST_SHARECALLBACK, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(activity) { // from class: com.petkit.android.widget.SharePopMenu.18
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                PetkitLog.d("shareCallback", "shareCallback success");
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareGoWalk(final int i) {
        if (this.mGoWalkData == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mGoWalkData.getDeviceId()));
        map.put("t1", this.mGoWalkData.getT1());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_GO_SHARE, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.19
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, resultStringRsp.getError().getMsg());
                    return;
                }
                Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(SharePopMenu.this.mActivity.getResources(), R.drawable.logo_white);
                PetkitLog.d("levin SHARE_TO_TWITTER");
                CallbackManager callbackManager2 = SharePopMenu.callbackManager;
                Activity activity = SharePopMenu.this.mActivity;
                int i3 = i;
                SharePopMenu sharePopMenu = SharePopMenu.this;
                ShareHelper.startShare(callbackManager2, activity, i3, sharePopMenu.getGoWalkDataShareContent(sharePopMenu.mGoWalkData), resultStringRsp.getResult(), bitmapDecodeResource);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareWalk(final int i) {
        if (this.mWalkData == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("t1", this.mWalkData.getT1());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_WALK_PET_SHARE, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.widget.SharePopMenu.20
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    PetkitToast.showShortToast(SharePopMenu.this.mActivity, resultStringRsp.getError().getMsg());
                    return;
                }
                Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(SharePopMenu.this.mActivity.getResources(), R.drawable.logo_white);
                PetkitLog.d("levin SHARE_TO_TWITTER");
                CallbackManager callbackManager2 = SharePopMenu.callbackManager;
                Activity activity = SharePopMenu.this.mActivity;
                int i3 = i;
                SharePopMenu sharePopMenu = SharePopMenu.this;
                ShareHelper.startShare(callbackManager2, activity, i3, sharePopMenu.getWalkDataShareContent(sharePopMenu.mWalkData), resultStringRsp.getResult(), bitmapDecodeResource);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getGoWalkDataShareContent(GoWalkData goWalkData) {
        StringBuilder sb = new StringBuilder();
        if (goWalkData.getPoi() != null) {
            sb.append(this.mActivity.getString(R.string.Go_walk_share_location_format, goWalkData.getPoi().getName()));
        }
        Activity activity = this.mActivity;
        sb.append(activity.getString(R.string.Go_walk_share_time_and_distance_format, GoDataUtils.formatGoTime(activity, goWalkData.getDuration(), "-"), GoDataUtils.formatDistance(this.mActivity, goWalkData.getDistance())));
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getWalkDataShareContent(WalkData walkData) {
        StringBuilder sb = new StringBuilder();
        if (walkData.getPoi() != null) {
            sb.append(this.mActivity.getString(R.string.Go_walk_share_location_format, walkData.getPoi().getName()));
        }
        Activity activity = this.mActivity;
        sb.append(activity.getString(R.string.Walk_share_time_and_distance_format, GoDataUtils.formatGoTime(activity, walkData.getDuration(), "-"), GoDataUtils.formatDistance(this.mActivity, walkData.getDistance())));
        return sb.toString();
    }
}

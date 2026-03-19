package com.petkit.android.activities.community;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.facebook.CallbackManager;
import com.facebook.share.internal.ShareConstants;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.activities.chat.emotion.EmotionEditText;
import com.petkit.android.activities.chat.emotion.EmotionKeyboardView;
import com.petkit.android.activities.chat.emotion.view.AutoHeightLayout;
import com.petkit.android.activities.community.adapter.CommentListAdapter;
import com.petkit.android.activities.community.adapter.CommunityListAdapter;
import com.petkit.android.activities.community.utils.SoftKeyBoardListener;
import com.petkit.android.activities.community.widget.AndroidBug5497Workaround;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.petkitBleDevice.AdWebViewActivity;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.universalWindow.BottomSelectListWindow;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.CommentListRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.PostDetailRsp;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.Comment;
import com.petkit.android.model.Emotion;
import com.petkit.android.model.PostItem;
import com.petkit.android.model.User;
import com.petkit.android.utils.AtInputFilter;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.ShareHelper;
import com.petkit.android.utils.UploadImagesUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.SharePopMenu;
import com.petkit.oversea.R;
import com.tencent.open.SocialConstants;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class PostDetailActivity extends BaseListActivity implements View.OnClickListener, EmotionKeyboardView.EmotionKeyboardListener, BottomSelectListWindow.OnClickListener {
    public static final String EXTRA_FROM_COMMENT = "comment";
    public static final String EXTRA_FROM_LINK = "link";
    public static final String EXTRA_FROM_MESSAGE = "message";
    public static final int RESULT_POST_DETAIL = 255;
    private CallbackManager callbackManager;
    private Comment commentFrom;
    private CommunityListAdapter communityListAdapter;
    private String curTagId;
    ImageView emotionImageView;
    ImageView feedbackImageView;
    private String flagFromComment;
    protected String imageFilePath;
    private EmotionEditText inputEditText;
    private String lastKey;
    private BroadcastReceiver mBroadcastReceiver;
    private Comment mComment;
    private Context mContext;
    private View mHeaderView;
    private AtInputFilter mInputFilter;
    private String mPostId;
    private BroadcastReceiver mScreenReceiver;
    private PostItem mTopListItem;
    private Handler mainHandler;
    private String replyNick;
    private String replyTo;
    private ImageView sendImageView;
    private SharePopMenu sharePopMenu;
    private int menuType = 0;
    boolean canDeletePost = true;
    Guide guide = null;
    private int clickPosition = -1;

    @Override // com.petkit.android.activities.universalWindow.BottomSelectListWindow.OnClickListener
    public void onCancelClick() {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.isNeedPhotoController = true;
        if (bundle != null) {
            this.mTopListItem = (PostItem) bundle.getSerializable(Constants.EXTRA_POST_DATA);
            this.mPostId = bundle.getString(Constants.EXTRA_POST_ID);
            this.curTagId = bundle.getString(Constants.EXTRA_TAG_ID);
        } else {
            this.mTopListItem = (PostItem) getIntent().getSerializableExtra(Constants.EXTRA_POST_DATA);
            this.mPostId = getIntent().getStringExtra(Constants.EXTRA_POST_ID);
            this.curTagId = getIntent().getStringExtra(Constants.EXTRA_TAG_ID);
        }
        this.mContext = this;
        String stringExtra = getIntent().getStringExtra(Constants.EXTRA_CLICK_FROM);
        this.flagFromComment = stringExtra;
        if ("message".equals(stringExtra)) {
            this.clickPosition = 0;
            this.replyTo = getIntent().getStringExtra("replyTo");
            this.replyNick = getIntent().getStringExtra("replyNick");
            this.commentFrom = (Comment) getIntent().getSerializableExtra("commentId");
            if (this.replyTo == null || this.replyNick == null) {
                this.flagFromComment = null;
            }
        }
        super.onCreate(bundle);
        AndroidBug5497Workaround.assistActivity(this, this.autoHeightView);
        this.callbackManager = CallbackManager.Factory.create();
        this.autoHeightView.enableEmotionKeyborad();
        registerBoradcastReceiver();
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() { // from class: com.petkit.android.activities.community.PostDetailActivity.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.activities.community.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardShow(int i) {
                if (DataHelper.getBooleanSF(PostDetailActivity.this, Consts.FEEDBACK_TIP_IS_FIRST)) {
                    return;
                }
                if (PostDetailActivity.this.mainHandler == null) {
                    PostDetailActivity.this.mainHandler = new Handler(Looper.getMainLooper());
                }
                PostDetailActivity.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.community.PostDetailActivity.1.1
                    public RunnableC00441() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        PostDetailActivity.this.showGuide();
                    }
                }, 500L);
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$1$1 */
            public class RunnableC00441 implements Runnable {
                public RunnableC00441() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    PostDetailActivity.this.showGuide();
                }
            }

            @Override // com.petkit.android.activities.community.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
            public void keyBoardHide(int i) {
                Guide guide = PostDetailActivity.this.guide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$1 */
    public class AnonymousClass1 implements SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.activities.community.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
        public void keyBoardShow(int i) {
            if (DataHelper.getBooleanSF(PostDetailActivity.this, Consts.FEEDBACK_TIP_IS_FIRST)) {
                return;
            }
            if (PostDetailActivity.this.mainHandler == null) {
                PostDetailActivity.this.mainHandler = new Handler(Looper.getMainLooper());
            }
            PostDetailActivity.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.community.PostDetailActivity.1.1
                public RunnableC00441() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    PostDetailActivity.this.showGuide();
                }
            }, 500L);
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$1$1 */
        public class RunnableC00441 implements Runnable {
            public RunnableC00441() {
            }

            @Override // java.lang.Runnable
            public void run() {
                PostDetailActivity.this.showGuide();
            }
        }

        @Override // com.petkit.android.activities.community.utils.SoftKeyBoardListener.OnSoftKeyBoardChangeListener
        public void keyBoardHide(int i) {
            Guide guide = PostDetailActivity.this.guide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    public void showGuide() {
        DataHelper.setBooleanSF(this, Consts.FEEDBACK_TIP_IS_FIRST, Boolean.TRUE);
        if (this.guide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.feedbackImageView).setAlpha(180).setHighTargetCorner(30).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 1.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, 1.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 1.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 1.0f)).setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.community.PostDetailActivity.2
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass2() {
            }
        }).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Feedback_guide_prompt), "", 2, 32, 90, -10, getResources().getString(R.string.Know), R.layout.layout_feedback_guide), new ConfirmListener() { // from class: com.petkit.android.activities.community.PostDetailActivity.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = PostDetailActivity.this.guide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.guide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$2 */
    public class AnonymousClass2 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass2() {
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$3 */
    public class AnonymousClass3 implements ConfirmListener {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = PostDetailActivity.this.guide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_POST_DATA, this.mTopListItem);
        bundle.putString(Constants.EXTRA_POST_ID, this.mPostId);
        bundle.putString(Constants.EXTRA_TAG_ID, this.curTagId);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_NOTIFYDATASETCHANGED));
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mainHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mainHandler = null;
        }
        CommunityListAdapter communityListAdapter = this.communityListAdapter;
        if (communityListAdapter != null) {
            communityListAdapter.unRegisterBroadcastReceiver();
        }
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        if (this.mTopListItem == null && isEmpty(this.mPostId)) {
            finish();
            return;
        }
        setTitle(R.string.Pet_blog_detail);
        setTitleLeftButton(R.drawable.btn_back_gray, this);
        setTitleLineVisibility(8);
        setBackgroundColor(-591879);
        if (this.mTopListItem == null) {
            setViewState(0);
        }
        initInputView();
        if (this.mTopListItem != null) {
            if ("message".equals(this.flagFromComment)) {
                this.inputEditText.setHint(getString(R.string.Reply) + ": " + this.replyNick);
            } else if (getIntent().getBooleanExtra("link", false)) {
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.community.PostDetailActivity.4
                    public AnonymousClass4() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        PostDetailActivity postDetailActivity = PostDetailActivity.this;
                        postDetailActivity.showSoftInput(postDetailActivity.inputEditText);
                    }
                }, 300L);
            }
        }
        getContentDetail();
        this.autoHeightView.setAutoHeightLayoutView(this.mKeyboardView);
        this.autoHeightView.hideAutoView();
        this.autoHeightView.setKeyBoardViewListener(new AutoHeightLayout.KeyBoardViewListener() { // from class: com.petkit.android.activities.community.PostDetailActivity.5
            public AnonymousClass5() {
            }

            @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
            public void onSoftPop() {
                PostDetailActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
            }

            @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
            public void onSoftClose() {
                PostDetailActivity.this.emotionImageView.setImageResource(R.drawable.keyboard_btn);
            }

            @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
            public void onKeyBoardClose() {
                PostDetailActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$4 */
    public class AnonymousClass4 implements Runnable {
        public AnonymousClass4() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PostDetailActivity postDetailActivity = PostDetailActivity.this;
            postDetailActivity.showSoftInput(postDetailActivity.inputEditText);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$5 */
    public class AnonymousClass5 implements AutoHeightLayout.KeyBoardViewListener {
        public AnonymousClass5() {
        }

        @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
        public void onSoftPop() {
            PostDetailActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
        }

        @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
        public void onSoftClose() {
            PostDetailActivity.this.emotionImageView.setImageResource(R.drawable.keyboard_btn);
        }

        @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
        public void onKeyBoardClose() {
            PostDetailActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
        }
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        if (this.mTopListItem != null) {
            this.mListAdapter = new CommentListAdapter(this, null, this.commentFrom);
            this.mListView.setDividerHeight(0);
            this.mListView.addHeaderView(initHeaderViewNew());
            this.mListView.setAdapter((ListAdapter) this.mListAdapter);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.comment_send) {
            String strTrim = this.inputEditText.getEditableText().toString().trim();
            PetkitLog.d("detail: " + strTrim);
            if (isEmpty(strTrim)) {
                return;
            }
            AtInputFilter atInputFilter = this.mInputFilter;
            if (atInputFilter == null) {
                sendComment(strTrim);
            } else {
                sendComment(atInputFilter.getContentStr());
            }
            this.autoHeightView.hideAutoView();
            return;
        }
        if (id == R.id.image_select_bn) {
            this.autoHeightView.hideAutoView();
            this.menuType = 1;
            PetkitLog.d("image", "image_select_bn imageFilePath= " + this.imageFilePath);
            if (isEmpty(this.imageFilePath)) {
                showCameraAndAblumMenu();
                return;
            } else {
                showPopMenu(getString(R.string.Delete));
                return;
            }
        }
        if (id == R.id.image_emotion_bn) {
            int i = this.autoHeightView.getmKeyboardState();
            if (i != 100) {
                if (i == 102) {
                    this.emotionImageView.setImageResource(R.drawable.emotion_btn);
                    showSoftInput(this.inputEditText);
                    return;
                } else if (i != 103) {
                    return;
                }
            }
            this.emotionImageView.setImageResource(R.drawable.keyboard_btn);
            this.autoHeightView.showAutoView();
            hideSoftInput();
            return;
        }
        if (id == R.id.image_feedback_bn) {
            startActivity(AdWebViewActivity.newIntent(this, "", "https://support.petkit.com/support/discussions/forums/51000132354"));
            return;
        }
        if (id == R.id.content_comments) {
            return;
        }
        if (id == R.id.title_right_image) {
            if (this.mTopListItem == null) {
                return;
            }
            hideSoftInput();
            new HashMap().put("fromWhere", "blogDetail");
            this.menuType = 2;
            SharePopMenu sharePopMenu = new SharePopMenu(this, this.callbackManager);
            this.sharePopMenu = sharePopMenu;
            sharePopMenu.setData(this.mTopListItem);
            this.sharePopMenu.show();
            return;
        }
        if (id == R.id.menu_1) {
            int i2 = this.menuType;
            if (i2 == 2) {
                this.sharePopMenu.dismiss();
                if (this.mTopListItem.getMyCollect() == 1) {
                    sendRemoveCollect();
                    return;
                } else {
                    sendCollect();
                    return;
                }
            }
            if (i2 == 3) {
                this.mPopupWindow.dismiss();
                removeComment(this.mComment);
                return;
            } else {
                if (i2 == 1) {
                    this.mPopupWindow.dismiss();
                    if (isEmpty(this.imageFilePath)) {
                        getPhotoFromCamera();
                        return;
                    } else {
                        this.imageFilePath = null;
                        this.sendImageView.setImageResource(R.drawable.camera);
                        return;
                    }
                }
                return;
            }
        }
        if (id == R.id.menu_2) {
            int i3 = this.menuType;
            if (i3 != 2) {
                if (i3 == 1) {
                    this.mPopupWindow.dismiss();
                    getPhotoFromAlbum();
                    return;
                }
                return;
            }
            this.sharePopMenu.dismiss();
            if (!this.canDeletePost || !this.mTopListItem.getAuthor().getId().equals(UserInforUtils.getCurrentUserId(this))) {
                if (this.mTopListItem.getAuthor().getId().equals(UserInforUtils.getCurrentUserId(this))) {
                    return;
                }
                showReportDialog();
                return;
            }
            showDeleteDialog();
            return;
        }
        if (id == R.id.menu_3) {
            showDeleteDialog();
            return;
        }
        if (id == R.id.menu_cancel) {
            this.mPopupWindow.dismiss();
        } else if (id == R.id.title_left_btn) {
            hideSoftInput();
            finish();
        }
    }

    @Override // com.petkit.android.activities.chat.emotion.EmotionKeyboardView.EmotionKeyboardListener
    public void onBigEmotionInput(Emotion emotion) {
        sendEmotionComment(emotion);
        this.autoHeightView.hideAutoView();
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        this.lastKey = null;
        getContentDetail();
    }

    @Override // com.petkit.android.activities.universalWindow.BottomSelectListWindow.OnClickListener
    public void onMenuOneClick() {
        sendRemovePost();
    }

    public class editInputFilter implements InputFilter {
        public editInputFilter() {
        }

        public /* synthetic */ editInputFilter(PostDetailActivity postDetailActivity, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (charSequence.toString().equalsIgnoreCase("@") || charSequence.toString().equalsIgnoreCase("＠")) {
                PostDetailActivity postDetailActivity = PostDetailActivity.this;
                postDetailActivity.startActivityForResult(SelectFriendsActivity.newIntent(postDetailActivity, null), 14115);
            }
            return charSequence;
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        CallbackManager callbackManager = this.callbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        }
        ShareHelper.dealWithOnActivityResult(this, i, i2, intent);
        if (i2 == -1) {
            if (i == 14115) {
                User user = (User) intent.getSerializableExtra(Constants.EXTRA_USER_DETAIL);
                this.mInputFilter.addFilter(user.getId(), user.getNick());
                showSoftInput(this.inputEditText);
            } else if (i == 392) {
                showSoftInput(this.inputEditText);
            }
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount < 0 || headerViewsCount >= this.mListAdapter.getCount() || this.mListAdapter.getItem(headerViewsCount) == null) {
            return;
        }
        if (((Comment) this.mListAdapter.getItem(headerViewsCount)).getCommentor().getId().equals(UserInforUtils.getCurrentUserId(this))) {
            this.menuType = 3;
            this.mComment = (Comment) this.mListAdapter.getItem(headerViewsCount);
            showPopMenu(getString(R.string.Delete));
            return;
        }
        this.clickPosition = headerViewsCount;
        this.replyTo = ((Comment) this.mListAdapter.getItem(headerViewsCount)).getCommentor().getId();
        this.inputEditText.setHint(getString(R.string.Reply) + ": " + ((Comment) this.mListAdapter.getItem(headerViewsCount)).getCommentor().getNick());
        if (this.autoHeightView.getmKeyboardState() == 100) {
            showSoftInput(this.inputEditText);
        } else {
            setListViewSelection(this.clickPosition + 1);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void uploadHeadPic(String str) {
        this.imageFilePath = str;
        setImageView();
    }

    private void setImageView() {
        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.imageFilePath).imageView(this.sendImageView).errorPic(R.drawable.default_image).build());
    }

    private void getContentDetail() {
        HashMap map = new HashMap();
        map.put("id", getPostId());
        map.put("favorSnapshot", String.valueOf(true));
        map.put("favorSnapshotLimit", String.valueOf(8));
        post(ApiTools.SAMPLE_API_POST_GET, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.community.PostDetailActivity.6
            public AnonymousClass6(Activity this) {
                super(this);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                PostDetailActivity.this.refreshComplete();
                if (PostDetailActivity.this.mTopListItem == null) {
                    PostDetailActivity.this.setViewState(2);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                PostDetailRsp postDetailRsp = (PostDetailRsp) this.gson.fromJson(this.responseResult, PostDetailRsp.class);
                if (postDetailRsp.getError() != null) {
                    PostDetailActivity.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, postDetailRsp.getError().getMsg(), 0);
                    ((BaseActivity) PostDetailActivity.this).mBottomView.setVisibility(8);
                } else if (postDetailRsp.getResult() != null) {
                    PostDetailActivity.this.mTopListItem = postDetailRsp.getResult();
                    if (((BaseListActivity) PostDetailActivity.this).mListAdapter == null) {
                        PostDetailActivity.this.initAdapter();
                    }
                    PostDetailActivity.this.getComments();
                    PostDetailActivity.this.refreshHeaderViewNew();
                    PostDetailActivity.this.setViewState(1);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (((BaseListActivity) PostDetailActivity.this).mNetworkState != 0 || ((BaseListActivity) PostDetailActivity.this).mListAdapter == null) {
                    return;
                }
                ((BaseListActivity) PostDetailActivity.this).mNetworkState = 2;
                ((BaseListActivity) PostDetailActivity.this).mListAdapter.notifyDataSetChanged();
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$6 */
    public class AnonymousClass6 extends AsyncHttpRespHandler {
        public AnonymousClass6(Activity this) {
            super(this);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFinish() {
            super.onFinish();
            PostDetailActivity.this.refreshComplete();
            if (PostDetailActivity.this.mTopListItem == null) {
                PostDetailActivity.this.setViewState(2);
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            PostDetailRsp postDetailRsp = (PostDetailRsp) this.gson.fromJson(this.responseResult, PostDetailRsp.class);
            if (postDetailRsp.getError() != null) {
                PostDetailActivity.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, postDetailRsp.getError().getMsg(), 0);
                ((BaseActivity) PostDetailActivity.this).mBottomView.setVisibility(8);
            } else if (postDetailRsp.getResult() != null) {
                PostDetailActivity.this.mTopListItem = postDetailRsp.getResult();
                if (((BaseListActivity) PostDetailActivity.this).mListAdapter == null) {
                    PostDetailActivity.this.initAdapter();
                }
                PostDetailActivity.this.getComments();
                PostDetailActivity.this.refreshHeaderViewNew();
                PostDetailActivity.this.setViewState(1);
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            super.onFailure(i, headerArr, bArr, th);
            if (((BaseListActivity) PostDetailActivity.this).mNetworkState != 0 || ((BaseListActivity) PostDetailActivity.this).mListAdapter == null) {
                return;
            }
            ((BaseListActivity) PostDetailActivity.this).mNetworkState = 2;
            ((BaseListActivity) PostDetailActivity.this).mListAdapter.notifyDataSetChanged();
        }
    }

    private void initInputView() {
        setBottomView(R.layout.layout_comment_input);
        if (UserInforUtils.isPetPenSwitchOpen()) {
            findViewById(R.id.coment_paraent).setVisibility(0);
        } else {
            findViewById(R.id.coment_paraent).setVisibility(8);
        }
        findViewById(R.id.comment_send).setOnClickListener(this);
        EmotionEditText emotionEditText = (EmotionEditText) findViewById(R.id.input_comment_detail);
        this.inputEditText = emotionEditText;
        emotionEditText.setFilters(new InputFilter[]{new editInputFilter()});
        this.mInputFilter = new AtInputFilter(this, this.inputEditText);
        this.inputEditText.setHint(R.string.Hint_input_comment);
        ImageView imageView = (ImageView) findViewById(R.id.image_select_bn);
        this.sendImageView = imageView;
        imageView.setOnClickListener(this);
        this.inputEditText.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.image_emotion_bn);
        this.emotionImageView = imageView2;
        imageView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.image_feedback_bn);
        this.feedbackImageView = imageView3;
        imageView3.setOnClickListener(this);
        setKeyboardView(R.layout.layout_keyboard);
    }

    public void getComments() {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, getPostId());
        map.put("limit", "20");
        map.put("lastKey", this.lastKey);
        post(ApiTools.SAMPLE_API_COMMENTS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.community.PostDetailActivity.7
            public AnonymousClass7(Activity this) {
                super(this);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                PostDetailActivity.this.refreshComplete();
                if (((BaseListActivity) PostDetailActivity.this).mNetworkState == 0) {
                    ((BaseListActivity) PostDetailActivity.this).mNetworkState = 2;
                    if (((BaseListActivity) PostDetailActivity.this).mListAdapter != null) {
                        ((BaseListActivity) PostDetailActivity.this).mListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (!this.responseResult.contains("https")) {
                    this.responseResult = this.responseResult.replaceAll("http", "https");
                }
                PetkitLog.d("responseResultresponseResult:" + this.responseResult);
                CommentListRsp commentListRsp = (CommentListRsp) this.gson.fromJson(this.responseResult, CommentListRsp.class);
                if (commentListRsp.getError() != null) {
                    ((BaseListActivity) PostDetailActivity.this).mNetworkState = 2;
                    PostDetailActivity.this.showShortToast(commentListRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (commentListRsp.getResult() != null) {
                    ((BaseListActivity) PostDetailActivity.this).mNetworkState = 1;
                    List<Comment> list = commentListRsp.getResult().getList();
                    if (PostDetailActivity.this.commentFrom != null) {
                        list.add(0, PostDetailActivity.this.commentFrom);
                    }
                    PostDetailActivity postDetailActivity = PostDetailActivity.this;
                    if (postDetailActivity.isEmpty(postDetailActivity.lastKey)) {
                        ((BaseListActivity) PostDetailActivity.this).mListAdapter.setList(list, true);
                        if ("comment".equals(PostDetailActivity.this.flagFromComment)) {
                            new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.community.PostDetailActivity.7.1
                                public AnonymousClass1() {
                                }

                                @Override // java.lang.Runnable
                                public void run() {
                                    ((BaseListActivity) PostDetailActivity.this).mListView.smoothScrollToPositionFromTop(1, 0, 500);
                                }
                            }, 50L);
                        }
                    } else {
                        ((BaseListActivity) PostDetailActivity.this).mListAdapter.addList(list, true);
                    }
                    if (PostDetailActivity.this.clickPosition != -1) {
                        PostDetailActivity postDetailActivity2 = PostDetailActivity.this;
                        postDetailActivity2.showSoftInput(postDetailActivity2.inputEditText);
                    }
                    if (commentListRsp.getResult().getList() == null && commentListRsp.getResult().getList().size() == 0) {
                        ((BaseListActivity) PostDetailActivity.this).mNetworkState = 3;
                    } else {
                        PostDetailActivity.this.lastKey = commentListRsp.getResult().getLastKey();
                    }
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$7$1 */
            public class AnonymousClass1 implements Runnable {
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    ((BaseListActivity) PostDetailActivity.this).mListView.smoothScrollToPositionFromTop(1, 0, 500);
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$7 */
    public class AnonymousClass7 extends AsyncHttpRespHandler {
        public AnonymousClass7(Activity this) {
            super(this);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFinish() {
            super.onFinish();
            PostDetailActivity.this.refreshComplete();
            if (((BaseListActivity) PostDetailActivity.this).mNetworkState == 0) {
                ((BaseListActivity) PostDetailActivity.this).mNetworkState = 2;
                if (((BaseListActivity) PostDetailActivity.this).mListAdapter != null) {
                    ((BaseListActivity) PostDetailActivity.this).mListAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            if (!this.responseResult.contains("https")) {
                this.responseResult = this.responseResult.replaceAll("http", "https");
            }
            PetkitLog.d("responseResultresponseResult:" + this.responseResult);
            CommentListRsp commentListRsp = (CommentListRsp) this.gson.fromJson(this.responseResult, CommentListRsp.class);
            if (commentListRsp.getError() != null) {
                ((BaseListActivity) PostDetailActivity.this).mNetworkState = 2;
                PostDetailActivity.this.showShortToast(commentListRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            if (commentListRsp.getResult() != null) {
                ((BaseListActivity) PostDetailActivity.this).mNetworkState = 1;
                List<Comment> list = commentListRsp.getResult().getList();
                if (PostDetailActivity.this.commentFrom != null) {
                    list.add(0, PostDetailActivity.this.commentFrom);
                }
                PostDetailActivity postDetailActivity = PostDetailActivity.this;
                if (postDetailActivity.isEmpty(postDetailActivity.lastKey)) {
                    ((BaseListActivity) PostDetailActivity.this).mListAdapter.setList(list, true);
                    if ("comment".equals(PostDetailActivity.this.flagFromComment)) {
                        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.community.PostDetailActivity.7.1
                            public AnonymousClass1() {
                            }

                            @Override // java.lang.Runnable
                            public void run() {
                                ((BaseListActivity) PostDetailActivity.this).mListView.smoothScrollToPositionFromTop(1, 0, 500);
                            }
                        }, 50L);
                    }
                } else {
                    ((BaseListActivity) PostDetailActivity.this).mListAdapter.addList(list, true);
                }
                if (PostDetailActivity.this.clickPosition != -1) {
                    PostDetailActivity postDetailActivity2 = PostDetailActivity.this;
                    postDetailActivity2.showSoftInput(postDetailActivity2.inputEditText);
                }
                if (commentListRsp.getResult().getList() == null && commentListRsp.getResult().getList().size() == 0) {
                    ((BaseListActivity) PostDetailActivity.this).mNetworkState = 3;
                } else {
                    PostDetailActivity.this.lastKey = commentListRsp.getResult().getLastKey();
                }
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$7$1 */
        public class AnonymousClass1 implements Runnable {
            public AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                ((BaseListActivity) PostDetailActivity.this).mListView.smoothScrollToPositionFromTop(1, 0, 500);
            }
        }
    }

    private void sendComment(String str) {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, getPostId());
        map.put("detail", "" + str);
        if (!isEmpty(this.replyTo)) {
            map.put("replyTo", "" + this.replyTo);
        }
        showLoadDialog();
        if (!isEmpty(this.imageFilePath)) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(this.imageFilePath, "");
            new UploadImagesUtils(UploadImagesUtils.NS_COMMENT, linkedHashMap, new UploadImagesUtils.IUploadImagesListener() { // from class: com.petkit.android.activities.community.PostDetailActivity.8
                public final /* synthetic */ Map val$params;

                public AnonymousClass8(Map map2) {
                    map = map2;
                }

                @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
                public void onUploadImageSuccess(LinkedHashMap<String, String> linkedHashMap2) {
                    String str2 = linkedHashMap2.get(PostDetailActivity.this.imageFilePath);
                    PetkitLog.d("url = " + str2);
                    map.put(SocialConstants.PARAM_IMG_URL, str2);
                    PostDetailActivity.this.sendCommentDetail(map);
                }

                @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
                public void onUploadImageFailed() {
                    LoadDialog.dismissDialog();
                    PostDetailActivity.this.showShortToast(R.string.Publish_post_failed);
                }
            }, 2).start();
            return;
        }
        sendCommentDetail(map2);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$8 */
    public class AnonymousClass8 implements UploadImagesUtils.IUploadImagesListener {
        public final /* synthetic */ Map val$params;

        public AnonymousClass8(Map map2) {
            map = map2;
        }

        @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
        public void onUploadImageSuccess(LinkedHashMap<String, String> linkedHashMap2) {
            String str2 = linkedHashMap2.get(PostDetailActivity.this.imageFilePath);
            PetkitLog.d("url = " + str2);
            map.put(SocialConstants.PARAM_IMG_URL, str2);
            PostDetailActivity.this.sendCommentDetail(map);
        }

        @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
        public void onUploadImageFailed() {
            LoadDialog.dismissDialog();
            PostDetailActivity.this.showShortToast(R.string.Publish_post_failed);
        }
    }

    private void sendEmotionComment(Emotion emotion) {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, getPostId());
        map.put("bigEmotion", new Gson().toJson(emotion));
        if (!isEmpty(this.replyTo)) {
            map.put("replyTo", "" + this.replyTo);
        }
        showLoadDialog();
        sendCommentDetail(map);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$9 */
    public class AnonymousClass9 implements PetkitCallback<Comment> {
        public final /* synthetic */ Map val$params;

        public AnonymousClass9(Map map) {
            map = map;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(Comment comment) {
            PostDetailActivity.this.dismissLoadDialog();
            ((BaseListActivity) PostDetailActivity.this).mNetworkState = 1;
            if (PostDetailActivity.this.isFinishing()) {
                return;
            }
            if (TextUtils.isEmpty((CharSequence) map.get("bigEmotion"))) {
                PostDetailActivity.this.initCommonInputView();
            }
            PostDetailActivity.this.mTopListItem.setComment(PostDetailActivity.this.mTopListItem.getComment() + 1);
            PostDetailActivity.this.refreshHeaderViewNew();
            if (((BaseListActivity) PostDetailActivity.this).mListAdapter != null) {
                ((CommentListAdapter) ((BaseListActivity) PostDetailActivity.this).mListAdapter).addCommentItem(comment);
                ((BaseListActivity) PostDetailActivity.this).mListView.smoothScrollToPosition(((BaseListActivity) PostDetailActivity.this).mListAdapter.getCount());
            }
            Intent intent = new Intent(Constants.BROADCAST_MSG_COMMENT_POST);
            intent.putExtra(Constants.EXTRA_POST_DATA, PostDetailActivity.this.mTopListItem);
            intent.putExtra(Constants.EXTRA_POST_COMMENT, comment);
            LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(intent);
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
            PostDetailActivity.this.dismissLoadDialog();
            PostDetailActivity.this.showLongToast(errorInfor.getMsg(), R.drawable.toast_failed);
        }
    }

    public void sendCommentDetail(Map<String, String> map) {
        WebModelRepository.getInstance().sendCommentDetail(map, new PetkitCallback<Comment>() { // from class: com.petkit.android.activities.community.PostDetailActivity.9
            public final /* synthetic */ Map val$params;

            public AnonymousClass9(Map map2) {
                map = map2;
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(Comment comment) {
                PostDetailActivity.this.dismissLoadDialog();
                ((BaseListActivity) PostDetailActivity.this).mNetworkState = 1;
                if (PostDetailActivity.this.isFinishing()) {
                    return;
                }
                if (TextUtils.isEmpty((CharSequence) map.get("bigEmotion"))) {
                    PostDetailActivity.this.initCommonInputView();
                }
                PostDetailActivity.this.mTopListItem.setComment(PostDetailActivity.this.mTopListItem.getComment() + 1);
                PostDetailActivity.this.refreshHeaderViewNew();
                if (((BaseListActivity) PostDetailActivity.this).mListAdapter != null) {
                    ((CommentListAdapter) ((BaseListActivity) PostDetailActivity.this).mListAdapter).addCommentItem(comment);
                    ((BaseListActivity) PostDetailActivity.this).mListView.smoothScrollToPosition(((BaseListActivity) PostDetailActivity.this).mListAdapter.getCount());
                }
                Intent intent = new Intent(Constants.BROADCAST_MSG_COMMENT_POST);
                intent.putExtra(Constants.EXTRA_POST_DATA, PostDetailActivity.this.mTopListItem);
                intent.putExtra(Constants.EXTRA_POST_COMMENT, comment);
                LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(intent);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                PostDetailActivity.this.dismissLoadDialog();
                PostDetailActivity.this.showLongToast(errorInfor.getMsg(), R.drawable.toast_failed);
            }
        });
    }

    private void sendCollect() {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, getPostId());
        post(ApiTools.SAMPLE_API_POST_COLLECT, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.community.PostDetailActivity.10
            public AnonymousClass10(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PostDetailActivity.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PostDetailActivity.this.showLongToast(R.string.Collected, R.drawable.icon_collect_true);
                PostDetailActivity.this.mTopListItem.setMyCollect(1);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setCollectsCount(chatCenter.getCollectsCount() + 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$10 */
    public class AnonymousClass10 extends AsyncHttpRespHandler {
        public AnonymousClass10(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PostDetailActivity.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            PostDetailActivity.this.showLongToast(R.string.Collected, R.drawable.icon_collect_true);
            PostDetailActivity.this.mTopListItem.setMyCollect(1);
            ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
            chatCenter.setCollectsCount(chatCenter.getCollectsCount() + 1);
            SugarRecord.save(chatCenter);
            LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
        }
    }

    private void sendRemoveCollect() {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, getPostId());
        post(ApiTools.SAMPLE_API_POST_REMOVE_COLLECT, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.community.PostDetailActivity.11
            public AnonymousClass11(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PostDetailActivity.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PostDetailActivity.this.showLongToast(R.string.Uncollected);
                PostDetailActivity.this.mTopListItem.setMyCollect(0);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setCollectsCount(chatCenter.getCollectsCount() - 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$11 */
    public class AnonymousClass11 extends AsyncHttpRespHandler {
        public AnonymousClass11(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PostDetailActivity.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            PostDetailActivity.this.showLongToast(R.string.Uncollected);
            PostDetailActivity.this.mTopListItem.setMyCollect(0);
            ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
            chatCenter.setCollectsCount(chatCenter.getCollectsCount() - 1);
            SugarRecord.save(chatCenter);
            LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
        }
    }

    public void sendUserSuggest(String str) {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, str);
        post(ApiTools.SAMPLE_API_POST_REPORT, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.community.PostDetailActivity.12
            public AnonymousClass12(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PostDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    PostDetailActivity.this.showLongToast(R.string.Succeed, R.drawable.toast_succeed);
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$12 */
    public class AnonymousClass12 extends AsyncHttpRespHandler {
        public AnonymousClass12(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PostDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
            } else {
                PostDetailActivity.this.showLongToast(R.string.Succeed, R.drawable.toast_succeed);
            }
        }
    }

    public void sendRemovePost() {
        HashMap map = new HashMap();
        map.put(ShareConstants.RESULT_POST_ID, this.mTopListItem.getId());
        post(ApiTools.SAMPLE_API_POST_REMOVE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.community.PostDetailActivity.13
            public AnonymousClass13(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PostDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PostDetailActivity.this.showLongToast(R.string.Succeed);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setPostsCount(chatCenter.getPostsCount() - 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
                PostDetailActivity.this.setResult(-1);
                PostDetailActivity.this.finish();
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$13 */
    public class AnonymousClass13 extends AsyncHttpRespHandler {
        public AnonymousClass13(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PostDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            PostDetailActivity.this.showLongToast(R.string.Succeed);
            ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
            chatCenter.setPostsCount(chatCenter.getPostsCount() - 1);
            SugarRecord.save(chatCenter);
            LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
            PostDetailActivity.this.setResult(-1);
            PostDetailActivity.this.finish();
        }
    }

    private void removeComment(Comment comment) {
        HashMap map = new HashMap();
        map.put("commentId", comment.getId());
        post(ApiTools.SAMPLE_API_POST_REMOVECOMMENT, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.community.PostDetailActivity.14
            public final /* synthetic */ Comment val$comment;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass14(Activity this, boolean z, Comment comment2) {
                super(this, z);
                comment = comment2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PostDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PostDetailActivity.this.showLongToast(R.string.Delete);
                PostDetailActivity.this.initCommonInputView();
                PostDetailActivity.this.mTopListItem.setComment(PostDetailActivity.this.mTopListItem.getComment() - 1);
                PostDetailActivity.this.refreshHeaderViewNew();
                ((CommentListAdapter) ((BaseListActivity) PostDetailActivity.this).mListAdapter).removeCommentItem(comment);
                Intent intent = new Intent(Constants.BROADCAST_MSG_REMOVE_COMMENT_POST);
                intent.putExtra(Constants.EXTRA_POST_DATA, PostDetailActivity.this.mTopListItem);
                intent.putExtra(Constants.EXTRA_POST_COMMENT, comment);
                LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(intent);
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$14 */
    public class AnonymousClass14 extends AsyncHttpRespHandler {
        public final /* synthetic */ Comment val$comment;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass14(Activity this, boolean z, Comment comment2) {
            super(this, z);
            comment = comment2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                PostDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            PostDetailActivity.this.showLongToast(R.string.Delete);
            PostDetailActivity.this.initCommonInputView();
            PostDetailActivity.this.mTopListItem.setComment(PostDetailActivity.this.mTopListItem.getComment() - 1);
            PostDetailActivity.this.refreshHeaderViewNew();
            ((CommentListAdapter) ((BaseListActivity) PostDetailActivity.this).mListAdapter).removeCommentItem(comment);
            Intent intent = new Intent(Constants.BROADCAST_MSG_REMOVE_COMMENT_POST);
            intent.putExtra(Constants.EXTRA_POST_DATA, PostDetailActivity.this.mTopListItem);
            intent.putExtra(Constants.EXTRA_POST_COMMENT, comment);
            LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(intent);
        }
    }

    private void showReportDialog() {
        new AlertDialog.Builder(this).setCancelable(true).setTitle(R.string.Warning).setMessage(R.string.Confirm_report).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.community.PostDetailActivity.15
            public AnonymousClass15() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PostDetailActivity postDetailActivity = PostDetailActivity.this;
                postDetailActivity.sendUserSuggest(postDetailActivity.mTopListItem.getId());
            }
        }).setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$15 */
    public class AnonymousClass15 implements DialogInterface.OnClickListener {
        public AnonymousClass15() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            PostDetailActivity postDetailActivity = PostDetailActivity.this;
            postDetailActivity.sendUserSuggest(postDetailActivity.mTopListItem.getId());
        }
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this).setCancelable(true).setTitle(R.string.Warning).setMessage(R.string.Confirm_delete_post).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.community.PostDetailActivity.16
            public AnonymousClass16() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PostDetailActivity.this.sendRemovePost();
            }
        }).setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$16 */
    public class AnonymousClass16 implements DialogInterface.OnClickListener {
        public AnonymousClass16() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            PostDetailActivity.this.sendRemovePost();
        }
    }

    public void initCommonInputView() {
        if (!"message".equals(this.flagFromComment)) {
            this.replyTo = null;
            this.inputEditText.setHint(R.string.Hint_input_comment);
        } else {
            this.inputEditText.setHint(getString(R.string.Reply) + ": " + this.replyNick);
        }
        this.inputEditText.setText("");
        this.imageFilePath = null;
        this.sendImageView.setImageResource(R.drawable.camera);
        PetkitLog.d("image", "initCommonInputView sendImageView R.drawable.camera");
    }

    private String getPostId() {
        PostItem postItem = this.mTopListItem;
        return postItem == null ? this.mPostId : postItem.getId();
    }

    private View initHeaderViewNew() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mTopListItem);
        CommunityListAdapter communityListAdapter = new CommunityListAdapter(this, arrayList, 4);
        this.communityListAdapter = communityListAdapter;
        View view = communityListAdapter.getView(0, null, null);
        this.mHeaderView = view;
        return view;
    }

    public void refreshHeaderViewNew() {
        if (this.communityListAdapter != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.mTopListItem);
            this.communityListAdapter.setList(arrayList);
            this.communityListAdapter.getView(0, this.mHeaderView, null);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        hideSoftInput();
        this.inputEditText.refreshEmotionInputEventListener();
        this.mKeyboardView.setEmotionKeyboardListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        NormalBaseAdapter normalBaseAdapter = this.mListAdapter;
        if (normalBaseAdapter == null) {
            setViewState(0);
            getContentDetail();
        } else {
            this.mNetworkState = 0;
            normalBaseAdapter.notifyDataSetChanged();
            getComments();
        }
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, com.jess.arms.widget.InputMethodRelativeLayout.OnSizeChangedListener
    public void onSizeChange(boolean z, int i, int i2) {
        int i3;
        NormalBaseAdapter normalBaseAdapter = this.mListAdapter;
        if (normalBaseAdapter == null || normalBaseAdapter.getCount() <= 0 || (i3 = this.clickPosition) == -1) {
            return;
        }
        setListViewSelection(i3 + 1);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getComments();
    }

    private void setListViewSelection(int i) {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        View view = this.mListAdapter.getView(this.clickPosition, null, this.mListView);
        view.measure(View.MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels, 1073741824), 0);
        int measuredHeight = view.getMeasuredHeight();
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.community.PostDetailActivity.17
            public final /* synthetic */ int val$contentVisibleHeight;
            public final /* synthetic */ int val$measureHeight;
            public final /* synthetic */ int val$position;

            public AnonymousClass17(int i2, int i3, int measuredHeight2) {
                i = i2;
                i = i3;
                i = measuredHeight2;
            }

            @Override // java.lang.Runnable
            public void run() {
                ((BaseListActivity) PostDetailActivity.this).mListView.setSelectionFromTop(i, i - i);
            }
        }, 100L);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$17 */
    public class AnonymousClass17 implements Runnable {
        public final /* synthetic */ int val$contentVisibleHeight;
        public final /* synthetic */ int val$measureHeight;
        public final /* synthetic */ int val$position;

        public AnonymousClass17(int i2, int i3, int measuredHeight2) {
            i = i2;
            i = i3;
            i = measuredHeight2;
        }

        @Override // java.lang.Runnable
        public void run() {
            ((BaseListActivity) PostDetailActivity.this).mListView.setSelectionFromTop(i, i - i);
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    @SuppressLint({"RestrictedApi"})
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            EmotionKeyboardView emotionKeyboardView = this.mKeyboardView;
            if (emotionKeyboardView != null && emotionKeyboardView.isShown()) {
                this.autoHeightView.hideAutoView();
                this.emotionImageView.setImageResource(R.drawable.emotion_btn);
                return true;
            }
            return super.dispatchKeyEvent(keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$18 */
    public class AnonymousClass18 extends BroadcastReceiver {
        public AnonymousClass18() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            PostItem postItem;
            if (intent.getAction().equals(Constants.BROADCAST_MSG_USER_FOLLOW_CHANGED)) {
                if (intent.getStringExtra(Constants.EXTRA_USER_ID).equals(PostDetailActivity.this.mTopListItem.getAuthor().getId())) {
                    PostDetailActivity.this.mTopListItem.setFollowed(intent.getIntExtra(Constants.EXTRA_FOLLOWED, 0));
                    PostDetailActivity.this.refreshHeaderViewNew();
                    return;
                }
                return;
            }
            if (!intent.getAction().equals(Constants.BROADCAST_MSG_FAVOR_POST) || (postItem = (PostItem) intent.getSerializableExtra(Constants.EXTRA_POST_DATA)) == null || PostDetailActivity.this.mTopListItem == null || !postItem.getId().equals(PostDetailActivity.this.mTopListItem.getId())) {
                return;
            }
            PostDetailActivity.this.setViewState(1);
            PostDetailActivity.this.mTopListItem = postItem;
            PostDetailActivity.this.refreshHeaderViewNew();
            if (((BaseListActivity) PostDetailActivity.this).mListAdapter != null) {
                ((BaseListActivity) PostDetailActivity.this).mListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.community.PostDetailActivity.18
            public AnonymousClass18() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                PostItem postItem;
                if (intent.getAction().equals(Constants.BROADCAST_MSG_USER_FOLLOW_CHANGED)) {
                    if (intent.getStringExtra(Constants.EXTRA_USER_ID).equals(PostDetailActivity.this.mTopListItem.getAuthor().getId())) {
                        PostDetailActivity.this.mTopListItem.setFollowed(intent.getIntExtra(Constants.EXTRA_FOLLOWED, 0));
                        PostDetailActivity.this.refreshHeaderViewNew();
                        return;
                    }
                    return;
                }
                if (!intent.getAction().equals(Constants.BROADCAST_MSG_FAVOR_POST) || (postItem = (PostItem) intent.getSerializableExtra(Constants.EXTRA_POST_DATA)) == null || PostDetailActivity.this.mTopListItem == null || !postItem.getId().equals(PostDetailActivity.this.mTopListItem.getId())) {
                    return;
                }
                PostDetailActivity.this.setViewState(1);
                PostDetailActivity.this.mTopListItem = postItem;
                PostDetailActivity.this.refreshHeaderViewNew();
                if (((BaseListActivity) PostDetailActivity.this).mListAdapter != null) {
                    ((BaseListActivity) PostDetailActivity.this).mListAdapter.notifyDataSetChanged();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_USER_FOLLOW_CHANGED);
        intentFilter.addAction(Constants.BROADCAST_MSG_FAVOR_POST);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
        this.mScreenReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.community.PostDetailActivity.19
            public AnonymousClass19() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                    LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_NOTIFYDATASETCHANGED));
                }
            }
        };
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.SCREEN_OFF");
        this.mContext.registerReceiver(this.mScreenReceiver, intentFilter2);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostDetailActivity$19 */
    public class AnonymousClass19 extends BroadcastReceiver {
        public AnonymousClass19() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                LocalBroadcastManager.getInstance(PostDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_NOTIFYDATASETCHANGED));
            }
        }
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mScreenReceiver);
    }
}

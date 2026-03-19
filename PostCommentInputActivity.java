package com.petkit.android.activities.community;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.emotion.EmotionEditText;
import com.petkit.android.activities.chat.emotion.EmotionKeyboardView;
import com.petkit.android.activities.chat.emotion.view.AutoHeightLayout;
import com.petkit.android.activities.common.presenter.IPostCommentPresenter;
import com.petkit.android.activities.common.presenter.PostCommentPresenterImpl;
import com.petkit.android.activities.common.view.IPostCommentView;
import com.petkit.android.activities.community.widget.AndroidBug5497Workaround;
import com.petkit.android.activities.petkitBleDevice.AdWebViewActivity;
import com.petkit.android.model.Emotion;
import com.petkit.android.model.PostItem;
import com.petkit.android.model.User;
import com.petkit.android.utils.AtInputFilter;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class PostCommentInputActivity extends BaseActivity implements EmotionKeyboardView.EmotionKeyboardListener, IPostCommentView, View.OnLayoutChangeListener {
    private ImageView emotionImageView;
    private ImageView feedBackImageView;
    private EmotionEditText inputEditText;
    private AtInputFilter mInputFilter;
    private IPostCommentPresenter mPostCommentPresenter;
    private PostItem mPostItem;
    private ImageView sendImageView;

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.isNeedPhotoController = true;
        if (bundle == null) {
            this.mPostItem = (PostItem) getIntent().getSerializableExtra(Constants.EXTRA_POST_DATA);
        } else {
            this.mPostItem = (PostItem) bundle.getSerializable(Constants.EXTRA_POST_DATA);
        }
        super.onCreate(bundle);
        this.autoHeightView.enableEmotionKeyborad();
        setContentView(R.layout.activity_post_comment_input);
        AndroidBug5497Workaround.assistActivity(this, this.autoHeightView);
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.community.PostCommentInputActivity.1
            public AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                PostCommentInputActivity postCommentInputActivity = PostCommentInputActivity.this;
                postCommentInputActivity.showSoftInput(postCommentInputActivity.inputEditText);
            }
        }, 100L);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostCommentInputActivity$1 */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            PostCommentInputActivity postCommentInputActivity = PostCommentInputActivity.this;
            postCommentInputActivity.showSoftInput(postCommentInputActivity.inputEditText);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_POST_DATA, this.mPostItem);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        findViewById(R.id.main_view).addOnLayoutChangeListener(this);
        this.inputEditText.refreshEmotionInputEventListener();
        this.mKeyboardView.setEmotionKeyboardListener(this);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.zoom_out);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.image_select_bn) {
            this.autoHeightView.hideAutoView();
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
        if (id == R.id.comment_send) {
            String strTrim = this.inputEditText.getEditableText().toString().trim();
            PetkitLog.d("detail: " + strTrim);
            if (isEmpty(strTrim)) {
                return;
            }
            AtInputFilter atInputFilter = this.mInputFilter;
            if (atInputFilter == null) {
                this.mPostCommentPresenter.sendComment(strTrim, this.imageFilePath);
            } else {
                this.mPostCommentPresenter.sendComment(atInputFilter.getContentStr(), this.imageFilePath);
            }
            this.autoHeightView.hideAutoView();
            return;
        }
        if (id == R.id.image_feedback_bn) {
            startActivity(AdWebViewActivity.newIntent(this, "", "https://support.petkit.com/support/discussions/forums/51000132354"));
            return;
        }
        if (id == R.id.menu_1) {
            this.mPopupWindow.dismiss();
            if (isEmpty(this.imageFilePath)) {
                getPhotoFromCamera();
                return;
            }
            this.imageFilePath = null;
            this.sendImageView.setImageResource(R.drawable.camera);
            PetkitLog.d("image", "删除 sendImageView R.drawable.camera");
            return;
        }
        if (id == R.id.menu_2) {
            this.mPopupWindow.dismiss();
            getPhotoFromAlbum();
        } else if (id == R.id.content) {
            finish();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
        setBackgroudTransparent(0);
        setStatusLineBackgroudColor(CommonUtils.getColorById(R.color.transparent));
        findViewById(R.id.content).setOnClickListener(this);
        PostCommentPresenterImpl postCommentPresenterImpl = new PostCommentPresenterImpl(this, this);
        this.mPostCommentPresenter = postCommentPresenterImpl;
        postCommentPresenterImpl.setPostItem(this.mPostItem);
        setBottomView(R.layout.layout_comment_input);
        findViewById(R.id.comment_send).setOnClickListener(this);
        EmotionEditText emotionEditText = (EmotionEditText) findViewById(R.id.input_comment_detail);
        this.inputEditText = emotionEditText;
        emotionEditText.setFilters(new InputFilter[]{new editInputFilter()});
        this.mInputFilter = new AtInputFilter(this, this.inputEditText);
        this.inputEditText.setHint(R.string.Hint_input_comment);
        ImageView imageView = (ImageView) findViewById(R.id.image_select_bn);
        this.sendImageView = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.image_feedback_bn);
        this.feedBackImageView = imageView2;
        imageView2.setOnClickListener(this);
        this.inputEditText.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.image_select_bn);
        this.sendImageView = imageView3;
        imageView3.setOnClickListener(this);
        ImageView imageView4 = (ImageView) findViewById(R.id.image_emotion_bn);
        this.emotionImageView = imageView4;
        imageView4.setOnClickListener(this);
        setKeyboardView(R.layout.layout_keyboard);
        this.mKeyboardView.setVisibility(4);
        this.autoHeightView.setAutoHeightLayoutView(this.mKeyboardView);
        this.autoHeightView.hideAutoView();
        this.autoHeightView.setKeyBoardViewListener(new AutoHeightLayout.KeyBoardViewListener() { // from class: com.petkit.android.activities.community.PostCommentInputActivity.2
            public AnonymousClass2() {
            }

            @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
            public void onSoftPop() {
                PostCommentInputActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
            }

            @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
            public void onSoftClose() {
                PostCommentInputActivity.this.emotionImageView.setImageResource(R.drawable.keyboard_btn);
            }

            @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
            public void onKeyBoardClose() {
                PostCommentInputActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.PostCommentInputActivity$2 */
    public class AnonymousClass2 implements AutoHeightLayout.KeyBoardViewListener {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
        public void onSoftPop() {
            PostCommentInputActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
        }

        @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
        public void onSoftClose() {
            PostCommentInputActivity.this.emotionImageView.setImageResource(R.drawable.keyboard_btn);
        }

        @Override // com.petkit.android.activities.chat.emotion.view.AutoHeightLayout.KeyBoardViewListener
        public void onKeyBoardClose() {
            PostCommentInputActivity.this.emotionImageView.setImageResource(R.drawable.emotion_btn);
        }
    }

    @Override // com.petkit.android.activities.chat.emotion.EmotionKeyboardView.EmotionKeyboardListener
    public void onBigEmotionInput(Emotion emotion) {
        this.mPostCommentPresenter.sendEmotionComment(emotion);
        this.autoHeightView.hideAutoView();
    }

    public class editInputFilter implements InputFilter {
        public editInputFilter() {
        }

        public /* synthetic */ editInputFilter(PostCommentInputActivity postCommentInputActivity, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (charSequence.toString().equalsIgnoreCase("@") || charSequence.toString().equalsIgnoreCase("＠")) {
                PostCommentInputActivity.this.hideSoftInput();
                ((BaseActivity) PostCommentInputActivity.this).autoHeightView.hideAutoView();
                PostCommentInputActivity postCommentInputActivity = PostCommentInputActivity.this;
                postCommentInputActivity.startActivityForResult(SelectFriendsActivity.newIntent(postCommentInputActivity, null), 14115);
            }
            return charSequence;
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 14115) {
            User user = (User) intent.getSerializableExtra(Constants.EXTRA_USER_DETAIL);
            this.mInputFilter.addFilter(user.getId(), user.getNick());
            showSoftInput(this.inputEditText);
        }
    }

    @Override // com.petkit.android.activities.common.view.IPostCommentView
    public void onCommentSuccess(PostItem postItem) {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void uploadHeadPic(String str) {
        this.imageFilePath = str;
        setImageView();
    }

    private void setImageView() {
        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.imageFilePath).imageView(this.sendImageView).errorPic(R.drawable.default_image).transformation(new GlideCircleTransform(this)).build());
    }

    private void hideInputView() {
        EmotionKeyboardView emotionKeyboardView = this.mKeyboardView;
        if (emotionKeyboardView != null && emotionKeyboardView.isShown()) {
            this.autoHeightView.hideAutoView();
            this.emotionImageView.setImageResource(R.drawable.emotion_btn);
        } else {
            hideSoftInput();
        }
    }
}

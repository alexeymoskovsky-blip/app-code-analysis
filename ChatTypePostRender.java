package com.petkit.android.activities.chat.adapter.render;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.adapter.ChatAdapter;
import com.petkit.android.model.Author;
import com.petkit.android.model.ChatMsg;
import com.petkit.android.model.Comment;
import com.petkit.android.model.PayloadContent;
import com.petkit.android.model.PostItem;
import com.petkit.android.model.User;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.EmotionView;
import com.petkit.android.widget.SpecialMarksView;
import com.petkit.android.widget.TextViewFixTouchConsume;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class ChatTypePostRender extends ChatTypeBaseRender {
    public SpecialMarksView authorNickMarks;
    public ImageView avatar;
    public ImageView chatAvatar;
    public ViewStub chatContent;
    public TextView chatTime;
    public TextViewFixTouchConsume content;
    public ImageView contentImg;
    public LinearLayout contentView;
    public View divider;
    public EmotionView emotionView;
    public ImageView favor;
    public TextView header;
    public TextViewFixTouchConsume postContent;
    public ImageView postImage;
    public TextView time;
    public ImageView videoPlayBtn;

    public ChatTypePostRender(Activity activity, ChatAdapter chatAdapter) {
        super(activity, chatAdapter);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.adapter_message_post, (ViewGroup) null);
        this.contentView = linearLayout;
        this.avatar = (ImageView) linearLayout.findViewById(R.id.author_avatar);
        this.authorNickMarks = (SpecialMarksView) this.contentView.findViewById(R.id.author_nick_marks);
        this.time = (TextView) this.contentView.findViewById(R.id.msg_time);
        this.postImage = (ImageView) this.contentView.findViewById(R.id.post_image);
        this.postContent = (TextViewFixTouchConsume) this.contentView.findViewById(R.id.post_content);
        this.contentImg = (ImageView) this.contentView.findViewById(R.id.msg_image);
        this.emotionView = (EmotionView) this.contentView.findViewById(R.id.emotion_view);
        this.videoPlayBtn = (ImageView) this.contentView.findViewById(R.id.post_video_play_btn);
        this.favor = (ImageView) this.contentView.findViewById(R.id.msg_favor);
        this.content = (TextViewFixTouchConsume) this.contentView.findViewById(R.id.msg_content);
        this.header = (TextView) this.contentView.findViewById(R.id.header);
        this.divider = this.contentView.findViewById(R.id.divider);
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public View getConvertView() {
        return this.contentView;
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitEvents() {
        this.avatar.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.chat.adapter.render.ChatTypePostRender.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ChatTypePostRender.this.entryPersonalPage((Author) view.getTag(R.id.id_image_view_tag));
            }
        });
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitDatas(int i) {
        ChatMsg item = this.chatAdapter.getItem(i);
        if (this.chatAdapter.getTimeindex() == -1 && i == 0) {
            this.header.setVisibility(8);
            this.header.setText(R.string.History);
        } else if (i == 0) {
            this.header.setVisibility(8);
            this.header.setText(R.string.Latest);
        } else if (i == this.chatAdapter.getTimeindex()) {
            this.header.setVisibility(8);
            this.header.setText(R.string.History);
        } else {
            this.header.setVisibility(8);
        }
        this.divider.setVisibility((i == this.chatAdapter.getTimeindex() - 1 || i == this.chatAdapter.getCount() - 1) ? 8 : 0);
        this.time.setText(CommonUtils.getDisplayTimeFromDate(this.activity, item.getTimestamp()));
        PayloadContent payloadContent = (PayloadContent) new Gson().fromJson(item.getPayloadContent(), PayloadContent.class);
        if (payloadContent.getPost() != null) {
            PostItem post = payloadContent.getPost();
            if (post.getImages() != null && post.getImages().size() > 0) {
                this.postImage.setVisibility(0);
                this.postContent.setVisibility(8);
                this.videoPlayBtn.setVisibility(8);
                ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(post.getImages().get(0).getUrl()).imageView(this.postImage).errorPic(R.drawable.default_image).build());
            } else if (post.getVideo() != null) {
                this.postImage.setVisibility(0);
                this.postContent.setVisibility(8);
                this.videoPlayBtn.setVisibility(0);
                ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(post.getVideo().getThumbnailUrl()).imageView(this.postImage).errorPic(R.drawable.default_image).build());
            } else {
                this.postImage.setVisibility(8);
                this.videoPlayBtn.setVisibility(8);
                this.postContent.setVisibility(0);
                if (!TextUtils.isEmpty(post.getDetail())) {
                    this.postContent.setText(post.getDetail());
                    TextViewFixTouchConsume textViewFixTouchConsume = this.postContent;
                    textViewFixTouchConsume.checkSpannableText(textViewFixTouchConsume, CommonUtils.getColorById(R.color.topic_text_color), false);
                }
            }
        }
        if (Constants.IM_PAYLOAD_TYPE_POST_FAVOR.equals(item.getPayloadType())) {
            this.content.setVisibility(8);
            this.contentImg.setVisibility(8);
            this.emotionView.setVisibility(8);
            this.favor.setVisibility(0);
            if (payloadContent.getUser() != null) {
                User user = payloadContent.getUser();
                ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(user.getAvatar()).imageView(this.avatar).errorPic(user.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideRoundTransform(CommonUtils.getAppContext(), (int) DeviceUtils.dpToPixel(CommonUtils.getAppContext(), 5.0f))).build());
                this.authorNickMarks.setUser(user);
                this.avatar.setTag(R.id.id_image_view_tag, UserInforUtils.createAuthorObjectForUser(user));
                return;
            }
            return;
        }
        if (item.getPayloadType().equals(Constants.IM_PAYLOAD_TYPE_POST_COMMENT) || item.getPayloadType().equals(Constants.IM_PAYLOAD_TYPE_AT_COMMENT) || item.getPayloadType().equals(Constants.IM_PAYLOAD_TYPE_POST_COMMENTREPLY)) {
            this.content.setVisibility(8);
            this.contentImg.setVisibility(8);
            this.emotionView.setVisibility(8);
            this.favor.setVisibility(8);
            if (payloadContent.getComment() != null) {
                Comment comment = payloadContent.getComment();
                if (!CommonUtils.isEmpty(comment.getImg())) {
                    this.contentImg.setVisibility(0);
                    ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(comment.getImg()).imageView(this.contentImg).errorPic(R.drawable.default_image).build());
                } else if (comment.getBigEmotion() != null) {
                    this.emotionView.setVisibility(0);
                    this.emotionView.setEmotion(comment.getBigEmotion());
                }
                this.content.setVisibility(0);
                if (comment.getReplyTo() != null) {
                    SpannableStringUtils.SpanText spanText = new SpannableStringUtils.SpanText(this.activity.getString(R.string.Reply), CommonUtils.getColorById(R.color.gray), 1.0f);
                    SpannableStringUtils.SpanText spanText2 = new SpannableStringUtils.SpanText(comment.getReplyTo().getNick(), CommonUtils.getColorById(R.color.blue), 1.0f);
                    this.content.setText(SpannableStringUtils.makeSpannableString(spanText, spanText2, new SpannableStringUtils.SpanText(": " + comment.getDetail(), CommonUtils.getColorById(R.color.text_content_color), 1.0f)).toString());
                    TextViewFixTouchConsume textViewFixTouchConsume2 = this.content;
                    textViewFixTouchConsume2.checkSpannableText(textViewFixTouchConsume2, CommonUtils.getColorById(R.color.topic_text_color), SpannableStringUtils.makeSpannableString(spanText, spanText2), false);
                } else if (TextUtils.isEmpty(comment.getDetail().trim())) {
                    this.content.setVisibility(8);
                } else {
                    this.content.setText(comment.getDetail());
                    TextViewFixTouchConsume textViewFixTouchConsume3 = this.content;
                    textViewFixTouchConsume3.checkSpannableText(textViewFixTouchConsume3, CommonUtils.getColorById(R.color.topic_text_color), false);
                }
                Author commentor = comment.getCommentor();
                ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(commentor.getAvatar()).imageView(this.avatar).errorPic(commentor.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideRoundTransform(CommonUtils.getAppContext(), (int) DeviceUtils.dpToPixel(CommonUtils.getAppContext(), 5.0f))).build());
                this.authorNickMarks.setAuthor(commentor);
                this.avatar.setTag(R.id.id_image_view_tag, commentor);
                return;
            }
            return;
        }
        if (item.getPayloadType().equals(Constants.IM_PAYLOAD_TYPE_AT_POST)) {
            this.postContent.setVisibility(8);
            this.contentImg.setVisibility(8);
            this.emotionView.setVisibility(8);
            this.favor.setVisibility(8);
            PostItem post2 = payloadContent.getPost();
            if (!TextUtils.isEmpty(post2.getDetail())) {
                this.content.setText(post2.getDetail());
                TextViewFixTouchConsume textViewFixTouchConsume4 = this.content;
                textViewFixTouchConsume4.checkSpannableText(textViewFixTouchConsume4, CommonUtils.getColorById(R.color.topic_text_color), false);
            }
            Author author = post2.getAuthor();
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(author.getAvatar()).imageView(this.avatar).errorPic(author.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideRoundTransform(CommonUtils.getAppContext(), (int) DeviceUtils.dpToPixel(CommonUtils.getAppContext(), 5.0f))).build());
            this.authorNickMarks.setAuthor(author);
            this.avatar.setTag(R.id.id_image_view_tag, author);
        }
    }
}

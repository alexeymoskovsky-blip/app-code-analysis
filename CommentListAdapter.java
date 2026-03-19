package com.petkit.android.activities.community.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.community.ImageDetailActivity;
import com.petkit.android.activities.personal.PersonalActivity;
import com.petkit.android.model.Comment;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.utils.sort.CommentSortUtil;
import com.petkit.android.widget.EmotionView;
import com.petkit.android.widget.SpecialMarksView;
import com.petkit.android.widget.TextViewFixTouchConsume;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CommentListAdapter extends LoadMoreBaseAdapter {
    private Comment commentFrom;

    public CommentListAdapter(Activity activity, List list, Comment comment) {
        super(activity, list);
        this.commentFrom = comment;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void addCommentItem(Comment comment) {
        this.mList.add((T) comment);
        sorList();
    }

    public void removeCommentItem(Comment comment) {
        this.mList.remove(comment);
        sorList();
    }

    private void sorList() {
        if (this.commentFrom != null) {
            if (this.mList.size() > 1) {
                List<T> list = this.mList;
                Collections.sort(list.subList(1, list.size() - 1), new CommentSortUtil());
            }
        } else {
            Collections.sort(this.mList, new CommentSortUtil());
        }
        Iterator it = this.mList.iterator();
        Comment comment = null;
        while (it.hasNext()) {
            Comment comment2 = (Comment) it.next();
            if (comment == null || !comment2.getCreatedAt().equals(comment.getCreatedAt())) {
                comment = comment2;
            } else if (this.commentFrom == null) {
                it.remove();
            }
        }
        notifyDataSetChanged();
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    public View getEmptyFootView() {
        if (this.mList.size() == 0 || !UserInforUtils.isPetPenSwitchOpen()) {
            View viewInflate = LayoutInflater.from(this.mActivity).inflate(R.layout.layout_list_empty, (ViewGroup) null);
            RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.list_empty);
            relativeLayout.setBackgroundColor(this.mActivity.getResources().getColor(R.color.home_bg_gray));
            TextView textView = (TextView) viewInflate.findViewById(R.id.list_empty_text);
            textView.setVisibility(0);
            textView.setText(R.string.Hint_empty_text_post_comment_list_new);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_comment_none, 0, 0);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.list_empty_image);
            if (UserInforUtils.isPetPenSwitchOpen()) {
                relativeLayout.setVisibility(0);
            } else {
                relativeLayout.setVisibility(8);
            }
            imageView.setVisibility(8);
            return viewInflate;
        }
        return super.getEmptyFootView();
    }

    /* JADX WARN: Multi-variable type inference failed */
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
    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    @SuppressLint({"InflateParams"})
    public View getContentView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_comment_list, (ViewGroup) null);
            viewHolder.commentTitle = (TextView) view.findViewById(R.id.current_comment_title);
            viewHolder.commentorAvatar = (ImageView) view.findViewById(R.id.commentor_avatar);
            viewHolder.commentorNickMarks = (SpecialMarksView) view.findViewById(R.id.commentor_nick_marks);
            viewHolder.commentTime = (TextView) view.findViewById(R.id.comment_time);
            viewHolder.commentDetail = (TextViewFixTouchConsume) view.findViewById(R.id.comment_detail);
            viewHolder.commentorImg = (ImageView) view.findViewById(R.id.comment_img);
            viewHolder.emotionView = (EmotionView) view.findViewById(R.id.emotion_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (UserInforUtils.isPetPenSwitchOpen()) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
        final Comment comment = (Comment) getItem(i);
        if (i == 0) {
            if (this.commentFrom != null) {
                viewHolder.commentTitle.setVisibility(0);
                viewHolder.commentTitle.setText(R.string.Post_current_comment);
            } else {
                viewHolder.commentTitle.setVisibility(0);
                viewHolder.commentTitle.setText(R.string.Post_all_comment);
            }
        } else if (i == 1 && this.commentFrom != null) {
            viewHolder.commentTitle.setVisibility(0);
            viewHolder.commentTitle.setText(R.string.Post_all_comment);
        } else {
            viewHolder.commentTitle.setVisibility(8);
        }
        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(comment.getCommentor().getAvatar()).imageView(viewHolder.commentorAvatar).errorPic(comment.getCommentor().getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideRoundTransform(CommonUtils.getAppContext(), (int) DeviceUtils.dpToPixel(CommonUtils.getAppContext(), 5.0f))).build());
        viewHolder.commentorAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.CommentListAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$getContentView$0(comment, view2);
            }
        });
        viewHolder.commentorNickMarks.setAuthor(comment.getCommentor());
        viewHolder.commentTime.setText(CommonUtils.getDisplayTimeFromDate(this.mActivity, comment.getCreatedAt()));
        viewHolder.commentDetail.setVisibility(0);
        if (comment.getReplyTo() != null) {
            String str = "<a href=\"user:" + comment.getReplyTo().getId() + "\">" + comment.getReplyTo().getNick() + "</a>";
            SpannableStringBuilder spannableStringBuilderMakeSpannableString = SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(this.mActivity.getString(R.string.Reply), CommonUtils.getColorById(R.color.gray), 1.0f), new SpannableStringUtils.SpanText(str, CommonUtils.getColorById(R.color.topic_text_color), 1.0f), new SpannableStringUtils.SpanText(": " + comment.getDetail(), CommonUtils.getColorById(R.color.text_content_color), 1.0f));
            if (!TextUtils.isEmpty(spannableStringBuilderMakeSpannableString.toString())) {
                viewHolder.commentDetail.setText(spannableStringBuilderMakeSpannableString.toString());
                TextViewFixTouchConsume textViewFixTouchConsume = viewHolder.commentDetail;
                textViewFixTouchConsume.checkSpannableText(textViewFixTouchConsume, CommonUtils.getColorById(R.color.topic_text_color), false);
            } else {
                viewHolder.commentDetail.setVisibility(8);
            }
        } else if (!TextUtils.isEmpty(comment.getDetail().trim())) {
            viewHolder.commentDetail.setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(comment.getDetail(), CommonUtils.getColorById(R.color.text_content_color), 1.0f)).toString());
            TextViewFixTouchConsume textViewFixTouchConsume2 = viewHolder.commentDetail;
            textViewFixTouchConsume2.checkSpannableText(textViewFixTouchConsume2, CommonUtils.getColorById(R.color.topic_text_color), false);
        } else {
            viewHolder.commentDetail.setVisibility(8);
        }
        if (comment.getBigEmotion() != null) {
            viewHolder.commentorImg.setVisibility(8);
            viewHolder.emotionView.setVisibility(0);
            viewHolder.emotionView.setEmotion(comment.getBigEmotion());
        } else if (!CommonUtils.isEmpty(comment.getImg())) {
            viewHolder.commentorImg.setVisibility(0);
            viewHolder.emotionView.setVisibility(8);
            ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(comment.getImg()).imageView(viewHolder.commentorImg).errorPic(R.drawable.default_image).build());
            viewHolder.commentorImg.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.CommentListAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$getContentView$1(comment, view2);
                }
            });
        } else {
            viewHolder.commentorImg.setVisibility(8);
            viewHolder.emotionView.setVisibility(8);
        }
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getContentView$0(Comment comment, View view) {
        if (comment.getCommentor() != null) {
            new HashMap().put("fromWhere", "blogDetail");
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_AUTHOR, comment.getCommentor());
            ((BaseActivity) this.mActivity).startActivityWithData(PersonalActivity.class, bundle, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getContentView$1(Comment comment, View view) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(comment.getImg());
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ImageDetailActivity.IMAGE_LIST_DATA, arrayList);
        bundle.putInt(ImageDetailActivity.IMAGE_LIST_POSITION, 0);
        ((BaseActivity) this.mActivity).startActivityWithData(ImageDetailActivity.class, bundle, false);
        this.mActivity.overridePendingTransition(R.anim.img_scale_in, R.anim.slide_none);
    }

    public class ViewHolder {
        public TextViewFixTouchConsume commentDetail;
        public TextView commentTime;
        public TextView commentTitle;
        public ImageView commentorAvatar;
        public ImageView commentorImg;
        public SpecialMarksView commentorNickMarks;
        public EmotionView emotionView;

        public ViewHolder() {
        }
    }
}

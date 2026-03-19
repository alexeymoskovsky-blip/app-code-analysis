package com.petkit.android.activities.community.adapter.render;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.AdapterTypeRender;
import com.petkit.android.activities.community.PersonalRecommendListActivity;
import com.petkit.android.activities.community.TopicsListActivity;
import com.petkit.android.activities.community.adapter.CommunityListAdapter;
import com.petkit.android.activities.personal.PersonalActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.Author;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.PostItem;
import com.petkit.android.model.UserDetail;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CommunityTypeUserRender implements AdapterTypeRender {
    public Activity activity;
    public CommunityListAdapter communityListAdapterNew;
    public LinearLayout container;
    public View contentView;
    public HorizontalScrollView mHorizontalScrollView;

    @SuppressLint({"InflateParams"})
    public CommunityTypeUserRender(Activity activity, CommunityListAdapter communityListAdapter) {
        this.activity = activity;
        this.communityListAdapterNew = communityListAdapter;
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.adapter_community_users, (ViewGroup) null);
        this.contentView = viewInflate;
        this.container = (LinearLayout) viewInflate.findViewById(R.id.recommend_user_container);
        this.contentView.findViewById(R.id.gap_line).setVisibility(8);
        this.contentView.findViewById(R.id.recommend_btn_parent).setVisibility(8);
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public View getConvertView() {
        return this.contentView;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender$1 */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CommunityTypeUserRender.this.activity.startActivity(new Intent(CommunityTypeUserRender.this.activity, (Class<?>) TopicsListActivity.class));
        }
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitEvents() {
        this.contentView.findViewById(R.id.recommend_topic_view).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender.1
            public AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CommunityTypeUserRender.this.activity.startActivity(new Intent(CommunityTypeUserRender.this.activity, (Class<?>) TopicsListActivity.class));
            }
        });
        this.contentView.findViewById(R.id.sign_in_view).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }

            public AnonymousClass2() {
            }
        });
        this.contentView.findViewById(R.id.recommend_parent_view).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender.3
            public AnonymousClass3() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(CommunityTypeUserRender.this.activity, (Class<?>) PersonalRecommendListActivity.class);
                intent.putExtra(Constants.EXTRA_TYPE, 5);
                CommunityTypeUserRender.this.activity.startActivity(intent);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
        }

        public AnonymousClass2() {
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender$3 */
    public class AnonymousClass3 implements View.OnClickListener {
        public AnonymousClass3() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Intent intent = new Intent(CommunityTypeUserRender.this.activity, (Class<?>) PersonalRecommendListActivity.class);
            intent.putExtra(Constants.EXTRA_TYPE, 5);
            CommunityTypeUserRender.this.activity.startActivity(intent);
        }
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    @SuppressLint({"InflateParams"})
    public void fitDatas(int i) {
        this.container.removeAllViews();
        for (final UserDetail userDetail : (List) this.communityListAdapterNew.getItem(i)) {
            View viewInflate = LayoutInflater.from(this.activity).inflate(R.layout.recommend_user_item, (ViewGroup) null);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.recommend_user_img);
            TextView textView = (TextView) viewInflate.findViewById(R.id.recommend_user_name);
            TextView textView2 = (TextView) viewInflate.findViewById(R.id.recommend_user_type);
            ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.recommend_add);
            ImageLoader imageLoader = ((BaseApplication) this.activity.getApplication()).getAppComponent().imageLoader();
            Activity activity = this.activity;
            GlideImageConfig.Builder builderErrorPic = GlideImageConfig.builder().url(userDetail.getUser().getAvatar()).imageView(imageView).errorPic(userDetail.getUser().getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f);
            Activity activity2 = this.activity;
            imageLoader.loadImage(activity, builderErrorPic.transformation(new GlideRoundTransform(activity2, (int) DeviceUtils.dpToPixel(activity2, 16.0f))).build());
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$fitDatas$0(userDetail, view);
                }
            });
            imageView2.setVisibility(userDetail.getFollowed() == 1 ? 8 : 0);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender.4
                final /* synthetic */ UserDetail val$detail;

                public AnonymousClass4(final UserDetail userDetail2) {
                    userDetail = userDetail2;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (userDetail.getFollowed() == 0) {
                        CommunityTypeUserRender.this.follow(userDetail, view);
                    }
                }
            });
            textView.setText(userDetail2.getUser().getNick());
            textView2.setText(userDetail2.getType());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin = (int) DeviceUtils.dpToPixel(this.activity, 15.0f);
            this.container.addView(viewInflate, layoutParams);
        }
    }

    public final /* synthetic */ void lambda$fitDatas$0(UserDetail userDetail, View view) {
        Author author = new Author();
        author.setAvatar(userDetail.getUser().getAvatar());
        author.setGender(userDetail.getUser().getGender());
        author.setId(userDetail.getUser().getId());
        author.setNick(userDetail.getUser().getNick());
        author.setCoin(userDetail.getUser().getCoin());
        author.setPoint(userDetail.getUser().getPoint());
        author.setLocality(userDetail.getUser().getLocality());
        Intent intent = new Intent(this.activity, (Class<?>) PersonalActivity.class);
        intent.putExtra(Constants.EXTRA_AUTHOR, author);
        this.activity.startActivity(intent);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender$4 */
    public class AnonymousClass4 implements View.OnClickListener {
        final /* synthetic */ UserDetail val$detail;

        public AnonymousClass4(final UserDetail userDetail2) {
            userDetail = userDetail2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (userDetail.getFollowed() == 0) {
                CommunityTypeUserRender.this.follow(userDetail, view);
            }
        }
    }

    public final void follow(UserDetail userDetail, View view) {
        HashMap map = new HashMap();
        map.put("followeeId", userDetail.getUser().getId());
        new HashMap().put("fromWhere", "list");
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_FOLLOW_FOLLOW, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.activity, true) { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender.5
            public final /* synthetic */ UserDetail val$userDetail;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(Activity activity, boolean z, UserDetail userDetail2) {
                super(activity, z);
                userDetail = userDetail2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    PetkitToast.showShortToast(CommunityTypeUserRender.this.activity, resultStringRsp.getError().getMsg());
                    return;
                }
                if (resultStringRsp.getResult() == null || !resultStringRsp.getResult().equalsIgnoreCase("success")) {
                    PetkitToast.showShortToast(CommunityTypeUserRender.this.activity, resultStringRsp.getResult());
                    return;
                }
                userDetail.setFollowed(1);
                for (int i2 = 0; i2 < CommunityTypeUserRender.this.communityListAdapterNew.getCount(); i2++) {
                    if (CommunityTypeUserRender.this.communityListAdapterNew.getItem(i2) instanceof PostItem) {
                        PostItem postItem = (PostItem) CommunityTypeUserRender.this.communityListAdapterNew.getItem(i2);
                        if (postItem.getAuthor().getId().equals(userDetail.getUser().getId())) {
                            postItem.setFollowed(1);
                        }
                    }
                }
                CommunityTypeUserRender.this.communityListAdapterNew.notifyDataSetChanged();
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setFollowerscount(chatCenter.getFollowerscount() + 1);
                SugarRecord.save(chatCenter);
                LocalBroadcastManager.getInstance(CommunityTypeUserRender.this.activity).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
                Intent intent = new Intent(Constants.BROADCAST_MSG_USER_FOLLOW_CHANGED);
                intent.putExtra(Constants.EXTRA_FOLLOWED, 1);
                intent.putExtra(Constants.EXTRA_USER_ID, userDetail.getUser().getId());
                LocalBroadcastManager.getInstance(CommunityTypeUserRender.this.activity).sendBroadcast(intent);
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeUserRender$5 */
    public class AnonymousClass5 extends AsyncHttpRespHandler {
        public final /* synthetic */ UserDetail val$userDetail;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(Activity activity, boolean z, UserDetail userDetail2) {
            super(activity, z);
            userDetail = userDetail2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
            if (resultStringRsp.getError() != null) {
                PetkitToast.showShortToast(CommunityTypeUserRender.this.activity, resultStringRsp.getError().getMsg());
                return;
            }
            if (resultStringRsp.getResult() == null || !resultStringRsp.getResult().equalsIgnoreCase("success")) {
                PetkitToast.showShortToast(CommunityTypeUserRender.this.activity, resultStringRsp.getResult());
                return;
            }
            userDetail.setFollowed(1);
            for (int i2 = 0; i2 < CommunityTypeUserRender.this.communityListAdapterNew.getCount(); i2++) {
                if (CommunityTypeUserRender.this.communityListAdapterNew.getItem(i2) instanceof PostItem) {
                    PostItem postItem = (PostItem) CommunityTypeUserRender.this.communityListAdapterNew.getItem(i2);
                    if (postItem.getAuthor().getId().equals(userDetail.getUser().getId())) {
                        postItem.setFollowed(1);
                    }
                }
            }
            CommunityTypeUserRender.this.communityListAdapterNew.notifyDataSetChanged();
            ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
            chatCenter.setFollowerscount(chatCenter.getFollowerscount() + 1);
            SugarRecord.save(chatCenter);
            LocalBroadcastManager.getInstance(CommunityTypeUserRender.this.activity).sendBroadcast(new Intent(Constants.BROADCAST_MSG_POST_INFOR_CHANGED));
            Intent intent = new Intent(Constants.BROADCAST_MSG_USER_FOLLOW_CHANGED);
            intent.putExtra(Constants.EXTRA_FOLLOWED, 1);
            intent.putExtra(Constants.EXTRA_USER_ID, userDetail.getUser().getId());
            LocalBroadcastManager.getInstance(CommunityTypeUserRender.this.activity).sendBroadcast(intent);
        }
    }
}

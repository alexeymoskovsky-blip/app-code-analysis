package com.qiyukf.uikit.common.ui.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import com.qiyukf.nimlib.sdk.uinfo.model.UserInfo;
import com.qiyukf.uikit.a;
import com.qiyukf.uikit.b;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.pop.POPManager;
import com.qiyukf.unicorn.api.pop.ShopInfo;
import com.qiyukf.unicorn.c;

/* JADX INFO: loaded from: classes6.dex */
public class HeadImageView extends ShapedImageView {
    public HeadImageView(Context context) {
        super(context);
    }

    public HeadImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HeadImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void loadBuddyAvatar(String str, String str2) {
        if (b.a() != null) {
            loadBuddyAvatar(str, (int) b.a().getResources().getDimension(R.dimen.ysf_avatar_size), str2);
        }
    }

    public void loadBuddyOriginalAvatar(String str) {
        loadBuddyAvatar(str, 0, "");
    }

    public void loadBuddyAvatar(String str, int i, String str2) {
        UserInfo userInfo;
        setImageResource(c.d().getApplicationInfo().icon);
        if (c.h().i() != null && !TextUtils.isEmpty(c.h().i().shopId) && str.equals(c.h().i().shopId)) {
            userInfo = POPManager.getShopInfo(c.h().i().shopId);
        } else {
            userInfo = com.qiyukf.nimlib.c.I().getUserInfo(str);
        }
        if (userInfo == null || !a.c(userInfo.getAvatar())) {
            setTag(null);
        } else {
            setTag(userInfo.getAvatar());
            loadUri(userInfo.getAvatar(), i, getTag());
        }
    }

    public void loadShopAvatar(String str) {
        setImageResource(c.d().getApplicationInfo().icon);
        ShopInfo shopInfo = POPManager.getShopInfo(str);
        if (shopInfo == null || !a.c(shopInfo.getAvatar())) {
            setTag(null);
            return;
        }
        setTag(shopInfo.getAvatar());
        loadUri(shopInfo.getAvatar(), (int) getContext().getResources().getDimension(R.dimen.ysf_avatar_size), getTag());
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.common.ui.imageview.HeadImageView$1 */
    public class AnonymousClass1 implements ImageLoaderListener {
        final /* synthetic */ Object val$tag;

        @Override // com.qiyukf.unicorn.api.ImageLoaderListener
        public void onLoadFailed(Throwable th) {
        }

        public AnonymousClass1(Object obj) {
            obj = obj;
        }

        @Override // com.qiyukf.unicorn.api.ImageLoaderListener
        public void onLoadComplete(@NonNull Bitmap bitmap) {
            if (HeadImageView.this.getTag() == null || !HeadImageView.this.getTag().equals(obj)) {
                return;
            }
            HeadImageView.this.setImageBitmap(bitmap);
        }
    }

    private void loadUri(String str, int i, Object obj) {
        AnonymousClass1 anonymousClass1 = new ImageLoaderListener() { // from class: com.qiyukf.uikit.common.ui.imageview.HeadImageView.1
            final /* synthetic */ Object val$tag;

            @Override // com.qiyukf.unicorn.api.ImageLoaderListener
            public void onLoadFailed(Throwable th) {
            }

            public AnonymousClass1(Object obj2) {
                obj = obj2;
            }

            @Override // com.qiyukf.unicorn.api.ImageLoaderListener
            public void onLoadComplete(@NonNull Bitmap bitmap) {
                if (HeadImageView.this.getTag() == null || !HeadImageView.this.getTag().equals(obj)) {
                    return;
                }
                HeadImageView.this.setImageBitmap(bitmap);
            }
        };
        Bitmap bitmapB = a.b(str);
        if (bitmapB != null) {
            anonymousClass1.onLoadComplete(bitmapB);
        } else {
            a.a(str, i, i, anonymousClass1);
        }
    }

    public void resetImageView() {
        setImageBitmap(null);
    }
}

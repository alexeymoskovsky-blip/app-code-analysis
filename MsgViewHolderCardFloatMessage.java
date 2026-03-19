package com.qiyukf.unicorn.ui.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.msg.MessageService;
import com.qiyukf.unicorn.h.a.d.c;

/* JADX INFO: loaded from: classes6.dex */
public class MsgViewHolderCardFloatMessage extends com.qiyukf.unicorn.ui.viewholder.a.i {
    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public boolean isShowQuickEntry() {
        return true;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public boolean showAvatar() {
        return false;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_message_action_custom_layout;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.contentContainer.setVisibility(8);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        com.qiyukf.unicorn.h.a.d.d dVar = (com.qiyukf.unicorn.h.a.d.d) this.message.getAttachment();
        LinearLayout quickEntryContainer = getQuickEntryContainer();
        quickEntryContainer.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        quickEntryContainer.removeAllViews();
        int i = 0;
        if ("product".equals(dVar.a())) {
            while (i < dVar.b().size()) {
                CardFloatProductItemView cardFloatProductItemView = new CardFloatProductItemView(this.context);
                cardFloatProductItemView.a(dVar.b().get(i), dVar.c());
                quickEntryContainer.addView(cardFloatProductItemView);
                i++;
            }
            return;
        }
        if ("button".equals(dVar.a())) {
            if (isReceivedMessage()) {
                setGravity(quickEntryContainer, 3);
            } else {
                setGravity(quickEntryContainer, 5);
            }
            while (i < dVar.b().size()) {
                CardFloatButtonItemView cardFloatButtonItemView = new CardFloatButtonItemView(this, this.context);
                cardFloatButtonItemView.a(dVar.b().get(i), dVar.c());
                quickEntryContainer.addView(cardFloatButtonItemView);
                i++;
            }
        }
    }

    public class CardFloatButtonItemView extends FrameLayout {
        private TextView b;
        private final Context c;

        public CardFloatButtonItemView(MsgViewHolderCardFloatMessage msgViewHolderCardFloatMessage, @NonNull Context context) {
            this(context, (byte) 0);
        }

        private CardFloatButtonItemView(Context context, @NonNull byte b) {
            super(context, null);
            this.c = context;
            this.b = (TextView) View.inflate(getContext(), R.layout.ysf_message_card_float_button_item, this).findViewById(R.id.ysf_card_float_button_tv);
        }

        public final void a(final c.b bVar, c.C0181c c0181c) {
            this.b.setText(bVar.c());
            this.b.setPadding(c0181c.c(), c0181c.b(), c0181c.d(), c0181c.a());
            this.b.setBackgroundColor(Color.parseColor(c0181c.k()));
            this.b.setTextSize(c0181c.f());
            this.b.setTextColor(Color.parseColor(c0181c.i()));
            this.b.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.MsgViewHolderCardFloatMessage$CardFloatButtonItemView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.a(bVar, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(c.b bVar, View view) {
            if (bVar.e() == 0) {
                String strG = bVar.g();
                if (TextUtils.isEmpty(strG) || com.qiyukf.unicorn.c.f().onMessageItemClickListener == null) {
                    return;
                }
                com.qiyukf.unicorn.c.f().onMessageItemClickListener.onURLClicked(this.c, strG);
                return;
            }
            IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(((MsgViewHolderBase) MsgViewHolderCardFloatMessage.this).message.getSessionId(), ((MsgViewHolderBase) MsgViewHolderCardFloatMessage.this).message.getSessionType(), bVar.m());
            iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
            MsgViewHolderCardFloatMessage.this.getAdapter().b().b(iMMessageCreateTextMessage);
        }
    }

    public static class CardFloatProductItemView extends FrameLayout {
        private final Context a;
        private ImageView b;
        private TextView c;
        private TextView d;
        private TextView e;
        private LinearLayout f;

        public CardFloatProductItemView(@NonNull Context context) {
            this(context, null);
        }

        public CardFloatProductItemView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = context;
            View viewInflate = View.inflate(getContext(), R.layout.ysf_message_card_float_product_item, this);
            this.f = (LinearLayout) viewInflate.findViewById(R.id.ysf_card_float_product_ll);
            this.b = (ImageView) viewInflate.findViewById(R.id.ysf_card_float_product_iv);
            this.c = (TextView) viewInflate.findViewById(R.id.ysf_card_float_product_title);
            this.d = (TextView) viewInflate.findViewById(R.id.ysf_card_float_product_price);
            this.e = (TextView) viewInflate.findViewById(R.id.ysf_card_float_product_desc);
            if (com.qiyukf.unicorn.m.a.a().c()) {
                this.f.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().e()), PorterDuff.Mode.SRC_IN);
            }
        }

        public final void a(final c.b bVar, c.C0181c c0181c) {
            com.qiyukf.uikit.a.a(bVar.f(), this.b);
            this.c.setText(bVar.c());
            this.c.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            this.c.setPadding(c0181c.c(), 0, c0181c.d(), 0);
            this.d.setText(bVar.d());
            this.d.setPadding(c0181c.c(), 0, 0, c0181c.a());
            this.e.setText(bVar.h());
            this.e.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            this.e.setPadding(0, 0, c0181c.d(), c0181c.a());
            this.f.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.MsgViewHolderCardFloatMessage$CardFloatProductItemView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.a(bVar, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(c.b bVar, View view) {
            if (bVar.e() == 0) {
                String strG = bVar.g();
                if (TextUtils.isEmpty(strG) || com.qiyukf.unicorn.c.f().onMessageItemClickListener == null) {
                    return;
                }
                com.qiyukf.unicorn.c.f().onMessageItemClickListener.onURLClicked(this.a, strG);
                return;
            }
            MessageService.sendProductMessage(new ProductDetail.Builder().setTitle(bVar.c()).setDesc(bVar.h()).setPicture(bVar.f()).setUrl(bVar.g()).setShow(1).setOptionId(bVar.n()).build());
        }
    }
}

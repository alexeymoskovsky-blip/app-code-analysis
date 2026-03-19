package com.qiyukf.unicorn.ui.viewholder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.session.helper.ClickMovementMethod;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.msg.MessageService;
import com.qiyukf.unicorn.api.msg.UnicornMessageBuilder;
import com.qiyukf.unicorn.api.msg.attachment.ProductAttachment;
import com.qiyukf.unicorn.h.a.d.c;
import com.qiyukf.unicorn.widget.flowlayout.FlowLayout;
import com.qiyukf.unicorn.widget.flowlayout.TagAdapter;
import com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout;
import java.util.ArrayList;

/* JADX INFO: compiled from: MsgViewHolderCardMessage.java */
/* JADX INFO: loaded from: classes6.dex */
public class b extends MsgViewHolderBase {
    private com.qiyukf.unicorn.h.a.d.c a;
    private LinearLayout b;

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_card_message_layout;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.b = (LinearLayout) findViewById(R.id.ysf_card_message_parent);
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void bindContentView() {
        int color;
        this.a = (com.qiyukf.unicorn.h.a.d.c) this.message.getAttachment();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.b.getLayoutParams();
        final int dimension = (int) this.context.getResources().getDimension(R.dimen.ysf_bubble_max_width);
        layoutParams.width = dimension;
        this.b.setLayoutParams(layoutParams);
        com.qiyukf.unicorn.h.a.d.c cVar = this.a;
        if (cVar == null || cVar.i() == null || this.a.i().isEmpty()) {
            return;
        }
        this.b.removeAllViews();
        boolean z = false;
        int i = 0;
        while (i < this.a.i().size()) {
            final c.a aVar = this.a.i().get(i);
            if ("title".equals(aVar.g())) {
                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_title_layout, this.b, z);
                LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(R.id.ysf_card_title_parent);
                ImageView imageView = (ImageView) linearLayout.findViewById(R.id.ysf_card_title_iv);
                TextView textView = (TextView) linearLayout.findViewById(R.id.ysf_card_title_tv);
                linearLayout2.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.d(aVar, view);
                    }
                });
                textView.setText(aVar.d());
                textView.setTextSize(aVar.j().f());
                textView.setTextColor(Color.parseColor(aVar.j().i()));
                com.qiyukf.uikit.a.a(aVar.b(), imageView, aVar.j().g(), aVar.j().g());
                a(linearLayout, i);
            } else if ("rich".equals(aVar.g())) {
                LinearLayout linearLayout3 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_rich_layout, this.b, z);
                TextView textView2 = (TextView) linearLayout3.findViewById(R.id.ysf_card_rich_tv);
                Context context = this.context;
                UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
                if (uICustomization == null || (color = uICustomization.hyperLinkColorLeft) == 0) {
                    color = context.getResources().getColor(R.color.ysf_text_link_color_blue);
                }
                textView2.setLinkTextColor(color);
                textView2.setOnTouchListener(ClickMovementMethod.newInstance());
                com.qiyukf.unicorn.n.f.a(textView2, aVar.d(), (int) textView2.getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width), this.message.getSessionId());
                a(linearLayout3, i);
            } else if ("subtitle".equals(aVar.g())) {
                LinearLayout linearLayout4 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_subtitle_layout, this.b, z);
                LinearLayout linearLayout5 = (LinearLayout) linearLayout4.findViewById(R.id.ysf_card_subtitle_parent);
                ImageView imageView2 = (ImageView) linearLayout4.findViewById(R.id.ysf_card_subtitle_iv);
                TextView textView3 = (TextView) linearLayout4.findViewById(R.id.ysf_card_subtitle_tv);
                TextView textView4 = (TextView) linearLayout4.findViewById(R.id.ysf_card_subtitle_sub_tv);
                linearLayout4.setBackgroundColor(Color.parseColor(aVar.j().k()));
                linearLayout5.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                linearLayout5.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.c(aVar, view);
                    }
                });
                textView3.setText(aVar.d());
                textView3.setTextSize(aVar.j().f());
                textView3.setTextColor(Color.parseColor(aVar.j().i()));
                textView4.setText(aVar.e());
                textView4.setTextSize(aVar.j().h());
                textView4.setTextColor(Color.parseColor(aVar.j().j()));
                com.qiyukf.uikit.a.a(aVar.b(), imageView2, aVar.j().g(), aVar.j().g());
                a(linearLayout4, i);
            } else if ("image".equals(aVar.g())) {
                LinearLayout linearLayout6 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_image_layout, this.b, z);
                LinearLayout linearLayout7 = (LinearLayout) linearLayout6.findViewById(R.id.ysf_card_image_parent);
                final ImageView imageView3 = (ImageView) linearLayout6.findViewById(R.id.ysf_card_image_iv);
                linearLayout7.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                linearLayout7.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.b(aVar, view);
                    }
                });
                com.qiyukf.uikit.a.a(aVar.b(), new ImageLoaderListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b.1
                    @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                    public final void onLoadFailed(Throwable th) {
                    }

                    @Override // com.qiyukf.unicorn.api.ImageLoaderListener
                    public final void onLoadComplete(@NonNull final Bitmap bitmap) {
                        com.qiyukf.unicorn.n.m.b(new Runnable() { // from class: com.qiyukf.unicorn.ui.viewholder.b.1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                int iA = (com.qiyukf.unicorn.n.p.a() - aVar.j().c()) - aVar.j().d();
                                int i2 = (int) (((height * 1.0f) * iA) / width);
                                imageView3.setImageBitmap(bitmap);
                                ViewGroup.LayoutParams layoutParams2 = imageView3.getLayoutParams();
                                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(layoutParams2);
                                layoutParams2.width = iA;
                                layoutParams2.height = i2;
                                imageView3.setLayoutParams(layoutParams3);
                            }
                        });
                    }
                });
                a(linearLayout6, i);
            } else if ("flow".equals(aVar.g())) {
                LinearLayout linearLayout8 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_linear_layout, this.b, z);
                LinearLayout linearLayout9 = (LinearLayout) linearLayout8.findViewById(R.id.ysf_card_flow_item_parent);
                ((TextView) linearLayout8.findViewById(R.id.ysf_card_flow_item_change)).setVisibility(8);
                linearLayout9.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                int i2 = 0;
                while (i2 < aVar.k().size()) {
                    c.b bVar = aVar.k().get(i2);
                    LinearLayout linearLayout10 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_flow_item_layout, linearLayout9, z);
                    ImageView imageView4 = (ImageView) linearLayout10.findViewById(R.id.ysf_card_flow_item_iv);
                    View viewFindViewById = linearLayout10.findViewById(R.id.ysf_card_flow_item_sub_view);
                    TextView textView5 = (TextView) linearLayout10.findViewById(R.id.ysf_card_flow_item_tv);
                    TextView textView6 = (TextView) linearLayout10.findViewById(R.id.ysf_card_flow_item_sub_tv);
                    textView5.setText(bVar.c());
                    textView6.setText(bVar.b());
                    textView5.setTextSize(aVar.j().f());
                    textView6.setTextSize(aVar.j().h());
                    Drawable background = viewFindViewById.getBackground();
                    int color2 = Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b());
                    PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
                    background.setColorFilter(color2, mode);
                    textView6.setTextColor(Color.parseColor(aVar.j().l()));
                    if (bVar.a() == 1) {
                        if (this.message.getDirect() == MsgDirectionEnum.Out) {
                            imageView4.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()), mode);
                            textView5.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().d()));
                        } else {
                            imageView4.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().e()), mode);
                            textView5.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().e()));
                        }
                    } else {
                        imageView4.getBackground().setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().c()), mode);
                        textView5.setTextColor(Color.parseColor(aVar.j().i()));
                    }
                    linearLayout9.addView(linearLayout10);
                    i2++;
                    z = false;
                }
                a(linearLayout8, i);
            } else if ("product".equals(aVar.g())) {
                LinearLayout linearLayout11 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_linear_layout, (ViewGroup) this.b, false);
                final LinearLayout linearLayout12 = (LinearLayout) linearLayout11.findViewById(R.id.ysf_card_flow_item_parent);
                TextView textView7 = (TextView) linearLayout11.findViewById(R.id.ysf_card_flow_item_change);
                linearLayout12.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                int size = aVar.k().size();
                final int iG = this.a.g();
                final int iCeil = (int) Math.ceil(((double) size) / ((double) iG));
                final int[] iArr = {this.a.h()};
                textView7.setVisibility(8);
                a(linearLayout12, aVar, iArr[0], iG, this.a.f());
                if (this.a.f() && iCeil > 1) {
                    textView7.setVisibility(0);
                    textView7.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
                    textView7.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().e()));
                    textView7.getCompoundDrawables()[0].setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().e()), PorterDuff.Mode.SRC_IN);
                    textView7.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int[] iArr2 = iArr;
                            int i3 = (iArr2[0] + 1) % iCeil;
                            iArr2[0] = i3;
                            b bVar2 = b.this;
                            bVar2.a(linearLayout12, aVar, i3, iG, bVar2.a.f());
                            b.this.a.a(iArr[0]);
                        }
                    });
                }
                a(linearLayout11, i);
            } else if ("order".equals(aVar.g())) {
                LinearLayout linearLayout13 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_order_layout, (ViewGroup) this.b, false);
                LinearLayout linearLayout14 = (LinearLayout) linearLayout13.findViewById(R.id.ysf_card_message_order_parent);
                final LinearLayout linearLayout15 = (LinearLayout) linearLayout13.findViewById(R.id.ysf_card_message_order_item);
                TextView textView8 = (TextView) linearLayout13.findViewById(R.id.ysf_card_message_order_item_id);
                TextView textView9 = (TextView) linearLayout13.findViewById(R.id.ysf_card_message_order_item_time);
                View viewFindViewById2 = linearLayout13.findViewById(R.id.ysf_card_order_item_divider);
                TextView textView10 = (TextView) linearLayout13.findViewById(R.id.ysf_card_order_item_change);
                if (aVar.a() == null || TextUtils.isEmpty(aVar.a().a())) {
                    textView8.setText(this.context.getString(R.string.ysf_order_id) + aVar.i());
                } else {
                    textView8.setText(aVar.a().a() + ": " + aVar.i());
                }
                textView8.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
                textView9.setText(aVar.h());
                textView9.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
                linearLayout14.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                int size2 = aVar.k().size();
                final int iG2 = this.a.g();
                final int iCeil2 = (int) Math.ceil(((double) size2) / ((double) iG2));
                textView8.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        try {
                            ((ClipboardManager) ((com.qiyukf.uikit.common.a.f) b.this).context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", aVar.i()));
                            com.qiyukf.unicorn.n.t.a(R.string.ysf_copy_phone_success_str);
                        } catch (Exception unused) {
                            com.qiyukf.unicorn.n.t.a(R.string.ysf_copy_phone_error_str);
                        }
                    }
                });
                final int[] iArr2 = {this.a.h()};
                viewFindViewById2.setVisibility(8);
                textView10.setVisibility(8);
                a(linearLayout15, aVar, iArr2[0], iG2);
                if (this.a.f() && iCeil2 > 1) {
                    viewFindViewById2.setVisibility(0);
                    textView10.setVisibility(0);
                    if (com.qiyukf.unicorn.m.a.a().c()) {
                        viewFindViewById2.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
                        textView10.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().e()));
                        textView10.getCompoundDrawables()[0].setColorFilter(Color.parseColor(com.qiyukf.unicorn.m.a.a().e()), PorterDuff.Mode.SRC_IN);
                    }
                    textView10.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b.4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int[] iArr3 = iArr2;
                            int i3 = (iArr3[0] + 1) % iCeil2;
                            iArr3[0] = i3;
                            b bVar2 = b.this;
                            LinearLayout linearLayout16 = linearLayout15;
                            c.a aVar2 = aVar;
                            int i4 = iG2;
                            com.qiyukf.unicorn.h.a.d.c unused = bVar2.a;
                            bVar2.a(linearLayout16, aVar2, i3, i4);
                            b.this.a.a(iArr2[0]);
                        }
                    });
                }
                a(linearLayout13, i);
            } else if ("button".equals(aVar.g())) {
                LinearLayout linearLayout16 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_button_layout, (ViewGroup) this.b, false);
                LinearLayout linearLayout17 = (LinearLayout) linearLayout16.findViewById(R.id.ysf_card_message_button_parent);
                TagFlowLayout tagFlowLayout = (TagFlowLayout) linearLayout16.findViewById(R.id.ysf_card_message_button_flow_layout);
                linearLayout17.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < aVar.k().size(); i3++) {
                    arrayList.add(aVar.k().get(i3).c());
                }
                tagFlowLayout.setAdapter(new TagAdapter<String>(arrayList) { // from class: com.qiyukf.unicorn.ui.viewholder.b.5
                    @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
                    public final /* synthetic */ View getView(FlowLayout flowLayout, int i4, String str) {
                        View viewInflate = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.ysf_card_message_button_item_layout, (ViewGroup) flowLayout, false);
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(viewInflate.getLayoutParams());
                        layoutParams2.width = (((dimension - aVar.j().c()) - aVar.j().d()) - (com.qiyukf.unicorn.n.p.a(8.0f) * aVar.c())) / aVar.c();
                        layoutParams2.setMargins(0, com.qiyukf.unicorn.n.p.a(10.0f), com.qiyukf.unicorn.n.p.a(8.0f), 0);
                        viewInflate.setLayoutParams(layoutParams2);
                        TextView textView11 = (TextView) viewInflate.findViewById(R.id.ysf_card_message_button_item_tv);
                        textView11.setTextColor(Color.parseColor(aVar.j().i()));
                        textView11.setTextSize(aVar.j().f());
                        textView11.getBackground().setColorFilter(Color.parseColor(aVar.j().k()), PorterDuff.Mode.SRC_IN);
                        textView11.setText(str);
                        return viewInflate;
                    }
                });
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b$$ExternalSyntheticLambda3
                    @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
                    public final boolean onTagClick(View view, int i4, FlowLayout flowLayout) {
                        return this.f$0.a(aVar, view, i4, flowLayout);
                    }
                });
                a(linearLayout16, i);
            } else {
                if ("link".equals(aVar.g())) {
                    LinearLayout linearLayout18 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_link_layout, (ViewGroup) this.b, false);
                    LinearLayout linearLayout19 = (LinearLayout) linearLayout18.findViewById(R.id.ysf_card_link_parent);
                    TextView textView11 = (TextView) linearLayout18.findViewById(R.id.ysf_card_link_tv);
                    linearLayout19.setPadding(aVar.j().c(), aVar.j().b(), aVar.j().d(), aVar.j().a());
                    linearLayout19.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.a(aVar, view);
                        }
                    });
                    textView11.setText(aVar.d());
                    textView11.setTextColor(Color.parseColor(aVar.j().i()));
                    textView11.setTextSize(aVar.j().f());
                    a(linearLayout18, i);
                }
                i++;
                z = false;
            }
            i++;
            z = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(c.a aVar, View view) {
        a(aVar.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(c.a aVar, View view) {
        a(aVar.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(c.a aVar, View view) {
        a(aVar.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(c.a aVar, View view, int i, FlowLayout flowLayout) {
        if (aVar.k().get(i).e() == 0) {
            a(aVar.k().get(i).g());
            return true;
        }
        IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(this.message.getSessionId(), this.message.getSessionType(), aVar.k().get(i).m());
        iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
        getAdapter().b().b(iMMessageCreateTextMessage);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(c.a aVar, View view) {
        a(aVar.f());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(LinearLayout linearLayout, final c.a aVar, int i, int i2) {
        linearLayout.removeAllViews();
        int i3 = i * i2;
        int iMin = Math.min(i3 + i2, aVar.k().size());
        while (i3 < iMin) {
            final c.b bVar = aVar.k().get(i3);
            final c.d dVarA = aVar.a();
            LinearLayout linearLayout2 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_order_item_layout, (ViewGroup) linearLayout, false);
            ImageView imageView = (ImageView) linearLayout2.findViewById(R.id.ysf_card_message_order_item_iv);
            TextView textView = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_order_item_title);
            TextView textView2 = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_order_item_price);
            TextView textView3 = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_order_item_desc);
            TextView textView4 = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_order_item_num);
            TextView textView5 = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_order_item_sku);
            TextView textView6 = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_order_item_status);
            View viewFindViewById = linearLayout2.findViewById(R.id.ysf_message_include_divider);
            if (com.qiyukf.unicorn.m.a.a().c()) {
                viewFindViewById.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            }
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.a(bVar, aVar, dVarA, view);
                }
            });
            textView.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            textView2.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            textView3.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            textView4.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            textView5.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            textView.setText(bVar.c());
            textView2.setText(bVar.i());
            textView3.setText(bVar.h());
            textView4.setText(bVar.j());
            textView5.setText(bVar.k());
            textView6.setText(bVar.l());
            com.qiyukf.uikit.a.a(bVar.f(), imageView);
            linearLayout.addView(linearLayout2);
            i3++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(c.b bVar, c.a aVar, c.d dVar, View view) {
        if (bVar.e() == 0) {
            a(bVar.g());
            return;
        }
        ProductAttachment productAttachment = new ProductAttachment();
        productAttachment.setOrderID(aVar.i());
        productAttachment.setOrderTime(aVar.h());
        productAttachment.setOrderSku(bVar.k());
        productAttachment.setOrderCount(bVar.j());
        productAttachment.setOrderStatus(bVar.l());
        productAttachment.setPicture(bVar.f());
        productAttachment.setTitle(bVar.c());
        productAttachment.setDesc(bVar.h());
        productAttachment.setUrl(bVar.g());
        productAttachment.setShow(1);
        productAttachment.setPayMoney(bVar.i());
        productAttachment.setOptionId(bVar.n());
        productAttachment.setCustomLabelOrderId(dVar.a());
        productAttachment.setCustomLabelOrderTime(dVar.b());
        IMMessage iMMessageBuildCustomMessage = UnicornMessageBuilder.buildCustomMessage(com.qiyukf.unicorn.k.c.a(), productAttachment);
        iMMessageBuildCustomMessage.setStatus(MsgStatusEnum.success);
        com.qiyukf.unicorn.k.c.c(iMMessageBuildCustomMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(LinearLayout linearLayout, c.a aVar, int i, int i2, boolean z) {
        linearLayout.removeAllViews();
        int i3 = i * i2;
        int iMin = Math.min(i2 + i3, aVar.k().size());
        while (i3 < iMin) {
            final c.b bVar = aVar.k().get(i3);
            final c.d dVarA = aVar.a();
            int i4 = 0;
            LinearLayout linearLayout2 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.ysf_card_message_product_item_layout, (ViewGroup) linearLayout, false);
            ImageView imageView = (ImageView) linearLayout2.findViewById(R.id.ysf_card_message_product_item_iv);
            TextView textView = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_product_item_title);
            TextView textView2 = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_product_item_desc);
            TextView textView3 = (TextView) linearLayout2.findViewById(R.id.ysf_card_message_product_item_price);
            View viewFindViewById = linearLayout2.findViewById(R.id.ysf_message_include_divider);
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.b$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.a(bVar, dVarA, view);
                }
            });
            com.qiyukf.uikit.a.a(bVar.f(), imageView);
            textView.setText(bVar.c());
            textView2.setText(bVar.h());
            textView3.setText(bVar.d());
            textView.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            textView2.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().b()));
            if (!z && i3 == 0) {
                i4 = 8;
            }
            viewFindViewById.setVisibility(i4);
            viewFindViewById.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            linearLayout.addView(linearLayout2);
            i3++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(c.b bVar, c.d dVar, View view) {
        if (bVar.e() == 0) {
            a(bVar.g());
        } else {
            MessageService.sendProductMessage(new ProductDetail.Builder().setTitle(bVar.c()).setDesc(bVar.h()).setPicture(bVar.f()).setUrl(bVar.g()).setNote(bVar.d()).setShow(1).setOptionId(bVar.n()).setCustomLabelOrderId(dVar.a()).setCustomLabelOrderTime(dVar.b()).build());
        }
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str) || com.qiyukf.unicorn.c.f().onMessageItemClickListener == null) {
            return;
        }
        com.qiyukf.unicorn.c.f().onMessageItemClickListener.onURLClicked(this.context, str);
    }

    private void a(ViewGroup viewGroup, int i) {
        if (i != this.a.i().size() - 1) {
            View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.ysf_include_divider, viewGroup, false);
            View viewFindViewById = viewInflate.findViewById(R.id.ysf_message_include_divider);
            if (com.qiyukf.unicorn.m.a.a().c()) {
                viewFindViewById.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            }
            viewInflate.setLayoutParams(new LinearLayout.LayoutParams(-1, com.qiyukf.unicorn.n.p.a(this.a.i().get(i).j().e())));
            viewGroup.addView(viewInflate);
        }
        this.b.addView(viewGroup);
    }
}

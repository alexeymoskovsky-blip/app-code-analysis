package com.qiyukf.unicorn.ui.viewholder.a;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.nimlib.sdk.ysf.YsfService;
import com.qiyukf.uikit.session.helper.ClickMovementMethod;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.h.a.a.a.s;
import com.qiyukf.unicorn.widget.BotActionItemView;
import com.qiyukf.unicorn.widget.flowlayout.FlowLayout;
import com.qiyukf.unicorn.widget.flowlayout.TagAdapter;
import com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout;
import com.qiyukf.unicorn.widget.timepicker.TimeSelector;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: TemplateHolderRadioBtn.java */
/* JADX INFO: loaded from: classes6.dex */
public class aa extends i {
    private TextView a;
    private ClickMovementMethod b;
    private com.qiyukf.unicorn.h.a.a.a.s c;

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public int getContentResId() {
        return R.layout.ysf_msg_item_radio_btn;
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public void inflateContentView() {
        this.a = (TextView) findViewById(R.id.ysf_tv_radio_btn_title);
        ClickMovementMethod clickMovementMethodNewInstance = ClickMovementMethod.newInstance();
        this.b = clickMovementMethodNewInstance;
        this.a.setOnTouchListener(clickMovementMethodNewInstance);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.a.setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
        }
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        this.c = (com.qiyukf.unicorn.h.a.a.a.s) this.message.getAttachment();
        com.qiyukf.unicorn.n.f.a(this.a, this.c.c(), (int) this.a.getResources().getDimension(R.dimen.ysf_bubble_content_rich_image_max_width), this.message.getSessionId());
        if (!this.c.i()) {
            if (!this.c.j()) {
                List<s.a> listD = this.c.d();
                LinearLayout quickEntryContainer = getQuickEntryContainer();
                quickEntryContainer.removeAllViews();
                int i = 0;
                while (i < listD.size()) {
                    final s.a aVar = listD.get(i);
                    BotActionItemView botActionItemView = new BotActionItemView(this.context);
                    botActionItemView.setData("", aVar.a(), false);
                    int i2 = i + 1;
                    botActionItemView.setStartDelay(Long.valueOf((i2 * 100) << 1));
                    if (!this.c.e()) {
                        botActionItemView.setDoAnim(Boolean.TRUE);
                    } else {
                        botActionItemView.setDoAnim(Boolean.FALSE);
                    }
                    botActionItemView.setTextSize(this.context.getResources().getDimension(R.dimen.ysf_text_size_14));
                    botActionItemView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.aa.5
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            if (aa.a(aa.this)) {
                                return;
                            }
                            IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(((MsgViewHolderBase) aa.this).message.getSessionId(), ((MsgViewHolderBase) aa.this).message.getSessionType(), aVar.a());
                            iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
                            aa.this.getAdapter().b().b(iMMessageCreateTextMessage);
                        }
                    });
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, com.qiyukf.unicorn.n.p.a(36.0f));
                    layoutParams.leftMargin = com.qiyukf.unicorn.n.p.a(i == 0 ? 0.0f : 4.0f);
                    layoutParams.rightMargin = com.qiyukf.unicorn.n.p.a(4.0f);
                    botActionItemView.setLayoutParams(layoutParams);
                    UICustomization uICustomization = com.qiyukf.unicorn.c.f().uiCustomization;
                    if (uICustomization != null && uICustomization.inputUpBtnTextColor != 0) {
                        botActionItemView.getTextView().setTextColor(uICustomization.inputUpBtnTextColor);
                    } else if (com.qiyukf.unicorn.m.a.a().c()) {
                        botActionItemView.getTextView().setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
                    } else {
                        botActionItemView.getTextView().setTextColor(this.context.getResources().getColor(R.color.ysf_black_333333));
                    }
                    if (com.qiyukf.unicorn.m.a.a().c()) {
                        botActionItemView.getRootView().setBackground(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().e(), com.qiyukf.unicorn.m.a.a().b().s().e(), 100));
                        botActionItemView.getHighLightView().setBackground(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().e()));
                    } else if (uICustomization != null && uICustomization.inputUpBtnBack != 0) {
                        botActionItemView.getRootView().setBackgroundResource(uICustomization.inputUpBtnBack);
                    } else {
                        botActionItemView.getRootView().setBackgroundResource(R.drawable.ysf_message_quick_entry_item_bg);
                    }
                    quickEntryContainer.addView(botActionItemView);
                    i = i2;
                }
                if (this.c.e()) {
                    return;
                }
                this.c.f();
                ((com.qiyukf.unicorn.b.a) this.c.a()).a("isAlreadyShowAnimation", Boolean.TRUE);
                ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(this.message, false);
                return;
            }
            if (this.c.l()) {
                return;
            }
            final boolean zK = this.c.k();
            final String string = this.context.getString(zK ? R.string.ysf_select_date_time : R.string.ysf_select_date);
            final String str = zK ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd";
            this.ysfBtnRobotAnswerOk.setText(string);
            this.ysfFlCheckRobotAnswerTag.setVisibility(8);
            if (com.qiyukf.unicorn.m.a.a().c()) {
                this.ysfBtnRobotAnswerOk.setBackgroundDrawable(com.qiyukf.unicorn.m.b.a(com.qiyukf.unicorn.m.a.a().b().k()));
            }
            a(true);
            this.ysfBtnRobotAnswerOk.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.aa.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    if (aa.a(aa.this)) {
                        return;
                    }
                    Context context = ((com.qiyukf.uikit.common.a.f) aa.this).context;
                    TimeSelector.ResultHandler resultHandler = new TimeSelector.ResultHandler() { // from class: com.qiyukf.unicorn.ui.viewholder.a.aa.1.1
                        @Override // com.qiyukf.unicorn.widget.timepicker.TimeSelector.ResultHandler
                        public final void handle(String str2, Date date) {
                            aa.a(aa.this, str2);
                        }
                    };
                    boolean z = zK;
                    TimeSelector timeSelector = new TimeSelector(context, resultHandler, TimeSelector.START_TIME, TimeSelector.END_TIME, true, true, true, z, z, str, string);
                    timeSelector.setCurrentData(new Date());
                    timeSelector.show();
                }
            });
            return;
        }
        if (this.c.l()) {
            return;
        }
        TagAdapter<s.a> tagAdapter = new TagAdapter<s.a>(this.c.d()) { // from class: com.qiyukf.unicorn.ui.viewholder.a.aa.2
            @Override // com.qiyukf.unicorn.widget.flowlayout.TagAdapter
            public final /* synthetic */ View getView(FlowLayout flowLayout, int i3, s.a aVar2) {
                Drawable drawable;
                View viewInflate = LayoutInflater.from(flowLayout.getContext()).inflate(R.layout.ysf_item_tag, (ViewGroup) flowLayout, false);
                TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tag_text);
                textView.setText(aVar2.a());
                textView.setSelected(aa.this.c.n().contains(Integer.valueOf(i3)));
                if (com.qiyukf.unicorn.m.a.a().c()) {
                    textView.setTextColor(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().b().k(), ((com.qiyukf.uikit.common.a.f) aa.this).context.getResources().getColor(R.color.ysf_grey_999999)));
                    String strK = com.qiyukf.unicorn.m.a.a().b().k();
                    if (TextUtils.isEmpty(strK)) {
                        drawable = null;
                    } else {
                        StateListDrawable stateListDrawable = new StateListDrawable();
                        GradientDrawable gradientDrawable = new GradientDrawable();
                        gradientDrawable.setColor(Color.parseColor("#ffffff"));
                        gradientDrawable.setShape(0);
                        gradientDrawable.setCornerRadius(com.qiyukf.unicorn.n.p.a(30.0f));
                        gradientDrawable.setStroke(com.qiyukf.unicorn.n.p.a(0.5f), Color.parseColor(strK));
                        GradientDrawable gradientDrawable2 = new GradientDrawable();
                        gradientDrawable2.setColor(Color.parseColor("#ffffff"));
                        gradientDrawable2.setShape(0);
                        gradientDrawable2.setCornerRadius(com.qiyukf.unicorn.n.p.a(30.0f));
                        gradientDrawable2.setStroke(com.qiyukf.unicorn.n.p.a(0.5f), Color.parseColor("#999999"));
                        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, gradientDrawable);
                        stateListDrawable.addState(new int[0], gradientDrawable2);
                        drawable = stateListDrawable;
                    }
                    textView.setBackgroundDrawable(drawable);
                }
                return viewInflate;
            }
        };
        TagFlowLayout.OnTagClickListener onTagClickListener = new TagFlowLayout.OnTagClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.aa.3
            @Override // com.qiyukf.unicorn.widget.flowlayout.TagFlowLayout.OnTagClickListener
            public final boolean onTagClick(View view, int i3, FlowLayout flowLayout) {
                TextView textView = (TextView) view.findViewById(R.id.ysf_tag_text);
                if (!textView.isSelected()) {
                    textView.setSelected(true);
                    aa.this.c.n().add(Integer.valueOf(i3));
                } else {
                    textView.setSelected(false);
                    aa.this.c.n().remove(Integer.valueOf(i3));
                }
                aa.this.a(!r2.c.n().isEmpty());
                return true;
            }
        };
        this.ysfBtnRobotAnswerOk.setText(this.context.getString(R.string.ysf_ok_check));
        this.ysfFlCheckRobotAnswerTag.setAdapter(tagAdapter);
        this.ysfFlCheckRobotAnswerTag.setOnTagClickListener(onTagClickListener);
        this.ysfFlCheckRobotAnswerTag.setVisibility(0);
        if (com.qiyukf.unicorn.m.a.a().c()) {
            this.ysfBtnRobotAnswerOk.setBackgroundDrawable(com.qiyukf.unicorn.m.b.b(com.qiyukf.unicorn.m.a.a().b().k()));
        }
        a(!this.c.n().isEmpty());
        this.ysfBtnRobotAnswerOk.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.aa.4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (aa.a(aa.this)) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                Iterator<Integer> it = aa.this.c.n().iterator();
                while (it.hasNext()) {
                    sb.append(aa.this.c.d().get(it.next().intValue()).a());
                    sb.append("、");
                }
                aa.a(aa.this, sb.substring(0, sb.length() - 1));
            }
        });
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public boolean isShowRobotAnswerCheckContainer() {
        com.qiyukf.unicorn.h.a.a.a.s sVar = (com.qiyukf.unicorn.h.a.a.a.s) this.message.getAttachment();
        this.c = sVar;
        if (sVar.l()) {
            return false;
        }
        return this.c.i() || this.c.j();
    }

    @Override // com.qiyukf.uikit.session.viewholder.MsgViewHolderBase
    public boolean isShowQuickEntry() {
        com.qiyukf.unicorn.h.a.a.a.s sVar = (com.qiyukf.unicorn.h.a.a.a.s) this.message.getAttachment();
        this.c = sVar;
        return (sVar.g() || this.c.i() || this.c.j()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        Button button = this.ysfBtnRobotAnswerOk;
        if (button != null) {
            button.setEnabled(z);
        }
    }

    public static /* synthetic */ boolean a(aa aaVar) {
        if (aaVar.g()) {
            return false;
        }
        com.qiyukf.unicorn.n.t.a(R.string.ysf_robot_msg_invalid);
        return true;
    }

    public static /* synthetic */ void a(aa aaVar, String str) {
        ((com.qiyukf.unicorn.h.a.a.a.s) aaVar.message.getAttachment()).m();
        aaVar.c.m();
        ((com.qiyukf.unicorn.b.a) aaVar.c.a()).a("CHECK_BOX_IS_DONE", Boolean.TRUE);
        ((YsfService) NIMClient.getService(YsfService.class)).updateIMMessageStatus(aaVar.message, true);
        IMMessage iMMessageCreateTextMessage = MessageBuilder.createTextMessage(aaVar.message.getSessionId(), aaVar.message.getSessionType(), str);
        iMMessageCreateTextMessage.setStatus(MsgStatusEnum.success);
        aaVar.getAdapter().b().b(iMMessageCreateTextMessage);
    }
}

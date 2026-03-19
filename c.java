package com.qiyukf.unicorn.ui.viewholder.a;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.uikit.session.module.a.b;
import com.qiyukf.uikit.session.viewholder.MsgViewHolderBase;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.OnBotEventListener;
import com.qiyukf.unicorn.api.helper.UnicornWorkSheetHelper;
import com.qiyukf.unicorn.h.a.d.ag;
import com.qiyukf.unicorn.widget.BotVerticalActionItemView;
import com.qiyukf.unicorn.widget.tabLayout.PagerTabLayout;
import com.qiyukf.unicorn.widget.tabLayout.RobotQuickPagerTabLayout;
import com.qiyukf.unicorn.widget.tabLayout.ViewPagerTab;
import java.util.List;

/* JADX INFO: compiled from: MsgViewHolderRobotQuickEnter.java */
/* JADX INFO: loaded from: classes6.dex */
public class c extends i {
    private LinearLayout a;

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
        this.a = (LinearLayout) findViewById(R.id.ysf_ll_message_item_quick_container_tab);
    }

    @Override // com.qiyukf.unicorn.ui.viewholder.a.i
    public final void a() {
        final ag agVar = (ag) this.message.getAttachment();
        LinearLayout quickEntryContainer = getQuickEntryContainer();
        quickEntryContainer.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        quickEntryContainer.removeAllViews();
        this.a.setVisibility(8);
        quickEntryContainer.setVisibility(8);
        if (agVar.a().size() > 0) {
            quickEntryContainer.setVisibility(0);
            a(quickEntryContainer, agVar.a(), false);
            return;
        }
        if (agVar.b().size() == 1) {
            quickEntryContainer.setVisibility(0);
            a(quickEntryContainer, agVar.b().get(0).b(), false);
            return;
        }
        if (agVar.b().size() > 1) {
            this.a.setVisibility(0);
            this.a.removeAllViews();
            LinearLayout linearLayout = (LinearLayout) View.inflate(this.context, R.layout.ysf_message_robot_quick_group, this.a);
            linearLayout.findViewById(R.id.ysf_robot_quick_group_ll);
            final LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(R.id.ysf_robot_quick_group_list);
            RobotQuickPagerTabLayout robotQuickPagerTabLayout = (RobotQuickPagerTabLayout) linearLayout.findViewById(R.id.ysf_robot_quick_group_tab_layout);
            final List<ag.b> listB = agVar.b();
            ViewPagerTab[] viewPagerTabArr = new ViewPagerTab[listB.size()];
            for (int i = 0; i < listB.size(); i++) {
                viewPagerTabArr[i] = new ViewPagerTab(listB.get(i).a(), 1);
            }
            robotQuickPagerTabLayout.setOnTabClickListener(new PagerTabLayout.OnTabClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.c.1
                @Override // com.qiyukf.unicorn.widget.tabLayout.PagerTabLayout.OnTabClickListener
                public final void onCurrentTabClicked(int i2) {
                    linearLayout2.removeAllViews();
                    agVar.a(i2);
                    c.this.a(linearLayout2, ((ag.b) listB.get(agVar.c())).b(), true);
                }
            });
            robotQuickPagerTabLayout.setTabs(viewPagerTabArr, agVar.c());
            linearLayout2.removeAllViews();
            a(linearLayout2, listB.get(agVar.c()).b(), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(LinearLayout linearLayout, List<ag.a> list, boolean z) {
        for (int i = 0; i < list.size(); i++) {
            final ag.a aVar = list.get(i);
            BotVerticalActionItemView botVerticalActionItemView = new BotVerticalActionItemView(this.context, z);
            if (aVar.d() == 0) {
                botVerticalActionItemView.setImage(aVar.e());
            } else if (aVar.d() == 1) {
                com.qiyukf.uikit.a.a(aVar.e(), botVerticalActionItemView.getImageView());
            }
            botVerticalActionItemView.getTextView().setText(aVar.b());
            botVerticalActionItemView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.viewholder.a.c.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    long j;
                    int iA = aVar.a();
                    if (iA != 1) {
                        if (iA == 2) {
                            OnBotEventListener onBotEventListener = com.qiyukf.unicorn.c.f().onBotEventListener;
                            if (onBotEventListener != null) {
                                onBotEventListener.onUrlClick(((com.qiyukf.uikit.common.a.f) c.this).context, aVar.c());
                            }
                        } else if (iA == 3) {
                            UnicornWorkSheetHelper.openUserWorkSheetActivity(((com.qiyukf.uikit.common.a.f) c.this).context, aVar.f(), aVar.g(), ((MsgViewHolderBase) c.this).message.getSessionId());
                        } else if (iA == 4) {
                            try {
                                j = Long.parseLong(aVar.c());
                            } catch (Exception e) {
                                AbsUnicornLog.e("MsgViewHolderRobotQuick", "parse content is error url= " + aVar.c(), e);
                                j = 0L;
                            }
                            if (j == 0) {
                                return;
                            } else {
                                c.this.a(j);
                            }
                        }
                    } else if (com.qiyukf.unicorn.k.c.a(true)) {
                        com.qiyukf.unicorn.k.c.c(MessageBuilder.createTextMessage(((MsgViewHolderBase) c.this).message.getSessionId(), ((MsgViewHolderBase) c.this).message.getSessionType(), aVar.c()));
                    }
                    String sessionId = ((MsgViewHolderBase) c.this).message.getSessionId();
                    ((MsgViewHolderBase) c.this).message.getSessionId();
                    com.qiyukf.unicorn.c.a.a(sessionId, 2, aVar.h(), 0L, aVar.b());
                }
            });
            linearLayout.addView(botVerticalActionItemView);
        }
    }

    public final void a(long j) {
        getAdapter().b().b();
        b.InterfaceC0169b interfaceC0169bB = getAdapter().b();
        this.message.getSessionId();
        interfaceC0169bB.a(j, new RequestCallback<String>() { // from class: com.qiyukf.unicorn.ui.viewholder.a.c.3
            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onException(Throwable th) {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final void onFailed(int i) {
            }

            @Override // com.qiyukf.nimlib.sdk.RequestCallback
            public final /* bridge */ /* synthetic */ void onSuccess(String str) {
            }
        });
    }
}

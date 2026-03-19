package com.qiyukf.uikit.session.module.input;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.j;
import com.qiyukf.nimlib.sdk.RequestCallback;
import com.qiyukf.nimlib.sdk.RequestCallbackWrapper;
import com.qiyukf.nimlib.sdk.msg.MessageBuilder;
import com.qiyukf.uikit.session.helper.QueryProductHelper;
import com.qiyukf.uikit.session.helper.WorkSheetHelper;
import com.qiyukf.uikit.session.module.a;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.OnBotEventListener;
import com.qiyukf.unicorn.api.OnMessageItemClickListener;
import com.qiyukf.unicorn.api.QuickEntry;
import com.qiyukf.unicorn.api.QuickEntryListener;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.event.EventService;
import com.qiyukf.unicorn.api.helper.UnicornWorkSheetHelper;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.d;
import com.qiyukf.unicorn.f.i;
import com.qiyukf.unicorn.f.u;
import com.qiyukf.unicorn.f.z;
import com.qiyukf.unicorn.h.a.e.e;
import com.qiyukf.unicorn.h.a.e.f;
import com.qiyukf.unicorn.h.a.e.g;
import com.qiyukf.unicorn.h.a.e.h;
import com.qiyukf.unicorn.m.b;
import com.qiyukf.unicorn.n.p;
import com.qiyukf.unicorn.n.t;
import com.qiyukf.unicorn.widget.BotActionItemView;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes6.dex */
public class QuickEntryHelper {
    private static final String TAG = "QuickEntryHelper";
    private a container;
    private LinearLayout messageActivityBottomLayout;
    private long onClickTime = 0;
    private LinearLayout quickEntryContainer;
    private WorkSheetHelper workSheetHelper;

    public QuickEntryHelper(a aVar, LinearLayout linearLayout) {
        this.container = aVar;
        this.messageActivityBottomLayout = linearLayout;
    }

    public void setQuickEntryList(List<? extends i> list) {
        if (list == null || list.isEmpty()) {
            LinearLayout linearLayout = this.quickEntryContainer;
            if (linearLayout == null || linearLayout.getVisibility() == 8) {
                return;
            }
            this.quickEntryContainer.setVisibility(8);
            return;
        }
        if (this.quickEntryContainer == null) {
            addQuickEntryLayout();
        }
        this.quickEntryContainer.setVisibility(0);
        setQuickEntryItem(list);
    }

    private void addQuickEntryLayout() {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this.container.a).inflate(R.layout.ysf_message_quick_entry_layout, (ViewGroup) this.messageActivityBottomLayout, false);
        this.quickEntryContainer = linearLayout;
        this.messageActivityBottomLayout.addView(linearLayout, 0);
    }

    private void setQuickEntryItem(final List<? extends i> list) {
        LinearLayout linearLayout = (LinearLayout) this.quickEntryContainer.findViewById(R.id.ysf_message_quick_entry_container);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) this.quickEntryContainer.findViewById(R.id.ysf_hs_quick_scroller);
        View viewFindViewById = this.quickEntryContainer.findViewById(R.id.ysf_message_include_divider);
        int i = 0;
        if (horizontalScrollView != null) {
            horizontalScrollView.setScrollX(0);
        }
        linearLayout.removeAllViews();
        while (i < list.size()) {
            final i iVar = list.get(i);
            final BotActionItemView botActionItemView = new BotActionItemView(this.container.a);
            UICustomization uICustomization = c.f().uiCustomization;
            if (uICustomization != null && uICustomization.inputUpBtnTextColor != 0) {
                botActionItemView.getTextView().setTextColor(uICustomization.inputUpBtnTextColor);
            } else if (com.qiyukf.unicorn.m.a.a().c()) {
                botActionItemView.getTextView().setTextColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().f()));
            } else {
                botActionItemView.getTextView().setTextColor(this.container.a.getResources().getColor(R.color.ysf_black_333333));
            }
            if (com.qiyukf.unicorn.m.a.a().c()) {
                botActionItemView.getRootView().setBackground(b.a(com.qiyukf.unicorn.m.a.a().e(), "#00000000", 100));
                botActionItemView.getHighLightView().setBackground(b.b(com.qiyukf.unicorn.m.a.a().e()));
                viewFindViewById.setBackgroundColor(Color.parseColor(com.qiyukf.unicorn.m.a.a().b().s().a()));
            } else if (uICustomization != null && uICustomization.inputUpBtnBack != 0) {
                botActionItemView.getRootView().setBackgroundResource(uICustomization.inputUpBtnBack);
            } else {
                botActionItemView.getRootView().setBackgroundResource(R.drawable.ysf_message_quick_entry_item_bg);
            }
            botActionItemView.setData(iVar.getIconUrl(), iVar.getName(), iVar.isHighLight());
            int i2 = i + 1;
            botActionItemView.setStartDelay(Long.valueOf((i2 * 100) << 1));
            botActionItemView.setDoAnim(Boolean.TRUE);
            botActionItemView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.uikit.session.module.input.QuickEntryHelper.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    long j;
                    long j2;
                    if (System.currentTimeMillis() - QuickEntryHelper.this.onClickTime < 1000) {
                        return;
                    }
                    i iVar2 = iVar;
                    if (iVar2 instanceof com.qiyukf.unicorn.h.a.e.a) {
                        com.qiyukf.unicorn.h.a.e.a aVar = (com.qiyukf.unicorn.h.a.e.a) iVar2;
                        if (aVar.isHighLight()) {
                            botActionItemView.getHighLightView().setVisibility(8);
                            aVar.f();
                            c.h().a(QuickEntryHelper.this.container.c, aVar.a());
                        }
                        if (aVar.b() == 1) {
                            if (QuickEntryHelper.this.container.e.isAllowSendMessage(true)) {
                                QuickEntryHelper.this.container.e.sendMessage(MessageBuilder.createTextMessage(QuickEntryHelper.this.container.c, QuickEntryHelper.this.container.d, aVar.e()), false);
                            }
                            z zVar = c.h().f().get(QuickEntryHelper.this.container.c);
                            if (zVar != null) {
                                long j3 = zVar.a;
                                jAbs = Math.abs(zVar.b);
                                j2 = j3;
                            } else {
                                j2 = 0;
                            }
                            JSONObject jSONObject = new JSONObject();
                            j.a(jSONObject, "robotId", jAbs);
                            j.a(jSONObject, "sessionId", j2);
                            j.a(jSONObject, "clickItem", aVar.getName());
                            JSONArray jSONArray = new JSONArray();
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                j.a(jSONArray, ((i) it.next()).getName());
                            }
                            j.a(jSONObject, "displayItems", jSONArray.toString());
                            String str = QuickEntryHelper.this.container.c;
                            try {
                                com.qiyukf.unicorn.h.a.f.a aVar2 = new com.qiyukf.unicorn.h.a.f.a();
                                aVar2.a("ai_bot_direct_button_click");
                                aVar2.a(jSONObject);
                                aVar2.a(Long.valueOf(c.h().d(str)));
                                com.qiyukf.unicorn.k.c.a(aVar2, str);
                            } catch (Exception e) {
                                AbsUnicornLog.e("BuriedPointUtil", "埋点失败，失败原因", e);
                            }
                        } else if (aVar.b() == 2) {
                            OnBotEventListener onBotEventListener = c.f().onBotEventListener;
                            if (onBotEventListener != null) {
                                onBotEventListener.onUrlClick(QuickEntryHelper.this.container.a, aVar.e());
                            }
                        } else if (aVar.b() == 3) {
                            UnicornWorkSheetHelper.openUserWorkSheetActivity(QuickEntryHelper.this.container.a, aVar.g(), aVar.h(), QuickEntryHelper.this.container.c);
                        } else if (aVar.b() == 4) {
                            try {
                                j = Long.parseLong(aVar.e());
                            } catch (Exception e2) {
                                AbsUnicornLog.i(QuickEntryHelper.TAG, "parse template is error url= " + aVar.e(), e2);
                                j = 0L;
                            }
                            if (j == 0) {
                                return;
                            }
                            QuickEntryHelper quickEntryHelper = QuickEntryHelper.this;
                            quickEntryHelper.workSheetHelper = new WorkSheetHelper(quickEntryHelper.container.b);
                            QuickEntryHelper.this.workSheetHelper.openWorkSheetDialog(j, QuickEntryHelper.this.container.c, 20, 19, new RequestCallback<String>() { // from class: com.qiyukf.uikit.session.module.input.QuickEntryHelper.1.1
                                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                                public void onException(Throwable th) {
                                }

                                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                                public void onFailed(int i3) {
                                }

                                @Override // com.qiyukf.nimlib.sdk.RequestCallback
                                public void onSuccess(String str2) {
                                    QuickEntryHelper.this.workSheetHelper = null;
                                }
                            });
                        } else if (aVar.b() == -1) {
                            u uVar = new u(QuickEntryHelper.this.container.c);
                            uVar.a(true);
                            uVar.a((d) null);
                            uVar.a(5);
                            uVar.c(30);
                            c.h().a(uVar);
                        }
                        String str2 = QuickEntryHelper.this.container.c;
                        a unused = QuickEntryHelper.this.container;
                        com.qiyukf.unicorn.c.a.a(str2, 1, aVar.i(), aVar.a(), aVar.d());
                    } else if (iVar2 instanceof QuickEntry) {
                        QuickEntry quickEntry = (QuickEntry) iVar2;
                        QuickEntryListener quickEntryListener = c.f().quickEntryListener;
                        if (quickEntryListener != null) {
                            quickEntryListener.onClick(QuickEntryHelper.this.container.a, QuickEntryHelper.this.container.c, quickEntry);
                        }
                    } else if (iVar2 instanceof com.qiyukf.unicorn.h.a.e.d) {
                        EventService.openEvaluation(QuickEntryHelper.this.container.a, QuickEntryHelper.this.container.c, new RequestCallbackWrapper() { // from class: com.qiyukf.uikit.session.module.input.QuickEntryHelper.1.2
                            @Override // com.qiyukf.nimlib.sdk.RequestCallbackWrapper
                            public void onResult(int i3, Object obj, Throwable th) {
                                t.a(R.string.ysf_evaluation_success);
                            }
                        });
                    } else if (iVar2 instanceof f) {
                        f fVar = (f) iVar2;
                        if (TextUtils.isEmpty(fVar.a())) {
                            return;
                        }
                        OnMessageItemClickListener onMessageItemClickListener = c.f().onMessageItemClickListener;
                        if (onMessageItemClickListener != null) {
                            onMessageItemClickListener.onURLClicked(QuickEntryHelper.this.container.a, fVar.a());
                        }
                    } else if (iVar2 instanceof com.qiyukf.unicorn.h.a.e.b) {
                        if (!EventService.closeSession(QuickEntryHelper.this.container.c, QuickEntryHelper.this.container.a.getString(R.string.ysf_already_quit_session))) {
                            t.a(R.string.ysf_already_quit_advisory);
                        }
                    } else if (iVar2 instanceof com.qiyukf.unicorn.h.a.e.c) {
                        com.qiyukf.unicorn.h.a.e.c cVar = (com.qiyukf.unicorn.h.a.e.c) iVar2;
                        if (TextUtils.isEmpty(cVar.a())) {
                            return;
                        }
                        OnMessageItemClickListener onMessageItemClickListener2 = c.f().onMessageItemClickListener;
                        if (onMessageItemClickListener2 != null) {
                            onMessageItemClickListener2.onURLClicked(QuickEntryHelper.this.container.a, cVar.a());
                        }
                    } else if (iVar2 instanceof h) {
                        QuickEntryHelper quickEntryHelper2 = QuickEntryHelper.this;
                        quickEntryHelper2.workSheetHelper = new WorkSheetHelper(quickEntryHelper2.container.b);
                        QuickEntryHelper.this.workSheetHelper.openWorkSheetDialog(((h) iVar2).a(), QuickEntryHelper.this.container.c, 20, 19, new RequestCallback<String>() { // from class: com.qiyukf.uikit.session.module.input.QuickEntryHelper.1.3
                            @Override // com.qiyukf.nimlib.sdk.RequestCallback
                            public void onException(Throwable th) {
                            }

                            @Override // com.qiyukf.nimlib.sdk.RequestCallback
                            public void onFailed(int i3) {
                            }

                            @Override // com.qiyukf.nimlib.sdk.RequestCallback
                            public void onSuccess(String str3) {
                                QuickEntryHelper.this.workSheetHelper = null;
                            }
                        });
                    } else if (iVar2 instanceof e) {
                        e eVar = (e) iVar2;
                        UnicornWorkSheetHelper.openUserWorkSheetActivity(QuickEntryHelper.this.container.a, eVar.b(), eVar.a(), QuickEntryHelper.this.container.c);
                    } else if (iVar2 instanceof g) {
                        g gVar = (g) iVar2;
                        z zVar2 = c.h().f().get(QuickEntryHelper.this.container.c);
                        QueryProductHelper.showQueryProductDialog(QuickEntryHelper.this.container.a, gVar.a(), zVar2 != null ? zVar2.a : 0L);
                    }
                    QuickEntryHelper.this.onClickTime = System.currentTimeMillis();
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, p.a(28.0f));
            float f = 4.0f;
            layoutParams.leftMargin = p.a(i == 0 ? 8.0f : 4.0f);
            if (i == list.size() - 1) {
                f = 8.0f;
            }
            layoutParams.rightMargin = p.a(f);
            botActionItemView.setLayoutParams(layoutParams);
            linearLayout.addView(botActionItemView);
            i = i2;
        }
    }

    public void onActivityResult(int i, Intent intent) {
        WorkSheetHelper workSheetHelper;
        if (i == 17) {
            WorkSheetHelper workSheetHelper2 = this.workSheetHelper;
            if (workSheetHelper2 != null) {
                workSheetHelper2.onResultWorkSheet(17, intent);
                return;
            }
            return;
        }
        if (i != 19) {
            if (i == 20 && (workSheetHelper = this.workSheetHelper) != null) {
                workSheetHelper.onResultWorkSheet(20, intent);
                return;
            }
            return;
        }
        WorkSheetHelper workSheetHelper3 = this.workSheetHelper;
        if (workSheetHelper3 != null) {
            workSheetHelper3.onResultWorkSheet(19, intent);
        }
    }
}

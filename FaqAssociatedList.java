package com.qiyukf.uikit.session.module.input.faq;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.qiyukf.uikit.common.a.c;
import com.qiyukf.uikit.common.a.d;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.f.t;
import com.qiyukf.unicorn.h.a.d.l;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class FaqAssociatedList {
    private static final int MAX_MATCH_LENGTH = 10;
    private String currentText;
    private String exchange;
    private d<l.a> faqListAdapter;
    private ListView faqListView;
    private Handler handler;
    private String lastMatchText;
    private List<l.a> faqDataList = new ArrayList();
    private long lastQueryTime = 0;
    private Runnable delayRunnable = new Runnable() { // from class: com.qiyukf.uikit.session.module.input.faq.FaqAssociatedList.2
        @Override // java.lang.Runnable
        public void run() {
            if (TextUtils.isEmpty(FaqAssociatedList.this.currentText) || FaqAssociatedList.this.currentText.length() > 10) {
                return;
            }
            FaqAssociatedList faqAssociatedList = FaqAssociatedList.this;
            faqAssociatedList.sendMatchRequest(faqAssociatedList.currentText);
            FaqAssociatedList faqAssociatedList2 = FaqAssociatedList.this;
            faqAssociatedList2.lastMatchText = faqAssociatedList2.currentText;
            FaqAssociatedList.this.lastQueryTime = System.currentTimeMillis();
        }
    };

    public interface OnFaqItemClickListener {
        void onClick(l.a aVar);
    }

    public void init(Context context, View view, String str, final OnFaqItemClickListener onFaqItemClickListener) {
        this.exchange = str;
        this.faqListView = (ListView) view.findViewById(R.id.ysf_quick_reply_list_view);
        d<l.a> dVar = new d<>(context, this.faqDataList, new c(FaqListViewHolder.class));
        this.faqListAdapter = dVar;
        this.faqListView.setAdapter((ListAdapter) dVar);
        this.faqListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.qiyukf.uikit.session.module.input.faq.FaqAssociatedList.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                l.a aVar;
                if (onFaqItemClickListener == null || (aVar = (l.a) FaqAssociatedList.this.faqListAdapter.getItem(i)) == null) {
                    return;
                }
                onFaqItemClickListener.onClick(aVar);
                FaqAssociatedList.this.lastMatchText = aVar.a();
            }
        });
        this.handler = new Handler(context.getMainLooper());
    }

    public void onFaqListUpdate(List<l.a> list) {
        this.faqDataList.clear();
        this.faqDataList.addAll(list);
        filterItems(this.currentText);
    }

    private void filterItems(String str) {
        if (TextUtils.isEmpty(str)) {
            this.faqDataList.clear();
        } else {
            Iterator<l.a> it = this.faqDataList.iterator();
            while (it.hasNext()) {
                l.a next = it.next();
                if (!next.a().contains(str) || next.a().equals(str)) {
                    it.remove();
                }
            }
        }
        this.faqListAdapter.notifyDataSetChanged();
        if (this.faqDataList.size() == 0) {
            this.faqListView.setVisibility(8);
        } else {
            this.faqListView.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMatchRequest(String str) {
        com.qiyukf.unicorn.h.a.f.d dVar = new com.qiyukf.unicorn.h.a.f.d();
        dVar.a(com.qiyukf.unicorn.c.h().f(this.exchange));
        dVar.a(str);
        com.qiyukf.unicorn.k.c.a(dVar, this.exchange);
    }

    public void onEditTextChanged(String str) {
        t tVarN = com.qiyukf.unicorn.c.h().n(this.exchange);
        if (tVarN == null || !tVarN.a()) {
            List<l.a> list = this.faqDataList;
            if (list == null || list.size() <= 0) {
                return;
            }
            this.faqDataList.clear();
            this.faqListAdapter.notifyDataSetChanged();
            return;
        }
        if (TextUtils.isEmpty(str) || str.length() > 10) {
            str = "";
        }
        if (!str.equals(this.lastMatchText) || this.faqDataList.size() == 0) {
            this.handler.removeCallbacks(this.delayRunnable);
            long jCurrentTimeMillis = System.currentTimeMillis() - this.lastQueryTime;
            long jB = (long) (tVarN.b() * 1000.0f);
            this.handler.postDelayed(this.delayRunnable, Math.max(Math.min(jB - jCurrentTimeMillis, jB), 50L));
        }
        this.currentText = str;
        this.faqListAdapter.setTag(str);
        filterItems(this.currentText);
    }
}

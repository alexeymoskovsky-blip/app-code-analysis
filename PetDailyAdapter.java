package com.petkit.android.activities.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.model.ChatMsg;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PetDailyAdapter extends LoadMoreBaseAdapter<ChatMsg> {
    public PetDailyAdapter(Activity activity, List<ChatMsg> list) {
        super(activity, list);
        setListPageSize(30);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0196  */
    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View getContentView(int r17, android.view.View r18, android.view.ViewGroup r19) {
        /*
            Method dump skipped, instruction units count: 2298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.PetDailyAdapter.getContentView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public class ViewHolder {
        public View bottomLine;
        public TextView content;
        public View dateView;
        public TextView day;
        public ImageView icon;
        public TextView mouth;
        public TextView name;
        public TextView time;

        public ViewHolder() {
        }
    }

    private boolean isShowDateView(int i) throws Exception {
        return i == 0 || CommonUtils.compareTwoDateState(new Date(Long.parseLong(getItem(i).getTimestamp())), new Date(Long.parseLong(getItem(i - 1).getTimestamp()))) != 1;
    }

    private boolean isShowLongBottomLine(int i) throws Exception {
        return i >= getCount() - 1 || CommonUtils.compareTwoDateState(new Date(Long.parseLong(getItem(i).getTimestamp())), new Date(Long.parseLong(getItem(i + 1).getTimestamp()))) != 1;
    }

    public long getNewestTimeindex() {
        List<T> list = this.mList;
        if (list == 0 || list.size() == 0) {
            return 0L;
        }
        return ((ChatMsg) this.mList.get(0)).getTimeindex();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void addNewestChatMsgs(List<ChatMsg> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.mList.addAll(0, list);
        notifyDataSetChanged();
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    public View getEmptyFootView() {
        View viewInflate = LayoutInflater.from(this.mActivity).inflate(R.layout.layout_loadmore_complete, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.loadmore_text);
        textView.setTextAppearance(this.mActivity, R.style.Text_T3_Setting_Gray_Small);
        textView.setText(R.string.No_log_prompt);
        return viewInflate;
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter, com.petkit.android.activities.base.adapter.NormalBaseAdapter, android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getContentView(i, view, viewGroup);
    }
}

package com.petkit.android.activities.mate;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.api.http.apiResponse.HsDeviceRsp;
import com.petkit.android.model.MateDevice;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class MateListActivity extends BaseListActivity {
    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Mate_select);
        setViewState(1);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        HsDeviceRsp.HsDeviceResult hsDeviceResult = HsConsts.getHsDeviceResult(this);
        if (hsDeviceResult == null || hsDeviceResult.getOwnerDevices() == null || hsDeviceResult.getOwnerDevices().size() < 2) {
            finish();
            return;
        }
        MateListAdapter mateListAdapter = new MateListAdapter(this, hsDeviceResult.getOwnerDevices());
        this.mListAdapter = mateListAdapter;
        this.mListView.setAdapter((ListAdapter) mateListAdapter);
        this.mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        MateDevice mateDevice = (MateDevice) this.mListAdapter.getItem(i);
        Intent intent = new Intent(this, (Class<?>) InCallActivity.class);
        intent.putExtra(Constants.EXTRA_HS_DEVICE_DEATILS, new Gson().toJson(mateDevice));
        intent.setFlags(335544320);
        startActivity(intent);
    }

    public class MateListAdapter extends LoadMoreBaseAdapter<MateDevice> {
        public MateListAdapter(Activity activity, List list) {
            super(activity, list);
        }

        @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
        public View getContentView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                view = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_mate_list, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.avatar = (ImageView) view.findViewById(R.id.mate_icon);
                viewHolder.name = (TextView) view.findViewById(R.id.mate_name);
                viewHolder.desc = (TextView) view.findViewById(R.id.mate_desc);
                viewHolder.state = (TextView) view.findViewById(R.id.mate_state);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            MateDevice item = getItem(i);
            viewHolder.avatar.setImageResource(R.drawable.home_dev_mate);
            viewHolder.name.setText(item.getName());
            String lastUseTimeForMate = DeviceCenterUtils.getLastUseTimeForMate(Long.valueOf(item.getId()).longValue());
            if (CommonUtils.isEmpty(lastUseTimeForMate)) {
                viewHolder.desc.setVisibility(8);
            } else {
                viewHolder.desc.setVisibility(0);
                TextView textView = viewHolder.desc;
                Activity activity = this.mActivity;
                textView.setText(activity.getString(R.string.Last_watch_time_format, CommonUtils.getDisplayTimeFromDate(activity, lastUseTimeForMate)));
            }
            viewHolder.state.setText(this.mActivity.getString(HsConsts.getMateStateResId(item.getCallInfo().getStatus())));
            int status = item.getCallInfo().getStatus();
            if (status == 2) {
                viewHolder.state.setTextColor(CommonUtils.getColorById(R.color.blue));
            } else if (status == 3 || status == 4) {
                viewHolder.state.setTextColor(CommonUtils.getColorById(R.color.red));
            } else {
                viewHolder.state.setTextColor(CommonUtils.getColorById(R.color.gray));
            }
            return view;
        }

        public class ViewHolder {
            public ImageView avatar;
            public TextView desc;
            public TextView name;
            public TextView state;

            public ViewHolder() {
            }
        }
    }
}

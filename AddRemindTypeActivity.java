package com.petkit.android.activities.remind;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.RemindTypesRsp;
import com.petkit.android.model.RemindType;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class AddRemindTypeActivity extends BaseListActivity {
    private RemindTypeListAdapter mRemindTypeListAdapter;
    private String petId;

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getRemindTypeList();
        if (bundle == null) {
            this.petId = getIntent().getStringExtra(Constants.EXTRA_PET_ID);
        } else {
            this.petId = bundle.getString(Constants.EXTRA_PET_ID);
        }
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Add_reminder);
        this.mListView.setDividerHeight(0);
        this.mListView.setSelector(R.color.transparent);
        this.mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        RemindTypeListAdapter remindTypeListAdapter = new RemindTypeListAdapter(this, null);
        this.mRemindTypeListAdapter = remindTypeListAdapter;
        this.mListView.setAdapter((ListAdapter) remindTypeListAdapter);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        startActivityForResult(AddRemindActivity.newIntent(this, this.petId, (RemindType) this.mRemindTypeListAdapter.getItem(i)), 1);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getRemindTypeList();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1) {
            setResult(-1);
            finish();
        }
    }

    private void getRemindTypeList() {
        post(ApiTools.SAMPLE_API_SCHEDULE_TYPES, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.remind.AddRemindTypeActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                AddRemindTypeActivity.this.setViewState(2);
                AddRemindTypeActivity.this.setStateFailOrEmpty(R.drawable.default_list_empty_icon, R.string.Hint_error_text_network_lost, 0);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                AddRemindTypeActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RemindTypesRsp remindTypesRsp = (RemindTypesRsp) this.gson.fromJson(this.responseResult, RemindTypesRsp.class);
                if (remindTypesRsp.getError() != null) {
                    AddRemindTypeActivity.this.setViewState(2);
                    AddRemindTypeActivity.this.showLongToast(remindTypesRsp.getError().getMsg(), R.drawable.toast_failed);
                } else if (remindTypesRsp.getResult() != null) {
                    AddRemindTypeActivity.this.setViewState(1);
                    AddRemindTypeActivity.this.mRemindTypeListAdapter.setList(remindTypesRsp.getResult());
                }
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_PET_ID, this.petId);
    }

    public class RemindTypeListAdapter extends NormalBaseAdapter {
        public RemindTypeListAdapter(Activity activity, List<RemindType> list) {
            super(activity, list);
        }

        @Override // android.widget.Adapter
        @SuppressLint({"InflateParams"})
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                ViewHolder viewHolder2 = new ViewHolder();
                View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_remind_type_list, (ViewGroup) null);
                viewHolder2.name = (TextView) viewInflate.findViewById(R.id.type_name_tv);
                viewHolder2.img = (ImageView) viewInflate.findViewById(R.id.type_image);
                viewInflate.setTag(viewHolder2);
                viewHolder = viewHolder2;
                view = viewInflate;
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            RemindType remindType = (RemindType) getItem(i);
            viewHolder.name.setText(remindType.getName());
            ((BaseApplication) AddRemindTypeActivity.this.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(remindType.getImg()).imageView(viewHolder.img).errorPic(R.drawable.default_image_middle).build());
            return view;
        }

        public class ViewHolder {
            public ImageView img;
            public TextView name;

            public ViewHolder() {
            }
        }
    }
}

package com.petkit.android.activities.registe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.RegionServicesRsp;
import com.petkit.android.model.Account;
import com.petkit.android.model.Region;
import com.petkit.android.model.SerMap;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LoginUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class RegisterRegionSelectActivity extends BaseListActivity {
    private RegionListAdapter mListAdapter;
    private SerMap mLoginParams;
    private int selectRegionIndex = 0;
    private boolean isChangedRegion = false;
    private boolean isFirstChange = false;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClick$1(DialogInterface dialogInterface, int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClick$3(DialogInterface dialogInterface, int i) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.isChangedRegion = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
            this.isFirstChange = getIntent().getBooleanExtra(Constants.EXTRA_INFO_BOOLEAN, false);
            this.mLoginParams = (SerMap) getIntent().getSerializableExtra("params");
        } else {
            this.isChangedRegion = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.isFirstChange = bundle.getBoolean(Constants.EXTRA_INFO_BOOLEAN);
            this.mLoginParams = (SerMap) bundle.getSerializable("params");
        }
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isChangedRegion);
        bundle.putBoolean(Constants.EXTRA_INFO_BOOLEAN, this.isFirstChange);
        bundle.putSerializable("params", this.mLoginParams);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Title_region);
        setTitleSize(18);
        setTitleLineVisibility(8);
        setTitleBottomShadeVisibility(0);
        setTitleRightButton(R.string.Done, this);
        setTitleRightButtonTextColor(getResources().getColor(R.color.login_new_blue));
        setTitleRightButtonTextSize(15);
        this.mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
        getRegionList();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        this.mListAdapter = new RegionListAdapter(this, null);
        this.mListView.setDividerHeight(0);
        this.mListView.setAdapter((ListAdapter) this.mListAdapter);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.selectRegionIndex = i;
        this.mListAdapter.notifyDataSetChanged();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.title_right_btn || this.mListAdapter.getCount() == 0) {
            return;
        }
        Region region = (Region) this.mListAdapter.getItem(this.selectRegionIndex);
        SerMap serMap = this.mLoginParams;
        if (serMap != null) {
            serMap.getMap().put(TtmlNode.TAG_REGION, region.getId());
            LoadDialog.dismissDialog();
            LoadDialog.show(this);
            new LoginUtils(this, new LoginUtils.LoginResultListener() { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity.1
                @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                public void onFailed(ErrorInfor errorInfor) {
                }

                @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                public void onSuccess() {
                    LoadDialog.dismissDialog();
                    RegisterRegionSelectActivity.this.entryMainHome();
                }

                @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                public void onFailed(String str) {
                    LoadDialog.dismissDialog();
                    RegisterRegionSelectActivity.this.showShortToast(str, R.drawable.toast_failed);
                }
            }, this.mLoginParams.getMap(), region).start();
            return;
        }
        if (this.isChangedRegion) {
            if (this.isFirstChange) {
                setRegionServer();
                return;
            }
            if (((Region) this.mListAdapter.getItem(0)).getGateway().equals(((Region) this.mListAdapter.getItem(this.selectRegionIndex)).getGateway())) {
                new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Change_region_prompt).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.lambda$onClick$0(dialogInterface, i);
                    }
                }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        RegisterRegionSelectActivity.lambda$onClick$1(dialogInterface, i);
                    }
                }).show();
                return;
            } else {
                new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Change_region_prompt_two).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        this.f$0.lambda$onClick$2(dialogInterface, i);
                    }
                }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        RegisterRegionSelectActivity.lambda$onClick$3(dialogInterface, i);
                    }
                }).show();
                return;
            }
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_REGION, region);
        setResult(-1, intent);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(DialogInterface dialogInterface, int i) {
        setRegionServer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(DialogInterface dialogInterface, int i) {
        setRegionServer();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        setViewState(0);
        getRegionList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void entryMainHome() {
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_MSG_CHANGE_USER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        startActivity(HomeActivity.class, false);
        overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
        sendCloseActivityBroadcast();
    }

    private void getRegionList() {
        post(ApiTools.PASSPORT_REGIONSERVERS, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                String pref;
                super.onSuccess(i, headerArr, bArr);
                RegionServicesRsp regionServicesRsp = (RegionServicesRsp) this.gson.fromJson(this.responseResult, RegionServicesRsp.class);
                if (regionServicesRsp.getError() != null) {
                    RegisterRegionSelectActivity.this.setViewState(2);
                    RegisterRegionSelectActivity.this.showLongToast(regionServicesRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (regionServicesRsp.getResult() == null) {
                    RegisterRegionSelectActivity.this.setViewState(3);
                    return;
                }
                if (regionServicesRsp.getResult().getList() == null || regionServicesRsp.getResult().getList().size() <= 0) {
                    RegisterRegionSelectActivity.this.setViewState(3);
                    return;
                }
                RegisterRegionSelectActivity.this.setViewState(1);
                if (RegisterRegionSelectActivity.this.isChangedRegion) {
                    Account account = UserInforUtils.getAccount();
                    pref = account != null ? account.getRegion() : null;
                } else {
                    pref = regionServicesRsp.getResult().getPref();
                }
                if (!RegisterRegionSelectActivity.this.isEmpty(pref)) {
                    Iterator<Region> it = regionServicesRsp.getResult().getList().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Region next = it.next();
                        if (next.getId().equals(pref)) {
                            regionServicesRsp.getResult().getList().remove(next);
                            regionServicesRsp.getResult().getList().add(0, next);
                            break;
                        }
                    }
                }
                RegisterRegionSelectActivity.this.mListAdapter.setList(regionServicesRsp.getResult().getList());
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                RegisterRegionSelectActivity.this.setViewState(2);
            }
        });
    }

    private void setRegionServer() {
        String id = ((Region) this.mListAdapter.getItem(this.selectRegionIndex)).getId();
        HashMap map = new HashMap(1);
        map.put(TtmlNode.TAG_REGION, id);
        post(ApiTools.SAMPLE_API_USER_UPDATE_REGION, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    CommonUtils.addSysMap(Consts.SHARED_SESSION_ID, "");
                    Intent intent = new Intent();
                    intent.setClass(RegisterRegionSelectActivity.this, WelcomeActivity.class);
                    intent.putExtra(Constants.EXTRA_CAN_GO_BACK, RegisterRegionSelectActivity.this.isFirstChange);
                    RegisterRegionSelectActivity.this.startActivity(intent);
                    if (RegisterRegionSelectActivity.this.isFirstChange) {
                        RegisterRegionSelectActivity.this.sendCloseActivityBroadcast();
                        return;
                    }
                    return;
                }
                RegisterRegionSelectActivity.this.showShortToast(baseRsp.getError().getMsg());
            }
        });
    }

    private void setRegionUpdateAware() {
        post(ApiTools.SAMPLE_API_APP_REGION_UPDATE_AWARE, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.registe.RegisterRegionSelectActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                CommonUtils.addSysBoolMap(RegisterRegionSelectActivity.this, Constants.SHARED_REGION_UPDATE_PROMPT, false);
            }
        });
    }

    public class RegionListAdapter extends NormalBaseAdapter {
        public RegionListAdapter(Activity activity, List<Region> list) {
            super(activity, list);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                view = LayoutInflater.from(RegisterRegionSelectActivity.this).inflate(R.layout.layout_textview_with_lefticon_and_rightarrow, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) view.findViewById(R.id.text_name);
                ImageView imageView = (ImageView) view.findViewById(R.id.right_arrow);
                viewHolder.icon = imageView;
                imageView.setImageResource(R.drawable.icon_service_selected_new);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.name.setText(((Region) getItem(i)).getName());
            if (i == RegisterRegionSelectActivity.this.selectRegionIndex) {
                viewHolder.icon.setVisibility(0);
            } else {
                viewHolder.icon.setVisibility(8);
            }
            if (i == getCount() - 1) {
                View viewFindViewById = view.findViewById(R.id.divider_line);
                viewHolder.dividerLine = viewFindViewById;
                viewFindViewById.setVisibility(8);
            }
            viewHolder.name.setTextColor(CommonUtils.getColorById(R.color.black));
            return view;
        }

        public class ViewHolder {
            public View dividerLine;
            public ImageView icon;
            public TextView name;

            public ViewHolder() {
            }
        }
    }
}

package com.petkit.android.activities.registe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.im.iot.NewIotManager;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.registe.fragment.BaseRegionFragment;
import com.petkit.android.activities.registe.fragment.RegionFragment;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.model.Region;
import com.petkit.android.model.SerMap;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LoginUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes5.dex */
public class RegionMainActivity extends BaseActivity implements BaseRegionFragment.OnSwitchSearchModeListenr, RegionFragment.OnSearchResultClickListener {
    private boolean isChangedRegion = false;
    private boolean isFirstChange = false;
    private SerMap mLoginParams;
    private RegionFragment regionFragment;
    private Region selectRegion;

    public static /* synthetic */ void lambda$rightBtnClick$1(DialogInterface dialogInterface, int i) {
    }

    public static /* synthetic */ void lambda$rightBtnClick$3(DialogInterface dialogInterface, int i) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.isChangedRegion = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
            this.isFirstChange = getIntent().getBooleanExtra(Constants.EXTRA_INFO_BOOLEAN, false);
            this.mLoginParams = (SerMap) getIntent().getSerializableExtra("params");
            this.selectRegion = (Region) getIntent().getSerializableExtra(Constants.EXTRA_REGION);
        } else {
            this.isChangedRegion = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.isFirstChange = bundle.getBoolean(Constants.EXTRA_INFO_BOOLEAN);
            this.mLoginParams = (SerMap) bundle.getSerializable("params");
            this.selectRegion = (Region) bundle.getSerializable(Constants.EXTRA_REGION);
        }
        super.onCreate(bundle);
        setContentView(R.layout.layout_setting);
        setupViews();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isChangedRegion);
        bundle.putBoolean(Constants.EXTRA_INFO_BOOLEAN, this.isFirstChange);
        bundle.putSerializable("params", this.mLoginParams);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Title_region);
        setTitleSize(18);
        setTitleLineVisibility(8);
        setTitleBottomShadeVisibility(0);
        setTitleRightButton(R.string.Done, this);
        setTitleRightButtonTextColor(getResources().getColor(R.color.login_new_blue));
        setTitleRightButtonTextSize(15);
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        this.regionFragment = new RegionFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isChangedRegion);
        bundle.putBoolean(Constants.EXTRA_INFO_BOOLEAN, this.isFirstChange);
        bundle.putSerializable("params", this.mLoginParams);
        bundle.putSerializable(Constants.EXTRA_REGION, this.selectRegion);
        this.regionFragment.setArguments(bundle);
        fragmentTransactionBeginTransaction.add(R.id.re_basecontent, this.regionFragment);
        fragmentTransactionBeginTransaction.commit();
        this.regionFragment.setOnSwitchSearchModeListenr(this);
        this.regionFragment.setOnSearchResultClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.title_right_btn) {
            rightBtnClick();
        }
    }

    private void rightBtnClick() {
        Region selectedCategory;
        if (this.regionFragment.getListAdapter().getCount() != 0) {
            if (this.regionFragment.getListAdapter().getSelectedCategory() == null && this.regionFragment.getPrefRegion() == null) {
                return;
            }
            if (this.regionFragment.getListAdapter().getSelectedCategory() == null) {
                selectedCategory = this.regionFragment.getPrefRegion();
            } else {
                selectedCategory = this.regionFragment.getListAdapter().getSelectedCategory();
            }
            SerMap serMap = this.mLoginParams;
            if (serMap != null) {
                serMap.getMap().put(TtmlNode.TAG_REGION, selectedCategory.getId());
                LoadDialog.dismissDialog();
                LoadDialog.show(this);
                new LoginUtils(this, new LoginUtils.LoginResultListener() { // from class: com.petkit.android.activities.registe.RegionMainActivity.1
                    @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                    public void onFailed(ErrorInfor errorInfor) {
                    }

                    public AnonymousClass1() {
                    }

                    @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                    public void onSuccess() {
                        LoadDialog.dismissDialog();
                        RegionMainActivity.this.entryMainHome();
                    }

                    @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
                    public void onFailed(String str) {
                        LoadDialog.dismissDialog();
                        RegionMainActivity.this.showShortToast(str, R.drawable.toast_failed);
                    }
                }, this.mLoginParams.getMap(), selectedCategory).start();
                return;
            }
            if (this.isChangedRegion) {
                if (this.isFirstChange) {
                    setRegionServer();
                    return;
                }
                Region prefRegion = this.regionFragment.getPrefRegion();
                Region selectedCategory2 = this.regionFragment.getListAdapter().getSelectedCategory();
                if (selectedCategory2 == null) {
                    finish();
                    return;
                } else if (prefRegion.getGateway().equals(selectedCategory2.getGateway())) {
                    new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Change_region_prompt).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegionMainActivity$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            this.f$0.lambda$rightBtnClick$0(dialogInterface, i);
                        }
                    }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegionMainActivity$$ExternalSyntheticLambda1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            RegionMainActivity.lambda$rightBtnClick$1(dialogInterface, i);
                        }
                    }).show();
                    return;
                } else {
                    new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Change_region_prompt_two).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegionMainActivity$$ExternalSyntheticLambda2
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            this.f$0.lambda$rightBtnClick$2(dialogInterface, i);
                        }
                    }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.RegionMainActivity$$ExternalSyntheticLambda3
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            RegionMainActivity.lambda$rightBtnClick$3(dialogInterface, i);
                        }
                    }).show();
                    return;
                }
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_REGION, selectedCategory);
            setResult(-1, intent);
            finish();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.registe.RegionMainActivity$1 */
    public class AnonymousClass1 implements LoginUtils.LoginResultListener {
        @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
        public void onFailed(ErrorInfor errorInfor) {
        }

        public AnonymousClass1() {
        }

        @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
        public void onSuccess() {
            LoadDialog.dismissDialog();
            RegionMainActivity.this.entryMainHome();
        }

        @Override // com.petkit.android.utils.LoginUtils.LoginResultListener
        public void onFailed(String str) {
            LoadDialog.dismissDialog();
            RegionMainActivity.this.showShortToast(str, R.drawable.toast_failed);
        }
    }

    public /* synthetic */ void lambda$rightBtnClick$0(DialogInterface dialogInterface, int i) {
        setRegionServer();
    }

    public /* synthetic */ void lambda$rightBtnClick$2(DialogInterface dialogInterface, int i) {
        setRegionServer();
    }

    public void entryMainHome() {
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_MSG_CHANGE_USER);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        startActivity(HomeActivity.class, false);
        overridePendingTransition(R.anim.push_right_in, R.anim.slide_none);
        sendCloseActivityBroadcast();
    }

    private void setRegionServer() {
        String id = this.regionFragment.getListAdapter().getSelectedCategory().getId();
        HashMap map = new HashMap(1);
        map.put(TtmlNode.TAG_REGION, id);
        post(ApiTools.SAMPLE_API_USER_UPDATE_REGION, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.registe.RegionMainActivity.2
            public AnonymousClass2(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    CommonUtils.addSysMap(Consts.SHARED_SESSION_ID, "");
                    DataHelper.setStringSF(RegionMainActivity.this, Consts.ACCOUNT_REGION_STRING, new Gson().toJson(RegionMainActivity.this.regionFragment.getListAdapter().getSelectedCategory()));
                    NewIotManager.getInstance().destroy();
                    Intent intent = new Intent();
                    intent.setClass(RegionMainActivity.this, NormalLoginActivity.class);
                    intent.putExtra(Constants.EXTRA_CAN_GO_BACK, RegionMainActivity.this.isFirstChange);
                    RegionMainActivity.this.startActivity(intent);
                    if (RegionMainActivity.this.isFirstChange) {
                        RegionMainActivity.this.sendCloseActivityBroadcast();
                        return;
                    }
                    return;
                }
                RegionMainActivity.this.showShortToast(baseRsp.getError().getMsg());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.registe.RegionMainActivity$2 */
    public class AnonymousClass2 extends AsyncHttpRespHandler {
        public AnonymousClass2(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() == null) {
                CommonUtils.addSysMap(Consts.SHARED_SESSION_ID, "");
                DataHelper.setStringSF(RegionMainActivity.this, Consts.ACCOUNT_REGION_STRING, new Gson().toJson(RegionMainActivity.this.regionFragment.getListAdapter().getSelectedCategory()));
                NewIotManager.getInstance().destroy();
                Intent intent = new Intent();
                intent.setClass(RegionMainActivity.this, NormalLoginActivity.class);
                intent.putExtra(Constants.EXTRA_CAN_GO_BACK, RegionMainActivity.this.isFirstChange);
                RegionMainActivity.this.startActivity(intent);
                if (RegionMainActivity.this.isFirstChange) {
                    RegionMainActivity.this.sendCloseActivityBroadcast();
                    return;
                }
                return;
            }
            RegionMainActivity.this.showShortToast(baseRsp.getError().getMsg());
        }
    }

    @Override // com.petkit.android.activities.registe.fragment.BaseRegionFragment.OnSwitchSearchModeListenr
    public void isInSearchMode(boolean z) {
        if (z) {
            setNoTitle();
        } else {
            setHasTitle();
        }
    }

    @Override // com.petkit.android.activities.registe.fragment.RegionFragment.OnSearchResultClickListener
    public void click() {
        rightBtnClick();
    }
}

package com.petkit.android.activities.registe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.pet.PetRegisterFirstPartActivity;
import com.petkit.android.activities.registe.fragment.RegisterFragment;
import com.petkit.android.activities.registe.fragment.WelcomeControlFragment;
import com.petkit.android.activities.registe.mode.TwitterResult;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.RegionServicesRsp;
import com.petkit.android.model.Region;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.ShareHelper;
import com.petkit.oversea.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import cz.msebera.android.httpclient.Header;
import java.util.Iterator;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes5.dex */
@SuppressLint({"InflateParams"})
public class WelcomeActivity extends BaseActivity implements View.OnClickListener, SlidingUpPanelLayout.PanelSlideListener {
    private static final float ALPHA_DEFAULT = 0.2f;
    private static final float ALPHA_SELECTED = 0.7f;
    private static final int ANIMATION_SCALE_TIME = 2000;
    private static final int ANIMATION_START_DELAY_TIME = 500;
    public static final int DEFAULT_AUTH_REQUEST_CODE = 140;
    public static final int REQUEST_REGION = 4662;
    private boolean canGoBack = true;
    private Region mRegion;
    private TextView tvRegion;
    private ImageView welcomeCenterLogo;
    private WelcomeControlFragment welcomeControlFragment;
    private ImageView welcomeTopLogo;

    @Override // com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
    public void onPanelSlide(View view, float f) {
    }

    @Override // com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState panelState, SlidingUpPanelLayout.PanelState panelState2) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.jess.arms.widget.InputMethodRelativeLayout.OnSizeChangedListener
    public void onSizeChange(boolean z, int i, int i2) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.canGoBack = getIntent().getBooleanExtra(Constants.EXTRA_CAN_GO_BACK, true);
        Region region = (Region) getIntent().getSerializableExtra(Constants.EXTRA_REGION);
        this.mRegion = region;
        if (region == null) {
            Region region2 = (Region) new Gson().fromJson(DataHelper.getStringSF(this, Consts.ACCOUNT_REGION_STRING), Region.class);
            this.mRegion = region2;
            if (region2 == null) {
                startActivity(new Intent(this, (Class<?>) AccountSelectRegionActivity.class));
                finish();
            } else {
                refreshRegionGateway();
            }
        }
        setContentView(R.layout.activity_welcome);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
        this.welcomeCenterLogo = (ImageView) this.contentView.findViewById(R.id.welcome_center_logo);
        this.welcomeTopLogo = (ImageView) this.contentView.findViewById(R.id.welcome_top_logo);
        this.tvRegion = (TextView) this.contentView.findViewById(R.id.tv_region);
        this.contentView.findViewById(R.id.welcome_bg).setOnClickListener(this);
        this.tvRegion.setOnClickListener(this);
        WelcomeControlFragment welcomeControlFragment = (WelcomeControlFragment) getSupportFragmentManager().findFragmentById(R.id.controlFragment);
        this.welcomeControlFragment = welcomeControlFragment;
        Region region = this.mRegion;
        if (region != null) {
            welcomeControlFragment.setRegion(region);
            this.tvRegion.setText(this.mRegion.getName());
        }
        this.welcomeControlFragment.setPanelSlideListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    @RequiresApi(api = 23)
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.welcome_bg) {
            if (isSoftShowing()) {
                hideSoftInput();
            }
        } else if (id == R.id.tv_region) {
            Intent intent = new Intent(this, (Class<?>) RegionMainActivity.class);
            if (this.mRegion != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_REGION, this.mRegion);
                intent.putExtras(bundle);
            }
            startActivityForResult(intent, 4662);
        }
    }

    public void refreshRegionGateway() {
        AsyncHttpUtil.post(ApiTools.PASSPORT_REGIONSERVERS, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.registe.WelcomeActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RegionServicesRsp regionServicesRsp = (RegionServicesRsp) this.gson.fromJson(this.responseResult, RegionServicesRsp.class);
                if (regionServicesRsp.getError() != null) {
                    return;
                }
                if (regionServicesRsp.getResult() == null) {
                    ApiTools.setApiBaseUrl(WelcomeActivity.this.mRegion.getGateway());
                    return;
                }
                if (regionServicesRsp.getResult().getList() == null || regionServicesRsp.getResult().getList().size() <= 0) {
                    return;
                }
                Iterator<Region> it = regionServicesRsp.getResult().getList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Region next = it.next();
                    if (next.getId().equals(WelcomeActivity.this.mRegion.getId())) {
                        WelcomeActivity.this.mRegion.setGateway(next.getGateway());
                        DataHelper.setStringSF(WelcomeActivity.this, Consts.ACCOUNT_REGION_STRING, new Gson().toJson(WelcomeActivity.this.mRegion));
                        break;
                    }
                }
                ApiTools.setApiBaseUrl(WelcomeActivity.this.mRegion.getGateway());
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                ApiTools.setApiBaseUrl(WelcomeActivity.this.mRegion.getGateway());
            }
        });
    }

    private boolean isSoftShowing() {
        int height = getWindow().getDecorView().getHeight();
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return (height * 2) / 3 > rect.bottom;
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) {
            if (isSoftShowing()) {
                hideSoftInput();
                return true;
            }
            if (!this.canGoBack) {
                showBackDialog();
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        ShareHelper.dealWithOnActivityResult(this, i, i2, intent);
        if (i != 140) {
            if (i2 == -1) {
                switch (i) {
                    case RegisterFragment.REQUEST_REGISTER_USER_INFO /* 4660 */:
                        Intent intent2 = new Intent(this, (Class<?>) PetRegisterFirstPartActivity.class);
                        intent2.putExtra(Constants.VIEW_FROM, 1);
                        startActivityForResult(intent2, RegisterFragment.REQUEST_REGISTER_FIRST_PET);
                        int i3 = R.anim.slide_none;
                        overridePendingTransition(i3, i3);
                        break;
                    case RegisterFragment.REQUEST_REGISTER_FIRST_PET /* 4661 */:
                        startActivity(HomeActivity.class, false);
                        sendCloseActivityBroadcast();
                        break;
                    case 4662:
                        Region region = (Region) intent.getSerializableExtra(Constants.EXTRA_REGION);
                        this.mRegion = region;
                        this.welcomeControlFragment.setRegion(region);
                        ApiTools.setApiBaseUrl(this.mRegion.getGateway());
                        this.tvRegion.setText(this.mRegion.getName());
                        break;
                }
            }
        } else {
            EventBus.getDefault().post(new TwitterResult(i, i2, intent));
        }
        super.onActivityResult(i, i2, intent);
    }

    private void showBackDialog() {
        new AlertDialog.Builder(this).setCancelable(true).setTitle(R.string.Prompt).setMessage(R.string.Confirm_app_quit).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.WelcomeActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("exit", true);
                WelcomeActivity.this.startActivityWithData(HomeActivity.class, bundle, true);
                WelcomeActivity.this.sendCloseActivityBroadcast();
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.registe.WelcomeActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }
}

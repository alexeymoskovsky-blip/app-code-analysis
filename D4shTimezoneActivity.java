package com.petkit.android.activities.petkitBleDevice.d4sh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentTransaction;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.BaseTimezoneFragment;
import com.petkit.android.activities.petkitBleDevice.d4sh.TimezoneFragment;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shTimezone;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shUpdateSettingEvent;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D4shTimezoneActivity extends BaseActivity implements BaseTimezoneFragment.OnSwitchSearchModeListenr, TimezoneFragment.OnSearchResultClickListener {
    private long deviceId;
    private TimezoneFragment timezoneFragment;
    private int typeCode;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.typeCode = getIntent().getIntExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
        } else {
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID, 0L);
            this.typeCode = bundle.getInt(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
        }
        super.onCreate(bundle);
        setContentView(R.layout.layout_setting);
        setupViews();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Set_the_time_zone);
        setTitleLineVisibility(8);
        setTitleRightButton(R.string.Done, this);
        setTitleRightButtonTextColor(getResources().getColor(R.color.login_new_blue));
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        this.timezoneFragment = new TimezoneFragment();
        this.timezoneFragment.setArguments(new Bundle());
        fragmentTransactionBeginTransaction.add(R.id.re_basecontent, this.timezoneFragment);
        fragmentTransactionBeginTransaction.commit();
        this.timezoneFragment.setOnSwitchSearchModeListenr(this);
        this.timezoneFragment.setOnSearchResultClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.title_right_btn) {
            rightBtnClick();
        }
    }

    private void rightBtnClick() {
        D4shTimezone selectedCategory;
        if (this.timezoneFragment.getListAdapter().getCount() != 0) {
            if (this.timezoneFragment.getListAdapter().getSelectedCategory() == null && this.timezoneFragment.getPrefRegion() == null) {
                return;
            }
            if (this.timezoneFragment.getListAdapter().getSelectedCategory() == null) {
                selectedCategory = this.timezoneFragment.getPrefRegion();
            } else {
                selectedCategory = this.timezoneFragment.getListAdapter().getSelectedCategory();
            }
            HashMap map = new HashMap(1);
            String strValueOf = String.valueOf(selectedCategory.getTimeZone().getRawOffset() / 3600000.0f);
            map.put("kv", "{\"timezone\": " + strValueOf + "}");
            map.put("id", String.valueOf(this.deviceId));
            post(this.typeCode == 0 ? ApiTools.SAMPLE_API_D4SH_UPDATE_SETTINGS : ApiTools.SAMPLE_API_D4H_UPDATE_SETTINGS, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.D4shTimezoneActivity.1
                public final /* synthetic */ String val$timezone;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(Activity this, boolean z, String strValueOf2) {
                    super(this, z);
                    str = strValueOf2;
                }

                @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                    super.onSuccess(i, headerArr, bArr);
                    BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                    if (baseRsp.getError() == null) {
                        D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(D4shTimezoneActivity.this.deviceId, D4shTimezoneActivity.this.typeCode);
                        d4shRecordByDeviceId.setTimezone(Float.parseFloat(str));
                        d4shRecordByDeviceId.save();
                        EventBus.getDefault().post(new D4shUpdateSettingEvent());
                        D4shTimezoneActivity.this.finish();
                        return;
                    }
                    D4shTimezoneActivity.this.showShortToast(baseRsp.getError().getMsg());
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.d4sh.D4shTimezoneActivity$1 */
    public class AnonymousClass1 extends AsyncHttpRespHandler {
        public final /* synthetic */ String val$timezone;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Activity this, boolean z, String strValueOf2) {
            super(this, z);
            str = strValueOf2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() == null) {
                D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(D4shTimezoneActivity.this.deviceId, D4shTimezoneActivity.this.typeCode);
                d4shRecordByDeviceId.setTimezone(Float.parseFloat(str));
                d4shRecordByDeviceId.save();
                EventBus.getDefault().post(new D4shUpdateSettingEvent());
                D4shTimezoneActivity.this.finish();
                return;
            }
            D4shTimezoneActivity.this.showShortToast(baseRsp.getError().getMsg());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.BaseTimezoneFragment.OnSwitchSearchModeListenr
    public void isInSearchMode(boolean z) {
        if (z) {
            setNoTitle();
        } else {
            setHasTitle();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.TimezoneFragment.OnSearchResultClickListener
    public void click() {
        rightBtnClick();
    }
}

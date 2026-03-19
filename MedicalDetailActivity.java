package com.petkit.android.activities.mate.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.ImageDetailActivity;
import com.petkit.android.activities.community.adapter.PostImagesAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.model.Medical;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class MedicalDetailActivity extends BaseActivity {
    private int image_length = 0;
    GridView mGridView;
    private Medical mMedical;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.mMedical = (Medical) getIntent().getSerializableExtra(Constants.EXTRA_MEDICAL);
        } else {
            this.mMedical = (Medical) bundle.getSerializable(Constants.EXTRA_MEDICAL);
        }
        if (this.mMedical == null) {
            return;
        }
        this.image_length = (int) (((BaseApplication.displayMetrics.widthPixels - (DeviceUtils.dpToPixel(this, 10.0f) * 2.0f)) - (DeviceUtils.dpToPixel(this, 5.0f) * 2.0f)) / 3.0f);
        setContentView(R.layout.activity_medical_detail);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_MEDICAL, this.mMedical);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.title_right_btn) {
            removeMedical();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(this.mMedical.getHappendAt().length() > 10 ? this.mMedical.getHappendAt().substring(0, 10) : this.mMedical.getHappendAt());
        setTitleRightButton(R.string.Delete, this);
        ((TextView) findViewById(R.id.medical_detail)).setText(this.mMedical.getDetail());
        this.mGridView = (GridView) findViewById(R.id.medical_imgs);
        if (this.mMedical.getImgs() == null || this.mMedical.getImgs().size() == 0) {
            this.mGridView.setVisibility(8);
            return;
        }
        this.mGridView.setVisibility(0);
        int size = this.mMedical.getImgs().size();
        int i = size < 3 ? size : 3;
        this.mGridView.setNumColumns(i);
        this.mGridView.setAdapter((ListAdapter) new PostImagesAdapter(this, this.mMedical.getImgs(), i));
        ViewGroup.LayoutParams layoutParams = this.mGridView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = CommonUtils.getGridViewHeight(this.mGridView, i, (int) DeviceUtils.dpToPixel(this, 5.0f));
            if (size < 3) {
                layoutParams.width = (int) ((this.image_length * size) + ((size + 1) * DeviceUtils.dpToPixel(this, 5.0f)));
            } else {
                layoutParams.width = -1;
            }
            this.mGridView.setLayoutParams(layoutParams);
        }
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.petkit.android.activities.mate.medical.MedicalDetailActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                Intent intent = new Intent(MedicalDetailActivity.this, (Class<?>) ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.IMAGE_LIST_DATA, MedicalDetailActivity.this.mMedical.getImgs());
                intent.putExtra(ImageDetailActivity.IMAGE_LIST_POSITION, i2);
                MedicalDetailActivity.this.startActivity(intent);
                MedicalDetailActivity.this.overridePendingTransition(R.anim.img_scale_in, R.anim.slide_none);
            }
        });
    }

    private void removeMedical() {
        HashMap map = new HashMap();
        map.put("id", this.mMedical.getId());
        post(ApiTools.SAMPLE_API_MEDICALRECORD_REMOVE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.mate.medical.MedicalDetailActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    MedicalDetailActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else if (this.responseResult.contains("success")) {
                    MedicalDetailActivity.this.setResult(-1);
                    MedicalDetailActivity.this.finish();
                }
            }
        }, false);
    }
}

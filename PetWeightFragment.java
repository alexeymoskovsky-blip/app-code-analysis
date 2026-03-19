package com.petkit.android.activities.pet.fragment.create;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.pet.adapter.RegisterPetProgressAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.WeightRangeRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PetCategory;
import com.petkit.android.model.PetSize;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.PetWeightScaleScrollView;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes4.dex */
public class PetWeightFragment extends BaseSwitchFragment implements View.OnClickListener {
    private String birth;
    private TextView btnNext;
    private FrameLayout flWeight;
    private String gender;
    private float[] ideaWeight;
    private boolean isModifyWeight;
    private double maxWeight;
    private double minWeight;
    private OnWeightListner onWeightListner;
    private String petCategoryId;
    private String petCategoryName;
    private RegisterPetProgressAdapter petProgressAdapter;
    private String petSizeId;
    private String petSizeName;
    private TextView petWeightHint1;
    private RecyclerView rvTitleProcess;
    private PetWeightScaleScrollView scaleScrollView;
    private long t;
    private TextView tvWeightNum;
    private TextView tvWeightUnit;
    private String unitString;
    private boolean updateWeight;
    private int weightUnit;
    private double petWeight = 10.0d;
    private boolean ifWeightTenable = true;

    public interface OnWeightListner {
        void isWeightTenable(boolean z);
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonStringId(boolean z, int i) {
        return 0;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonVisibility(int i) {
        return 8;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleStringId() {
        return 0;
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        PetSize size;
        super.onCreate(bundle);
        Pet pet = (Pet) getArguments().getSerializable(Constants.EXTRA_DOG);
        if (pet != null) {
            this.petCategoryId = String.valueOf(pet.getCategory().getId());
            size = pet.getSize();
            this.petCategoryName = String.valueOf(pet.getCategory().getName());
            this.petWeight = Double.valueOf(pet.getWeight()).doubleValue();
            this.birth = pet.getBirth();
            this.gender = String.valueOf(pet.getGender());
        } else {
            PetCategory petCategory = (PetCategory) getArguments().getSerializable(Constants.EXTRA_CATEGORY);
            size = (PetSize) getArguments().getSerializable(Constants.EXTRA_PETSIZE);
            this.petCategoryId = String.valueOf(petCategory.getId());
            this.petCategoryName = petCategory.getName();
            this.birth = getArguments().getString(Constants.EXTRA_BIRTHDAY);
            this.gender = getArguments().getString(Constants.EXTRA_GENDER);
        }
        this.petSizeId = String.valueOf(size.getId());
        this.petSizeName = size.getName();
        this.isModifyWeight = getArguments().getInt(Constants.MODIFY_PET_PROPS, -1) == 6;
        this.updateWeight = getArguments().getBoolean(Constants.EXTRA_BOOLEAN);
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_pet_weight);
        setNoTitle();
        getWeightRange();
        setCurrentfragment(6);
    }

    private void getWeightRange() {
        HashMap map = new HashMap();
        map.put("categoryId", this.petCategoryId);
        map.put(Consts.PET_BIRTH, this.birth);
        map.put(Consts.PET_GENDER, this.gender);
        String str = this.petSizeId;
        if (str != null) {
            map.put("size", str);
        }
        post(ApiTools.SAMPLE_API_PET_WEIGHT_RANGE, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.pet.fragment.create.PetWeightFragment.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                WeightRangeRsp weightRangeRsp = (WeightRangeRsp) this.gson.fromJson(this.responseResult, WeightRangeRsp.class);
                try {
                    PetWeightFragment.this.minWeight = weightRangeRsp.getResult().getCriticalWeight()[0];
                    PetWeightFragment.this.maxWeight = weightRangeRsp.getResult().getCriticalWeight()[1];
                    if (PetWeightFragment.this.minWeight < 0.001d && PetWeightFragment.this.maxWeight < 0.001d) {
                        PetWeightFragment.this.minWeight = 0.0d;
                        if (PetWeightFragment.this.weightUnit == 1) {
                            PetWeightFragment.this.maxWeight = 220.0d;
                        } else {
                            PetWeightFragment.this.maxWeight = 100.0d;
                        }
                    }
                    PetWeightFragment.this.ideaWeight = weightRangeRsp.getResult().getIdealWeight();
                    PetWeightFragment.this.initAllView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAllView() {
        int i;
        if (!this.isModifyWeight) {
            if (this.ideaWeight[1] > 0.0d) {
                this.petWeight = (r0[0] + r4) / 2.0f;
            }
        }
        this.tvWeightNum = (TextView) this.contentView.findViewById(R.id.pet_weight_num);
        this.tvWeightUnit = (TextView) this.contentView.findViewById(R.id.pet_weight_unit);
        TextView textView = (TextView) this.contentView.findViewById(R.id.btn_next);
        this.btnNext = textView;
        textView.setOnClickListener(this);
        this.petWeightHint1 = (TextView) this.contentView.findViewById(R.id.pet_weight_hint1);
        setWeightNum();
        int unit = UserInforUtils.getCurrentLoginResult().getSettings().getUnit();
        this.weightUnit = unit;
        if (unit == 1) {
            this.petWeight = CommonUtil.KgToLb(this.petWeight);
            this.ideaWeight[0] = (float) CommonUtil.KgToLb(r0[0]);
            this.ideaWeight[1] = (float) CommonUtil.KgToLb(r0[1]);
            this.minWeight = (float) CommonUtil.KgToLb(this.minWeight);
            this.maxWeight = (float) CommonUtil.KgToLb(this.maxWeight);
            this.unitString = getString(R.string.Unit_lb);
            i = 2205;
        } else {
            this.unitString = getString(R.string.Unit_kg);
            i = 1000;
        }
        this.tvWeightUnit.setText(this.unitString);
        double d = this.maxWeight;
        if (d * 10.0d > i) {
            i = (int) (d * 10.0d * 1.5d);
        }
        int i2 = getResources().getConfiguration().screenWidthDp;
        int i3 = getResources().getConfiguration().screenHeightDp;
        this.flWeight = (FrameLayout) this.contentView.findViewById(R.id.fl_weight);
        initUi(i2, i3);
        PetWeightScaleScrollView petWeightScaleScrollView = (PetWeightScaleScrollView) this.contentView.findViewById(R.id.weight_scroll_view);
        this.scaleScrollView = petWeightScaleScrollView;
        petWeightScaleScrollView.setMinAndMaxWeight(this.minWeight, this.maxWeight);
        this.scaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.pet.fragment.create.PetWeightFragment.2
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i4) {
                PetWeightFragment.this.petWeight = ((double) i4) / 10.0d;
                PetWeightFragment.this.setWeightNum();
            }
        });
        this.petWeight = CommonUtil.doubleToDouble(this.petWeight);
        this.scaleScrollView.setScaleColor(getActivity().getResources().getColor(R.color.gray));
        this.scaleScrollView.setInvalidScaleColor(getActivity().getResources().getColor(R.color.pet_weight_invalid_color));
        this.scaleScrollView.setValueRange(i, 0);
        this.scaleScrollView.setValueValidRange((int) (this.minWeight * 10.0d), (int) (this.maxWeight * 10.0d));
        PetWeightScaleScrollView petWeightScaleScrollView2 = this.scaleScrollView;
        int i4 = (int) (this.petWeight * 10.0d);
        FragmentActivity activity = getActivity();
        int i5 = i2 * 2;
        float f = i2;
        if (i5 > i3) {
            f /= 2.0f;
        }
        petWeightScaleScrollView2.setDefault(i4, ArmsUtils.dip2px(activity, f));
        this.rvTitleProcess = (RecyclerView) this.contentView.findViewById(R.id.rv_title_process);
        if (this.updateWeight) {
            RegisterPetProgressAdapter registerPetProgressAdapter = new RegisterPetProgressAdapter(getActivity(), 5);
            this.petProgressAdapter = registerPetProgressAdapter;
            registerPetProgressAdapter.setCurrentStep(5);
        } else {
            RegisterPetProgressAdapter registerPetProgressAdapter2 = new RegisterPetProgressAdapter(getActivity(), 6);
            this.petProgressAdapter = registerPetProgressAdapter2;
            registerPetProgressAdapter2.setCurrentStep(6);
        }
        this.rvTitleProcess.setAdapter(this.petProgressAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(0);
        this.rvTitleProcess.setLayoutManager(linearLayoutManager);
        if (this.isModifyWeight) {
            this.rvTitleProcess.setVisibility(4);
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.t < 500) {
            return;
        }
        this.t = jCurrentTimeMillis;
        if (view.getId() == R.id.btn_next) {
            goToNextStep();
        }
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public void collectDataToBundle() {
        this.current_fragment = 6;
        if (!this.ifWeightTenable) {
            this.isDataDone = false;
            return;
        }
        if (1 == UserInforUtils.getCurrentLoginResult().getSettings().getUnit()) {
            this.petWeight = CommonUtil.LbToKg(this.petWeight);
        }
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        this.bundle.putString(Constants.EXTRA_WEIGHT, String.valueOf(this.petWeight));
        this.params.put("weight", String.valueOf(this.petWeight));
        this.isDataDone = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWeightNum() {
        String str;
        String str2;
        this.tvWeightNum.setText(String.valueOf(CommonUtil.doubleToDouble(this.petWeight)));
        if (this.ideaWeight[1] < 0.001d) {
            this.petWeightHint1.setVisibility(8);
        } else {
            double d = this.petWeight;
            if (d >= this.minWeight && d <= this.maxWeight) {
                this.ifWeightTenable = true;
                this.petWeightHint1.setTextColor(getResources().getColor(R.color.gray));
                if (this.petSizeId == null) {
                    str = this.petCategoryName;
                } else {
                    str = this.petCategoryName + " (" + this.petSizeName + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                if (this.weightUnit == 1) {
                    str2 = CommonUtil.doubleToDouble(this.ideaWeight[0]) + this.unitString + "-" + CommonUtil.doubleToDouble(this.ideaWeight[1]) + this.unitString;
                } else {
                    str2 = this.ideaWeight[0] + this.unitString + "-" + this.ideaWeight[1] + this.unitString;
                }
                this.petWeightHint1.setText(getString(R.string.Pet_weight_limit_hint_format, str, str2));
                this.btnNext.setBackgroundResource(R.drawable.selector_solid_new_blue);
                this.btnNext.setClickable(true);
                this.btnNext.setTextColor(-1);
            } else {
                this.ifWeightTenable = false;
                this.petWeightHint1.setText(R.string.Pet_weight_over_limit_hint);
                this.petWeightHint1.setTextColor(getResources().getColor(R.color.w5_home_red));
                this.btnNext.setBackgroundResource(R.drawable.solid_login_light_blue_bg);
                this.btnNext.setClickable(false);
                this.btnNext.setTextColor(getResources().getColor(R.color.slide_bar_hint_color));
            }
        }
        OnWeightListner onWeightListner = this.onWeightListner;
        if (onWeightListner != null) {
            onWeightListner.isWeightTenable(this.ifWeightTenable);
        }
    }

    public void setOnWeightListner(OnWeightListner onWeightListner) {
        this.onWeightListner = onWeightListner;
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        RegisterPetProgressAdapter registerPetProgressAdapter = this.petProgressAdapter;
        if (registerPetProgressAdapter != null) {
            registerPetProgressAdapter.setWidth(configuration.screenWidthDp);
        }
    }

    private void initUi(int i, int i2) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.flWeight.getLayoutParams();
        if (i * 1.7777778f > i2) {
            layoutParams.width = (int) (((double) ArmsUtils.dip2px(getActivity(), i - 32)) * 0.5d);
        } else {
            layoutParams.width = ArmsUtils.dip2px(getActivity(), i - 32);
        }
        this.flWeight.setLayoutParams(layoutParams);
    }
}

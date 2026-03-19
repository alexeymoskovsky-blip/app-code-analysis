package com.petkit.android.activities.pet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.WeightRangeRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PetCategory;
import com.petkit.android.model.PetSize;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.WeightScaleScrollView;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes4.dex */
public class PetRegisterWeightFragment extends BaseSwitchFragment implements View.OnClickListener {
    private String birth;
    private String gender;
    private float[] ideaWeightKg;
    private boolean isModifyWeight;
    private double maxWeight;
    private double minWeight;
    private OnWeightListner onWeightListner;
    private String petCategoryId;
    private String petCategoryName;
    private String petSizeId;
    private String petSizeName;
    private TextView petWeightHint1;
    private TextView tvWeightEnglish;
    private TextView tvWeightMetric;
    private TextView tvWeightNum;
    private TextView tvWeightUnit;
    private String unitString;
    private int weightUnit;
    private double petWeight = 10.0d;
    private float[] ideaWeightLb = new float[2];
    private boolean ifWeightTenable = true;

    public interface OnWeightListner {
        void isWeightTenable(boolean z);
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonVisibility(int i) {
        return 0;
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        PetSize size;
        super.onCreate(bundle);
        Pet pet = (Pet) getArguments().getSerializable(Constants.EXTRA_DOG);
        this.weightUnit = UserInforUtils.getCurrentLoginResult().getSettings().getUnit();
        if (pet != null) {
            this.petCategoryId = String.valueOf(pet.getCategory().getId());
            size = pet.getSize();
            this.petCategoryName = String.valueOf(pet.getCategory().getName());
            String weight = pet.getWeight();
            if (this.weightUnit == 0) {
                this.petWeight = Double.valueOf(weight).doubleValue();
            } else {
                this.petWeight = CommonUtil.KgToLb(Double.valueOf(weight).doubleValue());
            }
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
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.activity_pet_register_weight);
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
        post(ApiTools.SAMPLE_API_PET_WEIGHT_RANGE, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.pet.fragment.PetRegisterWeightFragment.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                WeightRangeRsp weightRangeRsp = (WeightRangeRsp) this.gson.fromJson(this.responseResult, WeightRangeRsp.class);
                try {
                    PetRegisterWeightFragment.this.minWeight = weightRangeRsp.getResult().getCriticalWeight()[0];
                    PetRegisterWeightFragment.this.maxWeight = weightRangeRsp.getResult().getCriticalWeight()[1];
                    if (PetRegisterWeightFragment.this.minWeight < 0.001d && PetRegisterWeightFragment.this.maxWeight < 0.001d) {
                        PetRegisterWeightFragment.this.minWeight = 0.0d;
                        if (PetRegisterWeightFragment.this.weightUnit == 1) {
                            PetRegisterWeightFragment.this.maxWeight = 220.0d;
                        } else {
                            PetRegisterWeightFragment.this.maxWeight = 100.0d;
                        }
                    }
                    PetRegisterWeightFragment.this.ideaWeightKg = weightRangeRsp.getResult().getIdealWeight();
                    PetRegisterWeightFragment.this.ideaWeightLb[0] = (float) CommonUtil.KgToLb(PetRegisterWeightFragment.this.ideaWeightKg[0]);
                    PetRegisterWeightFragment.this.ideaWeightLb[1] = (float) CommonUtil.KgToLb(PetRegisterWeightFragment.this.ideaWeightKg[1]);
                    PetRegisterWeightFragment.this.initAllView();
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
        this.tvWeightNum = (TextView) this.contentView.findViewById(R.id.pet_weight_num);
        this.tvWeightUnit = (TextView) this.contentView.findViewById(R.id.pet_weight_unit);
        this.petWeightHint1 = (TextView) this.contentView.findViewById(R.id.pet_weight_hint1);
        this.tvWeightMetric = (TextView) this.contentView.findViewById(R.id.tv_kg);
        this.tvWeightEnglish = (TextView) this.contentView.findViewById(R.id.tv_lb);
        this.tvWeightMetric.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.pet.fragment.PetRegisterWeightFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PetRegisterWeightFragment.this.tvWeightMetric.isSelected()) {
                    return;
                }
                PetRegisterWeightFragment.this.setWeightUnit(0);
            }
        });
        this.tvWeightEnglish.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.pet.fragment.PetRegisterWeightFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PetRegisterWeightFragment.this.tvWeightEnglish.isSelected()) {
                    return;
                }
                PetRegisterWeightFragment.this.setWeightUnit(1);
            }
        });
        SpannableString spannableString = new SpannableString(getString(R.string.Weight_unit_metric));
        int indexForUnit = getIndexForUnit(getString(R.string.Weight_unit_metric));
        spannableString.setSpan(new AbsoluteSizeSpan(15, true), 0, indexForUnit, 33);
        spannableString.setSpan(new AbsoluteSizeSpan(8, true), indexForUnit, spannableString.length(), 33);
        this.tvWeightMetric.setText(spannableString);
        SpannableString spannableString2 = new SpannableString(getString(R.string.Weight_unit_english));
        int indexForUnit2 = getIndexForUnit(getString(R.string.Weight_unit_english));
        spannableString2.setSpan(new AbsoluteSizeSpan(15, true), 0, indexForUnit2, 33);
        spannableString2.setSpan(new AbsoluteSizeSpan(8, true), indexForUnit2, spannableString2.length(), 33);
        this.tvWeightEnglish.setText(spannableString2);
        if (this.weightUnit == 0) {
            this.tvWeightMetric.setSelected(true);
        } else {
            this.tvWeightEnglish.setSelected(true);
        }
        if (!this.isModifyWeight) {
            float[] fArr = this.ideaWeightKg;
            float f = fArr[1];
            if (f > 0.0d) {
                double d = (fArr[0] + f) / 2.0f;
                this.petWeight = d;
                if (this.weightUnit == 1) {
                    this.petWeight = CommonUtil.KgToLb(d);
                }
            }
        }
        refreshUnit();
    }

    private int getIndexForUnit(String str) {
        int length = str.length();
        String[] strArr = new String[length];
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            String strSubstring = str.substring(i, i2);
            strArr[i] = strSubstring;
            if (ChineseToPinyinResource.Field.LEFT_BRACKET.equals(strSubstring)) {
                return i;
            }
            i = i2;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshUnit() {
        int i;
        this.weightUnit = UserInforUtils.getCurrentLoginResult().getSettings().getUnit();
        setWeightNum();
        if (this.weightUnit == 1) {
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
        WeightScaleScrollView weightScaleScrollView = (WeightScaleScrollView) this.contentView.findViewById(R.id.weight_scroll_view);
        weightScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.pet.fragment.PetRegisterWeightFragment.4
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i2) {
                PetRegisterWeightFragment.this.petWeight = ((double) i2) / 10.0d;
                PetRegisterWeightFragment.this.setWeightNum();
            }
        });
        this.petWeight = CommonUtil.doubleToDouble(this.petWeight);
        weightScaleScrollView.setScaleColor(-16777216);
        weightScaleScrollView.setValueRange(i, 0);
        weightScaleScrollView.setValueValidRange((int) (this.minWeight * 10.0d), (int) (this.maxWeight * 10.0d));
        weightScaleScrollView.setInvalidScaleColor(SupportMenu.CATEGORY_MASK);
        weightScaleScrollView.setDefault((int) (this.petWeight * 10.0d), BaseApplication.displayMetrics.widthPixels);
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
        if (this.ideaWeightKg[1] < 0.001d) {
            this.petWeightHint1.setVisibility(8);
        } else {
            double d = this.petWeight;
            if (d >= this.minWeight && d <= this.maxWeight) {
                this.ifWeightTenable = true;
                this.petWeightHint1.setTextColor(-16777216);
                if (this.petSizeId == null) {
                    str = this.petCategoryName;
                } else {
                    str = this.petCategoryName + " (" + this.petSizeName + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                if (this.weightUnit == 1) {
                    str2 = CommonUtil.doubleToDouble(this.ideaWeightLb[0]) + this.unitString + "-" + CommonUtil.doubleToDouble(this.ideaWeightLb[1]) + this.unitString;
                } else {
                    str2 = this.ideaWeightKg[0] + this.unitString + "-" + this.ideaWeightKg[1] + this.unitString;
                }
                this.petWeightHint1.setText(getString(R.string.Pet_weight_limit_hint_format, str, str2));
            } else {
                this.ifWeightTenable = false;
                this.petWeightHint1.setText(R.string.Pet_weight_over_limit_hint);
                this.petWeightHint1.setTextColor(SupportMenu.CATEGORY_MASK);
            }
        }
        OnWeightListner onWeightListner = this.onWeightListner;
        if (onWeightListner != null) {
            onWeightListner.isWeightTenable(this.ifWeightTenable);
        }
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleStringId() {
        return R.string.Pet_weight;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonStringId(boolean z, int i) {
        if (z) {
            return R.string.OK;
        }
        return R.string.Done;
    }

    public void setOnWeightListner(OnWeightListner onWeightListner) {
        this.onWeightListner = onWeightListner;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWeightUnit(final int i) {
        HashMap map = new HashMap();
        map.put("unit", "" + i);
        post(ApiTools.SAMPLE_API_APP_SAVEUNIT, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.pet.fragment.PetRegisterWeightFragment.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetRegisterWeightFragment.this.showShortToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                if (currentLoginResult != null) {
                    currentLoginResult.getSettings().setUnit(i);
                    UserInforUtils.updateLoginResult(currentLoginResult);
                }
                Intent intent = new Intent();
                intent.setAction(Constants.BROADCAST_MSG_UPDATE_DOG_UNIT);
                LocalBroadcastManager.getInstance(PetRegisterWeightFragment.this.getActivity()).sendBroadcast(intent);
                if (PetRegisterWeightFragment.this.getActivity() == null) {
                    return;
                }
                if (i == 0) {
                    PetRegisterWeightFragment.this.tvWeightMetric.setSelected(true);
                    PetRegisterWeightFragment.this.tvWeightEnglish.setSelected(false);
                    PetRegisterWeightFragment.this.maxWeight = 100.0d;
                    PetRegisterWeightFragment petRegisterWeightFragment = PetRegisterWeightFragment.this;
                    petRegisterWeightFragment.petWeight = CommonUtil.LbToKg(petRegisterWeightFragment.petWeight);
                    PetRegisterWeightFragment.this.refreshUnit();
                    return;
                }
                PetRegisterWeightFragment.this.tvWeightMetric.setSelected(false);
                PetRegisterWeightFragment.this.tvWeightEnglish.setSelected(true);
                PetRegisterWeightFragment.this.maxWeight = 220.0d;
                PetRegisterWeightFragment petRegisterWeightFragment2 = PetRegisterWeightFragment.this;
                petRegisterWeightFragment2.petWeight = CommonUtil.KgToLb(petRegisterWeightFragment2.petWeight);
                PetRegisterWeightFragment.this.refreshUnit();
            }
        }, false);
    }
}

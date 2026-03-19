package com.petkit.android.activities.pet.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.WindowUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class PetRegisterTypeFragment extends BaseSwitchFragment {
    private static final int ANIMMOVEDISTANCE = 50;
    private static final int ANIMOFF = 1;
    private static final int ANIMON = 2;
    private static final int ANIMTIME = 400;
    private String avatarPath;
    Button btnSetHeader;
    int catViewLocation;
    int dogContainerLocation;
    int dogViewLocation;
    private EditText etNickName;
    private ImageView ivPetCatHeader;
    private ImageView ivPetCatImg;
    private ImageView ivPetDogHeader;
    private ImageView ivPetDogImg;
    private String petNick;
    private TextView petRegisterPrompt;
    private TextView tvPetCategoryCat;
    private TextView tvPetCategoryDog;
    private View viewContainer;
    private View viewPetCat;
    private View viewPetDog;
    private int petType = 0;
    private int animFlag = 1;
    private boolean isInit = false;
    int[] dogLocation = new int[2];
    int[] catLocation = new int[2];
    int[] petContainerLocation = new int[2];

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonVisibility(int i) {
        return i == 1 ? 8 : 0;
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.bundle = bundle.getBundle(Constants.EXTRA_PET_BUNDLE);
            this.petType = bundle.getInt(Constants.EXTRA_TYPE);
            this.avatarPath = bundle.getString(Constants.EXTRA_AVATAR_PATH);
            this.petNick = bundle.getString(Constants.EXTRA_NICKNAME);
            this.dogLocation = bundle.getIntArray("dogLocation");
            this.catLocation = bundle.getIntArray("catLocation");
            this.petContainerLocation = bundle.getIntArray("petContainerLocation");
            this.dogViewLocation = bundle.getInt("dogViewLocation");
            this.catViewLocation = bundle.getInt("catViewLocation");
            this.dogContainerLocation = bundle.getInt("dogContainerLocation");
            return;
        }
        if (getArguments() != null) {
            this.petType = getArguments().getInt("PET_TYPE");
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setNoTitle();
        setContentView(layoutInflater, R.layout.activity_pet_register_type);
        initView();
    }

    private void initView() {
        this.petRegisterPrompt = (TextView) this.contentView.findViewById(R.id.pet_registe_prompt);
        View viewFindViewById = this.contentView.findViewById(R.id.pet_registe_dog);
        this.viewPetDog = viewFindViewById;
        viewFindViewById.setOnClickListener(this);
        View viewFindViewById2 = this.contentView.findViewById(R.id.pet_registe_cat);
        this.viewPetCat = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this);
        this.viewContainer = this.contentView.findViewById(R.id.pet_category_container);
        this.tvPetCategoryDog = (TextView) this.contentView.findViewById(R.id.pet_category_dog);
        this.tvPetCategoryCat = (TextView) this.contentView.findViewById(R.id.pet_category_cat);
        Button button = (Button) this.contentView.findViewById(R.id.btn_set_head);
        this.btnSetHeader = button;
        button.setOnClickListener(this);
        this.etNickName = (EditText) this.contentView.findViewById(R.id.et_input_pet_name);
        this.ivPetDogHeader = (ImageView) this.contentView.findViewById(R.id.pet_category_dog_header);
        this.ivPetCatHeader = (ImageView) this.contentView.findViewById(R.id.pet_category_cat_header);
        this.ivPetDogImg = (ImageView) this.contentView.findViewById(R.id.pet_dog_header_img);
        this.ivPetCatImg = (ImageView) this.contentView.findViewById(R.id.pet_cat_header_img);
        if (isEmpty(this.petNick)) {
            return;
        }
        this.etNickName.setText(this.petNick);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(Constants.EXTRA_PET_BUNDLE, this.bundle);
        bundle.putInt(Constants.EXTRA_TYPE, this.petType);
        bundle.putString(Constants.EXTRA_AVATAR_PATH, this.avatarPath);
        EditText editText = this.etNickName;
        if (editText != null) {
            bundle.putString(Constants.EXTRA_NICKNAME, editText.getText().toString().trim());
        }
        bundle.putIntArray("dogLocation", this.dogLocation);
        bundle.putIntArray("catLocation", this.catLocation);
        bundle.putIntArray("petContainerLocation", this.petContainerLocation);
        bundle.putInt("dogViewLocation", this.dogViewLocation);
        bundle.putInt("catViewLocation", this.catViewLocation);
        bundle.putInt("dogContainerLocation", this.dogContainerLocation);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.pet_registe_dog) {
            if (this.current_fragment == 1) {
                clickPetDog(400);
                goToNextStep();
                return;
            } else {
                showPopMenu();
                return;
            }
        }
        if (id == R.id.pet_registe_cat) {
            if (this.current_fragment == 1) {
                clickPetCat(400);
                goToNextStep();
                return;
            } else {
                showPopMenu();
                return;
            }
        }
        if (id == R.id.btn_set_head) {
            showPopMenu();
        }
    }

    private void showPopMenu() {
        ((BaseActivity) getActivity()).showCameraAndAblumMenu();
    }

    public void onBackPressed() {
        if (this.animFlag == 2 && this.current_fragment == 2) {
            int i = this.petType;
            if (i == 1) {
                clickPetDogBack();
                this.current_fragment = 1;
            } else if (i == 2) {
                clickPetCatBack();
                this.current_fragment = 1;
            }
        }
    }

    public void uploadHeadPic(String str) {
        this.avatarPath = str;
        if (isEmpty(str)) {
            return;
        }
        int i = this.petType;
        if (i == 1) {
            this.ivPetDogHeader.setVisibility(8);
            this.ivPetDogImg.setVisibility(0);
            ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.avatarPath).imageView(this.ivPetDogImg).errorPic(R.drawable.my_pets_gray).transformation(new GlideCircleTransform(getContext())).build());
        } else if (i == 2) {
            this.ivPetCatHeader.setVisibility(8);
            this.ivPetCatImg.setVisibility(0);
            ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.avatarPath).imageView(this.ivPetCatImg).errorPic(R.drawable.my_pets_gray).transformation(new GlideCircleTransform(getContext())).build());
        }
    }

    public void onWindowFocusChanged(boolean z) {
        View view;
        int[] iArr = this.dogLocation;
        if (iArr[1] == 0 && (view = this.viewPetDog) != null) {
            view.getLocationInWindow(iArr);
            this.dogViewLocation = ((this.dogLocation[1] - ((int) getResources().getDimension(R.dimen.base_titleheight))) - ((int) getResources().getDimension(R.dimen.comment_input_height))) - WindowUtils.getStatusBarHeight(getActivity());
            this.dogContainerLocation = (int) ((r5 + this.viewPetDog.getHeight()) - DeviceUtils.dpToPixel(getActivity(), 30.0f));
            this.viewContainer.getLocationInWindow(this.petContainerLocation);
            int[] iArr2 = this.petContainerLocation;
            iArr2[1] = ((iArr2[1] - ((int) getResources().getDimension(R.dimen.base_titleheight))) - ((int) getResources().getDimension(R.dimen.comment_input_height))) - WindowUtils.getStatusBarHeight(getActivity());
            this.viewPetCat.getLocationInWindow(this.catLocation);
            this.catViewLocation = ((this.catLocation[1] - ((int) getResources().getDimension(R.dimen.base_titleheight))) - ((int) getResources().getDimension(R.dimen.comment_input_height))) - WindowUtils.getStatusBarHeight(getActivity());
        }
        if (this.isInit) {
            return;
        }
        this.isInit = true;
        int i = this.petType;
        if (i == 1) {
            this.current_fragment = 1;
            clickPetDog(50);
        } else if (i == 2) {
            this.current_fragment = 2;
            clickPetCat(50);
        }
    }

    private void clickPetDog(int i) {
        if (this.viewPetDog.getVisibility() == 0 && this.viewPetCat.getVisibility() == 0) {
            this.petType = 1;
            this.animFlag = 2;
            this.tvPetCategoryDog.setVisibility(8);
            this.petRegisterPrompt.setVisibility(8);
            this.viewPetCat.setVisibility(8);
            this.viewContainer.setVisibility(0);
            return;
        }
        if (this.viewPetCat.getVisibility() == 0 || this.viewPetDog.getVisibility() != 0) {
            return;
        }
        showPopMenu();
    }

    private void clickPetCat(int i) {
        if (this.viewPetDog.getVisibility() == 0 && this.viewPetCat.getVisibility() == 0) {
            this.petType = 2;
            this.animFlag = 2;
            this.viewPetDog.setVisibility(8);
            this.petRegisterPrompt.setVisibility(8);
            this.tvPetCategoryCat.setVisibility(8);
            this.viewContainer.setVisibility(0);
            return;
        }
        if (this.viewPetDog.getVisibility() == 0 || this.viewPetCat.getVisibility() != 0) {
            return;
        }
        showPopMenu();
    }

    public void clickPetDogBack() {
        this.viewContainer.setVisibility(8);
        reset();
        this.animFlag = 1;
        this.petRegisterPrompt.setVisibility(0);
        this.tvPetCategoryDog.setVisibility(0);
        this.viewPetCat.setVisibility(0);
    }

    private void clickPetCatBack() {
        this.animFlag = 2;
        this.viewContainer.setVisibility(4);
        reset();
        this.animFlag = 1;
        this.viewPetDog.setVisibility(0);
        this.petRegisterPrompt.setVisibility(0);
        this.tvPetCategoryCat.setVisibility(0);
    }

    private void reset() {
        this.petType = 0;
        this.ivPetDogImg.setVisibility(8);
        this.ivPetCatImg.setVisibility(8);
        this.ivPetDogHeader.setImageResource(R.drawable.dog);
        this.ivPetDogHeader.setVisibility(0);
        this.ivPetCatHeader.setImageResource(R.drawable.cat);
        this.ivPetCatHeader.setVisibility(0);
        this.avatarPath = null;
        this.petNick = null;
        this.etNickName.setText("");
    }

    private void validateName(String str) {
        HashMap map = new HashMap();
        map.put("name", str);
        this.isDataDone = false;
        post(ApiTools.SAMPLE_API_PET_NAME_VALIDATE, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.pet.fragment.PetRegisterTypeFragment.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    PetRegisterTypeFragment.this.goToNextFragment();
                } else {
                    PetRegisterTypeFragment.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetRegisterTypeFragment.this.showShortToast(R.string.Hint_network_failed);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToNextFragment() {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        this.bundle.putInt(Constants.EXTRA_TYPE, this.petType);
        this.params.put("type", Integer.valueOf(this.petType));
        this.params.put("name", this.etNickName.getText().toString().trim());
        if (!TextUtils.isEmpty(this.avatarPath)) {
            this.params.put(Consts.PET_AVATAR, this.avatarPath);
        }
        this.isDataDone = true;
        this.current_fragment = 2;
        this.onSwitchListner.dataDone(this.bundle, this.params);
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public void collectDataToBundle() {
        if (this.current_fragment == 1) {
            this.current_fragment = 2;
        } else if (TextUtils.isEmpty(this.etNickName.getText()) || this.etNickName.getText().toString().trim().length() == 0) {
            showShortToast(R.string.Hint_input_pet_name);
            this.isDataDone = false;
        } else {
            validateName(this.etNickName.getText().toString().trim());
        }
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleStringId() {
        return R.string.Title_pet_add;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonStringId(boolean z, int i) {
        if (i == 1) {
            return 0;
        }
        return R.string.Next;
    }
}

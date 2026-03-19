package com.petkit.android.activities.pet.fragment.create;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.activities.pet.adapter.RegisterPetProgressAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class PetNameFragment extends BaseSwitchFragment implements TextWatcher {
    private AnimatorSet animatorSet;
    private ObjectAnimator animatorX;
    private ObjectAnimator animatorY;
    private String avatarPath;
    private TextView btnNext;
    private ImageView ivClear;
    private ImageView ivPetHeadPortrait;
    private RelativeLayout llPetPhoto;
    private InputMethodManager mInputManager;
    private int nameEditMaxLength;
    private TextView nickPrompt;
    private EditText nicknameEditView;
    private String petNick;
    private RegisterPetProgressAdapter petProgressAdapter;
    private LinearLayout popChangeAvatar;
    private RecyclerView rvTitleProcess;
    private TextView tvNickHint;
    private int type = 0;
    private boolean updateWeight;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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
        super.onCreate(bundle);
        if (bundle != null) {
            this.bundle = bundle.getBundle(Constants.EXTRA_PET_BUNDLE);
            this.avatarPath = bundle.getString(Constants.EXTRA_AVATAR_PATH);
            this.petNick = bundle.getString(Constants.EXTRA_NICKNAME);
            this.updateWeight = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            return;
        }
        if (getArguments() != null) {
            this.updateWeight = getArguments().getBoolean(Constants.EXTRA_BOOLEAN);
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setNoTitle();
        setContentView(layoutInflater, R.layout.fragment_pet_name);
        initView();
    }

    private void initView() {
        this.mInputManager = (InputMethodManager) getActivity().getSystemService("input_method");
        this.llPetPhoto = (RelativeLayout) this.contentView.findViewById(R.id.ll_pet_photo);
        this.ivPetHeadPortrait = (ImageView) this.contentView.findViewById(R.id.iv_pet_head_portrait);
        this.nicknameEditView = (EditText) this.contentView.findViewById(R.id.nickname_edit_view);
        this.tvNickHint = (TextView) this.contentView.findViewById(R.id.tv_nick_hint);
        this.btnNext = (TextView) this.contentView.findViewById(R.id.btn_next);
        this.ivClear = (ImageView) this.contentView.findViewById(R.id.iv_clear);
        this.popChangeAvatar = (LinearLayout) this.contentView.findViewById(R.id.pop_change_avatar);
        this.nickPrompt = (TextView) this.contentView.findViewById(R.id.nick_prompt);
        this.llPetPhoto.setOnClickListener(this);
        this.ivClear.setOnClickListener(this);
        this.btnNext.setOnClickListener(this);
        this.tvNickHint.setOnClickListener(this);
        this.nicknameEditView.addTextChangedListener(this);
        if ("pt_PT".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            this.tvNickHint.setTextSize(1, 12.0f);
        }
        this.nicknameEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.petkit.android.activities.pet.fragment.create.PetNameFragment.1
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    PetNameFragment.this.ivClear.setVisibility(0);
                } else {
                    PetNameFragment.this.ivClear.setVisibility(8);
                }
            }
        });
        if (!isEmpty(this.petNick)) {
            this.nicknameEditView.setText(this.petNick);
        }
        checkBtnState();
        initEditViewEvent();
        this.nickPrompt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.pet.fragment.create.PetNameFragment.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PetNameFragment petNameFragment = PetNameFragment.this;
                petNameFragment.nameEditMaxLength = (BaseApplication.displayMetrics.widthPixels - petNameFragment.nickPrompt.getMeasuredWidth()) - ArmsUtils.dip2px(PetNameFragment.this.getActivity(), 126.0f);
                PetkitLog.d("nameEditMaxLength:" + PetNameFragment.this.nameEditMaxLength);
                PetNameFragment.this.nickPrompt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        this.rvTitleProcess = (RecyclerView) this.contentView.findViewById(R.id.rv_title_process);
        RegisterPetProgressAdapter registerPetProgressAdapter = new RegisterPetProgressAdapter(getActivity(), 6);
        this.petProgressAdapter = registerPetProgressAdapter;
        registerPetProgressAdapter.setCurrentStep(4);
        if (this.updateWeight) {
            RegisterPetProgressAdapter registerPetProgressAdapter2 = new RegisterPetProgressAdapter(getActivity(), 5);
            this.petProgressAdapter = registerPetProgressAdapter2;
            registerPetProgressAdapter2.setCurrentStep(3);
        } else {
            RegisterPetProgressAdapter registerPetProgressAdapter3 = new RegisterPetProgressAdapter(getActivity(), 6);
            this.petProgressAdapter = registerPetProgressAdapter3;
            registerPetProgressAdapter3.setCurrentStep(4);
        }
        this.rvTitleProcess.setAdapter(this.petProgressAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(0);
        this.rvTitleProcess.setLayoutManager(linearLayoutManager);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(Constants.EXTRA_PET_BUNDLE, this.bundle);
        bundle.putString(Constants.EXTRA_AVATAR_PATH, this.avatarPath);
        EditText editText = this.nicknameEditView;
        if (editText != null) {
            bundle.putString(Constants.EXTRA_NICKNAME, editText.getText().toString().trim());
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.isResume) {
            int id = view.getId();
            if (id == R.id.ll_pet_photo) {
                showPopMenu();
                return;
            }
            if (id == R.id.iv_clear) {
                this.nicknameEditView.setText("");
                return;
            }
            if (id == R.id.btn_next) {
                this.type = 1;
                goToNextStep();
            } else if (id == R.id.tv_nick_hint) {
                this.nicknameEditView.setFocusable(true);
                this.nicknameEditView.setFocusableInTouchMode(true);
                this.nicknameEditView.requestFocus();
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this.nicknameEditView, 0);
            }
        }
    }

    private void showPopMenu() {
        ((PetCreateActivity) getActivity()).showCameraAndAblumMenu();
    }

    public void uploadHeadPic(String str) {
        this.avatarPath = str;
        if (isEmpty(str)) {
            return;
        }
        initEditViewEvent();
        stopAvatarAnimator();
        ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.avatarPath).imageView(this.ivPetHeadPortrait).errorPic(R.drawable.my_pets_gray).transformation(new GlideCircleTransform(getContext())).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateName(String str) {
        validateName(str, true);
    }

    private void validateName(String str, boolean z) {
        HashMap map = new HashMap();
        map.put("name", str);
        this.isDataDone = false;
        post(ApiTools.SAMPLE_API_PET_NAME_VALIDATE, map, new AsyncHttpRespHandler(getActivity(), z) { // from class: com.petkit.android.activities.pet.fragment.create.PetNameFragment.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() == null) {
                    if (PetNameFragment.this.type != 0) {
                        if (PetNameFragment.this.type == 1) {
                            PetNameFragment.this.hideSoftInput();
                            PetNameFragment.this.goToNextFragment();
                            return;
                        }
                        return;
                    }
                    PetNameFragment.this.hideSoftInput();
                    PetNameFragment.this.checkBtnState();
                    return;
                }
                PetkitToast.showTopToast(PetNameFragment.this.getActivity(), baseRsp.getError().getMsg(), R.drawable.top_toast_warn_icon, 0);
                PetNameFragment.this.nicknameEditView.setSelection(PetNameFragment.this.nicknameEditView.getText().toString().length());
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetNameFragment.this.showShortToast(R.string.Hint_network_failed);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToNextFragment() {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        this.params.put("name", this.nicknameEditView.getText().toString().trim());
        if (!TextUtils.isEmpty(this.avatarPath)) {
            this.params.put(Consts.PET_AVATAR, this.avatarPath);
        }
        this.isDataDone = true;
        this.current_fragment = 4;
        this.onSwitchListner.dataDone(this.bundle, this.params);
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public void collectDataToBundle() {
        if (TextUtils.isEmpty(this.nicknameEditView.getText()) || this.nicknameEditView.getText().toString().trim().length() == 0) {
            showShortToast(R.string.Hint_input_pet_name);
            this.isDataDone = false;
        } else {
            validateName(this.nicknameEditView.getText().toString().trim());
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        checkBtnState();
        if (!TextUtils.isEmpty(this.nicknameEditView.getText().toString())) {
            this.tvNickHint.setVisibility(8);
            if (TextUtils.isEmpty(this.avatarPath)) {
                startAvatarAnimator();
            }
        } else {
            this.tvNickHint.setVisibility(0);
            stopAvatarAnimator();
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(getResources().getDisplayMetrics().scaledDensity * 15.0f);
        PetkitLog.d("measureText" + textPaint.measureText(this.nicknameEditView.getText().toString()) + ",nameEditMaxLength:" + this.nameEditMaxLength);
        if (!TextUtils.isEmpty(this.nicknameEditView.getText().toString())) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.nicknameEditView.getLayoutParams();
            layoutParams.width = this.nameEditMaxLength;
            this.nicknameEditView.setLayoutParams(layoutParams);
        } else {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.nicknameEditView.getLayoutParams();
            if (layoutParams2.width > 0) {
                layoutParams2.width = ArmsUtils.dip2px(getActivity(), 1.0f);
                this.nicknameEditView.setLayoutParams(layoutParams2);
            }
        }
        if (this.nicknameEditView.getText().toString().length() > 15) {
            this.type = 0;
            validateName(this.nicknameEditView.getText().toString(), false);
            EditText editText = this.nicknameEditView;
            editText.setText(editText.getText().toString().substring(0, 15));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBtnState() {
        if (!TextUtils.isEmpty(this.nicknameEditView.getText().toString())) {
            this.btnNext.setBackgroundResource(R.drawable.selector_solid_new_blue);
            this.btnNext.setClickable(true);
            this.btnNext.setTextColor(-1);
        } else {
            this.btnNext.setBackgroundResource(R.drawable.solid_login_light_blue_bg);
            this.btnNext.setClickable(false);
            this.btnNext.setTextColor(getResources().getColor(R.color.slide_bar_hint_color));
        }
    }

    private void initEditViewEvent() {
        this.nicknameEditView.setImeOptions(5);
        this.nicknameEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.petkit.android.activities.pet.fragment.create.PetNameFragment.4
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 5) {
                    if (!TextUtils.isEmpty(PetNameFragment.this.nicknameEditView.getText().toString())) {
                        PetNameFragment.this.type = 1;
                        PetNameFragment petNameFragment = PetNameFragment.this;
                        petNameFragment.validateName(petNameFragment.nicknameEditView.getText().toString());
                    } else {
                        PetkitToast.showTopToast(PetNameFragment.this.getActivity(), PetNameFragment.this.getActivity().getResources().getString(R.string.Hint_input_pet_name), R.drawable.top_toast_warn_icon, 0);
                    }
                }
                return true;
            }
        });
    }

    private void startAvatarAnimator() {
        AnimatorSet animatorSet;
        this.popChangeAvatar.setVisibility(0);
        if (this.animatorX == null || (animatorSet = this.animatorSet) == null || this.animatorY == null || !animatorSet.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.popChangeAvatar, Key.SCALE_X, 0.9f, 1.0f, 0.9f);
            this.animatorX = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setRepeatMode(2);
            this.animatorX.setRepeatCount(-1);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.popChangeAvatar, Key.SCALE_Y, 0.9f, 1.0f, 0.9f);
            this.animatorY = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setRepeatMode(2);
            this.animatorY.setRepeatCount(-1);
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.animatorSet = animatorSet2;
            animatorSet2.setDuration(1000L);
            this.animatorSet.setStartDelay(300L);
            this.animatorSet.playTogether(this.animatorX, this.animatorY);
            this.animatorSet.start();
        }
    }

    private void stopAvatarAnimator() {
        this.popChangeAvatar.setVisibility(4);
        AnimatorSet animatorSet = this.animatorSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return;
        }
        this.animatorSet.cancel();
        this.animatorSet = null;
        this.animatorX = null;
        this.animatorY = null;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public void setResume(boolean z, BaseSwitchFragment.OnResumeListener onResumeListener) {
        super.setResume(z, onResumeListener);
        if (z) {
            return;
        }
        this.nicknameEditView.clearFocus();
    }

    public void hideSoftInput() {
        try {
            this.mInputManager.hideSoftInputFromWindow(this.nicknameEditView.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        RegisterPetProgressAdapter registerPetProgressAdapter = this.petProgressAdapter;
        if (registerPetProgressAdapter != null) {
            registerPetProgressAdapter.setWidth(configuration.screenWidthDp);
        }
    }
}

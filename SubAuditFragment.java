package com.petkit.android.activities.fragment;

import android.content.Intent;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.petkit.android.activities.cloudservice.ServiceDetailActivity;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.device.AiActivity;
import com.petkit.android.activities.device.adapter.AuditTagAdapter;
import com.petkit.android.activities.device.mode.AwardInfo;
import com.petkit.android.activities.device.mode.MaterialEventInfo;
import com.petkit.android.activities.device.mode.MaterialInfo;
import com.petkit.android.activities.device.mode.MaterialSubRes;
import com.petkit.android.activities.device.mode.MaterialUploadInfo;
import com.petkit.android.activities.device.mode.OnItemClickListener;
import com.petkit.android.activities.device.mode.TagInfo;
import com.petkit.android.activities.device.widget.AiSubSuccessDialog;
import com.petkit.android.activities.device.widget.AwardReceiveDialog;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.fragment.presenter.SubAiPresenter;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.t6.widget.CircleLayout;
import com.petkit.android.activities.petkitBleDevice.utils.AiLinearLayout;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SubAuditFragment extends SubAiFragment {
    private AwardInfo awardInfo;
    private boolean check;
    private ImageView ivCheck;
    private AiLinearLayout llContainerPet;
    private MaterialEventInfo materialEventInfo;
    private MaterialInfo materialInfo;
    private NestedScrollView nsv;
    private final List<Pet> petList = new ArrayList();
    private int progress;
    private CircleLayout rlAct;
    private RelativeLayout rlSub;
    private String ruleStr;
    private RecyclerView rvTag;
    private Pet selectPet;
    private SubAuditListener subAuditListener;
    private AuditTagAdapter tagAdapter;
    private int threshold;
    private TextView tvAuditDate;
    private TextView tvFreeTitle;
    private TextView tvGoService;
    private TextView tvLookPrivacy;
    private TextView tvName;
    private TextView tvProgress;
    private TextView tvRule;
    private TextView tvSubAudit;
    private TextView tvThreshold;
    private boolean unOption;

    public interface SubAuditListener {
        void rule(String str);

        void subSuccess();
    }

    public static /* synthetic */ void lambda$updateSuccess$5(View view) {
    }

    private void submit() {
    }

    @Override // com.petkit.android.activities.fragment.SubAiFragment, com.petkit.android.activities.fragment.ListFragment
    public int initFragmentView() {
        return R.layout.fragment_sub_audit;
    }

    @Override // com.petkit.android.activities.fragment.SubAiFragment, com.petkit.android.activities.fragment.ListFragment
    public void initPrepare(View view) {
        this.nsv = (NestedScrollView) view.findViewById(R.id.nsv_audit);
        this.rlAct = (CircleLayout) view.findViewById(R.id.rl_act);
        this.rlSub = (RelativeLayout) view.findViewById(R.id.rl_sub);
        this.tvSubAudit = (TextView) view.findViewById(R.id.tv_sub_audit);
        this.ivCheck = (ImageView) view.findViewById(R.id.iv_check);
        this.tvFreeTitle = (TextView) view.findViewById(R.id.tv_free_title);
        this.tvName = (TextView) view.findViewById(R.id.tv_name);
        this.tvGoService = (TextView) view.findViewById(R.id.tv_go_service);
        this.tvAuditDate = (TextView) view.findViewById(R.id.tv_audit_date);
        this.tvProgress = (TextView) view.findViewById(R.id.tv_progress);
        this.tvThreshold = (TextView) view.findViewById(R.id.tv_threshold);
        this.llContainerPet = (AiLinearLayout) view.findViewById(R.id.ll_container_pet);
        this.rvTag = (RecyclerView) view.findViewById(R.id.rv_tag);
        this.tvRule = (TextView) view.findViewById(R.id.tv_rule);
        this.tvLookPrivacy = (TextView) view.findViewById(R.id.tv_look_privacy);
    }

    @Override // com.petkit.android.activities.fragment.SubAiFragment, com.petkit.android.activities.fragment.ListFragment
    public void initData() {
        if (getArguments() != null) {
            this.materialEventInfo = (MaterialEventInfo) new Gson().fromJson(getArguments().getString(Constants.EXTRA_MATERIAL_INFO), MaterialEventInfo.class);
        }
        initListener();
        initPrivacy();
        MaterialEventInfo materialEventInfo = this.materialEventInfo;
        if (materialEventInfo != null) {
            ((SubAiPresenter) this.mPresenter).aiMaterialDetail(materialEventInfo.getMaterialId());
        }
        this.nsv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment.1
            public AnonymousClass1() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                SubAuditFragment.this.nsv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (SubAuditFragment.this.nsv.getMeasuredHeight() > DeviceUtils.getScreenWidth(SubAuditFragment.this.getActivity()) - ArmsUtils.dip2px(SubAuditFragment.this.getActivity(), 140.0f)) {
                    SubAuditFragment.this.rlSub.setBackgroundResource(R.drawable.solid_white_corners_16);
                } else {
                    SubAuditFragment.this.rlSub.setBackgroundResource(0);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.fragment.SubAuditFragment$1 */
    public class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass1() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            SubAuditFragment.this.nsv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            if (SubAuditFragment.this.nsv.getMeasuredHeight() > DeviceUtils.getScreenWidth(SubAuditFragment.this.getActivity()) - ArmsUtils.dip2px(SubAuditFragment.this.getActivity(), 140.0f)) {
                SubAuditFragment.this.rlSub.setBackgroundResource(R.drawable.solid_white_corners_16);
            } else {
                SubAuditFragment.this.rlSub.setBackgroundResource(0);
            }
        }
    }

    private void initListener() {
        this.ivCheck.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$0(view);
            }
        });
        this.tvRule.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$1(view);
            }
        });
        this.tvSubAudit.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$2(view);
            }
        });
        this.tvGoService.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$3(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0(View view) {
        if (this.unOption) {
            return;
        }
        if (this.check) {
            this.ivCheck.setImageResource(R.drawable.checkbox_normal);
        } else {
            this.ivCheck.setImageResource(R.drawable.check_box_selected);
        }
        this.check = !this.check;
        checkUi();
    }

    public /* synthetic */ void lambda$initListener$1(View view) {
        SubAuditListener subAuditListener = this.subAuditListener;
        if (subAuditListener != null) {
            subAuditListener.rule(this.ruleStr);
        }
    }

    public /* synthetic */ void lambda$initListener$2(View view) {
        if (checkSub()) {
            submit();
        }
    }

    public /* synthetic */ void lambda$initListener$3(View view) {
        goServiceDetailPage(AiActivity.deviceId.intValue(), AiActivity.deviceType.intValue());
    }

    private void initPrivacy() {
        String string = getResources().getString(R.string.Co_creation_privacy_title);
        String string2 = getResources().getString(R.string.Upload_materal_statement_of_commitment, string);
        int iIndexOf = string2.indexOf(string);
        int length = string.length() + iIndexOf;
        SpannableString spannableString = new SpannableString(string2);
        spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.fragment.SubAuditFragment.2
            public final /* synthetic */ String val$s2;

            public AnonymousClass2(String string3) {
                str = string3;
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(SubAuditFragment.this.getResources().getColor(R.color.new_bind_blue));
                textPaint.setUnderlineText(false);
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(@NonNull View view) {
                SubAuditFragment.this.startWebViewActivity(ApiTools.getWebUrlByKey("Co_Creation_Privacy_Protocol"), str);
            }
        }, iIndexOf, length, 17);
        this.tvLookPrivacy.setText(spannableString);
        this.tvLookPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
        this.tvLookPrivacy.setHighlightColor(getResources().getColor(R.color.transparent));
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.fragment.SubAuditFragment$2 */
    public class AnonymousClass2 extends ClickableSpan {
        public final /* synthetic */ String val$s2;

        public AnonymousClass2(String string3) {
            str = string3;
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(@NonNull TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(SubAuditFragment.this.getResources().getColor(R.color.new_bind_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(@NonNull View view) {
            SubAuditFragment.this.startWebViewActivity(ApiTools.getWebUrlByKey("Co_Creation_Privacy_Protocol"), str);
        }
    }

    private void initPetList() {
        this.petList.clear();
        this.petList.addAll(getPetList());
        Pet pet = new Pet();
        pet.setAvatar("");
        pet.setId("-1");
        pet.setName(getResources().getString(R.string.Other));
        this.petList.add(pet);
        setPetSelectItems(this.petList);
    }

    private List<Pet> getPetList() {
        ArrayList arrayList = new ArrayList();
        Iterator<FamilyInfor> it = FamilyUtils.getInstance().getAllOwnFamilyList(getActivity()).iterator();
        while (it.hasNext()) {
            FamilyInfor next = it.next();
            if (next == null) {
                next = FamilyUtils.getInstance().getCurrentFamilyInfo(getActivity());
            }
            if (UserInforUtils.getCurrentLoginResult().getUser().getDogs(true) != null && next != null) {
                this.petList.addAll(FamilyUtils.getInstance().getPetListThroughFamily(UserInforUtils.getCurrentLoginResult().getUser().getDogs(true), next));
            }
        }
        return arrayList;
    }

    public void setPetSelectItems(List<Pet> list) {
        this.llContainerPet.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            this.llContainerPet.addView(getChildView(list.get(i), i, new OnItemClickListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment.3
                public final /* synthetic */ List val$list;

                @Override // com.petkit.android.activities.device.mode.OnItemClickListener
                public /* synthetic */ void clickItem(TagInfo.TagMode tagMode, int i2) {
                    OnItemClickListener.CC.$default$clickItem(this, tagMode, i2);
                }

                @Override // com.petkit.android.activities.device.mode.OnItemClickListener
                public /* synthetic */ void clickItem(String str, int i2) {
                    OnItemClickListener.CC.$default$clickItem(this, str, i2);
                }

                public AnonymousClass3(List list2) {
                    list = list2;
                }

                @Override // com.petkit.android.activities.device.mode.OnItemClickListener
                public void clickItem(Pet pet, int i2) {
                    if (SubAuditFragment.this.unOption) {
                        return;
                    }
                    if (SubAuditFragment.this.selectPet == null || !SubAuditFragment.this.selectPet.getId().equals(pet.getId())) {
                        SubAuditFragment.this.selectPet = pet;
                    } else {
                        SubAuditFragment.this.selectPet = null;
                    }
                    SubAuditFragment.this.setPetSelectItems(list);
                }
            }));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.fragment.SubAuditFragment$3 */
    public class AnonymousClass3 implements OnItemClickListener {
        public final /* synthetic */ List val$list;

        @Override // com.petkit.android.activities.device.mode.OnItemClickListener
        public /* synthetic */ void clickItem(TagInfo.TagMode tagMode, int i2) {
            OnItemClickListener.CC.$default$clickItem(this, tagMode, i2);
        }

        @Override // com.petkit.android.activities.device.mode.OnItemClickListener
        public /* synthetic */ void clickItem(String str, int i2) {
            OnItemClickListener.CC.$default$clickItem(this, str, i2);
        }

        public AnonymousClass3(List list2) {
            list = list2;
        }

        @Override // com.petkit.android.activities.device.mode.OnItemClickListener
        public void clickItem(Pet pet, int i2) {
            if (SubAuditFragment.this.unOption) {
                return;
            }
            if (SubAuditFragment.this.selectPet == null || !SubAuditFragment.this.selectPet.getId().equals(pet.getId())) {
                SubAuditFragment.this.selectPet = pet;
            } else {
                SubAuditFragment.this.selectPet = null;
            }
            SubAuditFragment.this.setPetSelectItems(list);
        }
    }

    private View getChildView(final Pet pet, final int i, final OnItemClickListener onItemClickListener) {
        View viewInflate = LayoutInflater.from(((ListFragment) this).mContext).inflate(R.layout.layout_audit_select_item, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_str);
        textView.setText(pet.getName());
        textView.setGravity(17);
        Pet pet2 = this.selectPet;
        if (pet2 != null && pet2.getId().equals(pet.getId())) {
            textView.setBackgroundResource(R.drawable.solid_login_blue_bg);
            textView.setTextColor(((ListFragment) this).mContext.getResources().getColor(R.color.white));
        } else {
            textView.setTextColor(((ListFragment) this).mContext.getResources().getColor(R.color.common_text));
            textView.setBackgroundResource(R.drawable.solid_login_light_blue_bg);
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubAuditFragment.lambda$getChildView$4(onItemClickListener, pet, i, view);
            }
        });
        return viewInflate;
    }

    public static /* synthetic */ void lambda$getChildView$4(OnItemClickListener onItemClickListener, Pet pet, int i, View view) {
        if (onItemClickListener != null) {
            onItemClickListener.clickItem(pet, i);
        }
    }

    private void checkUi() {
        if (checkSub()) {
            this.tvSubAudit.setAlpha(1.0f);
        } else {
            this.tvSubAudit.setAlpha(0.4f);
        }
    }

    private boolean checkSub() {
        Pet pet;
        if (this.unOption || !this.check || (pet = this.selectPet) == null || TextUtils.isEmpty(pet.getId())) {
            return false;
        }
        return this.tagAdapter.selectAllTag();
    }

    private SpannableString getAwardStr(AwardInfo awardInfo, int i) {
        String planName = awardInfo.getPlanName();
        String string = getResources().getString(R.string.Upload_materal_award_finished, planName);
        SpannableString spannableString = new SpannableString(string);
        int iIndexOf = string.indexOf(planName);
        spannableString.setSpan(new ForegroundColorSpan(i), iIndexOf, planName.length() + iIndexOf, 18);
        return spannableString;
    }

    @Override // com.petkit.android.activities.fragment.SubAiFragment, com.petkit.android.activities.fragment.contract.SubAiContract.View
    public void getAuditDetailInfo(MaterialUploadInfo materialUploadInfo) {
        if (materialUploadInfo == null) {
            return;
        }
        this.unOption = true;
        this.ivCheck.setImageResource(R.drawable.check_box_selected);
        this.tvSubAudit.setText(getResources().getString(R.string.Upload_materal_uploaded));
        checkUi();
        MaterialInfo materialInfo = new MaterialInfo();
        this.materialInfo = materialInfo;
        materialInfo.setPetId(materialUploadInfo.getPetId());
        this.materialInfo.setPetName(materialUploadInfo.getPetName());
        this.materialInfo.setPetAvatar(materialUploadInfo.getPetAvatar());
        this.materialInfo.setTagIndex(materialUploadInfo.getTagIndex());
        if (!hasPet(materialUploadInfo.getPetId())) {
            Pet pet = new Pet();
            pet.setAvatar(materialUploadInfo.getPetAvatar());
            pet.setId(materialUploadInfo.getPetId());
            pet.setName(materialUploadInfo.getPetName());
            this.petList.add(0, pet);
        }
        Pet pet2 = new Pet();
        this.selectPet = pet2;
        pet2.setId(materialUploadInfo.getPetId());
        this.selectPet.setAvatar(materialUploadInfo.getPetAvatar());
        this.selectPet.setName(materialUploadInfo.getPetName());
        setPetSelectItems(this.petList);
        AuditTagAdapter auditTagAdapter = this.tagAdapter;
        if (auditTagAdapter != null) {
            auditTagAdapter.setSelectIndex(materialUploadInfo.getTagIndex());
            this.tagAdapter.setUnOption(true);
        }
    }

    private boolean hasPet(String str) {
        Iterator<Pet> it = this.petList.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void startWebViewActivity(String str, String str2) {
        launchActivity(WebviewActivity.newIntent(getActivity(), str2, str));
    }

    @Override // com.petkit.android.activities.fragment.SubAiFragment, com.petkit.android.activities.fragment.contract.SubAiContract.View
    public void updateSuccess(MaterialSubRes materialSubRes) {
        SubAuditListener subAuditListener = this.subAuditListener;
        if (subAuditListener != null) {
            subAuditListener.subSuccess();
        }
        this.unOption = true;
        this.tvSubAudit.setText(getResources().getString(R.string.Upload_materal_uploaded));
        this.tvProgress.setText(String.valueOf(this.progress + 1));
        AuditTagAdapter auditTagAdapter = this.tagAdapter;
        if (auditTagAdapter != null) {
            auditTagAdapter.setUnOption(this.unOption);
        }
        int i = this.threshold;
        if (i > 0 && this.progress == i - 1) {
            if (materialSubRes != null && materialSubRes.isRecive()) {
                awardAuditSuccess();
            }
            this.tvGoService.setVisibility(0);
            this.tvProgress.setVisibility(8);
            this.tvThreshold.setVisibility(8);
        } else {
            new AiSubSuccessDialog((AppCompatActivity) getActivity(), new View.OnClickListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubAuditFragment.lambda$updateSuccess$5(view);
                }
            }, getResources().getString(R.string.Submit_success)).show();
        }
        checkUi();
    }

    @Override // com.petkit.android.activities.fragment.SubAiFragment, com.petkit.android.activities.fragment.contract.SubAiContract.View
    public void showActEnd() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        NewIKnowWindow newIKnowWindow = new NewIKnowWindow(getActivity(), (String) null, getResources().getString(R.string.Activity_finished_no_awardreward), (String) null, (String) null);
        if (newIKnowWindow.isShowing()) {
            return;
        }
        newIKnowWindow.show(getActivity().getWindow().getDecorView());
    }

    private void awardAuditSuccess() {
        showAwardDialog();
    }

    private void showAwardDialog() {
        if (this.awardInfo == null) {
            return;
        }
        AwardReceiveDialog awardReceiveDialog = new AwardReceiveDialog(getActivity(), getAwardStr(this.awardInfo, getResources().getColor(R.color.dark_brown)));
        awardReceiveDialog.setAwardListener(new AwardReceiveDialog.AwardListener() { // from class: com.petkit.android.activities.fragment.SubAuditFragment$$ExternalSyntheticLambda2
            @Override // com.petkit.android.activities.device.widget.AwardReceiveDialog.AwardListener
            public final void clickLeft() {
                this.f$0.lambda$showAwardDialog$6();
            }
        });
        awardReceiveDialog.show();
    }

    public /* synthetic */ void lambda$showAwardDialog$6() {
        goServiceDetailPage(AiActivity.deviceId.intValue(), AiActivity.deviceType.intValue());
    }

    private void goServiceDetailPage(long j, int i) {
        Intent intent = new Intent(getActivity(), (Class<?>) ServiceDetailActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_CLOUD_SERVICE_FROM, true);
        startActivity(intent);
    }

    public void setSubAuditListener(SubAuditListener subAuditListener) {
        this.subAuditListener = subAuditListener;
    }
}

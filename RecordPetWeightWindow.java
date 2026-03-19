package com.petkit.android.activities.statistics.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.statistics.utils.StatisticsUtils;
import com.petkit.android.api.http.apiResponse.WeightRangeRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PetSize;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.WeightScaleScrollView;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* JADX INFO: loaded from: classes5.dex */
public class RecordPetWeightWindow extends BasePetkitWindow implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private String birth;
    private Context context;
    private String date;
    private SimpleDateFormat dateFormat;
    private String defaultName;
    private String gender;
    private float[] ideaWeight;
    private ImageView ivPetAvatar;
    private LinearLayout llWeight;
    private float maxWeight;
    private RelativeLayout menuLayout;
    private float minWeight;
    private Pet pet;
    private DatePicker petBirthDate;
    private String petCategoryId;
    private String petCategoryName;
    private String petSizeId;
    private String petSizeName;
    private double petWeight;
    private TextView petWeightNum;
    private TextView petWeightUnit;
    private RecordPetWeightClickListener recordPetWeightClickListener;
    private RelativeLayout rlDate;
    private RelativeLayout rlDatePanel;
    private RelativeLayout rlWeight;
    private RelativeLayout rlWeightPanel;
    private RelativeLayout rootPanel;
    private TextView tvAdd;
    private TextView tvCancel;
    private TextView tvDate;
    private TextView tvDatePrompt;
    private TextView tvPetName;
    private TextView tvTitle;
    private TextView tvWeight;
    private TextView tvWeightPrompt;
    private String unitString;
    private WeightRangeRsp weightRangeRsp;
    private WeightScaleScrollView weightScrollView;
    private int weightUnit;

    public interface RecordPetWeightClickListener {
        void onClick(String str, double d);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecordPetWeightWindow(Context context, Pet pet, WeightRangeRsp weightRangeRsp, RecordPetWeightClickListener recordPetWeightClickListener) {
        super(context, LayoutInflater.from(context).inflate(R.layout.pop_update_pet_weight_window, (ViewGroup) null), true);
        PetSize size = null;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.petWeight = 10.0d;
        this.context = context;
        this.pet = pet;
        this.weightRangeRsp = weightRangeRsp;
        this.recordPetWeightClickListener = recordPetWeightClickListener;
        this.defaultName = context.getResources().getString(R.string.Record_weight);
        setSoftInputMode(32);
        this.menuLayout = (RelativeLayout) getContentView().findViewById(R.id.menu_layout);
        this.tvTitle = (TextView) getContentView().findViewById(R.id.tv_title);
        this.tvCancel = (TextView) getContentView().findViewById(R.id.tv_cancel);
        this.tvAdd = (TextView) getContentView().findViewById(R.id.tv_add);
        this.tvDatePrompt = (TextView) getContentView().findViewById(R.id.tv_date_prompt);
        this.tvDate = (TextView) getContentView().findViewById(R.id.tv_date);
        this.rlDate = (RelativeLayout) getContentView().findViewById(R.id.rl_date);
        this.tvWeightPrompt = (TextView) getContentView().findViewById(R.id.tv_weight_prompt);
        this.tvWeight = (TextView) getContentView().findViewById(R.id.tv_weight);
        this.rlWeight = (RelativeLayout) getContentView().findViewById(R.id.rl_weight);
        this.petBirthDate = (DatePicker) getContentView().findViewById(R.id.pet_birth_date);
        this.rlDatePanel = (RelativeLayout) getContentView().findViewById(R.id.rl_date_panel);
        this.ivPetAvatar = (ImageView) getContentView().findViewById(R.id.iv_pet_avatar);
        this.tvPetName = (TextView) getContentView().findViewById(R.id.tv_pet_name);
        this.petWeightNum = (TextView) getContentView().findViewById(R.id.pet_weight_num);
        this.petWeightUnit = (TextView) getContentView().findViewById(R.id.pet_weight_unit);
        this.llWeight = (LinearLayout) getContentView().findViewById(R.id.ll_weight);
        this.weightScrollView = (WeightScaleScrollView) getContentView().findViewById(R.id.weight_scroll_view);
        this.rlWeightPanel = (RelativeLayout) getContentView().findViewById(R.id.rl_weight_panel);
        this.menuLayout = (RelativeLayout) getContentView().findViewById(R.id.menu_layout);
        this.rootPanel = (RelativeLayout) getContentView().findViewById(R.id.root_panel);
        this.tvDate.setTextColor(context.getResources().getColor(R.color.light_black));
        this.tvWeight.setTextColor(context.getResources().getColor(R.color.w5_main_blue));
        this.tvDate.setText(StatisticsUtils.getDayDate(this.dateFormat.format(Calendar.getInstance().getTime())));
        this.tvCancel.setOnClickListener(this);
        this.tvAdd.setOnClickListener(this);
        this.rlDate.setOnClickListener(this);
        this.rlDatePanel.setOnClickListener(this);
        this.rlWeight.setOnClickListener(this);
        this.rlWeightPanel.setOnClickListener(this);
        ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(pet.getAvatar()).imageView(this.ivPetAvatar).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
        this.tvPetName.setText(pet.getName());
        if (this.pet != null) {
            this.petCategoryId = String.valueOf(pet.getCategory().getId());
            size = pet.getSize();
            this.petCategoryName = String.valueOf(pet.getCategory().getName());
            this.petWeight = Double.valueOf(pet.getWeight()).doubleValue();
            this.birth = pet.getBirth();
            this.gender = String.valueOf(pet.getGender());
        }
        this.petSizeId = String.valueOf(size.getId());
        this.petSizeName = size.getName();
        try {
            this.minWeight = weightRangeRsp.getResult().getCriticalWeight()[0];
            float f = weightRangeRsp.getResult().getCriticalWeight()[1];
            this.maxWeight = f;
            if (this.minWeight < 0.001d && f < 0.001d) {
                this.minWeight = 0.0f;
                if (this.weightUnit == 1) {
                    this.maxWeight = 220.0f;
                } else {
                    this.maxWeight = 100.0f;
                }
            }
            this.weightScrollView.setMinAndMaxWeight(this.minWeight, this.maxWeight);
            this.ideaWeight = weightRangeRsp.getResult().getIdealWeight();
            initAllView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(1);
        int i2 = calendar.get(2);
        int i3 = calendar.get(5);
        this.petBirthDate.setMaxDate(System.currentTimeMillis());
        this.petBirthDate.init(i, i2, i3, this);
        this.date = this.dateFormat.format(calendar.getTime());
        this.tvWeight.setText(String.valueOf(pet.getWeight()) + this.unitString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWeightNum() {
        if (this.petWeight < this.maxWeight) {
            this.tvAdd.setTextColor(this.context.getResources().getColor(R.color.h2_ota_blue));
        } else {
            this.tvAdd.setTextColor(this.context.getResources().getColor(R.color.gray));
        }
        this.tvWeight.setText(String.valueOf(CommonUtil.doubleToDouble(this.petWeight)) + this.unitString);
        this.petWeightNum.setText(String.valueOf(CommonUtil.doubleToDouble(this.petWeight)));
        if (this.ideaWeight[1] < 0.001d) {
            return;
        }
        double d = this.petWeight;
        if (d < this.minWeight || d > this.maxWeight) {
            return;
        }
        if (this.weightUnit == 1) {
            CommonUtil.doubleToDouble(this.ideaWeight[0]);
            CommonUtil.doubleToDouble(this.ideaWeight[1]);
        } else {
            float[] fArr = this.ideaWeight;
            float f = fArr[0];
            float f2 = fArr[1];
        }
    }

    private void initAllView() {
        int i;
        setWeightNum();
        int unit = UserInforUtils.getCurrentLoginResult().getSettings().getUnit();
        this.weightUnit = unit;
        if (unit == 1) {
            this.petWeight = CommonUtil.KgToLb(this.petWeight);
            this.ideaWeight[0] = (float) CommonUtil.KgToLb(r0[0]);
            this.ideaWeight[1] = (float) CommonUtil.KgToLb(r0[1]);
            this.minWeight = (float) CommonUtil.KgToLb(this.minWeight);
            this.maxWeight = (float) CommonUtil.KgToLb(this.maxWeight);
            this.unitString = this.context.getResources().getString(R.string.Unit_lb);
            i = 2205;
        } else {
            this.unitString = this.context.getResources().getString(R.string.Unit_kg);
            i = 1000;
        }
        this.petWeightUnit.setText(this.unitString);
        float f = this.maxWeight;
        if (f * 10.0f > i) {
            i = (int) (((double) (f * 10.0f)) * 1.5d);
        }
        this.weightScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.statistics.widget.RecordPetWeightWindow.1
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i2) {
                RecordPetWeightWindow.this.petWeight = ((double) i2) / 10.0d;
                RecordPetWeightWindow.this.setWeightNum();
            }
        });
        this.petWeight = CommonUtil.doubleToDouble(this.petWeight);
        this.weightScrollView.setScaleColor(-7829368);
        this.weightScrollView.setValueRange(i, 0);
        this.weightScrollView.setValueValidRange((int) (this.minWeight * 10.0f), (int) (this.maxWeight * 10.0f));
        this.weightScrollView.setInvalidScaleColor(SupportMenu.CATEGORY_MASK);
        this.weightScrollView.setDefault((int) (this.petWeight * 10.0d), BaseApplication.displayMetrics.widthPixels);
        this.weightScrollView.setSingleScaleWidth(ArmsUtils.dip2px(this.context, 2.0f));
    }

    public String getDefaultName() {
        return this.defaultName;
    }

    public void setDefaultName(String str) {
        this.defaultName = str;
    }

    @Override // com.petkit.android.widget.windows.BasePetkitWindow, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_cancel) {
            dismiss();
            return;
        }
        if (id == R.id.tv_add) {
            if (this.petWeight < this.maxWeight) {
                dismiss();
                RecordPetWeightClickListener recordPetWeightClickListener = this.recordPetWeightClickListener;
                if (recordPetWeightClickListener != null) {
                    if (this.weightUnit == 1) {
                        recordPetWeightClickListener.onClick(this.date, CommonUtil.LbToKg(this.petWeight));
                        return;
                    } else {
                        recordPetWeightClickListener.onClick(this.date, this.petWeight);
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (id == R.id.rl_date) {
            this.rlDatePanel.setVisibility(0);
            this.rlWeightPanel.setVisibility(8);
            this.tvDate.setTextColor(this.context.getResources().getColor(R.color.w5_main_blue));
            this.tvWeight.setTextColor(this.context.getResources().getColor(R.color.light_black));
            return;
        }
        if (id == R.id.rl_weight) {
            this.rlDatePanel.setVisibility(8);
            this.rlWeightPanel.setVisibility(0);
            this.tvDate.setTextColor(this.context.getResources().getColor(R.color.light_black));
            this.tvWeight.setTextColor(this.context.getResources().getColor(R.color.w5_main_blue));
        }
    }

    public void show(View view) {
        setFocusable(true);
        setOutsideTouchable(true);
        showAtLocation(view, 17, 0, 0);
    }

    @Override // android.widget.DatePicker.OnDateChangedListener
    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
        String strValueOf;
        String strValueOf2;
        int i4 = i2 + 1;
        if (i4 > 9) {
            strValueOf = String.valueOf(i4);
        } else {
            strValueOf = "0" + i4;
        }
        if (i3 <= 9) {
            strValueOf2 = "0" + i3;
        } else {
            strValueOf2 = String.valueOf(i3);
        }
        String str = String.valueOf(i) + strValueOf + strValueOf2;
        this.date = str;
        this.tvDate.setText(StatisticsUtils.getDayDate(str));
    }
}

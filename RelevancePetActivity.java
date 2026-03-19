package com.petkit.android.activities.remind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.PetCategoryView;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class RelevancePetActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llPetContainer;
    private String petId;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_relevance_pet);
        initViews();
        setTitle(getString(R.string.Change_pet));
        if (bundle == null) {
            this.petId = getIntent().getStringExtra(Constants.EXTRA_PET_ID);
        } else {
            this.petId = bundle.getString(Constants.EXTRA_PET_ID);
        }
        List<Pet> dogs = UserInforUtils.getCurrentLoginResult().getUser().getDogs();
        for (int i = 0; i < dogs.size(); i++) {
            final Pet pet = dogs.get(i);
            LayoutInflater.from(this).inflate(R.layout.item_relevance_pet, (ViewGroup) this.llPetContainer, true);
            View childAt = this.llPetContainer.getChildAt(i);
            RelativeLayout relativeLayout = (RelativeLayout) childAt;
            ImageView imageView = (ImageView) ((FrameLayout) relativeLayout.getChildAt(2)).getChildAt(0);
            PetCategoryView petCategoryView = (PetCategoryView) relativeLayout.getChildAt(4);
            TextView textView = (TextView) relativeLayout.getChildAt(5);
            TextView textView2 = (TextView) relativeLayout.getChildAt(3);
            TextView textView3 = (TextView) relativeLayout.getChildAt(6);
            ImageView imageView2 = (ImageView) relativeLayout.getChildAt(9);
            imageView.setVisibility(0);
            petCategoryView.setVisibility(0);
            textView.setVisibility(0);
            textView2.setVisibility(0);
            imageView2.setVisibility(8);
            if (!TextUtils.isEmpty(this.petId) && !this.petId.equals(PetUtils.ALL_DEVICE) && this.petId.equals(pet.getId())) {
                imageView2.setVisibility(0);
            }
            ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(pet.getAvatar()).imageView(imageView).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
            if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() != 1) {
                textView3.setText(CommonUtil.doubleToDouble(Double.valueOf(pet.getWeight()).doubleValue()) + " " + getString(R.string.Unit_kg));
            } else if (pet.getWeight() != null) {
                textView3.setText(CommonUtil.doubleToDouble(CommonUtil.KgToLb(Double.valueOf(pet.getWeight()).doubleValue())) + " " + getString(R.string.Unit_lb));
            } else {
                textView3.setText("10 " + getString(R.string.Unit_lb));
            }
            textView2.setText(pet.getName());
            petCategoryView.setPetCategory(pet);
            textView.setText(CommonUtils.getSimplifyAgeByBirthday(this, pet.getBirth()));
            childAt.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.remind.RelevancePetActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("pet", pet);
                    RelevancePetActivity.this.setResult(-1, intent);
                    RelevancePetActivity.this.finish();
                }
            });
        }
    }

    private void initViews() {
        this.llPetContainer = (LinearLayout) findViewById(R.id.ll_pet_container);
        findViewById(R.id.tv_no_associated_pet).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.tv_no_associated_pet) {
            setResult(-1, new Intent());
            finish();
        }
    }

    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, (Class<?>) RelevancePetActivity.class);
        intent.putExtra(Constants.EXTRA_PET_ID, str);
        return intent;
    }
}

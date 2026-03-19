package com.petkit.android.widget;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.pet.PetAiInstructionActivity;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.activities.pet.PetDetailActivity;
import com.petkit.android.activities.pet.PetDetailModifyActivity;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.model.Pet;
import com.petkit.android.model.User;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class PetListView extends LinearLayout {
    private Activity context;
    private User petOwner;
    private List<Pet> pets;
    private int viewLocation;

    public PetListView(Activity activity, List<Pet> list, User user, int i) {
        super(activity);
        this.context = activity;
        this.pets = list;
        this.viewLocation = i;
        this.petOwner = user;
        initPetsView();
    }

    private View getNonePetView() {
        LinearLayout linearLayout = (LinearLayout) this.context.getLayoutInflater().inflate(R.layout.myself_fragment_pets_view, (ViewGroup) null);
        ((ImageView) linearLayout.findViewById(R.id.dog_avatar)).setImageResource(R.drawable.myselt_fragment_no_pets);
        ((TextView) linearLayout.findViewById(R.id.pet_name)).setVisibility(4);
        ((TextView) linearLayout.findViewById(R.id.pet_type)).setVisibility(8);
        setSinglePetOnClickListner(linearLayout, null);
        return linearLayout;
    }

    private void initPetsView() {
        setOrientation(0);
        this.context.getLayoutInflater().inflate(R.layout.layout_pets_horizontal_view, this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.personal_pets_view_3_or_more);
        List<Pet> list = this.pets;
        if (list != null && list.size() > 0) {
            Iterator<Pet> it = this.pets.iterator();
            while (it.hasNext()) {
                linearLayout.addView(getHorizontalPetView(it.next()));
            }
        }
        if (this.viewLocation == 0) {
            linearLayout.addView(getNonePetView());
        }
    }

    private View getHorizontalPetView(Pet pet) {
        LinearLayout linearLayout = (LinearLayout) this.context.getLayoutInflater().inflate(R.layout.myself_fragment_pets_view, (ViewGroup) null);
        ((BaseApplication) this.context.getApplication()).getAppComponent().imageLoader().loadImage(this.context, GlideImageConfig.builder().url(pet.getAvatar()).imageView((ImageView) linearLayout.findViewById(R.id.dog_avatar)).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this.context)).build());
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.fit_avatar);
        if (pet.getDevice() != null) {
            if (pet.getDevice().getHardware() == 1) {
                imageView.setImageResource(R.drawable.device_p1);
                imageView.setVisibility(0);
            }
            if (pet.getDevice().getHardware() == 2) {
                imageView.setImageResource(R.drawable.device_p2);
                imageView.setVisibility(0);
            }
        }
        ImageView imageView2 = (ImageView) linearLayout.findViewById(R.id.pet_from_type);
        if (pet.getIsRoyalCaninPet() != 0) {
            imageView2.setVisibility(0);
        }
        TextView textView = (TextView) linearLayout.findViewById(R.id.pet_name);
        textView.setText(pet.getName());
        if (this.viewLocation != 0) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, pet.getGender() == 1 ? R.drawable.gender_man : R.drawable.gender_women, 0);
        } else {
            textView.setTextColor(this.context.getResources().getColor(R.color.w5_home_black));
            textView.getPaint().setFakeBoldText(true);
        }
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.pet_type);
        textView2.setText(pet.getCategory().getName());
        if (this.viewLocation == 0) {
            textView2.setVisibility(8);
        }
        setSinglePetOnClickListner(linearLayout, pet);
        return linearLayout;
    }

    private void setSinglePetOnClickListner(View view, final Pet pet) {
        view.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.widget.PetListView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PetListView.this.viewLocation == 0) {
                    Pet pet2 = pet;
                    if (pet2 == null) {
                        Intent intentNewIntent = PetCreateActivity.newIntent(PetListView.this.context, FamilyUtils.getInstance().getCurrentFamilyId(PetListView.this.context), false);
                        if (intentNewIntent != null) {
                            PetListView.this.context.startActivity(intentNewIntent);
                            return;
                        }
                        return;
                    }
                    if (PetListView.this.hasSealedPet(pet2)) {
                        Intent intent = new Intent(PetListView.this.context, (Class<?>) PetAiInstructionActivity.class);
                        intent.putExtra(Constants.EXTRA_DATA, pet);
                        intent.putExtra(Constants.EXTRA_INTEGER, 1);
                        PetListView.this.context.startActivity(intent);
                        return;
                    }
                    Intent intent2 = new Intent(PetListView.this.context, (Class<?>) PetDetailModifyActivity.class);
                    intent2.putExtra(Constants.EXTRA_DOG, pet);
                    PetListView.this.context.startActivity(intent2);
                    return;
                }
                Intent intent3 = new Intent(PetListView.this.context, (Class<?>) PetDetailActivity.class);
                if (PetListView.this.viewLocation == 1 && PetListView.this.petOwner != null) {
                    intent3.putExtra(Constants.NEED_DISPLAY_USER_AVATAR, PetListView.this.petOwner.getId().equals(UserInforUtils.getCurrentUserId(PetListView.this.context)));
                }
                intent3.putExtra(Constants.EXTRA_DOG, pet);
                PetListView.this.context.startActivity(intent3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasSealedPet(Pet pet) {
        return pet != null && pet.getBlocke() == 1;
    }
}

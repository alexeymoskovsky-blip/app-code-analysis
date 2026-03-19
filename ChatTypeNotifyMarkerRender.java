package com.petkit.android.activities.chat.adapter.render;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.adapter.ChatAdapter;
import com.petkit.android.activities.go.GoMarkersActivity;
import com.petkit.android.activities.go.model.GoMarker;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.pet.PetDetailActivity;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.JSONUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ChatTypeNotifyMarkerRender extends ChatTypeNotifyBaseRender {
    public GoMarker mGoMarker;
    public Pet myPet;
    public ImageView petAvatarImageView;
    public TextView petDescTextView;
    public TextView petNameTextView;

    public ChatTypeNotifyMarkerRender(Activity activity, ChatAdapter chatAdapter) {
        super(activity, chatAdapter);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyBaseRender
    public void initExternalView() {
        super.initExternalView();
        this.notifyImg.setImageResource(R.drawable.icon_push_go_marker);
        this.msgLink.setText(this.activity.getString(R.string.Click_to_see_marker) + " >");
        this.layoutContainer.setLayoutResource(R.layout.layout_notify_marker);
        this.layoutContainer.inflate();
        this.petNameTextView = (TextView) this.contentView.findViewById(R.id.pet_name);
        this.petDescTextView = (TextView) this.contentView.findViewById(R.id.pet_desc);
        this.petAvatarImageView = (ImageView) this.contentView.findViewById(R.id.pet_avatar);
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyBaseRender, com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitEvents() {
        super.fitEvents();
        this.msgLink.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyMarkerRender.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ChatTypeNotifyMarkerRender.this.myPet != null) {
                    if (ChatTypeNotifyMarkerRender.this.mGoMarker != null) {
                        Intent intent = new Intent(ChatTypeNotifyMarkerRender.this.activity, (Class<?>) GoMarkersActivity.class);
                        intent.putExtra(GoDataUtils.EXTRA_GO_MARKER, ChatTypeNotifyMarkerRender.this.mGoMarker);
                        intent.putExtra(Constants.EXTRA_DOG, ChatTypeNotifyMarkerRender.this.myPet);
                        ChatTypeNotifyMarkerRender.this.activity.startActivity(intent);
                        return;
                    }
                    return;
                }
                PetkitToast.showShortToast(ChatTypeNotifyMarkerRender.this.activity, R.string.Hint_see_marker_failed_as_pet_is_deleted, R.drawable.toast_failed);
            }
        });
        this.contentView.findViewById(R.id.layout_container_id).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyMarkerRender.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view.getTag() != null) {
                    Intent intent = new Intent(ChatTypeNotifyMarkerRender.this.activity, (Class<?>) PetDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_DOG, (Pet) view.getTag());
                    ChatTypeNotifyMarkerRender.this.activity.startActivity(intent);
                }
            }
        });
    }

    @Override // com.petkit.android.activities.chat.adapter.render.ChatTypeNotifyBaseRender, com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitDatas(int i) {
        super.fitDatas(i);
        try {
            JSONObject jSONObject = JSONUtils.getJSONObject(this.chatAdapter.getItem(i).getPayloadContent());
            Pet pet = (Pet) new Gson().fromJson(JSONUtils.getValue(jSONObject, "pet"), Pet.class);
            if (pet != null) {
                this.contentView.findViewById(R.id.layout_container_id).setTag(pet);
                this.petNameTextView.setText(pet.getName());
                this.petNameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, pet.getGender() == 1 ? R.drawable.gender_man : R.drawable.gender_women, 0);
                this.petDescTextView.setText(pet.getCategory().getName() + "\n" + CommonUtils.getSimplifyAgeByBirthday(this.activity, pet.getBirth()));
                ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(pet.getAvatar()).imageView(this.petAvatarImageView).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
            }
            String value = JSONUtils.getValue(jSONObject, "myPetId");
            if (!CommonUtils.isEmpty(value)) {
                this.myPet = UserInforUtils.getPetById(value);
            } else {
                this.myPet = null;
            }
            this.mGoMarker = (GoMarker) new Gson().fromJson(JSONUtils.getValue(jSONObject, "mark"), GoMarker.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

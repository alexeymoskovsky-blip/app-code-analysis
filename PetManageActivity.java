package com.petkit.android.activities.pet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.pet.adapter.PetsManageAdapter;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PetManageActivity extends BaseActivity {
    private BroadcastReceiver mBroadcastReceiver;
    PetManageDialog petManageDialog;
    RecyclerView recyclerView;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_pet);
        registerBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Pet_manage);
        setTitleRightButton(getResources().getString(R.string.Create), new View.OnClickListener() { // from class: com.petkit.android.activities.pet.PetManageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PetManageActivity.this.startActivity(PetCreateActivity.class);
            }
        });
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final List<Pet> sortPets = UserInforUtils.getSortPets();
        PetsManageAdapter petsManageAdapter = new PetsManageAdapter(this, sortPets);
        petsManageAdapter.setOnItemClickListener(new PetsManageAdapter.OnItemClickListener() { // from class: com.petkit.android.activities.pet.PetManageActivity$$ExternalSyntheticLambda0
            @Override // com.petkit.android.activities.pet.adapter.PetsManageAdapter.OnItemClickListener
            public final void onClick(int i) {
                this.f$0.lambda$setupViews$0(sortPets, i);
            }
        });
        this.recyclerView.setAdapter(petsManageAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PetItemTouchHelperCallback(petsManageAdapter));
        petsManageAdapter.setOnItemDragListener(new PetsManageAdapter.OnItemDragListener() { // from class: com.petkit.android.activities.pet.PetManageActivity$$ExternalSyntheticLambda1
            @Override // com.petkit.android.activities.pet.adapter.PetsManageAdapter.OnItemDragListener
            public final void onDrag(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        });
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupViews$0(List list, int i) {
        if (list == null || list.size() == 0) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) PetDetailModifyActivity.class);
        intent.putExtra(Constants.EXTRA_DOG, (Serializable) list.get(i));
        startActivity(intent);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.title_right_image) {
            startActivity(PetCreateActivity.class);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.pet.PetManageActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(Constants.BROADCAST_MSG_UPDATE_DOG)) {
                    PetManageActivity.this.setupViews();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

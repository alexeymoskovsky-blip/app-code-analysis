package com.petkit.android.activities.pet.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.activities.pet.CustomFoodActivity;
import com.petkit.android.activities.pet.adapter.FoodsListAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.FoodsRsp;
import com.petkit.android.api.http.apiResponse.PrivateFoodsRsp;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.Brand;
import com.petkit.android.model.Food;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PrivateFood;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.utils.sort.FoodSortUtil;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* JADX INFO: loaded from: classes4.dex */
public class PetFoodListFragment extends BasePinnedHeaderListFragment implements View.OnClickListener {
    private FoodsListAdapter adapter;
    private BroadcastReceiver broadcastReceiver;
    private boolean flag;
    private boolean isFeedCalculator;
    private boolean isModifyFood = false;
    private Brand mBrand;
    private Food mFood;
    private ArrayList<Food> mFoods;
    ArrayList<String> mListItems;
    ArrayList<Integer> mListSectionPos;
    private int mLongClickPosition;
    private onPetFoodListSelectListener mSelectListener;
    private String petId;
    private int petType;
    private PrivateFood privateFood;

    public interface onPetFoodListSelectListener {
        void onPetFoodSelect(boolean z);
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment
    public void doSearch(String str) {
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonVisibility(int i) {
        return 0;
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment, com.petkit.android.activities.base.BaseFragment, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.petId = bundle.getString(Constants.EXTRA_DOG_ID);
            this.mBrand = (Brand) bundle.getSerializable(Constants.EXTRA_BRAND);
            this.petType = bundle.getInt(Constants.EXTRA_TYPE);
            this.isFeedCalculator = bundle.getBoolean(Constants.EXTRA_FEED_CALCULATOR_BOOLEAN);
        } else {
            Pet pet = (Pet) getArguments().getSerializable(Constants.EXTRA_DOG);
            this.mBrand = (Brand) getArguments().getSerializable(Constants.EXTRA_BRAND);
            this.isFeedCalculator = getArguments().getBoolean(Constants.EXTRA_FEED_CALCULATOR_BOOLEAN);
            this.petType = getArguments().getInt(Constants.EXTRA_TYPE, 1);
            if (pet != null) {
                this.petId = pet.getId();
                this.petType = pet.getType().getId();
                this.mFood = UserInforUtils.getFoodByPetId(this.petId);
                this.privateFood = pet.getPrivateFood();
            }
            this.isModifyFood = getArguments().getInt(Constants.MODIFY_PET_PROPS, -1) == 8;
        }
        super.onCreate(bundle);
        registerBroadcastReceiver();
        onPetFoodListSelectListener onpetfoodlistselectlistener = this.mSelectListener;
        if (onpetfoodlistselectlistener != null) {
            onpetfoodlistselectlistener.onPetFoodSelect(false);
        }
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment, com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        super.setupViews(layoutInflater);
        setNoTitle();
        setCurrentfragment(8);
        if (!getArguments().getBoolean(Constants.EXTRA_SELECT_FOOD, false) || this.flag) {
            setTitleRightButton(R.string.Save, this);
        } else {
            setTitleRightButton(R.string.Next, this);
        }
        if (this.mBrand.getPrivated() == 2) {
            getPrivateFootList();
            this.mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.petkit.android.activities.pet.fragment.PetFoodListFragment.1
                @Override // android.widget.AdapterView.OnItemLongClickListener
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                    int headerViewsCount = i - ((BasePinnedHeaderListFragment) PetFoodListFragment.this).mListView.getHeaderViewsCount();
                    if (headerViewsCount < 0) {
                        return true;
                    }
                    PetFoodListFragment.this.mLongClickPosition = headerViewsCount;
                    PetFoodListFragment.this.showPopMenu(PetFoodListFragment.this.getString(R.string.Delete));
                    return true;
                }
            });
            View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_private_food_add, (ViewGroup) null);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.pet.fragment.PetFoodListFragment.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Intent intent = new Intent(PetFoodListFragment.this.getActivity(), (Class<?>) CustomFoodActivity.class);
                    intent.putExtra(Constants.EXTRA_DOG, UserInforUtils.getPetById(PetFoodListFragment.this.petId));
                    intent.putExtra(Constants.EXTRA_TYPE, PetFoodListFragment.this.petType);
                    intent.putExtra(Constants.EXTRA_FEED_CALCULATOR_BOOLEAN, PetFoodListFragment.this.isFeedCalculator);
                    PetFoodListFragment.this.startActivity(intent);
                }
            });
            this.mListView.addFooterView(viewInflate);
        } else {
            getFootList();
        }
        setSearchViewVisibility(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_DOG_ID, this.petId);
        bundle.putSerializable(Constants.EXTRA_BRAND, this.mBrand);
        bundle.putInt(Constants.EXTRA_TYPE, this.petType);
        bundle.putBoolean(Constants.EXTRA_FEED_CALCULATOR_BOOLEAN, this.isFeedCalculator);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.adapter.setFoodSelect(i - this.mListView.getHeaderViewsCount());
        onPetFoodListSelectListener onpetfoodlistselectlistener = this.mSelectListener;
        if (onpetfoodlistselectlistener != null) {
            onpetfoodlistselectlistener.onPetFoodSelect(this.adapter.getFoodSelect() != -1);
        }
    }

    private void getFootList() {
        HashMap map = new HashMap();
        map.put("brandId", this.mBrand.getId() + "");
        map.put("petType", String.valueOf(this.petType));
        map.put("petId", this.petId);
        if (!this.isFeedCalculator) {
            map.put("petId", this.petId);
        }
        post(ApiTools.SAMPLE_API_PET_FOOD_PRODUCTS, map, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.pet.fragment.PetFoodListFragment.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                FoodsRsp foodsRsp = (FoodsRsp) this.gson.fromJson(this.responseResult, FoodsRsp.class);
                if (foodsRsp.getError() != null) {
                    PetFoodListFragment.this.setViewState(2);
                    PetFoodListFragment.this.showLongToast(foodsRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    PetFoodListFragment.this.mFoods = foodsRsp.getResult();
                    PetFoodListFragment.this.sortFoodList();
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPrivateFootList() {
        HashMap map = new HashMap();
        map.put("petType", String.valueOf(this.petType));
        post(ApiTools.SAMPLE_API_PET_PRIVATE_FOODS, map, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.pet.fragment.PetFoodListFragment.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                PrivateFoodsRsp privateFoodsRsp = (PrivateFoodsRsp) this.gson.fromJson(this.responseResult, PrivateFoodsRsp.class);
                if (privateFoodsRsp.getError() != null || !PetFoodListFragment.this.isAdded()) {
                    PetFoodListFragment.this.setViewState(2);
                    if (privateFoodsRsp.getError() != null) {
                        PetFoodListFragment.this.showLongToast(privateFoodsRsp.getError().getMsg(), R.drawable.toast_failed);
                        return;
                    }
                    return;
                }
                PetFoodListFragment.this.mFoods = new ArrayList();
                for (int i2 = 0; i2 < privateFoodsRsp.getResult().size(); i2++) {
                    Food food = new Food();
                    PrivateFood privateFood = privateFoodsRsp.getResult().get(i2);
                    food.setIndex(PetFoodListFragment.this.getString(R.string.Pet_food_I_add));
                    food.setId(privateFood.getId());
                    food.setName(privateFood.getName());
                    food.setPrivateFood(true);
                    food.setDetail(CommonUtil.getPrivateFoodAdditionString(PetFoodListFragment.this.getContext(), privateFood));
                    PetFoodListFragment.this.mFoods.add(food);
                }
                PetFoodListFragment.this.sortFoodList();
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sortFoodList() {
        String strSubstring;
        this.mListSectionPos = new ArrayList<>();
        this.mListItems = new ArrayList<>();
        int i = 0;
        while (true) {
            strSubstring = "";
            if (i >= this.mFoods.size()) {
                break;
            }
            if (this.mFoods.get(i).getIndex() != null && this.mFoods.get(i).getIndex().length() != 0) {
                strSubstring = this.mFoods.get(i).getIndex().substring(0, 1);
            }
            if (!isEmpty(strSubstring) && strSubstring.matches("[A-Z]")) {
                this.mFoods.get(i).setIndex(strSubstring);
            } else {
                this.mFoods.get(i).setIndex(MqttTopic.MULTI_LEVEL_WILDCARD);
            }
            i++;
        }
        if (this.mBrand.getPrivated() != 2 && this.mFoods.size() == 0) {
            setViewState(3);
            return;
        }
        Collections.sort(this.mFoods, new FoodSortUtil());
        for (int i2 = 0; i2 < this.mFoods.size(); i2++) {
            String index = this.mFoods.get(i2).getIndex();
            this.mFoods.get(i2).setIndex(index);
            if (!strSubstring.equals(index)) {
                this.mListItems.add(index);
                this.mListSectionPos.add(Integer.valueOf(this.mListItems.indexOf(index)));
                strSubstring = index;
            } else {
                this.mListItems.add(index);
            }
        }
        setList();
    }

    private void setList() {
        if (getActivity() == null) {
            return;
        }
        FoodsListAdapter foodsListAdapter = new FoodsListAdapter(getActivity(), this.mFoods, this.mBrand);
        this.adapter = foodsListAdapter;
        this.mListView.setAdapter((ListAdapter) foodsListAdapter);
        this.mListView.setOnScrollListener(this.adapter);
        setIndexBar(this.mListItems, this.mListSectionPos);
        setViewState(1);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.menu_1) {
            removePrivateFood();
            this.mPopupWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public void collectDataToBundle() {
        FoodsListAdapter foodsListAdapter = this.adapter;
        if (foodsListAdapter == null || foodsListAdapter.getFoodSelect() == -1) {
            showLongToast(R.string.Hint_select_dog_food);
            this.isDataDone = false;
        } else if (this.mFood != null || this.privateFood != null) {
            this.isDataDone = false;
            new AlertDialog.Builder(getActivity()).setCancelable(true).setTitle(R.string.Prompt).setMessage(R.string.Confirm_dog_food_change).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.pet.fragment.PetFoodListFragment.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    PetFoodListFragment.this.confirmData();
                    ((BaseSwitchFragment) PetFoodListFragment.this).onSwitchListner.dataDone(((BaseSwitchFragment) PetFoodListFragment.this).bundle, ((BaseSwitchFragment) PetFoodListFragment.this).params);
                }
            }).setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) null).show();
        } else {
            confirmData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmData() {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        Bundle bundle = this.bundle;
        FoodsListAdapter foodsListAdapter = this.adapter;
        bundle.putSerializable(Constants.EXTRA_PET_FOOD, foodsListAdapter.getItem(foodsListAdapter.getFoodSelect()));
        if (this.mBrand.getPrivated() == 2) {
            HashMap<String, Object> map = this.params;
            FoodsListAdapter foodsListAdapter2 = this.adapter;
            map.put(Consts.PET_PRIVATE_FOOD, Integer.valueOf(foodsListAdapter2.getItem(foodsListAdapter2.getFoodSelect()).getId()));
        } else {
            HashMap<String, Object> map2 = this.params;
            FoodsListAdapter foodsListAdapter3 = this.adapter;
            map2.put("food", Integer.valueOf(foodsListAdapter3.getItem(foodsListAdapter3.getFoodSelect()).getId()));
        }
        this.isDataDone = true;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleStringId() {
        return R.string.Pet_feed_food;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonStringId(boolean z, int i) {
        if (z) {
            return R.string.Select;
        }
        return R.string.Done;
    }

    private void removePrivateFood() {
        if (this.mLongClickPosition == -1) {
            return;
        }
        HashMap map = new HashMap();
        map.put("id", this.adapter.getItem(this.mLongClickPosition).getId() + "");
        post(ApiTools.SAMPLE_API_PET_REMOVEPRIVATEFOOD, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.pet.fragment.PetFoodListFragment.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (PetFoodListFragment.this.getActivity() == null) {
                    return;
                }
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getResult() != null && resultStringRsp.getResult().contains("success")) {
                    PetFoodListFragment.this.adapter.removeItem(PetFoodListFragment.this.mLongClickPosition);
                } else if (resultStringRsp.getError() != null) {
                    PetFoodListFragment.this.showShortToast(resultStringRsp.getError().getMsg());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetFoodListFragment.this.showShortToast(R.string.Hint_network_failed);
            }
        }, false);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.pet.fragment.PetFoodListFragment.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.petkit.android.updateDogFood") && PetFoodListFragment.this.mBrand.getPrivated() == 2) {
                    PetFoodListFragment.this.getPrivateFootList();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.petkit.android.updateDogFood");
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(this.broadcastReceiver, intentFilter);
    }

    private void unRegisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.broadcastReceiver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onPetFoodListSelectListener) {
            this.mSelectListener = (onPetFoodListSelectListener) activity;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.mSelectListener = null;
    }
}

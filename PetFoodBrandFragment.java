package com.petkit.android.activities.pet.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.activities.base.widget.PinnedHeaderListView;
import com.petkit.android.activities.base.widget.impl.IPinnedHeader;
import com.petkit.android.activities.common.fragment.FailureFragment;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.pet.CustomFoodActivity;
import com.petkit.android.activities.pet.event.BrandChooseEvent;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BrandsRsp;
import com.petkit.android.model.Brand;
import com.petkit.android.model.Food;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PrivateFood;
import com.petkit.android.utils.CharacterParser;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class PetFoodBrandFragment extends BasePinnedHeaderListFragment implements View.OnClickListener, FailureFragment.FailureOnClickListener {
    private Brand brand;
    private BroadcastReceiver broadcastReceiver;
    private boolean isFeedCalculator;
    private Food mFood;
    private BrandListAdapter mListAdapter;
    private ArrayList<String> mListItems;
    private ArrayList<Integer> mListPinyinID;
    private ArrayList<String> mListPinyinSections;
    private ArrayList<Integer> mListSectionPos;
    private ArrayList<Brand> mSearchedBrands;
    private View notChooseLayout;
    private Pet pet;
    private int petType;
    private PrivateFood privateFood;
    private PrivateFood selectedPrivateFood;
    private ArrayList<Brand> mFoodBrand = new ArrayList<>();
    private ArrayList<PrivateFood> mPrivateFoods = new ArrayList<>();
    private boolean mSearching = false;
    private mSearchThread mThread = null;
    private boolean isModifyFood = false;

    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() { // from class: com.petkit.android.activities.pet.fragment.PetFoodBrandFragment.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            PetFoodBrandFragment petFoodBrandFragment = PetFoodBrandFragment.this;
            petFoodBrandFragment.setBrandList(petFoodBrandFragment.mSearchedBrands);
        }
    };

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonStringId(boolean z, int i) {
        return 0;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonVisibility(int i) {
        return 8;
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.petType = bundle.getInt(Constants.EXTRA_TYPE);
            this.bundle = bundle.getBundle(Constants.EXTRA_PET_BUNDLE);
            this.pet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
            this.isFeedCalculator = bundle.getBoolean(Constants.EXTRA_FEED_CALCULATOR_BOOLEAN);
        } else {
            Pet pet = (Pet) getArguments().getSerializable(Constants.EXTRA_DOG);
            this.pet = pet;
            if (pet != null) {
                this.petType = pet.getType().getId();
                this.mFood = this.pet.getFood();
                this.privateFood = this.pet.getPrivateFood();
            } else {
                this.petType = getArguments().getInt(Constants.EXTRA_TYPE, 1);
            }
            this.isFeedCalculator = getArguments().getBoolean(Constants.EXTRA_FEED_CALCULATOR_BOOLEAN);
            this.isModifyFood = getArguments().getInt(Constants.MODIFY_PET_PROPS, -1) == 8;
            this.bundle = getArguments();
        }
        registerBroadcastReceiver();
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_TYPE, this.petType);
        bundle.putBundle(Constants.EXTRA_PET_BUNDLE, this.bundle);
        bundle.putBoolean(Constants.EXTRA_FEED_CALCULATOR_BOOLEAN, this.isFeedCalculator);
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment, com.petkit.android.activities.base.BaseFragment
    @SuppressLint({"InflateParams"})
    public void setupViews(LayoutInflater layoutInflater) {
        setNoTitle();
        super.setupViews(layoutInflater);
        setSearchModeView();
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.not_choose_layout, (ViewGroup) null);
        this.notChooseLayout = viewInflate;
        this.mListView.addHeaderView(viewInflate);
        this.notChooseLayout.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.pet.fragment.PetFoodBrandFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PetFoodBrandFragment.this.isModifyFood) {
                    return;
                }
                ((BaseSwitchFragment) PetFoodBrandFragment.this).onSwitchListner.dataDone(null, ((BaseSwitchFragment) PetFoodBrandFragment.this).params);
            }
        });
        BrandListAdapter brandListAdapter = new BrandListAdapter(getActivity(), this.mFoodBrand);
        this.mListAdapter = brandListAdapter;
        this.mListView.setAdapter((ListAdapter) brandListAdapter);
        this.mListView.setOnScrollListener(this.mListAdapter);
        setViewState(0);
        getBrandAll();
        setCurrentfragment(8);
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment
    public void setSearchModeStatus(boolean z) {
        super.setSearchModeStatus(z);
        if (!z) {
            setBrandList(this.mFoodBrand);
        }
        if (z) {
            this.mListView.removeHeaderView(this.notChooseLayout);
        } else {
            this.mListView.removeHeaderView(this.notChooseLayout);
            this.mListView.addHeaderView(this.notChooseLayout);
        }
        BasePinnedHeaderListFragment.OnSwitchSearchModeListenr onSwitchSearchModeListenr = this.onSwitchSearchModeListenr;
        if (onSwitchSearchModeListenr != null) {
            onSwitchSearchModeListenr.isInSearchMode(z);
        }
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public void collectDataToBundle() {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        if (this.brand != null) {
            PrivateFood privateFood = this.selectedPrivateFood;
            if (privateFood != null) {
                this.bundle.putSerializable(Constants.EXTRA_PRIVATE_FOOD, privateFood);
                this.params.put(Consts.PET_PRIVATE_FOOD, Integer.valueOf(this.selectedPrivateFood.getId()));
                if (this.mFood != null || this.privateFood != null) {
                    this.isDataDone = false;
                    new AlertDialog.Builder(getActivity()).setCancelable(true).setTitle(R.string.Prompt).setMessage(R.string.Confirm_dog_food_change).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.pet.fragment.PetFoodBrandFragment.3
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((BaseSwitchFragment) PetFoodBrandFragment.this).isDataDone = true;
                            ((BaseSwitchFragment) PetFoodBrandFragment.this).onSwitchListner.dataDone(((BaseSwitchFragment) PetFoodBrandFragment.this).bundle, ((BaseSwitchFragment) PetFoodBrandFragment.this).params);
                        }
                    }).setNegativeButton(R.string.Cancel, (DialogInterface.OnClickListener) null).show();
                } else {
                    this.isDataDone = true;
                }
                this.bundle.putSerializable(Constants.EXTRA_BRAND, this.brand);
                this.bundle.putBoolean(Constants.EXTRA_SELECT_FOOD, getArguments().getBoolean(Constants.EXTRA_SELECT_FOOD, false));
                this.bundle.putInt(Constants.EXTRA_PET_FOOD, this.brand.getId());
                return;
            }
            this.bundle.putSerializable(Constants.EXTRA_PRIVATE_FOOD, null);
            this.bundle.putSerializable(Constants.EXTRA_BRAND, this.brand);
            this.bundle.putBoolean(Constants.EXTRA_SELECT_FOOD, getArguments().getBoolean(Constants.EXTRA_SELECT_FOOD, false));
            this.bundle.putInt(Constants.EXTRA_PET_FOOD, this.brand.getId());
            this.isDataDone = true;
        }
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleStringId() {
        return R.string.Pet_feed_food;
    }

    public class mSearchThread extends Thread {
        public String SearchStr;

        public mSearchThread(String str) {
            this.SearchStr = str;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            String str = this.SearchStr;
            if (str != null) {
                getSearchedBrandList(str);
            }
        }

        @SuppressLint({"DefaultLocale"})
        public final void getSearchedBrandList(String str) {
            PetFoodBrandFragment.this.setSearchingFlag(true);
            try {
                PetFoodBrandFragment.this.mSearchedBrands = new ArrayList();
                String upperCase = str.toUpperCase();
                for (int i = 0; i < PetFoodBrandFragment.this.mFoodBrand.size() && PetFoodBrandFragment.this.getSearchingFlag(); i++) {
                    if (((Brand) PetFoodBrandFragment.this.mFoodBrand.get(i)).getName().contains(upperCase) || ((Brand) PetFoodBrandFragment.this.mFoodBrand.get(i)).getName().contains(str)) {
                        PetFoodBrandFragment.this.mSearchedBrands.add((Brand) PetFoodBrandFragment.this.mFoodBrand.get(i));
                    }
                }
                if (PetFoodBrandFragment.this.mListPinyinSections != null && PetFoodBrandFragment.this.mListPinyinSections.size() != 0) {
                    for (int i2 = 0; i2 < PetFoodBrandFragment.this.mListPinyinSections.size(); i2++) {
                        if (((String) PetFoodBrandFragment.this.mListPinyinSections.get(i2)).contains(upperCase)) {
                            int iIntValue = ((Integer) PetFoodBrandFragment.this.mListPinyinID.get(i2)).intValue();
                            int i3 = 0;
                            while (true) {
                                if (i3 < PetFoodBrandFragment.this.mSearchedBrands.size()) {
                                    if (iIntValue == ((Brand) PetFoodBrandFragment.this.mSearchedBrands.get(i3)).getId()) {
                                        break;
                                    } else {
                                        i3++;
                                    }
                                } else if (PetFoodBrandFragment.this.getSearchingFlag()) {
                                    PetFoodBrandFragment.this.mSearchedBrands.add((Brand) PetFoodBrandFragment.this.mFoodBrand.get(i2));
                                }
                            }
                        }
                    }
                }
                Message message = new Message();
                message.what = 1;
                PetFoodBrandFragment.this.mHandler.sendMessage(message);
                PetFoodBrandFragment.this.setSearchingFlag(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBrandList(ArrayList<Brand> arrayList) {
        setSearchViewVisibility(true);
        this.mListAdapter.setList(arrayList);
        if (!getSearchMode()) {
            setIndexBar(this.mListItems, this.mListSectionPos);
        } else {
            setIndexBar(null, null);
        }
        if (arrayList.size() > 0) {
            if (getSearchMode()) {
                setSearchModeStatus(true);
            }
            setSearchViewVisibility(true);
            setViewState(1);
            return;
        }
        Pet pet = this.pet;
        if (pet == null) {
            return;
        }
        FeederUtils.getFeederRecordForPetId(pet.getId());
        setFoodBrandEmpty(getString(R.string.Search_pet_food_empty), getString(R.string.Pet_food_add_mine));
        this.contentView.setVisibility(0);
        setViewState(7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sortBrandList() {
        this.mListSectionPos = new ArrayList<>();
        this.mListItems = new ArrayList<>();
        this.mListPinyinID = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPrivateFoods.size(); i++) {
            Brand brand = new Brand();
            PrivateFood privateFood = this.mPrivateFoods.get(i);
            brand.setIndex(getString(R.string.Pet_food_I_add));
            brand.setId(privateFood.getId());
            brand.setName(privateFood.getName());
            brand.setPrivated(1);
            arrayList.add(brand);
        }
        Brand brand2 = new Brand();
        brand2.setIndex(getString(R.string.Pet_food_I_add));
        brand2.setId(99);
        brand2.setName(getString(R.string.Private_food));
        brand2.setPrivated(2);
        arrayList.add(brand2);
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i2 = 0; i2 < this.mFoodBrand.size(); i2++) {
            String strSubstring = this.mFoodBrand.get(i2).getIndex().toUpperCase().substring(0, 1);
            if (!isEmpty(strSubstring) && strSubstring.matches("[A-Z]")) {
                this.mFoodBrand.get(i2).setIndex(strSubstring);
            } else {
                this.mFoodBrand.get(i2).setIndex(MqttTopic.MULTI_LEVEL_WILDCARD);
            }
        }
        Collections.sort(this.mFoodBrand, new BrandsSortUtil());
        this.mFoodBrand.addAll(0, arrayList);
        String str = "";
        for (int i3 = 0; i3 < this.mFoodBrand.size(); i3++) {
            String index = this.mFoodBrand.get(i3).getIndex();
            if (getString(R.string.Pet_food_I_add).equals(index)) {
                index = MqttTopic.MULTI_LEVEL_WILDCARD;
            }
            this.mListItems.add(index);
            arrayList2.add(this.mFoodBrand.get(i3).getName());
            this.mListPinyinID.add(Integer.valueOf(this.mFoodBrand.get(i3).getId()));
            if (!str.equals(index)) {
                this.mListSectionPos.add(Integer.valueOf(this.mListItems.indexOf(index)));
                str = index;
            }
        }
        this.mListPinyinSections = filledData(arrayList2);
        setBrandList(this.mFoodBrand);
    }

    @SuppressLint({"DefaultLocale"})
    private ArrayList<String> filledData(ArrayList<String> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(CharacterParser.getInstance().getSelling(arrayList.get(i)).toUpperCase());
        }
        return arrayList2;
    }

    public class BrandsSortUtil implements Comparator<Brand> {
        public BrandsSortUtil() {
        }

        @Override // java.util.Comparator
        public int compare(Brand brand, Brand brand2) {
            return brand.getIndex().compareTo(brand2.getIndex());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getSearchingFlag() {
        return this.mSearching;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSearchingFlag(boolean z) {
        this.mSearching = z;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount >= 0) {
            if (getSearchMode()) {
                ArrayList<Brand> arrayList = this.mSearchedBrands;
                if (arrayList == null || arrayList.size() < headerViewsCount) {
                    return;
                } else {
                    this.brand = this.mSearchedBrands.get(headerViewsCount);
                }
            } else {
                this.brand = this.mListAdapter.getItem(headerViewsCount);
            }
            EventBus.getDefault().post(new BrandChooseEvent(this.brand.getName()), "brandChoose");
            this.selectedPrivateFood = null;
            setSearchModeStatus(false);
            goToNextStep();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getBrandAll() {
        HashMap map = new HashMap();
        map.put("petType", String.valueOf(this.petType));
        Pet pet = this.pet;
        if (pet != null) {
            map.put("petId", pet.getId());
        }
        post(ApiTools.SAMPLE_API_PET_FOOD_BRANDS, map, new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.pet.fragment.PetFoodBrandFragment.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (PetFoodBrandFragment.this.getActivity() == null) {
                    return;
                }
                BrandsRsp brandsRsp = (BrandsRsp) this.gson.fromJson(this.responseResult, BrandsRsp.class);
                if (brandsRsp.getError() != null) {
                    PetFoodBrandFragment.this.setSearchViewVisibility(false);
                    PetFoodBrandFragment.this.setViewState(2);
                    PetFoodBrandFragment.this.showLongToast(brandsRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    PetFoodBrandFragment.this.mFoodBrand = (ArrayList) brandsRsp.getResult();
                    PetFoodBrandFragment.this.mPrivateFoods = new ArrayList();
                    PetFoodBrandFragment.this.sortBrandList();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetFoodBrandFragment.this.setSearchViewVisibility(false);
                PetFoodBrandFragment.this.setViewState(2);
            }
        }, false);
    }

    public class BrandListAdapter extends BaseAdapter implements AbsListView.OnScrollListener, IPinnedHeader {
        private LayoutInflater inflater;
        private List<Brand> mFoods;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
        }

        public BrandListAdapter(Context context, List<Brand> list) {
            this.inflater = LayoutInflater.from(context);
            this.mFoods = list;
        }

        public void setList(List<Brand> list) {
            this.mFoods = list;
            notifyDataSetChanged();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mFoods.size();
        }

        @Override // android.widget.Adapter
        public Brand getItem(int i) {
            return this.mFoods.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            Brand item = getItem(i);
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                viewHolder = new ViewHolder();
                view = this.inflater.inflate(R.layout.adapter_dog_food_brand, (ViewGroup) null);
                viewHolder.brands_header = (TextView) view.findViewById(R.id.brands_header);
                viewHolder.name = (TextView) view.findViewById(R.id.brand_name);
                viewHolder.icon = (ImageView) view.findViewById(R.id.brand_icon);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (!PetFoodBrandFragment.this.getSearchMode()) {
                if (i == 0 || isPinnedHeaderPushedUp(i - 1, i)) {
                    viewHolder.brands_header.setVisibility(0);
                    viewHolder.brands_header.setText(item.getIndex());
                } else {
                    viewHolder.brands_header.setVisibility(8);
                }
            } else {
                viewHolder.brands_header.setVisibility(8);
            }
            if (item.getPrivated() == 2) {
                viewHolder.icon.setImageResource(R.drawable.icon_private_food);
            } else {
                ((BaseApplication) PetFoodBrandFragment.this.getActivity().getApplication()).getAppComponent().imageLoader().loadImage(PetFoodBrandFragment.this.getContext(), GlideImageConfig.builder().url(item.getIcon()).imageView(viewHolder.icon).errorPic(R.drawable.default_image).build());
            }
            viewHolder.name.setText(item.getName());
            return view;
        }

        public class ViewHolder {
            public TextView brands_header;
            public ImageView icon;
            public TextView name;

            public ViewHolder() {
            }
        }

        @Override // com.petkit.android.activities.base.widget.impl.IPinnedHeader
        public int getPinnedHeaderState(int i) {
            int i2 = 0;
            if (PetFoodBrandFragment.this.getSearchMode()) {
                return 0;
            }
            if (getCount() != 0 && i > 0) {
                i2 = 1;
                if (isPinnedHeaderPushedUp(i - ((BasePinnedHeaderListFragment) PetFoodBrandFragment.this).mListView.getHeaderViewsCount(), (i + 1) - ((BasePinnedHeaderListFragment) PetFoodBrandFragment.this).mListView.getHeaderViewsCount())) {
                    return 2;
                }
            }
            return i2;
        }

        private boolean isPinnedHeaderPushedUp(int i, int i2) {
            return i2 >= ((BasePinnedHeaderListFragment) PetFoodBrandFragment.this).mListView.getHeaderViewsCount() && i2 < getCount() && !getItem(i).getIndex().equals(getItem(i2).getIndex());
        }

        @Override // com.petkit.android.activities.base.widget.impl.IPinnedHeader
        public void configurePinnedHeader(View view, int i) {
            ((TextView) view.findViewById(R.id.pinned_header)).setText(getItem(i - ((BasePinnedHeaderListFragment) PetFoodBrandFragment.this).mListView.getHeaderViewsCount()).getIndex());
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            if (absListView instanceof PinnedHeaderListView) {
                ((PinnedHeaderListView) absListView).configureHeaderView(i);
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        view.getId();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        setViewState(1);
        setSearchModeStatus(false);
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment, com.petkit.android.activities.base.BaseFragment, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        if (z) {
            if (getSearchMode()) {
                Intent intent = new Intent(getActivity(), (Class<?>) CustomFoodActivity.class);
                intent.putExtras(this.bundle);
                startActivity(intent);
                return;
            }
            new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.pet.fragment.PetFoodBrandFragment.5
                @Override // java.lang.Runnable
                public void run() {
                    if (PetFoodBrandFragment.this.mFoodBrand == null || PetFoodBrandFragment.this.mFoodBrand.size() == 0) {
                        PetFoodBrandFragment.this.brand = new Brand();
                        PetFoodBrandFragment.this.brand.setIndex(PetFoodBrandFragment.this.getString(R.string.Pet_food_I_add));
                        PetFoodBrandFragment.this.brand.setId(99);
                        PetFoodBrandFragment.this.brand.setName(PetFoodBrandFragment.this.getString(R.string.Private_food));
                        PetFoodBrandFragment.this.brand.setPrivated(2);
                    } else {
                        PetFoodBrandFragment petFoodBrandFragment = PetFoodBrandFragment.this;
                        petFoodBrandFragment.brand = (Brand) petFoodBrandFragment.mFoodBrand.get(0);
                    }
                    PetFoodBrandFragment.this.goToNextStep();
                }
            }, 400L);
            return;
        }
        if (getSearchMode()) {
            return;
        }
        setSearchViewVisibility(false);
        setViewState(0);
        getBrandAll();
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment
    public void doSearch(String str) {
        if (getSearchMode()) {
            if (str.isEmpty()) {
                setBrandList(this.mFoodBrand);
                setSearchViewVisibility(true);
                setViewState(6);
                return;
            }
            mSearchThread msearchthread = this.mThread;
            if (msearchthread != null && msearchthread.isAlive() && getSearchingFlag()) {
                setSearchingFlag(false);
            }
            this.mThread = new mSearchThread(str);
            new Thread(this.mThread).start();
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.pet.fragment.PetFoodBrandFragment.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.petkit.android.updateDogFood")) {
                    PetFoodBrandFragment.this.getBrandAll();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.petkit.android.updateDogFood");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.broadcastReceiver, intentFilter);
    }

    private void unRegisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.broadcastReceiver);
    }
}

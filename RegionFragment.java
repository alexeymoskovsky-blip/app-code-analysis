package com.petkit.android.activities.registe.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.activities.registe.adapter.RegionListAdapter;
import com.petkit.android.activities.registe.fragment.BaseRegionFragment;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.RegionServicesRsp;
import com.petkit.android.model.Account;
import com.petkit.android.model.PetCategory;
import com.petkit.android.model.PetSize;
import com.petkit.android.model.Region;
import com.petkit.android.model.SerMap;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.utils.sort.RegionSortUtil;
import com.petkit.android.widget.PetSizePopWindow;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class RegionFragment extends BaseRegionFragment implements View.OnClickListener {
    public static final String ACTION_DATA = "ACTION_DATA";
    private PetCategory category;
    private RegionListAdapter mListAdaper;
    ArrayList<String> mListItems;
    ArrayList<Integer> mListSectionPos;
    private SerMap mLoginParams;
    private OnSearchResultClickListener onSearchResultClickListener;
    private PetSizePopWindow petSizePopWindow;
    private Region prefRegion;
    private Region selectRegion;
    private boolean isModifyCategory = false;
    private List<Region> mPetCategoryList = new ArrayList();
    private boolean isChangedRegion = false;
    private boolean isFirstChange = false;

    public interface OnSearchResultClickListener {
        void click();
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonVisibility(int i) {
        return 8;
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.isChangedRegion = getArguments().getBoolean(Constants.EXTRA_BOOLEAN, false);
            this.isFirstChange = getArguments().getBoolean(Constants.EXTRA_INFO_BOOLEAN, false);
            this.mLoginParams = (SerMap) getArguments().getSerializable("params");
            this.selectRegion = (Region) getArguments().getSerializable(Constants.EXTRA_REGION);
        } else {
            this.isChangedRegion = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.isFirstChange = bundle.getBoolean(Constants.EXTRA_INFO_BOOLEAN);
            this.mLoginParams = (SerMap) bundle.getSerializable("params");
            this.selectRegion = (Region) bundle.getSerializable(Constants.EXTRA_REGION);
        }
        this.topViewLayoutRes = R.layout.fragment_region_top;
        this.pinnedHeaderLayoutRes = R.layout.layout_list_pinned_header_pet_create;
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isChangedRegion);
        bundle.putBoolean(Constants.EXTRA_INFO_BOOLEAN, this.isFirstChange);
        bundle.putSerializable("params", this.mLoginParams);
    }

    @Override // com.petkit.android.activities.registe.fragment.BaseRegionFragment, com.petkit.android.activities.base.BaseFragment
    @RequiresApi(api = 23)
    public void setupViews(LayoutInflater layoutInflater) {
        super.setupViews(layoutInflater);
        setNoTitle();
        RegionListAdapter regionListAdapter = new RegionListAdapter(getActivity(), this.mPetCategoryList);
        this.mListAdaper = regionListAdapter;
        this.mListView.setAdapter((ListAdapter) regionListAdapter);
        this.mListView.setOnScrollListener(this.mListAdaper);
        setSearchModeView();
        this.mClearEditText.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.registe.fragment.RegionFragment.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (RegionFragment.this.getSearchMode()) {
                    return false;
                }
                RegionFragment.this.setSearchModeStatus(true);
                return false;
            }
        });
        this.rlPref.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.registe.fragment.RegionFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RegionFragment.this.icon.setVisibility(0);
                RegionFragment.this.mListAdaper.clearSelectIndex();
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.registe.fragment.RegionFragment.3
            @Override // java.lang.Runnable
            public void run() {
                RegionFragment.this.getRegionList();
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRegionList() {
        post(ApiTools.PASSPORT_REGIONSERVERS, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.registe.fragment.RegionFragment.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                String id;
                super.onSuccess(i, headerArr, bArr);
                RegionServicesRsp regionServicesRsp = (RegionServicesRsp) this.gson.fromJson(this.responseResult, RegionServicesRsp.class);
                if (regionServicesRsp.getError() != null) {
                    RegionFragment.this.setViewState(2);
                    RegionFragment.this.showLongToast(regionServicesRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (regionServicesRsp.getResult() != null) {
                    if (regionServicesRsp.getResult().getList() != null && regionServicesRsp.getResult().getList().size() > 0) {
                        RegionFragment.this.setViewState(1);
                        if (!RegionFragment.this.isChangedRegion) {
                            if (RegionFragment.this.selectRegion != null) {
                                id = RegionFragment.this.selectRegion.getId();
                            } else {
                                id = regionServicesRsp.getResult().getPref();
                            }
                        } else {
                            Account account = UserInforUtils.getAccount();
                            id = account != null ? account.getRegion() : null;
                        }
                        if (!RegionFragment.this.isEmpty(id)) {
                            Iterator<Region> it = regionServicesRsp.getResult().getList().iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Region next = it.next();
                                if (next.getId().equals(id)) {
                                    RegionFragment.this.textName.setText(next.getName());
                                    RegionFragment.this.icon.setVisibility(0);
                                    RegionFragment.this.prefRegion = next;
                                    break;
                                }
                            }
                        }
                        RegionFragment.this.sortCategoryList(regionServicesRsp.getResult().getList());
                        return;
                    }
                    RegionFragment.this.setViewState(3);
                    return;
                }
                RegionFragment.this.setViewState(3);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                RegionFragment.this.setViewState(2);
            }
        });
    }

    public Region getPrefRegion() {
        return this.prefRegion;
    }

    public void setOnSearchResultClickListener(OnSearchResultClickListener onSearchResultClickListener) {
        this.onSearchResultClickListener = onSearchResultClickListener;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        collapseSoftInputMethod();
        this.mListAdaper.setSelectIndex(headerViewsCount);
        this.icon.setVisibility(4);
        if (this.mListAdaper.getSearchMode()) {
            this.onSearchResultClickListener.click();
        }
    }

    private void selectPet(int i) {
        if (i == -1) {
            return;
        }
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        collapseSoftInputMethod();
        this.mListAdaper.setSelectIndex(headerViewsCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sortCategoryList(List<Region> list) {
        if (list.size() == 0) {
            return;
        }
        this.mListSectionPos = new ArrayList<>();
        this.mListItems = new ArrayList<>();
        Collections.sort(list, new RegionSortUtil());
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            String strSubstring = list.get(i).getIndex().substring(0, 1);
            if (!str.equals(strSubstring)) {
                this.mListItems.add(strSubstring);
                this.mListSectionPos.add(Integer.valueOf(this.mListItems.indexOf(strSubstring)));
                str = strSubstring;
            } else {
                this.mListItems.add(strSubstring);
            }
        }
        this.mPetCategoryList = list;
        setPetCategoryList(list);
    }

    private void setPetCategoryList(List<Region> list) {
        this.mListAdaper.setList(list);
        if (!getSearchMode()) {
            setIndexBar(this.mListItems, this.mListSectionPos, R.color.login_new_blue);
        } else {
            setIndexBar(null, null);
        }
        this.mListView.setDividerHeight(0);
        if (list.size() > 0) {
            setViewState(1);
            initIndexBarHeight();
        } else {
            setPetCreateStateFailOrEmpty(R.drawable.pet_category_list_empty_icon, R.string.No_available_result, 0, 12);
        }
    }

    private void initIndexBarHeight() {
        this.llSearch.post(new Runnable() { // from class: com.petkit.android.activities.registe.fragment.RegionFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initIndexBarHeight$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initIndexBarHeight$0() {
        this.llSearchHeight = this.llSearch.getHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mIndexBarView.getLayoutParams();
        layoutParams.topMargin = this.mPinnedHeaderView.getMeasuredHeight() + this.llSearchHeight;
        this.mIndexBarView.setLayoutParams(layoutParams);
        this.mListView.setPinnedHeaderView(this.mPinnedHeaderView);
        this.mListView.setIndexBarView(this.mIndexBarView);
    }

    @Override // com.petkit.android.activities.registe.fragment.BaseRegionFragment, com.petkit.android.activities.base.BaseFragment, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        if (getSearchMode()) {
            return;
        }
        setViewState(0);
        getRegionList();
    }

    @Override // com.petkit.android.activities.registe.fragment.BaseRegionFragment
    public void setSearchModeStatus(boolean z) {
        super.setSearchModeStatus(z);
        if (z) {
            RegionListAdapter regionListAdapter = this.mListAdaper;
            if (regionListAdapter != null) {
                regionListAdapter.setSeachMode(true);
                BaseRegionFragment.OnSwitchSearchModeListenr onSwitchSearchModeListenr = this.onSwitchSearchModeListenr;
                if (onSwitchSearchModeListenr != null) {
                    onSwitchSearchModeListenr.isInSearchMode(true);
                }
            }
            this.rlPref.setVisibility(8);
            ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this.mClearEditText, 0);
            return;
        }
        this.rlPref.setVisibility(0);
        this.mListAdaper.setSeachMode(false);
        BaseRegionFragment.OnSwitchSearchModeListenr onSwitchSearchModeListenr2 = this.onSwitchSearchModeListenr;
        if (onSwitchSearchModeListenr2 != null) {
            onSwitchSearchModeListenr2.isInSearchMode(false);
        }
        setPetCategoryList(this.mPetCategoryList);
    }

    @Override // com.petkit.android.activities.registe.fragment.BaseRegionFragment
    public void doSearch(String str) {
        if (str.isEmpty()) {
            setPetCategoryList(this.mPetCategoryList);
            setViewState(6);
        } else {
            setPetCategoryList(getSearchedCategoryList(str));
        }
    }

    @Override // com.petkit.android.activities.registe.fragment.BaseRegionFragment
    public void onScrollY(int i) {
        this.mListAdaper.setListViewAllVisible(i >= this.llSearchHeight);
        if (i >= this.llSearchHeight) {
            this.mPinnedHeaderView.setVisibility(0);
            int i2 = i - this.llSearchHeight;
            int i3 = 0;
            while (true) {
                if (i3 >= this.mListView.getChildCount()) {
                    break;
                }
                View childAt = this.mListView.getChildAt(i3);
                if (i2 < childAt.getBottom() && i2 > childAt.getTop()) {
                    this.mListView.configureHeaderView(i3, i2);
                    break;
                }
                i3++;
            }
        } else {
            this.mPinnedHeaderView.setVisibility(8);
        }
        if (getSearchMode() && this.mSearchModeView.getVisibility() == 0 && i > 0) {
            this.scrollView.scrollBy(0, -i);
        }
    }

    private List<Region> getSearchedCategoryList(String str) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        String lowerCase = str;
        while (i < this.mPetCategoryList.size()) {
            String name = this.mPetCategoryList.get(i).getName();
            if (!TextUtils.isEmpty(name)) {
                String lowerCase2 = name.toLowerCase();
                lowerCase = lowerCase.toLowerCase();
                if (lowerCase2.contains(lowerCase) || lowerCase.contains(lowerCase2)) {
                    arrayList.add(this.mPetCategoryList.get(i));
                }
            }
            i++;
            lowerCase = lowerCase;
        }
        return arrayList;
    }

    private int getSearchedCategoryIndex(String str) {
        int i = 0;
        String lowerCase = str;
        while (i < this.mPetCategoryList.size()) {
            String name = this.mPetCategoryList.get(i).getName();
            if (!TextUtils.isEmpty(name)) {
                String lowerCase2 = name.toLowerCase();
                lowerCase = lowerCase.toLowerCase();
                if (lowerCase2.contains(lowerCase) || lowerCase.contains(lowerCase2)) {
                    return i;
                }
            }
            i++;
            lowerCase = lowerCase;
        }
        return -1;
    }

    private void goToSelectPetSize(PetCategory petCategory) {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        this.bundle.putSerializable(Constants.EXTRA_CATEGORY, petCategory);
        this.params.put(Consts.PET_CATEGORY, Integer.valueOf(petCategory.getId()));
        if (petCategory.getSizes().size() > 1) {
            this.isDataDone = false;
            PetSizePopWindow petSizePopWindow = new PetSizePopWindow(getActivity(), getActivity().getLayoutInflater().inflate(R.layout.layout_pet_detail_size, (ViewGroup) null), petCategory.getSizes(), true);
            this.petSizePopWindow = petSizePopWindow;
            petSizePopWindow.setPetSizeSelectListner(new PetSizePopWindow.PetSizeSelectListner() { // from class: com.petkit.android.activities.registe.fragment.RegionFragment.5
                @Override // com.petkit.android.widget.PetSizePopWindow.PetSizeSelectListner
                public void onConfirmBtnClicked(PetSize petSize) {
                    ((BaseSwitchFragment) RegionFragment.this).params.put("size", Integer.valueOf(petSize.getId()));
                    ((BaseSwitchFragment) RegionFragment.this).bundle.putSerializable(Constants.EXTRA_PETSIZE, petSize);
                    ((BaseSwitchFragment) RegionFragment.this).isDataDone = true;
                    ((BaseSwitchFragment) RegionFragment.this).onSwitchListner.dataDone(((BaseSwitchFragment) RegionFragment.this).bundle, ((BaseSwitchFragment) RegionFragment.this).params);
                }
            });
            this.petSizePopWindow.setBackgroundDrawable(new BitmapDrawable());
            this.petSizePopWindow.showAtLocation(getActivity().getWindow().getDecorView(), 17, 0, 0);
        } else if (petCategory.getSizes().size() == 1) {
            this.bundle.putSerializable(Constants.EXTRA_PETSIZE, petCategory.getSizes().get(0));
            this.params.put("size", Integer.valueOf(petCategory.getSizes().get(0).getId()));
        }
        setSearchModeStatus(false);
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public void collectDataToBundle() {
        this.isDataDone = true;
        goToSelectPetSize(this.category);
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleStringId() {
        return R.string.Pet_info;
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment
    public int getTitleRightButtonStringId(boolean z, int i) {
        if (z) {
            return R.string.OK;
        }
        return R.string.Next;
    }

    public RegionListAdapter getListAdapter() {
        return this.mListAdaper;
    }
}

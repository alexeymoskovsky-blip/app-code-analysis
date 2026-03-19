package com.petkit.android.activities.mate.medical;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.community.PublishActivity;
import com.petkit.android.activities.pet.adapter.PetsListAdapter;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.MedicalListRsp;
import com.petkit.android.model.Medical;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.PetCategoryView;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class MedicalListActivity extends BaseListActivity {
    private Pet curDog;
    private String lastTime = null;
    private List<Pet> mDogs;

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.curDog = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
        } else {
            this.curDog = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
        }
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curDog);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitleRightButton(R.string.Add, this);
        this.mListView.setDividerHeight(0);
        this.mListView.setSelector(R.color.transparent);
        setTopView(R.layout.layout_dog_infor);
        this.mTopView.setBackgroundColor(CommonUtils.getColorById(R.color.white));
        this.mTopView.setOnClickListener(this);
        changeDog();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        MedicalListAdapter medicalListAdapter = new MedicalListAdapter(this, null);
        this.mListAdapter = medicalListAdapter;
        this.mListView.setAdapter((ListAdapter) medicalListAdapter);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
        getList(this.lastTime);
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        getList(null);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount >= 0) {
            Intent intent = new Intent(this, (Class<?>) MedicalDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.EXTRA_MEDICAL, (Medical) this.mListAdapter.getItem(headerViewsCount));
            intent.putExtras(bundle);
            startActivityForResult(intent, 392);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_right_btn || id == R.id.list_empty_btn) {
            Intent intent = new Intent(this, (Class<?>) PublishActivity.class);
            intent.putExtra(Constants.EXTRA_PUBLISH_TYPE, 2);
            intent.putExtra(Constants.EXTRA_DOG, this.curDog);
            startActivityForResult(intent, 254);
            overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_none);
            return;
        }
        if (id == R.id.top_view_id) {
            popDogsSelectWindows();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        if (z) {
            Intent intent = new Intent(this, (Class<?>) PublishActivity.class);
            intent.putExtra(Constants.EXTRA_PUBLISH_TYPE, 2);
            intent.putExtra(Constants.EXTRA_DOG, this.curDog);
            startActivityForResult(intent, 254);
            overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_none);
            return;
        }
        setViewState(0);
        getList(null);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 254) {
                this.lastTime = null;
                this.mPtrFrame.autoRefresh();
                this.mListView.setSelection(0);
            } else if (i == 392) {
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.mate.medical.MedicalListActivity.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ((BaseListActivity) MedicalListActivity.this).mPtrFrame.autoRefresh();
                    }
                }, 300L);
            }
        }
    }

    private void initDogs() {
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult != null) {
            List<Pet> dogs = currentLoginResult.getUser().getDogs();
            this.mDogs = dogs;
            if (this.curDog == null) {
                this.curDog = dogs.get(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDog() {
        setTitleDogView();
        this.lastTime = null;
        setStateLoad();
        getList(this.lastTime);
    }

    private void setTitleDogView() {
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.curDog.getAvatar()).imageView((ImageView) findViewById(R.id.dog_avatar)).errorPic(this.curDog.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        ((TextView) findViewById(R.id.dog_name)).setText(this.curDog.getName());
        ((PetCategoryView) findViewById(R.id.dog_category)).setPetCategory(this.curDog);
        ((TextView) findViewById(R.id.dog_age)).setText(CommonUtils.getSimplifyAgeByBirthday(this, this.curDog.getBirth()));
        findViewById(R.id.dog_bottom_gap_line).setVisibility(8);
    }

    private void getList(final String str) {
        if (this.curDog == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("petId", this.curDog.getId());
        map.put("limit", String.valueOf(20));
        map.put("lastKey", str);
        post(ApiTools.SAMPLE_API_MEDICALRECORD_TIMELINE, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.mate.medical.MedicalListActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                MedicalListActivity.this.refreshComplete();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                MedicalListRsp medicalListRsp = (MedicalListRsp) this.gson.fromJson(this.responseResult, MedicalListRsp.class);
                if (medicalListRsp.getError() != null) {
                    MedicalListActivity medicalListActivity = MedicalListActivity.this;
                    if (medicalListActivity.isEmpty(medicalListActivity.lastTime)) {
                        MedicalListActivity.this.setStateFailOrEmpty();
                    }
                    MedicalListActivity.this.showLongToast(medicalListRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (medicalListRsp.getResult() != null) {
                    MedicalListActivity.this.setStateNormal();
                    if (MedicalListActivity.this.isEmpty(str)) {
                        ((BaseListActivity) MedicalListActivity.this).mListAdapter.setList(medicalListRsp.getResult().getList());
                        medicalListRsp.getResult().getList().size();
                    } else {
                        ((BaseListActivity) MedicalListActivity.this).mListAdapter.addList(medicalListRsp.getResult().getList());
                    }
                    MedicalListActivity.this.lastTime = medicalListRsp.getResult().getLastKey();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                MedicalListActivity.this.setViewState(2);
            }
        }, false);
    }

    public class MedicalListAdapter extends LoadMoreBaseAdapter {
        private int imgHeight;

        public MedicalListAdapter(Activity activity, List<Medical> list) {
            super(activity, list);
            this.imgHeight = 0;
            this.imgHeight = (int) (BaseApplication.displayMetrics.widthPixels - ((DeviceUtils.dpToPixel(MedicalListActivity.this, 448.0f) * 2.0f) / 5.0f));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
        @SuppressLint({"InflateParams"})
        public View getContentView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(MedicalListActivity.this).inflate(R.layout.adapter_medical_list, (ViewGroup) null);
                viewHolder.time = (TextView) view.findViewById(R.id.medical_time);
                viewHolder.detail = (TextView) view.findViewById(R.id.medical_detail);
                viewHolder.img = (ImageView) view.findViewById(R.id.medical_image);
                viewHolder.tagBottom = view.findViewById(R.id.medical_tag_bottom);
                viewHolder.tagTop = view.findViewById(R.id.medical_tag_top);
                if (this.imgHeight > 0) {
                    viewHolder.img.setLayoutParams(new LinearLayout.LayoutParams(-1, this.imgHeight));
                }
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            Medical medical = (Medical) getItem(i);
            viewHolder.time.setText(medical.getHappendAt().length() > 10 ? medical.getHappendAt().substring(0, 10) : medical.getHappendAt());
            viewHolder.detail.setText(medical.getDetail());
            viewHolder.tagTop.setBackgroundColor(CommonUtils.getColorById(i == 0 ? R.color.transparent : R.color.medical));
            viewHolder.tagBottom.setBackgroundColor(CommonUtils.getColorById(i + 1 == getCount() ? R.color.transparent : R.color.medical));
            if (medical.getImgs() != null && medical.getImgs().size() > 0) {
                viewHolder.img.setVisibility(0);
                ((BaseApplication) MedicalListActivity.this.getApplication()).getAppComponent().imageLoader().loadImage(MedicalListActivity.this, GlideImageConfig.builder().url(medical.getImgs().get(0)).imageView(viewHolder.img).errorPic(R.drawable.default_image_middle).build());
            } else {
                viewHolder.img.setVisibility(8);
            }
            return view;
        }

        public class ViewHolder {
            public TextView detail;
            public ImageView img;
            public View tagBottom;
            public View tagTop;
            public TextView time;

            public ViewHolder() {
            }
        }
    }

    private void popDogsSelectWindows() {
        final View viewInflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.layout_pop_list, (ViewGroup) null);
        if (viewInflate == null) {
            return;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final PopupWindow popupWindow = new PopupWindow(viewInflate, displayMetrics.widthPixels, -2, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 1.0f;
        getWindow().setAttributes(attributes);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.mate.medical.MedicalListActivity.3
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                WindowManager.LayoutParams attributes2 = MedicalListActivity.this.getWindow().getAttributes();
                attributes2.alpha = 1.0f;
                MedicalListActivity.this.getWindow().setAttributes(attributes2);
            }
        });
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            findViewById(R.id.top_view_id).post(new Runnable() { // from class: com.petkit.android.activities.mate.medical.MedicalListActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    ListView listView = (ListView) viewInflate.findViewById(R.id.pop_list);
                    popupWindow.showAtLocation(MedicalListActivity.this.findViewById(R.id.top_view_id), 48, 0, (int) (MedicalListActivity.this.getResources().getDimension(R.dimen.base_titleheight) + DeviceUtils.dpToPixel(MedicalListActivity.this, 25.0f)));
                    popupWindow.setAnimationStyle(R.style.PopupAnimation);
                    listView.setDividerHeight(0);
                    MedicalListActivity medicalListActivity = MedicalListActivity.this;
                    listView.setAdapter((ListAdapter) new PetsListAdapter(medicalListActivity, medicalListActivity.mDogs, 1));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.petkit.android.activities.mate.medical.MedicalListActivity.4.1
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            MedicalListActivity medicalListActivity2 = MedicalListActivity.this;
                            medicalListActivity2.curDog = (Pet) medicalListActivity2.mDogs.get(i);
                            MedicalListActivity.this.mDogs.remove(i);
                            MedicalListActivity.this.mDogs.add(0, MedicalListActivity.this.curDog);
                            MedicalListActivity.this.changeDog();
                            popupWindow.dismiss();
                        }
                    });
                }
            });
        }
    }
}

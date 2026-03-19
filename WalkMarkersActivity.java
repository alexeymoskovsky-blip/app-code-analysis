package com.petkit.android.activities.walkdog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.go.adapter.PetsListSelectAdapter;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.activities.walkdog.adapter.MarkerListAdapter;
import com.petkit.android.activities.walkdog.model.WalkMarker;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.activities.walkdog.widget.MarkerListCardView;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.WalkMarkerRsp;
import com.petkit.android.api.http.apiResponse.WalkMarkersRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes6.dex */
public class WalkMarkersActivity extends BaseActivity implements GoogleMap.OnCameraMoveListener {
    private GoogleMap googlemap;
    private BroadcastReceiver mBroadcastReceiver;
    private LinearLayout mContainerLayout;
    private Marker mCurHightlightMarker;
    private WalkMarker mCurMarker;
    private Pet mCurPet;
    private HashMap<WalkMarker, Marker> mDisplayMarkers;
    private MapView mGoogleMapView;
    private LatLng mLastCenterLatLng;
    private MarkerListAdapter mMarkerListAdapter;
    private ViewPager mMarkersViewPager;
    private MarkerListCardView markerListCardWindow;
    private View petView;
    private PopupWindow popupWindow;
    private boolean isAutoMoveMap = false;
    private boolean isAutoDisplayCard = false;
    private float mPetViewDefaultPosX = 0.0f;
    private float mPetViewMoveDistance = 0.0f;

    @Override // com.google.android.gms.maps.GoogleMap.OnCameraMoveListener
    public void onCameraMove() {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mCurPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
            this.mCurMarker = (WalkMarker) bundle.getSerializable(WalkDataUtils.EXTRA_WALK_MARKER);
        } else {
            this.mCurPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
            this.mCurMarker = (WalkMarker) getIntent().getSerializableExtra(WalkDataUtils.EXTRA_WALK_MARKER);
        }
        setContentView(R.layout.activity_go_markers);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.My_marks);
        this.mContainerLayout = (LinearLayout) findViewById(R.id.map_container);
        this.mDisplayMarkers = new HashMap<>();
        initMapView();
        initMarkerCardView();
        initViewState();
        showPetView();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        MapView mapView = this.mGoogleMapView;
        if (mapView != null) {
            try {
                mapView.onResume();
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        MapView mapView = this.mGoogleMapView;
        if (mapView != null) {
            try {
                mapView.onPause();
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_DOG, this.mCurPet);
        bundle.putSerializable(GoDataUtils.EXTRA_GO_MARKER, this.mCurMarker);
        MapView mapView = this.mGoogleMapView;
        if (mapView != null) {
            try {
                mapView.onSaveInstanceState(bundle);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        MapView mapView = this.mGoogleMapView;
        if (mapView != null) {
            try {
                mapView.onDestroy();
            } catch (Exception unused) {
            }
        }
        hidePetView();
        MarkerListCardView markerListCardView = this.markerListCardWindow;
        if (markerListCardView != null && markerListCardView.isShowing()) {
            this.markerListCardWindow.dismiss();
        }
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.title_right_btn) {
            hidePetView();
            return;
        }
        if (id == R.id.pet_view) {
            showPetView();
            showPetListWindow();
        } else if (id == R.id.go_intro_text_2) {
            if (this.mCurPet == null) {
                startActivity(PetCreateActivity.class, false);
            } else {
                startActivity(WebviewActivity.newIntent(this, "", ApiTools.getWebUrlByKey("go_markfaq")));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initViewState() {
        this.petView = findViewById(R.id.pet_view);
        initParams();
        if (this.mCurPet == null) {
            initPetEmptyView();
            return;
        }
        if (!DeviceCenterUtils.getDeviceCenter().isHasMarks()) {
            initPetView();
            initMarkersEmptyView();
            return;
        }
        initPetView();
        getMarks(this.mCurMarker != null ? new LatLng(this.mCurMarker.getLoc().get(1).doubleValue(), this.mCurMarker.getLoc().get(0).doubleValue()) : null, true);
        WalkMarker walkMarker = this.mCurMarker;
        if (walkMarker != null) {
            getMark(walkMarker);
        }
    }

    private void initParams() {
        GoogleMap googleMap = this.googlemap;
        if (googleMap != null) {
            googleMap.clear();
        }
        this.mDisplayMarkers.clear();
        this.mLastCenterLatLng = null;
    }

    private void initPetView() {
        this.petView.setVisibility(0);
        findViewById(R.id.go_intro_view).setVisibility(8);
        this.petView.setOnClickListener(this);
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.mCurPet.getAvatar()).imageView((ImageView) findViewById(R.id.pet_avatar)).errorPic(this.mCurPet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        ((ImageView) findViewById(R.id.pet_indictor)).setImageResource(R.drawable.indictor_blue_bottom);
    }

    private void initPetEmptyView() {
        this.petView.setVisibility(8);
        findViewById(R.id.go_intro_view).setVisibility(0);
        ((ImageView) findViewById(R.id.go_intro_icon)).setImageResource(R.drawable.icon_marker_pet_null);
        ((TextView) findViewById(R.id.go_intro_text)).setText(R.string.Notice_add_pet_to_use_marker);
        TextView textView = (TextView) findViewById(R.id.go_intro_text_2);
        textView.setText(getString(R.string.Title_pet_add) + ">");
        textView.setOnClickListener(this);
    }

    private void initMarkersEmptyView() {
        findViewById(R.id.go_intro_view).setVisibility(0);
        ((ImageView) findViewById(R.id.go_intro_icon)).setImageResource(R.drawable.icon_marker_null);
        ((TextView) findViewById(R.id.go_intro_text)).setText(R.string.Notice_markers_is_empty);
        TextView textView = (TextView) findViewById(R.id.go_intro_text_2);
        textView.setText(R.string.How_to_use);
        textView.setOnClickListener(this);
    }

    private void getMarks(final LatLng latLng, final boolean z) {
        LatLng latLng2 = this.mLastCenterLatLng;
        if ((latLng2 == null || !latLng2.equals(latLng)) && this.mCurPet != null) {
            HashMap map = new HashMap();
            map.put("petId", this.mCurPet.getId());
            if (latLng != null) {
                map.put(TtmlNode.CENTER, latLng.latitude + ChineseToPinyinResource.Field.COMMA + latLng.longitude);
            }
            post(ApiTools.SAMPLE_API_WALK_PET_MY_MARKS, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.1
                @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                    super.onSuccess(i, headerArr, bArr);
                    WalkMarkersRsp walkMarkersRsp = (WalkMarkersRsp) this.gson.fromJson(this.responseResult, WalkMarkersRsp.class);
                    if (walkMarkersRsp.getResult() != null) {
                        if (walkMarkersRsp.getResult().getList() != null) {
                            WalkMarkersActivity.this.setMarkerList(walkMarkersRsp.getResult().getList(), z);
                        }
                        WalkMarkersActivity.this.mLastCenterLatLng = latLng;
                    }
                }
            }, false);
        }
    }

    private void getMark(WalkMarker walkMarker) {
        HashMap map = new HashMap();
        map.put("id", walkMarker.getId());
        post(ApiTools.SAMPLE_API_WALK_PET_MARK, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                WalkMarkerRsp walkMarkerRsp = (WalkMarkerRsp) this.gson.fromJson(this.responseResult, WalkMarkerRsp.class);
                if (walkMarkerRsp.getResult() != null) {
                    if (!WalkMarkersActivity.this.mDisplayMarkers.containsKey(walkMarkerRsp.getResult())) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(walkMarkerRsp.getResult());
                        WalkMarkersActivity.this.setMarkerList(arrayList, true);
                    }
                    WalkMarkersActivity.this.isAutoDisplayCard = true;
                    WalkMarkersActivity walkMarkersActivity = WalkMarkersActivity.this;
                    walkMarkersActivity.setMarkerHightlight((Marker) walkMarkersActivity.mDisplayMarkers.get(walkMarkerRsp.getResult()));
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            WalkMarkersActivity.this.isAutoDisplayCard = false;
                        }
                    }, 500L);
                    return;
                }
                WalkMarkersActivity.this.showLongToast(R.string.Hint_marker_is_disappeared_follow_time, R.drawable.toast_failed);
            }
        }, false);
    }

    private void showPetView() {
        float f = this.mPetViewDefaultPosX;
        if (f == 0.0f) {
            new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    WalkMarkersActivity walkMarkersActivity = WalkMarkersActivity.this;
                    walkMarkersActivity.mPetViewDefaultPosX = walkMarkersActivity.petView.getX();
                    WalkMarkersActivity walkMarkersActivity2 = WalkMarkersActivity.this;
                    walkMarkersActivity2.mPetViewMoveDistance = DeviceUtils.dpToPixel(walkMarkersActivity2, 34.0f);
                }
            }, 30L);
            return;
        }
        if (f != this.petView.getX()) {
            View view = this.petView;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "x", view.getX(), this.mPetViewDefaultPosX);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(400L);
            animatorSet.playTogether(objectAnimatorOfFloat);
            animatorSet.start();
        }
        hideMarkerCardView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hidePetView() {
        if (this.mPetViewDefaultPosX == this.petView.getX()) {
            View view = this.petView;
            float f = this.mPetViewDefaultPosX;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "x", f, this.mPetViewMoveDistance + f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(400L);
            animatorSet.playTogether(objectAnimatorOfFloat);
            animatorSet.start();
        }
    }

    private void initMarkerCardView() {
        if (this.markerListCardWindow == null) {
            View viewInflate = getLayoutInflater().inflate(R.layout.pop_marker_list_card, (ViewGroup) null);
            this.mMarkersViewPager = (ViewPager) viewInflate.findViewById(R.id.marker_list);
            MarkerListAdapter markerListAdapter = new MarkerListAdapter((int) (BaseApplication.displayMetrics.widthPixels - DeviceUtils.dpToPixel(this, 100.0f)));
            this.mMarkerListAdapter = markerListAdapter;
            this.mMarkersViewPager.setAdapter(markerListAdapter);
            this.mMarkersViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.4
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    WalkMarker walkMarker = WalkMarkersActivity.this.mMarkerListAdapter.getMarkers().get(i);
                    WalkMarkersActivity walkMarkersActivity = WalkMarkersActivity.this;
                    walkMarkersActivity.setMarkerHightlight((Marker) walkMarkersActivity.mDisplayMarkers.get(walkMarker));
                }
            });
            MarkerListCardView markerListCardView = new MarkerListCardView(this, viewInflate, false);
            this.markerListCardWindow = markerListCardView;
            markerListCardView.setFocusable(false);
            this.markerListCardWindow.setOutsideTouchable(false);
            this.markerListCardWindow.setBackgroundDrawable(new BitmapDrawable());
        }
    }

    private void showMarkerCardView(WalkMarker walkMarker) {
        List<WalkMarker> markers = this.mMarkerListAdapter.getMarkers();
        int i = 0;
        while (i < markers.size() && !markers.get(i).equals(walkMarker)) {
            i++;
        }
        if (this.mMarkersViewPager.getCurrentItem() != i) {
            this.mMarkersViewPager.setCurrentItem(i);
        }
        if (this.markerListCardWindow.isShowing()) {
            return;
        }
        this.markerListCardWindow.showAtLocation(getWindow().getDecorView(), 80, 0, 0);
    }

    private void showPetListWindow() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_pop_pet_list, (ViewGroup) null);
        ListView listView = (ListView) viewInflate.findViewById(R.id.pop_list);
        final PetsListSelectAdapter petsListSelectAdapter = new PetsListSelectAdapter(this, UserInforUtils.getUser().getDogs(), this.mCurPet);
        listView.setAdapter((ListAdapter) petsListSelectAdapter);
        listView.setDividerHeight(1);
        int totalHeightofListView = CommonUtils.getTotalHeightofListView(listView);
        int i = BaseApplication.displayMetrics.heightPixels;
        PopupWindow popupWindow = new PopupWindow(viewInflate, (int) (BaseApplication.displayMetrics.widthPixels - (DeviceUtils.dpToPixel(this, 15.0f) * 2.0f)), totalHeightofListView > i / 2 ? i / 2 : -2);
        this.popupWindow = popupWindow;
        popupWindow.setOutsideTouchable(true);
        this.popupWindow.setAnimationStyle(android.R.style.Animation.Dialog);
        this.popupWindow.update();
        this.popupWindow.setTouchable(true);
        this.popupWindow.setFocusable(true);
        this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] iArr = new int[2];
        this.petView.getLocationOnScreen(iArr);
        this.popupWindow.showAtLocation(this.petView, 0, (int) DeviceUtils.dpToPixel(this, 15.0f), iArr[1] + this.petView.getHeight());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.5
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                WalkMarkersActivity.this.mCurPet = petsListSelectAdapter.getItem(i2);
                WalkMarkersActivity.this.initViewState();
                WalkMarkersActivity.this.hidePetView();
                WalkMarkersActivity.this.popupWindow.dismiss();
            }
        });
    }

    private WalkMarker getWalkMarkerForMarker(Marker marker) {
        for (Map.Entry<WalkMarker, Marker> entry : this.mDisplayMarkers.entrySet()) {
            if (marker.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(Constants.BROADCAST_MSG_ADD_DOG_COMPLETE) && WalkMarkersActivity.this.mCurPet == null) {
                    WalkMarkersActivity.this.mCurPet = GoDataUtils.getDefaultPet();
                    WalkMarkersActivity.this.initViewState();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_ADD_DOG_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void initMapView() {
        double dDoubleValue;
        double dDoubleValue2;
        WalkMarker walkMarker = this.mCurMarker;
        if (walkMarker != null) {
            dDoubleValue = walkMarker.getLoc().get(1).doubleValue();
            dDoubleValue2 = this.mCurMarker.getLoc().get(0).doubleValue();
        } else {
            String sysMap = CommonUtils.getSysMap(Consts.HTTP_HEADER_LOCATION);
            if (isEmpty(sysMap) || !sysMap.contains(ChineseToPinyinResource.Field.COMMA)) {
                dDoubleValue = 39.23242d;
                dDoubleValue2 = 116.253654d;
            } else {
                String[] strArrSplit = sysMap.split(ChineseToPinyinResource.Field.COMMA);
                dDoubleValue = Double.valueOf(strArrSplit[1]).doubleValue();
                dDoubleValue2 = Double.valueOf(strArrSplit[0]).doubleValue();
            }
        }
        MapView mapView = new MapView(this, new GoogleMapOptions().camera(new CameraPosition(new LatLng(dDoubleValue, dDoubleValue2), 18.0f, 0.0f, 0.0f)));
        this.mGoogleMapView = mapView;
        mapView.onCreate(null);
        this.mGoogleMapView.onResume();
        this.mContainerLayout.addView(this.mGoogleMapView, new LinearLayout.LayoutParams(-1, -1));
        this.mGoogleMapView.getMapAsync(new OnMapReadyCallback() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.7
            @Override // com.google.android.gms.maps.OnMapReadyCallback
            public void onMapReady(GoogleMap googleMap) {
                WalkMarkersActivity.this.googlemap = googleMap;
                if (WalkMarkersActivity.this.googlemap != null) {
                    WalkMarkersActivity.this.googlemap.setOnCameraMoveListener(WalkMarkersActivity.this);
                    WalkMarkersActivity.this.googlemap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() { // from class: com.petkit.android.activities.walkdog.WalkMarkersActivity.7.1
                        @Override // com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
                        public boolean onMarkerClick(Marker marker) {
                            WalkMarkersActivity.this.setMarkerHightlight(marker);
                            return false;
                        }
                    });
                }
            }
        });
    }

    private void hideMarkerCardView() {
        if (this.googlemap != null && this.markerListCardWindow.isShowing()) {
            this.markerListCardWindow.dismiss();
            Marker marker = this.mCurHightlightMarker;
            if (marker != null) {
                this.mCurHightlightMarker.setIcon(BitmapDescriptorFactory.fromResource(WalkDataUtils.getMarkerIconResId(getWalkMarkerForMarker(marker), 0)));
                this.mCurHightlightMarker = null;
                this.mGoogleMapView.invalidate();
            }
        }
    }

    private Marker addNormalMarker(LatLng latLng, int i) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(i));
        return this.googlemap.addMarker(markerOptions);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMarkerHightlight(Marker marker) {
        if (isFinishing() || this.googlemap == null) {
            return;
        }
        Marker marker2 = this.mCurHightlightMarker;
        if (marker2 == null || !marker2.getPosition().equals(marker.getPosition())) {
            WalkMarker walkMarkerForMarker = getWalkMarkerForMarker(marker);
            Marker marker3 = this.mCurHightlightMarker;
            if (marker3 != null) {
                this.mCurHightlightMarker.setIcon(BitmapDescriptorFactory.fromResource(WalkDataUtils.getMarkerIconResId(getWalkMarkerForMarker(marker3), 0)));
                this.mCurHightlightMarker = null;
            }
            if (walkMarkerForMarker == null) {
                return;
            }
            marker.setIcon(BitmapDescriptorFactory.fromResource(WalkDataUtils.getMarkerIconResId(walkMarkerForMarker, 1)));
            this.mGoogleMapView.invalidate();
            this.mCurHightlightMarker = marker;
            showMarkerCardView(walkMarkerForMarker);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMarkerList(List<WalkMarker> list, boolean z) {
        if (this.googlemap == null) {
            return;
        }
        if (list == null || list.size() == 0) {
            this.googlemap.clear();
            initMarkersEmptyView();
            hideMarkerCardView();
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (WalkMarker walkMarker : list) {
            if (!this.mDisplayMarkers.containsKey(walkMarker) && walkMarker.getLoc() != null) {
                LatLng latLng = new LatLng(walkMarker.getLoc().get(1).doubleValue(), walkMarker.getLoc().get(0).doubleValue());
                builder.include(latLng);
                this.mDisplayMarkers.put(walkMarker, addNormalMarker(latLng, WalkDataUtils.getMarkerIconResId(walkMarker, 0)));
            }
        }
        if (z) {
            this.isAutoMoveMap = true;
            this.googlemap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), list.size()));
        }
        this.mMarkerListAdapter.setMarkers(list);
    }
}

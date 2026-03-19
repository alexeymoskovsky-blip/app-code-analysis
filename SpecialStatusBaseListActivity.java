package com.petkit.android.activities.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.InputMethodRelativeLayout;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.base.adapter.LoadMoreTypeAdapter;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.activities.base.fragment.BaseTitleFragment;
import com.petkit.android.activities.common.fragment.FailureFragment;
import com.petkit.android.activities.common.fragment.LoadFragment;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.PhotoController;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import io.agora.rtc2.internal.AudioRoutingController;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SpecialStatusBaseListActivity extends SwipeBackBaseActivity implements View.OnClickListener, InputMethodRelativeLayout.OnSizeChangedListener, FailureFragment.FailureOnClickListener, PhotoController.PhotoControllerListener, AdapterView.OnItemClickListener {
    public static final String BROADCAST_MSG_CLOSE_ACTIVITY = "com.petkit.android.exit";
    protected static final int SPECIAL_VIEW_STATE_GONE = 1;
    protected static final int SPECIAL_VIEW_STATE_VISIBILITY = 0;
    protected static final int View_State_Empty = 3;
    protected static final int View_State_Fail = 2;
    protected static final int View_State_GONE = 4;
    protected static final int View_State_Loadding = 0;
    protected static final int View_State_Normal = 1;
    private View divider_line;
    private Fragment failureFragment;
    protected String imageFilePath;
    private Fragment loadFragment;
    protected String localTempImageFileName;
    protected View mBottomView;
    private BroadcastReceiver mBroadcastReceiver;
    protected View mHeaderView;
    protected NormalBaseAdapter mListAdapter;
    protected ListView mListView;
    protected PopupWindow mPopupWindow;
    private View mainView;
    private PhotoController photoController;
    protected BaseTitleFragment titleView;
    protected int TitleLeftType = 0;
    protected int specialViewState = -1;
    private int stateViewHeight = 0;
    protected boolean isNeedPhotoController = false;
    protected int scrollLimitPosition = 12;
    protected boolean isScrollToTopEnable = false;
    private final AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() { // from class: com.petkit.android.activities.base.SpecialStatusBaseListActivity.1
        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            View view;
            SpecialStatusBaseListActivity.this.onListViewScroll(absListView, i, i2, i3);
            SpecialStatusBaseListActivity specialStatusBaseListActivity = SpecialStatusBaseListActivity.this;
            specialStatusBaseListActivity.isScrollToTopEnable = i > specialStatusBaseListActivity.scrollLimitPosition;
            if (i != 0 || (view = specialStatusBaseListActivity.mHeaderView) == null) {
                return;
            }
            int height = view.getHeight();
            int dimension = (int) SpecialStatusBaseListActivity.this.getResources().getDimension(R.dimen.base_titleheight);
            double scrollY = SpecialStatusBaseListActivity.this.getScrollY();
            double d = height - dimension;
            double d2 = 0.7d * d;
            SpecialStatusBaseListActivity.this.setSpecialTitleViewState(scrollY > d2 ? (int) (((double) (((int) (scrollY - d2)) * 255)) / (d * 0.3d)) : 0);
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            SpecialStatusBaseListActivity.this.onListViewScrollStateChanged(absListView, i);
            if (i == 0 && CommonUtil.isListViewReachBottomEdge(SpecialStatusBaseListActivity.this.mListView)) {
                NormalBaseAdapter normalBaseAdapter = SpecialStatusBaseListActivity.this.mListAdapter;
                if ((normalBaseAdapter instanceof LoadMoreBaseAdapter) && ((LoadMoreBaseAdapter) normalBaseAdapter).checkStartLoadMore()) {
                    ((LoadMoreBaseAdapter) SpecialStatusBaseListActivity.this.mListAdapter).setLoadMoreState(1);
                    SpecialStatusBaseListActivity.this.onLoadMoreBegin();
                    return;
                }
                NormalBaseAdapter normalBaseAdapter2 = SpecialStatusBaseListActivity.this.mListAdapter;
                if ((normalBaseAdapter2 instanceof LoadMoreTypeAdapter) && ((LoadMoreTypeAdapter) normalBaseAdapter2).checkStartLoadMore()) {
                    ((LoadMoreTypeAdapter) SpecialStatusBaseListActivity.this.mListAdapter).setLoadMoreState(1);
                    SpecialStatusBaseListActivity.this.onLoadMoreBegin();
                }
            }
        }
    };
    private String tempImagefilePath = "";
    private String cropImagefilePath = "";
    private boolean isCrop = false;

    public abstract void initAdatper();

    public void onListViewScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    public void onListViewScrollStateChanged(AbsListView absListView, int i) {
    }

    public abstract void onLoadMoreBegin();

    @Override // com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
    }

    @Override // com.jess.arms.widget.InputMethodRelativeLayout.OnSizeChangedListener
    public void onSizeChange(boolean z, int i, int i2) {
    }

    public abstract void refreshTitleView();

    public abstract void setupViews();

    public void uploadHeadPic(String str) {
    }

    @Override // com.petkit.android.activities.base.SwipeBackBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.isCrop = bundle.getBoolean("isCrop");
            this.tempImagefilePath = bundle.getString(Constants.EXTRA_TEMP_IMAGE_PATH);
            this.cropImagefilePath = bundle.getString(Constants.EXTRA_CROP_IMAGE_PATH);
        }
        initPhotoController();
        super.setContentView(R.layout.activity_base_special_status_list);
        initBaseViews();
        registerBoradcastReceiver();
    }

    private void initPhotoController() {
        if (this.isNeedPhotoController) {
            PhotoController photoController = new PhotoController(this);
            this.photoController = photoController;
            photoController.setPhotoControllerListener(this);
            this.photoController.setCrop(this.isCrop);
            this.photoController.setTempImagefilePath(this.tempImagefilePath);
            this.photoController.setCropImagefilePath(this.cropImagefilePath);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        cancenRequest(true);
        PetkitLog.d("onDestroy : " + getLocalClassName());
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        PhotoController photoController;
        super.onSaveInstanceState(bundle);
        if (!this.isNeedPhotoController || (photoController = this.photoController) == null) {
            return;
        }
        bundle.putBoolean("isCrop", photoController.isCrop());
        bundle.putString(Constants.EXTRA_TEMP_IMAGE_PATH, this.photoController.getTempImagefilePath());
        bundle.putString(Constants.EXTRA_CROP_IMAGE_PATH, this.photoController.getCropImagefilePath());
    }

    private void initBaseViews() {
        this.mainView = findViewById(R.id.main_view);
        this.mListView = (ListView) findViewById(R.id.list);
        this.titleView = (BaseTitleFragment) getSupportFragmentManager().findFragmentById(R.id.re_toptitle_frgmt);
        this.divider_line = findViewById(R.id.title_divider_line);
        this.mListView.setScrollbarFadingEnabled(true);
        this.mListView.setOnScrollListener(this.mScrollListener);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setDividerHeight(1);
        setSpecialTitleViewState(0);
        setupViews();
        initAdatper();
        checkLoadMoreListener();
    }

    public void cancel(View view) {
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    public void setDividerLineVisibility(int i) {
        this.divider_line.setVisibility(i);
    }

    @Override // android.app.Activity
    public void setTitle(int i) {
        setTitle(getString(i));
    }

    public void setTitle(int i, int i2) {
        setTitle(getString(i), getResources().getColor(i2));
    }

    public void setTitle(String str, int i) {
        setTitle(str);
        this.titleView.setTitleTextColor(i);
    }

    public void setTitle(String str) {
        if (str != null) {
            this.titleView.setTitle(str);
        }
    }

    public void setTitleNameVisibility(View.OnClickListener onClickListener) {
        this.titleView.setTitleListner(onClickListener);
    }

    public TextView getTitleNameView() {
        return this.titleView.getTitleNameView();
    }

    public View getTitleView() {
        return this.titleView.getTitleView();
    }

    public void setTitleBackgroundColor(int i) {
        this.titleView.setTitleBackgroundColor(i);
    }

    public void setBackgroundResourceId(int i) {
        View view = this.mHeaderView;
        if (view != null) {
            view.setBackgroundResource(i);
        }
    }

    public void setNoTitle() {
        this.titleView.setTitleFrgmtVisibility(8);
    }

    public void setHasTitle() {
        this.titleView.setTitleFrgmtVisibility(0);
    }

    public void setTitleLeftTextButton(int i, View.OnClickListener onClickListener) {
        this.titleView.setTitleLeftTextButton(i, onClickListener);
        this.TitleLeftType = 1;
    }

    public void setTitleLeftButton(int i, View.OnClickListener onClickListener) {
        this.titleView.setTitleLeftButton(i, onClickListener);
        this.TitleLeftType = 1;
    }

    public void setTitleLeftButton(int i) {
        this.titleView.setTitleLeftButton(i, null);
        this.TitleLeftType = 1;
    }

    public void setNoTitleLeftButton() {
        this.titleView.setNoTitleLeftButton();
        this.TitleLeftType = 1;
    }

    public void setTitleRightButton(int i, View.OnClickListener onClickListener) {
        this.titleView.setTitleRightButton(getString(i), onClickListener);
    }

    public void setTitleRightButton(String str, View.OnClickListener onClickListener) {
        this.titleView.setTitleRightButton(str, onClickListener);
    }

    public void setTitleRightButton(String str, int i, View.OnClickListener onClickListener) {
        this.titleView.setTitleRightButton(str, i, onClickListener);
    }

    public void setTitleRightButtonVisibility(int i) {
        this.titleView.setTitleRightButtonVisibility(i);
    }

    public void setTitleRightImageView(int i, View.OnClickListener onClickListener) {
        this.titleView.setTitleRightImageView(i, onClickListener);
    }

    public void setTitleRight2ImageView(int i, View.OnClickListener onClickListener, int i2, View.OnClickListener onClickListener2) {
        this.titleView.setTitleRight2ImageView(i, onClickListener, i2, onClickListener2);
    }

    public void setTitleRight2ImageViewVisibility(int i, int i2) {
        this.titleView.setTitleRight2ImageViewVisibility(i, i2);
    }

    public void setTitleStatus(String str, int i, int i2) {
        this.titleView.setTitleStatus(str, i, i2);
    }

    public void setTitleStatusName(String str) {
        this.titleView.setTitleStatusName(str);
    }

    public LinearLayout getTitleTab() {
        return this.titleView.getTitleTab();
    }

    public void showTitleTab() {
        getTitleTab();
    }

    public void setTitleMask(int i) {
        setTitleMask(null, i);
    }

    public void setTitleMask(Animation animation, int i) {
        this.titleView.setTitleMask(animation, i);
    }

    public void setTitleLineVisibility(int i) {
        this.titleView.setTitleLineVisibility(i);
    }

    public void setStatusLineTransparent() {
        getWindow().addFlags(AudioRoutingController.DEVICE_OUT_USB_HEADSET);
    }

    public void setStatusLineOpaque() {
        getWindow().clearFlags(AudioRoutingController.DEVICE_OUT_USB_HEADSET);
    }

    public void setStateLoad() {
        if (this.loadFragment == null) {
            this.loadFragment = new LoadFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(LoadFragment.ARGUMENT_VIEW_HEIGHT, this.stateViewHeight);
        this.loadFragment.setArguments(bundle);
        setFragment(this.loadFragment);
    }

    public void setStateFailOrEmpty() {
        if (this.failureFragment == null) {
            this.failureFragment = new FailureFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("FailureFragment_Height", this.stateViewHeight);
        this.failureFragment.setArguments(bundle);
        setFragment(this.failureFragment);
    }

    public void setStateFailOrEmpty(int i, int i2, int i3) {
        setStateFailOrEmpty(i, getString(i2), i3);
    }

    public void setStateFailOrEmpty(int i, String str, int i2) {
        if (this.failureFragment == null) {
            this.failureFragment = new FailureFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("FailureFragment_IconResId", i);
        bundle.putString("FailureFragment_Content", str);
        bundle.putInt("FailureFragment_Button", i2);
        bundle.putInt("FailureFragment_Height", this.stateViewHeight);
        this.failureFragment.setArguments(bundle);
        setFragment(this.failureFragment);
    }

    public void setStateNormal() {
        Fragment fragment = this.loadFragment;
        if (fragment != null) {
            removeFragment(fragment);
        }
        Fragment fragment2 = this.failureFragment;
        if (fragment2 != null) {
            removeFragment(fragment2);
        }
    }

    public void setFragment(Fragment fragment) {
        if (isFinishing() || fragment.isAdded()) {
            return;
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.re_basecontent, fragment);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    public void removeFragment(Fragment fragment) {
        if (isFinishing()) {
            return;
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.remove(fragment);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    public void setBottomView(int i) {
        if (this.mBottomView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.bottom_view);
            viewStub.setLayoutResource(i);
            viewStub.inflate();
            this.mBottomView = findViewById(R.id.bottom_view_id);
        }
    }

    public void setAddIconVisiblility(int i) {
        findViewById(R.id.ib_add).setVisibility(i);
    }

    public void showLongToast(String str, int i) {
        PetkitToast.showLongToast(this, str, i);
    }

    public void showLongToast(int i) {
        PetkitToast.showLongToast(this, i);
    }

    public void showLongToast(String str) {
        PetkitToast.showLongToast(this, str);
    }

    public void showLongToast(int i, int i2) {
        PetkitToast.showLongToast(this, getString(i), i2);
    }

    public void showShortToast(String str) {
        PetkitToast.showShortToast(this, str);
    }

    public void showShortToast(String str, int i) {
        PetkitToast.showShortToast(this, str, i);
    }

    public void showShortToast(int i) {
        PetkitToast.showShortToast(this, i);
    }

    public void showShortToast(int i, int i2) {
        PetkitToast.showShortToast(this, i, i2);
    }

    public int dip2px(float f) {
        return (int) DeviceUtils.dpToPixel(this, f);
    }

    public void startActivity(Class<?> cls) {
        startActivity(cls, false);
    }

    public void startActivity(Class<?> cls, boolean z) {
        startActivity(new Intent(this, cls));
        if (z) {
            finish();
        }
    }

    public void startActivityForResult(Class<?> cls, int i) {
        startActivityForResult(new Intent(this, cls), i);
    }

    public void startActivityWithData(Class<?> cls, Map<String, String> map, boolean z) {
        Intent intent = new Intent(this, cls);
        Bundle bundle = new Bundle();
        for (String str : map.keySet()) {
            bundle.putString(str, map.get(str));
        }
        intent.putExtras(bundle);
        startActivity(intent);
        if (z) {
            finish();
        }
    }

    public void startActivityWithData(Class<?> cls, Bundle bundle, boolean z) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (z) {
            finish();
        }
    }

    public boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public void showLoadDialog() {
        LoadDialog.show(this);
    }

    public void dismissLoadDialog() {
        LoadDialog.dismissDialog();
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.base.SpecialStatusBaseListActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                SpecialStatusBaseListActivity.this.finish();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.petkit.android.exit");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    public void sendCloseActivityBroadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.petkit.android.exit"));
    }

    public void showSoftInput(final EditText editText) {
        editText.requestFocus();
        new Timer().schedule(new TimerTask() { // from class: com.petkit.android.activities.base.SpecialStatusBaseListActivity.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                ((InputMethodManager) editText.getContext().getSystemService("input_method")).showSoftInput(editText, 0);
            }
        }, 200L);
    }

    public void hideSoftInput() {
        if (getCurrentFocus() == null || getCurrentFocus().getWindowToken() == null) {
            return;
        }
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
    }

    public void showPopMenu(String... strArr) {
        showPopMenu((String) null, strArr);
    }

    public static class PopMenuKeyAndListener {
        public String key;
        public View.OnClickListener listener;

        public PopMenuKeyAndListener(String str, View.OnClickListener onClickListener) {
            this.key = str;
            this.listener = onClickListener;
        }
    }

    public void dismissPopMenu() {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            return;
        }
        this.mPopupWindow.dismiss();
    }

    public void showPopMenu(String str, PopMenuKeyAndListener... popMenuKeyAndListenerArr) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
            }
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_pop_menu, (ViewGroup) null);
            this.mPopupWindow = new BasePetkitWindow((Context) this, viewInflate, false);
            if (!isEmpty(str)) {
                TextView textView = (TextView) viewInflate.findViewById(R.id.menu_desc);
                textView.setVisibility(0);
                textView.setText(str);
                viewInflate.findViewById(R.id.menu_desc_gap).setVisibility(0);
            }
            int i = 0;
            while (i < popMenuKeyAndListenerArr.length) {
                Resources resources = getResources();
                StringBuilder sb = new StringBuilder();
                sb.append("menu_");
                int i2 = i + 1;
                sb.append(i2);
                Button button = (Button) viewInflate.findViewById(resources.getIdentifier(sb.toString(), "id", getPackageName()));
                button.setVisibility(0);
                button.setText(popMenuKeyAndListenerArr[i].key);
                button.setOnClickListener(popMenuKeyAndListenerArr[i].listener);
                i = i2;
            }
            if (popMenuKeyAndListenerArr.length < 3) {
                View viewFindViewById = viewInflate.findViewById(getResources().getIdentifier("menu_gap_" + popMenuKeyAndListenerArr.length, "id", getPackageName()));
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(8);
                }
            }
            ((Button) viewInflate.findViewById(R.id.menu_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.base.SpecialStatusBaseListActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    SpecialStatusBaseListActivity.this.mPopupWindow.dismiss();
                }
            });
            this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            this.mPopupWindow.setOutsideTouchable(true);
            this.mPopupWindow.setFocusable(true);
            this.mPopupWindow.showAtLocation(this.mListView, 80, 0, 0);
        }
    }

    public void showPopMenu(String str, String... strArr) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
            }
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_pop_menu, (ViewGroup) null);
            this.mPopupWindow = new BasePetkitWindow((Context) this, viewInflate, false);
            if (!isEmpty(str)) {
                TextView textView = (TextView) viewInflate.findViewById(R.id.menu_desc);
                textView.setVisibility(0);
                textView.setText(str);
                viewInflate.findViewById(R.id.menu_desc_gap).setVisibility(0);
            }
            int i = 0;
            while (i < strArr.length) {
                Resources resources = getResources();
                StringBuilder sb = new StringBuilder();
                sb.append("menu_");
                int i2 = i + 1;
                sb.append(i2);
                Button button = (Button) viewInflate.findViewById(resources.getIdentifier(sb.toString(), "id", getPackageName()));
                button.setVisibility(0);
                button.setText(strArr[i]);
                button.setOnClickListener(this);
                i = i2;
            }
            if (strArr.length < 3) {
                View viewFindViewById = viewInflate.findViewById(getResources().getIdentifier("menu_gap_" + strArr.length, "id", getPackageName()));
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(8);
                }
            }
            ((Button) viewInflate.findViewById(R.id.menu_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.base.SpecialStatusBaseListActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    SpecialStatusBaseListActivity.this.mPopupWindow.dismiss();
                }
            });
            this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            this.mPopupWindow.setOutsideTouchable(true);
            this.mPopupWindow.setFocusable(true);
            this.mPopupWindow.showAtLocation(this.mListView, 80, 0, 0);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.menu_cancel) {
            this.mPopupWindow.dismiss();
        }
    }

    public void setCrop(boolean z) {
        PhotoController photoController = this.photoController;
        if (photoController != null) {
            photoController.setCrop(z);
        }
    }

    public void getPhotoFromAlbum() {
        PhotoController photoController = this.photoController;
        if (photoController != null) {
            photoController.getPhotoFromAlbum();
        }
    }

    public void getPhotoFromCamera() {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            ((BasePetkitWindow) popupWindow).dismisswithOutAnima();
        }
        PhotoController photoController = this.photoController;
        if (photoController != null) {
            photoController.getPhotoFromCamera();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        PhotoController photoController = this.photoController;
        if (photoController != null) {
            photoController.onActivityResult(i, i2, intent);
        }
    }

    @Override // com.petkit.android.utils.PhotoController.PhotoControllerListener
    public void onGetPhoto(String str) {
        uploadHeadPic(str);
    }

    public void post(String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        post(str, (Map<String, String>) null, asyncHttpResponseHandler);
    }

    public void post(String str, Map<String, String> map, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        post(str, map, asyncHttpResponseHandler, false);
    }

    public void post(String str, AsyncHttpResponseHandler asyncHttpResponseHandler, boolean z) {
        post(str, null, asyncHttpResponseHandler, z);
    }

    public void post(String str, Map<String, String> map, AsyncHttpResponseHandler asyncHttpResponseHandler, boolean z) {
        AsyncHttpUtil.post(this, str, map, asyncHttpResponseHandler, z);
    }

    public void cancenRequest(boolean z) {
        AsyncHttpUtil.cancenRequest(this, z);
    }

    public void setSpecialTitleViewState(int i) {
        if (250 < i) {
            if (this.specialViewState != 1) {
                this.specialViewState = 1;
                refreshTitleView();
            }
        } else if (this.specialViewState != 0) {
            this.specialViewState = 0;
            refreshTitleView();
        }
        this.titleView.setSpecialTitleViewState(i, false);
    }

    public int getScrollY() {
        View childAt = this.mListView.getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        return (-childAt.getTop()) + (this.mListView.getFirstVisiblePosition() * childAt.getHeight());
    }

    public void checkLoadMoreListener() {
        NormalBaseAdapter normalBaseAdapter = this.mListAdapter;
        if (normalBaseAdapter instanceof LoadMoreBaseAdapter) {
            ((LoadMoreBaseAdapter) normalBaseAdapter).setLoadMoreRefresh(new LoadMoreBaseAdapter.ILoadMoreRefresh() { // from class: com.petkit.android.activities.base.SpecialStatusBaseListActivity.6
                @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter.ILoadMoreRefresh
                public void onRefresh() {
                    ((LoadMoreBaseAdapter) SpecialStatusBaseListActivity.this.mListAdapter).setLoadMoreState(1);
                    SpecialStatusBaseListActivity.this.mListAdapter.notifyDataSetChanged();
                    SpecialStatusBaseListActivity.this.onLoadMoreBegin();
                }
            });
        } else if (normalBaseAdapter instanceof LoadMoreTypeAdapter) {
            ((LoadMoreTypeAdapter) normalBaseAdapter).setLoadMoreRefresh(new LoadMoreTypeAdapter.ILoadMoreRefresh() { // from class: com.petkit.android.activities.base.SpecialStatusBaseListActivity.7
                @Override // com.petkit.android.activities.base.adapter.LoadMoreTypeAdapter.ILoadMoreRefresh
                public void onRefresh() {
                    ((LoadMoreTypeAdapter) SpecialStatusBaseListActivity.this.mListAdapter).setLoadMoreState(1);
                    SpecialStatusBaseListActivity.this.mListAdapter.notifyDataSetChanged();
                    SpecialStatusBaseListActivity.this.onLoadMoreBegin();
                }
            });
        }
    }
}

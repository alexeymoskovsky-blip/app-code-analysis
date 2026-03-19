package com.petkit.android.activities.registe.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.model.Region;
import com.petkit.oversea.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/* JADX INFO: loaded from: classes5.dex */
public class WelcomeControlFragment extends BaseFragment {
    private LinearLayout actionTitleView;
    private WelcomePagerAdapter mPagerAdapter;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;
    private ViewPager mViewPager;
    private RelativeLayout rlLoginRegister;
    private TextView tvLogin;
    private View tvLoginIndicator;
    private TextView tvRegister;
    private View tvRegisterIndicator;

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_registe_control);
        setNoTitle();
        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) this.contentView.findViewById(R.id.slidingDrawer);
        this.mSlidingUpPanelLayout = slidingUpPanelLayout;
        slidingUpPanelLayout.setDragView(this.contentView.findViewById(R.id.drag_view));
        this.mSlidingUpPanelLayout.setTouchEnabled(false);
        this.mViewPager = (ViewPager) this.mainView.findViewById(R.id.welcome_viewpager);
        WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter(getActivity().getSupportFragmentManager());
        this.mPagerAdapter = welcomePagerAdapter;
        this.mViewPager.setAdapter(welcomePagerAdapter);
        float fDip2px = BaseApplication.displayMetrics.heightPixels - ArmsUtils.dip2px(getActivity(), 275.0f);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mViewPager.getLayoutParams();
        layoutParams.height = (int) fDip2px;
        this.mViewPager.setLayoutParams(layoutParams);
        this.mainView.findViewById(R.id.login).setOnClickListener(this);
        this.mainView.findViewById(R.id.register).setOnClickListener(this);
        this.mainView.findViewById(R.id.rl_login).setOnClickListener(this);
        this.mainView.findViewById(R.id.rl_register).setOnClickListener(this);
        this.actionTitleView = (LinearLayout) this.mainView.findViewById(R.id.action_title_view);
        this.rlLoginRegister = (RelativeLayout) this.mainView.findViewById(R.id.rl_login_register);
        this.tvLoginIndicator = this.mainView.findViewById(R.id.tv_login_indicator);
        this.tvRegisterIndicator = this.mainView.findViewById(R.id.tv_register_indicator);
        this.tvLogin = (TextView) this.mainView.findViewById(R.id.tv_login);
        this.tvRegister = (TextView) this.mainView.findViewById(R.id.tv_register);
        this.tvLogin.setOnClickListener(this);
        this.tvRegister.setOnClickListener(this);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.registe.fragment.WelcomeControlFragment.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                WelcomeControlFragment.this.refreshActionIndictorView();
            }
        });
        refreshActionIndictorView();
        openPanel();
    }

    public void setRegion(Region region) {
        this.mPagerAdapter.setRegion(region);
    }

    public void setPanelSlideListener(SlidingUpPanelLayout.PanelSlideListener panelSlideListener) {
        this.mSlidingUpPanelLayout.addPanelSlideListener(panelSlideListener);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.login || id == R.id.tv_login || id == R.id.rl_login) {
            this.mViewPager.setCurrentItem(0);
            refreshActionIndictorView();
            openPanel();
        } else if (id == R.id.register || id == R.id.tv_register || id == R.id.rl_register) {
            this.mViewPager.setCurrentItem(1);
            refreshActionIndictorView();
            openPanel();
        }
    }

    public boolean isOpened() {
        return this.mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED;
    }

    public void hidePanel() {
        this.actionTitleView.setVisibility(0);
        this.rlLoginRegister.setVisibility(8);
        this.mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    public void openPanel() {
        this.actionTitleView.setVisibility(8);
        this.rlLoginRegister.setVisibility(0);
        this.mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshActionIndictorView() {
        if (this.mViewPager.getCurrentItem() == 0) {
            this.tvLoginIndicator.setVisibility(0);
            this.tvLogin.setTextColor(getActivity().getResources().getColor(R.color.white));
            this.tvRegisterIndicator.setVisibility(8);
            this.tvRegister.setTextColor(getActivity().getResources().getColor(R.color.welcome_light_white));
        } else {
            this.tvRegisterIndicator.setVisibility(0);
            this.tvRegister.setTextColor(getActivity().getResources().getColor(R.color.white));
            this.tvLoginIndicator.setVisibility(8);
            this.tvLogin.setTextColor(getActivity().getResources().getColor(R.color.welcome_light_white));
        }
        this.mPagerAdapter.refreshFocus(this.mViewPager.getCurrentItem());
    }

    public class WelcomePagerAdapter extends FragmentPagerAdapter {
        public PetkitLoginFragment mPetkitLoginFragment;
        public RegisterFragment mRegisterFragment;

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return 2;
        }

        public WelcomePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.mPetkitLoginFragment = new PetkitLoginFragment();
            this.mRegisterFragment = new RegisterFragment();
        }

        public void setRegion(Region region) {
            RegisterFragment registerFragment = this.mRegisterFragment;
            if (registerFragment != null) {
                registerFragment.setRegion(region);
            }
            PetkitLoginFragment petkitLoginFragment = this.mPetkitLoginFragment;
            if (petkitLoginFragment != null) {
                petkitLoginFragment.setRegion(region);
            }
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return i == 0 ? this.mPetkitLoginFragment : this.mRegisterFragment;
        }

        public void refreshFocus(int i) {
            if (i == 0) {
                this.mPetkitLoginFragment.refreshFocus();
            } else {
                this.mRegisterFragment.refreshFocus();
            }
        }
    }
}

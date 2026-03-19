package com.petkit.android.activities.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import com.jess.arms.utils.DeviceUtils;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.home.adapter.MessageListPagerAdapter;
import com.petkit.android.activities.setting.PrivacySettingActivity;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.ConvertDipPx;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class MessageFragment extends BaseFragment implements View.OnClickListener {
    private ImageView bottomLog;
    private ImageView bottomMsg;
    private int currentPage = 0;
    private View logNewMsgFlag;
    private MessageListPagerAdapter mAdapter;
    private BroadcastReceiver mBroadcastReceiver;
    private ViewPager mViewPager;
    private View msgNewMsgFlag;
    private TextView tvLog;
    private TextView tvMsg;

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.activity_classify_detail_page);
        initView();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.title_new_rl) {
            if (this.currentPage != 0) {
                this.mViewPager.setCurrentItem(0);
                return;
            } else {
                this.mAdapter.moveToTop(0);
                return;
            }
        }
        if (id == R.id.title_nearby_rl) {
            if (this.currentPage != 1) {
                this.mViewPager.setCurrentItem(1);
            } else {
                this.mAdapter.moveToTop(0);
            }
        }
    }

    private void initView() {
        LinearLayout titleTab = getTitleTab();
        titleTab.setVisibility(0);
        titleTab.findViewById(R.id.title_new_rl).setOnClickListener(this);
        titleTab.findViewById(R.id.title_nearby_rl).setVisibility(0);
        titleTab.findViewById(R.id.title_nearby_rl).setOnClickListener(this);
        titleTab.findViewById(R.id.title_hot_rl).setOnClickListener(this);
        titleTab.findViewById(R.id.title_hot_rl).setVisibility(8);
        setTitleRightImageView(R.drawable.btn_setting, new View.OnClickListener() { // from class: com.petkit.android.activities.home.MessageFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initView$0(view);
            }
        });
        ViewPager viewPager = (ViewPager) this.contentView.findViewById(R.id.pager);
        this.mViewPager = viewPager;
        viewPager.setOffscreenPageLimit(2);
        MessageListPagerAdapter messageListPagerAdapter = new MessageListPagerAdapter(getChildFragmentManager(), getActivity());
        this.mAdapter = messageListPagerAdapter;
        this.mViewPager.setAdapter(messageListPagerAdapter);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.home.MessageFragment.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                MessageFragment.this.currentPage = i;
                MessageFragment.this.changTabTitle();
                MessageFragment.this.clearUnreadNumberForLog();
            }
        });
        this.currentPage = 0;
        changTabTitle();
        this.tvMsg.setText(R.string.Homepage_message);
        this.tvLog.setText(R.string.Log);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        startActivity(new Intent(getContext(), (Class<?>) PrivacySettingActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changTabTitle() {
        this.tvMsg = (TextView) getTitleTab().findViewById(R.id.title_follow);
        this.bottomMsg = (ImageView) getTitleTab().findViewById(R.id.bottom_follow);
        this.msgNewMsgFlag = getTitleTab().findViewById(R.id.title_follow_new_flag);
        this.tvLog = (TextView) getTitleTab().findViewById(R.id.title_nearby);
        this.bottomLog = (ImageView) getTitleTab().findViewById(R.id.bottom_nearby);
        this.logNewMsgFlag = getTitleTab().findViewById(R.id.title_nearby_new_flag);
        float fDpToPixel = DeviceUtils.dpToPixel(getActivity(), 16.0f);
        if (this.currentPage == 0) {
            this.tvMsg.setTextColor(getActivity().getResources().getColor(R.color.community_switch_color_hl));
            this.tvLog.setTextColor(getActivity().getResources().getColor(R.color.community_switch_color_nm));
            this.tvMsg.setTextSize(ConvertDipPx.px2dip(getActivity(), fDpToPixel));
            this.tvLog.setTextSize(ConvertDipPx.px2dip(getActivity(), fDpToPixel) - 3);
            this.msgNewMsgFlag.setVisibility(8);
            this.logNewMsgFlag.setVisibility(CommonUtils.getUnReadMsgCount(Constants.JID_TYPE_SYSTEM_LOG) > 0 ? 0 : 8);
            this.bottomMsg.setVisibility(0);
            this.bottomLog.setVisibility(4);
            return;
        }
        this.tvMsg.setTextColor(getActivity().getResources().getColor(R.color.community_switch_color_nm));
        this.tvLog.setTextColor(getActivity().getResources().getColor(R.color.community_switch_color_hl));
        this.tvMsg.setTextSize(ConvertDipPx.px2dip(getActivity(), fDpToPixel) - 3);
        this.tvLog.setTextSize(ConvertDipPx.px2dip(getActivity(), fDpToPixel));
        this.msgNewMsgFlag.setVisibility((CommonUtils.getAllUnReadMsgCount() - CommonUtils.getUnReadMsgCount(Constants.JID_TYPE_SYSTEM_LOG)) + ChatUtils.getChatCenter(CommonUtils.getCurrentUserId()).getNewMsgCount() > 0 ? 0 : 8);
        this.logNewMsgFlag.setVisibility(8);
        this.bottomMsg.setVisibility(4);
        this.bottomLog.setVisibility(0);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.home.MessageFragment.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_MESSAGE)) {
                    MessageFragment.this.changTabTitle();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_MESSAGE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mBroadcastReceiver);
    }

    public boolean isShowLogPage() {
        return this.currentPage == 1;
    }

    public void clearUnreadNumberForLog() {
        if (isShowLogPage()) {
            ((LogListFragment) this.mAdapter.getItem(1)).clearUnreadNumber();
        }
    }
}

package com.petkit.android.activities.feeder.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.internal.ServerProtocol;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.SelectFriendsActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.User;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class FeederSettingShareActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    private LinearLayout layout;
    private MyShareAdapter mAdapter;
    private CheckBox mCheckBox;
    private FeederRecord mFeederRecord;
    private TextView tvFriendList;
    private ArrayList<User> mSharedList = new ArrayList<>();
    private boolean mIsDelMode = false;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        long longExtra;
        super.onCreate(bundle);
        if (bundle == null) {
            longExtra = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
        } else {
            longExtra = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
        }
        this.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(longExtra);
        setContentView(R.layout.activity_feeder_setting_share);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Device_share);
        CheckBox checkBox = (CheckBox) findViewById(R.id.share_onoff);
        this.mCheckBox = checkBox;
        checkBox.setOnCheckedChangeListener(this);
        this.mCheckBox.setChecked(this.mFeederRecord.isShareOpen());
        addSymbol();
        this.layout = (LinearLayout) findViewById(R.id.shared_list);
        this.tvFriendList = (TextView) findViewById(R.id.shared_list_tips);
        MyGridView myGridView = new MyGridView(this);
        myGridView.setNumColumns(getResources().getInteger(R.integer.mate_share_column_count));
        MyShareAdapter myShareAdapter = new MyShareAdapter();
        this.mAdapter = myShareAdapter;
        myGridView.setAdapter((ListAdapter) myShareAdapter);
        myGridView.setOnItemClickListener(this);
        this.layout.addView(myGridView);
        getShareUserList();
        refreshFriends(this.mFeederRecord);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mFeederRecord.isShareOpen() != z) {
            shareOpen(z);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1) {
            getShareUserList();
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (i == this.mSharedList.size() - 1) {
            this.mIsDelMode = !this.mIsDelMode;
            this.mAdapter.notifyDataSetChanged();
        } else if (i == this.mSharedList.size() - 2) {
            this.mIsDelMode = false;
            startActivityForResult(SelectFriendsActivity.newIntent(this, String.valueOf(this.mFeederRecord.getDeviceId()), 2, 0, true, getSharedUserIds()), 1);
        }
    }

    private void addSymbol() {
        this.mSharedList.clear();
        User user = new User();
        this.mSharedList.add(user);
        this.mSharedList.add(user);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSharedList(List<User> list) {
        setViewState(1);
        addSymbol();
        Iterator<User> it = list.iterator();
        while (it.hasNext()) {
            this.mSharedList.add(0, it.next());
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private String getSharedUserIds() {
        ArrayList<User> arrayList = this.mSharedList;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("&");
        Iterator<User> it = this.mSharedList.iterator();
        while (it.hasNext()) {
            sb.append(it.next().getId());
            sb.append("&");
        }
        return sb.toString();
    }

    private void shareOpen(final boolean z) {
        HashMap map = new HashMap();
        map.put("deviceId", this.mFeederRecord.getDeviceId() + "");
        map.put("open", z ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        WebModelRepository.getInstance().feederShareOpen(this, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingShareActivity.1
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                FeederSettingShareActivity.this.mFeederRecord.setShareOpen(z ? 1 : 0);
                FeederSettingShareActivity.this.mFeederRecord.save();
                FeederSettingShareActivity feederSettingShareActivity = FeederSettingShareActivity.this;
                feederSettingShareActivity.refreshFriends(feederSettingShareActivity.mFeederRecord);
                LocalBroadcastManager.getInstance(FeederSettingShareActivity.this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_UPDATE));
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                FeederSettingShareActivity.this.showShortToast(errorInfor.getMsg(), R.drawable.toast_failed);
                if (FeederSettingShareActivity.this.mCheckBox != null) {
                    FeederSettingShareActivity.this.mCheckBox.setChecked(FeederSettingShareActivity.this.mFeederRecord.isShareOpen());
                }
                FeederSettingShareActivity feederSettingShareActivity = FeederSettingShareActivity.this;
                feederSettingShareActivity.refreshFriends(feederSettingShareActivity.mFeederRecord);
            }
        });
    }

    public void refreshFriends(FeederRecord feederRecord) {
        if (isFinishing()) {
            return;
        }
        if (feederRecord.isShareOpen()) {
            this.layout.setVisibility(0);
            this.tvFriendList.setVisibility(0);
        } else {
            this.tvFriendList.setVisibility(8);
            this.layout.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareRemove(final User user) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        map.put("userIds", user.getId());
        WebModelRepository.getInstance().feederShareRemove(this, map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingShareActivity.2
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                FeederSettingShareActivity.this.mSharedList.remove(user);
                FeederSettingShareActivity.this.mAdapter.notifyDataSetChanged();
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                FeederSettingShareActivity.this.showShortToast(errorInfor.getMsg(), R.drawable.toast_failed);
            }
        });
    }

    private void getShareUserList() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        WebModelRepository.getInstance().feederShareUsers(this, map, new PetkitCallback<ArrayList<User>>() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingShareActivity.3
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(ArrayList<User> arrayList) {
                if (arrayList != null) {
                    FeederSettingShareActivity.this.updateSharedList(arrayList);
                }
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                FeederSettingShareActivity.this.setViewState(2);
                FeederSettingShareActivity.this.showShortToast(errorInfor.getMsg(), R.drawable.toast_failed);
            }
        });
    }

    public class MyShareAdapter extends BaseAdapter implements View.OnClickListener {
        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public MyShareAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return FeederSettingShareActivity.this.mSharedList.size();
        }

        @Override // android.widget.Adapter
        public User getItem(int i) {
            return (User) FeederSettingShareActivity.this.mSharedList.get(i);
        }

        @Override // android.widget.Adapter
        @SuppressLint({"InflateParams"})
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(FeederSettingShareActivity.this).inflate(R.layout.adapter_mate_share_list, (ViewGroup) null);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.mate_share_avatar);
                viewHolder.textView = (TextView) view.findViewById(R.id.mate_share_txt);
                ImageButton imageButton = (ImageButton) view.findViewById(R.id.mate_share_del);
                viewHolder.del = imageButton;
                imageButton.setTag(Integer.valueOf(i));
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            view.setTag(Integer.valueOf(i));
            if (i == getCount() - 1) {
                viewHolder.imageView.setImageResource(R.drawable.mate_share_minus);
                viewHolder.textView.setVisibility(4);
                viewHolder.del.setVisibility(8);
            } else if (i == getCount() - 2) {
                viewHolder.imageView.setImageResource(R.drawable.mate_share_add);
                viewHolder.textView.setVisibility(4);
                viewHolder.del.setVisibility(8);
            } else {
                User item = getItem(i);
                ((BaseApplication) FeederSettingShareActivity.this.getApplication()).getAppComponent().imageLoader().loadImage(FeederSettingShareActivity.this, GlideImageConfig.builder().url(item.getAvatar()).imageView(viewHolder.imageView).errorPic(item.getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f).transformation(new GlideRoundTransform(FeederSettingShareActivity.this.getApplicationContext(), (int) DeviceUtils.dpToPixel(FeederSettingShareActivity.this.getApplicationContext(), 5.0f))).build());
                viewHolder.textView.setText(getItem(i).getNick());
                viewHolder.textView.setVisibility(0);
                if (FeederSettingShareActivity.this.mIsDelMode) {
                    viewHolder.del.setVisibility(0);
                    viewHolder.del.setOnClickListener(this);
                } else {
                    viewHolder.del.setVisibility(8);
                    viewHolder.del.setOnClickListener(null);
                }
            }
            return view;
        }

        public class ViewHolder {
            public ImageButton del;
            public ImageView imageView;
            public TextView textView;

            public ViewHolder() {
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (FeederSettingShareActivity.this.mFeederRecord.isShareOpen()) {
                try {
                    int iIntValue = ((Integer) view.getTag()).intValue();
                    FeederSettingShareActivity feederSettingShareActivity = FeederSettingShareActivity.this;
                    feederSettingShareActivity.shareRemove((User) feederSettingShareActivity.mSharedList.get(iIntValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class MyGridView extends GridView {
        public MyGridView(Context context) {
            super(context);
        }

        public MyGridView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
        }
    }
}

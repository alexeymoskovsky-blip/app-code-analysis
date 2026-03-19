package com.petkit.android.activities.feeder.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.internal.AnalyticsEvents;
import com.github.sunnysuperman.commons.utils.CollectionUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.device.mode.PetAmountItem;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederItemData;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.mate.InCallActivity;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.HsDeviceRsp;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class FeederPromptWindow extends PopupWindow {
    private Activity mActivity;
    private FeederItemData mFeederItemData;

    public FeederPromptWindow(Activity activity, FeederItemData feederItemData) {
        super(activity);
        this.mFeederItemData = feederItemData;
        this.mActivity = activity;
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.pop_feeder_item, (ViewGroup) null);
        initView(viewInflate);
        setContentView(viewInflate);
        setWidth((int) (BaseApplication.displayMetrics.widthPixels - (DeviceUtils.dpToPixel(activity, 20.0f) * 2.0f)));
        setHeight(-2);
    }

    @Override // android.widget.PopupWindow
    public void showAtLocation(View view, int i, int i2, int i3) {
        super.showAtLocation(view, i, i2, i3);
    }

    public void showAtLocation(View view) {
        int[] iArrCalculatePopWindowPos = calculatePopWindowPos(view, getContentView());
        showAtLocation(view, 8388659, iArrCalculatePopWindowPos[0], iArrCalculatePopWindowPos[1]);
    }

    private void initView(View view) {
        List<Pet> listFindRelatedPets;
        String str;
        final Pet petById;
        ((TextView) view.findViewById(R.id.feeder_name)).setText(getFeederItemDataNameWithState(this.mFeederItemData));
        FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(this.mFeederItemData.getDeviceId());
        ((TextView) view.findViewById(R.id.feeder_time)).setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mActivity, this.mFeederItemData.getTime()));
        TextView textView = (TextView) view.findViewById(R.id.feeder_amount);
        if (CommonUtils.isSameTimeZoneAsLocal(feederRecordByDeviceId.getLocale(), feederRecordByDeviceId.getTimezone())) {
            view.findViewById(R.id.tv_time_zone).setVisibility(8);
        } else {
            view.findViewById(R.id.tv_time_zone).setVisibility(0);
        }
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.feeder_tags);
        List<PetAmountItem> petAmount = this.mFeederItemData.getPetAmount();
        linearLayout.removeAllViews();
        if (!CollectionUtil.isEmpty(petAmount)) {
            for (PetAmountItem petAmountItem : petAmount) {
                if (!TextUtils.isEmpty(petAmountItem.getPetId())) {
                    if (feederRecordByDeviceId.getDeviceShared() == null) {
                        petById = UserInforUtils.getPetById(petAmountItem.getPetId());
                    } else {
                        List<Pet> pets = feederRecordByDeviceId.getDeviceShared().getPets();
                        if (pets != null) {
                            for (Pet pet : pets) {
                                if (pet.getId().equals(petAmountItem.getPetId())) {
                                    petById = pet;
                                    break;
                                }
                            }
                            petById = null;
                        } else {
                            petById = null;
                        }
                    }
                    if (petById != null) {
                        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.1
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public void onGlobalLayout() {
                                int measuredWidth = linearLayout.getMeasuredWidth();
                                TextView textView2 = (TextView) LayoutInflater.from(FeederPromptWindow.this.mActivity).inflate(R.layout.layout_feeder_pet_tag_item, (ViewGroup) null);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                                layoutParams.leftMargin = ArmsUtils.dip2px(FeederPromptWindow.this.mActivity, 1.5f);
                                layoutParams.rightMargin = ArmsUtils.dip2px(FeederPromptWindow.this.mActivity, 1.5f);
                                textView2.setLayoutParams(layoutParams);
                                textView2.setText(petById.getName());
                                textView2.setMaxWidth((measuredWidth / 3) - ArmsUtils.dip2px(FeederPromptWindow.this.mActivity, 1.5f));
                                linearLayout.addView(textView2);
                                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });
                    }
                }
            }
        }
        if (feederRecordByDeviceId.getDeviceShared() != null) {
            listFindRelatedPets = feederRecordByDeviceId.getDeviceShared().getPets();
        } else {
            listFindRelatedPets = feederRecordByDeviceId.findRelatedPets();
        }
        if (listFindRelatedPets == null || listFindRelatedPets.size() < 2) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
        }
        if (this.mFeederItemData.getStatus() == 3) {
            view.findViewById(R.id.feeder_state_timeout_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_valid_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_parent).setVisibility(0);
            view.findViewById(R.id.feeder_state_doing_anim_view).setVisibility(0);
            textView.setText(FeederUtils.getAmountFormat(this.mFeederItemData.getAmount()) + this.mActivity.getString(FeederUtils.getFeederAmountUnit(this.mFeederItemData.getAmount())));
            view.findViewById(R.id.feeder_state_action_stop).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    FeederPromptWindow.this.stopFeeding();
                }
            });
            ((AnimationDrawable) ((ImageView) view.findViewById(R.id.feeder_doing_point)).getDrawable()).start();
            TextView textView2 = (TextView) view.findViewById(R.id.feeder_state_prompt);
            textView2.setText(this.mActivity.getString(R.string.Mate_share_see_now) + " >");
            textView2.setTextColor(CommonUtils.getColorById(R.color.black));
            HsDeviceRsp.HsDeviceResult hsDeviceResult = HsConsts.getHsDeviceResult(this.mActivity);
            if (hsDeviceResult == null || hsDeviceResult.getOwnerDevices() == null || hsDeviceResult.getOwnerDevices().size() == 0) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        HsDeviceRsp.HsDeviceResult hsDeviceResult2 = HsConsts.getHsDeviceResult(FeederPromptWindow.this.mActivity);
                        if (hsDeviceResult2 == null || hsDeviceResult2.getOwnerDevices() == null || hsDeviceResult2.getOwnerDevices().size() == 0) {
                            return;
                        }
                        if (hsDeviceResult2.getOwnerDevices().size() != 1) {
                            FeederPromptWindow.this.mActivity.startActivity(new Intent(FeederPromptWindow.this.mActivity, (Class<?>) InCallActivity.class));
                            return;
                        }
                        Intent intent = new Intent(FeederPromptWindow.this.mActivity, (Class<?>) InCallActivity.class);
                        intent.putExtra(Constants.EXTRA_HS_DEVICE_DEATILS, hsDeviceResult2.getOwnerDevices().get(0));
                        intent.setFlags(335544320);
                        FeederPromptWindow.this.mActivity.startActivity(intent);
                    }
                });
            }
            str = "Feeding";
        } else if (FeederUtils.checkFeederItemIsTimeout(this.mFeederItemData, feederRecordByDeviceId.getActualTimeZone())) {
            view.findViewById(R.id.feeder_state_timeout_parent).setVisibility(0);
            view.findViewById(R.id.feeder_state_valid_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_anim_view).setVisibility(8);
            textView.setText(FeederUtils.getAmountFormat(this.mFeederItemData.getRealAmount()) + this.mActivity.getString(FeederUtils.getFeederAmountUnit(this.mFeederItemData.getAmount())));
            TextView textView3 = (TextView) view.findViewById(R.id.feeder_state_timeout);
            TextView textView4 = (TextView) view.findViewById(R.id.feeder_state_prompt);
            textView4.setVisibility(0);
            int status = this.mFeederItemData.getStatus();
            if (status == 1) {
                textView3.setText(this.mActivity.getString(R.string.Canceled));
                textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                textView4.setText(this.mFeederItemData.getErrMsg());
                str = AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED;
            } else if (status == 2) {
                textView3.setText(this.mActivity.getString(R.string.Not_start));
                textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                textView4.setText(R.string.Feeder_item_not_start_prompt);
                str = "not_start";
            } else if (CommonUtils.isEmpty(this.mFeederItemData.getErrMsg())) {
                if (CommonUtils.isEmpty(this.mFeederItemData.getCompletedAt())) {
                    textView3.setText(this.mActivity.getString(R.string.Unknown));
                    textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                    textView4.setText(R.string.Feeder_item_unknown_prompt);
                    str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                } else {
                    textView3.setText(this.mActivity.getString(R.string.Complete));
                    textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_complete, 0, 0, 0);
                    textView4.setVisibility(8);
                    str = "Complete";
                }
            } else {
                textView3.setText(this.mActivity.getString(R.string.Not_complete));
                textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                textView4.setText(this.mFeederItemData.getErrMsg());
                str = "Not_complete";
            }
        } else {
            view.findViewById(R.id.feeder_state_timeout_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_valid_parent).setVisibility(0);
            view.findViewById(R.id.feeder_state_doing_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_anim_view).setVisibility(8);
            textView.setText(FeederUtils.getAmountFormat(this.mFeederItemData.getAmount()) + this.mActivity.getString(FeederUtils.getFeederAmountUnit(this.mFeederItemData.getAmount())));
            TextView textView5 = (TextView) view.findViewById(R.id.feeder_state_valid);
            if (this.mFeederItemData.getStatus() == 0) {
                textView5.setText(R.string.Feeder_state_wait);
                textView5.setTextColor(CommonUtils.getColorById(R.color.feeder_main_color));
                textView5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_wait, 0, 0, 0);
            } else {
                textView5.setText(R.string.Canceled);
                textView5.setTextColor(CommonUtils.getColorById(R.color.gray));
                textView5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_canceled, 0, 0, 0);
            }
            if (this.mFeederItemData.getSrc() == 1) {
                view.findViewById(R.id.feeder_state_action_text).setVisibility(8);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.feeder_state_action_checkbox);
                checkBox.setVisibility(0);
                checkBox.setChecked(this.mFeederItemData.getStatus() != 1);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.4
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z == (FeederPromptWindow.this.mFeederItemData.getStatus() != 1)) {
                            return;
                        }
                        if (z) {
                            FeederPromptWindow.this.recoveryFeederItemData();
                        } else {
                            FeederPromptWindow.this.removeFeederItemData();
                        }
                    }
                });
            } else {
                view.findViewById(R.id.feeder_state_action_checkbox).setVisibility(8);
                TextView textView6 = (TextView) view.findViewById(R.id.feeder_state_action_text);
                textView6.setVisibility(0);
                textView6.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        FeederPromptWindow.this.removeFeederItemData();
                    }
                });
            }
            view.findViewById(R.id.feeder_state_prompt).setVisibility(8);
            str = "Wait";
        }
        new HashMap().put("type", str);
    }

    public void updateView() {
        this.mFeederItemData = FeederUtils.getFeederItemData(this.mFeederItemData.getDeviceId(), this.mFeederItemData.getDay(), this.mFeederItemData.getTime());
        initView(getContentView());
    }

    private String getFeederItemDataNameWithState(FeederItemData feederItemData) {
        int src = feederItemData.getSrc();
        if (src == 2 || src == 3) {
            return this.mActivity.getString(R.string.Feeder_add_manual_online);
        }
        if (src == 4) {
            return this.mActivity.getString(R.string.Feeder_add_manual_offline);
        }
        return feederItemData.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recoveryFeederItemData() {
        if (this.mFeederItemData == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederItemData.getDeviceId()));
        map.put("day", String.valueOf(this.mFeederItemData.getDay()));
        map.put("id", this.mFeederItemData.getItemId());
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_RESTORE_DAILY_FEED, map, new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    FeederPromptWindow.this.mFeederItemData.setStatus(0);
                    FeederPromptWindow.this.mFeederItemData.save();
                    TextView textView = (TextView) FeederPromptWindow.this.getContentView().findViewById(R.id.feeder_state_valid);
                    textView.setText(R.string.Feeder_state_wait);
                    textView.setTextColor(CommonUtils.getColorById(R.color.feeder_main_color));
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_wait, 0, 0, 0);
                    LocalBroadcastManager.getInstance(FeederPromptWindow.this.mActivity).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_STATE_CHANGED));
                    return;
                }
                PetkitToast.showShortToast(FeederPromptWindow.this.mActivity, resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
                ((CheckBox) FeederPromptWindow.this.getContentView().findViewById(R.id.feeder_state_action_checkbox)).setChecked(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFeederItemData() {
        if (this.mFeederItemData == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederItemData.getDeviceId()));
        map.put("day", String.valueOf(this.mFeederItemData.getDay()));
        map.put("id", this.mFeederItemData.getItemId());
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_REMOVE_DAILY_FEED, map, new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    if (FeederPromptWindow.this.mFeederItemData.getSrc() == 1) {
                        FeederPromptWindow.this.mFeederItemData.setStatus(1);
                        FeederPromptWindow.this.mFeederItemData.save();
                        TextView textView = (TextView) FeederPromptWindow.this.getContentView().findViewById(R.id.feeder_state_valid);
                        textView.setText(R.string.Canceled);
                        textView.setTextColor(CommonUtils.getColorById(R.color.gray));
                        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_canceled, 0, 0, 0);
                    } else {
                        FeederPromptWindow.this.mFeederItemData.delete();
                        FeederPromptWindow.this.dismiss();
                    }
                    LocalBroadcastManager.getInstance(FeederPromptWindow.this.mActivity).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_STATE_CHANGED));
                    return;
                }
                PetkitToast.showShortToast(FeederPromptWindow.this.mActivity, resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
                ((CheckBox) FeederPromptWindow.this.getContentView().findViewById(R.id.feeder_state_action_checkbox)).setChecked(true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopFeeding() {
        int iIntValue = Integer.valueOf(CommonUtils.getDateStringByOffset(0)).intValue();
        new HashMap().put("type", "feeddetail");
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederItemData.getDeviceId()));
        map.put("day", String.valueOf(iIntValue));
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_CANCEL_REALTIME_FEED, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.activities.feeder.widget.FeederPromptWindow.8
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    LocalBroadcastManager.getInstance(FeederPromptWindow.this.mActivity).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_STATE_CHANGED));
                } else {
                    PetkitToast.showShortToast(FeederPromptWindow.this.mActivity, resultStringRsp.getError().getMsg());
                }
            }
        }, false);
    }

    private int[] calculatePopWindowPos(View view, View view2) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr2);
        int height = view.getHeight();
        int i = BaseApplication.displayMetrics.heightPixels;
        view2.measure(0, 0);
        int measuredHeight = view2.getMeasuredHeight();
        boolean z = ((i - iArr2[1]) - height) - view.getHeight() < measuredHeight;
        iArr[0] = (int) DeviceUtils.dpToPixel(this.mActivity, 20.0f);
        if (z) {
            iArr[1] = (iArr2[1] - measuredHeight) + view.getHeight();
        } else {
            iArr[1] = (iArr2[1] + height) - view.getHeight();
        }
        return iArr;
    }
}

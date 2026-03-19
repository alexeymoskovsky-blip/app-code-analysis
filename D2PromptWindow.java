package com.petkit.android.activities.d2.widget;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.internal.AnalyticsEvents;
import com.github.sunnysuperman.commons.utils.CollectionUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.d2.mode.D2ItemData;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.device.mode.PetAmountItem;
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
public class D2PromptWindow extends PopupWindow {
    private D2ItemData d2ItemData;
    private D2Record d2Record;
    private Activity mActivity;
    private RelativeLayout rlMention;

    public D2PromptWindow(Activity activity, D2ItemData d2ItemData) {
        super(activity);
        this.d2ItemData = d2ItemData;
        this.mActivity = activity;
        this.d2Record = D2Utils.getD2RecordByDeviceId(d2ItemData.getDeviceId());
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
        ((TextView) view.findViewById(R.id.feeder_name)).setText(getItemDataNameWithState(this.d2ItemData));
        D2Record d2RecordByDeviceId = D2Utils.getD2RecordByDeviceId(this.d2ItemData.getDeviceId());
        this.rlMention = (RelativeLayout) view.findViewById(R.id.ll_mention);
        if (d2RecordByDeviceId.getState().getBatteryStatus() != 0 && this.d2ItemData.getIsExecuted() == 0 && this.d2ItemData.getStatus() == 0) {
            this.rlMention.setVisibility(0);
        } else {
            this.rlMention.setVisibility(8);
        }
        ((TextView) view.findViewById(R.id.feeder_time)).setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mActivity, this.d2ItemData.getTime()));
        TextView textView = (TextView) view.findViewById(R.id.feeder_amount);
        if (CommonUtils.isSameTimeZoneAsLocal(d2RecordByDeviceId.getLocale(), d2RecordByDeviceId.getTimezone())) {
            view.findViewById(R.id.tv_time_zone).setVisibility(8);
        } else {
            view.findViewById(R.id.tv_time_zone).setVisibility(0);
        }
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.feeder_tags);
        List<PetAmountItem> petAmount = this.d2ItemData.getPetAmount();
        linearLayout.removeAllViews();
        if (!CollectionUtil.isEmpty(petAmount)) {
            for (PetAmountItem petAmountItem : petAmount) {
                if (!TextUtils.isEmpty(petAmountItem.getPetId())) {
                    if (d2RecordByDeviceId.getDeviceShared() == null) {
                        petById = UserInforUtils.getPetById(petAmountItem.getPetId());
                    } else {
                        List<Pet> pets = d2RecordByDeviceId.getDeviceShared().getPets();
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
                        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.1
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public void onGlobalLayout() {
                                int measuredWidth = linearLayout.getMeasuredWidth();
                                TextView textView2 = (TextView) LayoutInflater.from(D2PromptWindow.this.mActivity).inflate(R.layout.layout_feeder_pet_tag_item, (ViewGroup) null);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                                layoutParams.leftMargin = ArmsUtils.dip2px(D2PromptWindow.this.mActivity, 1.5f);
                                layoutParams.rightMargin = ArmsUtils.dip2px(D2PromptWindow.this.mActivity, 1.5f);
                                textView2.setLayoutParams(layoutParams);
                                textView2.setText(petById.getName());
                                textView2.setMaxWidth((measuredWidth / 3) - ArmsUtils.dip2px(D2PromptWindow.this.mActivity, 1.5f));
                                linearLayout.addView(textView2);
                                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });
                    }
                }
            }
        }
        if (d2RecordByDeviceId.getDeviceShared() != null) {
            listFindRelatedPets = d2RecordByDeviceId.getDeviceShared().getPets();
        } else {
            listFindRelatedPets = d2RecordByDeviceId.findRelatedPets();
        }
        if (listFindRelatedPets == null || listFindRelatedPets.size() < 2) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
        }
        if (this.d2ItemData.getStatus() == 3) {
            view.findViewById(R.id.feeder_state_timeout_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_valid_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_parent).setVisibility(0);
            view.findViewById(R.id.feeder_state_doing_anim_view).setVisibility(0);
            textView.setText(D2Utils.getAmountFormat(this.d2ItemData.getAmount()) + this.mActivity.getString(D2Utils.getD2AmountUnit(this.d2ItemData.getAmount())));
            view.findViewById(R.id.feeder_state_action_stop).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    D2PromptWindow.this.stopFeeding();
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
                textView2.setTextColor(CommonUtils.getColorById(R.color.orange));
                textView2.setVisibility(0);
                textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        HsDeviceRsp.HsDeviceResult hsDeviceResult2 = HsConsts.getHsDeviceResult(D2PromptWindow.this.mActivity);
                        if (hsDeviceResult2 == null || hsDeviceResult2.getOwnerDevices() == null || hsDeviceResult2.getOwnerDevices().size() == 0) {
                            return;
                        }
                        if (hsDeviceResult2.getOwnerDevices().size() != 1) {
                            D2PromptWindow.this.mActivity.startActivity(new Intent(D2PromptWindow.this.mActivity, (Class<?>) InCallActivity.class));
                            return;
                        }
                        Intent intent = new Intent(D2PromptWindow.this.mActivity, (Class<?>) InCallActivity.class);
                        intent.putExtra(Constants.EXTRA_HS_DEVICE_DEATILS, hsDeviceResult2.getOwnerDevices().get(0));
                        intent.setFlags(335544320);
                        D2PromptWindow.this.mActivity.startActivity(intent);
                    }
                });
            }
            str = "Feeding";
        } else if (D2Utils.checkD2ItemIsTimeout(this.d2ItemData, d2RecordByDeviceId.getActualTimeZone())) {
            view.findViewById(R.id.feeder_state_timeout_parent).setVisibility(0);
            view.findViewById(R.id.feeder_state_valid_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_anim_view).setVisibility(8);
            textView.setText(D2Utils.getAmountFormat(this.d2ItemData.getRealAmount()) + this.mActivity.getString(D2Utils.getD2AmountUnit(this.d2ItemData.getAmount())));
            TextView textView3 = (TextView) view.findViewById(R.id.feeder_state_timeout);
            TextView textView4 = (TextView) view.findViewById(R.id.feeder_state_prompt);
            textView4.setTextColor(CommonUtils.getColorById(R.color.orange));
            textView4.setVisibility(0);
            int status = this.d2ItemData.getStatus();
            if (status == 1) {
                textView3.setText(this.mActivity.getString(R.string.Canceled));
                textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                textView4.setText(this.d2ItemData.getErrMsg());
                str = AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED;
            } else if (status == 2) {
                textView3.setText(this.mActivity.getString(R.string.Not_start));
                textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                textView4.setText(R.string.Feeder_item_not_start_prompt);
                str = "not_start";
            } else if (CommonUtils.isEmpty(this.d2ItemData.getErrMsg())) {
                if (CommonUtils.isEmpty(this.d2ItemData.getCompletedAt())) {
                    textView3.setText(this.mActivity.getString(R.string.Unknown));
                    textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_complete, 0, 0, 0);
                    textView4.setText(R.string.Feeder_item_unknown_prompt);
                    this.rlMention.setVisibility(8);
                    textView.setText("?" + this.mActivity.getString(R.string.Feeder_unit));
                    str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                } else {
                    textView3.setText(this.mActivity.getString(R.string.Complete));
                    textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_complete, 0, 0, 0);
                    textView4.setVisibility(8);
                    str = "Complete";
                }
            } else if (CommonUtils.isEmpty(this.d2ItemData.getErrCode()) && !CommonUtils.isEmpty(this.d2ItemData.getCompletedAt())) {
                textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_complete, 0, 0, 0);
                textView4.setText(this.d2ItemData.getErrMsg());
                textView3.setText(this.mActivity.getString(R.string.Complete));
                str = "Complete";
            } else {
                if (!CommonUtils.isEmpty(this.d2ItemData.getErrCode()) && (this.d2ItemData.getErrCode().equals("ia-1") || this.d2ItemData.getErrCode().equals("em-1") || this.d2ItemData.getErrCode().equals("le-1"))) {
                    textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                    textView4.setText(this.d2ItemData.getErrMsg());
                    textView3.setText(this.mActivity.getString(R.string.Not_complete));
                } else {
                    textView3.setText(this.mActivity.getString(R.string.Not_complete));
                    textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_failed, 0, 0, 0);
                    textView4.setText(this.d2ItemData.getErrMsg());
                }
                str = "Not_complete";
            }
        } else {
            view.findViewById(R.id.feeder_state_timeout_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_valid_parent).setVisibility(0);
            view.findViewById(R.id.feeder_state_doing_parent).setVisibility(8);
            view.findViewById(R.id.feeder_state_doing_anim_view).setVisibility(8);
            textView.setText(D2Utils.getAmountFormat(this.d2ItemData.getAmount()) + this.mActivity.getString(D2Utils.getD2AmountUnit(this.d2ItemData.getAmount())));
            TextView textView5 = (TextView) view.findViewById(R.id.feeder_state_valid);
            if (this.d2ItemData.getStatus() == 0) {
                textView5.setText(R.string.Feeder_state_wait);
                textView5.setTextColor(CommonUtils.getColorById(R.color.feeder_main_color));
                textView5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_wait, 0, 0, 0);
            } else {
                textView5.setText(R.string.Canceled);
                textView5.setTextColor(CommonUtils.getColorById(R.color.gray));
                textView5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_canceled, 0, 0, 0);
            }
            if (this.d2ItemData.getSrc() == 1) {
                view.findViewById(R.id.feeder_state_action_text).setVisibility(8);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.feeder_state_action_checkbox);
                checkBox.setVisibility(0);
                checkBox.setChecked(this.d2ItemData.getStatus() != 1);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.4
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z == (D2PromptWindow.this.d2ItemData.getStatus() != 1)) {
                            return;
                        }
                        if (z) {
                            D2PromptWindow.this.recoveryD2ItemData();
                        } else {
                            D2PromptWindow.this.removeFeederItemData();
                        }
                    }
                });
            } else {
                view.findViewById(R.id.feeder_state_action_checkbox).setVisibility(8);
                TextView textView6 = (TextView) view.findViewById(R.id.feeder_state_action_text);
                textView6.setVisibility(0);
                textView6.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        D2PromptWindow.this.removeFeederItemData();
                    }
                });
            }
            view.findViewById(R.id.feeder_state_prompt).setVisibility(8);
            str = "Wait";
        }
        new HashMap().put("type", str);
    }

    public void updateView() {
        this.d2ItemData = D2Utils.getD2ItemData(this.d2ItemData.getDeviceId(), this.d2ItemData.getDay(), this.d2ItemData.getTime());
        initView(getContentView());
    }

    private String getItemDataNameWithState(D2ItemData d2ItemData) {
        int src = d2ItemData.getSrc();
        if (src == 2 || src == 3) {
            return this.mActivity.getString(R.string.Feeder_add_manual_online);
        }
        if (src == 4) {
            return this.mActivity.getString(R.string.Feeder_add_manual_offline);
        }
        return d2ItemData.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recoveryD2ItemData() {
        if (this.d2ItemData == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.d2ItemData.getDeviceId()));
        map.put("day", String.valueOf(this.d2ItemData.getDay()));
        map.put("id", this.d2ItemData.getItemId());
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDERMINI_RESTORE_DAILY_FEED, map, new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    D2PromptWindow.this.d2ItemData.setStatus(0);
                    D2PromptWindow.this.d2ItemData.save();
                    TextView textView = (TextView) D2PromptWindow.this.getContentView().findViewById(R.id.feeder_state_valid);
                    textView.setText(R.string.Feeder_state_wait);
                    textView.setTextColor(CommonUtils.getColorById(R.color.feeder_main_color));
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_wait, 0, 0, 0);
                    if (D2PromptWindow.this.d2Record.getState().getBatteryStatus() != 0) {
                        D2PromptWindow.this.d2ItemData.setIsExecuted(0);
                        D2PromptWindow.this.rlMention.setVisibility(0);
                    }
                    LocalBroadcastManager.getInstance(D2PromptWindow.this.mActivity).sendBroadcast(new Intent(D2Utils.BROADCAST_D2_STATE_CHANGED));
                    return;
                }
                PetkitToast.showShortToast(D2PromptWindow.this.mActivity, resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
                ((CheckBox) D2PromptWindow.this.getContentView().findViewById(R.id.feeder_state_action_checkbox)).setChecked(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFeederItemData() {
        if (this.d2ItemData == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.d2ItemData.getDeviceId()));
        map.put("day", String.valueOf(this.d2ItemData.getDay()));
        map.put("id", this.d2ItemData.getItemId());
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDERMINI_REMOVE_DAILY_FEED, map, new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    if (D2PromptWindow.this.d2ItemData.getSrc() == 1) {
                        D2PromptWindow.this.d2ItemData.setStatus(1);
                        D2PromptWindow.this.d2ItemData.save();
                        TextView textView = (TextView) D2PromptWindow.this.getContentView().findViewById(R.id.feeder_state_valid);
                        textView.setText(R.string.Canceled);
                        textView.setTextColor(CommonUtils.getColorById(R.color.gray));
                        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.feeder_state_canceled, 0, 0, 0);
                    } else {
                        D2PromptWindow.this.d2ItemData.delete();
                        D2PromptWindow.this.dismiss();
                    }
                    LocalBroadcastManager.getInstance(D2PromptWindow.this.mActivity).sendBroadcast(new Intent(D2Utils.BROADCAST_D2_STATE_CHANGED));
                    return;
                }
                PetkitToast.showShortToast(D2PromptWindow.this.mActivity, resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
                ((CheckBox) D2PromptWindow.this.getContentView().findViewById(R.id.feeder_state_action_checkbox)).setChecked(true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopFeeding() {
        int iIntValue = Integer.valueOf(CommonUtils.getDateStringByOffset(0)).intValue();
        new HashMap().put("where", "feeddetail");
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.d2ItemData.getDeviceId()));
        map.put("day", String.valueOf(iIntValue));
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDERMINI_CANCEL_REALTIME_FEED, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this.mActivity, true) { // from class: com.petkit.android.activities.d2.widget.D2PromptWindow.8
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    LocalBroadcastManager.getInstance(D2PromptWindow.this.mActivity).sendBroadcast(new Intent(D2Utils.BROADCAST_D2_STATE_CHANGED));
                } else {
                    PetkitToast.showShortToast(D2PromptWindow.this.mActivity, resultStringRsp.getError().getMsg());
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

package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgRecord;
import com.petkit.android.activities.petkitBleDevice.hg.utils.HgUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class HgHomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public Disposable disposable;
    public FrameLayout flState;
    public boolean isCentigrade;
    public boolean isHave;
    public ImageView ivLoading;
    public long leftTime;
    public TextView tvR2Temp;
    public TextView tvTimePrompt;
    public TextView tvVirtualDevice;

    public final float CentigradeToFahrenheit(int i, float f) {
        if (i == 1) {
            f = (f * 1.8f) + 32.0f;
        }
        return ((int) (f * 10.0f)) / 10.0f;
    }

    public HgHomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvR2Temp = (TextView) view.findViewById(R.id.tv_r2_temp);
        this.tvTimePrompt = (TextView) view.findViewById(R.id.tv_time_prompt);
        this.ivLoading = (ImageView) view.findViewById(R.id.iv_loading);
        this.tvVirtualDevice = (TextView) view.findViewById(R.id.tv_virtual_device);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData) {
        int length;
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_tran);
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.home_chart_text_color));
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isHgNeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        if (homeDeviceData.getData().getShared() == null) {
            this.tvDeviceName.setText(homeDeviceData.getData().getName());
        } else {
            this.tvDeviceName.setText(homeDeviceData.getData().getShared().getOwnerNick() + "-" + homeDeviceData.getData().getName());
        }
        String string = this.context.getString(R.string.symbol_temperature);
        this.isCentigrade = true;
        if (UserInforUtils.getCurrentLoginResult().getSettings().getTempUnit() == 1) {
            string = this.context.getString(R.string.fahrenheit_temperature);
            this.isCentigrade = false;
        }
        HgRecord orCreateHgRecordByDeviceId = HgUtils.getOrCreateHgRecordByDeviceId(homeDeviceData.getId());
        if (orCreateHgRecordByDeviceId != null) {
            if (homeDeviceData.getData().getIsActive() == 0) {
                String str = "——" + string;
                this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, "——", -1, 28, true));
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setText(this.context.getString(R.string.Not_activate));
                this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                closeDryingRemainTimer();
            } else if (!PetkitBleDeviceManager.getInstance().checkDeviceState(orCreateHgRecordByDeviceId.getMac())) {
                String str2 = "——" + string;
                this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, "——", -1, 28, true));
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setText(this.context.getString(R.string.Disconnect));
                this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                closeDryingRemainTimer();
            } else {
                String binaryString = Integer.toBinaryString(orCreateHgRecordByDeviceId.getErrCode());
                int length2 = binaryString.length() - 1;
                while (true) {
                    if (length2 < 0) {
                        length = -1;
                        break;
                    } else {
                        if (String.valueOf(binaryString.charAt(length2)).equals("1")) {
                            length = (binaryString.length() - length2) - 1;
                            break;
                        }
                        length2--;
                    }
                }
                if (length != -1) {
                    this.tvDeviceState.setVisibility(0);
                    this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
                    this.tvDeviceState.setPadding(ArmsUtils.dip2px(this.context, 5.0f), 0, ArmsUtils.dip2px(this.context, 5.0f), 0);
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                    if (length == 0 || length == 4) {
                        this.tvDeviceState.setText(this.context.getString(R.string.Aqh1_system_error_tip));
                    } else if (length == 5 || length == 6 || length == 7 || length == 8 || length == 9 || length == 10) {
                        this.tvDeviceState.setText(this.context.getString(R.string.HG_err_temperature_title));
                    } else if (length == 2 || length == 3) {
                        this.tvDeviceState.setText(this.context.getString(R.string.Aqh1_system_error_tip));
                    } else if (length == 1) {
                        this.tvDeviceState.setText(this.context.getString(R.string.HG_err_power_title));
                    }
                    String str3 = "" + CentigradeToFahrenheit(0, homeDeviceData.getData().getTemp() / 10.0f);
                    String str4 = str3 + string;
                    this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str4), str4, str3, -1, 28, true));
                    this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                    closeDryingRemainTimer();
                } else {
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    if (homeDeviceData.getData().getStatus().getStatus() == 0) {
                        String str5 = "" + CentigradeToFahrenheit(0, homeDeviceData.getData().getTemp() / 10.0f);
                        String str6 = str5 + string;
                        this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str6), str6, str5, -1, 28, true));
                        if (homeDeviceData.getData().getTypeCode() == 2 && homeDeviceData.getData().getOzoneState() == 1) {
                            this.tvDeviceState.setText(this.context.getString(R.string.HG_is_sterilization));
                        } else {
                            this.tvDeviceState.setText(this.context.getString(R.string.Cozy_await));
                        }
                        this.tvDeviceState.setVisibility(0);
                        this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                        closeDryingRemainTimer();
                    } else if (homeDeviceData.getData().getStatus().getStatus() == 1) {
                        openDryingRemainTimer(orCreateHgRecordByDeviceId.getRemainTime(), homeDeviceData.getId());
                        this.tvDeviceState.setVisibility(0);
                        this.tvTimePrompt.setText(this.context.getString(R.string.Dry_remain_time));
                        this.isHave = false;
                        if (orCreateHgRecordByDeviceId.getModeDataList() != null) {
                            int i = 0;
                            while (true) {
                                if (i >= orCreateHgRecordByDeviceId.getModeDataList().size()) {
                                    break;
                                }
                                if (homeDeviceData.getData().getSettings().getDryTime() == orCreateHgRecordByDeviceId.getModeDataList().get(i).getDryTime() && homeDeviceData.getData().getSettings().getRev() == orCreateHgRecordByDeviceId.getModeDataList().get(i).getRev() && CentigradeToFahrenheit(0, homeDeviceData.getData().getSettings().getTargetTemp() / 100.0f) == CentigradeToFahrenheit(UserInforUtils.getCurrentLoginResult().getSettings().getTempUnit(), orCreateHgRecordByDeviceId.getModeDataList().get(i).getTemp() / 100.0f)) {
                                    if (orCreateHgRecordByDeviceId.getModeDataList().get(i).getModeType() == 1) {
                                        TextView textView = this.tvDeviceState;
                                        Context context = this.context;
                                        textView.setText(context.getString(R.string.Mode_of, context.getString(R.string.Comfort)));
                                    } else if (orCreateHgRecordByDeviceId.getModeDataList().get(i).getModeType() == 2) {
                                        TextView textView2 = this.tvDeviceState;
                                        Context context2 = this.context;
                                        textView2.setText(context2.getString(R.string.Mode_of, context2.getString(R.string.Standard)));
                                    } else if (orCreateHgRecordByDeviceId.getModeDataList().get(i).getModeType() == 3) {
                                        TextView textView3 = this.tvDeviceState;
                                        Context context3 = this.context;
                                        textView3.setText(context3.getString(R.string.Mode_of, context3.getString(R.string.Fast)));
                                    } else if (orCreateHgRecordByDeviceId.getModeDataList().get(i).getModeType() == 0) {
                                        this.tvDeviceState.setText(this.context.getString(R.string.Mode_of, orCreateHgRecordByDeviceId.getModeDataList().get(i).getModeName()));
                                    }
                                    this.isHave = true;
                                } else {
                                    i++;
                                }
                            }
                            if (!this.isHave) {
                                TextView textView4 = this.tvDeviceState;
                                Context context4 = this.context;
                                textView4.setText(context4.getString(R.string.Mode_of, context4.getString(R.string.Customize)));
                            }
                        }
                    } else if (homeDeviceData.getData().getStatus().getStatus() == 2) {
                        this.tvR2Temp.setText(TimeUtils.getInstance().secondsToMinuteAndSeconds(orCreateHgRecordByDeviceId.getRemainTime()));
                        this.tvDeviceState.setText(this.context.getString(R.string.State_pausing));
                        this.tvDeviceState.setVisibility(0);
                        this.tvTimePrompt.setText(this.context.getString(R.string.Dry_remain_time));
                        closeDryingRemainTimer();
                    } else if (homeDeviceData.getData().getStatus().getStatus() == 3) {
                        String str7 = "" + CentigradeToFahrenheit(0, homeDeviceData.getData().getTemp() / 10.0f);
                        String str8 = str7 + string;
                        this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str8), str8, str7, -1, 28, true));
                        this.tvDeviceState.setText(this.context.getString(R.string.Cozy_await));
                        this.tvDeviceState.setVisibility(0);
                        this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                        closeDryingRemainTimer();
                    }
                }
            }
        }
        if (homeDeviceData.getId() == -1) {
            this.tvVirtualDevice.setVisibility(0);
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceState.setVisibility(8);
            if (orCreateHgRecordByDeviceId.getTypeCode() == 1) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.HG_name_default));
            } else if (orCreateHgRecordByDeviceId.getTypeCode() == 2) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.HG_p_name_default));
            }
            this.tvR2Temp.setText(39.8d + string);
            return;
        }
        this.tvVirtualDevice.setVisibility(8);
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isHgNeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        this.tvDeviceState.setVisibility(0);
    }

    public void openDryingRemainTimer(long j, long j2) {
        if (j < 0) {
            j = 0;
        }
        this.leftTime = j;
        if (this.disposable == null) {
            this.disposable = Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.home.adapter.holder.HgHomeDeviceViewHolder$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$openDryingRemainTimer$0((Long) obj);
                }
            });
        }
    }

    public final /* synthetic */ void lambda$openDryingRemainTimer$0(Long l) throws Exception {
        TextView textView = this.tvR2Temp;
        if (textView != null) {
            textView.setText(TimeUtils.getInstance().secondsToMinuteAndSeconds(this.leftTime));
        }
        long j = this.leftTime - 1;
        this.leftTime = j;
        if (j < 0) {
            this.leftTime = 0L;
        }
    }

    public void closeDryingRemainTimer() {
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }

    public void startLoad() {
        this.ivLoading.setVisibility(0);
        if (this.ivLoading.getVisibility() == 0) {
            this.ivLoading.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.rotate_anim));
        }
    }

    public void stopLoad() {
        this.ivLoading.clearAnimation();
        this.ivLoading.setVisibility(4);
    }
}

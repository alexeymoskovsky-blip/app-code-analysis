package com.petkit.android.activities.home.adapter.newholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.NewCardOnClickListener;
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
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class HgHomeLargeDeviceViewHolder extends BaseHomeLargeDeviceViewHolder {
    public final Context context;
    public Disposable disposable;
    public boolean isCentigrade;
    public boolean isHave;
    public long leftTime;
    public TextView tvStatus;

    public HgHomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvStatus = (TextView) view.findViewById(R.id.tv_status);
    }

    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData, int i, NewCardOnClickListener newCardOnClickListener) {
        int length;
        this.tvMode.setVisibility(8);
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
        if (homeDeviceData.getData().getIsActive() == 0) {
            String str = "--" + string;
            this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, string, this.context.getResources().getColor(R.color.home_black_111111), 12, true));
            setUnconnectDeviceState();
            this.tvDeviceState.setText(this.context.getString(R.string.Not_activate));
            this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
            this.tvStatus.setText("");
            closeDryingRemainTimer();
        } else if (!PetkitBleDeviceManager.getInstance().checkDeviceState(orCreateHgRecordByDeviceId.getMac())) {
            String str2 = "--" + string;
            this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, string, this.context.getResources().getColor(R.color.home_black_111111), 12, true));
            setUnconnectDeviceState();
            this.tvDeviceState.setText(R.string.Disconnect);
            this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
            this.tvStatus.setText("");
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
                setWarnDeviceState();
                this.tvStatus.setText("");
                if (length == 0 || length == 4) {
                    this.tvDeviceState.setText(this.context.getString(R.string.Aqh1_system_error_tip));
                } else if (length == 5 || length == 6 || length == 7 || length == 8 || length == 9 || length == 10) {
                    this.tvDeviceState.setText(this.context.getString(R.string.HG_err_temperature_title));
                } else if (length == 2 || length == 3) {
                    this.tvDeviceState.setText(this.context.getString(R.string.Aqh1_system_error_tip));
                } else if (length == 1) {
                    this.tvDeviceState.setText(this.context.getString(R.string.HG_err_power_title));
                }
                String str3 = CentigradeToFahrenheit(0, homeDeviceData.getData().getTemp() / 10.0f) + string;
                this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, string, this.context.getResources().getColor(R.color.home_black_111111), 12, true));
                this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                closeDryingRemainTimer();
            } else {
                this.rlDeviceState.setVisibility(8);
                if (homeDeviceData.getData().getStatus().getStatus() == 0) {
                    String str4 = CentigradeToFahrenheit(0, homeDeviceData.getData().getTemp() / 10.0f) + string;
                    this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str4), str4, string, this.context.getResources().getColor(R.color.home_black_111111), 12, true));
                    if (homeDeviceData.getData().getTypeCode() == 2 && homeDeviceData.getData().getOzoneState() == 1) {
                        this.tvStatus.setText(this.context.getString(R.string.HG_is_sterilization));
                    } else {
                        this.tvStatus.setText(this.context.getString(R.string.Cozy_await));
                    }
                    this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                    closeDryingRemainTimer();
                } else if (homeDeviceData.getData().getStatus().getStatus() == 1) {
                    openDryingRemainTimer(orCreateHgRecordByDeviceId.getRemainTime(), homeDeviceData.getId());
                    this.tvTimePrompt.setText(this.context.getString(R.string.Dry_remain_time));
                    this.isHave = false;
                    if (orCreateHgRecordByDeviceId.getModeDataList() != null) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= orCreateHgRecordByDeviceId.getModeDataList().size()) {
                                break;
                            }
                            if (homeDeviceData.getData().getSettings().getDryTime() == orCreateHgRecordByDeviceId.getModeDataList().get(i2).getDryTime() && homeDeviceData.getData().getSettings().getRev() == orCreateHgRecordByDeviceId.getModeDataList().get(i2).getRev() && CentigradeToFahrenheit(0, homeDeviceData.getData().getSettings().getTargetTemp() / 100.0f) == CentigradeToFahrenheit(UserInforUtils.getCurrentLoginResult().getSettings().getTempUnit(), orCreateHgRecordByDeviceId.getModeDataList().get(i2).getTemp() / 100.0f)) {
                                if (orCreateHgRecordByDeviceId.getModeDataList().get(i2).getModeType() == 1) {
                                    TextView textView = this.tvStatus;
                                    Context context = this.context;
                                    textView.setText(context.getString(R.string.Mode_of, context.getString(R.string.Comfort)));
                                } else if (orCreateHgRecordByDeviceId.getModeDataList().get(i2).getModeType() == 2) {
                                    TextView textView2 = this.tvStatus;
                                    Context context2 = this.context;
                                    textView2.setText(context2.getString(R.string.Mode_of, context2.getString(R.string.Standard)));
                                } else if (orCreateHgRecordByDeviceId.getModeDataList().get(i2).getModeType() == 3) {
                                    TextView textView3 = this.tvStatus;
                                    Context context3 = this.context;
                                    textView3.setText(context3.getString(R.string.Mode_of, context3.getString(R.string.Fast)));
                                } else if (orCreateHgRecordByDeviceId.getModeDataList().get(i2).getModeType() == 0) {
                                    this.tvStatus.setText(this.context.getString(R.string.Mode_of, orCreateHgRecordByDeviceId.getModeDataList().get(i2).getModeName()));
                                }
                                this.isHave = true;
                            } else {
                                i2++;
                            }
                        }
                        if (!this.isHave) {
                            TextView textView4 = this.tvStatus;
                            Context context4 = this.context;
                            textView4.setText(context4.getString(R.string.Mode_of, context4.getString(R.string.Customize)));
                        }
                    }
                } else if (homeDeviceData.getData().getStatus().getStatus() == 2) {
                    this.tvTime.setText(TimeUtils.getInstance().secondsToMinuteAndSeconds(orCreateHgRecordByDeviceId.getRemainTime()));
                    this.tvStatus.setText(this.context.getString(R.string.State_pausing));
                    this.tvTimePrompt.setText(this.context.getString(R.string.Dry_remain_time));
                    closeDryingRemainTimer();
                } else if (homeDeviceData.getData().getStatus().getStatus() == 3) {
                    String str5 = CentigradeToFahrenheit(0, homeDeviceData.getData().getTemp() / 10.0f) + string;
                    this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str5), str5, string, this.context.getResources().getColor(R.color.home_black_111111), 12, true));
                    this.tvStatus.setText(this.context.getString(R.string.Cozy_await));
                    this.tvTimePrompt.setText(this.context.getString(R.string.Box_temperature));
                    closeDryingRemainTimer();
                }
            }
        }
        if (homeDeviceData.getId() == -1) {
            this.rlDeviceState.setVisibility(0);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.rlDeviceState.setBackgroundResource(R.drawable.solid_home_device_new_state_virtual_experience);
            this.tvDeviceState.setText(R.string.Virtual_experience);
            this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
            if (orCreateHgRecordByDeviceId.getTypeCode() == 1) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.HG_name_default));
            } else if (orCreateHgRecordByDeviceId.getTypeCode() == 2) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.HG_p_name_default));
            }
            String str6 = 39.8d + string;
            this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str6), str6, string, this.context.getResources().getColor(R.color.home_black_111111), 12, true));
            return;
        }
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
    }

    public void openDryingRemainTimer(long j, long j2) {
        if (j < 0) {
            j = 0;
        }
        this.leftTime = j;
        if (this.disposable == null) {
            this.disposable = Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.home.adapter.newholder.HgHomeLargeDeviceViewHolder$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$openDryingRemainTimer$0((Long) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDryingRemainTimer$0(Long l) throws Exception {
        TextView textView = this.tvTime;
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

    private float CentigradeToFahrenheit(int i, float f) {
        if (i == 1) {
            return new BigDecimal((f * 1.8f) + 32.0f).setScale(1, 4).floatValue();
        }
        return new BigDecimal(f).setScale(1, 4).floatValue();
    }
}

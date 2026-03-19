package com.petkit.android.activities.setting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.model.DeviceRelation;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class SettingDeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
    public ArrayList<DeviceCard> deviceCards = new ArrayList<>();
    public OnClickListener listener;
    public Context mContext;

    public interface OnClickListener {
        void onViewClick(DeviceCard deviceCard);
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public void setData(List<DeviceCard> list) {
        this.deviceCards.clear();
        this.deviceCards.addAll(list);
        notifyDataSetChanged();
    }

    public ArrayList<DeviceCard> getData() {
        return this.deviceCards;
    }

    public SettingDeviceAdapter(Context context, OnClickListener onClickListener) {
        this.listener = onClickListener;
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DeviceViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_device_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull DeviceViewHolder deviceViewHolder, @SuppressLint({"RecyclerView"}) final int i) {
        deviceViewHolder.tvDeviceName.setText(this.deviceCards.get(i).getDeviceData().getData().getName());
        DeviceCard deviceCard = this.deviceCards.get(i);
        int viewTypeFromData = deviceCard.getViewTypeFromData();
        if (viewTypeFromData != 11) {
            if (viewTypeFromData != 20) {
                if (viewTypeFromData == 21) {
                    T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(deviceCard.getDeviceData().getData().getId(), 1);
                    if (t6RecordByDeviceId != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                        if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                            String name = deviceCard.getDeviceData().getData().getName();
                            if (!TextUtils.isEmpty(name)) {
                                deviceViewHolder.tvDeviceName.setText(name);
                            } else if (t6RecordByDeviceId.getModelCode() == 2) {
                                deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T5_2_name_default));
                            } else {
                                deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T5_name_default));
                            }
                        } else {
                            String name2 = deviceCard.getDeviceData().getData().getName();
                            if (!TextUtils.isEmpty(name2)) {
                                deviceViewHolder.tvDeviceName.setText(name2);
                            } else if (t6RecordByDeviceId.getModelCode() == 2) {
                                deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T5_2_name_default));
                            } else {
                                deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T5_name_default));
                            }
                        }
                    }
                } else if (viewTypeFromData != 26) {
                    if (viewTypeFromData != 27) {
                        switch (viewTypeFromData) {
                            case 5:
                                if (deviceCard.getDeviceData().getData().getShared() != null) {
                                    String name3 = deviceCard.getDeviceData().getData().getName();
                                    String petNick = deviceCard.getDeviceData().getData().getShared().getPetNick();
                                    if (!TextUtils.isEmpty(name3)) {
                                        deviceViewHolder.tvDeviceName.setText(name3);
                                    } else if (!TextUtils.isEmpty(petNick)) {
                                        deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.Cozy_name_format, petNick));
                                    } else {
                                        deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.Device_z1_name));
                                    }
                                } else {
                                    String name4 = deviceCard.getDeviceData().getData().getName();
                                    String petId = deviceCard.getDeviceData().getData().getOwner().getPetId();
                                    if (!TextUtils.isEmpty(name4)) {
                                        deviceViewHolder.tvDeviceName.setText(name4);
                                    } else if (UserInforUtils.getPetById(petId) != null) {
                                        deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.Cozy_name_format, UserInforUtils.getPetById(petId).getName()));
                                    } else {
                                        deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.Device_z1_name));
                                    }
                                }
                                break;
                            case 6:
                                if (D2Utils.getD2RecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                    if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                        String name5 = deviceCard.getDeviceData().getData().getName();
                                        List<Pet> pets = deviceCard.getDeviceData().getData().getDeviceShared().getPets();
                                        if (!TextUtils.isEmpty(name5)) {
                                            deviceViewHolder.tvDeviceName.setText(name5);
                                        } else if (pets != null && pets.size() > 0 && !TextUtils.isEmpty(pets.get(0).getName())) {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D2_name_format, pets.get(0).getName()));
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.Device_mini_name));
                                        }
                                    } else {
                                        String name6 = deviceCard.getDeviceData().getData().getName();
                                        DeviceRelation relation = deviceCard.getDeviceData().getData().getRelation();
                                        List<String> petIds = relation != null ? relation.getPetIds() : null;
                                        if (!TextUtils.isEmpty(name6)) {
                                            deviceViewHolder.tvDeviceName.setText(name6);
                                        } else if (petIds != null && petIds.size() > 0 && UserInforUtils.getPetById(petIds.get(0)) != null) {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D2_name_format, UserInforUtils.getPetById(petIds.get(0)).getName()));
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.Device_mini_name));
                                        }
                                    }
                                }
                                break;
                            case 7:
                                if (BleDeviceUtils.getT3RecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                    if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                        String name7 = deviceCard.getDeviceData().getData().getName();
                                        if (!TextUtils.isEmpty(name7)) {
                                            deviceViewHolder.tvDeviceName.setText(name7);
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T3_name_default));
                                        }
                                    } else {
                                        String name8 = deviceCard.getDeviceData().getData().getName();
                                        if (!TextUtils.isEmpty(name8)) {
                                            deviceViewHolder.tvDeviceName.setText(name8);
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T3_name_default));
                                        }
                                    }
                                }
                                break;
                            case 8:
                                if (BleDeviceUtils.getK2RecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                    if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                        String name9 = deviceCard.getDeviceData().getData().getName();
                                        if (!TextUtils.isEmpty(name9)) {
                                            deviceViewHolder.tvDeviceName.setText(name9);
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.K2_name_default));
                                        }
                                    } else {
                                        String name10 = deviceCard.getDeviceData().getData().getName();
                                        if (!TextUtils.isEmpty(name10)) {
                                            deviceViewHolder.tvDeviceName.setText(name10);
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.K2_name_default));
                                        }
                                    }
                                }
                                break;
                            case 9:
                                if (D3Utils.getD3RecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                    if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                                        String name11 = deviceCard.getDeviceData().getData().getName();
                                        if (!TextUtils.isEmpty(name11)) {
                                            deviceViewHolder.tvDeviceName.setText(name11);
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D3_name_default));
                                        }
                                    } else {
                                        String name12 = deviceCard.getDeviceData().getData().getName();
                                        if (!TextUtils.isEmpty(name12)) {
                                            deviceViewHolder.tvDeviceName.setText(name12);
                                        } else {
                                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D3_name_default));
                                        }
                                    }
                                }
                                break;
                        }
                    } else if (T6Utils.getT6RecordByDeviceId(deviceCard.getDeviceData().getData().getId(), 0) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                        if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                            String name13 = deviceCard.getDeviceData().getData().getName();
                            if (!TextUtils.isEmpty(name13)) {
                                deviceViewHolder.tvDeviceName.setText(name13);
                            } else {
                                deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T6_name_default));
                            }
                        } else {
                            String name14 = deviceCard.getDeviceData().getData().getName();
                            if (!TextUtils.isEmpty(name14)) {
                                deviceViewHolder.tvDeviceName.setText(name14);
                            } else {
                                deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.T6_name_default));
                            }
                        }
                    }
                } else if (D4shUtils.getD4shRecordByDeviceId(deviceCard.getDeviceData().getData().getId(), 1) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                    if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                        String name15 = deviceCard.getDeviceData().getData().getName();
                        if (!TextUtils.isEmpty(name15)) {
                            deviceViewHolder.tvDeviceName.setText(name15);
                        } else {
                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D4H_name_default));
                        }
                    } else {
                        String name16 = deviceCard.getDeviceData().getData().getName();
                        if (!TextUtils.isEmpty(name16)) {
                            deviceViewHolder.tvDeviceName.setText(name16);
                        } else {
                            deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D4H_name_default));
                        }
                    }
                }
            } else if (D4sUtils.getD4sRecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                    String name17 = deviceCard.getDeviceData().getData().getName();
                    if (!TextUtils.isEmpty(name17)) {
                        deviceViewHolder.tvDeviceName.setText(name17);
                    } else {
                        deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D4S_name_default));
                    }
                } else {
                    String name18 = deviceCard.getDeviceData().getData().getName();
                    if (!TextUtils.isEmpty(name18)) {
                        deviceViewHolder.tvDeviceName.setText(name18);
                    } else {
                        deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D4S_name_default));
                    }
                }
            }
        } else if (D4Utils.getD4RecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
            if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                String name19 = deviceCard.getDeviceData().getData().getName();
                if (!TextUtils.isEmpty(name19)) {
                    deviceViewHolder.tvDeviceName.setText(name19);
                } else {
                    deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D4_name_default));
                }
            } else {
                String name20 = deviceCard.getDeviceData().getData().getName();
                if (!TextUtils.isEmpty(name20)) {
                    deviceViewHolder.tvDeviceName.setText(name20);
                } else {
                    deviceViewHolder.tvDeviceName.setText(this.mContext.getString(R.string.D4_name_default));
                }
            }
        }
        deviceViewHolder.rlRootPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.setting.adapter.SettingDeviceAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SettingDeviceAdapter.this.listener != null) {
                    SettingDeviceAdapter.this.listener.onViewClick((DeviceCard) SettingDeviceAdapter.this.deviceCards.get(i));
                }
            }
        });
        if (i == this.deviceCards.size() - 1) {
            deviceViewHolder.viewLine.setVisibility(8);
        } else {
            deviceViewHolder.viewLine.setVisibility(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.deviceCards.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlRootPanel;
        public TextView tvDeviceName;
        public View viewLine;

        public DeviceViewHolder(@NonNull View view) {
            super(view);
            this.tvDeviceName = (TextView) view.findViewById(R.id.tv_device_name);
            this.rlRootPanel = (RelativeLayout) view.findViewById(R.id.rl_root_panel);
            this.viewLine = view.findViewById(R.id.view_line);
        }
    }
}

package com.petkit.android.activities.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.home.mode.HomeCardData;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.remind.HealthRemindActivity2_0;
import com.petkit.android.activities.remind.RemindTypeActivity;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes4.dex */
public class TodoListAdapter extends RecyclerView.Adapter<BaseTodoViewHolder> {
    public HomeCardData.ToDoCardsBean bean;
    public List<HomeCardData.ToDoCardsBean.ToDoListBean> dataList;
    public boolean isRemind;
    public OnClickListener listener;
    public Context mContext;
    public TodoHeaderViewHolder todoHeaderViewHolder;
    public int type;
    public final int TYPE_EMPTY = 0;
    public final int TYPE_HEADER = 1;
    public final int TYPE_ITEM = 2;
    public String tag = "";

    public interface OnClickListener {
        void onCbClick(TodoItemViewHolder todoItemViewHolder, HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, long j, int i, String str, int i2);

        void onCbClick(TodoItemViewHolder todoItemViewHolder, HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, String str, int i);

        void onClick(HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, long j, int i, String str);

        void onClickFinishRemind(int i, String str);

        void onClickRemind(int i, String str);

        void onClickShare(HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, String str);
    }

    public TodoListAdapter(Context context, HomeCardData.ToDoCardsBean toDoCardsBean) {
        this.mContext = context;
        this.bean = toDoCardsBean;
        this.dataList = toDoCardsBean.getToDoList();
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public void removeItem(int i) {
        if (i >= getItemCount() || i == -1) {
            return;
        }
        this.dataList.remove(i - 1);
        notifyItemRemoved(i);
    }

    public void addNewItemAfterRemoveItem(int i, HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean) {
        if (i >= getItemCount() || i == -1) {
            return;
        }
        this.dataList.remove(i - 1);
        notifyItemRangeRemoved(i, 1);
        addItem(toDoListBean);
    }

    public void addItem(HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean) {
        this.dataList.add(toDoListBean);
        notifyItemRangeInserted(this.dataList.size(), 1);
    }

    public void setRemindTitle(int i) {
        TodoHeaderViewHolder todoHeaderViewHolder = this.todoHeaderViewHolder;
        if (todoHeaderViewHolder != null) {
            int i2 = this.type;
            String str = "";
            if (i2 == 1) {
                TextView textView = todoHeaderViewHolder.tvTitle;
                StringBuilder sb = new StringBuilder();
                sb.append(this.mContext.getString(R.string.Smart_reminder));
                if (i > 0) {
                    str = " (" + i + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                sb.append(str);
                textView.setText(sb.toString());
                return;
            }
            if (i2 == 2) {
                TextView textView2 = todoHeaderViewHolder.tvTitle;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mContext.getString(R.string.Family_share));
                if (i > 0) {
                    str = " (" + i + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                sb2.append(str);
                textView2.setText(sb2.toString());
                return;
            }
            if (i2 == 3) {
                TextView textView3 = todoHeaderViewHolder.tvTitle;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.mContext.getString(R.string.Device_share));
                if (i > 0) {
                    str = " (" + i + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                sb3.append(str);
                textView3.setText(sb3.toString());
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseTodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i != 1) {
            if (i == 2) {
                return new TodoItemViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_to_do_item_layout, viewGroup, false));
            }
            return new TodoEmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_to_do_empty_layout, viewGroup, false));
        }
        TodoHeaderViewHolder todoHeaderViewHolder = new TodoHeaderViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_to_do_header_layout, viewGroup, false));
        this.todoHeaderViewHolder = todoHeaderViewHolder;
        return todoHeaderViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"RecyclerView"})
    public void onBindViewHolder(@NonNull final BaseTodoViewHolder baseTodoViewHolder, int i) {
        PetkitLog.d("TodoListAdapter", "position:" + i + ",adapterPosition:" + baseTodoViewHolder.getAdapterPosition() + ",layoutPosition:" + baseTodoViewHolder.getLayoutPosition());
        if (baseTodoViewHolder instanceof TodoEmptyViewHolder) {
            ((TodoEmptyViewHolder) baseTodoViewHolder).rlHeader.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$0(view);
                }
            });
            return;
        }
        if (baseTodoViewHolder instanceof TodoHeaderViewHolder) {
            int i2 = this.type;
            String str = "";
            if (i2 == 1) {
                int size = this.bean.getToDoList().size();
                TodoHeaderViewHolder todoHeaderViewHolder = (TodoHeaderViewHolder) baseTodoViewHolder;
                TextView textView = todoHeaderViewHolder.tvTitle;
                StringBuilder sb = new StringBuilder();
                sb.append(this.mContext.getString(R.string.Smart_reminder));
                if (size > 0) {
                    str = " (" + size + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                sb.append(str);
                textView.setText(sb.toString());
                todoHeaderViewHolder.rlHeader.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$1(view);
                    }
                });
                todoHeaderViewHolder.ivIcon.setImageResource(R.drawable.to_do_agent_icon);
                todoHeaderViewHolder.ivHeaderArrow.setVisibility(0);
                todoHeaderViewHolder.rlHeader.setBackgroundResource(R.drawable.shape_to_do_header_bg);
                return;
            }
            if (i2 == 0) {
                TodoHeaderViewHolder todoHeaderViewHolder2 = (TodoHeaderViewHolder) baseTodoViewHolder;
                todoHeaderViewHolder2.ivIcon.setImageResource(getIconResource(CommonUtils.getDeviceTypeByString(this.bean.getType()), this.bean.getId()));
                todoHeaderViewHolder2.rlHeader.setBackgroundResource(getTitleBgResource(CommonUtils.getDeviceTypeByString(this.bean.getType())));
                todoHeaderViewHolder2.ivHeaderArrow.setVisibility(8);
                if (CommonUtils.getDeviceTypeByString(this.bean.getType()) != 1) {
                    todoHeaderViewHolder2.tvTitle.setText(this.bean.getName());
                    return;
                }
                DeviceCard device = DeviceCenterUtils.getDevice(Long.valueOf(this.bean.getId()), 1);
                if (device != null) {
                    Pet petById = UserInforUtils.getPetById(device.getDeviceData().getData().getPetId());
                    if (petById != null) {
                        todoHeaderViewHolder2.tvTitle.setText(this.mContext.getResources().getString(R.string.Whose_fit_format, petById.getName()));
                        return;
                    } else {
                        todoHeaderViewHolder2.tvTitle.setText(this.mContext.getResources().getString(R.string.Card_name_fit));
                        return;
                    }
                }
                todoHeaderViewHolder2.tvTitle.setText(this.mContext.getResources().getString(R.string.Card_name_fit));
                return;
            }
            if (i2 == 2) {
                int size2 = this.bean.getToDoList().size();
                this.dataList.get(baseTodoViewHolder.getAdapterPosition());
                TodoHeaderViewHolder todoHeaderViewHolder3 = (TodoHeaderViewHolder) baseTodoViewHolder;
                TextView textView2 = todoHeaderViewHolder3.tvTitle;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mContext.getString(R.string.Family_share));
                if (size2 > 0) {
                    str = " (" + size2 + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                sb2.append(str);
                textView2.setText(sb2.toString());
                todoHeaderViewHolder3.ivIcon.setImageResource(R.drawable.to_do_share_icon);
                todoHeaderViewHolder3.ivHeaderArrow.setVisibility(8);
                todoHeaderViewHolder3.rlHeader.setBackgroundResource(R.drawable.shape_to_do_header_share_bg);
                return;
            }
            if (i2 == 3) {
                int size3 = this.bean.getToDoList().size();
                this.dataList.get(baseTodoViewHolder.getAdapterPosition());
                TodoHeaderViewHolder todoHeaderViewHolder4 = (TodoHeaderViewHolder) baseTodoViewHolder;
                TextView textView3 = todoHeaderViewHolder4.tvTitle;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.mContext.getString(R.string.Device_share));
                if (size3 > 0) {
                    str = " (" + size3 + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                sb3.append(str);
                textView3.setText(sb3.toString());
                todoHeaderViewHolder4.ivIcon.setImageResource(R.drawable.to_do_share_icon);
                todoHeaderViewHolder4.ivHeaderArrow.setVisibility(8);
                todoHeaderViewHolder4.rlHeader.setBackgroundResource(R.drawable.shape_to_do_header_share_bg);
                return;
            }
            return;
        }
        if (baseTodoViewHolder instanceof TodoItemViewHolder) {
            final HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean = this.dataList.get(baseTodoViewHolder.getAdapterPosition() - 1);
            TodoItemViewHolder todoItemViewHolder = (TodoItemViewHolder) baseTodoViewHolder;
            todoItemViewHolder.tvRemainTime.setText(DateUtil.getTodoRemindDateTime(this.mContext, toDoListBean.getTime()));
            int i3 = this.type;
            if (i3 == 1) {
                todoItemViewHolder.cbTodo.setChecked(false);
                todoItemViewHolder.cbTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter.1
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (TodoListAdapter.this.listener != null) {
                            TodoListAdapter.this.listener.onClickFinishRemind(baseTodoViewHolder.getAdapterPosition(), TodoListAdapter.this.tag);
                        }
                    }
                });
                todoItemViewHolder.cbTodo.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TodoListAdapter.lambda$onBindViewHolder$2(baseTodoViewHolder, view);
                    }
                });
                todoItemViewHolder.rlHeader.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$3(baseTodoViewHolder, view);
                    }
                });
                todoItemViewHolder.tvContent.setText(toDoListBean.getDesc() + " " + DateUtil.getDateFormatShortTimeShortString(toDoListBean.getDate()));
            } else if (i3 == 0) {
                todoItemViewHolder.cbTodo.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$4(baseTodoViewHolder, toDoListBean, view);
                    }
                });
                todoItemViewHolder.cbTodo.setChecked(false);
                todoItemViewHolder.tvContent.setText(toDoListBean.getDesc());
                todoItemViewHolder.rlHeader.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$5(toDoListBean, view);
                    }
                });
            } else if (i3 == 2) {
                todoItemViewHolder.cbTodo.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$6(baseTodoViewHolder, toDoListBean, view);
                    }
                });
                todoItemViewHolder.cbTodo.setChecked(false);
                todoItemViewHolder.tvContent.setText(this.mContext.getResources().getString(R.string.Todo_invite_prompt, toDoListBean.getUserName(), toDoListBean.getDesc()));
                todoItemViewHolder.rlHeader.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$7(toDoListBean, view);
                    }
                });
            } else if (i3 == 3) {
                todoItemViewHolder.cbTodo.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$8(baseTodoViewHolder, toDoListBean, view);
                    }
                });
                todoItemViewHolder.cbTodo.setChecked(false);
                todoItemViewHolder.tvContent.setText(toDoListBean.getUserName() + this.mContext.getResources().getString(R.string.Invite_you_to_use, toDoListBean.getDesc()));
                todoItemViewHolder.rlHeader.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.TodoListAdapter$$ExternalSyntheticLambda9
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$9(toDoListBean, view);
                    }
                });
            }
            if (TextUtils.isEmpty(toDoListBean.getDate()) || this.type == 0) {
                todoItemViewHolder.tvTime.setVisibility(8);
            } else {
                todoItemViewHolder.tvTime.setVisibility(8);
            }
            if (baseTodoViewHolder.getAdapterPosition() == this.dataList.size()) {
                todoItemViewHolder.viewLine.setVisibility(8);
            } else {
                todoItemViewHolder.viewLine.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) RemindTypeActivity.class);
        intent.putExtra(Constants.EXTRA_PET_ID, PetUtils.ALL_DEVICE);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) HealthRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_PET_ID, PetUtils.ALL_DEVICE);
        this.mContext.startActivity(intent);
    }

    public static /* synthetic */ void lambda$onBindViewHolder$2(BaseTodoViewHolder baseTodoViewHolder, View view) {
        ((TodoItemViewHolder) baseTodoViewHolder).cbTodo.setChecked(true);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$3(BaseTodoViewHolder baseTodoViewHolder, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClickRemind(baseTodoViewHolder.getAdapterPosition(), this.tag);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$4(BaseTodoViewHolder baseTodoViewHolder, HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, View view) {
        TodoItemViewHolder todoItemViewHolder = (TodoItemViewHolder) baseTodoViewHolder;
        todoItemViewHolder.cbTodo.setChecked(false);
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onCbClick(todoItemViewHolder, toDoListBean, this.bean.getId(), CommonUtils.getDeviceTypeByString(this.bean.getType()), this.tag, baseTodoViewHolder.getAdapterPosition());
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$5(HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClick(toDoListBean, this.bean.getId(), CommonUtils.getDeviceTypeByString(this.bean.getType()), this.tag);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$6(BaseTodoViewHolder baseTodoViewHolder, HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, View view) {
        TodoItemViewHolder todoItemViewHolder = (TodoItemViewHolder) baseTodoViewHolder;
        todoItemViewHolder.cbTodo.setChecked(false);
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onCbClick(todoItemViewHolder, toDoListBean, this.tag, baseTodoViewHolder.getAdapterPosition());
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$7(HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClickShare(toDoListBean, this.tag);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$8(BaseTodoViewHolder baseTodoViewHolder, HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, View view) {
        TodoItemViewHolder todoItemViewHolder = (TodoItemViewHolder) baseTodoViewHolder;
        todoItemViewHolder.cbTodo.setChecked(false);
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onCbClick(todoItemViewHolder, toDoListBean, this.tag, baseTodoViewHolder.getAdapterPosition());
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$9(HomeCardData.ToDoCardsBean.ToDoListBean toDoListBean, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onClickShare(toDoListBean, this.tag);
        }
    }

    public final int getTitleBgResource(int i) {
        switch (i) {
            case 1:
                return R.drawable.shape_to_do_header_fit_bg;
            case 2:
                return R.drawable.shape_to_do_header_mate_bg;
            case 3:
                return R.drawable.shape_to_do_header_go_bg;
            case 4:
                return R.drawable.shape_to_do_header_d1_bg;
            case 5:
                return R.drawable.shape_to_do_header_z1_bg;
            case 6:
                return R.drawable.shape_to_do_header_d2_bg;
            case 7:
                return R.drawable.shape_to_do_header_t3_bg;
            case 8:
                return R.drawable.shape_to_do_header_k2_bg;
            case 9:
                return R.drawable.shape_to_do_header_d3_bg;
            case 10:
                return R.drawable.shape_to_do_header_aq_bg;
            case 11:
            case 20:
                return R.drawable.shape_to_do_header_d4_bg;
            case 12:
                return R.drawable.shape_to_do_header_p3_bg;
            case 13:
                return R.drawable.shape_to_do_header_h2_bg;
            case 14:
                return R.drawable.shape_to_do_header_w5c_bg;
            case 15:
                return R.drawable.shape_to_do_header_t4_bg;
            case 16:
                return R.drawable.shape_to_do_header_k3_bg;
            case 17:
                return R.drawable.shape_to_do_header_aqr_bg;
            case 18:
                return R.drawable.shape_to_do_header_r2_bg;
            case 19:
                return R.drawable.shape_to_do_header_aqh1_bg;
            case 21:
            case 23:
            default:
                return R.drawable.shape_to_do_header_bg;
            case 22:
                return R.drawable.shape_to_do_header_hg_bg;
            case 24:
                return R.drawable.shape_to_do_header_w5c_bg;
            case 25:
            case 26:
                return R.drawable.shape_to_do_header_d4sh_bg;
        }
    }

    public final int getIconResource(int i, long j) {
        switch (i) {
            case 1:
                DeviceCard device = DeviceCenterUtils.getDevice(Long.valueOf(j), 1);
                if (device != null) {
                    if (device.getDeviceData().getData().getHardware() == 1) {
                        return R.drawable.record_device_p1;
                    }
                    return R.drawable.record_device_p2;
                }
                return R.drawable.dev_home_fit;
            case 2:
                return R.drawable.record_device_mate;
            case 3:
                return R.drawable.record_device_go;
            case 4:
                return R.drawable.record_device_d1;
            case 5:
                return R.drawable.record_device_z1;
            case 6:
                return R.drawable.record_device_d2;
            case 7:
                return R.drawable.record_device_t3;
            case 8:
                return R.drawable.record_device_k2;
            case 9:
                return R.drawable.record_device_d3;
            case 10:
                AqRecord aqRecordByDeviceId = AqUtils.getAqRecordByDeviceId(j);
                if (aqRecordByDeviceId != null) {
                    if (aqRecordByDeviceId.getTypeCode() == 1) {
                        return R.drawable.record_device_aq;
                    }
                    return R.drawable.record_device_aq1s;
                }
                return R.drawable.record_device_aq;
            case 11:
                return R.drawable.record_device_d4;
            case 12:
                return R.drawable.record_device_p3;
            case 13:
                return R.drawable.record_device_h2;
            case 14:
                return R.drawable.record_device_w5;
            case 15:
                return R.drawable.record_device_t4;
            case 16:
                return R.drawable.record_device_k3;
            case 17:
                return R.drawable.record_device_aqr;
            case 18:
                return R.drawable.record_device_r2;
            case 19:
                return R.drawable.record_device_aqh1;
            case 20:
                return R.drawable.record_device_d4s;
            case 21:
            case 23:
            default:
                return R.drawable.dev_home_p1;
            case 22:
                return R.drawable.record_device_hg;
            case 24:
                return R.drawable.record_device_ctw3;
            case 25:
                return R.drawable.record_device_d4sh;
            case 26:
                return R.drawable.record_device_d4h;
            case 27:
                return R.drawable.record_device_t6;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.dataList.size() == 0) {
            return 2;
        }
        return this.dataList.size() + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.dataList.size() == 0 ? i == 0 ? 1 : 0 : i == 0 ? 1 : 2;
    }

    public static class TodoEmptyViewHolder extends BaseTodoViewHolder {
        public RelativeLayout rlHeader;
        public TextView tvCreateRemind;

        public TodoEmptyViewHolder(View view) {
            super(view);
            this.tvCreateRemind = (TextView) view.findViewById(R.id.tv_create_remind);
            this.rlHeader = (RelativeLayout) view.findViewById(R.id.rl_header);
        }
    }

    public static class TodoItemViewHolder extends BaseTodoViewHolder {
        public CheckBox cbTodo;
        public RelativeLayout rlHeader;
        public TextView tvContent;
        public TextView tvRemainTime;
        public TextView tvTime;
        public View viewLine;

        public TodoItemViewHolder(View view) {
            super(view);
            this.cbTodo = (CheckBox) view.findViewById(R.id.cb_todo);
            this.tvContent = (TextView) view.findViewById(R.id.tv_content);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.tvRemainTime = (TextView) view.findViewById(R.id.tv_remain_time);
            this.viewLine = view.findViewById(R.id.view_line);
            this.rlHeader = (RelativeLayout) view.findViewById(R.id.rl_header);
        }
    }

    public static class TodoHeaderViewHolder extends BaseTodoViewHolder {
        public ImageView ivHeaderArrow;
        public ImageView ivIcon;
        public RelativeLayout rlHeader;
        public TextView tvTitle;

        public TodoHeaderViewHolder(View view) {
            super(view);
            this.ivHeaderArrow = (ImageView) view.findViewById(R.id.iv_header_arrow);
            this.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.rlHeader = (RelativeLayout) view.findViewById(R.id.rl_header);
        }
    }

    public static class BaseTodoViewHolder extends RecyclerView.ViewHolder {
        public BaseTodoViewHolder(View view) {
            super(view);
        }
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public List<HomeCardData.ToDoCardsBean.ToDoListBean> getDataList() {
        return this.dataList;
    }

    public boolean isRemind() {
        return this.isRemind;
    }

    public void setRemind(boolean z) {
        this.isRemind = z;
    }

    public void setType(int i) {
        this.type = i;
    }
}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderView> {
    private boolean isSelectedAll = false;
    private Context mContext;
    private List<Order> orderList;

    public interface OnItemCheckListener {
        void onItemCheck(Order order, int position);

        void onItemUncheck(Order order, int position);
    }

    /* private OnClickListener mListener;*/
    private String vehicleId;
    private int selectedOrder = -1;
    List<String> list;
    List<Order> listdOrder = new ArrayList<>();
    private String TAG = OrderAdapter.class.getCanonicalName();

    @NonNull
    private OnItemCheckListener onItemCheckListener;


   /* public interface OnClickListener {
        void onItemClickListener(int position,  List<Order> listdOrder);
    }
*/
   /* public void setClickListener(OnClickListener mListener) {
        this.mListener = mListener;
    }*/

    public OrderAdapter(Context applicationContext, List<Order> orderList, String vehicleId, OnItemCheckListener onItemCheckListeners) {
        this.orderList = orderList;
        this.mContext = applicationContext;
        this.vehicleId = vehicleId;
        onItemCheckListener = onItemCheckListeners;
    }

    public void selectAll() {
        Log.e("onClickSelectAll", "yes");
        isSelectedAll = true;

        notifyDataSetChanged();
    }

    public void unselectedAll() {
        isSelectedAll = false;
        notifyDataSetChanged();
    }
    public void removeItem(int position) {
        orderList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Order item, int position) {
        orderList.add(position, item);
        notifyItemInserted(position);
    }

    public List<Order> getData() {
        return orderList;
    }
    @NonNull
    @Override
    public OrderView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list, viewGroup, false);
        return new OrderView(view);
    }

    public void update(List<Integer> position) {
        for (int poss : position) {
            try {
                orderList.remove(poss);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderView orderView, int position) {
        final Order sortedOrder = orderList.get(position);

//        orderView.tvOrderId.setText(orderList.get(position).getOrder_id()); //Commented by Laukendra on 02-12-19
        orderView.tvOrderId.setText(orderList.get(position).getLead_number()); //Added by Laukendra on 02-12-19
        orderView.tv_Location.setText(orderList.get(position).getLocation_name());
        orderView.tvCustomerName.setText(orderList.get(position).getFname());
//        orderView.tv_Address.setText(orderList.get(position).getAddress());
        orderView.tv_Con_Person.setText(orderList.get(position).getOrder_contact_person_name());
        orderView.tv_PhoneNumber.setText(orderList.get(position).getContact_person_phone());
        orderView.tv_quantity.setText(orderList.get(position).getQuantity());
        Log.e("ASSET_WISE_TEST","SONALk"+orderList.get(position).getAsset_wise());
        Log.e("ASSET_WISE_TEST","SONALkq"+orderList.get(position).getQuantity());
        if (!isSelectedAll) {
            orderView.cb_Accepted.setChecked(false);
        } else {
            orderView.cb_Accepted.setChecked(true);
        }


        orderView.tv_date.setText(orderList.get(position).getRegistration_no());

Log.e("RegNo",""+orderList.get(position).getRegistration_no());

        if (orderList.get(position).getSlotId().equalsIgnoreCase("1")) {
            orderView.tv_Address.setText(getDeliverySlot(0));
        } else if (orderList.get(position).getSlotId().equalsIgnoreCase("2")) {
            orderView.tv_Address.setText(getDeliverySlot(1));
        } else if (orderList.get(position).getSlotId().equalsIgnoreCase("3")) {
            orderView.tv_Address.setText(getDeliverySlot(2));
        } else if (orderList.get(position).getSlotId().equalsIgnoreCase("4")) {
            orderView.tv_Address.setText(getDeliverySlot(3));
        } else if (orderList.get(position).getSlotId().equalsIgnoreCase("5")) {
            orderView.tv_Address.setText(getDeliverySlot(4));
        } else if (orderList.get(position).getSlotId().equalsIgnoreCase("6")) {
            orderView.tv_Address.setText(getDeliverySlot(4));
        }
//        orderView.tv_date.setText(orderList.get(position).getCreated_datatime());
        orderView.tvOrderId.setText(orderList.get(position).getOrder_id());

        if (orderList.get(position).getOrderType().equalsIgnoreCase("1")) {
            orderView.tv_OrderType.setText("Normal");
            orderView.ll_orderlist.setBackgroundColor(ContextCompat.getColor(mContext, R.color.md_green_400));
            orderView.ll_orderlist2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.md_green_50));
        } else if (orderList.get(position).getOrderType().equalsIgnoreCase("2")) {
            orderView.tv_OrderType.setText("Express");
            orderView.ll_orderlist.setBackgroundColor(ContextCompat.getColor(mContext, R.color.md_blue_400));
            orderView.ll_orderlist2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.md_blue_50));
        } else {
            orderView.ll_orderlist.setBackgroundColor(ContextCompat.getColor(mContext, R.color.md_red_400));
            orderView.ll_orderlist2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.md_red_100));
            orderView.tv_OrderType.setText("Priority");
        }
        if (orderList.get(position).getStaus().contentEquals("1")) {
            orderView.tv_Status.setText("Pending");
        } else if (orderList.get(position).getStaus().contentEquals("11")) {
            orderView.tv_Status.setText("Delivery in Progress");
        } else {
//            orderView.tv_Status.setText(orderList.get(position).getStaus());
        }
        orderView.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (orderView).cb_Accepted.setChecked(!(orderView).cb_Accepted.isChecked());
                if (orderView.cb_Accepted.isChecked()) {
                    onItemCheckListener.onItemCheck(sortedOrder, position);
                } else {
                    onItemCheckListener.onItemUncheck(sortedOrder, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList == null ? 0 : orderList.size();
    }


    public class OrderView extends RecyclerView.ViewHolder {
        private final View card;
        private View ll_orderlist, ll_orderlist2;
        private TextView tv_OrderType;
        TextView tv_Location, tv_Address, tv_Con_Person, tv_PhoneNumber, tv_date, tv_Status, tv_Type, tv_quantity, tvOrderId, tvCustomerName;
        CheckBox cb_Accepted;

        public OrderView(View itemView) {
            super(itemView);
            card = itemView;
            tv_Location = itemView.findViewById(R.id.order_location);
            tvCustomerName = itemView.findViewById(R.id.customer_name);
            tv_Address = itemView.findViewById(R.id.order_address);
            tv_Con_Person = itemView.findViewById(R.id.order_ContactPerson);
            tv_PhoneNumber = itemView.findViewById(R.id.order_phone_number);
            tv_quantity = itemView.findViewById(R.id.order_quantity);
            tv_date = itemView.findViewById(R.id.order_date_time);
            tv_Status = itemView.findViewById(R.id.order_status);
            tvOrderId = itemView.findViewById(R.id.tv_OrdeId);
            cb_Accepted = itemView.findViewById(R.id.cb_Accepted);
            cb_Accepted.setClickable(false);
            tv_OrderType = itemView.findViewById(R.id.tv_OrderType);
            ll_orderlist = itemView.findViewById(R.id.ll_orderlist);
            ll_orderlist2 = itemView.findViewById(R.id.ll_orderlist2);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

    }

    public String getDeliverySlot(int postion) {
        String Value = "";

        try {
            Value = SharedPref.getLoginResponse().getData().get(0).getTimeslot().get(postion).getDisplayTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Value;
    }


}

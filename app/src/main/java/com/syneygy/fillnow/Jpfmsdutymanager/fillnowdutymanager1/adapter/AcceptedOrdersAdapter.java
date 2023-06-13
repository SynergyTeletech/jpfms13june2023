package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentAcceptedOrders;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import java.util.List;
import java.util.Locale;


public class AcceptedOrdersAdapter extends RecyclerView.Adapter<AcceptedOrdersAdapter.AcceptedOrdersHolder> {

    private final FragmentAcceptedOrders.ActionTake actionTake;
    private List<Order> confirmOrderList;
    private Context context;
    private LayoutInflater inflater;
    private int positon;
    private boolean flagOrderAssetWise=false;

    public AcceptedOrdersAdapter(Context context, List<Order> confirmOrderList, FragmentAcceptedOrders.ActionTake actionTake) {
        this.confirmOrderList = confirmOrderList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.actionTake = actionTake;
    }

    @NonNull
    @Override
    public AcceptedOrdersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new AcceptedOrdersHolder(inflater.inflate(R.layout.process_accepted_order, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedOrdersHolder holder, int position) {
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Contact Person Details");
                builder.setMessage("Name : " + confirmOrderList.get(position).getOrder_contact_person_name() + "\n \nPhone No. :  " + confirmOrderList.get(position).getContact_person_phone());

                builder.setPositiveButton("Show Invoice", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        print();
                        if (actionTake != null) {
                            actionTake.onActionTaken(FragmentAcceptedOrders.ActionPerformed.INVOICE, confirmOrderList.get(position), Integer.parseInt(confirmOrderList.get(position).getTransaction_id()));
                        }

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

//        holder.id.setText(confirmOrderList.get(position).getOrder_id()); //Commented By Laukendra on 07-11-19
        holder.id.setText(confirmOrderList.get(position).getLead_number());  //Added By Laukendra on 07-11-19
//        holder.id.setText("1002190929");
        holder.location.setText(confirmOrderList.get(position).getLocation_name());
        holder.tvCustName.setText(confirmOrderList.get(position).getFname());
        holder.address.setText(confirmOrderList.get(position).getSlotname());
        holder.contact_person.setText(confirmOrderList.get(position).getQuantity());
        holder.time.setText(confirmOrderList.get(position).getRegistration_no());
        holder.tvEquipmentId.setText(confirmOrderList.get(position).getAsset_wise());
        Log.e("TEST","Accepted_order"+confirmOrderList.get(position).getAsset_wise()+"--"+confirmOrderList.get(position).getAsset_name()+"...."+confirmOrderList.get(position).getStaus());

       /* if (confirmOrderList.get(position).getAsset_name()!=null){
            holder.status.setText(confirmOrderList.get(position).getAsset_name());
        }*/
        try {
            //holder.phone.setText(String.valueOf(Integer.valueOf(confirmOrderList.get(position).getQuantity()) - Integer.parseInt(confirmOrderList.get(position).getDelivered_data()))); //Commented by Laukendra on 14-1-19
         //...balance quantity
           holder.phone.setText(String.format("%.2f",Float.parseFloat(confirmOrderList.get(position).getQuantity()) - Float.parseFloat(confirmOrderList.get(position).getDelivered_data())));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        holder.quantity.setText(confirmOrderList.get(position).getOrderdate());
        holder.locationLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uris = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",
                        Double.parseDouble(confirmOrderList.get(position).getLatitude()),
                        Double.parseDouble(confirmOrderList.get(position).getLongitude())
                        , confirmOrderList.get(position).getLocation_name());
//                        , confirmOrderList.get(position).getLocation_name()) + "+to:12.9747,77.6094" + "+to:28.532062,77.310905";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uris));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uris));
                        context.startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        Toast.makeText(context, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (confirmOrderList.get(position).getOrderType().equalsIgnoreCase("1")) {
            holder.tv_OrderType.setText("Normal");
            holder.ll_orderlist.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_400));
            holder.ll_orderlist2.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_50));
        } else if (confirmOrderList.get(position).getOrderType().equalsIgnoreCase("2")) {
            holder.tv_OrderType.setText("Express");
            holder.ll_orderlist.setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_400));
            holder.ll_orderlist2.setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_50));
        }
        // Added the following Line by Laukendra
        else if (confirmOrderList.get(position).getOrderType().equalsIgnoreCase("3")){
            holder.tv_OrderType.setText("Priority");
            holder.ll_orderlist.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_400));
            holder.ll_orderlist2.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_100));
        }
        // Added the above Line by Laukendra

        //Status 1: Fresh/New Order
        //Status 2: Accepted Order
        //Status 3: Out For Delivery Order
        //Status 4: Arrived Order
        //Status 5: ....... Order
        //Status 6: ....... Order
        //.
        //.
        //.
        //Status 11: Delivery In Progress Order
        //Status 12: Delivery Complete Order


if (confirmOrderList.get(position).getAsset_wise()!=null){
    holder.status.setText(confirmOrderList.get(position).getAsset_wise());
}
//todo by sonal
                 if((confirmOrderList.get(position).getStaus().equalsIgnoreCase("2"))&&
                         (!confirmOrderList.get(position).getAsset_name().contains(("Not Selected"))))
        {
            Log.v("TEST","CALL");
            flagOrderAssetWise=true;
            holder.actionButton.setText("Dispense");
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positon= position;
                    actionTake.onActionTaken(FragmentAcceptedOrders.ActionPerformed.DISPENSE, confirmOrderList.get(position), Integer.parseInt(confirmOrderList.get(position).getTransaction_id()));
                }
            });
            holder.order_lay.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_100));
            holder.actionButton.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        if ( confirmOrderList.get(position).getStaus().equalsIgnoreCase("2") && flagOrderAssetWise==false) {
           // holder.status.setText("Accepted");

            holder.actionButton.setText("Notify");
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positon= position;
                    actionTake.onActionTaken(FragmentAcceptedOrders.ActionPerformed.NOTIFY, confirmOrderList.get(position), Integer.parseInt(confirmOrderList.get(position).getTransaction_id()));
                }
            });
        } else if (confirmOrderList.get(position).getStaus().equalsIgnoreCase("3")&& flagOrderAssetWise==false) {
           // holder.status.setText("Out For Delivery");
            holder.route_planInfo.setVisibility(View.VISIBLE);

            holder.actionButton.setText("Notify");
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positon= position;
                    actionTake.onActionTaken(FragmentAcceptedOrders.ActionPerformed.NOTIFY, confirmOrderList.get(position), Integer.parseInt(confirmOrderList.get(position).getTransaction_id()));
                }
            });

        }
        else if (confirmOrderList.get(position).getStaus().equalsIgnoreCase("4") && flagOrderAssetWise==false ) {
           // holder.status.setText("Order Arrived");
            holder.actionButton.setText("Dispense");
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positon= position;
                    actionTake.onActionTaken(FragmentAcceptedOrders.ActionPerformed.DISPENSE, confirmOrderList.get(position), Integer.parseInt(confirmOrderList.get(position).getTransaction_id()));
                }
            });
            holder.order_lay.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_100));
            holder.actionButton.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } else if (confirmOrderList.get(position).getStaus().equalsIgnoreCase("11")) {
        ;

            holder.actionButton.setText("Go");
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positon= position;
                    actionTake.onActionTaken(FragmentAcceptedOrders.ActionPerformed.DISPENSE, confirmOrderList.get(position), Integer.parseInt(confirmOrderList.get(position).getTransaction_id()));
                    Log.e("printId",confirmOrderList.get(position).getTransaction_id());
                }
            });
            holder.order_lay.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_100));
            holder.actionButton.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } else if (confirmOrderList.get(position).getStaus().equalsIgnoreCase("12")) {
           // holder.status.setText("Delivery Complete");
            Log.v("COMPLETE","acceptedOrderList"+confirmOrderList.get(position).getStaus() +"------"+SharedPref.getStatusComplete());
            holder.actionButton.setText("Complete");
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positon= position;
                    actionTake.onActionTaken(FragmentAcceptedOrders.ActionPerformed.COMPLETE, confirmOrderList.get(position), Integer.parseInt(confirmOrderList.get(position).getTransaction_id()));

                }
            });
            holder.actionButton.setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_200));
        } else {

        }

    }

    @Override
    public int getItemCount() {
        return confirmOrderList == null ? 0 : confirmOrderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class AcceptedOrdersHolder extends RecyclerView.ViewHolder {
        private View locationLay;
        private View route_planInfo;
        private View order_lay;
        private View ll_orderlist, ll_orderlist2;
        private View cardView;
        private TextView actionButton;
        public TextView id, location,tvEquipmentId, address, contact_person, phone, quantity, deliveryDay, time, status, tvCustName,tv_OrderType ;


        public AcceptedOrdersHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            id = itemView.findViewById(R.id.cust_id);
            location = itemView.findViewById(R.id.location);
            tvCustName = itemView.findViewById(R.id.cust_name);
            address = itemView.findViewById(R.id.address);
            contact_person = itemView.findViewById(R.id.contact_person);
            quantity = itemView.findViewById(R.id.qunantity);
            deliveryDay = itemView.findViewById(R.id.delivery_day);
            time = itemView.findViewById(R.id.time);
            phone = itemView.findViewById(R.id.phone_num);
            status = itemView.findViewById(R.id.status);
            actionButton = itemView.findViewById(R.id.actionButton);
            order_lay = itemView.findViewById(R.id.order_lay);
            locationLay = itemView.findViewById(R.id.locationLay);
            route_planInfo = itemView.findViewById(R.id.route_planInfo);
            tv_OrderType = itemView.findViewById(R.id.tv_OrderType);
            ll_orderlist = itemView.findViewById(R.id.ll_orderlist);
            ll_orderlist2 = itemView.findViewById(R.id.order_lay);
            tvEquipmentId = itemView.findViewById(R.id.tvEquipmentId);
        }
    }


    public int getPosition(){
        return positon;
    }
}

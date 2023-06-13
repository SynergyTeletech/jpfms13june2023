package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentCompletedOrders;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;

import java.util.List;
import java.util.Locale;

/**
 * Created by Ved Yadav on 4/12/2019.
 */
public class CompletedOrdersAdapter extends RecyclerView.Adapter<CompletedOrdersAdapter.CompletedOrdersHolder> {
    private final FragmentCompletedOrders.ActionTake actionTake;
    private List<Order> confirmOrderList;
    private Context context;
    private LayoutInflater inflater;

    public CompletedOrdersAdapter(Context context, List<Order> confirmOrderList, FragmentCompletedOrders.ActionTake actionTake) {
        this.confirmOrderList = confirmOrderList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.actionTake = actionTake;
    }

    @NonNull
    @Override
    public CompletedOrdersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new CompletedOrdersHolder(inflater.inflate(R.layout.process_completed_order, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedOrdersHolder holder, int position) {
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (actionTake != null) {
                    actionTake.onActionTaken(FragmentCompletedOrders.ActionPerformed.INVOICE, confirmOrderList.get(position));
                }
                return false;
            }
        });

            holder.id.setText(confirmOrderList.get(position).getOrder_id());
            holder.location.setText(confirmOrderList.get(position).getLocation_name());
          //  holder.customerName.setText(confirmOrderList.get(position).getFname());
            holder.address.setText(confirmOrderList.get(position).getAddress());
            holder.contact_person.setText(confirmOrderList.get(position).getOrder_contact_person_name());
            holder.time.setText(confirmOrderList.get(position).getUnit_price());
            holder.phone.setText(confirmOrderList.get(position).getCreated_datatime());

        /* holder.status.setText(confirmOrderList.get(position).getStatus()=2?"Confirm":"Status");*/

        holder.locationLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uris = String.format(Locale.ENGLISH,
                        "http://maps.google.com/maps?daddr=%f,%f (%s)",
                        Double.parseDouble(confirmOrderList.get(position).getLatitude()),
                        Double.parseDouble(confirmOrderList.get(position).getLongitude())
                        , confirmOrderList.get(position).getLocation_name()) + "+to:12.9747,77.6094" + "+to:28.532062,77.310905";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uris));
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
        if (confirmOrderList.get(position).getStaus().contentEquals("5")) {
            holder.status.setText("Completed");
            holder.actionButton.setText("Print");
            holder.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionTake.onActionTaken(FragmentCompletedOrders.ActionPerformed.PRINT, confirmOrderList.get(position));
                }
            });

            holder.order_lay.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_100));
            holder.actionButton.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_400));
        } else {
            holder.status.setText("Status");
        }
        holder.quantity.setText(confirmOrderList.get(position).
                getQuantity());
    }

    @Override
    public int getItemCount() {
        return confirmOrderList == null ? 0 : confirmOrderList.size();
    }

    public class CompletedOrdersHolder extends RecyclerView.ViewHolder {
        private View locationLay;
        private View route_planInfo;
        private View order_lay;
        private View cardView;
        private TextView actionButton;
        public TextView id, location, address, contact_person, phone, quantity, time, status, customerName;


        public CompletedOrdersHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            id = itemView.findViewById(R.id.cust_id);
            location = itemView.findViewById(R.id.location);
            customerName = itemView.findViewById(R.id.customer_name);
            address = itemView.findViewById(R.id.address);
            contact_person = itemView.findViewById(R.id.contact_person);
            quantity = itemView.findViewById(R.id.qunantity);
            time = itemView.findViewById(R.id.time);
            phone = itemView.findViewById(R.id.phone_num);
            status = itemView.findViewById(R.id.status);
            actionButton = itemView.findViewById(R.id.actionButton);
            order_lay = itemView.findViewById(R.id.order_lay);
            locationLay = itemView.findViewById(R.id.locationLay);
            route_planInfo = itemView.findViewById(R.id.route_planInfo);
        }
    }
}

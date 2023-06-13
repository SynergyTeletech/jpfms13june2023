package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.orderSpinnermodel;

import java.util.ArrayList;

public class OrderSpinnerAdapter extends ArrayAdapter<orderSpinnermodel> {
    LayoutInflater inflater;
    ArrayList<orderSpinnermodel> objects;
    ViewHolder holder = null;

    public OrderSpinnerAdapter(Context context, int textViewResourceId, ArrayList<orderSpinnermodel> objects) {
        super(context, textViewResourceId, objects);
        inflater = ((Activity) context).getLayoutInflater();
        this.objects = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        orderSpinnermodel listItem = objects.get(position);
        View row = convertView;

        if (null == row) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.simple_spinner_dropdown_item, parent, false);
            holder.id = (TextView) row.findViewById(R.id.cust_id);
            holder.location = (TextView) row.findViewById(R.id.location);
            holder.address = (TextView) row.findViewById(R.id.address);
            holder.contact_person = (TextView) row.findViewById(R.id.contact_person);
            holder.quantity = (TextView) row.findViewById(R.id.qunantity);
            holder.time = (TextView) row.findViewById(R.id.time);

            holder.phone = (TextView) row.findViewById(R.id.phone_num);
            holder.status = (TextView) row.findViewById(R.id.status);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.id.setText(listItem.getOrder_id());
        holder.location.setText(listItem.getLoc());
        holder.address.setText(listItem.getAddress());
        holder.contact_person.setText(listItem.getContact_name());
        holder.time.setText(listItem.getTime());
        holder.phone.setText(listItem.getContact_person_phone());
        /* holder.status.setText(listItem.getStatus()=2?"Confirm":"Status");*/
        if (listItem.getStatus().contentEquals("2")) {
            holder.status.setText("Confirm");

        } else if (listItem.getStatus().contentEquals("3")) {
            holder.status.setText("In Process");
        } else if (listItem.getStatus().contentEquals("4")) {
            holder.status.setText("Order Arrived");
        } else {
            holder.status.setText("Status");
        }
        holder.quantity.setText(listItem.getQuantity());

        return row;
    }

    static class ViewHolder {
        TextView id, location, address, contact_person, phone, quantity, time, status;

    }
}

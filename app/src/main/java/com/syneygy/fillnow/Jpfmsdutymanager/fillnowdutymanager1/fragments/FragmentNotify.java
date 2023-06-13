package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.constant.AppConstants;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Arrival;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.ArriveJourney;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Journey;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.OrderStaus;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.PreferenceManager;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotify extends Fragment {
    private CheckBox outForOrder, arrived, complete;
    private String Status, vehicle, transactionId;
    private Context context;
    private int position;
    private DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;
    private List<Order> list = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        dispenseLocalDatabaseHandler = new DispenseLocalDatabaseHandler(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
       ArrayList<Order>orders=  dispenseLocalDatabaseHandler.getAcceptedOrder();
       for (Order order:orders){
           Log.d("order",order.getStaus().toString());
       }
//        dispenseLocalDatabaseHandler.deleteAcceptedOrder();


        vehicle = PreferenceManager.read(AppConstants.Vehicle_id, "");
//        transactionId = PreferenceManager.read("Transaction_ID", "");
//        Status = PreferenceManager.read("status", "");

        if (getArguments() != null) {
            transactionId = getArguments().getString("Transaction_ID", "");
            Status = getArguments().getString("status", "");
            position = getArguments().getInt("position", 0);
            Log.e("ePosyion", "" + position);
            Toast.makeText(context, "Notify User", Toast.LENGTH_SHORT).show();
        }
        outForOrder = (CheckBox) view.findViewById(R.id.cb_1);
        arrived = (CheckBox) view.findViewById(R.id.cb_2);
        complete = (CheckBox) view.findViewById(R.id.cb_3);
        arrived.setClickable(false);
        complete.setClickable(false);
        if (Status.contentEquals("3")) {
            outForOrder.setChecked(true);
            outForOrder.setClickable(false);
            arrived.setChecked(false);
            complete.setChecked(false);
            arrived.setClickable(true);
            complete.setClickable(false);
        } else if (Status.contentEquals("4")) {
            outForOrder.setClickable(false);
            arrived.setClickable(false);
            complete.setClickable(true);
            outForOrder.setChecked(true);
            arrived.setChecked(true);
            complete.setChecked(false);
        }

        outForOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        arrived.setEnabled(true);

                        Toast.makeText(context, "Out For Delivery", Toast.LENGTH_LONG).show();


                        /* outForOrder.setEnabled(false);*/

                        if (Status.contentEquals("2")) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (Utils.isNetworkConnected(context)) {
                                    SetupTransactionId();
                                } else {
                                    dispenseLocalDatabaseHandler.updateAcceptedOrder(transactionId,"3");
    //                                Toast.makeText(context, "Successfully updated" + dispenseLocalDatabaseHandler.getAcceptedOrder().size(), Toast.LENGTH_LONG).show();
    //                                list.clear();
    //                                list = dispenseLocalDatabaseHandler.getAcceptedOrder();
    //                                for (int i = 0; i < dispenseLocalDatabaseHandler.getAcceptedOrder().size(); i++) {
    //                                    if (position == Integer.parseInt(dispenseLocalDatabaseHandler.getAcceptedOrder().get(i).getTransaction_id())) {
    //                                        list.get(i).setStaus("3");
    //                                    }
    //                                }
    //
    //
    //                                dispenseLocalDatabaseHandler.addAcceptedOrder(list);
                                    //  Log.e("datachange",""+position+SharedPref.getAcceptedList().get(position).getStaus());
                                    fragmentGoBack();
                                }
                            }
                            outForOrder.setClickable(false);
                        } else {
                            Toast.makeText(context, "Select Confirm order only", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });


        arrived.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        complete.setEnabled(true);

                        Toast.makeText(context, "arrived", Toast.LENGTH_LONG).show();

                        if (Status.contentEquals("3")) {
                            if (Utils.isNetworkConnected(context)) {
                                SetupArrival();
                            } else {
                                Toast.makeText(context, "Successfully updated", Toast.LENGTH_LONG).show();
                                dispenseLocalDatabaseHandler.updateAcceptedOrder(transactionId,"4");
//                                list.clear();
//                                list = dispenseLocalDatabaseHandler.getAcceptedOrder();
//                                for (int i = 0; i < dispenseLocalDatabaseHandler.getAcceptedOrder().size(); i++) {
//                                    if (position == Integer.parseInt(dispenseLocalDatabaseHandler.getAcceptedOrder().get(i).getTransaction_id())) {
//                                        list.get(i).setStaus("4");
//                                    }
//                                }
//
//                                dispenseLocalDatabaseHandler.addAcceptedOrder(list);


                                fragmentGoBack();
                            }

                            arrived.setClickable(false);
                            complete.setClickable(true);
                        } else {
                            Toast.makeText(context, "Select only arrival Item", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });


        complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        Toast.makeText(context, "Complete", Toast.LENGTH_LONG).show();
                        /*   complete.setEnabled(false);*/
                        if (Status.contentEquals("4")) {
                            SetupComplete();
                            complete.setClickable(false);
                        } else {
                            Toast.makeText(context, "Select only Complete Order", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        return view;
    }

    private void SetupTransactionId() {
        progressDialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("transaction_id", transactionId);
        Log.e("TRANSACTION_ID",transactionId);
        hashMap.put(AppConstants.Vehicle_id, vehicle);
        Log.e("VEHICLE_ID",vehicle);

        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getNotify(hashMap).enqueue(new Callback<OrderStaus>() {
            @Override
            public void onResponse(Call<OrderStaus> call, Response<OrderStaus> response) {
                if (response.body().getSucc()) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    List<Journey> journeys = response.body().getJourney();
                    for (int i = 0; i < journeys.size(); i++) {
                        Status = journeys.get(i).getStaus();
                    }
                    dispenseLocalDatabaseHandler.updateAcceptedOrder(transactionId,"3");
//                    list.clear();
//                    list = dispenseLocalDatabaseHandler.getAcceptedOrder();
//                    for (int i = 0; i < dispenseLocalDatabaseHandler.getAcceptedOrder().size(); i++) {
//                        if (position == Integer.parseInt(dispenseLocalDatabaseHandler.getAcceptedOrder().get(i).getTransaction_id())) {
//                            list.get(i).setStaus("3");
//                        }
//                    }
//                    dispenseLocalDatabaseHandler.addAcceptedOrder(list);


                    Toast.makeText(context, "Successfully updated", Toast.LENGTH_LONG).show();
                    fragmentGoBack();
                }

            }

            @Override
            public void onFailure(Call<OrderStaus> call, Throwable t) {
                Toast.makeText(context, "Server Error occured", Toast.LENGTH_LONG).show();
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }


            }
        });
    }

    private void SetupArrival() {
        progressDialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("transaction_id", transactionId);
        hashMap.put(AppConstants.Vehicle_id, vehicle);

        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getNotifyArrival(hashMap).enqueue(new Callback<Arrival>() {
            @Override
            public void onResponse(Call<Arrival> call, Response<Arrival> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                dispenseLocalDatabaseHandler.updateAcceptedOrder(transactionId,"4");
//                ArrayList<ArriveJourney> arrivals = response.body().getArrived();
//                for (int i = 0; i < arrivals.size(); i++) {
//                    Status = arrivals.get(i).getStatus();//4
//                }
//                list.clear();
//                list = dispenseLocalDatabaseHandler.getAcceptedOrder();
//                for (int i = 0; i < dispenseLocalDatabaseHandler.getAcceptedOrder().size(); i++) {
//                    if (position == Integer.parseInt(dispenseLocalDatabaseHandler.getAcceptedOrder().get(i).getTransaction_id())) {
//                        list.get(i).setStaus("4");
//                    }
//                }
//                dispenseLocalDatabaseHandler.addAcceptedOrder(list);

                Toast.makeText(context, "Successfully updated", Toast.LENGTH_LONG).show();
                fragmentGoBack();
            }

            @Override
            public void onFailure(Call<Arrival> call, Throwable t) {
                Toast.makeText(context, "Server Error occured", Toast.LENGTH_LONG).show();
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        });
    }

    private void SetupComplete() {
        progressDialog.show();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("transaction_id", transactionId);
        hashMap.put(AppConstants.Vehicle_id, vehicle);

        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getNotifyComplete(hashMap).enqueue(new Callback<Arrival>() {
            @Override
            public void onResponse(Call<Arrival> call, Response<Arrival> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                ArrayList<ArriveJourney> arrivals = response.body().getArrived();
                for (int i = 0; i < arrivals.size(); i++) {
                    Status = arrivals.get(i).getStatus();
                }


                Toast.makeText(context, "Successfully updated", Toast.LENGTH_LONG).show();
                fragmentGoBack();

            }

            @Override
            public void onFailure(Call<Arrival> call, Throwable t) {
                Toast.makeText(context, "Server Error occurred", Toast.LENGTH_LONG).show();
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        });
    }

    public void fragmentGoBack() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

}

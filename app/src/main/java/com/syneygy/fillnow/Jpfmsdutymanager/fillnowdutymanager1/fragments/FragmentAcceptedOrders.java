package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.SynergyDispenser;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter.AcceptedOrdersAdapter;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.ServerRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.CammondQueueAtg;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.ServerATG285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.LogToFile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.AcceptedOrdersResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PrintData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.SaveRatingModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ChangePasswordbean;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.FineteckCalculation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.TokheiumCalculation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAcceptedOrders extends Fragment implements View.OnClickListener,RouterResponseRelay {

    private static final String TAG = FragmentAcceptedOrders.class.getSimpleName();

    private DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;
    private RecyclerView acceptedOrdersRecycler;
    private Context context;
    RouterResponseListener routerResponseListener;
    private AcceptedOrdersAdapter acceptedOrdersAdapter;
    private List<Order> orderArrayList = new ArrayList<>();
    private List<Order> inProgressOrderArrayList = new ArrayList<>();
    private List<Order> allOrderArrayList = new ArrayList<>();
    private List<Order> filteredList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private int position;
    AlertDialog alertDialogT;
    private String atgDipChart="";
    LoginResponse response;
    private Order orderToSendInDialog = null;
    private int atgCount = 0;
    private LinkedHashMap<String, JSONObject> atgLinkedHashMapTank1, atgLinkedHashMapTank2;
    int atgcount = 0;
    int atgcount2 = 0;
    private double tank1MaxVolume = 0f, tank2MaxVolume = 0f;
    private boolean isATGPort3Selected, isATGPort4Selected;
    int test = 0;
    int test2 = 0;
    private String atgSerialNoTank1 = "", atgSerialNoTank2 = "";
    DialogInterface dialogProgress;
    boolean isDialogProgressDismiss= false;
    private AcceptedOrdersResponse acceptedOrdersResponse = new AcceptedOrdersResponse();


    ActionTake actionTake = new ActionTake() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onActionTaken(ActionPerformed actionPerformed, Order order, int postion) {
            FragmentManager fragmentManager = getChildFragmentManager();
            position = postion;
            switch (actionPerformed) {
                case NOTIFY:
                    if(Utils.internetIsConnected(context)) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Transaction_ID", order.getTransaction_id());
                        bundle1.putString("status", order.getStaus());
                        bundle1.putInt("position", postion);
                        FragmentNotify fragmentNotify = new FragmentNotify();
                        fragmentNotify.setArguments(bundle1);
                        fragmentManager
                                .beginTransaction()
                                .addToBackStack(FragmentNotify.class.getSimpleName())
                                .add(R.id.ll_child_fragment_container, fragmentNotify, FragmentNotify.class.getSimpleName())
                                .commit();
                    }
                    else {
                        Toast.makeText(context,"App in offline mode",Toast.LENGTH_SHORT).show();
                    }
                    break;


                case COMPLETE:
                    if(Utils.internetIsConnected(context)) {
                        completeOrder(order.getTransaction_id(), order.getLogin_id());
                    }
                    else {
                        Toast.makeText(context,"App in offline mode",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case INVOICE:
                    if(Utils.internetIsConnected(context)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("performa", order.getPerformaId());
                        FragmentInvoice fragmentInvoice = new FragmentInvoice();
                        fragmentInvoice.setArguments(bundle);
                        fragmentManager
                                .beginTransaction()
                                .addToBackStack(FragmentInvoice.class.getSimpleName())
                                .add(R.id.ll_child_fragment_container, fragmentInvoice, FragmentInvoice.class.getSimpleName())
                                .commit();
                    }
                    else {
                        Toast.makeText(context,"App in offline mode",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case DISPENSE:
                    Intent intent = new Intent(context, SynergyDispenser.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("isFromFreshDispense", false);
                    intent.putExtra("position", position);
                    intent.putExtra("orderDetail", order);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to start dispensing?");
                    builder.setPositiveButton("Start Dispensing", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                            apiInterface.offlineOrderstatuschanged(String.valueOf(position)).enqueue(new Callback<ChangePasswordbean>() {
                                @Override
                                public void onResponse(Call<ChangePasswordbean> call, Response<ChangePasswordbean> response) {

                                }

                                @Override
                                public void onFailure(Call<ChangePasswordbean> call, Throwable t) {

                                }
                            });

                            // offline save data
                            List<Order> listt = new ArrayList<>();
                            List<Order> prolist = new ArrayList<>();
                            listt = dispenseLocalDatabaseHandler.getAcceptedOrder();

                            prolist = dispenseLocalDatabaseHandler.getOfflineOrder();
                            if (listt.size() > 0) {
                                for (int i = 0; i < dispenseLocalDatabaseHandler.getAcceptedOrder().size(); i++) {
                                    if (position == Integer.parseInt(dispenseLocalDatabaseHandler.getAcceptedOrder().get(i).getTransaction_id())) {
                                        listt.get(i).setStaus("11");
                                        Order order1 = listt.get(i);
                                        if (prolist != null) {
                                            prolist.add(order1);
                                        }
                                        listt.remove(i);
                                        dispenseLocalDatabaseHandler.deleteAcceptedOrder();
                                        dispenseLocalDatabaseHandler.deleteProgressOrder();
                                        dispenseLocalDatabaseHandler.addAcceptedOrder(listt);
                                        dispenseLocalDatabaseHandler.addProgressOrder(prolist);
                                    }
                                }

                            }

                            startActivity(intent);
                        }


                    });
                    builder.setNegativeButton("Order No Show", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sureNoShow();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
//                    } else {
//                        requestOtp(order);
//                    }

                    break;
                case VIEW_PROFILE:
                    break;
            }
        }
    };



    private void requestOtp(Order order) {
//       progressDialog.setMessage("Please wait..");
//        progressDialog.show();
//        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
//        apiInterface.requestForOtp(order.getOrder_id()).enqueue(new Callback<RequestOptModel>() {
//            @Override
//            public void onResponse(Call<RequestOptModel> call, Response<RequestOptModel> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getSucc()) {
//                        progressDialog.hide();
//
//                        Toast.makeText(context, "" + response.body().getPublicMsg(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View customLayout = getLayoutInflater().inflate(R.layout.otp_alert_layout, null);
        builder.setView(customLayout);
        EditText otpEditText = customLayout.findViewById(R.id.otpEdit);
        TextView submitBtn = customLayout.findViewById(R.id.submit);
        TextView resendBtn = customLayout.findViewById(R.id.reSend);

        AlertDialog dialog = builder.create();
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     progressDialog.show();

                requestOtp(order);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                              progressDialog.show();
//                                dialog.hide();
                sumitOrder(order, otpEditText.getText().toString());
            }
        });

        dialog.show();



    }

    private void sumitOrder(Order order, String s) {

        Log.d("opttpt" + order.getOffline_otp(), s);
        if (order.getOffline_otp().equals(s)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to start dispensing for this order ?");
            builder.setPositiveButton("Start Dispensing", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(context, SynergyDispenser.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("isFromFreshDispense", false);
                    intent.putExtra("orderDetail", order);
                    Log.e("postion", "" + position);
                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                    apiInterface.offlineOrderstatuschanged(String.valueOf(position)).enqueue(new Callback<ChangePasswordbean>() {
                        @Override
                        public void onResponse(Call<ChangePasswordbean> call, Response<ChangePasswordbean> response) {

                        }

                        @Override
                        public void onFailure(Call<ChangePasswordbean> call, Throwable t) {

                        }
                    });
                    intent.putExtra("position", position);
                    // offline save data
                    List<Order> list = new ArrayList<>();
                    List<Order> prolist = new ArrayList<>();
                    list = SharedPref.getAcceptedList();
                    if (list.size() > 0) {
                        for (int i = 0; i < SharedPref.getAcceptedList().size(); i++) {
                            if (position == Integer.parseInt(SharedPref.getAcceptedList().get(i).getTransaction_id())) {
                                list.get(i).setStaus("11");
                                Order order1 = list.get(i);
                                prolist.add(order1);
                                list.remove(i);
                                SharedPref.setProgressList(prolist);
                                SharedPref.setAcceotedList(list);
                            }
                        }

                    }

                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Order No Show", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sureNoShow();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(context, "Otp Mismatch", Toast.LENGTH_SHORT).show();
        }


    }

    private View noPendingOrder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        dispenseLocalDatabaseHandler = new DispenseLocalDatabaseHandler(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_layout, container, false);
        ((BrancoApp) context.getApplicationContext()).getAtgDataObserver().observe((AppCompatActivity) context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        });
        acceptedOrdersRecycler = view.findViewById(R.id.ordersRecycler);
        acceptedOrdersRecycler.setLayoutManager(new LinearLayoutManager(context));
        noPendingOrder = view.findViewById(R.id.noPendingOrder);
        RouterResponseRelay routerResponseRelay=this;
//        ServerRelay serverRelay=new ServerRelay("192.168.1.104",54301,this);
        ServerRelay.getInstance("192.168.1.104",54301).setListner(routerResponseRelay);
        response=SharedPref.getLoginResponse();
        if(response.getData().get(0).getAtgDataList().isEmpty()){
            atgSerialNoTank1="1234";
        }else {
            atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_atg_serial_no();


        }
        atgDipChart=response.getData().get(0).getAtgDataList().get(0).getData_volume();
        NavigationDrawerActivity.searchView.setVisibility(View.VISIBLE);
        view.findViewById(R.id.btnPendingOrderList).setOnClickListener(this);
        view.findViewById(R.id.btnRefreshOrdeList).setOnClickListener(this);
        new SearchViewFormatter()
                .setSearchIconResource(R.drawable.ic_search, true, true) //true to icon inside edittext, false to outside
                .setSearchVoiceIconResource(R.drawable.ic_search)
                .setSearchTextColorResource(R.color.white)
                .setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                .format(NavigationDrawerActivity.searchView);
        noPendingOrder.setVisibility(View.GONE);

        Log.e("Atgs", "" + SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size());
        if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size() > 1) {
            Log.e("AtgSerialNo2", SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no());
            String string2 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(1).getData_volume();
            if (string2 != null) {
                atgSerialNoTank2 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(1).getData_atg_serial_no();
                test2 = atgLinkedHashMapTank2.size();
            }
            try {
                Object obj = atgLinkedHashMapTank2.entrySet().toArray()[atgLinkedHashMapTank2.size() - 1];
                LinkedHashMap.Entry linkedTreeMap = (LinkedHashMap.Entry) (obj);
                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap.getValue()).getAsJsonObject();
                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
                    tank2MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                }
            } catch (Exception je) {

            }
        }
        else if (SharedPref.getLoginResponse().getData().get(0).atgDataList.size() == 1) {
            Log.e("AtgSerialNo", SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no());
            String string = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
            if (string != null) {
                Log.e("AtgDataFinkam", string);
                atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 = new JSONObject(string);
                    atgLinkedHashMapTank1 = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAcceptedOrdersList(String VehicleId) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d("noInternet", "noInternet");
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            orderArrayList.clear();
            inProgressOrderArrayList.clear();
            allOrderArrayList.clear();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            noPendingOrder.setVisibility(View.GONE);
            if(dispenseLocalDatabaseHandler.getOfflineOrder().size()>0 || dispenseLocalDatabaseHandler.getAcceptedOrder().size()>0) {
                if (dispenseLocalDatabaseHandler.getOfflineOrder().size() > 0) {
                    inProgressOrderArrayList = dispenseLocalDatabaseHandler.getOfflineOrder();
                    Log.d("inProgreesDialog",""+inProgressOrderArrayList);
                }

                if (dispenseLocalDatabaseHandler.getAcceptedOrder().size() > 0) {
                    orderArrayList = dispenseLocalDatabaseHandler.getAcceptedOrder();
                    Log.d("inProgreesDialog",""+orderArrayList);
                }

//                try {
//                    if (SharedPref.getProgressList() != null) {
//                        Log.v("test","new test call");
//
//                        inProgressOrderArrayList = SharedPref.getProgressList();// show Completed list
//                        Log.v("test","new test size  check" + inProgressOrderArrayList.size());//all list
//                        Log.v("test","new test size  check" + SharedPref.getAcceptedList().size());
//                    }
//                    if (SharedPref.getAcceptedList() != null) {
//                        orderArrayList = SharedPref.getAcceptedList();
//                    }

                //The Following lines added by Laukendra on 07-11-19

                List<Order> orderArrayListExpress = new ArrayList<>();
                List<Order> orderArrayListNormal = new ArrayList<>();
                List<Order> orderArrayListPriority = new ArrayList<>();
                if (orderArrayList.size() > 0) {
                    for (Order order : orderArrayList) {
                        if (order.getOrderType().equalsIgnoreCase("1")) {
                            orderArrayListNormal.add(order);
                        } else if (order.getOrderType().equalsIgnoreCase("2")) {
                            orderArrayListExpress.add(order);
                        } else if (order.getOrderType().equalsIgnoreCase("3")) {
                            orderArrayListPriority.add(order);
                        }
                    }

                    orderArrayList.clear();
                    orderArrayList.addAll(orderArrayListPriority);
                    orderArrayList.addAll(orderArrayListExpress);
                    orderArrayList.addAll(orderArrayListNormal);
                }

                if (inProgressOrderArrayList.size() > 0) {
                    allOrderArrayList.addAll(inProgressOrderArrayList);
                }
                if (orderArrayList.size() > 0) {
                    allOrderArrayList.addAll(orderArrayList);
                }
                acceptedOrdersAdapter = new AcceptedOrdersAdapter(context, allOrderArrayList, actionTake);
                acceptedOrdersRecycler.setAdapter(acceptedOrdersAdapter);
                DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                acceptedOrdersRecycler.addItemDecoration(itemDecor);
                acceptedOrdersRecycler.setItemAnimator(new DefaultItemAnimator());
                NavigationDrawerActivity.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        filteredList.clear();
                        Toast.makeText(context, "" + allOrderArrayList.size(), Toast.LENGTH_SHORT).show();
                        if (s.length() > 0) {
                            if (allOrderArrayList.size() > 0) {
                                for (Order order1 : allOrderArrayList) {
                                    if (order1 != null) {
                                        if (order1.getFname().toLowerCase().contains(s.toLowerCase())) {
                                            filteredList.add(order1);
                                        } else if (order1.getAddress().toLowerCase().contains(s.toLowerCase())) {
                                            filteredList.add(order1);
                                        } else if (order1.getOrderdate().contains(s.toLowerCase())) {
                                            Log.e("4", "4");
                                            filteredList.add(order1);
                                        }
                                    }
                                }

                                if (filteredList.size() > 0) {
                                    AcceptedOrdersAdapter acceptedOrdersAdapter1 = new AcceptedOrdersAdapter(context, filteredList, actionTake);
                                    acceptedOrdersRecycler.setAdapter(acceptedOrdersAdapter1);
                                } else {
                                    Toast.makeText(context, "No Mactching Result Found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Search Field Empty", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
                if (context instanceof NavigationDrawerActivity) {
                    ((NavigationDrawerActivity) context).setTotalTicketAndFuel(String.valueOf(allOrderArrayList.size()), String.valueOf(calculateOpenTicketsFuel(allOrderArrayList)));
                }


                boolean isDeliveryInProgressOrder = false;

                for (Order order : inProgressOrderArrayList) {
                    if (order.getStaus().equalsIgnoreCase("11")) {
                        isDeliveryInProgressOrder = true;
                        Log.v("ORDER PROGRESS", String.valueOf(isDeliveryInProgressOrder) + order.getStaus());
                        orderToSendInDialog = order;
                    }
//                         if (isDeliveryInProgressOrder) {
//                             AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                             builder.setCancelable(true);
//                             builder.setTitle("You have an order pending in delivery, Proceed to complete it.");
//                             builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                 @Override
//                                 public void onClick(DialogInterface dialog, int which) {
//
//                                     if (orderToSendInDialog.getStaus().equalsIgnoreCase("12")) {
//                                         Log.d("order status", "dialog resume" + order.getStaus());
//                                         dialog.dismiss();
//                                     } else {
//                                         dialogProgress = dialog;
//                                         Log.d("order status", "" + orderToSendInDialog.getStaus());
//                                         Intent intent = new Intent(context, SynergyDispenser.class);
//                                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                         intent.putExtra("isFromFreshDispense", false);
//                                         intent.putExtra("orderDetail", orderToSendInDialog);
//
//                                         intent.putExtra("position", position);
//                                         startActivity(intent);
//                                         dialogProgress.dismiss();
//                                         isDialogProgressDismiss = true;
//
//                                     }
//
//
//                                 }
//                             });
//                             AlertDialog alertDialog = builder.create();
//                             //  alertDialogT = builder.create();
//                             alertDialog.show();
//                         } else {
//                         /*   if (order.getStaus().equalsIgnoreCase("12")){
//                                Log.e("progress list", "else dialog call size");*/
//                             Log.e("progress list", "else dialog call size TEST");
//                             if(dialogProgress!=null) {
//                                 dialogProgress.dismiss();
//                             }
//                         }

                    if (!isDeliveryInProgressOrder && order.getStaus().equalsIgnoreCase("12")) {
                        Log.e("progress list", "TEST");
                        if(dialogProgress!=null) {
                            dialogProgress.dismiss();
                        }
                    }

                }
            }


            else {
                Log.e("hikamalq", "hi14");
                noPendingOrder.setVisibility(View.VISIBLE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnPendingOrderList:
                if (context instanceof NavigationDrawerActivity) {
                    ((NavigationDrawerActivity) context).setUpOrderList();
                }
                break;
            case R.id.btnRefreshOrdeList:
                getAcceptedOrdersList(SharedPref.getVehicleId());
                break;
        }


    }

    @Override
    public void RecivedData1(String responseAscci, String responseHex, boolean isConnected) {
        if(!responseAscci.contains("SERIAL PORT")) {
            Log.d("hexRespnse", responseAscci + "," + responseHex);
            String volumes = "0";
            if (responseHex.contains("0103")) {
                FineteckCalculation fineteckCalculation = new FineteckCalculation();
                volumes = String.format("%.2f", fineteckCalculation.getAtgState(fineteckCalculation.response(responseHex), atgDipChart));
            } else if (responseAscci.contains(atgSerialNoTank1)) {
                TokheiumCalculation tokheiumCalculation = new TokheiumCalculation();
                Float volume = tokheiumCalculation.getAtgState(tokheiumCalculation.response(responseAscci), atgDipChart);
                volumes = String.format("%.2f", volume);
                Log.d("hexRespnse", responseAscci + "," + volumes);
            } else {

            }

            String finalVolumes = volumes;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("hexRespnse", responseAscci + "," + finalVolumes);
                    NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + finalVolumes);
                }
            });
        }


    }

    public interface ActionTake {
        public void onActionTaken(ActionPerformed actionPerformed, Order order, int postion);
    }

    public enum ActionPerformed {
        DISPENSE, NOTIFY, INVOICE, VIEW_PROFILE, COMPLETE
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        Log.e("Accepted order Resume", "call");

        //  Toast.makeText(context, "onResume offline", Toast.LENGTH_LONG).show();
        if (context instanceof NavigationDrawerActivity) {

            ((NavigationDrawerActivity) context).setNavigationDrawerItemTitle("Accepted Orders");
            ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
            // Toast.makeText(context, "onResume offline 2", Toast.LENGTH_LONG).show();
        }

        getAcceptedOrdersList(SharedPref.getVehicleId());

        if(dialogProgress !=null){
            Log.e("dialog", "not null call");
            dialogProgress.dismiss();
        }
    }

    public void sureNoShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to cancel this Order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                TO DO
                //cancel order (API -> order status= 10)
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void completeOrder(String transactionID, String CustId) {
        //check with saurabh for Input pararmeters
        //getAcceptedOrdersList("");
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Utils.internetIsConnected(context)) {
                ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                apiInterface.orderComplete(transactionID, SharedPref.getLoginId()).enqueue(new Callback<LoginResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (response.isSuccessful() && response.body().getSucc()) {
                            try {
//                                 dispenseLocalDatabaseHandler.deleteProgressOrder();
                                List<Order> list = dispenseLocalDatabaseHandler.getOfflineOrder();
                                Log.d("pravintp",""+list.size()+","+list.toString());
                                for (int i = 0; i < list.size(); i++) {
                                    if (transactionID.equalsIgnoreCase(list.get(i).getTransaction_id())) {
                                        dispenseLocalDatabaseHandler.deleteProgressOrderTransaction(transactionID);

                                    }
                                }
                                List<Order> list1 = dispenseLocalDatabaseHandler.getOfflineOrder();
                                Log.d("pravintp1",""+list1.size()+","+list1.toString());
//                                SharedPref.setProgressList(list);
//                                List<DeliveryInProgress> dilveryList = SharedPref.getOfflineOrderList();
//                                for (int j = 0; j < SharedPref.getOfflineOrderList().size(); j++) {
//                                    //   Log.e("offlinelist", SharedPref.getOfflineOrderList().toString());
//                                    if (transactionID.equalsIgnoreCase(SharedPref.getOfflineOrderList().get(j).getOrder().get(0).getTransactionId())) {
//                                        dilveryList.remove(j);
//                                    }
//                                }
//                                SharedPref.setOffineOrderList(dilveryList);
                                getAcceptedOrdersList(SharedPref.getVehicleId());
                                try {
                                    Dialog rankDialog = new Dialog(context, R.style.FullHeightDialog);
                                    rankDialog.setContentView(R.layout.rank_dialog);
                                    rankDialog.setCancelable(true);
                                    RatingBar ratingBar = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);
                                    // ratingBar.setRating(userRankValue);

                                    TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);


                                    Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                                    updateButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ApiInterface apiInterface1 = ApiClient.getClientCI().create(ApiInterface.class);
                                            apiInterface1.saverating(
                                                    "" + ratingBar.getRating(), transactionID, "1", CustId, SharedPref.getLoginId()
                                            ).enqueue(new Callback<SaveRatingModel>() {
                                                @Override
                                                public void onResponse(Call<SaveRatingModel> call, Response<SaveRatingModel> response) {
                                                    if (response.isSuccessful()) {
                                                        if (response.body().getSucc()) {
                                                            Toast.makeText(context, "" + response.body().getPublicMsg(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<SaveRatingModel> call, Throwable t) {

                                                }
                                            });
                                            rankDialog.dismiss();
                                        }
                                    });
                                    //now that the dialog is set up, it's time to show it
                                    rankDialog.show();

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(context, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(context, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private String calculateOpenTicketsFuel(List<Order> orderList) {
        Double total = 0d;
        for (Order order : orderList) {
            total += Double.parseDouble(order.getQuantity());
            Log.e("TOTAL QUANTITY", String.valueOf(total));

        }
        return String.valueOf(total);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NavigationDrawerActivity.searchView.setVisibility(View.GONE);
    }
}

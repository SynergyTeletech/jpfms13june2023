package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter.OrderAdapter;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter.OrderSpinnerAdapter;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.constant.AppConstants;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.AcceptedOrdersResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.CheckAvailability;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.ConfirmOrder;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.OfflineOrderDetailData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.RejectOrder;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Root;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.orderSpinnermodel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SwipeToDeleteCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOrderList extends Fragment implements View.OnClickListener, OrderAdapter.OnItemCheckListener {
    private String vehicleId;
    private List<Order> orderList;
    private List<Order> confirmOrderList;
    private List<Order> sortedOrder;
    private RecyclerView orderRecyclerView;
    private String TAG = FragmentOrderList.class.getSimpleName();
    private View btnAcceted;
    private View rejectOrder;

    private Spinner order_spinner;
    private String orderid, loc, address, cont_person, orderphone, orderedquantity, time, status, vehical;
    private ArrayList<orderSpinnermodel> spinneritem;
    private OrderAdapter orderAdapter;
    private ArrayList<String> transaction_info;
    private OrderSpinnerAdapter orderSpinnerAdapter;
    private int statuss;
    orderSpinnermodel orderSpinnermodels;
    private List<ConfirmOrder> order;
    private List<Integer> positions;
    private ArrayList<ConfirmOrder> spinner_data;

    private SharedPreferences pref;
    private boolean isSubmit=false;
    private boolean mNaviFirstHit = true;
    List<Order> acceptedOrder = null;
    private Context context = BrancoApp.getContext();
    private View noPendingOrder;
    private String transactioIDs = "";
    private StringBuffer transactioIDoffs ;
    private CheckBox selectAllCheck;
    CoordinatorLayout coordinatorLayout;
    DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static byte CalcLRC(byte[] bytes) {
        byte LRC = 0x00;
        for (byte aByte : bytes) {
            LRC = (byte) ((LRC + aByte) & 0xFF);
        }
        return (byte) (((LRC ^ 0xFF) + 1) & 0xFF);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        orderRecyclerView = view.findViewById(R.id.order_list_recycler);
        noPendingOrder = view.findViewById(R.id.noPendingOrder);
        dispenseLocalDatabaseHandler = new DispenseLocalDatabaseHandler(getActivity());
        order_spinner = view.findViewById(R.id.spinner_items);
        selectAllCheck = view.findViewById(R.id.selectAllCheck);

        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        btnAcceted = ((AppCompatActivity) context).findViewById(R.id.btn_Accept);
        rejectOrder = ((AppCompatActivity) context).findViewById(R.id.rejectOrder);

        ArrayList<OrderDispenseLocalData> local =    dispenseLocalDatabaseHandler.getAllOrderDispenseDataList();
        if(local.size()>0) {
            for (OrderDispenseLocalData data : local) {
                if (data.getStatus().equalsIgnoreCase("0")) {
                    isSubmit = true;
                    break;
                } else {
                    isSubmit = false;
                }
            }
        }

        rejectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectItem(sortedOrder);
            }
        });
        selectAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    if (orderList != null) {
                        if (sortedOrder != null) {
                            sortedOrder.clear();
                        }

                        orderAdapter.selectAll();
                        for (Order order : orderList) {
                            sortedOrder.add(order);
                        }

                        orderAdapter.notifyDataSetChanged();

                        if (context instanceof NavigationDrawerActivity) {
                            if (sortedOrder != null && sortedOrder.size() > 0) {
                                btnAcceted.setEnabled(true);
                            }
                            ((NavigationDrawerActivity) context).setNavigationDrawerItemTitle("Order List");
//            ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.VISIBLE);

                        }

                    }
                } else {
                    orderAdapter.unselectedAll();
                    sortedOrder.clear();
                    if (context instanceof NavigationDrawerActivity) {
                        if (sortedOrder != null && sortedOrder.size() > 0) {
                            btnAcceted.setEnabled(true);
                        } else {
                            btnAcceted.setEnabled(false);
                        }
                        ((NavigationDrawerActivity) context).setNavigationDrawerItemTitle("Order List");
//            ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.VISIBLE);

                    }


                }
            }
        });

        try {
            view.findViewById(R.id.btnAcceptedOrderList).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            view.findViewById(R.id.btnRefreshOrdeList).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        noPendingOrder.setVisibility(View.GONE);
        spinner_data = new ArrayList<>();
        orderSpinnermodels = new orderSpinnermodel("orderid", "Loc", "Address", "TranId", "Name", "PhoneNumber", "Qty", "Time", "Status");
        if(!isSubmit) {
            getAcceptedOrdersList(SharedPref.getVehicleId());
        }
        order_spinner.setPrompt("Select Order");
        spinneritem = new ArrayList<>();
        transaction_info = new ArrayList<>();
        positions = new ArrayList<>();
        orderList = new ArrayList<>();
        confirmOrderList = new ArrayList<>();
        sortedOrder = new ArrayList<>();

        order = new ArrayList<>();
        try {
            Bundle bundle = getArguments();
            vehicleId = bundle.getString(AppConstants.Vehicle_id);
//            ConfirmStatus(vehicleId);
//            getOrderList(vehicleId, "13");
            btnAcceted.setOnClickListener(this);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getOrderList(vehicleId, "13");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getOrderList(String vehicleId, String Status) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        if (Utils.isNetworkConnected(context)) {
            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
            apiInterface.getOrder(vehicleId, Status).enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.i(TAG, "" + call.request().url());
                    if (response.isSuccessful() && response.body().getSucc()) {
                        noPendingOrder.setVisibility(View.GONE);
                        try {
                            orderList = response.body().getOrder();
                            if (orderList.size() > 0) {
                                setOrderListAdapter();
                                selectAllCheck.setVisibility(View.VISIBLE);

                                if (context instanceof NavigationDrawerActivity) {
                                    ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.VISIBLE);
                                    ((NavigationDrawerActivity) context).setTotalTicketAndFuel(String.valueOf(orderList.size()), String.valueOf(calculateOpenTicketsFuel(orderList)));
                                }
                            } else {

                                noPendingOrder.setVisibility(View.VISIBLE);
                                ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        catch (IllegalStateException e){

                        }
                    } else {
                        Log.e("ObjectList", "object!");
                        noPendingOrder.setVisibility(View.VISIBLE);
                        ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    try {
                        Log.e(TAG, " " + t.getMessage());
                        noPendingOrder.setVisibility(View.VISIBLE);
                        ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(context, "Server Problem.. Please try after sometime!!!", Toast.LENGTH_SHORT).show();
                    }
                    catch (IllegalStateException e){

                    }

                }
            });
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            noPendingOrder.setVisibility(View.VISIBLE);
            ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
            Toast.makeText(context, "Server Problem.. Please try after sometime!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private String calculateOpenTicketsFuel(List<Order> orderList) {
        Double total = 0d;
        for (Order order : orderList) {
            total += Double.parseDouble(order.getQuantity());

        }
        return String.valueOf(total);
    }

    private void setOrderListAdapter() {
        orderAdapter = new OrderAdapter(context, orderList, vehicleId, this);
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setAdapter(orderAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(ContextCompat.getDrawable(context, R.drawable.dividerline));

        orderRecyclerView.addItemDecoration(itemDecor);
        orderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        // enableSwipeToDeleteAndUndo();
        orderAdapter.notifyDataSetChanged();
    }



    private void rejectItem(List<Order> item) {
        for (Order order : item) {
            transaction_info.add(order.getTransaction_id());
            transactioIDs = transactioIDs + order.getTransaction_id() + ",";
        }

        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.rejectOrder(transactioIDs).enqueue(new Callback<RejectOrder>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<RejectOrder> call, Response<RejectOrder> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSucc()) {
                        Toast.makeText(context, "Order Rejected", Toast.LENGTH_SHORT).show();
                        getOrderList(SharedPref.getVehicleId(), "13");
                    }
                }
            }

            @Override
            public void onFailure(Call<RejectOrder> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (context instanceof NavigationDrawerActivity) {
            if (sortedOrder != null && sortedOrder.size() > 0) {
                btnAcceted.setEnabled(true);
            } else {
                btnAcceted.setEnabled(false);
            }
            ((NavigationDrawerActivity) context).setNavigationDrawerItemTitle("Order List");
//            ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.VISIBLE);

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Accept:
                if (sortedOrder.size() > 0) {
                    sendAcceptedData(sortedOrder);
                } else {
                    try {
                        Toast.makeText(context, "Please Select some order", Toast.LENGTH_LONG).show();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.btnAcceptedOrderList:
                if (context instanceof NavigationDrawerActivity) {
                    ((NavigationDrawerActivity) context).setupOperationMenu();
                }
                break;
            case R.id.btnRefreshOrdeList:
                getOrderList(SharedPref.getVehicleId(), "13");
                break;
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendAcceptedData(List<Order> transaction_ids) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        Log.e("Send json from server", new Gson().toJson(transaction_ids));
        for (Order order : transaction_ids) {
            transaction_info.add(order.getTransaction_id());
            transactioIDs = transactioIDs + order.getTransaction_id() + ",";
        }



        System.out.println("Transactions IDs" + transactioIDs);
        if (Utils.isNetworkConnected(context)) {
            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
            apiInterface.sendOrderList(transactioIDs, vehicleId, SharedPref.getLoginResponse().getData().get(0).getDuty_id()).enqueue(new Callback<CheckAvailability>() {

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<CheckAvailability> call, Response<CheckAvailability> response) {

                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        btnAcceted.setEnabled(false);
                    }
//                    order = response.body().getOrder();

                    getAcceptedOrdersList(SharedPref.getVehicleId());
                    sortedOrder.clear();
                    getOrderList(SharedPref.getVehicleId(), "13");

//                    orderAdapter.update(positions);
//                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<CheckAvailability> call, Throwable t) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("tag", t.getMessage());
                }
            });


        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(context, "Internet connection error", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getOfflinedetails(String transactioIDs) {

        if (Utils.isNetworkConnected(context)) {
            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
            apiInterface.getOfflineData(transactioIDs).enqueue(new Callback<OfflineOrderDetailData>() {
                @Override
                public void onResponse(Call<OfflineOrderDetailData> call, Response<OfflineOrderDetailData> response) {

                    if (response.isSuccessful() && response.body().getSucc()) {

                        SharedPref.setOffineOrderList(response.body().getOfflineData());
                    }
                }

                @Override
                public void onFailure(Call<OfflineOrderDetailData> call, Throwable t) {
                    Toast.makeText(context, "Server Problem.. Please try after sometime!!!", Toast.LENGTH_SHORT).show();
                    noPendingOrder.setVisibility(View.VISIBLE);

                    ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);

                    Log.e(TAG, t.getMessage() + "");
                }
            });
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getAcceptedOrdersList(String VehicleId) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        if (Utils.isNetworkConnected(context)) {
            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
            apiInterface.getConfirmOrder(VehicleId).enqueue(new Callback<AcceptedOrdersResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<AcceptedOrdersResponse> call, Response<AcceptedOrdersResponse> response) {

                    if (response.isSuccessful() && response.body().getSucc()) {
                        dispenseLocalDatabaseHandler.deleteAcceptedOrder();
                        dispenseLocalDatabaseHandler.deleteProgressOrder();
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        transactioIDoffs=new StringBuffer();
                        if (response.body().getOrder().size() > 0) {

                            dispenseLocalDatabaseHandler.addAcceptedOrder(response.body().getOrder());

                            for (Order order:response.body().getOrder()){
                                transactioIDoffs.append(order.getTransaction_id()).append(",");
                            }
                        }
                        if (response.body().getDeliveryInprogress().size() > 0) {
                            dispenseLocalDatabaseHandler.addProgressOrder(response.body().getDeliveryInprogress());
                            for (Order order:response.body().getDeliveryInprogress()){
                                transactioIDoffs.append(order.getTransaction_id()).append(",");
                            }
                        }
                        String transact=transactioIDoffs.toString();
                        if(transact.length()>0){
                            transact=transact.substring(0,transact.length()-1);
                        }


                        getOfflinedetails(transact);
                    }
                    else {
                        dispenseLocalDatabaseHandler.deleteAcceptedOrder();
                        dispenseLocalDatabaseHandler.deleteProgressOrder();
                    }
                }

                @Override
                public void onFailure(Call<AcceptedOrdersResponse> call, Throwable t) {
                    Toast.makeText(context, "Server Problem.. Please try after sometime!!!", Toast.LENGTH_SHORT).show();
                    noPendingOrder.setVisibility(View.VISIBLE);
                    ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e(TAG, t.getMessage() + "");
                }
            });
        } else {
            noPendingOrder.setVisibility(View.VISIBLE);
            ((NavigationDrawerActivity) context).findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(context, "No Internet.. Please try after sometime!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemCheck(Order order, int position) {
        sortedOrder.add(order);
        positions.add(position);
        if (sortedOrder.size() > 0) {
            btnAcceted.setEnabled(true);
        } else {
            btnAcceted.setEnabled(false);
        }

    }

    @Override
    public void onItemUncheck(Order order, int position) {
        sortedOrder.remove(order);
        if (sortedOrder.size() > 0) {
            btnAcceted.setEnabled(true);
        } else {
            btnAcceted.setEnabled(false);
        }
    }

}


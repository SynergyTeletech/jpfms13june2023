package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.DispenserUnitActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter.CompletedOrdersAdapter;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.AddReadingsDialog;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.Click;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInprogres;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Root;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCompletedOrders extends Fragment {

    private DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;
    private static final String TAG = FragmentCompletedOrders.class.getSimpleName();

    RecyclerView completedOrdersRecycler;
    private Context context;

    ActionTake actionTake = new ActionTake() {
        @Override
        public void onActionTaken(ActionPerformed actionPerformed, Order order) {
            FragmentManager fragmentManager = getChildFragmentManager();
            switch (actionPerformed) {
                case NOTIFY:
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("Transaction_ID", order.getTransaction_id());
                    bundle1.putString("status", order.getStaus());
                    FragmentNotify fragmentNotify = new FragmentNotify();
                    fragmentNotify.setArguments(bundle1);
                    fragmentManager
                            .beginTransaction()
                            .addToBackStack(FragmentNotify.class.getSimpleName())
                            .add(R.id.ll_child_fragment_container, fragmentNotify, FragmentNotify.class.getSimpleName())
                            .commit();
                    break;
                case INVOICE:

                    Bundle bundle = new Bundle();
                    bundle.putString("performa", order.getPerformaId());
                    FragmentInvoice fragmentInvoice = new FragmentInvoice();
                    fragmentInvoice.setArguments(bundle);
                    fragmentManager
                            .beginTransaction()
                            .addToBackStack(FragmentInvoice.class.getSimpleName())
                            .add(R.id.ll_child_fragment_container, fragmentInvoice, FragmentInvoice.class.getSimpleName())
                            .commit();
                    break;
                case DISPENSE:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to start dispensing for this order ?");
                    AlertDialog alertDialog = builder.create();
                    builder.setPositiveButton("Start Dispensing", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(context, DispenserUnitActivity.class).putExtra("orderDetail", order));
                            alertDialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Wait", null);

                    alertDialog.show();

                    break;
                case PRINT:

                    Bundle bundle3 = new Bundle();
                    bundle3.putString("FuelDispensed", String.valueOf(order.getDelivered_data()));
                    //bundle.putString("CurrentUserCharge", String.valueOf(tvCurrentDispensedFuelChargeAmount.getText()));
                    bundle3.putString("CurrentUserCharge", String.valueOf(Float.parseFloat(String.valueOf(order.getQuantity())) * Float.parseFloat(String.valueOf(60))));
                    bundle3.putString("FuelRate", String.valueOf(60));
                    bundle3.putString("startTime", order.getCreated_datatime());
                    bundle3.putString("selectedAsset", order.getAsset_name());
                    bundle3.putString("selectedAssetName", order.getAsset_name());
                    bundle3.putBoolean("rfidEnabled", true);
                    bundle3.putBoolean("rfidByPass", false);
                    bundle3.putString("rfidTagId", "");
                    bundle3.putString("atgStart", order.getDelivered_data());
                    bundle3.putString("orderDate",order.getOrderdate());
                    bundle3.putParcelable("orderDetail", order);
                    bundle3.putBoolean("stausofprint",true);

//                    if (isFromFreshDispense) {
//                        bundle.putSerializable("vehicleDataForFresh", vehicleDataForFresh);
//                        bundle.putBoolean("isFromFreshDispense", true);
//                    } else {
//                        bundle.putBoolean("isFromFreshDispense", false);
//                    }
                    Click click=new Click() {
                        @Override
                        public void ClickK(boolean b) {

                        }
                    };
                    AddReadingsDialog addReadingsDialog = new AddReadingsDialog(click);
                    addReadingsDialog.setCancelable(false);
                    addReadingsDialog.setArguments(bundle3);
                    addReadingsDialog.show(getFragmentManager(), AddReadingsDialog.class.getSimpleName());

                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        dispenseLocalDatabaseHandler=new DispenseLocalDatabaseHandler(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complete_orders_layout, container, false);
        getCompletedOrdersList(SharedPref.getVehicleId());
        Toast.makeText(context, "completed orders", Toast.LENGTH_SHORT).show();
        completedOrdersRecycler = view.findViewById(R.id.ordersRecycler);
        completedOrdersRecycler.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void getCompletedOrdersList(String VehicleId) {
        if (Utils.isNetworkConnected(context)) {
            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
            apiInterface.getOrder(VehicleId, "5").enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {

                    if (response.isSuccessful()) {
                        try {
                            CompletedOrdersAdapter acceptedOrdersAdapter = new CompletedOrdersAdapter(context, response.body().getOrder(), actionTake);
                            completedOrdersRecycler.setAdapter(acceptedOrdersAdapter);
                            DividerItemDecoration itemDecor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                            completedOrdersRecycler.addItemDecoration(itemDecor);
                            completedOrdersRecycler.setItemAnimator(new DefaultItemAnimator());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    Toast.makeText(context, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    public interface ActionTake {
        public void onActionTaken(ActionPerformed actionPerformed, Order order);
    }

    public enum ActionPerformed {
        DISPENSE, NOTIFY, INVOICE, PRINT
    }

    @Override
    public void onResume() {
        super.onResume();
        if (context instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) context).setNavigationDrawerItemTitle("Completed Orders");
        }
        getCompletedOrdersList(SharedPref.getVehicleId());
    }
}

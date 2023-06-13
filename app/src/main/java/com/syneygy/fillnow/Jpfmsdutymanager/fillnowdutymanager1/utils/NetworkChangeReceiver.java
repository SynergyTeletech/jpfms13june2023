package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.constant.AppConstants;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PostOrderDelivered;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ChangePasswordbean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;
   // private OderComp oderComp;

   /* public NetworkChangeReceiver(OderComp oderComp) {
        this.oderComp = oderComp;

    }*/
    public NetworkChangeReceiver() {
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            Log.e("Laukendra", "Checking Internet...");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkTHings(context);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkTHings(Context context) {
        if (Utils.isNetworkConnected(context)) {
            Log.e("Laukendra", "Internet Available Now");
            dispenseLocalDatabaseHandler=new DispenseLocalDatabaseHandler(context);
            if (dispenseLocalDatabaseHandler.getAllOrderDispenseDataList().size()>0) {
                ArrayList<OrderDispenseLocalData> orderDispenseLocalDataList = (ArrayList<OrderDispenseLocalData>) dispenseLocalDatabaseHandler.getAllOrderDispenseDataList();
                for (OrderDispenseLocalData orderDispenseLocalData : orderDispenseLocalDataList) {
                    if(orderDispenseLocalData.getStatus().equalsIgnoreCase("0")) {
                        saveOrderDispenseOnServer(orderDispenseLocalData);
                    }
                }
            }

            if (SharedPref.getProgressList()!=null&&SharedPref.getProgressList().size()>0){
                for (int i=0;i<SharedPref.getProgressList().size();i++){
                    if (SharedPref.getProgressList().get(i).getStaus().equalsIgnoreCase("4")){
                        ApiInterface apiInterface=ApiClient.getClientCI().create(ApiInterface.class);
                        apiInterface.offlineOrderstatuschanged(SharedPref.getProgressList().get(i).getTransaction_id()).enqueue(new Callback<ChangePasswordbean>() {
                            @Override
                            public void onResponse(Call<ChangePasswordbean> call, Response<ChangePasswordbean> response) {

                            }

                            @Override
                            public void onFailure(Call<ChangePasswordbean> call, Throwable t) {

                            }
                        });
                    }

                }
            }
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    dispenseLocalDatabaseHandler=new DispenseLocalDatabaseHandler(context);
//                    if (dispenseLocalDatabaseHandler.getAllFreshDispenseDataList().size()>0) {
//                        ArrayList<OrderDispenseLocalData> orderDispenseLocalDataList = (ArrayList<OrderDispenseLocalData>) dispenseLocalDatabaseHandler.getAllFreshDispenseDataList();
//                        for (OrderDispenseLocalData orderDispenseLocalData : orderDispenseLocalDataList) {
//                            Log.e("LaukendraFresh", "Submitting Local Fresh Dispense");
//                            saveFreshDispenseOnServer(orderDispenseLocalData);
//                        }
//                    }else {
//                        Log.e("LaukendraFresh", "No Local Fresh Dispense available");
//                    }
//                }
//            },2000);

//            Handler handler2=new Handler();
//            handler2.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    dispenseLocalDatabaseHandler=new DispenseLocalDatabaseHandler(context);
//                    if (dispenseLocalDatabaseHandler.getAllGoLocalDispenseDataList().size()>0) {
//                        ArrayList<OrderDispenseLocalData> orderDispenseLocalDataList = (ArrayList<OrderDispenseLocalData>) dispenseLocalDatabaseHandler.getAllGoLocalDispenseDataList();
//                        for (OrderDispenseLocalData orderDispenseLocalData : orderDispenseLocalDataList) {
//                            Log.e("LaukendraGoLocal", "Submitting Go Local Fresh Dispense");
//                            saveFreshDispenseOnServer(orderDispenseLocalData);
//                        }
//                    }else {
//                        Log.e("LaukendraGoLocal", "No Local Fresh Dispense available");
//                    }
//
//                }
//            },4000);
        } else {
            Log.e("Laukendra", "Internet Connectivity Not Available");
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Do something after 5s = 5000ms
//                    checkTHings(context);
//                }
//            }, 15000);

        }

    }

    private boolean isOnline(Context context) {
        try {
            NetworkInfo.State wifiState = null;
            NetworkInfo.State mobileState = null;
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
                // phone network connect success
                return true;
            } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                // no network
                return false;
            } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
                // wift connect success
                return true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void saveOrderDispenseOnServer(OrderDispenseLocalData orderDispensedLocalTableData){
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.postOrderDelivered(getOrderDispenseHashMap(orderDispensedLocalTableData)).enqueue(new Callback<PostOrderDelivered>() {
            @Override
            public void onResponse(Call<PostOrderDelivered> call, Response<PostOrderDelivered> response) {

                if (response.isSuccessful() && response.body().getSucc()) {
                    dispenseLocalDatabaseHandler.updateStatus(orderDispensedLocalTableData.getTransaction_no());
                } else {

                }
            }

            @Override
            public void onFailure(Call<PostOrderDelivered> call, Throwable t) {



            }
        });
    }


    public void saveFreshDispenseOnServer(OrderDispenseLocalData orderDispensedLocalTableData){
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.postFreshDispenseOrderDelivered(getOrderDispenseHashMap(orderDispensedLocalTableData)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body().getSucc()) {
                    Log.e("Laukendra","LocalFreshDisEntryDelet "+"POST_DATA" + dispenseLocalDatabaseHandler.deleteFreshDispenseDataEntry(orderDispensedLocalTableData.getOrderLocalID()));
                } else {
                    Log.e("Laukendra","Fresh Dispense Not Submitted on server side"+"POST_DATA");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("LaukendraFailResponse","POST_DATA"+ t.getMessage());

            }
        });
    }

    public HashMap getOrderDispenseHashMap(OrderDispenseLocalData orderDispensedLocalTableData){

        HashMap<String, String> hashMap = new HashMap<>();
        Log.v("LOCAL","TIME"+orderDispensedLocalTableData.getFueling_start_time() +"end=="+orderDispensedLocalTableData.getFueling_stop_time());

        if(orderDispensedLocalTableData.getFueling_start_time()==null){
            hashMap.put("fueling_start_time","00.00");
        }else{
            hashMap.put("fueling_start_time", orderDispensedLocalTableData.getFueling_start_time());
        }


        if(orderDispensedLocalTableData.getFueling_stop_time() ==null){
            hashMap.put("fueling_stop_time", "00.00");

        }else{
            hashMap.put("fueling_stop_time", orderDispensedLocalTableData.getFueling_stop_time());
        }

         if(orderDispensedLocalTableData.getAssets_id()==null){
             hashMap.put("assets_id", "00.00");
         }else{
             hashMap.put("assets_id", orderDispensedLocalTableData.getAssets_id());
         }
         hashMap.put("dispense_qty", orderDispensedLocalTableData.getDispense_qty());
         hashMap.put("meter_reading", orderDispensedLocalTableData.getMeter_reading());
         hashMap.put("asset_other_reading", orderDispensedLocalTableData.getAsset_other_reading());
        if(orderDispensedLocalTableData.getAtg_tank_start_reading()==null){
            hashMap.put("atg_tank_start_reading", "00.00");
        }else{
            hashMap.put("atg_tank_start_reading", orderDispensedLocalTableData.getAtg_tank_start_reading());
        }
        if(orderDispensedLocalTableData.getAtg_tank_end_reading()==null){
            hashMap.put("atg_tank_end_reading", "00.00");
        }else {
            hashMap.put("atg_tank_end_reading", orderDispensedLocalTableData.getAtg_tank_end_reading());
        }


        hashMap.put("volume_totalizer", orderDispensedLocalTableData.getVolume_totalizer());
        Log.v("VOLUME_TOTALIZER","POST_DATA_OFLN"+ orderDispensedLocalTableData.getVolume_totalizer());
        hashMap.put("no_of_event_start_stop", orderDispensedLocalTableData.getNo_of_event_start_stop());
        hashMap.put("dispensed_in", orderDispensedLocalTableData.getDispensed_in());
        hashMap.put("rfid_status", orderDispensedLocalTableData.getRfid_status());
        hashMap.put("transaction_type", orderDispensedLocalTableData.getTransaction_type());
        hashMap.put("terminal_id", orderDispensedLocalTableData.getTerminal_id());
        hashMap.put("batch_no", orderDispensedLocalTableData.getBatch_no());
        hashMap.put("latitude", orderDispensedLocalTableData.getLatitude());
        hashMap.put("longitude", orderDispensedLocalTableData.getLongitude());
        hashMap.put("location_reading_1", orderDispensedLocalTableData.getLocation_reading_1());
        hashMap.put("location_reading_2", orderDispensedLocalTableData.getLocation_reading_2());
        hashMap.put("gps_status", orderDispensedLocalTableData.getLocation_reading_2());
        hashMap.put("dcv_status", orderDispensedLocalTableData.getDcv_status());
        hashMap.put("flag", orderDispensedLocalTableData.getFlag());
        hashMap.put("transaction_no", orderDispensedLocalTableData.getTransaction_no());
        hashMap.put("unit_price", orderDispensedLocalTableData.getUnit_price());
        hashMap.put("total_qty", orderDispensedLocalTableData.getTotal_qty());
        hashMap.put(AppConstants.Vehicle_id, orderDispensedLocalTableData.getVehicle_id());
        hashMap.put(AppConstants.transaction_id, orderDispensedLocalTableData.getTransaction_id());
        hashMap.put("order_id", orderDispensedLocalTableData.getOrder_id()==null?new Date().getTime()+"":(orderDispensedLocalTableData.getOrder_id().isEmpty()?new Date().getTime()+"":orderDispensedLocalTableData.getOrder_id()));
        hashMap.put("duty_id", orderDispensedLocalTableData.getDuty_id());
        hashMap.put("location_id", orderDispensedLocalTableData.getLocation_id());
        hashMap.put("footer_message", orderDispensedLocalTableData.getFooter_message());
        hashMap.put("gst_percentage", orderDispensedLocalTableData.getGst_percentage());
        hashMap.put("total_amount", orderDispensedLocalTableData.getTotal_amount());
        hashMap.put("asset_other_reading_2", orderDispensedLocalTableData.getMeter_reading());
        hashMap.put("asset_mriv", orderDispensedLocalTableData.getAsset_other_reading());
        hashMap.put("asset_remark", orderDispensedLocalTableData.getAsset_other_reading_2());
        hashMap.put("asset_intial_level",orderDispensedLocalTableData.getElock_start_reading());
        hashMap.put("asset_final_level",orderDispensedLocalTableData.getElock_end_reading());
        Log.v("READING","local"+"ODOMETER=="+orderDispensedLocalTableData.getMeter_reading()+"MRIV=="+orderDispensedLocalTableData.getAsset_other_reading()+"remark="+ orderDispensedLocalTableData.getAsset_other_reading_2());
        printDispenseParams(orderDispensedLocalTableData);

        return hashMap;
    }

    public void printDispenseParams(OrderDispenseLocalData dataToPrint){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fueling_start_time", dataToPrint.getFueling_start_time());
            jsonObject.put("fueling_stop_time", dataToPrint.getFueling_stop_time());
            jsonObject.put("assets_id", dataToPrint.getAssets_id());
            jsonObject.put("dispense_qty", dataToPrint.getDispense_qty());
            jsonObject.put("meter_reading", dataToPrint.getMeter_reading());

            jsonObject.put("asset_other_reading_2",  dataToPrint.getMeter_reading());
            jsonObject.put("asset_mriv",  dataToPrint.getAsset_reading2());
            jsonObject.put("asset_remark",  dataToPrint.getAsset_remark());
            jsonObject.put("asset_other_reading", dataToPrint.getAsset_other_reading());
            jsonObject.put("atg_tank_start_reading", dataToPrint.getAtg_tank_start_reading());
            jsonObject.put("atg_tank_end_reading", dataToPrint.getAtg_tank_end_reading());
            jsonObject.put("volume_totalizer", dataToPrint.getVolume_totalizer());
            jsonObject.put("no_of_event_start_stop", dataToPrint.getNo_of_event_start_stop());
            jsonObject.put("dispensed_in", dataToPrint.getDispensed_in());
            jsonObject.put("rfid_status", dataToPrint.getRfid_status());
            jsonObject.put("transaction_type", dataToPrint.getTransaction_type());
            jsonObject.put("terminal_id", dataToPrint.getTerminal_id());
            jsonObject.put("batch_no", dataToPrint.getBatch_no());
            jsonObject.put("latitude", dataToPrint.getLatitude());
            jsonObject.put("longitude", dataToPrint.getLongitude());
            jsonObject.put("location_reading_1", dataToPrint.getLocation_reading_1());
            jsonObject.put("location_reading_2", dataToPrint.getLocation_reading_2());
            jsonObject.put("gps_status", dataToPrint.getGps_status());
            jsonObject.put("dcv_status", dataToPrint.getDcv_status());
            jsonObject.put("flag", dataToPrint.getFlag());
            jsonObject.put("transaction_no", dataToPrint.getTransaction_no());
            jsonObject.put("unit_price", dataToPrint.getUnit_price());
            jsonObject.put("vehicle_id", dataToPrint.getVehicle_id());
            jsonObject.put("transaction_id", dataToPrint.getTransaction_id());
            jsonObject.put("total_qty", dataToPrint.getTotal_qty());
            jsonObject.put("order_id", dataToPrint.getOrder_id());
            jsonObject.put("duty_id", dataToPrint.getDuty_id());
            jsonObject.put("location_id", dataToPrint.getLocation_id());
            jsonObject.put("footer_message", dataToPrint.getFooter_message());
            jsonObject.put("gst_percentage", dataToPrint.getGst_percentage());
            jsonObject.put("total_amount", dataToPrint.getTotal_amount());
            Log.e("LocalOrderParams", jsonObject.toString());
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

}

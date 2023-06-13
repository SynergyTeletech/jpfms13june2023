package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.DispenserUnitK;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LocationVehicleDatum;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PostOrderDelivered;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PrintData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.NetworkStatusClass;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.printer.PrintPic;
import com.syneygy.fillnow.Jpfmsdutymanager.printer.PrinterCommands;
import com.syneygy.fillnow.Jpfmsdutymanager.printer.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintReciveDialog extends DialogFragment implements View.OnClickListener {

        private String Status = "";
        private Context context;
        private String TAG = "OrderCompleteTAg";
        //    EditText message;
        TextView btnBill, btnDonate;
        private static final int REQUEST_CODE = 5454;
        private String dispensedQuantity = "0.00";
        private String Rate = "0.00";
        private String Amount = "0.00";
        private String batch = "4353";

        private static BluetoothSocket btsocket;
        private static OutputStream outputStream;
        private TextView quantity;
        private TextView rate;
        private TextView amount, btnPrintReceipt;
        private EditText meterReading, assetReading1, assetReading2;
        private String commandsToPrint;
        private AppDatabase db;
        private String startTime, OrderDataTime;
        private DeliveryInProgress orderDeliveryInProgress;
        private String selectedAsset, selectedAssetName = "";
        private String locationName = "";
        private int atgStart;
        private int atg_end;
        private int dcv_status;
        private PrintData dataToPrint;
        private Boolean isFreshDispense = false;

        private String mOrderDate = "";

        private LocationVehicleDatum vehicleDataForFresh;
        private boolean isFromFreshDispense,isRFidEnabled,isRFidByPass;
        private String rfIdTagId="";

        private DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;

        public PrintReciveDialog() { }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            this.context = context;
            db = BrancoApp.getDb();
            dispenseLocalDatabaseHandler=new DispenseLocalDatabaseHandler(context);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.dialog_note_readings, container, false);
            quantity = v.findViewById(R.id.quantity);
            rate = v.findViewById(R.id.rate);
            amount = v.findViewById(R.id.et_amount);
            meterReading = v.findViewById(R.id.meterReading);
            assetReading1 = v.findViewById(R.id.assetReading1);
            assetReading2 = v.findViewById(R.id.assetReading2);
            btnPrintReceipt = v.findViewById(R.id.tv_btn_print);


            Bundle data = getArguments();
            if (data != null) {
                try {
                    dispensedQuantity = data.getString("FuelDispensed");
                    Amount = data.getString("CurrentUserCharge");
                    Rate = data.getString("FuelRate");
                    startTime = data.getString("startTime");
                    selectedAsset = data.getString("selectedAsset");
                    selectedAssetName = data.getString("selectedAssetName");
                    locationName = data.getString("location_name");
                    mOrderDate = data.getString("orderDate");
                    rfIdTagId = data.getString("rfidTagId");
                    isRFidEnabled=data.getBoolean("rfidEnabled");
                    isRFidByPass=data.getBoolean("rfidByPass");
                    isFromFreshDispense = data.getBoolean("isFromFreshDispense");
                    orderDeliveryInProgress = data.getParcelable("orderDetail");

                    Log.d("New Volume",dispensedQuantity);
                    Log.d("New Amount",Amount);
                    if (Double.parseDouble(SharedPref.getTotalizerReadingDiff())>0){
                        double dispensedQuantityF=Double.parseDouble(dispensedQuantity)+Double.parseDouble(SharedPref.getTotalizerReadingDiff());
                        Double AmountF=dispensedQuantityF*Double.parseDouble(Rate);
                        dispensedQuantity=String.format("%.2f",dispensedQuantityF);
                        Amount=String.format("%.2f",AmountF);
                        Log.d("New Volume 2",dispensedQuantity);
                        Log.d("New Amount 2",Amount);
                    }

                    Log.d("mOrderDate",mOrderDate+"");

                    if (isFromFreshDispense) {
                        vehicleDataForFresh = (LocationVehicleDatum) data.getSerializable("vehicleDataForFresh");
                    }

                    quantity.setText(String.format("dispensedQuantity:%s Ltr.", dispensedQuantity!=null?dispensedQuantity:"N/A"));
                    amount.setText(String.format("Amount :%s Rs.", Amount!=null?Amount:"N/A"));
                    rate.setText(String.format("Rate %s Rs./Ltr", Rate!=null?Rate:"N/A"));

//                String da = data.containsKey("atgStart") ? data.getString("atgStart") : "0";
//                // Following Lines Added by Laukendra on 02-12-19
//                if (da!=null&&!da.isEmpty())
//                    atgStart = Integer.parseInt(da);
//                else atgStart=0;
                    // Above Lines Added by Laukendra on 02-12-19
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            btnBill = v.findViewById(R.id.saveReadings);
            v.findViewById(R.id.saveReadings).setOnClickListener(this);
            v.findViewById(R.id.backBtn).setOnClickListener(this);

            return v;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }


        @Override
        public void onResume() {
            super.onResume();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            // The absolute width of the available display size in pixels.
            int displayWidth = displayMetrics.widthPixels;
            // The absolute height of the available display size in pixels.
            int displayHeight = displayMetrics.heightPixels;
            // Initialize a new window manager layout parameters
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            // Copy the alert dialog window attributes to new layout parameter instance
            layoutParams.copyFrom(getDialog().getWindow().getAttributes());
            // Set the alert dialog window width and height
            // Set alert dialog width equal to screen width 90%
            int dialogWindowWidth = (int) (displayWidth * 0.50f);
            //Set alert dialog height equal to screen height 90%
            int dialogWindowHeight = (int) (displayHeight * 0.85f);
            // Set the width and height for the layout parameters
            // This will bet the width and height of alert dialog
            layoutParams.width = dialogWindowWidth;
            layoutParams.height = dialogWindowHeight;
            // Apply the newly created layout parameters to the alert dialog window
            getDialog().getWindow().setAttributes(layoutParams);
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.saveReadings:
                    if (meterReading.getText().length() == 0 || assetReading1.getText().length() == 0 || assetReading2.getText().length() == 0) {
                        Toast.makeText(context, "Please Enter Readings First", Toast.LENGTH_LONG).show();
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to fill another asset, Or Complete Delivery?");
                    builder.setNegativeButton("Complete Delivery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (isFromFreshDispense) {
                                markFreshDispenseTransactionCompleted("U");
                            } else {
                                markOrderDispenseTransactionCompleted("U");
                            }

                        }
                    });
                    builder.setPositiveButton("Fill Another Asset ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (isFromFreshDispense) {
                                markFreshDispenseTransactionCompleted("I");
                            } else {
                                markOrderDispenseTransactionCompleted("I");
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;

                case R.id.backBtn:
                    dismiss();
                    break;
            }
        }

        private void clearSALE() {
            if (context instanceof DispenserUnitK) {
                ((DispenserUnitK) context).ClearSale();
            }

            if (context instanceof DispenserUnitK) {
                ((DispenserUnitK) context).ClearSale();
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
//        try {
//            if (btsocket != null) {
//                outputStream.close();
//                btsocket.close();
//                btsocket = null;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        }

        private void markOrderDispenseTransactionCompleted(String flag) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
                String strDate = simpleDateFormat.format(new Date());
                dataToPrint = new PrintData();
                dataToPrint.setTransaction_type("Order Dispense");
                dataToPrint.setAmount(String.format("%.2f",Float.parseFloat(Amount)));
                dataToPrint.setLocationName(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                dataToPrint.setCustomer_name(orderDeliveryInProgress.getOrder().get(0).getCustomer_name());
                dataToPrint.setAssets_id(selectedAsset);
                dataToPrint.setAssets_name(selectedAssetName);
                dataToPrint.setFueling_start_time(startTime == null ? "NULL" : startTime);
                dataToPrint.setFueling_stop_time(String.valueOf(Calendar.getInstance().getTime()));
                dataToPrint.setDispense_qty(String.format("%.2f",Float.parseFloat(dispensedQuantity)));
                dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
                dataToPrint.setAsset_other_reading(String.valueOf(assetReading1.getText()));
                dataToPrint.setAsset_other_reading2(String.valueOf(assetReading2.getText()));
                dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
                dataToPrint.setAtg_tank_end_reading(String.valueOf(atg_end));
                dataToPrint.setVolume_totalizer("703");
                dataToPrint.setNo_of_event_start_stop("3");
                dataToPrint.setDispensed_in(selectedAssetName);
                dataToPrint.setRfid_status(rfIdTagId.isEmpty()?"N/A":rfIdTagId);
                dataToPrint.setTerminal_id(SharedPref.getTerminalID());
                dataToPrint.setBatch_no(batch);
                dataToPrint.setLatitude(orderDeliveryInProgress.getProgress().getCurrentLat());
                dataToPrint.setLongitude(orderDeliveryInProgress.getProgress().getCurrentLong());
                dataToPrint.setLocation_reading_1(orderDeliveryInProgress.getProgress().getCurrentLat());
                dataToPrint.setLocation_reading_2(orderDeliveryInProgress.getProgress().getCurrentLong());
                dataToPrint.setGps_status((orderDeliveryInProgress.getProgress().getLocationBypass() ? "Bypassed" : "OK"));
                dataToPrint.setDcv_status(String.valueOf(dcv_status));
                dataToPrint.setFlag(flag);
                dataToPrint.setTransaction_no(AddReadingsDialog.dataToPrint.getTransaction_no());
                dataToPrint.setUnit_price(String.format("%.2f",Float.parseFloat(Rate)));
                dataToPrint.setVehicle_id(com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref.getVehicleId());
                dataToPrint.setTransaction_id(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                dataToPrint.setOrder_id(orderDeliveryInProgress.getOrder().get(0).getLead_number());
                dataToPrint.setOrder_data_time(orderDeliveryInProgress.getOrder().get(0).getCreatedDatatime());
                dataToPrint.setTotal_qty(orderDeliveryInProgress.getOrder().get(0).getQty());
                dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
                dataToPrint.setLocation_id(orderDeliveryInProgress.getOrder().get(0).getLocationId());
                dataToPrint.setLocationName(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty()?" Thank You ":SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
                dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
                dataToPrint.setGST("0"); //Need GST Value Which API will provide it
                dataToPrint.setTotal_amount(String.format("%.2f",Float.parseFloat(dataToPrint.getAmount())+Float.parseFloat(dataToPrint.getAmount())*Float.parseFloat(dataToPrint.getGST())/100));


                printBill(dataToPrint);

                SharedPref.setIsFuelingStarted(false);
                SharedPref.setTotalizerReadingDiff("0");

                printDispenseParams(dataToPrint);

                if (NetworkStatusClass.isNetworkStatusAvailable(context)) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                    apiInterface.postOrderDelivered(getHashMap(dataToPrint)).enqueue(new Callback<PostOrderDelivered>() {
                        @Override
                        public void onResponse(Call<PostOrderDelivered> call, Response<PostOrderDelivered> response) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (response.isSuccessful() && response.body().getSucc()) {
                                showResponseInDialog(context,response.body().getPublicMsg(),true,false);
                            } else {
                                showResponseInDialog(context,response.body().getPublicMsg(),false,true);
                                Log.e("PostUnSuccResponse", response.body().getSucc()+"");
                            }
                        }

                        @Override
                        public void onFailure(Call<PostOrderDelivered> call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            try {
                                showResponseInDialog(context,"Server Error. Please try again...",false,true);
                                Log.e("PostFailure", t.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    //save Order Data in Local
                    Toast.makeText(context, "Internet Not Available. Data saved in Local", Toast.LENGTH_LONG).show();
                    saveOrderDispenseDataInSQLiteLocal(dataToPrint);
                    clearSALE();
                    dismiss();
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void markFreshDispenseTransactionCompleted(String flag) {
            try {
                dataToPrint = new PrintData();
                dataToPrint.setTransaction_type("Instant Dispense");
                dataToPrint.setAmount(String.format("%.2f",Float.parseFloat(Amount)));
                dataToPrint.setAssets_id(selectedAsset);
                dataToPrint.setAssets_name(selectedAssetName);
                dataToPrint.setFueling_start_time(startTime == null ? "NULL" : startTime);
                dataToPrint.setFueling_stop_time(String.valueOf(Calendar.getInstance().getTime()));
                dataToPrint.setDispense_qty(String.format("%.2f",Float.parseFloat(dispensedQuantity)));
                dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
                dataToPrint.setAsset_other_reading(String.valueOf(assetReading1.getText()));
                dataToPrint.setAsset_other_reading2(String.valueOf(assetReading2.getText()));//mriv
                dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
                dataToPrint.setAtg_tank_end_reading(String.valueOf(atg_end));
                dataToPrint.setVolume_totalizer("703");
                dataToPrint.setNo_of_event_start_stop("1");
                dataToPrint.setDispensed_in(selectedAssetName);
                dataToPrint.setTerminal_id(SharedPref.getTerminalID());
                dataToPrint.setBatch_no(batch);
                dataToPrint.setLatitude(orderDeliveryInProgress.getProgress().getCurrentLat());
                dataToPrint.setLongitude(orderDeliveryInProgress.getProgress().getCurrentLong());
                dataToPrint.setLocation_reading_1(orderDeliveryInProgress.getProgress().getCurrentLat());
                dataToPrint.setLocation_reading_2(orderDeliveryInProgress.getProgress().getCurrentLong());
                dataToPrint.setRfid_status(rfIdTagId.isEmpty()?"N/A":rfIdTagId);
                dataToPrint.setGps_status(String.valueOf(orderDeliveryInProgress.getProgress().getLocationBypass()));
                dataToPrint.setDcv_status(String.valueOf(dcv_status));
                dataToPrint.setFlag(flag);
                dataToPrint.setUnit_price(orderDeliveryInProgress.getProgress().getFuelPrice());
                dataToPrint.setUnit_price(Rate);
                dataToPrint.setVehicle_id(com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref.getVehicleId());
                dataToPrint.setTransaction_no(AddReadingsDialog.dataToPrint.getTransaction_no());
                dataToPrint.setTransaction_id(SharedPref.getTerminalID() + orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                dataToPrint.setOrder_id(SharedPref.getTerminalID() + orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                dataToPrint.setTotal_qty(orderDeliveryInProgress.getOrder().get(0).getQty());
                dataToPrint.setLocation_id(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
                dataToPrint.setTotal_qty(orderDeliveryInProgress.getOrder().get(0).getQty());
                dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
                dataToPrint.setLocationName(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                dataToPrint.setCustomer_name(orderDeliveryInProgress.getOrder().get(0).getCustomer_name());
                dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty()?" Thank You ":SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
                dataToPrint.setOrder_data_time(mOrderDate);
                dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
                dataToPrint.setGST("0"); //Need GST Value Which API will provide it
                dataToPrint.setTotal_amount(String.format("%.2f",Float.parseFloat(dataToPrint.getAmount())+Float.parseFloat(dataToPrint.getAmount())*Float.parseFloat(dataToPrint.getGST())/100));

                printBill(dataToPrint);

                SharedPref.setIsFuelingStarted(false);
                SharedPref.setTotalizerReadingDiff("0");

                printDispenseParams(dataToPrint);

                if (NetworkStatusClass.isNetworkStatusAvailable(context)) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                    apiInterface.postFreshDispenseOrderDelivered(getHashMap(dataToPrint)).enqueue(new Callback<LoginResponse>() {

                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Response response1=response;
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("PostOnResponse", response.body().getSucc()+"");
                            if (response.isSuccessful() && response.body().getSucc()) {
                                Log.e("PostSuccResponse", response.body().getSucc()+"");
                                showResponseInDialog(context,"Order Successfully updated",true,true);

                            } else {
                                showResponseInDialog(context,"Something went wrong. Please try again...",false,true);
                                Log.e("PostUnSuccResponse", response.body().getSucc()+"");
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            try {
                                showResponseInDialog(context,"Server Error. Please try again...",false,true);
                                Log.e("PostonFailure", t.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    saveFreshDispenseDataInSQLiteLocal(dataToPrint);
                    clearSALE();
                    dismiss();
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void showResponseInDialog(Context context, String message, boolean isSuccess, boolean isFreshDispense){

            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle(message);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

            if (alertDialog!=null)
                alertDialog.dismiss();

            if (isSuccess){
                clearSALE();
            }

            if (dataToPrint.getFlag().equalsIgnoreCase("I")) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().finish();
                        if (!isFreshDispense) {
                            Intent intent = new Intent(getActivity(), DispenserUnitK.class);
                            Order order = new Order();
                            order.setTransaction_id(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                            order.setQuantity(orderDeliveryInProgress.getOrder().get(0).getQty());
                            order.setLocation_id(orderDeliveryInProgress.getOrder().get(0).getLocationId());
                            order.setLocation_name(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                            intent.putExtra("orderDetail", order);
                            intent.putExtra("isFromFreshDispense", false);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getActivity(), DispenserUnitK.class);
                            orderDeliveryInProgress.getProgress().setDeliveredData(dispensedQuantity);
                            intent.putExtra("freshOrder", orderDeliveryInProgress);
                            intent.putExtra("isFromFreshDispense", true);
                            startActivity(intent);
                        }

                    }
                },1000);

            } else {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        }

        public void printDispenseParams(PrintData dataToPrint){
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fueling_start_time", dataToPrint.getFueling_start_time());
                jsonObject.put("fueling_stop_time", dataToPrint.getFueling_stop_time());
                jsonObject.put("assets_id", dataToPrint.getAssets_id());
                jsonObject.put("dispense_qty", dataToPrint.getDispense_qty());
                jsonObject.put("meter_reading", dataToPrint.getMeter_reading());
                jsonObject.put("asset_other_reading", dataToPrint.getAsset_other_reading());
                jsonObject.put("asset_other_reading_2", dataToPrint.getAsset_other_reading2());
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
                jsonObject.put("gst_percentage", dataToPrint.getGST());
                jsonObject.put("total_amount", dataToPrint.getTotal_amount());
                Log.d("PostOrderParams", jsonObject.toString());
                System.out.println("PostOrderParams:"+jsonObject.toString());
            }catch (JSONException je){
                je.printStackTrace();
            }
        }

        public HashMap getHashMap(PrintData dataToPrint){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("fueling_start_time", dataToPrint.getFueling_start_time());
            hashMap.put("fueling_stop_time", dataToPrint.getFueling_stop_time());
            hashMap.put("assets_id", dataToPrint.getAssets_id());
            hashMap.put("dispense_qty", dataToPrint.getDispense_qty());
            hashMap.put("meter_reading", dataToPrint.getMeter_reading());
            hashMap.put("asset_other_reading", dataToPrint.getAsset_other_reading());
            hashMap.put("asset_other_reading_2", dataToPrint.getAsset_other_reading2());
            hashMap.put("atg_tank_start_reading", dataToPrint.getAtg_tank_start_reading());
            hashMap.put("atg_tank_end_reading", dataToPrint.getAtg_tank_end_reading());
            hashMap.put("volume_totalizer", dataToPrint.getVolume_totalizer());
            hashMap.put("no_of_event_start_stop", dataToPrint.getNo_of_event_start_stop());
            hashMap.put("dispensed_in", dataToPrint.getDispensed_in());
            hashMap.put("rfid_status", dataToPrint.getRfid_status());
            hashMap.put("transaction_type", dataToPrint.getTransaction_type());
            hashMap.put("terminal_id", dataToPrint.getTerminal_id());
            hashMap.put("batch_no", dataToPrint.getBatch_no());
            hashMap.put("latitude", dataToPrint.getLatitude());
            hashMap.put("longitude", dataToPrint.getLongitude());
            hashMap.put("location_reading_1", dataToPrint.getLocation_reading_1());
            hashMap.put("location_reading_2", dataToPrint.getLocation_reading_2());
            hashMap.put("gps_status", dataToPrint.getGps_status());
            hashMap.put("dcv_status", dataToPrint.getDcv_status());
            hashMap.put("flag", dataToPrint.getFlag());
            hashMap.put("transaction_no", dataToPrint.getTransaction_no());
            hashMap.put("unit_price", dataToPrint.getUnit_price());
            hashMap.put("vehicle_id", dataToPrint.getVehicle_id());
            hashMap.put("transaction_id", dataToPrint.getTransaction_id());
            hashMap.put("total_qty", dataToPrint.getTotal_qty());
            hashMap.put("order_id", dataToPrint.getOrder_id()==null?new Date().getTime()+"":(dataToPrint.getOrder_id().isEmpty()?new Date().getTime()+"":dataToPrint.getOrder_id()));
            hashMap.put("duty_id", dataToPrint.getDuty_id());
            hashMap.put("location_id", dataToPrint.getLocation_id());
            hashMap.put("footer_message", dataToPrint.getFooter_message());
            hashMap.put("gst_percentage", dataToPrint.getGST());
            hashMap.put("total_amount", dataToPrint.getTotal_amount());
            return hashMap;
        }

        public void saveOrderDispenseDataInSQLiteLocal(PrintData printData) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OrderDispenseLocalData orderModel = new OrderDispenseLocalData();
                            orderModel.setTransaction_id(printData.getTransaction_id());
                            orderModel.setTransaction_no(printData.getTransaction_no());
                            orderModel.setVehicle_id(printData.getVehicle_id());
                            orderModel.setAssets_id(printData.getAssets_id());
                            orderModel.setMeter_reading(printData.getMeter_reading());
                            orderModel.setAsset_other_reading(printData.getAsset_other_reading());
                            orderModel.setAsset_other_reading_2(printData.getAsset_other_reading2());
                            orderModel.setAtg_tank_start_reading(printData.getAtg_tank_start_reading());
                            orderModel.setAtg_tank_end_reading(printData.getAtg_tank_end_reading());
                            orderModel.setBatch_no(printData.getBatch_no());
                            orderModel.setTerminal_id(printData.getTerminal_id());
                            orderModel.setDispense_qty(printData.getDispense_qty());
                            orderModel.setUnit_price(printData.getUnit_price());
                            orderModel.setFlag(printData.getFlag());
                            orderModel.setDispensed_in(printData.getDispensed_in());
                            orderModel.setDcv_status(printData.getDcv_status());
                            orderModel.setGps_status(printData.getGps_status());
                            orderModel.setFueling_start_time(printData.getFueling_start_time());
                            orderModel.setFueling_stop_time(printData.getFueling_stop_time());
                            orderModel.setLatitude(printData.getLatitude());
                            orderModel.setLongitude(printData.getLongitude());
                            orderModel.setLocation_reading_1(printData.getLocation_reading_1());
                            orderModel.setLocation_reading_2(printData.getLocation_reading_2());
                            orderModel.setVolume_totalizer(printData.getVolume_totalizer());
                            orderModel.setNo_of_event_start_stop(printData.getNo_of_event_start_stop());
                            orderModel.setRfid_status(printData.getRfid_status());
                            orderModel.setTransaction_type(printData.getTransaction_type());
                            orderModel.setTotal_qty(printData.getTotal_qty());
                            orderModel.setOrder_id(printData.getOrder_id());
                            orderModel.setDuty_id(printData.getDuty_id());
                            orderModel.setLocation_id(printData.getLocation_id());
                            orderModel.setFooter_message(printData.getFooter_message());
                            orderModel.setGst_percentage(printData.getGST());
                            orderModel.setTotal_amount(printData.getTotal_amount());
                            Log.d("Locally","Pre Data saved Locally with TransactionId: " + printData.getTransaction_id());
                            dispenseLocalDatabaseHandler.addOrderDispenseData(orderModel);
                            Log.d("Locally","SavedOrderDataListSize" + dispenseLocalDatabaseHandler.getAllOrderDispenseDataList().size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void saveFreshDispenseDataInSQLiteLocal(PrintData printData) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OrderDispenseLocalData orderModel = new OrderDispenseLocalData();
                            orderModel.setTransaction_id(printData.getTransaction_id());
                            orderModel.setTransaction_no(printData.getTransaction_no());
                            orderModel.setVehicle_id(printData.getVehicle_id());
                            orderModel.setAssets_id(printData.getAssets_id());
                            orderModel.setMeter_reading(printData.getMeter_reading());
                            orderModel.setAsset_other_reading(printData.getAsset_other_reading());
                            orderModel.setAsset_other_reading_2(printData.getAsset_other_reading2());
                            orderModel.setAtg_tank_start_reading(printData.getAtg_tank_start_reading());
                            orderModel.setAtg_tank_end_reading(printData.getAtg_tank_end_reading());
                            orderModel.setBatch_no(printData.getBatch_no());
                            orderModel.setTerminal_id(printData.getTerminal_id());
                            orderModel.setDispense_qty(printData.getDispense_qty());
                            orderModel.setUnit_price(printData.getUnit_price());
                            orderModel.setFlag(printData.getFlag());
                            orderModel.setDispensed_in(printData.getDispensed_in());
                            orderModel.setDcv_status(printData.getDcv_status());
                            orderModel.setGps_status(printData.getGps_status());
                            orderModel.setFueling_start_time(printData.getFueling_start_time());
                            orderModel.setFueling_stop_time(printData.getFueling_stop_time());
                            orderModel.setLatitude(printData.getLatitude());
                            orderModel.setLongitude(printData.getLongitude());
                            orderModel.setLocation_reading_1(printData.getLocation_reading_1());
                            orderModel.setLocation_reading_2(printData.getLocation_reading_2());
                            orderModel.setVolume_totalizer(printData.getVolume_totalizer());
                            orderModel.setNo_of_event_start_stop(printData.getNo_of_event_start_stop());
                            orderModel.setRfid_status(printData.getRfid_status());
                            orderModel.setTransaction_type(printData.getTransaction_type());
                            orderModel.setTotal_qty(printData.getTotal_qty());
                            orderModel.setOrder_id(printData.getOrder_id());
                            orderModel.setDuty_id(printData.getDuty_id());
                            orderModel.setLocation_id(printData.getLocation_id());
                            orderModel.setFooter_message(printData.getFooter_message());
                            orderModel.setGst_percentage(printData.getGST());
                            orderModel.setTotal_amount(printData.getTotal_amount());
                            dispenseLocalDatabaseHandler.addFreshDispenseData(orderModel);
                            System.out.print("Fresh Data saved Locally with TransactionId: " + printData.getTransaction_id());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void printBill(PrintData printData) {

            if (printData != null) {
                if (NavigationDrawerActivity.btsocket == null) {
                    Toast.makeText(context, "Please Connect Printer to print Bill", Toast.LENGTH_SHORT).show();
                } else {
                    //Added/Updated following lines by Laukendra on 15-11-19
                    int gstPercentage = 0;
                    if (SharedPref.getLoginResponse().getVehicle_data().get(0).getProductName().equalsIgnoreCase("DIESEL")) {
                        gstPercentage = 0; //custom value
                    } else {
                        gstPercentage = 12;
                    }
                    double gstAmount = Double.parseDouble(printData.getAmount()) * gstPercentage / 100;
                    printData.setGST(gstPercentage+"");
                    printData.setTotal_amount(String.valueOf(Float.parseFloat(printData.getAmount())+gstAmount));
                    printTransactionBill(printData,gstPercentage, gstAmount);
                    //Added/Updated above lines by Laukendra on 15-11-19
                }
            }
        }

        protected void printTransactionBill(PrintData printData,int gstPercentage, double gstAmount) {
            OutputStream opstream = null;
            try {
                opstream = NavigationDrawerActivity.btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = NavigationDrawerActivity.btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
                outputStream.write(printformat);
                printPhoto(R.drawable.fill_logo);
                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                if (isFromFreshDispense)
                    printCustom("INSTANT DISPENSE", 1, 1);
                else printCustom("DISPENSE", 1, 1);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Location Name: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getBranchName(), 0, 0);
                printCustom("Address: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getAddress(), 0, 0);
                printCustom("Mobile No: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getContactNum(), 0, 0);
                printCustom("Person Name: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getContactPerson(), 0, 0);
                printNewLine();
                printCustom("FUEL", 1, 1);
                printCustom("Fuel Selected: " + SharedPref.getLoginResponse().getDelivery_inProgress().get(0).getOrder().get(0).getFuel(), 0, 0);
                printCustom("Quantity: " + printData.getDispense_qty(), 0, 0);
                printCustom("Rate: " + printData.getUnit_price(), 0, 0);
                printCustom("Amount: " + printData.getAmount(), 0, 0);
                printNewLine();

                printCustom("LUBRICANTS", 1, 1);
                printCustom("Lubricants Selected: " + "NA", 0, 0);
                printCustom("Quantity: " + 0, 0, 0);
                printCustom("Amount: " +0, 0, 0);
                printNewLine();

                printCustom("DELIVERY DETAILS", 1, 1);

                printCustom("Date " + SharedPref.getLoginResponse().getDelivery_inProgress().get(0).getOrder().get(0).getOrderDate(), 0, 0);
                printCustom("Selected Delivery Slot: " +SharedPref.getLoginResponse().getDelivery_inProgress().get(0).getOrder().get(0).getCreatedDatatime(), 0, 0);
                printNewLine();


                printCustom("AMOUNT PAYABLE", 1, 1);
                printCustom("Fuel: " + printData.getAmount(), 0, 0);
                printCustom("Lubricants(s): " + 0, 0, 0);
                printCustom("Location Discount: " +SharedPref.getLoginResponse().getDelivery_inProgress().get(0).getProgress().getDiscount() , 0, 0);
                printCustom("Happy Hour Discount: " +0 , 0, 0);
                printCustom("Taxable Amount: " +printData.getAmount() , 0, 0);
                printCustom("GST: " +printData.getGST() , 0, 0);
                printCustom("Delivery (Inclusive GST): " +0, 0, 0);
              //  printCustom("Round Off(+/-): " +printData., 0, 0);
                printCustom(" Total Amount: " + printData.getTotal_amount(), 0, 0);
                printNewLine();

//                printNewLine();
//                printCustom("Franchise Name: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getName(), 0, 0);
//                printCustom("GSTIN: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getGstNo(), 0, 0);
//                printCustom("Refueller No: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getRegNo(), 0, 0);
//
//                printNewLine();
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("ORDER DETAILS", 1, 1);
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printNewLine();
//
//                try {
//                    printCustom("Order Date: " + printData.getOrder_data_time(), 0, 0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                printCustom("Order ID: " + printData.getOrder_id(), 0, 0);
//                printCustom("Customer Name: " + printData.getCustomer_name(), 0, 0);
//                printCustom("Location Name: " + printData.getLocationName(), 0, 0);
//
//                printNewLine();
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("DELIVERY DETAILS", 1, 1);
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printNewLine();
//
//                printCustom("Latitude: " + printData.getLatitude(), 0, 0);
//                printCustom("Longitude: " + printData.getLongitude(), 0, 0);
//                printCustom("GPS Status: " + printData.getGps_status(), 0, 0);
//                printCustom("Terminal ID: " + printData.getTerminal_id(), 0, 0);
//                printCustom("Batch no: " + printData.getBatch_no(), 0, 0);
//
//                printNewLine();
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("PRODUCT: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getProductName().toUpperCase(), 1, 1);
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printNewLine();
//
//                printCustom("Asset Name: " + printData.getAssets_name(), 0, 0);
//                printCustom("RFID Status: " + printData.getRfid_status(), 0, 0);
//
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("Start Fueling Time: " + printData.getFueling_start_time(), 0, 0);
//                printCustom("End Fueling Time: " + printData.getFueling_stop_time(), 0, 0);
//
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("Txn No: " + printData.getTransaction_no(), 1, 0);
//                printCustom("Volume (in Lts): " + printData.getDispense_qty(), 1, 0);
//                printCustom("Rate (in INR/Litre): " + printData.getUnit_price(), 1, 0);
//                printCustom("Amount (in INR): " + printData.getAmount(), 1, 0);
//
//                printCustom("GST (in INR) (" + gstPercentage + "%): " + String.format("%.2f", gstAmount), 1, 0);
//                printCustom("Total Amount (in INR): " + String.format("%.2f", Double.parseDouble(printData.getAmount()) + gstAmount), 1, 0);
//
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("Meter Reading: " + printData.getMeter_reading(), 0, 0);
//                printCustom("Asset Other Reading1: " + printData.getAsset_other_reading(), 0, 0);
//                printCustom("Asset Other Reading2: " + printData.getAsset_other_reading2(), 0, 0);
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("Delivered by:" + printData.getDeliveredBy(), 0, 0);
//
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//
//                printNewLine();
//                printCustom(printData.getFooter_message(), 0, 1);
//
//                printCustom(new String(new char[32]).replace("\0", "="), 0, 1);
//
//                printCustom(new String(new char[32]).replace("\0", ""), 0, 1);
//                printCustom(new String(new char[32]).replace("\0", ""), 0, 1);
//                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void printCustom(String msg, int size, int align) {
            //Print config "mode"
            byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
            //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
            byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
            byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
            byte[] bbc2 = new byte[]{0x1B, 0x21, 0x05}; // 2- bold with light medium text
            byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text
            try {
                switch (size) {
                    case 0:
                        outputStream.write(cc);
                        break;
                    case 1:
                        outputStream.write(bb);
                        break;
                    case 2:
                        outputStream.write(bb2);
                        break;
                    case 3:
                        outputStream.write(bb3);
                        break;
                    case 4:
                        outputStream.write(bbc2);
                        break;
                }

                switch (align) {
                    case 0:
                        //left align
                        outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                        break;
                    case 1:
                        //center align
                        outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                        break;
                    case 2:
                        //right align
                        outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                        break;
                }
                outputStream.write(msg.getBytes());
                outputStream.write(PrinterCommands.LF);
                //outputStream.write(cc);
                //printNewLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void printPhoto(int img) {
            try {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
                if (bmp != null) {
                    byte[] command = Utils.decodeBitmap(bmp);
                    //outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    outputStream.write(PrinterCommands.ESC_HORIZONTAL_CENTERS);
                    printText(command);
//                printCustomPhoto(command, 0, 1);
                } else {
                    Log.e("Print Photo error", "the file isn't exists");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("PrintTools", "the file isn't exists");
            }
        }

        public void printCustomPhoto(int img) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            PrintPic printPic = PrintPic.getInstance();
            printPic.init(bmp);
            byte[] bitmapdata = printPic.printDraw();

            try {
                outputStream.write(bitmapdata);
            } catch (IOException e) {
                e.printStackTrace();
            }
            printNewLine();

//        try {
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
//                    img);
//            if (bmp != null) {
//                byte[] command = Utils.decodeBitmap(bmp);
//                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
////                outputStream.write(PrinterCommands.ESC_HORIZONTAL_CENTERS);
//                printText(command);
////                printCustomPhoto(command, 0, 1);
//            } else {
//                Log.e("Print Photo error", "the file isn't exists");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("PrintTools", "the file isn't exists");
//        }

        }

        public void printUnicode() {
            try {
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(Utils.UNICODE_TEXT);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void printNewLine() {
            try {
                outputStream.write(PrinterCommands.FEED_LINE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static void resetPrint() {
            try {
                outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
                outputStream.write(PrinterCommands.FS_FONT_ALIGN);
                outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
                outputStream.write(PrinterCommands.LF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void printText(String msg) {
            try {
                // Print normal text
                outputStream.write(msg.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //print byte[]
        private void printText(byte[] msg) {
            try {
                // Print normal text
                outputStream.write(msg);
                printNewLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }





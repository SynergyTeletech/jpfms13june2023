package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.DispenserUnitK;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.FreshDispenserUnitActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.Click;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LocationVehicleDatum;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PostOrderDelivered;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PrintData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.sendVol;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ChangePasswordbean;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddreadingPrint extends DialogFragment implements View.OnClickListener {
    List<Order> list = new ArrayList<>();
    private String Status = "";
    private boolean RateSet = false;
    private Context context;
    private String TAG = "OrderCompleteTAg";
    //    EditText message;
    TextView btnBill, btnDonate;
    public boolean complete = false;
    private Activity activity;
    private static final int REQUEST_CODE = 5454;
    private String dispensedQuantity = "0.00";
    private String Rate = "0.00";
    private String Amount = "0.00";
    private String batch = "4353";
    private int position = 0;
    private boolean orderState = false;
    private Order orderk;
    private int p = 0;
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
    private String atgStart;
    private int atg_end;
    private int dcv_status;
    private PrintData dataToPrint;
    private Boolean isFreshDispense = false;
    private Boolean stusBollean = false;
    private String mOrderDate = "";
    private boolean instant = false;
    private LocationVehicleDatum vehicleDataForFresh;
    private boolean isFromFreshDispense, isRFidEnabled, isRFidByPass;
    private String rfIdTagId = "", pumpId, nozzle;
    private Click click;
    boolean automation = false;
    private String franchiseName, GSTN, StartTime, endTime, Latitude, Longitude, GPS_Status, Lock_Status, ATG_Status, FuelQty, atgEnd, recieveQty, Mismatch, range, price, ammount;
    private DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;
    private String product, productId, TankId;

    public AddreadingPrint() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        db = BrancoApp.getDb();
        dispenseLocalDatabaseHandler = new DispenseLocalDatabaseHandler(context);
    }

    public interface clickTOtlizer {
        void ClickTOtlizer(boolean b);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recieve_layout_print, container, false);
        quantity = v.findViewById(R.id.quantity);
        rate = v.findViewById(R.id.rate);
        if (SharedPref.getLastFuelDispenserReading() == null) {
            //DispenserUnitActivity.clickTOtlizer.ClickTOtlizer(true);
        }
        amount = v.findViewById(R.id.et_amount);
        meterReading = v.findViewById(R.id.meterReading);
        assetReading1 = v.findViewById(R.id.assetReading1);
        assetReading2 = v.findViewById(R.id.assetReading2);
        btnPrintReceipt = v.findViewById(R.id.tv_btn_print);

        btnPrintReceipt.setOnClickListener(this);
        Bundle data = getArguments();
        if (data != null) {
            try {
                automation = data.getBoolean("automation", false);
                instant = data.getBoolean("instant", false);
                franchiseName = data.getString("franchiseName", "");
                GSTN = data.getString("GSTN", "");
                endTime = data.getString("endTime", "");
                StartTime = data.getString("StartTime", "");
                product = data.getString("product", "");
                productId = data.getString("productId", "");
                TankId = data.getString("tankId", "");
                Log.e("StartTimeka", "kam" + StartTime);
                Latitude = data.getString("Latitude", "");
                Longitude = data.getString("Longitude", "");

                GPS_Status = data.getString("GPS_Status", "");
                Lock_Status = data.getString("Lock_Status", "");

                ATG_Status = data.getString("ATG_Status", "");
                FuelQty = data.getString("FuelQty", "");
                atgStart = data.getString("atgStart", "");
                atgEnd = data.getString("atgEnd", "");
                recieveQty = data.getString("recieveQty", "");
                quantity.setText("Quantity : " + recieveQty);
                Mismatch = data.getString("Mismatch", "");
                range = data.getString("range", "");
                price = data.getString("price", "");
                ammount = data.getString("ammount", "");

                try {


                    Log.d("New Volume 2", dispensedQuantity);
                    Log.d("New Amount 2", Amount);
                    float AmountF = 0.0f;
                    Amount = String.format("%.2f", AmountF);
                    //}
                } catch (Exception e) {
                    Log.e("kamalResponse", "error");
                    e.printStackTrace();
                }
                Log.d("mOrderDate", mOrderDate + "");

                if (isFromFreshDispense) {
                    vehicleDataForFresh = (LocationVehicleDatum) data.getSerializable("vehicleDataForFresh");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        v.findViewById(R.id.saveReadings).setOnClickListener(this);



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
                else {
                    print();
                }
//                if (stusBollean) {
//                    print();
//                }
//                else {
//                    for (int i = 0; i < list.size(); i++) {
//                        if (Integer.parseInt(DispenserUnitK.transactionId) == Integer.parseInt(list.get(i).getTransaction_id())) {
//                            Log.e("transtionId", DispenserUnitK.transactionId + "," + list.get(i).getTransaction_id());
//                            p = i;
//                        } else {
//
//                            Log.e("transtionId", "not MAtched");
//                        }
//                    }
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setMessage("Do you want to fill another asset, Or Complete Delivery?");
//                    builder.setNegativeButton("Complete Delivery", new DialogInterface.OnClickListener() {
//                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            orderState = true;
//                            list.clear();
//                            list = SharedPref.getProgressList();
//
//                            sendAssetStatus(DispenserUnitK.selectedAssetId);
//                            if (isFromFreshDispense) {
//                                list.get(p).setStaus("12");
//
//                                complete = true;
//                                Log.e("isFromFreshDispenceAdd", "FreshDispence");
//                                markFreshDispenseTransactionCompleted("U");
//                            } else {
//
//                                Log.e("isFromFreshDispenceAdd", "Dispence");
//                                complete = true;
//                                list.get(p).setStaus("12");
//                                markOrderDispenseTransactionCompleted("U");
//                            }
//
//                        }
//                    });
//                    builder.setPositiveButton("Fill Another Asset ", new DialogInterface.OnClickListener() {
//                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            list.clear();
//                            orderState = false;
//                            sendAssetStatus(DispenserUnitK.selectedAssetId);
//                            list = SharedPref.getProgressList();
//                            if (isFromFreshDispense) {
//                                complete = false;
//
//                                Log.e("isFromFreshDispenceAdd", "FreshDispence");
//                                markFreshDispenseTransactionCompleted("I");
//                            } else {
//                                complete = false;
//                                Log.e("isFromFreshDispenceAdd", "Dispence");
//                                markOrderDispenseTransactionCompleted("I");
//                            }
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
////                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
////
////                        @Override
////                        public void onShow(DialogInterface dialog) {
////                            //   Log.d("KAMALqUNATITY", "" + p + String.valueOf(Double.parseDouble(orderDeliveryInProgress.getOrder().get(p).getQty()) - Double.parseDouble(dispensedQuantity)));
////                            if (Double.parseDouble(orderDeliveryInProgress.getOrder().get(p).getQty()) - Double.parseDouble(orderDeliveryInProgress.getProgress().getDeliveredData()) - Double.parseDouble(dispensedQuantity) <= 0.1) {
////                                if (((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE) != null) {
////                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
////                                } else {
////                                    Toast.makeText(context, "button null", Toast.LENGTH_SHORT).show();
////                                }
////
////                            }
////                        }
////                    });
//
//
//                    alertDialog.show();
//                }
                break;

            case R.id.backBtn:
                dismiss();
                break;
        }
    }

    private void print() {
        Log.e("logkam", "print");

        printBill();
//        dataToPrint = new PrintData();
//        dataToPrint.setTransaction_type("Order Dispense");
//        dataToPrint.setAmount(orderk.getTotal_amount());
//        dataToPrint.setLocationName(orderk.getCustomer_name());
//        dataToPrint.setCustomer_name(orderk.getFname());
//        dataToPrint.setAssets_id(selectedAsset);
//
//        Log.e("AssetIdADD", "" + selectedAsset);
//        dataToPrint.setAssets_name(selectedAssetName);
//        dataToPrint.setFueling_start_time(startTime == null ? "NULL" : startTime);
//        dataToPrint.setFueling_stop_time(String.valueOf(Calendar.getInstance().getTime()));
//        dataToPrint.setDispense_qty(orderk.getTotal_dispense_qty());
//        dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
//        dataToPrint.setAsset_other_reading(orderk.getLocation_reading_1());
//        Log.e("assetReadingKAmal", String.valueOf(assetReading2.getText()));
//        dataToPrint.setAsset_other_reading2(orderk.getAsset_other_reading_2());
//        dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
//        dataToPrint.setAtg_tank_end_reading(String.valueOf(atg_end));
//        if (SharedPref.getLastFuelDispenserReading() != null) {
//            Log.e("PrintTotlizer", SharedPref.getLastFuelDispenserReading());
//            dataToPrint.setVolume_totalizer(SharedPref.getLastFuelDispenserReading());
//        } else {
//            dataToPrint.setVolume_totalizer("777");
//
//            Log.e("PrintTotlizer", dataToPrint.getVolume_totalizer());
//
//        }
//
//        dataToPrint.setNo_of_event_start_stop("3");
//        dataToPrint.setDispensed_in(selectedAssetName);
//        dataToPrint.setRfid_status(rfIdTagId.isEmpty() ? "N/A" : rfIdTagId);
//        dataToPrint.setTerminal_id(SharedPref.getTerminalID());
//        dataToPrint.setBatch_no(batch);
//        dataToPrint.setLatitude(orderk.getLatitude());
//        dataToPrint.setLongitude(orderk.getLongitude());
//        dataToPrint.setLocation_reading_1(orderk.getDispense_latitude());
//        dataToPrint.setLocation_reading_2(orderk.getDispense_longitude());
//        dataToPrint.setGps_status("Bypassed");
//        dataToPrint.setDcv_status(String.valueOf(dcv_status));
//        dataToPrint.setFlag("U");
//        dataToPrint.setTransaction_no(orderk.getOrder_id());
//        dataToPrint.setUnit_price(orderk.getUnit_price());
//        if (automation) {
//            dataToPrint.setVehicle_id(pumpId);
//        } else if (instant) {
//
//            dataToPrint.setVehicle_id(pumpId);
//        } else {
//            dataToPrint.setVehicle_id(SharedPref.getVehicleId());
//        }
//        dataToPrint.setTransaction_id(orderk.getTransaction_id());
//        dataToPrint.setOrder_id(orderk.getLead_number());
//        dataToPrint.setOrder_data_time(orderk.getCreated_datatime());
//        dataToPrint.setTotal_qty(orderk.getQuantity());
//        dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
//        dataToPrint.setLocation_id(orderk.getLocation_id());
//        dataToPrint.setLocationName(orderk.getLocation_name());
//        dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty() ? " Thank You " : SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
//        dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
//        dataToPrint.setGST(orderk.getGst_percentage()); //Need GST Value Which API will provide it
//        dataToPrint.setTotal_amount(orderk.getTotal_amount());

//if (SharedPref.getProgressList()!=null&&SharedPref.getOfflineOrderList()!=null) {
//    list = SharedPref.getProgressList();
//    List<DeliveryInProgress> delivery = new ArrayList<>();
//    delivery = SharedPref.getOfflineOrderList();
//    delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", Float.parseFloat(dispensedQuantity)));
//
//    list.get(position).setDelivered_data(String.format("%.2f", Float.parseFloat(dispensedQuantity)));
//    SharedPref.setProgressList(list);
//    SharedPref.setOffineOrderList(delivery);
//}


//        SharedPref.setIsFuelingStarted(false);
//        SharedPref.setTotalizerReadingDiff("0");
//        printDispenseParams(dataToPrint);
    }

    private void sendAssetStatus(String assetId) {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.change_asset_status(assetId, 1).enqueue(new Callback<ChangePasswordbean>() {
            @Override
            public void onResponse(Call<ChangePasswordbean> call, Response<ChangePasswordbean> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSucc()) {
                        try {
                            Toast.makeText(getActivity(), "asset status updated", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordbean> call, Throwable t) {

            }
        });
    }

    private void clearSALE() {
        if (context instanceof DispenserUnitK) {
            ((DispenserUnitK) context).ClearSale();
        }

        if (context instanceof FreshDispenserUnitActivity) {
            ((FreshDispenserUnitActivity) context).ClearSale();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void markOrderDispenseTransactionCompleted(String flag) {
        try {
            Log.d("printthis", "print");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssZ");
            String strDate = simpleDateFormat.format(new Date());
            dataToPrint = new PrintData();
            dataToPrint.setTransaction_type("Order Dispense");
            dataToPrint.setAmount(String.format("%.2f", Float.parseFloat(Amount)));
            dataToPrint.setLocationName("noida");
            dataToPrint.setCustomer_name("new");
            if (automation) {
                dataToPrint.setAssets_id(pumpId);
            } else {
                dataToPrint.setAssets_id(selectedAsset);
            }
            dataToPrint.setAssets_name("selectedAssetName");
            dataToPrint.setFueling_start_time(startTime == null ? "NULL" : startTime);
            dataToPrint.setFueling_stop_time(String.valueOf(Calendar.getInstance().getTime()));
            dataToPrint.setDispense_qty(String.format("%.2f", Float.parseFloat(dispensedQuantity)));
            dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
            dataToPrint.setAsset_other_reading(String.valueOf(assetReading1.getText()));
            Log.e("assetReadingKAmal", String.valueOf(assetReading2.getText()));
            dataToPrint.setAsset_other_reading2(String.valueOf(assetReading2.getText()));
            dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
            dataToPrint.setAtg_tank_end_reading(String.valueOf(atg_end));

            if (SharedPref.getLastFuelDispenserReading() != null && !SharedPref.getLastFuelDispenserReading().equalsIgnoreCase("")) {
                Log.e("PrintTotlizer", "kamal" + SharedPref.getLastFuelDispenserReading());
                dataToPrint.setVolume_totalizer(SharedPref.getLastFuelDispenserReading());
            } else {
                dataToPrint.setVolume_totalizer("777");

                Log.e("PrintTotlizer", "777");

            }
            Log.e("Totlizerlast", "kamal" + dataToPrint.getVolume_totalizer());
            dataToPrint.setNo_of_event_start_stop("3");
            dataToPrint.setDispensed_in(selectedAssetName);
            dataToPrint.setRfid_status(rfIdTagId.isEmpty() ? "N/A" : "" + rfIdTagId);
            Log.e("RfidKamal", "" + rfIdTagId);
            dataToPrint.setTerminal_id(SharedPref.getTerminalID());
            dataToPrint.setBatch_no(batch);
            if (automation || instant) {

                dataToPrint.setLatitude(" ");
                dataToPrint.setLongitude(" ");
                dataToPrint.setLocation_reading_1(" ");
                dataToPrint.setLocation_reading_2(" ");
            } else {
                dataToPrint.setLatitude(orderDeliveryInProgress.getProgress().getCurrentLat());
                dataToPrint.setLongitude(orderDeliveryInProgress.getProgress().getCurrentLong());
                dataToPrint.setLocation_reading_1(orderDeliveryInProgress.getProgress().getCurrentLat());
                dataToPrint.setLocation_reading_2(orderDeliveryInProgress.getProgress().getCurrentLong());
            }
            dataToPrint.setGps_status("ok");
            dataToPrint.setDcv_status(String.valueOf(dcv_status));
            dataToPrint.setFlag(flag);
            if (automation || instant) {

                dataToPrint.setTransaction_no("11");
            } else {
                dataToPrint.setTransaction_no(AddReadingsDialog.dataToPrint.getTransaction_no());
            }
            dataToPrint.setUnit_price(String.format("%.2f", Float.parseFloat(Rate)));
            if (automation || instant) {
                dataToPrint.setVehicle_id(pumpId);
            } else {
                dataToPrint.setVehicle_id(pumpId);
            }
            if (automation || instant) {
                dataToPrint.setTransaction_id(" ");
                dataToPrint.setOrder_id(" ");
                dataToPrint.setOrder_data_time(" ");
                dataToPrint.setTotal_qty(" ");
                dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
                dataToPrint.setLocation_id(" ");
                dataToPrint.setLocationName(" ");
            } else {
                dataToPrint.setTransaction_id(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                dataToPrint.setOrder_id(orderDeliveryInProgress.getOrder().get(0).getLead_number());
                dataToPrint.setOrder_data_time(orderDeliveryInProgress.getOrder().get(0).getCreatedDatatime());
                dataToPrint.setTotal_qty(orderDeliveryInProgress.getOrder().get(0).getQty());
                dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
                dataToPrint.setLocation_id(selectedAsset);
                dataToPrint.setLocationName(orderDeliveryInProgress.getOrder().get(0).getLocationName());
            }
            dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty() ? " Thank You " : SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
            dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
            dataToPrint.setGST("0"); //Need GST Value Which API will provide it
            dataToPrint.setTotal_amount(String.format("%.2f", Float.parseFloat(dataToPrint.getAmount()) + Float.parseFloat(dataToPrint.getAmount()) * Float.parseFloat(dataToPrint.getGST()) / 100));
            printBill();
            SharedPref.setIsFuelingStarted(false);
            SharedPref.setTotalizerReadingDiff("0");
            printDispenseParams(dataToPrint);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils.isNetworkConnected(context)) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);

                    Log.e("hashmapResponse", getHashMap(dataToPrint).toString());

                    apiInterface.postOrderDelivered(getHashMap(dataToPrint)).enqueue(new Callback<PostOrderDelivered>() {
                        @Override
                        public void onResponse(Call<PostOrderDelivered> call, Response<PostOrderDelivered> response) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            if (response.isSuccessful() && response.body().getSucc()) {
                                // SharedPref.setLastFuelDispenserReading(null);
                                List<DeliveryInProgress> delivery = new ArrayList<>();
                                delivery = SharedPref.getOfflineOrderList();
                                delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));

                                list.get(p).setDelivered_data(String.format("%.2f", (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))));
                                SharedPref.setProgressList(list);
                                SharedPref.setOffineOrderList(delivery);
                                Log.e("kamalOrderResponse", list.get(p).getTransaction_id() + "" + (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))
                                        + "," + String.format("%.2f", (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))))
                                ;
                                showResponseInDialog(context, response.body().getPublicMsg(), true, false);
                            } else {
                                showResponseInDialog(context, response.body().getPublicMsg(), false, true);
                                Log.e("PostUnSuccResponse", response.body().getSucc() + "");
                            }
                        }

                        @Override
                        public void onFailure(Call<PostOrderDelivered> call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            try {
                                showResponseInDialog(context, "Server Error. Please try again...", false, true);
                                Log.e("PostFailure", t.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            } else {
                //  showResponseInDialog(context, "No Internet", true, false);
                Log.e("kamalkkk", "no Internet ");
                //save Order Data in Local
                Toast.makeText(context, "Internet Not Available. Data saved in Local", Toast.LENGTH_LONG).show();
                saveOrderDispenseDataInSQLiteLocal(dataToPrint);
                if (instant || automation) {

                } else {
                    List<DeliveryInProgress> delivery = new ArrayList<>();
                    delivery = SharedPref.getOfflineOrderList();
                    delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));

                    list.get(p).setDelivered_data(String.format("%.2f", (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))));
                    SharedPref.setProgressList(list);
                    SharedPref.setOffineOrderList(delivery);
                    Log.e("kamalOrderResponse", list.get(p).getTransaction_id() + "" + (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))
                            + "," + String.format("%.2f", (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))))
                    ;
                }
                clearSALE();
                dismiss();
                if (getActivity() != null) {
                    getActivity().finish();
                } else {
                    Log.e("ActivityNot", "Null");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void markFreshDispenseTransactionCompleted(String flag) {
        try {
            dataToPrint = new PrintData();
            dataToPrint.setTransaction_type("Instant Dispense");
            dataToPrint.setAmount(String.format("%.2f", Float.parseFloat(Amount)));
            dataToPrint.setAssets_id(selectedAsset);
            dataToPrint.setAssets_name(selectedAssetName);
            dataToPrint.setFueling_start_time(startTime == null ? "NULL" : startTime);
            dataToPrint.setFueling_stop_time(String.valueOf(Calendar.getInstance().getTime()));
            dataToPrint.setDispense_qty(String.format("%.2f", Float.parseFloat(dispensedQuantity)));
            dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
            dataToPrint.setAsset_other_reading(String.valueOf(assetReading1.getText()));

            Log.e("assetReadingKAmal", String.valueOf(assetReading2.getText()));
            dataToPrint.setAsset_other_reading2(String.valueOf(assetReading2.getText()));
            dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
            dataToPrint.setAtg_tank_end_reading(String.valueOf(atg_end));

            if (SharedPref.getLastFuelDispenserReading() != null) {
                Log.e("PrintTotlizer", SharedPref.getLastFuelDispenserReading());
                dataToPrint.setVolume_totalizer(SharedPref.getLastFuelDispenserReading());
            } else {
                dataToPrint.setVolume_totalizer("777");

                Log.e("PrintTotlizer", "777");

            }

            dataToPrint.setNo_of_event_start_stop("1");
            dataToPrint.setDispensed_in(selectedAssetName);
            dataToPrint.setTerminal_id(SharedPref.getTerminalID());
            dataToPrint.setBatch_no(batch);
            dataToPrint.setLatitude(orderDeliveryInProgress.getProgress().getCurrentLat());
            dataToPrint.setLongitude(orderDeliveryInProgress.getProgress().getCurrentLong());
            dataToPrint.setLocation_reading_1(orderDeliveryInProgress.getProgress().getCurrentLat());
            dataToPrint.setLocation_reading_2(orderDeliveryInProgress.getProgress().getCurrentLong());
            dataToPrint.setRfid_status(rfIdTagId.isEmpty() ? "N/A" : "" + rfIdTagId);
            dataToPrint.setGps_status(String.valueOf(orderDeliveryInProgress.getProgress().getLocationBypass()));
            dataToPrint.setDcv_status(String.valueOf(dcv_status));
            dataToPrint.setFlag(flag);
            dataToPrint.setUnit_price(orderDeliveryInProgress.getProgress().getFuelPrice());
            dataToPrint.setUnit_price(Rate);
            if (automation || instant) {
                dataToPrint.setVehicle_id(pumpId);
            } else {
                dataToPrint.setVehicle_id(pumpId);
            }
         //   dataToPrint.setTransaction_no(SharedPref.getTerminalID() + orderDeliveryInProgress.getOrder().get(0).getTransactionId());
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
            dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty() ? " Thank You " : SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
            dataToPrint.setOrder_data_time(mOrderDate);
            dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
            dataToPrint.setGST("0"); //Need GST Value Which API will provide it
            dataToPrint.setTotal_amount(String.format("%.2f", Float.parseFloat(dataToPrint.getAmount()) + Float.parseFloat(dataToPrint.getAmount()) * Float.parseFloat(dataToPrint.getGST()) / 100));


            printBill();

            Log.e("TransactionID", SharedPref.getProgressList().get(p).getTransaction_id());
            SharedPref.setIsFuelingStarted(false);
            SharedPref.setTotalizerReadingDiff("0");

            printDispenseParams(dataToPrint);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils.isNetworkConnected(context)) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                    Log.e("hashmapResponse", getHashMap(dataToPrint).toString());
                    apiInterface.postFreshDispenseOrderDelivered(getHashMap(dataToPrint)).enqueue(new Callback<LoginResponse>() {

                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Response response1 = response;
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("PostOnResponse", response.body().getSucc() + "");
                            if (response.isSuccessful() && response.body().getSucc()) {
                                List<DeliveryInProgress> delivery = new ArrayList<>();
                                delivery = SharedPref.getOfflineOrderList();
                                Log.e("dispence", "" + (Float.parseFloat(list.get(position).getDelivered_data()) + Float.parseFloat(dispensedQuantity)));
                                delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));

                                list.get(p).setDelivered_data(String.format("%.2f", (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))));
                                SharedPref.setProgressList(list);
                                SharedPref.setOffineOrderList(delivery);
                                Log.e("PostSuccResponse", response.body().getSucc() + "");
                                // SharedPref.setLastFuelDispenserReading(null);
                                showResponseInDialog(context, "Order Successfully updated", true, true);

                            } else {
                                showResponseInDialog(context, "Something went wrong. Please try again...", false, true);
                                Log.e("PostUnSuccResponse", response.body().getSucc() + "");
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            try {
                                showResponseInDialog(context, "Server Error. Please try again...", false, true);
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
                    } else {
                        click.ClickK(complete);
                        Log.e("ActivityNot", "null");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showResponseInDialog(Context context, String message, boolean isSuccess, boolean isFreshDispense) {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(message);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            if (alertDialog != null)
                alertDialog.dismiss();
        }
        Log.e("Activitynot", "" + isSuccess + "showDialog");
        if (isSuccess) {
            clearSALE();


            if (dataToPrint.getFlag().equalsIgnoreCase("I")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null) {


                            if (!isFreshDispense) {
                                Log.e("ActivityNot", "run");
                                Intent intent = new Intent(context, DispenserUnitK.class);
                                Order order = new Order();
                                order.setTransaction_id(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                                order.setQuantity(orderDeliveryInProgress.getOrder().get(0).getQty());
                                order.setLocation_id(orderDeliveryInProgress.getOrder().get(0).getLocationId());
                                order.setLocation_name(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                                intent.putExtra("pumpId", pumpId);
                                intent.putExtra("nozzle", nozzle);
                                intent.putExtra("orderDetail", order);
                                intent.putExtra("isFromFreshDispense", false);

                                startActivity(intent);
                            } else {

                                Log.e("ActivityNot", "DispenceNot");
                                Intent intent = new Intent(context, DispenserUnitK.class);
                                orderDeliveryInProgress.getProgress().setDeliveredData(dispensedQuantity);
                                intent.putExtra("freshOrder", orderDeliveryInProgress);
                                intent.putExtra("pumpId", pumpId);
                                intent.putExtra("nozzle", nozzle);
                                intent.putExtra("isFromFreshDispense", true);
                                startActivity(intent);
                            }
                            getActivity().finish();
                        } else {

                            Log.e("ActivityNot", "nullmanoj");

                            Log.e("completetru", "" + complete + "," + instant);
                            if (instant) {
                                complete = true;
                            }
                            click.ClickK(complete);
                            Log.e("listnerADdreading", String.valueOf(orderState));
                            //  DispenserUnitActivity.lisner.setClick(orderState);
                        }
                    }
                }, 1000);

            } else {
                if (instant) {
                    complete = true;
                }
                click.ClickK(complete);
            }
        } else {
            if (SharedPref.getLastFuelDispenserReading() == null) {
                Toast.makeText(context, "Get TotlizerId", Toast.LENGTH_SHORT).show();
                // DispenserUnitActivity.clickTOtlizer.ClickTOtlizer(true);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    public void printDispenseParams(PrintData dataToPrint) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fueling_start_time", dataToPrint.getFueling_start_time());
            jsonObject.put("fueling_stop_time", dataToPrint.getFueling_stop_time());
            jsonObject.put("assets_id", dataToPrint.getAssets_id());
            jsonObject.put("dispense_qty", dataToPrint.getDispense_qty());
            jsonObject.put("meter_reading", dataToPrint.getMeter_reading());
            jsonObject.put("asset_other_reading", dataToPrint.getAsset_other_reading());
            Log.e("Asset_other_reading2", dataToPrint.getAsset_other_reading2());
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
            if (automation || instant) {
                jsonObject.put("vehicle_id", pumpId);
            } else {
                jsonObject.put("vehicle_id", dataToPrint.getVehicle_id());
            }
            jsonObject.put("transaction_id", dataToPrint.getTransaction_id());
            jsonObject.put("total_qty", dataToPrint.getTotal_qty());
            jsonObject.put("order_id", dataToPrint.getOrder_id());
            jsonObject.put("duty_id", dataToPrint.getDuty_id());
            jsonObject.put("location_id", dataToPrint.getLocation_id());
            jsonObject.put("footer_message", dataToPrint.getFooter_message());
            jsonObject.put("gst_percentage", dataToPrint.getGST());
            jsonObject.put("total_amount", dataToPrint.getTotal_amount());
            Log.d("PostOrderParams", jsonObject.toString());
            System.out.println("PostOrderParams:" + jsonObject.toString());
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public HashMap getHashMap(PrintData dataToPrint) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fueling_start_time", dataToPrint.getFueling_start_time());
        hashMap.put("fueling_stop_time", dataToPrint.getFueling_stop_time());
        hashMap.put("assets_id", dataToPrint.getAssets_id());
        hashMap.put("dispense_qty", dataToPrint.getDispense_qty());
        hashMap.put("meter_reading", dataToPrint.getMeter_reading());
        hashMap.put("asset_other_reading", dataToPrint.getAsset_other_reading());

        Log.e("Asset_other_reading2", dataToPrint.getAsset_other_reading2());
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
        if (automation || instant) {
            hashMap.put("vehicle_id", pumpId);
        } else {
            hashMap.put("vehicle_id", dataToPrint.getVehicle_id());
        }
        hashMap.put("transaction_id", dataToPrint.getTransaction_id());
        hashMap.put("total_qty", dataToPrint.getTotal_qty());
        hashMap.put("order_id", dataToPrint.getOrder_id() == null ? new Date().getTime() + "" : (dataToPrint.getOrder_id().isEmpty() ? new Date().getTime() + "" : dataToPrint.getOrder_id()));
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
                        if (automation || instant) {
                            orderModel.setVehicle_id(pumpId);
                        } else {
                            orderModel.setVehicle_id(printData.getVehicle_id());
                        }
                        orderModel.setAssets_id(printData.getAssets_id());
                        orderModel.setMeter_reading(printData.getMeter_reading());
                        orderModel.setAsset_other_reading(printData.getAsset_other_reading());

                        Log.e("Asset_other_reading2", dataToPrint.getAsset_other_reading2());
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
                        Log.d("Locally", "Pre Data saved Locally with TransactionId: " + printData.getTransaction_id());
                        dispenseLocalDatabaseHandler.addOrderDispenseData(orderModel);
                        Log.d("Locally", "SavedOrderDataListSize" + dispenseLocalDatabaseHandler.getAllOrderDispenseDataList().size());
                    } catch (Exception e) {
                        Log.d("LocallyCrash", "SavedOrderDataListSize");

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
                        if (automation || instant) {
                            orderModel.setVehicle_id(pumpId);
                        } else {
                            orderModel.setVehicle_id(printData.getVehicle_id());
                        }
                        orderModel.setAssets_id(printData.getAssets_id());
                        orderModel.setMeter_reading(printData.getMeter_reading());
                        orderModel.setAsset_other_reading(printData.getAsset_other_reading());

                        Log.e("Asset_other_reading2", dataToPrint.getAsset_other_reading2());
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

    private void printBill() {
        sendata();
        if (NavigationDrawerActivity.btsocket == null) {
            Toast.makeText(context, "Please Connect Printer to print Bill", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            //Added/Updated following lines by Laukendra on 15-11-19
//                int gstPercentage = 0;
//                if (SharedPref.getLoginResponse().getVehicle_data().get(0).getProductName().equalsIgnoreCase("DIESEL")) {
//                    gstPercentage = 0; //custom value
//                } else {
//                    gstPercentage = 12;
//                }
//                double gstAmount = Double.parseDouble(printData.getAmount()) * gstPercentage / 100;
//                printData.setGST(gstPercentage + "");
//                printData.setTotal_amount(String.valueOf(Float.parseFloat(printData.getAmount()) + gstAmount));
            printTransactionBill();
            //Added/Updated above lines by Laukendra on 15-11-19
        }

    }

    protected void printTransactionBill() {

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
            printPhoto(R.drawable.synergyandjplogo);
            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//            if (isFromFreshDispense) {
//                Log.e("isFromFreshDispenceAdd", "FreshDispence");
//                printCustom("INSTANT DISPENSE", 1, 1);
//            } else {
//                Log.e("isFromFreshDispenceAdd", "Dispence");
//
//            }
            printCustom("FUEL RECIEVED", 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

            printNewLine();
            printCustom("Franchise Name: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getName(), 0, 0);
            printCustom("GSTIN: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getGstNo(), 0, 0);
            printCustom("Refueller No: " + meterReading.getText().toString(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("TRANSACTION DETAILS", 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

            try {

                printCustom("Order Date: " + StartTime, 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            printCustom("Txn No: " + "1", 0, 0);
//            printCustom("Customer Name: " + printData.getCustomer_name(), 0, 0);
//            printCustom("Location Name: " + printData.getLocationName(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//            printCustom("DELIVERY DETAILS", 1, 1);

            printCustom("Start Fueling Time: " + StartTime, 0, 0);
            printCustom("End Fueling Time: " + endTime, 0, 0);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

//            printCustom("Latitude: " + Latitude, 0, 0);
//            printCustom("Longitude: " + Longitude, 0, 0);
//            printCustom("GPS Status: " + GPS_Status, 0, 0);
//            printCustom("Lock Status: " + Lock_Status, 0, 0);
            printCustom("ATG Status: " + "OK", 0, 0);
            // printCustom("Batch no: " + printData.getBatch_no(), 0, 0);


            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("PRODUCT: " + product.toUpperCase(), 1, 1);
            printCustom("DENSITY: " + assetReading1.getText().toString(), 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

//            printCustom("Asset Name: " + printData.getAssets_name(), 0, 0);
//            printCustom("RFID Status: " + printData.getRfid_status(), 0, 0);
//
//            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//
//            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//            printCustom("Txn No: " + printData.getTransaction_no(), 1, 0);

            printCustom("Total Ordered Qty (in Lts): " + FuelQty, 1, 0);
            printCustom("ATG Start Reading (in Lts): " + atgStart, 1, 0);
            printCustom("ATG End Reading (in Lts): " + atgEnd, 1, 0);
            printCustom("Total Received Qty (in Lts): " + recieveQty, 1, 0);
            printCustom("Total Mismatch : " + Mismatch, 1, 0);
//            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//            printNewLine();
//            printCustom("Rate (in INR/Litre): " + price, 1, 0);
//            printCustom("Amount (in INR): " + ammount, 1, 0);

//            printCustom("GST (in INR) (" + gstPercentage + "%): " + String.format("%.2f", gstAmount), 1, 0);
//            printCustom("Total Amount (in INR): " + String.format("%.2f", Double.parseDouble(printData.getAmount()) + gstAmount), 1, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);


            printNewLine();
            printCustom(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty() ? " Thank You " : SharedPref.getLoginResponse().franchise_data.get(0).footerMessage, 0, 1);

            printCustom(new String(new char[32]).replace("\0", "="), 0, 1);

            printCustom(new String(new char[32]).replace("\0", ""), 0, 1);
            printCustom(new String(new char[32]).replace("\0", ""), 0, 1);
            printNewLine();

            outputStream.flush();
            (getActivity()).finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendata() {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.fuelRecieved(SharedPref.getVehicleId(), StartTime, endTime, "Recive", "3", "0.0", "0.0", "0.0", "1", "2", Mismatch, atgStart, atgEnd, "807", GPS_Status, "", "", ATG_Status, FuelQty, recieveQty).enqueue(new Callback<sendVol>() {
            @Override
            public void onResponse(Call<sendVol> call, Response<sendVol> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSucc()) {

                    }
                }
            }

            @Override
            public void onFailure(Call<sendVol> call, Throwable t) {

            }
        });
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
            dismiss();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



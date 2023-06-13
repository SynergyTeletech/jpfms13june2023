package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.SynergyDispenser;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.FreshDispenserUnitActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.Click;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LocationVehicleDatum;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PostOrderDelivered;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PrintData;
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
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddReadingsDialog extends DialogFragment implements View.OnClickListener {
    List<Order> list = new ArrayList<>();
    String atgFinalReading;
    private  String fuelingStartTime,fuelingEndTime,finalVolumeTotalizer,initialVolumeTotalizer,currentLong,currentLat;
    private Context context;
    private String TAG = "AddReadingDialog_ATG";
    ProgressDialog progressDialog;
    TextView btnBill ;
    public boolean complete = false;
    public boolean isflagComplete = false;
    private String dispensedQuantity = "0.00";
    private String Rate = "0.00";
    private String batch = "4353";
    private int position = 0;
    private boolean orderState = false;
    private Order orderk;
    private int p = 0;
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
    private String atgStart = "0.0";
    private String atg_end = "0.0";
    private String asetatgintial_Reading="0";
    private String asetatgfinal_Reading="0";
    private int dcv_status;
    private ScheduledExecutorService executor;
    public static PrintData dataToPrint;
    private Boolean isFreshDispense = false;
    int AssetPostion=0;
    private Boolean stusBollean = false;
    private ArrayList<Asset> asset;
    private String mOrderDate = "",assetRegNo="0";
    private LocationVehicleDatum vehicleDataForFresh;
    private boolean isFromFreshDispense, isRFidEnabled, isRFidByPass;
    private String rfIdTagId = "";
    private Click click;
    public String timeStamp;
    public String atgVolumeN;
    float amountPrint;
    String amounttot;
    String transaction_no="";
    String uuid="";
    private DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;

    public AddReadingsDialog(Click click) {
        this.click = click;
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

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_note_readings, container, false);
        Long tsLong = System.currentTimeMillis()/1000;
        timeStamp = tsLong.toString();

        try {
            uuid=createTransactionID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        transaction_no=uuid+timeStamp;
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
                atgVolumeN = data.getString("atgVolumeN");
                Rate = data.getString("FuelRate");
                amountPrint = (Float.valueOf(dispensedQuantity) * Float.valueOf(Rate));
                amounttot=String.valueOf(amountPrint);
                startTime = data.getString("startTime");
                selectedAsset = data.getString("selectedAsset");
                selectedAssetName = data.getString("selectedAssetName");
                locationName = data.getString("location_name");
                asetatgintial_Reading=data.getString("assetStartReading");
                asetatgfinal_Reading=data.getString("assetEndtReading");
                assetRegNo = data.getString("assetRegNo");
                mOrderDate = data.getString("orderDate");
                rfIdTagId = data.getString("rfidTagId");
                atgStart = data.getString("atgStart");
                atg_end = data.getString("atgEnd");
                isRFidEnabled = data.getBoolean("rfidEnabled");
                isRFidByPass = data.getBoolean("rfidByPass");
                isFromFreshDispense = data.getBoolean("isFromFreshDispense");
                fuelingEndTime = data.getString("fuelingEndTime");
                fuelingStartTime = data.getString("fuelingStartTime");
                finalVolumeTotalizer = data.getString("finalVolumeTotalizer");
                initialVolumeTotalizer = data.getString("initalVolumeTotalizer");
                currentLat = data.getString("currentLat");
                currentLong = data.getString("currentLong");
                stusBollean = data.getBoolean("stausofprint", false);
                position = SynergyDispenser.position;
                try {
                    orderDeliveryInProgress = data.getParcelable("orderDetail");
                    asset=SharedPref.getOfflineOrderList().get(position).getAssets();
                } catch (Exception e) {
                    orderk = data.getParcelable("orderDetail");
                }
                if (isFromFreshDispense) {
                    vehicleDataForFresh = (LocationVehicleDatum) data.getSerializable("vehicleDataForFresh");
                }
                quantity.setText(String.format("Quantity:%s Ltr.", dispensedQuantity != null ?
                        dispensedQuantity : "N/A"));
                amount.setText(String.format("Amount : Rs."+""+amountPrint));
                rate.setText(String.format("Rate %s Rs./Ltr", Rate != null ? Rate : "N/A"));
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
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getDialog().getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.50f);
        int dialogWindowHeight = (int) (displayHeight * 0.85f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        getDialog().getWindow().setAttributes(layoutParams);
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
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
                if (stusBollean) {
                    print();
                }
                else {
                    list.clear();
                    list = dispenseLocalDatabaseHandler.getOfflineOrder();

//                    for (int i = 0; i < list.size(); i++) {
//                        if (Integer.parseInt(SynergyDispenser.transactionId) == Integer.parseInt(list.get(i).getTransaction_id())) {
//
//                        } else {
//                        }
//                    }
                    for (int i=0;i<asset.size();i++){
                        if(Integer.parseInt(selectedAsset)==Integer.parseInt(asset.get(i).getId())){
                            AssetPostion=i;

                        }
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to fill another asset, Or Complete Delivery?");
                    builder.setNegativeButton("Complete Delivery", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            orderState = true;

//                            sendAssetStatus(SynergyDispenser.selectedAssetId);
                            if (isFromFreshDispense) {
                                list.get(p).setStaus("12");
                                isflagComplete = true;
                                complete = true;
                                Log.e("isFromFreshDispenceAdd", " complete FreshDispence");
                                Log.v("STATUS", "Freshdispence complete" + list.get(p).getStaus());
                                markFreshDispenseTransactionCompleted("U");
                                btnBill.setEnabled(false);
                                btnBill.setBackgroundColor(context.getResources().getColor(R.color.md_grey_500));
//                                    btnBill.setBackgroundColor(R.color.md_grey_500);
                            } else {
                                complete = true;
                                isflagComplete = true;
                                list.get(p).setStaus("12");
                                if (initialVolumeTotalizer != null) {
                                    updateStatus();
                                    markOrderDispenseTransactionCompleted("U");
                                    btnBill.setEnabled(false);
//                                        btnBill.setBackgroundColor(R.color.md_grey_500);
//                                    dismiss();
                                } else {
                                    Toast.makeText(context, "InitialTotalizer null", Toast.LENGTH_LONG).show();
                                }

                            }
                        }

                    });

                    builder.setPositiveButton("Fill Another Asset ", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.clear();
                            orderState = false;
                            list = dispenseLocalDatabaseHandler.getOfflineOrder();

                            if (isFromFreshDispense) {
                                isflagComplete = false;
                                complete = false;
                                Log.e("isFromFreshDispenceAdd", "FreshDispence");
                                markFreshDispenseTransactionCompleted("I");

                                btnBill.setEnabled(false);
                                btnBill.setBackgroundColor(context.getResources().getColor(R.color.md_grey_500));
                            } else {
                                //     SharedPref.setStatusComplete("I");
                                isflagComplete = false;
                                complete = false;
                                Log.e("isFromFreshDispenceAdd", "Dispence");
                                if (initialVolumeTotalizer != null) {
                                    //    markOrderDispenseTransactionCompleted("U");
                                    markOrderDispenseTransactionCompleted("I");
                                    btnBill.setEnabled(false);
                                    btnBill.setBackgroundColor(context.getResources().getColor(R.color.md_grey_500));
//                                    dismiss();
                                } else {
                                    Toast.makeText(context, "InitialTotalizer null", Toast.LENGTH_LONG).show();
                                }


                            }
                        }
                    });


                    AlertDialog alertDialog = builder.create();
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                        @Override
                        public void onShow(DialogInterface dialog) {
                            Log.v("ORDER_LIST", orderDeliveryInProgress.getOrder().get(0).getQty());
                            //  Log.d("KAMALqUNATITY", "" + p + String.valueOf(Double.parseDouble(orderDeliveryInProgress.getOrder().get(p).getQty()) - Double.parseDouble(dispensedQuantity)));
                            if (Double.parseDouble(orderDeliveryInProgress.getOrder().get(0).getQty())
                                    - Double.parseDouble(orderDeliveryInProgress.getProgress().getDeliveredData())
                                    - Double.parseDouble(dispensedQuantity) <= 0.99) {
                                if (((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE) != null) {

                                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    Log.v("ORDER_LISTT", orderDeliveryInProgress.getOrder().get(0).getQty());
                                    // dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "button null", Toast.LENGTH_SHORT).show();
                                    Log.v("ORDER_LISTTT", orderDeliveryInProgress.getOrder().get(0).getQty());
                                }

                            }

                        }
                    });


                    alertDialog.show();

                }




                break;

            case R.id.backBtn:
                dismiss();
                break;
        }
    }

    private void print() {
        dataToPrint = new PrintData();
        dataToPrint.setTransaction_type("Order Dispense");
        dataToPrint.setAmount(orderk.getTotal_amount());
        dataToPrint.setLocationName(orderk.getCustomer_name());
        dataToPrint.setCustomer_name(orderk.getFname());
        dataToPrint.setAssets_id(selectedAsset);
        dataToPrint.setAssets_name(selectedAssetName);
        dataToPrint.setFueling_start_time(fuelingStartTime == null ? "NULL" : fuelingStartTime);
        Calendar calender = Calendar.getInstance();
        calender.setTimeZone(TimeZone.getTimeZone("IST"));
        dataToPrint.setFueling_stop_time(fuelingEndTime);
        dataToPrint.setDispense_qty(orderk.getTotal_dispense_qty());
        dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
        dataToPrint.setAsset_other_reading(String.valueOf(assetReading1.getText()));
        dataToPrint.setAsset_other_reading2(String.valueOf(assetReading2.getText()));
        dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
        if(SharedPref.getLoginResponse().getData().get(0).getAtgDataList().isEmpty()) {
            dataToPrint.setAtg_tank_end_reading(atg_end);
        } else {
            if (atgFinalReading == null) {
                dataToPrint.setAtg_tank_end_reading(atg_end);
            } else {
                dataToPrint.setAtg_tank_end_reading(atg_end);
            }
        }
        if (SharedPref.getVolTotlizer() != null) {

            dataToPrint.setVolume_totalizer(finalVolumeTotalizer);
        } else {
            dataToPrint.setVolume_totalizer(finalVolumeTotalizer);
        }
        dataToPrint.setNo_of_event_start_stop("3");
        dataToPrint.setDispensed_in(selectedAssetName);
        dataToPrint.setRfid_status(rfIdTagId.isEmpty() ? "N/A" : rfIdTagId);
        dataToPrint.setTerminal_id(SharedPref.getTerminalID());
        dataToPrint.setBatch_no(batch);
        dataToPrint.setLatitude(currentLat);
        dataToPrint.setLongitude(currentLong);
        dataToPrint.setLocation_reading_1(orderk.getDispense_latitude());
        dataToPrint.setLocation_reading_2(orderk.getDispense_longitude());
        dataToPrint.setGps_status("Bypassed");
        dataToPrint.setDcv_status(String.valueOf(dcv_status));
        dataToPrint.setFlag("U");
        dataToPrint.setTransaction_no(transaction_no);
        dataToPrint.setUnit_price(orderk.getUnit_price());
        dataToPrint.setVehicle_id(SharedPref.getVehicleId());
        dataToPrint.setTransaction_id(orderk.getTransaction_id());
        dataToPrint.setOrder_id(orderk.getLead_number());
        dataToPrint.setOrder_data_time(orderk.getCreated_datatime());
        dataToPrint.setTotal_qty(orderk.getQuantity());
        dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
        dataToPrint.setLocation_id(orderk.getLocation_id());
        dataToPrint.setLocationName(orderk.getLocation_name());
        dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty() ? " Thank You " : SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
        dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
        dataToPrint.setGST(orderk.getGst_percentage()); //Need GST Value Which API will provide it
        dataToPrint.setTotal_amount(orderk.getTotal_amount());
        dataToPrint.setElock_start_reading(asetatgintial_Reading);
        dataToPrint.setElock_end_reading(asetatgfinal_Reading);

        if (dispenseLocalDatabaseHandler.getOfflineOrder()!=null&&SharedPref.getOfflineOrderList()!=null) {
//    list = dispenseLocalDatabaseHandler.getOfflineOrder();
            if(orderk!=null) {
                List<DeliveryInProgress> delivery = new ArrayList<>();
                delivery = SharedPref.getOfflineOrderList();
                delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));
                String qty = dispenseLocalDatabaseHandler.getQuantity(orderk.getTransaction_id());
                Log.d("transaction",""+qty+ ","+orderk.getTransaction_id()+","+ String.format("%.2f", Float.parseFloat(dispensedQuantity) + Float.parseFloat(qty)));
                dispenseLocalDatabaseHandler.updateOfflineData(orderk.getTransaction_id(), String.format("%.2f", Float.parseFloat(dispensedQuantity) + Float.parseFloat(qty)));
                SharedPref.setOffineOrderList(delivery);
            }
            else {
                List<DeliveryInProgress> delivery = new ArrayList<>();
                delivery = SharedPref.getOfflineOrderList();
                delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));
                String qty = dispenseLocalDatabaseHandler.getQuantity(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                Log.d("transaction1",""+qty+ ","+orderk.getTransaction_id()+","+ String.format("%.2f", Float.parseFloat(dispensedQuantity) + Float.parseFloat(qty)));
                dispenseLocalDatabaseHandler.updateOfflineData(orderDeliveryInProgress.getOrder().get(0).getTransactionId(), String.format("%.2f", Float.parseFloat(dispensedQuantity) + Float.parseFloat(qty)));
                SharedPref.setOffineOrderList(delivery);

            }
        }
        printBill(dataToPrint);
        SharedPref.setIsFuelingStarted(false);
        SharedPref.setTotalizerReadingDiff("0");
        printDispenseParams(dataToPrint);
    }

    public String createTransactionID() throws Exception{
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
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
        if (context instanceof SynergyDispenser) {
//            ((SynergyDispenser) context).ClearSale();
        }

        if (context instanceof FreshDispenserUnitActivity) {
            ((FreshDispenserUnitActivity) context).ClearSale();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void markOrderDispenseTransactionCompleted(String flag) {
        try {
               progressDialog = new ProgressDialog(context);
               progressDialog.setMessage("Please wait...");
               progressDialog.show();
                dataToPrint = new PrintData();
                dataToPrint.setTransaction_type("Order Dispense");
                dataToPrint.setAmount(String.format("%.2f", Float.parseFloat(amounttot)));
                dataToPrint.setLocationName(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                dataToPrint.setCustomer_name(orderDeliveryInProgress.getOrder().get(0).getCustomer_name());
                dataToPrint.setAssets_id(selectedAsset);
                dataToPrint.setAssets_name(selectedAssetName);
                dataToPrint.setFueling_start_time(fuelingStartTime == null ? "NULL" : fuelingStartTime);
                dataToPrint.setFueling_stop_time(fuelingEndTime);
                dataToPrint.setDispense_qty(String.format("%.2f", Float.parseFloat(dispensedQuantity)));
                dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
                dataToPrint.setAsset_other_reading(String.valueOf(assetReading1.getText()));
                dataToPrint.setAsset_other_reading2(String.valueOf(assetReading2.getText()));
                dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
                if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().isEmpty()) {
                    dataToPrint.setAtg_tank_end_reading("00.00");
                } else {
                    if (atgFinalReading == null) {
                        dataToPrint.setAtg_tank_end_reading(atg_end);
                    } else {
                        dataToPrint.setAtg_tank_end_reading(atg_end);
                    }
                }
                dataToPrint.setVolume_totalizer(finalVolumeTotalizer);
                dataToPrint.setNo_of_event_start_stop("3");
                dataToPrint.setDispensed_in(selectedAssetName);
                dataToPrint.setRfid_status(rfIdTagId.isEmpty() ? "N/A" : rfIdTagId);
                dataToPrint.setTerminal_id(SharedPref.getTerminalID());
                dataToPrint.setBatch_no(batch);
                dataToPrint.setLatitude(currentLat);
                dataToPrint.setLongitude(currentLong);
                dataToPrint.setLocation_reading_1(orderDeliveryInProgress.getProgress().getCurrentLat());
                dataToPrint.setLocation_reading_2(orderDeliveryInProgress.getProgress().getCurrentLong());
                dataToPrint.setGps_status((orderDeliveryInProgress.getProgress().getLocationBypass() ? "Bypassed" : "OK"));
                dataToPrint.setDcv_status(String.valueOf(dcv_status));
                dataToPrint.setFlag(flag);
                dataToPrint.setTransaction_no(transaction_no);
                dataToPrint.setUnit_price(String.format("%.2f", Float.parseFloat(Rate)));
                dataToPrint.setVehicle_id(SharedPref.getVehicleId());
                dataToPrint.setTransaction_id(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                dataToPrint.setOrder_id(orderDeliveryInProgress.getOrder().get(0).getLead_number());
                dataToPrint.setOrder_data_time(orderDeliveryInProgress.getOrder().get(0).getCreatedDatatime());
                dataToPrint.setTotal_qty(orderDeliveryInProgress.getOrder().get(0).getQty());
                dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
                dataToPrint.setLocation_id(orderDeliveryInProgress.getOrder().get(0).getLocationId());
                dataToPrint.setLocationName(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty() ? " Thank You " : SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
                dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
                dataToPrint.setGST("0"); //Need GST Value Which API will provide it
                dataToPrint.setTotal_amount(String.format("%.2f", Float.parseFloat(dataToPrint.getAmount()) + Float.parseFloat(dataToPrint.getAmount()) * Float.parseFloat(dataToPrint.getGST()) / 100));
                dataToPrint.setElock_start_reading(asetatgintial_Reading);
                dataToPrint.setElock_end_reading(asetatgfinal_Reading);


            printBill(dataToPrint);
            SharedPref.setIsFuelingStarted(false);
            SharedPref.setTotalizerReadingDiff("0");
            printDispenseParams(dataToPrint);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils.isNetworkConnected(context)) {

                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);

                    Log.e("hashmapResponse", getHashMap(dataToPrint).toString());
                    apiInterface.postOrderDelivered(getHashMap(dataToPrint)).enqueue(new Callback<PostOrderDelivered>() {
                        @Override
                        public void onResponse(Call<PostOrderDelivered> call, Response<PostOrderDelivered> response) {
                            Log.e("onResponse", "CALL");

                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            if (response.isSuccessful() && response.body().getSucc()) {
                                try {
                                    List<DeliveryInProgress> delivery = new ArrayList<>();
                                    delivery = SharedPref.getOfflineOrderList();
                                    String qty = dispenseLocalDatabaseHandler.getQuantity(orderDeliveryInProgress.getOrder().get(p).getTransactionId());
                                    dispenseLocalDatabaseHandler.updateOfflineData(orderDeliveryInProgress.getOrder().get(p).getTransactionId(), String.format("%.2f", Float.parseFloat(dispensedQuantity) + Float.parseFloat(qty)));
                                    delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));
                                    if(delivery.get(position).getProgress().getStatus_type().contains("1")){
                                        delivery.get(position).getAssets().get(AssetPostion).setAssetDispenseQty(String.format("%.2f",(Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getAssets().get(AssetPostion).getAssetDispenseQty())+Float.parseFloat(dispensedQuantity))));
                                    }
                                    SharedPref.setOffineOrderList(delivery);
                                    showResponseInDialog(context, response.body().getPublicMsg(), true, false);
                                }
                                catch (Exception e){
                                    tempMethod();
                                    Log.d("threadname2",e.getLocalizedMessage());
                                    showResponseInDialog(context, "Response "+e.getLocalizedMessage(), false, true);

                                }


                            } else {
                                try {
                                    Log.d("threadname3",Thread.currentThread().getName());

                                    tempMethod();

                                    showResponseInDialog(context, "", false, true);
                                }
                                catch (Exception e){
                                    tempMethod();
                                    Log.d("threadname4",Thread.currentThread().getName());
                                    Log.d(TAG, "print2");

                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<PostOrderDelivered> call, Throwable t) {

                            Log.d(TAG, "print3");
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                Log.d(TAG, "print4");
                                tempMethod();
                            }


                        }
                    });
                }
                else {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        Log.d(TAG, "print5");
                        tempMethod();
                    }
                    else {
                        Log.d(TAG, "print6");
                        tempMethod();
                    }
                }
            } }
        catch (NullPointerException e){

        }
        catch (Exception e) {
            tempMethod();
        }
    }

    private void updateStatus() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(context instanceof SynergyDispenser) {
                    dispenseLocalDatabaseHandler.updateProgressOrder(SynergyDispenser.transactionId, "12");
                }
            }
        }, 500);
    }
    public  void tempMethod()
    {
        Log.d(TAG, "print8");


        AsyncTask.execute(new Runnable()
        {
            @Override
            public void run()
            {
                List<DeliveryInProgress> delivery = new ArrayList<>();
                delivery = SharedPref.getOfflineOrderList();
                delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));
                if(delivery.get(position).getProgress().getStatus_type().contains("1")){
                    delivery.get(position).getAssets().get(AssetPostion).setAssetDispenseQty(String.format("%.2f",(Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getAssets().get(AssetPostion).getAssetDispenseQty())+Float.parseFloat(dispensedQuantity))));
                }
                SharedPref.setOffineOrderList(delivery);
                String qty=dispenseLocalDatabaseHandler.getQuantity(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                Log.d("transactionPravin",""+qty+ ","+dispensedQuantity+","+orderDeliveryInProgress.getOrder().get(0).getTransactionId()+","+ String.format("%.2f", Float.parseFloat(dispensedQuantity) + Float.parseFloat(qty)));
                dispenseLocalDatabaseHandler.updateOfflineData(orderDeliveryInProgress.getOrder().get(0).getTransactionId(),String.format("%.2f", Float.parseFloat(dispensedQuantity)+Float.parseFloat(qty)));
                saveOrderDispenseDataInSQLiteLocal(dataToPrint);
                Log.d(TAG, "print9");



            }

        });
        showResponseInDialog(context, "Saved offline successfully", true, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void markFreshDispenseTransactionCompleted(String flag) {
        try {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            dataToPrint = new PrintData();
            dataToPrint.setTransaction_type("Instant Dispense");
            dataToPrint.setAmount(String.format("%.2f", Float.parseFloat(amounttot)));
            dataToPrint.setAssets_id(selectedAsset);
            dataToPrint.setAssets_name(selectedAssetName);
            dataToPrint.setFueling_start_time(fuelingStartTime == null ? "NULL" : fuelingStartTime);
            dataToPrint.setFueling_stop_time(fuelingEndTime);
            dataToPrint.setDispense_qty(String.format("%.2f", Float.parseFloat(dispensedQuantity)));
            dataToPrint.setMeter_reading(String.valueOf(meterReading.getText()));
            dataToPrint.setAsset_other_reading(String.valueOf(assetReading1.getText()));

            dataToPrint.setAsset_other_reading2(String.valueOf(assetReading2.getText()));


            dataToPrint.setAtg_tank_start_reading(String.valueOf(atgStart));
            dataToPrint.setAtg_tank_end_reading(atg_end);
            dataToPrint.setVolume_totalizer(finalVolumeTotalizer);

//            if (SharedPref.getLastFuelDispenserReading() != null) {
//                Log.e("PrintTotlizer", SharedPref.getLastFuelDispenserReading());
//                dataToPrint.setVolume_totalizer(SharedPref.getLastFuelDispenserReading());
//            } else {
//                dataToPrint.setVolume_totalizer("777");
//
//                Log.e("PrintTotlizer", "777");
//
//            }

            dataToPrint.setNo_of_event_start_stop("1");
            dataToPrint.setDispensed_in(selectedAssetName);
            dataToPrint.setTerminal_id(SharedPref.getTerminalID());
            dataToPrint.setBatch_no(batch);
            dataToPrint.setLatitude(currentLat);
            dataToPrint.setLongitude(currentLong);
            dataToPrint.setLocation_reading_1(orderDeliveryInProgress.getProgress().getCurrentLat());
            dataToPrint.setLocation_reading_2(orderDeliveryInProgress.getProgress().getCurrentLong());
            dataToPrint.setRfid_status(rfIdTagId.isEmpty() ? "N/A" : rfIdTagId);
            dataToPrint.setGps_status(String.valueOf(orderDeliveryInProgress.getProgress().getLocationBypass()));
            dataToPrint.setDcv_status(String.valueOf(dcv_status));
            dataToPrint.setFlag(flag);
            dataToPrint.setUnit_price(orderDeliveryInProgress.getProgress().getFuelPrice());
            dataToPrint.setUnit_price(Rate);
            dataToPrint.setVehicle_id(SharedPref.getVehicleId());
            // dataToPrint.setTransaction_no(SharedPref.getTerminalID() + orderDeliveryInProgress.getOrder().get(0).getTransactionId());
            dataToPrint.setTransaction_no(transaction_no);
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
            dataToPrint.setTotal_amount(String.format("%.2f", Float.parseFloat(amounttot) + Float.parseFloat(dataToPrint.getAmount()) * Float.parseFloat(dataToPrint.getGST()) / 100));
            dataToPrint.setElock_start_reading(asetatgintial_Reading);
            dataToPrint.setElock_end_reading(asetatgfinal_Reading);


            printBill(dataToPrint);

            Log.e("TransactionID", SharedPref.getProgressList().get(p).getTransaction_id());
            SharedPref.setIsFuelingStarted(false);
            SharedPref.setTotalizerReadingDiff("0");

            printDispenseParams(dataToPrint);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils.isNetworkConnected(context)) {

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

                                delivery.get(position).getProgress().setDeliveredData(String.format("%.2f", (Float.parseFloat(SharedPref.getOfflineOrderList().get(position).getProgress().getDeliveredData()) + Float.parseFloat(dispensedQuantity))));


//                list.get(p).setDelivered_data(String.format("%.2f", (Float.parseFloat(list.get(p).getDelivered_data()) + Float.parseFloat(dispensedQuantity))));

                                SharedPref.setOffineOrderList(delivery);
                                String qty=dispenseLocalDatabaseHandler.getQuantity(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                                Log.d("transaction4",""+qty+ ","+orderk.getTransaction_id()+","+ String.format("%.2f", Float.parseFloat(dispensedQuantity) + Float.parseFloat(qty)));
                                dispenseLocalDatabaseHandler.updateOfflineData(orderDeliveryInProgress.getOrder().get(0).getTransactionId(),String.format("%.2f", Float.parseFloat(dispensedQuantity)+Float.parseFloat(qty)));
                                showResponseInDialog(context, "Order Successfully updated", true, true);

                            } else {
                                tempMethod();
                                showResponseInDialog(context, "Something went wrong. Please try again...", false, true);

                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                tempMethod();
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
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        tempMethod();
                    }

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
        Log.d("threadname", Thread.currentThread().getName());
        if (context != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(message);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            if (alertDialog != null)
                alertDialog.dismiss();
//        }
            Log.e("Activitynot", "DIALOG TEST" + isSuccess + "showDialog");
            if (isSuccess) {
                clearSALE();


                if (dataToPrint.getFlag().equalsIgnoreCase("I")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (getActivity() != null) {


                                if (!isFreshDispense) {
                                    Log.e("ActivityNot", "normal dispense");
                                    Intent intent = new Intent(requireActivity(), SynergyDispenser.class);
                                    Order order = new Order();
                                    order.setTransaction_id(orderDeliveryInProgress.getOrder().get(0).getTransactionId());
                                    order.setQuantity(orderDeliveryInProgress.getOrder().get(0).getQty());
                                    order.setLocation_id(orderDeliveryInProgress.getOrder().get(0).getLocationId());
                                    order.setLocation_name(orderDeliveryInProgress.getOrder().get(0).getLocationName());
                                    intent.putExtra("orderDetail", order);
                                    //  intent.putExtra("StatusForUpdateAtg", isflagComplete);

                                    intent.putExtra("isFromFreshDispense", false);

                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    // atgHandler2.removeCallbacks(atgRunnable2);
                                    Log.e("ActivityNot", "DispenceNot");
                                    Intent intent = new Intent(requireActivity(), SynergyDispenser.class);
                                    orderDeliveryInProgress.getProgress().setDeliveredData(dispensedQuantity);
                                    intent.putExtra("freshOrder", orderDeliveryInProgress);
                                    intent.putExtra("isFromFreshDispense", true);

                                    startActivity(intent);
                                    getActivity().finish();
                                }
                                // getActivity().finish();
                            } else {

                                Log.e("ActivityNot", "nullmanoj");
                                click.ClickK(complete);
                                Log.e("listnerADdreading", String.valueOf(orderState));
                                //  DispenserUnitActivity.lisner.setClick(orderState);
                            }
                        }
                    }, 1000);

                } else {
                    click.ClickK(complete);
                }
            } else {
                if (SharedPref.getLastFuelDispenserReading() == null) {
                    Toast.makeText(context, "Get TotlizerId", Toast.LENGTH_SHORT).show();
                    // DispenserUnitActivity.clickTOtlizer.ClickTOtlizer(true);
                }
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
            jsonObject.put("asset_other_reading_2",  dataToPrint.getMeter_reading());
            jsonObject.put("asset_mriv",  dataToPrint.getAsset_other_reading());
            jsonObject.put("asset_remark",  dataToPrint.getAsset_other_reading2());
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
            Log.d(TAG,"PostOrderParams==" +jsonObject.toString());
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
        hashMap.put("asset_other_reading_2", dataToPrint.getMeter_reading());
        hashMap.put("asset_mriv", dataToPrint.getAsset_other_reading());
        hashMap.put("asset_remark", dataToPrint.getAsset_other_reading2());
        hashMap.put("asset_other_reading", dataToPrint.getAsset_other_reading());
        hashMap.put("atg_tank_start_reading", dataToPrint.getAtg_tank_start_reading());
        hashMap.put("atg_tank_end_reading", dataToPrint.getAtg_tank_end_reading());
        hashMap.put("initial_volume_totalizer", initialVolumeTotalizer);
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
        hashMap.put("order_id", dataToPrint.getOrder_id() == null ? new Date().getTime() + "" : (dataToPrint.getOrder_id().isEmpty() ? new Date().getTime() + "" : dataToPrint.getOrder_id()));
        hashMap.put("duty_id", dataToPrint.getDuty_id());
        hashMap.put("location_id", dataToPrint.getLocation_id());
        hashMap.put("footer_message", dataToPrint.getFooter_message());
        hashMap.put("gst_percentage", dataToPrint.getGST());
        hashMap.put("total_amount", dataToPrint.getTotal_amount());
        hashMap.put("volume_totalizer", dataToPrint.getVolume_totalizer());
        hashMap.put("asset_intial_level", dataToPrint.getElock_start_reading());
        hashMap.put("asset_final_level", dataToPrint.getElock_start_reading());
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

                        orderModel.setInitial_volume_totalizer(initialVolumeTotalizer);
                        orderModel.setStatus("0");
                        orderModel.setTotal_amount(amounttot);
                        orderModel.setElock_start_reading(printData.getElock_start_reading());
                        orderModel.setElock_end_reading(printData.getElock_end_reading());
                        orderModel.setStatus("0");
                        Log.d("Locally", "Pre Data saved Locally with TransactionId: " + printData.getTransaction_id());
                        dispenseLocalDatabaseHandler.addOrderDispenseData(orderModel);


                    } catch (Exception e) {

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
                        orderModel.setTotal_amount(amounttot);
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
                double gstAmount = Double.parseDouble(amounttot) * gstPercentage / 100;
                printData.setGST(gstPercentage + "");
                printData.setTotal_amount(String.valueOf(Float.parseFloat(amounttot) + gstAmount));
                printTransactionBill(printData, gstPercentage, gstAmount);
                //Added/Updated above lines by Laukendra on 15-11-19
            }
        } else {
            Toast.makeText(context, "Print data is empty", Toast.LENGTH_SHORT).show();
        }
    }

    protected void printTransactionBill(PrintData printData, int gstPercentage, double gstAmount) {
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
            if (isFromFreshDispense) {
                Log.e("isFromFreshDispenceAdd", "FreshDispence");
                printCustom("INSTANT DISPENSE", 1, 1);
            } else {
                Log.e("isFromFreshDispenceAdd", "Dispence");
                printCustom("DISPENSE", 1, 1);
            }
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

            printNewLine();
            printCustom("RO Name: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getName(), 0, 0);
            printCustom("GSTIN: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getGstNo(), 0, 0);
            printCustom("Bowser No: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getRegNo(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("ORDER DETAILS", 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

            try {
                printCustom("Order Date: " + printData.getOrder_data_time(), 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            printCustom("Order ID: " + printData.getOrder_id(), 0, 0);
            printCustom("Customer Name: " + printData.getCustomer_name(), 0, 0);
            printCustom("Project Name: " + printData.getLocationName(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("DELIVERY DETAILS", 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

            printCustom("Latitude: " + printData.getLatitude(), 0, 0);
            printCustom("Longitude: " + printData.getLongitude(), 0, 0);
            printCustom("GPS Status: " + printData.getGps_status(), 0, 0);
            printCustom("Terminal ID: " + printData.getTerminal_id(), 0, 0);
            printCustom("Batch no: " + printData.getBatch_no(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("PRODUCT: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getProductName().toUpperCase(), 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

            printCustom("Equipment Reg no: " + assetRegNo, 0, 0);
            printCustom("Equipment Name: " + printData.getAssets_name(), 0, 0);
//            printCustom("RFID Status: " + printData.getRfid_status(), 0, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("Start Fueling Time: " + printData.getFueling_start_time(), 0, 0);
            printCustom("End Fueling Time: " + printData.getFueling_stop_time(), 0, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("Txn No: " + printData.getTransaction_no(), 1, 0);
            printCustom("Volume (in Lts): " + printData.getDispense_qty(), 1, 0);
//            printCustom("Rate (in INR/Litre): " + printData.getUnit_price(), 1, 0);
//            printCustom("Amount (in INR): " + printData.getAmount(), 1, 0);
//
//            printCustom("GST (in INR) (" + gstPercentage + "%): " + String.format("%.2f", gstAmount), 1, 0);
//            printCustom("Total Amount (in INR): " + String.format("%.2f", Double.parseDouble(printData.getAmount()) + gstAmount), 1, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("ODO Meter Reading: " + printData.getMeter_reading(), 0, 0);
            printCustom("MRIV No: " + printData.getAsset_other_reading(), 0, 0);
            printCustom("Remarks: " + printData.getAsset_other_reading2(), 0, 0);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("Delivered by:" + printData.getDeliveredBy(), 0, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

            printNewLine();
            printCustom("Save Fuel Waste", 0, 1);
            printCustom("Save Nation", 0, 1);

            printCustom(new String(new char[32]).replace("\0", "="), 0, 1);

            printCustom(new String(new char[32]).replace("\0", ""), 0, 1);
            printCustom(new String(new char[32]).replace("\0", ""), 0, 1);
            printNewLine();

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
            dismiss();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



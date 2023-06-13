package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.WaveLoadingView;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Command285Queue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server285_ManholeLock;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.AddreadingPrint;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.CompartmentListDialog;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Server285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.ServerATG285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.LogToFile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.OnCompartmentOperation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.ATGData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.CompartmentInfo;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.FranchiseInfo;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.FranchiseInfoRec;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.FranchiseeList;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.LocationUtil;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveFuelActivity extends AppCompatActivity implements View.OnClickListener, RouterResponseListener {

    private int atgCount = 0;
    InetAddress inetAddress1;
    Client client2;
    private String TAG = ReceiveFuelActivity.class.getSimpleName();
    private TextView connection_maintainedState;
    private boolean isIntialATG;
    private String responseOf = "";
    private String initialReadingCompartment1;
    private String initialReadingCompartment2;
    private String endReadingCompartment1;
    int atgcount = 0;
    int atgcount2 = 0;
    int test = 0;
    int test2 = 0;
    String fCode = "", fName = "", Gstin = "", startTime = "", endTime = "", Latitude = "", Longitude = "", qnty = "", price = "", productName = "", productId = "";
    private List<FranchiseeList.Datum> fNameList = new ArrayList<>();

    private String endReadingCompartment2;
    private String selectedAtgSerialNo;
    private TextView dispenserVolumeRefiling, tank1Balance, tank2Balance;
    private TextView dispenserCompartmentAtgVolume;
    private TextView startTankCalibration;
    private boolean isCommandALLowed = false;
    private TextView timerText;
    private Runnable getCommandsRunnable, getATGCommandsRunnable;
    private ScheduledExecutorService executor, executorAtg;
    private CardView startTankCalibrationCard;
    private int selectedCompartment;
    private String atgData;
    private boolean isstart = false;
    private double recevingBeforeVolume = 0;
    private int count = 0;
    private static final long timerInMiles = 60000000;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeftInMiles = timerInMiles;
    private String compartmentName1 = "";
    private ProgressDialog progressBar;
    private List<FranchiseInfo.FranchiseVehicleItem> vehicleItems = new ArrayList<>();
    private List<FranchiseInfo.FranchiseDetailItem> franchiseDetail = new ArrayList<>();
    private Timer t;
    private TextView tvDispenserLocationId, tvDispenserCompartmentId, tvDispenserSelectCompartment, tvDispenserVolumeRefueling, tvVolumeCompartment, tvReconnect, tvStartReceiving;
    private TextView tvTotalBalanceAvailable, tvManhole1LockStatus, tvManhole2LockStatus, tvLatitude, tvLongitude, tvByPassATG, tvByPassGPS;
    private WaveLoadingView waveLoadingViewTank1, waveLoadingViewTank2;
    private String atgSerialNoTank1, atgSerialNoTank2, intialATGReading, lastATGReading;
    private LinkedHashMap<String, Object> atgLinkedHashMapTank1, atgLinkedHashMapTank2;
    private ArrayList<CompartmentInfo> compartmentInfoArrayList;
    private double tank1MaxVolume = 0f, tank2MaxVolume = 0f, totalAvailableVolume = 0f;
    private boolean isATGPort3Selected, isATGPort4Selected;
    private Runnable getATG1Runnable, getATG2Runnable;
    private ScheduledExecutorService executorAtg1, executorAtg2, executeCommand;
    private String ATG_Status;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_receive_fuel);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        connection_maintainedState = findViewById(R.id.pumpStatus);
        tank1Balance = findViewById(R.id.tank1Balance);
        tank2Balance = findViewById(R.id.tank2Balance);
        startTankCalibration = findViewById(R.id.tvStartReceiving);
        startTankCalibrationCard = findViewById(R.id.startTankCalibration);
        t = new Timer();
        tvDispenserLocationId = findViewById(R.id.tvDispenserLocationId);
        tvDispenserCompartmentId = findViewById(R.id.tvDispenserCompartmentId);
        tvDispenserSelectCompartment = findViewById(R.id.tvDispenserSelectCompartment);

        tvDispenserVolumeRefueling = findViewById(R.id.tvDispenserVolumeRefueling);
        tvVolumeCompartment = findViewById(R.id.tvVolumeCompartment);
        tvReconnect = findViewById(R.id.tvReconnect);
        tvStartReceiving = findViewById(R.id.tvStartReceiving);
        timerText = findViewById(R.id.timerText);

        waveLoadingViewTank1 = findViewById(R.id.waveLoadingViewTank1);
        waveLoadingViewTank2 = findViewById(R.id.waveLoadingViewTank2);

        tvTotalBalanceAvailable = findViewById(R.id.tv_total_available_balance);
        tvManhole1LockStatus = findViewById(R.id.tv_manhole_1_lock_status);
        tvManhole2LockStatus = findViewById(R.id.tv_manhole_2_lock_status);
        tvLatitude = findViewById(R.id.tv_latitude);
        tvLongitude = findViewById(R.id.tv_longitude);
        tvByPassATG = findViewById(R.id.tv_bypass_atg);
        tvByPassGPS = findViewById(R.id.tv_bypass_gps);

        waveLoadingViewTank1.setOnClickListener(this);
        waveLoadingViewTank2.setOnClickListener(this);
        tvDispenserSelectCompartment.setOnClickListener(this);
        tvReconnect.setOnClickListener(this);
        tvStartReceiving.setOnClickListener(this);

        if (SharedPref.getLoginResponse().getFranchise_data().get(1).franchise_bypass.equalsIgnoreCase("True"))
            tvByPassGPS.setText("GPS BYPASSED");
        else
            Toast.makeText(ReceiveFuelActivity.this, "GPS not byPassed", Toast.LENGTH_SHORT).show();

        showDialogForTakeFranchiseCode("Enter Franchisee Code.");

        //ATG
        try {
            compartmentInfoArrayList = new ArrayList<>();
            for (ATGData atgData : SharedPref.getLoginResponse().getData().get(0).atgDataList) {
                CompartmentInfo compartmentInfo = new CompartmentInfo();
                compartmentInfo.setAtgAutoId(atgData.getData_auto_id());
                compartmentInfo.setAtgVehicleId(atgData.getData_vehicle_id());
                compartmentInfo.setAtgSerialNo(atgData.getData_atg_serial_no());
                compartmentInfoArrayList.add(compartmentInfo);
            }

            if (SharedPref.getLoginResponse().getData().get(0).atgDataList.size() > 1) {
                String string1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
                if (string1 != null) {
                    atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
                    // atgSerialNoTank1 = "23872"; //Testing value
                    JSONObject jsonObject1 = new JSONObject(string1);
                    atgLinkedHashMapTank1 = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);
                }

                try {
                    Object obj = atgLinkedHashMapTank1.entrySet().toArray()[atgLinkedHashMapTank1.size() - 1];
                    LinkedHashMap.Entry linkedTreeMap = (LinkedHashMap.Entry) (obj);
                    JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap.getValue()).getAsJsonObject();
                    if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
                        tank1MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                    }
                } catch (Exception je) {
                    je.printStackTrace();
                }
                Log.d("Tank1AtgMaxVolume", tank1MaxVolume + "");
                Log.d("Tank1MaxVolume", (tank1MaxVolume = tank1MaxVolume * .95) + "");

//                for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
//                    try {
//                        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
//                        JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
//                        if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                            tank1MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                        }
//                    } catch (Exception je) {
//                        je.printStackTrace();
//                    }
//                }


                String string2 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(1).getData_volume();
                if (string2 != null) {
                    atgSerialNoTank2 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(1).getData_atg_serial_no();
                    //   atgSerialNoTank2 = "23872"; //Testing value
                    JSONObject jsonObject2 = new JSONObject(string2);
                    atgLinkedHashMapTank2 = new Gson().fromJson(jsonObject2.toString(), LinkedHashMap.class);
                }

                try {
                    Object obj = atgLinkedHashMapTank2.entrySet().toArray()[atgLinkedHashMapTank2.size() - 1];
                    LinkedHashMap.Entry linkedTreeMap = (LinkedHashMap.Entry) (obj);
                    JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap.getValue()).getAsJsonObject();
                    if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
                        tank2MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                    }
                } catch (Exception je) {
                    je.printStackTrace();
                }
                Log.d("Tank2AtgMaxVolume", tank2MaxVolume + "");
                Log.d("Tank2MaxVolume", (tank2MaxVolume = tank2MaxVolume * .95) + "");

            } else if (SharedPref.getLoginResponse().getData().get(0).atgDataList.size() == 1) {
                waveLoadingViewTank2.setCenterTitle("N/A");
                waveLoadingViewTank2.setOnClickListener(null);
                String string = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
                if (string != null) {
                    atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
                    // atgSerialNoTank1 = "23872"; //Testing value
                    JSONObject jsonObject1 = new JSONObject(string);
                    atgLinkedHashMapTank1 = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);
                }
                try {
                    Object obj = atgLinkedHashMapTank1.entrySet().toArray()[atgLinkedHashMapTank1.size() - 1];
                    LinkedHashMap.Entry linkedTreeMap = (LinkedHashMap.Entry) (obj);
                    JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap.getValue()).getAsJsonObject();
                    if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
                        tank1MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                    }
                } catch (Exception je) {
                    je.printStackTrace();
                }
                Log.d("Tank1AtgMaxVolume", tank1MaxVolume + "");
                Log.d("Tank1MaxVolume", (tank1MaxVolume = tank1MaxVolume * .95) + "");
            } else {
                // create an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name");
                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.recieve_layout, null);
                builder.setView(customLayout);
                // add a button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send data from the AlertDialog to the Activity
                        EditText editText = customLayout.findViewById(R.id.editText);

                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        } catch (JSONException e) {
            Log.e("ATG-Table-EXc", "Occured");
            e.printStackTrace();
        }

        //  connectToSynergyWifi();
        connect232Port();

        connection_maintainedState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    if (s.toString().contains("Connecting")) {
                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(ReceiveFuelActivity.this, R.color.md_red_300));


                    } else {
//                        progressBar = new ProgressDialog(ReceiveFuelActivity.this);
//                        progressBar.setMessage("Checking Compartment Values");
//                        progressBar.show();
                        //checkATGInitialValues();
                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(ReceiveFuelActivity.this, R.color.md_green_400));
                    }

                }
            }
        });


        // TODO: 2/22/2019 Start Executor for every Two Seconds to check Current Dispenser State

        getCommandsRunnable = new Runnable() {
            public void run() {
                //Added by Laukendra on 30-12-19
                // checkForMainHoleStatus();

            }
        };

        getATG1Runnable = new Runnable() {
            public void run() {
                getATG1Data();
            }
        };
//        executeCommand=Executors.newScheduledThreadPool(1);
//        executeCommand.scheduleAtFixedRate(getCommandsRunnable,1,2,TimeUnit.SECONDS);


        executorAtg1 = Executors.newScheduledThreadPool(1);
        executorAtg1.scheduleAtFixedRate(getATG1Runnable, 7, 7, TimeUnit.SECONDS);

//        getATG2Runnable = new Runnable() {
//            public void run() {
//                getATG1Data();
//            }
//        };
//        executorAtg2 = Executors.newScheduledThreadPool(1);
//        executorAtg2.scheduleAtFixedRate(getATG2Runnable, 1, 1, TimeUnit.SECONDS);
        //need to call these following lines when start Receiving Fuel
        //executorAtg = Executors.newScheduledThreadPool(1);
        //executorAtg.scheduleAtFixedRate(getCommandsRunnable, 0, 1, TimeUnit.SECONDS);
    }

    private void getgetFranchiseDetail(String franchiseId) {

        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        vehicleItems.clear();
        franchiseDetail.clear();
        apiInterface.getFranchiseDetail(SharedPref.getLoginResponse().getVehicle_data().get(0).getFranchiseeId(), SharedPref.getVehicleId()).enqueue(new Callback<FranchiseInfoRec>() {
            @Override
            public void onResponse(Call<FranchiseInfoRec> call, Response<FranchiseInfoRec> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSucc()) {
                        fName = response.body().getData().getFranchiseDetail().get(0).getName();
                        productId = response.body().getData().getFranchiseDetail().get(0).getProductId();
                        Log.e("product", "kam" + productId);
                        Gstin = response.body().getData().getFranchiseDetail().get(0).getGstNo();
                        Log.e("response of the api", response.toString());

                    } else {
                        Toast.makeText(ReceiveFuelActivity.this, "" + response.body().getErrCodes().get(0).toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<FranchiseInfoRec> call, Throwable t) {
                Log.e("failuew", "faile");
            }
        });
    }

    public void getATG1Data() {
        Log.e("AtgKamal", SharedPref.getMakeOfAtg());
        Log.e("getAtg", "getAtg");
        if (SharedPref.getMakeOfAtg().contains("OPW")) {
            if (ServerATG285.getSocket() != null) {
                if (ServerATG285.getAsyncServer().isRunning()) {
                    if (!atgSerialNoTank2.isEmpty()) {
                        //if (!isATGPort4Selected)
                        ListenATG1();
                        ReadATG1();
                    }
                }
            }
        } else {

            Log.e("AtgKamal2", SharedPref.getMakeOfAtg());
            if (!atgSerialNoTank1.isEmpty()) {

                Log.e("AtgKamal3", SharedPref.getMakeOfAtg());
                //if (!isATGPort3Selected)


//                        Timer timer = new Timer();
//                        timer.scheduleAtFixedRate(
//                                new java.util.TimerTask() {
//                                    @Override
//                                    public void run() {
                ReadATG1();

//                                    }
//                                },
//                                2000, 2000);


            }
        }
    }

    public void getATG2Data() {
        if (ServerATG285.getSocket() != null) {
            if (ServerATG285.getAsyncServer().isRunning()) {
                if (!atgSerialNoTank2.isEmpty()) {
                    if (!isATGPort4Selected)
                        ListenATG2();
                    ReadATG2();

                }
            }
        }
    }

    public void ListenATG1() {
        client2.writeData(Commands.LISTEN_ATG_2_285.toString().getBytes());
    }


    private void OpenPrinterDailog() {
        Bundle bundle = new Bundle();
        bundle.putString("franchiseName", fName);
        bundle.putString("product", "Diesal");
        bundle.putString("productId", productId);
        bundle.putString("tankId", fCode);
        bundle.putString("franchiseName", fName);
        bundle.putString("GSTN", Gstin);
        Log.e("kamalK", fName + "," + productName + "," + productId + "," + fCode);

        // bundle.putString("Refueller", String.valueOf(" " + tvCurrentFuelDispensedQnty.getText()));
        bundle.putString("StartTime", startTime);
        Log.e("kamtime", startTime + "," + endTime);
        bundle.putString("endTime", endTime);
        bundle.putString("Latitude", Latitude);
        bundle.putString("Longitude", Longitude);
        bundle.putString("GPS_Status", Longitude);
        bundle.putString("Lock_Status", Longitude);
        bundle.putString("ATG_Status", ATG_Status);
        bundle.putString("FuelQty", qnty);
        bundle.putString("atgStart", " " + intialATGReading);
        bundle.putString("atgEnd", lastATGReading);
        bundle.putString("recieveQty", tank1Balance.getText().toString());
        Log.e("Resultk", qnty + "," + intialATGReading + "," + tank1Balance + "," + lastATGReading);
        try {
            bundle.putString("Mismatch", "" + (Double.parseDouble(tank1Balance.getText().toString()) - Double.parseDouble(qnty)));
            Log.e("mismatchKam", "ram" + (Double.parseDouble(qnty) - Double.parseDouble(tank1Balance.getText().toString()) / 100));
            bundle.putString("range", "" + (Double.parseDouble(qnty) - Double.parseDouble(tank1Balance.getText().toString())));
            bundle.putString("ammount", "" + (Double.parseDouble(tank1Balance.getText().toString()) * Double.parseDouble(price)));
        } catch (Exception e) {

        }

        bundle.putString("price", "" + price);
        AddreadingPrint addReadingsDialog = new AddreadingPrint();
        addReadingsDialog.setCancelable(false);
        addReadingsDialog.setArguments(bundle);
        addReadingsDialog.show(getSupportFragmentManager(), AddreadingPrint.class.getSimpleName());
    }

    public void ListenATG2() {
        send285ATGCommand(Commands.LISTEN_ATG_2_285.toString());
    }

    public void ReadATG1() {
        Log.e("AtgKamal4", SharedPref.getMakeOfAtg());
        if (SharedPref.getMakeOfAtg().contains("OPW")) {
            if (ServerATG285.getSocket() != null) {
                if (ServerATG285.getAsyncServer().isRunning()) {
                    if (!atgSerialNoTank1.isEmpty()) {
                        //if (!isATGPort4Selected)

                        send285ATGCommand("M" + atgSerialNoTank1+"\n\r"); //This is atg value/id
                        Log.d("CommandReadAtgPort4", "M" + atgSerialNoTank1);
                    }
                }
            }
        } else {
            Log.e("AtgKamal5", SharedPref.getMakeOfAtg());

            client2.writeData(hexStringToByteArray(Commands.FINETEK_ATG_LEVEL.toString()));
            //send285ATGshortCommand("010310070002710A");String[]{Commands.FINETEK_ATG_LEVEL.toString()};
            ////
            ////            new CammondQueueAtg(readAtg, new OnAllCommandCompleted() {
            ////                @Override
            ////                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
            ////                    if (lastResponse != null) {
            ////                        Log.e("responseAtgK1", lastResponse);
            ////                    } else {
            ////                        Log.e("responseAtgK", "null");
            ////                    }
            ////                }
            ////
            ////                @Override
            ////                public void onAllCommandCompleted(int currentCommand, String response) {
            ////                    Log.e("responseAtgK2", response);
            ////                    response.contains("0103");
            ////                    String respone1 = response.substring(6, 8);
            ////                    String respone2 = response.substring(8, 10);
            ////                    String respone3 = response.substring(10, 12);
            ////                    String respone4 = response.substring(12, 14);
            ////
            ////
            ////                    float myFloat = Float.intBitsToFloat((int) Long.parseLong(respone3 + respone4 + respone1 + respone2, 16));
            ////                    Log.e("atgResponse", "" + myFloat + "," + respone1 + respone2 + respone3 + respone4);
            ////
            ////                    float tank1AtgReading = Float.parseFloat(String.valueOf(myFloat).substring(0, String.valueOf(myFloat).indexOf(".") + 2));
            ////
            ////                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
            ////                    for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
            ////                        atgcount = test;
            ////                        if (atgcount == test - 10) {
            ////                            test = atgCount;
            ////                            return;
            ////                        } else {
            ////                            atgCount--;
            ////                            try {
            ////                                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
            ////                                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
            ////                                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
            ////                                    float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
            ////                                    if (tableAtgReading == tank1AtgReading) {
            ////
            ////                                        double tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
            ////                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");
            ////
            ////                                        double volumeDifference = 0;
            ////                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");
            ////
            ////                                        tank1Volume = tank1Volume + volumeDifference;
            ////                                        int indexd = String.valueOf(tank1Volume).indexOf(".");
            ////                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + String.valueOf(tank1Volume).substring(0, indexd + 3));
            ////                                        SharedPref.setAvailablevolume(String.valueOf(tank1Volume).substring(0, indexd + 3));
            ////                                        Log.d("VolumeAfterTempTank1", tank1Volume + "," + "kam" + NavigationDrawerActivity.availableBalance.getText().toString());
            ////                                        if (tank1MaxVolume > 0) {
            ////                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
            ////                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
            ////                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
            ////                                            // waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
            ////                                        }
            ////                                        //waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
            ////                                        double totalAvailableVolume = tank1Volume;
            //////                                                                balanceQunty = totalAvailableVolume;
            ////                                        tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
            //////                                        atgData.postValue((String.format("%.2f", (totalAvailableVolume))));
            ////
            ////
            ////                                    } else if (tableAtgReading < tank1AtgReading) {
            ////                                        preAtgReading = tableAtgReading;
            ////                                        preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
            ////                                    } else if (tableAtgReading > tank1AtgReading) {
            ////
            ////                                        postAtgReading = tableAtgReading;
            ////                                        postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
            ////                                        //Calculate Mean Volume of Tank
            ////                                        meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank1AtgReading - preAtgReading) + preAtgVolume;
            ////
            ////                                        double tank1Volume = meanAtgVolume;
            ////                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");
            ////
            ////                                        double volumeDifference = 0;
            ////                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");
            ////
            ////                                        tank1Volume = tank1Volume + volumeDifference;
            ////                                        Log.d("VolumeAfterTempTank1", tank1Volume + "");
            ////
            ////                                        int indexd = String.valueOf(tank1Volume).indexOf(".");
            ////                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + String.valueOf(tank1Volume).substring(0, indexd + 3));
            ////                                        SharedPref.setAvailablevolume(String.valueOf(tank1Volume).substring(0, indexd + 3));
            ////                                        if (tank1MaxVolume > 0) {
            ////                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
            ////                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
            ////                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
            ////                                            //  waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
            ////                                        }
            ////                                        // waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
            ////                                        double totalAvailableVolume = tank1Volume;
            //////                                        atgData.postValue((String.format("%.2f", (totalAvailableVolume))));
            ////                                        tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
            ////                                        break;
            ////                                    }
            ////                                }
            ////                            } catch (Exception je) {
            ////                                je.printStackTrace();
            ////                            }
            ////                        }
            ////                    }
            ////                }
            ////            }).doCommandChaining();
//            String[] readAtg = new
        }
    }

    public void ReadATG2() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                send285ATGCommand("M"+atgSerialNoTank2); //This is atg value/id
//                Log.d("CommandReadAtgPort4", "M"+atgSerialNoTank2);
//            }
//        });

//        Timer t=new Timer();
//        TimerTask timerTask=new TimerTask() {
//            @Override
//            public void run() {
        send285ATGCommand("M" + atgSerialNoTank2); //This is atg value/id
        Log.d("CommandReadAtgPort4", "M" + atgSerialNoTank2);
//            }
//        };
//        t.scheduleAtFixedRate(timerTask,20000,20000);
    }


    private void checkATGInitialValues() {
        String[] atgCommands = new String[]{Commands.LISTEN_ATG_1_285.toString(), atgData};
        new Command285Queue(atgCommands, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.d(TAG, " 285 ATG queue empty" + isEmpty + " " + lastResponse);
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                Log.d(TAG, "285 ATG Command: " + currentCommand + ", Response: " + response);

            }
        }).doCommandChaining();

    }


    private void sendToast(String message) {
        ((AppCompatActivity) ReceiveFuelActivity.this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ReceiveFuelActivity.this, message + " ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private void checkForMainHoleStatus() {
//      progressBar=new ProgressDialog(ReceiveFuelActivity.this);
//        progressBar.setMessage("Checking MainHole status");
//        High input=Mainhole Open
//        Low input=Mainhole Close //added by Laukendra
        Log.d("checkPoint", "point");
        String[] dd = new String[]{Commands.CHECK_MANHOLE_CLOSED_RFID1_1.toString(), Commands.CHECK_MANHOLE_CLOSED_RFID2_1.toString()};
        new Command285Queue(dd, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.d("checkManholeOpen", " 285 ManholeCheck queue empty" + isEmpty + " " + lastResponse);

                //  progressBar.dismiss();
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                Log.d("Response 28ManholeCheck", "285 ManholeCheck Command: " + currentCommand + ", Response: " + response);
                if (response.toLowerCase().contains("low") || response.toLowerCase().contains("high")) {
                    responseOf = response;

                    if (responseOf.contains("GI0")) {
                        String manhole1[] = responseOf.split("GI0");
                        Log.d("subString", "" + manhole1.length + manhole1[0] + "," + manhole1[1]);
                        if (responseOf.contains("GI01INPUT 1 IS HIGH")) {
                            tvManhole1LockStatus.setText("OPEN");
                        } else if (responseOf.contains("GI01INPUT 1 IS LOW")) {
                            tvManhole1LockStatus.setText("LOCKED");
                        }
                        if (responseOf.contains("GI02INPUT 2 IS HIGH")) {
                            tvManhole2LockStatus.setText("OPEN");
                        } else if (responseOf.contains("GI02INPUT 2 IS LOW")) {
                            tvManhole2LockStatus.setText("LOCKED");
                        }
                    }
                } else {
                    checkForMainHoleStatus();
                }
//                progressBar.dismiss();

            }
        }).doCommandChaining();

    }


    private void showStartDialog(Commands command) {


        if (compartmentName1.equalsIgnoreCase("1")) {

            send285Command(command.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveFuelActivity.this);
            builder.setMessage("Open MainHole for " + tvDispenserCompartmentId.getText().toString() + " to start receiving. Press start when ready.");
            builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    count++;

                    startTime = String.valueOf(Calendar.getInstance().getTime());
                    startTimer();
                    isstart = true;
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            isCommandALLowed = true;
//                            sendToast("Checking For Values");
//                        }
//                    }, 5000);
                    startTankCalibration.setText(startTankCalibration.getText().toString().replace("Start", "Stop"));
                    startTankCalibrationCard.setCardBackgroundColor(ContextCompat.getColor(ReceiveFuelActivity.this, R.color.md_red_400));

                }
            });

            builder.setNegativeButton("Choose Other Compartment", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isCommandALLowed = false;
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (compartmentName1.equalsIgnoreCase("2") && responseOf.contains("GI02INPUT 2 IS HIGH")) {

            // showCompartmentListDialog();
            send285Command(command.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveFuelActivity.this);
            builder.setMessage("Open MainHole for " + tvDispenserCompartmentId.getText().toString() + " to start receiving. Press start when ready.");
            builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isstart = true;
                    count++;
                    startTimer();
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            isCommandALLowed = true;
//                            sendToast("Checking For Values");
//                        }
//                    }, 5000);
                    startTankCalibration.setText(startTankCalibration.getText().toString().replace("Start", "Stop"));
                    startTankCalibrationCard.setCardBackgroundColor(ContextCompat.getColor(ReceiveFuelActivity.this, R.color.md_red_400));

                }
            });

            builder.setNegativeButton("Choose Other Compartment", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isCommandALLowed = false;
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(this, "Open ManHole ", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkConnection() {
        if (Server285.getSocket() == null) {
            Toast.makeText(ReceiveFuelActivity.this, "Please Check Your Connection With Wifi Router and Retry.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean checkConnection285ManHole() {
        if (Server285_ManholeLock.getSocket() == null) {
            Toast.makeText(ReceiveFuelActivity.this, "Please Check Your Connection With Wifi Router and Retry.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void connectToSynergyWifi() {
        stopServer();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Server285("192.168.1.103", 14306, ReceiveFuelActivity.this);
                    new ServerATG285("192.168.1.103", 54310, ReceiveFuelActivity.this, atgSerialNoTank1, atgSerialNoTank2);
//                    new Server285_ManholeLock("192.168.1.103", 54311, ReceiveFuelActivity.this);
//                    setMessage("Connecting...");
                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(ReceiveFuelActivity.this, "Something Went Wrong With Server", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogForTakeFranchiseCode(String title) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.selected_asset_qnty_dialog, null);
        final EditText et_qnty = alertLayout.findViewById(R.id.et_qnty);
        final TextView done = alertLayout.findViewById(R.id.done);

        AlertDialog.Builder alert = new AlertDialog.Builder(ReceiveFuelActivity.this);
        alert.setTitle(title);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click", "click");
                if (et_qnty.getText().toString().length() <= 0) {
                    Toast.makeText(ReceiveFuelActivity.this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (SharedPref.getLoginResponse().franchise_data.get(1).franchise_bypass.equalsIgnoreCase("false")) {
                        if (!SharedPref.getLoginResponse().getVehicle_data().get(0).getAtg_bypass()) {
                            //If ATG is not Bypassed then implement logic for ATG here
                            ATG_Status = "Enable";
                            dialog.dismiss();
                            getgetFranchiseDetail(et_qnty.getText().toString());
                        } else {
                            ATG_Status = "bypass";
                            tvByPassATG.setText("ATG ByPassed");
                            Toast.makeText(ReceiveFuelActivity.this, "ATG ByPassed", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ReceiveFuelActivity.this, "GPS ByPassed", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        dialog.show();
    }

    private void showCompartmentListDialog() {
        CompartmentListDialog compartmentListDialog = new CompartmentListDialog();
        compartmentListDialog.setCompartmentListener(new OnCompartmentOperation() {
            @Override
            public void OnCompartmentSelected(String compartmentName, String atgSerialNo, CompartmentInfo compartmentInfo, String command) {
                tvDispenserCompartmentId.setText("Compartment:" + compartmentName);
                compartmentName1 = compartmentName;
                selectedAtgSerialNo = compartmentInfo.getAtgSerialNo();

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.selected_asset_qnty_dialog, null);
                final EditText et_qnty = alertLayout.findViewById(R.id.et_qnty);
                final TextView done = alertLayout.findViewById(R.id.done);

                AlertDialog.Builder alert = new AlertDialog.Builder(ReceiveFuelActivity.this);
                //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
                alert.setTitle("Enter Quantity For this Compartment:" + compartmentName);  //Added by Laukendra on 15-11-19
                alert.setView(alertLayout);
                alert.setCancelable(false);
                AlertDialog dialog = alert.create();
                alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        showCompartmentListDialog();
                    }
                });

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qnty = et_qnty.getText().toString();
                        if (qnty.length() <= 0) {
                            Toast.makeText(ReceiveFuelActivity.this, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            try {
                                if (SharedPref.getLoginResponse().getVehicle_data().get(0).getAtg_bypass()) {
                                    tvDispenserVolumeRefueling.setText(qnty);
                                    SharedPref.setRecieveFuel(tvDispenserVolumeRefueling.getText().toString());
                                    tvByPassATG.setText("BYPASSED ATG");
                                    dialog.dismiss();
                                } else {
                                    if (compartmentName.equalsIgnoreCase("1")) {
                                        if (tank1MaxVolume > 0) {
                                            if (Float.parseFloat(qnty) <= tank1MaxVolume) {
                                                tvDispenserVolumeRefueling.setText(qnty);
                                                fCode = compartmentInfo.getAtgAutoId();
                                                showStartDialog(Commands.OpenManHoleLockandRFID1);
                                                dialog.dismiss();

                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        send285ATGCommand("M" + atgSerialNoTank1+"\n\r");
                                                    }
                                                }, 1000);
                                            } else {
                                                Toast.makeText(getBaseContext(), "You can receive max " + tank1MaxVolume + " Ltr in Compartment " + compartmentName, Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(ReceiveFuelActivity.this, "Is entered quantity " + qnty + " appropriate?", Toast.LENGTH_LONG).show();
                                        }
                                    } else if (compartmentName.equalsIgnoreCase("2")) {
                                        if (tank1MaxVolume > 0) {
                                            if (Float.parseFloat(qnty) <= tank2MaxVolume) {
                                                tvDispenserVolumeRefueling.setText(qnty);
                                                showStartDialog(Commands.OpenManHoleLockandRFID1);
                                                dialog.dismiss();
                                                send285ATGCommand(String.valueOf(Commands.LISTEN_ATG_2_285));
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        send285ATGCommand("M" + atgSerialNoTank2+"\n\r");
                                                    }
                                                }, 1000);
                                            } else {
                                                Toast.makeText(getBaseContext(), "You can receive max " + tank2MaxVolume + " Ltr in Compartment " + compartmentName, Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(ReceiveFuelActivity.this, "Is entered quantity " + qnty + " appropriate?", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                Toast.makeText(ReceiveFuelActivity.this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog.show();

            }
        });
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("compartmentList", compartmentInfoArrayList);
        compartmentListDialog.setArguments(bundle);
        compartmentListDialog.show(

                getSupportFragmentManager(), CompartmentListDialog.class.

                        getSimpleName());
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void connect232Port() {


        try {
            inetAddress1 = InetAddress.getByName(SharedPref.getServerIp());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
      client2 = new Client(inetAddress1, 232, get232ReciverListner());
     //   client2 = new Client(inetAddress1, 54306, get232ReciverListner());
        client2.execute();
    }


    private Client.ReciverListner get232ReciverListner() {
        Client.ReciverListner reciverListner1 = new Client.ReciverListner() {
            @Override
            public void RecivedData(String response, String responseAscii, boolean isConnected) {
                Log.e("ReasponseM", response + "," + responseAscii + "" + isConnected);

                if (isConnected) {
                    connection_maintainedState.setText("Connected");
                }
                if (response.contains("0103")) {
                    Log.e("responseAtgK2", response);
                    String responseK = response.substring(response.indexOf("0103"), response.indexOf("0103") + 18);
                    Log.e("responseAtg1", "Kam" + responseK);
                    response.contains("0103");
                    String respone1 = responseK.substring(6, 8);
                    String respone2 = responseK.substring(8, 10);
                    String respone3 = responseK.substring(10, 12);
                    String respone4 = responseK.substring(12, 14);


                    float myFloat = Float.intBitsToFloat((int) Long.parseLong(respone3 + respone4 + respone1 + respone2, 16));
                    Log.e("atgResponse", "" + myFloat + "," + respone1 + respone2 + respone3 + respone4);

                    float tank1AtgReading = Float.parseFloat(String.valueOf(myFloat).substring(0, String.valueOf(myFloat).indexOf(".") + 2));

                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
                    Log.e("AtgSizelist", "Size" + atgLinkedHashMapTank1.entrySet().size());
                    for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
                        if (atgcount == test - 10) {
                            test = atgCount;
                            return;
                        } else {
                            atgCount--;
                            try {
                                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
                                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
                                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
                                    float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
                                    Log.e("AtgdataCompare", "kam" + tank1AtgReading + "," + tableAtgReading);
                                    if (tableAtgReading == tank1AtgReading) {

                                        double tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");

                                        double volumeDifference = 0;
                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");

                                        tank1Volume = tank1Volume + volumeDifference;
                                        int indexd = String.valueOf(tank1Volume).indexOf(".");
                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + String.valueOf(tank1Volume).substring(0, indexd + 3));
                                        tvVolumeCompartment.setText(String.format("%.2f", tank1Volume) + "");
                                        tvTotalBalanceAvailable.setText(String.format("%.2f", (totalAvailableVolume)) + "");

                                        if (!isstart) {
                                            intialATGReading = String.valueOf(tank1Volume);
                                            recevingBeforeVolume = tank1Volume;

                                            Log.e("recevieATg1", "ka" + recevingBeforeVolume);
                                        }
                                        lastATGReading = String.valueOf(tank1Volume);
                                        tank1Balance.setText(String.format("%.2f", (tank1Volume - recevingBeforeVolume)));

                                        Log.d("VolumeAfterTempTank1", tank1Volume + "," + "kam" + NavigationDrawerActivity.availableBalance.getText().toString());
                                        if (tank1MaxVolume > 0) {
                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
                                            // waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
                                        }
                                        //waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                                double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//                                                                balanceQunty = totalAvailableVolume;
//                                                                tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");


                                    } else if (tableAtgReading < tank1AtgReading) {
                                        preAtgReading = tableAtgReading;
                                        preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                                        Log.e("preAtg", "ka" + tableAtgReading + "," + tank1AtgReading + "," + preAtgVolume);
                                    } else if (tableAtgReading > tank1AtgReading) {

                                        postAtgReading = tableAtgReading;
                                        postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                                        //Calculate Mean Volume of Tank
                                        meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank1AtgReading - preAtgReading) + preAtgVolume;

                                        double tank1Volume = meanAtgVolume;
                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");

                                        double volumeDifference = 0;
                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");

                                        tank1Volume = tank1Volume + volumeDifference;
                                        Log.d("VolumeAfterTempTank1", tank1Volume + "");

                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + tank1Volume);
                                        tvVolumeCompartment.setText(String.format("%.2f", tank1Volume) + "");
                                        tvTotalBalanceAvailable.setText(String.format("%.2f", (totalAvailableVolume)) + "");

                                        if (!isstart) {
                                            intialATGReading = String.valueOf(tank1Volume);
                                            recevingBeforeVolume = tank1Volume;
                                            Log.e("recevieATg", "ka" + recevingBeforeVolume);
                                        }
                                        lastATGReading = String.valueOf(tank1Volume);
                                        tank1Balance.setText(String.format("%.2f", (tank1Volume - recevingBeforeVolume)));
                                        if (tank1MaxVolume > 0) {
                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
                                            //  waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
                                        }
                                        // waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                        double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//
//                                                        tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
                                        break;
                                    }
                                }
                            } catch (Exception je) {
                                je.printStackTrace();
                            }

                        }
                    }
                    //suspendSale();
                }
                if (response.contains(atgSerialNoTank1)) {
                    int index = response.indexOf(atgSerialNoTank1);
                    String volume = response.substring(index + 13, index + 21);
                    Log.e("ATGVolume", volume);
                    atgData = volume;
                    float tank1AtgReading= Float.parseFloat(atgData);

                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
                    for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
                        if (atgcount == test - 10) {
                            test = atgCount;
                            return;
                        } else {
                            atgCount--;
                            try {
                                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
                                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
                                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
                                    float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
                                    Log.e("AtgdataCompare", "kam" + tank1AtgReading + "," + tableAtgReading);
                                    if (tableAtgReading == tank1AtgReading) {

                                        double tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");

                                        double volumeDifference = 0;
                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");

                                        tank1Volume = tank1Volume + volumeDifference;
                                        int indexd = String.valueOf(tank1Volume).indexOf(".");
                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + String.valueOf(tank1Volume).substring(0, indexd + 3));
                                        tvVolumeCompartment.setText(String.format("%.2f", tank1Volume) + "");
                                        tvTotalBalanceAvailable.setText(String.format("%.2f", (totalAvailableVolume)) + "");

                                        if (!isstart) {
                                            intialATGReading = String.valueOf(tank1Volume);
                                            recevingBeforeVolume = tank1Volume;

                                            Log.e("recevieATg1", "ka" + recevingBeforeVolume);
                                        }
                                        lastATGReading = String.valueOf(tank1Volume);
                                        tank1Balance.setText(String.format("%.2f", (tank1Volume - recevingBeforeVolume)));

                                        Log.d("VolumeAfterTempTank1", tank1Volume + "," + "kam" + NavigationDrawerActivity.availableBalance.getText().toString());
                                        if (tank1MaxVolume > 0) {
                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
                                            // waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
                                        }
                                        //waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                                double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//                                                                balanceQunty = totalAvailableVolume;
//                                                                tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");


                                    } else if (tableAtgReading < tank1AtgReading) {
                                        preAtgReading = tableAtgReading;
                                        preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                                        Log.e("preAtg", "ka" + tableAtgReading + "," + tank1AtgReading + "," + preAtgVolume);
                                    } else if (tableAtgReading > tank1AtgReading) {

                                        postAtgReading = tableAtgReading;
                                        postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
                                        //Calculate Mean Volume of Tank
                                        meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank1AtgReading - preAtgReading) + preAtgVolume;

                                        double tank1Volume = meanAtgVolume;
                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");

                                        double volumeDifference = 0;
                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");

                                        tank1Volume = tank1Volume + volumeDifference;
                                        Log.d("VolumeAfterTempTank1", tank1Volume + "");

                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + tank1Volume);
                                        tvVolumeCompartment.setText(String.format("%.2f", tank1Volume) + "");
                                        tvTotalBalanceAvailable.setText(String.format("%.2f", (totalAvailableVolume)) + "");

                                        if (!isstart) {
                                            intialATGReading = String.valueOf(tank1Volume);
                                            recevingBeforeVolume = tank1Volume;
                                            Log.e("recevieATg", "ka" + recevingBeforeVolume);
                                        }
                                        lastATGReading = String.valueOf(tank1Volume);
                                        tank1Balance.setText(String.format("%.2f", (tank1Volume - recevingBeforeVolume)));
                                        if (tank1MaxVolume > 0) {
                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
                                            //  waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
                                        }
                                        // waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                        double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//
//                                                        tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
                                        break;
                                    }
                                }
                            } catch (Exception je) {
                                je.printStackTrace();
                            }

                        }
                    }


                }

            }
        };
        return reciverListner1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.waveLoadingViewTank1:
                isATGPort3Selected = false;
                getATG1Data(); //Added by Laukendra on 03-01-2020
                break;

            case R.id.waveLoadingViewTank2:
                isATGPort4Selected = false;
                getATG2Data(); //Added by Laukendra on 03-01-2020
                break;

            case R.id.tvDispenserSelectCompartment:
                showCompartmentListDialog();
                break;
            case R.id.tvReconnect:
                reconnectMethod();
                break;
            case R.id.tvStartReceiving:
//                if (responseOf.toLowerCase().contains("low") || responseOf.toLowerCase().contains("high")) {
                if (count == 0) {
                    showCompartmentListDialog();
                    showStartDialog(Commands.OpenManHoleLockandRFID1);
                } else {
//                        startTankCalibration.setText(startTankCalibration.getText().toString().replace("Stop", "Start"));
//                        startTankCalibrationCard.setCardBackgroundColor(ContextCompat.getColor(ReceiveFuelActivity.this, R.color.green));
                    isstart = false;
                    recevingBeforeVolume = 0;
                    endTime = String.valueOf(Calendar.getInstance().getTime());
                    resetTimer();

                    client2.stopClint();
                    ProgressDialog progressDialog = new ProgressDialog(ReceiveFuelActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.show();
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            OpenPrinterDailog();
                            timer.cancel();
                        }
                    };
                    timer.schedule(task, 15000);
                    executorAtg1.shutdownNow();
                    count--;
                }
//                } else {
//                    Log.d("NoResponse Avilable", "NoOne");
//                }
                //  startReceivingMethod();
                break;

        }
    }


    public void reconnectMethod() {
        stopServer();
        connectToSynergyWifi();
    }

    public void startReceivingMethod() {
        if (SharedPref.getLoginResponse().getFranchise_data().get(1).franchise_bypass.equalsIgnoreCase("True")) {
            Toast.makeText(this, "Franchise ByPassed", Toast.LENGTH_SHORT).show();
        } else {
            float gpsRange = Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
            float currentGpsDifference = Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(vehicleItems.get(0).getLatitude()), Double.valueOf(vehicleItems.get(0).getLongitude()), Double.valueOf(franchiseDetail.get(0).getLat()), Double.valueOf(franchiseDetail.get(0).getLongitude()))));

            if (gpsRange <= currentGpsDifference) {
                String mismatchRange = String.valueOf(currentGpsDifference - gpsRange);
                gpsByPassCheckDialog("7", mismatchRange, SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
            } else {

                Toast.makeText(this, "GPS Check Success", Toast.LENGTH_LONG).show();
                //  showAssetDialog(orderDetailed);

            }
            Toast.makeText(this, "Franchise Not ByPassed", Toast.LENGTH_SHORT).show();
        }
    }

    public void gpsByPassCheckDialog(String orderTransactionId, String mismatch, String range) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("The GPS is out of range");
        builder.setMessage("Current Mismatch is : " + mismatch + " metres" + "\n Allowable range is: " + range + " metres" + " in Current GPS status");
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //  getOrderFullDetails(orderTransactionId);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private boolean send285Command(String command) {

        if (Server285.getSocket() != null) {
            Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    ((AppCompatActivity) ReceiveFuelActivity.this).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("writing232", command + "");

                        }
                    });
                }
            });
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void OnRouter485QueueConnected() {

    }

    @Override
    public void OnRouter485StatusConnected() {

    }

    @Override
    public void OnRouter485TxnConnected() {

    }

    @Override
    public void OnRouter485QueueAborted() {

    }

    @Override
    public void OnRouter285Connected() {
        ((AppCompatActivity) ReceiveFuelActivity.this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ReceiveFuelActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));
                connection_maintainedState.setTextColor(getResources().getColor(R.color.green));


            }
        });
    }

    @Override
    public void OnRouter285Aborted() {
        setMessage("Connection Aborted");
    }

    @Override
    public void OnRouter285RfidConnected() {
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (Server285_ManholeLock.getSocket() != null) {
                    checkForMainHoleStatus();
                } else {
                    Log.e("Not Connected", "     mn");
                }
            }

            ;
        };
        t.scheduleAtFixedRate(tt, 10000, 10000);
        createCommandManholeStatusCheck();
    }

    @Override
    public void OnRouter285RfidAborted() {

    }

    @Override
    public void OnRouter485StatusAborted() {

    }

    @Override
    public void OnRouter485TxnAborted() {

    }


    public void createCommandManholeStatusCheck() {
        if (!atgSerialNoTank1.isEmpty()) {
            send285RFIDCommand(String.valueOf(Commands.OpenManHoleLockandRFID1));
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    send285RFIDCommand(String.valueOf(Commands.CHECK_MANHOLE_CLOSED_RFID1_1));
//                }
//            },1000);
        }

        if (!atgSerialNoTank2.isEmpty())
            send285RFIDCommand(String.valueOf(Commands.OpenManHoleLockandRFID2));
    }


    @Override
    public void OnRfIdReceived(String rfIdFOund) {
        Log.d("kamaljeet", rfIdFOund + "kamal");
        if (rfIdFOund != null) {
            Log.d("232FoundATG", rfIdFOund + " ");
            if (rfIdFOund.contains(".") && rfIdFOund.trim().length() == 34) {
                try {
                    if (rfIdFOund != null && !rfIdFOund.isEmpty() && rfIdFOund.trim().length() == 34) {
                        try {
                            atgData = rfIdFOund.substring(13, rfIdFOund.indexOf(".") + 3);
                            if (atgData.contains(".")) {
//                                try {
//                                    ArrayList<Double> data = new DataBaseHelper(ReceiveFuelActivity.this).getValues(Double.parseDouble(atgData));
//                                    double interpola = (((data.get(3) - data.get(1)) / (data.get(2) - data.get(0))) * (Double.parseDouble(atgData) - data.get(0))) + data.get(1);
//                                    Log.d(LoginActivity.class.getSimpleName(), String.format("Volume y1=%.2f , Volume y2=%.2f , Depth x1=%.2f , Depth x2=%.2f , interpolation=%.2f", data.get(1), data.get(3), data.get(0), data.get(2), interpola));
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
                            }
                            if (atgData.contains(".") && selectedCompartment == 1) {
                                if (initialReadingCompartment1 == null || initialReadingCompartment1.isEmpty()) {
                                    initialReadingCompartment1 = atgData;
                                } else {
                                    endReadingCompartment1 = atgData;
                                }
                            } else {
                                if (atgData.contains(".") && initialReadingCompartment2 == null || initialReadingCompartment2.isEmpty()) {
                                    initialReadingCompartment2 = atgData;
                                } else {
                                    endReadingCompartment2 = atgData;
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dispenserCompartmentAtgVolume.setText(atgData);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } catch (Exception e) {
                    Toast.makeText(ReceiveFuelActivity.this, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }


            LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + rfIdFOund + "\n");

        }

    }

    @Override
    public void OnRouter285ATGConnected() {
        ((AppCompatActivity) ReceiveFuelActivity.this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ReceiveFuelActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));

            }
        });
    }

    @Override
    public void OnRouter285ATGAborted() {

    }

    @Override
    public void OnATGReceivedLK(String atgData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.d("ATG Response Data1", atgData);
//                if (atgData != null && !atgData.isEmpty()) {
//                    Log.d("ATG-Actual-Data1", atgData + "");
//                    if (atgData.contains(".")) {
//                        try {
//
//                            if (atgData.trim().length() == 34) {
//                                if (!atgSerialNoTank1.isEmpty() && atgData.contains("23872")) {
//                                    String initialData = atgData.substring(13, atgData.indexOf(".") + 3);
//                                    String atgTank1Temp = atgData.substring(9, 12);
//                                    float tempDifference = 15 - (Float.parseFloat(atgTank1Temp) / 10);
//                                    float tank1AtgReading = Float.parseFloat(initialData);
//                                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
//                                    for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
//                                        try {
//                                            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
//                                            JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
//                                            if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                                float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
//                                                Log.d("PartA", "" + tableAtgReading);
//                                                if (tableAtgReading == tank1AtgReading) {
//
//                                                    double tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                    Log.d("VolumeBeforeTempTank1", tank1Volume + "");
//
//                                                    double volumeDifference = tempDifference * (0.083 / 100) * tank1Volume;
//                                                    Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                    tank1Volume = tank1Volume + volumeDifference;
//                                                    Log.d("VolumeAfterTempTank1", tank1Volume + "");
//
//                                                    String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
//                                                    int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                    Log.d("VolumeTank1Progress", tank1VolumePercent + "");
//                                                    waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
//
//                                                    waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                    double totalAvailableVolume = tank1Volume;
//
//                                                    //if (compartmentName1.equalsIgnoreCase("1")) {
//                                                    if (!isstart) {
//                                                        recevingBeforeVolume = tank1Volume;
//                                                    }
//                                                    tank1Balance.setText(String.format("%.2f", (tank1Volume - recevingBeforeVolume)));
//                                                    tvVolumeCompartment.setText(String.format("%.2f", tank1Volume) + "");
//                                                    //  }
//                                                    tvTotalBalanceAvailable.setText(String.format("%.2f", (totalAvailableVolume)) + "");
//                                                    break;
//                                                } else if (tableAtgReading < tank1AtgReading) {
//                                                    preAtgReading = tableAtgReading;
//                                                    Log.d("Partb<", "" + tableAtgReading);
//                                                    preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                } else if (tableAtgReading > tank1AtgReading) {
//
//                                                    postAtgReading = tableAtgReading;
//                                                    Log.d("PartB<", "" + tableAtgReading);
//                                                    postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                    //Calculate Mean Volume of Tank
//                                                    meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank1AtgReading - preAtgReading) + preAtgVolume;
//
//                                                    double tank1Volume = meanAtgVolume;
//                                                    Log.d("VolumeBeforeTempTank1", tank1Volume + "");
//
//                                                    double volumeDifference = tempDifference * (0.083 / 100) * tank1Volume;
//                                                    Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                    tank1Volume = tank1Volume + volumeDifference;
//                                                    Log.d("VolumeAfterTempTank1", tank1Volume + "");
//
//                                                    String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
//                                                    int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                    Log.d("VolumeTank1Progress", tank1VolumePercent + "");
//                                                    waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
//                                                    String.format("%.2f", tank1Volume);
//                                                    waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                    double totalAvailableVolume = tank1Volume;
//
//                                                    tvTotalBalanceAvailable.setText(String.format("%.2f", (totalAvailableVolume)) + "");
//
//                                                    // if (compartmentName1.equalsIgnoreCase("1")) {
//                                                    if (!isstart) {
//                                                        intialATGReading = String.valueOf(tank1Volume);
//                                                        recevingBeforeVolume = tank1Volume;
//                                                    }
//                                                    lastATGReading = String.valueOf(tank1Volume);
//                                                    tank1Balance.setText(String.format("%.2f", (tank1Volume - recevingBeforeVolume)));
//
//                                                    tvVolumeCompartment.setText(String.format("%.2f", tank1Volume) + "");
//                                                    // }
//
//
//                                                    break;
//                                                }
//                                            }
//                                        } catch (Exception je) {
//                                            je.printStackTrace();
//                                        }
//                                    }
//                                    //  ReadATG1();
//                                }
//                            }
//
//                        } catch (Exception e) {
//                            Toast.makeText(ReceiveFuelActivity.this, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//                        LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + atgData + "\n");
//                    } else if (atgData.contains("SP31SERIAL PORT 3 SELECTED")) {
//                        isATGPort3Selected = true;
//                        if (!atgSerialNoTank1.isEmpty()) {
////                            ReadATG1(); //new Call for read Atg data Command
//                        }
//                    } else if (atgData.contains("SP41SERIAL PORT 4 SELECTED")) {
//                        ReadATG1();
////                        isATGPort4Selected = true;
////                        if (!atgSerialNoTank2.isEmpty()) {
////                            ReadATG2(); //new Call for read Atg data Command
////                        }
//                    }
//                } else {
//                    isATGPort3Selected = false;
//                    isATGPort4Selected = false;
//                }

            }
        });

//        else if (atgData != null && atgData.length() <= 12) {
//            rF_IdTxt.setTextColor(ContextCompat.getColor(DispenserUnitActivity.this, R.color.black));
//            rF_IdTxt.setText(String.format(Locale.US, "RF Id: %s", atgData));
//            if (atgData.startsWith("c") || atgData.startsWith("C")) {
//                dispensingIn.setText(("DISPENSING IN:Jerry Can"));
//                if (!isAlreadyPopForJerryCan) {
//                    isAlreadyPopForJerryCan = true;
//                    if (!isAlreadyPopForAuthorize) {
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
//                        builder.setMessage("Do you want to proceed Fueling in Jerry Can?")
//                                .setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
////                                                authorizeFueling();
//                                        isAlreadyPopForAuthorize = true;
//                                        dialog.cancel();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        isAlreadyPopForAuthorize = false;
//                                        isAlreadyPopForJerryCan = false;
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                    }
//                }
//            }
//            else if (atgData != null && atgData.trim().length() == 1 && atgData.equalsIgnoreCase("�")) {
//                if (pumpStatusTxt.getText().toString().equalsIgnoreCase("FUELING STATE")) {
//                    if (rfNotCloseProgress == null) {
////                                showRfNotCloseError();
//                    } else {
////                                if (!rfNotCloseProgress.isShowing()) {
////                                    rfNotCloseProgress.show();
////                                }
//                    }
////                            suspendSale();
//                }
//                rF_IdTxt.setText(String.format(Locale.US, "RF Id: %s", "No Rf ID found"));
//                dispensingIn.setText(("DISPENSING IN:"));
//                rF_IdTxt.setTextColor(ContextCompat.getColor(DispenserUnitActivity.this, R.color.md_red_400));
//            }
//            else {
//                if (isAlreadyPopForJerryCan && isAlreadyPopForAuthorize) {
//                    Log.d("dispensingFaulty", "Fueling is already authorized for Jerry can");
//                    try {
////                                Log.d("rfIdSubstring", rfId.substring(0, 6) + "");
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//                } else if (atgData != null && atgData.length() > 6 && !isAlreadyPopForAuthorize) {
//                    if (!pumpStatusTxt.getText().toString().isEmpty()) {
//                        isAlreadyPopForAuthorize = true;
////                                authorizeFueling();
//                        dispensingIn.setText(("DISPENSING IN:Asset"));
//                    } else {
//                        if (!pumpStatusTxt.getText().toString().isEmpty() && pumpStatusTxt.getText().toString().equalsIgnoreCase("PAYABLE STATE")) {
//                            if (payableProgress == null) {
//                                showRfErrorInPayableMode();
//                            } else {
//                                if (!payableProgress.isShowing()) {
//                                    payableProgress.show();
//                                }
//                            }
//                        } else {
//                            dismissRfErrorInPayableMode();
//                        }
//                    }
//                }
//            }
//        }

    }

    @Override
    public void OnATGReceivedLK2(String atgData) {
        Log.e("chla", "chla" + atgData);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.d("ATG Response Data", atgData);
                if (atgData != null && !atgData.isEmpty()) {
                    Log.d("ATG-Actual-Data", atgData + "");
//                    if (atgData.contains(".") && atgData.trim().length() == 34) {
                    if (atgData.contains(".")) {
                        try {

                            if (atgData.trim().length() == 34) {
                                if (!atgSerialNoTank1.isEmpty() && atgData.contains(atgSerialNoTank1)) {
                                    String initialData = atgData.substring(13, atgData.indexOf(".") + 3);
                                    String atgTank1Temp = atgData.substring(9, 12);
                                    float tempDifference = 15 - (Float.parseFloat(atgTank1Temp) / 10);
                                    float tank2AtgReading = Float.parseFloat(initialData);

                                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;


//                            for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
//                                if (atgcount2 == test2 - 10) {
//                                    test2 = atgcount2;
//                                    Log.e("kamalrk", "kamalrk");
//                                    return;
//                                } else {
//                                    atgcount2--;
                                    atgcount2 = test2;
                                    for (int k = atgcount2; k < (atgcount2 + 100); k++) {
                                        Log.e("kamak", "ka" + k);
                                        test2 = k;
                                        try {

                                            Object firstKey = atgLinkedHashMapTank1.entrySet().toArray()[k];
                                            int index = firstKey.toString().indexOf("=");
                                            String json = firstKey.toString().substring(index + 1);
                                            Log.e("objectkam", "" + json);
//                                    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) atgLinkedHashMapTank1.get(firstKey);
//                                    JsonObject jsonObject = (new Gson()).toJsonTree(atgLinkedHashMapTank1.get(firstKey)).getAsJsonObject();
                                            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
                                            if (!jsonObject.get("A").toString().equalsIgnoreCase("ATG")) {

                                                Log.e("Kamalke", "" + jsonObject + "," + jsonObject.get("A").toString() + "," + jsonObject.get("B").toString());
                                                float tableAtgReading = Float.parseFloat(jsonObject.get("A").toString());
                                                Log.e("FLoatvalu", "" + tableAtgReading);
                                                if (tableAtgReading == tank2AtgReading) {
                                                    Log.e("ParseFloat1", "" + Float.parseFloat(jsonObject.get("B").toString()));
                                                    double tank2Volume = Float.parseFloat(jsonObject.get("B").toString());
                                                    Log.e("ParseFloat", "" + tank2Volume);
                                                    Log.d("VolumeBeforeTempTank1", tank2Volume + "");

                                                    double volumeDifference = tempDifference * (0.083 / 100) * tank2Volume;
                                                    Log.d("VolumeTempDiffTank1", volumeDifference + "");

                                                    tank2Volume = tank2Volume + volumeDifference;
                                                    Log.d("VolumeAfterTempTank1", tank2Volume + "");

                                                    String string = String.valueOf((tank2Volume * 100) / tank1MaxVolume);
                                                    int tank2VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                                    Log.d("VolumeTank2Progress", tank2VolumePercent + "");
//                                                    waveLoadingViewTank2.setProgressValue(tank2VolumePercent);
//                                                    waveLoadingViewTank2.setCenterTitle(String.format("%.2f", tank2Volume));
                                                    double totalAvailableVolume = tank2Volume;
                                                    tvTotalBalanceAvailable.setText(String.format("%.2f", (totalAvailableVolume)) + "");

                                                    if (!isstart) {
                                                        intialATGReading = String.valueOf(tank2Volume);
                                                        recevingBeforeVolume = tank2Volume;
                                                    }
                                                    lastATGReading = String.valueOf(tank2Volume);
                                                    tank1Balance.setText(String.format("%.2f", (tank2Volume - recevingBeforeVolume)));

                                                } else if (tableAtgReading < tank2AtgReading) {
                                                    preAtgReading = tableAtgReading;
                                                    preAtgVolume = Float.parseFloat(jsonObject.get("B").toString());
                                                } else if (tableAtgReading > tank2AtgReading) {
                                                    postAtgReading = tableAtgReading;
                                                    postAtgVolume = Float.parseFloat(jsonObject.get("B").toString());
                                                    //Calculate Mean Volume of Tank
                                                    meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank2AtgReading - preAtgReading) + preAtgVolume;

                                                    double tank2Volume = meanAtgVolume;
                                                    Log.d("VolumeBeforeTempTank2", tank2Volume + "");

                                                    double volumeDifference = tempDifference * (0.083 / 100) * tank2Volume;
                                                    Log.d("VolumeTempDiffTank2", volumeDifference + "");

                                                    tank2Volume = tank2Volume + volumeDifference;
                                                    Log.d("VolumeAfterTempTank2", tank2Volume + "");

                                                    String string = String.valueOf((tank2Volume * 100) / tank1MaxVolume);
                                                    Log.e("CrashJgh", string);
                                                    int tank2VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                                    Log.d("VolumeTank2Progress", tank2VolumePercent + "");
                                                    waveLoadingViewTank1.setProgressValue(tank2VolumePercent);

                                                    waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank2Volume));
                                                    double totalAvailableVolume = tank2Volume;
                                                    tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");

                                                    if (!isstart) {
                                                        intialATGReading = String.valueOf(tank2Volume);
                                                        recevingBeforeVolume = tank2Volume;
                                                    }
                                                    lastATGReading = String.valueOf(tank2Volume);
                                                    tank1Balance.setText(String.format("%.2f", (tank2Volume - recevingBeforeVolume)));


                                                    break;
                                                }
                                            } else {
                                                Log.e("kamkr", "kamal");
                                            }
                                        } catch (Exception je) {
                                            je.printStackTrace();
                                        }
                                    }
                                }
                                //ReadATG2();

                            }

                        } catch (Exception e) {
                            Toast.makeText(ReceiveFuelActivity.this, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + atgData + "\n");
                    } else if (atgData.contains("SP31SERIAL PORT 3 SELECTED")) {
                        isATGPort3Selected = true;
                        if (!atgSerialNoTank1.isEmpty()) {
//                            ReadATG1(); //new Call for read Atg data Command
                        }
                    } else if (atgData.contains("SP41SERIAL PORT 4 SELECTED")) {
//                        isATGPort4Selected = true;
//                        if (!atgSerialNoTank2.isEmpty()) {
//                            ReadATG2(); //new Call for read Atg data Command
//                        }
                    }
                } else {
                    isATGPort3Selected = false;
                    isATGPort4Selected = false;
                }
            }
        });
    }

    public void send285RFIDCommand(String command) {

        if (Server285_ManholeLock.getSocket() != null) {
            Util.writeAll(Server285_ManholeLock.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    Log.d("writing232-RFID", command + "");
                }
            });
        }
    }

    public void send285ATGCommand(String command) {
        if (ServerATG285.getSocket() != null) {
            Util.writeAll(ServerATG285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    Log.d("ServerATG-writing232", command + "");
                }
            });
        } else {
            Log.e("Server not null", "not null");
        }
    }

    public void setMessage(final String msg) {
        ((AppCompatActivity) ReceiveFuelActivity.this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connection_maintainedState.setText(msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopServer();
    }

    private void stopServer() {
        try {
            if (executor != null && !executor.isTerminated()) {
                executor.shutdown();
            }
            getATGCommandsRunnable = null;
            Server285.getAsyncServer().stop();
            ServerATG285.getAsyncServer().stop();
            Server285_ManholeLock.getAsyncServer().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            getATGCommandsRunnable = null;
                            stopServer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            finish();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timerInMiles, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMiles = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                timerText.setText("0:0");
            }
        }.start();
        isTimerRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
    }

    private void resetTimer() {
        countDownTimer.cancel();
        timeLeftInMiles = 0;
        timerText.setText("0:0");
    }

    private void updateCountDownText() {
        int mintues = (int) (((timerInMiles - timeLeftInMiles) / 1000) / 60);
        int sec = (int) (((timerInMiles - timeLeftInMiles) / 1000) % 60);
        String timeFormated = String.format(Locale.getDefault(), "%02d:%02d", mintues, sec);
        timerText.setText(timeFormated);
    }
}


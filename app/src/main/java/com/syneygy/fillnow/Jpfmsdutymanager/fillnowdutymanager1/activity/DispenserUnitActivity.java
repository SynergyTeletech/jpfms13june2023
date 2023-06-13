package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.WaveLoadingView;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LocationVehicleDatum;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Progress;

import org.json.JSONObject;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ScheduledExecutorService;

public class DispenserUnitActivity extends AppCompatActivity /*implements RouterResponseListener, View.OnClickListener*/ {

    private Context context;

    public static int position;
    private TextView dispenseNow;
    private TextView connection_maintainedState, rF_IdTxt;
    private ProgressDialog progressDialog;
    private EditText setPresetEdt;
    private TextView pumpStatusTxt, tvSiteLatitude, tvSiteLongitude, tvByPassGPS, tvGPSRange;
    private double intialTotalizer = 0d;
    private double curentTotalizer = 0d;
    private String status = "";
    private TextView tvCurrentFuelDispensedQnty;
    private TextView tvCurrentFuelRate;
    private TextView tvCurrentDispensedFuelChargeAmount;
    public static TextView tvCommandExecutionTxt;

    private String uniqueTransactionNumber = "";
    private TextView intialTotalizerTxt;

    private boolean isLockObtanedForNewTransaction;

    private TextView dispenserAssetId, dispenserFuelRate, dispenserLocationInfo, dispenserPresetVolume, dispenserOrderQnty;
    private ScheduledExecutorService executor;
    private Order order;
    private Order checkOrder;
    private Runnable getStatusRunnable;
    public static String transactionId;
    public static String checkTransactionId;
    private TextView dispensingIn;
    private boolean isAlreadyPopForJerryCan = false;
    private boolean isAlreadyPopForAuthorize = false;
    private View dispenserSelectAssetId;
    private ArrayList<Asset> asset;
    private ProgressDialog payableProgress;
    private ProgressDialog rfNotCloseProgress;
    private Progress fuelDetails;
    //    private Progress cheackFuelDetails;
    private int suspendEvent = 0;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private int count = 0;
    private String startedTime;
    private String intialATG;
    private boolean isIntialATG;
    private String intialATGReading;
    private String lastATGReading;
    private int atgCount = 0;
    public static String selectedAssetId, selectedAssetName;
    private DeliveryInProgress orderDetailed;

    private String currentVehicleLat, currentVehicleLong;
    private String orderLocationLat, orderLocationLong;
    private String price;
    private static final Double INTERPOLATION_VALUE_OF = 16.0;
    public static String mPresetValue;

    private TextView tank1, mConnectBluetooth, tvBtnStart, tvTotalBalanceAvailable;
    public static BluetoothSocket btsocket;
    public static OutputStream outputStream;
    private Boolean isFromFreshDispense = false;
    private String freshDispensePresetValue = "";
    private LocationVehicleDatum vehicleDataForFresh;
    private boolean initialVolumeTotalizerCalled = true;

    private AppDatabase appDatabase;
    private boolean isRFidEnabled, isRFidByPass;
    private String rfIdTagId = "";
    private WaveLoadingView waveLoadingViewTank1, waveLoadingViewTank2;

    private LinkedHashMap<String, JSONObject> atgLinkedHashMapTank1, atgLinkedHashMapTank2;
    private String atgSerialNoTank1 = "", atgSerialNoTank2 = "";

    private TextView tvWifiServer232General, tvWifiServer232Atg, tvWifiServer232Rfid, tvWifiServer485General, tvWifiServer485Status, tvWifiServer485ReadTxn;
    private Button btnRefresh232485Server;
    private Runnable getATG1Runnable, getATG2Runnable;
    private ScheduledExecutorService executorAtg1, executorAtg2;
    private boolean isATGPort3Selected, isATGPort4Selected;
    private double tank1MaxVolume = 0f, tank2MaxVolume = 0f;
    private Asset selectedAsset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispenser_unit);
        Log.e("ON_CREATE","ON CREATE1");

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        context = DispenserUnitActivity.this;
//
//        tvCurrentDispensedFuelChargeAmount = findViewById(R.id.currentDispensedFuelChargeAmount);
//        tvCurrentFuelDispensedQnty = findViewById(R.id.currentFuelDispensedQnty);
//        tvCurrentFuelRate = findViewById(R.id.currentFuelRate);
//
//        dispenserAssetId = findViewById(R.id.dispenserSelectedAssetId);
//        dispenserFuelRate = findViewById(R.id.dispenserFuelRate);
//
//        dispenserLocationInfo = findViewById(R.id.tvDispenserLocationId);
//        dispenserSelectAssetId = findViewById(R.id.dispenserSelectAsset);
//        dispenserPresetVolume = findViewById(R.id.dispenserPresetVolume);
//        dispenserOrderQnty = findViewById(R.id.dispenserOrderQnty);
//
////        setPresetEdt = findViewById(R.id.setPresetEdt);
//
//        intialTotalizerTxt = findViewById(R.id.intialTotalizer);
//        pumpStatusTxt = ((TextView) findViewById(R.id.pumpStatus));
//        tvCommandExecutionTxt = ((TextView) findViewById(R.id.tv_command_in_execution));
//        tvSiteLongitude = ((TextView) findViewById(R.id.site_longitude));
//        tvSiteLatitude = ((TextView) findViewById(R.id.site_lattidue));
//        tvByPassGPS = ((TextView) findViewById(R.id.tv_bypass_gps));
//        tvGPSRange = ((TextView) findViewById(R.id.tv_gps_range));
//        rF_IdTxt = ((TextView) findViewById(R.id.rfId));
//        dispensingIn = ((TextView) findViewById(R.id.dispensingIn));
//        tank1 = findViewById(R.id.tv_tank1);
//        mConnectBluetooth = findViewById(R.id.tv_connect_bluetooth);
//        connection_maintainedState = findViewById(R.id.pumpStatus);
//        tvBtnStart = findViewById(R.id.btnStart);
//
//        tvTotalBalanceAvailable = findViewById(R.id.tv_total_balance_available);
//
//        waveLoadingViewTank1 = findViewById(R.id.waveLoadingViewTank1);
//        waveLoadingViewTank2 = findViewById(R.id.waveLoadingViewTank2);
//
//        tvWifiServer232General = findViewById(R.id.tv_wifi_server_status_285);
//        tvWifiServer232Atg = findViewById(R.id.tv_wifi_server_status_285_atg);
//        tvWifiServer232Rfid = findViewById(R.id.tv_wifi_server_status_285_rfid);
//        tvWifiServer485General = findViewById(R.id.tv_wifi_server_status_485);
//        tvWifiServer485Status = findViewById(R.id.tv_wifi_server_status_485_status);
//        tvWifiServer485ReadTxn = findViewById(R.id.tv_wifi_server_status_485_read_txn);
//        btnRefresh232485Server = findViewById(R.id.btn_refresh_232_485_server_status);
//
//        btnRefresh232485Server.setOnClickListener(this);
//        mConnectBluetooth.setOnClickListener(this);
//
//        waveLoadingViewTank1.setOnClickListener(this);
//        waveLoadingViewTank2.setOnClickListener(this);
//
//        findViewById(R.id.btnStop).setOnClickListener(this);
//        findViewById(R.id.btnStart).setOnClickListener(this);
//        findViewById(R.id.btn_for_idlestate).setOnClickListener(this);
//        ((TextView) findViewById(R.id.btn_for_idlestate)).setEnabled(true);
//
//        findViewById(R.id.tvReconnect).setOnClickListener(this);
//        findViewById(R.id.btnSuspend).setOnClickListener(this);
//        findViewById(R.id.btnResume).setOnClickListener(this);
//        findViewById(R.id.orderCompleted).setOnClickListener(this);
//        dispenserSelectAssetId.setOnClickListener(this);
//
//        appDatabase = BrancoApp.getDb();
//
//        try {
//            NavigationDrawerActivity.btsocket = DeviceList.getSocket();
//            if (NavigationDrawerActivity.btsocket != null) {
//                mConnectBluetooth.setText("Printer Connected");
//                mConnectBluetooth.setBackgroundColor(ContextCompat.getColor(this, R.color.md_green_300));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if (SharedPref.getLoginResponse().getData().get(0).atgDataList.size() > 1) {
//                        String string1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
//                        if (string1 != null) {
//                            atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
//                            // atgSerialNoTank1 = "23872"; //Testing
//                            JSONObject jsonObject1 = new JSONObject(string1);
//                            atgLinkedHashMapTank1 = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);
//                        }

//                        try {
//                            Object obj = atgLinkedHashMapTank1.entrySet().toArray()[atgLinkedHashMapTank1.size() - 1];
//                            LinkedHashMap.Entry linkedTreeMap = (LinkedHashMap.Entry) (obj);
//                            JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap.getValue()).getAsJsonObject();
//                            if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                tank1MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                            }
//                        } catch (Exception je) {
//                            je.printStackTrace();
//                        }
//                        Log.d("Tank1AtgMaxVolume", tank1MaxVolume + "");
//                        Log.d("Tank1MaxVolume", (tank1MaxVolume = tank1MaxVolume * .95) + "");

//                        String string2 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(1).getData_volume();
//                        if (string2 != null) {
//                            atgSerialNoTank2 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(1).getData_atg_serial_no();
//                            //  atgSerialNoTank2 = "23872"; //Testing
//                            JSONObject jsonObject2 = new JSONObject(string2);
//                            atgLinkedHashMapTank2 = new Gson().fromJson(jsonObject2.toString(), LinkedHashMap.class);
//                        }
//
//                        try {
//                            Object obj = atgLinkedHashMapTank2.entrySet().toArray()[atgLinkedHashMapTank2.size() - 1];
//                            LinkedHashMap.Entry linkedTreeMap = (LinkedHashMap.Entry) (obj);
//                            JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap.getValue()).getAsJsonObject();
//                            if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                tank2MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                            }
//                        } catch (Exception je) {
//                            je.printStackTrace();
//                        }
//                        Log.d("Tank1AtgMaxVolume", tank1MaxVolume + "");
//                        Log.d("Tank1MaxVolume", (tank1MaxVolume = tank1MaxVolume * .95) + "");
//
//                    } else if (SharedPref.getLoginResponse().getData().get(0).atgDataList.size() == 1) {
//                        waveLoadingViewTank2.setCenterTitle("N/A");
//                        waveLoadingViewTank2.setOnClickListener(null);
//                        String string = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
//                        if (string != null) {
//                            // atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
//                            atgSerialNoTank1 = "23872"; //Testing
//                            JSONObject jsonObject1 = new JSONObject(string);
//                            atgLinkedHashMapTank1 = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);
//                        }
//
//                        try {
//                            Object obj = atgLinkedHashMapTank1.entrySet().toArray()[atgLinkedHashMapTank1.size() - 1];
//                            LinkedHashMap.Entry linkedTreeMap = (LinkedHashMap.Entry) (obj);
//                            JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap.getValue()).getAsJsonObject();
//                            if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                tank1MaxVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                            }
//                        } catch (Exception je) {
//                            je.printStackTrace();
//                        }
//
//                        Log.d("Tank1AtgMaxVolume", tank1MaxVolume + "");
//                        Log.d("Tank1MaxVolume", (tank1MaxVolume = tank1MaxVolume * .95) + "");
//
//                    }
//                } catch (JSONException e) {
//
//                    Log.e("ATG-Table-EXc", "Occured");
//                    e.printStackTrace();
//
//                }
//            }
//        }, 0);
//
//        if (getIntent() != null) {
//            Intent intent = getIntent();
//
//            isFromFreshDispense = intent.getBooleanExtra("isFromFreshDispense", false);
//
//            if (isFromFreshDispense) {
//                Toast.makeText(context, "Hello Fresh Dispense", Toast.LENGTH_SHORT).show();
//
//                //freshDispensePresetValue = intent.getStringExtra("fresh_dispense_preset");
//                orderDetailed = (DeliveryInProgress) intent.getParcelableExtra("freshOrder");
//                asset = orderDetailed.getAssets();
//                //LocationDatum locationDatum = (LocationDatum) bundle.getSerializable("LocationData");
//                //vehicleDataForFresh = (LocationVehicleDatum) bundle.getSerializable("VehicleData");
//                //order = intent.getParcelableExtra("orderDetail");
//
////                transactionId = orderDetailed.getOrder().get(0).getTransactionId();
////                uniqueTransactionNumber = orderDetailed.getOrder().get(0).getTransactionId();
//
//                freshDispensePresetValue = orderDetailed.getOrder().get(0).getQty();
//                dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", freshDispensePresetValue));
//                dispenserPresetVolume.setText(String.format("%s", freshDispensePresetValue));
//
//
//                currentVehicleLat = orderDetailed.getProgress().getCurrentLat();
//                currentVehicleLong = orderDetailed.getProgress().getCurrentLong();
//                orderLocationLat = orderDetailed.getOrder().get(0).getLatitude(); //locationDatum.getLatitude();
//                orderLocationLong = orderDetailed.getOrder().get(0).getLogitude(); //locationDatum.getLogitude();
//
//                //price = vehicleDataForFresh.getNetPrice();
//                price = orderDetailed.getProgress().getFuelPrice();
//                tvSiteLatitude.setText("Lat: " + currentVehicleLat);
//                tvSiteLongitude.setText("Long: " + currentVehicleLong);
//
//                float gpsRange = Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
//                //float gpsRange = Float.valueOf(vehicleDataForFresh.getGpsMismatchRange());
//                float currentGpsDifference = Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))));
//                Log.e("gpsRange", "" + gpsRange);
//                Log.e("currentGpsDifference", "" + currentGpsDifference);
//                //Commented on 24-01-20
////                if (gpsRange <= currentGpsDifference) {
////                    String mismatchRange = String.valueOf(currentGpsDifference - gpsRange);
////                    gpsByPassCheckFreshDispenseDialog(mismatchRange, SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
////                } else {
//                Toast.makeText(context, "GPS Check Success", Toast.LENGTH_LONG).show();
//                showAssetDialog(orderDetailed);
////                }
//                if (price.isEmpty()) {
//                    Toast.makeText(context, "Price Is missing,Please contact Backend Team", Toast.LENGTH_LONG).show();
//                }
//                dispenserFuelRate.setText(String.format(" %s ", price));
//                checkConnectedSetPrice(price);
//            } else {
//
//                StopServer(); //Added On 22-01-20
//
//                order = intent.getParcelableExtra("orderDetail");
//                Log.e("OrderQty", order.getQuantity() + "");
//                transactionId = order.getTransaction_id();
//                getOrderFullDetails(order.getTransaction_id());
//                if (order != null) {
//                    try {
//                        //Toast.makeText(this, "Order Received", Toast.LENGTH_SHORT).show();
//                        dispenserLocationInfo.setText(String.format(" %s", order.getLocation_name()));
//                        dispenserPresetVolume.setText(String.format(" %s Lts", order.getQuantity()));
//                        dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", order.getQuantity()));
//                        uniqueTransactionNumber = order.getTransaction_id();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        connectToSynergyWifi();
//
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Executing Command... ");
//
//        connection_maintainedState.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s != null && s.length() > 0) {
//                    if (s.toString().contains("Connecting")) {
//                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_300));
//                    } else {
//                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_400));
//                    }
//
//                }
//            }
//        });
//
//        connection_maintainedState.setSelected(true);
//
////        try {
////            uniqueTransactionNumber = order.getTransaction_id();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        tvCurrentFuelDispensedQnty.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() != 0)
//                    try {
//                        double currentPrice = Double.parseDouble(s.toString()) * Double.parseDouble(tvCurrentFuelRate.getText().toString());
//                        if (currentPrice >= 0) {
//                            tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", currentPrice));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//            }
//        });
//
//
//        //----------------------------------------------------------------------------------------------------------------------------------
//        // TODO: 2/22/2019 Start Executor for every Two Seconds to check Current Dispenser State
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                getStatusRunnable = new Runnable() {
//                    public void run() {
//                        if (Server485_status.getSocket() != null) {
//                            getStatusPoll();
//                        }
//                    }
//                };
//                executor = Executors.newScheduledThreadPool(1);
//                executor.scheduleAtFixedRate(getStatusRunnable, 1, 1, TimeUnit.SECONDS);
//
//
//                getATG1Runnable = new Runnable() {
//                    public void run() {
//                        getATG1Data();
//                    }
//                };
//                executorAtg1 = Executors.newScheduledThreadPool(1);
//                executorAtg1.scheduleAtFixedRate(getATG1Runnable, 2, 2, TimeUnit.SECONDS);
//
//                getATG2Runnable = new Runnable() {
//                    public void run() {
//                        getATG2Data();
//                    }
//                };
//                executorAtg2 = Executors.newScheduledThreadPool(1);
//                executorAtg2.scheduleAtFixedRate(getATG2Runnable, 3, 2, TimeUnit.SECONDS);
//            }
//        });

//        try {
//            ArrayList<Double> data = new DataBaseHelper(DispenserUnitActivity.this).getValues(INTERPOLATION_VALUE_OF);
//            //added if condition by Laukendra. System was generating error here (IndexOutOfBoundsException) on 14-11-19
//            if (data.size() > 0) {
//                double interPola = (((data.get(3) - data.get(1)) / (data.get(2) - data.get(0))) * (INTERPOLATION_VALUE_OF - data.get(0))) + data.get(1);
//                Log.d(LoginActivity.class.getSimpleName(), String.format("Volume y1=%.2f , Volume y2=%.2f , Depth x1=%.2f , Depth x2=%.2f , interpolation=%.2f", data.get(1), data.get(3), data.get(0), data.get(2), interPola));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

//    public void getATG1Data() {
//        if (ServerATG285.getSocket() != null) {
//            if (ServerATG285.getAsyncServer().isRunning()) {
//                if (!atgSerialNoTank1.isEmpty()) {
//                    //if (!isATGPort3Selected)
//                    ListenATG1();
//                    ReadATG1();
//
//                }
//            }
//        }
//    }
//
//    public void getATG2Data() {
//        if (ServerATG285.getSocket() != null) {
//            if (ServerATG285.getAsyncServer().isRunning()) {
//                if (!atgSerialNoTank2.isEmpty()) {
//                    //if (!isATGPort4Selected)
//                    ListenATG2();
//                    ReadATG2();
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//    }
//
//    // TODO: 2/22/2019 Connect to Synergy Wifi
//
//    /**
//     * This Method is used to connect with Synergy Wifi
//     */
//    public void connectToSynergyWifi() {
//        try {
//
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (Server285.getAsyncServer() == null || !Server285.getAsyncServer().isRunning())
//                        new Server285("192.168.1.103", 54306, (RouterResponseListener) context);
//                    if (ServerATG285.getAsyncServer() == null || !ServerATG285.getAsyncServer().isRunning())
//                        new ServerATG285("192.168.1.103", 54310, (RouterResponseListener) context, atgSerialNoTank1, atgSerialNoTank2);
//                    if (Server285_ReadRFID.getAsyncServer() == null || !Server285_ReadRFID.getAsyncServer().isRunning())
//                        new Server285_ReadRFID("192.168.1.103", 54311, (RouterResponseListener) context);
//                    if (Server485.getAsyncServer() == null || !Server485.getAsyncServer().isRunning())
//                        new Server485("192.168.1.103", 54307, (RouterResponseListener) context);
//                    if (Server485_status.getAsyncStausServer() == null || !Server485_status.getAsyncStausServer().isRunning())
//                        new Server485_status("192.168.1.103", 54308, (RouterResponseListener) context);
//                    if (Server485_ReadTxn.getAsyncReadTxnServer() == null || !Server485_ReadTxn.getAsyncReadTxnServer().isRunning())
//                        new Server485_ReadTxn("192.168.1.103", 54309, (RouterResponseListener) context);
//
////                    try {
////                        readVolumeTotalizer();
////                        //getRfidData();
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//
//                    //setMessage("Connecting...");
//                }
//            }, 500);
//        } catch (Exception e) {
//            Toast.makeText(context, "Something Went Wrong With Server", Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    private void getOrderFullDetails(String orderTransactionId) {
//        if (progressDialog != null) {
//            progressDialog.setMessage("Getting Order Details, Hold On...");
//            progressDialog.show();
//        }
//
//        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
//        //2261
//        apiInterface.getOrderDetail(orderTransactionId, SharedPref.getVehicleId()).enqueue(new Callback<DeliveryInProgress>() {
//            @Override
//            public void onResponse(Call<DeliveryInProgress> call, Response<DeliveryInProgress> response) {
//                progressDialog.dismiss();
//
//                if (response.isSuccessful() && response.body() != null && response.body().getSucc()) {
//                    orderDetailed = response.body();
//                    asset = response.body().getAssets();
//                    fuelDetails = response.body() == null ? new Progress() : response.body().getProgress();
//
//                    price = fuelDetails.getFuelPrice();
//                    tvSiteLatitude.setText("Lat: " + fuelDetails.getCurrentLat());
//                    tvSiteLongitude.setText("Long: " + fuelDetails.getCurrentLong());
//
//                    if (fuelDetails.getLocationBypass()) {
//                        Toast.makeText(context, "GPS ByPassed", Toast.LENGTH_LONG).show();
//                        tvByPassGPS.setVisibility(View.VISIBLE);
//                        showAssetDialog(orderDetailed);
//                    } else {
//                        currentVehicleLat = orderDetailed.getProgress().getCurrentLat();
//                        currentVehicleLong = orderDetailed.getProgress().getCurrentLong();
//                        orderLocationLat = orderDetailed.getOrder().get(0).getLatitude();
//                        orderLocationLong = orderDetailed.getOrder().get(0).getLogitude();
//
//                        float gpsRange = Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
//                        float currentGpsDifference = Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))));
//                        Log.e("gpsRange", "" + gpsRange);
//                        Log.e("currentGpsDifference", "" + currentGpsDifference);
//
//                        if (gpsRange <= currentGpsDifference) {
//                            String mismatchRange = String.valueOf(currentGpsDifference - gpsRange);
//                            gpsByPassCheckDialog(orderTransactionId, mismatchRange, SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
//                        } else {
//                            Toast.makeText(context, "GPS Check Success", Toast.LENGTH_LONG).show();
//                            showAssetDialog(orderDetailed);
//                        }
//                    }
//
//                    if (price.isEmpty()) {
//                        Toast.makeText(context, "Price Is missing,Please contact Backend Team", Toast.LENGTH_LONG).show();
//                    }
//
//                    if (fuelDetails.getDiscountType().equalsIgnoreCase("perliter")) {
//                        String valueToShow = String.valueOf(Double.valueOf(fuelDetails.getFuelPrice()) - Double.valueOf(fuelDetails.getDiscount()));
//                        dispenserFuelRate.setText(String.format(" %s", valueToShow));
//                        checkConnectedSetPrice(valueToShow);
//                        Toast.makeText(context, "Price per litre: " + valueToShow, Toast.LENGTH_SHORT).show(); //Text change/updated  by Laukendra on 14-11-19
//
//                    } else {
//                        String valueToShow = String.valueOf((Double.valueOf(fuelDetails.getFuelPrice()) - (Double.valueOf(fuelDetails.getDiscount()) * .01) / 100));
//                        dispenserFuelRate.setText(String.format(" %s ", valueToShow));
//                        checkConnectedSetPrice(valueToShow);
//                        Toast.makeText(context, "Discount Percentage:" + valueToShow, Toast.LENGTH_SHORT).show(); //Text change/updated  by Laukendra on 14-11-19
//
//                    }
////                    checkConnectedSetPrice(price);
////                    showAssetDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DeliveryInProgress> call, Throwable t) {
//                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void checkConnectedSetPrice(String price) {
//        if (pumpStatusTxt.getText().toString().isEmpty() || pumpStatusTxt.getText().toString().equalsIgnoreCase("Connecting...") || pumpStatusTxt.getText().toString().equalsIgnoreCase("Connection Maintained")) {
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    checkConnectedSetPrice(price);
//                    Log.d("newPrice---->", "checking");
//                }
//            }, 100); //Last value was 2000 changed by Laukendra on 14-11-19
//        } else {
//            Log.d("newPrice", price);
//            setRate(price);
//        }
//    }
//
//    private void showAssetDialog(DeliveryInProgress orderDetailed) {
//        //send285Command("#*RL10"); //Added on 13-11-19
//        try {
//            if (asset != null && asset.size() > 1) {
//
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("assetList", asset);
//                //bundle.putString("qnty", order.getQuantity()); //Commented by Laukendra on 15-11-19
//                float balancedQnty = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
//                bundle.putString("balance_qnty", String.format("%.2f", balancedQnty));
//
//                dispenserPresetVolume.setText(balancedQnty + "");
//
//                AssetListDialog assetListDialog = new AssetListDialog();
//                assetListDialog.setAssetListener(new OnAssetOperation() {
//
//                    @Override
//                    public void OnAssetSelected(String assetId, String remainingBalance, String assetName, Asset asset, int postion) {
//                        selectedAsset = asset;
//
//                        LayoutInflater inflater = getLayoutInflater();
//                        View alertLayout = inflater.inflate(R.layout.selected_asset_qnty_dialog, null);
//                        final EditText et_qnty = alertLayout.findViewById(R.id.et_qnty);
//                        final TextView done = alertLayout.findViewById(R.id.done);
//
//                        selectedAssetId = assetId;
//                        selectedAssetName = assetName;
//                        rfIdTagId = asset.getAssetsTagid();
////                        rfIdTagId = "Test"; //Testing purpose
//                        if (asset.getAssetsRfid().equalsIgnoreCase("1")) {
//                            isRFidEnabled = true;
//                        } else if (asset.getAssetsRfid().equalsIgnoreCase("0")) {
//                            isRFidEnabled = false;
//                        }
//                        if (asset.getAssetsBypassRfid().equalsIgnoreCase("1")) {
//                            isRFidByPass = true;
//                        } else if (asset.getAssetsBypassRfid().equalsIgnoreCase("0")) {
//                            isRFidByPass = false;
//                        }
//
//                        Log.d("RFID-Enabled", isRFidEnabled + "");
//                        Log.d("RFID-ByPassed", isRFidByPass + "");
//                        Log.d("RFID-TagId", rfIdTagId + "");
//
//                        if (!isRFidEnabled) {
//                            AlertDialog.Builder alert = new AlertDialog.Builder(DispenserUnitActivity.this);
//                            //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
//                            alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
//                            // this is set the view from XML inside AlertDialog
//                            alert.setView(alertLayout);
//                            // disallow cancel of AlertDialog on click of back button and outside touch
//                            alert.setCancelable(false);
//                            AlertDialog dialog = alert.create();
//                            alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                    showAssetDialog(orderDetailed);
//                                }
//                            });
//
//                            done.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    String qnty = et_qnty.getText().toString();
//                                    if (qnty.length() <= 0) {
//                                        Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//                                    if (Float.parseFloat(qnty) <= balancedQnty) {
//                                        dispenserPresetVolume.setText(qnty);
//                                        dispenserAssetId.setText(asset.getAssetName());
////                                    order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty))); //Commented by Laukendra on 15-11-19
////                                    Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + (balancedQnty), Toast.LENGTH_SHORT).show(); //Commented by Laukendra on 15-11-19
//                                        dialog.dismiss();
//                                    } else {
//                                        Toast.makeText(getBaseContext(), "Insufficient Balance Left " + qnty + " " + remainingBalance, Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });
//                            dialog.show();
//
//                        } else if (!isRFidByPass && rfIdTagId.isEmpty()) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                            builder.setCancelable(false);
//                            builder.setTitle("Alert");
//                            builder.setMessage("RFID TagId is empty. You can't dispense Fuel. Please contact the administrator.");
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                    finish();
//                                }
//                            });
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//                        } else {
//                            AlertDialog.Builder alert = new AlertDialog.Builder(DispenserUnitActivity.this);
//                            //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
//                            alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
//                            alert.setView(alertLayout);
//                            alert.setCancelable(false);
//                            AlertDialog dialog = alert.create();
//                            alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                    showAssetDialog(orderDetailed);
//                                }
//                            });
//
//                            done.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    String qnty = et_qnty.getText().toString();
//                                    if (qnty.length() <= 0) {
//                                        Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//                                    if (Float.parseFloat(qnty) <= balancedQnty) {
//                                        dispenserPresetVolume.setText(qnty);
//                                        dispenserAssetId.setText(asset.getAssetName());
//                                        dialog.dismiss();
//                                    } else {
//                                        Toast.makeText(getBaseContext(), "Insufficient Balance Left " + qnty + " " + remainingBalance, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                            dialog.show();
//                        }
//                    }
//
//                });
//                assetListDialog.setArguments(bundle);
//                assetListDialog.show(getSupportFragmentManager(), AssetListDialog.class.getSimpleName());
//            } else if (asset != null && asset.size() == 1) { //This line added by Laukendra on 20-11-19
//
//                //Following Lines Added by Laukendra on 11-12-19
////                float balancedQnty = Float.parseFloat(order.getQuantity()) - Float.parseFloat(order.getDelivered_data());
////                dispenserPresetVolume.setText(balancedQnty + "");
////                dispenserAssetId.setText(asset.get(0).getAssetName());
////                selectedAssetId = asset.get(0).getAssetName();
////                selectedAssetName = asset.get(0).getAssetName();
//                rfIdTagId = asset.get(0).getAssetsTagid();
//                //rfIdTagId = "Test"; //Testing purpose
//                if (asset.get(0).getAssetsRfid().equalsIgnoreCase("1")) {
//                    isRFidEnabled = true;
//                } else if (asset.get(0).getAssetsRfid().equalsIgnoreCase("0")) {
//                    isRFidEnabled = false;
//                }
//                if (asset.get(0).getAssetsBypassRfid().equalsIgnoreCase("1")) {
//                    isRFidByPass = true;
//                } else if (asset.get(0).getAssetsBypassRfid().equalsIgnoreCase("0")) {
//                    isRFidByPass = false;
//                }
//
//                Log.d("RFID-Enabled", isRFidEnabled + "");
//                Log.d("RFID-ByPassed", isRFidByPass + "");
//                Log.d("RFID-TagId", rfIdTagId + "");
//
//                if (!isRFidEnabled) {
//                    float balancedQnty = Float.parseFloat(order.getQuantity()) - Float.parseFloat(order.getDelivered_data());
//                    dispenserPresetVolume.setText(balancedQnty + "");
//                    dispenserAssetId.setText(asset.get(0).getAssetName());
//                    selectedAssetId = asset.get(0).getAssetName();
//                    selectedAssetName = asset.get(0).getAssetName();
//
//                } else if (!isRFidByPass && rfIdTagId.isEmpty()) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setCancelable(false);
//                    builder.setTitle("Alert");
//                    builder.setMessage("RFID TagId is empty. You can't dispense Fuel. Please contact the administrator.");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            finish();
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {
//                    float balancedQnty = Float.parseFloat(order.getQuantity()) - Float.parseFloat(order.getDelivered_data());
//                    dispenserPresetVolume.setText(balancedQnty + "");
//                    dispenserAssetId.setText(asset.get(0).getAssetName());
//                    selectedAssetId = asset.get(0).getAssetName();
//                    selectedAssetName = asset.get(0).getAssetName();
//                }
//
//
//                //Above Lines Added by Laukendra on 02-12-19
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    //----------------------------------------------------------------------------------------------------------------------------------
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        try {
//                            getStatusRunnable = null;
//                            getATG1Runnable = null;
//                            getATG2Runnable = null;
//                            StopServer();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            finish();
//                        }
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
//
//    // TODO: 4/22/2019 Stop Server
//
//    public void StopServer() {
//
//        try {
//            if (Server285.getSocket() != null && Server285.getAsyncServer().isRunning()) {
//                Server285.getAsyncServer().stop();
//                Log.d("Server285", "Stopped");
//            }
//            if (ServerATG285.getSocket() != null && ServerATG285.getAsyncServer().isRunning()) {
//                ServerATG285.getAsyncServer().stop();
//                Log.d("ServerATG285", "Stopped");
//            }
//            if (Server285_ReadRFID.getSocket() != null && Server285_ReadRFID.getAsyncServer().isRunning()) {
//                Server285_ReadRFID.getAsyncServer().stop();
//                Log.d("Server285ReadRFID", "Stopped");
//            }
//            if (Server485.getSocket() != null && Server485.getAsyncServer().isRunning()) {
//                Server485.getAsyncServer().stop();
//                Log.d("Server485", "Stopped");
//            }
//            if (Server485_status.getSocket() != null && Server485_status.getAsyncStausServer().isRunning()) {
//                Server485_status.getAsyncStausServer().stop();
//                Log.d("Server485_status", "Stopped");
//            }
//            if (Server485_ReadTxn.getSocket() != null && Server485_ReadTxn.getAsyncReadTxnServer().isRunning()) {
//                Server485_ReadTxn.getAsyncReadTxnServer().stop();
//                Log.d("Server485_ReadTxn", "Stopped");
//            }
//            isATGPort3Selected = false;
//            isATGPort4Selected = false;
//        } catch (NullPointerException ne) {
//            ne.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        try {
////            Server485.getAsyncServer().stop();
////            Server485_status.getAsyncStausServer().stop();
////            Server485_ReadTxn.getAsyncReadTxnServer().stop();
////            Server2 85.getAsyncServer().stop();
////            //if (!atgSerialNoTank1.isEmpty() || !atgSerialNoTank2.isEmpty())
////            ServerATG285.getAsyncServer().stop();
////            //Server285_ReadRFID.getAsyncServer().stop(); //added by Laukendra on 28-11-19
////            isATGPort3Selected = false;
////            isATGPort4Selected = false;
////        } catch (NullPointerException ne) {
////            ne.printStackTrace();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//    // TODO: 2/22/2019 Implement Click Listener Logics
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.waveLoadingViewTank1:
//                isATGPort3Selected = false;
//                getATG1Data(); //Added by Laukendra on 03-01-2020
//                break;
//
//            case R.id.waveLoadingViewTank2:
//                isATGPort4Selected = false;
//                getATG2Data(); //Added by Laukendra on 03-01-2020
//                break;
//
//            case R.id.tv_connect_bluetooth:
//                connectBluetooth();
//                break;
//
//            case R.id.btnStop:
//                setLogToFile("\n\n" + "--------------------Pump Stop Requested ------------------" + "\n\n");
//                progressDialog.setMessage("Stopping Pump...");
//                progressDialog.show();
//                final String[] dd = new String[]{Commands.PUMP_STOP.toString(), Commands.STATUS_POLL.toString()};
//                new CommandQueue(dd, new OnAllCommandCompleted() {
//                    @Override
//                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                        Log.d("pumpStop", lastResponse + "");
//                        PollStatus.getPollState(lastResponse);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressDialog.dismiss();
//                            }
//                        });
//                        afterStopPressed();
//                        Toast.makeText(context, "Pump Stopped Successfully", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onAllCommandCompleted(int currentCommand, String response) {
//                        try {
//                            progressDialog.setMessage(Commands.getEnumByString(dd[currentCommand]));
//                        } catch (Exception e) {
//                            progressDialog.dismiss();
//                        }
//                    }
//                }).doCommandChaining();
//                break;
//
//            case R.id.btn_for_idlestate:
//                try {
//                    toSetIdle();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//
//            case R.id.btnStart:
//                //Following Lines added by Laukendra
//                findViewById(R.id.btnStart).setEnabled(false);
//                Thread thread = new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } finally {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    tvBtnStart.setEnabled(true);
//                                }
//                            });
//                        }
//                    }
//                };
//                thread.start();
////                if (!atgSerialNoTank1.isEmpty() || !atgSerialNoTank2.isEmpty())
////                {
////                    float totalAvailableVolume = Float.parseFloat(tvTotalBalanceAvailable.getText().toString().trim());
////                    float presetVolume = Float.parseFloat(dispenserPresetVolume.getText().toString().trim());
////                    if (presetVolume > totalAvailableVolume) {
////                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
////                        alertDialog.setCancelable(false);
////                        alertDialog.setTitle("Alert!!!");
////                        alertDialog.setMessage("You can dispense maximum " + totalAvailableVolume + " Litres.");
////                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.dismiss();
////                                showAssetDialog(orderDetailed);
////                            }
////                        });
////                        alertDialog.setNegativeButton("Cancel Dispense", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.dismiss();
////                                finish();
////                            }
////                        });
////                        AlertDialog alertDialog1 = alertDialog.create();
////                        alertDialog1.show();
////                    }else {
////                        curentTotalizer = 0f;
////                        tvCurrentFuelDispensedQnty.setText("" + curentTotalizer);
////                        isLockObtanedForNewTransaction = true;
////                        nozzleRelayStop();
////
////                        //--------------------------------------------------------------------------------------------------------------------------------
////
////                        final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
////                        setProgressBarMessage("Checking Status");
////                        progressDialog.show();
////
////                        new CommandQueue(refresh, new OnAllCommandCompleted() {
////                            @Override
////                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                if (lastResponse != null) {
////                                    if (lastResponse.length() <= 14) {
////                                        status = PollStatus.getPollState(stringToHex(lastResponse));
////                                        new DispenseNowClick(context).getPollState(stringToHex(lastResponse));
////                                        ((TextView) findViewById(R.id.pumpStatus)).setText(status);
////                                        if (status.equalsIgnoreCase("STATE IDLE")) {
////                                            if (asset != null && asset.size() >= 1) {
////                                                if (!String.valueOf(dispenserPresetVolume.getText()).isEmpty()) {
////                                                    setPreset(String.valueOf(dispenserPresetVolume.getText()));
////                                                    mPresetValue = dispenserPresetVolume.getText().toString();
////                                                    Toast.makeText(context, String.valueOf(dispenserPresetVolume.getText()), Toast.LENGTH_SHORT).show();
////                                                } else {
////                                                    Toast.makeText(context, "Please Select An Asset To deliver Fuel", Toast.LENGTH_SHORT).show();
////                                                }
////                                            } else {
////                                                if (isFromFreshDispense) {
////                                                    setPreset(freshDispensePresetValue);
////                                                } else {
////                                                    setPreset(order.getQuantity());
////                                                }
//////                                        setPreset(order.getQuantity());
//////                                        mPresetValue = dispenserPresetVolume.getText().toString();
////                                                Toast.makeText(context, String.valueOf(order.getQuantity()), Toast.LENGTH_LONG).show();
////                                            }
////                                        } else {
////                                            try {
////                                                progressDialog.setMessage("waiting for preset to set for next few seconds");
////                                                progressDialog.show();
////                                            } catch (Exception e) {
////                                                e.printStackTrace();
////                                            }
////                                            Handler handler = new Handler();
////                                            handler.postDelayed(new Runnable() {
////                                                @Override
////                                                public void run() {
////                                                    if (asset != null && asset.size() >= 1) {
////                                                        if (!String.valueOf(dispenserPresetVolume.getText()).isEmpty()) {
////                                                            setPreset(String.valueOf(dispenserPresetVolume.getText()));
////                                                            mPresetValue = dispenserPresetVolume.getText().toString();
////                                                        } else {
////                                                            Toast.makeText(context, "Please Select An Asset To deliver Fuel", Toast.LENGTH_SHORT).show();
////                                                        }
////                                                    } else {
////                                                        if (isFromFreshDispense) {
////                                                            setPreset(freshDispensePresetValue);
////                                                        } else {
////                                                            setPreset(order.getQuantity());
////                                                        }
////                                                    }
////                                                }
////                                            }, 1000);
////                                        }
////                                        isLockObtanedForNewTransaction = false;
////                                    }
////                                    dismissProgressBar();
////                                }
////                            }
////
////                            @Override
////                            public void onAllCommandCompleted(final int currentCommand, final String response) {
////                                try {
////                                    if (currentCommand == 0) {
////                                        //todo Read Totalizer Value
//////                                setIntialTotalizer(response);
////                                    }
////
////                                } catch (Exception e) {
////                                    e.printStackTrace();
////                                }
////                            }
////                        }).doCommandChaining();
////                    }
////                }
////                else {
//                curentTotalizer = 0f;
//                tvCurrentFuelDispensedQnty.setText("" + curentTotalizer);
//                isLockObtanedForNewTransaction = true;
//
//                //nozzleRelayStop(); Commented by Laukendra on 02-01-2020
//
//                //--------------------------------------------------------------------------------------------------------------------------------
//
//                final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
//                setProgressBarMessage("Checking Status");
//                progressDialog.show();
//
//                new CommandQueue(refresh, new OnAllCommandCompleted() {
//                    @Override
//                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                        if (lastResponse != null) {
//                            if (lastResponse.length() <= 14) {
//                                status = PollStatus.getPollState(stringToHex(lastResponse));
//
//                                ((TextView) findViewById(R.id.pumpStatus)).setText(status);
//                                if (status.equalsIgnoreCase("STATE IDLE")) {
//                                    if (asset != null && asset.size() >= 1) {
//                                        if (!String.valueOf(dispenserPresetVolume.getText()).isEmpty()) {
//                                            setPreset(String.valueOf(dispenserPresetVolume.getText()));
//                                            mPresetValue = dispenserPresetVolume.getText().toString();
//                                            Toast.makeText(context, String.valueOf(dispenserPresetVolume.getText()), Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(context, "Please Select An Asset To deliver Fuel", Toast.LENGTH_SHORT).show();
//                                        }
//                                    } else {
//                                        if (isFromFreshDispense) {
//                                            setPreset(freshDispensePresetValue);
//                                        } else {
//                                            setPreset(order.getQuantity());
//                                        }
////                                        setPreset(order.getQuantity());
////                                        mPresetValue = dispenserPresetVolume.getText().toString();
//                                        Toast.makeText(context, String.valueOf(order.getQuantity()), Toast.LENGTH_LONG).show();
//                                    }
//                                } else {
//
//                                    new DispenseNowClick(context).getPollState(stringToHex(lastResponse));
//
//                                    try {
//                                        progressDialog.setMessage("waiting for preset to set for next few seconds");
//                                        progressDialog.show();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (asset != null && asset.size() >= 1) {
//                                                if (!String.valueOf(dispenserPresetVolume.getText()).isEmpty()) {
//                                                    setPreset(String.valueOf(dispenserPresetVolume.getText()));
//                                                    mPresetValue = dispenserPresetVolume.getText().toString();
//                                                } else {
//                                                    Toast.makeText(context, "Please Select An Asset To deliver Fuel", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } else {
//                                                if (isFromFreshDispense) {
//                                                    setPreset(freshDispensePresetValue);
//                                                } else {
//                                                    setPreset(order.getQuantity());
//                                                }
//                                            }
//                                        }
//                                    }, 1000);
//                                }
//                                isLockObtanedForNewTransaction = false;
//                            }
//                            dismissProgressBar();
//                        }
//                    }
//
//                    @Override
//                    public void onAllCommandCompleted(final int currentCommand, final String response) {
//                        try {
//                            if (currentCommand == 0) {
//                                //todo Read Totalizer Value
////                                setIntialTotalizer(response);
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).doCommandChaining();
//                //}
//                break;
//
////            case R.id.setPresetBtn:
////                if (isFromFreshDispense) {
////                    if (Integer.valueOf(freshDispensePresetValue) < 0) {
////                        Toast.makeText(context, "Invalid Value For Preset", Toast.LENGTH_SHORT).show();
////                    } else if (Double.parseDouble(freshDispensePresetValue) < 1) {
////                        Toast.makeText(context, "Please Enter Value For Preset more than 1.0", Toast.LENGTH_SHORT).show();
////                    } else {
////                        setProgressBarMessage("Setting Preset...");
////                        setPreset(freshDispensePresetValue);
////                        mPresetValue = dispenserPresetVolume.getText().toString();
////
////                    }
////                } else {
////                    if (setPresetEdt.getText().length() < 0) {
////                        Toast.makeText(context, "Please Enter Valid Value For Preset", Toast.LENGTH_SHORT).show();
////                    } else if (Double.parseDouble(setPresetEdt.getText().toString()) < 1) {
////                        Toast.makeText(context, "Please Enter Value For Preset more than 1.0", Toast.LENGTH_SHORT).show();
////                    } else {
////                        setProgressBarMessage("Setting Preset...");
////                        setPreset(setPresetEdt.getText().toString());
////                        mPresetValue = dispenserPresetVolume.getText().toString();
////
////                    }
////                }
////                break;
////            case R.id.pumpStatusRefresh:
////                refreshPumpState();
////                break;
//            case R.id.tvReconnect:
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        StopServer(); //Added by Laukendra on 04-12-19
//                        connectToSynergyWifi();
//                    }
//                }, 100); //previous Value 2000, //Updating by Laukendra
//                break;
//            case R.id.btnSuspend:
//                //setLogToFile("\n\n" + "------------------Transaction Suspended Requested -----------------" + "\n\n");
//                suspendSale();
//                break;
//            case R.id.btnResume:
//                //setLogToFile("\n\n" + "--------------------Transaction Resume Requested ------------------" + "\n\n");
//                resumeSale();
//                break;
////            case R.id.pdfConvert:
////                PdfCreator.createPDF();
////                break;
//            case R.id.orderCompleted:
//                if (isAlreadyPopForJerryCan) {
//                    send285Command(Commands.LISTEN_XIBEE_RFID_285.toString());
//                    Handler handler2 = new Handler();
//                    handler2.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            sendCommandTenTimes();
//                        }
//                    }, 100); //previous Value 1000, //Updating by Laukendra
//                }
//
//                Bundle bundle = new Bundle();
//                bundle.putString("FuelDispensed", String.valueOf(tvCurrentFuelDispensedQnty.getText()));
//                //bundle.putString("CurrentUserCharge", String.valueOf(tvCurrentDispensedFuelChargeAmount.getText()));
//                bundle.putString("CurrentUserCharge", String.valueOf(Float.parseFloat(String.valueOf(tvCurrentFuelDispensedQnty.getText())) * Float.parseFloat(String.valueOf(dispenserFuelRate.getText()))));
//                bundle.putString("FuelRate", String.valueOf(dispenserFuelRate.getText()));
//                bundle.putString("startTime", startedTime);
//                bundle.putString("selectedAsset", selectedAssetId);
//                bundle.putString("selectedAssetName", selectedAssetName);
//                bundle.putBoolean("rfidEnabled", isRFidEnabled);
//                bundle.putBoolean("rfidByPass", isRFidByPass);
//                bundle.putString("rfidTagId", rfIdTagId);
//                bundle.putString("atgStart", intialATGReading);
//                if (isFromFreshDispense) {
//                    bundle.putSerializable("vehicleDataForFresh", vehicleDataForFresh);
//                    bundle.putParcelable("orderDetail", order);
//                    bundle.putBoolean("isFromFreshDispense", true);
//                    bundle.putString("orderDate", order.getCreated_datatime());
//                } else {
//                    bundle.putParcelable("orderDetail", orderDetailed);
//                    bundle.putBoolean("isFromFreshDispense", false);
//                    bundle.putString("orderDate", orderDetailed.getOrder().get(0).getCreatedDatatime());
//                }
//
//                AddReadingsDialog addReadingsDialog = new AddReadingsDialog();
//                addReadingsDialog.setCancelable(false);
//                addReadingsDialog.setArguments(bundle);
//                addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());
//
//                break;
//            case R.id.dispenserSelectAsset:
//                showAssetDialog(orderDetailed);
//                break;
//
//            case R.id.btn_refresh_232_485_server_status:
//                //Get Status of Wifi Server(232,485)
//
//                break;
//        }
//
//    }
//
//    private void connectBluetooth() {
//
//        Toast.makeText(context, "Please go to Operation Menu and Connect Printer", Toast.LENGTH_SHORT).show();
//
////        if (NavigationDrawerActivity.btsocket == null) {
////            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
//////            Intent BTIntent = new Intent(getApplicationContext(), DeviceList2.class);
////            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
////        } else {
////
////            mConnectBluetooth.setText("Printer Connected");
////            mConnectBluetooth.setBackgroundColor(ContextCompat.getColor(this, R.color.md_green_300));
////
////        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void performATGAction() {
////        ListenATG1();
////        ReadATG1();
////        ListenATG2();
//        ReadATG2();
//        Toast.makeText(context, "Hello ATG", Toast.LENGTH_SHORT).show();
//    }
//
//    private void sendCommandTenTimes() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                send285Command("{123456}");
//                Log.d("multiCommandSent", count + " ");
//                count++;
//                if (count <= 10) {
//                    sendCommandTenTimes();
//                } else {
//                    count = 0;
//                }
//            }
//        }, 100);
//    }
//
//    private TransactionDbModel getLastIdModel() {
//        String lastId = SharedPref.getLastTransactionId();
//        TransactionDbModel lastModel = appDatabase.transactionDbDao().getTransactionById(lastId);
//        return lastModel;
//    }
//
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//    /**
//     * @param response the response is parsed to get Intial Totalizer Reading
//     */
//    private void setIntialTotalizer(String response) {
//        Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+1?");
//        List<String> numbers = new ArrayList<String>();
//        Matcher m = p.matcher(response);
//        while (m.find()) {
//            numbers.add(m.group());
//        }
//        try {
//            if (response != null && response.contains(".")) {
//                Log.d("fuelIntial", numbers.get(0).substring(1).replaceFirst("^0+(?!$)", ""));
//                intialTotalizer = (Double.parseDouble(numbers.get(0).substring(1).replaceFirst("^0+(?!$)", "")));
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        intialTotalizerTxt.setText(String.format("%s", String.valueOf(intialTotalizer)));
//                        SharedPref.setLastFuelDispenserReading(intialTotalizerTxt.getText().toString());
//                    }
//                }, 200);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            {
//
//            }
//        }
//
//    }
//
//    /**
//     * This method is used to Authorize Fueling ,When FCC CON is displayed on Dispenser.
//     */
//    private void authorizeFueling() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
//        builder.setMessage("Are you sure to Start Fueling?")
//                .setCancelable(false)
//                .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        nozzleRelayStart();
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
//                                new CommandQueue(authorized, new OnAllCommandCompleted() {
//                                    @Override
//                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                        curentTotalizer = 0f;
//                                        tvCurrentFuelDispensedQnty.setText("" + curentTotalizer);
//                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onAllCommandCompleted(int currentCommand, String response) {
//                                    }
//                                }).doCommandChaining();
//
//                                eLockOn(); //Added on 08-01-2020
//
//                            }
//
//                        }, 5000); //previous Value 20000, //Updating by Laukendra
//                    }
//                })
//                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
//                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
//                            @Override
//                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                isAlreadyPopForAuthorize = false;
//                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onAllCommandCompleted(int currentCommand, String response) {
//                                try {
//
//                                } catch (Exception e) {
//                                }
//                            }
//                        }).doCommandChaining();
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//
//    }
//
//    private void eLockOn() {
//        if (selectedAsset != null) {
//            if (selectedAsset.getAssets_elock().equalsIgnoreCase("YES")) {
////                send285Command("#*SP21");
////                send285Command("*********");
////                send285Command("#*SP00");
//                send285RFIDCommand("#*SP21");
//                send285RFIDCommand("*********");
//                //send285RFIDCommand("#*SP00");
//
//            }
//        }
//    }
//
//    public void eLockOff() {
//        if (selectedAsset != null) {
//            if (selectedAsset.getAssets_elock().equalsIgnoreCase("YES")) {
////                send285Command("#*SP21");
////                send285Command("#########");
////                send285Command("#*SP00");
//                send285RFIDCommand("#*SP21");
//                send285RFIDCommand("#########");
//                //send285RFIDCommand("#*SP00");
//            }
//        }
//    }
//
//
//    private void refreshPumpState() {
//        final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
//
//        showProgressBar();
//
//        new CommandQueue(refresh, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                Toast.makeText(context, "Pump Status Refresh Successfully", Toast.LENGTH_SHORT).show();
//
//                dismissProgressBar();
//            }
//
//            @Override
//            public void onAllCommandCompleted(final int currentCommand, final String response) {
//                if (response != null) runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            setProgressBarMessage(Commands.getEnumByString(refresh[currentCommand]));
//                            String status = PollStatus.getPollState(stringToHex(response));
//                            Log.d("statusReceived", status + " " + response + currentCommand);
//                            ((TextView) findViewById(R.id.pumpStatus)).setText(status);
//                        } catch (Exception e) {
//                            Log.d("statusReceivedExcep", e.getLocalizedMessage() + " " + response + currentCommand);
//                            dismissProgressBar();
//                        }
//                    }
//                });
//
//            }
//        }).doCommandChaining();
//
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//
//    private String stringToHex(String string) {
//        StringBuilder buf = new StringBuilder(200);
//        for (char ch : string.toCharArray()) {
//            if (buf.length() > 0)
//                buf.append(' ');
//            buf.append(String.format("%02x", (int) ch));
//        }
//        return buf.toString();
//    }
//
//    private String stringToHexWithoutSpace(String string) {
//        StringBuilder buf = new StringBuilder(200);
//        for (char ch : string.toCharArray()) {
//            buf.append(String.format("%02X", (int) ch));
//        }
//        return buf.toString();
//    }
//
//    private static String _stringToHexWithoutSpace(String string) {
//        StringBuilder buf = new StringBuilder(200);
//        for (char ch : string.toCharArray()) {
//            buf.append(String.format("%02X", (int) ch));
//        }
//        return buf.toString();
//
//    }
//
//    public void setMessage(final String msg) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                connection_maintainedState.setText(msg);
//            }
//        });
//    }
//
//    private void checkPreset() {
//        final String[] refresh = new String[]{Commands.PRINT_START.toString()};
//
//        new CommandQueue(refresh, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//
//            }
//
//            @Override
//            public void onAllCommandCompleted(final int currentCommand, final String response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Log.d("responsePrint", response + "");
//                            progressDialog.setMessage(Commands.getEnumByString(refresh[currentCommand]));
//                        } catch (Exception e) {
//                            Log.d("responsePrintExcep", e.getLocalizedMessage() + " " + response + currentCommand);
//                            progressDialog.dismiss();
//                        }
//                    }
//                });
//
//            }
//        }).doCommandChaining();
//
//    }
//
//    private void clearPreset() {
//        final String[] refresh = new String[]{Commands.CLEAR_PRESET.toString()};
//        progressDialog.setMessage("Clearing Preset...");
//        progressDialog.show();
//
//        new CommandQueue(refresh, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                Toast.makeText(context, "Preset Cleared", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onAllCommandCompleted(final int currentCommand, final String response) {
//                if (response != null) runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
//
//            }
//        }).doCommandChaining();
//
//    }
//
//    private void checkAtgInitialValue() {
////        try {
////            ProgressDialog progressDialog = new ProgressDialog(context);
////            progressDialog.setMessage("Please wait checking ATG Reading");
////            progressDialog.show();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        Log.d(DispenserUnitActivity.class.getSimpleName(), "Checking ATG Initial Readings");
//        if (ServerATG285.getSocket() != null) {
//            //String[] commandsATg = new String[]{Commands.LISTEN_ATG_1_285.toString(), getAtgSerial(0), Commands.LISTEN_ATG_2_285.toString(), getAtgSerial(1)};
//            String[] commandsATg = new String[]{Commands.LISTEN_ATG_1_285.toString()};
//
//            for (String command : commandsATg) {
//                send285ATGCommand(command);
//            }
//        }
//
//        SharedPref.getLoginResponse().getVehicle_data().get(0).getCompartmentInfo().get(0).getAtgSerialNo();
//        new Command285Queue(new String[]{Commands.LISTEN_ATG_1_285.toString(), "M23872\r\n", "M23872\r\n"}, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                Log.d(DispenserUnitActivity.class.getSimpleName(), lastResponse + "");
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onAllCommandCompleted(int currentCommand, String response) {
//                Log.d(DispenserUnitActivity.class.getSimpleName(), response + "");
//            }
//        }, 5000).doCommandChaining();
//
//
//    }
//
//    private String getAtgSerial(int atgSerialNoPosition) {
//        return String.format(Locale.CANADA, "M%s\r\r", SharedPref.getLoginResponse().getVehicle_data().get(0).getCompartmentInfo().get(atgSerialNoPosition).getAtgSerialNo());
//        //  return "hello";
//    }
//
//
//    @Override
//    public void OnRouter285Connected() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvWifiServer232General.setText("Connected");
////                        checkAtgInitialValue();
//                    }
//                }, 100); //Last value 2000 changed by Laukendra on 15-11-19
//                Toast.makeText(context, "Connected to 285", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    @Override
//    public void OnRouter285Aborted() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer232General.setText("Disconnected");
//                Toast.makeText(context, "Disconnected from 285", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter285RfidConnected() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer232Rfid.setText("Connected");
//                send285RFIDCommand("#*SP21"); // Open port Read RFID Data
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter285RfidAborted() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer232Rfid.setText("Disconnected");
//            }
//        });
//    }
//
//    //The following function commented by Laukendra on 28-11-19
//
//    @Override
//    public void OnRfIdReceived(String rfIdFoundData) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                Log.d("RFID Data", rfIdFoundData);
//
//                Animation anim = new AlphaAnimation(0.0f, 1.0f);
//                anim.setDuration(50); //You can manage the blinking time with this parameter
//                findViewById(R.id.blinkCircle).startAnimation(anim);
//
//                if (selectedAsset.getAssetsRfid().equalsIgnoreCase("1")) {
//                    if (selectedAsset.getAssetsBypassRfid().equalsIgnoreCase("0")) {
////                        if (rfIdFoundData.length() >= 14 && rfIdFoundData.charAt(0) == '5' && rfIdFoundData.charAt(1) == '5') {
////                            int firstIndex=10;
////                            int lastIndex=rfIdFoundData.lastIndexOf("03");
////                            Log.d("RFID index",firstIndex+","+lastIndex);
////                        if (!selectedAsset.getAssetsTagid().isEmpty() && selectedAsset.getAssetsTagid().equalsIgnoreCase(rfIdFoundData.substring(firstIndex,lastIndex))) {
////                            Toast.makeText(context, "RFID Tag Id Matched", Toast.LENGTH_SHORT).show();
////                            if (rfIdFoundData.charAt(8) == '5' && rfIdFoundData.charAt(9) == '0') {
////                                Log.e("RFID", "RFID Tag Reading Failed");
////                                rF_IdTxt.setText("RFID: Tag Reading Failed");
////                                suspendEventIfRFIDReadFailed();
////                            } else if (rfIdFoundData.charAt(8) == '6' && rfIdFoundData.charAt(9) == '0') {
////                                Log.e("RFID", "RFID Tag Low Battery");
////                                rF_IdTxt.setText("RFID: Tag Low Battery");
////                                suspendEventIfRFIDReadFailed();
////                            } else if (rfIdFoundData.charAt(8) == '7' && rfIdFoundData.charAt(9) == '0') {
////                                Log.e("RFID", "RFID Tag Idle State");
////                                rF_IdTxt.setText("RFID: Tag Idle State");
////                                suspendEventIfRFIDReadFailed();
////                            } else if (rfIdFoundData.charAt(8) == '3' && rfIdFoundData.charAt(9) == '0') {
////                                Log.e("RFID", "RFID Address Setting Succeeded");
////                                rF_IdTxt.setText("RFID: Address Setting Succeeded");
////                                suspendEventIfRFIDReadFailed();
////                            } else if (rfIdFoundData.charAt(8) == '4' && rfIdFoundData.charAt(9) == '0') {
////                                Log.e("RFID", "RFID Tag Reading Succeed");
////                                rF_IdTxt.setText("RFID: Tag Reading Succeed");
////                                //resumeSale();
////                            }
////                        }
////                        }else {
////                            Toast.makeText(context, "RFID Tag Id Not Matched", Toast.LENGTH_SHORT).show();
////                        }
//
//                        if (pumpStatusTxt.getText().toString().trim().equalsIgnoreCase("FUELING STATE")) {
//                            if (rfIdFoundData.length() >= 14 && rfIdFoundData.charAt(0) == '5' && rfIdFoundData.charAt(1) == '5') {
//                                //CHECKing RFID tag data
//                                int firstIndex = 10;
//                                int lastIndex = rfIdFoundData.lastIndexOf("03");
//                                if (!selectedAsset.getAssetsTagid().isEmpty() && selectedAsset.getAssetsTagid().equalsIgnoreCase(rfIdFoundData.substring(firstIndex, lastIndex))) {
//                                    //Toast.makeText(context, "RFID Tag Id Matched", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    rF_IdTxt.setText("RFID: TagId Not Matched");
//                                    //Toast.makeText(context, "RFID Tag Id Not Matched", Toast.LENGTH_SHORT).show();
//                                }
//                                if (rfIdFoundData.charAt(8) == '5' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Reading Failed");
//                                    rF_IdTxt.setText("RFID: Tag Reading Failed");
//                                    suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '6' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Low Battery");
//                                    rF_IdTxt.setText("RFID: Tag Low Battery");
//                                    suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '7' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Idle State");
//                                    rF_IdTxt.setText("RFID: Tag Idle State");
//                                    suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '3' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Address Setting Succeeded");
//                                    rF_IdTxt.setText("RFID: Address Setting Succeeded");
//                                    suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '4' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Reading Succeed");
//                                    rF_IdTxt.setText("RFID: Tag Reading Succeed");
//                                    //resumeSale();
//                                }
//                            }
//                        } else if (pumpStatusTxt.getText().toString().trim().equalsIgnoreCase("SUSPENDED STATE")) {
//                            //CHECKing RFID tag data
//                            int firstIndex = 10;
//                            int lastIndex = rfIdFoundData.lastIndexOf("03");
//                            if (!selectedAsset.getAssetsTagid().isEmpty() && selectedAsset.getAssetsTagid().equalsIgnoreCase(rfIdFoundData.substring(firstIndex, lastIndex))) {
//                                rF_IdTxt.setText("RFID: TagId Matched");
//                                //Toast.makeText(context, "RFID Tag Id Matched", Toast.LENGTH_SHORT).show();
//                            } else {
//                                rF_IdTxt.setText("RFID: TagId Not Matched");
//                                //Toast.makeText(context, "RFID Tag Id Not Matched", Toast.LENGTH_SHORT).show();
//                            }
//                            if (rfIdFoundData.length() >= 14 && rfIdFoundData.charAt(0) == '5' && rfIdFoundData.charAt(1) == '5') {
//                                if (rfIdFoundData.charAt(8) == '5' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Reading Failed");
//                                    rF_IdTxt.setText("RFID: Tag Reading Failed");
//                                    //suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '6' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Low Battery");
//                                    rF_IdTxt.setText("RFID: Tag Low Battery");
//                                    //suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '7' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Idle State");
//                                    rF_IdTxt.setText("RFID: Tag Idle State");
//                                    //suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '3' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Address Setting Succeeded");
//                                    rF_IdTxt.setText("RFID: Address Setting Succeeded");
//                                    //suspendEventIfRFIDReadFailed();
//                                } else if (rfIdFoundData.charAt(8) == '4' && rfIdFoundData.charAt(9) == '0') {
//                                    Log.e("RFID", "RFID Tag Reading Succeed");
//                                    rF_IdTxt.setText("RFID: Tag Reading Succeed");
//                                    resumeSale();
//                                }
//                            }
//                        }
//                    } else {
//                        rF_IdTxt.setText("RFID: Not Enabled");
//                    }
//                } else {
//                    rF_IdTxt.setText("RFID: ByPassed");
//                }
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter285ATGConnected() {
//        tvWifiServer232Atg.setText("Connected");
//        getATG1Data();
//        getATG2Data();
//    }
//
//    @Override
//    public void OnRouter285ATGAborted() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer232Atg.setText("Disconnected");
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter485QueueConnected() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer485General.setText("Connected");
//                getVolumeTotalizer();
//                Toast.makeText(context, "Connected To Queue", Toast.LENGTH_SHORT).show();
//                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));
//            }
//        });
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                Toast.makeText(context, "Connected To Queue", Toast.LENGTH_SHORT).show();
////                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));
////            }
////        });
//    }
//
//    @Override
//    public void OnRouter485StatusConnected() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer485Status.setText("Connected");
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getStatusPoll();
//                    }
//
//                }, 1000); //previous value 2000
//            }
//        });
//
//    }
//
//    @Override
//    public void OnRouter485TxnConnected() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer485ReadTxn.setText("Connected");
//                Toast.makeText(context, "Connected to Txn ", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "Checking Last Readings" + "", Toast.LENGTH_SHORT).show();
//                getReadTransaction();
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter485QueueAborted() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer485General.setText("Disconnected");
//                connection_maintainedState.setText("Connection Aborted");
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter485StatusAborted() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer485Status.setText("Disconnected");
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter485TxnAborted() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                tvWifiServer485ReadTxn.setText("Disconnected");
//            }
//        });
//    }
//
//
//    private void suspendEventIfRFIDReadFailed() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                suspendSale();
//            }
//        }, 5000);
//    }
//
//    @Override
//    public void OnATGReceivedLK(String atgData) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                Log.d("ATG Response Data", atgData);
//                if (atgData != null && !atgData.isEmpty()) {
//                    Log.d("ATG-Actual-Data", atgData + "");
//                    if (atgData.contains(".")) {
//                        try {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (atgData.trim().length() == 34) {
//                                        if (!atgSerialNoTank1.isEmpty() && atgData.contains(atgSerialNoTank1)) {
//                                            String initialData = atgData.substring(13, atgData.indexOf(".") + 3);
//                                            String atgTank1Temp = atgData.substring(9, 12);
//                                            float tempDifference = 15 - (Float.parseFloat(atgTank1Temp) / 10);
//                                            float tank1AtgReading = Float.parseFloat(initialData);
//                                            float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
//                                            for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
//                                                try {
//                                                    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
//                                                    JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
//                                                    if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                                        float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
//                                                        if (tableAtgReading == tank1AtgReading) {
//                                                            runOnUiThread(new Runnable() {
//                                                                @Override
//                                                                public void run() {
//                                                                    double tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                                    Log.d("VolumeBeforeTempTank1", tank1Volume + "");
//
//                                                                    double volumeDifference = tempDifference * (0.083 / 100) * tank1Volume;
//                                                                    Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                                    tank1Volume = tank1Volume + volumeDifference;
//                                                                    Log.d("VolumeAfterTempTank1", tank1Volume + "");
//                                                                    if (tank1MaxVolume > 0) {
//                                                                        String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
//                                                                        int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                                        Log.d("VolumeTank1Progress", tank1VolumePercent + "");
//                                                                        waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
//                                                                    }
//                                                                    waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                                    double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//                                                                    tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                                }
//                                                            });
//
//                                                        } else if (tableAtgReading < tank1AtgReading) {
//                                                            preAtgReading = tableAtgReading;
//                                                            preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                        } else if (tableAtgReading > tank1AtgReading) {
//
//                                                            postAtgReading = tableAtgReading;
//                                                            postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                            //Calculate Mean Volume of Tank
//                                                            meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank1AtgReading - preAtgReading) + preAtgVolume;
//
//                                                            double tank1Volume = meanAtgVolume;
//                                                            Log.d("VolumeBeforeTempTank1", tank1Volume + "");
//
//                                                            double volumeDifference = tempDifference * (0.083 / 100) * tank1Volume;
//                                                            Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                            tank1Volume = tank1Volume + volumeDifference;
//                                                            Log.d("VolumeAfterTempTank1", tank1Volume + "");
//                                                            if (tank1MaxVolume > 0) {
//                                                                String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
//                                                                int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                                Log.d("VolumeTank1Progress", tank1VolumePercent + "");
//                                                                waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
//                                                            }
//                                                            waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                            double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//                                                            tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                            break;
//                                                        }
//                                                    }
//                                                } catch (Exception je) {
//                                                    je.printStackTrace();
//                                                }
//                                            }
//                                            ReadATG1();
//                                        }
//                                    }
//                                }
//                            });
//                        } catch (Exception e) {
//                            Toast.makeText(DispenserUnitActivity.this, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//                        //LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + atgData + "\n");
//                    } else if (atgData.contains("SP31SERIAL PORT 3 SELECTED")) {
//                        isATGPort3Selected = true;
//                        if (!atgSerialNoTank1.isEmpty()) {
//                            ReadATG1(); //new Call for read Atg data Command
//                        }
//                    } else if (atgData.contains("SP41SERIAL PORT 4 SELECTED")) {
//                        isATGPort4Selected = true;
//                        if (!atgSerialNoTank2.isEmpty()) {
//                            ReadATG2(); //new Call for read Atg data Command
//                        }
//                    }
//                } else {
//                    isATGPort3Selected = false;
//                    isATGPort4Selected = false;
//                }
//
//            }
//        });
//
////        else if (atgData != null && atgData.length() <= 12) {
////            rF_IdTxt.setTextColor(ContextCompat.getColor(DispenserUnitActivity.this, R.color.black));
////            rF_IdTxt.setText(String.format(Locale.US, "RF Id: %s", atgData));
////            if (atgData.startsWith("c") || atgData.startsWith("C")) {
////                dispensingIn.setText(("DISPENSING IN:Jerry Can"));
////                if (!isAlreadyPopForJerryCan) {
////                    isAlreadyPopForJerryCan = true;
////                    if (!isAlreadyPopForAuthorize) {
////                        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
////                        builder.setMessage("Do you want to proceed Fueling in Jerry Can?")
////                                .setCancelable(false)
////                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
//////                                                authorizeFueling();
////                                        isAlreadyPopForAuthorize = true;
////                                        dialog.cancel();
////                                    }
////                                })
////                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        isAlreadyPopForAuthorize = false;
////                                        isAlreadyPopForJerryCan = false;
////                                        dialog.cancel();
////                                    }
////                                });
////                        AlertDialog alert = builder.create();
////                        alert.show();
////                    }
////                }
////            }
////            else if (atgData != null && atgData.trim().length() == 1 && atgData.equalsIgnoreCase("�")) {
////                if (pumpStatusTxt.getText().toString().equalsIgnoreCase("FUELING STATE")) {
////                    if (rfNotCloseProgress == null) {
//////                                showRfNotCloseError();
////                    } else {
//////                                if (!rfNotCloseProgress.isShowing()) {
//////                                    rfNotCloseProgress.show();
//////                                }
////                    }
//////                            suspendSale();
////                }
////                rF_IdTxt.setText(String.format(Locale.US, "RF Id: %s", "No Rf ID found"));
////                dispensingIn.setText(("DISPENSING IN:"));
////                rF_IdTxt.setTextColor(ContextCompat.getColor(DispenserUnitActivity.this, R.color.md_red_400));
////            }
////            else {
////                if (isAlreadyPopForJerryCan && isAlreadyPopForAuthorize) {
////                    Log.d("dispensingFaulty", "Fueling is already authorized for Jerry can");
////                    try {
//////                                Log.d("rfIdSubstring", rfId.substring(0, 6) + "");
////
////                    } catch (Exception e) {
////                        e.printStackTrace();
////
////                    }
////                } else if (atgData != null && atgData.length() > 6 && !isAlreadyPopForAuthorize) {
////                    if (!pumpStatusTxt.getText().toString().isEmpty()) {
////                        isAlreadyPopForAuthorize = true;
//////                                authorizeFueling();
////                        dispensingIn.setText(("DISPENSING IN:Asset"));
////                    } else {
////                        if (!pumpStatusTxt.getText().toString().isEmpty() && pumpStatusTxt.getText().toString().equalsIgnoreCase("PAYABLE STATE")) {
////                            if (payableProgress == null) {
////                                showRfErrorInPayableMode();
////                            } else {
////                                if (!payableProgress.isShowing()) {
////                                    payableProgress.show();
////                                }
////                            }
////                        } else {
////                            dismissRfErrorInPayableMode();
////                        }
////                    }
////                }
////            }
////        }
//
//    }
//
//    @Override
//    public void OnATGReceivedLK2(String atgData) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                Log.d("ATG Response Data", atgData);
//                if (atgData != null && !atgData.isEmpty()) {
//                    Log.d("ATG-Actual-Data", atgData + "");
////                    if (atgData.contains(".") && atgData.trim().length() == 34) {
//                    if (atgData.contains(".")) {
//                        try {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (atgData.trim().length() == 34) {
//                                        if (!atgSerialNoTank2.isEmpty() && atgData.contains(atgSerialNoTank2)) {
//                                            String initialData = atgData.substring(13, atgData.indexOf(".") + 3);
//                                            String atgTank1Temp = atgData.substring(9, 12);
//                                            float tempDifference = 15 - (Float.parseFloat(atgTank1Temp) / 10);
//                                            float tank2AtgReading = Float.parseFloat(initialData);
//                                            float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
//                                            for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank2.entrySet()) {
//                                                try {
//                                                    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
//                                                    JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
//                                                    if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                                        float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
//                                                        if (tableAtgReading == tank2AtgReading) {
//
//                                                            double tank2Volume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                            Log.d("VolumeBeforeTempTank1", tank2Volume + "");
//
//                                                            double volumeDifference = tempDifference * (0.083 / 100) * tank2Volume;
//                                                            Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                            tank2Volume = tank2Volume + volumeDifference;
//                                                            Log.d("VolumeAfterTempTank1", tank2Volume + "");
//                                                            if (tank2MaxVolume > 0) {
//                                                                String string = String.valueOf((tank2Volume * 100) / tank2MaxVolume);
//                                                                int tank2VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                                Log.d("VolumeTank2Progress", tank2VolumePercent + "");
//                                                                waveLoadingViewTank2.setProgressValue(tank2VolumePercent);
//                                                            }
//                                                            waveLoadingViewTank2.setCenterTitle(String.format("%.2f", tank2Volume));
//                                                            double totalAvailableVolume = tank2Volume + Float.parseFloat(waveLoadingViewTank1.getCenterTitle());
//                                                            tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                        } else if (tableAtgReading < tank2AtgReading) {
//                                                            preAtgReading = tableAtgReading;
//                                                            preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                        } else if (tableAtgReading > tank2AtgReading) {
//                                                            postAtgReading = tableAtgReading;
//                                                            postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                            //Calculate Mean Volume of Tank
//                                                            meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank2AtgReading - preAtgReading) + preAtgVolume;
//
//                                                            double tank2Volume = meanAtgVolume;
//                                                            Log.d("VolumeBeforeTempTank2", tank2Volume + "");
//
//                                                            double volumeDifference = tempDifference * (0.083 / 100) * tank2Volume;
//                                                            Log.d("VolumeTempDiffTank2", volumeDifference + "");
//
//                                                            tank2Volume = tank2Volume + volumeDifference;
//                                                            Log.d("VolumeAfterTempTank2", tank2Volume + "");
//
//                                                            if (tank2MaxVolume > 0) {
//                                                                String string = String.valueOf((tank2Volume * 100) / tank2MaxVolume);
//                                                                int tank2VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                                Log.d("VolumeTank2Progress", tank2VolumePercent + "");
//                                                                waveLoadingViewTank2.setProgressValue(tank2VolumePercent);
//                                                            }
//                                                            waveLoadingViewTank2.setCenterTitle(String.format("%.2f", tank2Volume));
//                                                            double totalAvailableVolume = tank2Volume + Float.parseFloat(waveLoadingViewTank1.getCenterTitle());
//                                                            tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                            break;
//                                                        }
//                                                    }
//                                                } catch (Exception je) {
//                                                    je.printStackTrace();
//                                                }
//                                            }
//                                            ReadATG2();
//                                        }
//                                    }
//                                }
//                            });
//                        } catch (Exception e) {
//                            Toast.makeText(DispenserUnitActivity.this, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//                        //LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + atgData + "\n");
//                    } else if (atgData.contains("SP31SERIAL PORT 3 SELECTED")) {
//                        isATGPort3Selected = true;
//                        if (!atgSerialNoTank1.isEmpty()) {
//                            ReadATG1(); //new Call for read Atg data Command
//                        }
//                    } else if (atgData.contains("SP41SERIAL PORT 4 SELECTED")) {
//                        isATGPort4Selected = true;
//                        if (!atgSerialNoTank2.isEmpty()) {
//                            ReadATG2();
//                            //new Call for read Atg data Command
//                        }
//                    }
//                } else {
//                    isATGPort3Selected = false;
//                    isATGPort4Selected = false;
//                }
//
//            }
//        });
//    }
//
//    private void readATGData(String[] readTxn) {
//
//        CommandReadATGQueue.getInstance(readTxn, new OnAtgQueueCompleted() {
//            @Override
//            public void onAtgQueueEmpty(boolean isEmpty, final String lastResponse) {
//            }
//
//            @Override
//            public void OnAtgQueueCommandCompleted(final int currentCommand, final String lastResponse) {
//                try {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (lastResponse != null) {
//                                try {
//                                    Log.e("ATGCurrentCommand", currentCommand + "");
//                                    Log.e("ATG-Response", lastResponse);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                    }).start();
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).doCommandChaining();
//    }
//
//    private void showRfErrorInPayableMode() {
//        payableProgress = new ProgressDialog(context);
//        payableProgress.setMessage("Pump not in idle state ,Please took nozzle back,and press start for getting started ");
//        payableProgress.show();
//    }
//
//    private void showRfNotCloseError() {
//        rfNotCloseProgress = new ProgressDialog(context);
//        rfNotCloseProgress.setMessage("Rf Id not found ,Suspending Fueling..");
//        rfNotCloseProgress.show();
//    }
//
//    private void dismissRfNotCloseError() {
//        if (rfNotCloseProgress != null && rfNotCloseProgress.isShowing()) {
//            rfNotCloseProgress.dismiss();
//        }
//    }
//
//    private void dismissRfErrorInPayableMode() {
//        if (payableProgress != null && payableProgress.isShowing()) {
//            payableProgress.dismiss();
//        }
//    }
//
//    private void readVolumeTotalizer() {
//
//        final String[] readTxn = new String[]{Commands.READ_VOL_TOTALIZER.toString()};
//
//        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
//            @Override
//            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {
//            }
//
//            @Override
//            public void OnTxnQueueCommandCompleted(final int currentCommand, final String lastResponse) {
//                try {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
////                            if (lastResponse != null && StringUtils.countMatches(lastResponse, ".") == 3) {
//                            if (lastResponse != null) {
//                                try {
//                                    Log.i("CurrentCommand", currentCommand + "");
//                                    if (initialVolumeTotalizerCalled) {
//                                        if (lastResponse.contains(".")) {
//                                            int lastIndexOfCharA = lastResponse.lastIndexOf("A");
//                                            int lastIndexOfCharDot = lastResponse.indexOf(".");
//                                            if (lastIndexOfCharA < lastIndexOfCharDot) {
//                                                String finalString = lastResponse.substring(lastIndexOfCharA + 3, lastIndexOfCharDot + 3).trim();
//                                                Log.e("initVolumeTotalizerRes-", finalString);
//                                                SharedPref.setInitialVolumeTotalizer(finalString);
//                                            }
//                                        }
//                                    } else {
//                                        if (lastResponse.contains(".")) {
//                                            int lastIndexOfCharA = lastResponse.lastIndexOf("A");
//                                            int lastIndexOfCharDot = lastResponse.indexOf(".");
//                                            if (lastIndexOfCharA < lastIndexOfCharDot) {
//                                                String finalString = lastResponse.substring(lastIndexOfCharA + 3, lastIndexOfCharDot + 3).trim();
//                                                Log.e("FinalReadingVolTotal", finalString);
//
//                                                Log.i("initVolumeTotalizerRes", SharedPref.getInitialVolumeTotalizer());
//                                                Log.i("TillVolumeTotalizerRes", finalString);
//
//                                                if (finalString.length() >= 5) {
//                                                    double differenceVolume = 0;
//                                                    if (finalString.charAt(0) == SharedPref.getInitialVolumeTotalizer().charAt(0)) {
//                                                        differenceVolume = Double.parseDouble(finalString) - Double.parseDouble(SharedPref.getInitialVolumeTotalizer());
//                                                    } else if (finalString.length() > SharedPref.getInitialVolumeTotalizer().length()) {
//                                                        differenceVolume = Double.parseDouble(finalString.substring(1)) - Double.parseDouble(SharedPref.getInitialVolumeTotalizer());
//                                                    } else if (finalString.length() < SharedPref.getInitialVolumeTotalizer().length()) {
//                                                        differenceVolume = Double.parseDouble(finalString) - Double.parseDouble(SharedPref.getInitialVolumeTotalizer().substring(1));
//                                                    }
//                                                    final String txnFuelRate = tvCurrentFuelRate.getText().toString().trim();
//                                                    Log.i("DiffVolumeTotalizer", differenceVolume + "");
//                                                    Log.i("AmtVolumeTotalizer", differenceVolume * Double.parseDouble(txnFuelRate) + "");
//
//                                                    Double presetQuantity = Double.parseDouble(dispenserPresetVolume.getText().toString());
//
//                                                    if (differenceVolume > 0 && differenceVolume >= presetQuantity && (differenceVolume - presetQuantity) < 1) {
//                                                        final String txnDispense = String.format("%.2f", differenceVolume);
//                                                        final String txnCharges = String.format("%.2f", differenceVolume * Double.parseDouble(txnFuelRate));
//                                                        Log.i("VolumeTotalizerReading", " amount =" + txnFuelRate + ", dispense=" + txnDispense + ", charges=" + txnCharges);
//
//                                                        tvCurrentFuelDispensedQnty.setText(String.format("%.2f", Double.parseDouble(txnDispense)));
//                                                        tvCurrentDispensedFuelChargeAmount.setText(String.format("%.2f", Double.parseDouble(txnCharges)));
//                                                    }
//                                                }
//                                                SharedPref.setInitialVolumeTotalizer(finalString);
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }).start();
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).doCommandChaining();
//    }
//
//    private void getReadTransaction() {
//
//        final String[] readTxn = new String[]{Commands.READ_TXN.toString()};
//
//        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
//            @Override
//            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {
//            }
//
//            @Override
//            public void OnTxnQueueCommandCompleted(final int currentCommand, final String lastResponse) {
//                try {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("TxnResponse", lastResponse + "");
//                            if (lastResponse != null && StringUtils.countMatches(lastResponse, ".") == 3) {
//                                try {
//                                    Log.i("CurrentCommand", currentCommand + "");
//                                    Log.i("TxnResponseInner", lastResponse);
//
//                                    final String txnFuelRate = lastResponse.substring(4, lastResponse.indexOf(".") + 3);
//                                    final String txnDispense = lastResponse.substring(lastResponse.indexOf(".") + 3, CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3);
//                                    final String txnCharges = lastResponse.substring(CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3, CommonUtils.findNextOccurance(lastResponse, ".", 3) + 3);
//
//                                    Log.i("FuelDispenseNewTxn", " amount =" + txnFuelRate + ", dispense=" + txnDispense + ", charges=" + txnCharges);
//
//                                    Handler handler = new Handler(Looper.getMainLooper());
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                if (curentTotalizer < 0) {
//                                                    return;
//                                                }
//                                                tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
//                                                tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));
//                                                //tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.trim())));
//                                                //Added by Laukendra
//                                                if (txnFuelRate.length() > 5) {
//                                                    String string = txnFuelRate.trim().substring(txnFuelRate.length() - 6);
//                                                    Log.d("Rate-:", string);
//                                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(string.trim())));
//                                                } else
//                                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.trim())));
//
//                                                if (Float.parseFloat(txnDispense) > Float.parseFloat(mPresetValue)
//                                                ) {
//
//                                                    sendToServerStopPump();
//                                                }
//                                                //Added by Laukendra
//                                            } catch (NumberFormatException e) {
//                                                e.printStackTrace();
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                                //added following lines by Laukendra on 22-11-19
////                                                tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
////                                                tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));
////                                                tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.substring(2))));
//                                                //added above lines by Laukendra on 22-11-19
//
//                                            }
//                                        }
//                                    }, 100);
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                    }).start();
//
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }).doCommandChaining();
//
//    }
//
//    private void _getTotalizer() {
//        final String[] readTxn = new String[]{Commands.READ_TXN.toString(), Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()};
//        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
//            @Override
//            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {
//            }
//
//            @Override
//            public void OnTxnQueueCommandCompleted(final int currentCommand, final String lastResponse) {
//                try {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (lastResponse != null && StringUtils.countMatches(lastResponse, ".") == 3) {
//                                try {
//                                    final String txnFuelRate = lastResponse.substring(4, lastResponse.indexOf(".") + 3);
//                                    final String txnDispense = lastResponse.substring(lastResponse.indexOf(".") + 3, CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3);
//                                    final String txnCharges = lastResponse.substring(CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3, CommonUtils.findNextOccurance(lastResponse, ".", 3) + 3);
//
//                                    Log.i("_Response", lastResponse);
//                                    Log.i("_CurrentCommand", currentCommand + "");
//                                    Log.i("_FuelDispenseNewTxn", " amount=" + txnFuelRate + ", dispense=" + txnDispense + ", charges=" + txnCharges);
//
//                                    Handler handler = new Handler(Looper.getMainLooper());
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                if (curentTotalizer < 0) {
//                                                    return;
//                                                }
//                                                tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
//                                                tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));
//                                                //tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.trim())));
//                                                //Added by Laukendra
//                                                if (txnFuelRate.length() > 5) {
//                                                    String string = txnFuelRate.trim().substring(txnFuelRate.length() - 6);
//                                                    Log.d("Rate-:", string);
//                                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(string.trim())));
//                                                } else
//                                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.trim())));
//                                                //Added by Laukendra
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }, 200);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                    }).start();
//
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }).doCommandChaining();
//
//    }
//
//    private void getVolumeTotalizer() {
//
//        final String[] readVolume = new String[]{Commands.READ_VOL_TOTALIZER.toString()};
//
//        new CommandReadTxnQueue(readVolume, new OnTxnQueueCompleted() {
//            @Override
//            public void onTxnQueueEmpty(boolean isEmpty, String response) {
//                Log.i("fuelDispenseNewVol", response + "");
//                if (response != null && StringUtils.countMatches(response, ".") == 1) {
//                    try {
//                        final String txnFuelVolume = response.substring(4, response.indexOf(".") + 3);
//
//                        if (Double.parseDouble(txnFuelVolume) != 0 && Double.parseDouble(txnFuelVolume) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
//                            Log.e("totalizerVolume", txnFuelVolume);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    intialTotalizerTxt.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelVolume)));
//                                }
//                            });
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    getVolumeTotalizer();
//                }
//            }
//
//            @Override
//            public void OnTxnQueueCommandCompleted(int currentCommand, String response) {
//
//            }
//        }).doCommandChaining();
//
//
//    }
//
//    private void getRfidData() {
//
//        final String[] readTxn = new String[]{Commands.LISTEN_XIBEE_RFID_285.toString()};
//
//        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
//            @Override
//            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {
//            }
//
//            @Override
//            public void OnTxnQueueCommandCompleted(final int currentCommand, final String lastResponse) {
//                try {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (lastResponse != null) {
//                                try {
//                                    Log.i("CurrentCommand", currentCommand + "");
//                                    Log.i("RFIDResponse", lastResponse);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                    }).start();
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).doCommandChaining();
//    }
//
//    public void dismissProgressBar() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//            }
//        });
//    }
//
//    public void setProgressBarMessage(String message) {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (progressDialog != null) {
//                    if (progressDialog.isShowing()) {
//                        progressDialog.setMessage(message);
//                    } else {
//                        progressDialog.show();
//                        progressDialog.setMessage(message);
//                    }
//                } else {
//                    progressDialog = new ProgressDialog(context);
//                    progressDialog.setMessage(message);
//                    progressDialog.show();
//                }
//            }
//        });
//
//
//    }
//
//    public void showProgressBar() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (progressDialog != null && !progressDialog.isShowing()) {
//                    progressDialog.show();
//                }
//            }
//        });
//
//    }
//
//    public void setPreset(String volume) {
//        if (Server485.getSocket() != null) {
//            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));
//            String command = "01415031" + stringToHexWithoutSpace(b) + "7F";
//
//            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
//            Log.d(
//                    "ExecutingCommand", command
//                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
//                            + " totalCommand= " + checkSumCommand
//                            + " String-" + convertToAscii(command)
//                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));
//
//
//            final String[] d = new String[]{Commands.READ_VOL_TOTALIZER.toString(), checkSumCommand, Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString()};
//            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
//                @Override
//                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                    setPresetWithoutStart(volume);
//
////                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
////                    try {
////
////                        dismissProgressBar();
////                        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
////                        builder.setMessage("Please Take Nozzle to fueling position")
////                                .setCancelable(false)
////                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        authorizeFueling();
////                                    }
////                                })
////                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        isAlreadyPopForAuthorize = false;
////                                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
////                                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
////                                            @Override
////                                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
////
////                                            }
////
////                                            @Override
////                                            public void onAllCommandCompleted(int currentCommand, String response) {
////                                                try {
////                                                } catch (Exception e) {
////                                                }
////                                            }
////                                        }).doCommandChaining();
////
////                                        dialog.cancel();
////                                    }
////                                });
////                        AlertDialog alert = builder.create();
////                        alert.show();
//
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                }
//
//                @Override
//                public void onAllCommandCompleted(int currentCommand, String response) {
////                    try {
////                        if (currentCommand == 0) {
////                            todo Read Totalizer Value
////                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
////
////                                setIntialTotalizer(response);
////                            }
////                        }
////                    } catch (Exception e) {
////
////                    }
//                }
//            });
//            data.doCommandChaining();
//        }
//
//    }
//
//    public static void _setPreset(String volume) {
//        if (Server485.getSocket() != null) {
//            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));
//            String command = "01415031" + _stringToHexWithoutSpace(b) + "7F";
//
//            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
//            Log.d(
//                    "ExecutingCommand", command
//                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
//                            + " totalCommand= " + checkSumCommand
//                            + " String-" + convertToAscii(command)
//                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));
//
//
//            final String[] d = new String[]{Commands.READ_VOL_TOTALIZER.toString(), checkSumCommand, Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString()};
//            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
//                @Override
//                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                    _setPresetWithoutStart(volume);
////                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
////                    try {
////
////                        dismissProgressBar();
////                        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
////                        builder.setMessage("Please Take Nozzle to fueling position")
////                                .setCancelable(false)
////                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        authorizeFueling();
////                                    }
////                                })
////                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        isAlreadyPopForAuthorize = false;
////                                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
////                                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
////                                            @Override
////                                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
////
////                                            }
////
////                                            @Override
////                                            public void onAllCommandCompleted(int currentCommand, String response) {
////                                                try {
////                                                } catch (Exception e) {
////                                                }
////                                            }
////                                        }).doCommandChaining();
////
////                                        dialog.cancel();
////                                    }
////                                });
////                        AlertDialog alert = builder.create();
////                        alert.show();
//
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                }
//
//                @Override
//                public void onAllCommandCompleted(int currentCommand, String response) {
////                    try {
////                        if (currentCommand == 0) {
////                            todo Read Totalizer Value
////                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
////
////                                setIntialTotalizer(response);
////                            }
////                        }
////                    } catch (Exception e) {
////
////                    }
//                }
//            });
//            data.doCommandChaining();
//        }
//
//    }
//
//    public void setPresetWithoutStart(String volume) {
//        if (Server485.getSocket() != null) {
//            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));
//            String command = "01415031" + stringToHexWithoutSpace(b) + "7F";
//
//            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
//            Log.d(
//                    "ExecutingCommand", command
//                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
//                            + " totalCommand= " + checkSumCommand
//                            + " String-" + convertToAscii(command)
//                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));
//            final String[] d = new String[]{checkSumCommand};
//            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
//                @Override
//                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                    Log.e("responseOfPreset", lastResponse);
//                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
//                    try {
//                        dismissProgressBar();
//                        if (builder == null)
//                            builder = new AlertDialog.Builder(DispenserUnitActivity.this);
//                        builder.setMessage("Please Take Nozzle to fueling position")
//                                .setCancelable(false)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//
////                                        nozzleRelayStop();
//                                        authorizeFueling();
//      //                                  _authorizeFueling();
//
//                                    }
//                                })
//                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        isAlreadyPopForAuthorize = false;
//                                        isAlreadyPopForJerryCan = false;
//                                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
//                                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
//                                            @Override
//                                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
//
//                                            }
//
//                                            @Override
//                                            public void onAllCommandCompleted(int currentCommand, String response) {
//                                                try {
//                                                } catch (Exception e) {
//                                                }
//                                            }
//                                        }).doCommandChaining();
//
//                                        dialog.cancel();
//                                    }
//                                });
//                        if (alert == null) alert = builder.create();
//                        if (!alert.isShowing()) {
//                            alert.show();
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onAllCommandCompleted(int currentCommand, String response) {
////                    try {
////                        if (currentCommand == 0) {
////                            todo Read Totalizer Value
////                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
////
////                                setIntialTotalizer(response);
////                            }
////                        }
////                    } catch (Exception e) {
////
////                    }
//                }
//            });
//            data.doCommandChaining();
//        }
//
//    }
//
////    public  static void _setPresetWithoutStart(String volume) {
////        if (Server485.getSocket() != null) {
////            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));
////            String command = "01415031" + stringToHexWithoutSpace(b) + "7F";
////
////            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
////            Log.d(
////                    "ExecutingCommand", command
////                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
////                            + " totalCommand= " + checkSumCommand
////                            + " String-" + convertToAscii(command)
////                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));
////            final String[] d = new String[]{checkSumCommand};
////            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
////                @Override
////                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////
////                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
////                    try {
////                        dismissProgressBar();
////                        if (builder == null) builder = new AlertDialog.Builder(DispenserUnitActivity.this);
////                        builder.setMessage("Please Take Nozzle to fueling position")
////                                .setCancelable(false)
////                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////
//////                                        nozzleRelayStop();
////                                        authorizeFueling();
//////                                        _authorizeFueling();
////
////
////                                    }
////                                })
////                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        isAlreadyPopForAuthorize = false;
////                                        isAlreadyPopForJerryCan = false;
////                                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
////                                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
////                                            @Override
////                                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
////
////                                            }
////
////                                            @Override
////                                            public void onAllCommandCompleted(int currentCommand, String response) {
////                                                try {
////                                                } catch (Exception e) {
////                                                }
////                                            }
////                                        }).doCommandChaining();
////
////                                        dialog.cancel();
////                                    }
////                                });
////                        if (alert == null) alert = builder.create();
////                        if (!alert.isShowing()) {
////                            alert.show();
////                        }
////
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////
////                @Override
////                public void onAllCommandCompleted(int currentCommand, String response) {
//////                    try {
//////                        if (currentCommand == 0) {
//////                            todo Read Totalizer Value
//////                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
//////
//////                                setIntialTotalizer(response);
//////                            }
//////                        }
//////                    } catch (Exception e) {
//////
//////                    }
////                }
////            });
////            data.doCommandChaining();
////        }
////
////    }
//
//    /**
//     * @param fuelPrice this is used to set New Price per Ltr of Fuel in Dispenser Unit
//     */
//    public void setRate(String fuelPrice) {
//        ProgressDialog setRateProgressDialog = new ProgressDialog(context);
//        setRateProgressDialog.setMessage("Setting New Fuel Price:" + fuelPrice);
//        setRateProgressDialog.show();
//        if (Server485.getSocket() != null) {
//            String b = String.format(Locale.US, "%06d", (int) (Double.parseDouble(fuelPrice) * 100));
//
//            String integerr = b.substring(0, 4);
//            String decimal = b.substring(4, 6);
//
//            Log.e("LeftPart", integerr);
//            Log.e("RightPart", decimal);
//            Log.e("RatetoUpdate", b);
//
//            String command = "014142" + stringToHexWithoutSpace(integerr) + "2E" + stringToHexWithoutSpace(decimal) + "7F";
//            Log.e("Rate to Update", command);
//
//            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
////            String checkSumCommand = "014142303035302E30307F56";
//            Log.d(
//                    "ExecutingRateCommand", command
//                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
//                            + " totalCommand= " + checkSumCommand
//                            + " String-" + convertToAscii(command)
//                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));
//
//            final String[] d = new String[]{Commands.READ_VOL_TOTALIZER.toString(), checkSumCommand, Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString()};
//            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
//                @Override
//                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                    Toast.makeText(BrancoApp.getContext(), "New Fuel Rate Set ", Toast.LENGTH_SHORT).show();
//                    Log.i("rate", lastResponse + "");
//                    dismissProgressBar();
//                    if (setRateProgressDialog != null && setRateProgressDialog.isShowing()) {
//                        setRateProgressDialog.dismiss();
//                    }
//                }
//
//                @Override
//                public void onAllCommandCompleted(int currentCommand, String response) {
//                    try {
//                        if (currentCommand == 0) {
//                            //todo Read Totalizer Value
//                            Log.i("Read Totalizer Value", response + "");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            data.doCommandChaining();
//        }
//    }
//
//    /**
//     * @param byteData this is used to calculate Checksum that is needed for write operations in Dispenser Communication.
//     * @return
//     */
//    private static String calculateCheckSum(byte[] byteData) {
//        byte chkSumByte = 0x00;
//        for (int i = 0; i < byteData.length; i++) {
//            chkSumByte ^= byteData[i];
//        }
//        Log.d("createdCheckSum", convertToAscii(String.valueOf(chkSumByte)));
//        return String.format("%02x", chkSumByte);
//
//
//    }
//
//
//    public static String printByteArray(byte[] bytes) {
//        StringBuilder sb = new StringBuilder("new byte[] { ");
//        for (byte d : bytes) {
//            sb.append(d + ", ");
//        }
//        sb.append("}");
//        return sb.toString();
//    }
//
//    /**
//     * this method is used to suspend Current Fueling
//     */
//    void suspendSale() {
//        suspendEvent++;
//        try {
//            if (progressDialog != null)
//                progressDialog.show();
//        } catch (WindowManager.BadTokenException e) {
//            //use a log message
//        }
//
//        final String[] refresh = new String[]{Commands.SUSPEND_SALE.toString()};
//        new CommandQueue(refresh, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                try {
//                    if (progressDialog != null)
//                        progressDialog.show();
//                } catch (WindowManager.BadTokenException e) {
//                    //use a log message
//                }
//            }
//
//            @Override
//            public void onAllCommandCompleted(final int currentCommand, final String response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            progressDialog.setMessage(Commands.getEnumByString(refresh[currentCommand]));
//                        } catch (Exception e) {
//                            Log.d("statusReceivedExcSusp", e.getLocalizedMessage() + " " + response + currentCommand);
//                            progressDialog.dismiss();
//                        }
//                    }
//                });
//
//            }
//        }).doCommandChaining();
//
//    }
//
//    /**
//     * this method is used to Resume already Suspended Fueling.
//     */
//
//    void resumeSale() {
//
//        final String[] refresh = new String[]{Commands.RESUME_SALE.toString()};
//        progressDialog.show();
//
//        new CommandQueue(refresh, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                Log.d("print", lastResponse + "");
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onAllCommandCompleted(final int currentCommand, final String response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            progressDialog.setMessage(Commands.getEnumByString(refresh[currentCommand]));
//                        } catch (Exception e) {
//                            Log.d("statusReceivedExcResum", e.getLocalizedMessage() + " " + response + currentCommand);
//                            progressDialog.dismiss();
//                        }
//                    }
//                });
//            }
//        }).doCommandChaining();
//    }
//
//
//    public void setStatusMessage(final String status) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                pumpStatusTxt.setText(status);
//            }
//        });
//
//    }
//
//    /*
//     * This Method is Used to get Current State of Fuel Dispenser Unit .
//     * States could be any from
//     * STATE_IDLE,  CALL STATE, PRESET READY STATE, FUELING STATE,  FUELING STATE,  PAYABLE STATE
//     * SUSPENDED STATE, STOPPED STATE, IN-OPERATIVE STATE, AUTHORIZE STATE, Suspend Started State
//     * WAIT FOR PRESET STATE,STARTED STATE .
//     */
//
//    void getStatusPoll() {
//        final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
//        CommandStatusQueue commandStatus = CommandStatusQueue.getInstance(refresh, new OnStatusQueueListener() {
//            @Override
//            public void onStatusQueueQueueEmpty(boolean isEmpty, String lastResponse) {
//            }
//
//            @Override
//            public void OnStatusQueueCommandCompleted(final int currentCommand, final String response) {
//                if (response != null) {
//                    if (response.length() <= 14) {
//                        status = PollStatus.getPollState(response);
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!status.isEmpty()) {
//                                if (status.contains("STATE IDLE") || status.equalsIgnoreCase("CALL STATE")) {
//
//                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(true);
//
//                                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                    ((TextView) findViewById(R.id.btnStop)).setEnabled(false);
//
////                                    findViewById(R.id.setPresetLay).setVisibility(View.VISIBLE);
//                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
//                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                } else if (status.equalsIgnoreCase("FUELING STATE")||status.equalsIgnoreCase("STARTED FUELING STATE")) {
////                                    readVol();
//                                    if (!isLockObtanedForNewTransaction) {
//                                        Handler handler = new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Log.e("fuelingStateK", status);
//
//                                                getReadTransaction();
//                                            }
//                                        }, 1);
////                                        getVolumeTotalizer();
//                                    }
////                                    findViewById(R.id.pdfConvert).setVisibility(View.GONE);
//                                    //startLogTransaction(String.valueOf(uniqueTransactionNumber), intialTotalizer, tvCurrentFuelDispensedQnty.getText().toString(), tvCurrentDispensedFuelChargeAmount.getText().toString(), String.valueOf(Calendar.getInstance().getTime()));
//
//                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(true);
//
//                                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));//changed on 13-11-19
//                                    ((TextView) findViewById(R.id.btnStop)).setEnabled(false); //changed on 13-11-19
//
//                                } else if (status.equalsIgnoreCase("PAYABLE STATE") || status.equalsIgnoreCase("STOPPED STATE")) {
//                                    isAlreadyPopForAuthorize = false;
//                                    if (!isLockObtanedForNewTransaction) {
//                                        //To DO
//                                        //send Command RL10->IKNOWATIONS:#*RL1O
////                                        nozzleRelayStop();
//
//                                        getReadTransaction();
//                                        _getTotalizer();
//                                        Log.e("StatusTOidle", status);
//
//                                    }
////                                    isPayableState = true;
////                                    findViewById(R.id.orderCompletedLay).setVisibility(View.GONE);
//                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
//                                } else {
////                                    isPayableState = false;
//                                    //To DO
////                                    nozzleRelayStop();
//                                    //send Command RL10->IKNOWATIONS:#*RL1O
//                                    CommandReadTxnQueue.TerminateCommandChain();
////                                    findViewById(R.id.orderCompletedLay).setVisibility(View.VISIBLE);
//                                }
//                                if (status.equalsIgnoreCase("SUSPENDED STATE") || status.equalsIgnoreCase("SUSPEND STARTED STATE")) {
////                                    nozzleRelayStop();
//                                    getReadTransaction(); // added by Laukendra
//
//                                    initialVolumeTotalizerCalled = false;
//                                    //readVolumeTotalizer();
//
//                                    dismissRfNotCloseError();
//
//                                    if (Double.valueOf(tvCurrentFuelDispensedQnty.getText().toString().replace("Lts", "").trim()) >= Double.valueOf(dispenserPresetVolume.getText().toString().replace("Lts", "").trim())) {
//
//                                        final String[] dd = new String[]{Commands.PUMP_STOP.toString(), Commands.STATUS_POLL.toString()};
//
//                                        new CommandQueue(dd, new OnAllCommandCompleted() {
//                                            @Override
//                                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                                Log.d("pumpStop-SuspendedMode", lastResponse + "");
//                                                PollStatus.getPollState(lastResponse);
//                                                runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        progressDialog.dismiss();
//                                                    }
//                                                });
//                                                afterStopPressed();
//                                                Toast.makeText(context, "Pump Stopped Successfully", Toast.LENGTH_SHORT).show();
//
//                                            }
//
//                                            @Override
//                                            public void onAllCommandCompleted(int currentCommand, String response) {
//                                                try {
//                                                    progressDialog.setMessage(Commands.getEnumByString(dd[currentCommand]));
//                                                } catch (Exception e) {
//                                                    progressDialog.dismiss();
//                                                }
//                                            }
//                                        }).doCommandChaining();
//
//                                    } else {
//                                        ((TextView) findViewById(R.id.btnResume)).setEnabled(true);
//                                        ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//
//                                        ((TextView) findViewById(R.id.btnStop)).setEnabled(true);
//                                        ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_400));
//
//                                        ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                        ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
//                                    }
//
//                                } else if (status.equalsIgnoreCase("STARTED STATE")) {
//                                    startedTime = String.valueOf(Calendar.getInstance().getTime());
//                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(true);
//                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);
//                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);
//
//                                } else if (status.equalsIgnoreCase("STOPPED STATE")) {
//
//                                    isAlreadyPopForAuthorize = false;
//                                    suspendEvent = 0;
//
//                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(true);
//                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);
//                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
////                                    readVol();
//                                    if (!isLockObtanedForNewTransaction) {
//                                        //To DO
//                                        //send Command RL10->IKNOWATIONS:#*RL1O
//                                        getReadTransaction();
//
//                                    }
//
////                                    initialVolumeTotalizerCalled=false; //added by Laukendra on 22-11-19
////                                    readVolumeTotalizer();  //added by Laukendra on 22-11-19
//
////                                    isPayableState = true;
//                                    findViewById(R.id.orderCompleted).setVisibility(View.VISIBLE);
//                                    nozzleRelayStop();
//
//                                } else if (status.equalsIgnoreCase("AUTHORIZE STATE")) {
//
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
////                                            Toast.makeText(context, "To set IDLE State", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
////                                    toSetIdle();
//
//                                } else {
//                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);
//                                }
//
//
//                                Log.d("OnStatusQueueListener", status + "");
//                                setStatusMessage(status);
//                            }
//                        }
//                    });
//
//                }
//
//
//            }
//
//        });
//        commandStatus.doCommandChaining();
//    }
//
//    /**
//     * THIS METHOD IS USED TO LOG ALL TRANSACTION IN TEXT FILE WITH FOLLOWING PARAMETERS.
//     *
//     * @param transactionNum
//     * @param lastDispenserValue
//     * @param currentDispenserValue
//     * @param CurrentUserCharges
//     * @param dateTime
//     */
//
//    void startLogTransaction(final String transactionNum, final Double lastDispenserValue, final String currentDispenserValue, final String CurrentUserCharges, final String dateTime) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LogToFile.setFuelingLog(
//                        String.valueOf(lastDispenserValue)
//                        , currentDispenserValue
//                        , CurrentUserCharges
//                        , dateTime
//                        , String.valueOf(dispenserAssetId.getText())
//                        , tvCurrentFuelRate.getText().toString()
//                        , order
//                        , String.valueOf(dispensingIn.getText())
//                        , suspendEvent
//                );
//            }
//        }).start();
//    }
//
//    void setLogToFile(final String logMsg) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LogToFile.LogMessageToFile(logMsg);
//            }
//        }).start();
//    }
//
//    private void sopDB_Values() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String lastId = SharedPref.getLastTransactionId();
//                TransactionDbModel lastModel = appDatabase.transactionDbDao().getTransactionById(lastId);
//                if (lastModel != null) {
//                    try {
//                        Log.d("dbValues", appDatabase.transactionDbDao().getTransactionById(SharedPref.getLastTransactionId()).toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
////            CommandQueue.TerminateCommandChain();
////            CommandStatusQueue.TerminateCommandChain();
////            CommandReadTxnQueue.TerminateCommandChain();
////            Command285Queue.TerminateCommandChain();//Added by laukendra on 19-12-19
////            CommandReadATGQueue.TerminateCommandChain();//Added by laukendra on 19-12-19
//            if (executor != null && !executor.isTerminated()) {
//                executor.shutdown();
//            }
//            StopServer();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
////    @Override
////    protected void onStop() {
////        super.onStop();
////        try {
////            CommandQueue.TerminateCommandChain();
////            CommandStatusQueue.TerminateCommandChain();
////            CommandReadTxnQueue.TerminateCommandChain();
//////            Command285Queue.TerminateCommandChain();//Added by laukendra on 19-12-19
//////            CommandReadATGQueue.TerminateCommandChain();//Added by laukendra on 19-12-19
////            if (executor != null && !executor.isTerminated()) {
////                executor.shutdown();
////            }
////            StopServer();
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//
//    public void ClearSale() {
//        ProgressDialog progressDialog = new ProgressDialog(DispenserUnitActivity.this);
//        progressDialog.setMessage("Please wait for transaction completion..");
//        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()};
//        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                progressDialog.dismiss();
//                Log.e("lastClearSale", lastResponse + "");
//                Toast.makeText(BrancoApp.getContext(), "Sale Cleared " + lastResponse, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAllCommandCompleted(int currentCommand, String response) {
//                try {
//
//                } catch (Exception e) {
//
//                }
//            }
//        }).doCommandChaining();
//    }
//
//    public void send285Command(String command) {
//        if (Server285.getSocket() != null) {
//            Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
//                @Override
//                public void onCompleted(Exception ex) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("writing232", command + "");
//                            // Log.e("ATG-Res", ex.toString());
//                        }
//                    });
//                }
//            });
//        }
//    }
//
//    public void send285Command(String command, int delay) {
//
//        if (Server285.getSocket() != null) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
//                        @Override
//                        public void onCompleted(Exception ex) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Log.d("writing232Delay", command + " delay=" + delay);
//                                }
//                            });
//                        }
//                    });
//                }
//            }, delay);
//        }
//    }
//
//    public void send285ATGCommand(String command) {
//        if (ServerATG285.getSocket() != null && ServerATG285.getAsyncServer().isRunning()) {
//            Util.writeAll(ServerATG285.getSocket(), command.getBytes(), new CompletedCallback() {
//                @Override
//                public void onCompleted(Exception ex) {
//
//                    Log.d("ATG-writing232", command + "");
//
//                }
//            });
//        }
//    }
//
//    public void send285RFIDCommand(String command) {
//
//        if (Server285_ReadRFID.getSocket() != null) {
//            Util.writeAll(Server285_ReadRFID.getSocket(), command.getBytes(), new CompletedCallback() {
//                @Override
//                public void onCompleted(Exception ex) {
//
//                    Log.d("writing232-RFID", command + "");
//                }
//            });
//        }
//    }
//
//    public void ListenATG1() {
//        send285ATGCommand(Commands.LISTEN_ATG_1_285.toString());
//    }
//
//    public void ListenATG2() {
//        send285ATGCommand(Commands.LISTEN_ATG_2_285.toString());
//    }
//
//    public void ReadATG1() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                send285ATGCommand("M" + atgSerialNoTank1); //This is atg value/id
//                Log.d("CommandReadAtgPort3", "M" + atgSerialNoTank1);
//            }
//        });
//    }
//
//    public void ReadATG2() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                send285ATGCommand("M" + atgSerialNoTank2); //This is atg value/id
//                Log.d("CommandReadAtgPort4", "M" + atgSerialNoTank2);
//            }
//        });
//    }
//
//    public void afterStopPressed() {
//        try {
//            sendToServerStopPump();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void nozzleRelayStart() {
//        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getMakeOfRelayBoard().equalsIgnoreCase("IKNOWVATIONS")) {
//            send285Command("RL10");
//            send285Command("RL11");
//            Log.d("RL11-----", "RL11");
//        } else {
//            send285Command("#*RL10");
//            eLockOff();
//            Log.d("RL10----->", "#*RL10");
//            send285Command("#*RL11");
//            Log.d("RL11----->", "#*RL11");
//            send285Command("#*BZ11");
//            send285Command("#*BZ10");
//        }
//    }
//
//    public void nozzleRelayStop() {
//        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getMakeOfRelayBoard().equalsIgnoreCase("IKNOWVATIONS")) {
//            send285Command("RL10");
//        } else {
//            send285Command("#*RL10");
//            eLockOff();
//        }
//    }
//
//    public void sendToServerStopPump() {
//
////        try {
////            CommandQueue.TerminateCommandChain();
////            CommandStatusQueue.TerminateCommandChain();
////            CommandReadTxnQueue.TerminateCommandChain();
////            //Command285Queue.TerminateCommandChain();//Added by laukendra on 19-12-19
////            //CommandReadATGQueue.TerminateCommandChain();//Added by laukendra on 19-12-19
////            if (executor != null && !executor.isTerminated()) {
////                executor.shutdown();
////            }
////            StopServer();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
//        Bundle bundle = new Bundle();
//        bundle.putString("FuelDispensed", String.valueOf(tvCurrentFuelDispensedQnty.getText()));
//        //bundle.putString("CurrentUserCharge", String.valueOf(tvCurrentDispensedFuelChargeAmount.getText()));
//        bundle.putString("CurrentUserCharge", String.valueOf(Float.parseFloat(String.valueOf(tvCurrentFuelDispensedQnty.getText())) * Float.parseFloat(String.valueOf(dispenserFuelRate.getText()))));
//        bundle.putString("FuelRate", String.valueOf(dispenserFuelRate.getText()));
//        bundle.putString("startTime", startedTime);
//        bundle.putString("selectedAsset", selectedAssetId);
//        bundle.putString("selectedAssetName", selectedAssetName);
//        bundle.putBoolean("rfidEnabled", isRFidEnabled);
//        bundle.putBoolean("rfidByPass", isRFidByPass);
//        bundle.putString("rfidTagId", rfIdTagId);
//        bundle.putString("atgStart", intialATGReading);
//        bundle.putString("orderDate", orderDetailed.getOrder().get(0).getOrderDate());
//        bundle.putParcelable("orderDetail", orderDetailed);
//
//        if (isFromFreshDispense) {
//            bundle.putSerializable("vehicleDataForFresh", vehicleDataForFresh);
//            bundle.putBoolean("isFromFreshDispense", true);
//        } else {
//            bundle.putBoolean("isFromFreshDispense", false);
//        }
//        Log.e("AddreadingOP","opOP");
//        AddReadingsDialog addReadingsDialog = new AddReadingsDialog();
//        addReadingsDialog.setCancelable(false);
//        addReadingsDialog.setArguments(bundle);
//        addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());
//        send285Command("#*RL10"); //Added on 20-12-19  //Commented on 02-01-20
//        getReadTransaction();
//        getVolumeTotalizer();
//        eLockOff();
//
//        //Added By Laukendra
////        tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", 0.00));
////        tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", 0.00));
////        dispenserFuelRate.setText(String.format(Locale.US, "%.2f", 0.00));
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
////        Toast.makeText(context, String.valueOf(LocationUtil.distanceInMeters(28.657060, 77.325950, 31.361180, 70.997840)), Toast.LENGTH_SHORT).show();
//    }
//
//    public void gpsByPassCheckDialog(String orderTransactionId, String mismatch, String range) {
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//        builder.setTitle("The GPS is out of range");
//        builder.setMessage("Current Mismatch is : " + mismatch + " metres" + "\n Allowable range is: " + range + " metres" + " in Current GPS status");
//
//        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                getOrderFullDetails(orderTransactionId);
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//
//        android.app.AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        alertDialog.setCancelable(false);
//    }
//
//    public void gpsByPassCheckFreshDispenseDialog(String mismatch, String range) {
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//        builder.setTitle("The GPS is out of range");
//        builder.setMessage("Current Mismatch is : " + mismatch + " metres" + "\n Allowable range is: " + range + " metres" + " in Current GPS status");
//
//        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//
//        android.app.AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        alertDialog.setCancelable(false);
//    }
//
//    public void toSetIdle() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Kindly reset power to continue ..")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

}

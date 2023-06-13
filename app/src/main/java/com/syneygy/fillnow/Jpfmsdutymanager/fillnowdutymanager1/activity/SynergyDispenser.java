package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.WaveLoadingView;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.AlertDIalogFragment;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseSynergy;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.ServerRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.ServerSynery;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.AddReadingsDialog;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.AssetListDialog;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.Click;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.OnAssetOperation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.AssetfuelingStatus;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.FuelingModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LocationVehicleDatum;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Progress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.SaveRatingModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.getFuelingState;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ChangePasswordbean;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.FineteckCalculation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.TokheiumCalculation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//.............Updated code by Sonal 29-12-2021....JPFMS DUTY MANAGER..........//
public class SynergyDispenser extends AppCompatActivity implements View.OnClickListener, RouterResponseRelay, RouterResponseSynergy {
    private static final String TAG = "SynergyDispenser";
    Handler portCloseHandler;
    Runnable portCloseRunnable;
    private boolean isFuelingStart = false;
    String fuelingStartTime, fuelingEndTime, finalVolumeTotalizer, finalVolume, finalAmount, initialVolumeTotalizer, mFinalPrice;
    int atgInitialCount = 0;
    boolean isFuelingTxn = false;
    boolean isDispenseComplete = false, flagAtgHnadleRunning = false;
    String atgInitialReading = "0.00";
    String atgFinalReading = "0.00";
    Timer timerK;
    boolean tagReadingFaled = false;
    String responseRFIDk = "";
    boolean startPump = false;
    boolean isReceiver232;
    int isBattry = 0;
    String atgdipChartDataVol;
    private boolean port2Selected = false;
    private boolean port1Selected = false;
    private boolean port4Selected = false;
    private int connect1 = 0;

    boolean lastTOT = false;
    int assetShow = 0;
    String atgdipChartN;
    int auth = 0;
   public int getTOtlizer = 0;
    boolean lcBleen = false, relayBolean, relay20bolean;
    public boolean suspendClick = false;
    private Client client1;

    int lock = 0;
    private Context context;
    int atgcount = 0, elockCount = 0;
    boolean SetRate = false;
    private TextView connection_maintainedState, rF_IdTxt;
    private TextView pumpStatusTxt, tvSiteLatitude, tvSiteLongitude, tvByPassGPS, tvGPSRange;
    private double intialTotalizer = 0d;
    private double curentTotalizer = 0d;
    public boolean rfidData = false;
    public static String status = "";
    private TextView tvCurrentFuelDispensedQnty;
    private TextView tvCurrentFuelRate;
    public static int position;
    private TextView tvCurrentDispensedFuelChargeAmount;
    public static TextView tvCommandExecutionTxt;
    int test = 0;
    private TextView intialTotalizerTxt;
    private boolean fuelingState = false;
    private TextView dispenserAssetId, dispenserFuelRate, dispenserLocationInfo, dispenserPresetVolume, analogReadingVol_i, analogReadingVol_f, dispenserOrderQnty;
    private ScheduledExecutorService executor;
    private Order order;
    private Order checkOrder;
    //    private Runnable getStatusRunnable;
    public static String transactionId;
    public static String checkTransactionId, finalAnaloloReading = "0", intialreading = "0", finalreading = "0";
    private TextView dispensingIn;
    private View dispenserSelectAssetId;
    private ArrayList<Asset> asset;
    private ProgressDialog rfNotCloseProgress;
    private Progress fuelDetails;
    private AlertDialog alert;
    AlertDialog.Builder alertDialogRFID;
    private AlertDialog.Builder builder;
    private int counts = 0;
    private String startedTime;
    private int atgCount = 0;
    public static String selectedAssetId;
    private static String selectedAssetName;
    public static String eLockId, tagid;
    private static DeliveryInProgress orderDetailed;
    int selected, noozle;
    private String currentVehicleLat = "00.0000", currentVehicleLong = "00.0000";
    private String orderLocationLat = "00.00000", orderLocationLong = "00.0000";
    private String price;
    public static String mPresetValue;
    private TextView tank1, mConnectBluetooth, tvBtnStart, tvTotalBalanceAvailable;
    public static BluetoothSocket btsocket;
    public static OutputStream outputStream;
    private Boolean isFromFreshDispense = false, StatusForUpdateAtg = true;
    public boolean resumeClicked = false;
    private String freshDispensePresetValue = "";
    private LocationVehicleDatum vehicleDataForFresh;
    private boolean initialVolumeTotalizerCalled = true;
    private AppDatabase appDatabase;
    Timer timer ;
    private boolean isRFidEnabled, isRFidByPass;
    private String rfIdTagId = "";
    private boolean isStartTimer = false;
    Handler handlerChinese;
    private WaveLoadingView waveLoadingViewTank1, waveLoadingViewTank2;
    private LinkedHashMap<String, JSONObject> atgLinkedHashMapTank1, atgLinkedHashMapTank2;
    private String atgSerialNoTank1 = "";
    private TextView tvWifiServer232General, tvWifiServer232Atg, tvWifiServer232Rfid, tvWifiServer485General, tvWifiServer485Status, tvWifiServer485ReadTxn;
    private Button btnRefresh232485Server;
    private Runnable getATG1Runnable, getATG2Runnable;
    private ScheduledExecutorService executorAtg1, executorAtg2;
    private boolean isATGPort3Selected, isATGPort4Selected;
    private double tank1MaxVolume = 0f, tank2MaxVolume = 0f;
    public Asset selectedAsset;
    private TextView nozzleOnHook;
    public static Double balanceQunty;
    public int mismatchEvent = 0;
    public int relayCommandPassed = 1;
    InetAddress inetAddress, inetAddress1;
    public static String receive;
    Handler presetHandler, atgHandler;
    Runnable presetrunnable;
    String assetRegNo = "0";

    private String atgDipChart = "";
    String statusK = "";
    TextView btnSuspend;
    Boolean isIntial = true;
    private Boolean readRFid = false;
    LinearLayout ll_btn_visibility;
    private static SynergyDispenser instance;
    private boolean rFIDRead = false;
    AlertDialog dialogRf;
    LoginResponse response;
    String atgdipChart;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DispenseLocalDatabaseHandler dispenseLocalDatabaseHandler;
    String fuelDispensedn, analogLevelSenserReading;
    private final int DELAY = 20000; // 10 seconds
    private Handler handler = new Handler();
    boolean isBackPress = true;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        ClientAtg clientAtg = null;
        setContentView(R.layout.activity_synergy_dispenser);
        Runtime.getRuntime().gc();
        RouterResponseRelay routerResponseRelay=this;
        RouterResponseSynergy routerResponseSynergy=this;
//        ServerRelay serverRelay=new ServerRelay("192.168.1.104",54301,this);
        ServerRelay.getInstance("192.168.1.104",54301).setListner(routerResponseRelay);
        ServerSynery.getInstance("192.168.1.104",54302).setListner(routerResponseSynergy);
//        ServerSynery serverSynery=new ServerSynery("192.168.1.104",54302);
//        serverSynery.setListner(routerResponseSynergy);
        handlerChinese = new Handler();
        response = SharedPref.getLoginResponse();
        database = FirebaseDatabase.getInstance();
        getTOtlizer=0;
        if (response.getData().get(0).getAtgDataList().isEmpty()) {
            atgSerialNoTank1 = "1234";
        } else {
            atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_atg_serial_no();

        }
        atgDipChart = response.getData().get(0).getAtgDataList().get(0).getData_volume();
        myRef = database.getReference("livefueling").child(SharedPref.getVehicleId());
        atgdipChart = response.getData().get(0).getAtgDataList().get(0).getData_volume();
        dispenseLocalDatabaseHandler = new DispenseLocalDatabaseHandler(this);
        atgInitialCount = 0;
        isFuelingStart = false;
        if (portCloseHandler != null) {
            portCloseHandler.removeCallbacks(portCloseRunnable);
        }
        instance = this;
        getViewID();

        if (!SharedPref.getLoginResponse().getData().get(0).getAtgDataList().isEmpty()) {
            getATGDataFinetek();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context = SynergyDispenser.this;
        getNozzles();
        appDatabase = BrancoApp.getDb();
        tvTotalBalanceAvailable.setText(SharedPref.getAvailablevolume());
    }
    private void getATGDataFinetek() {
        if(ServerRelay.getSocket()!=null){
            sendCommandToRelay("#*SP31");
        }
        flagAtgHnadleRunning = true;
        atgHandler = new Handler();
        atgHandler.postDelayed(atgRunnable, 2500);
    }


    public SynergyDispenser() {
    }

    Runnable atgRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (response.getVehicle_data().get(0).getMakeOfAtg().contains("Finetech")) {

                } else if (response.getVehicle_data().get(0).getMakeOfAtg().contains("Tokheium")) {
                    sendCommandToRelay("M"+atgSerialNoTank1+"\r");

                } else {
                    sendCommandToRelay("M"+atgSerialNoTank1+"\r");
                }
            } finally {
                atgHandler.postDelayed(atgRunnable, 1500);
            }
        }
    };

    private void sendCommandToRelay(String s) {
        if(ServerRelay.getSocket()!=null) {
            Util.writeAll(ServerRelay.getSocket(), s.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {

                }
            });
        }

    }

    public static SynergyDispenser getInstance() {
        return instance;
    }


    private void getOrderData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (SharedPref.getOfflineOrderList() != null) {
                for (int i = 0; i < SharedPref.getOfflineOrderList().size(); i++) {
                    Log.d("Offline", "Offline");
                    if (getIntent().getIntExtra("position", 0) == Integer.parseInt(SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId())) {
                        position = i;
                        transactionId = SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId();
                        getFuelingStatus(transactionId);
                        if (SharedPref.getOfflineOrderList().get(i).getProgress() != null) {
                            fuelDetails = SharedPref.getOfflineOrderList().get(i).getProgress();
                            Log.d("Asset",fuelDetails.toString());
                        }
                        orderDetailed = SharedPref.getOfflineOrderList().get(i);
                        Log.d("Asset2",orderDetailed.toString());
                        asset = SharedPref.getOfflineOrderList().get(i).getAssets();
                        Log.d("Asset1",asset.toString());
                    }
                }
            }
            isFromFreshDispense = intent.getBooleanExtra("isFromFreshDispense", false);
            Log.v("isFromFreshDispence", "Dispence" + isFromFreshDispense);
            Log.v("ATG_STATUS", "StatusForUpdateAtg" + StatusForUpdateAtg);
            if (isFromFreshDispense) {
                Toast.makeText(context, "Hello Fresh Dispense", Toast.LENGTH_SHORT).show();
                orderDetailed = (DeliveryInProgress) intent.getParcelableExtra("freshOrder");
                asset = orderDetailed.getAssets();
                freshDispensePresetValue = orderDetailed.getOrder().get(0).getQty();
                dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", freshDispensePresetValue));
                dispenserPresetVolume.setText(String.format("%s", freshDispensePresetValue));
                currentVehicleLat = "00.0000";
                currentVehicleLong = "00.0000";
                price = orderDetailed.getProgress().getFuelPrice();
                tvSiteLatitude.setText("Lat: " + currentVehicleLat);
                tvSiteLongitude.setText("Long: " + currentVehicleLong);
            } else {
                order = intent.getParcelableExtra("orderDetail");
                Log.v("OrderQty", order.getQuantity() + "");
                transactionId = order.getTransaction_id();
                getFuelingStatus(transactionId);
                if (SharedPref.getOfflineOrderList() != null) {
                    Log.v("terminiD", "" + getIntent().getIntExtra("position", 0));
                    for (int i = 0; i < SharedPref.getOfflineOrderList().size(); i++) {
                        if (Integer.parseInt(transactionId) == Integer.parseInt(SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId())) {
                            Log.v("offID", SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId());
                            position = i;
                            transactionId = SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId();
                            Log.v("trasationId", transactionId);

                            if (SharedPref.getOfflineOrderList().get(i).getProgress() != null) {
                                fuelDetails = SharedPref.getOfflineOrderList().get(i).getProgress();
                            } else {
                                Log.v("fuelDetails", "" + fuelDetails.toString());
                            }
                            orderDetailed = SharedPref.getOfflineOrderList().get(i);
                            asset = SharedPref.getOfflineOrderList().get(i).getAssets();
                            Log.v("OrderDetails", SharedPref.getOfflineOrderList().get(i).getOrder().toString());
                        }
                    }
                } else {
                    Log.v("offlineData", "null");
                }
                Log.v("trasectionid", transactionId);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getOrderFullDetails(order.getTransaction_id());
                }
                if (order != null) {
                    try {
                        dispenserLocationInfo.setText(String.format(" %s", order.getLocation_name()));
                        dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", order.getQuantity()));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    private void getAssetLatLongApi() {
        showAssetDialog(orderDetailed);
    }
    private void getNozzles() {
        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getNo_of_nozzle().equals("2")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SynergyDispenser.this);
            builder.setTitle("Select Nozzle");
            builder.setCancelable(false);
            final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
            TextView nozzle1 = customLayout.findViewById(R.id.nozzle1);
            TextView nozzle2 = customLayout.findViewById(R.id.nozzle2);
            nozzle1.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    selected = 1;
                    nozzle1.setTextColor(context.getColor(R.color.green));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        nozzle2.setTextColor(context.getColor(R.color.color_blue));
                    }
                }
            });

            nozzle2.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    selected = 2;
                    nozzle2.setTextColor(context.getColor(R.color.green));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        nozzle1.setTextColor(context.getColor(R.color.color_blue));
                    }
                }
            });
            builder.setView(customLayout);
            // add a button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    noozle = selected;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void showAnalogReadingIntial(String intialreading) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                analogReadingVol_i.setText("Intial Diesel Level :"+intialreading);
            }
        });
    }

    private void showAnalogReadingFinal(String finalReading) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                analogReadingVol_f.setText("Final Diesel Level :"+finalReading);
            }
        });

    }

    private double convertlat(String lat, String d) {
        Double latitude = 0.0;
        String degreemin = null;
        try {
            degreemin = lat.substring(0, lat.indexOf("."));
        } catch (Exception e) {

        }
        if (degreemin != null) {
            if (degreemin.length() == 5) {
                String latdegree = degreemin.substring(0, 3);
                String latmin = lat.substring(3, lat.length());
                if (d.contains("E") || d.contains("N")) {
                    latitude = +Double.parseDouble(latdegree) + Double.parseDouble(latmin) / 60;
                } else {
                    latitude = -Double.parseDouble(latdegree) + Double.parseDouble(latmin) / 60;
                }
            } else {
                String latdegree = degreemin.substring(0, 2);
                String latmin = lat.substring(2, lat.length());
                if (d.contains("E") || d.contains("N")) {
                    latitude = +Double.parseDouble(latdegree) + Double.parseDouble(latmin) / 60;
                } else {
                    latitude = -Double.parseDouble(latdegree) + Double.parseDouble(latmin) / 60;
                }
            }
            return latitude;

        } else {
            return 0.0;
        }
    }

    private int stringContainsItemFromList(String rmc, String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains(rmc)) {
                return i;
            }
        }
        return -1;

    }
    private void anologReading(String intial) {
        if (intial.contains("0")) {
            finalAnaloloReading = "0";
            String command = "@#DR" + eLockId;
            sendCommandToRelay(command);
        } else if (intial.contains("1")) {

            String command = "@#DR" + eLockId;
            sendCommandToRelay(command);
            finalAnaloloReading = "1";
        } else {

        }

    }
    private void sendData(String vol, String ammount, String status, String vehicleStatus, String assetId) {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.sendAssetStatus(status,assetId).enqueue(new Callback<AssetfuelingStatus>() {
            @Override
            public void onResponse(Call<AssetfuelingStatus> call, Response<AssetfuelingStatus> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<AssetfuelingStatus> call, Throwable t) {

            }
        });
    }

    private void getStatusD() {

        if (handlerChinese != null) {
            handlerChinese.postDelayed(getStatusRunnable, 1000);
        }
    }
    Runnable getStatusRunnable = new Runnable() {
        public void run() {
            try {
                Log.d("getStatusRun",""+getTOtlizer +Thread.currentThread().getName());
                if (getTOtlizer < 3) {

                    sendCommandToSynergy(hexStringToByteArray("FA0103A31121"));
                }
                else if (getTOtlizer == 3) {
//                    Log.v("CommandChinese", "FA0108B200000001012A69");
                    sendCommandToSynergy(hexStringToByteArray(Commands.SYNERGY_STATUS.toString()));
                    getTOtlizer++;
                } else {
                    sendCommandToSynergy(hexStringToByteArray(Commands.SYNERGY_STATUSPOLLING.toString()));
                }
            } finally {
                handlerChinese.postDelayed(this, 1000);
            }
        }
    };

    private void Printer() {
        if (isFuelingStart == true) {

            portCloseHandler = new Handler(Looper.getMainLooper());
            portCloseRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        isIntial = false;
                        getATGDataFinetek();


                    } catch (Exception e) {

                    }
                }
            };
            portCloseHandler.postDelayed(portCloseRunnable, 100);
            lockCloseDialog();
        } else {
            Toast.makeText(context, "Please Start Fueling", Toast.LENGTH_LONG).show();
        }


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
    private void getOrderFullDetails(String orderTransactionId) {
        Log.v("getCOde", "getCode");
//        orderTransactionID = orderTransactionId;

        SetRate = false;
        findViewById(R.id.tvReconnect).setEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.v("postiondespense", "" + position);
            if (fuelDetails != null) {
                price = fuelDetails.getFuelPrice();
                dispenserFuelRate.setText(fuelDetails.getFuelPrice());

//                tvSiteLatitude.setText("Lat: " + fuelDetails.getCurrentLat());
//                tvSiteLongitude.setText("Long: " + fuelDetails.getCurrentLong());

            } else {
                Log.v("fueldetailsK", "null");
            }

            if (fuelDetails != null && fuelDetails.getLocationBypass()) {
                currentVehicleLat = "00.0000";
                currentVehicleLong = "00.0000";
                orderLocationLat = orderDetailed.getOrder().get(0).getLatitude();
                orderLocationLong = orderDetailed.getOrder().get(0).getLogitude();
                Log.d("assetlat", "1");
                getAssetLatLongApi();
                Toast.makeText(context, "GPS ByPassed", Toast.LENGTH_LONG).show();
                Log.v("AssetLocation1", "GPS" + fuelDetails.getLocationBypass());
//

            } else if (SharedPref.getLoginResponse().getVehicle_data().get(0).getGeofencing().equals("2")) {
                Log.v("AssetLocation2", "GPS" + (SharedPref.getLoginResponse().getVehicle_data().get(0).getGeofencing()));
                Log.d("assetlat", "2");
                getAssetLatLongApi();
                Toast.makeText(context, "GPS ByPassed", Toast.LENGTH_LONG).show();
            } else {
                getAssetLatLongApi();
//
            }
            if (price != null && price.isEmpty()) {
                Toast.makeText(context, "Price Is missing,Please contact Backend Team", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void gpsByPassCheckDialog(String orderTransactionId, String mismatch, String range) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SynergyDispenser.this);
                builder.setTitle("The GPS is out of range");
                builder.setMessage("Current Mismatch is : " + mismatch + " metres" + "\n Allowable range is: " + range + " metres" + " in Current GPS status");
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Log.v("retry", "retry");
                            if (port4Selected) {
                                sendCommandToRelay("#*SP11");
                            } else {
                                sendCommandToRelay("#*SP11");
                            }
                        }
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
        });

    }
    private void getFuelingStatus(String tra) {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getFUelinState(tra).enqueue(new Callback<getFuelingState>() {
            @Override
            public void onResponse(Call<getFuelingState> call, Response<getFuelingState> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSucc()) {
                        if (response.body().getStatus().equalsIgnoreCase("0")) {
                            Toast.makeText(SynergyDispenser.this, "Last Fueling Not Complete", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SynergyDispenser.this, "Last Fueling Completed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getFuelingState> call, Throwable t) {

            }
        });
    }


    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void pumpStart() {
        startPump = true;
        Log.v(TAG, "S_PUMP_START" + "START");
        Click click = new Click() {
            @Override
            public void ClickK(boolean b) {
                eLockOn();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateTime = dateFormat.format(new Date());
                fuelingStartTime = currentDateTime;
                sendCommandToSynergy(hexStringToByteArray(Commands.SYNERGY_STARTPUMP.toString()));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anologReading("0");

                    }
                }, 500);
            }
        };
        AlertDIalogFragment alertDIalogFragment = new AlertDIalogFragment("Start Fueling", SynergyDispenser.this, click);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.add(alertDIalogFragment, "Start Fueling");
        ft.commitAllowingStateLoss();
    }

    public void nozzleRelayStop() {
        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getMakeOfRelayBoard().equalsIgnoreCase("IKNOWVATIONS")) {
            sendCommandToRelay("#*RL10");
        } else {
            sendCommandToRelay("#*RL10");
        }
    }

    private void nozzleRelayStart() {
            sendCommandToRelay("#*RL11");
            sendCommandToRelay("#*RL20");
    }

    private void getViewID() {
        //  setTOIdleBtn = findViewById(R.id.setTOIdleBtn);
        tvCurrentDispensedFuelChargeAmount = findViewById(R.id.currentDispensedFuelChargeAmount);
        ll_btn_visibility = findViewById(R.id.ll_btn_visibility);
        tvCurrentFuelDispensedQnty = findViewById(R.id.currentFuelDispensedQnty);
        tvCurrentFuelRate = findViewById(R.id.currentFuelRate);
        nozzleOnHook = findViewById(R.id.nozzleOnHook);
        dispenserAssetId = findViewById(R.id.dispenserSelectedAssetId);
        dispenserFuelRate = findViewById(R.id.dispenserFuelRate);
        dispenserLocationInfo = findViewById(R.id.tvDispenserLocationId);
        dispenserSelectAssetId = findViewById(R.id.dispenserSelectAsset);
        dispenserPresetVolume = findViewById(R.id.dispenserPresetVolume);
        dispenserOrderQnty = findViewById(R.id.dispenserOrderQnty);
        analogReadingVol_i = findViewById(R.id.analogReadingVol_intial);
        analogReadingVol_f = findViewById(R.id.analogReadingVol_final);
//      setPresetEdt = findViewById(R.id.setPresetEdt);
        intialTotalizerTxt = findViewById(R.id.intialTotalizer);
        pumpStatusTxt = ((TextView) findViewById(R.id.pumpStatus));
        tvCommandExecutionTxt = ((TextView) findViewById(R.id.tv_command_in_execution));
        tvSiteLongitude = ((TextView) findViewById(R.id.site_longitude));
        tvSiteLatitude = ((TextView) findViewById(R.id.site_lattidue));
        tvByPassGPS = ((TextView) findViewById(R.id.tv_bypass_gps));
        tvGPSRange = ((TextView) findViewById(R.id.tv_gps_range));
        rF_IdTxt = ((TextView) findViewById(R.id.rfId));
        dispensingIn = ((TextView) findViewById(R.id.dispensingIn));
        tank1 = findViewById(R.id.tv_tank1);
        mConnectBluetooth = findViewById(R.id.tv_connect_bluetooth);
        connection_maintainedState = findViewById(R.id.pumpStatus);
        tvBtnStart = findViewById(R.id.btnStart);
        btnSuspend = findViewById(R.id.btnSuspend);
        tvTotalBalanceAvailable = findViewById(R.id.tv_total_balance_available);
//        tvTotalBalanceAvailable.setText(SharedPref.getAvailablevolume());
        waveLoadingViewTank1 = findViewById(R.id.waveLoadingViewTank1);
        waveLoadingViewTank2 = findViewById(R.id.waveLoadingViewTank2);
        tvWifiServer232General = findViewById(R.id.tv_wifi_server_status_285);
        tvWifiServer232Atg = findViewById(R.id.tv_wifi_server_status_285_atg);
        tvWifiServer232Rfid = findViewById(R.id.tv_wifi_server_status_285_rfid);
        tvWifiServer485General = findViewById(R.id.tv_wifi_server_status_485);
        tvWifiServer485Status = findViewById(R.id.tv_wifi_server_status_485_status);
        tvWifiServer485ReadTxn = findViewById(R.id.tv_wifi_server_status_485_read_txn);
        btnRefresh232485Server = findViewById(R.id.btn_refresh_232_485_server_status);


        Log.v("ATG VOLume", "SONAL" + SharedPref.getAvailablevolume());

        findViewById(R.id.btnSuspend).setOnClickListener(this);
        findViewById(R.id.nozzleOnHook).setOnClickListener(this);
        findViewById(R.id.btnResume).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        ((TextView) findViewById(R.id.btnStop)).setOnClickListener(this);
        dispenserSelectAssetId.setOnClickListener(this);
        //  tvTotalBalanceAvailable.setText(SharedPref.getAvailablevolume());
        // Log.v("ATG VOLUME",SharedPref.getAvailablevolume());
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public String checkSum(String command) {
        String checksum = "";
        checksum = "" + ((Integer.parseInt(crc16arc(command), 16) & Integer.parseInt("7f7f", 16)));
        Log.v("CheckSum", command + "," + Integer.toHexString(Integer.parseInt(checksum)));
        return Integer.toHexString(Integer.parseInt(checksum));
    }

    public String crc16arc(String args) {
        int[] table = {
                0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
                0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
                0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
                0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
                0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
                0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
                0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
                0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
                0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
                0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
                0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
                0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
                0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
                0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
                0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
                0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
                0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
                0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
                0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
                0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
                0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
                0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
                0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
                0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
                0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
                0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
                0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
                0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
                0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
                0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
                0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
                0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,
        };

        byte[] bytes = hexStringToByteArray(args);
        int crc = 0x0000;
        for (byte b : bytes) {
            crc = (crc >>> 8) ^ table[(crc ^ b) & 0xff];
        }
        Log.v("crc16Arc", args + "," + Integer.toHexString(crc));
        return Integer.toHexString(crc);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nozzleOnHook:
                sendCommandToRelay("#*RL10");
                break;
            case R.id.btnStart:
                Log.v("CLICK", "START");
                if (flagAtgHnadleRunning == true) {
                    atgHandler.removeCallbacks(atgRunnable);
                }
                dispensingIn.setText("DISPENSING IN:"+selectedAssetName);
                Date currentTime = Calendar.getInstance().getTime();
                findViewById(R.id.btnStart).setEnabled(false);
                findViewById(R.id.btnStart).setBackgroundColor(context.getColor(R.color.md_grey_500));

                findViewById(R.id.btnSuspend).setEnabled(true);
                findViewById(R.id.btnSuspend).setBackgroundColor(context.getColor(R.color.md_blue_500));

                findViewById(R.id.btnStop).setEnabled(false);
                findViewById(R.id.btnStop).setBackgroundColor(context.getColor(R.color.md_grey_500));

                findViewById(R.id.btnResume).setEnabled(false);
                findViewById(R.id.btnResume).setBackgroundColor(context.getColor(R.color.md_grey_500));

                nozzleOnHook.setEnabled(false);
                tvBtnStart.setEnabled(false);
                auth = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    nozzleOnHook.setBackgroundColor(context.getColor(R.color.md_grey_500));
                }
                dispensing();
                curentTotalizer = 0f;
                tvCurrentFuelDispensedQnty.setText("0");
                findViewById(R.id.btnStart).setEnabled(false);
                findViewById(R.id.btnStart).setBackgroundColor(context.getColor(R.color.md_grey_500));
                break;
            case R.id.waveLoadingViewTank1:
                isATGPort3Selected = false;
                break;
            case R.id.waveLoadingViewTank2:
                isATGPort4Selected = false;
                break;
            case R.id.btnStop:
                Log.v("CLICK", "STOP");
                findViewById(R.id.btnStart).setEnabled(false);
                findViewById(R.id.btnStart).setBackgroundColor(context.getColor(R.color.md_grey_500));

                findViewById(R.id.btnStop).setEnabled(false);
                findViewById(R.id.btnStop).setBackgroundColor(context.getColor(R.color.md_grey_500));
                findViewById(R.id.btnResume).setEnabled(false);
                findViewById(R.id.btnResume).setBackgroundColor(context.getColor(R.color.md_grey_500));
                findViewById(R.id.btnSuspend).setEnabled(false);
                findViewById(R.id.btnSuspend).setBackgroundColor(context.getColor(R.color.md_grey_500));
          /*      getATGDataFinetek();
                getATGdataDipChart();*/
                if (SharedPref.getDISPENSER().equalsIgnoreCase("Synergy")) {
                    sendCommandToSynergy(hexStringToByteArray(Commands.SYNERGY_STOP.toString()));
                    afterStopPressed();
                    Log.v("PUMP_STOP", "Synergy");
                }


                // }

                break;

            case R.id.tvReconnect:

                break;
            case R.id.btnSuspend:
                Log.v("CLICK", "SUSPEND");
                suspendClick = true;
                resumeClicked = false;
                relayBolean = false;
                Log.d("resumecalledSuspend", "1602");
                suspendSale();
                btnsuspendVisibility(false, "SUSPEND FALSE");


                break;
            case R.id.btnResume:

                if (tagReadingFaled == true) {
                    Log.d("resumecalledSuspend", "1610");
                    suspendSale();
                    btnsuspendVisibility(false, "SUSPEND FALSE");
                } else {
                    Log.d("resumecalled", "1611");
                    resumeSale();
                }
                relay20bolean = false;
                suspendClick = false;
                resumeClicked = true;
                break;
            case R.id.orderCompleted:

                break;
            case R.id.dispenserSelectAsset:
                Log.d("AssetDialog", "2");
                if(isBackPress) {
                    showAssetDialog(orderDetailed);
                }
                break;

            case R.id.btn_refresh_232_485_server_status:
                break;


           }
    }

    void resumeSale() {
        if (SharedPref.getDISPENSER().equalsIgnoreCase("Synergy")) {
            Log.v("RESUME SALE", SharedPref.getDISPENSER());
            relay21();
            btnsuspendVisibility(true, "SUSPEND TRUE");

        }
    }

    void suspendSale() {
        Log.d("resumecalledSuspend", "");
        if (SharedPref.getDISPENSER().equalsIgnoreCase("Synergy")) {
            relay20();
        }
    }

    public void afterStopPressed() {
        btnstopVisibility(false, "FALSE");
        if (isFuelingStart == true) {

            portCloseHandler = new Handler(Looper.getMainLooper());
            portCloseRunnable = new Runnable() {
                @Override
                public void run() {
                    portCloseHandler.postDelayed(portCloseRunnable, 1500);
                }
            };
            portCloseHandler.postDelayed(portCloseRunnable, 1500);
            lockCloseDialog();
        } else {
            Toast.makeText(context, "Please Start Fueling", Toast.LENGTH_LONG).show();
        }

//        try {
//            sendToServerStopPump();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void dispensing() {
        startPump = false;
        Log.v("PresetKam", String.valueOf(dispenserPresetVolume.getText()));
        setPreset(String.valueOf(dispenserPresetVolume.getText()));
    }



    public void setPreset(String volume) {
        volume = volume.replaceAll("[^0-9.]", "");
        if (builder == null)
            builder = new AlertDialog.Builder(SynergyDispenser.this);
        String finalVolume = volume;

        builder.setMessage("Please Take Nozzle to fueling position")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(context, "Wait few seconds", Toast.LENGTH_LONG).show();
                        sendCommandToRelay("#*RL20");

                        if (port1Selected) {
                            sendCommandToRelay("#*SP21");

                        } else {

                            sendCommandToRelay("#*SP21");
                        }


                        presetHandler = new Handler();
                        presetrunnable = new Runnable() {
                            @Override
                            public void run() {
                                setPresetWithoutStart(finalVolume);

                            }
                        };
                        presetHandler.postDelayed(presetrunnable, 200);


                    }
                })
                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        if (alert == null) {
            alert = builder.create();
        }
        if (!alert.isShowing()) {
            alert.show();
        }


    }


    private void lockCloseDialog() {

        lcBleen = false;
        if (lock == 0) {
            lock++;
//            fuelingState = false;
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            final View customLayout = getLayoutInflater().inflate(R.layout.countdown_layout, null);
            dialog.setTitle("Lock Closed in");
            dialog.setView(customLayout);
            AlertDialog alert = dialog.create();
            TextView count = customLayout.findViewById(R.id.count);
            TextView closeBtn = customLayout.findViewById(R.id.closeBtn);


            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                private static final int AUTO_DISMISS_MILLIS = 30000;

                @Override
                public void onShow(final DialogInterface dialog) {
                    final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                    final CharSequence negativeButtonText = defaultButton.getText();
                    new CountDownTimer(AUTO_DISMISS_MILLIS, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            count.setText(String.format(
                                    Locale.getDefault(), "%s (%d)",
                                    negativeButtonText,
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                            ) + " Sec");
                        }

                        @Override
                        public void onFinish() {
//                            executor.shutdown();
//                            isIntialATG=true;
                            if (handlerChinese != null) {
                                handlerChinese.removeCallbacks(getStatusRunnable);
                            }
                            Date currentTime = Calendar.getInstance().getTime();
                            fuelingEndTime = String.valueOf(currentTime);
                            isDispenseComplete = true;


                            if (((AlertDialog) dialog).isShowing()) {
                                try {
                                    dialog.dismiss();
                                    Bundle bundle = new Bundle();
                                    //     bundle.putString("FuelDispensed", String.valueOf(tvCurrentFuelDispensedQnty.getText()));//
                                    bundle.putString("FuelDispensed", finalVolume);//
                                    //bundle.putString("CurrentUserCharge", String.valueOf(tvCurrentDispensedFuelChargeAmount.getText()));
                                    //     bundle.putString("CurrentUserCharge", String.valueOf(Float.parseFloat(String.valueOf(tvCurrentFuelDispensedQnty.getText())) * Float.parseFloat(String.valueOf(dispenserFuelRate.getText()))));
                                    bundle.putString("CurrentUserCharge", finalAmount);
                                    bundle.putString("FuelRate", mFinalPrice);
                                    bundle.putString("startTime", startedTime);
                                    bundle.putString("selectedAsset", selectedAssetId);
                                    bundle.putString("selectedAssetName", selectedAssetName);
                                    bundle.putBoolean("rfidEnabled", isRFidEnabled);
                                    bundle.putString("assetRegNo", "" + assetRegNo);
                                    bundle.putBoolean("rfidByPass", isRFidByPass);
                                    bundle.putString("rfidTagId", rfIdTagId);
                                    if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().isEmpty()) {
                                        bundle.putString("atgStart", atgInitialReading);
                                        bundle.putString("atgEnd", atgFinalReading);
                                        // bundle.putString("atgStart",  SharedPref.getAtgInitialReading());
                                        Log.e("ATG_INITIAL", "TEST22" + atgInitialReading);
                                    } else {
                                        if (atgInitialReading != null) {
                                            bundle.putString("atgStart", atgInitialReading);
                                        }
                                        if (atgFinalReading != null) {
                                            bundle.putString("atgEnd", atgFinalReading);
                                        }
                                    }
                                    Log.d("volumeofAtg", atgInitialReading + "," + atgFinalReading);
                                    bundle.putString("orderDate", orderDetailed.getOrder().get(0).getOrderDate());
                                    bundle.putParcelable("orderDetail", orderDetailed);
                                    bundle.putString("atgdipChartN", atgdipChartN);
                                    bundle.putString("fuelingStartTime", fuelingStartTime);
                                    bundle.putString("fuelingEndTime", fuelingEndTime);
                                    bundle.putString("finalVolumeTotalizer", finalVolumeTotalizer);
                                    bundle.putString("initalVolumeTotalizer", initialVolumeTotalizer);
                                    bundle.putString("currentLat", currentVehicleLat);
                                    bundle.putString("currentLong", currentVehicleLong);
                                    Log.v("atgdipChartN", "atgVol--" + atgdipChartN + "==" + initialVolumeTotalizer);
                                    Log.v("isFromFreshDispence", "Dispence");
                                    bundle.putBoolean("isFromFreshDispense", false);
                                    bundle.putString("assetStartReading", intialreading);
                                    bundle.putString("assetEndtReading", finalreading);
                                    Click click = new Click() {
                                        @Override
                                        public void ClickK(boolean b) {
                                            if (b) {
                                                Log.v("ActivityNot", "Finish");
                                                finish();
                                            } else {
                                                try {
//                                                    isIntial = true;
                                                    Log.v("ActivityNot", "notFinish");
                                                    Intent intent = getIntent();
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    finish();
                                                    startActivity(intent);
                                                } catch (Exception e) {

                                                }
                                            }
                                        }
                                    };
                                    presetHandler.removeCallbacks(presetrunnable);
//                                    if(flagAtgHnadleRunning==true){
//                                        atgHandler.removeCallbacks(atgRunnable);
//                                    }
                                    if (atgHandler != null) {
                                        atgHandler.removeCallbacks(atgRunnable);
                                    }
                                    AddReadingsDialog addReadingsDialog = new AddReadingsDialog(click);
                                    addReadingsDialog.setCancelable(false);
                                    addReadingsDialog.setArguments(bundle);
                                    addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());
                                    Log.v("AddreadingOP", "opOP" + "CALL 2");
                                    portCloseHandler.removeCallbacks(portCloseRunnable);

                                    if (handlerChinese != null) {
                                        handlerChinese.removeCallbacks(getStatusRunnable);
                                    }
//                                    executor.shutdown();
                                } catch (Exception e) {

                                }
                                Log.v("AddreadingOP", "opOP");
                            }
                        }
                    }.start();
                }
            });
//            closeBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (alert.isShowing()) {
//                        alert.dismiss();
//                    }
////                    executor.shutdown();
//                    if (handlerChinese != null) {
//                        handlerChinese.removeCallbacks(getStatusRunnable);
//                    }
//                    Date currentTime = Calendar.getInstance().getTime();
//                    fuelingEndTime = String.valueOf(currentTime);
//                    isDispenseComplete = true;
//                    Bundle bundle = new Bundle();
//                    try {
//                        bundle.putString("FuelDispensed", finalVolume);
//                        bundle.putString("CurrentUserCharge", String.valueOf(Float.parseFloat(finalVolume) * Float.parseFloat(String.valueOf(dispenserFuelRate.getText().toString()))));
//                        bundle.putString("FuelRate", String.valueOf(dispenserFuelRate.getText().toString()));
//                        bundle.putString("startTime", startedTime);
//                        bundle.putString("selectedAsset", selectedAssetId);
//                        bundle.putString("selectedAssetName", selectedAssetName);
//                        bundle.putBoolean("rfidEnabled", isRFidEnabled);
//                        bundle.putBoolean("rfidByPass", isRFidByPass);
//                        bundle.putString("rfidTagId", rfIdTagId);
//                        bundle.putString("assetRegNo", "" + assetRegNo);
//                        bundle.putString("orderDate", orderDetailed.getOrder().get(0).getOrderDate());
//                        bundle.putParcelable("orderDetail", orderDetailed);
//                        bundle.putString("atgdipChartN", atgdipChartN);
//                        bundle.putString("fuelingStartTime", fuelingStartTime);
//                        bundle.putString("fuelingEndTime", fuelingEndTime);
//                        bundle.putString("finalVolumeTotalizer", finalVolumeTotalizer);
//                        bundle.putString("initalVolumeTotalizer", initialVolumeTotalizer);
//                        bundle.putString("currentLat", currentVehicleLat);
//                        bundle.putString("currentLong", currentVehicleLong);
//                        bundle.putBoolean("isFromFreshDispense", false);
//                        bundle.putString("assetStartReading", intialreading);
//                        bundle.putString("assetEndtReading", finalreading);
//                        if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().isEmpty()) {
//                            bundle.putString("atgStart", atgInitialReading);
//                            bundle.putString("atgEnd", atgFinalReading);
//                        } else {
//                            if (atgInitialReading != null) {
//                                bundle.putString("atgStart", atgInitialReading);
//                                bundle.putString("atgEnd", atgFinalReading);
//                            }
//
//                        }
//                    }
//                    catch (NullPointerException e){
//
//                    }
//                    catch (NumberFormatException e){
//
//                    }
//
//                    Click click = new Click() {
//                        @Override
//                        public void ClickK(boolean b) {
//                            if (b) {
//                                finish();
//                                Log.d("ActivityNot1", "finish");
//                            } else {
//                                Log.d("ActivityNot1", "notfinish");
//                                Intent intent = getIntent();
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                finish();
////                                isIntial = true;
//                                startActivity(intent);
//                            }
//                        }
//                    };
//                    presetHandler.removeCallbacks(presetrunnable);
//                    portCloseHandler.removeCallbacks(portCloseRunnable);
//                    if (atgHandler != null) {
//                        atgHandler.removeCallbacks(atgRunnable);
//                    }
//                    AddReadingsDialog addReadingsDialog = new AddReadingsDialog(click);
//                    addReadingsDialog.setCancelable(false);
//                    addReadingsDialog.setArguments(bundle);
//                    addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());
//
////                    executor.shutdown();
//                    if (handlerChinese != null) {
//                        handlerChinese.removeCallbacks(getStatusRunnable);
//                    }
//                    client2.stopClint();
//                    client1.stopClint();
//                }
//            });
            alert.setCancelable(false);
            if (alert != null && !alert.isShowing()) {
                alert.show();
            }
        }
    }

    public void relay21() {
          sendCommandToRelay("#*RL21");
          sendCommandToRelay("#*RL11");
    }

    public void relay20() {
        sendCommandToRelay("#*RL20");
        sendCommandToRelay("#*RL11");
    }


    @SuppressLint("NewApi")
    public void setPresetWithoutStart(String s) {
        Log.v("Preset", s);
        if (s.contains(".")) {
            int isdoble = s.codePointCount(s.indexOf("."), s.length());
            if (isdoble == 2) {
                s = s + "0";
            }
            Log.v("Doblesiz", "" + isdoble);
        } else {
            s = s + ".00";
        }

        s = s.replace(".", "");
        String index = "0", index1 = "0", index2 = "0", index3 = "0", index4 = "0", index5 = "0", index6 = "0", index7 = "0";
        int length = s.length();
        if (length > 7) {
            try {
                index = s.substring(length - 1, length);
                index1 = s.substring(length - 2, length - 1);
                index2 = s.substring(length - 3, length - 2);
                index3 = s.substring(length - 4, length - 3);
                index4 = s.substring(length - 5, length - 4);
                index5 = s.substring(length - 6, length - 5);
                index6 = s.substring(length - 7, length - 6);
                index7 = s.substring(length - 8, length - 7);
            } catch (ArrayIndexOutOfBoundsException e) {

            } catch (StringIndexOutOfBoundsException e) {

            }
        } else if (length > 6) {

            index = s.substring(length - 1, length);
            index1 = s.substring(length - 2, length - 1);
            index2 = s.substring(length - 3, length - 2);
            index3 = s.substring(length - 4, length - 3);
            index4 = s.substring(length - 5, length - 4);
            index5 = s.substring(length - 6, length - 5);
            index6 = s.substring(length - 7, length - 6);
        } else if (length > 5) {
            index = s.substring(length - 1, length);
            index1 = s.substring(length - 2, length - 1);
            index2 = s.substring(length - 3, length - 2);
            index3 = s.substring(length - 4, length - 3);
            index4 = s.substring(length - 5, length - 4);
            index5 = s.substring(length - 6, length - 5);
        } else if (length > 4) {
            index = s.substring(length - 1, length);
            index1 = s.substring(length - 2, length - 1);
            index2 = s.substring(length - 3, length - 2);
            index3 = s.substring(length - 4, length - 3);
            index4 = s.substring(length - 5, length - 4);
        } else if (length > 3) {

            index = s.substring(length - 1, length);
            index1 = s.substring(length - 2, length - 1);
            index2 = s.substring(length - 3, length - 2);
            index3 = s.substring(length - 4, length - 3);
        } else if (length > 2) {
            index = s.substring(length - 1, length);
            index1 = s.substring(length - 2, length - 1);
            index2 = s.substring(length - 3, length - 2);
        } else if (length > 1) {
            index = s.substring(length - 1, length);
            index1 = s.substring(length - 2, length - 1);
        } else {
            index = s.substring(length - 1, length);
        }
        String Command = "FA0108E402" + index7 + index6 + index5 + index4 + index3 + index2 + index1 + index;
        String preset1[] = {Command + checkSum(Command)};
        if (checkSum(Command).length() == 3) {
            sendCommandToSynergy(hexStringToByteArray(Command + "0" + checkSum(Command)));

        } else {
            sendCommandToSynergy(hexStringToByteArray(Command + checkSum(Command)));
        }
    }

    private void eLockOn() {
        String command = "@#LO" + eLockId;
        sendCommandToRelay(command);
    }

    public void eLockOff() {
        String command = "@#LC" + eLockId;
        sendCommandToRelay(command);
    }


    private void showAssetDialog(DeliveryInProgress orderDetailed1) {
        auth = 0;
        try {
            if (asset != null && asset.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("assetList", asset);
                bundle.putString("status",orderDetailed1.getOrder().get(0).getStatus_type());
                String transaction = orderDetailed1.getOrder().get(0).getTransactionId();
                Log.d("transactionNumber", "" + transaction);
                String qty = dispenseLocalDatabaseHandler.getQuantity(transaction);
                Log.d("transactionNumber", "" + qty + "," + orderDetailed1.getOrder().get(0).getQty());
                float balancedQnty = Float.parseFloat(orderDetailed1.getOrder().get(0).getQty()) - Float.parseFloat(qty);
                bundle.putString("balance_qnty", String.format("%.2f", balancedQnty));
                dispenserPresetVolume.setText(balancedQnty + "");
                AssetListDialog assetListDialog = new AssetListDialog();

                assetListDialog.setAssetListener(new OnAssetOperation() {
                    @Override
                    public void OnAssetSelected(String assetId, String remainingBalance, String assetName, Asset assets, int postion) {
                        atgHandler.removeCallbacks(atgRunnable);
                            selectedAsset = assets;
                            eLockId = assets.getElock_serial();
                             rfIdTagId = assets.getAssetsTagid();
                             sendCommandToRelay("#*SP00");
                        sendCommandToRelay("#*SP11");
                             Log.d("AssetDetail","Asset"+selectedAsset+",elock="+eLockId+",tagid="+rfIdTagId);
//                        rfIdTagId = "1010011608205732";
                        if (selectedAsset == null) {
                            selectedAsset = asset.get(postion);
                            assetRegNo = asset.get(postion).getRegistration_no();
                            selectedAssetId = asset.get(postion).getId();
                            eLockId = assets.getElock_serial();
                            sendCommandToRelay("#*SP00");
                            sendCommandToRelay("#*SP11");
                             rfIdTagId = assets.getAssetsTagid();
                            Log.d("AssetDetail","Asset"+selectedAsset+",elock1="+eLockId+",tagid="+rfIdTagId);
                            getAssetLat(assetId);

                            if (selectedAsset.getAssetsRfid().equalsIgnoreCase("1")) {
                                isRFidEnabled = true;
                            } else if (selectedAsset.getAssetsRfid().equalsIgnoreCase("0")) {
                                isRFidEnabled = false;
                            }
                            if (selectedAsset.getAssetsBypassRfid().equalsIgnoreCase("1")) {
                                isRFidByPass = true;
                            } else if (selectedAsset.getAssetsBypassRfid().equalsIgnoreCase("0")) {
                                isRFidByPass = false;
                            }


                        } else {
                            selectedAssetId = assetId;
                            Log.d("selectedAsset6", "" + selectedAsset);
                            assetRegNo = assets.getRegistration_no();
                            eLockId = assets.getElock_serial();
                            sendCommandToRelay("#*SP00");
                            sendCommandToRelay("#*SP11");
                            getAssetLat(assetId);
                            selectedAssetName = assetName;
                            rfIdTagId = assets.getAssetsTagid();
                            Log.d("AssetDetail","Asset"+selectedAsset+",elock2="+eLockId+",tagid="+rfIdTagId);
                            if (assets.getAssetsRfid().equalsIgnoreCase("1")) {
                                isRFidEnabled = true;
                            } else if (assets.getAssetsRfid().equalsIgnoreCase("0")) {
                                isRFidEnabled = false;
                            }
                            if (assets.getAssetsBypassRfid().equalsIgnoreCase("1")) {
                                isRFidByPass = true;
                            } else if (assets.getAssetsBypassRfid().equalsIgnoreCase("0")) {
                                isRFidByPass = false;
                            }
                        }

                        LayoutInflater inflater = getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.selected_asset_qnty_dialog, null);
                        final EditText et_qnty = alertLayout.findViewById(R.id.et_qnty);
                        final TextView done = alertLayout.findViewById(R.id.done);


                        if (!isRFidEnabled) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(SynergyDispenser.this);
                            if(orderDetailed1.getOrder().get(0).getStatus_type()!=null) {
                                if (orderDetailed1.getOrder().get(0).getStatus_type().contains("1")) {
                                    //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
                                    alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", Double.parseDouble(remainingBalance)) + ")");  //Added by Laukendra on 15-11-19
                                    // this is set the view from XML inside AlertDialog
                                    alert.setView(alertLayout);
                                    // disallow cancel of AlertDialog on click of back button and outside touch
                                    alert.setCancelable(false);
                                    AlertDialog dialog = alert.create();
                                    alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Log.d("AssetDialog", "3");
                                            showAssetDialog(orderDetailed);
                                        }
                                    });

                                    done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //......todo..............monday..................
                                            String qnty = et_qnty.getText().toString();
                                            sendCommandToRelay("#*SP11");
                                            //0.99 prev
                                            if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.00) {
                                                Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else
                                                Log.v("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.00));
                                            if (Float.parseFloat(qnty) <= Double.parseDouble(remainingBalance)) {
                                                dispenserPresetVolume.setText("" + (Double.parseDouble(qnty)));
                                                dispenserAssetId.setText(assets.getAssetName());
//                                    order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty))); //Commented by Laukendra on 15-11-19
//                                    Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + (balancedQnty), Toast.LENGTH_SHORT).show(); //Commented by Laukendra on 15-11-19
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Insufficient Balance Left " + remainingBalance, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    dialog.show();
                                }
                                else {
                                    alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
                                    // this is set the view from XML inside AlertDialog
                                    alert.setView(alertLayout);
                                    // disallow cancel of AlertDialog on click of back button and outside touch
                                    alert.setCancelable(false);
                                    AlertDialog dialog = alert.create();
                                    alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Log.d("AssetDialog", "3");
                                            showAssetDialog(orderDetailed);
                                        }
                                    });

                                    done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //......todo..............monday..................
                                            String qnty = et_qnty.getText().toString();
                                            //0.99 prev
                                            if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.00) {
                                                Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else
                                                Log.v("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.00));
                                            if (Float.parseFloat(qnty) <= balancedQnty) {
                                                dispenserPresetVolume.setText("" + (Double.parseDouble(qnty)));
                                                dispenserAssetId.setText(assets.getAssetName());
//                                    order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty))); //Commented by Laukendra on 15-11-19
//                                    Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + (balancedQnty), Toast.LENGTH_SHORT).show(); //Commented by Laukendra on 15-11-19
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Insufficient Balance Left  " + remainingBalance, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    dialog.show();

                                }
                            }
                            else {
                                if (orderDetailed1.getOrder().get(0).getStatus_type().contains("1")){
                                    alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", Double.parseDouble(remainingBalance)) + ")");  //Added by Laukendra on 15-11-19
                                    // this is set the view from XML inside AlertDialog
                                    alert.setView(alertLayout);
                                    // disallow cancel of AlertDialog on click of back button and outside touch
                                    alert.setCancelable(false);
                                    AlertDialog dialog = alert.create();
                                    alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Log.d("AssetDialog", "3");
                                            showAssetDialog(orderDetailed);
                                        }
                                    });

                                    done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //......todo..............monday..................
                                            String qnty = et_qnty.getText().toString();
                                            //0.99 prev
                                            if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.00) {
                                                Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else
                                                Log.v("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.00));
                                            if (Float.parseFloat(qnty) <= Double.parseDouble(remainingBalance)) {
                                                dispenserPresetVolume.setText("" + (Double.parseDouble(qnty)));
                                                dispenserAssetId.setText(assets.getAssetName());
//                                    order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty))); //Commented by Laukendra on 15-11-19
//                                    Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + (balancedQnty), Toast.LENGTH_SHORT).show(); //Commented by Laukendra on 15-11-19
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Insufficient Balance Left " + remainingBalance, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    dialog.show();

                                }
                                else {
                                    alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
                                    // this is set the view from XML inside AlertDialog
                                    alert.setView(alertLayout);
                                    // disallow cancel of AlertDialog on click of back button and outside touch
                                    alert.setCancelable(false);
                                    AlertDialog dialog = alert.create();
                                    alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Log.d("AssetDialog", "3");
                                            showAssetDialog(orderDetailed);
                                        }
                                    });

                                    done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //......todo..............monday..................
                                            String qnty = et_qnty.getText().toString();
                                            //0.99 prev
                                            if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.00) {
                                                Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else
                                                Log.v("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.00));
                                            if (Float.parseFloat(qnty) <= balancedQnty) {
                                                dispenserPresetVolume.setText("" + (Double.parseDouble(qnty)));
                                                dispenserAssetId.setText(assets.getAssetName());
//                                    order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty))); //Commented by Laukendra on 15-11-19
//                                    Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + (balancedQnty), Toast.LENGTH_SHORT).show(); //Commented by Laukendra on 15-11-19
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Insufficient Balance Left  " + remainingBalance, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    dialog.show();
                                }
                            }


                        } else if (!isRFidByPass && rfIdTagId.isEmpty()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SynergyDispenser.this);
                            builder.setCancelable(false);
                            builder.setTitle("Alert");
                            builder.setMessage("RFID TagId is empty. You can't dispense Fuel. Please contact the administrator.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(SynergyDispenser.this);
                            if(orderDetailed1.getOrder().get(0).getStatus_type()!=null) {
                                if (orderDetailed1.getOrder().get(0).getStatus_type().contains("1")) {
                                    //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
                                    alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", Double.parseDouble(remainingBalance)) + ")");  //Added by Laukendra on 15-11-19
                                    // this is set the view from XML inside AlertDialog
                                    alert.setView(alertLayout);
                                    // disallow cancel of AlertDialog on click of back button and outside touch
                                    alert.setCancelable(false);
                                    AlertDialog dialog = alert.create();
                                    alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Log.d("AssetDialog", "3");
                                            showAssetDialog(orderDetailed);
                                        }
                                    });

                                    done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //......todo..............monday..................
                                            String qnty = et_qnty.getText().toString();
                                            //0.99 prev
                                            if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.00) {
                                                Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                                return;
                                            } else
                                                Log.v("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.00));
                                            if (Float.parseFloat(qnty) <= Double.parseDouble(remainingBalance)) {
                                                dispenserPresetVolume.setText("" + (Double.parseDouble(qnty)));
                                                dispenserAssetId.setText(assets.getAssetName());
//                                    order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty))); //Commented by Laukendra on 15-11-19
//                                    Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + (balancedQnty), Toast.LENGTH_SHORT).show(); //Commented by Laukendra on 15-11-19
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Insufficient Balance Left " + remainingBalance, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    dialog.show();
                                }
                                else {
                                    alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
                                    alert.setView(alertLayout);
                                    alert.setCancelable(false);
                                    AlertDialog dialog = alert.create();
                                    alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Log.d("AssetDialog", "4");
                                            showAssetDialog(orderDetailed);
                                        }
                                    });

                                    done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

//                                    connectToSynergyWifi();
                                            String qnty = et_qnty.getText().toString();
                                            //... 0.99 prev
                                            if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.00) {
                                                Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            Log.v("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.00));
                                            if (Float.parseFloat(qnty) <= balancedQnty) {
                                                dispenserPresetVolume.setText("" + (Double.parseDouble(qnty)));
                                                dispenserAssetId.setText(assets.getAssetName());
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Insufficient Balance Left  " + remainingBalance, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    dialog.show();

                                }
                            }
                            else{

                                //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
                                alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
                                alert.setView(alertLayout);
                                alert.setCancelable(false);
                                AlertDialog dialog = alert.create();
                                alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Log.d("AssetDialog", "4");
                                        showAssetDialog(orderDetailed);
                                    }
                                });

                                done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

//                                    connectToSynergyWifi();
                                        String qnty = et_qnty.getText().toString();
                                        //... 0.99 prev
                                        if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.00) {
                                            Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Log.v("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.00));
                                        if (Float.parseFloat(qnty) <= balancedQnty) {
                                            dispenserPresetVolume.setText("" + (Double.parseDouble(qnty)));
                                            dispenserAssetId.setText(assets.getAssetName());
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getBaseContext(), "Insufficient Balance Left  " + remainingBalance, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialog.show();
                            }
                        }
                    }

                });

                assetListDialog.setArguments(bundle);
                assetListDialog.show(getSupportFragmentManager(), AssetListDialog.class.getSimpleName());
            } else if (asset != null && asset.size() == 1) { //This line added by Laukendra on 20-11-19

                getAssetLat(asset.get(0).getId());

                try {
                    assetRegNo = asset.get(0).getRegistration_no();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                float balancedQnty = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                dispenserPresetVolume.setText(String.format("%.2f", balancedQnty));
                selectedAssetId = asset.get(0).getId();
                selectedAssetName = asset.get(0).getAssetName();
                selectedAsset = asset.get(0);
                eLockId = asset.get(0).getElock_serial();
                atgdipChartDataVol = asset.get(0).getData_volume();
                if (asset.get(0).getAssetsRfid().equalsIgnoreCase("1")) {
                    isRFidEnabled = true;
                } else if (asset.get(0).getAssetsRfid().equalsIgnoreCase("0")) {
                    isRFidEnabled = false;
                }
                if (asset.get(0).getAssetsBypassRfid().equalsIgnoreCase("1")) {
                    isRFidByPass = true;
                } else if (asset.get(0).getAssetsBypassRfid().equalsIgnoreCase("0")) {
                    isRFidByPass = false;
                }
                if (!isRFidEnabled) {
                    float balancedQnty1 = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                    dispenserPresetVolume.setText(String.format("%.2f", balancedQnty1));
                    dispenserAssetId.setText(asset.get(0).getAssetName());
                    selectedAssetId = asset.get(0).getId();
                    eLockId = asset.get(0).getElock_serial();
                    selectedAssetName = asset.get(0).getAssetName();
                    atgdipChartDataVol = asset.get(0).getData_volume();
                } else if (!isRFidByPass && rfIdTagId.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SynergyDispenser.this);
                    builder.setCancelable(false);
                    builder.setTitle("Alert");
                    builder.setMessage("RFID TagId is empty. You can't dispense Fuel. Please contact the administrator.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    //  float balancedQnty2 = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                    float balancedQnty2 = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                    dispenserPresetVolume.setText(String.format("%.2f", balancedQnty2));
//                    Log.v("deleiveredQTY1", "" + orderDetailed.getProgress().getDeliveredData() + "," + dispenserPresetVolume.getText().toString());
                    dispenserAssetId.setText(asset.get(0).getAssetName());
                    selectedAssetId = asset.get(0).getId();
                    eLockId = asset.get(0).getElock_serial();
                    selectedAssetName = asset.get(0).getAssetName();
                    atgdipChartDataVol = asset.get(0).getData_volume();
                }

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getAssetLat(String assetId) {
        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getGeofencing().equals("2")) {
            if (port4Selected) {
              Log.d("latlngCalled",""+1);
              sendCommandToRelay("#*SP11");
            } else {
                Log.d("latlngCalled",""+2);
                sendCommandToRelay("#*SP11");
            }
            getStatusD();
        }
        else if (fuelDetails.getLocationBypass()) {

            if (port4Selected) {
                Log.d("latlngCalled",""+3);
                sendCommandToRelay("#*SP11");
            } else {
                Log.d("latlngCalled",""+4);
                sendCommandToRelay("#*SP11");
            }
            getStatusD();
        } else {
            Log.d("latlngCalled",""+5);
        }

    }


    @Override
    public void onBackPressed() {
        if (isBackPress == false) {
            Log.v("call", "call" + isBackPress);
      ;
            Toast.makeText(getApplicationContext(), "You can't go back until dispensing is completed", Toast.LENGTH_LONG).show();
        }
        else {
            ServerRelay.getInstance("192.168.1.104",54301).setListner(null);
            ServerSynery.getInstance("192.168.1.104",54302).setListner(null);


            Log.v("call", "call else" + isBackPress);
            isBackPress = true;

            if (timerK != null) {
                timerK.cancel();
            }

//            if (executor != null && !executor.isShutdown()) {
//                executor.shutdown();
//            }
            if (handlerChinese != null) {
                handlerChinese.removeCallbacks(getStatusRunnable);
            }
            super.onBackPressed();

//            finish();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (executor != null && !executor.isShutdown()) {
//            executor.shutdown();
//        }
        if (handlerChinese != null) {
            handlerChinese.removeCallbacks(getStatusRunnable);
        }
//        ServerRelay.getInstance("192.168.1.104",54301).setListner(null);
//        ServerSynery.getInstance("192.168.1.104",54302).setListner(null);


        if (timerK != null) {
            timerK.cancel();
        }




    }

    private void btnsuspendVisibility(boolean isVisible, String fl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isVisible) {

                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);

                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(true);

                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);

                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnStop)).setEnabled(false);

                } else {
                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);

                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);

                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                    ((TextView) findViewById(R.id.btnResume)).setEnabled(true);

                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                    ((TextView) findViewById(R.id.btnStop)).setEnabled(true);
                }

            }
        });
        Log.v("BUTTON_VISIBILITY", "SUSPEND_BUTTON" + fl);

    }

    //	commented by Prasan 07.09.21
    private void btnstopVisibility(boolean isVisible, String fl) {
        Log.v("BUTTON_VISIBILITY", "STOP_BUTTON" + fl);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isVisible) {
                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);
                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);
                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                    ((TextView) findViewById(R.id.btnStop)).setEnabled(true);

                } else {
                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);
                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);
                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_500));
                    ((TextView) findViewById(R.id.btnStop)).setEnabled(false);
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("TEST_ONLY", "resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (flagAtgHnadleRunning == true) {
            atgHandler.removeCallbacks(atgRunnable);
        }
        try {

        } catch (NullPointerException e) {

        }
    }

    @Override
    public void RecivedData1(String responseAscci, String responseHex, boolean isConnected) {
        Log.d("hexRespnse1", responseAscci + "," + responseHex);
        if (isConnected) {
            if (connect1 == 0) {
                connect1++;
                Log.d("step1",""+isConnected+","+connect1);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getOrderData();
                    }
                },3000);

                sendCommandToRelay("#*RL20");
            }
        }
        if (responseHex.contains("A555") || responseHex.contains("550B52") || responseHex.contains("550152")) {
            isBattry = 0;
            try {
                String finalResponseAscii = responseHex;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(50); //You can manage the blinking time with this parameter
                        findViewById(R.id.blinkCircle).startAnimation(anim);
                        if (selectedAssetId != null) {
                            if (fuelingState) {
                                if (isRFidEnabled && !isRFidByPass) {
                                    if (finalResponseAscii.length() < 10) {
                                    } else {
                                        try {
                                            responseRFIDk = finalResponseAscii.substring(finalResponseAscii.indexOf("550") + 8, finalResponseAscii.indexOf("550") + 10);
                                            if (responseRFIDk.contains("40")) {
                                                rF_IdTxt.setText("RFID: Tag Reading Succeed");

                                                if (finalResponseAscii.contains(rfIdTagId)) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            rF_IdTxt.setText("RFID: TagId Matched");
                                                        }
                                                    });

                                                    mismatchEvent = 0;
                                                    isStartTimer = false;
                                                    try {
                                                        timer.cancel();
                                                    } catch (Exception e) {

                                                    }
                                                    rfidData = true;
//                                                            fuelingState = true;
                                                    if (suspendClick) {
                                                        suspendClick = false;
                                                        Log.d("resumecalledSuspend", "483");
                                                        suspendSale();
//
                                                        btnsuspendVisibility(false, "SUSPEND FALSE");
                                                    } else {
                                                        if ((relayCommandPassed == 1) || (resumeClicked)) {
                                                            suspendClick = false;
                                                            resumeClicked = false;
                                                            mismatchEvent = 0;
                                                            Log.d("resumecalled", "492");
                                                            resumeSale();
                                                            relayCommandPassed++;
                                                            btnsuspendVisibility(true, "SUSPEND TRUE");
                                                        }
                                                    }
                                                } else {
                                                    try {
                                                        rF_IdTxt.setText("RFID: TagId Not Matched");
                                                        rfidData = false;
//                                                                fuelingState = true;
                                                        tagReadingFaled = true;
                                                        Log.d("resumecalledSuspend", "507");
                                                        suspendSale();
                                                        Log.d("RLF", "RL20");
//
                                                    } catch (Exception e) {

                                                    }
                                                }
                                            }
                                            else if (responseRFIDk.contains("50") || responseRFIDk.contains("70") || responseRFIDk.contains("30") || responseRFIDk.contains("60")) {
                                                Log.d("mismatcheventcalled", "" + mismatchEvent);
                                                if (mismatchEvent >= 7){
                                                    isStartTimer = false;
                                                    suspendSale();
                                                    if(responseRFIDk.contains("60")){
                                                        rF_IdTxt.setText("Battery Low");
                                                    }
                                                    else {
                                                        rF_IdTxt.setText("RFID..: TagId not Read");
                                                    }
                                                    btnsuspendVisibility(false, "SUSPEND FALSE");
                                                    tagReadingFaled = true;
                                                    fuelingState = false;
                                                    mismatchEvent = 0;
                                                }
                                                else {
                                                    if (!isStartTimer) {
                                                        timer=new Timer();
                                                        timer.scheduleAtFixedRate(new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                isStartTimer = true;
                                                                mismatchEvent++;
                                                                if(mismatchEvent>=7){
                                                                    mismatchEvent = 0;
                                                                    timer.cancel();
                                                                    isStartTimer = false;
                                                                    suspendSale();
                                                                    Log.d("mismatcheventcalled1", "" + mismatchEvent);
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            rF_IdTxt.setText("RFID..: TagId not Read");
                                                                            btnsuspendVisibility(false, "SUSPEND FALSE");
                                                                        }
                                                                    });
                                                                    tagReadingFaled = true;
                                                                    fuelingState = false;

                                                                }
                                                                Log.d("mismatcheventcalled1", "" + mismatchEvent);
                                                            }
                                                        }, 0, 1000);
                                                    }
                                                }
                                            }
                                        } catch (IndexOutOfBoundsException e) {

                                        } catch (NullPointerException e) {

                                        } catch (IllegalArgumentException e) {

                                        } catch (IllegalStateException e) {

                                        }
                                    }
                                }
                            } else {



                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (responseAscci.contains("12552")) {
            try {
                int index = responseAscci.indexOf("12552");
                String volume = responseAscci.substring(index + 13, index + 21);
                analogLevelSenserReading = volume;
            } catch (Exception e) {
                Log.e("exception717", e.getMessage());
            }
        } else if (responseAscci.contains("PORT")) {
            if (responseAscci.contains("PORT 4 SELECTED")) {
                port4Selected = true;
                port2Selected = false;
                port1Selected = false;

            } else if (responseAscci.contains("PORT 2 SELECTED")) {
                port2Selected = true;
                port4Selected = false;
                port1Selected = false;
            } else if (responseAscci.contains("PORT 1 SELECTED")) {
                port1Selected = true;
                port2Selected = false;
                port4Selected = false;
            }

        } else if (responseAscci.contains("DIESEL LEVEL=")) {
            String subString = "DIESEL LEVEL=";
            Log.d("PranvinAnalog", responseAscci);
            String analogReading = null;
            try {
                analogReading = responseAscci.substring(responseAscci.indexOf(subString) + subString.length(), responseAscci.indexOf(".") + 3);
                Log.d("PranvinAnalog1", analogReading);
            } catch (IndexOutOfBoundsException e) {
                analogReading="0";
                Log.d("PranvinAnalogE", "" + responseAscci);
            }

            if (finalAnaloloReading.contains("0")) {
                intialreading = analogReading;
                showAnalogReadingIntial(intialreading);

            } else if (finalAnaloloReading.contains("1")) {
                finalreading = analogReading;
                showAnalogReadingFinal(finalreading);
            }


//                    responseAscii.substring(responseAscii.lastIndexOf("DIESEL LEVEL="),responseAscii.indexOf("RL"));

        } else if (responseAscci.contains("LOCK OPENED")) {
            Log.d("PravinCommand2", "" + responseAscci);

        } else if (responseAscci.contains("LOCK CLOSED")) {
            Log.d("PravinCommand3", "" + responseAscci);

        } else if (responseAscci.contains(atgSerialNoTank1)) {
            String tokheiumResponse = responseAscci.substring(0, responseAscci.indexOf("\n\r"));
            TokheiumCalculation tokheiumCalculation = new TokheiumCalculation();
            Float volume = tokheiumCalculation.getAtgState(tokheiumCalculation.response(tokheiumResponse), atgDipChart);
            if (isIntial) {
                atgInitialReading = String.format("%.2f", volume);
            } else {
                atgFinalReading = String.format("%.2f", volume);
            }
            Log.d("readingOfAtg", atgInitialReading + "," + atgFinalReading);
        } else if (responseHex.contains("0103")) {
            FineteckCalculation fineteckCalculation = new FineteckCalculation();
            String volumes = String.format("%.2f", fineteckCalculation.getAtgState(fineteckCalculation.response(responseHex), atgDipChart));
            if (isIntial) {
                atgInitialReading = String.format("%.2f", volumes);
            } else {
                atgFinalReading = String.format("%.2f", volumes);
            }
            Log.d("readingOfAtg", atgInitialReading + "," + atgFinalReading);
        } else if (responseAscci.contains("RMC")) {
            try {
                if (responseAscci.contains("N") && responseAscci.contains("E") && responseAscci.contains("A")) {
                    String lines[] = responseAscci.split("\\r?\\n");
                    String latlng = "";
                    int pos = stringContainsItemFromList("RMC", lines);
                    if (pos != -1) {
                        latlng = lines[pos];
                        ArrayList<String> latlngs = new ArrayList<>(Arrays.asList(latlng.split(",")));
                        if (latlngs.get(2).contains("A")) {
                            String lat = latlngs.get(3);
                            String d = latlngs.get(4);
                            String longi = latlngs.get(5);
                            String d1 = latlngs.get(6);
                            Double latitude = convertlat(lat, d);
                            Double longititude = convertlat(longi, d1);
                            currentVehicleLat = String.valueOf(latitude);
                            currentVehicleLong = String.valueOf(longititude);
                            sendCommandToRelay("#*SP00");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (assetShow == 0) {
                                        getOrderData();
                                        assetShow++;
                                    }
                                }
                            });

                        } else {
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

};



    @Override
    public void RecivedData(String responseAscci, String responseHex, boolean isConnected) {
        Log.v(TAG, "RecivedDataK" + responseHex+",hex"+responseHex +",ascii"+responseAscci);

        FuelingModel fuelingModel = new FuelingModel();
        try {
            if (isConnected) {
//                isBackPress = false;
            }

            if (responseHex.contains("FA")) {
                try {
                    statusK = responseHex.substring(responseHex.indexOf("FA") + 6, responseHex.indexOf("FA") + 8);

                } catch (IndexOutOfBoundsException e) {

                }

            }
            runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    if (statusK.contains("BA")) {
                        Log.d("chinese", "BA");
                        pumpStatusTxt.setText("STATE IDLE");
                        fuelingModel.setStatus(0);
                        ((TextView) findViewById(R.id.btnStart)).setVisibility(View.VISIBLE);
                        ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                        ((TextView) findViewById(R.id.pumpStatus)).setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                        ((TextView) findViewById(R.id.btnStart)).setEnabled(true);

                        if (fuelingState) {
                            Log.d("chinese", "BAFueling");
                            sendCommandToSynergy(hexStringToByteArray(Commands.SYNERGY_STATUSPOLLING.toString()));
                            if (responseHex.contains("FA01") && statusK.contains("BA")) {
                                try {
                                    Log.d("chinese", "BAFueling1");
                                    String volume = responseHex.substring(responseHex.indexOf("BA") + 2, responseHex.indexOf("BA") + 10);
                                    volume = volume.substring(0, volume.length() - 2) + "." + volume.substring(volume.length() - 2);
                                    volume = volume.replaceAll("[a-zA-Z]", "");
                                    String ammount = responseHex.substring(responseHex.indexOf("BA") + 10, responseHex.indexOf("BA") + 18);
                                    ammount = ammount.substring(0, ammount.length() - 2) + "." + ammount.substring(ammount.length() - 2);
                                    String rate1 = responseHex.substring(responseHex.indexOf("BA") + 18, responseHex.indexOf("BA") + 24);
                                    rate1 = rate1.substring(0, rate1.length() - 2) + "." + rate1.substring(rate1.length() - 2);
                                    rate1 = rate1.replaceAll("[a-zA-Z]", "");
                                    tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(volume)));
                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(rate1)));
                                    Log.v("RATE", rate1);
                                    ammount = ammount.replaceAll("[a-zA-Z]", "");
                                    tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(ammount)));
                                    fuelingModel.setAmount(Double.parseDouble(tvCurrentDispensedFuelChargeAmount.getText().toString()));
                                    fuelingModel.setVolume(Double.parseDouble(tvCurrentFuelDispensedQnty.getText().toString()));
//                                        fuelingModel.setRate(Double.parseDouble(tvCurrentFuelRate.getText().toString()));
                                    myRef.setValue(fuelingModel);
                                } catch (NullPointerException e) {

                                } catch (IndexOutOfBoundsException e) {

                                }
                            }
                        } else {
                            if (responseHex.contains("FA01") && statusK.contains("BA")) {
                                try {
                                    String rate1 = responseHex.substring(responseHex.indexOf("BA") + 18, responseHex.indexOf("BA") + 24);
                                    rate1 = rate1.substring(0, rate1.length() - 2) + "." + rate1.substring(rate1.length() - 2);
                                    rate1 = rate1.replaceAll("[a-zA-Z]", "");
                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(rate1)));
                                    fuelingModel.setRate(Double.parseDouble(tvCurrentFuelRate.getText().toString()));
                                } catch (IndexOutOfBoundsException e) {

                                }
//                                        myRef.setValue(fuelingModel);
                            }

                        }
//                                try {
//                                    if (response.contains("FA01") && statusK.contains("BA")) {
//
//                                        Log.v("STATE_IDLE", "STATE_IDLE");
//
//                                        String volume = response.substring(response.indexOf("BA") + 2, response.indexOf("BA") + 10);
//                                        volume = volume.substring(0, volume.length() - 2) + "." + volume.substring(volume.length() - 2);
//                                        volume = volume.replaceAll("[a-zA-Z]", "");
//                                        if (fuelingState) {
//                                            Log.v(TAG, "IDLE" + "IdleFuel");
//                                            if (!volume.equals("0.0") || !volume.equals("00.0")) {
//                                                Log.v(TAG, "IDLE" + "IdleFuel1");
////                                                getVolumeTotlizer();
////                                                Handler handler=new Handler();
////                                                handler.postDelayed(new Runnable() {
////                                                    @Override
////                                                    public void run() {
////
////                                                        client1.writeData(hexStringToByteArray(Commands.SYNERGY_STATUSPOLLING.toString()));
//////                                                        fuelingState=false;
////
////                                                    }
////                                                },00);
//
//                                            }
//                                        }
////                                        String ammount = response.substring(response.indexOf("BA") + 10, response.indexOf("BA") + 18);
////                                        ammount = ammount.substring(0, ammount.length() - 2) + "." + ammount.substring(ammount.length() - 2);
////                                        String rate1 = response.substring(response.indexOf("BA") + 18, response.indexOf("BA") + 24);
////                                        rate1 = rate1.substring(0, rate1.length() - 2) + "." + rate1.substring(rate1.length() - 2);
////                                        rate1 = rate1.replaceAll("[a-zA-Z]", "");
////                                        tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(volume)));
////                                        tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(rate1)));
////                                        Log.v("RATE", rate1);
////                                        ammount = ammount.replaceAll("[a-zA-Z]", "");
////                                        Log.v("VolumekjA", ammount);
////                                        Log.v(TAG, "IDLEDATA" + volume+","+rate1+","+ammount);
////
////                                        tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(ammount)));
////                                        sendData(tvCurrentFuelDispensedQnty.getText().toString(), tvCurrentDispensedFuelChargeAmount.getText().toString(), "STATE IDLE", "false", "" + selectedAsset.getId());
////                                        Log.v("volumeSyneBA", volume + "," + ammount);
////                                        finalVolume = (String.format(Locale.US, "%.2f", Double.parseDouble(volume)));
////                                        finalAmount = ammount;
////                                        Log.v("FinalVolumeBA", finalVolume + "," + ammount);
//                                        //  atgdipChartN=volume;
//                                    }
//
//                                } catch (Exception e) {
//
//                                }
                    } else if (statusK.contains("A3")) {
                        try {
                            elockCount = 0;
                            String volTotlizer = responseHex.substring(responseHex.indexOf("A3") + 2, responseHex.indexOf("A3") + 12);
                            String amountTotlizer = responseHex.substring(responseHex.indexOf("A3") + 12, responseHex.indexOf("A3") + 22);
                            volTotlizer = volTotlizer.substring(0, volTotlizer.length() - 2) + "." + volTotlizer.substring(volTotlizer.length() - 2);
                            amountTotlizer = amountTotlizer.substring(0, amountTotlizer.length() - 2) + "." + amountTotlizer.substring(amountTotlizer.length() - 2);
                            Log.v(TAG, "A3" + volTotlizer + "," + amountTotlizer);
                            initialVolumeTotalizer = volTotlizer;
                            Log.d("chinese", "BATotaliser");
                           if (volTotlizer.matches(".*[a-zA-Z]+.*")) {
                                Log.v("unValidData", "Unvalid");
                            } else {
                                if (!fuelingState) {
                                    getTOtlizer = 3;
                                    Log.d("getStatusRun1",""+getTOtlizer +Thread.currentThread().getName());
                                } else {
                                    if (Double.parseDouble(tvCurrentFuelDispensedQnty.getText().toString()) <= 0) {
                                        return;
                                    }
                                    Log.v("volTOTlizerAAm2", "correct");
                                    SharedPref.setAmmountTotlizer(amountTotlizer);
                                    SharedPref.setVolTotlizer(volTotlizer);

                                    if (lastTOT) {
                                        if (handlerChinese != null) {
                                            handlerChinese.removeCallbacks(getStatusRunnable);
                                        }
//                                                executor.shutdown();
                                        Printer();
                                    }

                                }

                            }
                        } catch (Exception e) {

                        }

                    } else if (statusK.contains("B3")) {
                        isBackPress = false;
                        if (elockCount <= 1) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    eLockOff();
                                    sendData("0", "0",
                                            "0", "0", "" + selectedAsset.getId());
                                    elockCount++;
                                }
                            }, 300);

                        }
                        Log.d("chinese", "Fueling");

                        Log.v(TAG, "B3 Fueling" + statusK);
                        fuelingState = true;
                        isFuelingStart = true;
                        if (responseHex.contains("FA01")) {
                            try {
                                Calendar calender = Calendar.getInstance();
                                calender.setTimeZone(TimeZone.getTimeZone("IST"));
                                startedTime = String.valueOf(calender.getTime());
                                pumpStatusTxt.setText("FUELING STATE");
                                findViewById(R.id.btnStart).setEnabled(false);
                                findViewById(R.id.btnStart).setBackgroundColor(getResources().getColor(R.color.md_grey_500));
                                //..TODO.............
                                isFuelingTxn = true;

                                String volume = responseHex.substring(responseHex.indexOf("B3") + 2, responseHex.indexOf("B3") + 10);
                                Log.v("volume", volume);
                                volume = volume.substring(0, volume.length() - 2) + "." + volume.substring(volume.length() - 2);
                                atgdipChartN = volume;
                                //     Log.e("AtgVol", "=" + atgdipChartN);
                                String ammount = responseHex.substring(responseHex.indexOf("B3") + 10, responseHex.indexOf("B3") + 18);
                                ammount = ammount.substring(0, ammount.length() - 2) + "." + ammount.substring(ammount.length() - 2);
                                volume = volume.replaceAll("[a-zA-Z]", "");
                                tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(volume)));

                                ammount = ammount.replaceAll("[a-zA-Z]", "");
                                tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(ammount)));
                                fuelingModel.setAmount(Double.parseDouble(tvCurrentDispensedFuelChargeAmount.getText().toString()));
                                fuelingModel.setVolume(Double.parseDouble(tvCurrentFuelDispensedQnty.getText().toString()));
                                fuelingModel.setRate(Double.parseDouble(tvCurrentFuelRate.getText().toString()));
                                fuelingModel.setStatus(1);
                                myRef.setValue(fuelingModel);


                            } catch (Exception e) {

                            }

                        } else {
                            Log.v("DataNotParse", "NotParse");
                        }
                    } else if (statusK.contains("E3")) {
                        elockCount = 0;
                        anologReading("1");
                        Log.d("chinese", "BAE3");
                        if (isFuelingStart) {
                            try {
                                sendData("0", "0",
                                        "1", "0", "" + selectedAsset.getId());
                                fuelingState = false;
                                sendCommandToSynergy(hexStringToByteArray(Commands.SYNERGY_STATUS.toString()));

                                finalVolume = String.valueOf(Double.parseDouble(responseHex.substring(16, 24).replaceFirst("^0+(?!$)", "")) / 100);
                                finalAmount = String.valueOf(Double.parseDouble(responseHex.substring(24, 32).replaceFirst("^0+(?!$)", "")) / 100);
                                String totaliser = String.valueOf(Double.parseDouble(responseHex.substring(32, 42).replaceFirst("^0+(?!$)", "")) / 100);
                                mFinalPrice = String.valueOf(Double.parseDouble(responseHex.substring(42, 48).replaceFirst("^0+(?!$)", "")) / 100);

                                Log.d("chinese", "BAE3Afterfueling"+finalAmount+","+finalVolume+","+mFinalPrice);
                                tvCurrentFuelDispensedQnty.setText(finalVolume);
                                tvCurrentDispensedFuelChargeAmount.setText(finalAmount);
                                tvCurrentFuelRate.setText(mFinalPrice);
                                fuelingModel.setAmount(Double.parseDouble(tvCurrentDispensedFuelChargeAmount.getText().toString()));
                                fuelingModel.setVolume(Double.parseDouble(tvCurrentFuelDispensedQnty.getText().toString()));
                                fuelingModel.setRate(Double.parseDouble(tvCurrentFuelRate.getText().toString()));
                                fuelingModel.setStatus(0);
                                myRef.setValue(fuelingModel);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String currentDateTime = dateFormat.format(new Date());
                                fuelingEndTime = currentDateTime;
                                finalVolumeTotalizer = totaliser;
//
//                                    sendData(finalVolume, finalAmount, "STATE IDLE", "false", "" + selectedAsset.getId());
                                Printer();
                            } catch (Exception e) {
                                Log.d("fuelingError", e.getMessage() + "," + response);
                            } catch (OutOfMemoryError e) {

                            }
                        } else {
                           sendCommandToSynergy(hexStringToByteArray(Commands.SYNERGY_STATUS.toString()));
                        }


                    }
                    if (statusK.contains("E4")) {
                        if (isRFidEnabled  && !isRFidByPass) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                if (!startPump) {
                                    nozzleRelayStart();
                                    pumpStart();
                                }
                                presetHandler.removeCallbacks(presetrunnable);
                            }
                        } else {
                            relay21();
                            presetHandler.removeCallbacks(presetrunnable);
                            pumpStart();

                        }
                    }
                }
            });

        } catch (NullPointerException e) {
            Log.d("Error found", e.getLocalizedMessage());
        } catch (ArrayIndexOutOfBoundsException e) {

        } catch (ArithmeticException e) {

        }

    }

    private void sendCommandToSynergy(byte[] hexStringToByteArray) {
        if(ServerSynery.getSocket()!=null){
            Util.writeAll(ServerSynery.getSocket(), hexStringToByteArray, new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {

                }
            });
        }
    }
}


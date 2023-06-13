package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.WaveLoadingView;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.AlertDIalogFragment;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.CommonUtils;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnStatusQueueListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnTxnQueueCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Command285Queue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandReadTotlizerQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandReadTxnQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandStatusQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server285_ReadRFID;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485_ReadTxn;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485_status;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.AddReadingsDialog;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.AssetListDialog;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.DispenseNowClick;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Server285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.ServerATG285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.PollStatus;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.Click;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.OnAssetOperation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.GetCurrentAssetLatlong;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LocationVehicleDatum;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Progress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.getFuelingState;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ChangePasswordbean;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.LocationUtil;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.StringUtils;
import com.syneygy.fillnow.Jpfmsdutymanager.printer.DeviceList;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue.convertToAscii;
public class DispenserUnitK extends AppCompatActivity implements RouterResponseListener, View.OnClickListener {
    private Handler authHandler = new Handler();
    private Runnable authRunnable;
    int auth = 0;
    boolean authBoolean = false;
    int idelcount = 0;
    boolean atgLastReading = false;
    int atgLastReadingInt = 0;
    int totsta = 0;
    int payableint = 0;
    boolean setRateB = false;
    public int tot = 0;
    int flag = 0;
    int fu = 0;
    int flagBtnStop = 0;
    private int adddiloagBolean = 1;
    private Context context;

    int atgcount = 0;
    private int txnreadingcount = 0;
    boolean SetRate = false;
    int atgcount2 = 0;
    private TextView dispenseNow;
    private TextView connection_maintainedState, rF_IdTxt;
    private ProgressDialog progressDialog;
    private EditText setPresetEdt;
    private TextView pumpStatusTxt, tvSiteLatitude, tvSiteLongitude, tvByPassGPS, tvGPSRange;
    private double intialTotalizer = 0d;
    private double curentTotalizer = 0d;
    public static String status = "";
    private TextView tvCurrentFuelDispensedQnty;
    private TextView tvCurrentFuelRate;
    public static int position;
    private TextView tvCurrentDispensedFuelChargeAmount;
    public static TextView tvCommandExecutionTxt;
    int test = 0;
    int test2 = 0;
    private String uniqueTransactionNumber = "";
    private TextView intialTotalizerTxt;
    private boolean fuelingState = false;
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
    public static String selectedAssetId;
    private String selectedAssetName;
    private DeliveryInProgress orderDetailed;
    int selected, noozle;
    private String currentVehicleLat, currentVehicleLong;
    private String orderLocationLat, orderLocationLong;
    private String price;
    private static final Double INTERPOLATION_VALUE_OF = 16.0;
    public static String mPresetValue;
    boolean StateIdel = false;
    private TextView tank1, mConnectBluetooth, tvBtnStart, tvTotalBalanceAvailable;
    public static BluetoothSocket btsocket;
    public static OutputStream outputStream;
    private Boolean isFromFreshDispense = false;
    private String freshDispensePresetValue = "";
    private LocationVehicleDatum vehicleDataForFresh;
    private boolean initialVolumeTotalizerCalled = true;
    private Command285Queue commandQueue7;
    private AppDatabase appDatabase;
    private boolean isRFidEnabled, isRFidByPass;
    private String rfIdTagId = "";
    private WaveLoadingView waveLoadingViewTank1, waveLoadingViewTank2;
    public static lisner lisner;
    private LinkedHashMap<String, JSONObject> atgLinkedHashMapTank1, atgLinkedHashMapTank2;
    private String atgSerialNoTank1 = "", atgSerialNoTank2 = "";
    Command285Queue commandQueue8;
    private TextView tvWifiServer232General, tvWifiServer232Atg, tvWifiServer232Rfid, tvWifiServer485General, tvWifiServer485Status, tvWifiServer485ReadTxn;
    private Button btnRefresh232485Server;
    private Runnable getATG1Runnable, getATG2Runnable;
    private ScheduledExecutorService executorAtg1, executorAtg2;
    private boolean isATGPort3Selected, isATGPort4Selected;
    private double tank1MaxVolume = 0f, tank2MaxVolume = 0f;
    private Asset selectedAsset;
    private TextView nozzleOnHook;
    public static Double balanceQunty;
    public TextView setTOIdleBtn;
    ProgressDialog setIdleProgressDialog;
    LocationManager locationManager;

    //Handlers and Runnables
    Runnable rateRunnable;
    Handler rateHandler;
    public static String receive;
    Handler presetHandler;
    Runnable presetrunnable;
    private Double lat, longi, course;
    private Boolean readRFid = false;

    public static interface lisner {
        void setClick(boolean state);
    }

    private void sendMessage(final String msg) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("192.168.1.170", 8000);

                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println(msg);
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String st = input.readLine();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String s = "  ";
                            if (st.trim().length() != 0)
                                Log.e("run", "runing");
                        }
                    });

                    output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispenser_unit);
        if (SharedPref.getMakeOfFCC().equalsIgnoreCase("Orpak")) {

            Log.e("MAkeofFcc2", "mm" + SharedPref.getMakeOfFCC());
            if (ServerATG285.getAsyncServer() == null || !ServerATG285.getAsyncServer().isRunning())
                new Server285("192.168.1.170", 8000, (RouterResponseListener) DispenserUnitK.this);

        } else {
            Log.e("MAkeofFcc", "kk" + SharedPref.getMakeOfFCC());
        }
        rateHandler = new Handler();
        rateRunnable = new Runnable() {
            @Override
            public void run() {
                setRate(fuelDetails.getFuelPrice());
            }
        };


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        context = DispenserUnitK.this;

       /* setTOIdleBtn = findViewById(R.id.setTOIdleBtn);

        setTOIdleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStateIdle();
            }
        });*/


        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getNo_of_nozzle().equals("2")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitK.this);
            builder.setTitle("Select Nozzle");
            builder.setCancelable(false);
            // set the custom layout
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
                    // send data from the AlertDialog to the Activity
                    noozle = selected;
                }
            });
            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        ((BrancoApp) context.getApplicationContext()).getAtgDataObserver().observe((AppCompatActivity) context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//

                if (atgLastReading) {
                    lastATGReading = s;
                } else {
                    Log.e("LivedataNullDK", s);
                    intialATGReading = s;
                }
                NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + s);
            }
        });

        tvCurrentDispensedFuelChargeAmount = findViewById(R.id.currentDispensedFuelChargeAmount);
        tvCurrentFuelDispensedQnty = findViewById(R.id.currentFuelDispensedQnty);
        tvCurrentFuelRate = findViewById(R.id.currentFuelRate);
        nozzleOnHook = findViewById(R.id.nozzleOnHook);

        dispenserAssetId = findViewById(R.id.dispenserSelectedAssetId);
        dispenserFuelRate = findViewById(R.id.dispenserFuelRate);

        dispenserLocationInfo = findViewById(R.id.tvDispenserLocationId);
        dispenserSelectAssetId = findViewById(R.id.dispenserSelectAsset);
        dispenserPresetVolume = findViewById(R.id.dispenserPresetVolume);
        dispenserOrderQnty = findViewById(R.id.dispenserOrderQnty);

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

        tvTotalBalanceAvailable = findViewById(R.id.tv_total_balance_available);
        tvTotalBalanceAvailable.setText(SharedPref.getAvailablevolume());

        waveLoadingViewTank1 = findViewById(R.id.waveLoadingViewTank1);
        waveLoadingViewTank2 = findViewById(R.id.waveLoadingViewTank2);

        tvWifiServer232General = findViewById(R.id.tv_wifi_server_status_285);
        tvWifiServer232Atg = findViewById(R.id.tv_wifi_server_status_285_atg);
        tvWifiServer232Rfid = findViewById(R.id.tv_wifi_server_status_285_rfid);
        tvWifiServer485General = findViewById(R.id.tv_wifi_server_status_485);
        tvWifiServer485Status = findViewById(R.id.tv_wifi_server_status_485_status);
        tvWifiServer485ReadTxn = findViewById(R.id.tv_wifi_server_status_485_read_txn);
        btnRefresh232485Server = findViewById(R.id.btn_refresh_232_485_server_status);

        btnRefresh232485Server.setOnClickListener(this);
        mConnectBluetooth.setOnClickListener(this);
//        connected();
        //nozzle on hook button by kamal
        nozzleOnHook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nozzleRelayStop();
            }
        });
        waveLoadingViewTank1.setOnClickListener(this);
        waveLoadingViewTank2.setOnClickListener(this);

        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btn_for_idlestate).setOnClickListener(this);
        ((TextView) findViewById(R.id.btn_for_idlestate)).setEnabled(true);

        findViewById(R.id.tvReconnect).setOnClickListener(this);
        findViewById(R.id.btnSuspend).setOnClickListener(this);
        findViewById(R.id.btnResume).setOnClickListener(this);
        findViewById(R.id.orderCompleted).setOnClickListener(this);
        dispenserSelectAssetId.setOnClickListener(this);

        appDatabase = BrancoApp.getDb();

        try {
            NavigationDrawerActivity.btsocket = DeviceList.getSocket();
            if (NavigationDrawerActivity.btsocket != null) {
                mConnectBluetooth.setText("Printer Connected");
                mConnectBluetooth.setBackgroundColor(ContextCompat.getColor(this, R.color.md_green_300));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("atgRun", "run");
                    if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size() > 1) {
                        String string1 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_volume();
                        Log.d("atgSerialNoTank1", string1);
                        if (string1 != null) {
                            atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
                            //  atgSerialNoTank1 = "23872"; //Testing
                            Log.d("AtgTankSerial", atgSerialNoTank1);
                            JSONObject jsonObject1 = new JSONObject(string1);
                            atgLinkedHashMapTank1 = new Gson().fromJson(jsonObject1.toString(), LinkedHashMap.class);

                            atgCount = atgLinkedHashMapTank1.size();
                            test = atgLinkedHashMapTank1.size();
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

                        String string2 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(1).getData_volume();
                        if (string2 != null) {
                            atgSerialNoTank2 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(1).getData_atg_serial_no();
                            // atgSerialNoTank2 = "23872"; //Testing
//                            JSONObject jsonObject2 = new JSONObject(string2);
//                            atgLinkedHashMapTank2 = new Gson().fromJson(jsonObject2.toString(), LinkedHashMap.class);

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
                            je.printStackTrace();
                        }
                        Log.d("Tank1AtgMaxVolume", tank1MaxVolume + "");
                        Log.d("Tank1MaxVolume", (tank1MaxVolume = tank1MaxVolume * .95) + "");

                    } else if (SharedPref.getLoginResponse().getData().get(0).atgDataList.size() == 1) {
                        waveLoadingViewTank2.setCenterTitle("N/A");
                        waveLoadingViewTank2.setOnClickListener(null);
                        String string = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
                        if (string != null) {
                            atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
                            // atgSerialNoTank1 = "23872"; //Testing
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
                        tvTotalBalanceAvailable.setText(SharedPref.getRecieveFuel());
                    }
                } catch (JSONException e) {

                    Log.e("ATG-Table-EXc", "Occured");
                    e.printStackTrace();

                }
            }
        }, 500);

        if (getIntent() != null) {
            Intent intent = getIntent();

            if (SharedPref.getOfflineOrderList() != null) {
                Log.e("terminiD", "" + getIntent().getIntExtra("position", 0));
                Log.e("terminiD1", "" + SharedPref.getOfflineOrderList().get(0).getOrder().get(0).getTransactionId());
                for (int i = 0; i < SharedPref.getOfflineOrderList().size(); i++) {
                    if (getIntent().getIntExtra("position", 0) == Integer.parseInt(SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId())) {

                        Log.e("offID", getIntent().getIntExtra("position", 0) + "," + SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId());
                        position = i;
                        transactionId = SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId();
                        getFuelingStatus(transactionId);
                        if (SharedPref.getOfflineOrderList().get(i).getProgress() != null) {
                            fuelDetails = SharedPref.getOfflineOrderList().get(i).getProgress();
                        } else {
                            Log.e("fuelDetails", "" + fuelDetails.toString());
                        }
                        orderDetailed = SharedPref.getOfflineOrderList().get(i);
                        asset = SharedPref.getOfflineOrderList().get(i).getAssets();
                        Log.e("OrderDetails", SharedPref.getOfflineOrderList().get(i).getOrder().toString());
                    }
                }
            }
            isFromFreshDispense = intent.getBooleanExtra("isFromFreshDispense", false);

            Log.e("isFromFreshDispence", "Dispence" + isFromFreshDispense);
            if (isFromFreshDispense) {
                Toast.makeText(context, "Hello Fresh Dispense", Toast.LENGTH_SHORT).show();

                //freshDispensePresetValue = intent.getStringExtra("fresh_dispense_preset");
                orderDetailed = (DeliveryInProgress) intent.getParcelableExtra("freshOrder");
                asset = orderDetailed.getAssets();
                //LocationDatum locationDatum = (LocationDatum) bundle.getSerializable("LocationData");
                //vehicleDataForFresh = (LocationVehicleDatum) bundle.getSerializable("VehicleData");
                //order = intent.getParcelableExtra("orderDetail");

//                transactionId = orderDetailed.getOrder().get(0).getTransactionId();
//                uniqueTransactionNumber = orderDetailed.getOrder().get(0).getTransactionId();

                freshDispensePresetValue = orderDetailed.getOrder().get(0).getQty();
                dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", freshDispensePresetValue));
                dispenserPresetVolume.setText(String.format("%s", freshDispensePresetValue));


                currentVehicleLat = orderDetailed.getProgress().getCurrentLat();
                currentVehicleLong = orderDetailed.getProgress().getCurrentLong();
                orderLocationLat = orderDetailed.getOrder().get(0).getLatitude(); //locationDatum.getLatitude();
                orderLocationLong = orderDetailed.getOrder().get(0).getLogitude(); //locationDatum.getLogitude();

                //price = vehicleDataForFresh.getNetPrice();
                price = orderDetailed.getProgress().getFuelPrice();
                tvSiteLatitude.setText("Lat: " + currentVehicleLat);
                tvSiteLongitude.setText("Long: " + currentVehicleLong);
                if (currentVehicleLat.equalsIgnoreCase("00.0000") && currentVehicleLong.equalsIgnoreCase("00.0000")) {
                    String[] st = {"#*SP31"};
                    Command285Queue commandQueue7 = new Command285Queue(st, new OnAllCommandCompleted() {
                        @Override
                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                            Log.e("latlongk", lastResponse);
                        }

                        @Override
                        public void onAllCommandCompleted(int currentCommand, String response) {
                            Log.e("latlongk2", response);
                        }
                    });
                    commandQueue7.doCommandChaining();
                }
                float gpsRange = Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
                //float gpsRange = Float.valueOf(vehicleDataForFresh.getGpsMismatchRange());
                float currentGpsDifference = Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))));
                Log.e("gpsRange", "" + gpsRange);
                Log.e("currentGpsDifference", "" + currentGpsDifference);
                //Commented on 24-01-20
//                if (gpsRange <= currentGpsDifference) {
//                    String mismatchRange = String.valueOf(currentGpsDifference - gpsRange);
//                    gpsByPassCheckFreshDispenseDialog(mismatchRange, SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
//                } else {
                Toast.makeText(context, "GPS Check Success", Toast.LENGTH_LONG).show();
                showAssetDialog(orderDetailed);
//                }
                if (price.isEmpty()) {
                    Toast.makeText(context, "Price Is missing,Please contact Backend Team", Toast.LENGTH_LONG).show();
                }
                try {
                    String valueToShow = String.valueOf(Double.valueOf(fuelDetails.getFuelPrice()) - Double.valueOf(fuelDetails.getDiscount()));
                    dispenserFuelRate.setText(String.format(" %s ", valueToShow));
                } catch (Exception e) {

                }
                //   checkConnectedSetPrice(valueToShow);
            } else {

                //stopServer(); //Added On 22-01-20

                order = intent.getParcelableExtra("orderDetail");
                Log.e("OrderQty", order.getQuantity() + "");
                transactionId = order.getTransaction_id();
                getFuelingStatus(transactionId);
                if (SharedPref.getOfflineOrderList() != null) {
                    Log.e("terminiD", "" + getIntent().getIntExtra("position", 0));
                    for (int i = 0; i < SharedPref.getOfflineOrderList().size(); i++) {
                        if (Integer.parseInt(transactionId) == Integer.parseInt(SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId())) {

                            Log.e("offID", SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId());
                            position = i;
                            transactionId = SharedPref.getOfflineOrderList().get(i).getOrder().get(0).getTransactionId();
                            Log.e("trasationId", transactionId);

                            if (SharedPref.getOfflineOrderList().get(i).getProgress() != null) {
                                fuelDetails = SharedPref.getOfflineOrderList().get(i).getProgress();
                            } else {
                                Log.e("fuelDetails", "" + fuelDetails.toString());
                            }
                            orderDetailed = SharedPref.getOfflineOrderList().get(i);
                            asset = SharedPref.getOfflineOrderList().get(i).getAssets();
                            Log.e("OrderDetails", SharedPref.getOfflineOrderList().get(i).getOrder().toString());
                        }
                    }
                }

                Log.e("trasectionid", transactionId);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getOrderFullDetails(order.getTransaction_id());
                }
                if (order != null) {
                    try {
                        //Toast.makeText(this, "Order Received", Toast.LENGTH_SHORT).show();
                        dispenserLocationInfo.setText(String.format(" %s", order.getLocation_name()));
                        dispenserPresetVolume.setText(String.format(" %s Lts", order.getQuantity()));
                        dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", order.getQuantity()));
                        uniqueTransactionNumber = order.getTransaction_id();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
//        stopServer();
//        connectToSynergyWifi();
        fuelingState = false;
        lisner = new lisner() {
            @Override
            public void setClick(boolean orderState) {
                Log.e("orderState", String.valueOf(orderState));
                if (orderState) {
                    finish();
                } else {
                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);
                    fuelingState = false;
                    getOrderFullDetails(transactionId);
                }
            }
        };
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Executing Command... ");

        connection_maintainedState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tvWifiServer232General.getText().toString().equalsIgnoreCase("connection maintained")) {
                    nozzleRelayStop();

                    // getStatusPoll();
                }
//                if (s != null && s.length() > 0) {
//                    if (s.toString().contains("Connecting")) {
//                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_300));
//                    } else {
//                        setStateIdle();
//                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_400));
//                    }
//
//
//                }
            }
        });

        connection_maintainedState.setSelected(true);


//        try {
//            uniqueTransactionNumber = order.getTransaction_id();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getStatusRunnable = new Runnable() {
                    public void run() {
                        if (Server485_status.getSocket() != null) {
//                            if (!status.equalsIgnoreCase("STATE IDLE")) {
                            getStatusPoll();

                            Log.e("GetStatusPool", "Pool" + status);
                        }

//                            } else {
//
//                                Log.e("GetStatusPool", status);
//                            }
                        Log.e("Server485_status", status);

                    }
                };
                executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(getStatusRunnable, 500, 500, TimeUnit.MILLISECONDS);
            }
        });
        tvCurrentFuelDispensedQnty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0)
                    try {
                        double currentPrice = Double.parseDouble(s.toString()) * Double.parseDouble(tvCurrentFuelRate.getText().toString());
                        if (currentPrice >= 0) {
                            tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", currentPrice));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });

        connectRfid();



    }

    private void connectRfid() {
        if (Server285_ReadRFID.getSocket() != null && Server285_ReadRFID.getAsyncServer().isRunning()) {
            Server285_ReadRFID.getAsyncServer().stop();
        }
        if (Server285_ReadRFID.getAsyncServer() == null || !Server285_ReadRFID.getAsyncServer().isRunning())
            new Server285_ReadRFID("192.168.1.103", 54311, (RouterResponseListener) context);
    }

    private void connected() {
        if (Server285.getAsyncServer() != null || Server285.getAsyncServer().isRunning()) {
            tvWifiServer232General.setText("Connected");
        }


//                    if (ServerATG285.getAsyncServer() == null || !ServerATG285.getAsyncServer().isRunning())
//                        new ServerATG285("192.168.1.103", 54310, (RouterResponseListener) context, atgSerialNoTank1, atgSerialNoTank2);
//        if (Server285_ReadRFID.getAsyncServer() != null || Server285_ReadRFID.getAsyncServer().isRunning()) {
//            tvWifiServer232Rfid.setText("Connected");
//            send285RFIDCommand("#*SP21");
//
//        }
        if (Server485.getAsyncServer() != null || Server485.getAsyncServer().isRunning()) {
            tvWifiServer485General.setText("Connected");
        }
        if (Server485_status.getAsyncStausServer() != null || Server485_status.getAsyncStausServer().isRunning()) {
            tvWifiServer485Status.setText("Connected");
        }
        if (Server485_ReadTxn.getAsyncReadTxnServer() != null || Server485_ReadTxn.getAsyncReadTxnServer().isRunning()) {
            tvWifiServer485ReadTxn.setText("Connected");
        }

        if (tvWifiServer232General.getText().toString().contains("Connected") &&
                tvWifiServer232Rfid.getText().toString().contains("Connected") &&
                tvWifiServer485General.getText().toString().contains("Connected") &&
                tvWifiServer485Status.getText().toString().contains("Connected") &&
                tvWifiServer485ReadTxn.getText().toString().contains("Connected")

        ) {
            connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));
        }
    }

    private void getFuelingStatus(String tra) {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getFUelinState(tra).enqueue(new Callback<getFuelingState>() {
            @Override
            public void onResponse(Call<getFuelingState> call, Response<getFuelingState> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSucc()) {
                        if (response.body().getStatus().equalsIgnoreCase("0")) {
                            Toast.makeText(DispenserUnitK.this, "Last Fueling Not Complete", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DispenserUnitK.this, "Last Fueling Completed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getFuelingState> call, Throwable t) {

            }
        });
    }

    private void updateFuelingStatus() {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.UpdateFUelinState(transactionId).enqueue(new Callback<getFuelingState>() {
            @Override
            public void onResponse(Call<getFuelingState> call, Response<getFuelingState> response) {
                if (response.isSuccessful()) {


                }
            }

            @Override
            public void onFailure(Call<getFuelingState> call, Throwable t) {

            }
        });
    }


    private void setIdleFulingState() {


        if (status.equalsIgnoreCase("FUELING STATE")) {
            Log.e("runFuelIdle", "k" + status);
            new DispenseNowClick(context).getPollState1(status);
        }
    }


    private void setStateIdle() {
        if (idelcount == 0) {

            if (status.equalsIgnoreCase("STATE IDLE") || status.equalsIgnoreCase("PRESET READY STATE")) {
                idelcount++;

                Log.e("setStateIdel", status);
            } else if (status.equalsIgnoreCase("PAYABLE STATE") ||
                    status.equalsIgnoreCase("STOPPED STATE") ||
                    status.equalsIgnoreCase("IN-OPERATIVE STATE") ||
                    !status.equalsIgnoreCase("FUELING STATE") ||
                    !status.equalsIgnoreCase("CALL STATE") ||
                    !status.equalsIgnoreCase("PRESET READY STATE")


            ) {
                Log.e("dot", status);
                if (status != null && !status.equals("")) {
                    Log.e("setStateIdelBBBrub", "setStateIdelBBB" + status);

                    new DispenseNowClick(context).getPollState1(status);
                    idelcount++;
                } else {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            setIdleProgressDialog.dismiss();
//                            setStateIdle();
//                        }
//                    }, 300);
                }
            } else if (connection_maintainedState.getText().toString().equalsIgnoreCase(getResources().getString(R.string.connection_maintained))) {

                idelcount = 0;
//                Log.e("contectsetidel", status);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setIdleProgressDialog.dismiss();
//                        setStateIdle();
//                    }
//                }, 300);
            } else {


            }
//                    }
//                }
//
//
//
//
//            }
//
//        });
//        commandStatus.doCommandChaining();

        }
    }


    public void getLatLogGPRMC(String sentence) {
        try {
            int begin = sentence.indexOf("$GPRMC");
            int end = sentence.indexOf("A*");
            sentence = sentence.substring(begin, end);
            if (sentence.startsWith("$GPRMC")) {
                String[] strValues = sentence.split(",");
                lat = Double.parseDouble(strValues[3]) * .01;
                if (strValues[4].charAt(0) == 'S') {
                    lat = -lat;
                }
                longi = Double.parseDouble(strValues[5]) * .01;
                if (strValues[6].charAt(0) == 'W') {
                    longi = -longi;
                }
                course = Double.parseDouble(strValues[8]);
                System.out.println("latitude=" + lat + " ; longitude=" + longi + " ; course = " + course);
            }
        } catch (Exception e) {

        }

    }

    void getStatusPoll() {
        //CommandStatusQueue.TerminateCommandChain();
        final String[] refresh;
        Log.d("getStatuskamal", "run");
        if (noozle == 2) {
            refresh = new String[]{Commands.STATUS_POLL2.toString()};
        } else {
            refresh = new String[]{Commands.STATUS_POLL.toString()};
        }

        CommandStatusQueue commandStatus = CommandStatusQueue.getInstance(refresh, new OnStatusQueueListener() {
            @Override
            public void onStatusQueueQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.d("getStatuskk", "" + lastResponse);

            }

            @Override
            public void OnStatusQueueCommandCompleted(final int currentCommand, final String response) {

                Log.d("getStatuscc", "" + status + response);

                if (response != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("getStatusBB", "" + response);
                            if (response.length() <= 14) {

                                status = PollStatus.getPollState(response);
                                Log.d("statuskamal2", status);
                            }
                            if (!status.isEmpty()) {
                                Log.d("statuskamal", status);
                                if (status.contains("STATE IDLE") /*|| status.equalsIgnoreCase("CALL STATE")*/) {
                                    Log.e("STATE","IDLE STATE") ;

                                    //  getReadTransaction();
                                    if (atgLastReadingInt == 0) {
                                        atgLastReadingInt++;
                                        ListenATG2();
                                        ReadATG1();
                                    }
                                    if (!SetRate) {
                                        // getReadTransaction();
                                        setRate(price);
                                        // setPresetWithoutStart(String.valueOf(dispenserPresetVolume.getText()));
                                        SetRate = true;
                                    }

                                    if (totsta < 10) {

                                        getVolumeTotalizer(1);
                                        totsta++;
                                    }
//                                    getATG1Data();
//                                    getATG2Data();
                                    ((TextView) findViewById(R.id.btnStart)).setVisibility(View.VISIBLE);
                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                                    ((TextView) findViewById(R.id.pumpStatus)).setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(true);
                                    StateIdel = true;
                                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnStop)).setEnabled(false);

//                                    findViewById(R.id.setPresetLay).setVisibility(View.VISIBLE);
                                    //...todo
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                }
                                else if (status.contains("CALL STATE")) {
                                    Log.e("STATE","CALL STATE") ;

                                    SendAuthCammand();
                                } else if (status.contains("PRESET READY STATE")) {
                                    Log.e("STATE","PRESET STATE") ;

                                    if (auth == 0) {
                                        Log.e("SendAuth", "" + auth);
                                        authorizeFueling();
                                        auth++;
                                    }
                                } else if (status.equalsIgnoreCase("FUELING STATE") || status.equalsIgnoreCase("STARTED STATE")) {
//                                    readVol();
                                    Log.e("STATE","FUELING STATE") ;

                                    StateIdel = false;
                                    Log.e("FuelingState", "Fueling_Status");
                                    CommandQueue.TerminateCommandChain();
                                    fuelingState = true;
                                    if (fu == 0) {
                                        updateFuelingStatus();
                                        adddiloagBolean = 0;
                                        fu++;
                                    }
                                    findViewById(R.id.tvReconnect).setEnabled(false);
                                    authBoolean = false;
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("transactionstatus", status);
                                            getReadTransaction();

                                            //    getVolumeTotalizer(2);
                                        }
                                    }, 300);

//
//                                    findViewById(R.id.pdfConvert).setVisibility(View.GONE);
                                    //startLogTransaction(String.valueOf(uniqueTransactionNumber), intialTotalizer, tvCurrentFuelDispensedQnty.getText().toString(), tvCurrentDispensedFuelChargeAmount.getText().toString(), String.valueOf(Calendar.getInstance().getTime()));

                                    //.....todo
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(true);

                                    ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));//changed on 13-11-19
                                    ((TextView) findViewById(R.id.btnStop)).setEnabled(false); //changed on 13-11-19

                                } else if (status.equalsIgnoreCase("PAYABLE STATE") || status.equalsIgnoreCase("STOPPED STATE")) {
                                    Log.e("STATE","PAYABLE STATE") ;


                                    StateIdel = false;
                                    isAlreadyPopForAuthorize = false;
                                    idelcount = 0;
                                    if (!isLockObtanedForNewTransaction) {
                                        //To DO
                                        //send Command RL10->IKNOWATIONS:#*RL1O
//                                        nozzleRelayStop();
                                        Log.e("payablesetToIdle", "setTo Idle");

                                        setStateIdle();


                                        getReadTransaction();
                                        if (totsta < 10) {

                                            getVolumeTotalizer(2);
                                            totsta++;
                                        }
                                        if (fuelingState) {
                                            if (adddiloagBolean == 0) {

//                                                getVolumeTotalizer(2);
                                                afterStopPressed();
                                                adddiloagBolean++;
                                            }
                                        }
                                        // getVolumeTotalizer(2); //added by kamal 6.8..2020
                                        //   _getTotalizer();
                                        if (adddiloagBolean == 0) {
                                            if (Double.parseDouble(tvCurrentFuelDispensedQnty.getText().toString()) > 0.0) {
                                                //adddiloagBolean++;
                                                Log.e("kamalpay", "payabl" + adddiloagBolean);
                                                //  afterStopPressed();
                                            }

                                        }
                                    }
//                                    isPayableState = true;
//                                    findViewById(R.id.orderCompletedLay).setVisibility(View.GONE);
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                                } else {
//                                    isPayableState = false;
                                    //To DO
//                                    nozzleRelayStop();
                                    //send Command RL10->IKNOWATIONS:#*RL1O
                                    CommandReadTxnQueue.TerminateCommandChain();
//                                    findViewById(R.id.orderCompletedLay).setVisibility(View.VISIBLE);
                                }
                                if (status.equalsIgnoreCase("SUSPENDED STATE") || status.equalsIgnoreCase("SUSPEND STARTED STATE")) {
//                                    nozzleRelayStop();

                                    Log.e("STATE","SUSPENDED STATE");
                                    getReadTransaction(); // added by Laukendra
                                    StateIdel = false;
                                    initialVolumeTotalizerCalled = false;
                                    //readVolumeTotalizer();

                                    dismissRfNotCloseError();

                                    if (Double.valueOf(tvCurrentFuelDispensedQnty.getText().toString().replace("Lts", "").trim()) >= Double.valueOf(dispenserPresetVolume.getText().toString().replace("Lts", "").trim())) {

//                                        if (adddiloagBolean == 0) {
//                                                afterStopPressed();
//
//                                           adddiloagBolean++;
//
//                                            Log.e("kamalpay", "suspend" + adddiloagBolean);
//                                        }

                                        //Commented by kamal 29.8.2020
//                                        final String[] dd = new String[]{Commands.PUMP_STOP.toString()/*, Commands.STATUS_POLL.toString()*/};
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
//
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

                                    } else {
                                        Log.e("notmSuspend", "notmatched");
                                        ((TextView) findViewById(R.id.btnResume)).setEnabled(true);
                                        ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));

                                        ((TextView) findViewById(R.id.btnStop)).setEnabled(true);
                                        ((TextView) findViewById(R.id.btnStop)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_400));
                                      //..todo
                                        ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                        ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                                    }

                                } else if (status.equalsIgnoreCase("STARTED STATE")) {
                                  Log.e("STATE","STARTED STATE") ;
                                    getReadTransaction();
                                    startedTime = String.valueOf(Calendar.getInstance().getTime());
                                    //....todo
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(true);
                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);
                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);

                                } else if (status.equalsIgnoreCase("STOPPED STATE")) {
                                    Log.e("STATE","STOPPED STATE") ;

                                    isAlreadyPopForAuthorize = false;
                                    suspendEvent = 0;
                                    idelcount = 0;
                                    setStateIdle();
                                    if (fuelingState) {
                                        if (adddiloagBolean == 0) {
                                            afterStopPressed();
                                            adddiloagBolean++;
                                        }
                                    }
//                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(true);
                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);

                                    //.......todo
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
//                                    readVol();
                                    if (!isLockObtanedForNewTransaction) {
                                        //To DO
                                        //send Command RL10->IKNOWATIONS:#*RL1O
                                        getReadTransaction();

                                    }

//                                    initialVolumeTotalizerCalled=false; //added by Laukendra on 22-11-19
//                                    readVolumeTotalizer();  //added by Laukendra on 22-11-19

//                                    isPayableState = true;
                                    findViewById(R.id.orderCompleted).setVisibility(View.VISIBLE);
                                    nozzleRelayStop();

                                } else if (status.equalsIgnoreCase("AUTHORIZE STATE")) {
                                    Log.e("STATE","AUTH STATE") ;

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
//                                            Toast.makeText(context, "To set IDLE State", Toast.LENGTH_SHORT).show();
                                        }
                                    });

//                                    toSetIdle();

                                } else {
                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);
                                }


                                Log.d("OnStatusQueueListener", status + "");
                                setStatusMessage(status);
                            }
                        }
                    });

                }


            }

        });
        commandStatus.doCommandChaining();
    }


    public void setStatusMessage(final String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pumpStatusTxt.setText(status);
            }
        });

    }

    public void afterStopPressed() {

        final String[] dd = new String[]{Commands.PUMP_STOP.toString()/*, Commands.STATUS_POLL.toString()*/};
        new CommandQueue(dd, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.d("pumpStop", lastResponse + "");
                PollStatus.getPollState(lastResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                afterStopPressed();
                Toast.makeText(context, "Pump Stopped Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                try {
                    progressDialog.setMessage(Commands.getEnumByString(dd[currentCommand]));
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }
        }).doCommandChaining();
        Log.e("StopClicked", "StopCLicked");
        try {
            sendToServerStopPump();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void nozzleRelayStop() {
        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getMakeOfRelayBoard().equalsIgnoreCase("IKNOWVATIONS")) {
            send285Command("RL10");
        } else {
            send285Command("#*RL10");
            //   eLockOff();
        }
    }


    public void send285RFIDCommand(String command) {

        if (Server285_ReadRFID.getSocket() != null) {
            Util.writeAll(Server285_ReadRFID.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {

                    Log.d("writing232-RFID", command + "");
                }
            });
        }
    }

    public void send285Command(String command) {
        if (Server285.getSocket() != null) {
            Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("writing232", command + "");
                            // Log.e("ATG-Res", ex.toString());
                        }
                    });
                }
            });
        }
    }


    public void send485Command(String command) {

        Log.d("writing485", command + "");
        if (Server485.getSocket() != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Util.writeAll(Server485.getSocket(), stringToHexWithoutSpace(command).getBytes(), new CompletedCallback() {
                        @Override
                        public void onCompleted(Exception ex) {

                            Log.d("writing485", command + "");
                            // Log.e("ATG-Res", ex.toString());
                        }
                    });
                }
            });
        }
    }


    public void eLockOff() {
        if (selectedAsset != null) {
            if (selectedAsset.getAssets_elock().equalsIgnoreCase("YES")) {

                send285RFIDCommand("#*SP00");
                //  send285RFIDCommand("#########");
            }
        }
    }

    private void dismissRfNotCloseError() {
        if (rfNotCloseProgress != null && rfNotCloseProgress.isShowing()) {
            rfNotCloseProgress.dismiss();
        }
    }


    public void sendToServerStopPump() {
        ListenATG2();
        ReadATG1();
        send285Command("#*RL10"); //Added on 20-12-19  //Commented on 02-01-20
        CommandQueue.TerminateCommandChain();
//        getVolumeTotalizer(2);
        setIdleFulingState();
        setStateIdle();
        send285Command("#*SP21");
//        send285Command("#$LC000479$#");
        String[] lc = {"#$LC000479$#"};
        Command285Queue command285Queue = new Command285Queue(lc, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                Log.e("lockClose", response);
                if (response.equalsIgnoreCase("LC")) {

                    Command285Queue.TerminateCommandChain();
                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                    apiInterface.change_asset_status(selectedAsset.getId(), 2).enqueue(new Callback<ChangePasswordbean>() {
                        @Override
                        public void onResponse(Call<ChangePasswordbean> call, Response<ChangePasswordbean> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSucc()) {
                                    Toast.makeText(context, "Locked", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(context, "Locking not succesful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordbean> call, Throwable t) {

                            Toast.makeText(context, "Locking not succesful", Toast.LENGTH_SHORT).show();
                        }
                    });
                    eLockOff();
                    Bundle bundle = new Bundle();
                    bundle.putString("FuelDispensed", String.valueOf(tvCurrentFuelDispensedQnty.getText()));
                    //bundle.putString("CurrentUserCharge", String.valueOf(tvCurrentDispensedFuelChargeAmount.getText()));
                    bundle.putString("CurrentUserCharge", String.valueOf(Float.parseFloat(String.valueOf(tvCurrentFuelDispensedQnty.getText())) * Float.parseFloat(String.valueOf(dispenserFuelRate.getText()))));
                    bundle.putString("FuelRate", String.valueOf(dispenserFuelRate.getText()));
                    bundle.putString("startTime", startedTime);
                    bundle.putString("selectedAsset", selectedAssetId);
                    Log.e("AssetId", "" + selectedAssetId);
                    bundle.putString("selectedAssetName", selectedAssetName);
                    bundle.putBoolean("rfidEnabled", isRFidEnabled);
                    bundle.putBoolean("rfidByPass", isRFidByPass);
                    bundle.putString("rfidTagId", rfIdTagId);
                    bundle.putString("atgStart", intialATGReading);
                    bundle.putString("orderDate", orderDetailed.getOrder().get(0).getOrderDate());
                    bundle.putParcelable("orderDetail", orderDetailed);

                    if (isFromFreshDispense) {

                        Log.e("isFromFreshDispence", "FreshDispence");
                        bundle.putSerializable("vehicleDataForFresh", vehicleDataForFresh);
                        bundle.putBoolean("isFromFreshDispense", true);
                    } else {

                        Log.e("isFromFreshDispence", "Dispence");
                        bundle.putBoolean("isFromFreshDispense", false);
                    }

                    Click click = new Click() {
                        @Override
                        public void ClickK(boolean b) {
                            if (b) {
                                Log.e("ActivityNot", "Finish");
                                finish();
                            } else {
                                Log.e("ActivityNot", "notFinish");
                                Intent intent = getIntent();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(intent);
                            }
                        }
                    };
                    AddReadingsDialog addReadingsDialog = new AddReadingsDialog(click);
                    addReadingsDialog.setCancelable(false);
                    addReadingsDialog.setArguments(bundle);
                    addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());
                    executor.shutdown();
                    Log.e("AddreadingOP", "opOP");
                }
            }
        });
        command285Queue.doCommandChaining();
//        send285Command("#*SP00");

        //Added By Laukendra
//        tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", 0.00));
//        tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", 0.00));
//        dispenserFuelRate.setText(String.format(Locale.US, "%.2f", 0.00));

    }


    public void ClearSale() {
//        ProgressDialog progressDialog = new ProgressDialog(DispenserUnitK.this);
//        progressDialog.setMessage("Please wait for transaction completion..");
        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()};
        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                progressDialog.dismiss();
                Log.e("lastClearSale", lastResponse + "");
                Toast.makeText(BrancoApp.getContext(), "Sale Cleared " + lastResponse, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                try {

                } catch (Exception e) {

                }
            }
        }).doCommandChaining();
    }

    private void getReadTransaction() {
        final String[] readTxn;
        if (noozle == 2) {
            readTxn = new String[]{Commands.READ_TXN2.toString()};
        } else {
            readTxn = new String[]{Commands.READ_TXN.toString()};
        }


        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
            @Override
            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {
                Log.e("TxnResponseonner", "" + lastResponse);
            }

            @Override
            public void OnTxnQueueCommandCompleted(final int currentCommand, final String lastResponse) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("TxnResponse", lastResponse + "");
                            if (lastResponse != null && StringUtils.countMatches(lastResponse, ".") == 3) {

                                try {
                                    Log.i("CurrentCommand", currentCommand + "");
                                    Log.i("TxnResponseInner", lastResponse);

                                    String respomse = lastResponse;
                                    Log.e("txnreskam", "" + (CommonUtils.findNextOccurance(respomse, ".", 1) > 4) + "" + (CommonUtils.findNextOccurance(respomse, ".", 2) > 13) + "&&" + (CommonUtils.findNextOccurance(respomse, ".", 3) > 22));
                                    final String txnFuelRate = respomse.substring(4, respomse.indexOf(".") + 3);

                                    final String txnDispense = respomse.substring(respomse.indexOf(".") + 3, CommonUtils.findNextOccurance(respomse, ".", 2) + 3);

                                    final String txnCharges = respomse.substring(CommonUtils.findNextOccurance(respomse, ".", 2) + 3, CommonUtils.findNextOccurance(respomse, ".", 3) + 3);

                                    Log.e("lastdispenseAmoount", txnDispense);
                                    Log.i("FuelDispenseNewTxn", " amount =" + txnFuelRate + ", dispense=" + txnDispense + ", charges=" + txnCharges);

                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if (curentTotalizer < 0) {
                                                    return;
                                                }
                                                tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
                                                tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));

                                                //tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.trim())));
                                                //Added by kamal 29.8.2020
                                                if (status.equalsIgnoreCase("SUSPENDED STATE") || status.equalsIgnoreCase("PAYABLE STATE") || status.equalsIgnoreCase("STOPPED STATE")) {
                                                    if (CommonUtils.findNextOccurance(respomse, ".", 1) > 5 && CommonUtils.findNextOccurance(respomse, ".", 2) > 13 && CommonUtils.findNextOccurance(respomse, ".", 3) > 22) {
                                                        if (Double.parseDouble(dispenserPresetVolume.getText().toString()) <= Double.parseDouble(txnDispense)) {
                                                            if (adddiloagBolean == 0) {
                                                                getVolumeTotalizer(2);
                                                                afterStopPressed();
                                                                adddiloagBolean++;
                                                            }
                                                        }
                                                    }
                                                }
                                                if (txnFuelRate.length() > 5) {
                                                    String string = txnFuelRate.trim().substring(txnFuelRate.length() - 6);
                                                    Log.d("Rate-:", string);
                                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(string.trim())));
                                                } else
                                                    tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.trim())));


                                                //Added by Laukendra
//                                                if (pumpStatusTxt.getText().toString().trim().equalsIgnoreCase("FUELING STATE")) {
//                                                    if (Float.parseFloat(tvCurrentFuelDispensedQnty.getText().toString().trim()) >= Float.parseFloat(dispenserPresetVolume.getText().toString())) {
//                                                        suspendSale();
//
//                                                    }
//                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                //added following lines by Laukendra on 22-11-19
//                                                tvCurrentFuelDispensedQnty.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
//                                                tvCurrentDispensedFuelChargeAmount.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));
//                                                tvCurrentFuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate.substring(2))));
                                                //added above lines by Laukendra on 22-11-19
                                            }
                                        }
                                    }, 200); // added by kamal 8.6.2020

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }).start();

                } catch (
                        Exception e) {
                    e.printStackTrace();

                }
            }
        }).doCommandChaining();

    }


    /**
     * This Method is used to connect with Synergy Wifi
     */
    public void connectToSynergyWifi() {


        try {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Server285.CloseSocket();
//                    Server485.CloseSocket();

//
//                    if (Server285.getAsyncServer() == null || !Server285.getAsyncServer().isRunning())
//                        Log.e("285connection", "not running");
//                    new Server285("192.168.1.170", 8000, (RouterResponseListener) DispenserUnitK.this);


                    if (Server285.getAsyncServer() == null || !Server285.getAsyncServer().isRunning())
                        Log.e("285connection", "not running");
                    new Server285("192.168.1.103", 54306, (RouterResponseListener) context);

                    if (ServerATG285.getAsyncServer() == null || !ServerATG285.getAsyncServer().isRunning())
                        new ServerATG285("192.168.1.103", 54310, (RouterResponseListener) context, atgSerialNoTank1, atgSerialNoTank2);
                    if (Server285_ReadRFID.getAsyncServer() == null || !Server285_ReadRFID.getAsyncServer().isRunning())
                        new Server285_ReadRFID("192.168.1.103", 54311, (RouterResponseListener) context);
                    if (Server485.getAsyncServer() == null || !Server485.getAsyncServer().isRunning())
                        new Server485("192.168.1.103", 54307, (RouterResponseListener) context);
                    if (Server485_status.getAsyncStausServer() == null || !Server485_status.getAsyncStausServer().isRunning())
                        new Server485_status("192.168.1.103", 54308, (RouterResponseListener) context);
                    if (Server485_ReadTxn.getAsyncReadTxnServer() == null || !Server485_ReadTxn.getAsyncReadTxnServer().isRunning())
                        new Server485_ReadTxn("192.168.1.103", 54309, (RouterResponseListener) context);

                    try {
                        //   readVolumeTotalizer();
                        //getRfidData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // setMessage("Connecting...");
                }
            }, 500);
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong With Server", Toast.LENGTH_LONG).show();
        }
    }


    public void stopServer() {

        try {
            if (Server285.getSocket() != null && Server285.getAsyncServer().isRunning()) {
                Server285.getAsyncServer().stop();
                Log.d("285connection", "Stopped");
            }
            if (ServerATG285.getSocket() != null && ServerATG285.getAsyncServer().isRunning()) {
                ServerATG285.getAsyncServer().stop();
                Log.d("ServerATG285", "Stopped");
            }
            if (Server285_ReadRFID.getSocket() != null && Server285_ReadRFID.getAsyncServer().isRunning()) {
                Server285_ReadRFID.getAsyncServer().stop();
                Log.d("Server285ReadRFID", "Stopped");
            }
            if (Server485.getSocket() != null && Server485.getAsyncServer().isRunning()) {
                Server485.getAsyncServer().stop();
                Log.d("Server485", "Stopped");
            }
            if (Server485_status.getSocket() != null && Server485_status.getAsyncStausServer().isRunning()) {
                Server485_status.getAsyncStausServer().stop();
                Log.d("Server485_status", "Stopped");
            }
            if (Server485_ReadTxn.getSocket() != null && Server485_ReadTxn.getAsyncReadTxnServer().isRunning()) {
                Server485_ReadTxn.getAsyncReadTxnServer().stop();
                Log.d("Server485_ReadTxn", "Stopped");
            }
            isATGPort3Selected = false;
            isATGPort4Selected = false;
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showAssetDialog(DeliveryInProgress orderDetailed) {
        //send285Command("#*RL10"); //Added on 13-11-19
//        stopServer();
//        connectToSynergyWifi();
        auth = 0;
        try {
            if (asset != null && asset.size() > 1) {
//                stopServer();
//                connectToSynergyWifi();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("assetList", asset);
                //bundle.putString("qnty", order.getQuantity()); //Commented by Laukendra on 15-11-19
                Log.e("trasionid", orderDetailed.getOrder().get(0).getTransactionId());
                float balancedQnty = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                bundle.putString("balance_qnty", String.format("%.2f", balancedQnty));

                dispenserPresetVolume.setText(balancedQnty + "");

                AssetListDialog assetListDialog = new AssetListDialog();
                assetListDialog.setAssetListener(new OnAssetOperation() {

                    @Override
                    public void OnAssetSelected(String assetId, String remainingBalance, String assetName, Asset asset, int postion) {
                        selectedAsset = asset;

                        LayoutInflater inflater = getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.selected_asset_qnty_dialog, null);
                        final EditText et_qnty = alertLayout.findViewById(R.id.et_qnty);
                        final TextView done = alertLayout.findViewById(R.id.done);

                        selectedAssetId = assetId;
                        getAssetLat(assetId);
                        selectedAssetName = assetName;
                        rfIdTagId = asset.getAssetsTagid();
//                        rfIdTagId = "Test"; //Testing purpose
                        if (asset.getAssetsRfid().equalsIgnoreCase("1")) {
                            isRFidEnabled = true;
                        } else if (asset.getAssetsRfid().equalsIgnoreCase("0")) {
                            isRFidEnabled = false;
                        }
                        if (asset.getAssetsBypassRfid().equalsIgnoreCase("1")) {
                            isRFidByPass = true;
                        } else if (asset.getAssetsBypassRfid().equalsIgnoreCase("0")) {
                            isRFidByPass = false;
                        }

                        Log.d("RFID-Enabled", isRFidEnabled + "");
                        Log.d("RFID-ByPassed", isRFidByPass + "");
                        Log.d("RFID-TagId", rfIdTagId + "");

                        if (!isRFidEnabled) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(DispenserUnitK.this);
                            //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
                            alert.setTitle("Enter Quantity For this Equipment(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
                            // this is set the view from XML inside AlertDialog
                            alert.setView(alertLayout);
                            // disallow cancel of AlertDialog on click of back button and outside touch
                            alert.setCancelable(false);
                            AlertDialog dialog = alert.create();
                            alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    showAssetDialog(orderDetailed);
                                }
                            });

                            done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String qnty = et_qnty.getText().toString();
                                    if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.99) {
                                        Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else
                                        Log.e("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.99));
                                    if (Float.parseFloat(qnty) <= balancedQnty) {
                                        dispenserPresetVolume.setText(qnty);
                                        dispenserAssetId.setText(asset.getAssetName());
//                                    order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty))); //Commented by Laukendra on 15-11-19
//                                    Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + (balancedQnty), Toast.LENGTH_SHORT).show(); //Commented by Laukendra on 15-11-19
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Insufficient Balance Left " + qnty + " " + remainingBalance, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                            dialog.show();

                        } else if (!isRFidByPass && rfIdTagId.isEmpty()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitK.this);
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
                            AlertDialog.Builder alert = new AlertDialog.Builder(DispenserUnitK.this);
                            //alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")"); //Commented by Laukendra on 15-11-19
                            alert.setTitle("Enter Quantity For this Asset(Total Qnty : " + String.format("%.2f", balancedQnty) + ")");  //Added by Laukendra on 15-11-19
                            alert.setView(alertLayout);
                            alert.setCancelable(false);
                            AlertDialog dialog = alert.create();
                            alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    showAssetDialog(orderDetailed);
                                }
                            });

                            done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    stopServer();
//                                    connectToSynergyWifi();
                                    String qnty = et_qnty.getText().toString();
                                    if (qnty.length() <= 0 || Double.parseDouble(qnty) <= 0.99) {
                                        Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Log.e("kamalQuty", qnty + "," + (Double.parseDouble(qnty) <= 0.99));
                                    if (Float.parseFloat(qnty) <= balancedQnty) {
                                        dispenserPresetVolume.setText(qnty);
                                        dispenserAssetId.setText(asset.getAssetName());
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Insufficient Balance Left " + qnty + " " + remainingBalance, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            dialog.show();
                        }
                    }

                });
                assetListDialog.setArguments(bundle);
                assetListDialog.show(getSupportFragmentManager(), AssetListDialog.class.getSimpleName());
            } else if (asset != null && asset.size() == 1) { //This line added by Laukendra on 20-11-19

//                stopServer();
//                connectToSynergyWifi();
                //Following Lines Added by Laukendra on 11-12-19
                float balancedQnty = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                dispenserPresetVolume.setText(balancedQnty + "");
//                dispenserAssetId.setText(asset.get(0).getAssetName());
                selectedAssetId = asset.get(0).getId();
                selectedAssetName = asset.get(0).getAssetName();
                selectedAsset = asset.get(0);
                rfIdTagId = asset.get(0).getAssetsTagid();
                //rfIdTagId = "Test"; //Testing purpose
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

                Log.d("RFID-Enabled", isRFidEnabled + "");
                Log.d("RFID-ByPassed", isRFidByPass + "");
                Log.d("RFID-TagId", rfIdTagId + "");

                if (!isRFidEnabled) {
                    float balancedQnty1 = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                    dispenserPresetVolume.setText(balancedQnty1 + "");
                    dispenserAssetId.setText(asset.get(0).getAssetName());
                    selectedAssetId = asset.get(0).getId();
                    selectedAssetName = asset.get(0).getAssetName();

                } else if (!isRFidByPass && rfIdTagId.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitK.this);
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
                    float balancedQnty2 = Float.parseFloat(orderDetailed.getOrder().get(0).getQty()) - Float.parseFloat(orderDetailed.getProgress().getDeliveredData());
                    dispenserPresetVolume.setText(balancedQnty2 + "");
                    dispenserAssetId.setText(asset.get(0).getAssetName());
                    selectedAssetId = asset.get(0).getId();
                    selectedAssetName = asset.get(0).getAssetName();
                }


                //Above Lines Added by Laukendra on 02-12-19
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getAssetLat(String assetId) {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getassetLatLong(assetId,SharedPref.getVehicleId()).enqueue(new Callback<GetCurrentAssetLatlong>() {
            @Override
            public void onResponse(Call<GetCurrentAssetLatlong> call, Response<GetCurrentAssetLatlong> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSucc()) {
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<GetCurrentAssetLatlong> call, Throwable t) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getOrderFullDetails(String orderTransactionId) {
        if (progressDialog != null) {
            progressDialog.setMessage("Getting Order Details, Hold On...");
            progressDialog.show();
        }
        SetRate = false;
        findViewById(R.id.tvReconnect).setEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Log.d("listSixw", "" + SharedPref.getOfflineOrderList().toString());

            Log.e("postiondespense", "" + position);
            price = fuelDetails.getFuelPrice();
            dispenserFuelRate.setText(fuelDetails.getFuelPrice());

            tvSiteLatitude.setText("Lat: " + fuelDetails.getCurrentLat());
            tvSiteLongitude.setText("Long: " + fuelDetails.getCurrentLong());

            if (fuelDetails.getLocationBypass()) {
                currentVehicleLat = orderDetailed.getProgress().getCurrentLat();
                currentVehicleLong = orderDetailed.getProgress().getCurrentLong();
                orderLocationLat = orderDetailed.getOrder().get(0).getLatitude();
                orderLocationLong = orderDetailed.getOrder().get(0).getLogitude();
                float gpsRange = Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
                float currentGpsDifference = Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))));
                if (currentVehicleLat.equalsIgnoreCase("00.0000") && currentVehicleLong.equalsIgnoreCase("00.0000")) {

//                    String[] st = {"#*SP11*#"};
//                    commandQueue7 = new Command285Queue(st, new OnAllCommandCompleted() {
//                        @Override
//                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                            getLatLogGPRMC(lastResponse);
//                            send285Command("#*SP10");
//                            commandQueue7.TerminateCommandChain();
//                            Log.e("latlongk", lastResponse);
//                        }
//
//                        @Override
//                        public void onAllCommandCompleted(int currentCommand, String response) {
//                            getLatLogGPRMC(response);
//                            send285Command("#*SP10");
//                            commandQueue7.TerminateCommandChain();
//
//                            Log.e("latlongk2", response);
//                        }
//                    });
//                    commandQueue7.doCommandChaining();
                }
                Toast.makeText(context, "GPS ByPassed", Toast.LENGTH_LONG).show();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DispenserUnitK.this);
                builder.setTitle("The GPS is out of range");
                builder.setMessage("Current Mismatch is : " + currentGpsDifference + " metres" + "\n Allowable range is: " + gpsRange + " metres" + " in Current GPS status");

                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getOrderFullDetails(orderTransactionId);
                    }
                });

                builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        tvByPassGPS.setVisibility(View.VISIBLE);
                        showAssetDialog(orderDetailed);
                    }
                });

                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCancelable(false);

            } else {
                currentVehicleLat = orderDetailed.getProgress().getCurrentLat();
                currentVehicleLong = orderDetailed.getProgress().getCurrentLong();
                orderLocationLat = orderDetailed.getOrder().get(0).getLatitude();
                orderLocationLong = orderDetailed.getOrder().get(0).getLogitude();
                if (currentVehicleLat.equalsIgnoreCase("00.0000") && currentVehicleLong.equalsIgnoreCase("00.0000")) {
//                    String[] st = {"#*SP11*#"};
//                    commandQueue8 = new Command285Queue(st, new OnAllCommandCompleted() {
//                        @Override
//                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                            Log.e("latlongk", lastResponse);
//                            getLatLogGPRMC(lastResponse);
//                            commandQueue8.TerminateCommandChain();
//                            send285Command("#*SP10");
//                        }
//
//                        @Override
//                        public void onAllCommandCompleted(int currentCommand, String response) {
//                            Log.e("latlongk2", response);
//                            getLatLogGPRMC(response);
//
//                            commandQueue8.TerminateCommandChain();
//                            send285Command("#*SP10");
//                        }
//                    });
//                    commandQueue8.doCommandChaining();
                }
                float gpsRange = Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
                float currentGpsDifference = Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))));
                Log.e("gpsRange", "" + gpsRange);
                Log.e("currentGpsDifference", "" + currentGpsDifference);

                if (gpsRange <= currentGpsDifference) {
                    String mismatchRange = String.valueOf(currentGpsDifference - gpsRange);
                    gpsByPassCheckDialog(orderTransactionId, mismatchRange, SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
                } else {
                    String mismatchRange = String.valueOf(currentGpsDifference - gpsRange);
                    Toast.makeText(context, "GPS Check Success", Toast.LENGTH_LONG).show();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DispenserUnitK.this);
                    builder.setTitle("The GPS in range");
                    builder.setMessage("Current Mismatch is : " + currentGpsDifference + " metres" + "\n Allowable range is: " + gpsRange + " metres" + " in Current GPS status");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            showAssetDialog(orderDetailed);
                        }
                    });


                    android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setCancelable(false);


                }
            }

            if (price.isEmpty()) {
                Toast.makeText(context, "Price Is missing,Please contact Backend Team", Toast.LENGTH_LONG).show();
            }


            //                    checkConnectedSetPrice(price);
            //                    showAssetDialog();
            // showAssetDialog(orderDetailed);
        }
//        }


    }


    private void checkConnectedSetPrice(String price) {
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
    }


    public void dismissProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private String stringToHexWithoutSpace(String string) {
        StringBuilder buf = new StringBuilder(200);
        for (char ch : string.toCharArray()) {
            buf.append(String.format("%02X", (int) ch));
        }
        return buf.toString();
    }

    private static String calculateCheckSum(byte[] byteData) {
        byte chkSumByte = 0x00;
        for (int i = 0; i < byteData.length; i++) {
            chkSumByte ^= byteData[i];
        }
        Log.d("createdCheckSum", convertToAscii(String.valueOf(chkSumByte)));
        return String.format("%02x", chkSumByte);


    }

    public static String printByteArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder("new byte[] { ");
        for (byte d : bytes) {
            sb.append(d + ", ");
        }
        sb.append("}");
        return sb.toString();
    }

    public void setRate(String fuelPrice) {
//        ProgressDialog setRateProgressDialog;
        if (!((Activity) context).isFinishing()) {
//            setRateProgressDialog = new ProgressDialog(context);
//            setRateProgressDialog.setMessage("Setting New Fuel Price:" + fuelPrice);
//            setRateProgressDialog.show();
            //show dialog


            if (Server485.getSocket() != null) {
                //   String b = String.format(Locale.US, "%06d", (int) (Double.parseDouble(fuelPrice) * 100));
                Log.e("stringRate", fuelPrice);

                String finalFuelPrice = "00" + fuelPrice;
                int index = finalFuelPrice.indexOf(".");
                //     String b= String.valueOf((Double.parseDouble(fuelPrice)));
                //   Log.e("stringFormatrate", b);
                String integerr = finalFuelPrice.substring(0, index);
                String decimal;
                try {

                    decimal = finalFuelPrice.substring(index + 1);
                } catch (Exception e) {
                    decimal = finalFuelPrice.substring(4);
                }
                dispenserFuelRate.setText(fuelPrice);
                Log.e("LeftPart", integerr);
                Log.e("RightPart", decimal);
                Log.e("RatetoUpdate", finalFuelPrice);
                String command;
                if (noozle == 2) {
                    command = "014242" + stringToHexWithoutSpace(integerr) + "2E" + stringToHexWithoutSpace(decimal) + "7F";
                } else {

                    command = "014142" + stringToHexWithoutSpace(integerr) + "2E" + stringToHexWithoutSpace(decimal) + "7F";
                }
                Log.e("Rate to Update", command);

                String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
//            String checkSumCommand = "014142303035302E30307F56";
                Log.d(
                        "ExecutingRateCommand", command
                                + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
                                + " totalCommand= " + checkSumCommand
                                + " String-" + convertToAscii(command)
                                + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));

                final String[] d;
                if (noozle == 2) {
                    d = new String[]{checkSumCommand};

                } else {
                    d = new String[]{checkSumCommand};

                }
                CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

//                        do {
//                            break;
//                        }
                        //
//
//                    do{
//                        if (setRateProgressDialog != null && setRateProgressDialog.isShowing()) {
//                            setRateProgressDialog.dismiss();
//                        }
//
//                        setRate(fuelPrice);
//                        return;
//                    }
//                        while (!lastResponse.contains("597f"))
//                    } {
//                        if (setRateProgressDialog != null && setRateProgressDialog.isShowing()) {
//                            setRateProgressDialog.dismiss();
//                        }
//                        setRate(fuelPrice);
//                        if (lastResponse.contains("597f")) {
//
//                            break;
//                        }
//                    }
//                    ;


                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                            dismissProgressBar();
                            //todo Read Totalizer Value
                            if (!response.contains("597f")) {
                                setRate(fuelPrice);
//                                if (setRateProgressDialog != null && setRateProgressDialog.isShowing()) {
//                                    setRateProgressDialog.dismiss();
//                                }
                            } else {
                                Toast.makeText(DispenserUnitK.this, "Rate Set Success", Toast.LENGTH_SHORT).show();
                                Log.e("597fKamal", "Contain");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                data.doCommandChaining();
            }
        }
    }


    public void gpsByPassCheckDialog(String orderTransactionId, String mismatch, String range) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DispenserUnitK.this);
        builder.setTitle("The GPS is out of range");
        builder.setMessage("Current Mismatch is : " + mismatch + " metres" + "\n Allowable range is: " + range + " metres" + " in Current GPS status");

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getOrderFullDetails(orderTransactionId);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                Log.e("CLICK","START");
                //Following Lines added by Laukendra
                findViewById(R.id.btnStart).setEnabled(false);
                findViewById(R.id.btnStart).setBackgroundColor(context.getColor(R.color.md_grey_500));
                /*findViewById(R.id.btnSuspend).setEnabled(true);
                findViewById(R.id.btnSuspend).setBackgroundColor(context.getColor(R.color.md_blue_500));
                findViewById(R.id.btnResume).setEnabled(false);
                findViewById(R.id.btnResume).setBackgroundColor(context.getColor(R.color.md_blue_500));*/
                eLockOn();
                nozzleOnHook.setEnabled(false);
                tvBtnStart.setEnabled(false);
                auth = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    nozzleOnHook.setBackgroundColor(context.getColor(R.color.md_grey_500));
                }
//                Thread thread = new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            sleep(40000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } finally {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    tvBtnStart.setEnabled(true);
//
//                                    findViewById(R.id.btnStart).setBackgroundColor(context.getColor(R.color.green));
//                                }
//                            });
//                        }
//                    }
//                };
//                thread.start();
                dispensing();
                curentTotalizer = 0f;
                tvCurrentFuelDispensedQnty.setText("" + curentTotalizer);
                isLockObtanedForNewTransaction = true;
                break;


            case R.id.waveLoadingViewTank1:
                isATGPort3Selected = false;
                //  getATG1Data(); //Added by Laukendra on 03-01-2020
                break;

            case R.id.waveLoadingViewTank2:
                isATGPort4Selected = false;
                //   getATG2Data(); //Added by Laukendra on 03-01-2020
                break;

//            case R.id.tv_connect_bluetooth:
//                connectBluetooth();
//                break;

            case R.id.btnStop:
                if (flagBtnStop > 0) {

                }

                else {

                    if(SharedPref.getDISPENSER().equalsIgnoreCase("Synergy")){
                            stopPumpDispenseSynergy();
                    }
                    if(SharedPref.getDISPENSER().equalsIgnoreCase("Tokheim"))
                    {
                        stopPumpDispenseTokehim();

                    }

                }
                break;

            case R.id.tvReconnect:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopServer(); //Added by Laukendra on 04-12-19
                        connectToSynergyWifi();
                    }
                }, 100); //previous Value 2000, //Updating by Laukendra
                break;
            case R.id.btnSuspend:
                //setLogToFile("\n\n" + "------------------Transaction Suspended Requested -----------------" + "\n\n");
               Log.e("CLICK","SUSPEND");
                //................todo
                findViewById(R.id.btnStart).setEnabled(false);
                findViewById(R.id.btnStart).setBackgroundColor(context.getColor(R.color.md_grey_500));
                findViewById(R.id.btnSuspend).setEnabled(false);
                findViewById(R.id.btnSuspend).setBackgroundColor(context.getColor(R.color.md_grey_500));

                findViewById(R.id.btnResume).setEnabled(true);
                findViewById(R.id.btnResume).setBackgroundColor(context.getColor(R.color.md_blue_500));
                findViewById(R.id.btnStop).setEnabled(true);
                findViewById(R.id.btnStop).setBackgroundColor(context.getColor(R.color.md_blue_500));

                suspendSale();
                break;
            case R.id.btnResume:
                //setLogToFile("\n\n" + "--------------------Transaction Resume Requested ------------------" + "\n\n");
                resumeSale();
                break;
//            case R.id.pdfConvert:
//                PdfCreator.createPDF();
//                break;
            case R.id.orderCompleted:
                if (isAlreadyPopForJerryCan) {
                    send285Command(Commands.LISTEN_XIBEE_RFID_285.toString());
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendCommandTenTimes();
                        }
                    }, 100); //previous Value 1000, //Updating by Laukendra
                }

                Bundle bundle = new Bundle();
                bundle.putString("FuelDispensed", String.valueOf(tvCurrentFuelDispensedQnty.getText()));
                //bundle.putString("CurrentUserCharge", String.valueOf(tvCurrentDispensedFuelChargeAmount.getText()));
                bundle.putString("CurrentUserCharge", String.valueOf(Float.parseFloat(String.valueOf(tvCurrentFuelDispensedQnty.getText())) * Float.parseFloat(String.valueOf(dispenserFuelRate.getText()))));
                bundle.putString("FuelRate", String.valueOf(dispenserFuelRate.getText()));
                bundle.putString("startTime", startedTime);
                bundle.putString("selectedAsset", selectedAssetId);
                bundle.putString("selectedAssetName", selectedAssetName);
                bundle.putBoolean("rfidEnabled", isRFidEnabled);
                bundle.putBoolean("rfidByPass", isRFidByPass);
                bundle.putString("rfidTagId", rfIdTagId);
                bundle.putString("atgStart", intialATGReading);
                if (isFromFreshDispense) {
                    Log.e("isFromFreshDispence", "FreshDispence");
                    bundle.putSerializable("vehicleDataForFresh", vehicleDataForFresh);
                    bundle.putParcelable("orderDetail", order);
                    bundle.putBoolean("isFromFreshDispense", true);
                    bundle.putString("orderDate", order.getCreated_datatime());
                } else {

                    Log.e("isFromFreshDispence", "Dispence");
                    bundle.putParcelable("orderDetail", orderDetailed);
                    bundle.putBoolean("isFromFreshDispense", false);
                    bundle.putString("orderDate", orderDetailed.getOrder().get(0).getCreatedDatatime());
                }

//                AddReadingsDialog addReadingsDialog = new AddReadingsDialog();
//                addReadingsDialog.setCancelable(false);
//                addReadingsDialog.setArguments(bundle);
//                addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());

                break;
            case R.id.dispenserSelectAsset:
                showAssetDialog(orderDetailed);
                break;

            case R.id.btn_refresh_232_485_server_status:
                //Get Status of Wifi Server(232,485)

                break;


        }
    }

    private void stopPumpDispenseTokehim() {
        flagBtnStop++;
        CommandQueue.TerminateCommandChain();
//                progressDialog.setMessage("Stopping Pump...");
//                progressDialog.show();
        final String[] dd = new String[]{Commands.PUMP_STOP.toString()
                /*, Commands.STATUS_POLL.toString()*/};
        new CommandQueue(dd, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.d("pumpStop", lastResponse + "");
                PollStatus.getPollState(lastResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                afterStopPressed();
                Toast.makeText(context, "Pump Stopped Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                try {
                    progressDialog.setMessage(Commands.getEnumByString(dd[currentCommand]));
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }
        }).doCommandChaining();
    }

    private void stopPumpDispenseSynergy() {
        flagBtnStop++;
        CommandQueue.TerminateCommandChain();
//                progressDialog.setMessage("Stopping Pump...");
//                progressDialog.show();
        final String[] dd = new String[]{Commands.SYNERGY_STOP.toString()
                /*, Commands.STATUS_POLL.toString()*/};
        new CommandQueue(dd, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.d("pumpStop", lastResponse + "");
                PollStatus.getPollState(lastResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                afterStopPressed();
                Toast.makeText(context, "Pump Stopped Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                try {
                    progressDialog.setMessage(Commands.getEnumByString(dd[currentCommand]));
                } catch (Exception e) {
                    progressDialog.dismiss();
                }
            }
        }).doCommandChaining();
    }

    private void sendCommandTenTimes() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                send285Command("{123456}");
                Log.d("multiCommandSent", count + " ");
                count++;
                if (count <= 10) {
                    sendCommandTenTimes();
                } else {
                    count = 0;
                }
            }
        }, 100);
    }

    public void setProgressBarMessage(String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!DispenserUnitK.this.isFinishing()) {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.setMessage(message);
                        } else {
                            progressDialog.show();
                            progressDialog.setMessage(message);
                        }
                    } else {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage(message);
                        progressDialog.show();
                    }
                }
            }
        });


    }

    private String stringToHex(String string) {
        StringBuilder buf = new StringBuilder(200);
        for (char ch : string.toCharArray()) {
            if (buf.length() > 0)
                buf.append(' ');
            buf.append(String.format("%02x", (int) ch));
        }
        return buf.toString();
    }


    public void dispensing() {


        setPreset(String.valueOf(dispenserPresetVolume.getText()));
    }


    public void setPreset(String volume) {
        volume = volume.replaceAll("[^0-9.]", "");
        if (builder == null)
            builder = new AlertDialog.Builder(DispenserUnitK.this);
        String finalVolume = volume;

        builder.setMessage("Please Take Nozzle to fueling position")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, "Wait few seconds", Toast.LENGTH_LONG).show();
                        setPresetWithoutStart(finalVolume);
//                                        nozzleRelayStop();

//                                        _authorizeFueling();

                    }
                })
                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isAlreadyPopForAuthorize = false;
                        isAlreadyPopForJerryCan = false;
                        final String[] presetCompletedStateCommands;
                        if (noozle == 2) {

                            presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET2.toString()};
                        } else {

                            presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
                        }
                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
                            @Override
                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                if (alert != null && alert.isShowing()) {
                                    alert.dismiss();
                                }
                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onAllCommandCompleted(int currentCommand, String response) {
                                try {
                                    if (alert != null && alert.isShowing()) {
                                        alert.dismiss();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }).doCommandChaining();

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

    public void setPresetWithoutStart(String volume) {
        if (Server485.getSocket() != null) {
            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));

            String command;
            if (noozle == 2) {

                command = "01425031" + stringToHexWithoutSpace(b) + "7F";
            } else {
                command = "01415031" + stringToHexWithoutSpace(b) + "7F";
            }
            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
            Log.d(
                    "ExecutingCommand", command
                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
                            + " totalCommand= " + checkSumCommand
                            + " String-" + convertToAscii(command)
                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));
            final String[] d = new String[]{checkSumCommand};
            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
                @Override
                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                    Log.e("responseOfPreset", stringToHexWithoutSpace(lastResponse));

                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
                    try {

                        Log.d("Responsematch", "" + stringToHexWithoutSpace(lastResponse).contains("597f"));
                        dismissProgressBar();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //    }

                @Override
                public void onAllCommandCompleted(int currentCommand, String response) {


                }
            });
            data.doCommandChaining();
        }

    }

    private void SendAuthCammand() {

        findViewById(R.id.tvReconnect).setEnabled(false);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        final String[] authorized;
        if (noozle == 2) {

            authorized = new String[]{Commands.AUTHORIZATION2.toString()};
        } else {
            authorized = new String[]{Commands.AUTHORIZATION.toString()};

        }
        new CommandQueue(authorized, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                curentTotalizer = 0f;
                Log.e("Authakmal", lastResponse);

            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                Log.e("Authakmalresponse", response + status);
            }
        }).doCommandChaining();

        //Added on 08-01-2020

//            }
//
//        }, 5000); //previous Value 20000, //Updating by Laukendra
    }

    public void showProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }
        });

    }

    private void eLockOn() {
        if (selectedAsset != null) {
            if (selectedAsset.getAssets_elock().equalsIgnoreCase("YES")) {
//                send285Command("#*SP21");
//                send285Command("*********");
//                send285Command("#*SP00");
                send285RFIDCommand("#*SP21");
                // send285RFIDCommand("*********");
                //send285RFIDCommand("#*SP00");

            }
        }
    }


    private void authorizeFueling() {
        send285Command("#*SP21");
        String[] Lo = {"#$LO000479$#"};
        Command285Queue command285Queue = new Command285Queue(Lo, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.e("lockResponse", lastResponse);
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                Log.e("lockResponseL", response);

                if (response.contains("LO")) {
                    flag = 1;

                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                    apiInterface.change_asset_status(""/*+selectedAsset.getId()*/, flag).enqueue(new Callback<ChangePasswordbean>() {
                        @Override
                        public void onResponse(Call<ChangePasswordbean> call, Response<ChangePasswordbean> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getSucc()) {
                                    Log.e("Authfun", "authfun");

                                    Click click = new Click() {
                                        @Override
                                        public void ClickK(boolean b) {
                                            Log.e("bolleankam", "" + b);
                                            if (b) {
                                                if (isRFidEnabled) {
                                                    if (!isRFidByPass) {
                                                        if (readRFid) {

                                                            Log.e("RfiNo", "Rfid found");

                                                            nozzleRelayStart();
                                                            fu = 0;

                                                        } else {


                                                            fu = 0;
                                                            Log.e("RfiNo", "Rfidnot found");
                                                            send285RFIDCommand("#*SP21");
                                                            authorizeFueling();
                                                        }
                                                    } else {

                                                        fu = 0;
                                                        Log.e("RfiNo", "RfidBypassfound");
                                                        nozzleRelayStart();

                                                    }
                                                } else {
                                                    fu = 0;
                                                    nozzleRelayStart();

                                                }
                                            } else {
                                                final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
                                                new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
                                                    @Override
                                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                                        isAlreadyPopForAuthorize = false;

                                                        Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onAllCommandCompleted(int currentCommand, String response) {
                                                        try {

                                                        } catch (Exception e) {
                                                        }
                                                    }
                                                }).doCommandChaining();

                                            }
                                        }
                                    };


                                    AlertDIalogFragment alertDIalogFragment = new AlertDIalogFragment("Start Fueling", DispenserUnitK.this, click);

                                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                    Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);
                                    ft.add(alertDIalogFragment, "Start Fueling");
                                    ft.commitAllowingStateLoss();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitK.this);
//                      AlertDialog  alert1 = builder.create();
//                        builder.setMessage("Are you sure to Start Fueling?")
//                                .setCancelable(false)
//                                .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        if (!isRFidByPass) {
//                                            if (readRFid) {
//
//                                                Log.e("RfiNo", "Rfid found");
//                                                alert1.dismiss();
//                                                nozzleRelayStart();
//                                                fu = 0;
//                                                SendAuthCammand();
//                                            } else {
//                                                alert1.dismiss();
//
//                                                fu = 0;
//                                                Log.e("RfiNo", "Rfidnot found");
//                                                send285RFIDCommand("#*SP21");
//                                                authorizeFueling();
//                                            }
//                                        } else {
//                                            alert1.dismiss();
//                                            fu = 0;
//                                            Log.e("RfiNo", "RfidBypassfound");
//                                            nozzleRelayStart();
//                                            SendAuthCammand();
//                                        }
////                        do {
////                            Log.e("command485","command"+status);
////
////                            send485Command(String.valueOf(Commands.AUTHORIZATION));
////
////                        }
////                        while (status.equalsIgnoreCase("CALL STATE"));
//                                    }
//                                })
//                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
//                                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
//                                            @Override
//                                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                                isAlreadyPopForAuthorize = false;
//                                                alert1.dismiss();
//                                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
//                                            }
//
//                                            @Override
//                                            public void onAllCommandCompleted(int currentCommand, String response) {
//                                                try {
//
//                                                } catch (Exception e) {
//                                                }
//                                            }
//                                        }).doCommandChaining();
//                                        dialog.cancel();
//                                    }
//                                });
//
//                        alert1.show();

                                } else {
                                    Toast.makeText(context, "" + response.body().getErrCodes().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordbean> call, Throwable t) {

                        }
                    });

                }

                Command285Queue.TerminateCommandChain();
            }
        });
        command285Queue.doCommandChaining();
//        send285Command();
//        send285Command("#*SP00");
//
//        send285Command("#*SP21");


    }

    public void nozzleRelayStart() {
        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getMakeOfRelayBoard().equalsIgnoreCase("IKNOWVATIONS")) {
            send285Command("RL10");
            send285Command("RL11");
            Log.d("RL11-----", "RL11");
        } else {
            send285Command("#*RL10");
            // eLockOff();
            Log.d("RL10----->", "#*RL10");
            send285Command("#*RL11");
            Log.d("RL11----->", "#*RL11");
            send285Command("#*BZ11");
            send285Command("#*BZ10");
        }
    }


    @Override
    public void OnRouter285Connected() {
        Log.e("router", "285connected" + (Server285.getSocket() != null));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvWifiServer232General.setText("Connected");

//                        checkAtgInitialValue();
                    }
                }, 100); //Last value 2000 changed by Laukendra on 15-11-19
                Toast.makeText(context, "Connected to 285", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    public void OnRouter285Aborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer232General.setText("Disconnected");
                Log.e("285connection", "285 disconnected");
                Toast.makeText(context, "Disconnected from 285", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnRouter285RfidConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer232Rfid.setText("Connected");
                //send285RFIDCommand("#*SP21"); // Open port Read RFID Data
            }
        });
    }

    @Override
    public void OnRouter285RfidAborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer232Rfid.setText("Disconnected");
            }
        });
    }

    //The following function commented by Laukendra on 28-11-19

    @Override
    public void OnRfIdReceived(String rfIdFoundData) {


        Log.e("RFIDkamal", rfIdFoundData + status);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                Log.e("RfidMatch", selectedAsset.getAssetsTagid() + "," + rfIdFoundData + status);

                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(50); //You can manage the blinking time with this parameter
                findViewById(R.id.blinkCircle).startAnimation(anim);
                if (selectedAsset != null) {
                    Log.e("RFIDData", "k"+rfIdFoundData);


                    if (selectedAsset.getAssetsRfid().equalsIgnoreCase("1")) {
                        if (selectedAsset.getAssetsBypassRfid().equalsIgnoreCase("0")) {
//                        if (rfIdFoundData.length() >= 14 && rfIdFoundData.charAt(0) == '5' && rfIdFoundData.charAt(1) == '5') {
//                            int firstIndex=10;
//                            int lastIndex=rfIdFoundData.lastIndexOf("03");
//                            Log.d("RFID index",firstIndex+","+lastIndex);
//                        if (!selectedAsset.getAssetsTagid().isEmpty() && selectedAsset.getAssetsTagid().equalsIgnoreCase(rfIdFoundData.substring(firstIndex,lastIndex))) {
//                            Toast.makeText(context, "RFID Tag Id Matched", Toast.LENGTH_SHORT).show();
//                            if (rfIdFoundData.charAt(8) == '5' && rfIdFoundData.charAt(9) == '0') {
//                                Log.e("RFID", "RFID Tag Reading Failed");
//                                rF_IdTxt.setText("RFID: Tag Reading Failed");
//                                suspendEventIfRFIDReadFailed();
//                            } else if (rfIdFoundData.charAt(8) == '6' && rfIdFoundData.charAt(9) == '0') {
//                                Log.e("RFID", "RFID Tag Low Battery");
//                                rF_IdTxt.setText("RFID: Tag Low Battery");
//                                suspendEventIfRFIDReadFailed();
//                            } else if (rfIdFoundData.charAt(8) == '7' && rfIdFoundData.charAt(9) == '0') {
//                                Log.e("RFID", "RFID Tag Idle State");
//                                rF_IdTxt.setText("RFID: Tag Idle State");
//                                suspendEventIfRFIDReadFailed();
//                            } else if (rfIdFoundData.charAt(8) == '3' && rfIdFoundData.charAt(9) == '0') {
//                                Log.e("RFID", "RFID Address Setting Succeeded");
//                                rF_IdTxt.setText("RFID: Address Setting Succeeded");
//                                suspendEventIfRFIDReadFailed();
//                            } else if (rfIdFoundData.charAt(8) == '4' && rfIdFoundData.charAt(9) == '0') {
//                                Log.e("RFID", "RFID Tag Reading Succeed");
//                                rF_IdTxt.setText("RFID: Tag Reading Succeed");
//                                //resumeSale();
//                            }
//                        }
//                        }else {
//                            Toast.makeText(context, "RFID Tag Id Not Matched", Toast.LENGTH_SHORT).show();
//                        }

                            if (pumpStatusTxt.getText().toString().trim().equalsIgnoreCase("PRESET READY STATE") || pumpStatusTxt.getText().toString().trim().equalsIgnoreCase("FUELING STATE")) {
                                if (rfIdFoundData.length() >= 14 && rfIdFoundData.charAt(0) == '5' && rfIdFoundData.charAt(1) == '5') {
                                    //CHECKing RFID tag data
                                    fuelingState = true;

                                    int firstIndex = 10;
                                    int lastIndex = rfIdFoundData.lastIndexOf("03");
                                    Log.e("RFIDCONTIAN", "" + rfIdFoundData.contains(selectedAsset.getAssetsTagid()) + "," + ("55025209405203005814FFFD1F1503FFFD").contains("7417011820005557"));
                                        //Toast.makeText(context, "RFID Tag Id Matched", Toast.LENGTH_SHORT).show();
                                        Log.e("RfidMatch", selectedAsset.getAssetsTagid() + "," + rfIdFoundData);
                                        if (rfIdFoundData.charAt(8) == '5' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Tag Reading Failed");
                                            rF_IdTxt.setText("RFID: Tag Reading Failed");
                                            suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '6' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Tag Low Battery");
                                            rF_IdTxt.setText("RFID: Tag Low Battery");
                                            suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '7' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Tag Idle State");
                                            rF_IdTxt.setText("RFID: Tag Idle State");
                                            suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '3' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Address Setting Succeeded");
                                            rF_IdTxt.setText("RFID: Address Setting Succeeded");
                                            suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '4' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Tag Reading Succeed");
                                            rF_IdTxt.setText("RFID: Tag Reading Succeed");
                                            readRFid = true;

                                            if (!selectedAsset.getAssetsTagid().isEmpty() && rfIdFoundData.contains(selectedAsset.getAssetsTagid())/*selectedAsset.getAssetsTagid().equalsIgnoreCase(rfIdFoundData.substring(firstIndex, lastIndex))*/) {
                                                rF_IdTxt.setText("RFID: TagId Matched");
                                            } else {
                                            rF_IdTxt.setText("RFID: TagId Not Matched");
                                            //Toast.makeText(context, "RFID Tag Id Not Matched", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        suspendEventIfRFIDReadFailed();
                                        rF_IdTxt.setText("RFID: TagId Not Matched");
                                    }
                                }
                            } else if (pumpStatusTxt.getText().toString().trim().equalsIgnoreCase("SUSPENDED STATE")) {
                                //CHECKing RFID tag data

                                int firstIndex = 10;
                                int lastIndex = rfIdFoundData.lastIndexOf("03");

                                    //Toast.makeText(context, "RFID Tag Id Matched", Toast.LENGTH_SHORT).show();
                                    if (rfIdFoundData.length() >= 14 && rfIdFoundData.charAt(0) == '5' && rfIdFoundData.charAt(1) == '5') {
                                        if (rfIdFoundData.charAt(8) == '5' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Tag Reading Failed");
                                            rF_IdTxt.setText("RFID: Tag Reading Failed");
                                            //suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '6' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Tag Low Battery");
                                            rF_IdTxt.setText("RFID: Tag Low Battery");
                                            //suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '7' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Tag Idle State");
                                            rF_IdTxt.setText("RFID: Tag Idle State");
                                            //suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '3' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFID", "RFID Address Setting Succeeded");
                                            readRFid = false;
                                            rF_IdTxt.setText("RFID: Address Setting Succeeded");
                                            //suspendEventIfRFIDReadFailed();
                                        } else if (rfIdFoundData.charAt(8) == '4' && rfIdFoundData.charAt(9) == '0') {
                                            Log.e("RFIDSuspend", "RFID Tag Reading Succeed");
                                            readRFid = true;
                                            rF_IdTxt.setText("RFID: Tag Reading Succeed");
                                            if (!selectedAsset.getAssetsTagid().isEmpty() && rfIdFoundData.contains(selectedAsset.getAssetsTagid())) {
                                                rF_IdTxt.setText("RFID: TagId Matched");
                                                resumeSale();
                                            }

                                        } else {
                                            suspendEventIfRFIDReadFailed();
                                            rF_IdTxt.setText("RFID: TagId Not Matched");
                                        }
                                    }
                                }

                            }
                        } else {
                            rF_IdTxt.setText("RFID: Not Enabled");
                        }
                    } else {
                        rF_IdTxt.setText("RFID: ByPassed");
                    }

            }

        });
    }

    @Override
    public void OnRouter285ATGConnected() {
        tvWifiServer232Atg.setText("Connected");
        // send285Command("#*SP00");
        getATG1Data();
        getATG2Data();
    }

    @Override
    public void OnRouter285ATGAborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer232Atg.setText("Disconnected");
            }
        });
    }

    public void getATG1Data() {
        if (ServerATG285.getSocket() != null) {
            if (ServerATG285.getAsyncServer().isRunning()) {
                if (!atgSerialNoTank1.isEmpty()) {
                    //if (!isATGPort3Selected)
                    ListenATG1();
                    ReadATG1();

                }
            }
        }
    }

    public void getATG2Data() {
        if (ServerATG285.getSocket() != null) {
            if (ServerATG285.getAsyncServer().isRunning()) {
                if (!atgSerialNoTank2.isEmpty()) {
                    //if (!isATGPort4Selected)
                    ListenATG2();
                    ReadATG2();
                }
            }
        }
    }

    @Override
    public void OnRouter485QueueConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer485General.setText("Connected");

//                if (!fuelingState) {
//                    //
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            getVolumeTotalizer();
//                        }
//                    }, 500);
//                }

                idelcount = 0;
                Toast.makeText(context, "Connected To Queue", Toast.LENGTH_SHORT).show();
                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));
            }
        });
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, "Connected To Queue", Toast.LENGTH_SHORT).show();
//                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));
//            }
//        });
    }

    @Override
    public void OnRouter485StatusConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer485Status.setText("Connected");
                // getStatusPoll();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getStatusPoll();
//                        getVolumeTotalizer();
//                        Log.d("getPool!", "run: ");
//                    }
//
//                }, 1000); //previous value 2000
            }
        });

    }

    @Override
    public void OnRouter485TxnConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer485ReadTxn.setText("Connected");
                Toast.makeText(context, "Connected to Txn ", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Checking Last Readings" + "", Toast.LENGTH_SHORT).show();
                getReadTransaction();
            }
        });
    }

    @Override
    public void OnRouter485QueueAborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer485General.setText("Disconnected");
                connection_maintainedState.setText("Connection Aborted");
            }
        });
    }

    @Override
    public void OnRouter485StatusAborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer485Status.setText("Disconnected");
            }
        });
    }

    @Override
    public void OnRouter485TxnAborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvWifiServer485ReadTxn.setText("Disconnected");
            }
        });
    }


    private void suspendEventIfRFIDReadFailed() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                suspendSale();
            }
        }, 2000);
    }

    @Override
    public void OnATGReceivedLK(String atgData) {

        Log.d("ATG Response Data", atgData);
//        if (atgData != null && !atgData.isEmpty()) {
//            Log.d("ATG-Actual-Data", atgData + "");
//
//            Log.e("atgDatakamal",atgData+","+atgSerialNoTank1);
//            if (atgData.contains(".")) {
//                try {
//                            Log.d("atgDtatLength", "" + atgData.trim().length() + "," + atgData);
//                            Log.e("atgDatakamal",atgData.trim()+","+SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no() + "N0");
//                            if (atgData.trim().contains(SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no() + "N0")) {
//                                if (!atgSerialNoTank1.isEmpty() && atgData.contains(atgSerialNoTank1)) {
//                                    int in = atgData.indexOf(SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no() + "N0");
//                                    String initialData = atgData.substring(in + 13, atgData.indexOf(".") + 3);
//
//                                    String atgTank1Temp = atgData.substring(in + 9, in + 12);
//                                    Log.e("AtgTemp", atgTank1Temp + in);
//                                    Log.e("AtgInstial data", initialData);
//                                    float tempDifference = 15 - (Float.parseFloat(atgTank1Temp) / 10);
//                                    float tank1AtgReading = Float.parseFloat(initialData);
//                                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
//                                    for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
//                                        atgcount = test;
//                                        if (atgcount == test - 10) {
//                                            test = atgCount;
//                                            return;
//                                        } else {
//                                            atgCount--;
//                                            try {
//                                                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
//                                                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
//                                                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                                    float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
//                                                    if (tableAtgReading == tank1AtgReading) {
//                                                        runOnUiThread(new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                double tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                                Log.d("VolumeBeforeTempTank1", tank1Volume + "");
//
//                                                                double volumeDifference = tempDifference * (0.083 / 100) * tank1Volume;
//                                                                Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                                tank1Volume = tank1Volume + volumeDifference;
//                                                                Log.d("VolumeAfterTempTank1", tank1Volume + "");
//                                                                if (tank1MaxVolume > 0) {
//                                                                    String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
//                                                                    int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                                    Log.d("VolumeTank1Progress", tank1VolumePercent + "");
//                                                                    waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
//                                                                }
//                                                                waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                                double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//                                                                balanceQunty = totalAvailableVolume;
//                                                                tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                            }
//                                                        });
//
//                                                    } else if (tableAtgReading < tank1AtgReading) {
//                                                        preAtgReading = tableAtgReading;
//                                                        preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                    } else if (tableAtgReading > tank1AtgReading) {
//
//                                                        postAtgReading = tableAtgReading;
//                                                        postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                        //Calculate Mean Volume of Tank
//                                                        meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank1AtgReading - preAtgReading) + preAtgVolume;
//
//                                                        double tank1Volume = meanAtgVolume;
//                                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");
//
//                                                        double volumeDifference = tempDifference * (0.083 / 100) * tank1Volume;
//                                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                        tank1Volume = tank1Volume + volumeDifference;
//                                                        Log.d("VolumeAfterTempTank1", tank1Volume + "");
//                                                        if (tank1MaxVolume > 0) {
//                                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
//                                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
//                                                            waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
//                                                        }
//                                                        waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
//                                                        double totalAvailableVolume = tank1Volume + Float.parseFloat(waveLoadingViewTank2.getCenterTitle());
//                                                        balanceQunty = totalAvailableVolume;
//                                                        tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                        break;
//                                                    }
//                                                }
//                                            } catch (Exception je) {
//                                                je.printStackTrace();
//                                            }
//                                        }
//                                    }
//                                    ReadATG1();
//                                }
//                            } else {
//                            }
//
//                } catch (Exception e) {
//                    Toast.makeText(DispenserUnitK.this, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                //LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + atgData + "\n");
//            } else if (atgData.contains("SP31SERIAL PORT 3 SELECTED")) {
//                isATGPort3Selected = true;
//                if (!atgSerialNoTank1.isEmpty()) {
//                    ReadATG1(); //new Call for read Atg data Command
//                }
//            } else if (atgData.contains("SP41SERIAL PORT 4 SELECTED")) {
//                isATGPort4Selected = true;
//                if (!atgSerialNoTank2.isEmpty()) {
//                    ReadATG2(); //new Call for read Atg data Command
//                }
//            }
//        } else {
//            isATGPort3Selected = false;
//            isATGPort4Selected = false;
//        }


    }

    @Override
    public void OnATGReceivedLK2(String atgData) {

        Log.d("ATG Response Data", atgData);
//        if (atgData != null && !atgData.isEmpty()) {
//            Log.d("ATG-Actual-Data", atgData + "");
//
//            Log.e("atgDatakamal",atgData+","+atgSerialNoTank2);
////                    if (atgData.contains(".") && atgData.trim().length() == 34) {
//            if (atgData.contains(".")) {
//                try {
//                                Log.e("atgDatakamal",atgData+","+atgSerialNoTank2);
//                                if (!atgSerialNoTank2.isEmpty() && atgData.contains(atgSerialNoTank2)) {
//                                    String initialData = atgData.substring(13, atgData.indexOf(".") + 3);
//                                    String atgTank1Temp = atgData.substring(9, 12);
//                                    float tempDifference = 15 - (Float.parseFloat(atgTank1Temp) / 10);
//                                    float tank2AtgReading = Float.parseFloat(initialData);
//                                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
//                                    for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank2.entrySet()) {
//                                        if (atgcount2 == test2 - 10) {
//                                            test2 = atgcount2;
//                                            return;
//                                        } else {
//                                            atgcount2--;
//                                            try {
//                                                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) hashMap.getValue();
//                                                JsonObject jsonObject = (new Gson()).toJsonTree(linkedTreeMap).getAsJsonObject();
//                                                if (!jsonObject.get("A").getAsString().equalsIgnoreCase("ATG")) {
//                                                    float tableAtgReading = Float.parseFloat(jsonObject.get("A").getAsString());
//                                                    if (tableAtgReading == tank2AtgReading) {
//
//                                                        double tank2Volume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                        Log.d("VolumeBeforeTempTank1", tank2Volume + "");
//
//                                                        double volumeDifference = tempDifference * (0.083 / 100) * tank2Volume;
//                                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");
//
//                                                        tank2Volume = tank2Volume + volumeDifference;
//                                                        Log.d("VolumeAfterTempTank1", tank2Volume + "");
//                                                        if (tank2MaxVolume > 0) {
//                                                            String string = String.valueOf((tank2Volume * 100) / tank2MaxVolume);
//                                                            int tank2VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                            Log.d("VolumeTank2Progress", tank2VolumePercent + "");
//                                                            waveLoadingViewTank2.setProgressValue(tank2VolumePercent);
//                                                        }
//                                                        waveLoadingViewTank2.setCenterTitle(String.format("%.2f", tank2Volume));
//                                                        double totalAvailableVolume = tank2Volume + Float.parseFloat(waveLoadingViewTank1.getCenterTitle());
//                                                        balanceQunty = totalAvailableVolume;
//                                                        tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                    } else if (tableAtgReading < tank2AtgReading) {
//                                                        preAtgReading = tableAtgReading;
//                                                        preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                    } else if (tableAtgReading > tank2AtgReading) {
//                                                        postAtgReading = tableAtgReading;
//                                                        postAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
//                                                        //Calculate Mean Volume of Tank
//                                                        meanAtgVolume = (postAtgVolume - preAtgVolume) / (postAtgReading - preAtgReading) * (tank2AtgReading - preAtgReading) + preAtgVolume;
//
//                                                        double tank2Volume = meanAtgVolume;
//                                                        Log.d("VolumeBeforeTempTank2", tank2Volume + "");
//
//                                                        double volumeDifference = tempDifference * (0.083 / 100) * tank2Volume;
//                                                        Log.d("VolumeTempDiffTank2", volumeDifference + "");
//
//                                                        tank2Volume = tank2Volume + volumeDifference;
//                                                        Log.d("VolumeAfterTempTank2", tank2Volume + "");
//
//                                                        if (tank2MaxVolume > 0) {
//                                                            String string = String.valueOf((tank2Volume * 100) / tank2MaxVolume);
//                                                            int tank2VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
//                                                            Log.d("VolumeTank2Progress", tank2VolumePercent + "");
//                                                            waveLoadingViewTank2.setProgressValue(tank2VolumePercent);
//                                                        }
//                                                        waveLoadingViewTank2.setCenterTitle(String.format("%.2f", tank2Volume));
//                                                        double totalAvailableVolume = tank2Volume + Float.parseFloat(waveLoadingViewTank1.getCenterTitle());
//                                                        balanceQunty = totalAvailableVolume;
//                                                        tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
//                                                        break;
//                                                    }
//                                                }
//                                            } catch (Exception je) {
//                                                je.printStackTrace();
//                                            }
//                                        }
//                                    }
//                                    ReadATG2();
//                                }
//
//
//                } catch (Exception e) {
//                    Toast.makeText(DispenserUnitK.this, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                //LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + atgData + "\n");
//            } else if (atgData.contains("SP31SERIAL PORT 3 SELECTED")) {
//                isATGPort3Selected = true;
//                if (!atgSerialNoTank1.isEmpty()) {
//                    ReadATG1(); //new Call for read Atg data Command
//                }
//            } else if (atgData.contains("SP41SERIAL PORT 4 SELECTED")) {
//                isATGPort4Selected = true;
//                if (!atgSerialNoTank2.isEmpty()) {
//                    ReadATG2();
//                    //new Call for read Atg data Command
//                }
//            }
//        } else {
//            isATGPort3Selected = false;
//            isATGPort4Selected = false;
//        }


    }


    public void ListenATG1() {

        send285ATGCommand(Commands.LISTEN_ATG_1_285.toString());

    }

    public void ListenATG2() {
        send285ATGCommand(Commands.LISTEN_ATG_2_285.toString());
    }

    public void ReadATG1() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                send285ATGCommand("M" + atgSerialNoTank1); //This is atg value/id
                Log.d("CommandReadAtgPort3", "M" + atgSerialNoTank1);
            }
        });
    }

    public void ReadATG2() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                send285ATGCommand("M" + atgSerialNoTank2); //This is atg value/id
                Log.d("CommandReadAtgPort4", "M" + atgSerialNoTank2);
            }
        });
    }

    public void send285ATGCommand(String command) {
        if (ServerATG285.getSocket() != null && ServerATG285.getAsyncServer().isRunning()) {
            Util.writeAll(ServerATG285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {

                    Log.d("ATG-writing232", command + "");

                }
            });
        }
    }


    void resumeSale() {
        CommandQueue.TerminateCommandChain();
        final String[] refresh;

        if (SharedPref.getDISPENSER().equalsIgnoreCase("Synergy")) {
            SynergyDispenser.getInstance().relay20();
        } else if (SharedPref.getDISPENSER().equalsIgnoreCase("Tokheim")) {

            if (noozle == 2) {
                refresh = new String[]{Commands.RESUME_SALE2.toString()};
            } else {
                refresh = new String[]{Commands.RESUME_SALE.toString()};
            }
//        progressDialog.show();

            new CommandQueue(refresh, new OnAllCommandCompleted() {
                @Override
                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                    Log.d("printerrorResumedale", lastResponse + "");
                    progressDialog.dismiss();
                }

                @Override
                public void onAllCommandCompleted(final int currentCommand, final String response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                    try {
                        Log.e("currentcammand", "" + currentCommand + "," + response);
//                    progressDialog.setMessage(Commands.getEnumByString(refresh[currentCommand]));
                    } catch (Exception e) {
                        Log.d("statusReceivedExcResum", e.getLocalizedMessage() + " " + response + currentCommand);
                        //  progressDialog.dismiss();
                    }
//                    }
//                });
                }
            }).doCommandChaining();
        }
    }

    void suspendSale() {
        CommandQueue.TerminateCommandChain();
        suspendEvent++;
        final String[] refresh;
        if (SharedPref.getDISPENSER().equalsIgnoreCase("Synergy")) {
            Log.e("MAKE DISPENSER","SYNERGY");
            SynergyDispenser.getInstance().relay20();
            Log.e("MAKE DISPENSER","SYNERGY");
        }

    }
    private void getVolumeTotalizerR(int state) {
        Log.e("goWithTotlizer", "totlizer");
        final String[] readVolume;

        if (noozle == 2) {
            readVolume = new String[]{Commands.Read_Volume_TOt2.toString()};
        } else {
            readVolume = new String[]{Commands.Read_Volume_TOt.toString()};
        }
        new CommandReadTotlizerQueue(readVolume, new OnTxnQueueCompleted() {
            @Override
            public void onTxnQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.e("TOtlizerResponse", lastResponse);
            }

            @Override
            public void OnTxnQueueCommandCompleted(int currentCommand, String response) {
                Log.e("TOtlizerResponse", response);
            }
        });


    }

    private void getVolumeTotalizer(int state) {
        Log.e("goWithTotlizer", "totlizer");
        CommandReadTotlizerQueue.TerminateCommandChain();
        final String[] readVolume;

        if (noozle == 2) {
            readVolume = new String[]{Commands.READ_VOL_TOTALIZER2.toString()};
        } else {
            readVolume = new String[]{Commands.READ_VOL_TOTALIZER.toString()};
        }

        new CommandReadTotlizerQueue(readVolume, new OnTxnQueueCompleted() {
            @Override
            public void onTxnQueueEmpty(boolean isEmpty, String response) {
                Log.i("fuelTOTlizerNewVol", response + "");

                if (response != null && StringUtils.countMatches(response, ".") == 1) {
                    try {
                        //   int index = response.indexOf("000");
                        String txnFuelVolume = response.replaceAll("[^0-9.]", "");
                        Log.e("getVolumeTotalizer", response);
                        txnFuelVolume = txnFuelVolume.substring(1, txnFuelVolume.length());
                        if (tot == 0) {
                            if (SharedPref.getLastFuelDispenserReading() != null && SharedPref.getLastFuelDispenserReading().equals("") && txnFuelVolume != null && !txnFuelVolume.equals("")) {

//                                SharedPref.setTotalizerReadingDiff(String.valueOf(Double.parseDouble(txnFuelVolume) - Double.parseDouble(SharedPref.getLastFuelDispenserReading())));
// kamal remove dispense diff reading 6.8.2020
                                //SharedPref.setLastFuelDispenserReading(txnFuelVolume);
                                // Log.e("logDiffernce", String.valueOf(Float.parseFloat(txnFuelVolume) - Float.parseFloat(SharedPref.getLastFuelDispenserReading())));
                                tot++;
                            }
                            if (txnFuelVolume != null && !txnFuelVolume.equals("")) {
                                if (state == 1) {

                                    Log.e("instotliz", txnFuelVolume);
                                    SharedPref.setInitialVolumeTotalizer(txnFuelVolume);
                                    SharedPref.setLastFuelDispenserReading(txnFuelVolume);
                                } else if (state == 2) {
                                    Log.e("Lasttotliz", txnFuelVolume);
                                    SharedPref.setLastFuelDispenserReading(txnFuelVolume);
                                }
                            }
                            Log.e("totlizerValue", SharedPref.getLastFuelDispenserReading());

                        }
                        Handler matchHandler = new Handler();
                        String finalTxnFuelVolume = txnFuelVolume;
                        Runnable matchrunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (Double.parseDouble(finalTxnFuelVolume) != 0 && Double.parseDouble(finalTxnFuelVolume) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
                                    Log.e("totalizerVolume", finalTxnFuelVolume);
                                    matchHandler.removeCallbacksAndMessages(null);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    intialTotalizerTxt.setText(String.format(Locale.US, "%.2f", Double.parseDouble(finalTxnFuelVolume)));
//                                }
//                            });


                                }
                            }
                        };
                        matchHandler.postDelayed(matchrunnable, 3000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!fuelingState) {
                        //
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getVolumeTotalizer(state);
//                            }
//                        }, 2000);


                    }

                }
            }

            @Override
            public void OnTxnQueueCommandCompleted(int currentCommand, String response) {
                Log.i("fuelDispenseNewVolCC", response + "");

                if (response != null && StringUtils.countMatches(response, ".") == 1) {

                    try {
//                        int index = response.indexOf("000");
                        String txnFuelVolume = response.replaceAll("[^0-9.]", "");
//                        txnFuelVolume = txnFuelVolume.substring(2, txnFuelVolume.length());
                        Log.e("LastValue Of TOTlizerCC", String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelVolume)));

                        if (Double.parseDouble(txnFuelVolume) != 0 && Double.parseDouble(txnFuelVolume) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
                            if (tot == 0) {
                                if (SharedPref.getLastFuelDispenserReading() != null && txnFuelVolume != null && !txnFuelVolume.equals("")) {
                                    txnFuelVolume = txnFuelVolume.substring(1, txnFuelVolume.length());
                                    //  SharedPref.setTotalizerReadingDiff(String.valueOf(Double.parseDouble(txnFuelVolume) - Double.parseDouble(SharedPref.getLastFuelDispenserReading())));
// kamal remove reading diff 6.8.2020
//                                    //   SharedPref.setTotalizerReadingDiff(Float.parseFloat(txnFuelVolume) - Float.parseFloat(SharedPref.getLastFuelDispenserReading()));
//
//                                    Log.e("logDiffernsetLastFuelDispenserReadingcecc", String.valueOf(Float.parseFloat(txnFuelVolume) - Float.parseFloat(SharedPref.getLastFuelDispenserReading())));

                                    tot++;
                                }
                                if (txnFuelVolume != null && !txnFuelVolume.equals("")) {
                                    if (state == 1) {

                                        Log.e("instotliz", txnFuelVolume);
                                        SharedPref.setInitialVolumeTotalizer(txnFuelVolume);
                                        SharedPref.setLastFuelDispenserReading(txnFuelVolume);
                                    } else if (state == 2) {
                                        Log.e("Lasttotliz", txnFuelVolume);
                                        SharedPref.setLastFuelDispenserReading(txnFuelVolume);
                                    }
                                }

                                SharedPref.setLastFuelDispenserReading(txnFuelVolume);
                            } else {
                                Log.e("Dispense Kaml", "No Dispensing");
                            }

                            SharedPref.setLastFuelDispenserReading(txnFuelVolume);
                            Log.e("totalizerVolumeCC", txnFuelVolume);
                            Handler matchHandler = new Handler();
                            String finalTxnFuelVolume = txnFuelVolume;
                            Runnable matchrunnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (Double.parseDouble(finalTxnFuelVolume) != 0 && Double.parseDouble(finalTxnFuelVolume) != Double.parseDouble(tvCurrentFuelRate.getText().toString())) {
                                        Log.e("totalizerVolume", finalTxnFuelVolume);
                                        matchHandler.removeCallbacksAndMessages(null);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    intialTotalizerTxt.setText(String.format(Locale.US, "%.2f", Double.parseDouble(finalTxnFuelVolume)));
//                                }
//                            });


                                    }
                                }
                            };
                            matchHandler.postDelayed(matchrunnable, 3000);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("elseTotlizer", "totlizer");
                    if (!fuelingState) {
                        //
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                getVolumeTotalizer(state);
//                            }
//                        }, 4000);


                    }
                }
            }
        }).doCommandChaining();


    }

    @Override
    public void onBackPressed() {

        if (fuelingState) {
            Toast.makeText(context, "Go Back After Fueling Finish.", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alert != null && alert.isShowing()) {
            alert.dismiss();
        }
        Log.e("destroyactivity", "distroy");
        CommandQueue.TerminateCommandChain();
        CommandReadTotlizerQueue.TerminateCommandChain();
        CommandStatusQueue.TerminateCommandChain();
        CommandReadTxnQueue.TerminateCommandChain();
    }
}

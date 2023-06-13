package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.WaveLoadingView;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.CommonUtils;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnStatusQueueListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnTxnQueueCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables.TransactionDbModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandReadTxnQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandStatusQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485_ReadTxn;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485_status;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.DispenseNowClick;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Server285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.LogToFile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.PollStatus;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.OfflineOrderDetail;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Progress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.LocationUtil;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue.convertToAscii;

public class FreshDispenserUnitActivity extends AppCompatActivity implements RouterResponseListener, View.OnClickListener {
    private Context context;
    private TextView dispenseNow;
    private TextView connection_maintainedState, rF_IdTxt;
    private ProgressDialog progressDialog;
    private EditText setPresetEdt;
    private TextView pumpStatusTxt, tvSiteLatitude, tvSiteLongitude, tvByPassGPS, tvGPSRange;
    private double intialTotalizer = 0d;
    private double curentTotalizer = 0d;
    private String status = "";
    private TextView fuelDispensed;
    private TextView fuelRate;
    private TextView currentUserCharge;
    public static TextView tvCommandExecutionTxt;

    private String uniqueTransactionNumber;
    private TextView intialTotalizerTxt;


    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private boolean isPayableStateLock = false;
    private boolean isPayableState = false;
    private AppDatabase db;

    private boolean isLockObtanedForNewTransaction;


    private TextView dispenserAssetId, dispenserFuelRate, dispenserLocationInfo, dispenserPresetAmount, dispenserOrderQnty;
    private ScheduledExecutorService executor;
//    private Order order;
    private OfflineOrderDetail order;
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
    private Progress cheackFuelDetails;
    private int suspendEvent = 0;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private int count = 0;
    private String startedTime;
    private String intialATG;
    private boolean isIntialATG;
    private String intialATGReading;
    private String endReadingIs;
    private int atgCount = 0;
    private TextView tank1;
    private String selectedAssetId;
    private DeliveryInProgress orderDetailed;
    private DeliveryInProgress checkOrderDetailed;

    private String currentVehicleLat, currentVehicleLong;
    private String orderLocationLat, orderLocationLong;
    private String price;
    private static final Double INTERPOLATION_VALUE_OF = 16.0;
    public static String mPresetValue;
    private WaveLoadingView waveTankOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fragment_operations);
        Log.e("ON_CREATE","ON CREATE4");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context = this;
        dispenserAssetId = findViewById(R.id.dispenserSelectedAssetId);
        dispenserFuelRate = findViewById(R.id.dispenserFuelRate);
        dispenserLocationInfo = findViewById(R.id.tvDispenserLocationId);
        dispenserPresetAmount = findViewById(R.id.dispenserPresetVolume);
        dispenserOrderQnty = findViewById(R.id.dispenserOrderQnty);
        dispenserSelectAssetId = findViewById(R.id.dispenserSelectAsset);
//        setPresetEdt = findViewById(R.id.setPresetEdt);
        fuelRate = findViewById(R.id.currentFuelRate);
        currentUserCharge = findViewById(R.id.currentDispensedFuelChargeAmount);
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
        waveTankOne = findViewById(R.id.waveLoadingViewTank1);

        waveTankOne.setOnClickListener(this);

        connection_maintainedState = findViewById(R.id.pumpStatus);

        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btn_for_idlestate).setOnClickListener(this);
        ((TextView) findViewById(R.id.btn_for_idlestate)).setEnabled(true);

        fuelDispensed = findViewById(R.id.currentFuelDispensedQnty);
//        findViewById(R.id.setPresetBtn).setOnClickListener(this);
//        findViewById(R.id.pumpStatusRefresh).setOnClickListener(this);
//        findViewById(R.id.resetNetwork).setOnClickListener(this);

        findViewById(R.id.tvReconnect).setOnClickListener(this);
        findViewById(R.id.btnSuspend).setOnClickListener(this);
        findViewById(R.id.btnResume).setOnClickListener(this);
        findViewById(R.id.orderCompleted).setOnClickListener(this);
        dispenserSelectAssetId.setOnClickListener(this);


        db = BrancoApp.getDb();

//        ListenATG1();
//        ReadATG1();

//        if (getIntent() != null) {
//            if (getIntent() != null && getIntent().getExtras() != null) {
//                Intent intent = getIntent();
//                checkOrder = intent.getParcelableExtra("orderDetail");
//                transactionId = checkOrder.getTransaction_id();
//                checkOrderFullDetails(checkOrder.getTransaction_id());
//            }
//        }
//
//        if (Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange()) < Float.valueOf(
////                String.valueOf(LocationUtil.distanceInMeters(28.657060, 77.325950, 31.361180, 70.997840))
////                String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getLatitude()), Double.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getLongitude()), Double.valueOf(cheackFuelDetails.getCurrentLat()), Double.valueOf(cheackFuelDetails.getCurrentLong()))
//                String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))
//
//                ))) {
//
//            String mismatchRange = String.valueOf(Float.valueOf(
////                    String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getLatitude()), Double.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getLongitude()), Double.valueOf(cheackFuelDetails.getCurrentLat()), Double.valueOf(cheackFuelDetails.getCurrentLong()))
//                    String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))
//                    )) - Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange()));
//            Toast.makeText(context, "Out of Range", Toast.LENGTH_SHORT).show();
//            gpsByPassCheckDialog(mismatchRange, SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
//        }
//        else {
//            if (getIntent() != null) {
//
//                if (getIntent() != null && getIntent().getExtras() != null) {
//                    Intent intent = getIntent();
//                    order = intent.getParcelableExtra("orderDetail");
//                    transactionId = order.getTransaction_id();
//                    getOrderFullDetails(order.getTransaction_id());
//                    if (order != null) {
//                        Toast.makeText(this, "Order Received", Toast.LENGTH_SHORT).show();
//                        dispenserLocationInfo.setText(String.format(" %s", order.getLocation_name()));
//                        dispenserPresetAmount.setText(String.format(" %s Lts", order.getQuantity()));
//                        dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", order.getQuantity()));
////                    dispenserAssetId.setText(String.format(" %s", order.getLocation_name()));
//                    }
//                }
//            }
//
//
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Executing Command... ");
//            connection_maintainedState.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (s != null && s.length() > 0) {
//                        if (s.toString().contains("Connecting")) {
//                            connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_300));
//                        } else {
//                            connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_400));
//                        }
//
//                    }
//                }
//            });
//
//            connection_maintainedState.setSelected(true);
//
//
////            ToEN
//
////        fuelRate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                setRate(price);
////            }
////        });
//
////        findViewById(R.id.test_rate).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                EditText etText = findViewById(R.id.et_testRate);
////                setRate(etText.getText().toString());
////                Toast.makeText(context, "Setting Rate", Toast.LENGTH_SHORT).show();
////            }
////        });
//
////        intialTotalizerTxt.setText(SharedPref.getLastFuelDispenserReading(context));
////        try {
////            intialTotalizer = (SharedPref.getLastFuelDispenserReading(context).isEmpty()) ? 0.0f : Double.parseDouble(SharedPref.getLastFuelDispenserReading(context));
////        } catch (Exception e) {
//
////            e.printStackTrace();
////        } finally {
////        uniqueTransactionNumber = (SharedPref.getLastTransactionId().isEmpty()) ? UUID.randomUUID() : UUID.fromString(SharedPref.getLastTransactionId());
//            uniqueTransactionNumber = order.getTransaction_id();
//
////        }
//
//
//            fuelDispensed.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (s.length() != 0)
//                        try {
//                            double currentPrice = Double.parseDouble(s.toString()) * Double.parseDouble(fuelRate.getText().toString());
//                            if (currentPrice >= 0) {
//                                currentUserCharge.setText(String.format(Locale.US, "%.2f", currentPrice));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                }
//            });
//
//            connectToSynergyWifi();
//
//            //----------------------------------------------------------------------------------------------------------------------------------
//            // TODO: 2/22/2019 Start Executor for every Two Seconds to check Current Dispenser State
//
//            getStatusRunnable = new Runnable() {
//                public void run() {
//                    if (Server485_status.getSocket() != null) {
//                        getStatusPoll();
//                    }
//                }
//            };
//            executor = Executors.newScheduledThreadPool(1);
//            executor.scheduleAtFixedRate(getStatusRunnable, 0, 1, TimeUnit.SECONDS);
//        }


        /* To End */

        if (getIntent() != null) {

            if (getIntent() != null && getIntent().getExtras() != null) {
                Intent intent = getIntent();
                order = (OfflineOrderDetail) intent.getSerializableExtra("orderDetail");
                transactionId = order.getTransactionId();
//                getOrderFullDetails(order.getTransaction_id());
                if (order != null) {
                    Toast.makeText(this, "Order Received", Toast.LENGTH_SHORT).show();
                    dispenserLocationInfo.setText(String.format(" %s", order.getLocationName()));
                    dispenserPresetAmount.setText(String.format(" %s Lts", order.getQty()));
                    dispenserOrderQnty.setText(String.format("Order Qnty: %s Lts", order.getQty()));
//                    dispenserAssetId.setText(String.format(" %s", order.getLocation_name()));
                }
            }
        }


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
                if (s != null && s.length() > 0) {
                    if (s.toString().contains("Connecting")) {
                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_red_300));
                    } else {
                        connection_maintainedState.setBackgroundColor(ContextCompat.getColor(context, R.color.md_green_400));
                    }

                }
            }
        });

        connection_maintainedState.setSelected(true);
//        fuelRate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setRate(price);
//            }
//        });

//        findViewById(R.id.test_rate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText etText = findViewById(R.id.et_testRate);
//                setRate(etText.getText().toString());
//                Toast.makeText(context, "Setting Rate", Toast.LENGTH_SHORT).show();
//            }
//        });

//        intialTotalizerTxt.setText(SharedPref.getLastFuelDispenserReading(context));
//        try {
//            intialTotalizer = (SharedPref.getLastFuelDispenserReading(context).isEmpty()) ? 0.0f : Double.parseDouble(SharedPref.getLastFuelDispenserReading(context));
//        } catch (Exception e) {

//            e.printStackTrace();
//        } finally {
//        uniqueTransactionNumber = (SharedPref.getLastTransactionId().isEmpty()) ? UUID.randomUUID() : UUID.fromString(SharedPref.getLastTransactionId());
        uniqueTransactionNumber = order.getTransactionId();

//        }


        fuelDispensed.addTextChangedListener(new TextWatcher() {
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
                        double currentPrice = Double.parseDouble(s.toString()) * Double.parseDouble(fuelRate.getText().toString());
                        if (currentPrice >= 0) {
                            currentUserCharge.setText(String.format(Locale.US, "%.2f", currentPrice));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });

        connectToSynergyWifi();

        //----------------------------------------------------------------------------------------------------------------------------------
        // TODO: 2/22/2019 Start Executor for every Two Seconds to check Current Dispenser State

        getStatusRunnable = new Runnable() {
            public void run() {
                if (Server485_status.getSocket() != null) {
                    getStatusPoll();
                }
            }
        };
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(getStatusRunnable, 0, 1, TimeUnit.SECONDS);

        try {
//            ArrayList<Double> data = new DataBaseHelper(FreshDispenserUnitActivity.this).getValues(INTERPOLATION_VALUE_OF);
//            double interpola = (((data.get(3) - data.get(1)) / (data.get(2) - data.get(0))) * (INTERPOLATION_VALUE_OF - data.get(0))) + data.get(1);
//            Log.d(LoginActivity.class.getSimpleName(), String.format("Volume y1=%.2f , Volume y2=%.2f , Depth x1=%.2f , Depth x2=%.2f , interpolation=%.2f", data.get(1), data.get(3), data.get(0), data.get(2), interpola));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getOrderFullDetails(String orderTransactionId) {
        if (progressDialog != null) {
            progressDialog.setMessage("Getting Order Details,Hold On..");
            progressDialog.show();
        }
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        //2261
        apiInterface.getOrderDetail(orderTransactionId, SharedPref.getVehicleId()).enqueue(new Callback<DeliveryInProgress>() {
            @Override
            public void onResponse(Call<DeliveryInProgress> call, Response<DeliveryInProgress> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null && response.body().getSucc()) {
                    orderDetailed = response.body();
                    asset = response.body().getAssets();
                    fuelDetails = response.body() == null ? new Progress() : response.body().getProgress();
                    price = fuelDetails.getFuelPrice();
                    tvSiteLatitude.setText("Lat: " + fuelDetails.getCurrentLat());
                    tvSiteLongitude.setText("Long: " + fuelDetails.getCurrentLong());
                    if (fuelDetails.getLocationBypass()) {
                        tvByPassGPS.setVisibility(View.VISIBLE);
//                        tvGPSRange.setText("RANGE: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());

                        tvByPassGPS.setVisibility(View.VISIBLE);
                    } else {
                        //To do
                        //by pass check
                    }

                    if (price.isEmpty()) {
                        Toast.makeText(context, "Price Is missing,Please contact Backend Team", Toast.LENGTH_LONG).show();
                    }
//                    dispenserFuelRate.setText(String.format(" %s", fuelDetails.getFuelPrice()));

//                    To Do % or perliter
                    if (fuelDetails.getDiscountType().equalsIgnoreCase("perliter")) {
                        String valueToShow = String.valueOf(Double.valueOf(fuelDetails.getFuelPrice()) - Double.valueOf(fuelDetails.getDiscount()));
                        dispenserFuelRate.setText(String.format(" %s", valueToShow));
                        checkConnectedSetPrice(valueToShow);
                        Toast.makeText(context, "perlitre:-" + valueToShow, Toast.LENGTH_SHORT).show();

                    } else {
                        String valueToShow = String.valueOf(
                                (Double.valueOf(fuelDetails.getFuelPrice()) - (Double.valueOf(fuelDetails.getDiscount()) * .01) / 100));
                        dispenserFuelRate.setText(String.format(" %s ", valueToShow));
                        checkConnectedSetPrice(valueToShow);
                        Toast.makeText(context, "percentage:-" + valueToShow, Toast.LENGTH_SHORT).show();


                    }

//                    checkConnectedSetPrice(price);
//                    showAssetDialog();
                }
            }

            @Override
            public void onFailure(Call<DeliveryInProgress> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void checkOrderFullDetails(String orderTransactionId) {
//        if (progressDialog != null) {
//            progressDialog.setMessage("Please Wait ...");
//            progressDialog.show();
//        }
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        //2261
        apiInterface.getOrderDetail(orderTransactionId, SharedPref.getVehicleId()).enqueue(new Callback<DeliveryInProgress>() {
            @Override
            public void onResponse(Call<DeliveryInProgress> call, Response<DeliveryInProgress> response) {
//                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null && response.body().getSucc()) {

                    checkOrderDetailed = response.body();
                    currentVehicleLat = checkOrderDetailed.getProgress().getCurrentLat();
                    currentVehicleLong = checkOrderDetailed.getProgress().getCurrentLong();
                    orderLocationLat = checkOrderDetailed.getOrder().get(0).getLatitude();
                    orderLocationLong = checkOrderDetailed.getOrder().get(0).getLogitude();

                    cheackFuelDetails = response.body() == null ? new Progress() : response.body().getProgress();

                    if (Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange()) < Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong))))) {

                        String mismatchRange = String.valueOf(Float.valueOf(String.valueOf(LocationUtil.distanceInMeters(Double.valueOf(currentVehicleLat), Double.valueOf(currentVehicleLong), Double.valueOf(orderLocationLat), Double.valueOf(orderLocationLong)))) - Float.valueOf(SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange()));
                        Toast.makeText(context, "Out of Range", Toast.LENGTH_SHORT).show();
                        sureNoShow(mismatchRange, SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
                    } else {
                        Toast.makeText(context, "No Cal", Toast.LENGTH_SHORT).show();

                    }


//                    if (fuelDetails.getLocationBypass()) {
//                        tvByPassGPS.setVisibility(View.VISIBLE);
////                        tvGPSRange.setText("RANGE: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getGpsMismatchRange());
//
//                        tvByPassGPS.setVisibility(View.VISIBLE);
//                    } else {
//                        //To do
//                        //by pass check
//                    }
                }
            }

            @Override
            public void onFailure(Call<DeliveryInProgress> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkConnectedSetPrice(String price) {
        if (pumpStatusTxt.getText().toString().isEmpty() || pumpStatusTxt.getText().toString().equalsIgnoreCase("Connecting...") || pumpStatusTxt.getText().toString().equalsIgnoreCase("Connection Maintained")) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkConnectedSetPrice(price);
                    Log.d("newPrice---->", "checking");
                }
            }, 2000);


        } else {
            Log.d("newPrice", price);
            setRate(price);

        }
    }

//    private void showAssetDialog() {
//        if (asset != null && asset.size() > 1) {
//
//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList("assetList", asset);
//            bundle.putString("qnty", order.getQuantity());
//
//            AssetListDialog assetListDialog = new AssetListDialog();
//            assetListDialog.setAssetListener(new OnAssetOperation() {
//
//
//                @Override
//                public void OnAssetSelected(String assetId, String remainingBalance, Asset asset) {
//
//                    LayoutInflater inflater = getLayoutInflater();
//                    View alertLayout = inflater.inflate(R.layout.selected_asset_qnty_dialog, null);
//                    final EditText et_qnty = alertLayout.findViewById(R.id.et_qnty);
//                    final TextView done = alertLayout.findViewById(R.id.done);
//
//                    selectedAssetId = assetId;
//
//                    AlertDialog.Builder alert = new AlertDialog.Builder(FreshDispenserUnitActivity.this);
//                    alert.setTitle("Enter Quantity For this Asset(Total Qnty :" + order.getQuantity() + ")");
//                    // this is set the view from XML inside AlertDialog
//                    alert.setView(alertLayout);
//                    // disallow cancel of AlertDialog on click of back button and outside touch
//                    alert.setCancelable(false);
//                    AlertDialog dialog = alert.create();
//                    alert.setPositiveButton("Choose Other", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            showAssetDialog();
//                        }
//                    });
//
//                    done.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (et_qnty.length() <= 0) {
//                                Toast.makeText(context, "Enter Valid Data", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            String qnty = et_qnty.getText().toString();
//
//                            if (Double.parseDouble(qnty) <= Double.parseDouble(remainingBalance)) {
//                                dispenserPresetAmount.setText(qnty);
//                                dispenserAssetId.setText(asset.getAssetName());
//                                order.setQuantity(String.valueOf(Double.parseDouble(order.getQuantity()) - Double.parseDouble(qnty)));
//                                Toast.makeText(getBaseContext(), "Quantity Used: " + qnty + " Left: " + order.getQuantity(), Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            } else {
//                                Toast.makeText(getBaseContext(), "Insufficient Balance Left " + qnty + " " + remainingBalance, Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//                    dialog.show();
//
//                }
//            });
//            assetListDialog.setArguments(bundle);
//            assetListDialog.show(getSupportFragmentManager(), AssetListDialog.class.getSimpleName());
//        }
//
//    }


    //----------------------------------------------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------------------------------------------

    // TODO: 2/22/2019 Connect to Synergy Wifi

    /**
     * This Method is used to connect with Synergy Wifi
     */
    private void connectToSynergyWifi() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Server285("192.168.1.103", 54306, (RouterResponseListener) context);
                    new Server485("192.168.1.103", 54307, (RouterResponseListener) context);
                    new Server485_status("192.168.1.103", 54308, (RouterResponseListener) context);
                    new Server485_ReadTxn("192.168.1.103", 54309, (RouterResponseListener) context);
                    setMessage("Connecting...");

                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong With Server", Toast.LENGTH_SHORT).show();
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
                            getStatusRunnable = null;
                            StopServer();
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

    // TODO: 4/22/2019 Stop Server

    private void StopServer() {
        try {
            Server485_status.getAsyncStausServer().stop();
            Server485_ReadTxn.getAsyncReadTxnServer().stop();
            Server285.getAsyncServer().stop();
            Server485.getAsyncServer().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    // TODO: 2/22/2019 Implement Click Listener Logics


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.waveLoadingViewTank1:
                performATGAction();
                break;

            case R.id.btnStop:
//                setLogToFile("\n\n" + "--------------------Pump Stop Requested ------------------" + "\n\n");
                progressDialog.setMessage("Stopping Pump...");
                progressDialog.show();

                final String[] dd = new String[]{Commands.PUMP_STOP.toString(), Commands.STATUS_POLL.toString()};
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

                break;


            case R.id.btn_for_idlestate:
                try {
                    toSetIdle();
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                getStatusRunnable = new Runnable() {
//                    public void run() {
//                        if (Server485_status.getSocket() != null) {
//                            getStatusPoll();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Toast.makeText(context, "Setting to IDLE", Toast.LENGTH_SHORT).show();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            });
//
//                        }
//                    }
//                };


                break;

            case R.id.btnStart:
//                if (isPayableState && isPayableStateLock) {
//                    Toast.makeText(context, "Please Follow Order Completion Steps First", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                isPayableStateLock = true;
                curentTotalizer = 0f;
                fuelDispensed.setText("" + curentTotalizer);
                isLockObtanedForNewTransaction = true;
                nozzleRelayStop();


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String lastId = SharedPref.getLastTransactionId();
                        TransactionDbModel lastModel = db.transactionDbDao().getTransactionById(lastId);
                        if (lastModel != null) {
                            lastModel.setFueling_Stop_Time(String.valueOf(Calendar.getInstance().getTime()));
                            db.transactionDbDao().update(lastModel);
                            System.out.print("DB Updated" + uniqueTransactionNumber);
                            try {
                                Log.d("dbValues", db.transactionDbDao().getTransactionById(SharedPref.getLastTransactionId()).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


//                uniqueTransactionNumber = UUID.randomUUID();
                uniqueTransactionNumber = order.getTransactionId();

//----------------------------------------------------------------------------------------------------------------------------------

                // TODO: 2/18/2019 Start Logging New Transaction
//                setLogToFile("" +
//                        "\n\n\n\n\n"
//                        + "----------------------New Transaction Requested -------------------" +
//                        "\n\n\n\n\n" +
//                        "\nTxn Id= " + String.valueOf(order.getTransactionId())
//                        + "\n"
//                        + " at " + String.valueOf(Calendar.getInstance().getTime())
//                        + "------------------------");

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.transactionDbDao().insertAll(new TransactionDbModel(String.valueOf(uniqueTransactionNumber), String.valueOf(Calendar.getInstance().getTime())));

                                System.out.print("DB Insertion " + uniqueTransactionNumber);
                                SharedPref.setLastTransactionId(String.valueOf(uniqueTransactionNumber));
                                sopDB_Values();
                            }
                        }).start();
                    }
                }, 2000);


                //--------------------------------------------------------------------------------------------------------------------------------

                final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
                setProgressBarMessage("Checking Status");
                progressDialog.show();

                new CommandQueue(refresh, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        if (lastResponse != null) {
                            if (lastResponse.length() <= 14) {
                                status = PollStatus.getPollState(stringToHex(lastResponse));
                                new DispenseNowClick(context).getPollState(stringToHex(lastResponse));
                                ((TextView) findViewById(R.id.pumpStatus)).setText(status);
                                if (status.equalsIgnoreCase("STATE IDLE")) {
                                    setPreset(order.getQty());
//                                    if (asset != null && asset.size() >= 1) {
//                                        if (!String.valueOf(dispenserPresetAmount.getText()).isEmpty()) {
//                                            setPreset(String.valueOf(dispenserPresetAmount.getText()));
//                                            mPresetValue = dispenserPresetAmount.getText().toString();
//                                            Toast.makeText(context, String.valueOf(dispenserPresetAmount.getText()), Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Toast.makeText(context, "Please Select An Asset To deliver Fuel", Toast.LENGTH_SHORT).show();
//                                        }
//                                    } else {
//                                        setPreset(order.getQuantity());
////                                        mPresetValue = dispenserPresetAmount.getText().toString();
//
//                                        Toast.makeText(context, String.valueOf(order.getQuantity()), Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    try {
//                                        progressDialog.setMessage("waiting for preset to set for next 4 seconds");
//                                        progressDialog.show();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    Handler handler = new Handler();
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (asset != null && asset.size() >= 1) {
//                                                if (!String.valueOf(dispenserPresetAmount.getText()).isEmpty()) {
//                                                    setPreset(String.valueOf(dispenserPresetAmount.getText()));
//                                                    mPresetValue = dispenserPresetAmount.getText().toString();
//
//                                                } else {
//                                                    Toast.makeText(context, "Please Select An Asset To deliver Fuel", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } else {
//                                                setPreset(order.getQty());
//                                            }
//                                        }
//                                    }, 2500);
                                }
                                isLockObtanedForNewTransaction = false;
                            }

                            dismissProgressBar();

                        }
                    }

                    @Override
                    public void onAllCommandCompleted(final int currentCommand, final String response) {
                        try {
                            if (currentCommand == 0) {
                                //todo Read Totalizer Value
//                                setIntialTotalizer(response);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }).doCommandChaining();

                break;
//            case R.id.setPresetBtn:
//
//                if (setPresetEdt.getText().length() < 0) {
//                    Toast.makeText(context, "Please Enter Valid Value For Preset", Toast.LENGTH_SHORT).show();
//                } else if (Double.parseDouble(setPresetEdt.getText().toString()) < 1) {
//                    Toast.makeText(context, "Please Enter Value For Preset more than 1.0", Toast.LENGTH_SHORT).show();
//                } else {
//                    setProgressBarMessage("Setting Preset...");
//                    setPreset(setPresetEdt.getText().toString());
//                    mPresetValue = dispenserPresetAmount.getText().toString();
//
//                }
//                break;
//            case R.id.pumpStatusRefresh:
//                refreshPumpState();
//                break;
            case R.id.tvReconnect:

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        connectToSynergyWifi();
                    }
                }, 2000);

                break;

//            case R.id.btnSuspendSale:
//                setLogToFile("\n\n" + "------------------Transaction Suspended Requested -----------------" + "\n\n");
//                suspendSale();
//                break;
//            case R.id.btn_ResumeSale:
//
//                setLogToFile("\n\n" + "--------------------Transaction Resume Requested ------------------" + "\n\n");
//                resumeSale();
//                break;

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
                    }, 1000);
                }

                Bundle bundle = new Bundle();
                bundle.putString("FuelDispensed", String.valueOf(fuelDispensed.getText()));
                bundle.putString("CurrentUserCharge", String.valueOf(currentUserCharge.getText()));
                bundle.putString("FuelRate", String.valueOf(dispenserFuelRate.getText()));
                bundle.putString("startTime", startedTime);
                bundle.putString("selectedAsset", selectedAssetId);
                bundle.putString("atgStart", intialATGReading);
                bundle.putParcelable("orderDetail", orderDetailed);

//                AddReadingsDialog addReadingsDialog = new AddReadingsDialog();
//                addReadingsDialog.setCancelable(false);
//                addReadingsDialog.setArguments(bundle);
//                addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());

                break;
            case R.id.dispenserSelectAsset:
//                showAssetDialog();
                break;
        }

    }

    private void performATGAction() {
        ListenATG();
        ReadATG();

        Toast.makeText(context, "Hello ATG", Toast.LENGTH_SHORT).show();
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
        }, 200);
    }

    private TransactionDbModel getLastIdModel() {
        String lastId = SharedPref.getLastTransactionId();
        TransactionDbModel lastModel = db.transactionDbDao().getTransactionById(lastId);
        return lastModel;
    }


    //----------------------------------------------------------------------------------------------------------------------------------

    /**
     * @param response the response is parsed to get Intial Totalizer Reading
     */
    private void setIntialTotalizer(String response) {
        Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+1?");
        List<String> numbers = new ArrayList<String>();
        Matcher m = p.matcher(response);
        while (m.find()) {
            numbers.add(m.group());
        }
        try {
            if (response != null && response.contains(".")) {
                Log.d("fuelIntial", numbers.get(0).substring(1).replaceFirst("^0+(?!$)", ""));
                intialTotalizer = (Double.parseDouble(numbers.get(0).substring(1).replaceFirst("^0+(?!$)", "")));
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intialTotalizerTxt.setText(String.format("%s", String.valueOf(intialTotalizer)));
                        SharedPref.setLastFuelDispenserReading(intialTotalizerTxt.getText().toString());
                    }
                }, 200);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            {

            }
        }

    }

    /**
     * This method is used to Authorize Fueling ,When FCC CON is displayed on Dispenser.
     */
    private void authorizeFueling() {
//        nozzleRelayStart();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }, 20000);

        final AlertDialog.Builder builder = new AlertDialog.Builder(FreshDispenserUnitActivity.this);
//        String relayCheck = Login
        //To DO
        //send Command RL11->IKNOWATIONS:#*RL11
//        findMakeOfATG();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        builder.setMessage("Are you sure to Start Fueling?")
//                .setTitle("Fueling starts in" + millisUntilFinished / 1000 + " seconds")

                .setCancelable(false)
                .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nozzleRelayStart();


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
                                new CommandQueue(authorized, new OnAllCommandCompleted() {
                                    @Override
                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                        curentTotalizer = 0f;
                                        fuelDispensed.setText("" + curentTotalizer);
                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onAllCommandCompleted(int currentCommand, String response) {
                                        try {

                                        } catch (Exception e) {

                                        }
                                    }
                                }).doCommandChaining();
                            }

                        }, 20000);

//                        new CountDownTimer(20000, 1000) {
//
//                            public void onTick(long millisUntilFinished) {
////                                Toast.makeText(context, "Fueling starts in" + millisUntilFinished / 1000 + " seconds", Toast.LENGTH_SHORT).show();
//                            }
//
//                            public void onFinish() {
//
//                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
//                                new CommandQueue(authorized, new OnAllCommandCompleted() {
//                                    @Override
//                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                        curentTotalizer = 0f;
//                                        fuelDispensed.setText("" + curentTotalizer);
//                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onAllCommandCompleted(int currentCommand, String response) {
//                                        try {
//
//                                        } catch (Exception e) {
//
//                                        }
//                                    }
//                                }).doCommandChaining();
//                            }
//                        }.start();


//                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
//                                new CommandQueue(authorized, new OnAllCommandCompleted() {
//                                    @Override
//                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                        curentTotalizer = 0f;
//                                        fuelDispensed.setText("" + curentTotalizer);
//                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                    @Override
//                                    public void onAllCommandCompleted(int currentCommand, String response) {
//                                        try {
//
//                                        } catch (Exception e) {
//
//                                        }
//                                    }
//                                }).doCommandChaining();
                    }
                })
                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

//    private void _authorizeFueling() {
//        nozzleRelayStart();
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(FreshDispenserUnitActivity.this);
////        String relayCheck = Login
//                //To DO
//                //send Command RL11->IKNOWATIONS:#*RL11
////        findMakeOfATG();
////        final Handler handler = new Handler();
////        handler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
//                builder.setMessage("Are you sure to Start Fueling?")
////                .setTitle("Fueling starts in" + millisUntilFinished / 1000 + " seconds")
//
//                        .setCancelable(false)
//                        .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
////                        nozzleRelayStart();
//
////                        final Handler handler = new Handler();
////                        handler.postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
//
//                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
//                                new CommandQueue(authorized, new OnAllCommandCompleted() {
//                                    @Override
//                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                        curentTotalizer = 0f;
//                                        fuelDispensed.setText("" + curentTotalizer);
//                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onAllCommandCompleted(int currentCommand, String response) {
//                                        try {
//
//                                        } catch (Exception e) {
//
//                                        }
//                                    }
//                                }).doCommandChaining();
////                            }
////
////                        }, 20000);
//
////                        new CountDownTimer(20000, 1000) {
////
////                            public void onTick(long millisUntilFinished) {
//////                                Toast.makeText(context, "Fueling starts in" + millisUntilFinished / 1000 + " seconds", Toast.LENGTH_SHORT).show();
////                            }
////
////                            public void onFinish() {
////
////                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
////                                new CommandQueue(authorized, new OnAllCommandCompleted() {
////                                    @Override
////                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                        curentTotalizer = 0f;
////                                        fuelDispensed.setText("" + curentTotalizer);
////                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
////                                    }
////
////                                    @Override
////                                    public void onAllCommandCompleted(int currentCommand, String response) {
////                                        try {
////
////                                        } catch (Exception e) {
////
////                                        }
////                                    }
////                                }).doCommandChaining();
////                            }
////                        }.start();
//
//
////                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
////                                new CommandQueue(authorized, new OnAllCommandCompleted() {
////                                    @Override
////                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                        curentTotalizer = 0f;
////                                        fuelDispensed.setText("" + curentTotalizer);
////                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
////
////                                    }
////
////                                    @Override
////                                    public void onAllCommandCompleted(int currentCommand, String response) {
////                                        try {
////
////                                        } catch (Exception e) {
////
////                                        }
////                                    }
////                                }).doCommandChaining();
//                            }
//                        })
//                        .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
//                                new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
//                                    @Override
//                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                        isAlreadyPopForAuthorize = false;
//                                        Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                    @Override
//                                    public void onAllCommandCompleted(int currentCommand, String response) {
//                                        try {
//
//                                        } catch (Exception e) {
//
//                                        }
//                                    }
//                                }).doCommandChaining();
//
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
//                Toast.makeText(context, "This msg Belongs to Authorized state", Toast.LENGTH_SHORT).show();
//            }
//        }, 20000);
//
////        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
//////        String relayCheck = Login
////        //To DO
////        //send Command RL11->IKNOWATIONS:#*RL11
//////        findMakeOfATG();
//////        final Handler handler = new Handler();
//////        handler.postDelayed(new Runnable() {
//////            @Override
//////            public void run() {
////        builder.setMessage("Are you sure to Start Fueling?")
//////                .setTitle("Fueling starts in" + millisUntilFinished / 1000 + " seconds")
////
////                .setCancelable(false)
////                .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
//////                        nozzleRelayStart();
////
//////                        final Handler handler = new Handler();
//////                        handler.postDelayed(new Runnable() {
//////                            @Override
//////                            public void run() {
////
////                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
////                                new CommandQueue(authorized, new OnAllCommandCompleted() {
////                                    @Override
////                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                        curentTotalizer = 0f;
////                                        fuelDispensed.setText("" + curentTotalizer);
////                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
////                                    }
////
////                                    @Override
////                                    public void onAllCommandCompleted(int currentCommand, String response) {
////                                        try {
////
////                                        } catch (Exception e) {
////
////                                        }
////                                    }
////                                }).doCommandChaining();
//////                            }
//////
//////                        }, 20000);
////
//////                        new CountDownTimer(20000, 1000) {
//////
//////                            public void onTick(long millisUntilFinished) {
////////                                Toast.makeText(context, "Fueling starts in" + millisUntilFinished / 1000 + " seconds", Toast.LENGTH_SHORT).show();
//////                            }
//////
//////                            public void onFinish() {
//////
//////                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
//////                                new CommandQueue(authorized, new OnAllCommandCompleted() {
//////                                    @Override
//////                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//////                                        curentTotalizer = 0f;
//////                                        fuelDispensed.setText("" + curentTotalizer);
//////                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
//////                                    }
//////
//////                                    @Override
//////                                    public void onAllCommandCompleted(int currentCommand, String response) {
//////                                        try {
//////
//////                                        } catch (Exception e) {
//////
//////                                        }
//////                                    }
//////                                }).doCommandChaining();
//////                            }
//////                        }.start();
////
////
//////                                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
//////                                new CommandQueue(authorized, new OnAllCommandCompleted() {
//////                                    @Override
//////                                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//////                                        curentTotalizer = 0f;
//////                                        fuelDispensed.setText("" + curentTotalizer);
//////                                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
//////
//////                                    }
//////
//////                                    @Override
//////                                    public void onAllCommandCompleted(int currentCommand, String response) {
//////                                        try {
//////
//////                                        } catch (Exception e) {
//////
//////                                        }
//////                                    }
//////                                }).doCommandChaining();
////                    }
////                })
////                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
////                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
////                            @Override
////                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                isAlreadyPopForAuthorize = false;
////                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
////
////                            }
////
////                            @Override
////                            public void onAllCommandCompleted(int currentCommand, String response) {
////                                try {
////
////                                } catch (Exception e) {
////
////                                }
////                            }
////                        }).doCommandChaining();
////
////                        dialog.cancel();
////                    }
////                });
////        AlertDialog alert = builder.create();
////        alert.show();
//
//    }


////        }, 3000);
//
//
////        builder.setMessage("Are you sure to continue Fueling?")
////                .setCancelable(false)
////                .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////
////                        final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
////                        new CommandQueue(authorized, new OnAllCommandCompleted() {
////                            @Override
////                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                curentTotalizer = 0f;
////                                fuelDispensed.setText("" + curentTotalizer);
////                                Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
////
////                            }
////
////                            @Override
////                            public void onAllCommandCompleted(int currentCommand, String response) {
////                                try {
////
////                                } catch (Exception e) {
////
////                                }
////                            }
////                        }).doCommandChaining();
////                    }
////                })
////                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
////                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
////                            @Override
////                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                                isAlreadyPopForAuthorize = false;
////                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
////
////                            }
////
////                            @Override
////                            public void onAllCommandCompleted(int currentCommand, String response) {
////                                try {
////
////                                } catch (Exception e) {
////
////                                }
////                            }
////                        }).doCommandChaining();
////
////                        dialog.cancel();
////                    }
////                });
////        AlertDialog alert = builder.create();
////        alert.show();
//
////    }
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

    private void refreshPumpState() {
        final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
        showProgressBar();

        new CommandQueue(refresh, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Toast.makeText(context, "Pump Status Refresh Successfully", Toast.LENGTH_SHORT).show();

                dismissProgressBar();
            }

            @Override
            public void onAllCommandCompleted(final int currentCommand, final String response) {
                if (response != null) runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            setProgressBarMessage(Commands.getEnumByString(refresh[currentCommand]));
                            String status = PollStatus.getPollState(stringToHex(response));
                            Log.d("statusReceived", status + " " + response + currentCommand);
                            ((TextView) findViewById(R.id.pumpStatus)).setText(status);
                        } catch (Exception e) {
                            Log.d("statusReceivedExcep", e.getLocalizedMessage() + " " + response + currentCommand);
                            dismissProgressBar();
                        }
                    }
                });

            }
        }).doCommandChaining();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

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

    private String stringToHexWithoutSpace(String string) {
        StringBuilder buf = new StringBuilder(200);
        for (char ch : string.toCharArray()) {
            buf.append(String.format("%02X", (int) ch));
        }
        return buf.toString();
    }

    private static String _stringToHexWithoutSpace(String string) {
        StringBuilder buf = new StringBuilder(200);
        for (char ch : string.toCharArray()) {
            buf.append(String.format("%02X", (int) ch));
        }
        return buf.toString();

    }

    public void setMessage(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connection_maintainedState.setText(msg);
            }
        });
    }


    @Override
    public void OnRouter485QueueAborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connection_maintainedState.setText("Connection Aborted");
            }
        });
    }

    @Override
    public void OnRouter285Connected() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        checkAteInitialValue();
                    }
                }, 2000);
                Toast.makeText(context, "Connected to 285", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAteInitialValue() {
        try {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait checking ATG Reading");
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(FreshDispenserUnitActivity.class.getSimpleName(), "Checking ATG Intial Readings");
        if (Server285.getSocket() != null) {

            String[] commandsATg = new String[]{Commands.LISTEN_SP3_285.toString(), getAtgSerial(0), Commands.LISTEN_SP4_285.toString(), getAtgSerial(1)};

            for (String command : commandsATg) {
                send285Command(command, 1500);
            }

        }

//            SharedPref.getLoginResponse().getVehicle_data().get(0).getCompartmentInfo().get(0).getAtgSerialNo()
//            new Command285Queue(new String[]{Commands.LISTEN_ATG_1_285.toString(), "M23872\r\n", "M23872\r\n"}, new OnAllCommandCompleted() {
//                @Override
//                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                    Log.d(DispenserUnitActivity.class.getSimpleName(), lastResponse + "");
//                    if (progressDialog != null && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
//                }
//
//                @Override
//                public void onAllCommandCompleted(int currentCommand, String response) {
//                    Log.d(DispenserUnitActivity.class.getSimpleName(), response + "");
//                }
//            }, 5000).doCommandChaining();
//        }

    }

    private String getAtgSerial(int atgSerialNoPosition) {
//        return String.format(Locale.CANADA, "M%s\r\r", SharedPref.getLoginResponse().getVehicle_data().get(0).getCompartmentInfo().get(atgSerialNoPosition).getAtgSerialNo());
        return "hello";
    }

    @Override
    public void OnRouter285Aborted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Disconnected from 285", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnRouter285RfidConnected() {

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

    @Override
    public void OnRfIdReceived(String rfIdFOund) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                if (!isIntialATG) {
//                    ReadATG1();
//                }
                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(50); //You can manage the blinking time with this parameter
                findViewById(R.id.blinkCircle).startAnimation(anim);
                String rfId = rfIdFOund.replaceFirst("^0+(?!$)", "");

                if (rfIdFOund != null && rfIdFOund.contains(".") && rfIdFOund.trim().length() == 34) {
                    Log.d("232FoundATG", rfIdFOund + " ");

                    try {
                        if (!isIntialATG) {
                            if (rfIdFOund != null && !rfIdFOund.isEmpty() && rfIdFOund.trim().length() == 34) {
                                isIntialATG = true;
                                try {
                                    String data = rfIdFOund.substring(13, rfIdFOund.indexOf(".") + 3);
                                    intialATGReading = data;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tank1.setText(data);
                                        }
                                    });
                                    Log.d("initialReading", data + " ");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    isIntialATG = false;
                                    ReadATG();
                                }
                            }

                        } else {
                            endReadingIs = intialATG;
                        }

                    } catch (Exception e) {
                        Toast.makeText(context, String.valueOf(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + rfIdFOund + "\n");


                } else if (rfId != null && rfId.length() <= 12) {
                    rF_IdTxt.setTextColor(ContextCompat.getColor(FreshDispenserUnitActivity.this, R.color.black));
                    rF_IdTxt.setText(String.format(Locale.US, "RF Id: %s", rfId));
                    if (rfId.startsWith("c") || rfId.startsWith("C")) {
                        dispensingIn.setText(("DISPENSING IN:Jerry Can"));
                        if (!isAlreadyPopForJerryCan) {
                            isAlreadyPopForJerryCan = true;
                            if (!isAlreadyPopForAuthorize) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(FreshDispenserUnitActivity.this);
                                builder.setMessage("Do you want to proceed Fueling in Jerry Can?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                authorizeFueling();
                                                isAlreadyPopForAuthorize = true;
                                                dialog.cancel();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                isAlreadyPopForAuthorize = false;
                                                isAlreadyPopForJerryCan = false;
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    } else if (rfId != null && rfId.trim().length() == 1 && rfId.equalsIgnoreCase("�")) {
                        if (pumpStatusTxt.getText().toString().equalsIgnoreCase("FUELING STATE")) {
                            if (rfNotCloseProgress == null) {
//                                showRfNotCloseError();
                            } else {
//                                if (!rfNotCloseProgress.isShowing()) {
//                                    rfNotCloseProgress.show();
//                                }
                            }
//                            suspendSale();
                        }
                        rF_IdTxt.setText(String.format(Locale.US, "RF Id: %s", "No Rf ID found"));
                        dispensingIn.setText(("DISPENSING IN:"));
                        rF_IdTxt.setTextColor(ContextCompat.getColor(FreshDispenserUnitActivity.this, R.color.md_red_400));
                    } else {

                        if (isAlreadyPopForJerryCan && isAlreadyPopForAuthorize) {
                            Log.d("dispensingFaulty", "Fueling is already authorized for Jerry can");
                            try {
//                                Log.d("rfIdSubstring", rfId.substring(0, 6) + "");

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        } else if (rfId != null && rfId.length() > 6 && !isAlreadyPopForAuthorize) {
                            if (!pumpStatusTxt.getText().toString().isEmpty()) {
                                isAlreadyPopForAuthorize = true;
                                authorizeFueling();
                                dispensingIn.setText(("DISPENSING IN:Asset"));
                            } else {
                                if (!pumpStatusTxt.getText().toString().isEmpty() && pumpStatusTxt.getText().toString().equalsIgnoreCase("PAYABLE STATE")) {
                                    if (payableProgress == null) {
                                        showRfErrorInPayableMode();
                                    } else {
                                        if (!payableProgress.isShowing()) {
                                            payableProgress.show();
                                        }
                                    }
                                } else {
                                    dismissRfErrorInPayableMode();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void OnRouter285ATGConnected() {

    }

    @Override
    public void OnRouter285ATGAborted() {

    }


    @Override
    public void OnATGReceivedLK(String atg) {

    }

    @Override
    public void OnATGReceivedLK2(String atg) {

    }

    private void showRfErrorInPayableMode() {
        payableProgress = new ProgressDialog(context);
        payableProgress.setMessage("Pump not in idle state ,Please took nozzle back,and press start for getting started ");
        payableProgress.show();
    }

    private void showRfNotCloseError() {
        rfNotCloseProgress = new ProgressDialog(context);
        rfNotCloseProgress.setMessage("Rf Id not found ,Suspending Fueling..");
        rfNotCloseProgress.show();
    }

    private void dismissRfNotCloseError() {
        if (rfNotCloseProgress != null && rfNotCloseProgress.isShowing()) {
            rfNotCloseProgress.dismiss();
        }
    }

    private void dismissRfErrorInPayableMode() {
        if (payableProgress != null && payableProgress.isShowing()) {
            payableProgress.dismiss();
        }
    }


    private void broadcastChannelIdForJerryCan() {

    }


    @Override
    public void OnRouter485QueueConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getVolumeTotalizer();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getVolumeTotalizer();
                    }
                }, 200);
            }
        });


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Connected To Queue", Toast.LENGTH_SHORT).show();
                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));


            }
        });
    }

    private void getTotalizer() {
        final String[] readTxn = new String[]{Commands.READ_TXN.toString()};
        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
            @Override
            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {

            }


            @Override
            public void OnTxnQueueCommandCompleted(final int currentCommand, final String lastResponse) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (lastResponse != null && StringUtils.countMatches(lastResponse, ".") == 3) {
                                try {
                                    final String txnFuelRate = lastResponse.substring(4, lastResponse.indexOf(".") + 3);

                                    final String txnDispense = lastResponse
                                            .substring(lastResponse.indexOf(".") + 3, CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3);

                                    final String txnCharges = lastResponse
                                            .substring(CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3, CommonUtils.findNextOccurance(lastResponse, ".", 3) + 3);

                                    Log.i("fuelDispenseNewTxn", lastResponse + " amount = " + txnFuelRate + "dispense=" + txnDispense + " charges= " + txnCharges);

                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if (curentTotalizer < 0) {
                                                    return;
                                                }
                                                fuelDispensed.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
                                                currentUserCharge.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));
                                                fuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate)));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 200);

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

    private void _getTotalizer() {
        final String[] readTxn = new String[]{Commands.READ_TXN.toString(), Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()};
        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
            @Override
            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {

            }


            @Override
            public void OnTxnQueueCommandCompleted(final int currentCommand, final String lastResponse) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (lastResponse != null && StringUtils.countMatches(lastResponse, ".") == 3) {
                                try {
                                    final String txnFuelRate = lastResponse.substring(4, lastResponse.indexOf(".") + 3);

                                    final String txnDispense = lastResponse
                                            .substring(lastResponse.indexOf(".") + 3, CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3);

                                    final String txnCharges = lastResponse
                                            .substring(CommonUtils.findNextOccurance(lastResponse, ".", 2) + 3, CommonUtils.findNextOccurance(lastResponse, ".", 3) + 3);

                                    Log.i("fuelDispenseNewTxn", lastResponse + " amount = " + txnFuelRate + "dispense=" + txnDispense + " charges= " + txnCharges);

                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if (curentTotalizer < 0) {
                                                    return;
                                                }
                                                fuelDispensed.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
                                                currentUserCharge.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));
                                                fuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate)));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 200);

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


    private void getVolumeTotalizer() {

        final String[] readVolume = new String[]{Commands.READ_VOL_TOTALIZER.toString()};


        new CommandReadTxnQueue(readVolume, new OnTxnQueueCompleted() {
            @Override
            public void onTxnQueueEmpty(boolean isEmpty, String response) {
                Log.i("fuelDispenseNewVol", response + "");
                if (response != null && StringUtils.countMatches(response, ".") == 1) {


                    try {
                        final String txnFuelVolume = response.substring(4, response.indexOf(".") + 3);

                        if (Double.parseDouble(txnFuelVolume) != 0 && Double.parseDouble(txnFuelVolume) != Double.parseDouble(fuelRate.getText().toString())) {
                            Log.e("totalizerVolume", txnFuelVolume);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    intialTotalizerTxt.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelVolume)));
                                }
                            });

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    getVolumeTotalizer();
                }

            }

            @Override
            public void OnTxnQueueCommandCompleted(int currentCommand, String response) {

            }
        }).doCommandChaining();


    }


    @Override
    public void OnRouter485StatusConnected() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getStatusPoll();
                    }

                }, 4000);
//                Toast.makeText(context, "Setting to IDLE", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void OnRouter485TxnConnected() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Connected to Txn ", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Checking Last Readings" +
                        "", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getTotalizer();
                    }
                }, 4000);
                getTotalizer();

            }
        });
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

    public void setProgressBarMessage(String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
        });


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

    public void setPreset(String volume) {
        if (Server485.getSocket() != null) {
            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));
            String command = "01415031" + stringToHexWithoutSpace(b) + "7F";

            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
            Log.d(
                    "ExecutingCommand", command
                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
                            + " totalCommand= " + checkSumCommand
                            + " String-" + convertToAscii(command)
                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));


            final String[] d = new String[]{Commands.READ_VOL_TOTALIZER.toString(), checkSumCommand, Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString()};
            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
                @Override
                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                    setPresetWithoutStart(volume);
//                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
//                    try {
//
//                        dismissProgressBar();
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
//                        builder.setMessage("Please Take Nozzle to fueling position")
//                                .setCancelable(false)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        authorizeFueling();
//                                    }
//                                })
//                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        isAlreadyPopForAuthorize = false;
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
//                        AlertDialog alert = builder.create();
//                        alert.show();

//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }

                @Override
                public void onAllCommandCompleted(int currentCommand, String response) {
//                    try {
//                        if (currentCommand == 0) {
//                            todo Read Totalizer Value
//                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(fuelRate.getText().toString())) {
//
//                                setIntialTotalizer(response);
//                            }
//                        }
//                    } catch (Exception e) {
//
//                    }
                }
            });
            data.doCommandChaining();
        }

    }

    public static void _setPreset(String volume) {
        if (Server485.getSocket() != null) {
            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));
            String command = "01415031" + _stringToHexWithoutSpace(b) + "7F";

            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
            Log.d(
                    "ExecutingCommand", command
                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
                            + " totalCommand= " + checkSumCommand
                            + " String-" + convertToAscii(command)
                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));


            final String[] d = new String[]{Commands.READ_VOL_TOTALIZER.toString(), checkSumCommand, Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString()};
            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
                @Override
                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                    _setPresetWithoutStart(volume);
//                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
//                    try {
//
//                        dismissProgressBar();
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(DispenserUnitActivity.this);
//                        builder.setMessage("Please Take Nozzle to fueling position")
//                                .setCancelable(false)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        authorizeFueling();
//                                    }
//                                })
//                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        isAlreadyPopForAuthorize = false;
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
//                        AlertDialog alert = builder.create();
//                        alert.show();

//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }

                @Override
                public void onAllCommandCompleted(int currentCommand, String response) {
//                    try {
//                        if (currentCommand == 0) {
//                            todo Read Totalizer Value
//                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(fuelRate.getText().toString())) {
//
//                                setIntialTotalizer(response);
//                            }
//                        }
//                    } catch (Exception e) {
//
//                    }
                }
            });
            data.doCommandChaining();
        }

    }

    public void setPresetWithoutStart(String volume) {
        if (Server485.getSocket() != null) {
            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(volume) * 100));
            String command = "01415031" + stringToHexWithoutSpace(b) + "7F";

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

                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
                    try {
                        dismissProgressBar();
                        if (builder == null) builder = new AlertDialog.Builder(FreshDispenserUnitActivity.this);
                        builder.setMessage("Please Take Nozzle to fueling position")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

//                                        nozzleRelayStop();
                                        authorizeFueling();
//                                        _authorizeFueling();


                                    }
                                })
                                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        isAlreadyPopForAuthorize = false;
                                        isAlreadyPopForJerryCan = false;
                                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
                                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
                                            @Override
                                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onAllCommandCompleted(int currentCommand, String response) {
                                                try {
                                                } catch (Exception e) {
                                                }
                                            }
                                        }).doCommandChaining();

                                        dialog.cancel();
                                    }
                                });
                        if (alert == null) alert = builder.create();
                        if (!alert.isShowing()) {
                            alert.show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAllCommandCompleted(int currentCommand, String response) {
//                    try {
//                        if (currentCommand == 0) {
//                            todo Read Totalizer Value
//                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(fuelRate.getText().toString())) {
//
//                                setIntialTotalizer(response);
//                            }
//                        }
//                    } catch (Exception e) {
//
//                    }
                }
            });
            data.doCommandChaining();
        }

    }
//    public  static void _setPresetWithoutStart(String volume) {
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
//
//                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
//                    try {
//                        dismissProgressBar();
//                        if (builder == null) builder = new AlertDialog.Builder(DispenserUnitActivity.this);
//                        builder.setMessage("Please Take Nozzle to fueling position")
//                                .setCancelable(false)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//
////                                        nozzleRelayStop();
//                                        authorizeFueling();
////                                        _authorizeFueling();
//
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
////                            if (StringUtils.countMatches(response, ".") == 1 && Double.parseDouble(response) != 0 && Double.parseDouble(response) != Double.parseDouble(fuelRate.getText().toString())) {
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

    /**
     * @param fuelPrice this is used to set New Price per Ltr of Fuel in Dispenser Unit
     */
    public void setRate(String fuelPrice) {
        ProgressDialog setRateProgressDialog = new ProgressDialog(context);
        setRateProgressDialog.setMessage("Setting New Fuel Price:" + fuelPrice);
        setRateProgressDialog.show();
        if (Server485.getSocket() != null) {
            String b = String.format(Locale.US, "%06d", (int) (Double.parseDouble(fuelPrice) * 100));

            String integerr = b.substring(0, 4);
            String decimal = b.substring(4, 6);

            Log.e("LeftPart", integerr);
            Log.e("RightPart", decimal);
            Log.e("RatetoUpdate", b);

//            Log.e("RateIndividual", Integer.valueOf(fuelPrice) + "");
//            int indexOfDecimal = fuelPrice.indexOf(".");
//            Log.e("Int Part", fuelPrice.substring(0, indexOfDecimal));
//            Log.e("Decimal Part", fuelPrice.substring(indexOfDecimal));


            String command = "014142" + stringToHexWithoutSpace(integerr) + "2E" + stringToHexWithoutSpace(decimal) + "7F";


            Log.e("RatetoUpdate--", command);

            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
//            String checkSumCommand = "014142303035302E30307F56";
            Log.d(
                    "ExecutingRateCommand", command
                            + "checksum= " + calculateCheckSum((convertToAscii(command)).getBytes())
                            + " totalCommand= " + checkSumCommand
                            + " String-" + convertToAscii(command)
                            + " Byte=" + printByteArray(convertToAscii(checkSumCommand).getBytes()));


            final String[] d = new String[]{Commands.READ_VOL_TOTALIZER.toString(), checkSumCommand, Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString()};
            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
                @Override
                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                    Toast.makeText(BrancoApp.getContext(), "New Fuel Rate Set ", Toast.LENGTH_SHORT).show();
                    Log.i("rate", lastResponse + "");
                    dismissProgressBar();
                    if (setRateProgressDialog != null && setRateProgressDialog.isShowing()) {
                        setRateProgressDialog.dismiss();
                    }
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(SynergyDashboard.this);
//                    builder.setMessage("Please Take Nozzle to fueling position,Press Start When Ready")
//                            .setCancelable(false)
//                            .setPositiveButton("Start ", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    authorizeFueling();
//                                }
//                            })
//                            .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
//                                    new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
//                                        @Override
//                                        public void commandsQueueEmpty(boolean isEmpty, String lastResponse) {
//                                            Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                        @Override
//                                        public void onCommandCompleted(int currentCommand, String response) {
//                                            try {
//                                            } catch (Exception e) {
//                                            }
//                                        }
//                                    }).doCommandChaining();
//
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();

                }

                @Override
                public void onAllCommandCompleted(int currentCommand, String response) {
                    try {
                        if (currentCommand == 0) {
                            //todo Read Totalizer Value
                            Log.i("rate", response + "");
                        }
                    } catch (Exception e) {

                    }
                }
            });
            data.doCommandChaining();
        } else {

//            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(fuelPrice) * 1));
//            Log.e("RatetoUpdate>>", b);
//            String command = "014142" + stringToHexWithoutSpace(b) + "7F";
//
//
//            Log.e("RatetoUpdate-->>", command);
//            String checkSumCommand = command + calculateCheckSum((convertToAscii(command)).getBytes());
//
//            Log.d(
//                    "ExecutingRateCommand>>", command
//                            + "checksum=>> " + calculateCheckSum((convertToAscii(command)).getBytes())
//                            + " totalCommand=>> " + checkSumCommand
//                            + " String->>" + convertToAscii(command)
//                            + " Byte=>>" + printByteArray(convertToAscii(checkSumCommand).getBytes()));
        }

    }

    /**
     * @param byteData this is used to calculate Checksum that is needed for write operations in Dispenser Communication.
     * @return
     */
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

    public void readVol() {
        final String[] readVol = new String[]{Commands.READ_VOL_TOTALIZER.toString()};

        CommandReadTxnQueue.getInstance(readVol, new OnTxnQueueCompleted() {
            @Override
            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {

            }


            @Override
            public void OnTxnQueueCommandCompleted(final int currentCommand, final String response) {
                try {
                    if (currentCommand == 0) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (response != null && response.contains(".")) {
                                    Pattern pattern = Pattern.compile("0000");
                                    Matcher matcher = pattern.matcher(response);
                                    String data = response.replaceAll("[^0-9]", "");
                                    try {
                                        if (matcher.end() != -1) {
                                            Log.i("fuelDispense", response + " matcher= " + data.substring(matcher.end()));
                                        }
                                    } catch (IllegalStateException e) {
                                        e.printStackTrace();
                                    } finally {
                                        Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+1?");
                                        List<String> numbers = new ArrayList<String>();
                                        Matcher m = p.matcher(response);
                                        while (m.find()) {
                                            numbers.add(m.group());
                                        }
                                        try {
                                            curentTotalizer = (Double.parseDouble(numbers.get(0).substring(1).replaceFirst("^0+(?!$)", ""))) - intialTotalizer;
                                            Log.i("fuelDispenseNew", response + " intial= " + intialTotalizer + "current=" + (Double.parseDouble(numbers.get(0).substring(1).replaceFirst("^0+(?!$)", "")) - intialTotalizer));
                                            Handler handler = new Handler(Looper.getMainLooper());
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (curentTotalizer < 0) {
                                                        return;
                                                    }
                                                    fuelDispensed.setText(String.format(Locale.US, "%.2f", curentTotalizer));
                                                }
                                            }, 200);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }

                            }
                        }).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).doCommandChaining();
    }

    /**
     * this method is used to suspend Current Fueling
     */
    void suspendSale() {
        suspendEvent++;
        final String[] refresh = new String[]{Commands.SUSPEND_SALE.toString()};
        progressDialog.show();

        new CommandQueue(refresh, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                progressDialog.dismiss();

            }

            @Override
            public void onAllCommandCompleted(final int currentCommand, final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressDialog.setMessage(Commands.getEnumByString(refresh[currentCommand]));

                        } catch (Exception e) {
                            Log.d("statusReceivedExcep", e.getLocalizedMessage() + " " + response + currentCommand);
                            progressDialog.dismiss();
                        }
                    }
                });

            }
        }).doCommandChaining();

    }

    /**
     * this method is used to Resume already Suspended Fueling.
     */
    void resumeSale() {
        final String[] refresh = new String[]{Commands.RESUME_SALE.toString()};
        progressDialog.show();

        new CommandQueue(refresh, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {

                Log.d("print", lastResponse + "");
                progressDialog.dismiss();
            }

            @Override
            public void onAllCommandCompleted(final int currentCommand, final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressDialog.setMessage(Commands.getEnumByString(refresh[currentCommand]));
                        } catch (Exception e) {
                            Log.d("statusReceivedExcep", e.getLocalizedMessage() + " " + response + currentCommand);
                            progressDialog.dismiss();
                        }
                    }
                });

            }
        }).doCommandChaining();

    }


    public void setStatusMessage(final String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pumpStatusTxt.setText(status);
            }
        });

    }


    /**
     * This Method is Used to get Current State of Fuel Dispenser Unit .
     * States could be any from
     * STATE_IDLE,  CALL STATE, PRESET READY STATE, FUELING STATE,  FUELING STATE,  PAYABLE STATE
     * SUSPENDED STATE, STOPPED STATE, IN-OPERATIVE STATE, AUTHORIZE STATE, Suspend Started State
     * WAIT FOR PRESET STATE,STARTED STATE .
     */

    void getStatusPoll() {
        final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
        CommandStatusQueue commandStatus = CommandStatusQueue.getInstance(refresh, new OnStatusQueueListener() {
            @Override
            public void onStatusQueueQueueEmpty(boolean isEmpty, String lastResponse) {

            }

            @Override
            public void OnStatusQueueCommandCompleted(final int currentCommand, final String response) {

                if (response != null) {
                    if (response.length() <= 14) {
                        status = PollStatus.getPollState(response);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!status.isEmpty()) {
                                if (status.contains("STATE IDLE") || status.equalsIgnoreCase("CALL STATE")) {
                                  Log.e("STATE1","IDLE");
//                                    Toast.makeText(context, "Connecting to IDLE ", Toast.LENGTH_SHORT).show();

//                                    findViewById(R.id.setPresetLay).setVisibility(View.VISIBLE);
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                } else {
//                                    findViewById(R.id.setPresetLay).setVisibility(View.GONE);
                                }
                                if (status.equalsIgnoreCase("FUELING STATE")) {
                                    Log.e("STATE1","FUELING");
//                                    readVol();
                                    if (!isLockObtanedForNewTransaction) {
                                        getTotalizer();
//                                        getVolumeTotalizer();
                                    }
//                                    findViewById(R.id.pdfConvert).setVisibility(View.GONE);
//                                    startLogTransaction(String.valueOf(uniqueTransactionNumber), intialTotalizer, fuelDispensed.getText().toString(), currentUserCharge.getText().toString(), String.valueOf(Calendar.getInstance().getTime()));

                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(true);

                                } else if (status.equalsIgnoreCase("PAYABLE STATE") || status.equalsIgnoreCase("STOPPED STATE")) {
                                    Log.e("STATE1","PAYABLE");
                                    isAlreadyPopForAuthorize = false;
//                                    readVol();
                                    if (!isLockObtanedForNewTransaction) {
                                        //To DO
                                        //send Command RL10->IKNOWATIONS:#*RL1O
//                                        nozzleRelayStop();
//                                        getTotalizer();
                                        _getTotalizer();

                                    }
                                    isPayableState = true;
                                    findViewById(R.id.orderCompletedLay).setVisibility(View.VISIBLE);
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                                } else {
                                    isPayableState = false;
                                    //To DO
//                                    nozzleRelayStop();
                                    //send Command RL10->IKNOWATIONS:#*RL1O
                                    CommandReadTxnQueue.TerminateCommandChain();
//                                    findViewById(R.id.orderCompletedLay).setVisibility(View.VISIBLE);

                                }
                                if (status.equalsIgnoreCase("SUSPENDED STATE") || status.equalsIgnoreCase("SUSPEND STARTED STATE")) {
                                       Log.e("STATE1","SUSPENDED");
//                                    nozzleRelayStop();
                                    dismissRfNotCloseError();
                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(true);
                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
                                    findViewById(R.id.orderCompletedLay).setVisibility(View.VISIBLE);
                                    findViewById(R.id.orderCompletedLay).setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreeen));


                                } else if (status.equalsIgnoreCase("STARTED STATE")) {
                                    Log.e("STATE1","STARTED");
                                    startedTime = String.valueOf(Calendar.getInstance().getTime());
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(true);
                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(false);
                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);

                                } else if (status.equalsIgnoreCase("STOPPED STATE")) {
                                    Log.e("STATE1","ST0PPED");
                                    nozzleRelayStop();

                                    isAlreadyPopForAuthorize = false;
                                    suspendEvent = 0;

                                    ((TextView) findViewById(R.id.btnStart)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
                                    ((TextView) findViewById(R.id.btnStart)).setEnabled(true);
                                    ((TextView) findViewById(R.id.btnResume)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnResume)).setEnabled(false);
                                    ((TextView) findViewById(R.id.btnSuspend)).setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                                    ((TextView) findViewById(R.id.btnSuspend)).setEnabled(false);
//                                    readVol();
                                    if (!isLockObtanedForNewTransaction) {
                                        //To DO
                                        //send Command RL10->IKNOWATIONS:#*RL1O
                                        getTotalizer();

                                    }
                                    isPayableState = true;
                                    findViewById(R.id.orderCompleted).setVisibility(View.VISIBLE);

                                } else if (status.equalsIgnoreCase("AUTHORIZE STATE")) {
                                    Log.e("STATE1","AUTH");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "To set IDLE State", Toast.LENGTH_SHORT).show();
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

    /**
     * THIS METHOD IS USED TO LOG ALL TRANSACTION IN TEXT FILE WITH FOLLOWING PARAMETERS.
     *
//     * @param transactionNum
//     * @param lastDispenserValue
//     * @param currentDispenserValue
//     * @param CurrentUserCharges
//     * @param dateTime
//     */

//    void startLogTransaction(final String transactionNum,
//                             final Double lastDispenserValue,
//                             final String currentDispenserValue,
//                             final String CurrentUserCharges,
//                             final String dateTime) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LogToFile.setFuelingLog(
//                        String.valueOf(lastDispenserValue)
//                        , currentDispenserValue
//                        , CurrentUserCharges
//                        , dateTime
//                        , String.valueOf(dispenserAssetId.getText())
//                        , fuelRate.getText().toString()
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


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


//    private void setOfflineDispenseLayout(boolean setOfflineDispenseLayout) {
//        if (setOfflineDispenseLayout) {
//            findViewById(R.id.fuelDispensedOffline).setVisibility(View.VISIBLE);
//        } else {
//            findViewById(R.id.fuelDispensedOffline).setVisibility(View.GONE);
//        }
//    }
//
//    public void setDispenseActionLayout(boolean setDispenseActionLayout) {
//        if (setDispenseActionLayout) {
//            findViewById(R.id.dispenseActions).setVisibility(View.VISIBLE);
//        } else {
//            findViewById(R.id.dispenseActions).setVisibility(View.GONE);
//        }
//    }

    public void startDispenseUnlockPayLock() {
        isPayableStateLock = false;
        findViewById(R.id.btnStart).performClick();
        findViewById(R.id.btnStart).performClick();
    }

    private void sopDB_Values() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String lastId = SharedPref.getLastTransactionId();
                TransactionDbModel lastModel = db.transactionDbDao().getTransactionById(lastId);
                if (lastModel != null) {
                    try {
                        Log.d("dbValues", db.transactionDbDao().getTransactionById(SharedPref.getLastTransactionId()).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            CommandQueue.TerminateCommandChain();
            CommandStatusQueue.TerminateCommandChain();
            CommandReadTxnQueue.TerminateCommandChain();

            if (executor != null && !executor.isTerminated()) {
                executor.shutdown();
            }
            StopServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            CommandQueue.TerminateCommandChain();
            CommandStatusQueue.TerminateCommandChain();
            CommandReadTxnQueue.TerminateCommandChain();

            if (executor != null && !executor.isTerminated()) {
                executor.shutdown();
            }
            StopServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ClearSale() {
        ProgressDialog progressDialog = new ProgressDialog(FreshDispenserUnitActivity.this);
        progressDialog.setMessage("Please wait for transaction completion..");
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

    public void send285Command(String command) {
        if (Server285.getSocket() != null) {
            Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("writing232", command + "");
                        }
                    });
                }
            });


        }
    }

    public void send285Command(String command, int delay) {

        if (Server285.getSocket() != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
                        @Override
                        public void onCompleted(Exception ex) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("writing232Delay", command + " delay=" + delay);
                                }
                            });
                        }
                    });
                }
            }, delay);


        }
    }

    public void ListenATG() {
        send285Command(Commands.LISTEN_ATG_1_285.toString());

    }

    public void ReadATG() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        send285Command("M23872");

                    }
                });
                Log.d("readingATG", intialATGReading + " end reading=" + endReadingIs);
            }
        }, 2500);
    }


    public void afterStopPressed() {
        try {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
////            builder.setMessage("Pumped Stopped")
////                    .setCancelable(false)
////                    .setPositiveButton("Fill Other Asset", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////                            try {
//////                                showAssetDialog();
////                                sendToServerStopPump();
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            } finally {
////                                finish();
////                            }
////                        }
////                    })
////                    .setNegativeButton("Complete Transaction", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
//////                            showAssetDialog();
////                            sendToServerStopPump();
////
////                        }
////                    });
////            AlertDialog alert = builder.create();
////            alert.show();
            sendToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLocationReading() {
        Toast.makeText(context, "Hello Asset", Toast.LENGTH_SHORT).show();
    }


//    if(LocationUtil.distanceInKm(28.657060, 77.325950, 31.361180, 70.997840))

    public void nozzleRelayStart() {
        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getMakeOfRelayBoard().equalsIgnoreCase("IKNOWVATIONS")) {
            send285Command("RL10");
            send285Command("RL11");
            Log.d("RL11-----", "RL11");
        } else {
            send285Command("#*RL10");
            Log.d("RL10----->", "#*RL10");
            send285Command("#*RL11");
            Log.d("RL11----->", "#*RL11");
            send285Command("#*BZ11");
            send285Command("#*BZ10");

//            send285Command("RL10");
//            Log.d("RL10----->", "#*RL10");
//            send285Command("RL11");
//            Log.d("RL11----->", "#*RL11");
//            send285Command("#*BZ11");
//            send285Command("#*BZ10");
        }

//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                Toast.makeText(context, "Fueling starts in" + millisUntilFinished / 1000 + " seconds", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onFinish() {
//
////                final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
////                new CommandQueue(authorized, new OnAllCommandCompleted() {
////                    @Override
////                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
////                        curentTotalizer = 0f;
////                        fuelDispensed.setText("" + curentTotalizer);
////                        Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
////                    }
////
////                    @Override
////                    public void onAllCommandCompleted(int currentCommand, String response) {
////                        try {
////
////                        } catch (Exception e) {
////
////                        }
////                    }
////                }).doCommandChaining();
//            }
//        }.start();
    }

    public void nozzleRelayStop() {
        if (SharedPref.getLoginResponse().getVehicle_data().get(0).getMakeOfRelayBoard().equalsIgnoreCase("IKNOWVATIONS")) {
            send285Command("RL10");
        } else {
            send285Command("#*RL10");
        }
    }

    public void testRate() {
        send285Command("");
    }

    public void locationAndGPSCheck() {
//        if(SharedPref.getLoginResponse().getVehicle_data().get(0))
    }

    public void sendToServer() {
        Bundle bundle = new Bundle();
        bundle.putString("FuelDispensed", String.valueOf(fuelDispensed.getText()));
        bundle.putString("CurrentUserCharge", String.valueOf(currentUserCharge.getText()));
        bundle.putString("FuelRate", String.valueOf(dispenserFuelRate.getText()));
        bundle.putString("startTime", startedTime);
        bundle.putString("selectedAsset", selectedAssetId);
        bundle.putString("atgStart", intialATGReading);
        bundle.putString("orderDate", order.getCreatedDatatime());
        bundle.putParcelable("orderDetail", orderDetailed);


//        AddReadingsDialog addReadingsDialog = new AddReadingsDialog();
//        addReadingsDialog.setCancelable(false);
//        addReadingsDialog.setArguments(bundle);
//        addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());
    }

    public void showDistance() {
//        Log.e("KM", String.valueOf(LocationUtil.distanceInKm(28.609120, 77.390600, 28.6090737, 77.390561)));
//        Log.e("KM", String.valueOf(LocationUtil.distanceInKm(28.657060, 77.325950, 31.361180, 70.997840)));
//        Log.e("Meters", String.valueOf(LocationUtil.distanceInMeters(28.657060, 77.325950, 31.361180, 70.997840)));

        tvGPSRange.setText(String.valueOf(LocationUtil.distanceInMeters(28.657060, 77.325950, 31.361180, 70.997840)));

    }

    @Override
    protected void onStart() {
        super.onStart();

//        Toast.makeText(context, String.valueOf(LocationUtil.distanceInMeters(28.657060, 77.325950, 31.361180, 70.997840)), Toast.LENGTH_SHORT).show();

    }

    public void sureNoShow(String mismatch, String range) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("The GPS is out of range");
        builder.setMessage("Current Mismatch is : " + mismatch + " metres" + "\n Allowable range is: " + range
                + " metres" + "/n Current GPS status  ");

        builder.setPositiveButton("By Pass GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
//
//        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    public void toSetIdle() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Kindly reset power to continue ..")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

//        final String[] dc = new String[]{Commands.PUMP_STOP.toString()
//                , Commands.PUMP_START.toString()
//        };
//        new CommandQueue(dc, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                Log.d("pumpIdle", lastResponse + "");
//                PollStatus.getPollState(lastResponse);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            progressDialog.dismiss();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                afterStopPressed();
//                Toast.makeText(context, "Pump Idle Successfully", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onAllCommandCompleted(int currentCommand, String response) {
//                try {
//                    progressDialog.setMessage(Commands.getEnumByString(dc[currentCommand]));
//                } catch (Exception e) {
//                    progressDialog.dismiss();
//                }
//            }
//        }).doCommandChaining();
    }
}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.CommonUtils;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnTxnQueueCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandReadTxnQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Server285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.LogToFile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Progress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue.convertToAscii;

public class ReadOnlyDispenserUnitActivity extends AppCompatActivity implements RouterResponseListener {

    private Context context;
    private TextView dispenseNow;
    private TextView connection_maintainedState, rF_IdTxt;
    private ProgressDialog progressDialog;
    private EditText setPresetEdt;
    private TextView pumpStatusTxt;
    private double intialTotalizer = 0d;
    private double curentTotalizer = 0d;
    private String status = "";
    private TextView fuelDispensed;
    private TextView fuelRate;
    private TextView currentUserCharge;

    private String uniqueTransactionNumber;
    private TextView intialTotalizerTxt;


    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private boolean isPayableStateLock = false;
    private boolean isPayableState = false;


    private TextView dispenserAssetId, dispenserFuelRate, dispenserLocationInfo, dispenserPresetAmount, dispenserOrderQnty;
    private ScheduledExecutorService executor;
    private Order order;
    private Runnable getStatusRunnable;
    public static String transactionId;
    private TextView dispensingIn;
    private boolean isAlreadyPopForJerryCan = false;
    private boolean isAlreadyPopForAuthorize = false;
    private View dispenserSelectAssetId;
    private ArrayList<Asset> asset;
    private ProgressDialog payableProgress;
    private ProgressDialog rfNotCloseProgress;
    private Progress fuelDetails;
    private int suspendEvent = 0;
    private View ATGTest;
    private boolean isATGtest = false;
    private AlertDialog alert;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fragment_operations);
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
        rF_IdTxt = ((TextView) findViewById(R.id.rfId));
        dispensingIn = ((TextView) findViewById(R.id.dispensingIn));

        connection_maintainedState = findViewById(R.id.pumpStatus);

        fuelDispensed = findViewById(R.id.currentFuelDispensedQnty);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Executing Command... ");


        connectToSynergyWifi();

        //----------------------------------------------------------------------------------------------------------------------------------
        // TODO: 2/22/2019 Start Executor for every Two Seconds to check Current Dispenser State

        getStatusRunnable = new Runnable() {
            public void run() {
                send285Command("M23871\r\n");
                send485Command(Commands.READ_VOL_TOTALIZER.toString());
            }
        };

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(getStatusRunnable, 0, 1, TimeUnit.SECONDS);



    }
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
                    new Server285("192.168.1.103", 4306, (RouterResponseListener) context);
                    new Server485("192.168.1.103", 4307, (RouterResponseListener) context);
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
            Server285.getAsyncServer().stop();
            Server485.getAsyncServer().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

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

    }

    @Override
    public void OnRouter285Aborted() {
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
    public void OnRfIdReceived(String response232) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response232 != null && response232.contains(".")) {
                    Log.d("232FoundATG", response232);
                    LogToFile.LogATGReadingToFile(String.valueOf(Calendar.getInstance().getTime()) + " ATG VALUE " + response232 + "\n");
                    if (isATGtest) {
                        Toast.makeText(context, "ATG Working", Toast.LENGTH_SHORT).show();
                        isATGtest = false;
                    }
                } else {
                    Log.d("232FoundRfId", response232);
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


    @Override
    public void OnRouter485QueueConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


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


    }

    @Override
    public void OnRouter485TxnConnected() {


    }


    void setLogToFile(final String logMsg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogToFile.LogMessageToFile(logMsg);
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            CommandQueue.TerminateCommandChain();

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

            if (executor != null && !executor.isTerminated()) {
                executor.shutdown();
            }
            StopServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void send285Command(String command) {
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

    private void send485Command(String command) {
        if (Server485.getSocket() != null) {
            Util.writeAll(Server485.getSocket(), convertToAscii(command).getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("writing485", command + "");
                        }
                    });
                }
            });


        }
    }
}

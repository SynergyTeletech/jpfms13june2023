//package com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.BrancoApp;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.R;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.interfaces.OnAllCommandCompleted;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.interfaces.OnStatusQueueListener;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.interfaces.OnTxnQueueCompleted;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.interfaces.RouterResponseListener;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.log_db.AppRoomDatabase;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.database.room.tables.TransactionDbModel;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.server485pack.CommandQueue;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.server485pack.CommandReadTxnQueue;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.server485pack.CommandStatusQueue;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.server485pack.Server485;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.server485pack.Server485_ReadTxn;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.server485pack.Server485_status;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.synergy.AddReadingsDialog;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.synergy.Commands;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.synergy.DispenseNowClick;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.synergy.Server285;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.utils.LogToFile;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.utils.PdfCreator;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.utils.PollStatus;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.utils.SharedPref;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Locale;
//import java.util.UUID;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static com.syneygy.fillnow.dutymanager.fillnowdutymanager.hardware.server485pack.CommandQueue.convertToAscii;
//
//public class SynergyDashboard extends AppCompatActivity implements RouterResponseListener, View.OnClickListener {
//
//    private Context context;
//    private TextView dispenseNow;
//    private TextView connection_maintainedState, rF_IdTxt;
//    private ProgressDialog progressDialog;
//    private EditText setPresetEdt;
//    private TextView pumpStatusTxt;
//    private double intialTotalizer = 0d;
//    private double curentTotalizer = 0d;
//    private String status = "";
//    private TextView fuelDispensed;
//    private TextView fuelRate;
//    private TextView currentUserCharge;
//
//    private UUID uniqueTransactionNumber;
//    private TextView intialTotalizerTxt;
//
//
//    int PERMISSION_ALL = 1;
//    String[] PERMISSIONS = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//    };
//
//    private boolean isPayableStateLock = false;
//    private boolean isPayableState = false;
//    private AppRoomDatabase db;
//
//    private boolean isLockObtanedForNewTransaction;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = this;
//        setContentView(R.layout.fill_now_tab);
//        findViewById(R.id.dispenseNowBtn).setOnClickListener(this);
//        findViewById(R.id.settings).setOnClickListener(this);
//        db = BrancoApp.getDb();
//
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        pumpStatusTxt = ((TextView) findViewById(R.id.pumpStatus));
//        rF_IdTxt = ((TextView) findViewById(R.id.rfId));
//
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Executing Command... ");
//
//
//        connection_maintainedState = findViewById(R.id.connectionStatus);
//        connection_maintainedState.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
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
//        setPresetEdt = findViewById(R.id.setPresetEdt);
//        fuelRate = findViewById(R.id.fuelRate);
//        currentUserCharge = findViewById(R.id.currentUserCharge);
//        intialTotalizerTxt = findViewById(R.id.intialTotalizer);
//
//        findViewById(R.id.pumpOFF).setOnClickListener(this);
//        findViewById(R.id.btn_StartDispense).setOnClickListener(this);
//        findViewById(R.id.setPresetBtn).setOnClickListener(this);
//        findViewById(R.id.pumpStatusRefresh).setOnClickListener(this);
//        findViewById(R.id.resetNetwork).setOnClickListener(this);
//        fuelDispensed = findViewById(R.id.fuelDispensed);
//
//        findViewById(R.id.btnSuspendSale).setOnClickListener(this);
//        findViewById(R.id.btn_ResumeSale).setOnClickListener(this);
//        findViewById(R.id.pdfConvert).setOnClickListener(this);
//        findViewById(R.id.orderCompleted).setOnClickListener(this);
//
//        fuelRate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setRate("15.00");
//            }
//        });
//
//        intialTotalizerTxt.setText(SharedPref.getLastFuelDispenserReading(context));
//        try {
//            intialTotalizer = (SharedPref.getLastFuelDispenserReading(context).isEmpty()) ? 0.0f : Double.parseDouble(SharedPref.getLastFuelDispenserReading(context));
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        } finally {
//            uniqueTransactionNumber = (SharedPref.getLastTransactionId(context).isEmpty()) ? UUID.randomUUID() : UUID.fromString(SharedPref.getLastTransactionId(context));
//        }
//        connectToSynergyWifi();
//
//        fuelDispensed.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() != 0)
//                    try {
//                        double currentPrice = Double.parseDouble(s.toString()) * Double.parseDouble(fuelRate.getText().toString());
//                        if (currentPrice >= 0) {
//                            currentUserCharge.setText(String.format(Locale.US, "%.2f", currentPrice));
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
//        Runnable getStatusRunnable = new Runnable() {
//            public void run() {
//                if (Server485_status.getSocket() != null) {
//                    getStatusPoll();
//                }
//            }
//        };
//
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        executor.scheduleAtFixedRate(getStatusRunnable, 0, 1, TimeUnit.SECONDS);
//
//
//    }
//
//
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//    // TODO: 2/22/2019 Connect to Synergy Wifi
//
//    private void connectToSynergyWifi() {
//        try {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    new Server285("192.168.1.103", 4306, (RouterResponseListener) context);
//                    new Server485("192.168.1.103", 4307, (RouterResponseListener) context);
//                    new Server485_status("192.168.1.103", 4308, (RouterResponseListener) context);
//                    new Server485_ReadTxn("192.168.1.103", 4309, (RouterResponseListener) context);
//                    setMessage("Connecting...");
//
//                }
//            }).start();
//        } catch (Exception e) {
//            Toast.makeText(context, "Something Went Wrong With Server", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//
//    @Override
//    public void onBackPressed() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you sure you want to exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        try {
//                            Server485.CloseSocket();
//                            Server485_status.CloseSocket();
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
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//    // TODO: 2/22/2019 Implement Click Listener Logics
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.pumpOFF:
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
//
//                break;
//
//            case R.id.btn_StartDispense:
////                if (isPayableState && isPayableStateLock) {
////                    Toast.makeText(context, "Please Follow Order Completion Steps First", Toast.LENGTH_SHORT).show();
////                    return;
////                }
////                isPayableStateLock = true;
//                curentTotalizer = 0f;
//                isLockObtanedForNewTransaction = true;
//                fuelDispensed.setText("" + curentTotalizer);
//
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String lastId = SharedPref.getLastTransactionId(context);
//                        TransactionDbModel lastModel = db.transactionDbDao().getTransactionById(lastId);
//                        if (lastModel != null) {
//                            lastModel.setFueling_Stop_Time(String.valueOf(Calendar.getInstance().getTime()));
//                            db.transactionDbDao().update(lastModel);
//                            System.out.print("DB Updated" + uniqueTransactionNumber);
//                            try {
//                                Log.d("dbValues", db.transactionDbDao().getTransactionById(SharedPref.getLastTransactionId(context)).toString());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
//
//
//                uniqueTransactionNumber = UUID.randomUUID();
//
//
////----------------------------------------------------------------------------------------------------------------------------------
//
//                // TODO: 2/18/2019 Start Logging New Transaction
//                setLogToFile("" +
//                        "\n\n\n\n\n"
//                        + "----------------------New Transaction Requested -------------------" +
//                        "\n\n\n\n\n" +
//                        "\nTxn Id= " + String.valueOf(uniqueTransactionNumber)
//                        + "\n"
//                        + " at " + String.valueOf(Calendar.getInstance().getTime())
//                        + "------------------------");
//
//                Handler handler1 = new Handler();
//                handler1.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                db.transactionDbDao().insertAll(new TransactionDbModel(String.valueOf(uniqueTransactionNumber), String.valueOf(Calendar.getInstance().getTime())));
//
//                                System.out.print("DB Insertion " + uniqueTransactionNumber);
//                                SharedPref.setLastTransactionId(context, String.valueOf(uniqueTransactionNumber));
//                                sopDB_Values();
//                            }
//                        }).start();
//                    }
//                }, 2000);
//
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
//                                new DispenseNowClick(context).getPollState(stringToHex(lastResponse));
//                                ((TextView) findViewById(R.id.pumpStatus)).setText(status);
//                                isLockObtanedForNewTransaction = false;
//                            }
//
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
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }).doCommandChaining();
//
//                break;
//            case R.id.setPresetBtn:
//
//                if (setPresetEdt.getText().length() < 0) {
//                    Toast.makeText(context, "Please Enter Valid Value For Preset", Toast.LENGTH_SHORT).show();
//
//                } else if (Double.parseDouble(setPresetEdt.getText().toString()) < 1) {
//                    Toast.makeText(context, "Please Enter Value For Preset more than 1.0", Toast.LENGTH_SHORT).show();
//                } else {
//                    setProgressBarMessage("Setting Preset...");
//                    setPreset(setPresetEdt.getText().toString());
//                }
//                break;
//            case R.id.pumpStatusRefresh:
//                refreshPumpState();
//                break;
//            case R.id.resetNetwork:
//                Server485.CloseSocket();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        connectToSynergyWifi();
//                    }
//                }, 2000);
//
//                break;
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
//
//                PdfCreator.createPDF();
//                break;
//            case R.id.dispenseNowBtn:
//
//                break;
//            case R.id.settings:
//                startActivity(new Intent(SynergyDashboard.this, SettingsActivity.class));
//                break;
//            case R.id.orderCompleted:
//                Bundle bundle = new Bundle();
//                bundle.putString("FuelDispensed", String.valueOf(fuelDispensed.getText()));
//                bundle.putString("CurrentUserCharge", String.valueOf(currentUserCharge.getText()));
//                bundle.putString("FuelRate", String.valueOf(fuelRate.getText()));
//                AddReadingsDialog addReadingsDialog = new AddReadingsDialog();
//                addReadingsDialog.setCancelable(false);
//                addReadingsDialog.setArguments(bundle);
//                addReadingsDialog.show(getSupportFragmentManager(), AddReadingsDialog.class.getSimpleName());
//                break;
//        }
//
//    }
//
//    private TransactionDbModel getLastIdModel() {
//        String lastId = SharedPref.getLastTransactionId(context);
//        TransactionDbModel lastModel = db.transactionDbDao().getTransactionById(lastId);
//        return lastModel;
//    }
//
//
//    //----------------------------------------------------------------------------------------------------------------------------------
//
//
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
//                        SharedPref.setLastFuelDispenserReading(context, intialTotalizerTxt.getText().toString());
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
//    private void authorizeFueling() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(SynergyDashboard.this);
//        builder.setMessage("Are you sure to continue?")
//                .setCancelable(false)
//                .setPositiveButton("Start Fueling", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        final String[] authorized = new String[]{Commands.AUTHORIZATION.toString()};
//                        new CommandQueue(authorized, new OnAllCommandCompleted() {
//                            @Override
//                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                Toast.makeText(BrancoApp.getContext(), "Authorized Fueling", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onAllCommandCompleted(int currentCommand, String response) {
//                                try {
//
//                                } catch (Exception e) {
//
//                                }
//                            }
//                        }).doCommandChaining();
//                    }
//                })
//                .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
//                        new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
//                            @Override
//                            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onAllCommandCompleted(int currentCommand, String response) {
//                                try {
//
//                                } catch (Exception e) {
//
//                                }
//                            }
//                        }).doCommandChaining();
//
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//
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
//    private void refreshPumpState() {
//        final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
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
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        try {
//            CommandQueue.TerminateCommandChain();
//            CommandStatusQueue.TerminateCommandChain();
//            CommandReadTxnQueue.TerminateCommandChain();
//
//            startLogTransaction(String.valueOf(uniqueTransactionNumber), intialTotalizer, fuelDispensed.getText().toString(), currentUserCharge.getText().toString(), String.valueOf(Calendar.getInstance().getTime()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
//    public void setMessage(final String msg) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                connection_maintainedState.setText(msg);
//            }
//        });
//    }
//
//
//    @Override
//    public void OnRouter485QueueAborted() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                connection_maintainedState.setText("Connection Aborted");
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter285Connected() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, "Connected to 285", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public void OnRouter285Aborted() {
//        Toast.makeText(context, "Disconnected from 285", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void OnRouter485StatusAborted() {
//
//    }
//
//    @Override
//    public void OnRouter485TxnAborted() {
//
//    }
//
//    @Override
//    public void OnRfIdReceived(final String rfId) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                rF_IdTxt.setText(String.format(Locale.US, "RF Id: %s", rfId));
//            }
//        });
//    }
//
//
//    @Override
//    public void OnRouter485QueueConnected() {
//        getStatusPoll();
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
//                connection_maintainedState.setText(getResources().getString(R.string.connection_maintained));
//
//            }
//        });
//    }
//
//    private void getTotalizer() {
//
//        final String[] readTxn = new String[]{Commands.READ_TXN.toString()};
//
//        CommandReadTxnQueue.getInstance(readTxn, new OnTxnQueueCompleted() {
//            @Override
//            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {
//
//            }
//
//
//            @Override
//            public void OnTxnQueueCommandCompleted(final int currentCommand, final String response) {
//                try {
//                    if (currentCommand == 0) {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (response != null && response.contains(".")) {
//                                    try {
//                                        final String txnFuelRate = response.substring(4, response.indexOf(".") + 3);
//
//                                        final String txnDispense = response
//                                                .substring(response.indexOf(".") + 3, CommonUtils.findNextOccurance(response, ".", 2) + 3);
//
//                                        final String txnCharges = response
//                                                .substring(CommonUtils.findNextOccurance(response, ".", 2) + 3, CommonUtils.findNextOccurance(response, ".", 3) + 3);
//
//                                        Log.i("fuelDispenseNewTxn", response + " amount = " + txnFuelRate + "dispense=" + txnDispense + " charges= " + txnCharges);
//
//                                        Handler handler = new Handler(Looper.getMainLooper());
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                try {
//                                                    if (curentTotalizer < 0) {
//                                                        return;
//                                                    }
//
//                                                    fuelDispensed.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnDispense)));
//                                                    currentUserCharge.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnCharges)));
//                                                    fuelRate.setText(String.format(Locale.US, "%.2f", Double.parseDouble(txnFuelRate)));
//                                                } catch (Exception e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }, 200);
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//
//                        }).start();
//                    }
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }).
//
//                doCommandChaining();
//
//    }
//
//    @Override
//    public void OnRouter485StatusConnected() {
//
//    }
//
//    @Override
//    public void OnRouter485TxnConnected() {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(context, "Checking Last Readings" +
//                        "", Toast.LENGTH_SHORT).show();
//                getTotalizer();
//            }
//        });
//    }
//
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
//    public void setProgressBarMessage(final String message) {
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
//
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
//
//                    Toast.makeText(BrancoApp.getContext(), "Preset Set ", Toast.LENGTH_SHORT).show();
//                    dismissProgressBar();
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
//                                        public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//                                            Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                        @Override
//                                        public void onAllCommandCompleted(int currentCommand, String response) {
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
//
//                }
//
//                @Override
//                public void onAllCommandCompleted(int currentCommand, String response) {
//                    try {
//                        if (currentCommand == 0) {
//                            //todo Read Totalizer Value
//                            setIntialTotalizer(response);
//                        }
//                    } catch (Exception e) {
//
//                    }
//                }
//            });
//            data.doCommandChaining();
//        }
//
//    }
//
//    public void setRate(String pumpPrice) {
//        if (Server485.getSocket() != null) {
//            String b = String.format(Locale.US, "%07d", (int) (Double.parseDouble(pumpPrice) * 100));
//            String command = "014142" + stringToHexWithoutSpace(b) + "7F";
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
//
//            final String[] d = new String[]{Commands.READ_VOL_TOTALIZER.toString(), checkSumCommand, Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString()};
//            CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
//                @Override
//                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//
//                    Toast.makeText(BrancoApp.getContext(), "New Fuel Rate Set ", Toast.LENGTH_SHORT).show();
//                    Log.i("rate", lastResponse + "");
//                    dismissProgressBar();
////                    final AlertDialog.Builder builder = new AlertDialog.Builder(SynergyDashboard.this);
////                    builder.setMessage("Please Take Nozzle to fueling position,Press Start When Ready")
////                            .setCancelable(false)
////                            .setPositiveButton("Start ", new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int id) {
////                                    authorizeFueling();
////                                }
////                            })
////                            .setNegativeButton("Cancel Preset", new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int id) {
////                                    final String[] presetCompletedStateCommands = new String[]{Commands.CLEAR_PRESET.toString()};
////                                    new CommandQueue(presetCompletedStateCommands, new OnAllCommandCompleted() {
////                                        @Override
////                                        public void commandsQueueEmpty(boolean isEmpty, String lastResponse) {
////                                            Toast.makeText(BrancoApp.getContext(), "Preset Cleared", Toast.LENGTH_SHORT).show();
////                                        }
////
////                                        @Override
////                                        public void onCommandCompleted(int currentCommand, String response) {
////                                            try {
////                                            } catch (Exception e) {
////                                            }
////                                        }
////                                    }).doCommandChaining();
////
////                                    dialog.cancel();
////                                }
////                            });
////                    AlertDialog alert = builder.create();
////                    alert.show();
//
//                }
//
//                @Override
//                public void onAllCommandCompleted(int currentCommand, String response) {
//                    try {
//                        if (currentCommand == 0) {
//                            //todo Read Totalizer Value
//                            Log.i("rate", response + "");
//                        }
//                    } catch (Exception e) {
//
//                    }
//                }
//            });
//            data.doCommandChaining();
//        }
//
//    }
//
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
//    public void readVol() {
//        final String[] readVol = new String[]{Commands.READ_VOL_TOTALIZER.toString()};
//
//        CommandReadTxnQueue.getInstance(readVol, new OnTxnQueueCompleted() {
//            @Override
//            public void onTxnQueueEmpty(boolean isEmpty, final String lastResponse) {
//
//            }
//
//
//            @Override
//            public void OnTxnQueueCommandCompleted(final int currentCommand, final String response) {
//                try {
//                    if (currentCommand == 0) {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (response != null && response.contains(".")) {
//                                    Pattern pattern = Pattern.compile("0000");
//                                    Matcher matcher = pattern.matcher(response);
//                                    String data = response.replaceAll("[^0-9]", "");
//                                    try {
//                                        if (matcher.end() != -1) {
//                                            Log.i("fuelDispense", response + " matcher= " + data.substring(matcher.end()));
//                                        }
//                                    } catch (IllegalStateException e) {
//                                        e.printStackTrace();
//                                    } finally {
//                                        Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+1?");
//                                        List<String> numbers = new ArrayList<String>();
//                                        Matcher m = p.matcher(response);
//                                        while (m.find()) {
//                                            numbers.add(m.group());
//                                        }
//                                        try {
//                                            curentTotalizer = (Double.parseDouble(numbers.get(0).substring(1).replaceFirst("^0+(?!$)", ""))) - intialTotalizer;
//                                            Log.i("fuelDispenseNew", response + " intial= " + intialTotalizer + "current=" + (Double.parseDouble(numbers.get(0).substring(1).replaceFirst("^0+(?!$)", "")) - intialTotalizer));
//                                            Handler handler = new Handler(Looper.getMainLooper());
//                                            handler.postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    if (curentTotalizer < 0) {
//                                                        return;
//                                                    }
//                                                    fuelDispensed.setText(String.format(Locale.US, "%.2f", curentTotalizer));
//                                                }
//                                            }, 200);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }
//
//                            }
//                        }).start();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }).doCommandChaining();
//    }
//
//
//    void suspendSale() {
//        final String[] refresh = new String[]{Commands.SUSPEND_SALE.toString()};
//        progressDialog.show();
//
//        new CommandQueue(refresh, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
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
//
//                        } catch (Exception e) {
//                            Log.d("statusReceivedExcep", e.getLocalizedMessage() + " " + response + currentCommand);
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
//    void resumeSale() {
//        final String[] refresh = new String[]{Commands.RESUME_SALE.toString()};
//        progressDialog.show();
//
//        new CommandQueue(refresh, new OnAllCommandCompleted() {
//            @Override
//            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
//
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
//                            Log.d("statusReceivedExcep", e.getLocalizedMessage() + " " + response + currentCommand);
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
//
//    void getStatusPoll() {
//        final String[] refresh = new String[]{Commands.STATUS_POLL.toString()};
//        CommandStatusQueue commandStatus = CommandStatusQueue.getInstance(refresh, new OnStatusQueueListener() {
//            @Override
//            public void onStatusQueueQueueEmpty(boolean isEmpty, String lastResponse) {
//
//            }
//
//            @Override
//            public void OnStatusQueueCommandCompleted(final int currentCommand, final String response) {
//
//                if (response != null) {
//                    if (response.length() <= 14) {
//                        status = PollStatus.getPollState(response);
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!status.isEmpty()) {
//                                if (status.contains("STATE IDLE") || status.equalsIgnoreCase("CALL STATE")) {
//                                    findViewById(R.id.setPresetLay).setVisibility(View.VISIBLE);
//                                } else {
//                                    findViewById(R.id.setPresetLay).setVisibility(View.GONE);
//                                }
//                                if (status.equalsIgnoreCase("FUELING STATE")) {
////                                    readVol();
//                                    if (!isLockObtanedForNewTransaction) {
//                                        getTotalizer();
//                                    }
//                                    findViewById(R.id.pdfConvert).setVisibility(View.GONE);
//                                    startLogTransaction(String.valueOf(uniqueTransactionNumber), intialTotalizer, fuelDispensed.getText().toString(), currentUserCharge.getText().toString(), String.valueOf(Calendar.getInstance().getTime()));
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setEnabled(true);
//
//                                } else if (status.equalsIgnoreCase("PAYABLE STATE")) {
////                                    readVol();
//                                    if (!isLockObtanedForNewTransaction) {
//                                        getTotalizer();
//                                    }
//                                    isPayableState = true;
//                                    findViewById(R.id.orderCompleted).setVisibility(View.VISIBLE);
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setEnabled(false);
//                                } else {
//                                    isPayableState = false;
//                                    CommandReadTxnQueue.TerminateCommandChain();
//                                    findViewById(R.id.orderCompleted).setVisibility(View.GONE);
//                                    findViewById(R.id.pdfConvert).setVisibility(View.GONE);
//
//                                }
//                                if (status.equalsIgnoreCase("SUSPENDED STATE") || status.equalsIgnoreCase("SUSPEND STARTED STATE")) {
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setEnabled(true);
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setEnabled(false);
//                                } else if (status.equalsIgnoreCase("STARTED STATE")) {
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.md_blue_500));
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setEnabled(true);
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setEnabled(false);
//                                } else if (status.equalsIgnoreCase("STOPPED STATE")) {
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setEnabled(false);
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
//                                    ((TextView) findViewById(R.id.btnSuspendSale)).setEnabled(false);
////                                    readVol();
//                                    if (!isLockObtanedForNewTransaction) {
//                                        getTotalizer();
//                                    }
//                                    isPayableState = true;
//                                    findViewById(R.id.orderCompleted).setVisibility(View.VISIBLE);
//                                } else {
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
//                                    ((TextView) findViewById(R.id.btn_ResumeSale)).setEnabled(false);
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
//    void startLogTransaction(final String transactionNum, final Double lastDispenserValue,
//                             final String currentDispenserValue, final String CurrentUserCharges,
//                             final String dateTime) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                LogToFile.setFuelingLog(String.valueOf(lastDispenserValue), currentDispenserValue, CurrentUserCharges, dateTime, String.valueOf(transactionNum), fuelRate.getText().toString());
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
//
//    public static boolean hasPermissions(Context context, String... permissions) {
//        if (context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//
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
//
//    public void startDispenseUnlockPayLock() {
//        isPayableStateLock = false;
//        findViewById(R.id.btn_StartDispense).performClick();
//        findViewById(R.id.btn_StartDispense).performClick();
//    }
//
//    private void sopDB_Values() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String lastId = SharedPref.getLastTransactionId(context);
//                TransactionDbModel lastModel = db.transactionDbDao().getTransactionById(lastId);
//                if (lastModel != null) {
//                    try {
//                        Log.d("dbValues", db.transactionDbDao().getTransactionById(SharedPref.getLastTransactionId(context)).toString());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//}

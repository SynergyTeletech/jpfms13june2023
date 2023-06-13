package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.CammondQueueAtg;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.ServerATG285;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.NetworkChangeReceiver;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.xuhao.didi.socket.client.sdk.OkSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;


/**
 * Created by Ved Yadav on 1/31/2019.
 */
public class BrancoApp extends Application implements RouterResponseListener
{
    private static Context context;
    private static AppDatabase db;
    int test = 0;
    int test2 = 0;
    int atgcount = 0;
    private int atgCount = 0;
    int atgcount2 = 0;
    OkSocket okSocket=new OkSocket();
    private MutableLiveData<String> atgData;
    NetworkChangeReceiver networkChangeReceiver;
    private static DispenseLocalDatabaseHandler dataBaseHelper;
    private static RouterResponseListener routerResponseListener;
    private String atgSerialNoTank1 = "", atgSerialNoTank2 = "";
    private LinkedHashMap<String, JSONObject> atgLinkedHashMapTank1, atgLinkedHashMapTank2;

    private double tank1MaxVolume = 0f, tank2MaxVolume = 0f;
    private boolean isATGPort3Selected, isATGPort4Selected;

    public static AppDatabase getDb() {
        return db;
    }

    public MutableLiveData<String> getAtgDataObserver() {
        if (atgData == null) {
            atgData = new MutableLiveData<String>();
        }
        return atgData;
    }

    public static RouterResponseListener getRouter() {

        return routerResponseListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
       Log.e("TEST_LOCAL","NORMAL"+SharedPref.getTestLocal());
       Log.e("TEST_LOCAL","CRASH"+SharedPref.getTestLocalCrash());

        routerResponseListener = (RouterResponseListener) context;
        connectToSynergyWifi();
        networkChangeReceiver = new NetworkChangeReceiver();
        db = Room.databaseBuilder(this, AppDatabase.class, "synergy_db").build();
        try {
            dataBaseHelper = new DispenseLocalDatabaseHandler(this);
        }
        catch (Exception e){

        }
        try {
//            ACRA.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }

    public static Context getContext() {
        return context;
    }

    public static DispenseLocalDatabaseHandler getLocalDbContext() {
        return dataBaseHelper;
    }

    public void connectToSynergyWifi() {
        try {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

//
//                    new Client285( BrancoApp.this,"122.176.117.78", 501).sendMessage();

////
//////                    if (Server285_ReadRFID.getAsyncServer() == null || !Server285_ReadRFID.getAsyncServer().isRunning())
//////                        new Server285_ReadRFID("122.176.117.78", 54311, BrancoApp.this);
//                    if (Server485.getAsyncServer() == null || !Server485.getAsyncServer().isRunning())

//                    if (Server485_status.getAsyncStausServer() == null || !Server485_status.getAsyncStausServer().isRunning())
//                        new Server485_status("122.176.117.78", 54308, BrancoApp.this);
//                    if (Server485_ReadTxn.getAsyncReadTxnServer() == null || !Server485_ReadTxn.getAsyncReadTxnServer().isRunning())
//                        new Server485_ReadTxn("122.176.117.78", 54309, BrancoApp.this);
//
//                    if (SharedPref.getLoginResponse() != null) {
//                        if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size() > 1) {
//                            String string1 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_volume();
//                            Log.d("atgSerialNoTank1", string1);
//                            if (string1 != null) {
//                                atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
//                            }
//                            String string2 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(1).getData_volume();
//                            if (string2 != null) {
//                                atgSerialNoTank2 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(1).getData_atg_serial_no();
//                            }
//
//                        } else if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size() == 1) {
//                            String string = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
//                            if (string != null) {
//                                atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_atg_serial_no();
//
//                            }
//                        }
//                        if (ServerATG285.getAsyncServer() == null || !ServerATG285.getAsyncServer().isRunning())
//                            new ServerATG285("122.176.117.78", 54310, (RouterResponseListener) context, atgSerialNoTank1, atgSerialNoTank2);
//
//                    }
//                    try {
//                        readVolumeTotalizer();
//                        //getRfidData();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    //setMessage("Connecting...");
                }
            }, 500);
        } catch (Exception e) {
            Toast.makeText(context, "Something Went Wrong With Server", Toast.LENGTH_LONG).show();
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
    public void OnRouter485StatusAborted() {

    }

    @Override
    public void OnRouter485TxnAborted() {

    }

    @Override
    public void OnRouter285Connected() {

    }

    @Override
    public void OnRouter285ATGConnected() {
        getATG1Data();
    }

    @Override
    public void OnRouter285RfidConnected() {

    }

    @Override
    public void OnRouter285Aborted() {

    }

    @Override
    public void OnRouter285ATGAborted() {

    }

    @Override
    public void OnRouter285RfidAborted() {

    }

    @Override
    public void OnRfIdReceived(String rfId) {

    }

    @Override
    public void OnATGReceivedLK(String atg) {

    }

    @Override
    public void OnATGReceivedLK2(String atg) {

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
            if (ServerATG285.getSocket() != null) {
                if (ServerATG285.getAsyncServer().isRunning()) {
                    if (!atgSerialNoTank1.isEmpty()) {

                        Log.e("AtgKamal3", SharedPref.getMakeOfAtg());
                        //if (!isATGPort3Selected)
                        ListenATG1();


                        ReadATG1();


                    } else {
                        Log.e("getAtg", "AtgTankNoNull");
                    }
                }
            }
        }
    }

    public void ListenATG1() {
        Log.e("getAtg", "Lisen");
        send285ATGCommand(Commands.LISTEN_ATG_2_285.toString());
    }

    public void ReadATG1() {

        Log.e("AtgKamal4", SharedPref.getMakeOfAtg());
        if (SharedPref.getMakeOfAtg().contains("OPW")) {
            if (ServerATG285.getSocket() != null) {
                if (ServerATG285.getAsyncServer().isRunning()) {
                    if (!atgSerialNoTank1.isEmpty()) {
                        //if (!isATGPort4Selected)

                        send285ATGCommand("M" + atgSerialNoTank1); //This is atg value/id
                        Log.d("CommandReadAtgPort4", "M" + atgSerialNoTank1);
                    }
                }
            }
        } else {
            Log.e("BrancoApp","ATG"+ SharedPref.getMakeOfAtg());
            //send285ATGshortCommand("010310070002710A");
            String[] readAtg = new String[]{Commands.FINETEK_ATG_LEVEL.toString()};

            new CammondQueueAtg(readAtg, new OnAllCommandCompleted() {
                @Override
                public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                    if (lastResponse != null) {
                        Log.e("responseAtgK1", lastResponse);
                    } else {
                        Log.e("responseAtgK", "null");
                    }
                }

                @Override
                public void onAllCommandCompleted(int currentCommand, String response) {
                    Log.e("responseAtgK2", response);
                    response.contains("0103");
                    String respone1 = response.substring(6, 8);
                    String respone2 = response.substring(8, 10);
                    String respone3 = response.substring(10, 12);
                    String respone4 = response.substring(12, 14);


                    float myFloat = Float.intBitsToFloat((int) Long.parseLong(respone3 + respone4 + respone1 + respone2, 16));
                    Log.e("atgResponse", "" + myFloat + "," + respone1 + respone2 + respone3 + respone4);

                    float tank1AtgReading = Float.parseFloat(String.valueOf(myFloat).substring(0, String.valueOf(myFloat).indexOf(".") + 2));

                    float preAtgReading = 0, postAtgReading, preAtgVolume = 0, postAtgVolume, meanAtgVolume;
                    for (LinkedHashMap.Entry hashMap : atgLinkedHashMapTank1.entrySet()) {
                        atgcount = test;
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
                                    if (tableAtgReading == tank1AtgReading) {

                                        double tank1Volume = Float.parseFloat(jsonObject.get("B").getAsString());
                                        Log.d("VolumeBeforeTempTank1", tank1Volume + "");

                                        double volumeDifference = 0;
                                        Log.d("VolumeTempDiffTank1", volumeDifference + "");

                                        tank1Volume = tank1Volume + volumeDifference;
                                        int indexd = String.valueOf(tank1Volume).indexOf(".");
                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + String.valueOf(tank1Volume).substring(0, indexd + 3));
                                        SharedPref.setAvailablevolume(String.valueOf(tank1Volume).substring(0, indexd + 3));
                                        Log.d("VolumeAfterTempTank1", tank1Volume + "," + "kam" + NavigationDrawerActivity.availableBalance.getText().toString());
                                        if (tank1MaxVolume > 0) {
                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
                                            // waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
                                        }
                                        //waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
                                        double totalAvailableVolume = tank1Volume;
//                                                                balanceQunty = totalAvailableVolume;
//                                                                tvTotalBalanceAvailable.setText(String.format("%.2f", totalAvailableVolume) + "");
                                        atgData.postValue((String.format("%.2f", (totalAvailableVolume))));


                                    } else if (tableAtgReading < tank1AtgReading) {
                                        preAtgReading = tableAtgReading;
                                        preAtgVolume = Float.parseFloat(jsonObject.get("B").getAsString());
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

                                        int indexd = String.valueOf(tank1Volume).indexOf(".");
                                        NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + String.valueOf(tank1Volume).substring(0, indexd + 3));
                                        SharedPref.setAvailablevolume(String.valueOf(tank1Volume).substring(0, indexd + 3));
                                        if (tank1MaxVolume > 0) {
                                            String string = String.valueOf((tank1Volume * 100) / tank1MaxVolume);
                                            int tank1VolumePercent = Integer.parseInt(string.substring(0, string.indexOf(".")));
                                            Log.d("VolumeTank1Progress", tank1VolumePercent + "");
                                            //  waveLoadingViewTank1.setProgressValue(tank1VolumePercent);
                                        }
                                        // waveLoadingViewTank1.setCenterTitle(String.format("%.2f", tank1Volume));
                                        double totalAvailableVolume = tank1Volume;
                                        atgData.postValue((String.format("%.2f", (totalAvailableVolume))));
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
            }).doCommandChaining();
        }
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

    void getAtgr() {
        if (SharedPref.getLoginResponse() != null) {
            if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size() > 1) {

                Log.e("AtgSerialNo2", SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_atg_serial_no());
                String string2 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(1).getData_volume();
                if (string2 != null) {
                    atgSerialNoTank2 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(1).getData_atg_serial_no();
                    // atgSerialNoTank2 = "23872"; //Testing
//                            JSONObject jsonObject2 = new JSONObject(string2);
//                            atgLinkedHashMapTank2 = new Gson().fromJson(jsonObject2.toString(), LinkedHashMap.class);

                    //   test2 = atgLinkedHashMapTank2.size();

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

            } else if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size() == 1) {
                Log.e("AtgSerialNo", SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_atg_serial_no());
                String string = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_volume();
                if (string != null) {
                    atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_atg_serial_no();
                    Log.e("atgkamal", atgSerialNoTank1);
                    // atgSerialNoTank1 = "23872"; //Testing
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
            }
        }
    }
}

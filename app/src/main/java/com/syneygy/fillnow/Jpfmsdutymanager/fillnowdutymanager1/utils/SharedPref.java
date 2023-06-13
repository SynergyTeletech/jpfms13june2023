package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.DeliveryInProgress;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPref {

    private static final String SHARED_PREF_NAME = "jpfms.synergy.usersharedpref";
    private static final String VEHICLE_ID = "jpfmsVehicleID";
    private static final String LOGIN_ID = "jpfmsloginId";
    private static final String USER_EMAIL = "jpfmsUserEmail";
    private static final String UNIQUE_TOKEN_VALUE = "jpfmsUniqueTokenGenerated";
    private static final String USER_LOGIN_CREDENTIAL = "jpfmsLoginCred";
    private static final String ACTIVE_SETTINGS = "jpfmsActiveSettings";
    private static final String LAST_FUEL_DISPENSER_READING = "jpfmsLastTotalizer";
    private static final String LAST_TRANSACTION_ID = "jpfmsLastTxnId";
    private static final String ACEPTED_ORDER_LIST = "jpfmsacceptedlist";
    private static final String OFFLINE_ORDER_LIST = "jpfmsofflineOrderList";
    private static final String INDELIVERY_PROGRESS_LIST = "jpfmsprogressList";
    private static final String IS_FUELING_STARTED = "jpfmsIsFuelingStarted";
    private static final String TOTALIZER_READING = "jpfmstotalizerReading";
    private static final String TOTALIZER_READING_DIFF = "jpfmstotalizerReadingDiff";
    private static final String MUST_ID = "jpfmsmustID";
    private static final String Recieve_Fuel = "jpfmsrecieveFuel";
    private static final String AVAILABLEVOLUME = "jpfmsavailablevolume";
    private static final String MAKE_OF_ATG = "jpfmsmakeofatg";
    private static final String MAKE_OF_VAHICLEDATA = "jpfmsmakeofvahicle";
    private static final String VOL_TOTLIZER = "voltotlizer";
    private static final String AMMOUNT_TOTLIZER = "ammounttotlizer";
    private static final String SERVER_IP = "serverIp";
    private static final String DISPENSER = "dispenser";
    private static final String BALANCE_QTY = "balance_qty";
    private static final String ORDER_QTY = "order_qty";
    private static final String TANK_VOLUME = "tankVolume";
    private static final String STATUS_COMPLETE = "status_complete";
    private static final String ATG_INITIAL_READING = "atg_initial_reading";
    private static final String ATG_DATA_FOUND ="true";
    private static final String ATG_FINAL_READING_BACKUP ="atg_final_reading";
    private static final String TEST_LOCAL ="test_local";
    private static final String TEST_LOCAL_CRASH ="test_local_crash";
    private SharedPref() {
    }

    private static SharedPreferences getUserSharedPreferences() {
        return BrancoApp.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setAcceotedList(List<Order> list) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(ACEPTED_ORDER_LIST, json);
        editor.apply();
    }
    public static void setTankVolume(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(TANK_VOLUME, newValue);
        editor.apply();
    }

    public static String getAtgDataFound() {
        return ATG_DATA_FOUND;
    }
  public static  void setAtgDataFound(String atg){
      final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
      editor.putString(ATG_DATA_FOUND, atg);
      editor.apply();
  }
    public static String getTankVolume() {
        return getUserSharedPreferences().getString(TANK_VOLUME, "");
    }
    public static List<Order> getAcceptedList() {
        String s = getUserSharedPreferences().getString(ACEPTED_ORDER_LIST, "");
        List<Order> list = new ArrayList<>();
        if (s != null) {
            Gson gson = new Gson();

            Type type = new TypeToken<List<Order>>() {
            }.getType();
            list = gson.fromJson(s, type);
        }
        return list;
    }

    public static void setOffineOrderList(List<DeliveryInProgress> list) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(OFFLINE_ORDER_LIST, json);
        editor.apply();
    }

    public static List<DeliveryInProgress> getOfflineOrderList() {
        String s = getUserSharedPreferences().getString(OFFLINE_ORDER_LIST, "");
        List<DeliveryInProgress> list = new ArrayList<>();
        if (s != null) {
            Gson gson = new Gson();

            Type type = new TypeToken<List<DeliveryInProgress>>() {
            }.getType();
            list = gson.fromJson(s, type);
        }
        return list;
    }

    public static void setProgressList(List<Order> list) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(INDELIVERY_PROGRESS_LIST, json);
        editor.apply();
    }

    public static List<Order> getProgressList() {
        String s = getUserSharedPreferences().getString(INDELIVERY_PROGRESS_LIST, "");
        List<Order> list = new ArrayList<>();
        if (s != null) {
            Gson gson = new Gson();

            Type type = new TypeToken<List<Order>>() {
            }.getType();
            list = gson.fromJson(s, type);
        }
        return list;
    }

    public static void setTestLocal(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(TEST_LOCAL, newValue);
        editor.apply();
    }

    public static String getTestLocal() {
        return getUserSharedPreferences().getString(TEST_LOCAL, "");
    }
    public static void setTestLocalCrash(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(TEST_LOCAL_CRASH, newValue);
        editor.apply();
    }

    public static String getTestLocalCrash() {
        return getUserSharedPreferences().getString(TEST_LOCAL_CRASH, "");
    }

    public static void setBalanceQty(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(BALANCE_QTY, newValue);
        editor.apply();
    }

    public static String getBalanceQty() {
        return getUserSharedPreferences().getString(BALANCE_QTY, "");
    }

    public static void setBalanceOrderQty(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(ORDER_QTY, newValue);
        editor.apply();
    }

    public static String getBalanceOrderQty() {
        return getUserSharedPreferences().getString(ORDER_QTY, "");
    }


    public static void setVehicleId(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(VEHICLE_ID, newValue);
        editor.apply();
    }

    public static String getVehicleId() {
        return getUserSharedPreferences().getString(VEHICLE_ID, "");
    }

    public static void setVolTotlizer(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(VOL_TOTLIZER, newValue);
        editor.apply();
    }

    public static String getVolTotlizer() {
        return getUserSharedPreferences().getString(VOL_TOTLIZER, "");

    }

    public static void setAmmountTotlizer(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(AMMOUNT_TOTLIZER, newValue);
        editor.apply();
    }

    public static String getAmmountTotlizer() {
        return getUserSharedPreferences().getString(AMMOUNT_TOTLIZER, "");
    }
   public static void setServerIp(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(SERVER_IP, newValue);
        editor.apply();
    }

    public static String getServerIp() {
        return getUserSharedPreferences().getString(SERVER_IP, "");
    }
    public static void setStatusComplete(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(STATUS_COMPLETE, newValue);
        editor.apply();
    }

    public static String getStatusComplete() {
        return getUserSharedPreferences().getString(STATUS_COMPLETE, "");
    }
    public static void setAtgInitialReading(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(ATG_INITIAL_READING, newValue);
        editor.apply();
    }

    public static String getAtgInitialReading() {
        return getUserSharedPreferences().getString(ATG_INITIAL_READING, "");
    }

    public static String getDISPENSER() {
        return getUserSharedPreferences().getString(DISPENSER, "");

    }
    public static void setMakeOfDispenser(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(DISPENSER, newValue);
        editor.apply();
    }


    public static void setMakeOfAtg(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(MAKE_OF_ATG, newValue);
        editor.apply();
    }

    public static String getMakeOfAtg() {
        return getUserSharedPreferences().getString(MAKE_OF_ATG, "");
    }

    public static void setMustId(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(MUST_ID, newValue);
        editor.apply();
    }

    public static String getMustId() {
        return getUserSharedPreferences().getString(MUST_ID, "");
    }

    public static void setAvailablevolume(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(AVAILABLEVOLUME, newValue);
        editor.apply();
    }

    public static String getAvailablevolume() {
        return getUserSharedPreferences().getString(AVAILABLEVOLUME, "");
    }

    public static void setRecieveFuel(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(Recieve_Fuel, newValue);
        editor.apply();
    }

    public static String getRecieveFuel() {
        return getUserSharedPreferences().getString(Recieve_Fuel, "");
    }


    public static void setLogineId(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(LOGIN_ID, newValue);
        editor.apply();
    }

    public static String getLoginId() {
        return getUserSharedPreferences().getString(LOGIN_ID, "");
    }


    public static LoginResponse getLoginResponse() {
        Type type = new TypeToken<LoginResponse>() {
        }.getType();
        return new Gson().fromJson(getUserSharedPreferences().getString(USER_LOGIN_CREDENTIAL, ""), type);

    }


    public static void setInitialVolumeTotalizer(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString("InitialVolumeTotalizer", newValue);
        editor.apply();
    }

    public static String getInitialVolumeTotalizer() {
        return getUserSharedPreferences().getString("InitialVolumeTotalizer", "");
    }

//    public static void setLoginResponse(LoginResponse loginResponse) {
//        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
//        setVehicleId(loginResponse.getData().get(0).getVehicle_id());
//        try {
//            editor.putString(USER_LOGIN_CREDENTIAL, new Gson().toJson(loginResponse));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        editor.apply();
//
//
//    }


    public static void setUserLoginCredential(LoginResponse loginResponse) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        setVehicleId(loginResponse.getData().get(0).getVehicle_id());
        try {
            editor.putString(USER_LOGIN_CREDENTIAL, new Gson().toJson(loginResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }

        editor.apply();


    }

    public static void setMakeOfFCC(String vahicleData) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();

        try {
            editor.putString(MAKE_OF_VAHICLEDATA, vahicleData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        editor.apply();


    }

    public static String getMakeOfFCC() {

        return getUserSharedPreferences().getString(MAKE_OF_VAHICLEDATA, "");

    }


    public static String getActiveSettings() {
        return getUserSharedPreferences().getString(ACTIVE_SETTINGS, null);
    }

    public static String getTerminalID() {
        try {
            JSONObject data = new JSONObject(getActiveSettings());
            if (data != null && data.has("Terminal_No")) {
                return data.getString("Terminal_No") + "";
            } else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static void setActiveSettings(JSONObject activeSettings) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        try {
            editor.putString(ACTIVE_SETTINGS, activeSettings.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public static String getLastFuelDispenserReading() {
        return getUserSharedPreferences().getString(LAST_FUEL_DISPENSER_READING, "");
    }


    public static void setLastFuelDispenserReading(String lastTotalizerValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        try {
            editor.putString(LAST_FUEL_DISPENSER_READING, lastTotalizerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public static String getLastTransactionId() {
        return getUserSharedPreferences().getString(LAST_TRANSACTION_ID, "");
    }


    public static void setLastTransactionId(String lastTransationId) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        try {
            editor.putString(LAST_TRANSACTION_ID, lastTransationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.apply();
    }


    public static String getServerIPAddress() {
        try {
            JSONObject jsonObject = new JSONObject(getActiveSettings());
            if (jsonObject.has("Server_Ip")) {
                Log.d("returnServer_Ip", jsonObject.getString("Server_Ip"));
                return jsonObject.getString("Server_Ip");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "192.168.1.104";
    }

    public static Integer getPortNumber() {
        try {
            JSONObject jsonObject = new JSONObject(getActiveSettings());
            if (jsonObject.has("Port_No")) {
                Log.d("returnPort", jsonObject.getString("Port_No"));
                return Integer.parseInt(jsonObject.getString("Port_No"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 4307;
    }

    public static void setIsFuelingStarted(boolean newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putBoolean(IS_FUELING_STARTED, newValue);
        editor.apply();
    }

    public static boolean getIsFuelingStarted() {
        return getUserSharedPreferences().getBoolean(IS_FUELING_STARTED, false);
    }

    public static void setTotalizerReading(float newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putFloat(TOTALIZER_READING, newValue);
        editor.apply();
    }

    public static float getTotalizerReading() {
        return getUserSharedPreferences().getFloat(TOTALIZER_READING, 0f);
    }


    public static void setTotalizerReadingDiff(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(TOTALIZER_READING_DIFF, newValue);
        editor.apply();
    }

    public static String getTotalizerReadingDiff() {
        return getUserSharedPreferences().getString(TOTALIZER_READING_DIFF, "0.0");
    }


    public static void setAtgFinalReadingBackup(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(ATG_FINAL_READING_BACKUP, newValue);
        editor.apply();
    }

    public static String getAtgFinalReadingBackup() {
        return getUserSharedPreferences().getString(ATG_FINAL_READING_BACKUP, "");
    }

}

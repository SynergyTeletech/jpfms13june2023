package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Utils
{
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isNetworkConnected(Context mContext){
        boolean isOnline = false;
        try {
            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkCapabilities capabilities = null;  // need ACCESS_NETWORK_STATE permission
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
            }
            isOnline = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOnline;
//        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo nInfo = cm.getActiveNetworkInfo();
//      boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
//        return connected;
    }
    public static boolean internetIsConnected(Context mContext) {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }
}

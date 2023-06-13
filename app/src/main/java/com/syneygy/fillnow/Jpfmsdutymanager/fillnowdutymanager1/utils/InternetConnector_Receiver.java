package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;

public class InternetConnector_Receiver extends BroadcastReceiver {
    public InternetConnector_Receiver() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Reciever","reciver bf");
        try {

            boolean isVisible = BrancoApp.isActivityVisible();// Check if
            // activity
            // is
            // visible
            // or not
            Log.i("Activity is Visible ", "Is activity visible : " + isVisible);

            // If it is visible then trigger the task else do nothing
            if (isVisible == true) {
                Log.e("Reciever23","reciver bf");
                // Check internet connection and accrding to state change the
                // text of activity by calling method
                if (Utils.isNetworkConnected(context)) {
                    Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
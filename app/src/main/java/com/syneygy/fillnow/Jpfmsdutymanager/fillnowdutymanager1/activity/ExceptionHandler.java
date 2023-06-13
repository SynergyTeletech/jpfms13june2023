package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Activity myContext;
    private final String LINE_SEPARATOR = "\n";

    public ExceptionHandler(Activity context) {
        myContext = context;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());
        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ Franchisee Details ************\n");
        errorReport.append("Franchisee Id: ");
        errorReport.append(SharedPref.getLoginResponse().getVehicle_data().get(0).getFranchiseeId());
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Terminal Id: ");
        errorReport.append(SharedPref.getTerminalID());
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK_INT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Received Error in JPFMS Duty manager");
        emailIntent.putExtra(Intent.EXTRA_TEXT, errorReport.toString());
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@synergyteletech.com"});
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);

        try {
            myContext.startActivity(emailIntent);
            myContext.finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(myContext, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

        Log.d("TAG", "Error received: " + errorReport.toString());

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }
}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils;

import android.os.Environment;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Order;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ved Yadav on 2/18/2019.
 */
public class LogToFile {
    private static File backupPath = Environment.getExternalStorageDirectory();
    private static File fullPath = null;
    private static String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Synergy/FillNows";


    public static void setFuelingLog(
            String intialTotalizer,
            String fuelDispensed,
            String currentUserCharge,
            String dateTime,
            String assetName,
            String currentFuelCharge,
            Order order,
            String dispensedIn,
            Integer suspendEvents) {

        String msg = "\n" + "----------------------------------------------------------------"
                        + "\nLast Reading: At " + dateTime
                        + " \n\nLast Transaction was = " + "\n" + order.getTransaction_id()
                        + " \n\nReading Before Transaction Start=" + "\n" + intialTotalizer + " Ltr."
                        + " \n\nDispensed= " + "\n" + fuelDispensed + " Ltr."
                        + " \n\nCurrent Fuel Price= " + "\n" + currentFuelCharge + " Rs."
                        + " \n\nDuty Manager ID= " + "\n" + SharedPref.getLoginResponse().getData().get(0).getDuty_id()
                        + " \n\nDuty Manager Name= " + "\n" + SharedPref.getLoginResponse().getData().get(0).getName()
                        + " \n\nVehicle ID= " + "\n" + SharedPref.getLoginResponse().getData().get(0).getVehicle_id()
                        + " \n\nVehicle Reg No= " + "\n" + "Not Present"
                        + " \n\nFranchisee/Branch ID= " + "\n" + order.getBranchID()
                        + " \n\nCustomer ID= " + "\n" + "Not Present"
                        + " \n\nCustomer Name= " + "\n" + order.getOrder_contact_person_name()
                        + " \n\nLocation ID= " + "\n" + order.getLocation_id()
                        + " \n\nLocation Name= " + "\n" + order.getLocation_name()
                        + " \n\nLatitude= " + "\n" + order.getLatitude()
                        + " \n\nLongitude= " + "\n" + order.getLongitude()
                        + " \n\nTerminal Id= " + "\n" + SharedPref.getTerminalID()
                        + " \n\nProduct= " + "\n" + order.getFuel()
                        + " \n\nAsset ID= " + "\n" + "Not Present"
                        + " \n\nAsset Name= " + "\n" + assetName
                        + " \n\nDispense Qty= " + "\n" + order.getQuantity()
                        + " \n\nUnit Price= " + "\n" + currentFuelCharge
                        + " \n\nAmount= " + "\n" + currentUserCharge + " Rs."
                        + " \n\nMeter Reading= " + "\n" + "Not Present"
                        + " \n\nAsset Other Reading= " + "\n" + "Not Present"
                        + " \n\nPayment Ref number= " + "\n" + "Not Present"
                        + " \n\nATG Tank Start Reading= " + "\n" + "Not Present"
                        + " \n\nATG Tank End Reading= " + "\n" + "Not Present"
                        + " \n\nVolume Totalizer= " + "\n" + intialTotalizer
                        + " \n\nGPS Status= " + "\n" + "false"
                        + " \n\nManhole Status= " + "\n" + "false"
                        + " \n\nRFID Status= " + "\n" + "false"
                        + " \n\nDCV Status= " + "\n" + "false"
                        + " \n\nATG Status= " + "\n" + "false"
                        + " \n\nLocation Reading 1= " + "\n" + order.getLatitude()
                        + " \n\nLocation Reading 2= " + "\n" + order.getLongitude()
                        + " \n\nNo of event (start/stop)= " + "\n" + String.valueOf(suspendEvents)
                        + " \n\nDispensed in = " + "\n" + dispensedIn
                        + " \n\nOrder ID= " + "\n" + order.getOrder_id();


        LogMessageToFile(msg);
    }

    public static void LogMessageToFile(String msg) {

//        if (!getBackupPath().exists()) {
//            boolean da = backupPath.mkdirs();
//            Log.d("isCreatedDir", String.valueOf(da));
//        } else {

        File root = new File(rootPath);
        if (!root.exists()) {
            root.mkdirs();
        }


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        String fileName = String.format("%s %s.txt", "LOG_FILL_NOW_", df.format(c));

        File logFile = new File(root, fileName);
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }


            PrintWriter printWriter = new PrintWriter(new FileWriter(logFile, true), true);
            printWriter.write(msg);
            printWriter.close();
            printWriter.flush();


        } catch (FileNotFoundException e) {

            e.printStackTrace();


        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void LogATGReadingToFile(String msg) {


        File root = new File(rootPath);
        if (!root.exists()) {
            root.mkdirs();
        }


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        String fileName = String.format("%s %s.txt", "LOG_FILL_NOW_ATG", df.format(c));

        File logFile = new File(root, fileName);
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }


            PrintWriter printWriter = new PrintWriter(new FileWriter(logFile, true), true);
            printWriter.write(msg);
            printWriter.close();
            printWriter.flush();


        } catch (FileNotFoundException e) {

            e.printStackTrace();


        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}

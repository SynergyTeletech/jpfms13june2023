package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnStatusQueueListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;


/**
 * Created by Ved Yadav on 2/5/2019.
 */
public class CommandStatusQueue {
    private static OnStatusQueueListener onStatusCompleted;
    private static String[] commandsList;
    private static String receiveStatus;
    private static boolean isReceivedStatus;
    private static int currentCommandStatus;
    private static CommandStatusQueue statusQueue;

    public CommandStatusQueue(String[] commandsList, OnStatusQueueListener onStatusCompleted) {
        TerminateCommandChain();
        this.commandsList = commandsList;
        this.onStatusCompleted = onStatusCompleted;
        currentCommandStatus = 0;

    }

    public static CommandStatusQueue getInstance(String[] commandsList, OnStatusQueueListener onStatusCompleted) {
        if (statusQueue == null) {
            statusQueue = new CommandStatusQueue(commandsList, onStatusCompleted);
            return statusQueue;
        } else {
            statusQueue.commandsList = commandsList;
            statusQueue.onStatusCompleted = onStatusCompleted;
            return statusQueue;
        }

    }

    public void doCommandChaining() {

        try {
            if (executeCommandOneByOne(commandsList[currentCommandStatus])) {
                Log.d("CommandInStatus", "Cleared Phase One by " + Commands.getEnumByString(String.valueOf(commandsList[currentCommandStatus])) + "" + ",Incrementing Value");
                Log.d("CammedLength", "" + commandsList.length);
                if (commandsList.length == 1) {
                    onStatusCompleted.onStatusQueueQueueEmpty(true, receiveStatus);
                    return;
                }
                currentCommandStatus++;
                isReceivedStatus = false;
                if (currentCommandStatus > commandsList.length - 1) {
                    onStatusCompleted.onStatusQueueQueueEmpty(true, receiveStatus);
                    Log.d("queExceedChainAfter", String.valueOf(Commands.getEnumByString(String.valueOf(commandsList[currentCommandStatus - 1])) + "length= " + commandsList.length));
                    return;
                }

//                else {
//                    Log.d("CommandInStatus", "Cleared Phase One ,Incremented Value Going For Other");
//
//                   // added by kamal on 29.8.2020
//                    Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            doCommandChaining();
//                        }
//                    }, 1000);
//                }
//            } else {
//                Log.d("CommandInStatus", "Failed Phase One ,Not Incremented Value");
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        doCommandChaining();
//                    }
//                }, 1000);
           }
        } catch (Exception e) {
            onStatusCompleted.onStatusQueueQueueEmpty(true, receiveStatus);
            currentCommandStatus = 0;
            e.printStackTrace();
            return;
        }
    }

    private synchronized boolean executeCommandOneByOne(String command) {
        Log.d("statusExecuteCommand", String.valueOf(currentCommandStatus) + " command= " + Commands.getEnumByString(command));
        if (sendCommand(command)) {
            return true;
        }
        else{
            Log.e("285kamal","command notsent");
        }
        return false;
    }

    private synchronized boolean sendCommand(String command) {

        if (Server485_status.getSocket() != null) {
            Util.writeAll(Server485_status.getSocket(), convertToAscii(command.toString()).getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {

                }
            });
            try {
                return new MyStatusTask().execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    private static String convertToAscii(String hex) {
        try {
            StringBuilder sb = new StringBuilder();
            StringBuilder temp = new StringBuilder();

            for (int i = 0; i < hex.length() - 1; i += 2) {
                String output = hex.substring(i, (i + 2));
                int decimal = Integer.parseInt(output.trim(), 16);
                sb.append((char) decimal);
                temp.append(decimal);
            }
            return sb.toString();
        } catch (Exception e) {
            Toast.makeText(BrancoApp.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "";
    }

    private static class MyStatusTask extends AsyncTask<Void, Void, Boolean> {
        private String receive_US_ASCII;
        private String receiveISO_8859_1;

        @Override
        protected Boolean doInBackground(Void... params) {
            Server485_status.getSocket().setDataCallback(new DataCallback() {


                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                    byte[] byteData = bb.getAllByteArray();
                    String receiveNoFormat = new String(byteData);
                    receiveStatus = encodeHexString(byteData);
                    receive_US_ASCII = new String(byteData, StandardCharsets.US_ASCII);
                    receiveISO_8859_1 = new String(byteData, StandardCharsets.ISO_8859_1);
                    Log.e("statusCommand",
                            "NoFormat= " + receiveNoFormat +
                                    "\nFormat_US_ASCII= " + receive_US_ASCII +
                                    "\nFormat_ISO_8859_1= " + receiveISO_8859_1 +
                                    "\nFormatHex= " + receiveStatus

                    );

//                    if (receiveStatus.contains("�") || receiveStatus.contains("@PIӱ")) {
//                        isReceivedStatus = false;
//                    } else {
//                        onStatusCompleted.onCommandCompleted(currentCommandStatus, receiveStatus);
//                        isReceivedStatus = true;
//                    }
                    if (receiveStatus.contains("7f")) {
                        isReceivedStatus = true;
                        onStatusCompleted.OnStatusQueueCommandCompleted(currentCommandStatus, receiveStatus);
                    } else {
                        isReceivedStatus = false;
                    }

                }
            });
            return isReceivedStatus;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Call activity method with results

        }
    }

    public static void TerminateCommandChain() {
        try {
            commandsList = null;
        } catch (Exception e) {
            e.printStackTrace();
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

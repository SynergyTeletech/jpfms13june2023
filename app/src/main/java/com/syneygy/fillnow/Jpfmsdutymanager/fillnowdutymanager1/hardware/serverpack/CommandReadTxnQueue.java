package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnTxnQueueCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;

import java.util.concurrent.ExecutionException;


/**
 * Created by Ved Yadav on 2/5/2019.
 */
public class CommandReadTxnQueue {
    private static OnTxnQueueCompleted onTxnCompleted;
    private static String[] commandsListReadTxn;
    private static String receiveTxn;
    private static boolean isReceivedTxn;
    private static int currentCommandTxn;
    private static CommandReadTxnQueue readTxnQueue;

    public CommandReadTxnQueue(String[] commandsListReadTxn, OnTxnQueueCompleted onTxnCompleted) {
        TerminateCommandChain();
        this.commandsListReadTxn = commandsListReadTxn;
        this.onTxnCompleted = onTxnCompleted;
        currentCommandTxn = 0;

    }

    public static CommandReadTxnQueue getInstance(String[] commandsList, OnTxnQueueCompleted onTxnQueueCompleted) {
        if (readTxnQueue == null) {
            readTxnQueue = new CommandReadTxnQueue(commandsList, onTxnQueueCompleted);
            return readTxnQueue;
        } else {
            readTxnQueue.commandsListReadTxn = commandsList;
            readTxnQueue.onTxnCompleted = onTxnQueueCompleted;
            return readTxnQueue;
        }

    }


    public void doCommandChaining() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (executeCommandOneByOne(commandsListReadTxn[currentCommandTxn])) {
                        Log.e("kamalCamand",commandsListReadTxn[currentCommandTxn]);
                        Log.d("CommandInRead", "Cleared Phase One by " + Commands.getEnumByString(String.valueOf(commandsListReadTxn[currentCommandTxn])) + "" + ",Incrementing Value");
                        if (commandsListReadTxn.length == 1) {
                            onTxnCompleted.onTxnQueueEmpty(true, receiveTxn);
                            return;
                        }

                        currentCommandTxn++;
                        isReceivedTxn = false;
                        if (currentCommandTxn > commandsListReadTxn.length - 1) {
                            onTxnCompleted.onTxnQueueEmpty(true, receiveTxn);
                            Log.d("queExceedChainAfter", String.valueOf(Commands.getEnumByString(String.valueOf(commandsListReadTxn[currentCommandTxn - 1])) + "length= " + commandsListReadTxn.length));
                            return;
                        } else {
                            Log.d("CommandInRead", "Cleared Phase One ,Incremented Value Going For Other");
                            doCommandChaining();
                        }
                    } else {
                        Log.d("CommandInRead", "Failed Phase One ,Not Incremented Value");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                doCommandChaining();
                            }
                        });

                    }
                } catch (Exception e) {
                    onTxnCompleted.onTxnQueueEmpty(true, receiveTxn);
                    currentCommandTxn = 0;
                    e.printStackTrace();
                    return;
                }
            }
        }, 200);

    }

    private synchronized boolean executeCommandOneByOne(String command) {
        Log.d("txnExecuteCommand", String.valueOf(currentCommandTxn) + " command= " + Commands.getEnumByString(command));
        if (sendCommand(command)) {
            return true;
        }
        return false;
    }

    private synchronized boolean sendCommand(String command) {

        try {
            if (Server485_ReadTxn.getSocket() != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Util.writeAll(Server485_ReadTxn.getSocket(), convertToAscii(command).getBytes(), new CompletedCallback() {
                            @Override
                            public void onCompleted(Exception ex) {

                            }
                        });
                    }
                }).start();

                try {
                    return new MyReadTxnTask().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private static class MyReadTxnTask extends AsyncTask<Void, Void, Boolean> {
        private String receive_US_ASCII;
        private String receiveISO_8859_1;

        @Override
        protected Boolean doInBackground(Void... params) {
            Server485_ReadTxn.getSocket().setDataCallback(new DataCallback() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                    byte[] byteData = bb.getAllByteArray();


//                    String receiveNoFormat = new String(byteData);
//                    receiveTxn = encodeHexString(byteData);
//                    receive_US_ASCII = new String(byteData, StandardCharsets.US_ASCII);
//
//                    receiveISO_8859_1 = new String(byteData, StandardCharsets.ISO_8859_1);
//
//                    Log.d("readTxnCommand",
//                            "NoFormat= " + receiveNoFormat +
//                                    "\nFormat_US_ASCII= " + receive_US_ASCII +
//                                    "\nFormat_ISO_8859_1= " + receiveISO_8859_1 +
//                                    "\nFormatHex= " + receiveTxn
//
//                    );
//
//                    Log.d("queueingStatusTask", receiveTxn + " ");
//                    if (receiveTxn.contains("�") || receiveTxn.contains("@PIӱ")) {
//                        isReceivedTxn = false;
//                    } else {
//                        onTxnCompleted.onCommandCompleted(currentCommandTxn, receiveTxn);
//                        isReceivedTxn = true;
//                    }
                    if (commandsListReadTxn != null && encodeHexString(byteData).contains("7f")) {
                        isReceivedTxn = true;
                        onTxnCompleted.OnTxnQueueCommandCompleted(currentCommandTxn, new String(byteData));
                    } else {
                        isReceivedTxn = false;
                    }
                }

//                    receiveTxn = new String(bb.getAllByteArray(), StandardCharsets.US_ASCII);
//                    Log.d("queueingReadTxnTask", receiveTxn + " ");
//                    if (receiveTxn.contains("�") || receiveTxn.contains("@PIӱ")) {
//                        isReceivedTxn = false;
//                    } else {
//                        onTxnCompleted.onCommandCompleted(currentCommandTxn, receiveTxn);
//                        isReceivedTxn = true;
//                    }

            });
            return isReceivedTxn;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Call activity method with results

        }
    }

    public static void TerminateCommandChain() {
        try {
            commandsListReadTxn = null;
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

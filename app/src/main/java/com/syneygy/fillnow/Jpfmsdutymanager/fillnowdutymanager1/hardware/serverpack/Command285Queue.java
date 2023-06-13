package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Server285;

import java.util.concurrent.ExecutionException;

/**
 * Created by Ved Yadav on 9/5/2019.
 */
public class Command285Queue {
    private long WAIT_TIME = 2000;
    private static OnAllCommandCompleted onAllCommandCompleted;
    private static String[] commandsList;
    private static String receive;
    private static boolean isReceived;
    private static int currentCommand;
    private Handler handler;

    public Command285Queue(String[] commandsList, OnAllCommandCompleted onAllCommandCompleted) {
        TerminateCommandChain();
        Command285Queue.commandsList = commandsList;
        Command285Queue.onAllCommandCompleted = onAllCommandCompleted;
        currentCommand = 0;
    }

    public Command285Queue(String[] commandsList, OnAllCommandCompleted onAllCommandCompleted, long WAIT_TIME) {
        TerminateCommandChain();
        Command285Queue.commandsList = commandsList;
        Command285Queue.onAllCommandCompleted = onAllCommandCompleted;
        currentCommand = 0;
        this.WAIT_TIME = WAIT_TIME;
    }

    public static String convertToAscii(String hex) {
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

    public void doCommandChaining() {

        try {
            Log.d("kamalka", "doCommandChaining: " + (commandsList[currentCommand]));
            if (executeCommandOneByOne(commandsList[currentCommand])) {
                Log.d("CommandIn285Queue", "Cleared Phase One by " + Commands.getEnumByString(String.valueOf(commandsList[currentCommand])) + "" + ",Incrementing Value");
                currentCommand++;
                isReceived = false;
                Log.e("size of things", "list:" + commandsList.length + "," + currentCommand);
                if (commandsList.length - 1 < currentCommand) {
                    onAllCommandCompleted.commandsAllQueueEmpty(true, receive.trim().toLowerCase());
                    //Log.d("queExceedChainAfter", String.valueOf(Commands.getEnumByString(String.valueOf(commandsList[currentCommand - 1])) + "length= " + commandsList.length));
                    return;
                }
//                else {
//                    //Log.d("CommandIn285Queue", "Cleared Phase One ,Incremented Value Going For Other");
//                    doCommandChaining();
//                }
            }
//            else {
//                Log.d("CommandIn285Queue", "Failed Phase One ,Not Incremented Value");
//                handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        doCommandChaining();
//                    }
//                }, WAIT_TIME);
//            }
        } catch (Exception e) {
            onAllCommandCompleted.commandsAllQueueEmpty(true, receive);
            e.printStackTrace();
            return;
        }
    }

    private synchronized boolean executeCommandOneByOne(String command) {
        //Log.d("queueingExecuteCommand", String.valueOf(currentCommand) + " command= " + Commands.getEnumByString(command));
        if (sendCommand(command)) {
            return true;
        } else if (sendCommand285(command)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean sendCommand285(String command) {
        if (Server285.getSocket() != null) {
            Util.writeAll(Server285.getSocket(), convertToAscii(command).getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                }
            });
            try {
                return new MyTask285().execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    private synchronized boolean sendCommand(String command) {

        if (Server285.getSocket() != null) {
            Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                }
            });
            try {
                return new MyTask().execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    private static class MyTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Server285.getSocket().setDataCallback(new DataCallback() {
                @Override
                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                    byte[] byteData = bb.getAllByteArray();
                    receive = new String(byteData);
                    if (receive.contains("�") || receive.contains("@PIӱ")) {
                        isReceived = false;
                    } else {
                        onAllCommandCompleted.onAllCommandCompleted(currentCommand, receive);
                        isReceived = true;
                    }
                    if (convertASCIItoHEX(receive).equalsIgnoreCase("lo")) {
                        Log.e("Loresponse", convertASCIItoHEX(receive));
                        onAllCommandCompleted.onAllCommandCompleted(currentCommand, convertASCIItoHEX(receive));
                    }
                }
            });
            return isReceived;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Call activity method with results

        }
    }

    private static class MyTask285 extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Server285.getSocket().setDataCallback(new DataCallback() {
                @Override
                public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                    byte[] byteData = bb.getAllByteArray();

                    receive = new String(byteData);

                    Log.e("reciverResponse", receive.toString());
                    if (receive.contains("�") || receive.contains("@PIӱ")) {
                        isReceived = false;
                    } else {
                        onAllCommandCompleted.onAllCommandCompleted(currentCommand, receive);
                        isReceived = true;
                    }
                    if (convertASCIItoHEX(receive).equalsIgnoreCase("lo")) {
                        Log.e("Loresponse", convertASCIItoHEX(receive));
                        onAllCommandCompleted.onAllCommandCompleted(currentCommand, convertASCIItoHEX(receive));
                    }
                }
            });
            Log.d("Recieved", "" + isReceived);
            return isReceived;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Call activity method with results

        }
    }

    public static String convertASCIItoHEX(String ascii) {

        // Step-1 - Convert ASCII string to char array
        char[] ch = ascii.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : ch) {
            // Step-2 Use %H to format character to Hex
            String hexCode = String.format("%H", c);
            if (hexCode.length() < 2)
                hexCode = "0" + hexCode;
            builder.append(hexCode);
        }
        System.out.println("ATGASCII = " + ascii);
        System.out.println("ATGHex = " + builder.toString());

        return builder.toString();
    }
}

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
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.CommandCodeNameMatrix;

import java.util.concurrent.ExecutionException;

import static com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.DispenserUnitK.tvCommandExecutionTxt;

public class CommandRfidQueue {

        private static OnAllCommandCompleted onAllCommandCompleted;
        private static String[] commandsList;
        private static String receive;
        private static boolean isReceived;
        private static int currentCommand;

        public CommandRfidQueue(String[] commandsList, OnAllCommandCompleted onAllCommandCompleted) {
            TerminateCommandChain();
            this.commandsList = commandsList;
            this.onAllCommandCompleted = onAllCommandCompleted;
            currentCommand = 0;

        }

        public com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandRfidQueue doCommandChaining() {

            try {
                if (commandsList!=null){
                    if (commandsList.length>currentCommand){
                        if (executeCommandOneByOne(commandsList[currentCommand])) {
                            try {
//                    tvCommandExecutionTxt.setText(commandsList[currentCommand]); //Commented this line & added below 2 lines by Laukendra
                                CommandCodeNameMatrix commandCodeNameMatrix=new CommandCodeNameMatrix();
                                tvCommandExecutionTxt.setText(commandCodeNameMatrix.getCommandNameOfCode(commandsList[currentCommand]));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //Log.d("CommandInQueue", "Cleared Phase One by " + Commands.getEnumByString(String.valueOf(commandsList[currentCommand])) + "" + ",Incrementing Value");
                            currentCommand++;
                            Log.e("kamal",""+currentCommand);
                            isReceived = false;
                            if (currentCommand > commandsList.length - 1) {
                                onAllCommandCompleted.commandsAllQueueEmpty(true, receive);
//                    Log.d("queExceedChainAfter", String.valueOf(Commands.getEnumByString(String.valueOf(commandsList[currentCommand - 1])) + "length= " + commandsList.length));
                                return null;
                            } else {
                                Log.d("CommandInQueue", "Cleared Phase One ,Incremented Value Going For Other");
//                    if (executeCommandOneByOne(commandsList[currentCommand])) {
//                        currentCommand++;
//                    } else {
                                doCommandChaining();
//                    }
                            }
                        } else {
                            //Log.d("CommandInQueue", "Failed Phase One ,Not Incremented Value");
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    doCommandChaining();
                                }
                            }, 200); //Last value was 1000 changed by Laukendra on 14-11-19
                        }
                    }
                }
            } catch (Exception e) {
                onAllCommandCompleted.commandsAllQueueEmpty(true, receive);
                e.printStackTrace();
                return null;
            }
            return null;
        }

        private synchronized boolean executeCommandOneByOne(String command) {
            Log.d("queueingkamal", String.valueOf(currentCommand) + " command= " + Commands.getEnumByString(command)+"   "+command);
            if (sendCommand(command)) {
                return true;
            }
            return false;
        }

        private synchronized boolean sendCommand(String command) {

            if (Server285_ReadRFID.getSocket() != null) {
                Util.writeAll(Server285_ReadRFID.getSocket(), convertToAscii(command).getBytes(), new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {

                    }
                });
                try {
                    return new MyTaskRFid().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return false;
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

        private static class MyTaskRFid extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... params) {

                Server285_ReadRFID.getSocket().setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                        byte[] byteData = bb.getAllByteArray();
                        receive = new String(byteData);

                        Log.e("ResponseHexK",encodeHexString(byteData));
                      //  if (encodeHexString(byteData).contains("597f")) {
                            onAllCommandCompleted.onAllCommandCompleted(currentCommand, encodeHexString(byteData));

//                            isReceived = true;
//                        } else {
//                            isReceived = false;
//                        }
                    }
                });
                return isReceived;

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

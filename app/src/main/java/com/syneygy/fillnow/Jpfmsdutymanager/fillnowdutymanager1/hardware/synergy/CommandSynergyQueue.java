package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.content.Context;
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
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Server485;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class CommandSynergyQueue {

    private long WAIT_TIME = 6000;
    private static OnAllCommandCompleted onAllCommandCompleted;
    private static String[] commandsList;
    private static String receive;
    private static boolean isReceived;
    private static int currentCommand;
    private Handler handler;
    private Context context;

    public CommandSynergyQueue(String[] commandsList, OnAllCommandCompleted onAllCommandCompleted, Context context) {
        TerminateCommandChain();
        CommandSynergyQueue.commandsList = commandsList;
        CommandSynergyQueue.onAllCommandCompleted = onAllCommandCompleted;
        currentCommand = 0;
        this.context = context;

    }

    public CommandSynergyQueue(String[] commandsList, OnAllCommandCompleted onAllCommandCompleted, long WAIT_TIME) {
        TerminateCommandChain();
        CommandSynergyQueue.commandsList = commandsList;
        CommandSynergyQueue.onAllCommandCompleted = onAllCommandCompleted;
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
                Log.d("CommandIn485Queue", "Cleared Phase One by " + Commands.getEnumByString(String.valueOf(commandsList[currentCommand])) + "" + ",Incrementing Value");
                currentCommand++;
                isReceived = false;
                Log.e("sizeofthings", "list:" + commandsList.length + "," + currentCommand);
                if (commandsList.length - 1 < currentCommand) {
                    onAllCommandCompleted.commandsAllQueueEmpty(true, receive.trim().toLowerCase());
                    //Log.d("queExceedChainAfter", String.valueOf(Commands.getEnumByString(String.valueOf(commandsList[currentCommand - 1])) + "length= " + commandsList.length));
                    return;
                } else {
                    //Log.d("CommandIn285Queue", "Cleared Phase One ,Incremented Value Going For Other");
                    //  doCommandChaining();
                }
            } else {
                Log.d("CommandIn485Queue", "Failed Phase One ,Not Incremented Value");
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doCommandChaining();
                    }
                }, WAIT_TIME);
            }
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
        }
//        else if(sendCommand285(command)){
//            return true;
//        }
        else {
            return false;
        }
    }

//    private boolean sendCommand285(String command) {
//        if (Server485_ManholeLock.getSocket() != null) {
//            Util.writeAll(Server485_ManholeLock.getSocket(), command.getBytes(), new CompletedCallback() {
//                @Override
//                public void onCompleted(Exception ex) {
//                }
//            });
//            try {
//                return new CommandSynergyQueue.MyTask285().execute().get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return false;
//    }

    private synchronized boolean sendCommand(String command) {
        Log.e("commandSend", command);
        if (Server485.getSocket() != null) {

            //Util.writeAll(Server485.getSocket(), command.getBytes("UTF-16"), new CompletedCallback() {
//            byte[] commandp = new byte[0];


//            try {
//                commandp = command.getBytes("UTF-8");
//                Log.e("commandName", new String(commandp));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

            Log.e("Sever485Ips",""+Server485.getSocket().isOpen());
            Util.writeAll(Server485.getSocket(), hexStringToByteArray(command), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {

                    Log.d("writing485", command + "");
                    // Log.e("ATG-Res", ex.toString());
                }
            });

            try {
                return new MyTaskkamal().execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            Log.e("socket485k", "null");
        }

        return false;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static class MyTaskkamal extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.e("KamalRes1", "res");
            if (Server485.getSocket() != null) {
                Log.e("server485", "Notnull");
                Server485.getSocket().setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        try {
                            Log.e("KamalRes", "res");
                            if (bb != null) {
                                byte[] byteData = bb.getAllByteArray();
                                receive = new String(byteData);
                                receive = bytesToHex(byteData);
                                Log.e("kamal", receive);
                            } else {
                                Log.e("KamalRes", "bbnull");
                            }
                            try {
                                Log.e("Stringres", new String(receive.getBytes(), "UTF-8"));
                                Log.e("Stringres1", new String(receive.getBytes(), "US-ASCII"));
                                Log.e("Stringres2", new String(receive.getBytes(), "ISO-8859-1"));
                                Log.e("Stringres3", new String(receive.getBytes(), "UTF-16BE"));
                                Log.e("Stringres4", new String(receive.getBytes(), "UTF-16LE"));
                                Log.e("Stringres5", new String(receive.getBytes(), "UTF-16"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Log.e("reviceKamal", receive);
                            // Log.e("nonAscci", encodeToNonLossyAscii(receive));

                            onAllCommandCompleted.onAllCommandCompleted(currentCommand, receive);
                            isReceived = true;
                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }
                    }
                });

            } else {
                Log.e("KamalRes2", "resNull");
            }
            Log.e("KamalRes2", "res");
            return isReceived;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Call activity method with results


        }
    }

    public static String encodeToNonLossyAscii(String original) {
        Charset asciiCharset = Charset.forName("UTF-8");
        if (asciiCharset.newEncoder().canEncode(original)) {
            return original;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c < 128) {
                stringBuffer.append(c);
            } else if (c < 256) {
                String octal = Integer.toOctalString(c);
                stringBuffer.append("\\");
                stringBuffer.append(octal);
            } else {
                String hex = Integer.toHexString(c);
                stringBuffer.append("\\u");
                stringBuffer.append(hex);
            }
        }
        return stringBuffer.toString();
    }

    private static class MyTask285 extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Server485.getSocket().setDataCallback(new DataCallback() {
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

}



package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Client extends AsyncTask<String, Integer, Boolean> {
    Socket socket;
    byte[] buffer;
    String hostAdd;
    InputStream inputStream;
    OutputStream outputStream;
    Thread thread;
    boolean isconnectedB = false;
    private int portNo = 0;
    ReciverListner reciverListner;

    public Client(InetAddress hostAddress, int portNo, ReciverListner reciverListner) {
        hostAdd = hostAddress.getHostAddress();
        this.portNo = portNo;
        Log.v("CLIENT","TEST"+hostAdd+"port_no,"+portNo);

        socket = new Socket();
        this.reciverListner = reciverListner;
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        boolean result = false;
        try {
            socket.connect(new InetSocketAddress(hostAdd, portNo), 5000);
            if (socket.isConnected()) {
                socket.setKeepAlive(true);
                socket.setSoTimeout(30000);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } else {

            }
            result = true;
            return result;
        }
        catch (IOException e) {
            e.printStackTrace();

            result = false;
            isconnectedB = false;
            return result;
        }
    }


    public void writeData(final byte[] bytes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (outputStream != null) {
                        outputStream.write(bytes);
                        outputStream.flush();
                        isconnectedB = true;
                    } else {
                        Log.v("client"+portNo, "null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public boolean isconnected() {
        return isconnectedB;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        android.os.SystemClock.sleep(600);

        Log.v("getDataR", "getdata");
        isconnectedB = true;
        if (outputStream != null) {

            reciverListner.RecivedData("", "", true);
        } else {
            Log.v("outstremnull", "null");
        }
        if (result) {

            thread = new Thread() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    super.run();
                    if(socket != null) {


                        android.os.SystemClock.sleep(1000);
                        buffer = new byte[150];
                        try {
                            socket.setTcpNoDelay(true);
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                    }
                    int x;
                    while (socket != null) {
                        try {
                            x = inputStream.read(buffer);

                            if (x > 0) {
                                String result = bytesToHex(buffer);
                                String a = new String(buffer);

//                                String text = new String(buffer, StandardCharsets.US_ASCII);
//                                Log.v("Asccii2", text);

                                String str = new String(buffer,StandardCharsets.UTF_8);
                                Log.v("ascci1", str + "," + portNo);
                                Log.v("resultk", result);
                                //  if (portNo == 232) {
                                if (portNo == 232) {
                                    reciverListner.RecivedData(result, str, true);
                                } else {
                                    reciverListner.RecivedData(result, str, true);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            thread.start();
//          new Thread(new Runnable() {
//                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                public void run() {
//
//                }
//            }).start();
        } else {
            Log.v("wificonnect", "could not create sockets");
            //restart socket assignment process
        }
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
    public interface ReciverListner {
        void RecivedData(String responseAscci, String responseHex, boolean isConnected);
    }

    void stopClint() {
        try {
            if (socket!=null) {
                socket.close();
            }
            socket = null;
            inputStream = null;
            outputStream = null;


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


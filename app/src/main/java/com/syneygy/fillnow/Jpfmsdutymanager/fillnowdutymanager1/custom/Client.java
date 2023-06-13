package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom;


import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

 public class Client extends AsyncTask<String, Integer, Boolean> {
    Socket socket;
    byte[] buffer;
    String hostAdd;
    InputStream inputStream;
    OutputStream outputStream;
    ReciverListner reciverListner;

    public Client(InetAddress hostAddress, ReciverListner reciverListner) {
        hostAdd = hostAddress.getHostAddress();
        socket = new Socket();
        this.reciverListner=reciverListner;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean result = false;
        try {
            socket.connect(new InetSocketAddress(hostAdd, 54306), 1000);
            Log.e("connected","connected");
            result = true;

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
            return result;
        }
    }

    //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public String recivedata(){
//        return new String(buffer, StandardCharsets. UTF_8);
//    }
    public void writeData(final byte[] bytes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outputStream.write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                if (outputStream!=null){

                    reciverListner.RecivedData("",true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable(){
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                public void run() {
                    buffer = new byte[100];
                    int x;
                    while (socket!=null) {
                        try {
                            x = inputStream.read(buffer);
                            if(x>0) {
                                String str=new String(buffer, StandardCharsets. UTF_8);
                                reciverListner.RecivedData(str,true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            Log.e("wificonnect","could not create sockets");
            //restart socket assignment process
        }
    }
   public interface ReciverListner{
        void RecivedData(String response,boolean isconnected);
    }
}
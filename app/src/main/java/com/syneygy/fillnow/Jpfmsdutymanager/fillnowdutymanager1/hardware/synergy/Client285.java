package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.koushikdutta.async.AsyncSocket;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;


public class Client285 {
    private AsyncSocket asyncSocket;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final RouterResponseListener routerResponseListener;
    private String host;
    private LongOperation lo;
    private int port;

    public static Socket socket = null;
    public Client285( RouterResponseListener routerResponseListener, String host, int port) {
        this.routerResponseListener = routerResponseListener;
        this.host = host;
        this.port = port;
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(String... params) {

            socket = null;
            SocketAddress address = new InetSocketAddress(host, port);

            socket = new Socket();


            try {
                socket.connect(address, 30000);
            } catch (IOException e) {
                Log.d("time","no worky X");
                e.printStackTrace();
            }
            try {
                socket.setSoTimeout(30000);
            } catch (SocketException e) {
                Log.d("timeout","server took too long to respond");

                e.printStackTrace();
                return "Can't Connect";
            }
            OutputStream out = null;
            try {
                out = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter output = new PrintWriter(out);


            output.print("FA0103A31121");
            output.flush();

//read
            String str = "waiting";
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Log.d("test","trying to read from server");

                String line;
                str = "";
                while ((line = br.readLine()) != null) {
                    Log.d("read line",line);
                    str = str + line;
                    str = str + "\r\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (str != null) {
                Log.d("test","trying to print what was just read");
                System.out.println(str);
            }


//read
            output.close();

//read
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//read
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("tag", "done server");
            return str;
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
          Log.e("result",result);

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}


        protected void onCancelled(){
            Log.d("cancel","ca");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
    /** Called when the user taps the Send button */
    public void sendMessage() {

        lo = new LongOperation();
        lo.execute();


        //startActivity(intent);
    }

}

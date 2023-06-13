package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack;

import android.util.Log;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseSynergy;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.LogToFile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerSynery{
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private  RouterResponseSynergy routerResponseListener;
    private InetAddress host;
    private int port;
    private static AsyncSocket socket;
    private static ServerSynery instace;
    private static AsyncServer asyncServer;

    public ServerSynery(String host, int port) {
        try {
            this.host = InetAddress.getByName(host);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = port;

        setup();
    }
    public void setListner(RouterResponseSynergy routerResponseListener){
        this.routerResponseListener=routerResponseListener;
    }
    public static ServerSynery getInstance(String host, int port){
        if(instace==null){
            instace=new  ServerSynery(host,  port);
        }
        return instace;
    }

    public static AsyncSocket getSocket() {
        return socket;
    }

    public static AsyncServer getAsyncServer() {
        return asyncServer;
    }


    private void setup() {

        asyncServer = new AsyncServer("Queue");
        asyncServer.listen(host, port, new ListenCallback() {
            @Override
            public void onAccepted(final AsyncSocket socket) {
                handleAccept(socket);
                ServerSynery.socket = socket;
            }

            @Override
            public void onListening(AsyncServerSocket socket) {
                System.out.println("[Server Queue] Server started listening for connections");
            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
                            AsyncServer.getCurrentThreadServer().stop();
                        }
                    } else {
                        Log.d("serverPortError485", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Queue] Successfully shutdown server");
            }
        });
    }

    private void handleAccept(final AsyncSocket socket) {
        System.out.println("[Server Queue] New Connection " + socket.toString());

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                byte[] bytesData = bb.getAllByteArray();
                 String hex= Utils.encodeHexString(bytesData).toUpperCase();


                String recive = new String(bytesData);
                if(routerResponseListener!=null) {
                    routerResponseListener.RecivedData(recive, hex, true);
                }


                System.out.println("Server485RE " + recive);
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {

                        // Log.d("serverPortError", ex.getLocalizedMessage());
                    } else {
                        //   Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Queue] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        try {
                            Log.d("serverPortError", ex.getLocalizedMessage());
                        }
                        catch (Exception e){
                            e.fillInStackTrace();
                        }

                    } else {
                        Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Queue] Successfully end connection" + ex.getLocalizedMessage());
            }
        });

    }

    public static void CloseSocket() {
        if (socket != null) {
            socket.close();
            socket.end();
        }
        if (AsyncServer.getDefault() != null) {
            AsyncServer.getDefault().stop();
        }
    }

    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        //Log.d("byteHex", String.valueOf(hexChars.length));
        //Log.d("byteHex", String.valueOf(hexChars.length));
        return new String(hexChars);
    }


}

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
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseSynergy;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerRelay {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private  RouterResponseRelay routerResponseListener;
    private static ServerRelay instace;

    private InetAddress host;
    private int port;
    private static AsyncSocket socket;
    private static AsyncServer asyncServer;

    private ServerRelay(String host, int port) {
        try {
            this.host = InetAddress.getByName(host);
//            this.routerResponseListener = routerResponseListener;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = port;

        setup();
    }
    public static ServerRelay getInstance(String host, int port){
        if(instace==null){
            instace=new  ServerRelay(host,  port);
        }
        return instace;
    }
    public void setListner(RouterResponseRelay routerResponseListener){
        this.routerResponseListener=routerResponseListener;
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
                Log.d("ServerRelay","Server Accepted Connection");
                handleAccept(socket);
                ServerRelay.socket = socket;
            }

            @Override
            public void onListening(AsyncServerSocket socket) {
                Log.d("ServerRelay","Server started listening for connections");

            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
                            AsyncServer.getCurrentThreadServer().stop();
                        }
                    } else {
                        Log.d("ServerRelay","Server"+ex.getLocalizedMessage());
//                        Log.d("serverPortError485", ex.getLocalizedMessage());
                    }
                }
                Log.d("ServerRelay","Server"+ex.getLocalizedMessage());
                System.out.println("[Server Queue] Successfully shutdown server");
            }
        });
    }

    private void handleAccept(final AsyncSocket socket) {
        Log.d("ServerRelay","Server socket");
        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                byte[] bytesData = bb.getAllByteArray();
                String hex= Utils.encodeHexString(bytesData).toUpperCase();


                String recive = new String(bytesData);
                if(routerResponseListener!=null) {
                    routerResponseListener.RecivedData1(recive, hex, true);
                }


                Log.d("ServerRelay","Server Recieved"+recive);
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

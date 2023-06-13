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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Server485_status {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final RouterResponseListener routerResponseListener;
    private InetAddress host;
    private int port;
    private static AsyncSocket socketStatus;
    private static AsyncServer asyncStausServer;

    public Server485_status(String host, int port, RouterResponseListener routerResponseListener) {
        try {
            this.host = InetAddress.getByName(host);
            this.routerResponseListener = routerResponseListener;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        this.port = port;
        setup();
    }

    public static AsyncSocket getSocket() {
        return socketStatus;
    }

    public static AsyncServer getAsyncStausServer() {
        return asyncStausServer;
    }

    public static void setSocket(AsyncSocket socketStatus) {
        Server485_status.socketStatus = socketStatus;
    }

    private void setup() {
        asyncStausServer = new AsyncServer("Status");
        asyncStausServer.listen(host, port, new ListenCallback() {

            @Override
            public void onAccepted(final AsyncSocket socketStatus) {
                handleAccept(socketStatus);
                Server485_status.socketStatus = socketStatus;
            }

            @Override
            public void onListening(AsyncServerSocket socketStatus) {
                System.out.println("[Server485 Status] Server started listening for connections");
            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter485StatusAborted();
                        //Commented by Laukendra on 24-12-19
//                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
//                            AsyncServer.getCurrentThreadServer().stop();
//                            Log.d("serverPortSucess", ex.getLocalizedMessage());
//                        }
                    } else {
                        Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Status] Successfully shutdown server");
            }
        });
    }

    private void handleAccept(final AsyncSocket socketStatus) {
        System.out.println("[Server Status] New Connection " + socketStatus.toString());

        socketStatus.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                String recive = new String(bb.getAllByteArray());
                if (recive.contains("RJ_485")) {
                    routerResponseListener.OnRouter485StatusConnected();
                }
                String data = bytesToHex(bb.getAllByteArray());
                System.out.println("[Server485 Status] Received Message Hex" + data);
                System.out.println("[Server485 Status] Received Message " + recive);
            }
        });

        socketStatus.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter485StatusAborted();
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Status] Successfully closed connection");
            }
        });

        socketStatus.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                        routerResponseListener.OnRouter485StatusAborted();
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server485 Status] Successfully end connection" + ex.getLocalizedMessage());
            }
        });

    }

    public static void CloseSocket() {

        if (socketStatus != null) {
            socketStatus.close();
            socketStatus.end();
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
        return new String(hexChars);
    }


}
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
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.LogToFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Server485 {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final RouterResponseListener routerResponseListener;
    private InetAddress host;
    private int port;
    private static AsyncSocket socket;
    private static AsyncServer asyncServer;

    public Server485(String host, int port, RouterResponseListener routerResponseListener) {
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
                Server485.socket = socket;
            }

            @Override
            public void onListening(AsyncServerSocket socket) {
                System.out.println("[Server Queue] Server started listening for connections");
            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter485QueueAborted();
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

                String recive = new String(bytesData);
                LogToFile.LogMessageToFile("Volume Received:" + recive);
                if (recive.contains("RJ_485")) {
                    routerResponseListener.OnRouter485QueueConnected();
                }


                System.out.println("Server485RE " + recive);
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter485QueueAborted();
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
                        routerResponseListener.OnRouter485QueueAborted();
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
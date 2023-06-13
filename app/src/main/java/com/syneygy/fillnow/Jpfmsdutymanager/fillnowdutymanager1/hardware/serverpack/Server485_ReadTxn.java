package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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
import java.nio.charset.StandardCharsets;

public class Server485_ReadTxn {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final RouterResponseListener routerResponseListener;
    private InetAddress host;
    private int port;
    private static AsyncSocket socketReadTXn;
    private static AsyncServer asyncReadTxnServer;

    public Server485_ReadTxn(String host, int port, RouterResponseListener routerResponseListener) {
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
        return socketReadTXn;
    }

    public static AsyncServer getAsyncReadTxnServer() {
        return asyncReadTxnServer;
    }

    private void setup() {
        asyncReadTxnServer = new AsyncServer("ReadTxn");
        asyncReadTxnServer.listen(host, port, new ListenCallback() {

            @Override
            public void onAccepted(final AsyncSocket socketReadTXn) {
                handleAccept(socketReadTXn);
                Server485_ReadTxn.socketReadTXn = socketReadTXn;
            }

            @Override
            public void onListening(AsyncServerSocket socketReadTXn) {
                System.out.println("[Server Txn] Server started listening for connections");
            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter485TxnAborted();
                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
                            AsyncServer.getCurrentThreadServer().stop();
                        }
                    } else {
                        Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Txn] Successfully shutdown server");
            }
        });
    }

    private void handleAccept(final AsyncSocket socketReadTXn) {
        System.out.println("[Server Txn] New Connection " + socketReadTXn.toString());

        socketReadTXn.setDataCallback(new DataCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                String recive = new String(bb.getAllByteArray(), StandardCharsets.UTF_8);
                if (recive.contains("RJ_485")) {
                    routerResponseListener.OnRouter485TxnConnected();
                }
                String data = bytesToHex(bb.getAllByteArray());
                Log.d("[Server Txn] HexData", data);
                System.out.println("[Server Txn] Received Message Hex" + data);
                System.out.println("[Server Txn] Received Message " + recive);
            }
        });

        socketReadTXn.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter485TxnAborted();
                        Log.d("serverPortError", ex.getLocalizedMessage());
                    } else {
                        Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Txn] Successfully closed connection");
            }
        });

        socketReadTXn.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        Log.d("serverPortError", ex.getLocalizedMessage());
                        routerResponseListener.OnRouter485TxnAborted();
                    } else {
                        Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server Txn] Successfully end connection" + ex.getLocalizedMessage());
            }
        });

    }

    public static void CloseSocket() {

        if (socketReadTXn != null) {
            socketReadTXn.close();
            socketReadTXn.end();
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
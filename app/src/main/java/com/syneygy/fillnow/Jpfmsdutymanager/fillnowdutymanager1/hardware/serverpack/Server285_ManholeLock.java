package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Server285_ManholeLock {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final RouterResponseListener routerResponseListener;
    private InetAddress host;
    private int port;
    private static AsyncSocket socketReadRFid;
    private static AsyncServer asyncReadRFidServer;

    public Server285_ManholeLock(String host, int port, RouterResponseListener routerResponseListener) {
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
        return socketReadRFid;
    }

    public static AsyncServer getAsyncServer() {
        return asyncReadRFidServer;
    }

    private void setup() {

        asyncReadRFidServer = new AsyncServer("ReadRFid");
        asyncReadRFidServer.listen(host, port, new ListenCallback() {

            @Override
            public void onAccepted(final AsyncSocket socketReadTXn) {
                handleAccept(socketReadTXn);
                Server285_ManholeLock.socketReadRFid = socketReadTXn;
//                send285RFIDCommand("#*SP00");// Close port read RFID Data
//                send285RFIDCommand("#*SP21"); // Open port Read RFID Data
            }

            @Override
            public void onListening(AsyncServerSocket socketReadTXn) {
                System.out.println("[Server ReadRFid] Server started listening for connections manhole");
            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter285RfidAborted();
                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
                            AsyncServer.getCurrentThreadServer().stop();
                        }
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server RFid] Successfully shutdown server");
            }
        });
    }

    private void handleAccept(final AsyncSocket socketReadRfid) {
        System.out.println("[Server RFid] New Connection " + socketReadRfid.toString());

        socketReadRfid.setDataCallback(new DataCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                String recive = new String(bb.getAllByteArray(), StandardCharsets.UTF_8);
                if (recive.contains("RS_232")) {
                    routerResponseListener.OnRouter285RfidConnected();
                }
                if (recive != null) {
                    if (convertASCIItoHEX(recive).contains("550252"))
                        routerResponseListener.OnRfIdReceived(convertASCIItoHEX(recive));
                    if (recive.contains("RL31") || recive.contains("RL41"))
                        routerResponseListener.OnRfIdReceived(recive);
                    if (recive.contains("GI01") || recive.contains("GI02") || recive.contains("GI03") || recive.contains("GI04"))
                        routerResponseListener.OnRfIdReceived(recive);

//                    if (recive.contains("SP31") || recive.contains("SP41"))
//                        routerResponseListener.OnATGReceivedLK(recive);

                }
                System.out.println("[Server RFid] Received Message UTF " + recive);
                System.out.println("[Server RFid] Received Message Hex " +   convertASCIItoHEX(recive)+"\n");
            }
        });

        socketReadRfid.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter285RfidAborted();
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server RFid] Successfully closed connection");
            }
        });

        socketReadRfid.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                        routerResponseListener.OnRouter285RfidAborted();
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server RFid] Successfully end connection" + ex.getLocalizedMessage());
            }
        });

    }

    public String convertASCIItoHEX(String ascii){

        // Step-1 - Convert ASCII string to char array
        char[] ch = ascii.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : ch) {
            // Step-2 Use %H to format character to Hex
            String hexCode=String.format("%H", c);
            if (hexCode.length()<2)
                hexCode="0"+hexCode;
            builder.append(hexCode);
        }
        Log.d("","ASCII = " + ascii);
        System.out.println("Hex = " + builder.toString());

        return builder.toString();
    }

    public static void CloseSocket() {

        if (socketReadRFid != null) {
            socketReadRFid.close();
            socketReadRFid.end();
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
        return new String(hexChars);
    }

    public void send285RFIDCommand(String command) {

        if (Server285_ManholeLock.getSocket() != null) {
            Util.writeAll(Server285_ManholeLock.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    Log.d("writing232-RFID", command + "");
                }
            });
        }
    }

}

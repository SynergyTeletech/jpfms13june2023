package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.util.Log;

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


public class ServerATG285 {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final RouterResponseListener routerResponseListener;
    private InetAddress host;
    private int port;
    private static AsyncSocket socket;
    private static AsyncServer asyncServer;
    private String atgSerialNoTank1,atgSerialNoTank2;

    public ServerATG285(String host, int port, RouterResponseListener routerResponseListener, String atgSerialNoTank1, String atgSerialNoTank2) {
        try {
            this.host = InetAddress.getByName(host);
            this.routerResponseListener = routerResponseListener;
            this.atgSerialNoTank1=atgSerialNoTank1;
            this.atgSerialNoTank2=atgSerialNoTank2;
            //Log.e("TAG", host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
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

        asyncServer = new AsyncServer("server22");
        asyncServer.listen(host, port, new ListenCallback() {
            @Override
            public void onAccepted(final AsyncSocket socket) {

                Log.e("AtgLisen","atgListen");
                handleAccept(socket);
                ServerATG285.socket = socket;
//                if (!atgSerialNoTank1.isEmpty()) {
//                    send285ATGCommand("#*SP31");
//                    String strCommand="M" + atgSerialNoTank1;
//                    send285ATGCommand(strCommand);//Added by Laukendra on 20-12-19
//                }
//                if (!atgSerialNoTank2.isEmpty()){
//                    send285ATGCommand("#*SP41");
//                    String strCommand="M" + atgSerialNoTank2;
//                    send285ATGCommand(strCommand);//Added by Laukendra on 20-12-19
//                }

//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        send285ATGCommand("#*SP31");
//                        send285ATGCommand("M"+atgSerialNoTank1);//Added by Laukendra on 20-12-19
//                    }
//                },1);
//
//                Handler handler2=new Handler();
//                handler2.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        send285ATGCommand("#*SP41");
//                        send285ATGCommand("M"+atgSerialNoTank2);//Added by Laukendra on 20-12-19
//                    }
//                },2000);


            }

            @Override
            public void onListening(AsyncServerSocket socket) {
                System.out.println("[Server 232 ATG] Server started listening for connections");
            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter285ATGAborted();
                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
                            AsyncServer.getCurrentThreadServer().stop();
                        }
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[ServerATG 232] Successfully shutdown server");
                ex.printStackTrace();
            }
        });
    }

    private void handleAccept(final AsyncSocket socket) {
        System.out.println("[Server ATG 232] New Connection " + socket.toString());

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {

                byte[] bytesData = bb.getAllByteArray();

                String recive = new String(bytesData);
                Log.e("atgString",recive);
                if (recive.contains("RS_232")) {
                    routerResponseListener.OnRouter285ATGConnected();
                    System.out.println("[Server RS_232] Connected " + recive);
                }

                if (convertASCIItoHEX(recive).contains("550252")) {
                    routerResponseListener.OnRfIdReceived(convertASCIItoHEX(recive));
                    System.out.println("[ServerRFID SP21] Message: " + recive);
                }

                if (recive.contains("SP31")){
                    Log.e("DataTag",recive);
                    routerResponseListener.OnATGReceivedLK(recive);
                }

                if (recive.contains("SP41")){

                    Log.e("DataTag2",recive);
                    routerResponseListener.OnATGReceivedLK2(recive);
                }

                if (!atgSerialNoTank1.isEmpty()) {
                    if (recive.contains(atgSerialNoTank1)) {

                        System.out.println("[Server ATG 232 Data] Received Message: " + recive);
//                    LogToFile.LogMessageToFile("ATG Receive:" + recive + "");
                    } else {
                        System.out.println("ATG Data Else " + recive);
                        //send285ATGCommand("M" + atgSerialNoTank1);

                        routerResponseListener.OnATGReceivedLK(recive);
                        //send285ATGCommand(Commands.CLEAR_285.toString());
                        System.out.println("[Server ATG SP31] Connection Message Else: " + recive);
                    }
                }

                if (!atgSerialNoTank2.isEmpty()) {
                    if (recive.contains(atgSerialNoTank2)) {
                        routerResponseListener.OnATGReceivedLK2(recive);
                        System.out.println("[Server ATG 232 Data] Received Message: " + recive);
//                    LogToFile.LogMessageToFile("ATG Receive:" + recive + "");
                    } else {
                        System.out.println("ATG Data Else " + recive);

                        routerResponseListener.OnATGReceivedLK2(recive);
                        //send285ATGCommand("M" + atgSerialNoTank2);
                        //send285ATGCommand(Commands.CLEAR_285.toString());
                        System.out.println("[Server ATG SP41] Connection Message Else: " + recive);
                    }
                }
                if (recive==null || recive.isEmpty())
                    routerResponseListener.OnATGReceivedLK("");
                send285ATGCommand(Commands.CLEAR_285.toString());
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter285Aborted();
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[ServerATG 232] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                        routerResponseListener.OnRouter285Aborted();
                    } else {
                        //Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[ServerATG 232] Successfully end connection" + ex.getLocalizedMessage());
                ex.printStackTrace();
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
        System.out.println("ATGASCII = " + ascii);
        System.out.println("ATGHex = " + builder.toString());

        return builder.toString();
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
        return new String(hexChars);
    }

    public void send285ATGCommand(String command) {

        if (ServerATG285.getSocket() != null) {
            Util.writeAll(ServerATG285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    Log.d("Server ATG - writing232", command + "");
                }
            });
        }
    }


}
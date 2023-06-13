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
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.LogToFile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server285Atg {



    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private final RouterResponseListener routerResponseListener;
    private InetAddress host;
    private int port;
    private static AsyncSocket socket;
    private static AsyncServer asyncServer;

    public Server285Atg(String host, int port, RouterResponseListener routerResponseListener) {
        try {
            this.host = InetAddress.getByName(host);
            this.routerResponseListener = routerResponseListener;
            Log.e("TAG", host);
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

        asyncServer = new AsyncServer("server232");
        asyncServer.listen(host, port, new ListenCallback() {

            @Override
            public void onAccepted(final AsyncSocket socket) {
                handleAccept(socket);
                Server285Atg.socket = socket;
                send285Command("#*RL10"); //Commented by Laukendra on 02-01-2020
            }

            @Override
            public void onListening(AsyncServerSocket socket) {
                System.out.println("[Server 232] Server started listening for connections");
            }

            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    if (ex instanceof IOException) {
                        routerResponseListener.OnRouter285Aborted();
                        if (AsyncServer.getCurrentThreadServer().isRunning()) {
                            AsyncServer.getCurrentThreadServer().stop();
                        }
                    } else {
                        Log.d("serverPortError", ex.getLocalizedMessage());
                    }
                }
                System.out.println("[Server 232] Successfully shutdown server");
                ex.printStackTrace();
            }
        });
    }

    private void handleAccept(final AsyncSocket socket) {

        System.out.println("[Server 232] New Connection " + socket.toString());
        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                byte[] bytesData = bb.getAllByteArray();
                String recive = new String(bytesData);

                if (recive.contains("RS_232")) {

                    routerResponseListener.OnRouter285Connected();

                }
                if (recive != null) {
                    if (convertASCIItoHEX(recive).contains("550252"))
                        routerResponseListener.OnRfIdReceived(convertASCIItoHEX(recive));
                    if (recive.contains("SP31") || recive.contains("SP41"))
                        recive=bytesToHex(bytesData);
                        routerResponseListener.OnATGReceivedLK(recive);
                    if (SharedPref.getLoginResponse().getData().get(0).getAtgDataList()!=null&&SharedPref.getLoginResponse().getData().get(0).getAtgDataList().size()>0) {
                        {
                            if (recive.contains(SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_atg_serial_no()))
                                routerResponseListener.OnATGReceivedLK(recive);
                        }
                    }
                    else{
                        Log.e("atgListSI","atgSize0");
                    }
                }
                System.out.println("[Server 232 Main] Received Message " + recive);
                //Log.e("[Server 232]", "Received Message " + recive);
                LogToFile.LogMessageToFile("[Server 232]:" + recive + "");
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
                System.out.println("[Server 232] Successfully closed connection");
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
                System.out.println("[Server 232] Successfully end connection" + ex.getLocalizedMessage());
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
        System.out.println("ASCII = " + ascii);
        System.out.println("Hex = " + builder.toString());

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

    public void send285Command(String command) {
        if (Server285.getSocket() != null) {
            Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("writing232", command + "");
//                        }
//                    });
                }
            });
        }
    }





}

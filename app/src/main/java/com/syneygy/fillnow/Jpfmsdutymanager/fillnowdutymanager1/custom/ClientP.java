package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.Client;

import java.net.InetSocketAddress;

public class ClientP {
    private String host;
    private int port;
    public static AsyncSocket socket;
    ClientP.ReciverListner reciverListner;

    public ClientP(String host, int port,ClientP.ReciverListner reciverListner) {
        this.host = host;
        this.port = port;
        this.reciverListner=reciverListner;

        setup();
    }

    private void setup() {
        AsyncServer.getDefault().connectSocket(new InetSocketAddress(host, port), new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
                ClientP.socket=socket;
                handleConnectCompleted(ex, socket);
            }
        });
    }

    public static AsyncSocket getSocket(){
       return socket;
    }

    private void handleConnectCompleted(Exception ex, final AsyncSocket socket) {
        if(ex != null) throw new RuntimeException(ex);

        Util.writeAll(socket, "Hello Server".getBytes(), new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully wrote message");
            }
        });

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
//                System.out.println("[Client] Received Message " + new String(bb.getAllByteArray()));
                String asccivalue="";
                String hexValue=encodeHexString(bb.getAllByteArray());
                asccivalue=hextoString(hexValue);
                System.out.println("[Client] Received Message1 " + asccivalue+","+hexValue);
                reciverListner.RecivedData(asccivalue,hexValue,true);
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully end connection");
            }
        });
    }

    private String hextoString(String hex) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            String str = hex.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public interface ReciverListner {
        void RecivedData(String responseAscci, String responseHex, boolean isConnected);
    }

    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }


}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.Command285Queue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Server285;


public class LockFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private TextView lockStatus, openLock, closeLock, levelLock, latLongLock, engineStatusLock, setUrl, setDeviceNo, setApn,setDelay,restartModem;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lock, container, false);
        getIds(view);
        return view;
    }

    private void getIds(View view) {
        lockStatus = view.findViewById(R.id.stausLock);
        openLock = view.findViewById(R.id.openLock);
        closeLock = view.findViewById(R.id.closeLock);
        levelLock = view.findViewById(R.id.levelLock);
        latLongLock = view.findViewById(R.id.latLongLock);
        engineStatusLock = view.findViewById(R.id.engineStatus);
        setUrl = view.findViewById(R.id.setUrl);
        setDeviceNo = view.findViewById(R.id.deviceNameLock);
        setApn = view.findViewById(R.id.setApn);
        setDelay = view.findViewById(R.id.setDelay);
        restartModem = view.findViewById(R.id.restartModem);

        lockStatus.setOnClickListener(this);
        openLock.setOnClickListener(this);
        closeLock.setOnClickListener(this);
        levelLock.setOnClickListener(this);
        latLongLock.setOnClickListener(this);
        engineStatusLock.setOnClickListener(this);
        setUrl.setOnClickListener(this);
        setDeviceNo.setOnClickListener(this);
        setApn.setOnClickListener(this);
        setDelay.setOnClickListener(this);
        restartModem.setOnClickListener(this);
    }

    public void send285Command(String command) {

        if (Server285.getSocket() != null) {
            Util.writeAll(Server285.getSocket(), command.getBytes(), new CompletedCallback() {
                @Override
                public void onCompleted(Exception ex) {
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("writing232", command + "");
                            // Log.e("ATG-Res", ex.toString());
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stausLock:
                send285Command("#*SP21*#");
                getLockResponse("#$LS000479$#");
                break;
            case R.id.openLock:

                send285Command("#*SP21*#");
                send285Command("#$LO000479$#");
                break;
            case R.id.closeLock:
                send285Command("#*SP21*#");
                send285Command("#$LC000479$#");
                break;
            case R.id.levelLock:

                send285Command("#*SP21*#");
                getLockResponse("#$AR000479$#");
                break;
            case R.id.latLongLock:
                send285Command("#*SP21*#");
                getLockResponse("#$GP000479$#");
                break;
            case R.id.engineStatus:
                send285Command("#*SP21*#");
                send285Command("#$ES000479$#");
                break;
            case R.id.setUrl:
                send285Command("#*SP21*#");
                setUrlDialog();
                break;
            case R.id.deviceNameLock:
                send285Command("#*SP21*#");
                setDeviceName();
                break;
            case R.id.setApn:
                send285Command("#*SP21*#");
                setApnLock();
                break;
                case R.id.setDelay:
                    send285Command("#*SP21*#");
               setDelayDialog();
                break;
                case R.id.restartModem:
                    send285Command("#*SP21*#");
                getLockResponse("#$RS000479$#");
                break;
        }
    }

    private void setDelayDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("SET Delay");
        final EditText input = new EditText(context);
        input.setHint("Enter Delay Seconds");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void setApnLock() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("SET ACCESS POINT NAME");
        final EditText input = new EditText(context);
        input.setHint("Enter ACCESS POINT NAME");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void setDeviceName() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Change Device No.");
        final EditText input = new EditText(context);
        input.setHint("Enter Device No.");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void setUrlDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Change Device Url");
        final EditText input = new EditText(context);
        input.setHint("Enter Url");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getLockResponse("");
                dialog.dismiss();
            }
        });


        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
    }


    private void getLockResponse(String s) {
        String[] st = {s};
        Command285Queue commandQueue7 = new Command285Queue(st, new OnAllCommandCompleted() {
            @Override
            public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                Log.e("LockStatus", lastResponse);
            }

            @Override
            public void onAllCommandCompleted(int currentCommand, String response) {
                Log.e("LockStatus", response);
            }
        });
        commandQueue7.doCommandChaining();
    }

}
package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.VehicleDatum;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;


/**
 * Created by Ved Yadav on 2/3/2019.
 */
public class SettingsActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private EditText tankType, driverName, noOfTank, terminalNo, vechicalId, serverIp, portNo, frenchies, gst, gps, footerMsg, volumeTot, ammountTOt;
    private EditText gpsMismatch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        driverName = findViewById(R.id.driverName);
        noOfTank = findViewById(R.id.noOfTank);
        terminalNo = findViewById(R.id.terminalNo);
        vechicalId = findViewById(R.id.vechicalId);
        serverIp = findViewById(R.id.serverIp);
        portNo = findViewById(R.id.portNo);
        driverName = findViewById(R.id.driverName);
        frenchies = findViewById(R.id.frenchies);
        gst = findViewById(R.id.gst);
        gps = findViewById(R.id.gps);
        footerMsg = findViewById(R.id.footerMsg);
        tankType = findViewById(R.id.tankType);
        gpsMismatch = findViewById(R.id.gpsMismatch);
        volumeTot = findViewById(R.id.volumeTot);
        ammountTOt = findViewById(R.id.ammountTot);



        setUpVehicle();
        if (SharedPref.getActiveSettings() != null) {
            try {
                setNestedFieldsJsonValue((ViewGroup) findViewById(R.id.settingLay), new JSONObject(SharedPref.getActiveSettings()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        serverIp.setText(SharedPref.getServerIp());
        findViewById(R.id.saveSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonObject = new JSONObject();
                SharedPref.setVolTotlizer(volumeTot.getText().toString());
                SharedPref.setAmmountTotlizer(ammountTOt.getText().toString());
                SharedPref.setServerIp(serverIp.getText().toString());
                Log.e("SERVER IP", String.valueOf(serverIp));
                Toast.makeText(SettingsActivity.this, "Settings have been saved!", Toast.LENGTH_SHORT).show();
                SharedPref.setActiveSettings(getNestedFieldsJsonValue((ViewGroup) findViewById(R.id.settingLay)));
                Log.d("JsonData", String.valueOf(getNestedFieldsJsonValue((ViewGroup) findViewById(R.id.settingLay))));

                Intent intent =new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    public JSONObject getNestedFieldsJsonValue(ViewGroup view) {
        try {
            for (int i = 0; i < view.getChildCount(); i++) {
                View v = view.getChildAt(i);
                if (v instanceof EditText) {
                    try {
                        String tag = ((EditText) v).getTag().toString();
                        jsonObject.put(tag, ((EditText) v).getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (v instanceof ViewGroup) {
                    getNestedFieldsJsonValue((ViewGroup) v);
                }
            }
        } catch (NullPointerException e) {
//            Toast.makeText(BrancoApp.getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void setNestedFieldsJsonValue(ViewGroup view, JSONObject jsonObject) {
        try {
            for (int i = 0; i < view.getChildCount(); i++) {
                View v = view.getChildAt(i);
                if (v instanceof EditText) {
                    try {
                        String tag = v.getTag().toString();
                        if (jsonObject.has(tag)) {
                            Log.d("ExploringObject", " " + tag);
                            ((EditText) v).setText(jsonObject.getString(tag));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (v instanceof ViewGroup) {
                setNestedFieldsJsonValue((ViewGroup) v, jsonObject);
                }
            }
        } catch (NullPointerException e) {
//            Toast.makeText(BrancoApp.getContext(), "Something went wrong ,please contact developer", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void setUpVehicle() {
        try {
            if (SharedPref.getLoginResponse() != null && SharedPref.getLoginResponse().getVehicle_data() != null && !SharedPref.getLoginResponse().getVehicle_data().isEmpty()) {
                VehicleDatum vehicle = SharedPref.getLoginResponse().getVehicle_data().get(0);
                driverName.setText(SharedPref.getLoginResponse().getData().get(0).getName());
                noOfTank.setText("4");
//                    terminalNo.setText(vehicle.);
                vechicalId.setText(vehicle.getRegNo());
//                    serverIp.setText(vehicle.);
//                    portNo.setText(vehicle.);
                frenchies.setText(vehicle.getFranchiseeId());
//                    gst.setText(vehicle.g);
                gps.setText(String.format("%s,%s", vehicle.getLatitude(), vehicle.getLongitude()));
//                    footerMsg.setText(vehicle.);
                gpsMismatch.setText(vehicle.getGpsMismatchRange());
                tankType.setText(vehicle.getTypeOfPermit());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHotspotAdress() {
        final WifiManager manager = (WifiManager) super.getApplicationContext().getSystemService(WIFI_SERVICE);
        final DhcpInfo dhcp = manager.getDhcpInfo();
        int ipAddress = dhcp.ipAddress;
        WifiInfo info = manager.getConnectionInfo();
        String ssid = info.getSSID();
        if (ssid != null && !ssid.isEmpty()) {
            Toast.makeText(this, ssid, Toast.LENGTH_SHORT).show();
        }
        ipAddress = (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) ?
                Integer.reverseBytes(ipAddress) : ipAddress;
        byte[] ipAddressByte = BigInteger.valueOf(ipAddress).toByteArray();
        try {
            InetAddress myAddr = InetAddress.getByAddress(ipAddressByte);
            return myAddr.getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            Log.e("Wifi Class", "Error getting Hotspot IP address ", e);
        } catch (Exception e) {
            Toast.makeText(this, "Please check permission section under Android Setting/Apps", Toast.LENGTH_SHORT).show();
        }
        return "null";
    }
}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.constant.AppConstants;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.custom.TCPClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentForgotPassword;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.LocationUtil;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.PInfo;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.PreferenceManager;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final Double INTERPOLATION_VALUE_OF = 16.0;
    private static Context mContext;
    private String LOGO_IMG;
    private EditText userEmail, userPasswod;
    private View btnLogin;
    private static String TAG = LoginActivity.class.getSimpleName();
    String user, pass;
    private ProgressBar pgsBar;
    private EditText odometer_reading, locationTrackingUrl;
    private String location;
    private ImageView printTest;
    private TextView forgetPassword;
    private String commandsToPrint;
    private ProgressBar mPbLogin;
    private TCPClient tcpClient;
    private Client client1;
    InetAddress inetAddress;

    private static final int PERMISSION_REQUEST_CODE = 10101;
    String[] appPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_new);
        TextView tvVersionCode= findViewById(R.id.versionCode);
        mContext = LoginActivity.this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.v("VERSION_NAME","TEST"+version);
            tvVersionCode.setText("Version Code :"+" "+version);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("tag", e.getMessage());
        }
        final String s = "1,605.00".replace(",", " ");

//        UPISecurity upiSecurity=new UPISecurity();
//        try {
//            //String message="YES0000000592667|Test123123|test|100|INR|P2P|Pay|1520||||||komal@yesb|||||UPI|BuyNswipe||||||VPA|||||||||NA|NA";
//            //String encry_key="3b2359e6f53f65725b68e660dadd2379";
//            //String message="YES0000000050090|Test123123|test|100|INR|P2P|Pay|1520||||||komal@yesb|||||UPI|BuyNswipe||||||VPA|||||||||NA|NA";
//            String message="YES0000000050090|100|jagjeet@yesb|510|TEST|NA|12|1234|000390100000202|YESB0000009|919595989586|9485624|769876897654|rohit@yesb|000390100000101|YESB0000001|269276297654|919595989586|9485831|NA|NA|NA|NA|NA|NA|NA|NA|NA|NA";
//            String encry_key="05c3018d55c37a283e17036a2e6b5e1d";
//            //String EncryptedMesage=upiSecurity.encrypt(message,encry_key);
//            String EncryptedMesage="96BD75CF48B60BF8E729114FE93B5665ABAAC459794FD737C13AF319A53D15005996C28A6A7B44EB5BA276C594E2CC12DDB1021A8A4A75AC659A90E5DD9DA9572704AF5F351F08998D49AA2696FB21780AEE73BDB1B97DF1F3D426707A6C6165B985544D5498E8B684FD4749F594310F";
//            Log.e("EncryptedMesage",EncryptedMesage);
//            String DecryptedMesage=upiSecurity.decrypt(EncryptedMesage,encry_key);
//            Log.e("DecryptedMesage",DecryptedMesage);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("Exception-UPISecurity","Occured");
//        }


        userEmail = findViewById(R.id.user_email);
        userPasswod = findViewById(R.id.user_password);
        odometer_reading = findViewById(R.id.odometer_reading);
        locationTrackingUrl = findViewById(R.id.locationTrackingUrl);
        mPbLogin = findViewById(R.id.pb);
        printTest = findViewById(R.id.print_test);
        forgetPassword = findViewById(R.id.forgetPassword);


        if (SharedPref.getLoginResponse() != null) {
            userEmail.setText(SharedPref.getLoginResponse().data.get(0).getUsername());

        }

        btnLogin = findViewById(R.id.login_button);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(View.GONE);
        PreferenceManager.init(getApplicationContext());
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Forget", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, FragmentForgotPassword.class);
                startActivity(intent);
            }
        });
        final WifiManager manager = (WifiManager) super.getApplicationContext().getSystemService(WIFI_SERVICE);
        try {
            WifiInfo info = manager.getConnectionInfo();
            String ssid = info.getSSID();
            if (ssid.trim().toLowerCase().contains("synergy")) {
                Toast.makeText(this, "You are connected to Synergy Wifi", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You are not connected to Synergy Wifi", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkAndRequestPermission();
        PInfo installedInfo = new PInfo(this);
        installedInfo.getPackages();
        LocationUtil.parseNmeaSentence("$GPGGA,085440.00,2836.96721,N,07723.27090,E,2,11,0.69,195.5,M,-40.2,M,,0000*77\r\n");

//        try {
//            ArrayList<Double> data = new DataBaseHelper(LoginActivity.this).getValues(INTERPOLATION_VALUE_OF);
//            if (data.size() > 0) {
//                double interpola = (((data.get(3) - data.get(1)) / (data.get(2) - data.get(0))) * (INTERPOLATION_VALUE_OF - data.get(0))) + data.get(1);
//                Log.d(LoginActivity.class.getSimpleName(), String.format("Volume y1=%.2f , Volume y2=%.2f , Depth x1=%.2f , Depth x2=%.2f , interpolation=%.2f", data.get(1), data.get(3), data.get(0), data.get(2), interpola));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SettingsActivity.class));
            }
        });


        printTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                pgsBar.setVisibility(View.VISIBLE);
                btnLogin.setPressed(true);
                user = userEmail.getText().toString().trim();
                pass = userPasswod.getText().toString().trim();
                location = locationTrackingUrl.getText().toString().trim();
                String odometer = odometer_reading.getText().toString().trim();
                Log.e("Username", user);
                Log.e("Password", pass);
                try {
                    Log.d("TerminalId", SharedPref.getTerminalID());
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Please setup terminal Id in settings", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (!user.equals("")) {
                    if (!pass.equals("")) {
                        if (checkAndRequestPermission()) {
                            hitLogin(user, pass, SharedPref.getTerminalID(), location, odometer);
                        }
                    } else {
                        pgsBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "enter your password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pgsBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "enter your username", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void hitLogin(String user, String pass, String terminalID, String location, String odometer) {

        if (Utils.isNetworkConnected(LoginActivity.this)) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("email", user);
            hashMap.put("password", pass);
            hashMap.put("terminal_id", terminalID);
            hashMap.put("odometReading", odometer);
            hashMap.put("Location_Tracking_URL", location);

            if (Utils.isNetworkConnected(LoginActivity.this)) {
                ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                apiInterface.getLogin(hashMap).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        pgsBar.setVisibility(View.GONE);
                        call.request().url();
                        try {
                            if (response.isSuccessful() && response.body().getSucc()) {
                                SharedPref.setUserLoginCredential(response.body());

                                SharedPref.setMakeOfDispenser(response.body().getVehicle_data().get(0).getMakeOfDispenser());
                                SharedPref.setMakeOfFCC(response.body().getVehicle_data().get(0).getMakeOfFcc());
                                SharedPref.setMustId(response.body().getVehicle_data().get(0).getNoOfAtg());
                                SharedPref.setVehicleId(response.body().getData().get(0).getVehicle_id());
                                SharedPref.setLogineId(response.body().getData().get(0).getLogin_id());
                                SharedPref.setMakeOfAtg(response.body().getVehicle_data().get(0).getMakeOfAtg());
                                PreferenceManager.write(AppConstants.Vehicle_id, response.body().getData().get(0).getVehicle_id());
                                     Intent i = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                                    i.putExtra(AppConstants.Vehicle_id, response.body().getData().get(0).getVehicle_id());
                                    startActivity(i);
//                                if(response.body().getData().get(0).getAtgDataList().isEmpty()){
//                                    Log.e("call","SONAL");
//                                    SharedPref.setAtgDataFound("false");
//                                    Intent i = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
//                                    i.putExtra(AppConstants.Vehicle_id, response.body().getData().get(0).getVehicle_id());
//                                    startActivity(i);
//                                }
//                                else {
//                                    try {
//                                        //  String string=SharedPref.getLoginResponse().getData().get(0).atgDataList.get(0).getData_volume();
//
//
//                                        String string = response.body().getData().get(0).getAtgDataList().get(0).getData_volume();
//                                        SharedPref.setAtgDataFound("true");
//                                        if (string != null) {
//                                            JSONObject jsonObject = new JSONObject(string);
//                                            Log.d("ATG-Table-JSON", jsonObject.toString());
//                                            LinkedHashMap<String, Object> linkedHashMap = new Gson().fromJson(jsonObject.toString(), LinkedHashMap.class);
//                                            Log.d("ATG-Table-LinkHashMap", linkedHashMap.toString());
//                                        }
//                                    } catch (JSONException e) {
//                                        Log.e("ATG-Table-EXc", "Occured");
//                                        e.printStackTrace();
//                                    }

//                                }



                            }else {
                                pgsBar.setVisibility(View.GONE);
                                try {
                                    Toast.makeText(LoginActivity.this, "" + response.body().getErrCodes().get(0), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, " " + e.getMessage());
                            e.printStackTrace();
                            pgsBar.setVisibility(View.GONE);
                            Log.e("Failure","Response"+e);
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e(TAG, " " + t.getMessage());
                        pgsBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(mContext, "Please check internet connection", Toast.LENGTH_LONG).show();
            }
        } else {
            pgsBar.setVisibility(View.GONE);
            Toast.makeText(this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }




    public boolean checkAndRequestPermission() {
        List<String> listPermisssionsAsked = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermisssionsAsked.add(perm);
            }
        }

        if (!listPermisssionsAsked.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermisssionsAsked.toArray(new String[listPermisssionsAsked.size()]),
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }


            if (deniedCount == 0) {
                initApp();
            } else {

                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        showPermissionDialog("Permission Request",
                                "Application need Storage permission to continue",
                                "Yes, Grant Permission", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        checkAndRequestPermission();
                                    }
                                }, "No, Exit App", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }, false);
                    } else {
                        showPermissionDialog("Permission Request",
                                "You have denied some of requested permission. Please Allow at [Setting] > [Permissions]",
                                "Go to Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                "No, Exit App", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();

                                    }
                                }

                                , false);
                        break;
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public AlertDialog showPermissionDialog(String title, String msg,
                                            String positiveBtn, DialogInterface.OnClickListener positiveOnClickListener,
                                            String negativeBtn, DialogInterface.OnClickListener negativeOnClickListener, boolean isCancelAble) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(isCancelAble);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveBtn, positiveOnClickListener);
        builder.setNegativeButton(negativeBtn, negativeOnClickListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return alertDialog;
    }

    private void initApp() {

    }
}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db.AppDatabase;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.OfflineOrderDetail;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PrintData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.RejectOrder;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.services.SaveGoLocalOrderOfflineService;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.NetworkStatusClass;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.printer.PrinterCommands;
import com.syneygy.fillnow.Jpfmsdutymanager.printer.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.DispenserUnitActivity.outputStream;

public class FreshDispenseActivity extends BaseActivity implements View.OnClickListener {

    private String mAthenticationCode = "";
    private TextView mRegularDispense, mGoLocalDispense, mSubmit, mShowStoredOrders,tv_submitl;
    private View mDispeneMode;
    private Boolean isRegularDispense = true;
    private Animation slideUp, fadeIn, fadeOut;
    private Context context;
    private List<OfflineOrderDetail> submitOrderList = new ArrayList<>();
    private List<OfflineOrderDetail> showOrderList = new ArrayList<>();
    private String startTime;
    private AppDatabase appDatabase;
    private TextView tvLocationID, tvPresetQty;
    private ProgressDialog progressDialog;
    private EditText et_location_id, et_fueling_start_time, et_fueling_stop_time, et_dispense_qty, et_meter_reading, et_asset_other_reading, et_asset_other_reading_2, et_atg_tank_start_reading, et_atg_tank_end_reading, et_volume_totalizer, et_no_of_event_start_stop, et_dispensed_in, et_batch_no, et_transaction_type, et_transaction_no, et_transaction_id, et_assets_id, et_rfid_status, et_terminal_id, et_latitude, et_longitude, et_location_reading_1, et_location_reading_2, et_gps_status, et_dcv_status, et_flag, et_unit_price, et_vehicle_id, et_total_qty, et_order_id, et_duty_id;
    private EditText et_gst,et_total_price,et_terminal_idl;
    private Calendar date;
    private boolean isStartTime=true; //True for StartTime //false for EndTime

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh_dispense_start);
        Log.e("ON_CREATE","ON CREATE3");
        context = this;
        mAthenticationCode = SharedPref.getLoginResponse().getData().get(0).getRandomCode() + "";
        appDatabase = BrancoApp.getDb();
        findViewByIds();
        showRegularDispenseForm();
        //showAlert("Enter Verification Code.");
        mRegularDispense.setOnClickListener(this);
        mGoLocalDispense.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mShowStoredOrders.setOnClickListener(this);
//        et_vehicle_id = findViewById(R.id.tv_unit_price);
//        et_duty_id = findViewById(R.id.tv_unit_price);
//        et_terminal_id = findViewById(R.id.tv_fueling_start_time);
//        et_transaction_type = findViewById(R.id.tv_unit_price);
//        et_transaction_id = findViewById(R.id.tv_unit_price);
//        et_transaction_no = findViewById(R.id.tv_fueling_start_time);
//        et_order_id = findViewById(R.id.tv_fueling_start_time);
//        et_flag = findViewById(R.id.tv_unit_price);
//

        et_location_id = findViewById(R.id.tv_location_id);
        et_fueling_start_time = findViewById(R.id.tv_fueling_start_time);
        et_fueling_stop_time = findViewById(R.id.tv_fueling_stop_time);
        et_dispense_qty = findViewById(R.id.tv_dispense_qty);
        et_unit_price = findViewById(R.id.tv_unit_price);
        et_gst = findViewById(R.id.tv_gst_percentage);
        et_total_price = findViewById(R.id.tv_total_amount);
        et_assets_id = findViewById(R.id.tv_asset_id);
        et_batch_no = findViewById(R.id.tv_batch_no);
        et_meter_reading = findViewById(R.id.tv_meter_reading);
        et_asset_other_reading = findViewById(R.id.tv_asset_other_reading);
        et_asset_other_reading_2 = findViewById(R.id.tv_asset_other_reading_2);
        et_atg_tank_start_reading = findViewById(R.id.tv_atg_tank_start_reading);
        et_atg_tank_end_reading = findViewById(R.id.tv_atg_tank_end_reading);
        et_volume_totalizer = findViewById(R.id.tv_volume_totalizer);
        et_no_of_event_start_stop = findViewById(R.id.tv_no_of_event_start_stop);
        et_latitude = findViewById(R.id.tv_latitude);
        et_longitude = findViewById(R.id.tv_longitude);
        et_location_reading_1 = findViewById(R.id.tv_location_reading_1);
        et_location_reading_2 = findViewById(R.id.tv_location_reading_2);

        et_fueling_start_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isStartTime=true;
                showDateTimePicker();
                return false;
            }
        });
        et_fueling_stop_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isStartTime=false;
                showDateTimePicker();
                return false;
            }
        });

        et_dispense_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    String strUnitPrice = et_unit_price.getText().toString();
                    if (!strUnitPrice.isEmpty()) {
                        String strGst = et_gst.getText().toString();
                        if (!strGst.isEmpty()){
                            float gst=Float.parseFloat(strGst);
                            float unitPrice=Float.parseFloat(strUnitPrice);
                            float dispenseQty=Float.parseFloat(s.toString());
                            float totalAmount=dispenseQty*unitPrice+(dispenseQty*unitPrice)*gst/100;
                            et_total_price.setText(String.format("%.2f",totalAmount));
                        }else et_total_price.setText(String.format("%.2f",0.00));
                    }else et_total_price.setText(String.format("%.2f",0.00));
                }else et_total_price.setText(String.format("%.2f",0.00));
            }
        });


        et_unit_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    String strDispenseQty = et_dispense_qty.getText().toString();
                    if (!strDispenseQty.isEmpty()) {
                        String strGst = et_gst.getText().toString();
                        if (!strGst.isEmpty()){
                            float dispenseQty=Float.parseFloat(strDispenseQty);
                            float gst=Float.parseFloat(strGst);
                            float unitPrice=Float.parseFloat(s.toString());
                            float totalAmount=dispenseQty*unitPrice+(dispenseQty*unitPrice)*gst/100;
                            et_total_price.setText(String.format("%.2f",totalAmount));
                        }else et_total_price.setText(String.format("%.2f",0.00));
                    }else et_total_price.setText(String.format("%.2f",0.00));
                }else et_total_price.setText(String.format("%.2f",0.00));
            }
        });

        et_gst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    String strDispenseQty = et_dispense_qty.getText().toString();
                    if (!strDispenseQty.isEmpty()) {
                        String strUnitPrice = et_unit_price.getText().toString();
                        if (!strUnitPrice.isEmpty()){
                            float dispenseQty=Float.parseFloat(strDispenseQty);
                            float unitPrice=Float.parseFloat(strUnitPrice);
                            float gst=Float.parseFloat(s.toString());
                            float totalAmount=dispenseQty*unitPrice+(dispenseQty*unitPrice)*gst/100;
                            et_total_price.setText(String.format("%.2f",totalAmount));
                        }else et_total_price.setText(String.format("%.2f",0.00));
                    }else et_total_price.setText(String.format("%.2f",0.00));
                }else et_total_price.setText(String.format("%.2f",0.00));
            }
        });

    }
    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker viewDate, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker viewTime, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        if (isStartTime)
                            et_fueling_start_time.setText(String.valueOf(date.getTime()));
                        else et_fueling_stop_time.setText(String.valueOf(date.getTime()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }


    private void findViewByIds() {
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        mRegularDispense = findViewById(R.id.tv_regular_dispense);
        mGoLocalDispense = findViewById(R.id.tv_go_local_dispense);
        mSubmit = findViewById(R.id.tv_submit);
        mDispeneMode = findViewById(R.id.lay_fresh_dispense_mode);
        mShowStoredOrders = findViewById(R.id.tv_localdb_order);
        et_terminal_idl = findViewById(R.id.et_terminal_id);
        tv_submitl = findViewById(R.id.tv_submitl);

        tvLocationID = findViewById(R.id.et_location_id);
        tvPresetQty = findViewById(R.id.et_preset_quantity);
        tv_submitl.setOnClickListener(this);
    }

    private void showAlert(String title) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.selected_asset_qnty_dialog, null);
        final EditText et_qnty = alertLayout.findViewById(R.id.et_qnty);
        final TextView done = alertLayout.findViewById(R.id.done);
        final TextView resend = alertLayout.findViewById(R.id.resend);
        resend.setVisibility(View.VISIBLE);
        AlertDialog.Builder alert = new AlertDialog.Builder(FreshDispenseActivity.this);
        alert.setTitle(title);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_qnty.length() <= 0) {
                    Toast.makeText(FreshDispenseActivity.this, "Please submit valid verification code", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String data = et_qnty.getText().toString();
                    if (data.equalsIgnoreCase(mAthenticationCode)) {
                        dialog.dismiss();
                        mDispeneMode.setVisibility(View.VISIBLE);
//                        showDispenseChoiceAlert("Please choose dispense type");
                    } else {
                        Toast.makeText(FreshDispenseActivity.this, "Please submit valid verification code", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regular_dispense:
                showRegularDispenseForm();
                break;

            case R.id.tv_go_local_dispense:
                showGoLocalForm();
                break;

            case R.id.tv_submit:
                submitDispense();
                break;
                case R.id.tv_submitl:
                submitDispense();
                break;

            case R.id.tv_localdb_order:
                try {
//                    List<OfflineOrderTableData> orderOffline = appDatabase.offlineOrderDao().getAll();
//                    if (orderOffline.size() > 0) {
//                        new GetLocalOrder().execute();
//                    } else {
//                        Toast.makeText(context, "No stored order in database", Toast.LENGTH_LONG).show();
//                    }
                } catch (Exception e) {
                    Toast.makeText(context, "No stored order in database", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void showRegularDispenseForm() {
        isRegularDispense = true;
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        findViewById(R.id.ll_golocal_dispense_form).setVisibility(View.GONE);
        findViewById(R.id.ll_golocal_dispense_form).setAnimation(fadeOut);
        mRegularDispense.setBackgroundColor(ContextCompat.getColor(this, R.color.md_green_300));
        mGoLocalDispense.setBackgroundColor(ContextCompat.getColor(this, R.color.color_blue));
        findViewById(R.id.ll_regular_dispense_form).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_regular_dispense_form).setAnimation(fadeIn);

    }

    private void showGoLocalForm() {
        isRegularDispense = false;
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        findViewById(R.id.ll_regular_dispense_form).setVisibility(View.GONE);
        findViewById(R.id.ll_regular_dispense_form).setAnimation(fadeOut);
        mRegularDispense.setBackgroundColor(ContextCompat.getColor(this, R.color.color_blue));
        mGoLocalDispense.setBackgroundColor(ContextCompat.getColor(this, R.color.md_green_200));
        findViewById(R.id.ll_golocal_dispense_form).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_golocal_dispense_form).setAnimation(fadeIn);


    }

    public void submitDispense() {
        if (isRegularDispense) {
            String strLocationId = tvLocationID.getText().toString();
            String strPresetQty = tvPresetQty.getText().toString();
            if (!strLocationId.isEmpty() && !strPresetQty.isEmpty() && Integer.valueOf(strPresetQty) > 0) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Loading... ");
                progressDialog.show();
                getLocationAndVehicleData(strLocationId, strPresetQty);
            } else {
                Toast.makeText(context, "Please Enter Valid Data", Toast.LENGTH_LONG).show(); //Updated Text by Laukendra
            }
        } else {
            Toast.makeText(context, "Go Local", Toast.LENGTH_SHORT).show();

//            saveData();

            String str_location_id = et_location_id.getText().toString().trim();
            String str_fueling_start_time = et_fueling_start_time.getText().toString().trim();
            String str_fueling_stop_time = et_fueling_stop_time.getText().toString().trim();
            String str_dispense_qty = et_dispense_qty.getText().toString().trim();
            String str_unit_price = et_unit_price.getText().toString().trim();
            String str_gst_percentage = et_gst.getText().toString().trim();
            float amount=Float.parseFloat(str_unit_price) * Float.parseFloat(str_dispense_qty);
            String str_amount = String.format("%.2f", amount);
            String str_total_amount = et_total_price.getText().toString().trim();
            String str_assets_id = et_assets_id.getText().toString().trim();
            String str_assets_name = et_assets_id.getText().toString().trim();
            String str_batch_no = et_batch_no.getText().toString().trim();
            String str_meter_reading = et_meter_reading.getText().toString().trim();
            String str_asset_other_reading = et_asset_other_reading.getText().toString().trim();
            String str_asset_other_reading_2 = et_asset_other_reading_2.getText().toString().trim();
            String str_atg_tank_start_reading = et_atg_tank_start_reading.getText().toString().trim();
            String str_atg_tank_end_reading = et_atg_tank_end_reading.getText().toString().trim();
            String str_volume_totalizer = et_volume_totalizer.getText().toString().trim();
            String str_no_of_event_start_stop = et_no_of_event_start_stop.getText().toString().trim();
            String str_latitude = et_latitude.getText().toString().trim();
            String str_longitude = et_longitude.getText().toString().trim();
            String str_location_reading_1 = et_location_reading_1.getText().toString().trim();
            String str_location_reading_2 = et_location_reading_2.getText().toString().trim();

            String str_dispensed_in = "N/A";
            String str_rfid_status = "ByPass";
            String str_gps_status = "ByPass";
            String str_dcv_status = "ByPass";

            if (!str_location_id.isEmpty()&&!str_fueling_stop_time.isEmpty()&&!str_dispense_qty.isEmpty()&&!str_unit_price.isEmpty()&&!str_amount.isEmpty()&&!str_gst_percentage.isEmpty()
                    &&!str_total_amount.isEmpty()&&!str_assets_id.isEmpty()&&!str_meter_reading.isEmpty()&&!str_asset_other_reading.isEmpty()&&!str_asset_other_reading_2.isEmpty() )
            {

                double timeInMillis = new Date().getTime();
                PrintData dataToPrint = new PrintData();
                dataToPrint.setVehicle_id(SharedPref.getVehicleId());
                dataToPrint.setTerminal_id(SharedPref.getTerminalID());
                dataToPrint.setTransaction_no(SharedPref.getTerminalID() + timeInMillis);
                dataToPrint.setTransaction_id(SharedPref.getTerminalID() + timeInMillis);
                dataToPrint.setOrder_id(SharedPref.getTerminalID() + timeInMillis);
                dataToPrint.setTransaction_type("ManualDispense");
                dataToPrint.setDuty_id(SharedPref.getLoginResponse().data.get(0).getDuty_id());
                dataToPrint.setFlag("U");
                dataToPrint.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty()?" Thank You ":SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
                dataToPrint.setOrder_data_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                dataToPrint.setDeliveredBy(SharedPref.getLoginResponse().data.get(0).getName());
                dataToPrint.setLocation_id(str_location_id);//M
                dataToPrint.setFueling_start_time(str_fueling_start_time.isEmpty()?"N/A":str_fueling_start_time);
                dataToPrint.setFueling_stop_time(str_fueling_stop_time);//M
                dataToPrint.setTotal_qty(str_dispense_qty);//M
                dataToPrint.setDispense_qty(str_dispense_qty);//M
                dataToPrint.setUnit_price(str_unit_price);//M
                dataToPrint.setAmount(str_amount);//M
                dataToPrint.setGST(str_gst_percentage);//M
                dataToPrint.setTotal_amount(str_total_amount);//M
                dataToPrint.setAssets_id(str_assets_id.isEmpty()?"N/A":str_assets_id);//M
                dataToPrint.setAssets_name(str_assets_name.isEmpty()?"N/A":str_assets_name);
                dataToPrint.setMeter_reading(str_meter_reading.isEmpty()?"N/A":str_meter_reading);//M
                dataToPrint.setAsset_other_reading( str_asset_other_reading.isEmpty()?"N/A":str_asset_other_reading);//M
                dataToPrint.setAsset_other_reading2( str_asset_other_reading_2.isEmpty()?"N/A":str_asset_other_reading_2);//M
                dataToPrint.setAtg_tank_start_reading( str_atg_tank_start_reading.isEmpty()?"N/A":str_atg_tank_start_reading);
                dataToPrint.setAtg_tank_end_reading( str_atg_tank_end_reading.isEmpty()?"N/A":str_atg_tank_end_reading);
                dataToPrint.setVolume_totalizer( str_volume_totalizer.isEmpty()?"N/A":str_volume_totalizer);
                dataToPrint.setLatitude( str_latitude.isEmpty()?"N/A":str_latitude);
                dataToPrint.setLongitude( str_longitude.isEmpty()?"N/A":str_longitude);
                dataToPrint.setLocation_reading_1( str_location_reading_1.isEmpty()?"N/A":str_location_reading_1);
                dataToPrint.setLocation_reading_2( str_location_reading_2.isEmpty()?"N/A":str_location_reading_2);
                dataToPrint.setCustomer_name( str_location_id.isEmpty()?"N/A":"LocationId->"+str_location_id); //No Need
                dataToPrint.setLocationName(str_location_id.isEmpty()?"N/A":"LocationId->"+str_location_id);
                dataToPrint.setDispensed_in( str_dispensed_in.isEmpty()?"N/A":str_dispensed_in); //No Need
                dataToPrint.setBatch_no( str_batch_no.isEmpty()?"N/A":str_batch_no); //No Need
                dataToPrint.setRfid_status( str_rfid_status.isEmpty()?"N/A":str_rfid_status); //No Need
                dataToPrint.setGps_status( str_gps_status.isEmpty()?"N/A":str_gps_status); //No Need
                dataToPrint.setDcv_status( str_dcv_status.isEmpty()?"N/A":str_dcv_status); //No Need
                dataToPrint.setNo_of_event_start_stop( str_no_of_event_start_stop.isEmpty()?"N/A":str_no_of_event_start_stop); //No Need

                printBill(dataToPrint);

                printDispenseParams(dataToPrint);

                if (NetworkStatusClass.isNetworkStatusAvailable(context)) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
                    apiInterface.postFreshDispenseOrderDelivered(getHashMap(dataToPrint)).enqueue(new Callback<LoginResponse>() {

                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Response response1=response;
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("PostOnResponse", response.body().getSucc()+"");
                            if (response.isSuccessful() && response.body().getSucc()) {
                                Log.e("PostSuccResponse", response.body().getSucc()+"");
                                showResponseInDialog(context,"Order Successfully updated",true,true);

                            } else {
                                showResponseInDialog(context,"Something went wrong. Please try again...",false,true);
                                Log.e("PostUnSuccResponse", response.body().getSucc()+"");
                            }

                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            try {
                                showResponseInDialog(context,"Server Error. Please try again...",false,true);
                                Log.e("PostonFailure", t.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    saveGoLocalDispenseDataInSQLiteLocal(dataToPrint);
                    finish();
                }

            }else if (str_location_id.isEmpty()){
                Toast.makeText(context, "Enter Location Id", Toast.LENGTH_SHORT).show();
            }else if (str_fueling_stop_time.isEmpty()){
                Toast.makeText(context, "Enter Fueling Stop Time", Toast.LENGTH_SHORT).show();
            }else if (str_dispense_qty.isEmpty()){
                Toast.makeText(context, "Enter Dispense Quantity", Toast.LENGTH_SHORT).show();
            }else if (str_unit_price.isEmpty()){
                Toast.makeText(context, "Enter Unit Price (Price per litre)", Toast.LENGTH_SHORT).show();
            }else if (str_gst_percentage.isEmpty()){
                Toast.makeText(context, "Enter GST", Toast.LENGTH_SHORT).show();
            }else if (str_total_amount.isEmpty()){
                Toast.makeText(context, "Enter Total Amount", Toast.LENGTH_SHORT).show();
            }else if (str_assets_id.isEmpty()){
                Toast.makeText(context, "Enter Asset Id", Toast.LENGTH_SHORT).show();
            }else if (str_meter_reading.isEmpty()){
                Toast.makeText(context, "Enter Meter Reading", Toast.LENGTH_SHORT).show();
            }else if (str_asset_other_reading.isEmpty()){
                Toast.makeText(context, "Enter Asset Other Reading", Toast.LENGTH_SHORT).show();
            }else if (str_asset_other_reading_2.isEmpty()){
                Toast.makeText(context, "Enter Asset Other Reading 2", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void getLocationAndVehicleData(String strLocationId, String strPresetQty) {
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.createOrder( et_terminal_idl.getText().toString(),strLocationId,strPresetQty).enqueue(new Callback<RejectOrder>() {
            @Override
            public void onResponse(Call<RejectOrder> call, Response<RejectOrder> response) {
                if (response.isSuccessful()){
                    if (response.body().getSucc()){
                        finish();
                        Toast.makeText(context, "Order Created", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RejectOrder> call, Throwable t) {

            }
        });
//        apiInterface.getFreshDispenseLocationData(SharedPref.getVehicleId(), strLocationId).enqueue(new Callback<RegularDispenseVehicleResponse>() {
//            @Override
//            public void onResponse(Call<RegularDispenseVehicleResponse> call, Response<RegularDispenseVehicleResponse> response) {
//
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                }
//
//                if (response.isSuccessful()) {
//                    if (response.body() != null && response.body().getSucc()) {
//                        //Following lines added by Laukendra
//                        List<LocationDatum> locationDatumList = response.body().getDatum().getLocationData();
//                        List<LocationVehicleDatum> vehicleDatumList = response.body().getDatum().getVehicleData();
//                        ArrayList<Asset> assetList = (ArrayList<Asset>) response.body().getDatum().getAssetData();
//                        Price priceData=response.body().getDatum().getPrice();
//                        List<VehicleLiveLocation> vehicleLiveLocationData = response.body().getDatum().getVehicleLiveLocation();
//
//                        Progress progress = new Progress();
//                        progress.setLocationId(locationDatumList.get(0).getLocationId());
//                        progress.setLocationName(locationDatumList.get(0).getLocationName());
//                        progress.setFuelPrice(priceData.getPrice());
//                        progress.setPaymentStatus("");
//                        progress.setDiscount("0");
//                        progress.setDiscountType("");
//                        if (vehicleLiveLocationData.size()>0) {
//                            progress.setCurrentLat(vehicleLiveLocationData.get(0).getLive_vehicle_lat());
//                            progress.setCurrentLong(vehicleLiveLocationData.get(0).getLive_vehicle_lng());
//                        }else {
//                            progress.setCurrentLat(vehicleDatumList.get(0).getLatitude());
//                            progress.setCurrentLong(vehicleDatumList.get(0).getLatitude());
//                        }
//                        progress.setDeliveredData("0");
//                        progress.setValveBypass(false);
//                        progress.setLockBypass(false);
//                        progress.setAtgBypass(false);
//                        progress.setLocationBypass(false);
//                        progress.setFranchiseBypass(false);
//                        progress.setFrCurrentLat(vehicleDatumList.get(0).getLatitude());
//                        progress.setFrCurrentLong(vehicleDatumList.get(0).getLatitude());
//
//                        OrderDetail orderDetail = new OrderDetail();
//                        orderDetail.setTransactionId("FRESH-DISPENSE");
//                        orderDetail.setFlag(""); // I/U
//                        orderDetail.setVehicleId(SharedPref.getLoginResponse().data.get(0).getVehicle_id());
//                        orderDetail.setOrderId("FRESH");
//                        orderDetail.setOrderDate(Calendar.getInstance().getTime() + "");
//                        orderDetail.setOrderType("FRESH");
//                        orderDetail.setFuel("DIESEL");
//                        orderDetail.setLocationId(locationDatumList.get(0).getLocationId());
//                        orderDetail.setLocationName(locationDatumList.get(0).getLocationName());
//                        orderDetail.setQty(strPresetQty);
//                        orderDetail.setStaus("11");
//                        orderDetail.setLoginId(SharedPref.getLoginResponse().data.get(0).getLogin_id());
//                        orderDetail.setCreatedDatatime(Calendar.getInstance().getTime() + "");
//                        orderDetail.setBranchId("BRANCH_ID");
//                        orderDetail.setAddress(locationDatumList.get(0).getAddress());
//                        orderDetail.setLatitude(vehicleDatumList.get(0).getLatitude());
//                        orderDetail.setLogitude(vehicleDatumList.get(0).getLongitude());
//                        orderDetail.setContactPersonName(locationDatumList.get(0).getContactPersonName());
//                        orderDetail.setContactPersonPhone(locationDatumList.get(0).getContactPersonPhone());
//                        orderDetail.setPerformaInvoiceNo("FRESH-DISPENSE");
//                        orderDetail.setFname("DIESEL");
//                        orderDetail.setCustomer_name(locationDatumList.get(0).getCustomer_name());
//
////                        Order order = new Order();
////                        order.setOrder_id("OFFLINE");
////                        order.setLogin_id(SharedPref.getLoginResponse().data.get(0).getLogin_id());
////                        order.setLocation_id(locationDatumList.get(0).getLocationId());
////                        order.setLocation_name(locationDatumList.get(0).getLocationName());
////                        order.setStaus("11"); //Delivery In Progress
////                        order.setSlotId(vehicleDatumList.get(0).getTimeSlot());
////                        order.setSlotname("CURRENT");
////                        order.setOrderdate("CURRENT");
////                        order.setOrderType("FRESH");
////                        order.setQuantity(strPresetQty);
////                        order.setCreated_datatime(Calendar.getInstance().getTime() + "");
////                        order.setAddress(locationDatumList.get(0).getAddress());
////                        order.setLatitude(locationDatumList.get(0).getLatitude());
////                        order.setLongitude(locationDatumList.get(0).getLogitude());
////                        order.setOrder_contact_person_name(locationDatumList.get(0).getContactPersonName());
////                        order.setContact_person_phone(locationDatumList.get(0).getContactPersonPhone());
////                        order.setFname("");
////                        order.setFuel("DIESEL");
////                        order.setTransaction_id("" + new Date().getTime());
////                        order.setPerformaId("OFFLINE");
////                        order.setBranchID("BRANCH_ID");
////                        order.setDelivered_data("");
//
//                        List<OrderDetail> orderDetails = new ArrayList<>();
//                        orderDetails.add(orderDetail);
//
//                        //assetList.get(0).setAssetsRfid("0"); //This line added for test purpose. After testing please comment it
//                        assetList.get(0).setAssetsBypassRfid("1"); //This line added for test purpose. After testing please comment it
//
//                        DeliveryInProgress deliveryInProgress = new DeliveryInProgress();
//                        deliveryInProgress.setAssets(assetList);
//                        deliveryInProgress.setProgress(progress);
//                        deliveryInProgress.setOrder(orderDetails);
//
//                        Intent intent = new Intent(context, DispenserUnitActivity.class);
//                        intent.putExtra("freshOrder", deliveryInProgress);
//                        intent.putExtra("isFromFreshDispense", true);
//                        //intent.putExtra("fresh_dispense_preset", strPresetQty);
////                        intent.putExtra("orderDetail", order);
////                        Bundle bundle = new Bundle();
////                        bundle.putSerializable("LocationData", (Serializable) locationDatumList.get(0));
////                        bundle.putSerializable("VehicleData", (Serializable) vehicleDatumList.get(0));
////                        bundle.putSerializable("AssetData", (Serializable) assetList);
////                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        //finish();
//
//                    } else {
//                        Toast.makeText(context, "Hold On....", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegularDispenseVehicleResponse> call, Throwable t) {
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                }
//                t.printStackTrace();
//                Toast.makeText(context, "Server error. Please try again", Toast.LENGTH_LONG).show();
//            }
//        });
//

    }

    public void saveGoLocalDispenseDataInSQLiteLocal(PrintData printData) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OrderDispenseLocalData orderModel = new OrderDispenseLocalData();
                        orderModel.setTransaction_id(printData.getTransaction_id());
                        orderModel.setTransaction_no(printData.getTransaction_no());
                        orderModel.setVehicle_id(printData.getVehicle_id());
                        orderModel.setAssets_id(printData.getAssets_id());
                        orderModel.setMeter_reading(printData.getMeter_reading());
                        orderModel.setAsset_other_reading(printData.getAsset_other_reading());
                        orderModel.setAsset_other_reading_2(printData.getAsset_other_reading2());
                        orderModel.setAtg_tank_start_reading(printData.getAtg_tank_start_reading());
                        orderModel.setAtg_tank_end_reading(printData.getAtg_tank_end_reading());
                        orderModel.setBatch_no(printData.getBatch_no());
                        orderModel.setTerminal_id(printData.getTerminal_id());
                        orderModel.setDispense_qty(printData.getDispense_qty());
                        orderModel.setUnit_price(printData.getUnit_price());
                        orderModel.setFlag(printData.getFlag());
                        orderModel.setDispensed_in(printData.getDispensed_in());
                        orderModel.setDcv_status(printData.getDcv_status());
                        orderModel.setGps_status(printData.getGps_status());
                        orderModel.setFueling_start_time(printData.getFueling_start_time());
                        orderModel.setFueling_stop_time(printData.getFueling_stop_time());
                        orderModel.setLatitude(printData.getLatitude());
                        orderModel.setLongitude(printData.getLongitude());
                        orderModel.setLocation_reading_1(printData.getLocation_reading_1());
                        orderModel.setLocation_reading_2(printData.getLocation_reading_2());
                        orderModel.setVolume_totalizer(printData.getVolume_totalizer());
                        orderModel.setNo_of_event_start_stop(printData.getNo_of_event_start_stop());
                        orderModel.setRfid_status(printData.getRfid_status());
                        orderModel.setTransaction_type(printData.getTransaction_type());
                        orderModel.setTotal_qty(printData.getTotal_qty());
                        orderModel.setOrder_id(printData.getOrder_id());
                        orderModel.setDuty_id(printData.getDuty_id());
                        orderModel.setLocation_id(printData.getLocation_id());
                        orderModel.setCustomer_name(printData.getCustomer_name());
                        orderModel.setFooter_message(SharedPref.getLoginResponse().franchise_data.get(0).footerMessage.isEmpty()?" Thank You ":SharedPref.getLoginResponse().franchise_data.get(0).footerMessage);
                        orderModel.setDeliveredBy(SharedPref.getLoginResponse().getData().get(0).getName());
                        orderModel.setGst_percentage("0"); //Need GST Value Which API will provide it
                        orderModel.setTotal_amount(String.format("%.2f",Float.parseFloat(printData.getTotal_amount())));

                        Intent intent = new Intent(context, SaveGoLocalOrderOfflineService.class);
                        intent.putExtra("orderDetail", orderModel);
                        intent.putExtra("process_type", "GO_LOCAL");
                        context.startService(intent);

                        //Need to call a API to submit order on server side too.

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printBill(PrintData printData) {

        if (printData != null) {
            if (NavigationDrawerActivity.btsocket == null) {
                Toast.makeText(context, "Please Connect Printer to print Bill", Toast.LENGTH_SHORT).show();
            } else {
                //Added/Updated following lines by Laukendra on 15-11-19
                int gstPercentage = 0;
                if (SharedPref.getLoginResponse().getVehicle_data().get(0).getProductName().equalsIgnoreCase("DIESEL")) {
                    gstPercentage = 0; //custom value
                } else {
                    gstPercentage = 12;
                }
                double gstAmount = Double.parseDouble(printData.getAmount()) * gstPercentage / 100;
                printData.setGST(gstPercentage+"");
                printData.setTotal_amount(String.valueOf(Float.parseFloat(printData.getAmount())+gstAmount));
                printTransactionBill(printData,gstPercentage, gstAmount);
                //Added/Updated above lines by Laukendra on 15-11-19
            }
        }
    }

    public void printDispenseParams(PrintData dataToPrint){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fueling_start_time", dataToPrint.getFueling_start_time());
            jsonObject.put("fueling_stop_time", dataToPrint.getFueling_stop_time());
            jsonObject.put("assets_id", dataToPrint.getAssets_id());
            jsonObject.put("dispense_qty", dataToPrint.getDispense_qty());
            jsonObject.put("meter_reading", dataToPrint.getMeter_reading());
            jsonObject.put("asset_other_reading", dataToPrint.getAsset_other_reading());
            jsonObject.put("asset_other_reading_2", dataToPrint.getAsset_other_reading2());
            jsonObject.put("atg_tank_start_reading", dataToPrint.getAtg_tank_start_reading());
            jsonObject.put("atg_tank_end_reading", dataToPrint.getAtg_tank_end_reading());
            jsonObject.put("volume_totalizer", dataToPrint.getVolume_totalizer());
            jsonObject.put("no_of_event_start_stop", dataToPrint.getNo_of_event_start_stop());
            jsonObject.put("dispensed_in", dataToPrint.getDispensed_in());
            jsonObject.put("rfid_status", dataToPrint.getRfid_status());
            jsonObject.put("transaction_type", dataToPrint.getTransaction_type());
            jsonObject.put("terminal_id", dataToPrint.getTerminal_id());
            jsonObject.put("batch_no", dataToPrint.getBatch_no());
            jsonObject.put("latitude", dataToPrint.getLatitude());
            jsonObject.put("longitude", dataToPrint.getLongitude());
            jsonObject.put("location_reading_1", dataToPrint.getLocation_reading_1());
            jsonObject.put("location_reading_2", dataToPrint.getLocation_reading_2());
            jsonObject.put("gps_status", dataToPrint.getGps_status());
            jsonObject.put("dcv_status", dataToPrint.getDcv_status());
            jsonObject.put("flag", dataToPrint.getFlag());
            jsonObject.put("transaction_no", dataToPrint.getTransaction_no());
            jsonObject.put("unit_price", dataToPrint.getUnit_price());
            jsonObject.put("vehicle_id", dataToPrint.getVehicle_id());
            jsonObject.put("transaction_id", dataToPrint.getTransaction_id());
            jsonObject.put("total_qty", dataToPrint.getTotal_qty());
            jsonObject.put("order_id", dataToPrint.getOrder_id());
            jsonObject.put("duty_id", dataToPrint.getDuty_id());
            jsonObject.put("location_id", dataToPrint.getLocation_id());
            jsonObject.put("footer_message", dataToPrint.getFooter_message());
            jsonObject.put("gst_percentage", dataToPrint.getGST());
            jsonObject.put("total_amount", dataToPrint.getTotal_amount());
            Log.d("PostOrderParams", jsonObject.toString());
            System.out.println("PostOrderParams:"+jsonObject.toString());
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    public HashMap getHashMap(PrintData dataToPrint){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fueling_start_time", dataToPrint.getFueling_start_time());
        hashMap.put("fueling_stop_time", dataToPrint.getFueling_stop_time());
        hashMap.put("assets_id", dataToPrint.getAssets_id());
        hashMap.put("dispense_qty", dataToPrint.getDispense_qty());
        hashMap.put("meter_reading", dataToPrint.getMeter_reading());
        hashMap.put("asset_other_reading", dataToPrint.getAsset_other_reading());
        hashMap.put("asset_other_reading_2", dataToPrint.getAsset_other_reading2());
        hashMap.put("atg_tank_start_reading", dataToPrint.getAtg_tank_start_reading());
        hashMap.put("atg_tank_end_reading", dataToPrint.getAtg_tank_end_reading());
        hashMap.put("volume_totalizer", dataToPrint.getVolume_totalizer());
        hashMap.put("no_of_event_start_stop", dataToPrint.getNo_of_event_start_stop());
        hashMap.put("dispensed_in", dataToPrint.getDispensed_in());
        hashMap.put("rfid_status", dataToPrint.getRfid_status());
        hashMap.put("transaction_type", dataToPrint.getTransaction_type());
        hashMap.put("terminal_id", dataToPrint.getTerminal_id());
        hashMap.put("batch_no", dataToPrint.getBatch_no());
        hashMap.put("latitude", dataToPrint.getLatitude());
        hashMap.put("longitude", dataToPrint.getLongitude());
        hashMap.put("location_reading_1", dataToPrint.getLocation_reading_1());
        hashMap.put("location_reading_2", dataToPrint.getLocation_reading_2());
        hashMap.put("gps_status", dataToPrint.getGps_status());
        hashMap.put("dcv_status", dataToPrint.getDcv_status());
        hashMap.put("flag", dataToPrint.getFlag());
        hashMap.put("transaction_no", dataToPrint.getTransaction_no());
        hashMap.put("unit_price", dataToPrint.getUnit_price());
        hashMap.put("vehicle_id", dataToPrint.getVehicle_id());
        hashMap.put("transaction_id", dataToPrint.getTransaction_id());
        hashMap.put("total_qty", dataToPrint.getTotal_qty());
        hashMap.put("order_id", dataToPrint.getOrder_id()==null?new Date().getTime()+"":(dataToPrint.getOrder_id().isEmpty()?new Date().getTime()+"":dataToPrint.getOrder_id()));
        hashMap.put("duty_id", dataToPrint.getDuty_id());
        hashMap.put("location_id", dataToPrint.getLocation_id());
        hashMap.put("footer_message", dataToPrint.getFooter_message());
        hashMap.put("gst_percentage", dataToPrint.getGST());
        hashMap.put("total_amount", dataToPrint.getTotal_amount());
        return hashMap;
    }

    public void showResponseInDialog(Context context, String message, boolean isSuccess, boolean isFreshDispense){

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context);
        builder.setTitle(message);
        android.app.AlertDialog alertDialog=builder.create();
        alertDialog.show();

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog!=null)
                    alertDialog.dismiss();
                if (isSuccess){
                    finish();

                }
            }
        },2000);
    }

    protected void printTransactionBill(PrintData printData,int gstPercentage, double gstAmount) {
        OutputStream opstream = null;
        try {
            opstream = NavigationDrawerActivity.btsocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStream = opstream;

        //print command
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream = NavigationDrawerActivity.btsocket.getOutputStream();
            byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
            outputStream.write(printformat);
            printPhoto(R.drawable.fill_logo);
            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

            printCustom("INSTANT DISPENSE (GO LOCAL)", 1, 1);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

            printNewLine();
            printCustom("Franchise Name: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getName(), 0, 0);
            printCustom("GSTIN: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getGstNo(), 0, 0);
            printCustom("Refueller No: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getRegNo(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("ORDER DETAILS", 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

            try {
                printCustom("Order Date: " + printData.getOrder_data_time(), 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            printCustom("Order ID: " + printData.getOrder_id(), 0, 0);
            printCustom("Customer Name: " + printData.getCustomer_name(), 0, 0);
            printCustom("Location Name: " + printData.getLocationName(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("DELIVERY DETAILS", 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

            printCustom("Latitude: " + printData.getLatitude(), 0, 0);
            printCustom("Longitude: " + printData.getLongitude(), 0, 0);
            printCustom("GPS Status: " + printData.getGps_status(), 0, 0);
            printCustom("Terminal ID: " + printData.getTerminal_id(), 0, 0);
            printCustom("Batch no: " + printData.getBatch_no(), 0, 0);

            printNewLine();
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("PRODUCT: " + SharedPref.getLoginResponse().getVehicle_data().get(0).getProductName().toUpperCase(), 1, 1);
            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printNewLine();

            printCustom("Asset Name: " + printData.getAssets_name(), 0, 0);
            printCustom("RFID Status: " + printData.getRfid_status(), 0, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("Start Fueling Time: " + printData.getFueling_start_time(), 0, 0);
            printCustom("End Fueling Time: " + printData.getFueling_stop_time(), 0, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("Txn No: " + printData.getTransaction_no(), 1, 0);
            printCustom("Volume (in Lts): " + printData.getDispense_qty(), 1, 0);
            printCustom("Rate (in INR/Litre): " + printData.getUnit_price(), 1, 0);
            printCustom("Amount (in INR): " + printData.getAmount(), 1, 0);

            printCustom("GST (in INR) (" + gstPercentage + "%): " + String.format("%.2f", gstAmount), 1, 0);
            printCustom("Total Amount (in INR): " + String.format("%.2f", Double.parseDouble(printData.getAmount()) + gstAmount), 1, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("Meter Reading: " + printData.getMeter_reading(), 0, 0);
            printCustom("Asset Other Reading1: " + printData.getAsset_other_reading(), 0, 0);
            printCustom("Asset Other Reading2: " + printData.getAsset_other_reading2(), 0, 0);

//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("<Repeat Asset if delivered to multiple assets, and show Total figs also>", 0, 0);
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("Location Reading 1:" + mLocationLatReading1, 0, 0);
//                printCustom("Location Reading 2:" + mLocationLongReading2, 0, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
            printCustom("Delivered by:" + printData.getDeliveredBy(), 0, 0);

            printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

            printNewLine();
            printCustom(printData.getFooter_message(), 0, 1);

            printCustom(new String(new char[32]).replace("\0", "="), 0, 1);
            printNewLine();
            printNewLine();
            printNewLine();
            printNewLine();
            printNewLine();
            printNewLine();

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bbc2 = new byte[]{0x1B, 0x21, 0x05}; // 2- bold with light medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text
        try {
            switch (size) {
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
                case 4:
                    outputStream.write(bbc2);
                    break;
            }

            switch (align) {
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
            if (bmp != null) {
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
//                outputStream.write(PrinterCommands.ESC_HORIZONTAL_CENTERS);
                printText(command);
//                printCustomPhoto(command, 0, 1);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PaymentMode;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentReceivedPayment extends Fragment {
    private EditText etCustomerId, etBankName, etBankAddress, etChequeNo, etAmount, etRemark, etDate;
    private Button pay;
    private String strSelectedDate;
    private String user_id, branch_id;
    private Spinner paymentType,paymentModeS;
    final Calendar myCalendar = Calendar.getInstance();
    private Context context;
    ArrayList<String> paymentTstring=new ArrayList<String>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_received_payment, container, false);
        user_id = PreferenceManager.read("Login_id", "");
        branch_id = PreferenceManager.read("branch_id", "");
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        etCustomerId = view.findViewById(R.id.et_customer_id);
        etBankName = view.findViewById(R.id.et_bank_name);
        etBankAddress = view.findViewById(R.id.bank_address);
        etChequeNo = view.findViewById(R.id.et_check_number);
        etAmount = view.findViewById(R.id.et_amount);
        etRemark = view.findViewById(R.id.Remark);
        etDate = view.findViewById(R.id.et_date);
        pay = view.findViewById(R.id.btnSubmit);
        paymentType=view.findViewById(R.id.sPaymentType);
        paymentModeS=view.findViewById(R.id.paymentModeS);
        paymentTstring.add("Selecet Payment Type");
        paymentTstring.add("Add Credet Note");
        paymentTstring.add("Add Debit Note");
        paymentTstring.add("Add Payment");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, paymentTstring);



        paymentType.setAdapter(dataAdapter);

        paymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCategory(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Service will be added soon...", Toast.LENGTH_LONG).show();

//                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("function_name", "add_payment");
//                hashMap.put("hidden_login_id", SharedPref.getLoginResponse().data.get(0).getLogin_id());
//                hashMap.put("branchId", branch_id);
//                hashMap.put("payment_type", "1");
//                hashMap.put("pay_mode", "7");
//                hashMap.put("tot_paid", etAmount.getText().toString());
//                hashMap.put("bank_name", etBankName.getText().toString());
//                hashMap.put("branch_bank", etBankAddress.getText().toString());
//                hashMap.put("deposited_bank", etCustomerId.getText().toString());
//                hashMap.put("slip_no", "");
//                hashMap.put("check_no", etChequeNo.getText().toString());
//                hashMap.put("cheque_date", strSelectedDate);
//                submitMethods(hashMap);
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }

    private void getCategory(int i) {
        ApiInterface apiInterface=ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getCategoryList(""+i).enqueue(new Callback<PaymentMode>() {
            @Override
            public void onResponse(Call<PaymentMode> call, Response<PaymentMode> response) {
                if (response.isSuccessful()){
                    if (response.body().getSucc()){
                        ArrayAdapter<PaymentMode.Datum> arrayAdapter=new ArrayAdapter<PaymentMode.Datum>(context,android.R.layout.simple_spinner_item,response.body().getData());
                        paymentModeS.setAdapter(arrayAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentMode> call, Throwable t) {
                Toast.makeText(context, "reponse not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        strSelectedDate = sdf.format(myCalendar.getTime());
        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void submitMethods(HashMap hashMap) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.payNow(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.e("Payment", response.body().toString());
                    Toast.makeText(context, "Payment Added Successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("payError", t.getMessage());
            }
        });
    }
}

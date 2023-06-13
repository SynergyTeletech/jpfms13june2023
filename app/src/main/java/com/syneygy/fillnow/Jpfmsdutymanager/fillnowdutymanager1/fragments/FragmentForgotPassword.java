package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ForgotResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.Mobiles;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentForgotPassword extends AppCompatActivity {
    EditText etOTP;
    BroadcastReceiver receiver;

    private EditText etMobileNo;
    private Button btnResetPassword;
    private String user_id;
    public EditText et_first, etSecond, etthird, etFourth, etFifth, etSixth;
    public LinearLayout ll1;
    String mobile;
    private FragmentForgotPassword mActivity;
    private String TAG = getClass().getSimpleName();


    public FragmentForgotPassword() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forgot_password);


        mActivity = this;
        etMobileNo = findViewById(R.id.et_mobile_no);
        btnResetPassword = findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = etMobileNo.getText().toString();
                if (mobile == null) {
                    Toast.makeText(FragmentForgotPassword.this, "Mobile number cannot be blank", Toast.LENGTH_SHORT).show();

                } else if ((mobile.length() < 10) || (mobile.length() >= 12)) {
                    Toast.makeText(FragmentForgotPassword.this, "Invalid Mobile Number.", Toast.LENGTH_SHORT).show();

                } else {
                    hitResetPasswordApi();
                }
            }
        });

    }


    /*
    method to reset password
     */
   /* private void hitResetPasswordApi() {
        Toast.makeText(mActivity, fragmentForgotPasswordBinding.getForgotpassword().getMobile(), Toast.LENGTH_SHORT).show();
    }*/


    /*
    method to hit reset password
     */
    private void hitResetPasswordApi() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phone", mobile);
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        /*  ForgotResponse*/
        apiInterface.getForgot(hashMap).enqueue(new Callback<Mobiles>() {
            @Override
            public void onResponse(Call<Mobiles> call, Response<Mobiles> response) {

                if (response.body().getSucc()) {
                    String otps = response.body().getOtp().getOtp();



/*
                    receiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(FragmentForgotPassword.this FragmentForgotPassword.this, Intent intent) {
                            if (intent.getAction().equalsIgnoreCase("otp")) {
                                final String message = intent.getStringExtra("message");
                                Log.e("Passeord Mess", "== " + message);
                                etOTP.setText(message);
                                //Do whatever you want with the code here
                            }
                        }
                    };
*/


                 /*   String s = response.body().getPublicMsg();
                    Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
                    long otp = response.body().getData().getOtp();*/
                    /*   openDialogForOTP(otp + "", fragmentForgotPasswordBinding.getForgotpassword().getMobile());*/
                    openDialogForOTP(otps, mobile);

/*
                   try {

                        JSONArray jsonArray = new JSONArray(s);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String otp1 = jsonObject.optString("OTP");
                        Log.e("OTP ::", otp1);
                        Toast.makeText(mActivity, "" + otp, Toast.LENGTH_SHORT).show();
                        Toast.makeText(mActivity, "" + otp, Toast.LENGTH_SHORT).show();
                        openDialogForOTP(otp1, etMobileNo.getText().toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
*/

                } else {
                    Toast.makeText(mActivity, "" + response.body().get_err_codes().get(0).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mobiles> call, Throwable t) {
                Toast.makeText(mActivity, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }


    /*
  method to open dialog
   */
    private void openDialogForOTP(final String otp, final String mobileNo) {

        TextView tvResend, tvMobileNo;
        final EditText etOTP, etPassword;

        Button btnResetPassword;

        final Dialog mDialogOTP = new Dialog(mActivity);

        mDialogOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogOTP.setContentView(R.layout.dialog_forgot_password);
        tvMobileNo = mDialogOTP.findViewById(R.id.tv_mobile_no);
        et_first = mDialogOTP.findViewById(R.id.et_first);
        etSecond = mDialogOTP.findViewById(R.id.et_second);
        etthird = mDialogOTP.findViewById(R.id.et_third);
        etFourth = mDialogOTP.findViewById(R.id.et_fourth);
        etFifth = mDialogOTP.findViewById(R.id.et_five);
        etSixth = mDialogOTP.findViewById(R.id.et_sixth);
        et_first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etSecond.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etthird.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etthird.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etFourth.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etFourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etFifth.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etFifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etSixth.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvResend = mDialogOTP.findViewById(R.id.tv_resendotp);
        btnResetPassword = mDialogOTP.findViewById(R.id.btn_reset_password);
        tvMobileNo.setText(mobileNo);

        tvResend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnected(mActivity)) {
                    mDialogOTP.hide();
                    hitResetPasswordApi();
                } else {
                    Toast.makeText(FragmentForgotPassword.this, "" + getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

                }
            }
        });


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnected(mActivity)) {

                    hitVerifyOTP(otp, mobileNo);
                    if (mDialogOTP.isShowing()) {
                        mDialogOTP.dismiss();
                    }

                } else {
                    Toast.makeText(FragmentForgotPassword.this, "" + getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

                }
            }
        });

        mDialogOTP.show();
    }


    /*
   method to hit OTP api
    */
    private void hitVerifyOTP(final String otp, final String mobileNo) {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phone", mobileNo);
        hashMap.put("otp", otp);
        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.getVerfityForgotPassword(hashMap).enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                if (response.body().getSucc()) {


                    if (response.body().getPublicMsg().contentEquals("You are successfully verified!")) {
                        Toast.makeText(mActivity, response.body().getPublicMsg(), Toast.LENGTH_LONG).show();
                        user_id = response.body().getData().getUser_id();


                    }

                    openDialogForForgotPassword(mobileNo);


                } else {
                    Toast.makeText(mActivity, response.body().getErrCodes().get(0).toString(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                Toast.makeText(mActivity, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

    }


    /*
method to open dialog
 */
    private void openDialogForForgotPassword(final String mobileNo) {

        TextView tvResend, tvMobileNo;
        final EditText etOTP, etPassword, etConfirmPassword;
        Button btnResetPassword;

        final Dialog mDialogOTP = new Dialog(mActivity);

        mDialogOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogOTP.setContentView(R.layout.dialog_forgot_password);
        ll1 = mDialogOTP.findViewById(R.id.otpView);
        et_first = mDialogOTP.findViewById(R.id.et_first);
        etSecond = mDialogOTP.findViewById(R.id.et_second);
        etthird = mDialogOTP.findViewById(R.id.et_third);
        etFourth = mDialogOTP.findViewById(R.id.et_fourth);
        etFifth = mDialogOTP.findViewById(R.id.et_five);
        etSixth = mDialogOTP.findViewById(R.id.et_sixth);

        tvMobileNo = mDialogOTP.findViewById(R.id.tv_mobile_no);
        /* etOTP = mDialogOTP.findViewById(R.id.et_otp);*/
        etPassword = mDialogOTP.findViewById(R.id.et_password);
        etConfirmPassword = mDialogOTP.findViewById(R.id.et_confirm_password);
        ll1.setVisibility(View.GONE);
        /*    etOTP.setVisibility(View.GONE);*/
        etConfirmPassword.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        tvResend = mDialogOTP.findViewById(R.id.tv_resendotp);
        btnResetPassword = mDialogOTP.findViewById(R.id.btn_reset_password);
        tvMobileNo.setText(mobileNo);
        tvResend.setVisibility(View.GONE);

        tvResend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnected(mActivity)) {
                    mDialogOTP.hide();
                    //   hitResendOTP(mobileNo);
                } else {
                    Toast.makeText(FragmentForgotPassword.this, "" + getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnected(mActivity)) {
                    mDialogOTP.hide();
                    if (etConfirmPassword.getText().toString().equals(etPassword.getText().toString())) {
                        hitSubmitPassword(mobileNo, etConfirmPassword.getText().toString(), etConfirmPassword.getText().toString());
                    } else {
                        Toast.makeText(mActivity, "Password Doesn't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FragmentForgotPassword.this, "" + getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

                }
            }
        });

        mDialogOTP.show();
    }


    /*
method to resend otp
*/
    private void hitSubmitPassword(final String mobileNo, String confirm_password, String password) {


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", user_id);
        hashMap.put("password", password);
        hashMap.put("confirm_password", confirm_password);

        ApiInterface apiClient = ApiClient.getClientCI().create(ApiInterface.class);
        apiClient.getForgotPassword(hashMap).enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {

                if (response.body().getSucc()) {
                    Toast.makeText(mActivity, response.body().getPublicMsg(), Toast.LENGTH_SHORT).show();
                    finish();
//                    Fragment fragment = new FragmentSignIn();
//                    mActivity.replaceFragment(mActivity, R.id.mainF, fragment, null, false);
                } else {
                    Toast.makeText(mActivity, response.body().getErrCodes().get(0).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                Toast.makeText(mActivity, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}


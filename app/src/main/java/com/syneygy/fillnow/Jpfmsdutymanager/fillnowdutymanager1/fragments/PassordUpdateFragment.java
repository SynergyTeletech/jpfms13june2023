package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;


import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ChangePasswordbean;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassordUpdateFragment extends Fragment implements View.OnClickListener {
private EditText edChnPass,edConPass,oldpassword;
private NavigationDrawerActivity mActivity;
private String oldPassword;
private ImageView cross1,cross2;
private Button btn_change_password;
private String user_id;

    public PassordUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_passord_update, container, false);
        mActivity=(NavigationDrawerActivity) getActivity();
        user_id = SharedPref.getLoginId();
        edChnPass=view.findViewById(R.id.ed_change_password);
        edConPass=view.findViewById(R.id.ed_con_password);
        mActivity.ivLogo.setVisibility(View.GONE);
        mActivity.mToolbar.setBackgroundColor(getResources().getColor(R.color.c_dark));
        mActivity.tvToolbar.setText("Update Password");
        mActivity.mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        mActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mActivity.tvToolbar.setTextColor(getResources().getColor(R.color.white));

        oldpassword=view.findViewById(R.id.ed_oldPassword);
        btn_change_password=view.findViewById(R.id.chn_pass_btn);
        btn_change_password.setOnClickListener(this);

       /*  cross1.setOnClickListener(this);
         cross2.setOnClickListener(this);*/
        return view;
    }

    private void hitChangePassApi(String changePass, String conPassword,String oldPassword)
    {
        mActivity.showProgressDialog(mActivity);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", user_id);
        hashMap.put("old_pass", oldPassword);
        hashMap.put("password", changePass);
        hashMap.put("confirm_password", conPassword);
        Log.e("Change Gson....",""+new Gson().toJson(hashMap));

        ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
        apiInterface.changePaswordApi(hashMap).enqueue(new Callback<ChangePasswordbean>() {
            @Override
            public void onResponse(Call<ChangePasswordbean> call, Response<ChangePasswordbean> response) {
                if (response.isSuccessful()) {
                    mActivity.hideProgressDialog(mActivity);
                    Log.e("Change Password Url",""+call.request().url());

                    Toast.makeText(mActivity, "" + response.body().getPublicMsg(), Toast.LENGTH_SHORT).show();


                } else {
                    try {
                        mActivity.hideProgressDialog(mActivity);
                        Toast.makeText(mActivity, "" + response.body().getErrCodes(), Toast.LENGTH_SHORT).show();
                    }catch (NullPointerException e){e.printStackTrace();}
                }
            }
            @Override
            public void onFailure(Call<ChangePasswordbean> call, Throwable t) {
                Toast.makeText(mActivity, getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                mActivity.hideProgressDialog(mActivity);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*case R.id.ed_cross1: edChnPass.setText("");
            break;
            case R.id.ed_cross2: edConPass.setText("");
            break;*/
            case R.id.chn_pass_btn:
                if(!oldpassword.getText().toString().contentEquals("")) {

                    if (edChnPass.getText().toString() != null && edConPass.getText().toString() != null) {
                        if (edChnPass.getText().toString().equals(edConPass.getText().toString())) {
                            hitChangePassApi(edChnPass.getText().toString(), edConPass.getText().toString(),oldpassword.getText().toString());
                        } else {
                            Toast.makeText(mActivity, "insert valid password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mActivity, "Enter some text", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(mActivity, "Old Password is mandetory", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

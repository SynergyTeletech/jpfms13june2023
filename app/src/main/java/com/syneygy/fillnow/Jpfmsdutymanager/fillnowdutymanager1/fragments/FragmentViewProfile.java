package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiInterface;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.ViewProfile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.ViewProfileData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentViewProfile extends Fragment {
    private List<ViewProfileData> viewProfileslist;
    private String TAG = FragmentViewProfile.class.getSimpleName();
    private TextView tvCompanyName, tvName, tvAddress, tvEmailId, tvPhoneNumber;
    private String baseUrl = "http://fuel.jpfms.in/ofo"; //https removed by Laukendra
    private CircleImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        tvName = view.findViewById(R.id.tv_Name);
        tvCompanyName = view.findViewById(R.id.tv_CompanyName);
        imageView = view.findViewById(R.id.profile_image);

        tvAddress = view.findViewById(R.id.tv_Address);
        tvEmailId = view.findViewById(R.id.tv_Email);
        tvPhoneNumber = view.findViewById(R.id.tv_PhoneNumber);
        viewProfileslist = new ArrayList<>();
        hitViewProfile("32"); //what is it fixed for ever? asking Laukendra
        return view;
    }

    private void hitViewProfile(String locationId) {
        if (Utils.isNetworkConnected(getContext())) {

            ApiInterface apiInterface = ApiClient.getClientCI().create(ApiInterface.class);
            apiInterface.getViewProfile(locationId).enqueue(new Callback<ViewProfile>() {
                @Override
                public void onResponse(Call<ViewProfile> call, Response<ViewProfile> response) {
                    Log.i(TAG, "" + call.request().url());
                    if (response.isSuccessful()) {
                        viewProfileslist = response.body().getData();
                        if (viewProfileslist!=null) {
                            if (viewProfileslist.size()>0) {
                                try {
                                    tvCompanyName.setText(response.body().getData().get(0).getCompany_name());
                                    tvName.setText(response.body().getData().get(0).getContact_person_name());
                                    tvAddress.setText("Noida...");
                                    tvEmailId.setText(response.body().getData().get(0).getContact_person_email());
                                    tvPhoneNumber.setText(response.body().getData().get(0).getContact_person_phone());

                                    /*  imageView.setImageURI(Uri.parse(baseUrl+response.body().getData().get(0).getProfile_img()));*/

                                    Glide.with(getActivity())
                                            .load(baseUrl + response.body().getData().get(0).getProfile_img())
                                            .into(imageView);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ViewProfile> call, Throwable t) {
                    Toast.makeText(getContext(), getResources().getString(R.string.server_problem), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

}

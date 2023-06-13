package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import android.os.Bundle;
import android.util.Log;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;

 //.............JPFMS DUTY MANAGER UPDATED CODE.......COMMENTED BY SONAL 07.jan.2022
public class MainActivity extends BaseActivity  {

    // public DataBaseHelper dataBaseHelper;
    private static final int RC_SIGN_IN = 234;
    private static final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


           /* if (findViewById(R.id.ll_container)!=null){
                if (savedInstanceState!=null){
                    return;
                }
                FragmentPlaceOrder fragmentPlaceOrder=new FragmentPlaceOrder();
                getSupportFragmentManager().beginTransaction().add(R.id.ll_container,fragmentPlaceOrder,null).commit();
            }*/
        Log.i(TAG, "onCreate");

        //  dataBaseHelper = new DataBaseHelper(this);


//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        FragmentGetStarted getStartedFragment = new FragmentGetStarted();
//        replaceFragment(MainActivity.this, R.id.ll_container, getStartedFragment, null, true);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

}

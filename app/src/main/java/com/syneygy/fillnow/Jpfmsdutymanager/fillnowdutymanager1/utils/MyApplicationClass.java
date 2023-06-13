package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.LoginActivity;


public class MyApplicationClass extends Application implements Application.ActivityLifecycleCallbacks{


    private static MyApplicationClass myApplicationClass;
    public static Context mAppContext;

//    ConnectivityRecevier connectivityRecevier;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    public void onCreate() {
        super.onCreate();

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//
//        refWatcher = LeakCanary.install(this);
//        LeakCanary.install(this);



        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        myApplicationClass = this;
        mAppContext = getApplicationContext();



    }

//    public static RefWatcher getRefWatcher(Context context) {
//        MyApplicationClass application = (MyApplicationClass) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;


    public static synchronized Context getmAppContext() {
        return mAppContext;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

        if (activity instanceof LoginActivity) {

        }
    }
}

package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.DispenseLocalDatabaseHandler;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.lk.OrderDispenseLocalData;


public class SaveGoLocalOrderOfflineService extends IntentService {

    public SaveGoLocalOrderOfflineService() {
        super("SaveGoLocalOrderOfflineService");
    }
    private DispenseLocalDatabaseHandler databaseHandler;

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            databaseHandler=new DispenseLocalDatabaseHandler(getApplicationContext());
            OrderDispenseLocalData orderDispenseLocalData =(OrderDispenseLocalData) intent.getSerializableExtra("orderDetail");
            String processType = intent.getStringExtra("process_type");
            new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    databaseHandler.addGoLocalDispenseData(orderDispenseLocalData);
                    Log.d("GoLocal","Saved GoLocal data in SQLite");
                    System.out.print("Go Local Data saved Locally ");
                    databaseHandler.close();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("GoLocal","Getting error to store GoLocal data in SQLite");
                }
            }
        }).start();

        }
    }

}

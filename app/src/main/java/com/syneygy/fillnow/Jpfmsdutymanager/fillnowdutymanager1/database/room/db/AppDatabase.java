package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.controller.FreshDispenseLocalDataAccessObject;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.controller.NewOfflineOrderDataAccessObject;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.controller.TransactionDbModelsDataAccessObject;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables.FreshDispensedLocalTableData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables.OrderDispensedLocalTableData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables.TransactionDbModel;

@Database(entities = {TransactionDbModel.class, OrderDispensedLocalTableData.class, FreshDispensedLocalTableData.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public abstract TransactionDbModelsDataAccessObject transactionDbDao();

    public abstract NewOfflineOrderDataAccessObject orderDispensedLocalTableDataDao();

    public abstract FreshDispenseLocalDataAccessObject freshDispenseLocalDataAccessObject();

}

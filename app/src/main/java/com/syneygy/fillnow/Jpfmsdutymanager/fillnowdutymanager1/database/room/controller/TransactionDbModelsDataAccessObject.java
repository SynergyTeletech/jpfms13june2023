package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.controller;

/**
 * Created by Ved Yadav on 3/13/2019.
 */


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables.TransactionDbModel;

@Dao
public interface TransactionDbModelsDataAccessObject {

    @Query("SELECT * FROM TransactionDbModel WHERE transactionId= :transactionId")
    TransactionDbModel getTransactionById(String transactionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TransactionDbModel draftedDbModels);

    @Update
    void update(TransactionDbModel transactionDetail);

    @Query("DELETE FROM TransactionDbModel WHERE transactionId= :transactionId")
    void deleteTransactionById(String transactionId);
}

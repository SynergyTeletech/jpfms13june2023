package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.controller;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables.FreshDispensedLocalTableData;

import java.util.List;

@Dao
public interface FreshDispenseLocalDataAccessObject {

    @Query("SELECT * FROM fresh_dispense_offline_order_table")
    List<FreshDispensedLocalTableData> getAllOrder();

    @Query("SELECT * FROM fresh_dispense_offline_order_table WHERE transaction_id IN (:id)")
    FreshDispensedLocalTableData getOrderById(String id);

    @Update
    void insert(FreshDispensedLocalTableData offlineOrderTableData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(FreshDispensedLocalTableData users);

    @Query("DELETE FROM fresh_dispense_offline_order_table WHERE transaction_id= :id")
    void deleteOrderById(String id);

    @Delete
    void delete(FreshDispensedLocalTableData offlineOrderTableData);

}

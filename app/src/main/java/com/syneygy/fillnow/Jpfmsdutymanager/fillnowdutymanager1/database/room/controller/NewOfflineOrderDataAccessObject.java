package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.controller;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.database.room.tables.OrderDispensedLocalTableData;

import java.util.List;

@Dao
public interface NewOfflineOrderDataAccessObject {

    @Query("SELECT * FROM new_offline_order_table")
    List<OrderDispensedLocalTableData> getAllOrder();

    @Query("SELECT * FROM new_offline_order_table WHERE transaction_id IN (:id)")
    OrderDispensedLocalTableData getOrderById(String id);

    @Update
    void insert(OrderDispensedLocalTableData offlineOrderTableData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(OrderDispensedLocalTableData users);

    @Query("DELETE FROM new_offline_order_table WHERE transaction_id= :id")
    void deleteOrderById(String id);

    @Delete
    void delete(OrderDispensedLocalTableData offlineOrderTableData);

}

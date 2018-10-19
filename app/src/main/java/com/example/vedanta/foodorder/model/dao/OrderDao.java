package com.example.vedanta.foodorder.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vedanta.foodorder.model.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getAllOrder();

    @Query("SELECT * FROM order_table LIMIT 1")
    Order[] getAnyOrder();

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);

    @Query("DELETE FROM order_table")
    void deleteAll();
}

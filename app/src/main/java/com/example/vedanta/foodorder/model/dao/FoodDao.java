package com.example.vedanta.foodorder.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vedanta.foodorder.model.entity.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM food_table")
    LiveData<List<Food>> getAllFood();

    @Query("SELECT * from food_table LIMIT 1")
    Food[] getAnyFood();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("DELETE FROM food_table")
    void deleteAll();
}

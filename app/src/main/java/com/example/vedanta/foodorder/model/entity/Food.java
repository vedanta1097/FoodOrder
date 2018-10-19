package com.example.vedanta.foodorder.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "food_table")
public class Food {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "food")
    private String mFood;

    @ColumnInfo(name = "price")
    private int mPrice;

    public Food(String mFood, int mPrice) {
        this.mFood = mFood;
        this.mPrice = mPrice;
    }

    @Ignore
    public Food(int id, String mFood, int mPrice) {
        this.id = id;
        this.mFood = mFood;
        this.mPrice = mPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood() {
        return mFood;
    }

    public void setFood(String mFood) {
        this.mFood = mFood;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }
}

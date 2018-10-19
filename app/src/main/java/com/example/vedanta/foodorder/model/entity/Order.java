package com.example.vedanta.foodorder.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "order_table")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "order")
    private String mOrder;

    @ColumnInfo(name = "total")
    private int mTotalPrice;

    public Order(String mOrder, int mTotalPrice) {
        this.mOrder = mOrder;
        this.mTotalPrice = mTotalPrice;
    }

    @Ignore
    public Order(int id, String mOrder, int mTotalPrice) {
        this.id = id;
        this.mOrder = mOrder;
        this.mTotalPrice = mTotalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder() {
        return mOrder;
    }

    public void setOrder(String mOrder) {
        this.mOrder = mOrder;
    }

    public int getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(int mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }
}

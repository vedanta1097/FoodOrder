package com.example.vedanta.foodorder.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.vedanta.foodorder.model.entity.Food;
import com.example.vedanta.foodorder.model.entity.Order;
import com.example.vedanta.foodorder.model.entity.OrderDetail;
import com.example.vedanta.foodorder.repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository mOrderRepository;
    private LiveData<List<Order>> mAllOrder;

    public OrderViewModel(Application application) {
        super(application);
        mOrderRepository = new OrderRepository(application);
        mAllOrder = mOrderRepository.getAllOrder();
    }

    public LiveData<List<Order>> getAllOrder() {
        return mAllOrder;
    }

    public void insert(Order order) {
        mOrderRepository.insert(order);
    }

    public void delete(Order order) {
        mOrderRepository.delete(order);
    }

    public void deleteAll() {
        mOrderRepository.deleteAll();
    }
}

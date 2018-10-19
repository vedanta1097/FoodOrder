package com.example.vedanta.foodorder.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.vedanta.foodorder.model.AppRoomDatabase;
import com.example.vedanta.foodorder.model.dao.OrderDao;
import com.example.vedanta.foodorder.model.entity.Order;

import java.util.List;

public class OrderRepository {

    private OrderDao mOrderDao;
    private LiveData<List<Order>> mAllOrder;

    public OrderRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mOrderDao = db.orderDao();
        mAllOrder = mOrderDao.getAllOrder();
    }

    public LiveData<List<Order>> getAllOrder() {
        return mAllOrder;
    }

    public void insert(Order order) {
        new InsertAsyncTask(mOrderDao).execute(order);
    }

    public void delete(Order order) {
        new DeleteAsyncTask(mOrderDao).execute(order);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(mOrderDao).execute();
    }

    // ==================== AsyncTask ============================

    private static class InsertAsyncTask extends AsyncTask<Order, Void, Void> {
        private OrderDao mAsyncTaskDao;

        InsertAsyncTask(OrderDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Order... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Order, Void, Void> {
        OrderDao mAsyncTaskDao;

        DeleteAsyncTask(OrderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Order... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        OrderDao mAsyncTaskDao;

        DeleteAllAsyncTask(OrderDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}

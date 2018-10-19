package com.example.vedanta.foodorder.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.vedanta.foodorder.model.AppRoomDatabase;
import com.example.vedanta.foodorder.model.dao.FoodDao;
import com.example.vedanta.foodorder.model.entity.Food;

import java.util.List;

public class FoodRepository {

    private FoodDao mFoodDao;
    private LiveData<List<Food>> mAllFood;

    public FoodRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mFoodDao = db.foodDao();
        mAllFood = mFoodDao.getAllFood();
    }

    public LiveData<List<Food>> getAllFood() {
        return mAllFood;
    }

    public void insert(Food food) {
        new InsertAsyncTask(mFoodDao).execute(food);
    }

    public void update(Food food) {
        new UpdateAsyncTask(mFoodDao).execute(food);
    }

    public void delete(Food food) {
        new DeleteAsyncTask(mFoodDao).execute(food);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(mFoodDao).execute();
    }

    // ==================== AsyncTask ============================

    private static class InsertAsyncTask extends AsyncTask<Food, Void, Void> {
        private FoodDao mAsyncTaskDao;

        InsertAsyncTask(FoodDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Food... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Food, Void, Void> {
        FoodDao mAsyncTaskDao;

        UpdateAsyncTask(FoodDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Food... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Food, Void, Void> {
        FoodDao mAsyncTaskDao;

        DeleteAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Food... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        FoodDao mAsyncTaskDao;

        DeleteAllAsyncTask(FoodDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}

package com.example.vedanta.foodorder.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.vedanta.foodorder.model.dao.FoodDao;
import com.example.vedanta.foodorder.model.dao.OrderDao;
import com.example.vedanta.foodorder.model.entity.Food;
import com.example.vedanta.foodorder.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Food.class, Order.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    //abstract getter to access food dao
    public abstract FoodDao foodDao();

    //abstract getter to access order dao
    public abstract OrderDao orderDao();

    private static AppRoomDatabase INSTANCE;

    //Create the AppRoomDatabase as a singleton to prevent
    // having multiple instances of the database opened at the same time
    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "food_order_database")
                            // don't provide migrations and
                            // clear the database when upgrade the version
                            .fallbackToDestructiveMigration()
                            //initialize food data
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new populateDbAsync(INSTANCE).execute();
        }
    };

    private static class populateDbAsync extends AsyncTask<Void, Void, Void> {

        private final FoodDao mFoodDao;
        private final OrderDao mOrderDao;

        populateDbAsync(AppRoomDatabase db) {
            mFoodDao = db.foodDao();
            mOrderDao = db.orderDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            // Initial list of foods for placeholder
            if (mFoodDao.getAnyFood().length < 1) {
                mFoodDao.insert(new Food("Takoyaki", 5000));
                mFoodDao.insert(new Food("Okonomiyaki", 15000));
                mFoodDao.insert(new Food("Ramen", 25000));
                mFoodDao.insert(new Food("Karaage", 10000));
                mFoodDao.insert(new Food("Gyutan", 65000));
            }

            // Initial list of orders for placeholder
            if (mOrderDao.getAnyOrder().length < 1) {
                mOrderDao.insert(new Order("Takoyaki (x1)\nOkonomiyaki (x1)", 20000));
                mOrderDao.insert(new Order("Gyutan (x1)", 65000));
                mOrderDao.insert(new Order("Ramen (x1)\nTakoyaki (x1)\nKaraage (x1)", 40000));
            }
            return null;
        }
    }
}

package com.example.vedanta.foodorder.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.vedanta.foodorder.model.entity.Food;
import com.example.vedanta.foodorder.repository.FoodRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private FoodRepository mFoodRepository;
    private LiveData<List<Food>> mAllFood;

    public FoodViewModel (Application application) {
        super(application);
        mFoodRepository = new FoodRepository(application);
        mAllFood = mFoodRepository.getAllFood();
    }

    public LiveData<List<Food>> getAllFood() {
        return mAllFood;
    }

    public void insert(Food food) {
        mFoodRepository.insert(food);
    }

    public void update(Food food) {
        mFoodRepository.update(food);
    }

    public void delete(Food food) {
        mFoodRepository.delete(food);
    }

    public void deleteAll() {
        mFoodRepository.deleteAll();
    }
}

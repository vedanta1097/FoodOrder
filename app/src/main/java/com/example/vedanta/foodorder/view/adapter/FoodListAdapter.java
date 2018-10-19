package com.example.vedanta.foodorder.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vedanta.foodorder.R;
import com.example.vedanta.foodorder.model.entity.Food;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private final LayoutInflater mInflater;
    private static ClickListener clickListener;
    private List<Food> mFoods;

    public FoodListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setFood(List<Food> foods) {
        mFoods = foods;
        notifyDataSetChanged();
    }

    public Food getFoodAtPosition (int position) {
        return mFoods.get(position);
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.food_recyclerview_item, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        if (mFoods != null) {
            final Food currentFood = mFoods.get(position);
            holder.mFoodTextView.setText(currentFood.getFood());
            holder.mPriceTextView.setText(String.valueOf(currentFood.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        if (mFoods != null) {
            return mFoods.size();
        }
        else return 0;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        private final TextView mFoodTextView, mPriceTextView;

        FoodViewHolder(View itemView) {
            super(itemView);
            mFoodTextView = itemView.findViewById(R.id.food_title);
            mPriceTextView = itemView.findViewById(R.id.food_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        FoodListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position);
    }
}

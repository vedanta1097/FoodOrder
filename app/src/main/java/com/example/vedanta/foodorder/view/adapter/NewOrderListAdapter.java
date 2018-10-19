package com.example.vedanta.foodorder.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vedanta.foodorder.R;
import com.example.vedanta.foodorder.model.entity.Food;
import com.example.vedanta.foodorder.model.entity.OrderDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NewOrderListAdapter extends RecyclerView.Adapter<NewOrderListAdapter.NewOrderViewHolder> {

    private final LayoutInflater mInflater;
    private List<Food> mFoods;
    private List<OrderDetail> mOrderDetail = new ArrayList<>();

    public NewOrderListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setFood(List<Food> foods) {
        mFoods = foods;
        notifyDataSetChanged();
    }

    public List<OrderDetail> getOrderList() {
        return mOrderDetail;
    }

    @NonNull
    @Override
    public NewOrderListAdapter.NewOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.new_order_recyclerview_item, parent, false);
        mOrderDetail.clear();
        //initialize order detail
        for (int i = 0; i < mFoods.size(); i++) {
            Food currentFood = mFoods.get(i);
            OrderDetail currentOrder = new OrderDetail(currentFood.getFood(), currentFood.getPrice(), 0);
            mOrderDetail.add(currentOrder);
        }
        return new NewOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewOrderListAdapter.NewOrderViewHolder holder, final int position) {
        if (mFoods != null) {
            final Food currentFood = mFoods.get(position);
            holder.mFoodTextView.setText(currentFood.getFood());
            holder.mPriceTextView.setText(String.valueOf(currentFood.getPrice()));

            final OrderDetail currentOrder = mOrderDetail.get(position);

            holder.mPlusQtyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentOrder.getFood().equalsIgnoreCase(currentFood.getFood())) {
                        int qty = currentOrder.getQuantity();
                        currentOrder.setQuantity(++qty);
                        holder.mQtyTextView.setText(String.valueOf(qty));
                        mOrderDetail.set(position, currentOrder);
                    }
                }
            });

            holder.mMinusQtyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentOrder.getFood().equalsIgnoreCase(currentFood.getFood())) {
                        int qty = currentOrder.getQuantity();
                        if (qty != 0) {
                            currentOrder.setQuantity(--qty);
                            holder.mQtyTextView.setText(String.valueOf(qty));
                            mOrderDetail.set(position, currentOrder);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mFoods != null) {
            return mFoods.size();
        }
        else return 0;
    }

    class NewOrderViewHolder extends RecyclerView.ViewHolder {

        private final TextView mFoodTextView, mPriceTextView, mQtyTextView;
        private final ImageButton mPlusQtyButton, mMinusQtyButton;

        NewOrderViewHolder(View itemView) {
            super(itemView);
            mFoodTextView = itemView.findViewById(R.id.food_title);
            mPriceTextView = itemView.findViewById(R.id.food_price);
            mPlusQtyButton = itemView.findViewById(R.id.image_button_plus);
            mMinusQtyButton = itemView.findViewById(R.id.image_button_minus);
            mQtyTextView = itemView.findViewById(R.id.quantity);
        }
    }
}

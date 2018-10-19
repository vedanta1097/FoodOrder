package com.example.vedanta.foodorder.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.vedanta.foodorder.R;
import com.example.vedanta.foodorder.model.entity.Food;
import com.example.vedanta.foodorder.model.entity.OrderDetail;
import com.example.vedanta.foodorder.view.adapter.NewOrderListAdapter;
import com.example.vedanta.foodorder.viewmodel.FoodViewModel;
import static android.content.ContentValues.TAG;

import java.util.List;

public class NewOrderActivity extends AppCompatActivity {

    private NewOrderListAdapter adapter;

    public static final String EXTRA_REPLY_ORDER_LIST = "EXTRA_REPLY_ORDER_LIST";
    public static final String EXTRA_REPLY_ORDER_TOTAL = "EXTRA_REPLY_ORDER_TOTAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        this.setTitle("Add Order");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_new_order);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewOrderListAdapter(this);
        recyclerView.setAdapter(adapter);

        //get food viewmodel and observe food data change
        FoodViewModel mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        mFoodViewModel.getAllFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> foods) {
                adapter.setFood(foods);
            }
        });

        Button button = findViewById(R.id.add_new_order);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<OrderDetail> mOrderDetail =  adapter.getOrderList();
                String orderListName = "";
                int totalPrice = 0;

                for (int i = 0; i < mOrderDetail.size(); i++) {
                    OrderDetail currentItem = mOrderDetail.get(i);
                    if (currentItem.getQuantity() != 0) {
                        totalPrice += (currentItem.getPrice() * currentItem.getQuantity());
                        orderListName += (currentItem.getFood() + " (" + currentItem.getQuantity() + ")\n");
                    }
                }

                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY_ORDER_LIST, orderListName);
                replyIntent.putExtra(EXTRA_REPLY_ORDER_TOTAL, totalPrice);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}

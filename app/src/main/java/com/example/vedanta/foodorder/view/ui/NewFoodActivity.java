package com.example.vedanta.foodorder.view.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vedanta.foodorder.R;

import static com.example.vedanta.foodorder.view.ui.FoodFragment.EXTRA_DATA_FOOD_ID;
import static com.example.vedanta.foodorder.view.ui.FoodFragment.EXTRA_DATA_FOOD_NAME;
import static com.example.vedanta.foodorder.view.ui.FoodFragment.EXTRA_DATA_FOOD_PRICE;

public class NewFoodActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_FOOD_NAME = "EXTRA_REPLY_FOOD_NAME";
    public static final String EXTRA_REPLY_FOOD_PRICE = "EXTRA_REPLY_FOOD_PRICE";
    public static final String EXTRA_REPLY_FOOD_ID = "EXTRA_REPLY_FOOD_ID";

    private EditText mEditTextFood, mEditTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);
        this.setTitle("Add New Food");

        mEditTextFood = findViewById(R.id.new_food);
        mEditTextPrice = findViewById(R.id.new_food_price);

        final Bundle extras = getIntent().getExtras();

        //fill the EditText with data
        if (extras != null) {
            String foodName = extras.getString(EXTRA_DATA_FOOD_NAME, "");
            int foodPrice = extras.getInt(EXTRA_DATA_FOOD_PRICE, -1);
            if (!foodName.isEmpty() && foodPrice != -1) {
                mEditTextFood.setText(foodName);
                mEditTextPrice.setText(String.valueOf(foodPrice));
                mEditTextFood.requestFocus();
            }
        }

        final Button button = findViewById(R.id.new_food_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(mEditTextFood.getText()) || TextUtils.isEmpty(mEditTextPrice.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String food = mEditTextFood.getText().toString();
                    int price = Integer.parseInt(mEditTextPrice.getText().toString());

                    if (extras != null && extras.containsKey(EXTRA_DATA_FOOD_ID)) {
                        int id = extras.getInt(EXTRA_DATA_FOOD_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_FOOD_ID, id);
                        }
                    }

                    replyIntent.putExtra(EXTRA_REPLY_FOOD_NAME, food);
                    replyIntent.putExtra(EXTRA_REPLY_FOOD_PRICE, price);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}

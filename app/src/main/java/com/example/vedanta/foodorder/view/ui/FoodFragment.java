package com.example.vedanta.foodorder.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vedanta.foodorder.R;
import com.example.vedanta.foodorder.model.entity.Food;
import com.example.vedanta.foodorder.view.adapter.FoodListAdapter;
import com.example.vedanta.foodorder.viewmodel.FoodViewModel;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.vedanta.foodorder.view.ui.NewFoodActivity.EXTRA_REPLY_FOOD_ID;
import static com.example.vedanta.foodorder.view.ui.NewFoodActivity.EXTRA_REPLY_FOOD_NAME;
import static com.example.vedanta.foodorder.view.ui.NewFoodActivity.EXTRA_REPLY_FOOD_PRICE;

public class FoodFragment extends Fragment {

    private FoodListAdapter adapter;
    private FoodViewModel mFoodViewModel;

    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_FOOD_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_FOOD_NAME = "EXTRA_DATA_FOOD_NAME";
    public static final String EXTRA_DATA_FOOD_PRICE = "EXTRA_DATA_FOOD_PRICE";
    public static final String EXTRA_DATA_FOOD_ID = "EXTRA_DATA_FOOD_ID";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food, container, false);
        setHasOptionsMenu(true);
        Objects.requireNonNull(getActivity()).setTitle("Food List");

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_food);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FoodListAdapter(getContext());
        recyclerView.setAdapter(adapter);

        //get food viewmodel and observe food data change
        mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        mFoodViewModel.getAllFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> foods) {
                adapter.setFood(foods);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_food);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewFoodActivity.class);
                startActivityForResult(intent, NEW_FOOD_ACTIVITY_REQUEST_CODE);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Food food = adapter.getFoodAtPosition(position);
                mFoodViewModel.delete(food);
                Toast.makeText(getContext(), food.getFood() + " deleted.", Toast.LENGTH_SHORT).show();
            }
        });
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new FoodListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                Food food = adapter.getFoodAtPosition(position);
                launchUpdateFoodActivity(food);
            }
        });

        return rootView;
    }

    public void launchUpdateFoodActivity(Food food) {
        Intent intent = new Intent(getActivity(), NewFoodActivity.class);
        intent.putExtra(EXTRA_DATA_FOOD_NAME, food.getFood());
        intent.putExtra(EXTRA_DATA_FOOD_PRICE, food.getPrice());
        intent.putExtra(EXTRA_DATA_FOOD_ID, food.getId());
        startActivityForResult(intent, UPDATE_FOOD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String foodName = data.getStringExtra(EXTRA_REPLY_FOOD_NAME);
            int price = data.getIntExtra(EXTRA_REPLY_FOOD_PRICE, -1);
            mFoodViewModel.insert(new Food(foodName, price));

        } else if (requestCode == UPDATE_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String foodName = data.getStringExtra(EXTRA_REPLY_FOOD_NAME);
            int price = data.getIntExtra(EXTRA_REPLY_FOOD_PRICE, -1);
            int id = data.getIntExtra(EXTRA_REPLY_FOOD_ID, -1);
            mFoodViewModel.update(new Food(id, foodName, price));

        } else {
            Toast.makeText(getContext(), "No food to save.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all) {
            mFoodViewModel.deleteAll();
            Toast.makeText(getContext(), "All food data deleted.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public FoodFragment() {
        // Required empty public constructor
    }
}

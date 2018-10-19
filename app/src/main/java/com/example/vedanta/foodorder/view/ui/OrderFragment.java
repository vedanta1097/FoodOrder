package com.example.vedanta.foodorder.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.example.vedanta.foodorder.model.entity.Order;
import com.example.vedanta.foodorder.view.adapter.OrderListAdapter;
import com.example.vedanta.foodorder.viewmodel.OrderViewModel;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.vedanta.foodorder.view.ui.NewOrderActivity.EXTRA_REPLY_ORDER_LIST;
import static com.example.vedanta.foodorder.view.ui.NewOrderActivity.EXTRA_REPLY_ORDER_TOTAL;

public class OrderFragment extends Fragment {

    private OrderListAdapter adapter;
    private OrderViewModel mOrderViewModel;

    public static final int NEW_ORDER_ACTIVITY_REQUEST_CODE = 11;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        setHasOptionsMenu(true);
        Objects.requireNonNull(getActivity()).setTitle("Order List");

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_order);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderListAdapter(getContext());
        recyclerView.setAdapter(adapter);

        //get order viewmodel and observe order data change
        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        mOrderViewModel.getAllOrder().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orders) {
                adapter.setOrders(orders);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add_order);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewOrderActivity.class);
                startActivityForResult(intent, NEW_ORDER_ACTIVITY_REQUEST_CODE);
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
                Order order = adapter.getOrderAtPosition(position);
                mOrderViewModel.delete(order);
                Toast.makeText(getContext(), "Order deleted.", Toast.LENGTH_LONG).show();
            }
        });
        helper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ORDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String orderList = data.getStringExtra(EXTRA_REPLY_ORDER_LIST);
            int orderTotal = data.getIntExtra(EXTRA_REPLY_ORDER_TOTAL, -1);
            mOrderViewModel.insert(new Order(orderList, orderTotal));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_all) {
            mOrderViewModel.deleteAll();
            Toast.makeText(getContext(), "All order data deleted.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public OrderFragment() {
        // Required empty public constructor
    }
}

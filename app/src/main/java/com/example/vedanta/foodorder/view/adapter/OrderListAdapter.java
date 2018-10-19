package com.example.vedanta.foodorder.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vedanta.foodorder.R;
import com.example.vedanta.foodorder.model.entity.Order;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private final LayoutInflater mInflater;
    private List<Order> mOrders;

    public OrderListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setOrders(List<Order> orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }

    public Order getOrderAtPosition(int position) {
        return mOrders.get(position);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.order_recyclerview_item, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        if (mOrders != null) {
            final Order currentOrder = mOrders.get(position);
            holder.mOrderTextView.setText(currentOrder.getOrder());
            holder.mTotalTextView.setText(String.valueOf(currentOrder.getTotalPrice()));
        }
    }

    @Override
    public int getItemCount() {
        if (mOrders != null) {
            return mOrders.size();
        }
        return 0;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView mOrderTextView, mTotalTextView;

        OrderViewHolder(View itemView) {
            super(itemView);
            mOrderTextView = itemView.findViewById(R.id.order_list);
            mTotalTextView = itemView.findViewById(R.id.order_total);
        }
    }
}

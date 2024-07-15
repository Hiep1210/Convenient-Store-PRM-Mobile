package com.example.convenientstoremobile.manager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convenientstoremobile.OrderDetailActivity;
import com.example.convenientstoremobile.R;
import com.example.convenientstoremobile.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ProductViewHolder> {
    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        System.out.println("THIS PRODUCT ADAPTER IS CALL!!!!!!!");
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_row_item, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {

        holder.ordName.setText("Order "+orderList.get(position).getId());

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        // Define the output format
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(orderList.get(position).getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Format the parsed date to desired output format
        String formattedDate = outputFormat.format(date);
        holder.ordDate.setText(formattedDate);
        holder.ordAddress.setText(orderList.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, OrderDetailActivity.class);
                i.putExtra("orderId", orderList.get(position).getId() + "");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView ordName, ordDate, ordAddress;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ordName = itemView.findViewById(R.id.ord_name);
            ordDate = itemView.findViewById(R.id.order_date);
            ordAddress = itemView.findViewById(R.id.ord_address);
        }
    }
}

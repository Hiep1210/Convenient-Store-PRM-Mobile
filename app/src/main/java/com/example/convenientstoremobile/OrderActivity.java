package com.example.convenientstoremobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.manager.adapter.OrderAdapter;
import com.example.convenientstoremobile.manager.adapter.ProductAdapter;
import com.example.convenientstoremobile.model.Order;
import com.example.convenientstoremobile.model.OrderDetail;
import com.example.convenientstoremobile.model.Product;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    OrderAdapter orderAdapter;
    SharedPreferences sharedPreferences;
    RecyclerView ordItemRecycler;
    ProgressDialog progressDialog;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ordItemRecycler = findViewById(R.id.orderRecycle);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait While Retrieving Your Orders");
        progressDialog.setTitle("Order");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        APIService.apiService.getAllOrder()
                .enqueue(new Callback<List<Order>>(){
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        List<Order> orders = response.body();
                        progressDialog.dismiss();
                        if(orders != null){
                            setOrdItemRecycler(orders);
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        Toast.makeText(context, "Retrieved Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void setOrdItemRecycler(List<Order> lp) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        ordItemRecycler.setLayoutManager(layoutManager);
        orderAdapter = new OrderAdapter(this, lp);
        ordItemRecycler.setAdapter(orderAdapter);
    }

}
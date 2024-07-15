package com.example.convenientstoremobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.manager.adapter.ProductAdapter;
import com.example.convenientstoremobile.model.Order;
import com.example.convenientstoremobile.model.OrderDetail;
import com.example.convenientstoremobile.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    ProductAdapter productAdapter;
    SharedPreferences sharedPreferences;
    RecyclerView  prodItemRecycler;
    EditText addressTxt;
    TextView tvTotalPrice;
    Button checkoutBtn;
    double totalPrice = 0;
    ProgressDialog progressDialog;
    Context context;
    List<OrderDetail> orderDetailList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        prodItemRecycler = findViewById(R.id.product_recycler);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait While Retrieving Your Order");
        progressDialog.setTitle("Order Detail");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Intent intent = getIntent();
        int orderId = Integer.parseInt(intent.getStringExtra("orderId"));

        APIService.apiService.getAllOrder("id eq "+orderId)
                .enqueue(new Callback<List<Order>>(){
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        List<Order> orders = response.body();
                        progressDialog.dismiss();
                        if(orders != null){
                            orderDetailList = orders.get(0).getOrderdetails();
                            for (OrderDetail detail : orderDetailList) {
                                totalPrice+= detail.getQuantity()*detail.getProduct().getPrice();
                            }
                            setProdItemRecycler(orderDetailList.stream()
                                    .map(OrderDetail::getProduct)
                                    .collect(Collectors.toList()));
                            tvTotalPrice.setText((Math.round(totalPrice * 100.0) / 100.0)+" $");
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        Toast.makeText(context, "Retrieved Failed", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private void setProdItemRecycler(List<Product> lp) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, lp);
        prodItemRecycler.setAdapter(productAdapter);
    }

}
package com.example.convenientstoremobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.convenientstoremobile.Enum.ProjEnum;
import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.model.Category;
import com.example.convenientstoremobile.model.Order;
import com.example.convenientstoremobile.model.OrderDetail;
import com.example.convenientstoremobile.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView productName, productPrice, productDescription, categoryName, productSupplier, quantityTxt;
    ImageView productImage, backImage, minusBtn, plusBtn;
    Button addToCartBtn;
    Product p;
    Category c;
    Context context;
    SharedPreferences sharedpreferences;
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        sharedpreferences = getSharedPreferences(ProjEnum.MyPREFERENCES.getValue(), Context.MODE_PRIVATE);
        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");

        quantityTxt = findViewById(R.id.quantityTxt);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        //call api to get product by id
        APIService.apiService.getProductById(Integer.parseInt(productId))
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Product product = response.body();
                        if(product != null){
                            addProductValueToPage(product);
                            addCategoryValueToPage(product.getCat());
                        }
                        else{
                            Toast.makeText(context, "Log In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(context, " "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(quantityTxt.getText().toString())==1) return;
                quantityTxt.setText(String.valueOf(Integer.parseInt(quantityTxt.getText().toString())-1));
            }
        });

        plusBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityTxt.setText(String.valueOf(Integer.parseInt(quantityTxt.getText().toString())+1));
            }
        }));

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<OrderDetail> details = new ArrayList<>();
                OrderDetail detail = new OrderDetail();
                detail.setQuantity(Integer.parseInt(quantityTxt.getText().toString()));
                detail.setProductId(Integer.parseInt(productId));
                details.add(detail);

                Order order = new Order();
                order.setDate(LocalDateTime.now().toString());
                order.setAddress("Ha Noi");
                order.setProcess(false);
                order.setOrderdetails(details);
                order.setUserId(sharedpreferences.getInt("user", 0));
                APIService.apiService.addOrder(order)
                        .enqueue(new Callback<Order>(){
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                Order orders = response.body();
                                if(orders != null){
                                    Intent intent = new Intent(ProductDetailsActivity.this, OrderActivity.class);
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {
                                Toast.makeText(context, "Retrieved Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    private void addCategoryValueToPage(Category c) {
        categoryName = findViewById(R.id.textView7);
        categoryName.setText(c.getName());
    }

    private void addProductValueToPage(Product p) {
        productDescription = findViewById(R.id.textView13);
        productName = findViewById(R.id.textView11);
        productPrice = findViewById(R.id.textView12);
        productImage = findViewById(R.id.imgSave);
        backImage = findViewById(R.id.imageView);
        productSupplier = findViewById(R.id.textView10);

        productDescription.setText("New Product!!");
        productName.setText(p.getName());
        productPrice.setText(p.getPrice() + " $");
        String url = p.getImage() == null ?
                "https://res.cloudinary.com/dnvpqwf4y/image/upload/v1718534465/depositphotos_199193024-stock-photo-new-product-concept-illustration-isolated_jy57s1.webp" :
                p.getImage().getUrl();

        Glide.with(this).load(url).into(productImage);
        productSupplier.setText(p.getSupplier().getName());
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
    }

    //anhpd add

}

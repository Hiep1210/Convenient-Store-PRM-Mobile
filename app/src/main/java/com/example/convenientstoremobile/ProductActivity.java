package com.example.convenientstoremobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convenientstoremobile.Enum.ProjEnum;
import com.example.convenientstoremobile.api.APIService;
import com.example.convenientstoremobile.manager.adapter.CategoryAdapter;
import com.example.convenientstoremobile.manager.adapter.ProductAdapter;
import com.example.convenientstoremobile.model.Category;
import com.example.convenientstoremobile.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    public static List<Product> productList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView productCatRecycler;
    RecyclerView prodItemRecycler;
    TextView textViewAll, textViewBeverage, textViewBakery, textViewFrozen, textViewHouse;
    ProductAdapter productAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_menu);
        bindingView();
        bindingAction();
        getProducts();
    }

    private void getProducts(){
        APIService.apiService.getAllProduct()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                        productList = response.body();
                        if(productList != null && !productList.isEmpty()){
                            setProdItemRecycler(productList);
                            sharedPreferences = getSharedPreferences(ProjEnum.ProdPREFERENCE.getValue(), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String gsonList = gson.toJson(productList);
                            editor.putString("prod", gsonList);
                            editor.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(ProductActivity.this,"We are cooked",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setProductRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        productCatRecycler.setAdapter(categoryAdapter);
    }

    private void setProdItemRecycler(List<Product> productsList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, productsList);
        prodItemRecycler.setAdapter(productAdapter);
    }

    private void bindingView() {
        textViewAll = findViewById(R.id.textViewAllProduct);
        productCatRecycler = findViewById(R.id.cat_recycler);
        prodItemRecycler = findViewById(R.id.product_recycler);
        textViewBeverage = findViewById(R.id.textViewBeverage);
        textViewBakery = findViewById(R.id.textViewBakery);
        textViewFrozen = findViewById(R.id.textViewFrozen);
        textViewHouse = findViewById(R.id.textViewHouse);
    }

    private void bindingAction() {
        textViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProducts();
            }
        });
        textViewBeverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = productList.stream()
                        .filter(product -> "Beverages".equals(product.getCat().getName())).collect(Collectors.toList());
                setProdItemRecycler(products);
            }
        });
        textViewBakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = productList.stream()
                        .filter(product -> "Bakery Items".equals(product.getCat().getName())).collect(Collectors.toList());
                setProdItemRecycler(products);
            }
        });
        textViewFrozen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = productList.stream()
                        .filter(product -> "Frozen Foods".equals(product.getCat().getName())).collect(Collectors.toList());
                setProdItemRecycler(products);
            }
        });

        textViewHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = productList.stream()
                        .filter(product -> "Household Supplies".equals(product.getCat().getName())).collect(Collectors.toList());
                setProdItemRecycler(products);
            }
        });
    }
}

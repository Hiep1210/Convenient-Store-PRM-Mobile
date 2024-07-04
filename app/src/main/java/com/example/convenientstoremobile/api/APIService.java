package com.example.convenientstoremobile.api;

import com.example.convenientstoremobile.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy")
            .create();
    APIService apiService = new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(ConfigHTTP.getUnsafeOkHttpClient().build())
            .build().create(APIService.class);

    @GET("api/Products")
//    Call<Product> getAllProduct(@Query("$skip") String skip);
    Call<List<Product>> getAllProduct();

}

package com.example.convenientstoremobile.api;

import com.example.convenientstoremobile.model.Order;
import com.example.convenientstoremobile.model.OrderDetail;
import com.example.convenientstoremobile.model.Product;
import com.example.convenientstoremobile.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy")
            .create();
    //10.0.2.2  27.67.182.179
    APIService apiService = new Retrofit.Builder().baseUrl("https://calofitweb.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(ConfigHTTP.getUnsafeOkHttpClient().build())
            .build().create(APIService.class);

    @GET("api/Products")
//    Call<Product> getAllProduct(@Query("$skip") String skip);
    Call<List<Product>> getAllProduct();

    @GET("api/Products/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @GET("api/Users")
//    Call<Product> getAllProduct(@Query("$skip") String skip);
    Call<List<User>> getAllUser(@Query("$filter") String filter);

    @POST("api/Users")
    Call<User> addUser(@Body User user);

    @GET("api/Users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @PUT("api/Users/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

    @POST("api/Orders")
    Call<Order> addOrder(@Body Order order);

    @GET("api/Orders")
    Call<List<Order>> getAllOrder(@Query("$filter") String filter);

    @GET("api/Orders")
    Call<List<Order>> getAllOrder();
}

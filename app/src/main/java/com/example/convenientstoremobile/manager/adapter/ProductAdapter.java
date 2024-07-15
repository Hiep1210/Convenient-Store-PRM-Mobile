package com.example.convenientstoremobile.manager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.convenientstoremobile.ProductDetailsActivity;
import com.example.convenientstoremobile.R;
import com.example.convenientstoremobile.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    Context context;
    List<Product> productsList;

    public ProductAdapter(Context context, List<Product> productsList) {
        this.context = context;
        this.productsList = productsList;
        System.out.println("THIS PRODUCT ADAPTER IS CALL!!!!!!!");
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_row_item, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        String url = productsList.get(position).getImage() == null ?
                "https://res.cloudinary.com/dnvpqwf4y/image/upload/v1718534465/depositphotos_199193024-stock-photo-new-product-concept-illustration-isolated_jy57s1.webp" :
                productsList.get(position).getImage().getUrl();

        Glide.with(this.context).load(url).into(holder.prodImage);

//        holder.prodImage.setImageBitmap(mIcon_val);
        holder.prodName.setText(productsList.get(position).getName());
        holder.prodSupplier.setText("" + productsList.get(position).getSupplier().getName());
        holder.prodPrice.setText(productsList.get(position).getPrice() + " $");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProductDetailsActivity.class);
                i.putExtra("productId", productsList.get(position).getId() + "");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView prodImage;
        TextView prodName, prodSupplier, prodPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImage = itemView.findViewById(R.id.prod_image);
            prodName = itemView.findViewById(R.id.prod_name);
            prodPrice = itemView.findViewById(R.id.prod_price);
            prodSupplier = itemView.findViewById(R.id.prod_sup);
        }
    }
}

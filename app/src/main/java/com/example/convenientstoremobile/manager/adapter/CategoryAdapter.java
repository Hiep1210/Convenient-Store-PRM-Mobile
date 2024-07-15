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

import com.example.convenientstoremobile.ProductActivity;
import com.example.convenientstoremobile.R;
import com.example.convenientstoremobile.model.Category;
import com.example.convenientstoremobile.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ProductViewHolder> {
    Context context;
    List<Category> categoryList;
    RecyclerView prodItemRecycler;
    ProductAdapter productAdapter;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row_item, parent, false);
        // lets create a recyclerview row item layout file
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryName.setText(categoryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Product> productsListUpdate = new ArrayList<>();
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX OLD: " + ProductActivity.productList.size());
                for (Product p : ProductActivity.productList) {
                    if (p.getCatId() == categoryList.get(position).getId()) {
                        productsListUpdate.add(p);
                    }
                }
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX UPDATE: " + productsListUpdate.size());
                ProductActivity.productList = null;
                Intent i = new Intent(context, ProductActivity.class);
                i.putExtra("list", productsListUpdate);
                context.startActivity(i);
            }
        });
    }

//    public ArrayList<Products> removeDuplicates(List<Products> list) {
//        Set set = new TreeSet(new Comparator() {
//            @Override
//            public int compare(Object o1, Object o2) {
//                if (((Products) o1).getProductName().equalsIgnoreCase(((Products) o2).getProductName())){
//                    return 0;
//                }
//                return 1;
//            }
//        });
//        set.addAll(list);
//        final ArrayList newList = new ArrayList(set);
//        return newList;
//    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.cat_name);
        }
    }
}
